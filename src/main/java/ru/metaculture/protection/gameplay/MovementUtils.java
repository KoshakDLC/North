package ru.metaculture.protection;

import lombok.Generated;
import net.minecraft.client.util.InputUtil;
import net.minecraft.util.PlayerInput;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public final class MovementUtils implements MinecraftAccessor {
   public static double measure(float f, float g, float h) {
      if (g < 0.0F) {
         f += 180.0F;
      }

      float floatValue = 1.0F;
      if (g < 0.0F) {
         floatValue = -0.5F;
      }

      if (g > 0.0F) {
         floatValue = 0.5F;
      }

      if (h > 0.0F) {
         f -= 90.0F * floatValue;
      }

      if (h < 0.0F) {
         f += 90.0F * floatValue;
      }

      return Math.toRadians(f);
   }

   public static boolean check() {
      return a_.player != null && a_.player.input != null && a_.player.input.playerInput != null
         ? a_.player.input.playerInput.forward()
            || a_.player.input.playerInput.backward()
            || a_.player.input.playerInput.left()
            || a_.player.input.playerInput.right()
         : false;
   }

   public static double[] resolve(double d) {
      float[] floatValues = resolve3();
      return resolve2(floatValues[0], floatValues[1], d);
   }

   public static double[] resolve2(float f, float g, double d) {
      float floatValue2 = a_.player.getYaw();
      if (f != 0.0F) {
         if (g > 0.0F) {
            floatValue2 += f > 0.0F ? -45.0F : 45.0F;
         } else if (g < 0.0F) {
            floatValue2 += f > 0.0F ? 45.0F : -45.0F;
         }

         g = 0.0F;
         f = f > 0.0F ? 1.0F : -1.0F;
      }

      double doubleValue = Math.sin(Math.toRadians(floatValue2 + 90.0F));
      double doubleValue2 = Math.cos(Math.toRadians(floatValue2 + 90.0F));
      double doubleValue3 = f * d * doubleValue2 + g * d * doubleValue;
      double doubleValue4 = f * d * doubleValue - g * d * doubleValue2;
      return new double[]{doubleValue3, doubleValue4};
   }

   public static void invoke(MovementInputEvent movementInputEvent, float f) {
      float floatValue3 = movementInputEvent.getFloatValue();
      float floatValue4 = movementInputEvent.getFloatValue2();
      double doubleValue5 = MathHelper.wrapDegrees(Math.toDegrees(measure(a_.player.isFlyingVehicle() ? a_.player.getYaw() : f, floatValue3, floatValue4)));
      if (floatValue3 != 0.0F || floatValue4 != 0.0F) {
         float floatValue5 = 0.0F;
         float floatValue6 = 0.0F;
         float floatValue7 = Float.MAX_VALUE;

         for (float floatValue8 = -1.0F; floatValue8 <= 1.0F; floatValue8++) {
            for (float floatValue9 = -1.0F; floatValue9 <= 1.0F; floatValue9++) {
               if (floatValue9 != 0.0F || floatValue8 != 0.0F) {
                  double doubleValue6 = MathHelper.wrapDegrees(Math.toDegrees(measure(a_.player.getYaw(), floatValue8, floatValue9)));
                  double doubleValue7 = Math.abs(doubleValue5 - doubleValue6);
                  if (doubleValue7 < floatValue7) {
                     floatValue7 = (float)doubleValue7;
                     floatValue5 = floatValue8;
                     floatValue6 = floatValue9;
                  }
               }
            }
         }

         movementInputEvent.setFloatValue(floatValue5);
         movementInputEvent.setFloatValue2(floatValue6);
      }
   }

   public static float[] resolve3() {
      float floatValue10 = 0.0F;
      float floatValue11 = 0.0F;
      long longValue = a_.getWindow().getHandle();
      if (InputUtil.isKeyPressed(longValue, a_.options.forwardKey.getDefaultKey().getCode())) {
         floatValue10++;
      }

      if (InputUtil.isKeyPressed(longValue, a_.options.backKey.getDefaultKey().getCode())) {
         floatValue10--;
      }

      if (InputUtil.isKeyPressed(longValue, a_.options.leftKey.getDefaultKey().getCode())) {
         floatValue11++;
      }

      if (InputUtil.isKeyPressed(longValue, a_.options.rightKey.getDefaultKey().getCode())) {
         floatValue11--;
      }

      return new float[]{floatValue10, floatValue11};
   }

   private static void invoke2(float f, float g) {
      if (a_.player != null && a_.player.input != null && a_.player.input.playerInput != null) {
         boolean flag = f > 0.0F;
         boolean flag2 = f < 0.0F;
         boolean flag3 = g > 0.0F;
         boolean flag4 = g < 0.0F;
         boolean flag5 = a_.player.input.playerInput.jump();
         boolean flag6 = a_.player.input.playerInput.sneak();
         boolean flag7 = a_.player.input.playerInput.sprint();
         a_.player.input.playerInput = new PlayerInput(flag, flag2, flag3, flag4, flag5, flag6, flag7);
      }
   }

   public static void invoke3(float f, Vec3d vec3d) {
      float[] floatValues2 = resolve3();
      float floatValue12 = floatValues2[0];
      float floatValue13 = floatValues2[1];
      if (floatValue12 == 0.0F && floatValue13 == 0.0F) {
         a_.options.forwardKey.setPressed(false);
         a_.options.backKey.setPressed(false);
         a_.options.leftKey.setPressed(false);
         a_.options.rightKey.setPressed(false);
      } else {
         Box box = AttackAura.livingEntity.getBoundingBox();
         double doubleValue8 = MathHelper.lerp(Math.random(), box.minX, box.maxX);
         double doubleValue9 = MathHelper.lerp(Math.random(), box.minY, box.maxY);
         double doubleValue10 = MathHelper.lerp(Math.random(), box.minZ, box.maxZ);
         doubleValue9 = MathHelper.clamp(doubleValue9, AttackAura.livingEntity.getY() + 0.2, AttackAura.livingEntity.getY() + AttackAura.livingEntity.getHeight() - 0.2);
         Vec3d vec3d2 = new Vec3d(doubleValue8, doubleValue9, doubleValue10);
         Vec3d vec3d3 = vec3d2.subtract(a_.player.getEyePos()).normalize();
         float floatValue14 = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec3d3.z, vec3d3.x)) - 90.0);
         double doubleValue11 = MathHelper.wrapDegrees(Math.toDegrees(measure(a_.player.isFlyingVehicle() ? a_.player.getYaw() : floatValue14, floatValue12, floatValue13)));
         float floatValue15 = 0.0F;
         float floatValue16 = 0.0F;
         float floatValue17 = Float.MAX_VALUE;

         for (float floatValue18 = -1.0F; floatValue18 <= 1.0F; floatValue18++) {
            for (float floatValue19 = -1.0F; floatValue19 <= 1.0F; floatValue19++) {
               if (floatValue19 != 0.0F || floatValue18 != 0.0F) {
                  double doubleValue12 = MathHelper.wrapDegrees(Math.toDegrees(measure(a_.player.getYaw(), floatValue18, floatValue19)));
                  double doubleValue13 = Math.abs(doubleValue11 - doubleValue12);
                  if (doubleValue13 < floatValue17) {
                     floatValue17 = (float)doubleValue13;
                     floatValue15 = floatValue18;
                     floatValue16 = floatValue19;
                  }
               }
            }
         }

         a_.options.forwardKey.setPressed(floatValue15 > 0.0F);
         a_.options.backKey.setPressed(floatValue15 < 0.0F);
         a_.options.leftKey.setPressed(floatValue16 > 0.0F);
         a_.options.rightKey.setPressed(floatValue16 < 0.0F);
      }
   }

   public static void invoke4(float f) {
      float[] floatValues3 = resolve3();
      float floatValue20 = floatValues3[0];
      float floatValue21 = floatValues3[1];
      if (floatValue20 == 0.0F && floatValue21 == 0.0F) {
         a_.options.forwardKey.setPressed(false);
         a_.options.backKey.setPressed(false);
         a_.options.leftKey.setPressed(false);
         a_.options.rightKey.setPressed(false);
      } else {
         double doubleValue14 = MathHelper.wrapDegrees(Math.toDegrees(measure(a_.player.isFlyingVehicle() ? a_.player.getYaw() : f, floatValue20, floatValue21)));
         float floatValue22 = 0.0F;
         float floatValue23 = 0.0F;
         float floatValue24 = Float.MAX_VALUE;

         for (float floatValue25 = -1.0F; floatValue25 <= 1.0F; floatValue25++) {
            for (float floatValue26 = -1.0F; floatValue26 <= 1.0F; floatValue26++) {
               if (floatValue26 != 0.0F || floatValue25 != 0.0F) {
                  double doubleValue15 = MathHelper.wrapDegrees(Math.toDegrees(measure(a_.player.getYaw(), floatValue25, floatValue26)));
                  double doubleValue16 = Math.abs(doubleValue14 - doubleValue15);
                  if (doubleValue16 < floatValue24) {
                     floatValue24 = (float)doubleValue16;
                     floatValue22 = floatValue25;
                     floatValue23 = floatValue26;
                  }
               }
            }
         }

         a_.options.forwardKey.setPressed(floatValue22 > 0.0F);
         a_.options.backKey.setPressed(floatValue22 < 0.0F);
         a_.options.leftKey.setPressed(floatValue23 > 0.0F);
         a_.options.rightKey.setPressed(floatValue23 < 0.0F);
      }
   }

   public static void invoke5(double d) {
      if (a_.player != null) {
         float floatValue27 = a_.player.getYaw();
         double doubleValue17 = Math.toRadians(floatValue27);
         double doubleValue18 = 0.0;
         double doubleValue19 = 0.0;
         Vec2f vec2f = a_.player.input.getMovementInput();
         float floatValue28 = vec2f.y;
         float floatValue29 = vec2f.x;
         if (floatValue28 > 0.0F) {
            doubleValue18 -= Math.sin(doubleValue17) * d;
            doubleValue19 += Math.cos(doubleValue17) * d;
         }

         if (floatValue28 < 0.0F) {
            doubleValue18 += Math.sin(doubleValue17) * d;
            doubleValue19 -= Math.cos(doubleValue17) * d;
         }

         if (floatValue29 > 0.0F) {
            doubleValue18 += Math.cos(doubleValue17) * d;
            doubleValue19 += Math.sin(doubleValue17) * d;
         }

         if (floatValue29 < 0.0F) {
            doubleValue18 -= Math.cos(doubleValue17) * d;
            doubleValue19 -= Math.sin(doubleValue17) * d;
         }

         if (floatValue29 > 0.0F && floatValue28 > 0.0F) {
            doubleValue18 = Math.cos(Math.toRadians(floatValue27 + 45.0F)) * d;
            doubleValue19 = Math.sin(Math.toRadians(floatValue27 + 45.0F)) * d;
         }

         if (floatValue29 < 0.0F && floatValue28 > 0.0F) {
            doubleValue18 = -Math.cos(Math.toRadians(floatValue27 - 45.0F)) * d;
            doubleValue19 = -Math.sin(Math.toRadians(floatValue27 - 45.0F)) * d;
         }

         if (floatValue29 > 0.0F && floatValue28 < 0.0F) {
            doubleValue18 = -Math.cos(Math.toRadians(floatValue27 + 135.0F)) * d;
            doubleValue19 = -Math.sin(Math.toRadians(floatValue27 + 135.0F)) * d;
         }

         if (floatValue29 < 0.0F && floatValue28 < 0.0F) {
            doubleValue18 = Math.cos(Math.toRadians(floatValue27 - 135.0F)) * d;
            doubleValue19 = Math.sin(Math.toRadians(floatValue27 - 135.0F)) * d;
         }

         a_.player.setVelocity(doubleValue18, a_.player.getVelocity().y, doubleValue19);
      }
   }

   public static float[] resolve4(float f, float g, float h, float i) {
      double doubleValue20 = measure(i, f, g);
      double doubleValue21 = Math.toRadians(h);
      double doubleValue22 = doubleValue20 - doubleValue21;
      float floatValue30 = (float)Math.cos(doubleValue22);
      float floatValue31 = (float)Math.sin(doubleValue22);
      float floatValue32 = Math.max(Math.abs(f), Math.abs(g));
      float floatValue33 = (float)Math.hypot(floatValue30, floatValue31);
      if (floatValue33 != 0.0F) {
         floatValue30 = floatValue30 / floatValue33 * floatValue32;
         floatValue31 = floatValue31 / floatValue33 * floatValue32;
      }

      return new float[]{floatValue30, floatValue31};
   }

   @Generated
   private MovementUtils() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
