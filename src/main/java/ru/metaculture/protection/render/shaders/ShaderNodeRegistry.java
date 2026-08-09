package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public final class ShaderNodeRegistry {
   private final Map<String, ShaderNodeDefinition> valuesByKey = new LinkedHashMap<>();

   public ShaderNodeRegistry() {
      this.invoke2();
   }

   public ShaderNodeDefinition resolve(String string) {
      return this.valuesByKey.get(string);
   }

   public Collection<ShaderNodeDefinition> resolve2() {
      return this.valuesByKey.values();
   }

   public List<ShaderNodeDefinition> resolve3(String string) {
      if (string != null && !string.isBlank()) {
         String text = string.toLowerCase(Locale.ROOT).trim();
         ArrayList arrayList = new ArrayList();

         for (ShaderNodeDefinition shaderNodeDefinition : this.valuesByKey.values()) {
            if (shaderNodeDefinition.getText2().toLowerCase(Locale.ROOT).contains(text)
               || shaderNodeDefinition.getText3().toLowerCase(Locale.ROOT).contains(text)
               || shaderNodeDefinition.getText().toLowerCase(Locale.ROOT).contains(text)) {
               arrayList.add(shaderNodeDefinition);
            }
         }

         return arrayList;
      } else {
         return new ArrayList<>(this.valuesByKey.values());
      }
   }

   public void invoke(ShaderNodeDefinition shaderNodeDefinition2) {
      this.valuesByKey.put(shaderNodeDefinition2.getText(), shaderNodeDefinition2);
   }

   private void invoke2() {
      this.invoke(
         new ShaderNodeDefinition(
            "input_uv",
            "Centered UV",
            "Inputs",
            164.0F,
            List.of(),
            List.of(ShaderPin.output("uv", "uv", ShaderValueType.VEC2)),
            (shaderExpressionUtils, shaderNodeKind, string) -> "uv"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "input_global_uv",
            "Global Screen UV",
            "Inputs",
            180.0F,
            List.of(),
            List.of(ShaderPin.output("uv", "uv", ShaderValueType.VEC2)),
            (shaderExpressionUtils2, shaderNodeKind2, string) -> "globalUv"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "input_screen_uv",
            "Screen UV",
            "Inputs",
            180.0F,
            List.of(),
            List.of(ShaderPin.output("uv", "uv", ShaderValueType.VEC2)),
            (shaderExpressionUtils3, shaderNodeKind3, string) -> "globalUv"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "input_time",
            "Time",
            "Inputs",
            164.0F,
            List.of(),
            List.of(ShaderPin.output("time", "time", ShaderValueType.FLOAT)),
            (shaderExpressionUtils4, shaderNodeKind4, string) -> "u_Time"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "input_mouse",
            "Mouse",
            "Inputs",
            164.0F,
            List.of(),
            List.of(ShaderPin.output("mouse", "mouse", ShaderValueType.VEC2)),
            (shaderExpressionUtils5, shaderNodeKind5, string) -> "(u_Mouse / max(u_Resolution, vec2(1.0)))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "input_global_mouse",
            "Global Mouse",
            "Inputs",
            178.0F,
            List.of(),
            List.of(ShaderPin.output("mouse", "mouse", ShaderValueType.VEC2)),
            (shaderExpressionUtils6, shaderNodeKind6, string) -> "((u_ElementRect.xy + u_Mouse) / max(u_Resolution, vec2(1.0)))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "input_element_uv",
            "Element UV",
            "Context",
            174.0F,
            List.of(),
            List.of(ShaderPin.output("uv", "uv", ShaderValueType.VEC2)),
            (shaderExpressionUtils7, shaderNodeKind7, string) -> "normalizedUv"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "input_element_centered_uv",
            "Element Centered UV",
            "Context",
            204.0F,
            List.of(),
            List.of(ShaderPin.output("uv", "uv", ShaderValueType.VEC2)),
            (shaderExpressionUtils8, shaderNodeKind8, string) -> "(normalizedUv - 0.5)"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "input_element_rect",
            "Element Rect",
            "Context",
            184.0F,
            List.of(),
            List.of(ShaderPin.output("rect", "rect", ShaderValueType.VEC4)),
            (shaderExpressionUtils9, shaderNodeKind9, string) -> "u_ElementRect"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "input_element_size",
            "Element Size",
            "Context",
            184.0F,
            List.of(),
            List.of(ShaderPin.output("size", "size", ShaderValueType.VEC2)),
            (shaderExpressionUtils10, shaderNodeKind10, string) -> "u_ElementRect.zw"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "input_element_radius",
            "Element Radius",
            "Context",
            190.0F,
            List.of(),
            List.of(ShaderPin.output("radius", "radius", ShaderValueType.FLOAT)),
            (shaderExpressionUtils11, shaderNodeKind11, string) -> "u_ElementRadius"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "input_local_mouse",
            "Local Mouse",
            "Context",
            180.0F,
            List.of(),
            List.of(ShaderPin.output("mouse", "mouse", ShaderValueType.VEC2)),
            (shaderExpressionUtils12, shaderNodeKind12, string) -> "clamp(u_Mouse / max(u_ElementRect.zw, vec2(1.0)), vec2(0.0), vec2(1.0))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "input_aspect",
            "Element Aspect",
            "Context",
            186.0F,
            List.of(),
            List.of(ShaderPin.output("aspect", "aspect", ShaderValueType.FLOAT)),
            (shaderExpressionUtils13, shaderNodeKind13, string) -> "(u_ElementRect.z / max(u_ElementRect.w, 1.0))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "theme_top",
            "Accent Top",
            "Inputs",
            174.0F,
            List.of(),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils14, shaderNodeKind14, string) -> "vec4(u_AccentTop, 1.0)"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "theme_bottom",
            "Accent Bottom",
            "Inputs",
            174.0F,
            List.of(),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils15, shaderNodeKind15, string) -> "vec4(u_AccentBottom, 1.0)"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "theme_panel",
            "Theme Panel",
            "Inputs",
            174.0F,
            List.of(),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils16, shaderNodeKind16, string) -> "u_ThemeColors[0]"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "exposed_float",
            "Exposed Float",
            "Inputs",
            188.0F,
            List.of(),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils17, shaderNodeKind17, string) -> shaderExpressionUtils17.resolve4(shaderNodeKind17)
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "exposed_color",
            "Exposed Color",
            "Inputs",
            194.0F,
            List.of(),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils18, shaderNodeKind18, string) -> shaderExpressionUtils18.resolve4(shaderNodeKind18)
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "float_value",
            "Float",
            "Constants",
            154.0F,
            List.of(),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils19, shaderNodeKind19, string) -> shaderExpressionUtils19.resolve2(shaderNodeKind19.measure("value", 0.5F))
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "vec2_value",
            "Vec2",
            "Constants",
            168.0F,
            List.of(ShaderPin.input("x", "x", ShaderValueType.FLOAT, "0.5"), ShaderPin.input("y", "y", ShaderValueType.FLOAT, "0.5")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.VEC2)),
            (shaderExpressionUtils20, shaderNodeKind20, string) -> "vec2("
               + shaderExpressionUtils20.resolve(shaderNodeKind20, "x")
               + ", "
               + shaderExpressionUtils20.resolve(shaderNodeKind20, "y")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "vec3_value",
            "Vec3",
            "Constants",
            168.0F,
            List.of(
               ShaderPin.input("x", "x", ShaderValueType.FLOAT, "0.0"),
               ShaderPin.input("y", "y", ShaderValueType.FLOAT, "0.0"),
               ShaderPin.input("z", "z", ShaderValueType.FLOAT, "0.0")
            ),
            List.of(ShaderPin.output("value", "value", ShaderValueType.VEC3)),
            (shaderExpressionUtils21, shaderNodeKind21, string) -> "vec3("
               + shaderExpressionUtils21.resolve(shaderNodeKind21, "x")
               + ", "
               + shaderExpressionUtils21.resolve(shaderNodeKind21, "y")
               + ", "
               + shaderExpressionUtils21.resolve(shaderNodeKind21, "z")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "vec4_value",
            "Vec4",
            "Constants",
            174.0F,
            List.of(
               ShaderPin.input("x", "x", ShaderValueType.FLOAT, "1.0"),
               ShaderPin.input("y", "y", ShaderValueType.FLOAT, "1.0"),
               ShaderPin.input("z", "z", ShaderValueType.FLOAT, "1.0"),
               ShaderPin.input("w", "w", ShaderValueType.FLOAT, "1.0")
            ),
            List.of(ShaderPin.output("value", "value", ShaderValueType.VEC4)),
            (shaderExpressionUtils22, shaderNodeKind22, string) -> "vec4("
               + shaderExpressionUtils22.resolve(shaderNodeKind22, "x")
               + ", "
               + shaderExpressionUtils22.resolve(shaderNodeKind22, "y")
               + ", "
               + shaderExpressionUtils22.resolve(shaderNodeKind22, "z")
               + ", "
               + shaderExpressionUtils22.resolve(shaderNodeKind22, "w")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "base_texture",
            "Base Texture",
            "Texture",
            190.0F,
            List.of(),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils23, shaderNodeKind23, string) -> "texture(u_DiffuseMap, wild_diffuse_uv())"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "input_entity_mask",
            "Entity Mask",
            "Entity Context",
            188.0F,
            List.of(),
            List.of(ShaderPin.output("mask", "mask", ShaderValueType.FLOAT)),
            (shaderExpressionUtils24, shaderNodeKind24, string) -> "step(0.001, texture(u_DiffuseMap, wild_diffuse_uv()).a)"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "input_depth",
            "Depth",
            "Entity Context",
            164.0F,
            List.of(),
            List.of(ShaderPin.output("depth", "depth", ShaderValueType.FLOAT)),
            (shaderExpressionUtils25, shaderNodeKind25, string) -> "clamp(1.0 - texture(u_DiffuseMap, wild_diffuse_uv()).a, 0.0, 1.0)"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "input_camera_distance",
            "Camera Distance",
            "Entity Context",
            196.0F,
            List.of(),
            List.of(ShaderPin.output("distance", "distance", ShaderValueType.FLOAT)),
            (shaderExpressionUtils26, shaderNodeKind26, string) -> "length((wild_screen_px() - u_Resolution * 0.5) / max(u_Resolution.y, 1.0))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "input_camera_dir",
            "Camera Direction",
            "World Context",
            198.0F,
            List.of(),
            List.of(ShaderPin.output("dir", "dir", ShaderValueType.VEC3)),
            (shaderExpressionUtils27, shaderNodeKind27, string) -> "normalize(vec3(wild_view_dir(vUv), 1.0))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "input_sun_dir",
            "Sun Direction",
            "World Context",
            184.0F,
            List.of(),
            List.of(ShaderPin.output("dir", "dir", ShaderValueType.VEC3)),
            (shaderExpressionUtils28, shaderNodeKind28, string) -> "normalize(vec3(cos(u_Time * 0.04), 0.42, sin(u_Time * 0.04)))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "input_world_time",
            "World Time",
            "World Context",
            178.0F,
            List.of(),
            List.of(ShaderPin.output("time", "time", ShaderValueType.FLOAT)),
            (shaderExpressionUtils29, shaderNodeKind29, string) -> "fract(u_Time * 0.012)"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "input_rain",
            "Rain Strength",
            "World Context",
            178.0F,
            List.of(),
            List.of(ShaderPin.output("rain", "rain", ShaderValueType.FLOAT)),
            (shaderExpressionUtils30, shaderNodeKind30, string) -> "0.0"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "input_biome_tint",
            "Biome Tint",
            "World Context",
            178.0F,
            List.of(),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils31, shaderNodeKind31, string) -> "vec4(mix(u_AccentBottom, u_AccentTop, 0.35), 1.0)"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "color_alpha",
            "Alpha Channel",
            "Texture",
            184.0F,
            List.of(ShaderPin.input("color", "color", ShaderValueType.VEC4, "vec4(1.0)")),
            List.of(ShaderPin.output("alpha", "alpha", ShaderValueType.FLOAT)),
            (shaderExpressionUtils32, shaderNodeKind32, string) -> "(" + shaderExpressionUtils32.resolve(shaderNodeKind32, "color") + ").a"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "float_add",
            "Add Float",
            "Math",
            168.0F,
            List.of(ShaderPin.input("a", "a", ShaderValueType.FLOAT, "0.0"), ShaderPin.input("b", "b", ShaderValueType.FLOAT, "0.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils33, shaderNodeKind33, string) -> "("
               + shaderExpressionUtils33.resolve(shaderNodeKind33, "a")
               + " + "
               + shaderExpressionUtils33.resolve(shaderNodeKind33, "b")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "float_mul",
            "Multiply Float",
            "Math",
            182.0F,
            List.of(ShaderPin.input("a", "a", ShaderValueType.FLOAT, "1.0"), ShaderPin.input("b", "b", ShaderValueType.FLOAT, "1.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils34, shaderNodeKind34, string) -> "("
               + shaderExpressionUtils34.resolve(shaderNodeKind34, "a")
               + " * "
               + shaderExpressionUtils34.resolve(shaderNodeKind34, "b")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "float_sin",
            "Sine",
            "Math",
            164.0F,
            List.of(ShaderPin.input("x", "x", ShaderValueType.FLOAT, "0.0"), ShaderPin.input("freq", "freq", ShaderValueType.FLOAT, "1.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils35, shaderNodeKind35, string) -> "(0.5 + 0.5 * sin(("
               + shaderExpressionUtils35.resolve(shaderNodeKind35, "x")
               + ") * ("
               + shaderExpressionUtils35.resolve(shaderNodeKind35, "freq")
               + ")))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "float_smoothstep",
            "Smoothstep",
            "Math",
            188.0F,
            List.of(
               ShaderPin.input("edge0", "edge0", ShaderValueType.FLOAT, "0.0"),
               ShaderPin.input("edge1", "edge1", ShaderValueType.FLOAT, "1.0"),
               ShaderPin.input("x", "x", ShaderValueType.FLOAT, "0.5")
            ),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils36, shaderNodeKind36, string) -> "smoothstep("
               + shaderExpressionUtils36.resolve(shaderNodeKind36, "edge0")
               + ", "
               + shaderExpressionUtils36.resolve(shaderNodeKind36, "edge1")
               + ", "
               + shaderExpressionUtils36.resolve(shaderNodeKind36, "x")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "vec4_mix",
            "Mix Color",
            "Color",
            184.0F,
            List.of(
               ShaderPin.input("a", "a", ShaderValueType.VEC4, "vec4(0.0, 0.0, 0.0, 1.0)"),
               ShaderPin.input("b", "b", ShaderValueType.VEC4, "vec4(1.0, 1.0, 1.0, 1.0)"),
               ShaderPin.input("t", "t", ShaderValueType.FLOAT, "0.5")
            ),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils37, shaderNodeKind37, string) -> "mix("
               + shaderExpressionUtils37.resolve(shaderNodeKind37, "a")
               + ", "
               + shaderExpressionUtils37.resolve(shaderNodeKind37, "b")
               + ", clamp("
               + shaderExpressionUtils37.resolve(shaderNodeKind37, "t")
               + ", 0.0, 1.0))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "sdf_fill",
            "SDF Fill",
            "Color",
            184.0F,
            List.of(
               ShaderPin.input("mask", "distance", ShaderValueType.FLOAT, "-1.0"),
               ShaderPin.input("color", "color", ShaderValueType.VEC4, "u_ThemeColors[0]"),
               ShaderPin.input("alpha", "alpha", ShaderValueType.FLOAT, "1.0")
            ),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils38, shaderNodeKind38, string) -> "vec4(("
               + shaderExpressionUtils38.resolve(shaderNodeKind38, "color")
               + ").rgb, ("
               + shaderExpressionUtils38.resolve(shaderNodeKind38, "color")
               + ").a * wild_sdf_alpha("
               + shaderExpressionUtils38.resolve(shaderNodeKind38, "mask")
               + ") * wild_sat("
               + shaderExpressionUtils38.resolve(shaderNodeKind38, "alpha")
               + "))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "element_mask",
            "Element Mask",
            "Base Shape",
            184.0F,
            List.of(),
            List.of(ShaderPin.output("mask", "distance", ShaderValueType.FLOAT)),
            (shaderExpressionUtils39, shaderNodeKind39, string) -> "wild_element_distance()"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "element_alpha",
            "Element Alpha",
            "Base Shape",
            184.0F,
            List.of(),
            List.of(ShaderPin.output("alpha", "alpha", ShaderValueType.FLOAT)),
            (shaderExpressionUtils40, shaderNodeKind40, string) -> "wild_sdf_alpha(wild_element_distance())"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "element_inner_mask",
            "Inset Element Mask",
            "Base Shape",
            206.0F,
            List.of(ShaderPin.input("inset", "inset", ShaderValueType.FLOAT, "1.0")),
            List.of(ShaderPin.output("mask", "distance", ShaderValueType.FLOAT)),
            (shaderExpressionUtils41, shaderNodeKind41, string) -> "wild_element_distance_inset(" + shaderExpressionUtils41.resolve(shaderNodeKind41, "inset") + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "glass_surface",
            "Mica Glass Surface",
            "Material",
            214.0F,
            List.of(
               ShaderPin.input("mask", "mask", ShaderValueType.FLOAT, "wild_element_distance()"),
               ShaderPin.input("tint", "tint", ShaderValueType.VEC4, "u_ThemeColors[0]"),
               ShaderPin.input("opacity", "opacity", ShaderValueType.FLOAT, "0.58"),
               ShaderPin.input("grain", "grain", ShaderValueType.FLOAT, "0.045")
            ),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils42, shaderNodeKind42, string) -> "wild_glass_surface("
               + shaderExpressionUtils42.resolve(shaderNodeKind42, "mask")
               + ", "
               + shaderExpressionUtils42.resolve(shaderNodeKind42, "tint")
               + ", "
               + shaderExpressionUtils42.resolve(shaderNodeKind42, "opacity")
               + ", "
               + shaderExpressionUtils42.resolve(shaderNodeKind42, "grain")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "rim_light",
            "Rim Light",
            "Material",
            196.0F,
            List.of(
               ShaderPin.input("mask", "mask", ShaderValueType.FLOAT, "wild_element_distance()"),
               ShaderPin.input("color", "color", ShaderValueType.VEC4, "vec4(u_AccentTop, 1.0)"),
               ShaderPin.input("thickness", "width", ShaderValueType.FLOAT, "1.0"),
               ShaderPin.input("intensity", "power", ShaderValueType.FLOAT, "0.18")
            ),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils43, shaderNodeKind43, string) -> "wild_rim_light("
               + shaderExpressionUtils43.resolve(shaderNodeKind43, "mask")
               + ", "
               + shaderExpressionUtils43.resolve(shaderNodeKind43, "color")
               + ", "
               + shaderExpressionUtils43.resolve(shaderNodeKind43, "thickness")
               + ", "
               + shaderExpressionUtils43.resolve(shaderNodeKind43, "intensity")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "hover_glow",
            "Magnetic Hover Glow",
            "Material",
            220.0F,
            List.of(
               ShaderPin.input("uv", "uv", ShaderValueType.VEC2, "normalizedUv"),
               ShaderPin.input("color", "color", ShaderValueType.VEC4, "vec4(u_AccentBottom, 1.0)"),
               ShaderPin.input("radius", "radius", ShaderValueType.FLOAT, "0.42"),
               ShaderPin.input("intensity", "power", ShaderValueType.FLOAT, "0.58")
            ),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils44, shaderNodeKind44, string) -> "wild_hover_glow("
               + shaderExpressionUtils44.resolve(shaderNodeKind44, "uv")
               + ", "
               + shaderExpressionUtils44.resolve(shaderNodeKind44, "color")
               + ", "
               + shaderExpressionUtils44.resolve(shaderNodeKind44, "radius")
               + ", "
               + shaderExpressionUtils44.resolve(shaderNodeKind44, "intensity")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "inner_shadow",
            "Inner Shadow",
            "Material",
            198.0F,
            List.of(
               ShaderPin.input("mask", "mask", ShaderValueType.FLOAT, "wild_element_distance()"),
               ShaderPin.input("strength", "power", ShaderValueType.FLOAT, "0.22"),
               ShaderPin.input("width", "width", ShaderValueType.FLOAT, "12.0")
            ),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils45, shaderNodeKind45, string) -> "wild_inner_shadow("
               + shaderExpressionUtils45.resolve(shaderNodeKind45, "mask")
               + ", "
               + shaderExpressionUtils45.resolve(shaderNodeKind45, "strength")
               + ", "
               + shaderExpressionUtils45.resolve(shaderNodeKind45, "width")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "exposure_lift",
            "Photographic Exposure",
            "Material",
            226.0F,
            List.of(
               ShaderPin.input("color", "color", ShaderValueType.VEC4, "u_ThemeColors[0]"),
               ShaderPin.input("amount", "amount", ShaderValueType.FLOAT, "0.18"),
               ShaderPin.input("decay", "decay", ShaderValueType.FLOAT, "2.0")
            ),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils46, shaderNodeKind46, string) -> "wild_exposure_lift("
               + shaderExpressionUtils46.resolve(shaderNodeKind46, "color")
               + ", "
               + shaderExpressionUtils46.resolve(shaderNodeKind46, "amount")
               + ", "
               + shaderExpressionUtils46.resolve(shaderNodeKind46, "decay")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "alpha_blend",
            "Alpha Blend",
            "Blend",
            190.0F,
            List.of(
               ShaderPin.input("base", "base", ShaderValueType.VEC4, "vec4(0.0)"), ShaderPin.input("layer", "layer", ShaderValueType.VEC4, "vec4(1.0)")
            ),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils47, shaderNodeKind47, string) -> "wild_alpha_over("
               + shaderExpressionUtils47.resolve(shaderNodeKind47, "base")
               + ", "
               + shaderExpressionUtils47.resolve(shaderNodeKind47, "layer")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "color_ramp",
            "Color Ramp",
            "Color",
            184.0F,
            List.of(
               ShaderPin.input("t", "t", ShaderValueType.FLOAT, "0.5"),
               ShaderPin.input("a", "a", ShaderValueType.VEC4, "vec4(u_AccentBottom, 1.0)"),
               ShaderPin.input("b", "b", ShaderValueType.VEC4, "vec4(u_AccentTop, 1.0)")
            ),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils48, shaderNodeKind48, string) -> "mix("
               + shaderExpressionUtils48.resolve(shaderNodeKind48, "a")
               + ", "
               + shaderExpressionUtils48.resolve(shaderNodeKind48, "b")
               + ", wild_sat("
               + shaderExpressionUtils48.resolve(shaderNodeKind48, "t")
               + "))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "color_multiply_scalar",
            "Color Multiply",
            "Color",
            198.0F,
            List.of(ShaderPin.input("color", "color", ShaderValueType.VEC4, "vec4(1.0)"), ShaderPin.input("factor", "factor", ShaderValueType.FLOAT, "1.0")),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils49, shaderNodeKind49, string) -> "vec4(("
               + shaderExpressionUtils49.resolve(shaderNodeKind49, "color")
               + ").rgb * "
               + shaderExpressionUtils49.resolve(shaderNodeKind49, "factor")
               + ", ("
               + shaderExpressionUtils49.resolve(shaderNodeKind49, "color")
               + ").a)"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "blend_screen",
            "Screen Blend",
            "Blend",
            188.0F,
            List.of(
               ShaderPin.input("base", "base", ShaderValueType.VEC4, "vec4(0.02, 0.022, 0.028, 1.0)"),
               ShaderPin.input("layer", "layer", ShaderValueType.VEC4, "vec4(u_AccentTop, 1.0)"),
               ShaderPin.input("opacity", "opacity", ShaderValueType.FLOAT, "0.5")
            ),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils50, shaderNodeKind50, string) -> "wild_blend_screen("
               + shaderExpressionUtils50.resolve(shaderNodeKind50, "base")
               + ", "
               + shaderExpressionUtils50.resolve(shaderNodeKind50, "layer")
               + ", "
               + shaderExpressionUtils50.resolve(shaderNodeKind50, "opacity")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "blend_overlay",
            "Overlay Blend",
            "Blend",
            188.0F,
            List.of(
               ShaderPin.input("base", "base", ShaderValueType.VEC4, "vec4(0.02, 0.022, 0.028, 1.0)"),
               ShaderPin.input("layer", "layer", ShaderValueType.VEC4, "vec4(u_AccentBottom, 1.0)"),
               ShaderPin.input("opacity", "opacity", ShaderValueType.FLOAT, "0.5")
            ),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils51, shaderNodeKind51, string) -> "wild_blend_overlay("
               + shaderExpressionUtils51.resolve(shaderNodeKind51, "base")
               + ", "
               + shaderExpressionUtils51.resolve(shaderNodeKind51, "layer")
               + ", "
               + shaderExpressionUtils51.resolve(shaderNodeKind51, "opacity")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "sdf_circle",
            "SDF Circle",
            "SDF",
            184.0F,
            List.of(
               ShaderPin.input("uv", "uv", ShaderValueType.VEC2, "uv"),
               ShaderPin.input("center", "center", ShaderValueType.VEC2, "vec2(0.0)"),
               ShaderPin.input("radius", "radius", ShaderValueType.FLOAT, "0.25"),
               ShaderPin.input("softness", "soft", ShaderValueType.FLOAT, "0.08")
            ),
            List.of(ShaderPin.output("mask", "distance", ShaderValueType.FLOAT)),
            (shaderExpressionUtils52, shaderNodeKind52, string) -> shaderExpressionUtils52.check()
               ? "wild_sdf_circle("
                  + shaderExpressionUtils52.resolve(shaderNodeKind52, "uv")
                  + ", ("
                  + shaderExpressionUtils52.resolve(shaderNodeKind52, "center")
                  + ") * (u_ElementRect.zw * 0.5), ("
                  + shaderExpressionUtils52.resolve(shaderNodeKind52, "radius")
                  + ") * min(u_ElementRect.z, u_ElementRect.w) * 0.5, "
                  + shaderExpressionUtils52.resolve(shaderNodeKind52, "softness")
                  + ")"
               : "wild_sdf_circle("
                  + shaderExpressionUtils52.resolve(shaderNodeKind52, "uv")
                  + ", "
                  + shaderExpressionUtils52.resolve(shaderNodeKind52, "center")
                  + ", "
                  + shaderExpressionUtils52.resolve(shaderNodeKind52, "radius")
                  + ", "
                  + shaderExpressionUtils52.resolve(shaderNodeKind52, "softness")
                  + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "sdf_round_box",
            "SDF Rounded Box",
            "SDF",
            196.0F,
            List.of(
               ShaderPin.input("uv", "uv", ShaderValueType.VEC2, "uv"),
               ShaderPin.input("center", "center", ShaderValueType.VEC2, "vec2(0.0)"),
               ShaderPin.input("size", "size", ShaderValueType.VEC2, "vec2(0.95, 0.32)"),
               ShaderPin.input("radius", "radius", ShaderValueType.FLOAT, "0.08"),
               ShaderPin.input("softness", "soft", ShaderValueType.FLOAT, "0.06")
            ),
            List.of(ShaderPin.output("mask", "distance", ShaderValueType.FLOAT)),
            (shaderExpressionUtils53, shaderNodeKind53, string) -> shaderExpressionUtils53.check()
               ? "wild_sdf_round_box("
                  + shaderExpressionUtils53.resolve(shaderNodeKind53, "uv")
                  + ", ("
                  + shaderExpressionUtils53.resolve(shaderNodeKind53, "center")
                  + ") * (u_ElementRect.zw * 0.5), ("
                  + shaderExpressionUtils53.resolve(shaderNodeKind53, "size")
                  + ") * (u_ElementRect.zw * 0.5), ("
                  + shaderExpressionUtils53.resolve(shaderNodeKind53, "radius")
                  + ") * min(u_ElementRect.z, u_ElementRect.w) * 0.5, "
                  + shaderExpressionUtils53.resolve(shaderNodeKind53, "softness")
                  + ")"
               : "wild_sdf_round_box("
                  + shaderExpressionUtils53.resolve(shaderNodeKind53, "uv")
                  + ", "
                  + shaderExpressionUtils53.resolve(shaderNodeKind53, "center")
                  + ", "
                  + shaderExpressionUtils53.resolve(shaderNodeKind53, "size")
                  + ", "
                  + shaderExpressionUtils53.resolve(shaderNodeKind53, "radius")
                  + ", "
                  + shaderExpressionUtils53.resolve(shaderNodeKind53, "softness")
                  + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "simplex_noise_3d",
            "Simplex Noise 3D",
            "Generator",
            202.0F,
            List.of(
               ShaderPin.input("p", "p", ShaderValueType.VEC3, "vec3(globalUv * 2.0, u_Time * 0.08)"),
               ShaderPin.input("scale", "scale", ShaderValueType.FLOAT, "3.0")
            ),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils54, shaderNodeKind54, string) -> "(0.5 + 0.5 * wild_simplex3("
               + shaderExpressionUtils54.resolve(shaderNodeKind54, "p")
               + " * "
               + shaderExpressionUtils54.resolve(shaderNodeKind54, "scale")
               + "))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "voronoi",
            "Voronoi",
            "Generator",
            190.0F,
            List.of(
               ShaderPin.input("uv", "uv", ShaderValueType.VEC2, "globalUv"),
               ShaderPin.input("scale", "scale", ShaderValueType.FLOAT, "7.0"),
               ShaderPin.input("time", "time", ShaderValueType.FLOAT, "u_Time")
            ),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils55, shaderNodeKind55, string) -> "wild_voronoi("
               + shaderExpressionUtils55.resolve(shaderNodeKind55, "uv")
               + " * "
               + shaderExpressionUtils55.resolve(shaderNodeKind55, "scale")
               + ", "
               + shaderExpressionUtils55.resolve(shaderNodeKind55, "time")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "chromatic_aberration",
            "Chromatic Aberration",
            "VFX",
            218.0F,
            List.of(
               ShaderPin.input("color", "color", ShaderValueType.VEC4, "vec4(u_AccentTop, 1.0)"),
               ShaderPin.input("amount", "amount", ShaderValueType.FLOAT, "0.08"),
               ShaderPin.input("phase", "phase", ShaderValueType.FLOAT, "u_Time")
            ),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils56, shaderNodeKind56, string) -> "wild_chromatic("
               + shaderExpressionUtils56.resolve(shaderNodeKind56, "color")
               + ", vUv, "
               + shaderExpressionUtils56.resolve(shaderNodeKind56, "amount")
               + ", "
               + shaderExpressionUtils56.resolve(shaderNodeKind56, "phase")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "output_color",
            "Master Output",
            "Output",
            184.0F,
            List.of(
               ShaderPin.input("color", "color", ShaderValueType.VEC4, "vec4(0.02, 0.022, 0.028, 1.0)"),
               ShaderPin.input("alpha", "alpha", ShaderValueType.FLOAT, "1.0")
            ),
            List.of(),
            (shaderExpressionUtils57, shaderNodeKind57, string) -> "vec4(("
               + shaderExpressionUtils57.resolve(shaderNodeKind57, "color")
               + ").rgb, ("
               + shaderExpressionUtils57.resolve(shaderNodeKind57, "color")
               + ").a * "
               + shaderExpressionUtils57.resolve(shaderNodeKind57, "alpha")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "sdf_triangle",
            "SDF Triangle",
            "SDF",
            196.0F,
            List.of(
               ShaderPin.input("uv", "uv", ShaderValueType.VEC2, "uv"),
               ShaderPin.input("center", "center", ShaderValueType.VEC2, "vec2(0.0)"),
               ShaderPin.input("radius", "radius", ShaderValueType.FLOAT, "0.35"),
               ShaderPin.input("softness", "soft", ShaderValueType.FLOAT, "0.05")
            ),
            List.of(ShaderPin.output("mask", "distance", ShaderValueType.FLOAT)),
            (shaderExpressionUtils58, shaderNodeKind58, string) -> shaderExpressionUtils58.check()
               ? "wild_sdf_triangle("
                  + shaderExpressionUtils58.resolve(shaderNodeKind58, "uv")
                  + ", ("
                  + shaderExpressionUtils58.resolve(shaderNodeKind58, "center")
                  + ") * (u_ElementRect.zw * 0.5), ("
                  + shaderExpressionUtils58.resolve(shaderNodeKind58, "radius")
                  + ") * min(u_ElementRect.z, u_ElementRect.w) * 0.5, "
                  + shaderExpressionUtils58.resolve(shaderNodeKind58, "softness")
                  + ")"
               : "wild_sdf_triangle("
                  + shaderExpressionUtils58.resolve(shaderNodeKind58, "uv")
                  + ", "
                  + shaderExpressionUtils58.resolve(shaderNodeKind58, "center")
                  + ", "
                  + shaderExpressionUtils58.resolve(shaderNodeKind58, "radius")
                  + ", "
                  + shaderExpressionUtils58.resolve(shaderNodeKind58, "softness")
                  + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "sdf_hex",
            "SDF Hexagon",
            "SDF",
            196.0F,
            List.of(
               ShaderPin.input("uv", "uv", ShaderValueType.VEC2, "uv"),
               ShaderPin.input("center", "center", ShaderValueType.VEC2, "vec2(0.0)"),
               ShaderPin.input("radius", "radius", ShaderValueType.FLOAT, "0.28"),
               ShaderPin.input("softness", "soft", ShaderValueType.FLOAT, "0.05")
            ),
            List.of(ShaderPin.output("mask", "distance", ShaderValueType.FLOAT)),
            (shaderExpressionUtils59, shaderNodeKind59, string) -> shaderExpressionUtils59.check()
               ? "wild_sdf_hex("
                  + shaderExpressionUtils59.resolve(shaderNodeKind59, "uv")
                  + ", ("
                  + shaderExpressionUtils59.resolve(shaderNodeKind59, "center")
                  + ") * (u_ElementRect.zw * 0.5), ("
                  + shaderExpressionUtils59.resolve(shaderNodeKind59, "radius")
                  + ") * min(u_ElementRect.z, u_ElementRect.w) * 0.5, "
                  + shaderExpressionUtils59.resolve(shaderNodeKind59, "softness")
                  + ")"
               : "wild_sdf_hex("
                  + shaderExpressionUtils59.resolve(shaderNodeKind59, "uv")
                  + ", "
                  + shaderExpressionUtils59.resolve(shaderNodeKind59, "center")
                  + ", "
                  + shaderExpressionUtils59.resolve(shaderNodeKind59, "radius")
                  + ", "
                  + shaderExpressionUtils59.resolve(shaderNodeKind59, "softness")
                  + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "fbm_noise",
            "FBM Noise",
            "Generator",
            196.0F,
            List.of(
               ShaderPin.input("p", "p", ShaderValueType.VEC3, "vec3(globalUv * 3.0, u_Time * 0.12)"),
               ShaderPin.input("octaves", "oct", ShaderValueType.FLOAT, "5.0"),
               ShaderPin.input("scale", "scale", ShaderValueType.FLOAT, "1.8")
            ),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils60, shaderNodeKind60, string) -> "wild_fbm("
               + shaderExpressionUtils60.resolve(shaderNodeKind60, "p")
               + " * "
               + shaderExpressionUtils60.resolve(shaderNodeKind60, "scale")
               + ", int(clamp("
               + shaderExpressionUtils60.resolve(shaderNodeKind60, "octaves")
               + ", 1.0, 8.0)))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "polar_uv",
            "Polar UV",
            "Coords",
            184.0F,
            List.of(ShaderPin.input("uv", "uv", ShaderValueType.VEC2, "vUv"), ShaderPin.input("center", "center", ShaderValueType.VEC2, "vec2(0.5)")),
            List.of(ShaderPin.output("uv", "uv", ShaderValueType.VEC2)),
            (shaderExpressionUtils61, shaderNodeKind61, string) -> "wild_polar("
               + shaderExpressionUtils61.resolve(shaderNodeKind61, "uv")
               + ", "
               + shaderExpressionUtils61.resolve(shaderNodeKind61, "center")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "rotate_uv",
            "Rotate UV",
            "Coords",
            186.0F,
            List.of(
               ShaderPin.input("uv", "uv", ShaderValueType.VEC2, "vUv"),
               ShaderPin.input("center", "center", ShaderValueType.VEC2, "vec2(0.5)"),
               ShaderPin.input("angle", "angle", ShaderValueType.FLOAT, "u_Time")
            ),
            List.of(ShaderPin.output("uv", "uv", ShaderValueType.VEC2)),
            (shaderExpressionUtils62, shaderNodeKind62, string) -> "wild_rotate_uv("
               + shaderExpressionUtils62.resolve(shaderNodeKind62, "uv")
               + ", "
               + shaderExpressionUtils62.resolve(shaderNodeKind62, "center")
               + ", "
               + shaderExpressionUtils62.resolve(shaderNodeKind62, "angle")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "twist_uv",
            "Twist UV",
            "Coords",
            196.0F,
            List.of(
               ShaderPin.input("uv", "uv", ShaderValueType.VEC2, "vUv"),
               ShaderPin.input("center", "center", ShaderValueType.VEC2, "vec2(0.5)"),
               ShaderPin.input("strength", "strength", ShaderValueType.FLOAT, "2.6")
            ),
            List.of(ShaderPin.output("uv", "uv", ShaderValueType.VEC2)),
            (shaderExpressionUtils63, shaderNodeKind63, string) -> "wild_twist_uv("
               + shaderExpressionUtils63.resolve(shaderNodeKind63, "uv")
               + ", "
               + shaderExpressionUtils63.resolve(shaderNodeKind63, "center")
               + ", "
               + shaderExpressionUtils63.resolve(shaderNodeKind63, "strength")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "vignette",
            "Vignette",
            "VFX",
            196.0F,
            List.of(
               ShaderPin.input("uv", "uv", ShaderValueType.VEC2, "vUv"),
               ShaderPin.input("intensity", "intensity", ShaderValueType.FLOAT, "1.0"),
               ShaderPin.input("falloff", "falloff", ShaderValueType.FLOAT, "0.5")
            ),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils64, shaderNodeKind64, string) -> "wild_vignette("
               + shaderExpressionUtils64.resolve(shaderNodeKind64, "uv")
               + ", "
               + shaderExpressionUtils64.resolve(shaderNodeKind64, "intensity")
               + ", "
               + shaderExpressionUtils64.resolve(shaderNodeKind64, "falloff")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "posterize",
            "Posterize",
            "VFX",
            196.0F,
            List.of(ShaderPin.input("color", "color", ShaderValueType.VEC4, "vec4(0.5)"), ShaderPin.input("steps", "steps", ShaderValueType.FLOAT, "6.0")),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils65, shaderNodeKind65, string) -> "vec4(floor("
               + shaderExpressionUtils65.resolve(shaderNodeKind65, "color")
               + ".rgb * max("
               + shaderExpressionUtils65.resolve(shaderNodeKind65, "steps")
               + ", 1.0)) / max("
               + shaderExpressionUtils65.resolve(shaderNodeKind65, "steps")
               + ", 1.0), "
               + shaderExpressionUtils65.resolve(shaderNodeKind65, "color")
               + ".a)"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "bloom_lift",
            "Bloom Lift",
            "VFX",
            196.0F,
            List.of(
               ShaderPin.input("color", "color", ShaderValueType.VEC4, "vec4(0.5)"),
               ShaderPin.input("threshold", "threshold", ShaderValueType.FLOAT, "0.6"),
               ShaderPin.input("amount", "amount", ShaderValueType.FLOAT, "1.2")
            ),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils66, shaderNodeKind66, string) -> "wild_bloom_lift("
               + shaderExpressionUtils66.resolve(shaderNodeKind66, "color")
               + ", "
               + shaderExpressionUtils66.resolve(shaderNodeKind66, "threshold")
               + ", "
               + shaderExpressionUtils66.resolve(shaderNodeKind66, "amount")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "color_pulse",
            "Color Pulse",
            "Color",
            196.0F,
            List.of(
               ShaderPin.input("a", "a", ShaderValueType.VEC4, "vec4(u_AccentBottom, 1.0)"),
               ShaderPin.input("b", "b", ShaderValueType.VEC4, "vec4(u_AccentTop, 1.0)"),
               ShaderPin.input("speed", "speed", ShaderValueType.FLOAT, "1.0")
            ),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils67, shaderNodeKind67, string) -> "mix("
               + shaderExpressionUtils67.resolve(shaderNodeKind67, "a")
               + ", "
               + shaderExpressionUtils67.resolve(shaderNodeKind67, "b")
               + ", 0.5 + 0.5 * sin(u_Time * "
               + shaderExpressionUtils67.resolve(shaderNodeKind67, "speed")
               + "))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "color_screen_split",
            "Channel Split",
            "Color",
            196.0F,
            List.of(
               ShaderPin.input("color", "color", ShaderValueType.VEC4, "vec4(0.5)"), ShaderPin.input("amount", "amount", ShaderValueType.FLOAT, "0.04")
            ),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils68, shaderNodeKind68, string) -> "wild_channel_split("
               + shaderExpressionUtils68.resolve(shaderNodeKind68, "color")
               + ", "
               + shaderExpressionUtils68.resolve(shaderNodeKind68, "amount")
               + ", u_Time)"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "fresnel",
            "Fresnel Rim",
            "VFX",
            196.0F,
            List.of(ShaderPin.input("uv", "uv", ShaderValueType.VEC2, "vUv"), ShaderPin.input("power", "power", ShaderValueType.FLOAT, "3.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils69, shaderNodeKind69, string) -> "pow(max(1.0 - 2.0 * length("
               + shaderExpressionUtils69.resolve(shaderNodeKind69, "uv")
               + " - 0.5), 0.0), max("
               + shaderExpressionUtils69.resolve(shaderNodeKind69, "power")
               + ", 0.001))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "step_threshold",
            "Step Threshold",
            "Math",
            184.0F,
            List.of(ShaderPin.input("edge", "edge", ShaderValueType.FLOAT, "0.5"), ShaderPin.input("x", "x", ShaderValueType.FLOAT, "0.5")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils70, shaderNodeKind70, string) -> "step("
               + shaderExpressionUtils70.resolve(shaderNodeKind70, "edge")
               + ", "
               + shaderExpressionUtils70.resolve(shaderNodeKind70, "x")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "fract_node",
            "Fract",
            "Math",
            174.0F,
            List.of(ShaderPin.input("x", "x", ShaderValueType.FLOAT, "0.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils71, shaderNodeKind71, string) -> "fract(" + shaderExpressionUtils71.resolve(shaderNodeKind71, "x") + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "abs_node",
            "Abs",
            "Math",
            168.0F,
            List.of(ShaderPin.input("x", "x", ShaderValueType.FLOAT, "0.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils72, shaderNodeKind72, string) -> "abs(" + shaderExpressionUtils72.resolve(shaderNodeKind72, "x") + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "saturate_node",
            "Saturate",
            "Math",
            174.0F,
            List.of(ShaderPin.input("x", "x", ShaderValueType.FLOAT, "0.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils73, shaderNodeKind73, string) -> "clamp(" + shaderExpressionUtils73.resolve(shaderNodeKind73, "x") + ", 0.0, 1.0)"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "blend_multiply",
            "Multiply Blend",
            "Blend",
            196.0F,
            List.of(
               ShaderPin.input("base", "base", ShaderValueType.VEC4, "vec4(0.02, 0.022, 0.028, 1.0)"),
               ShaderPin.input("layer", "layer", ShaderValueType.VEC4, "vec4(u_AccentTop, 1.0)"),
               ShaderPin.input("opacity", "opacity", ShaderValueType.FLOAT, "0.5")
            ),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils74, shaderNodeKind74, string) -> "vec4(mix("
               + shaderExpressionUtils74.resolve(shaderNodeKind74, "base")
               + ".rgb, "
               + shaderExpressionUtils74.resolve(shaderNodeKind74, "base")
               + ".rgb * "
               + shaderExpressionUtils74.resolve(shaderNodeKind74, "layer")
               + ".rgb, clamp("
               + shaderExpressionUtils74.resolve(shaderNodeKind74, "opacity")
               + ", 0.0, 1.0)), max("
               + shaderExpressionUtils74.resolve(shaderNodeKind74, "base")
               + ".a, "
               + shaderExpressionUtils74.resolve(shaderNodeKind74, "layer")
               + ".a))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "blend_add",
            "Additive Blend",
            "Blend",
            196.0F,
            List.of(
               ShaderPin.input("base", "base", ShaderValueType.VEC4, "vec4(0.02, 0.022, 0.028, 1.0)"),
               ShaderPin.input("layer", "layer", ShaderValueType.VEC4, "vec4(u_AccentTop, 1.0)"),
               ShaderPin.input("opacity", "opacity", ShaderValueType.FLOAT, "0.5")
            ),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils75, shaderNodeKind75, string) -> "vec4(clamp("
               + shaderExpressionUtils75.resolve(shaderNodeKind75, "base")
               + ".rgb + "
               + shaderExpressionUtils75.resolve(shaderNodeKind75, "layer")
               + ".rgb * clamp("
               + shaderExpressionUtils75.resolve(shaderNodeKind75, "opacity")
               + ", 0.0, 1.0), 0.0, 1.0), max("
               + shaderExpressionUtils75.resolve(shaderNodeKind75, "base")
               + ".a, "
               + shaderExpressionUtils75.resolve(shaderNodeKind75, "layer")
               + ".a))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "iridescence",
            "Iridescence",
            "Color",
            200.0F,
            List.of(ShaderPin.input("t", "t", ShaderValueType.FLOAT, "0.5"), ShaderPin.input("speed", "speed", ShaderValueType.FLOAT, "0.8")),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils76, shaderNodeKind76, string) -> "wild_iridescence("
               + shaderExpressionUtils76.resolve(shaderNodeKind76, "t")
               + ", "
               + shaderExpressionUtils76.resolve(shaderNodeKind76, "speed")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "sdf_union",
            "SDF Union",
            "SDF Booleans",
            196.0F,
            List.of(
               ShaderPin.input("a", "a", ShaderValueType.FLOAT, "0.0"),
               ShaderPin.input("b", "b", ShaderValueType.FLOAT, "0.0"),
               ShaderPin.input("smoothness", "smooth", ShaderValueType.FLOAT, "0.05")
            ),
            List.of(ShaderPin.output("mask", "distance", ShaderValueType.FLOAT)),
            (shaderExpressionUtils77, shaderNodeKind77, string) -> shaderExpressionUtils77.check()
               ? "wild_sdf_union("
                  + shaderExpressionUtils77.resolve(shaderNodeKind77, "a")
                  + ", "
                  + shaderExpressionUtils77.resolve(shaderNodeKind77, "b")
                  + ", ("
                  + shaderExpressionUtils77.resolve(shaderNodeKind77, "smoothness")
                  + ") * min(u_ElementRect.z, u_ElementRect.w) * 0.5)"
               : "wild_sdf_union("
                  + shaderExpressionUtils77.resolve(shaderNodeKind77, "a")
                  + ", "
                  + shaderExpressionUtils77.resolve(shaderNodeKind77, "b")
                  + ", "
                  + shaderExpressionUtils77.resolve(shaderNodeKind77, "smoothness")
                  + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "sdf_subtract",
            "SDF Subtract",
            "SDF Booleans",
            200.0F,
            List.of(
               ShaderPin.input("a", "a", ShaderValueType.FLOAT, "0.0"),
               ShaderPin.input("b", "b", ShaderValueType.FLOAT, "0.0"),
               ShaderPin.input("smoothness", "smooth", ShaderValueType.FLOAT, "0.05")
            ),
            List.of(ShaderPin.output("mask", "distance", ShaderValueType.FLOAT)),
            (shaderExpressionUtils78, shaderNodeKind78, string) -> shaderExpressionUtils78.check()
               ? "wild_sdf_subtract("
                  + shaderExpressionUtils78.resolve(shaderNodeKind78, "a")
                  + ", "
                  + shaderExpressionUtils78.resolve(shaderNodeKind78, "b")
                  + ", ("
                  + shaderExpressionUtils78.resolve(shaderNodeKind78, "smoothness")
                  + ") * min(u_ElementRect.z, u_ElementRect.w) * 0.5)"
               : "wild_sdf_subtract("
                  + shaderExpressionUtils78.resolve(shaderNodeKind78, "a")
                  + ", "
                  + shaderExpressionUtils78.resolve(shaderNodeKind78, "b")
                  + ", "
                  + shaderExpressionUtils78.resolve(shaderNodeKind78, "smoothness")
                  + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "sdf_intersect",
            "SDF Intersect",
            "SDF Booleans",
            204.0F,
            List.of(
               ShaderPin.input("a", "a", ShaderValueType.FLOAT, "0.0"),
               ShaderPin.input("b", "b", ShaderValueType.FLOAT, "0.0"),
               ShaderPin.input("smoothness", "smooth", ShaderValueType.FLOAT, "0.05")
            ),
            List.of(ShaderPin.output("mask", "distance", ShaderValueType.FLOAT)),
            (shaderExpressionUtils79, shaderNodeKind79, string) -> shaderExpressionUtils79.check()
               ? "wild_sdf_intersect("
                  + shaderExpressionUtils79.resolve(shaderNodeKind79, "a")
                  + ", "
                  + shaderExpressionUtils79.resolve(shaderNodeKind79, "b")
                  + ", ("
                  + shaderExpressionUtils79.resolve(shaderNodeKind79, "smoothness")
                  + ") * min(u_ElementRect.z, u_ElementRect.w) * 0.5)"
               : "wild_sdf_intersect("
                  + shaderExpressionUtils79.resolve(shaderNodeKind79, "a")
                  + ", "
                  + shaderExpressionUtils79.resolve(shaderNodeKind79, "b")
                  + ", "
                  + shaderExpressionUtils79.resolve(shaderNodeKind79, "smoothness")
                  + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "sdf_star",
            "SDF Star",
            "SDF",
            198.0F,
            List.of(
               ShaderPin.input("uv", "uv", ShaderValueType.VEC2, "uv"),
               ShaderPin.input("center", "center", ShaderValueType.VEC2, "vec2(0.0)"),
               ShaderPin.input("radius", "radius", ShaderValueType.FLOAT, "0.32"),
               ShaderPin.input("points", "points", ShaderValueType.FLOAT, "5.0"),
               ShaderPin.input("softness", "soft", ShaderValueType.FLOAT, "0.05")
            ),
            List.of(ShaderPin.output("mask", "distance", ShaderValueType.FLOAT)),
            (shaderExpressionUtils80, shaderNodeKind80, string) -> shaderExpressionUtils80.check()
               ? "wild_sdf_star("
                  + shaderExpressionUtils80.resolve(shaderNodeKind80, "uv")
                  + ", ("
                  + shaderExpressionUtils80.resolve(shaderNodeKind80, "center")
                  + ") * (u_ElementRect.zw * 0.5), ("
                  + shaderExpressionUtils80.resolve(shaderNodeKind80, "radius")
                  + ") * min(u_ElementRect.z, u_ElementRect.w) * 0.5, "
                  + shaderExpressionUtils80.resolve(shaderNodeKind80, "points")
                  + ", "
                  + shaderExpressionUtils80.resolve(shaderNodeKind80, "softness")
                  + ")"
               : "wild_sdf_star("
                  + shaderExpressionUtils80.resolve(shaderNodeKind80, "uv")
                  + ", "
                  + shaderExpressionUtils80.resolve(shaderNodeKind80, "center")
                  + ", "
                  + shaderExpressionUtils80.resolve(shaderNodeKind80, "radius")
                  + ", "
                  + shaderExpressionUtils80.resolve(shaderNodeKind80, "points")
                  + ", "
                  + shaderExpressionUtils80.resolve(shaderNodeKind80, "softness")
                  + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "math_remap",
            "Remap",
            "Math",
            196.0F,
            List.of(
               ShaderPin.input("v", "v", ShaderValueType.FLOAT, "0.5"),
               ShaderPin.input("inMin", "inMin", ShaderValueType.FLOAT, "0.0"),
               ShaderPin.input("inMax", "inMax", ShaderValueType.FLOAT, "1.0"),
               ShaderPin.input("outMin", "outMin", ShaderValueType.FLOAT, "0.0"),
               ShaderPin.input("outMax", "outMax", ShaderValueType.FLOAT, "1.0")
            ),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils81, shaderNodeKind81, string) -> "wild_remap("
               + shaderExpressionUtils81.resolve(shaderNodeKind81, "v")
               + ", "
               + shaderExpressionUtils81.resolve(shaderNodeKind81, "inMin")
               + ", "
               + shaderExpressionUtils81.resolve(shaderNodeKind81, "inMax")
               + ", "
               + shaderExpressionUtils81.resolve(shaderNodeKind81, "outMin")
               + ", "
               + shaderExpressionUtils81.resolve(shaderNodeKind81, "outMax")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "math_clamp",
            "Clamp",
            "Math",
            184.0F,
            List.of(
               ShaderPin.input("x", "x", ShaderValueType.FLOAT, "0.5"),
               ShaderPin.input("min", "min", ShaderValueType.FLOAT, "0.0"),
               ShaderPin.input("max", "max", ShaderValueType.FLOAT, "1.0")
            ),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils82, shaderNodeKind82, string) -> "clamp("
               + shaderExpressionUtils82.resolve(shaderNodeKind82, "x")
               + ", "
               + shaderExpressionUtils82.resolve(shaderNodeKind82, "min")
               + ", "
               + shaderExpressionUtils82.resolve(shaderNodeKind82, "max")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "math_floor",
            "Floor",
            "Math",
            168.0F,
            List.of(ShaderPin.input("x", "x", ShaderValueType.FLOAT, "0.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils83, shaderNodeKind83, string) -> "floor(" + shaderExpressionUtils83.resolve(shaderNodeKind83, "x") + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "math_ceil",
            "Ceil",
            "Math",
            168.0F,
            List.of(ShaderPin.input("x", "x", ShaderValueType.FLOAT, "0.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils84, shaderNodeKind84, string) -> "ceil(" + shaderExpressionUtils84.resolve(shaderNodeKind84, "x") + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "math_length",
            "Length",
            "Math",
            184.0F,
            List.of(ShaderPin.input("v", "v", ShaderValueType.VEC2, "vUv - 0.5")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils85, shaderNodeKind85, string) -> "length(" + shaderExpressionUtils85.resolve(shaderNodeKind85, "v") + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "math_distance",
            "Distance",
            "Math",
            192.0F,
            List.of(ShaderPin.input("a", "a", ShaderValueType.VEC2, "vUv"), ShaderPin.input("b", "b", ShaderValueType.VEC2, "vec2(0.5)")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils86, shaderNodeKind86, string) -> "distance("
               + shaderExpressionUtils86.resolve(shaderNodeKind86, "a")
               + ", "
               + shaderExpressionUtils86.resolve(shaderNodeKind86, "b")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "math_power",
            "Power",
            "Math",
            192.0F,
            List.of(ShaderPin.input("x", "x", ShaderValueType.FLOAT, "0.5"), ShaderPin.input("y", "y", ShaderValueType.FLOAT, "2.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils87, shaderNodeKind87, string) -> "pow(max("
               + shaderExpressionUtils87.resolve(shaderNodeKind87, "x")
               + ", 0.0), "
               + shaderExpressionUtils87.resolve(shaderNodeKind87, "y")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "math_mod",
            "Mod",
            "Math",
            188.0F,
            List.of(ShaderPin.input("x", "x", ShaderValueType.FLOAT, "0.0"), ShaderPin.input("y", "y", ShaderValueType.FLOAT, "1.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils88, shaderNodeKind88, string) -> "mod("
               + shaderExpressionUtils88.resolve(shaderNodeKind88, "x")
               + ", (abs("
               + shaderExpressionUtils88.resolve(shaderNodeKind88, "y")
               + ") < 1e-5 ? 1e-5 : "
               + shaderExpressionUtils88.resolve(shaderNodeKind88, "y")
               + "))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "math_dot",
            "Dot",
            "Math",
            188.0F,
            List.of(ShaderPin.input("a", "a", ShaderValueType.VEC2, "vUv"), ShaderPin.input("b", "b", ShaderValueType.VEC2, "vec2(1.0)")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils89, shaderNodeKind89, string) -> "dot("
               + shaderExpressionUtils89.resolve(shaderNodeKind89, "a")
               + ", "
               + shaderExpressionUtils89.resolve(shaderNodeKind89, "b")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "math_subtract",
            "Subtract",
            "Math",
            184.0F,
            List.of(ShaderPin.input("a", "a", ShaderValueType.FLOAT, "0.0"), ShaderPin.input("b", "b", ShaderValueType.FLOAT, "0.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils90, shaderNodeKind90, string) -> "("
               + shaderExpressionUtils90.resolve(shaderNodeKind90, "a")
               + " - "
               + shaderExpressionUtils90.resolve(shaderNodeKind90, "b")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "math_divide",
            "Divide",
            "Math",
            184.0F,
            List.of(ShaderPin.input("a", "a", ShaderValueType.FLOAT, "1.0"), ShaderPin.input("b", "b", ShaderValueType.FLOAT, "1.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils91, shaderNodeKind91, string) -> "("
               + shaderExpressionUtils91.resolve(shaderNodeKind91, "a")
               + " / max("
               + shaderExpressionUtils91.resolve(shaderNodeKind91, "b")
               + ", 1e-5))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "color_gradient_map",
            "Gradient Map",
            "Color",
            220.0F,
            List.of(
               ShaderPin.input("t", "t", ShaderValueType.FLOAT, "0.5"),
               ShaderPin.input("a", "a", ShaderValueType.VEC4, "vec4(u_AccentBottom, 1.0)"),
               ShaderPin.input("b", "b", ShaderValueType.VEC4, "vec4(u_AccentTop, 1.0)"),
               ShaderPin.input("c", "c", ShaderValueType.VEC4, "vec4(1.0)")
            ),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils92, shaderNodeKind92, string) -> "wild_gradient_map("
               + shaderExpressionUtils92.resolve(shaderNodeKind92, "t")
               + ", "
               + shaderExpressionUtils92.resolve(shaderNodeKind92, "a")
               + ", "
               + shaderExpressionUtils92.resolve(shaderNodeKind92, "b")
               + ", "
               + shaderExpressionUtils92.resolve(shaderNodeKind92, "c")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "color_desaturate",
            "Desaturate",
            "Color",
            192.0F,
            List.of(ShaderPin.input("color", "color", ShaderValueType.VEC4, "vec4(1.0)"), ShaderPin.input("amount", "amount", ShaderValueType.FLOAT, "1.0")),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils93, shaderNodeKind93, string) -> "vec4(wild_desaturate("
               + shaderExpressionUtils93.resolve(shaderNodeKind93, "color")
               + ".rgb, "
               + shaderExpressionUtils93.resolve(shaderNodeKind93, "amount")
               + "), "
               + shaderExpressionUtils93.resolve(shaderNodeKind93, "color")
               + ".a)"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "color_invert",
            "Invert",
            "Color",
            188.0F,
            List.of(ShaderPin.input("color", "color", ShaderValueType.VEC4, "vec4(0.5)"), ShaderPin.input("amount", "amount", ShaderValueType.FLOAT, "1.0")),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils94, shaderNodeKind94, string) -> "vec4(wild_invert("
               + shaderExpressionUtils94.resolve(shaderNodeKind94, "color")
               + ".rgb, "
               + shaderExpressionUtils94.resolve(shaderNodeKind94, "amount")
               + "), "
               + shaderExpressionUtils94.resolve(shaderNodeKind94, "color")
               + ".a)"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "color_hsv",
            "HSV → RGB",
            "Color",
            196.0F,
            List.of(
               ShaderPin.input("h", "hue", ShaderValueType.FLOAT, "0.0"),
               ShaderPin.input("s", "sat", ShaderValueType.FLOAT, "1.0"),
               ShaderPin.input("v", "val", ShaderValueType.FLOAT, "1.0")
            ),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils95, shaderNodeKind95, string) -> "vec4(wild_hsv2rgb(vec3("
               + shaderExpressionUtils95.resolve(shaderNodeKind95, "h")
               + ", "
               + shaderExpressionUtils95.resolve(shaderNodeKind95, "s")
               + ", "
               + shaderExpressionUtils95.resolve(shaderNodeKind95, "v")
               + ")), 1.0)"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "time_bpm",
            "BPM Sync",
            "Time",
            192.0F,
            List.of(ShaderPin.input("bpm", "bpm", ShaderValueType.FLOAT, "128.0"), ShaderPin.input("strength", "shape", ShaderValueType.FLOAT, "2.0")),
            List.of(ShaderPin.output("pulse", "pulse", ShaderValueType.FLOAT)),
            (shaderExpressionUtils96, shaderNodeKind96, string) -> "wild_bpm("
               + shaderExpressionUtils96.resolve(shaderNodeKind96, "bpm")
               + ", "
               + shaderExpressionUtils96.resolve(shaderNodeKind96, "strength")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "time_pulse",
            "Pulse",
            "Time",
            184.0F,
            List.of(ShaderPin.input("t", "t", ShaderValueType.FLOAT, "u_Time"), ShaderPin.input("duty", "duty", ShaderValueType.FLOAT, "0.5")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils97, shaderNodeKind97, string) -> "wild_pulse("
               + shaderExpressionUtils97.resolve(shaderNodeKind97, "t")
               + ", "
               + shaderExpressionUtils97.resolve(shaderNodeKind97, "duty")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "input_view_dir",
            "View Direction",
            "Inputs",
            178.0F,
            List.of(),
            List.of(ShaderPin.output("dir", "dir", ShaderValueType.VEC2)),
            (shaderExpressionUtils98, shaderNodeKind98, string) -> "wild_view_dir(vUv)"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "input_normal",
            "Normal (UV slope)",
            "Inputs",
            192.0F,
            List.of(ShaderPin.input("strength", "strength", ShaderValueType.FLOAT, "4.0")),
            List.of(ShaderPin.output("normal", "normal", ShaderValueType.VEC3)),
            (shaderExpressionUtils99, shaderNodeKind99, string) -> "wild_normal_from_uv(vUv, " + shaderExpressionUtils99.resolve(shaderNodeKind99, "strength") + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "input_resolution",
            "Resolution",
            "Inputs",
            178.0F,
            List.of(),
            List.of(ShaderPin.output("res", "res", ShaderValueType.VEC2)),
            (shaderExpressionUtils100, shaderNodeKind100, string) -> "u_Resolution"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "input_alpha",
            "Alpha",
            "Inputs",
            178.0F,
            List.of(),
            List.of(ShaderPin.output("alpha", "alpha", ShaderValueType.FLOAT)),
            (shaderExpressionUtils101, shaderNodeKind101, string) -> "u_Alpha"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "vec2_split",
            "Split Vec2",
            "Math",
            188.0F,
            List.of(ShaderPin.input("v", "v", ShaderValueType.VEC2, "vUv")),
            List.of(ShaderPin.output("x", "x", ShaderValueType.FLOAT), ShaderPin.output("y", "y", ShaderValueType.FLOAT)),
            (shaderExpressionUtils102, shaderNodeKind102, string) -> "x".equals(string)
               ? "(" + shaderExpressionUtils102.resolve(shaderNodeKind102, "v") + ").x"
               : "(" + shaderExpressionUtils102.resolve(shaderNodeKind102, "v") + ").y"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "math_max",
            "Max",
            "Math",
            184.0F,
            List.of(ShaderPin.input("a", "a", ShaderValueType.FLOAT, "0.0"), ShaderPin.input("b", "b", ShaderValueType.FLOAT, "0.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils103, shaderNodeKind103, string) -> "max("
               + shaderExpressionUtils103.resolve(shaderNodeKind103, "a")
               + ", "
               + shaderExpressionUtils103.resolve(shaderNodeKind103, "b")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "math_min",
            "Min",
            "Math",
            184.0F,
            List.of(ShaderPin.input("a", "a", ShaderValueType.FLOAT, "0.0"), ShaderPin.input("b", "b", ShaderValueType.FLOAT, "0.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils104, shaderNodeKind104, string) -> "min("
               + shaderExpressionUtils104.resolve(shaderNodeKind104, "a")
               + ", "
               + shaderExpressionUtils104.resolve(shaderNodeKind104, "b")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "math_sign",
            "Sign",
            "Math",
            172.0F,
            List.of(ShaderPin.input("x", "x", ShaderValueType.FLOAT, "0.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils105, shaderNodeKind105, string) -> "sign(" + shaderExpressionUtils105.resolve(shaderNodeKind105, "x") + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "math_sqrt",
            "Square Root",
            "Math",
            178.0F,
            List.of(ShaderPin.input("x", "x", ShaderValueType.FLOAT, "1.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils106, shaderNodeKind106, string) -> "sqrt(max(" + shaderExpressionUtils106.resolve(shaderNodeKind106, "x") + ", 0.0))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "math_exp",
            "Exp",
            "Math",
            172.0F,
            List.of(ShaderPin.input("x", "x", ShaderValueType.FLOAT, "0.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils107, shaderNodeKind107, string) -> "exp(" + shaderExpressionUtils107.resolve(shaderNodeKind107, "x") + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "math_log",
            "Log",
            "Math",
            172.0F,
            List.of(ShaderPin.input("x", "x", ShaderValueType.FLOAT, "1.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils108, shaderNodeKind108, string) -> "log(max(" + shaderExpressionUtils108.resolve(shaderNodeKind108, "x") + ", 1e-6))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "math_one_minus",
            "One Minus",
            "Math",
            178.0F,
            List.of(ShaderPin.input("x", "x", ShaderValueType.FLOAT, "0.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils109, shaderNodeKind109, string) -> "(1.0 - " + shaderExpressionUtils109.resolve(shaderNodeKind109, "x") + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "math_reciprocal",
            "Reciprocal",
            "Math",
            184.0F,
            List.of(ShaderPin.input("x", "x", ShaderValueType.FLOAT, "1.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils110, shaderNodeKind110, string) -> "(1.0 / (abs("
               + shaderExpressionUtils110.resolve(shaderNodeKind110, "x")
               + ") < 1e-5 ? 1e-5 : "
               + shaderExpressionUtils110.resolve(shaderNodeKind110, "x")
               + "))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "math_round",
            "Round",
            "Math",
            172.0F,
            List.of(ShaderPin.input("x", "x", ShaderValueType.FLOAT, "0.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils111, shaderNodeKind111, string) -> "floor(" + shaderExpressionUtils111.resolve(shaderNodeKind111, "x") + " + 0.5)"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "math_cos",
            "Cosine",
            "Math",
            172.0F,
            List.of(ShaderPin.input("x", "x", ShaderValueType.FLOAT, "0.0"), ShaderPin.input("freq", "freq", ShaderValueType.FLOAT, "1.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils112, shaderNodeKind112, string) -> "cos(("
               + shaderExpressionUtils112.resolve(shaderNodeKind112, "x")
               + ") * ("
               + shaderExpressionUtils112.resolve(shaderNodeKind112, "freq")
               + "))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "math_sin_raw",
            "Sine Raw",
            "Math",
            178.0F,
            List.of(ShaderPin.input("x", "x", ShaderValueType.FLOAT, "0.0"), ShaderPin.input("freq", "freq", ShaderValueType.FLOAT, "1.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils113, shaderNodeKind113, string) -> "sin(("
               + shaderExpressionUtils113.resolve(shaderNodeKind113, "x")
               + ") * ("
               + shaderExpressionUtils113.resolve(shaderNodeKind113, "freq")
               + "))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "math_atan2",
            "Atan2",
            "Math",
            178.0F,
            List.of(ShaderPin.input("a", "a", ShaderValueType.FLOAT, "1.0"), ShaderPin.input("b", "b", ShaderValueType.FLOAT, "1.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils114, shaderNodeKind114, string) -> "atan("
               + shaderExpressionUtils114.resolve(shaderNodeKind114, "a")
               + ", "
               + shaderExpressionUtils114.resolve(shaderNodeKind114, "b")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "vec_add3",
            "Add Vec3",
            "Vector",
            178.0F,
            List.of(ShaderPin.input("a", "a", ShaderValueType.VEC3, "vec3(0.0)"), ShaderPin.input("b", "b", ShaderValueType.VEC3, "vec3(0.0)")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.VEC3)),
            (shaderExpressionUtils115, shaderNodeKind115, string) -> "("
               + shaderExpressionUtils115.resolve(shaderNodeKind115, "a")
               + " + "
               + shaderExpressionUtils115.resolve(shaderNodeKind115, "b")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "vec_sub3",
            "Subtract Vec3",
            "Vector",
            190.0F,
            List.of(ShaderPin.input("a", "a", ShaderValueType.VEC3, "vec3(0.0)"), ShaderPin.input("b", "b", ShaderValueType.VEC3, "vec3(0.0)")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.VEC3)),
            (shaderExpressionUtils116, shaderNodeKind116, string) -> "("
               + shaderExpressionUtils116.resolve(shaderNodeKind116, "a")
               + " - "
               + shaderExpressionUtils116.resolve(shaderNodeKind116, "b")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "vec_mul3",
            "Multiply Vec3",
            "Vector",
            190.0F,
            List.of(ShaderPin.input("a", "a", ShaderValueType.VEC3, "vec3(1.0)"), ShaderPin.input("b", "b", ShaderValueType.VEC3, "vec3(1.0)")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.VEC3)),
            (shaderExpressionUtils117, shaderNodeKind117, string) -> "("
               + shaderExpressionUtils117.resolve(shaderNodeKind117, "a")
               + " * "
               + shaderExpressionUtils117.resolve(shaderNodeKind117, "b")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "vec_scale3",
            "Scale Vec3",
            "Vector",
            184.0F,
            List.of(ShaderPin.input("v", "v", ShaderValueType.VEC3, "vec3(1.0)"), ShaderPin.input("s", "s", ShaderValueType.FLOAT, "1.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.VEC3)),
            (shaderExpressionUtils118, shaderNodeKind118, string) -> "("
               + shaderExpressionUtils118.resolve(shaderNodeKind118, "v")
               + " * "
               + shaderExpressionUtils118.resolve(shaderNodeKind118, "s")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "vec_normalize",
            "Normalize",
            "Vector",
            184.0F,
            List.of(ShaderPin.input("v", "v", ShaderValueType.VEC3, "vec3(0.0,0.0,1.0)")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.VEC3)),
            (shaderExpressionUtils119, shaderNodeKind119, string) -> "normalize(" + shaderExpressionUtils119.resolve(shaderNodeKind119, "v") + " + 1e-6)"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "vec_cross",
            "Cross",
            "Vector",
            178.0F,
            List.of(ShaderPin.input("a", "a", ShaderValueType.VEC3, "vec3(0.0)"), ShaderPin.input("b", "b", ShaderValueType.VEC3, "vec3(0.0)")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.VEC3)),
            (shaderExpressionUtils120, shaderNodeKind120, string) -> "cross("
               + shaderExpressionUtils120.resolve(shaderNodeKind120, "a")
               + ", "
               + shaderExpressionUtils120.resolve(shaderNodeKind120, "b")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "vec_reflect",
            "Reflect",
            "Vector",
            184.0F,
            List.of(
               ShaderPin.input("i", "i", ShaderValueType.VEC3, "vec3(0.0,0.0,-1.0)"), ShaderPin.input("n", "n", ShaderValueType.VEC3, "vec3(0.0,0.0,1.0)")
            ),
            List.of(ShaderPin.output("value", "value", ShaderValueType.VEC3)),
            (shaderExpressionUtils121, shaderNodeKind121, string) -> "reflect("
               + shaderExpressionUtils121.resolve(shaderNodeKind121, "i")
               + ", normalize("
               + shaderExpressionUtils121.resolve(shaderNodeKind121, "n")
               + "))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "vec_dot3",
            "Dot Vec3",
            "Vector",
            178.0F,
            List.of(ShaderPin.input("a", "a", ShaderValueType.VEC3, "vec3(0.0)"), ShaderPin.input("b", "b", ShaderValueType.VEC3, "vec3(0.0)")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils122, shaderNodeKind122, string) -> "dot("
               + shaderExpressionUtils122.resolve(shaderNodeKind122, "a")
               + ", "
               + shaderExpressionUtils122.resolve(shaderNodeKind122, "b")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "vec_lerp3",
            "Lerp Vec3",
            "Vector",
            184.0F,
            List.of(
               ShaderPin.input("a", "a", ShaderValueType.VEC3, "vec3(0.0)"),
               ShaderPin.input("b", "b", ShaderValueType.VEC3, "vec3(0.0)"),
               ShaderPin.input("t", "t", ShaderValueType.FLOAT, "0.5")
            ),
            List.of(ShaderPin.output("value", "value", ShaderValueType.VEC3)),
            (shaderExpressionUtils123, shaderNodeKind123, string) -> "mix("
               + shaderExpressionUtils123.resolve(shaderNodeKind123, "a")
               + ", "
               + shaderExpressionUtils123.resolve(shaderNodeKind123, "b")
               + ", "
               + shaderExpressionUtils123.resolve(shaderNodeKind123, "t")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "vec2_add",
            "Add Vec2",
            "Vector",
            178.0F,
            List.of(ShaderPin.input("a", "a", ShaderValueType.VEC2, "vec2(0.0)"), ShaderPin.input("b", "b", ShaderValueType.VEC2, "vec2(0.0)")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.VEC2)),
            (shaderExpressionUtils124, shaderNodeKind124, string) -> "("
               + shaderExpressionUtils124.resolve(shaderNodeKind124, "a")
               + " + "
               + shaderExpressionUtils124.resolve(shaderNodeKind124, "b")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "vec2_mul",
            "Multiply Vec2",
            "Vector",
            190.0F,
            List.of(ShaderPin.input("a", "a", ShaderValueType.VEC2, "vec2(1.0)"), ShaderPin.input("b", "b", ShaderValueType.VEC2, "vec2(1.0)")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.VEC2)),
            (shaderExpressionUtils125, shaderNodeKind125, string) -> "("
               + shaderExpressionUtils125.resolve(shaderNodeKind125, "a")
               + " * "
               + shaderExpressionUtils125.resolve(shaderNodeKind125, "b")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "vec4_add",
            "Add Vec4",
            "Vector",
            178.0F,
            List.of(ShaderPin.input("a", "a", ShaderValueType.VEC4, "vec4(0.0)"), ShaderPin.input("b", "b", ShaderValueType.VEC4, "vec4(0.0)")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.VEC4)),
            (shaderExpressionUtils126, shaderNodeKind126, string) -> "("
               + shaderExpressionUtils126.resolve(shaderNodeKind126, "a")
               + " + "
               + shaderExpressionUtils126.resolve(shaderNodeKind126, "b")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "vec4_scale",
            "Scale Vec4",
            "Vector",
            184.0F,
            List.of(ShaderPin.input("v", "v", ShaderValueType.VEC4, "vec4(1.0)"), ShaderPin.input("s", "s", ShaderValueType.FLOAT, "1.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.VEC4)),
            (shaderExpressionUtils127, shaderNodeKind127, string) -> "("
               + shaderExpressionUtils127.resolve(shaderNodeKind127, "v")
               + " * "
               + shaderExpressionUtils127.resolve(shaderNodeKind127, "s")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "combine_vec3",
            "Combine Vec3",
            "Vector",
            184.0F,
            List.of(
               ShaderPin.input("x", "x", ShaderValueType.FLOAT, "0.0"),
               ShaderPin.input("y", "y", ShaderValueType.FLOAT, "0.0"),
               ShaderPin.input("z", "z", ShaderValueType.FLOAT, "0.0")
            ),
            List.of(ShaderPin.output("value", "value", ShaderValueType.VEC3)),
            (shaderExpressionUtils128, shaderNodeKind128, string) -> "vec3("
               + shaderExpressionUtils128.resolve(shaderNodeKind128, "x")
               + ", "
               + shaderExpressionUtils128.resolve(shaderNodeKind128, "y")
               + ", "
               + shaderExpressionUtils128.resolve(shaderNodeKind128, "z")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "combine_vec4",
            "Combine Vec4",
            "Vector",
            190.0F,
            List.of(
               ShaderPin.input("x", "x", ShaderValueType.FLOAT, "0.0"),
               ShaderPin.input("y", "y", ShaderValueType.FLOAT, "0.0"),
               ShaderPin.input("z", "z", ShaderValueType.FLOAT, "0.0"),
               ShaderPin.input("w", "w", ShaderValueType.FLOAT, "1.0")
            ),
            List.of(ShaderPin.output("value", "value", ShaderValueType.VEC4)),
            (shaderExpressionUtils129, shaderNodeKind129, string) -> "vec4("
               + shaderExpressionUtils129.resolve(shaderNodeKind129, "x")
               + ", "
               + shaderExpressionUtils129.resolve(shaderNodeKind129, "y")
               + ", "
               + shaderExpressionUtils129.resolve(shaderNodeKind129, "z")
               + ", "
               + shaderExpressionUtils129.resolve(shaderNodeKind129, "w")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "split_vec3",
            "Split Vec3",
            "Vector",
            184.0F,
            List.of(ShaderPin.input("v", "v", ShaderValueType.VEC3, "vec3(0.0)")),
            List.of(
               ShaderPin.output("x", "x", ShaderValueType.FLOAT),
               ShaderPin.output("y", "y", ShaderValueType.FLOAT),
               ShaderPin.output("z", "z", ShaderValueType.FLOAT)
            ),
            (shaderExpressionUtils130, shaderNodeKind130, string) -> "x".equals(string)
               ? "(" + shaderExpressionUtils130.resolve(shaderNodeKind130, "v") + ").x"
               : (
                  "y".equals(string)
                     ? "(" + shaderExpressionUtils130.resolve(shaderNodeKind130, "v") + ").y"
                     : "(" + shaderExpressionUtils130.resolve(shaderNodeKind130, "v") + ").z"
               )
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "split_vec4",
            "Split Vec4",
            "Vector",
            190.0F,
            List.of(ShaderPin.input("v", "v", ShaderValueType.VEC4, "vec4(0.0)")),
            List.of(
               ShaderPin.output("x", "x", ShaderValueType.FLOAT),
               ShaderPin.output("y", "y", ShaderValueType.FLOAT),
               ShaderPin.output("z", "z", ShaderValueType.FLOAT),
               ShaderPin.output("w", "w", ShaderValueType.FLOAT)
            ),
            (shaderExpressionUtils131, shaderNodeKind131, string) -> "x".equals(string)
               ? "(" + shaderExpressionUtils131.resolve(shaderNodeKind131, "v") + ").x"
               : (
                  "y".equals(string)
                     ? "(" + shaderExpressionUtils131.resolve(shaderNodeKind131, "v") + ").y"
                     : (
                        "z".equals(string)
                           ? "(" + shaderExpressionUtils131.resolve(shaderNodeKind131, "v") + ").z"
                           : "(" + shaderExpressionUtils131.resolve(shaderNodeKind131, "v") + ").w"
                     )
               )
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "uv_tiling_offset",
            "Tiling And Offset",
            "Coords",
            196.0F,
            List.of(
               ShaderPin.input("uv", "uv", ShaderValueType.VEC2, "uv"),
               ShaderPin.input("tiling", "tiling", ShaderValueType.VEC2, "vec2(1.0)"),
               ShaderPin.input("offset", "offset", ShaderValueType.VEC2, "vec2(0.0)")
            ),
            List.of(ShaderPin.output("uv", "uv", ShaderValueType.VEC2)),
            (shaderExpressionUtils132, shaderNodeKind132, string) -> "("
               + shaderExpressionUtils132.resolve(shaderNodeKind132, "uv")
               + " * "
               + shaderExpressionUtils132.resolve(shaderNodeKind132, "tiling")
               + " + "
               + shaderExpressionUtils132.resolve(shaderNodeKind132, "offset")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "uv_panner",
            "Panner",
            "Coords",
            186.0F,
            List.of(
               ShaderPin.input("uv", "uv", ShaderValueType.VEC2, "uv"),
               ShaderPin.input("speed", "speed", ShaderValueType.VEC2, "vec2(0.1)"),
               ShaderPin.input("time", "time", ShaderValueType.FLOAT, "u_Time")
            ),
            List.of(ShaderPin.output("uv", "uv", ShaderValueType.VEC2)),
            (shaderExpressionUtils133, shaderNodeKind133, string) -> "("
               + shaderExpressionUtils133.resolve(shaderNodeKind133, "uv")
               + " + "
               + shaderExpressionUtils133.resolve(shaderNodeKind133, "speed")
               + " * "
               + shaderExpressionUtils133.resolve(shaderNodeKind133, "time")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "uv_radial_shear",
            "Radial Shear",
            "Coords",
            196.0F,
            List.of(
               ShaderPin.input("uv", "uv", ShaderValueType.VEC2, "vUv"),
               ShaderPin.input("center", "center", ShaderValueType.VEC2, "vec2(0.5)"),
               ShaderPin.input("strength", "strength", ShaderValueType.FLOAT, "6.0")
            ),
            List.of(ShaderPin.output("uv", "uv", ShaderValueType.VEC2)),
            (shaderExpressionUtils134, shaderNodeKind134, string) -> "wild_radial_shear("
               + shaderExpressionUtils134.resolve(shaderNodeKind134, "uv")
               + ", "
               + shaderExpressionUtils134.resolve(shaderNodeKind134, "center")
               + ", "
               + shaderExpressionUtils134.resolve(shaderNodeKind134, "strength")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "uv_spherize",
            "Spherize",
            "Coords",
            190.0F,
            List.of(
               ShaderPin.input("uv", "uv", ShaderValueType.VEC2, "vUv"),
               ShaderPin.input("center", "center", ShaderValueType.VEC2, "vec2(0.5)"),
               ShaderPin.input("strength", "strength", ShaderValueType.FLOAT, "0.5")
            ),
            List.of(ShaderPin.output("uv", "uv", ShaderValueType.VEC2)),
            (shaderExpressionUtils135, shaderNodeKind135, string) -> "wild_spherize("
               + shaderExpressionUtils135.resolve(shaderNodeKind135, "uv")
               + ", "
               + shaderExpressionUtils135.resolve(shaderNodeKind135, "center")
               + ", "
               + shaderExpressionUtils135.resolve(shaderNodeKind135, "strength")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "checkerboard",
            "Checkerboard",
            "Generator",
            200.0F,
            List.of(
               ShaderPin.input("uv", "uv", ShaderValueType.VEC2, "vUv"),
               ShaderPin.input("freq", "freq", ShaderValueType.VEC2, "vec2(6.0)"),
               ShaderPin.input("a", "a", ShaderValueType.VEC4, "vec4(0.05,0.05,0.06,1.0)"),
               ShaderPin.input("b", "b", ShaderValueType.VEC4, "vec4(u_AccentTop,1.0)")
            ),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils136, shaderNodeKind136, string) -> "mix("
               + shaderExpressionUtils136.resolve(shaderNodeKind136, "a")
               + ", "
               + shaderExpressionUtils136.resolve(shaderNodeKind136, "b")
               + ", wild_checker("
               + shaderExpressionUtils136.resolve(shaderNodeKind136, "uv")
               + ", "
               + shaderExpressionUtils136.resolve(shaderNodeKind136, "freq")
               + "))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "simple_noise",
            "Simple Noise",
            "Generator",
            190.0F,
            List.of(ShaderPin.input("uv", "uv", ShaderValueType.VEC2, "globalUv"), ShaderPin.input("scale", "scale", ShaderValueType.FLOAT, "5.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils137, shaderNodeKind137, string) -> "wild_gnoise2("
               + shaderExpressionUtils137.resolve(shaderNodeKind137, "uv")
               + " * "
               + shaderExpressionUtils137.resolve(shaderNodeKind137, "scale")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "contrast",
            "Contrast",
            "Color",
            190.0F,
            List.of(ShaderPin.input("color", "color", ShaderValueType.VEC4, "vec4(0.5)"), ShaderPin.input("amount", "amount", ShaderValueType.FLOAT, "1.2")),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils138, shaderNodeKind138, string) -> "vec4(("
               + shaderExpressionUtils138.resolve(shaderNodeKind138, "color")
               + ".rgb - 0.5) * "
               + shaderExpressionUtils138.resolve(shaderNodeKind138, "amount")
               + " + 0.5, "
               + shaderExpressionUtils138.resolve(shaderNodeKind138, "color")
               + ".a)"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "saturation",
            "Saturation",
            "Color",
            190.0F,
            List.of(ShaderPin.input("color", "color", ShaderValueType.VEC4, "vec4(0.5)"), ShaderPin.input("amount", "amount", ShaderValueType.FLOAT, "1.2")),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils139, shaderNodeKind139, string) -> "vec4(mix(vec3(dot("
               + shaderExpressionUtils139.resolve(shaderNodeKind139, "color")
               + ".rgb, vec3(0.299,0.587,0.114))), "
               + shaderExpressionUtils139.resolve(shaderNodeKind139, "color")
               + ".rgb, "
               + shaderExpressionUtils139.resolve(shaderNodeKind139, "amount")
               + "), "
               + shaderExpressionUtils139.resolve(shaderNodeKind139, "color")
               + ".a)"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "brightness",
            "Brightness",
            "Color",
            190.0F,
            List.of(ShaderPin.input("color", "color", ShaderValueType.VEC4, "vec4(0.5)"), ShaderPin.input("amount", "amount", ShaderValueType.FLOAT, "0.0")),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils140, shaderNodeKind140, string) -> "vec4("
               + shaderExpressionUtils140.resolve(shaderNodeKind140, "color")
               + ".rgb + "
               + shaderExpressionUtils140.resolve(shaderNodeKind140, "amount")
               + ", "
               + shaderExpressionUtils140.resolve(shaderNodeKind140, "color")
               + ".a)"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "hue_shift",
            "Hue Shift",
            "Color",
            190.0F,
            List.of(
               ShaderPin.input("color", "color", ShaderValueType.VEC4, "vec4(u_AccentTop,1.0)"),
               ShaderPin.input("shift", "shift", ShaderValueType.FLOAT, "0.1")
            ),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils141, shaderNodeKind141, string) -> "vec4(wild_hsv2rgb(vec3(fract(wild_rgb2hsv("
               + shaderExpressionUtils141.resolve(shaderNodeKind141, "color")
               + ".rgb).x + "
               + shaderExpressionUtils141.resolve(shaderNodeKind141, "shift")
               + "), wild_rgb2hsv("
               + shaderExpressionUtils141.resolve(shaderNodeKind141, "color")
               + ".rgb).yz)), "
               + shaderExpressionUtils141.resolve(shaderNodeKind141, "color")
               + ".a)"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "blend_lighten",
            "Lighten Blend",
            "Blend",
            196.0F,
            List.of(
               ShaderPin.input("base", "base", ShaderValueType.VEC4, "vec4(0.1)"),
               ShaderPin.input("layer", "layer", ShaderValueType.VEC4, "vec4(u_AccentTop,1.0)"),
               ShaderPin.input("opacity", "opacity", ShaderValueType.FLOAT, "1.0")
            ),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils142, shaderNodeKind142, string) -> "vec4(mix("
               + shaderExpressionUtils142.resolve(shaderNodeKind142, "base")
               + ".rgb, max("
               + shaderExpressionUtils142.resolve(shaderNodeKind142, "base")
               + ".rgb, "
               + shaderExpressionUtils142.resolve(shaderNodeKind142, "layer")
               + ".rgb), clamp("
               + shaderExpressionUtils142.resolve(shaderNodeKind142, "opacity")
               + ",0.0,1.0)), max("
               + shaderExpressionUtils142.resolve(shaderNodeKind142, "base")
               + ".a, "
               + shaderExpressionUtils142.resolve(shaderNodeKind142, "layer")
               + ".a))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "blend_darken",
            "Darken Blend",
            "Blend",
            196.0F,
            List.of(
               ShaderPin.input("base", "base", ShaderValueType.VEC4, "vec4(0.1)"),
               ShaderPin.input("layer", "layer", ShaderValueType.VEC4, "vec4(u_AccentTop,1.0)"),
               ShaderPin.input("opacity", "opacity", ShaderValueType.FLOAT, "1.0")
            ),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils143, shaderNodeKind143, string) -> "vec4(mix("
               + shaderExpressionUtils143.resolve(shaderNodeKind143, "base")
               + ".rgb, min("
               + shaderExpressionUtils143.resolve(shaderNodeKind143, "base")
               + ".rgb, "
               + shaderExpressionUtils143.resolve(shaderNodeKind143, "layer")
               + ".rgb), clamp("
               + shaderExpressionUtils143.resolve(shaderNodeKind143, "opacity")
               + ",0.0,1.0)), max("
               + shaderExpressionUtils143.resolve(shaderNodeKind143, "base")
               + ".a, "
               + shaderExpressionUtils143.resolve(shaderNodeKind143, "layer")
               + ".a))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "blend_difference",
            "Difference Blend",
            "Blend",
            200.0F,
            List.of(
               ShaderPin.input("base", "base", ShaderValueType.VEC4, "vec4(0.1)"),
               ShaderPin.input("layer", "layer", ShaderValueType.VEC4, "vec4(u_AccentTop,1.0)"),
               ShaderPin.input("opacity", "opacity", ShaderValueType.FLOAT, "1.0")
            ),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils144, shaderNodeKind144, string) -> "vec4(mix("
               + shaderExpressionUtils144.resolve(shaderNodeKind144, "base")
               + ".rgb, abs("
               + shaderExpressionUtils144.resolve(shaderNodeKind144, "base")
               + ".rgb - "
               + shaderExpressionUtils144.resolve(shaderNodeKind144, "layer")
               + ".rgb), clamp("
               + shaderExpressionUtils144.resolve(shaderNodeKind144, "opacity")
               + ",0.0,1.0)), max("
               + shaderExpressionUtils144.resolve(shaderNodeKind144, "base")
               + ".a, "
               + shaderExpressionUtils144.resolve(shaderNodeKind144, "layer")
               + ".a))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "branch",
            "Branch",
            "Logic",
            190.0F,
            List.of(
               ShaderPin.input("pred", "pred", ShaderValueType.FLOAT, "0.0"),
               ShaderPin.input("whenTrue", "true", ShaderValueType.VEC4, "vec4(u_AccentTop,1.0)"),
               ShaderPin.input("whenFalse", "false", ShaderValueType.VEC4, "vec4(u_AccentBottom,1.0)")
            ),
            List.of(ShaderPin.output("color", "color", ShaderValueType.VEC4)),
            (shaderExpressionUtils145, shaderNodeKind145, string) -> "mix("
               + shaderExpressionUtils145.resolve(shaderNodeKind145, "whenFalse")
               + ", "
               + shaderExpressionUtils145.resolve(shaderNodeKind145, "whenTrue")
               + ", step(0.5, "
               + shaderExpressionUtils145.resolve(shaderNodeKind145, "pred")
               + "))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "compare_greater",
            "Greater Than",
            "Logic",
            196.0F,
            List.of(ShaderPin.input("a", "a", ShaderValueType.FLOAT, "0.5"), ShaderPin.input("b", "b", ShaderValueType.FLOAT, "0.0")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils146, shaderNodeKind146, string) -> "step("
               + shaderExpressionUtils146.resolve(shaderNodeKind146, "b")
               + ", "
               + shaderExpressionUtils146.resolve(shaderNodeKind146, "a")
               + ")"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "time_sine",
            "Time Sine",
            "Time",
            178.0F,
            List.of(),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils147, shaderNodeKind147, string) -> "(0.5 + 0.5 * sin(u_Time))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "time_cosine",
            "Time Cosine",
            "Time",
            184.0F,
            List.of(),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils148, shaderNodeKind148, string) -> "(0.5 + 0.5 * cos(u_Time))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "time_raw",
            "Time Raw",
            "Time",
            172.0F,
            List.of(),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils149, shaderNodeKind149, string) -> "u_Time"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "fresnel_real",
            "Fresnel Effect",
            "VFX",
            200.0F,
            List.of(
               ShaderPin.input("normal", "normal", ShaderValueType.VEC3, "vec3(0.0,0.0,1.0)"),
               ShaderPin.input("viewDir", "view", ShaderValueType.VEC3, "vec3(0.0,0.0,1.0)"),
               ShaderPin.input("power", "power", ShaderValueType.FLOAT, "3.0")
            ),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils150, shaderNodeKind150, string) -> "pow(1.0 - clamp(dot(normalize("
               + shaderExpressionUtils150.resolve(shaderNodeKind150, "normal")
               + "), normalize("
               + shaderExpressionUtils150.resolve(shaderNodeKind150, "viewDir")
               + ")), 0.0, 1.0), max("
               + shaderExpressionUtils150.resolve(shaderNodeKind150, "power")
               + ", 0.001))"
         )
      );
      this.invoke(
         new ShaderNodeDefinition(
            "dither",
            "Dither",
            "VFX",
            178.0F,
            List.of(ShaderPin.input("amount", "amount", ShaderValueType.FLOAT, "0.02")),
            List.of(ShaderPin.output("value", "value", ShaderValueType.FLOAT)),
            (shaderExpressionUtils151, shaderNodeKind151, string) -> "((wild_hash12(gl_FragCoord.xy) - 0.5) * " + shaderExpressionUtils151.resolve(shaderNodeKind151, "amount") + ")"
         )
      );
   }
}
