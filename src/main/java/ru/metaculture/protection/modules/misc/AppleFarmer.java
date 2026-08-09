package ru.metaculture.protection;

import baritone.api.BaritoneAPI;
import baritone.api.IBaritone;
import baritone.api.pathing.goals.GoalBlock;
import baritone.api.pathing.goals.GoalNear;
import java.util.ArrayDeque;
import java.util.ArrayList;
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
import net.minecraft.item.HoeItem;
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
   name = "AppleFarmer",
   category = Category.Misc,
   description = "Автоматически фармит для вас яблоки"
)
public class AppleFarmer extends Module {
   public final NumberSetting distantsiya = new NumberSetting("Дистанция", 4.5F, 3.0F, 4.5F, 0.1F, true);
   public final BooleanSetting avtoPopolnenieIzSundukov = new BooleanSetting("Авто-пополнение из сундуков", true);
   public final NumberSetting chinitPriProchnosti = new NumberSetting("Чинить при прочности <", 150.0F, 20.0F, 1000.0F, 10.0F, false)
      .setVisibilityCondition(() -> !this.avtoPopolnenieIzSundukov.isEnabled());
   public final NumberSetting radiusPoiskaSundukov = new NumberSetting("Радиус поиска сундуков", 12.0F, 4.0F, 40.0F, 1.0F, false)
      .setVisibilityCondition(() -> !this.avtoPopolnenieIzSundukov.isEnabled());
   public final NumberSetting razgruzkaPriSvobodnyhSlotah = new NumberSetting("Разгрузка при свободных слотах ≤", 3.0F, 0.0F, 10.0F, 1.0F, false)
      .setVisibilityCondition(() -> !this.avtoPopolnenieIzSundukov.isEnabled());
   private AppleFarmer.AppleFarmerState appleFarmerState = AppleFarmer.AppleFarmerState.FIND_SPOT;
   private BlockPos blockPos = null;
   private final List<BlockPos> items = new ArrayList<>();
   private static final int INT_VALUE = 2;
   private static final int INT_VALUE_2 = 4;
   private static final int INT_VALUE_3 = 8;
   private Direction direction = Direction.NORTH;
   private BlockPos blockPos2 = null;
   private int intValue = 0;
   private int intValue2 = 0;
   private IBaritone iBaritone;
   private boolean flag = false;
   private BlockPos blockPos3 = null;
   private AppleFarmer.AppleFarmerState3 appleFarmerState3 = AppleFarmer.AppleFarmerState3.NONE;
   private AppleFarmer.AppleFarmerState2 appleFarmerState2 = AppleFarmer.AppleFarmerState2.FIND_CHEST;
   private BlockPos blockPos4 = null;
   private boolean flag2 = false;
   private boolean flag3 = false;
   private int intValue3 = 0;
   private boolean flag4 = false;
   private int intValue4 = -1;
   private int intValue5 = -1;
   private float floatValue = 0.0F;
   private final DualTimer dualTimer = new DualTimer();
   private final DualTimer dualTimer2 = new DualTimer();
   private final DualTimer dualTimer3 = new DualTimer();
   private final DualTimer dualTimer4 = new DualTimer();
   private final DualTimer dualTimer5 = new DualTimer();
   private final DualTimer dualTimer6 = new DualTimer();
   private final Queue<Runnable> queue = new ArrayDeque<>();
   private boolean flag5 = false;
   private static final int INT_VALUE_4 = 64;
   private static final int INT_VALUE_5 = 64;
   private static final int INT_VALUE_6 = 128;
   private static final int INT_VALUE_7 = 64;

   public AppleFarmer() {
      this.addSettings(new Setting[]{this.distantsiya, this.avtoPopolnenieIzSundukov, this.chinitPriProchnosti, this.radiusPoiskaSundukov, this.razgruzkaPriSvobodnyhSlotah});
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.iBaritone = BaritoneAPI.getProvider().getPrimaryBaritone();
      this.appleFarmerState = AppleFarmer.AppleFarmerState.FIND_SPOT;
      this.blockPos = null;
      this.items.clear();
      this.blockPos2 = null;
      this.intValue2 = 0;
      this.flag = false;
      this.blockPos3 = null;
      this.flag5 = false;
      this.invoke26();
      this.intValue = 0;
   }

   @Override
   public void onDisable() {
      if (CLIENT.player != null && this.flag4) {
         try {
            CLIENT.interactionManager
               .clickSlot(CLIENT.player.playerScreenHandler.syncId, 45, this.intValue4, SlotActionType.SWAP, CLIENT.player);
            if (this.intValue4 >= 0) {
               CLIENT.player.getInventory().setSelectedSlot(this.intValue4);
            }

            CLIENT.player.setPitch(this.floatValue);
         } catch (Exception exception) {
         }
      }

      this.flag4 = false;
      if (this.iBaritone != null) {
         this.iBaritone.getPathingBehavior().cancelEverything();
      }

      this.invoke26();
      RotationController.rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
      RotationController.intValue = 0;
      RotationController.rotation = null;
      FreeLookController.active = false;
      this.blockPos2 = null;
      super.onDisable();
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null) {
         if (!PlayerHelper.check()) {
            if (this.avtoPopolnenieIzSundukov.isEnabled()) {
               if (this.appleFarmerState3 != AppleFarmer.AppleFarmerState3.NONE && CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen2) {
                  if (this.appleFarmerState3 == AppleFarmer.AppleFarmerState3.UNLOAD) {
                     this.invoke14(genericContainerScreen2);
                  } else {
                     this.invoke13(genericContainerScreen2);
                  }

                  return;
               }

               if (this.appleFarmerState3 == AppleFarmer.AppleFarmerState3.NONE && this.flag && CLIENT.currentScreen == null) {
                  AppleFarmer.AppleFarmerState3 appleFarmerState3 = this.resolve();
                  if (appleFarmerState3 != AppleFarmer.AppleFarmerState3.NONE) {
                     this.appleFarmerState3 = appleFarmerState3;
                     this.invoke6();
                  }
               }

               if (this.appleFarmerState3 != AppleFarmer.AppleFarmerState3.NONE) {
                  this.invoke7();
                  return;
               }
            }

            if (CLIENT.currentScreen == null) {
               this.intValue2++;
               if (this.intValue2 > 4) {
                  this.invoke29();
                  this.intValue2 = 0;
               }

               this.intValue++;
               if (this.appleFarmerState == AppleFarmer.AppleFarmerState.BREAKING || this.intValue >= 2) {
                  switch (this.appleFarmerState) {
                     case FIND_SPOT:
                        this.invoke();
                        break;
                     case PLACE:
                        this.invoke2();
                        break;
                     case BONEMEAL:
                        this.invoke3();
                        break;
                     case SCAN_TREE:
                        this.invoke4();
                        break;
                     case BREAKING:
                        this.invoke5();
                  }
               }
            }
         }
      }
   }

   private void invoke() {
      if (!this.flag) {
         this.blockPos3 = CLIENT.player.getBlockPos();
         this.direction = CLIENT.player.getHorizontalFacing();
         this.flag = true;
      }

      BlockPos blockPos3 = this.blockPos3;
      BlockPos blockPos4 = blockPos3.offset(this.direction);
      BlockPos blockPos5 = blockPos3.offset(this.direction, 2);
      BlockState blockState2 = CLIENT.world.getBlockState(blockPos5);
      if (this.check15(blockPos4) && this.check15(blockPos4.up())) {
         if (blockState2.getBlock() == Blocks.OAK_SAPLING) {
            this.blockPos = blockPos5;
            this.appleFarmerState = AppleFarmer.AppleFarmerState.BONEMEAL;
            this.intValue = 0;
         } else if (this.check17(blockState2)) {
            this.blockPos = blockPos5;
            this.appleFarmerState = AppleFarmer.AppleFarmerState.SCAN_TREE;
            this.intValue = 0;
         } else {
            BlockPos blockPos6 = blockPos5.down();
            if (this.check14(blockPos6) && blockState2.isReplaceable()) {
               this.blockPos = blockPos6.up();
               this.appleFarmerState = AppleFarmer.AppleFarmerState.PLACE;
            } else {
               ChatUtil.sendClientMessage("§c[AppleFarmer] §fВстаньте напротив места посадки: земля должна быть через один блок перед вами");
               this.toggle();
            }

            this.intValue = 0;
         }
      } else {
         ChatUtil.sendClientMessage("§c[AppleFarmer] §fМежду вами и местом посадки должен быть свободный блок");
         this.toggle();
         this.intValue = 0;
      }
   }

   private void invoke2() {
      if (this.blockPos == null) {
         this.appleFarmerState = AppleFarmer.AppleFarmerState.FIND_SPOT;
      } else {
         BlockState blockState3 = CLIENT.world.getBlockState(this.blockPos);
         if (blockState3.getBlock() == Blocks.OAK_SAPLING) {
            this.appleFarmerState = AppleFarmer.AppleFarmerState.BONEMEAL;
            this.intValue = 0;
         } else if (!blockState3.isReplaceable()) {
            this.appleFarmerState = AppleFarmer.AppleFarmerState.FIND_SPOT;
            this.intValue = 0;
         } else {
            int intValue = this.compute10(Items.OAK_SAPLING);
            if (intValue == -1) {
               intValue = this.compute11(Items.OAK_SAPLING);
            }

            if (intValue == -1) {
               if (this.avtoPopolnenieIzSundukov.isEnabled()) {
                  this.appleFarmerState = AppleFarmer.AppleFarmerState.FIND_SPOT;
                  this.intValue = 0;
               } else {
                  ChatUtil.sendClientMessage("§c[AppleFarmer] §fНет саженцев");
                  this.toggle();
               }
            } else if (this.check13(this.blockPos.down())) {
               int intValue2 = CLIENT.player.getInventory().getSelectedSlot();
               CLIENT.player.getInventory().setSelectedSlot(intValue);
               this.invoke27(this.blockPos.down());
               CLIENT.player.getInventory().setSelectedSlot(intValue2);
               this.appleFarmerState = AppleFarmer.AppleFarmerState.BONEMEAL;
               this.intValue = 0;
            }
         }
      }
   }

   private void invoke3() {
      if (this.blockPos != null) {
         BlockState blockState4 = CLIENT.world.getBlockState(this.blockPos);
         if (this.check17(blockState4)) {
            this.appleFarmerState = AppleFarmer.AppleFarmerState.SCAN_TREE;
         } else if (blockState4.isReplaceable()) {
            this.appleFarmerState = AppleFarmer.AppleFarmerState.PLACE;
         } else if (blockState4.getBlock() != Blocks.OAK_SAPLING) {
            this.appleFarmerState = AppleFarmer.AppleFarmerState.FIND_SPOT;
         } else {
            int intValue3 = this.compute10(Items.BONE_MEAL);
            if (intValue3 == -1) {
               intValue3 = this.compute11(Items.BONE_MEAL);
            }

            if (intValue3 == -1) {
               if (this.avtoPopolnenieIzSundukov.isEnabled()) {
                  this.appleFarmerState = AppleFarmer.AppleFarmerState.FIND_SPOT;
                  this.intValue = 0;
               } else {
                  ChatUtil.sendClientMessage("§c[AppleFarmer] §fНет костной муки");
                  this.toggle();
               }
            } else if (this.check13(this.blockPos)) {
               int intValue4 = CLIENT.player.getInventory().getSelectedSlot();
               CLIENT.player.getInventory().setSelectedSlot(intValue3);
               this.invoke27(this.blockPos);
               CLIENT.player.getInventory().setSelectedSlot(intValue4);
               this.intValue = 0;
            }
         }
      }
   }

   private void invoke4() {
      this.items.clear();
      BlockPos blockPos7 = this.blockPos;
      if (blockPos7 == null) {
         this.appleFarmerState = AppleFarmer.AppleFarmerState.PLACE;
      } else {
         double doubleValue = Math.min(this.distantsiya.getValue(), 4.5F);
         int intValue5 = (int)Math.ceil(doubleValue) + 1;
         BlockPos blockPos8 = this.flag && this.blockPos3 != null ? this.blockPos3 : CLIENT.player.getBlockPos();

         for (int intValue6 = -intValue5; intValue6 <= intValue5; intValue6++) {
            for (int intValue7 = -2; intValue7 <= 8; intValue7++) {
               for (int intValue8 = -intValue5; intValue8 <= intValue5; intValue8++) {
                  BlockPos blockPos9 = blockPos8.add(intValue6, intValue7, intValue8);
                  BlockState blockState5 = CLIENT.world.getBlockState(blockPos9);
                  if (this.check16(blockState5) && this.check19(blockPos9) && (!this.check17(blockState5) || this.check(blockPos9, blockPos7))) {
                     this.items.add(blockPos9);
                  }
               }
            }
         }

         if (this.items.isEmpty()) {
            this.appleFarmerState = AppleFarmer.AppleFarmerState.PLACE;
         } else {
            this.items.sort(this::compute9);
            this.blockPos2 = null;
            this.appleFarmerState = AppleFarmer.AppleFarmerState.BREAKING;
         }
      }
   }

   private boolean check(BlockPos blockPos, BlockPos blockPos2) {
      return Math.abs(blockPos.getX() - blockPos2.getX()) <= 4 && Math.abs(blockPos.getZ() - blockPos2.getZ()) <= 4;
   }

   private void invoke5() {
      this.items.removeIf(blockPos -> !this.check16(CLIENT.world.getBlockState(blockPos)) || !this.check19(blockPos));
      if (this.items.isEmpty()) {
         this.appleFarmerState = AppleFarmer.AppleFarmerState.PLACE;
         this.blockPos2 = null;
      } else {
         BlockPos blockPos10 = this.resolve10();
         if (blockPos10 == null) {
            this.appleFarmerState = AppleFarmer.AppleFarmerState.SCAN_TREE;
            this.blockPos2 = null;
            this.intValue = 0;
         } else {
            BlockState blockState6 = CLIENT.world.getBlockState(blockPos10);
            BlockHitResult blockHitResult = this.resolve11(blockPos10);
            if (blockHitResult == null) {
               this.blockPos2 = null;
            } else {
               boolean flag = this.check18(blockState6);
               boolean flag2 = this.check17(blockState6);
               if (flag2) {
                  this.invoke28(true);
               } else if (flag) {
                  this.invoke28(false);
               }

               Rotation rotation = this.resolve9(blockHitResult.getPos());
               RotationController.invoke3(rotation, 65.0F, 65.0F, 65.0F, 65.0F, 2, 20, false);
               if (!(new Rotation(CLIENT.player).measure(rotation) > 6.0F)) {
                  if (!blockPos10.equals(this.blockPos2)) {
                     CLIENT.interactionManager.attackBlock(blockPos10, blockHitResult.getSide());
                     this.blockPos2 = blockPos10;
                  } else {
                     CLIENT.interactionManager.updateBlockBreakingProgress(blockPos10, blockHitResult.getSide());
                  }

                  CLIENT.player.swingHand(Hand.MAIN_HAND);
               }
            }
         }
      }
   }

   private AppleFarmer.AppleFarmerState3 resolve() {
      if (this.check2()) {
         return AppleFarmer.AppleFarmerState3.UNLOAD;
      } else if (this.chinitPriProchnosti.getValue() > 0.0F && this.compute3() != -1) {
         return AppleFarmer.AppleFarmerState3.REPAIR;
      } else {
         if (this.appleFarmerState == AppleFarmer.AppleFarmerState.FIND_SPOT) {
            if (this.compute4(Items.BONE_MEAL) == 0) {
               return AppleFarmer.AppleFarmerState3.BONEMEAL;
            }

            if (this.compute4(Items.OAK_SAPLING) == 0) {
               return AppleFarmer.AppleFarmerState3.SAPLING;
            }
         }

         return AppleFarmer.AppleFarmerState3.NONE;
      }
   }

   private boolean check2() {
      if (this.flag5) {
         if (!this.dualTimer6.check5(30000L)) {
            return false;
         }

         this.flag5 = false;
      }

      return this.compute() <= (int)this.razgruzkaPriSvobodnyhSlotah.getValue() && this.check3();
   }

   private int compute() {
      int intValue9 = 0;

      for (int intValue10 = 0; intValue10 < 36; intValue10++) {
         if (CLIENT.player.getInventory().getStack(intValue10).isEmpty()) {
            intValue9++;
         }
      }

      return intValue9;
   }

   private boolean check3() {
      return this.compute4(Items.OAK_LOG) > 0 || this.compute4(Items.APPLE) > 0 || this.compute4(Items.STICK) > 0 || this.compute4(Items.OAK_SAPLING) > 64;
   }

   private void invoke6() {
      this.flag2 = false;
      this.flag3 = false;
      this.intValue3 = 0;
      this.blockPos4 = null;
      this.flag4 = false;
      this.intValue5 = -1;
      this.queue.clear();
      this.blockPos2 = null;
      this.items.clear();
      this.dualTimer.invoke();
      this.dualTimer2.invoke();
      this.dualTimer3.invoke();
      this.dualTimer4.invoke();
      switch (this.appleFarmerState3) {
         case REPAIR:
            this.appleFarmerState2 = this.compute4(Items.EXPERIENCE_BOTTLE) > 0 ? AppleFarmer.AppleFarmerState2.REPAIRING : AppleFarmer.AppleFarmerState2.FIND_CHEST;
            break;
         case BONEMEAL:
            this.appleFarmerState2 = this.check9() ? AppleFarmer.AppleFarmerState2.CRAFTING : AppleFarmer.AppleFarmerState2.FIND_CHEST;
            break;
         default:
            this.appleFarmerState2 = AppleFarmer.AppleFarmerState2.FIND_CHEST;
      }
   }

   private void invoke7() {
      if (CLIENT.currentScreen == null || CLIENT.currentScreen instanceof GenericContainerScreen) {
         switch (this.appleFarmerState2) {
            case FIND_CHEST:
               this.invoke8();
               break;
            case GOING:
               this.invoke9();
               break;
            case ROTATING:
               this.invoke10();
               break;
            case OPENING:
               this.invoke11();
               break;
            case WAIT_GUI:
               this.invoke12();
               break;
            case CRAFTING:
               this.invoke17();
               break;
            case REPAIRING:
               this.invoke19();
               break;
            case RETURNING:
               this.invoke21();
               break;
            case FACING:
               this.invoke22();
               break;
            default:
               this.invoke24();
         }
      }
   }

   private void invoke8() {
      this.blockPos4 = this.resolve3(this.appleFarmerState3);
      if (this.blockPos4 == null) {
         this.invoke25(
            "§c[AppleFarmer] §fНе найден сундук «" + this.resolve6(this.appleFarmerState3) + "» в радиусе " + (int)this.radiusPoiskaSundukov.getValue() + " бл."
         );
      } else {
         if (this.check19(this.blockPos4) && this.check12(this.blockPos4)) {
            this.appleFarmerState2 = AppleFarmer.AppleFarmerState2.ROTATING;
            this.dualTimer.invoke();
         } else {
            this.flag2 = true;
            this.appleFarmerState2 = AppleFarmer.AppleFarmerState2.GOING;
            this.dualTimer2.invoke();
            this.dualTimer3.invoke();
         }
      }
   }

   private void invoke9() {
      if (this.blockPos4 != null && this.check11(this.blockPos4)) {
         double doubleValue2 = CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(this.blockPos4));
         if (doubleValue2 <= this.distantsiya.getValue() && this.check12(this.blockPos4)) {
            if (this.iBaritone != null) {
               this.iBaritone.getPathingBehavior().cancelEverything();
            }

            this.appleFarmerState2 = AppleFarmer.AppleFarmerState2.ROTATING;
            this.dualTimer.invoke();
         } else {
            if (this.iBaritone != null && (!this.iBaritone.getCustomGoalProcess().isActive() || this.dualTimer2.check5(1500L))) {
               this.iBaritone.getCustomGoalProcess().setGoalAndPath(new GoalNear(this.blockPos4, 2));
               this.dualTimer2.invoke();
            }

            if (this.dualTimer3.check5(15000L)) {
               this.invoke25("§c[AppleFarmer] §fНе удалось дойти до сундука «" + this.resolve6(this.appleFarmerState3) + "»");
            }
         }
      } else {
         this.appleFarmerState2 = AppleFarmer.AppleFarmerState2.FIND_CHEST;
      }
   }

   private void invoke10() {
      if (this.blockPos4 == null) {
         this.appleFarmerState2 = AppleFarmer.AppleFarmerState2.FIND_CHEST;
      } else {
         if (this.check13(this.blockPos4)) {
            this.appleFarmerState2 = AppleFarmer.AppleFarmerState2.OPENING;
            this.dualTimer.invoke();
         }
      }
   }

   private void invoke11() {
      if (this.dualTimer.check5(200L)) {
         this.invoke27(this.blockPos4);
         this.appleFarmerState2 = AppleFarmer.AppleFarmerState2.WAIT_GUI;
         this.dualTimer.invoke();
      }
   }

   private void invoke12() {
      if (!(CLIENT.currentScreen instanceof GenericContainerScreen)) {
         if (this.dualTimer.check5(2500L)) {
            this.intValue3++;
            if (this.intValue3 > 3) {
               this.invoke25("§c[AppleFarmer] §fНе удалось открыть сундук «" + this.resolve6(this.appleFarmerState3) + "»");
            } else {
               this.appleFarmerState2 = AppleFarmer.AppleFarmerState2.ROTATING;
               this.dualTimer.invoke();
            }
         }
      }
   }

   private void invoke13(GenericContainerScreen genericContainerScreen) {
      GenericContainerScreenHandler genericContainerScreenHandler2 = (GenericContainerScreenHandler)genericContainerScreen.getScreenHandler();
      int intValue11 = genericContainerScreenHandler2.slots.size() - 36;
      if (intValue11 <= 0) {
         this.invoke16("§c[AppleFarmer] §fСундук пуст");
      } else if (this.dualTimer4.check5(120L)) {
         if (this.check6(this.appleFarmerState3)) {
            this.invoke15();
         } else {
            int intValue12 = this.compute2(genericContainerScreenHandler2, intValue11, this.appleFarmerState3);
            if (intValue12 == -1) {
               if (this.flag3) {
                  this.invoke15();
               } else {
                  this.invoke16("§c[AppleFarmer] §fВ сундуке «" + this.resolve6(this.appleFarmerState3) + "» нет нужных предметов");
               }
            } else {
               CLIENT.interactionManager.clickSlot(genericContainerScreenHandler2.syncId, intValue12, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
               this.flag3 = true;
               this.dualTimer4.invoke();
            }
         }
      }
   }

   private void invoke14(GenericContainerScreen genericContainerScreen) {
      GenericContainerScreenHandler genericContainerScreenHandler3 = (GenericContainerScreenHandler)genericContainerScreen.getScreenHandler();
      int intValue13 = genericContainerScreenHandler3.slots.size() - 36;
      if (intValue13 <= 0) {
         this.invoke16("§c[AppleFarmer] §fСундук пуст");
      } else if (this.dualTimer4.check5(120L)) {
         for (int intValue14 = intValue13; intValue14 < genericContainerScreenHandler3.slots.size(); intValue14++) {
            ItemStack itemStack2 = ((Slot)genericContainerScreenHandler3.slots.get(intValue14)).getStack();
            if (this.check4(itemStack2) && this.check5(genericContainerScreenHandler3, intValue13, itemStack2)) {
               CLIENT.interactionManager.clickSlot(genericContainerScreenHandler3.syncId, intValue14, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
               this.flag3 = true;
               this.dualTimer4.invoke();
               return;
            }
         }

         if (!this.flag3) {
            this.flag5 = true;
            this.dualTimer6.invoke();
            ChatUtil.sendClientMessage("§c[AppleFarmer] §fСундук «яблоки» переполнен — некуда разгружать");
         }

         if (CLIENT.player != null) {
            CLIENT.player.closeHandledScreen();
         }

         this.invoke23();
      }
   }

   private boolean check4(ItemStack itemStack) {
      if (itemStack.isEmpty()) {
         return false;
      } else {
         Item item2 = itemStack.getItem();
         if (item2 == Items.OAK_LOG || item2 == Items.APPLE || item2 == Items.STICK) {
            return true;
         } else {
            return item2 == Items.OAK_SAPLING ? this.compute4(Items.OAK_SAPLING) > 64 : false;
         }
      }
   }

   private boolean check5(GenericContainerScreenHandler genericContainerScreenHandler, int i, ItemStack itemStack) {
      for (int intValue15 = 0; intValue15 < i; intValue15++) {
         ItemStack itemStack3 = ((Slot)genericContainerScreenHandler.slots.get(intValue15)).getStack();
         if (itemStack3.isEmpty()) {
            return true;
         }

         if (itemStack3.getItem() == itemStack.getItem() && itemStack3.getCount() < itemStack3.getMaxCount()) {
            return true;
         }
      }

      return false;
   }

   private boolean check6(AppleFarmer.AppleFarmerState3 appleFarmerState32) {
      return switch (appleFarmerState32) {
         case REPAIR -> this.compute4(Items.EXPERIENCE_BOTTLE) >= 64;
         case BONEMEAL -> this.compute5() >= 128;
         case SAPLING -> this.compute4(Items.OAK_SAPLING) >= 64;
         default -> true;
      };
   }

   private int compute2(GenericContainerScreenHandler genericContainerScreenHandler, int i, AppleFarmer.AppleFarmerState3 appleFarmerState33) {
      for (int intValue16 = 0; intValue16 < i; intValue16++) {
         ItemStack itemStack4 = ((Slot)genericContainerScreenHandler.slots.get(intValue16)).getStack();
         if (!itemStack4.isEmpty() && this.check7(itemStack4.getItem(), appleFarmerState33)) {
            return intValue16;
         }
      }

      return -1;
   }

   private boolean check7(Item item, AppleFarmer.AppleFarmerState3 appleFarmerState34) {
      return switch (appleFarmerState34) {
         case REPAIR -> item == Items.EXPERIENCE_BOTTLE;
         case BONEMEAL -> item == Items.BONE_MEAL || item == Items.BONE || item == Items.BONE_BLOCK;
         case SAPLING -> item == Items.OAK_SAPLING;
         default -> false;
      };
   }

   private void invoke15() {
      if (CLIENT.player != null) {
         CLIENT.player.closeHandledScreen();
      }

      AppleFarmer.AppleFarmerState2 appleFarmerState2 = this.resolve2(this.appleFarmerState3);
      this.appleFarmerState2 = appleFarmerState2;
      if (appleFarmerState2 == AppleFarmer.AppleFarmerState2.RETURNING) {
         this.dualTimer2.invoke();
         this.dualTimer3.invoke();
      }

      this.dualTimer.invoke();
      this.dualTimer4.invoke();
      this.queue.clear();
   }

   private AppleFarmer.AppleFarmerState2 resolve2(AppleFarmer.AppleFarmerState3 appleFarmerState35) {
      return switch (appleFarmerState35) {
         case REPAIR -> AppleFarmer.AppleFarmerState2.REPAIRING;
         case BONEMEAL -> AppleFarmer.AppleFarmerState2.CRAFTING;
         default -> AppleFarmer.AppleFarmerState2.RETURNING;
      };
   }

   private void invoke16(String string) {
      if (CLIENT.player != null) {
         CLIENT.player.closeHandledScreen();
      }

      this.invoke25(string);
   }

   private void invoke17() {
      if (CLIENT.currentScreen == null) {
         if (!this.queue.isEmpty()) {
            if (this.dualTimer4.check5(90L)) {
               this.queue.poll().run();
               this.dualTimer4.invoke();
            }
         } else if (this.compute4(Items.BONE_MEAL) >= 128) {
            this.invoke23();
         } else {
            int intValue17 = this.compute6();
            if (intValue17 == -1) {
               this.invoke23();
            } else {
               int intValue18 = CLIENT.player.playerScreenHandler.syncId;
               this.queue.add(() -> CLIENT.interactionManager.clickSlot(intValue18, intValue17, 0, SlotActionType.PICKUP, CLIENT.player));
               this.queue.add(() -> CLIENT.interactionManager.clickSlot(intValue18, 1, 0, SlotActionType.PICKUP, CLIENT.player));
               this.queue.add(() -> CLIENT.interactionManager.clickSlot(intValue18, 0, 0, SlotActionType.QUICK_MOVE, CLIENT.player));
               this.queue.add(this::invoke18);
            }
         }
      }
   }

   private void invoke18() {
      int intValue19 = CLIENT.player.playerScreenHandler.syncId;

      for (int intValue20 = 1; intValue20 <= 4; intValue20++) {
         if (((Slot)CLIENT.player.playerScreenHandler.slots.get(intValue20)).hasStack()) {
            CLIENT.interactionManager.clickSlot(intValue19, intValue20, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
         }
      }

      if (!CLIENT.player.playerScreenHandler.getCursorStack().isEmpty()) {
         int intValue21 = this.compute7();
         if (intValue21 != -1) {
            CLIENT.interactionManager.clickSlot(intValue19, intValue21, 0, SlotActionType.PICKUP, CLIENT.player);
         }
      }
   }

   private void invoke19() {
      if (CLIENT.currentScreen == null) {
         int intValue22 = CLIENT.player.playerScreenHandler.syncId;
         if (!this.flag4) {
            int intValue23 = this.compute3();
            if (intValue23 == -1) {
               this.invoke23();
            } else if (this.compute4(Items.EXPERIENCE_BOTTLE) == 0) {
               this.appleFarmerState2 = AppleFarmer.AppleFarmerState2.FIND_CHEST;
            } else if (!CLIENT.player.getOffHandStack().isEmpty()) {
               int intValue24 = this.compute7();
               if (intValue24 == -1) {
                  this.invoke25("§c[AppleFarmer] §fОсвободите офф-хенд или место в инвентаре для починки");
               } else {
                  CLIENT.interactionManager.clickSlot(intValue22, 45, 0, SlotActionType.PICKUP, CLIENT.player);
                  CLIENT.interactionManager.clickSlot(intValue22, intValue24, 0, SlotActionType.PICKUP, CLIENT.player);
               }
            } else {
               this.intValue4 = intValue23;
               this.floatValue = CLIENT.player.getPitch();
               CLIENT.player.getInventory().setSelectedSlot(intValue23);
               CLIENT.interactionManager.clickSlot(intValue22, 45, intValue23, SlotActionType.SWAP, CLIENT.player);
               if (!this.check8()) {
                  CLIENT.interactionManager.clickSlot(intValue22, 45, intValue23, SlotActionType.SWAP, CLIENT.player);
                  CLIENT.player.getInventory().setSelectedSlot(intValue23);
                  this.appleFarmerState2 = AppleFarmer.AppleFarmerState2.FIND_CHEST;
               } else {
                  this.flag4 = true;
                  this.intValue5 = -1;
                  this.dualTimer5.invoke();
                  this.dualTimer.invoke();
               }
            }
         } else {
            ItemStack itemStack5 = CLIENT.player.getOffHandStack();
            if (!itemStack5.isEmpty() && itemStack5.isDamageable() && itemStack5.getDamage() != 0) {
               if (CLIENT.player.getMainHandStack().getItem() != Items.EXPERIENCE_BOTTLE && !this.check8()) {
                  this.invoke20(intValue22);
                  this.appleFarmerState2 = AppleFarmer.AppleFarmerState2.FIND_CHEST;
               } else {
                  int intValue25 = itemStack5.getDamage();
                  if (this.intValue5 == -1) {
                     this.intValue5 = intValue25;
                  }

                  if (intValue25 < this.intValue5) {
                     this.intValue5 = intValue25;
                     this.dualTimer5.invoke();
                  } else if (this.dualTimer5.check5(4000L)) {
                     this.invoke20(intValue22);
                     this.invoke25("§c[AppleFarmer] §fИнструмент не чинится (нет «Починки»?)");
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
               this.invoke20(intValue22);
            }
         }
      }
   }

   private void invoke20(int i) {
      CLIENT.interactionManager.clickSlot(i, 45, this.intValue4, SlotActionType.SWAP, CLIENT.player);
      if (this.intValue4 >= 0) {
         CLIENT.player.getInventory().setSelectedSlot(this.intValue4);
      }

      CLIENT.player.setPitch(this.floatValue);
      if (!CLIENT.player.getOffHandStack().isEmpty()) {
         int intValue26 = this.compute7();
         if (intValue26 != -1) {
            CLIENT.interactionManager.clickSlot(i, 45, 0, SlotActionType.PICKUP, CLIENT.player);
            CLIENT.interactionManager.clickSlot(i, intValue26, 0, SlotActionType.PICKUP, CLIENT.player);
         }
      }

      this.flag4 = false;
   }

   private boolean check8() {
      int intValue27 = this.compute8();
      if (intValue27 == -1) {
         return false;
      } else {
         if (intValue27 >= 36 && intValue27 <= 44) {
            CLIENT.player.getInventory().setSelectedSlot(intValue27 - 36);
         } else {
            CLIENT.interactionManager
               .clickSlot(
                  CLIENT.player.playerScreenHandler.syncId,
                  intValue27,
                  CLIENT.player.getInventory().getSelectedSlot(),
                  SlotActionType.SWAP,
                  CLIENT.player
               );
         }

         return true;
      }
   }

   private void invoke21() {
      if (this.flag2 && this.blockPos3 != null && this.iBaritone != null) {
         if (!CLIENT.player.getBlockPos().equals(this.blockPos3) && !(CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(this.blockPos3)) <= 0.7)
            )
          {
            if (!this.iBaritone.getCustomGoalProcess().isActive() || this.dualTimer2.check5(1500L)) {
               this.iBaritone.getCustomGoalProcess().setGoalAndPath(new GoalBlock(this.blockPos3));
               this.dualTimer2.invoke();
            }

            if (this.dualTimer3.check5(20000L)) {
               this.iBaritone.getPathingBehavior().cancelEverything();
               this.appleFarmerState2 = AppleFarmer.AppleFarmerState2.FACING;
            }
         } else {
            this.iBaritone.getPathingBehavior().cancelEverything();
            this.appleFarmerState2 = AppleFarmer.AppleFarmerState2.FACING;
            this.dualTimer.invoke();
         }
      } else {
         this.appleFarmerState2 = AppleFarmer.AppleFarmerState2.FACING;
      }
   }

   private void invoke22() {
      if (this.flag) {
         CLIENT.player.setYaw(this.measure(this.direction));
         CLIENT.player.setPitch(0.0F);
      }

      this.invoke24();
   }

   private float measure(Direction direction) {
      return switch (direction) {
         case SOUTH -> 0.0F;
         case WEST -> 90.0F;
         case NORTH -> 180.0F;
         case EAST -> -90.0F;
         default -> CLIENT.player.getYaw();
      };
   }

   private void invoke23() {
      this.appleFarmerState2 = AppleFarmer.AppleFarmerState2.RETURNING;
      this.dualTimer2.invoke();
      this.dualTimer3.invoke();
   }

   private void invoke24() {
      if (this.iBaritone != null) {
         this.iBaritone.getPathingBehavior().cancelEverything();
      }

      this.invoke26();
      this.appleFarmerState = AppleFarmer.AppleFarmerState.FIND_SPOT;
      this.intValue = 0;
      this.blockPos2 = null;
      this.items.clear();
   }

   private void invoke25(String string) {
      ChatUtil.sendClientMessage(string);
      if (this.iBaritone != null) {
         this.iBaritone.getPathingBehavior().cancelEverything();
      }

      this.invoke26();
      this.toggle();
   }

   private void invoke26() {
      this.appleFarmerState3 = AppleFarmer.AppleFarmerState3.NONE;
      this.appleFarmerState2 = AppleFarmer.AppleFarmerState2.FIND_CHEST;
      this.blockPos4 = null;
      this.flag2 = false;
      this.flag3 = false;
      this.intValue3 = 0;
      this.flag4 = false;
      this.intValue4 = -1;
      this.intValue5 = -1;
      this.queue.clear();
   }

   private int compute3() {
      for (int intValue28 = 0; intValue28 < 9; intValue28++) {
         ItemStack itemStack6 = CLIENT.player.getInventory().getStack(intValue28);
         if (!itemStack6.isEmpty() && itemStack6.isDamageable() && (itemStack6.getItem() instanceof AxeItem || itemStack6.getItem() instanceof HoeItem)) {
            int intValue29 = itemStack6.getMaxDamage() - itemStack6.getDamage();
            if (intValue29 <= (int)this.chinitPriProchnosti.getValue()) {
               return intValue28;
            }
         }
      }

      return -1;
   }

   private int compute4(Item item) {
      int intValue30 = 0;

      for (int intValue31 = 0; intValue31 < 36; intValue31++) {
         ItemStack itemStack7 = CLIENT.player.getInventory().getStack(intValue31);
         if (itemStack7.getItem() == item) {
            intValue30 += itemStack7.getCount();
         }
      }

      return intValue30;
   }

   private int compute5() {
      return this.compute4(Items.BONE_MEAL) + this.compute4(Items.BONE) * 3 + this.compute4(Items.BONE_BLOCK) * 9;
   }

   private boolean check9() {
      return this.compute4(Items.BONE) > 0 || this.compute4(Items.BONE_BLOCK) > 0;
   }

   private int compute6() {
      for (int intValue32 = 9; intValue32 <= 44; intValue32++) {
         Item item3 = ((Slot)CLIENT.player.playerScreenHandler.slots.get(intValue32)).getStack().getItem();
         if (item3 == Items.BONE || item3 == Items.BONE_BLOCK) {
            return intValue32;
         }
      }

      return -1;
   }

   private int compute7() {
      for (int intValue33 = 9; intValue33 <= 44; intValue33++) {
         if (!((Slot)CLIENT.player.playerScreenHandler.slots.get(intValue33)).hasStack()) {
            return intValue33;
         }
      }

      return -1;
   }

   private int compute8() {
      for (int intValue34 = 9; intValue34 <= 44; intValue34++) {
         if (((Slot)CLIENT.player.playerScreenHandler.slots.get(intValue34)).getStack().getItem() == Items.EXPERIENCE_BOTTLE) {
            return intValue34;
         }
      }

      return -1;
   }

   private BlockPos resolve3(AppleFarmer.AppleFarmerState3 appleFarmerState36) {
      if (CLIENT.world != null && CLIENT.player != null) {
         BlockPos blockPos11 = this.flag && this.blockPos3 != null ? this.blockPos3 : CLIENT.player.getBlockPos();
         int intValue35 = (int)this.radiusPoiskaSundukov.getValue();
         BlockPos blockPos12 = null;
         double doubleValue3 = Double.MAX_VALUE;

         for (BlockPos blockPos13 : BlockPos.iterate(blockPos11.add(-intValue35, -5, -intValue35), blockPos11.add(intValue35, 5, intValue35))) {
            if (this.check11(blockPos13) && this.check10(blockPos13, appleFarmerState36)) {
               double doubleValue4 = CLIENT.player.getPos().distanceTo(Vec3d.ofCenter(blockPos13));
               if (doubleValue4 < doubleValue3) {
                  doubleValue3 = doubleValue4;
                  blockPos12 = blockPos13.toImmutable();
               }
            }
         }

         return blockPos12;
      } else {
         return null;
      }
   }

   private boolean check10(BlockPos blockPos, AppleFarmer.AppleFarmerState3 appleFarmerState37) {
      String text = this.resolve4(blockPos).toLowerCase(Locale.ROOT);
      if (text.isEmpty()) {
         return false;
      } else {
         String text2;
         String[] texts;
         switch (appleFarmerState37) {
            case REPAIR:
               text2 = "опыт";
               texts = new String[]{"кост", "яблок"};
               break;
            case BONEMEAL:
               text2 = "кост";
               texts = new String[]{"опыт", "яблок"};
               break;
            case SAPLING:
            case UNLOAD:
               text2 = "яблок";
               texts = new String[]{"опыт", "кост"};
               break;
            default:
               return false;
         }

         if (!text.contains(text2)) {
            return false;
         } else {
            for (String text3 : texts) {
               if (text.contains(text3)) {
                  return false;
               }
            }

            return true;
         }
      }
   }

   private String resolve4(BlockPos blockPos) {
      if (blockPos != null && CLIENT.world != null) {
         SignBlockEntity signBlockEntity2 = null;
         double doubleValue5 = Double.MAX_VALUE;
         BlockPos blockPos14 = blockPos.add(-1, -1, -1);
         BlockPos blockPos15 = blockPos.add(1, 1, 1);

         for (BlockPos blockPos16 : BlockPos.iterate(blockPos14, blockPos15)) {
            if (CLIENT.world.getBlockEntity(blockPos16) instanceof SignBlockEntity signBlockEntity3) {
               double doubleValue6 = blockPos16.getSquaredDistance(blockPos);
               if (doubleValue6 < doubleValue5) {
                  doubleValue5 = doubleValue6;
                  signBlockEntity2 = signBlockEntity3;
               }
            }
         }

         return signBlockEntity2 == null ? "" : this.resolve5(signBlockEntity2);
      } else {
         return "";
      }
   }

   private String resolve5(SignBlockEntity signBlockEntity) {
      StringBuilder stringBuilder = new StringBuilder();

      for (Text text4 : signBlockEntity.getFrontText().getMessages(false)) {
         stringBuilder.append(text4.getString()).append(' ');
      }

      for (Text text5 : signBlockEntity.getBackText().getMessages(false)) {
         stringBuilder.append(text5.getString()).append(' ');
      }

      return stringBuilder.toString().replaceAll("§.", "").trim();
   }

   private boolean check11(BlockPos blockPos) {
      if (CLIENT.world == null) {
         return false;
      } else {
         BlockEntity blockEntity = CLIENT.world.getBlockEntity(blockPos);
         return blockEntity instanceof ChestBlockEntity || blockEntity instanceof BarrelBlockEntity || blockEntity instanceof ShulkerBoxBlockEntity;
      }
   }

   private boolean check12(BlockPos blockPos) {
      return this.resolve11(blockPos) != null;
   }

   private String resolve6(AppleFarmer.AppleFarmerState3 appleFarmerState38) {
      return switch (appleFarmerState38) {
         case REPAIR -> "опыт";
         case BONEMEAL -> "кости";
         case SAPLING, UNLOAD -> "яблоки";
         default -> "";
      };
   }

   private void invoke27(BlockPos blockPos) {
      Vec3d vec3d2 = this.resolve7(blockPos, Direction.UP);
      BlockHitResult blockHitResult2 = new BlockHitResult(vec3d2, Direction.UP, blockPos, false);
      CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, blockHitResult2);
      CLIENT.player.swingHand(Hand.MAIN_HAND);
   }

   private boolean check13(BlockPos blockPos) {
      Rotation rotation2 = this.resolve9(this.resolve7(blockPos, Direction.UP));
      RotationController.invoke3(rotation2, 65.0F, 65.0F, 65.0F, 65.0F, 2, 20, false);
      return new Rotation(CLIENT.player).measure(rotation2) <= 6.0F;
   }

   private Vec3d resolve7(BlockPos blockPos, Direction direction) {
      return new Vec3d(
         blockPos.getX() + 0.5 + direction.getOffsetX() * 0.5,
         blockPos.getY() + 0.5 + direction.getOffsetY() * 0.5,
         blockPos.getZ() + 0.5 + direction.getOffsetZ() * 0.5
      );
   }

   private Rotation resolve8(BlockPos blockPos) {
      return this.resolve9(new Vec3d(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5));
   }

   private Rotation resolve9(Vec3d vec3d) {
      if (CLIENT.player == null) {
         return new Rotation(0.0F, 0.0F);
      } else {
         Vec3d vec3d3 = CLIENT.player.getEyePos();
         double doubleValue7 = vec3d.x - vec3d3.x;
         double doubleValue8 = vec3d.y - vec3d3.y;
         double doubleValue9 = vec3d.z - vec3d3.z;
         double doubleValue10 = Math.sqrt(doubleValue7 * doubleValue7 + doubleValue9 * doubleValue9);
         float floatValue = (float)Math.toDegrees(Math.atan2(-doubleValue7, doubleValue9));
         float floatValue2 = (float)(-Math.toDegrees(Math.atan2(doubleValue8, doubleValue10)));
         return new Rotation(floatValue, floatValue2);
      }
   }

   private boolean check14(BlockPos blockPos) {
      Block block = CLIENT.world.getBlockState(blockPos).getBlock();
      return block == Blocks.GRASS_BLOCK || block == Blocks.DIRT || block == Blocks.COARSE_DIRT || block == Blocks.PODZOL;
   }

   private boolean check15(BlockPos blockPos) {
      BlockState blockState7 = CLIENT.world.getBlockState(blockPos);
      return blockState7.isAir() || blockState7.isReplaceable();
   }

   private boolean check16(BlockState blockState) {
      return this.check17(blockState) || this.check18(blockState);
   }

   private boolean check17(BlockState blockState) {
      return blockState.getBlock() == Blocks.OAK_LOG;
   }

   private boolean check18(BlockState blockState) {
      return blockState.getBlock() == Blocks.OAK_LEAVES;
   }

   private boolean check19(BlockPos blockPos) {
      double doubleValue11 = Math.min(this.distantsiya.getValue(), 4.5F);
      return CLIENT.player.getEyePos().squaredDistanceTo(Vec3d.ofCenter(blockPos)) <= doubleValue11 * doubleValue11;
   }

   private int compute9(BlockPos blockPos, BlockPos blockPos2) {
      boolean flag3 = this.check17(CLIENT.world.getBlockState(blockPos));
      boolean flag4 = this.check17(CLIENT.world.getBlockState(blockPos2));
      if (flag3 != flag4) {
         return flag3 ? 1 : -1;
      } else if (flag3) {
         return Integer.compare(blockPos.getY(), blockPos2.getY());
      } else {
         Vec3d vec3d4 = CLIENT.player.getEyePos();
         double doubleValue12 = vec3d4.squaredDistanceTo(Vec3d.ofCenter(blockPos));
         double doubleValue13 = vec3d4.squaredDistanceTo(Vec3d.ofCenter(blockPos2));
         return Double.compare(doubleValue12, doubleValue13);
      }
   }

   private BlockPos resolve10() {
      for (BlockPos blockPos17 : this.items) {
         if (this.resolve11(blockPos17) != null) {
            return blockPos17;
         }
      }

      return null;
   }

   private BlockHitResult resolve11(BlockPos blockPos) {
      Vec3d vec3d5 = CLIENT.player.getEyePos();
      double[] doubleValues = new double[]{0.5, 0.2, 0.8};

      for (double doubleValue14 : doubleValues) {
         for (double doubleValue15 : doubleValues) {
            for (double doubleValue16 : doubleValues) {
               Vec3d vec3d6 = new Vec3d(blockPos.getX() + doubleValue14, blockPos.getY() + doubleValue15, blockPos.getZ() + doubleValue16);
               BlockHitResult blockHitResult3 = CLIENT.world.raycast(new RaycastContext(vec3d5, vec3d6, ShapeType.OUTLINE, FluidHandling.NONE, CLIENT.player));
               if (blockHitResult3.getType() == Type.BLOCK && blockHitResult3.getBlockPos().equals(blockPos)) {
                  return blockHitResult3;
               }
            }
         }
      }

      return null;
   }

   private int compute10(Item item) {
      for (int intValue36 = 0; intValue36 < 9; intValue36++) {
         if (CLIENT.player.getInventory().getStack(intValue36).getItem() == item) {
            return intValue36;
         }
      }

      return -1;
   }

   private void invoke28(boolean bl) {
      int intValue37 = -1;
      ItemStack itemStack8 = CLIENT.player.getMainHandStack();
      if (!bl || !(itemStack8.getItem() instanceof AxeItem)) {
         if (bl || !(itemStack8.getItem() instanceof HoeItem)) {
            for (int intValue38 = 0; intValue38 < 9; intValue38++) {
               ItemStack itemStack9 = CLIENT.player.getInventory().getStack(intValue38);
               if (!itemStack9.isEmpty()) {
                  if (bl && itemStack9.getItem() instanceof AxeItem) {
                     intValue37 = intValue38;
                     break;
                  }

                  if (!bl && itemStack9.getItem() instanceof HoeItem) {
                     intValue37 = intValue38;
                     break;
                  }
               }
            }

            if (intValue37 != -1) {
               CLIENT.player.getInventory().setSelectedSlot(intValue37);
            }
         }
      }
   }

   private int compute11(Item item) {
      int intValue39 = -1;

      for (int intValue40 = 9; intValue40 < 36; intValue40++) {
         if (CLIENT.player.getInventory().getStack(intValue40).getItem() == item) {
            intValue39 = intValue40;
            break;
         }
      }

      if (intValue39 == -1) {
         return -1;
      } else {
         int intValue41 = this.compute12();
         if (intValue41 == -1) {
            return -1;
         } else {
            CLIENT.interactionManager.clickSlot(CLIENT.player.playerScreenHandler.syncId, intValue39, intValue41, SlotActionType.SWAP, CLIENT.player);
            return intValue41;
         }
      }
   }

   private int compute12() {
      for (int intValue42 = 0; intValue42 < 9; intValue42++) {
         if (CLIENT.player.getInventory().getStack(intValue42).isEmpty()) {
            return intValue42;
         }
      }

      for (int intValue43 = 0; intValue43 < 9; intValue43++) {
         Item item4 = CLIENT.player.getInventory().getStack(intValue43).getItem();
         if (!(item4 instanceof AxeItem)
            && !(item4 instanceof HoeItem)
            && item4 != Items.OAK_SAPLING
            && item4 != Items.BONE_MEAL
            && item4 != Items.BONE
            && item4 != Items.BONE_BLOCK
            && item4 != Items.EXPERIENCE_BOTTLE) {
            return intValue43;
         }
      }

      return -1;
   }

   private void invoke29() {
      for (int intValue44 = 0; intValue44 < 9; intValue44++) {
         ItemStack itemStack10 = CLIENT.player.getInventory().getStack(intValue44);
         boolean flag5 = itemStack10.getItem() == Items.BONE_MEAL || itemStack10.getItem() == Items.OAK_SAPLING;
         if (flag5 && itemStack10.getCount() < 64) {
            int intValue45 = -1;
            int intValue46 = itemStack10.getCount();

            for (int intValue47 = 9; intValue47 < 36; intValue47++) {
               ItemStack itemStack11 = CLIENT.player.getInventory().getStack(intValue47);
               if (itemStack11.getItem() == itemStack10.getItem() && itemStack11.getCount() > intValue46) {
                  intValue45 = intValue47;
                  intValue46 = itemStack11.getCount();
                  if (intValue46 == 64) {
                     break;
                  }
               }
            }

            if (intValue45 != -1) {
               CLIENT.interactionManager.clickSlot(CLIENT.player.playerScreenHandler.syncId, intValue45, intValue44, SlotActionType.SWAP, CLIENT.player);
               this.intValue2 = 0;
               return;
            }
         }
      }
   }

   static enum AppleFarmerState {
      FIND_SPOT,
      PLACE,
      BONEMEAL,
      SCAN_TREE,
      BREAKING;
   }

   static enum AppleFarmerState2 {
      FIND_CHEST,
      GOING,
      ROTATING,
      OPENING,
      WAIT_GUI,
      CRAFTING,
      REPAIRING,
      RETURNING,
      FACING;
   }

   static enum AppleFarmerState3 {
      NONE,
      REPAIR,
      BONEMEAL,
      SAPLING,
      UNLOAD;
   }
}
