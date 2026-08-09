package org.wild.mixin;

import com.mojang.authlib.GameProfile;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.client.util.SkinTextures.Model;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import ru.metaculture.protection.Cape;
import ru.metaculture.protection.ProtectInfo;
import ru.metaculture.protection.WildClient;

@Mixin({PlayerListEntry.class})
public abstract class PlayerListEntryMixin {
   @Unique
   private Identifier customCape = null;
   @Unique
   private boolean capeLoaded = false;

   @Shadow
   public abstract GameProfile getProfile();

   @Inject(
      method = {"getSkinTextures"},
      at = {@At("RETURN")},
      cancellable = true
   )
   private void wild$replaceSkinsAndCapes(CallbackInfoReturnable<SkinTextures> callbackInfoReturnable) {
      SkinTextures skinTextures = (SkinTextures)callbackInfoReturnable.getReturnValue();
      if (skinTextures != null) {
         boolean flag = false;
         Identifier identifier2 = skinTextures.texture();
         Identifier identifier3 = skinTextures.capeTexture();
         Identifier identifier4 = skinTextures.elytraTexture();
         Model model = skinTextures.model();
         if (ProtectInfo.check2()) {
            SkinTextures skinTextures2 = ProtectInfo.resolve4();
            identifier2 = skinTextures2.texture();
            model = skinTextures2.model();
            flag = true;
         }

         if (!this.capeLoaded) {
            this.capeLoaded = true;
            GameProfile gameProfile = this.getProfile();
            Cape cape = WildClient.INSTANCE.moduleManager.getModule(Cape.class);
            if (cape != null && cape.enabled) {
               Cape.invoke(gameProfile, identifier -> this.customCape = identifier);
            }
         }

         if (this.customCape != null) {
            identifier3 = this.customCape;
            identifier4 = this.customCape;
            flag = true;
         }

         if (flag) {
            callbackInfoReturnable.setReturnValue(new SkinTextures(identifier2, skinTextures.textureUrl(), identifier3, identifier4, model, skinTextures.secure()));
         }
      }
   }
}
