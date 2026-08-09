package ru.metaculture.protection;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "TriggerBot",
   description = "Бьет энтити при наведении на него",
   category = Category.Combat,
   riskLevels = {ModuleRiskLevel.RISKY, ModuleRiskLevel.GRIM}
)
public class TriggerBot extends Module {
   public static NumberSetting distantsiya = new NumberSetting("Дистанция", 4.5F, 3.0F, 8.0F, 0.1F, false);
   public static GroupSetting tseli = new GroupSetting(
      "Цели",
      new BooleanSetting("Игроки", true),
      new BooleanSetting("Голые", true),
      new BooleanSetting("Невидимки", true),
      new BooleanSetting("Голые невидимки", false),
      new BooleanSetting("Друзья", false),
      new BooleanSetting("NPC", true),
      new BooleanSetting("Мобы", false),
      new BooleanSetting("Животные", false),
      new BooleanSetting("Жители", false)
   );
   public static GroupSetting proverkiDoUdara = new GroupSetting(
      "Проверки до удара",
      new BooleanSetting("Бить через блоки", false),
      new BooleanSetting("Бить только оружием", false),
      new BooleanSetting("Не бить если кушаешь", true),
      new BooleanSetting("Не бить в контейнерах ", false),
      new BooleanSetting("Ломать щит", false),
      new BooleanSetting("Отжим щита", false)
   );
   public static GroupSetting dopolnitelnyeNastroyki = new GroupSetting(
      "Дополнительные настройки",
      new BooleanSetting("Расширенная настройки для атаки", false),
      new BooleanSetting("Умные криты", false),
      new BooleanSetting("Увеличенная дистанция удара", false),
      new BooleanSetting("Приоритет ближайшей цели", true)
   );
   public static NumberSetting radiusAtakiDlyaMobov = new NumberSetting("Радиус атаки для мобов", 4.5F, 3.0F, 8.0F, 0.1F, false)
      .setVisibilityCondition(() -> !dopolnitelnyeNastroyki.isEnabled("Расширенная настройки для атаки"));
   public static NumberSetting radiusAtakiDlyaIgrokov = new NumberSetting("Радиус атаки для игроков", 4.5F, 3.0F, 8.0F, 0.1F, false)
      .setVisibilityCondition(() -> !dopolnitelnyeNastroyki.isEnabled("Расширенная настройки для атаки"));
   public static LivingEntity livingEntity;
   private static long timestamp = 0L;
   private static boolean flag = false;
   private static float floatValue = 0.0F;

   public TriggerBot() {
      this.addSettings(new Setting[]{distantsiya, tseli, proverkiDoUdara, dopolnitelnyeNastroyki, radiusAtakiDlyaMobov, radiusAtakiDlyaIgrokov});
   }

   public static LivingEntity getLivingEntity() {
      return livingEntity;
   }

   @Override
   public void onDisable() {
      livingEntity = null;
      flag = false;
      floatValue = 0.0F;
      timestamp = 0L;
      super.onDisable();
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null) {
         this.invoke();
         if (!AttackAura.check16()) {
            LivingEntity livingEntity2 = this.resolve();
            if (livingEntity2 != null) {
               if (!this.check()) {
                  float floatValue = measure(livingEntity2);
                  float[] floatValues = new float[]{floatValue, 0.0F, floatValue};
                  AttackUtils.invoke6(livingEntity2, true, true, false);
                  boolean flag = dopolnitelnyeNastroyki.isEnabled("Умные криты");
                  if (AttackUtils.check15(livingEntity2, false, true, flag, 0L, floatValues)) {
                     Runnable[] runnables = AttackUtils.resolve(livingEntity2, proverkiDoUdara.isEnabled("Ломать щит"));
                     Runnable[] runnables2 = AttackUtils.resolve2(true);
                     Runnable[] runnables3 = AttackUtils.resolve3(false);
                     Runnable runnable = () -> {
                        runnables3[0].run();
                        runnables2[0].run();
                        runnables[0].run();
                     };
                     Runnable runnable2 = () -> {
                        runnables[1].run();
                        runnables2[1].run();
                        runnables3[1].run();
                     };
                     if (proverkiDoUdara.isEnabled("Отжим щита")
                        && CLIENT.player.getActiveItem().getItem().equals(Items.SHIELD)
                        && CLIENT.player.isUsingItem()) {
                        CLIENT.interactionManager.stopUsingItem(CLIENT.player);
                     }

                     if (AttackUtils.check5(livingEntity2, runnable, runnable2, Hand.MAIN_HAND, true)) {
                        livingEntity = livingEntity2;
                     }
                  }
               }
            }
         }
      } else {
         livingEntity = null;
      }
   }

   private void invoke() {
      if (livingEntity != null) {
         if (!livingEntity.isAlive()
            || livingEntity.isRemoved()
            || CLIENT.player == null
            || CLIENT.player.distanceTo(livingEntity) > measure(livingEntity) + 2.0F) {
            livingEntity = null;
         }
      }
   }

   private LivingEntity resolve() {
      LivingEntity livingEntity3 = null;
      double doubleValue = Double.MAX_VALUE;
      float floatValue2 = CLIENT.player.getYaw();
      float floatValue3 = CLIENT.player.getPitch();
      boolean flag2 = dopolnitelnyeNastroyki.isEnabled("Приоритет ближайшей цели");
      boolean flag3 = proverkiDoUdara.isEnabled("Бить через блоки");
      Vec3d vec3d = CLIENT.player.getEyePos();
      Vec3d vec3d2 = CLIENT.player.getRotationVec(1.0F).normalize();

      for (Entity entity : CLIENT.world.getEntities()) {
         if (entity instanceof LivingEntity livingEntity4 && this.check2(livingEntity4) && EntityRaycastUtils.check4(floatValue2, floatValue3, measure(livingEntity4), livingEntity4, flag3)) {
            double doubleValue2;
            if (flag2) {
               doubleValue2 = CLIENT.player.squaredDistanceTo(livingEntity4);
            } else {
               Vec3d vec3d3 = livingEntity4.getPos().add(0.0, livingEntity4.getHeight() * 0.5, 0.0);
               Vec3d vec3d4 = vec3d3.subtract(vec3d).normalize();
               doubleValue2 = Math.acos(MathHelper.clamp(vec3d2.dotProduct(vec3d4), -1.0, 1.0));
            }

            if (doubleValue2 < doubleValue) {
               doubleValue = doubleValue2;
               livingEntity3 = livingEntity4;
            }
         }
      }

      return livingEntity3;
   }

   public static float measure(LivingEntity livingEntity) {
      if (livingEntity == null) {
         return distantsiya.getValue();
      } else {
         float floatValue4 = distantsiya.getValue();
         if (dopolnitelnyeNastroyki.isEnabled("Расширенная настройки для атаки")) {
            floatValue4 = livingEntity instanceof PlayerEntity ? radiusAtakiDlyaIgrokov.getValue() : radiusAtakiDlyaMobov.getValue();
         }

         if (dopolnitelnyeNastroyki.isEnabled("Увеличенная дистанция удара")) {
            float floatValue5 = livingEntity.getHealth() + livingEntity.getAbsorptionAmount();
            if (floatValue5 >= 10.0F && floatValue5 <= 12.0F) {
               long longValue = System.currentTimeMillis();
               if (longValue >= timestamp) {
                  if (ThreadLocalRandom.current().nextInt(100) < 25) {
                     flag = true;
                     floatValue = 0.1F + ThreadLocalRandom.current().nextFloat() * 0.05F;
                     timestamp = longValue + ThreadLocalRandom.current().nextLong(400L, 700L);
                  } else {
                     flag = false;
                     floatValue = 0.0F;
                     timestamp = longValue + ThreadLocalRandom.current().nextLong(1500L, 2500L);
                  }
               }

               if (flag) {
                  return floatValue4 + floatValue;
               }
            } else {
               flag = false;
               floatValue = 0.0F;
            }
         }

         return floatValue4;
      }
   }

   private boolean check() {
      return CLIENT.player.isUsingItem()
            && proverkiDoUdara.isEnabled("Не бить если кушаешь")
            && !(CLIENT.player.getActiveItem().getItem() instanceof ShieldItem)
         || CLIENT.currentScreen != null && proverkiDoUdara.isEnabled("Не бить в контейнерах ")
         || !CLIENT.player.getMainHandStack().isIn(ItemTags.SWORDS)
            && !CLIENT.player.getMainHandStack().isIn(ItemTags.AXES)
            && proverkiDoUdara.isEnabled("Бить только оружием");
   }

   private boolean check2(LivingEntity livingEntity) {
      if (livingEntity instanceof ClientPlayerEntity || livingEntity == CLIENT.player) {
         return false;
      } else if (livingEntity.isAlive() && !livingEntity.isInvulnerable() && !(livingEntity instanceof ArmorStandEntity)) {
         if (CLIENT.player.distanceTo(livingEntity) > measure(livingEntity)) {
            return false;
         } else if (!proverkiDoUdara.isEnabled("Бить через блоки") && !CLIENT.player.canSee(livingEntity)) {
            return false;
         } else if (!tseli.isEnabled("NPC") && this.check4(livingEntity)) {
            return false;
         } else if (livingEntity instanceof PlayerEntity playerEntity2) {
            if (!playerEntity2.isCreative() && !playerEntity2.isSpectator()) {
               boolean flag4 = FriendCommand.check(playerEntity2.getName().getString());
               if (flag4 && !tseli.isEnabled("Друзья")) {
                  return false;
               } else if (!flag4 && !tseli.isEnabled("Игроки")) {
                  return false;
               } else if (AntiBot.check7(playerEntity2)) {
                  return false;
               } else {
                  boolean flag5 = !this.check3(playerEntity2);
                  boolean flag6 = playerEntity2.isInvisible();
                  if (flag6) {
                     return flag5 ? tseli.isEnabled("Голые невидимки") : tseli.isEnabled("Невидимки");
                  } else {
                     return !flag5 || tseli.isEnabled("Голые");
                  }
               }
            } else {
               return false;
            }
         } else {
            boolean flag7 = livingEntity instanceof Monster || livingEntity instanceof SlimeEntity;
            boolean flag8 = livingEntity instanceof VillagerEntity || livingEntity instanceof MerchantEntity;
            boolean flag9 = livingEntity instanceof AnimalEntity
               || livingEntity instanceof VillagerEntity
               || livingEntity instanceof WaterCreatureEntity
               || livingEntity instanceof AmbientEntity;
            if (flag7 && tseli.isEnabled("Мобы")) {
               return true;
            } else {
               return flag8 && tseli.isEnabled("Жители") ? true : flag9 && tseli.isEnabled("Животные");
            }
         }
      } else {
         return false;
      }
   }

   private boolean check3(PlayerEntity playerEntity) {
      return !playerEntity.getEquippedStack(EquipmentSlot.HEAD).isEmpty()
         || !playerEntity.getEquippedStack(EquipmentSlot.CHEST).isEmpty()
         || !playerEntity.getEquippedStack(EquipmentSlot.LEGS).isEmpty()
         || !playerEntity.getEquippedStack(EquipmentSlot.FEET).isEmpty();
   }

   private boolean check4(LivingEntity livingEntity) {
      String text = this.resolve2(livingEntity.getName().getString());
      String text2 = this.resolve2(livingEntity.getDisplayName().getString());
      String text3 = livingEntity.getCustomName() == null ? "" : this.resolve2(livingEntity.getCustomName().getString());
      String text4 = "";
      String text5 = "";
      if (livingEntity.getScoreboardTeam() != null) {
         text4 = this.resolve2(livingEntity.getScoreboardTeam().getPrefix().getString());
         text5 = this.resolve2(livingEntity.getScoreboardTeam().getSuffix().getString());
      }

      if (this.check5(text) || this.check5(text2) || this.check5(text3) || this.check5(text4) || this.check5(text5)) {
         return true;
      } else if (!(livingEntity instanceof PlayerEntity playerEntity3)) {
         return false;
      } else {
         boolean flag10 = CLIENT.getNetworkHandler() != null && CLIENT.getNetworkHandler().getPlayerListEntry(playerEntity3.getUuid()) == null;
         boolean flag11 = text.matches("\\d{1,8}") || text.startsWith("cit-");
         if (!flag10) {
            if (flag11) {
               if (!text2.equals(text) || !text4.isEmpty()) {
                  return true;
               }

               if (!text5.isEmpty()) {
                  return true;
               }
            }

            return false;
         } else {
            return true;
         }
      }
   }

   private boolean check5(String string) {
      return string.contains("npc") || string.contains("znpc") || string.contains("нпс") || string.contains("наставник");
   }

   private String resolve2(String string) {
      return string == null ? "" : string.replaceAll("(?i)§.", "").replaceAll("(?i)&.", "").replaceAll("\\p{Cntrl}", "").trim().toLowerCase(Locale.ROOT);
   }
}
