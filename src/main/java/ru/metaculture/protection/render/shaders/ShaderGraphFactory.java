package ru.metaculture.protection;

public final class ShaderGraphFactory {
   private ShaderGraphFactory() {
   }

   public static ShaderNode resolve(ShaderNodeRegistry shaderNodeRegistry) {
      return ShaderTemplateCatalog.resolve2(shaderNodeRegistry);
   }
}
