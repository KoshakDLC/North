package ru.metaculture.protection;

import net.minecraft.block.BlockState;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider.Immediate;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;
import org.joml.Matrix4f;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "BlockOutline",
   category = Category.Visuals,
   description = "Плавная светящаяся обводка блока под прицелом"
)
public final class BlockOutline extends Module implements ShaderBinding {
   private static final double DOUBLE_VALUE = 0.0022;
   private static final int[] INTS = new int[]{0, 1, 1, 5, 5, 4, 4, 0, 2, 3, 3, 7, 7, 6, 6, 2, 0, 2, 1, 3, 5, 7, 4, 6};
   private static final int[] INTS_2 = new int[]{0, 1, 5, 4, 2, 6, 7, 3, 0, 4, 6, 2, 1, 3, 7, 5, 0, 2, 3, 1, 4, 5, 7, 6};
   public final NumberSetting plavnost = new NumberSetting("Плавность", 0.55F, 0.0F, 1.0F, 0.01F, true);
   public final NumberSetting prozrachnost = new NumberSetting("Прозрачность", 1.0F, 0.05F, 1.0F, 0.01F, true);
   public final NumberSetting tolschina = new NumberSetting("Толщина", 2.0F, 0.5F, 6.0F, 0.1F, false);
   public final NumberSetting rasshirenie = new NumberSetting("Расширение", 0.0F, 0.0F, 0.2F, 0.005F, false);
   public final BooleanSetting svechenie = new BooleanSetting("Свечение", true);
   public final NumberSetting silaSvecheniya = new NumberSetting("Сила свечения", 1.2F, 0.2F, 3.0F, 0.05F, false)
      .setVisibilityCondition(() -> !this.svechenie.isEnabled());
   public final BooleanSetting zalivka = new BooleanSetting("Заливка", false);
   public final NumberSetting prozrachnostZalivki = new NumberSetting("Прозрачность заливки", 0.22F, 0.02F, 0.8F, 0.01F, true)
      .setVisibilityCondition(() -> !this.zalivka.isEnabled());
   public final BooleanSetting pulsatsiya = new BooleanSetting("Пульсация", false);
   public final NumberSetting skorostPulsatsii = new NumberSetting("Скорость пульсации", 2.0F, 0.2F, 6.0F, 0.1F, false)
      .setVisibilityCondition(() -> !this.pulsatsiya.isEnabled());
   public final BooleanSetting skvozSteny = new BooleanSetting("Сквозь стены", false);
   public final ModeSetting tsvet = new ModeSetting("Цвет", "Тема", "Тема", "Свой", "Радуга");
   public final ColorSetting svoyTsvet = new ColorSetting("Свой цвет", 50.0F, 0.82F, 1.0F).setVisibilityCondition(() -> !this.tsvet.is("Свой"));
   public final NumberSetting skorostRadugi = new NumberSetting("Скорость радуги", 1.0F, 0.1F, 4.0F, 0.1F, false)
      .setVisibilityCondition(() -> !this.tsvet.is("Радуга"));
   public final FoundryShaderSetting foundryShader = new FoundryShaderSetting("Foundry Shader", ShaderSurface.ESP);
   private final double[] doubles = new double[6];
   private final double[] doubles2 = new double[6];
   private final double[] doubles3 = new double[24];
   private boolean flag;
   private float floatValue;
   private long timestamp;

   public BlockOutline() {
      this.addSettings(
         new Setting[]{
            this.plavnost,
            this.prozrachnost,
            this.tolschina,
            this.rasshirenie,
            this.svechenie,
            this.silaSvecheniya,
            this.zalivka,
            this.prozrachnostZalivki,
            this.pulsatsiya,
            this.skorostPulsatsii,
            this.skvozSteny,
            this.tsvet,
            this.svoyTsvet,
            this.skorostRadugi,
            this.foundryShader
         }
      );
   }

   @Override
   public void onEnable() {
      super.onEnable();
      ShaderBindingRegistry.getINSTANCE().invoke(this, this);
   }

   @Override
   public void onDisable() {
      ShaderBindingRegistry.getINSTANCE().invoke4(this);
      this.invoke4();
      super.onDisable();
   }

   @Override
   public ShaderSurface getESP() {
      return ShaderSurface.ESP;
   }

   @Override
   public String resolve() {
      String text = this.foundryShader == null ? "" : this.foundryShader.resolve2();
      return text != null && !text.isBlank() ? text : null;
   }

   @Override
   public boolean check() {
      return true;
   }

   @EventHandler
   public void onRender3D(Render3DEvent render3DEvent) {
      ShaderBindingRegistry.getINSTANCE().invoke7(this, this);
      if (CLIENT.world != null && CLIENT.player != null && CLIENT.gameRenderer != null) {
         boolean flag = this.check2();
         float floatValue = this.measure(flag);
         if (this.flag && !(floatValue <= 0.003F)) {
            Camera camera = CLIENT.gameRenderer.getCamera();
            if (camera != null) {
               Vec3d vec3d2 = camera.getPos();
               Matrix4f matrix4f2 = render3DEvent.getMatrixStack().peek().getPositionMatrix();
               this.invoke(vec3d2);
               float floatValue2 = this.pulsatsiya.isEnabled()
                  ? 0.78F + 0.22F * (float)Math.sin(measure3() * this.skorostPulsatsii.getValue() * Math.PI)
                  : 1.0F;
               float floatValue3 = MathHelper.clamp(floatValue * this.prozrachnost.getValue() * floatValue2, 0.0F, 1.0F);
               if (!(floatValue3 <= 0.003F)) {
                  int intValue = this.compute();
                  int intValue2 = intValue >> 16 & 0xFF;
                  int intValue3 = intValue >> 8 & 0xFF;
                  int intValue4 = intValue & 0xFF;
                  boolean flag2 = !this.skvozSteny.isEnabled();
                  float floatValue4 = this.tolschina.getValue();
                  Immediate immediate = WorldRenderBuffer.getIMMEDIATE();
                  boolean flag3 = false ;

                  try {
                     flag3 = true;
                     if (this.zalivka.isEnabled()) {
                        int intValue5 = compute4(this.prozrachnostZalivki.getValue() * floatValue3 * 255.0F);
                        if (intValue5 > 0) {
                           RenderLayer renderLayer = flag2 ? WorldRenderPipelines.getRENDER_LAYER_4() : WorldRenderPipelines.getRENDER_LAYER_5();
                           this.invoke3(immediate.getBuffer(renderLayer), matrix4f2, intValue2, intValue3, intValue4, intValue5);
                        }
                     }

                     if (this.svechenie.isEnabled()) {
                        float floatValue5 = floatValue4 * (2.4F + this.silaSvecheniya.getValue());
                        int intValue6 = compute4(0.16F * this.silaSvecheniya.getValue() * floatValue3 * 255.0F);
                        if (intValue6 > 0) {
                           this.invoke2(immediate.getBuffer(resolve2(floatValue5, flag2)), matrix4f2, intValue2, intValue3, intValue4, intValue6);
                        }
                     }

                     int intValue7 = compute4(floatValue3 * 255.0F);
                     this.invoke2(immediate.getBuffer(resolve2(floatValue4, flag2)), matrix4f2, intValue2, intValue3, intValue4, intValue7);
                     flag3 = false;
                  } finally {
                     if (flag3) {
                        WorldRenderBuffer.invoke();
                     }
                  }

                  WorldRenderBuffer.invoke();
               }
            }
         }
      } else {
         this.invoke4();
      }
   }

   private boolean check2() {
      HitResult hitResult = CLIENT.crosshairTarget;
      if (hitResult instanceof BlockHitResult blockHitResult && hitResult.getType() == Type.BLOCK) {
         BlockPos blockPos = blockHitResult.getBlockPos();
         if (blockPos == null) {
            return false;
         } else {
            BlockState blockState = CLIENT.world.getBlockState(blockPos);
            if (blockState != null && !blockState.isAir()) {
               VoxelShape voxelShape = blockState.getOutlineShape(CLIENT.world, blockPos);
               if (voxelShape != null && !voxelShape.isEmpty()) {
                  Box box = voxelShape.getBoundingBox();
                  double doubleValue = this.rasshirenie.getValue() + 0.0022;
                  this.doubles2[0] = blockPos.getX() + box.minX - doubleValue;
                  this.doubles2[1] = blockPos.getY() + box.minY - doubleValue;
                  this.doubles2[2] = blockPos.getZ() + box.minZ - doubleValue;
                  this.doubles2[3] = blockPos.getX() + box.maxX + doubleValue;
                  this.doubles2[4] = blockPos.getY() + box.maxY + doubleValue;
                  this.doubles2[5] = blockPos.getZ() + box.maxZ + doubleValue;
                  return true;
               } else {
                  return false;
               }
            } else {
               return false;
            }
         }
      } else {
         return false;
      }
   }

   private float measure(boolean bl) {
      long longValue = System.nanoTime();
      float floatValue6 = this.timestamp == 0L ? 0.0F : Math.min((float)(longValue - this.timestamp) / 1.0E9F, 0.1F);
      this.timestamp = longValue;
      this.floatValue = this.floatValue + ((bl ? 1.0F : 0.0F) - this.floatValue) * measure2(16.0F, floatValue6);
      if (!bl && this.floatValue < 0.01F) {
         this.floatValue = 0.0F;
         this.flag = false;
         return 0.0F;
      } else if (!bl) {
         return this.floatValue;
      } else {
         if (!this.flag) {
            System.arraycopy(this.doubles2, 0, this.doubles, 0, 6);
            this.flag = true;
         } else {
            float floatValue7 = MathHelper.lerp(MathHelper.clamp(this.plavnost.getValue(), 0.0F, 1.0F), 42.0F, 4.5F);
            float floatValue8 = measure2(floatValue7, floatValue6);

            for (int intValue8 = 0; intValue8 < 6; intValue8++) {
               this.doubles[intValue8] = this.doubles[intValue8] + (this.doubles2[intValue8] - this.doubles[intValue8]) * floatValue8;
            }
         }

         return this.floatValue;
      }
   }

   private void invoke(Vec3d vec3d) {
      double doubleValue2 = this.doubles[0] - vec3d.x;
      double doubleValue3 = this.doubles[1] - vec3d.y;
      double doubleValue4 = this.doubles[2] - vec3d.z;
      double doubleValue5 = this.doubles[3] - vec3d.x;
      double doubleValue6 = this.doubles[4] - vec3d.y;
      double doubleValue7 = this.doubles[5] - vec3d.z;

      for (int intValue9 = 0; intValue9 < 8; intValue9++) {
         int intValue10 = intValue9 * 3;
         this.doubles3[intValue10] = (intValue9 & 1) == 0 ? doubleValue2 : doubleValue5;
         this.doubles3[intValue10 + 1] = (intValue9 & 2) == 0 ? doubleValue3 : doubleValue6;
         this.doubles3[intValue10 + 2] = (intValue9 & 4) == 0 ? doubleValue4 : doubleValue7;
      }
   }

   private void invoke2(VertexConsumer vertexConsumer, Matrix4f matrix4f, int i, int j, int k, int l) {
      for (byte byteValue = 0; byteValue < INTS.length; byteValue += 2) {
         int intValue11 = INTS[byteValue] * 3;
         int intValue12 = INTS[byteValue + 1] * 3;
         double doubleValue8 = this.doubles3[intValue11];
         double doubleValue9 = this.doubles3[intValue11 + 1];
         double doubleValue10 = this.doubles3[intValue11 + 2];
         double doubleValue11 = this.doubles3[intValue12];
         double doubleValue12 = this.doubles3[intValue12 + 1];
         double doubleValue13 = this.doubles3[intValue12 + 2];
         double doubleValue14 = doubleValue11 - doubleValue8;
         double doubleValue15 = doubleValue12 - doubleValue9;
         double doubleValue16 = doubleValue13 - doubleValue10;
         double doubleValue17 = Math.sqrt(doubleValue14 * doubleValue14 + doubleValue15 * doubleValue15 + doubleValue16 * doubleValue16);
         if (!(doubleValue17 < 1.0E-6)) {
            float floatValue9 = (float)(doubleValue14 / doubleValue17);
            float floatValue10 = (float)(doubleValue15 / doubleValue17);
            float floatValue11 = (float)(doubleValue16 / doubleValue17);
            vertexConsumer.vertex(matrix4f, (float)doubleValue8, (float)doubleValue9, (float)doubleValue10).color(i, j, k, l).normal(floatValue9, floatValue10, floatValue11);
            vertexConsumer.vertex(matrix4f, (float)doubleValue11, (float)doubleValue12, (float)doubleValue13).color(i, j, k, l).normal(floatValue9, floatValue10, floatValue11);
         }
      }
   }

   private void invoke3(VertexConsumer vertexConsumer, Matrix4f matrix4f, int i, int j, int k, int l) {
      for (byte byteValue2 = 0; byteValue2 < INTS_2.length; byteValue2 += 4) {
         for (int intValue13 = 0; intValue13 < 4; intValue13++) {
            int intValue14 = INTS_2[byteValue2 + intValue13] * 3;
            vertexConsumer.vertex(matrix4f, (float)this.doubles3[intValue14], (float)this.doubles3[intValue14 + 1], (float)this.doubles3[intValue14 + 2])
               .color(i, j, k, l);
         }
      }
   }

   private static RenderLayer resolve2(double d, boolean bl) {
      return bl ? WorldRenderPipelines.resolve5(d) : WorldRenderPipelines.resolve6(d);
   }

   private int compute() {
      if (this.tsvet.is("Радуга")) {
         float floatValue12 = measure3() * this.skorostRadugi.getValue() * 0.12F % 1.0F;
         return compute3(floatValue12 < 0.0F ? floatValue12 + 1.0F : floatValue12, 0.85F, 1.0F);
      } else {
         return this.tsvet.is("Свой") ? this.svoyTsvet.compute() & 16777215 : compute2();
      }
   }

   private static int compute2() {
      try {
         if (WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null) {
            Theme theme = WildClient.INSTANCE.themeManager.getTheme();
            if (theme == Theme.CUSTOM && WildClient.INSTANCE.themeManager.customThemeColor != null) {
               return WildClient.INSTANCE.themeManager.customThemeColor.compute() & 16777215;
            }

            if (theme != null && theme.getColor() != null) {
               return theme.getColor().getRGB() & 16777215;
            }
         }
      } catch (Throwable exception) {
      }

      return 6061311;
   }

   private static int compute3(float f, float g, float h) {
      float floatValue13 = (float)Math.floor(f * 6.0F);
      float floatValue14 = f * 6.0F - floatValue13;
      float floatValue15 = h * (1.0F - g);
      float floatValue16 = h * (1.0F - floatValue14 * g);
      float floatValue17 = h * (1.0F - (1.0F - floatValue14) * g);
      float floatValue18;
      float floatValue19;
      float floatValue20;
      switch ((int)floatValue13 % 6) {
         case 0:
            floatValue18 = h;
            floatValue19 = floatValue17;
            floatValue20 = floatValue15;
            break;
         case 1:
            floatValue18 = floatValue16;
            floatValue19 = h;
            floatValue20 = floatValue15;
            break;
         case 2:
            floatValue18 = floatValue15;
            floatValue19 = h;
            floatValue20 = floatValue17;
            break;
         case 3:
            floatValue18 = floatValue15;
            floatValue19 = floatValue16;
            floatValue20 = h;
            break;
         case 4:
            floatValue18 = floatValue17;
            floatValue19 = floatValue15;
            floatValue20 = h;
            break;
         default:
            floatValue18 = h;
            floatValue19 = floatValue15;
            floatValue20 = floatValue16;
      }

      return Math.round(floatValue18 * 255.0F) << 16 | Math.round(floatValue19 * 255.0F) << 8 | Math.round(floatValue20 * 255.0F);
   }

   private static int compute4(float f) {
      return MathHelper.clamp(Math.round(f), 0, 255);
   }

   private static float measure2(float f, float g) {
      return 1.0F - (float)Math.exp(-f * g);
   }

   private static float measure3() {
      return (float)(System.nanoTime() % 1000000000000L) / 1.0E9F;
   }

   private void invoke4() {
      this.flag = false;
      this.floatValue = 0.0F;
      this.timestamp = 0L;
   }
}
