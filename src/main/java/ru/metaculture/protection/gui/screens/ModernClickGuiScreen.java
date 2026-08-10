package ru.metaculture.protection;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public final class ModernClickGuiScreen extends Screen {
   private final ClickGui clickGui = new ClickGui();

   public ModernClickGuiScreen() {
      super(Text.literal("North"));
   }

   public ClickGui getClickGui() {
      return this.clickGui;
   }

   protected void init() {
      super.init();
      this.clickGui.invoke(this.client);
      DelayedFuse.invoke2();
   }

   public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
      if (!this.check3()) {
         this.clickGui.invoke5();
      } else {
         this.clickGui.invoke3(this.measure(mouseX), this.measure2(mouseY));
      }
   }

   public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
   }

   public void renderInGameBackground(DrawContext context) {
   }

   public void invoke(RenderManager renderManager, DrawContext drawContext, int i, int j, float f) {
      if (!this.check2(i, j)) {
         this.clickGui.invoke5();
      } else {
         this.clickGui.invoke2(this.client, drawContext, renderManager, i, j, f);
         if (this.clickGui.check9()) {
            this.invoke4();
         }
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (!this.check3()) {
         this.clickGui.invoke5();
         return true;
      } else if (this.clickGui.check10()) {
         return true;
      } else {
         this.clickGui.check2(this.measure(mouseX), this.measure2(mouseY), button);
         return true;
      }
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      if (!this.check3()) {
         this.clickGui.invoke5();
         return true;
      } else if (this.clickGui.check10()) {
         return true;
      } else {
         this.clickGui.check3();
         return true;
      }
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      if (!this.check3()) {
         this.clickGui.invoke5();
         return true;
      } else if (this.clickGui.check10()) {
         return true;
      } else {
         this.clickGui.check4(this.measure(mouseX), this.measure2(mouseY));
         return true;
      }
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
      if (!this.check3()) {
         this.clickGui.invoke5();
         return true;
      } else if (this.clickGui.check10()) {
         return true;
      } else {
         double doubleValue = horizontalAmount;
         double doubleValue2 = verticalAmount;
         if (horizontalAmount == 0.0 && (hasShiftDown() || hasControlDown())) {
            doubleValue = verticalAmount;
            doubleValue2 = 0.0;
         }

         this.clickGui.check5(this.measure(mouseX), this.measure2(mouseY), doubleValue, doubleValue2);
         return true;
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 300) {
         return super.keyPressed(keyCode, scanCode, modifiers);
      } else if (!this.check3()) {
         this.clickGui.invoke5();
         return true;
      } else if (keyCode == 256 && this.clickGui.check10()) {
         this.invoke4();
         return true;
      } else if (keyCode == this.compute()) {
         this.close();
         return true;
      } else if (this.clickGui.check10()) {
         return true;
      } else {
         return this.clickGui.check6(keyCode) ? true : super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   public boolean charTyped(char chr, int modifiers) {
      if (!this.check3()) {
         this.clickGui.invoke5();
         return true;
      } else if (this.clickGui.check10()) {
         return true;
      } else {
         return this.clickGui.check7(chr) ? true : super.charTyped(chr, modifiers);
      }
   }

   public void close() {
      if (!this.clickGui.check8()) {
         this.invoke4();
      } else {
         net.minecraft.client.MinecraftClient mc = net.minecraft.client.MinecraftClient.getInstance();
         if (mc != null && mc.currentScreen == this) {
            mc.setScreen(null);
         }
      }
   }

   public void removed() {
      this.clickGui.invoke6();
      super.removed();
   }

   public boolean shouldPause() {
      return false;
   }

   public void invoke2(int i, int j) {
      if (i <= 0 || j <= 0) {
         this.clickGui.invoke5();
      }
   }

   public void invoke3(boolean bl) {
      if (!bl) {
         this.clickGui.invoke5();
      }
   }

   private float measure(double d) {
      if (this.client != null && this.client.getWindow() != null) {
         int intValue = this.client.getWindow().getFramebufferWidth();
         int intValue2 = this.client.getWindow().getScaledWidth();
         return intValue > 0 && intValue2 > 0 ? (float)(d * intValue / Math.max(1.0, (double)intValue2)) : (float)d;
      } else {
         return (float)d;
      }
   }

   private float measure2(double d) {
      if (this.client != null && this.client.getWindow() != null) {
         int intValue3 = this.client.getWindow().getFramebufferHeight();
         int intValue4 = this.client.getWindow().getScaledHeight();
         return intValue3 > 0 && intValue4 > 0 ? (float)(d * intValue3 / Math.max(1.0, (double)intValue4)) : (float)d;
      } else {
         return (float)d;
      }
   }

   private boolean check() {
      return this.client != null && this.client.getWindow() != null
         ? this.check2(this.client.getWindow().getFramebufferWidth(), this.client.getWindow().getFramebufferHeight())
         : false;
   }

   private boolean check2(int i, int j) {
      return this.client != null && this.client.getWindow() != null
         ? i > 0
            && j > 0
            && this.client.getWindow().getFramebufferWidth() > 0
            && this.client.getWindow().getFramebufferHeight() > 0
            && this.client.getWindow().getScaledWidth() > 0
            && this.client.getWindow().getScaledHeight() > 0
         : false;
   }

   private boolean check3() {
      return this.check() && this.client.isWindowFocused();
   }

   private int compute() {
      MenuModule menuModule = MenuModule.getInstance();
      return menuModule != null && menuModule.bindKey != -1 ? menuModule.bindKey : 344;
   }

   private void invoke4() {
      this.clickGui.invoke6();
      if (this.client != null) {
         this.client.setScreen(null);
      }
   }
}
