package ru.metaculture.protection;

import java.util.function.Function;

public enum AnimationMode {
   LINEAR(double_ -> double_),
   SIGMOID(double_ -> 1.0 / (1.0 + Math.exp(-double_))),
   EASE_IN_QUAD(double_ -> double_ * double_),
   EASE_OUT_QUAD(double_ -> double_ * (2.0 - double_)),
   EASE_IN_OUT_QUAD(double_ -> double_ < 0.5 ? 2.0 * double_ * double_ : -1.0 + (4.0 - 2.0 * double_) * double_),
   EASE_IN_CUBIC(double_ -> double_ * double_ * double_),
   EASE_OUT_CUBIC(double_ -> {
      Double doubleValue;
      return (doubleValue = double_ - 1.0) * doubleValue * doubleValue + 1.0;
   }),
   EASE_IN_OUT_CUBIC(double_ -> double_ < 0.5 ? 4.0 * double_ * double_ * double_ : (double_ - 1.0) * (2.0 * double_ - 2.0) * (2.0 * double_ - 2.0) + 1.0),
   EASE_IN_QUART(double_ -> double_ * double_ * double_ * double_),
   EASE_OUT_QUART(double_ -> {
      Double doubleValue2;
      return 1.0 - (doubleValue2 = double_ - 1.0) * doubleValue2 * doubleValue2 * doubleValue2;
   }),
   EASE_IN_OUT_QUART(double_ -> {
      Double doubleValue3;
      return double_ < 0.5 ? 8.0 * double_ * double_ * double_ * double_ : 1.0 - 8.0 * (doubleValue3 = double_ - 1.0) * doubleValue3 * doubleValue3 * doubleValue3;
   }),
   EASE_IN_QUINT(double_ -> double_ * double_ * double_ * double_ * double_),
   EASE_OUT_QUINT(double_ -> {
      Double doubleValue4;
      return 1.0 + (doubleValue4 = double_ - 1.0) * doubleValue4 * doubleValue4 * doubleValue4 * doubleValue4;
   }),
   EASE_IN_OUT_QUINT(double_ -> {
      Double doubleValue5;
      return double_ < 0.5 ? 16.0 * double_ * double_ * double_ * double_ * double_ : 1.0 + 16.0 * (doubleValue5 = double_ - 1.0) * doubleValue5 * doubleValue5 * doubleValue5 * doubleValue5;
   }),
   EASE_IN_SINE(double_ -> 1.0 - Math.cos(double_ * Math.PI / 2.0)),
   EASE_OUT_SINE(double_ -> Math.sin(double_ * Math.PI / 2.0)),
   EASE_IN_OUT_SINE(double_ -> 1.0 - Math.cos(Math.PI * double_ / 2.0)),
   EASE_IN_EXPO(double_ -> double_ == 0.0 ? 0.0 : Math.pow(2.0, 10.0 * double_ - 10.0)),
   EASE_OUT_EXPO(double_ -> double_ == 1.0 ? 1.0 : 1.0 - Math.pow(2.0, -10.0 * double_)),
   EASE_IN_OUT_EXPO(
      double_ -> double_ == 0.0
         ? 0.0
         : (double_ == 1.0 ? 1.0 : (double_ < 0.5 ? Math.pow(2.0, 20.0 * double_ - 10.0) / 2.0 : (2.0 - Math.pow(2.0, -20.0 * double_ + 10.0)) / 2.0))
   ),
   EASE_IN_CIRC(double_ -> 1.0 - Math.sqrt(1.0 - double_ * double_)),
   EASE_OUT_CIRC(double_ -> {
      Double doubleValue6;
      return Math.sqrt(1.0 - (doubleValue6 = double_ - 1.0) * doubleValue6);
   }),
   EASE_IN_OUT_CIRC(
      double_ -> double_ < 0.5 ? (1.0 - Math.sqrt(1.0 - 4.0 * double_ * double_)) / 2.0 : (Math.sqrt(1.0 - 4.0 * (double_ - 1.0) * double_) + 1.0) / 2.0
   ),
   EASE_IN_BACK(double_ -> 2.70158 * double_ * double_ * double_ - 1.70158 * double_ * double_),
   EASE_OUT_BACK(double_ -> 1.0 + 2.70158 * Math.pow(double_ - 1.0, 3.0) + 1.70158 * Math.pow(double_ - 1.0, 2.0)),
   EASE_IN_OUT_BACK(
      double_ -> double_ < 0.5
         ? Math.pow(2.0 * double_, 2.0) * (7.189819 * double_ - 2.5949095) / 2.0
         : (Math.pow(2.0 * double_ - 2.0, 2.0) * (3.5949095 * (double_ * 2.0 - 2.0) + 2.5949095) + 2.0) / 2.0
   ),
   EASE_IN_ELASTIC(
      double_ -> double_ == 0.0
         ? 0.0
         : (double_ == 1.0 ? 1.0 : -Math.pow(2.0, 10.0 * double_ - 10.0) * Math.sin((double_ * 10.0 - 10.75) * (Math.PI * 2.0 / 3.0)))
   ),
   EASE_OUT_ELASTIC(
      double_ -> double_ == 0.0
         ? 0.0
         : (double_ == 1.0 ? 1.0 : Math.pow(2.0, -10.0 * double_) * Math.sin((double_ * 10.0 - 0.75) * (Math.PI * 2.0 / 3.0)) * 0.5 + 1.0)
   ),
   EASE_IN_OUT_ELASTIC(
      double_ -> double_ == 0.0
         ? 0.0
         : (
            double_ == 1.0
               ? 1.0
               : (
                  double_ < 0.5
                     ? -(Math.pow(2.0, 20.0 * double_ - 10.0) * Math.sin((20.0 * double_ - 11.125) * (Math.PI * 4.0 / 9.0))) / 2.0
                     : Math.pow(2.0, -20.0 * double_ + 10.0) * Math.sin((20.0 * double_ - 11.125) * (Math.PI * 4.0 / 9.0)) / 2.0 + 1.0
               )
         )
   ),
   SHRINK_EASING(double_ -> {
      float floatValue = 1.3F;
      float floatValue2 = floatValue + 1.0F;
      return Math.max(0.0, 1.0 + floatValue2 * Math.pow(double_ - 1.0, 3.0) + floatValue * Math.pow(double_ - 1.0, 2.0));
   });

   private final Function<Double, Double> function;

   private AnimationMode(Function<Double, Double> function) {
      this.function = function;
   }

   public Function<Double, Double> getFunction() {
      return this.function;
   }

   public double measure(double d) {
      return this.getFunction().apply(d);
   }

   public float measure2(float f) {
      return this.getFunction().apply((double)f).floatValue();
   }

   public static String resolve(String string) {
      int intValue;
      if (string != null && (intValue = string.length()) != 0) {
         char character = string.charAt(0);
         char character2 = Character.toTitleCase(character);
         if (character == character2) {
            return string;
         } else {
            char[] characters = new char[intValue];
            characters[0] = character2;
            string.getChars(1, intValue, characters, 1);
            return String.valueOf(characters);
         }
      } else {
         return string;
      }
   }

   @Override
   public String toString() {
      return resolve(super.toString().toLowerCase().replace("_", " "));
   }
}
