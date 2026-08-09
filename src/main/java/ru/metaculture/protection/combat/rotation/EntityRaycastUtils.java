package ru.metaculture.protection;

import java.util.Optional;
import java.util.function.Predicate;
import lombok.Generated;
import net.minecraft.entity.Entity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

public final class EntityRaycastUtils implements MinecraftAccessor {
   public static EntityHitResult resolve(Entity entity, Vec3d vec3d, Vec3d vec3d2, Box box, Predicate<Entity> predicate, double d) {
      World world = entity.getWorld();
      double doubleValue = d;
      Entity entity2 = null;
      Vec3d vec3d3 = null;

      for (Entity entity3 : world.getOtherEntities(entity, box, predicate)) {
         Box box2 = entity3.getBoundingBox().expand(entity3.getTargetingMargin());
         Optional optional = box2.raycast(vec3d, vec3d2);
         if (box2.contains(vec3d) || optional.isPresent()) {
            double doubleValue2 = ((Optional<Vec3d>)optional).<Double>map(vec3d::squaredDistanceTo).orElse(0.0);
            doubleValue2 = Math.sqrt(doubleValue2);
            if ((doubleValue2 < doubleValue || doubleValue == 0.0) && entity3.getRootVehicle() != entity.getRootVehicle()) {
               entity2 = entity3;
               vec3d3 = ((Optional<Vec3d>)optional).orElse(vec3d);
               doubleValue = doubleValue2;
            }
         }
      }

      return entity2 == null ? null : new EntityHitResult(entity2, vec3d3);
   }

   public static HitResult resolve2(double d, float f, float g, Entity entity, boolean bl) {
      float floatValue = a_.getRenderTickCounter().getTickProgress(true);
      Vec3d vec3d4 = a_.player.getCameraPosVec(floatValue);
      Vec3d vec3d5 = resolve4(g, f);
      Vec3d vec3d6 = vec3d4.add(vec3d5.multiply(d));
      HitResult hitResult = resolve5(vec3d4, vec3d6, ShapeType.COLLIDER, FluidHandling.NONE);
      double doubleValue3 = hitResult.getPos().squaredDistanceTo(vec3d4);
      Box box3 = entity.getBoundingBox().stretch(vec3d5.multiply(d)).expand(1.0);
      EntityHitResult entityHitResult = ProjectileUtil.raycast(
         entity, vec3d4, vec3d6, box3, entityx -> !entityx.isSpectator() && entityx.isAlive() && entityx.canHit(), d * d
      );
      return (HitResult)(entityHitResult == null || !bl && !(entityHitResult.getPos().squaredDistanceTo(vec3d4) < doubleValue3) ? hitResult : entityHitResult);
   }

   public static boolean check(float f, float g, double d, Entity entity) {
      float floatValue2 = a_.getRenderTickCounter().getTickProgress(true);
      Vec3d vec3d7 = a_.player.getCameraPosVec(floatValue2);
      Vec3d vec3d8 = resolve4(g, f);
      Vec3d vec3d9 = vec3d7.add(vec3d8.multiply(d));
      Box box4 = entity.getBoundingBox();
      return box4.contains(vec3d7) || box4.raycast(vec3d7, vec3d9).isPresent();
   }

   public static boolean check2(float f, float g, double d, Entity entity, boolean bl) {
      return resolve3(f, g, d, entity, bl) != null;
   }

   public static EntityHitResult resolve3(float f, float g, double d, Entity entity, boolean bl) {
      if (a_.player != null && a_.world != null && entity != null) {
         return resolve2(d, f, g, a_.player, bl) instanceof EntityHitResult entityHitResult2 && entityHitResult2.getEntity().equals(entity) ? entityHitResult2 : null;
      } else {
         return null;
      }
   }

   public static boolean check3(float f, float g, double d, Entity entity) {
      return check4(f, g, d, entity, true);
   }

   public static boolean check4(float f, float g, double d, Entity entity, boolean bl) {
      if (a_.player != null && a_.world != null && entity != null) {
         Vec3d vec3d10 = a_.player.getEyePos();
         Vec3d vec3d11 = resolve4(g, f);
         Vec3d vec3d12 = vec3d10.add(vec3d11.multiply(d));
         Box box5 = entity.getBoundingBox().expand(entity.getTargetingMargin());
         Optional optional2 = box5.raycast(vec3d10, vec3d12);
         if (box5.contains(vec3d10)) {
            return true;
         } else if (optional2.isEmpty()) {
            return false;
         } else if (bl) {
            return true;
         } else {
            HitResult hitResult2 = resolve5(vec3d10, vec3d12, ShapeType.COLLIDER, FluidHandling.NONE);
            return hitResult2.getType() == Type.MISS || ((Vec3d)optional2.get()).squaredDistanceTo(vec3d10) < hitResult2.getPos().squaredDistanceTo(vec3d10);
         }
      } else {
         return false;
      }
   }

   public static Vec3d resolve4(float f, float g) {
      float floatValue3 = -g * (float) (Math.PI / 180.0) - (float) Math.PI;
      float floatValue4 = -f * (float) (Math.PI / 180.0);
      float floatValue5 = MathHelper.cos(floatValue3);
      float floatValue6 = MathHelper.sin(floatValue3);
      float floatValue7 = -MathHelper.cos(floatValue4);
      float floatValue8 = MathHelper.sin(floatValue4);
      return new Vec3d(floatValue6 * floatValue7, floatValue8, floatValue5 * floatValue7);
   }

   public static HitResult resolve5(Vec3d vec3d, Vec3d vec3d2, ShapeType shapeType, FluidHandling fluidHandling) {
      return a_.world.raycast(new RaycastContext(vec3d, vec3d2, shapeType, fluidHandling, a_.player));
   }

   public static Vec3d resolve6(float f, float g) {
      float floatValue9 = (float)(g * (Math.PI / 180.0));
      float floatValue10 = (float)(-f * (Math.PI / 180.0));
      float floatValue11 = MathHelper.cos(floatValue10);
      float floatValue12 = MathHelper.sin(floatValue10);
      float floatValue13 = MathHelper.cos(floatValue9);
      float floatValue14 = MathHelper.sin(floatValue9);
      return new Vec3d(floatValue12 * floatValue13, -floatValue14, floatValue11 * floatValue13);
   }

   @Generated
   private EntityRaycastUtils() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
