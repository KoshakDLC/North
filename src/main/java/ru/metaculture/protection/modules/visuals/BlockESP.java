package ru.metaculture.protection;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.block.entity.BarrelBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.block.entity.ChestBlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.block.entity.DropperBlockEntity;
import net.minecraft.block.entity.EnderChestBlockEntity;
import net.minecraft.block.entity.FurnaceBlockEntity;
import net.minecraft.block.entity.HopperBlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.block.entity.ShulkerBoxBlockEntity;
import net.minecraft.block.entity.TrappedChestBlockEntity;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.RenderLayer.MultiPhaseParameters;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.chunk.WorldChunk;
import org.joml.Matrix4f;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "BlockESP",
   category = Category.Visuals,
   description = "Подцветка определенных блоков"
)
public class BlockESP extends Module {
   public static GroupSetting bloki = new GroupSetting(
      "Блоки",
      new BooleanSetting("Сундук", true),
      new BooleanSetting("Сундук ловушка", true),
      new BooleanSetting("Эндер сундук", true),
      new BooleanSetting("Спавнер", true),
      new BooleanSetting("Бочка", true),
      new BooleanSetting("Воронка", true),
      new BooleanSetting("Раздатчик", true),
      new BooleanSetting("Выбрасыватель", true),
      new BooleanSetting("Печка", true),
      new BooleanSetting("Шалкер", true)
   );
   public static final Map<BlockEntityType<?>, Integer> VALUES_BY_KEY = new HashMap<>();
   private static final int INT_VALUE = 1024;
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
      "block_esp_box", 1024, false, true, RENDER_PIPELINE, MultiPhaseParameters.builder().build(false)
   );

   public BlockESP() {
      this.addSettings(new Setting[]{bloki});
      VALUES_BY_KEY.clear();
      VALUES_BY_KEY.put(BlockEntityType.CHEST, new Color(255, 194, 84).getRGB());
      VALUES_BY_KEY.put(BlockEntityType.TRAPPED_CHEST, new Color(143, 109, 62).getRGB());
      VALUES_BY_KEY.put(BlockEntityType.ENDER_CHEST, new Color(153, 49, 238).getRGB());
      VALUES_BY_KEY.put(BlockEntityType.MOB_SPAWNER, 16777215);
      VALUES_BY_KEY.put(BlockEntityType.BARREL, new Color(250, 225, 62).getRGB());
      VALUES_BY_KEY.put(BlockEntityType.HOPPER, new Color(62, 137, 250).getRGB());
      VALUES_BY_KEY.put(BlockEntityType.DISPENSER, new Color(27, 64, 250).getRGB());
      VALUES_BY_KEY.put(BlockEntityType.DROPPER, new Color(0, 23, 255).getRGB());
      VALUES_BY_KEY.put(BlockEntityType.FURNACE, new Color(115, 115, 115).getRGB());
      VALUES_BY_KEY.put(BlockEntityType.SHULKER_BOX, new Color(246, 123, 123).getRGB());
   }

   @EventHandler
   public void onRender3D(Render3DEvent render3DEvent) {
      if (CLIENT.world != null && CLIENT.player != null) {
         Immediate immediate = WorldRenderBuffer.getIMMEDIATE();

         try {
            Vec3d vec3d = CLIENT.gameRenderer.getCamera().getPos();
            Matrix4f matrix4f2 = render3DEvent.getMatrixStack().peek().getPositionMatrix();
            VertexConsumer vertexConsumer2 = immediate.getBuffer(RENDER_LAYER);
            ChunkPos chunkPos = CLIENT.player.getChunkPos();
            int intValue = (Integer)CLIENT.options.getViewDistance().getValue();

            for (int intValue2 = chunkPos.x - intValue; intValue2 <= chunkPos.x + intValue; intValue2++) {
               for (int intValue3 = chunkPos.z - intValue; intValue3 <= chunkPos.z + intValue; intValue3++) {
                  WorldChunk worldChunk = CLIENT.world.getChunk(intValue2, intValue3);
                  if (worldChunk != null) {
                     for (BlockEntity blockEntity : worldChunk.getBlockEntities().values()) {
                        BlockEntityType blockEntityType = blockEntity.getType();
                        if (VALUES_BY_KEY.containsKey(blockEntityType)
                           && (!(blockEntity instanceof TrappedChestBlockEntity) || bloki.isEnabled("Сундук ловушка"))
                           && (!(blockEntity instanceof ChestBlockEntity) || bloki.isEnabled("Сундук"))
                           && (!(blockEntity instanceof EnderChestBlockEntity) || bloki.isEnabled("Эндер сундук"))
                           && (!(blockEntity instanceof MobSpawnerBlockEntity) || bloki.isEnabled("Спавнер"))
                           && (!(blockEntity instanceof BarrelBlockEntity) || bloki.isEnabled("Бочка"))
                           && (!(blockEntity instanceof HopperBlockEntity) || bloki.isEnabled("Воронка"))
                           && (!(blockEntity instanceof DispenserBlockEntity) || bloki.isEnabled("Раздатчик"))
                           && (!(blockEntity instanceof DropperBlockEntity) || bloki.isEnabled("Выбрасыватель"))
                           && (!(blockEntity instanceof FurnaceBlockEntity) || bloki.isEnabled("Печка"))
                           && (!(blockEntity instanceof ShulkerBoxBlockEntity) || bloki.isEnabled("Шалкер"))) {
                           BlockPos blockPos = blockEntity.getPos();
                           float floatValue = (float)(blockPos.getX() - vec3d.x);
                           float floatValue2 = (float)(blockPos.getY() - vec3d.y);
                           float floatValue3 = (float)(blockPos.getZ() - vec3d.z);
                           float floatValue4 = (float)(blockPos.getX() + 1 - vec3d.x);
                           float floatValue5 = (float)(blockPos.getY() + 1 - vec3d.y);
                           float floatValue6 = (float)(blockPos.getZ() + 1 - vec3d.z);
                           this.invoke(vertexConsumer2, matrix4f2, floatValue, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, VALUES_BY_KEY.get(blockEntityType), 120, 0);
                        }
                     }
                  }
               }
            }
         } finally {
            WorldRenderBuffer.invoke();
         }
      }
   }

   private void invoke(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, float k, int l, int m, int n) {
      int intValue4 = l >> 16 & 0xFF;
      int intValue5 = l >> 8 & 0xFF;
      int intValue6 = l & 0xFF;
      vertexConsumer.vertex(matrix4f, f, g, h).color(intValue4, intValue5, intValue6, m);
      vertexConsumer.vertex(matrix4f, i, g, h).color(intValue4, intValue5, intValue6, m);
      vertexConsumer.vertex(matrix4f, i, j, h).color(intValue4, intValue5, intValue6, n);
      vertexConsumer.vertex(matrix4f, f, j, h).color(intValue4, intValue5, intValue6, n);
      vertexConsumer.vertex(matrix4f, f, j, k).color(intValue4, intValue5, intValue6, n);
      vertexConsumer.vertex(matrix4f, i, j, k).color(intValue4, intValue5, intValue6, n);
      vertexConsumer.vertex(matrix4f, i, g, k).color(intValue4, intValue5, intValue6, m);
      vertexConsumer.vertex(matrix4f, f, g, k).color(intValue4, intValue5, intValue6, m);
      vertexConsumer.vertex(matrix4f, f, g, k).color(intValue4, intValue5, intValue6, m);
      vertexConsumer.vertex(matrix4f, f, g, h).color(intValue4, intValue5, intValue6, m);
      vertexConsumer.vertex(matrix4f, f, j, h).color(intValue4, intValue5, intValue6, n);
      vertexConsumer.vertex(matrix4f, f, j, k).color(intValue4, intValue5, intValue6, n);
      vertexConsumer.vertex(matrix4f, i, j, k).color(intValue4, intValue5, intValue6, n);
      vertexConsumer.vertex(matrix4f, i, j, h).color(intValue4, intValue5, intValue6, n);
      vertexConsumer.vertex(matrix4f, i, g, h).color(intValue4, intValue5, intValue6, m);
      vertexConsumer.vertex(matrix4f, i, g, k).color(intValue4, intValue5, intValue6, m);
      vertexConsumer.vertex(matrix4f, f, g, h).color(intValue4, intValue5, intValue6, m);
      vertexConsumer.vertex(matrix4f, f, g, k).color(intValue4, intValue5, intValue6, m);
      vertexConsumer.vertex(matrix4f, i, g, k).color(intValue4, intValue5, intValue6, m);
      vertexConsumer.vertex(matrix4f, i, g, h).color(intValue4, intValue5, intValue6, m);
      vertexConsumer.vertex(matrix4f, f, j, h).color(intValue4, intValue5, intValue6, n);
      vertexConsumer.vertex(matrix4f, i, j, h).color(intValue4, intValue5, intValue6, n);
      vertexConsumer.vertex(matrix4f, i, j, k).color(intValue4, intValue5, intValue6, n);
      vertexConsumer.vertex(matrix4f, f, j, k).color(intValue4, intValue5, intValue6, n);
   }
}
