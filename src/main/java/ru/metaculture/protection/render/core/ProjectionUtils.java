package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.util.math.MathHelper;

public class ProjectionUtils {
   private final double doubleValue;
   private final double doubleValue2;
   private int intValue;
   private int intValue2;
   private static int intValue3;

   public ProjectionUtils(MinecraftClient minecraftClient) {
      if (minecraftClient != null && minecraftClient.getWindow() != null) {
         this.intValue = minecraftClient.getWindow().getWidth();
         this.intValue2 = minecraftClient.getWindow().getHeight();
         intValue3 = 1;
         boolean flag = false;

         try {
            SimpleOption simpleOption = minecraftClient.options.getForceUnicodeFont();
            flag = simpleOption != null && Boolean.TRUE.equals(simpleOption.getValue());
         } catch (Exception exception) {
         }

         byte byteValue = 2;

         while (intValue3 < byteValue && this.intValue / (intValue3 + 1) >= 320 && this.intValue2 / (intValue3 + 1) >= 240) {
            intValue3++;
         }

         if (flag && intValue3 % 2 != 0 && intValue3 != 1) {
            intValue3--;
         }

         this.doubleValue = (double)this.intValue / intValue3;
         this.doubleValue2 = (double)this.intValue2 / intValue3;
         this.intValue = MathHelper.ceil(this.doubleValue);
         this.intValue2 = MathHelper.ceil(this.doubleValue2);
      } else {
         this.intValue = 1920;
         this.intValue2 = 1080;
         intValue3 = 1;
         this.doubleValue = this.intValue;
         this.doubleValue2 = this.intValue2;
      }
   }

   public int getIntValue() {
      return this.intValue;
   }

   public int getIntValue2() {
      return this.intValue2;
   }

   public double getDoubleValue() {
      return this.doubleValue;
   }

   public double getDoubleValue2() {
      return this.doubleValue2;
   }

   public static int getIntValue3() {
      return intValue3;
   }
}
