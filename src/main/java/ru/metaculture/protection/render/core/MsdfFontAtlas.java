package ru.metaculture.protection;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.lwjgl.opengl.GL11;
import org.lwjgl.stb.STBImage;
import org.lwjgl.system.MemoryStack;

final class MsdfFontAtlas {
   private final String text;
   private final String text2;
   private final Map<Integer, MsdfFontAtlas.MsdfFontAtlasState> valuesByKey;
   private final Map<Long, Float> valuesByKey2;
   private volatile int intValue;
   private final int intValue2;
   private final int intValue3;
   private final float floatValue;
   private final float floatValue2;
   private final float floatValue3;
   private final float floatValue4;
   private final float floatValue5;

   private MsdfFontAtlas(
      String string,
      String string2,
      int i,
      int j,
      int k,
      float f,
      float g,
      float h,
      float l,
      float m,
      Map<Integer, MsdfFontAtlas.MsdfFontAtlasState> map,
      Map<Long, Float> map2
   ) {
      this.text = string;
      this.text2 = string2;
      this.intValue = i;
      this.intValue2 = j;
      this.intValue3 = k;
      this.floatValue = f;
      this.floatValue2 = g;
      this.floatValue3 = h;
      this.floatValue4 = l;
      this.floatValue5 = m;
      this.valuesByKey = map;
      this.valuesByKey2 = map2;
   }

   static MsdfFontAtlas resolve(RenderEngine renderEngine, String string, String string2) {
      Objects.requireNonNull(renderEngine, "backend");
      Objects.requireNonNull(string, "jsonResourcePath");
      Objects.requireNonNull(string2, "textureResourcePath");
      String text = ResourceUtils.resolve(string);
      JsonObject jsonObject = JsonParser.parseString(text).getAsJsonObject();
      JsonObject jsonObject2 = jsonObject.getAsJsonObject("atlas");
      if (jsonObject2 == null) {
         throw new IllegalStateException("Missing 'atlas' section in MSDF font: " + string);
      } else {
         int intValue = jsonObject2.get("width").getAsInt();
         int intValue2 = jsonObject2.get("height").getAsInt();
         if (intValue > 0 && intValue2 > 0) {
            float floatValue = jsonObject2.has("distanceRange") ? jsonObject2.get("distanceRange").getAsFloat() : 6.0F;
            JsonObject jsonObject3 = jsonObject.getAsJsonObject("metrics");
            if (jsonObject3 == null) {
               throw new IllegalStateException("Missing 'metrics' section in MSDF font: " + string);
            } else {
               float floatValue2 = jsonObject3.has("emSize") ? jsonObject3.get("emSize").getAsFloat() : 1.0F;
               float floatValue3 = jsonObject3.has("lineHeight") ? jsonObject3.get("lineHeight").getAsFloat() : floatValue2;
               float floatValue4 = jsonObject3.has("ascender") ? jsonObject3.get("ascender").getAsFloat() : floatValue3;
               float floatValue5 = jsonObject3.has("descender") ? jsonObject3.get("descender").getAsFloat() : 0.0F;
               float floatValue6 = Math.abs(floatValue5);
               HashMap hashMap = new HashMap();
               JsonArray jsonArray = jsonObject.getAsJsonArray("glyphs");
               if (jsonArray != null) {
                  for (JsonElement jsonElement : jsonArray) {
                     JsonObject jsonObject4 = jsonElement.getAsJsonObject();
                     int intValue3 = jsonObject4.get("unicode").getAsInt();
                     float floatValue7 = jsonObject4.has("advance") ? jsonObject4.get("advance").getAsFloat() : 0.0F;
                     JsonObject jsonObject5 = jsonObject4.has("planeBounds") ? jsonObject4.getAsJsonObject("planeBounds") : null;
                     JsonObject jsonObject6 = jsonObject4.has("atlasBounds") ? jsonObject4.getAsJsonObject("atlasBounds") : null;
                     MsdfFontAtlas.MsdfFontAtlasState msdfFontAtlasState;
                     if (jsonObject5 != null && jsonObject6 != null) {
                        float floatValue8 = jsonObject5.get("left").getAsFloat();
                        float floatValue9 = jsonObject5.get("bottom").getAsFloat();
                        float floatValue10 = jsonObject5.get("right").getAsFloat();
                        float floatValue11 = jsonObject5.get("top").getAsFloat();
                        float floatValue12 = jsonObject6.get("left").getAsFloat();
                        float floatValue13 = jsonObject6.get("bottom").getAsFloat();
                        float floatValue14 = jsonObject6.get("right").getAsFloat();
                        float floatValue15 = jsonObject6.get("top").getAsFloat();
                        msdfFontAtlasState = new MsdfFontAtlas.MsdfFontAtlasState(floatValue7, floatValue8, floatValue9, floatValue10, floatValue11, floatValue12, floatValue13, floatValue14, floatValue15, intValue, intValue2);
                     } else {
                        msdfFontAtlasState = new MsdfFontAtlas.MsdfFontAtlasState(floatValue7);
                     }

                     hashMap.put(intValue3, msdfFontAtlasState);
                  }
               }

               HashMap hashMap2 = new HashMap();
               JsonArray jsonArray2 = jsonObject.getAsJsonArray("kerning");
               if (jsonArray2 != null) {
                  for (JsonElement jsonElement2 : jsonArray2) {
                     JsonObject jsonObject7 = jsonElement2.getAsJsonObject();
                     int intValue4 = jsonObject7.get("unicode1").getAsInt();
                     int intValue5 = jsonObject7.get("unicode2").getAsInt();
                     float floatValue16 = jsonObject7.has("advance") ? jsonObject7.get("advance").getAsFloat() : 0.0F;
                     hashMap2.put(compute(intValue4, intValue5), floatValue16);
                  }
               }

               MsdfFontAtlas.MsdfFontAtlasBounds msdfFontAtlasBounds = resolve2(renderEngine, string2);
               return new MsdfFontAtlas(string, string2, msdfFontAtlasBounds.textureId, msdfFontAtlasBounds.width, msdfFontAtlasBounds.height, floatValue, floatValue2, floatValue3, floatValue4, floatValue6, hashMap, hashMap2);
            }
         } else {
            throw new IllegalStateException("Invalid MSDF atlas dimensions in font: " + string);
         }
      }
   }

   void invoke(RenderEngine renderEngine2) {
      Objects.requireNonNull(renderEngine2, "backend");
      int intValue6 = this.intValue;

      try {
         MsdfFontAtlas.MsdfFontAtlasBounds msdfFontAtlasBounds2 = resolve2(renderEngine2, this.text2);
         if (msdfFontAtlasBounds2.width != this.intValue2 || msdfFontAtlasBounds2.height != this.intValue3) {
            if (msdfFontAtlasBounds2.textureId > 0) {
               GL11.glDeleteTextures(msdfFontAtlasBounds2.textureId);
            }

            return;
         }

         this.intValue = msdfFontAtlasBounds2.textureId;
      } catch (Throwable exception) {
         return;
      }

      if (intValue6 > 0 && intValue6 != this.intValue) {
         try {
            GL11.glDeleteTextures(intValue6);
         } catch (Throwable exception2) {
         }
      }
   }

   private static MsdfFontAtlas.MsdfFontAtlasBounds resolve2(RenderEngine renderEngine3, String string) {
      ByteBuffer byteBuffer = ResourceUtils.resolve2(string);
      MemoryStack memoryStack = MemoryStack.stackPush();

      MsdfFontAtlas.MsdfFontAtlasBounds msdfFontAtlasBounds3;
      try {
         IntBuffer intBuffer = memoryStack.mallocInt(1);
         IntBuffer intBuffer2 = memoryStack.mallocInt(1);
         IntBuffer intBuffer3 = memoryStack.mallocInt(1);
         ByteBuffer byteBuffer2 = STBImage.stbi_load_from_memory(byteBuffer, intBuffer, intBuffer2, intBuffer3, 4);
         if (byteBuffer2 == null) {
            throw new IllegalStateException("Failed to load MSDF atlas '" + string + "': " + STBImage.stbi_failure_reason());
         }

         try {
            int intValue7 = intBuffer.get(0);
            int intValue8 = intBuffer2.get(0);
            int intValue9 = renderEngine3.compute3(intValue7, intValue8, byteBuffer2);
            msdfFontAtlasBounds3 = new MsdfFontAtlas.MsdfFontAtlasBounds(intValue9, intValue7, intValue8);
         } finally {
            STBImage.stbi_image_free(byteBuffer2);
         }
      } catch (Throwable exception3) {
         if (memoryStack != null) {
            try {
               memoryStack.close();
            } catch (Throwable exception4) {
               exception3.addSuppressed(exception4);
            }
         }

         throw exception3;
      }

      if (memoryStack != null) {
         memoryStack.close();
      }

      return msdfFontAtlasBounds3;
   }

   int getIntValue() {
      return this.intValue;
   }

   boolean check() {
      int intValue10 = this.intValue;
      return intValue10 > 0 && GL11.glIsTexture(intValue10);
   }

   int getIntValue2() {
      return this.intValue2;
   }

   int getIntValue3() {
      return this.intValue3;
   }

   float getFloatValue() {
      return this.floatValue;
   }

   float getFloatValue2() {
      return this.floatValue2;
   }

   float getFloatValue3() {
      return this.floatValue3;
   }

   float getFloatValue4() {
      return this.floatValue4;
   }

   float getFloatValue5() {
      return this.floatValue5;
   }

   MsdfFontAtlas.MsdfFontAtlasState resolve3(int i) {
      return this.valuesByKey.get(i);
   }

   float measure(int i, int j) {
      return this.valuesByKey2.getOrDefault(compute(i, j), 0.0F);
   }

   private static long compute(int i, int j) {
      return (long)i << 32 | j & 4294967295L;
   }

   static float measure2(float f) {
      return measure3(f, 0.0F, 1.0F);
   }

   static float measure3(float f, float g, float h) {
      return !Float.isFinite(f) ? g : Math.max(g, Math.min(h, f));
   }

   static final class MsdfFontAtlasState {
      final float floatValue;
      final boolean flag;
      final float floatValue2;
      final float floatValue3;
      final float floatValue4;
      final float floatValue5;
      final float floatValue6;
      final float floatValue7;
      final float floatValue8;
      final float floatValue9;

      MsdfFontAtlasState(float f) {
         this.floatValue = f;
         this.flag = false;
         this.floatValue2 = 0.0F;
         this.floatValue3 = 0.0F;
         this.floatValue4 = 0.0F;
         this.floatValue5 = 0.0F;
         this.floatValue6 = 0.0F;
         this.floatValue7 = 0.0F;
         this.floatValue8 = 0.0F;
         this.floatValue9 = 0.0F;
      }

      MsdfFontAtlasState(float f, float g, float h, float i, float j, float k, float l, float m, float n, int o, int p) {
         this.floatValue = f;
         this.flag = true;
         this.floatValue2 = g;
         this.floatValue3 = h;
         this.floatValue4 = i;
         this.floatValue5 = j;
         float floatValue17 = 1.0F / Math.max(1.0F, (float)o);
         float floatValue18 = 1.0F / Math.max(1.0F, (float)p);
         float floatValue19 = floatValue17 * 0.5F;
         float floatValue20 = floatValue18 * 0.5F;
         float floatValue21 = MsdfFontAtlas.measure2(Math.min(k, m) * floatValue17);
         float floatValue22 = MsdfFontAtlas.measure2(Math.max(k, m) * floatValue17);
         float floatValue23 = MsdfFontAtlas.measure2(Math.min(l, n) * floatValue18);
         float floatValue24 = MsdfFontAtlas.measure2(Math.max(l, n) * floatValue18);
         if (floatValue22 - floatValue21 > floatValue17) {
            floatValue21 += floatValue19;
            floatValue22 -= floatValue19;
         }

         if (floatValue24 - floatValue23 > floatValue18) {
            floatValue23 += floatValue20;
            floatValue24 -= floatValue20;
         }

         this.floatValue6 = MsdfFontAtlas.measure3(floatValue21, 0.0F, 1.0F);
         this.floatValue8 = MsdfFontAtlas.measure3(floatValue22, 0.0F, 1.0F);
         float floatValue25 = MsdfFontAtlas.measure3(floatValue23, 0.0F, 1.0F);
         float floatValue26 = MsdfFontAtlas.measure3(floatValue24, 0.0F, 1.0F);
         this.floatValue7 = 1.0F - floatValue25;
         this.floatValue9 = 1.0F - floatValue26;
      }
   }

   record MsdfFontAtlasBounds(int textureId, int width, int height) {
   }
}
