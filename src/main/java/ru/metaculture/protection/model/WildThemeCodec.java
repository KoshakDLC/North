package ru.metaculture.protection;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Base64;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import org.json.JSONArray;
import org.json.JSONObject;

public final class WildThemeCodec {
   public static final String WILDTHEME = "WildTheme::";

   private WildThemeCodec() {
   }

   public static String resolve(ShaderNode shaderNode) {
      String text = resolve3(shaderNode).toString();
      byte[] byteValues = resolve7(text.getBytes(StandardCharsets.UTF_8));
      String text2 = Base64.getUrlEncoder().withoutPadding().encodeToString(byteValues);
      return "WildTheme::" + resolve9(text) + "::" + text2;
   }

   public static ShaderNode resolve2(String string, ShaderNodeRegistry shaderNodeRegistry) {
      if (string != null && string.startsWith("WildTheme::")) {
         String text3 = string.substring("WildTheme::".length());
         int intValue = text3.indexOf("::");
         String text4 = intValue >= 0 ? text3.substring(intValue + 2) : text3;
         byte[] byteValues2 = Base64.getUrlDecoder().decode(text4);
         String text5 = new String(resolve8(byteValues2), StandardCharsets.UTF_8);
         return resolve4(new JSONObject(text5), shaderNodeRegistry);
      } else {
         throw new IllegalArgumentException("Invalid WildTheme payload");
      }
   }

   public static JSONObject resolve3(ShaderNode shaderNode2) {
      JSONObject jsonObject = new JSONObject();
      jsonObject.put("version", 3);
      jsonObject.put("target", shaderNode2.getPreview());
      jsonObject.put("metadata", resolve5(shaderNode2.getShaderTemplate()));
      JSONArray jsonArray = new JSONArray();

      for (ShaderNodeKind shaderNodeKind : shaderNode2.resolve2()) {
         JSONObject jsonObject2 = new JSONObject();
         jsonObject2.put("id", shaderNodeKind.getText());
         jsonObject2.put("kind", shaderNodeKind.getText2());
         jsonObject2.put("x", shaderNodeKind.getFloatValue());
         jsonObject2.put("y", shaderNodeKind.getFloatValue2());
         JSONObject jsonObject3 = new JSONObject();

         for (Entry entry : shaderNodeKind.getValuesByKey().entrySet()) {
            jsonObject3.put((String)entry.getKey(), entry.getValue());
         }

         jsonObject2.put("values", jsonObject3);
         JSONObject jsonObject4 = new JSONObject();

         for (Entry entry2 : shaderNodeKind.getValuesByKey2().entrySet()) {
            jsonObject4.put((String)entry2.getKey(), entry2.getValue());
         }

         jsonObject2.put("textValues", jsonObject4);
         jsonArray.put(jsonObject2);
      }

      JSONArray jsonArray2 = new JSONArray();

      for (ShaderConnection shaderConnection : shaderNode2.getItems()) {
         JSONObject jsonObject5 = new JSONObject();
         jsonObject5.put("fromNode", shaderConnection.getText());
         jsonObject5.put("fromPin", shaderConnection.getText2());
         jsonObject5.put("toNode", shaderConnection.getText3());
         jsonObject5.put("toPin", shaderConnection.getText4());
         jsonArray2.put(jsonObject5);
      }

      jsonObject.put("nodes", jsonArray);
      jsonObject.put("connections", jsonArray2);
      return jsonObject;
   }

   public static ShaderNode resolve4(JSONObject jSONObject, ShaderNodeRegistry shaderNodeRegistry2) {
      ShaderNode shaderNode3 = new ShaderNode();
      String text6 = jSONObject.optString("target", "");
      if (!text6.isBlank()) {
         shaderNode3.invoke2(text6);
      }

      shaderNode3.invoke(resolve6(jSONObject.optJSONObject("metadata"), jSONObject));
      JSONArray jsonArray3 = jSONObject.optJSONArray("nodes");
      if (jsonArray3 != null) {
         for (int intValue2 = 0; intValue2 < jsonArray3.length(); intValue2++) {
            JSONObject jsonObject6 = jsonArray3.getJSONObject(intValue2);
            ShaderNodeKind shaderNodeKind2 = new ShaderNodeKind(
               jsonObject6.getString("id"), jsonObject6.getString("kind"), (float)jsonObject6.optDouble("x", 0.0), (float)jsonObject6.optDouble("y", 0.0)
            );
            JSONObject jsonObject7 = jsonObject6.optJSONObject("values");
            if (jsonObject7 != null) {
               for (String text7 : jsonObject7.keySet()) {
                  shaderNodeKind2.invoke2(text7, (float)jsonObject7.optDouble(text7, 0.0));
               }
            }

            JSONObject jsonObject8 = jsonObject6.optJSONObject("textValues");
            if (jsonObject8 != null) {
               for (String text8 : jsonObject8.keySet()) {
                  shaderNodeKind2.invoke3(text8, jsonObject8.optString(text8, ""));
               }
            }

            shaderNode3.invoke3(shaderNodeKind2, shaderNodeRegistry2);
         }
      }

      JSONArray jsonArray4 = jSONObject.optJSONArray("connections");
      if (jsonArray4 != null) {
         for (int intValue3 = 0; intValue3 < jsonArray4.length(); intValue3++) {
            JSONObject jsonObject9 = jsonArray4.getJSONObject(intValue3);
            shaderNode3.check2(jsonObject9.getString("fromNode"), jsonObject9.getString("fromPin"), jsonObject9.getString("toNode"), jsonObject9.getString("toPin"), shaderNodeRegistry2);
         }
      }

      return shaderNode3;
   }

   public static JSONObject resolve5(ShaderTemplate shaderTemplate) {
      ShaderTemplate shaderTemplate2 = shaderTemplate == null ? new ShaderTemplate() : shaderTemplate;
      JSONObject jsonObject10 = new JSONObject();
      jsonObject10.put("name", shaderTemplate2.getText());
      jsonObject10.put("author", shaderTemplate2.getText2());
      jsonObject10.put("description", shaderTemplate2.getText3());
      jsonObject10.put("complexity", shaderTemplate2.getCustom());
      jsonObject10.put("source", shaderTemplate2.getLocal());
      jsonObject10.put("shapeSource", shaderTemplate2.getHostRectangle());
      jsonObject10.put("createdAt", shaderTemplate2.getTimestamp());
      jsonObject10.put("updatedAt", shaderTemplate2.getTimestamp2());
      jsonObject10.put("favorite", shaderTemplate2.isFlag());
      jsonObject10.put("previewThumbnail", shaderTemplate2.getText4());
      return jsonObject10;
   }

   public static ShaderTemplate resolve6(JSONObject jSONObject, JSONObject jSONObject2) {
      ShaderTemplate shaderTemplate3 = new ShaderTemplate();
      JSONObject jsonObject11 = jSONObject == null ? new JSONObject() : jSONObject;
      JSONObject jsonObject12 = jSONObject2 == null ? new JSONObject() : jSONObject2;
      shaderTemplate3.setText(jsonObject11.optString("name", jsonObject12.optString("displayName", "")));
      shaderTemplate3.setText2(jsonObject11.optString("author", jsonObject12.optString("author", "")));
      shaderTemplate3.setText3(jsonObject11.optString("description", jsonObject12.optString("description", "")));
      shaderTemplate3.invoke2(jsonObject11.optString("complexity", jsonObject12.optString("complexity", "Custom")));
      shaderTemplate3.invoke3(jsonObject11.optString("source", jsonObject12.optString("source", "local")));
      shaderTemplate3.invoke4(jsonObject11.optString("shapeSource", jsonObject12.optString("shapeSource", "Host Rectangle")));
      shaderTemplate3.setTimestamp(jsonObject11.optLong("createdAt", jsonObject12.optLong("createdAt", 0L)));
      shaderTemplate3.setTimestamp2(jsonObject11.optLong("updatedAt", jsonObject12.optLong("updatedAt", 0L)));
      shaderTemplate3.setFlag(jsonObject11.optBoolean("favorite", jsonObject12.optBoolean("favorite", false)));
      shaderTemplate3.setText4(jsonObject11.optString("previewThumbnail", jsonObject12.optString("previewThumbnail", "")));
      return shaderTemplate3;
   }

   private static byte[] resolve7(byte[] bs) {
      try {
         ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

         try (GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream)) {
            gzipOutputStream.write(bs);
         }

         return byteArrayOutputStream.toByteArray();
      } catch (Exception exception) {
         throw new IllegalStateException("GZIP export failed", exception);
      }
   }

   private static byte[] resolve8(byte[] bs) {
      try {
         ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream();

         try (GZIPInputStream gzipInputStream = new GZIPInputStream(new ByteArrayInputStream(bs))) {
            gzipInputStream.transferTo(byteArrayOutputStream2);
         }

         return byteArrayOutputStream2.toByteArray();
      } catch (Exception exception2) {
         throw new IllegalArgumentException("GZIP import failed", exception2);
      }
   }

   private static String resolve9(String string) {
      try {
         MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
         byte[] byteValues3 = messageDigest.digest(string.getBytes(StandardCharsets.UTF_8));
         StringBuilder stringBuilder = new StringBuilder(16);

         for (int intValue4 = 0; intValue4 < 8; intValue4++) {
            stringBuilder.append(String.format("%02x", byteValues3[intValue4] & 255));
         }

         return stringBuilder.toString();
      } catch (Exception exception3) {
         return "0000000000000000";
      }
   }
}
