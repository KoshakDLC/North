package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class ClickGuiScreen extends Screen {
   public LegacyClickGuiOverlay legacyClickGuiOverlay;
   public MinecraftClient client = MinecraftClient.getInstance();
   private static volatile boolean flag = false;

   public ClickGuiScreen() {
      super(Text.literal("Gui"));
   }

   public static void invoke() {
      if (!flag) {
         flag = true;
         EventManager.register(new Object() {
            @EventHandler
            public void onHudFrame(HudFrameEvent hudFrameEvent) {
               MinecraftClient client = hudFrameEvent.getClient();
               if (client != null && client.currentScreen instanceof ClickGuiScreen) {
                  double[] doubleValues = new double[1];
                  double[] doubleValues2 = new double[1];
                  if (client.getWindow() != null) {
                     GLFW.glfwGetCursorPos(client.getWindow().getHandle(), doubleValues, doubleValues2);
                     if (client.mouse != null) {
                        client.mouse.unlockCursor();
                     }
                  }

                  int intValue = (int)doubleValues[0];
                  int intValue2 = (int)doubleValues2[0];
                  Object object = null;
                  LegacyClickGuiRenderer.invoke(hudFrameEvent.getRenderManager(), (DrawContext)object, intValue, intValue2, client.getRenderTickCounter().getDynamicDeltaTicks());
               }
            }
         });
      }
   }

   public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
   }

   public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
   }

   public void renderInGameBackground(DrawContext context) {
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      RenderManager renderManager = WildClient.resolve();
      return renderManager != null && LegacyClickGuiScreenController.check(renderManager, mouseX, mouseY, button) ? true : true;
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      LegacyDragController.invoke();
      return true;
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      return LegacyColorPickerInput.check(mouseX, mouseY, button, deltaX, deltaY) ? true : true;
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
      if (Math.abs(verticalAmount) > 1.0E-4) {
         int intValue3 = verticalAmount > 0.0 ? -200 : -201;
         if (LegacyClickGuiState.keybindSetting != null) {
            LegacyClickGuiState.keybindSetting.keyCode = intValue3;
            LegacyClickGuiState.keybindSetting.waitingForBind = false;
            LegacyClickGuiState.keybindSetting = null;
            if (WildClient.INSTANCE.configManager != null) {
               WildClient.INSTANCE.configManager.scheduleSave();
            }

            return true;
         }

         if (LegacyClickGuiState.module != null) {
            LegacyClickGuiState.module.bindKey = intValue3;
            LegacyClickGuiState.module.expanded = false;
            LegacyClickGuiState.resolve4(LegacyClickGuiState.module).animateTo(1.0, 0.2F, Easings.EASING_FUNCTION_14);
            LegacyClickGuiState.module = null;
            if (WildClient.INSTANCE.configManager != null) {
               WildClient.INSTANCE.configManager.scheduleSave();
            }

            return true;
         }
      }

      return LegacyClickGuiScreenController.check2(mouseX, mouseY, verticalAmount) ? true : true;
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      return LegacyMouseInput.check(keyCode, scanCode, modifiers) ? true : super.keyPressed(keyCode, scanCode, modifiers);
   }

   public boolean charTyped(char chr, int modifiers) {
      return LegacyTextInputHandler.check(chr, modifiers) ? true : super.charTyped(chr, modifiers);
   }

   public boolean shouldCloseOnEsc() {
      return LegacyPopupState.check();
   }

   public void close() {
      InputUtils.getINSTANCE().invoke2("Search");
      LegacyClickGuiState.flag4 = false;
      LegacyClickGuiState.text = "";
      WildClient.INSTANCE.themeManager.invoke3(LegacyClickGuiState.category);
      super.close();
   }

   public void tick() {
      super.tick();
      if (LegacyClickGuiState.flag6 && LegacyClickGuiState.easedAnimation.check3()) {
         this.close();
         LegacyClickGuiState.flag6 = false;
      }
   }

   public boolean shouldPause() {
      return false;
   }

   public void init() {
      super.init();
      this.legacyClickGuiOverlay = new LegacyClickGuiOverlay();
      LegacyGuiAnimator.invoke();
      MinecraftClient client2 = MinecraftClient.getInstance();
      if (client2 != null && client2.mouse != null) {
         client2.mouse.unlockCursor();
      }

      LegacyClickGuiState.categorys = Category.values();
      LegacyClickGuiState.themes = Theme.values();
      LegacyClickGuiState.floatValue8 = 366.475F;
      LegacyClickGuiState.floatValue9 = 238.805F;
      LegacyClickGuiState.floatValue6 = 480.0F - LegacyClickGuiState.floatValue8 / 2.0F;
      LegacyClickGuiState.floatValue7 = 260.0F - LegacyClickGuiState.floatValue9 / 2.0F;
      LegacyClickGuiState.directionalAnimation.invoke();
      if (WildClient.INSTANCE.themeManager == null) {
         WildClient.INSTANCE.themeManager = new ThemeManager();
         WildClient.INSTANCE.themeManager.invoke();
      }

      LegacyClickGuiState.theme = WildClient.INSTANCE.themeManager.getTheme();
      LegacyClickGuiState.theme2 = WildClient.INSTANCE.themeManager.getTheme();
      LegacyClickGuiState.category = WildClient.INSTANCE.themeManager.getCategory();
      if (WildClient.INSTANCE.moduleManager == null) {
         WildClient.INSTANCE.moduleManager = new ModuleManager();
      }

      LegacyClickGuiState.items = WildClient.INSTANCE.moduleManager.getModules(LegacyClickGuiState.category);
   }
}
