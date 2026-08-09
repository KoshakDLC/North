package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public final class ShaderPresetRegistry {
   private static final ShaderPresetRegistry INSTANCE = new ShaderPresetRegistry();
   private final Map<ShaderSurface, ShaderNode> valuesByKey = new EnumMap<>(ShaderSurface.class);
   private final Map<ShaderSurface, ShaderBuildResult> valuesByKey2 = new EnumMap<>(ShaderSurface.class);
   private final Map<ShaderSurface, String> valuesByKey3 = new EnumMap<>(ShaderSurface.class);
   private final Map<String, ShaderNode> valuesByKey4 = new LinkedHashMap<>();
   private final Map<String, ShaderBuildResult> valuesByKey5 = new LinkedHashMap<>();
   private final Map<String, String> valuesByKey6 = new LinkedHashMap<>();
   private final Map<String, ShaderPresetRegistry.ShaderPresetRegistryState2> valuesByKey7 = new LinkedHashMap<>();
   private final Map<String, ShaderPresetRegistry.ShaderPresetRegistryState> valuesByKey8 = new LinkedHashMap<>();
   private final Map<ShaderSurface, ShaderPresetRegistry.ShaderPresetRegistryState> valuesByKey9 = new EnumMap<>(ShaderSurface.class);
   private final Map<ShaderSurface, Map<String, float[]>> valuesByKey10 = new EnumMap<>(ShaderSurface.class);
   private final Map<String, Map<String, float[]>> valuesByKey11 = new LinkedHashMap<>();
   private final List<Consumer<ShaderSurface>> items = new CopyOnWriteArrayList<>();
   private final List<Consumer<String>> items2 = new CopyOnWriteArrayList<>();
   private ShaderSourceBuilder shaderSourceBuilder;

   private ShaderPresetRegistry() {
   }

   public static ShaderPresetRegistry getINSTANCE() {
      return INSTANCE;
   }

   public synchronized void setShaderSourceBuilder(ShaderSourceBuilder shaderSourceBuilder) {
      this.shaderSourceBuilder = shaderSourceBuilder;
   }

   public synchronized void invoke(ShaderSurface shaderSurface, ShaderNode shaderNode, ShaderBuildResult shaderBuildResult) {
      if (shaderSurface != null && shaderNode != null && shaderBuildResult != null) {
         this.valuesByKey.put(shaderSurface, shaderNode);
         this.valuesByKey2.put(shaderSurface, shaderBuildResult);
         this.valuesByKey9.put(shaderSurface, shaderBuildResult.ok() ? ShaderPresetRegistry.ShaderPresetRegistryState.SAVED : ShaderPresetRegistry.ShaderPresetRegistryState.FAILED);
         invoke14(this.valuesByKey10.computeIfAbsent(shaderSurface, shaderSurface2 -> new LinkedHashMap<>()), shaderBuildResult);

         try {
            this.valuesByKey3.put(shaderSurface, WildThemeCodec.resolve(shaderNode));
         } catch (Throwable exception) {
         }

         this.invoke6(shaderSurface);
      }
   }

   public synchronized void invoke2(String string, ShaderNode shaderNode2, ShaderBuildResult shaderBuildResult2) {
      this.invoke3(string, shaderNode2, shaderBuildResult2, resolve22(shaderNode2));
   }

   public synchronized void invoke3(String string, ShaderNode shaderNode3, ShaderBuildResult shaderBuildResult3, ShaderPresetRegistry.ShaderPresetRegistryState2 shaderPresetRegistryState2) {
      String text = resolve21(string);
      if (!text.isBlank() && shaderNode3 != null && shaderBuildResult3 != null) {
         this.valuesByKey4.put(text, shaderNode3);
         this.valuesByKey5.put(text, shaderBuildResult3);
         this.valuesByKey7.put(text, shaderPresetRegistryState2 == null ? ShaderPresetRegistry.ShaderPresetRegistryState2.USER : shaderPresetRegistryState2);
         this.valuesByKey8.put(text, shaderBuildResult3.ok() ? ShaderPresetRegistry.ShaderPresetRegistryState.SAVED : ShaderPresetRegistry.ShaderPresetRegistryState.FAILED);
         invoke14(this.valuesByKey11.computeIfAbsent(text, stringx -> new LinkedHashMap<>()), shaderBuildResult3);

         try {
            this.valuesByKey6.put(text, WildThemeCodec.resolve(shaderNode3));
         } catch (Throwable exception2) {
         }

         this.invoke7(text);
      }
   }

   public void invoke4(Consumer<ShaderSurface> consumer) {
      if (consumer != null) {
         this.items.add(consumer);
      }
   }

   public void invoke5(Consumer<String> consumer) {
      if (consumer != null) {
         this.items2.add(consumer);
      }
   }

   private void invoke6(ShaderSurface shaderSurface3) {
      for (Consumer consumer2 : this.items) {
         try {
            consumer2.accept(shaderSurface3);
         } catch (Throwable exception3) {
         }
      }
   }

   private void invoke7(String string) {
      for (Consumer consumer3 : this.items2) {
         try {
            consumer3.accept(string);
         } catch (Throwable exception4) {
         }
      }
   }

   public synchronized void invoke8(ShaderSurface shaderSurface4) {
      this.valuesByKey.remove(shaderSurface4);
      this.valuesByKey2.remove(shaderSurface4);
      this.valuesByKey3.remove(shaderSurface4);
      this.valuesByKey10.remove(shaderSurface4);
      this.valuesByKey9.remove(shaderSurface4);
      this.invoke6(shaderSurface4);
   }

   public synchronized void invoke9(String string) {
      String text2 = resolve21(string);
      this.valuesByKey4.remove(text2);
      this.valuesByKey5.remove(text2);
      this.valuesByKey6.remove(text2);
      this.valuesByKey7.remove(text2);
      this.valuesByKey8.remove(text2);
      this.valuesByKey11.remove(text2);
      ThemeShaderProgramCache.getINSTANCE().invoke2(text2);
      this.invoke7(text2);
   }

   public synchronized ShaderBuildResult resolve(ShaderSurface shaderSurface5) {
      return this.valuesByKey2.get(shaderSurface5);
   }

   public synchronized ShaderBuildResult resolve2(String string) {
      return this.valuesByKey5.get(resolve21(string));
   }

   public synchronized ShaderNode resolve3(ShaderSurface shaderSurface6) {
      return this.valuesByKey.get(shaderSurface6);
   }

   public synchronized ShaderNode resolve4(String string) {
      return this.valuesByKey4.get(resolve21(string));
   }

   public synchronized String resolve5(ShaderSurface shaderSurface7) {
      return this.valuesByKey3.get(shaderSurface7);
   }

   public synchronized String resolve6(String string) {
      return this.valuesByKey6.get(resolve21(string));
   }

   public synchronized boolean check(ShaderSurface shaderSurface8) {
      return shaderSurface8 != null && this.valuesByKey2.containsKey(shaderSurface8);
   }

   public synchronized boolean check2(String string) {
      return this.valuesByKey5.containsKey(resolve21(string));
   }

   public synchronized ShaderPresetRegistry.ShaderPresetRegistryState2 resolve7(String string) {
      return this.valuesByKey7.getOrDefault(resolve21(string), ShaderPresetRegistry.ShaderPresetRegistryState2.USER);
   }

   public synchronized ShaderPresetRegistry.ShaderPresetRegistryState resolve8(String string) {
      return this.valuesByKey8.getOrDefault(resolve21(string), ShaderPresetRegistry.ShaderPresetRegistryState.FAILED);
   }

   public synchronized ShaderPresetRegistry.ShaderPresetRegistryState resolve9(ShaderSurface shaderSurface9) {
      return this.valuesByKey9.getOrDefault(shaderSurface9, ShaderPresetRegistry.ShaderPresetRegistryState.FAILED);
   }

   public synchronized List<ShaderUniformSpec> resolve10(ShaderSurface shaderSurface10) {
      ShaderBuildResult shaderBuildResult4 = shaderSurface10 == null ? null : this.valuesByKey2.get(shaderSurface10);
      return shaderBuildResult4 == null ? List.of() : shaderBuildResult4.exposedUniforms();
   }

   public synchronized List<ShaderUniformSpec> resolve11(String string) {
      ShaderBuildResult shaderBuildResult5 = this.valuesByKey5.get(resolve21(string));
      return shaderBuildResult5 == null ? List.of() : shaderBuildResult5.exposedUniforms();
   }

   public synchronized Map<String, float[]> resolve12(ShaderSurface shaderSurface11) {
      return resolve18(this.valuesByKey10.get(shaderSurface11));
   }

   public synchronized Map<String, float[]> resolve13(String string) {
      return resolve18(this.valuesByKey11.get(resolve21(string)));
   }

   public synchronized void invoke10(ShaderSurface shaderSurface12, String string, float f) {
      if (shaderSurface12 != null && Float.isFinite(f)) {
         ShaderUniformSpec shaderUniformSpec = resolve19(this.resolve10(shaderSurface12), string, ShaderUniformSpec.ShaderUniformSpecState.FLOAT);
         if (shaderUniformSpec != null) {
            this.valuesByKey10.computeIfAbsent(shaderSurface12, shaderSurface13 -> new LinkedHashMap<>()).put(shaderUniformSpec.uniformName(), new float[]{f, 0.0F, 0.0F, 1.0F});
         }
      }
   }

   public synchronized void invoke11(String string, String string2, float f) {
      String text3 = resolve21(string);
      if (!text3.isBlank() && Float.isFinite(f)) {
         ShaderUniformSpec shaderUniformSpec2 = resolve19(this.resolve11(text3), string2, ShaderUniformSpec.ShaderUniformSpecState.FLOAT);
         if (shaderUniformSpec2 != null) {
            this.valuesByKey11.computeIfAbsent(text3, stringx -> new LinkedHashMap<>()).put(shaderUniformSpec2.uniformName(), new float[]{f, 0.0F, 0.0F, 1.0F});
         }
      }
   }

   public synchronized void invoke12(ShaderSurface shaderSurface14, String string, int i) {
      if (shaderSurface14 != null) {
         ShaderUniformSpec shaderUniformSpec3 = resolve19(this.resolve10(shaderSurface14), string, ShaderUniformSpec.ShaderUniformSpecState.COLOR);
         if (shaderUniformSpec3 != null) {
            this.valuesByKey10.computeIfAbsent(shaderSurface14, shaderSurface15 -> new LinkedHashMap<>()).put(shaderUniformSpec3.uniformName(), resolve20(i));
         }
      }
   }

   public synchronized void invoke13(String string, String string2, int i) {
      String text4 = resolve21(string);
      if (!text4.isBlank()) {
         ShaderUniformSpec shaderUniformSpec4 = resolve19(this.resolve11(text4), string2, ShaderUniformSpec.ShaderUniformSpecState.COLOR);
         if (shaderUniformSpec4 != null) {
            this.valuesByKey11.computeIfAbsent(text4, stringx -> new LinkedHashMap<>()).put(shaderUniformSpec4.uniformName(), resolve20(i));
         }
      }
   }

   public synchronized List<String> resolve14() {
      ArrayList arrayList = new ArrayList();

      for (String text5 : this.valuesByKey5.keySet()) {
         if (!check3(text5)) {
            arrayList.add(text5);
         }
      }

      Collections.sort(arrayList);
      return arrayList;
   }

   public synchronized List<String> resolve15(ShaderSurface shaderSurface16) {
      ShaderSurface shaderSurface17 = shaderSurface16 == null ? ShaderSurface.PREVIEW_ONLY : shaderSurface16;
      ArrayList arrayList2 = new ArrayList();

      for (String text6 : this.valuesByKey5.keySet()) {
         if (!check3(text6)) {
            ShaderNode shaderNode4 = this.valuesByKey4.get(text6);
            ShaderSurface shaderSurface18 = ShaderSurface.resolve4(shaderNode4 == null ? null : shaderNode4.getPreview());
            if (shaderSurface18 == shaderSurface17) {
               arrayList2.add(text6);
            }
         }
      }

      Collections.sort(arrayList2);
      return arrayList2;
   }

   public synchronized List<String> resolve16() {
      ArrayList arrayList3 = new ArrayList();
      arrayList3.add("None");
      arrayList3.addAll(this.resolve14());
      return arrayList3;
   }

   public synchronized List<String> resolve17(ShaderSurface shaderSurface19) {
      ArrayList arrayList4 = new ArrayList();
      arrayList4.add("None");
      arrayList4.addAll(this.resolve15(shaderSurface19));
      return arrayList4;
   }

   private static void invoke14(Map<String, float[]> map, ShaderBuildResult shaderBuildResult6) {
      if (map != null && shaderBuildResult6 != null) {
         for (ShaderUniformSpec shaderUniformSpec5 : shaderBuildResult6.exposedUniforms()) {
            map.putIfAbsent(shaderUniformSpec5.uniformName(), Arrays.copyOf(shaderUniformSpec5.defaults(), shaderUniformSpec5.defaults().length));
         }
      }
   }

   private static Map<String, float[]> resolve18(Map<String, float[]> map) {
      if (map != null && !map.isEmpty()) {
         HashMap hashMap = new HashMap();

         for (Entry entry : map.entrySet()) {
            hashMap.put(
               (String)entry.getKey(),
               entry.getValue() == null ? new float[]{0.0F, 0.0F, 0.0F, 1.0F} : Arrays.copyOf((float[])entry.getValue(), ((float[])entry.getValue()).length)
            );
         }

         return hashMap;
      } else {
         return Map.of();
      }
   }

   private static ShaderUniformSpec resolve19(List<ShaderUniformSpec> list, String string, ShaderUniformSpec.ShaderUniformSpecState shaderUniformSpecState) {
      if (list != null && !list.isEmpty() && string != null && !string.isBlank()) {
         String text7 = resolve21(string);

         for (ShaderUniformSpec shaderUniformSpec6 : list) {
            if (shaderUniformSpec6.kind() == shaderUniformSpecState && (resolve21(shaderUniformSpec6.name()).equals(text7) || resolve21(shaderUniformSpec6.uniformName()).equals(text7))) {
               return shaderUniformSpec6;
            }
         }

         return null;
      } else {
         return null;
      }
   }

   private static float[] resolve20(int i) {
      return new float[]{(i >> 16 & 0xFF) / 255.0F, (i >> 8 & 0xFF) / 255.0F, (i & 0xFF) / 255.0F, (i >>> 24 & 0xFF) / 255.0F};
   }

   public static String resolve21(String string) {
      if (string == null) {
         return "";
      } else {
         String text8 = string.trim().replaceAll("\\s+", " ");
         return text8.length() > 48 ? text8.substring(0, 48) : text8;
      }
   }

   private static boolean check3(String string) {
      return string != null && string.startsWith("__");
   }

   private static ShaderPresetRegistry.ShaderPresetRegistryState2 resolve22(ShaderNode shaderNode5) {
      if (shaderNode5 != null && shaderNode5.getShaderTemplate() != null) {
         String text9 = shaderNode5.getShaderTemplate().getLocal();
         if ("preset".equalsIgnoreCase(text9)) {
            return ShaderPresetRegistry.ShaderPresetRegistryState2.PRESET;
         } else if ("imported".equalsIgnoreCase(text9) || "shared".equalsIgnoreCase(text9)) {
            return ShaderPresetRegistry.ShaderPresetRegistryState2.IMPORTED;
         } else {
            return "runtime".equalsIgnoreCase(text9) ? ShaderPresetRegistry.ShaderPresetRegistryState2.RUNTIME : ShaderPresetRegistry.ShaderPresetRegistryState2.USER;
         }
      } else {
         return ShaderPresetRegistry.ShaderPresetRegistryState2.USER;
      }
   }

   public static enum ShaderPresetRegistryState {
      SAVED,
      DIRTY,
      FAILED,
      COMPILING;
   }

   public static enum ShaderPresetRegistryState2 {
      PRESET,
      USER,
      IMPORTED,
      RUNTIME;
   }
}
