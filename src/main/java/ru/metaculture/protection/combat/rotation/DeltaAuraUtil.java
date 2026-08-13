package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

public final class DeltaAuraUtil implements MinecraftAccessor {
   private DeltaAuraUtil() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   public static double squaredDistance(Vec3d eye, Entity entity) {
      Box box = entity.getBoundingBox();
      double cx = MathHelper.clamp(eye.x, box.minX, box.maxX);
      double cy = MathHelper.clamp(eye.y, box.minY, box.maxY);
      double cz = MathHelper.clamp(eye.z, box.minZ, box.maxZ);
      double dx = cx - eye.x;
      double dy = cy - eye.y;
      double dz = cz - eye.z;
      return dx * dx + dy * dy + dz * dz;
   }

   public static double squaredDistance(Entity entity) {
      return a_.player == null ? Double.POSITIVE_INFINITY : squaredDistance(a_.player.getEyePos(), entity);
   }

   public static boolean inReach(Entity entity, double maxReach) {
      return squaredDistance(entity) <= maxReach * maxReach;
   }

   public static boolean canAttack(LivingEntity entity, double distance) {
      Vec3d eye = a_.player.getEyePos();
      Box box = entity.getBoundingBox();
      double cx = MathHelper.clamp(eye.x, box.minX, box.maxX);
      double cy = MathHelper.clamp(eye.y, box.minY, box.maxY);
      double cz = MathHelper.clamp(eye.z, box.minZ, box.maxZ);
      Vec3d delta = new Vec3d(cx - eye.x, cy - eye.y, cz - eye.z);
      float yaw = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(delta.z, delta.x)) - 90.0);
      float pitch = (float)(-Math.toDegrees(Math.atan2(delta.y, Math.hypot(delta.x, delta.z))));
      return hits(yaw, pitch, distance, entity, true);
   }

   public static boolean hits(float yaw, float pitch, double distance, Entity entity, boolean throughWalls) {
      return a_.player == null || a_.world == null ? false : hits(a_.player.getEyePos(), yaw, pitch, distance, entity, throughWalls);
   }

   public static boolean hits(Vec3d origin, float yaw, float pitch, double distance, Entity entity, boolean throughWalls) {
      if (a_.player == null || a_.world == null || entity == null) {
         return false;
      }

      Vec3d dir = Vec3d.fromPolar(pitch, yaw).multiply(distance);
      Optional<Vec3d> hit = entity.getBoundingBox().contains(origin) ? Optional.of(origin) : entity.getBoundingBox().raycast(origin, origin.add(dir));
      if (hit.isEmpty()) {
         return false;
      }

      return throughWalls
         || a_.world.raycast(new RaycastContext(origin, hit.get(), ShapeType.COLLIDER, FluidHandling.NONE, a_.player)).getType() == Type.MISS;
   }

   public static boolean canSee(Vec3d from, LivingEntity entity, double reach) {
      Box box = entity.getBoundingBox();
      double[] steps = new double[]{0.0, 0.125, 0.25, 0.375, 0.5, 0.625, 0.75, 0.875, 1.0};
      int last = steps.length - 1;
      double reachSq = reach * reach;
      for (int x = 0; x <= last; x++) {
         for (int y = 0; y <= last; y++) {
            for (int z = 0; z <= last; z++) {
               if (x <= 0 || x >= last || y <= 0 || y >= last || z <= 0 || z >= last) {
                  Vec3d point = new Vec3d(
                     MathHelper.lerp(steps[x], box.minX, box.maxX),
                     MathHelper.lerp(steps[y], box.minY, box.maxY),
                     MathHelper.lerp(steps[z], box.minZ, box.maxZ)
                  );
                  double distSq = from.squaredDistanceTo(point);
                  if (distSq > reachSq) {
                     continue;
                  }

                  Vec3d end = point.add(from.subtract(point).multiply(0.05000000993895991 / Math.sqrt(distSq)));
                  if (a_.world.raycast(new RaycastContext(from, end, ShapeType.COLLIDER, FluidHandling.NONE, a_.player)).getType() == Type.MISS) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   public static boolean canHitSoon(int ticks, LivingEntity target, boolean skip) {
      return !skip && ticks >= 7 && canAttack(target, 3.0) && a_.player.getAttackCooldownProgress(0.5F) > 0.7F ? willLand() : false;
   }

   public static boolean willLand() {
      if (a_.player == null || a_.world == null) {
         return false;
      }

      double dy = (a_.player.getVelocity().y - 0.08000000049877275) * 0.9799995837206814;
      if (dy >= 0.0) {
         return false;
      }

      Box moved = a_.player.getBoundingBox().offset(0.0, dy, 0.0);
      Box feet = new Box(moved.minX, moved.minY - 0.010000001417203743, moved.minZ, moved.maxX, moved.minY, moved.maxZ);
      return a_.world.getBlockCollisions(a_.player, feet).iterator().hasNext();
   }

   public static Vec3d resolveAimPoint(Vec3d eye, LivingEntity target, double reach, boolean throughWalls) {
      Box box = target.getBoundingBox();
      boolean mace = isMaceHold();
      Vec3d aimEye = !mace || a_.player == null ? eye : eye.add(a_.player.getVelocity());
      double mx = (box.minX + box.maxX) * 0.5;
      double mz = (box.minZ + box.maxZ) * 0.5;
      Vec3d targetEye = target.getPos().add(0.0, target.getStandingEyeHeight(), 0.0);
      double distToTargetEye = aimEye.distanceTo(targetEye);
      Vec3d aimOrigin = aimEye;
      if (mace && distToTargetEye > 3.0) {
         aimOrigin = new Vec3d(aimEye.x, targetEye.y, aimEye.z);
      }

      double blendDist = mace ? Math.min(distToTargetEye, 3.0) : distToTargetEye;
      double aimHeight = aimEye.y;
      if (mace && distToTargetEye > 3.0) {
         aimHeight = targetEye.y;
      }

      double ay = MathHelper.lerp(MathHelper.clamp(blendDist / 3.0, 0.0, 1.0), box.minY, MathHelper.clamp(aimHeight, box.minY, box.maxY));
      List<Vec3d> points = new ArrayList<>();
      points.add(new Vec3d(mx, ay, mz));
      double[] steps = new double[]{0.0, 0.125, 0.25, 0.375, 0.5, 0.625, 0.75, 0.875, 1.0};
      int last = steps.length - 1;
      for (int x = 0; x < steps.length; x++) {
         for (int y = 0; y < steps.length; y++) {
            for (int z = 0; z < steps.length; z++) {
               if (x == 0 || x == last || y == 0 || y == last || z == 0 || z == last) {
                  points.add(
                     new Vec3d(
                        MathHelper.lerp(steps[x], box.minX, box.maxX),
                        MathHelper.lerp(steps[y], box.minY, box.maxY),
                        MathHelper.lerp(steps[z], box.minZ, box.maxZ)
                     )
                  );
               }
            }
         }
      }

      for (double pad : new double[]{0.0, 0.20000001551382535}) {
         List<Vec3d> visible = collectPoints(aimOrigin, target, points, reach, pad, mace, false);
         if (!visible.isEmpty()) {
            return closestToCentroid(visible).subtract(aimOrigin);
         }

         if (throughWalls) {
            List<Vec3d> through = collectPoints(aimOrigin, target, points, reach, pad, mace, true);
            if (!through.isEmpty()) {
               return closestToCentroid(through).subtract(aimOrigin);
            }
         }
      }

      return Vec3d.ZERO;
   }

   public static boolean needsCrit() {
      if (a_.player == null || a_.player.getWorld() == null) {
         return false;
      }

      FluidState fluid = a_.player.getWorld().getFluidState(BlockPos.ofFloored(a_.player.getEyePos()));
      return !(a_.player.hasStatusEffect(StatusEffects.LEVITATION)
         || a_.player.hasStatusEffect(StatusEffects.SLOW_FALLING)
         || fluid.isIn(FluidTags.WATER)
         || fluid.isIn(FluidTags.LAVA)
         || a_.player.getAbilities().flying
         || a_.player.isGliding()
         || a_.player.isClimbing()
         || a_.player.hasVehicle());
   }

   public static boolean canCrit() {
      return a_.player != null && needsCrit() && a_.player.fallDistance > 0.0F && !a_.player.isOnGround();
   }

   public static float lerpAngle(float start, float end, float amount) {
      float clamped = MathHelper.clamp(amount, 0.0F, 1.0F);
      float delta = MathHelper.wrapDegrees(end - start);
      if (Math.abs(delta) < 0.5F) {
         return end;
      }

      float stepped = MathHelper.wrapDegrees(start + delta * clamped);
      float patched = applyGcd(start, stepped);
      float remaining = MathHelper.wrapDegrees(end - patched);
      return Math.abs(remaining) < 0.5F ? end : patched;
   }

   public static float applyGcd(float lastYaw, float current) {
      double sens = a_.options.getMouseSensitivity().getValue() * 0.6000000498956214 + 0.19999998556632664;
      double gcd = sens * sens * sens * 8.0;
      return (float)(lastYaw + Math.ceil((current - lastYaw) / gcd / 0.15000006556510925) * gcd * 0.15000006556510925);
   }

   public static boolean isMaceHold() {
      return a_.player != null && a_.player.getMainHandStack().isOf(Items.MACE);
   }

   private static List<Vec3d> collectPoints(
      Vec3d aimOrigin, LivingEntity target, List<Vec3d> points, double reach, double pad, boolean mace, boolean throughWalls
   ) {
      List<Vec3d> result = new ArrayList<>();
      double limit = reach + pad;
      for (Vec3d point : points) {
         Vec3d delta = point.subtract(aimOrigin);
         double len = delta.length();
         if (mace || len <= limit) {
            float traceDist = (float)(mace ? len + pad + 0.010000001417203743 : limit);
            if (hits(
               aimOrigin,
               (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(delta.z, delta.x)) - 90.0),
               (float)(-Math.toDegrees(Math.atan2(delta.y, Math.hypot(delta.x, delta.z)))),
               traceDist,
               target,
               throughWalls
            )) {
               result.add(point);
            }
         }
      }

      return result;
   }

   private static Vec3d closestToCentroid(List<Vec3d> points) {
      Vec3d centroid = Vec3d.ZERO;
      for (Vec3d point : points) {
         centroid = centroid.add(point);
      }

      Vec3d center = centroid.multiply(1.0 / points.size());
      return points.stream().min(Comparator.comparingDouble(point -> point.squaredDistanceTo(center))).orElse(points.get(0));
   }
}
