package ru.metaculture.protection;

import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "Jesus",
   category = Category.Movement,
   description = "Иисус нахуй"
)
public class Jesus extends Module {
   private final ModeSetting rezhim = new ModeSetting("Режим", "Авто", "Авто", "Простой");
   private final NumberSetting skorost = new NumberSetting("Скорость", 0.2F, 0.2F, 1.05F, 0.01F, false)
      .setVisibilityCondition(() -> !this.rezhim.is("Простой"));
   private final KeybindSetting knopkaBusta = new KeybindSetting("Кнопка буста", -1);
   private long timestamp = 0L;
   private boolean flag = false;
   private boolean flag2 = false;
   private final float floatValue = 0.47F;
   private final float floatValue2 = 0.43F;

   public Jesus() {
      this.addSettings(new Setting[]{this.rezhim, this.skorost, this.knopkaBusta});
   }

   @EventHandler
   public void onRawInput(RawInputEvent rawInputEvent) {
      if (CLIENT.currentScreen == null && rawInputEvent.getAction() == 1) {
         if (rawInputEvent.getKeyCode() == this.knopkaBusta.getKeyCode()) {
            this.flag2 = true;
         }
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null) {
         if (this.flag2) {
            this.flag = true;
            this.timestamp = System.currentTimeMillis() + 2000L;
            this.flag2 = false;
         }

         if (this.flag && System.currentTimeMillis() > this.timestamp) {
            this.flag = false;
         }

         if (CLIENT.player.isTouchingWater() || CLIENT.player.isInLava()) {
            StatusEffectInstance statusEffectInstance3 = CLIENT.player.getStatusEffect(StatusEffects.SPEED);
            StatusEffectInstance statusEffectInstance4 = CLIENT.player.getStatusEffect(StatusEffects.SLOWNESS);
            ItemStack itemStack5 = CLIENT.player.getOffHandStack();
            String text = itemStack5.getName().getString();
            ItemStack itemStack6 = CLIENT.player.getEquippedStack(EquipmentSlot.HEAD);
            ItemStack itemStack7 = CLIENT.player.getEquippedStack(EquipmentSlot.CHEST);
            ItemStack itemStack8 = CLIENT.player.getEquippedStack(EquipmentSlot.LEGS);
            ItemStack itemStack9 = CLIENT.player.getEquippedStack(EquipmentSlot.FEET);
            String text2 = itemStack6.getName().getString();
            String text3 = itemStack7.getName().getString();
            String text4 = itemStack8.getName().getString();
            String text5 = itemStack9.getName().getString();
            float floatValue = this.measure(statusEffectInstance3, statusEffectInstance4, text);
            floatValue = this.measure2(floatValue, itemStack6, text2, itemStack7, text3, itemStack8, text4, itemStack9, text5);
            if (this.flag) {
               floatValue *= 1.89F;
            }

            MovementUtils.invoke5(floatValue);
            boolean flag = CLIENT.options.forwardKey.isPressed()
               || CLIENT.options.backKey.isPressed()
               || CLIENT.options.leftKey.isPressed()
               || CLIENT.options.rightKey.isPressed();
            if (!flag) {
               CLIENT.player.setVelocity(0.0, CLIENT.player.getVelocity().y, 0.0);
            }

            double doubleValue = CLIENT.options.jumpKey.isPressed() ? 0.019 : 0.003;
            CLIENT.player.setVelocity(CLIENT.player.getVelocity().x, doubleValue, CLIENT.player.getVelocity().z);
         }
      }
   }

   private float measure(StatusEffectInstance statusEffectInstance, StatusEffectInstance statusEffectInstance2, String string) {
      float floatValue2 = 0.0F;
      if (this.rezhim.is("Авто")) {
         if (statusEffectInstance != null) {
            if (statusEffectInstance.getAmplifier() == 2) {
               floatValue2 = this.check(string) ? 0.58515F : 0.53535F;
            } else if (statusEffectInstance.getAmplifier() == 1) {
               floatValue2 = this.check(string) ? 0.47F : 0.43F;
            }
         } else {
            floatValue2 = this.check(string) ? 0.3243F : 0.2967F;
         }
      } else if (this.rezhim.is("Простой")) {
         floatValue2 = this.skorost.getValue();
      }

      if (statusEffectInstance2 != null) {
         floatValue2 *= 0.85F;
      }

      return floatValue2;
   }

   private boolean check(String string) {
      return string.contains("Шар Геракла 2")
         || string.contains("Шар CHAMPION")
         || string.contains("Шар Аида 2")
         || string.contains("Шар GOD")
         || string.contains("КУБИК-РУБИК");
   }

   private float measure2(
      float f,
      ItemStack itemStack,
      String string,
      ItemStack itemStack2,
      String string2,
      ItemStack itemStack3,
      String string3,
      ItemStack itemStack4,
      String string4
   ) {
      if (itemStack4.getItem() == Items.GOLDEN_BOOTS && string4.contains("Тапочки админа SoveryBRIZ")) {
         f *= 1.01F;
      }

      if (itemStack3.getItem() == Items.GOLDEN_LEGGINGS && string3.contains("Штаны админа stqffy")) {
         f *= 1.02F;
      }

      if (itemStack.getItem() == Items.GOLDEN_HELMET && string.contains("Шляпа админа Vester")) {
         f *= 1.05F;
      }

      if (itemStack2.getItem() == Items.GOLDEN_CHESTPLATE && string2.contains("Грудак админа lxckscream")) {
         f *= 1.03F;
      }

      if (itemStack.getItem() == Items.PLAYER_HEAD && string.contains("Новогодний Подарок")) {
         f *= 0.75F;
      }

      return f;
   }
}
