package ru.metaculture.protection;

import com.google.gson.JsonObject;
import java.awt.Color;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.Camera;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.Vec3d;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "Stardust",
   description = "Цветное звездное небо и локальные светящиеся звезды",
   category = Category.Visuals,
   riskLevels = {ModuleRiskLevel.NEW}
)
public final class Stardust extends Module {
   private static final String KASTOMNYE_OBLAKA = "Кастомные облака";
   private static final String TSVET_OBLAKOV = "Цвет облаков";
   public static final ColorSetting TSVET_NEBA = new ColorSetting("Цвет неба", 72.0F, 0.72F, 1.0F);
   public static final ModeSetting SHEYDER_NEBA = new ModeSetting("Шейдер неба", StardustSkyPreset.AURORA.getText(), StardustSkyPreset.resolve());
   public static final NumberSetting PLOTNOST_ZVYOZD = new NumberSetting("Плотность звёзд", 1880.0F, 220.0F, 3600.0F, 20.0F, false);
   public static final NumberSetting YARKOST = new NumberSetting("Яркость", 1.55F, 0.2F, 2.75F, 0.05F, false);
   public static final ModeSetting VREMYA_SUTOK = new ModeSetting("Время суток", "Ночь", "День", "Закат", "Рассвет", "Ночь", "Полночь", "Полдень");
   public static final GroupSetting NASTROYKI_OBLAKOV = new GroupSetting(
      "Настройки облаков", new BooleanSetting("Кастомные облака", false), new BooleanSetting("Цвет облаков", false)
   );
   public static final ColorSetting TSVET_OBLAKOV_2 = new ColorSetting("Цвет облаков", 0.0F, 0.0F, 1.0F, 1.0F).setVisibilityCondition(() -> !check2());
   public static final NumberSetting SILA_TSVETA_OBLAKOV = new NumberSetting("Сила цвета облаков", 1.0F, 0.0F, 1.0F, 0.05F, true).setVisibilityCondition(() -> !check2());
   public static long timestamp = -1L;
   private static volatile boolean flag;
   private static final float FLOAT_VALUE = (float) (Math.PI * 2);
   private static final float FLOAT_VALUE_2 = 16.0F;
   private static final float FLOAT_VALUE_3 = 22.0F;
   private static final float FLOAT_VALUE_4 = 0.74F;
   private int intValue;
   private int intValue2;
   private int intValue3;
   private int intValue4 = Integer.MIN_VALUE;
   private int intValue5 = Integer.MIN_VALUE;
   private int intValue6 = Integer.MIN_VALUE;
   private static volatile int intValue7 = 7175679;
   private static volatile int intValue8 = 5435580;

   public Stardust() {
      StardustShaderRegistry.invoke();
      StardustSkyRenderer.invoke();
      this.addSettings(new Setting[]{TSVET_NEBA, SHEYDER_NEBA, PLOTNOST_ZVYOZD, YARKOST, VREMYA_SUTOK, NASTROYKI_OBLAKOV, TSVET_OBLAKOV_2, SILA_TSVETA_OBLAKOV});
   }

   @Override
   public void onEnable() {
      this.intValue = 0;
      this.intValue2 = 0;
      this.intValue3 = 8;
      this.intValue4 = Integer.MIN_VALUE;
      this.intValue5 = Integer.MIN_VALUE;
      this.intValue6 = Integer.MIN_VALUE;
      flag = true;
      invoke3();
      FloatingStardustParticle.invoke5();
      ShootingStarParticle.invoke4();
      super.onEnable();
   }

   @Override
   public void onDisable() {
      flag = false;
      timestamp = -1L;
      FloatingStardustParticle.invoke5();
      ShootingStarParticle.invoke4();
      super.onDisable();
   }

   @Override
   public void loadFromJson(JsonObject jsonObject) {
      invoke5(jsonObject);
      super.loadFromJson(jsonObject);
   }

   @EventHandler
   public void onClientTick(ClientTickEvent clientTickEvent) {
      MinecraftClient client = clientTickEvent.getClient();
      if (client != null && client.world != null && client.player != null && client.gameRenderer != null) {
         invoke3();
         invoke4();
         int intValue = Math.max(0, Math.round(PLOTNOST_ZVYOZD.getValue()));
         int intValue2 = intValue7;
         int intValue3 = resolve().compute();
         if (intValue != this.intValue4 || intValue2 != this.intValue5 || intValue3 != this.intValue6) {
            this.intValue = 0;
            this.intValue4 = intValue;
            this.intValue5 = intValue2;
            this.intValue6 = intValue3;
            FloatingStardustParticle.invoke5();
            ShootingStarParticle.invoke4();
         }

         Camera camera = client.gameRenderer.getCamera();
         if (camera != null) {
            Vec3d vec3d2 = camera.getPos();
            ClientWorld clientWorld2 = client.world;
            int intValue4 = FloatingStardustParticle.getIntValue();
            int intValue5 = intValue - intValue4;
            if (intValue5 > 0) {
               int intValue6 = Math.min(intValue5, intValue4 == 0 ? Math.min(intValue, 760) : 188);

               for (int intValue7 = 0; intValue7 < intValue6; intValue7++) {
                  this.invoke(clientWorld2, vec3d2, 16.0F, 22.0F);
               }
            }

            int intValue8 = ShootingStarParticle.getIntValue();
            if (intValue8 < Math.max(2, Math.round(YARKOST.getValue() * 2.6F))) {
               this.intValue3--;
               if (this.intValue3 <= 0) {
                  this.invoke2(clientWorld2, vec3d2);
                  float floatValue = measure9((this.intValue2 + 1) * 0.618034F + 0.491F);
                  this.intValue3 = Math.max(14, 64 - Math.round(YARKOST.getValue() * 14.0F) + (int)(floatValue * 46.0F));
               }
            }
         }
      }
   }

   private void invoke(ClientWorld clientWorld, Vec3d vec3d, float f, float g) {
      float floatValue2 = measure9((this.intValue + 1) * 0.618034F + 0.173F);
      float floatValue3 = measure9((this.intValue + 1) * 0.7548777F + 0.419F);
      float floatValue4 = measure9((this.intValue + 1) * 0.5698403F + 0.271F);
      float floatValue5 = measure9((this.intValue + 1) * 0.4386875F + 0.617F);
      float floatValue6 = measure9((this.intValue + 1) * 0.3271949F + 0.383F);
      float floatValue7 = measure9((this.intValue + 1) * 0.27917233F + 0.719F);
      float floatValue8 = measure9((this.intValue + 1) * 0.21132487F + 0.127F);
      float floatValue9 = floatValue2 * (float) (Math.PI * 2) + (floatValue6 - 0.5F) * 0.42F;
      float floatValue10 = (float)Math.sqrt(floatValue5);
      float floatValue11 = 1.4F + floatValue10 * Math.max(1.0F, g - 1.4F);
      float floatValue12;
      if (floatValue7 < 0.12F) {
         floatValue12 = 0.55F + floatValue3 * 3.15F;
         floatValue11 = 2.2F + floatValue10 * Math.max(1.0F, g - 2.2F);
      } else if (floatValue7 < 0.42F) {
         floatValue12 = 2.2F + (float)Math.pow(floatValue3, 0.76F) * (f * 0.58F);
      } else if (floatValue7 < 0.84F) {
         floatValue12 = 4.2F + (float)Math.pow(floatValue3, 0.48F) * f;
      } else {
         floatValue12 = f * (0.7F + floatValue3 * 0.44F) + floatValue8 * 4.5F;
         floatValue11 = 1.8F + (float)Math.sqrt(floatValue6) * Math.max(1.0F, g * 0.82F - 1.8F);
      }

      float floatValue13 = measure5(0.58F, 1.0F, floatValue8);
      floatValue11 *= 1.0F - floatValue13 * 0.16F;
      floatValue12 += (floatValue6 - 0.44F) * floatValue13 * 2.8F;
      double doubleValue = Math.cos(floatValue9);
      double doubleValue2 = Math.sin(floatValue9);
      double doubleValue3 = (floatValue4 - 0.5F) * 0.0013;
      double doubleValue4 = (floatValue2 - 0.5F) * 0.001;
      double doubleValue5 = (floatValue3 - 0.5F) * 0.0013;
      clientWorld.addImportantParticleClient(
         FloatingStardustParticle.simpleParticleType, true, vec3d.x + doubleValue * floatValue11, vec3d.y + floatValue12, vec3d.z + doubleValue2 * floatValue11, doubleValue3, doubleValue4, doubleValue5
      );
      this.intValue++;
   }

   private void invoke2(ClientWorld clientWorld, Vec3d vec3d) {
      float floatValue14 = measure9((this.intValue2 + 1) * 0.7548777F + 0.137F);
      float floatValue15 = measure9((this.intValue2 + 1) * 0.5698403F + 0.671F);
      float floatValue16 = measure9((this.intValue2 + 1) * 0.4386875F + 0.293F);
      float floatValue17 = measure9((this.intValue2 + 1) * 0.3271949F + 0.811F);
      float floatValue18 = measure9((this.intValue2 + 1) * 0.27917233F + 0.357F);
      float floatValue19 = floatValue14 * (float) (Math.PI * 2);
      float floatValue20 = floatValue19 + 2.1F + (floatValue15 - 0.5F) * 0.86F;
      float floatValue21 = 12.0F + floatValue16 * 18.92F;
      float floatValue22 = 16.0F * (0.64F + floatValue17 * 0.78F) + 2.0F;
      double doubleValue6 = Math.cos(floatValue19) * floatValue21;
      double doubleValue7 = Math.sin(floatValue19) * floatValue21;
      double doubleValue8 = 0.118 + floatValue18 * 0.092 + Math.min(0.08F, YARKOST.getValue() * 0.018F);
      double doubleValue9 = Math.cos(floatValue20) * doubleValue8;
      double doubleValue10 = Math.sin(floatValue20) * doubleValue8;
      double doubleValue11 = -0.03 - floatValue15 * 0.052;
      clientWorld.addImportantParticleClient(ShootingStarParticle.simpleParticleType, true, vec3d.x + doubleValue6, vec3d.y + floatValue22, vec3d.z + doubleValue7, doubleValue9, doubleValue11, doubleValue10);
      this.intValue2++;
   }

   @EventHandler
   public void onWorldJoin(WorldJoinEvent worldJoinEvent) {
      this.intValue = 0;
      this.intValue2 = 0;
      this.intValue3 = 8;
      FloatingStardustParticle.invoke5();
      ShootingStarParticle.invoke4();
   }

   public static boolean isFlag() {
      return flag;
   }

   public static float measure() {
      return YARKOST.getValue();
   }

   public static float measure2() {
      return Math.min(YARKOST.getValue() * 0.74F, 1.65F);
   }

   public static float measure3() {
      return 22.0F;
   }

   public static float measure4() {
      return 23.0F;
   }

   public static int getIntValue7() {
      return intValue7;
   }

   public static int getIntValue8() {
      return intValue8;
   }

   public static StardustSkyPreset resolve() {
      return StardustSkyPreset.resolve2(SHEYDER_NEBA.getValue());
   }

   public static int compute() {
      return resolve().compute();
   }

   public static boolean check() {
      return flag && timestamp >= 0L;
   }

   public static int compute2(int i) {
      return !check2() ? i : compute6(i, TSVET_OBLAKOV_2.getColor(), SILA_TSVETA_OBLAKOV.getValue());
   }

   private static void invoke3() {
      String text = VREMYA_SUTOK.getValue();
      switch (text) {
         case "День":
            timestamp = 1000L;
            break;
         case "Закат":
            timestamp = 12000L;
            break;
         case "Рассвет":
            timestamp = 23000L;
            break;
         case "Полночь":
            timestamp = 13000L;
            break;
         case "Ночь":
            timestamp = 18000L;
            break;
         case "Полдень":
            timestamp = 6000L;
            break;
         default:
            timestamp = 0L;
      }
   }

   private static boolean check2() {
      return flag && NASTROYKI_OBLAKOV.isEnabled("Кастомные облака") && NASTROYKI_OBLAKOV.isEnabled("Цвет облаков");
   }

   private static void invoke4() {
      int intValue9 = TSVET_NEBA.compute() & 16777215;
      int intValue10 = compute3(intValue9, resolve());
      if (intValue9 != intValue7 || intValue10 != intValue8) {
         intValue7 = intValue9;
         intValue8 = intValue10;
      }
   }

   private static int compute3(int i, StardustSkyPreset stardustSkyPreset) {
      return switch (stardustSkyPreset) {
         case STARDUST -> compute5(i, 0.34F, 0.68F, 1.08F);
         case TWILIGHT_RAYLEIGH -> compute5(i, 0.08F, 0.72F, 1.14F);
         case QUANTUM_NEBULA -> compute5(i, 0.5F, 0.86F, 1.2F);
         case CHRONOS_SINGULARITY -> compute5(i, 0.7F, 0.92F, 1.08F);
         default -> compute4(i);
      };
   }

   private static void invoke5(JsonObject jsonObject) {
      if (jsonObject != null && jsonObject.has("Settings")) {
         try {
            JsonObject jsonObject2 = jsonObject.getAsJsonObject("Settings");
            if (jsonObject2 == null || !jsonObject2.has(SHEYDER_NEBA.name)) {
               return;
            }

            String text2 = jsonObject2.get(SHEYDER_NEBA.name).getAsString();
            StardustSkyPreset stardustSkyPreset2 = StardustSkyPreset.resolve2(text2);
            if (!stardustSkyPreset2.getText().equals(text2)) {
               jsonObject2.addProperty(SHEYDER_NEBA.name, stardustSkyPreset2.getText());
            }
         } catch (Throwable exception) {
         }
      }
   }

   private static int compute4(int i) {
      return compute5(i, 0.32F, 0.74F, 0.92F);
   }

   private static int compute5(int i, float f, float g, float h) {
      int intValue11 = i >>> 16 & 0xFF;
      int intValue12 = i >>> 8 & 0xFF;
      int intValue13 = i & 0xFF;
      float[] floatValues = Color.RGBtoHSB(intValue11, intValue12, intValue13, null);
      float floatValue23 = measure9(floatValues[0] + f);
      float floatValue24 = measure6(g + floatValues[1] * 0.2F, 0.0F, 1.0F);
      float floatValue25 = measure6(h + floatValues[2] * 0.1F, 0.0F, 1.0F);
      return Color.HSBtoRGB(floatValue23, floatValue24, floatValue25) & 16777215;
   }

   private static float measure5(float f, float g, float h) {
      float floatValue26 = (h - f) / (g - f);
      if (floatValue26 <= 0.0F) {
         return 0.0F;
      } else {
         return floatValue26 >= 1.0F ? 1.0F : floatValue26 * floatValue26 * (3.0F - 2.0F * floatValue26);
      }
   }

   private static float measure6(float f, float g, float h) {
      if (f < g) {
         return g;
      } else {
         return f > h ? h : f;
      }
   }

   private static float measure7(float f) {
      return !Float.isFinite(f) ? 0.0F : Math.max(0.0F, Math.min(1.0F, f));
   }

   private static float measure8(float f, float g, float h) {
      return f + (g - f) * h;
   }

   private static int compute6(int i, Color color, float f) {
      float floatValue27 = measure7(f * (color.getAlpha() / 255.0F));
      if (floatValue27 <= 0.0F) {
         return i;
      } else {
         int intValue14 = i >>> 24 & 0xFF;
         int intValue15 = i >>> 16 & 0xFF;
         int intValue16 = i >>> 8 & 0xFF;
         int intValue17 = i & 0xFF;
         int intValue18 = Math.round(measure8(intValue15, color.getRed(), floatValue27));
         int intValue19 = Math.round(measure8(intValue16, color.getGreen(), floatValue27));
         int intValue20 = Math.round(measure8(intValue17, color.getBlue(), floatValue27));
         return intValue14 << 24 | intValue18 << 16 | intValue19 << 8 | intValue20;
      }
   }

   private static float measure9(float f) {
      return f - (float)Math.floor(f);
   }
}
