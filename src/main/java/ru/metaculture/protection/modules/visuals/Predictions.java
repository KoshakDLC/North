package ru.metaculture.protection;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.platform.DestFactor;
import com.mojang.blaze3d.platform.SourceFactor;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.RenderLayer.MultiPhaseParameters;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.thrown.ThrownEntity;
import net.minecraft.item.BowItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.EggItem;
import net.minecraft.item.EnderPearlItem;
import net.minecraft.item.ExperienceBottleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SnowballItem;
import net.minecraft.item.ThrowablePotionItem;
import net.minecraft.item.TridentItem;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Direction.Axis;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.joml.Matrix4f;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "Predictions",
   description = "Показ предикта траэктории полета",
   category = Category.Visuals
)
public class Predictions extends Module {
   private static final int INT_VALUE = 240;
   private static final int INT_VALUE_2 = 96;
   private static final int INT_VALUE_3 = 2097152;
   private static final int INT_VALUE_4 = 72;
   private static final int INT_VALUE_5 = 10;
   private static final int INT_VALUE_6 = 6;
   private static final int INT_VALUE_7 = 8;
   private static final int INT_VALUE_8 = 6;
   private static final long TIMESTAMP = System.nanoTime();
   private static final float FLOAT_VALUE = 1.5F;
   private static final float FLOAT_VALUE_2 = 0.5F;
   private static final float FLOAT_VALUE_3 = 0.7F;
   private static final float FLOAT_VALUE_4 = -20.0F;
   private static final double DOUBLE_VALUE = 0.99;
   private static final double DOUBLE_VALUE_2 = 0.8;
   private static final float FLOAT_VALUE_5 = 0.1F;
   private static final float FLOAT_VALUE_6 = 0.1F;
   private static final float FLOAT_VALUE_7 = 0.1F;
   private static final double DOUBLE_VALUE_3 = 64.0;
   private static final double DOUBLE_VALUE_4 = 16.0;
   private static final Identifier IDENTIFIER = Identifier.of("wild", "core/prediction_vfx");
   private static final BlendFunction BLEND_FUNCTION = new BlendFunction(SourceFactor.SRC_ALPHA, DestFactor.ONE);
   private static final RenderPipeline RENDER_PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET, RenderPipelines.GLOBALS_SNIPPET})
         .withLocation(Identifier.of("wild", "prediction_glass"))
         .withVertexShader(IDENTIFIER)
         .withFragmentShader(IDENTIFIER)
         .withVertexFormat(VertexFormats.POSITION_TEXTURE_COLOR_NORMAL, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.TRANSLUCENT)
         .build()
   );
   private static final RenderPipeline RENDER_PIPELINE_2 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET, RenderPipelines.GLOBALS_SNIPPET})
         .withLocation(Identifier.of("wild", "prediction_glass_no_depth"))
         .withVertexShader(IDENTIFIER)
         .withFragmentShader(IDENTIFIER)
         .withVertexFormat(VertexFormats.POSITION_TEXTURE_COLOR_NORMAL, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.TRANSLUCENT)
         .build()
   );
   private static final RenderPipeline RENDER_PIPELINE_3 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET, RenderPipelines.GLOBALS_SNIPPET})
         .withLocation(Identifier.of("wild", "prediction_emission"))
         .withVertexShader(IDENTIFIER)
         .withFragmentShader(IDENTIFIER)
         .withVertexFormat(VertexFormats.POSITION_TEXTURE_COLOR_NORMAL, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.LEQUAL_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BLEND_FUNCTION)
         .build()
   );
   private static final RenderPipeline RENDER_PIPELINE_4 = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.TRANSFORMS_AND_PROJECTION_SNIPPET, RenderPipelines.GLOBALS_SNIPPET})
         .withLocation(Identifier.of("wild", "prediction_emission_no_depth"))
         .withVertexShader(IDENTIFIER)
         .withFragmentShader(IDENTIFIER)
         .withVertexFormat(VertexFormats.POSITION_TEXTURE_COLOR_NORMAL, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BLEND_FUNCTION)
         .build()
   );
   private static final RenderLayer RENDER_LAYER = RenderLayer.of(
      "wild_prediction_glass", 2097152, false, true, RENDER_PIPELINE, MultiPhaseParameters.builder().build(false)
   );
   private static final RenderLayer RENDER_LAYER_2 = RenderLayer.of(
      "wild_prediction_glass_no_depth", 2097152, false, true, RENDER_PIPELINE_2, MultiPhaseParameters.builder().build(false)
   );
   private static final RenderLayer RENDER_LAYER_3 = RenderLayer.of(
      "wild_prediction_emission", 2097152, false, true, RENDER_PIPELINE_3, MultiPhaseParameters.builder().build(false)
   );
   private static final RenderLayer RENDER_LAYER_4 = RenderLayer.of(
      "wild_prediction_emission_no_depth", 2097152, false, true, RENDER_PIPELINE_4, MultiPhaseParameters.builder().build(false)
   );
   private final BooleanSetting throughwalls = new BooleanSetting("ThroughWalls", true);
   private final BooleanSetting aimpreview = new BooleanSetting("AimPreview", true);
   private final BooleanSetting showowner = new BooleanSetting("ShowOwner", false);
   private final HudElement hudElement = new HudElement() {};
   private final List<Predictions.PredictionsEntry> items = new ArrayList<>();
   private final Map<String, Predictions.PredictionsEntry> valuesByKey = new HashMap<>();
   private ItemStack itemStack;

   public Predictions() {
      this.addSettings(new Setting[]{this.throughwalls, this.aimpreview, this.showowner});
   }

   @EventHandler
   public void onRender3D(Render3DEvent render3DEvent) {
      if (CLIENT.world == null || CLIENT.player == null) {
         this.invoke3();
      } else if (CLIENT.options != null && CLIENT.options.getPerspective() != null && CLIENT.options.getPerspective().isFirstPerson()) {
         this.invoke(render3DEvent.getFloatValue());
         if (!this.items.isEmpty()) {
            MatrixStack matrices = render3DEvent.getMatrixStack();
            Matrix4f matrix4f2 = matrices.peek().getPositionMatrix();
            Vec3d vec3d4 = CLIENT.gameRenderer.getCamera().getPos();
            RenderLayer renderLayer = this.throughwalls.isEnabled() ? RENDER_LAYER_2 : RENDER_LAYER;
            RenderLayer renderLayer2 = this.throughwalls.isEnabled() ? RENDER_LAYER_4 : RENDER_LAYER_3;
            int intValue = ColorUtils.compute29(ColorUtils.compute41(), 235);
            Immediate immediate = WorldRenderBuffer.getIMMEDIATE();
            boolean flag = false ;

            try {
               flag = true;
               VertexConsumer vertexConsumer2 = immediate.getBuffer(renderLayer);

               for (Predictions.PredictionsEntry predictionsEntry : this.items) {
                  boolean flag2 = predictionsEntry.hitEntity() != null;
                  boolean flag3 = predictionsEntry.blockHit() != null && predictionsEntry.blockHit().getType() != Type.MISS;
                  boolean flag4 = flag2 || flag3;
                  int intValue2 = flag2 ? -51112 : intValue;
                  this.invoke4(vertexConsumer2, matrix4f2, vec3d4, predictionsEntry.path(), intValue2, flag4);
               }

               flag = false;
            } finally {
               if (flag) {
                  WorldRenderBuffer.invoke();
               }
            }

            WorldRenderBuffer.invoke();
            immediate = WorldRenderBuffer.getIMMEDIATE();

            try {
               VertexConsumer vertexConsumer3 = immediate.getBuffer(renderLayer2);

               for (Predictions.PredictionsEntry predictionsEntry2 : this.items) {
                  boolean flag5 = predictionsEntry2.hitEntity() != null;
                  boolean flag6 = predictionsEntry2.blockHit() != null && predictionsEntry2.blockHit().getType() != Type.MISS;
                  boolean flag7 = flag5 || flag6;
                  int intValue3 = flag5 ? -51112 : intValue;
                  int intValue4 = intValue3 >> 16 & 0xFF;
                  int intValue5 = intValue3 >> 8 & 0xFF;
                  int intValue6 = intValue3 & 0xFF;
                  this.invoke5(vertexConsumer3, matrix4f2, vec3d4, predictionsEntry2.path(), intValue3, flag7);
                  if (flag5) {
                     Box box3 = predictionsEntry2.targetBox() != null ? predictionsEntry2.targetBox() : predictionsEntry2.hitEntity().getBoundingBox();
                     this.invoke16(
                        vertexConsumer3,
                        matrix4f2,
                        box3.minX - vec3d4.x,
                        box3.minY - vec3d4.y,
                        box3.minZ - vec3d4.z,
                        box3.maxX - vec3d4.x,
                        box3.maxY - vec3d4.y,
                        box3.maxZ - vec3d4.z,
                        intValue4,
                        intValue5,
                        intValue6,
                        230
                     );
                     this.invoke9(vertexConsumer3, matrix4f2, vec3d4, box3, intValue4, intValue5, intValue6);
                  } else if (flag6) {
                     Vec3d vec3d5 = predictionsEntry2.blockRenderPos();
                     if (vec3d5 != null) {
                        this.invoke14(
                           vertexConsumer3,
                           matrix4f2,
                           vec3d5.x - vec3d4.x,
                           vec3d5.y - vec3d4.y,
                           vec3d5.z - vec3d4.z,
                           vec3d5.x + 1.0 - vec3d4.x,
                           vec3d5.y + 1.0 - vec3d4.y,
                           vec3d5.z + 1.0 - vec3d4.z,
                           intValue4,
                           intValue5,
                           intValue6
                        );
                     }

                     this.invoke8(vertexConsumer3, matrix4f2, vec3d4, predictionsEntry2.blockHit(), predictionsEntry2.landingPos(), intValue3);
                  }
               }
            } finally {
               WorldRenderBuffer.invoke();
            }
         }
      } else {
         this.invoke3();
      }
   }

   @EventHandler
   public void onHudRender(HudRenderEvent hudRenderEvent) {
      if (CLIENT.world != null && CLIENT.player != null) {
         if (CLIENT.options != null && CLIENT.options.getPerspective() != null && CLIENT.options.getPerspective().isFirstPerson()) {
            if (!this.items.isEmpty()) {
               RenderManager renderManager = hudRenderEvent.getRenderManager();
               renderManager.invoke48(23.0F);

               for (Predictions.PredictionsEntry predictionsEntry3 : this.items) {
                  if (!predictionsEntry3.isPreAim()) {
                     Vec3d vec3d6 = MathUtils.resolve(predictionsEntry3.landingPos());
                     if (vec3d6 != null && !(vec3d6.z < 0.0) && !(vec3d6.z > 1.0)) {
                        float floatValue = (float)vec3d6.x;
                        float floatValue2 = (float)vec3d6.y;
                        float floatValue3 = 1.0F;
                        this.invoke21(renderManager, floatValue, floatValue2, floatValue3, predictionsEntry3);
                        if (this.showowner.isEnabled() && this.check2(predictionsEntry3)) {
                           this.invoke22(renderManager, floatValue, floatValue2 - 31.0F * floatValue3, floatValue3, predictionsEntry3);
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void invoke(float f) {
      this.items.clear();
      if (CLIENT.world != null && CLIENT.player != null) {
         ArrayList arrayList = new ArrayList();
         ItemStack itemStack2 = CLIENT.player.getMainHandStack();
         boolean flag8 = false;
         if (itemStack2.isEmpty() || !this.check(itemStack2.getItem())) {
            itemStack2 = CLIENT.player.getOffHandStack();
            flag8 = true;
         }

         if (this.aimpreview.isEnabled() && !itemStack2.isEmpty() && this.check(itemStack2.getItem())) {
            Predictions.PredictionsEntry predictionsEntry4 = this.resolve(CLIENT.player, itemStack2, flag8, f);
            if (predictionsEntry4 != null) {
               arrayList.add(predictionsEntry4);
            }
         }

         if (ClickPearl.flag && (itemStack2.isEmpty() || !(itemStack2.getItem() instanceof EnderPearlItem))) {
            if (this.itemStack == null) {
               this.itemStack = new ItemStack(Items.ENDER_PEARL);
            }

            Predictions.PredictionsEntry predictionsEntry5 = this.resolve(CLIENT.player, this.itemStack, false, f);
            if (predictionsEntry5 != null) {
               arrayList.add(predictionsEntry5);
            }
         }

         for (ProjectileEntity projectileEntity2 : CLIENT.world
            .getEntitiesByClass(
               ProjectileEntity.class,
               CLIENT.player.getBoundingBox().expand(256.0),
               projectileEntity -> !(projectileEntity instanceof FireworkRocketEntity)
            )) {
            Predictions.PredictionsEntry predictionsEntry6 = this.resolve4(projectileEntity2, f);
            if (predictionsEntry6 != null) {
               arrayList.add(predictionsEntry6);
            }
         }

         this.invoke2(arrayList);
      } else {
         this.valuesByKey.clear();
      }
   }

   private boolean check(Item item) {
      return item instanceof EnderPearlItem
         || item instanceof SnowballItem
         || item instanceof EggItem
         || item instanceof BowItem
         || item instanceof CrossbowItem
         || item instanceof TridentItem
         || item instanceof ThrowablePotionItem
         || item instanceof ExperienceBottleItem;
   }

   private Predictions.PredictionsEntry resolve(PlayerEntity playerEntity, ItemStack itemStack, boolean bl, float f) {
      Item item2 = itemStack.getItem();
      Predictions.PredictionsData predictionsData = this.resolve2(playerEntity, item2);
      Identifier identifier2 = this.resolve19(item2);
      Camera camera = CLIENT.gameRenderer.getCamera();
      float floatValue4 = camera.getYaw();
      float floatValue5 = camera.getPitch();
      Vec3d vec3d7 = this.resolve3(playerEntity, floatValue4, floatValue5, predictionsData.speed(), predictionsData.pitchOffset());
      Vec3d vec3d8 = camera.getPos().subtract(0.0, 0.1, 0.0);
      Vec3d vec3d9 = Vec3d.fromPolar(floatValue5, floatValue4);
      Vec3d vec3d10 = Vec3d.fromPolar(0.0F, floatValue4 + 90.0F);
      float floatValue6 = bl ? -0.3F : 0.3F;
      Vec3d vec3d11 = vec3d8.add(vec3d9.multiply(0.4)).add(vec3d10.multiply(floatValue6)).subtract(0.0, 0.2, 0.0);
      Vec3d vec3d12 = vec3d11.subtract(vec3d8);
      String text = (bl ? "self:off:" : "self:main:") + Registries.ITEM.getId(item2);
      return this.resolve5(playerEntity, vec3d8, vec3d12, vec3d7, predictionsData.gravity(), 0.99, predictionsData.applyPhysicsBeforeMove(), f, text, "You", identifier2, true);
   }

   private Predictions.PredictionsData resolve2(PlayerEntity playerEntity, Item item) {
      double doubleValue = 1.5;
      double doubleValue2 = 0.03;
      float floatValue7 = 0.0F;
      boolean flag9 = item instanceof EnderPearlItem || item instanceof SnowballItem || item instanceof EggItem;
      if (item instanceof BowItem) {
         int intValue7 = playerEntity.getItemUseTime();
         float floatValue8 = intValue7 == 0 ? 1.0F : BowItem.getPullProgress(intValue7);
         doubleValue = floatValue8 * 3.0;
         doubleValue2 = 0.05;
         flag9 = false;
      } else if (item instanceof CrossbowItem) {
         doubleValue = 3.15;
         doubleValue2 = 0.05;
         flag9 = false;
      } else if (item instanceof TridentItem) {
         doubleValue = 2.5;
         doubleValue2 = 0.05;
         flag9 = false;
      } else if (item instanceof ExperienceBottleItem) {
         doubleValue = 0.7F;
         doubleValue2 = 0.07;
         floatValue7 = -20.0F;
         flag9 = true;
      } else if (item instanceof ThrowablePotionItem) {
         doubleValue = 0.5;
         doubleValue2 = 0.05;
         floatValue7 = -20.0F;
         flag9 = true;
      }

      return new Predictions.PredictionsData(doubleValue, doubleValue2, floatValue7, flag9);
   }

   private Vec3d resolve3(PlayerEntity playerEntity, float f, float g, double d, float h) {
      float floatValue9 = f * (float) (Math.PI / 180.0);
      float floatValue10 = g * (float) (Math.PI / 180.0);
      float floatValue11 = (g + h) * (float) (Math.PI / 180.0);
      double doubleValue3 = -MathHelper.sin(floatValue9) * MathHelper.cos(floatValue10);
      double doubleValue4 = -MathHelper.sin(floatValue11);
      double doubleValue5 = MathHelper.cos(floatValue9) * MathHelper.cos(floatValue10);
      Vec3d vec3d13 = new Vec3d(doubleValue3, doubleValue4, doubleValue5).normalize().multiply(d);
      Vec3d vec3d14 = playerEntity.getMovement();
      return vec3d13.add(vec3d14.x, playerEntity.isOnGround() ? 0.0 : vec3d14.y, vec3d14.z);
   }

   private Predictions.PredictionsEntry resolve4(ProjectileEntity projectileEntity, float f) {
      if (!projectileEntity.isRemoved() && !(projectileEntity.getVelocity().lengthSquared() < 0.001)) {
         Vec3d vec3d15 = projectileEntity.getLerpedPos(f);
         double doubleValue6 = projectileEntity.getFinalGravity();
         Identifier identifier3 = this.resolve20(projectileEntity);
         boolean flag10 = projectileEntity instanceof ThrownEntity;
         double doubleValue7 = flag10 && projectileEntity.isTouchingWater() ? 0.8 : 0.99;
         return this.resolve5(
            projectileEntity,
            vec3d15,
            Vec3d.ZERO,
            projectileEntity.getVelocity(),
            doubleValue6,
            doubleValue7,
            flag10,
            f,
            "entity:" + projectileEntity.getId(),
            Predictions.PredictionsEntry.resolveOwnerName(projectileEntity),
            identifier3,
            false
         );
      } else {
         return null;
      }
   }

   private Predictions.PredictionsEntry resolve5(
      Entity entity,
      Vec3d vec3d,
      Vec3d vec3d2,
      Vec3d vec3d3,
      double d,
      double e,
      boolean bl,
      float f,
      String string,
      String string2,
      Identifier identifier,
      boolean bl2
   ) {
      if (CLIENT.world == null) {
         return null;
      } else {
         Vec3d vec3d16 = vec3d3;
         Vec3d vec3d17 = vec3d;
         ArrayList arrayList2 = new ArrayList();
         arrayList2.add(vec3d.add(vec3d2));
         int intValue8 = 0;
         BlockHitResult blockHitResult2 = null;
         Entity entity2 = null;
         byte byteValue = 7;

         for (int intValue9 = 0; intValue9 < 240; intValue9++) {
            Vec3d vec3d18 = bl ? this.resolve16(vec3d16, d, e) : vec3d16;
            Vec3d vec3d19 = vec3d17.add(vec3d18);
            blockHitResult2 = CLIENT.world.raycast(new RaycastContext(vec3d17, vec3d19, ShapeType.COLLIDER, FluidHandling.NONE, entity));
            Vec3d vec3d20 = blockHitResult2.getType() != Type.MISS ? blockHitResult2.getPos() : vec3d19;
            Box box4 = new Box(vec3d17, vec3d20).expand(1.0);
            double doubleValue8 = Double.MAX_VALUE;
            Vec3d vec3d21 = null;
            Entity entity3 = null;

            for (Entity entity4 : CLIENT.world.getOtherEntities(entity, box4, entityx -> !entityx.isSpectator() && entityx.isAlive())) {
               Box box5 = entity4.getBoundingBox().expand(0.3);
               Optional optional = box5.raycast(vec3d17, vec3d20);
               if (optional.isPresent()) {
                  double doubleValue9 = vec3d17.squaredDistanceTo((Vec3d)optional.get());
                  if (doubleValue9 < doubleValue8) {
                     doubleValue8 = doubleValue9;
                     vec3d21 = (Vec3d)optional.get();
                     entity3 = entity4;
                  }
               }
            }

            double doubleValue10 = Math.max(0.0, 1.0 - (double)(intValue9 + 1) / byteValue);
            if (entity3 != null) {
               arrayList2.add(vec3d21.add(vec3d2.multiply(doubleValue10)));
               entity2 = entity3;
               vec3d17 = vec3d21;
               intValue8 = intValue9 + 1;
               break;
            }

            if (blockHitResult2.getType() != Type.MISS) {
               arrayList2.add(blockHitResult2.getPos().add(vec3d2.multiply(doubleValue10)));
               vec3d17 = blockHitResult2.getPos();
               intValue8 = intValue9 + 1;
               break;
            }

            arrayList2.add(vec3d19.add(vec3d2.multiply(doubleValue10)));
            vec3d17 = vec3d19;
            vec3d16 = bl ? vec3d18 : this.resolve17(vec3d16, d, e);
            intValue8 = intValue9 + 1;
         }

         if (arrayList2.size() < 2) {
            return null;
         } else {
            Box box6 = entity2 != null ? this.resolve14(entity2, f) : null;
            return new Predictions.PredictionsEntry(string, this.resolve6((List<Vec3d>)arrayList2), vec3d17, this.resolve15(blockHitResult2), box6, intValue8, entity2, blockHitResult2, string2, identifier, bl2);
         }
      }
   }

   private List<Vec3d> resolve6(List<Vec3d> list) {
      int intValue10 = list.size();
      if (intValue10 <= 96) {
         return list;
      } else {
         int intValue11 = Math.max(2, (int)Math.ceil(intValue10 / 96.0));
         ArrayList arrayList3 = new ArrayList(intValue10 / intValue11 + 2);

         for (int intValue12 = 0; intValue12 < intValue10; intValue12 += intValue11) {
            arrayList3.add((Vec3d)list.get(intValue12));
         }

         Vec3d vec3d22 = (Vec3d)list.get(intValue10 - 1);
         if (arrayList3.isEmpty() || arrayList3.get(arrayList3.size() - 1) != vec3d22) {
            arrayList3.add(vec3d22);
         }

         return arrayList3;
      }
   }

   private void invoke2(List<Predictions.PredictionsEntry> list) {
      if (list.isEmpty()) {
         this.valuesByKey.clear();
      } else {
         HashMap hashMap = new HashMap();

         for (Predictions.PredictionsEntry predictionsEntry7 : list) {
            Predictions.PredictionsEntry predictionsEntry8 = this.resolve7(predictionsEntry7);
            this.items.add(predictionsEntry8);
            hashMap.put(predictionsEntry8.key(), predictionsEntry8);
         }

         this.valuesByKey.clear();
         this.valuesByKey.putAll(hashMap);
      }
   }

   private Predictions.PredictionsEntry resolve7(Predictions.PredictionsEntry predictionsEntry9) {
      Predictions.PredictionsEntry predictionsEntry10 = this.valuesByKey.get(predictionsEntry9.key());
      if (predictionsEntry10 != null && predictionsEntry10.path().size() >= 2 && predictionsEntry9.path().size() >= 2) {
         if (predictionsEntry10.path().get(0).squaredDistanceTo(predictionsEntry9.path().get(0)) > 256.0) {
            return predictionsEntry9;
         } else {
            List items = this.resolve8(predictionsEntry10.path(), predictionsEntry9.path(), 0.1F);
            float floatValue12 = predictionsEntry9.isPreAim() ? 0.1F : 0.1F;
            Vec3d vec3d23 = this.resolve10(predictionsEntry10.landingPos(), predictionsEntry9.landingPos(), floatValue12, 64.0);
            Vec3d vec3d24 = this.resolve11(predictionsEntry10.blockRenderPos(), predictionsEntry9.blockRenderPos(), floatValue12, 64.0);
            Box box7 = this.resolve12(predictionsEntry10, predictionsEntry9, predictionsEntry9.isPreAim() ? 0.1F : 0.1F);
            return predictionsEntry9.withRenderState(items, vec3d23, vec3d24, box7);
         }
      } else {
         return predictionsEntry9;
      }
   }

   private List<Vec3d> resolve8(List<Vec3d> list, List<Vec3d> list2, float f) {
      ArrayList arrayList4 = new ArrayList(list2.size());

      for (int intValue13 = 0; intValue13 < list2.size(); intValue13++) {
         Vec3d vec3d25 = intValue13 < list.size() ? this.resolve9((Vec3d)list.get(intValue13), (Vec3d)list2.get(intValue13), f) : (Vec3d)list2.get(intValue13);
         arrayList4.add(vec3d25);
      }

      return arrayList4;
   }

   private Vec3d resolve9(Vec3d vec3d, Vec3d vec3d2, float f) {
      return new Vec3d(MathHelper.lerp(f, vec3d.x, vec3d2.x), MathHelper.lerp(f, vec3d.y, vec3d2.y), MathHelper.lerp(f, vec3d.z, vec3d2.z));
   }

   private Vec3d resolve10(Vec3d vec3d, Vec3d vec3d2, float f, double d) {
      return vec3d.squaredDistanceTo(vec3d2) > d ? vec3d2 : this.resolve9(vec3d, vec3d2, f);
   }

   private Vec3d resolve11(Vec3d vec3d, Vec3d vec3d2, float f, double d) {
      if (vec3d == null) {
         return vec3d2;
      } else if (vec3d2 == null) {
         return null;
      } else {
         return vec3d.squaredDistanceTo(vec3d2) > d ? vec3d2 : this.resolve9(vec3d, vec3d2, f);
      }
   }

   private Box resolve12(Predictions.PredictionsEntry predictionsEntry11, Predictions.PredictionsEntry predictionsEntry12, float f) {
      if (predictionsEntry12.targetBox() == null) {
         return null;
      } else if (predictionsEntry11.targetBox() != null && predictionsEntry11.hitEntity() != null && predictionsEntry12.hitEntity() != null) {
         if (predictionsEntry11.hitEntity().getId() != predictionsEntry12.hitEntity().getId()) {
            return predictionsEntry12.targetBox();
         } else {
            return this.measure(predictionsEntry11.targetBox(), predictionsEntry12.targetBox()) > 16.0
               ? predictionsEntry12.targetBox()
               : this.resolve13(predictionsEntry11.targetBox(), predictionsEntry12.targetBox(), f);
         }
      } else {
         return predictionsEntry12.targetBox();
      }
   }

   private Box resolve13(Box box, Box box2, float f) {
      return new Box(
         MathHelper.lerp(f, box.minX, box2.minX),
         MathHelper.lerp(f, box.minY, box2.minY),
         MathHelper.lerp(f, box.minZ, box2.minZ),
         MathHelper.lerp(f, box.maxX, box2.maxX),
         MathHelper.lerp(f, box.maxY, box2.maxY),
         MathHelper.lerp(f, box.maxZ, box2.maxZ)
      );
   }

   private double measure(Box box, Box box2) {
      double doubleValue11 = (box.minX + box.maxX - box2.minX - box2.maxX) * 0.5;
      double doubleValue12 = (box.minY + box.maxY - box2.minY - box2.maxY) * 0.5;
      double doubleValue13 = (box.minZ + box.maxZ - box2.minZ - box2.maxZ) * 0.5;
      return doubleValue11 * doubleValue11 + doubleValue12 * doubleValue12 + doubleValue13 * doubleValue13;
   }

   private Box resolve14(Entity entity, float f) {
      Vec3d vec3d26 = entity.getLerpedPos(f);
      Vec3d vec3d27 = entity.getPos();
      return entity.getBoundingBox().offset(vec3d26.x - vec3d27.x, vec3d26.y - vec3d27.y, vec3d26.z - vec3d27.z);
   }

   private Vec3d resolve15(BlockHitResult blockHitResult) {
      if (blockHitResult != null && blockHitResult.getType() != Type.MISS) {
         BlockPos blockPos = blockHitResult.getBlockPos();
         return new Vec3d(blockPos.getX(), blockPos.getY(), blockPos.getZ());
      } else {
         return null;
      }
   }

   private void invoke3() {
      this.items.clear();
      this.valuesByKey.clear();
   }

   private Vec3d resolve16(Vec3d vec3d, double d, double e) {
      return vec3d.subtract(0.0, d, 0.0).multiply(e);
   }

   private Vec3d resolve17(Vec3d vec3d, double d, double e) {
      return vec3d.multiply(e).subtract(0.0, d, 0.0);
   }

   private void invoke4(VertexConsumer vertexConsumer, Matrix4f matrix4f, Vec3d vec3d, List<Vec3d> list, int i, boolean bl) {
      if (list.size() >= 2) {
         float floatValue13 = this.measure6() * 0.3125F;
         this.invoke6(vertexConsumer, matrix4f, vec3d, list, i, bl, 0.072F, 0.024F, 0.56F, floatValue13 + 0.23F, 0.64F, 1.0F);
         this.invoke6(vertexConsumer, matrix4f, vec3d, list, i, bl, 0.042F, 0.014F, 0.94F, floatValue13 + 0.37F, 1.0F, 1.0F);
      }
   }

   private void invoke5(VertexConsumer vertexConsumer, Matrix4f matrix4f, Vec3d vec3d, List<Vec3d> list, int i, boolean bl) {
      if (list.size() >= 2) {
         float floatValue14 = this.measure6() * 0.3125F;
         this.invoke6(vertexConsumer, matrix4f, vec3d, list, i, bl, 0.235F, 0.066F, 0.16F, floatValue14, 0.25F, 0.0F);
         this.invoke6(vertexConsumer, matrix4f, vec3d, list, i, bl, 0.126F, 0.036F, 0.34F, floatValue14 + 0.19F, 0.58F, 0.0F);
      }
   }

   private void invoke6(
      VertexConsumer vertexConsumer, Matrix4f matrix4f, Vec3d vec3d, List<Vec3d> list, int i, boolean bl, float f, float g, float h, float j, float k, float l
   ) {
      int intValue14 = list.size();
      int intValue15 = intValue14 - 1;
      int intValue16 = i >> 16 & 0xFF;
      int intValue17 = i >> 8 & 0xFF;
      int intValue18 = i & 0xFF;
      int intValue19 = Math.max(120, i >>> 24 & 0xFF);
      double doubleValue14 = vec3d.x;
      double doubleValue15 = vec3d.y;
      double doubleValue16 = vec3d.z;

      for (int intValue20 = 0; intValue20 < intValue15; intValue20++) {
         Vec3d vec3d28 = (Vec3d)list.get(intValue20);
         Vec3d vec3d29 = (Vec3d)list.get(intValue20 + 1);
         double doubleValue17 = vec3d28.x - doubleValue14;
         double doubleValue18 = vec3d28.y - doubleValue15;
         double doubleValue19 = vec3d28.z - doubleValue16;
         double doubleValue20 = vec3d29.x - doubleValue14;
         double doubleValue21 = vec3d29.y - doubleValue15;
         double doubleValue22 = vec3d29.z - doubleValue16;
         double doubleValue23 = doubleValue20 - doubleValue17;
         double doubleValue24 = doubleValue21 - doubleValue18;
         double doubleValue25 = doubleValue22 - doubleValue19;
         double doubleValue26 = Math.sqrt(doubleValue23 * doubleValue23 + doubleValue24 * doubleValue24 + doubleValue25 * doubleValue25);
         if (!(doubleValue26 <= 1.0E-5)) {
            double doubleValue27 = doubleValue23 / doubleValue26;
            double doubleValue28 = doubleValue24 / doubleValue26;
            double doubleValue29 = doubleValue25 / doubleValue26;
            double doubleValue30 = Math.abs(doubleValue28) < 0.92 ? 0.0 : 1.0;
            double doubleValue31 = Math.abs(doubleValue28) < 0.92 ? 1.0 : 0.0;
            double doubleValue32 = 0.0;
            double doubleValue33 = doubleValue31 * doubleValue29 - doubleValue32 * doubleValue28;
            double doubleValue34 = doubleValue32 * doubleValue27 - doubleValue30 * doubleValue29;
            double doubleValue35 = doubleValue30 * doubleValue28 - doubleValue31 * doubleValue27;
            double doubleValue36 = Math.sqrt(doubleValue33 * doubleValue33 + doubleValue34 * doubleValue34 + doubleValue35 * doubleValue35);
            if (doubleValue36 <= 1.0E-5) {
               doubleValue33 = 1.0;
               doubleValue34 = 0.0;
               doubleValue35 = 0.0;
            } else {
               doubleValue33 /= doubleValue36;
               doubleValue34 /= doubleValue36;
               doubleValue35 /= doubleValue36;
            }

            double doubleValue37 = doubleValue28 * doubleValue35 - doubleValue29 * doubleValue34;
            double doubleValue38 = doubleValue29 * doubleValue33 - doubleValue27 * doubleValue35;
            double doubleValue39 = doubleValue27 * doubleValue34 - doubleValue28 * doubleValue33;
            float floatValue15 = (float)intValue20 / intValue15;
            float floatValue16 = (float)(intValue20 + 1) / intValue15;
            float floatValue17 = this.measure2(floatValue15, f, g, bl);
            float floatValue18 = this.measure2(floatValue16, f, g, bl);
            floatValue17 *= 1.0F + 0.085F * (float)Math.sin((floatValue15 * 2.7F - j * 3.8F + k * 0.31F) * Math.PI * 2.0);
            floatValue18 *= 1.0F + 0.085F * (float)Math.sin((floatValue16 * 2.7F - j * 3.8F + k * 0.31F) * Math.PI * 2.0);

            for (int intValue21 = 0; intValue21 < 10; intValue21++) {
               float floatValue19 = intValue21 / 10.0F;
               float floatValue20 = (intValue21 + 1) / 10.0F;
               if (intValue21 == 9) {
                  floatValue20 = 0.999F;
               }

               double doubleValue40 = (floatValue19 + j * 0.07F) * Math.PI * 2.0;
               double doubleValue41 = (floatValue20 + j * 0.07F) * Math.PI * 2.0;
               double doubleValue42 = Math.cos(doubleValue40);
               double doubleValue43 = Math.sin(doubleValue40);
               double doubleValue44 = Math.cos(doubleValue41);
               double doubleValue45 = Math.sin(doubleValue41);
               double doubleValue46 = doubleValue33 * doubleValue42 + doubleValue37 * doubleValue43;
               double doubleValue47 = doubleValue34 * doubleValue42 + doubleValue38 * doubleValue43;
               double doubleValue48 = doubleValue35 * doubleValue42 + doubleValue39 * doubleValue43;
               double doubleValue49 = doubleValue33 * doubleValue44 + doubleValue37 * doubleValue45;
               double doubleValue50 = doubleValue34 * doubleValue44 + doubleValue38 * doubleValue45;
               double doubleValue51 = doubleValue35 * doubleValue44 + doubleValue39 * doubleValue45;
               this.invoke7(
                  vertexConsumer,
                  matrix4f,
                  doubleValue17 + doubleValue46 * floatValue17,
                  doubleValue18 + doubleValue47 * floatValue17,
                  doubleValue19 + doubleValue48 * floatValue17,
                  intValue16,
                  intValue17,
                  intValue18,
                  intValue19,
                  floatValue15,
                  l + floatValue19,
                  j,
                  h,
                  k,
                  doubleValue46,
                  doubleValue47,
                  doubleValue48
               );
               this.invoke7(
                  vertexConsumer,
                  matrix4f,
                  doubleValue17 + doubleValue49 * floatValue17,
                  doubleValue18 + doubleValue50 * floatValue17,
                  doubleValue19 + doubleValue51 * floatValue17,
                  intValue16,
                  intValue17,
                  intValue18,
                  intValue19,
                  floatValue15,
                  l + floatValue20,
                  j,
                  h,
                  k,
                  doubleValue49,
                  doubleValue50,
                  doubleValue51
               );
               this.invoke7(
                  vertexConsumer,
                  matrix4f,
                  doubleValue20 + doubleValue49 * floatValue18,
                  doubleValue21 + doubleValue50 * floatValue18,
                  doubleValue22 + doubleValue51 * floatValue18,
                  intValue16,
                  intValue17,
                  intValue18,
                  intValue19,
                  floatValue16,
                  l + floatValue20,
                  j,
                  h,
                  k,
                  doubleValue49,
                  doubleValue50,
                  doubleValue51
               );
               this.invoke7(
                  vertexConsumer,
                  matrix4f,
                  doubleValue20 + doubleValue46 * floatValue18,
                  doubleValue21 + doubleValue47 * floatValue18,
                  doubleValue22 + doubleValue48 * floatValue18,
                  intValue16,
                  intValue17,
                  intValue18,
                  intValue19,
                  floatValue16,
                  l + floatValue19,
                  j,
                  h,
                  k,
                  doubleValue46,
                  doubleValue47,
                  doubleValue48
               );
            }
         }
      }
   }

   private void invoke7(
      VertexConsumer vertexConsumer,
      Matrix4f matrix4f,
      double d,
      double e,
      double f,
      int i,
      int j,
      int k,
      int l,
      float g,
      float h,
      float m,
      float n,
      float o,
      double p,
      double q,
      double r
   ) {
      float floatValue21 = this.measure3(g);
      int intValue22 = this.compute2(i, 255, o * 0.18F);
      int intValue23 = this.compute2(j, 255, o * 0.14F);
      int intValue24 = this.compute2(k, 255, o * 0.12F);
      int intValue25 = MathHelper.clamp(Math.round(l * n * floatValue21), 0, 255);
      vertexConsumer.vertex(matrix4f, (float)d, (float)e, (float)f)
         .texture(g + m * 0.28F, h)
         .color(intValue22, intValue23, intValue24, intValue25)
         .normal((float)p, (float)q, (float)r);
   }

   private void invoke8(VertexConsumer vertexConsumer, Matrix4f matrix4f, Vec3d vec3d, BlockHitResult blockHitResult, Vec3d vec3d2, int i) {
      if (blockHitResult != null && blockHitResult.getType() != Type.MISS) {
         Direction direction = blockHitResult.getSide();
         Vec3d vec3d30 = vec3d2 != null ? vec3d2 : blockHitResult.getPos();
         double doubleValue52 = direction.getOffsetX();
         double doubleValue53 = direction.getOffsetY();
         double doubleValue54 = direction.getOffsetZ();
         double doubleValue55 = vec3d30.x - vec3d.x + doubleValue52 * 0.01;
         double doubleValue56 = vec3d30.y - vec3d.y + doubleValue53 * 0.01;
         double doubleValue57 = vec3d30.z - vec3d.z + doubleValue54 * 0.01;
         double doubleValue58;
         double doubleValue59;
         double doubleValue60;
         double doubleValue61;
         double doubleValue62;
         double doubleValue63;
         if (direction.getAxis() == Axis.Y) {
            doubleValue58 = 1.0;
            doubleValue59 = 0.0;
            doubleValue60 = 0.0;
            doubleValue61 = 0.0;
            doubleValue62 = 0.0;
            doubleValue63 = direction == Direction.DOWN ? -1.0 : 1.0;
         } else if (direction.getAxis() == Axis.X) {
            doubleValue58 = 0.0;
            doubleValue59 = 0.0;
            doubleValue60 = direction == Direction.WEST ? -1.0 : 1.0;
            doubleValue61 = 0.0;
            doubleValue62 = 1.0;
            doubleValue63 = 0.0;
         } else {
            doubleValue58 = direction == Direction.NORTH ? -1.0 : 1.0;
            doubleValue59 = 0.0;
            doubleValue60 = 0.0;
            doubleValue61 = 0.0;
            doubleValue62 = 1.0;
            doubleValue63 = 0.0;
         }

         float floatValue22 = this.measure6() * 0.454545F;
         int intValue26 = i >> 16 & 0xFF;
         int intValue27 = i >> 8 & 0xFF;
         int intValue28 = i & 0xFF;
         float floatValue23 = 0.92F + 0.08F * (float)Math.sin(floatValue22 * Math.PI * 2.0);
         this.invoke10(
            vertexConsumer,
            matrix4f,
            doubleValue55,
            doubleValue56,
            doubleValue57,
            doubleValue52,
            doubleValue53,
            doubleValue54,
            doubleValue58,
            doubleValue59,
            doubleValue60,
            doubleValue61,
            doubleValue62,
            doubleValue63,
            1.2F * floatValue23,
            intValue26,
            intValue27,
            intValue28,
            56,
            floatValue22,
            4.0F
         );
         this.invoke10(
            vertexConsumer,
            matrix4f,
            doubleValue55,
            doubleValue56,
            doubleValue57,
            doubleValue52,
            doubleValue53,
            doubleValue54,
            doubleValue58,
            doubleValue59,
            doubleValue60,
            doubleValue61,
            doubleValue62,
            doubleValue63,
            0.74F * floatValue23,
            this.compute2(intValue26, 255, 0.18F),
            this.compute2(intValue27, 255, 0.14F),
            this.compute2(intValue28, 255, 0.16F),
            100,
            floatValue22 + 0.27F,
            4.0F
         );
         this.invoke12(
            vertexConsumer,
            matrix4f,
            doubleValue55,
            doubleValue56,
            doubleValue57,
            doubleValue52,
            doubleValue53,
            doubleValue54,
            doubleValue58,
            doubleValue59,
            doubleValue60,
            doubleValue61,
            doubleValue62,
            doubleValue63,
            0.54F * floatValue23,
            0.3F * floatValue23,
            this.compute2(intValue26, 255, 0.32F),
            this.compute2(intValue27, 255, 0.26F),
            this.compute2(intValue28, 255, 0.28F),
            130,
            floatValue22,
            4.0F
         );
         this.invoke18(
            vertexConsumer,
            matrix4f,
            doubleValue55,
            doubleValue56,
            doubleValue57,
            doubleValue58,
            doubleValue59,
            doubleValue60,
            doubleValue61,
            doubleValue62,
            doubleValue63,
            0.62F * floatValue23,
            0.09F,
            intValue26,
            intValue27,
            intValue28,
            110,
            floatValue22 + 0.21F,
            0.78F,
            2.0F
         );
         this.invoke18(
            vertexConsumer,
            matrix4f,
            doubleValue55,
            doubleValue56,
            doubleValue57,
            doubleValue58,
            doubleValue59,
            doubleValue60,
            doubleValue61,
            doubleValue62,
            doubleValue63,
            0.33F * floatValue23,
            0.038F,
            this.compute2(intValue26, 255, 0.36F),
            this.compute2(intValue27, 255, 0.3F),
            this.compute2(intValue28, 255, 0.32F),
            200,
            floatValue22 + 0.46F,
            0.95F,
            2.0F
         );
      }
   }

   private void invoke9(VertexConsumer vertexConsumer, Matrix4f matrix4f, Vec3d vec3d, Box box, int i, int j, int k) {
      double doubleValue64 = (box.minX + box.maxX) * 0.5 - vec3d.x;
      double doubleValue65 = box.minY - vec3d.y + 0.035;
      double doubleValue66 = (box.minZ + box.maxZ) * 0.5 - vec3d.z;
      double doubleValue67 = box.maxY - box.minY;
      double doubleValue68 = Math.max(box.maxX - box.minX, box.maxZ - box.minZ) * 0.66 + 0.22;
      long longValue = System.currentTimeMillis();
      float floatValue24 = (float)(longValue % 1800L) / 1800.0F;
      this.invoke18(vertexConsumer, matrix4f, doubleValue64, doubleValue65, doubleValue66, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, (float)doubleValue68, 0.058F, i, j, k, 215, floatValue24, 1.0F, 2.0F);
      this.invoke18(
         vertexConsumer,
         matrix4f,
         doubleValue64,
         doubleValue65 + doubleValue67 * 0.56,
         doubleValue66,
         1.0,
         0.0,
         0.0,
         0.0,
         0.0,
         1.0,
         (float)(doubleValue68 * 0.86),
         0.04F,
         i,
         j,
         k,
         120,
         floatValue24 + 0.33F,
         0.62F,
         2.0F
      );
   }

   private void invoke10(
      VertexConsumer vertexConsumer,
      Matrix4f matrix4f,
      double d,
      double e,
      double f,
      double g,
      double h,
      double i,
      double j,
      double k,
      double l,
      double m,
      double n,
      double o,
      float p,
      int q,
      int r,
      int s,
      int t,
      float u,
      float v
   ) {
      for (int intValue29 = 0; intValue29 < 8; intValue29++) {
         float floatValue25 = intValue29 / 8.0F;
         float floatValue26 = (intValue29 + 1) / 8.0F;
         float floatValue27 = floatValue25;
         float floatValue28 = intValue29 == 7 ? 0.999F : floatValue26;
         double doubleValue69 = p * floatValue25;
         double doubleValue70 = p * floatValue26;
         int intValue30 = MathHelper.clamp(Math.round(t * (1.0F - floatValue25) * (1.0F - floatValue25)), 0, 255);
         int intValue31 = MathHelper.clamp(Math.round(t * (1.0F - floatValue26) * (1.0F - floatValue26)), 0, 255);

         for (int intValue32 = 0; intValue32 < 72; intValue32++) {
            float floatValue29 = intValue32 / 72.0F;
            float floatValue30 = (intValue32 + 1) / 72.0F;
            double doubleValue71 = floatValue29 * Math.PI * 2.0;
            double doubleValue72 = floatValue30 * Math.PI * 2.0;
            double doubleValue73 = Math.cos(doubleValue71);
            double doubleValue74 = Math.sin(doubleValue71);
            double doubleValue75 = Math.cos(doubleValue72);
            double doubleValue76 = Math.sin(doubleValue72);
            this.invoke11(vertexConsumer, matrix4f, d, e, f, g, h, i, j, k, l, m, n, o, doubleValue73, doubleValue74, doubleValue70, q, r, s, intValue31, floatValue29 + u * 0.18F, v + floatValue28);
            this.invoke11(vertexConsumer, matrix4f, d, e, f, g, h, i, j, k, l, m, n, o, doubleValue73, doubleValue74, doubleValue69, q, r, s, intValue30, floatValue29 + u * 0.18F, v + floatValue27);
            this.invoke11(vertexConsumer, matrix4f, d, e, f, g, h, i, j, k, l, m, n, o, doubleValue75, doubleValue76, doubleValue69, q, r, s, intValue30, floatValue30 + u * 0.18F, v + floatValue27);
            this.invoke11(vertexConsumer, matrix4f, d, e, f, g, h, i, j, k, l, m, n, o, doubleValue75, doubleValue76, doubleValue70, q, r, s, intValue31, floatValue30 + u * 0.18F, v + floatValue28);
         }
      }
   }

   private void invoke11(
      VertexConsumer vertexConsumer,
      Matrix4f matrix4f,
      double d,
      double e,
      double f,
      double g,
      double h,
      double i,
      double j,
      double k,
      double l,
      double m,
      double n,
      double o,
      double p,
      double q,
      double r,
      int s,
      int t,
      int u,
      int v,
      float w,
      float x
   ) {
      double doubleValue77 = d + (j * p + m * q) * r;
      double doubleValue78 = e + (k * p + n * q) * r;
      double doubleValue79 = f + (l * p + o * q) * r;
      vertexConsumer.vertex(matrix4f, (float)doubleValue77, (float)doubleValue78, (float)doubleValue79).texture(w, x).color(s, t, u, v).normal((float)g, (float)h, (float)i);
   }

   private void invoke12(
      VertexConsumer vertexConsumer,
      Matrix4f matrix4f,
      double d,
      double e,
      double f,
      double g,
      double h,
      double i,
      double j,
      double k,
      double l,
      double m,
      double n,
      double o,
      float p,
      float q,
      int r,
      int s,
      int t,
      int u,
      float v,
      float w
   ) {
      for (int intValue33 = 0; intValue33 < 6; intValue33++) {
         float floatValue31 = intValue33 / 6.0F;
         float floatValue32 = (intValue33 + 1) / 6.0F;
         float floatValue33 = floatValue31;
         float floatValue34 = intValue33 == 5 ? 0.999F : floatValue32;
         double doubleValue80 = p * floatValue31;
         double doubleValue81 = p * floatValue32;
         double doubleValue82 = q * (1.0 - floatValue31 * floatValue31);
         double doubleValue83 = q * (1.0 - floatValue32 * floatValue32);
         int intValue34 = MathHelper.clamp(Math.round(u * (1.0F - floatValue31 * 0.62F)), 0, 255);
         int intValue35 = MathHelper.clamp(Math.round(u * (1.0F - floatValue32 * 0.62F)), 0, 255);

         for (int intValue36 = 0; intValue36 < 72; intValue36++) {
            float floatValue35 = intValue36 / 72.0F;
            float floatValue36 = (intValue36 + 1) / 72.0F;
            double doubleValue84 = floatValue35 * Math.PI * 2.0;
            double doubleValue85 = floatValue36 * Math.PI * 2.0;
            double doubleValue86 = Math.cos(doubleValue84);
            double doubleValue87 = Math.sin(doubleValue84);
            double doubleValue88 = Math.cos(doubleValue85);
            double doubleValue89 = Math.sin(doubleValue85);
            this.invoke13(
               vertexConsumer, matrix4f, d, e, f, g, h, i, j, k, l, m, n, o, doubleValue86, doubleValue87, doubleValue81, doubleValue83, floatValue32, r, s, t, intValue35, floatValue35 + v * 0.26F, w + floatValue34
            );
            this.invoke13(
               vertexConsumer, matrix4f, d, e, f, g, h, i, j, k, l, m, n, o, doubleValue86, doubleValue87, doubleValue80, doubleValue82, floatValue31, r, s, t, intValue34, floatValue35 + v * 0.26F, w + floatValue33
            );
            this.invoke13(
               vertexConsumer, matrix4f, d, e, f, g, h, i, j, k, l, m, n, o, doubleValue88, doubleValue89, doubleValue80, doubleValue82, floatValue31, r, s, t, intValue34, floatValue36 + v * 0.26F, w + floatValue33
            );
            this.invoke13(
               vertexConsumer, matrix4f, d, e, f, g, h, i, j, k, l, m, n, o, doubleValue88, doubleValue89, doubleValue81, doubleValue83, floatValue32, r, s, t, intValue35, floatValue36 + v * 0.26F, w + floatValue34
            );
         }
      }
   }

   private void invoke13(
      VertexConsumer vertexConsumer,
      Matrix4f matrix4f,
      double d,
      double e,
      double f,
      double g,
      double h,
      double i,
      double j,
      double k,
      double l,
      double m,
      double n,
      double o,
      double p,
      double q,
      double r,
      double s,
      float t,
      int u,
      int v,
      int w,
      int x,
      float y,
      float z
   ) {
      double doubleValue90 = j * p + m * q;
      double doubleValue91 = k * p + n * q;
      double doubleValue92 = l * p + o * q;
      double doubleValue93 = d + doubleValue90 * r + g * s;
      double doubleValue94 = e + doubleValue91 * r + h * s;
      double doubleValue95 = f + doubleValue92 * r + i * s;
      double doubleValue96 = g * (1.0 - t * 0.32F) + doubleValue90 * t * 0.68F;
      double doubleValue97 = h * (1.0 - t * 0.32F) + doubleValue91 * t * 0.68F;
      double doubleValue98 = i * (1.0 - t * 0.32F) + doubleValue92 * t * 0.68F;
      double doubleValue99 = Math.sqrt(doubleValue96 * doubleValue96 + doubleValue97 * doubleValue97 + doubleValue98 * doubleValue98);
      if (doubleValue99 <= 1.0E-5) {
         doubleValue96 = g;
         doubleValue97 = h;
         doubleValue98 = i;
      } else {
         doubleValue96 /= doubleValue99;
         doubleValue97 /= doubleValue99;
         doubleValue98 /= doubleValue99;
      }

      vertexConsumer.vertex(matrix4f, (float)doubleValue93, (float)doubleValue94, (float)doubleValue95)
         .texture(y, z)
         .color(u, v, w, x)
         .normal((float)doubleValue96, (float)doubleValue97, (float)doubleValue98);
   }

   private void invoke14(VertexConsumer vertexConsumer, Matrix4f matrix4f, double d, double e, double f, double g, double h, double i, int j, int k, int l) {
      double doubleValue100 = 0.004;
      double doubleValue101 = d - doubleValue100;
      double doubleValue102 = e - doubleValue100;
      double doubleValue103 = f - doubleValue100;
      double doubleValue104 = g + doubleValue100;
      double doubleValue105 = h + doubleValue100;
      double doubleValue106 = i + doubleValue100;
      int intValue37 = this.compute2(j, 255, 0.3F);
      int intValue38 = this.compute2(k, 255, 0.26F);
      int intValue39 = this.compute2(l, 255, 0.26F);
      this.invoke15(vertexConsumer, matrix4f, doubleValue101, doubleValue102, doubleValue103, doubleValue104, doubleValue105, doubleValue106, 0.046F, j, k, l, 26);
      this.invoke15(vertexConsumer, matrix4f, doubleValue101, doubleValue102, doubleValue103, doubleValue104, doubleValue105, doubleValue106, 0.02F, j, k, l, 60);
      this.invoke15(vertexConsumer, matrix4f, doubleValue101, doubleValue102, doubleValue103, doubleValue104, doubleValue105, doubleValue106, 0.008F, intValue37, intValue38, intValue39, 180);
   }

   private void invoke15(
      VertexConsumer vertexConsumer, Matrix4f matrix4f, double d, double e, double f, double g, double h, double i, float j, int k, int l, int m, int n
   ) {
      this.invoke20(vertexConsumer, matrix4f, d, e, f, g, e, f, j, k, l, m, n, 3.0F);
      this.invoke20(vertexConsumer, matrix4f, g, e, f, g, e, i, j, k, l, m, n, 3.0F);
      this.invoke20(vertexConsumer, matrix4f, g, e, i, d, e, i, j, k, l, m, n, 3.0F);
      this.invoke20(vertexConsumer, matrix4f, d, e, i, d, e, f, j, k, l, m, n, 3.0F);
      this.invoke20(vertexConsumer, matrix4f, d, h, f, g, h, f, j, k, l, m, n, 3.0F);
      this.invoke20(vertexConsumer, matrix4f, g, h, f, g, h, i, j, k, l, m, n, 3.0F);
      this.invoke20(vertexConsumer, matrix4f, g, h, i, d, h, i, j, k, l, m, n, 3.0F);
      this.invoke20(vertexConsumer, matrix4f, d, h, i, d, h, f, j, k, l, m, n, 3.0F);
      this.invoke20(vertexConsumer, matrix4f, d, e, f, d, h, f, j, k, l, m, n, 3.0F);
      this.invoke20(vertexConsumer, matrix4f, g, e, f, g, h, f, j, k, l, m, n, 3.0F);
      this.invoke20(vertexConsumer, matrix4f, g, e, i, g, h, i, j, k, l, m, n, 3.0F);
      this.invoke20(vertexConsumer, matrix4f, d, e, i, d, h, i, j, k, l, m, n, 3.0F);
   }

   private void invoke16(
      VertexConsumer vertexConsumer, Matrix4f matrix4f, double d, double e, double f, double g, double h, double i, int j, int k, int l, int m
   ) {
      double doubleValue107 = g - d;
      double doubleValue108 = h - e;
      double doubleValue109 = i - f;
      double doubleValue110 = Math.min(Math.min(doubleValue107, doubleValue108), doubleValue109) * 0.42;
      if (doubleValue110 < 0.18) {
         doubleValue110 = 0.18;
      }

      if (doubleValue110 > 0.38) {
         doubleValue110 = 0.38;
      }

      float floatValue37 = 0.028F;
      this.invoke17(vertexConsumer, matrix4f, d, e, f, 1.0, 1.0, 1.0, doubleValue110, floatValue37, j, k, l, m);
      this.invoke17(vertexConsumer, matrix4f, g, e, f, -1.0, 1.0, 1.0, doubleValue110, floatValue37, j, k, l, m);
      this.invoke17(vertexConsumer, matrix4f, g, e, i, -1.0, 1.0, -1.0, doubleValue110, floatValue37, j, k, l, m);
      this.invoke17(vertexConsumer, matrix4f, d, e, i, 1.0, 1.0, -1.0, doubleValue110, floatValue37, j, k, l, m);
      this.invoke17(vertexConsumer, matrix4f, d, h, f, 1.0, -1.0, 1.0, doubleValue110, floatValue37, j, k, l, m);
      this.invoke17(vertexConsumer, matrix4f, g, h, f, -1.0, -1.0, 1.0, doubleValue110, floatValue37, j, k, l, m);
      this.invoke17(vertexConsumer, matrix4f, g, h, i, -1.0, -1.0, -1.0, doubleValue110, floatValue37, j, k, l, m);
      this.invoke17(vertexConsumer, matrix4f, d, h, i, 1.0, -1.0, -1.0, doubleValue110, floatValue37, j, k, l, m);
   }

   private void invoke17(
      VertexConsumer vertexConsumer,
      Matrix4f matrix4f,
      double d,
      double e,
      double f,
      double g,
      double h,
      double i,
      double j,
      float k,
      int l,
      int m,
      int n,
      int o
   ) {
      this.invoke20(vertexConsumer, matrix4f, d, e, f, d + g * j, e, f, k, l, m, n, o, 3.0F);
      this.invoke20(vertexConsumer, matrix4f, d, e, f, d, e + h * j, f, k, l, m, n, o, 3.0F);
      this.invoke20(vertexConsumer, matrix4f, d, e, f, d, e, f + i * j, k, l, m, n, o, 3.0F);
   }

   private void invoke18(
      VertexConsumer vertexConsumer,
      Matrix4f matrix4f,
      double d,
      double e,
      double f,
      double g,
      double h,
      double i,
      double j,
      double k,
      double l,
      float m,
      float n,
      int o,
      int p,
      int q,
      int r,
      float s,
      float t,
      float u
   ) {
      double doubleValue111 = Math.max(0.01, (double)(m - n * 0.5F));
      double doubleValue112 = m + n * 0.5F;
      double doubleValue113 = h * l - i * k;
      double doubleValue114 = i * j - g * l;
      double doubleValue115 = g * k - h * j;

      for (int intValue40 = 0; intValue40 < 72; intValue40++) {
         float floatValue38 = intValue40 / 72.0F;
         float floatValue39 = (intValue40 + 1) / 72.0F;
         double doubleValue116 = floatValue38 * Math.PI * 2.0;
         double doubleValue117 = floatValue39 * Math.PI * 2.0;
         double doubleValue118 = Math.cos(doubleValue116);
         double doubleValue119 = Math.sin(doubleValue116);
         double doubleValue120 = Math.cos(doubleValue117);
         double doubleValue121 = Math.sin(doubleValue117);
         int intValue41 = this.compute(r, floatValue38, s, t);
         int intValue42 = this.compute(r, floatValue39, s, t);
         this.invoke19(
            vertexConsumer, matrix4f, d, e, f, g, h, i, j, k, l, doubleValue118, doubleValue119, doubleValue112, o, p, q, intValue41, floatValue38 + s * 0.2F, u + 0.92F, doubleValue113, doubleValue114, doubleValue115
         );
         this.invoke19(
            vertexConsumer, matrix4f, d, e, f, g, h, i, j, k, l, doubleValue118, doubleValue119, doubleValue111, o, p, q, intValue41, floatValue38 + s * 0.2F, u + 0.08F, doubleValue113, doubleValue114, doubleValue115
         );
         this.invoke19(
            vertexConsumer, matrix4f, d, e, f, g, h, i, j, k, l, doubleValue120, doubleValue121, doubleValue111, o, p, q, intValue42, floatValue39 + s * 0.2F, u + 0.08F, doubleValue113, doubleValue114, doubleValue115
         );
         this.invoke19(
            vertexConsumer, matrix4f, d, e, f, g, h, i, j, k, l, doubleValue120, doubleValue121, doubleValue112, o, p, q, intValue42, floatValue39 + s * 0.2F, u + 0.92F, doubleValue113, doubleValue114, doubleValue115
         );
      }
   }

   private void invoke19(
      VertexConsumer vertexConsumer,
      Matrix4f matrix4f,
      double d,
      double e,
      double f,
      double g,
      double h,
      double i,
      double j,
      double k,
      double l,
      double m,
      double n,
      double o,
      int p,
      int q,
      int r,
      int s,
      float t,
      float u,
      double v,
      double w,
      double x
   ) {
      double doubleValue122 = d + (g * m + j * n) * o;
      double doubleValue123 = e + (h * m + k * n) * o;
      double doubleValue124 = f + (i * m + l * n) * o;
      vertexConsumer.vertex(matrix4f, (float)doubleValue122, (float)doubleValue123, (float)doubleValue124).texture(t, u).color(p, q, r, s).normal((float)v, (float)w, (float)x);
   }

   private int compute(int i, float f, float g, float h) {
      float floatValue40 = 0.5F + 0.5F * (float)Math.sin((f * 3.0F - g * 2.0F) * Math.PI * 2.0);
      return MathHelper.clamp(Math.round(i * h * (0.48F + floatValue40 * 0.52F)), 0, 255);
   }

   private void invoke20(
      VertexConsumer vertexConsumer,
      Matrix4f matrix4f,
      double d,
      double e,
      double f,
      double g,
      double h,
      double i,
      float j,
      int k,
      int l,
      int m,
      int n,
      float o
   ) {
      double doubleValue125 = g - d;
      double doubleValue126 = h - e;
      double doubleValue127 = i - f;
      double doubleValue128 = Math.sqrt(doubleValue125 * doubleValue125 + doubleValue126 * doubleValue126 + doubleValue127 * doubleValue127);
      if (!(doubleValue128 <= 1.0E-5)) {
         double doubleValue129 = doubleValue125 / doubleValue128;
         double doubleValue130 = doubleValue126 / doubleValue128;
         double doubleValue131 = doubleValue127 / doubleValue128;
         double doubleValue132 = Math.abs(doubleValue130) < 0.92 ? 0.0 : 1.0;
         double doubleValue133 = Math.abs(doubleValue130) < 0.92 ? 1.0 : 0.0;
         double doubleValue134 = 0.0;
         double doubleValue135 = doubleValue133 * doubleValue131 - doubleValue134 * doubleValue130;
         double doubleValue136 = doubleValue134 * doubleValue129 - doubleValue132 * doubleValue131;
         double doubleValue137 = doubleValue132 * doubleValue130 - doubleValue133 * doubleValue129;
         double doubleValue138 = Math.sqrt(doubleValue135 * doubleValue135 + doubleValue136 * doubleValue136 + doubleValue137 * doubleValue137);
         if (doubleValue138 <= 1.0E-5) {
            doubleValue135 = 1.0;
            doubleValue136 = 0.0;
            doubleValue137 = 0.0;
         } else {
            doubleValue135 /= doubleValue138;
            doubleValue136 /= doubleValue138;
            doubleValue137 /= doubleValue138;
         }

         double doubleValue139 = doubleValue130 * doubleValue137 - doubleValue131 * doubleValue136;
         double doubleValue140 = doubleValue131 * doubleValue135 - doubleValue129 * doubleValue137;
         double doubleValue141 = doubleValue129 * doubleValue136 - doubleValue130 * doubleValue135;
         double doubleValue142 = j * 0.5;

         for (int intValue43 = 0; intValue43 < 6; intValue43++) {
            float floatValue41 = intValue43 / 6.0F;
            float floatValue42 = (intValue43 + 1) / 6.0F;
            if (intValue43 == 5) {
               floatValue42 = 0.999F;
            }

            double doubleValue143 = floatValue41 * Math.PI * 2.0;
            double doubleValue144 = floatValue42 * Math.PI * 2.0;
            double doubleValue145 = Math.cos(doubleValue143);
            double doubleValue146 = Math.sin(doubleValue143);
            double doubleValue147 = Math.cos(doubleValue144);
            double doubleValue148 = Math.sin(doubleValue144);
            double doubleValue149 = doubleValue135 * doubleValue145 + doubleValue139 * doubleValue146;
            double doubleValue150 = doubleValue136 * doubleValue145 + doubleValue140 * doubleValue146;
            double doubleValue151 = doubleValue137 * doubleValue145 + doubleValue141 * doubleValue146;
            double doubleValue152 = doubleValue135 * doubleValue147 + doubleValue139 * doubleValue148;
            double doubleValue153 = doubleValue136 * doubleValue147 + doubleValue140 * doubleValue148;
            double doubleValue154 = doubleValue137 * doubleValue147 + doubleValue141 * doubleValue148;
            vertexConsumer.vertex(matrix4f, (float)(d + doubleValue149 * doubleValue142), (float)(e + doubleValue150 * doubleValue142), (float)(f + doubleValue151 * doubleValue142))
               .texture(0.0F, o + floatValue41)
               .color(k, l, m, n)
               .normal((float)doubleValue149, (float)doubleValue150, (float)doubleValue151);
            vertexConsumer.vertex(matrix4f, (float)(d + doubleValue152 * doubleValue142), (float)(e + doubleValue153 * doubleValue142), (float)(f + doubleValue154 * doubleValue142))
               .texture(0.0F, o + floatValue42)
               .color(k, l, m, n)
               .normal((float)doubleValue152, (float)doubleValue153, (float)doubleValue154);
            vertexConsumer.vertex(matrix4f, (float)(g + doubleValue152 * doubleValue142), (float)(h + doubleValue153 * doubleValue142), (float)(i + doubleValue154 * doubleValue142))
               .texture(1.0F, o + floatValue42)
               .color(k, l, m, n)
               .normal((float)doubleValue152, (float)doubleValue153, (float)doubleValue154);
            vertexConsumer.vertex(matrix4f, (float)(g + doubleValue149 * doubleValue142), (float)(h + doubleValue150 * doubleValue142), (float)(i + doubleValue151 * doubleValue142))
               .texture(1.0F, o + floatValue41)
               .color(k, l, m, n)
               .normal((float)doubleValue149, (float)doubleValue150, (float)doubleValue151);
         }
      }
   }

   private float measure2(float f, float g, float h, boolean bl) {
      float floatValue43 = (float)Math.pow(this.measure5(f), 0.72F);
      float floatValue44 = g + (h - g) * floatValue43;
      return bl ? floatValue44 : floatValue44 * (1.0F - floatValue43 * 0.36F);
   }

   private float measure3(float f) {
      return this.measure4(0.0F, 0.055F, f) * (1.0F - this.measure4(0.885F, 1.0F, f));
   }

   private float measure4(float f, float g, float h) {
      float floatValue45 = this.measure5((h - f) / Math.max(1.0E-5F, g - f));
      return floatValue45 * floatValue45 * (3.0F - 2.0F * floatValue45);
   }

   private float measure5(float f) {
      return Math.max(0.0F, Math.min(1.0F, f));
   }

   private int compute2(int i, int j, float f) {
      float floatValue46 = this.measure5(f);
      return MathHelper.clamp(Math.round(i + (j - i) * floatValue46), 0, 255);
   }

   private float measure6() {
      return (float)(System.nanoTime() - TIMESTAMP) * 1.0E-9F;
   }

   private void invoke21(RenderManager renderManager2, float f, float g, float h, Predictions.PredictionsEntry predictionsEntry13) {
      String text2 = this.resolve18(predictionsEntry13.ticks() / 20.0F);
      float floatValue47 = 25.0F;
      float floatValue48 = 3.0F;
      float floatValue49 = 3.0F;
      float floatValue50 = 22.0F;
      float floatValue51 = 3.0F;
      float floatValue52 = 6.0F;
      float floatValue53 = RenderManager.resolve7(FontRegistry.fontObject, text2, floatValue47).floatValue;
      int intValue44 = this.compute3(predictionsEntry13.icon());
      boolean flag11 = intValue44 > 0;
      float floatValue54 = flag11 ? floatValue50 + floatValue51 : 0.0F;
      float floatValue55 = floatValue48 * 2.0F + floatValue54 + floatValue53 + floatValue52;
      float floatValue56 = floatValue49 + Math.max(flag11 ? floatValue50 : 0.0F, floatValue47);
      float floatValue57 = floatValue56 / 2.0F;
      renderManager2.invoke56(f, g);
      renderManager2.invoke59(h, h);
      float floatValue58 = -floatValue55 / 2.0F;
      float floatValue59 = -floatValue56;
      this.invoke24(renderManager2, floatValue58, floatValue59, floatValue55, floatValue56, floatValue57, 111.0F);
      float floatValue60 = floatValue58 + floatValue48 + (flag11 ? 0.0F : floatValue52 / 2.0F);
      if (flag11) {
         float floatValue61 = floatValue58 + floatValue48;
         float floatValue62 = floatValue59 + (floatValue56 - floatValue50) / 2.0F;
         renderManager2.invoke56(floatValue61, floatValue62 + floatValue50);
         renderManager2.invoke59(1.0F, -1.0F);
         renderManager2.invoke7(intValue44, 0.0F, 0.0F, floatValue50, floatValue50);
         renderManager2.invoke64();
         renderManager2.invoke57();
         floatValue60 = floatValue61 + floatValue50 + floatValue51;
      }

      float floatValue63 = floatValue59 + floatValue49 + floatValue47 - 10.0F;
      int intValue45 = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute7(1, 1), 230);
      renderManager2.invoke69(FontRegistry.fontObject, floatValue60 + 1.0F, floatValue63 + 1.0F, floatValue47, text2, intValue45);
      renderManager2.invoke64();
      renderManager2.invoke57();
   }

   private boolean check2(Predictions.PredictionsEntry predictionsEntry14) {
      Identifier identifier4 = predictionsEntry14.icon();
      return identifier4 != null && identifier4.getPath().contains("ender_pearl");
   }

   private void invoke22(RenderManager renderManager3, float f, float g, float h, Predictions.PredictionsEntry predictionsEntry15) {
      String text3 = predictionsEntry15.ownerName();
      if (text3 != null && !text3.isEmpty() && !text3.equals("Unknown") && !text3.equals("You")) {
         float floatValue64 = 22.0F;
         float floatValue65 = 4.0F;
         float floatValue66 = 3.0F;
         float floatValue67 = 18.0F;
         float floatValue68 = 4.0F;
         float floatValue69 = RenderManager.resolve7(FontRegistry.fontObject, text3, floatValue64).floatValue;
         float floatValue70 = floatValue65 * 2.0F + floatValue67 + floatValue68 + floatValue69;
         float floatValue71 = floatValue66 + Math.max(floatValue67, floatValue64);
         float floatValue72 = floatValue71 / 2.0F;
         renderManager3.invoke56(f, g);
         renderManager3.invoke59(h, h);
         float floatValue73 = -floatValue70 / 2.0F;
         float floatValue74 = -floatValue71;
         this.invoke24(renderManager3, floatValue73, floatValue74, floatValue70, floatValue71, floatValue72, 111.0F);
         float floatValue75 = floatValue73 + floatValue65;
         float floatValue76 = floatValue74 + (floatValue71 - floatValue67) / 2.0F;
         this.invoke23(renderManager3, text3, floatValue75, floatValue76, floatValue67, 1.0F);
         float floatValue77 = floatValue75 + floatValue67 + floatValue68;
         float floatValue78 = floatValue74 + floatValue66 + floatValue64 - 10.0F;
         int intValue46 = RenderManager.RenderManagerState.compute24(RenderManager.RenderManagerState.compute7(1, 1), 230);
         renderManager3.invoke69(FontRegistry.fontObject, floatValue77 + 1.0F, floatValue78 + 1.0F, floatValue64, text3, intValue46);
         renderManager3.invoke64();
         renderManager3.invoke57();
      }
   }

   private void invoke23(RenderManager renderManager4, String string, float f, float g, float h, float i) {
      if (CLIENT.getNetworkHandler() != null) {
         PlayerListEntry playerListEntry = null;

         for (PlayerListEntry playerListEntry2 : CLIENT.getNetworkHandler().getPlayerList()) {
            if (playerListEntry2.getProfile().getName().equalsIgnoreCase(string)) {
               playerListEntry = playerListEntry2;
               break;
            }
         }

         if (playerListEntry != null) {
            try {
               Identifier identifier5 = playerListEntry.getSkinTextures().texture();
               AbstractTexture abstractTexture = CLIENT.getTextureManager().getTexture(identifier5);
               if (abstractTexture != null && abstractTexture.getGlTexture() instanceof GlTexture glTexture && glTexture.getGlId() > 0) {
                  int intValue47 = glTexture.getGlId();
                  GlStateManager._bindTexture(intValue47);
                  renderManager4.invoke65(i);
                  renderManager4.invoke12(intValue47, f, g, h, h, 0.125F, 0.125F, 0.25F, 0.25F, 3.0F);
                  renderManager4.invoke12(intValue47, f, g, h, h, 0.625F, 0.125F, 0.75F, 0.25F, 3.0F);
                  renderManager4.invoke66();
               }
            } catch (Throwable exception) {
            }
         }
      }
   }

   private void invoke24(RenderManager renderManager5, float f, float g, float h, float i, float j, float k) {
      float floatValue79 = k / 155.0F;
      int intValue48 = this.hudElement.compute(255.0F);
      int intValue49 = this.hudElement.compute5(floatValue79);
      renderManager5.invoke5(f, g, h, i, 12.0F, intValue48);
   }

   private String resolve18(float f) {
      String text4 = String.format(Locale.US, "%.1f", f).replace('.', ',');
      return text4 + " сек";
   }

   private int compute3(Identifier identifier) {
      if (identifier == null) {
         return -1;
      } else {
         TextureManager textureManager = CLIENT.getTextureManager();
         if (textureManager == null) {
            return -1;
         } else {
            AbstractTexture abstractTexture2 = textureManager.getTexture(identifier);
            if (abstractTexture2 == null) {
               return -1;
            } else if (abstractTexture2.getGlTexture() instanceof GlTexture glTexture2) {
               int intValue50 = glTexture2.getGlId();
               return intValue50 > 0 ? intValue50 : -1;
            } else {
               return -1;
            }
         }
      }
   }

   private Identifier resolve19(Item item) {
      if (item instanceof TridentItem) {
         return Identifier.of("minecraft", "textures/item/trident.png");
      } else if (item instanceof BowItem || item instanceof CrossbowItem) {
         return Identifier.of("minecraft", "textures/item/arrow.png");
      } else if (item instanceof ThrowablePotionItem) {
         return Identifier.of("minecraft", "textures/item/potion.png");
      } else if (item instanceof SnowballItem) {
         return Identifier.of("minecraft", "textures/item/snowball.png");
      } else if (item instanceof EggItem) {
         return Identifier.of("minecraft", "textures/item/egg.png");
      } else if (item instanceof ExperienceBottleItem) {
         return Identifier.of("minecraft", "textures/item/experience_bottle.png");
      } else {
         return item instanceof EnderPearlItem ? Identifier.of("minecraft", "textures/item/ender_pearl.png") : null;
      }
   }

   private Identifier resolve20(ProjectileEntity projectileEntity) {
      String text5 = Registries.ENTITY_TYPE.getId(projectileEntity.getType()).getPath();
      if (text5.contains("trident")) {
         return Identifier.of("minecraft", "textures/item/trident.png");
      } else if (text5.contains("snowball")) {
         return Identifier.of("minecraft", "textures/item/snowball.png");
      } else if (text5.contains("arrow")) {
         return Identifier.of("minecraft", "textures/item/arrow.png");
      } else if (text5.contains("potion")) {
         return Identifier.of("minecraft", "textures/item/potion.png");
      } else if (text5.contains("pearl")) {
         return Identifier.of("minecraft", "textures/item/ender_pearl.png");
      } else if (text5.contains("egg")) {
         return Identifier.of("minecraft", "textures/item/egg.png");
      } else {
         return text5.contains("experience_bottle") ? Identifier.of("minecraft", "textures/item/experience_bottle.png") : null;
      }
   }

   record PredictionsData(double speed, double gravity, float pitchOffset, boolean applyPhysicsBeforeMove) {
   }

   record PredictionsEntry(
      String key,
      List<Vec3d> path,
      Vec3d landingPos,
      Vec3d blockRenderPos,
      Box targetBox,
      int ticks,
      Entity hitEntity,
      BlockHitResult blockHit,
      String ownerName,
      Identifier icon,
      boolean isPreAim
   ) {
      Predictions.PredictionsEntry withRenderState(List<Vec3d> list, Vec3d vec3d, Vec3d vec3d2, Box box) {
         return new Predictions.PredictionsEntry(this.key, list, vec3d, vec3d2, box, this.ticks, this.hitEntity, this.blockHit, this.ownerName, this.icon, this.isPreAim);
      }

      static String resolveOwnerName(ProjectileEntity projectileEntity) {
         return projectileEntity.getOwner() instanceof PlayerEntity playerEntity2 ? playerEntity2.getName().getString() : "Unknown";
      }
   }
}
