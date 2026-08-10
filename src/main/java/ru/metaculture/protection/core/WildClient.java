package ru.metaculture.protection;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Supplier;
import java.util.stream.Stream;
import lombok.Generated;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.resource.IdentifiableResourceReloadListener;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourceManager;
import net.minecraft.resource.ResourceType;
import net.minecraft.resource.ResourceReloader.Synchronizer;
import net.minecraft.util.Identifier;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Configurator;
import org.lwjgl.glfw.GLFW;
import org.wild.module.api.Module;
import ru.metaculture.profile.Profile;
import ru.metaculture.sdk.NotCompile;

public class WildClient implements ClientModInitializer {
   public static WildClient INSTANCE;
   public ModuleManager moduleManager;
   private static final File FILE = new File(
      System.getProperty(
         "wild.root",
         new File(net.fabricmc.loader.api.FabricLoader.getInstance().getGameDir().toFile(), "instance_files/wild").getPath()
      )
   );
   public final String wild = "North";
   public final String v1 = "v1";
   public final String text1218 = "1.21.8";
   public final File file = FILE;
   public final File file2 = this.file;
   public final String wild2 = "wild";
   public boolean flag = false;
   public static String text = null;
   public ThemeManager themeManager;
   public ListenerRegistry listenerRegistry;
   public ConfigManager configManager;
   public FriendCommand friendCommand;
   public ClickGuiScreen clickGuiScreen;
   public ModernClickGuiScreen modernClickGuiScreen;
   private final DiscordRpcManager discordRpcManager = new DiscordRpcManager();
   private CommandManager commandManager;
   private IrcWebSocketClient ircWebSocketClient;
   private static RenderEngine renderEngine;
   private static RenderManager renderManager;
   private static FontObject fontObject;
   static volatile boolean flag2 = false;
   private static volatile boolean flag3 = false;
   private static volatile boolean flag4 = false;
   private static volatile Thread thread;
   private static volatile boolean flag5 = false;
   private static int intValue = -1;
   private static int intValue2 = -1;
   private static volatile boolean flag6 = false;
   private String commandPrefix = ".";

   public static RenderManager resolve() {
      invoke15();
      return renderManager;
   }

   public static File getFILE() {
      return FILE;
   }

   public static RenderEngine resolve2() {
      invoke15();
      return renderEngine;
   }

   public static void invoke(int i, int j) {
      intValue = i;
      intValue2 = j;
      AnimationSystem.getINSTANCE().invoke2();
      FramebufferPool.invoke2();
      if (renderEngine != null) {
         renderEngine.invoke61(i, j);
      }

      EntityFramebufferCapture.getInstance().onFramebufferResize(i, j);
      BlurRenderer.getINSTANCE().invoke7(i, j);
      ScreenTransitionController.getINSTANCE().invoke4(i, j);
      ScreenTransitionRenderer.getINSTANCE().invoke4(i, j);
      WorldAtmosphereRenderer.getINSTANCE().invoke2(i, j);
      MotionBlurRenderer.getINSTANCE().invoke2(i, j);

      try {
         ClickGuiHolographicBlurShader.resolve().invoke4(i, j);
      } catch (Throwable exception) {
      }

      try {
         MidnightAzureMenuShader.getINSTANCE().invoke4(i, j);
      } catch (Throwable exception2) {
      }

      try {
         ThemeShaderProgramCache.getINSTANCE().invoke3();
      } catch (Throwable exception3) {
      }

      try {
         ColorPickerShader.invoke2();
      } catch (Throwable exception4) {
      }

      try {
         if (MinecraftAccessor.a_ != null && MinecraftAccessor.a_.currentScreen instanceof MainMenuScreen mainMenuScreen) {
            mainMenuScreen.invoke4(i, j);
         }
      } catch (Throwable exception5) {
      }

      if (INSTANCE != null && INSTANCE.modernClickGuiScreen != null) {
         INSTANCE.modernClickGuiScreen.invoke2(i, j);
      }
   }

   @NotCompile
   public static void invoke2(boolean bl) {
      AnimationSystem.getINSTANCE().invoke2();
      if (!bl) {
         if (renderEngine != null) {
            renderEngine.invoke61(0, 0);
         }

         EntityFramebufferCapture.getInstance().onFramebufferResize(0, 0);
      } else if (MinecraftAccessor.a_ != null && MinecraftAccessor.a_.getWindow() != null) {
         invoke(MinecraftAccessor.a_.getWindow().getFramebufferWidth(), MinecraftAccessor.a_.getWindow().getFramebufferHeight());
      }

      BlurRenderer.getINSTANCE().invoke8(bl);
      ScreenTransitionController.getINSTANCE().invoke5(bl);
      ScreenTransitionRenderer.getINSTANCE().invoke5(bl);
      if (INSTANCE != null && INSTANCE.modernClickGuiScreen != null) {
         INSTANCE.modernClickGuiScreen.invoke3(bl);
      }
   }

   @NotCompile
   public void onInitializeClient() {
      INSTANCE = this;
      invoke6(this::invoke3);
      invoke6(StardustParticleRegistry::invoke);
      invoke6(this::invoke9);
      invoke6((Runnable)(() -> text = Profile.getUsername()));
      this.moduleManager = resolve3(ModuleManager::new);
      if (this.moduleManager != null) {
         invoke6((Runnable)(() -> EventManager.register(this.moduleManager)));
      }

      this.friendCommand = resolve3(FriendCommand::new);
      this.configManager = resolve3(ConfigManager::new);
      this.themeManager = resolve3(ThemeManager::new);
      this.listenerRegistry = resolve3(ListenerRegistry::new);
      if (this.listenerRegistry != null) {
         invoke6((Runnable)(this.listenerRegistry::invoke));
      }

      if (this.themeManager != null) {
         invoke6((Runnable)(this.themeManager::invoke));
      }

      invoke6((Runnable)(() -> AutoLoginManager.invoke2(MinecraftClient.getInstance())));
      invoke6((Runnable)(() -> ProxyManager.invoke()));
      invoke6((Runnable)(() -> DefaultServerRegistrar.invoke(MinecraftClient.getInstance())));
      invoke6((Runnable)(() -> EventManager.register(TpsTracker.class)));
      invoke6(this::invoke17);
      if (this.themeManager != null) {
         invoke6((Runnable)(() -> {
            LegacyClickGuiState.theme = this.themeManager.getTheme();
            LegacyClickGuiState.theme2 = this.themeManager.getTheme();
            LegacyClickGuiState.category = this.themeManager.getCategory();
         }));
      }

      invoke6(this.discordRpcManager::invoke);
      invoke6((Runnable)(() -> KeyNameResolver.getINSTANCE().invoke()));
      if (this.configManager != null) {
         invoke6((Runnable)(() -> {
            this.configManager.invoke();
            if (this.configManager.resolve4("default") != null) {
               this.configManager.check("default");
            }
         }));
      }

      invoke6((Runnable)(ClientUserRegistry::start));
      invoke6((Runnable)(ru.metaculture.protection.HudPresetManager::invoke));
      invoke6(this::invoke10);
      invoke6((Runnable)(() -> {
         RenderManager.booleanSupplier = MenuVisualPreferences::check;
         RenderManager.booleanSupplier2 = MenuVisualPreferences::check2;
      }));
      invoke6((Runnable)(() -> Runtime.getRuntime().addShutdownHook(new Thread(WildClient::invoke11, "Wild-Client-Shutdown"))));
      this.clickGuiScreen = resolve3(ClickGuiScreen::new);
      this.modernClickGuiScreen = resolve3(ModernClickGuiScreen::new);
      invoke6((Runnable)(() -> BlurRenderer.getINSTANCE().invoke()));
      invoke6((Runnable)(ClickGuiEventBridge::invoke));
      invoke6((Runnable)(() -> EventManager.register(this)));
      invoke6(this::invoke7);
      invoke6(this::invoke8);
      if (this.moduleManager != null && this.configManager != null && this.themeManager != null && this.listenerRegistry != null) {
         flag3 = true;
         ProtectionHandler.checkAccess();
      }
   }

   public ModernClickGuiScreen getModernClickGui() {
      if (this.modernClickGuiScreen == null) {
         this.modernClickGuiScreen = resolve3(ModernClickGuiScreen::new);
      }

      return this.modernClickGuiScreen;
   }

   private static <T> T resolve3(Supplier<T> supplier) {
      try {
         return (T)supplier.get();
      } catch (GuardException guardException) {
         throw ProtectionHandler.resolve(guardException);
      } catch (Throwable exception6) {
         System.out.println("[Client] init failed: " + exception6.getClass().getSimpleName() + ": " + exception6.getMessage());
         return null;
      }
   }

   @NotCompile
   private void invoke3() {
      if (!FILE.exists() && !FILE.mkdirs()) {
         System.out.println("[Client] cannot create root directory: " + FILE.getAbsolutePath());
      } else {
         File file = new File(FabricLoader.getInstance().getGameDir().toFile(), "Wild");
         invoke4(file.toPath(), FILE.toPath());
      }
   }

   private static void invoke4(Path path, Path path2) {
      try {
         if (path == null || path2 == null || !Files.isDirectory(path) || Files.isSameFile(path, path2)) {
            return;
         }
      } catch (IOException ioException) {
         return;
      }

      try (Stream stream = Files.walk(path)) {
         stream.forEach(path3 -> {
            try {
               Path path4 = path.relativize((Path)path3);
               Path path5 = path2.resolve(path4);
               if (Files.isDirectory((Path)path3)) {
                  Files.createDirectories(path5);
               } else if (!Files.exists(path5)) {
                  Path path6 = path5.getParent();
                  if (path6 != null) {
                     Files.createDirectories(path6);
                  }

                  Files.copy((Path)path3, path5, StandardCopyOption.COPY_ATTRIBUTES);
               }
            } catch (Throwable exception7) {
            }
         });
      } catch (Throwable exception8) {
      }
   }

   public static void invoke5(MinecraftClient minecraftClient) {
      if (minecraftClient != null && minecraftClient.getWindow() != null && GLFW.glfwGetCurrentContext() != 0L) {
         int intValue = minecraftClient.getWindow().getFramebufferWidth();
         int intValue2 = minecraftClient.getWindow().getFramebufferHeight();
         if (!minecraftClient.getWindow().hasZeroWidthOrHeight() && intValue > 0 && intValue2 > 0) {
            try {
               invoke15();
            } catch (Throwable exception9) {
               return;
            }

            if (intValue != intValue || intValue2 != intValue2) {
               invoke(intValue, intValue2);
            }
         }
      }
   }

   private static void invoke6(Runnable runnable) {
      try {
         runnable.run();
      } catch (GuardException guardException2) {
         throw ProtectionHandler.resolve(guardException2);
      } catch (Throwable exception10) {
      }
   }

   @NotCompile
   private void invoke7() {
      try {
         ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new IdentifiableResourceReloadListener() {
            private final Identifier fabricId = Identifier.of("wild", "font_reload");

            public Identifier getFabricId() {
               return this.fabricId;
            }

            public CompletableFuture<Void> reload(Synchronizer synchronizer, ResourceManager resourceManager, Executor executor, Executor executor2) {
               return CompletableFuture.completedFuture(null).<Object>thenCompose(synchronizer::whenPrepared).thenAcceptAsync(object -> {
                  MinecraftClient client = MinecraftClient.getInstance();
                  if (client != null) {
                     client.execute(() -> {
                        try {
                           if (WildClient.flag2) {
                              FontRegistry.invoke2();
                           }
                        } catch (Throwable var1x) {
                        }
                     });
                  }
               }, executor2);
            }
         });
      } catch (Throwable exception11) {
      }
   }

   @NotCompile
   private void invoke8() {
      try {
         ResourceManagerHelper.get(ResourceType.CLIENT_RESOURCES).registerReloadListener(new IdentifiableResourceReloadListener() {
            private final Identifier fabricId = Identifier.of("wild", "theme_shader_reload");

            public Identifier getFabricId() {
               return this.fabricId;
            }

            public CompletableFuture<Void> reload(Synchronizer synchronizer, ResourceManager resourceManager, Executor executor, Executor executor2) {
               return CompletableFuture.completedFuture(null).<Object>thenCompose(synchronizer::whenPrepared).thenAcceptAsync(object -> {
                  MinecraftClient client2 = MinecraftClient.getInstance();
                  if (client2 != null) {
                     client2.execute(() -> {
                        try {
                           ThemeShaderProgramCache.getINSTANCE().invoke3();
                        } catch (Throwable exception12) {
                        }

                        try {
                           ColorPickerShader.invoke2();
                        } catch (Throwable exception13) {
                        }

                        try {
                           WorldAtmosphereRenderer.getINSTANCE().close();
                        } catch (Throwable exception14) {
                        }

                        try {
                           MotionBlurRenderer.getINSTANCE().close();
                        } catch (Throwable exception15) {
                        }

                        try {
                           ClickGuiHolographicBlurShader.resolve().invoke4(0, 0);
                        } catch (Throwable var2x) {
                        }

                        try {
                           MidnightAzureMenuShader.getINSTANCE().invoke4(0, 0);
                        } catch (Throwable var1x) {
                        }
                     });
                  }
               }, executor2);
            }
         });
      } catch (Throwable exception16) {
      }
   }

   @NotCompile
   private void invoke9() {
      String text = System.getProperty("wild.loader.pid");
      if (text == null || text.isBlank()) {
         text = System.getenv("WILD_LOADER_PID");
      }

      if (text != null && !text.isBlank()) {
         try {
            long longValue = Long.parseLong(text.trim());
            long longValue2 = ProcessHandle.current().pid();
            if (longValue <= 0L || longValue == longValue2) {
               return;
            }

            ProcessHandle.of(longValue).ifPresent(processHandle -> {
               if (processHandle.isAlive()) {
                  processHandle.destroy();
               }
            });
         } catch (Throwable exception17) {
         }
      }
   }

   @NotCompile
   private void invoke10() {
      Configurator.setLevel("com.mojang.authlib.yggdrasil.YggdrasilServicesKeyInfo", Level.OFF);
      Configurator.setLevel("net.minecraft.client.texture.PlayerSkinProvider", Level.ERROR);
      Configurator.setLevel("net.minecraft.client.network.ClientPlayNetworkHandler", Level.ERROR);
      Configurator.setLevel("net.minecraft.client.world.ClientChunkManager", Level.ERROR);
      Configurator.setLevel("net.minecraft.block.entity.BlockEntity", Level.ERROR);
   }

   @NotCompile
   public static void invoke11() {
      if (!flag5) {
         flag5 = true;
         System.out.println("[North] shutdown: begin");
         invoke12();
         invoke6((Runnable)(() -> {
            if (INSTANCE != null && INSTANCE.configManager != null) {
               INSTANCE.configManager.invoke2();
            }
         }));
         invoke6(WildClient::invoke13);
         invoke6(DiscordRpcManager::invoke2);
         invoke6(IrcWebSocketClient::invoke);
         invoke6((Runnable)(() -> {
            Thread workerThread = thread;
            thread = null;
            flag4 = false;
            if (workerThread != null) {
               workerThread.interrupt();
            }
         }));
         invoke6((Runnable)(SessionManager::invoke2));
         invoke6((Runnable)(ClientUserRegistry::stop));
         invoke6(TpsTracker::invoke6);
         invoke6(AutoBuy::invoke13);
         invoke6(AiRotationTrainer::invoke12);
         invoke6(NeuroRotationController::invoke5);
         invoke6(HeartbeatClient::invoke2);
         invoke6(MusicPlayerHud::invoke2);
         invoke6(AiRotationCommand::invoke);
         invoke6(CloudConfigService::invoke);
         invoke6(ru.metaculture.protection.FuseAudioEffect::invoke3);
         invoke6((Runnable)(SoundUtils::invoke));
         invoke6(HitSounds::invoke);
         invoke6((Runnable)(ColorUtils::invoke));
         invoke6((Runnable)(() -> WorldAtmosphereRenderer.getINSTANCE().close()));
         invoke6((Runnable)(() -> MotionBlurRenderer.getINSTANCE().close()));
         invoke6(ThemeShaderProgramCache.getINSTANCE()::invoke3);
         invoke6((Runnable)(() -> FramebufferPool.invoke2()));
         System.out.println("[North] shutdown: done");
      }
   }

   private static void invoke12() {
      if (!flag6) {
         flag6 = true;
         Thread thread2 = new Thread(() -> {
            try {
               Thread.sleep(4000L);
            } catch (InterruptedException interruptedException) {
               Thread.currentThread().interrupt();
            }

            System.out.println("[North] shutdown: force-exit failsafe -> halt(0)");
            Runtime.getRuntime();
            boolean flag = false;
         }, "Wild-ForceExit-Watchdog");
         thread2.setDaemon(true);
         thread2.setPriority(10);
         thread2.start();
      }
   }

   private static void invoke13() {
      if (INSTANCE != null && INSTANCE.moduleManager != null && INSTANCE.moduleManager.modules != null) {
         for (Module module : INSTANCE.moduleManager.modules) {
            if (module != null && module.enabled) {
               try {
                  module.enabled = false;
                  module.onDisable();
               } catch (Throwable exception18) {
               }
            }
         }
      }
   }

   public static void invoke14() {
      if (!flag4) {
         synchronized (WildClient.class) {
            if (flag4) {
               return;
            }

            flag4 = true;
         }

         Thread thread3 = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
               try {
                  double doubleValue = TpsTracker.getDoubleValue();
                  if (doubleValue <= 0.0) {
                     doubleValue = 20.0;
                  }

                  double doubleValue2 = 1.0 / doubleValue;
                  long longValue3 = (long)(doubleValue2 * 1000.0);
                  MinecraftClient client3 = MinecraftClient.getInstance();
                  if (client3 != null && !client3.isOnThread()) {
                     client3.execute(() -> EventManager.post((Event)(new TpsPulseEvent())));
                  } else {
                     EventManager.post((Event)(new TpsPulseEvent()));
                  }

                  Thread.sleep(Math.max(longValue3, 1L));
               } catch (GuardException guardException3) {
                  throw ProtectionHandler.resolve(guardException3);
               } catch (InterruptedException interruptedException2) {
                  Thread.currentThread().interrupt();
                  break;
               } catch (Throwable exception19) {
                  try {
                     Thread.sleep(100L);
                  } catch (InterruptedException interruptedException3) {
                     Thread.currentThread().interrupt();
                     break;
                  }
               }
            }
         }, "TPS");
         thread3.setDaemon(true);
         thread = thread3;
         thread3.start();
      }
   }

   public static void invoke15() {
      if (!flag2) {
         invoke16();
      }
   }

   private static synchronized void invoke16() {
      if (!flag2) {
         if (GLFW.glfwGetCurrentContext() != 0L) {
            renderEngine = new RenderEngine();
            renderManager = new RenderManager(renderEngine);
            FontRegistry.invoke(renderEngine, renderManager);
            fontObject = FontRegistry.fontObject;
            flag2 = true;
         }
      }
   }

   @NotCompile
   public void invoke17() {
      this.commandManager = new CommandManager();
   }

   public static void invoke18() {
      ProtectionHandler.checkAccess();
      if (flag3) {
         FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();

         try {
            if (MinecraftAccessor.a_ == null || MinecraftAccessor.a_.getWindow() == null) {
               return;
            }

            int intValue3 = MinecraftAccessor.a_.getWindow().getFramebufferWidth();
            int intValue4 = MinecraftAccessor.a_.getWindow().getFramebufferHeight();
            if (intValue3 <= 0 || intValue4 <= 0) {
               invoke(intValue3, intValue4);
               return;
            }

            try {
               invoke15();
            } catch (Throwable exception20) {
               return;
            }

            AnimationSystem.getINSTANCE().invoke();
            HudEditorRenderer hudEditorRenderer = HudEditorRenderer.getINSTANCE();
            hudEditorRenderer.invoke2(MinecraftAccessor.a_, renderManager, intValue3, intValue4);
            boolean flag2 = false;

            try {
               renderManager.invoke(intValue3, intValue4);
               flag2 = true;
               EventManager.post((Event)(new HudFrameEvent(MinecraftAccessor.a_, renderManager, fontObject, intValue3, intValue4)));
            } catch (Throwable exception21) {
            } finally {
               if (flag2) {
                  try {
                     renderManager.invoke19();
                  } catch (Throwable exception22) {
                     renderManager.invoke2();
                  }
               }
            }
         } catch (Throwable exception23) {
         } finally {
            FramebufferUtils.restoreGlState(glStateSnapshot);
         }
      }
   }

   @Generated
   public ModuleManager getModuleManager() {
      return this.moduleManager;
   }

   @Generated
   public String resolve4() {
      return "North";
   }

   @Generated
   public String resolve5() {
      return "v1";
   }

   @Generated
   public String resolve6() {
      return "1.21.8";
   }

   @Generated
   public File getFile() {
      return this.file;
   }

   @Generated
   public File getFile2() {
      return this.file2;
   }

   @Generated
   public String resolve7() {
      return "wild";
   }

   @Generated
   public boolean isFlag() {
      return this.flag;
   }

   @Generated
   public ThemeManager getThemeManager() {
      return this.themeManager;
   }

   @Generated
   public ListenerRegistry getListenerRegistry() {
      return this.listenerRegistry;
   }

   @Generated
   public ConfigManager getConfigManager() {
      return this.configManager;
   }

   @Generated
   public FriendCommand getFriendCommand() {
      return this.friendCommand;
   }

   @Generated
   public ClickGuiScreen getClickGuiScreen() {
      if (this.clickGuiScreen == null) {
         this.clickGuiScreen = resolve3(ClickGuiScreen::new);
      }

      return this.clickGuiScreen;
   }

   @Generated
   public DiscordRpcManager getDiscordRpcManager() {
      return this.discordRpcManager;
   }

   @Generated
   public CommandManager getCommandManager() {
      return this.commandManager;
   }

   @Generated
   public IrcWebSocketClient getIrcWebSocketClient() {
      return this.ircWebSocketClient;
   }

   @Generated
   public String getCommandPrefix() {
      return this.commandPrefix;
   }

   @Generated
   public void setModuleManager(ModuleManager moduleManager) {
      this.moduleManager = moduleManager;
   }

   @Generated
   public void setFlag(boolean bl) {
      this.flag = bl;
   }

   @Generated
   public void setThemeManager(ThemeManager themeManager) {
      this.themeManager = themeManager;
   }

   @Generated
   public void setListenerRegistry(ListenerRegistry listenerRegistry) {
      this.listenerRegistry = listenerRegistry;
   }

   @Generated
   public void setConfigManager(ConfigManager configManager) {
      this.configManager = configManager;
   }

   @Generated
   public void setFriendCommand(FriendCommand friendCommand) {
      this.friendCommand = friendCommand;
   }

   @Generated
   public void setClickGuiScreen(ClickGuiScreen clickGuiScreen) {
      this.clickGuiScreen = clickGuiScreen;
   }

   @Generated
   public void setModernClickGuiScreen(ModernClickGuiScreen modernClickGuiScreen) {
      this.modernClickGuiScreen = modernClickGuiScreen;
   }

   @Generated
   public void setCommandManager(CommandManager commandManager) {
      this.commandManager = commandManager;
   }

   @Generated
   public void setIrcWebSocketClient(IrcWebSocketClient ircWebSocketClient) {
      this.ircWebSocketClient = ircWebSocketClient;
   }

   @Generated
   public static boolean isInitialized() {
      return flag3;
   }

   @Generated
   public static boolean isShuttingDown() {
      return flag5;
   }

   @Generated
   public void setCommandPrefix(String string) {
      this.commandPrefix = string;
   }
}
