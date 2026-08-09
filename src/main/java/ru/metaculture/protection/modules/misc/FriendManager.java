package ru.metaculture.protection;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "FriendManager",
   category = Category.Misc,
   description = "Менеджер по управлению в друзьях"
)
public class FriendManager extends Module {
   public static KeybindSetting bindDruzey = new KeybindSetting("Бинд друзей", -1);
   public static BooleanSetting neBitDruzey = new BooleanSetting("Не бить друзей", true);
   public static BooleanSetting ubiratHitboksDruga = new BooleanSetting("Убирать хитбокс друга", true);
   private final ResettableTimer resettableTimer = new ResettableTimer();

   public FriendManager() {
      this.addSettings(new Setting[]{bindDruzey, neBitDruzey, ubiratHitboksDruga});
   }

   @EventHandler
   public void onRawInput(RawInputEvent rawInputEvent) {
      if (rawInputEvent.getKeyCode() == bindDruzey.getKeyCode() && rawInputEvent.getAction() == 1 && this.resettableTimer.check3(200L)) {
         if (AttackAura.livingEntity != null) {
            return;
         }

         HitResult hitResult = CLIENT.crosshairTarget;
         if (hitResult == null || hitResult.getType() != Type.ENTITY) {
            return;
         }

         if (!(((EntityHitResult)hitResult).getEntity() instanceof PlayerEntity playerEntity)) {
            return;
         }

         String text = playerEntity.getName().getString();
         String text2 = WildClient.INSTANCE.getCommandPrefix();
         if (!FriendCommand.check(text)) {
            WildClient.INSTANCE.getCommandManager().execute(text2 + "friend add " + text);
            SoundUtils.play("add", 0.5F);
         } else {
            WildClient.INSTANCE.getCommandManager().execute(text2 + "friend remove " + text);
            SoundUtils.play("remove", 0.5F);
         }

         this.resettableTimer.invoke();
      }
   }
}
