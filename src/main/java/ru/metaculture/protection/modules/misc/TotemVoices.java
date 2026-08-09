package ru.metaculture.protection;

import java.io.BufferedInputStream;
import java.io.InputStream;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.FloatControl.Type;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.particle.ParticleTypes;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "Totem Voices",
   description = "Заменяет звук тотема на кастомный",
   category = Category.Misc
)
public class TotemVoices extends Module {
   private final NumberSetting gromkost = new NumberSetting("Громкость", 50.0F, 0.0F, 100.0F, 1.0F, false);
   private final ModeSetting zvuk = new ModeSetting("Звук", "Хмм", "Хмм", "Это печально(", "Ебать это чё", "67!");

   public TotemVoices() {
      this.addSettings(new Setting[]{this.zvuk, this.gromkost});
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (CLIENT.player != null && CLIENT.world != null) {
         if (packetEvent.getPacket() instanceof EntityStatusS2CPacket entityStatusS2CPacket && entityStatusS2CPacket.getStatus() == 35) {
            Entity entity = entityStatusS2CPacket.getEntity(CLIENT.world);
            if (entity != null && entity.getId() == CLIENT.player.getId()) {
               packetEvent.invalidate();
               this.invoke();
               CLIENT.execute(() -> {
                  CLIENT.particleManager.addEmitter(entity, ParticleTypes.TOTEM_OF_UNDYING, 30);
                  CLIENT.gameRenderer.showFloatingItem(new ItemStack(Items.TOTEM_OF_UNDYING));
               });
            }
         }
      }
   }

   private void invoke() {
      String text;
      if (this.zvuk.is("Хмм")) {
         text = "hm_pon.wav";
      } else if (this.zvuk.is("Это печально(")) {
         text = "tusky_etopechalno.wav";
      } else if (this.zvuk.is("67!")) {
         text = "pampimpoms.wav";
      } else {
         text = "ebat_eto_cho.wav";
      }

      String text2 = "/assets/" + "wild" + "/tusky/" + text;
      Thread thread = new Thread(() -> {
         try {
            InputStream var2x = TotemVoices.class.getResourceAsStream(text2);
            if (var2x == null) {
               ChatUtil.sendClientMessage("Не найден звук по пути: " + text2);
               return;
            }

            AudioInputStream var3x = AudioSystem.getAudioInputStream(new BufferedInputStream(var2x));
            Clip clip = AudioSystem.getClip();
            clip.open(var3x);
            FloatControl floatControl = (FloatControl)clip.getControl(Type.MASTER_GAIN);
            float floatValue = this.gromkost.getValue();
            if (floatValue <= 0.0F) {
               floatControl.setValue(floatControl.getMinimum());
            } else {
               float floatValue2 = (float)(Math.log10(floatValue / 100.0) * 20.0);
               floatControl.setValue(Math.max(floatControl.getMinimum(), Math.min(floatControl.getMaximum(), floatValue2)));
            }

            clip.start();
         } catch (Exception exception) {
            exception.printStackTrace();
            ChatUtil.sendClientMessage("Ошибка воспроизведения: " + exception.getMessage());
         }
      }, "Wild-TotemVoice");
      thread.setDaemon(true);
      thread.start();
   }
}
