package ru.metaculture.protection;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

public final class ShaderSourceBuilder {
   private static volatile Function<ShaderSurface, String> function;
   private final ShaderNodeRegistry shaderNodeRegistry;

   public ShaderSourceBuilder(ShaderNodeRegistry shaderNodeRegistry) {
      this.shaderNodeRegistry = shaderNodeRegistry;
   }

   public static void setFunction(Function<ShaderSurface, String> function) {
      ShaderSourceBuilder.function = function;
   }

   private static String resolve(ShaderSurface shaderSurface) {
      Function function2 = function;
      if (function2 == null) {
         return "";
      } else {
         String text = (String)function2.apply(shaderSurface);
         return text == null ? "" : text;
      }
   }

   public ShaderBuildResult resolve2(ShaderNode shaderNode) {
      return this.resolve4(
         shaderNode, null, null, null, ShaderSurface.resolve4(shaderNode == null ? null : shaderNode.getPreview()).resolve()
      );
   }

   public ShaderBuildResult resolve3(ShaderNode shaderNode2, String string, String string2, ShaderValueType shaderValueType) {
      return this.resolve4(shaderNode2, string, string2, shaderValueType, ShaderSurface.PREVIEW_ONLY);
   }

   private ShaderBuildResult resolve4(ShaderNode shaderNode3, String string, String string2, ShaderValueType shaderValueType2, ShaderSurface shaderSurface2) {
      ShaderSurface shaderSurface3 = shaderSurface2 == null ? ShaderSurface.PREVIEW_ONLY : shaderSurface2.resolve();

      try {
         List items = this.resolve10(shaderNode3);
         HashMap hashMap = new HashMap();
         HashMap hashMap2 = new HashMap();
         List items2 = this.resolve6(items, hashMap2);
         ShaderExpressionUtils shaderExpressionUtils = new ShaderExpressionUtils(shaderNode3, this.shaderNodeRegistry, hashMap, hashMap2, shaderSurface3);
         StringBuilder stringBuilder = new StringBuilder(4096);
         String text2 = "vec4(0.02, 0.022, 0.028, 1.0)";

         for (ShaderNodeKind shaderNodeKind : (List<ShaderNodeKind>)items) {
            ShaderNodeDefinition shaderNodeDefinition = this.shaderNodeRegistry.resolve(shaderNodeKind.getText2());
            if (shaderNodeDefinition == null) {
               return new ShaderBuildResult(this.resolve16(shaderSurface3), "invalid", "Unknown node: " + shaderNodeKind.getText2());
            }

            if (!"output_color".equals(shaderNodeDefinition.getText())) {
               for (ShaderPin shaderPin : shaderNodeDefinition.getItems2()) {
                  String text3 = shaderExpressionUtils.resolve3(shaderNodeKind, shaderPin.id());
                  String text4 = shaderNodeDefinition.getShaderNodeEmitter().emit(shaderExpressionUtils, shaderNodeKind, shaderPin.id());
                  stringBuilder.append("    ").append(shaderPin.type().getText()).append(" ").append(text3).append(" = ").append(text4).append(";\n");
                  hashMap.put(shaderNodeKind.getText() + "." + shaderPin.id(), text3);
                  if (string != null && string.equals(shaderNodeKind.getText()) && shaderPin.id().equals(string2)) {
                     text2 = resolve5(text3, shaderValueType2 == null ? shaderPin.type() : shaderValueType2, string2);
                  }
               }
            } else if (string == null || string.equals(shaderNodeKind.getText())) {
               text2 = shaderNodeDefinition.getShaderNodeEmitter().emit(shaderExpressionUtils, shaderNodeKind, "color");
            }
         }

         String text5 = this.resolve11(shaderNode3, shaderSurface3, check(items), resolve7(items2), stringBuilder.toString(), text2);
         return new ShaderBuildResult(text5, resolve17(text5), null, items2);
      } catch (RuntimeException exception) {
         String text6 = this.resolve16(shaderSurface3);
         return new ShaderBuildResult(text6, resolve17(text6), exception.getMessage());
      }
   }

   private static boolean check(List<ShaderNodeKind> list) {
      for (ShaderNodeKind shaderNodeKind2 : list) {
         if (shaderNodeKind2 != null && "base_texture".equals(shaderNodeKind2.getText2())) {
            return true;
         }
      }

      return false;
   }

   private static String resolve5(String string, ShaderValueType shaderValueType3, String string2) {
      if (shaderValueType3 == null) {
         return "vec4(0.02, 0.022, 0.028, 1.0)";
      } else if ("mask".equals(string2)) {
         return "vec4(vec3(wild_sdf_alpha(" + string + ")), 1.0)";
      } else {
         return switch (shaderValueType3) {
            case FLOAT -> "vec4(vec3(clamp(" + string + ", 0.0, 1.0)), 1.0)";
            case VEC2 -> "vec4(clamp(" + string + ", vec2(0.0), vec2(1.0)), 0.0, 1.0)";
            case VEC3 -> "vec4(clamp(" + string + ", vec3(0.0), vec3(1.0)), 1.0)";
            case VEC4 -> "vec4(clamp((" + string + ").rgb, vec3(0.0), vec3(1.0)), clamp((" + string + ").a, 0.0, 1.0))";
            case INT -> "vec4(vec3(clamp(float(" + string + ") / 8.0, 0.0, 1.0)), 1.0)";
         };
      }
   }

   private List<ShaderUniformSpec> resolve6(List<ShaderNodeKind> list, Map<String, String> map) {
      ArrayList arrayList = new ArrayList();
      LinkedHashMap linkedHashMap = new LinkedHashMap();

      for (ShaderNodeKind shaderNodeKind3 : list) {
         if ("exposed_float".equals(shaderNodeKind3.getText2()) || "exposed_color".equals(shaderNodeKind3.getText2())) {
            String text7 = resolve8(shaderNodeKind3);
            String text8 = resolve9(text7);
            int intValue = (int)(Integer)linkedHashMap.getOrDefault(text8, 0);
            linkedHashMap.put(text8, intValue + 1);
            String text9 = "u_" + text8 + (intValue == 0 ? "" : "_" + (intValue + 1));
            map.put(shaderNodeKind3.getText(), text9);
            if ("exposed_float".equals(shaderNodeKind3.getText2())) {
               float floatValue = shaderNodeKind3.measure("value", 0.5F);
               float floatValue2 = shaderNodeKind3.measure("min", 0.0F);
               float floatValue3 = shaderNodeKind3.measure("max", 1.0F);
               float floatValue4 = shaderNodeKind3.measure("step", 0.01F);
               arrayList.add(new ShaderUniformSpec(text7, text9, ShaderUniformSpec.ShaderUniformSpecState.FLOAT, new float[]{floatValue, 0.0F, 0.0F, 1.0F}, floatValue2, floatValue3, floatValue4));
            } else {
               float floatValue5 = shaderNodeKind3.measure("r", 1.0F);
               float floatValue6 = shaderNodeKind3.measure("g", 1.0F);
               float floatValue7 = shaderNodeKind3.measure("b", 1.0F);
               float floatValue8 = shaderNodeKind3.measure("a", 1.0F);
               arrayList.add(new ShaderUniformSpec(text7, text9, ShaderUniformSpec.ShaderUniformSpecState.COLOR, new float[]{floatValue5, floatValue6, floatValue7, floatValue8}, 0.0F, 1.0F, 0.01F));
            }
         }
      }

      return arrayList;
   }

   private static String resolve7(List<ShaderUniformSpec> list) {
      if (list != null && !list.isEmpty()) {
         StringBuilder stringBuilder2 = new StringBuilder(list.size() * 28);

         for (ShaderUniformSpec shaderUniformSpec : list) {
            stringBuilder2.append("uniform ").append(shaderUniformSpec.kind() == ShaderUniformSpec.ShaderUniformSpecState.FLOAT ? "float " : "vec4 ").append(shaderUniformSpec.uniformName()).append(";\n");
         }

         return stringBuilder2.toString();
      } else {
         return "";
      }
   }

   private static String resolve8(ShaderNodeKind shaderNodeKind4) {
      String text10 = "exposed_color".equals(shaderNodeKind4.getText2()) ? "Color" : "Value";
      String text11 = shaderNodeKind4.resolve("name", text10);
      return text11 != null && !text11.isBlank() ? text11.trim() : text10;
   }

   private static String resolve9(String string) {
      String text12 = string == null ? "Value" : string.trim();
      if (text12.isBlank()) {
         text12 = "Value";
      }

      StringBuilder stringBuilder3 = new StringBuilder(text12.length());
      boolean flag = true;

      for (int intValue2 = 0; intValue2 < text12.length(); intValue2++) {
         char character = text12.charAt(intValue2);
         if (Character.isLetterOrDigit(character)) {
            stringBuilder3.append(flag ? Character.toUpperCase(character) : character);
            flag = false;
         } else {
            flag = true;
         }
      }

      if (stringBuilder3.isEmpty()) {
         stringBuilder3.append("Value");
      }

      if (Character.isDigit(stringBuilder3.charAt(0))) {
         stringBuilder3.insert(0, 'N');
      }

      return stringBuilder3.toString();
   }

   private List<ShaderNodeKind> resolve10(ShaderNode shaderNode4) {
      HashMap hashMap3 = new HashMap();
      HashMap hashMap4 = new HashMap();

      for (ShaderNodeKind shaderNodeKind5 : shaderNode4.resolve2()) {
         hashMap3.put(shaderNodeKind5.getText(), 0);
         hashMap4.put(shaderNodeKind5.getText(), new ArrayList());
      }

      for (ShaderConnection shaderConnection : shaderNode4.getItems()) {
         if (hashMap3.containsKey(shaderConnection.getText()) && hashMap3.containsKey(shaderConnection.getText3())) {
            ((List)hashMap4.get(shaderConnection.getText())).add(shaderConnection.getText3());
            hashMap3.put(shaderConnection.getText3(), (Integer)hashMap3.get(shaderConnection.getText3()) + 1);
         }
      }

      ArrayDeque arrayDeque = new ArrayDeque();

      for (Entry entry : ((Map<String, Integer>)hashMap3).entrySet()) {
         if ((Integer)entry.getValue() == 0) {
            arrayDeque.add((String)entry.getKey());
         }
      }

      ArrayList arrayList2 = new ArrayList();

      while (!arrayDeque.isEmpty()) {
         String text13 = (String)arrayDeque.removeFirst();
         ShaderNodeKind shaderNodeKind6 = shaderNode4.resolve3(text13);
         if (shaderNodeKind6 != null) {
            arrayList2.add(shaderNodeKind6);
         }

         for (String text14 : (List<String>)hashMap4.getOrDefault(text13, List.of())) {
            int intValue3 = (Integer)hashMap3.get(text14) - 1;
            hashMap3.put(text14, intValue3);
            if (intValue3 == 0) {
               arrayDeque.add(text14);
            }
         }
      }

      if (arrayList2.size() == hashMap3.size()) {
         return arrayList2;
      } else {
         LinkedHashSet linkedHashSet = new LinkedHashSet(hashMap3.keySet());

         for (ShaderNodeKind shaderNodeKind7 : (List<ShaderNodeKind>)arrayList2) {
            linkedHashSet.remove(shaderNodeKind7.getText());
         }

         throw new IllegalStateException("Circular dependency in graph: " + String.join(", ", linkedHashSet));
      }
   }

   private String resolve11(ShaderNode shaderNode5, ShaderSurface shaderSurface4, boolean bl, String string, String string2, String string3) {
      ShaderSurface shaderSurface5 = shaderSurface4 == null ? ShaderSurface.PREVIEW_ONLY : shaderSurface4.resolve();
      String text15 = shaderSurface5 == ShaderSurface.HUD
         ? resolve13(shaderNode5)
         : (
            shaderSurface5.check2() && !bl
               ? "    float diffuseAlpha = texture(u_DiffuseMap, wild_diffuse_uv()).a;\n    fragColor = vec4(finalColor, finalAlpha * diffuseAlpha * clamp(u_Alpha, 0.0, 1.0));"
               : "    fragColor = vec4(finalColor, finalAlpha * clamp(u_Alpha, 0.0, 1.0));"
         );
      return "#version 330 core\nlayout(location = 0) out vec4 fragColor;\nin vec2 vUv;\nin vec2 vLocal;\nin vec2 vScreen;\nuniform vec2 uViewport;\nuniform vec4 uRect;\nuniform float u_Time;\nuniform vec2 u_Resolution;\nuniform vec2 u_Mouse;\nuniform vec4 u_ElementRect;\nuniform float u_ElementRadius;\nuniform vec2 u_GlobalUV;\nuniform vec3 u_AccentTop;\nuniform vec3 u_AccentBottom;\nuniform vec4 u_ThemeColors[4];\nuniform float u_Alpha;\nuniform sampler2D u_DiffuseMap;\n%s\n\nfloat wild_sat(float v) {\n    return clamp(v, 0.0, 1.0);\n}\n\nvec2 wild_screen_px() {\n    return vec2(gl_FragCoord.x, u_Resolution.y - gl_FragCoord.y);\n}\n\nvec2 wild_global_uv() {\n    return wild_screen_px() / max(u_Resolution, vec2(1.0));\n}\n\nfloat wild_sdf_alpha(float d) {\n    float aa = max(fwidth(d), 1.0);\n    return 1.0 - smoothstep(0.0, aa, d);\n}\n\nfloat wild_shadow_alpha(float d, vec2 size) {\n    float outside = max(d, 0.0);\n    float soft = clamp(min(size.x, size.y) * 0.32, 10.0, 54.0);\n    float gaussian = exp(-(outside * outside) / max(2.0 * soft * soft, 1.0));\n    float falloff = 1.0 - smoothstep(0.0, soft * 2.4, outside);\n    return gaussian * falloff * 0.28 * step(0.0, d);\n}\n\nvec2 wild_diffuse_uv() {\n    return vec2(vScreen.x / max(uViewport.x, 1.0), 1.0 - vScreen.y / max(uViewport.y, 1.0));\n}\n\nvec4 wild_blend_screen(vec4 base, vec4 layer, float opacity) {\n    vec3 v = 1.0 - (1.0 - base.rgb) * (1.0 - layer.rgb);\n    return vec4(mix(base.rgb, v, wild_sat(opacity)), max(base.a, layer.a));\n}\n\nvec4 wild_blend_overlay(vec4 base, vec4 layer, float opacity) {\n    vec3 lo = 2.0 * base.rgb * layer.rgb;\n    vec3 hi = 1.0 - 2.0 * (1.0 - base.rgb) * (1.0 - layer.rgb);\n    vec3 v = mix(lo, hi, step(vec3(0.5), base.rgb));\n    return vec4(mix(base.rgb, v, wild_sat(opacity)), max(base.a, layer.a));\n}\n\nfloat wild_hash12(vec2 p) {\n    vec3 p3 = fract(vec3(p.xyx) * 0.1031);\n    p3 += dot(p3, p3.yzx + 33.33);\n    return fract((p3.x + p3.y) * p3.z);\n}\n\nfloat wild_hash13(vec3 p3) {\n    p3 = fract(p3 * 0.1031);\n    p3 += dot(p3, p3.zyx + 31.32);\n    return fract((p3.x + p3.y) * p3.z);\n}\n\nfloat wild_noise3(vec3 p) {\n    vec3 i = floor(p);\n    vec3 f = fract(p);\n    vec3 u = f * f * (3.0 - 2.0 * f);\n    float n000 = wild_hash13(i + vec3(0.0, 0.0, 0.0));\n    float n100 = wild_hash13(i + vec3(1.0, 0.0, 0.0));\n    float n010 = wild_hash13(i + vec3(0.0, 1.0, 0.0));\n    float n110 = wild_hash13(i + vec3(1.0, 1.0, 0.0));\n    float n001 = wild_hash13(i + vec3(0.0, 0.0, 1.0));\n    float n101 = wild_hash13(i + vec3(1.0, 0.0, 1.0));\n    float n011 = wild_hash13(i + vec3(0.0, 1.0, 1.0));\n    float n111 = wild_hash13(i + vec3(1.0, 1.0, 1.0));\n    float nx00 = mix(n000, n100, u.x);\n    float nx10 = mix(n010, n110, u.x);\n    float nx01 = mix(n001, n101, u.x);\n    float nx11 = mix(n011, n111, u.x);\n    float nxy0 = mix(nx00, nx10, u.y);\n    float nxy1 = mix(nx01, nx11, u.y);\n    return mix(nxy0, nxy1, u.z);\n}\n\nfloat wild_simplex3(vec3 p) {\n    float v = 0.0;\n    float a = 0.5;\n    float f = 1.0;\n    for (int i = 0; i < 5; i++) {\n        v += (wild_noise3(p * f) * 2.0 - 1.0) * a;\n        f *= 2.03;\n        a *= 0.52;\n    }\n    return clamp(v, -1.0, 1.0);\n}\n\nfloat wild_voronoi(vec2 x, float time) {\n    vec2 n = floor(x);\n    vec2 f = fract(x);\n    float md = 8.0;\n    for (int j = -1; j <= 1; j++) {\n        for (int i = -1; i <= 1; i++) {\n            vec2 g = vec2(float(i), float(j));\n            vec2 o = vec2(wild_hash12(n + g), wild_hash12(n + g + 17.31));\n            o = 0.5 + 0.5 * sin(time * 0.45 + 6.2831 * o);\n            vec2 r = g + o - f;\n            md = min(md, dot(r, r));\n        }\n    }\n    return clamp(sqrt(md), 0.0, 1.0);\n}\n\nfloat wild_sdf_circle(vec2 uv, vec2 center, float radius, float softness) {\n    return length(uv - center) - max(radius, 0.0);\n}\n\nfloat wild_sdf_round_box(vec2 uv, vec2 center, vec2 size, float radius, float softness) {\n    vec2 p = uv - center;\n    vec2 safeSize = max(size, vec2(0.0001));\n    float safeRadius = clamp(radius, 0.0, min(safeSize.x, safeSize.y));\n    vec2 q = abs(p) - safeSize + safeRadius;\n    float d = length(max(q, 0.0)) - safeRadius + min(max(q.x, q.y), 0.0);\n    return d;\n}\n\nvec2 wild_local_pos() {\n    return wild_screen_px() - u_ElementRect.xy;\n}\n\nvec2 wild_center_pos() {\n    return wild_local_pos() - u_ElementRect.zw * 0.5;\n}\n\n%s\n\nvec4 wild_alpha_over(vec4 base, vec4 layer) {\n    float outA = layer.a + base.a * (1.0 - layer.a);\n    vec3 outRgb = outA <= 0.0001 ? vec3(0.0) : (layer.rgb * layer.a + base.rgb * base.a * (1.0 - layer.a)) / outA;\n    return vec4(outRgb, outA);\n}\n\nvec4 wild_glass_surface(float d, vec4 tint, float opacity, float grain) {\n    float mask = wild_sdf_alpha(d);\n    vec2 uvn = wild_local_pos() / max(u_ElementRect.zw, vec2(1.0));\n    float noise = wild_noise3(vec3(uvn * max(u_ElementRect.zw, vec2(1.0)) * 0.035, u_Time * 0.18));\n    float vertical = smoothstep(1.0, 0.0, uvn.y);\n    vec3 base = mix(vec3(0.018, 0.020, 0.028), tint.rgb, 0.18 + vertical * 0.10);\n    base += (noise - 0.5) * clamp(grain, 0.0, 0.18);\n    base += vec3(0.035) * smoothstep(0.85, 0.02, abs(uvn.y - 0.08));\n    return vec4(clamp(base, 0.0, 1.0), mask * clamp(opacity, 0.0, 1.0) * tint.a);\n}\n\nvec4 wild_rim_light(float d, vec4 color, float thickness, float intensity) {\n    float width = max(thickness, max(fwidth(d), 0.75));\n    float edge = exp(-(d * d) / max(width * width * 2.0, 0.0001));\n    float inside = wild_sdf_alpha(d);\n    float a = edge * inside * clamp(intensity, 0.0, 1.0) * color.a;\n    return vec4(color.rgb * a, a);\n}\n\nvec4 wild_hover_glow(vec2 uv, vec4 color, float radius, float intensity) {\n    vec2 mouse = clamp(u_Mouse / max(u_ElementRect.zw, vec2(1.0)), vec2(0.0), vec2(1.0));\n    float r = max(radius, 0.001);\n    float d = distance(uv, mouse);\n    float glow = exp(-(d * d) / max(r * r, 0.0001)) * clamp(intensity, 0.0, 2.0);\n    float mask = wild_sdf_alpha(wild_element_distance());\n    float a = glow * mask * color.a;\n    return vec4(color.rgb * glow, a);\n}\n\nfloat wild_inner_shadow(float d, float strength, float width) {\n    float w = max(width, max(fwidth(d), 1.0));\n    float edge = 1.0 - smoothstep(0.0, w, -d);\n    return edge * wild_sdf_alpha(d) * clamp(strength, 0.0, 1.0);\n}\n\nvec4 wild_exposure_lift(vec4 color, float amount, float decay) {\n    float pulse = exp(-fract(u_Time * max(decay, 0.001)) * 4.0);\n    vec3 lifted = color.rgb + color.rgb * pulse * max(amount, 0.0);\n    return vec4(clamp(lifted, 0.0, 1.0), color.a);\n}\n\nvec4 wild_chromatic(vec4 color, vec2 uv, float amount, float phase) {\n    vec2 c = uv - 0.5;\n    float r = color.r + sin(dot(c, vec2(21.7, 17.1)) + phase) * amount;\n    float b = color.b + cos(dot(c, vec2(15.1, 24.2)) - phase * 0.8) * amount;\n    return vec4(clamp(vec3(r, color.g, b), 0.0, 1.0), color.a);\n}\n\nfloat wild_sdf_triangle(vec2 uv, vec2 center, float radius, float softness) {\n    vec2 p = uv - center;\n    const float k = 1.7320508;\n    p.x = abs(p.x) - radius;\n    p.y = p.y + radius / k;\n    if (p.x + k * p.y > 0.0) {\n        p = vec2(p.x - k * p.y, -k * p.x - p.y) / 2.0;\n    }\n    p.x -= clamp(p.x, -2.0 * radius, 0.0);\n    float d = -length(p) * sign(p.y);\n    return d;\n}\n\nfloat wild_sdf_hex(vec2 uv, vec2 center, float radius, float softness) {\n    vec2 p = abs(uv - center);\n    const vec2 k = vec2(0.8660254, 0.5);\n    p -= 2.0 * min(dot(k, p), 0.0) * k;\n    p -= vec2(clamp(p.x, -k.y * radius, k.y * radius), radius);\n    float d = length(p) * sign(p.y);\n    return d;\n}\n\nfloat wild_fbm(vec3 p, int octaves) {\n    float v = 0.0;\n    float a = 0.5;\n    float f = 1.0;\n    for (int i = 0; i < 8; i++) {\n        if (i >= octaves) break;\n        v += (wild_noise3(p * f) * 2.0 - 1.0) * a;\n        f *= 2.07;\n        a *= 0.52;\n    }\n    return clamp(v * 0.5 + 0.5, 0.0, 1.0);\n}\n\nvec2 wild_polar(vec2 uv, vec2 center) {\n    vec2 p = uv - center;\n    float r = length(p);\n    float a = atan(p.y, p.x);\n    return vec2(a / 6.2831 + 0.5, clamp(r * 2.0, 0.0, 1.0));\n}\n\nvec2 wild_rotate_uv(vec2 uv, vec2 center, float angle) {\n    vec2 p = uv - center;\n    float s = sin(angle);\n    float c = cos(angle);\n    return center + vec2(p.x * c - p.y * s, p.x * s + p.y * c);\n}\n\nvec2 wild_twist_uv(vec2 uv, vec2 center, float strength) {\n    vec2 p = uv - center;\n    float r = length(p);\n    float a = atan(p.y, p.x) + r * strength;\n    return center + vec2(cos(a), sin(a)) * r;\n}\n\nfloat wild_vignette(vec2 uv, float intensity, float falloff) {\n    float d = length(uv - 0.5) * 1.4142;\n    return clamp(pow(1.0 - d * clamp(intensity, 0.0, 4.0), max(falloff, 0.0001) * 4.0), 0.0, 1.0);\n}\n\nvec4 wild_bloom_lift(vec4 color, float threshold, float amount) {\n    float lum = dot(color.rgb, vec3(0.2126, 0.7152, 0.0722));\n    float boost = smoothstep(threshold, threshold + 0.05, lum) * max(amount, 0.0);\n    return vec4(color.rgb + color.rgb * boost, color.a);\n}\n\nvec4 wild_channel_split(vec4 color, float amount, float t) {\n    float a = clamp(amount, 0.0, 0.5);\n    float r = color.r + sin(t * 1.3) * a;\n    float g = color.g + sin(t * 1.7 + 1.0) * a * 0.6;\n    float b = color.b + sin(t * 2.1 + 2.0) * a;\n    return vec4(clamp(vec3(r, g, b), 0.0, 1.0), color.a);\n}\n\nvec4 wild_iridescence(float t, float speed) {\n    float phase = t + u_Time * speed * 0.4;\n    vec3 col = 0.5 + 0.5 * cos(6.2831 * (phase + vec3(0.0, 0.33, 0.67)));\n    return vec4(col, 1.0);\n}\n\nfloat wild_smin(float a, float b, float k) {\n    float kk = max(k, 0.0001);\n    float h = clamp(0.5 + 0.5 * (b - a) / kk, 0.0, 1.0);\n    return mix(b, a, h) - kk * h * (1.0 - h);\n}\n\nfloat wild_smax(float a, float b, float k) {\n    float kk = max(k, 0.0001);\n    float h = clamp(0.5 - 0.5 * (b - a) / kk, 0.0, 1.0);\n    return mix(b, a, h) + kk * h * (1.0 - h);\n}\n\nfloat wild_sdf_union(float a, float b, float smoothness) {\n    return wild_smin(a, b, smoothness);\n}\n\nfloat wild_sdf_subtract(float a, float b, float smoothness) {\n    return wild_smax(a, -b, smoothness);\n}\n\nfloat wild_sdf_intersect(float a, float b, float smoothness) {\n    return wild_smax(a, b, smoothness);\n}\n\nfloat wild_remap(float v, float inMin, float inMax, float outMin, float outMax) {\n    float dn = inMax - inMin;\n    if (abs(dn) < 1e-5) return outMin;\n    float t = clamp((v - inMin) / dn, 0.0, 1.0);\n    return mix(outMin, outMax, t);\n}\n\nvec3 wild_gradient3(float t, vec3 a, vec3 b, vec3 c) {\n    float ct = clamp(t, 0.0, 1.0);\n    if (ct < 0.5) {\n        return mix(a, b, ct * 2.0);\n    }\n    return mix(b, c, (ct - 0.5) * 2.0);\n}\n\nvec4 wild_gradient_map(float t, vec4 a, vec4 b, vec4 c) {\n    float ct = clamp(t, 0.0, 1.0);\n    if (ct < 0.5) {\n        return mix(a, b, ct * 2.0);\n    }\n    return mix(b, c, (ct - 0.5) * 2.0);\n}\n\nvec3 wild_desaturate(vec3 col, float amount) {\n    float lum = dot(col, vec3(0.299, 0.587, 0.114));\n    return mix(col, vec3(lum), clamp(amount, 0.0, 1.0));\n}\n\nvec3 wild_invert(vec3 col, float amount) {\n    return mix(col, vec3(1.0) - col, clamp(amount, 0.0, 1.0));\n}\n\nvec3 wild_hsv2rgb(vec3 c) {\n    vec3 p = abs(fract(c.xxx + vec3(0.0, 2.0 / 3.0, 1.0 / 3.0)) * 6.0 - 3.0);\n    return c.z * mix(vec3(1.0), clamp(p - 1.0, 0.0, 1.0), c.y);\n}\n\nfloat wild_bpm(float bpm, float strength) {\n    float beats = u_Time * (max(bpm, 1.0) / 60.0);\n    float pulse = 0.5 + 0.5 * sin(beats * 6.2831);\n    return pow(pulse, max(strength, 0.0001));\n}\n\nvec2 wild_view_dir(vec2 uv) {\n    return normalize(uv - 0.5 + 1e-5);\n}\n\nvec3 wild_normal_from_uv(vec2 uv, float strength) {\n    vec3 nx = vec3(1.0, 0.0, dFdx(length(uv - 0.5)) * strength * 40.0);\n    vec3 ny = vec3(0.0, 1.0, dFdy(length(uv - 0.5)) * strength * 40.0);\n    return normalize(cross(nx, ny));\n}\n\nfloat wild_pulse(float t, float duty) {\n    float f = fract(t);\n    return step(f, clamp(duty, 0.0, 1.0));\n}\n\nvec4 wild_box_blur(vec2 uv, vec4 base, float radius, int samples) {\n    return base;\n}\n\nfloat wild_sdf_star(vec2 uv, vec2 center, float radius, float points, float softness) {\n    vec2 p = uv - center;\n    float a = atan(p.y, p.x);\n    float r = length(p);\n    float pts = max(points, 3.0);\n    float angle = 6.2831 / pts;\n    float c = cos(floor(0.5 + a / angle) * angle - a);\n    float d = r * c - radius;\n    return d;\n}\n\nvec3 wild_rgb2hsv(vec3 c) {\n    vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);\n    vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));\n    vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));\n    float d = q.x - min(q.w, q.y);\n    float e = 1.0e-10;\n    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);\n}\n\nvec2 wild_radial_shear(vec2 uv, vec2 center, float strength) {\n    vec2 d = uv - center;\n    float r = dot(d, d);\n    return uv + vec2(d.y, -d.x) * r * strength;\n}\n\nvec2 wild_spherize(vec2 uv, vec2 center, float strength) {\n    vec2 d = uv - center;\n    float r = length(d);\n    vec2 dir = r > 1e-5 ? d / r : vec2(0.0);\n    float rr = mix(r, sin(r * 1.5708), clamp(strength, 0.0, 1.0));\n    return center + dir * rr;\n}\n\nfloat wild_checker(vec2 uv, vec2 freq) {\n    vec2 c = floor(uv * freq);\n    return mod(c.x + c.y, 2.0);\n}\n\nfloat wild_gnoise2(vec2 p) {\n    return wild_noise3(vec3(p, 0.0));\n}\n\nvoid main() {\n    vec2 screenPos = wild_screen_px();\n    vec2 globalUv = wild_global_uv();\n    vec2 localPos = screenPos - u_ElementRect.xy;\n    vec2 centerPos = localPos - (u_ElementRect.zw * 0.5);\n    vec2 normalizedUv = localPos / max(u_ElementRect.zw, vec2(1.0));\n    vec2 uv = %s;\n%s\n    vec4 color = %s;\n    float vignette = smoothstep(0.92, 0.18, length(normalizedUv - 0.5));\n    color.rgb *= 0.74 + vignette * 0.42;\n    color.rgb += pow(max(color.rgb, vec3(0.0)), vec3(2.2)) * 0.18;\n    vec3 finalColor = clamp(color.rgb, 0.0, 1.0);\n    float finalAlpha = clamp(color.a, 0.0, 1.0);\n%s\n}\n"
         .formatted(
            resolve(shaderSurface5) + (string == null ? "" : string),
            resolve14(shaderNode5),
            shaderSurface5 == ShaderSurface.HUD ? "centerPos" : "((localPos - 0.5 * u_ElementRect.zw) / max(u_ElementRect.w, 1.0))",
            string2,
            string3,
            text15
         );
   }

   public String resolve12() {
      return this.resolve16(ShaderSurface.PREVIEW_ONLY);
   }

   private static String resolve13(ShaderNode shaderNode6) {
      return "Full Quad".equals(resolve15(shaderNode6))
         ? "    fragColor = vec4(finalColor, finalAlpha * clamp(u_Alpha, 0.0, 1.0));"
         : "    float hudDistance = wild_element_distance();\n    float hudInside = wild_sdf_alpha(hudDistance);\n    vec2 hudAabbMask = step(u_ElementRect.xy, screenPos) * step(screenPos, u_ElementRect.xy + u_ElementRect.zw);\n    float hudAabbInside = hudAabbMask.x * hudAabbMask.y;\n    float hudShadow = wild_shadow_alpha(hudDistance, u_ElementRect.zw) * (1.0 - hudAabbInside);\n    float hudAlpha = finalAlpha * clamp(u_Alpha, 0.0, 1.0) * hudInside;\n    float outAlpha = clamp(hudAlpha + hudShadow * (1.0 - hudAlpha), 0.0, 1.0);\n    vec3 outColor = outAlpha <= 0.0001 ? vec3(0.0) : (finalColor * hudAlpha) / outAlpha;\n    fragColor = vec4(outColor, outAlpha);";
   }

   private static String resolve14(ShaderNode shaderNode7) {
      String text16 = resolve15(shaderNode7);
      String text17 = "float wild_host_element_distance(float inset) {\n    float px = max(inset, 0.0);\n    vec2 size = max(vec2(1.0), u_ElementRect.zw * 0.5 - vec2(px));\n    float radius = max(0.0, u_ElementRadius - px);\n    return wild_sdf_round_box(wild_center_pos(), vec2(0.0), size, radius, 0.0);\n}\n";
      if ("Full Quad".equals(text16)) {
         return text17
            + "float wild_element_distance() {\n    return -1.0;\n}\n\nfloat wild_element_distance_inset(float inset) {\n    return wild_host_element_distance(inset);\n}\n";
      } else {
         return "Inset Shape".equals(text16)
            ? text17
               + "float wild_element_distance() {\n    float inset = max(1.0, min(u_ElementRect.z, u_ElementRect.w) * 0.075);\n    return wild_host_element_distance(inset);\n}\n\nfloat wild_element_distance_inset(float inset) {\n    float baseInset = max(1.0, min(u_ElementRect.z, u_ElementRect.w) * 0.075);\n    return wild_host_element_distance(baseInset + max(inset, 0.0));\n}\n"
            : text17
               + "float wild_element_distance() {\n    return wild_host_element_distance(0.0);\n}\n\nfloat wild_element_distance_inset(float inset) {\n    return wild_host_element_distance(inset);\n}\n";
      }
   }

   private static String resolve15(ShaderNode shaderNode8) {
      if (shaderNode8 != null && shaderNode8.getShaderTemplate() != null) {
         String text18 = shaderNode8.getShaderTemplate().getHostRectangle();
         return !"Inset Shape".equals(text18) && !"Full Quad".equals(text18) ? "Host Rectangle" : text18;
      } else {
         return "Host Rectangle";
      }
   }

   public String resolve16(ShaderSurface shaderSurface6) {
      return this.resolve11(
         null, shaderSurface6, false, "", "", "vec4(mix(vec3(0.018, 0.020, 0.027), u_AccentTop, 0.18 + 0.12 * sin(u_Time + vUv.x * 6.2831)), 1.0)"
      );
   }

   private static String resolve17(String string) {
      try {
         MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
         byte[] byteValues = messageDigest.digest(string.getBytes(StandardCharsets.UTF_8));
         StringBuilder stringBuilder4 = new StringBuilder(16);

         for (int intValue4 = 0; intValue4 < 8; intValue4++) {
            stringBuilder4.append(String.format("%02x", byteValues[intValue4] & 255));
         }

         return stringBuilder4.toString();
      } catch (Exception exception2) {
         return "0000000000000000";
      }
   }
}
