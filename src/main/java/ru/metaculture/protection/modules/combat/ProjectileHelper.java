package ru.metaculture.protection;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.StreamSupport;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "ProjectileHelper",
   description = "Помогает целиться по противнику при стрельбе из лука или трезубца",
   category = Category.Combat
)
public class ProjectileHelper extends Module {
   private int d;
   private int e;
   private boolean g;
   private boolean h;
   private final LivingEntity[] b = new LivingEntity[2];
   private final Vec3d[] c = new Vec3d[5];
   private boolean f = true;

   public LivingEntity q() {
      return this.b[0];
   }

   public boolean r() {
      if (this.b[0] == null || CLIENT.player == null || !CLIENT.player.isUsingItem()) {
         return false;
      }
      return CLIENT.player.getItemUseTime() > 2;
   }

   @Override
   public void onDisable() {
      this.s();
      super.onDisable();
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player == null || CLIENT.world == null) {
         return;
      }

      ItemStack stack = CLIENT.player.getStackInHand(Hand.MAIN_HAND);
      if (!(stack.getItem() instanceof BowItem) && !(stack.getItem() instanceof TridentItem)) {
         this.s();
         return;
      }

      if (!CLIENT.player.isUsingItem()) {
         this.f = true;
      }
      if (!this.f) {
         this.s();
         return;
      }

      this.g = DeltaAuraUtil.willLand();
      this.h = CLIENT.player.input != null
         && CLIENT.player.input.playerInput.jump()
         && (CLIENT.player.isOnGround() || this.g);
      this.a(this.t());
      if (this.b[0] != null) {
         Vec3d[] samples = this.c;
         int i = this.e;
         this.e = i + 1;
         samples[i % this.c.length] = new Vec3d(this.b[0].getX() - this.b[0].lastX, 0.0, this.b[0].getZ() - this.b[0].lastZ);
      }

      Rotation aim;
      if (this.r() && (aim = this.a(stack)) != null) {
         RotationController.invoke7(aim, 180.0F, 180.0F, 1, 1);
      }
   }

   @EventHandler
   public void onMouseButtonPosition(MouseButtonPositionEvent mouseButtonPositionEvent) {
      if (mouseButtonPositionEvent.isPress() && mouseButtonPositionEvent.getButton() == 0 && CLIENT.player != null && CLIENT.player.isUsingItem()) {
         this.f = !this.f;
      }
   }

   private void s() {
      this.b[1] = null;
      this.b[0] = null;
      this.d = 0;
      Arrays.fill(this.c, null);
   }

   private LivingEntity t() {
      Vec3d eye = CLIENT.player.getEyePos();
      Vec3d look = Vec3d.fromPolar(lookPitch(), lookYaw());
      return StreamSupport.stream(CLIENT.world.getEntities().spliterator(), false)
         .filter(PlayerEntity.class::isInstance)
         .map(PlayerEntity.class::cast)
         .filter(player -> player != CLIENT.player
            && player.isAlive()
            && !FriendCommand.check(player.getName().getString())
            && eye.squaredDistanceTo(player.getBoundingBox().getCenter()) <= 14400.0)
         .min(Comparator.comparingDouble(player -> -look.dotProduct(player.getBoundingBox().getCenter().subtract(eye).normalize())))
         .orElse(null);
   }

   private void a(LivingEntity best) {
      if (best != this.b[1]) {
         this.b[1] = best;
         this.d = 0;
      } else {
         this.d++;
      }
      if (this.b[0] != this.b[1]) {
         if (this.b[0] == null || this.d >= 4) {
            this.b[0] = this.b[1];
            Arrays.fill(this.c, null);
         }
      }
   }

   private Vec3d u() {
      Vec3d sum = Vec3d.ZERO;
      int count = 0;
      for (Vec3d entry : this.c) {
         if (entry != null && entry.horizontalLengthSquared() > 1.000000229429758E-6) {
            sum = sum.add(entry);
            count++;
         }
      }
      return count == 0 ? Vec3d.ZERO : sum.multiply(1.0 / count);
   }

   private Rotation a(ItemStack stack) {
      Vec3d shooter = this.v();
      Vec3d origin = CLIENT.player.getEyePos().add(0.0, -0.1000000074661073, 0.0);
      double speed = stack.getItem() instanceof BowItem ? this.b(stack) : 2.5;
      Box box = this.b[0].getBoundingBox();
      Vec3d motion = this.u();
      Vec3d aim = box.getCenter();
      float yaw = 0.0F;
      float pitch = 0.0F;
      for (int i = 0; i < 6; i++) {
         yaw = this.a(origin, aim);
         pitch = this.a(origin, aim, shooter, speed);
         double[] shot = this.a(origin, Vec3d.fromPolar(pitch, yaw).multiply(speed).add(shooter), Math.hypot(aim.x - origin.x, aim.z - origin.z), true);
         if (shot == null) {
            return null;
         }
         Vec3d moved = box.getCenter().add(motion.multiply(Math.min(shot[1] + 6.0, 13.0)));
         if (moved.squaredDistanceTo(aim) < 9.999996044721066E-5) {
            break;
         }
         aim = moved;
      }

      Rotation rotation = new Rotation(MathHelper.wrapDegrees(yaw), pitch);
      float t = CLIENT.player.age + CLIENT.getRenderTickCounter().getTickProgress(false);
      float smoothW = (float)((((Math.sin(t * 0.8F) * 11.0)
         + (Math.sin(t * 0.04000001688754603 + 17.200001527756587) * 1.5)
         + (Math.sin(t * 0.11000000003049541 + 5.800002923050999) * 3.0)
         + (Math.sin(t * 0.07000000374109333 + 12.300000031704212) * 1.0))) / 4.0F);
      float smoothH = (float)((Math.sin(t * 0.1000000001867308) + (Math.sin(t * 0.029999988014174556 + 54.09998474500903) * 0.5)) / 2.0F);
      boolean tridentEarly = stack.getItem() instanceof TridentItem && CLIENT.player.getItemUseTime() < 9;
      if (!tridentEarly) {
         smoothW = MathHelper.clamp(smoothW, -0.3F, 0.3F);
         smoothH = MathHelper.clamp(smoothH, -0.3F, 0.3F);
      }
      rotation.floatValue += smoothW;
      rotation.floatValue2 += smoothH;
      return rotation;
   }

   private float a(Vec3d origin, Vec3d aim, Vec3d shooter, double speed) {
      float low = -90.0F;
      float high = 90.0F;
      float yaw = this.a(origin, aim);
      double target = Math.hypot(aim.x - origin.x, aim.z - origin.z);
      double height = aim.y - origin.y;
      for (int i = 0; i < 24; i++) {
         float middle = (low + high) / 2.0F;
         double[] shot = this.a(origin, Vec3d.fromPolar(middle, yaw).multiply(speed).add(shooter), target, false);
         if (shot == null || shot[0] >= height) {
            low = middle;
         } else {
            high = middle;
         }
      }
      return (low + high) / 2.0F;
   }

   private double[] a(Vec3d origin, Vec3d velocity, double target, boolean blocked) {
      Vec3d position = origin;
      Vec3d current = velocity;
      double travelled = 0.0;
      for (int tick = 1; tick <= 100; tick++) {
         Vec3d next = position.add(current);
         if (blocked
            && CLIENT.world.raycast(new RaycastContext(position, next, ShapeType.COLLIDER, FluidHandling.NONE, CLIENT.player)).getType() != Type.MISS) {
            return null;
         }
         double reached = Math.hypot(next.x - origin.x, next.z - origin.z);
         if (reached >= target) {
            double alpha = reached == travelled ? 1.0 : (target - travelled) / (reached - travelled);
            return new double[]{MathHelper.lerp(alpha, position.y, next.y) - origin.y, tick - 1 + alpha};
         }
         position = next;
         travelled = reached;
         current = current.multiply(this.a(position) ? 0.6000002908794272 : 0.9900000228356232).add(0.0, -0.050000001868616015, 0.0);
      }
      return null;
   }

   private boolean a(Vec3d position) {
      return CLIENT.world.getBlockState(BlockPos.ofFloored(position)).getFluidState().isIn(FluidTags.WATER);
   }

   private Vec3d v() {
      Vec3d velocity = new Vec3d(
         CLIENT.player.getX() - CLIENT.player.lastX,
         CLIENT.player.getY() - CLIENT.player.lastY,
         CLIENT.player.getZ() - CLIENT.player.lastZ
      );
      if (!this.h) {
         return new Vec3d(velocity.x, CLIENT.player.isOnGround() ? 0.0 : velocity.y, velocity.z);
      }
      float yaw = CLIENT.player.getYaw() * 0.017453292F;
      double sprint = CLIENT.player.isSprinting() ? 0.19999997617511883 : 0.0;
      return new Vec3d(
         velocity.x - MathHelper.sin(yaw) * sprint,
         Math.max(0.42F + CLIENT.player.getJumpBoostVelocityModifier(), velocity.y),
         velocity.z + MathHelper.cos(yaw) * sprint
      );
   }

   private double b(ItemStack stack) {
      float pull = 1.0F;
      ItemStack active = CLIENT.player.getActiveItem();
      if (CLIENT.player.isUsingItem() && active.getItem() instanceof BowItem) {
         float f = (CLIENT.player.getItemUseTime() + 1.5F) / 20.0F;
         pull = Math.min((f * f + f * 2.0F) / 3.0F, 1.0F);
      }
      return pull * 3.0;
   }

   private float a(Vec3d from, Vec3d to) {
      return (float)Math.toDegrees(Math.atan2(-(to.x - from.x), to.z - from.z));
   }

   private static float lookYaw() {
      return FreeLookController.floatValue;
   }

   private static float lookPitch() {
      return FreeLookController.floatValue2;
   }
}
