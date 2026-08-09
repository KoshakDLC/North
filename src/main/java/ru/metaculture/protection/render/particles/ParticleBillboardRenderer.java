package ru.metaculture.protection;

import net.minecraft.client.render.Camera;
import net.minecraft.util.math.Vec3d;
import org.joml.Quaternionf;

final class ParticleBillboardRenderer {
   private static Camera camera;
   private static double doubleValue = Double.NaN;
   private static double doubleValue2 = Double.NaN;
   private static double doubleValue3 = Double.NaN;
   private static float floatValue = Float.NaN;
   private static float floatValue2 = Float.NaN;
   private static float floatValue3 = Float.NaN;
   private static float floatValue4 = Float.NaN;
   private static double doubleValue4;
   private static double doubleValue5;
   private static double doubleValue6;
   private static float floatValue5;
   private static float floatValue6;
   private static float floatValue7;
   private static float floatValue8;
   private static float floatValue9;
   private static float floatValue10;

   private ParticleBillboardRenderer() {
   }

   static void invoke(Camera camera) {
      Vec3d vec3d = camera.getPos();
      Quaternionf quaternionf = camera.getRotation();
      float floatValue = quaternionf.x();
      float floatValue2 = quaternionf.y();
      float floatValue3 = quaternionf.z();
      float floatValue4 = quaternionf.w();
      if (camera != camera
         || vec3d.x != doubleValue
         || vec3d.y != doubleValue2
         || vec3d.z != doubleValue3
         || floatValue != floatValue
         || floatValue2 != floatValue2
         || floatValue3 != floatValue3
         || floatValue4 != floatValue4) {
         ParticleBillboardRenderer.camera = camera;
         doubleValue = vec3d.x;
         doubleValue2 = vec3d.y;
         doubleValue3 = vec3d.z;
         ParticleBillboardRenderer.floatValue = floatValue;
         ParticleBillboardRenderer.floatValue2 = floatValue2;
         ParticleBillboardRenderer.floatValue3 = floatValue3;
         ParticleBillboardRenderer.floatValue4 = floatValue4;
         doubleValue4 = vec3d.x;
         doubleValue5 = vec3d.y;
         doubleValue6 = vec3d.z;
         floatValue5 = 1.0F - 2.0F * (floatValue2 * floatValue2 + floatValue3 * floatValue3);
         floatValue6 = 2.0F * (floatValue * floatValue2 + floatValue3 * floatValue4);
         floatValue7 = 2.0F * (floatValue * floatValue3 - floatValue2 * floatValue4);
         floatValue8 = 2.0F * (floatValue * floatValue2 - floatValue3 * floatValue4);
         floatValue9 = 1.0F - 2.0F * (floatValue * floatValue + floatValue3 * floatValue3);
         floatValue10 = 2.0F * (floatValue2 * floatValue3 + floatValue * floatValue4);
      }
   }

   static double getDoubleValue4() {
      return doubleValue4;
   }

   static double getDoubleValue5() {
      return doubleValue5;
   }

   static double getDoubleValue6() {
      return doubleValue6;
   }

   static float getFloatValue5() {
      return floatValue5;
   }

   static float getFloatValue6() {
      return floatValue6;
   }

   static float getFloatValue7() {
      return floatValue7;
   }

   static float getFloatValue8() {
      return floatValue8;
   }

   static float getFloatValue9() {
      return floatValue9;
   }

   static float getFloatValue10() {
      return floatValue10;
   }
}
