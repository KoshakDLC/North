package ru.metaculture.protection;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.RenderLayer.MultiPhaseParameters;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket.Action;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDeltaUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.Mutable;
import org.joml.Matrix4f;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AncientXray",
   category = Category.Visuals,
   description = "Поиск обломков после взрыва ТНТ",
   riskLevels = {ModuleRiskLevel.VIP}
)
public class AncientXray extends Module {
   private final Set<BlockPos> values = ConcurrentHashMap.newKeySet();
   private final Set<BlockPos> values2 = ConcurrentHashMap.newKeySet();
   private final List<AncientXray.AncientXrayState> items = new ArrayList<>();
   private static final int INT_VALUE = 28;
   private static final int[] INTS = new int[]{4, 10, 20, 40};
   private long timestamp = 0L;
   private static final int INT_VALUE_2 = 4096;
   private static final RenderPipeline RENDER_PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "block_esp_box"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.LIGHTNING)
         .build()
   );
   private static final RenderLayer RENDER_LAYER = RenderLayer.of(
      "block_esp_box", 4096, false, true, RENDER_PIPELINE, MultiPhaseParameters.builder().build(false)
   );

   @Override
   public void onEnable() {
      super.onEnable();
      this.values.clear();
      this.values2.clear();
      this.items.clear();
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null) {
         this.invoke();
      }
   }

   public void invoke() {
      if (CLIENT.player != null && CLIENT.world != null) {
         Iterator iterator = this.items.iterator();

         while (iterator.hasNext()) {
            AncientXray.AncientXrayState ancientXrayState = (AncientXray.AncientXrayState)iterator.next();
            ancientXrayState.intValue--;
            if (ancientXrayState.intValue <= 0) {
               this.invoke8(ancientXrayState.blockPos, 28);
               iterator.remove();
            }
         }

         if (System.currentTimeMillis() - this.timestamp > 50L) {
            for (BlockPos blockPos2 : this.values) {
               if (!this.values2.contains(blockPos2)) {
                  this.values2.add(blockPos2);
                  this.invoke6(blockPos2);
                  this.timestamp = System.currentTimeMillis();
                  break;
               }
            }
         }
      }
   }

   public void invoke2() {
      this.values.clear();
      this.values2.clear();
      this.items.clear();
   }

   public void invoke3(BlockPos blockPos, int i) {
      if (blockPos != null) {
         this.items.add(new AncientXray.AncientXrayState(blockPos.toImmutable(), i));
      }
   }

   public void invoke4(BlockPos blockPos, Block block) {
      this.invoke7(blockPos, block);
   }

   public List<BlockPos> resolve() {
      return new ArrayList<>(this.values);
   }

   public void invoke5(BlockPos blockPos) {
      this.values.remove(blockPos);
      this.values2.remove(blockPos);
   }

   public boolean check(BlockPos blockPos) {
      return this.values.contains(blockPos);
   }

   public boolean check2(BlockPos blockPos) {
      return this.check3(blockPos);
   }

   private void invoke6(BlockPos blockPos) {
      if (CLIENT.getNetworkHandler() != null) {
         CLIENT.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(Action.START_DESTROY_BLOCK, blockPos, Direction.UP));
         CLIENT.getNetworkHandler().sendPacket(new PlayerActionC2SPacket(Action.ABORT_DESTROY_BLOCK, blockPos, Direction.UP));
      }
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (CLIENT.world != null) {
         if (packetEvent.getPacket() instanceof ExplosionS2CPacket explosionS2CPacket) {
            BlockPos blockPos3 = BlockPos.ofFloored(explosionS2CPacket.center());

            for (int intValue : INTS) {
               this.invoke3(blockPos3, intValue);
            }
         } else if (packetEvent.getPacket() instanceof BlockUpdateS2CPacket blockUpdateS2CPacket) {
            this.invoke7(blockUpdateS2CPacket.getPos(), blockUpdateS2CPacket.getState().getBlock());
         } else if (packetEvent.getPacket() instanceof ChunkDeltaUpdateS2CPacket chunkDeltaUpdateS2CPacket) {
            chunkDeltaUpdateS2CPacket.visitUpdates((blockPos, blockState) -> this.invoke7(blockPos, blockState.getBlock()));
         }
      }
   }

   private void invoke7(BlockPos blockPos, Block block) {
      BlockPos blockPos4 = blockPos.toImmutable();
      if (block == Blocks.ANCIENT_DEBRIS) {
         if (this.check3(blockPos4) && this.values.add(blockPos4)) {
            ChatUtil.sendClientMessage("§6[AncientXray] §fОбломок найден §e" + blockPos4.toShortString());
         }
      } else {
         this.values.remove(blockPos4);
         this.values2.remove(blockPos4);
      }
   }

   private void invoke8(BlockPos blockPos, int i) {
      if (CLIENT.world != null) {
         Mutable mutable = new Mutable();

         for (int intValue2 = -i; intValue2 <= i; intValue2++) {
            for (int intValue3 = -i; intValue3 <= i; intValue3++) {
               for (int intValue4 = -i; intValue4 <= i; intValue4++) {
                  mutable.set(blockPos.getX() + intValue2, blockPos.getY() + intValue3, blockPos.getZ() + intValue4);
                  if (this.check3(mutable)) {
                     BlockPos blockPos5 = mutable.toImmutable();
                     if (this.values.add(blockPos5)) {
                        ChatUtil.sendClientMessage("§fОбнаружен обломок: §e" + blockPos5.toShortString());
                     }
                  }
               }
            }
         }
      }
   }

   private boolean check3(BlockPos blockPos) {
      if (CLIENT.world == null) {
         return false;
      } else {
         Block block2 = CLIENT.world.getBlockState(blockPos).getBlock();
         return block2 == Blocks.ANCIENT_DEBRIS
            && this.check4(blockPos)
            && !this.check5(blockPos)
            && this.check6(blockPos)
            && !this.check7(blockPos);
      }
   }

   private boolean check4(BlockPos blockPos) {
      int intValue5 = 0;

      for (Direction direction : Direction.values()) {
         Block block3 = CLIENT.world.getBlockState(blockPos.offset(direction)).getBlock();
         if (block3 == Blocks.AIR || block3 == Blocks.LAVA || block3 == Blocks.CAVE_AIR) {
            if (++intValue5 >= 2) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean check5(BlockPos blockPos) {
      int intValue6 = 0;

      for (int intValue7 = -1; intValue7 <= 1; intValue7++) {
         for (int intValue8 = -1; intValue8 <= 1; intValue8++) {
            for (int intValue9 = -1; intValue9 <= 1; intValue9++) {
               Block block4 = CLIENT.world.getBlockState(blockPos.add(intValue7, intValue8, intValue9)).getBlock();
               if (block4 == Blocks.NETHER_QUARTZ_ORE || block4 == Blocks.NETHER_GOLD_ORE) {
                  if (++intValue6 >= 4) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   private boolean check6(BlockPos blockPos) {
      int intValue10 = 0;

      for (int intValue11 = -1; intValue11 <= 1; intValue11++) {
         for (int intValue12 = -1; intValue12 <= 1; intValue12++) {
            for (int intValue13 = -1; intValue13 <= 1; intValue13++) {
               Block block5 = CLIENT.world.getBlockState(blockPos.add(intValue11, intValue12, intValue13)).getBlock();
               if (block5 == Blocks.AIR || block5 == Blocks.LAVA || block5 == Blocks.CAVE_AIR) {
                  if (++intValue10 >= 4) {
                     return true;
                  }
               }
            }
         }
      }

      return intValue10 >= 4;
   }

   private boolean check7(BlockPos blockPos) {
      int intValue14 = 0;

      for (int intValue15 = -3; intValue15 <= 2; intValue15++) {
         for (int intValue16 = -2; intValue16 <= 2; intValue16++) {
            for (int intValue17 = -2; intValue17 <= 3; intValue17++) {
               if (CLIENT.world.getBlockState(blockPos.add(intValue15, intValue16, intValue17)).getBlock() == Blocks.ANCIENT_DEBRIS) {
                  if (++intValue14 > 6) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }

   @EventHandler
   public void onRender3D(Render3DEvent render3DEvent) {
      if (CLIENT.world != null && CLIENT.player != null && !this.values.isEmpty()) {
         Immediate immediate = WorldRenderBuffer.getIMMEDIATE();

         try {
            Vec3d vec3d = CLIENT.gameRenderer.getCamera().getPos();
            Matrix4f matrix4f = render3DEvent.getMatrixStack().peek().getPositionMatrix();
            int intValue18 = -2147418368;
            VertexConsumer vertexConsumer = immediate.getBuffer(RENDER_LAYER);

            for (BlockPos blockPos6 : this.values) {
               if (!CLIENT.world.getBlockState(blockPos6).isOf(Blocks.ANCIENT_DEBRIS)) {
                  this.values.remove(blockPos6);
               } else {
                  float floatValue = (float)(blockPos6.getX() - vec3d.x);
                  float floatValue2 = (float)(blockPos6.getY() - vec3d.y);
                  float floatValue3 = (float)(blockPos6.getZ() - vec3d.z);
                  float floatValue4 = floatValue + 1.0F;
                  float floatValue5 = floatValue2 + 1.0F;
                  float floatValue6 = floatValue3 + 1.0F;
                  EspBoxVertexWriter.invoke(vertexConsumer, matrix4f, floatValue, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, intValue18);
               }
            }
         } finally {
            WorldRenderBuffer.invoke();
         }
      }
   }

   static class AncientXrayState {
      BlockPos blockPos;
      int intValue;

      AncientXrayState(BlockPos blockPos, int i) {
         this.blockPos = blockPos;
         this.intValue = i;
      }
   }
}
