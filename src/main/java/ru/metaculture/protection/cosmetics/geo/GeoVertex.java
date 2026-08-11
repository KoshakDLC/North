package ru.metaculture.protection.cosmetics.geo;

public final class GeoVertex {
   public final Vec3F position;
   public final float textureU;
   public final float textureV;

   public GeoVertex(float x, float y, float z, float u, float v) {
      this.position = new Vec3F(x, y, z);
      this.textureU = u;
      this.textureV = v;
   }
}
