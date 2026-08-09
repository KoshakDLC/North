package ru.metaculture.protection;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.JsonOps;
import java.util.Arrays;
import java.util.Locale;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.Window;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.LoreComponent;
import net.minecraft.component.type.AttributeModifiersComponent.Entry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.Item.TooltipContext;
import net.minecraft.item.tooltip.TooltipType.Default;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.registry.BuiltinRegistries;
import net.minecraft.registry.Registries;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.glfw.GLFW;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AutoSwap",
   description = "Автоматически свапает предметы через бинд",
   category = Category.Combat,
   riskLevels = {ModuleRiskLevel.GRIM}
)
public class AutoSwap extends Module {
   private static final String OBYCHNYY = "Обычный";
   private static final String TRIO_SVAP = "Трио свап";
   private static final String BEZ_FILTRA = "Без фильтра";
   private static final String FT_RW = "FT/RW";
   private static final int INT_VALUE = 3;
   private static boolean flag;
   public static ModeSetting vyborRabotySvapov = new ModeSetting("Выбор работы свапов:", "Трио свап", "Обычный", "Трио свап");
   public static ModeSetting filtrSvapov = new ModeSetting("Фильтр свапов", "Без фильтра", "Без фильтра", "FT/RW");
   public static ModeSetting pervyyPredmet = new ModeSetting("Первый предмет", "Шар", "Золотое яблоко", "Щит", "Шар", "Тотем")
      .setVisibilityCondition(() -> !vyborRabotySvapov.is("Обычный"));
   public static ModeSetting vtoroyPredmet = new ModeSetting("Второй предмет", "Тотем 2", "Золотое яблоко 2", "Щит 2", "Шар 2", "Тотем 2")
      .setVisibilityCondition(() -> !vyborRabotySvapov.is("Обычный"));
   public static KeybindSetting knopka = new KeybindSetting("Кнопка", -1);
   public static BooleanSetting tolkoZacharovanyhTotemov = new BooleanSetting("Только зачарованых тотемов", false).visibleWhen(() -> !vyborRabotySvapov.is("Обычный"));
   private boolean flag2;
   private final ResettableTimer resettableTimer = new ResettableTimer();
   private final ItemStack[] itemStacks = new ItemStack[]{ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};
   private final String[] text = new String[]{"", "", ""};
   private int intValue = 0;
   private int intValue2 = 0;
   private int intValue3 = -1;
   private String text2 = "";
   private boolean flag3;
   private boolean flag4;
   private int intValue4 = -1;
   private int intValue5 = -1;
   private float floatValue;
   private float floatValue2;
   private float floatValue3;
   private float floatValue4;
   private long timestamp;
   private long timestamp2;
   private long timestamp3;
   private float[] floats = new float[3];
   public static boolean flag5 = false;

   public AutoSwap() {
      this.addSettings(new Setting[]{vyborRabotySvapov, filtrSvapov, pervyyPredmet, vtoroyPredmet, knopka, tolkoZacharovanyhTotemov});
   }

   public static boolean isFlag() {
      return flag;
   }

   @EventHandler
   public void onClientTick(ClientTickEvent clientTickEvent) {
      if (CLIENT.player == null || CLIENT.world == null) {
         this.invoke21();
      } else if (this.flag3) {
         if (!vyborRabotySvapov.is("Трио свап") || knopka.getKeyCode() == -1) {
            this.invoke6(false);
         } else if (!this.check13(knopka.getKeyCode())) {
            this.invoke6(true);
         }
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player == null || CLIENT.world == null) {
         this.invoke21();
      } else if (this.intValue > 0) {
         this.invoke4();
         if (this.intValue2 > 0) {
            this.intValue2--;
         } else {
            this.invoke();
         }
      }
   }

   @EventHandler
   public void onHudRender(HudRenderEvent hudRenderEvent) {
      if (this.flag3 && vyborRabotySvapov.is("Трио свап") && CLIENT.player != null && CLIENT.world != null) {
         RenderManager renderManager = hudRenderEvent.getRenderManager();
         DrawContext context = hudRenderEvent.getDrawContext();
         if (renderManager != null && context != null) {
            renderManager.invoke48(7.0F);
            this.invoke15();
            int intValue = hudRenderEvent.getIntValue();
            int intValue2 = hudRenderEvent.getIntValue2();
            if (this.flag4) {
               this.invoke10();
               this.invoke12(renderManager, context, intValue, intValue2);
            } else {
               this.intValue4 = this.compute5(this.floatValue, this.floatValue2, intValue, intValue2);
               this.invoke10();
               this.invoke11(renderManager, context, intValue, intValue2);
            }
         }
      }
   }

   @EventHandler
   public void onRawInput(RawInputEvent rawInputEvent) {
      if (rawInputEvent.getKeyCode() == knopka.getKeyCode() && knopka.getKeyCode() != -1) {
         if (!vyborRabotySvapov.is("Трио свап")) {
            if (CLIENT.currentScreen == null && rawInputEvent.getAction() == 1 && this.resettableTimer.check3(300L) && this.intValue == 0) {
               this.resettableTimer.invoke();
               this.invoke2();
            }
         } else {
            if (rawInputEvent.getAction() == 1 && this.intValue == 0 && !this.flag3 && this.resettableTimer.check3(120L)) {
               this.invoke5();
               this.resettableTimer.invoke();
               rawInputEvent.invalidate();
            } else if (rawInputEvent.getAction() == 0 && this.flag3) {
               this.invoke6(true);
               rawInputEvent.invalidate();
            }
         }
      }
   }

   @EventHandler
   public void onMouseButtonPosition(MouseButtonPositionEvent mouseButtonPositionEvent) {
      int intValue3 = -100 - mouseButtonPositionEvent.getButton();
      if (vyborRabotySvapov.is("Трио свап") && knopka.getKeyCode() == intValue3 && this.intValue == 0) {
         if (mouseButtonPositionEvent.isPress() && !this.flag3 && this.resettableTimer.check3(120L)) {
            this.invoke18((float)mouseButtonPositionEvent.getCursorX(), (float)mouseButtonPositionEvent.getCursorY());
            this.invoke5();
            this.resettableTimer.invoke();
            mouseButtonPositionEvent.invalidate();
            return;
         }

         if (mouseButtonPositionEvent.isRelease() && this.flag3) {
            this.invoke18((float)mouseButtonPositionEvent.getCursorX(), (float)mouseButtonPositionEvent.getCursorY());
            this.invoke6(true);
            mouseButtonPositionEvent.invalidate();
            return;
         }
      }

      if (this.flag3) {
         this.invoke18((float)mouseButtonPositionEvent.getCursorX(), (float)mouseButtonPositionEvent.getCursorY());
         if (mouseButtonPositionEvent.isPress()) {
            this.invoke7(mouseButtonPositionEvent.getButton());
         }

         mouseButtonPositionEvent.invalidate();
      }
   }

   @EventHandler
   public void onMouseUpdate(MouseUpdateEvent mouseUpdateEvent) {
      if (this.flag3) {
         mouseUpdateEvent.invalidate();
      }
   }

   @EventHandler
   public void onMouseButton(MouseButtonEvent mouseButtonEvent) {
      if (this.flag3) {
         mouseButtonEvent.invalidate();
      }
   }

   private void invoke() {
      switch (this.intValue) {
         case 1:
            this.invoke4();
            this.intValue = 2;
            this.intValue2 = 1;
            break;
         case 2:
            if (this.intValue3 < 0 || this.intValue3 >= 36) {
               this.intValue = 3;
               this.intValue2 = 1;
               return;
            }

            int intValue4 = CLIENT.player.currentScreenHandler.syncId;
            ItemStack itemStack3 = CLIENT.player.getInventory().getStack(this.intValue3);
            String text = this.resolve11(itemStack3, this.text2);
            if (!itemStack3.isEmpty() && !CLIENT.player.isSprinting()) {
               CLIENT.interactionManager
                  .clickSlot(intValue4, this.intValue3 < 9 ? this.intValue3 + 36 : this.intValue3, 40, SlotActionType.SWAP, CLIENT.player);
               this.invoke19(itemStack3, text);
            }

            CLIENT.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(intValue4));
            this.intValue = 3;
            this.intValue2 = 1;
            break;
         case 3:
            InputUtils.getINSTANCE().invoke2("AutoSwap");
            this.intValue = 0;
            this.intValue3 = -1;
            flag5 = false;
      }
   }

   private void invoke2() {
      boolean flag = false;
      if (this.flag2) {
         String text2 = vtoroyPredmet.getValue();
         switch (text2) {
            case "Шар 2":
               flag = this.check2(Items.PLAYER_HEAD, "Шар", false);
               break;
            case "Золотое яблоко 2":
               flag = this.check2(Items.GOLDEN_APPLE, "Золотое яблоко", false);
               break;
            case "Тотем 2":
               flag = this.check2(Items.TOTEM_OF_UNDYING, "Тотем", tolkoZacharovanyhTotemov.isEnabled());
               break;
            case "Щит 2":
               flag = this.check2(Items.SHIELD, "Щит", false);
         }

         if (flag || !this.check(vtoroyPredmet.getValue())) {
            this.flag2 = false;
         }
      } else {
         String text3 = pervyyPredmet.getValue();
         switch (text3) {
            case "Шар":
               flag = this.check2(Items.PLAYER_HEAD, "Шар", false);
               break;
            case "Тотем":
               flag = this.check2(Items.TOTEM_OF_UNDYING, "Тотем", tolkoZacharovanyhTotemov.isEnabled());
               break;
            case "Золотое яблоко":
               flag = this.check2(Items.GOLDEN_APPLE, "Золотое яблоко", false);
               break;
            case "Щит":
               flag = this.check2(Items.SHIELD, "Щит", false);
         }

         if (flag || !this.check(pervyyPredmet.getValue())) {
            this.flag2 = true;
         }
      }
   }

   private boolean check(String string) {
      if (filtrSvapov.is("FT/RW")) {
         if ("Шар".equals(string)) {
            return true;
         }

         if ("Шар 2".equals(string)) {
            return true;
         }
      }

      return false;
   }

   private boolean check2(Item item, String string, boolean bl) {
      int intValue5 = item == Items.PLAYER_HEAD && filtrSvapov.is("FT/RW") ? this.compute2() : this.compute(item, bl);
      if (intValue5 == -1) {
         return false;
      } else {
         this.invoke3(intValue5, string);
         return true;
      }
   }

   private boolean check3(int i) {
      if (i >= 0 && i < 3) {
         ItemStack itemStack4 = this.itemStacks[i];
         String text4 = this.resolve3(i);
         if ((!itemStack4.isEmpty() || !text4.isEmpty()) && !this.check4(i)) {
            int intValue6 = this.compute3(itemStack4, text4);
            if (intValue6 == -1) {
               return false;
            } else {
               this.invoke3(intValue6, this.resolve11(CLIENT.player.getInventory().getStack(intValue6), itemStack4.getName().getString()));
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   private void invoke3(int i, String string) {
      this.intValue3 = i;
      this.text2 = string;
      this.intValue = 1;
      flag5 = true;
      this.invoke();
   }

   private void invoke4() {
      Sprint.intValue = 2;
      InputUtils.getINSTANCE().invoke("AutoSwap");
      CLIENT.options.sprintKey.setPressed(false);
      CLIENT.player.setSprinting(false);
   }

   private int compute(Item item, boolean bl) {
      if (CLIENT.player == null) {
         return -1;
      } else {
         for (int intValue7 = 0; intValue7 < 36; intValue7++) {
            ItemStack itemStack5 = CLIENT.player.getInventory().getStack(intValue7);
            if (itemStack5.isOf(item) && (!bl || itemStack5.hasEnchantments() || itemStack5.hasGlint())) {
               return intValue7;
            }
         }

         return -1;
      }
   }

   private int compute2() {
      if (CLIENT.player == null) {
         return -1;
      } else {
         for (int intValue8 = 0; intValue8 < 36; intValue8++) {
            ItemStack itemStack6 = CLIENT.player.getInventory().getStack(intValue8);
            if (this.check8(itemStack6)) {
               return intValue8;
            }
         }

         return -1;
      }
   }

   private int compute3(ItemStack itemStack, String string) {
      if (CLIENT.player == null) {
         return -1;
      } else {
         for (int intValue9 = 0; intValue9 < 36; intValue9++) {
            ItemStack itemStack7 = CLIENT.player.getInventory().getStack(intValue9);
            if (this.check7(itemStack7, itemStack, string)) {
               return intValue9;
            }
         }

         return -1;
      }
   }

   private boolean check4(int i) {
      if (CLIENT.player != null && i >= 0 && i < 3 && (!this.itemStacks[i].isEmpty() || !this.resolve3(i).isEmpty())) {
         ItemStack itemStack8 = CLIENT.player.getOffHandStack();
         return itemStack8 != null && !itemStack8.isEmpty() ? this.check7(itemStack8, this.itemStacks[i], this.resolve3(i)) : false;
      } else {
         return false;
      }
   }

   private void invoke5() {
      this.flag3 = true;
      this.intValue4 = -1;
      this.invoke9();
      flag5 = true;
      this.timestamp = this.timestamp2 = this.timestamp3 = System.nanoTime();
      Arrays.fill(this.floats, 0.0F);
      this.invoke16();
      this.floatValue3 = this.floatValue;
      this.floatValue4 = this.floatValue2;
      if (CLIENT.mouse != null) {
         CLIENT.mouse.unlockCursor();
      }

      this.invoke17();
   }

   private void invoke6(boolean bl) {
      if (this.flag3) {
         this.invoke15();
         boolean flag2 = this.flag4;
         int intValue10 = flag2
            ? -1
            : this.compute5(
               this.floatValue, this.floatValue2, CLIENT.getWindow().getFramebufferWidth(), CLIENT.getWindow().getFramebufferHeight()
            );
         this.flag3 = false;
         this.intValue4 = -1;
         this.invoke9();
         flag = false;
         boolean flag3 = bl && intValue10 != -1 && this.check3(intValue10);
         if (!flag3 && this.intValue == 0) {
            flag5 = false;
         }

         if (CLIENT.currentScreen == null && CLIENT.mouse != null) {
            CLIENT.mouse.lockCursor();
         }
      }
   }

   private void invoke7(int i) {
      if (CLIENT.player != null && CLIENT.getWindow() != null) {
         if (this.flag4) {
            if (i == 0) {
               this.invoke8();
            }

            if (i == 1) {
               this.invoke9();
            }
         } else {
            int intValue11 = this.compute5(
               this.floatValue, this.floatValue2, CLIENT.getWindow().getFramebufferWidth(), CLIENT.getWindow().getFramebufferHeight()
            );
            if (intValue11 != -1) {
               if (i == 0) {
                  this.flag4 = true;
                  this.intValue5 = intValue11;
                  this.timestamp2 = System.nanoTime();
                  flag = true;
               } else if (i == 1) {
                  this.itemStacks[intValue11] = ItemStack.EMPTY;
                  this.text[intValue11] = "";
                  this.invoke20();
               }
            }
         }
      }
   }

   private void invoke8() {
      int intValue12 = this.compute7(
         this.floatValue, this.floatValue2, CLIENT.getWindow().getFramebufferWidth(), CLIENT.getWindow().getFramebufferHeight()
      );
      if (intValue12 >= 0 && this.intValue5 >= 0 && this.intValue5 < 3) {
         ItemStack itemStack9 = CLIENT.player.getInventory().getStack(intValue12);
         if (!itemStack9.isEmpty()) {
            ItemStack itemStack10 = itemStack9.copy();
            itemStack10.setCount(1);
            this.itemStacks[this.intValue5] = itemStack10;
            this.text[this.intValue5] = this.resolve5(itemStack10);
            this.invoke9();
            this.invoke20();
         }
      } else {
         this.invoke9();
      }
   }

   private void invoke9() {
      this.flag4 = false;
      this.intValue5 = -1;
      flag = false;
   }

   private void invoke10() {
      long longValue = System.nanoTime();
      float floatValue = this.timestamp3 == 0L ? 0.016F : this.measure5((float)(longValue - this.timestamp3) / 1.0E9F, 0.001F, 0.05F);
      this.timestamp3 = longValue;

      for (int intValue13 = 0; intValue13 < 3; intValue13++) {
         this.floats[intValue13] = this.measure6(this.floats[intValue13], intValue13 == this.intValue4 ? 1.0F : 0.0F, floatValue, 20.0F);
      }
   }

   private float measure(int i) {
      float floatValue2 = (float)(System.nanoTime() - this.timestamp) / 1000000.0F - i * 24.0F;
      return this.measure7(this.measure5(floatValue2 / 135.0F, 0.0F, 1.0F));
   }

   private float measure2() {
      return this.measure7(this.measure5((float)(System.nanoTime() - this.timestamp2) / 1.2E8F, 0.0F, 1.0F));
   }

   private float measure3(int i, int j) {
      float floatValue3 = (float)(System.nanoTime() - this.timestamp2) / 1000000.0F - (i * 9 + j) * 3.2F;
      return this.measure7(this.measure5(floatValue3 / 120.0F, 0.0F, 1.0F));
   }

   private void invoke11(RenderManager renderManager2, DrawContext drawContext, int i, int j) {
      for (int intValue14 = 0; intValue14 < 3; intValue14++) {
         AutoSwap.AutoSwapData autoSwapData = this.resolve2(intValue14, i, j);
         boolean flag4 = intValue14 == this.intValue4;
         boolean flag5 = this.check4(intValue14);
         float floatValue4 = this.measure(intValue14);
         float floatValue5 = this.floats[intValue14];
         float floatValue6 = autoSwapData.size * (0.86F + floatValue4 * 0.14F + floatValue5 * 0.035F);
         float floatValue7 = this.measure8(i / 2.0F, autoSwapData.centerX(), floatValue4);
         float floatValue8 = this.measure8(j / 2.0F, autoSwapData.centerY(), floatValue4);
         float floatValue9 = floatValue7 - floatValue6 / 2.0F;
         float floatValue10 = floatValue8 - floatValue6 / 2.0F;
         float floatValue11 = Math.max(8.0F, floatValue6 * 0.16F);
         int intValue15 = flag5
            ? ColorUtils.compute43(90, 20, 26, flag4 ? 180 : 135)
            : (flag4 ? ColorUtils.compute43(70, 66, 28, 168) : ColorUtils.compute43(24, 26, 32, 132));
         int intValue16 = flag5
            ? ColorUtils.compute43(44, 14, 18, flag4 ? 170 : 120)
            : (flag4 ? ColorUtils.compute43(34, 34, 22, 156) : ColorUtils.compute43(12, 14, 18, 118));
         int intValue17 = flag5
            ? ColorUtils.compute43(255, 65, 75, flag4 ? 230 : 190)
            : (flag4 ? ColorUtils.compute43(255, 245, 110, 215) : ColorUtils.compute43(255, 255, 255, 115));
         renderManager2.invoke65(floatValue4);
         renderManager2.invoke41(
            floatValue9,
            floatValue10,
            floatValue6,
            floatValue6,
            floatValue11,
            flag4 ? 10.0F : 6.0F,
            1.5F,
            flag5 ? ColorUtils.compute43(255, 55, 65, flag4 ? 70 : 45) : ColorUtils.compute43(0, 0, 0, flag4 ? 90 : 60)
         );
         this.invoke13(renderManager2, floatValue9, floatValue10, floatValue6, floatValue6, floatValue11, intValue15, intValue16, intValue17, flag4 ? 23.0F : 60.0F, flag5 ? 2.4F : (flag4 ? 2.0F : 1.25F));
         if (this.itemStacks[intValue14].isEmpty()) {
            this.invoke14(renderManager2, floatValue7, floatValue8, floatValue6 * 0.28F, ColorUtils.compute43(255, 255, 255, flag4 ? 230 : 160));
         }

         renderManager2.invoke66();
      }

      renderManager2.invoke20();

      for (int intValue18 = 0; intValue18 < 3; intValue18++) {
         ItemStack itemStack11 = this.itemStacks[intValue18];
         if (!itemStack11.isEmpty()) {
            AutoSwap.AutoSwapData autoSwapData2 = this.resolve2(intValue18, i, j);
            float floatValue12 = this.measure(intValue18);
            if (!(floatValue12 <= 0.08F)) {
               float floatValue13 = this.floats[intValue18];
               float floatValue14 = this.measure8(i / 2.0F, autoSwapData2.centerX(), floatValue12);
               float floatValue15 = this.measure8(j / 2.0F, autoSwapData2.centerY(), floatValue12);
               float floatValue16 = (intValue18 == this.intValue4 ? 2.85F : 2.55F) * (0.84F + floatValue12 * 0.16F + floatValue13 * 0.035F);
               float floatValue17 = 16.0F * floatValue16;
               float floatValue18 = autoSwapData2.size * (0.86F + floatValue12 * 0.14F + floatValue13 * 0.035F);
               float floatValue19 = floatValue14 - floatValue18 * 0.5F;
               float floatValue20 = floatValue15 - floatValue18 * 0.5F;
               float floatValue21 = Math.max(8.0F, floatValue18 * 0.16F);
               renderManager2.invoke24(floatValue19, floatValue20, floatValue18, floatValue18, floatValue21, floatValue21, floatValue21, floatValue21);
               boolean flag6 = false ;

               try {
                  flag6 = true;
                  ItemRenderUtil.invoke3(
                     renderManager2,
                     itemStack11,
                     ItemRenderUtil.measure(floatValue14 - floatValue17 * 0.5F),
                     ItemRenderUtil.measure(floatValue15 - floatValue17 * 0.5F),
                     ItemRenderUtil.measure3(floatValue16),
                     intValue18,
                     true,
                     intValue18
                  );
                  flag6 = false;
               } finally {
                  if (flag6) {
                     renderManager2.invoke20();
                     renderManager2.invoke25();
                  }
               }

               renderManager2.invoke20();
               renderManager2.invoke25();
            }
         }
      }
   }

   private void invoke12(RenderManager renderManager3, DrawContext drawContext, int i, int j) {
      AutoSwap.AutoSwapBounds autoSwapBounds = this.resolve(i, j);
      float floatValue22 = 10.0F;
      float floatValue23 = autoSwapBounds.startX - floatValue22;
      float floatValue24 = autoSwapBounds.startY - floatValue22;
      float floatValue25 = autoSwapBounds.width + floatValue22 * 2.0F;
      float floatValue26 = autoSwapBounds.height + floatValue22 * 2.0F;
      int intValue19 = ColorUtils.compute43(15, 15, 18, 150);
      int intValue20 = ColorUtils.compute43(255, 255, 255, 80);
      float floatValue27 = this.measure2();
      renderManager3.invoke65(floatValue27);
      this.invoke13(renderManager3, floatValue23, floatValue24, floatValue25, floatValue26, 10.0F, intValue19, ColorUtils.compute43(8, 8, 10, 130), intValue20, 23.0F, 1.25F);
      int intValue21 = this.compute7(this.floatValue, this.floatValue2, i, j);

      for (int intValue22 = 0; intValue22 < 4; intValue22++) {
         for (int intValue23 = 0; intValue23 < 9; intValue23++) {
            int intValue24 = this.compute8(intValue22, intValue23);
            float floatValue28 = autoSwapBounds.startX + intValue23 * (autoSwapBounds.slotSize + autoSwapBounds.gap);
            float floatValue29 = autoSwapBounds.startY + intValue22 * (autoSwapBounds.slotSize + autoSwapBounds.gap);
            ItemStack itemStack12 = CLIENT.player.getInventory().getStack(intValue24);
            boolean flag7 = intValue24 == intValue21;
            boolean flag8 = this.check5(itemStack12);
            int intValue25 = flag7 ? ColorUtils.compute43(255, 255, 255, 75) : ColorUtils.compute43(0, 0, 0, 75);
            int intValue26 = flag8
               ? ColorUtils.compute43(255, 55, 65, 225)
               : (flag7 ? ColorUtils.compute43(255, 245, 120, 210) : ColorUtils.compute43(255, 255, 255, 45));
            if (flag7) {
               this.invoke13(
                  renderManager3,
                  floatValue28,
                  floatValue29,
                  autoSwapBounds.slotSize,
                  autoSwapBounds.slotSize,
                  5.0F,
                  intValue25,
                  ColorUtils.compute43(0, 0, 0, 62),
                  intValue26,
                  23.0F,
                  flag8 ? 2.0F : 1.0F
               );
            } else {
               renderManager3.invoke5(floatValue28, floatValue29, autoSwapBounds.slotSize, autoSwapBounds.slotSize, 5.0F, intValue25);
               renderManager3.invoke28(floatValue28, floatValue29, autoSwapBounds.slotSize, autoSwapBounds.slotSize, 5.0F, intValue26, flag8 ? 2.0F : 1.0F);
            }
         }
      }

      renderManager3.invoke66();
      renderManager3.invoke20();
      renderManager3.invoke24(floatValue23, floatValue24, floatValue25, floatValue26, 10.0F, 10.0F, 10.0F, 10.0F);
      boolean flag9 = false ;

      try {
         flag9 = true;

         for (int intValue27 = 0; intValue27 < 4; intValue27++) {
            for (int intValue28 = 0; intValue28 < 9; intValue28++) {
               int intValue29 = this.compute8(intValue27, intValue28);
               ItemStack itemStack13 = CLIENT.player.getInventory().getStack(intValue29);
               if (!itemStack13.isEmpty()) {
                  float floatValue30 = this.measure3(intValue27, intValue28);
                  if (!(floatValue30 <= 0.05F)) {
                     float floatValue31 = autoSwapBounds.slotSize / 22.0F * (0.76F + floatValue30 * 0.24F);
                     float floatValue32 = 16.0F * floatValue31;
                     float floatValue33 = autoSwapBounds.startX + intValue28 * (autoSwapBounds.slotSize + autoSwapBounds.gap) + (autoSwapBounds.slotSize - floatValue32) / 2.0F;
                     float floatValue34 = autoSwapBounds.startY + intValue27 * (autoSwapBounds.slotSize + autoSwapBounds.gap) + (autoSwapBounds.slotSize - floatValue32) / 2.0F;
                     ItemRenderUtil.invoke3(
                        renderManager3,
                        itemStack13,
                        ItemRenderUtil.measure(floatValue33),
                        ItemRenderUtil.measure(floatValue34),
                        ItemRenderUtil.measure3(floatValue31),
                        intValue29,
                        true,
                        intValue29
                     );
                  }
               }
            }
         }

         flag9 = false;
      } finally {
         if (flag9) {
            renderManager3.invoke20();
            renderManager3.invoke25();
         }
      }

      renderManager3.invoke20();
      renderManager3.invoke25();
   }

   private int compute4(float f, float g, int i, int j) {
      for (int intValue30 = 0; intValue30 < 3; intValue30++) {
         AutoSwap.AutoSwapData autoSwapData3 = this.resolve2(intValue30, i, j);
         if (ClickGuiRenderUtils.check2(f, g, autoSwapData3.x, autoSwapData3.y, autoSwapData3.size, autoSwapData3.size)) {
            return intValue30;
         }
      }

      return -1;
   }

   private int compute5(float f, float g, int i, int j) {
      int intValue31 = this.compute4(f, g, i, j);
      return intValue31 != -1 ? intValue31 : this.compute6(f, g, i, j);
   }

   private int compute6(float f, float g, int i, int j) {
      float floatValue35 = f - this.floatValue3;
      float floatValue36 = g - this.floatValue4;
      float floatValue37 = this.measure5(Math.min(i, j) * 0.035F, 18.0F, 38.0F);
      float floatValue38 = floatValue35 * floatValue35 + floatValue36 * floatValue36;
      if (floatValue38 < floatValue37 * floatValue37) {
         return -1;
      } else {
         float floatValue39 = 1.0F / (float)Math.sqrt(floatValue38);
         float floatValue40 = floatValue35 * floatValue39;
         float floatValue41 = floatValue36 * floatValue39;
         if (floatValue41 > 0.82F && Math.abs(floatValue40) < 0.38F) {
            return -1;
         } else {
            float floatValue42 = -floatValue41;
            float floatValue43 = floatValue40 * 0.848F + floatValue41 * 0.53F;
            float floatValue44 = -floatValue40 * 0.848F + floatValue41 * 0.53F;
            float floatValue45 = Math.max(floatValue42, Math.max(floatValue43, floatValue44));
            if (floatValue45 < 0.45F) {
               return -1;
            } else if (floatValue45 == floatValue42) {
               return 0;
            } else {
               return floatValue45 == floatValue43 ? 1 : 2;
            }
         }
      }
   }

   private int compute7(float f, float g, int i, int j) {
      AutoSwap.AutoSwapBounds autoSwapBounds2 = this.resolve(i, j);

      for (int intValue32 = 0; intValue32 < 4; intValue32++) {
         for (int intValue33 = 0; intValue33 < 9; intValue33++) {
            float floatValue46 = autoSwapBounds2.startX + intValue33 * (autoSwapBounds2.slotSize + autoSwapBounds2.gap);
            float floatValue47 = autoSwapBounds2.startY + intValue32 * (autoSwapBounds2.slotSize + autoSwapBounds2.gap);
            if (f >= floatValue46 && f <= floatValue46 + autoSwapBounds2.slotSize && g >= floatValue47 && g <= floatValue47 + autoSwapBounds2.slotSize) {
               return this.compute8(intValue32, intValue33);
            }
         }
      }

      return -1;
   }

   private AutoSwap.AutoSwapBounds resolve(int i, int j) {
      float floatValue48 = this.measure5(Math.min(i, j) * 0.042F, 30.0F, 42.0F);
      float floatValue49 = Math.max(4.0F, floatValue48 * 0.14F);
      float floatValue50 = floatValue48 * 9.0F + floatValue49 * 8.0F;
      float floatValue51 = floatValue48 * 4.0F + floatValue49 * 3.0F;
      float floatValue52 = (i - floatValue50) / 2.0F;
      float floatValue53 = (j - floatValue51) / 2.0F;
      return new AutoSwap.AutoSwapBounds(floatValue52, floatValue53, floatValue48, floatValue49, floatValue50, floatValue51);
   }

   private int compute8(int i, int j) {
      return i == 3 ? j : 9 + i * 9 + j;
   }

   private AutoSwap.AutoSwapData resolve2(int i, int j, int k) {
      float floatValue54 = this.measure4(j, k);
      float floatValue55 = j / 2.0F;
      float floatValue56 = k / 2.0F;
      float floatValue57 = floatValue54 * 1.35F;
      float floatValue58 = floatValue54 * 1.25F;
      float floatValue59 = floatValue54 * 0.85F;
      float floatValue60 = floatValue55 - floatValue54 / 2.0F;
      float floatValue61 = floatValue56 - floatValue58 - floatValue54 / 2.0F;
      if (i == 1) {
         floatValue60 = floatValue55 + floatValue57 - floatValue54 / 2.0F;
         floatValue61 = floatValue56 + floatValue59 - floatValue54 / 2.0F;
      } else if (i == 2) {
         floatValue60 = floatValue55 - floatValue57 - floatValue54 / 2.0F;
         floatValue61 = floatValue56 + floatValue59 - floatValue54 / 2.0F;
      }

      return new AutoSwap.AutoSwapData(floatValue60, floatValue61, floatValue54, Math.max(8.0F, floatValue54 * 0.16F));
   }

   private float measure4(int i, int j) {
      return this.measure5(Math.min(i, j) * 0.155F, 76.0F, 118.0F);
   }

   private void invoke13(RenderManager renderManager4, float f, float g, float h, float i, float j, int k, int l, int m, float n, float o) {
      ClickGuiRenderUtils.invoke6(renderManager4, f, g, h, i, j, () -> {
         renderManager4.invoke44(f, g, h, i, j, n);
         renderManager4.invoke37(f, g, h, i, 0.0F, k, l);
      });
      renderManager4.invoke28(f, g, h, i, j, m, o);
   }

   private void invoke14(RenderManager renderManager5, float f, float g, float h, int i) {
      float floatValue62 = Math.max(2.0F, h * 0.16F);
      renderManager5.invoke5(f - floatValue62 / 2.0F, g - h / 2.0F, floatValue62, h, floatValue62 / 2.0F, i);
      renderManager5.invoke5(f - h / 2.0F, g - floatValue62 / 2.0F, h, floatValue62, floatValue62 / 2.0F, i);
   }

   private boolean check5(ItemStack itemStack) {
      if (CLIENT.player != null && itemStack != null && !itemStack.isEmpty()) {
         ItemStack itemStack14 = CLIENT.player.getOffHandStack();
         return itemStack14 != null && !itemStack14.isEmpty() && this.check7(itemStack14, itemStack, this.resolve4(itemStack));
      } else {
         return false;
      }
   }

   private boolean check6(ItemStack itemStack, ItemStack itemStack2) {
      return this.check7(itemStack, itemStack2, this.resolve4(itemStack2));
   }

   private boolean check7(ItemStack itemStack, ItemStack itemStack2, String string) {
      if (itemStack == null || itemStack.isEmpty()) {
         return false;
      } else if (itemStack2 != null && !itemStack2.isEmpty()) {
         String text5 = this.resolve8(string);
         if (!text5.isEmpty()) {
            return "ft:sphere:any".equals(text5) ? this.check8(itemStack) : text5.equals(this.resolve4(itemStack));
         } else if (!filtrSvapov.is("FT/RW") || !itemStack2.isOf(Items.PLAYER_HEAD) && !this.check8(itemStack2)) {
            return !itemStack.isOf(itemStack2.getItem())
               ? false
               : itemStack2.getComponentChanges().isEmpty() || ItemStack.areItemsAndComponentsEqual(itemStack, itemStack2);
         } else {
            return this.check8(itemStack);
         }
      } else {
         String text6 = this.resolve8(string);
         return "ft:sphere:any".equals(text6) ? this.check8(itemStack) : !text6.isEmpty() && text6.equals(this.resolve4(itemStack));
      }
   }

   private String resolve3(int i) {
      if (i >= 0 && i < 3) {
         String text7 = this.resolve8(this.text[i]);
         return !text7.isEmpty() ? text7 : this.resolve4(this.itemStacks[i]);
      } else {
         return "";
      }
   }

   private String resolve4(ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty()) {
         String text8 = this.resolve7(itemStack);
         if (!text8.isEmpty()) {
            return text8;
         } else {
            if (itemStack.contains(DataComponentTypes.CUSTOM_NAME)) {
               String text9 = this.resolve9(itemStack.getName().getString());
               if (!text9.isEmpty()) {
                  return "name:" + Registries.ITEM.getId(itemStack.getItem()) + ":" + text9;
               }
            }

            return "item:" + Registries.ITEM.getId(itemStack.getItem());
         }
      } else {
         return "";
      }
   }

   private String resolve5(ItemStack itemStack) {
      return this.resolve4(itemStack);
   }

   private String resolve6(String string, ItemStack itemStack) {
      String text10 = this.resolve8(string);
      if ("ft:sphere:any".equals(text10) && itemStack != null && !itemStack.isEmpty()) {
         String text11 = this.resolve4(itemStack);
         if (text11.startsWith("ft:sphere:") && !"ft:sphere:any".equals(text11)) {
            return text11;
         }
      }

      return text10;
   }

   private String resolve7(ItemStack itemStack) {
      if (SpecialItemUtils.check8(itemStack)) {
         return "ft:sphere:haos";
      } else if (SpecialItemUtils.check9(itemStack)) {
         return "ft:sphere:titan";
      } else if (SpecialItemUtils.check10(itemStack)) {
         return "ft:sphere:ares";
      } else if (SpecialItemUtils.check11(itemStack)) {
         return "ft:sphere:besti";
      } else if (SpecialItemUtils.check12(itemStack)) {
         return "ft:sphere:gidra";
      } else if (SpecialItemUtils.check13(itemStack)) {
         return "ft:sphere:ikara";
      } else if (SpecialItemUtils.check14(itemStack)) {
         return "ft:sphere:erida";
      } else if (SpecialItemUtils.check15(itemStack)) {
         return "ft:sphere:satira";
      } else if (SpecialItemUtils.check16(itemStack)) {
         return "ft:sphere:moroz";
      } else if (SpecialItemUtils.check17(itemStack)) {
         return "ft:talisman:demon";
      } else if (SpecialItemUtils.check18(itemStack)) {
         return "ft:talisman:karatel";
      } else if (SpecialItemUtils.check19(itemStack)) {
         return "ft:talisman:mrak";
      } else if (SpecialItemUtils.check20(itemStack)) {
         return "ft:talisman:yaristi";
      } else if (SpecialItemUtils.check21(itemStack)) {
         return "ft:talisman:tiran";
      } else if (SpecialItemUtils.check22(itemStack)) {
         return "ft:talisman:krushitel";
      } else if (SpecialItemUtils.check23(itemStack)) {
         return "ft:talisman:razdor";
      } else if (SpecialItemUtils.check24(itemStack)) {
         return "ft:talisman:sara";
      } else if (SpecialItemUtils.check25(itemStack)) {
         return "ft:potion:assassin";
      } else if (SpecialItemUtils.check26(itemStack)) {
         return "ft:potion:gnev";
      } else if (SpecialItemUtils.check27(itemStack)) {
         return "ft:potion:hlopushka";
      } else if (SpecialItemUtils.check28(itemStack)) {
         return "ft:potion:holy_water";
      } else if (SpecialItemUtils.check29(itemStack)) {
         return "ft:potion:paladin";
      } else if (SpecialItemUtils.check30(itemStack)) {
         return "ft:potion:radiation";
      } else if (SpecialItemUtils.check31(itemStack)) {
         return "ft:potion:snotvornoye";
      } else if (SpecialItemUtils.check32(itemStack)) {
         return "ft:item:light_dust";
      } else if (SpecialItemUtils.check33(itemStack)) {
         return "ft:item:disorientation";
      } else if (SpecialItemUtils.check34(itemStack)) {
         return "ft:item:trapka";
      } else if (SpecialItemUtils.check35(itemStack)) {
         return "ft:item:lockpick_spheres";
      } else if (SpecialItemUtils.check36(itemStack)) {
         return "ft:item:plast";
      } else if (SpecialItemUtils.check51(itemStack)) {
         return "ft:item:dragon_skin";
      } else if (SpecialItemUtils.check52(itemStack)) {
         return "ft:item:fire_whirlwind";
      } else if (SpecialItemUtils.check53(itemStack)) {
         return "ft:item:freezing_snowball";
      } else if (SpecialItemUtils.check54(itemStack)) {
         return "ft:item:gods_aura";
      } else {
         return SpecialItemUtils.check55(itemStack) ? "ft:item:silver" : "";
      }
   }

   private String resolve8(String string) {
      return string == null ? "" : string.trim().toLowerCase(Locale.ROOT);
   }

   private String resolve9(String string) {
      return string == null
         ? ""
         : string.replaceAll("§.", "").replaceAll("В§.", "").replaceAll("&.", "").replace(' ', ' ').replaceAll("\\s+", " ").trim().toLowerCase(Locale.ROOT);
   }

   private boolean check8(ItemStack itemStack) {
      return itemStack != null
         && itemStack.isOf(Items.PLAYER_HEAD)
         && (this.check11(itemStack) || this.check9(itemStack) || this.check10(itemStack));
   }

   private boolean check9(ItemStack itemStack) {
      AttributeModifiersComponent attributeModifiersComponent = (AttributeModifiersComponent)itemStack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
      if (attributeModifiersComponent == null) {
         return false;
      } else {
         for (Entry entry : attributeModifiersComponent.modifiers()) {
            if (entry.slot().matches(EquipmentSlot.OFFHAND)) {
               return true;
            }
         }

         return false;
      }
   }

   private boolean check10(ItemStack itemStack) {
      if (itemStack != null && !itemStack.isEmpty()) {
         LoreComponent loreComponent = (LoreComponent)itemStack.get(DataComponentTypes.LORE);
         if (loreComponent == null) {
            return false;
         } else {
            String text12 = this.resolve10(I18n.translate("item.modifiers.offhand", new Object[0]));
            StringBuilder stringBuilder = new StringBuilder();

            for (Text text13 : loreComponent.lines()) {
               String text14 = this.resolve10(text13.getString());
               stringBuilder.append(' ').append(text14);
               if (text14.contains("when in off hand")
                  || !text12.isEmpty() && text14.contains(text12)
                  || text14.contains("когда во второстепенной")
                  || text14.contains("коли в другій руці")
                  || text14.contains("при ношении в левой")
                  || text14.contains("в лівій руці")) {
                  return true;
               }
            }

            String text15 = stringBuilder.toString();
            return text15.contains("when in off hand")
               || !text12.isEmpty() && text15.contains(text12)
               || text15.contains("когда во второстепенной")
               || text15.contains("коли в другій руці")
               || text15.contains("при ношении в левой")
               || text15.contains("в лівій руці");
         }
      } else {
         return false;
      }
   }

   private boolean check11(ItemStack itemStack) {
      if (CLIENT.player != null && CLIENT.world != null) {
         try {
            for (Text text16 : itemStack.getTooltip(TooltipContext.create(CLIENT.world), CLIENT.player, Default.BASIC)) {
               if (this.check12(this.resolve10(text16.getString()))) {
                  return true;
               }
            }

            return false;
         } catch (Throwable exception) {
            return false;
         }
      } else {
         return false;
      }
   }

   private String resolve10(String string) {
      return string == null ? "" : string.replaceAll("§[0-9a-fk-orA-FK-OR]", "").replace(' ', ' ').replaceAll("\\s+", " ").trim().toLowerCase(Locale.ROOT);
   }

   private boolean check12(String string) {
      String text17 = this.resolve10(I18n.translate("item.modifiers.offhand", new Object[0]));
      return string.contains("when in off hand")
         || !text17.isEmpty() && string.contains(text17)
         || string.contains("когда во второстепенной")
         || string.contains("коли в другій руці")
         || string.contains("при ношении в левой")
         || string.contains("в лівій руці");
   }

   private void invoke15() {
      if (CLIENT.getWindow() != null) {
         double[] doubleValues = new double[1];
         double[] doubleValues2 = new double[1];
         GLFW.glfwGetCursorPos(CLIENT.getWindow().getHandle(), doubleValues, doubleValues2);
         this.invoke18((float)doubleValues[0], (float)doubleValues2[0]);
      }
   }

   private void invoke16() {
      Window window = CLIENT.getWindow();
      if (window != null && !window.hasZeroWidthOrHeight() && window.getFramebufferWidth() > 0 && window.getFramebufferHeight() > 0) {
         this.floatValue = window.getFramebufferWidth() * 0.5F;
         this.floatValue2 = window.getFramebufferHeight() * 0.5F;
      }
   }

   private void invoke17() {
      Window window2 = CLIENT.getWindow();
      if (window2 != null && !window2.hasZeroWidthOrHeight() && window2.getWidth() > 0 && window2.getHeight() > 0) {
         GLFW.glfwSetCursorPos(window2.getHandle(), window2.getWidth() * 0.5, window2.getHeight() * 0.5);
      }
   }

   private void invoke18(float f, float g) {
      if (Float.isFinite(f) && Float.isFinite(g)) {
         Window window3 = CLIENT.getWindow();
         if (window3 != null
            && !window3.hasZeroWidthOrHeight()
            && window3.getFramebufferWidth() > 0
            && window3.getFramebufferHeight() > 0
            && window3.getWidth() > 0
            && window3.getHeight() > 0) {
            this.floatValue = this.measure5(
               (float)((double)(f * window3.getFramebufferWidth()) / window3.getWidth()), 0.0F, Math.max(0.0F, window3.getFramebufferWidth() - 1.0F)
            );
            this.floatValue2 = this.measure5(
               (float)((double)(g * window3.getFramebufferHeight()) / window3.getHeight()), 0.0F, Math.max(0.0F, window3.getFramebufferHeight() - 1.0F)
            );
            return;
         }

         this.floatValue = f;
         this.floatValue2 = g;
      }
   }

   private boolean check13(int i) {
      if (CLIENT.getWindow() == null) {
         return false;
      } else {
         long longValue2 = CLIENT.getWindow().getHandle();
         if (i >= 0) {
            return InputUtil.isKeyPressed(longValue2, i);
         } else if (i > -100) {
            return false;
         } else {
            int intValue34 = -i - 100;
            return intValue34 >= 0 && intValue34 <= 7 && GLFW.glfwGetMouseButton(longValue2, intValue34) == 1;
         }
      }
   }

   private void invoke19(ItemStack itemStack, String string) {
      NotificationsHud.invoke3(itemStack, string, 2200L);
   }

   private void invoke20() {
      if (WildClient.INSTANCE != null && WildClient.INSTANCE.configManager != null) {
         WildClient.INSTANCE.configManager.scheduleSave();
      }
   }

   private String resolve11(ItemStack itemStack, String string) {
      if (itemStack == null || itemStack.isEmpty()) {
         return string;
      } else if (SpecialItemUtils.check8(itemStack)) {
         return "Сфера Хаоса";
      } else if (SpecialItemUtils.check9(itemStack)) {
         return "Сфера Титана";
      } else if (SpecialItemUtils.check10(itemStack)) {
         return "Сфера Ареса";
      } else if (SpecialItemUtils.check11(itemStack)) {
         return "Сфера Бестии";
      } else if (SpecialItemUtils.check12(itemStack)) {
         return "Сфера Гидры";
      } else if (SpecialItemUtils.check13(itemStack)) {
         return "Сфера Икара";
      } else if (SpecialItemUtils.check14(itemStack)) {
         return "Сфера Эрида";
      } else if (SpecialItemUtils.check15(itemStack)) {
         return "Сфера Сатира";
      } else if (SpecialItemUtils.check16(itemStack)) {
         return "Сфера Мороз";
      } else if (SpecialItemUtils.check17(itemStack)) {
         return "Талисман Демона";
      } else if (SpecialItemUtils.check18(itemStack)) {
         return "Талисман Карателя";
      } else if (SpecialItemUtils.check19(itemStack)) {
         return "Талисман Мрака";
      } else if (SpecialItemUtils.check20(itemStack)) {
         return "Талисман Ярости";
      } else if (SpecialItemUtils.check21(itemStack)) {
         return "Талисман Тирана";
      } else if (SpecialItemUtils.check22(itemStack)) {
         return "Талисман Крушителя";
      } else if (SpecialItemUtils.check23(itemStack)) {
         return "Талисман Раздора";
      } else {
         return itemStack.contains(DataComponentTypes.CUSTOM_NAME) ? itemStack.getName().getString() : string;
      }
   }

   private void invoke21() {
      boolean flag10 = this.flag3;
      if (this.intValue > 0) {
         InputUtils.getINSTANCE().invoke2("AutoSwap");
      }

      this.flag3 = false;
      this.intValue4 = -1;
      this.invoke9();
      flag = false;
      this.intValue = 0;
      this.intValue2 = 0;
      this.intValue3 = -1;
      flag5 = false;
      if (flag10 && CLIENT.currentScreen == null && CLIENT.mouse != null) {
         CLIENT.mouse.lockCursor();
      }
   }

   @Override
   public JsonObject toJson() {
      JsonObject jsonObject2 = super.toJson();
      JsonObject jsonObject3 = new JsonObject();
      JsonArray jsonArray = new JsonArray();

      for (int intValue35 = 0; intValue35 < 3; intValue35++) {
         JsonObject jsonObject4 = new JsonObject();
         ItemStack itemStack15 = this.itemStacks[intValue35];
         if (itemStack15 != null && !itemStack15.isEmpty()) {
            jsonObject4.addProperty("item", Registries.ITEM.getId(itemStack15.getItem()).toString());
            String text18 = this.resolve3(intValue35);
            if (!text18.isEmpty()) {
               jsonObject4.addProperty("key", text18);
            }

            ItemStack itemStack16 = itemStack15.copy();
            itemStack16.setCount(1);
            ItemStack.CODEC.encodeStart(this.resolve12(), itemStack16).result().ifPresent(jsonElement -> jsonObject4.add("stack", jsonElement));
         }

         jsonArray.add(jsonObject4);
      }

      jsonObject3.add("Slots", jsonArray);
      jsonObject2.add("AutoSwapTrio", jsonObject3);
      return jsonObject2;
   }

   @Override
   public void loadFromJson(JsonObject jsonObject) {
      super.loadFromJson(jsonObject);
      if (jsonObject != null && jsonObject.has("AutoSwapTrio") && jsonObject.get("AutoSwapTrio").isJsonObject()) {
         JsonObject jsonObject5 = jsonObject.getAsJsonObject("AutoSwapTrio");
         if (jsonObject5.has("Slots") && jsonObject5.get("Slots").isJsonArray()) {
            ItemStack[] itemStacks = new ItemStack[]{ItemStack.EMPTY, ItemStack.EMPTY, ItemStack.EMPTY};
            String[] texts = new String[]{"", "", ""};
            JsonArray jsonArray2 = jsonObject5.getAsJsonArray("Slots");

            for (int intValue36 = 0; intValue36 < Math.min(3, jsonArray2.size()); intValue36++) {
               JsonElement jsonElement2 = jsonArray2.get(intValue36);
               if (jsonElement2 != null && jsonElement2.isJsonObject()) {
                  JsonObject jsonObject6 = jsonElement2.getAsJsonObject();
                  if (jsonObject6.has("key")) {
                     texts[intValue36] = this.resolve8(jsonObject6.get("key").getAsString());
                  }

                  if (jsonObject6.has("stack")) {
                     ItemStack itemStack17 = ItemStack.CODEC.parse(this.resolve12(), jsonObject6.get("stack")).result().orElse(ItemStack.EMPTY);
                     if (!itemStack17.isEmpty()) {
                        itemStack17.setCount(1);
                        itemStacks[intValue36] = itemStack17;
                        texts[intValue36] = this.resolve6(texts[intValue36], itemStack17);
                        if (texts[intValue36].isEmpty()) {
                           texts[intValue36] = this.resolve5(itemStack17);
                        }
                        continue;
                     }
                  }

                  if (jsonObject6.has("item")) {
                     Identifier identifier = Identifier.tryParse(jsonObject6.get("item").getAsString());
                     if (identifier != null) {
                        Item item2 = (Item)Registries.ITEM.get(identifier);
                        if (item2 != Items.AIR) {
                           itemStacks[intValue36] = new ItemStack(item2);
                           if (texts[intValue36].isEmpty()) {
                              texts[intValue36] = item2 == Items.PLAYER_HEAD && filtrSvapov.is("FT/RW") ? "ft:sphere:any" : this.resolve5(itemStacks[intValue36]);
                           }
                        }
                     }
                  }
               }
            }

            for (int intValue37 = 0; intValue37 < 3; intValue37++) {
               this.itemStacks[intValue37] = itemStacks[intValue37];
               this.text[intValue37] = texts[intValue37];
            }
         }
      }
   }

   private DynamicOps<JsonElement> resolve12() {
      if (CLIENT.world != null) {
         return CLIENT.world.getRegistryManager().getOps(JsonOps.INSTANCE);
      } else {
         return CLIENT.getNetworkHandler() != null
            ? CLIENT.getNetworkHandler().getRegistryManager().getOps(JsonOps.INSTANCE)
            : BuiltinRegistries.createWrapperLookup().getOps(JsonOps.INSTANCE);
      }
   }

   @Override
   public void onDisable() {
      this.invoke21();
      super.onDisable();
   }

   private float measure5(float f, float g, float h) {
      return Math.max(g, Math.min(h, f));
   }

   private float measure6(float f, float g, float h, float i) {
      return f + (g - f) * (1.0F - (float)Math.exp(-i * h));
   }

   private float measure7(float f) {
      float floatValue63 = 1.0F - this.measure5(f, 0.0F, 1.0F);
      return 1.0F - floatValue63 * floatValue63 * floatValue63;
   }

   private float measure8(float f, float g, float h) {
      return f + (g - f) * h;
   }

   record AutoSwapBounds(float startX, float startY, float slotSize, float gap, float width, float height) {
   }

   record AutoSwapData(float x, float y, float size, float radius) {

      float centerX() {
         return this.x + this.size / 2.0F;
      }

      float centerY() {
         return this.y + this.size / 2.0F;
      }
   }
}
