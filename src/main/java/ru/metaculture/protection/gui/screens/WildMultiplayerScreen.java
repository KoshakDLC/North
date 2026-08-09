package ru.metaculture.protection;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.multiplayer.AddServerScreen;
import net.minecraft.client.gui.screen.multiplayer.ConnectScreen;
import net.minecraft.client.gui.screen.multiplayer.DirectConnectScreen;
import net.minecraft.client.network.MultiplayerServerListPinger;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.network.ServerInfo.ServerType;
import net.minecraft.client.network.ServerInfo.Status;
import net.minecraft.client.option.ServerList;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.lwjgl.opengl.GL11;

public final class WildMultiplayerScreen extends Screen implements BackdropScreen {
   private static final ThemePalette THEME_PALETTE = ThemePalette.resolve2();
   private static final int INT_VALUE = 14;
   private static final long TIMESTAMP = 140L;
   private static final long TIMESTAMP_2 = 70L;
   private static final ThreadFactory THREAD_FACTORY = runnable -> {
      Thread thread = new Thread(runnable, "Wild Server Ping");
      thread.setDaemon(true);
      return thread;
   };
   private final Screen screen;
   private final MainMenuRenderer mainMenuRenderer = new MainMenuRenderer();
   private MultiplayerServerListPinger multiplayerServerListPinger = new MultiplayerServerListPinger();
   private final List<ServerInfo> items = new ArrayList<>();
   private final List<WildMultiplayerScreen.WildMultiplayerScreenUiState> items2 = new ArrayList<>();
   private final Map<String, WildMultiplayerScreen.WildMultiplayerScreenResources> valuesByKey = new HashMap<>();
   private final List<WildMultiplayerScreen.WildMultiplayerScreenUiState> items3 = List.of(
      new WildMultiplayerScreen.WildMultiplayerScreenUiState("Join", WildMultiplayerScreen.WildMultiplayerScreenState.JOIN),
      new WildMultiplayerScreen.WildMultiplayerScreenUiState("Direct", WildMultiplayerScreen.WildMultiplayerScreenState.DIRECT),
      new WildMultiplayerScreen.WildMultiplayerScreenUiState("Add", WildMultiplayerScreen.WildMultiplayerScreenState.ADD),
      new WildMultiplayerScreen.WildMultiplayerScreenUiState("Edit", WildMultiplayerScreen.WildMultiplayerScreenState.EDIT),
      new WildMultiplayerScreen.WildMultiplayerScreenUiState("Delete", WildMultiplayerScreen.WildMultiplayerScreenState.DELETE),
      new WildMultiplayerScreen.WildMultiplayerScreenUiState("Proxy", WildMultiplayerScreen.WildMultiplayerScreenState.PROXY),
      new WildMultiplayerScreen.WildMultiplayerScreenUiState("Refresh", WildMultiplayerScreen.WildMultiplayerScreenState.REFRESH),
      new WildMultiplayerScreen.WildMultiplayerScreenUiState("Back", WildMultiplayerScreen.WildMultiplayerScreenState.BACK)
   );
   private final WildMultiplayerScreen.WildMultiplayerScreenState2[] wildMultiplayerScreenState2s = new WildMultiplayerScreen.WildMultiplayerScreenState2[14];
   private final SpringIntegrator springIntegrator = new SpringIntegrator(SpringSpec.resolve6());
   private final SpringIntegrator springIntegrator2 = new SpringIntegrator(SpringSpec.resolve6());
   private ServerList serverList;
   private long timestamp;
   private long timestamp2;
   private long timestamp3;
   private long timestamp4;
   private float floatValue;
   private float floatValue2;
   private float floatValue3;
   private float floatValue4;
   private float floatValue5;
   private float floatValue6;
   private float floatValue7;
   private float floatValue8;
   private float floatValue9;
   private float floatValue10;
   private float floatValue11;
   private boolean flag;
   private boolean flag2;
   private boolean flag3;
   private int intValue;
   private int intValue2;
   private int intValue3 = -6357021;
   private int intValue4 = -11341636;
   private Theme theme = Theme.AURORA;
   private boolean flag4;
   private int intValue5 = -1;
   private float floatValue12;
   private float floatValue13;
   private int intValue6 = 5;
   private int intValue7 = -1;
   private String chooseAServer = "Choose a server";
   private MainMenuScreen.MainMenuScreenTimedEntry mainMenuScreenTimedEntry;
   private volatile ScheduledExecutorService scheduledExecutorService;
   private final AtomicInteger atomicInteger = new AtomicInteger();
   private volatile int intValue8;
   private final AtomicInteger atomicInteger2 = new AtomicInteger();
   private volatile int intValue9;
   private float floatValue14 = -100.0F;
   private long timestamp5;
   private float floatValue15;
   private float floatValue16;
   private float floatValue17;
   private float floatValue18;
   private boolean flag5;
   private float floatValue19;
   private float floatValue20;
   private float floatValue21;
   private float floatValue22;
   private float floatValue23;
   private float floatValue24;
   private float floatValue25;
   private boolean flag6;
   private final AtomicBoolean atomicBoolean = new AtomicBoolean(false);

   public WildMultiplayerScreen(Screen screen) {
      super(Text.literal("low free Multiplayer"));
      this.screen = screen;

      for (int intValue = 0; intValue < this.wildMultiplayerScreenState2s.length; intValue++) {
         this.wildMultiplayerScreenState2s[intValue] = new WildMultiplayerScreen.WildMultiplayerScreenState2();
      }
   }

   protected void init() {
      super.init();
      this.timestamp = System.nanoTime();
      this.timestamp2 = this.timestamp;
      this.timestamp3 = this.timestamp;
      this.flag = false;
      this.flag2 = false;
      this.flag3 = false;
      this.intValue = 0;
      this.intValue2 = 0;
      this.floatValue12 = 0.0F;
      this.floatValue13 = 0.0F;
      this.invoke16(true);
      this.springIntegrator.setFloatValue(0.0F);
      this.springIntegrator2.setFloatValue(0.0F);

      for (WildMultiplayerScreen.WildMultiplayerScreenUiState wildMultiplayerScreenUiState : this.items2) {
         wildMultiplayerScreenUiState.invoke();
      }

      for (WildMultiplayerScreen.WildMultiplayerScreenUiState wildMultiplayerScreenUiState2 : this.items3) {
         wildMultiplayerScreenUiState2.invoke();
      }

      this.invoke4();
   }

   public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
      this.invoke(mouseX, mouseY, deltaTicks, false);
   }

   @Override
   public void invoke2(int i, int j, float f) {
      this.invoke(i, j, f, true);
   }

   public void tick() {
      super.tick();
   }

   private void invoke(int i, int j, float f, boolean bl) {
      Window window2 = this.client == null ? null : this.client.getWindow();
      if (window2 != null && !window2.hasZeroWidthOrHeight() && window2.getFramebufferWidth() > 0 && window2.getFramebufferHeight() > 0) {
         int intValue2 = window2.getFramebufferWidth();
         int intValue3 = window2.getFramebufferHeight();
         long longValue = System.nanoTime();
         float floatValue = Math.max(0.001F, Math.min(0.05F, (float)(longValue - this.timestamp2) / 1.0E9F));
         this.timestamp2 = longValue;
         this.floatValue = (float)(longValue - this.timestamp) / 1.0E9F;
         if (this.check(window2, intValue2, intValue3, i, j, longValue)) {
            floatValue = 0.001F;
         }

         this.invoke20();
         this.invoke21(window2, i, j, floatValue, longValue);
         this.invoke22(intValue2, intValue3, floatValue);
         this.invoke23();
         float floatValue2 = (this.floatValue2 / Math.max(1.0F, (float)intValue2) - 0.5F) * 2.0F;
         float floatValue3 = (this.floatValue3 / Math.max(1.0F, (float)intValue3) - 0.5F) * 2.0F;
         float floatValue4 = this.springIntegrator.measure(floatValue2, floatValue);
         float floatValue5 = this.springIntegrator2.measure(floatValue3, floatValue);
         this.invoke26(intValue2, intValue3, floatValue4, floatValue5, floatValue);
         int intValue4 = GL11.glGetInteger(36006);
         MainMenuScreen.MainMenuScreenTimedEntry mainMenuScreenTimedEntry = this.resolve(intValue2, intValue3, intValue4, floatValue4, floatValue5, longValue);
         this.mainMenuScreenTimedEntry = mainMenuScreenTimedEntry;
         if (bl) {
            FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();

            try {
               this.mainMenuRenderer.check(mainMenuScreenTimedEntry);
            } finally {
               FramebufferUtils.restoreGlState(glStateSnapshot);
            }

            this.invoke28(mainMenuScreenTimedEntry);
         }
      }
   }

   public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
   }

   public void renderInGameBackground(DrawContext context) {
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 0 && this.client != null && this.client.getWindow() != null) {
         float floatValue6 = this.measure2(this.client.getWindow(), mouseX);
         float floatValue7 = this.measure3(this.client.getWindow(), mouseY);
         long longValue2 = System.nanoTime();
         if (this.flag6) {
            float floatValue8 = 8.0F;
            if (floatValue6 >= this.floatValue20 - floatValue8
               && floatValue6 <= this.floatValue20 + this.floatValue22 + floatValue8
               && floatValue7 >= this.floatValue21
               && floatValue7 <= this.floatValue21 + this.floatValue23) {
               this.flag5 = true;
               if (floatValue7 >= this.floatValue24 && floatValue7 <= this.floatValue24 + this.floatValue25) {
                  this.floatValue19 = floatValue7 - this.floatValue24;
               } else {
                  this.floatValue19 = this.floatValue25 * 0.5F;
               }

               this.invoke3(floatValue7);
               return true;
            }
         }

         for (WildMultiplayerScreen.WildMultiplayerScreenUiState wildMultiplayerScreenUiState3 : this.items3) {
            if (wildMultiplayerScreenUiState3.flag2 && wildMultiplayerScreenUiState3.flag3 && wildMultiplayerScreenUiState3.check(floatValue6, floatValue7)) {
               wildMultiplayerScreenUiState3.floatValue10 = 1.0F;
               wildMultiplayerScreenUiState3.floatValue11 = 1.0F;
               this.invoke32(wildMultiplayerScreenUiState3.wildMultiplayerScreenState);
               return true;
            }
         }

         for (WildMultiplayerScreen.WildMultiplayerScreenUiState wildMultiplayerScreenUiState4 : this.items2) {
            if (wildMultiplayerScreenUiState4.flag2
               && wildMultiplayerScreenUiState4.flag3
               && wildMultiplayerScreenUiState4.wildMultiplayerScreenState == WildMultiplayerScreen.WildMultiplayerScreenState.SERVER
               && wildMultiplayerScreenUiState4.check(floatValue6, floatValue7)
               && !(wildMultiplayerScreenUiState4.floatValue18 < 0.1F)) {
               if (this.intValue5 == wildMultiplayerScreenUiState4.intValue && this.intValue7 == wildMultiplayerScreenUiState4.intValue && longValue2 - this.timestamp4 < 360000000L) {
                  wildMultiplayerScreenUiState4.floatValue10 = 1.0F;
                  wildMultiplayerScreenUiState4.floatValue11 = 1.0F;
                  this.invoke32(WildMultiplayerScreen.WildMultiplayerScreenState.JOIN);
               } else {
                  this.intValue5 = wildMultiplayerScreenUiState4.intValue;
                  this.chooseAServer = "Ready";
                  wildMultiplayerScreenUiState4.floatValue11 = Math.max(wildMultiplayerScreenUiState4.floatValue11, 0.38F);
               }

               this.intValue7 = wildMultiplayerScreenUiState4.intValue;
               this.timestamp4 = longValue2;
               this.invoke41();
               return true;
            }
         }

         return true;
      } else {
         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
      if (this.items.size() <= this.intValue6) {
         return true;
      } else {
         this.timestamp5 = System.nanoTime();
         this.floatValue12 -= (float)verticalAmount;
         int intValue5 = Math.max(0, this.items.size() - Math.max(1, this.intValue6));
         this.floatValue12 = measure8(this.floatValue12, 0.0F, (float)intValue5);
         return true;
      }
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      if (this.flag5 && this.flag6 && this.client != null && this.client.getWindow() != null) {
         this.invoke3(this.measure3(this.client.getWindow(), mouseY));
         return true;
      } else {
         return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
      }
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      if (button == 0 && this.flag5) {
         this.flag5 = false;
         return true;
      } else {
         return super.mouseReleased(mouseX, mouseY, button);
      }
   }

   private void invoke3(float f) {
      float floatValue9 = this.floatValue23 - this.floatValue25;
      if (!(floatValue9 <= 0.001F)) {
         float floatValue10 = measure8(f - this.floatValue19, this.floatValue21, this.floatValue21 + floatValue9);
         float floatValue11 = (floatValue10 - this.floatValue21) / floatValue9;
         int intValue6 = Math.max(0, this.items.size() - Math.max(1, this.intValue6));
         this.timestamp5 = System.nanoTime();
         this.floatValue12 = floatValue11 * intValue6;
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      boolean flag = (modifiers & 2) != 0 || (modifiers & 8) != 0;
      if (keyCode == 256) {
         this.invoke32(WildMultiplayerScreen.WildMultiplayerScreenState.BACK);
         return true;
      } else if (keyCode == 257 || keyCode == 335) {
         this.invoke32(WildMultiplayerScreen.WildMultiplayerScreenState.JOIN);
         return true;
      } else if (flag && keyCode == 67) {
         this.invoke38();
         return true;
      } else if (keyCode == 82) {
         this.invoke32(WildMultiplayerScreen.WildMultiplayerScreenState.REFRESH);
         return true;
      } else if (keyCode == 261) {
         this.invoke32(WildMultiplayerScreen.WildMultiplayerScreenState.DELETE);
         return true;
      } else if (keyCode == 264) {
         if (flag) {
            this.invoke39(1);
         } else {
            this.invoke40(1);
         }

         return true;
      } else if (keyCode == 265) {
         if (flag) {
            this.invoke39(-1);
         } else {
            this.invoke40(-1);
         }

         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   public boolean charTyped(char chr, int modifiers) {
      if (!this.items.isEmpty() && chr > ' ') {
         char character = Character.toLowerCase(chr);
         int intValue7 = this.intValue5 < 0 ? -1 : this.intValue5;
         int intValue8 = this.items.size();

         for (int intValue9 = 1; intValue9 <= intValue8; intValue9++) {
            int intValue10 = ((intValue7 + intValue9) % intValue8 + intValue8) % intValue8;
            ServerInfo serverInfo2 = this.items.get(intValue10);
            String text2 = serverInfo2 == null ? "" : resolve12(serverInfo2.name, "");
            if (!text2.isEmpty() && Character.toLowerCase(text2.charAt(0)) == character) {
               this.intValue5 = intValue10;
               this.chooseAServer = "Jumped to " + text2;
               this.invoke41();
               return true;
            }
         }

         return true;
      } else {
         return super.charTyped(chr, modifiers);
      }
   }

   public boolean shouldPause() {
      return false;
   }

   public boolean shouldCloseOnEsc() {
      return false;
   }

   public void close() {
      this.invoke32(WildMultiplayerScreen.WildMultiplayerScreenState.BACK);
   }

   public void removed() {
      this.invoke16(true);
      this.invoke42();
      this.mainMenuRenderer.close();
      super.removed();
   }

   private void invoke4() {
      MinecraftClient client = this.client == null ? MinecraftClient.getInstance() : this.client;
      if (client != null) {
         if (this.atomicBoolean.compareAndSet(false, true)) {
            this.invoke16(true);
            this.invoke42();
            this.items.clear();
            this.chooseAServer = "Loading servers...";
            ServerList serverList2 = new ServerList(client);
            CompletableFuture.runAsync(() -> {
               try {
                  serverList2.loadFile();
               } catch (Throwable var2x) {
               }
            }).whenComplete((void_, throwable) -> client.execute(() -> this.invoke5(serverList2, throwable)));
         }
      }
   }

   private void invoke5(ServerList serverList, Throwable throwable) {
      try {
         this.serverList = serverList;
         this.items.clear();

         try {
            int intValue11 = serverList == null ? 0 : serverList.size();

            for (int intValue12 = 0; intValue12 < intValue11; intValue12++) {
               ServerInfo serverInfo3 = serverList.get(intValue12);
               if (serverInfo3 != null) {
                  this.items.add(serverInfo3);
               }
            }
         } catch (Throwable exception) {
         }

         if (throwable != null) {
            this.chooseAServer = "Failed to load servers";
         }

         if (this.items.isEmpty()) {
            this.intValue5 = -1;
            this.floatValue12 = 0.0F;
            this.floatValue13 = 0.0F;
            if (throwable == null) {
               this.chooseAServer = "No saved servers";
            }
         } else {
            if (this.intValue5 < 0 || this.intValue5 >= this.items.size()) {
               this.intValue5 = 0;
            }

            this.floatValue12 = measure8(this.floatValue12, 0.0F, (float)Math.max(0, this.items.size() - this.intValue6));
            this.invoke41();
            if (throwable == null) {
               this.chooseAServer = "Choose a server";
            }

            this.invoke7(false);
         }
      } finally {
         this.atomicBoolean.set(false);
      }
   }

   private void invoke6() {
      if (this.serverList != null) {
         try {
            this.serverList.saveFile();
         } catch (Throwable exception2) {
         }
      }
   }

   private void invoke7(boolean bl) {
      MinecraftClient client2 = this.client == null ? MinecraftClient.getInstance() : this.client;
      if (client2 != null) {
         int intValue13 = ++this.intValue9;
         this.invoke16(false);
         ArrayList arrayList = new ArrayList<>(this.items);
         this.atomicInteger.set(0);
         this.intValue8 = arrayList.size();
         this.atomicInteger2.set(0);
         if (bl) {
            this.invoke19();
         }

         this.chooseAServer = bl ? "Refreshing servers..." : "Pinging servers...";
         this.invoke8(client2, arrayList, intValue13);
      }
   }

   private void invoke8(MinecraftClient minecraftClient, List<ServerInfo> list, int i) {
      if (list.isEmpty()) {
         this.invoke15();
      } else {
         MultiplayerServerListPinger multiplayerServerListPinger2 = this.multiplayerServerListPinger;
         ScheduledExecutorService scheduledExecutorService2 = Executors.newSingleThreadScheduledExecutor(THREAD_FACTORY);
         this.scheduledExecutorService = scheduledExecutorService2;
         scheduledExecutorService2.scheduleWithFixedDelay(() -> this.invoke9(minecraftClient, multiplayerServerListPinger2, list, i, scheduledExecutorService2), 140L, 70L, TimeUnit.MILLISECONDS);
      }
   }

   private void invoke9(
      MinecraftClient minecraftClient,
      MultiplayerServerListPinger multiplayerServerListPinger,
      List<ServerInfo> list,
      int i,
      ScheduledExecutorService scheduledExecutorService
   ) {
      if (i == this.intValue9 && !scheduledExecutorService.isShutdown()) {
         try {
            if (this.atomicInteger.get() < list.size()) {
               int intValue14 = this.atomicInteger.getAndIncrement();
               ServerInfo serverInfo4 = intValue14 < list.size() ? (ServerInfo)list.get(intValue14) : null;
               if (serverInfo4 == null) {
                  this.atomicInteger.set(list.size());
               } else {
                  this.invoke10(minecraftClient, multiplayerServerListPinger, serverInfo4, i);
               }
            }

            multiplayerServerListPinger.tick();
            if (this.atomicInteger.get() >= this.intValue8 && this.atomicInteger2.get() <= 0) {
               minecraftClient.execute(this::invoke15);
               invoke17(multiplayerServerListPinger);
               scheduledExecutorService.shutdown();
               if (this.scheduledExecutorService == scheduledExecutorService) {
                  this.scheduledExecutorService = null;
               }
            }
         } catch (Throwable exception3) {
         }
      } else {
         invoke17(multiplayerServerListPinger);
         scheduledExecutorService.shutdown();
      }
   }

   private void invoke10(MinecraftClient minecraftClient, MultiplayerServerListPinger multiplayerServerListPinger, ServerInfo serverInfo, int i) {
      this.atomicInteger2.incrementAndGet();

      try {
         minecraftClient.execute(() -> this.invoke11(serverInfo, i));
         multiplayerServerListPinger.add(
            serverInfo,
            () -> minecraftClient.execute(() -> this.invoke12(serverInfo, i)),
            () -> minecraftClient.execute(() -> this.invoke13(serverInfo, i))
         );
      } catch (Throwable exception4) {
         minecraftClient.execute(() -> this.invoke13(serverInfo, i));
      }
   }

   private void invoke11(ServerInfo serverInfo, int i) {
      if (i == this.intValue9) {
         serverInfo.setStatus(Status.PINGING);
         serverInfo.playerCountLabel = Text.literal("...");
      }
   }

   private void invoke12(ServerInfo serverInfo, int i) {
      if (i == this.intValue9) {
         CompletableFuture.runAsync(() -> {
            try {
               ServerList.updateServerListEntry(serverInfo);
            } catch (Throwable exception5) {
            }
         }, Util.getMainWorkerExecutor());
         this.invoke14(i);
      }
   }

   private void invoke13(ServerInfo serverInfo, int i) {
      if (i == this.intValue9) {
         serverInfo.ping = -1L;
         serverInfo.setStatus(Status.UNREACHABLE);
         if (serverInfo.label == null || serverInfo.label.getString().isBlank()) {
            serverInfo.label = Text.literal("Cannot reach server");
         }

         serverInfo.playerCountLabel = Text.literal("-");
         this.invoke14(i);
      }
   }

   private void invoke14(int i) {
      if (i == this.intValue9) {
         this.atomicInteger2.updateAndGet(ix -> Math.max(0, ix - 1));
         this.invoke15();
      }
   }

   private void invoke15() {
      if (this.atomicInteger.get() >= this.intValue8 && this.atomicInteger2.get() <= 0) {
         if (!this.items.isEmpty()) {
            this.chooseAServer = "Servers updated";
         }
      }
   }

   private void invoke16(boolean bl) {
      if (bl) {
         this.intValue9++;
      }

      ScheduledExecutorService scheduledExecutorService3 = this.scheduledExecutorService;
      this.scheduledExecutorService = null;
      MultiplayerServerListPinger multiplayerServerListPinger3 = this.multiplayerServerListPinger;
      this.multiplayerServerListPinger = new MultiplayerServerListPinger();
      this.atomicInteger.set(0);
      this.intValue8 = 0;
      this.atomicInteger2.set(0);
      if (scheduledExecutorService3 != null) {
         scheduledExecutorService3.shutdownNow();
      }

      CompletableFuture.runAsync(() -> invoke17(multiplayerServerListPinger3), Util.getMainWorkerExecutor());
   }

   private static void invoke17(MultiplayerServerListPinger multiplayerServerListPinger) {
      try {
         multiplayerServerListPinger.cancel();
      } catch (Throwable exception6) {
      }
   }

   private void invoke18() {
      if (this.items.isEmpty()) {
         this.invoke19();
         this.invoke4();
         this.chooseAServer = "Refreshing servers...";
      } else {
         this.invoke7(true);
      }
   }

   private void invoke19() {
      this.floatValue14 = this.floatValue;

      for (WildMultiplayerScreen.WildMultiplayerScreenUiState wildMultiplayerScreenUiState5 : this.items2) {
         if (wildMultiplayerScreenUiState5.flag2) {
            wildMultiplayerScreenUiState5.floatValue11 = Math.max(wildMultiplayerScreenUiState5.floatValue11, 0.72F);
            wildMultiplayerScreenUiState5.floatValue10 = Math.max(wildMultiplayerScreenUiState5.floatValue10, 0.16F);
            wildMultiplayerScreenUiState5.floatValue12 = Math.max(wildMultiplayerScreenUiState5.floatValue12, 0.65F);
         }
      }

      for (WildMultiplayerScreen.WildMultiplayerScreenUiState wildMultiplayerScreenUiState6 : this.items3) {
         if (wildMultiplayerScreenUiState6.wildMultiplayerScreenState == WildMultiplayerScreen.WildMultiplayerScreenState.REFRESH) {
            wildMultiplayerScreenUiState6.floatValue11 = Math.max(wildMultiplayerScreenUiState6.floatValue11, 1.0F);
            wildMultiplayerScreenUiState6.floatValue10 = Math.max(wildMultiplayerScreenUiState6.floatValue10, 0.18F);
            break;
         }
      }
   }

   private void invoke20() {
      Theme theme = WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null ? WildClient.INSTANCE.themeManager.getTheme() : Theme.AURORA;
      this.theme = theme;
      ThemePalette.Swatch swatch = THEME_PALETTE.resolve3(theme);
      if (swatch != null) {
         this.intValue3 = swatch.getIntValue();
         this.intValue4 = swatch.getIntValue2();
         this.flag4 = swatch.isFlag();
      } else {
         this.flag4 = false;
         Color color = theme.getColor();
         this.intValue3 = 0xFF000000 | color.getRGB() & 16777215;
         float[] floatValues = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
         this.intValue4 = 0xFF000000
            | Color.HSBtoRGB((floatValues[0] + 0.075F) % 1.0F, Math.min(1.0F, floatValues[1] * 1.08F), Math.min(1.0F, floatValues[2] * 1.18F)) & 16777215;
      }
   }

   private void invoke21(Window window, int i, int j, float f, long l) {
      float floatValue12 = this.measure2(window, (double)i);
      float floatValue13 = this.measure3(window, (double)j);
      if (!this.flag) {
         this.floatValue2 = floatValue12;
         this.floatValue3 = floatValue13;
         this.floatValue4 = 0.0F;
         this.floatValue5 = 0.0F;
         this.flag = true;
      } else {
         float floatValue14 = floatValue12 - this.floatValue2;
         float floatValue15 = floatValue13 - this.floatValue3;
         float floatValue16 = measure6(floatValue14, floatValue15);
         if (floatValue16 > 0.2F) {
            this.floatValue4 = measure8(floatValue14 / Math.max(1.0F, (float)window.getFramebufferWidth()) / f, -3.0F, 3.0F);
            this.floatValue5 = measure8(floatValue15 / Math.max(1.0F, (float)window.getFramebufferHeight()) / f, -3.0F, 3.0F);
         } else {
            float floatValue17 = (float)Math.pow(8.0E-4F, f);
            this.floatValue4 *= floatValue17;
            this.floatValue5 *= floatValue17;
         }

         this.floatValue2 = floatValue12;
         this.floatValue3 = floatValue13;
         if (floatValue16 > 1.5F) {
            this.timestamp3 = l;
         }
      }
   }

   private void invoke22(int i, int j, float f) {
      if (!this.flag2) {
         this.floatValue6 = this.floatValue2;
         this.floatValue7 = this.floatValue3;
         this.floatValue8 = 0.0F;
         this.floatValue9 = 0.0F;
         this.flag2 = true;
      } else {
         float floatValue18 = this.floatValue6;
         float floatValue19 = this.floatValue7;
         float floatValue20 = measure6(this.floatValue2 - this.floatValue6, this.floatValue3 - this.floatValue7);
         float floatValue21 = (1.0F - (float)Math.pow(1.8E-5F, f)) * (0.62F + measure8(floatValue20 / 680.0F, 0.0F, 0.32F));
         this.floatValue6 = this.floatValue6 + (this.floatValue2 - this.floatValue6) * measure8(floatValue21, 0.035F, 0.18F);
         this.floatValue7 = this.floatValue7 + (this.floatValue3 - this.floatValue7) * measure8(floatValue21, 0.035F, 0.18F);
         float floatValue22 = measure8((this.floatValue6 - floatValue18) / Math.max(1.0F, (float)i) / f, -1.35F, 1.35F);
         float floatValue23 = measure8((this.floatValue7 - floatValue19) / Math.max(1.0F, (float)j) / f, -1.35F, 1.35F);
         float floatValue24 = 1.0F - (float)Math.pow(0.004F, f);
         this.floatValue8 = this.floatValue8 + (floatValue22 - this.floatValue8) * floatValue24;
         this.floatValue9 = this.floatValue9 + (floatValue23 - this.floatValue9) * floatValue24;
      }
   }

   private void invoke23() {
      if (!this.flag3) {
         this.floatValue10 = this.floatValue6;
         this.floatValue11 = this.floatValue7;
         this.flag3 = true;
         this.invoke25(this.floatValue6, this.floatValue7, 0.24F);
      } else {
         float floatValue25 = measure6(this.floatValue6 - this.floatValue10, this.floatValue7 - this.floatValue11);
         if (floatValue25 > 8.5F) {
            this.invoke25(this.floatValue6, this.floatValue7, measure8(floatValue25 / 240.0F, 0.08F, 0.38F));
            this.floatValue10 = this.floatValue6;
            this.floatValue11 = this.floatValue7;
         }
      }
   }

   private boolean check(Window window, int i, int j, int k, int l, long m) {
      if (this.intValue == i && this.intValue2 == j) {
         return false;
      } else {
         this.intValue = i;
         this.intValue2 = j;
         float floatValue26 = measure8(this.measure2(window, (double)k), 0.0F, (float)i);
         float floatValue27 = measure8(this.measure3(window, (double)l), 0.0F, (float)j);
         this.floatValue2 = this.floatValue6 = this.floatValue10 = floatValue26;
         this.floatValue3 = this.floatValue7 = this.floatValue11 = floatValue27;
         this.floatValue4 = this.floatValue5 = 0.0F;
         this.floatValue8 = this.floatValue9 = 0.0F;
         this.flag = true;
         this.flag2 = true;
         this.flag3 = true;
         this.timestamp3 = m;
         this.flag5 = false;
         this.springIntegrator.setFloatValue(0.0F);
         this.springIntegrator2.setFloatValue(0.0F);
         this.floatValue13 = this.floatValue12;
         this.mainMenuScreenTimedEntry = null;
         this.invoke24();
         this.invoke25(floatValue26, floatValue27, 0.14F);
         this.invoke41();
         return true;
      }
   }

   private void invoke24() {
      for (WildMultiplayerScreen.WildMultiplayerScreenState2 wildMultiplayerScreenState2 : this.wildMultiplayerScreenState2s) {
         wildMultiplayerScreenState2.floatValue = 0.0F;
         wildMultiplayerScreenState2.floatValue2 = 0.0F;
         wildMultiplayerScreenState2.floatValue3 = -100.0F;
         wildMultiplayerScreenState2.floatValue4 = 0.0F;
      }
   }

   private void invoke25(float f, float g, float h) {
      int intValue15 = 0;
      float floatValue28 = -1.0F;

      for (int intValue16 = 0; intValue16 < this.wildMultiplayerScreenState2s.length; intValue16++) {
         float floatValue29 = this.floatValue - this.wildMultiplayerScreenState2s[intValue16].floatValue3;
         if (this.wildMultiplayerScreenState2s[intValue16].floatValue4 <= 0.0F) {
            intValue15 = intValue16;
            break;
         }

         if (floatValue29 > floatValue28) {
            floatValue28 = floatValue29;
            intValue15 = intValue16;
         }
      }

      this.wildMultiplayerScreenState2s[intValue15].floatValue = f;
      this.wildMultiplayerScreenState2s[intValue15].floatValue2 = g;
      this.wildMultiplayerScreenState2s[intValue15].floatValue3 = this.floatValue;
      this.wildMultiplayerScreenState2s[intValue15].floatValue4 = h;
   }

   private void invoke26(int i, int j, float f, float g, float h) {
      float floatValue30 = measure4(i, j);
      float floatValue31 = measure8(i * 0.38F, 520.0F * floatValue30, 760.0F * floatValue30);
      float floatValue32 = measure8(j * 0.078F, 72.0F * floatValue30, 94.0F * floatValue30);
      float floatValue33 = 14.0F * floatValue30;
      this.intValue6 = Math.max(3, Math.min(6, (int)(j * 0.54F / (floatValue32 + floatValue33))));
      if (this.items.size() < this.intValue6 && !this.items.isEmpty()) {
         this.intValue6 = Math.max(1, this.items.size());
      }

      int intValue17 = Math.max(0, this.items.size() - Math.max(1, this.intValue6));
      this.floatValue12 = measure8(this.floatValue12, 0.0F, (float)intValue17);
      float floatValue34 = 1.0F - (float)Math.exp(-22.0F * h);
      this.floatValue13 = this.floatValue13 + (this.floatValue12 - this.floatValue13) * floatValue34;
      if (Float.isNaN(this.floatValue13)) {
         this.floatValue13 = this.floatValue12;
      }

      float floatValue35 = this.intValue6 * floatValue32 + Math.max(0, this.intValue6 - 1) * floatValue33;
      float floatValue36 = i * 0.5F + f * 1.65F * floatValue30;
      float floatValue37 = j * 0.255F + g * 1.05F * floatValue30;
      if (floatValue37 + floatValue35 > j * 0.79F) {
         floatValue37 = j * 0.79F - floatValue35;
      }

      floatValue37 = Math.max(j * 0.18F, floatValue37);
      this.floatValue15 = floatValue36 - floatValue31 * 0.5F;
      this.floatValue16 = floatValue37;
      this.floatValue17 = floatValue31;
      this.floatValue18 = floatValue35;
      this.flag6 = intValue17 > 0;
      if (this.flag6) {
         this.floatValue22 = Math.max(4.0F, 5.5F * floatValue30);
         this.floatValue20 = floatValue36 + floatValue31 * 0.5F + 16.0F * floatValue30;
         this.floatValue21 = floatValue37;
         this.floatValue23 = floatValue35;
         float floatValue38 = measure8((float)this.intValue6 / this.items.size(), 0.1F, 1.0F);
         this.floatValue25 = Math.max(34.0F * floatValue30, this.floatValue23 * floatValue38);
         float floatValue39 = this.floatValue23 - this.floatValue25;
         float floatValue40 = intValue17 == 0 ? 0.0F : this.floatValue13 / intValue17;
         this.floatValue24 = this.floatValue21 + floatValue39 * floatValue40;
      }

      int intValue18 = (int)Math.floor(this.floatValue13);
      float floatValue41 = this.floatValue13 - intValue18;
      int intValue19 = this.items.isEmpty() ? 1 : Math.min(this.items.size(), this.intValue6 + 2);

      while (this.items2.size() < intValue19) {
         this.items2.add(new WildMultiplayerScreen.WildMultiplayerScreenUiState("", WildMultiplayerScreen.WildMultiplayerScreenState.SERVER));
      }

      for (int intValue20 = 0; intValue20 < this.items2.size(); intValue20++) {
         WildMultiplayerScreen.WildMultiplayerScreenUiState wildMultiplayerScreenUiState7 = this.items2.get(intValue20);
         if (intValue20 >= intValue19) {
            wildMultiplayerScreenUiState7.flag2 = false;
         } else {
            wildMultiplayerScreenUiState7.flag2 = true;
            wildMultiplayerScreenUiState7.floatValue5 = floatValue31;
            wildMultiplayerScreenUiState7.floatValue6 = floatValue32;
            wildMultiplayerScreenUiState7.floatValue = floatValue36 - floatValue31 * 0.5F;
            wildMultiplayerScreenUiState7.floatValue2 = floatValue37 + (intValue20 - floatValue41) * (floatValue32 + floatValue33);
            wildMultiplayerScreenUiState7.floatValue7 = Math.min(floatValue32 * 0.36F, 20.0F * floatValue30);
            wildMultiplayerScreenUiState7.floatValue17 = 58.0F * floatValue30;
            wildMultiplayerScreenUiState7.flag3 = !this.items.isEmpty();
            wildMultiplayerScreenUiState7.floatValue12 = this.measure(intValue20);
            if (this.items.isEmpty()) {
               wildMultiplayerScreenUiState7.text = "No saved servers";
               wildMultiplayerScreenUiState7.text2 = "Add a server or connect directly";
               wildMultiplayerScreenUiState7.intValue = -1;
               wildMultiplayerScreenUiState7.flag = false;
               wildMultiplayerScreenUiState7.floatValue18 = measure7(measure8((this.floatValue - 0.15F) / 0.92F, 0.0F, 1.0F));
            } else {
               int intValue21 = intValue18 + intValue20;
               ServerInfo serverInfo5 = intValue21 >= 0 && intValue21 < this.items.size() ? this.items.get(intValue21) : null;
               wildMultiplayerScreenUiState7.intValue = intValue21;
               wildMultiplayerScreenUiState7.flag3 = serverInfo5 != null;
               wildMultiplayerScreenUiState7.text = serverInfo5 == null ? "" : resolve12(serverInfo5.name, "Unnamed server");
               wildMultiplayerScreenUiState7.text2 = serverInfo5 == null ? "" : resolve12(serverInfo5.address, "No address");
               wildMultiplayerScreenUiState7.flag = intValue21 == this.intValue5;
               float floatValue42 = wildMultiplayerScreenUiState7.floatValue2 + floatValue32 * 0.5F;
               float floatValue43 = floatValue37 + floatValue35;
               float floatValue44 = floatValue32 * 0.65F;
               float floatValue45 = measure8((floatValue42 - floatValue37 + floatValue44) / floatValue44, 0.0F, 1.0F);
               float floatValue46 = measure8((floatValue43 + floatValue44 - floatValue42) / floatValue44, 0.0F, 1.0F);
               float floatValue47 = floatValue45 * floatValue46;
               wildMultiplayerScreenUiState7.floatValue18 = measure7(measure8((this.floatValue - 0.15F - intValue20 * 0.045F) / 0.92F, 0.0F, 1.0F)) * floatValue47;
               wildMultiplayerScreenUiState7.floatValue11 = Math.max(wildMultiplayerScreenUiState7.floatValue11, wildMultiplayerScreenUiState7.floatValue12 * 0.34F);
            }

            this.invoke27(wildMultiplayerScreenUiState7, h, floatValue30);
         }
      }

      float floatValue48 = 10.0F * floatValue30;
      float floatValue49 = measure8(i * 0.08F, 95.0F * floatValue30, 135.0F * floatValue30);
      float floatValue50 = 42.0F * floatValue30;
      int intValue22 = Math.min(5, this.items3.size());
      int intValue23 = this.items3.size() - intValue22;
      float floatValue51 = intValue22 * floatValue49 + (intValue22 - 1) * floatValue48;
      float floatValue52 = intValue23 * floatValue49 + (intValue23 - 1) * floatValue48;
      float floatValue53 = i * 0.5F - floatValue51 * 0.5F + f * 1.35F * floatValue30;
      float floatValue54 = i * 0.5F - floatValue52 * 0.5F + f * 1.35F * floatValue30;
      float floatValue55 = Math.min(j - floatValue50 * 2.0F - floatValue48 - 28.0F * floatValue30, floatValue37 + floatValue35 + 24.0F * floatValue30 + g * 0.45F * floatValue30);

      for (int intValue24 = 0; intValue24 < this.items3.size(); intValue24++) {
         WildMultiplayerScreen.WildMultiplayerScreenUiState wildMultiplayerScreenUiState8 = this.items3.get(intValue24);
         wildMultiplayerScreenUiState8.flag2 = true;
         wildMultiplayerScreenUiState8.floatValue5 = floatValue49;
         wildMultiplayerScreenUiState8.floatValue6 = floatValue50;
         boolean flag2 = intValue24 < intValue22;
         int intValue25 = flag2 ? intValue24 : intValue24 - intValue22;
         wildMultiplayerScreenUiState8.floatValue = (flag2 ? floatValue53 : floatValue54) + intValue25 * (floatValue49 + floatValue48);
         wildMultiplayerScreenUiState8.floatValue2 = floatValue55 + (flag2 ? 0.0F : floatValue50 + floatValue48);
         wildMultiplayerScreenUiState8.floatValue7 = Math.min(floatValue50 * 0.42F, 18.0F * floatValue30);
         wildMultiplayerScreenUiState8.floatValue17 = 42.0F * floatValue30;
         wildMultiplayerScreenUiState8.floatValue18 = measure7(measure8((this.floatValue - 0.38F - intValue24 * 0.035F) / 0.74F, 0.0F, 1.0F));
         wildMultiplayerScreenUiState8.flag3 = this.check3(wildMultiplayerScreenUiState8.wildMultiplayerScreenState);
         wildMultiplayerScreenUiState8.flag = false;
         wildMultiplayerScreenUiState8.floatValue12 = wildMultiplayerScreenUiState8.wildMultiplayerScreenState == WildMultiplayerScreen.WildMultiplayerScreenState.REFRESH ? this.measure(0) : 0.0F;
         this.invoke27(wildMultiplayerScreenUiState8, h, floatValue30);
      }
   }

   private float measure(int i) {
      float floatValue56 = this.floatValue - this.floatValue14 - i * 0.055F;
      if (!(floatValue56 < 0.0F) && !(floatValue56 > 0.86F)) {
         float floatValue57 = measure8(floatValue56 / 0.86F, 0.0F, 1.0F);
         return (float)Math.sin(floatValue57 * Math.PI) * measure7(1.0F - floatValue57 * 0.42F);
      } else {
         return 0.0F;
      }
   }

   private void invoke27(WildMultiplayerScreen.WildMultiplayerScreenUiState wildMultiplayerScreenUiState9, float f, float g) {
      float floatValue58 = measure5(
         this.floatValue2,
         this.floatValue3,
         wildMultiplayerScreenUiState9.floatValue,
         wildMultiplayerScreenUiState9.floatValue2,
         wildMultiplayerScreenUiState9.floatValue5,
         wildMultiplayerScreenUiState9.floatValue6,
         wildMultiplayerScreenUiState9.floatValue7
      );
      boolean flag3 = floatValue58 <= 0.0F;
      float floatValue59 = wildMultiplayerScreenUiState9.wildMultiplayerScreenState == WildMultiplayerScreen.WildMultiplayerScreenState.SERVER ? 42.0F * g : 24.0F * g;
      float floatValue60 = 1.0F - measure7(measure8(Math.max(0.0F, floatValue58) / Math.max(1.0F, floatValue59), 0.0F, 1.0F));
      float floatValue61 = wildMultiplayerScreenUiState9.flag ? 0.42F : 0.0F;
      float floatValue62 = wildMultiplayerScreenUiState9.flag3 ? Math.max(floatValue60, floatValue61) : 0.0F;
      float floatValue63 = wildMultiplayerScreenUiState9.flag3 && flag3 ? 1.0F : floatValue61 * 0.45F;
      wildMultiplayerScreenUiState9.floatValue8 = wildMultiplayerScreenUiState9.floatValue8 + (floatValue63 - wildMultiplayerScreenUiState9.floatValue8) * (1.0F - (float)Math.pow(1.1E-4F, f));
      wildMultiplayerScreenUiState9.floatValue9 = wildMultiplayerScreenUiState9.floatValue9 + (floatValue62 - wildMultiplayerScreenUiState9.floatValue9) * (1.0F - (float)Math.pow(1.6E-4F, f));
      wildMultiplayerScreenUiState9.floatValue10 = wildMultiplayerScreenUiState9.floatValue10 + (0.0F - wildMultiplayerScreenUiState9.floatValue10) * (1.0F - (float)Math.pow(1.8E-5F, f));
      wildMultiplayerScreenUiState9.floatValue11 = wildMultiplayerScreenUiState9.floatValue11 + (0.0F - wildMultiplayerScreenUiState9.floatValue11) * (1.0F - (float)Math.pow(6.0E-6F, f));
      float floatValue64 = measure8((this.floatValue6 - wildMultiplayerScreenUiState9.floatValue) / Math.max(1.0F, wildMultiplayerScreenUiState9.floatValue5), 0.0F, 1.0F);
      float floatValue65 = measure8((this.floatValue7 - wildMultiplayerScreenUiState9.floatValue2) / Math.max(1.0F, wildMultiplayerScreenUiState9.floatValue6), 0.0F, 1.0F);
      float floatValue66 = 1.0F - (float)Math.pow(2.5E-4F, f);
      wildMultiplayerScreenUiState9.floatValue14 = wildMultiplayerScreenUiState9.floatValue14 + (floatValue64 - wildMultiplayerScreenUiState9.floatValue14) * floatValue66;
      wildMultiplayerScreenUiState9.floatValue15 = wildMultiplayerScreenUiState9.floatValue15 + (floatValue65 - wildMultiplayerScreenUiState9.floatValue15) * floatValue66;
      float floatValue67 = 1.0F
         + wildMultiplayerScreenUiState9.floatValue9 * (wildMultiplayerScreenUiState9.wildMultiplayerScreenState == WildMultiplayerScreen.WildMultiplayerScreenState.SERVER ? 0.034F : 0.042F)
         + (wildMultiplayerScreenUiState9.flag ? 0.008F : 0.0F)
         + wildMultiplayerScreenUiState9.floatValue12 * 0.018F
         - wildMultiplayerScreenUiState9.floatValue10 * 0.065F;
      wildMultiplayerScreenUiState9.floatValue13 = wildMultiplayerScreenUiState9.springIntegrator.measure(floatValue67, f);
      float floatValue68 = (1.0F - wildMultiplayerScreenUiState9.floatValue18) * (wildMultiplayerScreenUiState9.wildMultiplayerScreenState == WildMultiplayerScreen.WildMultiplayerScreenState.SERVER ? 18.0F : 11.0F) * g;
      float floatValue69 = (wildMultiplayerScreenUiState9.floatValue14 - 0.5F)
         * (wildMultiplayerScreenUiState9.wildMultiplayerScreenState == WildMultiplayerScreen.WildMultiplayerScreenState.SERVER ? 9.5F : 6.5F)
         * g
         * wildMultiplayerScreenUiState9.floatValue9;
      float floatValue70 = (wildMultiplayerScreenUiState9.floatValue15 - 0.5F)
            * (wildMultiplayerScreenUiState9.wildMultiplayerScreenState == WildMultiplayerScreen.WildMultiplayerScreenState.SERVER ? 5.5F : 4.0F)
            * g
            * wildMultiplayerScreenUiState9.floatValue9
         - wildMultiplayerScreenUiState9.floatValue8 * 1.2F * g
         + floatValue68
         - wildMultiplayerScreenUiState9.floatValue12 * (wildMultiplayerScreenUiState9.wildMultiplayerScreenState == WildMultiplayerScreen.WildMultiplayerScreenState.SERVER ? 5.0F : 2.5F) * g;
      wildMultiplayerScreenUiState9.floatValue3 = wildMultiplayerScreenUiState9.floatValue + floatValue69;
      wildMultiplayerScreenUiState9.floatValue4 = wildMultiplayerScreenUiState9.floatValue2 + floatValue70;
      wildMultiplayerScreenUiState9.floatValue16 = measure8(
         measure6(this.floatValue8, this.floatValue9) * 0.46F * wildMultiplayerScreenUiState9.floatValue9
            + Math.abs(wildMultiplayerScreenUiState9.springIntegrator.getFloatValue2()) * 0.032F
            + wildMultiplayerScreenUiState9.floatValue12 * 0.22F,
         0.0F,
         1.0F
      );
   }

   private MainMenuScreen.MainMenuScreenTimedEntry resolve(int i, int j, int k, float f, float g, long l) {
      float floatValue71 = Math.max(0.0F, (float)(l - this.timestamp3) / 1.0E9F);
      float floatValue72 = measure8(measure6(this.floatValue8, this.floatValue9), 0.0F, 3.0F);
      float floatValue73 = Math.max((float)Math.exp(-floatValue71 * 1.35F), measure8(floatValue72 * 0.28F, 0.0F, 1.0F));
      float floatValue74 = measure7(measure8(this.floatValue / 0.95F, 0.0F, 1.0F));
      float floatValue75 = measure4(i, j);
      float floatValue76 = 0.0F;
      ArrayList arrayList2 = new ArrayList();

      for (WildMultiplayerScreen.WildMultiplayerScreenUiState wildMultiplayerScreenUiState10 : this.items2) {
         if (wildMultiplayerScreenUiState10.flag2 && !(wildMultiplayerScreenUiState10.floatValue18 <= 0.01F)) {
            floatValue76 = Math.max(floatValue76, wildMultiplayerScreenUiState10.floatValue11);
            arrayList2.add(this.resolve2(wildMultiplayerScreenUiState10));
         }
      }

      for (WildMultiplayerScreen.WildMultiplayerScreenUiState wildMultiplayerScreenUiState11 : this.items3) {
         if (wildMultiplayerScreenUiState11.flag2) {
            floatValue76 = Math.max(floatValue76, wildMultiplayerScreenUiState11.floatValue11);
            arrayList2.add(this.resolve2(wildMultiplayerScreenUiState11));
         }
      }

      MainMenuScreen.MainMenuScreenData[] w283s = new MainMenuScreen.MainMenuScreenData[14];

      for (int intValue26 = 0; intValue26 < 14; intValue26++) {
         WildMultiplayerScreen.WildMultiplayerScreenState2 wildMultiplayerScreenState22 = this.wildMultiplayerScreenState2s[intValue26];
         float floatValue77 = Math.max(0.0F, this.floatValue - wildMultiplayerScreenState22.floatValue3);
         float floatValue78 = floatValue77 > 3.1F ? 0.0F : wildMultiplayerScreenState22.floatValue4;
         w283s[intValue26] = new MainMenuScreen.MainMenuScreenData(wildMultiplayerScreenState22.floatValue / Math.max(1.0F, (float)i), wildMultiplayerScreenState22.floatValue2 / Math.max(1.0F, (float)j), floatValue77, floatValue78);
      }

      return new MainMenuScreen.MainMenuScreenTimedEntry(
         i,
         j,
         k,
         this.floatValue,
         this.floatValue6,
         this.floatValue7,
         this.floatValue6 / Math.max(1.0F, (float)i),
         this.floatValue7 / Math.max(1.0F, (float)j),
         this.floatValue8,
         this.floatValue9,
         floatValue72,
         measure9(this.intValue3),
         measure10(this.intValue3),
         measure11(this.intValue3),
         measure9(this.intValue4),
         measure10(this.intValue4),
         measure11(this.intValue4),
         -f * 0.0011F,
         -g * 9.0E-4F,
         f * 1.25F * floatValue75,
         g * 1.05F * floatValue75,
         f * 1.55F * floatValue75,
         g * 1.35F * floatValue75,
         floatValue73,
         floatValue73 > 0.08F ? 1.0F : 0.88F,
         floatValue74,
         measure8(floatValue76, 0.0F, 1.0F),
         this.theme == Theme.SAKURA_BREEZE,
         this.theme == Theme.VERNAL_SOLSTICE,
         this.theme == Theme.MIDNIGHT_AZURE,
         this.flag4,
         null,
         null,
         List.of(),
         List.of(),
         List.of(),
         new MainMenuScreen.MainMenuScreenBounds3(0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
         arrayList2,
         w283s
      );
   }

   private MainMenuScreen.MainMenuScreenBounds resolve2(WildMultiplayerScreen.WildMultiplayerScreenUiState wildMultiplayerScreenUiState12) {
      float floatValue79 = wildMultiplayerScreenUiState12.flag3 ? wildMultiplayerScreenUiState12.floatValue18 : wildMultiplayerScreenUiState12.floatValue18 * 0.62F;
      return new MainMenuScreen.MainMenuScreenBounds(
         wildMultiplayerScreenUiState12.text,
         wildMultiplayerScreenUiState12.floatValue3,
         wildMultiplayerScreenUiState12.floatValue4,
         wildMultiplayerScreenUiState12.floatValue5,
         wildMultiplayerScreenUiState12.floatValue6,
         wildMultiplayerScreenUiState12.floatValue7,
         wildMultiplayerScreenUiState12.floatValue8,
         wildMultiplayerScreenUiState12.floatValue9,
         wildMultiplayerScreenUiState12.floatValue10,
         floatValue79,
         wildMultiplayerScreenUiState12.floatValue11,
         wildMultiplayerScreenUiState12.floatValue17,
         wildMultiplayerScreenUiState12.floatValue13,
         wildMultiplayerScreenUiState12.floatValue14,
         wildMultiplayerScreenUiState12.floatValue15,
         wildMultiplayerScreenUiState12.floatValue16
      );
   }

   private void invoke28(MainMenuScreen.MainMenuScreenTimedEntry mainMenuScreenTimedEntry2) {
      try {
         WildClient.invoke15();
         RenderManager renderManager = WildClient.resolve();
         if (renderManager == null) {
            return;
         }

         FramebufferUtils.GlStateSnapshot glStateSnapshot2 = FramebufferUtils.captureGlState();

         try {
            renderManager.invoke(mainMenuScreenTimedEntry2.framebufferWidth(), mainMenuScreenTimedEntry2.framebufferHeight());
            float floatValue80 = measure4(mainMenuScreenTimedEntry2.framebufferWidth(), mainMenuScreenTimedEntry2.framebufferHeight());
            float floatValue81 = mainMenuScreenTimedEntry2.framebufferWidth() * 0.5F + mainMenuScreenTimedEntry2.uiParallaxX() * 0.16F;
            float floatValue82 = mainMenuScreenTimedEntry2.framebufferHeight() * 0.135F + mainMenuScreenTimedEntry2.uiParallaxY() * 0.1F;
            float floatValue83 = measure7(mainMenuScreenTimedEntry2.sceneEntry());
            renderManager.invoke70(FontRegistry.fontObject4, floatValue81, floatValue82, 38.0F * floatValue80, "Multiplayer", this.compute3(0.92F * floatValue83), "c");
            String text3 = this.items.size() == 1 ? "1 saved server" : this.items.size() + " saved servers";
            renderManager.invoke70(
               FontRegistry.fontObject, floatValue81, floatValue82 + 28.0F * floatValue80, 25.0F * floatValue80, text3 + "  /  " + this.chooseAServer, this.compute4(0.48F * floatValue83), "c"
            );
            renderManager.invoke20();
            renderManager.invoke24(
               this.floatValue15 - 15.0F * floatValue80,
               this.floatValue16 - 8.0F * floatValue80,
               this.floatValue17 + 30.0F * floatValue80,
               this.floatValue18 + 16.0F * floatValue80,
               0.0F,
               0.0F,
               0.0F,
               0.0F
            );

            for (WildMultiplayerScreen.WildMultiplayerScreenUiState wildMultiplayerScreenUiState13 : this.items2) {
               if (wildMultiplayerScreenUiState13.flag2 && wildMultiplayerScreenUiState13.floatValue18 > 0.01F) {
                  this.invoke29(renderManager, wildMultiplayerScreenUiState13, floatValue80);
               }
            }

            renderManager.invoke20();
            renderManager.invoke25();

            for (WildMultiplayerScreen.WildMultiplayerScreenUiState wildMultiplayerScreenUiState14 : this.items3) {
               if (wildMultiplayerScreenUiState14.flag2) {
                  this.invoke30(renderManager, wildMultiplayerScreenUiState14, floatValue80);
               }
            }

            if (this.flag6) {
               float floatValue84 = measure7(mainMenuScreenTimedEntry2.sceneEntry());
               renderManager.invoke5(
                  this.floatValue20,
                  this.floatValue21,
                  this.floatValue22,
                  this.floatValue23,
                  this.floatValue22 * 0.5F,
                  this.flag4 ? compute5(0.0F, 0.0F, 0.0F, 0.045F * floatValue84) : compute5(1.0F, 1.0F, 1.0F, 0.05F * floatValue84)
               );
               int intValue27 = compute6(this.intValue4, this.intValue3, 0.5F, (this.flag5 ? 0.75F : 0.45F) * floatValue84);
               renderManager.invoke5(this.floatValue20, this.floatValue24, this.floatValue22, this.floatValue25, this.floatValue22 * 0.5F, intValue27);
            }

            renderManager.invoke19();
         } finally {
            FramebufferUtils.restoreGlState(glStateSnapshot2);
         }
      } catch (Throwable exception7) {
      }
   }

   private void invoke29(RenderManager renderManager2, WildMultiplayerScreen.WildMultiplayerScreenUiState wildMultiplayerScreenUiState15, float f) {
      float floatValue85 = wildMultiplayerScreenUiState15.floatValue18 * (wildMultiplayerScreenUiState15.flag3 ? 1.0F : 0.58F);
      float floatValue86 = 25.0F * f;
      ServerInfo serverInfo6 = wildMultiplayerScreenUiState15.intValue >= 0 && wildMultiplayerScreenUiState15.intValue < this.items.size()
         ? this.items.get(wildMultiplayerScreenUiState15.intValue)
         : null;
      float floatValue87 = Math.min(wildMultiplayerScreenUiState15.floatValue6 * 0.62F, 54.0F * f);
      float floatValue88 = wildMultiplayerScreenUiState15.floatValue3 + floatValue86;
      float floatValue89 = wildMultiplayerScreenUiState15.floatValue4 + wildMultiplayerScreenUiState15.floatValue6 * 0.5F - floatValue87 * 0.5F;
      float floatValue90 = wildMultiplayerScreenUiState15.flag ? 0.66F + 0.34F * (float)Math.sin(this.floatValue * 2.1F) : 0.36F + 0.16F * wildMultiplayerScreenUiState15.floatValue9;
      int intValue28 = compute6(
         this.intValue4, this.intValue3, floatValue90, (0.1F + wildMultiplayerScreenUiState15.floatValue9 * 0.16F + (wildMultiplayerScreenUiState15.flag ? 0.12F : 0.0F)) * floatValue85
      );
      renderManager2.invoke5(floatValue88, floatValue89, floatValue87, floatValue87, floatValue87 * 0.32F, intValue28);
      if (wildMultiplayerScreenUiState15.floatValue12 > 0.001F) {
         float floatValue91 = wildMultiplayerScreenUiState15.floatValue3 + 26.0F * f;
         float floatValue92 = wildMultiplayerScreenUiState15.floatValue4 + wildMultiplayerScreenUiState15.floatValue6 - 8.0F * f;
         float floatValue93 = (wildMultiplayerScreenUiState15.floatValue5 - 52.0F * f) * wildMultiplayerScreenUiState15.floatValue12;
         renderManager2.invoke5(
            floatValue91,
            floatValue92,
            floatValue93,
            2.4F * f,
            1.2F * f,
            compute6(this.intValue4, this.intValue3, 0.5F + wildMultiplayerScreenUiState15.floatValue12 * 0.25F, 0.42F * floatValue85 * wildMultiplayerScreenUiState15.floatValue12)
         );
      }

      WildMultiplayerScreen.WildMultiplayerScreenResources wildMultiplayerScreenResources = serverInfo6 == null ? null : (this.check4() ? this.resolve10(serverInfo6) : this.resolve9(serverInfo6));
      int intValue29 = wildMultiplayerScreenResources == null ? 0 : wildMultiplayerScreenResources.compute();
      if (intValue29 > 0) {
         renderManager2.invoke12(intValue29, floatValue88 + 2.0F * f, floatValue89 + 2.0F * f, floatValue87 - 4.0F * f, floatValue87 - 4.0F * f, 0.0F, 0.0F, 1.0F, 1.0F, floatValue87 * 0.25F);
         renderManager2.invoke5(floatValue88, floatValue89, floatValue87, floatValue87, floatValue87 * 0.32F, compute5(1.0F, 1.0F, 1.0F, (0.032F + wildMultiplayerScreenUiState15.floatValue9 * 0.026F) * floatValue85));
      } else {
         renderManager2.invoke70(
            BrandMark.font(),
            floatValue88 + floatValue87 * 0.5F,
            floatValue89 + floatValue87 * 0.72F,
            floatValue87 * 0.82F,
            BrandMark.GLYPH,
            this.flag4
               ? this.compute3((0.72F + wildMultiplayerScreenUiState15.floatValue9 * 0.2F) * floatValue85)
               : compute5(1.0F, 1.0F, 1.0F, (0.72F + wildMultiplayerScreenUiState15.floatValue9 * 0.2F) * floatValue85),
            "c"
         );
      }

      float floatValue94 = floatValue88 + floatValue87 + 18.0F * f;
      String text4 = serverInfo6 != null ? this.resolve4(serverInfo6) : "";
      float floatValue95 = wildMultiplayerScreenUiState15.flag3
         ? Math.max(72.0F * f, RenderManager.resolve7(FontRegistry.fontObject, text4, 24.0F * f).floatValue + 24.0F * f)
         : 0.0F;
      float floatValue96 = wildMultiplayerScreenUiState15.flag3 ? floatValue95 + 48.0F * f : 80.0F * f;
      float floatValue97 = wildMultiplayerScreenUiState15.floatValue5 - (floatValue94 - wildMultiplayerScreenUiState15.floatValue3) - floatValue96;
      String text5 = resolve13(wildMultiplayerScreenUiState15.text, floatValue97, 25.0F * f, FontRegistry.fontObject4);
      String text6 = resolve13(wildMultiplayerScreenUiState15.text2, floatValue97, 22.0F * f, FontRegistry.fontObject);
      renderManager2.invoke69(
         FontRegistry.fontObject4,
         floatValue94,
         wildMultiplayerScreenUiState15.floatValue4 + wildMultiplayerScreenUiState15.floatValue6 * 0.5F - 6.0F * f,
         25.0F * f,
         text5,
         this.compute3((0.88F + wildMultiplayerScreenUiState15.floatValue9 * 0.08F) * floatValue85)
      );
      renderManager2.invoke69(
         FontRegistry.fontObject,
         floatValue94,
         wildMultiplayerScreenUiState15.floatValue4 + wildMultiplayerScreenUiState15.floatValue6 * 0.5F + 12.0F * f,
         22.0F * f,
         "IP: " + text6,
         this.compute4((0.4F + wildMultiplayerScreenUiState15.floatValue9 * 0.18F) * floatValue85)
      );
      if (wildMultiplayerScreenUiState15.flag3 && serverInfo6 != null) {
         this.invoke31(renderManager2, wildMultiplayerScreenUiState15, serverInfo6, f, floatValue85);
      }
   }

   private void invoke30(RenderManager renderManager3, WildMultiplayerScreen.WildMultiplayerScreenUiState wildMultiplayerScreenUiState16, float f) {
      float floatValue98 = wildMultiplayerScreenUiState16.floatValue18 * (wildMultiplayerScreenUiState16.flag3 ? 0.88F : 0.28F);
      float floatValue99 = wildMultiplayerScreenUiState16.floatValue3 + wildMultiplayerScreenUiState16.floatValue5 * 0.5F;
      float floatValue100 = wildMultiplayerScreenUiState16.floatValue4 + wildMultiplayerScreenUiState16.floatValue6 * 0.5F;
      renderManager3.invoke70(FontRegistry.fontObject, floatValue99, floatValue100 + 4.0F * f, 26.0F * f, wildMultiplayerScreenUiState16.text, this.compute3(floatValue98), "c");
   }

   private void invoke31(RenderManager renderManager4, WildMultiplayerScreen.WildMultiplayerScreenUiState wildMultiplayerScreenUiState17, ServerInfo serverInfo, float f, float g) {
      String text7 = this.resolve4(serverInfo);
      float floatValue101 = 24.0F * f;
      float floatValue102 = RenderManager.resolve7(FontRegistry.fontObject, text7, 24.0F * f).floatValue;
      float floatValue103 = Math.max(48.0F * f, floatValue102 + 16.0F * f);
      float floatValue104 = wildMultiplayerScreenUiState17.floatValue3 + wildMultiplayerScreenUiState17.floatValue5 - 24.0F * f;
      float floatValue105 = floatValue104 - floatValue103;
      float floatValue106 = wildMultiplayerScreenUiState17.floatValue4 + wildMultiplayerScreenUiState17.floatValue6 * 0.5F - floatValue101 * 0.5F;
      renderManager4.invoke5(
         floatValue105,
         floatValue106,
         floatValue103,
         floatValue101,
         floatValue101 * 0.45F,
         this.flag4
            ? compute5(1.0F, 1.0F, 1.0F, (0.54F + wildMultiplayerScreenUiState17.floatValue9 * 0.12F) * g)
            : compute5(0.018F, 0.022F, 0.028F, (0.44F + wildMultiplayerScreenUiState17.floatValue9 * 0.1F) * g)
      );
      renderManager4.invoke70(
         FontRegistry.fontObject,
         floatValue105 + floatValue103 * 0.5F,
         floatValue106 + floatValue101 * 0.66F,
         24.0F * f,
         text7,
         this.compute(serverInfo, (0.72F + wildMultiplayerScreenUiState17.floatValue9 * 0.18F) * g),
         "c"
      );
   }

   private String resolve3(ServerInfo serverInfo) {
      if (serverInfo.getStatus() == Status.PINGING) {
         return "Pinging server...";
      } else if (serverInfo.getStatus() == Status.UNREACHABLE) {
         return serverInfo.label == null ? "Server is offline" : serverInfo.label.getString();
      } else if (serverInfo.getStatus() == Status.INCOMPATIBLE && serverInfo.version != null) {
         return "Version: " + serverInfo.version.getString();
      } else {
         return serverInfo.label != null && !serverInfo.label.getString().isBlank() ? serverInfo.label.getString().replace('\n', ' ') : "Waiting for response";
      }
   }

   private String resolve4(ServerInfo serverInfo) {
      if (serverInfo.getStatus() == Status.PINGING) {
         return this.resolve5();
      } else {
         String text8 = this.resolve6(serverInfo.playerCountLabel);
         if (serverInfo.players == null || serverInfo.players.max() <= 0 && serverInfo.players.online() <= 0) {
            if (this.check2(text8)) {
               return text8;
            } else {
               return serverInfo.players != null ? serverInfo.players.online() + "/" + serverInfo.players.max() : "-";
            }
         } else {
            return serverInfo.players.online() + "/" + serverInfo.players.max();
         }
      }
   }

   private String resolve5() {
      int intValue30 = 1 + (int)(this.floatValue * 6.0F) % 3;
      return ".".repeat(intValue30);
   }

   private String resolve6(Text text) {
      if (text == null) {
         return "";
      } else {
         String text9 = text.getString();
         StringBuilder stringBuilder = null;
         boolean flag4 = false;
         int intValue31 = 0;
         int intValue32 = text9.length();

         while (intValue31 < intValue32 && Character.isWhitespace(text9.charAt(intValue31))) {
            intValue31++;
         }

         while (intValue32 > intValue31 && Character.isWhitespace(text9.charAt(intValue32 - 1))) {
            intValue32--;
         }

         for (int intValue33 = intValue31; intValue33 < intValue32; intValue33++) {
            char character2 = text9.charAt(intValue33);
            boolean flag5 = Character.isWhitespace(character2);
            if (flag5) {
               if (!flag4) {
                  if (stringBuilder == null) {
                     stringBuilder = new StringBuilder(text9.length());
                     stringBuilder.append(text9, intValue31, intValue33);
                  }

                  stringBuilder.append(' ');
                  flag4 = true;
               }
            } else {
               if (stringBuilder != null) {
                  stringBuilder.append(character2);
               }

               flag4 = false;
            }
         }

         return stringBuilder == null ? text9.substring(intValue31, intValue32) : stringBuilder.toString();
      }
   }

   private boolean check2(String string) {
      if (string != null && !string.isBlank()) {
         String text10 = string.trim();
         return !text10.equals("-") && !text10.equals("?") && !text10.equals("???") && !text10.equals("...");
      } else {
         return false;
      }
   }

   private String resolve7(ServerInfo serverInfo) {
      if (serverInfo.getStatus() == Status.PINGING) {
         return "ping";
      } else if (serverInfo.ping >= 0L) {
         return serverInfo.ping + " ms";
      } else {
         return serverInfo.getStatus() == Status.UNREACHABLE ? "offline" : "-";
      }
   }

   private int compute(ServerInfo serverInfo, float f) {
      return switch (serverInfo.getStatus()) {
         case SUCCESSFUL -> compute6(this.intValue4, this.intValue3, 0.35F + 0.25F * (float)Math.sin(this.floatValue * 1.6F), 0.82F * f);
         case PINGING -> compute5(0.68F, 0.76F, 0.84F, 0.62F * f);
         case INCOMPATIBLE -> compute5(1.0F, 0.7F, 0.36F, 0.72F * f);
         case UNREACHABLE -> compute5(1.0F, 0.32F, 0.36F, 0.72F * f);
         case INITIAL -> compute5(0.58F, 0.64F, 0.7F, 0.54F * f);
         default -> throw new MatchException(null, null);
      };
   }

   private void invoke32(WildMultiplayerScreen.WildMultiplayerScreenState wildMultiplayerScreenState) {
      MinecraftClient client3 = this.client == null ? MinecraftClient.getInstance() : this.client;
      if (client3 != null) {
         switch (wildMultiplayerScreenState) {
            case SERVER:
            default:
               break;
            case JOIN:
               client3.execute(this::invoke33);
               break;
            case DIRECT:
               client3.execute(() -> this.invoke34(client3));
               break;
            case ADD:
               client3.execute(() -> this.invoke35(client3));
               break;
            case EDIT:
               client3.execute(() -> this.invoke36(client3));
               break;
            case DELETE:
               client3.execute(() -> this.invoke37(client3));
               break;
            case PROXY:
               client3.execute(() -> client3.setScreen(new ProxyScreen(this)));
               break;
            case REFRESH:
               this.invoke18();
               break;
            case BACK:
               client3.execute(() -> client3.setScreen(this.screen));
         }
      }
   }

   private void invoke33() {
      MinecraftClient client4 = this.client == null ? MinecraftClient.getInstance() : this.client;
      ServerInfo serverInfo7 = this.resolve8();
      if (client4 != null && serverInfo7 != null && serverInfo7.address != null && !serverInfo7.address.isBlank()) {
         this.chooseAServer = "Resolving address...";
         CompletableFuture.<ServerAddress>supplyAsync(() -> ServerAddress.parse(serverInfo7.address), Util.getMainWorkerExecutor())
            .whenComplete((serverAddress, throwable) -> client4.execute(() -> {
               if (throwable == null && serverAddress != null) {
                  ConnectScreen.connect(this, client4, serverAddress, serverInfo7, false, null);
               } else {
                  this.chooseAServer = "Invalid server address";
               }
            }));
      } else {
         this.chooseAServer = "Select a server";
      }
   }

   private void invoke34(MinecraftClient minecraftClient) {
      ServerInfo serverInfo8 = new ServerInfo("Direct Server", "", ServerType.OTHER);
      minecraftClient.setScreen(
         new DirectConnectScreen(
            this,
            bl -> {
               if (bl) {
                  this.chooseAServer = "Resolving address...";
                  CompletableFuture.<ServerAddress>supplyAsync(() -> ServerAddress.parse(serverInfo8.address), Util.getMainWorkerExecutor())
                     .whenComplete((serverAddress, throwable) -> minecraftClient.execute(() -> {
                        if (throwable == null && serverAddress != null) {
                           ConnectScreen.connect(this, minecraftClient, serverAddress, serverInfo8, false, null);
                        } else {
                           this.chooseAServer = "Invalid server address";
                           minecraftClient.setScreen(this);
                        }
                     }));
               } else {
                  minecraftClient.setScreen(this);
               }
            },
            serverInfo8
         )
      );
   }

   private void invoke35(MinecraftClient minecraftClient) {
      ServerInfo serverInfo9 = new ServerInfo("Minecraft Server", "", ServerType.OTHER);
      minecraftClient.setScreen(new AddServerScreen(this, bl -> {
         if (bl && this.serverList != null) {
            try {
               this.serverList.add(serverInfo9, false);
               this.items.add(serverInfo9);
               this.invoke6();
               this.intValue5 = this.serverList.size() - 1;
               this.chooseAServer = "Server added";
            } catch (Throwable exception8) {
               this.chooseAServer = "Failed to add server";
            }
         }

         minecraftClient.setScreen(this);
      }, serverInfo9));
   }

   private void invoke36(MinecraftClient minecraftClient) {
      ServerInfo serverInfo10 = this.resolve8();
      if (serverInfo10 != null && this.serverList != null && this.intValue5 >= 0 && this.intValue5 < this.serverList.size()) {
         int intValue34 = this.intValue5;
         ServerInfo serverInfo11 = new ServerInfo(serverInfo10.name, serverInfo10.address, serverInfo10.getServerType());
         serverInfo11.copyWithSettingsFrom(serverInfo10);
         minecraftClient.setScreen(new AddServerScreen(this, bl -> {
            if (bl && this.serverList != null && intValue34 >= 0 && intValue34 < this.serverList.size()) {
               try {
                  this.serverList.set(intValue34, serverInfo11);
                  if (intValue34 < this.items.size()) {
                     this.items.set(intValue34, serverInfo11);
                  }

                  this.invoke6();
                  this.intValue5 = intValue34;
                  this.chooseAServer = "Server updated";
               } catch (Throwable exception9) {
                  this.chooseAServer = "Failed to save changes";
               }
            }

            minecraftClient.setScreen(this);
         }, serverInfo11));
      } else {
         this.chooseAServer = "Select a server";
      }
   }

   private void invoke37(MinecraftClient minecraftClient) {
      ServerInfo serverInfo12 = this.resolve8();
      if (serverInfo12 != null && this.serverList != null) {
         String text11 = resolve12(serverInfo12.name, "Unnamed server");
         minecraftClient.setScreen(new ConfirmScreen(bl -> {
            if (bl && this.serverList != null) {
               try {
                  this.serverList.remove(serverInfo12);
                  this.items.remove(serverInfo12);
                  this.invoke6();
                  this.intValue5 = Math.min(this.intValue5, Math.max(0, this.serverList.size() - 1));
                  if (this.serverList.size() == 0) {
                     this.intValue5 = -1;
                  }

                  this.chooseAServer = "Server deleted";
               } catch (Throwable exception10) {
                  this.chooseAServer = "Failed to delete server";
               }
            }

            minecraftClient.setScreen(this);
         }, Text.literal("Delete server?"), Text.literal(text11)));
      } else {
         this.chooseAServer = "Select a server";
      }
   }

   private ServerInfo resolve8() {
      return this.intValue5 >= 0 && this.intValue5 < this.items.size() ? this.items.get(this.intValue5) : null;
   }

   private void invoke38() {
      ServerInfo serverInfo13 = this.resolve8();
      if (serverInfo13 != null && serverInfo13.address != null && !serverInfo13.address.isBlank()) {
         MinecraftClient client5 = this.client == null ? MinecraftClient.getInstance() : this.client;
         if (client5 != null && client5.keyboard != null) {
            client5.keyboard.setClipboard(serverInfo13.address);
            this.chooseAServer = "IP copied: " + serverInfo13.address;
         }
      } else {
         this.chooseAServer = "Select a server";
      }
   }

   private void invoke39(int i) {
      if (this.serverList != null && this.intValue5 >= 0 && this.intValue5 < this.items.size()) {
         int intValue35 = this.intValue5 + i;
         if (intValue35 >= 0 && intValue35 < this.items.size() && intValue35 < this.serverList.size()) {
            try {
               ServerInfo serverInfo14 = this.serverList.get(this.intValue5);
               ServerInfo serverInfo15 = this.serverList.get(intValue35);
               this.serverList.set(this.intValue5, serverInfo15);
               this.serverList.set(intValue35, serverInfo14);
               Collections.swap(this.items, this.intValue5, intValue35);
               this.invoke6();
               this.intValue5 = intValue35;
               this.chooseAServer = "Server moved";
               this.invoke41();
            } catch (Throwable exception11) {
               this.chooseAServer = "Failed to move server";
            }
         }
      } else {
         this.chooseAServer = "Select a server";
      }
   }

   private boolean check3(WildMultiplayerScreen.WildMultiplayerScreenState wildMultiplayerScreenState3) {
      boolean flag6 = this.resolve8() != null;

      return switch (wildMultiplayerScreenState3) {
         case SERVER -> false;
         case JOIN, EDIT, DELETE -> flag6;
         case DIRECT, ADD, PROXY, REFRESH, BACK -> true;
      };
   }

   private void invoke40(int i) {
      if (this.items.isEmpty()) {
         this.intValue5 = -1;
         this.chooseAServer = "No saved servers";
      } else {
         this.intValue5 = compute2(this.intValue5 + i, 0, this.items.size() - 1);
         this.chooseAServer = "Ready";
         this.invoke41();
      }
   }

   private void invoke41() {
      if (this.intValue5 >= 0) {
         if (this.intValue5 < this.floatValue12) {
            this.floatValue12 = this.intValue5;
         }

         if (this.intValue5 > this.floatValue12 + this.intValue6 - 1.0F) {
            this.floatValue12 = this.intValue5 - this.intValue6 + 1;
         }

         int intValue36 = Math.max(0, this.items.size() - Math.max(1, this.intValue6));
         this.floatValue12 = measure8(this.floatValue12, 0.0F, (float)intValue36);
      }
   }

   private WildMultiplayerScreen.WildMultiplayerScreenResources resolve9(ServerInfo serverInfo) {
      byte[] byteValues = serverInfo.getFavicon();
      if (byteValues != null && byteValues.length != 0) {
         String text12 = this.resolve11(serverInfo, byteValues);
         WildMultiplayerScreen.WildMultiplayerScreenResources wildMultiplayerScreenResources2 = this.valuesByKey.get(text12);
         if (wildMultiplayerScreenResources2 != null) {
            return wildMultiplayerScreenResources2;
         } else {
            try {
               NativeImage nativeImage = NativeImage.read(byteValues);
               NativeImageBackedTexture nativeImageBackedTexture2 = new NativeImageBackedTexture(() -> "wild_server_icon", nativeImage);
               nativeImageBackedTexture2.setFilter(true, false);
               nativeImageBackedTexture2.upload();
               WildMultiplayerScreen.WildMultiplayerScreenResources wildMultiplayerScreenResources3 = new WildMultiplayerScreen.WildMultiplayerScreenResources(nativeImageBackedTexture2);
               this.valuesByKey.put(text12, wildMultiplayerScreenResources3);
               return wildMultiplayerScreenResources3;
            } catch (Throwable exception12) {
               return null;
            }
         }
      } else {
         return null;
      }
   }

   private WildMultiplayerScreen.WildMultiplayerScreenResources resolve10(ServerInfo serverInfo) {
      byte[] byteValues2 = serverInfo.getFavicon();
      return byteValues2 != null && byteValues2.length != 0 ? this.valuesByKey.get(this.resolve11(serverInfo, byteValues2)) : null;
   }

   private boolean check4() {
      return System.nanoTime() - this.timestamp5 < 180000000L || Math.abs(this.floatValue12 - this.floatValue13) > 0.06F;
   }

   private String resolve11(ServerInfo serverInfo, byte[] bs) {
      return resolve12(serverInfo.address, "") + ":" + Arrays.hashCode(bs);
   }

   private void invoke42() {
      for (WildMultiplayerScreen.WildMultiplayerScreenResources wildMultiplayerScreenResources4 : this.valuesByKey.values()) {
         wildMultiplayerScreenResources4.close();
      }

      this.valuesByKey.clear();
   }

   private float measure2(Window window, double d) {
      return (float)(d * window.getFramebufferWidth() / Math.max(1.0, (double)window.getScaledWidth()));
   }

   private float measure3(Window window, double d) {
      return (float)(d * window.getFramebufferHeight() / Math.max(1.0, (double)window.getScaledHeight()));
   }

   private static String resolve12(String string, String string2) {
      return string != null && !string.isBlank() ? string : string2;
   }

   private static String resolve13(String string, float f, float g, FontObject fontObject) {
      if (string == null) {
         return "";
      } else if (f <= 0.0F) {
         return "";
      } else if (RenderManager.resolve7(fontObject, string, g).floatValue <= f) {
         return string;
      } else {
         String text13 = "...";
         if (RenderManager.resolve7(fontObject, text13, g).floatValue > f) {
            return "";
         } else {
            int intValue37 = 1;
            int intValue38 = string.length();
            int intValue39 = 1;

            while (intValue37 <= intValue38) {
               int intValue40 = intValue37 + intValue38 >>> 1;
               if (RenderManager.resolve7(fontObject, string.substring(0, intValue40) + text13, g).floatValue <= f) {
                  intValue39 = intValue40;
                  intValue37 = intValue40 + 1;
               } else {
                  intValue38 = intValue40 - 1;
               }
            }

            return string.substring(0, intValue39) + text13;
         }
      }
   }

   private static float measure4(float f, float g) {
      return measure8(Math.min(f / 1920.0F, g / 1080.0F) * 1.08F, 0.62F, 1.2F);
   }

   static float measure5(float f, float g, float h, float i, float j, float k, float l) {
      float floatValue107 = h + j * 0.5F;
      float floatValue108 = i + k * 0.5F;
      float floatValue109 = j * 0.5F - l;
      float floatValue110 = k * 0.5F - l;
      float floatValue111 = Math.abs(f - floatValue107) - floatValue109;
      float floatValue112 = Math.abs(g - floatValue108) - floatValue110;
      float floatValue113 = Math.max(floatValue111, 0.0F);
      float floatValue114 = Math.max(floatValue112, 0.0F);
      return (float)Math.sqrt(floatValue113 * floatValue113 + floatValue114 * floatValue114) + Math.min(Math.max(floatValue111, floatValue112), 0.0F) - l;
   }

   private static float measure6(float f, float g) {
      return (float)Math.sqrt(f * f + g * g);
   }

   private static float measure7(float f) {
      float floatValue115 = measure8(f, 0.0F, 1.0F);
      return floatValue115 * floatValue115 * floatValue115 * (floatValue115 * (floatValue115 * 6.0F - 15.0F) + 10.0F);
   }

   private static float measure8(float f, float g, float h) {
      return Math.max(g, Math.min(h, f));
   }

   private static int compute2(int i, int j, int k) {
      return Math.max(j, Math.min(k, i));
   }

   private static float measure9(int i) {
      return (i >> 16 & 0xFF) / 255.0F;
   }

   private static float measure10(int i) {
      return (i >> 8 & 0xFF) / 255.0F;
   }

   private static float measure11(int i) {
      return (i & 0xFF) / 255.0F;
   }

   private int compute3(float f) {
      return this.flag4 ? compute5(0.1F, 0.1F, 0.1F, f) : compute5(1.0F, 1.0F, 1.0F, f);
   }

   private int compute4(float f) {
      return this.flag4 ? compute5(0.4F, 0.4F, 0.4F, f) : compute5(0.78F, 0.84F, 0.88F, f);
   }

   private static int compute5(float f, float g, float h, float i) {
      int intValue41 = Math.round(measure8(f, 0.0F, 1.0F) * 255.0F);
      int intValue42 = Math.round(measure8(g, 0.0F, 1.0F) * 255.0F);
      int intValue43 = Math.round(measure8(h, 0.0F, 1.0F) * 255.0F);
      int intValue44 = Math.round(measure8(i, 0.0F, 1.0F) * 255.0F);
      return intValue44 << 24 | intValue41 << 16 | intValue42 << 8 | intValue43;
   }

   private static int compute6(int i, int j, float f, float g) {
      float floatValue116 = measure8(f, 0.0F, 1.0F);
      int intValue45 = ColorUtils.compute16(i, j, floatValue116);
      int intValue46 = Math.round(measure8(g, 0.0F, 1.0F) * 255.0F);
      return intValue46 << 24 | intValue45;
   }

   static enum WildMultiplayerScreenState {
      SERVER,
      JOIN,
      DIRECT,
      ADD,
      EDIT,
      DELETE,
      PROXY,
      REFRESH,
      BACK;
   }

   static final class WildMultiplayerScreenResources implements AutoCloseable {
      private final NativeImageBackedTexture nativeImageBackedTexture;

      WildMultiplayerScreenResources(NativeImageBackedTexture nativeImageBackedTexture) {
         this.nativeImageBackedTexture = nativeImageBackedTexture;
      }

      int compute() {
         return this.nativeImageBackedTexture.getGlTexture() instanceof GlTexture glTexture ? glTexture.getGlId() : 0;
      }

      @Override
      public void close() {
         this.nativeImageBackedTexture.close();
      }
   }

   static final class WildMultiplayerScreenUiState {
      String text;
      String text2 = "";
      final WildMultiplayerScreen.WildMultiplayerScreenState wildMultiplayerScreenState;
      final SpringIntegrator springIntegrator = new SpringIntegrator(SpringSpec.resolve6());
      float floatValue;
      float floatValue2;
      float floatValue3;
      float floatValue4;
      float floatValue5;
      float floatValue6;
      float floatValue7;
      float floatValue8;
      float floatValue9;
      float floatValue10;
      float floatValue11;
      float floatValue12;
      float floatValue13 = 1.0F;
      float floatValue14 = 0.5F;
      float floatValue15 = 0.5F;
      float floatValue16;
      float floatValue17;
      float floatValue18;
      int intValue = -1;
      boolean flag;
      boolean flag2;
      boolean flag3 = true;

      WildMultiplayerScreenUiState(String string, WildMultiplayerScreen.WildMultiplayerScreenState wildMultiplayerScreenState4) {
         this.text = string;
         this.wildMultiplayerScreenState = wildMultiplayerScreenState4;
      }

      void invoke() {
         this.floatValue8 = 0.0F;
         this.floatValue9 = 0.0F;
         this.floatValue10 = 0.0F;
         this.floatValue11 = 0.0F;
         this.floatValue12 = 0.0F;
         this.floatValue13 = 1.0F;
         this.floatValue14 = 0.5F;
         this.floatValue15 = 0.5F;
         this.floatValue16 = 0.0F;
         this.floatValue18 = 0.0F;
         this.flag = false;
         this.flag2 = false;
         this.flag3 = true;
         this.springIntegrator.setFloatValue(1.0F);
      }

      boolean check(float f, float g) {
         return WildMultiplayerScreen.measure5(f, g, this.floatValue, this.floatValue2, this.floatValue5, this.floatValue6, this.floatValue7)
            <= 0.0F;
      }
   }

   static final class WildMultiplayerScreenState2 {
      float floatValue;
      float floatValue2;
      float floatValue3 = -100.0F;
      float floatValue4;
   }
}
