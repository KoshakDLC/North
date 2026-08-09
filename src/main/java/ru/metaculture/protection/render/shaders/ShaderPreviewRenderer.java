package ru.metaculture.protection;

import com.mojang.blaze3d.opengl.GlStateManager;
import java.lang.reflect.Method;
import java.util.function.Supplier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.render.state.GuiRenderState;
import net.minecraft.client.gui.render.state.special.EntityGuiElementRenderState;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public final class ShaderPreviewRenderer {
   private static final OffscreenFramebuffer OFFSCREEN_FRAMEBUFFER = new OffscreenFramebuffer();
   private static final OffscreenFramebuffer OFFSCREEN_FRAMEBUFFER_2 = new OffscreenFramebuffer();
   private static final OffscreenFramebuffer OFFSCREEN_FRAMEBUFFER_3 = new OffscreenFramebuffer();
   private static final int INT_VALUE = -15657957;
   private static final int INT_VALUE_2 = -14670802;
   private static final String FOUNDRY_PREVIEW_LIVE = "__foundry_preview_live";
   private static String foundryPreviewLive = "__foundry_preview_live";
   private static String text = "";
   private static final float FLOAT_VALUE = 0.78F;
   private static final float FLOAT_VALUE_2 = -6.0F;
   private static final float FLOAT_VALUE_3 = 22.0F;
   private static Boolean booleanValue;
   private static Method method;
   private static Method method2;

   private ShaderPreviewRenderer() {
   }

   public static void invoke(
      RenderManager renderManager,
      ThemeContext themeContext,
      ShaderSurface shaderSurface,
      NamedShaderProgram namedShaderProgram,
      ShaderNode shaderNode,
      float f,
      float g,
      float h,
      float i,
      int j,
      int k,
      float l,
      float m,
      float n
   ) {
      Metrics metrics = themeContext.getMetrics();
      ColorScheme colorScheme = themeContext.getColorScheme();
      ShaderSurface shaderSurface2 = shaderSurface == null ? ShaderSurface.PREVIEW_ONLY : shaderSurface;
      ShaderSurface shaderSurface3 = shaderSurface2.resolve();
      renderManager.invoke20();
      renderManager.invoke24(f, g, h, i, metrics.measure(10.0F), metrics.measure(10.0F), metrics.measure(10.0F), metrics.measure(10.0F));

      try {
         if (shaderSurface2 == ShaderSurface.TRAILS) {
            invoke8(renderManager, themeContext, namedShaderProgram, shaderNode, f, g, h, i, j, k, l, m, n);
         } else if (shaderSurface2 == ShaderSurface.SKY) {
            invoke4(renderManager, namedShaderProgram, shaderNode, colorScheme, f, g, h, i, j, k, l, m, n);
         } else if (shaderSurface2 == ShaderSurface.NAMETAG) {
            invoke7(renderManager, themeContext, namedShaderProgram, shaderNode, f, g, h, i, j, k, l, m, n);
         } else if (shaderSurface2 == ShaderSurface.CHAMS) {
            invoke9(renderManager, themeContext, namedShaderProgram, shaderNode, f, g, h, i, j, k, l, m, n, true);
         } else if (shaderSurface2 == ShaderSurface.HEALTH_BAR) {
            invoke12(renderManager, themeContext, namedShaderProgram, shaderNode, f, g, h, i, j, k, l, m, n);
         } else {
            switch (shaderSurface3) {
               case HUD:
                  invoke6(renderManager, themeContext, namedShaderProgram, shaderNode, f, g, h, i, j, k, l, m, n);
                  break;
               case ESP:
                  invoke9(renderManager, themeContext, namedShaderProgram, shaderNode, f, g, h, i, j, k, l, m, n, false);
                  break;
               case BACKGROUND:
                  invoke3(renderManager, namedShaderProgram, shaderNode, colorScheme, f, g, h, i, j, k, l, m, n);
                  break;
               default:
                  invoke5(renderManager, namedShaderProgram, shaderNode, colorScheme, f, g, h, i, j, k, l, m, n);
            }
         }
      } finally {
         renderManager.invoke20();
         renderManager.invoke25();
      }

      renderManager.invoke28(f, g, h, i, metrics.measure(10.0F), ColorScheme.compute6(colorScheme.getIntValue14(), 96), 0.7F);
   }

   public static void invoke2(
      RenderManager renderManager2,
      ThemeContext themeContext2,
      String string,
      ShaderSurface shaderSurface4,
      ShaderNode shaderNode2,
      float f,
      float g,
      float h,
      float i,
      int j,
      int k,
      float l,
      float m,
      float n
   ) {
      String text = foundryPreviewLive;
      String text2 = text;
      String text3 = ShaderPresetRegistry.resolve21(string);
      foundryPreviewLive = text3.isBlank() ? "__foundry_preview_live" : "__foundry_slot_preview_" + text3;
      text = text3;
      boolean flag = false ;

      try {
         flag = true;
         invoke(renderManager2, themeContext2, shaderSurface4, null, shaderNode2, f, g, h, i, j, k, l, m, n);
         flag = false;
      } finally {
         if (flag) {
            foundryPreviewLive = text;
            text = text2;
         }
      }

      foundryPreviewLive = text;
      text = text2;
   }

   private static void invoke3(
      RenderManager renderManager3,
      NamedShaderProgram namedShaderProgram2,
      ShaderNode shaderNode3,
      ColorScheme colorScheme2,
      float f,
      float g,
      float h,
      float i,
      int j,
      int k,
      float l,
      float m,
      float n
   ) {
      renderManager3.invoke5(f, g, h, i, 0.0F, -16645366);
      invoke17(renderManager3, namedShaderProgram2, shaderNode3, f, g, h, i, j, k, l, m, colorScheme2, n);
   }

   private static void invoke4(
      RenderManager renderManager4,
      NamedShaderProgram namedShaderProgram3,
      ShaderNode shaderNode4,
      ColorScheme colorScheme3,
      float f,
      float g,
      float h,
      float i,
      int j,
      int k,
      float l,
      float m,
      float n
   ) {
      renderManager4.invoke37(f, g, h, i, 0.0F, -16381929, -15460309);
      renderManager4.invoke34(
         f,
         g + i * 0.58F,
         h,
         i * 0.42F,
         0.0F,
         ColorScheme.compute6(colorScheme3.getIntValue15(), 54),
         ColorScheme.compute6(colorScheme3.getIntValue14(), 28)
      );
      invoke17(renderManager4, namedShaderProgram3, shaderNode4, f, g, h, i, j, k, l, m, colorScheme3, n);
   }

   private static void invoke5(
      RenderManager renderManager5,
      NamedShaderProgram namedShaderProgram4,
      ShaderNode shaderNode5,
      ColorScheme colorScheme4,
      float f,
      float g,
      float h,
      float i,
      int j,
      int k,
      float l,
      float m,
      float n
   ) {
      invoke16(renderManager5, colorScheme4, f, g, h, i, n);
      invoke17(renderManager5, namedShaderProgram4, shaderNode5, f, g, h, i, j, k, l, m, colorScheme4, n);
   }

   private static void invoke6(
      RenderManager renderManager6,
      ThemeContext themeContext3,
      NamedShaderProgram namedShaderProgram5,
      ShaderNode shaderNode6,
      float f,
      float g,
      float h,
      float i,
      int j,
      int k,
      float l,
      float m,
      float n
   ) {
      ColorScheme colorScheme5 = themeContext3.getColorScheme();
      invoke16(renderManager6, colorScheme5, f, g, h, i, n);
      ShaderBuildResult shaderBuildResult = resolve2(namedShaderProgram5, shaderNode6);
      float floatValue = Math.max(18.0F, Math.min(h * 0.84F, 220.0F));
      float floatValue2 = Math.max(12.0F, Math.min(i * 0.58F, floatValue * 0.42F));
      float floatValue3 = f + (h - floatValue) * 0.5F;
      float floatValue4 = g + i * 0.26F;
      float floatValue5 = Math.min(floatValue, floatValue2) * 0.18F;
      invoke15(renderManager6, namedShaderProgram5, shaderNode6, shaderBuildResult, f, g, h, i, floatValue3, floatValue4, floatValue, floatValue2, floatValue5, l, m, colorScheme5, n);
   }

   private static void invoke7(
      RenderManager renderManager7,
      ThemeContext themeContext4,
      NamedShaderProgram namedShaderProgram6,
      ShaderNode shaderNode7,
      float f,
      float g,
      float h,
      float i,
      int j,
      int k,
      float l,
      float m,
      float n
   ) {
      ColorScheme colorScheme6 = themeContext4.getColorScheme();
      invoke16(renderManager7, colorScheme6, f, g, h, i, n);
      float floatValue6 = Math.min(h * 0.7F, 230.0F);
      float floatValue7 = Math.min(i * 0.24F, 54.0F);
      float floatValue8 = f + (h - floatValue6) * 0.5F;
      float floatValue9 = g + i * 0.34F;
      float floatValue10 = Math.min(floatValue6, floatValue7) * 0.22F;
      ShaderBuildResult shaderBuildResult2 = resolve2(namedShaderProgram6, shaderNode7);
      invoke15(renderManager7, namedShaderProgram6, shaderNode7, shaderBuildResult2, f, g, h, i, floatValue8, floatValue9, floatValue6, floatValue7, floatValue10, l, m, colorScheme6, n);
      MinecraftClient client = MinecraftClient.getInstance();
      String text4 = client != null && client.player != null ? client.player.getName().getString() : "Player";
      ClickGuiRenderUtils.invoke4(
         renderManager7,
         themeContext4.getMetrics(),
         FontRegistry.fontObject4,
         floatValue8,
         floatValue9 + floatValue7 * 0.2F,
         floatValue7 * 0.42F,
         10.0F,
         text4,
         colorScheme6.getIntValue13()
      );
      ClickGuiRenderUtils.invoke4(
         renderManager7,
         themeContext4.getMetrics(),
         FontRegistry.fontObject,
         floatValue8,
         floatValue9 + floatValue7 * 0.52F,
         floatValue7 * 0.34F,
         8.0F,
         "20.0",
         ColorScheme.compute6(colorScheme6.getIntValue15(), 220)
      );
   }

   private static void invoke8(
      RenderManager renderManager8,
      ThemeContext themeContext5,
      NamedShaderProgram namedShaderProgram7,
      ShaderNode shaderNode8,
      float f,
      float g,
      float h,
      float i,
      int j,
      int k,
      float l,
      float m,
      float n
   ) {
      Metrics metrics2 = themeContext5.getMetrics();
      ColorScheme colorScheme7 = themeContext5.getColorScheme();
      MinecraftClient client2 = MinecraftClient.getInstance();
      ClientPlayerEntity clientPlayerEntity2 = client2 != null ? client2.player : null;
      invoke16(renderManager8, colorScheme7, f, g, h, i, n);
      float floatValue11 = g + i * 0.86F;
      renderManager8.invoke5(f, floatValue11, h, i - (floatValue11 - g), 0.0F, -15657957);
      renderManager8.invoke5(f, floatValue11, h, 1.0F, 0.0F, -14670802);
      ShaderBuildResult shaderBuildResult3 = resolve2(namedShaderProgram7, shaderNode8);
      float floatValue12 = Math.max(metrics2.measure(10.0F), i * 0.085F);
      float floatValue13 = h * 0.55F;
      float floatValue14 = f + h * 0.1F;
      float floatValue15 = floatValue11 - i * 0.3F - floatValue12 * 0.5F;
      float floatValue16 = floatValue12 * 2.1F;
      float floatValue17 = floatValue15 - (floatValue16 - floatValue12) * 0.5F;
      renderManager8.invoke24(floatValue14, floatValue17, floatValue13, floatValue16, floatValue16 * 0.5F, floatValue16 * 0.5F, floatValue16 * 0.5F, floatValue16 * 0.5F);

      try {
         invoke18(renderManager8, namedShaderProgram7, shaderNode8, shaderBuildResult3, floatValue14, floatValue17, floatValue13, floatValue16, j, k, l, m, colorScheme7, n * 0.34F);
      } finally {
         renderManager8.invoke20();
         renderManager8.invoke25();
      }

      renderManager8.invoke24(floatValue14, floatValue15, floatValue13, floatValue12, floatValue12 * 0.5F, floatValue12 * 0.5F, floatValue12 * 0.5F, floatValue12 * 0.5F);

      try {
         invoke18(renderManager8, namedShaderProgram7, shaderNode8, shaderBuildResult3, floatValue14, floatValue15, floatValue13, floatValue12, j, k, l, m, colorScheme7, n);
      } finally {
         renderManager8.invoke20();
         renderManager8.invoke25();
      }

      if (clientPlayerEntity2 != null) {
         float floatValue18 = Math.max(24.0F, Math.min(i * 0.58F, 105.0F));
         EntityGuiElementRenderState entityGuiElementRenderState2 = resolve(client2, clientPlayerEntity2, f + h * 0.66F, floatValue11, floatValue18);
         invoke13(client2, entityGuiElementRenderState2);
      } else {
         float floatValue19 = Math.min(h * 0.18F, 54.0F);
         float floatValue20 = Math.min(i * 0.48F, 104.0F);
         float floatValue21 = f + h * 0.66F - floatValue19 * 0.5F;
         float floatValue22 = floatValue11 - floatValue20;
         renderManager8.invoke5(floatValue21, floatValue22, floatValue19, floatValue20, floatValue19 * 0.22F, ColorScheme.compute5(10, 12, 18, 230));
         renderManager8.invoke28(floatValue21, floatValue22, floatValue19, floatValue20, floatValue19 * 0.22F, ColorScheme.compute6(colorScheme7.getIntValue14(), 140), 0.7F);
      }
   }

   private static void invoke9(
      RenderManager renderManager9,
      ThemeContext themeContext6,
      NamedShaderProgram namedShaderProgram8,
      ShaderNode shaderNode9,
      float f,
      float g,
      float h,
      float i,
      int j,
      int k,
      float l,
      float m,
      float n,
      boolean bl
   ) {
      ColorScheme colorScheme8 = themeContext6.getColorScheme();
      MinecraftClient client3 = MinecraftClient.getInstance();
      ClientPlayerEntity clientPlayerEntity3 = client3 != null ? client3.player : null;
      invoke16(renderManager9, colorScheme8, f, g, h, i, n);
      float floatValue23 = g + i * 0.88F;
      renderManager9.invoke5(f, floatValue23, h, i - (floatValue23 - g), 0.0F, -15657957);
      renderManager9.invoke5(f, floatValue23, h, 1.0F, 0.0F, -14670802);
      if (clientPlayerEntity3 != null) {
         float floatValue24 = (floatValue23 - g) * 0.92F;
         float floatValue25 = Math.max(24.0F, Math.min(floatValue24, i * 0.78F) * 0.78F);
         float floatValue26 = f + h * 0.5F;
         EntityGuiElementRenderState entityGuiElementRenderState3 = resolve(client3, clientPlayerEntity3, floatValue26, floatValue23, floatValue25);
         int intValue = compute(client3, entityGuiElementRenderState3, j, k);
         invoke13(client3, entityGuiElementRenderState3);
         if (intValue > 0) {
            ShaderBuildResult shaderBuildResult4 = resolve2(namedShaderProgram8, shaderNode9);
            boolean flag2 = ShaderUniformBinder.check9(resolve3(), shaderBuildResult4, intValue, f, g, h, i, j, k, l, m, colorScheme8, bl ? n : n * 0.96F);
            invoke20();
            renderManager9.invoke20();
            if (flag2) {
               return;
            }
         }
      }

      invoke10(renderManager9, themeContext6, namedShaderProgram8, shaderNode9, f, g, h, i, floatValue23, j, k, l, m, n, bl);
   }

   private static void invoke10(
      RenderManager renderManager10,
      ThemeContext themeContext7,
      NamedShaderProgram namedShaderProgram9,
      ShaderNode shaderNode10,
      float f,
      float g,
      float h,
      float i,
      float j,
      int k,
      int l,
      float m,
      float n,
      float o,
      boolean bl
   ) {
      Metrics metrics3 = themeContext7.getMetrics();
      ColorScheme colorScheme9 = themeContext7.getColorScheme();
      ShaderBuildResult shaderBuildResult5 = resolve2(namedShaderProgram9, shaderNode10);
      float floatValue27 = f + h * 0.5F;
      float floatValue28 = Math.min(i * 0.52F, metrics3.measure(130.0F));
      float floatValue29 = Math.min(h * 0.2F, metrics3.measure(56.0F));
      float floatValue30 = floatValue29 * 0.72F;
      float floatValue31 = floatValue27 - floatValue29 * 0.5F;
      float floatValue32 = j - floatValue28;
      float floatValue33 = floatValue27 - floatValue30 * 0.5F;
      float floatValue34 = floatValue32 - floatValue30 * 0.62F;
      float floatValue35 = floatValue29 * 0.3F;
      float floatValue36 = floatValue28 * 0.62F;
      float floatValue37 = floatValue29 * 0.34F;
      float floatValue38 = floatValue28 * 0.42F;
      renderManager10.invoke41(
         floatValue31 - metrics3.measure(6.0F),
         floatValue34 - metrics3.measure(6.0F),
         floatValue29 + metrics3.measure(12.0F),
         j - floatValue34 + metrics3.measure(6.0F),
         floatValue29 * 0.28F,
         metrics3.measure(22.0F),
         metrics3.measure(2.0F),
         ColorScheme.compute6(colorScheme9.getIntValue14(), Math.round(64.0F * o))
      );
      invoke11(renderManager10, namedShaderProgram9, shaderNode10, shaderBuildResult5, floatValue33, floatValue34, floatValue30, floatValue30, floatValue30 * 0.42F, k, l, m, n, colorScheme9, o);
      invoke11(
         renderManager10, namedShaderProgram9, shaderNode10, shaderBuildResult5, floatValue31 - floatValue35 * 0.72F, floatValue32 + floatValue28 * 0.06F, floatValue35, floatValue36, floatValue35 * 0.5F, k, l, m, n, colorScheme9, o
      );
      invoke11(
         renderManager10,
         namedShaderProgram9,
         shaderNode10,
         shaderBuildResult5,
         floatValue31 + floatValue29 - floatValue35 * 0.28F,
         floatValue32 + floatValue28 * 0.06F,
         floatValue35,
         floatValue36,
         floatValue35 * 0.5F,
         k,
         l,
         m,
         n,
         colorScheme9,
         o
      );
      invoke11(
         renderManager10, namedShaderProgram9, shaderNode10, shaderBuildResult5, floatValue31 + floatValue29 * 0.1F, floatValue32 + floatValue28 * 0.58F, floatValue37, floatValue38, floatValue37 * 0.4F, k, l, m, n, colorScheme9, o
      );
      invoke11(
         renderManager10,
         namedShaderProgram9,
         shaderNode10,
         shaderBuildResult5,
         floatValue31 + floatValue29 - floatValue37 - floatValue29 * 0.1F,
         floatValue32 + floatValue28 * 0.58F,
         floatValue37,
         floatValue38,
         floatValue37 * 0.4F,
         k,
         l,
         m,
         n,
         colorScheme9,
         o
      );
      invoke11(renderManager10, namedShaderProgram9, shaderNode10, shaderBuildResult5, floatValue31, floatValue32, floatValue29, floatValue28 * 0.66F, floatValue29 * 0.3F, k, l, m, n, colorScheme9, o);
      if (!bl) {
         renderManager10.invoke28(floatValue33, floatValue34, floatValue30, floatValue30, floatValue30 * 0.42F, ColorScheme.compute6(colorScheme9.getIntValue14(), Math.round(150.0F * o)), 0.7F);
         renderManager10.invoke28(floatValue31, floatValue32, floatValue29, floatValue28 * 0.66F, floatValue29 * 0.3F, ColorScheme.compute6(colorScheme9.getIntValue14(), Math.round(150.0F * o)), 0.7F);
      }
   }

   private static void invoke11(
      RenderManager renderManager11,
      NamedShaderProgram namedShaderProgram10,
      ShaderNode shaderNode11,
      ShaderBuildResult shaderBuildResult6,
      float f,
      float g,
      float h,
      float i,
      float j,
      int k,
      int l,
      float m,
      float n,
      ColorScheme colorScheme10,
      float o
   ) {
      if (!(h <= 1.0F) && !(i <= 1.0F)) {
         renderManager11.invoke20();
         renderManager11.invoke24(f, g, h, i, j, j, j, j);
         boolean flag3 = false ;

         try {
            flag3 = true;
            invoke18(renderManager11, namedShaderProgram10, shaderNode11, shaderBuildResult6, f, g, h, i, k, l, m, n, colorScheme10, o);
            flag3 = false;
         } finally {
            if (flag3) {
               renderManager11.invoke20();
               renderManager11.invoke25();
            }
         }

         renderManager11.invoke20();
         renderManager11.invoke25();
      }
   }

   private static void invoke12(
      RenderManager renderManager12,
      ThemeContext themeContext8,
      NamedShaderProgram namedShaderProgram11,
      ShaderNode shaderNode12,
      float f,
      float g,
      float h,
      float i,
      int j,
      int k,
      float l,
      float m,
      float n
   ) {
      Metrics metrics4 = themeContext8.getMetrics();
      ColorScheme colorScheme11 = themeContext8.getColorScheme();
      invoke16(renderManager12, colorScheme11, f, g, h, i, n);
      float floatValue39 = Math.min(h * 0.82F, metrics4.measure(280.0F));
      float floatValue40 = Math.max(metrics4.measure(14.0F), Math.min(i * 0.16F, metrics4.measure(26.0F)));
      float floatValue41 = f + (h - floatValue39) * 0.5F;
      float floatValue42 = g + i * 0.44F;
      float floatValue43 = floatValue40 * 0.5F;
      float floatValue44 = 0.68F;
      renderManager12.invoke5(floatValue41, floatValue42, floatValue39, floatValue40, floatValue43, ColorScheme.compute5(10, 12, 18, Math.round(220.0F * n)));
      renderManager12.invoke28(floatValue41, floatValue42, floatValue39, floatValue40, floatValue43, ColorScheme.compute6(colorScheme11.getIntValue13(), Math.round(40.0F * n)), 0.7F);
      float floatValue45 = Math.max(floatValue40, floatValue39 * floatValue44);
      renderManager12.invoke20();
      renderManager12.invoke24(floatValue41, floatValue42, floatValue45, floatValue40, floatValue43, floatValue43, floatValue43, floatValue43);

      try {
         invoke17(renderManager12, namedShaderProgram11, shaderNode12, floatValue41, floatValue42, floatValue39, floatValue40, j, k, l, m, colorScheme11, n);
      } finally {
         renderManager12.invoke20();
         renderManager12.invoke25();
      }

      renderManager12.invoke5(
         floatValue41 + floatValue45 - metrics4.measure(1.5F),
         floatValue42 + metrics4.measure(1.5F),
         metrics4.measure(1.5F),
         floatValue40 - metrics4.measure(3.0F),
         0.0F,
         ColorScheme.compute6(colorScheme11.getIntValue13(), Math.round(150.0F * n))
      );
      float floatValue46 = floatValue42 + floatValue40 + metrics4.measure(8.0F);
      float floatValue47 = Math.max(metrics4.measure(6.0F), floatValue40 * 0.42F);
      renderManager12.invoke5(floatValue41, floatValue46, floatValue39, floatValue47, floatValue47 * 0.5F, ColorScheme.compute5(10, 12, 18, Math.round(200.0F * n)));
      renderManager12.invoke5(floatValue41, floatValue46, floatValue39 * 0.5F, floatValue47, floatValue47 * 0.5F, ColorScheme.compute6(colorScheme11.getIntValue14(), Math.round(150.0F * n)));
      ClickGuiRenderUtils.invoke4(
         renderManager12,
         metrics4,
         FontRegistry.fontObject,
         floatValue41,
         floatValue42 - metrics4.measure(16.0F),
         metrics4.measure(12.0F),
         8.0F,
         "20.0 / 20.0",
         ColorScheme.compute6(colorScheme11.getIntValue13(), Math.round(180.0F * n))
      );
   }

   private static EntityGuiElementRenderState resolve(MinecraftClient minecraftClient, ClientPlayerEntity clientPlayerEntity, float f, float g, float h) {
      if (minecraftClient != null && clientPlayerEntity != null) {
         int intValue2 = Math.max(48, Math.round(h * 2.24F));
         int intValue3 = Math.max(36, Math.round(intValue2 * 0.68F));
         int intValue4 = Math.round(f - intValue3 * 0.5F);
         int intValue5 = intValue4 + intValue3;
         int intValue6 = Math.round(g);
         int intValue7 = intValue6 - intValue2;
         int intValue8 = Math.max(18, Math.round(intValue2 * 0.43F));
         float floatValue48 = (intValue4 + intValue5) * 0.5F;
         float floatValue49 = (intValue7 + intValue6) * 0.5F;
         float floatValue50 = floatValue48 - (float)Math.tan(1.1F) * 40.0F;
         float floatValue51 = floatValue49 - (float)Math.tan(0.3F) * 40.0F;
         GuiRenderState guiRenderState = new GuiRenderState();
         DrawContext context = new DrawContext(minecraftClient, guiRenderState);
         InventoryScreen.drawEntity(context, intValue4, intValue7, intValue5, intValue6, intValue8, 0.0625F, floatValue50, floatValue51, clientPlayerEntity);
         EntityGuiElementRenderState[] entityGuiElementRenderStates = new EntityGuiElementRenderState[1];
         guiRenderState.forEachSpecialElement(specialGuiElementRenderState -> {
            if (specialGuiElementRenderState instanceof EntityGuiElementRenderState entityGuiElementRenderState4) {
               entityGuiElementRenderStates[0] = entityGuiElementRenderState4;
            }
         });
         return entityGuiElementRenderStates[0];
      } else {
         return null;
      }
   }

   private static int compute(MinecraftClient minecraftClient, EntityGuiElementRenderState entityGuiElementRenderState, int i, int j) {
      if (entityGuiElementRenderState == null) {
         return 0;
      } else {
         int intValue9 = Math.max(1, i);
         int intValue10 = Math.max(1, j);
         OFFSCREEN_FRAMEBUFFER_2.invoke(intValue9, intValue10);
         if (!OFFSCREEN_FRAMEBUFFER_2.check()) {
            return 0;
         } else {
            FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();

            try {
               OFFSCREEN_FRAMEBUFFER_2.invoke2();
               GL11.glViewport(0, 0, Math.max(0, intValue9), Math.max(0, intValue10));
               GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
               GL11.glClear(16640);
               GL11.glDisable(2929);
               GL11.glDisable(2884);
               GL11.glDepthMask(false);
               GlStateManager._enableBlend();
               GL11.glEnable(3042);
               GL14.glBlendFuncSeparate(770, 771, 1, 771);
               GL11.glDisable(36281);
               invoke14(minecraftClient, entityGuiElementRenderState);
            } catch (Throwable exception) {
               GL30.glBindFramebuffer(36160, 0);
               FramebufferUtils.restoreGlState(glStateSnapshot);
               invoke20();
               return 0;
            }

            GL30.glBindFramebuffer(36160, 0);
            GL20.glUseProgram(0);
            FramebufferUtils.restoreGlState(glStateSnapshot);
            invoke20();
            return OFFSCREEN_FRAMEBUFFER_2.getIntValue2();
         }
      }
   }

   private static void invoke13(MinecraftClient minecraftClient, EntityGuiElementRenderState entityGuiElementRenderState) {
      if (entityGuiElementRenderState != null) {
         FramebufferUtils.GlStateSnapshot glStateSnapshot2 = FramebufferUtils.captureGlState();
         boolean flag4 = false ;

         label48: {
            try {
               flag4 = true;
               GlStateManager._enableBlend();
               GL11.glEnable(3042);
               GL14.glBlendFuncSeparate(770, 771, 1, 771);
               invoke14(minecraftClient, entityGuiElementRenderState);
               flag4 = false;
               break label48;
            } catch (Throwable exception2) {
               flag4 = false;
            } finally {
               if (flag4) {
                  GL20.glUseProgram(0);
                  FramebufferUtils.restoreGlState(glStateSnapshot2);
                  invoke20();
               }
            }

            GL20.glUseProgram(0);
            FramebufferUtils.restoreGlState(glStateSnapshot2);
            invoke20();
            return;
         }

         GL20.glUseProgram(0);
         FramebufferUtils.restoreGlState(glStateSnapshot2);
         invoke20();
      }
   }

   private static void invoke14(MinecraftClient minecraftClient, EntityGuiElementRenderState entityGuiElementRenderState) {
      if (minecraftClient != null && entityGuiElementRenderState != null) {
         EntityRenderDispatcher entityRenderDispatcher = minecraftClient.getEntityRenderDispatcher();
         if (entityRenderDispatcher != null) {
            Immediate immediate = minecraftClient.getBufferBuilders().getEntityVertexConsumers();
            if (immediate != null) {
               MatrixStack matrices = new MatrixStack();
               matrices.push();
               matrices.translate(
                  (entityGuiElementRenderState.x1() + entityGuiElementRenderState.x2()) * 0.5F,
                  (entityGuiElementRenderState.y1() + entityGuiElementRenderState.y2()) * 0.5F,
                  1000.0F
               );
               matrices.scale(entityGuiElementRenderState.scale(), entityGuiElementRenderState.scale(), -entityGuiElementRenderState.scale());
               Vector3f vector3f = entityGuiElementRenderState.translation();
               matrices.translate(vector3f.x, vector3f.y, vector3f.z);
               matrices.multiply(entityGuiElementRenderState.rotation());
               Quaternionf quaternionf = entityGuiElementRenderState.overrideCameraAngle();
               boolean flag5 = false;
               if (quaternionf != null) {
                  entityRenderDispatcher.setRotation(quaternionf.conjugate(new Quaternionf()).rotateY((float) Math.PI));
                  flag5 = true;
               }

               entityRenderDispatcher.setRenderShadows(false);
               int intValue11 = LightmapTextureManager.pack(15, 15);
               boolean flag6 = false ;

               label89: {
                  label88: {
                     try {
                        flag6 = true;
                        entityRenderDispatcher.render(entityGuiElementRenderState.renderState(), 0.0, 0.0, 0.0, matrices, immediate, intValue11);
                        immediate.draw();
                        flag6 = false;
                        break label88;
                     } catch (Throwable exception3) {
                        flag6 = false;
                     } finally {
                        if (flag6) {
                           entityRenderDispatcher.setRenderShadows(true);
                           if (flag5 && minecraftClient.gameRenderer != null && minecraftClient.gameRenderer.getCamera() != null) {
                              entityRenderDispatcher.setRotation(minecraftClient.gameRenderer.getCamera().getRotation());
                           }
                        }
                     }

                     entityRenderDispatcher.setRenderShadows(true);
                     if (flag5 && minecraftClient.gameRenderer != null && minecraftClient.gameRenderer.getCamera() != null) {
                        entityRenderDispatcher.setRotation(minecraftClient.gameRenderer.getCamera().getRotation());
                     }
                     break label89;
                  }

                  entityRenderDispatcher.setRenderShadows(true);
                  if (flag5 && minecraftClient.gameRenderer != null && minecraftClient.gameRenderer.getCamera() != null) {
                     entityRenderDispatcher.setRotation(minecraftClient.gameRenderer.getCamera().getRotation());
                  }
               }

               matrices.pop();
            }
         }
      }
   }

   private static void invoke15(
      RenderManager renderManager13,
      NamedShaderProgram namedShaderProgram12,
      ShaderNode shaderNode13,
      ShaderBuildResult shaderBuildResult7,
      float f,
      float g,
      float h,
      float i,
      float j,
      float k,
      float l,
      float m,
      float n,
      float o,
      float p,
      ColorScheme colorScheme12,
      float q
   ) {
      renderManager13.invoke20();
      int intValue12 = Math.max(1, (int)Math.ceil(h));
      int intValue13 = Math.max(1, (int)Math.ceil(i));
      OFFSCREEN_FRAMEBUFFER.invoke(intValue12, intValue13);
      if (!OFFSCREEN_FRAMEBUFFER.check()) {
         invoke18(
            renderManager13,
            namedShaderProgram12,
            shaderNode13,
            shaderBuildResult7,
            j,
            k,
            l,
            m,
            Math.max(1, Math.round(l)),
            Math.max(1, Math.round(m)),
            o,
            p,
            colorScheme12,
            q
         );
      } else {
         FramebufferUtils.GlStateSnapshot glStateSnapshot3 = FramebufferUtils.captureGlState();
         float[] floatValues = new float[4];
         GL11.glGetFloatv(3106, floatValues);
         boolean flag7 = false ;

         try {
            flag7 = true;
            OFFSCREEN_FRAMEBUFFER.invoke2();
            GL11.glViewport(0, 0, Math.max(0, intValue12), Math.max(0, intValue13));
            GL11.glDisable(2929);
            GL11.glDisable(2884);
            GL11.glDepthMask(false);
            GlStateManager._enableBlend();
            GL11.glEnable(3042);
            GL14.glBlendFuncSeparate(770, 771, 1, 771);
            GL11.glDisable(36281);
            GL11.glEnable(3089);
            GL11.glScissor(0, 0, Math.max(0, intValue12), Math.max(0, intValue13));
            GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
            GL11.glClear(16384);
            float floatValue52 = j - f;
            float floatValue53 = k - g;
            float floatValue54 = Math.max(4.0F, Math.min(28.0F, Math.min(l, m) * 0.44F));
            if (shaderBuildResult7 != null) {
               ShaderUniformBinder.check7(
                  resolve3(),
                  shaderBuildResult7,
                  floatValue52 - floatValue54,
                  floatValue53 - floatValue54,
                  l + floatValue54 * 2.0F,
                  m + floatValue54 * 2.0F,
                  floatValue52,
                  floatValue53,
                  l,
                  m,
                  n,
                  intValue12,
                  intValue13,
                  o - f,
                  p - g,
                  colorScheme12,
                  q
               );
               flag7 = false;
            } else if (namedShaderProgram12 != null) {
               namedShaderProgram12.invoke3(shaderNode13, floatValue52, floatValue53, l, m, intValue12, intValue13, o - f, p - g, colorScheme12, q);
               flag7 = false;
            } else {
               flag7 = false;
            }
         } finally {
            if (flag7) {
               GL11.glClearColor(floatValues[0], floatValues[1], floatValues[2], floatValues[3]);
               GL20.glUseProgram(0);
               FramebufferUtils.restoreGlState(glStateSnapshot3);
               invoke20();
               GlStateManager._enableBlend();
               GL11.glEnable(3042);
               GL14.glBlendFuncSeparate(770, 771, 1, 771);
            }
         }

         GL11.glClearColor(floatValues[0], floatValues[1], floatValues[2], floatValues[3]);
         GL20.glUseProgram(0);
         FramebufferUtils.restoreGlState(glStateSnapshot3);
         invoke20();
         GlStateManager._enableBlend();
         GL11.glEnable(3042);
         GL14.glBlendFuncSeparate(770, 771, 1, 771);
         renderManager13.drawFlippedTexture(OFFSCREEN_FRAMEBUFFER.getIntValue2(), f, g, h, i);
         renderManager13.invoke20();
      }
   }

   private static void invoke16(RenderManager renderManager14, ColorScheme colorScheme13, float f, float g, float h, float i, float j) {
      boolean flag8 = colorScheme13.isFlag();
      int intValue14 = Math.round(255.0F * Math.max(0.0F, Math.min(1.0F, j)));
      int intValue15 = flag8 ? ColorScheme.compute5(233, 236, 243, intValue14) : ColorScheme.compute5(26, 28, 37, intValue14);
      int intValue16 = flag8 ? ColorScheme.compute5(212, 216, 227, intValue14) : ColorScheme.compute5(12, 13, 19, intValue14);
      renderManager14.invoke37(f, g, h, i, 0.0F, intValue15, intValue16);
      renderManager14.invoke37(
         f,
         g,
         h,
         i * 0.6F,
         0.0F,
         ColorScheme.compute6(colorScheme13.getIntValue14(), Math.round(16.0F * j)),
         ColorScheme.compute6(colorScheme13.getIntValue14(), 0)
      );
      float floatValue55 = Math.min(h, i) * 0.34F;
      int intValue17 = ColorScheme.compute5(0, 0, 0, Math.round((flag8 ? 26.0F : 60.0F) * j));
      renderManager14.invoke37(f, g, h, floatValue55, 0.0F, intValue17, ColorScheme.compute5(0, 0, 0, 0));
      renderManager14.invoke37(f, g + i - floatValue55, h, floatValue55, 0.0F, ColorScheme.compute5(0, 0, 0, 0), intValue17);
      renderManager14.invoke34(f, g, floatValue55, i, 0.0F, intValue17, ColorScheme.compute5(0, 0, 0, 0));
      renderManager14.invoke34(f + h - floatValue55, g, floatValue55, i, 0.0F, ColorScheme.compute5(0, 0, 0, 0), intValue17);
   }

   private static void invoke17(
      RenderManager renderManager15,
      NamedShaderProgram namedShaderProgram13,
      ShaderNode shaderNode14,
      float f,
      float g,
      float h,
      float i,
      int j,
      int k,
      float l,
      float m,
      ColorScheme colorScheme14,
      float n
   ) {
      invoke18(renderManager15, namedShaderProgram13, shaderNode14, resolve2(namedShaderProgram13, shaderNode14), f, g, h, i, j, k, l, m, colorScheme14, n);
   }

   private static void invoke18(
      RenderManager renderManager16,
      NamedShaderProgram namedShaderProgram14,
      ShaderNode shaderNode15,
      ShaderBuildResult shaderBuildResult8,
      float f,
      float g,
      float h,
      float i,
      int j,
      int k,
      float l,
      float m,
      ColorScheme colorScheme15,
      float n
   ) {
      renderManager16.invoke20();
      int intValue18 = Math.max(1, (int)Math.ceil(h));
      int intValue19 = Math.max(1, (int)Math.ceil(i));
      OFFSCREEN_FRAMEBUFFER.invoke(intValue18, intValue19);
      if (!OFFSCREEN_FRAMEBUFFER.check()) {
         if (shaderBuildResult8 != null) {
            ShaderUniformBinder.check8(resolve3(), shaderBuildResult8, f, g, h, i, j, k, l, m, colorScheme15, n);
            invoke20();
         } else if (namedShaderProgram14 != null) {
            namedShaderProgram14.invoke3(shaderNode15, f, g, h, i, j, k, l, m, colorScheme15, n);
         }

         renderManager16.invoke20();
      } else {
         FramebufferUtils.GlStateSnapshot glStateSnapshot4 = FramebufferUtils.captureGlState();
         float[] floatValues2 = new float[4];
         GL11.glGetFloatv(3106, floatValues2);
         boolean flag9 = false ;

         try {
            flag9 = true;
            OFFSCREEN_FRAMEBUFFER.invoke2();
            GL11.glViewport(0, 0, Math.max(0, intValue18), Math.max(0, intValue19));
            GL11.glDisable(2929);
            GL11.glDisable(2884);
            GL11.glDepthMask(false);
            GlStateManager._enableBlend();
            GL11.glEnable(3042);
            GL14.glBlendFuncSeparate(770, 771, 1, 771);
            GL11.glDisable(36281);
            GL11.glEnable(3089);
            GL11.glScissor(0, 0, Math.max(0, intValue18), Math.max(0, intValue19));
            GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
            GL11.glClear(16384);
            if (shaderBuildResult8 != null) {
               ShaderUniformBinder.check8(resolve3(), shaderBuildResult8, 0.0F, 0.0F, intValue18, intValue19, intValue18, intValue19, l - f, m - g, colorScheme15, n);
               flag9 = false;
            } else if (namedShaderProgram14 != null) {
               namedShaderProgram14.invoke3(shaderNode15, 0.0F, 0.0F, intValue18, intValue19, intValue18, intValue19, l - f, m - g, colorScheme15, n);
               flag9 = false;
            } else {
               flag9 = false;
            }
         } finally {
            if (flag9) {
               GL11.glClearColor(floatValues2[0], floatValues2[1], floatValues2[2], floatValues2[3]);
               GL20.glUseProgram(0);
               FramebufferUtils.restoreGlState(glStateSnapshot4);
               invoke20();
               GlStateManager._enableBlend();
               GL11.glEnable(3042);
               GL14.glBlendFuncSeparate(770, 771, 1, 771);
            }
         }

         GL11.glClearColor(floatValues2[0], floatValues2[1], floatValues2[2], floatValues2[3]);
         GL20.glUseProgram(0);
         FramebufferUtils.restoreGlState(glStateSnapshot4);
         invoke20();
         GlStateManager._enableBlend();
         GL11.glEnable(3042);
         GL14.glBlendFuncSeparate(770, 771, 1, 771);
         renderManager16.drawFlippedTexture(OFFSCREEN_FRAMEBUFFER.getIntValue2(), f, g, h, i);
         renderManager16.invoke20();
      }
   }

   public static void invoke19(
      RenderManager renderManager17,
      ThemeContext themeContext9,
      ShaderBuildResult shaderBuildResult9,
      String string,
      float f,
      float g,
      float h,
      float i,
      int j,
      int k,
      float l,
      float m,
      float n
   ) {
      ColorScheme colorScheme16 = themeContext9.getColorScheme();
      Metrics metrics5 = themeContext9.getMetrics();
      float floatValue56 = metrics5.measure(6.0F);
      renderManager17.invoke20();
      renderManager17.invoke24(f, g, h, i, floatValue56, floatValue56, floatValue56, floatValue56);
      boolean flag10 = false ;

      try {
         flag10 = true;
         invoke16(renderManager17, colorScheme16, f, g, h, i, n);
         if (shaderBuildResult9 != null) {
            if (shaderBuildResult9.ok()) {
               if (string != null) {
                  if (!string.isBlank()) {
                     if (h > 2.0F) {
                        if (i > 2.0F) {
                           int intValue20 = Math.max(1, (int)Math.ceil(h));
                           int intValue21 = Math.max(1, (int)Math.ceil(i));
                           OFFSCREEN_FRAMEBUFFER_3.invoke(intValue20, intValue21);
                           if (OFFSCREEN_FRAMEBUFFER_3.check()) {
                              renderManager17.invoke20();
                              FramebufferUtils.GlStateSnapshot glStateSnapshot5 = FramebufferUtils.captureGlState();
                              float[] floatValues3 = new float[4];
                              GL11.glGetFloatv(3106, floatValues3);

                              try {
                                 OFFSCREEN_FRAMEBUFFER_3.invoke2();
                                 GL11.glViewport(0, 0, Math.max(0, intValue20), Math.max(0, intValue21));
                                 GL11.glDisable(2929);
                                 GL11.glDisable(2884);
                                 GL11.glDepthMask(false);
                                 GlStateManager._enableBlend();
                                 GL11.glEnable(3042);
                                 GL14.glBlendFuncSeparate(770, 771, 1, 771);
                                 GL11.glDisable(36281);
                                 GL11.glEnable(3089);
                                 GL11.glScissor(0, 0, Math.max(0, intValue20), Math.max(0, intValue21));
                                 GL11.glClearColor(0.0F, 0.0F, 0.0F, 0.0F);
                                 GL11.glClear(16384);
                                 ShaderUniformBinder.check8(string, shaderBuildResult9, 0.0F, 0.0F, intValue20, intValue21, intValue20, intValue21, l - f, m - g, colorScheme16, 1.0F);
                              } finally {
                                 GL11.glClearColor(floatValues3[0], floatValues3[1], floatValues3[2], floatValues3[3]);
                                 GL20.glUseProgram(0);
                                 FramebufferUtils.restoreGlState(glStateSnapshot5);
                                 invoke20();
                                 GlStateManager._enableBlend();
                                 GL11.glEnable(3042);
                                 GL14.glBlendFuncSeparate(770, 771, 1, 771);
                              }

                              renderManager17.drawFlippedTexture(OFFSCREEN_FRAMEBUFFER_3.getIntValue2(), f, g, h, i);
                              renderManager17.invoke20();
                              flag10 = false;
                           } else {
                              flag10 = false;
                           }
                        } else {
                           flag10 = false;
                        }
                     } else {
                        flag10 = false;
                     }
                  } else {
                     flag10 = false;
                  }
               } else {
                  flag10 = false;
               }
            } else {
               flag10 = false;
            }
         } else {
            flag10 = false;
         }
      } finally {
         if (flag10) {
            renderManager17.invoke20();
            renderManager17.invoke25();
         }
      }

      renderManager17.invoke20();
      renderManager17.invoke25();
      renderManager17.invoke28(f, g, h, i, floatValue56, ColorScheme.compute6(colorScheme16.getIntValue14(), Math.round(70.0F * n)), 0.6F);
   }

   private static ShaderBuildResult resolve2(NamedShaderProgram namedShaderProgram15, ShaderNode shaderNode16) {
      if (namedShaderProgram15 != null) {
         return namedShaderProgram15.resolve(shaderNode16);
      } else if (shaderNode16 == null) {
         return null;
      } else {
         if (text != null && !text.isBlank()) {
            ShaderBuildResult shaderBuildResult10 = ShaderPresetRegistry.getINSTANCE().resolve2(text);
            if (shaderBuildResult10 != null) {
               return shaderBuildResult10;
            }
         }

         String text5 = shaderNode16.getShaderTemplate() == null ? "" : shaderNode16.getShaderTemplate().getText();
         if (!text5.isBlank()) {
            ShaderBuildResult shaderBuildResult11 = ShaderPresetRegistry.getINSTANCE().resolve2(text5);
            if (shaderBuildResult11 != null) {
               return shaderBuildResult11;
            }
         }

         return null;
      }
   }

   private static String resolve3() {
      return foundryPreviewLive != null && !foundryPreviewLive.isBlank() ? foundryPreviewLive : "__foundry_preview_live";
   }

   private static void invoke20() {
      GL20.glUseProgram(0);
      if (!Boolean.FALSE.equals(booleanValue)) {
         try {
            if (booleanValue == null) {
               Class type = Class.forName("com.mojang.blaze3d.systems.RenderSystem");
               Class type2 = Class.forName("net.minecraft.client.render.GameRenderer");
               method = type.getMethod("setShader", Supplier.class);
               method2 = type2.getMethod("getPositionColorProgram");
               booleanValue = true;
            }

            Supplier supplier = () -> {
               try {
                  return method2.invoke(null);
               } catch (Throwable var1x) {
                  return null;
               }
            };
            method.invoke(null, supplier);
         } catch (Throwable exception4) {
            booleanValue = false;
         }
      }
   }
}
