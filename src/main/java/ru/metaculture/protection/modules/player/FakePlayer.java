package ru.metaculture.protection;

import com.mojang.authlib.GameProfile;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DeathProtectionComponent;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.Entity.RemovalReason;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "FakePlayer",
   category = Category.Player,
   description = "Создаёт локального WildBot для тренировки атак и тотемов"
)
public final class FakePlayer extends Module {
   private static final UUID U_UID = UUID.nameUUIDFromBytes("WildClient:WildBot".getBytes(StandardCharsets.UTF_8));
   private static final int INT_VALUE = -1337;
   private static final int INT_VALUE_2 = 20;
   private static final float FLOAT_VALUE = 0.1F;
   private static final float FLOAT_VALUE_2 = 0.5F;
   private static final float FLOAT_VALUE_3 = 1.5F;
   private final ModeSetting bronya = new ModeSetting(
      "Броня", "Копировать", "Копировать", "Без брони", "Кожаная", "Кольчужная", "Золотая", "Железная", "Алмазная", "Незеритовая"
   );
   private final BooleanSetting snyatieTotemov = new BooleanSetting("Снятие тотемов", true);
   private final ModeSetting povedenie = new ModeSetting("Поведение", "Манекен", "Манекен", "Подвижный");
   private final NumberSetting aktivnost = new NumberSetting("Активность", 1.0F, 0.3F, 1.5F, 0.05F, false)
      .setVisibilityCondition(() -> !this.povedenie.is("Подвижный"));
   private final BooleanSetting pryzhki = new BooleanSetting("Прыжки", true).visibleWhen(() -> !this.povedenie.is("Подвижный"));
   private final BooleanSetting zamahi = new BooleanSetting("Замахи", true).visibleWhen(() -> !this.povedenie.is("Подвижный"));
   private FakePlayer.FakePlayerVariant fakePlayerVariant;
   private ClientWorld clientWorld;
   private int intValue;
   private String text;
   private double doubleValue;
   private double doubleValue2;
   private double doubleValue3;
   private int intValue2 = 1;
   private int intValue3;
   private double doubleValue4 = 3.0;
   private int intValue4;
   private int intValue5;
   private int intValue6;
   private int intValue7;

   public FakePlayer() {
      this.addSettings(new Setting[]{this.povedenie, this.aktivnost, this.pryzhki, this.zamahi, this.bronya, this.snyatieTotemov});
   }

   @Override
   public void onEnable() {
      this.invoke2();
      super.onEnable();
   }

   @Override
   public void onDisable() {
      this.invoke12();
      super.onDisable();
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player == null || CLIENT.world == null) {
         this.invoke12();
      } else if (this.fakePlayerVariant != null && !this.fakePlayerVariant.isRemoved() && this.clientWorld == CLIENT.world) {
         if (!this.bronya.getValue().equals(this.text)) {
            this.invoke4(this.fakePlayerVariant);
         }

         this.invoke11();
         if (this.intValue > 0) {
            this.intValue--;
         } else if (this.fakePlayerVariant.getHealth() < this.fakePlayerVariant.getMaxHealth()) {
            this.fakePlayerVariant.heal(0.1F);
         }
      } else {
         this.invoke2();
      }
   }

   void invoke(FakePlayer.FakePlayerVariant fakePlayerVariant) {
      if (fakePlayerVariant == this.fakePlayerVariant && CLIENT.player != null && CLIENT.world != null) {
         if (!this.povedenie.is("Подвижный")) {
            this.doubleValue = 0.0;
            this.doubleValue3 = 0.0;
            fakePlayerVariant.setSprinting(false);
            if (fakePlayerVariant.isOnGround()) {
               this.doubleValue2 = 0.0;
               fakePlayerVariant.setVelocity(Vec3d.ZERO);
            } else {
               this.doubleValue2 = (this.doubleValue2 - 0.08) * 0.98;
               fakePlayerVariant.move(MovementType.SELF, new Vec3d(0.0, this.doubleValue2, 0.0));
               fakePlayerVariant.setVelocity(0.0, fakePlayerVariant.getY() - fakePlayerVariant.lastY, 0.0);
            }
         } else {
            ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
            double doubleValue = CLIENT.player.getX() - fakePlayerVariant.getX();
            double doubleValue2 = CLIENT.player.getZ() - fakePlayerVariant.getZ();
            double doubleValue3 = Math.hypot(doubleValue, doubleValue2);
            if (!(fakePlayerVariant.getY() < CLIENT.player.getY() - 24.0) && !(doubleValue3 > 16.0)) {
               float floatValue = this.aktivnost.getValue();
               if (--this.intValue3 <= 0) {
                  this.intValue2 = threadLocalRandom.nextBoolean() ? 1 : -1;
                  this.intValue3 = threadLocalRandom.nextInt(18, 60);
               }

               if (--this.intValue4 <= 0) {
                  this.doubleValue4 = threadLocalRandom.nextDouble(1.6, 4.4);
                  this.intValue4 = threadLocalRandom.nextInt(40, 110);
               }

               if (this.intValue7 > 0) {
                  this.intValue7--;
               } else if (threadLocalRandom.nextFloat() < 0.005F) {
                  this.intValue7 = threadLocalRandom.nextInt(6, 18);
               }

               double doubleValue4 = doubleValue3 < 1.0E-4 ? 0.0 : 1.0 / doubleValue3;
               double doubleValue5 = doubleValue * doubleValue4;
               double doubleValue6 = doubleValue2 * doubleValue4;
               double doubleValue7 = MathHelper.clamp((doubleValue3 - this.doubleValue4) * 0.45, -1.0, 1.0);
               double doubleValue8 = doubleValue5 * doubleValue7 - doubleValue6 * this.intValue2 * 0.9;
               double doubleValue9 = doubleValue6 * doubleValue7 + doubleValue5 * this.intValue2 * 0.9;
               double doubleValue10 = Math.hypot(doubleValue8, doubleValue9);
               if (doubleValue10 > 1.0) {
                  doubleValue8 /= doubleValue10;
                  doubleValue9 /= doubleValue10;
               }

               double doubleValue11 = this.intValue7 > 0 ? 0.0 : 0.26 * floatValue;
               double doubleValue12 = fakePlayerVariant.isOnGround() ? 0.3 : 0.1;
               this.doubleValue = this.doubleValue + (doubleValue8 * doubleValue11 - this.doubleValue) * doubleValue12;
               this.doubleValue3 = this.doubleValue3 + (doubleValue9 * doubleValue11 - this.doubleValue3) * doubleValue12;
               if (this.intValue5 > 0) {
                  this.intValue5--;
               }

               if (fakePlayerVariant.isOnGround()) {
                  this.doubleValue2 = -0.0784;
                  if (this.pryzhki.isEnabled() && this.intValue5 == 0 && (fakePlayerVariant.horizontalCollision || threadLocalRandom.nextFloat() < 0.035F * floatValue)) {
                     this.doubleValue2 = 0.42;
                     this.intValue5 = threadLocalRandom.nextInt(25, 70);
                  }
               } else {
                  this.doubleValue2 = (this.doubleValue2 - 0.08) * 0.98;
               }

               fakePlayerVariant.setSprinting(Math.hypot(this.doubleValue, this.doubleValue3) > 0.18);
               fakePlayerVariant.move(MovementType.SELF, new Vec3d(this.doubleValue, this.doubleValue2, this.doubleValue3));
               fakePlayerVariant.setVelocity(fakePlayerVariant.getX() - fakePlayerVariant.lastX, fakePlayerVariant.getY() - fakePlayerVariant.lastY, fakePlayerVariant.getZ() - fakePlayerVariant.lastZ);
               float floatValue2 = (float)Math.toDegrees(Math.atan2(-(CLIENT.player.getX() - fakePlayerVariant.getX()), CLIENT.player.getZ() - fakePlayerVariant.getZ()));
               double doubleValue13 = CLIENT.player.getX() - fakePlayerVariant.getX();
               double doubleValue14 = CLIENT.player.getEyeY() - fakePlayerVariant.getEyeY();
               double doubleValue15 = CLIENT.player.getZ() - fakePlayerVariant.getZ();
               float floatValue3 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(doubleValue14, Math.hypot(doubleValue13, doubleValue15))), -60.0, 60.0);
               fakePlayerVariant.headYaw = fakePlayerVariant.headYaw + MathHelper.clamp(MathHelper.wrapDegrees(floatValue2 - fakePlayerVariant.headYaw), -30.0F, 30.0F);
               fakePlayerVariant.setYaw(fakePlayerVariant.headYaw);
               fakePlayerVariant.setPitch(fakePlayerVariant.getPitch() + MathHelper.clamp(floatValue3 - fakePlayerVariant.getPitch(), -15.0F, 15.0F));
               if (this.intValue6 > 0) {
                  this.intValue6--;
               }

               if (this.zamahi.isEnabled() && this.intValue6 == 0 && doubleValue3 < 3.2 && threadLocalRandom.nextFloat() < 0.3F) {
                  fakePlayerVariant.swingHand(Hand.MAIN_HAND);
                  this.intValue6 = threadLocalRandom.nextInt(11, 22);
               }
            } else {
               double doubleValue16 = threadLocalRandom.nextDouble(0.0, Math.PI * 2);
               fakePlayerVariant.refreshPositionAndAngles(
                  CLIENT.player.getX() + Math.cos(doubleValue16) * 3.0,
                  CLIENT.player.getY(),
                  CLIENT.player.getZ() + Math.sin(doubleValue16) * 3.0,
                  fakePlayerVariant.getYaw(),
                  0.0F
               );
               this.doubleValue = 0.0;
               this.doubleValue2 = 0.0;
               this.doubleValue3 = 0.0;
            }
         }
      }
   }

   @EventHandler
   public void onWorldJoin(WorldJoinEvent worldJoinEvent) {
      this.invoke12();
   }

   public static boolean check(Entity entity) {
      FakePlayer fakePlayer = resolve();
      return fakePlayer != null && fakePlayer.check3(entity);
   }

   public static boolean check2(Entity entity) {
      FakePlayer fakePlayer2 = resolve();
      return fakePlayer2 != null && entity == fakePlayer2.fakePlayerVariant;
   }

   static FakePlayer resolve() {
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
         FakePlayer fakePlayer3 = WildClient.INSTANCE.moduleManager.getModule(FakePlayer.class);
         return fakePlayer3 != null && fakePlayer3.enabled ? fakePlayer3 : null;
      } else {
         return null;
      }
   }

   private void invoke2() {
      this.invoke12();
      if (CLIENT.player != null && CLIENT.world != null) {
         GameProfile gameProfile2 = new GameProfile(U_UID, "WildBot");
         gameProfile2.getProperties().putAll(CLIENT.player.getGameProfile().getProperties());
         SkinTextures skinTextures2 = CLIENT.player.getSkinTextures();
         FakePlayer.FakePlayerVariant fakePlayerVariant2 = new FakePlayer.FakePlayerVariant(CLIENT.world, gameProfile2, skinTextures2);
         fakePlayerVariant2.setId(this.compute(CLIENT.world));
         Vec3d vec3d = CLIENT.player.getRotationVec(1.0F);
         Vec3d vec3d2 = new Vec3d(vec3d.x, 0.0, vec3d.z);
         if (vec3d2.lengthSquared() < 1.0E-6) {
            vec3d2 = new Vec3d(0.0, 0.0, 1.0);
         } else {
            vec3d2 = vec3d2.normalize();
         }

         double doubleValue17 = CLIENT.player.getX() + vec3d2.x * 2.5;
         double doubleValue18 = CLIENT.player.getY();
         double doubleValue19 = CLIENT.player.getZ() + vec3d2.z * 2.5;
         float floatValue4 = CLIENT.player.getYaw() + 180.0F;
         fakePlayerVariant2.refreshPositionAndAngles(doubleValue17, doubleValue18, doubleValue19, floatValue4, 0.0F);
         fakePlayerVariant2.bodyYaw = floatValue4;
         fakePlayerVariant2.headYaw = floatValue4;
         fakePlayerVariant2.lastBodyYaw = floatValue4;
         fakePlayerVariant2.lastHeadYaw = floatValue4;
         fakePlayerVariant2.setOnGround(true);
         this.invoke3(fakePlayerVariant2);
         fakePlayerVariant2.setHealth(fakePlayerVariant2.getMaxHealth());
         CLIENT.world.addEntity(fakePlayerVariant2);
         this.fakePlayerVariant = fakePlayerVariant2;
         this.clientWorld = CLIENT.world;
         this.intValue = 0;
         this.doubleValue = 0.0;
         this.doubleValue2 = 0.0;
         this.doubleValue3 = 0.0;
         this.intValue3 = 0;
         this.intValue4 = 0;
         this.intValue5 = 0;
         this.intValue6 = 0;
         this.intValue7 = 0;
      }
   }

   private void invoke3(FakePlayer.FakePlayerVariant fakePlayerVariant3) {
      this.invoke4(fakePlayerVariant3);
      fakePlayerVariant3.equipStack(EquipmentSlot.MAINHAND, CLIENT.player.getMainHandStack().copy());
      fakePlayerVariant3.setStackInHand(Hand.OFF_HAND, new ItemStack(Items.TOTEM_OF_UNDYING));
   }

   private void invoke4(FakePlayer.FakePlayerVariant fakePlayerVariant4) {
      String text = this.bronya.getValue();
      switch (text) {
         case "Без брони":
            this.invoke5(fakePlayerVariant4, null, null, null, null);
            break;
         case "Кожаная":
            this.invoke5(fakePlayerVariant4, Items.LEATHER_HELMET, Items.LEATHER_CHESTPLATE, Items.LEATHER_LEGGINGS, Items.LEATHER_BOOTS);
            break;
         case "Кольчужная":
            this.invoke5(fakePlayerVariant4, Items.CHAINMAIL_HELMET, Items.CHAINMAIL_CHESTPLATE, Items.CHAINMAIL_LEGGINGS, Items.CHAINMAIL_BOOTS);
            break;
         case "Золотая":
            this.invoke5(fakePlayerVariant4, Items.GOLDEN_HELMET, Items.GOLDEN_CHESTPLATE, Items.GOLDEN_LEGGINGS, Items.GOLDEN_BOOTS);
            break;
         case "Железная":
            this.invoke5(fakePlayerVariant4, Items.IRON_HELMET, Items.IRON_CHESTPLATE, Items.IRON_LEGGINGS, Items.IRON_BOOTS);
            break;
         case "Алмазная":
            this.invoke5(fakePlayerVariant4, Items.DIAMOND_HELMET, Items.DIAMOND_CHESTPLATE, Items.DIAMOND_LEGGINGS, Items.DIAMOND_BOOTS);
            break;
         case "Незеритовая":
            this.invoke5(fakePlayerVariant4, Items.NETHERITE_HELMET, Items.NETHERITE_CHESTPLATE, Items.NETHERITE_LEGGINGS, Items.NETHERITE_BOOTS);
            break;
         default:
            fakePlayerVariant4.equipStack(EquipmentSlot.HEAD, CLIENT.player.getEquippedStack(EquipmentSlot.HEAD).copy());
            fakePlayerVariant4.equipStack(EquipmentSlot.CHEST, CLIENT.player.getEquippedStack(EquipmentSlot.CHEST).copy());
            fakePlayerVariant4.equipStack(EquipmentSlot.LEGS, CLIENT.player.getEquippedStack(EquipmentSlot.LEGS).copy());
            fakePlayerVariant4.equipStack(EquipmentSlot.FEET, CLIENT.player.getEquippedStack(EquipmentSlot.FEET).copy());
      }

      this.text = this.bronya.getValue();
   }

   private void invoke5(FakePlayer.FakePlayerVariant fakePlayerVariant5, Item item, Item item2, Item item3, Item item4) {
      fakePlayerVariant5.equipStack(EquipmentSlot.HEAD, this.resolve2(item));
      fakePlayerVariant5.equipStack(EquipmentSlot.CHEST, this.resolve2(item2));
      fakePlayerVariant5.equipStack(EquipmentSlot.LEGS, this.resolve2(item3));
      fakePlayerVariant5.equipStack(EquipmentSlot.FEET, this.resolve2(item4));
   }

   private ItemStack resolve2(Item item) {
      return item == null ? ItemStack.EMPTY : new ItemStack(item);
   }

   private boolean check3(Entity entity) {
      if (entity == this.fakePlayerVariant
         && this.fakePlayerVariant != null
         && !this.fakePlayerVariant.isRemoved()
         && CLIENT.player != null
         && CLIENT.world != null) {
         float floatValue5 = CLIENT.player.getAttackCooldownProgress(0.5F);
         boolean flag = floatValue5 > 0.9F
            && CLIENT.player.fallDistance > 0.0
            && !CLIENT.player.isOnGround()
            && !CLIENT.player.isClimbing()
            && !CLIENT.player.isTouchingWater()
            && !CLIENT.player.hasStatusEffect(StatusEffects.BLINDNESS)
            && !CLIENT.player.hasVehicle()
            && !CLIENT.player.isSprinting();
         float floatValue6 = 0.5F * (flag ? 1.5F : 1.0F);
         this.intValue = 20;
         CLIENT.player.resetLastAttackedTicks();
         this.fakePlayerVariant.animateDamage(CLIENT.player.getYaw());
         this.invoke6(flag, floatValue5);
         this.invoke7(flag);
         if (!this.snyatieTotemov.isEnabled()) {
            this.fakePlayerVariant.setHealth(this.fakePlayerVariant.getMaxHealth());
            this.fakePlayerVariant.setAbsorptionAmount(0.0F);
            this.intValue = 0;
            return true;
         } else {
            float floatValue7 = Math.max(0.0F, floatValue6);
            float floatValue8 = Math.min(this.fakePlayerVariant.getAbsorptionAmount(), floatValue7);
            if (floatValue8 > 0.0F) {
               this.fakePlayerVariant.setAbsorptionAmount(this.fakePlayerVariant.getAbsorptionAmount() - floatValue8);
               floatValue7 -= floatValue8;
            }

            float floatValue9 = this.fakePlayerVariant.getHealth() - floatValue7;
            if (floatValue9 <= 0.0F) {
               this.invoke8();
            } else {
               this.fakePlayerVariant.setHealth(floatValue9);
               CLIENT.world
                  .playSoundClient(
                     this.fakePlayerVariant.getX(),
                     this.fakePlayerVariant.getY(),
                     this.fakePlayerVariant.getZ(),
                     SoundEvents.ENTITY_PLAYER_HURT,
                     SoundCategory.PLAYERS,
                     1.0F,
                     1.0F,
                     false
                  );
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private void invoke6(boolean bl, float f) {
      CLIENT.world
         .playSoundClient(
            CLIENT.player.getX(),
            CLIENT.player.getY(),
            CLIENT.player.getZ(),
            bl ? SoundEvents.ENTITY_PLAYER_ATTACK_CRIT : (f > 0.9F ? SoundEvents.ENTITY_PLAYER_ATTACK_STRONG : SoundEvents.ENTITY_PLAYER_ATTACK_WEAK),
            SoundCategory.PLAYERS,
            1.0F,
            1.0F,
            false
         );
   }

   private void invoke7(boolean bl) {
      ThreadLocalRandom threadLocalRandom2 = ThreadLocalRandom.current();
      int intValue = bl ? 18 : 7;

      for (int intValue2 = 0; intValue2 < intValue; intValue2++) {
         double doubleValue20 = this.fakePlayerVariant.getX() + threadLocalRandom2.nextDouble(-0.32, 0.32);
         double doubleValue21 = this.fakePlayerVariant.getBodyY(threadLocalRandom2.nextDouble(0.25, 0.85));
         double doubleValue22 = this.fakePlayerVariant.getZ() + threadLocalRandom2.nextDouble(-0.32, 0.32);
         double doubleValue23 = threadLocalRandom2.nextDouble(-0.35, 0.35);
         double doubleValue24 = threadLocalRandom2.nextDouble(0.05, 0.45);
         double doubleValue25 = threadLocalRandom2.nextDouble(-0.35, 0.35);
         this.invoke10(ParticleTypes.CRIT, doubleValue20, doubleValue21, doubleValue22, doubleValue23, doubleValue24, doubleValue25);
      }

      for (int intValue3 = 0; intValue3 < 4; intValue3++) {
         this.invoke10(
            ParticleTypes.DAMAGE_INDICATOR,
            this.fakePlayerVariant.getX() + threadLocalRandom2.nextDouble(-0.2, 0.2),
            this.fakePlayerVariant.getBodyY(threadLocalRandom2.nextDouble(0.35, 0.75)),
            this.fakePlayerVariant.getZ() + threadLocalRandom2.nextDouble(-0.2, 0.2),
            threadLocalRandom2.nextDouble(-0.08, 0.08),
            threadLocalRandom2.nextDouble(0.05, 0.18),
            threadLocalRandom2.nextDouble(-0.08, 0.08)
         );
      }

      if (CLIENT.player.getMainHandStack().hasEnchantments()) {
         for (int intValue4 = 0; intValue4 < 12; intValue4++) {
            this.invoke10(
               ParticleTypes.ENCHANTED_HIT,
               this.fakePlayerVariant.getX() + threadLocalRandom2.nextDouble(-0.35, 0.35),
               this.fakePlayerVariant.getBodyY(threadLocalRandom2.nextDouble(0.2, 0.9)),
               this.fakePlayerVariant.getZ() + threadLocalRandom2.nextDouble(-0.35, 0.35),
               threadLocalRandom2.nextDouble(-0.45, 0.45),
               threadLocalRandom2.nextDouble(0.05, 0.5),
               threadLocalRandom2.nextDouble(-0.45, 0.45)
            );
         }
      }
   }

   private void invoke8() {
      ItemStack itemStack = new ItemStack(Items.TOTEM_OF_UNDYING);
      this.fakePlayerVariant.setHealth(1.0F);
      this.fakePlayerVariant.deathTime = 0;
      DeathProtectionComponent deathProtectionComponent = (DeathProtectionComponent)itemStack.get(DataComponentTypes.DEATH_PROTECTION);
      if (deathProtectionComponent != null) {
         deathProtectionComponent.applyDeathEffects(itemStack, this.fakePlayerVariant);
      }

      this.intValue = 20;
      this.invoke11();
      this.invoke9();
      CLIENT.world
         .playSoundClient(
            this.fakePlayerVariant.getX(),
            this.fakePlayerVariant.getY(),
            this.fakePlayerVariant.getZ(),
            SoundEvents.ITEM_TOTEM_USE,
            SoundCategory.PLAYERS,
            1.0F,
            1.0F,
            false
         );
   }

   private void invoke9() {
      ThreadLocalRandom threadLocalRandom3 = ThreadLocalRandom.current();

      for (int intValue5 = 0; intValue5 < 72; intValue5++) {
         double doubleValue26 = threadLocalRandom3.nextDouble(0.0, Math.PI * 2);
         double doubleValue27 = threadLocalRandom3.nextDouble(0.05, 0.48);
         double doubleValue28 = this.fakePlayerVariant.getX() + Math.cos(doubleValue26) * doubleValue27;
         double doubleValue29 = this.fakePlayerVariant.getBodyY(threadLocalRandom3.nextDouble(0.05, 0.95));
         double doubleValue30 = this.fakePlayerVariant.getZ() + Math.sin(doubleValue26) * doubleValue27;
         double doubleValue31 = threadLocalRandom3.nextDouble(0.12, 0.65);
         double doubleValue32 = Math.cos(doubleValue26) * doubleValue31 + threadLocalRandom3.nextDouble(-0.12, 0.12);
         double doubleValue33 = threadLocalRandom3.nextDouble(0.15, 0.85);
         double doubleValue34 = Math.sin(doubleValue26) * doubleValue31 + threadLocalRandom3.nextDouble(-0.12, 0.12);
         this.invoke10(ParticleTypes.TOTEM_OF_UNDYING, doubleValue28, doubleValue29, doubleValue30, doubleValue32, doubleValue33, doubleValue34);
      }
   }

   private void invoke10(ParticleEffect particleEffect, double d, double e, double f, double g, double h, double i) {
      CLIENT.world.addParticleClient(particleEffect, true, true, d, e, f, g, h, i);
   }

   private void invoke11() {
      if (this.fakePlayerVariant != null) {
         if (!this.snyatieTotemov.isEnabled()) {
            if (!this.fakePlayerVariant.getOffHandStack().isEmpty()) {
               this.fakePlayerVariant.setStackInHand(Hand.OFF_HAND, ItemStack.EMPTY);
            }

            this.fakePlayerVariant.setHealth(this.fakePlayerVariant.getMaxHealth());
            this.fakePlayerVariant.setAbsorptionAmount(0.0F);
         } else {
            if (!this.fakePlayerVariant.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
               this.fakePlayerVariant.setStackInHand(Hand.OFF_HAND, new ItemStack(Items.TOTEM_OF_UNDYING));
            }
         }
      }
   }

   private int compute(ClientWorld clientWorld) {
      int intValue6 = -1337;

      while (clientWorld.getEntityById(intValue6) != null) {
         intValue6--;
      }

      return intValue6;
   }

   private void invoke12() {
      if (this.fakePlayerVariant != null && this.clientWorld != null) {
         this.clientWorld.removeEntity(this.fakePlayerVariant.getId(), RemovalReason.DISCARDED);
      }

      this.fakePlayerVariant = null;
      this.clientWorld = null;
      this.intValue = 0;
      this.text = null;
   }

   static final class FakePlayerVariant extends OtherClientPlayerEntity {
      private final SkinTextures skinTextures;

      FakePlayerVariant(ClientWorld clientWorld, GameProfile gameProfile, SkinTextures skinTextures) {
         super(clientWorld, gameProfile);
         this.skinTextures = skinTextures;
      }

      public void tick() {
         FakePlayer fakePlayer4 = FakePlayer.resolve();
         if (fakePlayer4 != null) {
            fakePlayer4.invoke(this);
         }

         super.tick();
      }

      public SkinTextures getSkinTextures() {
         return this.skinTextures != null ? this.skinTextures : super.getSkinTextures();
      }
   }
}
