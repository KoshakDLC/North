package ru.metaculture.protection;

import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.List;
import lombok.Generated;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;

public final class ServerBlockUtils implements MinecraftAccessor {
   public static boolean check() {
      return a_.player == null || a_.world == null;
   }

   public static boolean check2(double d, double e, double f) {
      BlockPos blockPos2 = BlockPos.ofFloored(d, e, f);
      return a_.world.getBlockState(blockPos2).isFullCube(a_.world, blockPos2);
   }

   public static Block resolve(BlockPos blockPos) {
      return a_.world.getBlockState(blockPos).getBlock();
   }

   public static float measure(float f) {
      double doubleValue = a_.player.getX() - a_.player.lastRenderX;
      double doubleValue2 = a_.player.getZ() - a_.player.lastRenderZ;
      float floatValue = (float)(doubleValue * doubleValue + doubleValue2 * doubleValue2);
      float floatValue2 = a_.player.lastBodyYaw;
      float floatValue3 = floatValue2;
      if (floatValue > 0.0025000002F) {
         floatValue3 = (float)MathHelper.atan2(doubleValue2, doubleValue) * 180.0F / (float) Math.PI - 90.0F;
      }

      if (a_.player != null && a_.player.handSwingProgress > 0.0F) {
      }

      float floatValue4 = MathHelper.wrapDegrees(f - (floatValue2 + MathHelper.wrapDegrees(floatValue3 - floatValue2) * 0.3F));
      floatValue4 = MathHelper.clamp(floatValue4, -50.0F, 50.0F);
      floatValue2 = f - floatValue4;
      if (floatValue4 * floatValue4 > 2500.0F) {
         floatValue2 += floatValue4 * 0.2F;
      }

      return floatValue2;
   }

   public static boolean check3() {
      Box box2 = a_.player.getBoundingBox();
      BlockPos blockPos3 = a_.player.getBlockPos();
      return resolve2(blockPos3).stream().anyMatch(blockPos -> check4(box2, blockPos));
   }

   private static boolean check4(Box box, BlockPos blockPos) {
      if (!a_.world.getBlockState(blockPos).isOf(Blocks.COBWEB)) {
         return false;
      } else {
         Box box3 = new Box(blockPos);
         return box.intersects(box3);
      }
   }

   private static List<BlockPos> resolve2(BlockPos blockPos) {
      ArrayList arrayList = new ArrayList();

      for (int intValue = blockPos.getX() - 2; intValue <= blockPos.getX() + 2; intValue++) {
         for (int intValue2 = blockPos.getY() - 1; intValue2 <= blockPos.getY() + 4; intValue2++) {
            for (int intValue3 = blockPos.getZ() - 2; intValue3 <= blockPos.getZ() + 2; intValue3++) {
               arrayList.add(new BlockPos(intValue, intValue2, intValue3));
            }
         }
      }

      return arrayList;
   }

   public static List<BlockPos> resolve3(BlockPos blockPos, float f, float g) {
      ArrayList arrayList2 = new ArrayList();
      int intValue4 = blockPos.getX();
      int intValue5 = blockPos.getY();
      int intValue6 = blockPos.getZ();

      for (int intValue7 = intValue4 - (int)f; intValue7 <= intValue4 + (int)f; intValue7++) {
         for (int intValue8 = intValue6 - (int)f; intValue8 <= intValue6 + (int)f; intValue8++) {
            for (int intValue9 = intValue5; intValue9 <= intValue5 + (int)g; intValue9++) {
               arrayList2.add(new BlockPos(intValue7, intValue9, intValue8));
            }
         }
      }

      return arrayList2;
   }

   public static boolean check5(Block block, BlockPos blockPos, float f, float g) {
      return resolve3(blockPos, f, g).stream().map(blockPosx -> a_.world.getBlockState(blockPosx).getBlock()).anyMatch(block2 -> block2.equals(block));
   }

   public static boolean check6(String string) {
      if (string == null || string.isEmpty()) {
         return false;
      } else if (!check() && a_.getNetworkHandler() != null) {
         String text = null;
         if (a_.getCurrentServerEntry() != null) {
            text = a_.getCurrentServerEntry().address;
         }

         if ((text == null || text.isEmpty()) && a_.getNetworkHandler().getConnection() != null) {
            SocketAddress socketAddress = a_.getNetworkHandler().getConnection().getAddress();
            if (socketAddress != null) {
               text = socketAddress.toString();
               if (text.startsWith("/")) {
                  text = text.substring(1);
               }
            }
         }

         if (a_.isConnectedToLocalServer()) {
            text = "localhost";
         }

         return text != null && !text.isEmpty() ? text.toLowerCase().contains(string.toLowerCase()) : false;
      } else {
         return false;
      }
   }

   @Generated
   private ServerBlockUtils() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }
}
