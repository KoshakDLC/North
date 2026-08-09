package ru.metaculture.protection;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

public final class NeuroRotationController implements MinecraftAccessor {
   private static final int INT_VALUE = 18;
   private static final long TIMESTAMP = 700L;
   private static float floatValue = 34.0F;
   private static float floatValue2 = 22.0F;
   private static final RotationAssetLoader ROTATION_ASSET_LOADER = new RotationAssetLoader();
   private static RotationLabModule.RotationLabModuleState rotationLabModuleState;
   private static final NeuroRotationController.NeuroRotationControllerState NEURO_ROTATION_CONTROLLER_STATE = new NeuroRotationController.NeuroRotationControllerState();
   private static int intValue = -1;
   private static int intValue2;
   private static int intValue3;
   private static String neuroIdle = "Neuro idle";
   private static long timestamp;
   private static boolean flag;

   private NeuroRotationController() {
   }

   public static void invoke(LivingEntity livingEntity, boolean bl, boolean bl2) {
      invoke2(livingEntity, bl, bl2, false);
   }

   public static void invoke2(LivingEntity livingEntity, boolean bl, boolean bl2, boolean bl3) {
      if (a_.player != null && a_.world != null && livingEntity != null) {
         intValue3++;
         invoke7(livingEntity);
         Rotation rotation = resolve7(livingEntity, NEURO_ROTATION_CONTROLLER_STATE);
         Rotation rotation2 = new Rotation(a_.player);
         float floatValue = MathHelper.wrapDegrees(rotation.floatValue - rotation2.floatValue);
         float floatValue2 = rotation.floatValue2 - rotation2.floatValue2;
         float floatValue3 = (float)Math.hypot(floatValue, floatValue2);
         boolean flag = bl && !bl2;
         invoke8(floatValue3, floatValue, floatValue2, flag, bl2);
         if (check(livingEntity, floatValue3, flag)) {
            invoke6(livingEntity, floatValue, floatValue2, flag);
         }

         RotationLabModule.RotationLabModuleState2 rotationLabModuleState2 = resolve2();
         float floatValue4 = measure5(floatValue3, flag, bl2, rotationLabModuleState2 != null);
         float floatValue5 = measure4(rotationLabModuleState2, floatValue3, flag, bl2);
         NeuroRotationController.NeuroRotationControllerData neuroRotationControllerData = resolve4(rotationLabModuleState2, floatValue, floatValue2, floatValue3, floatValue4, flag);
         NeuroRotationController.NeuroRotationControllerData2 neuroRotationControllerData2 = resolve3(floatValue, floatValue2, neuroRotationControllerData, floatValue3, floatValue5, flag, bl2);
         float floatValue6 = neuroRotationControllerData2.targetYawVelocity;
         float floatValue7 = neuroRotationControllerData2.targetPitchVelocity;
         float floatValue8 = neuroRotationControllerData2.yaw;
         float floatValue9 = neuroRotationControllerData2.pitch;
         float floatValue10 = rotation2.floatValue + floatValue8;
         float floatValue11 = MathHelper.clamp(rotation2.floatValue2 + floatValue9, -90.0F, 90.0F);
         float floatValue12 = Math.max(0.18F, Math.abs(floatValue8));
         float floatValue13 = Math.max(0.14F, Math.abs(floatValue9));
         flag = RotationController.intValue <= 18;
         RotationController.invoke3(new Rotation(floatValue10, floatValue11), floatValue12, floatValue13, 30.0F, 30.0F, 1, 18, false);
         if (rotationLabModuleState2 != null) {
            intValue2++;
         }

         NEURO_ROTATION_CONTROLLER_STATE.floatValue12 = floatValue;
         NEURO_ROTATION_CONTROLLER_STATE.floatValue13 = floatValue2;
         NEURO_ROTATION_CONTROLLER_STATE.flag = true;
         neuroIdle = "Neuro humanize " + ROTATION_ASSET_LOADER.compute() + "p";
         invoke9(
            bl3,
            "aim target="
               + livingEntity.getId()
               + " type="
               + resolve5()
               + " sample="
               + intValue2
               + "/"
               + compute()
               + " yawErr="
               + resolve6(floatValue)
               + " pitchErr="
               + resolve6(floatValue2)
               + " yawBase="
               + resolve6(floatValue6)
               + " pitchBase="
               + resolve6(floatValue7)
               + " humanYaw="
               + resolve6(neuroRotationControllerData.yaw)
               + " humanPitch="
               + resolve6(neuroRotationControllerData.pitch)
               + " yawStep="
               + resolve6(floatValue8)
               + " pitchStep="
               + resolve6(floatValue9)
               + " speed="
               + resolve6(floatValue5)
               + " focus="
               + resolve6(NEURO_ROTATION_CONTROLLER_STATE.floatValue)
               + "/"
               + resolve6(NEURO_ROTATION_CONTROLLER_STATE.floatValue3)
               + "/"
               + resolve6(NEURO_ROTATION_CONTROLLER_STATE.floatValue2)
               + " cfg="
               + resolve6(measure9())
               + "/"
               + resolve6(measure12())
               + " hold="
               + NEURO_ROTATION_CONTROLLER_STATE.intValue4
               + " attack="
               + flag
               + " blocked="
               + bl2
         );
      } else {
         invoke4(AttackAura.neuroClientFinish.isEnabled());
         neuroIdle = "Neuro idle";
         invoke9(bl3, "idle target=" + (livingEntity == null ? "null" : livingEntity.getId()));
      }
   }

   public static void invoke3() {
      rotationLabModuleState = null;
      intValue = -1;
      intValue2 = 0;
      NEURO_ROTATION_CONTROLLER_STATE.invoke();
      flag = false;
      neuroIdle = "Neuro reset";
   }

   public static void invoke4(boolean bl) {
      if (!flag) {
         invoke3();
      } else {
         if (a_.player != null) {
            floatValue = a_.player.getYaw();
            floatValue2 = a_.player.getPitch();
            if (bl) {
               a_.player.setYaw(a_.player.getYaw());
               a_.player.setPitch(a_.player.getPitch());
               a_.player.headYaw = a_.player.getYaw();
            }
         }

         RotationController.rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
         RotationController.intValue = 0;
         RotationController.flag = false;
         RotationController.rotation = null;
         RotationController.intValue3 = 0;
         FreeLookController.active = bl ? false : FreeLookController.flag;
         invoke3();
      }
   }

   public static void invoke5() {
      invoke3();
   }

   public static String resolve() {
      return neuroIdle + " / " + ROTATION_ASSET_LOADER.resolve2();
   }

   private static boolean check(LivingEntity livingEntity, float f, boolean bl) {
      if (ROTATION_ASSET_LOADER.compute() == 0) {
         rotationLabModuleState = null;
         intValue = -1;
         intValue2 = 0;
         return false;
      } else if (rotationLabModuleState == null || livingEntity.getId() != intValue) {
         return true;
      } else if (rotationLabModuleState.items == null || intValue2 >= rotationLabModuleState.items.size()) {
         return true;
      } else {
         return bl && !"Attack".equalsIgnoreCase(rotationLabModuleState.mixed) && intValue2 > 2
            ? true
            : f < 5.0F && "Flick".equalsIgnoreCase(rotationLabModuleState.mixed) && intValue2 > 2;
      }
   }

   private static void invoke6(LivingEntity livingEntity, float f, float g, boolean bl) {
      rotationLabModuleState = ROTATION_ASSET_LOADER.resolve(MathHelper.clamp(f, -45.0F, 45.0F), MathHelper.clamp(g, -30.0F, 30.0F), bl);
      intValue = livingEntity.getId();
      intValue2 = 0;
   }

   private static RotationLabModule.RotationLabModuleState2 resolve2() {
      return rotationLabModuleState != null && rotationLabModuleState.items != null && !rotationLabModuleState.items.isEmpty()
         ? rotationLabModuleState.items.get(Math.min(intValue2, rotationLabModuleState.items.size() - 1))
         : null;
   }

   private static float measure(float f, boolean bl, boolean bl2, boolean bl3) {
      float floatValue14 = Math.abs(f);
      if (floatValue14 < 0.001F) {
         return 0.0F;
      } else {
         float floatValue15 = bl ? 0.62F : 0.42F;
         float floatValue16 = bl ? 34.0F : 22.0F;
         float floatValue17 = bl ? 0.31F : 0.25F;
         if (bl2) {
            floatValue16 *= 1.12F;
            floatValue17 *= 1.08F;
         }

         if (bl3) {
            floatValue16 *= 0.82F;
            floatValue17 *= 0.88F;
         }

         if (floatValue14 > 95.0F) {
            floatValue17 += bl ? 0.08F : 0.05F;
         }

         float floatValue18 = floatValue14 * floatValue17 + floatValue15;
         if (floatValue14 < 3.0F) {
            floatValue18 = Math.max(floatValue15 * 0.45F, floatValue14 * 0.68F);
         }

         floatValue18 = MathHelper.clamp(floatValue18, floatValue15 * 0.45F, floatValue16);
         floatValue18 = Math.min(floatValue18, floatValue14);
         return Math.signum(f) * floatValue18;
      }
   }

   private static NeuroRotationController.NeuroRotationControllerData2 resolve3(float f, float g, NeuroRotationController.NeuroRotationControllerData neuroRotationControllerData3, float h, float i, boolean bl, boolean bl2) {
      float floatValue19 = NEURO_ROTATION_CONTROLLER_STATE.floatValue14 + neuroRotationControllerData3.yaw * 0.72F;
      float floatValue20 = NEURO_ROTATION_CONTROLLER_STATE.floatValue15 + neuroRotationControllerData3.pitch * 0.68F;
      float floatValue21 = measure2(floatValue19, true, h, i, bl, bl2);
      float floatValue22 = measure2(floatValue20, false, h, i, bl, bl2);
      float floatValue23 = measure15(3.4F, 2.75F, 3.15F) * (bl ? 1.12F : 1.0F);
      float floatValue24 = measure15(2.4F, 1.95F, 2.25F) * (bl ? 1.1F : 1.0F);
      if (bl2) {
         floatValue23 *= 0.78F;
         floatValue24 *= 0.78F;
      }

      NEURO_ROTATION_CONTROLLER_STATE.floatValue16 = measure3(NEURO_ROTATION_CONTROLLER_STATE.floatValue16, floatValue21, floatValue23);
      NEURO_ROTATION_CONTROLLER_STATE.floatValue17 = measure3(NEURO_ROTATION_CONTROLLER_STATE.floatValue17, floatValue22, floatValue24);
      float floatValue25 = measure6(NEURO_ROTATION_CONTROLLER_STATE.floatValue16, f, true, h);
      float floatValue26 = measure6(NEURO_ROTATION_CONTROLLER_STATE.floatValue17, g, false, h);
      NEURO_ROTATION_CONTROLLER_STATE.floatValue16 = floatValue25 * 0.86F + NEURO_ROTATION_CONTROLLER_STATE.floatValue16 * 0.14F;
      NEURO_ROTATION_CONTROLLER_STATE.floatValue17 = floatValue26 * 0.86F + NEURO_ROTATION_CONTROLLER_STATE.floatValue17 * 0.14F;
      return new NeuroRotationController.NeuroRotationControllerData2(floatValue25, floatValue26, floatValue21, floatValue22);
   }

   private static float measure2(float f, boolean bl, float g, float h, boolean bl2, boolean bl3) {
      float floatValue27 = Math.abs(f);
      if (floatValue27 < 0.001F) {
         return 0.0F;
      } else {
         float floatValue28 = (bl ? 34.0F : 22.0F) * measure15(0.72F, 0.64F, 0.72F);
         float floatValue29 = (float)Math.sqrt(floatValue27) * (bl ? 2.15F : 1.55F);
         float floatValue30 = floatValue27 * (bl ? 0.052F : 0.04F);
         float floatValue31 = floatValue29 + floatValue30;
         if (floatValue27 < 7.0F) {
            floatValue31 = floatValue27 * measure15(0.54F, 0.43F, 0.48F) + (bl ? 0.12F : 0.08F);
         }

         if (NEURO_ROTATION_CONTROLLER_STATE.intValue4 > 0 && g < 30.0F) {
            floatValue31 *= bl2 ? 0.62F : 0.42F;
         }

         if (bl3) {
            floatValue31 *= 0.76F;
         }

         floatValue31 *= h;
         floatValue31 = MathHelper.clamp(floatValue31, 0.0F, floatValue28);
         return Math.signum(f) * Math.min(floatValue31, floatValue27 + (g < 7.0F ? (bl ? 0.8F : 0.45F) : 0.0F));
      }
   }

   private static float measure3(float f, float g, float h) {
      float floatValue32 = g - f;
      float floatValue33 = h + Math.abs(f) * 0.16F;
      return f + MathHelper.clamp(floatValue32, -floatValue33, floatValue33);
   }

   private static float measure4(RotationLabModule.RotationLabModuleState2 rotationLabModuleState22, float f, boolean bl, boolean bl2) {
      float floatValue34 = f > 80.0F ? 1.08F : 0.96F;
      if (rotationLabModuleState22 != null) {
         float floatValue35 = Math.abs(rotationLabModuleState22.floatValue3)
            + Math.abs(rotationLabModuleState22.floatValue4)
            + Math.abs(rotationLabModuleState22.floatValue5) * 0.035F
            + Math.abs(rotationLabModuleState22.floatValue6) * 0.03F;
         floatValue34 = 0.78F + MathHelper.clamp(floatValue35 / 7.0F, 0.0F, 1.0F) * 0.48F;
         if (rotationLabModuleState22.floatValue7 > 0.72F) {
            floatValue34 -= 0.04F;
         }
      } else {
         floatValue34 += (float)Math.sin(intValue3 * 0.31F) * 0.04F;
      }

      float floatValue36 = measure9();
      floatValue34 += (float)Math.sin(NEURO_ROTATION_CONTROLLER_STATE.intValue2 * 0.23F + NEURO_ROTATION_CONTROLLER_STATE.floatValue7) * 0.065F * floatValue36;
      floatValue34 += (float)Math.sin(NEURO_ROTATION_CONTROLLER_STATE.intValue2 * 0.071F + NEURO_ROTATION_CONTROLLER_STATE.floatValue7 * 0.43F) * 0.035F * floatValue36;
      if (NEURO_ROTATION_CONTROLLER_STATE.intValue4 > 0 && f < 28.0F) {
         floatValue34 *= bl ? 0.74F : 0.58F;
      }

      if (bl) {
         floatValue34 += 0.08F;
      }

      if (bl2) {
         floatValue34 -= 0.12F;
      }

      return MathHelper.clamp(floatValue34, 0.72F, 1.28F);
   }

   private static float measure5(float f, boolean bl, boolean bl2, boolean bl3) {
      float floatValue37;
      if (f > 90.0F) {
         floatValue37 = 0.52F;
      } else if (f > 35.0F) {
         floatValue37 = 0.74F;
      } else if (f > 8.0F) {
         floatValue37 = 1.0F;
      } else {
         floatValue37 = 0.86F;
      }

      if (bl) {
         floatValue37 *= 1.08F;
      }

      if (bl2) {
         floatValue37 *= 0.55F;
      }

      if (!bl3) {
         floatValue37 *= 0.45F;
      }

      return MathHelper.clamp(floatValue37 * measure9(), 0.0F, 2.25F);
   }

   private static NeuroRotationController.NeuroRotationControllerData resolve4(RotationLabModule.RotationLabModuleState2 rotationLabModuleState23, float f, float g, float h, float i, boolean bl) {
      float floatValue38 = measure7(f);
      float floatValue39 = measure7(g);
      float floatValue40 = 0.0F;
      float floatValue41 = 0.0F;
      if (rotationLabModuleState23 != null) {
         float floatValue42 = measure11();
         float floatValue43 = Math.abs(rotationLabModuleState23.floatValue3) * floatValue38 * 0.14F
            + rotationLabModuleState23.floatValue3 * 0.045F
            + Math.signum(rotationLabModuleState23.floatValue3) * Math.min(Math.abs(rotationLabModuleState23.floatValue5) * 0.012F, 0.42F);
         float floatValue44 = Math.abs(rotationLabModuleState23.floatValue4) * floatValue39 * 0.115F
            + rotationLabModuleState23.floatValue4 * 0.036F
            + Math.signum(rotationLabModuleState23.floatValue4) * Math.min(Math.abs(rotationLabModuleState23.floatValue6) * 0.01F, 0.34F);
         floatValue40 += MathHelper.clamp(floatValue43 * floatValue42, -4.2F, 4.2F);
         floatValue41 += MathHelper.clamp(floatValue44 * floatValue42, -2.75F, 2.75F);
         if (rotationLabModuleState != null && rotationLabModuleState23.floatValue7 > 0.82F && h < 10.0F) {
            floatValue40 += floatValue38 * MathHelper.clamp(Math.abs(rotationLabModuleState.floatValue5) * 0.28F * floatValue42, 0.0F, bl ? 1.65F : 0.95F);
            floatValue41 += floatValue39 * MathHelper.clamp(Math.abs(rotationLabModuleState.floatValue6) * 0.22F * floatValue42, 0.0F, bl ? 1.05F : 0.65F);
         }
      }

      float floatValue45 = measure10();
      float floatValue46 = (
            (float)Math.sin(NEURO_ROTATION_CONTROLLER_STATE.intValue2 * 0.81F + NEURO_ROTATION_CONTROLLER_STATE.floatValue8) * 0.26F
               + (float)Math.sin(NEURO_ROTATION_CONTROLLER_STATE.intValue2 * 1.37F + NEURO_ROTATION_CONTROLLER_STATE.floatValue8 * 0.7F) * 0.11F
         )
         * floatValue38
         * floatValue45;
      float floatValue47 = (
            (float)Math.sin(NEURO_ROTATION_CONTROLLER_STATE.intValue2 * 0.67F + NEURO_ROTATION_CONTROLLER_STATE.floatValue8 * 1.3F) * 0.18F
               + (float)Math.sin(NEURO_ROTATION_CONTROLLER_STATE.intValue2 * 1.11F + NEURO_ROTATION_CONTROLLER_STATE.floatValue8 * 0.4F) * 0.075F
         )
         * floatValue39
         * floatValue45;
      float floatValue48 = (float)Math.sin(NEURO_ROTATION_CONTROLLER_STATE.intValue2 * 0.097F + NEURO_ROTATION_CONTROLLER_STATE.floatValue9) * 0.34F;
      float floatValue49 = (float)Math.sin(NEURO_ROTATION_CONTROLLER_STATE.intValue2 * 0.083F + NEURO_ROTATION_CONTROLLER_STATE.floatValue9 * 0.62F) * 0.22F;
      float floatValue50 = MathHelper.clamp(Math.abs(g) * 0.008F, 0.0F, 0.32F) * floatValue38;
      float floatValue51 = MathHelper.clamp(Math.abs(f) * 0.005F, 0.0F, 0.22F) * floatValue39;
      float floatValue52 = h < 7.0F ? 1.28F : (h < 18.0F ? 1.12F : (h > 65.0F ? 0.72F : 1.0F));
      floatValue40 += floatValue46 + floatValue48 + floatValue50 + NEURO_ROTATION_CONTROLLER_STATE.floatValue10;
      floatValue41 += floatValue47 + floatValue49 + floatValue51 + NEURO_ROTATION_CONTROLLER_STATE.floatValue11;
      return new NeuroRotationController.NeuroRotationControllerData(floatValue40 * i * floatValue52, floatValue41 * i * floatValue52);
   }

   private static float measure6(float f, float g, boolean bl, float h) {
      float floatValue53 = Math.abs(g);
      if (floatValue53 < 0.001F) {
         return 0.0F;
      } else {
         float floatValue54 = bl ? 39.44F : 24.64F;
         if (floatValue53 > 2.8F && Math.signum(f) != Math.signum(g)) {
            f = Math.signum(g) * Math.min(floatValue53, bl ? 0.34F : 0.24F);
         }

         float floatValue55 = h < 7.0F ? (bl ? 0.95F : 0.55F) : 0.0F;
         float floatValue56 = Math.min(floatValue54, floatValue53 + floatValue55);
         f = MathHelper.clamp(f, -floatValue56, floatValue56);
         if (h > 5.0F && Math.abs(f) > floatValue53) {
            f = g;
         }

         return f;
      }
   }

   private static float measure7(float f) {
      return f < 0.0F ? -1.0F : 1.0F;
   }

   private static String resolve5() {
      return rotationLabModuleState == null ? "synthetic" : rotationLabModuleState.mixed;
   }

   private static int compute() {
      return rotationLabModuleState != null && rotationLabModuleState.items != null ? rotationLabModuleState.items.size() : 0;
   }

   private static void invoke7(LivingEntity livingEntity) {
      if (NEURO_ROTATION_CONTROLLER_STATE.intValue != livingEntity.getId()) {
         NEURO_ROTATION_CONTROLLER_STATE.invoke();
         NEURO_ROTATION_CONTROLLER_STATE.intValue = livingEntity.getId();
         float floatValue57 = measure12();
         float floatValue58 = measure14();
         NEURO_ROTATION_CONTROLLER_STATE.floatValue = measure8(-0.16F * floatValue57, 0.16F * floatValue57);
         NEURO_ROTATION_CONTROLLER_STATE.floatValue2 = measure8(-0.1F * floatValue57, 0.12F * floatValue57);
         NEURO_ROTATION_CONTROLLER_STATE.floatValue3 = measure8(0.58F - 0.06F * floatValue58, 0.58F + 0.1F * floatValue58);
         NEURO_ROTATION_CONTROLLER_STATE.floatValue4 = measure8(-0.24F * floatValue57, 0.24F * floatValue57);
         NEURO_ROTATION_CONTROLLER_STATE.floatValue5 = measure8(-0.16F * floatValue57, 0.18F * floatValue57);
         NEURO_ROTATION_CONTROLLER_STATE.floatValue6 = measure8(0.58F - 0.1F * floatValue58, 0.58F + 0.16F * floatValue58);
         NEURO_ROTATION_CONTROLLER_STATE.intValue3 = compute2(false);
         NEURO_ROTATION_CONTROLLER_STATE.floatValue7 = measure8(0.0F, (float) (Math.PI * 2));
         NEURO_ROTATION_CONTROLLER_STATE.floatValue8 = measure8(0.0F, (float) (Math.PI * 2));
         NEURO_ROTATION_CONTROLLER_STATE.floatValue9 = measure8(0.0F, (float) (Math.PI * 2));
      }
   }

   private static void invoke8(float f, float g, float h, boolean bl, boolean bl2) {
      NEURO_ROTATION_CONTROLLER_STATE.intValue2++;
      if (!NEURO_ROTATION_CONTROLLER_STATE.flag2) {
         NEURO_ROTATION_CONTROLLER_STATE.floatValue14 = g;
         NEURO_ROTATION_CONTROLLER_STATE.floatValue15 = h;
         NEURO_ROTATION_CONTROLLER_STATE.flag2 = true;
      } else {
         float floatValue59 = measure15(0.66F, 0.48F, 0.55F);
         if (bl) {
            floatValue59 += 0.08F;
         }

         if (bl2) {
            floatValue59 *= 0.72F;
         }

         NEURO_ROTATION_CONTROLLER_STATE.floatValue14 = NEURO_ROTATION_CONTROLLER_STATE.floatValue14
            + MathHelper.wrapDegrees(g - NEURO_ROTATION_CONTROLLER_STATE.floatValue14) * MathHelper.clamp(floatValue59, 0.18F, 0.86F);
         NEURO_ROTATION_CONTROLLER_STATE.floatValue15 = NEURO_ROTATION_CONTROLLER_STATE.floatValue15 + (h - NEURO_ROTATION_CONTROLLER_STATE.floatValue15) * MathHelper.clamp(floatValue59, 0.18F, 0.86F);
      }

      if (NEURO_ROTATION_CONTROLLER_STATE.intValue3-- <= 0) {
         float floatValue60 = measure12();
         float floatValue61 = measure14();
         NEURO_ROTATION_CONTROLLER_STATE.floatValue4 = measure8(-0.3F * floatValue60, 0.3F * floatValue60);
         NEURO_ROTATION_CONTROLLER_STATE.floatValue5 = measure8(-0.2F * floatValue60, 0.22F * floatValue60);
         NEURO_ROTATION_CONTROLLER_STATE.floatValue6 = measure8(0.58F - 0.12F * floatValue61, bl ? 0.58F + 0.22F * floatValue61 : 0.58F + 0.16F * floatValue61);
         NEURO_ROTATION_CONTROLLER_STATE.intValue3 = compute2(bl);
      }

      float floatValue62 = (bl ? 0.16F : 0.095F) * measure13();
      NEURO_ROTATION_CONTROLLER_STATE.floatValue = NEURO_ROTATION_CONTROLLER_STATE.floatValue + (NEURO_ROTATION_CONTROLLER_STATE.floatValue4 - NEURO_ROTATION_CONTROLLER_STATE.floatValue) * floatValue62;
      NEURO_ROTATION_CONTROLLER_STATE.floatValue2 = NEURO_ROTATION_CONTROLLER_STATE.floatValue2 + (NEURO_ROTATION_CONTROLLER_STATE.floatValue5 - NEURO_ROTATION_CONTROLLER_STATE.floatValue2) * floatValue62;
      NEURO_ROTATION_CONTROLLER_STATE.floatValue3 = NEURO_ROTATION_CONTROLLER_STATE.floatValue3 + (NEURO_ROTATION_CONTROLLER_STATE.floatValue6 - NEURO_ROTATION_CONTROLLER_STATE.floatValue3) * floatValue62;
      if (NEURO_ROTATION_CONTROLLER_STATE.intValue4 > 0) {
         NEURO_ROTATION_CONTROLLER_STATE.intValue4--;
      } else if (!bl2 && f > 2.2F && f < 24.0F) {
         float floatValue63 = bl ? 0.012F : 0.034F;
         if (ThreadLocalRandom.current().nextFloat() < floatValue63) {
            NEURO_ROTATION_CONTROLLER_STATE.intValue4 = ThreadLocalRandom.current().nextInt(1, bl ? 3 : 4);
         }
      }

      if (NEURO_ROTATION_CONTROLLER_STATE.flag && f < 32.0F) {
         float floatValue64 = Math.abs(g) - Math.abs(NEURO_ROTATION_CONTROLLER_STATE.floatValue12);
         float floatValue65 = Math.abs(h) - Math.abs(NEURO_ROTATION_CONTROLLER_STATE.floatValue13);
         if (floatValue64 > 0.8F) {
            NEURO_ROTATION_CONTROLLER_STATE.floatValue10 = NEURO_ROTATION_CONTROLLER_STATE.floatValue10 - Math.signum(g) * MathHelper.clamp(floatValue64 * 0.075F, 0.0F, 0.52F);
         }

         if (floatValue65 > 0.65F) {
            NEURO_ROTATION_CONTROLLER_STATE.floatValue11 = NEURO_ROTATION_CONTROLLER_STATE.floatValue11 - Math.signum(h) * MathHelper.clamp(floatValue65 * 0.055F, 0.0F, 0.34F);
         }
      }

      NEURO_ROTATION_CONTROLLER_STATE.floatValue10 *= bl ? 0.76F : 0.68F;
      NEURO_ROTATION_CONTROLLER_STATE.floatValue11 *= bl ? 0.74F : 0.66F;
   }

   private static void invoke9(boolean bl, String string) {
      if (bl) {
         long longValue = System.currentTimeMillis();
         String text = "[Neuro] " + string;

         try {
            Path path = RotationAssetLoader.resolve3().resolve("neuro_debug.log");
            Files.createDirectories(path.getParent());
            Files.writeString(path, longValue + " " + text + System.lineSeparator(), StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
         } catch (Throwable exception) {
         }

         if (longValue - timestamp >= 700L) {
            timestamp = longValue;
            ChatUtil.sendClientMessage(text);
         }
      }
   }

   private static String resolve6(float f) {
      return String.format(Locale.ROOT, "%.2f", f);
   }

   private static Rotation resolve7(LivingEntity livingEntity, NeuroRotationController.NeuroRotationControllerState neuroRotationControllerState) {
      Vec3d vec3d = a_.player.getEyePos();
      Box box = livingEntity.getBoundingBox();
      Vec3d vec3d2 = livingEntity.getPos();
      Vec3d vec3d3 = a_.player.getPos().subtract(vec3d2);
      double doubleValue = Math.hypot(vec3d3.x, vec3d3.z);
      double doubleValue2 = doubleValue > 1.0E-4 ? vec3d3.x / doubleValue : 0.0;
      double doubleValue3 = doubleValue > 1.0E-4 ? vec3d3.z / doubleValue : 1.0;
      double doubleValue4 = doubleValue > 1.0E-4 ? vec3d3.z / doubleValue : 1.0;
      double doubleValue5 = doubleValue > 1.0E-4 ? -vec3d3.x / doubleValue : 0.0;
      double doubleValue6 = neuroRotationControllerState.floatValue * Math.max(0.25, (double)livingEntity.getWidth());
      double doubleValue7 = neuroRotationControllerState.floatValue2 * Math.max(0.25, (double)livingEntity.getWidth());
      double doubleValue8 = box.minY + livingEntity.getHeight() * 0.22;
      double doubleValue9 = box.minY + livingEntity.getHeight() * 0.84;
      Vec3d vec3d4 = new Vec3d(
         MathHelper.clamp(vec3d2.x + doubleValue4 * doubleValue6 + doubleValue2 * doubleValue7, box.minX, box.maxX),
         MathHelper.clamp(box.minY + livingEntity.getHeight() * neuroRotationControllerState.floatValue3, doubleValue8, doubleValue9),
         MathHelper.clamp(vec3d2.z + doubleValue5 * doubleValue6 + doubleValue3 * doubleValue7, box.minZ, box.maxZ)
      );
      Vec3d vec3d5 = vec3d4.subtract(vec3d);
      float floatValue66 = (float)Math.toDegrees(Math.atan2(-vec3d5.x, vec3d5.z));
      float floatValue67 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d5.y, Math.hypot(vec3d5.x, vec3d5.z))), -90.0, 90.0);
      return new Rotation(floatValue66, floatValue67);
   }

   private static float measure8(float f, float g) {
      return f + ThreadLocalRandom.current().nextFloat() * (g - f);
   }

   private static int compute2(boolean bl) {
      float floatValue68 = measure13();
      int intValue = Math.max(2, Math.round((bl ? 5.0F : 7.0F) / floatValue68));
      int intValue2 = Math.max(intValue + 1, Math.round((bl ? 14.0F : 22.0F) / floatValue68));
      return ThreadLocalRandom.current().nextInt(intValue, intValue2 + 1);
   }

   private static float measure9() {
      return measure15(1.05F, 1.45F, 1.75F);
   }

   private static float measure10() {
      return measure15(0.78F, 1.18F, 1.42F);
   }

   private static float measure11() {
      return measure15(1.0F, 1.35F, 1.65F);
   }

   private static float measure12() {
      return measure15(0.9F, 1.32F, 1.58F);
   }

   private static float measure13() {
      return measure15(0.82F, 1.08F, 1.32F);
   }

   private static float measure14() {
      return measure15(0.78F, 1.08F, 1.32F);
   }

   private static float measure15(float f, float g, float h) {
      String text2 = AttackAura.neuroProfile.getValue();

      float floatValue69 = switch (text2) {
         case "Stable" -> f;
         case "Dynamic" -> h;
         default -> g;
      };
      return MathHelper.clamp(floatValue69 * AttackAura.neuroStrength.getValue(), 0.0F, 3.0F);
   }

   record NeuroRotationControllerData(float yaw, float pitch) {
   }

   static final class NeuroRotationControllerState {
      int intValue = -1;
      int intValue2;
      int intValue3;
      int intValue4;
      boolean flag;
      boolean flag2;
      float floatValue;
      float floatValue2;
      float floatValue3 = 0.58F;
      float floatValue4;
      float floatValue5;
      float floatValue6 = 0.58F;
      float floatValue7;
      float floatValue8;
      float floatValue9;
      float floatValue10;
      float floatValue11;
      float floatValue12;
      float floatValue13;
      float floatValue14;
      float floatValue15;
      float floatValue16;
      float floatValue17;

      void invoke() {
         this.intValue = -1;
         this.intValue2 = 0;
         this.intValue3 = 0;
         this.intValue4 = 0;
         this.flag = false;
         this.flag2 = false;
         this.floatValue = 0.0F;
         this.floatValue2 = 0.0F;
         this.floatValue3 = 0.58F;
         this.floatValue4 = 0.0F;
         this.floatValue5 = 0.0F;
         this.floatValue6 = 0.58F;
         this.floatValue7 = 0.0F;
         this.floatValue8 = 0.0F;
         this.floatValue9 = 0.0F;
         this.floatValue10 = 0.0F;
         this.floatValue11 = 0.0F;
         this.floatValue12 = 0.0F;
         this.floatValue13 = 0.0F;
         this.floatValue14 = 0.0F;
         this.floatValue15 = 0.0F;
         this.floatValue16 = 0.0F;
         this.floatValue17 = 0.0F;
      }
   }

   record NeuroRotationControllerData2(float yaw, float pitch, float targetYawVelocity, float targetPitchVelocity) {
   }
}
