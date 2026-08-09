package ru.metaculture.protection;

public enum ShaderValueType {
   FLOAT("float", 1),
   VEC2("vec2", 2),
   VEC3("vec3", 3),
   VEC4("vec4", 4),
   INT("int", 1);

   private final String text;
   private final int intValue;

   private ShaderValueType(String string2, int j) {
      this.text = string2;
      this.intValue = j;
   }

   public String getText() {
      return this.text;
   }

   public int getIntValue() {
      return this.intValue;
   }
}
