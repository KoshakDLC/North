package ru.metaculture.protection;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.SourceDataLine;
import net.minecraft.entity.LivingEntity;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "HitSounds",
   category = Category.Combat,
   description = "Звуки при попадании и критическом ударе"
)
public class HitSounds extends Module {
   private static final int INT_VALUE = 48000;
   private static final ExecutorService EXECUTOR_SERVICE = Executors.newFixedThreadPool(1, runnable -> {
      Thread thread = new Thread(runnable, "Wild-HitSounds");
      thread.setDaemon(true);
      return thread;
   });
   private final ModeSetting tembr = new ModeSetting("Тембр", "Органик", "Органик", "Стекло", "Глубокий", "Резкий");
   private final NumberSetting gromkost = new NumberSetting("Громкость", 0.62F, 0.0F, 1.0F, 0.01F, true);
   private final NumberSetting vysotaTona = new NumberSetting("Высота тона", 1.0F, 0.72F, 1.34F, 0.01F, false);
   private final NumberSetting yarkost = new NumberSetting("Яркость", 0.58F, 0.0F, 1.0F, 0.01F, true);
   private final NumberSetting nizkieChastoty = new NumberSetting("Низкие частоты", 0.62F, 0.0F, 1.0F, 0.01F, true);
   private final NumberSetting zaderzhka = new NumberSetting("Задержка", 35.0F, 0.0F, 180.0F, 1.0F, false);
   private final BooleanSetting sloyKrita = new BooleanSetting("Слой крита", true);
   private long timestamp;

   public HitSounds() {
      this.addSettings(
         new Setting[]{
            this.tembr, this.gromkost, this.vysotaTona, this.yarkost, this.nizkieChastoty, this.zaderzhka, this.sloyKrita
         }
      );
   }

   @EventHandler
   public void onAttackEntity(AttackEntityEvent attackEntityEvent) {
      if (!ServerModeDetector.check() && attackEntityEvent != null && attackEntityEvent.getEntity() instanceof LivingEntity livingEntity) {
         long longValue = System.currentTimeMillis();
         if (!((float)(longValue - this.timestamp) < this.zaderzhka.getValue())) {
            this.timestamp = longValue;
            boolean flag = this.sloyKrita.isEnabled()
               && CLIENT.player.fallDistance > 0.0
               && !CLIENT.player.isOnGround()
               && !CLIENT.player.isTouchingWater()
               && !CLIENT.player.isClimbing();
            float floatValue = CLIENT.player.getAttackCooldownProgress(0.5F);
            float floatValue2 = (float)Math.min(1.0, (double)(CLIENT.player.distanceTo(livingEntity) / 4.5F));
            float floatValue3 = measure3(0.42F + floatValue * 0.48F + (flag ? 0.26F : 0.0F) - floatValue2 * 0.1F, 0.18F, 1.18F);
            float floatValue4 = measure3(this.gromkost.getValue() * floatValue3, 0.0F, 1.0F);
            float floatValue5 = 0.965F + ThreadLocalRandom.current().nextFloat() * 0.071F;
            float floatValue6 = this.vysotaTona.getValue() * (flag ? 1.075F : 1.0F) * floatValue5;
            String text = this.tembr.value;
            float floatValue7 = this.yarkost.getValue();
            float floatValue8 = this.nizkieChastoty.getValue();
            EXECUTOR_SERVICE.execute(() -> invoke2(resolve(text, floatValue4, floatValue6, floatValue7, floatValue8, flag)));
         }
      }
   }

   public static void invoke() {
      try {
         EXECUTOR_SERVICE.shutdownNow();
         EXECUTOR_SERVICE.awaitTermination(250L, TimeUnit.MILLISECONDS);
      } catch (Throwable exception) {
      }
   }

   private static byte[] resolve(String string, float f, float g, float h, float i, boolean bl) {
      int intValue = Math.round(48000.0F * (bl ? 0.145F : 0.112F));
      byte[] byteValues = new byte[intValue * 2];
      long longValue2 = System.nanoTime();

      float floatValue9 = switch (string) {
         case "Стекло" -> 238.0F;
         case "Глубокий" -> 96.0F;
         case "Резкий" -> 184.0F;
         default -> 132.0F;
      } * g;

      float floatValue10 = switch (string) {
         case "Стекло" -> 1920.0F;
         case "Глубокий" -> 720.0F;
         case "Резкий" -> 2640.0F;
         default -> 1280.0F;
      } * g;

      for (int intValue2 = 0; intValue2 < intValue; intValue2++) {
         float floatValue11 = intValue2 / 48000.0F;
         float floatValue12 = measure(longValue2 + intValue2 * -7046029254386353131L);
         float floatValue13 = 1.0F - (float)Math.exp(-floatValue11 * 920.0F);
         float floatValue14 = (float)Math.exp(-floatValue11 * (bl ? 22.0F : 29.0F));
         float floatValue15 = (float)Math.exp(-floatValue11 * 220.0F);
         float floatValue16 = (float)Math.exp(-floatValue11 * (bl ? 36.0F : 52.0F));
         float floatValue17 = (float)Math.sin((Math.PI * 2) * (floatValue9 * floatValue11 - floatValue11 * floatValue11 * floatValue9 * 2.3F));
         float floatValue18 = (float)Math.sin((Math.PI * 2) * (floatValue9 * 0.47F * floatValue11));
         float floatValue19 = (float)Math.sin((Math.PI * 2) * (floatValue10 * floatValue11 + floatValue11 * floatValue11 * 1080.0F * g));
         float floatValue20 = (float)Math.sin((Math.PI * 2) * (floatValue10 * 1.74F * floatValue11 + Math.sin(floatValue11 * 44.0F) * 0.018F));
         float floatValue21 = bl ? (float)Math.sin((Math.PI * 2) * (floatValue10 * 2.1F * floatValue11 + floatValue11 * floatValue11 * 1600.0F)) * (float)Math.exp(-floatValue11 * 31.0F) : 0.0F;
         float floatValue22 = floatValue17 * floatValue14 * (0.22F + i * 0.38F);
         floatValue22 += floatValue18 * floatValue14 * i * 0.16F;
         floatValue22 += floatValue12 * floatValue15 * (0.3F + h * 0.34F);
         floatValue22 += floatValue19 * floatValue16 * h * 0.18F;
         floatValue22 += floatValue20 * floatValue16 * h * ("Стекло".equals(string) ? 0.22F : 0.07F);
         floatValue22 += floatValue21 * h * 0.22F;
         floatValue22 *= floatValue13 * f;
         floatValue22 = measure2(floatValue22);
         short shortValue = (short)Math.round(measure3(floatValue22, -1.0F, 1.0F) * 32767.0F);
         byteValues[intValue2 * 2] = (byte)(shortValue & 255);
         byteValues[intValue2 * 2 + 1] = (byte)(shortValue >> 8 & 0xFF);
      }

      return byteValues;
   }

   private static void invoke2(byte[] bs) {
      if (bs != null && bs.length != 0) {
         AudioFormat audioFormat = new AudioFormat(48000.0F, 16, 1, true, false);

         try (SourceDataLine sourceDataLine = AudioSystem.getSourceDataLine(audioFormat)) {
            sourceDataLine.open(audioFormat, Math.min(bs.length, 6000));
            sourceDataLine.start();
            sourceDataLine.write(bs, 0, bs.length);
            sourceDataLine.drain();
         } catch (Throwable exception2) {
         }
      }
   }

   private static float measure(long l) {
      l ^= l >>> 33;
      l *= -49064778989728563L;
      l ^= l >>> 33;
      l *= -4265267296055464877L;
      l ^= l >>> 33;
      return (float)(l & 65535L) / 32767.5F - 1.0F;
   }

   private static float measure2(float f) {
      return (float)Math.tanh(f * 1.42F);
   }

   private static float measure3(float f, float g, float h) {
      return Math.max(g, Math.min(h, f));
   }
}
