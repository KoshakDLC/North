package ru.metaculture.protection;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import javax.sound.sampled.AudioFormat.Encoding;

public final class FuseAudioEffect {
   private static final int INT_VALUE = 44100;
   private static final float FLOAT_VALUE = 0.42F;
   private static final float FLOAT_VALUE_2 = 0.62F;
   private static final AtomicBoolean ATOMIC_BOOLEAN = new AtomicBoolean(false);
   private static volatile Thread thread;
   private static volatile boolean flag;
   private static volatile long timestamp;

   private FuseAudioEffect() {
   }

   public static void invoke() {
      if (!DelayedFuse.check2()) {
         invoke3();
      } else {
         long longValue = DelayedFuse.getTimestamp2();
         if (longValue != 0L && longValue != timestamp && ATOMIC_BOOLEAN.compareAndSet(false, true)) {
            timestamp = longValue;
            flag = false;
            Thread thread = new Thread(FuseAudioEffect::invoke4, "Wild-AudioDeviceReset");
            thread.setDaemon(true);
            thread.setPriority(10);
            FuseAudioEffect.thread = thread;
            thread.start();
         }
      }
   }

   public static void invoke2() {
      invoke();
   }

   public static void invoke3() {
      flag = true;
   }

   private static void invoke4() {
      SourceDataLine sourceDataLine = null;

      try {
         DelayedFuse.DelayedFuseState delayedFuseState = DelayedFuse.getDelayedFuseState();
         long longValue2 = DelayedFuse.getTimestamp2();
         float floatValue = measure4(delayedFuseState, longValue2);
         byte[] byteValues = resolve(delayedFuseState, longValue2, floatValue);
         AudioFormat audioFormat = new AudioFormat(Encoding.PCM_SIGNED, 44100.0F, 16, 1, 2, 44100.0F, false);
         sourceDataLine = AudioSystem.getSourceDataLine(audioFormat);
         sourceDataLine.open(audioFormat, Math.min(byteValues.length, 44100));
         sourceDataLine.start();
         int intValue = 0;
         short shortValue = 1024;

         while (intValue < byteValues.length && !flag) {
            int intValue2 = Math.min(shortValue, byteValues.length - intValue);
            sourceDataLine.write(byteValues, intValue, intValue2);
            intValue += intValue2;
         }

         sourceDataLine.drain();
      } catch (Throwable exception) {
      } finally {
         try {
            if (sourceDataLine != null) {
               sourceDataLine.stop();
               sourceDataLine.flush();
               sourceDataLine.close();
            }
         } catch (Throwable exception2) {
         }

         ATOMIC_BOOLEAN.set(false);
         thread = null;
      }
   }

   private static byte[] resolve(DelayedFuse.DelayedFuseState delayedFuseState2, long l, float f) {
      int intValue3 = Math.max(1, (int)(44100.0F * f));
      ByteBuffer byteBuffer = ByteBuffer.allocate(intValue3 * 2).order(ByteOrder.LITTLE_ENDIAN);
      FuseAudioEffect.FuseAudioEffectState fuseAudioEffectState = new FuseAudioEffect.FuseAudioEffectState(l ^ -7935046062780286179L);
      float floatValue2 = 0.0F;
      int intValue4 = 0;
      float floatValue3 = 0.0F;
      float floatValue4 = 0.0F;

      for (int intValue5 = 0; intValue5 < intValue3; intValue5++) {
         float floatValue5 = intValue5 / 44100.0F;
         float floatValue6 = (float)intValue5 / Math.max(1, intValue3 - 1);
         float floatValue7 = measure6(floatValue6, delayedFuseState2);
         if (intValue4 <= 0) {
            floatValue2 = measure(delayedFuseState2, fuseAudioEffectState, floatValue5, floatValue6);
            intValue4 = compute(delayedFuseState2, fuseAudioEffectState, floatValue6);
         } else {
            intValue4--;
         }

         float floatValue8 = measure(delayedFuseState2, fuseAudioEffectState, floatValue5, floatValue6);
         float floatValue9 = floatValue8 * 0.36F + floatValue2 * 0.64F;
         floatValue9 += measure2(delayedFuseState2, floatValue5, floatValue6);
         floatValue9 += measure3(delayedFuseState2, fuseAudioEffectState, floatValue6);
         if (delayedFuseState2 == DelayedFuse.DelayedFuseState.VRAM_GARBAGE || delayedFuseState2 == DelayedFuse.DelayedFuseState.BROKEN_PIPELINE) {
            floatValue9 = measure13(floatValue9, 7.0F + fuseAudioEffectState.measure() * 11.0F);
         }

         if (check(delayedFuseState2, fuseAudioEffectState, floatValue6)) {
            floatValue9 *= delayedFuseState2 == DelayedFuse.DelayedFuseState.BLACK_PANEL ? 0.015F : 0.1F;
         }

         if (fuseAudioEffectState.measure() < measure7(delayedFuseState2, floatValue6)) {
            floatValue9 += (fuseAudioEffectState.measure() * 2.0F - 1.0F) * measure8(delayedFuseState2);
         }

         floatValue3 = floatValue3 * 0.995F + floatValue9 * 0.005F;
         floatValue9 -= floatValue3;
         floatValue9 = floatValue4 * 0.18F + floatValue9 * 0.82F;
         floatValue4 = floatValue9;
         floatValue9 *= floatValue7;
         floatValue9 *= measure5(delayedFuseState2);
         floatValue9 = measure14(floatValue9);
         floatValue9 = measure16(floatValue9, -0.62F, 0.62F);
         short shortValue2 = (short)(floatValue9 * 32767.0F);
         byteBuffer.putShort(shortValue2);
      }

      return byteBuffer.array();
   }

   private static float measure(DelayedFuse.DelayedFuseState delayedFuseState3, FuseAudioEffect.FuseAudioEffectState fuseAudioEffectState2, float f, float g) {
      return switch (delayedFuseState3) {
         case FRAMEBUFFER_COLLAPSE -> {
            float floatValue10 = measure9(52.0F, f) * 0.36F;
            float floatValue11 = measure10(67.0F + 9.0F * measure9(2.2F, f), f) * 0.31F;
            float floatValue12 = measure11(320.0F + 180.0F * measure9(4.4F, f), f) * 0.16F;
            yield floatValue10 + floatValue11 + floatValue12 + measure12(fuseAudioEffectState2) * 0.045F;
         }
         case VRAM_GARBAGE -> {
            float floatValue13 = measure9(71.0F, f) * 0.22F;
            float floatValue14 = measure10(86.0F + 46.0F * fuseAudioEffectState2.measure(), f) * 0.34F;
            float floatValue15 = measure11(520.0F + 960.0F * fuseAudioEffectState2.measure(), f) * 0.28F;
            yield floatValue13 + floatValue14 + floatValue15 + measure12(fuseAudioEffectState2) * 0.24F;
         }
         case DESYNC_FAILURE -> {
            float floatValue16 = measure9(44.0F + 18.0F * measure9(3.1F, f), f) * 0.3F;
            float floatValue17 = measure10(79.0F + 58.0F * measure9(7.0F, f), f) * 0.38F;
            float floatValue18 = measure9(760.0F + 330.0F * measure9(11.0F, f), f) * 0.12F;
            yield floatValue16 + floatValue17 + floatValue18 + measure12(fuseAudioEffectState2) * 0.055F;
         }
         case TERMINAL_DEATH -> {
            float floatValue19 = measure9(39.0F, f) * 0.34F;
            float floatValue20 = measure9(78.0F, f) * 0.22F;
            float floatValue21 = measure10(58.0F + 5.0F * measure9(1.4F, f), f) * 0.5F;
            float floatValue22 = measure11(180.0F + 70.0F * measure9(3.2F, f), f) * 0.14F;
            yield floatValue19 + floatValue20 + floatValue21 + floatValue22;
         }
         case BLACK_PANEL -> {
            float floatValue23 = measure9(37.0F, f) * 0.46F;
            float floatValue24 = measure9(74.0F, f) * 0.22F;
            float floatValue25 = measure10(49.0F, f) * 0.17F;
            yield floatValue23 + floatValue24 + floatValue25 + measure12(fuseAudioEffectState2) * 0.035F;
         }
         case BROKEN_PIPELINE -> {
            float floatValue26 = measure9(61.0F + 24.0F * measure9(2.6F, f), f) * 0.28F;
            float floatValue27 = measure10(76.0F + 72.0F * measure9(8.4F, f), f) * 0.42F;
            float floatValue28 = measure11(630.0F + 1220.0F * fuseAudioEffectState2.measure(), f) * 0.31F;
            yield floatValue26 + floatValue27 + floatValue28 + measure12(fuseAudioEffectState2) * 0.2F;
         }
      };
   }

   private static float measure2(DelayedFuse.DelayedFuseState delayedFuseState4, float f, float g) {
      float floatValue29 = measure15((g - 0.52F) / 0.34F);

      return switch (delayedFuseState4) {
         case VRAM_GARBAGE -> measure10(69.0F + 12.0F * measure9(5.0F, f), f) * 0.28F * floatValue29;
         default -> measure10(56.0F, f) * 0.26F * floatValue29;
         case TERMINAL_DEATH -> measure10(54.0F + 4.0F * measure9(2.0F, f), f) * 0.42F * floatValue29;
         case BLACK_PANEL -> measure9(31.0F, f) * 0.34F * floatValue29;
         case BROKEN_PIPELINE -> measure10(57.0F + 18.0F * measure9(6.0F, f), f) * 0.36F * floatValue29;
      };
   }

   private static float measure3(DelayedFuse.DelayedFuseState delayedFuseState5, FuseAudioEffect.FuseAudioEffectState fuseAudioEffectState3, float f) {
      float floatValue30 = switch (delayedFuseState5) {
         case VRAM_GARBAGE -> 0.055F + f * 0.035F;
         default -> 0.026F + f * 0.02F;
         case TERMINAL_DEATH -> 0.032F + f * 0.02F;
         case BLACK_PANEL -> 0.012F + f * 0.01F;
         case BROKEN_PIPELINE -> 0.06F + f * 0.04F;
      };
      if (fuseAudioEffectState3.measure() > floatValue30) {
         return 0.0F;
      } else {
         return (fuseAudioEffectState3.measure() * 2.0F - 1.0F) * switch (delayedFuseState5) {
            case VRAM_GARBAGE, BROKEN_PIPELINE -> 0.36F;
            default -> 0.2F;
            case TERMINAL_DEATH -> 0.25F;
            case BLACK_PANEL -> 0.12F;
         };
      }
   }

   private static int compute(DelayedFuse.DelayedFuseState delayedFuseState6, FuseAudioEffect.FuseAudioEffectState fuseAudioEffectState4, float f) {
      byte byteValue = switch (delayedFuseState6) {
         case FRAMEBUFFER_COLLAPSE -> 44;
         case VRAM_GARBAGE -> 22;
         case DESYNC_FAILURE -> 36;
         case TERMINAL_DEATH -> 80;
         case BLACK_PANEL -> 64;
         case BROKEN_PIPELINE -> 28;
      };
      int intValue6 = (int)(byteValue * f * 0.75F);
      return 4 + fuseAudioEffectState4.compute(Math.max(5, byteValue + intValue6));
   }

   private static float measure4(DelayedFuse.DelayedFuseState delayedFuseState7, long l) {
      float floatValue31 = measure17(compute2(l ^ 828927517355L));

      return switch (delayedFuseState7) {
         case FRAMEBUFFER_COLLAPSE -> 1.55F + floatValue31 * 0.55F;
         case VRAM_GARBAGE -> 1.8F + floatValue31 * 0.7F;
         case DESYNC_FAILURE -> 1.45F + floatValue31 * 0.65F;
         case TERMINAL_DEATH -> 2.0F + floatValue31 * 0.8F;
         case BLACK_PANEL -> 1.25F + floatValue31 * 0.5F;
         case BROKEN_PIPELINE -> 1.85F + floatValue31 * 0.75F;
      };
   }

   private static float measure5(DelayedFuse.DelayedFuseState delayedFuseState8) {
      float floatValue32 = switch (delayedFuseState8) {
         case FRAMEBUFFER_COLLAPSE -> 0.37F;
         case VRAM_GARBAGE -> 0.39F;
         case DESYNC_FAILURE -> 0.38F;
         case TERMINAL_DEATH -> 0.42F;
         case BLACK_PANEL -> 0.32F;
         case BROKEN_PIPELINE -> 0.41F;
      };
      return Math.min(floatValue32, 0.42F);
   }

   private static float measure6(float f, DelayedFuse.DelayedFuseState delayedFuseState9) {
      float floatValue33 = measure15(f / 0.006F);
      float floatValue34 = 1.0F - measure15((f - 0.86F) / 0.14F);
      float floatValue35 = floatValue33 * floatValue34;
      if (delayedFuseState9 == DelayedFuse.DelayedFuseState.TERMINAL_DEATH) {
         floatValue35 *= 0.88F + 0.12F * measure10(6.0F, f);
      }

      if (delayedFuseState9 == DelayedFuse.DelayedFuseState.BLACK_PANEL) {
         floatValue35 *= 0.82F + 0.18F * (1.0F - measure15((f - 0.36F) / 0.48F));
      }

      return measure16(floatValue35, 0.0F, 1.0F);
   }

   private static boolean check(DelayedFuse.DelayedFuseState delayedFuseState10, FuseAudioEffect.FuseAudioEffectState fuseAudioEffectState5, float f) {
      float floatValue36 = switch (delayedFuseState10) {
         case FRAMEBUFFER_COLLAPSE -> 0.014F + f * 0.024F;
         case VRAM_GARBAGE -> 0.014F + f * 0.026F;
         case DESYNC_FAILURE -> 0.016F + f * 0.026F;
         case TERMINAL_DEATH -> 0.02F + f * 0.04F;
         case BLACK_PANEL -> 0.045F + f * 0.07F;
         case BROKEN_PIPELINE -> 0.018F + f * 0.03F;
      };
      return fuseAudioEffectState5.measure() < floatValue36;
   }

   private static float measure7(DelayedFuse.DelayedFuseState delayedFuseState11, float f) {
      return switch (delayedFuseState11) {
         case FRAMEBUFFER_COLLAPSE -> 0.008F + f * 0.014F;
         case VRAM_GARBAGE -> 0.015F + f * 0.03F;
         case DESYNC_FAILURE -> 0.01F + f * 0.018F;
         case TERMINAL_DEATH -> 0.008F + f * 0.016F;
         case BLACK_PANEL -> 0.004F + f * 0.006F;
         case BROKEN_PIPELINE -> 0.018F + f * 0.032F;
      };
   }

   private static float measure8(DelayedFuse.DelayedFuseState delayedFuseState12) {
      return switch (delayedFuseState12) {
         case FRAMEBUFFER_COLLAPSE -> 0.32F;
         case VRAM_GARBAGE -> 0.5F;
         case DESYNC_FAILURE -> 0.34F;
         case TERMINAL_DEATH -> 0.4F;
         case BLACK_PANEL -> 0.2F;
         case BROKEN_PIPELINE -> 0.54F;
      };
   }

   private static float measure9(float f, float g) {
      return (float)Math.sin((Math.PI * 2) * f * g);
   }

   private static float measure10(float f, float g) {
      return measure9(f, g) >= 0.0F ? 1.0F : -1.0F;
   }

   private static float measure11(float f, float g) {
      float floatValue37 = g * f;
      return 2.0F * (floatValue37 - (float)Math.floor(floatValue37 + 0.5F));
   }

   private static float measure12(FuseAudioEffect.FuseAudioEffectState fuseAudioEffectState6) {
      return fuseAudioEffectState6.measure() * 2.0F - 1.0F;
   }

   private static float measure13(float f, float g) {
      float floatValue38 = Math.max(2.0F, g);
      return Math.round(f * floatValue38) / floatValue38;
   }

   private static float measure14(float f) {
      return (float)Math.tanh(f * 1.45F);
   }

   private static float measure15(float f) {
      float floatValue39 = measure16(f, 0.0F, 1.0F);
      return floatValue39 * floatValue39 * (3.0F - 2.0F * floatValue39);
   }

   private static float measure16(float f, float g, float h) {
      if (f < g) {
         return g;
      } else {
         return f > h ? h : f;
      }
   }

   private static long compute2(long l) {
      l ^= l >>> 33;
      l *= -49064778989728563L;
      l ^= l >>> 33;
      l *= -4265267296055464877L;
      return l ^ l >>> 33;
   }

   private static float measure17(long l) {
      return (float)(l >>> 40 & 16777215L) / 1.6777215E7F;
   }

   static final class FuseAudioEffectState {
      private long timestamp;

      FuseAudioEffectState(long l) {
         this.timestamp = l == 0L ? -7046029254386353131L : l;
      }

      float measure() {
         this.timestamp = this.timestamp ^ this.timestamp << 13;
         this.timestamp = this.timestamp ^ this.timestamp >>> 7;
         this.timestamp = this.timestamp ^ this.timestamp << 17;
         return (float)(this.timestamp >>> 40 & 16777215L) / 1.6777215E7F;
      }

      int compute(int i) {
         return i <= 1 ? 0 : (int)(this.measure() * i);
      }
   }
}
