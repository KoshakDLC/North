package org.wild.mixin;

import net.minecraft.scoreboard.Team;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.metaculture.protection.ProtectInfo;

@Mixin({Team.class})
public class TeamMixin {
   @Inject(
      method = {"decorateName"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void litka$maskScoreboardTeam(Text text, CallbackInfoReturnable<MutableText> callbackInfoReturnable) {
      MutableText mutableText = (MutableText)callbackInfoReturnable.getReturnValue();
      if (mutableText != null) {
         callbackInfoReturnable.setReturnValue((MutableText)ProtectInfo.resolve6(mutableText));
      }
   }
}
