package ru.metaculture.protection;

import org.joml.Vector4f;

public final class EspPreviewBounds {
   private EspPreviewBounds() {
   }

   public static void invoke(float f, float g, float h, float i, Vector4f vector4f, int j) {
   }

   public static void invoke2(RenderManager renderManager, float f, float g, float h, float i, Vector4f vector4f, int j) {
      renderManager.invoke6(f, g, h, i, vector4f.x, vector4f.y, vector4f.z, vector4f.w, j);
   }

   public static void invoke3(RenderManager renderManager2, float f, float g, float h, float i, float j, int k) {
      renderManager2.invoke6(f, g, h, i, j, j, j, j, k);
   }

   public static class EspPreviewBoundsState {
      private EspPreviewBoundsState() {
      }
   }
}
