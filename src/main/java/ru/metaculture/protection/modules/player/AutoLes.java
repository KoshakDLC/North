package ru.metaculture.protection;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleAccess(
   usernames = {"lichoday"}
)
@ModuleRegister(
   name = "AutoLes",
   category = Category.Player,
   description = "Автоматически фармит для вас лес, и зарабатывает на ReallyWorld"
)
public class AutoLes extends Module {
   public final NumberSetting radius = new NumberSetting("Радиус", 4.0F, 1.0F, 6.0F, 0.5F, false);
   public final BooleanSetting mahatRukoy = new BooleanSetting("Махать рукой", true);
   public final BooleanSetting avtoSdacha = new BooleanSetting("Авто-сдача", true);
   public final BooleanSetting autopay = new BooleanSetting("AutoPay", false);
   public final TextSetting nikDlyaPerevodaDeneg = new TextSetting("Ник для перевода денег", "");
   public final NumberSetting kolVoMonet = new NumberSetting("Кол-во монет", 1000.0F, 500.0F, 25000.0F, 1000.0F, false)
      .setVisibilityCondition(() -> !this.autopay.isEnabled());
   public final NumberSetting raspisanieS = new NumberSetting("Расписание/с", 20.0F, 1.0F, 60.0F, 1.0F, false);
   private final Map<BlockPos, BlockState> valuesByKey = new ConcurrentHashMap<>();
   private final Map<BlockPos, Long> valuesByKey2 = new ConcurrentHashMap<>();
   private final Set<BlockPos> values = ConcurrentHashMap.newKeySet();
   private long timestamp = 0L;
   private long timestamp2 = 0L;
   private long timestamp3 = 0L;
   private BlockPos blockPos = null;

   public AutoLes() {
      this.addSettings(
         new Setting[]{
            this.radius, this.mahatRukoy, this.avtoSdacha, this.autopay, this.nikDlyaPerevodaDeneg, this.kolVoMonet, this.raspisanieS
         }
      );
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.invoke8();
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.invoke7();
      this.invoke8();
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null) {
         long longValue = System.currentTimeMillis();
         if (this.avtoSdacha.isEnabled() && (float)(longValue - this.timestamp) > this.raspisanieS.getValue() * 500.0F) {
            CLIENT.getNetworkHandler().sendChatCommand("sellwood");
            this.timestamp = longValue;
         }

         if (this.autopay.isEnabled() && (float)(longValue - this.timestamp2) > this.raspisanieS.getValue() * 500.0F + 200.0F) {
            CLIENT.getNetworkHandler().sendChatCommand("pay " + this.nikDlyaPerevodaDeneg.getValue() + " " + (int)this.kolVoMonet.getValue());
            this.timestamp2 = longValue;
         }

         this.invoke(longValue);
         this.invoke5(longValue);
      }
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (CLIENT.player != null && CLIENT.world != null) {
         if (packetEvent.getPacket() instanceof PlayerActionC2SPacket playerActionC2SPacket) {
            if (playerActionC2SPacket.getAction() == Action.STOP_DESTROY_BLOCK || playerActionC2SPacket.getAction() == Action.START_DESTROY_BLOCK) {
               this.invoke3(playerActionC2SPacket.getPos());
            }
         } else if (packetEvent.getPacket() instanceof PlayerInteractBlockC2SPacket playerInteractBlockC2SPacket) {
            if (CLIENT.player.getStackInHand(playerInteractBlockC2SPacket.getHand()).getItem() instanceof BlockItem) {
               BlockPos blockPos2 = playerInteractBlockC2SPacket.getBlockHitResult().getBlockPos().offset(playerInteractBlockC2SPacket.getBlockHitResult().getSide());
               this.values.add(blockPos2);
               this.valuesByKey.remove(blockPos2);
               this.valuesByKey2.remove(blockPos2);
            }
         } else if (packetEvent.getPacket() instanceof BlockUpdateS2CPacket blockUpdateS2CPacket2) {
            this.invoke4(packetEvent, blockUpdateS2CPacket2);
         }
      }
   }

   private void invoke(long l) {
      if (this.blockPos != null && (!this.check(this.blockPos) || !this.check2(this.blockPos))) {
         this.blockPos = null;
      }

      if (this.blockPos == null) {
         this.invoke2();
      }

      if (this.blockPos != null && l - this.timestamp3 > 0L) {
         CLIENT.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(Action.START_DESTROY_BLOCK, this.blockPos, Direction.UP));
         CLIENT.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(Action.STOP_DESTROY_BLOCK, this.blockPos, Direction.DOWN));
         this.timestamp3 = l;
      }
   }

   private void invoke2() {
      int intValue = (int)this.radius.getValue();
      BlockPos blockPos3 = CLIENT.player.getBlockPos();
      double doubleValue = Double.MAX_VALUE;
      BlockPos blockPos4 = null;

      for (BlockPos blockPos5 : BlockPos.iterate(blockPos3.add(-intValue, -intValue, -intValue), blockPos3.add(intValue, intValue, intValue))) {
         if (this.check(blockPos5)) {
            double doubleValue2 = CLIENT.player.squaredDistanceTo(blockPos5.toCenterPos());
            if (doubleValue2 <= intValue * intValue && doubleValue2 < doubleValue) {
               doubleValue = doubleValue2;
               blockPos4 = blockPos5.toImmutable();
            }
         }
      }

      this.blockPos = blockPos4;
   }

   private boolean check(BlockPos blockPos) {
      return CLIENT.world.getBlockState(blockPos).isIn(BlockTags.LOGS);
   }

   private boolean check2(BlockPos blockPos) {
      float floatValue = this.radius.getValue();
      return CLIENT.player.squaredDistanceTo(blockPos.toCenterPos()) <= floatValue * floatValue;
   }

   private void invoke3(BlockPos blockPos) {
      BlockState blockState2 = CLIENT.world.getBlockState(blockPos);
      if (!blockState2.isAir()) {
         this.valuesByKey.put(blockPos, blockState2);
         this.valuesByKey2.put(blockPos, System.currentTimeMillis());
         this.invoke6(blockPos, blockState2);
      }
   }

   private void invoke4(PacketEvent packetEvent2, BlockUpdateS2CPacket blockUpdateS2CPacket) {
      BlockPos blockPos6 = blockUpdateS2CPacket.getPos();
      BlockState blockState3 = blockUpdateS2CPacket.getState();
      if (this.valuesByKey.containsKey(blockPos6)) {
         BlockState blockState4 = this.valuesByKey.get(blockPos6);
         if (blockState3.isAir() || !blockState3.equals(blockState4)) {
            packetEvent2.invalidate();
            this.invoke6(blockPos6, blockState4);
         }
      } else if (this.values.contains(blockPos6) && blockState3.isAir()) {
         packetEvent2.invalidate();
         CLIENT.execute(() -> {
            if (CLIENT.world != null) {
               CLIENT.world.setBlockState(blockPos6, CLIENT.world.getBlockState(blockPos6), 0);
            }
         });
      }
   }

   private void invoke5(long l) {
      this.valuesByKey.forEach((blockPos, blockState) -> {
         BlockState blockState5 = CLIENT.world.getBlockState(blockPos);
         if (!blockState5.equals(blockState)) {
            CLIENT.world.setBlockState(blockPos, blockState, 0);
            if (!blockState5.isAir()) {
               this.valuesByKey2.put(blockPos, l);
            }
         }

         for (Direction direction : Direction.values()) {
            BlockPos blockPos7 = blockPos.offset(direction);
            if (this.valuesByKey.containsKey(blockPos7)) {
               BlockState blockState6 = this.valuesByKey.get(blockPos7);
               if (!CLIENT.world.getBlockState(blockPos7).equals(blockState6)) {
                  CLIENT.world.setBlockState(blockPos7, blockState6, 0);
               }
            }
         }
      });
      this.valuesByKey2.entrySet().removeIf(entry -> {
         if (l - entry.getValue() > 300000L) {
            this.valuesByKey.remove(entry.getKey());
            return true;
         } else {
            return false;
         }
      });
   }

   private void invoke6(BlockPos blockPos, BlockState blockState) {
      CLIENT.execute(() -> {
         if (CLIENT.world != null) {
            CLIENT.world.setBlockState(blockPos, blockState, 0);
         }
      });
   }

   private void invoke7() {
      if (CLIENT.world != null) {
         CLIENT.execute(() -> {
            for (BlockPos blockPos8 : this.valuesByKey.keySet()) {
               CLIENT.world.setBlockState(blockPos8, Blocks.AIR.getDefaultState(), 0);
            }
         });
      }
   }

   private void invoke8() {
      this.blockPos = null;
      this.timestamp = 0L;
      this.timestamp2 = 0L;
      this.timestamp3 = 0L;
      this.valuesByKey.clear();
      this.values.clear();
      this.valuesByKey2.clear();
   }
}
