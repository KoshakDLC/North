package ru.metaculture.protection;

import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.util.hit.HitResult.Type;
import org.lwjgl.glfw.GLFW;
import org.wild.mixin.acceser.MinecraftClientAccessor;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "TapeMouse",
   description = "Кто ваще это юзает ?-?",
   category = Category.Misc
)
public class TapeMouse extends Module {
   private final ModeSetting knopka = new ModeSetting("Кнопка", "ЛКМ", "ЛКМ", "ПКМ", "Обе");
   private final ModeSetting rezhimUdarov = new ModeSetting("Режим ударов", "По кулдауну", "По кулдауну", "По задержке", "CPS");
   private final NumberSetting zaderzhka = new NumberSetting("Задержка", 1000.0F, 100.0F, 5000.0F, 100.0F, false)
      .setVisibilityCondition(() -> !this.rezhimUdarov.is("По задержке"));
   private final NumberSetting cpsMinimum = new NumberSetting("CPS минимум", 8.0F, 1.0F, 20.0F, 1.0F, false)
      .setVisibilityCondition(() -> !this.rezhimUdarov.is("CPS"));
   private final NumberSetting cpsMaksimum = new NumberSetting("CPS максимум", 12.0F, 1.0F, 20.0F, 1.0F, false)
      .setVisibilityCondition(() -> !this.rezhimUdarov.is("CPS"));
   private final BooleanSetting proverkaNaEntiti = new BooleanSetting("Проверка на энтити", false);
   private final BooleanSetting tolkoPriZazhatii = new BooleanSetting("Только при зажатии", false);
   private final Stopwatch stopwatch = new Stopwatch();
   private final Stopwatch stopwatch2 = new Stopwatch();
   private long timestamp;
   private long timestamp2;

   public TapeMouse() {
      this.addSettings(
         new Setting[]{
            this.knopka, this.rezhimUdarov, this.zaderzhka, this.cpsMinimum, this.cpsMaksimum, this.proverkaNaEntiti, this.tolkoPriZazhatii
         }
      );
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.invoke3();
   }

   @Override
   public void toggle() {
      super.toggle();
      this.invoke3();
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.interactionManager != null && CLIENT.currentScreen == null) {
         if (this.knopka.is("ЛКМ") || this.knopka.is("Обе")) {
            this.invoke(true);
         }

         if (this.knopka.is("ПКМ") || this.knopka.is("Обе")) {
            this.invoke(false);
         }
      }
   }

   private void invoke(boolean bl) {
      if (!bl || !this.proverkaNaEntiti.isEnabled() || this.check2()) {
         if (!this.tolkoPriZazhatii.isEnabled() || this.check3(bl ? 0 : 1)) {
            Stopwatch stopwatch = bl ? this.stopwatch : this.stopwatch2;
            if (this.rezhimUdarov.is("По кулдауну")) {
               if (this.check(bl)) {
                  this.invoke2(bl);
               }
            } else if (this.rezhimUdarov.is("По задержке")) {
               if (stopwatch.check((double)this.zaderzhka.getValue())) {
                  this.invoke2(bl);
                  stopwatch.invoke();
               }
            } else {
               long longValue = bl ? this.timestamp : this.timestamp2;
               if (stopwatch.check((double)longValue)) {
                  this.invoke2(bl);
                  stopwatch.invoke();
                  long longValue2 = this.compute();
                  if (bl) {
                     this.timestamp = longValue2;
                  } else {
                     this.timestamp2 = longValue2;
                  }
               }
            }
         }
      }
   }

   private boolean check(boolean bl) {
      return bl ? CLIENT.player.getAttackCooldownProgress(0.0F) >= 1.0F : ((MinecraftClientAccessor)CLIENT).getItemUseCooldown() <= 0;
   }

   private void invoke2(boolean bl) {
      MinecraftClientAccessor minecraftClientAccessor = (MinecraftClientAccessor)CLIENT;
      if (bl) {
         minecraftClientAccessor.invokeDoAttack();
      } else {
         minecraftClientAccessor.invokeDoItemUse();
         if (this.rezhimUdarov.is("По кулдауну")) {
            minecraftClientAccessor.setItemUseCooldown(4);
         }
      }
   }

   private boolean check2() {
      return CLIENT.crosshairTarget != null && CLIENT.crosshairTarget.getType() == Type.ENTITY;
   }

   private boolean check3(int i) {
      return CLIENT.getWindow() == null ? false : GLFW.glfwGetMouseButton(CLIENT.getWindow().getHandle(), i) == 1;
   }

   private long compute() {
      float floatValue = Math.min(this.cpsMinimum.getValue(), this.cpsMaksimum.getValue());
      float floatValue2 = Math.max(this.cpsMinimum.getValue(), this.cpsMaksimum.getValue());
      double doubleValue = floatValue >= floatValue2 ? floatValue : floatValue + ThreadLocalRandom.current().nextDouble() * (floatValue2 - floatValue);
      if (doubleValue < 0.1) {
         doubleValue = 0.1;
      }

      return (long)(1000.0 / doubleValue);
   }

   private void invoke3() {
      this.stopwatch.invoke();
      this.stopwatch2.invoke();
      this.timestamp = this.compute();
      this.timestamp2 = this.compute();
   }
}
