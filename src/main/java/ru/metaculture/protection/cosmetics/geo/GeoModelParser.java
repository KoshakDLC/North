package ru.metaculture.protection.cosmetics.geo;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.util.HashMap;
import java.util.Map;

public final class GeoModelParser {
   private GeoModelParser() {
   }

   public static GeoModel parse(String json) {
      try {
         return parseModel(JsonParser.parseString(json).getAsJsonObject());
      } catch (Exception exception) {
         System.out.println("[Cosmetics] Failed to parse Bedrock model: " + exception.getMessage());
         return null;
      }
   }

   private static GeoModel parseModel(JsonObject root) {
      JsonArray geometries = root.getAsJsonArray("minecraft:geometry");
      if (geometries == null || geometries.isEmpty()) {
         return null;
      }

      JsonObject geometry = geometries.get(0).getAsJsonObject();
      JsonObject description = geometry.getAsJsonObject("description");
      int textureWidth = description != null && description.has("texture_width") ? description.get("texture_width").getAsInt() : 64;
      int textureHeight = description != null && description.has("texture_height") ? description.get("texture_height").getAsInt() : 64;
      GeoModel model = new GeoModel();
      model.textureWidth = textureWidth;
      model.textureHeight = textureHeight;
      JsonArray bones = geometry.getAsJsonArray("bones");
      if (bones == null) {
         return model;
      }

      Map<String, GeoBone> byName = new HashMap<>();
      for (JsonElement element : bones) {
         GeoBone bone = parseBone(element.getAsJsonObject(), textureWidth, textureHeight);
         byName.put(bone.name, bone);
      }

      for (JsonElement element : bones) {
         JsonObject object = element.getAsJsonObject();
         GeoBone bone = byName.get(object.get("name").getAsString());
         if (object.has("parent")) {
            GeoBone parent = byName.get(object.get("parent").getAsString());
            if (parent != null) {
               parent.childBones.add(bone);
               bone.parent = parent;
            }
         } else {
            model.topLevelBones.add(bone);
         }
      }

      return model;
   }

   private static GeoBone parseBone(JsonObject object, int textureWidth, int textureHeight) {
      GeoBone bone = new GeoBone(object.get("name").getAsString());
      if (object.has("pivot")) {
         JsonArray pivot = object.getAsJsonArray("pivot");
         bone.rotationPointX = -pivot.get(0).getAsFloat();
         bone.rotationPointY = pivot.get(1).getAsFloat();
         bone.rotationPointZ = pivot.get(2).getAsFloat();
      }

      if (object.has("rotation")) {
         JsonArray rotation = object.getAsJsonArray("rotation");
         bone.setRotationX((float)Math.toRadians(-rotation.get(0).getAsFloat()));
         bone.setRotationY((float)Math.toRadians(-rotation.get(1).getAsFloat()));
         bone.setRotationZ((float)Math.toRadians(rotation.get(2).getAsFloat()));
      }

      if (object.has("cubes")) {
         for (JsonElement cube : object.getAsJsonArray("cubes")) {
            bone.childCubes.add(parseCube(cube.getAsJsonObject(), textureWidth, textureHeight));
         }
      }

      return bone;
   }

   private static GeoCube parseCube(JsonObject object, int textureWidth, int textureHeight) {
      float[] origin = parseFloatArray(object, "origin", new float[]{0.0F, 0.0F, 0.0F});
      float[] size = parseFloatArray(object, "size", new float[]{1.0F, 1.0F, 1.0F});
      float[] pivot = parseFloatArray(object, "pivot", origin.clone());
      float[] rotation = parseFloatArray(object, "rotation", new float[]{0.0F, 0.0F, 0.0F});
      float inflate = object.has("inflate") ? object.get("inflate").getAsFloat() : 0.0F;
      boolean mirror = object.has("mirror") && object.get("mirror").getAsBoolean();
      GeoCube cube = new GeoCube(size[0], size[1], size[2]);
      cube.pivot = new Vec3F(-pivot[0], pivot[1], pivot[2]);
      cube.rotation = new Vec3F(
         (float)Math.toRadians(-rotation[0]),
         (float)Math.toRadians(-rotation[1]),
         (float)Math.toRadians(rotation[2])
      );
      cube.inflate = inflate;
      cube.mirror = mirror;
      buildCubeQuads(cube, origin, size, inflate, object, textureWidth, textureHeight);
      return cube;
   }

   private static void buildCubeQuads(
      GeoCube cube, float[] origin, float[] size, float inflate, JsonObject object, int textureWidth, int textureHeight
   ) {
      float x = origin[0] - inflate;
      float y = origin[1] - inflate;
      float z = origin[2] - inflate;
      float sx = size[0] + inflate * 2.0F;
      float sy = size[1] + inflate * 2.0F;
      float sz = size[2] + inflate * 2.0F;
      float u = 0.0F;
      float v = 0.0F;
      JsonObject perFace = null;
      if (object.has("uv")) {
         JsonElement uv = object.get("uv");
         if (uv.isJsonArray()) {
            JsonArray array = uv.getAsJsonArray();
            u = array.get(0).getAsFloat();
            v = array.get(1).getAsFloat();
         } else if (uv.isJsonObject()) {
            perFace = uv.getAsJsonObject();
         }
      }

      float x0 = -(x + sx) / 16.0F;
      float y0 = y / 16.0F;
      float z0 = z / 16.0F;
      float x1 = -x / 16.0F;
      float y1 = (y + sy) / 16.0F;
      float z1 = (z + sz) / 16.0F;
      if (perFace != null) {
         cube.quads[0] = buildQuadPerFace(perFace, "west", x0, y0, z0, x0, y1, z1, -1.0F, 0.0F, 0.0F, textureWidth, textureHeight);
         cube.quads[1] = buildQuadPerFace(perFace, "east", x1, y0, z0, x1, y1, z1, 1.0F, 0.0F, 0.0F, textureWidth, textureHeight);
         cube.quads[2] = buildQuadPerFace(perFace, "down", x0, y0, z0, x1, y0, z1, 0.0F, -1.0F, 0.0F, textureWidth, textureHeight);
         cube.quads[3] = buildQuadPerFace(perFace, "up", x0, y1, z0, x1, y1, z1, 0.0F, 1.0F, 0.0F, textureWidth, textureHeight);
         cube.quads[4] = buildQuadPerFace(perFace, "north", x0, y0, z0, x1, y1, z0, 0.0F, 0.0F, -1.0F, textureWidth, textureHeight);
         cube.quads[5] = buildQuadPerFace(perFace, "south", x0, y0, z1, x1, y1, z1, 0.0F, 0.0F, 1.0F, textureWidth, textureHeight);
      } else {
         float tw = textureWidth;
         float th = textureHeight;
         cube.quads[0] = buildQuadBox(x0, y0, z0, x0, y1, z1, -1.0F, 0.0F, 0.0F, u, v, sz, sy, sx, tw, th, "west");
         cube.quads[1] = buildQuadBox(x1, y0, z0, x1, y1, z1, 1.0F, 0.0F, 0.0F, u, v, sz, sy, sx, tw, th, "east");
         cube.quads[2] = buildQuadBox(x0, y0, z0, x1, y0, z1, 0.0F, -1.0F, 0.0F, u, v, sz, sy, sx, tw, th, "down");
         cube.quads[3] = buildQuadBox(x0, y1, z0, x1, y1, z1, 0.0F, 1.0F, 0.0F, u, v, sz, sy, sx, tw, th, "up");
         cube.quads[4] = buildQuadBox(x0, y0, z0, x1, y1, z0, 0.0F, 0.0F, -1.0F, u, v, sz, sy, sx, tw, th, "north");
         cube.quads[5] = buildQuadBox(x0, y0, z1, x1, y1, z1, 0.0F, 0.0F, 1.0F, u, v, sz, sy, sx, tw, th, "south");
      }
   }

   private static GeoQuad buildQuadPerFace(
      JsonObject faces,
      String face,
      float x0,
      float y0,
      float z0,
      float x1,
      float y1,
      float z1,
      float nx,
      float ny,
      float nz,
      int textureWidth,
      int textureHeight
   ) {
      if (!faces.has(face)) {
         return null;
      }

      JsonObject object = faces.getAsJsonObject(face);
      JsonArray uv = object.getAsJsonArray("uv");
      JsonArray uvSize = object.getAsJsonArray("uv_size");
      float u = uv.get(0).getAsFloat() / textureWidth;
      float v = uv.get(1).getAsFloat() / textureHeight;
      float su = uvSize.get(0).getAsFloat() / textureWidth;
      float sv = uvSize.get(1).getAsFloat() / textureHeight;
      return new GeoQuad(buildFaceVertices(face, x0, y0, z0, x1, y1, z1, u, v, su, sv), nx, ny, nz);
   }

   private static GeoQuad buildQuadBox(
      float x0,
      float y0,
      float z0,
      float x1,
      float y1,
      float z1,
      float nx,
      float ny,
      float nz,
      float u,
      float v,
      float depth,
      float height,
      float width,
      float textureWidth,
      float textureHeight,
      String face
   ) {
      float ou;
      float ov;
      float su;
      float sv;
      switch (face) {
         case "north" -> {
            ou = (u + depth + width) / textureWidth;
            ov = (v + depth) / textureHeight;
            su = width / textureWidth;
            sv = height / textureHeight;
         }
         case "south" -> {
            ou = (u + depth + width + depth) / textureWidth;
            ov = (v + depth) / textureHeight;
            su = width / textureWidth;
            sv = height / textureHeight;
         }
         case "east" -> {
            ou = u / textureWidth;
            ov = (v + depth) / textureHeight;
            su = depth / textureWidth;
            sv = height / textureHeight;
         }
         case "west" -> {
            ou = (u + depth + width) / textureWidth;
            ov = (v + depth) / textureHeight;
            su = depth / textureWidth;
            sv = height / textureHeight;
         }
         case "up" -> {
            ou = (u + depth) / textureWidth;
            ov = v / textureHeight;
            su = width / textureWidth;
            sv = depth / textureHeight;
         }
         case "down" -> {
            ou = (u + depth + width) / textureWidth;
            ov = v / textureHeight;
            su = width / textureWidth;
            sv = depth / textureHeight;
         }
         default -> {
            ou = 0.0F;
            ov = 0.0F;
            su = 0.0F;
            sv = 0.0F;
         }
      }

      return new GeoQuad(buildFaceVertices(face, x0, y0, z0, x1, y1, z1, ou, ov, su, sv), nx, ny, nz);
   }

   private static GeoVertex[] buildFaceVertices(
      String face, float x0, float y0, float z0, float x1, float y1, float z1, float u, float v, float su, float sv
   ) {
      GeoVertex[] vertices = new GeoVertex[4];
      float u1 = u + su;
      float v1 = v + sv;
      switch (face) {
         case "north" -> {
            vertices[0] = new GeoVertex(x1, y1, z0, u, v);
            vertices[1] = new GeoVertex(x0, y1, z0, u1, v);
            vertices[2] = new GeoVertex(x0, y0, z0, u1, v1);
            vertices[3] = new GeoVertex(x1, y0, z0, u, v1);
         }
         case "south" -> {
            vertices[0] = new GeoVertex(x0, y1, z1, u, v);
            vertices[1] = new GeoVertex(x1, y1, z1, u1, v);
            vertices[2] = new GeoVertex(x1, y0, z1, u1, v1);
            vertices[3] = new GeoVertex(x0, y0, z1, u, v1);
         }
         case "east" -> {
            vertices[0] = new GeoVertex(x1, y1, z1, u, v);
            vertices[1] = new GeoVertex(x1, y1, z0, u1, v);
            vertices[2] = new GeoVertex(x1, y0, z0, u1, v1);
            vertices[3] = new GeoVertex(x1, y0, z1, u, v1);
         }
         case "west" -> {
            vertices[0] = new GeoVertex(x0, y1, z0, u, v);
            vertices[1] = new GeoVertex(x0, y1, z1, u1, v);
            vertices[2] = new GeoVertex(x0, y0, z1, u1, v1);
            vertices[3] = new GeoVertex(x0, y0, z0, u, v1);
         }
         case "up" -> {
            vertices[0] = new GeoVertex(x0, y1, z0, u, v);
            vertices[1] = new GeoVertex(x0, y1, z1, u, v1);
            vertices[2] = new GeoVertex(x1, y1, z1, u1, v1);
            vertices[3] = new GeoVertex(x1, y1, z0, u1, v);
         }
         case "down" -> {
            vertices[0] = new GeoVertex(x1, y0, z0, u, v);
            vertices[1] = new GeoVertex(x1, y0, z1, u, v1);
            vertices[2] = new GeoVertex(x0, y0, z1, u1, v1);
            vertices[3] = new GeoVertex(x0, y0, z0, u1, v);
         }
         default -> {
         }
      }

      return vertices;
   }

   private static float[] parseFloatArray(JsonObject object, String key, float[] fallback) {
      if (!object.has(key)) {
         return fallback;
      }

      JsonArray array = object.getAsJsonArray(key);
      float[] values = new float[array.size()];
      for (int i = 0; i < array.size(); i++) {
         values[i] = array.get(i).getAsFloat();
      }

      return values;
   }
}
