package ru.metaculture.protection;

import com.mojang.blaze3d.opengl.GlStateManager;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.state.EntityRenderState;
import net.minecraft.client.render.entity.state.LivingEntityRenderState;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.ReadableScoreboardScore;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.number.StyledNumberFormat;
import net.minecraft.text.MutableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joml.Quaternionf;
import org.joml.Vector3f;

@HudElementInfo(
   resolve = "TargetHUD",
   resolve2 = "w"
)
public final class TargetHud extends HudElement {
   private static final TargetHud INSTANCE = new TargetHud();
   private static final Logger LOGGER = LogManager.getLogger("TargetHUD");
   private static final Animation ANIMATION = new Animation();
   private static final Animation ANIMATION_2 = new Animation();
   private static final SpringAnimation SPRING_ANIMATION = new SpringAnimation(0.0F);
   private static final SpringAnimation SPRING_ANIMATION_2 = new SpringAnimation(0.0F);
   private static final SpringAnimation SPRING_ANIMATION_3 = new SpringAnimation(0.0F);
   private static final List<TargetHud.TargetHudState> ITEMS = new ArrayList<>();
   private static final ItemStack[] ITEM_STACKS = new ItemStack[4];
   private static final Pattern PATTERN = Pattern.compile("(?i)(?:\\u00A7|\\u0412\\u00A7).");
   private static final Pattern PATTERN_2 = Pattern.compile("\\d+(?:[\\.,]\\d+)?");
   private static final Pattern PATTERN_3 = Pattern.compile("[^A-Za-z\\u0410-\\u042F\\u0430-\\u044F\\u0401\\u04510-9\\s\\[\\]()_\\-.,!<>:|]");
   private final BooleanSetting priNavodke = new BooleanSetting("При наводке", false);
   private final BooleanSetting animirovatPriUdare = new BooleanSetting("Анимировать при ударе", true);
   private final BooleanSetting zolotyeSerdtsa = new BooleanSetting("Золотые сердца", true);
   private final ModeSetting vidOtobrazheniya = new ModeSetting("Вид отображения", "Голова", "Голова", "От 3-лица");
   private final ModeSetting pozitsiya = new ModeSetting("Позиция", "На экране", "На экране", "На цели");
   private final NumberSetting smeschenieX = new NumberSetting("Смещение X", 0.0F, -0.25F, 0.25F, 0.01F, false)
      .setVisibilityCondition(() -> !this.pozitsiya.is("На цели"));
   private static float floatValue;
   private static float floatValue2;
   private static final float FLOAT_VALUE = 0.58F;
   private static final float FLOAT_VALUE_2 = 130.0F;
   private static float floatValue3;
   private static float floatValue4;
   private static float floatValue5;
   private static LivingEntity livingEntity;
   private static int intValue = Integer.MIN_VALUE;
   private static int intValue2 = Integer.MIN_VALUE;
   private static int intValue3 = Integer.MIN_VALUE;
   private static int intValue4;
   private static float floatValue6 = Float.NaN;
   private static int intValue5 = 1;
   private static final long TIMESTAMP = 1000L;
   private static final Map<String, Long> VALUES_BY_KEY = new HashMap<>();
   private static final Map<String, Long> VALUES_BY_KEY_2 = new HashMap<>();

   private TargetHud() {
      this.invoke(this.priNavodke);
      this.invoke(this.animirovatPriUdare);
      this.invoke(this.zolotyeSerdtsa);
      this.invoke(this.vidOtobrazheniya);
      this.invoke(this.pozitsiya);
      this.invoke(this.smeschenieX);
      ru.metaculture.protection.HudPresetManager.invoke2(this);
   }

   public static TargetHud getINSTANCE() {
      return INSTANCE;
   }

   public static float measure(LivingEntity livingEntity) {
      if (livingEntity instanceof PlayerEntity playerEntity2) {
         Float floatValue = resolve(playerEntity2);
         if (floatValue != null) {
            return Math.max(0.0F, floatValue);
         }
      }

      float floatValue2 = livingEntity.getHealth() + measure3(livingEntity);
      return Math.max(0.0F, floatValue2);
   }

   private static float measure2(LivingEntity livingEntity) {
      if (livingEntity instanceof PlayerEntity playerEntity3) {
         Float floatValue3 = resolve(playerEntity3);
         if (floatValue3 != null) {
            return Math.max(0.0F, floatValue3);
         }
      }

      return RenderMath.measure49(livingEntity.getHealth(), 0.0F, livingEntity.getMaxHealth());
   }

   private static Float resolve(PlayerEntity playerEntity) {
      if (MinecraftAccessor.a_.world != null) {
         Float floatValue4 = resolve2(playerEntity, MinecraftAccessor.a_.world.getScoreboard());
         if (floatValue4 != null) {
            return floatValue4;
         }
      }

      return resolve2(playerEntity, playerEntity.getScoreboard());
   }

   private static Float resolve2(PlayerEntity playerEntity, Scoreboard scoreboard) {
      if (scoreboard == null) {
         return null;
      } else {
         ScoreboardObjective scoreboardObjective = scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.BELOW_NAME);
         if (scoreboardObjective == null) {
            return null;
         } else {
            ReadableScoreboardScore readableScoreboardScore = scoreboard.getScore(playerEntity, scoreboardObjective);
            if (readableScoreboardScore == null) {
               return null;
            } else {
               MutableText mutableText = ReadableScoreboardScore.getFormattedScore(readableScoreboardScore, scoreboardObjective.getNumberFormatOr(StyledNumberFormat.EMPTY));
               Float floatValue5 = resolve3(mutableText.getString());
               return floatValue5 != null ? floatValue5 : (float)readableScoreboardScore.getScore();
            }
         }
      }
   }

   private static Float resolve3(String string) {
      if (string != null && !string.isBlank()) {
         String text = PATTERN.matcher(string).replaceAll("").replace(',', '.');
         Matcher matcher = PATTERN_2.matcher(text);
         if (!matcher.find()) {
            return null;
         } else {
            try {
               return Float.parseFloat(matcher.group());
            } catch (NumberFormatException numberFormatException) {
               return null;
            }
         }
      } else {
         return null;
      }
   }

   private static float measure3(LivingEntity livingEntity) {
      try {
         return Math.max(0.0F, livingEntity.getAbsorptionAmount());
      } catch (Throwable exception) {
         return 0.0F;
      }
   }

   public static void invoke(RenderManager renderManager, DrawContext drawContext) {
      INSTANCE.invoke2(renderManager, drawContext);
   }

   public void invoke2(RenderManager renderManager2, DrawContext drawContext) {
      if (!(MinecraftAccessor.a_.currentScreen instanceof InventoryScreen)) {
         Object object = null;
         LivingEntity livingEntity2 = AttackAura.livingEntity;
         if (livingEntity2 instanceof LivingEntity) {
            object = livingEntity2;
         }

         if (object == null) {
            LivingEntity livingEntity3 = TriggerBot.getLivingEntity();
            if (livingEntity3 != null) {
               object = livingEntity3;
            }
         }

         if (object == null && this.priNavodke.isEnabled() && MinecraftAccessor.a_.targetedEntity instanceof LivingEntity livingEntity4 && livingEntity4.isAlive()) {
            object = livingEntity4;
         }

         if (object == null && MinecraftAccessor.a_.currentScreen instanceof ChatScreen && MinecraftAccessor.a_.player != null) {
            object = MinecraftAccessor.a_.player;
         }

         boolean flag = object != null;
         if (flag) {
            livingEntity = (LivingEntity)object;
         }

         ANIMATION.check();
         ANIMATION.resolve4(flag ? 1.0 : 0.0, 0.22F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, true);
         float floatValue6 = ANIMATION.measure3();
         Object object2 = flag ? object : livingEntity;
         if (!(floatValue6 <= 0.01F) && object2 != null) {
            boolean flag2 = this.zolotyeSerdtsa.isEnabled();
            Float floatValue7 = object2 instanceof PlayerEntity playerEntity4 ? resolve(playerEntity4) : null;
            float floatValue8 = floatValue7 != null ? Math.max(0.0F, floatValue7) : (flag2 ? measure2((LivingEntity)object2) : measure((LivingEntity)object2));
            float floatValue9 = flag2 && floatValue7 == null ? measure3((LivingEntity)object2) : 0.0F;
            float floatValue10 = flag2 ? floatValue8 + floatValue9 : floatValue8;
            float floatValue11 = Math.max(1.0F, Math.max(((LivingEntity)object2).getMaxHealth(), floatValue8));
            float floatValue12 = Math.min(1.0F, floatValue8 / floatValue11);
            float floatValue13 = Math.min(1.0F, floatValue9 / floatValue11);
            float floatValue14 = measure5((LivingEntity)object2);
            if (floatValue7 != null) {
               SPRING_ANIMATION_3.invoke(0.0F);
            }

            if (intValue != ((LivingEntity)object2).getId()) {
               intValue = ((LivingEntity)object2).getId();
               SPRING_ANIMATION.invoke(floatValue12);
               SPRING_ANIMATION_2.invoke(floatValue14);
               SPRING_ANIMATION_3.invoke(floatValue13);
            }

            floatValue3 = RenderMath.measure49(SPRING_ANIMATION.measure(floatValue12, SpringSpec.resolve10()), 0.0F, 1.0F);
            floatValue4 = RenderMath.measure49(SPRING_ANIMATION_2.measure(floatValue14, SpringSpec.resolve10()), 0.0F, 1.0F);
            floatValue5 = RenderMath.measure49(SPRING_ANIMATION_3.measure(floatValue13, SpringSpec.resolve10()), 0.0F, 1.0F);
            boolean flag3 = check2((LivingEntity)object2, floatValue10);
            ANIMATION_2.check();
            if (flag3 && this.animirovatPriUdare.isEnabled()) {
               intValue5 = (System.nanoTime() & 1L) == 0L ? 1 : -1;
               ANIMATION_2.invoke(1.0);
            }

            ANIMATION_2.resolve4(0.0, 0.34F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
            float floatValue15 = this.animirovatPriUdare.isEnabled() ? RenderMath.measure49(ANIMATION_2.measure3(), 0.0F, 1.0F) : 0.0F;
            String text2 = "";
            float floatValue16 = floatValue6 * this.prozrachnost.getValue();
            boolean flag4 = this.check8();
            String text3 = ((LivingEntity)object2).getName().getString();
            if (object2 instanceof PlayerEntity playerEntity5) {
               text2 = resolve10(playerEntity5);
            }

            text3 = ProtectInfo.resolve7(text3);
            if (!text2.isEmpty()) {
               text2 = text2 + " ";
            }

            text3 = resolve9(text3);
            String text4 = resolve8(floatValue10);
            String text5 = " hp";
            float floatValue17 = 252.204F;
            float floatValue18 = 85.472F;
            float floatValue19 = MinecraftAccessor.a_.getWindow().getFramebufferHeight();
            float floatValue20 = MinecraftAccessor.a_.getWindow().getFramebufferWidth();
            boolean flag5 = this.pozitsiya.is("На цели");
            HudEditorRenderer.HudEditorRendererState hudEditorRendererState = null;
            TargetHud.TargetHudData targetHudData = null;
            float floatValue21;
            float floatValue22;
            float floatValue23;
            float floatValue24;
            float floatValue25;
            if (flag5) {
               float floatValue26 = MinecraftAccessor.a_.getRenderTickCounter().getTickProgress(true);
               targetHudData = this.resolve4((LivingEntity)object2, floatValue26, (int)floatValue20, (int)floatValue19);
               if (targetHudData == null) {
                  return;
               }

               float floatValue27 = this.measure4();
               floatValue25 = floatValue27;
               floatValue23 = floatValue17 * floatValue27;
               floatValue24 = floatValue18 * floatValue27;
               floatValue21 = targetHudData.x - floatValue23 * 0.5F;
               floatValue22 = targetHudData.y - floatValue24 * 0.5F;
               if (MinecraftAccessor.a_.currentScreen instanceof ChatScreen) {
                  hudEditorRendererState = HudEditorRenderer.getINSTANCE().resolve2("HUD_TargetHUD", floatValue21, floatValue22, floatValue17, floatValue18);
                  floatValue25 = Math.min(hudEditorRendererState.floatValue3 / floatValue17, hudEditorRendererState.floatValue4 / floatValue18);
                  floatValue23 = floatValue17 * floatValue25;
                  floatValue24 = floatValue18 * floatValue25;
                  floatValue21 = targetHudData.x - floatValue23 * 0.5F;
                  floatValue22 = targetHudData.y - floatValue24 * 0.5F;
               }
            } else {
               hudEditorRendererState = HudEditorRenderer.getINSTANCE().resolve("HUD_TargetHUD", 10.0F, Math.max(10.0F, floatValue19 - floatValue18 - 10.0F), floatValue17, floatValue18);
               float floatValue28 = hudEditorRendererState.floatValue;
               float floatValue29 = hudEditorRendererState.floatValue2;
               float floatValue30 = hudEditorRendererState.floatValue3;
               float floatValue31 = hudEditorRendererState.floatValue4;
               floatValue25 = Math.min(floatValue30 / floatValue17, floatValue31 / floatValue18);
               floatValue23 = floatValue17 * floatValue25;
               floatValue24 = floatValue18 * floatValue25;
               floatValue21 = floatValue28 + (floatValue30 - floatValue23) / 2.0F;
               floatValue22 = floatValue29 + (floatValue31 - floatValue24) / 2.0F;
            }

            this.invoke3(floatValue21, floatValue22, floatValue23, floatValue24);
            int intValue = (int)(255.0F * floatValue16);
            int intValue2 = this.compute6(floatValue16);
            int intValue3 = intValue2;
            if (object2 instanceof PlayerEntity playerEntity6) {
               intValue3 = compute3(playerEntity6, intValue2, intValue);
            }

            float floatValue32 = 14.0F * floatValue25;
            int intValue4 = this.compute5(floatValue16);
            int intValue5 = this.compute2(floatValue16);
            float floatValue33 = RenderMath.measure49((floatValue3 - 0.16F) / 0.84F, 0.0F, 1.0F);
            int intValue6 = compute2(ColorUtils.compute43(255, 84, 96, intValue), ColorUtils.compute43(128, 255, 171, intValue), floatValue33);
            int intValue7 = compute2(ColorUtils.compute43(210, 35, 52, intValue), ColorUtils.compute43(34, 213, 122, intValue), floatValue33);
            int intValue8 = ColorUtils.compute43(192, 220, 255, intValue);
            int intValue9 = ColorUtils.compute43(86, 132, 202, intValue);
            int intValue10 = this.compute7(floatValue16);
            float floatValue34 = floatValue21 + 7.0F * floatValue25;
            float floatValue35 = floatValue22 + 6.834F * floatValue25;
            float floatValue36 = 71.799F * floatValue25;
            float floatValue37 = 71.803F * floatValue25;
            float floatValue38 = 54.367F * floatValue25;
            float floatValue39 = floatValue21 + 15.716F * floatValue25;
            float floatValue40 = floatValue22 + 15.552F * floatValue25;
            float floatValue41 = flag5 ? floatValue16 : floatValue16 * RenderMath.measure49((floatValue6 - 0.42F) / 0.58F, 0.0F, 1.0F);
            boolean flag6 = this.vidOtobrazheniya.options.size() > 1
               && this.vidOtobrazheniya.getValue().equalsIgnoreCase(this.vidOtobrazheniya.options.get(1));
            float floatValue42 = floatValue21 + 84.185F * floatValue25;
            float floatValue43 = 161.02F * floatValue25;
            float floatValue44 = floatValue22 + 6.834F * floatValue25;
            float floatValue45 = 31.592F * floatValue25;
            if (flag4) {
               NeumorphismRenderer.invoke();
            }

            try {
               this.invoke(renderManager2, floatValue21, floatValue22, floatValue23, floatValue24, floatValue32, floatValue16);
               this.invoke2(renderManager2, floatValue34, floatValue35, floatValue36, floatValue37, 10.0F * floatValue25, floatValue16);
               this.invoke2(renderManager2, floatValue42, floatValue44, floatValue43, floatValue45, 9.0F * floatValue25, floatValue16);
               if (flag4) {
                  NeumorphismRenderer.invoke2();
               }

               float floatValue46 = 30.0F * floatValue25;
               float floatValue47 = 20.0F * floatValue25;
               float floatValue48 = floatValue21 + 94.33F * floatValue25;
               float floatValue49 = floatValue44 + floatValue45 / 2.0F + 6.6F * floatValue25;
               float floatValue50 = floatValue42 + floatValue43 - 10.0F * floatValue25;
               if (!text2.isEmpty()) {
                  int intValue11 = intValue3 == intValue2 ? ColorUtils.compute43(255, 50, 50, intValue) : intValue3;
                  String text6 = text2.trim().toUpperCase(Locale.ROOT);
                  float floatValue51 = TextMeasureCache.resolve(FontRegistry.fontObject4, text6, floatValue47).floatValue;
                  String text7 = resolve11(renderManager2, text3, floatValue46, Math.max(20.0F * floatValue25, floatValue50 - floatValue48 - floatValue51 - 5.0F * floatValue25));
                  renderManager2.invoke69(FontRegistry.fontObject4, floatValue48, floatValue49, floatValue46, text7, intValue2);
                  renderManager2.invoke69(FontRegistry.fontObject4, floatValue50 - floatValue51, floatValue49 - 0.5F * floatValue25, floatValue47, text6, intValue11);
               } else {
                  String text8 = resolve11(renderManager2, text3, floatValue46, Math.max(20.0F * floatValue25, floatValue50 - floatValue48));
                  renderManager2.invoke69(FontRegistry.fontObject4, floatValue48, floatValue49, floatValue46, text8, intValue2);
               }
            } finally {
               if (flag4) {
                  NeumorphismRenderer.invoke3();
               }
            }

            float floatValue52 = floatValue22 + 43.426F * floatValue25;
            float floatValue53 = 35.211F * floatValue25;
            this.invoke2(renderManager2, floatValue42, floatValue52, floatValue43, floatValue53, 9.0F * floatValue25, floatValue16);
            float floatValue54 = 16.01F * floatValue25;
            float floatValue55 = flag5 ? floatValue16 : floatValue16 * RenderMath.measure49((floatValue6 - 0.42F) / 0.58F, 0.0F, 1.0F);
            invoke12(
               renderManager2,
               drawContext,
               this,
               floatValue21 + 90.04F * floatValue25,
               floatValue22 + 48.53F * floatValue25,
               (LivingEntity)object2,
               floatValue16,
               floatValue55,
               floatValue25,
               intValue5,
               intValue4,
               floatValue54,
               flag4
            );
            float floatValue56 = 24.0F * floatValue25;
            float floatValue57 = TextMeasureCache.resolve(FontRegistry.fontObject, text4, floatValue56).floatValue;
            float floatValue58 = TextMeasureCache.resolve(FontRegistry.fontObject, text5, floatValue56).floatValue;
            float floatValue59 = floatValue42 + floatValue43 - floatValue57 - floatValue58 - 9.2F * floatValue25;
            float floatValue60 = floatValue52 + 19.1F * floatValue25;
            renderManager2.invoke69(FontRegistry.fontObject, floatValue59, floatValue60, floatValue56, text4, intValue2);
            renderManager2.invoke69(FontRegistry.fontObject, floatValue59 + floatValue57, floatValue60, floatValue56, text5, intValue10);
            float floatValue61 = floatValue21 + 90.339F * floatValue25;
            float floatValue62 = floatValue21 + 90.339F * floatValue25;
            float floatValue63 = floatValue22 + 65.28F * floatValue25;
            float floatValue64 = 76.0F * floatValue25;
            float floatValue65 = 3.72F * floatValue25;
            float floatValue66 = floatValue65 * 0.5F;
            this.invoke2(renderManager2, floatValue62, floatValue63, floatValue64, floatValue65, floatValue66, floatValue16);
            float floatValue67 = Math.min(floatValue65 * 0.32F, Math.max(0.72F * floatValue25, 0.45F));
            float floatValue68 = Math.max(0.0F, (floatValue64 - floatValue67 * 2.0F) * floatValue4);
            if (floatValue68 > 0.35F) {
               float floatValue69 = Math.max(1.0F, floatValue65 - floatValue67 * 2.0F);
               renderManager2.invoke24(
                  floatValue62 + floatValue67, floatValue63 + floatValue67, Math.max(1.0F, floatValue64 - floatValue67 * 2.0F), floatValue69, floatValue69 * 0.5F, floatValue69 * 0.5F, floatValue69 * 0.5F, floatValue69 * 0.5F
               );
               renderManager2.invoke34(floatValue62 + floatValue67, floatValue63 + floatValue67, floatValue68, floatValue69, floatValue69 * 0.5F, intValue9, intValue8);
               renderManager2.invoke25();
            }

            float floatValue70 = floatValue22 + 70.12F * floatValue25;
            float floatValue71 = 146.92F * floatValue25;
            float floatValue72 = 6.72F * floatValue25;
            float floatValue73 = floatValue72 * 0.5F;
            this.invoke2(renderManager2, floatValue61, floatValue70, floatValue71, floatValue72, floatValue73, floatValue16);
            float floatValue74 = Math.max(1.15F * floatValue25, 0.85F);
            float floatValue75 = floatValue61 + floatValue74;
            float floatValue76 = floatValue70 + floatValue74;
            float floatValue77 = Math.max(1.0F, floatValue72 - floatValue74 * 2.0F);
            float floatValue78 = Math.max(0.0F, (floatValue71 - floatValue74 * 2.0F) * floatValue3);
            float floatValue79 = floatValue77 * 0.5F;
            if (floatValue78 > 0.5F) {
               float floatValue80 = RenderMath.measure49(Math.abs(SPRING_ANIMATION.getFloatValue2()) * 0.018F, 0.0F, 0.075F);
               float floatValue81 = Math.min(floatValue71 - floatValue74 * 2.0F, floatValue78 + (floatValue71 - floatValue74 * 2.0F) * floatValue80);
               renderManager2.invoke24(floatValue75, floatValue76, Math.max(1.0F, floatValue71 - floatValue74 * 2.0F), floatValue77, floatValue79, floatValue79, floatValue79, floatValue79);
               renderManager2.invoke37(floatValue75, floatValue76, floatValue81, floatValue77, floatValue79, intValue6, intValue7);
               renderManager2.invoke5(
                  floatValue75 + floatValue79 * 0.5F,
                  floatValue76 + floatValue77 * 0.18F,
                  Math.max(0.0F, floatValue81 - floatValue79),
                  Math.max(1.0F, floatValue77 * 0.22F),
                  floatValue77 * 0.11F,
                  ColorUtils.compute43(255, 255, 255, (int)(58.0F * floatValue16))
               );
               renderManager2.invoke25();
            }

            if (flag2 && floatValue5 > 0.001F) {
               int intValue12 = ColorUtils.compute43(255, 224, 92, (int)(245.0F * floatValue16));
               int intValue13 = ColorUtils.compute43(232, 154, 35, (int)(245.0F * floatValue16));
               float floatValue82 = Math.max(1.0F, floatValue71 - floatValue74 * 2.0F);
               float floatValue83 = floatValue82 * RenderMath.measure49(floatValue5, 0.0F, 1.0F);
               if (floatValue83 > 0.5F) {
                  renderManager2.invoke24(floatValue75, floatValue76, floatValue82, floatValue77, floatValue79, floatValue79, floatValue79, floatValue79);
                  renderManager2.invoke37(floatValue75, floatValue76, floatValue83, floatValue77, floatValue79, intValue12, intValue13);
                  renderManager2.invoke25();
               }
            }

            if (floatValue41 > 0.01F) {
               invoke3(renderManager2, drawContext, (LivingEntity)object2, floatValue39, floatValue40, floatValue38, floatValue41, floatValue15, flag6);
               invoke11(renderManager2, floatValue21, floatValue22, floatValue25, floatValue41);
            }

            if (hudEditorRendererState != null) {
               HudModule.invoke3("HUD_TargetHUD", floatValue21, floatValue22, floatValue23, floatValue24);
               HudEditorRenderer hudEditorRenderer = HudEditorRenderer.getINSTANCE();
               hudEditorRenderer.invoke7(hudEditorRendererState, floatValue21, floatValue22, floatValue23, floatValue24);
               if (flag5) {
                  HudSettingsRenderer.invoke(
                     renderManager2,
                     this,
                     floatValue21,
                     floatValue22,
                     floatValue23,
                     floatValue24,
                     MinecraftAccessor.a_.getWindow().getScaledWidth(),
                     MinecraftAccessor.a_.getWindow().getScaledHeight(),
                     hudEditorRendererState.floatValue6,
                     hudEditorRenderer.getFloatValue(),
                     hudEditorRenderer.getFloatValue2(),
                     hudEditorRenderer.isFlag4(),
                     hudEditorRenderer.isFlag3()
                  );
               } else {
                  HudSettingsRenderer.invoke2(
                     renderManager2, this, hudEditorRendererState, hudEditorRenderer, MinecraftAccessor.a_.getWindow().getScaledWidth(), MinecraftAccessor.a_.getWindow().getScaledHeight()
                  );
               }
            } else if (targetHudData != null) {
               HudModule.invoke3("HUD_TargetHUD", floatValue21, floatValue22, floatValue23, floatValue24);
            }
         } else {
            floatValue3 = 0.0F;
            floatValue4 = 0.0F;
            floatValue5 = 0.0F;
            SPRING_ANIMATION.invoke(0.0F);
            SPRING_ANIMATION_2.invoke(0.0F);
            SPRING_ANIMATION_3.invoke(0.0F);
            intValue = Integer.MIN_VALUE;
            intValue2 = Integer.MIN_VALUE;
            if (!flag) {
               livingEntity = null;
            }
         }
      }
   }

   private float measure4() {
      HudEditorRenderer.HudEditorRendererData2 hudEditorRendererData2 = HudEditorRenderer.getINSTANCE().getValuesByKey2().get("HUD_TargetHUD");
      return hudEditorRendererData2 == null ? 1.0F : RenderMath.measure49(Math.min(hudEditorRendererData2.scaleX(), hudEditorRendererData2.scaleY()), 0.72F, 1.48F);
   }

   private TargetHud.TargetHudData resolve4(LivingEntity livingEntity, float f, int i, int j) {
      if (livingEntity != null
         && !livingEntity.isRemoved()
         && i > 1
         && j > 1
         && MinecraftAccessor.a_.gameRenderer != null
         && MinecraftAccessor.a_.gameRenderer.getCamera() != null) {
         Vec3d vec3d = livingEntity.getLerpedPos(f);
         double doubleValue = Math.max(0.65, (double)livingEntity.getHeight());
         Vec3d vec3d2 = new Vec3d(vec3d.x, vec3d.y + doubleValue * 0.5, vec3d.z);
         Vec3d vec3d3 = MathUtils.resolve(vec3d2);
         if (vec3d3 != null && !(vec3d3.z <= 0.001) && !(vec3d3.z > 1.0)) {
            float floatValue84 = (float)vec3d3.x + 130.0F * this.smeschenieX.getValue();
            float floatValue85 = (float)vec3d3.y;
            if (livingEntity.getId() != intValue2) {
               intValue2 = livingEntity.getId();
               floatValue = floatValue84;
               floatValue2 = floatValue85;
            } else {
               floatValue = floatValue + (floatValue84 - floatValue) * 0.58F;
               floatValue2 = floatValue2 + (floatValue85 - floatValue2) * 0.58F;
            }

            return new TargetHud.TargetHudData(floatValue, floatValue2);
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   private static void invoke3(
      RenderManager renderManager3, DrawContext drawContext, LivingEntity livingEntity, float f, float g, float h, float i, float j, boolean bl
   ) {
      float floatValue86 = ItemRenderUtil.measure(f);
      float floatValue87 = ItemRenderUtil.measure(g);
      float floatValue88 = ItemRenderUtil.measure2(h);
      float floatValue89 = Math.max(2.0F, (float)Math.round(floatValue88 * 0.11F));
      if (!bl || !check(renderManager3, drawContext, livingEntity, floatValue86, floatValue87, floatValue88, floatValue89, i, j, true)) {
         if (livingEntity instanceof PlayerEntity playerEntity7 && MinecraftAccessor.a_.getNetworkHandler() != null) {
            PlayerListEntry playerListEntry = MinecraftAccessor.a_.getNetworkHandler().getPlayerListEntry(playerEntity7.getUuid());
            if (playerListEntry != null) {
               try {
                  Identifier identifier = playerListEntry.getSkinTextures().texture();
                  AbstractTexture abstractTexture = MinecraftAccessor.a_.getTextureManager().getTexture(identifier);
                  if (abstractTexture != null && abstractTexture.getGlTexture() instanceof GlTexture glTexture && glTexture.getGlId() > 0) {
                     ItemRenderUtil.invoke4(identifier);
                     int intValue14 = glTexture.getGlId();
                     GlStateManager._bindTexture(intValue14);
                     invoke5(renderManager3, floatValue86, floatValue87, floatValue88, j);

                     try {
                        renderManager3.invoke65(i);

                        try {
                           renderManager3.invoke12(intValue14, -floatValue88 * 0.5F, -floatValue88 * 0.5F, floatValue88, floatValue88, 0.125F, 0.125F, 0.25F, 0.25F, floatValue89);
                           renderManager3.invoke12(intValue14, -floatValue88 * 0.5F, -floatValue88 * 0.5F, floatValue88, floatValue88, 0.625F, 0.125F, 0.75F, 0.25F, floatValue89);
                        } finally {
                           renderManager3.invoke66();
                        }

                        invoke7(renderManager3, floatValue88, floatValue89, i, j);
                     } finally {
                        invoke6(renderManager3);
                     }

                     return;
                  }
               } catch (Throwable exception2) {
               }
            }
         }

         int intValue15 = ColorUtils.compute43(30, 30, 30, (int)(120.0F * i));
         renderManager3.invoke5(floatValue86, floatValue87, floatValue88, floatValue88, floatValue89, intValue15);
         int intValue16 = ColorUtils.compute43(200, 200, 200, (int)(200.0F * i));
         float floatValue90 = floatValue88 * 1.3F;
         String text9 = "a";
         float floatValue91 = TextMeasureCache.resolve(FontRegistry.fontObject3, text9, floatValue90).floatValue;
         renderManager3.invoke69(FontRegistry.fontObject3, floatValue86 + (floatValue88 - floatValue91) / 2.0F, floatValue87 + floatValue88 / 2.0F + floatValue90 * 0.25F, floatValue90, text9, intValue16);
      }
   }

   private static boolean check(
      RenderManager renderManager4, DrawContext drawContext, LivingEntity livingEntity, float f, float g, float h, float i, float j, float k, boolean bl
   ) {
      if (livingEntity != null && MinecraftAccessor.a_ != null && drawContext != null && !(MinecraftAccessor.a_.currentScreen instanceof InventoryScreen)) {
         float floatValue92 = MinecraftAccessor.a_.getWindow().getScaleFactor();
         if (floatValue92 <= 0.0F) {
            return false;
         } else {
            float floatValue93 = RenderMath.measure49(k, 0.0F, 1.0F);
            float floatValue94 = 1.0F - floatValue93 * 0.085F;
            float floatValue95 = h * floatValue94;
            float floatValue96 = f + (h - floatValue95) * 0.5F;
            float floatValue97 = g + (h - floatValue95) * 0.5F;
            int intValue17 = Math.round(floatValue96 / floatValue92);
            int intValue18 = Math.round(floatValue97 / floatValue92);
            int intValue19 = Math.max(1, Math.round(floatValue95 / floatValue92));
            float floatValue98 = Math.max(0.65F, livingEntity.getHeight());
            float floatValue99 = RenderMath.measure49(1.8F / floatValue98, 0.72F, 1.65F);
            int intValue20 = Math.max(8, Math.round(intValue19 * (bl ? 1.02F : 1.15F) * floatValue99));
            int intValue21 = Math.max(intValue19 + 1, Math.round(intValue19 * (bl ? 2.24F : 2.05F)));
            int intValue22 = intValue18 - Math.round(intValue19 * (bl ? 0.12F : 0.0F));
            int intValue23 = intValue22 + intValue21;
            float floatValue100 = intValue17 + intValue19 * 0.5F;
            float floatValue101 = (intValue22 + intValue23) * 0.5F;
            float floatValue102 = (bl ? 24.0F : 8.0F) + intValue5 * floatValue93 * 50.0F;
            float floatValue103 = bl ? -7.0F : -4.0F;
            float floatValue104 = floatValue100 - (float)Math.tan(floatValue102 / 20.0F) * 5.0F;
            float floatValue105 = floatValue101 - (float)Math.tan(-floatValue103 / 20.0F);
            renderManager4.invoke20();

            label109: {
               boolean flag7;
               try {
                  drawContext.enableScissor(intValue17, intValue18, intValue17 + intValue19, intValue18 + intValue19);
                  invoke4(drawContext, intValue17, intValue22, intValue17 + intValue19, intValue23, intValue20, 0.0625F, floatValue104, floatValue105, livingEntity);
                  break label109;
               } catch (Throwable exception3) {
                  flag7 = false;
               } finally {
                  try {
                     drawContext.disableScissor();
                  } catch (Throwable exception4) {
                  }
               }

               return flag7;
            }

            if (floatValue93 > 0.001F) {
               renderManager4.invoke5(floatValue96, floatValue97, floatValue95, floatValue95, i, ColorUtils.compute43(255, 55, 55, (int)(58.0F * j * floatValue93)));
            }

            return true;
         }
      } else {
         return false;
      }
   }

   private static void invoke4(DrawContext drawContext, int i, int j, int k, int l, int m, float f, float g, float h, LivingEntity livingEntity) {
      float floatValue106 = (i + k) * 0.5F;
      float floatValue107 = (j + l) * 0.5F;
      float floatValue108 = (float)Math.atan((floatValue106 - g) / 40.0F);
      float floatValue109 = (float)Math.atan((floatValue107 - h) / 40.0F);
      Quaternionf quaternionf = new Quaternionf().rotateZ((float) Math.PI);
      Quaternionf quaternionf2 = new Quaternionf().rotateX(floatValue109 * 20.0F * (float) (Math.PI / 180.0));
      quaternionf.mul(quaternionf2);
      EntityRenderer entityRenderer = MinecraftAccessor.a_.getEntityRenderDispatcher().getRenderer(livingEntity);
      EntityRenderState entityRenderState = entityRenderer.getAndUpdateRenderState(livingEntity, 1.0F);
      entityRenderState.hitbox = null;
      if (entityRenderState instanceof LivingEntityRenderState livingEntityRenderState) {
         float floatValue110 = 180.0F + floatValue108 * 20.0F;
         livingEntityRenderState.bodyYaw = floatValue110;
         livingEntityRenderState.relativeHeadYaw = 180.0F + floatValue108 * 40.0F - floatValue110;
         livingEntityRenderState.pitch = -floatValue109 * 20.0F;
      }

      float floatValue111 = Math.max(0.001F, livingEntity.getScale());
      Vector3f vector3f = new Vector3f(0.0F, livingEntity.getHeight() / 2.0F + f * floatValue111, 0.0F);
      drawContext.addEntity(entityRenderState, m / floatValue111, vector3f, quaternionf, quaternionf2, i, j, k, l);
   }

   private static void invoke5(RenderManager renderManager5, float f, float g, float h, float i) {
      float floatValue112 = RenderMath.measure49(i, 0.0F, 1.0F);
      float floatValue113 = f + h * 0.5F;
      float floatValue114 = g + h * 0.5F;
      float floatValue115 = 1.0F - floatValue112 * 0.085F;
      float floatValue116 = intValue5 * floatValue112 * 8.5F;
      renderManager5.invoke56(floatValue113, floatValue114);
      renderManager5.invoke54(floatValue116);
      renderManager5.invoke59(floatValue115, floatValue115);
   }

   private static void invoke6(RenderManager renderManager6) {
      renderManager6.invoke64();
      renderManager6.invoke55();
      renderManager6.invoke57();
   }

   private static void invoke7(RenderManager renderManager7, float f, float g, float h, float i) {
      float floatValue117 = RenderMath.measure49(i, 0.0F, 1.0F);
      if (!(floatValue117 <= 0.001F)) {
         renderManager7.invoke5(-f * 0.5F, -f * 0.5F, f, f, g, ColorUtils.compute43(255, 55, 55, (int)(58.0F * h * floatValue117)));
      }
   }

   private static void invoke8(LivingEntity livingEntity, boolean bl, String string, String string2) {
      long longValue = System.currentTimeMillis();
      String text10 = string + "|" + compute(livingEntity) + "|" + bl;
      Long longValue2 = VALUES_BY_KEY.get(text10);
      if (longValue2 == null || longValue - longValue2 >= 1000L) {
         VALUES_BY_KEY.put(text10, longValue);
      }
   }

   private static void invoke9(LivingEntity livingEntity, boolean bl, String string, String string2, Throwable throwable) {
      long longValue3 = System.currentTimeMillis();
      String text11 = throwable == null ? "none" : throwable.getClass().getName();
      String text12 = string + "|" + compute(livingEntity) + "|" + bl + "|" + text11;
      Long longValue4 = VALUES_BY_KEY_2.get(text12);
      if (longValue4 == null || longValue3 - longValue4 >= 1000L) {
         VALUES_BY_KEY_2.put(text12, longValue3);
         LOGGER.warn(
            "[portrait] stage={} target={} id={} type={} class={} thirdPerson={} {}",
            string,
            resolve5(livingEntity),
            compute(livingEntity),
            resolve6(livingEntity),
            resolve7(livingEntity),
            bl,
            string2,
            throwable
         );
      }
   }

   private static int compute(LivingEntity livingEntity) {
      return livingEntity == null ? Integer.MIN_VALUE : livingEntity.getId();
   }

   private static String resolve5(LivingEntity livingEntity) {
      if (livingEntity == null) {
         return "null";
      } else {
         try {
            return livingEntity.getName().getString();
         } catch (Throwable exception5) {
            return "name-error";
         }
      }
   }

   private static String resolve6(LivingEntity livingEntity) {
      if (livingEntity == null) {
         return "null";
      } else {
         try {
            return String.valueOf(livingEntity.getType());
         } catch (Throwable exception6) {
            return "type-error";
         }
      }
   }

   private static String resolve7(LivingEntity livingEntity) {
      return livingEntity == null ? "null" : livingEntity.getClass().getName();
   }

   private static boolean check2(LivingEntity livingEntity, float f) {
      int intValue24 = livingEntity.getId();
      if (intValue24 != intValue3) {
         intValue3 = intValue24;
         intValue4 = 0;
         floatValue6 = f;
         ITEMS.clear();
         ANIMATION_2.invoke(0.0);
         return false;
      } else {
         boolean flag8 = livingEntity.hurtTime > 0 && (intValue4 == 0 || livingEntity.hurtTime > intValue4);
         boolean flag9 = !Float.isNaN(floatValue6) && f < floatValue6 - 0.05F;
         if (flag8 || flag9) {
            invoke10();
         }

         intValue4 = livingEntity.hurtTime;
         floatValue6 = f;
         return flag8 || flag9;
      }
   }

   private static void invoke10() {
      byte byteValue = 24;
      float floatValue118 = (float)(System.nanoTime() & 7L) * 0.06F;

      for (int intValue25 = 0; intValue25 < byteValue; intValue25++) {
         float floatValue119 = (float)((Math.PI * 2) * intValue25 / byteValue) + floatValue118;
         float floatValue120 = 1.35F + intValue25 % 5 * 0.15F;
         float floatValue121 = 43.0F + (intValue25 % 3 - 1) * 3.1F;
         float floatValue122 = 42.8F + (intValue25 % 2 == 0 ? -2.8F : 2.8F);
         float floatValue123 = (float)Math.cos(floatValue119) * floatValue120;
         float floatValue124 = (float)Math.sin(floatValue119) * floatValue120 - 0.08F;
         float floatValue125 = 1.32F + intValue25 % 3 * 0.32F;
         int intValue26 = 56 + intValue25 % 10;
         ITEMS.add(new TargetHud.TargetHudState(floatValue121, floatValue122, floatValue123, floatValue124, floatValue125, intValue26));
      }
   }

   private static void invoke11(RenderManager renderManager8, float f, float g, float h, float i) {
      for (int intValue27 = ITEMS.size() - 1; intValue27 >= 0; intValue27--) {
         TargetHud.TargetHudState targetHudState = ITEMS.get(intValue27);
         targetHudState.intValue2++;
         if (targetHudState.intValue2 >= targetHudState.intValue) {
            ITEMS.remove(intValue27);
         } else {
            targetHudState.floatValue = targetHudState.floatValue + targetHudState.floatValue3;
            targetHudState.floatValue2 = targetHudState.floatValue2 + targetHudState.floatValue4;
            targetHudState.floatValue3 *= 0.988F;
            targetHudState.floatValue4 = targetHudState.floatValue4 * 0.988F + 0.012F;
            float floatValue126 = (float)targetHudState.intValue2 / targetHudState.intValue;
            float floatValue127 = 1.0F - (1.0F - floatValue126) * (1.0F - floatValue126);
            float floatValue128 = Math.max(0.0F, 1.0F - floatValue126) * i;
            float floatValue129 = f + targetHudState.floatValue * h;
            float floatValue130 = g + targetHudState.floatValue2 * h;
            float floatValue131 = targetHudState.floatValue5 * h * (1.0F + floatValue127 * 0.28F);
            renderManager8.invoke39(floatValue129, floatValue130, floatValue131 * 4.2F, 0.0F, 1.0F, ColorUtils.compute43(146, 170, 255, (int)(32.0F * floatValue128)));
            renderManager8.invoke39(floatValue129, floatValue130, floatValue131, 0.0F, 1.0F, ColorUtils.compute43(146, 170, 255, (int)(235.0F * floatValue128)));
         }
      }
   }

   private static String resolve8(float f) {
      int intValue28 = Math.max(0, Math.round(f * 10.0F));
      return intValue28 / 10 + "." + intValue28 % 10;
   }

   private static float measure5(LivingEntity livingEntity) {
      if (livingEntity == null) {
         return 0.0F;
      } else {
         float floatValue132 = 0.0F;
         EquipmentSlot[] equipmentSlots = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

         for (EquipmentSlot equipmentSlot : equipmentSlots) {
            ItemStack itemStack = livingEntity.getEquippedStack(equipmentSlot);
            if (itemStack != null && !itemStack.isEmpty()) {
               if (itemStack.isDamageable() && itemStack.getMaxDamage() > 0) {
                  floatValue132 += RenderMath.measure49(1.0F - (float)itemStack.getDamage() / itemStack.getMaxDamage(), 0.0F, 1.0F);
               } else {
                  floatValue132++;
               }
            }
         }

         return RenderMath.measure49(floatValue132 / equipmentSlots.length, 0.0F, 1.0F);
      }
   }

   private static int compute2(int i, int j, float f) {
      float floatValue133 = RenderMath.measure49(f, 0.0F, 1.0F);
      int intValue29 = i >>> 24 & 0xFF;
      int intValue30 = i >>> 16 & 0xFF;
      int intValue31 = i >>> 8 & 0xFF;
      int intValue32 = i & 0xFF;
      int intValue33 = j >>> 24 & 0xFF;
      int intValue34 = j >>> 16 & 0xFF;
      int intValue35 = j >>> 8 & 0xFF;
      int intValue36 = j & 0xFF;
      int intValue37 = Math.round(intValue29 + (intValue33 - intValue29) * floatValue133);
      int intValue38 = Math.round(intValue30 + (intValue34 - intValue30) * floatValue133);
      int intValue39 = Math.round(intValue31 + (intValue35 - intValue31) * floatValue133);
      int intValue40 = Math.round(intValue32 + (intValue36 - intValue32) * floatValue133);
      return (intValue37 & 0xFF) << 24 | (intValue38 & 0xFF) << 16 | (intValue39 & 0xFF) << 8 | intValue40 & 0xFF;
   }

   private static String resolve9(String string) {
      return string != null && !string.isEmpty() ? PATTERN_3.matcher(PATTERN.matcher(string).replaceAll("")).replaceAll("").trim() : "";
   }

   public static String resolve10(PlayerEntity playerEntity) {
      return playerEntity != null && playerEntity.getScoreboardTeam() != null
         ? resolve9(ProtectInfo.resolve7(playerEntity.getScoreboardTeam().getPrefix().getString()))
         : "";
   }

   public static int compute3(PlayerEntity playerEntity, int i, int j) {
      if (playerEntity != null && playerEntity.getScoreboardTeam() != null) {
         Formatting formatting = playerEntity.getScoreboardTeam().getColor();
         return formatting != null && formatting.getColorValue() != null ? ColorUtils.compute29(formatting.getColorValue(), j) : i;
      } else {
         return i;
      }
   }

   private static String resolve11(RenderManager renderManager9, String string, float f, float g) {
      if (string != null && !string.isEmpty() && !(TextMeasureCache.resolve(FontRegistry.fontObject, string, f).floatValue <= g)) {
         String text13 = "...";

         for (int intValue41 = string.length(); intValue41 > 0; intValue41--) {
            String text14 = string.substring(0, intValue41).trim() + text13;
            if (TextMeasureCache.resolve(FontRegistry.fontObject, text14, f).floatValue <= g) {
               return text14;
            }
         }

         return text13;
      } else {
         return string == null ? "" : string;
      }
   }

   private static void invoke12(
      RenderManager renderManager10,
      DrawContext drawContext,
      HudElement hudElement,
      float f,
      float g,
      LivingEntity livingEntity,
      float h,
      float i,
      float j,
      int k,
      int l,
      float m,
      boolean bl
   ) {
      if (livingEntity != null) {
         ITEM_STACKS[0] = livingEntity.getEquippedStack(EquipmentSlot.HEAD);
         ITEM_STACKS[1] = livingEntity.getEquippedStack(EquipmentSlot.CHEST);
         ITEM_STACKS[2] = livingEntity.getEquippedStack(EquipmentSlot.LEGS);
         ITEM_STACKS[3] = livingEntity.getEquippedStack(EquipmentSlot.FEET);
      } else {
         ITEM_STACKS[0] = null;
         ITEM_STACKS[1] = null;
         ITEM_STACKS[2] = null;
         ITEM_STACKS[3] = null;
      }

      float floatValue134 = 3.99F * j;

      for (int intValue42 = 0; intValue42 < 4; intValue42++) {
         float floatValue135 = f + intValue42 * (m + floatValue134);
         if (hudElement == null || !hudElement.check9() && !hudElement.check10()) {
            if (!bl || !NeumorphismRenderer.check11(null, floatValue135, g, m, m, 4.0F * j, Math.max(1.6F, 2.8F * j), Math.max(3.0F, 5.5F * j), 0.82F, 2, true, h)) {
               renderManager10.invoke5(floatValue135, g, m, m, 4.0F * j, k);
               renderManager10.invoke28(floatValue135, g, m, m, 4.0F * j, l, 1.0F * j);
            }
         } else {
            hudElement.invoke2(renderManager10, floatValue135, g, m, m, 4.0F * j, h);
         }
      }

      NeumorphismRenderer.invoke2();
      renderManager10.invoke20();
      if (!(i <= 0.01F)) {
         renderManager10.invoke65(i);

         for (int intValue43 = 0; intValue43 < 4; intValue43++) {
            float floatValue136 = f + intValue43 * (m + floatValue134);
            ItemStack itemStack2 = ITEM_STACKS[intValue43];
            if (itemStack2 != null && !itemStack2.isEmpty()) {
               float floatValue137 = m / 16.0F * 0.72F;
               float floatValue138 = 16.0F * floatValue137;
               float floatValue139 = floatValue136 + (m - floatValue138) / 2.0F;
               float floatValue140 = g + (m - floatValue138) / 2.0F;
               ItemRenderUtil.invoke3(renderManager10, itemStack2, floatValue139, floatValue140, floatValue137, intValue43, false, 0);
            }
         }

         renderManager10.invoke66();
      }
   }

   record TargetHudData(float x, float y) {
   }

   static final class TargetHudState {
      float floatValue;
      float floatValue2;
      float floatValue3;
      float floatValue4;
      final float floatValue5;
      final int intValue;
      int intValue2;

      TargetHudState(float f, float g, float h, float i, float j, int k) {
         this.floatValue = f;
         this.floatValue2 = g;
         this.floatValue3 = h;
         this.floatValue4 = i;
         this.floatValue5 = j;
         this.intValue = k;
      }
   }
}
