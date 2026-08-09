package ru.metaculture.protection;

import java.util.Set;

public final class BuiltInShaderPresets {
   private static boolean flag;
   private static final Set<String> VALUES = Set.of(
      "Adaptive Mica Plate",
      "Velvet Module Card",
      "Nebula Panel Bloom",
      "Aurora Button Pulse",
      "Entity Aura Mask",
      "Holographic Nametag",
      "Trail Energy Ribbon",
      "Magnetic Rim Glow",
      "Pulse Health Ribbon",
      "Phase Chams Film",
      "Prism Sky Wash",
      "Menu Mica Backdrop",
      "Vivid Veil"
   );

   private BuiltInShaderPresets() {
   }

   public static synchronized void invoke(ShaderNodeRegistry shaderNodeRegistry, ShaderSourceBuilder shaderSourceBuilder) {
      if (!flag && shaderNodeRegistry != null && shaderSourceBuilder != null) {
         flag = true;
         ShaderPresetStore shaderPresetStore = ShaderPresetStore.getINSTANCE();
         shaderPresetStore.invoke(shaderNodeRegistry);
         ShaderPresetRegistry.getINSTANCE().setShaderSourceBuilder(shaderSourceBuilder);
         ShaderEffectRenderer.getINSTANCE().invoke(shaderSourceBuilder, shaderNodeRegistry);

         for (ShaderTemplateCatalog.ShaderTemplateCatalogEntityData shaderTemplateCatalogEntityData : ShaderTemplateCatalog.ITEMS) {
            try {
               ShaderNode shaderNode = ShaderTemplateCatalog.resolve(shaderTemplateCatalogEntityData, shaderNodeRegistry);
               if (shaderNode != null) {
                  shaderNode.invoke2(shaderTemplateCatalogEntityData.target().getText());
                  ShaderBuildResult shaderBuildResult = shaderSourceBuilder.resolve2(shaderNode);
                  if (!shaderBuildResult.ok()) {
                     System.out.println("[FoundryBootstrap] skipped failed preset " + shaderTemplateCatalogEntityData.title() + ": " + shaderBuildResult.error());
                  } else {
                     ShaderPresetRegistry.getINSTANCE().invoke3(shaderTemplateCatalogEntityData.title(), shaderNode, shaderBuildResult, ShaderPresetRegistry.ShaderPresetRegistryState2.PRESET);
                  }
               }
            } catch (Throwable exception) {
               System.out.println("[FoundryBootstrap] failed to publish preset " + shaderTemplateCatalogEntityData.title() + ": " + exception.getMessage());
            }
         }

         for (SavedShaderPreset savedShaderPreset : shaderPresetStore.resolve()) {
            try {
               if (!check(savedShaderPreset)) {
                  ShaderNode shaderNode2 = shaderPresetStore.resolve11(savedShaderPreset.getText(), shaderNodeRegistry);
                  if (shaderNode2 != null) {
                     ShaderBuildResult shaderBuildResult2 = shaderSourceBuilder.resolve2(shaderNode2);
                     if (!shaderBuildResult2.ok()) {
                        System.out.println("[FoundryBootstrap] skipped failed slot " + savedShaderPreset.getText2() + ": " + shaderBuildResult2.error());
                     } else {
                        ShaderPresetRegistry.getINSTANCE().invoke3(savedShaderPreset.getText2(), shaderNode2, shaderBuildResult2, resolve(savedShaderPreset));
                     }
                  }
               }
            } catch (Throwable exception2) {
               System.out.println("[FoundryBootstrap] failed to publish " + savedShaderPreset.getText2() + ": " + exception2.getMessage());
            }
         }

         for (ShaderSurface shaderSurface : ShaderSurface.values()) {
            SavedShaderPreset savedShaderPreset2 = shaderPresetStore.resolve13(shaderSurface);
            if (savedShaderPreset2 != null) {
               try {
                  ShaderNode shaderNode3 = shaderPresetStore.resolve11(savedShaderPreset2.getText(), shaderNodeRegistry);
                  if (shaderNode3 != null) {
                     shaderNode3.invoke2(shaderSurface.getText());
                     ShaderBuildResult shaderBuildResult3 = shaderSourceBuilder.resolve2(shaderNode3);
                     if (!shaderBuildResult3.ok()) {
                        System.out.println("[FoundryBootstrap] skipped failed bound target " + shaderSurface.getText() + ": " + shaderBuildResult3.error());
                     } else {
                        ShaderPresetRegistry.getINSTANCE().invoke(shaderSurface, shaderNode3, shaderBuildResult3);
                     }
                  }
               } catch (Throwable exception3) {
                  System.out.println("[FoundryBootstrap] failed to publish " + shaderSurface.getText() + ": " + exception3.getMessage());
               }
            }
         }
      }
   }

   public static Set<String> getVALUES() {
      return VALUES;
   }

   private static boolean check(SavedShaderPreset savedShaderPreset3) {
      if (savedShaderPreset3 == null) {
         return false;
      } else {
         String text = ShaderPresetRegistry.resolve21(savedShaderPreset3.getText2());
         return VALUES.contains(text);
      }
   }

   private static ShaderPresetRegistry.ShaderPresetRegistryState2 resolve(SavedShaderPreset savedShaderPreset4) {
      if (savedShaderPreset4 == null) {
         return ShaderPresetRegistry.ShaderPresetRegistryState2.USER;
      } else {
         String text2 = savedShaderPreset4.getText8();
         if ("preset".equalsIgnoreCase(text2)) {
            return ShaderPresetRegistry.ShaderPresetRegistryState2.PRESET;
         } else {
            return !"imported".equalsIgnoreCase(text2) && !"shared".equalsIgnoreCase(text2) ? ShaderPresetRegistry.ShaderPresetRegistryState2.USER : ShaderPresetRegistry.ShaderPresetRegistryState2.IMPORTED;
         }
      }
   }
}
