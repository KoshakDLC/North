package ru.metaculture.protection;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.RenderLayer.MultiPhaseParameters;
import net.minecraft.client.render.RenderPhase.LineWidth;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "ESP",
   description = "Подцветка игроков",
   category = Category.Visuals
)
public class ESP extends Module {
   private static final int INT_VALUE = 2048;
   private static final int INT_VALUE_2 = 96;
   private static final int INT_VALUE_3 = ColorUtils.compute43(52, 255, 96, 255);
   public final GroupSetting targets = new GroupSetting("Targets", new BooleanSetting("Players", true), new BooleanSetting("Mobs", true));
   public final NumberSetting distance = new NumberSetting("Distance", 72.0F, 8.0F, 200.0F, 1.0F, false);
   public final BooleanSetting fill = new BooleanSetting("Fill", true);
   public final BooleanSetting outline = new BooleanSetting("Outline", true);
   private final List<Entity> items = new ArrayList<>(96);
   private final int[] ints = new int[4];
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
      "litka_esp_fill", 2048, false, true, RENDER_PIPELINE, MultiPhaseParameters.builder().build(false)
   );
   private static final RenderLayer RENDER_LAYER_2 = RenderLayer.of(
      "litka_esp_line", 2048, false, true, RENDER_PIPELINE_2, MultiPhaseParameters.builder().lineWidth(new LineWidth(OptionalDouble.of(2.2))).build(false)
   );

   public ESP() {
      this.addSettings(new Setting[]{this.targets, this.distance, this.fill, this.outline});
   }

   @EventHandler
   public void onRender3D(Render3DEvent render3DEvent) {
      if (CLIENT.world != null && CLIENT.player != null) {
         List items = this.resolve();
         if (!items.isEmpty()) {
            Immediate immediate2 = WorldRenderBuffer.getIMMEDIATE();

            try {
               for (Entity entity2 : (List<Entity>)items) {
                  this.invoke(render3DEvent.getMatrixStack(), immediate2, entity2, render3DEvent.getFloatValue());
               }
            } finally {
               WorldRenderBuffer.invoke();
            }
         }
      }
   }

   private List<Entity> resolve() {
      this.items.clear();
      float floatValue = this.distance.getValue() * this.distance.getValue();
      boolean flag = this.targets.isEnabled("Players");
      boolean flag2 = this.targets.isEnabled("Mobs");

      for (Entity entity3 : CLIENT.world.getEntities()) {
         if (this.items.size() >= 96) {
            break;
         }

         if (this.check(entity3, flag, flag2) && !(CLIENT.player.squaredDistanceTo(entity3) > floatValue)) {
            this.items.add(entity3);
         }
      }

      return this.items;
   }

   private boolean check(Entity entity, boolean bl, boolean bl2) {
      if (entity != null && entity != CLIENT.player) {
         if (!(entity instanceof LivingEntity livingEntity && livingEntity.isAlive())) {
            return false;
         } else {
            return entity instanceof PlayerEntity ? bl : bl2;
         }
      } else {
         return false;
      }
   }

   private void invoke(MatrixStack matrixStack, Immediate immediate, Entity entity, float f) {
      Vec3d vec3d = CLIENT.gameRenderer.getCamera().getPos();
      Vec3d vec3d2 = this.resolve2(entity, f);
      Box box = entity.getBoundingBox().offset(vec3d2.x - entity.getX(), vec3d2.y - entity.getY(), vec3d2.z - entity.getZ());
      float floatValue2 = entity instanceof PlayerEntity ? 0.09F : 0.06F;
      Box box2 = box.expand(floatValue2).offset(-vec3d.x, -vec3d.y, -vec3d.z);
      int intValue = this.compute(entity);
      float floatValue3 = this.measure(entity);
      int intValue2 = ColorUtils.compute34(intValue, 0.92F);
      int intValue3 = ColorUtils.compute33(intValue, 0.62F);
      int intValue4 = ColorUtils.compute33(intValue, 0.8F);
      int[] intValues = this.ints;
      intValues[0] = ColorUtils.compute42(intValue2, intValue4, 0, 10);
      intValues[1] = ColorUtils.compute42(intValue4, intValue3, 90, 10);
      intValues[2] = ColorUtils.compute42(intValue3, intValue4, 180, 10);
      intValues[3] = ColorUtils.compute42(intValue4, intValue2, 270, 10);
      Matrix4f matrix4f = matrixStack.peek().getPositionMatrix();
      if (this.fill.isEnabled()) {
         int intValue5 = (int)(MathHelper.clamp(floatValue3, 0.1F, 1.0F) * 95.0F);
         VertexConsumer vertexConsumer = immediate.getBuffer(RENDER_LAYER);
         VertexShapeRenderer.invoke2(vertexConsumer, matrix4f, box2.minX, box2.minY, box2.minZ, box2.maxX, box2.maxY, box2.maxZ, intValues, intValue5);
      }

      if (this.outline.isEnabled()) {
         int intValue6 = (int)(MathHelper.clamp(floatValue3, 0.2F, 1.0F) * 255.0F);
         VertexConsumer vertexConsumer2 = immediate.getBuffer(RENDER_LAYER_2);
         VertexShapeRenderer.invoke3(vertexConsumer2, matrix4f, box2.minX, box2.minY, box2.minZ, box2.maxX, box2.maxY, box2.maxZ, intValues, intValue6, 0.18, 0.06);
      }
   }

   private Vec3d resolve2(Entity entity, float f) {
      double doubleValue = MathHelper.lerp(f, entity.lastRenderX, entity.getX());
      double doubleValue2 = MathHelper.lerp(f, entity.lastRenderY, entity.getY());
      double doubleValue3 = MathHelper.lerp(f, entity.lastRenderZ, entity.getZ());
      return new Vec3d(doubleValue, doubleValue2, doubleValue3);
   }

   private float measure(Entity entity) {
      float floatValue4 = CLIENT.player.distanceTo(entity);
      float floatValue5 = Math.max(this.distance.getValue(), 1.0F);
      return 1.0F - MathHelper.clamp(floatValue4 / floatValue5, 0.0F, 1.0F);
   }

   private int compute(Entity entity) {
      if (entity instanceof PlayerEntity playerEntity) {
         String text = playerEntity.getGameProfile() != null ? playerEntity.getGameProfile().getName() : playerEntity.getName().getString();
         return FriendCommand.check(text) ? INT_VALUE_3 : ColorUtils.compute40(playerEntity.getId() * 17);
      } else {
         return ColorUtils.compute40(entity.getId() * 11);
      }
   }
}
