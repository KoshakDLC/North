package ru.metaculture.protection;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.util.OptionalDouble;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.RenderLayer.MultiPhaseParameters;
import net.minecraft.client.render.RenderPhase.LineWidth;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleAccess(
   usernames = {"lichoday"}
)
@ModuleRegister(
   name = "ElytraTarget",
   description = "Преследует таргета на элитре",
   category = Category.Combat,
   riskLevels = {ModuleRiskLevel.RISKY}
)
public class ElytraTarget extends Module {
   public final BooleanSetting peregonyat = new BooleanSetting("Перегонять", true);
   public final ModeSetting rezhimPredikta = new ModeSetting("Режим предикта", "ReallyWorld", "ReallyWorld", "ReallyWorld - 2", "Default");
   public final NumberSetting silaPredikta = new NumberSetting("Сила предикта", 2.7F, 1.0F, 5.0F, 0.1F, false);
   public static final NumberSetting RADIUS_OBNARUZHENIYA_ELITRY = new NumberSetting("Радиус обнаружения элитры", 20.0F, 5.0F, 60.0F, 1.0F, false)
      .setVisibilityCondition(() -> !check());
   public final NumberSetting rastoyaniePresledovaniya = new NumberSetting("Растояние преследования", 30.0F, 10.0F, 100.0F, 5.0F, false);
   public final BooleanSetting razvorotNa180 = new BooleanSetting("Разворот на 180", false);
   public final BooleanSetting risovatPredikt = new BooleanSetting("Рисовать предикт", true);
   public final NumberSetting prozrachnost = new NumberSetting("Прозрачность", 40.0F, 0.0F, 255.0F, 1.0F, false)
      .setVisibilityCondition(() -> !this.risovatPredikt.isEnabled());
   public final BooleanSetting otTemy = new BooleanSetting("От темы", true).visibleWhen(() -> !this.risovatPredikt.isEnabled());
   public final ModeSetting vidKvadrata = new ModeSetting("Вид квадрата", "Обычный", "Обычный", "Пунктир", "Диагонали")
      .setVisibilityCondition(() -> !this.risovatPredikt.isEnabled());
   private static final double DOUBLE_VALUE = 0.35;
   private static final float FLOAT_VALUE = 2.5F;
   private Vec3d vec3d = null;
   private boolean flag = false;
   private LivingEntity livingEntity = null;
   private static final int INT_VALUE = 2048;
   private static final RenderPipeline RENDER_PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("minecraft", "rendertype_lequal_depth_test"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.LIGHTNING)
         .build()
   );
   private static final RenderPipeline RENDER_PIPELINE_2 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("minecraft", "rendertype_lines"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.DEBUG_LINES)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.LIGHTNING)
         .build()
   );
   private static final RenderLayer RENDER_LAYER = RenderLayer.of(
      "elytra_target_fill", 2048, false, true, RENDER_PIPELINE, MultiPhaseParameters.builder().build(false)
   );
   private static final RenderLayer RENDER_LAYER_2 = RenderLayer.of(
      "elytra_target_line", 2048, false, true, RENDER_PIPELINE_2, MultiPhaseParameters.builder().lineWidth(new LineWidth(OptionalDouble.of(2.0))).build(false)
   );

   public ElytraTarget() {
      this.addSettings(
         new Setting[]{
            this.peregonyat,
            this.rezhimPredikta,
            this.silaPredikta,
            RADIUS_OBNARUZHENIYA_ELITRY,
            this.rastoyaniePresledovaniya,
            this.razvorotNa180,
            this.risovatPredikt,
            this.prozrachnost,
            this.otTemy,
            this.vidKvadrata
         }
      );
   }

   public static boolean check() {
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
         ElytraTarget elytraTarget = WildClient.INSTANCE.moduleManager.getModule(ElytraTarget.class);
         return elytraTarget != null && elytraTarget.enabled;
      } else {
         return false;
      }
   }

   @Override
   public void onDisable() {
      this.vec3d = null;
      this.flag = false;
      this.livingEntity = null;
      super.onDisable();
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      this.vec3d = null;
      if (!ServerBlockUtils.check() && CLIENT.player != null && CLIENT.world != null) {
         if (this.peregonyat.isEnabled() && CLIENT.player.isGliding()) {
            LivingEntity livingEntity2 = this.resolve();
            if (livingEntity2 == null) {
               this.flag = false;
            } else {
               float floatValue = CLIENT.player.distanceTo(livingEntity2);
               Vec3d vec3d = this.resolve2(livingEntity2);
               this.vec3d = vec3d;
               Vec3d vec3d2 = vec3d.subtract(CLIENT.player.getEyePos());
               if (!(vec3d2.lengthSquared() < 1.0E-7)) {
                  float floatValue2 = (float)Math.toDegrees(Math.atan2(-vec3d2.x, vec3d2.z));
                  float floatValue3 = (float)MathHelper.clamp(-Math.toDegrees(Math.atan2(vec3d2.y, Math.hypot(vec3d2.x, vec3d2.z))), -90.0, 90.0);
                  if (this.check3(floatValue)) {
                     floatValue2 = MathHelper.wrapDegrees(floatValue2 + 180.0F);
                  }

                  RotationController.invoke3(new Rotation(floatValue2, floatValue3), 32.0F, 32.0F, 360.0F, 360.0F, 0, 12, true);
               }
            }
         } else {
            this.flag = false;
            this.livingEntity = null;
         }
      }
   }

   private LivingEntity resolve() {
      float floatValue4 = RADIUS_OBNARUZHENIYA_ELITRY.getValue();
      float floatValue5 = Math.max(this.rastoyaniePresledovaniya.getValue(), floatValue4);
      if (this.check2(this.livingEntity) && CLIENT.player.distanceTo(this.livingEntity) <= floatValue5) {
         return this.livingEntity;
      } else {
         this.livingEntity = null;
         LivingEntity livingEntity3 = AttackAura.livingEntity;
         if (this.check2(livingEntity3) && CLIENT.player.distanceTo(livingEntity3) <= floatValue5) {
            this.livingEntity = livingEntity3;
            return this.livingEntity;
         } else {
            LivingEntity livingEntity4 = null;
            double doubleValue = floatValue4 * floatValue4;

            for (Entity entity : CLIENT.world.getEntities()) {
               if (entity instanceof LivingEntity livingEntity5 && this.check2(livingEntity5)) {
                  double doubleValue2 = CLIENT.player.squaredDistanceTo(livingEntity5);
                  if (doubleValue2 <= doubleValue) {
                     doubleValue = doubleValue2;
                     livingEntity4 = livingEntity5;
                  }
               }
            }

            this.livingEntity = livingEntity4;
            return livingEntity4;
         }
      }
   }

   private boolean check2(LivingEntity livingEntity) {
      return livingEntity != null
         && livingEntity.isAlive()
         && livingEntity != CLIENT.player
         && !(livingEntity instanceof ClientPlayerEntity)
         && livingEntity.isGliding();
   }

   private Vec3d resolve2(LivingEntity livingEntity) {
      Vec3d vec3d3 = livingEntity.getPos().add(0.0, livingEntity.getHeight() * 0.5, 0.0);
      Vec3d vec3d4 = livingEntity.getVelocity();
      double doubleValue3 = this.silaPredikta.getValue();
      if (this.rezhimPredikta.is("Default")) {
         return vec3d3;
      } else if (this.rezhimPredikta.is("ReallyWorld - 2")) {
         Vec3d vec3d5 = livingEntity.getRotationVector().normalize().multiply(2.0);
         return vec3d3.add(vec3d5).add(vec3d4.multiply(doubleValue3));
      } else {
         return vec3d3.add(vec3d4.multiply(doubleValue3));
      }
   }

   private boolean check3(float f) {
      if (!this.razvorotNa180.isEnabled()) {
         this.flag = false;
         return false;
      } else {
         float floatValue6 = Math.max(2.5F, this.silaPredikta.getValue());
         float floatValue7 = floatValue6 + 3.0F;
         if (!this.flag && f <= 2.5F) {
            this.flag = true;
         }

         if (this.flag && f >= floatValue7) {
            this.flag = false;
         }

         return this.flag;
      }
   }

   @EventHandler
   public void onRender3D(Render3DEvent render3DEvent) {
      if (this.risovatPredikt.isEnabled() && this.vec3d != null && CLIENT.world != null) {
         Vec3d vec3d6 = CLIENT.gameRenderer.getCamera().getPos();
         double doubleValue4 = this.vec3d.x - 0.35 - vec3d6.x;
         double doubleValue5 = this.vec3d.y - 0.35 - vec3d6.y;
         double doubleValue6 = this.vec3d.z - 0.35 - vec3d6.z;
         double doubleValue7 = this.vec3d.x + 0.35 - vec3d6.x;
         double doubleValue8 = this.vec3d.y + 0.35 - vec3d6.y;
         double doubleValue9 = this.vec3d.z + 0.35 - vec3d6.z;
         int intValue = this.otTemy.isEnabled() ? ColorUtils.compute41() : ColorUtils.compute43(255, 255, 255, 255);
         int[] intValues = new int[]{intValue, intValue, intValue, intValue};
         int intValue2 = (int)this.prozrachnost.getValue();
         boolean flag = this.vidKvadrata.is("Диагонали");
         double doubleValue10 = this.vidKvadrata.is("Пунктир") ? 0.12 : 0.5;
         double doubleValue11 = this.vidKvadrata.is("Пунктир") ? 0.1 : 0.0;
         Immediate immediate = WorldRenderBuffer.getIMMEDIATE();

         try {
            MatrixStack matrices = render3DEvent.getMatrixStack();
            Matrix4f matrix4f2 = matrices.peek().getPositionMatrix();
            if (intValue2 > 0) {
               VertexConsumer vertexConsumer2 = immediate.getBuffer(RENDER_LAYER);
               VertexShapeRenderer.invoke2(vertexConsumer2, matrix4f2, doubleValue4, doubleValue5, doubleValue6, doubleValue7, doubleValue8, doubleValue9, intValues, intValue2);
            }

            VertexConsumer vertexConsumer3 = immediate.getBuffer(RENDER_LAYER_2);
            VertexShapeRenderer.invoke3(vertexConsumer3, matrix4f2, doubleValue4, doubleValue5, doubleValue6, doubleValue7, doubleValue8, doubleValue9, intValues, 255, doubleValue10, doubleValue11);
            if (flag) {
               this.invoke(vertexConsumer3, matrix4f2, intValue, doubleValue4, doubleValue5, doubleValue6, doubleValue7, doubleValue8, doubleValue9);
            }
         } finally {
            WorldRenderBuffer.invoke();
         }
      }
   }

   private void invoke(VertexConsumer vertexConsumer, Matrix4f matrix4f, int i, double d, double e, double f, double g, double h, double j) {
      int intValue3 = i >> 16 & 0xFF;
      int intValue4 = i >> 8 & 0xFF;
      int intValue5 = i & 0xFF;
      short shortValue = 255;
      double[][] doubleValuesValues = new double[][]{{d, e, f, g, h, j}, {g, e, f, d, h, j}, {d, e, j, g, h, f}, {g, e, j, d, h, f}};

      for (double[] doubleValues : doubleValuesValues) {
         vertexConsumer.vertex(matrix4f, (float)doubleValues[0], (float)doubleValues[1], (float)doubleValues[2]).color(intValue3, intValue4, intValue5, shortValue);
         vertexConsumer.vertex(matrix4f, (float)doubleValues[3], (float)doubleValues[4], (float)doubleValues[5]).color(intValue3, intValue4, intValue5, shortValue);
      }
   }
}
