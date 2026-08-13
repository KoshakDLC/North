package ru.metaculture.protection;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.function.Predicate;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.RenderLayer.MultiPhaseParameters;
import net.minecraft.client.render.RenderPhase.Texture;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.joml.Vector3f;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "TargetESP",
   description = "Жозки таргет есп",
   category = Category.Visuals
)
public class TargetESP extends Module implements ShaderBinding {
   private static final String TARGET_ESP = "target_esp";
   public static ModeSetting tekstura = new ModeSetting("Текстура", "Кристаллы", "Кристаллы", "Картинка", "Призраки", "Кольцо", "Кубики", "Сфера", "Дельта");
   public static ModeSetting rezhimPrizrakov = new ModeSetting("Режим призраков", "Обычный", "Обычный", "Новый", "Старый", "Орбита", "Спираль")
      .setVisibilityCondition(() -> !tekstura.is("Призраки"));
   public static ModeSetting rezhimKartinki = new ModeSetting("Режим картинки", "Клиент", "Клиент", "Ромб", "Ромб 2")
      .setVisibilityCondition(() -> !tekstura.is("Картинка"));
   public static ModeSetting rezhimKubikov = new ModeSetting("Режим кубиков", "Новый", "Новый", "Старый", "Орбита")
      .setVisibilityCondition(() -> !tekstura.is("Кубики"));
   public static ModeSetting rezhimKristallov = new ModeSetting("Режим кристаллов", "Орбита", "Орбита", "Хаос")
      .setVisibilityCondition(() -> !tekstura.is("Кристаллы"));
   public static ModeSetting rezhimDelta = new ModeSetting("Режим Дельта", "Сферы", "Сферы", "Круг")
      .setVisibilityCondition(() -> !tekstura.is("Дельта"));
   private static final Vector3f[] DELTA_CRYSTAL_VERTS = new Vector3f[]{
      new Vector3f(0.0F, 1.5F, 0.0F),
      new Vector3f(0.0F, -1.5F, 0.0F),
      new Vector3f(1.0F, 0.0F, 0.0F),
      new Vector3f(-1.0F, 0.0F, 0.0F),
      new Vector3f(0.0F, 0.0F, 1.0F),
      new Vector3f(0.0F, 0.0F, -1.0F)
   };
   private static final int[][] DELTA_CRYSTAL_FACES = new int[][]{
      {0, 4, 2}, {0, 3, 4}, {0, 5, 3}, {0, 2, 5}, {1, 2, 4}, {1, 4, 3}, {1, 3, 5}, {1, 5, 2}
   };
   private static final float[] DELTA_CRYSTAL_SHADE = new float[]{1.0F, 0.8F, 0.6F, 0.9F, 0.7F, 0.5F, 0.4F, 0.6F};
   public static FoundryShaderSetting foundryShader = new FoundryShaderSetting("Foundry Shader", ShaderSurface.ESP);
   private static final Identifier IDENTIFIER = Identifier.of("wild", "textures/world/target.png");
   private static final Identifier IDENTIFIER_2 = Identifier.of("wild", "textures/world/targetn2.png");
   private static final Identifier IDENTIFIER_3 = Identifier.of("wild", "textures/world/targetn.png");
   private static final Identifier IDENTIFIER_4 = Identifier.of("wild", "textures/world/glow.png");
   private static final Identifier IDENTIFIER_5 = Identifier.of("wild", "textures/world/dashbloom.png");
   public static EasedAnimation easedAnimation = new EasedAnimation();
   public static EasedAnimation easedAnimation2 = new EasedAnimation();
   private LivingEntity livingEntity = null;
   private final Predicate<Entity> predicate = entity -> entity == AttackAura.livingEntity || entity == this.livingEntity;
   private static long timestamp = 0L;
   private float floatValue = 0.0F;
   private long timestamp2 = 0L;
   private final ArrayList<TargetESP.TargetESPEntityState> arrayList = new ArrayList<>();
   private static long timestamp3 = System.currentTimeMillis();
   static float floatValue2 = 0.0F;
   private static final long TIMESTAMP = 1000L;
   private static final int INT_VALUE = 1;
   private static final float FLOAT_VALUE = 0.02F;
   private static final int INT_VALUE_2 = 50;
   private float floatValue3 = 0.0F;
   private static final int INT_VALUE_3 = 1024;
   private static final String WILD = "wild";
   private static final RenderPipeline RENDER_PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_TEX_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "pipeline/world/textured_quads"))
         .withVertexFormat(VertexFormats.POSITION_TEXTURE_COLOR, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.LIGHTNING)
         .build()
   );
   private static final RenderPipeline RENDER_PIPELINE_2 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_TEX_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "pipeline/world/textured_quads"))
         .withVertexFormat(VertexFormats.POSITION_TEXTURE_COLOR, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.LIGHTNING)
         .build()
   );
   private static final RenderLayer RENDER_LAYER = resolve5(IDENTIFIER, RENDER_PIPELINE_2);
   private static final RenderLayer RENDER_LAYER_2 = resolve5(IDENTIFIER_3, RENDER_PIPELINE_2);
   private static final RenderLayer RENDER_LAYER_3 = resolve5(IDENTIFIER_2, RENDER_PIPELINE_2);
   private static final RenderLayer RENDER_LAYER_4 = resolve5(IDENTIFIER_4, RENDER_PIPELINE);
   private static final RenderLayer RENDER_LAYER_5 = resolve5(IDENTIFIER_5, RENDER_PIPELINE);
   private static final RenderPipeline RENDER_PIPELINE_3 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("minecraft", "rendertype_lequal_depth_test"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.TRIANGLE_STRIP)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.LIGHTNING)
         .build()
   );
   private static final RenderPipeline RENDER_PIPELINE_4 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("minecraft", "rendertype_lines"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.DEBUG_LINE_STRIP)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.LIGHTNING)
         .build()
   );
   private static final RenderLayer RENDER_LAYER_6 = RenderLayer.of(
      "ring_strip", 1024, false, true, RENDER_PIPELINE_3, MultiPhaseParameters.builder().build(false)
   );
   private static final RenderLayer RENDER_LAYER_7 = RenderLayer.of("ring_line", 1024, false, true, RENDER_PIPELINE_4, MultiPhaseParameters.builder().build(false));
   private static final RenderPipeline RENDER_PIPELINE_5 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "pipeline/world/color_quads"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.LIGHTNING)
         .build()
   );
   private static final RenderLayer RENDER_LAYER_8 = RenderLayer.of("color_quads", 1024, false, true, RENDER_PIPELINE_5, MultiPhaseParameters.builder().build(false));
   private static final RenderPipeline RENDER_PIPELINE_6 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("minecraft", "rendertype_lines"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.LINES)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.LIGHTNING)
         .build()
   );
   private static final RenderPipeline RENDER_PIPELINE_7 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "targetesp_cube_lines"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.DEBUG_LINES)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.LIGHTNING)
         .build()
   );
   private static final RenderLayer RENDER_LAYER_9 = RenderLayer.of(
      "targetesp_cube_lines", 1024, false, true, RENDER_PIPELINE_7, MultiPhaseParameters.builder().build(false)
   );
   private static final RenderPipeline RENDER_PIPELINE_8 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "targetesp_cube_fill"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.TRANSLUCENT)
         .build()
   );
   static final RenderLayer RENDER_LAYER_10 = RenderLayer.of(
      "targetesp_cube_fill", 1024, false, true, RENDER_PIPELINE_8, MultiPhaseParameters.builder().build(false)
   );
   private static final RenderPipeline RENDER_PIPELINE_9 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "targetesp_cube_outline"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.DEBUG_LINES)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.TRANSLUCENT)
         .build()
   );
   static final RenderLayer RENDER_LAYER_11 = RenderLayer.of("targetesp_cube_outline", 1024, false, true, RENDER_PIPELINE_9, MultiPhaseParameters.builder().build(false));

   public TargetESP() {
      this.addSettings(new Setting[]{tekstura, rezhimPrizrakov, rezhimKartinki, rezhimKubikov, rezhimKristallov, rezhimDelta, foundryShader});
   }

   @Override
   public ShaderSurface getESP() {
      return ShaderSurface.ESP;
   }

   @Override
   public String resolve() {
      String text = resolve2();
      return text != null && !text.isBlank() ? text : null;
   }

   public static String resolve2() {
      String text2 = foundryShader == null ? "" : foundryShader.resolve2();
      return text2 == null ? "" : text2;
   }

   @Override
   public boolean check() {
      return true;
   }

   @Override
   public void onEnable() {
      super.onEnable();
      ShaderBindingRegistry.getINSTANCE().invoke(this, this);
      this.invoke();
   }

   @Override
   public void onDisable() {
      EntityFramebufferCapture.getInstance().removeCaptureFilter("target_esp");
      ShaderBindingRegistry.getINSTANCE().invoke4(this);
      this.arrayList.clear();
      super.onDisable();
   }

   @EventHandler
   public void onRender3D(Render3DEvent render3DEvent) {
      ShaderBindingRegistry.getINSTANCE().invoke7(this, this);
      this.invoke();
      easedAnimation.check();
      LivingEntity livingEntity2 = AttackAura.livingEntity != null ? AttackAura.livingEntity : TriggerBot.getLivingEntity();
      if (CLIENT.world != null && CLIENT.player != null) {
         AttackAura attackAura = (AttackAura)WildClient.INSTANCE.moduleManager.findModule(AttackAura.class);
         if (attackAura != null) {
            easedAnimation.animateTo(livingEntity2 == null ? 0.0 : 1.0, 0.35F, Easings.EASE_OUT_QUART);
            if (easedAnimation.getDoubleValue4() > 0.0) {
               if (livingEntity2 != null) {
                  if (this.livingEntity != livingEntity2) {
                     timestamp = 0L;
                     this.timestamp2 = 0L;
                     this.floatValue = 0.0F;
                  }

                  this.livingEntity = livingEntity2;
               }

               if (this.livingEntity != null && !tekstura.is("Не отображать")) {
                  Immediate immediate2 = WorldRenderBuffer.getIMMEDIATE();

                  try {
                     if (tekstura.is("Картинка") && rezhimKartinki.is("Ромб")) {
                        this.invoke2(render3DEvent.getMatrixStack(), immediate2, this.livingEntity, render3DEvent.getFloatValue());
                     }

                     if (tekstura.is("Картинка") && rezhimKartinki.is("Клиент")) {
                        this.invoke3(render3DEvent.getMatrixStack(), immediate2, this.livingEntity, render3DEvent.getFloatValue());
                     }

                     if (tekstura.is("Картинка") && rezhimKartinki.is("Ромб 2")) {
                        this.invoke4(render3DEvent.getMatrixStack(), immediate2, this.livingEntity, render3DEvent.getFloatValue());
                     }

                     if (tekstura.is("Призраки") && rezhimPrizrakov.is("Обычный")) {
                        this.invoke15(render3DEvent.getMatrixStack(), immediate2, this.livingEntity, render3DEvent.getFloatValue());
                     }

                     if (tekstura.is("Призраки") && rezhimPrizrakov.is("Новый")) {
                        this.invoke6(render3DEvent.getMatrixStack(), immediate2, this.livingEntity, render3DEvent.getFloatValue());
                     }

                     if (tekstura.is("Призраки") && rezhimPrizrakov.is("Старый")) {
                        this.invoke7(render3DEvent.getMatrixStack(), immediate2, this.livingEntity, render3DEvent.getFloatValue());
                     }

                     if (tekstura.is("Призраки") && rezhimPrizrakov.is("Орбита")) {
                        this.invoke9(render3DEvent.getMatrixStack(), immediate2, this.livingEntity, render3DEvent.getFloatValue());
                     }

                     if (tekstura.is("Призраки") && rezhimPrizrakov.is("Спираль")) {
                        this.invoke10(render3DEvent.getMatrixStack(), immediate2, this.livingEntity, render3DEvent.getFloatValue());
                     }

                     if (tekstura.is("Кольцо")) {
                        this.invoke5(render3DEvent.getMatrixStack(), immediate2, this.livingEntity, render3DEvent.getFloatValue());
                     }

                     if (tekstura.is("Кубики") && rezhimKubikov.is("Новый")) {
                        this.invoke17(render3DEvent.getMatrixStack(), immediate2, this.livingEntity, render3DEvent.getFloatValue());
                     }

                     if (tekstura.is("Кубики") && rezhimKubikov.is("Старый")) {
                        this.invoke18(render3DEvent.getMatrixStack(), immediate2, this.livingEntity, render3DEvent.getFloatValue());
                     }

                     if (tekstura.is("Кубики") && rezhimKubikov.is("Орбита")) {
                        this.invoke11(render3DEvent.getMatrixStack(), immediate2, this.livingEntity, render3DEvent.getFloatValue());
                     }

                     if (tekstura.is("Сфера")) {
                        this.invoke12(render3DEvent.getMatrixStack(), immediate2, this.livingEntity, render3DEvent.getFloatValue());
                     }

                     if (tekstura.is("Кристаллы")) {
                        this.invoke19(render3DEvent.getMatrixStack(), immediate2, this.livingEntity, render3DEvent.getFloatValue());
                     }

                     if (tekstura.is("Дельта") && rezhimDelta.is("Сферы")) {
                        this.invoke22(render3DEvent.getMatrixStack(), immediate2, this.livingEntity, render3DEvent.getFloatValue());
                     }

                     if (tekstura.is("Дельта") && rezhimDelta.is("Круг")) {
                        this.invoke23(render3DEvent.getMatrixStack(), immediate2, this.livingEntity, render3DEvent.getFloatValue());
                     }
                  } finally {
                     WorldRenderBuffer.invoke();
                  }
               }
            } else {
               this.livingEntity = null;
               timestamp = 0L;
               this.timestamp2 = 0L;
               this.floatValue = 0.0F;
               this.arrayList.clear();
            }
         }
      }
   }

   @EventHandler
   public void onHudRender(HudRenderEvent hudRenderEvent) {
      if (CLIENT.world != null && CLIENT.player != null && hudRenderEvent != null && hudRenderEvent.getClient() != null) {
         String text3 = resolve2();
         if (!text3.isBlank() && this.livingEntity != null && !(easedAnimation.getDoubleValue4() <= 0.001F)) {
            float floatValue = hudRenderEvent.getClient().getRenderTickCounter().getDynamicDeltaTicks();
            TargetESP.TargetESPData targetESPData = this.resolve3(this.livingEntity, floatValue, hudRenderEvent.getIntValue(), hudRenderEvent.getIntValue2());
            if (targetESPData != null) {
               RenderManager renderManager = hudRenderEvent.getRenderManager();
               if (renderManager != null) {
                  renderManager.invoke20();
               }

               float floatValue2 = (float)Math.min(0.92, easedAnimation.getDoubleValue4() * 0.78);
               float floatValue3 = targetESPData.x + targetESPData.w * 0.5F;
               float floatValue4 = targetESPData.y + targetESPData.h * 0.5F;
               int intValue = EntityFramebufferCapture.getInstance().getCaptureColorTextureId();
               boolean flag = NeumorphismRenderer.check16(
                  text3, intValue, targetESPData.x, targetESPData.y, targetESPData.w, targetESPData.h, hudRenderEvent.getIntValue(), hudRenderEvent.getIntValue2(), floatValue3, floatValue4, resolve4(), floatValue2
               );
               if (flag && renderManager != null) {
                  renderManager.invoke20();
               }
            }
         }
      }
   }

   private void invoke() {
      EntityFramebufferCapture.getInstance().setCaptureFilter("target_esp", this.enabled && !resolve2().isBlank(), this.predicate);
   }

   private TargetESP.TargetESPData resolve3(LivingEntity livingEntity, float f, int i, int j) {
      if (livingEntity != null
         && !livingEntity.isRemoved()
         && i > 1
         && j > 1
         && CLIENT.gameRenderer != null
         && CLIENT.gameRenderer.getCamera() != null) {
         Vec3d vec3d2 = livingEntity.getLerpedPos(f);
         Vec3d vec3d3 = livingEntity.getPos();
         Box box = livingEntity.getBoundingBox()
            .offset(vec3d2.x - vec3d3.x, vec3d2.y - vec3d3.y, vec3d2.z - vec3d3.z)
            .expand(0.05, Math.max(0.05, livingEntity.getHeight() * 0.035), 0.05);
         float floatValue5 = Float.POSITIVE_INFINITY;
         float floatValue6 = Float.POSITIVE_INFINITY;
         float floatValue7 = Float.NEGATIVE_INFINITY;
         float floatValue8 = Float.NEGATIVE_INFINITY;

         for (int intValue2 = 0; intValue2 < 2; intValue2++) {
            double doubleValue = intValue2 == 0 ? box.minX : box.maxX;

            for (int intValue3 = 0; intValue3 < 2; intValue3++) {
               double doubleValue2 = intValue3 == 0 ? box.minY : box.maxY;

               for (int intValue4 = 0; intValue4 < 2; intValue4++) {
                  double doubleValue3 = intValue4 == 0 ? box.minZ : box.maxZ;
                  Vec3d vec3d4 = MathUtils.resolve(new Vec3d(doubleValue, doubleValue2, doubleValue3));
                  if (vec3d4 == null || vec3d4.z <= 0.001F || vec3d4.z > 1.0) {
                     return null;
                  }

                  floatValue5 = Math.min(floatValue5, (float)vec3d4.x);
                  floatValue6 = Math.min(floatValue6, (float)vec3d4.y);
                  floatValue7 = Math.max(floatValue7, (float)vec3d4.x);
                  floatValue8 = Math.max(floatValue8, (float)vec3d4.y);
               }
            }
         }

         if (!Float.isFinite(floatValue5) || !Float.isFinite(floatValue6) || !Float.isFinite(floatValue7) || !Float.isFinite(floatValue8)) {
            return null;
         } else if (!(floatValue7 < 0.0F) && !(floatValue8 < 0.0F) && !(floatValue5 > i) && !(floatValue6 > j)) {
            float floatValue9 = Math.max(1.0F, floatValue7 - floatValue5);
            float floatValue10 = Math.max(1.0F, floatValue8 - floatValue6);
            float floatValue11 = Math.min(96.0F, Math.max(18.0F, floatValue9 * 0.28F));
            float floatValue12 = Math.min(96.0F, Math.max(18.0F, floatValue10 * 0.18F));
            float floatValue13 = Math.max(0.0F, floatValue5 - floatValue11);
            float floatValue14 = Math.max(0.0F, floatValue6 - floatValue12);
            float floatValue15 = Math.min((float)i, floatValue7 + floatValue11);
            float floatValue16 = Math.min((float)j, floatValue8 + floatValue12);
            float floatValue17 = floatValue15 - floatValue13;
            float floatValue18 = floatValue16 - floatValue14;
            return floatValue17 > 2.0F && floatValue18 > 2.0F ? new TargetESP.TargetESPData(floatValue13, floatValue14, floatValue17, floatValue18) : null;
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private static ColorScheme resolve4() {
      Theme theme = WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null ? WildClient.INSTANCE.themeManager.getTheme() : Theme.WILD;
      return ColorScheme.resolve2(theme, NeumorphismRenderer.check13());
   }

   private void invoke2(MatrixStack matrixStack, Immediate immediate, LivingEntity livingEntity, float f) {
      Vec3d vec3d5 = livingEntity.getLerpedPos(f);
      double doubleValue4 = vec3d5.x;
      double doubleValue5 = vec3d5.y;
      double doubleValue6 = vec3d5.z;
      Vec3d vec3d6 = CLIENT.gameRenderer.getCamera().getPos();
      matrixStack.push();
      matrixStack.translate(doubleValue4 - vec3d6.x, doubleValue5 - vec3d6.y + livingEntity.getHeight() / 1.75F, doubleValue6 - vec3d6.z);
      matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-CLIENT.gameRenderer.getCamera().getYaw()));
      matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(CLIENT.gameRenderer.getCamera().getPitch()));
      long longValue = System.currentTimeMillis();
      float floatValue19 = (float)MathUtils.measure26(0.0, 720.0, (Math.sin(longValue / 900.0) + 1.0) / 2.0 * 360.0 * 2.0);
      matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(floatValue19));
      easedAnimation2.check();
      int intValue5 = livingEntity.hurtTime;
      float floatValue20 = (float)Math.sin(intValue5 * (Math.PI / 20));
      easedAnimation2.animateTo(floatValue20, 0.4F, Easings.EASE_OUT_QUART);
      float floatValue21 = easedAnimation2.measure3();
      float floatValue22 = (float)easedAnimation.getDoubleValue4();
      int intValue6 = ColorUtils.compute43(200, 70, 70, (int)(255.0F * floatValue22));
      int intValue7 = ColorUtils.compute35(ColorUtils.compute31(ColorUtils.compute41(), floatValue22), intValue6, easedAnimation2.measure3());
      float floatValue23 = 1.7F - 0.9F * floatValue22 + (0.35F - 0.35F * floatValue21);
      matrixStack.scale(floatValue23, floatValue23, 1.0F);
      RenderLayer renderLayer2 = RENDER_LAYER;
      Matrix4f matrix4f2 = matrixStack.peek().getPositionMatrix();
      VertexConsumer vertexConsumer2 = immediate.getBuffer(renderLayer2);
      invoke14(vertexConsumer2, matrix4f2, intValue7, (int)(255.0F * floatValue22));
      matrixStack.pop();
   }

   private void invoke3(MatrixStack matrixStack, Immediate immediate, LivingEntity livingEntity, float f) {
      Vec3d vec3d7 = livingEntity.getLerpedPos(f);
      double doubleValue7 = vec3d7.x;
      double doubleValue8 = vec3d7.y;
      double doubleValue9 = vec3d7.z;
      Vec3d vec3d8 = CLIENT.gameRenderer.getCamera().getPos();
      matrixStack.push();
      matrixStack.translate(doubleValue7 - vec3d8.x, doubleValue8 - vec3d8.y + livingEntity.getHeight() / 1.75F, doubleValue9 - vec3d8.z);
      matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-CLIENT.gameRenderer.getCamera().getYaw()));
      matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(CLIENT.gameRenderer.getCamera().getPitch()));
      long longValue2 = System.currentTimeMillis();
      float floatValue24 = (float)MathUtils.measure26(0.0, 720.0, (Math.sin(longValue2 / 1600.0) + 1.0) / 2.0 * 360.0 * 2.0);
      matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(floatValue24));
      easedAnimation2.check();
      int intValue8 = livingEntity.hurtTime;
      float floatValue25 = (float)Math.sin(intValue8 * (Math.PI / 20));
      easedAnimation2.animateTo(floatValue25, 0.4F, Easings.EASE_OUT_QUART);
      float floatValue26 = easedAnimation2.measure3();
      float floatValue27 = (float)easedAnimation.getDoubleValue4();
      int intValue9 = ColorUtils.compute43(200, 70, 70, (int)(255.0F * floatValue27));
      int intValue10 = ColorUtils.compute35(ColorUtils.compute31(ColorUtils.compute41(), floatValue27), intValue9, easedAnimation2.measure3());
      float floatValue28 = 1.5F - 0.9F * floatValue27 + (0.35F - 0.35F * floatValue26);
      matrixStack.scale(floatValue28, floatValue28, 1.0F);
      RenderLayer renderLayer3 = RENDER_LAYER_2;
      Matrix4f matrix4f3 = matrixStack.peek().getPositionMatrix();
      VertexConsumer vertexConsumer3 = immediate.getBuffer(renderLayer3);
      invoke14(vertexConsumer3, matrix4f3, intValue10, (int)(255.0F * floatValue27));
      matrixStack.pop();
   }

   private void invoke4(MatrixStack matrixStack, Immediate immediate, LivingEntity livingEntity, float f) {
      Vec3d vec3d9 = livingEntity.getLerpedPos(f);
      double doubleValue10 = vec3d9.x;
      double doubleValue11 = vec3d9.y;
      double doubleValue12 = vec3d9.z;
      Vec3d vec3d10 = CLIENT.gameRenderer.getCamera().getPos();
      matrixStack.push();
      matrixStack.translate(doubleValue10 - vec3d10.x, doubleValue11 - vec3d10.y + livingEntity.getHeight() / 1.75F, doubleValue12 - vec3d10.z);
      matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-CLIENT.gameRenderer.getCamera().getYaw()));
      matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(CLIENT.gameRenderer.getCamera().getPitch()));
      long longValue3 = System.currentTimeMillis();
      float floatValue29 = (float)MathUtils.measure26(0.0, 720.0, (Math.sin(longValue3 / 1000.0) + 1.0) / 2.0 * 360.0 * 2.0);
      matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(floatValue29));
      easedAnimation2.check();
      int intValue11 = livingEntity.hurtTime;
      float floatValue30 = (float)Math.sin(intValue11 * (Math.PI / 20));
      easedAnimation2.animateTo(floatValue30, 0.4F, Easings.EASE_OUT_QUART);
      float floatValue31 = easedAnimation2.measure3();
      float floatValue32 = (float)easedAnimation.getDoubleValue4();
      int intValue12 = ColorUtils.compute43(200, 70, 70, (int)(255.0F * floatValue32));
      int intValue13 = ColorUtils.compute35(ColorUtils.compute31(ColorUtils.compute41(), floatValue32), intValue12, easedAnimation2.measure3());
      float floatValue33 = 1.25F - 0.6F * floatValue32 + (0.35F - 0.35F * floatValue31);
      matrixStack.scale(floatValue33, floatValue33, 1.0F);
      RenderLayer renderLayer4 = RENDER_LAYER_3;
      Matrix4f matrix4f4 = matrixStack.peek().getPositionMatrix();
      VertexConsumer vertexConsumer4 = immediate.getBuffer(renderLayer4);
      invoke14(vertexConsumer4, matrix4f4, intValue13, (int)(255.0F * floatValue32));
      matrixStack.pop();
   }

   private void invoke5(MatrixStack matrixStack, Immediate immediate, LivingEntity livingEntity, float f) {
      if (livingEntity != null) {
         Vec3d vec3d11 = CLIENT.gameRenderer.getCamera().getPos();
         double doubleValue13 = livingEntity.lastRenderX + (livingEntity.getX() - livingEntity.lastRenderX) * f;
         double doubleValue14 = livingEntity.lastRenderY + (livingEntity.getY() - livingEntity.lastRenderY) * f;
         double doubleValue15 = livingEntity.lastRenderZ + (livingEntity.getZ() - livingEntity.lastRenderZ) * f;
         matrixStack.push();
         matrixStack.translate(doubleValue13 - vec3d11.x, doubleValue14 - vec3d11.y, doubleValue15 - vec3d11.z);
         float floatValue34 = (float)easedAnimation.getDoubleValue4();
         float floatValue35 = livingEntity.getHeight();
         double doubleValue16 = livingEntity.getWidth() * 1.0F - 0.2F * easedAnimation2.measure3();
         int intValue14 = ColorUtils.compute43(200, 70, 70, (int)(255.0F * floatValue34));
         easedAnimation2.check();
         int intValue15 = livingEntity.hurtTime;
         float floatValue36 = (float)Math.sin(intValue15 * (Math.PI / 20));
         easedAnimation2.animateTo(floatValue36, 0.4F, Easings.EASE_OUT_QUART);
         Matrix4f matrix4f5 = matrixStack.peek().getPositionMatrix();
         double doubleValue17 = 1800.0;
         double doubleValue18 = System.currentTimeMillis() % doubleValue17;
         boolean flag2 = doubleValue18 > doubleValue17 / 2.0;
         double doubleValue19 = doubleValue18 / (doubleValue17 / 2.0);
         doubleValue19 = flag2 ? doubleValue19 - 1.0 : 1.0 - doubleValue19;
         doubleValue19 = doubleValue19 < 0.5 ? 2.0 * doubleValue19 * doubleValue19 : 1.0 - Math.pow(-2.0 * doubleValue19 + 2.0, 2.0) / 2.0;
         double doubleValue20 = floatValue35 / 1.25F * (doubleValue19 > 0.5 ? 1.0 - doubleValue19 : doubleValue19) * (flag2 ? -1 : 1);
         VertexConsumer vertexConsumer5 = immediate.getBuffer(RENDER_LAYER_6);

         for (byte byteValue = 0; byteValue <= 360; byteValue += 5) {
            double doubleValue21 = Math.toRadians(byteValue);
            float floatValue37 = (float)(Math.cos(doubleValue21) * doubleValue16);
            float floatValue38 = (float)(Math.sin(doubleValue21) * doubleValue16);
            int intValue16 = ColorUtils.compute35(
               ColorUtils.compute31(
                  ColorUtils.compute42(
                     ColorUtils.compute33(ColorUtils.compute41(), 0.5F),
                     ColorUtils.compute33(ColorUtils.compute41(), 1.0F),
                     byteValue * 4,
                     1
                  ),
                  floatValue34
               ),
               intValue14,
               easedAnimation2.measure3()
            );
            int intValue17 = intValue16 >> 16 & 0xFF;
            int intValue18 = intValue16 >> 8 & 0xFF;
            int intValue19 = intValue16 & 0xFF;
            vertexConsumer5.vertex(matrix4f5, floatValue37, (float)(floatValue35 * doubleValue19), floatValue38).color(intValue17, intValue18, intValue19, (int)(180.0F * floatValue34));
            vertexConsumer5.vertex(matrix4f5, floatValue37, (float)(floatValue35 * doubleValue19 + doubleValue20), floatValue38).color(intValue17, intValue18, intValue19, 0);
         }

         VertexConsumer vertexConsumer6 = immediate.getBuffer(RENDER_LAYER_7);

         for (byte byteValue2 = 0; byteValue2 <= 360; byteValue2 += 5) {
            double doubleValue22 = Math.toRadians(byteValue2);
            float floatValue39 = (float)(Math.cos(doubleValue22) * doubleValue16);
            float floatValue40 = (float)(Math.sin(doubleValue22) * doubleValue16);
            int intValue20 = ColorUtils.compute35(
               ColorUtils.compute31(
                  ColorUtils.compute42(
                     ColorUtils.compute33(ColorUtils.compute41(), 0.5F),
                     ColorUtils.compute33(ColorUtils.compute41(), 1.0F),
                     byteValue2 * 4,
                     1
                  ),
                  floatValue34
               ),
               intValue14,
               easedAnimation2.measure3()
            );
            vertexConsumer6.vertex(matrix4f5, floatValue39, (float)(floatValue35 * doubleValue19), floatValue40).color(ColorUtils.compute29(intValue20, (int)(255.0F * floatValue34)));
         }

         matrixStack.pop();
      }
   }

   private void invoke6(MatrixStack matrixStack, Immediate immediate, LivingEntity livingEntity, float f) {
      if (livingEntity != null) {
         long longValue4 = System.currentTimeMillis();
         if (this.timestamp2 == 0L) {
            this.timestamp2 = longValue4;
         }

         long longValue5 = longValue4 - this.timestamp2;
         if (longValue5 > 0L) {
            this.floatValue += (float)(5L * longValue5) / 900.0F;
         }

         this.timestamp2 = longValue4;
         Vec3d vec3d12 = livingEntity.getLerpedPos(f);
         Vec3d vec3d13 = CLIENT.gameRenderer.getCamera().getPos();
         double doubleValue23 = vec3d12.x - vec3d13.x;
         double doubleValue24 = vec3d12.y - vec3d13.y;
         double doubleValue25 = vec3d12.z - vec3d13.z;
         float floatValue41 = (float)easedAnimation.getDoubleValue4();
         easedAnimation2.check();
         int intValue21 = livingEntity.hurtTime;
         float floatValue42 = (float)Math.sin(intValue21 * (Math.PI / 20));
         easedAnimation2.animateTo(floatValue42, 0.4F, Easings.EASE_OUT_QUART);
         float floatValue43 = easedAnimation2.measure3();
         int intValue22 = ColorUtils.compute41();
         int intValue23 = ColorUtils.compute43(200, 70, 70, (int)(255.0F * floatValue41));
         int intValue24 = ColorUtils.compute35(ColorUtils.compute31(intValue22, floatValue41), intValue23, floatValue43);
         RenderLayer renderLayer5 = RENDER_LAYER_4;
         byte byteValue3 = 3;
         byte byteValue4 = 12;
         int intValue25 = 3 * byteValue3;
         matrixStack.push();
         Camera camera2 = CLIENT.gameRenderer.getCamera();

         for (byte byteValue5 = 0; byteValue5 < intValue25; byteValue5 += byteValue3) {
            for (int intValue26 = 0; intValue26 < byteValue4; intValue26++) {
               float floatValue44 = this.floatValue + intValue26 * 0.1F;
               float floatValue45 = 0.75F;
               float floatValue46 = 0.5F;
               int intValue27 = (int)Math.pow(byteValue5, 2.0);
               matrixStack.push();
               double doubleValue26 = doubleValue23 + floatValue45 * Math.sin(floatValue44 + intValue27);
               double doubleValue27 = doubleValue24 + floatValue46 + 0.3F * Math.sin(this.floatValue + intValue26 * 0.2F) + 0.2F * byteValue5;
               double doubleValue28 = doubleValue25 + floatValue45 * Math.cos(floatValue44 - intValue27);
               matrixStack.translate(doubleValue26, doubleValue27, doubleValue28);
               float floatValue47 = 0.005F + intValue26 / 2000.0F;
               matrixStack.scale(floatValue47, floatValue47, floatValue47);
               matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-camera2.getYaw()));
               matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera2.getPitch()));
               Matrix4f matrix4f6 = matrixStack.peek().getPositionMatrix();
               VertexConsumer vertexConsumer7 = immediate.getBuffer(renderLayer5);
               int intValue28 = intValue24 >> 16 & 0xFF;
               int intValue29 = intValue24 >> 8 & 0xFF;
               int intValue30 = intValue24 & 0xFF;
               int intValue31 = (int)(floatValue41 * 255.0F);
               byte byteValue6 = -25;
               byte byteValue7 = 50;
               vertexConsumer7.vertex(matrix4f6, byteValue6, byteValue6 + byteValue7, 0.0F)
                  .color(intValue28, intValue29, intValue30, intValue31)
                  .texture(0.0F, 1.0F)
                  .overlay(OverlayTexture.DEFAULT_UV)
                  .light(15728880)
                  .normal(0.0F, 0.0F, 1.0F);
               vertexConsumer7.vertex(matrix4f6, byteValue6 + byteValue7, byteValue6 + byteValue7, 0.0F)
                  .color(intValue28, intValue29, intValue30, intValue31)
                  .texture(1.0F, 1.0F)
                  .overlay(OverlayTexture.DEFAULT_UV)
                  .light(15728880)
                  .normal(0.0F, 0.0F, 1.0F);
               vertexConsumer7.vertex(matrix4f6, byteValue6 + byteValue7, byteValue6, 0.0F)
                  .color(intValue28, intValue29, intValue30, intValue31)
                  .texture(1.0F, 0.0F)
                  .overlay(OverlayTexture.DEFAULT_UV)
                  .light(15728880)
                  .normal(0.0F, 0.0F, 1.0F);
               vertexConsumer7.vertex(matrix4f6, byteValue6, byteValue6, 0.0F)
                  .color(intValue28, intValue29, intValue30, intValue31)
                  .texture(0.0F, 0.0F)
                  .overlay(OverlayTexture.DEFAULT_UV)
                  .light(15728880)
                  .normal(0.0F, 0.0F, 1.0F);
               matrixStack.pop();
            }
         }

         matrixStack.pop();
      }
   }

   private void invoke7(MatrixStack matrixStack, Immediate immediate, LivingEntity livingEntity, float f) {
      if (livingEntity != null) {
         long longValue6 = System.currentTimeMillis();
         if (this.timestamp2 == 0L) {
            this.timestamp2 = longValue6;
         }

         long longValue7 = longValue6 - this.timestamp2;
         if (longValue7 > 0L) {
            this.floatValue += (float)(5L * longValue7) / 200.0F;
         }

         this.timestamp2 = longValue6;
         Vec3d vec3d14 = livingEntity.getLerpedPos(f);
         Vec3d vec3d15 = CLIENT.gameRenderer.getCamera().getPos();
         double doubleValue29 = vec3d14.x - vec3d15.x;
         double doubleValue30 = vec3d14.y + 1.1F - vec3d15.y;
         double doubleValue31 = vec3d14.z - vec3d15.z;
         float floatValue48 = (float)easedAnimation.getDoubleValue4();
         RenderLayer renderLayer6 = RENDER_LAYER_4;
         byte byteValue8 = 17;
         byte byteValue9 = 6;
         float floatValue49 = 1.25F;
         float floatValue50 = 1.1F;
         float floatValue51 = this.floatValue;
         Camera camera3 = CLIENT.gameRenderer.getCamera();
         double doubleValue32 = livingEntity.getWidth() + 0.12F;
         boolean flag3 = CLIENT.player.canSee(livingEntity);
         VertexConsumer vertexConsumer8 = immediate.getBuffer(renderLayer6);
         easedAnimation2.check();
         int intValue32 = livingEntity.hurtTime;
         float floatValue52 = (float)Math.sin(intValue32 * (Math.PI / 20));
         easedAnimation2.animateTo(floatValue52, 0.4F, Easings.EASE_OUT_QUART);
         float floatValue53 = easedAnimation2.measure3();
         int intValue33 = compute(255, floatValue53);

         for (int intValue34 = 0; intValue34 < 3; intValue34++) {
            for (int intValue35 = 0; intValue35 <= byteValue8; intValue35++) {
               double doubleValue33 = Math.toRadians(((intValue35 / 1.5F + floatValue51) * byteValue9 + intValue34 * 120) % (byteValue9 * 360));
               double doubleValue34 = Math.sin(Math.toRadians(floatValue51 * 2.0F + intValue35 * (intValue34 + 1)) * floatValue50) / floatValue49;
               float floatValue54 = (float)intValue35 / byteValue8;
               matrixStack.push();
               matrixStack.translate(doubleValue29 + Math.cos(doubleValue33) * doubleValue32, doubleValue30 + doubleValue34, doubleValue31 + Math.sin(doubleValue33) * doubleValue32);
               matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-camera3.getYaw()));
               matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera3.getPitch()));
               Matrix4f matrix4f7 = matrixStack.peek().getPositionMatrix();
               int intValue36 = compute2(intValue33, (int)(255.0F * floatValue54 * floatValue48));
               int intValue37 = intValue36 >> 16 & 0xFF;
               int intValue38 = intValue36 >> 8 & 0xFF;
               int intValue39 = intValue36 & 0xFF;
               int intValue40 = intValue36 >> 24 & 0xFF;
               float floatValue55 = Math.max(0.25F * floatValue54, 0.22F);
               vertexConsumer8.vertex(matrix4f7, -floatValue55, floatValue55, 0.0F)
                  .color(intValue37, intValue38, intValue39, intValue40)
                  .texture(0.0F, 1.0F)
                  .overlay(OverlayTexture.DEFAULT_UV)
                  .light(15728880)
                  .normal(0.0F, 0.0F, 1.0F);
               vertexConsumer8.vertex(matrix4f7, floatValue55, floatValue55, 0.0F)
                  .color(intValue37, intValue38, intValue39, intValue40)
                  .texture(1.0F, 1.0F)
                  .overlay(OverlayTexture.DEFAULT_UV)
                  .light(15728880)
                  .normal(0.0F, 0.0F, 1.0F);
               vertexConsumer8.vertex(matrix4f7, floatValue55, -floatValue55, 0.0F)
                  .color(intValue37, intValue38, intValue39, intValue40)
                  .texture(1.0F, 0.0F)
                  .overlay(OverlayTexture.DEFAULT_UV)
                  .light(15728880)
                  .normal(0.0F, 0.0F, 1.0F);
               vertexConsumer8.vertex(matrix4f7, -floatValue55, -floatValue55, 0.0F)
                  .color(intValue37, intValue38, intValue39, intValue40)
                  .texture(0.0F, 0.0F)
                  .overlay(OverlayTexture.DEFAULT_UV)
                  .light(15728880)
                  .normal(0.0F, 0.0F, 1.0F);
               matrixStack.pop();
            }
         }
      }
   }

   private static float measure() {
      return (float)(System.currentTimeMillis() % 1000000L) / 1000.0F;
   }

   private float measure2(LivingEntity livingEntity) {
      easedAnimation2.check();
      int intValue41 = livingEntity.hurtTime;
      float floatValue56 = (float)Math.sin(intValue41 * (Math.PI / 20));
      easedAnimation2.animateTo(floatValue56, 0.4F, Easings.EASE_OUT_QUART);
      return easedAnimation2.measure3();
   }

   private void invoke8(
      MatrixStack matrixStack, Immediate immediate, Camera camera, RenderLayer renderLayer, double d, double e, double f, float g, int i, int j
   ) {
      if (j > 0) {
         matrixStack.push();
         matrixStack.translate(d, e, f);
         matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-camera.getYaw()));
         matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
         matrixStack.scale(g, g, g);
         invoke14(immediate.getBuffer(renderLayer), matrixStack.peek().getPositionMatrix(), i, j);
         matrixStack.pop();
      }
   }

   private void invoke9(MatrixStack matrixStack, Immediate immediate, LivingEntity livingEntity, float f) {
      if (livingEntity != null) {
         Camera camera4 = CLIENT.gameRenderer.getCamera();
         Vec3d vec3d16 = camera4.getPos();
         Vec3d vec3d17 = livingEntity.getLerpedPos(f);
         float floatValue57 = (float)easedAnimation.getDoubleValue4();
         float floatValue58 = this.measure2(livingEntity);
         int intValue42 = compute(255, floatValue58) & 16777215;
         double doubleValue35 = vec3d17.x - vec3d16.x;
         double doubleValue36 = vec3d17.z - vec3d16.z;
         double doubleValue37 = vec3d17.y - vec3d16.y + livingEntity.getHeight() * 0.5;
         double doubleValue38 = livingEntity.getWidth() / 2.0 + 0.5;
         double doubleValue39 = livingEntity.getHeight() * 0.18;
         byte byteValue10 = 16;
         float floatValue59 = measure();

         for (int intValue43 = 0; intValue43 < byteValue10; intValue43++) {
            double doubleValue40 = (Math.PI * 2) / byteValue10 * intValue43 + floatValue59 * 1.4;
            double doubleValue41 = doubleValue35 + Math.cos(doubleValue40) * doubleValue38;
            double doubleValue42 = doubleValue36 + Math.sin(doubleValue40) * doubleValue38;
            double doubleValue43 = doubleValue37 + Math.sin(floatValue59 * 2.2 + intValue43 * 0.6) * doubleValue39;
            float floatValue60 = 0.55F + 0.45F * (float)Math.sin(floatValue59 * 2.0 + intValue43);
            int intValue44 = (int)(215.0F * floatValue57 * floatValue60);
            float floatValue61 = 0.3F + 0.06F * (float)Math.sin(floatValue59 * 3.0 + intValue43);
            this.invoke8(matrixStack, immediate, camera4, RENDER_LAYER_4, doubleValue41, doubleValue43, doubleValue42, floatValue61, intValue42, intValue44);
         }
      }
   }

   private void invoke10(MatrixStack matrixStack, Immediate immediate, LivingEntity livingEntity, float f) {
      if (livingEntity != null) {
         Camera camera5 = CLIENT.gameRenderer.getCamera();
         Vec3d vec3d18 = camera5.getPos();
         Vec3d vec3d19 = livingEntity.getLerpedPos(f);
         float floatValue62 = (float)easedAnimation.getDoubleValue4();
         float floatValue63 = this.measure2(livingEntity);
         int intValue45 = compute(255, floatValue63) & 16777215;
         double doubleValue44 = vec3d19.x - vec3d18.x;
         double doubleValue45 = vec3d19.z - vec3d18.z;
         double doubleValue46 = vec3d19.y - vec3d18.y - 0.1;
         double doubleValue47 = livingEntity.getWidth() / 2.0 + 0.32;
         double doubleValue48 = livingEntity.getHeight() + 0.2;
         double doubleValue49 = 2.5;
         byte byteValue11 = 18;
         float floatValue64 = measure();

         for (int intValue46 = 0; intValue46 < 2; intValue46++) {
            for (int intValue47 = 0; intValue47 <= byteValue11; intValue47++) {
               double doubleValue50 = (double)intValue47 / byteValue11;
               double doubleValue51 = doubleValue50 * doubleValue49 * Math.PI * 2.0 + floatValue64 * 2.0 + intValue46 * Math.PI;
               double doubleValue52 = doubleValue44 + Math.cos(doubleValue51) * doubleValue47;
               double doubleValue53 = doubleValue45 + Math.sin(doubleValue51) * doubleValue47;
               double doubleValue54 = doubleValue46 + doubleValue50 * doubleValue48;
               int intValue48 = (int)(220.0F * floatValue62 * (0.3 + 0.7 * Math.sin(doubleValue50 * Math.PI)));
               this.invoke8(matrixStack, immediate, camera5, RENDER_LAYER_4, doubleValue52, doubleValue54, doubleValue53, 0.24F, intValue45, intValue48);
            }
         }
      }
   }

   private void invoke11(MatrixStack matrixStack, Immediate immediate, LivingEntity livingEntity, float f) {
      if (livingEntity != null) {
         Camera camera6 = CLIENT.gameRenderer.getCamera();
         Vec3d vec3d20 = camera6.getPos();
         Vec3d vec3d21 = livingEntity.getLerpedPos(f);
         float floatValue65 = (float)easedAnimation.getDoubleValue4();
         float floatValue66 = this.measure2(livingEntity);
         int intValue49 = compute(255, floatValue66) & 16777215;
         double doubleValue55 = vec3d21.x - vec3d20.x;
         double doubleValue56 = vec3d21.z - vec3d20.z;
         double doubleValue57 = vec3d21.y - vec3d20.y + livingEntity.getHeight() * 0.5;
         double doubleValue58 = livingEntity.getWidth() / 2.0 + 0.55;
         byte byteValue12 = 14;
         float floatValue67 = measure();

         for (int intValue50 = 0; intValue50 < byteValue12; intValue50++) {
            double doubleValue59 = (Math.PI * 2) / byteValue12 * intValue50 + floatValue67 * 1.1;
            double doubleValue60 = doubleValue55 + Math.cos(doubleValue59) * doubleValue58;
            double doubleValue61 = doubleValue56 + Math.sin(doubleValue59) * doubleValue58;
            double doubleValue62 = doubleValue57 + Math.sin(floatValue67 * 2.0 + intValue50) * 0.12;
            matrixStack.push();
            matrixStack.translate(doubleValue60, doubleValue62, doubleValue61);
            matrixStack.push();
            float floatValue68 = (floatValue67 * 50.0F + intValue50 * 28.0F) % 360.0F;
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(floatValue68));
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(floatValue68 * 0.7F));
            Matrix4f matrix4f8 = matrixStack.peek().getPositionMatrix();
            float floatValue69 = 0.16F + 0.02F * (float)Math.sin(floatValue67 * 3.0 + intValue50);
            EspBoxVertexWriter.invoke4(immediate.getBuffer(RENDER_LAYER_10), matrix4f8, compute2(intValue49, (int)(70.0F * floatValue65)), floatValue69);
            EspBoxVertexWriter.invoke5(immediate.getBuffer(RENDER_LAYER_11), matrix4f8, compute2(intValue49, (int)(230.0F * floatValue65)), floatValue69);
            matrixStack.pop();
            matrixStack.push();
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-camera6.getYaw()));
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera6.getPitch()));
            float floatValue70 = floatValue69 * 2.4F;
            matrixStack.scale(floatValue70, floatValue70, floatValue70);
            invoke14(immediate.getBuffer(RENDER_LAYER_5), matrixStack.peek().getPositionMatrix(), intValue49, (int)(60.0F * floatValue65));
            matrixStack.pop();
            matrixStack.pop();
         }
      }
   }

   private void invoke12(MatrixStack matrixStack, Immediate immediate, LivingEntity livingEntity, float f) {
      if (livingEntity != null) {
         Camera camera7 = CLIENT.gameRenderer.getCamera();
         Vec3d vec3d22 = camera7.getPos();
         Vec3d vec3d23 = livingEntity.getLerpedPos(f);
         float floatValue71 = (float)easedAnimation.getDoubleValue4();
         float floatValue72 = this.measure2(livingEntity);
         int intValue51 = compute2(compute(255, floatValue72) & 16777215, (int)(220.0F * floatValue71));
         double doubleValue63 = vec3d23.x - vec3d22.x;
         double doubleValue64 = vec3d23.y - vec3d22.y + livingEntity.getHeight() * 0.5;
         double doubleValue65 = vec3d23.z - vec3d22.z;
         float floatValue73 = (float)(Math.max((double)livingEntity.getWidth(), livingEntity.getHeight() * 0.5) * 0.72 + 0.3 + floatValue72 * 0.2);
         float floatValue74 = measure();
         matrixStack.push();
         matrixStack.translate(doubleValue63, doubleValue64, doubleValue65);
         matrixStack.push();
         matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(floatValue74 * 38.0F));
         invoke13(immediate.getBuffer(RENDER_LAYER_11), matrixStack.peek().getPositionMatrix(), floatValue73, 40, intValue51);
         matrixStack.pop();
         matrixStack.push();
         matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(floatValue74 * 30.0F));
         matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(90.0F));
         invoke13(immediate.getBuffer(RENDER_LAYER_11), matrixStack.peek().getPositionMatrix(), floatValue73, 40, intValue51);
         matrixStack.pop();
         matrixStack.push();
         matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(floatValue74 * 26.0F + 90.0F));
         matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(90.0F));
         invoke13(immediate.getBuffer(RENDER_LAYER_11), matrixStack.peek().getPositionMatrix(), floatValue73, 40, intValue51);
         matrixStack.pop();
         matrixStack.pop();
      }
   }

   private static void invoke13(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, int i, int j) {
      int intValue52 = j >> 16 & 0xFF;
      int intValue53 = j >> 8 & 0xFF;
      int intValue54 = j & 0xFF;
      int intValue55 = j >>> 24 & 0xFF;

      for (int intValue56 = 0; intValue56 < i; intValue56++) {
         double doubleValue66 = (Math.PI * 2) / i * intValue56;
         double doubleValue67 = (Math.PI * 2) / i * (intValue56 + 1);
         vertexConsumer.vertex(matrix4f, (float)(Math.cos(doubleValue66) * f), 0.0F, (float)(Math.sin(doubleValue66) * f)).color(intValue52, intValue53, intValue54, intValue55);
         vertexConsumer.vertex(matrix4f, (float)(Math.cos(doubleValue67) * f), 0.0F, (float)(Math.sin(doubleValue67) * f)).color(intValue52, intValue53, intValue54, intValue55);
      }
   }

   static void invoke14(VertexConsumer vertexConsumer, Matrix4f matrix4f, int i, int j) {
      int intValue57 = i >> 16 & 0xFF;
      int intValue58 = i >> 8 & 0xFF;
      int intValue59 = i & 0xFF;
      vertexConsumer.vertex(matrix4f, -0.5F, -0.5F, 0.0F)
         .color(intValue57, intValue58, intValue59, j)
         .texture(0.0F, 1.0F)
         .overlay(OverlayTexture.DEFAULT_UV)
         .light(15728880)
         .normal(0.0F, 0.0F, 1.0F);
      vertexConsumer.vertex(matrix4f, 0.5F, -0.5F, 0.0F)
         .color(intValue57, intValue58, intValue59, j)
         .texture(1.0F, 1.0F)
         .overlay(OverlayTexture.DEFAULT_UV)
         .light(15728880)
         .normal(0.0F, 0.0F, 1.0F);
      vertexConsumer.vertex(matrix4f, 0.5F, 0.5F, 0.0F)
         .color(intValue57, intValue58, intValue59, j)
         .texture(1.0F, 0.0F)
         .overlay(OverlayTexture.DEFAULT_UV)
         .light(15728880)
         .normal(0.0F, 0.0F, 1.0F);
      vertexConsumer.vertex(matrix4f, -0.5F, 0.5F, 0.0F)
         .color(intValue57, intValue58, intValue59, j)
         .texture(0.0F, 0.0F)
         .overlay(OverlayTexture.DEFAULT_UV)
         .light(15728880)
         .normal(0.0F, 0.0F, 1.0F);
   }

   private static RenderLayer resolve5(Identifier identifier, RenderPipeline renderPipeline) {
      return RenderLayer.of(
         identifier.toString(), 1024, false, true, renderPipeline, MultiPhaseParameters.builder().texture(new Texture(identifier, false)).build(false)
      );
   }

   private static int compute(int i, float f) {
      int intValue60 = 6061311;

      try {
         Theme theme2 = WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null ? WildClient.INSTANCE.themeManager.getTheme() : Theme.WILD;
         if (theme2 == Theme.CUSTOM && WildClient.INSTANCE.themeManager.customThemeColor != null) {
            intValue60 = WildClient.INSTANCE.themeManager.customThemeColor.compute() & 16777215;
         } else if (theme2 != null && theme2.getColor() != null) {
            intValue60 = theme2.getColor().getRGB() & 16777215;
         }
      } catch (Throwable exception) {
      }

      float floatValue75 = f < 0.0F ? 0.0F : Math.min(f, 1.0F);
      int intValue61 = intValue60 >> 16 & 0xFF;
      int intValue62 = intValue60 >> 8 & 0xFF;
      int intValue63 = intValue60 & 0xFF;
      int intValue64 = Math.round(intValue61 + (235 - intValue61) * floatValue75);
      int intValue65 = Math.round(intValue62 + (70 - intValue62) * floatValue75);
      int intValue66 = Math.round(intValue63 + (70 - intValue63) * floatValue75);
      int intValue67 = Math.max(0, Math.min(255, i));
      return intValue67 << 24 | intValue64 << 16 | intValue65 << 8 | intValue66;
   }

   static int compute2(int i, int j) {
      return Math.max(0, Math.min(255, j)) << 24 | i & 16777215;
   }

   private void invoke15(MatrixStack matrixStack, Immediate immediate, LivingEntity livingEntity, float f) {
      MinecraftClient client = MinecraftClient.getInstance();
      if (livingEntity != null) {
         double doubleValue68 = 0.3 + livingEntity.getWidth() / 2.0F;
         easedAnimation2.check();
         int intValue68 = livingEntity.hurtTime;
         float floatValue76 = (float)Math.sin(intValue68 * (Math.PI / 20));
         easedAnimation2.animateTo(floatValue76, 0.4F, Easings.EASE_OUT_QUART);
         float floatValue77 = easedAnimation2.measure3();
         float floatValue78 = 30.0F;
         float floatValue79 = 0.4F - 0.1F * floatValue77;
         double doubleValue69 = 6 - (int)(1.0F * floatValue77);
         int intValue69 = 40 - (int)(12.0F * floatValue77);
         Vec3d vec3d24 = client.gameRenderer.getCamera().getPos();
         Camera camera8 = client.gameRenderer.getCamera();
         if (timestamp == 0L) {
            timestamp = System.currentTimeMillis();
         }

         long longValue8 = System.currentTimeMillis();
         Vec3d vec3d25 = livingEntity.getLerpedPos(f);
         vec3d25 = new Vec3d(vec3d25.x, vec3d25.y + 0.32 + livingEntity.getHeight() / 2.0F, vec3d25.z);
         double doubleValue70 = vec3d25.x + 0.2;
         double doubleValue71 = vec3d25.y;
         double doubleValue72 = vec3d25.z;
         RenderLayer renderLayer7 = RENDER_LAYER_4;
         VertexConsumer vertexConsumer9 = immediate.getBuffer(renderLayer7);
         float floatValue80 = (float)easedAnimation.getDoubleValue4();
         int intValue70 = compute((int)(255.0F * floatValue80), floatValue77);
         int intValue71 = intValue70;
         int intValue72 = compute2(intValue70, (int)(210.0F * floatValue80));
         int intValue73 = compute2(intValue70, (int)(150.0F * floatValue80));
         int intValue74 = compute2(intValue70, (int)(90.0F * floatValue80));
         matrixStack.push();
         matrixStack.translate(doubleValue70 - vec3d24.x, doubleValue71 - vec3d24.y, doubleValue72 - vec3d24.z);
         float floatValue81 = 0.3F;

         for (int intValue75 = 0; intValue75 < intValue69; intValue75++) {
            double doubleValue73 = 0.05F * (longValue8 - timestamp - intValue75 * doubleValue69) / floatValue78;
            double doubleValue74 = Math.sin(doubleValue73 * Math.PI) * doubleValue68;
            double doubleValue75 = Math.cos(doubleValue73 * Math.PI) * doubleValue68;
            double doubleValue76 = Math.cos(doubleValue73 * Math.PI) * doubleValue68;
            float floatValue82 = (float)intValue75 / (intValue69 - 1);
            float floatValue83 = 1.0F - floatValue82 * floatValue81;
            float floatValue84 = floatValue79 * floatValue83;
            matrixStack.push();
            matrixStack.translate(doubleValue74, doubleValue76, -doubleValue75);
            matrixStack.translate(-floatValue84 / 2.0F, -floatValue84 / 2.0F, 0.0F);
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-camera8.getYaw()));
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera8.getPitch()));
            matrixStack.translate(floatValue84 / 2.0F, floatValue84 / 2.0F, 0.0F);
            Matrix4f matrix4f9 = matrixStack.peek().getPositionMatrix();
            this.invoke16(vertexConsumer9, matrix4f9, intValue71, intValue72, intValue73, intValue74, floatValue84);
            matrixStack.pop();
         }

         for (int intValue76 = 0; intValue76 < intValue69; intValue76++) {
            double doubleValue77 = 0.05F * (longValue8 - timestamp - intValue76 * doubleValue69) / floatValue78;
            double doubleValue78 = Math.sin(doubleValue77 * Math.PI) * doubleValue68;
            double doubleValue79 = Math.cos(doubleValue77 * Math.PI) * doubleValue68;
            double doubleValue80 = Math.sin(doubleValue77 * Math.PI) * doubleValue68;
            float floatValue85 = (float)intValue76 / (intValue69 - 1);
            float floatValue86 = 1.0F - floatValue85 * floatValue81;
            float floatValue87 = floatValue79 * floatValue86;
            matrixStack.push();
            matrixStack.translate(-doubleValue78, doubleValue80, -doubleValue79);
            matrixStack.translate(-floatValue87 / 2.0F, -floatValue87 / 2.0F, 0.0F);
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-camera8.getYaw()));
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera8.getPitch()));
            matrixStack.translate(floatValue87 / 2.0F, floatValue87 / 2.0F, 0.0F);
            Matrix4f matrix4f10 = matrixStack.peek().getPositionMatrix();
            this.invoke16(vertexConsumer9, matrix4f10, intValue71, intValue72, intValue73, intValue74, floatValue87);
            matrixStack.pop();
         }

         for (int intValue77 = 0; intValue77 < intValue69; intValue77++) {
            double doubleValue81 = 0.05F * (longValue8 - timestamp - intValue77 * doubleValue69) / floatValue78;
            double doubleValue82 = Math.sin(doubleValue81 * Math.PI) * doubleValue68;
            double doubleValue83 = Math.cos(doubleValue81 * Math.PI) * doubleValue68;
            double doubleValue84 = Math.sin(doubleValue81 * Math.PI) * doubleValue68;
            float floatValue88 = (float)intValue77 / (intValue69 - 1);
            float floatValue89 = 1.0F - floatValue88 * floatValue81;
            float floatValue90 = floatValue79 * floatValue89;
            matrixStack.push();
            matrixStack.translate(doubleValue82, doubleValue84, doubleValue83);
            matrixStack.translate(-floatValue90 / 2.0F, -floatValue90 / 2.0F, 0.0F);
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-camera8.getYaw()));
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera8.getPitch()));
            matrixStack.translate(floatValue90 / 2.0F, floatValue90 / 2.0F, 0.0F);
            Matrix4f matrix4f11 = matrixStack.peek().getPositionMatrix();
            this.invoke16(vertexConsumer9, matrix4f11, intValue71, intValue72, intValue73, intValue74, floatValue90);
            matrixStack.pop();
         }

         matrixStack.pop();
      }
   }

   private void invoke16(VertexConsumer vertexConsumer, Matrix4f matrix4f, int i, int j, int k, int l, float f) {
      int intValue78 = i >> 16 & 0xFF;
      int intValue79 = i >> 8 & 0xFF;
      int intValue80 = i & 0xFF;
      int intValue81 = i >> 24 & 0xFF;
      int intValue82 = j >> 16 & 0xFF;
      int intValue83 = j >> 8 & 0xFF;
      int intValue84 = j & 0xFF;
      int intValue85 = j >> 24 & 0xFF;
      int intValue86 = k >> 16 & 0xFF;
      int intValue87 = k >> 8 & 0xFF;
      int intValue88 = k & 0xFF;
      int intValue89 = k >> 24 & 0xFF;
      int intValue90 = l >> 16 & 0xFF;
      int intValue91 = l >> 8 & 0xFF;
      int intValue92 = l & 0xFF;
      int intValue93 = l >> 24 & 0xFF;
      vertexConsumer.vertex(matrix4f, 0.0F, -f, 0.0F).texture(0.0F, 0.0F).color(intValue78, intValue79, intValue80, intValue81);
      vertexConsumer.vertex(matrix4f, -f, -f, 0.0F).texture(0.0F, 1.0F).color(intValue82, intValue83, intValue84, intValue85);
      vertexConsumer.vertex(matrix4f, -f, 0.0F, 0.0F).texture(1.0F, 1.0F).color(intValue86, intValue87, intValue88, intValue89);
      vertexConsumer.vertex(matrix4f, 0.0F, 0.0F, 0.0F).texture(1.0F, 0.0F).color(intValue90, intValue91, intValue92, intValue93);
   }

   private void invoke17(MatrixStack matrixStack, Immediate immediate, LivingEntity livingEntity, float f) {
      if (livingEntity != null) {
         Vec3d vec3d26 = CLIENT.gameRenderer.getCamera().getPos();
         long longValue9 = System.currentTimeMillis();
         byte byteValue13 = 24;
         double doubleValue85 = 0.4 + livingEntity.getWidth() / 2.0F + 0.35F - 0.35F * easedAnimation.measure3();
         double doubleValue86 = livingEntity.getHeight();
         Vec3d vec3d27 = livingEntity.getLerpedPos(f);
         float floatValue91 = (float)easedAnimation.getDoubleValue4();
         easedAnimation2.check();
         int intValue94 = livingEntity.hurtTime;
         float floatValue92 = (float)Math.sin(intValue94 * (Math.PI / 20));
         easedAnimation2.animateTo(floatValue92, 0.4F, Easings.EASE_OUT_QUART);
         float floatValue93 = easedAnimation2.measure3();
         int intValue95 = compute(Math.round(70.0F * floatValue91), floatValue93);
         int intValue96 = compute(Math.round(225.0F * floatValue91), floatValue93);
         int intValue97 = compute(255, floatValue93);

         for (int intValue98 = 0; intValue98 < byteValue13; intValue98++) {
            double doubleValue87 = Math.sin(intValue98 * 132.12 + 4.12);
            double doubleValue88 = Math.cos(intValue98 * 453.21 + 1.23);
            double doubleValue89 = Math.sin(intValue98 * 789.34 + 9.87);
            double doubleValue90 = 1.0;
            double doubleValue91 = (Math.PI * 2) / byteValue13 * intValue98;
            double doubleValue92 = longValue9 / 6000.0 * (Math.PI * 2) * doubleValue90;
            double doubleValue93 = doubleValue92 + doubleValue91;
            double doubleValue94 = Math.cos(doubleValue93) * doubleValue85;
            double doubleValue95 = Math.sin(doubleValue93) * doubleValue85;
            double doubleValue96 = 1.0 + doubleValue87 * 0.2;
            double doubleValue97 = doubleValue91 + doubleValue89 * 2.0;
            double doubleValue98 = Math.sin(longValue9 / 9000.0 * (Math.PI * 2) * doubleValue96 + doubleValue97) * 0.45 + 0.55;
            double doubleValue99 = doubleValue98 * doubleValue86;
            double doubleValue100 = vec3d27.x + doubleValue94 - vec3d26.x;
            double doubleValue101 = vec3d27.y + doubleValue99 - vec3d26.y;
            double doubleValue102 = vec3d27.z + doubleValue95 - vec3d26.z;
            matrixStack.push();
            matrixStack.translate(doubleValue100, doubleValue101, doubleValue102);
            float floatValue94 = 1.0F + 0.15F * (float)Math.sin(longValue9 / 400.0 + intValue98 * 1.5);
            float floatValue95 = 0.19F * floatValue94;
            double doubleValue103 = floatValue93 * (0.5 + 0.5 * Math.sin(intValue98 * 123.45));
            if (doubleValue103 > 0.05) {
               floatValue95 = (float)(floatValue95 * (1.0 - doubleValue103 * 0.2));
               double doubleValue104 = doubleValue103 * 0.4;
               matrixStack.translate(Math.cos(doubleValue93) * doubleValue104, 0.0, Math.sin(doubleValue93) * doubleValue104);
            }

            matrixStack.push();
            float floatValue96 = 12000.0F + (float)doubleValue89 * 2000.0F;
            float floatValue97 = (float)(longValue9 % (long)Math.abs(floatValue96)) / Math.abs(floatValue96) * 360.0F;
            if (intValue98 % 3 == 0) {
               matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(floatValue97));
               matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(floatValue97));
            } else if (intValue98 % 3 == 1) {
               matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(floatValue97));
               matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(floatValue97));
            } else {
               matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(floatValue97));
               matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(floatValue97));
            }

            VertexConsumer vertexConsumer10 = immediate.getBuffer(RENDER_LAYER_10);
            Matrix4f matrix4f12 = matrixStack.peek().getPositionMatrix();
            EspBoxVertexWriter.invoke4(vertexConsumer10, matrix4f12, intValue95, floatValue95);
            VertexConsumer vertexConsumer11 = immediate.getBuffer(RENDER_LAYER_11);
            EspBoxVertexWriter.invoke5(vertexConsumer11, matrix4f12, intValue96, floatValue95);
            matrixStack.pop();
            matrixStack.push();
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-CLIENT.gameRenderer.getCamera().getYaw()));
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(CLIENT.gameRenderer.getCamera().getPitch()));
            VertexConsumer vertexConsumer12 = immediate.getBuffer(RENDER_LAYER_5);
            Matrix4f matrix4f13 = matrixStack.peek().getPositionMatrix();
            float floatValue98 = floatValue95 * 2.0F;
            matrixStack.scale(floatValue98, floatValue98, floatValue98);
            invoke14(vertexConsumer12, matrix4f13, intValue97, (int)(70.0F * floatValue91));
            matrixStack.pop();
            matrixStack.pop();
         }
      }
   }

   private void invoke19(MatrixStack matrixStack, Immediate immediate, LivingEntity livingEntity, float f) {
      if (livingEntity == null) {
         return;
      }

      Camera camera = CLIENT.gameRenderer.getCamera();
      Vec3d cameraPos = camera.getPos();
      Vec3d targetPos = livingEntity.getLerpedPos(f);
      float alpha = (float)easedAnimation.getDoubleValue4();
      float hurt = this.measure2(livingEntity);
      int themeRgb = compute(255, hurt) & 16777215;
      int crystalFill = compute2(blendCrystalColor(themeRgb, 0.72F), (int)(95.0F * alpha));
      int crystalEdge = compute2(blendCrystalColor(themeRgb, 0.35F), (int)(220.0F * alpha));
      int glowRgb = themeRgb;
      float time = measure();
      boolean chaos = rezhimKristallov.is("Хаос");
      int count = chaos ? 18 : 14;
      double radius = livingEntity.getWidth() / 2.0 + (chaos ? 0.72 : 0.58);
      double bodyY = targetPos.y - cameraPos.y + livingEntity.getHeight() * 0.52;
      double centerX = targetPos.x - cameraPos.x;
      double centerZ = targetPos.z - cameraPos.z;

      matrixStack.push();
      matrixStack.translate(centerX, bodyY, centerZ);
      matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-camera.getYaw()));
      matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
      float auraSize = (float)(livingEntity.getHeight() * 0.95 + 0.35);
      matrixStack.scale(auraSize, auraSize, auraSize);
      invoke14(immediate.getBuffer(RENDER_LAYER_5), matrixStack.peek().getPositionMatrix(), glowRgb, (int)((55.0F + hurt * 70.0F) * alpha));
      matrixStack.pop();

      for (int i = 0; i < count; i++) {
         double spin = time * (chaos ? 1.35 : 0.95) + (Math.PI * 2) * i / count;
         double bob = Math.sin(time * 2.4 + i * 0.85) * (chaos ? 0.28 : 0.18);
         double lift = (i % 3) * 0.16 - 0.12 + Math.sin(time * 1.7 + i) * 0.08;
         double orbitR = radius * (0.85 + 0.18 * Math.sin(time * 1.1 + i * 1.7));
         double x = centerX + Math.cos(spin) * orbitR;
         double z = centerZ + Math.sin(spin) * orbitR;
         double y = bodyY + lift + bob;

         matrixStack.push();
         matrixStack.translate(x, y, z);
         float yaw = (float)(Math.toDegrees(spin) + 90.0 + Math.sin(time * 1.3 + i) * 25.0);
         float pitch = 25.0F + (float)Math.sin(time * 1.8 + i * 0.6) * 35.0F + i * 7.0F;
         float roll = (float)Math.cos(time * 1.5 + i * 0.9) * 40.0F;
         matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(yaw));
         matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(pitch));
         matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(roll));

         float len = 0.42F + 0.08F * (float)Math.sin(time * 2.0 + i);
         float wid = 0.13F + 0.03F * (i % 3);
         float thick = 0.045F + 0.01F * (i % 2);
         if (chaos) {
            len *= 0.85F + (i % 4) * 0.08F;
            wid *= 0.9F + (i % 3) * 0.1F;
         }

         Matrix4f matrix = matrixStack.peek().getPositionMatrix();
         invoke20(immediate.getBuffer(RENDER_LAYER_10), matrix, len, wid, thick, crystalFill);
         invoke21(immediate.getBuffer(RENDER_LAYER_11), matrix, len, wid, thick, crystalEdge);

         matrixStack.push();
         matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-camera.getYaw()));
         matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera.getPitch()));
         float glowScale = len * 1.55F;
         matrixStack.scale(glowScale, glowScale, glowScale);
         invoke14(immediate.getBuffer(RENDER_LAYER_5), matrixStack.peek().getPositionMatrix(), glowRgb, (int)(48.0F * alpha));
         matrixStack.pop();
         matrixStack.pop();
      }
   }

   private static int blendCrystalColor(int rgb, float whiteMix) {
      float mix = Math.max(0.0F, Math.min(1.0F, whiteMix));
      int r = rgb >> 16 & 0xFF;
      int g = rgb >> 8 & 0xFF;
      int b = rgb & 0xFF;
      int outR = Math.round(r + (245 - r) * mix);
      int outG = Math.round(g + (230 - g) * mix * 0.92F);
      int outB = Math.round(b + (240 - b) * mix * 0.95F);
      return outR << 16 | outG << 8 | outB;
   }

   private static void invoke20(VertexConsumer vertexConsumer, Matrix4f matrix4f, float length, float width, float thickness, int argb) {
      float h = length * 0.5F;
      float w = width * 0.5F;
      float t = thickness * 0.5F;
      float[][] points = new float[][]{{0.0F, h, 0.0F}, {0.0F, -h, 0.0F}, {w, 0.0F, 0.0F}, {-w, 0.0F, 0.0F}, {0.0F, 0.0F, t}, {0.0F, 0.0F, -t}};
      int[][] faces = new int[][]{{0, 2, 4}, {0, 4, 3}, {0, 3, 5}, {0, 5, 2}, {1, 4, 2}, {1, 3, 4}, {1, 5, 3}, {1, 2, 5}};
      int r = argb >> 16 & 0xFF;
      int g = argb >> 8 & 0xFF;
      int b = argb & 0xFF;
      int a = argb >>> 24 & 0xFF;

      for (int[] face : faces) {
         float[] p0 = points[face[0]];
         float[] p1 = points[face[1]];
         float[] p2 = points[face[2]];
         vertexConsumer.vertex(matrix4f, p0[0], p0[1], p0[2]).color(r, g, b, a);
         vertexConsumer.vertex(matrix4f, p1[0], p1[1], p1[2]).color(r, g, b, a);
         vertexConsumer.vertex(matrix4f, p2[0], p2[1], p2[2]).color(r, g, b, a);
         vertexConsumer.vertex(matrix4f, p2[0], p2[1], p2[2]).color(r, g, b, a);
      }
   }

   private static void invoke21(VertexConsumer vertexConsumer, Matrix4f matrix4f, float length, float width, float thickness, int argb) {
      float h = length * 0.5F;
      float w = width * 0.5F;
      float t = thickness * 0.5F;
      float[][] points = new float[][]{{0.0F, h, 0.0F}, {0.0F, -h, 0.0F}, {w, 0.0F, 0.0F}, {-w, 0.0F, 0.0F}, {0.0F, 0.0F, t}, {0.0F, 0.0F, -t}};
      int[][] edges = new int[][]{{0, 2}, {0, 3}, {0, 4}, {0, 5}, {1, 2}, {1, 3}, {1, 4}, {1, 5}, {2, 4}, {4, 3}, {3, 5}, {5, 2}};
      int r = argb >> 16 & 0xFF;
      int g = argb >> 8 & 0xFF;
      int b = argb & 0xFF;
      int a = argb >>> 24 & 0xFF;

      for (int[] edge : edges) {
         float[] p0 = points[edge[0]];
         float[] p1 = points[edge[1]];
         vertexConsumer.vertex(matrix4f, p0[0], p0[1], p0[2]).color(r, g, b, a);
         vertexConsumer.vertex(matrix4f, p1[0], p1[1], p1[2]).color(r, g, b, a);
      }
   }

   private void invoke22(MatrixStack matrixStack, Immediate immediate, LivingEntity livingEntity, float f) {
      if (livingEntity == null) {
         return;
      }

      float anim = (float)easedAnimation.getDoubleValue4();
      if (anim <= 0.001F) {
         return;
      }

      Vec3d renderPos = livingEntity.getLerpedPos(f);
      Vec3d cameraPos = CLIENT.gameRenderer.getCamera().getPos();
      Vec3d targetCenter = livingEntity.getPos().add(0.0, livingEntity.getHeight() / 2.0, 0.0);
      float ringWidth = livingEntity.getWidth() * 1.5F;
      float ringScale = 1.25F - 0.5F * anim;
      float moving = (System.currentTimeMillis() % 360000L) / 2.5F + anim;
      float hurt = this.measure2(livingEntity);
      int themeRgb = compute(255, hurt) & 16777215;
      int fill = compute2(themeRgb, (int)(255.0F * anim));

      for (int i = 0; i < 360; i += 20) {
         float angle = (float)Math.toRadians(i + moving * 0.3F);
         float offsetX = (float)Math.sin(angle) * ringWidth * ringScale;
         float offsetZ = (float)Math.cos(angle) * ringWidth * ringScale;
         float offsetY = 0.1F + livingEntity.getHeight() * Math.abs((float)Math.sin(i));
         Vec3d crystalPos = renderPos.add(offsetX, offsetY, offsetZ);
         Vec3d look = targetCenter.subtract(crystalPos);
         if (look.lengthSquared() < 1.0E-6) {
            look = new Vec3d(0.0, 1.0, 0.0);
         } else {
            look = look.normalize();
         }

         matrixStack.push();
         matrixStack.translate(crystalPos.x - cameraPos.x, crystalPos.y - cameraPos.y, crystalPos.z - cameraPos.z);
         matrixStack.multiply(new Quaternionf().rotationTo(new Vector3f(0.0F, 1.0F, 0.0F), new Vector3f((float)look.x, (float)look.y, (float)look.z)));
         matrixStack.scale(0.1F, 0.1F, 0.1F);
         invoke24(immediate.getBuffer(RENDER_LAYER_10), matrixStack.peek().getPositionMatrix(), fill);
         matrixStack.pop();

         matrixStack.push();
         matrixStack.translate(crystalPos.x - cameraPos.x, crystalPos.y - cameraPos.y, crystalPos.z - cameraPos.z);
         matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-CLIENT.gameRenderer.getCamera().getYaw()));
         matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(CLIENT.gameRenderer.getCamera().getPitch()));
         float bloom = 1.5F * anim;
         matrixStack.scale(bloom, bloom, bloom);
         invoke14(immediate.getBuffer(RENDER_LAYER_5), matrixStack.peek().getPositionMatrix(), themeRgb, (int)(255.0F * anim));
         matrixStack.pop();

         matrixStack.push();
         matrixStack.translate(crystalPos.x - cameraPos.x, crystalPos.y - cameraPos.y, crystalPos.z - cameraPos.z);
         matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-CLIENT.gameRenderer.getCamera().getYaw()));
         matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(CLIENT.gameRenderer.getCamera().getPitch()));
         float bloomInner = 0.6F * anim;
         matrixStack.scale(bloomInner, bloomInner, bloomInner);
         invoke14(immediate.getBuffer(RENDER_LAYER_5), matrixStack.peek().getPositionMatrix(), themeRgb, (int)(51.0F * anim));
         matrixStack.pop();
      }
   }

   private void invoke23(MatrixStack matrixStack, Immediate immediate, LivingEntity livingEntity, float f) {
      if (livingEntity == null) {
         return;
      }

      float anim = (float)easedAnimation.getDoubleValue4();
      if (anim <= 0.001F) {
         return;
      }

      Vec3d renderPos = livingEntity.getLerpedPos(f);
      Vec3d cameraPos = CLIENT.gameRenderer.getCamera().getPos();
      float height = livingEntity.getHeight() + 0.15F;
      float radius = livingEntity.getWidth() * 0.8F;
      double time = System.currentTimeMillis() % 1750.0;
      boolean inverted = time > 875.0;
      double progress = time / 875.0;
      double progress2 = inverted ? progress - 1.0 : 1.0 - progress;
      double ease = progress2 < 0.5 ? 2.0 * progress2 * progress2 : 1.0 - Math.pow(-2.0 * progress2 + 2.0, 2.0) / 2.0;
      float y = (float)(height * ease);
      float offset = (float)(height * 0.8 * Math.min(ease, 1.0 - ease) * (inverted ? -1.0 : 1.0));
      float hurt = this.measure2(livingEntity);
      int color = compute2(compute(255, hurt) & 16777215, (int)(255.0F * anim));
      int red = color >> 16 & 0xFF;
      int green = color >> 8 & 0xFF;
      int blue = color & 0xFF;
      int alpha = (int)((color >>> 24 & 0xFF) * anim);

      matrixStack.push();
      matrixStack.translate(renderPos.x - cameraPos.x, renderPos.y - cameraPos.y, renderPos.z - cameraPos.z);
      Matrix4f matrix = matrixStack.peek().getPositionMatrix();
      VertexConsumer skirt = immediate.getBuffer(RENDER_LAYER_6);
      for (int deg = 0; deg <= 360; deg++) {
         double rad = Math.toRadians(deg);
         float x = (float)(Math.cos(rad) * radius);
         float z = (float)(Math.sin(rad) * radius);
         skirt.vertex(matrix, x, y, z).color(red, green, blue, (int)(alpha * 0.55F));
         skirt.vertex(matrix, x, y + offset, z).color(red, green, blue, 0);
      }

      VertexConsumer outline = immediate.getBuffer(RENDER_LAYER_7);
      for (int deg = 0; deg < 360; deg++) {
         double a0 = Math.toRadians(deg);
         double a1 = Math.toRadians(deg + 1);
         outline.vertex(matrix, (float)(Math.cos(a0) * radius), y, (float)(Math.sin(a0) * radius)).color(red, green, blue, alpha);
         outline.vertex(matrix, (float)(Math.cos(a1) * radius), y, (float)(Math.sin(a1) * radius)).color(red, green, blue, alpha);
      }

      matrixStack.pop();
   }

   private static void invoke24(VertexConsumer vertexConsumer, Matrix4f matrix4f, int argb) {
      int red = argb >> 16 & 0xFF;
      int green = argb >> 8 & 0xFF;
      int blue = argb & 0xFF;
      int alpha = argb >>> 24 & 0xFF;

      for (int i = 0; i < DELTA_CRYSTAL_FACES.length; i++) {
         int[] face = DELTA_CRYSTAL_FACES[i];
         float brightness = DELTA_CRYSTAL_SHADE[i];
         int shaded = MathHelper.clamp((int)(red * brightness), 0, 255) << 16
            | MathHelper.clamp((int)(green * brightness), 0, 255) << 8
            | MathHelper.clamp((int)(blue * brightness), 0, 255);
         int faceAlpha = alpha;
         int faceRed = shaded >> 16 & 0xFF;
         int faceGreen = shaded >> 8 & 0xFF;
         int faceBlue = shaded & 0xFF;
         for (int v = 0; v < 3; v++) {
            Vector3f vertex = DELTA_CRYSTAL_VERTS[face[v]];
            vertexConsumer.vertex(matrix4f, vertex.x, vertex.y, vertex.z).color(faceRed, faceGreen, faceBlue, faceAlpha);
         }

         Vector3f last = DELTA_CRYSTAL_VERTS[face[2]];
         vertexConsumer.vertex(matrix4f, last.x, last.y, last.z).color(faceRed, faceGreen, faceBlue, faceAlpha);
      }
   }

   private void invoke18(MatrixStack matrixStack, Immediate immediate, LivingEntity livingEntity, float f) {
      if (livingEntity == null) {
         this.arrayList.clear();
      } else {
         Iterator iterator = this.arrayList.iterator();

         while (iterator.hasNext()) {
            TargetESP.TargetESPEntityState targetESPEntityState = (TargetESP.TargetESPEntityState)iterator.next();
            if (targetESPEntityState.directionalAnimation.getAnimationDirection() != AnimationDirection.FORWARDS && targetESPEntityState.directionalAnimation.measure3() <= 0.0F) {
               iterator.remove();
            }
         }

         long longValue10 = System.currentTimeMillis();
         floatValue2 = Math.max(0.001F, Math.min(0.1F, (float)(longValue10 - timestamp3) / 1000.0F));
         timestamp3 = longValue10;
         if (this.arrayList.size() < 50) {
            this.floatValue3 = this.floatValue3 + floatValue2;

            while (this.floatValue3 >= 0.02F && this.arrayList.size() < 50) {
               this.floatValue3 -= 0.02F;

               for (int intValue99 = 0; intValue99 < 1 && this.arrayList.size() < 50; intValue99++) {
                  double doubleValue105 = RenderMath.measure5(0.0F, 360.0F);
                  double doubleValue106 = Math.cos(doubleValue105 * Math.PI / 180.0) * 0.7F;
                  double doubleValue107 = RenderMath.measure53(0.04F, 0.2F);
                  double doubleValue108 = Math.sin(doubleValue105 * Math.PI / 180.0) * 0.7F;
                  this.arrayList.add(new TargetESP.TargetESPEntityState(livingEntity, doubleValue106, doubleValue107, doubleValue108));
               }
            }
         }

         if (!this.arrayList.isEmpty()) {
            float floatValue99 = (float)easedAnimation.getDoubleValue4();
            easedAnimation2.check();
            int intValue100 = livingEntity.hurtTime;
            float floatValue100 = (float)Math.sin(intValue100 * (Math.PI / 20));
            easedAnimation2.animateTo(floatValue100, 0.4F, Easings.EASE_OUT_QUART);
            float floatValue101 = easedAnimation2.measure3();
            int intValue101 = compute(255, floatValue101);
            int intValue102 = compute(255, floatValue101);
            Vec3d vec3d28 = CLIENT.gameRenderer.getCamera().getPos();
            float floatValue102 = CLIENT.gameRenderer.getCamera().getPitch();
            float floatValue103 = CLIENT.gameRenderer.getCamera().getYaw();

            for (TargetESP.TargetESPEntityState targetESPEntityState2 : this.arrayList) {
               targetESPEntityState2.invoke(f);
               targetESPEntityState2.invoke2(matrixStack, immediate, intValue101, intValue102, floatValue99, floatValue101, f, vec3d28, floatValue102, floatValue103, RENDER_LAYER_5);
            }
         }
      }
   }

   static class TargetESPEntityState {
      double doubleValue;
      double doubleValue2;
      double doubleValue3;
      double doubleValue4;
      double doubleValue5;
      double doubleValue6;
      double doubleValue7;
      double doubleValue8;
      double doubleValue9;
      long timestamp;
      LivingEntity livingEntity;
      DirectionalAnimation directionalAnimation = new EaseInOutQuadAnimation(500, 1.0);
      private double doubleValue10;

      public TargetESPEntityState(LivingEntity livingEntity, double d, double e, double f) {
         this.doubleValue = d;
         this.doubleValue2 = e;
         this.doubleValue3 = f;
         this.livingEntity = livingEntity;
         this.timestamp = System.currentTimeMillis();
         this.doubleValue10 = RenderMath.measure53(0.01F, 0.04F);
      }

      public long getTimestamp() {
         return this.timestamp;
      }

      public void invoke(float f) {
         long longValue11 = System.currentTimeMillis();
         long longValue12 = longValue11 - this.getTimestamp();
         this.directionalAnimation.invoke3(longValue12 <= 800L ? AnimationDirection.FORWARDS : AnimationDirection.BACKWARDS);
         this.doubleValue2 = this.doubleValue2 + this.doubleValue10 * (TargetESP.floatValue2 * 60.0F);
         if (this.livingEntity != null) {
            Vec3d vec3d29 = this.livingEntity.getLerpedPos(f);
            this.doubleValue7 = this.doubleValue + vec3d29.x;
            this.doubleValue8 = this.doubleValue2 + vec3d29.y;
            this.doubleValue9 = this.doubleValue3 + vec3d29.z;
         }
      }

      public void invoke2(
         MatrixStack matrixStack, Immediate immediate, int i, int j, float f, float g, float h, Vec3d vec3d, float k, float l, RenderLayer renderLayer
      ) {
         long longValue13 = System.currentTimeMillis();
         double doubleValue109 = (longValue13 - this.getTimestamp()) / 10.0;
         double doubleValue110 = RenderMath.measure27(0.2F);
         this.doubleValue4 = RenderMath.measure50(this.doubleValue4, this.doubleValue7 - vec3d.x, doubleValue110);
         this.doubleValue5 = RenderMath.measure50(this.doubleValue5, this.doubleValue8 - vec3d.y, doubleValue110);
         this.doubleValue6 = RenderMath.measure50(this.doubleValue6, this.doubleValue9 - vec3d.z, doubleValue110);
         float floatValue104 = this.directionalAnimation.measure3();
         if (!(floatValue104 <= 0.0F)) {
            float floatValue105 = 1.0F + 0.15F * (float)Math.sin((longValue13 - this.getTimestamp()) / 400.0);
            float floatValue106 = 0.12F + 0.04F * floatValue104;
            matrixStack.push();
            matrixStack.translate(this.doubleValue4, this.doubleValue5, this.doubleValue6);
            matrixStack.push();
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees((float)doubleValue109));
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float)doubleValue109));
            matrixStack.multiply(RotationAxis.POSITIVE_Z.rotationDegrees((float)doubleValue109));
            Matrix4f matrix4f14 = matrixStack.peek().getPositionMatrix();
            int intValue103 = TargetESP.compute2(i, (int)(70.0F * f * floatValue104));
            VertexConsumer vertexConsumer13 = immediate.getBuffer(TargetESP.RENDER_LAYER_10);
            EspBoxVertexWriter.invoke4(vertexConsumer13, matrix4f14, intValue103, floatValue106);
            int intValue104 = TargetESP.compute2(i, (int)(225.0F * f * floatValue104));
            VertexConsumer vertexConsumer14 = immediate.getBuffer(TargetESP.RENDER_LAYER_11);
            EspBoxVertexWriter.invoke5(vertexConsumer14, matrix4f14, intValue104, floatValue106);
            matrixStack.pop();
            matrixStack.push();
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-l));
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(k));
            VertexConsumer vertexConsumer15 = immediate.getBuffer(renderLayer);
            Matrix4f matrix4f15 = matrixStack.peek().getPositionMatrix();
            float floatValue107 = floatValue106 * 2.0F;
            matrixStack.scale(floatValue107, floatValue107, floatValue107);
            TargetESP.invoke14(vertexConsumer15, matrix4f15, j, (int)(70.0F * f * floatValue104));
            matrixStack.pop();
            matrixStack.pop();
         }
      }
   }

   record TargetESPData(float x, float y, float w, float h) {
   }
}
