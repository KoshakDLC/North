package ru.metaculture.protection;

public record ShaderPin(String id, String label, ShaderValueType type, ShaderPinDirection direction, String defaultExpression) {
   public static ShaderPin input(String string, String string2, ShaderValueType shaderValueType, String string3) {
      return new ShaderPin(string, string2, shaderValueType, ShaderPinDirection.INPUT, string3);
   }

   public static ShaderPin output(String string, String string2, ShaderValueType shaderValueType2) {
      return new ShaderPin(string, string2, shaderValueType2, ShaderPinDirection.OUTPUT, "");
   }
}
