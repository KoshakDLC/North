package ru.metaculture.protection;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalNear;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Queue;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.item.AxeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AutoWood",
   category = Category.Misc,
   description = "Автоматически выращивает и добывает дерево"
)
public class AutoWood extends Module {
   public ModeSetting chtoDobyvat = new ModeSetting("Что добывать", "Тёмный дуб", "Тропик дерево", "Тёмный дуб", "Еловое дерево");
   public ModeSetting chtoDelatSDerevom = new ModeSetting("Что делать с деревом", "Ничего", "Ничего", "Продавать на ауке", "Складывать в сундук");
   public NumberSetting porogDereva = new NumberSetting("Порог дерева", 64.0F, 64.0F, 640.0F, 64.0F, false)
      .setVisibilityCondition(() -> this.chtoDelatSDerevom.is("Ничего"));
   public BooleanSetting chinitTopor = new BooleanSetting("Чинить топор", false);
   public NumberSetting porogDlyaPochinkiTopora = new NumberSetting("Порог для починки топора", 300.0F, 100.0F, 2031.0F, 100.0F, false)
      .setVisibilityCondition(() -> !this.chinitTopor.isEnabled());
   public BooleanSetting popolnyatMukuIzSunduka = new BooleanSetting("Пополнять муку из сундука", true);
   public NumberSetting radiusPoiskaSundukov = new NumberSetting("Радиус поиска сундуков", 12.0F, 4.0F, 40.0F, 1.0F, false);
   private static final double DOUBLE_VALUE = 4.5;
   private static final int INT_VALUE = 6;
   private static final int INT_VALUE_2 = 64;
   private static final int INT_VALUE_3 = 128;
   private AutoWood.AutoWoodState autoWoodState = AutoWood.AutoWoodState.SETUP;
   private final List<List<BlockPos>> items = new ArrayList<>();
   private boolean flag = false;
   private BlockPos blockPos = null;
   private int intValue = 0;
   private int intValue2 = -1;
   private BlockPos blockPos2 = null;
   private int intValue3 = 0;
   private AutoWood.AutoWoodState4 autoWoodState4 = AutoWood.AutoWoodState4.NONE;
   private int intValue4 = 0;
   private boolean flag2 = false;
   private boolean flag3 = false;
   private IBaritone iBaritone;
   private AutoWood.AutoWoodState3 autoWoodState3 = AutoWood.AutoWoodState3.NONE;
   private AutoWood.AutoWoodState2 autoWoodState2 = AutoWood.AutoWoodState2.FIND_CHEST;
   private BlockPos blockPos3 = null;
   private boolean flag4 = false;
   private boolean flag5 = false;
   private int intValue5 = 0;
   private boolean flag6 = false;
   private int intValue6 = -1;
   private int intValue7 = -1;
   private float floatValue = 0.0F;
   private final DualTimer dualTimer = new DualTimer();
   private final DualTimer dualTimer2 = new DualTimer();
   private final DualTimer dualTimer3 = new DualTimer();
   private final DualTimer dualTimer4 = new DualTimer();
   private final DualTimer dualTimer5 = new DualTimer();
   private final DualTimer dualTimer6 = new DualTimer();
   private final DualTimer dualTimer7 = new DualTimer();
   private final DualTimer dualTimer8 = new DualTimer();
   private final DualTimer dualTimer9 = new DualTimer();
   private final DualTimer dualTimer10 = new DualTimer();
   private final Queue<Runnable> queue = new ArrayDeque<>();

   public AutoWood() {
      this.addSettings(
         new Setting[]{
            this.chtoDobyvat, this.chtoDelatSDerevom, this.porogDereva, this.chinitTopor, this.porogDlyaPochinkiTopora, this.popolnyatMukuIzSunduka, this.radiusPoiskaSundukov
         }
      );
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.iBaritone = BaritoneAPI.getProvider().getPrimaryBaritone();
      this.autoWoodState = AutoWood.AutoWoodState.SETUP;
      this.items.clear();
      this.flag = false;
      this.blockPos = null;
      this.intValue = 0;
      this.intValue2 = -1;
      this.blockPos2 = null;
      this.autoWoodState4 = AutoWood.AutoWoodState4.NONE;
      this.intValue4 = 0;
      this.flag2 = false;
      this.flag3 = false;
      this.invoke33();
      this.intValue3 = 0;
   }

   @Override
   public void onDisable() {
      if (CLIENT.player != null && this.flag6) {
         try {
            CLIENT.interactionManager
               .clickSlot(CLIENT.player.playerScreenHandler.syncId, 45, this.intValue6, SlotActionType.SWAP, CLIENT.player);
            if (this.intValue6 >= 0) {
               CLIENT.player.getInventory().setSelectedSlot(this.intValue6);
            }

            CLIENT.player.setPitch(this.floatValue);
         } catch (Exception exception) {
         }
      }

      this.flag6 = false;
      if (this.iBaritone != null) {
         this.iBaritone.getPathingBehavior().cancelEverything();
      }

      this.invoke33();
      this.autoWoodState4 = AutoWood.AutoWoodState4.NONE;
      RotationController.rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
      RotationController.intValue = 0;
      RotationController.rotation = null;
      FreeLookController.active = false;
      super.onDisable();
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null) {
         if (!PlayerHelper.check()) {
            if (this.autoWoodState3 != AutoWood.AutoWoodState3.NONE && CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen2) {
               if (this.autoWoodState3 == AutoWood.AutoWoodState3.DEPOSIT) {
                  this.invoke22(genericContainerScreen2);
               } else {
                  this.invoke21(genericContainerScreen2);
               }
            } else if (this.autoWoodState3 != AutoWood.AutoWoodState3.NONE) {
               this.invoke15();
            } else if (this.autoWoodState4 != AutoWood.AutoWoodState4.NONE) {
               this.invoke8();
            } else if (CLIENT.currentScreen == null) {
               if (!this.flag) {
                  this.invoke();
               } else if (this.chinitTopor.isEnabled() && this.compute2() != -1) {
                  this.autoWoodState3 = AutoWood.AutoWoodState3.REPAIR;
                  this.invoke14();
               } else if (this.check4()) {
                  this.autoWoodState4 = AutoWood.AutoWoodState4.EQUIP;
                  this.intValue4 = 0;
               } else if (this.check5()) {
                  this.autoWoodState3 = AutoWood.AutoWoodState3.DEPOSIT;
                  this.invoke14();
               } else {
                  this.intValue3++;
                  if (this.autoWoodState == AutoWood.AutoWoodState.WAIT_FELL || this.intValue3 >= 2) {
                     switch (this.autoWoodState) {
                        case FARM:
                           this.invoke3();
                           break;
                        case WAIT_FELL:
                           this.invoke7();
                           break;
                        default:
                           this.autoWoodState = AutoWood.AutoWoodState.FARM;
                     }
                  }
               }
            }
         }
      }
   }

   private Item resolve() {
      String text = this.chtoDobyvat.getValue();

      return switch (text) {
         case "Тропик дерево" -> Items.JUNGLE_SAPLING;
         case "Еловое дерево" -> Items.SPRUCE_SAPLING;
         default -> Items.DARK_OAK_SAPLING;
      };
   }

   private Block resolve2() {
      String text2 = this.chtoDobyvat.getValue();

      return switch (text2) {
         case "Тропик дерево" -> Blocks.JUNGLE_SAPLING;
         case "Еловое дерево" -> Blocks.SPRUCE_SAPLING;
         default -> Blocks.DARK_OAK_SAPLING;
      };
   }

   private Block resolve3() {
      String text3 = this.chtoDobyvat.getValue();

      return switch (text3) {
         case "Тропик дерево" -> Blocks.JUNGLE_LOG;
         case "Еловое дерево" -> Blocks.SPRUCE_LOG;
         default -> Blocks.DARK_OAK_LOG;
      };
   }

   private Block resolve4() {
      String text4 = this.chtoDobyvat.getValue();

      return switch (text4) {
         case "Тропик дерево" -> Blocks.JUNGLE_LEAVES;
         case "Еловое дерево" -> Blocks.SPRUCE_LEAVES;
         default -> Blocks.DARK_OAK_LEAVES;
      };
   }

   private Item resolve5() {
      String text5 = this.chtoDobyvat.getValue();

      return switch (text5) {
         case "Тропик дерево" -> Items.JUNGLE_LOG;
         case "Еловое дерево" -> Items.SPRUCE_LOG;
         default -> Items.DARK_OAK_LOG;
      };
   }

   private void invoke() {
      this.items.clear();
      ArrayList arrayList = new ArrayList();
      this.invoke2(this.resolve2(), arrayList, false);
      this.invoke2(this.resolve3(), arrayList, true);
      if (this.items.isEmpty()) {
         ChatUtil.sendClientMessage("§c[AutoWood] §fПоставьте саженцы квадратами 2×2 рядом с собой и включите модуль");
         this.toggle();
      } else {
         this.blockPos = CLIENT.player.getBlockPos();
         this.flag = true;
         this.autoWoodState = AutoWood.AutoWoodState.FARM;
         this.intValue = 0;
         this.intValue3 = 0;
         ChatUtil.sendClientMessage("§a[AutoWood] §fНайдено площадок 2×2: " + this.items.size());
      }
   }

   private void invoke2(Block block, List<BlockPos> list, boolean bl) {
      BlockPos blockPos2 = CLIENT.player.getBlockPos();

      for (BlockPos blockPos3 : BlockPos.iterate(blockPos2.add(-6, -3, -6), blockPos2.add(6, 3, 6))) {
         if (this.check(blockPos3, block)) {
            BlockPos blockPos4 = blockPos3.toImmutable();
            if (!bl || CLIENT.world.getBlockState(blockPos4.down()).getBlock() != block) {
               List items = List.of(blockPos4, blockPos4.east(), blockPos4.south(), blockPos4.east().south());
               boolean flag = false;

               for (BlockPos blockPos5 : (List<BlockPos>)items) {
                  if (list.contains(blockPos5)) {
                     flag = true;
                     break;
                  }
               }

               if (!flag) {
                  boolean flag2 = true;
                  Iterator iterator = items.iterator();

                  while (true) {
                     if (iterator.hasNext()) {
                        BlockPos blockPos6 = (BlockPos)iterator.next();
                        if (this.check15(blockPos6)) {
                           continue;
                        }

                        flag2 = false;
                     }

                     if (flag2) {
                        this.items.add(new ArrayList<>(items));
                        list.addAll(items);
                     }
                     break;
                  }
               }
            }
         }
      }
   }

   private boolean check(BlockPos blockPos, Block block) {
      return CLIENT.world.getBlockState(blockPos).getBlock() == block
         && CLIENT.world.getBlockState(blockPos.east()).getBlock() == block
         && CLIENT.world.getBlockState(blockPos.south()).getBlock() == block
         && CLIENT.world.getBlockState(blockPos.east().south()).getBlock() == block;
   }

   private void invoke3() {
      boolean flag3 = false;

      for (int intValue = 0; intValue < this.items.size(); intValue++) {
         if (this.check2(intValue)) {
            flag3 = true;
            if (this.check3(intValue)) {
               return;
            }
         }
      }

      if (!flag3) {
         for (List items2 : this.items) {
            for (BlockPos blockPos7 : (List<BlockPos>)items2) {
               BlockState blockState2 = CLIENT.world.getBlockState(blockPos7);
               if (blockState2.getBlock() != this.resolve2()) {
                  this.invoke4(blockPos7, blockState2);
                  return;
               }
            }
         }

         this.blockPos2 = null;
         this.invoke6();
      }
   }

   private boolean check2(int i) {
      for (BlockPos blockPos8 : this.items.get(i)) {
         if (CLIENT.world.getBlockState(blockPos8).getBlock() == this.resolve3()) {
            return true;
         }
      }

      return false;
   }

   private void invoke4(BlockPos blockPos, BlockState blockState) {
      if (blockState.getBlock() == this.resolve4()) {
         this.invoke5(blockPos);
      } else {
         this.blockPos2 = null;
         if (!blockState.isReplaceable()) {
            if (this.dualTimer10.check5(15000L)) {
               ChatUtil.sendClientMessage("§e[AutoWood] §fМесто посадки занято посторонним блоком, жду освобождения");
               this.dualTimer10.invoke();
            }
         } else {
            int intValue2 = this.compute9(this.resolve());
            if (intValue2 == -1) {
               intValue2 = this.compute10(this.resolve());
            }

            if (intValue2 == -1) {
               if (this.dualTimer10.check5(15000L)) {
                  ChatUtil.sendClientMessage("§e[AutoWood] §fНет саженцев в инвентаре, жду дроп с листвы");
                  this.dualTimer10.invoke();
               }
            } else if (this.check14(blockPos.down())) {
               int intValue3 = CLIENT.player.getInventory().getSelectedSlot();
               CLIENT.player.getInventory().setSelectedSlot(intValue2);
               this.invoke34(blockPos.down());
               CLIENT.player.getInventory().setSelectedSlot(intValue3);
               this.intValue3 = 0;
            }
         }
      }
   }

   private void invoke5(BlockPos blockPos) {
      BlockHitResult blockHitResult = this.resolve12(blockPos);
      if (blockHitResult == null) {
         this.blockPos2 = null;
      } else {
         Rotation rotation = this.resolve11(blockHitResult.getPos());
         RotationController.invoke3(rotation, 65.0F, 65.0F, 65.0F, 65.0F, 2, 20, false);
         if (!(new Rotation(CLIENT.player).measure(rotation) > 6.0F)) {
            if (!blockPos.equals(this.blockPos2)) {
               CLIENT.interactionManager.attackBlock(blockPos, blockHitResult.getSide());
               this.blockPos2 = blockPos;
            } else {
               CLIENT.interactionManager.updateBlockBreakingProgress(blockPos, blockHitResult.getSide());
            }

            CLIENT.player.swingHand(Hand.MAIN_HAND);
         }
      }
   }

   private void invoke6() {
      if (!this.items.isEmpty()) {
         int intValue4 = this.items.size();

         for (int intValue5 = 0; intValue5 < intValue4; intValue5++) {
            int intValue6 = (this.intValue + intValue5) % intValue4;
            BlockPos blockPos9 = null;

            for (BlockPos blockPos10 : this.items.get(intValue6)) {
               if (CLIENT.world.getBlockState(blockPos10).getBlock() == this.resolve2()) {
                  blockPos9 = blockPos10;
                  break;
               }
            }

            if (blockPos9 != null) {
               int intValue7 = this.compute9(Items.BONE_MEAL);
               if (intValue7 == -1) {
                  intValue7 = this.compute10(Items.BONE_MEAL);
               }

               if (intValue7 == -1) {
                  if (this.popolnyatMukuIzSunduka.isEnabled()) {
                     this.autoWoodState3 = AutoWood.AutoWoodState3.BONEMEAL;
                     this.invoke14();
                     return;
                  }

                  ChatUtil.sendClientMessage("§c[AutoWood] §fЗакончилась костная мука — выключаюсь");
                  this.toggle();
                  return;
               }

               if (!this.check14(blockPos9)) {
                  return;
               }

               int intValue8 = CLIENT.player.getInventory().getSelectedSlot();
               CLIENT.player.getInventory().setSelectedSlot(intValue7);
               this.invoke34(blockPos9);
               CLIENT.player.getInventory().setSelectedSlot(intValue8);
               this.intValue = (intValue6 + 1) % intValue4;
               this.intValue3 = 0;
               return;
            }
         }
      }
   }

   private boolean check3(int i) {
      BlockPos blockPos11 = null;
      BlockHitResult blockHitResult2 = null;

      for (BlockPos blockPos12 : this.items.get(i)) {
         if (CLIENT.world.getBlockState(blockPos12).getBlock() == this.resolve3()) {
            BlockHitResult blockHitResult3 = this.resolve12(blockPos12);
            if (blockHitResult3 != null) {
               blockPos11 = blockPos12;
               blockHitResult2 = blockHitResult3;
               break;
            }
         }
      }

      if (blockPos11 == null) {
         return false;
      } else {
         if (!(CLIENT.player.getMainHandStack().getItem() instanceof AxeItem)) {
            int intValue9 = this.compute3();
            if (intValue9 == -1) {
               if (this.dualTimer10.check5(15000L)) {
                  ChatUtil.sendClientMessage("§e[AutoWood] §fНет топора в хотбаре, жду");
                  this.dualTimer10.invoke();
               }

               return true;
            }

            CLIENT.player.getInventory().setSelectedSlot(intValue9);
         }

         Rotation rotation2 = this.resolve11(blockHitResult2.getPos());
         RotationController.invoke3(rotation2, 65.0F, 65.0F, 65.0F, 65.0F, 2, 20, false);
         if (new Rotation(CLIENT.player).measure(rotation2) > 6.0F) {
            return true;
         } else {
            CLIENT.interactionManager.attackBlock(blockPos11, blockHitResult2.getSide());
            CLIENT.player.swingHand(Hand.MAIN_HAND);
            this.intValue2 = i;
            this.dualTimer6.invoke();
            this.autoWoodState = AutoWood.AutoWoodState.WAIT_FELL;
            this.intValue3 = 0;
            return true;
         }
      }
   }

   private void invoke7() {
      if (this.intValue2 < 0 || this.intValue2 >= this.items.size()) {
         this.autoWoodState = AutoWood.AutoWoodState.FARM;
      } else if (!this.check2(this.intValue2)) {
         this.intValue2 = -1;
         this.autoWoodState = AutoWood.AutoWoodState.FARM;
         this.intValue3 = 0;
      } else {
         if (this.dualTimer6.check5(2000L)) {
            this.autoWoodState = AutoWood.AutoWoodState.FARM;
            this.intValue3 = 0;
         }
      }
   }

   private boolean check4() {
      if (!this.chtoDelatSDerevom.is("Продавать на ауке")) {
         return false;
      } else if (this.autoWoodState != AutoWood.AutoWoodState.FARM) {
         return false;
      } else {
         if (this.flag2) {
            if (!this.dualTimer8.check5(30000L)) {
               return false;
            }

            this.flag2 = false;
         }

         return this.compute4(this.resolve5()) >= (int)this.porogDereva.getValue();
      }
   }

   private boolean check5() {
      if (!this.chtoDelatSDerevom.is("Складывать в сундук")) {
         return false;
      } else if (this.autoWoodState != AutoWood.AutoWoodState.FARM) {
         return false;
      } else {
         if (this.flag3) {
            if (!this.dualTimer9.check5(30000L)) {
               return false;
            }

            this.flag3 = false;
         }

         return this.compute4(this.resolve5()) >= (int)this.porogDereva.getValue();
      }
   }

   private void invoke8() {
      switch (this.autoWoodState4) {
         case EQUIP:
            this.invoke9();
            break;
         case COMMAND:
            this.invoke10();
            break;
         case CONFIRM:
            this.invoke11();
            break;
         case WAIT_RESULT:
            this.invoke12();
            break;
         default:
            this.invoke13();
      }
   }

   private void invoke9() {
      if (CLIENT.currentScreen != null) {
         CLIENT.player.closeHandledScreen();
      } else if (this.compute4(this.resolve5()) < 64) {
         this.invoke13();
      } else {
         int intValue10 = -1;
         int intValue11 = 0;

         for (int intValue12 = 0; intValue12 < 9; intValue12++) {
            ItemStack itemStack2 = CLIENT.player.getInventory().getStack(intValue12);
            if (itemStack2.getItem() == this.resolve5() && itemStack2.getCount() > intValue11) {
               intValue10 = intValue12;
               intValue11 = itemStack2.getCount();
            }
         }

         if (intValue10 != -1) {
            CLIENT.player.getInventory().setSelectedSlot(intValue10);
            if (CLIENT.player.getMainHandStack().getItem() == this.resolve5()) {
               this.autoWoodState4 = AutoWood.AutoWoodState4.COMMAND;
               this.dualTimer7.invoke();
            }
         } else {
            int intValue13 = -1;
            intValue11 = 0;

            for (int intValue14 = 9; intValue14 < 36; intValue14++) {
               ItemStack itemStack3 = CLIENT.player.getInventory().getStack(intValue14);
               if (itemStack3.getItem() == this.resolve5() && itemStack3.getCount() > intValue11) {
                  intValue13 = intValue14;
                  intValue11 = itemStack3.getCount();
               }
            }

            if (intValue13 == -1) {
               this.invoke13();
            } else {
               int intValue15 = this.compute11();
               if (intValue15 == -1) {
                  intValue15 = 0;
               }

               CLIENT.interactionManager.clickSlot(CLIENT.player.playerScreenHandler.syncId, intValue13, intValue15, SlotActionType.SWAP, CLIENT.player);
            }
         }
      }
   }

   private void invoke10() {
      if (CLIENT.player.getMainHandStack().getItem() != this.resolve5()) {
         this.autoWoodState4 = AutoWood.AutoWoodState4.EQUIP;
      } else if (CLIENT.currentScreen != null) {
         CLIENT.player.closeHandledScreen();
      } else {
         CLIENT.player.networkHandler.sendChatCommand("ah sell auto");
         this.dualTimer7.invoke();
         this.autoWoodState4 = AutoWood.AutoWoodState4.CONFIRM;
      }
   }

   private void invoke11() {
      if (this.dualTimer7.check5(1000L)) {
         CLIENT.player.networkHandler.sendChatCommand("ah sell auto confirm");
         this.dualTimer7.invoke();
         this.autoWoodState4 = AutoWood.AutoWoodState4.WAIT_RESULT;
      }
   }

   private void invoke12() {
      if (CLIENT.player.getMainHandStack().getItem() != this.resolve5()) {
         this.intValue4 = 0;
         if (this.compute4(this.resolve5()) >= 64) {
            this.autoWoodState4 = AutoWood.AutoWoodState4.EQUIP;
         } else {
            this.invoke13();
         }
      } else {
         if (this.dualTimer7.check5(6000L)) {
            this.intValue4++;
            if (this.intValue4 >= 3) {
               ChatUtil.sendClientMessage("§c[AutoWood] §fНе удалось продать дерево на аукционе, попробую позже");
               this.flag2 = true;
               this.dualTimer8.invoke();
               this.invoke13();
            } else {
               this.autoWoodState4 = AutoWood.AutoWoodState4.COMMAND;
               this.dualTimer7.invoke();
            }
         }
      }
   }

   private void invoke13() {
      this.autoWoodState4 = AutoWood.AutoWoodState4.NONE;
      this.intValue3 = 0;
   }

   private void invoke14() {
      this.flag4 = false;
      this.flag5 = false;
      this.intValue5 = 0;
      this.blockPos3 = null;
      this.flag6 = false;
      this.intValue7 = -1;
      this.queue.clear();
      this.dualTimer.invoke();
      this.dualTimer2.invoke();
      this.dualTimer3.invoke();
      this.dualTimer4.invoke();
      switch (this.autoWoodState3) {
         case REPAIR:
            this.autoWoodState2 = this.compute4(Items.EXPERIENCE_BOTTLE) > 0 ? AutoWood.AutoWoodState2.REPAIRING : AutoWood.AutoWoodState2.FIND_CHEST;
            break;
         case BONEMEAL:
            this.autoWoodState2 = this.check10() ? AutoWood.AutoWoodState2.CRAFTING : AutoWood.AutoWoodState2.FIND_CHEST;
            break;
         default:
            this.autoWoodState2 = AutoWood.AutoWoodState2.FIND_CHEST;
      }
   }

   private void invoke15() {
      if (CLIENT.currentScreen == null || CLIENT.currentScreen instanceof GenericContainerScreen) {
         switch (this.autoWoodState2) {
            case FIND_CHEST:
               this.invoke16();
               break;
            case GOING:
               this.invoke17();
               break;
            case ROTATING:
               this.invoke18();
               break;
            case OPENING:
               this.invoke19();
               break;
            case WAIT_GUI:
               this.invoke20();
               break;
            case CRAFTING:
               this.invoke25();
               break;
            case REPAIRING:
               this.invoke27();
               break;
            case RETURNING:
               this.invoke29();
               break;
            default:
               this.invoke31();
         }
      }
   }

   private void invoke16() {
      this.blockPos3 = this.resolve6(this.autoWoodState3);
      if (this.blockPos3 == null) {
         this.invoke32(
            "§c[AutoWood] §fНе найден сундук «"
               + this.resolve9(this.autoWoodState3)
               + "» в радиусе "
               + (int)this.radiusPoiskaSundukov.getValue()
               + " бл. — выключаюсь"
         );
      } else {
         if (this.check15(this.blockPos3) && this.check13(this.blockPos3)) {
            this.autoWoodState2 = AutoWood.AutoWoodState2.ROTATING;
            this.dualTimer.invoke();
         } else {
            this.flag4 = true;
            this.autoWoodState2 = AutoWood.AutoWoodState2.GOING;
            this.dualTimer2.invoke();
            this.dualTimer3.invoke();
         }
      }
   }

   private void invoke17() {
      if (this.blockPos3 != null && this.check12(this.blockPos3)) {
         double doubleValue = CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(this.blockPos3));
         if (doubleValue <= 4.5 && this.check13(this.blockPos3)) {
            if (this.iBaritone != null) {
               this.iBaritone.getPathingBehavior().cancelEverything();
            }

            this.autoWoodState2 = AutoWood.AutoWoodState2.ROTATING;
            this.dualTimer.invoke();
         } else {
            if (this.iBaritone != null && (!this.iBaritone.getCustomGoalProcess().isActive() || this.dualTimer2.check5(1500L))) {
               this.iBaritone.getCustomGoalProcess().setGoalAndPath(new GoalNear(this.blockPos3, 2));
               this.dualTimer2.invoke();
            }

            if (this.dualTimer3.check5(15000L)) {
               this.invoke32("§c[AutoWood] §fНе удалось дойти до сундука «" + this.resolve9(this.autoWoodState3) + "»");
            }
         }
      } else {
         this.autoWoodState2 = AutoWood.AutoWoodState2.FIND_CHEST;
      }
   }

   private void invoke18() {
      if (this.blockPos3 == null) {
         this.autoWoodState2 = AutoWood.AutoWoodState2.FIND_CHEST;
      } else {
         if (this.check14(this.blockPos3)) {
            this.autoWoodState2 = AutoWood.AutoWoodState2.OPENING;
            this.dualTimer.invoke();
         }
      }
   }

   private void invoke19() {
      if (this.dualTimer.check5(200L)) {
         this.invoke34(this.blockPos3);
         this.autoWoodState2 = AutoWood.AutoWoodState2.WAIT_GUI;
         this.dualTimer.invoke();
      }
   }

   private void invoke20() {
      if (!(CLIENT.currentScreen instanceof GenericContainerScreen)) {
         if (this.dualTimer.check5(2500L)) {
            this.intValue5++;
            if (this.intValue5 > 3) {
               this.invoke32("§c[AutoWood] §fНе удалось открыть сундук «" + this.resolve9(this.autoWoodState3) + "»");
            } else {
               this.autoWoodState2 = AutoWood.AutoWoodState2.ROTATING;
               this.dualTimer.invoke();
            }
         }
      }
   }

   private void invoke21(GenericContainerScreen genericContainerScreen) {
      GenericContainerScreenHandler genericContainerScreenHandler2 = (GenericContainerScreenHandler)genericContainerScreen.getScreenHandler();
      int intValue16 = genericContainerScreenHandler2.slots.size() - 36;
      if (intValue16 <= 0) {
         this.invoke24("§c[AutoWood] §fСундук «" + this.resolve9(this.autoWoodState3) + "» пуст — выключаюсь");
      } else if (this.dualTimer4.check5(120L)) {
         if (this.check7(this.autoWoodState3)) {
            this.invoke23();
         } else {
            int intValue17 = this.compute(genericContainerScreenHandler2, intValue16, this.autoWoodState3);
            if (intValue17 == -1) {
               if (this.flag5) {
                  this.invoke23();
               } else {
                  this.invoke24("§c[AutoWood] §fВ сундуке «" + this.resolve9(this.autoWoodState3) + "» нет нужных предметов — выключаюсь");
               }
            } else {
               CLIENT.interactionManager.clickSlot(genericContainerScreenHandler2.syncId, intValue17, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
               this.flag5 = true;
               this.dualTimer4.invoke();
            }
         }
      }
   }

   private void invoke22(GenericContainerScreen genericContainerScreen) {
      GenericContainerScreenHandler genericContainerScreenHandler3 = (GenericContainerScreenHandler)genericContainerScreen.getScreenHandler();
      int intValue18 = genericContainerScreenHandler3.slots.size() - 36;
      if (intValue18 <= 0) {
         if (CLIENT.player != null) {
            CLIENT.player.closeHandledScreen();
         }

         this.invoke30();
      } else if (this.dualTimer4.check5(120L)) {
         for (int intValue19 = intValue18; intValue19 < genericContainerScreenHandler3.slots.size(); intValue19++) {
            ItemStack itemStack4 = ((Slot)genericContainerScreenHandler3.slots.get(intValue19)).getStack();
            if (itemStack4.getItem() == this.resolve5() && this.check6(genericContainerScreenHandler3, intValue18, itemStack4)) {
               CLIENT.interactionManager.clickSlot(genericContainerScreenHandler3.syncId, intValue19, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
               this.flag5 = true;
               this.dualTimer4.invoke();
               return;
            }
         }

         if (!this.flag5) {
            this.flag3 = true;
            this.dualTimer9.invoke();
            ChatUtil.sendClientMessage("§c[AutoWood] §fСундук «лут/дерево» переполнен — некуда складывать, попробую позже");
         }

         if (CLIENT.player != null) {
            CLIENT.player.closeHandledScreen();
         }

         this.invoke30();
      }
   }

   private boolean check6(GenericContainerScreenHandler genericContainerScreenHandler, int i, ItemStack itemStack) {
      for (int intValue20 = 0; intValue20 < i; intValue20++) {
         ItemStack itemStack5 = ((Slot)genericContainerScreenHandler.slots.get(intValue20)).getStack();
         if (itemStack5.isEmpty()) {
            return true;
         }

         if (itemStack5.getItem() == itemStack.getItem() && itemStack5.getCount() < itemStack5.getMaxCount()) {
            return true;
         }
      }

      return false;
   }

   private boolean check7(AutoWood.AutoWoodState3 autoWoodState3) {
      return switch (autoWoodState3) {
         case REPAIR -> this.compute4(Items.EXPERIENCE_BOTTLE) >= 64;
         case BONEMEAL -> this.compute5() >= 128;
         default -> true;
      };
   }

   private int compute(GenericContainerScreenHandler genericContainerScreenHandler, int i, AutoWood.AutoWoodState3 autoWoodState32) {
      for (int intValue21 = 0; intValue21 < i; intValue21++) {
         ItemStack itemStack6 = ((Slot)genericContainerScreenHandler.slots.get(intValue21)).getStack();
         if (!itemStack6.isEmpty() && this.check8(itemStack6.getItem(), autoWoodState32)) {
            return intValue21;
         }
      }

      return -1;
   }

   private boolean check8(Item item, AutoWood.AutoWoodState3 autoWoodState33) {
      return switch (autoWoodState33) {
         case REPAIR -> item == Items.EXPERIENCE_BOTTLE;
         case BONEMEAL -> item == Items.BONE_MEAL || item == Items.BONE || item == Items.BONE_BLOCK;
         default -> false;
      };
   }

   private void invoke23() {
      if (CLIENT.player != null) {
         CLIENT.player.closeHandledScreen();
      }
      this.autoWoodState2 = switch (this.autoWoodState3) {
         case REPAIR -> AutoWood.AutoWoodState2.REPAIRING;
         case BONEMEAL -> AutoWood.AutoWoodState2.CRAFTING;
         default -> AutoWood.AutoWoodState2.RETURNING;
      };
      if (this.autoWoodState2 == AutoWood.AutoWoodState2.RETURNING) {
         this.dualTimer2.invoke();
         this.dualTimer3.invoke();
      }

      this.dualTimer.invoke();
      this.dualTimer4.invoke();
      this.queue.clear();
   }

   private void invoke24(String string) {
      if (CLIENT.player != null) {
         CLIENT.player.closeHandledScreen();
      }

      this.invoke32(string);
   }

   private void invoke25() {
      if (CLIENT.currentScreen == null) {
         if (!this.queue.isEmpty()) {
            if (this.dualTimer4.check5(90L)) {
               this.queue.poll().run();
               this.dualTimer4.invoke();
            }
         } else if (this.compute4(Items.BONE_MEAL) >= 128) {
            this.invoke30();
         } else {
            int intValue22 = this.compute6();
            if (intValue22 == -1) {
               if (this.compute4(Items.BONE_MEAL) == 0) {
                  this.invoke32("§c[AutoWood] §fКостная мука закончилась и крафтить не из чего — выключаюсь");
               } else {
                  this.invoke30();
               }
            } else {
               int intValue23 = CLIENT.player.playerScreenHandler.syncId;
               this.queue.add(() -> CLIENT.interactionManager.clickSlot(intValue23, intValue22, 0, SlotActionType.PICKUP, CLIENT.player));
               this.queue.add(() -> CLIENT.interactionManager.clickSlot(intValue23, 1, 0, SlotActionType.PICKUP, CLIENT.player));
               this.queue.add(() -> CLIENT.interactionManager.clickSlot(intValue23, 0, 0, SlotActionType.QUICK_MOVE, CLIENT.player));
               this.queue.add(this::invoke26);
            }
         }
      }
   }

   private void invoke26() {
      int intValue24 = CLIENT.player.playerScreenHandler.syncId;

      for (int intValue25 = 1; intValue25 <= 4; intValue25++) {
         if (((Slot)CLIENT.player.playerScreenHandler.slots.get(intValue25)).hasStack()) {
            CLIENT.interactionManager.clickSlot(intValue24, intValue25, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
         }
      }

      if (!CLIENT.player.playerScreenHandler.getCursorStack().isEmpty()) {
         int intValue26 = this.compute7();
         if (intValue26 != -1) {
            CLIENT.interactionManager.clickSlot(intValue24, intValue26, 0, SlotActionType.PICKUP, CLIENT.player);
         }
      }
   }

   private void invoke27() {
      if (CLIENT.currentScreen == null) {
         int intValue27 = CLIENT.player.playerScreenHandler.syncId;
         if (!this.flag6) {
            int intValue28 = this.compute2();
            if (intValue28 == -1) {
               this.invoke30();
            } else if (this.compute4(Items.EXPERIENCE_BOTTLE) == 0) {
               this.autoWoodState2 = AutoWood.AutoWoodState2.FIND_CHEST;
            } else if (!CLIENT.player.getOffHandStack().isEmpty()) {
               int intValue29 = this.compute7();
               if (intValue29 == -1) {
                  this.invoke32("§c[AutoWood] §fОсвободите офф-хенд или место в инвентаре для починки");
               } else {
                  CLIENT.interactionManager.clickSlot(intValue27, 45, 0, SlotActionType.PICKUP, CLIENT.player);
                  CLIENT.interactionManager.clickSlot(intValue27, intValue29, 0, SlotActionType.PICKUP, CLIENT.player);
               }
            } else {
               this.intValue6 = intValue28;
               this.floatValue = CLIENT.player.getPitch();
               CLIENT.player.getInventory().setSelectedSlot(intValue28);
               CLIENT.interactionManager.clickSlot(intValue27, 45, intValue28, SlotActionType.SWAP, CLIENT.player);
               if (!this.check9()) {
                  CLIENT.interactionManager.clickSlot(intValue27, 45, intValue28, SlotActionType.SWAP, CLIENT.player);
                  CLIENT.player.getInventory().setSelectedSlot(intValue28);
                  this.autoWoodState2 = AutoWood.AutoWoodState2.FIND_CHEST;
               } else {
                  this.flag6 = true;
                  this.intValue7 = -1;
                  this.dualTimer5.invoke();
                  this.dualTimer.invoke();
               }
            }
         } else {
            ItemStack itemStack7 = CLIENT.player.getOffHandStack();
            if (!itemStack7.isEmpty() && itemStack7.isDamageable() && itemStack7.getDamage() != 0) {
               if (CLIENT.player.getMainHandStack().getItem() != Items.EXPERIENCE_BOTTLE && !this.check9()) {
                  this.invoke28(intValue27);
                  this.autoWoodState2 = AutoWood.AutoWoodState2.FIND_CHEST;
               } else {
                  int intValue30 = itemStack7.getDamage();
                  if (this.intValue7 == -1) {
                     this.intValue7 = intValue30;
                  }

                  if (intValue30 < this.intValue7) {
                     this.intValue7 = intValue30;
                     this.dualTimer5.invoke();
                  } else if (this.dualTimer5.check5(4000L)) {
                     this.invoke28(intValue27);
                     this.invoke32("§c[AutoWood] §fТопор не чинится (нет «Починки»?)");
                     return;
                  }

                  if (this.dualTimer.check5(120L)) {
                     CLIENT.player.setPitch(90.0F);
                     CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
                     CLIENT.player.swingHand(Hand.MAIN_HAND);
                     this.dualTimer.invoke();
                  }
               }
            } else {
               this.invoke28(intValue27);
            }
         }
      }
   }

   private void invoke28(int i) {
      CLIENT.interactionManager.clickSlot(i, 45, this.intValue6, SlotActionType.SWAP, CLIENT.player);
      if (this.intValue6 >= 0) {
         CLIENT.player.getInventory().setSelectedSlot(this.intValue6);
      }

      CLIENT.player.setPitch(this.floatValue);
      if (!CLIENT.player.getOffHandStack().isEmpty()) {
         int intValue31 = this.compute7();
         if (intValue31 != -1) {
            CLIENT.interactionManager.clickSlot(i, 45, 0, SlotActionType.PICKUP, CLIENT.player);
            CLIENT.interactionManager.clickSlot(i, intValue31, 0, SlotActionType.PICKUP, CLIENT.player);
         }
      }

      this.flag6 = false;
      this.invoke30();
   }

   private boolean check9() {
      int intValue32 = this.compute8();
      if (intValue32 == -1) {
         return false;
      } else {
         if (intValue32 >= 36 && intValue32 <= 44) {
            CLIENT.player.getInventory().setSelectedSlot(intValue32 - 36);
         } else {
            CLIENT.interactionManager
               .clickSlot(
                  CLIENT.player.playerScreenHandler.syncId,
                  intValue32,
                  CLIENT.player.getInventory().getSelectedSlot(),
                  SlotActionType.SWAP,
                  CLIENT.player
               );
         }

         return true;
      }
   }

   private void invoke29() {
      if (this.flag4 && this.blockPos != null && this.iBaritone != null) {
         if (!CLIENT.player.getBlockPos().equals(this.blockPos)
            && !(CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(this.blockPos)) <= 0.7)) {
            if (!this.iBaritone.getCustomGoalProcess().isActive() || this.dualTimer2.check5(1500L)) {
               this.iBaritone.getCustomGoalProcess().setGoalAndPath(new GoalBlock(this.blockPos));
               this.dualTimer2.invoke();
            }

            if (this.dualTimer3.check5(20000L)) {
               this.iBaritone.getPathingBehavior().cancelEverything();
               this.invoke31();
            }
         } else {
            this.iBaritone.getPathingBehavior().cancelEverything();
            this.invoke31();
         }
      } else {
         this.invoke31();
      }
   }

   private void invoke30() {
      this.autoWoodState2 = AutoWood.AutoWoodState2.RETURNING;
      this.dualTimer2.invoke();
      this.dualTimer3.invoke();
   }

   private void invoke31() {
      if (this.iBaritone != null) {
         this.iBaritone.getPathingBehavior().cancelEverything();
      }

      this.invoke33();
      this.autoWoodState = AutoWood.AutoWoodState.FARM;
      this.intValue3 = 0;
   }

   private void invoke32(String string) {
      ChatUtil.sendClientMessage(string);
      if (this.iBaritone != null) {
         this.iBaritone.getPathingBehavior().cancelEverything();
      }

      this.invoke33();
      this.toggle();
   }

   private void invoke33() {
      this.autoWoodState3 = AutoWood.AutoWoodState3.NONE;
      this.autoWoodState2 = AutoWood.AutoWoodState2.FIND_CHEST;
      this.blockPos3 = null;
      this.flag4 = false;
      this.flag5 = false;
      this.intValue5 = 0;
      this.flag6 = false;
      this.intValue6 = -1;
      this.intValue7 = -1;
      this.queue.clear();
   }

   private int compute2() {
      if (!this.chinitTopor.isEnabled()) {
         return -1;
      } else {
         for (int intValue33 = 0; intValue33 < 9; intValue33++) {
            ItemStack itemStack8 = CLIENT.player.getInventory().getStack(intValue33);
            if (!itemStack8.isEmpty() && itemStack8.isDamageable() && itemStack8.getItem() instanceof AxeItem) {
               int intValue34 = itemStack8.getMaxDamage() - itemStack8.getDamage();
               if (intValue34 <= (int)this.porogDlyaPochinkiTopora.getValue()) {
                  return intValue33;
               }
            }
         }

         return -1;
      }
   }

   private int compute3() {
      for (int intValue35 = 0; intValue35 < 9; intValue35++) {
         if (CLIENT.player.getInventory().getStack(intValue35).getItem() instanceof AxeItem) {
            return intValue35;
         }
      }

      return -1;
   }

   private int compute4(Item item) {
      int intValue36 = 0;

      for (int intValue37 = 0; intValue37 < 36; intValue37++) {
         ItemStack itemStack9 = CLIENT.player.getInventory().getStack(intValue37);
         if (itemStack9.getItem() == item) {
            intValue36 += itemStack9.getCount();
         }
      }

      return intValue36;
   }

   private int compute5() {
      return this.compute4(Items.BONE_MEAL) + this.compute4(Items.BONE) * 3 + this.compute4(Items.BONE_BLOCK) * 9;
   }

   private boolean check10() {
      return this.compute4(Items.BONE) > 0 || this.compute4(Items.BONE_BLOCK) > 0;
   }

   private int compute6() {
      for (int intValue38 = 9; intValue38 <= 44; intValue38++) {
         Item item2 = ((Slot)CLIENT.player.playerScreenHandler.slots.get(intValue38)).getStack().getItem();
         if (item2 == Items.BONE || item2 == Items.BONE_BLOCK) {
            return intValue38;
         }
      }

      return -1;
   }

   private int compute7() {
      for (int intValue39 = 9; intValue39 <= 44; intValue39++) {
         if (!((Slot)CLIENT.player.playerScreenHandler.slots.get(intValue39)).hasStack()) {
            return intValue39;
         }
      }

      return -1;
   }

   private int compute8() {
      for (int intValue40 = 9; intValue40 <= 44; intValue40++) {
         if (((Slot)CLIENT.player.playerScreenHandler.slots.get(intValue40)).getStack().getItem() == Items.EXPERIENCE_BOTTLE) {
            return intValue40;
         }
      }

      return -1;
   }

   private BlockPos resolve6(AutoWood.AutoWoodState3 autoWoodState34) {
      if (CLIENT.world != null && CLIENT.player != null) {
         BlockPos blockPos13 = this.blockPos != null ? this.blockPos : CLIENT.player.getBlockPos();
         int intValue41 = (int)this.radiusPoiskaSundukov.getValue();
         BlockPos blockPos14 = null;
         double doubleValue2 = Double.MAX_VALUE;

         for (BlockPos blockPos15 : BlockPos.iterate(blockPos13.add(-intValue41, -5, -intValue41), blockPos13.add(intValue41, 5, intValue41))) {
            if (this.check12(blockPos15) && this.check11(blockPos15, autoWoodState34)) {
               double doubleValue3 = CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(blockPos15));
               if (doubleValue3 < doubleValue2) {
                  doubleValue2 = doubleValue3;
                  blockPos14 = blockPos15.toImmutable();
               }
            }
         }

         return blockPos14;
      } else {
         return null;
      }
   }

   private boolean check11(BlockPos blockPos, AutoWood.AutoWoodState3 autoWoodState35) {
      String text6 = this.resolve7(blockPos).toLowerCase(Locale.ROOT);
      if (text6.isEmpty()) {
         return false;
      } else {
         String[] texts;
         String[] texts2;
         switch (autoWoodState35) {
            case REPAIR:
               texts = new String[]{"опыт"};
               texts2 = new String[]{"кост", "мука"};
               break;
            case BONEMEAL:
               texts = new String[]{"кост", "мука"};
               texts2 = new String[]{"опыт", "лут", "дерев"};
               break;
            case DEPOSIT:
               texts = new String[]{"лут", "дерев"};
               texts2 = new String[]{"опыт", "кост", "мука"};
               break;
            default:
               return false;
         }

         boolean flag4 = false;

         for (String text7 : texts) {
            if (text6.contains(text7)) {
               flag4 = true;
               break;
            }
         }

         if (!flag4) {
            return false;
         } else {
            for (String text8 : texts2) {
               if (text6.contains(text8)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private String resolve7(BlockPos blockPos) {
      if (blockPos != null && CLIENT.world != null) {
         SignBlockEntity signBlockEntity2 = null;
         double doubleValue4 = Double.MAX_VALUE;
         BlockPos blockPos16 = blockPos.add(-1, -1, -1);
         BlockPos blockPos17 = blockPos.add(1, 1, 1);

         for (BlockPos blockPos18 : BlockPos.iterate(blockPos16, blockPos17)) {
            if (CLIENT.world.getBlockEntity(blockPos18) instanceof SignBlockEntity signBlockEntity3) {
               double doubleValue5 = blockPos18.getSquaredDistance(blockPos);
               if (doubleValue5 < doubleValue4) {
                  doubleValue4 = doubleValue5;
                  signBlockEntity2 = signBlockEntity3;
               }
            }
         }

         return signBlockEntity2 == null ? "" : this.resolve8(signBlockEntity2);
      } else {
         return "";
      }
   }

   private String resolve8(SignBlockEntity signBlockEntity) {
      StringBuilder stringBuilder = new StringBuilder();

      for (Text text9 : signBlockEntity.getFrontText().getMessages(false)) {
         stringBuilder.append(text9.getString()).append(' ');
      }

      for (Text text10 : signBlockEntity.getBackText().getMessages(false)) {
         stringBuilder.append(text10.getString()).append(' ');
      }

      return stringBuilder.toString().replaceAll("§.", "").trim();
   }

   private boolean check12(BlockPos blockPos) {
      if (CLIENT.world == null) {
         return false;
      } else {
         BlockEntity blockEntity = CLIENT.world.getBlockEntity(blockPos);
         return blockEntity instanceof ChestBlockEntity || blockEntity instanceof BarrelBlockEntity || blockEntity instanceof ShulkerBoxBlockEntity;
      }
   }

   private boolean check13(BlockPos blockPos) {
      return this.resolve12(blockPos) != null;
   }

   private String resolve9(AutoWood.AutoWoodState3 autoWoodState36) {
      return switch (autoWoodState36) {
         case REPAIR -> "опыт";
         case BONEMEAL -> "костная мука";
         case DEPOSIT -> "лут/дерево";
         default -> "";
      };
   }

   private void invoke34(BlockPos blockPos) {
      Vec3d vec3d2 = this.resolve10(blockPos, Direction.UP);
      BlockHitResult blockHitResult4 = new BlockHitResult(vec3d2, Direction.UP, blockPos, false);
      CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, blockHitResult4);
      CLIENT.player.swingHand(Hand.MAIN_HAND);
   }

   private boolean check14(BlockPos blockPos) {
      Rotation rotation3 = this.resolve11(this.resolve10(blockPos, Direction.UP));
      RotationController.invoke3(rotation3, 65.0F, 65.0F, 65.0F, 65.0F, 2, 20, false);
      return new Rotation(CLIENT.player).measure(rotation3) <= 6.0F;
   }

   private Vec3d resolve10(BlockPos blockPos, Direction direction) {
      return new Vec3d(
         blockPos.getX() + 0.5 + direction.getOffsetX() * 0.5,
         blockPos.getY() + 0.5 + direction.getOffsetY() * 0.5,
         blockPos.getZ() + 0.5 + direction.getOffsetZ() * 0.5
      );
   }

   private Rotation resolve11(Vec3d vec3d) {
      if (CLIENT.player == null) {
         return new Rotation(0.0F, 0.0F);
      } else {
         Vec3d vec3d3 = CLIENT.player.getEyePos();
         double doubleValue6 = vec3d.x - vec3d3.x;
         double doubleValue7 = vec3d.y - vec3d3.y;
         double doubleValue8 = vec3d.z - vec3d3.z;
         double doubleValue9 = Math.sqrt(doubleValue6 * doubleValue6 + doubleValue8 * doubleValue8);
         float floatValue = (float)Math.toDegrees(Math.atan2(-doubleValue6, doubleValue8));
         float floatValue2 = (float)(-Math.toDegrees(Math.atan2(doubleValue7, doubleValue9)));
         return new Rotation(floatValue, floatValue2);
      }
   }

   private boolean check15(BlockPos blockPos) {
      return CLIENT.player.getEyePos().squaredDistanceTo(Vec3d.ofCenter(blockPos)) <= 20.25;
   }

   private BlockHitResult resolve12(BlockPos blockPos) {
      Vec3d vec3d4 = CLIENT.player.getEyePos();
      double[] doubleValues = new double[]{0.5, 0.2, 0.8};

      for (double doubleValue10 : doubleValues) {
         for (double doubleValue11 : doubleValues) {
            for (double doubleValue12 : doubleValues) {
               Vec3d vec3d5 = new Vec3d(blockPos.getX() + doubleValue10, blockPos.getY() + doubleValue11, blockPos.getZ() + doubleValue12);
               BlockHitResult blockHitResult5 = CLIENT.world.raycast(new RaycastContext(vec3d4, vec3d5, ShapeType.OUTLINE, FluidHandling.NONE, CLIENT.player));
               if (blockHitResult5.getType() == Type.BLOCK && blockHitResult5.getBlockPos().equals(blockPos)) {
                  return blockHitResult5;
               }
            }
         }
      }

      return null;
   }

   private int compute9(Item item) {
      for (int intValue42 = 0; intValue42 < 9; intValue42++) {
         if (CLIENT.player.getInventory().getStack(intValue42).getItem() == item) {
            return intValue42;
         }
      }

      return -1;
   }

   private int compute10(Item item) {
      int intValue43 = -1;

      for (int intValue44 = 9; intValue44 < 36; intValue44++) {
         if (CLIENT.player.getInventory().getStack(intValue44).getItem() == item) {
            intValue43 = intValue44;
            break;
         }
      }

      if (intValue43 == -1) {
         return -1;
      } else {
         int intValue45 = this.compute11();
         if (intValue45 == -1) {
            return -1;
         } else {
            CLIENT.interactionManager.clickSlot(CLIENT.player.playerScreenHandler.syncId, intValue43, intValue45, SlotActionType.SWAP, CLIENT.player);
            return intValue45;
         }
      }
   }

   private int compute11() {
      for (int intValue46 = 0; intValue46 < 9; intValue46++) {
         if (CLIENT.player.getInventory().getStack(intValue46).isEmpty()) {
            return intValue46;
         }
      }

      for (int intValue47 = 0; intValue47 < 9; intValue47++) {
         Item item3 = CLIENT.player.getInventory().getStack(intValue47).getItem();
         if (!(item3 instanceof AxeItem)
            && item3 != this.resolve()
            && item3 != Items.BONE_MEAL
            && item3 != Items.BONE
            && item3 != Items.BONE_BLOCK
            && item3 != Items.EXPERIENCE_BOTTLE) {
            return intValue47;
         }
      }

      return -1;
   }

   static enum AutoWoodState {
      SETUP,
      FARM,
      WAIT_FELL;
   }

   static enum AutoWoodState2 {
      FIND_CHEST,
      GOING,
      ROTATING,
      OPENING,
      WAIT_GUI,
      CRAFTING,
      REPAIRING,
      RETURNING;
   }

   static enum AutoWoodState3 {
      NONE,
      REPAIR,
      BONEMEAL,
      DEPOSIT;
   }

   static enum AutoWoodState4 {
      NONE,
      EQUIP,
      COMMAND,
      CONFIRM,
      WAIT_RESULT;
   }
}
