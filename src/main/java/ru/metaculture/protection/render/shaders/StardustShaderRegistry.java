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

public final class StardustShaderRegistry {
   private static final int INT_VALUE = 1048576;
   private static final Identifier IDENTIFIER = Identifier.of("minecraft", "core/stardust");
   private static final BlendFunction BLEND_FUNCTION = new BlendFunction(SourceFactor.SRC_ALPHA, DestFactor.ONE);
   private static final RenderPipeline RENDER_PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET, RenderPipelines.GLOBALS_SNIPPET})
         .withLocation(Identifier.of("wild", "pipeline/stardust"))
         .withVertexShader(IDENTIFIER)
         .withFragmentShader(IDENTIFIER)
         .withVertexFormat(VertexFormats.POSITION_TEXTURE_COLOR_NORMAL, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BLEND_FUNCTION)
         .build()
   );
   private static final RenderLayer RENDER_LAYER = RenderLayer.of(
      "wild/stardust", 1048576, false, true, RENDER_PIPELINE, MultiPhaseParameters.builder().build(false)
   );

   private StardustShaderRegistry() {
   }

   public static void invoke() {
      if (RENDER_PIPELINE == null || RENDER_LAYER == null) {
         RenderDiagnosticsTracker.getInstance().fail("StardustShaderRegistry.init", new IllegalStateException("Stardust shader registry failed"));
      }
   }

   public static RenderLayer getRENDER_LAYER() {
      return RENDER_LAYER;
   }
}
