using System;
using System.Diagnostics;
using System.IO;
using System.Runtime.InteropServices;
using System.Text.RegularExpressions;
using System.Windows.Forms;

/// <summary>
/// Native drop-in for run.bat: finds Java 17+, then starts low-free-loader.jar without a console.
/// Pass "debug" to keep a console and wait for the process.
/// </summary>
internal static class Program {
   [DllImport("kernel32.dll")]
   private static extern bool AllocConsole();

   [STAThread]
   private static void Main(string[] args) {
      string root = AppDomain.CurrentDomain.BaseDirectory;
      Directory.SetCurrentDirectory(root);

      bool debug = args.Length > 0 && string.Equals(args[0], "debug", StringComparison.OrdinalIgnoreCase);
      if (debug) {
         AllocConsole();
      }

      string javaBin = FindJava();
      if (javaBin == null) {
         Fail(
            "Не найдена Java 17 или новее.\n\n"
            + "Загрузчику нужна она — та Java, что стоит в PATH, слишком старая.\n"
            + "Поставь JDK 17+ или задай JAVA_HOME.",
            debug
         );
         return;
      }

      string jar = Path.Combine(root, "low-free-loader.jar");
      if (!File.Exists(jar)) {
         string build = Path.Combine(root, "build.bat");
         if (!File.Exists(build)) {
            Fail("Рядом нет low-free-loader.jar.", debug);
            return;
         }

         int code = Run(build, "", root, true);
         if (code != 0 || !File.Exists(jar)) {
            Fail("Не удалось собрать low-free-loader.jar (код " + code + ").", debug);
            return;
         }
      }

      string java = Path.Combine(javaBin, debug ? "java.exe" : "javaw.exe");
      if (!File.Exists(java)) {
         Fail("В " + javaBin + " нет " + Path.GetFileName(java), debug);
         return;
      }

      ProcessStartInfo psi = new ProcessStartInfo {
         FileName = java,
         Arguments = "-jar \"" + jar + "\"",
         WorkingDirectory = root,
         UseShellExecute = false
      };

      if (debug) {
         Process process = Process.Start(psi);
         if (process == null) {
            Fail("Не удалось запустить " + java, true);
            return;
         }

         process.WaitForExit();
         Console.WriteLine();
         Console.WriteLine("[*] Загрузчик завершился с кодом " + process.ExitCode + ".");
         Console.WriteLine("Нажми Enter…");
         Console.ReadLine();
      } else {
         Process.Start(psi);
      }
   }

   private static string FindJava() {
      string home = Environment.GetEnvironmentVariable("JAVA_HOME");
      if (!string.IsNullOrWhiteSpace(home)) {
         string hit = Probe(Path.Combine(home.TrimEnd('\\', '/'), "bin"));
         if (hit != null) {
            return hit;
         }
      }

      string user = Environment.GetFolderPath(Environment.SpecialFolder.UserProfile);
      string[] roots = {
         Path.Combine(user, ".jdks"),
         Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.ProgramFiles), "Eclipse Adoptium"),
         Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.ProgramFiles), "Java"),
         Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.ProgramFiles), "Microsoft"),
         Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.ProgramFiles), "Amazon Corretto"),
         Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.ProgramFiles), "Zulu"),
         Path.Combine(Environment.GetFolderPath(Environment.SpecialFolder.ProgramFiles), "BellSoft")
      };

      foreach (string root in roots) {
         if (!Directory.Exists(root)) {
            continue;
         }

         foreach (string dir in Directory.GetDirectories(root)) {
            string hit = Probe(Path.Combine(dir, "bin"));
            if (hit != null) {
               return hit;
            }
         }
      }

      string path = Environment.GetEnvironmentVariable("PATH") ?? "";
      foreach (string entry in path.Split(new[] { Path.PathSeparator }, StringSplitOptions.RemoveEmptyEntries)) {
         string hit = Probe(entry.Trim().Trim('"'));
         if (hit != null) {
            return hit;
         }
      }

      return null;
   }

   private static string Probe(string bin) {
      if (string.IsNullOrWhiteSpace(bin)) {
         return null;
      }

      string java = Path.Combine(bin, "java.exe");
      if (!File.Exists(java)) {
         return null;
      }

      try {
         ProcessStartInfo psi = new ProcessStartInfo {
            FileName = java,
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
            process.WaitForExit(8000);
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
            if (!int.TryParse(majorPart, out major) || major < 17) {
               return null;
            }

            return Path.GetFullPath(bin);
         }
      } catch {
         return null;
      }
   }

   private static int Run(string file, string arguments, string cwd, bool wait) {
      ProcessStartInfo psi = new ProcessStartInfo {
         FileName = file,
         Arguments = arguments,
         WorkingDirectory = cwd,
         UseShellExecute = true
      };
      using (Process process = Process.Start(psi)) {
         if (process == null) {
            return 1;
         }

         if (wait) {
            process.WaitForExit();
            return process.ExitCode;
         }

         return 0;
      }
   }

   private static void Fail(string message, bool console) {
      if (console) {
         Console.Error.WriteLine("[!] " + message);
         Console.WriteLine("Нажми Enter…");
         Console.ReadLine();
      } else {
         MessageBox.Show(message, "low free", MessageBoxButtons.OK, MessageBoxIcon.Error);
      }
   }
}
