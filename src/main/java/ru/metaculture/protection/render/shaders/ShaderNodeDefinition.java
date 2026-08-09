package ru.metaculture.protection;

import java.util.List;
import java.util.Objects;

public final class ShaderNodeDefinition {
   private final String text;
   private final String text2;
   private final String text3;
   private final float floatValue;
   private final List<ShaderPin> items;
   private final List<ShaderPin> items2;
   private final ShaderNodeEmitter shaderNodeEmitter;
   private final boolean flag;

   public ShaderNodeDefinition(
      String string, String string2, String string3, float f, List<ShaderPin> list, List<ShaderPin> list2, ShaderNodeEmitter shaderNodeEmitter
   ) {
      this(string, string2, string3, f, list, list2, shaderNodeEmitter, check(string3, list2));
   }

   public ShaderNodeDefinition(
      String string, String string2, String string3, float f, List<ShaderPin> list, List<ShaderPin> list2, ShaderNodeEmitter shaderNodeEmitter2, boolean bl
   ) {
      this.text = Objects.requireNonNull(string, "id");
      this.text2 = Objects.requireNonNull(string2, "title");
      this.text3 = Objects.requireNonNull(string3, "category");
      this.floatValue = Math.max(132.0F, f);
      this.items = List.copyOf(list);
      this.items2 = List.copyOf(list2);
      this.shaderNodeEmitter = Objects.requireNonNull(shaderNodeEmitter2, "emitter");
      this.flag = bl;
   }

   public String getText() {
      return this.text;
   }

   public String getText2() {
      return this.text2;
   }

   public String getText3() {
      return this.text3;
   }

   public float getFloatValue() {
      return this.floatValue;
   }

   public List<ShaderPin> getItems() {
      return this.items;
   }

   public List<ShaderPin> getItems2() {
      return this.items2;
   }

   public ShaderNodeEmitter getShaderNodeEmitter() {
      return this.shaderNodeEmitter;
   }

   public boolean isFlag() {
      return this.flag;
   }

   public ShaderPin resolve(String string) {
      for (ShaderPin shaderPin : this.items) {
         if (shaderPin.id().equals(string)) {
            return shaderPin;
         }
      }

      return null;
   }

   public ShaderPin resolve2(String string) {
      for (ShaderPin shaderPin2 : this.items2) {
         if (shaderPin2.id().equals(string)) {
            return shaderPin2;
         }
      }

      return null;
   }

   private static boolean check(String string, List<ShaderPin> list) {
      return list != null && !list.isEmpty();
   }
}
