package ru.metaculture.protection;

import java.util.Locale;
import java.util.Map;

public final class ShaderExpressionUtils {
   private final ShaderNode shaderNode;
   private final ShaderNodeRegistry shaderNodeRegistry;
   private final Map<String, String> valuesByKey;
   private final Map<String, String> valuesByKey2;
   private final ShaderSurface shaderSurface;

   ShaderExpressionUtils(ShaderNode shaderNode, ShaderNodeRegistry shaderNodeRegistry, Map<String, String> map, Map<String, String> map2, ShaderSurface shaderSurface) {
      this.shaderNode = shaderNode;
      this.shaderNodeRegistry = shaderNodeRegistry;
      this.valuesByKey = map;
      this.valuesByKey2 = map2;
      this.shaderSurface = shaderSurface == null ? ShaderSurface.PREVIEW_ONLY : shaderSurface.resolve();
   }

   public String resolve(ShaderNodeKind shaderNodeKind, String string) {
      ShaderConnection shaderConnection = this.shaderNode.resolve4(shaderNodeKind.getText(), string);
      if (shaderConnection != null) {
         String text = this.valuesByKey.get(shaderConnection.resolve());
         if (text != null) {
            return text;
         }
      }

      ShaderNodeDefinition shaderNodeDefinition = this.shaderNodeRegistry.resolve(shaderNodeKind.getText2());
      if (shaderNodeDefinition != null) {
         ShaderPin shaderPin = shaderNodeDefinition.resolve(string);
         if (shaderPin != null && shaderPin.defaultExpression() != null && !shaderPin.defaultExpression().isBlank()) {
            return shaderPin.defaultExpression();
         }
      }

      return "0.0";
   }

   public String resolve2(float f) {
      if (!Float.isFinite(f)) {
         return "0.0";
      } else {
         String text2 = String.format(Locale.ROOT, "%.6f", f);

         while (text2.contains(".") && text2.endsWith("0")) {
            text2 = text2.substring(0, text2.length() - 1);
         }

         if (text2.endsWith(".")) {
            text2 = text2 + "0";
         }

         return text2;
      }
   }

   public String resolve3(ShaderNodeKind shaderNodeKind2, String string) {
      return "n_" + resolve5(shaderNodeKind2.getText()) + "_" + resolve5(string);
   }

   public String resolve4(ShaderNodeKind shaderNodeKind3) {
      return shaderNodeKind3 == null
         ? "u_Value"
         : this.valuesByKey2.getOrDefault(shaderNodeKind3.getText(), "u_" + resolve5(shaderNodeKind3.resolve("name", "Value")));
   }

   public ShaderSurface getShaderSurface() {
      return this.shaderSurface;
   }

   public boolean check() {
      return this.shaderSurface == ShaderSurface.HUD;
   }

   private static String resolve5(String string) {
      if (string != null && !string.isBlank()) {
         String text3 = string.replaceAll("[^A-Za-z0-9_]", "_");
         return Character.isDigit(text3.charAt(0)) ? "_" + text3 : text3;
      } else {
         return "x";
      }
   }
}
