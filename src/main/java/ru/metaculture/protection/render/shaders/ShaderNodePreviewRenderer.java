package ru.metaculture.protection;

import com.mojang.blaze3d.opengl.GlStateManager;
import java.util.HashMap;
import java.util.Map;
import org.lwjgl.opengl.GL11;

public final class ShaderNodePreviewRenderer implements AutoCloseable {
   private final ShaderNodeRegistry shaderNodeRegistry;
   private final ShaderSourceBuilder shaderSourceBuilder;
   private final OffscreenFramebuffer offscreenFramebuffer = new OffscreenFramebuffer();
   private final Map<String, ShaderNodePreviewRenderer.ShaderNodePreviewRendererData> valuesByKey = new HashMap<>();

   public ShaderNodePreviewRenderer(ShaderNodeRegistry shaderNodeRegistry, ShaderSourceBuilder shaderSourceBuilder) {
      this.shaderNodeRegistry = shaderNodeRegistry;
      this.shaderSourceBuilder = shaderSourceBuilder;
   }

   public void invoke(
      ShaderNode shaderNode,
      String string,
      NamedShaderProgram namedShaderProgram,
      RenderManager renderManager,
      float f,
      float g,
      float h,
      float i,
      int j,
      int k,
      ColorScheme colorScheme,
      float l
   ) {
      if (shaderNode != null && string != null && renderManager != null && !(h <= 2.0F) && !(i <= 2.0F) && !(l <= 0.001F)) {
         ShaderNodeKind shaderNodeKind = shaderNode.resolve3(string);
         ShaderNodeDefinition shaderNodeDefinition = shaderNodeKind == null ? null : this.shaderNodeRegistry.resolve(shaderNodeKind.getText2());
         ShaderPin shaderPin = resolve(shaderNodeDefinition);
         if (shaderPin != null) {
            String text = "__node_preview_" + string;
            ShaderNodePreviewRenderer.ShaderNodePreviewRendererData shaderNodePreviewRendererData = this.valuesByKey.get(text);
            int intValue = shaderNode.getIntValue();
            if (shaderNodePreviewRendererData == null || shaderNodePreviewRendererData.version != intValue || !shaderPin.id().equals(shaderNodePreviewRendererData.pinId)) {
               ShaderNode shaderNode2 = shaderNode.resolve6(string);
               shaderNode2.invoke2(ShaderSurface.PREVIEW_ONLY.getText());
               ShaderBuildResult shaderBuildResult = this.shaderSourceBuilder.resolve3(shaderNode2, string, shaderPin.id(), shaderPin.type());
               shaderNodePreviewRendererData = new ShaderNodePreviewRenderer.ShaderNodePreviewRendererData(intValue, shaderPin.id(), shaderBuildResult == null ? "" : shaderBuildResult.hash(), shaderBuildResult);
               this.valuesByKey.put(text, shaderNodePreviewRendererData);
            }

            ShaderBuildResult shaderBuildResult2 = shaderNodePreviewRendererData.compilation;
            if (shaderBuildResult2 != null && shaderBuildResult2.ok()) {
               renderManager.invoke20();
               FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();
               boolean flag = false ;

               label86: {
                  try {
                     flag = true;
                     int intValue2 = Math.max(32, Math.min(512, (int)Math.ceil(h)));
                     int intValue3 = Math.max(32, Math.min(384, (int)Math.ceil(i)));
                     this.offscreenFramebuffer.invoke(intValue2, intValue3);
                     if (!this.offscreenFramebuffer.check()) {
                        flag = false;
                        break label86;
                     }

                     this.offscreenFramebuffer.invoke2();
                     GL11.glDisable(3089);
                     GlStateManager._enableBlend();
                     GL11.glEnable(3042);
                     GL11.glClearColor(0.008F, 0.01F, 0.015F, 0.0F);
                     GL11.glClear(16384);
                     ShaderUniformBinder.check8(
                        "__node_preview_" + shaderNodePreviewRendererData.hash, shaderBuildResult2, 0.0F, 0.0F, intValue2, intValue3, intValue2, intValue3, h * 0.5F, i * 0.5F, colorScheme, l
                     );
                     flag = false;
                  } finally {
                     if (flag) {
                        FramebufferUtils.restoreGlState(glStateSnapshot);
                     }
                  }

                  FramebufferUtils.restoreGlState(glStateSnapshot);
                  renderManager.invoke10(this.offscreenFramebuffer.getIntValue2(), f, g, h, i, ColorScheme.compute6(-1, Math.round(255.0F * l)), true);
                  return;
               }

               FramebufferUtils.restoreGlState(glStateSnapshot);
            } else {
               invoke2(renderManager, f, g, h, i, colorScheme, l);
            }
         }
      }
   }

   private static ShaderPin resolve(ShaderNodeDefinition shaderNodeDefinition2) {
      if (shaderNodeDefinition2 != null && !shaderNodeDefinition2.getItems2().isEmpty()) {
         for (ShaderPin shaderPin2 : shaderNodeDefinition2.getItems2()) {
            if ("color".equals(shaderPin2.id()) || "mask".equals(shaderPin2.id()) || "value".equals(shaderPin2.id())) {
               return shaderPin2;
            }
         }

         return shaderNodeDefinition2.getItems2().get(0);
      } else {
         return null;
      }
   }

   private static void invoke2(RenderManager renderManager2, float f, float g, float h, float i, ColorScheme colorScheme2, float j) {
      int intValue4 = ColorScheme.compute5(40, 10, 14, Math.round(132.0F * j));
      int intValue5 = ColorScheme.compute5(255, 134, 146, Math.round(230.0F * j));
      renderManager2.invoke5(f, g, h, i, 8.0F, intValue4);
      float floatValue = ClickGuiRenderUtils.measure2(null, FontRegistry.fontObject, "preview error", 9.0F);
      ClickGuiRenderUtils.invoke4(renderManager2, null, FontRegistry.fontObject, f + (h - floatValue) * 0.5F, g, i, 9.0F, "preview error", intValue5);
   }

   @Override
   public void close() {
      this.offscreenFramebuffer.close();
      this.valuesByKey.clear();
   }

   record ShaderNodePreviewRendererData(int version, String pinId, String hash, ShaderBuildResult compilation) {
   }
}
