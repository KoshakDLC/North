package wild.loader;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/** Development helper: renders the loader window to a PNG so the design can be reviewed. */
public final class Preview {
   public static void main(String[] args) throws Exception {
      String output = args.length > 0 ? args[0] : "preview.png";
      int page = args.length > 1 ? Integer.parseInt(args[1]) : 0;
      Loader loader = Loader.class.getDeclaredConstructor().newInstance();
      Method show = Loader.class.getDeclaredMethod("show");
      show.setAccessible(true);
      SwingUtilities.invokeAndWait(() -> {
         try {
            show.invoke(loader);
         } catch (Exception exception) {
            throw new RuntimeException(exception);
         }
      });
      Field frameField = Loader.class.getDeclaredField("frame");
      frameField.setAccessible(true);
      JFrame frame = (JFrame)frameField.get(loader);
      if (page != 0) {
         Method select = Loader.class.getDeclaredMethod("select", int.class);
         select.setAccessible(true);
         SwingUtilities.invokeAndWait(() -> {
            try {
               select.invoke(loader, page);
            } catch (Exception exception2) {
               throw new RuntimeException(exception2);
            }
         });
      }

      if (args.length > 2 && "busy".equals(args[2])) {
         Method begin = Loader.class.getDeclaredMethod("begin", String.class);
         begin.setAccessible(true);
         Method sinkMethod = Loader.class.getDeclaredMethod("sink", boolean.class);
         sinkMethod.setAccessible(true);
         SwingUtilities.invokeAndWait(() -> {
            try {
               begin.invoke(loader, "Запуск");
               Pipeline.Sink sink = (Pipeline.Sink)sinkMethod.invoke(loader, false);
               sink.log("Ключ подтверждён — lowfi.", Theme.OK);
               sink.log("Minecraft: C:\\Users\\lowfi\\AppData\\Roaming\\.minecraft", Theme.OK);
               sink.log("Fabric: fabric-loader-0.19.3-1.21.8", Theme.OK);
               sink.log("Удалена старая сборка wild-v0.jar", Theme.MUTED);
               sink.log("Установлен wild-v1.jar (4.2 МБ).", Theme.OK);
               sink.log("Fabric API не найден — проверь папку mods.", Theme.WARN);
               sink.progress(0.62, "Установка клиента");
            } catch (Exception exception3) {
               throw new RuntimeException(exception3);
            }
         });
      }

      Thread.sleep(2200L);
      BufferedImage image = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
      SwingUtilities.invokeAndWait(() -> {
         Graphics2D g2 = image.createGraphics();
         g2.setColor(new Color(0x2C, 0x2C, 0x36));
         g2.fillRect(0, 0, image.getWidth(), image.getHeight());
         frame.printAll(g2);
         g2.dispose();
      });
      ImageIO.write(image, "png", new File(output));
      System.out.println("saved " + output);
      System.exit(0);
   }
}
