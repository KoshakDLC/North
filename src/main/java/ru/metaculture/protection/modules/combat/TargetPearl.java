package ru.metaculture.protection;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleAccess(
   usernames = {"lichoday"}
)
@ModuleRegister(
   name = "TargetPearl",
   description = "Кидает эндер-перл вслед за перлом ближайшего игрока",
   category = Category.Combat
)
public class TargetPearl extends Module {
   private static final double DOUBLE_VALUE = 0.03;
   private static final double DOUBLE_VALUE_2 = 0.99;
   private static final double DOUBLE_VALUE_3 = 0.8;
   private static final double DOUBLE_VALUE_4 = 1.5;
   private static final int INT_VALUE = 240;
   private static final int INT_VALUE_2 = 160;
   private static final String TARGETPEARL = "TargetPearl";
   private final NumberSetting radiusReaktsii = new NumberSetting("Радиус реакции", 48.0F, 8.0F, 128.0F, 1.0F, false);
   private final BooleanSetting ispolzovatRotatsiyu = new BooleanSetting("Использовать ротацию", true);
   private final NumberSetting skorostPovorota = new NumberSetting("Скорость поворота", 40.0F, 5.0F, 180.0F, 1.0F, false)
      .setVisibilityCondition(() -> !this.ispolzovatRotatsiyu.isEnabled());
   private final NumberSetting tochnostPritsela = new NumberSetting("Точность прицела", 2.5F, 0.5F, 10.0F, 0.1F, false)
      .setVisibilityCondition(() -> !this.ispolzovatRotatsiyu.isEnabled());
   private final NumberSetting maksPromahBloki = new NumberSetting("Макс. промах (блоки)", 2.5F, 0.5F, 8.0F, 0.1F, false);
   private final NumberSetting zaderzhkaBroskaMs = new NumberSetting("Задержка броска (мс)", 600.0F, 0.0F, 3000.0F, 50.0F, false);
   private final BooleanSetting tolkoIzHotbara = new BooleanSetting("Только из хотбара", false);
   private final BooleanSetting tolkoIzInventarya = new BooleanSetting("Только из инвентаря", false);
   private final BooleanSetting ignorirovatDruzey = new BooleanSetting("Игнорировать друзей", true);
   private final BooleanSetting trebovatVladeltsa = new BooleanSetting("Требовать владельца", false);
   private static final double DOUBLE_VALUE_5 = 3.0;
   private final Set<Integer> values = new HashSet<>();
   private final Set<Integer> values2 = new HashSet<>();
   private final Map<Integer, Vec3d> valuesByKey = new HashMap<>();
   private final Map<Integer, Vec3d> valuesByKey2 = new HashMap<>();
   private long timestamp;
   private int intValue = -1;
   private int intValue2;
   private int intValue3;
   private int intValue4 = -1;
   private int intValue5 = -1;
   private float floatValue;
   private float floatValue2;
   private float floatValue3;
   private float floatValue4;

   public TargetPearl() {
      this.addSettings(
         new Setting[]{
            this.radiusReaktsii,
            this.ispolzovatRotatsiyu,
            this.skorostPovorota,
            this.tochnostPritsela,
            this.maksPromahBloki,
            this.zaderzhkaBroskaMs,
            this.tolkoIzHotbara,
            this.tolkoIzInventarya,
            this.ignorirovatDruzey,
            this.trebovatVladeltsa
         }
      );
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null && CLIENT.interactionManager != null) {
         this.invoke6();
         if (this.intValue2 > 0) {
            this.invoke3();
         } else {
            this.invoke7();
            EnderPearlEntity enderPearlEntity2 = this.resolve2();
            if (enderPearlEntity2 != null) {
               Vec3d vec3d3 = this.resolve3(enderPearlEntity2, this.resolve(enderPearlEntity2));
               if (vec3d3 != null) {
                  Vec3d vec3d4 = CLIENT.player.getEyePos().subtract(0.0, 0.1, 0.0);
                  TargetPearl.TargetPearlState targetPearlState = this.resolve4(vec3d4, vec3d3);
                  if (targetPearlState != null && !(targetPearlState.doubleValue > this.maksPromahBloki.getValue())) {
                     if (this.ispolzovatRotatsiyu.isEnabled()) {
                        float floatValue = this.skorostPovorota.getValue();
                        RotationController.invoke7(new Rotation(targetPearlState.floatValue, targetPearlState.floatValue2), floatValue, floatValue, 6, 5);
                     }

                     if (!this.values.contains(enderPearlEntity2.getId())) {
                        if (System.currentTimeMillis() - this.timestamp >= (long)this.zaderzhkaBroskaMs.getValue()) {
                           if (this.ispolzovatRotatsiyu.isEnabled()) {
                              float floatValue2 = new Rotation(CLIENT.player).measure(new Rotation(targetPearlState.floatValue, targetPearlState.floatValue2));
                              if (floatValue2 > this.tochnostPritsela.getValue()) {
                                 return;
                              }
                           }

                           int intValue = this.compute();
                           if (intValue != -1) {
                              this.invoke(intValue, targetPearlState, enderPearlEntity2.getId());
                              this.values.add(enderPearlEntity2.getId());
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void invoke(int i, TargetPearl.TargetPearlState targetPearlState2, int j) {
      this.intValue4 = i;
      this.intValue = j;
      this.floatValue = targetPearlState2.floatValue;
      this.floatValue2 = targetPearlState2.floatValue2;
      this.intValue5 = -1;
      this.intValue3 = 0;
      this.intValue2 = 1;
      this.invoke3();
   }

   private void invoke2() {
      if (this.intValue != -1 && CLIENT.world != null) {
         if (CLIENT.world.getEntityById(this.intValue) instanceof EnderPearlEntity enderPearlEntity3) {
            Vec3d vec3d5 = this.resolve3(enderPearlEntity3, this.resolve(enderPearlEntity3));
            if (vec3d5 != null) {
               Vec3d vec3d6 = CLIENT.player.getEyePos().subtract(0.0, 0.1, 0.0);
               TargetPearl.TargetPearlState targetPearlState3 = this.resolve4(vec3d6, vec3d5);
               if (targetPearlState3 != null && targetPearlState3.doubleValue <= this.maksPromahBloki.getValue()) {
                  this.floatValue = targetPearlState3.floatValue;
                  this.floatValue2 = targetPearlState3.floatValue2;
               }
            }
         }
      }
   }

   private void invoke3() {
      boolean flag = this.check(this.intValue4);
      if (!flag) {
         Sprint.intValue = 2;
         CLIENT.options.sprintKey.setPressed(false);
         CLIENT.player.setSprinting(false);
         InputUtils.getINSTANCE().invoke("TargetPearl");
      }

      if (this.intValue3 > 0) {
         this.intValue3--;
      } else if (flag) {
         int intValue2 = this.compute2(this.intValue4);
         switch (this.intValue2) {
            case 1:
               this.intValue5 = CLIENT.player.getInventory().getSelectedSlot();
               if (this.intValue5 != intValue2) {
                  CLIENT.player.getInventory().setSelectedSlot(intValue2);
               }

               this.invoke4();
               this.intValue2 = 2;
               this.intValue3 = 1;
               break;
            case 2:
               if (this.intValue5 != intValue2) {
                  CLIENT.player.getInventory().setSelectedSlot(this.intValue5);
               }

               this.invoke5();
         }
      } else {
         switch (this.intValue2) {
            case 1:
               this.intValue2 = 2;
               this.intValue3 = 3;
               break;
            case 2:
               this.intValue5 = CLIENT.player.getInventory().getSelectedSlot();
               if (!CLIENT.player.isSprinting()) {
                  CLIENT.interactionManager
                     .clickSlot(
                        CLIENT.player.playerScreenHandler.syncId, this.intValue4, this.intValue5, SlotActionType.SWAP, CLIENT.player
                     );
               }

               this.intValue2 = 3;
               this.intValue3 = 1;
               break;
            case 3:
               this.invoke4();
               this.intValue2 = 4;
               this.intValue3 = 1;
               break;
            case 4:
               if (!CLIENT.player.isSprinting()) {
                  CLIENT.interactionManager
                     .clickSlot(
                        CLIENT.player.playerScreenHandler.syncId, this.intValue4, this.intValue5, SlotActionType.SWAP, CLIENT.player
                     );
               }

               if (CLIENT.getNetworkHandler() != null) {
                  CLIENT.getNetworkHandler().sendPacket(new CloseHandledScreenC2SPacket(CLIENT.player.playerScreenHandler.syncId));
               }

               this.intValue2 = 5;
               this.intValue3 = 1;
               break;
            case 5:
               InputUtils.getINSTANCE().invoke2("TargetPearl");
               this.invoke5();
         }
      }
   }

   private void invoke4() {
      this.invoke2();
      this.floatValue3 = CLIENT.player.getYaw();
      this.floatValue4 = CLIENT.player.getPitch();
      CLIENT.player.setYaw(this.floatValue);
      CLIENT.player.headYaw = this.floatValue;
      CLIENT.player.setPitch(this.floatValue2);
      CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
      CLIENT.player.swingHand(Hand.MAIN_HAND);
      CLIENT.player.setYaw(this.floatValue3);
      CLIENT.player.headYaw = this.floatValue3;
      CLIENT.player.setPitch(this.floatValue4);
   }

   private void invoke5() {
      this.timestamp = System.currentTimeMillis();
      this.intValue2 = 0;
      this.intValue3 = 0;
      this.intValue4 = -1;
      this.intValue = -1;
      this.intValue5 = -1;
   }

   private void invoke6() {
      this.valuesByKey2.clear();
      HashSet hashSet = new HashSet();

      for (Entity entity2 : CLIENT.world.getEntities()) {
         if (entity2 instanceof EnderPearlEntity enderPearlEntity4) {
            int intValue3 = enderPearlEntity4.getId();
            hashSet.add(intValue3);
            Vec3d vec3d7 = enderPearlEntity4.getPos();
            Vec3d vec3d8 = this.valuesByKey.get(intValue3);
            if (vec3d8 == null && CLIENT.player.getEyePos().squaredDistanceTo(vec3d7) < 9.0) {
               this.values2.add(intValue3);
            }

            this.valuesByKey2.put(intValue3, vec3d8 != null ? vec3d7.subtract(vec3d8) : enderPearlEntity4.getVelocity());
            this.valuesByKey.put(intValue3, vec3d7);
         }
      }

      this.valuesByKey.keySet().retainAll(hashSet);
      this.values2.retainAll(hashSet);
   }

   private Vec3d resolve(EnderPearlEntity enderPearlEntity) {
      Vec3d vec3d9 = enderPearlEntity.getVelocity();
      if (vec3d9.lengthSquared() > 0.001) {
         return vec3d9;
      } else {
         Vec3d vec3d10 = this.valuesByKey2.get(enderPearlEntity.getId());
         return vec3d10 != null ? vec3d10 : vec3d9;
      }
   }

   private EnderPearlEntity resolve2() {
      EnderPearlEntity enderPearlEntity5 = null;
      double doubleValue = Double.MAX_VALUE;
      double doubleValue2 = this.radiusReaktsii.getValue() * this.radiusReaktsii.getValue();

      for (Entity entity3 : CLIENT.world.getEntities()) {
         if (entity3 instanceof EnderPearlEntity enderPearlEntity6 && !(this.resolve(enderPearlEntity6).lengthSquared() < 0.001) && !this.values2.contains(enderPearlEntity6.getId())) {
            PlayerEntity playerEntity = enderPearlEntity6.getOwner() instanceof PlayerEntity playerEntity2 ? playerEntity2 : null;
            if (playerEntity != CLIENT.player
               && (playerEntity == null ? !this.trebovatVladeltsa.isEnabled() : !this.ignorirovatDruzey.isEnabled() || !FriendCommand.check(playerEntity.getName().getString()))) {
               double doubleValue3 = CLIENT.player.squaredDistanceTo(enderPearlEntity6);
               if (!(doubleValue3 > doubleValue2) && doubleValue3 < doubleValue) {
                  doubleValue = doubleValue3;
                  enderPearlEntity5 = enderPearlEntity6;
               }
            }
         }
      }

      return enderPearlEntity5;
   }

   private Vec3d resolve3(EnderPearlEntity enderPearlEntity, Vec3d vec3d) {
      double doubleValue4 = enderPearlEntity.getFinalGravity();
      if (doubleValue4 <= 0.0) {
         doubleValue4 = 0.03;
      }

      double doubleValue5 = enderPearlEntity.isTouchingWater() ? 0.8 : 0.99;
      return this.resolve7(enderPearlEntity.getPos(), vec3d, doubleValue4, doubleValue5, enderPearlEntity, 240);
   }

   private TargetPearl.TargetPearlState resolve4(Vec3d vec3d, Vec3d vec3d2) {
      double doubleValue6 = vec3d2.x - vec3d.x;
      double doubleValue7 = vec3d2.z - vec3d.z;
      float floatValue3 = (float)Math.toDegrees(Math.atan2(-doubleValue6, doubleValue7));
      TargetPearl.TargetPearlState targetPearlState4 = null;

      for (float floatValue4 = -10.0F; floatValue4 <= 10.0F; floatValue4 += 2.0F) {
         float floatValue5 = floatValue3 + floatValue4;

         for (float floatValue6 = -90.0F; floatValue6 <= 90.0F; floatValue6++) {
            TargetPearl.TargetPearlState targetPearlState5 = this.resolve5(vec3d, vec3d2, floatValue5, floatValue6);
            if (targetPearlState5 != null && (targetPearlState4 == null || targetPearlState5.doubleValue < targetPearlState4.doubleValue)) {
               targetPearlState4 = targetPearlState5;
            }
         }
      }

      if (targetPearlState4 == null) {
         return null;
      } else {
         TargetPearl.TargetPearlState targetPearlState6 = targetPearlState4;

         for (float floatValue7 = targetPearlState4.floatValue - 2.0F; floatValue7 <= targetPearlState4.floatValue + 2.0F; floatValue7 += 0.5F) {
            for (float floatValue8 = targetPearlState4.floatValue2 - 2.0F; floatValue8 <= targetPearlState4.floatValue2 + 2.0F; floatValue8 += 0.3F) {
               TargetPearl.TargetPearlState targetPearlState7 = this.resolve5(vec3d, vec3d2, floatValue7, floatValue8);
               if (targetPearlState7 != null && targetPearlState7.doubleValue < targetPearlState6.doubleValue) {
                  targetPearlState6 = targetPearlState7;
               }
            }
         }

         return targetPearlState6;
      }
   }

   private TargetPearl.TargetPearlState resolve5(Vec3d vec3d, Vec3d vec3d2, float f, float g) {
      Vec3d vec3d11 = this.resolve6(f, g);
      Vec3d vec3d12 = this.resolve7(vec3d, vec3d11, 0.03, 0.99, CLIENT.player, 160);
      if (vec3d12 == null) {
         return null;
      } else {
         double doubleValue8 = Math.sqrt(vec3d12.squaredDistanceTo(vec3d2));
         return new TargetPearl.TargetPearlState(MathHelper.wrapDegrees(f), MathHelper.clamp(g, -90.0F, 90.0F), doubleValue8);
      }
   }

   private Vec3d resolve6(float f, float g) {
      float floatValue9 = f * (float) (Math.PI / 180.0);
      float floatValue10 = g * (float) (Math.PI / 180.0);
      double doubleValue9 = -MathHelper.sin(floatValue9) * MathHelper.cos(floatValue10);
      double doubleValue10 = -MathHelper.sin(floatValue10);
      double doubleValue11 = MathHelper.cos(floatValue9) * MathHelper.cos(floatValue10);
      Vec3d vec3d13 = new Vec3d(doubleValue9, doubleValue10, doubleValue11).normalize().multiply(1.5);
      Vec3d vec3d14 = CLIENT.player.getMovement();
      return vec3d13.add(vec3d14.x, CLIENT.player.isOnGround() ? 0.0 : vec3d14.y, vec3d14.z);
   }

   private Vec3d resolve7(Vec3d vec3d, Vec3d vec3d2, double d, double e, Entity entity, int i) {
      if (CLIENT.world == null) {
         return null;
      } else {
         Vec3d vec3d15 = vec3d;
         Vec3d vec3d16 = vec3d2;

         for (int intValue4 = 0; intValue4 < i; intValue4++) {
            vec3d16 = vec3d16.subtract(0.0, d, 0.0).multiply(e);
            Vec3d vec3d17 = vec3d15.add(vec3d16);
            BlockHitResult blockHitResult = CLIENT.world.raycast(new RaycastContext(vec3d15, vec3d17, ShapeType.COLLIDER, FluidHandling.NONE, entity));
            if (blockHitResult.getType() != Type.MISS) {
               return blockHitResult.getPos();
            }

            Box box = new Box(vec3d15, vec3d17).expand(1.0);
            double doubleValue12 = Double.MAX_VALUE;
            Vec3d vec3d18 = null;

            for (Entity entity4 : CLIENT.world
               .getOtherEntities(entity, box, entityx -> entityx.isAlive() && !entityx.isSpectator() && entityx instanceof PlayerEntity)) {
               Box box2 = entity4.getBoundingBox().expand(0.3);
               Optional optional = box2.raycast(vec3d15, vec3d17);
               if (optional.isPresent()) {
                  double doubleValue13 = vec3d15.squaredDistanceTo((Vec3d)optional.get());
                  if (doubleValue13 < doubleValue12) {
                     doubleValue12 = doubleValue13;
                     vec3d18 = (Vec3d)optional.get();
                  }
               }
            }

            if (vec3d18 != null) {
               return vec3d18;
            }

            vec3d15 = vec3d17;
         }

         return vec3d15;
      }
   }

   private int compute() {
      for (int intValue5 = 0; intValue5 < 36; intValue5++) {
         if (CLIENT.player.getInventory().getStack(intValue5).getItem() == Items.ENDER_PEARL) {
            boolean flag2 = intValue5 < 9;
            if ((!this.tolkoIzInventarya.isEnabled() || !flag2) && (!this.tolkoIzHotbara.isEnabled() || flag2)) {
               return flag2 ? intValue5 + 36 : intValue5;
            }
         }
      }

      return -1;
   }

   private boolean check(int i) {
      return i >= 0 && i <= 8 || i >= 36 && i <= 44;
   }

   private int compute2(int i) {
      if (i >= 0 && i <= 8) {
         return i;
      } else {
         return i >= 36 && i <= 44 ? i - 36 : -1;
      }
   }

   private void invoke7() {
      if (!this.values.isEmpty()) {
         this.values.removeIf(integer -> CLIENT.world.getEntityById(integer) == null);
      }
   }

   @Override
   public void onDisable() {
      if (this.intValue2 > 0 && !this.check(this.intValue4)) {
         InputUtils.getINSTANCE().invoke2("TargetPearl");
      }

      this.values.clear();
      this.values2.clear();
      this.valuesByKey.clear();
      this.valuesByKey2.clear();
      this.intValue2 = 0;
      this.intValue3 = 0;
      this.intValue4 = -1;
      this.intValue = -1;
      FreeLookController.active = FreeLookController.flag;
      super.onDisable();
   }

   static final class TargetPearlState {
      final float floatValue;
      final float floatValue2;
      final double doubleValue;

      TargetPearlState(float f, float g, double d) {
         this.floatValue = f;
         this.floatValue2 = g;
         this.doubleValue = d;
      }
   }
}
