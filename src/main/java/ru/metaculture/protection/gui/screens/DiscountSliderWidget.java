package ru.metaculture.protection;

import net.minecraft.client.gui.widget.SliderWidget;
import net.minecraft.text.Text;

public final class DiscountSliderWidget extends SliderWidget {
   private final AutoBuy autoBuy;

   public DiscountSliderWidget(AutoBuy autoBuy, int i, int j, int k, int l) {
      super(i, j, k, l, resolve(autoBuy), measure(autoBuy));
      this.autoBuy = autoBuy;
   }

   protected void updateMessage() {
      this.setMessage(resolve(this.autoBuy));
   }

   protected void applyValue() {
      this.autoBuy.parsSkidka.invoke(measure2(this.autoBuy, this.value));
      this.updateMessage();
   }

   public void invoke() {
      this.value = measure(this.autoBuy);
      this.updateMessage();
   }

   private static Text resolve(AutoBuy autoBuy2) {
      return Text.literal("Discount: " + Math.round(autoBuy2.parsSkidka.getValue()) + "%");
   }

   private static double measure(AutoBuy autoBuy3) {
      float floatValue = autoBuy3.parsSkidka.minimum;
      float floatValue2 = autoBuy3.parsSkidka.maximum;
      return floatValue2 <= floatValue ? 0.0 : Math.max(0.0, Math.min(1.0, (double)((autoBuy3.parsSkidka.getValue() - floatValue) / (floatValue2 - floatValue))));
   }

   private static float measure2(AutoBuy autoBuy4, double d) {
      double doubleValue = Math.max(0.0, Math.min(1.0, d));
      float floatValue3 = autoBuy4.parsSkidka.minimum;
      float floatValue4 = autoBuy4.parsSkidka.maximum;
      float floatValue5 = (float)(floatValue3 + doubleValue * (floatValue4 - floatValue3));
      float floatValue6 = autoBuy4.parsSkidka.step;
      if (floatValue6 > 0.0F) {
         floatValue5 = Math.round(floatValue5 / floatValue6) * floatValue6;
      }

      return Math.max(floatValue3, Math.min(floatValue4, floatValue5));
   }
}
