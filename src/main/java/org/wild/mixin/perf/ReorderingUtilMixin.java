package org.wild.mixin.perf;

import java.util.ArrayList;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.resource.language.ReorderingUtil;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin({ReorderingUtil.class})
public class ReorderingUtilMixin {
   private static final Map<StringVisitable, OrderedText> WILD$CACHE = new ConcurrentHashMap<>(2048);
   private static final int WILD$CACHE_LIMIT = 8192;

   @Inject(
      method = {"reorder"},
      at = {@At("HEAD")},
      cancellable = true
   )
   private static void wild$skipBidi(StringVisitable stringVisitable, boolean bl, CallbackInfoReturnable<OrderedText> callbackInfoReturnable) {
      if (stringVisitable == null) {
         callbackInfoReturnable.setReturnValue(OrderedText.EMPTY);
      } else {
         OrderedText orderedText = WILD$CACHE.get(stringVisitable);
         if (orderedText != null) {
            callbackInfoReturnable.setReturnValue(orderedText);
         } else {
            OrderedText orderedText2 = wild$buildLtr(stringVisitable);
            if (WILD$CACHE.size() >= 8192) {
               WILD$CACHE.clear();
            }

            WILD$CACHE.put(stringVisitable, orderedText2);
            callbackInfoReturnable.setReturnValue(orderedText2);
         }
      }
   }

   private static OrderedText wild$buildLtr(StringVisitable stringVisitable) {
      ArrayList arrayList = new ArrayList(4);
      stringVisitable.visit((style, string) -> {
         if (!string.isEmpty()) {
            arrayList.add(OrderedText.styledForwardsVisitedString(string, style));
         }

         return Optional.empty();
      }, Style.EMPTY);
      if (arrayList.isEmpty()) {
         return OrderedText.EMPTY;
      } else {
         return arrayList.size() == 1 ? (OrderedText)arrayList.get(0) : OrderedText.concat(arrayList);
      }
   }
}
