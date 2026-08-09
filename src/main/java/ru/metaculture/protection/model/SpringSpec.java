package ru.metaculture.protection;

import lombok.Generated;

public final class SpringSpec {
   private final float floatValue;
   private final float floatValue2;
   private final float floatValue3;
   private final float floatValue4;

   private static SpringSpec resolve(float f, float g, float h, float i) {
      AnimationPreset animationPreset = AnimationPreset.resolve();
      return new SpringSpec(animationPreset.measure(f), animationPreset.measure2(g), animationPreset.measure3(h), animationPreset.measure3(i));
   }

   public static SpringSpec resolve2() {
      return resolve(0.045F, 0.85F, 0.001F, 0.001F);
   }

   public static SpringSpec resolve3() {
      return new SpringSpec(0.03F, 0.87F, 0.001F, 0.001F);
   }

   public static SpringSpec resolve4() {
      return new SpringSpec(0.075F, 0.86F, 0.002F, 0.002F);
   }

   public static SpringSpec resolve5() {
      return new SpringSpec(0.045F, 0.85F, 0.001F, 0.001F);
   }

   public static SpringSpec resolve6() {
      return resolve(0.065F, 0.75F, 0.001F, 0.001F);
   }

   public static SpringSpec resolve7() {
      return resolve(0.12F, 0.9F, 0.02F, 0.02F);
   }

   public static SpringSpec resolve8() {
      return resolve(0.05F, 0.84F, 0.001F, 0.001F);
   }

   public static SpringSpec resolve9() {
      return resolve(0.062F, 0.86F, 0.001F, 0.001F);
   }

   public static SpringSpec resolve10() {
      return resolve(0.08F, 0.55F, 0.001F, 0.001F);
   }

   public static SpringSpec resolve11() {
      return resolve(0.105F, 0.68F, 0.001F, 0.001F);
   }

   public static SpringSpec resolve12() {
      return resolve(0.038F, 0.86F, 0.001F, 0.001F);
   }

   public static SpringSpec resolve13() {
      return resolve(0.052F, 0.72F, 0.001F, 0.001F);
   }

   public static SpringSpec resolve14() {
      return resolve(0.018F, 0.88F, 0.001F, 0.001F);
   }

   public static SpringSpec resolve15() {
      return resolve(0.012F, 0.92F, 0.001F, 0.001F);
   }

   public static SpringSpec resolve16() {
      return resolve(0.1F, 0.88F, 0.002F, 0.002F);
   }

   public static SpringSpec resolve17() {
      return resolve(0.035F, 0.88F, 0.001F, 0.001F);
   }

   public static SpringSpec resolve18() {
      return new SpringSpec(0.06111111F, (float)Math.exp(-0.4F), 0.001F, 0.001F);
   }

   @Generated
   public SpringSpec(float f, float g, float h, float i) {
      this.floatValue = f;
      this.floatValue2 = g;
      this.floatValue3 = h;
      this.floatValue4 = i;
   }

   @Generated
   public float getFloatValue() {
      return this.floatValue;
   }

   @Generated
   public float getFloatValue2() {
      return this.floatValue2;
   }

   @Generated
   public float getFloatValue3() {
      return this.floatValue3;
   }

   @Generated
   public float getFloatValue4() {
      return this.floatValue4;
   }

   @Generated
   @Override
   public boolean equals(Object object) {
      if (object == this) {
         return true;
      } else if (!(object instanceof SpringSpec springSpec)) {
         return false;
      } else if (Float.compare(this.getFloatValue(), springSpec.getFloatValue()) != 0) {
         return false;
      } else if (Float.compare(this.getFloatValue2(), springSpec.getFloatValue2()) != 0) {
         return false;
      } else {
         return Float.compare(this.getFloatValue3(), springSpec.getFloatValue3()) != 0 ? false : Float.compare(this.getFloatValue4(), springSpec.getFloatValue4()) == 0;
      }
   }

   @Generated
   @Override
   public int hashCode() {
      byte byteValue = 59;
      int intValue = 1;
      intValue = intValue * 59 + Float.floatToIntBits(this.getFloatValue());
      intValue = intValue * 59 + Float.floatToIntBits(this.getFloatValue2());
      intValue = intValue * 59 + Float.floatToIntBits(this.getFloatValue3());
      return intValue * 59 + Float.floatToIntBits(this.getFloatValue4());
   }

   @Generated
   @Override
   public String toString() {
      return "SpringSpec(stiffness="
         + this.getFloatValue()
         + ", damping="
         + this.getFloatValue2()
         + ", settleDistance="
         + this.getFloatValue3()
         + ", settleVelocity="
         + this.getFloatValue4()
         + ")";
   }
}
