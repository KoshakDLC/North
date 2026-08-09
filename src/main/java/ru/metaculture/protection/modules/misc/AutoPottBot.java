package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Predicate;
import net.minecraft.block.entity.BrewingStandBlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.fluid.FluidState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.potion.Potion;
import net.minecraft.potion.Potions;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.screen.BrewingStandScreenHandler;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleAccess(
   usernames = {"lichoday"}
)
@ModuleRegister(
   name = "AutoPottBot",
   category = Category.Misc,
   description = ""
)
public class AutoPottBot extends Module {
   private static final long TIMESTAMP = 20000L;
   private static final long TIMESTAMP_2 = 1500L;
   private static final long TIMESTAMP_3 = 4000L;
   private static final long TIMESTAMP_4 = 1600L;
   private static final long TIMESTAMP_5 = 8000L;
   private static final long TIMESTAMP_6 = 15000L;
   private static final int INT_VALUE = 3;
   private static final int INT_VALUE_2 = 4;
   private static final int INT_VALUE_3 = 5;
   private static final int INT_VALUE_4 = 3;
   private static final int INT_VALUE_5 = 3;
   private static final int INT_VALUE_6 = 5;
   public static volatile boolean flag;
   public static volatile String text = "—";
   public static volatile int intValue;
   public static volatile int intValue2;
   public static volatile int intValue3;
   public static volatile int intValue4;
   public static volatile int intValue5;
   public static volatile int intValue6;
   public static volatile int[] ints = new int[7];
   public static volatile List<AutoPottBot.AutoPottBotDisplayEntry> items = List.of();
   public static volatile List<String> items2 = List.of();
   private final BooleanSetting zeleSily = new BooleanSetting("Зелье силы", true);
   private final BooleanSetting zeleSkorosti = new BooleanSetting("Зелье скорости", false);
   private final BooleanSetting zeleOgnestoykosti = new BooleanSetting("Зелье огнестойкости", false);
   private final GroupSetting varit = new GroupSetting("Варить", this.zeleSily, this.zeleSkorosti, this.zeleOgnestoykosti);
   private final NumberSetting zaderzhkaKlikov = new NumberSetting("Задержка кликов", 120.0F, 30.0F, 600.0F, 10.0F, false);
   private final NumberSetting radiusVarok = new NumberSetting("Радиус варок", 4.5F, 2.0F, 6.0F, 0.5F, false);
   private final BooleanSetting napolnyatButylki = new BooleanSetting("Наполнять бутылки", true);
   private final NumberSetting buferVody = new NumberSetting("Буфер воды", 12.0F, 3.0F, 24.0F, 1.0F, false);
   private final BooleanSetting skladyvatVSunduk = new BooleanSetting("Складывать в сундук", true);
   private final DualTimer dualTimer = new DualTimer();
   private final DualTimer dualTimer2 = new DualTimer();
   private final Map<BlockPos, AutoPottBot.AutoPottBotState5> valuesByKey = new LinkedHashMap<>();
   private final Map<String, Long> valuesByKey2 = new HashMap<>();
   private AutoPottBot.AutoPottBotState2 autoPottBotState2 = AutoPottBot.AutoPottBotState2.SCAN;
   private BlockPos blockPos;
   private BlockPos blockPos2;
   private BlockPos blockPos3;
   private int intValue7;
   private int intValue8;
   private int intValue9;
   private long timestamp;
   private int intValue10;

   public AutoPottBot() {
      this.addSettings(new Setting[]{this.varit, this.zaderzhkaKlikov, this.radiusVarok, this.napolnyatButylki, this.buferVody, this.skladyvatVSunduk});
   }

   @Override
   public void onEnable() {
      this.invoke();
      flag = true;
      super.onEnable();
   }

   @Override
   public void onDisable() {
      flag = false;
      if (CLIENT.player != null
         && (
            CLIENT.player.currentScreenHandler instanceof BrewingStandScreenHandler
               || CLIENT.player.currentScreenHandler instanceof GenericContainerScreenHandler
         )) {
         CLIENT.player.closeHandledScreen();
      }

      this.invoke();
      super.onDisable();
   }

   private void invoke() {
      this.autoPottBotState2 = AutoPottBot.AutoPottBotState2.SCAN;
      this.blockPos = null;
      this.blockPos2 = null;
      this.blockPos3 = null;
      this.timestamp = 0L;
      this.intValue8 = 0;
      this.intValue10 = 0;
      this.valuesByKey.clear();
      this.valuesByKey2.clear();
      this.dualTimer.invoke();
      this.dualTimer2.invoke();
      items = List.of();
      items2 = List.of();
   }

   @EventHandler
   public void onClientTick(ClientTickEvent clientTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null && CLIENT.interactionManager != null) {
         this.invoke2();
         switch (this.autoPottBotState2) {
            case SCAN:
               this.invoke3();
               break;
            case OPENING:
               this.invoke4();
               break;
            case SERVICING:
               this.invoke5();
               break;
            case CLOSING:
               this.invoke6();
               break;
            case FILL_WATER:
               this.invoke7();
               break;
            case DEPOSIT_OPEN:
               this.invoke10();
               break;
            case DEPOSIT_MOVE:
               this.invoke11();
         }

         this.invoke15();
      }
   }

   private void invoke2() {
      long longValue = System.currentTimeMillis();
      int intValue = (int)Math.ceil(this.radiusVarok.getValue() + 4.0);
      BlockPos blockPos2 = CLIENT.player.getBlockPos();

      for (int intValue2 = -intValue; intValue2 <= intValue; intValue2++) {
         for (int intValue3 = -intValue; intValue3 <= intValue; intValue3++) {
            for (int intValue4 = -intValue; intValue4 <= intValue; intValue4++) {
               BlockPos blockPos3 = blockPos2.add(intValue2, intValue3, intValue4);
               if (CLIENT.world.getBlockEntity(blockPos3) instanceof BrewingStandBlockEntity) {
                  AutoPottBot.AutoPottBotState5 autoPottBotState5 = this.valuesByKey.get(blockPos3);
                  if (autoPottBotState5 == null) {
                     this.valuesByKey.put(blockPos3.toImmutable(), new AutoPottBot.AutoPottBotState5(blockPos3.toImmutable()));
                  } else {
                     autoPottBotState5.timestamp4 = longValue;
                  }
               }
            }
         }
      }

      Iterator iterator = this.valuesByKey.entrySet().iterator();

      while (iterator.hasNext()) {
         AutoPottBot.AutoPottBotState5 autoPottBotState52 = (AutoPottBot.AutoPottBotState5)((Entry)iterator.next()).getValue();
         if (CLIENT.world.getBlockEntity(autoPottBotState52.blockPos) instanceof BrewingStandBlockEntity) {
            autoPottBotState52.timestamp4 = longValue;
         } else if (longValue - autoPottBotState52.timestamp4 > 8000L) {
            iterator.remove();
         }
      }
   }

   private void invoke3() {
      if (!(CLIENT.player.currentScreenHandler instanceof BrewingStandScreenHandler)
         && !(CLIENT.player.currentScreenHandler instanceof GenericContainerScreenHandler)) {
         if (this.skladyvatVSunduk.isEnabled() && this.compute6() <= 3 && this.compute7() > 0) {
            BlockPos blockPos4 = this.resolve10();
            if (blockPos4 != null) {
               this.blockPos2 = blockPos4;
               this.autoPottBotState2 = AutoPottBot.AutoPottBotState2.DEPOSIT_OPEN;
               this.dualTimer2.invoke();
               this.dualTimer.invoke();
               return;
            }
         }

         if (this.napolnyatButylki.isEnabled()
            && this.compute8(Items.GLASS_BOTTLE) > 0
            && this.compute9() < 3
            && System.currentTimeMillis() >= this.timestamp) {
            int intValue5 = this.compute2();
            int intValue6 = this.compute9();
            int intValue7 = Math.min((int)this.buferVody.getValue(), Math.max(3, intValue5));
            int intValue8 = Math.max(0, this.compute6() - 5);
            int intValue9 = Math.min(intValue7, intValue6 + intValue8);
            if (intValue5 > 0 && intValue9 > intValue6) {
               BlockPos blockPos5 = this.resolve3();
               if (blockPos5 != null) {
                  if (this.check2()) {
                     this.blockPos3 = blockPos5;
                     this.intValue7 = intValue9;
                     this.intValue8 = 0;
                     this.intValue9 = intValue6;
                     this.autoPottBotState2 = AutoPottBot.AutoPottBotState2.FILL_WATER;
                     this.dualTimer.invoke();
                     return;
                  }

                  this.invoke9("Бутылочки в хотбар");
               }
            }
         }

         AutoPottBot.AutoPottBotState5 autoPottBotState53 = this.resolve();
         if (autoPottBotState53 != null) {
            this.blockPos = autoPottBotState53.blockPos;
            this.autoPottBotState2 = AutoPottBot.AutoPottBotState2.OPENING;
            this.dualTimer2.invoke();
            this.dualTimer.invoke();
         }
      } else {
         CLIENT.player.closeHandledScreen();
      }
   }

   private AutoPottBot.AutoPottBotState5 resolve() {
      long longValue2 = System.currentTimeMillis();
      Vec3d vec3d = CLIENT.player.getEyePos();
      double doubleValue = this.radiusVarok.getValue() * this.radiusVarok.getValue();
      boolean flag = this.check4();
      AutoPottBot.AutoPottBotState5 autoPottBotState54 = null;
      int intValue10 = -1;
      double doubleValue2 = Double.MAX_VALUE;

      for (AutoPottBot.AutoPottBotState5 autoPottBotState55 : this.valuesByKey.values()) {
         double doubleValue3 = Vec3d.ofCenter(autoPottBotState55.blockPos).squaredDistanceTo(vec3d);
         if (!(doubleValue3 > doubleValue) && longValue2 >= autoPottBotState55.timestamp3 && (!autoPottBotState55.flag2 || longValue2 >= autoPottBotState55.timestamp2)) {
            int intValue11 = this.compute(autoPottBotState55, flag);
            if (intValue11 > 0 && (intValue11 > intValue10 || intValue11 == intValue10 && doubleValue3 < doubleValue2)) {
               autoPottBotState54 = autoPottBotState55;
               intValue10 = intValue11;
               doubleValue2 = doubleValue3;
            }
         }
      }

      return autoPottBotState54;
   }

   private int compute(AutoPottBot.AutoPottBotState5 autoPottBotState56, boolean bl) {
      return switch (autoPottBotState56.autoPottBotState4) {
         case UNKNOWN -> 2;
         case EMPTY -> bl ? 1 : 0;
         case WATER, AWKWARD, BASE -> 3;
         case FINAL -> 4;
         default -> 0;
      };
   }

   private void invoke4() {
      if (CLIENT.player.currentScreenHandler instanceof BrewingStandScreenHandler) {
         this.autoPottBotState2 = AutoPottBot.AutoPottBotState2.SERVICING;
         this.dualTimer.invoke();
      } else if (this.blockPos == null || !(CLIENT.world.getBlockEntity(this.blockPos) instanceof BrewingStandBlockEntity)) {
         this.autoPottBotState2 = AutoPottBot.AutoPottBotState2.SCAN;
      } else if (this.dualTimer2.check9(1600L)) {
         AutoPottBot.AutoPottBotState5 autoPottBotState57 = this.valuesByKey.get(this.blockPos);
         if (autoPottBotState57 != null) {
            autoPottBotState57.timestamp3 = System.currentTimeMillis() + 4500L;
         }

         this.autoPottBotState2 = AutoPottBot.AutoPottBotState2.SCAN;
      } else {
         if (this.dualTimer.check9(450L)) {
            this.invoke12(this.blockPos);
            this.dualTimer.invoke();
         }
      }
   }

   private void invoke5() {
      if (CLIENT.player.currentScreenHandler instanceof BrewingStandScreenHandler brewingStandScreenHandler2) {
         AutoPottBot.AutoPottBotState5 autoPottBotState58 = this.valuesByKey.get(this.blockPos);
         if (autoPottBotState58 == null) {
            this.autoPottBotState2 = AutoPottBot.AutoPottBotState2.CLOSING;
         } else if (this.dualTimer.check9((long)this.zaderzhkaKlikov.getValue())) {
            this.dualTimer.invoke();
            AutoPottBot.AutoPottBotState autoPottBotState = this.resolve2(brewingStandScreenHandler2, autoPottBotState58);
            switch (autoPottBotState) {
               case CONTINUE:
               default:
                  break;
               case BREW_STARTED:
                  long longValue3 = System.currentTimeMillis();
                  autoPottBotState58.flag2 = true;
                  autoPottBotState58.timestamp = longValue3;
                  autoPottBotState58.timestamp2 = longValue3 + 20000L;
                  autoPottBotState58.timestamp3 = autoPottBotState58.timestamp2;
                  this.autoPottBotState2 = AutoPottBot.AutoPottBotState2.CLOSING;
                  break;
               case DONE:
                  long longValue4 = autoPottBotState58.autoPottBotState4 == AutoPottBot.AutoPottBotState4.EMPTY ? 4000L : 1500L;
                  autoPottBotState58.timestamp3 = System.currentTimeMillis() + longValue4;
                  this.autoPottBotState2 = AutoPottBot.AutoPottBotState2.CLOSING;
            }
         }
      } else {
         this.autoPottBotState2 = AutoPottBot.AutoPottBotState2.SCAN;
      }
   }

   private AutoPottBot.AutoPottBotState resolve2(BrewingStandScreenHandler brewingStandScreenHandler, AutoPottBot.AutoPottBotState5 autoPottBotState59) {
      boolean flag2 = !brewingStandScreenHandler.getSlot(3).getStack().isEmpty();
      int intValue12 = this.compute3(brewingStandScreenHandler, autoPottBotState59);
      boolean flag3 = intValue12 >= 0;
      autoPottBotState59.flag = flag3;
      autoPottBotState59.intValue = flag3 ? Math.min(3, intValue12 + (flag2 ? 1 : 0)) : 0;
      autoPottBotState59.autoPottBotState4 = this.resolve8(intValue12, flag2);
      if (flag2) {
         autoPottBotState59.flag2 = true;
         if (autoPottBotState59.timestamp2 == 0L) {
            autoPottBotState59.timestamp = System.currentTimeMillis();
            autoPottBotState59.timestamp2 = autoPottBotState59.timestamp + 20000L;
         }

         return AutoPottBot.AutoPottBotState.DONE;
      } else {
         autoPottBotState59.flag2 = false;
         if (autoPottBotState59.autoPottBotState4 == AutoPottBot.AutoPottBotState4.OTHER) {
            return AutoPottBot.AutoPottBotState.DONE;
         } else if (autoPottBotState59.autoPottBotState4 == AutoPottBot.AutoPottBotState4.FINAL) {
            if (this.compute6() <= 0) {
               return AutoPottBot.AutoPottBotState.DONE;
            } else {
               for (int intValue13 = 0; intValue13 < 3; intValue13++) {
                  if (!brewingStandScreenHandler.getSlot(intValue13).getStack().isEmpty()) {
                     this.invoke13(intValue13);
                  }
               }

               autoPottBotState59.flag = false;
               autoPottBotState59.intValue = 0;
               autoPottBotState59.autoPottBotState4 = AutoPottBot.AutoPottBotState4.EMPTY;
               return AutoPottBot.AutoPottBotState.CONTINUE;
            }
         } else {
            if (!flag3) {
               AutoPottBot.AutoPottBotState3 autoPottBotState3 = this.resolve7();
               if (autoPottBotState3 == null) {
                  return AutoPottBot.AutoPottBotState.DONE;
               }

               autoPottBotState59.autoPottBotState3 = autoPottBotState3;
            }

            if (brewingStandScreenHandler.getFuel() <= 0 && brewingStandScreenHandler.getSlot(4).getStack().isEmpty() && this.check6(Items.BLAZE_POWDER, 4)) {
               return AutoPottBot.AutoPottBotState.CONTINUE;
            } else {
               if (this.check5(brewingStandScreenHandler)) {
                  int intValue14 = this.compute5((Predicate<ItemStack>)(itemStack -> this.check8(itemStack, Potions.WATER)));
                  if (intValue14 != -1) {
                     this.invoke13(intValue14);
                     return AutoPottBot.AutoPottBotState.CONTINUE;
                  }

                  if (this.compute4(brewingStandScreenHandler) == 0) {
                     return AutoPottBot.AutoPottBotState.DONE;
                  }
               }

               AutoPottBot.AutoPottBotState3 autoPottBotState32 = autoPottBotState59.autoPottBotState3 != null ? autoPottBotState59.autoPottBotState3 : this.resolve7();
               if (autoPottBotState32 == null) {
                  return AutoPottBot.AutoPottBotState.DONE;
               } else {
                  autoPottBotState59.autoPottBotState3 = autoPottBotState32;

                  Item item3 = switch (autoPottBotState59.autoPottBotState4) {
                     case WATER -> Items.NETHER_WART;
                     case AWKWARD -> autoPottBotState32.item;
                     case BASE -> autoPottBotState32.item2;
                     default -> null;
                  };
                  if (item3 == null) {
                     return AutoPottBot.AutoPottBotState.DONE;
                  } else if (!this.check7(item3)) {
                     return AutoPottBot.AutoPottBotState.DONE;
                  } else {
                     return this.check6(item3, 3) ? AutoPottBot.AutoPottBotState.BREW_STARTED : AutoPottBot.AutoPottBotState.DONE;
                  }
               }
            }
         }
      }
   }

   private void invoke6() {
      CLIENT.player.closeHandledScreen();
      this.blockPos = null;
      this.autoPottBotState2 = AutoPottBot.AutoPottBotState2.SCAN;
   }

   private void invoke7() {
      if (!(CLIENT.player.currentScreenHandler instanceof BrewingStandScreenHandler)
         && !(CLIENT.player.currentScreenHandler instanceof GenericContainerScreenHandler)) {
         if (this.compute9() < this.intValue7 && this.compute8(Items.GLASS_BOTTLE) > 0) {
            if (this.intValue8 > this.intValue7 * 2 + 20) {
               if (this.compute9() <= this.intValue9) {
                  this.timestamp = System.currentTimeMillis() + 6000L;
                  this.invoke9("Источник воды недосягаем");
               }

               this.autoPottBotState2 = AutoPottBot.AutoPottBotState2.SCAN;
            } else {
               if (this.blockPos3 == null || !this.check(this.blockPos3)) {
                  this.blockPos3 = this.resolve3();
                  if (this.blockPos3 == null) {
                     this.autoPottBotState2 = AutoPottBot.AutoPottBotState2.SCAN;
                     return;
                  }
               }

               if (!this.check3()) {
                  this.autoPottBotState2 = AutoPottBot.AutoPottBotState2.SCAN;
               } else if (this.dualTimer.check9((long)this.zaderzhkaKlikov.getValue())) {
                  this.dualTimer.invoke();
                  this.invoke8(this.blockPos3);
                  CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
                  this.intValue8++;
               }
            }
         } else {
            this.autoPottBotState2 = AutoPottBot.AutoPottBotState2.SCAN;
         }
      } else {
         CLIENT.player.closeHandledScreen();
      }
   }

   private int compute2() {
      int intValue15 = 0;

      for (AutoPottBot.AutoPottBotState5 autoPottBotState510 : this.valuesByKey.values()) {
         if (autoPottBotState510.autoPottBotState4 == AutoPottBot.AutoPottBotState4.EMPTY || autoPottBotState510.autoPottBotState4 == AutoPottBot.AutoPottBotState4.UNKNOWN) {
            intValue15++;
         }
      }

      return intValue15 * 3;
   }

   private BlockPos resolve3() {
      BlockPos blockPos6 = CLIENT.player.getBlockPos();
      int intValue16 = (int)Math.ceil(this.radiusVarok.getValue());
      double doubleValue4 = this.radiusVarok.getValue() * this.radiusVarok.getValue();
      Vec3d vec3d2 = CLIENT.player.getEyePos();
      BlockPos blockPos7 = null;
      double doubleValue5 = Double.MAX_VALUE;

      for (int intValue17 = -intValue16; intValue17 <= intValue16; intValue17++) {
         for (int intValue18 = -intValue16; intValue18 <= intValue16; intValue18++) {
            for (int intValue19 = -intValue16; intValue19 <= intValue16; intValue19++) {
               BlockPos blockPos8 = blockPos6.add(intValue17, intValue18, intValue19);
               if (this.check(blockPos8)) {
                  double doubleValue6 = Vec3d.ofCenter(blockPos8).squaredDistanceTo(vec3d2);
                  if (doubleValue6 <= doubleValue4 && doubleValue6 < doubleValue5) {
                     doubleValue5 = doubleValue6;
                     blockPos7 = blockPos8.toImmutable();
                  }
               }
            }
         }
      }

      return blockPos7;
   }

   private boolean check(BlockPos blockPos) {
      FluidState fluidState = CLIENT.world.getFluidState(blockPos);
      return fluidState.isStill() && fluidState.isIn(FluidTags.WATER);
   }

   private boolean check2() {
      for (int intValue20 = 0; intValue20 < 9; intValue20++) {
         if (CLIENT.player.getInventory().getStack(intValue20).getItem() == Items.GLASS_BOTTLE) {
            return true;
         }
      }

      return false;
   }

   private boolean check3() {
      for (int intValue21 = 0; intValue21 < 9; intValue21++) {
         if (CLIENT.player.getInventory().getStack(intValue21).getItem() == Items.GLASS_BOTTLE) {
            if (CLIENT.player.getInventory().getSelectedSlot() != intValue21) {
               CLIENT.player.getInventory().setSelectedSlot(intValue21);
            }

            return true;
         }
      }

      return false;
   }

   private void invoke8(BlockPos blockPos) {
      Vec3d vec3d3 = CLIENT.player.getEyePos();
      double doubleValue7 = blockPos.getX() + 0.5 - vec3d3.x;
      double doubleValue8 = blockPos.getY() + 0.5 - vec3d3.y;
      double doubleValue9 = blockPos.getZ() + 0.5 - vec3d3.z;
      double doubleValue10 = Math.sqrt(doubleValue7 * doubleValue7 + doubleValue9 * doubleValue9);
      float floatValue = (float)(Math.toDegrees(Math.atan2(doubleValue9, doubleValue7)) - 90.0);
      float floatValue2 = (float)(-Math.toDegrees(Math.atan2(doubleValue8, doubleValue10)));
      CLIENT.player.setYaw(floatValue);
      CLIENT.player.setPitch(Math.max(-90.0F, Math.min(90.0F, floatValue2)));
   }

   private void invoke9(String string) {
      long longValue5 = System.currentTimeMillis();
      Long longValue6 = this.valuesByKey2.get(string);
      if (longValue6 == null || longValue5 - longValue6 > 15000L) {
         this.valuesByKey2.put(string, longValue5);
         ChatUtil.sendClientMessage("§8[AutoPottBot] §c" + string);
      }
   }

   private void invoke10() {
      if (CLIENT.player.currentScreenHandler instanceof GenericContainerScreenHandler) {
         this.autoPottBotState2 = AutoPottBot.AutoPottBotState2.DEPOSIT_MOVE;
         this.dualTimer.invoke();
      } else if (this.blockPos2 == null || !(CLIENT.world.getBlockEntity(this.blockPos2) instanceof ChestBlockEntity)) {
         this.autoPottBotState2 = AutoPottBot.AutoPottBotState2.SCAN;
      } else if (this.dualTimer2.check9(1600L)) {
         this.autoPottBotState2 = AutoPottBot.AutoPottBotState2.SCAN;
      } else {
         if (this.dualTimer.check9(450L)) {
            this.invoke12(this.blockPos2);
            this.dualTimer.invoke();
         }
      }
   }

   private void invoke11() {
      if (CLIENT.player.currentScreenHandler instanceof GenericContainerScreenHandler genericContainerScreenHandler) {
         if (this.dualTimer.check9((long)this.zaderzhkaKlikov.getValue())) {
            this.dualTimer.invoke();
            int intValue22 = genericContainerScreenHandler.getRows() * 9;

            for (int intValue23 = intValue22; intValue23 < genericContainerScreenHandler.slots.size(); intValue23++) {
               if (this.check11(((Slot)genericContainerScreenHandler.slots.get(intValue23)).getStack())) {
                  this.invoke14(intValue23, 0, SlotActionType.QUICK_MOVE);
                  return;
               }
            }

            CLIENT.player.closeHandledScreen();
            this.blockPos2 = null;
            this.autoPottBotState2 = AutoPottBot.AutoPottBotState2.SCAN;
         }
      } else {
         this.autoPottBotState2 = AutoPottBot.AutoPottBotState2.SCAN;
      }
   }

   private List<AutoPottBot.AutoPottBotState3> resolve4() {
      ArrayList arrayList = new ArrayList(3);
      if (this.zeleSily.isEnabled()) {
         arrayList.add(AutoPottBot.AutoPottBotState3.STRENGTH);
      }

      if (this.zeleSkorosti.isEnabled()) {
         arrayList.add(AutoPottBot.AutoPottBotState3.SWIFTNESS);
      }

      if (this.zeleOgnestoykosti.isEnabled()) {
         arrayList.add(AutoPottBot.AutoPottBotState3.FIRE_RESISTANCE);
      }

      return arrayList;
   }

   private Map<Item, Integer> resolve5() {
      HashMap hashMap = new HashMap();

      for (AutoPottBot.AutoPottBotState5 autoPottBotState511 : this.valuesByKey.values()) {
         if (autoPottBotState511.flag && autoPottBotState511.autoPottBotState3 != null) {
            if (autoPottBotState511.intValue < 1) {
               invoke17(hashMap, Items.NETHER_WART, 1);
            }

            if (autoPottBotState511.intValue < 2) {
               invoke17(hashMap, autoPottBotState511.autoPottBotState3.item, 1);
            }

            if (autoPottBotState511.intValue < 3) {
               invoke17(hashMap, autoPottBotState511.autoPottBotState3.item2, 1);
            }
         }
      }

      return hashMap;
   }

   private List<AutoPottBot.AutoPottBotState3> resolve6() {
      Map valuesByKey = this.resolve5();
      int intValue24 = this.compute9();
      ArrayList arrayList2 = new ArrayList(3);

      for (AutoPottBot.AutoPottBotState3 autoPottBotState33 : this.resolve4()) {
         int intValue25 = this.compute8(Items.NETHER_WART) - (int)(Integer)valuesByKey.getOrDefault(Items.NETHER_WART, 0);
         int intValue26 = this.compute8(autoPottBotState33.item) - (int)(Integer)valuesByKey.getOrDefault(autoPottBotState33.item, 0);
         int intValue27 = this.compute8(autoPottBotState33.item2) - (int)(Integer)valuesByKey.getOrDefault(autoPottBotState33.item2, 0);
         if (intValue24 >= 1 && intValue25 >= 1 && intValue26 >= 1 && intValue27 >= 1) {
            arrayList2.add(autoPottBotState33);
         }
      }

      return arrayList2;
   }

   private boolean check4() {
      return !this.resolve6().isEmpty();
   }

   private AutoPottBot.AutoPottBotState3 resolve7() {
      List items = this.resolve6();
      if (items.isEmpty()) {
         return null;
      } else {
         AutoPottBot.AutoPottBotState3 autoPottBotState34 = (AutoPottBot.AutoPottBotState3)items.get(Math.floorMod(this.intValue10, items.size()));
         this.intValue10++;
         return autoPottBotState34;
      }
   }

   private int compute3(BrewingStandScreenHandler brewingStandScreenHandler, AutoPottBot.AutoPottBotState5 autoPottBotState512) {
      RegistryEntry registryEntry3 = null;
      int intValue28 = 0;

      for (int intValue29 = 0; intValue29 < 3; intValue29++) {
         ItemStack itemStack2 = brewingStandScreenHandler.getSlot(intValue29).getStack();
         if (itemStack2.getItem() == Items.POTION) {
            intValue28++;
            if (registryEntry3 == null) {
               registryEntry3 = this.resolve9(itemStack2);
            }
         }
      }

      if (intValue28 == 0 || registryEntry3 == null) {
         return -1;
      } else if (this.check10(registryEntry3, Potions.WATER)) {
         return 0;
      } else if (this.check10(registryEntry3, Potions.AWKWARD)) {
         return 1;
      } else {
         for (AutoPottBot.AutoPottBotState3 autoPottBotState35 : AutoPottBot.AutoPottBotState3.values()) {
            if (this.check10(registryEntry3, autoPottBotState35.registryEntry2)) {
               autoPottBotState512.autoPottBotState3 = autoPottBotState35;
               return 3;
            }

            if (this.check10(registryEntry3, autoPottBotState35.registryEntry)) {
               autoPottBotState512.autoPottBotState3 = autoPottBotState35;
               return 2;
            }
         }

         return -2;
      }
   }

   private AutoPottBot.AutoPottBotState4 resolve8(int i, boolean bl) {
      return switch (i) {
         case -1 -> AutoPottBot.AutoPottBotState4.EMPTY;
         case 0 -> AutoPottBot.AutoPottBotState4.WATER;
         case 1 -> AutoPottBot.AutoPottBotState4.AWKWARD;
         case 2 -> AutoPottBot.AutoPottBotState4.BASE;
         case 3 -> AutoPottBot.AutoPottBotState4.FINAL;
         default -> AutoPottBot.AutoPottBotState4.OTHER;
      };
   }

   private boolean check5(BrewingStandScreenHandler brewingStandScreenHandler) {
      for (int intValue30 = 0; intValue30 < 3; intValue30++) {
         if (brewingStandScreenHandler.getSlot(intValue30).getStack().isEmpty()) {
            return true;
         }
      }

      return false;
   }

   private int compute4(BrewingStandScreenHandler brewingStandScreenHandler) {
      int intValue31 = 0;

      for (int intValue32 = 0; intValue32 < 3; intValue32++) {
         if (!brewingStandScreenHandler.getSlot(intValue32).getStack().isEmpty()) {
            intValue31++;
         }
      }

      return intValue31;
   }

   private boolean check6(Item item, int i) {
      int intValue33 = this.compute5((Predicate<ItemStack>)(itemStack -> itemStack.getItem() == item));
      if (intValue33 == -1) {
         return false;
      } else {
         this.invoke14(intValue33, 0, SlotActionType.PICKUP);
         this.invoke14(i, 1, SlotActionType.PICKUP);
         this.invoke14(intValue33, 0, SlotActionType.PICKUP);
         return true;
      }
   }

   private int compute5(Predicate<ItemStack> predicate) {
      ScreenHandler screenHandler = CLIENT.player.currentScreenHandler;

      for (int intValue34 = 5; intValue34 < screenHandler.slots.size(); intValue34++) {
         ItemStack itemStack3 = ((Slot)screenHandler.slots.get(intValue34)).getStack();
         if (!itemStack3.isEmpty() && predicate.test(itemStack3)) {
            return intValue34;
         }
      }

      return -1;
   }

   private boolean check7(Item item) {
      return this.compute5((Predicate<ItemStack>)(itemStack -> itemStack.getItem() == item)) != -1;
   }

   private boolean check8(ItemStack itemStack, RegistryEntry<Potion> registryEntry) {
      if (itemStack.getItem() != Items.POTION) {
         return false;
      } else {
         RegistryEntry registryEntry4 = this.resolve9(itemStack);
         return registryEntry4 != null && this.check10(registryEntry4, registryEntry);
      }
   }

   private boolean check9(ItemStack itemStack) {
      return itemStack.getItem() == Items.POTION || itemStack.getItem() == Items.SPLASH_POTION || itemStack.getItem() == Items.LINGERING_POTION;
   }

   private RegistryEntry<Potion> resolve9(ItemStack itemStack) {
      PotionContentsComponent potionContentsComponent = (PotionContentsComponent)itemStack.get(DataComponentTypes.POTION_CONTENTS);
      return potionContentsComponent != null && !potionContentsComponent.potion().isEmpty() ? (RegistryEntry)potionContentsComponent.potion().get() : null;
   }

   private boolean check10(RegistryEntry<Potion> registryEntry, RegistryEntry<Potion> registryEntry2) {
      return registryEntry == registryEntry2
         || registryEntry.getKey().isPresent()
            && registryEntry2.getKey().isPresent()
            && ((RegistryKey)registryEntry.getKey().get()).equals(registryEntry2.getKey().get());
   }

   private int compute6() {
      int intValue35 = 0;

      for (int intValue36 = 0; intValue36 < CLIENT.player.getInventory().size(); intValue36++) {
         if (CLIENT.player.getInventory().getStack(intValue36).isEmpty()) {
            intValue35++;
         }
      }

      return intValue35;
   }

   private boolean check11(ItemStack itemStack) {
      if (!this.check9(itemStack)) {
         return false;
      } else {
         RegistryEntry registryEntry5 = this.resolve9(itemStack);
         return registryEntry5 == null ? false : !this.check10(registryEntry5, Potions.WATER) && !this.check10(registryEntry5, Potions.AWKWARD);
      }
   }

   private int compute7() {
      int intValue37 = 0;

      for (int intValue38 = 0; intValue38 < CLIENT.player.getInventory().size(); intValue38++) {
         if (this.check11(CLIENT.player.getInventory().getStack(intValue38))) {
            intValue37++;
         }
      }

      return intValue37;
   }

   private int compute8(Item item) {
      int intValue39 = 0;

      for (int intValue40 = 0; intValue40 < CLIENT.player.getInventory().size(); intValue40++) {
         ItemStack itemStack4 = CLIENT.player.getInventory().getStack(intValue40);
         if (itemStack4.getItem() == item) {
            intValue39 += itemStack4.getCount();
         }
      }

      return intValue39;
   }

   private int compute9() {
      int intValue41 = 0;

      for (int intValue42 = 0; intValue42 < CLIENT.player.getInventory().size(); intValue42++) {
         ItemStack itemStack5 = CLIENT.player.getInventory().getStack(intValue42);
         if (itemStack5.getItem() == Items.POTION) {
            RegistryEntry registryEntry6 = this.resolve9(itemStack5);
            if (registryEntry6 != null && this.check10(registryEntry6, Potions.WATER)) {
               intValue41 += itemStack5.getCount();
            }
         }
      }

      return intValue41;
   }

   private BlockPos resolve10() {
      BlockPos blockPos9 = CLIENT.player.getBlockPos();
      int intValue43 = (int)Math.ceil(this.radiusVarok.getValue() + 1.0);
      BlockPos blockPos10 = null;
      double doubleValue11 = Double.MAX_VALUE;
      Vec3d vec3d4 = CLIENT.player.getEyePos();

      for (int intValue44 = -intValue43; intValue44 <= intValue43; intValue44++) {
         for (int intValue45 = -intValue43; intValue45 <= intValue43; intValue45++) {
            for (int intValue46 = -intValue43; intValue46 <= intValue43; intValue46++) {
               BlockPos blockPos11 = blockPos9.add(intValue44, intValue45, intValue46);
               if (CLIENT.world.getBlockEntity(blockPos11) instanceof ChestBlockEntity) {
                  double doubleValue12 = Vec3d.ofCenter(blockPos11).squaredDistanceTo(vec3d4);
                  if (doubleValue12 < doubleValue11) {
                     doubleValue11 = doubleValue12;
                     blockPos10 = blockPos11.toImmutable();
                  }
               }
            }
         }
      }

      return blockPos10;
   }

   private void invoke12(BlockPos blockPos) {
      Vec3d vec3d5 = new Vec3d(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
      BlockHitResult blockHitResult = new BlockHitResult(vec3d5, Direction.UP, blockPos, false);
      CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, blockHitResult);
   }

   private void invoke13(int i) {
      this.invoke14(i, 0, SlotActionType.QUICK_MOVE);
   }

   private void invoke14(int i, int j, SlotActionType slotActionType) {
      CLIENT.interactionManager.clickSlot(CLIENT.player.currentScreenHandler.syncId, i, j, slotActionType, CLIENT.player);
   }

   private void invoke15() {
      long longValue7 = System.currentTimeMillis();
      int intValue47 = 0;
      int intValue48 = 0;
      int intValue49 = 0;
      ArrayList arrayList3 = new ArrayList(this.valuesByKey.size());

      for (AutoPottBot.AutoPottBotState5 autoPottBotState513 : this.valuesByKey.values()) {
         if (autoPottBotState513.autoPottBotState4 == AutoPottBot.AutoPottBotState4.FINAL) {
            intValue49++;
         } else if (autoPottBotState513.flag2 && longValue7 < autoPottBotState513.timestamp2) {
            intValue47++;
         } else if (autoPottBotState513.autoPottBotState4 != AutoPottBot.AutoPottBotState4.OTHER) {
            intValue48++;
         }

         arrayList3.add(new AutoPottBot.AutoPottBotDisplayEntry(autoPottBotState513.resolve(), autoPottBotState513.compute(), autoPottBotState513.measure(longValue7), autoPottBotState513.resolve2(longValue7)));
      }

      arrayList3.sort((object, object2) -> Float.compare(((AutoPottBot.AutoPottBotDisplayEntry)object2).progress(), ((AutoPottBot.AutoPottBotDisplayEntry)object).progress()));
      ints = new int[]{
         this.compute9(),
         this.compute8(Items.NETHER_WART),
         this.compute8(Items.BLAZE_POWDER),
         this.compute8(Items.GLOWSTONE_DUST),
         this.compute8(Items.SUGAR),
         this.compute8(Items.MAGMA_CREAM),
         this.compute8(Items.REDSTONE)
      };
      intValue6 = this.compute8(Items.GLASS_BOTTLE);
      intValue = this.valuesByKey.size();
      intValue2 = intValue47;
      intValue3 = intValue48;
      intValue4 = intValue49;
      intValue5 = this.compute10();
      items = arrayList3;
      text = this.autoPottBotState2.text;
      items2 = this.resolve11();
      this.invoke16();
   }

   private int compute10() {
      List items2 = this.resolve4();
      if (items2.isEmpty()) {
         return 0;
      } else {
         Map valuesByKey2 = this.resolve5();
         int intValue50 = this.compute9();
         int intValue51 = Math.max(0, this.compute8(Items.NETHER_WART) - (int)(Integer)valuesByKey2.getOrDefault(Items.NETHER_WART, 0));
         int intValue52 = 0;

         for (AutoPottBot.AutoPottBotState3 autoPottBotState36 : (List<AutoPottBot.AutoPottBotState3>)items2) {
            int intValue53 = this.compute8(autoPottBotState36.item) - (int)(Integer)valuesByKey2.getOrDefault(autoPottBotState36.item, 0);
            int intValue54 = this.compute8(autoPottBotState36.item2) - (int)(Integer)valuesByKey2.getOrDefault(autoPottBotState36.item2, 0);
            intValue52 += Math.max(0, Math.min(intValue53, intValue54));
         }

         intValue52 = Math.min(intValue52, intValue51);
         return Math.max(0, Math.min(intValue50, intValue52 * 3));
      }
   }

   private List<String> resolve11() {
      List items3 = this.resolve4();
      if (items3.isEmpty()) {
         return List.of("Не выбрано зелье");
      } else {
         Map valuesByKey3 = this.resolve5();
         ArrayList arrayList4 = new ArrayList();
         if (this.compute9() < 1) {
            boolean flag4 = this.napolnyatButylki.isEnabled() && this.compute8(Items.GLASS_BOTTLE) > 0;
            if (!flag4) {
               arrayList4.add(this.compute8(Items.GLASS_BOTTLE) > 0 ? "Источник воды" : "Вода / Бутылочки");
            }
         }

         if (this.compute8(Items.NETHER_WART) - (int)(Integer)valuesByKey3.getOrDefault(Items.NETHER_WART, 0) < 1) {
            arrayList4.add("Адский нарост");
         }

         for (AutoPottBot.AutoPottBotState3 autoPottBotState37 : (List<AutoPottBot.AutoPottBotState3>)items3) {
            if (this.compute8(autoPottBotState37.item) - (int)(Integer)valuesByKey3.getOrDefault(autoPottBotState37.item, 0) < 1) {
               invoke18(arrayList4, autoPottBotState37.item.getName().getString());
            }

            if (this.compute8(autoPottBotState37.item2) - (int)(Integer)valuesByKey3.getOrDefault(autoPottBotState37.item2, 0) < 1) {
               invoke18(arrayList4, autoPottBotState37.item2.getName().getString());
            }
         }

         if (this.compute8(Items.BLAZE_POWDER) <= 0) {
            invoke18(arrayList4, "Огненный порошок (топливо)");
         }

         return arrayList4;
      }
   }

   private void invoke16() {
      boolean flag5 = false;

      for (AutoPottBot.AutoPottBotState5 autoPottBotState514 : this.valuesByKey.values()) {
         if (autoPottBotState514.autoPottBotState4 == AutoPottBot.AutoPottBotState4.EMPTY || autoPottBotState514.autoPottBotState4 == AutoPottBot.AutoPottBotState4.UNKNOWN) {
            flag5 = true;
            break;
         }
      }

      if (flag5 && !items2.isEmpty()) {
         long longValue8 = System.currentTimeMillis();

         for (String text : items2) {
            Long longValue9 = this.valuesByKey2.get(text);
            if (longValue9 == null || longValue8 - longValue9 > 15000L) {
               this.valuesByKey2.put(text, longValue8);
               ChatUtil.sendClientMessage("§8[AutoPottBot] §cНе хватает: §f" + text);
            }
         }
      }
   }

   private static void invoke17(Map<Item, Integer> map, Item item, int i) {
      map.merge(item, i, Integer::sum);
   }

   private static void invoke18(List<String> list, String string) {
      if (!list.contains(string)) {
         list.add(string);
      }
   }

   static enum AutoPottBotState {
      CONTINUE,
      BREW_STARTED,
      DONE;
   }

   static enum AutoPottBotState2 {
      SCAN("Поиск"),
      OPENING("Открытие"),
      SERVICING("Загрузка"),
      CLOSING("Закрытие"),
      FILL_WATER("Налив воды"),
      DEPOSIT_OPEN("Сундук"),
      DEPOSIT_MOVE("Разгрузка");

      final String text;

      private AutoPottBotState2(String string2) {
         this.text = string2;
      }
   }

   static enum AutoPottBotState3 {
      STRENGTH("Сила", Items.BLAZE_POWDER, Items.GLOWSTONE_DUST, Potions.STRENGTH, Potions.STRONG_STRENGTH, 14042437),
      SWIFTNESS("Скорость", Items.SUGAR, Items.GLOWSTONE_DUST, Potions.SWIFTNESS, Potions.STRONG_SWIFTNESS, 5227511),
      FIRE_RESISTANCE("Огнестойкость", Items.MAGMA_CREAM, Items.REDSTONE, Potions.FIRE_RESISTANCE, Potions.LONG_FIRE_RESISTANCE, 16750592);

      final String text;
      final Item item;
      final Item item2;
      final RegistryEntry<Potion> registryEntry;
      final RegistryEntry<Potion> registryEntry2;
      final int intValue;

      private AutoPottBotState3(String string2, Item item, Item item2, RegistryEntry<Potion> registryEntry, RegistryEntry<Potion> registryEntry2, int j) {
         this.text = string2;
         this.item = item;
         this.item2 = item2;
         this.registryEntry = registryEntry;
         this.registryEntry2 = registryEntry2;
         this.intValue = j;
      }
   }

   static enum AutoPottBotState4 {
      UNKNOWN,
      EMPTY,
      WATER,
      AWKWARD,
      BASE,
      FINAL,
      OTHER;
   }

   static final class AutoPottBotState5 {
      final BlockPos blockPos;
      AutoPottBot.AutoPottBotState3 autoPottBotState3;
      AutoPottBot.AutoPottBotState4 autoPottBotState4 = AutoPottBot.AutoPottBotState4.UNKNOWN;
      boolean flag;
      int intValue;
      boolean flag2;
      long timestamp;
      long timestamp2;
      long timestamp3;
      long timestamp4 = System.currentTimeMillis();

      AutoPottBotState5(BlockPos blockPos) {
         this.blockPos = blockPos;
      }

      float measure(long l) {
         if (this.autoPottBotState4 == AutoPottBot.AutoPottBotState4.FINAL) {
            return 1.0F;
         } else if (this.flag2 && this.timestamp2 > this.timestamp) {
            float floatValue3 = (float)(l - this.timestamp) / (float)(this.timestamp2 - this.timestamp);
            return floatValue3 < 0.0F ? 0.0F : Math.min(floatValue3, 1.0F);
         } else {
            return 0.0F;
         }
      }

      int compute() {
         if (this.autoPottBotState4 == AutoPottBot.AutoPottBotState4.FINAL) {
            return 5954680;
         } else {
            return this.autoPottBotState3 != null ? this.autoPottBotState3.intValue : 9868960;
         }
      }

      String resolve() {
         return this.autoPottBotState3 != null ? this.autoPottBotState3.text : "—";
      }

      String resolve2(long l) {
         if (this.autoPottBotState4 == AutoPottBot.AutoPottBotState4.FINAL) {
            return this.resolve() + " ✓";
         } else if (this.flag2 && l < this.timestamp2) {
            return this.resolve() + " " + (int)(this.measure(l) * 100.0F) + "%";
         } else if (this.autoPottBotState4 == AutoPottBot.AutoPottBotState4.EMPTY) {
            return "Свободна";
         } else {
            return this.autoPottBotState4 == AutoPottBot.AutoPottBotState4.UNKNOWN ? "…" : this.resolve() + " готова";
         }
      }
   }

   public record AutoPottBotDisplayEntry(String name, int color, float progress, String label) {
   }
}
