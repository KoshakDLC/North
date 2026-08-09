package ru.metaculture.protection;

import com.mojang.blaze3d.pipeline.BlendFunction;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import com.mojang.blaze3d.pipeline.RenderPipeline.Snippet;
import com.mojang.blaze3d.platform.DepthTestFunction;
import com.mojang.blaze3d.vertex.VertexFormat.DrawMode;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import net.minecraft.block.Block;
import net.minecraft.client.gl.RenderPipelines;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexFormats;
import net.minecraft.client.render.RenderLayer.MultiPhaseParameters;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.BlockPos.Mutable;
import org.joml.Matrix4f;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public class XrayCommand extends Command {
   private static final int INT_VALUE = 16777215;
   private static final int INT_VALUE_2 = 12;
   private static final int INT_VALUE_3 = 1;
   private static final int INT_VALUE_4 = 64;
   private static final long TIMESTAMP = 250L;
   private static final int INT_VALUE_5 = 4096;
   private static final List<String> ITEMS = List.of("clear", "off", "reset", "help");
   private boolean flag;
   private Block block;
   private Identifier identifier;
   private int intValue = 16777215;
   private int intValue2 = 12;
   private long timestamp;
   private final List<BlockPos> items = new ArrayList<>();
   private static final RenderPipeline RENDER_PIPELINE = RenderPipelines.register(
      RenderPipeline.builder(new Snippet[]{RenderPipelines.POSITION_COLOR_SNIPPET})
         .withLocation(Identifier.of("wild", "xray_box"))
         .withVertexFormat(VertexFormats.POSITION_COLOR, DrawMode.QUADS)
         .withCull(false)
         .withDepthTestFunction(DepthTestFunction.NO_DEPTH_TEST)
         .withDepthWrite(false)
         .withBlend(BlendFunction.LIGHTNING)
         .build()
   );
   private static final RenderLayer RENDER_LAYER = RenderLayer.of("xray_box", 4096, false, true, RENDER_PIPELINE, MultiPhaseParameters.builder().build(false));

   public XrayCommand() {
      super("xray", "Подсветка блока в заданном радиусе", ".xray <block|clear/off/reset/help> [r,g,b] [radius]");
   }

   @Compile
   @Override
   public void execute(String[] strings) {
      if (strings.length == 0 || strings[0].equalsIgnoreCase("help")) {
         this.invoke3();
         return;
      }

      String text = strings[0].toLowerCase(Locale.ROOT);
      if (text.equals("off") || text.equals("clear") || text.equals("reset")) {
         this.invoke2();
         return;
      }

      Identifier identifier = this.resolve(text);
      if (identifier == null || !Registries.BLOCK.getIds().contains(identifier)) {
         ChatUtil.sendClientMessage("§cНеизвестный блок: §f" + strings[0]);
         return;
      }

      Integer integer = strings.length >= 2 ? this.resolve2(strings[1]) : Integer.valueOf(16777215);
      Integer integer2 = strings.length >= 3 ? this.resolve3(strings[2]) : Integer.valueOf(12);
      if (integer == null) {
         ChatUtil.sendClientMessage("§cЦвет должен иметь формат R,G,B (0–255).");
         return;
      }

      if (integer2 == null) {
         ChatUtil.sendClientMessage("§cРадиус должен быть от 1 до 64.");
         return;
      }

      this.identifier = identifier;
      this.block = Registries.BLOCK.get(identifier);
      this.intValue = integer;
      this.intValue2 = integer2;
      this.flag = true;
      this.timestamp = 0L;
      this.items.clear();
      ChatUtil.sendClientMessage(
         "§aXRay: §f" + this.resolve5(identifier) + " §7| цвет " + this.resolve4(integer) + " | радиус " + integer2
      );
   }

   @Override
   public List<String> getSuggestions(String[] strings) {
      if (strings.length != 2) {
         if (strings.length == 3 && !this.check(strings[1].toLowerCase(Locale.ROOT))) {
            String text2 = strings[2].toLowerCase(Locale.ROOT);
            return List.of("255,255,255", "255,0,0", "0,255,0", "0,128,255", String.valueOf(12)).stream().filter(string2 -> string2.startsWith(text2)).toList();
         } else if (strings.length == 4 && !this.check(strings[1].toLowerCase(Locale.ROOT))) {
            String text3 = strings[3].toLowerCase(Locale.ROOT);
            return List.of("8", "12", "15", "24", "32", "64").stream().filter(string2 -> string2.startsWith(text3)).toList();
         } else {
            return List.of();
         }
      } else {
         String text4 = strings[1].toLowerCase(Locale.ROOT);
         ArrayList arrayList = new ArrayList();

         for (String text5 : ITEMS) {
            if (text5.startsWith(text4)) {
               arrayList.add(text5);
            }
         }

         for (Identifier identifier2 : Registries.BLOCK.getIds()) {
            String text6 = this.resolve5(identifier2);
            if (text6.startsWith(text4)) {
               arrayList.add(text6);
               if (arrayList.size() >= 30) {
                  break;
               }
            }
         }

         return arrayList;
      }
   }

   @EventHandler
   public void onRender3D(Render3DEvent render3DEvent) {
      if (this.flag && this.block != null && a_.world != null && a_.player != null) {
         this.invoke();
         if (!this.items.isEmpty()) {
            Immediate immediate = WorldRenderBuffer.getIMMEDIATE();

            try {
               Vec3d vec3d = a_.gameRenderer.getCamera().getPos();
               Matrix4f matrix4f = render3DEvent.getMatrixStack().peek().getPositionMatrix();
               VertexConsumer vertexConsumer = immediate.getBuffer(RENDER_LAYER);
               Color color = new Color(this.intValue);
               Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), 120);
               Color color3 = new Color(color.getRed(), color.getGreen(), color.getBlue(), 0);

               for (BlockPos blockPos : this.items) {
                  if (a_.world.getBlockState(blockPos).isOf(this.block)) {
                     float floatValue = (float)(blockPos.getX() - vec3d.x);
                     float floatValue2 = (float)(blockPos.getY() - vec3d.y);
                     float floatValue3 = (float)(blockPos.getZ() - vec3d.z);
                     float floatValue4 = floatValue + 1.0F;
                     float floatValue5 = floatValue2 + 1.0F;
                     float floatValue6 = floatValue3 + 1.0F;
                     this.invoke4(vertexConsumer, matrix4f, floatValue, floatValue2, floatValue3, floatValue4, floatValue5, floatValue6, color2, color3);
                  }
               }
            } finally {
               WorldRenderBuffer.invoke();
            }
         }
      }
   }

   private void invoke() {
      long longValue = System.currentTimeMillis();
      if (longValue - this.timestamp >= 250L) {
         this.timestamp = longValue;
         this.items.clear();
         BlockPos blockPos2 = a_.player.getBlockPos();
         Mutable mutable = new Mutable();
         int intValue = Math.max(a_.world.getBottomY(), blockPos2.getY() - this.intValue2);
         int intValue2 = Math.min(a_.world.getTopYInclusive(), blockPos2.getY() + this.intValue2);

         for (int intValue3 = blockPos2.getX() - this.intValue2; intValue3 <= blockPos2.getX() + this.intValue2; intValue3++) {
            for (int intValue4 = intValue; intValue4 <= intValue2; intValue4++) {
               for (int intValue5 = blockPos2.getZ() - this.intValue2; intValue5 <= blockPos2.getZ() + this.intValue2; intValue5++) {
                  mutable.set(intValue3, intValue4, intValue5);
                  if (a_.world.getBlockState(mutable).isOf(this.block)) {
                     this.items.add(mutable.toImmutable());
                  }
               }
            }
         }
      }
   }

   private void invoke2() {
      this.flag = false;
      this.block = null;
      this.identifier = null;
      this.items.clear();
      ChatUtil.sendClientMessage("§7XRay выключен.");
   }

   private void invoke3() {
      ChatUtil.sendClientMessage("§cИспользование: " + this.getUsage());
      ChatUtil.sendClientMessage("§7Пример: §f.xray diamond_ore 255,255,255 15");
      ChatUtil.sendClientMessage("§7Команды: §f.xray clear §7/ §f.xray off §7/ §f.xray reset");
   }

   private boolean check(String string) {
      return string != null && (string.equals("off") || string.equals("clear") || string.equals("reset") || string.equals("help"));
   }

   private Identifier resolve(String string) {
      if (string != null && !string.isBlank()) {
         String text7 = string.trim().toLowerCase(Locale.ROOT);
         if (!text7.contains(":")) {
            text7 = "minecraft:" + text7;
         }

         return Identifier.tryParse(text7);
      } else {
         return null;
      }
   }

   private Integer resolve2(String string) {
      String[] texts = string.split(",");
      if (texts.length != 3) {
         return null;
      } else {
         int[] intValues = new int[3];

         for (int intValue6 = 0; intValue6 < 3; intValue6++) {
            try {
               intValues[intValue6] = Integer.parseInt(texts[intValue6].trim());
            } catch (NumberFormatException numberFormatException) {
               return null;
            }

            if (intValues[intValue6] < 0 || intValues[intValue6] > 255) {
               return null;
            }
         }

         return intValues[0] << 16 | intValues[1] << 8 | intValues[2];
      }
   }

   private Integer resolve3(String string) {
      try {
         int intValue7 = Integer.parseInt(string.trim());
         return intValue7 >= 1 && intValue7 <= 64 ? intValue7 : null;
      } catch (NumberFormatException numberFormatException2) {
         return null;
      }
   }

   private String resolve4(int i) {
      return (i >> 16 & 0xFF) + "," + (i >> 8 & 0xFF) + "," + (i & 0xFF);
   }

   private String resolve5(Identifier identifier) {
      if (identifier == null) {
         return "";
      } else {
         return "minecraft".equals(identifier.getNamespace()) ? identifier.getPath() : identifier.toString();
      }
   }

   private void invoke4(VertexConsumer vertexConsumer, Matrix4f matrix4f, float f, float g, float h, float i, float j, float k, Color color, Color color2) {
      int intValue8 = color.getRed();
      int intValue9 = color.getGreen();
      int intValue10 = color.getBlue();
      int intValue11 = color.getAlpha();
      int intValue12 = color2.getRed();
      int intValue13 = color2.getGreen();
      int intValue14 = color2.getBlue();
      int intValue15 = color2.getAlpha();
      vertexConsumer.vertex(matrix4f, f, g, h).color(intValue8, intValue9, intValue10, intValue11);
      vertexConsumer.vertex(matrix4f, i, g, h).color(intValue8, intValue9, intValue10, intValue11);
      vertexConsumer.vertex(matrix4f, i, j, h).color(intValue12, intValue13, intValue14, intValue15);
      vertexConsumer.vertex(matrix4f, f, j, h).color(intValue12, intValue13, intValue14, intValue15);
      vertexConsumer.vertex(matrix4f, f, j, k).color(intValue12, intValue13, intValue14, intValue15);
      vertexConsumer.vertex(matrix4f, i, j, k).color(intValue12, intValue13, intValue14, intValue15);
      vertexConsumer.vertex(matrix4f, i, g, k).color(intValue8, intValue9, intValue10, intValue11);
      vertexConsumer.vertex(matrix4f, f, g, k).color(intValue8, intValue9, intValue10, intValue11);
      vertexConsumer.vertex(matrix4f, f, g, k).color(intValue8, intValue9, intValue10, intValue11);
      vertexConsumer.vertex(matrix4f, f, g, h).color(intValue8, intValue9, intValue10, intValue11);
      vertexConsumer.vertex(matrix4f, f, j, h).color(intValue12, intValue13, intValue14, intValue15);
      vertexConsumer.vertex(matrix4f, f, j, k).color(intValue12, intValue13, intValue14, intValue15);
      vertexConsumer.vertex(matrix4f, i, j, k).color(intValue12, intValue13, intValue14, intValue15);
      vertexConsumer.vertex(matrix4f, i, j, h).color(intValue12, intValue13, intValue14, intValue15);
      vertexConsumer.vertex(matrix4f, i, g, h).color(intValue8, intValue9, intValue10, intValue11);
      vertexConsumer.vertex(matrix4f, i, g, k).color(intValue8, intValue9, intValue10, intValue11);
      vertexConsumer.vertex(matrix4f, f, g, h).color(intValue8, intValue9, intValue10, intValue11);
      vertexConsumer.vertex(matrix4f, f, g, k).color(intValue8, intValue9, intValue10, intValue11);
      vertexConsumer.vertex(matrix4f, i, g, k).color(intValue8, intValue9, intValue10, intValue11);
      vertexConsumer.vertex(matrix4f, i, g, h).color(intValue8, intValue9, intValue10, intValue11);
      vertexConsumer.vertex(matrix4f, f, j, h).color(intValue12, intValue13, intValue14, intValue15);
      vertexConsumer.vertex(matrix4f, i, j, h).color(intValue12, intValue13, intValue14, intValue15);
      vertexConsumer.vertex(matrix4f, i, j, k).color(intValue12, intValue13, intValue14, intValue15);
      vertexConsumer.vertex(matrix4f, f, j, k).color(intValue12, intValue13, intValue14, intValue15);
   }

   static {
      Loader.initialize();
   }
}
