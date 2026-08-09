package ru.metaculture.protection;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.FloatControl.Type;

public class SoundUtils {
   private static final int INT_VALUE = 16;
   private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(2, runnable -> {
      Thread thread = new Thread(runnable, "Wild-Audio");
      thread.setDaemon(true);
      return thread;
   });
   private static final List<Clip> ITEMS = Collections.synchronizedList(new ArrayList<>());
   private static volatile Clip clip = null;

   public static void invoke() {
      try {
         synchronized (ITEMS) {
            for (Clip clip : ITEMS) {
               try {
                  if (clip != null) {
                     if (clip.isRunning()) {
                        clip.stop();
                     }

                     if (clip.isOpen()) {
                        clip.close();
                     }
                  }
               } catch (Throwable exception) {
               }
            }

            ITEMS.clear();
         }

         Clip clip2 = clip;
         if (clip2 != null) {
            try {
               if (clip2.isRunning()) {
                  clip2.stop();
               }

               if (clip2.isOpen()) {
                  clip2.close();
               }
            } catch (Throwable exception2) {
            }
         }

         clip = null;
         EXECUTOR_SERVICE.shutdownNow();
         EXECUTOR_SERVICE.awaitTermination(500L, TimeUnit.MILLISECONDS);
      } catch (Throwable exception3) {
      }
   }

   public static void invoke2(String string, float f, boolean bl) {
      Clip clip3 = clip;
      if (clip3 != null) {
         try {
            if (clip3.isRunning()) {
               clip3.stop();
            }

            if (clip3.isOpen()) {
               clip3.close();
            }
         } catch (Throwable exception4) {
         }
      }

      String text = "/assets/" + "wild" + "/sound/mp3/" + string;
      EXECUTOR_SERVICE.submit(() -> {
         try {
            try (
               InputStream var3x = WildClient.class.getResourceAsStream(text);
               BufferedInputStream var4x = var3x != null ? new BufferedInputStream(var3x) : null;
               AudioInputStream var5x = var4x != null ? AudioSystem.getAudioInputStream(var4x) : null;
            ) {
               if (var5x != null) {
                  Clip clip4 = AudioSystem.getClip();
                  clip4.open(var5x);
                  clip = clip4;

                  try {
                     FloatControl floatControl = (FloatControl)clip4.getControl(Type.MASTER_GAIN);
                     float floatValue = floatControl.getMinimum();
                     float floatValue2 = floatControl.getMaximum();
                     float floatValue3 = (float)(floatValue * (1.0 - f / 100.0) + floatValue2 * (f / 100.0));
                     floatControl.setValue(floatValue3);
                  } catch (Throwable exception5) {
                  }

                  if (bl) {
                     clip4.addLineListener(lineEvent -> {
                        if (lineEvent.getType() == javax.sound.sampled.LineEvent.Type.STOP) {
                           if (clip4 == clip) {
                              try {
                                 clip4.setFramePosition(0);
                                 clip4.start();
                              } catch (Throwable var4xx) {
                              }
                           } else {
                              try {
                                 if (clip4.isOpen()) {
                                    clip4.close();
                                 }
                              } catch (Throwable var3xx) {
                              }
                           }
                        }
                     });
                  } else {
                     clip4.addLineListener(lineEvent -> {
                        if (lineEvent.getType() == javax.sound.sampled.LineEvent.Type.STOP) {
                           try {
                              if (clip4.isOpen()) {
                                 clip4.close();
                              }
                           } catch (Throwable var3xx) {
                           }

                           if (clip4 == clip) {
                              clip = null;
                           }
                        }
                     });
                  }

                  clip4.start();
                  return;
               }

               System.err.println("[SoundUtil] mp3 not found: " + text);
            }
         } catch (Throwable exception6) {
            System.err.println("[SoundUtil] mp3 error: " + exception6);
         }
      });
   }

   public static void play(String string, float f) {
      synchronized (ITEMS) {
         for (int intValue = ITEMS.size() - 1; intValue >= 0; intValue--) {
            Clip clip5 = ITEMS.get(intValue);
            if (clip5 == null) {
               ITEMS.remove(intValue);
            } else if (!clip5.isRunning()) {
               try {
                  if (clip5.isOpen()) {
                     clip5.close();
                  }
               } catch (Throwable exception7) {
               }

               ITEMS.remove(intValue);
            }
         }
      }

      Objects.requireNonNull(WildClient.INSTANCE);
      String text2 = "/assets/" + "wild" + "/sound/wav/" + string + ".wav";
      EXECUTOR_SERVICE.submit(() -> {
         try {
            try (
               InputStream inputStream = SoundUtils.class.getResourceAsStream(text2);
               BufferedInputStream var3x = inputStream != null ? new BufferedInputStream(inputStream) : null;
               AudioInputStream var4x = var3x != null ? AudioSystem.getAudioInputStream(var3x) : null;
            ) {
               if (var4x != null) {
                  Clip clip6 = AudioSystem.getClip();
                  clip6.open(var4x);
                  float floatValue4 = f < 0.0F ? 0.0F : Math.min(1.0F, f);

                  try {
                     FloatControl var7x = (FloatControl)clip6.getControl(Type.MASTER_GAIN);
                     double doubleValue = floatValue4 <= 0.0F ? -80.0 : Math.log(floatValue4) / Math.log(10.0) * 20.0;
                     var7x.setValue((float)doubleValue);
                  } catch (Throwable exception8) {
                  }

                  clip6.addLineListener(lineEvent -> {
                     if (lineEvent.getType() == javax.sound.sampled.LineEvent.Type.STOP) {
                        try {
                           if (clip6.isOpen()) {
                              clip6.close();
                           }
                        } catch (Throwable var5x) {
                        }

                        synchronized (ITEMS) {
                           ITEMS.remove(clip6);
                        }
                     }
                  });
                  synchronized (ITEMS) {
                     while (ITEMS.size() >= 16) {
                        Clip clip7 = ITEMS.remove(0);

                        try {
                           if (clip7.isRunning()) {
                              clip7.stop();
                           }

                           if (clip7.isOpen()) {
                              clip7.close();
                           }
                        } catch (Throwable exception9) {
                        }
                     }

                     ITEMS.add(clip6);
                  }

                  clip6.start();
                  return;
               }
            }
         } catch (Throwable exception10) {
            System.err.println("[SoundUtil] wav error: " + exception10);
         }
      });
   }

   static {
      Runtime.getRuntime().addShutdownHook(new Thread(SoundUtils::invoke, "Wild-SoundUtil-Shutdown"));
   }
}
