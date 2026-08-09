package ru.metaculture.protection;

import lombok.Generated;
import net.minecraft.client.MinecraftClient;

public final class ClickGuiLayoutEngine {
   private final LayoutSpec layoutSpec;

   public Metrics resolve(MinecraftClient minecraftClient, ClickGuiState clickGuiState, ClickGuiGeometry clickGuiGeometry) {
      Metrics metrics = Metrics.resolve(minecraftClient, this.layoutSpec);
      if (minecraftClient != null && minecraftClient.getWindow() != null) {
         float floatValue = minecraftClient.getWindow().getFramebufferWidth();
         float floatValue2 = minecraftClient.getWindow().getFramebufferHeight();
         if (!(floatValue <= 0.0F) && !(floatValue2 <= 0.0F)) {
            clickGuiState.invoke54(metrics, floatValue, floatValue2);
            clickGuiState.invoke55(metrics, floatValue, floatValue2);
            this.invoke(metrics, clickGuiState, clickGuiGeometry);
            return metrics;
         } else {
            this.invoke(metrics, clickGuiState, clickGuiGeometry);
            return metrics;
         }
      } else {
         this.invoke(metrics, clickGuiState, clickGuiGeometry);
         return metrics;
      }
   }

   public Metrics resolve2(float f, float g, ClickGuiState clickGuiState2, ClickGuiGeometry clickGuiGeometry2) {
      Metrics metrics2 = Metrics.resolve3(f, g, 1.0F, this.layoutSpec);
      if (!(f <= 0.0F) && !(g <= 0.0F)) {
         clickGuiState2.invoke54(metrics2, f, g);
         clickGuiState2.invoke55(metrics2, f, g);
         this.invoke(metrics2, clickGuiState2, clickGuiGeometry2);
         return metrics2;
      } else {
         this.invoke(metrics2, clickGuiState2, clickGuiGeometry2);
         return metrics2;
      }
   }

   public Metrics resolve3(MinecraftClient minecraftClient, float f, float g, ClickGuiState clickGuiState3, ClickGuiGeometry clickGuiGeometry3) {
      Metrics metrics3 = minecraftClient == null ? Metrics.resolve2(f, g, this.layoutSpec) : Metrics.resolve(minecraftClient, this.layoutSpec);
      if (!(f <= 0.0F) && !(g <= 0.0F)) {
         clickGuiState3.invoke54(metrics3, f, g);
         clickGuiState3.invoke55(metrics3, f, g);
         this.invoke(metrics3, clickGuiState3, clickGuiGeometry3);
         return metrics3;
      } else {
         this.invoke(metrics3, clickGuiState3, clickGuiGeometry3);
         return metrics3;
      }
   }

   public void invoke(Metrics metrics4, ClickGuiState clickGuiState4, ClickGuiGeometry clickGuiGeometry4) {
      this.invoke2(metrics4, clickGuiState4, clickGuiGeometry4);
      this.invoke3(metrics4, clickGuiState4, clickGuiGeometry4);
   }

   private void invoke2(Metrics metrics5, ClickGuiState clickGuiState5, ClickGuiGeometry clickGuiGeometry5) {
      float floatValue3 = this.measure(clickGuiState5.getFloatValue3());
      float floatValue4 = this.measure(clickGuiState5.getFloatValue4());
      float floatValue5 = this.measure2(floatValue3, metrics5.getFloatValue3());
      float floatValue6 = this.measure(floatValue3 + metrics5.getFloatValue5());
      float floatValue7 = this.measure(floatValue4 + metrics5.getFloatValue5());
      float floatValue8 = this.measure2(floatValue6, metrics5.getFloatValue7());
      float floatValue9 = this.measure(floatValue6 + floatValue8 + metrics5.getFloatValue6());
      float floatValue10 = Math.max(0.0F, this.measure(floatValue3 + floatValue5 - metrics5.getFloatValue5()) - floatValue9);
      float floatValue11 = Math.max(0.0F, this.measure(floatValue4 + metrics5.getFloatValue4() - metrics5.getFloatValue5()) - floatValue7);
      float floatValue12 = this.measure2(floatValue7, metrics5.getFloatValue10());
      float floatValue13 = this.measure2(0.0F, metrics5.getFloatValue11());
      float floatValue14 = Math.max(0.0F, this.measure(floatValue10 - floatValue13 - metrics5.getFloatValue6()));
      float floatValue15 = this.measure(floatValue9 + floatValue14 + metrics5.getFloatValue6());
      float floatValue16 = this.measure(floatValue7 + floatValue12 + metrics5.getFloatValue6());
      float floatValue17 = Math.max(0.0F, this.measure(floatValue7 + floatValue11) - floatValue16);
      float floatValue18 = this.measure(floatValue9 + metrics5.getFloatValue13());
      float floatValue19 = this.measure(floatValue16 + metrics5.getFloatValue13());
      clickGuiGeometry5.setFloatValue(floatValue3);
      clickGuiGeometry5.setFloatValue2(floatValue4);
      clickGuiGeometry5.setFloatValue3(floatValue6);
      clickGuiGeometry5.setFloatValue4(floatValue7);
      clickGuiGeometry5.setFloatValue5(floatValue9);
      clickGuiGeometry5.setFloatValue6(floatValue7);
      clickGuiGeometry5.setFloatValue7(floatValue9);
      clickGuiGeometry5.setFloatValue8(floatValue7);
      clickGuiGeometry5.setFloatValue9(floatValue14);
      clickGuiGeometry5.setFloatValue10(floatValue15);
      clickGuiGeometry5.setFloatValue11(floatValue9);
      clickGuiGeometry5.setFloatValue12(floatValue16);
      clickGuiGeometry5.setFloatValue13(floatValue10);
      clickGuiGeometry5.setFloatValue14(floatValue17);
      clickGuiGeometry5.setFloatValue15(floatValue18);
      clickGuiGeometry5.setFloatValue16(floatValue19);
      clickGuiGeometry5.setFloatValue17(Math.max(0.0F, this.measure(floatValue9 + floatValue10 - metrics5.getFloatValue13()) - floatValue18));
      clickGuiGeometry5.setFloatValue18(Math.max(0.0F, this.measure(floatValue16 + floatValue17 - metrics5.getFloatValue13()) - floatValue19));
      clickGuiGeometry5.setFloatValue19(clickGuiGeometry5.getFloatValue15());
      clickGuiGeometry5.setFloatValue20(this.measure(clickGuiGeometry5.getFloatValue19() + metrics5.getFloatValue14() + metrics5.getFloatValue6()));
      clickGuiGeometry5.setFloatValue21(this.measure(clickGuiGeometry5.getFloatValue20() + metrics5.getFloatValue14() + metrics5.getFloatValue6()));
   }

   private void invoke3(Metrics metrics6, ClickGuiState clickGuiState6, ClickGuiGeometry clickGuiGeometry6) {
      clickGuiGeometry6.setFloatValue22(this.measure(clickGuiState6.getFloatValue5()));
      clickGuiGeometry6.setFloatValue23(this.measure(clickGuiState6.getFloatValue6()));
      clickGuiGeometry6.setFloatValue24(this.measure(clickGuiGeometry6.getFloatValue22() + metrics6.measure2(7.0F)));
      clickGuiGeometry6.setFloatValue25(this.measure(clickGuiGeometry6.getFloatValue23() + metrics6.measure2(63.0F)));
   }

   private float measure(float f) {
      return Math.round(f);
   }

   private float measure2(float f, float g) {
      return Math.max(0.0F, this.measure(f + g) - this.measure(f));
   }

   @Generated
   public ClickGuiLayoutEngine(LayoutSpec layoutSpec) {
      this.layoutSpec = layoutSpec;
   }
}
