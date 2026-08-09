package wild.loader;

import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice.WindowTranslucency;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Path;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

/** Entry point and window composition for the low free loader. */
public final class Loader {
   private static final int WIDTH = 1004;
   private static final int HEIGHT = 736;
   private static final int SIDEBAR = 250;
   private static final int HEADER = 54;
   private static final int GUTTER = 34;
   private static final String VERSION = "v1";
   private static final String GAME_VERSION = "1.21.8";

   private final Config config = Config.load();
   private final JFrame frame = new JFrame("low free");
   private final Root root = new Root();
   private final Widgets.Nav[] navs = new Widgets.Nav[3];
   private final JComponent[] pages = new JComponent[3];
   private final Widgets.Log log = new Widgets.Log();
   private final Widgets.Progress progress = new Widgets.Progress();
   private final Widgets.Chip status = new Widgets.Chip("ОФЛАЙН", Theme.MUTED);
   private Widgets.Btn launchButton;
   private Widgets.Field keyField;
   private String stage = "Готов к запуску";
   private int active = -1;
   private boolean running;

   public static void main(String[] args) {
      System.setProperty("sun.java2d.uiScale.enabled", "true");

      try {
         UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
      } catch (Exception exception) {
      }

      SwingUtilities.invokeLater(() -> new Loader().show());
   }

   private void show() {
      this.frame.setUndecorated(true);
      this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.frame.setSize(WIDTH, HEIGHT);
      this.frame.setMinimumSize(new Dimension(WIDTH, HEIGHT));
      this.frame.setLocationRelativeTo(null);
      this.frame.setIconImage(icon());
      if (GraphicsEnvironment.getLocalGraphicsEnvironment()
         .getDefaultScreenDevice()
         .isWindowTranslucencySupported(WindowTranslucency.PERPIXEL_TRANSLUCENT)) {
         this.frame.setBackground(new Color(0, 0, 0, 0));
      } else {
         this.frame.setShape(Theme.round(Root.MARGIN, Root.MARGIN, WIDTH - Root.MARGIN * 2.0, HEIGHT - Root.MARGIN * 2.0, Root.RADIUS));
      }

      this.root.setLayout(null);
      this.frame.setContentPane(this.root);
      this.buildHeader();
      this.buildSidebar();
      this.pages[0] = this.buildHome();
      this.pages[1] = this.buildSettings();
      this.pages[2] = this.buildAbout();

      for (JComponent page : this.pages) {
         page.setBounds(Root.MARGIN + SIDEBAR + GUTTER, Root.MARGIN + HEADER, contentWidth(), contentHeight());
         page.setVisible(false);
         this.root.add(page);
      }

      drag(this.root, this.frame);
      this.select(0);
      this.frame.setVisible(true);
      this.keyField.input().requestFocusInWindow();
      this.log.add("Загрузчик готов. Версия " + VERSION + " для Minecraft " + GAME_VERSION + ".", Theme.MUTED);
      this.log.add("Источник сборки: " + this.config.get(Config.REPO, Config.DEFAULT_REPO), Theme.MUTED);
   }

   private static int contentWidth() {
      return WIDTH - Root.MARGIN * 2 - SIDEBAR - GUTTER * 2;
   }

   private static int contentHeight() {
      return HEIGHT - Root.MARGIN * 2 - HEADER - 26;
   }

   private void buildHeader() {
      Widgets.IconBtn close = new Widgets.IconBtn(true, () -> System.exit(0));
      close.setBounds(WIDTH - Root.MARGIN - 42, Root.MARGIN + 16, 28, 28);
      this.root.add(close);
      Widgets.IconBtn minimize = new Widgets.IconBtn(false, () -> this.frame.setState(JFrame.ICONIFIED));
      minimize.setBounds(WIDTH - Root.MARGIN - 78, Root.MARGIN + 16, 28, 28);
      this.root.add(minimize);
   }

   private void buildSidebar() {
      Loader.Sidebar sidebar = new Loader.Sidebar();
      sidebar.setBounds(Root.MARGIN, Root.MARGIN, SIDEBAR, HEIGHT - Root.MARGIN * 2);
      sidebar.setLayout(null);
      Widgets.Brand brand = new Widgets.Brand();
      brand.setBounds(28, 32, 48, 48);
      sidebar.add(brand);
      String[] labels = {"Запуск", "Настройки", "О клиенте"};
      Widgets.Icon[] icons = {Widgets.Icon.PLAY, Widgets.Icon.GEAR, Widgets.Icon.INFO};

      for (int i = 0; i < labels.length; i++) {
         int index = i;
         Widgets.Nav nav = new Widgets.Nav(labels[i], icons[i], () -> this.select(index));
         nav.setBounds(16, 132 + i * 46, SIDEBAR - 32, 40);
         sidebar.add(nav);
         this.navs[i] = nav;
      }

      drag(sidebar, this.frame);
      this.root.add(sidebar);
   }

   private JComponent buildHome() {
      Loader.Page page = new Loader.Page() {
         @Override
         protected void paintComponent(Graphics g) {
            Graphics2D g2 = Theme.hq(g);
            Theme.text(g2, "С возвращением", 0.0, 30.0, Theme.font(Font.BOLD, 25.0F), Theme.TEXT);
            Theme.text(g2, "Проверим ключ, обновим сборку и откроем игру.", 0.0, 52.0, Theme.font(Font.PLAIN, 13.0F), Theme.MUTED);
            Theme.glass(g2, 0.0, 76.0, this.getWidth(), 118.0, 16.0, 1.0);
            Font stageFont = Theme.font(Font.BOLD, 12.0F);
            Theme.text(g2, Loader.this.stage, 2.0, 262.0, stageFont, Theme.mix(Theme.MUTED, Theme.TEXT, 0.5));
            String percent = Math.round(Loader.this.progressValue() * 100.0) + "%";
            Theme.text(g2, percent, this.getWidth() - Theme.width(g2, percent, stageFont) - 2.0, 262.0, stageFont, Theme.MUTED);
            Theme.text(g2, "ЖУРНАЛ", 2.0, 306.0, Theme.font(Font.BOLD, 10.5F), Theme.FAINT);
            g2.dispose();
         }
      };
      this.keyField = new Widgets.Field("Лицензионный ключ", "XXXX-XXXX-XXXX-XXXX", true);
      this.keyField.value(this.config.get(Config.KEY, ""));
      this.keyField.setBounds(20, 96, 372, 78);
      this.keyField.input().addActionListener(event -> this.launch());
      page.add(this.keyField);
      this.launchButton = new Widgets.Btn("ЗАПУСТИТЬ", true, this::launch);
      this.launchButton.setBounds(contentWidth() - 222, 122, 202, 46);
      page.add(this.launchButton);
      Widgets.Btn install = new Widgets.Btn("Только установить", false, this::installOnly);
      install.setBounds(0, 200, 176, 34);
      page.add(install);
      this.status.setBounds(contentWidth() - 148, 202, 148, 30);
      page.add(this.status);
      this.progress.setBounds(0, 272, contentWidth(), 8);
      page.add(this.progress);
      this.log.setBounds(0, 316, contentWidth(), contentHeight() - 316);
      page.add(this.log);
      this.refreshStatus();
      return page;
   }

   private JComponent buildSettings() {
      Loader.Page page = new Loader.Page();
      Widgets.Card paths = new Widgets.Card("Пути");
      paths.setBounds(0, 0, contentWidth(), 204);
      Widgets.Field mcDir = new Widgets.Field("Папка Minecraft", Config.defaultMinecraftDir().toString(), false);
      mcDir.value(this.config.get(Config.MC_DIR, ""));
      mcDir.setBounds(20, 40, contentWidth() - 40, 68);
      mcDir.setTrailing("Обзор", () -> this.browse(mcDir, true));
      bindSave(mcDir, Config.MC_DIR, this.config);
      paths.add(mcDir);
      Widgets.Field jar = new Widgets.Field("Джарник клиента", "авто: build/libs/wild-*.jar", false);
      jar.value(this.config.get(Config.JAR, ""));
      jar.setBounds(20, 118, contentWidth() - 40, 68);
      jar.setTrailing("Обзор", () -> this.browse(jar, false));
      bindSave(jar, Config.JAR, this.config);
      paths.add(jar);
      page.add(paths);
      Widgets.Card network = new Widgets.Card("Сеть");
      network.setBounds(0, 216, contentWidth(), 204);
      Widgets.Field repo = new Widgets.Field("Репозиторий с клиентом", Config.DEFAULT_REPO, false);
      repo.value(this.config.get(Config.REPO, Config.DEFAULT_REPO));
      repo.setBounds(20, 40, contentWidth() - 40, 68);
      bindSave(repo, Config.REPO, this.config);
      network.add(repo);
      Widgets.Field api = new Widgets.Field("Сервер лицензий", "пусто — офлайн-режим", false);
      api.value(this.config.get(Config.API, ""));
      api.setBounds(20, 118, contentWidth() - 40, 68);
      bindSave(api, Config.API, this.config, this::refreshStatus);
      network.add(api);
      page.add(network);
      Widgets.Card options = new Widgets.Card("Параметры");
      options.setBounds(0, 432, contentWidth(), 180);
      Widgets.Slider ram = new Widgets.Slider("Память для игры", " ГБ", 2, 16, 1, this.config.getInt(Config.RAM, 4));
      ram.setBounds(20, 38, contentWidth() - 40, 40);
      ram.onChange(() -> {
         this.config.set(Config.RAM, Integer.toString(ram.value()));
         this.config.save();
      });
      options.add(ram);
      Widgets.Toggle autoInstall = new Widgets.Toggle("Обновлять клиент при запуске", "Копирует свежий джарник в mods", this.config.getBoolean(Config.AUTO_INSTALL, true));
      autoInstall.setBounds(20, 88, contentWidth() - 40, 40);
      autoInstall.onChange(() -> {
         this.config.setBoolean(Config.AUTO_INSTALL, autoInstall.value());
         this.config.save();
      });
      options.add(autoInstall);
      Widgets.Toggle closeOnLaunch = new Widgets.Toggle("Закрывать загрузчик после старта", "Через пару секунд после запуска игры", this.config.getBoolean(Config.CLOSE_ON_LAUNCH, false));
      closeOnLaunch.setBounds(20, 130, contentWidth() - 40, 40);
      closeOnLaunch.onChange(() -> {
         this.config.setBoolean(Config.CLOSE_ON_LAUNCH, closeOnLaunch.value());
         this.config.save();
      });
      options.add(closeOnLaunch);
      page.add(options);
      return page;
   }

   private JComponent buildAbout() {
      Loader.Page page = new Loader.Page() {
         @Override
         protected void paintComponent(Graphics g) {
            Graphics2D g2 = Theme.hq(g);
            Theme.glass(g2, 0.0, 0.0, this.getWidth(), 208.0, 16.0, 1.0);
            Theme.text(g2, "low free", 28.0, 56.0, Theme.font(Font.BOLD, 26.0F), Theme.TEXT);
            Theme.text(g2, VERSION + " · Minecraft " + GAME_VERSION + " · Fabric", 28.0, 80.0, Theme.font(Font.PLAIN, 13.0F), Theme.ACCENT);
            Font font = Theme.font(Font.PLAIN, 12.5F);
            String[] lines = {
               "Загрузчик проверяет ключ, ставит свежую сборку в папку mods",
               "и открывает лаунчер с профилем Fabric.",
               "",
               "HWID: " + Config.hardwareId(),
               "Настройки: " + Config.appData().resolve("low free").resolve("loader.properties")
            };
            double y = 118.0;

            for (String line : lines) {
               Theme.text(g2, Theme.ellipsize(g2, line, font, this.getWidth() - 56.0), 28.0, y, font, Theme.MUTED);
               y += 21.0;
            }

            double cardHeight = this.getHeight() - 226.0;
            Theme.glass(g2, 0.0, 226.0, this.getWidth(), cardHeight, 16.0, 1.0);
            Theme.text(g2, "ЧТО ПРОИСХОДИТ ПРИ ЗАПУСКЕ", 28.0, 258.0, Theme.font(Font.BOLD, 11.0F), Theme.MUTED);
            String[] steps = {
               "Проверка лицензионного ключа",
               "Поиск папки Minecraft",
               "Проверка установленного Fabric " + GAME_VERSION,
               "Скачивание свежей сборки с GitHub",
               "Установка джарника в mods и запуск лаунчера"
            };
            double spacing = Theme.clamp((cardHeight - 96.0) / steps.length, 38.0, 56.0);
            double stepY = 292.0;

            for (int i = 0; i < steps.length; i++) {
               double circle = 22.0;
               Theme.fill(g2, 28.0, stepY, circle, circle, circle / 2.0, Theme.alpha(Theme.ACCENT, 0.16));
               Theme.stroke(g2, 28.0, stepY, circle, circle, circle / 2.0, Theme.alpha(Theme.ACCENT, 0.4), 1.0);
               Font number = Theme.font(Font.BOLD, 11.5F);
               Theme.textCentered(g2, Integer.toString(i + 1), 28.0 + circle / 2.0, Theme.baseline(g2, stepY, circle, number), number, Theme.ACCENT);
               Theme.text(g2, steps[i], 62.0, Theme.baseline(g2, stepY, circle, font), font, Theme.mix(Theme.MUTED, Theme.TEXT, 0.35));
               stepY += spacing;
            }

            g2.dispose();
         }
      };
      return page;
   }

   private double progressValue() {
      return this.progressFraction;
   }

   private double progressFraction;

   private void select(int index) {
      if (this.active != index) {
         this.active = index;

         for (int i = 0; i < this.pages.length; i++) {
            this.navs[i].setActive(i == index);
            boolean visible = i == index;
            this.pages[i].setVisible(visible);
            if (visible) {
               ((Loader.Page)this.pages[i]).enter();
            }
         }

         this.root.repaint();
      }
   }

   private void launch() {
      if (!this.running) {
         String key = this.keyField.value();
         this.config.set(Config.KEY, key);
         this.config.save();
         this.begin("Запуск");
         new Pipeline(this.config, this.sink(true)).start(key);
      }
   }

   private void installOnly() {
      if (!this.running) {
         this.begin("Установка");
         this.log.add("Установка без запуска игры.", Theme.MUTED);
         new Pipeline(this.config, this.sink(false)).start(this.keyField.value());
      }
   }

   private void begin(String title) {
      this.running = true;
      this.launchButton.setEnabledState(false);
      this.launchButton.setBusy(true);
      this.launchButton.setText(title.toUpperCase());
      this.progress.reset();
      this.select(0);
   }

   private Pipeline.Sink sink(boolean closeAfter) {
      return new Pipeline.Sink() {
         @Override
         public void log(String message, Color color) {
            SwingUtilities.invokeLater(() -> Loader.this.log.add(message, color));
         }

         @Override
         public void progress(double fraction, String stage) {
            SwingUtilities.invokeLater(() -> {
               Loader.this.progressFraction = fraction;
               Loader.this.stage = stage;
               Loader.this.progress.set(fraction, fraction < 1.0);
               Loader.this.pages[0].repaint();
            });
         }

         @Override
         public void finished(boolean success, String message) {
            SwingUtilities.invokeLater(() -> {
               Loader.this.running = false;
               Loader.this.launchButton.setBusy(false);
               Loader.this.launchButton.setEnabledState(true);
               Loader.this.launchButton.setText("ЗАПУСТИТЬ");
               Loader.this.stage = message;
               if (!success) {
                  Loader.this.progressFraction = 0.0;
                  Loader.this.progress.set(0.0, false);
               } else {
                  Loader.this.progress.set(1.0, false);
               }

               Loader.this.log.add(message, success ? Theme.OK : Theme.BAD);
               Loader.this.pages[0].repaint();
               if (success && closeAfter && Loader.this.config.getBoolean(Config.CLOSE_ON_LAUNCH, false)) {
                  javax.swing.Timer timer = new javax.swing.Timer(2500, event -> System.exit(0));
                  timer.setRepeats(false);
                  timer.start();
               }
            });
         }
      };
   }

   private void refreshStatus() {
      boolean online = !this.config.get(Config.API, "").isEmpty();
      this.status.set(online ? "СЕРВЕР ЛИЦЕНЗИЙ" : "ОФЛАЙН-РЕЖИМ", online ? Theme.OK : Theme.WARN);
   }

   private void browse(Widgets.Field field, boolean directory) {
      JFileChooser chooser = new JFileChooser();
      chooser.setFileSelectionMode(directory ? JFileChooser.DIRECTORIES_ONLY : JFileChooser.FILES_ONLY);
      chooser.setDialogTitle(directory ? "Папка Minecraft" : "Джарник клиента");
      String current = field.value();
      if (!current.isEmpty()) {
         File file = new File(current);
         chooser.setCurrentDirectory(directory ? file : file.getParentFile());
      }

      if (chooser.showOpenDialog(this.frame) == JFileChooser.APPROVE_OPTION) {
         field.value(chooser.getSelectedFile().getAbsolutePath());
         field.repaint();
      }
   }

   private static void bindSave(Widgets.Field field, String key, Config config) {
      bindSave(field, key, config, null);
   }

   private static void bindSave(Widgets.Field field, String key, Config config, Runnable after) {
      field.input().addFocusListener(new java.awt.event.FocusAdapter() {
         @Override
         public void focusLost(java.awt.event.FocusEvent event) {
            config.set(key, field.value());
            config.save();
            if (after != null) {
               after.run();
            }
         }
      });
      field.input().addActionListener(event -> {
         config.set(key, field.value());
         config.save();
         if (after != null) {
            after.run();
         }
      });
   }

   /** Lets the frame be moved by dragging empty areas of the given component. */
   private static void drag(JComponent component, JFrame frame) {
      Point[] origin = new Point[1];
      MouseAdapter adapter = new MouseAdapter() {
         @Override
         public void mousePressed(MouseEvent event) {
            origin[0] = event.getPoint();
         }

         @Override
         public void mouseReleased(MouseEvent event) {
            origin[0] = null;
         }

         @Override
         public void mouseDragged(MouseEvent event) {
            if (origin[0] != null) {
               Point onScreen = event.getLocationOnScreen();
               Point offset = SwingUtilities.convertPoint(component, origin[0], frame);
               frame.setLocation(onScreen.x - offset.x, onScreen.y - offset.y);
            }
         }
      };
      component.addMouseListener(adapter);
      component.addMouseMotionListener(adapter);
   }

   private static BufferedImage icon() {
      int size = 128;
      BufferedImage image = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
      Graphics2D g2 = Theme.hq(image.createGraphics());
      Theme.fill(g2, 0.0, 0.0, size, size, size * 0.3, Theme.brand(0.0, size));
      Font font = Theme.font(Font.BOLD, size * 0.58F);
      Theme.textCentered(g2, "L", size / 2.0, Theme.baseline(g2, 0.0, size, font), font, new Color(0x18, 0x12, 0x2C));
      g2.dispose();
      return image;
   }

   /** Sidebar surface: a slightly lighter column with a hairline on its right edge. */
   private static final class Sidebar extends JComponent {
      @Override
      protected void paintComponent(Graphics g) {
         Graphics2D g2 = Theme.hq(g);
         double width = this.getWidth();
         double height = this.getHeight();
         Graphics2D clip = (Graphics2D)g2.create();
         clip.clip(Theme.round(0.0, 0.0, width * 2.0, height, Root.RADIUS));
         Theme.fill(clip, 0.0, 0.0, width, height, 0.0, Theme.vertical(0.0, height, Theme.alpha(Color.BLACK, 0.28), Theme.alpha(Color.BLACK, 0.16)));
         Theme.fill(clip, 0.0, 0.0, width, height, 0.0, Theme.alpha(Theme.WHITE, 0.022));
         clip.dispose();
         Theme.fill(g2, width - 1.0, 12.0, 1.0, height - 24.0, 0.5, Theme.alpha(Theme.WHITE, 0.07));
         Theme.text(g2, "low free", 86.0, 54.0, Theme.font(Font.BOLD, 19.0F), Theme.TEXT);
         Theme.text(g2, VERSION + " · " + GAME_VERSION, 86.0, 72.0, Theme.font(Font.PLAIN, 11.5F), Theme.FAINT);
         Font font = Theme.font(Font.PLAIN, 11.0F);
         Theme.text(g2, "HWID " + Config.hardwareId(), 28.0, height - 34.0, font, Theme.FAINT);
         g2.dispose();
      }
   }

   /** Page container that fades and lifts into place when it becomes active. */
   private static class Page extends JComponent {
      private final Anim.Val in;

      Page() {
         this.setLayout(null);
         this.in = Anim.attach(this, 1.0, 0.16);
      }

      void enter() {
         this.in.set(0.0);
         this.in.to(1.0);
      }

      @Override
      public void paint(Graphics g) {
         double t = Theme.easeOut(this.in.get());
         Graphics2D g2 = (Graphics2D)g.create();
         g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)Theme.clamp(t, 0.0, 1.0)));
         g2.translate(0.0, (1.0 - t) * 12.0);
         super.paint(g2);
         g2.dispose();
      }
   }
}
