package ru.metaculture.protection;

public final class RenderHashSink {
   private RenderStateHasher renderStateHasher;

   void setRenderStateHasher(RenderStateHasher renderStateHasher) {
      this.renderStateHasher = renderStateHasher;
   }

   public void invoke(int i) {
      if (this.renderStateHasher != null) {
         this.renderStateHasher.invoke(i);
      }
   }

   public void invoke2(long l) {
      if (this.renderStateHasher != null) {
         this.renderStateHasher.invoke2(l);
      }
   }

   public void invoke3(float f) {
      if (this.renderStateHasher != null) {
         this.renderStateHasher.invoke3(f);
      }
   }
}
