package ru.metaculture.protection;

import lombok.Generated;
import org.wild.module.api.Module;

public final class ModulePlacement {
   private final Module module;
   private final float floatValue;
   private final float floatValue2;
   private final float floatValue3;
   private final float floatValue4;
   private final float floatValue5;

   @Generated
   public ModulePlacement(Module module, float f, float g, float h, float i, float j) {
      this.module = module;
      this.floatValue = f;
      this.floatValue2 = g;
      this.floatValue3 = h;
      this.floatValue4 = i;
      this.floatValue5 = j;
   }

   @Generated
   public Module getModule() {
      return this.module;
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
   public float getFloatValue5() {
      return this.floatValue5;
   }

   @Generated
   @Override
   public boolean equals(Object object) {
      if (object == this) {
         return true;
      } else if (!(object instanceof ModulePlacement modulePlacement)) {
         return false;
      } else if (Float.compare(this.getFloatValue(), modulePlacement.getFloatValue()) != 0) {
         return false;
      } else if (Float.compare(this.getFloatValue2(), modulePlacement.getFloatValue2()) != 0) {
         return false;
      } else if (Float.compare(this.getFloatValue3(), modulePlacement.getFloatValue3()) != 0) {
         return false;
      } else if (Float.compare(this.getFloatValue4(), modulePlacement.getFloatValue4()) != 0) {
         return false;
      } else if (Float.compare(this.getFloatValue5(), modulePlacement.getFloatValue5()) != 0) {
         return false;
      } else {
         Module module2 = this.getModule();
         Module module3 = modulePlacement.getModule();
         return module2 == null ? module3 == null : module2.equals(module3);
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
      intValue = intValue * 59 + Float.floatToIntBits(this.getFloatValue4());
      intValue = intValue * 59 + Float.floatToIntBits(this.getFloatValue5());
      Module module4 = this.getModule();
      return intValue * 59 + (module4 == null ? 43 : module4.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "ModulePlacement(module="
         + this.getModule()
         + ", x="
         + this.getFloatValue()
         + ", y="
         + this.getFloatValue2()
         + ", width="
         + this.getFloatValue3()
         + ", height="
         + this.getFloatValue4()
         + ", settingsHeight="
         + this.getFloatValue5()
         + ")";
   }
}
