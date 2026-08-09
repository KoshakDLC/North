package org.wild.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gl.Framebuffer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.gui.hud.bar.Bar;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.joml.Matrix4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.AiLabScreen;
import ru.metaculture.protection.Animations;
import ru.metaculture.protection.AutoBuyScreen;
import ru.metaculture.protection.AutoSwap;
import ru.metaculture.protection.BlurRenderer;
import ru.metaculture.protection.ColorPlus;
import ru.metaculture.protection.Event;
import ru.metaculture.protection.EventManager;
import ru.metaculture.protection.FontRegistry;
import ru.metaculture.protection.HudConstructorScreen;
import ru.metaculture.protection.HudModule;
import ru.metaculture.protection.ClickGuiScreen;
import ru.metaculture.protection.ModernClickGuiScreen;
import ru.metaculture.protection.MotionBlur;
import ru.metaculture.protection.DelayedFuse;
import ru.metaculture.protection.FuseAudioEffect;
import ru.metaculture.protection.HudRenderEvent;
import ru.metaculture.protection.ColorPlusPreset;
import ru.metaculture.protection.HudEditorRenderer;
import ru.metaculture.protection.MathUtils;
import ru.metaculture.protection.FramebufferUtils;
import ru.metaculture.protection.ColorPlusRenderer;
import ru.metaculture.protection.MotionBlurRenderer;
import ru.metaculture.protection.FramebufferPool;
import ru.metaculture.protection.ProtectInfo;
import ru.metaculture.protection.Removals;
import ru.metaculture.protection.RenderManager;
import ru.metaculture.protection.RotationBuilderScreen;
import ru.metaculture.protection.WildClient;

@Environment(EnvType.CLIENT)
@Mixin({InGameHud.class})
public class InGameHudMixin {
   @Unique
   private static final HudRenderEvent wild$cachedEventScreen = new HudRenderEvent();
   @Unique
   private long wild$lastCorruptionFrameMs;
   @Unique
   private float wild$heldTearY;
   @Unique
   private float wild$heldTearH;
   @Unique
   private float wild$heldTearShift;
   @Unique
   private float wild$panicWhite;
   @Unique
   private float wild$panicBlack;

   @Inject(
      method = {"renderCrosshair"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onRenderCrosshair(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
      MinecraftClient client = MinecraftClient.getInstance();
      if (DelayedFuse.check2()) {
         callbackInfo.cancel();
      } else {
         if (AutoSwap.isFlag()
            || client.currentScreen instanceof AutoBuyScreen
            || client.currentScreen instanceof ModernClickGuiScreen
            || client.currentScreen instanceof ClickGuiScreen
            || client.currentScreen instanceof HudConstructorScreen
            || client.currentScreen instanceof RotationBuilderScreen
            || client.currentScreen instanceof AiLabScreen) {
            callbackInfo.cancel();
         }
      }
   }

   @Inject(
      method = {"renderStatusEffectOverlay"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onRenderStatusEffects(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
      if (DelayedFuse.check2()) {
         callbackInfo.cancel();
      } else {
         if (HudModule.GROUP_SETTING.isEnabled("Potions") || wild$noRenderPotions()) {
            callbackInfo.cancel();
         }
      }
   }

   @Inject(
      method = {"renderHotbar"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onRenderHotbar(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
      if (DelayedFuse.check2()) {
         callbackInfo.cancel();
      } else {
         if (wild$customHotbarActive() || this.wild$foundryOverlayVisible() || MinecraftClient.getInstance().currentScreen instanceof HudConstructorScreen) {
            callbackInfo.cancel();
         }
      }
   }

   @Unique
   private boolean wild$foundryOverlayVisible() {
      MinecraftClient client2 = MinecraftClient.getInstance();
      return client2 != null && client2.currentScreen instanceof ModernClickGuiScreen modernClickGuiScreen ? modernClickGuiScreen.getClickGui().check() : false;
   }

   @Inject(
      method = {"renderStatusBars"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 0
   )
   private void onRenderStatusBars(DrawContext drawContext, CallbackInfo callbackInfo) {
      if (DelayedFuse.check2() || wild$customHotbarActive()) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"renderHeldItemTooltip"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 0
   )
   private void wild$cancelHeldItemTooltip(DrawContext drawContext, CallbackInfo callbackInfo) {
      if (DelayedFuse.check2() || wild$customHotbarActive()) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"renderScoreboardSidebar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/scoreboard/ScoreboardObjective;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onRenderScoreboardSidebar(DrawContext drawContext, ScoreboardObjective scoreboardObjective, CallbackInfo callbackInfo) {
      if (DelayedFuse.check2()) {
         callbackInfo.cancel();
      } else {
         ProtectInfo protectInfo = WildClient.INSTANCE.moduleManager.getModule(ProtectInfo.class);
         if (protectInfo != null && protectInfo.enabled && protectInfo.renderitPng.isEnabled()) {
            callbackInfo.cancel();
         }
      }
   }

   @Inject(
      method = {"renderMainHud"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 0
   )
   private void wild$cancelMainHudDuringCorruption(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
      if (DelayedFuse.check2()) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"renderPlayerList"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 0
   )
   private void wild$cancelPlayerListDuringCorruption(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
      if (DelayedFuse.check2()) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"renderOverlayMessage"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 0
   )
   private void wild$cancelOverlayMessageDuringCorruption(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
      if (DelayedFuse.check2()) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"renderTitleAndSubtitle"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 0
   )
   private void wild$cancelTitleDuringCorruption(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
      if (DelayedFuse.check2()) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"renderVignetteOverlay"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 0
   )
   private void wild$cancelVignette(DrawContext drawContext, Entity entity, CallbackInfo callbackInfo) {
      if (Removals.check("Виньетка")) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"renderSpyglassOverlay"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 0
   )
   private void wild$cancelSpyglass(DrawContext drawContext, float f, CallbackInfo callbackInfo) {
      if (Removals.check("Подзорная труба")) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"renderPortalOverlay"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 0
   )
   private void wild$cancelPortalOverlay(DrawContext drawContext, float f, CallbackInfo callbackInfo) {
      if (Removals.check("Портал")) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"renderNauseaOverlay"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 0
   )
   private void wild$cancelNauseaOverlay(DrawContext drawContext, float f, CallbackInfo callbackInfo) {
      if (Removals.check("Тошнота (экран)")) {
         callbackInfo.cancel();
      }
   }

   @Inject(
      method = {"renderOverlay"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 0
   )
   private void wild$cancelMiscOverlay(DrawContext drawContext, Identifier identifier, float f, CallbackInfo callbackInfo) {
      if (identifier != null) {
         String text = identifier.getPath();
         if (text.contains("pumpkin") && Removals.check("Тыква")) {
            callbackInfo.cancel();
         } else {
            if (text.contains("powder_snow") && Removals.check("Порошковый снег")) {
               callbackInfo.cancel();
            }
         }
      }
   }

   @Redirect(
      method = {"renderPlayerList"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/option/KeyBinding;isPressed()Z"
      ),
      require = 0
   )
   private boolean wild$keepTabListForClose(KeyBinding keyBinding) {
      if (DelayedFuse.check2()) {
         return false;
      } else {
         boolean flag = keyBinding.isPressed();
         if (WildClient.isInitialized() && WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
            Animations animations = WildClient.INSTANCE.moduleManager.getModule(Animations.class);
            return animations != null && animations.enabled && animations.animirovat.isEnabled("Таб") ? animations.check3(flag) : flag;
         } else {
            return flag;
         }
      }
   }

   @Redirect(
      method = {"renderScoreboardSidebar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/scoreboard/ScoreboardObjective;)V"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/scoreboard/ScoreboardObjective;getDisplayName()Lnet/minecraft/text/Text;"
      )
   )
   private Text litka$maskScoreboardTitle(ScoreboardObjective scoreboardObjective) {
      if (DelayedFuse.check2()) {
         return Text.empty();
      } else if (scoreboardObjective == null) {
         return Text.empty();
      } else {
         Text text2 = scoreboardObjective.getDisplayName();
         return (Text)(text2 != null ? ProtectInfo.resolve5(text2) : Text.empty());
      }
   }

   @Redirect(
      method = {"renderMainHud"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/hud/bar/Bar;drawExperienceLevel(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/font/TextRenderer;I)V"
      )
   )
   private void redirectDrawExperienceLevel(DrawContext drawContext, TextRenderer textRenderer, int i) {
      if (!DelayedFuse.check2() && !wild$customHotbarActive()) {
         Bar.drawExperienceLevel(drawContext, textRenderer, i);
      }
   }

   @Redirect(
      method = {"renderMainHud"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/hud/bar/Bar;renderBar(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V"
      ),
      require = 0
   )
   private void redirectRenderBar(Bar bar, DrawContext drawContext, RenderTickCounter renderTickCounter) {
      if (!DelayedFuse.check2()) {
         if (!wild$customHotbarActive()) {
            bar.renderBar(drawContext, renderTickCounter);
         }
      }
   }

   @Redirect(
      method = {"renderMainHud"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/hud/bar/Bar;renderAddons(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/render/RenderTickCounter;)V"
      ),
      require = 0
   )
   private void redirectRenderAddons(Bar bar, DrawContext drawContext, RenderTickCounter renderTickCounter) {
      if (!DelayedFuse.check2()) {
         if (!wild$customHotbarActive()) {
            bar.renderAddons(drawContext, renderTickCounter);
         }
      }
   }

   @Inject(
      method = {"render"},
      at = {@At("HEAD")}
   )
   private void wild$applyColorPlus(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
      if (WildClient.isInitialized()) {
         if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
            ColorPlus colorPlus;
            MotionBlur motionBlur;
            try {
               colorPlus = (ColorPlus)WildClient.INSTANCE.moduleManager.findModule(ColorPlus.class);
               motionBlur = (MotionBlur)WildClient.INSTANCE.moduleManager.findModule(MotionBlur.class);
            } catch (Throwable exception) {
               return;
            }

            boolean flag2 = colorPlus != null && colorPlus.enabled;
            boolean flag3 = motionBlur != null && motionBlur.enabled;
            if (flag2 || flag3) {
               MinecraftClient client3 = MinecraftClient.getInstance();
               if (client3 != null && client3.world != null && client3.player != null) {
                  if (client3.getWindow() != null) {
                     int intValue = client3.getWindow().getFramebufferWidth();
                     int intValue2 = client3.getWindow().getFramebufferHeight();
                     if (intValue > 1 && intValue2 > 1) {
                        Framebuffer framebuffer = client3.getFramebuffer();
                        if (framebuffer != null) {
                           if (framebuffer.getColorAttachment() instanceof GlTexture glTexture) {
                              int intValue3 = glTexture.getGlId();
                              if (intValue3 > 0) {
                                 if (flag3) {
                                    if (client3.currentScreen == null) {
                                       try {
                                          MotionBlurRenderer.getINSTANCE()
                                             .invoke(
                                                client3,
                                                client3.gameRenderer.getCamera(),
                                                new Matrix4f(MathUtils.MATRIX4F_3),
                                                new Matrix4f(MathUtils.MATRIX4F),
                                                motionBlur.resolve()
                                             );
                                       } catch (Throwable exception2) {
                                          System.err.println("[SilkFlow] apply failed: " + exception2.getMessage());
                                       }
                                    } else {
                                       MotionBlurRenderer.getINSTANCE().invoke3();
                                    }
                                 }

                                 if (flag2) {
                                    ColorPlusPreset colorPlusPreset = colorPlus.resolve();
                                    ColorPlusRenderer.ColorPlusRendererState colorPlusRendererState = new ColorPlusRenderer.ColorPlusRendererState();
                                    colorPlusRendererState.floatValue = colorPlus.silaEffekta.getValue();
                                    colorPlusRendererState.floatValue2 = colorPlusPreset.floatValue + colorPlus.ekspozitsiya.getValue();
                                    colorPlusRendererState.floatValue3 = colorPlusPreset.floatValue2 + colorPlus.kontrast.getValue();
                                    colorPlusRendererState.floatValue4 = colorPlusPreset.floatValue3 + colorPlus.nasyschennost.getValue();
                                    colorPlusRendererState.floatValue5 = colorPlusPreset.floatValue4 + colorPlus.vibrance.getValue();
                                    colorPlusRendererState.floatValue6 = colorPlusPreset.floatValue5 + colorPlus.gamma.getValue();
                                    colorPlusRendererState.floatValue7 = colorPlusPreset.floatValue6 + colorPlus.temperatura.getValue();
                                    colorPlusRendererState.floatValue8 = colorPlusPreset.floatValue7 + colorPlus.ottenokZelyonyyMadzhenta.getValue();
                                    colorPlusRendererState.floatValue9 = colorPlusPreset.floats[0];
                                    colorPlusRendererState.floatValue10 = colorPlusPreset.floats[1];
                                    colorPlusRendererState.floatValue11 = colorPlusPreset.floats[2];
                                    colorPlusRendererState.floatValue12 = colorPlusPreset.floats2[0];
                                    colorPlusRendererState.floatValue13 = colorPlusPreset.floats2[1];
                                    colorPlusRendererState.floatValue14 = colorPlusPreset.floats2[2];
                                    colorPlusRendererState.floatValue15 = colorPlusPreset.floats3[0];
                                    colorPlusRendererState.floatValue16 = colorPlusPreset.floats3[1];
                                    colorPlusRendererState.floatValue17 = colorPlusPreset.floats3[2];
                                    colorPlusRendererState.floatValue18 = 0.0F;
                                    colorPlusRendererState.floatValue19 = colorPlusPreset.floatValue9;
                                    colorPlusRendererState.floatValue20 = colorPlusPreset.floatValue10;
                                    colorPlusRendererState.floatValue21 = colorPlus.rezkostCas.isEnabled()
                                       ? Math.max(0.0F, colorPlusPreset.floatValue11 + colorPlus.silaRezkosti.getValue())
                                       : 0.0F;
                                    colorPlusRendererState.floatValue22 = Math.max(0.0F, colorPlusPreset.floatValue12 + colorPlus.vinetka.getValue());
                                    colorPlusRendererState.flag = true;

                                    try {
                                       ColorPlusRenderer.resolve().invoke(intValue3, intValue, intValue2, colorPlusRendererState);
                                    } catch (Throwable exception3) {
                                       System.err.println("[ColorPlus] apply failed: " + exception3.getMessage());
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   @Inject(
      method = {"render"},
      at = {@At("RETURN")}
   )
   private void onRenderHud(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
      if (WildClient.isInitialized()) {
         MinecraftClient client4 = MinecraftClient.getInstance();
         if (client4 != null && client4.player != null && client4.world != null && client4.getWindow() != null) {
            int intValue4 = client4.getWindow().getFramebufferWidth();
            int intValue5 = client4.getWindow().getFramebufferHeight();
            if (intValue4 > 0 && intValue5 > 0) {
               try {
                  WildClient.invoke15();
               } catch (Throwable exception4) {
                  return;
               }

               if (WildClient.resolve() != null) {
                  FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();
                  int intValue6 = 0;

                  try {
                     try {
                        FontRegistry.invoke3();
                     } catch (Throwable exception5) {
                     }

                     Framebuffer framebuffer2 = client4.getFramebuffer();
                     if (framebuffer2 != null) {
                        if (framebuffer2.getColorAttachment() instanceof GlTexture glTexture2) {
                           int intValue7 = glTexture2.getGlId();
                           intValue6 = FramebufferPool.compute();
                           if (intValue6 == 0) {
                              FramebufferUtils.check(36009, glStateSnapshot.intValue);
                              FramebufferUtils.check(36008, glStateSnapshot.intValue2);
                           } else {
                              GL30.glBindFramebuffer(36160, intValue6);
                              GL30.glFramebufferTexture2D(36160, 36064, 3553, intValue7, 0);
                              GL11.glDrawBuffer(36064);
                              if (GL30.glCheckFramebufferStatus(36160) != 36053) {
                                 GL30.glDeleteFramebuffers(intValue6);
                                 FramebufferPool.invoke(intValue6);
                                 intValue6 = 0;
                                 FramebufferUtils.check(36009, glStateSnapshot.intValue);
                                 FramebufferUtils.check(36008, glStateSnapshot.intValue2);
                              }
                           }
                        } else {
                           FramebufferUtils.check(36009, glStateSnapshot.intValue);
                           FramebufferUtils.check(36008, glStateSnapshot.intValue2);
                        }
                     } else {
                        FramebufferUtils.check(36009, glStateSnapshot.intValue);
                        FramebufferUtils.check(36008, glStateSnapshot.intValue2);
                     }

                     GL11.glColorMask(true, true, true, true);
                     GL11.glDisable(2929);
                     GL11.glEnable(3042);
                     RenderManager renderManager = WildClient.resolve();
                     if (renderManager == null) {
                        return;
                     }

                     HudEditorRenderer hudEditorRenderer = HudEditorRenderer.getINSTANCE();
                     hudEditorRenderer.invoke2(client4, renderManager, intValue4, intValue5);
                     hudEditorRenderer.invoke3();
                     Screen screen = client4.currentScreen;
                     ModernClickGuiScreen modernClickGuiScreen2 = screen instanceof ModernClickGuiScreen ? (ModernClickGuiScreen)screen : null;
                     boolean flag4 = DelayedFuse.check2();
                     FuseAudioEffect.invoke();
                     boolean flag5 = false;

                     try {
                        renderManager.invoke(intValue4, intValue5);
                        flag5 = true;
                        if (flag4) {
                           this.wild$drawCorruption(renderManager, intValue4, intValue5);
                        } else if (modernClickGuiScreen2 != null) {
                           modernClickGuiScreen2.invoke(renderManager, drawContext, intValue4, intValue5, client4.getRenderTickCounter().getDynamicDeltaTicks());
                        } else {
                           wild$cachedEventScreen.resolve(client4, renderManager, FontRegistry.fontObject, intValue4, intValue5, drawContext);
                           EventManager.post((Event)wild$cachedEventScreen);
                        }
                     } finally {
                        if (flag5) {
                           renderManager.invoke19();
                           hudEditorRenderer.invoke5();
                           if (!flag4 && modernClickGuiScreen2 != null) {
                              if (intValue6 != 0) {
                                 GL30.glBindFramebuffer(36160, intValue6);
                                 GL11.glDrawBuffer(36064);
                              }

                              BlurRenderer.getINSTANCE().invoke6();
                           }
                        }
                     }
                  } finally {
                     if (intValue6 != 0) {
                        GL30.glBindFramebuffer(36160, intValue6);
                        GL30.glFramebufferTexture2D(36160, 36064, 3553, 0, 0);
                     }

                     FramebufferUtils.restoreGlState(glStateSnapshot);
                  }
               }
            } else {
               WildClient.invoke(intValue4, intValue5);
            }
         }
      }
   }

   @Unique
   private void wild$drawCorruption(RenderManager renderManager2, int i, int j) {
      if (i > 0 && j > 0) {
         long longValue = System.currentTimeMillis();
         if (longValue - this.wild$lastCorruptionFrameMs > 28L || DelayedFuse.check11(9182, 120L, 0.36F, 42L)) {
            this.wild$lastCorruptionFrameMs = longValue;
            this.wild$heldTearY = wild$norm(DelayedFuse.measure12(901)) * j;
            this.wild$heldTearH = 8.0F + Math.abs(DelayedFuse.measure12(902)) * j * 0.22F;
            this.wild$heldTearShift = DelayedFuse.measure12(903) * i * 0.42F;
         }

         int intValue8 = DelayedFuse.compute4();
         float floatValue = DelayedFuse.measure();
         float floatValue2 = DelayedFuse.measure3(1250000000L, 2250000000L);
         if (DelayedFuse.check7()) {
            this.wild$panicWhite = Math.min(1.0F, this.wild$panicWhite + 0.68F + DelayedFuse.getFloatValue2() * 0.24F);
         } else {
            this.wild$panicWhite *= 0.58F;
         }

         if (DelayedFuse.check5()) {
            this.wild$panicBlack = Math.min(1.0F, this.wild$panicBlack + 0.38F + DelayedFuse.getFloatValue() * 0.32F);
         } else {
            this.wild$panicBlack *= 0.72F;
         }

         this.wild$drawNoHudVoid(renderManager2, i, j, intValue8, floatValue, floatValue2);
         this.wild$drawBacklightPulse(renderManager2, i, j, intValue8, floatValue);
         this.wild$drawScanMatrix(renderManager2, i, j, intValue8, floatValue);
         this.wild$drawHeldTear(renderManager2, i, j, intValue8, floatValue);
         this.wild$drawTconFailure(renderManager2, i, j, intValue8, floatValue);
         this.wild$drawVramFailure(renderManager2, i, j, intValue8, floatValue);
         this.wild$drawDeadPixels(renderManager2, i, j, intValue8, floatValue);
         this.wild$drawEdgePressure(renderManager2, i, j, intValue8, floatValue, floatValue2);
         this.wild$drawBlackout(renderManager2, i, j, intValue8, floatValue, floatValue2);
      }
   }

   @Unique
   private void wild$drawNoHudVoid(RenderManager renderManager3, int i, int j, int k, float f, float g) {
      int intValue9 = wild$alpha(42 + (int)(95.0F * DelayedFuse.measure9()));
      renderManager3.invoke4(0.0F, 0.0F, (float)i, (float)j, wild$rgba(intValue9, 0, 0, 0));
      if (k <= 2) {
         int intValue10 = wild$alpha(18 + (int)(36.0F * f));
         renderManager3.invoke4(0.0F, 0.0F, (float)i, (float)j, wild$rgba(intValue10, 2, 7, 13));
      }

      if (k >= 3) {
         int intValue11 = wild$alpha((int)(72.0F * f));
         renderManager3.invoke4(0.0F, 0.0F, (float)i, (float)j, wild$rgba(intValue11, 7, 0, 0));
      }

      if (g > 0.0F) {
         renderManager3.invoke4(0.0F, 0.0F, (float)i, (float)j, wild$rgba(wild$alpha((int)(120.0F * g)), 0, 0, 0));
      }
   }

   @Unique
   private void wild$drawBacklightPulse(RenderManager renderManager4, int i, int j, int k, float f) {
      if (this.wild$panicWhite > 0.01F) {
         int intValue12 = wild$alpha((int)(220.0F * this.wild$panicWhite));
         renderManager4.invoke4(0.0F, 0.0F, (float)i, (float)j, wild$rgba(intValue12, 255, 255, 255));
      }

      if (k >= 2 && DelayedFuse.check11(12001, 260L, 0.38F + DelayedFuse.getFloatValue2() * 0.28F, 28L)) {
         int intValue13 = wild$alpha(60 + (int)(130.0F * f));
         renderManager4.invoke4(0.0F, 0.0F, (float)i, (float)j, wild$rgba(intValue13, 230, 245, 255));
      }

      if (k >= 3 && DelayedFuse.check11(12002, 720L, 0.26F + DelayedFuse.getFloatValue() * 0.34F, 95L)) {
         int intValue14 = wild$alpha(130 + (int)(90.0F * f));
         renderManager4.invoke4(0.0F, 0.0F, (float)i, (float)j, wild$rgba(intValue14, 0, 0, 0));
      }
   }

   @Unique
   private void wild$drawScanMatrix(RenderManager renderManager5, int i, int j, int k, float f) {
      int intValue15 = k >= 3 ? 2 : 3;
      float floatValue3 = (float)(System.nanoTime() / 1400000L % intValue15);
      int intValue16 = wild$alpha(20 + (int)(54.0F * f));

      for (float floatValue4 = -floatValue3; floatValue4 < j; floatValue4 += intValue15) {
         renderManager5.invoke4(0.0F, floatValue4, (float)i, 1.0F, wild$rgba(intValue16, 0, 0, 0));
      }

      int intValue17 = k >= 3 ? 7 : 3;

      for (int intValue18 = 0; intValue18 < intValue17; intValue18++) {
         float floatValue5 = wild$norm(DelayedFuse.measure13(13000 + intValue18, 85L)) * i;
         float floatValue6 = 1.0F + Math.abs(DelayedFuse.measure13(13020 + intValue18, 130L)) * 5.0F * f;
         int intValue19 = wild$alpha(12 + (int)(56.0F * f));
         renderManager5.invoke4(floatValue5, 0.0F, floatValue6, (float)j, wild$rgba(intValue19, 255, 255, 255));
      }

      int intValue20 = k >= 3 ? 18 : (k >= 2 ? 9 : 4);

      for (int intValue21 = 0; intValue21 < intValue20; intValue21++) {
         float floatValue7 = wild$norm(DelayedFuse.measure13(13100 + intValue21, 18L + intValue21)) * j;
         float floatValue8 = 1.0F + Math.abs(DelayedFuse.measure13(13140 + intValue21, 44L)) * (k >= 3 ? 6.0F : 2.0F);
         int intValue22 = wild$alpha(28 + (int)(120.0F * f * Math.abs(DelayedFuse.measure13(13180 + intValue21, 31L))));
         renderManager5.invoke4(0.0F, floatValue7, (float)i, floatValue8, wild$rgba(intValue22, 210, 228, 255));
      }
   }

   @Unique
   private void wild$drawHeldTear(RenderManager renderManager6, int i, int j, int k, float f) {
      if (k >= 2) {
         int intValue23 = wild$alpha(36 + (int)(105.0F * f));
         float floatValue9 = Math.max(0.0F, Math.min((float)j, this.wild$heldTearY));
         float floatValue10 = Math.max(1.0F, Math.min(j * 0.45F, this.wild$heldTearH));
         float floatValue11 = this.wild$heldTearShift * f;
         renderManager6.invoke4(floatValue11, floatValue9, i + Math.abs(floatValue11) * 2.0F, floatValue10, wild$rgba(intValue23, 220, 220, 220));
         if (k >= 3) {
            renderManager6.invoke4(
               floatValue11 - 12.0F * f, floatValue9, i + Math.abs(floatValue11) * 2.0F, Math.max(1.0F, floatValue10 * 0.16F), wild$rgba(wild$alpha(intValue23 + 28), 255, 25, 38)
            );
            renderManager6.invoke4(
               floatValue11 + 8.0F * f, floatValue9 + floatValue10 * 0.34F, i + Math.abs(floatValue11) * 2.0F, Math.max(1.0F, floatValue10 * 0.12F), wild$rgba(wild$alpha(intValue23 + 18), 25, 255, 70)
            );
            renderManager6.invoke4(
               floatValue11 + 18.0F * f, floatValue9 + floatValue10 * 0.66F, i + Math.abs(floatValue11) * 2.0F, Math.max(1.0F, floatValue10 * 0.1F), wild$rgba(wild$alpha(intValue23 + 18), 40, 80, 255)
            );
         }
      }
   }

   @Unique
   private void wild$drawTconFailure(RenderManager renderManager7, int i, int j, int k, float f) {
      int intValue24 = DelayedFuse.compute5() + (k >= 3 ? 16 : 4);
      long longValue2 = k >= 3 ? 14L : 30L;

      for (int intValue25 = 0; intValue25 < intValue24; intValue25++) {
         float floatValue12 = wild$norm(DelayedFuse.measure13(14000 + intValue25 * 7, longValue2 + intValue25)) * j;
         float floatValue13 = 1.0F + Math.abs(DelayedFuse.measure13(14001 + intValue25 * 7, longValue2 + 11L)) * (k >= 3 ? 26.0F : 9.0F) * f;
         float floatValue14 = DelayedFuse.measure13(14002 + intValue25 * 7, longValue2) * i * (k >= 3 ? 0.44F : 0.18F) * f;
         float floatValue15 = i + Math.abs(floatValue14) * 2.0F;
         int intValue26 = wild$alpha(22 + (int)(118.0F * f * Math.abs(DelayedFuse.measure13(14003 + intValue25 * 7, longValue2))));
         int intValue27 = intValue25 % 11;
         if (intValue27 == 0) {
            renderManager7.invoke4(floatValue14, floatValue12, floatValue15, floatValue13, wild$rgba(intValue26, 255, 25, 35));
         } else if (intValue27 == 1) {
            renderManager7.invoke4(floatValue14, floatValue12, floatValue15, floatValue13, wild$rgba(intValue26, 28, 255, 70));
         } else if (intValue27 == 2) {
            renderManager7.invoke4(floatValue14, floatValue12, floatValue15, floatValue13, wild$rgba(intValue26, 42, 86, 255));
         } else if (intValue27 == 3) {
            renderManager7.invoke4(floatValue14, floatValue12, floatValue15, floatValue13, wild$rgba(wild$alpha(intValue26 + 30), 255, 255, 255));
         } else {
            renderManager7.invoke4(floatValue14, floatValue12, floatValue15, floatValue13, wild$rgba(intValue26, 210, 210, 210));
         }
      }

      if (k >= 3) {
         int intValue28 = 3 + (int)(8.0F * DelayedFuse.getFloatValue3());

         for (int intValue29 = 0; intValue29 < intValue28; intValue29++) {
            float floatValue16 = wild$norm(DelayedFuse.measure13(14200 + intValue29, 44L)) * j;
            float floatValue17 = 12.0F + Math.abs(DelayedFuse.measure13(14250 + intValue29, 58L)) * j * 0.18F * f;
            float floatValue18 = DelayedFuse.measure13(14300 + intValue29, 32L) * i * 0.58F * f;
            int intValue30 = wild$alpha(28 + (int)(112.0F * f));
            renderManager7.invoke4(floatValue18, floatValue16, i + Math.abs(floatValue18) * 2.0F, floatValue17, wild$rgba(intValue30, 230, 230, 230));
         }
      }
   }

   @Unique
   private void wild$drawVramFailure(RenderManager renderManager8, int i, int j, int k, float f) {
      if (k >= 2) {
         int intValue31 = DelayedFuse.compute6() + (k >= 3 ? 22 : 4);
         long longValue3 = k >= 3 ? 32L : 76L;

         for (int intValue32 = 0; intValue32 < intValue31; intValue32++) {
            float floatValue19 = wild$norm(DelayedFuse.measure13(15000 + intValue32 * 6, longValue3)) * i;
            float floatValue20 = wild$norm(DelayedFuse.measure13(15001 + intValue32 * 6, longValue3 + 7L)) * j;
            float floatValue21 = 3.0F + Math.abs(DelayedFuse.measure13(15002 + intValue32 * 6, longValue3 + 13L)) * (k >= 3 ? 210.0F : 76.0F) * f;
            float floatValue22 = 2.0F + Math.abs(DelayedFuse.measure13(15003 + intValue32 * 6, longValue3 + 19L)) * (k >= 3 ? 116.0F : 38.0F) * f;
            int intValue33 = wild$alpha(26 + (int)(130.0F * f));
            int intValue34 = intValue32 % 17;
            int intValue35 = wild$byte(DelayedFuse.measure13(15004 + intValue32 * 6, longValue3 + 23L));
            if (intValue34 == 0) {
               renderManager8.invoke4(floatValue19, floatValue20, floatValue21, floatValue22, wild$rgba(intValue33, 255, 0, 0));
            } else if (intValue34 == 1) {
               renderManager8.invoke4(floatValue19, floatValue20, floatValue21, floatValue22, wild$rgba(intValue33, 0, 255, 70));
            } else if (intValue34 == 2) {
               renderManager8.invoke4(floatValue19, floatValue20, floatValue21, floatValue22, wild$rgba(intValue33, 40, 80, 255));
            } else if (intValue34 == 3) {
               renderManager8.invoke4(floatValue19, floatValue20, floatValue21, floatValue22, wild$rgba(wild$alpha(intValue33 + 30), 255, 255, 255));
            } else if (intValue34 == 4 && k >= 3) {
               renderManager8.invoke4(floatValue19, floatValue20, floatValue21, floatValue22, wild$rgba(wild$alpha(intValue33 + 20), 0, 0, 0));
            } else {
               renderManager8.invoke4(floatValue19, floatValue20, floatValue21, floatValue22, wild$rgba(intValue33, intValue35, intValue35, intValue35));
            }
         }
      }
   }

   @Unique
   private void wild$drawDeadPixels(RenderManager renderManager9, int i, int j, int k, float f) {
      int intValue36 = DelayedFuse.compute7() + (k >= 3 ? 120 : 24);

      for (int intValue37 = 0; intValue37 < intValue36; intValue37++) {
         float floatValue23 = wild$norm(DelayedFuse.measure14(16000 + intValue37 * 3)) * i;
         float floatValue24 = wild$norm(DelayedFuse.measure14(16001 + intValue37 * 3)) * j;
         if (k >= 3 || !(DelayedFuse.measure13(16002 + intValue37 * 3, 230L) < -0.42F)) {
            float floatValue25 = k >= 3 && intValue37 % 9 == 0 ? 2.0F : 1.0F;
            int intValue38 = wild$alpha(36 + (int)(205.0F * f * Math.abs(DelayedFuse.measure13(16100 + intValue37, 110L))));
            int intValue39 = intValue37 % 19;
            if (intValue39 == 0) {
               renderManager9.invoke4(floatValue23, floatValue24, floatValue25, floatValue25, wild$rgba(intValue38, 255, 0, 0));
            } else if (intValue39 == 1) {
               renderManager9.invoke4(floatValue23, floatValue24, floatValue25, floatValue25, wild$rgba(intValue38, 0, 255, 50));
            } else if (intValue39 == 2) {
               renderManager9.invoke4(floatValue23, floatValue24, floatValue25, floatValue25, wild$rgba(intValue38, 40, 90, 255));
            } else if (intValue39 == 3) {
               renderManager9.invoke4(floatValue23, floatValue24, floatValue25, floatValue25, wild$rgba(intValue38, 0, 0, 0));
            } else {
               renderManager9.invoke4(floatValue23, floatValue24, floatValue25, floatValue25, wild$rgba(intValue38, 235, 235, 235));
            }
         }
      }
   }

   @Unique
   private void wild$drawEdgePressure(RenderManager renderManager10, int i, int j, int k, float f, float g) {
      float floatValue26 = j * (0.04F + Math.abs(DelayedFuse.measure13(17000, 70L)) * 0.12F * f);
      float floatValue27 = j * (0.04F + Math.abs(DelayedFuse.measure13(17001, 80L)) * 0.14F * f);
      float floatValue28 = i * (0.012F + Math.abs(DelayedFuse.measure13(17002, 95L)) * 0.055F * f);
      float floatValue29 = i * (0.012F + Math.abs(DelayedFuse.measure13(17003, 105L)) * 0.055F * f);
      renderManager10.invoke4(0.0F, 0.0F, (float)i, floatValue26, wild$rgba(wild$alpha(55 + (int)(145.0F * f)), 0, 0, 0));
      renderManager10.invoke4(0.0F, j - floatValue27, (float)i, floatValue27, wild$rgba(wild$alpha(55 + (int)(155.0F * f)), 0, 0, 0));
      if (k >= 3) {
         renderManager10.invoke4(0.0F, 0.0F, floatValue28, (float)j, wild$rgba(wild$alpha(45 + (int)(130.0F * f)), 0, 0, 0));
         renderManager10.invoke4(i - floatValue29, 0.0F, floatValue29, (float)j, wild$rgba(wild$alpha(45 + (int)(130.0F * f)), 0, 0, 0));
      }

      if (g > 0.0F) {
         renderManager10.invoke4(0.0F, 0.0F, (float)i, (float)j, wild$rgba(wild$alpha((int)(150.0F * g)), 0, 0, 0));
      }
   }

   @Unique
   private void wild$drawBlackout(RenderManager renderManager11, int i, int j, int k, float f, float g) {
      if (this.wild$panicBlack > 0.01F) {
         renderManager11.invoke4(0.0F, 0.0F, (float)i, (float)j, wild$rgba(wild$alpha((int)(235.0F * this.wild$panicBlack)), 0, 0, 0));
      }

      if (DelayedFuse.check6()) {
         renderManager11.invoke4(0.0F, 0.0F, (float)i, (float)j, wild$rgba(wild$alpha(190 + (int)(62.0F * f)), 0, 0, 0));
      }

      if (k >= 4) {
         float floatValue30 = (float)(System.nanoTime() / 2300000L % Math.max(1, j));
         renderManager11.invoke4(0.0F, floatValue30, (float)i, 2.0F, wild$rgba(235, 255, 255, 255));
         renderManager11.invoke4(0.0F, floatValue30 + 3.0F, (float)i, 1.0F, wild$rgba(130, 255, 30, 60));
         renderManager11.invoke4(0.0F, floatValue30 + 5.0F, (float)i, 1.0F, wild$rgba(130, 40, 90, 255));
         renderManager11.invoke4(0.0F, 0.0F, (float)i, (float)j, wild$rgba(wild$alpha((int)(120.0F + 125.0F * g)), 0, 0, 0));
      }

      if (k >= 5) {
         renderManager11.invoke4(0.0F, 0.0F, (float)i, (float)j, -16777216);
      }
   }

   @Unique
   private static float wild$norm(float f) {
      return (f + 1.0F) * 0.5F;
   }

   @Unique
   private static int wild$byte(float f) {
      int intValue40 = (int)(wild$norm(f) * 255.0F);
      if (intValue40 < 0) {
         return 0;
      } else {
         return intValue40 > 255 ? 255 : intValue40;
      }
   }

   @Unique
   private static int wild$alpha(int i) {
      if (i < 0) {
         return 0;
      } else {
         return i > 255 ? 255 : i;
      }
   }

   @Unique
   private static int wild$rgba(int i, int j, int k, int l) {
      return (i & 0xFF) << 24 | (j & 0xFF) << 16 | (k & 0xFF) << 8 | l & 0xFF;
   }

   @Inject(
      method = {"renderChat"},
      at = {@At("HEAD")},
      cancellable = true,
      require = 0
   )
   private void wild$cancelChatDuringCorruption(DrawContext drawContext, RenderTickCounter renderTickCounter, CallbackInfo callbackInfo) {
      if (DelayedFuse.check2()) {
         callbackInfo.cancel();
      }
   }

   @Unique
   private static boolean wild$customHotbarActive() {
      if (HudModule.GROUP_SETTING.isEnabled("HotBar")
         && WildClient.isInitialized()
         && WildClient.INSTANCE != null
         && WildClient.INSTANCE.moduleManager != null) {
         HudModule hudModule = WildClient.INSTANCE.moduleManager.getModule(HudModule.class);
         return hudModule != null && hudModule.enabled;
      } else {
         return false;
      }
   }

   @Unique
   private static boolean wild$noRenderPotions() {
      return Removals.check("Иконки эффектов");
   }
}
