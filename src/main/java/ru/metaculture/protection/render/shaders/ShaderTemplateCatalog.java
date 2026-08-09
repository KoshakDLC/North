package ru.metaculture.protection;

import java.util.List;
import java.util.function.Function;

public final class ShaderTemplateCatalog {
   public static final List<ShaderTemplateCatalog.ShaderTemplateCatalogEntityData> ITEMS = List.of(
      new ShaderTemplateCatalog.ShaderTemplateCatalogEntityData(
         "Ferro HUD Starter",
         "matte host plate with rim, grain and hover light",
         ShaderSurface.HUD,
         "Starter",
         List.of("Element Mask", "Mica Glass", "Rim Light", "Hover Glow"),
         shaderNodeRegistry -> resolve3(shaderNodeRegistry, ShaderSurface.HUD, "Ferro HUD Starter", "clean HUD plate shader", 0.72F, 0.07F, 0.34F, 0.6F)
      ),
      new ShaderTemplateCatalog.ShaderTemplateCatalogEntityData(
         "Ferro Module Card",
         "module row glass without pulse or layout noise",
         ShaderSurface.MODULE_CARD,
         "Starter",
         List.of("Element Mask", "Mica Glass", "Rim Light"),
         shaderNodeRegistry2 -> resolve3(shaderNodeRegistry2, ShaderSurface.MODULE_CARD, "Ferro Module Card", "module card material starter", 0.62F, 0.045F, 0.22F, 0.42F)
      ),
      new ShaderTemplateCatalog.ShaderTemplateCatalogEntityData(
         "Ferro Panel Surface",
         "dock panel surface with stable mica depth",
         ShaderSurface.PANEL_BACKGROUND,
         "Starter",
         List.of("Element Mask", "Mica Glass", "Rim Light"),
         shaderNodeRegistry3 -> resolve3(
            shaderNodeRegistry3, ShaderSurface.PANEL_BACKGROUND, "Ferro Panel Surface", "panel background material starter", 0.68F, 0.055F, 0.26F, 0.48F
         )
      ),
      new ShaderTemplateCatalog.ShaderTemplateCatalogEntityData(
         "Ferro Button Surface",
         "button body with compact magnetic response",
         ShaderSurface.BUTTON,
         "Starter",
         List.of("Element Mask", "Mica Glass", "Hover Glow"),
         shaderNodeRegistry4 -> resolve3(
            shaderNodeRegistry4, ShaderSurface.BUTTON, "Ferro Button Surface", "interactive button material starter", 0.66F, 0.038F, 0.3F, 0.82F
         )
      ),
      new ShaderTemplateCatalog.ShaderTemplateCatalogEntityData(
         "Clean Health Fill",
         "stable gradient fill for bars and shield surfaces",
         ShaderSurface.HEALTH_BAR,
         "Starter",
         List.of("Element UV", "Gradient Map", "SDF Fill"),
         ShaderTemplateCatalog::resolve4
      ),
      new ShaderTemplateCatalog.ShaderTemplateCatalogEntityData(
         "Clean Menu Background",
         "quiet full-screen gradient background",
         ShaderSurface.BACKGROUND,
         "Starter",
         List.of("Global UV", "Gradient Map"),
         shaderNodeRegistry5 -> resolve5(shaderNodeRegistry5, ShaderSurface.BACKGROUND, "Clean Menu Background", "full-screen interface background starter")
      ),
      new ShaderTemplateCatalog.ShaderTemplateCatalogEntityData(
         "Clean Sky Atmosphere",
         "soft atmospheric wash for sky target",
         ShaderSurface.SKY,
         "Starter",
         List.of("Global UV", "Gradient Map"),
         shaderNodeRegistry6 -> resolve5(shaderNodeRegistry6, ShaderSurface.SKY, "Clean Sky Atmosphere", "world atmosphere starter")
      ),
      new ShaderTemplateCatalog.ShaderTemplateCatalogEntityData(
         "Clean ESP Silhouette",
         "entity-target rounded silhouette with rim",
         ShaderSurface.ESP,
         "Starter",
         List.of("Element Mask", "SDF Fill", "Rim Light"),
         ShaderTemplateCatalog::resolve6
      ),
      new ShaderTemplateCatalog.ShaderTemplateCatalogEntityData(
         "Clean Chams Film",
         "texture-preserving entity film with stable fresnel",
         ShaderSurface.CHAMS,
         "Starter",
         List.of("Base Texture", "Fresnel", "Screen Blend"),
         ShaderTemplateCatalog::resolve7
      ),
      new ShaderTemplateCatalog.ShaderTemplateCatalogEntityData(
         "Clean Nametag Plate",
         "billboard nametag mica plate",
         ShaderSurface.NAMETAG,
         "Starter",
         List.of("Element Mask", "Mica Glass", "Rim Light"),
         shaderNodeRegistry7 -> resolve3(shaderNodeRegistry7, ShaderSurface.NAMETAG, "Clean Nametag Plate", "nametag plate material starter", 0.64F, 0.042F, 0.28F, 0.34F)
      ),
      new ShaderTemplateCatalog.ShaderTemplateCatalogEntityData(
         "Clean Trail Ribbon",
         "additive ribbon starter with stable edge energy",
         ShaderSurface.TRAILS,
         "Starter",
         List.of("Fresnel", "Gradient Map", "Bloom Lift"),
         ShaderTemplateCatalog::resolve8
      )
   );

   private ShaderTemplateCatalog() {
   }

   public static ShaderNode resolve(ShaderTemplateCatalog.ShaderTemplateCatalogEntityData shaderTemplateCatalogEntityData, ShaderNodeRegistry shaderNodeRegistry8) {
      return shaderTemplateCatalogEntityData == null ? resolve2(shaderNodeRegistry8) : shaderTemplateCatalogEntityData.builder.apply(shaderNodeRegistry8);
   }

   public static ShaderNode resolve2(ShaderNodeRegistry shaderNodeRegistry9) {
      return resolve3(shaderNodeRegistry9, ShaderSurface.HUD, "Ferro HUD Starter", "clean HUD plate shader", 0.72F, 0.07F, 0.34F, 0.6F);
   }

   private static ShaderNode resolve3(
      ShaderNodeRegistry shaderNodeRegistry10, ShaderSurface shaderSurface, String string, String string2, float f, float g, float h, float i
   ) {
      ShaderNode shaderNode = new ShaderNode();
      invoke(shaderNode, string, string2, shaderSurface, "Starter");
      ShaderTemplateCatalog.ShaderTemplateCatalogData shaderTemplateCatalogData = new ShaderTemplateCatalog.ShaderTemplateCatalogData(-780.0F, -180.0F, 224.0F, 108.0F);
      ShaderNodeKind shaderNodeKind = resolve9(shaderNode, shaderNodeRegistry10, "input_element_uv", shaderTemplateCatalogData, 0, 0);
      ShaderNodeKind shaderNodeKind2 = resolve9(shaderNode, shaderNodeRegistry10, "element_mask", shaderTemplateCatalogData, 0, 1);
      ShaderNodeKind shaderNodeKind3 = resolve9(shaderNode, shaderNodeRegistry10, "theme_panel", shaderTemplateCatalogData, 0, 3);
      ShaderNodeKind shaderNodeKind4 = resolve9(shaderNode, shaderNodeRegistry10, "theme_top", shaderTemplateCatalogData, 0, 4);
      ShaderNodeKind shaderNodeKind5 = resolve9(shaderNode, shaderNodeRegistry10, "theme_bottom", shaderTemplateCatalogData, 0, 5);
      ShaderNodeKind shaderNodeKind6 = resolve9(shaderNode, shaderNodeRegistry10, "exposed_float", shaderTemplateCatalogData, 1, 0);
      ShaderNodeKind shaderNodeKind7 = resolve9(shaderNode, shaderNodeRegistry10, "exposed_float", shaderTemplateCatalogData, 1, 1);
      ShaderNodeKind shaderNodeKind8 = resolve9(shaderNode, shaderNodeRegistry10, "exposed_float", shaderTemplateCatalogData, 1, 2);
      ShaderNodeKind shaderNodeKind9 = resolve9(shaderNode, shaderNodeRegistry10, "exposed_float", shaderTemplateCatalogData, 1, 3);
      ShaderNodeKind shaderNodeKind10 = resolve9(shaderNode, shaderNodeRegistry10, "exposed_float", shaderTemplateCatalogData, 1, 4);
      ShaderNodeKind shaderNodeKind11 = resolve9(shaderNode, shaderNodeRegistry10, "exposed_float", shaderTemplateCatalogData, 1, 5);
      ShaderNodeKind shaderNodeKind12 = resolve9(shaderNode, shaderNodeRegistry10, "glass_surface", shaderTemplateCatalogData, 2, 0);
      ShaderNodeKind shaderNodeKind13 = resolve9(shaderNode, shaderNodeRegistry10, "rim_light", shaderTemplateCatalogData, 2, 2);
      ShaderNodeKind shaderNodeKind14 = resolve9(shaderNode, shaderNodeRegistry10, "hover_glow", shaderTemplateCatalogData, 2, 4);
      ShaderNodeKind shaderNodeKind15 = resolve9(shaderNode, shaderNodeRegistry10, "alpha_blend", shaderTemplateCatalogData, 3, 1);
      ShaderNodeKind shaderNodeKind16 = resolve9(shaderNode, shaderNodeRegistry10, "alpha_blend", shaderTemplateCatalogData, 4, 1);
      ShaderNodeKind shaderNodeKind17 = resolve9(shaderNode, shaderNodeRegistry10, "output_color", shaderTemplateCatalogData, 5, 1);
      invoke2(shaderNodeKind6, "Opacity", f, 0.05F, 1.0F, 0.01F);
      invoke2(shaderNodeKind7, "Grain", g, 0.0F, 0.18F, 0.002F);
      invoke2(shaderNodeKind8, "Rim Width", 1.15F, 0.35F, 4.0F, 0.05F);
      invoke2(shaderNodeKind9, "Rim Power", h, 0.0F, 1.0F, 0.01F);
      invoke2(shaderNodeKind10, "Hover Radius", 0.44F, 0.05F, 1.2F, 0.01F);
      invoke2(shaderNodeKind11, "Hover Power", i, 0.0F, 1.8F, 0.01F);
      shaderNode.check2(shaderNodeKind2.getText(), "mask", shaderNodeKind12.getText(), "mask", shaderNodeRegistry10);
      shaderNode.check2(shaderNodeKind3.getText(), "color", shaderNodeKind12.getText(), "tint", shaderNodeRegistry10);
      shaderNode.check2(shaderNodeKind6.getText(), "value", shaderNodeKind12.getText(), "opacity", shaderNodeRegistry10);
      shaderNode.check2(shaderNodeKind7.getText(), "value", shaderNodeKind12.getText(), "grain", shaderNodeRegistry10);
      shaderNode.check2(shaderNodeKind2.getText(), "mask", shaderNodeKind13.getText(), "mask", shaderNodeRegistry10);
      shaderNode.check2(shaderNodeKind4.getText(), "color", shaderNodeKind13.getText(), "color", shaderNodeRegistry10);
      shaderNode.check2(shaderNodeKind8.getText(), "value", shaderNodeKind13.getText(), "thickness", shaderNodeRegistry10);
      shaderNode.check2(shaderNodeKind9.getText(), "value", shaderNodeKind13.getText(), "intensity", shaderNodeRegistry10);
      shaderNode.check2(shaderNodeKind.getText(), "uv", shaderNodeKind14.getText(), "uv", shaderNodeRegistry10);
      shaderNode.check2(shaderNodeKind5.getText(), "color", shaderNodeKind14.getText(), "color", shaderNodeRegistry10);
      shaderNode.check2(shaderNodeKind10.getText(), "value", shaderNodeKind14.getText(), "radius", shaderNodeRegistry10);
      shaderNode.check2(shaderNodeKind11.getText(), "value", shaderNodeKind14.getText(), "intensity", shaderNodeRegistry10);
      shaderNode.check2(shaderNodeKind12.getText(), "color", shaderNodeKind15.getText(), "base", shaderNodeRegistry10);
      shaderNode.check2(shaderNodeKind13.getText(), "color", shaderNodeKind15.getText(), "layer", shaderNodeRegistry10);
      shaderNode.check2(shaderNodeKind15.getText(), "color", shaderNodeKind16.getText(), "base", shaderNodeRegistry10);
      shaderNode.check2(shaderNodeKind14.getText(), "color", shaderNodeKind16.getText(), "layer", shaderNodeRegistry10);
      shaderNode.check2(shaderNodeKind16.getText(), "color", shaderNodeKind17.getText(), "color", shaderNodeRegistry10);
      return shaderNode;
   }

   private static ShaderNode resolve4(ShaderNodeRegistry shaderNodeRegistry11) {
      ShaderNode shaderNode2 = new ShaderNode();
      invoke(shaderNode2, "Clean Health Fill", "stable health bar shader starter", ShaderSurface.HEALTH_BAR, "Starter");
      ShaderTemplateCatalog.ShaderTemplateCatalogData shaderTemplateCatalogData2 = new ShaderTemplateCatalog.ShaderTemplateCatalogData(-720.0F, -150.0F, 216.0F, 104.0F);
      ShaderNodeKind shaderNodeKind18 = resolve9(shaderNode2, shaderNodeRegistry11, "input_element_uv", shaderTemplateCatalogData2, 0, 0);
      ShaderNodeKind shaderNodeKind19 = resolve9(shaderNode2, shaderNodeRegistry11, "vec2_split", shaderTemplateCatalogData2, 1, 0);
      ShaderNodeKind shaderNodeKind20 = resolve9(shaderNode2, shaderNodeRegistry11, "element_mask", shaderTemplateCatalogData2, 0, 2);
      ShaderNodeKind shaderNodeKind21 = resolve9(shaderNode2, shaderNodeRegistry11, "theme_bottom", shaderTemplateCatalogData2, 1, 2);
      ShaderNodeKind shaderNodeKind22 = resolve9(shaderNode2, shaderNodeRegistry11, "theme_top", shaderTemplateCatalogData2, 1, 3);
      ShaderNodeKind shaderNodeKind23 = resolve9(shaderNode2, shaderNodeRegistry11, "exposed_float", shaderTemplateCatalogData2, 2, 0);
      ShaderNodeKind shaderNodeKind24 = resolve9(shaderNode2, shaderNodeRegistry11, "color_ramp", shaderTemplateCatalogData2, 2, 2);
      ShaderNodeKind shaderNodeKind25 = resolve9(shaderNode2, shaderNodeRegistry11, "sdf_fill", shaderTemplateCatalogData2, 3, 2);
      ShaderNodeKind shaderNodeKind26 = resolve9(shaderNode2, shaderNodeRegistry11, "output_color", shaderTemplateCatalogData2, 4, 2);
      invoke2(shaderNodeKind23, "Fill Alpha", 0.92F, 0.0F, 1.0F, 0.01F);
      shaderNode2.check2(shaderNodeKind18.getText(), "uv", shaderNodeKind19.getText(), "v", shaderNodeRegistry11);
      shaderNode2.check2(shaderNodeKind19.getText(), "x", shaderNodeKind24.getText(), "t", shaderNodeRegistry11);
      shaderNode2.check2(shaderNodeKind21.getText(), "color", shaderNodeKind24.getText(), "a", shaderNodeRegistry11);
      shaderNode2.check2(shaderNodeKind22.getText(), "color", shaderNodeKind24.getText(), "b", shaderNodeRegistry11);
      shaderNode2.check2(shaderNodeKind20.getText(), "mask", shaderNodeKind25.getText(), "mask", shaderNodeRegistry11);
      shaderNode2.check2(shaderNodeKind24.getText(), "color", shaderNodeKind25.getText(), "color", shaderNodeRegistry11);
      shaderNode2.check2(shaderNodeKind23.getText(), "value", shaderNodeKind25.getText(), "alpha", shaderNodeRegistry11);
      shaderNode2.check2(shaderNodeKind25.getText(), "color", shaderNodeKind26.getText(), "color", shaderNodeRegistry11);
      return shaderNode2;
   }

   private static ShaderNode resolve5(ShaderNodeRegistry shaderNodeRegistry12, ShaderSurface shaderSurface2, String string, String string2) {
      ShaderNode shaderNode3 = new ShaderNode();
      invoke(shaderNode3, string, string2, shaderSurface2, "Starter");
      ShaderTemplateCatalog.ShaderTemplateCatalogData shaderTemplateCatalogData3 = new ShaderTemplateCatalog.ShaderTemplateCatalogData(-700.0F, -130.0F, 216.0F, 104.0F);
      ShaderNodeKind shaderNodeKind27 = resolve9(shaderNode3, shaderNodeRegistry12, "input_global_uv", shaderTemplateCatalogData3, 0, 0);
      ShaderNodeKind shaderNodeKind28 = resolve9(shaderNode3, shaderNodeRegistry12, "vec2_split", shaderTemplateCatalogData3, 1, 0);
      ShaderNodeKind shaderNodeKind29 = resolve9(shaderNode3, shaderNodeRegistry12, "theme_bottom", shaderTemplateCatalogData3, 1, 2);
      ShaderNodeKind shaderNodeKind30 = resolve9(shaderNode3, shaderNodeRegistry12, "theme_panel", shaderTemplateCatalogData3, 1, 3);
      ShaderNodeKind shaderNodeKind31 = resolve9(shaderNode3, shaderNodeRegistry12, "theme_top", shaderTemplateCatalogData3, 1, 4);
      ShaderNodeKind shaderNodeKind32 = resolve9(shaderNode3, shaderNodeRegistry12, "color_gradient_map", shaderTemplateCatalogData3, 2, 1);
      ShaderNodeKind shaderNodeKind33 = resolve9(shaderNode3, shaderNodeRegistry12, "output_color", shaderTemplateCatalogData3, 3, 1);
      shaderNode3.check2(shaderNodeKind27.getText(), "uv", shaderNodeKind28.getText(), "v", shaderNodeRegistry12);
      shaderNode3.check2(shaderNodeKind28.getText(), "y", shaderNodeKind32.getText(), "t", shaderNodeRegistry12);
      shaderNode3.check2(shaderNodeKind29.getText(), "color", shaderNodeKind32.getText(), "a", shaderNodeRegistry12);
      shaderNode3.check2(shaderNodeKind30.getText(), "color", shaderNodeKind32.getText(), "b", shaderNodeRegistry12);
      shaderNode3.check2(shaderNodeKind31.getText(), "color", shaderNodeKind32.getText(), "c", shaderNodeRegistry12);
      shaderNode3.check2(shaderNodeKind32.getText(), "color", shaderNodeKind33.getText(), "color", shaderNodeRegistry12);
      return shaderNode3;
   }

   private static ShaderNode resolve6(ShaderNodeRegistry shaderNodeRegistry13) {
      ShaderNode shaderNode4 = new ShaderNode();
      invoke(shaderNode4, "Clean ESP Silhouette", "stable entity silhouette shader starter", ShaderSurface.ESP, "Starter");
      ShaderTemplateCatalog.ShaderTemplateCatalogData shaderTemplateCatalogData4 = new ShaderTemplateCatalog.ShaderTemplateCatalogData(-720.0F, -160.0F, 216.0F, 106.0F);
      ShaderNodeKind shaderNodeKind34 = resolve9(shaderNode4, shaderNodeRegistry13, "element_mask", shaderTemplateCatalogData4, 0, 0);
      ShaderNodeKind shaderNodeKind35 = resolve9(shaderNode4, shaderNodeRegistry13, "theme_bottom", shaderTemplateCatalogData4, 0, 2);
      ShaderNodeKind shaderNodeKind36 = resolve9(shaderNode4, shaderNodeRegistry13, "theme_top", shaderTemplateCatalogData4, 0, 3);
      ShaderNodeKind shaderNodeKind37 = resolve9(shaderNode4, shaderNodeRegistry13, "exposed_float", shaderTemplateCatalogData4, 1, 0);
      ShaderNodeKind shaderNodeKind38 = resolve9(shaderNode4, shaderNodeRegistry13, "exposed_float", shaderTemplateCatalogData4, 1, 1);
      ShaderNodeKind shaderNodeKind39 = resolve9(shaderNode4, shaderNodeRegistry13, "sdf_fill", shaderTemplateCatalogData4, 2, 0);
      ShaderNodeKind shaderNodeKind40 = resolve9(shaderNode4, shaderNodeRegistry13, "rim_light", shaderTemplateCatalogData4, 2, 2);
      ShaderNodeKind shaderNodeKind41 = resolve9(shaderNode4, shaderNodeRegistry13, "alpha_blend", shaderTemplateCatalogData4, 3, 1);
      ShaderNodeKind shaderNodeKind42 = resolve9(shaderNode4, shaderNodeRegistry13, "output_color", shaderTemplateCatalogData4, 4, 1);
      invoke2(shaderNodeKind37, "Aura Alpha", 0.78F, 0.0F, 1.0F, 0.01F);
      invoke2(shaderNodeKind38, "Rim Power", 0.46F, 0.0F, 1.2F, 0.01F);
      shaderNode4.check2(shaderNodeKind34.getText(), "mask", shaderNodeKind39.getText(), "mask", shaderNodeRegistry13);
      shaderNode4.check2(shaderNodeKind35.getText(), "color", shaderNodeKind39.getText(), "color", shaderNodeRegistry13);
      shaderNode4.check2(shaderNodeKind37.getText(), "value", shaderNodeKind39.getText(), "alpha", shaderNodeRegistry13);
      shaderNode4.check2(shaderNodeKind34.getText(), "mask", shaderNodeKind40.getText(), "mask", shaderNodeRegistry13);
      shaderNode4.check2(shaderNodeKind36.getText(), "color", shaderNodeKind40.getText(), "color", shaderNodeRegistry13);
      shaderNode4.check2(shaderNodeKind38.getText(), "value", shaderNodeKind40.getText(), "intensity", shaderNodeRegistry13);
      shaderNode4.check2(shaderNodeKind39.getText(), "color", shaderNodeKind41.getText(), "base", shaderNodeRegistry13);
      shaderNode4.check2(shaderNodeKind40.getText(), "color", shaderNodeKind41.getText(), "layer", shaderNodeRegistry13);
      shaderNode4.check2(shaderNodeKind41.getText(), "color", shaderNodeKind42.getText(), "color", shaderNodeRegistry13);
      return shaderNode4;
   }

   private static ShaderNode resolve7(ShaderNodeRegistry shaderNodeRegistry14) {
      ShaderNode shaderNode5 = new ShaderNode();
      invoke(shaderNode5, "Clean Chams Film", "stable chams material starter", ShaderSurface.CHAMS, "Starter");
      ShaderTemplateCatalog.ShaderTemplateCatalogData shaderTemplateCatalogData5 = new ShaderTemplateCatalog.ShaderTemplateCatalogData(-740.0F, -150.0F, 216.0F, 106.0F);
      ShaderNodeKind shaderNodeKind43 = resolve9(shaderNode5, shaderNodeRegistry14, "input_uv", shaderTemplateCatalogData5, 0, 0);
      ShaderNodeKind shaderNodeKind44 = resolve9(shaderNode5, shaderNodeRegistry14, "base_texture", shaderTemplateCatalogData5, 0, 2);
      ShaderNodeKind shaderNodeKind45 = resolve9(shaderNode5, shaderNodeRegistry14, "color_alpha", shaderTemplateCatalogData5, 1, 2);
      ShaderNodeKind shaderNodeKind46 = resolve9(shaderNode5, shaderNodeRegistry14, "fresnel", shaderTemplateCatalogData5, 1, 0);
      ShaderNodeKind shaderNodeKind47 = resolve9(shaderNode5, shaderNodeRegistry14, "theme_top", shaderTemplateCatalogData5, 1, 4);
      ShaderNodeKind shaderNodeKind48 = resolve9(shaderNode5, shaderNodeRegistry14, "theme_bottom", shaderTemplateCatalogData5, 1, 5);
      ShaderNodeKind shaderNodeKind49 = resolve9(shaderNode5, shaderNodeRegistry14, "color_ramp", shaderTemplateCatalogData5, 2, 0);
      ShaderNodeKind shaderNodeKind50 = resolve9(shaderNode5, shaderNodeRegistry14, "color_multiply_scalar", shaderTemplateCatalogData5, 3, 0);
      ShaderNodeKind shaderNodeKind51 = resolve9(shaderNode5, shaderNodeRegistry14, "blend_screen", shaderTemplateCatalogData5, 4, 1);
      ShaderNodeKind shaderNodeKind52 = resolve9(shaderNode5, shaderNodeRegistry14, "output_color", shaderTemplateCatalogData5, 5, 1);
      shaderNode5.check2(shaderNodeKind43.getText(), "uv", shaderNodeKind46.getText(), "uv", shaderNodeRegistry14);
      shaderNode5.check2(shaderNodeKind46.getText(), "value", shaderNodeKind49.getText(), "t", shaderNodeRegistry14);
      shaderNode5.check2(shaderNodeKind48.getText(), "color", shaderNodeKind49.getText(), "a", shaderNodeRegistry14);
      shaderNode5.check2(shaderNodeKind47.getText(), "color", shaderNodeKind49.getText(), "b", shaderNodeRegistry14);
      shaderNode5.check2(shaderNodeKind44.getText(), "color", shaderNodeKind45.getText(), "color", shaderNodeRegistry14);
      shaderNode5.check2(shaderNodeKind49.getText(), "color", shaderNodeKind50.getText(), "color", shaderNodeRegistry14);
      shaderNode5.check2(shaderNodeKind45.getText(), "alpha", shaderNodeKind50.getText(), "factor", shaderNodeRegistry14);
      shaderNode5.check2(shaderNodeKind44.getText(), "color", shaderNodeKind51.getText(), "base", shaderNodeRegistry14);
      shaderNode5.check2(shaderNodeKind50.getText(), "color", shaderNodeKind51.getText(), "layer", shaderNodeRegistry14);
      shaderNode5.check2(shaderNodeKind45.getText(), "alpha", shaderNodeKind51.getText(), "opacity", shaderNodeRegistry14);
      shaderNode5.check2(shaderNodeKind51.getText(), "color", shaderNodeKind52.getText(), "color", shaderNodeRegistry14);
      shaderNode5.check2(shaderNodeKind45.getText(), "alpha", shaderNodeKind52.getText(), "alpha", shaderNodeRegistry14);
      return shaderNode5;
   }

   private static ShaderNode resolve8(ShaderNodeRegistry shaderNodeRegistry15) {
      ShaderNode shaderNode6 = new ShaderNode();
      invoke(shaderNode6, "Clean Trail Ribbon", "stable trail ribbon shader starter", ShaderSurface.TRAILS, "Starter");
      ShaderTemplateCatalog.ShaderTemplateCatalogData shaderTemplateCatalogData6 = new ShaderTemplateCatalog.ShaderTemplateCatalogData(-720.0F, -145.0F, 216.0F, 104.0F);
      ShaderNodeKind shaderNodeKind53 = resolve9(shaderNode6, shaderNodeRegistry15, "input_uv", shaderTemplateCatalogData6, 0, 0);
      ShaderNodeKind shaderNodeKind54 = resolve9(shaderNode6, shaderNodeRegistry15, "fresnel", shaderTemplateCatalogData6, 1, 0);
      ShaderNodeKind shaderNodeKind55 = resolve9(shaderNode6, shaderNodeRegistry15, "theme_top", shaderTemplateCatalogData6, 1, 2);
      ShaderNodeKind shaderNodeKind56 = resolve9(shaderNode6, shaderNodeRegistry15, "theme_bottom", shaderTemplateCatalogData6, 1, 3);
      ShaderNodeKind shaderNodeKind57 = resolve9(shaderNode6, shaderNodeRegistry15, "exposed_float", shaderTemplateCatalogData6, 2, 0);
      ShaderNodeKind shaderNodeKind58 = resolve9(shaderNode6, shaderNodeRegistry15, "color_ramp", shaderTemplateCatalogData6, 2, 2);
      ShaderNodeKind shaderNodeKind59 = resolve9(shaderNode6, shaderNodeRegistry15, "bloom_lift", shaderTemplateCatalogData6, 3, 2);
      ShaderNodeKind shaderNodeKind60 = resolve9(shaderNode6, shaderNodeRegistry15, "output_color", shaderTemplateCatalogData6, 4, 2);
      invoke2(shaderNodeKind57, "Ribbon Alpha", 0.86F, 0.0F, 1.0F, 0.01F);
      shaderNode6.check2(shaderNodeKind53.getText(), "uv", shaderNodeKind54.getText(), "uv", shaderNodeRegistry15);
      shaderNode6.check2(shaderNodeKind54.getText(), "value", shaderNodeKind58.getText(), "t", shaderNodeRegistry15);
      shaderNode6.check2(shaderNodeKind56.getText(), "color", shaderNodeKind58.getText(), "a", shaderNodeRegistry15);
      shaderNode6.check2(shaderNodeKind55.getText(), "color", shaderNodeKind58.getText(), "b", shaderNodeRegistry15);
      shaderNode6.check2(shaderNodeKind58.getText(), "color", shaderNodeKind59.getText(), "color", shaderNodeRegistry15);
      shaderNode6.check2(shaderNodeKind59.getText(), "color", shaderNodeKind60.getText(), "color", shaderNodeRegistry15);
      shaderNode6.check2(shaderNodeKind57.getText(), "value", shaderNodeKind60.getText(), "alpha", shaderNodeRegistry15);
      return shaderNode6;
   }

   private static void invoke(ShaderNode shaderNode7, String string, String string2, ShaderSurface shaderSurface3, String string3) {
      shaderNode7.getShaderTemplate().setText(string);
      shaderNode7.getShaderTemplate().setText3(string2);
      shaderNode7.getShaderTemplate().invoke2(string3);
      shaderNode7.getShaderTemplate().invoke3("preset");
      if (shaderSurface3 != null) {
         shaderNode7.invoke2(shaderSurface3.getText());
      }
   }

   private static ShaderNodeKind resolve9(
      ShaderNode shaderNode8, ShaderNodeRegistry shaderNodeRegistry16, String string, ShaderTemplateCatalog.ShaderTemplateCatalogData shaderTemplateCatalogData7, int i, int j
   ) {
      return shaderNode8.resolve(string, shaderTemplateCatalogData7.x(i), shaderTemplateCatalogData7.y(j), shaderNodeRegistry16);
   }

   private static void invoke2(ShaderNodeKind shaderNodeKind61, String string, float f, float g, float h, float i) {
      shaderNodeKind61.invoke3("name", string);
      shaderNodeKind61.invoke2("value", f);
      shaderNodeKind61.invoke2("min", g);
      shaderNodeKind61.invoke2("max", h);
      shaderNodeKind61.invoke2("step", i);
   }

   record ShaderTemplateCatalogData(float originX, float originY, float column, float row) {
      float x(int i) {
         return this.originX + this.column * i;
      }

      float y(int i) {
         return this.originY + this.row * i;
      }
   }

   public record ShaderTemplateCatalogEntityData(
      String title, String description, ShaderSurface target, String complexity, List<String> nodes, Function<ShaderNodeRegistry, ShaderNode> builder
   ) {
   }
}
