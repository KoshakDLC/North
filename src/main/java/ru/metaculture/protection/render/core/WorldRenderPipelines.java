package ru.metaculture.protection;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.systems.RenderPass;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.util.Map;
import java.util.Objects;
import java.util.OptionalDouble;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Consumer;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.RenderLayer.MultiPhase;
import net.minecraft.client.render.RenderLayer.MultiPhaseParameters;
import net.minecraft.client.render.RenderPhase.LineWidth;
import net.minecraft.client.render.RenderPhase.Texture;
import net.minecraft.util.Identifier;

public final class WorldRenderPipelines {
   private static final int INT_VALUE = 1024;
   private static final int INT_VALUE_2 = 256;
   private static final String WILD = "wild";
   private static final double DOUBLE_VALUE = 0.0625;
   private static final double DOUBLE_VALUE_2 = 64.0;
   private static final int INT_VALUE_3 = 128;
   private static final RenderPipeline RENDER_PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "pipeline/world/position_color_quads"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
         .withDepthWrite(true)
         .build()
   );
   private static final RenderPipeline RENDER_PIPELINE_2 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "pipeline/world/position_color_quads_no_depth"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .build()
   );
   private static final RenderPipeline RENDER_PIPELINE_3 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "pipeline/world/position_color_quads_no_depth_blend"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.LIGHTNING)
         .build()
   );
   private static final RenderPipeline RENDER_PIPELINE_4 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "pipeline/world/position_color_quads_translucent"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.TRANSLUCENT)
         .build()
   );
   private static final RenderPipeline RENDER_PIPELINE_5 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "pipeline/world/position_color_quads_translucent_no_depth"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.TRANSLUCENT)
         .build()
   );
   private static final RenderPipeline RENDER_PIPELINE_6 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.RENDERTYPE_LINES_SNIPPET})
         .withLocation(Identifier.of("wild", "pipeline/world/lines"))
         .withVertexFormat(VertexFormats.POSITION_COLOR_NORMAL, DrawMode.LINES)
         .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
         .withDepthWrite(true)
         .build()
   );
   private static final RenderPipeline RENDER_PIPELINE_7 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.RENDERTYPE_LINES_SNIPPET})
         .withLocation(Identifier.of("wild", "pipeline/world/lines_no_depth"))
         .withVertexFormat(VertexFormats.POSITION_COLOR_NORMAL, DrawMode.LINES)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .build()
   );
   private static final RenderPipeline RENDER_PIPELINE_8 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_TEX_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "pipeline/world/textured_quads"))
         .withVertexFormat(VertexFormats.POSITION_TEXTURE_COLOR, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.TRANSLUCENT)
         .build()
   );
   private static final RenderPipeline RENDER_PIPELINE_9 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_TEX_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "pipeline/world/textured_quads_additive"))
         .withVertexFormat(VertexFormats.POSITION_TEXTURE_COLOR, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.ADDITIVE)
         .build()
   );
   private static final RenderPipeline RENDER_PIPELINE_10 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_TEX_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "pipeline/world/textured_quads_no_depth_additive"))
         .withVertexFormat(VertexFormats.POSITION_TEXTURE_COLOR, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.ADDITIVE)
         .build()
   );
   private static final RenderPipeline RENDER_PIPELINE_11 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_TEX_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "pipeline/world/textured_quads_no_depth"))
         .withVertexFormat(VertexFormats.POSITION_TEXTURE_COLOR, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.TRANSLUCENT)
         .build()
   );
   private static final RenderLayer RENDER_LAYER = RenderLayer.of(
      "wild/world/position_color_quads", 1024, false, true, RENDER_PIPELINE, MultiPhaseParameters.builder().build(false)
   );
   private static final RenderLayer RENDER_LAYER_2 = RenderLayer.of(
      "wild/world/position_color_quads_no_depth", 1024, false, true, RENDER_PIPELINE_2, MultiPhaseParameters.builder().build(false)
   );
   private static final RenderLayer RENDER_LAYER_3 = RenderLayer.of(
      "wild/world/position_color_quads_no_depth_blend", 1024, false, true, RENDER_PIPELINE_3, MultiPhaseParameters.builder().build(false)
   );
   private static final RenderLayer RENDER_LAYER_4 = RenderLayer.of(
      "wild/world/position_color_quads_translucent", 1024, false, true, RENDER_PIPELINE_4, MultiPhaseParameters.builder().build(false)
   );
   private static final RenderLayer RENDER_LAYER_5 = RenderLayer.of(
      "wild/world/position_color_quads_translucent_no_depth", 1024, false, true, RENDER_PIPELINE_5, MultiPhaseParameters.builder().build(false)
   );
   private static final RenderLayer RENDER_LAYER_6 = RenderLayer.of(
      "wild/world/textured_quads", 1024, false, true, RENDER_PIPELINE_8, MultiPhaseParameters.builder().build(false)
   );
   private static final RenderLayer RENDER_LAYER_7 = RenderLayer.of(
      "wild/world/textured_quads_additive", 1024, false, true, RENDER_PIPELINE_9, MultiPhaseParameters.builder().build(false)
   );
   private static final RenderLayer RENDER_LAYER_8 = RenderLayer.of(
      "wild/world/textured_quads_no_depth_additive", 1024, false, true, RENDER_PIPELINE_10, MultiPhaseParameters.builder().build(false)
   );
   private static final RenderLayer RENDER_LAYER_9 = RenderLayer.of(
      "wild/world/textured_quads_no_depth", 1024, false, true, RENDER_PIPELINE_11, MultiPhaseParameters.builder().build(false)
   );
   private static final Map<Double, RenderLayer> VALUES_BY_KEY = new ConcurrentHashMap<>();
   private static final Map<Double, RenderLayer> VALUES_BY_KEY_2 = new ConcurrentHashMap<>();

   private WorldRenderPipelines() {
   }

   public static RenderLayer getRENDER_LAYER() {
      return RENDER_LAYER;
   }

   public static RenderLayer getRENDER_LAYER_2() {
      return RENDER_LAYER_2;
   }

   public static RenderLayer getRENDER_LAYER_3() {
      return RENDER_LAYER_3;
   }

   public static RenderLayer getRENDER_LAYER_4() {
      return RENDER_LAYER_4;
   }

   public static RenderLayer getRENDER_LAYER_5() {
      return RENDER_LAYER_5;
   }

   public static RenderLayer getRENDER_LAYER_6() {
      return RENDER_LAYER_6;
   }

   public static RenderLayer resolve(Identifier identifier) {
      return RenderLayer.of(
         identifier.toString(), 1024, false, true, RENDER_PIPELINE_11, MultiPhaseParameters.builder().texture(new Texture(identifier, false)).build(false)
      );
   }

   public static RenderLayer resolve2(Identifier identifier) {
      return RenderLayer.of(
         identifier.toString(), 1024, false, true, RENDER_PIPELINE_8, MultiPhaseParameters.builder().texture(new Texture(identifier, false)).build(false)
      );
   }

   public static RenderLayer resolve3(Identifier identifier) {
      return RenderLayer.of(
         identifier.toString(), 1024, false, true, RENDER_PIPELINE_9, MultiPhaseParameters.builder().texture(new Texture(identifier, false)).build(false)
      );
   }

   public static RenderLayer resolve4(Identifier identifier) {
      return RenderLayer.of(
         identifier.toString(), 1024, false, true, RENDER_PIPELINE_10, MultiPhaseParameters.builder().texture(new Texture(identifier, false)).build(false)
      );
   }

   public static RenderLayer resolve5(double d) {
      invoke(VALUES_BY_KEY);
      double doubleValue = measure(d);
      return VALUES_BY_KEY.computeIfAbsent(doubleValue, double_ -> resolve7(double_, "wild/world/lines", RENDER_PIPELINE_6));
   }

   public static RenderLayer resolve6(double d) {
      invoke(VALUES_BY_KEY_2);
      double doubleValue2 = measure(d);
      return VALUES_BY_KEY_2.computeIfAbsent(doubleValue2, double_ -> resolve7(double_, "wild/world/lines_no_depth", RENDER_PIPELINE_7));
   }

   private static RenderLayer resolve7(double d, String string, RenderPipeline renderPipeline) {
      LineWidth lineWidth = new LineWidth(d == 0.0 ? OptionalDouble.empty() : OptionalDouble.of(d));
      return RenderLayer.of(
         string + "/" + (d == 0.0 ? "default" : Double.toHexString(d)),
         256,
         false,
         true,
         renderPipeline,
         MultiPhaseParameters.builder().lineWidth(lineWidth).build(false)
      );
   }

   public static MultiPhase withRenderPassSetup(RenderLayer renderLayer, Consumer<RenderPass> consumer) {
      Objects.requireNonNull(renderLayer, "renderLayer");
      if (renderLayer instanceof MultiPhase multiPhase) {
         MultiPhaseRenderPass.resolve(multiPhase).withRenderPassSetup(consumer);
         return multiPhase;
      } else {
         throw new IllegalArgumentException("Render layer must be a MultiPhase instance.");
      }
   }

   private static double measure(double d) {
      if (!Double.isFinite(d)) {
         throw new IllegalArgumentException("Line width must be finite.");
      } else if (d < 0.0) {
         throw new IllegalArgumentException("Line width cannot be negative.");
      } else if (d == 0.0) {
         return 0.0;
      } else {
         double doubleValue3 = Math.min(d, 64.0);
         double doubleValue4 = Math.round(doubleValue3 / 0.0625) * 0.0625;
         if (doubleValue4 <= 0.0) {
            doubleValue4 = 0.0625;
         }

         return doubleValue4;
      }
   }

   private static void invoke(Map<Double, RenderLayer> map) {
      if (map.size() > 128) {
         map.clear();
      }
   }
}
