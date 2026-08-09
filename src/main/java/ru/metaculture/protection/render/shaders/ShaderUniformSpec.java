package ru.metaculture.protection;

import java.util.Arrays;

public record ShaderUniformSpec(String name, String uniformName, ShaderUniformSpec.ShaderUniformSpecState kind, float[] defaults, float minimum, float maximum, float increment) {
   public ShaderUniformSpec(String name, String uniformName, ShaderUniformSpec.ShaderUniformSpecState kind, float[] defaults, float minimum, float maximum, float increment) {
      name = name != null && !name.isBlank() ? name.trim() : "Value";
      uniformName = uniformName != null && !uniformName.isBlank() ? uniformName.trim() : "u_Value";
      defaults = defaults == null ? new float[]{0.0F, 0.0F, 0.0F, 1.0F} : Arrays.copyOf(defaults, Math.max(4, defaults.length));
      if (!Float.isFinite(minimum)) {
         minimum = 0.0F;
      }

      if (!Float.isFinite(maximum) || maximum <= minimum) {
         maximum = minimum + 1.0F;
      }

      if (!Float.isFinite(increment) || increment <= 0.0F) {
         increment = 0.01F;
      }

      this.name = name;
      this.uniformName = uniformName;
      this.kind = kind;
      this.defaults = defaults;
      this.minimum = minimum;
      this.maximum = maximum;
      this.increment = increment;
   }

   public float defaultFloat() {
      return this.defaults[0];
   }

   public int defaultRgba() {
      int intValue = channel(this.defaults[0]);
      int intValue2 = channel(this.defaults[1]);
      int intValue3 = channel(this.defaults[2]);
      int intValue4 = channel(this.defaults[3]);
      return intValue4 << 24 | intValue << 16 | intValue2 << 8 | intValue3;
   }

   private static int channel(float f) {
      return !Float.isFinite(f) ? 0 : Math.max(0, Math.min(255, Math.round(f * 255.0F)));
   }

   public static enum ShaderUniformSpecState {
      FLOAT,
      COLOR;
   }
}
