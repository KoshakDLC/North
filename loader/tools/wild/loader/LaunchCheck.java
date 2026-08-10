package wild.loader;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Development helper: installs the game into a folder of your choosing and prints the command line
 * that would start it, without starting anything. The first run downloads the whole game, so give
 * it a scratch folder and some time.
 */
public final class LaunchCheck {
   public static void main(String[] args) throws Exception {
      Path root = Paths.get(args.length > 0 ? args[0] : System.getProperty("java.io.tmpdir") + "/wild-launch-check");
      String version = args.length > 1 ? args[1] : Pipeline.MINECRAFT_VERSION;
      Game game = new Game(root, version, new Game.Report() {
         @Override
         public void info(String message) {
            System.out.println("  " + message);
         }

         @Override
         public void ok(String message) {
            System.out.println("+ " + message);
         }

         @Override
         public void warn(String message) {
            System.out.println("! " + message);
         }

         @Override
         public void step(double fraction, String stage) {
            System.out.println(String.format("[%3d%%] %s", Math.round(fraction * 100.0), stage));
         }
      });
      game.install();
      System.out.println();

      for (String piece : game.command("Player", 4)) {
         System.out.println(piece);
      }
   }
}
