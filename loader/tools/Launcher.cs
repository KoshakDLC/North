using System;
using System.Diagnostics;
using System.IO;
using System.Reflection;
using System.Runtime.InteropServices;
using System.Text.RegularExpressions;
using System.Windows.Forms;

[assembly: AssemblyTitle("NorthLoader")]
[assembly: AssemblyDescription("Официальный загрузчик клиента North для Minecraft.")]
[assembly: AssemblyConfiguration("")]
[assembly: AssemblyCompany("KoshakDLC")]
[assembly: AssemblyProduct("NorthLoader")]
[assembly: AssemblyCopyright("Copyright © KoshakDLC 2026")]
[assembly: AssemblyTrademark("")]
[assembly: AssemblyCulture("")]
[assembly: AssemblyVersion("1.0.0.0")]
[assembly: AssemblyFileVersion("1.0.0.0")]
[assembly: ComVisible(false)]
[assembly: Guid("b7e2c1a0-4f5d-4a8e-9c31-a1b2c3d4e5f6")]

/// <summary>
/// Thin Windows bootstrap for NorthLoader.
/// Unpacks the bundled UI jar into LocalAppData and starts it with a local Java 17+.
/// Does not download runtimes silently — that pattern trips antivirus heuristics.
/// </summary>
internal static class Program {
   private const string JarResource = "north-loader.jar";
   private const string ProductDir = "NorthLoader";
   private const int MinJava = 17;
   private const string JavaHelpUrl = "https://adoptium.net/temurin/releases/?version=17&os=windows&package=jre";

   [STAThread]
   private static void Main(string[] args) {
      Application.EnableVisualStyles();
      Application.SetCompatibleTextRenderingDefault(false);

      bool debug = args.Length > 0 && string.Equals(args[0], "debug", StringComparison.OrdinalIgnoreCase);
      TextWriter log = debug ? Console.Out : TextWriter.Null;
      if (debug) {
         AllocConsole();
      }

      try {
         string install = Path.Combine(
            Environment.GetFolderPath(Environment.SpecialFolder.LocalApplicationData),
            "Programs",
            ProductDir
         );
         Directory.CreateDirectory(install);

         string jar = EnsureJar(install, log);
         string javaBin = FindJava(log);
         if (javaBin == null) {
            if (!AskInstallJava(debug)) {
               return;
            }

            javaBin = FindJava(log);
            if (javaBin == null) {
               Fail(
                  "Java 17+ всё ещё не найдена.\n\n"
                  + "Установи Temurin JRE 17 с adoptium.net и запусти NorthLoader.exe снова.",
                  debug
               );
               return;
            }
         }

         string java = Path.Combine(javaBin, debug ? "java.exe" : "javaw.exe");
         ProcessStartInfo start = new ProcessStartInfo {
            FileName = java,
            Arguments = "-jar \"" + jar + "\"",
            WorkingDirectory = install,
            UseShellExecute = false
         };

         log.WriteLine("[*] " + java + " -jar " + jar);
         Process process = Process.Start(start);
         if (process == null) {
            Fail("Не удалось запустить Java:\n" + java, debug);
            return;
         }

         if (debug) {
            process.WaitForExit();
            Console.WriteLine("[*] Код выхода: " + process.ExitCode);
            Console.WriteLine("Enter…");
            Console.ReadLine();
         }
      } catch (Exception exception) {
         Fail(exception.Message, debug);
      }
   }

   private static string EnsureJar(string install, TextWriter log) {
      string jar = Path.Combine(install, "north-loader.jar");
      string stampPath = Path.Combine(install, "loader.stamp");
      string stamp = ExeIdentity();

      if (File.Exists(jar) && File.Exists(stampPath) && File.ReadAllText(stampPath).Trim() == stamp) {
         log.WriteLine("[*] UI: " + jar);
         return jar;
      }

      using (Stream stream = Assembly.GetExecutingAssembly().GetManifestResourceStream(JarResource)) {
         if (stream == null) {
            string beside = Path.Combine(AppDomain.CurrentDomain.BaseDirectory, "north-loader.jar");
            if (!File.Exists(beside)) {
               throw new FileNotFoundException(
                  "Внутри NorthLoader.exe нет интерфейса. Пересобери через build-exe.bat."
               );
            }

            File.Copy(beside, jar, true);
         } else {
            string temporary = jar + ".tmp";
            using (FileStream output = File.Create(temporary)) {
               byte[] buffer = new byte[81920];
               int read;
               while ((read = stream.Read(buffer, 0, buffer.Length)) > 0) {
                  output.Write(buffer, 0, read);
               }
            }

            if (File.Exists(jar)) {
               File.Delete(jar);
            }

            File.Move(temporary, jar);
         }
      }

      File.WriteAllText(stampPath, stamp);
      log.WriteLine("[+] UI установлен: " + jar);
      return jar;
   }

   private static string ExeIdentity() {
      string exe = Assembly.GetExecutingAssembly().Location;
      FileInfo info = new FileInfo(exe);
      return info.Length + ":" + info.LastWriteTimeUtc.Ticks;
   }

   private static string FindJava(TextWriter log) {
      string home = Environment.GetEnvironmentVariable("JAVA_HOME");
      if (!string.IsNullOrWhiteSpace(home)) {
         string hit = Probe(Path.Combine(home.TrimEnd('\\', '/'), "bin"));
         if (hit != null) {
            log.WriteLine("[*] JAVA_HOME → " + hit);
            return hit;
         }
      }

      string user = Environment.GetFolderPath(Environment.SpecialFolder.UserProfile);
      string appData = Environment.GetFolderPath(Environment.SpecialFolder.ApplicationData);
      string[] roots = {
         Path.Combine(user, ".jdks"),
         Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.ProgramFiles), "Eclipse Adoptium"),
         Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.ProgramFiles), "Microsoft"),
         Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.ProgramFiles), "Java"),
         Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.ProgramFiles), "Amazon Corretto"),
         Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.ProgramFiles), "Zulu"),
         Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.ProgramFiles), "BellSoft"),
         Path.Combine(appData, "North", "runtime"),
         Path.Combine(appData, "North", "loader-runtime")
      };

      foreach (string root in roots) {
         string hit = SearchTree(root);
         if (hit != null) {
            log.WriteLine("[*] Java → " + hit);
            return hit;
         }
      }

      string path = Environment.GetEnvironmentVariable("PATH") ?? "";
      foreach (string entry in path.Split(new[] { Path.PathSeparator }, StringSplitOptions.RemoveEmptyEntries)) {
         string hit = Probe(entry.Trim().Trim('"'));
         if (hit != null) {
            log.WriteLine("[*] PATH → " + hit);
            return hit;
         }
      }

      return null;
   }

   private static string SearchTree(string root) {
      if (!Directory.Exists(root)) {
         return null;
      }

      string direct = Probe(Path.Combine(root, "bin"));
      if (direct != null) {
         return direct;
      }

      try {
         foreach (string dir in Directory.GetDirectories(root)) {
            string hit = Probe(Path.Combine(dir, "bin"));
            if (hit != null) {
               return hit;
            }

            foreach (string nested in Directory.GetDirectories(dir)) {
               hit = Probe(Path.Combine(nested, "bin"));
               if (hit != null) {
                  return hit;
               }
            }
         }
      } catch (IOException) {
      } catch (UnauthorizedAccessException) {
      }

      return null;
   }

   private static string Probe(string bin) {
      if (string.IsNullOrWhiteSpace(bin) || !File.Exists(Path.Combine(bin, "java.exe"))) {
         return null;
      }

      try {
         ProcessStartInfo psi = new ProcessStartInfo {
            FileName = Path.Combine(bin, "java.exe"),
            Arguments = "-version",
            UseShellExecute = false,
            RedirectStandardError = true,
            RedirectStandardOutput = true,
            CreateNoWindow = true
         };
         using (Process process = Process.Start(psi)) {
            if (process == null) {
               return null;
            }

            string output = process.StandardError.ReadToEnd() + process.StandardOutput.ReadToEnd();
            if (!process.WaitForExit(8000)) {
               try {
                  process.Kill();
               } catch {
               }

               return null;
            }

            Match match = Regex.Match(output, "version \"([^\"]+)\"", RegexOptions.IgnoreCase);
            if (!match.Success) {
               return null;
            }

            string version = match.Groups[1].Value;
            if (version.StartsWith("1.", StringComparison.Ordinal)) {
               return null;
            }

            string majorPart = version.Split('.', '-', '+', '_')[0];
            int major;
            if (!int.TryParse(majorPart, out major) || major < MinJava) {
               return null;
            }

            return Path.GetFullPath(bin);
         }
      } catch {
         return null;
      }
   }

   private static bool AskInstallJava(bool debug) {
      DialogResult result = MessageBox.Show(
         "Для North нужна Java 17 или новее.\n\n"
         + "Открыть страницу загрузки Eclipse Temurin (Adoptium)?\n"
         + "После установки запусти NorthLoader.exe ещё раз.",
         "NorthLoader",
         MessageBoxButtons.YesNo,
         MessageBoxIcon.Information
      );
      if (result != DialogResult.Yes) {
         if (debug) {
            Console.WriteLine("[!] Java не установлена, отмена.");
         }

         return false;
      }

      try {
         Process.Start(new ProcessStartInfo {
            FileName = JavaHelpUrl,
            UseShellExecute = true
         });
      } catch (Exception exception) {
         Fail("Не удалось открыть браузер:\n" + exception.Message + "\n\nОткрой сам:\n" + JavaHelpUrl, debug);
         return false;
      }

      MessageBox.Show(
         "Когда установщик Java закончит работу, нажми OK — проверю ещё раз.",
         "NorthLoader",
         MessageBoxButtons.OK,
         MessageBoxIcon.Information
      );
      return true;
   }

   private static void Fail(string message, bool console) {
      if (console) {
         Console.Error.WriteLine("[!] " + message);
         Console.WriteLine("Enter…");
         Console.ReadLine();
      } else {
         MessageBox.Show(message, "NorthLoader", MessageBoxButtons.OK, MessageBoxIcon.Error);
      }
   }

   [DllImport("kernel32.dll", SetLastError = true)]
   private static extern bool AllocConsole();
}
