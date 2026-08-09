package ru.metaculture.protection;

import net.minecraft.util.math.MathHelper;

public class RotationController extends RotationListener {
   private static RotationController.RotationControllerState3 rotationControllerState3;
   private static RotationController.RotationControllerProvider rotationControllerProvider;
   public static RotationController.RotationControllerState2 rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
   public static float floatValue;
   public static float floatValue2;
   public static float floatValue3;
   public static float floatValue4;
   public static int intValue;
   public static int intValue2;
   public static int intValue3;
   public static Rotation rotation;
   public static boolean flag;

   public static void invoke(RotationController.RotationControllerState3 rotationControllerState3, Runnable runnable) {
      RotationController.rotationControllerState3 = rotationControllerState3;
      boolean flag = false ;

      try {
         flag = true;
         runnable.run();
         flag = false;
      } finally {
         if (flag) {
            rotationControllerState3 = null;
         }
      }

      rotationControllerState3 = null;
   }

   public static boolean check() {
      return !rotationControllerState2.equals(RotationController.RotationControllerState2.IDLE);
   }

   private void invoke2() {
      if (rotationControllerProvider != null) {
         RotationController.RotationControllerState rotationControllerState = rotationControllerProvider.nextStep();
         if (rotationControllerState != null && !rotationControllerState.flag && rotationControllerState.rotation != null) {
            check2(rotationControllerState.rotation, rotationControllerState.floatValue, rotationControllerState.floatValue2);
         } else {
            this.invoke10();
         }
      } else {
         Rotation rotation = new Rotation(FreeLookController.floatValue, FreeLookController.floatValue2);
         if (check2(rotation, floatValue3, floatValue4)) {
            this.invoke10();
         }
      }
   }

   @EventHandler
   public void onClientTick(ClientTickEvent clientTickEvent) {
      if (rotationControllerState2.equals(RotationController.RotationControllerState2.AIM) && intValue3 > intValue2) {
         if (flag) {
            this.invoke10();
         } else {
            rotationControllerState2 = RotationController.RotationControllerState2.RESET;
         }
      }

      if (rotationControllerState2.equals(RotationController.RotationControllerState2.RESET)) {
         this.invoke2();
      }

      intValue3++;
   }

   public static void invoke3(Rotation rotation2, float f, float g, float h, float i, int j, int k, boolean bl) {
      invoke5(rotation2, f, g, h, i, j, k, bl, null);
   }

   public static void invoke4(Rotation rotation3, float f, float g, float h, float i, int j, int k, boolean bl, RotationController.RotationControllerProvider rotationControllerProvider) {
      invoke5(rotation3, f, g, h, i, j, k, bl, rotationControllerProvider);
   }

   private static void invoke5(Rotation rotation4, float f, float g, float h, float i, int j, int k, boolean bl, RotationController.RotationControllerProvider rotationControllerProvider2) {
      if (rotationControllerState3 != null) {
         f = rotationControllerState3.floatValue;
         g = rotationControllerState3.floatValue2;
         h = rotationControllerState3.floatValue3;
         i = rotationControllerState3.floatValue4;
         bl = rotationControllerState3.flag;
      }

      if (intValue <= k) {
         if (RotationOffsetState.flag && rotation4 != null) {
            RotationOffsetState.invoke2();
            float floatValue = rotation4.floatValue + RotationOffsetState.floatValue;
            float floatValue2 = MathHelper.clamp(
               MathHelper.clamp(rotation4.floatValue2 + RotationOffsetState.floatValue2, RotationOffsetState.floatValue3, RotationOffsetState.floatValue4), -90.0F, 90.0F
            );
            rotation4 = new Rotation(floatValue, floatValue2);
         }

         if (bl) {
            FreeLookController.active = false;
            if (a_.player != null) {
               FreeLookController.floatValue = a_.player.getYaw();
               FreeLookController.floatValue2 = a_.player.getPitch();
            }
         } else if (rotationControllerState2.equals(RotationController.RotationControllerState2.IDLE)) {
            FreeLookController.active = true;
         }

         floatValue = f;
         floatValue2 = g;
         floatValue3 = h;
         floatValue4 = i;
         intValue2 = j;
         intValue = k;
         rotationControllerState2 = RotationController.RotationControllerState2.AIM;
         flag = bl;
         rotationControllerProvider = rotationControllerProvider2;
         rotation = rotation4;
         check2(rotation4, f, g);
      }
   }

   public static void invoke6(Rotation rotation5, float f, float g, float h, float i, int j, int k) {
      invoke3(rotation5, f, g, h, i, j, k, true);
   }

   public static void invoke7(Rotation rotation6, float f, float g, int i, int j) {
      invoke3(rotation6, f, f, g, g, i, j, false);
   }

   public static void invoke8(RotationController.RotationControllerProvider rotationControllerProvider3) {
      if (rotationControllerProvider3 != null && rotationControllerProvider == rotationControllerProvider3 && !rotationControllerState2.equals(RotationController.RotationControllerState2.IDLE)) {
         rotationControllerState2 = RotationController.RotationControllerState2.RESET;
         flag = false;
         intValue3 = 0;
      }
   }

   public static void invoke9(RotationController.RotationControllerProvider rotationControllerProvider4) {
      if (rotationControllerProvider == rotationControllerProvider4) {
         rotationControllerProvider = null;
      }
   }

   static boolean check2(Rotation rotation7, float f, float g) {
      if (a_.player == null) {
         return false;
      } else {
         Rotation rotation8 = new Rotation(a_.player);
         float floatValue3 = RenderMath.measure27(rotation7.floatValue - rotation8.floatValue);
         float floatValue4 = rotation7.floatValue2 - rotation8.floatValue2;
         float floatValue5 = Math.min(Math.abs(floatValue3), f);
         float floatValue6 = Math.min(Math.abs(floatValue4), g);
         a_.player.setYaw(a_.player.headYaw = a_.player.headYaw + MouseSensitivityUtils.measure(MathHelper.clamp(floatValue3, -floatValue5, floatValue5)));
         a_.player.setPitch(MathHelper.clamp(a_.player.getPitch() + MouseSensitivityUtils.measure(MathHelper.clamp(floatValue4, -floatValue6, floatValue6)), -90.0F, 90.0F));
         intValue3 = 0;
         return new Rotation(a_.player).measure(rotation7) < 1.0F;
      }
   }

   public void invoke10() {
      rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
      intValue = 0;
      flag = false;
      rotationControllerProvider = null;
      FreeLookController.active = FreeLookController.flag;
   }

   @FunctionalInterface
   public interface RotationControllerProvider {
      RotationController.RotationControllerState nextStep();
   }

   public static final class RotationControllerState {
      public final Rotation rotation;
      public final float floatValue;
      public final float floatValue2;
      public final boolean flag;

      public RotationControllerState(Rotation rotation9, float f, float g, boolean bl) {
         this.rotation = rotation9;
         this.floatValue = f;
         this.floatValue2 = g;
         this.flag = bl;
      }

      public static RotationController.RotationControllerState resolve() {
         return new RotationController.RotationControllerState(null, 0.0F, 0.0F, true);
      }
   }

   public static enum RotationControllerState2 {
      AIM,
      RESET,
      IDLE;
   }

   public static final class RotationControllerState3 {
      public final float floatValue;
      public final float floatValue2;
      public final float floatValue3;
      public final float floatValue4;
      public final boolean flag;

      public RotationControllerState3(float f, float g, float h, float i, boolean bl) {
         this.floatValue = f;
         this.floatValue2 = g;
         this.floatValue3 = h;
         this.floatValue4 = i;
         this.flag = bl;
      }
   }
}
