package ru.metaculture.protection;

final class RenderEngineFacade {
   private final RenderEngine renderEngine;

   RenderEngineFacade(RenderEngine renderEngine) {
      this.renderEngine = renderEngine;
   }

   void invoke(float f, float g, float h, float i, float j, float k, float l, float m, int n, float[] fs) {
      this.renderEngine.invoke29(f, g, h, i, j, k, l, m, n, fs);
   }

   void invoke2(float f, float g, float h, float i, float j, float k, float l, float m, int n, float o, float[] fs) {
      this.renderEngine.invoke30(f, g, h, i, j, k, l, m, n, o, fs);
   }

   void invoke3(float f, float g, float h, float i, float j, float k, float l, float m, int n, int o, int p, int q, float[] fs) {
      this.renderEngine.invoke31(f, g, h, i, j, k, l, m, n, o, p, q, fs);
   }

   void invoke4(float f, float g, float h, float i, float j, int k, float[] fs) {
      this.renderEngine.invoke32(f, g, h, i, j, k, fs);
   }

   void invoke5(float f, float g, float h, float i, float j, float k, int l, float[] fs) {
      this.renderEngine.invoke33(f, g, h, i, j, k, l, fs);
   }

   void invoke6() {
      this.renderEngine.invoke19();
   }
}
