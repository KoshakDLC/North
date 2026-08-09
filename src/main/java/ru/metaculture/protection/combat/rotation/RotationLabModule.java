package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.List;

public final class RotationLabModule {
   public int intValue = 1;
   public long timestamp;
   public long timestamp2;
   public String rotationLab = "rotation_lab";
   public String rotationlab = "RotationLab";
   public List<RotationLabModule.RotationLabModuleState> items = new ArrayList<>();

   public static final class RotationLabModuleState {
      public String mixed = "Mixed";
      public long timestamp;
      public float floatValue;
      public float floatValue2;
      public float floatValue3;
      public float floatValue4;
      public float floatValue5;
      public float floatValue6;
      public int intValue;
      public int intValue2;
      public float floatValue7;
      public List<RotationLabModule.RotationLabModuleState2> items = new ArrayList<>();

      public float measure() {
         float floatValue = Math.abs(Math.abs(this.floatValue3) > 0.001F ? this.floatValue3 : this.floatValue);
         float floatValue2 = Math.abs(Math.abs(this.floatValue4) > 0.001F ? this.floatValue4 : this.floatValue2);
         return (float)Math.hypot(floatValue, floatValue2);
      }
   }

   public static final class RotationLabModuleState2 {
      public int intValue;
      public float floatValue;
      public float floatValue2;
      public float floatValue3;
      public float floatValue4;
      public float floatValue5;
      public float floatValue6;
      public float floatValue7;
   }
}
