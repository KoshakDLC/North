package ru.metaculture.protection;

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

public final class ShootingStarParticle extends SpriteBillboardParticle {
   public static SimpleParticleType simpleParticleType = StardustParticleRegistry.SIMPLE_PARTICLE_TYPE_2;
   private static int intValue;
   private static int intValue2;
   private final int intValue3;
   private final float floatValue;
   private final float floatValue2;
   private final float floatValue3;
   private final float floatValue4;
   private final float floatValue5;
   private final float floatValue6;
   private final float floatValue7;
   private final int intValue4;
   private final int intValue5;
   private final int intValue6;
   private float floatValue8;
   private float floatValue9;
   private float floatValue10;
   private boolean flag = true;

   public ShootingStarParticle(ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
      super(clientWorld, d, e, f, 0.0, 0.0, 0.0);
      float floatValue = measure4((float)(d * 0.047 + e * 0.131 + f * 0.089));
      float floatValue2 = measure4(floatValue * 43.19F + (float)g * 7.71F);
      float floatValue3 = measure2((float)g, (float)h, (float)i);
      if (floatValue3 <= 0.0F) {
         g = 0.028;
         h = -0.07;
         i = 0.018;
         floatValue3 = measure2((float)g, (float)h, (float)i);
      }

      this.floatValue = 0.036F + floatValue * 0.026F;
      this.floatValue2 = 3.4F + floatValue2 * 3.2F;
      this.floatValue3 = (float)g * floatValue3;
      this.floatValue4 = (float)h * floatValue3;
      this.floatValue5 = (float)i * floatValue3;
      float floatValue4 = 0.2F + floatValue * 0.18F;
      this.floatValue6 = (float)Math.sin(floatValue4);
      this.floatValue7 = (float)Math.cos(floatValue4);
      this.floatValue8 = 0.0F;
      this.floatValue9 = 1.0F;
      this.invoke2();
      int intValue = floatValue < 0.5F ? Stardust.getIntValue7() : Stardust.getIntValue8();
      this.intValue4 = compute(measure(214.0F, compute2(intValue, 16), 0.44F + floatValue * 0.28F) + 18.0F);
      this.intValue5 = compute(measure(236.0F, compute2(intValue, 8), 0.38F + floatValue2 * 0.3F) + 12.0F);
      this.intValue6 = compute(measure(255.0F, compute2(intValue, 0), 0.32F + floatValue2 * 0.24F));
      this.intValue3 = intValue2;
      this.maxAge = 34 + (int)(floatValue2 * 28.0F);
      this.collidesWithWorld = false;
      this.gravityStrength = 0.0F;
      this.velocityMultiplier = 0.986F;
      this.setVelocity(g, h, i);
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
         this.x = this.x + this.velocityX;
         this.y = this.y + this.velocityY;
         this.z = this.z + this.velocityZ;
         this.velocityX *= 0.985;
         this.velocityY *= 0.985;
         this.velocityZ *= 0.985;
         this.invoke();
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
         float floatValue5 = (this.age + tickProgress) / this.maxAge;
         float floatValue6 = measure3(0.0F, 0.12F, floatValue5) * (1.0F - measure3(0.7F, 1.0F, floatValue5)) * Stardust.measure() * 0.92F;
         if (!(floatValue6 <= 0.003F)) {
            ParticleBillboardRenderer.invoke(camera);
            double doubleValue = this.lastX + (this.x - this.lastX) * tickProgress;
            double doubleValue2 = this.lastY + (this.y - this.lastY) * tickProgress;
            double doubleValue3 = this.lastZ + (this.z - this.lastZ) * tickProgress;
            float floatValue7 = (float)(doubleValue - ParticleBillboardRenderer.getDoubleValue4());
            float floatValue8 = (float)(doubleValue2 - ParticleBillboardRenderer.getDoubleValue5());
            float floatValue9 = (float)(doubleValue3 - ParticleBillboardRenderer.getDoubleValue6());
            float floatValue10 = ParticleBillboardRenderer.getFloatValue5();
            float floatValue11 = ParticleBillboardRenderer.getFloatValue6();
            float floatValue12 = ParticleBillboardRenderer.getFloatValue7();
            float floatValue13 = ParticleBillboardRenderer.getFloatValue8();
            float floatValue14 = ParticleBillboardRenderer.getFloatValue9();
            float floatValue15 = ParticleBillboardRenderer.getFloatValue10();
            float floatValue16 = this.floatValue3;
            float floatValue17 = this.floatValue4;
            float floatValue18 = this.floatValue5;
            float floatValue19 = floatValue16 * floatValue10 + floatValue17 * floatValue11 + floatValue18 * floatValue12;
            float floatValue20 = floatValue16 * floatValue13 + floatValue17 * floatValue14 + floatValue18 * floatValue15;
            float floatValue21 = measure2(floatValue19, floatValue20, 0.0F);
            if (floatValue21 <= 0.0F) {
               floatValue19 = 1.0F;
               floatValue20 = 0.0F;
               floatValue21 = 1.0F;
            }

            floatValue19 *= floatValue21;
            floatValue20 *= floatValue21;
            float floatValue22 = floatValue10 * floatValue19 + floatValue13 * floatValue20;
            float floatValue23 = floatValue11 * floatValue19 + floatValue14 * floatValue20;
            float floatValue24 = floatValue12 * floatValue19 + floatValue15 * floatValue20;
            float floatValue25 = floatValue10 * -floatValue20 + floatValue13 * floatValue19;
            float floatValue26 = floatValue11 * -floatValue20 + floatValue14 * floatValue19;
            float floatValue27 = floatValue12 * -floatValue20 + floatValue15 * floatValue19;
            float floatValue28 = this.floatValue8 + (this.floatValue10 - this.floatValue8) * tickProgress;
            float floatValue29 = 0.84F + 0.16F * floatValue28;
            float floatValue30 = this.floatValue * floatValue29;
            float floatValue31 = this.floatValue * 0.26F;
            float floatValue32 = this.floatValue2 * (0.86F + floatValue29 * 0.24F);
            float floatValue33 = floatValue22 * floatValue30;
            float floatValue34 = floatValue23 * floatValue30;
            float floatValue35 = floatValue24 * floatValue30;
            float floatValue36 = floatValue22 * floatValue32;
            float floatValue37 = floatValue23 * floatValue32;
            float floatValue38 = floatValue24 * floatValue32;
            float floatValue39 = floatValue25 * floatValue30;
            float floatValue40 = floatValue26 * floatValue30;
            float floatValue41 = floatValue27 * floatValue30;
            float floatValue42 = floatValue25 * floatValue31;
            float floatValue43 = floatValue26 * floatValue31;
            float floatValue44 = floatValue27 * floatValue31;
            int intValue2 = compute(floatValue6 * 255.0F);
            VertexConsumer vertexConsumer2 = vertexConsumers.getBuffer(StardustShaderRegistry.getRENDER_LAYER());
            this.invoke3(vertexConsumer2, floatValue7 - floatValue36 - floatValue42, floatValue8 - floatValue37 - floatValue43, floatValue9 - floatValue38 - floatValue44, 0.0F, 1.0F, intValue2);
            this.invoke3(vertexConsumer2, floatValue7 + floatValue33 - floatValue39, floatValue8 + floatValue34 - floatValue40, floatValue9 + floatValue35 - floatValue41, 1.0F, 0.0F, intValue2);
            this.invoke3(vertexConsumer2, floatValue7 + floatValue33 + floatValue39, floatValue8 + floatValue34 + floatValue40, floatValue9 + floatValue35 + floatValue41, 1.0F, 0.0F, intValue2);
            this.invoke3(vertexConsumer2, floatValue7 - floatValue36 + floatValue42, floatValue8 - floatValue37 + floatValue43, floatValue9 - floatValue38 + floatValue44, 0.0F, 1.0F, intValue2);
         }
      }
   }

   private void invoke() {
      float floatValue45 = this.floatValue10;
      float floatValue46 = this.floatValue9 * this.floatValue7 - this.floatValue8 * this.floatValue6;
      this.floatValue8 = floatValue45;
      this.floatValue9 = floatValue46;
      this.invoke2();
   }

   private void invoke2() {
      this.floatValue10 = this.floatValue8 * this.floatValue7 + this.floatValue9 * this.floatValue6;
   }

   public void markDead() {
      if (this.flag) {
         this.flag = false;
         if (this.intValue3 == intValue2 && intValue > 0) {
            intValue--;
         }
      }

      super.markDead();
   }

   private void invoke3(VertexConsumer vertexConsumer, float f, float g, float h, float i, float j, int k) {
      vertexConsumer.vertex(f, g, h).texture(i, j).color(this.intValue4, this.intValue5, this.intValue6, k).normal(1.0F, 0.0F, 0.0F);
   }

   public static int getIntValue() {
      return intValue;
   }

   public static void invoke4() {
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
      float floatValue47 = f * f + g * g + h * h;
      return floatValue47 <= 1.0E-8F ? 0.0F : (float)(1.0 / Math.sqrt(floatValue47));
   }

   private static float measure3(float f, float g, float h) {
      float floatValue48 = (h - f) / (g - f);
      if (floatValue48 <= 0.0F) {
         return 0.0F;
      } else {
         return floatValue48 >= 1.0F ? 1.0F : floatValue48 * floatValue48 * (3.0F - 2.0F * floatValue48);
      }
   }

   private static float measure4(float f) {
      return measure5((float)Math.sin(f * 12.9898F + 78.233F) * 43758.547F);
   }

   private static float measure5(float f) {
      return f - (float)Math.floor(f);
   }

   public static final class ShootingStarParticleState implements ParticleFactory<SimpleParticleType> {
      @Override
      public Particle createParticle(SimpleParticleType simpleParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
         return new ShootingStarParticle(clientWorld, d, e, f, g, h, i);
      }
   }
}
