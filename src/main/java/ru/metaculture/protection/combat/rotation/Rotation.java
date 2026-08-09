package ru.metaculture.protection;

import net.minecraft.entity.Entity;
import org.joml.Vector2f;

public class Rotation implements MinecraftAccessor {
   public float floatValue;
   public float floatValue2;

   public Rotation(Entity entity) {
      this.floatValue = entity.getYaw();
      this.floatValue2 = entity.getPitch();
   }

   public Rotation(float f, float g) {
      this.floatValue = f;
      this.floatValue2 = g;
   }

   public float measure(Rotation rotation) {
      float floatValue = RenderMath.measure27(rotation.floatValue - this.floatValue);
      float floatValue2 = rotation.floatValue2 - this.floatValue2;
      return (float)Math.hypot(Math.abs(floatValue), Math.abs(floatValue2));
   }

   public double measure2(Rotation rotation2) {
      double doubleValue = RenderMath.measure27(rotation2.floatValue - this.floatValue);
      double doubleValue2 = RenderMath.measure27(rotation2.floatValue2 - this.floatValue2);
      return Math.hypot(doubleValue, doubleValue2);
   }

   public static Vector2f resolve() {
      return new Vector2f(measure3(), measure4());
   }

   public static float measure3() {
      return RenderMath.measure27(a_.gameRenderer.getCamera().getYaw() + (a_.gameRenderer.getCamera().isThirdPerson() ? 180 : 0));
   }

   public static float measure4() {
      return (a_.gameRenderer.getCamera().isThirdPerson() ? -1 : 1) * a_.gameRenderer.getCamera().getPitch();
   }
}
