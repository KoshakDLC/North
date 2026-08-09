package ru.metaculture.protection;

import dev.redstones.mediaplayerinfo.IMediaSession;
import dev.redstones.mediaplayerinfo.MediaInfo;
import dev.redstones.mediaplayerinfo.MediaPlayerInfo;
import java.io.ByteArrayInputStream;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.util.Identifier;

@HudElementInfo(
   resolve = "MusicPlayer",
   resolve2 = "w"
)
public final class MusicPlayerHud extends HudElement {
   private static final MusicPlayerHud INSTANCE = new MusicPlayerHud();
   private static final String OZHIDANIE = "Ожидание...";
   private static final String NET_DANNYH = "Нет данных";
   private static final long TIMESTAMP = 160L;
   private static final Animation ANIMATION = new Animation();
   private static final ExecutorService EXECUTOR_SERVICE = Executors.newSingleThreadExecutor(runnable -> {
      Thread thread = new Thread(runnable, "Wild-Media-Fetch");
      thread.setDaemon(true);
      return thread;
   });
   private volatile String ozhidanie = "Ожидание...";
   private volatile String netDannyh = "Нет данных";
   private volatile boolean flag = false;
   private volatile double doubleValue = 0.0;
   private volatile long timestamp = 0L;
   private volatile long timestamp2 = 0L;
   private volatile long timestamp3 = 0L;
   private volatile long timestamp4 = 10000000L;
   private volatile long timestamp5 = 0L;
   private volatile boolean flag2 = false;
   private volatile double doubleValue2 = 0.0;
   private final Animation animation = new Animation();
   private volatile float floatValue = 0.0F;
   private volatile float floatValue2 = 0.0F;
   private volatile float floatValue3 = 0.0F;
   private volatile float floatValue4 = 0.0F;
   private volatile byte[] bytes = null;
   private volatile int intValue = 0;
   private volatile boolean flag3 = false;
   private int intValue2 = Integer.MIN_VALUE;
   private int intValue3 = -1;
   private Identifier identifier = null;
   private volatile int intValue4 = 0;
   private volatile int intValue5 = 0;
   private MediaPlayerInfo mediaPlayerInfo;
   private long timestamp6 = 0L;
   private static boolean flag4 = false;
   private final AtomicBoolean atomicBoolean = new AtomicBoolean(false);
   private final AtomicReference<MusicPlayerHud.MusicPlayerHudPositionData> atomicReference = new AtomicReference<>();
   private final Animation animation2 = new Animation();

   private MusicPlayerHud() {
      ru.metaculture.protection.HudPresetManager.invoke2(this);
   }

   public static MusicPlayerHud getINSTANCE() {
      return INSTANCE;
   }

   public static void invoke(RenderManager renderManager) {
      INSTANCE.invoke3(renderManager);
   }

   public static void invoke2() {
      EXECUTOR_SERVICE.shutdownNow();
      INSTANCE.atomicReference.set(null);
      INSTANCE.atomicBoolean.set(false);
   }

   public void invoke3(RenderManager renderManager2) {
      if (MinecraftAccessor.a_.player != null) {
         ANIMATION.check();
         ANIMATION.resolve4(1.0, 0.22F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         float floatValue = ANIMATION.measure3();
         if (!(floatValue <= 0.01F)) {
            float floatValue2 = HudEditorRenderer.getINSTANCE().getFloatValue();
            float floatValue3 = HudEditorRenderer.getINSTANCE().getFloatValue2();
            boolean flag = HudEditorRenderer.getINSTANCE().isFlag4();
            boolean flag2 = HudEditorRenderer.getINSTANCE().isFlag3();
            if (flag
               && this.floatValue3 > 0.0F
               && this.check(floatValue2, floatValue3, this.floatValue - 4.0F, this.floatValue2, this.floatValue3 + 8.0F, this.floatValue4)) {
               this.flag2 = true;
            }

            if (this.flag2) {
               HudEditorRenderer.getINSTANCE().invoke();
            }

            this.invoke5();
            this.invoke7();
            this.invoke9();
            float floatValue4 = 7.0F;
            float floatValue5 = 5.0F;
            float floatValue6 = 160.0F;
            float floatValue7 = 26.0F;
            float floatValue8 = 24.0F;
            float floatValue9 = floatValue6 + floatValue4 * 2.0F;
            float floatValue10 = floatValue4 + floatValue6 + floatValue5 + floatValue7 + floatValue5 + floatValue8 + floatValue4;
            HudEditorRenderer.HudEditorRendererState hudEditorRendererState = HudEditorRenderer.getINSTANCE().resolve("HUD_MusicPlayer", 10.0F, 10.0F, floatValue9, floatValue10);
            float floatValue11 = hudEditorRendererState.floatValue;
            float floatValue12 = hudEditorRendererState.floatValue2;
            float floatValue13 = hudEditorRendererState.floatValue3;
            float floatValue14 = hudEditorRendererState.floatValue4;
            float floatValue15 = MinecraftAccessor.a_.getWindow().getFramebufferWidth();
            float floatValue16 = MinecraftAccessor.a_.getWindow().getFramebufferHeight();
            if (floatValue13 > 1.0F && floatValue14 > 1.0F) {
               floatValue11 = Math.max(2.0F, Math.min(floatValue11, floatValue15 - floatValue13 - 2.0F));
               floatValue12 = Math.max(2.0F, Math.min(floatValue12, floatValue16 - floatValue14 - 2.0F));
            }

            this.invoke3(floatValue11, floatValue12, floatValue13, floatValue14);
            float floatValue17 = floatValue13 / Math.max(1.0F, floatValue9);
            float floatValue18 = floatValue14 / Math.max(1.0F, floatValue10);
            float floatValue19 = Math.min(floatValue17, floatValue18);
            float floatValue20 = floatValue4 * floatValue17;
            float floatValue21 = floatValue4 * floatValue18;
            float floatValue22 = floatValue5 * floatValue18;
            float floatValue23 = floatValue6 * floatValue17;
            float floatValue24 = floatValue6 * floatValue18;
            float floatValue25 = floatValue7 * floatValue18;
            float floatValue26 = floatValue8 * floatValue18;
            float floatValue27 = floatValue * this.prozrachnost.getValue();
            float floatValue28 = this.measureSelf(floatValue27);
            int intValue = (int)(255.0F * floatValue27);
            int intValue2 = this.compute(floatValue27);
            int intValue3 = this.compute2(floatValue27);
            int intValue4 = this.compute3(floatValue27);
            int intValue5 = this.compute5(floatValue27);
            int intValue6 = this.compute6(floatValue27);
            int intValue7 = this.compute7(floatValue27);
            int intValue8 = this.compute8(floatValue27);
            int intValue9 = this.stilistika.getValue().equals("Светлый") ? intValue8 : ColorUtils.compute43(255, 255, 255, intValue);
            boolean flag3 = this.check8();
            float floatValue29 = 14.0F;
            this.invoke(renderManager2, floatValue11, floatValue12, floatValue13, floatValue14, floatValue29, floatValue27);
            float floatValue30 = floatValue11 + floatValue20;
            float floatValue31 = floatValue12 + floatValue21;
            if (flag3) {
               this.invoke2(renderManager2, floatValue30, floatValue31, floatValue23, floatValue24, 11.0F, floatValue27);
            }

            renderManager2.invoke24(floatValue30, floatValue31, floatValue23, floatValue24, 11.0F, 11.0F, 4.0F, 4.0F);
            if (this.identifier != null) {
               int intValue10 = compute(this.identifier);
               if (intValue10 > 0) {
                  float floatValue32 = 0.0F;
                  float floatValue33 = 0.0F;
                  float floatValue34 = 1.0F;
                  float floatValue35 = 1.0F;
                  if (this.intValue4 > 0 && this.intValue5 > 0) {
                     if (this.intValue4 > this.intValue5) {
                        float floatValue36 = (float)this.intValue5 / this.intValue4;
                        float floatValue37 = (1.0F - floatValue36) / 2.0F;
                        floatValue32 = floatValue37;
                        floatValue34 = 1.0F - floatValue37;
                     } else if (this.intValue5 > this.intValue4) {
                        float floatValue38 = (float)this.intValue4 / this.intValue5;
                        float floatValue39 = (1.0F - floatValue38) / 2.0F;
                        floatValue33 = floatValue39;
                        floatValue35 = 1.0F - floatValue39;
                     }
                  }

                  renderManager2.invoke11(intValue10, floatValue30, floatValue31, floatValue23, floatValue24, floatValue32, floatValue33, floatValue34, floatValue35);
               } else if (!flag3) {
                  renderManager2.invoke5(floatValue30, floatValue31, floatValue23, floatValue24, 0.0F, intValue3);
               }
            } else if (!flag3) {
               renderManager2.invoke5(floatValue30, floatValue31, floatValue23, floatValue24, 0.0F, intValue3);
            }

            float floatValue40 = 90.0F * floatValue18;
            float floatValue41 = floatValue31 + floatValue24 - floatValue40;
            renderManager2.invoke38(
               floatValue30,
               floatValue41,
               floatValue23,
               floatValue40,
               11.0F,
               11.0F,
               4.0F,
               4.0F,
               ColorUtils.compute43(0, 0, 0, 0),
               ColorUtils.compute43(0, 0, 0, (int)(220.0F * floatValue28))
            );
            float floatValue42 = 26.0F * floatValue19;
            float floatValue43 = 22.0F * floatValue19;
            float floatValue44 = floatValue31 + floatValue24 - 32.0F * floatValue18;
            float floatValue45 = floatValue23 - 16.0F * floatValue17;
            this.invoke4(
               renderManager2,
               FontRegistry.fontObject4,
               this.ozhidanie,
               floatValue30 + 10.0F * floatValue17,
               floatValue44,
               floatValue42,
               intValue6,
               floatValue41,
               floatValue40,
               floatValue45,
               floatValue30 + floatValue23 / 2.0F
            );
            this.invoke4(
               renderManager2,
               FontRegistry.fontObject,
               this.netDannyh,
               floatValue30 + 10.0F * floatValue17,
               floatValue44 + 15.0F * floatValue18,
               floatValue43,
               intValue7,
               floatValue41,
               floatValue40,
               floatValue45,
               floatValue30 + floatValue23 / 2.0F
            );
            renderManager2.invoke25();
            float floatValue46 = floatValue31 + floatValue24 + floatValue22;
            if (flag3) {
               this.invoke2(renderManager2, floatValue30, floatValue46, floatValue23, floatValue25, 7.0F, floatValue27);
            } else {
               renderManager2.invoke6(floatValue30, floatValue46, floatValue23, floatValue25, 4.0F, 4.0F, 4.0F, 4.0F, intValue4);
               if (this.check2()) {
                  renderManager2.invoke28(floatValue30, floatValue46, floatValue23, floatValue25, 4.0F, intValue5, 1.0F);
               }
            }

            float floatValue47 = 20.0F * floatValue19;
            float floatValue48 = floatValue30 + floatValue23 / 2.0F;
            float floatValue49 = floatValue46 + floatValue25 / 2.0F + 4.0F * floatValue18;
            String text = this.flag ? "x" : "p";
            String text2 = "z";
            String text3 = "c";
            float floatValue50 = TextMeasureCache.resolve(FontRegistry.fontObject8, text, floatValue47).floatValue;
            float floatValue51 = TextMeasureCache.resolve(FontRegistry.fontObject8, text2, floatValue47).floatValue;
            float floatValue52 = TextMeasureCache.resolve(FontRegistry.fontObject8, text3, floatValue47).floatValue;
            float floatValue53 = 22.0F * floatValue17;
            renderManager2.invoke69(FontRegistry.fontObject8, floatValue48 - floatValue53 - floatValue51 / 2.0F, floatValue49, floatValue47, text2, intValue9);
            renderManager2.invoke69(FontRegistry.fontObject8, floatValue48 - floatValue50 / 2.0F, floatValue49, floatValue47, text, intValue9);
            renderManager2.invoke69(FontRegistry.fontObject8, floatValue48 + floatValue53 - floatValue52 / 2.0F, floatValue49, floatValue47, text3, intValue9);
            if (flag && !this.flag2) {
               float floatValue54 = 24.0F * floatValue17;
               if (this.check(floatValue2, floatValue3, floatValue48 - floatValue53 - floatValue54 / 2.0F, floatValue46, floatValue54, floatValue25)) {
                  if (NativeMediaController.isFlag()) {
                     NativeMediaController.invoke3();
                  }
               } else if (this.check(floatValue2, floatValue3, floatValue48 - floatValue54 / 2.0F, floatValue46, floatValue54, floatValue25)) {
                  if (NativeMediaController.isFlag()) {
                     NativeMediaController.invoke();
                  }
               } else if (this.check(floatValue2, floatValue3, floatValue48 + floatValue53 - floatValue54 / 2.0F, floatValue46, floatValue54, floatValue25) && NativeMediaController.isFlag()) {
                  NativeMediaController.invoke2();
               }
            }

            float floatValue55 = floatValue46 + floatValue25 + floatValue22;
            if (flag3) {
               this.invoke2(renderManager2, floatValue30, floatValue55, floatValue23, floatValue26, 8.0F, floatValue27);
            } else {
               renderManager2.invoke6(floatValue30, floatValue55, floatValue23, floatValue26, 4.0F, 4.0F, 11.0F, 11.0F, intValue4);
            }

            float floatValue56 = 20.0F * floatValue19;
            String text4 = this.resolve(this.timestamp2);
            float floatValue57 = TextMeasureCache.resolve(FontRegistry.fontObject, text4, floatValue56).floatValue;
            float floatValue58 = 10.0F * floatValue17;
            float floatValue59 = 8.0F * floatValue17;
            float floatValue60 = floatValue30 + floatValue58 + floatValue57 + floatValue59;
            float floatValue61 = floatValue23 - floatValue58 * 2.0F - floatValue57 * 2.0F - floatValue59 * 2.0F;
            this.floatValue = floatValue60;
            this.floatValue2 = floatValue55;
            this.floatValue3 = floatValue61;
            this.floatValue4 = floatValue26;
            long longValue = this.timestamp;
            boolean flag4 = this.check(floatValue2, floatValue3, floatValue60 - 4.0F * floatValue17, floatValue55, floatValue61 + 8.0F * floatValue17, floatValue26);
            if (this.flag2) {
               this.doubleValue2 = Math.max(0.0, Math.min(1.0, (double)((floatValue2 - floatValue60) / Math.max(1.0F, floatValue61))));
               longValue = (long)(this.doubleValue2 * this.timestamp2);
               if (!flag2) {
                  this.flag2 = false;
                  if (this.timestamp2 > 0L && NativeMediaController.isFlag()) {
                     long longValue2 = (long)((double)longValue / this.timestamp4 * 1000.0);
                     NativeMediaController.invoke4(longValue2);
                     this.timestamp = longValue;
                     this.timestamp5 = System.currentTimeMillis();
                     this.timestamp3 = System.currentTimeMillis();
                  }
               }
            } else if (this.flag && this.timestamp2 > 0L) {
               long longValue3 = System.currentTimeMillis() - this.timestamp3;
               long longValue4 = (long)(longValue3 * (this.timestamp4 / 1000.0));
               longValue += Math.max(0L, longValue4);
               if (longValue > this.timestamp2) {
                  longValue = this.timestamp2;
               }
            }

            this.doubleValue = this.timestamp2 > 0L ? (double)longValue / this.timestamp2 : 0.0;
            String text5 = this.resolve(longValue);
            float floatValue62 = floatValue55 + floatValue26 / 2.0F + 3.0F * floatValue18;
            renderManager2.invoke69(FontRegistry.fontObject, floatValue30 + floatValue58, floatValue62, floatValue56, text5, intValue7);
            renderManager2.invoke69(FontRegistry.fontObject, floatValue30 + floatValue23 - floatValue58 - floatValue57, floatValue62, floatValue56, text4, intValue7);
            this.animation.check();
            this.animation.resolve4(!flag4 && !this.flag2 ? 0.0 : 1.0, 0.15F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            float floatValue63 = 4.0F * floatValue18 + 4.0F * floatValue18 * this.animation.measure3();
            float floatValue64 = floatValue55 + (floatValue26 - floatValue63) / 2.0F;
            this.animation2.check();
            this.animation2.resolve4((float)this.doubleValue, this.flag2 ? 0.05F : 0.2F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION, false);
            if (flag3) {
               this.invoke2(renderManager2, floatValue60, floatValue64, floatValue61, floatValue63, floatValue63 / 2.0F, floatValue27);
            } else {
               renderManager2.invoke5(floatValue60, floatValue64, floatValue61, floatValue63, floatValue63 / 2.0F, ColorUtils.compute43(100, 100, 100, (int)(80.0F * floatValue28)));
            }

            renderManager2.invoke5(floatValue60, floatValue64, floatValue61 * this.animation2.measure3(), floatValue63, floatValue63 / 2.0F, intValue8);
            HudEditorRenderer.getINSTANCE().invoke6(hudEditorRendererState);
            HudSettingsRenderer.invoke(
               renderManager2,
               this,
               floatValue11,
               floatValue12,
               floatValue13,
               floatValue14,
               MinecraftAccessor.a_.getWindow().getScaledWidth(),
               MinecraftAccessor.a_.getWindow().getScaledHeight(),
               hudEditorRendererState.floatValue6,
               HudEditorRenderer.getINSTANCE().getFloatValue(),
               HudEditorRenderer.getINSTANCE().getFloatValue2(),
               HudEditorRenderer.getINSTANCE().isFlag4(),
               HudEditorRenderer.getINSTANCE().isFlag3()
            );
         }
      }
   }

   private boolean check(float f, float g, float h, float i, float j, float k) {
      return f >= h && f <= h + j && g >= i && g <= i + k;
   }

   private String resolve(long l) {
      if (l <= 0L) {
         return "0:00";
      } else {
         long longValue5 = l / this.timestamp4;
         long longValue6 = longValue5 % 60L;
         return longValue5 / 60L + (longValue6 < 10L ? ":0" : ":") + longValue6;
      }
   }

   private void invoke4(
      RenderManager renderManager3, FontObject fontObject, String string, float f, float g, float h, int i, float j, float k, float l, float m
   ) {
      float floatValue65 = TextMeasureCache.resolve(fontObject, string, h).floatValue;
      if (floatValue65 <= l) {
         renderManager3.invoke69(fontObject, m - floatValue65 / 2.0F, g, h, string, i);
      } else {
         float floatValue66 = floatValue65 - l;
         long longValue7 = 8000L;
         float floatValue67 = (float)(System.currentTimeMillis() % longValue7) / (float)longValue7;
         float floatValue68 = floatValue67 < 0.2F
            ? 0.0F
            : (
               floatValue67 < 0.45F
                  ? this.measureSelf((floatValue67 - 0.2F) / 0.3F)
                  : (floatValue67 < 0.7F ? 1.0F : (floatValue67 < 0.95F ? 1.0F - this.measureSelf((floatValue67 - 0.7F) / 0.25F) : 0.0F))
            );
         renderManager3.invoke24(f, j, l, k, 0.0F, 0.0F, 0.0F, 0.0F);
         renderManager3.invoke69(fontObject, f - floatValue66 * floatValue68, g, h, string, i);
         renderManager3.invoke25();
      }
   }

   private float measureSelf(float f) {
      float floatValue69 = 2.0F;
      float floatValue70 = floatValue69 + 1.0F;
      float floatValue71 = f - 1.0F;
      return 1.0F + floatValue70 * floatValue71 * floatValue71 * floatValue71 + floatValue69 * floatValue71 * floatValue71;
   }

   private void invoke5() {
      long longValue8 = System.currentTimeMillis();
      if (longValue8 - this.timestamp6 >= 160L) {
         this.timestamp6 = longValue8;
         if (this.atomicBoolean.compareAndSet(false, true)) {
            EXECUTOR_SERVICE.execute(() -> {
               boolean flag5 = false ;

               label49: {
                  try {
                     flag5 = true;
                     this.invoke6(this.resolve2());
                     flag5 = false;
                     break label49;
                  } catch (Throwable exception) {
                     if (!flag4) {
                        flag4 = true;
                     }

                     this.invoke6(MusicPlayerHud.MusicPlayerHudPositionData.empty());
                     flag5 = false;
                  } finally {
                     if (flag5) {
                        this.atomicBoolean.set(false);
                     }
                  }

                  this.atomicBoolean.set(false);
                  return;
               }

               this.atomicBoolean.set(false);
            });
         }
      }
   }

   private MusicPlayerHud.MusicPlayerHudPositionData resolve2() {
      if (this.mediaPlayerInfo == null) {
         this.mediaPlayerInfo = MediaPlayerInfo.INSTANCE;
      }

      List items = this.mediaPlayerInfo.getMediaSessions();
      if (items != null && !items.isEmpty()) {
         MediaInfo mediaInfo2 = null;

         for (IMediaSession iMediaSession : (List<IMediaSession>)items) {
            if (iMediaSession != null) {
               MediaInfo mediaInfo3 = iMediaSession.getMedia();
               if (mediaInfo3 != null && this.check2(mediaInfo3)) {
                  if (mediaInfo2 == null) {
                     mediaInfo2 = mediaInfo3;
                  }

                  if (mediaInfo3.isPlaying()) {
                     mediaInfo2 = mediaInfo3;
                     break;
                  }
               }
            }
         }

         return mediaInfo2 == null ? MusicPlayerHud.MusicPlayerHudPositionData.empty() : MusicPlayerHud.MusicPlayerHudPositionData.from(mediaInfo2);
      } else {
         return MusicPlayerHud.MusicPlayerHudPositionData.empty();
      }
   }

   private void invoke6(MusicPlayerHud.MusicPlayerHudPositionData musicPlayerHudPositionData) {
      this.atomicReference.set(musicPlayerHudPositionData);
      if (MinecraftAccessor.a_ != null) {
         MinecraftAccessor.a_.execute(this::invoke7);
      }
   }

   private void invoke7() {
      MusicPlayerHud.MusicPlayerHudPositionData musicPlayerHudPositionData2 = this.atomicReference.getAndSet(null);
      if (musicPlayerHudPositionData2 != null) {
         if (!musicPlayerHudPositionData2.available()) {
            this.invoke10();
         } else {
            this.ozhidanie = musicPlayerHudPositionData2.title();
            this.netDannyh = musicPlayerHudPositionData2.artist();
            this.flag = musicPlayerHudPositionData2.playing();
            if (!this.flag2 && System.currentTimeMillis() - this.timestamp5 > 2000L) {
               this.timestamp = musicPlayerHudPositionData2.position();
            }

            this.timestamp2 = musicPlayerHudPositionData2.duration();
            this.timestamp3 = System.currentTimeMillis();
            if (this.timestamp2 > 360000000L) {
               this.timestamp4 = 10000000L;
            } else if (this.timestamp2 > 100000L) {
               this.timestamp4 = 1000L;
            } else {
               this.timestamp4 = 1L;
            }

            this.invoke8(musicPlayerHudPositionData2.thumbnail());
         }
      }
   }

   private void invoke8(byte[] bs) {
      if (bs != null && bs.length > 0) {
         int intValue11 = Arrays.hashCode(bs);
         if (intValue11 != this.intValue || this.bytes == null || this.bytes.length != bs.length) {
            this.bytes = Arrays.copyOf(bs, bs.length);
            this.intValue = intValue11;
            this.flag3 = true;
         }
      } else if (this.bytes != null || this.intValue != 0) {
         this.bytes = null;
         this.intValue = 0;
         this.flag3 = true;
      }
   }

   private void invoke9() {
      byte[] byteValues = this.bytes;
      int intValue12 = this.intValue;
      boolean flag6 = this.flag3;
      if (byteValues == null) {
         if (flag6) {
            this.flag3 = false;
            this.intValue2 = Integer.MIN_VALUE;
            this.intValue3 = -1;
            this.intValue4 = 0;
            this.intValue5 = 0;
            if (this.identifier != null) {
               MinecraftAccessor.a_.getTextureManager().destroyTexture(this.identifier);
               this.identifier = null;
            }
         }
      } else if (flag6 || intValue12 != this.intValue2 || byteValues.length != this.intValue3) {
         try {
            this.flag3 = false;
            this.intValue2 = intValue12;
            this.intValue3 = byteValues.length;
            NativeImage nativeImage = NativeImage.read(new ByteArrayInputStream(byteValues));
            this.intValue4 = nativeImage.getWidth();
            this.intValue5 = nativeImage.getHeight();
            if (this.identifier != null) {
               MinecraftAccessor.a_.getTextureManager().destroyTexture(this.identifier);
            }

            NativeImageBackedTexture nativeImageBackedTexture = new NativeImageBackedTexture(() -> "media_cover", nativeImage);
            this.identifier = Identifier.of("wild", "media_cover_" + System.nanoTime());
            MinecraftAccessor.a_.getTextureManager().registerTexture(this.identifier, nativeImageBackedTexture);
         } catch (Exception exception2) {
         }
      }
   }

   private void invoke10() {
      this.ozhidanie = "Ожидание...";
      this.netDannyh = "Нет данных";
      this.flag = false;
      this.doubleValue = 0.0;
      this.timestamp = 0L;
      this.timestamp2 = 0L;
      this.timestamp3 = System.currentTimeMillis();
      if (this.bytes != null || this.intValue != 0) {
         this.bytes = null;
         this.intValue = 0;
         this.flag3 = true;
      }
   }

   private boolean check2(MediaInfo mediaInfo) {
      if (mediaInfo == null) {
         return false;
      } else {
         String text6 = mediaInfo.getTitle();
         String text7 = mediaInfo.getArtist();
         return text6 != null && !text6.isBlank()
            || text7 != null && !text7.isBlank()
            || mediaInfo.getDuration() > 0L
            || mediaInfo.getPosition() > 0L
            || mediaInfo.isPlaying();
      }
   }

   private static int compute(Identifier identifier) {
      if (MinecraftAccessor.a_ == null) {
         return -1;
      } else {
         AbstractTexture abstractTexture = MinecraftAccessor.a_.getTextureManager().getTexture(identifier);
         return abstractTexture != null && abstractTexture.getGlTexture() instanceof GlTexture glTexture ? glTexture.getGlId() : -1;
      }
   }

   record MusicPlayerHudPositionData(boolean available, String title, String artist, long position, long duration, boolean playing, byte[] thumbnail) {
      MusicPlayerHudPositionData(boolean available, String title, String artist, long position, long duration, boolean playing, byte[] thumbnail) {
         title = title != null && !title.isBlank() ? title : "Ожидание...";
         artist = artist != null && !artist.isBlank() ? artist : "Нет данных";
         position = Math.max(0L, position);
         duration = Math.max(0L, duration);
         thumbnail = thumbnail != null && thumbnail.length != 0 ? Arrays.copyOf(thumbnail, thumbnail.length) : null;
         this.available = available;
         this.title = title;
         this.artist = artist;
         this.position = position;
         this.duration = duration;
         this.playing = playing;
         this.thumbnail = thumbnail;
      }

      static MusicPlayerHud.MusicPlayerHudPositionData empty() {
         return new MusicPlayerHud.MusicPlayerHudPositionData(false, "Ожидание...", "Нет данных", 0L, 0L, false, null);
      }

      static MusicPlayerHud.MusicPlayerHudPositionData from(MediaInfo mediaInfo) {
         return new MusicPlayerHud.MusicPlayerHudPositionData(
            true,
            mediaInfo.getTitle(),
            mediaInfo.getArtist(),
            mediaInfo.getPosition(),
            mediaInfo.getDuration(),
            mediaInfo.isPlaying(),
            mediaInfo.getArtworkPng()
         );
      }

      public byte[] thumbnail() {
         return this.thumbnail == null ? null : Arrays.copyOf(this.thumbnail, this.thumbnail.length);
      }
   }
}
