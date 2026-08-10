package ru.metaculture.protection;

import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.block.BlockState;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.AmbientEntity;
import net.minecraft.entity.mob.Monster;
import net.minecraft.entity.mob.SlimeEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.MerchantEntity;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.ShieldItem;
import net.minecraft.network.packet.c2s.play.HandSwingC2SPacket;
import net.minecraft.network.packet.c2s.play.UpdateSelectedSlotC2SPacket;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AttackAura",
   description = "Автоматически бьет энтити - таргетов",
   category = Category.Combat,
   riskLevels = {ModuleRiskLevel.RISKY, ModuleRiskLevel.GRIM}
)
public class AttackAura extends Module {
   public static NumberSetting radiusAtaki = new NumberSetting("Радиус атаки", 3.0F, 3.0F, 6.0F, 0.1F, false);
   public static NumberSetting radiusObnaruzheniya = new NumberSetting("Радиус обнаружения", 1.0F, 0.0F, 5.0F, 0.1F, false);
   public static ModeSetting rezhimRotatsii = new ModeSetting("Режим ротации", "Smooth", resolve());
   public static ButtonSetting konstruktorRotatsii = new ButtonSetting("Конструктор ротации", 0)
      .setRun("Открыть")
      .setRunnable(AttackAura::invoke27)
      .setVisibilityCondition(() -> !rezhimRotatsii.is("Custom"));
   public static NumberSetting aiJitter = new NumberSetting("AI Jitter", 1.0F, 0.0F, 2.0F, 0.05F, false).setVisibilityCondition(() -> !rezhimRotatsii.is("AI"));
   public static BooleanSetting aiDebugLog = new BooleanSetting("AI Debug Log", false).visibleWhen(() -> !rezhimRotatsii.is("AI"));
   public static BooleanSetting aiHumanMisses = new BooleanSetting("AI Human Misses", false).visibleWhen(() -> !rezhimRotatsii.is("AI"));
   public static ButtonSetting aiLab = new ButtonSetting("AI Lab", 0)
      .setRun("Открыть")
      .setRunnable(AttackAura::invoke28)
      .setVisibilityCondition(() -> !rezhimRotatsii.is("AI"));
   public static ModeSetting rezhimSnapa = new ModeSetting("Режим снапа", "Fast", "Fast", "Smooth", "Random")
      .setVisibilityCondition(() -> !rezhimRotatsii.is("Snap") && !rezhimRotatsii.is("FOV"));
   public static NumberSetting fov = new NumberSetting("FOV", 90.0F, 5.0F, 180.0F, 1.0F, true).setVisibilityCondition(() -> !rezhimRotatsii.is("FOV"));
   public static BooleanSetting otobrazhatFov = new BooleanSetting("Отображать FOV", true).visibleWhen(() -> !rezhimRotatsii.is("FOV"));
   public static BooleanSetting lipnutKIgroku = new BooleanSetting("Липнуть к игроку", false).visibleWhen(() -> !rezhimRotatsii.is("Legit"));
   public static NumberSetting skorostLegit = new NumberSetting("Скорость Legit", 0.08F, 0.02F, 0.4F, 0.01F, false)
      .setVisibilityCondition(() -> !rezhimRotatsii.is("Legit"));
   public static BooleanSetting sidepointExtraChecks = new BooleanSetting("SidePoint Extra Checks", false).visibleWhen(() -> !rezhimRotatsii.is("Side Point"));
   public static DynamicButtonSetting neuroStatus = new DynamicButtonSetting("Neuro Status", 0, NeuroRotationController::resolve)
      .setVisibilityCondition(() -> !rezhimRotatsii.is("Neuro") || !check());
   public static BooleanSetting neuroDebug = new BooleanSetting("Neuro Debug", false)
      .visibleWhen(() -> !rezhimRotatsii.is("Neuro") || !check());
   public static ModeSetting neuroProfile = new ModeSetting("Neuro Profile", "Human", "Stable", "Human", "Dynamic")
      .setVisibilityCondition(() -> !rezhimRotatsii.is("Neuro") || !check());
   public static NumberSetting neuroStrength = new NumberSetting("Neuro Strength", 1.25F, 0.0F, 2.0F, 0.05F, false)
      .setVisibilityCondition(() -> !rezhimRotatsii.is("Neuro") || !check());
   public static BooleanSetting neuroClientFinish = new BooleanSetting("Neuro Client Finish", false)
      .visibleWhen(() -> !rezhimRotatsii.is("Neuro") || !check());
   public static GroupSetting tseli = new GroupSetting(
      "Цели",
      new BooleanSetting("Игроки", true),
      new BooleanSetting("Голые", true),
      new BooleanSetting("Невидимки", true),
      new BooleanSetting("Голые невидимки", false),
      new BooleanSetting("Друзья", false),
      new BooleanSetting("NPC", true),
      new BooleanSetting("Мобы", false),
      new BooleanSetting("Животные", false),
      new BooleanSetting("Жители", false)
   );
   public static ModeSetting taymingUdara = new ModeSetting("Тайминг удара", "Быстрый", "Быстрый", "Динамичный");
   public static BooleanSetting adaptivnyyTayming = new BooleanSetting("Адаптивный тайминг", false);
   public static ModeSetting rezhimSprinta = new ModeSetting("Режим спринта", "Обычный", "Обычный", "Обновленный", "Тестовый", "Легит");
   public static GroupSetting proverkiDoUdara = new GroupSetting(
      "Проверки до удара",
      new BooleanSetting("Бить через блоки", false),
      new BooleanSetting("Бить только оружием", false),
      new BooleanSetting("Не бить если кушаешь", true),
      new BooleanSetting("Не бить в контейнерах ", false),
      new BooleanSetting("Ломать щит", false),
      new BooleanSetting("Отжим щита", false)
   );
   public static GroupSetting dopolnitelnyeNastroyki = new GroupSetting(
      "Дополнительные настройки",
      new BooleanSetting("Расширенная настройки для атаки", true),
      new BooleanSetting("Умные криты", false),
      new BooleanSetting("Увеличенная дистанция удара", false),
      new BooleanSetting("Приоритет ближайшей цели", false)
   );
   public static NumberSetting radiusAtakiDlyaMobov = new NumberSetting("Радиус атаки для мобов", 3.0F, 3.0F, 6.0F, 0.1F, false)
      .setVisibilityCondition(() -> !dopolnitelnyeNastroyki.isEnabled("Расширенная настройки для атаки") && !tseli.isEnabled("Мобы"));
   public static NumberSetting radiusAtakiDlyaIgrokov = new NumberSetting("Радиус атаки для игроков", 3.0F, 3.0F, 6.0F, 0.1F, false)
      .setVisibilityCondition(() -> !dopolnitelnyeNastroyki.isEnabled("Расширенная настройки для атаки") && !tseli.isEnabled("Игроки"));
   public static ModeSetting rezhimDvizheniya = new ModeSetting("Режим движения", "Default", "Default", "Free", "Target", "Преследование");
   public static BooleanSetting bulava = new BooleanSetting("Булава", false);
   public static ModeSetting rezhimBulavy = new ModeSetting("Режим булавы", "Авто", "Авто", "Бинд").setVisibilityCondition(() -> !bulava.isEnabled());
   public static KeybindSetting knopkaBulavy = new KeybindSetting("Кнопка булавы", -1)
      .visibleWhen(() -> !bulava.isEnabled() || !rezhimBulavy.is("Бинд"));
   public static NumberSetting vysotaBulavy = new NumberSetting("Высота булавы", 2.0F, 0.5F, 6.0F, 0.1F, false)
      .setVisibilityCondition(() -> !bulava.isEnabled() || !rezhimBulavy.is("Авто"));
   public static BooleanSetting otladkaBulavy = new BooleanSetting("Отладка булавы", false).visibleWhen(() -> !bulava.isEnabled());
   public static LivingEntity livingEntity;
   public static boolean flag = false;
   private static final Runnable RUNNABLE = RandomAimStrategy::invoke2;
   private static long timestamp = 0L;
   private static boolean flag2 = false;
   private static float floatValue = 0.0F;
   private static long timestamp2 = 0L;
   private static float floatValue2 = 0.0F;
   private static long timestamp3 = 0L;
   private static long timestamp4 = 0L;
   private static int intValue = Integer.MIN_VALUE;
   private boolean flag3 = false;
   private boolean flag4 = false;
   private static final String AURAMACE = "AuraMace";
   private static final int INT_VALUE = 40;
   private static final int INT_VALUE_2 = 4;
   private static final long TIMESTAMP = 300L;
   private static int intValue2 = 0;
   private static int intValue3 = 0;
   private static boolean flag5 = false;
   private static int intValue4 = -1;
   private static int intValue5 = -1;
   private static boolean flag6 = false;
   private static int intValue6 = -1;
   private static boolean flag7 = false;
   private static boolean flag8 = false;
   private static int intValue7 = 0;
   private static int intValue8 = 0;
   private static long timestamp5 = 0L;
   public static long timestamp6 = 0L;
   public static long timestamp7 = ThreadLocalRandom.current().nextLong(90000L, 180000L);
   public static boolean flag9 = false;
   public static long timestamp8 = 0L;
   public static int intValue9 = 0;

   private static String[] resolve() {
      return check()
         ? new String[]{"Matrix", "Random Smooth ", "Snap", "FOV", "Smooth ", "FunTime", "FT-New", "SpookyTime", "Legit", "Custom", "AI", "Neuro"}
         : new String[]{"Matrix", "Random Smooth ", "Snap", "FOV", "Smooth ", "FunTime", "FT-New", "SpookyTime", "Legit", "Custom", "AI"};
   }

   private static boolean check() {
      return ModuleManager.hasAccess(AttackAura.AttackAuraState.class.getAnnotation(ModuleAccess.class));
   }

   private static boolean check2() {
      return rezhimRotatsii.is("Neuro") && !check();
   }

   public AttackAura() {
      AttackUtils.invoke3();
      this.addSettings(
         new Setting[]{
            radiusAtaki,
            radiusObnaruzheniya,
            rezhimRotatsii,
            konstruktorRotatsii,
            aiJitter,
            aiDebugLog,
            aiHumanMisses,
            aiLab,
            rezhimSnapa,
            fov,
            otobrazhatFov,
            lipnutKIgroku,
            skorostLegit,
            neuroStatus,
            neuroDebug,
            neuroProfile,
            neuroStrength,
            neuroClientFinish,
            tseli,
            taymingUdara,
            adaptivnyyTayming,
            rezhimSprinta,
            proverkiDoUdara,
            dopolnitelnyeNastroyki,
            radiusAtakiDlyaMobov,
            radiusAtakiDlyaIgrokov,
            rezhimDvizheniya,
            bulava,
            rezhimBulavy,
            knopkaBulavy,
            vysotaBulavy,
            otladkaBulavy,
            ElytraTarget.RADIUS_OBNARUZHENIYA_ELITRY
         }
      );
   }

   @EventHandler
   public void onHudRender(HudRenderEvent hudRenderEvent) {
      if (this.enabled && !check16() && rezhimRotatsii.is("FOV") && hudRenderEvent.getRenderManager() != null && otobrazhatFov.isEnabled()) {
         AimPointCalculator.invoke(hudRenderEvent.getRenderManager(), fov.getValue(), hudRenderEvent.getIntValue(), hudRenderEvent.getIntValue2());
      }
   }

   @EventHandler
   public void onClientTick(ClientTickEvent clientTickEvent) {
      CriticalHitController.invoke3();
      if (check16()) {
         RandomAimStrategy.invoke4();
         NoiseAimStrategy.invoke2();
         this.invoke29();
         this.invoke30();
      } else if (livingEntity != null && CLIENT.player != null && CLIENT.world != null) {
         this.invoke26();
         if (!rezhimRotatsii.is("Legit")) {
            this.invoke2();
         }
      } else {
         RandomAimStrategy.invoke4();
         NoiseAimStrategy.invoke2();
         this.invoke29();
         this.invoke30();
      }
   }

   @EventHandler
   public void onCameraRotation(CameraRotationEvent cameraRotationEvent) {
      if (!check16() && rezhimRotatsii.is("Legit")) {
         if (livingEntity != null && CLIENT.player != null && CLIENT.world != null) {
            DirectAimStrategy.invoke(livingEntity, cameraRotationEvent);
         }
      }
   }

   @EventHandler
   public void onMovementInput(MovementInputEvent movementInputEvent) {
      if (!check16() && rezhimSprinta.is("Тестовый")) {
         if (livingEntity != null && CLIENT.player != null && CLIENT.world != null && CLIENT.currentScreen == null) {
            Vec3d vec3d = livingEntity.getPos().add(0.0, livingEntity.getHeight() * 0.5, 0.0).subtract(CLIENT.player.getEyePos());
            float floatValue = (float)Math.toDegrees(Math.atan2(-vec3d.x, vec3d.z));
            MovementUtils.invoke(movementInputEvent, floatValue);
            if (AttackUtils.check18(livingEntity, resolve2(livingEntity))) {
               movementInputEvent.setFloatValue(0.0F);
               movementInputEvent.setFloatValue2(0.0F);
            }
         }
      }
   }

   @EventHandler
   public void onTpsPulse(TpsPulseEvent tpsPulseEvent) {
      if (!check16()) {
         if (proverkiDoUdara.isEnabled("Синхрон с ТПС")) {
            this.invoke26();
            if (!rezhimSprinta.is("Легит") && this.check5()) {
               CLIENT.player.setSprinting(false);
               CLIENT.options.sprintKey.setPressed(false);
            }

            if (!this.check12()) {
               this.invoke();
            }
         }
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      this.invoke12();
      if (check16()) {
         this.invoke29();
         this.invoke5();
         this.invoke7();
      } else if (!CLIENT.player.isAlive()) {
         this.invoke29();
         this.invoke5();
         this.invoke7();
         this.toggle();
      } else {
         if (livingEntity == null || !this.check7(livingEntity)) {
            this.invoke10();
         }

         if (livingEntity == null) {
            RandomAimStrategy.invoke4();
            NoiseAimStrategy.invoke2();
            this.invoke29();
            this.invoke30();
            this.invoke5();
            this.invoke7();
         } else if (CLIENT.currentScreen != null) {
            this.invoke5();
            this.invoke7();
            this.invoke8();
         } else if (intValue2 != 0) {
            this.invoke5();
            this.invoke7();
            InputUtils.getINSTANCE().invoke("AuraMace");
         } else {
            this.invoke6();
            if (rezhimDvizheniya.is("Free")) {
               this.invoke5();
               MovementUtils.invoke4(CLIENT.gameRenderer.getCamera().getYaw());
            }

            if (rezhimDvizheniya.is("Target")) {
               this.invoke5();
               MovementUtils.invoke3(CLIENT.player.getYaw(), livingEntity.getPos());
            }

            if (rezhimDvizheniya.is("Преследование")) {
               this.invoke3();
            } else {
               this.invoke5();
            }

            if (!proverkiDoUdara.isEnabled("Синхрон с ТПС")) {
               this.invoke26();
               if (!rezhimSprinta.is("Легит") && this.check5()) {
                  CLIENT.player.setSprinting(false);
                  CLIENT.options.sprintKey.setPressed(false);
               }

               if (!this.check12()) {
                  this.invoke();
               }
            }
         }
      }
   }

   public static float measure(LivingEntity livingEntity) {
      if (livingEntity == null) {
         return radiusAtaki.getValue();
      } else {
         float floatValue2 = radiusAtaki.getValue();
         if (dopolnitelnyeNastroyki.isEnabled("Расширенная настройки для атаки")) {
            if (livingEntity instanceof PlayerEntity) {
               floatValue2 = radiusAtakiDlyaIgrokov.getValue();
            } else {
               floatValue2 = radiusAtakiDlyaMobov.getValue();
            }
         }

         if (dopolnitelnyeNastroyki.isEnabled("Увеличенная дистанция удара")) {
            float floatValue3 = livingEntity.getHealth() + livingEntity.getAbsorptionAmount();
            if (floatValue3 >= 10.0F && floatValue3 <= 12.0F) {
               long longValue = System.currentTimeMillis();
               if (longValue >= timestamp) {
                  if (ThreadLocalRandom.current().nextInt(100) < 25) {
                     flag2 = true;
                     floatValue = 0.1F + ThreadLocalRandom.current().nextFloat() * 0.05F;
                     timestamp = longValue + ThreadLocalRandom.current().nextLong(400L, 700L);
                  } else {
                     flag2 = false;
                     floatValue = 0.0F;
                     timestamp = longValue + ThreadLocalRandom.current().nextLong(1500L, 2500L);
                  }
               }

               if (flag2) {
                  return floatValue2 + floatValue;
               }
            } else {
               flag2 = false;
               floatValue = 0.0F;
            }
         }

         return floatValue2;
      }
   }

   public static float[] resolve2(LivingEntity livingEntity) {
      float floatValue4 = measure(livingEntity);
      return new float[]{floatValue4, radiusObnaruzheniya.getValue(), floatValue4 + radiusObnaruzheniya.getValue()};
   }

   public boolean check3() {
      return true;
   }

   public void invoke() {
      assert CLIENT.player != null;

      if (intValue2 == 0) {
         float floatValue5 = measure(livingEntity);
         if (!(RaycastUtils.measure((Entity)livingEntity) >= floatValue5)) {
            float[] floatValues = resolve2(livingEntity);
            floatValues = new float[]{floatValues[0], floatValues[1], floatValues[0] + floatValues[1]};
            if (!CLIENT.player.hasStatusEffect(StatusEffects.BLINDNESS) && !CLIENT.player.isFlyingVehicle() && rezhimSprinta.is("Обычный")) {
               boolean flag = true;
            } else {
               boolean flag2 = false;
            }

            if (livingEntity != null) {
               if (!rezhimRotatsii.is("FOV") || AimPointCalculator.check(livingEntity, fov.getValue())) {
                  if (!rezhimRotatsii.is("AI") || AiRotationTrainer.check3()) {
                     AttackUtils.invoke6(livingEntity, true, this.check3(), false);
                     boolean flag3 = rezhimRotatsii.is("FT-New");
                     boolean flag4 = bulava.isEnabled() && CLIENT.player.getMainHandStack().getItem() == Items.MACE;
                     boolean attackReady;
                     if (flag4 && AttackAura.flag5) {
                        long longValue2 = rezhimBulavy.is("Авто") ? -2000L : 0L;
                        boolean flag6 = RaycastUtils.check(livingEntity, floatValues[0], true);
                        boolean flag7 = AttackUtils.check10(longValue2);
                        boolean flag8 = !this.check3() || AttackUtils.check13(livingEntity, floatValues[0]);
                        boolean flag9 = !rezhimBulavy.is("Авто") || !flag8;
            attackReady = intValue2 == 0 && !flag7 && flag9 && flag6 && flag7 && flag8;
                     } else if (flag3) {
                        attackReady = CriticalHitController.check(livingEntity, 0) && (!this.check3() || AttackUtils.check13(livingEntity, floatValues[0]));
                     } else {
                        attackReady = AttackUtils.check15(livingEntity, this.check3(), true, true, compute4(), floatValues);
                     }

                     if (attackReady) {
                        if (!flag3 || CriticalHitController.check3(livingEntity) && CriticalHitController.check4(proverkiDoUdara.isEnabled("Отжим щита"))) {
                           if (flag3 || !adaptivnyyTayming.isEnabled() || !AttackUtils.check7(livingEntity)) {
                              Runnable[] runnables = AttackUtils.resolve(livingEntity, !flag3 && proverkiDoUdara.isEnabled("Ломать щит"));
                              Runnable[] runnables2 = AttackUtils.resolve2(!flag3);
                              Runnable[] runnables3 = AttackUtils.resolve3(false);
                              Runnable runnable = () -> {
                                 runnables3[0].run();
                                 runnables2[0].run();
                                 runnables[0].run();
                              };
                              Runnable runnable2 = () -> {
                                 runnables[1].run();
                                 runnables2[1].run();
                                 runnables3[1].run();
                              };
                              if (!flag3
                                 && proverkiDoUdara.isEnabled("Отжим щита")
                                 && CLIENT.player.getActiveItem().getItem().equals(Items.SHIELD)
                                 && CLIENT.player.isUsingItem()) {
                                 CLIENT.interactionManager.stopUsingItem(CLIENT.player);
                              }

                              if (!flag3 && adaptivnyyTayming.isEnabled()) {
                                 Runnable runnable3 = rezhimRotatsii.is("FunTime") ? RUNNABLE : null;
                                 AttackUtils.check6(livingEntity, runnable, runnable2, Hand.MAIN_HAND, true, runnable3);
                              } else {
                                 if (AttackUtils.check5(livingEntity, runnable, runnable2, Hand.MAIN_HAND, true)) {
                                    if (rezhimRotatsii.is("FunTime")) {
                                       RandomAimStrategy.invoke2();
                                    } else if (flag3) {
                                       CriticalHitController.invoke4();
                                    }

                                    this.invoke23();
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   private void invoke2() {
      if (livingEntity != null && !check16() && !check2() && (intValue2 == 0 || flag6)) {
         if (!rezhimRotatsii.is("FT-New")) {
            AttackRotationController.invoke4();
            NoiseAimStrategy.invoke3();
         }

         this.invoke26();
         double doubleValue = CLIENT.player.getY() - livingEntity.getY();
         boolean flag10 = CLIENT.player.getMainHandStack().getItem() == Items.MACE;
         boolean flag11 = !CLIENT.player.isOnGround() && (doubleValue >= 2.0 || flag10);
         if (!rezhimRotatsii.is("Legit") && flag10 && flag11 && CLIENT.player.getY() > livingEntity.getY()) {
            RotationSmoothing.invoke3(livingEntity);
         } else if (!rezhimRotatsii.is("Legit") && bulava.isEnabled() && flag10) {
            SmoothAimStrategy.invoke(livingEntity);
         } else {
            float[] floatValues2 = resolve2(livingEntity);
            floatValues2 = new float[]{floatValues2[0], floatValues2[1], floatValues2[0] + floatValues2[1]};
            boolean flag12 = AttackUtils.check15(livingEntity, false, true, true, compute4(), floatValues2);
            String text = rezhimRotatsii.getValue();
            switch (text) {
               case "Random Smooth ":
                  PredictiveAimStrategy.invoke(
                     livingEntity,
                     AttackUtils.check15(livingEntity, false, true, true, compute5(-50L), floatValues2),
                     measure(livingEntity),
                     this.check12()
                  );
                  break;
               case "Matrix":
                  if (ServerBlockUtils.check6("spookytime")) {
                     RotationSmoothing.invoke4(livingEntity, flag12);
                  } else if (ServerBlockUtils.check6("holy")) {
                     RotationSmoothing.invoke5(livingEntity, flag12);
                  } else if (ServerBlockUtils.check6("ares")) {
                     RotationSmoothing.invoke6(livingEntity, flag12);
                  } else {
                     RotationSmoothing.invoke4(livingEntity, flag12);
                  }
                  break;
               case "Snap":
                  RotationSmoothing.invoke9(
                     livingEntity, AttackUtils.check15(livingEntity, false, true, true, compute5(-50L), floatValues2), rezhimSnapa.getValue()
                  );
                  break;
               case "FOV":
                  boolean flag13 = AimPointCalculator.check(livingEntity, fov.getValue());
                  boolean flag14 = flag13 && AttackUtils.check15(livingEntity, false, true, true, compute5(-50L), floatValues2);
                  RotationSmoothing.invoke10(livingEntity, flag14, rezhimSnapa.getValue());
                  break;
               case "Smooth ":
                  SmoothAimStrategy.invoke(livingEntity);
                  break;
               case "FunTime":
                  RandomAimStrategy.invoke(livingEntity);
                  break;
               case "FT-New":
                  NoiseAimStrategy.invoke(livingEntity);
                  break;
               case "SpookyTime":
                  MultipointAimStrategy.invoke(livingEntity);
                  break;
               case "Custom":
                  AimPointMode.invoke(livingEntity);
                  break;
               case "Lony Grief":
                  HumanizedRotationPattern.invoke(livingEntity);
                  break;
               case "Side Point":
                  BoundingBoxAimStrategy.invoke(livingEntity);
                  break;
               case "AI":
                  AiRotationTrainer.invoke3(livingEntity);
                  break;
               case "Neuro":
                  NeuroRotationController.invoke2(livingEntity, flag12, this.check12(), neuroDebug.isEnabled());
            }
         }
      }
   }

   private void invoke3() {
      if (CLIENT.player != null && livingEntity != null && CLIENT.options != null && CLIENT.getWindow() != null) {
         if (!CLIENT.player.isUsingItem() && !CLIENT.player.isSneaking() && !CLIENT.player.hasVehicle() && CLIENT.currentScreen == null) {
            float[] floatValues3 = MovementUtils.resolve3();
            float floatValue6 = floatValues3[0];
            float floatValue7 = floatValues3[1];
            Vec3d vec3d2 = livingEntity.getPos();
            if (floatValue6 != 0.0F || floatValue7 != 0.0F) {
               Vec3d vec3d3 = this.resolve3(livingEntity.getYaw());
               Vec3d vec3d4 = this.resolve3(livingEntity.getYaw() + 90.0F);
               vec3d2 = vec3d2.add(vec3d3.multiply(floatValue6)).add(vec3d4.multiply(-floatValue7));
            }

            Vec3d vec3d5 = vec3d2.subtract(CLIENT.player.getPos());
            if (vec3d5.x * vec3d5.x + vec3d5.z * vec3d5.z < 1.0E-4) {
               this.invoke4(1.0F, 0.0F, true, false);
            } else {
               float floatValue8 = (float)MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec3d5.z, vec3d5.x)) - 90.0);
               float floatValue9 = AttackPacketController.measure(CLIENT.player.getYaw());
               float floatValue10 = 0.0F;
               float floatValue11 = 0.0F;
               float floatValue12 = Float.MAX_VALUE;

               for (float floatValue13 = -1.0F; floatValue13 <= 1.0F; floatValue13++) {
                  for (float floatValue14 = -1.0F; floatValue14 <= 1.0F; floatValue14++) {
                     if (floatValue13 != 0.0F || floatValue14 != 0.0F) {
                        double doubleValue2 = MathHelper.wrapDegrees(Math.toDegrees(MovementUtils.measure(floatValue9, floatValue13, floatValue14)));
                        float floatValue15 = this.measure2(floatValue8, (float)doubleValue2);
                        if (floatValue15 < floatValue12) {
                           floatValue12 = floatValue15;
                           floatValue10 = floatValue13;
                           floatValue11 = floatValue14;
                        }
                     }
                  }
               }

               boolean flag15 = CLIENT.player.horizontalCollision && CLIENT.player.isOnGround();
               this.invoke4(floatValue10, floatValue11, true, flag15);
            }
         } else {
            this.invoke5();
         }
      } else {
         this.invoke5();
      }
   }

   private Vec3d resolve3(float f) {
      double doubleValue3 = Math.toRadians(f);
      return new Vec3d(-Math.sin(doubleValue3), 0.0, Math.cos(doubleValue3));
   }

   private float measure2(float f, float g) {
      return Math.abs(MathHelper.wrapDegrees(f - g));
   }

   private void invoke4(float f, float g, boolean bl, boolean bl2) {
      if (CLIENT.options != null) {
         this.flag3 = true;
         CLIENT.options.forwardKey.setPressed(f > 0.0F);
         CLIENT.options.backKey.setPressed(f < 0.0F);
         CLIENT.options.leftKey.setPressed(g > 0.0F);
         CLIENT.options.rightKey.setPressed(g < 0.0F);
         boolean flag16 = bl;
         if (rezhimSprinta.is("Легит") && livingEntity != null && AttackUtils.check18(livingEntity, resolve2(livingEntity))) {
            CLIENT.player.setSprinting(false);
            flag16 = false;
         }

         CLIENT.options.sprintKey.setPressed(flag16);
         if (bl2) {
            CLIENT.options.jumpKey.setPressed(true);
         } else if (!this.check6(CLIENT.options.jumpKey)) {
            CLIENT.options.jumpKey.setPressed(false);
         }
      }
   }

   private void invoke5() {
      if (this.flag3 && CLIENT.options != null) {
         this.flag3 = false;
         this.invoke9(CLIENT.options.forwardKey);
         this.invoke9(CLIENT.options.backKey);
         this.invoke9(CLIENT.options.leftKey);
         this.invoke9(CLIENT.options.rightKey);
         this.invoke9(CLIENT.options.jumpKey);
         this.invoke9(CLIENT.options.sprintKey);
      }
   }

   private void invoke6() {
      if (rezhimSprinta.is("Легит")
         && CLIENT.player != null
         && CLIENT.world != null
         && CLIENT.options != null
         && CLIENT.currentScreen == null) {
         this.flag4 = true;
         boolean flag17 = this.check5();
         if (flag17) {
            CLIENT.player.setSprinting(false);
            CLIENT.options.sprintKey.setPressed(false);
         } else {
            CLIENT.options.sprintKey.setPressed(this.check4());
         }
      } else {
         this.invoke7();
      }
   }

   private boolean check4() {
      return CLIENT.options == null ? false : this.check6(CLIENT.options.forwardKey) && !this.check6(CLIENT.options.backKey);
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (rezhimRotatsii.is("FT-New") && packetEvent.check()) {
         if (packetEvent.getPacket() instanceof HandSwingC2SPacket || packetEvent.getPacket() instanceof UpdateSelectedSlotC2SPacket) {
            CriticalHitController.invoke5();
         }
      }
   }

   private boolean check5() {
      if (livingEntity == null || CLIENT.player == null) {
         return false;
      } else {
         return rezhimRotatsii.is("FT-New") ? CriticalHitController.check2(livingEntity) : AttackUtils.check18(livingEntity, resolve2(livingEntity));
      }
   }

   private void invoke7() {
      if (this.flag4 && CLIENT.options != null) {
         this.flag4 = false;
         this.invoke9(CLIENT.options.sprintKey);
      }
   }

   private void invoke8() {
      if (CLIENT.options != null) {
         CLIENT.options.forwardKey.setPressed(false);
         CLIENT.options.backKey.setPressed(false);
         CLIENT.options.leftKey.setPressed(false);
         CLIENT.options.rightKey.setPressed(false);
         CLIENT.options.jumpKey.setPressed(false);
         this.invoke9(CLIENT.options.sprintKey);
      }
   }

   private void invoke9(KeyBinding keyBinding) {
      if (keyBinding != null) {
         keyBinding.setPressed(this.check6(keyBinding));
      }
   }

   private boolean check6(KeyBinding keyBinding) {
      return CLIENT.getWindow() != null && keyBinding != null
         ? InputUtil.isKeyPressed(CLIENT.getWindow().getHandle(), keyBinding.getDefaultKey().getCode())
         : false;
   }

   private void invoke10() {
      LivingEntity livingEntity2 = livingEntity;
      LivingEntity livingEntity3 = null;
      double doubleValue4 = Double.MAX_VALUE;
      Vec3d vec3d6 = CLIENT.player.getEyePos();
      Vec3d vec3d7 = CLIENT.player.getRotationVec(1.0F).normalize();

      for (Entity entity : CLIENT.world.getEntities()) {
         if (entity instanceof LivingEntity livingEntity4 && this.check7(livingEntity4)) {
            double doubleValue5;
            if (dopolnitelnyeNastroyki.isEnabled("Приоритет ближайшей цели")) {
               doubleValue5 = CLIENT.player.squaredDistanceTo(livingEntity4);
            } else {
               Vec3d vec3d8 = livingEntity4.getPos().add(0.0, livingEntity4.getHeight() * 0.5, 0.0);
               Vec3d vec3d9 = vec3d8.subtract(vec3d6).normalize();
               doubleValue5 = Math.acos(MathHelper.clamp(vec3d7.dotProduct(vec3d9), -1.0, 1.0));
            }

            if (doubleValue5 < doubleValue4) {
               doubleValue4 = doubleValue5;
               livingEntity3 = livingEntity4;
            }
         }
      }

      livingEntity = livingEntity3;
      if (rezhimRotatsii.is("FunTime") && livingEntity2 != null && livingEntity3 == null) {
         RandomAimStrategy.invoke3();
      }
   }

   private float measure3() {
      return measure(livingEntity) + radiusObnaruzheniya.getValue();
   }

   private boolean check7(LivingEntity livingEntity) {
      return this.check8(livingEntity, measure(livingEntity) + radiusObnaruzheniya.getValue());
   }

   private boolean check8(LivingEntity livingEntity, float f) {
      if (livingEntity instanceof ClientPlayerEntity || livingEntity == CLIENT.player) {
         return false;
      } else if (livingEntity.isAlive() && !livingEntity.isInvulnerable() && !(livingEntity instanceof ArmorStandEntity)) {
         if (CLIENT.player.distanceTo(livingEntity) > f) {
            return false;
         } else if (!proverkiDoUdara.isEnabled("Бить через блоки") && !CLIENT.player.canSee(livingEntity)) {
            return false;
         } else if (!tseli.isEnabled("NPC") && this.check10(livingEntity)) {
            return false;
         } else if (livingEntity instanceof PlayerEntity playerEntity2) {
            if (!playerEntity2.isCreative() && !playerEntity2.isSpectator()) {
               boolean flag18 = FriendCommand.check(playerEntity2.getName().getString());
               if (flag18 && !tseli.isEnabled("Друзья")) {
                  return false;
               } else if (!flag18 && !tseli.isEnabled("Игроки")) {
                  return false;
               } else {
                  boolean flag19 = !this.check9(playerEntity2);
                  boolean flag20 = playerEntity2.isInvisible();
                  if (AntiBot.check7(playerEntity2)) {
                     return false;
                  } else if (flag20) {
                     return flag19 ? tseli.isEnabled("Голые невидимки") : tseli.isEnabled("Невидимки");
                  } else {
                     return !flag19 || tseli.isEnabled("Голые");
                  }
               }
            } else {
               return false;
            }
         } else {
            boolean flag21 = livingEntity instanceof Monster || livingEntity instanceof SlimeEntity;
            boolean flag22 = livingEntity instanceof VillagerEntity || livingEntity instanceof MerchantEntity;
            boolean flag23 = livingEntity instanceof AnimalEntity
               || livingEntity instanceof VillagerEntity
               || livingEntity instanceof WaterCreatureEntity
               || livingEntity instanceof AmbientEntity;
            if (flag21 && tseli.isEnabled("Мобы")) {
               return true;
            } else {
               return flag22 && tseli.isEnabled("Жители") ? true : flag23 && tseli.isEnabled("Животные");
            }
         }
      } else {
         return false;
      }
   }

   private boolean check9(PlayerEntity playerEntity) {
      return !playerEntity.getEquippedStack(EquipmentSlot.HEAD).isEmpty()
         || !playerEntity.getEquippedStack(EquipmentSlot.CHEST).isEmpty()
         || !playerEntity.getEquippedStack(EquipmentSlot.LEGS).isEmpty()
         || !playerEntity.getEquippedStack(EquipmentSlot.FEET).isEmpty();
   }

   private boolean check10(LivingEntity livingEntity) {
      String text2 = this.resolve4(livingEntity.getName().getString());
      String text3 = this.resolve4(livingEntity.getDisplayName().getString());
      String text4 = livingEntity.getCustomName() == null ? "" : this.resolve4(livingEntity.getCustomName().getString());
      String text5 = "";
      String text6 = "";
      if (livingEntity.getScoreboardTeam() != null) {
         text5 = this.resolve4(livingEntity.getScoreboardTeam().getPrefix().getString());
         text6 = this.resolve4(livingEntity.getScoreboardTeam().getSuffix().getString());
      }

      if (this.check11(text2) || this.check11(text3) || this.check11(text4) || this.check11(text5) || this.check11(text6)) {
         return true;
      } else if (!(livingEntity instanceof PlayerEntity playerEntity3)) {
         return false;
      } else {
         boolean flag24 = CLIENT.getNetworkHandler() != null && CLIENT.getNetworkHandler().getPlayerListEntry(playerEntity3.getUuid()) == null;
         boolean flag25 = text2.matches("\\d{1,8}") || text2.startsWith("cit-");
         return flag24 || flag25 && (!text3.equals(text2) || !text5.isEmpty() || !text6.isEmpty());
      }
   }

   private boolean check11(String string) {
      return string.contains("npc") || string.contains("znpc") || string.contains("нпс") || string.contains("наставник");
   }

   private String resolve4(String string) {
      return string == null ? "" : string.replaceAll("(?i)§.", "").replaceAll("(?i)&.", "").replaceAll("\\p{Cntrl}", "").trim().toLowerCase(Locale.ROOT);
   }

   @Override
   public void onEnable() {
      FreeLock freeLock = WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null
         ? WildClient.INSTANCE.moduleManager.getModule(FreeLock.class)
         : null;
      if (freeLock != null && freeLock.enabled && freeLock.check()) {
         this.enabled = false;
         ChatUtil.sendClientMessage("Отключите FreeLock перед включением AttackAura");
      } else {
         CriticalHitController.invoke();
         super.onEnable();
      }
   }

   @Override
   public void toggle() {
      super.toggle();
      this.invoke11();
   }

   private void invoke11() {
      this.invoke24();
      this.invoke29();
      this.invoke30();
      AttackUtils.invoke4();
      this.invoke5();
      this.invoke7();
      AiRotationTrainer.invoke10();
      RandomAimStrategy.invoke3();
      AttackRotationController.invoke4();
      if (this.enabled) {
         NoiseAimStrategy.invoke3();
      } else {
         NoiseAimStrategy.invoke2();
      }

      MultipointAimStrategy.invoke7();
      livingEntity = null;
      InputUtils.getINSTANCE().invoke2("Aura");
      if (CLIENT.player != null) {
         flag9 = false;
         timestamp8 = 0L;
      }

      flag2 = false;
      floatValue = 0.0F;
      timestamp = 0L;
      floatValue2 = 0.0F;
      timestamp2 = 0L;
      timestamp3 = 0L;
      timestamp4 = 0L;
      intValue = Integer.MIN_VALUE;
   }

   private boolean check12() {
      return check16()
         ? true
         : CLIENT.player.isUsingItem()
               && proverkiDoUdara.isEnabled("Не бить если кушаешь")
               && !(CLIENT.player.getActiveItem().getItem() instanceof ShieldItem)
            || CLIENT.currentScreen != null && proverkiDoUdara.isEnabled("Не бить в контейнерах ")
            || !CLIENT.player.getMainHandStack().isIn(ItemTags.SWORDS)
               && !CLIENT.player.getMainHandStack().isIn(ItemTags.AXES)
               && CLIENT.player.getMainHandStack().getItem() != Items.MACE
               && proverkiDoUdara.isEnabled("Бить только оружием");
   }

   private int compute(Item item) {
      if (CLIENT.player == null) {
         return -1;
      } else {
         for (int intValue = 0; intValue < 9; intValue++) {
            if (CLIENT.player.getInventory().getStack(intValue).getItem() == item) {
               return intValue;
            }
         }

         return -1;
      }
   }

   private int compute2() {
      if (CLIENT.player == null) {
         return -1;
      } else {
         for (int intValue2 = 0; intValue2 < 9; intValue2++) {
            if (CLIENT.player.getInventory().getStack(intValue2).isIn(ItemTags.SWORDS)) {
               return intValue2;
            }
         }

         return -1;
      }
   }

   @EventHandler
   public void onRawInput(RawInputEvent rawInputEvent) {
      if (this.enabled && bulava.isEnabled() && rezhimBulavy.is("Бинд")) {
         if (knopkaBulavy.getKeyCode() != -1 && rawInputEvent.getKeyCode() == knopkaBulavy.getKeyCode() && rawInputEvent.getAction() == 1) {
            if (CLIENT.currentScreen == null && !check16()) {
               this.invoke14();
            }
         }
      }
   }

   @EventHandler
   public void onMouseButtonPosition(MouseButtonPositionEvent mouseButtonPositionEvent) {
      if (this.enabled && bulava.isEnabled() && rezhimBulavy.is("Бинд")) {
         int intValue3 = -100 - mouseButtonPositionEvent.getButton();
         if (knopkaBulavy.getKeyCode() != -1 && knopkaBulavy.getKeyCode() == intValue3 && mouseButtonPositionEvent.isPress()) {
            if (CLIENT.currentScreen == null && !check16()) {
               this.invoke14();
            }
         }
      }
   }

   private void invoke12() {
      if (CLIENT.player == null || CLIENT.world == null || CLIENT.interactionManager == null) {
         this.invoke25();
      } else if (intValue2 != 0) {
         this.invoke15();
      } else if (bulava.isEnabled() && !check16()) {
         if (CLIENT.currentScreen == null) {
            if (rezhimBulavy.is("Авто")) {
               this.invoke13();
            }
         }
      } else {
         if (flag5) {
            this.check14();
         }
      }
   }

   private void invoke13() {
      LivingEntity livingEntity5 = this.resolve5();
      boolean flag26 = livingEntity5 != null && CLIENT.player.getY() - livingEntity5.getY() >= vysotaBulavy.getValue();
      if (!flag5) {
         if (!flag26) {
            flag8 = false;
         } else {
            if (!flag8 && CLIENT.player.getMainHandStack().getItem() != Items.MACE && this.compute3() != -1) {
               intValue7 = 0;
               this.check13();
            }
         }
      } else {
         intValue7++;
         if (flag7 || intValue7 > 40) {
            flag8 = true;
            this.check14();
         }
      }
   }

   private LivingEntity resolve5() {
      if (CLIENT.player != null && CLIENT.world != null) {
         float floatValue16 = measure((LivingEntity)null) + radiusObnaruzheniya.getValue() + vysotaBulavy.getValue() + 2.0F;
         double doubleValue6 = measure((LivingEntity)null) + radiusObnaruzheniya.getValue() + 1.5;
         double doubleValue7 = doubleValue6 * doubleValue6;
         LivingEntity livingEntity6 = null;
         double doubleValue8 = Double.MAX_VALUE;

         for (Entity entity2 : CLIENT.world.getEntities()) {
            if (entity2 instanceof LivingEntity livingEntity7 && !(CLIENT.player.getY() - livingEntity7.getY() < vysotaBulavy.getValue())) {
               double doubleValue9 = livingEntity7.getX() - CLIENT.player.getX();
               double doubleValue10 = livingEntity7.getZ() - CLIENT.player.getZ();
               double doubleValue11 = doubleValue9 * doubleValue9 + doubleValue10 * doubleValue10;
               if (!(doubleValue11 > doubleValue7) && this.check8(livingEntity7, floatValue16) && doubleValue11 < doubleValue8) {
                  doubleValue8 = doubleValue11;
                  livingEntity6 = livingEntity7;
               }
            }
         }

         return livingEntity6;
      } else {
         return null;
      }
   }

   private void invoke14() {
      if (intValue2 == 0 && CLIENT.player != null) {
         if (flag5) {
            this.check14();
         } else {
            this.check13();
         }
      }
   }

   private boolean check13() {
      if (!flag5 && intValue2 == 0 && CLIENT.player != null) {
         if (rezhimBulavy.is("Авто") && System.currentTimeMillis() < timestamp5) {
            return false;
         } else if (CLIENT.player.getMainHandStack().getItem() == Items.MACE) {
            return false;
         } else {
            int intValue4 = this.compute3();
            if (intValue4 == -1) {
               return false;
            } else {
               int intValue5 = CLIENT.player.getInventory().getSelectedSlot();
               if (intValue4 == intValue5) {
                  return false;
               } else {
                  this.intValue5 = intValue5;
                  if (intValue4 < 9) {
                     flag6 = true;
                     intValue6 = intValue4;
                     intValue4 = -1;
                  } else {
                     flag6 = false;
                     intValue6 = -1;
                     this.intValue4 = intValue4;
                  }

                  this.invoke22(flag6 ? "свап IN хотбар слот=" + intValue4 : "свап IN инвентарь слот=" + intValue4);
                  intValue2 = 1;
                  intValue3 = 0;
                  this.invoke20();
                  return true;
               }
            }
         }
      } else {
         return false;
      }
   }

   private boolean check14() {
      if (flag5 && intValue2 == 0) {
         this.invoke22("свап OUT старт");
         intValue2 = 11;
         intValue3 = 0;
         this.invoke20();
         return true;
      } else {
         return false;
      }
   }

   private void invoke15() {
      InputUtils.getINSTANCE().invoke("AuraMace");
      this.invoke21();
      if (intValue3 > 0) {
         intValue3--;
      } else {
         switch (intValue2) {
            case 1:
               intValue2 = 2;
               intValue3 = 0;
               break;
            case 2:
               this.invoke17();
               flag5 = true;
               intValue2 = 3;
               intValue3 = 1;
               break;
            case 3:
               this.invoke19();
               break;
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            default:
               this.invoke19();
               break;
            case 11:
               intValue2 = 12;
               intValue3 = 0;
               break;
            case 12:
               this.invoke18();
               intValue8 = 4;
               intValue2 = 13;
               intValue3 = 1;
               break;
            case 13:
               if (this.check15()) {
                  this.invoke16();
               } else if (intValue8 > 0) {
                  intValue8--;
                  this.invoke18();
                  intValue3 = 1;
               } else {
                  this.invoke16();
               }
         }
      }
   }

   private boolean check15() {
      return CLIENT.player == null ? true : CLIENT.player.getMainHandStack().getItem() != Items.MACE;
   }

   private void invoke16() {
      this.invoke22("восстановлено");
      flag5 = false;
      intValue4 = -1;
      intValue5 = -1;
      flag6 = false;
      intValue6 = -1;
      intValue8 = 0;
      flag7 = false;
      timestamp5 = System.currentTimeMillis() + 300L;
      this.invoke19();
   }

   private void invoke17() {
      if (CLIENT.player != null && CLIENT.interactionManager != null) {
         if (flag6) {
            if (intValue6 >= 0 && intValue6 <= 8) {
               CLIENT.player.getInventory().setSelectedSlot(intValue6);
            }
         } else if (intValue4 >= 0 && intValue5 >= 0 && intValue5 <= 8) {
            this.invoke22("clickSlot IN");
            CLIENT.interactionManager
               .clickSlot(CLIENT.player.playerScreenHandler.syncId, intValue4, intValue5, SlotActionType.SWAP, CLIENT.player);
         }
      }
   }

   private void invoke18() {
      if (CLIENT.player != null && CLIENT.interactionManager != null) {
         if (flag6) {
            if (intValue5 >= 0 && intValue5 <= 8) {
               CLIENT.player.getInventory().setSelectedSlot(intValue5);
            }
         } else if (intValue4 >= 0 && intValue5 >= 0 && intValue5 <= 8) {
            this.invoke22("clickSlot OUT");
            CLIENT.interactionManager
               .clickSlot(CLIENT.player.playerScreenHandler.syncId, intValue4, intValue5, SlotActionType.SWAP, CLIENT.player);
         }
      }
   }

   private void invoke19() {
      intValue2 = 0;
      intValue3 = 0;
      InputUtils.getINSTANCE().invoke2("AuraMace");
   }

   private void invoke20() {
      AttackUtils.invoke4();
      InputUtils.getINSTANCE().invoke("AuraMace");
      this.invoke21();
   }

   private void invoke21() {
      Sprint.intValue = 2;
      if (CLIENT.options != null) {
         CLIENT.options.sprintKey.setPressed(false);
      }

      if (CLIENT.player != null) {
         CLIENT.player.setSprinting(false);
      }
   }

   private void invoke22(String string) {
      if (otladkaBulavy.isEnabled()) {
         int intValue6 = 0;
         int intValue7 = 0;
         if (CLIENT.player != null && CLIENT.player.input != null) {
            intValue6 = (CLIENT.player.input.playerInput.forward() ? 1 : 0) - (CLIENT.player.input.playerInput.backward() ? 1 : 0);
            intValue7 = (CLIENT.player.input.playerInput.left() ? 1 : 0) - (CLIENT.player.input.playerInput.right() ? 1 : 0);
         }

         ChatUtil.sendClientMessage("[Булава] " + string + " (fwd=" + intValue6 + " str=" + intValue7 + ")");
      }
   }

   private void invoke23() {
      if (bulava.isEnabled() && rezhimBulavy.is("Авто") && flag5 && CLIENT.player != null) {
         if (CLIENT.player.getMainHandStack().getItem() == Items.MACE) {
            flag7 = true;
            flag8 = true;
         }
      }
   }

   private void invoke24() {
      if (CLIENT.player != null && CLIENT.interactionManager != null) {
         if (flag5) {
            this.invoke18();
         }

         this.invoke25();
      } else {
         this.invoke25();
      }
   }

   private void invoke25() {
      if (intValue2 != 0) {
         InputUtils.getINSTANCE().invoke2("AuraMace");
      }

      intValue2 = 0;
      intValue3 = 0;
      flag5 = false;
      intValue4 = -1;
      intValue5 = -1;
      flag6 = false;
      intValue6 = -1;
      flag7 = false;
      flag8 = false;
      intValue7 = 0;
      intValue8 = 0;
      timestamp5 = 0L;
   }

   private int compute3() {
      if (CLIENT.player == null) {
         return -1;
      } else {
         for (int intValue8 = 0; intValue8 < 36; intValue8++) {
            if (CLIENT.player.getInventory().getStack(intValue8).getItem() == Items.MACE) {
               return intValue8;
            }
         }

         return -1;
      }
   }

   public static boolean check16() {
      return ServerHelper.flag || ClickPearl.flag2 || AutoSwap.flag5 || AutoTotem.flag3;
   }

   private void invoke26() {
      if (!check17()) {
         floatValue2 = 0.0F;
         timestamp2 = 0L;
      } else {
         long longValue3 = System.currentTimeMillis();
         if (longValue3 >= timestamp2 || floatValue2 <= 0.0F) {
            floatValue2 = ThreadLocalRandom.current().nextFloat(0.08F, 0.32F);
            timestamp2 = longValue3 + ThreadLocalRandom.current().nextLong(55L, 130L);
         }

         CLIENT.player.fallDistance = floatValue2;
      }
   }

   private static boolean check17() {
      return CLIENT.player != null && CLIENT.world != null && CLIENT.player.isOnGround() && check18();
   }

   public static boolean check18() {
      if (CLIENT.player != null && CLIENT.world != null) {
         BlockPos blockPos2 = BlockPos.ofFloored(CLIENT.player.getX(), CLIENT.player.getBoundingBox().minY - 0.05, CLIENT.player.getZ());
         BlockPos blockPos3 = BlockPos.ofFloored(CLIENT.player.getX(), CLIENT.player.getBoundingBox().maxY + 0.2, CLIENT.player.getZ());
         return check19(blockPos2) && check19(blockPos3);
      } else {
         return false;
      }
   }

   private static boolean check19(BlockPos blockPos) {
      BlockState blockState = CLIENT.world.getBlockState(blockPos);
      return !blockState.getCollisionShape(CLIENT.world, blockPos).isEmpty();
   }

   private static long compute4() {
      return compute5(0L);
   }

   private static long compute5(long l) {
      if (dopolnitelnyeNastroyki.isEnabled("Умные криты") && CLIENT.player != null && livingEntity != null) {
         long longValue4 = System.currentTimeMillis();
         int intValue9 = livingEntity.getId();
         if (intValue9 != intValue || longValue4 >= timestamp3 || AttackUtils.measure5() < 75.0F) {
            intValue = intValue9;
            timestamp4 = compute6();
            timestamp3 = longValue4 + ThreadLocalRandom.current().nextLong(95L, 180L);
         }

         return l + timestamp4;
      } else {
         return l;
      }
   }

   private static long compute6() {
      long longValue5 = -35L;
      long longValue6 = 28L;
      boolean flag27 = CLIENT.player.fallDistance > 0.0 || CLIENT.player.getVelocity().y < -0.0784;
      if (flag27) {
         longValue5 -= 18L;
         longValue6 -= 8L;
      }

      if (check17()) {
         longValue5 -= 22L;
         longValue6 -= 6L;
      } else if (CLIENT.player.isOnGround()) {
         longValue5 += 8L;
         longValue6 += 18L;
      }

      if (livingEntity.hurtTime > 0) {
         longValue5 = Math.max(longValue5, 4L);
         longValue6 += 34L;
      }

      double doubleValue12 = measure(livingEntity) - RaycastUtils.measure((Entity)livingEntity);
      if (doubleValue12 < 0.35F) {
         longValue5 += 10L;
         longValue6 += 22L;
      } else if (doubleValue12 > 1.0) {
         longValue5 -= 8L;
      }

      if (longValue6 < longValue5) {
         longValue6 = longValue5;
      }

      return ThreadLocalRandom.current().nextLong(longValue5, longValue6 + 1L);
   }

   @Override
   public void onDisable() {
      this.invoke24();
      this.invoke29();
      this.invoke30();
      AttackUtils.invoke4();
      this.invoke7();
      RandomAimStrategy.invoke3();
      NoiseAimStrategy.invoke2();
      CriticalHitController.invoke2();
      AttackRotationController.invoke4();
      super.onDisable();
   }

   private static void invoke27() {
      if (CLIENT != null) {
         CLIENT.execute(() -> CLIENT.setScreen(new RotationBuilderScreen()));
      }
   }

   private static void invoke28() {
      if (CLIENT != null) {
         CLIENT.execute(() -> CLIENT.setScreen(new AiLabScreen()));
      }
   }

   private void invoke29() {
      if (rezhimRotatsii.is("Lony Grief")) {
         HumanizedRotationPattern.invoke2();
      }

      if (rezhimRotatsii.is("Side Point")) {
         BoundingBoxAimStrategy.invoke2();
      }
   }

   private void invoke30() {
      if (rezhimRotatsii.is("Neuro")) {
         NeuroRotationController.invoke4(neuroClientFinish.isEnabled());
      }
   }

   @ModuleAccess(
      usernames = {"lichoday", "bitrixtime", "oblamovvv"}
   )
   static final class AttackAuraState {
      private AttackAuraState() {
      }
   }
}
