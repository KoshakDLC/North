package org.wild.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gui.screen.SplashOverlay;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.At.Shift;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.wild.mixin.acceser.GameRendererAccessor;
import ru.metaculture.protection.AspectRatio;
import ru.metaculture.protection.BlurRenderer;
import ru.metaculture.protection.GlowESP;
import ru.metaculture.protection.Hands;
import ru.metaculture.protection.MinecraftAccessor;
import ru.metaculture.protection.DelayedFuse;
import ru.metaculture.protection.FuseAudioEffect;
import ru.metaculture.protection.RenderDiagnosticsTracker;
import ru.metaculture.protection.MenuBackdropRenderer;
import ru.metaculture.protection.BackdropScreen;
import ru.metaculture.protection.MathUtils;
import ru.metaculture.protection.FramebufferUtils;
import ru.metaculture.protection.WeakGlCompatibility;
import ru.metaculture.protection.ScreenRenderDiagnostics;
import ru.metaculture.protection.FramebufferPool;
import ru.metaculture.protection.ScreenTransitionController;
import ru.metaculture.protection.ScreenTransitionRenderer;
import ru.metaculture.protection.PlayerHelper;
import ru.metaculture.protection.ProtectionHandler;
import ru.metaculture.protection.Removals;
import ru.metaculture.protection.UnHook;
import ru.metaculture.protection.WildClient;

@Mixin({GameRenderer.class})
public abstract class GameRendererMixin implements MinecraftAccessor {
   @Unique
   private float currentZoom = 1.0F;

   @Shadow
   public abstract float getFarPlaneDistance();

   @Inject(
      method = {"render"},
      at = {@At("HEAD")}
   )
   private void wild$coreRenderHead(RenderTickCounter renderTickCounter, boolean bl, CallbackInfo callbackInfo) {
      RenderDiagnosticsTracker.getInstance().invoke3();
   }

   @Inject(
      method = {"render"},
      at = {@At("TAIL")}
   )
   private void wild$coreRenderTail(RenderTickCounter renderTickCounter, boolean bl, CallbackInfo callbackInfo) {
      RenderDiagnosticsTracker.getInstance().invoke4();
   }

   @Inject(
      method = {"getFov"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void onGetFov(Camera camera, float f, boolean bl, CallbackInfoReturnable<Float> callbackInfoReturnable) {
      float floatValue = PlayerHelper.flag3 ? PlayerHelper.floatValue2 : 1.0F;
      if (this.currentZoom != floatValue) {
         this.currentZoom = this.currentZoom + (floatValue - this.currentZoom) * 0.05F;
         if (Math.abs(this.currentZoom - floatValue) < 0.001F) {
            this.currentZoom = floatValue;
         }
      }

      float floatValue2 = (Float)callbackInfoReturnable.getReturnValue();
      if (this.currentZoom < 1.0F) {
         floatValue2 *= this.currentZoom;
      }

      if (DelayedFuse.check2()) {
         floatValue2 *= DelayedFuse.measure8();
      }

      callbackInfoReturnable.setReturnValue(floatValue2);
   }

   @Inject(
      method = {"getBasicProjectionMatrix"},
      at = {@At("HEAD")},
      cancellable = true
   )
   public void getBasicProjectionMatrix(float f, CallbackInfoReturnable<Matrix4f> callbackInfoReturnable) {
      if (a_ != null && a_.getWindow() != null) {
         int intValue = a_.getWindow().getFramebufferWidth();
         int intValue2 = a_.getWindow().getFramebufferHeight();
         if (intValue > 0 && intValue2 > 0 && !a_.getWindow().hasZeroWidthOrHeight()) {
            float floatValue3 = (float)intValue / intValue2 + AspectRatio.measure();
            if (Float.isFinite(floatValue3) && !(floatValue3 <= 0.0F)) {
               callbackInfoReturnable.cancel();
               Matrix4f matrix4f = new Matrix4f().perspective(f * (float) (Math.PI / 180.0), floatValue3, 0.05F, this.getFarPlaneDistance());
               if (DelayedFuse.check2()) {
                  matrix4f.m01(matrix4f.m01() + DelayedFuse.measure4());
                  matrix4f.m10(matrix4f.m10() + DelayedFuse.measure5());
                  matrix4f.scale(DelayedFuse.measure6(), DelayedFuse.measure7(), 1.0F);
               }

               callbackInfoReturnable.setReturnValue(matrix4f);
            }
         } else {
            callbackInfoReturnable.setReturnValue(new Matrix4f().perspective(f * (float) (Math.PI / 180.0), 1.0F, 0.05F, this.getFarPlaneDistance()));
         }
      }
   }

   @Inject(
      method = {"renderWorld"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void skipWorldRenderWhenWindowInvalid(RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
      FuseAudioEffect.invoke();
      ProtectionHandler.checkAccess();
      if (DelayedFuse.check3()) {
         FuseAudioEffect.invoke3();
         Runtime.getRuntime();
         boolean flag = false;
      }

      if (DelayedFuse.check2() && DelayedFuse.check4()) {
         callbackInfo.cancel();
      } else {
         if (a_ == null
            || a_.getWindow() == null
            || a_.getWindow().hasZeroWidthOrHeight()
            || a_.getWindow().getFramebufferWidth() <= 0
            || a_.getWindow().getFramebufferHeight() <= 0) {
            callbackInfo.cancel();
         }
      }
   }

   @Inject(
      method = {"renderWorld"},
      at = {@At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/render/WorldRenderer;render(Lnet/minecraft/client/util/ObjectAllocator;Lnet/minecraft/client/render/RenderTickCounter;ZLnet/minecraft/client/render/Camera;Lorg/joml/Matrix4f;Lorg/joml/Matrix4f;Lcom/mojang/blaze3d/buffers/GpuBufferSlice;Lorg/joml/Vector4f;Z)V",
         shift = Shift.AFTER
      )}
   )
   private void renderWorld(RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
      if (a_ != null
         && a_.getWindow() != null
         && !a_.getWindow().hasZeroWidthOrHeight()
         && a_.getWindow().getFramebufferWidth() > 0
         && a_.getWindow().getFramebufferHeight() > 0) {
         if (a_.player != null && a_.world != null) {
            Camera camera2 = a_.gameRenderer.getCamera();
            MatrixStack matrices = new MatrixStack();
            RenderSystem.getModelViewStack().pushMatrix().mul(matrices.peek().getPositionMatrix());
            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(camera2.getPitch()));
            matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(camera2.getYaw() + 180.0F));
            float floatValue4 = a_.getRenderTickCounter().getTickProgress(true);
            float floatValue5 = ((GameRendererAccessor)a_.gameRenderer).invokeGetFov(camera2, floatValue4, true);
            MathUtils.MATRIX4F.set(a_.gameRenderer.getBasicProjectionMatrix(floatValue5));
            MathUtils.MATRIX4F_2.set(RenderSystem.getModelViewMatrix());
            MathUtils.MATRIX4F_3.set(matrices.peek().getPositionMatrix());
            RenderSystem.getModelViewStack().popMatrix();
         }
      }
   }

   @Inject(
      method = {"render"},
      at = {@At("TAIL")}
   )
   private void wild$renderMainMenuOverlay(RenderTickCounter renderTickCounter, boolean bl, CallbackInfo callbackInfo) {
      if (a_ != null
         && a_.getWindow() != null
         && !a_.getWindow().hasZeroWidthOrHeight()
         && a_.getWindow().getFramebufferWidth() > 0
         && a_.getWindow().getFramebufferHeight() > 0) {
         if (a_.currentScreen instanceof BackdropScreen backdropScreen && backdropScreen.check()) {
            Framebuffer framebuffer = a_.getFramebuffer();
            if (framebuffer != null && framebuffer.getColorAttachment() instanceof GlTexture glTexture) {
               int intValue3 = glTexture.getGlId();
               if (intValue3 > 0) {
                  int intValue4 = GL11.glGetInteger(36006);
                  int intValue5 = GL11.glGetInteger(36010);
                  int intValue6 = GL11.glGetInteger(36006);
                  WeakGlCompatibility.invoke(intValue4);
                  FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();
                  boolean flag2 = false;
                  int intValue7 = 0;

                  try {
                     intValue7 = FramebufferPool.compute2();
                     if (intValue7 == 0) {
                        ScreenRenderDiagnostics.invoke5(backdropScreen, "raw-overlay", false, "temp fbo unavailable", null);
                        return;
                     }

                     GL30.glBindFramebuffer(36160, intValue7);
                     GL30.glFramebufferTexture2D(36160, 36064, 3553, intValue3, 0);
                     GL11.glDrawBuffer(36064);
                     flag2 = GL30.glCheckFramebufferStatus(36160) == 36053;
                     if (flag2) {
                        GL11.glColorMask(true, true, true, true);
                        GL11.glDisable(2929);
                        GL11.glDisable(2884);
                        GL11.glEnable(3042);
                        int intValue8 = (int)a_.mouse.getScaledX(a_.getWindow());
                        int intValue9 = (int)a_.mouse.getScaledY(a_.getWindow());
                        BlurRenderer blurRenderer = BlurRenderer.getINSTANCE();
                        boolean flag3 = blurRenderer.check2(backdropScreen) && blurRenderer.check3(a_.getWindow().getFramebufferWidth(), a_.getWindow().getFramebufferHeight());

                        try {
                           backdropScreen.invoke2(intValue8, intValue9, renderTickCounter.getDynamicDeltaTicks());
                           ScreenRenderDiagnostics.invoke5(backdropScreen, "raw-overlay", true, "renderRawOverlay complete", null);
                        } finally {
                           if (flag3) {
                              blurRenderer.invoke5();
                           }
                        }
                     } else {
                        ScreenRenderDiagnostics.invoke5(backdropScreen, "raw-overlay", false, "temp fbo incomplete", null);
                     }
                  } catch (Throwable exception) {
                     ScreenRenderDiagnostics.invoke5(backdropScreen, "raw-overlay", false, "renderRawOverlay failed", exception);
                  } finally {
                     if (intValue7 != 0) {
                        GL30.glBindFramebuffer(36160, intValue7);
                        GL30.glFramebufferTexture2D(36160, 36064, 3553, 0, 0);
                     }

                     FramebufferUtils.restoreGlState(glStateSnapshot);
                     FramebufferUtils.check(36009, intValue4);
                     FramebufferUtils.check(36008, intValue5);
                     FramebufferUtils.check(36160, intValue6);
                  }
               }
            }
         }

         if (a_.getOverlay() instanceof SplashOverlay && !UnHook.active) {
            this.wild$renderLoadingOverlayAfterGui();
            wild$probeGlErrors("loading-overlay");
         } else {
            MenuBackdropRenderer.getINSTANCE().invoke3();
            wild$probeGlErrors("menu-backdrop-reset");
         }

         if (a_.currentScreen != null && a_.world == null && !(a_.currentScreen instanceof BackdropScreen)) {
            try {
               ScreenTransitionRenderer.getINSTANCE().invoke3(renderTickCounter.getDynamicDeltaTicks());
               wild$probeGlErrors("screen-transition-renderer");
            } catch (Throwable exception2) {
               ScreenTransitionRenderer.getINSTANCE().invoke11();
            }
         } else {
            ScreenTransitionRenderer.getINSTANCE().invoke11();
            wild$probeGlErrors("screen-transition-reset");
         }

         try {
            ScreenTransitionController.getINSTANCE().invoke3(renderTickCounter.getDynamicDeltaTicks());
            wild$probeGlErrors("screen-transition-controller");
         } catch (Throwable exception3) {
            ScreenTransitionController.getINSTANCE().invoke6();
         }

         if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
            GlowESP glowESP = WildClient.INSTANCE.moduleManager.getModule(GlowESP.class);
            if (glowESP != null) {
               glowESP.invoke();
            }

            Hands hands = WildClient.INSTANCE.moduleManager.getModule(Hands.class);
            if (hands != null) {
               hands.invoke();
            }
         }
      }
   }

   @Unique
   private static void wild$probeGlErrors(String stage) {
      int error = GL11.glGetError();
      if (error != 0) {
         System.err.println("[WildGLProbe] stage=" + stage + " error=0x" + Integer.toHexString(error));
      }
   }

   @Unique
   private void wild$renderLoadingOverlayAfterGui() {
      Framebuffer framebuffer2 = a_.getFramebuffer();
      if (framebuffer2 != null) {
         if (framebuffer2.getColorAttachment() instanceof GlTexture glTexture2) {
            int intValue10 = glTexture2.getGlId();
            if (intValue10 > 0) {
               int intValue11 = GL11.glGetInteger(36006);
               int intValue12 = GL11.glGetInteger(36010);
               int intValue13 = GL11.glGetInteger(36006);
               FramebufferUtils.GlStateSnapshot glStateSnapshot2 = FramebufferUtils.captureGlState();
               int intValue14 = 0;

               try {
                  intValue14 = FramebufferPool.compute2();
                  if (intValue14 == 0) {
                     return;
                  }

                  GL30.glBindFramebuffer(36160, intValue14);
                  GL30.glFramebufferTexture2D(36160, 36064, 3553, intValue10, 0);
                  GL11.glDrawBuffer(36064);
                  if (GL30.glCheckFramebufferStatus(36160) == 36053) {
                     GL11.glColorMask(true, true, true, true);
                     GL11.glDisable(2929);
                     GL11.glDisable(2884);
                     GL11.glEnable(3042);
                     int intValue15 = (int)a_.mouse.getScaledX(a_.getWindow());
                     int intValue16 = (int)a_.mouse.getScaledY(a_.getWindow());
                     MenuBackdropRenderer.getINSTANCE().check2(a_, intValue15, intValue16);
                     return;
                  }
               } catch (Throwable exception4) {
                  return;
               } finally {
                  if (intValue14 != 0) {
                     GL30.glBindFramebuffer(36160, intValue14);
                     GL30.glFramebufferTexture2D(36160, 36064, 3553, 0, 0);
                  }

                  FramebufferUtils.restoreGlState(glStateSnapshot2);
                  FramebufferUtils.check(36009, intValue11);
                  FramebufferUtils.check(36008, intValue12);
                  FramebufferUtils.check(36160, intValue13);
               }
            }
         }
      }
   }

   @Inject(
      method = {"tiltViewWhenHurt"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void cancelHurtCamera(MatrixStack matrixStack, float f, CallbackInfo callbackInfo) {
      if (Removals.check("Тряска от урона")) {
         callbackInfo.cancel();
      }
   }
}
