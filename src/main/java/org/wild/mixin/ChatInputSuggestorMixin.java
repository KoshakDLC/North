package org.wild.mixin;

import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import ru.metaculture.protection.Command;
import ru.metaculture.protection.UnHook;
import ru.metaculture.protection.WildClient;

@Mixin({ChatInputSuggestor.class})
public abstract class ChatInputSuggestorMixin {
   @Shadow
   @Final
   TextFieldWidget textField;
   @Shadow
   private CompletableFuture<Suggestions> pendingSuggestions;

   @Shadow
   public abstract void show(boolean bl);

   @Inject(
      method = {"refresh"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void onRefresh(CallbackInfo callbackInfo) {
      if (!UnHook.active) {
         String text = this.textField.getText();
         String text2 = WildClient.INSTANCE.getCommandPrefix();
         if (text.startsWith(text2)) {
            int intValue = this.textField.getCursor();
            String text3 = text.substring(0, intValue);
            int intValue2 = text3.lastIndexOf(32) + 1;
            if (intValue2 < 0) {
               intValue2 = 0;
            }

            SuggestionsBuilder suggestionsBuilder = new SuggestionsBuilder(text3, intValue2);
            String text4 = text3.substring(text2.length());
            String[] texts = text4.split(" ", -1);
            String text5 = texts[0];
            if (texts.length <= 1) {
               for (Command command : WildClient.INSTANCE.getCommandManager().getCommands()) {
                  if (command.getName().toLowerCase().startsWith(text5.toLowerCase())) {
                     suggestionsBuilder.suggest(text2 + command.getName(), Text.literal(command.getDescription()));
                  }
               }
            } else {
               for (Command command2 : WildClient.INSTANCE.getCommandManager().getCommands()) {
                  if (command2.getName().equalsIgnoreCase(text5)) {
                     for (String text6 : command2.getSuggestions(texts)) {
                        suggestionsBuilder.suggest(text6);
                     }
                  }
               }
            }

            this.pendingSuggestions = suggestionsBuilder.buildFuture();
            this.show(false);
            callbackInfo.cancel();
         }
      }
   }

   @Shadow
   public abstract void setWindowActive(boolean bl);

   @Inject(
      method = {"refresh"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void litka$hideSuggestionsOnUnhook(CallbackInfo callbackInfo) {
      if (UnHook.active) {
         String text7 = this.textField.getText();
         String text8 = WildClient.INSTANCE.getCommandPrefix();
         if (text7 != null && (text7.startsWith(text8) || text7.startsWith("#"))) {
            this.setWindowActive(false);
            callbackInfo.cancel();
         }
      }
   }
}
