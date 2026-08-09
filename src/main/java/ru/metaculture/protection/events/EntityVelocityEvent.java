package ru.metaculture.protection;

import lombok.Generated;
import net.minecraft.util.math.Vec3d;

public class EntityVelocityEvent extends Event {
   private Vec3d vec3d;

   public EntityVelocityEvent(Vec3d vec3d) {
      this.vec3d = vec3d;
   }

   @Generated
   public Vec3d getVec3d() {
      return this.vec3d;
   }

   @Generated
   public void setVec3d(Vec3d vec3d) {
      this.vec3d = vec3d;
   }
}
