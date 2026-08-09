package ru.metaculture.protection;

import net.minecraft.util.math.MathHelper;

public final class RotationOffsetState {
   public static boolean flag;
   public static float floatValue;
   public static float floatValue2;
   public static float floatValue3 = -90.0F;
   public static float floatValue4 = 90.0F;
   private static float floatValue5;
   private static float floatValue6;
   private static float floatValue7;
   private static float floatValue8;
   private static float floatValue9 = 1.0F;

   private RotationOffsetState() {
   }

   public static void invoke(float f, float g, float h) {
      floatValue5 = f;
      floatValue6 = g;
      floatValue9 = MathHelper.clamp(h, 0.05F, 1.0F);
   }

   public static void invoke2() {
      if (!flag) {
         floatValue7 = 0.0F;
         floatValue8 = 0.0F;
         floatValue = 0.0F;
         floatValue2 = 0.0F;
      } else {
         floatValue7 = floatValue7 + (floatValue5 - floatValue7) * floatValue9;
         floatValue8 = floatValue8 + (floatValue6 - floatValue8) * floatValue9;
         floatValue = floatValue7;
         floatValue2 = floatValue8;
      }
   }

   public static void invoke3() {
      flag = false;
      floatValue = 0.0F;
      floatValue2 = 0.0F;
      floatValue3 = -90.0F;
      floatValue4 = 90.0F;
      floatValue5 = 0.0F;
      floatValue6 = 0.0F;
      floatValue7 = 0.0F;
      floatValue8 = 0.0F;
      floatValue9 = 1.0F;
   }
}
