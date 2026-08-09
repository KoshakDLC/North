package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleFactory;
import net.minecraft.client.particle.ParticleTextureSheet;
import net.minecraft.client.particle.SpriteBillboardParticle;
import net.minecraft.client.render.Camera;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.SimpleParticleType;
import net.minecraft.util.math.Vec3d;

public final class FloatingStardustParticle extends SpriteBillboardParticle {
   public static SimpleParticleType simpleParticleType = StardustParticleRegistry.SIMPLE_PARTICLE_TYPE;
   private static int intValue;
   private static int intValue2;
   private static final float FLOAT_VALUE = 0.035F;
   private static final float FLOAT_VALUE_2 = 0.0238F;
   private static final float FLOAT_VALUE_3 = (float)Math.sin(0.035F);
   private static final float FLOAT_VALUE_4 = (float)Math.cos(0.035F);
   private static final float FLOAT_VALUE_5 = (float)Math.sin(0.0238F);
   private static final float FLOAT_VALUE_6 = (float)Math.cos(0.0238F);
   private static ClientWorld clientWorld;
   private static long timestamp = Long.MIN_VALUE;
   private static double doubleValue;
   private static double doubleValue2;
   private static double doubleValue3;
   private static double doubleValue4;
   private static float floatValue;
   private static boolean flag;
   private final int intValue3;
   private final float floatValue2;
   private final float floatValue3;
   private final float floatValue4;
   private final float floatValue5;
   private final int intValue4;
   private final int intValue5;
   private final int intValue6;
   private float floatValue6;
   private float floatValue7;
   private float floatValue8;
   private float floatValue9;
   private float floatValue10;
   private float floatValue11;
   private float floatValue12;
   private float floatValue13;
   private float floatValue14;
   private boolean flag2 = true;

   public FloatingStardustParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
      super(clientWorld, d, e, f, 0.0, 0.0, 0.0);
      float floatValue = measure3((float)(d * 0.071 + e * 0.113 + f * 0.197));
      float floatValue2 = measure3(floatValue * 37.13F + (float)e * 0.021F);
      float floatValue3 = measure3(floatValue2 * 51.73F + (float)f * 0.017F);
      float floatValue4 = floatValue * (float) (Math.PI * 2);
      float floatValue5 = floatValue2 * (float) (Math.PI * 2);
      this.floatValue3 = floatValue2;
      this.floatValue2 = 0.092F + floatValue3 * 0.244F + measure2(0.74F, 1.0F, floatValue2) * 0.306F;
      float floatValue6 = (1.0F + floatValue * 60.0F) * 0.035F;
      float floatValue7 = floatValue6 * 0.68F;
      this.floatValue6 = (float)Math.sin(floatValue6 + floatValue4);
      this.floatValue7 = (float)Math.cos(floatValue6 + floatValue4);
      this.floatValue8 = (float)Math.sin(floatValue7 + floatValue5);
      this.floatValue9 = (float)Math.cos(floatValue7 + floatValue5);
      this.floatValue10 = (float)Math.sin(floatValue6 + floatValue5);
      this.floatValue11 = (float)Math.cos(floatValue6 + floatValue5);
      float floatValue8 = 0.085F + floatValue * 0.075F;
      this.floatValue4 = (float)Math.sin(floatValue8);
      this.floatValue5 = (float)Math.cos(floatValue8);
      this.floatValue12 = (float)Math.sin(floatValue4);
      this.floatValue13 = (float)Math.cos(floatValue4);
      this.invoke3();
      int intValue = floatValue < 0.58F ? Stardust.getIntValue7() : Stardust.getIntValue8();
      float floatValue9 = 0.36F + floatValue2 * 0.36F;
      float floatValue10 = measure2(0.84F, 1.0F, floatValue3);
      this.intValue4 = compute(measure(184.0F + floatValue10 * 48.0F, compute2(intValue, 16), floatValue9) + floatValue * 18.0F);
      this.intValue5 = compute(measure(218.0F + floatValue10 * 30.0F, compute2(intValue, 8), floatValue9) + floatValue2 * 14.0F);
      this.intValue6 = compute(measure(246.0F, compute2(intValue, 0), floatValue9) + floatValue3 * 10.0F);
      this.intValue3 = intValue2;
      this.maxAge = 142 + (int)(floatValue2 * 138.0F);
      this.collidesWithWorld = false;
      this.gravityStrength = 0.0F;
      this.velocityMultiplier = 0.99F;
      this.setVelocity(g, h, i);
      this.scale = this.floatValue2;
      intValue++;
   }

   public ParticleTextureSheet getType() {
      return ParticleTextureSheet.CUSTOM;
   }

   public void tick() {
      this.lastX = this.x;
      this.lastY = this.y;
      this.lastZ = this.z;
      if (Stardust.isFlag() && this.intValue3 == intValue2 && this.age++ < this.maxAge) {
         if (check(this.world)) {
            double offsetX = this.x - doubleValue;
            double offsetY = this.y - doubleValue2;
            double offsetZ = this.z - doubleValue3;
            if (offsetX * offsetX + offsetZ * offsetZ > doubleValue4 || offsetY < -2.4 || offsetY > floatValue) {
               this.markDead();
               return;
            }
         }

         this.x = this.x + (this.velocityX + this.floatValue6 * 0.0028);
         this.y = this.y + (this.velocityY + this.floatValue8 * 0.0022);
         this.z = this.z + (this.velocityZ + this.floatValue11 * 0.0028);
         this.velocityX *= 0.973;
         this.velocityY *= 0.97;
         this.velocityZ *= 0.973;
         this.invoke();
         this.invoke2();
      } else {
         this.markDead();
      }
   }

   public void render(VertexConsumer vertexConsumer, Camera camera, float tickProgress) {
   }

   public void renderCustom(MatrixStack matrices, VertexConsumerProvider vertexConsumers, Camera camera, float tickProgress) {
      if (!Stardust.isFlag()) {
         this.markDead();
      } else {
         float floatValue11 = (this.age + tickProgress) / this.maxAge;
         float floatValue12 = measure2(0.0F, 0.18F, floatValue11) * (1.0F - measure2(0.76F, 1.0F, floatValue11));
         float floatValue13 = this.floatValue12 + (this.floatValue14 - this.floatValue12) * tickProgress;
         float floatValue14 = 0.76F + 0.24F * floatValue13;
         floatValue12 *= floatValue14 * Stardust.measure() * (0.68F + this.floatValue3 * 0.48F);
         if (!(floatValue12 <= 0.003F)) {
            ParticleBillboardRenderer.invoke(camera);
            double doubleValue4 = this.lastX + (this.x - this.lastX) * tickProgress;
            double doubleValue5 = this.lastY + (this.y - this.lastY) * tickProgress;
            double doubleValue6 = this.lastZ + (this.z - this.lastZ) * tickProgress;
            float floatValue15 = (float)(doubleValue4 - ParticleBillboardRenderer.getDoubleValue4());
            float floatValue16 = (float)(doubleValue5 - ParticleBillboardRenderer.getDoubleValue5());
            float floatValue17 = (float)(doubleValue6 - ParticleBillboardRenderer.getDoubleValue6());
            float floatValue18 = ParticleBillboardRenderer.getFloatValue5();
            float floatValue19 = ParticleBillboardRenderer.getFloatValue6();
            float floatValue20 = ParticleBillboardRenderer.getFloatValue7();
            float floatValue21 = ParticleBillboardRenderer.getFloatValue8();
            float floatValue22 = ParticleBillboardRenderer.getFloatValue9();
            float floatValue23 = ParticleBillboardRenderer.getFloatValue10();
            float floatValue24 = (float)Math.sqrt(floatValue15 * floatValue15 + floatValue16 * floatValue16 + floatValue17 * floatValue17);
            float floatValue25 = 1.0F - measure2(2.8F, 24.0F, floatValue24);
            float floatValue26 = this.floatValue2 * (0.9F + floatValue14 * 0.24F + floatValue25 * 0.88F);
            float floatValue27 = floatValue26 * 0.5F;
            int intValue2 = compute(floatValue12 * 255.0F);
            VertexConsumer vertexConsumer2 = vertexConsumers.getBuffer(StardustShaderRegistry.getRENDER_LAYER());
            float floatValue28 = floatValue18 * floatValue27;
            float floatValue29 = floatValue19 * floatValue27;
            float floatValue30 = floatValue20 * floatValue27;
            float floatValue31 = floatValue21 * floatValue27;
            float floatValue32 = floatValue22 * floatValue27;
            float floatValue33 = floatValue23 * floatValue27;
            this.invoke4(vertexConsumer2, floatValue15 - floatValue28 - floatValue31, floatValue16 - floatValue29 - floatValue32, floatValue17 - floatValue30 - floatValue33, 0.0F, 1.0F, intValue2);
            this.invoke4(vertexConsumer2, floatValue15 + floatValue28 - floatValue31, floatValue16 + floatValue29 - floatValue32, floatValue17 + floatValue30 - floatValue33, 1.0F, 1.0F, intValue2);
            this.invoke4(vertexConsumer2, floatValue15 + floatValue28 + floatValue31, floatValue16 + floatValue29 + floatValue32, floatValue17 + floatValue30 + floatValue33, 1.0F, 0.0F, intValue2);
            this.invoke4(vertexConsumer2, floatValue15 - floatValue28 + floatValue31, floatValue16 - floatValue29 + floatValue32, floatValue17 - floatValue30 + floatValue33, 0.0F, 0.0F, intValue2);
         }
      }
   }

   private static boolean check(ClientWorld clientWorld) {
      if (clientWorld == null) {
         flag = false;
         return false;
      } else {
         long longValue = clientWorld.getTime();
         if (clientWorld == clientWorld && longValue == timestamp) {
            return flag;
         } else {
            FloatingStardustParticle.clientWorld = clientWorld;
            timestamp = longValue;
            MinecraftClient client = MinecraftClient.getInstance();
            if (client != null && client.gameRenderer != null && client.gameRenderer.getCamera() != null) {
               Vec3d vec3d = client.gameRenderer.getCamera().getPos();
               double doubleValue7 = Stardust.measure3() + 5.0F;
               doubleValue = vec3d.x;
               doubleValue2 = vec3d.y;
               doubleValue3 = vec3d.z;
               doubleValue4 = doubleValue7 * doubleValue7;
               floatValue = Stardust.measure4();
               flag = true;
               return true;
            } else {
               flag = false;
               return false;
            }
         }
      }
   }

   private void invoke() {
      float floatValue34 = this.floatValue6 * FLOAT_VALUE_4 + this.floatValue7 * FLOAT_VALUE_3;
      this.floatValue7 = this.floatValue7 * FLOAT_VALUE_4 - this.floatValue6 * FLOAT_VALUE_3;
      this.floatValue6 = floatValue34;
      floatValue34 = this.floatValue8 * FLOAT_VALUE_6 + this.floatValue9 * FLOAT_VALUE_5;
      this.floatValue9 = this.floatValue9 * FLOAT_VALUE_6 - this.floatValue8 * FLOAT_VALUE_5;
      this.floatValue8 = floatValue34;
      floatValue34 = this.floatValue10 * FLOAT_VALUE_4 + this.floatValue11 * FLOAT_VALUE_3;
      this.floatValue11 = this.floatValue11 * FLOAT_VALUE_4 - this.floatValue10 * FLOAT_VALUE_3;
      this.floatValue10 = floatValue34;
   }

   private void invoke2() {
      float floatValue35 = this.floatValue14;
      float floatValue36 = this.floatValue13 * this.floatValue5 - this.floatValue12 * this.floatValue4;
      this.floatValue12 = floatValue35;
      this.floatValue13 = floatValue36;
      this.invoke3();
   }

   private void invoke3() {
      this.floatValue14 = this.floatValue12 * this.floatValue5 + this.floatValue13 * this.floatValue4;
   }

   public void markDead() {
      if (this.flag2) {
         this.flag2 = false;
         if (this.intValue3 == intValue2 && intValue > 0) {
            intValue--;
         }
      }

      super.markDead();
   }

   private void invoke4(VertexConsumer vertexConsumer, float f, float g, float h, float i, float j, int k) {
      vertexConsumer.vertex(f, g, h).texture(i, j).color(this.intValue4, this.intValue5, this.intValue6, k).normal(0.0F, 1.0F, 0.0F);
   }

   public static int getIntValue() {
      return intValue;
   }

   public static void invoke5() {
      intValue = 0;
      intValue2++;
   }

   private static int compute(float f) {
      if (f <= 0.0F) {
         return 0;
      } else {
         return f >= 255.0F ? 255 : (int)f;
      }
   }

   private static float measure(float f, float g, float h) {
      return f + (g - f) * h;
   }

   private static int compute2(int i, int j) {
      return i >>> j & 0xFF;
   }

   private static float measure2(float f, float g, float h) {
      float floatValue37 = (h - f) / (g - f);
      if (floatValue37 <= 0.0F) {
         return 0.0F;
      } else {
         return floatValue37 >= 1.0F ? 1.0F : floatValue37 * floatValue37 * (3.0F - 2.0F * floatValue37);
      }
   }

   private static float measure3(float f) {
      return measure4((float)Math.sin(f * 12.9898F + 78.233F) * 43758.547F);
   }

   private static float measure4(float f) {
      return f - (float)Math.floor(f);
   }

   public static final class FloatingStardustParticleState implements ParticleFactory<SimpleParticleType> {
      @Override
      public Particle createParticle(SimpleParticleType simpleParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
         return new FloatingStardustParticle(clientWorld, d, e, f, g, h, i);
      }
   }
}
