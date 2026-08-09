package org.wild.mixin;

import java.io.File;
import java.nio.file.Path;
import java.util.Locale;
import java.util.stream.Stream;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.pack.PackScreen;
import net.minecraft.client.gui.screen.pack.ResourcePackOrganizer.Pack;
import net.minecraft.client.gui.tooltip.Tooltip;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.ButtonWidget.Builder;
import net.minecraft.text.Text;
import net.minecraft.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.gen.Invoker;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.wild.mixin.acceser.MessageAccessor;
import ru.metaculture.protection.UnHook;

@Mixin({PackScreen.class})
public abstract class PackScreenMixin extends Screen {
   @Unique
   private static final Logger LOGGER = LoggerFactory.getLogger(PackScreenMixin.class);
   @Unique
   private static final Text OPEN_FOLDER = Text.translatable("pack.openFolder");
   @Unique
   private static final Text FOLDER_INFO = Text.translatable("pack.folderInfo");
   @Unique
   private static final Text SEARCH_TITLE = Text.literal("Search resource packs");
   @Unique
   private static final Text SEARCH_PLACEHOLDER = Text.literal("Search...");
   @Unique
   private TextFieldWidget wild$packSearchField;
   @Unique
   private String wild$packSearchQuery = "";
   @Shadow
   private Path file;

   protected PackScreenMixin(Text text) {
      super(text);
   }

   @Invoker("updatePackLists")
   public abstract void wild$updatePackLists();

   @Inject(
      method = {"init"},
      at = {@At("RETURN")}
   )
   private void wild$addPackSearch(CallbackInfo callbackInfo) {
      if (!UnHook.active) {
         this.wild$packSearchField = new TextFieldWidget(this.textRenderer, 0, 0, 160, 20, SEARCH_TITLE);
         this.wild$packSearchField.setMaxLength(128);
         this.wild$packSearchField.setPlaceholder(SEARCH_PLACEHOLDER);
         this.wild$packSearchField.setText(this.wild$packSearchQuery);
         this.wild$packSearchField.setChangedListener(string -> {
            this.wild$packSearchQuery = string == null ? "" : string;
            this.wild$updatePackLists();
         });
         this.wild$positionPackSearchField();
         this.addDrawableChild(this.wild$packSearchField);
      }
   }

   @Inject(
      method = {"refreshWidgetPositions"},
      at = {@At("RETURN")}
   )
   private void wild$refreshPackSearchPosition(CallbackInfo callbackInfo) {
      this.wild$positionPackSearchField();
   }

   @Inject(
      method = {"tick"},
      at = {@At("TAIL")}
   )
   private void wild$tickPackSearchVisibility(CallbackInfo callbackInfo) {
      this.wild$syncPackSearchVisibility();
   }

   @ModifyVariable(
      method = {"updatePackList"},
      at = @At("HEAD"),
      argsOnly = true,
      ordinal = 0,
      require = 0
   )
   private Stream<Pack> wild$filterPackList(Stream<Pack> stream) {
      if (UnHook.active) {
         return stream;
      } else {
         String text2 = this.wild$packSearchQuery == null ? "" : this.wild$packSearchQuery.trim().toLowerCase(Locale.ROOT);
         return text2.isEmpty() ? stream : stream.filter(pack -> this.wild$matchesPackSearch(pack, text2));
      }
   }

   @Unique
   private void wild$positionPackSearchField() {
      if (this.wild$packSearchField != null) {
         int intValue = Math.min(180, Math.max(120, this.width / 5));
         this.wild$packSearchField.setDimensions(intValue, 20);
         this.wild$packSearchField.setX(this.width - intValue - 8);
         this.wild$packSearchField.setY(8);
      }
   }

   @Unique
   private void wild$syncPackSearchVisibility() {
      if (this.wild$packSearchField != null) {
         boolean flag = !UnHook.active;
         this.wild$packSearchField.visible = flag;
         this.wild$packSearchField.active = flag;
         if (!flag) {
            this.wild$packSearchField.setFocused(false);
         }
      }
   }

   @Unique
   private boolean wild$matchesPackSearch(Pack pack, String string) {
      return pack == null
         ? false
         : this.wild$contains(pack.getName(), string) || this.wild$contains(pack.getDisplayName(), string) || this.wild$contains(pack.getDescription(), string);
   }

   @Unique
   private boolean wild$contains(Text text, String string) {
      return text != null && this.wild$contains(text.getString(), string);
   }

   @Unique
   private boolean wild$contains(String string, String string2) {
      return string != null && string.toLowerCase(Locale.ROOT).contains(string2);
   }

   @Redirect(
      method = {"init"},
      at = @At(
         value = "INVOKE",
         target = "Lnet/minecraft/client/gui/widget/ButtonWidget$Builder;build()Lnet/minecraft/client/gui/widget/ButtonWidget;"
      )
   )
   private ButtonWidget redirectOpenFolderButton(Builder builder) {
      Text text3 = ((MessageAccessor)builder).getMessage();
      return text3.equals(OPEN_FOLDER) ? ButtonWidget.builder(OPEN_FOLDER, buttonWidget -> {
         File var2x;
         if (UnHook.active && UnHook.file != null) {
            var2x = UnHook.file;
         } else {
            var2x = this.file.toFile();
         }

         if (!var2x.exists()) {
            var2x.mkdirs();
         }

         LOGGER.info("Opening folder: {}", var2x.getPath());
         Util.getOperatingSystem().open(var2x);
      }).tooltip(Tooltip.of(FOLDER_INFO)).build() : builder.build();
   }
}
