package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.MathHelper;

public class AspectRatioController {
   private final double doubleValue;
   private final double doubleValue2;
   private int intValue;
   private int intValue2;
   private static int intValue3;

   public AspectRatioController(MinecraftClient minecraftClient) {
      this.intValue = minecraftClient.getWindow().getWidth();
      this.intValue2 = minecraftClient.getWindow().getHeight();
      intValue3 = 1;
      short shortValue = 2;
      if (shortValue == 0) {
         shortValue = 1000;
      }

      while (intValue3 < shortValue && this.intValue / (intValue3 + 1) >= 320 && this.intValue2 / (intValue3 + 1) >= 240) {
         intValue3++;
      }

      this.doubleValue = (double)this.intValue / intValue3;
      this.doubleValue2 = (double)this.intValue2 / intValue3;
      this.intValue = MathHelper.ceil(this.doubleValue);
      this.intValue2 = MathHelper.ceil(this.doubleValue2);
   }

   public int getIntValue() {
      return this.intValue;
   }

   public int getIntValue2() {
      return this.intValue2;
   }

   public int getIntValue3() {
      return this.intValue;
   }

   public int getIntValue22() {
      return this.intValue2;
   }

   public double getDoubleValue() {
      return this.doubleValue;
   }

   public double getDoubleValue2() {
      return this.doubleValue2;
   }

   public static int getIntValue32() {
      return intValue3;
   }
}
