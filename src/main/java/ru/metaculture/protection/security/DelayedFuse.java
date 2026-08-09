package ru.metaculture.protection;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

public final class DelayedFuse {
   public static final long TIMESTAMP = 80000000L;
   public static final long TIMESTAMP_2 = 180000000L;
   public static final long TIMESTAMP_3 = 650000000L;
   public static final long TIMESTAMP_4 = 1250000000L;
   public static final long TIMESTAMP_5 = 2250000000L;
   private static final AtomicBoolean ATOMIC_BOOLEAN = new AtomicBoolean(false);
   private static final AtomicBoolean ATOMIC_BOOLEAN_2 = new AtomicBoolean(false);
   private static volatile long timestamp;
   private static volatile long timestamp2;
   private static volatile ru.metaculture.protection.DelayedFuse.DelayedFuseState delayedFuseState = ru.metaculture.protection.DelayedFuse.DelayedFuseState.FRAMEBUFFER_COLLAPSE;
   private static volatile long timestamp3 = 80000000L;
   private static volatile long timestamp4 = 650000000L;
   private static volatile long timestamp5 = 1250000000L;
   private static volatile long timestamp6 = 2250000000L;
   private static volatile float floatValue;
   private static volatile float floatValue2;
   private static volatile float floatValue3;
   private static volatile float floatValue4;
   private static volatile float floatValue5;
   private static volatile float floatValue6;

   private DelayedFuse() {
   }

   public static void invoke() {
      ATOMIC_BOOLEAN.set(true);
   }

   public static boolean check() {
      return ATOMIC_BOOLEAN.get();
   }

   public static void invoke2() {
      if (ATOMIC_BOOLEAN.get()) {
         if (!ATOMIC_BOOLEAN_2.get()) {
            if (ThreadLocalRandom.current().nextInt(100) < 70) {
               invoke3();
            }
         }
      }
   }

   public static void invoke3() {
      if (ATOMIC_BOOLEAN_2.compareAndSet(false, true)) {
         ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
         timestamp = System.nanoTime();
         timestamp2 = threadLocalRandom.nextLong();
         ru.metaculture.protection.DelayedFuse.DelayedFuseState[] w6s = ru.metaculture.protection.DelayedFuse.DelayedFuseState.values();
         delayedFuseState = w6s[threadLocalRandom.nextInt(w6s.length)];
         long longValue = threadLocalRandom.nextLong(35L, 125L);
         long longValue2 = longValue + threadLocalRandom.nextLong(220L, 760L);
         long longValue3 = longValue2 + threadLocalRandom.nextLong(260L, 980L);
         long longValue4 = longValue3 + threadLocalRandom.nextLong(220L, 920L);
         timestamp3 = longValue * 1000000L;
         timestamp4 = longValue2 * 1000000L;
         timestamp5 = longValue3 * 1000000L;
         timestamp6 = longValue4 * 1000000L;
         floatValue = threadLocalRandom.nextFloat(0.1F, 0.88F);
         floatValue2 = threadLocalRandom.nextFloat(0.04F, 0.62F);
         floatValue3 = threadLocalRandom.nextFloat(0.28F, 1.0F);
         floatValue4 = threadLocalRandom.nextFloat(0.1F, 0.68F);
         floatValue5 = threadLocalRandom.nextFloat(0.28F, 1.0F);
         floatValue6 = threadLocalRandom.nextFloat(0.2F, 1.0F);
         switch (delayedFuseState) {
            case FRAMEBUFFER_COLLAPSE:
               floatValue = Math.max(floatValue, 0.6F);
               floatValue4 = Math.max(floatValue4, 0.46F);
               floatValue5 = Math.max(floatValue5, 0.66F);
               break;
            case VRAM_GARBAGE:
               floatValue6 = Math.max(floatValue6, 0.88F);
               floatValue3 = Math.max(floatValue3, 0.74F);
               floatValue2 = Math.max(floatValue2, 0.18F);
               break;
            case DESYNC_FAILURE:
               floatValue5 = Math.max(floatValue5, 0.88F);
               floatValue4 = Math.max(floatValue4, 0.42F);
               floatValue3 = Math.max(floatValue3, 0.6F);
               break;
            case TERMINAL_DEATH:
               floatValue = Math.max(floatValue, 0.76F);
               floatValue2 = Math.max(floatValue2, 0.44F);
               floatValue4 = Math.max(floatValue4, 0.54F);
               timestamp6 = Math.min(timestamp6, 1420000000L + threadLocalRandom.nextLong(0L, 580000000L));
               break;
            case BLACK_PANEL:
               floatValue = Math.max(floatValue, 0.92F);
               floatValue2 = Math.min(floatValue2, 0.16F);
               floatValue6 = Math.min(floatValue6, 0.34F);
               floatValue3 = Math.min(floatValue3, 0.48F);
               break;
            case BROKEN_PIPELINE:
               floatValue5 = Math.max(floatValue5, 0.96F);
               floatValue3 = Math.max(floatValue3, 0.86F);
               floatValue6 = Math.max(floatValue6, 0.72F);
               floatValue4 = Math.max(floatValue4, 0.52F);
         }

         try {
            ru.metaculture.protection.FuseAudioEffect.invoke2();
         } catch (Throwable exception) {
         }
      }
   }

   public static boolean check2() {
      return ATOMIC_BOOLEAN_2.get();
   }

   public static ru.metaculture.protection.DelayedFuse.DelayedFuseState getDelayedFuseState() {
      return delayedFuseState;
   }

   public static long compute() {
      return ATOMIC_BOOLEAN_2.get() ? Math.max(0L, System.nanoTime() - timestamp) : 0L;
   }

   public static long compute2() {
      return compute() / 1000000L;
   }

   public static long compute3() {
      return compute2();
   }

   public static long getTimestamp2() {
      return timestamp2;
   }

   public static ru.metaculture.protection.DelayedFuse.DelayedFuseState2 resolve() {
      if (!ATOMIC_BOOLEAN_2.get()) {
         return ru.metaculture.protection.DelayedFuse.DelayedFuseState2.IDLE;
      } else {
         long longValue5 = compute();
         if (longValue5 < timestamp3) {
            return ru.metaculture.protection.DelayedFuse.DelayedFuseState2.STRIKE;
         } else {
            return longValue5 < timestamp5 ? ru.metaculture.protection.DelayedFuse.DelayedFuseState2.COLLAPSE : ru.metaculture.protection.DelayedFuse.DelayedFuseState2.DEATH;
         }
      }
   }

   public static int compute4() {
      if (!ATOMIC_BOOLEAN_2.get()) {
         return 0;
      } else {
         long longValue6 = compute();
         if (longValue6 >= timestamp6) {
            return 5;
         } else if (longValue6 >= timestamp5) {
            return 4;
         } else if (longValue6 >= timestamp4) {
            return 3;
         } else {
            return longValue6 >= timestamp3 ? 2 : 1;
         }
      }
   }

   public static float measure() {
      if (!ATOMIC_BOOLEAN_2.get()) {
         return 0.0F;
      } else {
         long longValue7 = compute();
         if (longValue7 < timestamp3) {
            return 0.72F + 0.28F * measure15((float)longValue7 / (float)Math.max(1L, timestamp3));
         } else if (longValue7 < timestamp4) {
            return 0.82F + 0.18F * measure15((float)(longValue7 - timestamp3) / (float)Math.max(1L, timestamp4 - timestamp3));
         } else {
            return longValue7 < timestamp5
               ? 0.92F + 0.08F * measure15((float)(longValue7 - timestamp4) / (float)Math.max(1L, timestamp5 - timestamp4))
               : 1.0F;
         }
      }
   }

   public static float measure2() {
      return !ATOMIC_BOOLEAN_2.get() ? 0.0F : measure16((float)compute() / (float)Math.max(1L, timestamp6));
   }

   public static float measure3(long l, long m) {
      if (!ATOMIC_BOOLEAN_2.get()) {
         return 0.0F;
      } else {
         return m <= l ? 1.0F : measure16((float)(compute() - l) / (float)(m - l));
      }
   }

   public static boolean check3() {
      return ATOMIC_BOOLEAN_2.get() && compute() >= timestamp6;
   }

   public static boolean check4() {
      if (!ATOMIC_BOOLEAN_2.get()) {
         return false;
      } else {
         ru.metaculture.protection.DelayedFuse.DelayedFuseState2 delayedFuseState2 = resolve();
         if (delayedFuseState2 == ru.metaculture.protection.DelayedFuse.DelayedFuseState2.IDLE) {
            return false;
         } else {
            float floatValue = switch (delayedFuseState) {
               case FRAMEBUFFER_COLLAPSE -> 0.18F;
               case VRAM_GARBAGE -> 0.08F;
               case DESYNC_FAILURE -> 0.24F;
               case TERMINAL_DEATH -> 0.36F;
               case BLACK_PANEL -> 0.14F;
               case BROKEN_PIPELINE -> 0.3F;
            };
            if (delayedFuseState2 == ru.metaculture.protection.DelayedFuse.DelayedFuseState2.STRIKE) {
               return check11(7001, 88L, 0.46F + floatValue4 * 0.32F, 26L);
            } else {
               return delayedFuseState2 == ru.metaculture.protection.DelayedFuse.DelayedFuseState2.DEATH
                  ? check12(floatValue + 0.4F + floatValue4 * 0.34F, compute8(7002, 24L))
                  : check12(floatValue + floatValue4 * 0.34F * measure(), compute8(7003, 30L));
            }
         }
      }
   }

   public static boolean check5() {
      if (!ATOMIC_BOOLEAN_2.get()) {
         return false;
      } else {
         ru.metaculture.protection.DelayedFuse.DelayedFuseState2 delayedFuseState22 = resolve();
         if (delayedFuseState22 == ru.metaculture.protection.DelayedFuse.DelayedFuseState2.IDLE) {
            return false;
         } else if (delayedFuseState == ru.metaculture.protection.DelayedFuse.DelayedFuseState.BLACK_PANEL && delayedFuseState22 == ru.metaculture.protection.DelayedFuse.DelayedFuseState2.DEATH) {
            return true;
         } else if (delayedFuseState == ru.metaculture.protection.DelayedFuse.DelayedFuseState.TERMINAL_DEATH && compute() > timestamp5 + 120000000L) {
            return true;
         } else if (delayedFuseState22 == ru.metaculture.protection.DelayedFuse.DelayedFuseState2.STRIKE) {
            return check11(7101, 140L, floatValue * 0.48F, 42L);
         } else {
            return delayedFuseState22 == ru.metaculture.protection.DelayedFuse.DelayedFuseState2.DEATH
               ? check12(0.36F + floatValue * 0.58F, compute8(7102, 38L))
               : check12(0.08F + floatValue * 0.42F * measure(), compute8(7103, 48L));
         }
      }
   }

   public static boolean check6() {
      if (!ATOMIC_BOOLEAN_2.get()) {
         return false;
      } else {
         ru.metaculture.protection.DelayedFuse.DelayedFuseState2 delayedFuseState23 = resolve();
         if (delayedFuseState23 == ru.metaculture.protection.DelayedFuse.DelayedFuseState2.DEATH && delayedFuseState == ru.metaculture.protection.DelayedFuse.DelayedFuseState.BLACK_PANEL) {
            return true;
         } else {
            return delayedFuseState23 == ru.metaculture.protection.DelayedFuse.DelayedFuseState2.DEATH && delayedFuseState == ru.metaculture.protection.DelayedFuse.DelayedFuseState.TERMINAL_DEATH
               ? compute() > timestamp5 + 90000000L
               : check5() && check12(0.24F + floatValue * 0.48F, compute8(7201, 62L));
         }
      }
   }

   public static boolean check7() {
      if (!ATOMIC_BOOLEAN_2.get()) {
         return false;
      } else {
         ru.metaculture.protection.DelayedFuse.DelayedFuseState2 delayedFuseState24 = resolve();
         if (delayedFuseState == ru.metaculture.protection.DelayedFuse.DelayedFuseState.BLACK_PANEL) {
            return check11(7301, 820L, 0.1F, 24L);
         } else if (delayedFuseState24 == ru.metaculture.protection.DelayedFuse.DelayedFuseState2.STRIKE) {
            return check11(7302, 105L, 0.78F + floatValue2 * 0.18F, 20L);
         } else {
            return delayedFuseState24 == ru.metaculture.protection.DelayedFuse.DelayedFuseState2.DEATH
               ? check11(7303, 240L, 0.24F + floatValue2 * 0.44F, 36L)
               : check11(7304, 300L, 0.1F + floatValue2 * 0.36F, 28L);
         }
      }
   }

   public static boolean check8() {
      if (!ATOMIC_BOOLEAN_2.get()) {
         return false;
      } else {
         ru.metaculture.protection.DelayedFuse.DelayedFuseState2 delayedFuseState25 = resolve();
         if (delayedFuseState25 == ru.metaculture.protection.DelayedFuse.DelayedFuseState2.IDLE) {
            return false;
         } else if (delayedFuseState25 == ru.metaculture.protection.DelayedFuse.DelayedFuseState2.STRIKE) {
            return check11(7401, 140L, 0.32F, 42L);
         } else {
            float floatValue2 = switch (delayedFuseState) {
               case FRAMEBUFFER_COLLAPSE -> 0.44F;
               case VRAM_GARBAGE -> 0.16F;
               case DESYNC_FAILURE -> 0.28F;
               case TERMINAL_DEATH -> 0.5F;
               case BLACK_PANEL -> 0.38F;
               case BROKEN_PIPELINE -> 0.36F;
            };
            return check11(7402, 460L, floatValue2, 86L + (long)(130.0F * measure()));
         }
      }
   }

   public static boolean check9() {
      if (!ATOMIC_BOOLEAN_2.get()) {
         return false;
      } else {
         ru.metaculture.protection.DelayedFuse.DelayedFuseState2 delayedFuseState26 = resolve();
         if (delayedFuseState26 == ru.metaculture.protection.DelayedFuse.DelayedFuseState2.DEATH) {
            return true;
         } else {
            float floatValue3 = switch (delayedFuseState) {
               case FRAMEBUFFER_COLLAPSE -> 0.5F;
               case VRAM_GARBAGE -> 0.12F;
               case DESYNC_FAILURE -> 0.58F;
               case TERMINAL_DEATH -> 0.4F;
               case BLACK_PANEL -> 0.2F;
               case BROKEN_PIPELINE -> 0.72F;
            };
            return check12(floatValue3 * floatValue5 * measure(), compute8(7501, 34L));
         }
      }
   }

   public static float measure4() {
      if (!ATOMIC_BOOLEAN_2.get()) {
         return 0.0F;
      } else {
         float floatValue4 = switch (delayedFuseState) {
            case FRAMEBUFFER_COLLAPSE -> 0.12F;
            case VRAM_GARBAGE -> 0.05F;
            case DESYNC_FAILURE -> 0.24F;
            case TERMINAL_DEATH -> 0.18F;
            case BLACK_PANEL -> 0.04F;
            case BROKEN_PIPELINE -> 0.3F;
         };
         return measure13(7601, 20L) * floatValue4 * floatValue5 * measure();
      }
   }

   public static float measure5() {
      if (!ATOMIC_BOOLEAN_2.get()) {
         return 0.0F;
      } else {
         float floatValue5 = switch (delayedFuseState) {
            case FRAMEBUFFER_COLLAPSE -> 0.16F;
            case VRAM_GARBAGE -> 0.07F;
            case DESYNC_FAILURE -> 0.3F;
            case TERMINAL_DEATH -> 0.2F;
            case BLACK_PANEL -> 0.06F;
            case BROKEN_PIPELINE -> 0.34F;
         };
         return measure13(7602, 24L) * floatValue5 * floatValue5 * measure();
      }
   }

   public static float measure6() {
      if (!ATOMIC_BOOLEAN_2.get()) {
         return 1.0F;
      } else {
         return check9()
            ? Math.max(0.018F, 1.0F - Math.abs(measure13(7603, 32L)) * 0.95F * floatValue5)
            : 1.0F + measure13(7604, 28L) * 0.26F * floatValue5 * measure();
      }
   }

   public static float measure7() {
      if (!ATOMIC_BOOLEAN_2.get()) {
         return 1.0F;
      } else {
         return check9()
            ? Math.max(0.012F, 1.0F - Math.abs(measure13(7605, 30L)) * 0.98F * floatValue5)
            : 1.0F + measure13(7606, 26L) * 0.34F * floatValue5 * measure();
      }
   }

   public static float measure8() {
      if (!ATOMIC_BOOLEAN_2.get()) {
         return 1.0F;
      } else if (delayedFuseState == ru.metaculture.protection.DelayedFuse.DelayedFuseState.BLACK_PANEL) {
         return 1.0F;
      } else {
         ru.metaculture.protection.DelayedFuse.DelayedFuseState2 delayedFuseState27 = resolve();

         float floatValue6 = switch (delayedFuseState) {
            case FRAMEBUFFER_COLLAPSE -> 0.16F;
            case VRAM_GARBAGE -> 0.08F;
            case DESYNC_FAILURE -> 0.3F;
            case TERMINAL_DEATH -> 0.18F;
            case BLACK_PANEL -> 0.0F;
            case BROKEN_PIPELINE -> 0.26F;
         };
         return delayedFuseState27 == ru.metaculture.protection.DelayedFuse.DelayedFuseState2.STRIKE ? 1.0F + measure13(7701, 16L) * floatValue6 * 1.45F : 1.0F + measure13(7702, 32L) * floatValue6 * measure();
      }
   }

   public static int compute5() {
      if (!ATOMIC_BOOLEAN_2.get()) {
         return 0;
      } else {
         byte byteValue = switch (delayedFuseState) {
            case FRAMEBUFFER_COLLAPSE -> 24;
            case VRAM_GARBAGE -> 36;
            case DESYNC_FAILURE -> 20;
            case TERMINAL_DEATH -> 14;
            case BLACK_PANEL -> 8;
            case BROKEN_PIPELINE -> 46;
         };
         return Math.max(1, (int)(byteValue * (0.55F + floatValue3 * 0.78F) * measure()));
      }
   }

   public static int compute6() {
      if (!ATOMIC_BOOLEAN_2.get()) {
         return 0;
      } else {
         byte byteValue2 = switch (delayedFuseState) {
            case FRAMEBUFFER_COLLAPSE -> 18;
            case VRAM_GARBAGE -> 82;
            case DESYNC_FAILURE -> 24;
            case TERMINAL_DEATH -> 16;
            case BLACK_PANEL -> 6;
            case BROKEN_PIPELINE -> 60;
         };
         return Math.max(1, (int)(byteValue2 * (0.36F + floatValue6) * measure()));
      }
   }

   public static int compute7() {
      if (!ATOMIC_BOOLEAN_2.get()) {
         return 0;
      } else {
         short shortValue = switch (delayedFuseState) {
            case FRAMEBUFFER_COLLAPSE -> 100;
            case VRAM_GARBAGE -> 290;
            case DESYNC_FAILURE -> 130;
            case TERMINAL_DEATH -> 86;
            case BLACK_PANEL -> 38;
            case BROKEN_PIPELINE -> 210;
         };
         return Math.max(1, (int)(shortValue * (0.26F + floatValue6) * measure()));
      }
   }

   public static float measure9() {
      if (!ATOMIC_BOOLEAN_2.get()) {
         return 0.0F;
      } else {
         ru.metaculture.protection.DelayedFuse.DelayedFuseState2 delayedFuseState28 = resolve();

         float floatValue7 = switch (delayedFuseState28) {
            case IDLE -> 0.0F;
            case STRIKE -> 0.18F;
            case COLLAPSE -> 0.36F;
            case DEATH -> 0.8F;
         };
         if (delayedFuseState == ru.metaculture.protection.DelayedFuse.DelayedFuseState.BLACK_PANEL) {
            floatValue7 += 0.22F;
         }

         if (delayedFuseState == ru.metaculture.protection.DelayedFuse.DelayedFuseState.TERMINAL_DEATH && delayedFuseState28 == ru.metaculture.protection.DelayedFuse.DelayedFuseState2.DEATH) {
            floatValue7 += 0.18F;
         }

         return measure16(floatValue7 + floatValue * 0.3F * measure());
      }
   }

   public static float measure10() {
      if (!ATOMIC_BOOLEAN_2.get()) {
         return 0.0F;
      } else if (!check7()) {
         return 0.0F;
      } else {
         float floatValue8 = switch (resolve()) {
            case IDLE -> 0.0F;
            case STRIKE -> 0.84F;
            case COLLAPSE -> 0.56F;
            case DEATH -> 0.7F;
         };
         return measure16(floatValue8 + floatValue2 * 0.2F);
      }
   }

   public static float getFloatValue3() {
      return floatValue3;
   }

   public static float getFloatValue6() {
      return floatValue6;
   }

   public static float getFloatValue() {
      return floatValue;
   }

   public static float getFloatValue2() {
      return floatValue2;
   }

   public static float getFloatValue5() {
      return floatValue5;
   }

   public static float getFloatValue4() {
      return floatValue4;
   }

   public static float measure11(int i) {
      long longValue8 = System.nanoTime() / 16000000L;
      return measure18(compute9(timestamp2 ^ longValue8 ^ i * -7046029254386353131L));
   }

   public static float measure12(int i) {
      long longValue9 = System.nanoTime() / 7000000L;
      return measure18(compute9(timestamp2 ^ longValue9 ^ i * -4417276706812531889L));
   }

   public static float measure13(int i, long l) {
      long longValue10 = Math.max(1L, l);
      long longValue11 = compute2() / longValue10;
      return measure18(compute9(timestamp2 ^ longValue11 * -3335678366873096957L ^ i * -7046029254386353131L));
   }

   public static float measure14(int i) {
      return measure18(compute9(timestamp2 ^ i * -4417276706812531889L));
   }

   public static boolean check10(int i, long l, float f) {
      if (!ATOMIC_BOOLEAN_2.get()) {
         return false;
      } else {
         long longValue12 = Math.max(1L, l);
         float floatValue9 = measure16(f);
         long longValue13 = compute10(compute9(timestamp2 ^ i * -7723592293110705685L)) % longValue12;
         long longValue14 = Math.floorMod(compute2() + longValue13, longValue12);
         return longValue14 < (long)((float)longValue12 * floatValue9);
      }
   }

   public static boolean check11(int i, long l, float f, long m) {
      if (!ATOMIC_BOOLEAN_2.get()) {
         return false;
      } else {
         long longValue15 = Math.max(1L, l);
         long longValue16 = Math.max(1L, Math.min(m, longValue15));
         long longValue17 = compute2();
         long longValue18 = longValue17 / longValue15;
         long longValue19 = longValue17 % longValue15;
         float floatValue10 = measure17(compute9(timestamp2 ^ longValue18 * -4658895280553007687L ^ i * -7046029254386353131L));
         return floatValue10 < measure16(f) && longValue19 < longValue16;
      }
   }

   public static boolean check12(float f, long l) {
      if (!ATOMIC_BOOLEAN_2.get()) {
         return false;
      } else {
         long longValue20 = timestamp2 ^ l * -2960836687051489901L;
         return measure17(compute9(longValue20)) < measure16(f);
      }
   }

   public static long compute8(int i, long l) {
      long longValue21 = Math.max(1L, l);
      return compute2() / longValue21 * -7046029254386353131L ^ i;
   }

   public static float measure15(float f) {
      float floatValue11 = measure16(f);
      return floatValue11 * floatValue11 * (3.0F - 2.0F * floatValue11);
   }

   public static float measure16(float f) {
      if (f <= 0.0F) {
         return 0.0F;
      } else {
         return f >= 1.0F ? 1.0F : f;
      }
   }

   private static long compute9(long l) {
      l ^= l >>> 33;
      l *= -49064778989728563L;
      l ^= l >>> 33;
      l *= -4265267296055464877L;
      return l ^ l >>> 33;
   }

   private static long compute10(long l) {
      return l & Long.MAX_VALUE;
   }

   private static float measure17(long l) {
      return (float)(l >>> 40 & 16777215L) / 1.6777215E7F;
   }

   private static float measure18(long l) {
      return measure17(l) * 2.0F - 1.0F;
   }

   public static enum DelayedFuseState {
      FRAMEBUFFER_COLLAPSE,
      VRAM_GARBAGE,
      DESYNC_FAILURE,
      TERMINAL_DEATH,
      BLACK_PANEL,
      BROKEN_PIPELINE;
   }

   public static enum DelayedFuseState2 {
      IDLE,
      STRIKE,
      COLLAPSE,
      DEATH;
   }
}
