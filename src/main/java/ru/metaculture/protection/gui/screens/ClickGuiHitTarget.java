package ru.metaculture.protection;

public final class ClickGuiHitTarget {
   private final int intValue;
   private final float floatValue;
   private final float floatValue2;
   private final float floatValue3;
   private final float floatValue4;
   private final float floatValue5;
   private final float floatValue6;
   private final float floatValue7;
   private final float floatValue8;
   private final ClickGuiAction clickGuiAction;

   ClickGuiHitTarget(ClickGuiHitTarget.ClickGuiHitTargetState clickGuiHitTargetState) {
      this.intValue = clickGuiHitTargetState.intValue;
      this.floatValue = clickGuiHitTargetState.floatValue;
      this.floatValue2 = clickGuiHitTargetState.floatValue2;
      this.floatValue3 = clickGuiHitTargetState.floatValue3;
      this.floatValue4 = clickGuiHitTargetState.floatValue4;
      this.floatValue5 = clickGuiHitTargetState.floatValue5;
      this.floatValue6 = clickGuiHitTargetState.floatValue6;
      this.floatValue7 = clickGuiHitTargetState.floatValue7;
      this.floatValue8 = clickGuiHitTargetState.floatValue8;
      this.clickGuiAction = clickGuiHitTargetState.clickGuiAction;
   }

   public static ClickGuiHitTarget.ClickGuiHitTargetState resolve() {
      return new ClickGuiHitTarget.ClickGuiHitTargetState();
   }

   public boolean check(float f, float g, int i) {
      return (this.intValue < 0 || this.intValue == i)
         && this.check2(f, g)
         && f >= this.floatValue
         && g >= this.floatValue2
         && f < this.floatValue + this.floatValue3
         && g < this.floatValue2 + this.floatValue4;
   }

   public void invoke(ClickGuiState clickGuiState) {
      if (this.clickGuiAction != null) {
         this.clickGuiAction.execute(clickGuiState);
      }
   }

   private boolean check2(float f, float g) {
      return !(this.floatValue7 <= 0.0F) && !(this.floatValue8 <= 0.0F)
         ? f >= this.floatValue5
            && g >= this.floatValue6
            && f < this.floatValue5 + this.floatValue7
            && g < this.floatValue6 + this.floatValue8
         : true;
   }

   public static final class ClickGuiHitTargetState {
      int intValue = -1;
      float floatValue;
      float floatValue2;
      float floatValue3;
      float floatValue4;
      float floatValue5;
      float floatValue6;
      float floatValue7;
      float floatValue8;
      ClickGuiAction clickGuiAction;

      public ClickGuiHitTarget.ClickGuiHitTargetState setIntValue(int i) {
         this.intValue = i;
         return this;
      }

      public ClickGuiHitTarget.ClickGuiHitTargetState setFloatValue(float f) {
         this.floatValue = f;
         return this;
      }

      public ClickGuiHitTarget.ClickGuiHitTargetState setFloatValue2(float f) {
         this.floatValue2 = f;
         return this;
      }

      public ClickGuiHitTarget.ClickGuiHitTargetState setFloatValue3(float f) {
         this.floatValue3 = f;
         return this;
      }

      public ClickGuiHitTarget.ClickGuiHitTargetState setFloatValue4(float f) {
         this.floatValue4 = f;
         return this;
      }

      public ClickGuiHitTarget.ClickGuiHitTargetState setFloatValue5(float f) {
         this.floatValue5 = f;
         return this;
      }

      public ClickGuiHitTarget.ClickGuiHitTargetState setFloatValue6(float f) {
         this.floatValue6 = f;
         return this;
      }

      public ClickGuiHitTarget.ClickGuiHitTargetState setFloatValue7(float f) {
         this.floatValue7 = f;
         return this;
      }

      public ClickGuiHitTarget.ClickGuiHitTargetState setFloatValue8(float f) {
         this.floatValue8 = f;
         return this;
      }

      public ClickGuiHitTarget.ClickGuiHitTargetState setClickGuiAction(ClickGuiAction clickGuiAction) {
         this.clickGuiAction = clickGuiAction;
         return this;
      }

      public ClickGuiHitTarget resolve() {
         return new ClickGuiHitTarget(this);
      }
   }
}
