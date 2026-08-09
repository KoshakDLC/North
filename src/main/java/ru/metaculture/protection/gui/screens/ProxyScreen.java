package ru.metaculture.protection;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;
import org.lwjgl.opengl.GL11;

public final class ProxyScreen extends Screen implements BackdropScreen {
   private static final ThemePalette THEME_PALETTE = ThemePalette.resolve2();
   private static final int INT_VALUE = 14;
   private final Screen screen;
   private final MainMenuRenderer mainMenuRenderer = new MainMenuRenderer();
   private final ProxyScreen.ProxyScreenUiState2 proxyScreenUiState2 = new ProxyScreen.ProxyScreenUiState2("Host", false, 255, ProxyScreen.ProxyScreenState2.HOST);
   private final ProxyScreen.ProxyScreenUiState2 proxyScreenUiState22 = new ProxyScreen.ProxyScreenUiState2("Port", false, 5, ProxyScreen.ProxyScreenState2.PORT);
   private final ProxyScreen.ProxyScreenUiState2 proxyScreenUiState23 = new ProxyScreen.ProxyScreenUiState2("Username", false, 128, ProxyScreen.ProxyScreenState2.TEXT);
   private final ProxyScreen.ProxyScreenUiState2 proxyScreenUiState24 = new ProxyScreen.ProxyScreenUiState2("Password", true, 256, ProxyScreen.ProxyScreenState2.SECRET);
   private final List<ProxyScreen.ProxyScreenUiState2> items = List.of(this.proxyScreenUiState2, this.proxyScreenUiState22, this.proxyScreenUiState23, this.proxyScreenUiState24);
   private final List<ProxyScreen.ProxyScreenUiState> items2 = List.of(
      new ProxyScreen.ProxyScreenUiState("Socks5", ProxyScreen.ProxyScreenState.TYPE),
      new ProxyScreen.ProxyScreenUiState("Enabled", ProxyScreen.ProxyScreenState.ENABLED),
      new ProxyScreen.ProxyScreenUiState("Paste", ProxyScreen.ProxyScreenState.PASTE),
      new ProxyScreen.ProxyScreenUiState("Test", ProxyScreen.ProxyScreenState.TEST),
      new ProxyScreen.ProxyScreenUiState("Save", ProxyScreen.ProxyScreenState.SAVE),
      new ProxyScreen.ProxyScreenUiState("Back", ProxyScreen.ProxyScreenState.BACK)
   );
   private final ProxyScreen.ProxyScreenState4[] proxyScreenState4s = new ProxyScreen.ProxyScreenState4[14];
   private final SpringIntegrator springIntegrator = new SpringIntegrator(SpringSpec.resolve6());
   private final SpringIntegrator springIntegrator2 = new SpringIntegrator(SpringSpec.resolve6());
   private long timestamp;
   private long timestamp2;
   private long timestamp3;
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
   private boolean flag5;
   private boolean flag6;
   private String socks5 = "Socks5";
   private String proxyDisabled = "Proxy disabled";
   private int intValue5;

   public ProxyScreen(Screen screen) {
      super(Text.literal("Proxy"));
      this.screen = screen;

      for (int intValue = 0; intValue < this.proxyScreenState4s.length; intValue++) {
         this.proxyScreenState4s[intValue] = new ProxyScreen.ProxyScreenState4();
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
      this.springIntegrator.setFloatValue(0.0F);
      this.springIntegrator2.setFloatValue(0.0F);
      if (!this.flag5) {
         ProxyManager.ProxyManagerEntry proxyManagerEntry = ProxyManager.resolve();
         this.flag6 = proxyManagerEntry.enabled();
         this.socks5 = ProxyManager.resolve7(proxyManagerEntry.type());
         this.proxyScreenUiState2.text = proxyManagerEntry.host();
         this.proxyScreenUiState22.text = proxyManagerEntry.port();
         this.proxyScreenUiState23.text = proxyManagerEntry.username();
         this.proxyScreenUiState24.text = proxyManagerEntry.password();

         for (ProxyScreen.ProxyScreenUiState2 proxyScreenUiState2 : this.items) {
            proxyScreenUiState2.intValue2 = proxyScreenUiState2.text.length();
         }

         this.proxyDisabled = this.flag6 ? "Proxy enabled" : "Proxy disabled";
         this.flag5 = true;
      }

      for (ProxyScreen.ProxyScreenUiState2 proxyScreenUiState22 : this.items) {
         proxyScreenUiState22.invoke6();
      }

      for (ProxyScreen.ProxyScreenUiState proxyScreenUiState : this.items2) {
         proxyScreenUiState.invoke();
      }
   }

   public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
      this.invoke(mouseX, mouseY, deltaTicks, false);
   }

   @Override
   public void invoke2(int i, int j, float f) {
      this.invoke(i, j, f, true);
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

         this.invoke11();
         this.invoke12(window2, i, j, floatValue, longValue);
         this.invoke13(intValue2, intValue3, floatValue);
         this.invoke14();
         float floatValue2 = (this.floatValue2 / Math.max(1.0F, (float)intValue2) - 0.5F) * 2.0F;
         float floatValue3 = (this.floatValue3 / Math.max(1.0F, (float)intValue3) - 0.5F) * 2.0F;
         float floatValue4 = this.springIntegrator.measure(floatValue2, floatValue);
         float floatValue5 = this.springIntegrator2.measure(floatValue3, floatValue);
         this.invoke17(intValue2, intValue3, floatValue4, floatValue5, floatValue);
         int intValue4 = GL11.glGetInteger(36006);
         MainMenuScreen.MainMenuScreenTimedEntry mainMenuScreenTimedEntry = this.resolve3(intValue2, intValue3, intValue4, floatValue4, floatValue5, longValue);
         if (bl) {
            FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();

            try {
               this.mainMenuRenderer.check(mainMenuScreenTimedEntry);
            } finally {
               FramebufferUtils.restoreGlState(glStateSnapshot);
            }

            this.invoke20(mainMenuScreenTimedEntry);
         }
      }
   }

   public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
   }

   public void renderInGameBackground(DrawContext context) {
   }

   public boolean shouldPause() {
      return false;
   }

   public boolean shouldCloseOnEsc() {
      return false;
   }

   public void close() {
      this.invoke3(ProxyScreen.ProxyScreenState.BACK);
   }

   public void removed() {
      this.intValue5++;
      this.mainMenuRenderer.close();
      super.removed();
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 0 && this.client != null && this.client.getWindow() != null) {
         float floatValue6 = this.measure(this.client.getWindow(), mouseX);
         float floatValue7 = this.measure2(this.client.getWindow(), mouseY);

         for (ProxyScreen.ProxyScreenUiState2 proxyScreenUiState23 : this.items) {
            if (proxyScreenUiState23.check(floatValue6, floatValue7)) {
               this.invoke8(proxyScreenUiState23);
               proxyScreenUiState23.floatValue10 = 1.0F;
               return true;
            }
         }

         this.invoke10();

         for (ProxyScreen.ProxyScreenUiState proxyScreenUiState3 : this.items2) {
            if (proxyScreenUiState3.check(floatValue6, floatValue7)) {
               proxyScreenUiState3.floatValue10 = 1.0F;
               proxyScreenUiState3.floatValue11 = 1.0F;
               this.invoke3(proxyScreenUiState3.proxyScreenState);
               return true;
            }
         }

         return true;
      } else {
         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   public boolean charTyped(char chr, int modifiers) {
      ProxyScreen.ProxyScreenUiState2 proxyScreenUiState24 = this.resolve2();
      if (proxyScreenUiState24 == null) {
         return super.charTyped(chr, modifiers);
      } else {
         if (chr >= ' ' && chr != 127) {
            proxyScreenUiState24.invoke2(String.valueOf(chr));
         }

         return true;
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      boolean flag = (modifiers & 2) != 0 || (modifiers & 8) != 0;
      ProxyScreen.ProxyScreenUiState2 proxyScreenUiState25 = this.resolve2();
      if (keyCode == 256) {
         if (proxyScreenUiState25 != null) {
            this.invoke10();
            return true;
         } else {
            this.invoke3(ProxyScreen.ProxyScreenState.BACK);
            return true;
         }
      } else if (keyCode == 258) {
         this.invoke9((modifiers & 1) != 0 ? -1 : 1);
         return true;
      } else if (proxyScreenUiState25 != null) {
         if (flag) {
            if (keyCode == 65) {
               proxyScreenUiState25.flag3 = true;
               return true;
            }

            if (keyCode == 67) {
               if (this.client != null && this.client.keyboard != null && proxyScreenUiState25.flag3) {
                  this.client.keyboard.setClipboard(proxyScreenUiState25.text);
               }

               return true;
            }

            if (keyCode == 86) {
               if (this.client != null && this.client.keyboard != null) {
                  proxyScreenUiState25.invoke2(this.client.keyboard.getClipboard());
               }

               return true;
            }
         }

         if (keyCode == 259) {
            proxyScreenUiState25.invoke3();
            return true;
         } else if (keyCode == 261) {
            proxyScreenUiState25.invoke4();
            return true;
         } else if (keyCode == 263) {
            proxyScreenUiState25.flag3 = false;
            proxyScreenUiState25.intValue2 = compute3(proxyScreenUiState25.intValue2 - 1, 0, proxyScreenUiState25.text.length());
            return true;
         } else if (keyCode == 262) {
            proxyScreenUiState25.flag3 = false;
            proxyScreenUiState25.intValue2 = compute3(proxyScreenUiState25.intValue2 + 1, 0, proxyScreenUiState25.text.length());
            return true;
         } else if (keyCode == 268) {
            proxyScreenUiState25.flag3 = false;
            proxyScreenUiState25.intValue2 = 0;
            return true;
         } else if (keyCode == 269) {
            proxyScreenUiState25.flag3 = false;
            proxyScreenUiState25.intValue2 = proxyScreenUiState25.text.length();
            return true;
         } else if (keyCode != 257 && keyCode != 335) {
            return true;
         } else {
            this.invoke3(ProxyScreen.ProxyScreenState.SAVE);
            return true;
         }
      } else if (flag && keyCode == 86) {
         this.invoke3(ProxyScreen.ProxyScreenState.PASTE);
         return true;
      } else if (keyCode != 257 && keyCode != 335) {
         return super.keyPressed(keyCode, scanCode, modifiers);
      } else {
         this.invoke3(ProxyScreen.ProxyScreenState.SAVE);
         return true;
      }
   }

   private void invoke3(ProxyScreen.ProxyScreenState proxyScreenState) {
      MinecraftClient client = this.client == null ? MinecraftClient.getInstance() : this.client;
      if (client != null) {
         switch (proxyScreenState) {
            case TYPE:
               this.socks5 = "Socks5".equals(this.socks5) ? "Socks4" : "Socks5";
               this.proxyDisabled = this.socks5 + " selected";
               break;
            case ENABLED:
               this.flag6 = !this.flag6;
               if (this.flag6) {
                  this.proxyDisabled = "Proxy enabled";
               } else {
                  ProxyManager.invoke3(this.resolve(false));
                  this.proxyDisabled = "Proxy disabled";
               }
               break;
            case PASTE:
               this.invoke4(client);
               break;
            case TEST:
               this.invoke5(client);
               break;
            case SAVE:
               this.invoke6(false);
               break;
            case BACK:
               client.execute(() -> client.setScreen(this.screen));
         }
      }
   }

   private void invoke4(MinecraftClient minecraftClient) {
      String text = "";

      try {
         text = minecraftClient.keyboard == null ? "" : minecraftClient.keyboard.getClipboard();
      } catch (Throwable exception) {
      }

      ProxyManager.ProxyManagerEntry2 proxyManagerEntry2 = ProxyManager.resolve6(text);
      if (!proxyManagerEntry2.host().isBlank() && !proxyManagerEntry2.port().isBlank()) {
         this.socks5 = ProxyManager.resolve7(proxyManagerEntry2.type());
         this.proxyScreenUiState2.invoke(proxyManagerEntry2.host());
         this.proxyScreenUiState22.invoke(proxyManagerEntry2.port());
         this.proxyScreenUiState23.invoke(proxyManagerEntry2.username());
         this.proxyScreenUiState24.invoke(proxyManagerEntry2.password());
         this.flag6 = true;
         this.invoke10();
         this.proxyDisabled = "Proxy imported";
      } else {
         this.proxyDisabled = "Clipboard has no proxy";
      }
   }

   private void invoke5(MinecraftClient minecraftClient) {
      this.invoke7();
      ProxyManager.ProxyManagerEntry proxyManagerEntry3 = this.resolve(true);
      String text2 = ProxyManager.resolve5(proxyManagerEntry3, true);
      if (text2 != null) {
         this.proxyDisabled = text2;
      } else {
         int intValue5 = ++this.intValue5;
         this.proxyDisabled = "Checking proxy...";
         ProxyManager.resolve4(proxyManagerEntry3, "mc.funtime.su", 25565, 8000).whenComplete((proxyManagerResult, throwable) -> minecraftClient.execute(() -> {
            if (intValue5 == this.intValue5) {
               if (throwable != null) {
                  this.proxyDisabled = "Proxy failed: " + throwable.getClass().getSimpleName();
               } else {
                  if (proxyManagerResult.success()) {
                     this.flag6 = true;
                     ProxyManager.invoke3(proxyManagerEntry3);
                     this.proxyDisabled = "Proxy OK and enabled: " + proxyManagerResult.millis() + " ms";
                  } else {
                     this.proxyDisabled = "Proxy failed: " + proxyManagerResult.message();
                  }
               }
            }
         }));
      }
   }

   private void invoke6(boolean bl) {
      this.invoke7();
      ProxyManager.ProxyManagerEntry proxyManagerEntry4 = this.resolve(true);
      String text3 = ProxyManager.resolve5(proxyManagerEntry4, true);
      if (text3 != null) {
         this.proxyDisabled = text3;
      } else {
         this.flag6 = true;
         ProxyManager.invoke3(proxyManagerEntry4);
         this.proxyDisabled = "Proxy saved and enabled";
         if (bl && this.client != null) {
            this.client.setScreen(this.screen);
         }
      }
   }

   private void invoke7() {
      String text4 = this.proxyScreenUiState2.text;
      ProxyManager.ProxyManagerEntry2 proxyManagerEntry22 = ProxyManager.resolve6(text4);
      if (!proxyManagerEntry22.host().isBlank()) {
         this.proxyScreenUiState2.invoke(proxyManagerEntry22.host());
         if (!proxyManagerEntry22.port().isBlank()) {
            this.proxyScreenUiState22.invoke(proxyManagerEntry22.port());
         }

         if (!proxyManagerEntry22.username().isBlank()) {
            this.proxyScreenUiState23.invoke(proxyManagerEntry22.username());
         }

         if (!proxyManagerEntry22.password().isBlank()) {
            this.proxyScreenUiState24.invoke(proxyManagerEntry22.password());
         }

         this.socks5 = ProxyManager.resolve7(proxyManagerEntry22.type());
      } else {
         ProxyManager.ProxyManagerEntry2 proxyManagerEntry23 = ProxyManager.resolve6(this.proxyScreenUiState2.text + ":" + this.proxyScreenUiState22.text);
         if (!proxyManagerEntry23.host().isBlank()) {
            this.proxyScreenUiState2.invoke(proxyManagerEntry23.host());
         }
      }
   }

   private ProxyManager.ProxyManagerEntry resolve(boolean bl) {
      return new ProxyManager.ProxyManagerEntry(
         bl,
         this.socks5,
         this.proxyScreenUiState2.text,
         this.proxyScreenUiState22.text,
         this.proxyScreenUiState23.text,
         this.proxyScreenUiState24.text
      );
   }

   private void invoke8(ProxyScreen.ProxyScreenUiState2 proxyScreenUiState26) {
      for (ProxyScreen.ProxyScreenUiState2 proxyScreenUiState27 : this.items) {
         proxyScreenUiState27.flag2 = proxyScreenUiState27 == proxyScreenUiState26;
         proxyScreenUiState27.flag3 = false;
         if (proxyScreenUiState27.flag2) {
            proxyScreenUiState27.intValue2 = proxyScreenUiState27.text.length();
         }
      }
   }

   private void invoke9(int i) {
      ProxyScreen.ProxyScreenUiState2 proxyScreenUiState28 = this.resolve2();
      int intValue6 = proxyScreenUiState28 == null ? (i > 0 ? -1 : this.items.size()) : this.items.indexOf(proxyScreenUiState28);
      int intValue7 = Math.floorMod(intValue6 + i, this.items.size());
      this.invoke8(this.items.get(intValue7));
   }

   private void invoke10() {
      for (ProxyScreen.ProxyScreenUiState2 proxyScreenUiState29 : this.items) {
         proxyScreenUiState29.flag2 = false;
         proxyScreenUiState29.flag3 = false;
      }
   }

   private ProxyScreen.ProxyScreenUiState2 resolve2() {
      for (ProxyScreen.ProxyScreenUiState2 proxyScreenUiState210 : this.items) {
         if (proxyScreenUiState210.flag2) {
            return proxyScreenUiState210;
         }
      }

      return null;
   }

   private void invoke11() {
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
         this.intValue4 = 0xFF000000 | Color.HSBtoRGB((floatValues[0] + 0.075F) % 1.0F, Math.min(1.0F, floatValues[1] * 1.08F), Math.min(1.0F, floatValues[2] * 1.18F)) & 16777215;
      }
   }

   private boolean check(Window window, int i, int j, int k, int l, long m) {
      if (this.intValue == i && this.intValue2 == j) {
         return false;
      } else {
         this.intValue = i;
         this.intValue2 = j;
         float floatValue8 = measure8(this.measure(window, (double)k), 0.0F, (float)i);
         float floatValue9 = measure8(this.measure2(window, l), 0.0F, (float)j);
         this.floatValue2 = this.floatValue6 = this.floatValue10 = floatValue8;
         this.floatValue3 = this.floatValue7 = this.floatValue11 = floatValue9;
         this.floatValue4 = this.floatValue5 = 0.0F;
         this.floatValue8 = this.floatValue9 = 0.0F;
         this.flag = true;
         this.flag2 = true;
         this.flag3 = true;
         this.timestamp3 = m;
         this.springIntegrator.setFloatValue(0.0F);
         this.springIntegrator2.setFloatValue(0.0F);
         this.invoke15();
         this.invoke16(floatValue8, floatValue9, 0.12F);
         return true;
      }
   }

   private void invoke12(Window window, int i, int j, float f, long l) {
      float floatValue10 = this.measure(window, (double)i);
      float floatValue11 = this.measure2(window, j);
      if (!this.flag) {
         this.floatValue2 = floatValue10;
         this.floatValue3 = floatValue11;
         this.floatValue4 = 0.0F;
         this.floatValue5 = 0.0F;
         this.flag = true;
      } else {
         float floatValue12 = floatValue10 - this.floatValue2;
         float floatValue13 = floatValue11 - this.floatValue3;
         float floatValue14 = measure6(floatValue12, floatValue13);
         if (floatValue14 > 0.2F) {
            this.floatValue4 = measure8(floatValue12 / Math.max(1.0F, (float)window.getFramebufferWidth()) / f, -3.0F, 3.0F);
            this.floatValue5 = measure8(floatValue13 / Math.max(1.0F, (float)window.getFramebufferHeight()) / f, -3.0F, 3.0F);
         } else {
            float floatValue15 = (float)Math.pow(8.0E-4F, f);
            this.floatValue4 *= floatValue15;
            this.floatValue5 *= floatValue15;
         }

         this.floatValue2 = floatValue10;
         this.floatValue3 = floatValue11;
         if (floatValue14 > 1.5F) {
            this.timestamp3 = l;
         }
      }
   }

   private void invoke13(int i, int j, float f) {
      if (!this.flag2) {
         this.floatValue6 = this.floatValue2;
         this.floatValue7 = this.floatValue3;
         this.floatValue8 = 0.0F;
         this.floatValue9 = 0.0F;
         this.flag2 = true;
      } else {
         float floatValue16 = this.floatValue6;
         float floatValue17 = this.floatValue7;
         float floatValue18 = measure6(this.floatValue2 - this.floatValue6, this.floatValue3 - this.floatValue7);
         float floatValue19 = (1.0F - (float)Math.pow(3.5E-5F, f)) * (0.72F + measure8(floatValue18 / 520.0F, 0.0F, 0.42F));
         this.floatValue6 = this.floatValue6 + (this.floatValue2 - this.floatValue6) * measure8(floatValue19, 0.05F, 0.26F);
         this.floatValue7 = this.floatValue7 + (this.floatValue3 - this.floatValue7) * measure8(floatValue19, 0.05F, 0.26F);
         float floatValue20 = measure8((this.floatValue6 - floatValue16) / Math.max(1.0F, (float)i) / f, -1.8F, 1.8F);
         float floatValue21 = measure8((this.floatValue7 - floatValue17) / Math.max(1.0F, (float)j) / f, -1.8F, 1.8F);
         float floatValue22 = 1.0F - (float)Math.pow(0.0025F, f);
         this.floatValue8 = this.floatValue8 + (floatValue20 - this.floatValue8) * floatValue22;
         this.floatValue9 = this.floatValue9 + (floatValue21 - this.floatValue9) * floatValue22;
      }
   }

   private void invoke14() {
      if (MenuVisualPreferences.check3() && MenuModule.check(MenuModule.SLED_KURSORA_VMENYU)) {
         if (!this.flag3) {
            this.floatValue10 = this.floatValue6;
            this.floatValue11 = this.floatValue7;
            this.flag3 = true;
            this.invoke16(this.floatValue6, this.floatValue7, 0.3F);
         } else {
            float floatValue23 = measure6(this.floatValue6 - this.floatValue10, this.floatValue7 - this.floatValue11);
            if (floatValue23 > 5.5F) {
               this.invoke16(this.floatValue6, this.floatValue7, measure8(floatValue23 / 190.0F, 0.1F, 0.48F));
               this.floatValue10 = this.floatValue6;
               this.floatValue11 = this.floatValue7;
            }
         }
      }
   }

   private void invoke15() {
      for (ProxyScreen.ProxyScreenState4 proxyScreenState4 : this.proxyScreenState4s) {
         proxyScreenState4.floatValue = 0.0F;
         proxyScreenState4.floatValue2 = 0.0F;
         proxyScreenState4.floatValue3 = -100.0F;
         proxyScreenState4.floatValue4 = 0.0F;
      }
   }

   private void invoke16(float f, float g, float h) {
      int intValue8 = 0;
      float floatValue24 = -1.0F;

      for (int intValue9 = 0; intValue9 < this.proxyScreenState4s.length; intValue9++) {
         float floatValue25 = this.floatValue - this.proxyScreenState4s[intValue9].floatValue3;
         if (this.proxyScreenState4s[intValue9].floatValue4 <= 0.0F) {
            intValue8 = intValue9;
            break;
         }

         if (floatValue25 > floatValue24) {
            floatValue24 = floatValue25;
            intValue8 = intValue9;
         }
      }

      this.proxyScreenState4s[intValue8].floatValue = f;
      this.proxyScreenState4s[intValue8].floatValue2 = g;
      this.proxyScreenState4s[intValue8].floatValue3 = this.floatValue;
      this.proxyScreenState4s[intValue8].floatValue4 = h;
   }

   private void invoke17(int i, int j, float f, float g, float h) {
      float floatValue26 = measure4(i, j);
      boolean flag2 = i < 980.0F * floatValue26;
      float floatValue27 = 46.0F * floatValue26;
      float floatValue28 = 18.0F * floatValue26;
      float floatValue29 = 16.0F * floatValue26;
      float floatValue30 = flag2 ? measure8(i * 0.68F, 300.0F * floatValue26, 520.0F * floatValue26) : measure8(i * 0.2F, 280.0F * floatValue26, 410.0F * floatValue26);
      float floatValue31 = flag2 ? floatValue30 : floatValue30 * 2.0F + floatValue29;
      float floatValue32 = 42.0F * floatValue26;
      float floatValue33 = 10.0F * floatValue26;
      float floatValue34 = flag2 ? (floatValue31 - floatValue33) * 0.5F : measure8(i * 0.072F, 96.0F * floatValue26, 128.0F * floatValue26);
      int intValue10 = flag2 ? 2 : 6;
      int intValue11 = flag2 ? 3 : 1;
      float floatValue35 = intValue10 * floatValue34 + (intValue10 - 1) * floatValue33;
      float floatValue36 = flag2 ? this.items.size() * floatValue27 + (this.items.size() - 1) * floatValue28 : floatValue27 * 2.0F + floatValue28;
      float floatValue37 = intValue11 * floatValue32 + (intValue11 - 1) * floatValue33;
      float floatValue38 = floatValue36 + 34.0F * floatValue26 + floatValue37;
      float floatValue39 = i * 0.5F + f * 1.35F * floatValue26;
      float floatValue40 = j * 0.305F + g * 0.92F * floatValue26;
      if (floatValue40 + floatValue38 > j - 58.0F * floatValue26) {
         floatValue40 = j - floatValue38 - 58.0F * floatValue26;
      }

      floatValue40 = Math.max(j * 0.21F, floatValue40);
      float floatValue41 = floatValue39 - floatValue31 * 0.5F;

      for (int intValue12 = 0; intValue12 < this.items.size(); intValue12++) {
         ProxyScreen.ProxyScreenUiState2 proxyScreenUiState211 = this.items.get(intValue12);
         int intValue13 = flag2 ? 0 : intValue12 % 2;
         int intValue14 = flag2 ? intValue12 : intValue12 / 2;
         float floatValue42 = floatValue41 + intValue13 * (floatValue30 + floatValue29);
         float floatValue43 = floatValue40 + intValue14 * (floatValue27 + floatValue28);
         this.invoke18(proxyScreenUiState211, floatValue42, floatValue43, floatValue30, floatValue27, floatValue27 * 0.5F, h, floatValue26);
      }

      float floatValue44 = floatValue39 - floatValue35 * 0.5F;
      float floatValue45 = floatValue40 + floatValue36 + 34.0F * floatValue26;

      for (int intValue15 = 0; intValue15 < this.items2.size(); intValue15++) {
         ProxyScreen.ProxyScreenUiState proxyScreenUiState4 = this.items2.get(intValue15);
         int intValue16 = intValue15 % intValue10;
         int intValue17 = intValue15 / intValue10;
         proxyScreenUiState4.text = this.resolve5(proxyScreenUiState4.proxyScreenState);
         proxyScreenUiState4.floatValue = floatValue44 + intValue16 * (floatValue34 + floatValue33);
         proxyScreenUiState4.floatValue2 = floatValue45 + intValue17 * (floatValue32 + floatValue33);
         proxyScreenUiState4.floatValue5 = floatValue34;
         proxyScreenUiState4.floatValue6 = floatValue32;
         proxyScreenUiState4.floatValue7 = floatValue32 * 0.5F;
         proxyScreenUiState4.floatValue16 = 46.0F * floatValue26;
         proxyScreenUiState4.floatValue17 = measure7(measure8((this.floatValue - 0.32F - intValue15 * 0.035F) / 0.76F, 0.0F, 1.0F));
         this.invoke19(proxyScreenUiState4, h, floatValue26);
      }
   }

   private void invoke18(ProxyScreen.ProxyScreenUiState2 proxyScreenUiState212, float f, float g, float h, float i, float j, float k, float l) {
      proxyScreenUiState212.floatValue = f;
      proxyScreenUiState212.floatValue2 = g;
      proxyScreenUiState212.floatValue5 = h;
      proxyScreenUiState212.floatValue6 = i;
      proxyScreenUiState212.floatValue7 = j;
      proxyScreenUiState212.floatValue16 = 50.0F * l;
      proxyScreenUiState212.floatValue17 = measure7(measure8((this.floatValue - 0.22F - this.items.indexOf(proxyScreenUiState212) * 0.04F) / 0.82F, 0.0F, 1.0F));
      this.invoke19(proxyScreenUiState212, k, l);
      proxyScreenUiState212.floatValue = proxyScreenUiState212.floatValue
         + ((proxyScreenUiState212.flag2 ? 1.0F : 0.0F) - proxyScreenUiState212.floatValue) * (1.0F - (float)Math.pow(1.0E-4F, k));
   }

   private void invoke19(ProxyScreen.ProxyScreenState3 proxyScreenState3, float f, float g) {
      float floatValue46 = measure5(
         this.floatValue2,
         this.floatValue3,
         proxyScreenState3.floatValue,
         proxyScreenState3.floatValue2,
         proxyScreenState3.floatValue5,
         proxyScreenState3.floatValue6,
         proxyScreenState3.floatValue7
      );
      boolean flag3 = floatValue46 <= 0.0F;
      float floatValue47 = 1.0F - measure7(measure8(Math.max(0.0F, floatValue46) / Math.max(1.0F, 28.0F * g), 0.0F, 1.0F));
      float floatValue48 = proxyScreenState3 instanceof ProxyScreen.ProxyScreenUiState2 proxyScreenUiState213 && proxyScreenUiState213.flag2 ? 0.52F : 0.0F;
      float floatValue49 = Math.max(flag3 ? Math.max(0.74F, floatValue47) : floatValue47 * 0.48F, floatValue48);
      proxyScreenState3.floatValue8 = proxyScreenState3.floatValue8
         + ((flag3 ? 1.0F : 0.0F) - proxyScreenState3.floatValue8) * (1.0F - (float)Math.pow(1.0E-4F, f));
      proxyScreenState3.floatValue9 = proxyScreenState3.floatValue9 + (floatValue49 - proxyScreenState3.floatValue9) * (1.0F - (float)Math.pow(1.4E-4F, f));
      proxyScreenState3.floatValue10 = proxyScreenState3.floatValue10 + (0.0F - proxyScreenState3.floatValue10) * (1.0F - (float)Math.pow(1.8E-5F, f));
      proxyScreenState3.floatValue11 = proxyScreenState3.floatValue11 + (0.0F - proxyScreenState3.floatValue11) * (1.0F - (float)Math.pow(6.0E-6F, f));
      float floatValue50 = measure8((this.floatValue6 - proxyScreenState3.floatValue) / Math.max(1.0F, proxyScreenState3.floatValue5), 0.0F, 1.0F);
      float floatValue51 = measure8((this.floatValue7 - proxyScreenState3.floatValue2) / Math.max(1.0F, proxyScreenState3.floatValue6), 0.0F, 1.0F);
      float floatValue52 = 1.0F - (float)Math.pow(2.2E-4F, f);
      proxyScreenState3.floatValue13 = proxyScreenState3.floatValue13 + (floatValue50 - proxyScreenState3.floatValue13) * floatValue52;
      proxyScreenState3.floatValue14 = proxyScreenState3.floatValue14 + (floatValue51 - proxyScreenState3.floatValue14) * floatValue52;
      float floatValue53 = 1.0F + proxyScreenState3.floatValue9 * 0.04F - proxyScreenState3.floatValue10 * 0.065F + floatValue48 * 0.018F;
      proxyScreenState3.floatValue12 = proxyScreenState3.springIntegrator.measure(floatValue53, f);
      proxyScreenState3.floatValue3 = proxyScreenState3.floatValue + (proxyScreenState3.floatValue13 - 0.5F) * 5.0F * g * proxyScreenState3.floatValue9;
      proxyScreenState3.floatValue4 = proxyScreenState3.floatValue2
         + (proxyScreenState3.floatValue14 - 0.5F) * 3.5F * g * proxyScreenState3.floatValue9
         - proxyScreenState3.floatValue8 * 1.2F * g;
      proxyScreenState3.floatValue15 = measure8(
         measure6(this.floatValue8, this.floatValue9) * 0.42F * proxyScreenState3.floatValue9 + Math.abs(proxyScreenState3.springIntegrator.getFloatValue2()) * 0.04F,
         0.0F,
         1.0F
      );
   }

   private MainMenuScreen.MainMenuScreenTimedEntry resolve3(int i, int j, int k, float f, float g, long l) {
      float floatValue54 = Math.max(0.0F, (float)(l - this.timestamp3) / 1.0E9F);
      float floatValue55 = measure8(measure6(this.floatValue8, this.floatValue9), 0.0F, 3.0F);
      float floatValue56 = Math.max((float)Math.exp(-floatValue54 * 1.28F), measure8(floatValue55 * 0.24F, 0.0F, 1.0F));
      float floatValue57 = measure7(measure8(this.floatValue / 0.88F, 0.0F, 1.0F));
      float floatValue58 = measure4(i, j);
      float floatValue59 = 0.0F;
      ArrayList arrayList = new ArrayList();

      for (ProxyScreen.ProxyScreenUiState2 proxyScreenUiState214 : this.items) {
         arrayList.add(this.resolve4(proxyScreenUiState214, proxyScreenUiState214.floatValue17));
         floatValue59 = Math.max(floatValue59, proxyScreenUiState214.floatValue11);
      }

      for (ProxyScreen.ProxyScreenUiState proxyScreenUiState5 : this.items2) {
         arrayList.add(this.resolve4(proxyScreenUiState5, proxyScreenUiState5.floatValue17));
         floatValue59 = Math.max(floatValue59, proxyScreenUiState5.floatValue11);
      }

      MainMenuScreen.MainMenuScreenData[] w283s = new MainMenuScreen.MainMenuScreenData[14];

      for (int intValue18 = 0; intValue18 < 14; intValue18++) {
         ProxyScreen.ProxyScreenState4 proxyScreenState42 = this.proxyScreenState4s[intValue18];
         float floatValue60 = Math.max(0.0F, this.floatValue - proxyScreenState42.floatValue3);
         float floatValue61 = floatValue60 > 3.1F ? 0.0F : proxyScreenState42.floatValue4;
         w283s[intValue18] = new MainMenuScreen.MainMenuScreenData(proxyScreenState42.floatValue / Math.max(1.0F, (float)i), proxyScreenState42.floatValue2 / Math.max(1.0F, (float)j), floatValue60, floatValue61);
      }

      return new MainMenuScreen.MainMenuScreenTimedEntry(
         i,
         j,
         k,
         this.floatValue * 0.58F,
         this.floatValue6,
         this.floatValue7,
         this.floatValue6 / Math.max(1.0F, (float)i),
         this.floatValue7 / Math.max(1.0F, (float)j),
         this.floatValue8 * 0.56F,
         this.floatValue9 * 0.56F,
         floatValue55 * 0.56F,
         measure10(this.intValue3),
         measure11(this.intValue3),
         measure12(this.intValue3),
         measure10(this.intValue4),
         measure11(this.intValue4),
         measure12(this.intValue4),
         -f * 8.0E-4F,
         -g * 6.2E-4F,
         f * 0.92F * floatValue58,
         g * 0.78F * floatValue58,
         f * 1.25F * floatValue58,
         g * 1.05F * floatValue58,
         floatValue56 * 0.64F,
         floatValue56 > 0.1F ? 0.86F : 0.74F,
         0.56F + floatValue57 * 0.24F,
         measure8(floatValue59, 0.0F, 1.0F),
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
         arrayList,
         w283s
      );
   }

   private MainMenuScreen.MainMenuScreenBounds resolve4(ProxyScreen.ProxyScreenState3 proxyScreenState32, float f) {
      return new MainMenuScreen.MainMenuScreenBounds(
         proxyScreenState32.text,
         proxyScreenState32.floatValue3,
         proxyScreenState32.floatValue4,
         proxyScreenState32.floatValue5,
         proxyScreenState32.floatValue6,
         proxyScreenState32.floatValue7,
         proxyScreenState32.floatValue8,
         proxyScreenState32.floatValue9,
         proxyScreenState32.floatValue10,
         f,
         proxyScreenState32.floatValue11,
         proxyScreenState32.floatValue16,
         proxyScreenState32.floatValue12,
         proxyScreenState32.floatValue13,
         proxyScreenState32.floatValue14,
         proxyScreenState32.floatValue15
      );
   }

   private void invoke20(MainMenuScreen.MainMenuScreenTimedEntry mainMenuScreenTimedEntry2) {
      try {
         WildClient.invoke15();
         RenderManager renderManager = WildClient.resolve();
         if (renderManager == null) {
            return;
         }

         FramebufferUtils.GlStateSnapshot glStateSnapshot2 = FramebufferUtils.captureGlState();

         try {
            renderManager.invoke(mainMenuScreenTimedEntry2.framebufferWidth(), mainMenuScreenTimedEntry2.framebufferHeight());
            float floatValue62 = measure4(mainMenuScreenTimedEntry2.framebufferWidth(), mainMenuScreenTimedEntry2.framebufferHeight());
            float floatValue63 = measure7(measure8(this.floatValue / 0.82F, 0.0F, 1.0F));
            float floatValue64 = mainMenuScreenTimedEntry2.framebufferWidth() * 0.5F + mainMenuScreenTimedEntry2.uiParallaxX() * 0.12F;
            float floatValue65 = mainMenuScreenTimedEntry2.framebufferHeight() * 0.126F + mainMenuScreenTimedEntry2.uiParallaxY() * 0.08F;
            renderManager.invoke70(FontRegistry.fontObject4, floatValue64, floatValue65, 40.0F * floatValue62, "Proxy", this.compute(0.94F * floatValue63), "c");
            renderManager.invoke70(
               FontRegistry.fontObject,
               floatValue64,
               floatValue65 + 30.0F * floatValue62,
               24.0F * floatValue62,
               this.socks5 + "  /  " + this.proxyDisabled,
               this.compute2(0.52F * floatValue63),
               "c"
            );

            for (ProxyScreen.ProxyScreenUiState2 proxyScreenUiState215 : this.items) {
               this.invoke22(renderManager, proxyScreenUiState215, floatValue62);
            }

            for (ProxyScreen.ProxyScreenUiState proxyScreenUiState6 : this.items2) {
               this.invoke23(renderManager, proxyScreenUiState6, floatValue62);
            }

            this.invoke21(renderManager, mainMenuScreenTimedEntry2, floatValue62);
            renderManager.invoke19();
         } finally {
            FramebufferUtils.restoreGlState(glStateSnapshot2);
         }
      } catch (Throwable exception2) {
      }
   }

   private void invoke21(RenderManager renderManager2, MainMenuScreen.MainMenuScreenTimedEntry mainMenuScreenTimedEntry3, float f) {
      String text5 = this.resolve6();
      if (!text5.isBlank()) {
         float floatValue66 = 25.0F * f;
         float floatValue67 = mainMenuScreenTimedEntry3.framebufferWidth() * 0.5F;
         float floatValue68 = mainMenuScreenTimedEntry3.framebufferHeight() - 30.0F * f;
         renderManager2.invoke70(FontRegistry.fontObject, floatValue67, floatValue68, floatValue66, text5, this.compute2(0.4F * mainMenuScreenTimedEntry3.sceneEntry()), "c");
      }
   }

   private void invoke22(RenderManager renderManager3, ProxyScreen.ProxyScreenUiState2 proxyScreenUiState216, float f) {
      float floatValue69 = proxyScreenUiState216.floatValue17;
      String text6 = proxyScreenUiState216.flag ? "*".repeat(proxyScreenUiState216.text.length()) : proxyScreenUiState216.text;
      boolean flag4 = text6.isBlank();
      float floatValue70 = 22.0F * f;
      float floatValue71 = proxyScreenUiState216.floatValue3 + floatValue70;
      float floatValue72 = Math.max(8.0F * f, proxyScreenUiState216.floatValue5 - floatValue70 * 2.0F);
      float floatValue73 = 25.0F * f;
      boolean flag5 = flag4 && !proxyScreenUiState216.flag2;
      String text7 = flag5 ? proxyScreenUiState216.text : text6;
      int intValue19 = flag5 ? this.compute2(0.42F * floatValue69) : this.compute((0.78F + proxyScreenUiState216.floatValue * 0.18F) * floatValue69);
      if (!flag4 || proxyScreenUiState216.flag2) {
         renderManager3.invoke69(
            FontRegistry.fontObject,
            proxyScreenUiState216.floatValue3 + 18.0F * f,
            proxyScreenUiState216.floatValue4 - 7.0F * f,
            20.0F * f,
            proxyScreenUiState216.text,
            this.compute2((0.3F + proxyScreenUiState216.floatValue * 0.28F) * floatValue69)
         );
      }

      renderManager3.invoke24(
         floatValue71,
         proxyScreenUiState216.floatValue4 + 3.0F * f,
         floatValue72,
         proxyScreenUiState216.floatValue6 - 6.0F * f,
         proxyScreenUiState216.floatValue7 * 0.55F,
         proxyScreenUiState216.floatValue7 * 0.55F,
         proxyScreenUiState216.floatValue7 * 0.55F,
         proxyScreenUiState216.floatValue7 * 0.55F
      );
      if (!text7.isBlank()) {
         if (flag5) {
            invoke24(
               renderManager3,
               FontRegistry.fontObject,
               proxyScreenUiState216.floatValue3,
               proxyScreenUiState216.floatValue4,
               proxyScreenUiState216.floatValue5,
               proxyScreenUiState216.floatValue6,
               floatValue73,
               text7,
               intValue19
            );
         } else {
            float floatValue74 = measure3(FontRegistry.fontObject, floatValue73, proxyScreenUiState216.floatValue4, proxyScreenUiState216.floatValue6);
            if (proxyScreenUiState216.flag3) {
               float floatValue75 = RenderManager.resolve7(FontRegistry.fontObject, text7, floatValue73).floatValue;
               renderManager3.invoke5(
                  measure9(floatValue71 - proxyScreenUiState216.floatValue3 - 2.0F * f),
                  proxyScreenUiState216.floatValue4 + proxyScreenUiState216.floatValue6 * 0.25F,
                  floatValue75 + 4.0F * f,
                  proxyScreenUiState216.floatValue6 * 0.5F,
                  2.0F * f,
                  compute4(0.25F, 0.55F, 0.95F, 0.45F * floatValue69)
               );
            }

            renderManager3.invoke69(FontRegistry.fontObject, measure9(floatValue71 - proxyScreenUiState216.floatValue3), measure9(floatValue74), floatValue73, text7, intValue19);
         }
      }

      if (proxyScreenUiState216.flag2) {
         int intValue20 = compute3(proxyScreenUiState216.intValue2, 0, text6.length());
         String text8 = text6.substring(0, intValue20);
         float floatValue76 = RenderManager.resolve7(FontRegistry.fontObject, text8, floatValue73).floatValue;
         if (proxyScreenUiState216.flag3) {
            floatValue76 = RenderManager.resolve7(FontRegistry.fontObject, text6, floatValue73).floatValue;
         }

         float floatValue77 = proxyScreenUiState216.floatValue3;
         float floatValue78 = floatValue72 - 9.0F * f;
         if (floatValue76 - floatValue77 > floatValue78) {
            floatValue77 = floatValue76 - floatValue78;
         }

         if (floatValue76 - floatValue77 < 0.0F) {
            floatValue77 = floatValue76;
         }

         floatValue77 = Math.max(0.0F, floatValue77);
         proxyScreenUiState216.floatValue3 = proxyScreenUiState216.floatValue3 + (floatValue77 - proxyScreenUiState216.floatValue3) * 0.3F;
         proxyScreenUiState216.floatValue2 = proxyScreenUiState216.floatValue2 + (floatValue76 - proxyScreenUiState216.floatValue2) * 0.3F;
         float floatValue79 = 0.54F + 0.46F * (float)Math.sin(this.floatValue * 5.4F);
         if (!proxyScreenUiState216.flag3) {
            int intValue21 = compute5(this.intValue4, this.intValue3, floatValue79, (0.42F + floatValue79 * 0.36F) * floatValue69);
            float floatValue80 = floatValue71 + proxyScreenUiState216.floatValue2 - proxyScreenUiState216.floatValue3 + 2.0F * f;
            float floatValue81 = 20.0F * f;
            float floatValue82 = proxyScreenUiState216.floatValue4 + (proxyScreenUiState216.floatValue6 - floatValue81) * 0.5F;
            renderManager3.invoke5(measure9(floatValue80), measure9(floatValue82), Math.max(1.25F * f, 1.0F), floatValue81, 1.0F * f, intValue21);
         }

         renderManager3.invoke28(
            proxyScreenUiState216.floatValue3,
            proxyScreenUiState216.floatValue4,
            proxyScreenUiState216.floatValue5,
            proxyScreenUiState216.floatValue6,
            proxyScreenUiState216.floatValue7,
            compute5(this.intValue4, this.intValue3, floatValue79, 0.24F * floatValue69 * (0.35F + proxyScreenUiState216.floatValue * 0.65F)),
            1.0F * f
         );
      }

      renderManager3.invoke25();
   }

   private void invoke23(RenderManager renderManager4, ProxyScreen.ProxyScreenUiState proxyScreenUiState7, float f) {
      float floatValue83 = proxyScreenUiState7.floatValue17 * 0.9F;
      String text9 = resolve7(proxyScreenUiState7.text, proxyScreenUiState7.floatValue5 - 18.0F * f, 24.0F * f, FontRegistry.fontObject);
      invoke24(
         renderManager4,
         FontRegistry.fontObject,
         proxyScreenUiState7.floatValue3,
         proxyScreenUiState7.floatValue4,
         proxyScreenUiState7.floatValue5,
         proxyScreenUiState7.floatValue6,
         24.0F * f,
         text9,
         this.compute(floatValue83)
      );
   }

   private String resolve5(ProxyScreen.ProxyScreenState proxyScreenState2) {
      return switch (proxyScreenState2) {
         case TYPE -> this.socks5;
         case ENABLED -> this.flag6 ? "Enabled" : "Disabled";
         case PASTE -> "Paste";
         case TEST -> "Test";
         case SAVE -> "Save";
         case BACK -> "Back";
      };
   }

   private String resolve6() {
      String text10 = this.proxyScreenUiState2.text.trim();
      String text11 = this.proxyScreenUiState22.text.trim();
      if (text10.isBlank() || text11.isBlank()) {
         return "";
      } else {
         return "Socks5".equals(this.socks5) && !this.proxyScreenUiState23.text.isBlank()
            ? this.proxyScreenUiState23.text + ":" + "*".repeat(Math.min(10, this.proxyScreenUiState24.text.length())) + "@" + text10 + ":" + text11
            : text10 + ":" + text11;
      }
   }

   private int compute(float f) {
      return this.flag4 ? compute4(0.1F, 0.1F, 0.1F, f) : compute4(1.0F, 1.0F, 1.0F, f);
   }

   private int compute2(float f) {
      return this.flag4 ? compute4(0.4F, 0.4F, 0.4F, f) : compute4(0.8F, 0.86F, 0.9F, f);
   }

   private static void invoke24(RenderManager renderManager5, FontObject fontObject, float f, float g, float h, float i, float j, String string, int k) {
      String text12 = string == null ? "" : string;
      float floatValue84 = RenderManager.resolve7(fontObject, text12, j).floatValue;
      float floatValue85 = measure9(f + (h - floatValue84) * 0.5F);
      float floatValue86 = measure9(measure3(fontObject, j, g, i));
      renderManager5.invoke69(fontObject, floatValue85, floatValue86, j, text12, k);
   }

   private float measure(Window window, double d) {
      return (float)(d * window.getFramebufferWidth() / Math.max(1.0, (double)window.getScaledWidth()));
   }

   private float measure2(Window window, double d) {
      return (float)(d * window.getFramebufferHeight() / Math.max(1.0, (double)window.getScaledHeight()));
   }

   private static float measure3(FontObject fontObject2, float f, float g, float h) {
      try {
         return g + h * 0.5F + FontRegistry.measure(fontObject2, 72, f * 0.5F);
      } catch (Throwable exception3) {
         return g + h * 0.5F + f * 0.18F;
      }
   }

   private static String resolve7(String string, float f, float g, FontObject fontObject3) {
      if (string == null) {
         return "";
      } else if (f <= 0.0F) {
         return "";
      } else if (RenderManager.resolve7(fontObject3, string, g).floatValue <= f) {
         return string;
      } else {
         String text13 = "...";
         if (RenderManager.resolve7(fontObject3, text13, g).floatValue > f) {
            return "";
         } else {
            int intValue22 = 1;
            int intValue23 = string.length();
            int intValue24 = 1;

            while (intValue22 <= intValue23) {
               int intValue25 = intValue22 + intValue23 >>> 1;
               if (RenderManager.resolve7(fontObject3, string.substring(0, intValue25) + text13, g).floatValue <= f) {
                  intValue24 = intValue25;
                  intValue22 = intValue25 + 1;
               } else {
                  intValue23 = intValue25 - 1;
               }
            }

            return string.substring(0, intValue24) + text13;
         }
      }
   }

   private static float measure4(float f, float g) {
      return measure8(Math.min(f / 1920.0F, g / 1080.0F) * 1.16F, 0.72F, 1.34F);
   }

   static float measure5(float f, float g, float h, float i, float j, float k, float l) {
      float floatValue87 = h + j * 0.5F;
      float floatValue88 = i + k * 0.5F;
      float floatValue89 = j * 0.5F - l;
      float floatValue90 = k * 0.5F - l;
      float floatValue91 = Math.abs(f - floatValue87) - floatValue89;
      float floatValue92 = Math.abs(g - floatValue88) - floatValue90;
      float floatValue93 = Math.max(floatValue91, 0.0F);
      float floatValue94 = Math.max(floatValue92, 0.0F);
      return (float)Math.sqrt(floatValue93 * floatValue93 + floatValue94 * floatValue94) + Math.min(Math.max(floatValue91, floatValue92), 0.0F) - l;
   }

   private static float measure6(float f, float g) {
      return (float)Math.sqrt(f * f + g * g);
   }

   private static float measure7(float f) {
      float floatValue95 = measure8(f, 0.0F, 1.0F);
      return floatValue95 * floatValue95 * floatValue95 * (floatValue95 * (floatValue95 * 6.0F - 15.0F) + 10.0F);
   }

   private static float measure8(float f, float g, float h) {
      return Math.max(g, Math.min(h, f));
   }

   static int compute3(int i, int j, int k) {
      return Math.max(j, Math.min(k, i));
   }

   private static float measure9(float f) {
      return Math.round(f);
   }

   private static float measure10(int i) {
      return (i >> 16 & 0xFF) / 255.0F;
   }

   private static float measure11(int i) {
      return (i >> 8 & 0xFF) / 255.0F;
   }

   private static float measure12(int i) {
      return (i & 0xFF) / 255.0F;
   }

   private static int compute4(float f, float g, float h, float i) {
      int intValue26 = Math.round(measure8(f, 0.0F, 1.0F) * 255.0F);
      int intValue27 = Math.round(measure8(g, 0.0F, 1.0F) * 255.0F);
      int intValue28 = Math.round(measure8(h, 0.0F, 1.0F) * 255.0F);
      int intValue29 = Math.round(measure8(i, 0.0F, 1.0F) * 255.0F);
      return intValue29 << 24 | intValue26 << 16 | intValue27 << 8 | intValue28;
   }

   private static int compute5(int i, int j, float f, float g) {
      float floatValue96 = measure8(f, 0.0F, 1.0F);
      int intValue30 = ColorUtils.compute16(i, j, floatValue96);
      int intValue31 = Math.round(measure8(g, 0.0F, 1.0F) * 255.0F);
      return intValue31 << 24 | intValue30;
   }

   static enum ProxyScreenState {
      TYPE,
      ENABLED,
      PASTE,
      TEST,
      SAVE,
      BACK;
   }

   static final class ProxyScreenUiState extends ProxyScreen.ProxyScreenState3 {
      final ProxyScreen.ProxyScreenState proxyScreenState;

      ProxyScreenUiState(String string, ProxyScreen.ProxyScreenState proxyScreenState5) {
         super(string);
         this.proxyScreenState = proxyScreenState5;
      }
   }

   static final class ProxyScreenUiState2 extends ProxyScreen.ProxyScreenState3 {
      final boolean flag;
      private final int intValue;
      private final ProxyScreen.ProxyScreenState2 proxyScreenState2;
      String text = "";
      int intValue2;
      boolean flag2;
      boolean flag3;
      float floatValue;
      float floatValue2;
      float floatValue3;

      ProxyScreenUiState2(String string, boolean bl, int i, ProxyScreen.ProxyScreenState2 proxyScreenState22) {
         super(string);
         this.flag = bl;
         this.intValue = i;
         this.proxyScreenState2 = proxyScreenState22;
      }

      void invoke(String string) {
         this.text = this.resolve(string == null ? "" : string);
         if (this.text.length() > this.intValue) {
            this.text = this.text.substring(0, this.intValue);
         }

         this.intValue2 = this.text.length();
         this.flag3 = false;
         this.floatValue2 = 0.0F;
         this.floatValue3 = 0.0F;
      }

      void invoke2(String string) {
         String text14 = this.resolve(string == null ? "" : string);
         if (!text14.isEmpty()) {
            if (this.flag3) {
               this.text = "";
               this.intValue2 = 0;
               this.flag3 = false;
            }

            int intValue32 = this.intValue - this.text.length();
            if (intValue32 > 0) {
               if (text14.length() > intValue32) {
                  text14 = text14.substring(0, intValue32);
               }

               int intValue33 = ProxyScreen.compute3(this.intValue2, 0, this.text.length());
               this.text = this.text.substring(0, intValue33) + text14 + this.text.substring(intValue33);
               this.intValue2 = intValue33 + text14.length();
            }
         }
      }

      void invoke3() {
         if (this.flag3) {
            this.invoke5();
         } else if (this.intValue2 > 0 && !this.text.isEmpty()) {
            int intValue34 = ProxyScreen.compute3(this.intValue2, 0, this.text.length());
            if (intValue34 > 0) {
               this.text = this.text.substring(0, intValue34 - 1) + this.text.substring(intValue34);
               this.intValue2 = intValue34 - 1;
            }
         }
      }

      void invoke4() {
         if (this.flag3) {
            this.invoke5();
         } else {
            int intValue35 = ProxyScreen.compute3(this.intValue2, 0, this.text.length());
            if (intValue35 < this.text.length()) {
               this.text = this.text.substring(0, intValue35) + this.text.substring(intValue35 + 1);
               this.intValue2 = intValue35;
            }
         }
      }

      private void invoke5() {
         this.text = "";
         this.intValue2 = 0;
         this.floatValue2 = 0.0F;
         this.floatValue3 = 0.0F;
         this.flag3 = false;
      }

      void invoke6() {
         this.invoke();
         this.flag2 = false;
         this.flag3 = false;
         this.floatValue = 0.0F;
         this.floatValue2 = 0.0F;
         this.floatValue3 = 0.0F;
         this.intValue2 = ProxyScreen.compute3(this.intValue2, 0, this.text.length());
      }

      private String resolve(String string) {
         StringBuilder stringBuilder = new StringBuilder(string.length());

         for (int intValue36 = 0; intValue36 < string.length(); intValue36++) {
            char character = string.charAt(intValue36);
            if (character >= ' '
               && character != 127
               && (this.proxyScreenState2 != ProxyScreen.ProxyScreenState2.PORT || character >= '0' && character <= '9')
               && (this.proxyScreenState2 != ProxyScreen.ProxyScreenState2.HOST && this.proxyScreenState2 != ProxyScreen.ProxyScreenState2.TEXT || !Character.isWhitespace(character))) {
               stringBuilder.append(character);
            }
         }

         return stringBuilder.toString();
      }
   }

   static enum ProxyScreenState2 {
      HOST,
      PORT,
      TEXT,
      SECRET;
   }

   static class ProxyScreenState3 {
      protected String text;
      protected final SpringIntegrator springIntegrator = new SpringIntegrator(SpringSpec.resolve6());
      protected float floatValue;
      protected float floatValue2;
      protected float floatValue3;
      protected float floatValue4;
      protected float floatValue5;
      protected float floatValue6;
      protected float floatValue7;
      protected float floatValue8;
      protected float floatValue9;
      protected float floatValue10;
      protected float floatValue11;
      protected float floatValue12 = 1.0F;
      protected float floatValue13 = 0.5F;
      protected float floatValue14 = 0.5F;
      protected float floatValue15;
      protected float floatValue16;
      protected float floatValue17;

      protected ProxyScreenState3(String string) {
         this.text = string;
      }

      protected boolean check(float f, float g) {
         return ProxyScreen.measure5(f, g, this.floatValue, this.floatValue2, this.floatValue5, this.floatValue6, this.floatValue7) <= 0.0F;
      }

      protected void invoke() {
         this.floatValue8 = 0.0F;
         this.floatValue9 = 0.0F;
         this.floatValue10 = 0.0F;
         this.floatValue11 = 0.0F;
         this.floatValue15 = 0.0F;
         this.floatValue17 = 0.0F;
         this.floatValue12 = 1.0F;
         this.floatValue13 = 0.5F;
         this.floatValue14 = 0.5F;
         this.springIntegrator.setFloatValue(1.0F);
      }
   }

   static final class ProxyScreenState4 {
      float floatValue;
      float floatValue2;
      float floatValue3 = -100.0F;
      float floatValue4;
   }
}
