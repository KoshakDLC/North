package ru.metaculture.protection;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;

public class BlockInteractionUtils {
   public static MinecraftClient client = MinecraftClient.getInstance();

   public static void invoke(MovementInputEvent movementInputEvent, BlockPos blockPos, NumberSetting numberSetting, float f) {
      if (client.player != null && client.world != null && blockPos != null) {
         Vec3d vec3d = client.player.getPos();
         Vec3d vec3d2 = new Vec3d(blockPos.getX() + 0.5, client.player.getY(), blockPos.getZ() + 0.5);
         double doubleValue = vec3d.distanceTo(vec3d2);
         if (doubleValue <= numberSetting.getValue()) {
            movementInputEvent.setFloatValue(0.0F);
            movementInputEvent.setFloatValue2(0.0F);
            movementInputEvent.setFlag(false);
            movementInputEvent.setFlag2(false);
         } else {
            float floatValue = (float)Math.toDegrees(Math.atan2(vec3d2.z - vec3d.z, vec3d2.x - vec3d.x)) - 90.0F;
            BlockInteractionUtils.BlockInteractionUtilsData blockInteractionUtilsData = resolve(f, 1.2, 0.4);
            boolean flag = check5();
            movementInputEvent.setFlag2(flag);
            float floatValue2 = 0.0F;
            if (!flag && blockInteractionUtilsData.hitSolid) {
               float floatValue3 = RenderMath.measure27(floatValue - f);
               floatValue2 = floatValue3 > 0.0F ? -0.8F : 0.8F;
            }

            boolean flag2 = !flag && check(f);
            movementInputEvent.setFlag(flag2);
            invoke4(movementInputEvent, f, floatValue, floatValue2);
         }
      }
   }

   private static BlockInteractionUtils.BlockInteractionUtilsData resolve(float f, double d, double e) {
      if (client.player != null && client.world != null) {
         Vec3d vec3d3 = client.player.getEyePos().add(0.0, e, 0.0);
         double doubleValue2 = -Math.sin(Math.toRadians(f));
         double doubleValue3 = Math.cos(Math.toRadians(f));
         Vec3d vec3d4 = new Vec3d(doubleValue2, 0.0, doubleValue3).normalize();
         Vec3d vec3d5 = vec3d3.add(vec3d4.multiply(d));
         RaycastContext raycastContext = new RaycastContext(vec3d3, vec3d5, ShapeType.OUTLINE, FluidHandling.NONE, client.player);
         BlockHitResult blockHitResult = client.world.raycast(raycastContext);
         if (blockHitResult.getType() != Type.BLOCK) {
            return new BlockInteractionUtils.BlockInteractionUtilsData(false, BlockPos.ORIGIN);
         } else {
            BlockPos blockPos2 = blockHitResult.getBlockPos();
            BlockState blockState2 = client.world.getBlockState(blockPos2);
            boolean flag3 = !blockState2.isAir() && !blockState2.getCollisionShape(client.world, blockPos2).isEmpty();
            return new BlockInteractionUtils.BlockInteractionUtilsData(flag3, blockPos2);
         }
      } else {
         return new BlockInteractionUtils.BlockInteractionUtilsData(false, BlockPos.ORIGIN);
      }
   }

   public static boolean check(float f) {
      if (client.player == null || client.world == null) {
         return false;
      } else if (!client.player.isOnGround()) {
         return false;
      } else {
         Vec3d vec3d6 = client.player.getPos();
         double doubleValue4 = -Math.sin(Math.toRadians(f));
         double doubleValue5 = Math.cos(Math.toRadians(f));
         BlockPos blockPos3 = BlockPos.ofFloored(vec3d6);
         BlockPos blockPos4 = BlockPos.ofFloored(vec3d6.x + doubleValue4 * 0.8, vec3d6.y, vec3d6.z + doubleValue5 * 0.8);
         BlockPos blockPos5 = blockPos4.up();
         BlockState blockState3 = client.world.getBlockState(blockPos4);
         BlockState blockState4 = client.world.getBlockState(blockPos5);
         double doubleValue6 = blockPos4.getY() - blockPos3.getY();
         if (!(doubleValue6 < 0.6) && !(doubleValue6 > 1.25)) {
            boolean flag4 = !blockState3.isAir() && !blockState3.getCollisionShape(client.world, blockPos4).isEmpty();
            boolean flag5 = blockState4.isAir() || blockState4.getCollisionShape(client.world, blockPos5).isEmpty();
            if (flag4 && flag5) {
               BlockPos blockPos6 = blockPos4.add((int)Math.signum(doubleValue4), 0, (int)Math.signum(doubleValue5));
               BlockPos blockPos7 = blockPos6.down();
               BlockState blockState5 = client.world.getBlockState(blockPos7);
               return !blockState5.isAir() && !blockState5.getCollisionShape(client.world, blockPos7).isEmpty();
            } else {
               return false;
            }
         } else {
            return false;
         }
      }
   }

   public static void invoke2(BlockPos blockPos, NumberSetting numberSetting2, NumberSetting numberSetting3) {
      if (blockPos != null && client.player != null && client.world != null) {
         Vec3d vec3d7 = client.player.getEyePos();
         Vec3d vec3d8 = Vec3d.ofCenter(blockPos);
         Vec3d vec3d9 = vec3d8.subtract(vec3d7).normalize();
         float floatValue4 = (float)Math.toDegrees(Math.atan2(-vec3d9.x, vec3d9.z));
         float floatValue5 = (float)(-Math.toDegrees(Math.atan2(vec3d9.y, Math.sqrt(vec3d9.x * vec3d9.x + vec3d9.z * vec3d9.z))));
         long longValue = System.currentTimeMillis();
         float floatValue6 = numberSetting3.getValue();
         float floatValue7 = (float)Math.sin(longValue / 50.0) * floatValue6;
         float floatValue8 = (float)Math.cos(longValue / 40.0) * floatValue6 * 10.0F;
         float floatValue9 = (float)Math.sin(longValue / 40.0) * floatValue6 * 0.5F;
         float floatValue10 = floatValue7 + floatValue8;
         float floatValue11 = floatValue9 * 5.0F;
         Rotation rotation = new Rotation(floatValue4 + floatValue10, floatValue5 + floatValue11);
         RotationController.invoke3(rotation, numberSetting2.getValue(), numberSetting2.getValue(), 30.0F, 30.0F, 0, 18, false);
      }
   }

   public static void invoke3(BlockPos blockPos, NumberSetting numberSetting4, NumberSetting numberSetting5) {
      if (blockPos != null && client.player != null && client.world != null) {
         Vec3d vec3d10 = client.player.getEyePos();
         Vec3d vec3d11 = Vec3d.ofCenter(blockPos);
         Vec3d vec3d12 = vec3d11.subtract(vec3d10).normalize();
         float floatValue12 = (float)Math.toDegrees(Math.atan2(-vec3d12.x, vec3d12.z));
         float floatValue13 = (float)(-Math.toDegrees(Math.atan2(vec3d12.y, Math.sqrt(vec3d12.x * vec3d12.x + vec3d12.z * vec3d12.z))));
         long longValue2 = System.currentTimeMillis();
         float floatValue14 = (float)Math.sin(longValue2 / 200.0) * 0.8F;
         Rotation rotation2 = new Rotation(floatValue12 + floatValue14 * 0.3F, floatValue13 + floatValue14 * 0.2F);
         RotationController.invoke3(rotation2, numberSetting4.getValue() * 1.5F, numberSetting4.getValue() * 1.2F, 30.0F, 30.0F, 0, 10, false);
      }
   }

   public static boolean check2(BlockPos blockPos, float f) {
      if (blockPos != null && client.player != null && client.world != null) {
         Vec3d vec3d13 = client.player.getEyePos();
         Vec3d vec3d14 = Vec3d.ofCenter(blockPos);
         Vec3d vec3d15 = vec3d14.subtract(vec3d13).normalize();
         float floatValue15 = (float)Math.toDegrees(Math.atan2(-vec3d15.x, vec3d15.z));
         float floatValue16 = (float)(-Math.toDegrees(Math.atan2(vec3d15.y, Math.sqrt(vec3d15.x * vec3d15.x + vec3d15.z * vec3d15.z))));
         float floatValue17 = Math.abs(RenderMath.measure27(floatValue15 - client.player.getYaw()));
         float floatValue18 = Math.abs(RenderMath.measure27(floatValue16 - client.player.getPitch()));
         return floatValue17 <= f && floatValue18 <= f;
      } else {
         return false;
      }
   }

   public static boolean check3(BlockPos blockPos, long l, NumberSetting numberSetting6, NumberSetting numberSetting7) {
      if (blockPos != null && client.player != null && client.world != null) {
         double doubleValue7 = client.player.squaredDistanceTo(blockPos.getX() + 0.5, blockPos.getY() + 0.5, blockPos.getZ() + 0.5);
         if (doubleValue7 > numberSetting7.getValue() * numberSetting7.getValue()) {
            return false;
         } else if ((float)(System.currentTimeMillis() - l) < numberSetting6.getValue()) {
            return false;
         } else {
            client.interactionManager.attackBlock(blockPos, Direction.UP);
            client.player.swingHand(Hand.MAIN_HAND);
            return true;
         }
      } else {
         return false;
      }
   }

   public static boolean check4(BlockPos blockPos, Item item, long l) {
      if (blockPos != null && client.player != null && client.world != null && item != null) {
         if (System.currentTimeMillis() - l < 600L) {
            return false;
         } else {
            Hand hand = null;
            if (client.player.getOffHandStack().getItem() == item) {
               hand = Hand.OFF_HAND;
            } else if (client.player.getMainHandStack().getItem() == item) {
               hand = Hand.MAIN_HAND;
            }

            if (hand == null) {
               return false;
            } else {
               BlockPos blockPos8 = blockPos.up();
               if (!client.world.getBlockState(blockPos8).isReplaceable()) {
                  return false;
               } else {
                  Vec3d vec3d16 = Vec3d.ofCenter(blockPos).add(Vec3d.of(Direction.UP.getVector()).multiply(0.5));
                  BlockHitResult blockHitResult2 = new BlockHitResult(vec3d16, Direction.UP, blockPos, false);
                  client.interactionManager.interactBlock(client.player, hand, blockHitResult2);
                  client.player.swingHand(hand);
                  return true;
               }
            }
         }
      } else {
         return false;
      }
   }

   public static boolean check5() {
      if (client.player == null || client.world == null) {
         return false;
      } else if (client.player.isOnGround()) {
         return false;
      } else {
         Vec3d vec3d17 = client.player.getPos();
         BlockPos blockPos9 = BlockPos.ofFloored(vec3d17.x, vec3d17.y - 1.0, vec3d17.z);
         if (check6(blockPos9, 3)) {
            return true;
         } else {
            float floatValue19 = client.player.getYaw();
            double doubleValue8 = -Math.sin(Math.toRadians(floatValue19));
            double doubleValue9 = Math.cos(Math.toRadians(floatValue19));
            Vec3d vec3d18 = vec3d17.add(doubleValue8 * 0.8, 0.0, doubleValue9 * 0.8);
            BlockPos blockPos10 = BlockPos.ofFloored(vec3d18.x, vec3d18.y - 1.0, vec3d18.z);
            return check6(blockPos10, 3);
         }
      }
   }

   public static boolean check6(BlockPos blockPos, int i) {
      if (client.world == null) {
         return false;
      } else {
         int intValue = 0;

         for (int intValue2 = 0; intValue2 < i; intValue2++) {
            BlockPos blockPos11 = blockPos.down(intValue2);
            BlockState blockState6 = client.world.getBlockState(blockPos11);
            if (check7(blockState6, blockPos11) || !blockState6.isAir() && !blockState6.getCollisionShape(client.world, blockPos11).isEmpty()) {
               break;
            }

            intValue++;
         }

         return intValue >= i;
      }
   }

   private static boolean check7(BlockState blockState, BlockPos blockPos) {
      if (blockState.isAir()) {
         return false;
      } else {
         Block block = blockState.getBlock();
         if (block == Blocks.FARMLAND) {
            return true;
         } else if (block == Blocks.SOUL_SAND) {
            return true;
         } else {
            return block == Blocks.DIRT_PATH ? true : !blockState.getCollisionShape(client.world, blockPos).isEmpty();
         }
      }
   }

   private static void invoke4(MovementInputEvent movementInputEvent2, float f, float g, float h) {
      float floatValue20 = movementInputEvent2.getFloatValue();
      float floatValue21 = movementInputEvent2.getFloatValue2();
      double doubleValue10 = RenderMath.measure27((float)Math.toDegrees(measure(f, floatValue20, floatValue21)));
      if (floatValue20 == 0.0F && floatValue21 == 0.0F) {
         movementInputEvent2.setFloatValue(1.0F);
         movementInputEvent2.setFloatValue2(h);
      } else {
         float floatValue22 = 0.0F;
         float floatValue23 = 0.0F;
         float floatValue24 = Float.MAX_VALUE;

         for (float floatValue25 = -1.0F; floatValue25 <= 1.0F; floatValue25++) {
            for (float floatValue26 = -1.0F; floatValue26 <= 1.0F; floatValue26++) {
               if (floatValue25 != 0.0F || floatValue26 != 0.0F) {
                  double doubleValue11 = RenderMath.measure27((float)Math.toDegrees(measure(g, floatValue25, floatValue26)));
                  float floatValue27 = (float)Math.abs(doubleValue10 - doubleValue11);
                  if (floatValue27 < floatValue24) {
                     floatValue24 = floatValue27;
                     floatValue22 = floatValue25;
                     floatValue23 = floatValue26 + h;
                  }
               }
            }
         }

         movementInputEvent2.setFloatValue(floatValue22);
         movementInputEvent2.setFloatValue2(floatValue23);
      }
   }

   private static double measure(float f, float g, float h) {
      if (g == 0.0F && h == 0.0F) {
         return 0.0;
      } else {
         double doubleValue12 = Math.atan2(h, g);
         return doubleValue12 + Math.toRadians(f);
      }
   }

   record BlockInteractionUtilsData(boolean hitSolid, BlockPos hitPos) {
   }
}
