package ru.metaculture.protection;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.pathing.goals.GoalNear;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Queue;
import java.util.Map.Entry;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AutoCraft",
   category = Category.Misc,
   description = "Автоматически крафтит выбранный рецепт"
)
public class AutoCraft extends Module {
   public final TextArraySetting retsept = new TextArraySetting("Рецепт");
   public final TextSetting kolVoPredmetov = new TextSetting("Кол-во предметов", "64").resolve(6);
   public final NumberSetting zaderzhka = new NumberSetting("Задержка", 80.0F, 20.0F, 500.0F, 10.0F, false);
   public final BooleanSetting neOtobrazhatEkran = new BooleanSetting("Не отображать экран", false);
   private final DualTimer dualTimer = new DualTimer();
   private final DualTimer dualTimer2 = new DualTimer();
   private final DualTimer dualTimer3 = new DualTimer();
   private final Queue<Runnable> queue = new ArrayDeque<>();
   private IBaritone iBaritone;
   private AutoCraft.AutoCraftState autoCraftState = AutoCraft.AutoCraftState.IDLE;
   private BlockPos blockPos;
   private int intValue;
   private int intValue2;
   private int intValue3;
   private String text = "";
   private CraftingScreen craftingScreen;

   public AutoCraft() {
      this.addSettings(new Setting[]{this.retsept, this.kolVoPredmetov, this.zaderzhka, this.neOtobrazhatEkran});
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.iBaritone = BaritoneAPI.getProvider().getPrimaryBaritone();
      this.intValue = 0;
      this.intValue2 = 0;
      this.intValue3 = 0;
      this.queue.clear();
      this.text = "";
      if (this.retsept.check()) {
         this.invoke13("§cРецепт пуст.");
      } else if (this.compute5() <= 0) {
         this.invoke13("§cНекорректное количество предметов.");
      } else {
         this.autoCraftState = AutoCraft.AutoCraftState.FINDING_TABLE;
         this.dualTimer.invoke();
         this.dualTimer2.invoke();
         this.dualTimer3.invoke();
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.queue.clear();
      this.blockPos = null;
      this.intValue2 = 0;
      this.intValue3 = 0;
      this.autoCraftState = AutoCraft.AutoCraftState.IDLE;
      this.craftingScreen = null;
      RotationController.rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
      if (this.iBaritone != null) {
         this.iBaritone.getPathingBehavior().cancelEverything();
      }
   }

   @EventHandler
   public void onScreenOpen(ScreenOpenEvent screenOpenEvent) {
      if (this.neOtobrazhatEkran.isEnabled() && this.autoCraftState != AutoCraft.AutoCraftState.IDLE && screenOpenEvent.getScreen() instanceof CraftingScreen craftingScreen) {
         this.craftingScreen = craftingScreen;
         screenOpenEvent.invoke();
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null && CLIENT.interactionManager != null) {
         if (!this.check()) {
            if (!this.check2()) {
               if (!this.text.isBlank()) {
                  this.invoke13("§cНе хватает предмета: §f" + this.resolve6(this.text));
               } else {
                  switch (this.autoCraftState) {
                     case IDLE:
                     default:
                        break;
                     case FINDING_TABLE:
                        this.invoke();
                        break;
                     case GOING_TO_TABLE:
                        this.invoke2();
                        break;
                     case AIMING_TABLE:
                        this.invoke3();
                        break;
                     case OPENING_TABLE:
                        this.invoke4();
                        break;
                     case CLEARING_GRID:
                        this.invoke5();
                        break;
                     case PLACING_RECIPE:
                        this.invoke6();
                        break;
                     case WAITING_RESULT:
                        this.invoke7();
                        break;
                     case TAKING_RESULT:
                        this.invoke8();
                        break;
                     case CLOSING:
                        this.invoke9();
                  }
               }
            }
         }
      }
   }

   private boolean check() {
      if (!PlayerHelper.check()) {
         return false;
      } else {
         if (this.iBaritone != null) {
            this.iBaritone.getPathingBehavior().cancelEverything();
         }

         RotationController.rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
         RotationController.intValue = 0;
         RotationController.rotation = null;
         return true;
      }
   }

   private void invoke() {
      if (this.dualTimer.check5(this.compute4())) {
         this.blockPos = this.resolve4();
         if (this.blockPos == null) {
            this.invoke13("§cВерстак рядом не найден.");
         } else {
            this.autoCraftState = AutoCraft.AutoCraftState.GOING_TO_TABLE;
            this.dualTimer.invoke();
            this.dualTimer2.invoke();
            this.dualTimer3.invoke();
         }
      }
   }

   private void invoke2() {
      if (!this.check4(this.blockPos)) {
         this.autoCraftState = AutoCraft.AutoCraftState.FINDING_TABLE;
         this.dualTimer.invoke();
      } else {
         double doubleValue = CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(this.blockPos));
         if (doubleValue <= 4.0) {
            if (this.iBaritone != null) {
               this.iBaritone.getPathingBehavior().cancelEverything();
            }

            this.autoCraftState = AutoCraft.AutoCraftState.AIMING_TABLE;
            this.dualTimer.invoke();
         } else {
            if (this.iBaritone != null && (!this.iBaritone.getCustomGoalProcess().isActive() || this.dualTimer2.check5(1500L))) {
               this.iBaritone.getCustomGoalProcess().setGoalAndPath(new GoalNear(this.blockPos, 2));
               this.dualTimer2.invoke();
            }

            if (this.dualTimer3.check5(15000L)) {
               this.invoke13("§cНе удалось дойти до верстака.");
            }
         }
      }
   }

   private void invoke3() {
      Rotation rotation = this.resolve5(Vec3d.ofCenter(this.blockPos));
      RotationController.invoke3(rotation, 45.0F, 45.0F, 30.0F, 30.0F, 4, 5, false);
      if (!(new Rotation(CLIENT.player).measure(rotation) > 4.0F) && this.dualTimer.check5(this.compute4())) {
         BlockHitResult blockHitResult = new BlockHitResult(Vec3d.ofCenter(this.blockPos), Direction.UP, this.blockPos, false);
         CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, blockHitResult);
         CLIENT.player.swingHand(Hand.MAIN_HAND);
         this.autoCraftState = AutoCraft.AutoCraftState.OPENING_TABLE;
         this.dualTimer.invoke();
      }
   }

   private void invoke4() {
      if (this.resolve() != null) {
         this.autoCraftState = AutoCraft.AutoCraftState.CLEARING_GRID;
         this.dualTimer.invoke();
      } else {
         if (this.dualTimer.check5(5000L)) {
            this.invoke13("§cВерстак не открылся.");
         }
      }
   }

   private void invoke5() {
      CraftingScreen craftingScreen2 = this.resolve();
      if (craftingScreen2 == null) {
         this.autoCraftState = AutoCraft.AutoCraftState.FINDING_TABLE;
         this.dualTimer.invoke();
      } else {
         CraftingScreenHandler craftingScreenHandler2 = (CraftingScreenHandler)craftingScreen2.getScreenHandler();

         for (int intValue = 1; intValue <= 9; intValue++) {
            if (craftingScreenHandler2.getSlot(intValue).hasStack()) {
               int intValue2 = intValue;
               this.queue.add(() -> CLIENT.interactionManager.clickSlot(craftingScreenHandler2.syncId, intValue2, 0, SlotActionType.QUICK_MOVE, CLIENT.player));
            }
         }

         this.autoCraftState = AutoCraft.AutoCraftState.PLACING_RECIPE;
         this.dualTimer.invoke();
      }
   }

   private void invoke6() {
      CraftingScreen craftingScreen3 = this.resolve();
      if (craftingScreen3 == null) {
         this.autoCraftState = AutoCraft.AutoCraftState.FINDING_TABLE;
         this.dualTimer.invoke();
      } else {
         CraftingScreenHandler craftingScreenHandler3 = (CraftingScreenHandler)craftingScreen3.getScreenHandler();
         String text = this.resolve2(craftingScreenHandler3);
         if (!text.isBlank()) {
            this.invoke13("§cНе хватает предмета: §f" + this.resolve6(text));
         } else {
            for (int intValue3 = 0; intValue3 < 9; intValue3++) {
               String text2 = this.retsept.resolve(intValue3);
               if (!text2.isBlank()) {
                  int intValue4 = intValue3 + 1;
                  this.queue.add(() -> this.invoke11(craftingScreenHandler3, text2, intValue4));
               }
            }

            this.autoCraftState = AutoCraft.AutoCraftState.WAITING_RESULT;
            this.dualTimer.invoke();
         }
      }
   }

   private void invoke7() {
      CraftingScreen craftingScreen4 = this.resolve();
      if (craftingScreen4 == null) {
         this.autoCraftState = AutoCraft.AutoCraftState.FINDING_TABLE;
         this.dualTimer.invoke();
      } else if (this.dualTimer.check5(Math.max(150, this.compute4() * 2))) {
         if (!((CraftingScreenHandler)craftingScreen4.getScreenHandler()).getSlot(0).hasStack()) {
            this.invoke13("§cРецепт не даёт результат.");
         } else {
            ItemStack itemStack2 = ((CraftingScreenHandler)craftingScreen4.getScreenHandler()).getSlot(0).getStack().copy();
            int intValue5 = Math.max(1, itemStack2.getCount());
            int intValue6 = Math.max(1, this.compute5() - this.intValue);
            int intValue7 = Math.max(1, (intValue6 + intValue5 - 1) / intValue5);
            this.intValue3 = Math.max(1, Math.min(intValue7, this.compute2((CraftingScreenHandler)craftingScreen4.getScreenHandler())));
            this.intValue2 = this.intValue3 * intValue5;
            int intValue8 = this.intValue3 - 1;
            if (intValue8 > 0) {
               this.invoke10((CraftingScreenHandler)craftingScreen4.getScreenHandler(), intValue8);
            }

            this.autoCraftState = AutoCraft.AutoCraftState.TAKING_RESULT;
            this.dualTimer.invoke();
         }
      }
   }

   private void invoke8() {
      CraftingScreen craftingScreen5 = this.resolve();
      if (craftingScreen5 == null) {
         this.autoCraftState = AutoCraft.AutoCraftState.FINDING_TABLE;
         this.dualTimer.invoke();
      } else if (this.dualTimer.check5(this.compute4())) {
         ItemStack itemStack3 = ((CraftingScreenHandler)craftingScreen5.getScreenHandler()).getSlot(0).getStack().copy();
         int intValue9 = Math.max(1, itemStack3.getCount());
         CLIENT.interactionManager.clickSlot(((CraftingScreenHandler)craftingScreen5.getScreenHandler()).syncId, 0, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
         this.intValue = this.intValue + Math.max(intValue9, this.intValue2);
         ChatUtil.sendClientMessage("§8[§6AutoCraft§8] §aСкрафтил: §f" + Math.min(this.intValue, this.compute5()) + "/" + this.compute5());
         this.intValue2 = 0;
         this.intValue3 = 0;
         this.autoCraftState = AutoCraft.AutoCraftState.CLOSING;
         this.dualTimer.invoke();
      }
   }

   private void invoke9() {
      if (this.dualTimer.check5(this.compute4())) {
         if (this.intValue >= this.compute5()) {
            if (CLIENT.player != null) {
               CLIENT.player.closeHandledScreen();
            }

            this.craftingScreen = null;
            ChatUtil.sendClientMessage("§8[§6AutoCraft§8] §aГотово.");
            this.setEnabled(false);
         } else {
            this.autoCraftState = AutoCraft.AutoCraftState.CLEARING_GRID;
            this.dualTimer.invoke();
         }
      }
   }

   private boolean check2() {
      if (this.queue.isEmpty()) {
         return false;
      } else if (!this.dualTimer.check5(this.compute4())) {
         return true;
      } else {
         this.queue.poll().run();
         this.dualTimer.invoke();
         return true;
      }
   }

   private CraftingScreen resolve() {
      CraftingScreen craftingScreen6 = ScreenUtil.resolve(CLIENT, this.craftingScreen, CraftingScreen.class);
      if (craftingScreen6 == null) {
         this.craftingScreen = null;
      }

      return craftingScreen6;
   }

   private String resolve2(CraftingScreenHandler craftingScreenHandler) {
      HashMap hashMap = new HashMap();

      for (String text3 : this.retsept.resolve2()) {
         if (text3 != null && !text3.isBlank()) {
            hashMap.put(text3, (Integer)hashMap.getOrDefault(text3, 0) + 1);
         }
      }

      for (Entry entry : ((HashMap<String, Integer>)hashMap).entrySet()) {
         int intValue10 = this.compute(craftingScreenHandler, (String)entry.getKey());
         if (intValue10 < (Integer)entry.getValue()) {
            return (String)entry.getKey();
         }
      }

      return "";
   }

   private int compute(CraftingScreenHandler craftingScreenHandler, String string) {
      int intValue11 = 0;

      for (int intValue12 = 10; intValue12 < craftingScreenHandler.slots.size(); intValue12++) {
         ItemStack itemStack4 = craftingScreenHandler.getSlot(intValue12).getStack();
         if (this.check3(itemStack4, string)) {
            intValue11 += itemStack4.getCount();
         }
      }

      return intValue11;
   }

   private int compute2(CraftingScreenHandler craftingScreenHandler) {
      HashMap hashMap2 = new HashMap();
      int intValue13 = 64;

      for (String text4 : this.retsept.resolve2()) {
         if (text4 != null && !text4.isBlank()) {
            hashMap2.put(text4, (Integer)hashMap2.getOrDefault(text4, 0) + 1);
            ItemStack itemStack5 = this.resolve3(text4);
            if (!itemStack5.isEmpty()) {
               intValue13 = Math.min(intValue13, itemStack5.getMaxCount());
            }
         }
      }

      int intValue14 = intValue13;

      for (Entry entry2 : ((HashMap<String, Integer>)hashMap2).entrySet()) {
         int intValue15 = (Integer)entry2.getValue();
         int intValue16 = this.compute(craftingScreenHandler, (String)entry2.getKey()) + intValue15;
         intValue14 = Math.min(intValue14, intValue16 / (Integer)entry2.getValue());
      }

      return Math.max(1, intValue14);
   }

   private void invoke10(CraftingScreenHandler craftingScreenHandler, int i) {
      for (int intValue17 = 0; intValue17 < 9; intValue17++) {
         String text5 = this.retsept.resolve(intValue17);
         if (!text5.isBlank()) {
            int intValue18 = intValue17 + 1;
            this.queue.add(() -> this.invoke12(craftingScreenHandler, text5, intValue18, i));
         }
      }
   }

   private void invoke11(CraftingScreenHandler craftingScreenHandler, String string, int i) {
      int intValue19 = this.compute3(craftingScreenHandler, string);
      if (intValue19 == -1) {
         this.text = string;
      } else {
         CLIENT.interactionManager.clickSlot(craftingScreenHandler.syncId, intValue19, 0, SlotActionType.PICKUP, CLIENT.player);
         CLIENT.interactionManager.clickSlot(craftingScreenHandler.syncId, i, 1, SlotActionType.PICKUP, CLIENT.player);
         CLIENT.interactionManager.clickSlot(craftingScreenHandler.syncId, intValue19, 0, SlotActionType.PICKUP, CLIENT.player);
      }
   }

   private void invoke12(CraftingScreenHandler craftingScreenHandler, String string, int i, int j) {
      int intValue20 = j;

      while (intValue20 > 0) {
         int intValue21 = this.compute3(craftingScreenHandler, string);
         if (intValue21 == -1) {
            this.text = string;
            return;
         }

         CLIENT.interactionManager.clickSlot(craftingScreenHandler.syncId, intValue21, 0, SlotActionType.PICKUP, CLIENT.player);
         int intValue22 = intValue20;
         if (craftingScreenHandler.getCursorStack().isEmpty()) {
            this.text = string;
            return;
         }

         while (intValue20 > 0 && !craftingScreenHandler.getCursorStack().isEmpty()) {
            CLIENT.interactionManager.clickSlot(craftingScreenHandler.syncId, i, 1, SlotActionType.PICKUP, CLIENT.player);
            intValue20--;
         }

         if (intValue20 == intValue22) {
            this.text = string;
            return;
         }

         if (!craftingScreenHandler.getCursorStack().isEmpty()) {
            CLIENT.interactionManager.clickSlot(craftingScreenHandler.syncId, intValue21, 0, SlotActionType.PICKUP, CLIENT.player);
         }
      }
   }

   private int compute3(CraftingScreenHandler craftingScreenHandler, String string) {
      for (int intValue23 = 10; intValue23 < craftingScreenHandler.slots.size(); intValue23++) {
         Slot slot = craftingScreenHandler.getSlot(intValue23);
         if (slot.hasStack() && this.check3(slot.getStack(), string)) {
            return intValue23;
         }
      }

      return -1;
   }

   private boolean check3(ItemStack itemStack, String string) {
      if (itemStack != null && !itemStack.isEmpty() && string != null && !string.isBlank()) {
         Identifier identifier = Registries.ITEM.getId(itemStack.getItem());
         return identifier != null && identifier.toString().equals(string);
      } else {
         return false;
      }
   }

   private ItemStack resolve3(String string) {
      Identifier identifier2 = Identifier.tryParse(string == null ? "" : string);
      if (identifier2 == null) {
         return ItemStack.EMPTY;
      } else {
         Item item = (Item)Registries.ITEM.get(identifier2);
         return item == Items.AIR ? ItemStack.EMPTY : item.getDefaultStack();
      }
   }

   private BlockPos resolve4() {
      BlockPos blockPos2 = CLIENT.player.getBlockPos();
      BlockPos blockPos3 = null;
      double doubleValue2 = Double.MAX_VALUE;
      byte byteValue = 16;

      for (BlockPos blockPos4 : BlockPos.iterate(blockPos2.add(-byteValue, -5, -byteValue), blockPos2.add(byteValue, 5, byteValue))) {
         if (this.check4(blockPos4)) {
            double doubleValue3 = blockPos2.getSquaredDistance(blockPos4);
            if (doubleValue3 < doubleValue2) {
               doubleValue2 = doubleValue3;
               blockPos3 = blockPos4.toImmutable();
            }
         }
      }

      return blockPos3;
   }

   private boolean check4(BlockPos blockPos) {
      return blockPos != null && CLIENT.world != null && CLIENT.world.getBlockState(blockPos).isOf(Blocks.CRAFTING_TABLE);
   }

   private Rotation resolve5(Vec3d vec3d) {
      Vec3d vec3d2 = CLIENT.player.getEyePos();
      double doubleValue4 = vec3d.x - vec3d2.x;
      double doubleValue5 = vec3d.y - vec3d2.y;
      double doubleValue6 = vec3d.z - vec3d2.z;
      float floatValue = (float)Math.toDegrees(Math.atan2(doubleValue6, doubleValue4)) - 90.0F;
      float floatValue2 = (float)(-Math.toDegrees(Math.atan2(doubleValue5, Math.sqrt(doubleValue4 * doubleValue4 + doubleValue6 * doubleValue6))));
      return new Rotation(floatValue, floatValue2);
   }

   private int compute4() {
      return Math.max(20, (int)this.zaderzhka.getValue());
   }

   private int compute5() {
      String text6 = this.kolVoPredmetov.getValue().trim();
      if (text6.isEmpty()) {
         return 0;
      } else {
         try {
            return Math.max(0, Math.min(999999, Integer.parseInt(text6)));
         } catch (NumberFormatException numberFormatException) {
            return 0;
         }
      }
   }

   private String resolve6(String string) {
      Identifier identifier3 = Identifier.tryParse(string);
      if (identifier3 == null) {
         return string;
      } else {
         Item item2 = (Item)Registries.ITEM.get(identifier3);
         return item2 == Items.AIR ? string : item2.getName().getString();
      }
   }

   private void invoke13(String string) {
      ChatUtil.sendClientMessage("§8[§6AutoCraft§8] " + string);
      this.setEnabled(false);
   }

   static enum AutoCraftState {
      IDLE,
      FINDING_TABLE,
      GOING_TO_TABLE,
      AIMING_TABLE,
      OPENING_TABLE,
      CLEARING_GRID,
      PLACING_RECIPE,
      WAITING_RESULT,
      TAKING_RESULT,
      CLOSING;
   }
}
