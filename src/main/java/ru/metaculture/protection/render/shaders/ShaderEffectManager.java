package ru.metaculture.protection;

import com.mojang.blaze3d.opengl.GlStateManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import net.minecraft.entity.Entity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public final class ShaderEffectManager {
   private static final ShaderEffectManager INSTANCE = new ShaderEffectManager();
   static final float[] FLOATS = new float[]{0.0F, 0.0F, 0.0F, 0.0F};
   private static final int INT_VALUE = 48;
   private static final long TIMESTAMP = 33L;
   private static final int INT_VALUE_2 = 6;
   private static final int INT_VALUE_3 = 7;
   private final Map<String, ShaderEffectManager.ShaderEffectManagerEntityData> valuesByKey = new LinkedHashMap<>();
   private final Map<String, float[]> valuesByKey2 = new HashMap<>();
   private final Map<String, Integer> valuesByKey3 = new HashMap<>();
   private final Map<String, ShaderEffectManager.ShaderEffectManagerState> valuesByKey4 = new LinkedHashMap<>(16, 0.75F, true);
   private final ShaderEffectManager.ShaderEffectManagerResources shaderEffectManagerResources = new ShaderEffectManager.ShaderEffectManagerResources();
   private final ShaderEffectManager.ShaderEffectManagerResources2 shaderEffectManagerResources2 = new ShaderEffectManager.ShaderEffectManagerResources2();
   private boolean flag;

   private ShaderEffectManager() {
   }

   public static ShaderEffectManager getINSTANCE() {
      return INSTANCE;
   }

   public synchronized void invoke(ShaderNodeRegistry shaderNodeRegistry) {
      if (shaderNodeRegistry != null) {
         if (!this.flag) {
            this.invoke13();
            ShaderSourceBuilder.setFunction(this::resolve4);
            this.flag = true;
         }

         for (ShaderEffectManager.ShaderEffectManagerEntityData shaderEffectManagerEntityData : this.valuesByKey.values()) {
            if (shaderNodeRegistry.resolve(shaderEffectManagerEntityData.id()) == null) {
               shaderNodeRegistry.invoke(shaderEffectManagerEntityData.toNodeDefinition());
            }
         }

         invoke14(shaderNodeRegistry);
      }
   }

   public synchronized void invoke2(ShaderEffectManager.ShaderEffectManagerEntityData shaderEffectManagerEntityData2) {
      if (shaderEffectManagerEntityData2 != null) {
         this.valuesByKey.put(shaderEffectManagerEntityData2.id(), shaderEffectManagerEntityData2);
      }
   }

   public synchronized ShaderEffectManager.ShaderEffectManagerEntityData resolve(String string) {
      return this.valuesByKey.get(string);
   }

   public synchronized Collection<ShaderEffectManager.ShaderEffectManagerEntityData> resolve2() {
      return Collections.unmodifiableCollection(new ArrayList<>(this.valuesByKey.values()));
   }

   public synchronized List<ShaderEffectManager.ShaderEffectManagerEntityData> resolve3(ShaderSurface shaderSurface) {
      ShaderSurface shaderSurface2 = shaderSurface == null ? ShaderSurface.PREVIEW_ONLY : shaderSurface.resolve();
      ArrayList arrayList = new ArrayList();

      for (ShaderEffectManager.ShaderEffectManagerEntityData shaderEffectManagerEntityData3 : this.valuesByKey.values()) {
         if (shaderEffectManagerEntityData3.target().resolve() == shaderSurface2) {
            arrayList.add(shaderEffectManagerEntityData3);
         }
      }

      return arrayList;
   }

   public ShaderEffectManager.ShaderEffectManagerResources getShaderEffectManagerResources() {
      return this.shaderEffectManagerResources;
   }

   public ShaderEffectManager.ShaderEffectManagerResources2 getShaderEffectManagerResources2() {
      return this.shaderEffectManagerResources2;
   }

   public synchronized String resolve4(ShaderSurface shaderSurface3) {
      if (shaderSurface3 != null && shaderSurface3 != ShaderSurface.PREVIEW_ONLY) {
         StringBuilder stringBuilder = new StringBuilder();

         for (ShaderEffectManager.ShaderEffectManagerEntityData shaderEffectManagerEntityData4 : this.valuesByKey.values()) {
            if (shaderEffectManagerEntityData4.target().resolve() == shaderSurface3 && !shaderEffectManagerEntityData4.glslPreamble().isBlank()) {
               stringBuilder.append(shaderEffectManagerEntityData4.glslPreamble());
               if (!shaderEffectManagerEntityData4.glslPreamble().endsWith("\n")) {
                  stringBuilder.append('\n');
               }
            }
         }

         return stringBuilder.toString();
      } else {
         return "";
      }
   }

   public boolean check(ShaderNodeDefinition shaderNodeDefinition, String string, ShaderNodeDefinition shaderNodeDefinition2, String string2) {
      return this.resolve5(shaderNodeDefinition, string, shaderNodeDefinition2, string2) == null;
   }

   public String resolve5(ShaderNodeDefinition shaderNodeDefinition3, String string, ShaderNodeDefinition shaderNodeDefinition4, String string2) {
      if (shaderNodeDefinition3 != null && shaderNodeDefinition4 != null) {
         ShaderPin shaderPin = shaderNodeDefinition3.resolve2(string);
         if (shaderPin == null) {
            return shaderNodeDefinition3.getText() + " has no output slot '" + string + "'";
         } else {
            ShaderPin shaderPin2 = shaderNodeDefinition4.resolve(string2);
            if (shaderPin2 == null) {
               return shaderNodeDefinition4.getText() + " has no input slot '" + string2 + "'";
            } else {
               ShaderEffectManager.ShaderEffectManagerState2 shaderEffectManagerState2 = ShaderEffectManager.ShaderEffectManagerState2.resolve2(shaderPin.type());
               ShaderEffectManager.ShaderEffectManagerState2 shaderEffectManagerState22 = ShaderEffectManager.ShaderEffectManagerState2.resolve2(shaderPin2.type());
               return shaderPin.type() != shaderPin2.type() ? "type mismatch: " + shaderEffectManagerState2.getText() + " -> " + shaderEffectManagerState22.getText() : null;
            }
         }
      } else {
         return "unknown node definition";
      }
   }

   public JSONObject resolve6(ShaderNodeDefinition shaderNodeDefinition5) {
      JSONObject jsonObject = new JSONObject();
      if (shaderNodeDefinition5 == null) {
         return jsonObject;
      } else {
         jsonObject.put("id", shaderNodeDefinition5.getText());
         JSONArray jsonArray = new JSONArray();

         for (ShaderPin shaderPin3 : shaderNodeDefinition5.getItems()) {
            jsonArray.put(resolve9(shaderPin3));
         }

         JSONArray jsonArray2 = new JSONArray();

         for (ShaderPin shaderPin4 : shaderNodeDefinition5.getItems2()) {
            jsonArray2.put(resolve9(shaderPin4));
         }

         jsonObject.put("inputs", jsonArray);
         jsonObject.put("outputs", jsonArray2);
         return jsonObject;
      }
   }

   public JSONArray resolve7(ShaderNode shaderNode, ShaderNodeRegistry shaderNodeRegistry2) {
      JSONArray jsonArray3 = new JSONArray();
      if (shaderNode != null && shaderNodeRegistry2 != null) {
         for (ShaderConnection shaderConnection : shaderNode.getItems()) {
            ShaderNodeKind shaderNodeKind = shaderNode.resolve3(shaderConnection.getText());
            ShaderNodeKind shaderNodeKind2 = shaderNode.resolve3(shaderConnection.getText3());
            if (shaderNodeKind != null && shaderNodeKind2 != null) {
               ShaderNodeDefinition shaderNodeDefinition6 = shaderNodeRegistry2.resolve(shaderNodeKind.getText2());
               ShaderNodeDefinition shaderNodeDefinition7 = shaderNodeRegistry2.resolve(shaderNodeKind2.getText2());
               if (this.check(shaderNodeDefinition6, shaderConnection.getText2(), shaderNodeDefinition7, shaderConnection.getText4())) {
                  JSONObject jsonObject2 = new JSONObject();
                  jsonObject2.put("from", shaderConnection.getText());
                  jsonObject2.put("fromSlot", shaderConnection.getText2());
                  jsonObject2.put("to", shaderConnection.getText3());
                  jsonObject2.put("toSlot", shaderConnection.getText4());
                  jsonObject2.put("type", shaderNodeDefinition6.resolve2(shaderConnection.getText2()).type().getText());
                  jsonArray3.put(jsonObject2);
               }
            }
         }

         return jsonArray3;
      } else {
         return jsonArray3;
      }
   }

   public int compute(ShaderNode shaderNode2, JSONArray jSONArray, ShaderNodeRegistry shaderNodeRegistry3) {
      if (shaderNode2 != null && jSONArray != null && shaderNodeRegistry3 != null) {
         int intValue = 0;

         for (int intValue2 = 0; intValue2 < jSONArray.length(); intValue2++) {
            JSONObject jsonObject3 = jSONArray.optJSONObject(intValue2);
            if (jsonObject3 != null) {
               String text = jsonObject3.optString("from", "");
               String text2 = jsonObject3.optString("fromSlot", "");
               String text3 = jsonObject3.optString("to", "");
               String text4 = jsonObject3.optString("toSlot", "");
               ShaderNodeKind shaderNodeKind3 = shaderNode2.resolve3(text);
               ShaderNodeKind shaderNodeKind4 = shaderNode2.resolve3(text3);
               if (shaderNodeKind3 != null && shaderNodeKind4 != null) {
                  ShaderNodeDefinition shaderNodeDefinition8 = shaderNodeRegistry3.resolve(shaderNodeKind3.getText2());
                  ShaderNodeDefinition shaderNodeDefinition9 = shaderNodeRegistry3.resolve(shaderNodeKind4.getText2());
                  if (this.check(shaderNodeDefinition8, text2, shaderNodeDefinition9, text4)) {
                     String text5 = jsonObject3.optString("type", "");
                     if ((text5.isBlank() || text5.equals(shaderNodeDefinition8.resolve2(text2).type().getText()))
                        && shaderNode2.check2(text, text2, text3, text4, shaderNodeRegistry3)) {
                        intValue++;
                     }
                  }
               }
            }
         }

         return intValue;
      } else {
         return 0;
      }
   }

   public synchronized void invoke3(String string, float f, float g, float h, float i) {
      if (string != null && !string.isBlank()) {
         this.valuesByKey2.put(string, new float[]{f, g, h, i});
      }
   }

   public synchronized void invoke4(String string, int i) {
      if (string != null && !string.isBlank()) {
         if (i <= 0) {
            this.valuesByKey3.remove(string);
         } else {
            this.valuesByKey3.put(string, i);
         }
      }
   }

   public void invoke5(float f, float g, float h, float i) {
      this.invoke3("uRadii", Math.max(0.0F, f), Math.max(0.0F, g), Math.max(0.0F, h), Math.max(0.0F, i));
   }

   public synchronized void invoke6(GlShaderProgram glShaderProgram, ShaderSurface shaderSurface4) {
      if (glShaderProgram != null) {
         ShaderSurface shaderSurface5 = shaderSurface4 == null ? ShaderSurface.PREVIEW_ONLY : shaderSurface4.resolve();

         for (ShaderEffectManager.ShaderEffectManagerEntityData shaderEffectManagerEntityData5 : this.valuesByKey.values()) {
            if (shaderEffectManagerEntityData5.target().resolve() == shaderSurface5) {
               for (ShaderEffectManager.ShaderEffectManagerEntry shaderEffectManagerEntry : shaderEffectManagerEntityData5.uniforms()) {
                  int intValue3 = glShaderProgram.compute2(shaderEffectManagerEntry.name());
                  if (intValue3 >= 0) {
                     float[] floatValues = this.valuesByKey2.getOrDefault(shaderEffectManagerEntry.name(), shaderEffectManagerEntry.defaults());
                     switch (shaderEffectManagerEntry.kind()) {
                        case SAMPLER2D:
                           GL13.glActiveTexture(33984 + shaderEffectManagerEntry.textureUnit());
                           GL11.glBindTexture(3553, this.compute2(shaderEffectManagerEntry.name()));
                           GL20.glUniform1i(intValue3, shaderEffectManagerEntry.textureUnit());
                           break;
                        case VEC4:
                           GL20.glUniform4f(intValue3, floatValues[0], floatValues[1], floatValues[2], floatValues[3]);
                           break;
                        case VEC2:
                           GL20.glUniform2f(intValue3, floatValues[0], floatValues[1]);
                           break;
                        case FLOAT:
                           GL20.glUniform1f(intValue3, floatValues[0]);
                           break;
                        case INT:
                           GL20.glUniform1i(intValue3, Math.round(floatValues[0]));
                     }
                  }
               }
            }
         }

         GL13.glActiveTexture(33984);
      }
   }

   private int compute2(String string) {
      Integer integerValue = this.valuesByKey3.get(string);
      if (integerValue != null && integerValue > 0) {
         return integerValue;
      } else if ("uMask".equals(string)) {
         int intValue4 = this.shaderEffectManagerResources.compute();
         return intValue4 > 0 ? intValue4 : ThemeShaderProgramCache.getINSTANCE().compute();
      } else if ("uDepth".equals(string)) {
         int intValue5 = this.shaderEffectManagerResources.compute2();
         return intValue5 > 0 ? intValue5 : ThemeShaderProgramCache.getINSTANCE().compute();
      } else {
         return ThemeShaderProgramCache.getINSTANCE().compute();
      }
   }

   public boolean check2(
      String string,
      ShaderBuildResult shaderBuildResult,
      Map<String, float[]> map,
      float f,
      float g,
      float h,
      float i,
      int j,
      int k,
      float l,
      float m,
      ColorScheme colorScheme,
      float n
   ) {
      int intValue6 = this.shaderEffectManagerResources.compute();
      return intValue6 <= 0
         ? false
         : this.check4(string, shaderBuildResult, map, ShaderSurface.ESP, intValue6, f, g, h, i, f, g, h, i, 0.0F, j, k, l, m, colorScheme, n);
   }

   public boolean check3(
      String string,
      ShaderBuildResult shaderBuildResult2,
      Map<String, float[]> map,
      float f,
      float g,
      float h,
      float i,
      float j,
      float k,
      float l,
      float m,
      int n,
      int o,
      float p,
      float q,
      ColorScheme colorScheme2,
      float r
   ) {
      this.invoke5(j, k, l, m);
      float floatValue = Math.max(Math.max(j, k), Math.max(l, m));
      return this.check4(
         string, shaderBuildResult2, map, ShaderSurface.HUD, ThemeShaderProgramCache.getINSTANCE().compute(), f, g, h, i, f, g, h, i, floatValue, n, o, p, q, colorScheme2, r
      );
   }

   private boolean check4(
      String string,
      ShaderBuildResult shaderBuildResult3,
      Map<String, float[]> map,
      ShaderSurface shaderSurface6,
      int i,
      float f,
      float g,
      float h,
      float j,
      float k,
      float l,
      float m,
      float n,
      float o,
      int p,
      int q,
      float r,
      float s,
      ColorScheme colorScheme3,
      float t
   ) {
      if (shaderBuildResult3 != null && shaderBuildResult3.ok() && p > 0 && q > 0 && !(h <= 0.0F) && !(j <= 0.0F) && !(t <= 0.001F)) {
         GlShaderProgram glShaderProgram2 = ThemeShaderProgramCache.getINSTANCE().resolve3(string, shaderBuildResult3);
         ShaderProgram shaderProgram = ThemeShaderProgramCache.getINSTANCE().resolve();
         if (glShaderProgram2 != null && shaderProgram != null) {
            FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();
            boolean flag = false ;

            boolean flag2;
            try {
               flag = true;
               GL11.glViewport(0, 0, Math.max(0, p), Math.max(0, q));
               GL11.glDisable(2929);
               GL11.glDisable(2884);
               GL11.glDepthMask(false);
               GlStateManager._enableBlend();
               GL11.glEnable(3042);
               GL14.glBlendFuncSeparate(770, 771, 1, 771);
               GL11.glDisable(36281);
               glShaderProgram2.invoke();
               GL13.glActiveTexture(33984);
               GL11.glBindTexture(3553, i > 0 ? i : ThemeShaderProgramCache.getINSTANCE().compute());
               invoke16(glShaderProgram2, "u_DiffuseMap", 0);
               invoke17(glShaderProgram2, "uViewport", p, q);
               invoke19(glShaderProgram2, "uRect", f, g, h, j);
               invoke19(glShaderProgram2, "u_ElementRect", k, l, m, n);
               invoke15(glShaderProgram2, "u_ElementRadius", Math.max(0.0F, o));
               invoke17(glShaderProgram2, "u_GlobalUV", k / Math.max(1.0F, (float)p), l / Math.max(1.0F, (float)q));
               invoke17(glShaderProgram2, "u_Resolution", Math.max(1.0F, (float)p), Math.max(1.0F, (float)q));
               invoke15(glShaderProgram2, "u_Time", ThemeShaderProgramCache.getINSTANCE().measure());
               invoke17(glShaderProgram2, "u_Mouse", r - k, s - l);
               int intValue7 = colorScheme3 == null ? -1 : colorScheme3.getIntValue14();
               int intValue8 = colorScheme3 == null ? -16777216 : colorScheme3.getIntValue15();
               int intValue9 = colorScheme3 == null ? -15724520 : colorScheme3.getIntValue();
               int intValue10 = colorScheme3 == null ? -14671832 : colorScheme3.getIntValue2();
               invoke18(glShaderProgram2, "u_AccentTop", measure(intValue7, 16), measure(intValue7, 8), measure(intValue7, 0));
               invoke18(glShaderProgram2, "u_AccentBottom", measure(intValue8, 16), measure(intValue8, 8), measure(intValue8, 0));
               invoke19(glShaderProgram2, "u_ThemeColors[0]", measure(intValue9, 16), measure(intValue9, 8), measure(intValue9, 0), measure(intValue9, 24));
               invoke19(glShaderProgram2, "u_ThemeColors[1]", measure(intValue10, 16), measure(intValue10, 8), measure(intValue10, 0), measure(intValue10, 24));
               invoke19(glShaderProgram2, "u_ThemeColors[2]", measure(intValue7, 16), measure(intValue7, 8), measure(intValue7, 0), t);
               invoke19(glShaderProgram2, "u_ThemeColors[3]", measure(intValue8, 16), measure(intValue8, 8), measure(intValue8, 0), t);
               invoke15(glShaderProgram2, "u_Alpha", t);
               invoke12(glShaderProgram2, shaderBuildResult3, map);
               this.invoke6(glShaderProgram2, shaderSurface6);
               shaderProgram.invoke();
               flag2 = true;
               flag = false;
            } finally {
               if (flag) {
                  GL13.glActiveTexture(33984);
                  GL11.glBindTexture(3553, 0);
                  FramebufferUtils.restoreGlState(glStateSnapshot);
               }
            }

            GL13.glActiveTexture(33984);
            GL11.glBindTexture(3553, 0);
            FramebufferUtils.restoreGlState(glStateSnapshot);
            return flag2;
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public synchronized int compute3(
      ShaderSourceBuilder shaderSourceBuilder,
      ShaderNodeRegistry shaderNodeRegistry4,
      ShaderNode shaderNode3,
      String string,
      float f,
      float g,
      ColorScheme colorScheme4,
      float h,
      float i
   ) {
      if (shaderSourceBuilder != null && shaderNodeRegistry4 != null && shaderNode3 != null && string != null && !(f <= 2.0F) && !(g <= 2.0F)) {
         ShaderNodeKind shaderNodeKind5 = shaderNode3.resolve3(string);
         ShaderNodeDefinition shaderNodeDefinition10 = shaderNodeKind5 == null ? null : shaderNodeRegistry4.resolve(shaderNodeKind5.getText2());
         ShaderPin shaderPin5 = resolve8(shaderNodeDefinition10);
         if (shaderPin5 == null) {
            return 0;
         } else {
            ShaderEffectManager.ShaderEffectManagerState shaderEffectManagerState = this.valuesByKey4.computeIfAbsent(string, stringx -> new ShaderEffectManager.ShaderEffectManagerState());
            int intValue11 = shaderNode3.getIntValue();
            if (shaderEffectManagerState.shaderBuildResult == null || shaderEffectManagerState.intValue != intValue11 || !shaderPin5.id().equals(shaderEffectManagerState.text)) {
               ShaderNode shaderNode4 = shaderNode3.resolve6(string);
               shaderNode4.invoke2(ShaderSurface.PREVIEW_ONLY.getText());
               shaderEffectManagerState.shaderBuildResult = shaderSourceBuilder.resolve3(shaderNode4, string, shaderPin5.id(), shaderPin5.type());
               shaderEffectManagerState.intValue = intValue11;
               shaderEffectManagerState.text = shaderPin5.id();
               shaderEffectManagerState.timestamp = 0L;
               String text6 = shaderEffectManagerState.shaderBuildResult == null ? "" : "__template_preview_" + shaderEffectManagerState.shaderBuildResult.hash();
               if (!shaderEffectManagerState.text2.isEmpty() && !shaderEffectManagerState.text2.equals(text6)) {
                  ThemeShaderProgramCache.getINSTANCE().invoke2(shaderEffectManagerState.text2);
               }

               shaderEffectManagerState.text2 = text6;
            }

            if (shaderEffectManagerState.shaderBuildResult != null && shaderEffectManagerState.shaderBuildResult.ok()) {
               FramebufferUtils.GlStateSnapshot glStateSnapshot2;
               byte byteValue;
               label146: {
                  long longValue = System.currentTimeMillis();
                  int intValue12 = Math.max(32, Math.min(512, (int)Math.ceil(f)));
                  int intValue13 = Math.max(32, Math.min(384, (int)Math.ceil(g)));
                  if (longValue - shaderEffectManagerState.timestamp >= 33L || shaderEffectManagerState.intValue2 != intValue12 || shaderEffectManagerState.intValue3 != intValue13) {
                     glStateSnapshot2 = FramebufferUtils.captureGlState();
                     boolean flag3 = false ;

                     try {
                        flag3 = true;
                        shaderEffectManagerState.offscreenFramebuffer.invoke(intValue12, intValue13);
                        if (!shaderEffectManagerState.offscreenFramebuffer.check()) {
                           byteValue = 0;
                           flag3 = false;
                           break label146;
                        }

                        shaderEffectManagerState.offscreenFramebuffer.invoke2();
                        GL11.glDisable(3089);
                        GlStateManager._enableBlend();
                        GL11.glEnable(3042);
                        GL11.glClearColor(0.008F, 0.01F, 0.015F, 0.0F);
                        GL11.glClear(16384);
                        ShaderUniformBinder.check8(shaderEffectManagerState.text2, shaderEffectManagerState.shaderBuildResult, 0.0F, 0.0F, intValue12, intValue13, intValue12, intValue13, h, i, colorScheme4, 1.0F);
                        shaderEffectManagerState.timestamp = longValue;
                        shaderEffectManagerState.intValue2 = intValue12;
                        shaderEffectManagerState.intValue3 = intValue13;
                        flag3 = false;
                     } finally {
                        if (flag3) {
                           FramebufferUtils.restoreGlState(glStateSnapshot2);
                        }
                     }

                     FramebufferUtils.restoreGlState(glStateSnapshot2);
                  }

                  shaderEffectManagerState.timestamp2 = longValue;
                  this.invoke10();
                  return shaderEffectManagerState.offscreenFramebuffer.getIntValue2();
               }

               FramebufferUtils.restoreGlState(glStateSnapshot2);
               return byteValue;
            } else {
               return 0;
            }
         }
      } else {
         return 0;
      }
   }

   public synchronized void invoke7(String string) {
      ShaderEffectManager.ShaderEffectManagerState shaderEffectManagerState3 = this.valuesByKey4.remove(string);
      if (shaderEffectManagerState3 != null) {
         invoke11(shaderEffectManagerState3);
      }
   }

   public synchronized void invoke8() {
      for (ShaderEffectManager.ShaderEffectManagerState shaderEffectManagerState4 : this.valuesByKey4.values()) {
         invoke11(shaderEffectManagerState4);
      }

      this.valuesByKey4.clear();
   }

   public synchronized void invoke9() {
      this.invoke8();
      this.shaderEffectManagerResources.close();
      this.shaderEffectManagerResources2.close();
   }

   private void invoke10() {
      while (this.valuesByKey4.size() > 48) {
         Entry entry = this.valuesByKey4.entrySet().iterator().next();
         invoke11((ShaderEffectManager.ShaderEffectManagerState)entry.getValue());
         this.valuesByKey4.remove(entry.getKey());
      }
   }

   private static void invoke11(ShaderEffectManager.ShaderEffectManagerState shaderEffectManagerState5) {
      shaderEffectManagerState5.offscreenFramebuffer.close();
      if (!shaderEffectManagerState5.text2.isEmpty()) {
         ThemeShaderProgramCache.getINSTANCE().invoke2(shaderEffectManagerState5.text2);
         shaderEffectManagerState5.text2 = "";
      }
   }

   private static ShaderPin resolve8(ShaderNodeDefinition shaderNodeDefinition11) {
      if (shaderNodeDefinition11 != null && !shaderNodeDefinition11.getItems2().isEmpty()) {
         for (ShaderPin shaderPin6 : shaderNodeDefinition11.getItems2()) {
            if ("color".equals(shaderPin6.id()) || "mask".equals(shaderPin6.id()) || "value".equals(shaderPin6.id())) {
               return shaderPin6;
            }
         }

         return shaderNodeDefinition11.getItems2().get(0);
      } else {
         return null;
      }
   }

   private static JSONObject resolve9(ShaderPin shaderPin7) {
      JSONObject jsonObject4 = new JSONObject();
      jsonObject4.put("id", shaderPin7.id());
      jsonObject4.put("label", shaderPin7.label());
      jsonObject4.put("type", shaderPin7.type().getText());
      jsonObject4.put("direction", shaderPin7.direction().name().toLowerCase(Locale.ROOT));
      return jsonObject4;
   }

   private static void invoke12(GlShaderProgram glShaderProgram3, ShaderBuildResult shaderBuildResult4, Map<String, float[]> map) {
      if (shaderBuildResult4 != null && !shaderBuildResult4.exposedUniforms().isEmpty()) {
         for (ShaderUniformSpec shaderUniformSpec : shaderBuildResult4.exposedUniforms()) {
            float[] floatValues2 = map == null ? null : (float[])map.get(shaderUniformSpec.uniformName());
            if (floatValues2 == null) {
               floatValues2 = shaderUniformSpec.defaults();
            }

            if (shaderUniformSpec.kind() == ShaderUniformSpec.ShaderUniformSpecState.FLOAT) {
               invoke15(glShaderProgram3, shaderUniformSpec.uniformName(), floatValues2[0]);
            } else {
               invoke19(glShaderProgram3, shaderUniformSpec.uniformName(), floatValues2[0], floatValues2[1], floatValues2[2], floatValues2[3]);
            }
         }
      }
   }

   private void invoke13() {
      this.invoke2(
         new ShaderEffectManager.ShaderEffectManagerEntityData(
            "template_esp_dual_pass",
            "ESP Dual-Pass Source",
            "isolated entity mask and scene depth samplers",
            "Template",
            ShaderSurface.ESP,
            216.0F,
            List.of(),
            List.of(
               ShaderEffectManager.ShaderEffectManagerDisplayEntry.output("mask", "mask", ShaderEffectManager.ShaderEffectManagerState2.FLOAT),
               ShaderEffectManager.ShaderEffectManagerDisplayEntry.output("depth", "depth", ShaderEffectManager.ShaderEffectManagerState2.FLOAT),
               ShaderEffectManager.ShaderEffectManagerDisplayEntry.output("uv", "uv", ShaderEffectManager.ShaderEffectManagerState2.VEC2)
            ),
            "uniform sampler2D uMask;\nuniform sampler2D uDepth;\n\nfloat wild_template_mask(vec2 uv) {\n    return step(0.001, texture(uMask, uv).a);\n}\n\nfloat wild_template_depth(vec2 uv) {\n    float d = texture(uDepth, uv).r;\n    float ndc = d * 2.0 - 1.0;\n    float near = 0.05;\n    float far = 1024.0;\n    return clamp((2.0 * near) / (far + near - ndc * (far - near)), 0.0, 1.0);\n}\n",
            List.of(ShaderEffectManager.ShaderEffectManagerEntry.sampler("uMask", 6), ShaderEffectManager.ShaderEffectManagerEntry.sampler("uDepth", 7)),
            (shaderExpressionUtils, shaderNodeKind6, string) -> {
               boolean flag4 = shaderExpressionUtils.getShaderSurface() == ShaderSurface.ESP;

               return switch (string) {
                  case "mask" -> flag4 ? "wild_template_mask(wild_diffuse_uv())" : "step(0.001, texture(u_DiffuseMap, wild_diffuse_uv()).a)";
                  case "depth" -> flag4 ? "wild_template_depth(wild_diffuse_uv())" : "clamp(1.0 - texture(u_DiffuseMap, wild_diffuse_uv()).a, 0.0, 1.0)";
                  default -> "wild_diffuse_uv()";
               };
            }
         )
      );
      this.invoke2(
         new ShaderEffectManager.ShaderEffectManagerEntityData(
            "template_hud_roundrect",
            "SDF RoundRect Plate",
            "per-corner rounded plate driven by uRect and uRadii",
            "Template",
            ShaderSurface.HUD,
            224.0F,
            List.of(
               ShaderEffectManager.ShaderEffectManagerDisplayEntry.input("color", "color", ShaderEffectManager.ShaderEffectManagerState2.VEC4, "u_ThemeColors[0]"),
               ShaderEffectManager.ShaderEffectManagerDisplayEntry.input("softness", "soft", ShaderEffectManager.ShaderEffectManagerState2.FLOAT, "1.0")
            ),
            List.of(
               ShaderEffectManager.ShaderEffectManagerDisplayEntry.output("color", "color", ShaderEffectManager.ShaderEffectManagerState2.VEC4),
               ShaderEffectManager.ShaderEffectManagerDisplayEntry.output("mask", "distance", ShaderEffectManager.ShaderEffectManagerState2.FLOAT)
            ),
            "uniform vec4 uRadii;\n\nfloat wild_template_corner_pick(vec2 p, vec4 radii) {\n    float top = mix(radii.x, radii.y, step(0.0, p.x));\n    float bottom = mix(radii.w, radii.z, step(0.0, p.x));\n    return mix(top, bottom, step(0.0, p.y));\n}\n\nfloat wild_template_roundrect_distance() {\n    vec2 screenPx = vec2(gl_FragCoord.x, u_Resolution.y - gl_FragCoord.y);\n    vec2 p = screenPx - u_ElementRect.xy - u_ElementRect.zw * 0.5;\n    vec2 halfSize = max(u_ElementRect.zw * 0.5, vec2(0.5));\n    float radiiSum = uRadii.x + uRadii.y + uRadii.z + uRadii.w;\n    vec4 radii = mix(vec4(u_ElementRadius), uRadii, step(0.001, radiiSum));\n    float r = clamp(wild_template_corner_pick(p, radii), 0.0, min(halfSize.x, halfSize.y));\n    vec2 q = abs(p) - halfSize + vec2(r);\n    return length(max(q, vec2(0.0))) - r + min(max(q.x, q.y), 0.0);\n}\n\nfloat wild_template_roundrect_alpha(float d, float softness) {\n    float aa = max(fwidth(d), max(softness, 0.0001));\n    return 1.0 - smoothstep(0.0, aa, d);\n}\n",
            List.of(ShaderEffectManager.ShaderEffectManagerEntry.vec4("uRadii", 0.0F, 0.0F, 0.0F, 0.0F)),
            (shaderExpressionUtils2, shaderNodeKind7, string) -> {
               if (shaderExpressionUtils2.check()) {
                  return "mask".equals(string)
                     ? "wild_template_roundrect_distance()"
                     : "vec4(("
                        + shaderExpressionUtils2.resolve(shaderNodeKind7, "color")
                        + ").rgb, ("
                        + shaderExpressionUtils2.resolve(shaderNodeKind7, "color")
                        + ").a * wild_template_roundrect_alpha(wild_template_roundrect_distance(), "
                        + shaderExpressionUtils2.resolve(shaderNodeKind7, "softness")
                        + "))";
               } else {
                  String text7 = "wild_sdf_round_box(uv, vec2(0.0), vec2(0.42, 0.30), 0.08, 0.0)";
                  return "mask".equals(string)
                     ? text7
                     : "vec4(("
                        + shaderExpressionUtils2.resolve(shaderNodeKind7, "color")
                        + ").rgb, ("
                        + shaderExpressionUtils2.resolve(shaderNodeKind7, "color")
                        + ").a * wild_sdf_alpha("
                        + text7
                        + "))";
               }
            }
         )
      );
   }

   private static void invoke14(ShaderNodeRegistry shaderNodeRegistry5) {
      if (shaderNodeRegistry5.resolve("int_value") == null) {
         shaderNodeRegistry5.invoke(
            new ShaderNodeDefinition(
               "int_value",
               "Integer",
               "Constants",
               154.0F,
               List.of(),
               List.of(ShaderPin.output("value", "value", ShaderValueType.INT)),
               (shaderExpressionUtils3, shaderNodeKind8, string) -> String.valueOf(Math.round(shaderNodeKind8.measure("value", 1.0F)))
            )
         );
      }

      if (shaderNodeRegistry5.resolve("int_to_float") == null) {
         shaderNodeRegistry5.invoke(
            new ShaderNodeDefinition(
               "int_to_float",
               "Int → Float",
               "Math",
               174.0F,
               List.of(ShaderPin.input("i", "i", ShaderValueType.INT, "0")),
               List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
               (shaderExpressionUtils4, shaderNodeKind9, string) -> "float(" + shaderExpressionUtils4.resolve(shaderNodeKind9, "i") + ")"
            )
         );
      }

      if (shaderNodeRegistry5.resolve("float_to_int") == null) {
         shaderNodeRegistry5.invoke(
            new ShaderNodeDefinition(
               "float_to_int",
               "Float → Int",
               "Math",
               174.0F,
               List.of(ShaderPin.input("x", "x", ShaderValueType.FLOAT, "0.0")),
               List.of(ShaderPin.output("value", "value", ShaderValueType.INT)),
               (shaderExpressionUtils5, shaderNodeKind10, string) -> "int(floor((" + shaderExpressionUtils5.resolve(shaderNodeKind10, "x") + ") + 0.5))"
            )
         );
      }
   }

   static float measure(int i, int j) {
      return (i >>> j & 0xFF) / 255.0F;
   }

   static void invoke15(GlShaderProgram glShaderProgram4, String string, float f) {
      int intValue14 = glShaderProgram4.compute2(string);
      if (intValue14 >= 0) {
         GL20.glUniform1f(intValue14, f);
      }
   }

   private static void invoke16(GlShaderProgram glShaderProgram5, String string, int i) {
      int intValue15 = glShaderProgram5.compute2(string);
      if (intValue15 >= 0) {
         GL20.glUniform1i(intValue15, i);
      }
   }

   static void invoke17(GlShaderProgram glShaderProgram6, String string, float f, float g) {
      int intValue16 = glShaderProgram6.compute2(string);
      if (intValue16 >= 0) {
         GL20.glUniform2f(intValue16, f, g);
      }
   }

   private static void invoke18(GlShaderProgram glShaderProgram7, String string, float f, float g, float h) {
      int intValue17 = glShaderProgram7.compute2(string);
      if (intValue17 >= 0) {
         GL20.glUniform3f(intValue17, f, g, h);
      }
   }

   static void invoke19(GlShaderProgram glShaderProgram8, String string, float f, float g, float h, float i) {
      int intValue18 = glShaderProgram8.compute2(string);
      if (intValue18 >= 0) {
         GL20.glUniform4f(intValue18, f, g, h, i);
      }
   }

   public static final class ShaderEffectManagerResources implements AutoCloseable {
      private static final String FOUNDRY_TEMPLATE_ESP = "foundry_template_esp";
      private final DepthRenderTarget depthRenderTarget = new DepthRenderTarget();
      private FramebufferUtils.GlStateSnapshot glStateSnapshot;
      private boolean flag;

      public void invoke(Predicate<Entity> predicate) {
         EntityFramebufferCapture.getInstance().setCaptureFilter("foundry_template_esp", true, predicate);
         this.flag = true;
      }

      public void invoke2() {
         EntityFramebufferCapture.getInstance().removeCaptureFilter("foundry_template_esp");
         this.flag = false;
      }

      public boolean isFlag() {
         return this.flag;
      }

      public boolean check() {
         return EntityFramebufferCapture.getInstance().isCaptureTextureReady();
      }

      public int compute() {
         EntityFramebufferCapture entityFramebufferCapture = EntityFramebufferCapture.getInstance();
         return entityFramebufferCapture.isCaptureTextureReady() && entityFramebufferCapture.getCaptureColorTextureId() > 0 ? entityFramebufferCapture.getCaptureColorTextureId() : this.depthRenderTarget.intValue2;
      }

      public int compute2() {
         EntityFramebufferCapture entityFramebufferCapture2 = EntityFramebufferCapture.getInstance();
         return entityFramebufferCapture2.isCaptureTextureReady() && entityFramebufferCapture2.getCaptureDepthTextureId() > 0 ? entityFramebufferCapture2.getCaptureDepthTextureId() : this.depthRenderTarget.intValue3;
      }

      public int compute3() {
         EntityFramebufferCapture entityFramebufferCapture3 = EntityFramebufferCapture.getInstance();
         return entityFramebufferCapture3.isCaptureTextureReady() && entityFramebufferCapture3.getCaptureWidth() > 0 ? entityFramebufferCapture3.getCaptureWidth() : this.depthRenderTarget.intValue4;
      }

      public int compute4() {
         EntityFramebufferCapture entityFramebufferCapture4 = EntityFramebufferCapture.getInstance();
         return entityFramebufferCapture4.isCaptureTextureReady() && entityFramebufferCapture4.getCaptureHeight() > 0 ? entityFramebufferCapture4.getCaptureHeight() : this.depthRenderTarget.intValue5;
      }

      public boolean check2(int i, int j) {
         if (i > 0 && j > 0 && this.glStateSnapshot == null) {
            try {
               this.depthRenderTarget.invoke(i, j);
            } catch (IllegalStateException illegalStateException) {
               return false;
            }

            this.glStateSnapshot = FramebufferUtils.captureGlState();
            GL30.glBindFramebuffer(36008, FramebufferUtils.compute(this.glStateSnapshot.intValue));
            GL30.glBindFramebuffer(36009, this.depthRenderTarget.intValue);
            GL30.glBlitFramebuffer(0, 0, i, j, 0, 0, i, j, 256, 9728);
            GL30.glBindFramebuffer(36160, this.depthRenderTarget.intValue);
            GL11.glViewport(0, 0, Math.max(0, i), Math.max(0, j));
            GL11.glDisable(3089);
            GL30.glClearBufferfv(6144, 0, ShaderEffectManager.FLOATS);
            GL11.glEnable(2929);
            GL11.glDepthMask(false);
            return true;
         } else {
            return false;
         }
      }

      public void invoke3() {
         if (this.glStateSnapshot != null) {
            FramebufferUtils.restoreGlState(this.glStateSnapshot);
            this.glStateSnapshot = null;
         }
      }

      @Override
      public void close() {
         if (this.flag) {
            this.invoke2();
         }

         this.invoke3();
         this.depthRenderTarget.invoke2();
      }
   }

   static final class ShaderEffectManagerState {
      final OffscreenFramebuffer offscreenFramebuffer = new OffscreenFramebuffer();
      ShaderBuildResult shaderBuildResult;
      String text = "";
      String text2 = "";
      int intValue = Integer.MIN_VALUE;
      int intValue2;
      int intValue3;
      long timestamp;
      long timestamp2;
   }

   public static final class ShaderEffectManagerResources2 implements AutoCloseable {
      private GlShaderProgram glShaderProgram;
      private boolean flag;

      public boolean check(float f, float g, float h, float i, float j, float k, float l, float m, int n, int o, float p, float q, int r, int s, float t) {
         if (!this.flag && !(h <= 0.0F) && !(i <= 0.0F) && r > 0 && s > 0 && !(t <= 0.001F)) {
            GlShaderProgram glShaderProgram9 = this.resolve();
            ShaderProgram shaderProgram2 = ThemeShaderProgramCache.getINSTANCE().resolve();
            if (glShaderProgram9 != null && shaderProgram2 != null) {
               FramebufferUtils.GlStateSnapshot glStateSnapshot3 = FramebufferUtils.captureGlState();

               boolean flag5;
               try {
                  GL11.glViewport(0, 0, Math.max(0, r), Math.max(0, s));
                  GL11.glDisable(2929);
                  GL11.glDisable(2884);
                  GL11.glDepthMask(false);
                  GlStateManager._enableBlend();
                  GL11.glEnable(3042);
                  GL14.glBlendFuncSeparate(770, 771, 1, 771);
                  GL11.glDisable(36281);
                  glShaderProgram9.invoke();
                  ShaderEffectManager.invoke17(glShaderProgram9, "uViewport", r, s);
                  ShaderEffectManager.invoke19(glShaderProgram9, "uRect", f, g, h, i);
                  ShaderEffectManager.invoke19(glShaderProgram9, "uRadii", Math.max(0.0F, j), Math.max(0.0F, k), Math.max(0.0F, l), Math.max(0.0F, m));
                  ShaderEffectManager.invoke19(
                     glShaderProgram9,
                     "uTint",
                     ShaderEffectManager.measure(n, 16),
                     ShaderEffectManager.measure(n, 8),
                     ShaderEffectManager.measure(n, 0),
                     ShaderEffectManager.measure(n, 24)
                  );
                  ShaderEffectManager.invoke19(
                     glShaderProgram9,
                     "uStrokeTint",
                     ShaderEffectManager.measure(o, 16),
                     ShaderEffectManager.measure(o, 8),
                     ShaderEffectManager.measure(o, 0),
                     ShaderEffectManager.measure(o, 24)
                  );
                  ShaderEffectManager.invoke15(glShaderProgram9, "uStrokeWidth", Math.max(0.0F, p));
                  ShaderEffectManager.invoke15(glShaderProgram9, "uSoftness", Math.max(0.0F, q));
                  ShaderEffectManager.invoke15(glShaderProgram9, "uAlpha", t);
                  shaderProgram2.invoke();
                  flag5 = true;
               } finally {
                  FramebufferUtils.restoreGlState(glStateSnapshot3);
               }

               return flag5;
            } else {
               return false;
            }
         } else {
            return false;
         }
      }

      private GlShaderProgram resolve() {
         if (this.glShaderProgram != null) {
            return this.glShaderProgram;
         } else {
            try {
               this.glShaderProgram = GlShaderProgram.resolve("assets/wild/shaders/foundry/roundrect.vert", "assets/wild/shaders/foundry/roundrect.frag");
               return this.glShaderProgram;
            } catch (Throwable exception) {
               this.flag = true;
               return null;
            }
         }
      }

      @Override
      public void close() {
         if (this.glShaderProgram != null) {
            this.glShaderProgram.invoke2();
            this.glShaderProgram = null;
         }

         this.flag = false;
      }
   }

   public record ShaderEffectManagerEntityData(
      String id,
      String title,
      String description,
      String category,
      ShaderSurface target,
      float nodeWidth,
      List<ShaderEffectManager.ShaderEffectManagerDisplayEntry> inputs,
      List<ShaderEffectManager.ShaderEffectManagerDisplayEntry> outputs,
      String glslPreamble,
      List<ShaderEffectManager.ShaderEffectManagerEntry> uniforms,
      ShaderNodeEmitter emitter
   ) {
      public ShaderEffectManagerEntityData(
         String id,
         String title,
         String description,
         String category,
         ShaderSurface target,
         float nodeWidth,
         List<ShaderEffectManager.ShaderEffectManagerDisplayEntry> inputs,
         List<ShaderEffectManager.ShaderEffectManagerDisplayEntry> outputs,
         String glslPreamble,
         List<ShaderEffectManager.ShaderEffectManagerEntry> uniforms,
         ShaderNodeEmitter emitter
      ) {
         if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("template id required");
         } else if (target != null && emitter != null) {
            title = title != null && !title.isBlank() ? title : id;
            description = description == null ? "" : description;
            category = category != null && !category.isBlank() ? category : "Template";
            inputs = inputs == null ? List.of() : List.copyOf(inputs);
            outputs = outputs == null ? List.of() : List.copyOf(outputs);
            glslPreamble = glslPreamble == null ? "" : glslPreamble;
            uniforms = uniforms == null ? List.of() : List.copyOf(uniforms);
            this.id = id;
            this.title = title;
            this.description = description;
            this.category = category;
            this.target = target;
            this.nodeWidth = nodeWidth;
            this.inputs = inputs;
            this.outputs = outputs;
            this.glslPreamble = glslPreamble;
            this.uniforms = uniforms;
            this.emitter = emitter;
         } else {
            throw new IllegalArgumentException("template target and emitter required for " + id);
         }
      }

      public ShaderNodeDefinition toNodeDefinition() {
         ArrayList arrayList2 = new ArrayList(this.inputs.size());

         for (ShaderEffectManager.ShaderEffectManagerDisplayEntry shaderEffectManagerDisplayEntry : this.inputs) {
            arrayList2.add(shaderEffectManagerDisplayEntry.toPinTemplate());
         }

         ArrayList arrayList3 = new ArrayList(this.outputs.size());

         for (ShaderEffectManager.ShaderEffectManagerDisplayEntry shaderEffectManagerDisplayEntry2 : this.outputs) {
            arrayList3.add(shaderEffectManagerDisplayEntry2.toPinTemplate());
         }

         return new ShaderNodeDefinition(this.id, this.title, this.category, this.nodeWidth, arrayList2, arrayList3, this.emitter);
      }
   }

   public record ShaderEffectManagerDisplayEntry(String id, String label, ShaderEffectManager.ShaderEffectManagerState2 type, ShaderPinDirection direction, String defaultExpression) {
      public ShaderEffectManagerDisplayEntry(String id, String label, ShaderEffectManager.ShaderEffectManagerState2 type, ShaderPinDirection direction, String defaultExpression) {
         if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("slot id required");
         } else if (type != null && direction != null) {
            label = label != null && !label.isBlank() ? label : id;
            defaultExpression = defaultExpression == null ? "" : defaultExpression;
            this.id = id;
            this.label = label;
            this.type = type;
            this.direction = direction;
            this.defaultExpression = defaultExpression;
         } else {
            throw new IllegalArgumentException("slot type and direction required for " + id);
         }
      }

      public static ShaderEffectManager.ShaderEffectManagerDisplayEntry input(String string, String string2, ShaderEffectManager.ShaderEffectManagerState2 shaderEffectManagerState23, String string3) {
         return new ShaderEffectManager.ShaderEffectManagerDisplayEntry(string, string2, shaderEffectManagerState23, ShaderPinDirection.INPUT, string3);
      }

      public static ShaderEffectManager.ShaderEffectManagerDisplayEntry output(String string, String string2, ShaderEffectManager.ShaderEffectManagerState2 shaderEffectManagerState24) {
         return new ShaderEffectManager.ShaderEffectManagerDisplayEntry(string, string2, shaderEffectManagerState24, ShaderPinDirection.OUTPUT, "");
      }

      public ShaderPin toPinTemplate() {
         return new ShaderPin(this.id, this.label, this.type.resolve(), this.direction, this.defaultExpression);
      }
   }

   public static enum ShaderEffectManagerState2 {
      VEC4("vec4", 4, ColorScheme.compute5(255, 61, 158, 255)),
      VEC2("vec2", 2, ColorScheme.compute5(177, 140, 255, 255)),
      FLOAT("float", 1, ColorScheme.compute5(53, 228, 255, 255)),
      INT("int", 1, ColorScheme.compute5(155, 255, 61, 255));

      private final String text;
      private final int intValue;
      private final int intValue2;

      private ShaderEffectManagerState2(String string2, int j, int k) {
         this.text = string2;
         this.intValue = j;
         this.intValue2 = k;
      }

      public String getText() {
         return this.text;
      }

      public int getIntValue() {
         return this.intValue;
      }

      public int getIntValue2() {
         return this.intValue2;
      }

      public boolean check(ShaderEffectManager.ShaderEffectManagerState2 shaderEffectManagerState25) {
         return this == shaderEffectManagerState25;
      }

      public ShaderValueType resolve() {
         return switch (this) {
            case VEC4 -> ShaderValueType.VEC4;
            case VEC2 -> ShaderValueType.VEC2;
            case FLOAT -> ShaderValueType.FLOAT;
            case INT -> ShaderValueType.INT;
         };
      }

      public static ShaderEffectManager.ShaderEffectManagerState2 resolve2(ShaderValueType shaderValueType) {
         if (shaderValueType == null) {
            return FLOAT;
         } else {
            return switch (shaderValueType) {
               case VEC4 -> VEC4;
               case VEC3 -> VEC4;
               case VEC2 -> VEC2;
               case FLOAT -> FLOAT;
               case INT -> INT;
            };
         }
      }

      public static int compute(ShaderValueType shaderValueType2) {
         if (shaderValueType2 == null) {
            return FLOAT.intValue2;
         } else {
            return switch (shaderValueType2) {
               case VEC4 -> VEC4.intValue2;
               case VEC3 -> ColorScheme.compute5(250, 176, 96, 255);
               case VEC2 -> VEC2.intValue2;
               case FLOAT -> FLOAT.intValue2;
               case INT -> INT.intValue2;
            };
         }
      }
   }

   public record ShaderEffectManagerEntry(String name, ShaderUniformKind kind, int textureUnit, float[] defaults) {
      public ShaderEffectManagerEntry(String name, ShaderUniformKind kind, int textureUnit, float[] defaults) {
         if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("uniform name required");
         } else if (kind == null) {
            throw new IllegalArgumentException("uniform kind required for " + name);
         } else {
            defaults = defaults == null ? new float[4] : Arrays.copyOf(defaults, 4);
            this.name = name;
            this.kind = kind;
            this.textureUnit = textureUnit;
            this.defaults = defaults;
         }
      }

      public static ShaderEffectManager.ShaderEffectManagerEntry sampler(String string, int i) {
         return new ShaderEffectManager.ShaderEffectManagerEntry(string, ShaderUniformKind.SAMPLER2D, i, null);
      }

      public static ShaderEffectManager.ShaderEffectManagerEntry vec4(String string, float f, float g, float h, float i) {
         return new ShaderEffectManager.ShaderEffectManagerEntry(string, ShaderUniformKind.VEC4, -1, new float[]{f, g, h, i});
      }

      public static ShaderEffectManager.ShaderEffectManagerEntry vec2(String string, float f, float g) {
         return new ShaderEffectManager.ShaderEffectManagerEntry(string, ShaderUniformKind.VEC2, -1, new float[]{f, g, 0.0F, 0.0F});
      }

      public static ShaderEffectManager.ShaderEffectManagerEntry scalar(String string, float f) {
         return new ShaderEffectManager.ShaderEffectManagerEntry(string, ShaderUniformKind.FLOAT, -1, new float[]{f, 0.0F, 0.0F, 0.0F});
      }

      public static ShaderEffectManager.ShaderEffectManagerEntry integer(String string, int i) {
         return new ShaderEffectManager.ShaderEffectManagerEntry(string, ShaderUniformKind.INT, -1, new float[]{i, 0.0F, 0.0F, 0.0F});
      }
   }
}
