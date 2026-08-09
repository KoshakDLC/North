package ru.metaculture.protection;

import com.mojang.blaze3d.opengl.GlStateManager;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.ReadableScoreboardScore;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;

final class NameTagRenderer {
   private static final MinecraftClient CLIENT = MinecraftClient.getInstance();
   static final SpringSpec SPRING_SPEC = new SpringSpec(0.014F, 0.74F, 0.001F, 0.001F);
   static final SpringSpec SPRING_SPEC_2 = new SpringSpec(0.012F, 0.8F, 0.001F, 0.001F);
   static final SpringSpec SPRING_SPEC_3 = new SpringSpec(0.082F, 0.56F, 0.001F, 0.001F);
   static final SpringSpec SPRING_SPEC_4 = new SpringSpec(0.066F, 0.7F, 0.001F, 0.001F);
   private static final SpringSpec SPRING_SPEC_5 = new SpringSpec(0.072F, 0.62F, 0.001F, 0.001F);
   private static final SpringSpec SPRING_SPEC_6 = new SpringSpec(0.096F, 0.78F, 0.001F, 0.001F);
   private static final SpringSpec SPRING_SPEC_7 = new SpringSpec(0.028F, 0.92F, 0.001F, 0.001F);
   private static final SpringSpec SPRING_SPEC_8 = new SpringSpec(0.11F, 0.7F, 0.001F, 0.001F);
   private static final SpringSpec SPRING_SPEC_9 = new SpringSpec(0.062F, 0.76F, 0.001F, 0.001F);
   private static final SpringSpec SPRING_SPEC_10 = new SpringSpec(0.058F, 0.78F, 0.001F, 0.001F);
   private static final int CLIENT_MARK_COLOR = 10651391;
   private static final float FLOAT_VALUE = 96.0F;
   private static final float FLOAT_VALUE_2 = 0.85F;
   private static final float FLOAT_VALUE_3 = 1.35F;
   private static final float FLOAT_VALUE_4 = 11.5F;
   private static final float FLOAT_VALUE_5 = 29.0F;
   private static final float FLOAT_VALUE_6 = 7.0F;
   private static final float FLOAT_VALUE_7 = 8.0F;
   private static final float FLOAT_VALUE_8 = 5.5F;
   private static final float FLOAT_VALUE_9 = 11.0F;
   private static final float FLOAT_VALUE_10 = 22.0F;
   private static final float FLOAT_VALUE_11 = 8.0F;
   private static final float FLOAT_VALUE_12 = 10.0F;
   private static final float FLOAT_VALUE_13 = 16.0F;
   private static final float FLOAT_VALUE_14 = 7.0F;
   private static final float FLOAT_VALUE_15 = 14.8F;
   private static final float FLOAT_VALUE_16 = 11.2F;
   private static final float FLOAT_VALUE_17 = 12.8F;
   private static final float FLOAT_VALUE_18 = 9.4F;
   private static final float FLOAT_VALUE_19 = 3.6F;
   private static final float FLOAT_VALUE_20 = 5.0F;
   private static final float FLOAT_VALUE_21 = 16.0F;
   private static final float FLOAT_VALUE_22 = 4.0F;
   private static final float FLOAT_VALUE_23 = 6.0F;
   private static final float FLOAT_VALUE_24 = 22.0F;
   private static final float FLOAT_VALUE_25 = 7.2F;
   private static final float FLOAT_VALUE_26 = 92.0F;
   private static final float FLOAT_VALUE_27 = 340.0F;
   private static final float FLOAT_VALUE_28 = 4.0F;
   private static final float FLOAT_VALUE_29 = 1.35F;
   private static final float FLOAT_VALUE_30 = 14.0F;
   private static final float FLOAT_VALUE_31 = 0.085F;
   private static final float FLOAT_VALUE_32 = 4.0F;
   private static final float FLOAT_VALUE_33 = 1.32F;
   private static final long TIMESTAMP = 480L;
   private final Map<UUID, NameTagRenderer.NameTagRendererState2> valuesByKey = new HashMap<>();

   void invoke() {
      this.valuesByKey.clear();
   }

   void invoke2(HudRenderEvent hudRenderEvent, NameTags nameTags) {
      if (CLIENT.world != null && CLIENT.player != null && !(CLIENT.currentScreen instanceof InventoryScreen)) {
         RenderManager renderManager = hudRenderEvent.getRenderManager();
         NameTagRenderer.NameTagRendererData nameTagRendererData = this.resolve7();
         long longValue = System.currentTimeMillis();
         float floatValue = CLIENT.getRenderTickCounter().getTickProgress(true);
         HashSet hashSet = new HashSet();

         for (PlayerEntity playerEntity2 : CLIENT.world.getPlayers()) {
            if (this.check2(playerEntity2, nameTags)) {
               NameTagRenderer.NameTagRendererData2 nameTagRendererData2 = this.resolve2(playerEntity2, floatValue, hudRenderEvent.getIntValue(), hudRenderEvent.getIntValue2());
               NameTagRenderer.NameTagRendererState2 nameTagRendererState2 = this.valuesByKey.get(playerEntity2.getUuid());
               if (nameTagRendererData2 != null) {
                  if (nameTagRendererState2 == null) {
                     nameTagRendererState2 = this.valuesByKey.computeIfAbsent(playerEntity2.getUuid(), NameTagRenderer.NameTagRendererState2::new);
                  }

                  this.invoke3(nameTagRendererState2, playerEntity2, nameTagRendererData2, nameTags, longValue);
                  hashSet.add(playerEntity2.getUuid());
               } else if (nameTagRendererState2 != null) {
                  this.invoke4(nameTagRendererState2, playerEntity2, nameTags, longValue);
                  hashSet.add(playerEntity2.getUuid());
               }
            }
         }

         for (Entry entry2 : this.valuesByKey.entrySet()) {
            if (!hashSet.contains(entry2.getKey())) {
               ((NameTagRenderer.NameTagRendererState2)entry2.getValue()).invoke();
            }
         }

         ArrayList arrayList = new ArrayList(this.valuesByKey.size());

         for (NameTagRenderer.NameTagRendererState2 nameTagRendererState22 : this.valuesByKey.values()) {
            NameTagRenderer.NameTagRendererBounds nameTagRendererBounds = this.resolve(nameTagRendererState22, nameTags, longValue);
            if (nameTagRendererBounds != null) {
               arrayList.add(nameTagRendererBounds);
            }
         }

         renderManager.invoke48(this.measure13(arrayList.size()));
         arrayList.sort(Comparator.comparingDouble(NameTagRenderer.NameTagRendererBounds::distance).reversed());
         ArrayList arrayList2 = new ArrayList(arrayList.size());

         for (NameTagRenderer.NameTagRendererBounds nameTagRendererBounds2 : (List<NameTagRenderer.NameTagRendererBounds>)arrayList) {
            this.invoke5(renderManager, nameTagRendererData, nameTagRendererBounds2, longValue);
            if (nameTagRendererBounds2.itemReveal > 0.04F && !nameTagRendererBounds2.state.items.isEmpty()) {
               this.invoke10(renderManager, nameTagRendererData, nameTagRendererBounds2);
               arrayList2.add(nameTagRendererBounds2);
            }
         }

         this.invoke11(renderManager, arrayList2);
         this.valuesByKey.entrySet().removeIf(entry -> entry.getValue().check(longValue));
      } else {
         this.invoke();
      }
   }

   private void invoke3(NameTagRenderer.NameTagRendererState2 nameTagRendererState23, PlayerEntity playerEntity, NameTagRenderer.NameTagRendererData2 nameTagRendererData22, NameTags nameTags2, long l) {
      boolean flag = nameTagRendererState23.flag2;
      boolean flag2 = nameTagRendererState23.flag3;
      nameTagRendererState23.flag2 = true;
      nameTagRendererState23.flag3 = true;
      if (!flag || !flag2 && l - nameTagRendererState23.timestamp3 > 480L) {
         nameTagRendererState23.timestamp2 = l;
      }

      nameTagRendererState23.timestamp3 = l;
      if (!this.check(nameTagRendererState23, playerEntity, nameTags2, l)) {
         nameTagRendererState23.flag2 = false;
         nameTagRendererState23.flag3 = false;
      } else {
         nameTagRendererState23.nameTagRendererData2 = nameTagRendererData22;
         nameTagRendererState23.flag4 = this.check3(playerEntity, nameTags2);
         nameTagRendererState23.flag5 = nameTagRendererState23.flag4;
         nameTagRendererState23.flag6 = !nameTagRendererState23.items.isEmpty()
            && (nameTagRendererState23.flag4 || nameTagRendererState23.floatValue <= nameTags2.radiusDetaley.getValue());
         nameTagRendererState23.timestamp = l;
         if (!nameTagRendererState23.flag) {
            nameTagRendererState23.springValue.invoke2(0.0F);
            nameTagRendererState23.springValue2.invoke2(0.0F);
            nameTagRendererState23.springValue3.invoke2(0.0F);
            nameTagRendererState23.springValue4.invoke2(0.0F);
            nameTagRendererState23.springValue5.invoke2(nameTagRendererState23.floatValue3);
            nameTagRendererState23.springValue6.invoke2(nameTagRendererState23.floatValue5);
            nameTagRendererState23.springValue7.invoke2(nameTagRendererState23.floatValue2);
            nameTagRendererState23.springValue8.invoke2(nameTagRendererState23.floatValue2);
            nameTagRendererState23.springValue9.invoke2(0.0F);
            nameTagRendererState23.springValue10.invoke2(0.0F);
            nameTagRendererState23.springValue11.invoke2(0.0F);
            nameTagRendererState23.flag = true;
         }
      }
   }

   private void invoke4(NameTagRenderer.NameTagRendererState2 nameTagRendererState24, PlayerEntity playerEntity, NameTags nameTags3, long l) {
      nameTagRendererState24.flag2 = true;
      nameTagRendererState24.flag3 = false;
      if (!this.check(nameTagRendererState24, playerEntity, nameTags3, l)) {
         nameTagRendererState24.flag2 = false;
      } else {
         nameTagRendererState24.timestamp = l;
         nameTagRendererState24.flag4 = false;
         nameTagRendererState24.flag5 = nameTagRendererState24.flag5 && nameTagRendererState24.springValue3.getFloatValue() > 0.08F;
         nameTagRendererState24.flag6 = nameTagRendererState24.flag6 && nameTagRendererState24.springValue4.getFloatValue() > 0.08F;
      }
   }

   private boolean check(NameTagRenderer.NameTagRendererState2 nameTagRendererState25, PlayerEntity playerEntity, NameTags nameTags4, long l) {
      String text2 = this.resolve10(playerEntity.getGameProfile() != null ? playerEntity.getGameProfile().getName() : playerEntity.getName().getString());
      if (text2.isEmpty()) {
         return false;
      } else {
         nameTagRendererState25.text = ProtectInfo.resolve7(text2);
         nameTagRendererState25.flag7 = FriendCommand.check(text2);
         nameTagRendererState25.clientUser = nameTags4.znachokKlienta.isEnabled() && ClientUserRegistry.isClientUser(text2);
         nameTagRendererState25.text2 = TargetHud.resolve10(playerEntity);
         nameTagRendererState25.intValue3 = TargetHud.compute3(playerEntity, 16734824, 255) & 16777215;
         nameTagRendererState25.floatValue = CLIENT.player == null ? Float.MAX_VALUE : playerEntity.distanceTo(CLIENT.player);
         nameTagRendererState25.items = this.resolve3(playerEntity, nameTags4);
         nameTagRendererState25.items2 = this.resolve4(playerEntity, nameTags4);
         nameTagRendererState25.nameTagRendererDisplayEntry2 = this.resolve5(nameTagRendererState25);
         nameTagRendererState25.nameTagRendererDisplayEntry22 = this.resolve6(playerEntity, nameTagRendererState25);
         float floatValue2 = this.measure2(playerEntity);
         nameTagRendererState25.text20Hp = this.resolve8(floatValue2);
         float floatValue3 = Math.max(1.0F, playerEntity.getMaxHealth());
         float floatValue4 = this.measure22(floatValue2 / floatValue3);
         if (nameTagRendererState25.flag && floatValue4 + 0.004F < nameTagRendererState25.floatValue2) {
            nameTagRendererState25.timestamp4 = l;
         }

         nameTagRendererState25.floatValue2 = floatValue4;
         int intValue = this.compute(nameTagRendererState25);
         if (intValue != nameTagRendererState25.intValue2) {
            this.invoke13(nameTagRendererState25);
            nameTagRendererState25.intValue2 = intValue;
         }

         return true;
      }
   }

   private NameTagRenderer.NameTagRendererBounds resolve(NameTagRenderer.NameTagRendererState2 nameTagRendererState26, NameTags nameTags5, long l) {
      if (!nameTagRendererState26.flag) {
         return null;
      } else {
         float floatValue5 = nameTagRendererState26.springValue.measure(nameTagRendererState26.flag2 ? 1.0F : 0.0F, SPRING_SPEC);
         float floatValue6 = nameTagRendererState26.springValue2.measure(nameTagRendererState26.flag2 ? 1.0F : 0.0F, SPRING_SPEC_2);
         float floatValue7 = nameTagRendererState26.springValue7.measure(nameTagRendererState26.floatValue2, SPRING_SPEC_6);
         float floatValue8 = nameTagRendererState26.springValue8.getFloatValue();
         if (nameTagRendererState26.floatValue2 >= floatValue8) {
            nameTagRendererState26.springValue8.invoke2(nameTagRendererState26.floatValue2);
            floatValue8 = nameTagRendererState26.floatValue2;
         } else {
            floatValue8 = nameTagRendererState26.springValue8.measure(nameTagRendererState26.floatValue2, SPRING_SPEC_7);
         }

         float floatValue9 = nameTagRendererState26.springValue10.measure(0.0F, SPRING_SPEC_9);
         if (nameTagRendererState26.flag3 && nameTagRendererState26.nameTagRendererData2 != null) {
            float floatValue10 = nameTagRendererState26.springValue3.measure(nameTagRendererState26.flag5 ? 1.0F : 0.0F, SPRING_SPEC_3);
            float floatValue11 = nameTagRendererState26.springValue4.measure(nameTagRendererState26.flag6 ? 1.0F : 0.0F, SPRING_SPEC_4);
            float floatValue12 = nameTagRendererState26.springValue9.measure(nameTagRendererState26.flag4 ? 1.0F : 0.0F, SPRING_SPEC_8);
            float floatValue13 = this.measure20(nameTagRendererState26.floatValue3, nameTagRendererState26.floatValue4, floatValue10);
            float floatValue14 = this.measure20(nameTagRendererState26.floatValue5, nameTagRendererState26.floatValue6, floatValue10);
            float floatValue15 = nameTagRendererState26.springValue5.measure(floatValue13, SPRING_SPEC_5);
            float floatValue16 = nameTagRendererState26.springValue6.measure(floatValue14, SPRING_SPEC_5);
            float floatValue17 = this.measure(
               nameTagRendererState26.nameTagRendererData2.distance(), nameTagRendererState26.nameTagRendererData2.projectedHeight(), nameTags5.razmer.getValue()
            );
            float floatValue18 = floatValue15 * floatValue17;
            float floatValue19 = floatValue16 * floatValue17;
            float floatValue20 = this.measure11(nameTagRendererState26, floatValue11, floatValue17);
            float floatValue21 = (1.0F - this.measure16(0.1F, 0.95F, floatValue5)) * 5.0F * floatValue17;
            float floatValue22 = this.measure23(nameTagRendererState26.nameTagRendererData2.screenX() - floatValue18 * 0.5F);
            float floatValue23 = this.measure23(nameTagRendererState26.nameTagRendererData2.screenY() - floatValue19 - 8.0F * floatValue17 - floatValue21);
            float floatValue24 = this.measure16(0.02F, 0.94F, floatValue5);
            float floatValue25 = this.measure22(nameTags5.prozrachnost.getValue() * (0.14F + 0.86F * floatValue24));
            if (floatValue25 <= 0.01F) {
               return null;
            } else {
               float floatValue26 = this.measure17(floatValue7, l, nameTagRendererState26.intValue);
               float floatValue27 = this.measure18(l - nameTagRendererState26.timestamp2, 720L);
               float floatValue28 = this.measure18(l - nameTagRendererState26.timestamp4, 360L);
               float floatValue29 = Math.max(floatValue27, floatValue28);
               floatValue9 = nameTagRendererState26.springValue10.measure(floatValue29, SPRING_SPEC_9);
               boolean flag3 = floatValue5 < 0.985F || floatValue9 > 0.04F || nameTagRendererState26.timestamp2 >= l - 760L || floatValue12 > 0.04F;
               return new NameTagRenderer.NameTagRendererBounds(
                  nameTagRendererState26, floatValue22, floatValue23, floatValue18, floatValue19, floatValue17, floatValue20, floatValue25, floatValue5, floatValue6, floatValue10, floatValue11, floatValue12, floatValue7, floatValue8, floatValue26, floatValue9, flag3
               );
            }
         } else {
            return null;
         }
      }
   }

   private void invoke5(RenderManager renderManager2, NameTagRenderer.NameTagRendererData nameTagRendererData3, NameTagRenderer.NameTagRendererBounds nameTagRendererBounds3, long l) {
      float floatValue30 = this.measure7(nameTagRendererBounds3);
      if (nameTagRendererBounds3.shader) {
         this.invoke6(renderManager2, nameTagRendererData3, nameTagRendererBounds3);
         RenderManager.RenderManagerState2 renderManagerState2 = renderManager2.resolve3(nameTagRendererBounds3.x, nameTagRendererBounds3.y, nameTagRendererBounds3.width, nameTagRendererBounds3.height);
         if (renderManagerState2 != null) {
            try {
               this.invoke8(renderManager2, nameTagRendererData3, nameTagRendererBounds3, 0.0F, 0.0F, NameTagRenderer.NameTagRendererState.GHOST);
            } finally {
               renderManager2.invoke14(renderManagerState2);
            }

            renderManager2.invoke17(
               renderManagerState2,
               nameTagRendererBounds3.x,
               nameTagRendererBounds3.y,
               nameTagRendererBounds3.width,
               nameTagRendererBounds3.height,
               11.5F * nameTagRendererBounds3.scale,
               this.compute12(nameTagRendererData3.shellTop, nameTagRendererBounds3.alpha * 0.8F),
               this.compute12(this.compute3(nameTagRendererData3, nameTagRendererBounds3), nameTagRendererBounds3.alpha * (0.02F + nameTagRendererBounds3.focus * 0.07F)),
               this.compute12(
                  this.compute4(nameTagRendererData3, nameTagRendererBounds3), nameTagRendererBounds3.alpha * (0.38F + nameTagRendererBounds3.focus * 0.2F + nameTagRendererBounds3.threat * 0.16F)
               ),
               this.compute12(
                  this.compute5(nameTagRendererData3, nameTagRendererBounds3), nameTagRendererBounds3.alpha * (0.34F + nameTagRendererBounds3.focus * 0.18F + nameTagRendererBounds3.threat * 0.14F)
               ),
               nameTagRendererBounds3.appear,
               floatValue30,
               this.measure19(l),
               nameTagRendererBounds3.focus,
               nameTagRendererBounds3.threat,
               nameTagRendererBounds3.exposure
            );
            this.invoke8(renderManager2, nameTagRendererData3, nameTagRendererBounds3, nameTagRendererBounds3.x, nameTagRendererBounds3.y, NameTagRenderer.NameTagRendererState.OVERLAY);
            return;
         }
      }

      this.invoke7(renderManager2, nameTagRendererData3, nameTagRendererBounds3);
      this.invoke8(renderManager2, nameTagRendererData3, nameTagRendererBounds3, nameTagRendererBounds3.x, nameTagRendererBounds3.y, NameTagRenderer.NameTagRendererState.DIRECT);
   }

   private void invoke6(RenderManager renderManager3, NameTagRenderer.NameTagRendererData nameTagRendererData4, NameTagRenderer.NameTagRendererBounds nameTagRendererBounds4) {
      float floatValue31 = 11.5F * nameTagRendererBounds4.scale;
      float floatValue32 = this.measure7(nameTagRendererBounds4);
      renderManager3.invoke44(
         nameTagRendererBounds4.x,
         nameTagRendererBounds4.y,
         nameTagRendererBounds4.width,
         nameTagRendererBounds4.height,
         floatValue31,
         nameTagRendererBounds4.alpha * (0.5F + nameTagRendererBounds4.focus * 0.18F + (1.0F - floatValue32) * 0.12F)
      );
      float floatValue33 = nameTagRendererBounds4.focus * 0.92F + nameTagRendererBounds4.threat * 0.98F + nameTagRendererBounds4.exposure * 0.68F;
      if (floatValue33 > 0.03F) {
         int intValue2 = this.compute6(nameTagRendererData4, nameTagRendererBounds4);
         renderManager3.invoke41(
            nameTagRendererBounds4.x,
            nameTagRendererBounds4.y + nameTagRendererBounds4.scale,
            nameTagRendererBounds4.width,
            nameTagRendererBounds4.height,
            floatValue31,
            20.0F * nameTagRendererBounds4.scale * floatValue33,
            2.4F * nameTagRendererBounds4.scale,
            this.compute12(intValue2, nameTagRendererBounds4.alpha * floatValue33 * 0.15F)
         );
      }
   }

   private void invoke7(RenderManager renderManager4, NameTagRenderer.NameTagRendererData nameTagRendererData5, NameTagRenderer.NameTagRendererBounds nameTagRendererBounds5) {
      float floatValue34 = 11.5F * nameTagRendererBounds5.scale;
      int intValue3 = this.compute12(this.compute3(nameTagRendererData5, nameTagRendererBounds5), nameTagRendererBounds5.alpha * (0.1F + nameTagRendererBounds5.focus * 0.08F));
      int intValue4 = this.compute12(nameTagRendererData5.shellTop, nameTagRendererBounds5.alpha * 0.72F);
      int intValue5 = this.compute12(nameTagRendererData5.shellBottom, nameTagRendererBounds5.alpha * 0.88F);
      int intValue6 = this.compute6(nameTagRendererData5, nameTagRendererBounds5);
      renderManager4.invoke44(nameTagRendererBounds5.x, nameTagRendererBounds5.y, nameTagRendererBounds5.width, nameTagRendererBounds5.height, floatValue34, nameTagRendererBounds5.alpha * 0.52F);
      renderManager4.invoke37(nameTagRendererBounds5.x, nameTagRendererBounds5.y, nameTagRendererBounds5.width, nameTagRendererBounds5.height, floatValue34, intValue4, intValue5);
      renderManager4.invoke37(
         nameTagRendererBounds5.x + 1.0F,
         nameTagRendererBounds5.y + 1.0F,
         Math.max(0.0F, nameTagRendererBounds5.width - 2.0F),
         Math.max(0.0F, nameTagRendererBounds5.height * 0.52F),
         Math.max(0.0F, floatValue34 - 1.0F),
         this.compute12(16777215, nameTagRendererBounds5.alpha * 0.016F),
         this.compute12(16777215, 0.0F)
      );
      renderManager4.invoke28(
         nameTagRendererBounds5.x, nameTagRendererBounds5.y, nameTagRendererBounds5.width, nameTagRendererBounds5.height, floatValue34, intValue3, Math.max(0.7F, nameTagRendererBounds5.scale * 0.72F)
      );
      float floatValue35 = nameTagRendererBounds5.focus * 0.92F + nameTagRendererBounds5.threat * 0.98F + nameTagRendererBounds5.exposure * 0.68F;
      if (floatValue35 > 0.03F) {
         renderManager4.invoke41(
            nameTagRendererBounds5.x,
            nameTagRendererBounds5.y + nameTagRendererBounds5.scale,
            nameTagRendererBounds5.width,
            nameTagRendererBounds5.height,
            floatValue34,
            18.0F * nameTagRendererBounds5.scale * floatValue35,
            2.2F * nameTagRendererBounds5.scale,
            this.compute12(intValue6, nameTagRendererBounds5.alpha * floatValue35 * 0.13F)
         );
      }
   }

   private void invoke8(
      RenderManager renderManager5, NameTagRenderer.NameTagRendererData nameTagRendererData6, NameTagRenderer.NameTagRendererBounds nameTagRendererBounds6, float f, float g, NameTagRenderer.NameTagRendererState nameTagRendererState
   ) {
      NameTagRenderer.NameTagRendererState2 nameTagRendererState27 = nameTagRendererBounds6.state;
      float floatValue36 = nameTagRendererBounds6.scale;
      float floatValue37 = 10.0F * floatValue36;
      float floatValue38 = 29.0F * floatValue36;
      float floatValue39 = 16.0F * floatValue36;
      float floatValue40 = 7.0F * floatValue36;
      float floatValue41 = 14.8F * floatValue36;
      float floatValue42 = 11.2F * floatValue36;
      float floatValue43 = 12.8F * floatValue36;
      float floatValue44 = 9.4F * floatValue36;
      float floatValue45 = this.measure7(nameTagRendererBounds6);
      float floatValue46 = this.measure10(nameTagRendererBounds6, nameTagRendererState);
      float floatValue47 = this.measure9(nameTagRendererBounds6, floatValue46);
      float floatValue48 = nameTagRendererState == NameTagRenderer.NameTagRendererState.GHOST ? 0.48F : 1.0F;
      float floatValue49 = nameTagRendererState == NameTagRenderer.NameTagRendererState.GHOST ? 0.15F : 0.32F;
      float floatValue50 = (1.0F - floatValue45) * (nameTagRendererState == NameTagRenderer.NameTagRendererState.OVERLAY ? 1.85F : 5.0F) * floatValue36;
      float floatValue51 = f + floatValue37;
      float floatValue52 = g + (floatValue38 - floatValue39) * 0.5F + floatValue50 * 0.16F;
      float floatValue53 = this.measure16(0.18F, 0.74F, floatValue47);
      float floatValue54 = nameTagRendererState27.floatValue7 * floatValue36 * floatValue53;
      float floatValue55 = floatValue51 + floatValue39 + floatValue40;
      float floatValue56 = Math.max(0.0F, f + nameTagRendererBounds6.width - floatValue37 - floatValue55 - floatValue54 - 42.0F * floatValue36);
      String text3 = nameTagRendererState27.text2.isEmpty() ? "" : this.resolve9(nameTagRendererState27.text2, floatValue56, FontRegistry.fontObject, floatValue42);
      float floatValue57 = text3.isEmpty() ? 0.0F : this.measure3(FontRegistry.fontObject, text3, floatValue42);
      float floatValue58 = text3.isEmpty() ? 0.0F : 5.0F * floatValue36;
      float floatValue59 = floatValue55 + floatValue57 + floatValue58;
      float floatValue60 = Math.max(10.0F * floatValue36, f + nameTagRendererBounds6.width - floatValue37 - floatValue59 - Math.max(0.0F, floatValue54 + 8.0F * floatValue36 * floatValue53));
      String text4 = this.resolve9(nameTagRendererState27.text, floatValue60, FontRegistry.fontObject4, floatValue41);
      NameTagRenderer.NameTagRendererBounds2 nameTagRendererBounds22 = this.resolve11(FontRegistry.fontObject4, text4, floatValue41);
      float floatValue61 = this.measure5(g + floatValue50 * 0.08F, floatValue38 - 5.0F * floatValue36, nameTagRendererBounds22.height);
      renderManager5.invoke5(
         floatValue51, floatValue52, floatValue39, floatValue39, floatValue39 * 0.48F, this.compute12(nameTagRendererData6.avatarBackdrop, nameTagRendererBounds6.alpha * floatValue48 * (0.14F + 0.2F * floatValue46))
      );
      this.invoke14(
         renderManager5,
         nameTagRendererState27.uUID,
         nameTagRendererState27.text,
         floatValue51,
         floatValue52,
         floatValue39,
         nameTagRendererBounds6.alpha * (nameTagRendererState == NameTagRenderer.NameTagRendererState.GHOST ? 0.18F + 0.42F * floatValue46 : 0.42F + 0.58F * floatValue46)
      );
      int intValue7 = this.compute12(this.compute8(nameTagRendererData6, nameTagRendererBounds6), nameTagRendererBounds6.alpha * floatValue46 * floatValue48);
      if (!text3.isEmpty()) {
         NameTagRenderer.NameTagRendererBounds2 nameTagRendererBounds23 = this.resolve11(FontRegistry.fontObject, text3, floatValue42);
         float floatValue62 = this.measure5(g + floatValue50 * 0.08F, floatValue38 - 5.0F * floatValue36, nameTagRendererBounds23.height);
         this.invoke15(
            renderManager5,
            FontRegistry.fontObject,
            text3,
            floatValue55,
            floatValue62,
            floatValue42,
            this.compute12(nameTagRendererState27.intValue3, nameTagRendererBounds6.alpha * floatValue46 * floatValue48),
            nameTagRendererBounds6.alpha * floatValue46 * floatValue49
         );
      }

      this.invoke15(renderManager5, FontRegistry.fontObject4, text4, floatValue59, floatValue61, floatValue41, intValue7, nameTagRendererBounds6.alpha * floatValue46 * floatValue49);
      if (nameTagRendererState27.clientUser && nameTagRendererState != NameTagRenderer.NameTagRendererState.GHOST) {
         float floatValue200 = 15.5F * floatValue36;
         float floatValue201 = this.measure3(BrandMark.font(), BrandMark.GLYPH, floatValue200);
         this.invoke15(
            renderManager5,
            BrandMark.font(),
            BrandMark.GLYPH,
            f + (nameTagRendererBounds6.width - floatValue201) * 0.5F,
            g - 4.0F * floatValue36,
            floatValue200,
            this.compute12(CLIENT_MARK_COLOR, nameTagRendererBounds6.alpha * floatValue46 * floatValue48),
            nameTagRendererBounds6.alpha * floatValue46 * floatValue48 * 0.3F
         );
      }

      if (floatValue53 > 0.01F) {
         NameTagRenderer.NameTagRendererBounds2 nameTagRendererBounds24 = this.resolve11(FontRegistry.fontObject, nameTagRendererState27.text20Hp, floatValue43);
         float floatValue63 = this.measure3(FontRegistry.fontObject, nameTagRendererState27.text20Hp, floatValue43);
         float floatValue64 = f + nameTagRendererBounds6.width - floatValue37 - floatValue63;
         float floatValue65 = this.measure5(g + floatValue50 * 0.08F, floatValue38 - 5.0F * floatValue36, nameTagRendererBounds24.height);
         this.invoke15(
            renderManager5,
            FontRegistry.fontObject,
            nameTagRendererState27.text20Hp,
            floatValue64,
            floatValue65,
            floatValue43,
            this.compute12(nameTagRendererData6.textPrimary, nameTagRendererBounds6.alpha * floatValue53 * floatValue48),
            nameTagRendererBounds6.alpha * floatValue53 * floatValue48 * 0.28F
         );
      }

      this.invoke9(
         renderManager5,
         nameTagRendererData6,
         nameTagRendererBounds6,
         f + floatValue37,
         g + floatValue38 - 5.0F * floatValue36 + floatValue50 * 0.1F,
         nameTagRendererBounds6.width - floatValue37 * 2.0F,
         3.6F * floatValue36,
         floatValue46,
         floatValue48
      );
      if (!(floatValue47 <= 0.01F)) {
         boolean flag4 = this.check7(nameTagRendererState27);
         float floatValue66 = g + floatValue38 + 4.0F * floatValue36;
         int intValue8 = this.compute12(nameTagRendererData6.divider, nameTagRendererBounds6.alpha * 0.1F * floatValue47);
         renderManager5.invoke34(
            f + floatValue37,
            floatValue66 - 1.5F * floatValue36,
            nameTagRendererBounds6.width - floatValue37 * 2.0F,
            Math.max(1.0F, floatValue36),
            0.5F * floatValue36,
            intValue8,
            this.compute12(nameTagRendererData6.divider, 0.0F)
         );
         if (flag4) {
            float floatValue67 = this.measure16(0.16F, 0.7F, floatValue47) * floatValue46 * floatValue48;
            float floatValue68 = this.measure6(g, floatValue36);
            float floatValue69 = 10.0F * floatValue36;
            float floatValue70 = nameTagRendererState27.floatValue9 * floatValue36;
            float floatValue71 = Math.max(0.0F, nameTagRendererBounds6.width - floatValue37 * 2.0F - floatValue70 - (nameTagRendererState27.nameTagRendererDisplayEntry22.text.isEmpty() ? 0.0F : 8.0F * floatValue36));
            String text5 = this.resolve9(nameTagRendererState27.nameTagRendererDisplayEntry2.text, floatValue71, FontRegistry.fontObject, floatValue44);
            float floatValue72 = Math.max(0.0F, this.measure3(FontRegistry.fontObject, text5, floatValue44) + 8.0F * floatValue36);
            if (!text5.isEmpty()) {
               renderManager5.invoke5(
                  f + floatValue37 - 3.0F * floatValue36,
                  floatValue68 - floatValue69 * 0.72F,
                  floatValue72,
                  floatValue69,
                  floatValue69 * 0.5F,
                  this.compute12(this.compute13(nameTagRendererData6.avatarBackdrop, 592656, 0.3F), nameTagRendererBounds6.alpha * (0.34F * floatValue67))
               );
               this.invoke15(
                  renderManager5,
                  FontRegistry.fontObject,
                  text5,
                  f + floatValue37,
                  floatValue68,
                  floatValue44,
                  this.compute12(nameTagRendererState27.nameTagRendererDisplayEntry2.color, nameTagRendererBounds6.alpha * floatValue67),
                  nameTagRendererBounds6.alpha * floatValue67 * 0.26F
               );
            }

            if (!nameTagRendererState27.nameTagRendererDisplayEntry22.text.isEmpty()) {
               float floatValue73 = Math.max(nameTagRendererState27.floatValue9 * floatValue36, this.measure3(FontRegistry.fontObject, nameTagRendererState27.nameTagRendererDisplayEntry22.text, floatValue44));
               float floatValue74 = f + nameTagRendererBounds6.width - floatValue37 - floatValue73;
               renderManager5.invoke5(
                  floatValue74 - 3.0F * floatValue36,
                  floatValue68 - floatValue69 * 0.72F,
                  floatValue73 + 8.0F * floatValue36,
                  floatValue69,
                  floatValue69 * 0.5F,
                  this.compute12(this.compute13(nameTagRendererData6.avatarBackdrop, 592656, 0.3F), nameTagRendererBounds6.alpha * (0.34F * floatValue67))
               );
               this.invoke15(
                  renderManager5,
                  FontRegistry.fontObject,
                  nameTagRendererState27.nameTagRendererDisplayEntry22.text,
                  floatValue74,
                  floatValue68,
                  floatValue44,
                  this.compute12(nameTagRendererState27.nameTagRendererDisplayEntry22.color, nameTagRendererBounds6.alpha * floatValue67),
                  nameTagRendererBounds6.alpha * floatValue67 * 0.26F
               );
            }
         }
      }
   }

   private void invoke9(
      RenderManager renderManager6, NameTagRenderer.NameTagRendererData nameTagRendererData7, NameTagRenderer.NameTagRendererBounds nameTagRendererBounds7, float f, float g, float h, float i, float j, float k
   ) {
      float floatValue75 = i * 0.5F;
      float floatValue76 = h * this.measure22(nameTagRendererBounds7.damage);
      float floatValue77 = h * this.measure22(nameTagRendererBounds7.health);
      float floatValue78 = nameTagRendererBounds7.alpha * k * (0.24F + 0.76F * j);
      renderManager6.invoke5(f, g, h, i, floatValue75, this.compute12(nameTagRendererData7.barTrack, floatValue78 * 0.16F));
      renderManager6.invoke37(f, g, h, Math.max(i * 0.64F, 1.0F), floatValue75, this.compute12(16777215, floatValue78 * 0.02F), this.compute12(16777215, 0.0F));
      float floatValue79 = Math.max(0.0F, floatValue76 - floatValue77);
      if (floatValue79 > 0.4F) {
         renderManager6.invoke34(f + floatValue77, g, floatValue79, i, floatValue75, this.compute12(15988479, floatValue78 * 0.34F), this.compute12(16777215, floatValue78 * 0.18F));
      }

      if (floatValue77 > 0.5F) {
         int intValue9 = this.compute12(this.compute9(nameTagRendererData7, nameTagRendererBounds7), floatValue78);
         int intValue10 = this.compute12(this.compute10(nameTagRendererData7, nameTagRendererBounds7), floatValue78);
         renderManager6.invoke34(f, g, floatValue77, i, floatValue75, intValue9, intValue10);
         renderManager6.invoke37(f, g, floatValue77, i * 0.58F, floatValue75, this.compute12(16777215, floatValue78 * 0.15F), this.compute12(16777215, 0.0F));
         float floatValue80 = Math.max(i * 1.2F, 2.0F * nameTagRendererBounds7.scale);
         float floatValue81 = f + Math.max(0.0F, floatValue77 - floatValue80);
         renderManager6.invoke37(
            floatValue81,
            g - 0.15F * nameTagRendererBounds7.scale,
            floatValue80,
            i + 0.3F * nameTagRendererBounds7.scale,
            floatValue75,
            this.compute12(16777215, floatValue78 * 0.18F),
            this.compute12(this.compute10(nameTagRendererData7, nameTagRendererBounds7), floatValue78 * 0.1F)
         );
      }

      if (nameTagRendererBounds7.threat > 0.01F) {
         renderManager6.invoke21();

         try {
            renderManager6.invoke41(
               f,
               g,
               Math.max(1.0F, Math.max(floatValue77, floatValue76)),
               i,
               floatValue75,
               7.2F * nameTagRendererBounds7.scale * nameTagRendererBounds7.threat,
               1.65F * nameTagRendererBounds7.scale,
               this.compute12(nameTagRendererData7.dangerGlow, nameTagRendererBounds7.alpha * (0.1F + nameTagRendererBounds7.threat * 0.12F))
            );
         } finally {
            renderManager6.invoke22();
         }
      }
   }

   private void invoke10(RenderManager renderManager7, NameTagRenderer.NameTagRendererData nameTagRendererData8, NameTagRenderer.NameTagRendererBounds nameTagRendererBounds8) {
      NameTagRenderer.NameTagRendererState2 nameTagRendererState28 = nameTagRendererBounds8.state;
      if (!nameTagRendererState28.items.isEmpty() && !(nameTagRendererBounds8.itemReveal <= 0.02F)) {
         float floatValue82 = nameTagRendererBounds8.scale;
         float floatValue83 = 16.0F * floatValue82;
         float floatValue84 = 4.0F * floatValue82;
         float floatValue85 = this.measure14(nameTagRendererState28.items, floatValue83, floatValue84);
         float floatValue86 = nameTagRendererBounds8.x + (nameTagRendererBounds8.width - floatValue85) * 0.5F;
         float floatValue87 = this.measure12(nameTagRendererBounds8);
         float floatValue88 = this.measure16(0.1F, 0.84F, nameTagRendererBounds8.itemReveal);

         for (int intValue11 = 0; intValue11 < nameTagRendererState28.items.size(); intValue11++) {
            float floatValue89 = this.measure15(floatValue88, 0.08F + intValue11 * 0.06F, 0.2F);
            if (!(floatValue89 <= 0.01F)) {
               float floatValue90 = floatValue86 + intValue11 * (floatValue83 + floatValue84);
               float floatValue91 = floatValue87 + (1.0F - floatValue89) * 6.0F * floatValue82;
               float floatValue92 = floatValue83 * 0.42F;
               int intValue12 = this.compute12(this.compute13(nameTagRendererData8.slotFill, 16777215, 0.06F), nameTagRendererBounds8.alpha * (0.12F + floatValue89 * 0.05F));
               int intValue13 = this.compute12(this.compute13(nameTagRendererData8.avatarBackdrop, 132103, 0.18F), nameTagRendererBounds8.alpha * (0.64F + floatValue89 * 0.06F));
               int intValue14 = this.compute12(this.compute13(nameTagRendererData8.rim, 16777215, 0.05F), nameTagRendererBounds8.alpha * (0.06F + floatValue89 * 0.04F));
               renderManager7.invoke41(
                  floatValue90, floatValue91 + 0.8F * floatValue82, floatValue83, floatValue83, floatValue92, 4.8F * floatValue82, 1.05F * floatValue82, this.compute12(0, nameTagRendererBounds8.alpha * (0.08F + floatValue89 * 0.05F))
               );
               renderManager7.invoke37(floatValue90, floatValue91, floatValue83, floatValue83, floatValue92, intValue12, intValue13);
               renderManager7.invoke28(floatValue90, floatValue91, floatValue83, floatValue83, floatValue92, intValue14, Math.max(0.58F, floatValue82 * 0.7F));
            }
         }
      }
   }

   private void invoke11(RenderManager renderManager8, List<NameTagRenderer.NameTagRendererBounds> list) {
      if (!list.isEmpty() && CLIENT.player != null) {
         renderManager8.invoke20();

         for (NameTagRenderer.NameTagRendererBounds nameTagRendererBounds9 : list) {
            NameTagRenderer.NameTagRendererState2 nameTagRendererState29 = nameTagRendererBounds9.state;
            float floatValue93 = this.measure16(0.1F, 0.84F, nameTagRendererBounds9.itemReveal);
            if (!(floatValue93 <= 0.01F) && !nameTagRendererState29.items.isEmpty()) {
               float floatValue94 = nameTagRendererBounds9.scale;
               float floatValue95 = 16.0F * floatValue94;
               float floatValue96 = 4.0F * floatValue94;
               float floatValue97 = this.measure14(nameTagRendererState29.items, floatValue95, floatValue96);
               float floatValue98 = nameTagRendererBounds9.x + (nameTagRendererBounds9.width - floatValue97) * 0.5F;
               float floatValue99 = this.measure12(nameTagRendererBounds9);

               for (int intValue15 = 0; intValue15 < nameTagRendererState29.items.size(); intValue15++) {
                  float floatValue100 = this.measure15(floatValue93, 0.08F + intValue15 * 0.06F, 0.2F);
                  if (!(floatValue100 <= 0.05F)) {
                     float floatValue101 = floatValue98 + intValue15 * (floatValue95 + floatValue96);
                     float floatValue102 = floatValue99 + (1.0F - floatValue100) * 6.0F * floatValue94;
                     float floatValue103 = floatValue95 * (0.6F + floatValue100 * 0.24F) / 16.0F;
                     float floatValue104 = 16.0F * floatValue103;
                     float floatValue105 = floatValue101 + (floatValue95 - floatValue104) * 0.5F;
                     float floatValue106 = floatValue102 + (floatValue95 - floatValue104) * 0.5F;
                     int intValue16 = nameTagRendererState29.intValue + intValue15;
                     ItemRenderUtil.invoke3(renderManager8, nameTagRendererState29.items.get(intValue15).stack, floatValue105, floatValue106, floatValue103, intValue16, false, intValue15);
                  }
               }
            }
         }
      }
   }

   private boolean check2(PlayerEntity playerEntity, NameTags nameTags6) {
      return playerEntity != null
         && playerEntity.isAlive()
         && !playerEntity.isSpectator()
         && playerEntity != CLIENT.player
         && (nameTags6.nevidimki.isEnabled() || !playerEntity.isInvisibleTo(CLIENT.player));
   }

   private NameTagRenderer.NameTagRendererData2 resolve2(PlayerEntity playerEntity, float f, int i, int j) {
      Vec3d vec3d = playerEntity.getLerpedPos(f);
      double doubleValue = playerEntity.getHeight() + 0.3 - (playerEntity.isSneaking() ? 0.14 : 0.0);
      Vec3d vec3d2 = new Vec3d(vec3d.x, vec3d.y + doubleValue, vec3d.z);
      Vec3d vec3d3 = new Vec3d(vec3d.x, vec3d.y + 0.02, vec3d.z);
      Vec3d vec3d4 = MathUtils.resolve(vec3d2);
      Vec3d vec3d5 = MathUtils.resolve(vec3d3);
      if (vec3d4 == null || vec3d5 == null) {
         return null;
      } else if (!(vec3d4.z <= 0.001) && !(vec3d4.z > 1.0) && !(vec3d5.z <= 0.001) && !(vec3d5.z > 1.0)) {
         double doubleValue2 = CLIENT.gameRenderer.getCamera().getPos().distanceTo(vec3d2);
         if (doubleValue2 > 96.0) {
            return null;
         } else {
            float floatValue107 = Math.max(18.0F, Math.abs((float)(vec3d5.y - vec3d4.y)));
            float floatValue108 = Math.max(18.0F, floatValue107);
            if (!(vec3d4.x < -floatValue108 * 2.0F) && !(vec3d4.x > i + floatValue108 * 2.0F) && !(vec3d4.y < -j * 0.6F) && !(vec3d4.y > j + floatValue108 * 2.0F)) {
               float floatValue109 = this.measure21((float)vec3d4.x, -floatValue108 * 0.25F, i + floatValue108 * 0.25F);
               float floatValue110 = Math.max((float)vec3d4.y, 18.0F);
               return new NameTagRenderer.NameTagRendererData2(floatValue109, floatValue110, doubleValue2, floatValue107, (float)vec3d4.z);
            } else {
               return null;
            }
         }
      } else {
         return null;
      }
   }

   private boolean check3(PlayerEntity playerEntity, NameTags nameTags7) {
      return CLIENT.currentScreen == null && nameTags7.infoPriNavodke.isEnabled() && CLIENT.targetedEntity == playerEntity;
   }

   private List<NameTagRenderer.NameTagRendererItemData> resolve3(PlayerEntity playerEntity, NameTags nameTags8) {
      ArrayList arrayList3 = new ArrayList(6);
      if (nameTags8.bronya.isEnabled()) {
         this.invoke12(arrayList3, playerEntity.getEquippedStack(EquipmentSlot.HEAD), EquipmentSlot.HEAD);
         this.invoke12(arrayList3, playerEntity.getEquippedStack(EquipmentSlot.CHEST), EquipmentSlot.CHEST);
         this.invoke12(arrayList3, playerEntity.getEquippedStack(EquipmentSlot.LEGS), EquipmentSlot.LEGS);
         this.invoke12(arrayList3, playerEntity.getEquippedStack(EquipmentSlot.FEET), EquipmentSlot.FEET);
      }

      if (nameTags8.pravayaRuka.isEnabled()) {
         this.invoke12(arrayList3, playerEntity.getMainHandStack(), EquipmentSlot.MAINHAND);
      }

      if (nameTags8.levayaRuka.isEnabled()) {
         this.invoke12(arrayList3, playerEntity.getOffHandStack(), EquipmentSlot.OFFHAND);
      }

      return arrayList3;
   }

   private void invoke12(List<NameTagRenderer.NameTagRendererItemData> list, ItemStack itemStack, EquipmentSlot equipmentSlot) {
      if (itemStack != null && !itemStack.isEmpty()) {
         list.add(new NameTagRenderer.NameTagRendererItemData(itemStack.copy(), equipmentSlot));
      }
   }

   private List<NameTagRenderer.NameTagRendererDisplayEntry> resolve4(PlayerEntity playerEntity, NameTags nameTags9) {
      if (!nameTags9.effekty.isEnabled()) {
         return List.of();
      } else {
         ArrayList arrayList4 = new ArrayList();

         for (StatusEffectInstance statusEffectInstance : playerEntity.getStatusEffects()) {
            String text6 = this.resolve10(I18n.translate(((StatusEffect)statusEffectInstance.getEffectType().value()).getTranslationKey(), new Object[0]));
            if (!text6.isEmpty()) {
               String text7 = text6 + " " + this.resolve13(statusEffectInstance.getAmplifier() + 1);
               boolean flag5 = ((StatusEffect)statusEffectInstance.getEffectType().value()).getCategory() == StatusEffectCategory.HARMFUL;
               int intValue17 = flag5 ? 16732754 : 15133941;
               arrayList4.add(new NameTagRenderer.NameTagRendererDisplayEntry(text7, intValue17, flag5, statusEffectInstance.getDuration()));
            }
         }

         arrayList4.sort(
            Comparator.<NameTagRenderer.NameTagRendererDisplayEntry, Boolean>comparing(nameTagRendererDisplayEntry -> !nameTagRendererDisplayEntry.harmful)
               .thenComparingInt(NameTagRenderer.NameTagRendererDisplayEntry::duration)
               .reversed()
               .thenComparing(NameTagRenderer.NameTagRendererDisplayEntry::label)
         );
         return arrayList4.size() > 2 ? List.copyOf(arrayList4.subList(0, 2)) : List.copyOf(arrayList4);
      }
   }

   private NameTagRenderer.NameTagRendererDisplayEntry2 resolve5(NameTagRenderer.NameTagRendererState2 nameTagRendererState210) {
      if (!nameTagRendererState210.items2.isEmpty()) {
         return new NameTagRenderer.NameTagRendererDisplayEntry2(nameTagRendererState210.items2.get(0).label, nameTagRendererState210.items2.get(0).color);
      } else {
         return nameTagRendererState210.flag7 ? new NameTagRenderer.NameTagRendererDisplayEntry2("ALLY", 10284799) : new NameTagRenderer.NameTagRendererDisplayEntry2("", 15133941);
      }
   }

   private NameTagRenderer.NameTagRendererDisplayEntry2 resolve6(PlayerEntity playerEntity, NameTagRenderer.NameTagRendererState2 nameTagRendererState211) {
      if (nameTagRendererState211.items2.size() > 1) {
         return new NameTagRenderer.NameTagRendererDisplayEntry2(nameTagRendererState211.items2.get(1).label, nameTagRendererState211.items2.get(1).color);
      } else {
         return playerEntity.getArmor() > 0 ? new NameTagRenderer.NameTagRendererDisplayEntry2("ARM " + playerEntity.getArmor(), 12371672) : new NameTagRenderer.NameTagRendererDisplayEntry2("", 12371672);
      }
   }

   private void invoke13(NameTagRenderer.NameTagRendererState2 nameTagRendererState212) {
      nameTagRendererState212.floatValue7 = this.measure3(FontRegistry.fontObject, nameTagRendererState212.text20Hp, 12.8F);
      float floatValue111 = this.measure3(FontRegistry.fontObject4, nameTagRendererState212.text, 14.8F);
      float floatValue112 = nameTagRendererState212.text2.isEmpty() ? 0.0F : this.measure3(FontRegistry.fontObject, nameTagRendererState212.text2, 11.2F) + 5.0F;
      nameTagRendererState212.floatValue8 = this.measure3(FontRegistry.fontObject, nameTagRendererState212.nameTagRendererDisplayEntry2.text, 9.4F);
      nameTagRendererState212.floatValue9 = this.measure3(FontRegistry.fontObject, nameTagRendererState212.nameTagRendererDisplayEntry22.text, 9.4F);
      nameTagRendererState212.floatValue10 = this.measure14(nameTagRendererState212.items, 16.0F, 4.0F);
      nameTagRendererState212.floatValue3 = this.measure21(33.0F + floatValue112 + floatValue111 + 10.0F + 10.0F, 92.0F, 340.0F);
      float floatValue113 = 33.0F + floatValue112 + floatValue111 + 12.0F + nameTagRendererState212.floatValue7 + 10.0F;
      float floatValue114 = nameTagRendererState212.floatValue8
         + nameTagRendererState212.floatValue9
         + (!nameTagRendererState212.nameTagRendererDisplayEntry2.text.isEmpty() && !nameTagRendererState212.nameTagRendererDisplayEntry22.text.isEmpty() ? 8.0F : 0.0F);
      nameTagRendererState212.floatValue4 = this.measure21(Math.max(nameTagRendererState212.floatValue3 + 28.0F, Math.max(floatValue113, floatValue114 + 20.0F + 16.0F)), 92.0F, 340.0F);
      nameTagRendererState212.floatValue5 = 29.0F;
      float floatValue115 = 0.0F;
      if (this.check7(nameTagRendererState212)) {
         floatValue115 += 15.0F;
         floatValue115 += 11.0F;
      }

      nameTagRendererState212.floatValue6 = 29.0F + floatValue115;
   }

   private int compute(NameTagRenderer.NameTagRendererState2 nameTagRendererState213) {
      int intValue18 = nameTagRendererState213.text.hashCode();
      intValue18 = 31 * intValue18 + nameTagRendererState213.text2.hashCode();
      intValue18 = 31 * intValue18 + nameTagRendererState213.intValue3;
      intValue18 = 31 * intValue18 + nameTagRendererState213.text20Hp.hashCode();
      intValue18 = 31 * intValue18 + nameTagRendererState213.nameTagRendererDisplayEntry2.text.hashCode();
      intValue18 = 31 * intValue18 + nameTagRendererState213.nameTagRendererDisplayEntry2.color;
      intValue18 = 31 * intValue18 + nameTagRendererState213.nameTagRendererDisplayEntry22.text.hashCode();
      intValue18 = 31 * intValue18 + nameTagRendererState213.nameTagRendererDisplayEntry22.color;

      for (NameTagRenderer.NameTagRendererItemData nameTagRendererItemData : nameTagRendererState213.items) {
         intValue18 = 31 * intValue18 + ItemStack.hashCode(nameTagRendererItemData.stack);
         intValue18 = 31 * intValue18 + nameTagRendererItemData.slot.ordinal();
      }

      for (NameTagRenderer.NameTagRendererDisplayEntry nameTagRendererDisplayEntry2 : nameTagRendererState213.items2) {
         intValue18 = 31 * intValue18 + nameTagRendererDisplayEntry2.label.hashCode();
         intValue18 = 31 * intValue18 + nameTagRendererDisplayEntry2.color;
      }

      return intValue18;
   }

   private float measure(double d, float f, float g) {
      float floatValue116 = this.measure21(f / 96.0F, 0.75F, 1.35F);
      float floatValue117 = this.measure21((float)(1.35 - Math.log(d + 1.0) * 0.16), 0.75F, 1.25F);
      float floatValue118 = this.measure21(floatValue116 * 0.7F + floatValue117 * 0.3F, 0.85F, 1.35F);
      return this.measure24(floatValue118 * g, 0.01F);
   }

   private float measure2(PlayerEntity playerEntity) {
      float floatValue119 = playerEntity.getHealth() + playerEntity.getAbsorptionAmount();
      if (CLIENT.world != null) {
         Scoreboard scoreboard = CLIENT.world.getScoreboard();
         ScoreboardObjective scoreboardObjective = scoreboard.getObjectiveForSlot(ScoreboardDisplaySlot.BELOW_NAME);
         if (scoreboardObjective != null) {
            ReadableScoreboardScore readableScoreboardScore = scoreboard.getScore(playerEntity, scoreboardObjective);
            if (readableScoreboardScore != null && readableScoreboardScore.getScore() > 0) {
               floatValue119 = readableScoreboardScore.getScore();
            }
         }
      }

      return Math.max(0.0F, floatValue119);
   }

   private void invoke14(RenderManager renderManager9, UUID uUID, String string, float f, float g, float h, float i) {
      int intValue19 = this.compute2(uUID);
      if (intValue19 > 0) {
         GlStateManager._bindTexture(intValue19);
         renderManager9.invoke65(i);

         try {
            float floatValue120 = h * 0.48F;
            renderManager9.invoke12(intValue19, f, g, h, h, 0.125F, 0.125F, 0.25F, 0.25F, floatValue120);
            renderManager9.invoke12(intValue19, f, g, h, h, 0.625F, 0.125F, 0.75F, 0.25F, floatValue120);
         } finally {
            renderManager9.invoke66();
         }
      } else {
         renderManager9.invoke5(f, g, h, h, h * 0.48F, this.compute12(1842983, i * 0.92F));
         String text8 = string != null && !string.isEmpty() ? string.substring(0, 1).toUpperCase(Locale.ROOT) : "?";
         float floatValue121 = h * 0.62F;
         float floatValue122 = this.measure3(FontRegistry.fontObject4, text8, floatValue121);
         float floatValue123 = this.measure4(FontRegistry.fontObject4, text8, floatValue121);
         renderManager9.invoke69(FontRegistry.fontObject4, f + (h - floatValue122) * 0.5F, this.measure5(g, h, floatValue123), floatValue121, text8, this.compute12(15922683, i));
      }
   }

   private int compute2(UUID uUID) {
      if (CLIENT.getNetworkHandler() == null) {
         return 0;
      } else {
         PlayerListEntry playerListEntry = CLIENT.getNetworkHandler().getPlayerListEntry(uUID);
         if (playerListEntry == null) {
            return 0;
         } else {
            Identifier identifier = playerListEntry.getSkinTextures().texture();
            if (identifier == null) {
               return 0;
            } else {
               AbstractTexture abstractTexture = CLIENT.getTextureManager().getTexture(identifier);
               return abstractTexture != null && abstractTexture.getGlTexture() instanceof GlTexture glTexture && glTexture.getGlId() > 0 ? glTexture.getGlId() : 0;
            }
         }
      }
   }

   private NameTagRenderer.NameTagRendererData resolve7() {
      Theme theme = WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null ? WildClient.INSTANCE.themeManager.getTheme() : Theme.WILD;
      boolean flag6 = NeumorphismRenderer.check13();
      int intValue20 = theme.getColor().getRGB() & 16777215;
      int intValue21 = this.compute14(intValue20, 1.18F);
      int intValue22 = this.compute13(intValue20, 16777215, 0.18F);
      int intValue23 = flag6
         ? this.compute13(theme.getColor3().getRGB() & 16777215, 16777215, 0.44F)
         : this.compute13(theme.getColor3().getRGB() & 16777215, 329224, 0.34F);
      int intValue24 = flag6
         ? this.compute13(theme.getColor2().getRGB() & 16777215, 15265269, 0.5F)
         : this.compute13(theme.getColor2().getRGB() & 16777215, 197638, 0.44F);
      int intValue25 = flag6 ? this.compute13(theme.getColor5().getRGB() & 16777215, 1120034, 0.72F) : theme.getColor5().getRGB() & 16777215;
      int intValue26 = flag6
         ? this.compute13(theme.getColor6().getRGB() & 16777215, 4147287, 0.62F)
         : this.compute13(theme.getColor6().getRGB() & 16777215, 15134199, 0.18F);
      int intValue27 = flag6
         ? this.compute13(theme.getColor4().getRGB() & 16777215, intValue21, 0.34F)
         : this.compute13(theme.getColor4().getRGB() & 16777215, intValue21, 0.18F);
      return new NameTagRenderer.NameTagRendererData(
         intValue21,
         intValue22,
         intValue23,
         intValue24,
         intValue27,
         intValue25,
         intValue26,
         flag6 ? 15594234 : 1514017,
         flag6 ? 16251647 : 1843241,
         flag6 ? 14213614 : 2501688,
         flag6 ? 13029857 : 2962497,
         13775174,
         16729440,
         6094796
      );
   }

   private int compute3(NameTagRenderer.NameTagRendererData nameTagRendererData9, NameTagRenderer.NameTagRendererBounds nameTagRendererBounds10) {
      if (nameTagRendererBounds10.state.flag7) {
         return this.compute13(nameTagRendererData9.accentTop, 12122111, 0.34F);
      } else if (nameTagRendererBounds10.focus > 0.02F) {
         return this.compute13(nameTagRendererData9.accentTop, 16777215, 0.24F);
      } else {
         return nameTagRendererBounds10.threat > 0.01F ? this.compute13(nameTagRendererData9.danger, nameTagRendererData9.accentTop, 0.26F) : nameTagRendererData9.rim;
      }
   }

   private int compute4(NameTagRenderer.NameTagRendererData nameTagRendererData10, NameTagRenderer.NameTagRendererBounds nameTagRendererBounds11) {
      if (nameTagRendererBounds11.state.flag7) {
         return this.compute13(9105407, nameTagRendererData10.accentTop, 0.44F);
      } else {
         return nameTagRendererBounds11.threat > 0.08F ? this.compute13(nameTagRendererData10.dangerGlow, nameTagRendererData10.danger, 0.36F) : nameTagRendererData10.accentTop;
      }
   }

   private int compute5(NameTagRenderer.NameTagRendererData nameTagRendererData11, NameTagRenderer.NameTagRendererBounds nameTagRendererBounds12) {
      if (nameTagRendererBounds12.state.flag7) {
         return this.compute13(14089215, nameTagRendererData11.accentBottom, 0.4F);
      } else {
         return nameTagRendererBounds12.threat > 0.08F ? this.compute13(16756920, nameTagRendererData11.dangerGlow, 0.46F) : nameTagRendererData11.accentBottom;
      }
   }

   private int compute6(NameTagRenderer.NameTagRendererData nameTagRendererData12, NameTagRenderer.NameTagRendererBounds nameTagRendererBounds13) {
      if (nameTagRendererBounds13.threat > 0.08F) {
         return nameTagRendererData12.dangerGlow;
      } else if (nameTagRendererBounds13.state.flag7) {
         return 9366527;
      } else {
         return nameTagRendererBounds13.focus > 0.1F ? this.compute13(nameTagRendererData12.accentTop, 16777215, 0.16F) : nameTagRendererData12.accentTop;
      }
   }

   private int compute7(NameTagRenderer.NameTagRendererData nameTagRendererData13, NameTagRenderer.NameTagRendererBounds nameTagRendererBounds14, EquipmentSlot equipmentSlot) {
      if (equipmentSlot == EquipmentSlot.MAINHAND || equipmentSlot == EquipmentSlot.OFFHAND) {
         return this.compute4(nameTagRendererData13, nameTagRendererBounds14);
      } else {
         return nameTagRendererBounds14.state.flag7
            ? this.compute13(10219519, nameTagRendererData13.accentTop, 0.36F)
            : this.compute13(nameTagRendererData13.rim, nameTagRendererData13.accentBottom, 0.24F);
      }
   }

   private int compute8(NameTagRenderer.NameTagRendererData nameTagRendererData14, NameTagRenderer.NameTagRendererBounds nameTagRendererBounds15) {
      if (nameTagRendererBounds15.state.flag7) {
         return this.compute13(nameTagRendererData14.textPrimary, 9433855, 0.34F);
      } else {
         return nameTagRendererBounds15.focus > 0.08F ? this.compute13(nameTagRendererData14.textPrimary, nameTagRendererData14.accentTop, 0.18F) : nameTagRendererData14.textPrimary;
      }
   }

   private int compute9(NameTagRenderer.NameTagRendererData nameTagRendererData15, NameTagRenderer.NameTagRendererBounds nameTagRendererBounds16) {
      return nameTagRendererBounds16.state.flag7
         ? this.compute13(nameTagRendererData15.accentTop, nameTagRendererData15.safeGlow, 0.32F)
         : this.compute11(nameTagRendererBounds16.health, this.compute13(12985918, nameTagRendererData15.danger, 0.45F), 15245893, 5427594);
   }

   private int compute10(NameTagRenderer.NameTagRendererData nameTagRendererData16, NameTagRenderer.NameTagRendererBounds nameTagRendererBounds17) {
      return nameTagRendererBounds17.state.flag7
         ? this.compute13(nameTagRendererData16.accentBottom, nameTagRendererData16.safeGlow, 0.28F)
         : this.compute11(nameTagRendererBounds17.health, 16743309, 16765559, 10944454);
   }

   private int compute11(float f, int i, int j, int k) {
      float floatValue124 = this.measure22(f);
      return floatValue124 < 0.5F ? this.compute13(i, j, floatValue124 * 2.0F) : this.compute13(j, k, (floatValue124 - 0.5F) * 2.0F);
   }

   private String resolve8(float f) {
      float floatValue125 = Math.round(f * 10.0F) / 10.0F;
      return !(floatValue125 >= 10.0F) && floatValue125 != (int)floatValue125 ? String.format(Locale.US, "%.1f HP", floatValue125) : Math.round(floatValue125) + " HP";
   }

   private String resolve9(String string, float f, FontObject fontObject, float g) {
      String text9 = this.resolve10(string);
      if (text9.isEmpty()) {
         return "";
      } else if (this.measure3(fontObject, text9, g) <= f) {
         return text9;
      } else {
         for (int intValue28 = text9.length() - 1; intValue28 > 0; intValue28--) {
            String text10 = text9.substring(0, intValue28).trim() + "...";
            if (this.measure3(fontObject, text10, g) <= f) {
               return text10;
            }
         }

         return "...";
      }
   }

   private String resolve10(String string) {
      if (string != null && !string.isEmpty()) {
         String text11 = string.replaceAll("(?i)§[0-9A-FK-OR]", "").replace('\n', ' ').replace('\r', ' ').replaceAll("\\p{Cntrl}", "").trim();

         while (text11.contains("  ")) {
            text11 = text11.replace("  ", " ");
         }

         return text11;
      } else {
         return "";
      }
   }

   private void invoke15(RenderManager renderManager10, FontObject fontObject2, String string, float f, float g, float h, int i, float j) {
      if (string != null && !string.isEmpty()) {
         renderManager10.invoke69(fontObject2, f + 1.0F, g + 1.0F, h, string, this.compute12(0, j));
         renderManager10.invoke69(fontObject2, f, g, h, string, i);
      }
   }

   private NameTagRenderer.NameTagRendererBounds2 resolve11(FontObject fontObject3, String string, float f) {
      FontRenderer.FontRendererState fontRendererState = RenderManager.resolve7(fontObject3, string, f);
      return new NameTagRenderer.NameTagRendererBounds2(fontRendererState.floatValue, fontRendererState.floatValue2);
   }

   private float measure3(FontObject fontObject4, String string, float f) {
      return RenderManager.resolve7(fontObject4, string, f).floatValue;
   }

   private float measure4(FontObject fontObject5, String string, float f) {
      return RenderManager.resolve7(fontObject5, string, f).floatValue2;
   }

   private float measure5(float f, float g, float h) {
      return f + (g - h) * 0.5F + h * 0.72F;
   }

   private float measure6(float f, float g) {
      return f + 29.0F * g + 7.0F * g + 7.2F * g;
   }

   private float measure7(NameTagRenderer.NameTagRendererBounds nameTagRendererBounds18) {
      float floatValue126 = this.measure16(0.08F, 0.88F, nameTagRendererBounds18.content);
      float floatValue127 = this.measure16(0.04F, 0.58F, nameTagRendererBounds18.appear);
      return this.measure22(Math.max(floatValue126, floatValue127 * 0.74F));
   }

   private float measure8(NameTagRenderer.NameTagRendererBounds nameTagRendererBounds19) {
      return this.measure9(nameTagRendererBounds19, this.measure7(nameTagRendererBounds19));
   }

   private float measure9(NameTagRenderer.NameTagRendererBounds nameTagRendererBounds20, float f) {
      return this.measure16(0.1F, 0.92F, nameTagRendererBounds20.detail) * (0.56F + 0.44F * f);
   }

   private float measure10(NameTagRenderer.NameTagRendererBounds nameTagRendererBounds21, NameTagRenderer.NameTagRendererState nameTagRendererState3) {
      float floatValue128 = this.measure7(nameTagRendererBounds21);

      return switch (nameTagRendererState3) {
         case DIRECT -> floatValue128;
         case GHOST -> this.measure22(0.18F + floatValue128 * 0.52F);
         case OVERLAY -> this.measure22(0.62F + floatValue128 * 0.38F);
      };
   }

   private float measure11(NameTagRenderer.NameTagRendererState2 nameTagRendererState214, float f, float g) {
      return !nameTagRendererState214.items.isEmpty() && !(f <= 0.02F) ? 26.0F * g * this.measure16(0.1F, 0.72F, f) : 0.0F;
   }

   private float measure12(NameTagRenderer.NameTagRendererBounds nameTagRendererBounds25) {
      return nameTagRendererBounds25.y - nameTagRendererBounds25.topExtension + 2.0F * nameTagRendererBounds25.scale;
   }

   private float measure13(int i) {
      return i > 12 ? 10.0F : 13.0F;
   }

   private List<NameTagRenderer.NameTagRendererBounds> resolve12(List<NameTagRenderer.NameTagRendererBounds> list, int i) {
      if (list.isEmpty()) {
         return list;
      } else {
         ArrayList arrayList5 = new ArrayList(list);
         arrayList5.sort(Comparator.comparingDouble(NameTagRenderer.NameTagRendererBounds::y).reversed().thenComparingDouble(NameTagRenderer.NameTagRendererBounds::distance));
         ArrayList arrayList6 = new ArrayList(arrayList5.size());

         for (NameTagRenderer.NameTagRendererBounds nameTagRendererBounds26 : (List<NameTagRenderer.NameTagRendererBounds>)arrayList5) {
            float floatValue129 = 14.0F * nameTagRendererBounds26.scale;
            float floatValue130 = nameTagRendererBounds26.y;

            boolean flag7;
            do {
               flag7 = false;

               for (NameTagRenderer.NameTagRendererBounds nameTagRendererBounds27 : (List<NameTagRenderer.NameTagRendererBounds>)arrayList6) {
                  if (this.check4(nameTagRendererBounds26, nameTagRendererBounds27)
                     && this.check5(nameTagRendererBounds26.x, nameTagRendererBounds26.width, nameTagRendererBounds27.x, nameTagRendererBounds27.width, floatValue129 * 0.45F)
                     && this.check6(
                        floatValue130 - nameTagRendererBounds26.topExtension,
                        nameTagRendererBounds26.height + nameTagRendererBounds26.topExtension,
                        nameTagRendererBounds27.y - nameTagRendererBounds27.topExtension,
                        nameTagRendererBounds27.height + nameTagRendererBounds27.topExtension,
                        floatValue129 * 0.25F
                     )) {
                     floatValue130 = nameTagRendererBounds27.y - nameTagRendererBounds27.topExtension - nameTagRendererBounds26.height - floatValue129;
                     flag7 = true;
                  }
               }
            } while (flag7);

            float floatValue131 = 8.0F + nameTagRendererBounds26.topExtension;
            float floatValue132 = Math.max(floatValue131, i - nameTagRendererBounds26.height - 8.0F);
            float floatValue133 = this.measure21(floatValue130, floatValue131, floatValue132);
            float floatValue134 = this.measure21(nameTagRendererBounds26.y + nameTagRendererBounds26.state.springValue11.measure(floatValue133 - nameTagRendererBounds26.y, SPRING_SPEC_10), floatValue131, floatValue132);
            arrayList6.add(nameTagRendererBounds26.withY(floatValue134));
         }

         return arrayList6;
      }
   }

   private boolean check4(NameTagRenderer.NameTagRendererBounds nameTagRendererBounds28, NameTagRenderer.NameTagRendererBounds nameTagRendererBounds29) {
      float floatValue135 = Math.abs(nameTagRendererBounds28.state.nameTagRendererData2.depth() - nameTagRendererBounds29.state.nameTagRendererData2.depth());
      double doubleValue3 = Math.min(nameTagRendererBounds28.distance(), nameTagRendererBounds29.distance());
      double doubleValue4 = Math.max(nameTagRendererBounds28.distance(), nameTagRendererBounds29.distance());
      double doubleValue5 = doubleValue4 / Math.max(0.001, doubleValue3);
      double doubleValue6 = doubleValue4 - doubleValue3;
      return floatValue135 <= 0.085F || doubleValue5 <= 1.32F || doubleValue6 <= 4.0;
   }

   private boolean check5(float f, float g, float h, float i, float j) {
      return f < h + i + j && f + g + j > h;
   }

   private boolean check6(float f, float g, float h, float i, float j) {
      return f < h + i + j && f + g + j > h;
   }

   private boolean check7(NameTagRenderer.NameTagRendererState2 nameTagRendererState215) {
      return !nameTagRendererState215.nameTagRendererDisplayEntry2.text.isEmpty() || !nameTagRendererState215.nameTagRendererDisplayEntry22.text.isEmpty();
   }

   private float measure14(List<NameTagRenderer.NameTagRendererItemData> list, float f, float g) {
      return list.isEmpty() ? 0.0F : list.size() * f + Math.max(0, list.size() - 1) * g;
   }

   private float measure15(float f, float g, float h) {
      return this.measure16(g, g + h, f);
   }

   private float measure16(float f, float g, float h) {
      float floatValue136 = this.measure22((h - f) / Math.max(1.0E-5F, g - f));
      return floatValue136 * floatValue136 * (3.0F - 2.0F * floatValue136);
   }

   private float measure17(float f, long l, int i) {
      float floatValue137 = this.measure22((0.2F - f) / 0.2F);
      if (floatValue137 <= 0.0F) {
         return 0.0F;
      } else {
         float floatValue138 = 0.5F + 0.5F * (float)Math.sin(this.measure19(l) * 9.4F + i * 0.173F);
         return floatValue137 * (0.58F + 0.42F * floatValue138);
      }
   }

   private float measure18(long l, long m) {
      if (l >= 0L && l < m) {
         float floatValue139 = 1.0F - (float)l / (float)m;
         return floatValue139 * floatValue139 * (3.0F - 2.0F * floatValue139);
      } else {
         return 0.0F;
      }
   }

   private float measure19(long l) {
      return (float)(l % 1000000L) / 1000.0F;
   }

   private String resolve13(int i) {
      return switch (Math.max(1, Math.min(10, i))) {
         case 1 -> "I";
         case 2 -> "II";
         case 3 -> "III";
         case 4 -> "IV";
         case 5 -> "V";
         case 6 -> "VI";
         case 7 -> "VII";
         case 8 -> "VIII";
         case 9 -> "IX";
         default -> "X";
      };
   }

   private int compute12(int i, float f) {
      int intValue29 = Math.max(0, Math.min(255, Math.round(this.measure22(f) * 255.0F)));
      return intValue29 << 24 | i & 16777215;
   }

   private int compute13(int i, int j, float f) {
      float floatValue140 = this.measure22(f);
      int intValue30 = Math.round((i >> 16 & 0xFF) + ((j >> 16 & 0xFF) - (i >> 16 & 0xFF)) * floatValue140);
      int intValue31 = Math.round((i >> 8 & 0xFF) + ((j >> 8 & 0xFF) - (i >> 8 & 0xFF)) * floatValue140);
      int intValue32 = Math.round((i & 0xFF) + ((j & 0xFF) - (i & 0xFF)) * floatValue140);
      return intValue30 << 16 | intValue31 << 8 | intValue32;
   }

   private int compute14(int i, float f) {
      Color color2 = new Color(i);
      int intValue33 = Math.max(0, Math.min(255, Math.round(color2.getRed() * f)));
      int intValue34 = Math.max(0, Math.min(255, Math.round(color2.getGreen() * f)));
      int intValue35 = Math.max(0, Math.min(255, Math.round(color2.getBlue() * f)));
      return intValue33 << 16 | intValue34 << 8 | intValue35;
   }

   private float measure20(float f, float g, float h) {
      return f + (g - f) * this.measure22(h);
   }

   private float measure21(float f, float g, float h) {
      return Math.max(g, Math.min(h, f));
   }

   private float measure22(float f) {
      return this.measure21(f, 0.0F, 1.0F);
   }

   private float measure23(float f) {
      return Math.round(f);
   }

   private float measure24(float f, float g) {
      return g <= 0.0F ? f : Math.round(f / g) * g;
   }

   static enum NameTagRendererState {
      DIRECT,
      GHOST,
      OVERLAY;
   }

   record NameTagRendererDisplayEntry(String label, int color, boolean harmful, int duration) {
   }

   record NameTagRendererItemData(ItemStack stack, EquipmentSlot slot) {
   }

   record NameTagRendererDisplayEntry2(String text, int color) {

      NameTagRendererDisplayEntry2(String text, int color) {
         text = Objects.requireNonNullElse(text, "");
         this.text = text;
         this.color = color;
      }
   }

   record NameTagRendererData(
      int accentTop,
      int accentBottom,
      int shellTop,
      int shellBottom,
      int rim,
      int textPrimary,
      int textSecondary,
      int avatarBackdrop,
      int slotFill,
      int barTrack,
      int divider,
      int danger,
      int dangerGlow,
      int safeGlow
   ) {
   }

   static final class NameTagRendererState2 {
      final UUID uUID;
      final int intValue;
      final SpringValue springValue = new SpringValue(0.0F);
      final SpringValue springValue2 = new SpringValue(0.0F);
      final SpringValue springValue3 = new SpringValue(0.0F);
      final SpringValue springValue4 = new SpringValue(0.0F);
      final SpringValue springValue5 = new SpringValue(92.0F);
      final SpringValue springValue6 = new SpringValue(29.0F);
      final SpringValue springValue7 = new SpringValue(1.0F);
      final SpringValue springValue8 = new SpringValue(1.0F);
      final SpringValue springValue9 = new SpringValue(0.0F);
      final SpringValue springValue10 = new SpringValue(0.0F);
      final SpringValue springValue11 = new SpringValue(0.0F);
      NameTagRenderer.NameTagRendererData2 nameTagRendererData2;
      boolean flag;
      boolean flag2;
      boolean flag3;
      boolean flag4;
      boolean flag5;
      boolean flag6;
      boolean flag7;
      boolean clientUser;
      long timestamp;
      long timestamp2;
      long timestamp3;
      long timestamp4 = Long.MIN_VALUE;
      int intValue2;
      float floatValue = Float.MAX_VALUE;
      float floatValue2 = 1.0F;
      float floatValue3 = 92.0F;
      float floatValue4 = 92.0F;
      float floatValue5 = 29.0F;
      float floatValue6 = 29.0F;
      float floatValue7;
      float floatValue8;
      float floatValue9;
      float floatValue10;
      String text = "";
      String text2 = "";
      int intValue3 = 16734824;
      String text20Hp = "20 HP";
      NameTagRenderer.NameTagRendererDisplayEntry2 nameTagRendererDisplayEntry2 = new NameTagRenderer.NameTagRendererDisplayEntry2("", 15133941);
      NameTagRenderer.NameTagRendererDisplayEntry2 nameTagRendererDisplayEntry22 = new NameTagRenderer.NameTagRendererDisplayEntry2("", 12371672);
      List<NameTagRenderer.NameTagRendererItemData> items = List.of();
      List<NameTagRenderer.NameTagRendererDisplayEntry> items2 = List.of();

      private NameTagRendererState2(UUID uUID) {
         this.uUID = uUID;
         this.intValue = uUID.hashCode();
      }

      void invoke() {
         this.flag2 = false;
         this.flag3 = false;
         this.flag4 = false;
         this.flag5 = false;
         this.flag6 = false;
      }

      boolean check(long l) {
         return !this.flag2
            && this.springValue.check(0.0F, NameTagRenderer.SPRING_SPEC)
            && this.springValue2.check(0.0F, NameTagRenderer.SPRING_SPEC_2)
            && this.springValue3.check(0.0F, NameTagRenderer.SPRING_SPEC_3)
            && this.springValue4.check(0.0F, NameTagRenderer.SPRING_SPEC_4)
            && l - this.timestamp > 180L;
      }
   }

   record NameTagRendererData2(float screenX, float screenY, double distance, float projectedHeight, float depth) {
   }

   record NameTagRendererBounds(
      NameTagRenderer.NameTagRendererState2 state,
      float x,
      float y,
      float width,
      float height,
      float scale,
      float topExtension,
      float alpha,
      float appear,
      float content,
      float detail,
      float itemReveal,
      float focus,
      float health,
      float damage,
      float threat,
      float exposure,
      boolean shader
   ) {

      public double distance() {
         return this.state.nameTagRendererData2.distance();
      }

      public NameTagRenderer.NameTagRendererBounds withY(float f) {
         return new NameTagRenderer.NameTagRendererBounds(
            this.state,
            this.x,
            f,
            this.width,
            this.height,
            this.scale,
            this.topExtension,
            this.alpha,
            this.appear,
            this.content,
            this.detail,
            this.itemReveal,
            this.focus,
            this.health,
            this.damage,
            this.threat,
            this.exposure,
            this.shader
         );
      }
   }

   record NameTagRendererBounds2(float width, float height) {
   }
}
