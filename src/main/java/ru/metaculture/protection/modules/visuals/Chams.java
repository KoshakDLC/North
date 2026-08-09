package ru.metaculture.protection;

import java.awt.Color;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "Chams",
   description = "Красивая шейдерная заливка моделей сущностей за стенами.",
   category = Category.Visuals
)
public final class Chams extends Module {
   public static final String CRYSTAL_MODE = "Crystal";
   public static final String VOID_MODE = "Void";
   public static final String PHANTOM_MODE = "Phantom";
   public static final String HYBRID_DEPTH_MODE = "Гибрид";
   public static final String VISIBLE_PASS_MODE = "По сцене";
   public static final String THROUGH_WALLS_MODE = "Сквозь стены";
   public final GroupSetting targets = new GroupSetting(
      "Цели", new BooleanSetting("Игроки", true), new BooleanSetting("Мобы", true), new BooleanSetting("Себя", false), new BooleanSetting("Невидимые", true)
   );
   public final ModeSetting shaderMode = new ModeSetting("Режим", "Crystal", "Crystal", "Void", "Phantom");
   public final ModeSetting depthMode = new ModeSetting("Глубина", "Гибрид", "Гибрид", "По сцене", "Сквозь стены");
   public final BooleanSetting hideFeatures = new BooleanSetting("Скрывать броню и предметы", true);
   public final BooleanSetting hideVanillaShadow = new BooleanSetting("Скрывать ванильную тень", true);
   public final NumberSetting maxDistance = new NumberSetting("Дистанция", 96.0F, 8.0F, 256.0F, 1.0F, false);
   public final ColorSetting topAccent = new ColorSetting("Верхний акцент", 58.0F, 0.72F, 1.0F, 1.0F);
   public final ColorSetting bottomAccent = new ColorSetting("Нижний акцент", 76.0F, 0.82F, 1.0F, 1.0F);
   public final NumberSetting intensity = new NumberSetting("Интенсивность", 1.35F, 0.35F, 3.0F, 0.05F, false);
   public final NumberSetting opacity = new NumberSetting("Прозрачность", 1.0F, 0.25F, 1.0F, 0.01F, true);
   public final NumberSetting refraction = new NumberSetting("Преломление", 0.72F, 0.35F, 1.15F, 0.01F, false);
   private final Map<Integer, TransitionState> transitionsByEntityId = new ConcurrentHashMap<>();

   public Chams() {
      PrismaticChamsRenderer.initialize();
      this.shaderMode.description = "Screen-space шейдер чамсов: Crystal, Void или Phantom";
      this.depthMode.description = "Гибрид рисует скрытый проход через стены и основной проход по depth buffer";
      this.hideFeatures.description = "Отключает броню, предметы в руках и остальные feature layers у подсвеченной сущности";
      this.hideVanillaShadow.description = "Убирает стандартную круглую тень Minecraft под подсвеченной сущностью";
      this.addSettings(
         new Setting[]{
            this.targets,
            this.shaderMode,
            this.depthMode,
            this.hideFeatures,
            this.hideVanillaShadow,
            this.maxDistance,
            this.topAccent,
            this.bottomAccent,
            this.intensity,
            this.opacity,
            this.refraction
         }
      );
   }

   public boolean shouldRender(LivingEntityRenderState state) {
      return this.getTransitionAlpha(state) > 0.001F;
   }

   public boolean shouldHideFeatures(LivingEntityRenderState state) {
      return this.hideFeatures.isEnabled() && this.getTransitionAlpha(state) > 0.001F;
   }

   public boolean shouldHideVanillaShadow(LivingEntityRenderState state) {
      return this.hideVanillaShadow.isEnabled() && this.getTransitionAlpha(state) > 0.001F;
   }

   public float getTransitionAlpha(LivingEntityRenderState state) {
      if (state == null) {
         return 0.0F;
      } else {
         int entityKey = getEntityKey(state);
         boolean target = this.isTarget(state);
         TransitionState transition = this.transitionsByEntityId.get(entityKey);
         if (transition == null && !target) {
            return 0.0F;
         } else {
            if (transition == null) {
               transition = new TransitionState();
               this.transitionsByEntityId.put(entityKey, transition);
            }

            long nowNanos = System.nanoTime();
            float deltaSeconds = transition.lastUpdateNanos == 0L
               ? 0.0F
               : Math.min((float)(nowNanos - transition.lastUpdateNanos) / 1.0E9F, 0.05F);
            transition.lastUpdateNanos = nowNanos;
            float targetAlpha = target ? 1.0F : 0.0F;
            float alphaError = targetAlpha - transition.alpha;
            transition.velocity += alphaError * 42.0F * deltaSeconds;
            transition.velocity = transition.velocity * (float)Math.exp(-13.0F * deltaSeconds);
            transition.alpha = transition.alpha + transition.velocity * deltaSeconds;
            if (transition.alpha < 0.0F) {
               transition.alpha = 0.0F;
               transition.velocity = 0.0F;
            } else if (transition.alpha > 1.12F) {
               transition.alpha = 1.12F;
               transition.velocity *= -0.22F;
            }

            if (!target && transition.alpha <= 0.001F) {
               this.transitionsByEntityId.remove(entityKey);
               return 0.0F;
            } else {
               return transition.alpha;
            }
         }
      }
   }

   public boolean hasActiveTransitions() {
      Iterator<Entry<Integer, TransitionState>> transitions = this.transitionsByEntityId.entrySet().iterator();

      while (transitions.hasNext()) {
         TransitionState transition = transitions.next().getValue();
         if (!(transition.alpha <= 0.001F) || !(transition.velocity <= 0.001F)) {
            return true;
         }

         transitions.remove();
      }

      return false;
   }

   public int getShaderModeId() {
      if (this.shaderMode.is("Void")) {
         return 1;
      } else {
         return this.shaderMode.is("Phantom") ? 2 : 0;
      }
   }

   public boolean isHybridDepthMode() {
      return this.depthMode.is("Гибрид");
   }

   public boolean usesVisiblePassOnly() {
      return this.depthMode.is("По сцене");
   }

   private boolean isTarget(LivingEntityRenderState state) {
      if (!this.enabled) {
         return false;
      } else if (state == null || CLIENT == null || CLIENT.world == null) {
         return false;
      } else if (state.invisible && !this.targets.isEnabled("Невидимые")) {
         return false;
      } else {
         float maximumDistance = Math.max(1.0F, this.maxDistance.getValue());
         if (state.squaredDistanceToCamera > maximumDistance * maximumDistance) {
            return false;
         } else if (state instanceof PlayerEntityRenderState playerState) {
            return CLIENT.player != null && playerState.name != null && playerState.name.equals(CLIENT.player.getName().getString())
               ? this.targets.isEnabled("Себя")
               : this.targets.isEnabled("Игроки");
         } else {
            return this.targets.isEnabled("Мобы");
         }
      }
   }

   public float[] getTopAccentRgba() {
      return toRgba(this.topAccent.getColor());
   }

   public float[] getBottomAccentRgba() {
      return toRgba(this.bottomAccent.getColor());
   }

   public static Chams getActive() {
      Chams chams = getInstance();
      return chams == null || !chams.enabled && !chams.hasActiveTransitions() ? null : chams;
   }

   public static Chams getInstance() {
      return WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null ? WildClient.INSTANCE.moduleManager.getModule(Chams.class) : null;
   }

   private static int getEntityKey(LivingEntityRenderState state) {
      int entityId = ((ChamsRenderState)state).wild$getEntityId();
      if (entityId != Integer.MIN_VALUE) {
         return entityId;
      } else {
         int typeHash = state.entityType == null ? 0 : state.entityType.hashCode();
         int positionX = Math.round((float)state.x * 8.0F);
         int positionY = Math.round((float)state.y * 8.0F);
         int positionZ = Math.round((float)state.z * 8.0F);
         int positionHash = typeHash * 31 + positionX;
         positionHash = positionHash * 31 + positionY;
         return positionHash * 31 + positionZ;
      }
   }

   private static float[] toRgba(Color color) {
      return new float[]{color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F};
   }

   static final class TransitionState {
      float alpha;
      float velocity;
      long lastUpdateNanos;
   }
}
