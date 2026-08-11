package ru.metaculture.protection.cosmetics.geo;

public final class GeoQuad {
   public final GeoVertex[] vertices;
   public final Vec3F normal;

   public GeoQuad(GeoVertex[] vertices, float nx, float ny, float nz) {
      this.vertices = vertices;
      this.normal = new Vec3F(nx, ny, nz);
   }
}
