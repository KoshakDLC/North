package ru.metaculture.protection;

import com.mojang.blaze3d.systems.CommandEncoder;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.textures.GpuTexture;
import com.mojang.blaze3d.textures.GpuTextureView;
import java.nio.FloatBuffer;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.SequencedMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gl.SimpleFramebuffer;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.LightmapTextureManager;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.VertexConsumers;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.client.render.entity.EntityRenderDispatcher;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.client.util.BufferAllocator;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.util.math.MatrixStack.Entry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.LightType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.lwjgl.system.MemoryStack;
import org.wild.mixin.acceser.EntityRenderDispatcherAccessor;

public final class EntityFramebufferCapture {
   private static final int MAX_BUFFER_SIZE = 1048576;
   private static final Logger LOGGER = LogManager.getLogger("EntityFramebufferCapture");
   private static final EntityFramebufferCapture INSTANCE = new EntityFramebufferCapture();
   private static final Predicate<Entity> ALL_ENTITIES = entity -> true;
   private volatile SimpleFramebuffer captureFramebuffer;
   private volatile SimpleFramebuffer taggedFramebuffer;
   private final Map<String, Predicate<Entity>> captureFilters = new ConcurrentHashMap<>();
   private final Map<String, Predicate<Entity>> tagFilters = new ConcurrentHashMap<>();
   private volatile boolean captureAllEntities;
   private volatile boolean captureReady;
   private volatile boolean taggedCaptureReady;
   private volatile boolean renderingCapturedEntity;
   private volatile boolean renderingTaggedEntity;
   private volatile boolean captureFrameActive;
   private volatile int captureWidth = -1;
   private volatile int captureHeight = -1;
   private volatile int currentFrameEntityCount;
   private volatile int previousFrameEntityCount;
   private volatile int capturingLivingEntityId = Integer.MIN_VALUE;
   private int directFramebufferId;
   private EntityFramebufferCapture.CaptureResources captureResources;
   private final BufferAllocator sharedBufferAllocator = new BufferAllocator(1048576);
   private final SequencedMap<RenderLayer, BufferAllocator> layerAllocators = new LinkedHashMap<>();

   private EntityFramebufferCapture() {
   }

   public static EntityFramebufferCapture getInstance() {
      return INSTANCE;
   }

   public void setCaptureAllEntities(boolean bl) {
      if (this.captureAllEntities != bl) {
         this.captureAllEntities = bl;
         this.resetIfDisabled();
      }
   }

   public void setCaptureFilter(String string, boolean bl, Predicate<Entity> predicate) {
      if (string == null || string.isBlank()) {
         throw new IllegalArgumentException("owner");
      } else if (!bl) {
         this.removeCaptureFilter(string);
      } else {
         Predicate predicate2 = predicate == null ? ALL_ENTITIES : predicate;
         if (this.captureFilters.get(string) != predicate2) {
            this.captureFilters.put(string, predicate2);
         }
      }
   }

   public void removeCaptureFilter(String string) {
      if (string != null && !string.isBlank()) {
         this.captureFilters.remove(string);
         this.resetIfDisabled();
      }
   }

   public void setTagFilter(String string, boolean bl, Predicate<Entity> predicate) {
      if (string == null || string.isBlank()) {
         throw new IllegalArgumentException("owner");
      } else if (bl && predicate != null) {
         if (this.tagFilters.get(string) != predicate) {
            this.tagFilters.put(string, predicate);
         }
      } else {
         this.removeTagFilter(string);
      }
   }

   public void removeTagFilter(String string) {
      if (string != null && !string.isBlank()) {
         this.tagFilters.remove(string);
         if (this.tagFilters.isEmpty()) {
            this.taggedCaptureReady = false;
         }
      }
   }

   private boolean hasTagFilters() {
      return !this.tagFilters.isEmpty();
   }

   private boolean matchesTagFilter(Entity entity) {
      if (this.tagFilters.isEmpty()) {
         return false;
      } else {
         for (Predicate predicate3 : this.tagFilters.values()) {
            try {
               if (predicate3.test(entity)) {
                  return true;
               }
            } catch (RuntimeException exception) {
               LOGGER.warn("Entity tag filter failed for {}", entity.getName().getString(), exception);
            }
         }

         return false;
      }
   }

   public boolean isCaptureEnabled() {
      return this.captureAllEntities || !this.captureFilters.isEmpty();
   }

   public boolean isCaptureTextureReady() {
      return this.isCaptureEnabled() && this.captureReady && this.captureWidth > 0 && this.captureHeight > 0 && isFramebufferUsable(this.captureFramebuffer);
   }

   public int getCaptureColorTextureId() {
      return this.isCaptureTextureReady() ? getColorTextureId(this.captureFramebuffer) : 0;
   }

   public int getCaptureDepthTextureId() {
      return this.isCaptureTextureReady() ? getDepthTextureId(this.captureFramebuffer) : 0;
   }

   public boolean isTaggedCaptureTextureReady() {
      return this.isCaptureEnabled() && this.hasTagFilters() && this.taggedCaptureReady && isFramebufferUsable(this.taggedFramebuffer);
   }

   public int getTaggedCaptureColorTextureId() {
      return this.isTaggedCaptureTextureReady() ? getColorTextureId(this.taggedFramebuffer) : 0;
   }

   public int getTaggedCaptureDepthTextureId() {
      return this.isTaggedCaptureTextureReady() ? getDepthTextureId(this.taggedFramebuffer) : 0;
   }

   public boolean isCapturePassActive() {
      return this.renderingCapturedEntity || this.renderingTaggedEntity;
   }

   public boolean isRenderingCapturedEntity() {
      return this.renderingCapturedEntity;
   }

   public void beginCaptureFrame(WorldRenderer worldRenderer, RenderTickCounter renderTickCounter, Camera camera) {
      if (!this.isCaptureEnabled()) {
         this.resetFrameState();
      } else {
         Objects.requireNonNull(worldRenderer, "worldRenderer");
         Objects.requireNonNull(renderTickCounter, "tickCounter");
         MinecraftClient client = MinecraftClient.getInstance();
         if (client == null || client.world == null || client.gameRenderer == null) {
            this.resetFrameState();
         } else if (!client.gameRenderer.isRenderingPanorama() && camera != null) {
            Framebuffer framebuffer = client.getFramebuffer();
            if (framebuffer == null) {
               this.resetFrameState();
            } else {
               Window window = client.getWindow();
               int intValue = window != null ? window.getFramebufferWidth() : framebuffer.textureWidth;
               int intValue2 = window != null ? window.getFramebufferHeight() : framebuffer.textureHeight;
               if (intValue <= 0 || intValue2 <= 0) {
                  this.resetFrameState();
                  this.deleteCaptureFramebuffer();
                  this.captureWidth = -1;
                  this.captureHeight = -1;
               } else if (!this.ensureCaptureFramebuffer(intValue, intValue2)) {
                  this.resetFrameState();
               } else {
                  SimpleFramebuffer simpleFramebuffer = this.captureFramebuffer;
                  if (simpleFramebuffer == null) {
                     this.resetFrameState();
                  } else {
                     GpuTextureView gpuTextureView = simpleFramebuffer.getColorAttachmentView();
                     if (gpuTextureView != null && !gpuTextureView.isClosed()) {
                        GpuTextureView gpuTextureView2 = simpleFramebuffer.getDepthAttachmentView();
                        if (!this.clearFramebufferDirectly(simpleFramebuffer)) {
                           CommandEncoder commandEncoder = RenderSystem.getDevice().createCommandEncoder();
                           GpuTexture gpuTexture = gpuTextureView.texture();
                           if (gpuTextureView2 != null && !gpuTextureView2.isClosed()) {
                              commandEncoder.clearColorAndDepthTextures(gpuTexture, 0, gpuTextureView2.texture(), 1.0);
                           } else {
                              commandEncoder.clearColorTexture(gpuTexture, 0);
                           }
                        }

                        this.releaseCaptureResources();

                        try {
                           this.sharedBufferAllocator.clear();
                           this.layerAllocators.values().forEach(BufferAllocator::clear);
                           this.captureResources = new EntityFramebufferCapture.CaptureResources(this.sharedBufferAllocator, this.layerAllocators);
                        } catch (RuntimeException exception2) {
                           LOGGER.warn("Failed to allocate capture resources", exception2);
                           this.resetFrameState();
                           return;
                        }

                        this.prepareTaggedFramebuffer(intValue, intValue2);
                        this.captureReady = false;
                        this.captureFrameActive = true;
                        this.currentFrameEntityCount = 0;
                     } else {
                        this.resetFrameState();
                     }
                  }
               }
            }
         } else {
            this.resetFrameState();
         }
      }
   }

   private void prepareTaggedFramebuffer(int i, int j) {
      this.taggedCaptureReady = false;
      if (!this.hasTagFilters()) {
         this.deleteTaggedFramebuffer();
      } else if (this.ensureTaggedFramebuffer(i, j)) {
         SimpleFramebuffer simpleFramebuffer2 = this.taggedFramebuffer;
         if (simpleFramebuffer2 != null) {
            GpuTextureView gpuTextureView3 = simpleFramebuffer2.getColorAttachmentView();
            if (gpuTextureView3 != null && !gpuTextureView3.isClosed()) {
               GpuTextureView gpuTextureView4 = simpleFramebuffer2.getDepthAttachmentView();
               if (!this.clearFramebufferDirectly(simpleFramebuffer2)) {
                  CommandEncoder commandEncoder2 = RenderSystem.getDevice().createCommandEncoder();
                  if (gpuTextureView4 != null && !gpuTextureView4.isClosed()) {
                     commandEncoder2.clearColorAndDepthTextures(gpuTextureView3.texture(), 0, gpuTextureView4.texture(), 1.0);
                  } else {
                     commandEncoder2.clearColorTexture(gpuTextureView3.texture(), 0);
                  }
               }
            }
         }
      }
   }

   public void endCaptureFrame() {
      EntityFramebufferCapture.CaptureResources captureResources = this.captureResources;
      boolean flag = false ;

      label102: {
         label101: {
            try {
               flag = true;
               if (captureResources != null) {
                  try {
                     this.flushToCaptureFramebuffer(captureResources);
                  } finally {
                     captureResources.close();
                  }

                  flag = false;
               } else {
                  flag = false;
               }
               break label101;
            } catch (RuntimeException exception3) {
               LOGGER.warn("Failed to finalize capture frame", exception3);
               this.captureReady = false;
               flag = false;
            } finally {
               if (flag) {
                  this.captureResources = null;
                  this.captureFrameActive = false;
                  this.previousFrameEntityCount = this.currentFrameEntityCount;
               }
            }

            this.captureResources = null;
            this.captureFrameActive = false;
            this.previousFrameEntityCount = this.currentFrameEntityCount;
            break label102;
         }

         this.captureResources = null;
         this.captureFrameActive = false;
         this.previousFrameEntityCount = this.currentFrameEntityCount;
      }

      this.captureReady = this.captureReady && isFramebufferUsable(this.captureFramebuffer);
      this.taggedCaptureReady = this.taggedCaptureReady && isFramebufferUsable(this.taggedFramebuffer);
   }

   public void captureEntity(Entity entity, double d, double e, double f, float g, MatrixStack matrixStack) {
      if (this.isCaptureEnabled() && this.captureFrameActive && !this.renderingCapturedEntity && !this.renderingTaggedEntity && !IrisCompatibility.isRenderingShadowPass() && entity != null) {
         if (entity instanceof LivingEntity) {
            if (this.matchesTagFilter(entity)) {
               this.captureTaggedEntity(entity, d, e, f, g, matrixStack);
            }
         } else if (this.matchesCaptureFilter(entity)) {
            Objects.requireNonNull(matrixStack, "matrices");
            EntityFramebufferCapture.CaptureResources captureResources2 = this.captureResources;
            SimpleFramebuffer simpleFramebuffer3 = this.captureFramebuffer;
            if (captureResources2 != null && simpleFramebuffer3 != null && this.captureWidth > 0 && this.captureHeight > 0) {
               GpuTextureView gpuTextureView5 = simpleFramebuffer3.getColorAttachmentView();
               if (gpuTextureView5 != null && !gpuTextureView5.isClosed()) {
                  GpuTextureView gpuTextureView6 = simpleFramebuffer3.getDepthAttachmentView();
                  MinecraftClient client2 = MinecraftClient.getInstance();
                  if (client2 != null && client2.world != null) {
                     EntityRenderDispatcher entityRenderDispatcher = client2.getEntityRenderDispatcher();
                     if (entityRenderDispatcher != null) {
                        MatrixStack matrices = captureResources2.copyMatrixStack(matrixStack);
                        if (matrices != null) {
                           Vec3d vec3d = entity.getLerpedPos(g);
                           double doubleValue = vec3d.x - d;
                           double doubleValue2 = vec3d.y - e;
                           double doubleValue3 = vec3d.z - f;
                           BlockPos blockPos = BlockPos.ofFloored(vec3d);
                           int intValue3 = client2.world.getLightLevel(LightType.BLOCK, blockPos);
                           int intValue4 = client2.world.getLightLevel(LightType.SKY, blockPos);
                           int intValue5 = LightmapTextureManager.pack(intValue4, intValue3);
                           GpuTextureView gpuTextureView7 = RenderSystem.outputColorTextureOverride;
                           GpuTextureView gpuTextureView8 = RenderSystem.outputDepthTextureOverride;
                           RenderSystem.outputColorTextureOverride = gpuTextureView5;
                           RenderSystem.outputDepthTextureOverride = gpuTextureView6;
                           EntityRenderDispatcherAccessor entityRenderDispatcherAccessor = entityRenderDispatcher instanceof EntityRenderDispatcherAccessor entityRenderDispatcherAccessor2 ? entityRenderDispatcherAccessor2 : null;
                           boolean flag2 = entityRenderDispatcherAccessor != null;
                           boolean flag3 = false;
                           if (entityRenderDispatcherAccessor != null) {
                              flag3 = entityRenderDispatcherAccessor.night$getRenderShadows();
                              entityRenderDispatcherAccessor.night$setRenderShadows(false);
                           }

                           this.renderingCapturedEntity = true;
                           boolean flag4 = false ;

                           label252: {
                              try {
                                 try {
                                    flag4 = true;
                                    entityRenderDispatcher.render(entity, doubleValue, doubleValue2, doubleValue3, g, matrices, captureResources2.getProvider(), intValue5);
                                    captureResources2.markPending();
                                    this.currentFrameEntityCount++;
                                    this.captureReady = true;
                                 } finally {
                                    captureResources2.flush();
                                 }

                                 flag4 = false;
                                 break label252;
                              } catch (RuntimeException exception4) {
                                 LOGGER.warn("Failed to visuals entity {} into capture framebuffer", entity.getName().getString(), exception4);
                                 this.captureReady = false;
                                 flag4 = false;
                              } finally {
                                 if (flag4) {
                                    this.renderingCapturedEntity = false;
                                    if (flag2) {
                                       entityRenderDispatcherAccessor.night$setRenderShadows(flag3);
                                    }

                                    RenderSystem.outputColorTextureOverride = gpuTextureView7;
                                    RenderSystem.outputDepthTextureOverride = gpuTextureView8;
                                 }
                              }

                              this.renderingCapturedEntity = false;
                              if (flag2) {
                                 entityRenderDispatcherAccessor.night$setRenderShadows(flag3);
                              }

                              RenderSystem.outputColorTextureOverride = gpuTextureView7;
                              RenderSystem.outputDepthTextureOverride = gpuTextureView8;
                              return;
                           }

                           this.renderingCapturedEntity = false;
                           if (flag2) {
                              entityRenderDispatcherAccessor.night$setRenderShadows(flag3);
                           }

                           RenderSystem.outputColorTextureOverride = gpuTextureView7;
                           RenderSystem.outputDepthTextureOverride = gpuTextureView8;
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void captureTaggedEntity(Entity entity, double d, double e, double f, float g, MatrixStack matrixStack) {
      EntityFramebufferCapture.CaptureResources captureResources3 = this.captureResources;
      SimpleFramebuffer simpleFramebuffer4 = this.taggedFramebuffer;
      if (captureResources3 != null && simpleFramebuffer4 != null && isFramebufferUsable(simpleFramebuffer4) && matrixStack != null) {
         GpuTextureView gpuTextureView9 = simpleFramebuffer4.getColorAttachmentView();
         if (gpuTextureView9 != null && !gpuTextureView9.isClosed()) {
            GpuTextureView gpuTextureView10 = simpleFramebuffer4.getDepthAttachmentView();
            MinecraftClient client3 = MinecraftClient.getInstance();
            if (client3 != null && client3.world != null) {
               EntityRenderDispatcher entityRenderDispatcher2 = client3.getEntityRenderDispatcher();
               if (entityRenderDispatcher2 != null) {
                  MatrixStack matrices2 = captureResources3.copyMatrixStack(matrixStack);
                  if (matrices2 != null) {
                     double doubleValue4 = MathHelper.lerp(g, entity.lastRenderX, entity.getX());
                     double doubleValue5 = MathHelper.lerp(g, entity.lastRenderY, entity.getY());
                     double doubleValue6 = MathHelper.lerp(g, entity.lastRenderZ, entity.getZ());
                     double doubleValue7 = doubleValue4 - d;
                     double doubleValue8 = doubleValue5 - e;
                     double doubleValue9 = doubleValue6 - f;
                     BlockPos blockPos2 = BlockPos.ofFloored(doubleValue4, doubleValue5, doubleValue6);
                     int intValue6 = client3.world.getLightLevel(LightType.BLOCK, blockPos2);
                     int intValue7 = client3.world.getLightLevel(LightType.SKY, blockPos2);
                     int intValue8 = LightmapTextureManager.pack(intValue7, intValue6);
                     GpuTextureView gpuTextureView11 = RenderSystem.outputColorTextureOverride;
                     GpuTextureView gpuTextureView12 = RenderSystem.outputDepthTextureOverride;
                     RenderSystem.outputColorTextureOverride = gpuTextureView9;
                     RenderSystem.outputDepthTextureOverride = gpuTextureView10;
                     EntityRenderDispatcherAccessor entityRenderDispatcherAccessor3 = entityRenderDispatcher2 instanceof EntityRenderDispatcherAccessor entityRenderDispatcherAccessor4 ? entityRenderDispatcherAccessor4 : null;
                     boolean flag5 = false;
                     if (entityRenderDispatcherAccessor3 != null) {
                        flag5 = entityRenderDispatcherAccessor3.night$getRenderShadows();
                        entityRenderDispatcherAccessor3.night$setRenderShadows(false);
                     }

                     this.renderingTaggedEntity = true;
                     boolean flag6 = false ;

                     label182: {
                        try {
                           try {
                              flag6 = true;
                              entityRenderDispatcher2.render(entity, doubleValue7, doubleValue8, doubleValue9, g, matrices2, captureResources3.getProvider(), intValue8);
                              captureResources3.markPending();
                              this.taggedCaptureReady = true;
                           } finally {
                              captureResources3.flush();
                           }

                           flag6 = false;
                           break label182;
                        } catch (RuntimeException exception5) {
                           LOGGER.warn("Failed to render tagged entity {} into capture framebuffer", entity.getName().getString(), exception5);
                           this.taggedCaptureReady = false;
                           flag6 = false;
                        } finally {
                           if (flag6) {
                              this.renderingTaggedEntity = false;
                              if (entityRenderDispatcherAccessor3 != null) {
                                 entityRenderDispatcherAccessor3.night$setRenderShadows(flag5);
                              }

                              RenderSystem.outputColorTextureOverride = gpuTextureView11;
                              RenderSystem.outputDepthTextureOverride = gpuTextureView12;
                           }
                        }

                        this.renderingTaggedEntity = false;
                        if (entityRenderDispatcherAccessor3 != null) {
                           entityRenderDispatcherAccessor3.night$setRenderShadows(flag5);
                        }

                        RenderSystem.outputColorTextureOverride = gpuTextureView11;
                        RenderSystem.outputDepthTextureOverride = gpuTextureView12;
                        return;
                     }

                     this.renderingTaggedEntity = false;
                     if (entityRenderDispatcherAccessor3 != null) {
                        entityRenderDispatcherAccessor3.night$setRenderShadows(flag5);
                     }

                     RenderSystem.outputColorTextureOverride = gpuTextureView11;
                     RenderSystem.outputDepthTextureOverride = gpuTextureView12;
                  }
               }
            }
         }
      }
   }

   public boolean isCaptureFrameReady() {
      return this.isCaptureEnabled() && this.captureFrameActive && this.captureWidth > 0 && this.captureHeight > 0 && isFramebufferUsable(this.captureFramebuffer);
   }

   public VertexConsumer captureLivingLayer(VertexConsumer vertexConsumer, RenderLayer renderLayer, LivingEntityRenderState livingEntityRenderState) {
      if (vertexConsumer != null
         && renderLayer != null
         && this.isCaptureEnabled()
         && this.captureFrameActive
         && !this.renderingCapturedEntity
         && !this.renderingTaggedEntity
         && !IrisCompatibility.isRenderingShadowPass()) {
         LivingEntity livingEntity = this.getCapturedLivingEntity(livingEntityRenderState);
         EntityFramebufferCapture.CaptureResources captureResources4 = this.captureResources;
         SimpleFramebuffer simpleFramebuffer5 = this.captureFramebuffer;
         if (livingEntity != null && captureResources4 != null && simpleFramebuffer5 != null) {
            GpuTextureView gpuTextureView13 = simpleFramebuffer5.getColorAttachmentView();
            if (gpuTextureView13 != null && !gpuTextureView13.isClosed()) {
               GpuTextureView gpuTextureView14 = RenderSystem.outputColorTextureOverride;
               GpuTextureView gpuTextureView15 = RenderSystem.outputDepthTextureOverride;
               RenderSystem.outputColorTextureOverride = gpuTextureView13;
               RenderSystem.outputDepthTextureOverride = simpleFramebuffer5.getDepthAttachmentView();

               VertexConsumer vertexConsumer2;
               try {
                  VertexConsumer vertexConsumer3 = captureResources4.getBuffer(renderLayer);
                  captureResources4.markPending();
                  this.capturingLivingEntityId = livingEntity.getId();
                  return VertexConsumers.union(vertexConsumer, vertexConsumer3);
               } catch (RuntimeException exception6) {
                  LOGGER.warn("Failed to prepare living layer {} for {}", renderLayer, livingEntity.getName().getString(), exception6);
                  vertexConsumer2 = vertexConsumer;
               } finally {
                  RenderSystem.outputColorTextureOverride = gpuTextureView14;
                  RenderSystem.outputDepthTextureOverride = gpuTextureView15;
               }

               return vertexConsumer2;
            } else {
               return vertexConsumer;
            }
         } else {
            return vertexConsumer;
         }
      } else {
         return vertexConsumer;
      }
   }

   public void renderFeatureIntoCapture(
      FeatureRenderer featureRenderer,
      MatrixStack matrixStack,
      VertexConsumerProvider vertexConsumerProvider,
      int i,
      LivingEntityRenderState livingEntityRenderState,
      float f,
      float g
   ) {
      LivingEntity livingEntity2 = this.getCapturedLivingEntity(livingEntityRenderState);
      if (this.isCaptureEnabled() && this.captureFrameActive && !this.renderingCapturedEntity && !this.renderingTaggedEntity && !IrisCompatibility.isRenderingShadowPass() && livingEntity2 != null) {
         VertexConsumerProvider vertexConsumerProvider2 = renderLayer -> this.captureLivingLayer(vertexConsumerProvider.getBuffer(renderLayer), renderLayer, livingEntityRenderState);
         featureRenderer.render(matrixStack, vertexConsumerProvider2, i, livingEntityRenderState, f, g);
      } else {
         featureRenderer.render(matrixStack, vertexConsumerProvider, i, livingEntityRenderState, f, g);
      }
   }

   public void finishLivingEntityCapture(LivingEntityRenderState livingEntityRenderState) {
      if (!this.renderingTaggedEntity) {
         if (IrisCompatibility.isRenderingShadowPass()) {
            this.capturingLivingEntityId = Integer.MIN_VALUE;
         } else {
            LivingEntity livingEntity3 = this.getCapturedLivingEntity(livingEntityRenderState);
            if (livingEntity3 != null && this.capturingLivingEntityId == livingEntity3.getId()) {
               EntityFramebufferCapture.CaptureResources captureResources5 = this.captureResources;
               this.capturingLivingEntityId = Integer.MIN_VALUE;
               if (captureResources5 != null) {
                  try {
                     this.flushToCaptureFramebuffer(captureResources5);
                     this.currentFrameEntityCount++;
                     this.captureReady = true;
                  } catch (RuntimeException exception7) {
                     LOGGER.warn("Failed to finish living capture for {}", livingEntity3.getName().getString(), exception7);
                     this.captureReady = false;
                  }
               }
            }
         }
      }
   }

   public int getCurrentFrameEntityCount() {
      return this.currentFrameEntityCount;
   }

   public int getPreviousFrameEntityCount() {
      return this.previousFrameEntityCount;
   }

   public int getLayerAllocatorCount() {
      return this.layerAllocators.size();
   }

   public int getCaptureWidth() {
      return this.captureWidth;
   }

   public int getCaptureHeight() {
      return this.captureHeight;
   }

   public void renderCapturePreview(RenderManager renderManager, int i, int j) {
      if (renderManager != null && i > 0 && j > 0) {
         if (this.isCaptureTextureReady()) {
            int intValue9 = getColorTextureId(this.captureFramebuffer);
            if (intValue9 > 0) {
               renderManager.drawFlippedTexture(intValue9, 0.0F, 0.0F, (float)i, (float)j);
            }
         }
      }
   }

   public void onFramebufferResize(int i, int j) {
      this.resetFrameState();
      this.captureReady = false;
      if (i <= 0 || j <= 0 || i != this.captureWidth || j != this.captureHeight) {
         this.deleteCaptureFramebuffer();
         this.deleteTaggedFramebuffer();
         this.captureWidth = -1;
         this.captureHeight = -1;
      }
   }

   private boolean ensureCaptureFramebuffer(int i, int j) {
      if (i > 0 && j > 0) {
         SimpleFramebuffer simpleFramebuffer6 = this.captureFramebuffer;
         if (simpleFramebuffer6 != null && !isFramebufferUsable(simpleFramebuffer6)) {
            this.deleteCaptureFramebuffer();
            this.captureWidth = -1;
            this.captureHeight = -1;
            simpleFramebuffer6 = null;
         }

         if (simpleFramebuffer6 == null) {
            try {
               simpleFramebuffer6 = new SimpleFramebuffer("night_entity_capture", i, j, true);
               this.captureFramebuffer = simpleFramebuffer6;
               this.captureWidth = i;
               this.captureHeight = j;
            } catch (RuntimeException exception8) {
               LOGGER.warn("Failed to create capture framebuffer {}x{}", i, j, exception8);
               this.captureFramebuffer = null;
               this.captureWidth = -1;
               this.captureHeight = -1;
               return false;
            }
         }

         if (this.captureWidth != i || this.captureHeight != j) {
            try {
               simpleFramebuffer6.resize(i, j);
               this.captureWidth = i;
               this.captureHeight = j;
            } catch (RuntimeException exception9) {
               LOGGER.warn("Failed to resize capture framebuffer to {}x{}", i, j, exception9);
               this.deleteCaptureFramebuffer();
               this.captureWidth = -1;
               this.captureHeight = -1;
               return false;
            }
         }

         return isFramebufferUsable(simpleFramebuffer6);
      } else {
         this.deleteCaptureFramebuffer();
         this.captureWidth = -1;
         this.captureHeight = -1;
         return false;
      }
   }

   private boolean ensureTaggedFramebuffer(int i, int j) {
      if (i > 0 && j > 0) {
         SimpleFramebuffer simpleFramebuffer7 = this.taggedFramebuffer;
         if (simpleFramebuffer7 != null && !isFramebufferUsable(simpleFramebuffer7)) {
            this.deleteTaggedFramebuffer();
            simpleFramebuffer7 = null;
         }

         if (simpleFramebuffer7 == null) {
            try {
               simpleFramebuffer7 = new SimpleFramebuffer("wild_tagged_capture", i, j, true);
               this.taggedFramebuffer = simpleFramebuffer7;
            } catch (RuntimeException exception10) {
               LOGGER.warn("Failed to create tagged capture framebuffer {}x{}", i, j, exception10);
               this.taggedFramebuffer = null;
               return false;
            }
         }

         if (simpleFramebuffer7.textureWidth != i || simpleFramebuffer7.textureHeight != j) {
            try {
               simpleFramebuffer7.resize(i, j);
            } catch (RuntimeException exception11) {
               LOGGER.warn("Failed to resize tagged capture framebuffer to {}x{}", i, j, exception11);
               this.deleteTaggedFramebuffer();
               return false;
            }
         }

         return isFramebufferUsable(simpleFramebuffer7);
      } else {
         this.deleteTaggedFramebuffer();
         return false;
      }
   }

   private void deleteTaggedFramebuffer() {
      SimpleFramebuffer simpleFramebuffer8 = this.taggedFramebuffer;
      if (simpleFramebuffer8 != null) {
         if (!RenderSystem.isOnRenderThread()) {
            this.taggedFramebuffer = null;
         } else {
            try {
               simpleFramebuffer8.delete();
            } catch (RuntimeException exception12) {
               LOGGER.warn("Failed to delete tagged capture framebuffer", exception12);
            }

            this.taggedFramebuffer = null;
         }
      }
   }

   private void deleteCaptureFramebuffer() {
      SimpleFramebuffer simpleFramebuffer9 = this.captureFramebuffer;
      if (simpleFramebuffer9 != null || this.directFramebufferId != 0) {
         if (!RenderSystem.isOnRenderThread()) {
            this.captureFramebuffer = null;
            this.directFramebufferId = 0;
         } else {
            if (simpleFramebuffer9 != null) {
               try {
                  simpleFramebuffer9.delete();
               } catch (RuntimeException exception13) {
                  LOGGER.warn("Failed to delete capture framebuffer", exception13);
               }

               this.captureFramebuffer = null;
            }

            if (this.directFramebufferId != 0) {
               GL30.glDeleteFramebuffers(this.directFramebufferId);
               this.directFramebufferId = 0;
            }
         }
      }
   }

   private boolean clearFramebufferDirectly(Framebuffer framebuffer) {
      if (framebuffer.getColorAttachment() instanceof GlTexture glTexture) {
         int intValue10 = glTexture.getGlId();
         int intValue11 = framebuffer.getDepthAttachment() instanceof GlTexture glTexture2 ? glTexture2.getGlId() : 0;
         if (intValue10 <= 0) {
            return false;
         } else {
            FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();
            boolean flag7 = false ;

            boolean flag8;
            label159: {
               label160: {
                  boolean flag9;
                  try {
                     label146: {
                        MemoryStack memoryStack;
                        label161: {
                           flag7 = true;
                           memoryStack = MemoryStack.stackPush();

                           try {
                              if (this.directFramebufferId == 0) {
                                 this.directFramebufferId = GL30.glGenFramebuffers();
                              }

                              GL30.glBindFramebuffer(36160, this.directFramebufferId);
                              GL30.glFramebufferTexture2D(36160, 36064, 3553, intValue10, 0);
                              GL30.glFramebufferTexture2D(36160, 36096, 3553, intValue11, 0);
                              GL11.glDrawBuffer(36064);
                              if (GL30.glCheckFramebufferStatus(36160) != 36053) {
                                 flag8 = false;
                                 break label161;
                              }

                              GL11.glColorMask(true, true, true, true);
                              GL11.glDepthMask(true);
                              FloatBuffer floatBuffer = memoryStack.floats(0.0F, 0.0F, 0.0F, 0.0F);
                              GL30.glClearBufferfv(6144, 0, floatBuffer);
                              if (intValue11 > 0) {
                                 FloatBuffer floatBuffer2 = memoryStack.floats(1.0F);
                                 GL30.glClearBufferfv(6145, 0, floatBuffer2);
                              }

                              flag9 = true;
                           } catch (Throwable exception14) {
                              if (memoryStack != null) {
                                 try {
                                    memoryStack.close();
                                 } catch (Throwable exception15) {
                                    exception14.addSuppressed(exception15);
                                 }
                              }

                              throw exception14;
                           }

                           if (memoryStack != null) {
                              memoryStack.close();
                              flag7 = false;
                           } else {
                              flag7 = false;
                           }
                           break label146;
                        }

                        if (memoryStack != null) {
                           memoryStack.close();
                           flag7 = false;
                        } else {
                           flag7 = false;
                        }
                        break label160;
                     }
                  } catch (RuntimeException exception16) {
                     LOGGER.warn("Failed to clear capture framebuffer directly", exception16);
                     flag8 = false;
                     flag7 = false;
                     break label159;
                  } finally {
                     if (flag7) {
                        if (this.directFramebufferId != 0) {
                           GL30.glBindFramebuffer(36160, this.directFramebufferId);
                           GL30.glFramebufferTexture2D(36160, 36064, 3553, 0, 0);
                           GL30.glFramebufferTexture2D(36160, 36096, 3553, 0, 0);
                        }

                        FramebufferUtils.restoreGlState(glStateSnapshot);
                     }
                  }

                  if (this.directFramebufferId != 0) {
                     GL30.glBindFramebuffer(36160, this.directFramebufferId);
                     GL30.glFramebufferTexture2D(36160, 36064, 3553, 0, 0);
                     GL30.glFramebufferTexture2D(36160, 36096, 3553, 0, 0);
                  }

                  FramebufferUtils.restoreGlState(glStateSnapshot);
                  return flag9;
               }

               if (this.directFramebufferId != 0) {
                  GL30.glBindFramebuffer(36160, this.directFramebufferId);
                  GL30.glFramebufferTexture2D(36160, 36064, 3553, 0, 0);
                  GL30.glFramebufferTexture2D(36160, 36096, 3553, 0, 0);
               }

               FramebufferUtils.restoreGlState(glStateSnapshot);
               return flag8;
            }

            if (this.directFramebufferId != 0) {
               GL30.glBindFramebuffer(36160, this.directFramebufferId);
               GL30.glFramebufferTexture2D(36160, 36064, 3553, 0, 0);
               GL30.glFramebufferTexture2D(36160, 36096, 3553, 0, 0);
            }

            FramebufferUtils.restoreGlState(glStateSnapshot);
            return flag8;
         }
      } else {
         return false;
      }
   }

   private boolean matchesCaptureFilter(Entity entity) {
      if (this.captureAllEntities) {
         return true;
      } else {
         for (Predicate predicate4 : this.captureFilters.values()) {
            try {
               if (predicate4.test(entity)) {
                  return true;
               }
            } catch (RuntimeException exception17) {
               LOGGER.warn("Entity capture filter failed for {}", entity.getName().getString(), exception17);
            }
         }

         return false;
      }
   }

   private LivingEntity getCapturedLivingEntity(LivingEntityRenderState livingEntityRenderState) {
      if (livingEntityRenderState == null) {
         return null;
      } else {
         int intValue12 = ((ChamsRenderState)livingEntityRenderState).wild$getEntityId();
         MinecraftClient client4 = MinecraftClient.getInstance();
         return (client4 != null && client4.world != null && intValue12 != Integer.MIN_VALUE ? client4.world.getEntityById(intValue12) : null) instanceof LivingEntity livingEntity4
               && this.matchesCaptureFilter(livingEntity4)
            ? livingEntity4
            : null;
      }
   }

   private void flushToCaptureFramebuffer(EntityFramebufferCapture.CaptureResources captureResources6) {
      SimpleFramebuffer simpleFramebuffer10 = this.captureFramebuffer;
      if (captureResources6 != null && simpleFramebuffer10 != null) {
         GpuTextureView gpuTextureView16 = simpleFramebuffer10.getColorAttachmentView();
         if (gpuTextureView16 != null && !gpuTextureView16.isClosed()) {
            GpuTextureView gpuTextureView17 = RenderSystem.outputColorTextureOverride;
            GpuTextureView gpuTextureView18 = RenderSystem.outputDepthTextureOverride;
            RenderSystem.outputColorTextureOverride = gpuTextureView16;
            RenderSystem.outputDepthTextureOverride = simpleFramebuffer10.getDepthAttachmentView();
            this.renderingCapturedEntity = true;
            boolean flag10 = false ;

            try {
               flag10 = true;
               captureResources6.flush();
               flag10 = false;
            } finally {
               if (flag10) {
                  this.renderingCapturedEntity = false;
                  RenderSystem.outputColorTextureOverride = gpuTextureView17;
                  RenderSystem.outputDepthTextureOverride = gpuTextureView18;
               }
            }

            this.renderingCapturedEntity = false;
            RenderSystem.outputColorTextureOverride = gpuTextureView17;
            RenderSystem.outputDepthTextureOverride = gpuTextureView18;
         }
      }
   }

   private void resetIfDisabled() {
      if (!this.isCaptureEnabled()) {
         this.captureReady = false;
         this.taggedCaptureReady = false;
         this.renderingCapturedEntity = false;
         this.renderingTaggedEntity = false;
         this.captureFrameActive = false;
         this.captureWidth = -1;
         this.captureHeight = -1;
         this.currentFrameEntityCount = 0;
         this.previousFrameEntityCount = 0;
         this.capturingLivingEntityId = Integer.MIN_VALUE;
         this.releaseCaptureResources();
         this.closeLayerAllocators();
         this.deleteCaptureFramebuffer();
         this.deleteTaggedFramebuffer();
      }
   }

   private void resetFrameState() {
      this.captureReady = false;
      this.taggedCaptureReady = false;
      this.captureFrameActive = false;
      this.currentFrameEntityCount = 0;
      this.capturingLivingEntityId = Integer.MIN_VALUE;
      this.releaseCaptureResources();
   }

   private void releaseCaptureResources() {
      EntityFramebufferCapture.CaptureResources captureResources7 = this.captureResources;
      if (captureResources7 != null) {
         try {
            try {
               captureResources7.flush();
            } catch (RuntimeException exception18) {
               LOGGER.warn("Failed to flush capture resources during reset", exception18);
            }

            captureResources7.close();
         } catch (RuntimeException exception19) {
            LOGGER.warn("Failed to release capture resources", exception19);
         }

         this.captureResources = null;
      }
   }

   private void closeLayerAllocators() {
      for (BufferAllocator bufferAllocator : this.layerAllocators.values()) {
         try {
            bufferAllocator.close();
         } catch (RuntimeException exception20) {
            LOGGER.warn("Failed to close capture layer allocator", exception20);
         }
      }

      this.layerAllocators.clear();
   }

   private static boolean hasColorAttachment(Framebuffer framebuffer) {
      if (framebuffer == null) {
         return false;
      } else {
         return framebuffer.getColorAttachment() instanceof GlTexture glTexture3 ? glTexture3.getGlId() > 0 : false;
      }
   }

   private static boolean isFramebufferUsable(Framebuffer framebuffer) {
      if (!hasColorAttachment(framebuffer)) {
         return false;
      } else if (!(framebuffer instanceof SimpleFramebuffer simpleFramebuffer11)) {
         return true;
      } else {
         GpuTextureView gpuTextureView19 = simpleFramebuffer11.getColorAttachmentView();
         if (gpuTextureView19 != null && !gpuTextureView19.isClosed()) {
            GpuTextureView gpuTextureView20 = simpleFramebuffer11.getDepthAttachmentView();
            return gpuTextureView20 == null || !gpuTextureView20.isClosed();
         } else {
            return false;
         }
      }
   }

   private static int getColorTextureId(Framebuffer framebuffer) {
      if (framebuffer == null) {
         return 0;
      } else {
         return framebuffer.getColorAttachment() instanceof GlTexture glTexture4 ? glTexture4.getGlId() : 0;
      }
   }

   private static int getDepthTextureId(Framebuffer framebuffer) {
      if (framebuffer == null) {
         return 0;
      } else {
         return framebuffer.getDepthAttachment() instanceof GlTexture glTexture5 ? glTexture5.getGlId() : 0;
      }
   }

   static final class CaptureResources implements AutoCloseable {
      private final BufferAllocator sharedAllocator;
      private final SequencedMap<RenderLayer, BufferAllocator> layerAllocators;
      private final Immediate immediateProvider;
      private final VertexConsumerProvider routingProvider;
      private boolean flushed;

      CaptureResources(BufferAllocator bufferAllocator, SequencedMap<RenderLayer, BufferAllocator> sequencedMap) {
         this.sharedAllocator = bufferAllocator;
         this.layerAllocators = sequencedMap;
         this.immediateProvider = VertexConsumerProvider.immediate(sequencedMap, bufferAllocator);
         this.routingProvider = this::getBuffer;
      }

      VertexConsumerProvider getProvider() {
         return this.routingProvider;
      }

      VertexConsumer getBuffer(RenderLayer renderLayer) {
         this.layerAllocators
            .computeIfAbsent(renderLayer, renderLayerx -> new BufferAllocator(Math.max(4096, Math.min(renderLayerx.getExpectedBufferSize(), 262144))));
         this.flushed = false;
         return this.immediateProvider.getBuffer(renderLayer);
      }

      MatrixStack copyMatrixStack(MatrixStack matrixStack) {
         if (matrixStack == null) {
            return null;
         } else {
            MatrixStack matrices3 = new MatrixStack();
            Entry entry = matrixStack.peek();
            Entry entry2 = matrices3.peek();
            entry2.getPositionMatrix().set(entry.getPositionMatrix());
            entry2.getNormalMatrix().set(entry.getNormalMatrix());
            return matrices3;
         }
      }

      void markPending() {
         this.flushed = false;
      }

      void flush() {
         if (!this.flushed) {
            this.immediateProvider.draw();
            this.sharedAllocator.clear();
            this.layerAllocators.values().forEach(BufferAllocator::clear);
            this.flushed = true;
         }
      }

      @Override
      public void close() {
         this.sharedAllocator.clear();
         this.layerAllocators.values().forEach(BufferAllocator::clear);
      }
   }
}
