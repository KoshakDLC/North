package org.wild.mixin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.client.gui.hud.MessageIndicator;
import net.minecraft.client.gui.hud.ChatHudLine.Visible;
import net.minecraft.network.message.MessageSignatureData;
import net.minecraft.text.MutableText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.ClickEvent.RunCommand;
import net.minecraft.text.HoverEvent.ShowText;
import net.minecraft.util.Formatting;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.wild.mixin.acceser.ChatHudAccessor;
import ru.metaculture.protection.ChatHelper;
import ru.metaculture.protection.ProtectInfo;
import ru.metaculture.protection.UnHook;
import ru.metaculture.protection.WildClient;

@Mixin({ChatHud.class})
public class ChatHudMixin {
   private static boolean litka$updating;
   private static String litka$lastMessageKey;
   private static int litka$lastMessageCount;
   String currentPrefix = WildClient.INSTANCE.getCommandPrefix();
   private static final Pattern GENERAL_COORD_PATTERN = Pattern.compile("(-?\\d+)[\\s,]+(-?\\d+)[\\s,]+(-?\\d+)");

   @ModifyVariable(
      method = {"addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V"},
      at = @At("HEAD"),
      argsOnly = true
   )
   private Text litka$nameProtectAndExpand(Text text) {
      Text text3 = ProtectInfo.resolve5(text);
      MutableText mutableText = text3.copy();
      String text4 = mutableText.getString();
      Matcher matcher = GENERAL_COORD_PATTERN.matcher(text4);
      if (matcher.find()) {
         String text5 = matcher.group(1);
         String text6 = matcher.group(3);
         Style style = mutableText.getStyle()
            .withClickEvent(new RunCommand(this.currentPrefix + "gps " + text5 + " " + text6))
            .withHoverEvent(new ShowText(Text.literal("§a[GPS] Нажми, чтобы поставить метку на " + text5 + ", " + text6)));
         mutableText.setStyle(style);
      }

      if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
         ChatHelper chatHelper = WildClient.INSTANCE.moduleManager.getModule(ChatHelper.class);
         if (chatHelper != null && chatHelper.enabled && ChatHelper.RASSHIRENNYY_PROSMOTR_CHATA.isEnabled() && text4.contains("[Подробнее]")) {
            ArrayList arrayList = new ArrayList();
            this.litka$extractHoverText(text3, arrayList);

            for (Text text7 : (ArrayList<Text>)arrayList) {
               String text8 = text7.getString();
               if (text8.contains("Причина:") || text8.contains("Окончание:") || text8.contains("[БАН]")) {
                  mutableText.append(Text.literal("\n").formatted(Formatting.RESET));
                  mutableText.append(text7);
               }
            }
         }
      }

      return mutableText;
   }

   @Inject(
      method = {"getMessageHistory"},
      at = {@At("RETURN")}
   )
   private void litka$cleanHistoryOnUnhook(CallbackInfoReturnable<Object> callbackInfoReturnable) {
      if (UnHook.active && callbackInfoReturnable.getReturnValue() instanceof Collection items) {
         String text9 = WildClient.INSTANCE.getCommandPrefix();
         items.removeIf(object -> !(object instanceof String text10) ? false : text10.startsWith(text9) || text10.startsWith("#"));
      }
   }

   @Inject(
      method = {"addToMessageHistory"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void litka$blockHistoryWhenUnhooked(String string, CallbackInfo callbackInfo) {
      if (UnHook.active && string != null && (string.startsWith(WildClient.INSTANCE.getCommandPrefix()) || string.startsWith("#"))) {
         callbackInfo.cancel();
      }
   }

   private void litka$extractHoverText(Text text, List<Text> list) {
      Style style2 = text.getStyle();
      if (style2 != null && style2.getHoverEvent() != null && style2.getHoverEvent() instanceof ShowText showText) {
         Text text11 = showText.value();
         if (text11 != null) {
            boolean flag = list.stream().anyMatch(text2 -> text2.getString().equals(text11.getString()));
            if (!flag) {
               list.add(text11);
            }
         }
      }

      for (Text text12 : text.getSiblings()) {
         this.litka$extractHoverText(text12, list);
      }
   }

   @Inject(
      method = {"addMessage(Lnet/minecraft/text/Text;Lnet/minecraft/network/message/MessageSignatureData;Lnet/minecraft/client/gui/hud/MessageIndicator;)V"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void litka$mergeSpam(Text text, MessageSignatureData messageSignatureData, MessageIndicator messageIndicator, CallbackInfo callbackInfo) {
      if (!litka$updating) {
         if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
            ChatHelper chatHelper2 = WildClient.INSTANCE.moduleManager.getModule(ChatHelper.class);
            if (chatHelper2 != null && chatHelper2.enabled && ChatHelper.ANTISPAM_CHAT.isEnabled()) {
               String text13 = text.getString();
               if (text13 != null && !text13.isBlank()) {
                  if (text13.equals(litka$lastMessageKey)) {
                     litka$lastMessageCount++;
                     MutableText mutableText2 = text.copy().append(Text.literal(" [x" + litka$lastMessageCount + "]").formatted(Formatting.GRAY));
                     litka$updating = true;

                     try {
                        this.removeLastEntry();
                        ((ChatHud)(Object)this).addMessage(mutableText2, messageSignatureData, messageIndicator);
                     } finally {
                        litka$updating = false;
                     }

                     callbackInfo.cancel();
                  } else {
                     litka$lastMessageKey = text13;
                     litka$lastMessageCount = 1;
                  }
               }
            }
         }
      }
   }

   @Inject(
      method = {"clear"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private void litka$preserveChat(boolean bl, CallbackInfo callbackInfo) {
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
         ChatHelper chatHelper3 = WildClient.INSTANCE.moduleManager.getModule(ChatHelper.class);
         if (chatHelper3 != null && chatHelper3.enabled && ChatHelper.SOHRANYAT_CHAT.isEnabled()) {
            callbackInfo.cancel();
         } else {
            litka$lastMessageKey = null;
            litka$lastMessageCount = 0;
         }
      }
   }

   private void removeLastEntry() {
      ChatHudAccessor chatHudAccessor = (ChatHudAccessor)(Object)this;
      List items2 = chatHudAccessor.litka$getMessages();
      if (!items2.isEmpty()) {
         items2.remove(0);
      }

      List items3 = chatHudAccessor.litka$getVisibleMessages();
      Visible visible;
      if (!items3.isEmpty()) {
         do {
            visible = (Visible)items3.remove(0);
         } while (!items3.isEmpty() && !visible.endOfEntry());
      }
   }
}
