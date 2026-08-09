package ru.metaculture.protection;

public final class NamedShaderProgram implements AutoCloseable {
   private final ShaderSourceBuilder shaderSourceBuilder;
   private ShaderSurface shaderSurface = ShaderSurface.PREVIEW_ONLY;
   private String text = "";
   private String text2 = "";

   public NamedShaderProgram(ShaderSourceBuilder shaderSourceBuilder) {
      this.shaderSourceBuilder = shaderSourceBuilder;
      ShaderPresetRegistry.getINSTANCE().setShaderSourceBuilder(shaderSourceBuilder);
   }

   public ShaderSurface getShaderSurface() {
      return this.shaderSurface;
   }

   public void invoke(ShaderSurface shaderSurface) {
      if (shaderSurface != null) {
         this.shaderSurface = shaderSurface;
      }
   }

   public void invoke2(ShaderNode shaderNode) {
      if (shaderNode != null && this.shaderSourceBuilder != null) {
         ShaderBuildResult shaderBuildResult = this.shaderSourceBuilder.resolve2(shaderNode);
         this.text = shaderBuildResult.hash();
         this.text2 = shaderBuildResult.error() == null ? "" : shaderBuildResult.error();
         ShaderPresetRegistry.getINSTANCE().invoke(this.shaderSurface, shaderNode, shaderBuildResult);
         ThemeShaderProgramCache.getINSTANCE().resolve2(this.shaderSurface, shaderBuildResult);
      }
   }

   public ShaderBuildResult resolve(ShaderNode shaderNode2) {
      if (shaderNode2 != null && this.shaderSourceBuilder != null) {
         ShaderBuildResult shaderBuildResult2 = this.shaderSourceBuilder.resolve2(shaderNode2);
         this.text = shaderBuildResult2.hash();
         this.text2 = shaderBuildResult2.error() == null ? "" : shaderBuildResult2.error();
         return shaderBuildResult2;
      } else {
         return null;
      }
   }

   public boolean check(String string, ShaderNode shaderNode3) {
      if (shaderNode3 != null && this.shaderSourceBuilder != null) {
         String text = ShaderPresetRegistry.resolve21(string);
         if (text.isBlank()) {
            this.text2 = "Shader name is empty";
            return false;
         } else {
            ShaderBuildResult shaderBuildResult3 = this.shaderSourceBuilder.resolve2(shaderNode3);
            this.text = shaderBuildResult3.hash();
            this.text2 = shaderBuildResult3.error() == null ? "" : shaderBuildResult3.error();
            if (!this.text2.isBlank()) {
               return false;
            } else {
               ShaderPresetRegistry.getINSTANCE().invoke2(text, shaderNode3, shaderBuildResult3);
               ThemeShaderProgramCache.getINSTANCE().resolve3(text, shaderBuildResult3);
               return true;
            }
         }
      } else {
         return false;
      }
   }

   public void invoke3(ShaderNode shaderNode4, float f, float g, float h, float i, int j, int k, float l, float m, ColorScheme colorScheme, float n) {
      if (shaderNode4 != null && colorScheme != null) {
         this.invoke2(shaderNode4);
         ShaderUniformBinder.check(this.shaderSurface, f, g, h, i, j, k, l, m, colorScheme, n);
      }
   }

   public String resolve2() {
      return !this.text2.isBlank() ? this.text2 : ThemeShaderProgramCache.getINSTANCE().resolve4(this.shaderSurface);
   }

   public String resolve3() {
      return this.text == null ? "" : this.text;
   }

   @Override
   public void close() {
      this.text = "";
      this.text2 = "";
   }
}
