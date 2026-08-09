package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.consume.UseAction;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "UseTracker",
   category = Category.Misc,
   description = "Отслеживание тотемов/эффектов/расходников у игроков"
)
public class UseTracker extends Module {
   private static final String SNOS_TOTEMA = "Снос тотема";
   private static final String POLUCHENNYE_ZELYA = "Полученные зелья";
   private static final String SEDENNYE_PREDMETY = "Съеденные предметы";
   private static final int INT_VALUE = 31;
   private static final long TIMESTAMP = 500L;
   private final GroupSetting otslezhivat = new GroupSetting(
      "Отслеживать", new BooleanSetting("Снос тотема", true), new BooleanSetting("Полученные зелья", true), new BooleanSetting("Съеденные предметы", true)
   );
   private final Map<UUID, Map<String, StatusEffectInstance>> valuesByKey = new HashMap<>();
   private static final Map<UUID, Boolean> VALUES_BY_KEY = new HashMap<>();
   private final Map<UUID, ItemStack> valuesByKey2 = new HashMap<>();
   private final Map<UUID, Integer> valuesByKey3 = new HashMap<>();
   private final DualTimer dualTimer = new DualTimer();

   public UseTracker() {
      this.addSettings(new Setting[]{this.otslezhivat});
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player != null && CLIENT.world != null) {
         if (this.check5()) {
            this.invoke();
         } else {
            this.valuesByKey2.clear();
            this.valuesByKey3.clear();
         }

         if (!this.check4()) {
            this.valuesByKey.clear();
         } else if (this.dualTimer.check5(500L)) {
            this.dualTimer.invoke();
            this.invoke4();
         }
      } else {
         this.invoke9();
      }
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (packetEvent.getPacketEventState().equals(PacketEvent.PacketEventState.RECEIVE) && CLIENT.world != null && CLIENT.player != null) {
         if (packetEvent.getPacket() instanceof EntityStatusS2CPacket entityStatusS2CPacket2) {
            this.invoke2(entityStatusS2CPacket2);
         }

         if (packetEvent.getPacket() instanceof EntityStatusEffectS2CPacket entityStatusEffectS2CPacket2) {
            this.invoke3(entityStatusEffectS2CPacket2);
         }
      }
   }

   private void invoke() {
      HashSet hashSet = new HashSet();

      for (PlayerEntity playerEntity2 : CLIENT.world.getPlayers()) {
         if (playerEntity2 != null && playerEntity2.isAlive() && !this.check6(playerEntity2)) {
            UUID uuid = playerEntity2.getUuid();
            hashSet.add(uuid);
            if (playerEntity2.isUsingItem()) {
               this.valuesByKey2.computeIfAbsent(uuid, uUID -> playerEntity2.getActiveItem().copy());
               this.valuesByKey3.putIfAbsent(uuid, playerEntity2.age);
            } else {
               ItemStack itemStack2 = this.valuesByKey2.remove(uuid);
               Integer integerValue = this.valuesByKey3.remove(uuid);
               if (itemStack2 != null && !itemStack2.isEmpty() && integerValue != null && playerEntity2.age - integerValue >= 31) {
                  UseAction useAction = itemStack2.getUseAction();

                  String text = switch (useAction) {
                     case DRINK -> "выпил";
                     case EAT -> "съел";
                     default -> null;
                  };
                  if (text != null) {
                     String text2 = this.resolve5(itemStack2.getName().getString());
                     String text3 = this.resolve3(itemStack2);
                     String text4 = text3.isEmpty() ? "" : " §8(§7" + text3 + "§8)";
                     this.invoke8("§f" + playerEntity2.getName().getString() + "§7 " + text + " §f" + text2 + text4);
                  }
               }
            }
         }
      }

      this.valuesByKey2.keySet().removeIf(uUID -> !hashSet.contains(uUID));
      this.valuesByKey3.keySet().removeIf(uUID -> !hashSet.contains(uUID));
   }

   private void invoke2(EntityStatusS2CPacket entityStatusS2CPacket) {
      if (this.check3() && entityStatusS2CPacket.getStatus() == 35) {
         if (entityStatusS2CPacket.getEntity(CLIENT.world) instanceof PlayerEntity playerEntity3 && !this.check6(playerEntity3)) {
            boolean flag = EnchantmentHelper.hasEnchantments(playerEntity3.getOffHandStack())
               || EnchantmentHelper.hasEnchantments(playerEntity3.getMainHandStack())
               || playerEntity3.getOffHandStack().hasGlint()
               || playerEntity3.getMainHandStack().hasGlint();
            VALUES_BY_KEY.put(playerEntity3.getUuid(), flag);
            this.invoke8("§f" + playerEntity3.getName().getString() + "§7 потерял тотем бессмертия, зачарован: " + (flag ? "§a" : "§c") + "⬤");
         }
      }
   }

   private void invoke3(EntityStatusEffectS2CPacket entityStatusEffectS2CPacket) {
      if (this.check4()) {
         if (CLIENT.world.getEntityById(entityStatusEffectS2CPacket.getEntityId()) instanceof PlayerEntity playerEntity4) {
            UUID uuid2 = playerEntity4.getUuid();
            HashMap hashMap = new HashMap<>(this.valuesByKey.getOrDefault(uuid2, Map.of()));
            hashMap.putAll(this.resolve(playerEntity4));
            StatusEffectInstance statusEffectInstance3 = new StatusEffectInstance(
               entityStatusEffectS2CPacket.getEffectId(),
               entityStatusEffectS2CPacket.getDuration(),
               entityStatusEffectS2CPacket.getAmplifier(),
               entityStatusEffectS2CPacket.isAmbient(),
               entityStatusEffectS2CPacket.shouldShowParticles(),
               entityStatusEffectS2CPacket.shouldShowIcon()
            );
            String text5 = this.resolve2(entityStatusEffectS2CPacket.getEffectId(), entityStatusEffectS2CPacket.getAmplifier());
            hashMap.put(text5, statusEffectInstance3);
            HashSet hashSet2 = new HashSet();
            hashSet2.add(text5);
            this.invoke5(playerEntity4, hashMap, hashSet2);
            this.valuesByKey.put(uuid2, hashMap);
         }
      }
   }

   private void invoke4() {
      HashSet hashSet3 = new HashSet();

      for (PlayerEntity playerEntity5 : CLIENT.world.getPlayers()) {
         if (playerEntity5 != null && playerEntity5.isAlive()) {
            UUID uuid3 = playerEntity5.getUuid();
            hashSet3.add(uuid3);
            Map valuesByKey = this.valuesByKey.getOrDefault(uuid3, Map.of());
            Map valuesByKey2 = this.resolve(playerEntity5);
            HashSet hashSet4 = new HashSet();

            for (Entry entry : (Set<Entry>)valuesByKey2.entrySet()) {
               StatusEffectInstance statusEffectInstance4 = (StatusEffectInstance)valuesByKey.get(entry.getKey());
               if (statusEffectInstance4 == null || this.check(statusEffectInstance4, (StatusEffectInstance)entry.getValue())) {
                  hashSet4.add((String)entry.getKey());
               }
            }

            if (!hashSet4.isEmpty()) {
               this.invoke5(playerEntity5, valuesByKey2, hashSet4);
            }

            this.valuesByKey.put(uuid3, valuesByKey2);
         }
      }

      this.valuesByKey.keySet().removeIf(uUID -> !hashSet3.contains(uUID));
   }

   private boolean check(StatusEffectInstance statusEffectInstance, StatusEffectInstance statusEffectInstance2) {
      return statusEffectInstance.getAmplifier() != statusEffectInstance2.getAmplifier()
         ? true
         : statusEffectInstance2.getDuration() > statusEffectInstance.getDuration() + 20;
   }

   private void invoke5(PlayerEntity playerEntity, Map<String, StatusEffectInstance> map, Set<String> set) {
      ArrayList arrayList = new ArrayList();
      this.check2(map, set, arrayList, UseTracker.UseTrackerState.KILLER, "effect.minecraft.strength:3", "effect.minecraft.resistance:0");
      this.check2(map, set, arrayList, UseTracker.UseTrackerState.URINE, "effect.minecraft.jump_boost:0", "effect.minecraft.speed:2");
      this.check2(map, set, arrayList, UseTracker.UseTrackerState.MEDIC, "effect.minecraft.health_boost:2", "effect.minecraft.regeneration:2");
      this.check2(
         map,
         set,
         arrayList,
         UseTracker.UseTrackerState.BURP,
         "effect.minecraft.blindness:0",
         "effect.minecraft.glowing:0",
         "effect.minecraft.hunger:9",
         "effect.minecraft.slowness:2",
         "effect.minecraft.wither:4"
      );
      this.check2(map, set, arrayList, UseTracker.UseTrackerState.FLASH, "effect.minecraft.blindness:0", "effect.minecraft.glowing:0");
      this.check2(
         map,
         set,
         arrayList,
         UseTracker.UseTrackerState.SULFURIC_ACID,
         "effect.minecraft.poison:1",
         "effect.minecraft.slowness:3",
         "effect.minecraft.weakness:2",
         "effect.minecraft.wither:4"
      );
      this.check2(
         map,
         set,
         arrayList,
         UseTracker.UseTrackerState.WINNER,
         "effect.minecraft.health_boost:1",
         "effect.minecraft.invisibility:0",
         "effect.minecraft.regeneration:1",
         "effect.minecraft.resistance:0"
      );
      if (arrayList.isEmpty()) {
         for (String text6 : set) {
            StatusEffectInstance statusEffectInstance5 = (StatusEffectInstance)map.get(text6);
            if (statusEffectInstance5 != null) {
               this.invoke6(playerEntity, statusEffectInstance5);
            }
         }
      } else {
         for (UseTracker.UseTrackerState useTrackerState : (List<UseTracker.UseTrackerState>)arrayList) {
            this.invoke7(playerEntity, useTrackerState);
         }
      }
   }

   private void invoke6(PlayerEntity playerEntity, StatusEffectInstance statusEffectInstance) {
      String text7 = this.resolve5(Text.translatable(((StatusEffect)statusEffectInstance.getEffectType().value()).getTranslationKey()).getString());
      int intValue = Math.max(0, statusEffectInstance.getAmplifier()) + 1;
      String text8 = this.resolve4(statusEffectInstance);
      this.invoke8("§f" + playerEntity.getName().getString() + "§7 получил §f" + text7 + " " + intValue + "§7 на §f" + text8);
   }

   private boolean check2(Map<String, StatusEffectInstance> map, Set<String> set, List<UseTracker.UseTrackerState> list, UseTracker.UseTrackerState useTrackerState2, String... strings) {
      HashSet hashSet5 = new HashSet<>(Arrays.asList(strings));
      boolean flag2 = hashSet5.stream().allMatch(map::containsKey);
      boolean flag3 = hashSet5.stream().anyMatch(set::contains);
      if (flag2 && flag3) {
         list.add(useTrackerState2);
         set.removeAll(hashSet5);
         return true;
      } else {
         return false;
      }
   }

   private Map<String, StatusEffectInstance> resolve(PlayerEntity playerEntity) {
      HashMap hashMap2 = new HashMap();

      for (StatusEffectInstance statusEffectInstance6 : playerEntity.getStatusEffects()) {
         hashMap2.put(this.resolve2(statusEffectInstance6.getEffectType(), statusEffectInstance6.getAmplifier()), statusEffectInstance6);
      }

      return hashMap2;
   }

   private String resolve2(RegistryEntry<StatusEffect> registryEntry, int i) {
      return ((StatusEffect)registryEntry.value()).getTranslationKey() + ":" + i;
   }

   private void invoke7(PlayerEntity playerEntity, UseTracker.UseTrackerState useTrackerState3) {
      this.invoke8("§f" + playerEntity.getName().getString() + "§7 получил §f" + this.resolve5(useTrackerState3.text));
   }

   private String resolve3(ItemStack itemStack) {
      PotionContentsComponent potionContentsComponent = (PotionContentsComponent)itemStack.get(DataComponentTypes.POTION_CONTENTS);
      if (potionContentsComponent == null) {
         return "";
      } else {
         StringBuilder stringBuilder = new StringBuilder();

         for (StatusEffectInstance statusEffectInstance7 : potionContentsComponent.getEffects()) {
            if (!stringBuilder.isEmpty()) {
               stringBuilder.append("§8, §7");
            }

            String text9 = this.resolve5(Text.translatable(((StatusEffect)statusEffectInstance7.getEffectType().value()).getTranslationKey()).getString());
            int intValue2 = Math.max(0, statusEffectInstance7.getAmplifier()) + 1;
            stringBuilder.append(text9).append(" ").append(intValue2).append("§7 на §f").append(this.resolve4(statusEffectInstance7));
         }

         return stringBuilder.toString();
      }
   }

   private String resolve4(StatusEffectInstance statusEffectInstance) {
      if (statusEffectInstance.isInfinite()) {
         return "∞";
      } else {
         int intValue3 = statusEffectInstance.getDuration() / 20;
         int intValue4 = intValue3 / 60;
         intValue3 %= 60;
         return intValue4 > 0 ? intValue4 + " мин " + intValue3 + " сек" : intValue3 + " сек";
      }
   }

   private boolean check3() {
      return this.otslezhivat.isEnabled("Снос тотема");
   }

   private boolean check4() {
      return this.otslezhivat.isEnabled("Полученные зелья");
   }

   private boolean check5() {
      return this.otslezhivat.isEnabled("Съеденные предметы");
   }

   private boolean check6(PlayerEntity playerEntity) {
      return CLIENT.player != null && playerEntity.getUuid().equals(CLIENT.player.getUuid());
   }

   private String resolve5(String string) {
      return string == null ? "" : string.replaceAll("§[0-9a-fk-orA-FK-OR]", "");
   }

   private void invoke8(String string) {
      ChatUtil.sendClientMessage(string);
   }

   private void invoke9() {
      this.valuesByKey.clear();
      VALUES_BY_KEY.clear();
      this.valuesByKey2.clear();
      this.valuesByKey3.clear();
   }

   @Override
   public void onDisable() {
      this.invoke9();
      super.onDisable();
   }

   record UseTrackerData(RegistryEntry<StatusEffect> effect, int durationSeconds, int amplifier) {
      int durationTicks() {
         return this.durationSeconds * 20;
      }
   }

   static enum UseTrackerState {
      FLASH("§6[★] §eВспышка", List.of(new UseTracker.UseTrackerData(StatusEffects.BLINDNESS, 20, 0), new UseTracker.UseTrackerData(StatusEffects.GLOWING, 240, 0))),
      KILLER("§4[★] §cЗелье Киллера", List.of(new UseTracker.UseTrackerData(StatusEffects.RESISTANCE, 180, 0), new UseTracker.UseTrackerData(StatusEffects.STRENGTH, 90, 3))),
      BURP(
         "§c[★] §6Зелье Отрыжки",
         List.of(
            new UseTracker.UseTrackerData(StatusEffects.BLINDNESS, 10, 0),
            new UseTracker.UseTrackerData(StatusEffects.GLOWING, 180, 0),
            new UseTracker.UseTrackerData(StatusEffects.HUNGER, 90, 9),
            new UseTracker.UseTrackerData(StatusEffects.SLOWNESS, 180, 2),
            new UseTracker.UseTrackerData(StatusEffects.WITHER, 30, 4)
         )
      ),
      SULFURIC_ACID(
         "§2[★] §aСерная кислота",
         List.of(
            new UseTracker.UseTrackerData(StatusEffects.POISON, 50, 1),
            new UseTracker.UseTrackerData(StatusEffects.SLOWNESS, 90, 3),
            new UseTracker.UseTrackerData(StatusEffects.WEAKNESS, 90, 2),
            new UseTracker.UseTrackerData(StatusEffects.WITHER, 30, 4)
         )
      ),
      MEDIC("§5[★] §dЗелье Медика", List.of(new UseTracker.UseTrackerData(StatusEffects.HEALTH_BOOST, 45, 2), new UseTracker.UseTrackerData(StatusEffects.REGENERATION, 45, 2))),
      WINNER(
         "§2[★] §aЗелье Победителя",
         List.of(
            new UseTracker.UseTrackerData(StatusEffects.HEALTH_BOOST, 180, 1),
            new UseTracker.UseTrackerData(StatusEffects.INVISIBILITY, 900, 0),
            new UseTracker.UseTrackerData(StatusEffects.REGENERATION, 60, 1),
            new UseTracker.UseTrackerData(StatusEffects.RESISTANCE, 60, 0)
         )
      ),
      URINE("§3[★] §bМоча Флеша", List.of(new UseTracker.UseTrackerData(StatusEffects.JUMP_BOOST, 120, 1), new UseTracker.UseTrackerData(StatusEffects.SPEED, 120, 2)));

      final String text;
      private final List<UseTracker.UseTrackerData> items;

      private UseTrackerState(String string2, List<UseTracker.UseTrackerData> list) {
         this.text = string2;
         this.items = list;
      }
   }
}
