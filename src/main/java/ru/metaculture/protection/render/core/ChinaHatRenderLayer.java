package ru.metaculture.protection;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.platform.DestFactor;
import com.mojang.blaze3d.platform.SourceFactor;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.RenderLayer.MultiPhaseParameters;
import net.minecraft.util.Identifier;

public final class ChinaHatRenderLayer {
   private static final int INT_VALUE = 2097152;
   private static final Identifier IDENTIFIER = Identifier.of("wild", "core/chinahat");
   private static final BlendFunction BLEND_FUNCTION = new BlendFunction(SourceFactor.SRC_ALPHA, DestFactor.ONE);
   private static final RenderPipeline RENDER_PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET, RenderPipelines.GLOBALS_SNIPPET})
         .withLocation(Identifier.of("wild", "pipeline/chinahat_glass"))
         .withVertexShader(IDENTIFIER)
         .withFragmentShader(IDENTIFIER)
         .withVertexFormat(VertexFormats.POSITION_TEXTURE_COLOR_NORMAL, DrawMode.TRIANGLES)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
         .withDepthWrite(true)
         .withBlend(BlendFunction.TRANSLUCENT)
         .build()
   );
   private static final RenderPipeline RENDER_PIPELINE_2 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET, RenderPipelines.GLOBALS_SNIPPET})
         .withLocation(Identifier.of("wild", "pipeline/chinahat_bloom"))
         .withVertexShader(IDENTIFIER)
         .withFragmentShader(IDENTIFIER)
         .withVertexFormat(VertexFormats.POSITION_TEXTURE_COLOR_NORMAL, DrawMode.TRIANGLES)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BLEND_FUNCTION)
         .build()
   );
   private static final RenderLayer RENDER_LAYER = RenderLayer.of(
      "wild/chinahat_glass", 2097152, false, true, RENDER_PIPELINE, MultiPhaseParameters.builder().build(false)
   );
   private static final RenderLayer RENDER_LAYER_2 = RenderLayer.of(
      "wild/chinahat_bloom", 2097152, false, true, RENDER_PIPELINE_2, MultiPhaseParameters.builder().build(false)
   );

   private ChinaHatRenderLayer() {
   }

   public static void invoke() {
      if (RENDER_LAYER == null || RENDER_LAYER_2 == null) {
         RenderDiagnosticsTracker.getInstance().fail("ChinaHatShaderRegistry.init", new IllegalStateException("ChinaHat shader registry failed"));
      }
   }

   public static RenderLayer getRENDER_LAYER() {
      return RENDER_LAYER;
   }

   public static RenderLayer getRENDER_LAYER_2() {
      return RENDER_LAYER_2;
   }
}
