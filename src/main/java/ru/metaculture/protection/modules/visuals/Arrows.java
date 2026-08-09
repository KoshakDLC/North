package ru.metaculture.protection;

import java.util.ArrayList;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "Arrows",
   description = "Показывает игроков через стрелочки",
   category = Category.Visuals
)
public class Arrows extends Module {
   private static final Identifier IDENTIFIER = Identifier.of("wild", "textures/arrows/arrows.png");
   public static final BooleanSetting POKAZ_DISTANTSII = new BooleanSetting("Показ дистанции", true);
   public static final BooleanSetting POKAZ_IGROKOV_SBRONYOY = new BooleanSetting("Показ игроков с бронёй", true);
   public static final BooleanSetting VYDELYAT_TARGETA = new BooleanSetting("Выделять таргета", true);
   public static final BooleanSetting ANIMIROVAT = new BooleanSetting("Анимировать", true);
   public static final NumberSetting RAZMER = new NumberSetting("Размер", 10.0F, 1.0F, 100.0F, 1.0F, false);
   public static final NumberSetting DISTANTSIYA_OT_TSENTRA = new NumberSetting("Дистанция от центра", 150.0F, 80.0F, 300.0F, 5.0F, false);
   public static final BooleanSetting SORTIROVKA_PO_DISTANTSII = new BooleanSetting("Сортировка по дистанции", false);
   public static final BooleanSetting MERTSAT = new BooleanSetting("Мерцать", true).visibleWhen(() -> !POKAZ_DISTANTSII.isEnabled());
   public static LivingEntity livingEntity;
   public ArrayList<Arrows.ArrowsEntityState> arrayList = new ArrayList<>();
   public ArrayList<Arrows.ArrowsState> arrayList2 = new ArrayList<>();

   public Arrows() {
      this.addSettings(new Setting[]{POKAZ_DISTANTSII, POKAZ_IGROKOV_SBRONYOY, VYDELYAT_TARGETA, ANIMIROVAT, RAZMER, DISTANTSIYA_OT_TSENTRA, SORTIROVKA_PO_DISTANTSII, MERTSAT});
   }

   @EventHandler
   public void onHudRender(HudRenderEvent hudRenderEvent) {
      if (CLIENT.player != null && CLIENT.world != null) {
         if (AttackAura.livingEntity != null) {
            livingEntity = AttackAura.livingEntity;
         }

         if (livingEntity != null && (!livingEntity.isAlive() || !CLIENT.world.getPlayers().contains(livingEntity))) {
            livingEntity = null;
         }

         if (CLIENT.world.getPlayers() != null) {
            for (Entity entity2 : CLIENT.world.getPlayers()) {
               if (entity2 != null && entity2 != CLIENT.player) {
                  boolean flag = false;

                  for (Arrows.ArrowsEntityState arrowsEntityState : this.arrayList) {
                     if (arrowsEntityState.entity == entity2) {
                        flag = true;
                        break;
                     }
                  }

                  if (!flag) {
                     this.arrayList.add(new Arrows.ArrowsEntityState(entity2));
                  }
               }
            }
         }

         for (Arrows.ArrowsEntityState arrowsEntityState2 : this.arrayList) {
            arrowsEntityState2.invoke2(hudRenderEvent.getRenderManager());
         }

         this.arrayList
            .removeIf(arrowsEntityState3 -> arrowsEntityState3.directionalAnimation.getAnimationDirection() != AnimationDirection.FORWARDS && arrowsEntityState3.directionalAnimation.measure3() == 0.0F);
         WildFriends wildFriends = WildClient.INSTANCE.moduleManager != null ? WildClient.INSTANCE.moduleManager.getModule(WildFriends.class) : null;
         if (VYDELYAT_TARGETA.isEnabled() && wildFriends != null && wildFriends.fokusTseli.isEnabled() && wildFriends.enabled) {
            String text = IrcWebSocketClient.resolve();
            ArrayList arrayList = new ArrayList();

            for (IrcWebSocketClient.IrcWebSocketClientState2 ircWebSocketClientState2 : IrcWebSocketClient.VALUES_BY_KEY_4.values()) {
               if (ircWebSocketClientState2.text2.equals(text)) {
                  arrayList.add(ircWebSocketClientState2.text);
               }
            }

            if (WildFriends.text != null && !WildFriends.text.isEmpty() && !arrayList.contains(WildFriends.text)) {
               arrayList.add(WildFriends.text);
            }

            for (String text2 : (ArrayList<String>)arrayList) {
               boolean flag2 = CLIENT.world
                  .getPlayers()
                  .stream()
                  .anyMatch(
                     abstractClientPlayerEntity -> abstractClientPlayerEntity.getName().getString().equalsIgnoreCase(text2)
                        && abstractClientPlayerEntity != CLIENT.player
                  );
               if (!flag2) {
                  boolean flag3 = false;

                  for (Arrows.ArrowsState arrowsState : this.arrayList2) {
                     if (arrowsState.text.equalsIgnoreCase(text2)) {
                        flag3 = true;
                        break;
                     }
                  }

                  if (!flag3) {
                     this.arrayList2.add(new Arrows.ArrowsState(text2));
                  }
               }
            }
         }

         for (Arrows.ArrowsState arrowsState2 : this.arrayList2) {
            arrowsState2.invoke(hudRenderEvent.getRenderManager());
         }

         this.arrayList2
            .removeIf(arrowsState3 -> arrowsState3.directionalAnimation.getAnimationDirection() != AnimationDirection.FORWARDS && arrowsState3.directionalAnimation.measure3() == 0.0F);
      }
   }

   static int compute() {
      if (CLIENT != null && CLIENT.getTextureManager() != null) {
         AbstractTexture abstractTexture = CLIENT.getTextureManager().getTexture(IDENTIFIER);
         if (abstractTexture == null) {
            return -1;
         } else if (abstractTexture.getGlTexture() instanceof GlTexture glTexture) {
            int intValue = glTexture.getGlId();
            return intValue > 0 ? intValue : -1;
         } else {
            return -1;
         }
      } else {
         return -1;
      }
   }

   static int compute2(int i, int j) {
      return ColorUtils.compute29(i | 0xFF000000, j);
   }

   static float measure(String string) {
      return -RenderManager.resolve7(FontRegistry.fontObject4, string, 20.0F).floatValue / 2.0F;
   }

   static double measure2(double d) {
      return !SORTIROVKA_PO_DISTANTSII.isEnabled() ? 0.0 : Math.min(DISTANTSIYA_OT_TSENTRA.value * 0.85, Math.max(0.0, d * 0.65));
   }

   public static class ArrowsEntityState {
      DirectionalAnimation directionalAnimation = new EaseInOutQuadAnimation(300, 1.0);
      Entity entity;
      float floatValue;
      float floatValue2;
      float floatValue3;
      float floatValue4;

      public ArrowsEntityState(Entity entity) {
         this.entity = entity;
      }

      public void invoke() {
         if (Module.CLIENT.world != null && Module.CLIENT.player != null) {
            boolean flag4 = Module.CLIENT.world.getPlayers().contains(this.entity);
            boolean flag5 = this.entity.isAlive();
            boolean flag6 = this.entity == Module.CLIENT.player;
            boolean flag7 = flag4 && flag5 && !flag6;
            if (flag7 && Arrows.POKAZ_IGROKOV_SBRONYOY.isEnabled() && this.entity instanceof PlayerEntity playerEntity) {
               boolean flag8 = false;
               EquipmentSlot[] equipmentSlots = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET};

               for (EquipmentSlot equipmentSlot : equipmentSlots) {
                  ItemStack itemStack = playerEntity.getEquippedStack(equipmentSlot);
                  if (itemStack != null && !itemStack.isEmpty()) {
                     String text3 = itemStack.getItem().toString().toUpperCase();
                     if (text3.contains("DIAMOND") || text3.contains("NETHERITE")) {
                        flag8 = true;
                        break;
                     }
                  }
               }

               if (!flag8) {
                  flag7 = false;
               }
            }

            this.directionalAnimation.invoke3(flag7 ? AnimationDirection.FORWARDS : AnimationDirection.BACKWARDS);
         }
      }

      public void invoke2(RenderManager renderManager) {
         this.invoke();
         ProjectionUtils projectionUtils = new ProjectionUtils(Module.CLIENT);
         float[] floatValues = MovementUtils.resolve3();
         float floatValue = floatValues[0];
         float floatValue2 = floatValues[1];
         if (Arrows.ANIMIROVAT.isEnabled()) {
            this.floatValue2 = RenderMath.measure54(this.floatValue2, floatValue2 * 10.0F, 5.0F);
            this.floatValue3 = RenderMath.measure54(this.floatValue3, floatValue * 10.0F, 5.0F);
         } else {
            this.floatValue2 = 0.0F;
            this.floatValue3 = 0.0F;
         }

         float floatValue3 = FreeLookController.active ? Module.CLIENT.gameRenderer.getCamera().getYaw() : FreeLookController.floatValue;
         this.floatValue4 = RenderMath.measure54(this.floatValue4, floatValue3, 10.0F);
         boolean flag9 = Arrows.VYDELYAT_TARGETA.isEnabled() && Arrows.livingEntity != null && this.entity.equals(Arrows.livingEntity);
         if (!flag9 && Arrows.VYDELYAT_TARGETA.isEnabled()) {
            WildFriends wildFriends2 = WildClient.INSTANCE.moduleManager != null ? WildClient.INSTANCE.moduleManager.getModule(WildFriends.class) : null;
            if (wildFriends2 != null && wildFriends2.fokusTseli.isEnabled() && wildFriends2.enabled) {
               String text4 = this.entity.getName().getString();
               String text5 = IrcWebSocketClient.resolve();

               for (IrcWebSocketClient.IrcWebSocketClientState2 ircWebSocketClientState22 : IrcWebSocketClient.VALUES_BY_KEY_4.values()) {
                  if (ircWebSocketClientState22.text2.equals(text5) && ircWebSocketClientState22.text.equals(text4)) {
                     flag9 = true;
                     break;
                  }
               }

               if (WildFriends.text != null && WildFriends.text.equals(text4)) {
                  flag9 = true;
               }
            }
         }

         float floatValue4 = flag9 ? 1.5F : 1.0F;
         float floatValue5 = this.directionalAnimation.measure3() * (Arrows.DISTANTSIYA_OT_TSENTRA.value * floatValue4);
         if (Module.CLIENT.currentScreen instanceof GenericContainerScreen) {
            floatValue5 += 200.0F;
         }

         if (Module.CLIENT.currentScreen instanceof InventoryScreen) {
            floatValue5 += 180.0F;
         }

         if (Arrows.ANIMIROVAT.isEnabled() && (check() || Module.CLIENT.player.isInSneakingPose() || Module.CLIENT.player.isSwimming())
            || Module.CLIENT.currentScreen instanceof ChatScreen) {
            floatValue5 += 90.0F;
         }

         this.floatValue = Arrows.ANIMIROVAT.isEnabled() ? RenderMath.measure54(this.floatValue, floatValue5, 6.0F) : floatValue5;
         double doubleValue = this.entity.lastX
            + (this.entity.getX() - this.entity.lastX) * Module.CLIENT.gameRenderer.getCamera().getLastTickProgress()
            - Module.CLIENT.gameRenderer.getCamera().getPos().x;
         double doubleValue2 = this.entity.lastY
            + (this.entity.getY() - this.entity.lastY) * Module.CLIENT.gameRenderer.getCamera().getLastTickProgress()
            + this.entity.getHeight() / 2.0F
            - Module.CLIENT.gameRenderer.getCamera().getPos().y
            - Module.CLIENT.player.getEyeHeight(Module.CLIENT.player.getPose());
         double doubleValue3 = this.entity.lastZ
            + (this.entity.getZ() - this.entity.lastZ) * Module.CLIENT.gameRenderer.getCamera().getLastTickProgress()
            - Module.CLIENT.gameRenderer.getCamera().getPos().z;
         double doubleValue4 = Math.sqrt(doubleValue * doubleValue + doubleValue2 * doubleValue2 + doubleValue3 * doubleValue3);
         double doubleValue5 = MathHelper.cos((float)(this.floatValue4 * (Math.PI / 180.0)));
         double doubleValue6 = MathHelper.sin((float)(this.floatValue4 * (Math.PI / 180.0)));
         double doubleValue7 = -(doubleValue3 * doubleValue5 - doubleValue * doubleValue6);
         double doubleValue8 = -(doubleValue * doubleValue5 + doubleValue3 * doubleValue6);
         double doubleValue9 = Math.atan2(doubleValue7, doubleValue8) * 180.0 / Math.PI;
         double doubleValue10 = this.floatValue + Arrows.measure2(doubleValue4) * this.directionalAnimation.measure3();
         double doubleValue11 = Math.min(1.0, doubleValue4 / 20.0);
         double doubleValue12 = doubleValue10 * MathHelper.cos((float)Math.toRadians(doubleValue9)) + projectionUtils.getDoubleValue();
         double doubleValue13 = doubleValue10 * MathHelper.sin((float)Math.toRadians(doubleValue9)) + projectionUtils.getDoubleValue2();
         doubleValue12 += this.floatValue2;
         doubleValue13 += this.floatValue3 + doubleValue11;
         int intValue2 = Arrows.compute();
         if (intValue2 > 0) {
            int intValue3;
            if (flag9) {
               intValue3 = ColorUtils.INT_VALUE;
            } else if (this.entity instanceof AbstractClientPlayerEntity abstractClientPlayerEntity2 && FriendCommand.check(abstractClientPlayerEntity2.getNameForScoreboard())) {
               intValue3 = ColorUtils.INT_VALUE_2;
            } else {
               intValue3 = ColorUtils.compute41();
            }

            int intValue4 = (int)(this.directionalAnimation.measure3() * 255.0F);
            if (Arrows.POKAZ_DISTANTSII.isEnabled() && Arrows.MERTSAT.isEnabled() && doubleValue4 > 50.0) {
               long longValue = System.currentTimeMillis() % 5000L;
               if (longValue > 2500L) {
                  intValue4 = 0;
               }
            }

            if (intValue4 > 5) {
               renderManager.invoke56((float)doubleValue12, (float)doubleValue13);
               renderManager.invoke54((float)(doubleValue9 + 90.0));
               float floatValue6 = Arrows.RAZMER.value * 2.0F;
               renderManager.invoke9(intValue2, -floatValue6 / 2.0F, -floatValue6 / 2.0F, floatValue6, floatValue6, Arrows.compute2(intValue3, intValue4), false);
               renderManager.invoke57();
               if (Arrows.POKAZ_DISTANTSII.isEnabled()) {
                  String text6;
                  if (doubleValue4 > 100.0) {
                     text6 = "100+";
                  } else {
                     text6 = (int)doubleValue4 + "m";
                  }

                  float floatValue7 = Arrows.measure(text6);
                  float floatValue8 = Arrows.RAZMER.value + 8.0F;
                  renderManager.invoke69(FontRegistry.fontObject4, floatValue7, floatValue8, 20.0F, text6, ColorUtils.compute43(255, 255, 255, intValue4));
               }

               renderManager.invoke57();
            }
         }
      }

      public static boolean check() {
         float[] floatValues2 = MovementUtils.resolve3();
         return floatValues2[0] != 0.0F || floatValues2[1] != 0.0F;
      }
   }

   public static class ArrowsState {
      DirectionalAnimation directionalAnimation = new EaseInOutQuadAnimation(300, 1.0);
      String text;
      float floatValue;
      float floatValue2;
      float floatValue3;
      float floatValue4;

      public ArrowsState(String string) {
         this.text = string;
      }

      public void invoke(RenderManager renderManager2) {
         String text7 = IrcWebSocketClient.resolve();
         double doubleValue14 = 0.0;
         double doubleValue15 = 0.0;
         double doubleValue16 = 0.0;
         boolean flag10 = false;
         long longValue2 = 0L;

         for (IrcWebSocketClient.IrcWebSocketClientState2 ircWebSocketClientState23 : IrcWebSocketClient.VALUES_BY_KEY_4.values()) {
            if (ircWebSocketClientState23.text.equalsIgnoreCase(this.text) && ircWebSocketClientState23.text2.equals(text7)) {
               long longValue3 = System.currentTimeMillis() - ircWebSocketClientState23.timestamp;
               double doubleValue17 = MathHelper.clamp(longValue3 / 200.0, 0.0, 1.0);
               doubleValue14 = MathHelper.lerp(doubleValue17, ircWebSocketClientState23.doubleValue4, ircWebSocketClientState23.doubleValue);
               doubleValue15 = MathHelper.lerp(doubleValue17, ircWebSocketClientState23.doubleValue5, ircWebSocketClientState23.doubleValue2);
               doubleValue16 = MathHelper.lerp(doubleValue17, ircWebSocketClientState23.doubleValue6, ircWebSocketClientState23.doubleValue3);
               longValue2 = ircWebSocketClientState23.timestamp;
               flag10 = true;
               break;
            }
         }

         if (!flag10 && this.text.equalsIgnoreCase(WildFriends.text)) {
            doubleValue14 = WildFriends.doubleValue;
            doubleValue15 = WildFriends.doubleValue2;
            doubleValue16 = WildFriends.doubleValue3;
            longValue2 = System.currentTimeMillis();
            flag10 = true;
         }

         long longValue4 = System.currentTimeMillis() - longValue2;
         boolean flag11 = flag10 && (longValue4 < 4000L || this.text.equalsIgnoreCase(WildFriends.text));
         this.directionalAnimation.invoke3(flag11 ? AnimationDirection.FORWARDS : AnimationDirection.BACKWARDS);
         if (this.directionalAnimation.measure3() != 0.0F) {
            ProjectionUtils projectionUtils2 = new ProjectionUtils(Module.CLIENT);
            float[] floatValues3 = MovementUtils.resolve3();
            float floatValue9 = floatValues3[0];
            float floatValue10 = floatValues3[1];
            if (Arrows.ANIMIROVAT.isEnabled()) {
               this.floatValue2 = RenderMath.measure54(this.floatValue2, floatValue10 * 10.0F, 5.0F);
               this.floatValue3 = RenderMath.measure54(this.floatValue3, floatValue9 * 10.0F, 5.0F);
            } else {
               this.floatValue2 = 0.0F;
               this.floatValue3 = 0.0F;
            }

            float floatValue11 = FreeLookController.active ? Module.CLIENT.gameRenderer.getCamera().getYaw() : FreeLookController.floatValue;
            this.floatValue4 = RenderMath.measure54(this.floatValue4, floatValue11, 10.0F);
            float floatValue12 = 1.5F;
            float floatValue13 = this.directionalAnimation.measure3() * (Arrows.DISTANTSIYA_OT_TSENTRA.value * floatValue12);
            if (Module.CLIENT.currentScreen instanceof GenericContainerScreen) {
               floatValue13 += 200.0F;
            }

            if (Module.CLIENT.currentScreen instanceof InventoryScreen) {
               floatValue13 += 180.0F;
            }

            if (Arrows.ANIMIROVAT.isEnabled()
                  && (Arrows.ArrowsEntityState.check() || Module.CLIENT.player.isInSneakingPose() || Module.CLIENT.player.isSwimming())
               || Module.CLIENT.currentScreen instanceof ChatScreen) {
               floatValue13 += 90.0F;
            }

            this.floatValue = Arrows.ANIMIROVAT.isEnabled() ? RenderMath.measure54(this.floatValue, floatValue13, 6.0F) : floatValue13;
            double doubleValue18 = doubleValue14 - Module.CLIENT.gameRenderer.getCamera().getPos().x;
            double doubleValue19 = doubleValue15
               + 1.0
               - Module.CLIENT.gameRenderer.getCamera().getPos().y
               - Module.CLIENT.player.getEyeHeight(Module.CLIENT.player.getPose());
            double doubleValue20 = doubleValue16 - Module.CLIENT.gameRenderer.getCamera().getPos().z;
            double doubleValue21 = Math.sqrt(doubleValue18 * doubleValue18 + doubleValue19 * doubleValue19 + doubleValue20 * doubleValue20);
            double doubleValue22 = MathHelper.cos((float)(this.floatValue4 * (Math.PI / 180.0)));
            double doubleValue23 = MathHelper.sin((float)(this.floatValue4 * (Math.PI / 180.0)));
            double doubleValue24 = -(doubleValue20 * doubleValue22 - doubleValue18 * doubleValue23);
            double doubleValue25 = -(doubleValue18 * doubleValue22 + doubleValue20 * doubleValue23);
            double doubleValue26 = Math.atan2(doubleValue24, doubleValue25) * 180.0 / Math.PI;
            double doubleValue27 = this.floatValue + Arrows.measure2(doubleValue21) * this.directionalAnimation.measure3();
            double doubleValue28 = Math.min(1.0, doubleValue21 / 20.0);
            double doubleValue29 = doubleValue27 * MathHelper.cos((float)Math.toRadians(doubleValue26)) + projectionUtils2.getDoubleValue();
            double doubleValue30 = doubleValue27 * MathHelper.sin((float)Math.toRadians(doubleValue26)) + projectionUtils2.getDoubleValue2();
            doubleValue29 += this.floatValue2;
            doubleValue30 += this.floatValue3 + doubleValue28;
            int intValue5 = Arrows.compute();
            if (intValue5 > 0) {
               int intValue6 = ColorUtils.INT_VALUE;
               int intValue7 = (int)(this.directionalAnimation.measure3() * 255.0F);
               if (longValue4 > 3000L && !this.text.equalsIgnoreCase(WildFriends.text)) {
                  float floatValue14 = 1.0F - (float)(longValue4 - 3000L) / 1000.0F;
                  intValue7 = (int)(intValue7 * MathHelper.clamp(floatValue14, 0.0F, 1.0F));
               }

               if (Arrows.POKAZ_DISTANTSII.isEnabled() && Arrows.MERTSAT.isEnabled() && doubleValue21 > 50.0) {
                  long longValue5 = System.currentTimeMillis() % 5000L;
                  if (longValue5 > 2500L) {
                     intValue7 = 0;
                  }
               }

               if (intValue7 > 5) {
                  renderManager2.invoke56((float)doubleValue29, (float)doubleValue30);
                  renderManager2.invoke54((float)(doubleValue26 + 90.0));
                  float floatValue15 = Arrows.RAZMER.value * 2.0F;
                  renderManager2.invoke9(intValue5, -floatValue15 / 2.0F, -floatValue15 / 2.0F, floatValue15, floatValue15, Arrows.compute2(intValue6, intValue7), false);
                  renderManager2.invoke57();
                  if (Arrows.POKAZ_DISTANTSII.isEnabled()) {
                     String text8 = doubleValue21 > 300.0 ? "300+" : (int)doubleValue21 + "m";
                     float floatValue16 = Arrows.measure(text8);
                     float floatValue17 = Arrows.RAZMER.value + 8.0F;
                     renderManager2.invoke69(FontRegistry.fontObject4, floatValue16, floatValue17, 20.0F, text8, ColorUtils.compute43(255, 255, 255, intValue7));
                  }

                  renderManager2.invoke57();
               }
            }
         }
      }
   }
}
