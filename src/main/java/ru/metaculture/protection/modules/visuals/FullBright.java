package ru.metaculture.protection;

import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LightType;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "FullBright",
   description = "ПРОЗРЕВШИЙ",
   category = Category.Visuals
)
public class FullBright extends Module {
   private static final String GAMMA = "Гамма";
   private static final String EFFEKT = "Эффект";
   private static final String DINAMICHESKIY = "Динамический";
   private static final String ADAPTIVNYY = "Адаптивный";
   private static final String FAKEL = "Факел";
   public ModeSetting tip = new ModeSetting("Тип", "Гамма", "Гамма", "Эффект", "Динамический", "Адаптивный", "Факел");
   public NumberSetting porog = new NumberSetting("Порог", 0.53F, 0.5F, 0.6F, 0.01F, true).setVisibilityCondition(() -> !this.check3());
   public NumberSetting krivaya = new NumberSetting("Кривая", 1.4F, 0.5F, 3.0F, 0.1F, false).setVisibilityCondition(() -> !this.check3());
   public NumberSetting radius = new NumberSetting("Радиус", 10.0F, 5.0F, 20.0F, 1.0F, false).setVisibilityCondition(() -> !this.check4());
   public static volatile boolean flag = false;
   public static volatile float floatValue = 10.0F;
   public static volatile double doubleValue = 0.0;
   public static volatile double doubleValue2 = 0.0;
   public static volatile double doubleValue3 = 0.0;
   private int intValue = Integer.MIN_VALUE;
   private int intValue2 = Integer.MIN_VALUE;
   private int intValue3 = Integer.MIN_VALUE;
   private boolean flag2 = false;
   private int intValue4;
   private int intValue5;
   private int intValue6;
   private int intValue7;
   private int intValue8;
   private int intValue9;

   public FullBright() {
      this.addSettings(new Setting[]{this.tip, this.porog, this.krivaya, this.radius});
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.intValue = Integer.MIN_VALUE;
      this.intValue2 = Integer.MIN_VALUE;
      this.intValue3 = Integer.MIN_VALUE;
      if (CLIENT.worldRenderer != null && this.check6()) {
         CLIENT.worldRenderer.reload();
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.invoke2();
      if (CLIENT.worldRenderer != null && this.check6()) {
         CLIENT.worldRenderer.reload();
      }

      if (CLIENT.player != null) {
         CLIENT.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
      }
   }

   @EventHandler
   public void onClientTick(ClientTickEvent clientTickEvent) {
      if (CLIENT.player != null) {
         if (this.check5()) {
            CLIENT.player.removeStatusEffect(StatusEffects.NIGHT_VISION);
         }

         if (this.tip.is("Эффект")) {
            CLIENT.player.addStatusEffect(new StatusEffectInstance(StatusEffects.NIGHT_VISION, 300, 0, false, false));
         }

         this.invoke();
      }
   }

   private void invoke() {
      if (this.check4() && CLIENT.world != null && CLIENT.worldRenderer != null) {
         floatValue = this.radius.getValue();
         doubleValue = CLIENT.player.getX();
         doubleValue2 = CLIENT.player.getEyeY();
         doubleValue3 = CLIENT.player.getZ();
         flag = true;
         int intValue = MathHelper.floor(doubleValue);
         int intValue2 = MathHelper.floor(CLIENT.player.getY());
         int intValue3 = MathHelper.floor(doubleValue3);
         if (intValue != this.intValue || intValue2 != this.intValue2 || intValue3 != this.intValue3) {
            if (this.flag2) {
               CLIENT.worldRenderer
                  .scheduleBlockRenders(
                     this.intValue4, this.intValue5, this.intValue6, this.intValue7, this.intValue8, this.intValue9
                  );
            }

            int intValue4 = MathHelper.ceil(this.radius.getValue()) + 1;
            this.intValue4 = intValue - intValue4;
            this.intValue5 = intValue2 - intValue4;
            this.intValue6 = intValue3 - intValue4;
            this.intValue7 = intValue + intValue4;
            this.intValue8 = intValue2 + intValue4;
            this.intValue9 = intValue3 + intValue4;
            this.flag2 = true;
            CLIENT.worldRenderer
               .scheduleBlockRenders(this.intValue4, this.intValue5, this.intValue6, this.intValue7, this.intValue8, this.intValue9);
            this.intValue = intValue;
            this.intValue2 = intValue2;
            this.intValue3 = intValue3;
         }
      } else {
         this.invoke2();
      }
   }

   private void invoke2() {
      if (flag || this.flag2) {
         flag = false;
         if (this.flag2 && CLIENT.worldRenderer != null) {
            CLIENT.worldRenderer
               .scheduleBlockRenders(this.intValue4, this.intValue5, this.intValue6, this.intValue7, this.intValue8, this.intValue9);
         }

         this.flag2 = false;
         this.intValue = Integer.MIN_VALUE;
         this.intValue2 = Integer.MIN_VALUE;
         this.intValue3 = Integer.MIN_VALUE;
      }
   }

   public static int compute(int i, int j, int k) {
      return compute2(i + 0.5, j + 0.5, k + 0.5);
   }

   public static int compute2(double d, double e, double f) {
      if (!flag) {
         return 0;
      } else {
         float lightRadius = floatValue;
         if (lightRadius <= 0.0F) {
            return 0;
         } else {
            double offsetX = d - doubleValue;
            double offsetY = e - doubleValue2;
            double offsetZ = f - doubleValue3;
            double distance = Math.sqrt(offsetX * offsetX + offsetY * offsetY + offsetZ * offsetZ);
            if (distance >= lightRadius) {
               return 0;
            } else {
               int intValue5 = Math.round(15.0F * (float)(1.0 - distance / lightRadius));
               return intValue5 < 1 ? 0 : Math.min(intValue5, 15);
            }
         }
      }
   }

   public boolean check() {
      return this.tip.is("Гамма");
   }

   public boolean check2() {
      return this.tip.is("Динамический");
   }

   public boolean check3() {
      return this.tip.is("Адаптивный");
   }

   public boolean check4() {
      return this.tip.is("Факел");
   }

   public float measure() {
      return measure5(this.porog.getValue(), 0.0F, 1.0F);
   }

   public float measure2() {
      return measure5(this.krivaya.getValue(), 0.5F, 3.0F);
   }

   public float measure3() {
      if (CLIENT.player != null && CLIENT.world != null) {
         BlockPos blockPos = CLIENT.player.getBlockPos();
         float floatValue2 = CLIENT.world.getLightLevel(LightType.BLOCK, blockPos) / 15.0F;
         float floatValue3 = CLIENT.world.getLightLevel(LightType.SKY, blockPos) / 15.0F;
         float floatValue4 = measure4(CLIENT.world.getTimeOfDay() % 24000L);
         float floatValue5 = Math.max(floatValue2, floatValue3 * (1.0F - floatValue4 * 0.7F));
         float floatValue6 = measure5(Math.max(1.0F - floatValue5, floatValue4 * 0.65F), 0.0F, 1.0F);
         float floatValue7 = (float)Math.sin(System.currentTimeMillis() * 0.0018) * 0.035F;
         return 2.0F + measure5(floatValue6 + floatValue7, 0.0F, 1.0F) * 198.0F;
      } else {
         return 80.0F;
      }
   }

   private boolean check5() {
      return this.tip.is("Гамма") || this.tip.is("Динамический") || this.tip.is("Адаптивный");
   }

   private boolean check6() {
      return this.tip.is("Гамма") || this.tip.is("Динамический");
   }

   private static float measure4(long l) {
      if (l < 12000L) {
         return 0.0F;
      } else if (l < 14000L) {
         return (float)(l - 12000L) / 2000.0F;
      } else {
         return l < 22000L ? 1.0F : 1.0F - (float)(l - 22000L) / 2000.0F;
      }
   }

   private static float measure5(float f, float g, float h) {
      return Math.max(g, Math.min(h, f));
   }
}
