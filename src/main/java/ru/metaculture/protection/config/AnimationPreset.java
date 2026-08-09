package ru.metaculture.protection;

public enum AnimationPreset {
   SMOOTH("Smooth", 1.0F, 1.0F, 1.0F),
   SNAPPY("Snappy", 1.55F, 1.06F, 1.1F),
   BOUNCY("Bouncy", 0.82F, 0.62F, 0.85F),
   CINEMATIC("Cinematic", 0.55F, 1.0F, 0.92F),
   LINEAR("Linear", 2.1F, 1.16F, 1.5F);

   public static final AnimationPreset DEFAULT = SMOOTH;
   private static final float FLOAT_VALUE = 0.001F;
   private static final float FLOAT_VALUE_2 = 0.05F;
   private static final float FLOAT_VALUE_3 = 0.985F;
   public final String text;
   public final float floatValue;
   public final float floatValue2;
   public final float floatValue3;

   private AnimationPreset(String string2, float f, float g, float h) {
      this.text = string2;
      this.floatValue = f;
      this.floatValue2 = g;
      this.floatValue3 = h;
   }

   public float measure(float f) {
      return Math.max(0.001F, f * this.floatValue);
   }

   public float measure2(float f) {
      float floatValue = f * this.floatValue2;
      if (floatValue < 0.05F) {
         return 0.05F;
      } else {
         return floatValue > 0.985F ? 0.985F : floatValue;
      }
   }

   public float measure3(float f) {
      return f * this.floatValue3;
   }

   public static AnimationPreset resolve() {
      try {
         return MenuModule.STIL_ANIMATSIY == null ? DEFAULT : resolve2(MenuModule.STIL_ANIMATSIY.getValue());
      } catch (Throwable exception) {
         return DEFAULT;
      }
   }

   public static AnimationPreset resolve2(String string) {
      if (string == null) {
         return DEFAULT;
      } else {
         for (AnimationPreset animationPreset : values()) {
            if (animationPreset.text.equalsIgnoreCase(string)) {
               return animationPreset;
            }
         }

         return DEFAULT;
      }
   }

   public static String[] resolve3() {
      AnimationPreset[] animationPresets = values();
      String[] texts = new String[animationPresets.length];

      for (int intValue = 0; intValue < animationPresets.length; intValue++) {
         texts[intValue] = animationPresets[intValue].text;
      }

      return texts;
   }
}
