package ru.metaculture.protection;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleAccess(
   usernames = {"lichoday"}
)
@ModuleRegister(
   name = "ClanUpgrade",
   category = Category.Misc,
   description = "Прокачивает за вас клан",
   riskLevels = {ModuleRiskLevel.RISKY}
)
public class ClanUpgrade extends Module {
   private static final String FAKEL = "Факел";
   private static final String KRASNOY_PYLYU = "Красной пылью";
   private static final Item[] ITEMS = new Item[]{Items.TORCH, Items.REDSTONE_TORCH};
   private static final Item[] ITEMS_2 = new Item[]{Items.REDSTONE};
   private static final int INT_VALUE = 545;
   private static final int INT_VALUE_2 = 1;
   private static final float FLOAT_VALUE = -1170.1321F;
   private static final float FLOAT_VALUE_2 = 90.0F;
   private static final float FLOAT_VALUE_3 = 180.0F;
   private static final ClanUpgrade.ClanUpgradeData[] CLAN_UPGRADE_DATAS = new ClanUpgrade.ClanUpgradeData[]{
      new ClanUpgrade.ClanUpgradeData(7, 1, true),
      new ClanUpgrade.ClanUpgradeData(11, 0, true),
      new ClanUpgrade.ClanUpgradeData(28, 0, false),
      new ClanUpgrade.ClanUpgradeData(31, 0, true),
      new ClanUpgrade.ClanUpgradeData(32, 0, false),
      new ClanUpgrade.ClanUpgradeData(34, 0, true),
      new ClanUpgrade.ClanUpgradeData(539, 0, false),
      new ClanUpgrade.ClanUpgradeData(539, 1, false)
   };
   private final ModeSetting rezhim = new ModeSetting("Режим", "Красной пылью", "Факел", "Красной пылью");
   private final RotationResetController rotationResetController = new RotationResetController();
   private int intValue;
   private boolean flag;
   private boolean flag2;

   public ClanUpgrade() {
      this.addSettings(new Setting[]{this.rezhim});
   }

   @Override
   public void onEnable() {
      this.invoke11();
      super.onEnable();
   }

   @Override
   public void onDisable() {
      this.rotationResetController.invoke4();
      this.invoke9();
      this.invoke11();
      super.onDisable();
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null && CLIENT.interactionManager != null && CLIENT.getNetworkHandler() != null) {
         Item[] items2 = this.resolve();
         if (!this.check(items2)) {
            ChatUtil.sendClientMessage("§c[ClanUpgrade] §fНет предметов для режима: " + this.rezhim.getValue());
            this.invoke10();
         } else {
            BlockPos blockPos3 = CLIENT.player.getBlockPos().down();
            BlockState blockState2 = CLIENT.world.getBlockState(blockPos3);
            if (!blockState2.isReplaceable() && blockState2.getFluidState().isEmpty()) {
               this.invoke3();
               if (!this.check3()) {
                  this.invoke8();
               } else {
                  this.invoke7();
                  this.invoke4(blockPos3, blockPos3.up());
                  this.intValue++;
                  if (this.intValue >= 545) {
                     this.intValue = 0;
                     this.flag = false;
                     this.flag2 = false;
                  }
               }
            } else {
               this.invoke8();
            }
         }
      }
   }

   private Item[] resolve() {
      return this.rezhim.is("Факел") ? ITEMS : ITEMS_2;
   }

   private boolean check(Item[] items) {
      ItemStack itemStack2 = CLIENT.player.getInventory().getStack(1);
      if (this.check2(itemStack2, items)) {
         this.invoke2(1);
         return true;
      } else {
         int intValue = this.compute(items);
         if (intValue == -1) {
            return false;
         } else {
            this.invoke(intValue);
            this.invoke2(1);
            return this.check2(CLIENT.player.getInventory().getStack(1), items);
         }
      }
   }

   private int compute(Item[] items) {
      for (int intValue2 = 0; intValue2 < 36; intValue2++) {
         if (intValue2 != 1 && this.check2(CLIENT.player.getInventory().getStack(intValue2), items)) {
            return intValue2;
         }
      }

      return -1;
   }

   private boolean check2(ItemStack itemStack, Item[] items) {
      if (itemStack != null && !itemStack.isEmpty()) {
         for (Item item : items) {
            if (itemStack.isOf(item)) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private void invoke(int i) {
      if (i != 1) {
         int intValue3 = i < 9 ? 36 + i : i;
         CLIENT.interactionManager.clickSlot(CLIENT.player.playerScreenHandler.syncId, intValue3, 1, SlotActionType.SWAP, CLIENT.player);
      }
   }

   private void invoke2(int i) {
      if (i >= 0 && i <= 8) {
         if (CLIENT.player.getInventory().getSelectedSlot() != i) {
            CLIENT.player.getInventory().setSelectedSlot(i);
            CLIENT.getNetworkHandler().sendPacket(new UpdateSelectedSlotC2SPacket(i));
         }
      }
   }

   private void invoke3() {
      this.rotationResetController.invoke2(new Rotation(-1170.1321F, 90.0F), 180.0F, 180.0F, 1, 15);
   }

   private boolean check3() {
      float floatValue = Math.abs(MathHelper.wrapDegrees(-1170.1321F - CLIENT.player.getYaw()));
      float floatValue2 = Math.abs(90.0F - CLIENT.player.getPitch());
      return floatValue <= 1.0F && floatValue2 <= 1.0F;
   }

   private void invoke4(BlockPos blockPos, BlockPos blockPos2) {
      for (ClanUpgrade.ClanUpgradeData clanUpgradeData : CLAN_UPGRADE_DATAS) {
         if (clanUpgradeData.tick == this.intValue) {
            if (clanUpgradeData.button == 1) {
               this.flag2 = clanUpgradeData.press;
               CLIENT.options.useKey.setPressed(this.flag2);
               if (clanUpgradeData.press) {
                  this.invoke5(blockPos);
               }
            } else if (clanUpgradeData.button == 0) {
               this.flag = clanUpgradeData.press;
               CLIENT.options.attackKey.setPressed(this.flag);
               if (clanUpgradeData.press) {
                  this.invoke6(blockPos2);
               } else {
                  CLIENT.interactionManager.cancelBlockBreaking();
               }
            }
         }
      }
   }

   private void invoke5(BlockPos blockPos) {
      if (this.check2(CLIENT.player.getMainHandStack(), this.resolve())) {
         Vec3d vec3d = new Vec3d(blockPos.getX() + 0.5, blockPos.getY() + 1.0, blockPos.getZ() + 0.5);
         BlockHitResult blockHitResult = new BlockHitResult(vec3d, Direction.UP, blockPos, false);
         ActionResult actionResult = CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, blockHitResult);
         if (actionResult != ActionResult.PASS && actionResult != ActionResult.FAIL) {
            CLIENT.player.swingHand(Hand.MAIN_HAND);
         } else {
            actionResult = CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
            if (actionResult != ActionResult.PASS && actionResult != ActionResult.FAIL) {
               CLIENT.player.swingHand(Hand.MAIN_HAND);
            }
         }
      }
   }

   private void invoke6(BlockPos blockPos) {
      BlockState blockState3 = CLIENT.world.getBlockState(blockPos);
      if (this.check4(blockState3) || !blockState3.isReplaceable()) {
         CLIENT.interactionManager.attackBlock(blockPos, Direction.UP);
         CLIENT.player.swingHand(Hand.MAIN_HAND);
      }
   }

   private void invoke7() {
      this.invoke2(1);
      CLIENT.options.forwardKey.setPressed(false);
      CLIENT.options.backKey.setPressed(false);
      CLIENT.options.leftKey.setPressed(false);
      CLIENT.options.rightKey.setPressed(false);
      CLIENT.options.jumpKey.setPressed(false);
      CLIENT.options.sneakKey.setPressed(false);
      CLIENT.options.sprintKey.setPressed(false);
      CLIENT.options.useKey.setPressed(this.flag2);
      CLIENT.options.attackKey.setPressed(this.flag);
      if (CLIENT.player.isSprinting()) {
         CLIENT.player.setSprinting(false);
      }
   }

   private void invoke8() {
      this.flag = false;
      this.flag2 = false;
      this.invoke7();
   }

   private void invoke9() {
      if (CLIENT.options != null) {
         CLIENT.options.forwardKey.setPressed(false);
         CLIENT.options.backKey.setPressed(false);
         CLIENT.options.leftKey.setPressed(false);
         CLIENT.options.rightKey.setPressed(false);
         CLIENT.options.jumpKey.setPressed(false);
         CLIENT.options.sneakKey.setPressed(false);
         CLIENT.options.sprintKey.setPressed(false);
         CLIENT.options.useKey.setPressed(false);
         CLIENT.options.attackKey.setPressed(false);
      }
   }

   private boolean check4(BlockState blockState) {
      Block block = blockState.getBlock();
      return block == Blocks.REDSTONE_WIRE
         || block == Blocks.TORCH
         || block == Blocks.WALL_TORCH
         || block == Blocks.REDSTONE_TORCH
         || block == Blocks.REDSTONE_WALL_TORCH;
   }

   private void invoke10() {
      if (this.enabled) {
         this.toggle();
      }
   }

   private void invoke11() {
      this.intValue = 0;
      this.flag = false;
      this.flag2 = false;
   }

   record ClanUpgradeData(int tick, int button, boolean press) {
   }
}
