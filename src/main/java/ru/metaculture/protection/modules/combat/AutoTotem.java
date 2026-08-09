package ru.metaculture.protection;

import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.PlayerHeadItem;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.math.Box;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AutoTotem",
   description = "Автоматически берет тотем в левую руку",
   category = Category.Combat,
   riskLevels = {ModuleRiskLevel.GRIM}
)
public class AutoTotem extends Module {
   private static final String SOHRYANYAT_TALISMANY = "Сохрянять талисманы";
   private static final String NE_SVAPAT_ESLI_VKD = "Не свапать если в КД";
   private final GroupSetting nastroyki = new GroupSetting(
      "Настройки",
      new BooleanSetting("Здоровье с элитрами", true),
      new BooleanSetting("Динамит", true),
      new BooleanSetting("Падение", false),
      new BooleanSetting("Эндер-кристалл", false),
      new BooleanSetting("Не свапать если в КД", false),
      new BooleanSetting("Сохрянять талисманы", true)
   );
   private final NumberSetting zdorove = new NumberSetting("Здоровье", 4.0F, 1.0F, 20.0F, 0.5F, false);
   private final NumberSetting zdoroveNaElitre = new NumberSetting("Здоровье на элитре", 9.0F, 0.0F, 20.0F, 0.5F, false)
      .setVisibilityCondition(() -> !this.nastroyki.isEnabled("Здоровье с элитрами"));
   private final NumberSetting distantsiyaDoKristalla = new NumberSetting("Дистанция до кристалла", 4.0F, 1.0F, 10.0F, 1.0F, false)
      .setVisibilityCondition(() -> !this.nastroyki.isEnabled("Эндер-кристалл"));
   private final NumberSetting distantsiyaDoDinamita = new NumberSetting("Дистанция до динамита", 30.0F, 3.0F, 50.0F, 1.0F, false)
      .setVisibilityCondition(() -> !this.nastroyki.isEnabled("Динамит"));
   private final BooleanSetting neSvapatEsliShar = new BooleanSetting("Не свапать если шар", false);
   private int intValue = -1;
   private boolean flag = false;
   private AutoTotem.AutoTotemState autoTotemState = AutoTotem.AutoTotemState.IDLE;
   private final DualTimer dualTimer = new DualTimer();
   private int intValue2 = -1;
   private boolean flag2 = false;
   public static boolean flag3 = false;

   public AutoTotem() {
      this.addSettings(new Setting[]{this.nastroyki, this.zdorove, this.zdoroveNaElitre, this.distantsiyaDoKristalla, this.distantsiyaDoDinamita, this.neSvapatEsliShar});
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player == null || !CLIENT.player.isAlive() || CLIENT.world == null) {
         this.invoke5();
      } else if (this.autoTotemState != AutoTotem.AutoTotemState.IDLE) {
         Sprint.intValue = 2;
         CLIENT.options.sprintKey.setPressed(false);
         CLIENT.player.setSprinting(false);
         this.invoke4(false);
         this.invoke();
      } else {
         this.invoke2();
      }
   }

   private void invoke() {
      switch (this.autoTotemState) {
         case PREPARE:
            if (this.dualTimer.check(20L)) {
               this.dualTimer.invoke();
               this.autoTotemState = AutoTotem.AutoTotemState.SWAP;
            }
            break;
         case SWAP:
            if (!CLIENT.player.isSprinting()) {
               CLIENT.interactionManager
                  .clickSlot(CLIENT.player.playerScreenHandler.syncId, this.intValue2, 40, SlotActionType.SWAP, CLIENT.player);
            }

            CLIENT.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(CLIENT.player.playerScreenHandler.syncId));
            if (this.dualTimer.check(30L)) {
               this.dualTimer.invoke();
               this.autoTotemState = this.flag2 ? AutoTotem.AutoTotemState.RESTORE : AutoTotem.AutoTotemState.COOLDOWN;
            }
            break;
         case RESTORE:
            if (this.dualTimer.check(30L)) {
               this.dualTimer.invoke();
               this.invoke6();
               this.autoTotemState = AutoTotem.AutoTotemState.COOLDOWN;
            }
            break;
         case COOLDOWN:
            if (this.dualTimer.check(40L)) {
               this.invoke4(true);
               this.autoTotemState = AutoTotem.AutoTotemState.IDLE;
               flag3 = false;
            }
      }
   }

   private void invoke2() {
      boolean flag = this.check6();
      ItemStack itemStack2 = CLIENT.player.getOffHandStack();
      boolean flag2 = this.check2(itemStack2);
      boolean flag3 = this.nastroyki.isEnabled("Не свапать если в КД");
      if (flag3 && flag2 && this.check4(itemStack2)) {
         if (this.intValue != -1 && this.flag) {
            this.intValue2 = this.intValue;
            this.flag2 = true;
            this.invoke3();
         } else {
            this.invoke6();
         }
      } else {
         boolean flag4 = flag && this.nastroyki.isEnabled("Сохрянять талисманы") && this.check5(itemStack2);
         if (flag && (!flag2 || flag4)) {
            int intValue = flag4 ? this.compute2() : this.compute();
            if (intValue >= 0) {
               if (!this.flag) {
                  this.intValue = intValue;
                  this.flag = true;
               }

               this.intValue2 = intValue;
               this.flag2 = false;
               this.invoke3();
            }
         } else if (!flag && this.intValue != -1 && this.flag) {
            if (CLIENT.player.getOffHandStack().isOf(Items.TOTEM_OF_UNDYING)) {
               this.intValue2 = this.intValue;
               this.flag2 = true;
               this.invoke3();
            } else {
               this.invoke6();
            }
         }
      }
   }

   private void invoke3() {
      this.dualTimer.invoke();
      this.autoTotemState = AutoTotem.AutoTotemState.PREPARE;
      flag3 = true;
   }

   private void invoke4(boolean bl) {
      if (CLIENT.getWindow() != null) {
         KeyBinding[] keyBindings = new KeyBinding[]{
            CLIENT.options.forwardKey, CLIENT.options.backKey, CLIENT.options.leftKey, CLIENT.options.rightKey, CLIENT.options.jumpKey
         };
         long longValue = CLIENT.getWindow().getHandle();

         for (KeyBinding keyBinding : keyBindings) {
            boolean flag5 = bl && InputUtil.isKeyPressed(longValue, keyBinding.getDefaultKey().getCode());
            keyBinding.setPressed(flag5);
         }
      }
   }

   private void invoke5() {
      this.invoke4(true);
      this.autoTotemState = AutoTotem.AutoTotemState.IDLE;
      this.dualTimer.invoke();
      this.invoke6();
      flag3 = false;
   }

   private void invoke6() {
      this.intValue = -1;
      this.flag = false;
      this.flag2 = false;
   }

   private int compute() {
      int intValue2 = this.compute2();
      if (intValue2 >= 0) {
         return intValue2;
      } else {
         for (int intValue3 = 0; intValue3 < 36; intValue3++) {
            ItemStack itemStack3 = CLIENT.player.getInventory().getStack(intValue3);
            if (this.check3(itemStack3)) {
               return intValue3 < 9 ? intValue3 + 36 : intValue3;
            }
         }

         return -1;
      }
   }

   private int compute2() {
      for (int intValue4 = 0; intValue4 < 36; intValue4++) {
         ItemStack itemStack4 = CLIENT.player.getInventory().getStack(intValue4);
         if (this.check3(itemStack4) && !this.check5(itemStack4)) {
            return intValue4 < 9 ? intValue4 + 36 : intValue4;
         }
      }

      return -1;
   }

   public boolean check() {
      ItemStack itemStack5 = CLIENT.player.getOffHandStack();
      return this.check2(itemStack5);
   }

   private boolean check2(ItemStack itemStack) {
      return itemStack != null && itemStack.isOf(Items.TOTEM_OF_UNDYING);
   }

   private boolean check3(ItemStack itemStack) {
      return this.check2(itemStack) && (!this.nastroyki.isEnabled("Не свапать если в КД") || !this.check4(itemStack));
   }

   private boolean check4(ItemStack itemStack) {
      return CLIENT.player != null && itemStack != null && !itemStack.isEmpty() && CLIENT.player.getItemCooldownManager().isCoolingDown(itemStack);
   }

   private boolean check5(ItemStack itemStack) {
      if (this.check2(itemStack)) {
         if (itemStack.hasEnchantments()) {
            return true;
         }

         if (itemStack.hasGlint()) {
            return true;
         }
      }

      return false;
   }

   private boolean check6() {
      return this.check7()
         || this.check9()
         || this.check10()
         || this.check8()
         || CLIENT.player.getHealth() + CLIENT.player.getAbsorptionAmount() <= this.zdorove.getValue();
   }

   private boolean check7() {
      ItemStack itemStack6 = CLIENT.player.getEquippedStack(EquipmentSlot.CHEST);
      return itemStack6.getItem() == Items.ELYTRA
         && this.nastroyki.isEnabled("Здоровье с элитрами")
         && CLIENT.player.getHealth() + CLIENT.player.getAbsorptionAmount() <= this.zdoroveNaElitre.getValue();
   }

   private boolean check8() {
      return this.nastroyki.isEnabled("Падение") && CLIENT.player.fallDistance > 12.0;
   }

   private boolean check9() {
      if (!this.nastroyki.isEnabled("Эндер-кристалл")) {
         return false;
      } else {
         double doubleValue = this.distantsiyaDoKristalla.getValue() * this.distantsiyaDoKristalla.getValue();
         Box box = CLIENT.player.getBoundingBox().expand(this.distantsiyaDoKristalla.getValue());
         boolean flag6 = !CLIENT.world
            .getEntitiesByClass(EndCrystalEntity.class, box, endCrystalEntity -> endCrystalEntity.squaredDistanceTo(CLIENT.player) <= doubleValue)
            .isEmpty();
         return flag6 && (!(CLIENT.player.getOffHandStack().getItem() instanceof PlayerHeadItem) || !this.neSvapatEsliShar.isEnabled());
      }
   }

   private boolean check10() {
      if (!this.nastroyki.isEnabled("Динамит")) {
         return false;
      } else {
         double doubleValue2 = this.distantsiyaDoDinamita.getValue() * this.distantsiyaDoDinamita.getValue();
         Box box2 = CLIENT.player.getBoundingBox().expand(this.distantsiyaDoDinamita.getValue());
         return !CLIENT.world
            .getEntitiesByClass(
               Entity.class,
               box2,
               entity -> (entity instanceof TntEntity || entity instanceof TntMinecartEntity) && entity.squaredDistanceTo(CLIENT.player) <= doubleValue2
            )
            .isEmpty();
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.invoke5();
   }

   static enum AutoTotemState {
      IDLE,
      PREPARE,
      SWAP,
      RESTORE,
      COOLDOWN;
   }
}
