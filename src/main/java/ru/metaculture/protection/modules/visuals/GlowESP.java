package ru.metaculture.protection;

import com.mojang.blaze3d.systems.RenderSystem;
import java.util.function.Predicate;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix4f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.glfw.GLFW;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "GlowESP",
   description = "Шейдерная градиентная обводка игроков",
   category = Category.Visuals
)
public final class GlowESP extends Module {
   private static final String GLOW_ESP = "glow_esp";
   private static final String GLOW_ESP_FRIENDS = "glow_esp_friends";
   private static final GlowEspRenderer.GlowEspRendererBounds GLOW_ESP_RENDERER_BOUNDS = new GlowEspRenderer.GlowEspRendererBounds(0, 0, Integer.MAX_VALUE, Integer.MAX_VALUE);
   public final GroupSetting tseli = new GroupSetting(
      "Цели", new BooleanSetting("Игроки", true), new BooleanSetting("Мобы", false), new BooleanSetting("Предметы", false), new BooleanSetting("Себя", false)
   );
   public final BooleanSetting nevidimye = new BooleanSetting("Невидимые", true);
   public final NumberSetting distantsiya = new NumberSetting("Дистанция", 96.0F, 8.0F, 256.0F, 1.0F, false);
   public final ModeSetting effekt = new ModeSetting("Эффект", "Свечение + контур", "Свечение + контур", "Свечение", "Контур");
   public final NumberSetting razmerSvecheniya = new NumberSetting("Размер свечения", 10.0F, 2.0F, 32.0F, 1.0F, false).setVisibilityCondition(this::check4);
   public final NumberSetting yarkostSvecheniya = new NumberSetting("Яркость свечения", 2.0F, 0.25F, 5.0F, 0.05F, false).setVisibilityCondition(this::check4);
   public final NumberSetting tolschinaKontura = new NumberSetting("Толщина контура", 2.0F, 0.5F, 6.0F, 0.5F, false).setVisibilityCondition(this::check3);
   public final NumberSetting prozrachnost = new NumberSetting("Прозрачность", 0.92F, 0.05F, 1.0F, 0.01F, true);
   public final ModeSetting rezhimTsveta = new ModeSetting("Режим цвета", "Градиент", "Градиент", "Статичный");
   public final ModeSetting istochnikTsveta = new ModeSetting("Источник цвета", "Тема", "Тема", "Свой");
   public final ColorSetting osnovnoyTsvet = new ColorSetting("Основной цвет", 55.0F, 0.72F, 1.0F).setVisibilityCondition(() -> this.istochnikTsveta.is("Тема"));
   public final ColorSetting vtoroyTsvet = new ColorSetting("Второй цвет", 76.0F, 0.78F, 1.0F)
      .setVisibilityCondition(() -> this.istochnikTsveta.is("Тема") || this.rezhimTsveta.is("Статичный"));
   public final BooleanSetting vydelyatDruzey = new BooleanSetting("Выделять друзей", true);
   public final ColorSetting tsvetDruzey = new ColorSetting("Цвет друзей", 40.0F, 0.8F, 1.0F).setVisibilityCondition(() -> !this.vydelyatDruzey.isEnabled());
   private final Predicate<Entity> predicate = this::check;
   private final Predicate<Entity> predicate2 = entity -> this.check(entity) && this.check5(entity);
   private GlowEspRenderer glowEspRenderer;
   private static final int INT_VALUE = 0;
   private static final int INT_VALUE_2 = 1;
   private static final int INT_VALUE_3 = 2;
   private final Matrix4f matrix4f = new Matrix4f();
   private final Matrix4f matrix4f2 = new Matrix4f();
   private final Vector4f vector4f = new Vector4f();
   private final Vector3f vector3f = new Vector3f();
   private final int[] ints = new int[]{0, 0, 0, 0};
   private final float[] floats = new float[4];
   private final float[] floats2 = new float[3];
   private final float[] floats3 = new float[3];
   private final float[] floats4 = new float[3];
   private final float[] floats5 = new float[4];
   private boolean flag;
   private GlowEspRenderer.GlowEspRendererBounds glowEspRendererBounds;

   public GlowESP() {
      this.addSettings(
         new Setting[]{
            this.tseli,
            this.nevidimye,
            this.distantsiya,
            this.effekt,
            this.razmerSvecheniya,
            this.yarkostSvecheniya,
            this.tolschinaKontura,
            this.prozrachnost,
            this.rezhimTsveta,
            this.istochnikTsveta,
            this.osnovnoyTsvet,
            this.vtoroyTsvet,
            this.vydelyatDruzey,
            this.tsvetDruzey
         }
      );
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.invoke3();
   }

   @Override
   public void onDisable() {
      EntityFramebufferCapture entityFramebufferCapture = EntityFramebufferCapture.getInstance();
      entityFramebufferCapture.removeTagFilter("glow_esp_friends");
      entityFramebufferCapture.removeCaptureFilter("glow_esp");
      this.invoke6();
      super.onDisable();
   }

   @EventHandler
   public void onRender3D(Render3DEvent render3DEvent) {
      this.invoke3();
   }

   @EventHandler(
      priority = 0
   )
   public void onHudRender(HudRenderEvent hudRenderEvent) {
      this.invoke3();
      if (!IrisCompatibility.check()) {
         if (hudRenderEvent != null
            && hudRenderEvent.getClient() != null
            && CLIENT.world != null
            && CLIENT.player != null
            && CLIENT.getWindow() != null
            && !CLIENT.getWindow().hasZeroWidthOrHeight()) {
            int intValue = hudRenderEvent.getIntValue();
            int intValue2 = hudRenderEvent.getIntValue2();
            if (intValue > 0 && intValue2 > 0) {
               this.invoke2(intValue, intValue2, hudRenderEvent.getRenderManager());
            }
         }
      }
   }

   public void invoke() {
      if (this.enabled
         && IrisCompatibility.check()
         && CLIENT.world != null
         && CLIENT.player != null
         && CLIENT.getWindow() != null
         && !CLIENT.getWindow().hasZeroWidthOrHeight()) {
         int intValue3 = CLIENT.getWindow().getFramebufferWidth();
         int intValue4 = CLIENT.getWindow().getFramebufferHeight();
         if (intValue3 > 0 && intValue4 > 0) {
            this.invoke2(intValue3, intValue4, null);
         }
      }
   }

   private void invoke2(int i, int j, RenderManager renderManager) {
      EntityFramebufferCapture entityFramebufferCapture2 = EntityFramebufferCapture.getInstance();
      int intValue5 = entityFramebufferCapture2.getCaptureColorTextureId();
      int intValue6 = entityFramebufferCapture2.getCaptureDepthTextureId();
      if (intValue5 > 0) {
         int intValue7 = 0;
         int intValue8 = 0;
         if (this.vydelyatDruzey.isEnabled() && intValue6 > 0) {
            intValue7 = entityFramebufferCapture2.getTaggedCaptureColorTextureId();
            intValue8 = entityFramebufferCapture2.getTaggedCaptureDepthTextureId();
         }

         boolean flag = intValue7 > 0 && intValue8 > 0;
         float floatValue = this.razmerSvecheniya.getValue() * 2.0F;
         float floatValue2 = this.tolschinaKontura.getValue();
         GlowEspRenderer.GlowEspRendererBounds glowEspRendererBounds = this.resolve2(i, j, this.check4() ? 0.0F : floatValue, this.check3() ? 0.0F : floatValue2);
         if (glowEspRendererBounds != null) {
            GlowEspRenderer.GlowEspRendererBounds glowEspRendererBounds2 = glowEspRendererBounds == GLOW_ESP_RENDERER_BOUNDS ? null : glowEspRendererBounds;
            if (this.glowEspRenderer == null) {
               this.glowEspRenderer = new GlowEspRenderer();
            }

            if (renderManager != null) {
               renderManager.invoke20();
            }

            this.invoke4(this.floats2, this.floats3);
            this.glowEspRenderer.check3(intValue5, intValue6, i, j, this.resolve(this.floats2, this.floats3), glowEspRendererBounds2, intValue7, intValue8, flag ? 1 : 0);
            if (flag) {
               invoke5(this.tsvetDruzey.getColor().getRGB(), this.floats4);
               this.glowEspRenderer.check3(intValue5, intValue6, i, j, this.resolve(this.floats4, this.floats4), this.glowEspRendererBounds, intValue7, intValue8, 2);
            }

            if (renderManager != null) {
               renderManager.invoke20();
            }
         }
      }
   }

   private GlowEspRenderer.GlowEspRendererData resolve(float[] fs, float[] gs) {
      return new GlowEspRenderer.GlowEspRendererData(
         this.razmerSvecheniya.getValue() * 2.0F,
         this.tolschinaKontura.getValue(),
         this.check4() ? 0.0F : this.yarkostSvecheniya.getValue() * 2.0F,
         this.check3() ? 0.0F : 1.35F,
         this.prozrachnost.getValue(),
         0,
         this.rezhimTsveta.is("Статичный") ? 1 : 0,
         0,
         fs[0],
         fs[1],
         fs[2],
         gs[0],
         gs[1],
         gs[2]
      );
   }

   private boolean check(Entity entity) {
      if (!this.enabled || CLIENT.player == null || entity == null || !entity.isAlive() || entity.isRemoved()) {
         return false;
      } else if (entity.isInvisible() && !this.nevidimye.isEnabled()) {
         return false;
      } else {
         float floatValue3 = Math.max(1.0F, this.distantsiya.getValue());
         if (CLIENT.player.squaredDistanceTo(entity) > floatValue3 * floatValue3) {
            return false;
         } else if (entity == CLIENT.player) {
            return this.tseli.isEnabled("Себя");
         } else if (entity instanceof PlayerEntity) {
            return this.tseli.isEnabled("Игроки");
         } else {
            return entity instanceof ItemEntity
               ? this.tseli.isEnabled("Предметы")
               : entity instanceof LivingEntity && this.tseli.isEnabled("Мобы");
         }
      }
   }

   private GlowEspRenderer.GlowEspRendererBounds resolve2(int i, int j, float f, float g) {
      this.glowEspRendererBounds = null;
      this.flag = false;
      if (CLIENT.world != null && CLIENT.gameRenderer != null && i > 0 && j > 0) {
         Camera camera = CLIENT.gameRenderer.getCamera();
         if (camera == null) {
            return GLOW_ESP_RENDERER_BOUNDS;
         } else {
            Vec3d vec3d2 = camera.getPos();
            this.matrix4f.set(MathUtils.MATRIX4F_3);
            this.matrix4f2.set(MathUtils.MATRIX4F).mul(MathUtils.MATRIX4F_2);
            this.ints[0] = 0;
            this.ints[1] = 0;
            this.ints[2] = i;
            this.ints[3] = j;
            float floatValue4 = CLIENT.getRenderTickCounter().getTickProgress(true);
            boolean flag2 = this.vydelyatDruzey.isEnabled();
            float floatValue5 = Float.POSITIVE_INFINITY;
            float floatValue6 = Float.POSITIVE_INFINITY;
            float floatValue7 = Float.NEGATIVE_INFINITY;
            float floatValue8 = Float.NEGATIVE_INFINITY;
            boolean flag3 = false;

            for (Entity entity2 : CLIENT.world.getEntities()) {
               if (this.check(entity2)) {
                  int intValue9 = this.compute(entity2, floatValue4, i, j, vec3d2);
                  if (intValue9 == 2) {
                     return GLOW_ESP_RENDERER_BOUNDS;
                  }

                  if (intValue9 != 1) {
                     floatValue5 = Math.min(floatValue5, this.floats[0]);
                     floatValue6 = Math.min(floatValue6, this.floats[1]);
                     floatValue7 = Math.max(floatValue7, this.floats[2]);
                     floatValue8 = Math.max(floatValue8, this.floats[3]);
                     flag3 = true;
                     if (flag2 && this.check5(entity2)) {
                        if (this.flag) {
                           this.floats5[0] = Math.min(this.floats5[0], this.floats[0]);
                           this.floats5[1] = Math.min(this.floats5[1], this.floats[1]);
                           this.floats5[2] = Math.max(this.floats5[2], this.floats[2]);
                           this.floats5[3] = Math.max(this.floats5[3], this.floats[3]);
                        } else {
                           this.floats5[0] = this.floats[0];
                           this.floats5[1] = this.floats[1];
                           this.floats5[2] = this.floats[2];
                           this.floats5[3] = this.floats[3];
                           this.flag = true;
                        }
                     }
                  }
               }
            }

            if (this.flag) {
               this.glowEspRendererBounds = this.resolve3(this.floats5, i, j, f, g);
            }

            if (flag3 && Float.isFinite(floatValue5) && Float.isFinite(floatValue6) && Float.isFinite(floatValue7) && Float.isFinite(floatValue8)) {
               int intValue10 = (int)Math.ceil(Math.max(8.0F, f + g * 4.0F + 18.0F));
               int intValue11 = Math.max(0, (int)Math.floor(floatValue5) - intValue10);
               int intValue12 = Math.max(0, (int)Math.floor(floatValue6) - intValue10);
               int intValue13 = Math.min(i, (int)Math.ceil(floatValue7) + intValue10);
               int intValue14 = Math.min(j, (int)Math.ceil(floatValue8) + intValue10);
               int intValue15 = intValue13 - intValue11;
               int intValue16 = intValue14 - intValue12;
               return intValue15 > 2 && intValue16 > 2 ? new GlowEspRenderer.GlowEspRendererBounds(intValue11, intValue12, intValue15, intValue16) : GLOW_ESP_RENDERER_BOUNDS;
            } else {
               return null;
            }
         }
      } else {
         return GLOW_ESP_RENDERER_BOUNDS;
      }
   }

   private int compute(Entity entity, float f, int i, int j, Vec3d vec3d) {
      Vec3d vec3d3 = entity.getLerpedPos(f);
      Vec3d vec3d4 = entity.getPos();
      double doubleValue = vec3d3.x - vec3d4.x;
      double doubleValue2 = vec3d3.y - vec3d4.y;
      double doubleValue3 = vec3d3.z - vec3d4.z;
      double doubleValue4 = entity instanceof ItemEntity ? 0.45 : 0.18;
      double doubleValue5 = Math.max(0.1, doubleValue4 * 0.65);
      Box box = entity.getBoundingBox();
      double doubleValue6 = box.minX + doubleValue - doubleValue4;
      double doubleValue7 = box.maxX + doubleValue + doubleValue4;
      double doubleValue8 = box.minY + doubleValue2 - doubleValue5;
      double doubleValue9 = box.maxY + doubleValue2 + doubleValue5;
      double doubleValue10 = box.minZ + doubleValue3 - doubleValue4;
      double doubleValue11 = box.maxZ + doubleValue3 + doubleValue4;
      float floatValue9 = Float.POSITIVE_INFINITY;
      float floatValue10 = Float.POSITIVE_INFINITY;
      float floatValue11 = Float.NEGATIVE_INFINITY;
      float floatValue12 = Float.NEGATIVE_INFINITY;

      for (int intValue17 = 0; intValue17 < 2; intValue17++) {
         double doubleValue12 = intValue17 == 0 ? doubleValue6 : doubleValue7;

         for (int intValue18 = 0; intValue18 < 2; intValue18++) {
            double doubleValue13 = intValue18 == 0 ? doubleValue8 : doubleValue9;

            for (int intValue19 = 0; intValue19 < 2; intValue19++) {
               double doubleValue14 = intValue19 == 0 ? doubleValue10 : doubleValue11;
               if (!this.check2(doubleValue12, doubleValue13, doubleValue14, vec3d)) {
                  return 2;
               }

               float floatValue13 = this.vector3f.z;
               if (floatValue13 <= 0.001F || floatValue13 > 1.0F) {
                  return 2;
               }

               float floatValue14 = this.vector3f.x;
               float floatValue15 = j - this.vector3f.y;
               floatValue9 = Math.min(floatValue9, floatValue14);
               floatValue10 = Math.min(floatValue10, floatValue15);
               floatValue11 = Math.max(floatValue11, floatValue14);
               floatValue12 = Math.max(floatValue12, floatValue15);
            }
         }
      }

      if (!Float.isFinite(floatValue9) || !Float.isFinite(floatValue10) || !Float.isFinite(floatValue11) || !Float.isFinite(floatValue12)) {
         return 1;
      } else if (!(floatValue11 < 0.0F) && !(floatValue12 < 0.0F) && !(floatValue9 > i) && !(floatValue10 > j)) {
         int intValue20 = Math.max(0, (int)Math.floor(floatValue9));
         int intValue21 = Math.max(0, (int)Math.floor(floatValue10));
         int intValue22 = Math.min(i, (int)Math.ceil(floatValue11));
         int intValue23 = Math.min(j, (int)Math.ceil(floatValue12));
         if (intValue22 - intValue20 > 0 && intValue23 - intValue21 > 0) {
            this.floats[0] = intValue20;
            this.floats[1] = intValue21;
            this.floats[2] = intValue22;
            this.floats[3] = intValue23;
            return 0;
         } else {
            return 1;
         }
      } else {
         return 1;
      }
   }

   private boolean check2(double d, double e, double f, Vec3d vec3d) {
      this.vector4f.set((float)(d - vec3d.x), (float)(e - vec3d.y), (float)(f - vec3d.z), 1.0F).mul(this.matrix4f);
      this.matrix4f2.project(this.vector4f.x(), this.vector4f.y(), this.vector4f.z(), this.ints, this.vector3f);
      return Float.isFinite(this.vector3f.x) && Float.isFinite(this.vector3f.y) && Float.isFinite(this.vector3f.z);
   }

   private void invoke3() {
      EntityFramebufferCapture entityFramebufferCapture3 = EntityFramebufferCapture.getInstance();
      entityFramebufferCapture3.setCaptureFilter("glow_esp", this.enabled, this.predicate);
      boolean flag4 = this.enabled && this.vydelyatDruzey.isEnabled() && !FriendCommand.resolve().isEmpty();
      entityFramebufferCapture3.setTagFilter("glow_esp_friends", flag4, this.predicate2);
   }

   private boolean check3() {
      return this.effekt.is("Свечение");
   }

   private boolean check4() {
      return this.effekt.is("Контур");
   }

   private boolean check5(Entity entity) {
      if (entity instanceof PlayerEntity playerEntity && entity != CLIENT.player) {
         String text = playerEntity.getGameProfile() != null ? playerEntity.getGameProfile().getName() : playerEntity.getName().getString();
         return FriendCommand.check(text);
      } else {
         return false;
      }
   }

   private GlowEspRenderer.GlowEspRendererBounds resolve3(float[] fs, int i, int j, float f, float g) {
      int intValue24 = (int)Math.ceil(Math.max(8.0F, f + g * 4.0F + 18.0F));
      int intValue25 = Math.max(0, (int)Math.floor(fs[0]) - intValue24);
      int intValue26 = Math.max(0, (int)Math.floor(fs[1]) - intValue24);
      int intValue27 = Math.min(i, (int)Math.ceil(fs[2]) + intValue24);
      int intValue28 = Math.min(j, (int)Math.ceil(fs[3]) + intValue24);
      int intValue29 = intValue27 - intValue25;
      int intValue30 = intValue28 - intValue26;
      return intValue29 > 2 && intValue30 > 2 ? new GlowEspRenderer.GlowEspRendererBounds(intValue25, intValue26, intValue29, intValue30) : null;
   }

   private void invoke4(float[] fs, float[] gs) {
      if (this.istochnikTsveta.is("Свой")) {
         invoke5(this.osnovnoyTsvet.getColor().getRGB(), fs);
         invoke5(this.vtoroyTsvet.getColor().getRGB(), gs);
      } else {
         Theme theme = WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null ? WildClient.INSTANCE.themeManager.getTheme() : Theme.WILD;
         ColorScheme colorScheme = ColorScheme.resolve2(theme, NeumorphismRenderer.check13());
         invoke5(colorScheme.getIntValue14(), fs);
         invoke5(colorScheme.getIntValue15(), gs);
      }
   }

   private static void invoke5(int i, float[] fs) {
      fs[0] = (i >> 16 & 0xFF) / 255.0F;
      fs[1] = (i >> 8 & 0xFF) / 255.0F;
      fs[2] = (i & 0xFF) / 255.0F;
   }

   private void invoke6() {
      GlowEspRenderer glowEspRenderer = this.glowEspRenderer;
      this.glowEspRenderer = null;
      if (glowEspRenderer != null) {
         if (RenderSystem.isOnRenderThread() && GLFW.glfwGetCurrentContext() != 0L) {
            glowEspRenderer.close();
         } else {
            if (CLIENT != null) {
               CLIENT.execute(glowEspRenderer::close);
            }
         }
      }
   }
}
