package ru.metaculture.protection;

import com.mojang.blaze3d.opengl.GlStateManager;
import com.mojang.blaze3d.pipeline.RenderPipeline;
import java.awt.Color;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.item.ItemRenderState;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteContents;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ProfileComponent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemDisplayContext;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

public final class ItemRenderUtil {
   private static final MinecraftClient CLIENT = MinecraftClient.getInstance();
   private static final ItemRenderState ITEM_RENDER_STATE = new ItemRenderState();
   private static final Random RANDOM = Random.create(0L);
   private static final String[] TEXT = resolve11();
   private static final Set<Integer> VALUES = ConcurrentHashMap.newKeySet();
   private static final Map<String, ItemRenderUtil.ItemRenderUtilTimedEntry> VALUES_BY_KEY = new ConcurrentHashMap<>();
   private static final long TIMESTAMP = 5000L;
   private static Field field;

   private ItemRenderUtil() {
   }

   public static void invoke(DrawContext drawContext, ItemStack itemStack, float f, float g, float h, int i, boolean bl) {
      invoke2(drawContext, CLIENT.player, itemStack, f, g, h, i, bl);
   }

   public static void invoke2(DrawContext drawContext, PlayerEntity playerEntity, ItemStack itemStack, float f, float g, float h, int i, boolean bl) {
      Object object = playerEntity != null ? playerEntity : CLIENT.player;
      if (drawContext != null && object != null && itemStack != null && !itemStack.isEmpty()) {
         float floatValue = CLIENT.getWindow().getScaleFactor();
         float floatValue2 = measure(f);
         float floatValue3 = measure(g);
         float floatValue4 = measure3(h);
         drawContext.getMatrices().pushMatrix();
         drawContext.getMatrices().translate(floatValue2 / floatValue, floatValue3 / floatValue);
         drawContext.getMatrices().scale(floatValue4 / floatValue, floatValue4 / floatValue);
         drawContext.drawItem((LivingEntity)object, itemStack, 0, 0, i);
         if (bl) {
            drawContext.drawStackOverlay(CLIENT.textRenderer, itemStack, 0, 0);
         }

         drawContext.getMatrices().popMatrix();
      }
   }

   public static void invoke3(RenderManager renderManager, ItemStack itemStack, float f, float g, float h, int i, boolean bl, int j) {
      if (renderManager != null && CLIENT.player != null && itemStack != null && !itemStack.isEmpty()) {
         if (check(renderManager, itemStack, f, g, h)) {
            if (bl) {
               invoke6(renderManager, itemStack, f, g, h);
            }
         } else {
            try {
               ITEM_RENDER_STATE.clear();
               CLIENT.getItemModelManager().clearAndUpdate(ITEM_RENDER_STATE, itemStack, ItemDisplayContext.GUI, CLIENT.world, CLIENT.player, i);
               RANDOM.setSeed(i);
               Sprite sprite = ITEM_RENDER_STATE.getParticleSprite(RANDOM);
               if (sprite == null) {
                  return;
               }

               AbstractTexture abstractTexture = CLIENT.getTextureManager().getTexture(sprite.getAtlasId());
               if (abstractTexture == null || !(abstractTexture.getGlTexture() instanceof GlTexture glTexture && glTexture.getGlId() > 0)) {
                  return;
               }

               renderManager.invoke12(
                  glTexture.getGlId(), f, g, 16.0F * h, 16.0F * h, sprite.getMinU(), sprite.getMinV(), sprite.getMaxU(), sprite.getMaxV(), Math.max(2.0F, 4.0F * h)
               );
            } catch (Throwable exception) {
            }

            if (bl) {
               invoke6(renderManager, itemStack, f, g, h);
            }
         }
      }
   }

   public static int[] resolve(ItemStack itemStack, int i) {
      if (itemStack != null && !itemStack.isEmpty() && CLIENT.player != null) {
         long longValue = System.currentTimeMillis();
         String text = resolve9(itemStack, i);
         ItemRenderUtil.ItemRenderUtilTimedEntry itemRenderUtilTimedEntry = VALUES_BY_KEY.get(text);
         if (itemRenderUtilTimedEntry != null && longValue - itemRenderUtilTimedEntry.createdAt <= 5000L) {
            return resolve10(itemRenderUtilTimedEntry.colors);
         } else {
            int[] intValues = resolve4(itemStack);
            if (intValues == null) {
               intValues = resolve3(itemStack, i);
            }

            VALUES_BY_KEY.put(text, new ItemRenderUtil.ItemRenderUtilTimedEntry(resolve10(intValues), longValue));
            return intValues;
         }
      } else {
         return null;
      }
   }

   public static int[] resolve2() {
      return new int[]{RenderManager.RenderManagerState.compute32(220, 255, 245, 255), RenderManager.RenderManagerState.compute32(142, 226, 255, 255)};
   }

   private static int[] resolve3(ItemStack itemStack, int i) {
      try {
         ITEM_RENDER_STATE.clear();
         CLIENT.getItemModelManager().clearAndUpdate(ITEM_RENDER_STATE, itemStack, ItemDisplayContext.GUI, CLIENT.world, CLIENT.player, i);
         RANDOM.setSeed(i);
         Sprite sprite2 = ITEM_RENDER_STATE.getParticleSprite(RANDOM);
         if (sprite2 == null) {
            return null;
         } else {
            NativeImage nativeImage2 = resolve5(sprite2.getContents());
            if (nativeImage2 == null) {
               return null;
            } else {
               int intValue = Math.min(sprite2.getContents().getWidth(), nativeImage2.getWidth());
               int intValue2 = Math.min(sprite2.getContents().getHeight(), nativeImage2.getHeight());
               return resolve6(nativeImage2, 0, 0, intValue, intValue2);
            }
         }
      } catch (Throwable exception2) {
         return null;
      }
   }

   private static int[] resolve4(ItemStack itemStack) {
      if (!itemStack.isOf(Items.PLAYER_HEAD)) {
         return null;
      } else {
         try {
            ProfileComponent profileComponent = (ProfileComponent)itemStack.get(DataComponentTypes.PROFILE);
            if (profileComponent == null) {
               return null;
            } else {
               SkinTextures skinTextures = CLIENT.getSkinProvider().getSkinTextures(profileComponent.gameProfile());
               if (skinTextures != null && skinTextures.texture() != null) {
                  if (CLIENT.getTextureManager().getTexture(skinTextures.texture()) instanceof NativeImageBackedTexture nativeImageBackedTexture) {
                     NativeImage nativeImage3 = nativeImageBackedTexture.getImage();
                     if (nativeImage3 != null && nativeImage3.getWidth() >= 16 && nativeImage3.getHeight() >= 16) {
                        int[] intValues2 = resolve6(nativeImage3, 8, 8, 8, 8);
                        int[] intValues3 = nativeImage3.getWidth() >= 48 ? resolve6(nativeImage3, 40, 8, 8, 8) : null;
                        return resolve8(intValues2, intValues3);
                     } else {
                        return null;
                     }
                  } else {
                     return null;
                  }
               } else {
                  return null;
               }
            }
         } catch (Throwable exception3) {
            return null;
         }
      }
   }

   private static boolean check(RenderManager renderManager2, ItemStack itemStack, float f, float g, float h) {
      if (!itemStack.isOf(Items.PLAYER_HEAD)) {
         return false;
      } else {
         try {
            ProfileComponent profileComponent2 = (ProfileComponent)itemStack.get(DataComponentTypes.PROFILE);
            if (profileComponent2 == null) {
               return false;
            } else {
               SkinTextures skinTextures2 = CLIENT.getSkinProvider().getSkinTextures(profileComponent2.gameProfile());
               if (skinTextures2 != null && skinTextures2.texture() != null) {
                  AbstractTexture abstractTexture2 = CLIENT.getTextureManager().getTexture(skinTextures2.texture());
                  if (abstractTexture2 != null && abstractTexture2.getGlTexture() instanceof GlTexture glTexture2 && glTexture2.getGlId() > 0) {
                     float floatValue5 = 16.0F * h;
                     float floatValue6 = Math.max(2.0F, 4.0F * h);
                     int intValue3 = glTexture2.getGlId();
                     renderManager2.invoke12(intValue3, f, g, floatValue5, floatValue5, 0.125F, 0.125F, 0.25F, 0.25F, floatValue6);
                     renderManager2.invoke12(intValue3, f, g, floatValue5, floatValue5, 0.625F, 0.125F, 0.75F, 0.25F, floatValue6);
                     return true;
                  } else {
                     return false;
                  }
               } else {
                  return false;
               }
            }
         } catch (Throwable exception4) {
            return false;
         }
      }
   }

   private static NativeImage resolve5(SpriteContents spriteContents) {
      if (spriteContents == null) {
         return null;
      } else {
         try {
            Field imageField = field;
            if (imageField == null) {
               for (Field field2 : SpriteContents.class.getDeclaredFields()) {
                  if (field2.getType() == NativeImage.class) {
                     imageField = field2;
                     break;
                  }
               }

               if (imageField == null) {
                  return null;
               }

               imageField.setAccessible(true);
               field = imageField;
            }

            return imageField.get(spriteContents) instanceof NativeImage nativeImage4 ? nativeImage4 : null;
         } catch (Throwable exception5) {
            return null;
         }
      }
   }

   private static int[] resolve6(NativeImage nativeImage, int i, int j, int k, int l) {
      if (nativeImage != null && k > 0 && l > 0) {
         ItemRenderUtil.ItemRenderUtilState[] w148s = new ItemRenderUtil.ItemRenderUtilState[24];

         for (int intValue4 = 0; intValue4 < w148s.length; intValue4++) {
            w148s[intValue4] = new ItemRenderUtil.ItemRenderUtilState();
         }

         int intValue5 = Math.min(nativeImage.getWidth(), i + k);
         int intValue6 = Math.min(nativeImage.getHeight(), j + l);

         for (int intValue7 = Math.max(0, j); intValue7 < intValue6; intValue7++) {
            for (int intValue8 = Math.max(0, i); intValue8 < intValue5; intValue8++) {
               int intValue9 = nativeImage.getColorArgb(intValue8, intValue7);
               int intValue10 = intValue9 >>> 24 & 0xFF;
               if (intValue10 >= 40) {
                  int intValue11 = intValue9 >>> 16 & 0xFF;
                  int intValue12 = intValue9 >>> 8 & 0xFF;
                  int intValue13 = intValue9 & 0xFF;
                  float[] floatValues = Color.RGBtoHSB(intValue11, intValue12, intValue13, null);
                  if (!(floatValues[2] < 0.09F)) {
                     float floatValue7 = floatValues[1];
                     float floatValue8 = intValue10 / 255.0F * (0.25F + floatValue7 * 1.9F) * (0.35F + floatValues[2]);
                     if (floatValue7 < 0.08F) {
                        floatValue8 *= 0.2F;
                     }

                     int intValue14 = Math.min(w148s.length - 1, (int)(floatValues[0] * w148s.length));
                     w148s[intValue14].invoke(intValue11, intValue12, intValue13, floatValue8);
                  }
               }
            }
         }

         int intValue15 = compute(w148s, -1);
         if (intValue15 >= 0 && !(w148s[intValue15].floatValue4 < 0.75F)) {
            int intValue16 = compute(w148s, intValue15);
            int intValue17 = w148s[intValue15].compute();
            int intValue18 = intValue16 >= 0 && w148s[intValue16].floatValue4 >= w148s[intValue15].floatValue4 * 0.18F ? w148s[intValue16].compute() : compute3(intValue17);
            return resolve7(intValue17, intValue18);
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private static int compute(ItemRenderUtil.ItemRenderUtilState[] w148s2, int i) {
      int intValue19 = -1;
      float floatValue9 = 0.0F;

      for (int intValue20 = 0; intValue20 < w148s2.length; intValue20++) {
         if (intValue20 != i && !(w148s2[intValue20].floatValue4 <= floatValue9) && (i < 0 || compute2(intValue20, i, w148s2.length) >= 3)) {
            intValue19 = intValue20;
            floatValue9 = w148s2[intValue20].floatValue4;
         }
      }

      return intValue19;
   }

   private static int compute2(int i, int j, int k) {
      int intValue21 = Math.abs(i - j);
      return Math.min(intValue21, k - intValue21);
   }

   private static int compute3(int i) {
      float[] floatValues2 = Color.RGBtoHSB(RenderManager.RenderManagerState.compute26(i), RenderManager.RenderManagerState.compute27(i), RenderManager.RenderManagerState.compute28(i), null);
      float floatValue10 = (floatValues2[0] + 0.08F) % 1.0F;
      int intValue22 = Color.HSBtoRGB(floatValue10, Math.min(1.0F, floatValues2[1] * 1.08F), Math.min(1.0F, floatValues2[2] * 1.18F));
      return RenderManager.RenderManagerState.compute32(intValue22 >>> 16 & 0xFF, intValue22 >>> 8 & 0xFF, intValue22 & 0xFF, 255);
   }

   private static int[] resolve7(int i, int j) {
      int intValue23 = compute4(i);
      int intValue24 = compute4(j);
      if (compute5(intValue23, intValue24) < 42) {
         intValue24 = compute3(intValue23);
         intValue24 = compute4(intValue24);
      }

      return new int[]{intValue23, intValue24};
   }

   private static int compute4(int i) {
      int intValue25 = RenderManager.RenderManagerState.compute26(i);
      int intValue26 = RenderManager.RenderManagerState.compute27(i);
      int intValue27 = RenderManager.RenderManagerState.compute28(i);
      float[] floatValues3 = Color.RGBtoHSB(intValue25, intValue26, intValue27, null);
      float floatValue11 = intValue25 * 0.2126F + intValue26 * 0.7152F + intValue27 * 0.0722F;
      if (floatValues3[1] < 0.1F && floatValue11 < 150.0F) {
         return resolve2()[0];
      } else {
         float floatValue12 = Math.min(1.0F, Math.max(floatValues3[1] * 1.12F, 0.38F));
         float floatValue13 = Math.min(1.0F, Math.max(floatValues3[2], 0.78F));
         if (floatValue11 < 115.0F) {
            floatValue13 = Math.max(floatValue13, 0.88F);
         }

         int intValue28 = Color.HSBtoRGB(floatValues3[0], floatValue12, floatValue13);
         return RenderManager.RenderManagerState.compute32(intValue28 >>> 16 & 0xFF, intValue28 >>> 8 & 0xFF, intValue28 & 0xFF, 255);
      }
   }

   private static int compute5(int i, int j) {
      int intValue29 = RenderManager.RenderManagerState.compute26(i) - RenderManager.RenderManagerState.compute26(j);
      int intValue30 = RenderManager.RenderManagerState.compute27(i) - RenderManager.RenderManagerState.compute27(j);
      int intValue31 = RenderManager.RenderManagerState.compute28(i) - RenderManager.RenderManagerState.compute28(j);
      return Math.abs(intValue29) + Math.abs(intValue30) + Math.abs(intValue31);
   }

   private static int[] resolve8(int[] is, int[] js) {
      if (is == null) {
         return js;
      } else {
         return js == null ? is : new int[]{is[0], js[1]};
      }
   }

   private static String resolve9(ItemStack itemStack, int i) {
      return itemStack.getItem() + "|" + itemStack.getName().getString() + "|" + itemStack.getComponents() + "|" + i;
   }

   private static int[] resolve10(int[] is) {
      return is == null ? null : new int[]{is[0], is[1]};
   }

   public static void invoke4(Identifier identifier) {
      if (identifier != null) {
         try {
            AbstractTexture abstractTexture3 = CLIENT.getTextureManager().getTexture(identifier);
            if (abstractTexture3 != null && abstractTexture3.getGlTexture() instanceof GlTexture glTexture3 && glTexture3.getGlId() > 0) {
               int intValue32 = glTexture3.getGlId();
               if (VALUES.add(intValue32)) {
                  GlStateManager._bindTexture(intValue32);
                  GlStateManager._texParameter(3553, 10241, 9728);
                  GlStateManager._texParameter(3553, 10240, 9728);
               }
            }
         } catch (Throwable exception6) {
         }
      }
   }

   public static float measure(float f) {
      return Math.round(f);
   }

   public static float measure2(float f) {
      return Math.max(1.0F, (float)Math.round(f));
   }

   public static float measure3(float f) {
      return Float.isFinite(f) && !(f <= 0.0F) ? Math.max(0.0625F, Math.round(f * 16.0F) / 16.0F) : 1.0F;
   }

   public static void invoke5(DrawContext drawContext, RenderPipeline renderPipeline, Identifier identifier, float f, float g, float h, int i, int j) {
      if (drawContext != null && renderPipeline != null && identifier != null) {
         float floatValue14 = CLIENT.getWindow().getScaleFactor();
         drawContext.getMatrices().pushMatrix();
         drawContext.getMatrices().translate(f / floatValue14, g / floatValue14);
         drawContext.getMatrices().scale(h / floatValue14, h / floatValue14);
         drawContext.drawTexture(renderPipeline, identifier, 0, 0, 0.0F, 0.0F, i, j, i, j);
         drawContext.getMatrices().popMatrix();
      }
   }

   private static void invoke6(RenderManager renderManager3, ItemStack itemStack, float f, float g, float h) {
      if (itemStack.getCount() > 1) {
         int intValue33 = itemStack.getCount();
         String text2 = intValue33 >= 0 && intValue33 < TEXT.length ? TEXT[intValue33] : "999+";
         float floatValue15 = Math.max(7.0F, 18.0F * h);
         float floatValue16 = TextMeasureCache.resolve(FontRegistry.fontObject4, text2, floatValue15).floatValue;
         float floatValue17 = f + 16.0F * h - floatValue16 - 1.0F * h;
         float floatValue18 = g + 15.0F * h;
         renderManager3.invoke69(FontRegistry.fontObject4, floatValue17, floatValue18, floatValue15, text2, RenderManager.RenderManagerState.compute32(255, 255, 255, 255));
      }
   }

   private static String[] resolve11() {
      String[] texts = new String[1000];

      for (int intValue34 = 0; intValue34 < texts.length; intValue34++) {
         texts[intValue34] = Integer.toString(intValue34);
      }

      return texts;
   }

   record ItemRenderUtilTimedEntry(int[] colors, long createdAt) {
   }

   static final class ItemRenderUtilState {
      private float floatValue;
      private float floatValue2;
      private float floatValue3;
      float floatValue4;

      void invoke(int i, int j, int k, float f) {
         this.floatValue += i * f;
         this.floatValue2 += j * f;
         this.floatValue3 += k * f;
         this.floatValue4 += f;
      }

      int compute() {
         return this.floatValue4 <= 0.0F
            ? RenderManager.RenderManagerState.compute32(220, 255, 245, 255)
            : RenderManager.RenderManagerState.compute32(
               Math.round(this.floatValue / this.floatValue4),
               Math.round(this.floatValue2 / this.floatValue4),
               Math.round(this.floatValue3 / this.floatValue4),
               255
            );
      }
   }
}
