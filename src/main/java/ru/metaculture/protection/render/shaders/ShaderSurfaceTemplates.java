package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class ShaderSurfaceTemplates {
   public static final List<ShaderSurfaceTemplates.ShaderSurfaceTemplatesState> ITEMS = new ArrayList<>();

   private static boolean check(ShaderSurface shaderSurface) {
      for (ShaderSurface shaderSurface2 : ShaderSurface.resolve3()) {
         if (shaderSurface2 == shaderSurface) {
            return true;
         }
      }

      return false;
   }

   private ShaderSurfaceTemplates() {
   }

   public static ShaderNode resolve(ShaderSurfaceTemplates.ShaderSurfaceTemplatesState shaderSurfaceTemplatesState, ShaderNodeRegistry shaderNodeRegistry) {
      return shaderSurfaceTemplatesState.function.apply(shaderNodeRegistry);
   }

   static {
      for (ShaderTemplateCatalog.ShaderTemplateCatalogEntityData shaderTemplateCatalogEntityData : ShaderTemplateCatalog.ITEMS) {
         if (check(shaderTemplateCatalogEntityData.target())) {
            ITEMS.add(new ShaderSurfaceTemplates.ShaderSurfaceTemplatesState(shaderTemplateCatalogEntityData.title(), shaderTemplateCatalogEntityData.description(), shaderTemplateCatalogEntityData.target(), shaderTemplateCatalogEntityData.complexity(), shaderTemplateCatalogEntityData.nodes(), shaderTemplateCatalogEntityData.builder()));
         }
      }
   }

   public static final class ShaderSurfaceTemplatesState {
      public final String text;
      public final String text2;
      public final ShaderSurface shaderSurface;
      public final String text3;
      public final List<String> items;
      final Function<ShaderNodeRegistry, ShaderNode> function;

      public ShaderSurfaceTemplatesState(
         String string, String string2, ShaderSurface shaderSurface3, String string3, List<String> list, Function<ShaderNodeRegistry, ShaderNode> function
      ) {
         this.text = string;
         this.text2 = string2;
         this.shaderSurface = shaderSurface3;
         this.text3 = string3 != null && !string3.isBlank() ? string3 : "Custom";
         this.items = list == null ? List.of() : List.copyOf(list);
         this.function = function;
      }
   }
}
