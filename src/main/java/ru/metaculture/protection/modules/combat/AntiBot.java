package ru.metaculture.protection;

import com.mojang.authlib.GameProfile;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRemoveS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket.Action;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket.Entry;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "AntiBot",
   category = Category.Combat,
   description = "Убирает бота позади вас",
   riskLevels = {ModuleRiskLevel.MATRIX, ModuleRiskLevel.GRIM}
)
public class AntiBot extends Module {
   public final ModeSetting rezhim = new ModeSetting("Режим: ", "ALL", "ReallyWorld", "Matrix", "ALL");
   public static final Set<UUID> VALUES = ConcurrentHashMap.newKeySet();
   private static final Set<UUID> VALUES_2 = ConcurrentHashMap.newKeySet();
   private final Set<UUID> values = ConcurrentHashMap.newKeySet();
   private final Map<UUID, List<ItemStack>> valuesByKey = new ConcurrentHashMap<>();
   public static boolean flag = false;

   public AntiBot() {
      this.addSettings(new Setting[]{this.rezhim});
   }

   @Override
   public void onEnable() {
      super.onEnable();
      flag = true;
   }

   @Override
   public void onDisable() {
      flag = false;
      VALUES.clear();
      VALUES_2.clear();
      this.values.clear();
      this.valuesByKey.clear();
      super.onDisable();
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.world != null && CLIENT.player != null) {
         this.invoke6();
         String text = this.rezhim.getValue();
         if (!text.equals("ReallyWorld") && !this.values.isEmpty()) {
            CLIENT.world
               .getPlayers()
               .stream()
               .filter(abstractClientPlayerEntity -> this.values.contains(abstractClientPlayerEntity.getUuid()))
               .forEach(this::invoke3);
         }

         if (text.equals("Matrix") || text.equals("ALL")) {
            this.invoke4();
         }

         if (text.equals("ReallyWorld") || text.equals("ALL")) {
            this.invoke5();
         }
      }
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (packetEvent.getPacket() instanceof PlayerListS2CPacket playerListS2CPacket2) {
         this.invoke(playerListS2CPacket2);
      } else if (packetEvent.getPacket() instanceof PlayerRemoveS2CPacket playerRemoveS2CPacket2) {
         this.invoke2(playerRemoveS2CPacket2);
      }
   }

   private void invoke(PlayerListS2CPacket playerListS2CPacket) {
      if (playerListS2CPacket.getActions().contains(Action.ADD_PLAYER)) {
         playerListS2CPacket.getEntries().forEach(entry -> {
            GameProfile gameProfile2 = entry.profile();
            if (gameProfile2 != null) {
               if (this.check(entry, gameProfile2)) {
                  this.invoke7(gameProfile2.getId());
               } else {
                  UUID uuid = gameProfile2.getId();
                  if (this.check2(gameProfile2)) {
                     VALUES.add(uuid);
                  } else {
                     this.values.add(uuid);
                  }
               }
            }
         });
      }
   }

   private void invoke2(PlayerRemoveS2CPacket playerRemoveS2CPacket) {
      for (UUID uuid2 : playerRemoveS2CPacket.profileIds()) {
         this.values.remove(uuid2);
         VALUES.remove(uuid2);
         VALUES_2.remove(uuid2);
         this.valuesByKey.remove(uuid2);
      }
   }

   private boolean check(Entry entry, GameProfile gameProfile) {
      return CLIENT.player == null ? false : gameProfile.getId().equals(CLIENT.player.getUuid()) || entry.latency() > 0;
   }

   private boolean check2(GameProfile gameProfile) {
      return CLIENT.world == null
         ? false
         : CLIENT.world
            .getPlayers()
            .stream()
            .anyMatch(
               abstractClientPlayerEntity -> abstractClientPlayerEntity.getGameProfile().getName().equals(gameProfile.getName())
                  && !abstractClientPlayerEntity.getUuid().equals(gameProfile.getId())
            );
   }

   private List<ItemStack> resolve(PlayerEntity playerEntity) {
      ArrayList arrayList = new ArrayList(4);
      arrayList.add(playerEntity.getEquippedStack(EquipmentSlot.HEAD));
      arrayList.add(playerEntity.getEquippedStack(EquipmentSlot.CHEST));
      arrayList.add(playerEntity.getEquippedStack(EquipmentSlot.LEGS));
      arrayList.add(playerEntity.getEquippedStack(EquipmentSlot.FEET));
      return arrayList;
   }

   private void invoke3(PlayerEntity playerEntity) {
      if (check6(playerEntity)) {
         this.values.remove(playerEntity.getUuid());
         this.valuesByKey.remove(playerEntity.getUuid());
      } else {
         List items = this.resolve(playerEntity);
         List items2 = this.valuesByKey.get(playerEntity.getUuid());
         if (!this.check3((List<ItemStack>)items) && !this.check4(items, items2)) {
            this.valuesByKey.put(playerEntity.getUuid(), items);
         } else {
            VALUES.add(playerEntity.getUuid());
            this.values.remove(playerEntity.getUuid());
         }
      }
   }

   private void invoke4() {
      if (CLIENT.world != null && CLIENT.player != null) {
         Iterator iterator = this.values.iterator();

         while (true) {
            while (true) {
               if (!iterator.hasNext()) {
                  if (CLIENT.player.age % 100 == 0) {
                     VALUES.removeIf(uUID -> CLIENT.world.getPlayerByUuid(uUID) == null);
                  }

                  return;
               }

               UUID uuid3 = (UUID)iterator.next();
               PlayerEntity playerEntity2 = CLIENT.world.getPlayerByUuid(uuid3);
               if (playerEntity2 == null) {
                  break;
               }

               if (!check6(playerEntity2)) {
                  String text2 = playerEntity2.getName().getString();
                  boolean flag = text2.startsWith("CIT-") && !text2.contains("NPC") && !text2.contains("[ZNPC]");
                  int intValue = 0;

                  for (ItemStack itemStack : this.resolve(playerEntity2)) {
                     if (itemStack != null && !itemStack.isEmpty()) {
                        intValue++;
                     }
                  }

                  boolean flag2 = intValue == 4;
                  boolean flag3 = !playerEntity2.getUuid().equals(UUID.nameUUIDFromBytes(("OfflinePlayer:" + text2).getBytes()));
                  if (flag2 || flag || flag3) {
                     VALUES.add(uuid3);
                  }
                  break;
               }
            }

            iterator.remove();
         }
      }
   }

   private void invoke5() {
      if (CLIENT.world != null && CLIENT.player != null) {
         for (PlayerEntity playerEntity3 : CLIENT.world.getPlayers()) {
            if (playerEntity3 != CLIENT.player && !check6(playerEntity3)) {
               String text3 = playerEntity3.getName().getString();
               boolean flag4 = !playerEntity3.getUuid().equals(UUID.nameUUIDFromBytes(("OfflinePlayer:" + text3).getBytes()));
               boolean flag5 = text3.contains("NPC") || text3.startsWith("[ZNPC]");
               if (flag4 && !flag5) {
                  VALUES.add(playerEntity3.getUuid());
               }
            }
         }
      }
   }

   private boolean check3(List<ItemStack> list) {
      for (ItemStack itemStack2 : list) {
         if (itemStack2 == null || itemStack2.isEmpty() || itemStack2.hasEnchantments()) {
            return false;
         }
      }

      return true;
   }

   private boolean check4(List<ItemStack> list, List<ItemStack> list2) {
      if (list2 == null) {
         return false;
      } else {
         for (int intValue2 = 0; intValue2 < 4; intValue2++) {
            ItemStack itemStack3 = (ItemStack)list.get(intValue2);
            ItemStack itemStack4 = (ItemStack)list2.get(intValue2);
            if (itemStack3 != null && itemStack4 != null) {
               if (itemStack3.getItem() != itemStack4.getItem()) {
                  return true;
               }
            } else if (itemStack3 != itemStack4) {
               return true;
            }
         }

         return false;
      }
   }

   private void invoke6() {
      if (CLIENT.getNetworkHandler() != null) {
         for (PlayerListEntry playerListEntry : CLIENT.getNetworkHandler().getPlayerList()) {
            if (playerListEntry != null && playerListEntry.getProfile() != null && this.check5(playerListEntry.getProfile().getId(), playerListEntry.getLatency())) {
               this.invoke7(playerListEntry.getProfile().getId());
            }
         }

         VALUES_2.removeIf(
            uUID -> CLIENT.getNetworkHandler().getPlayerListEntry(uUID) == null
               && (CLIENT.world == null || CLIENT.world.getPlayerByUuid(uUID) == null)
         );
      }
   }

   private boolean check5(UUID uUID, int i) {
      return CLIENT.player != null && uUID.equals(CLIENT.player.getUuid()) || i > 0;
   }

   private void invoke7(UUID uUID) {
      invoke8(uUID);
      this.values.remove(uUID);
      this.valuesByKey.remove(uUID);
   }

   private static void invoke8(UUID uUID) {
      if (uUID != null) {
         VALUES_2.add(uUID);
         VALUES.remove(uUID);
      }
   }

   private static boolean check6(PlayerEntity playerEntity) {
      if (playerEntity == null) {
         return false;
      } else {
         UUID uuid4 = playerEntity.getUuid();
         if (VALUES_2.contains(uuid4)) {
            return true;
         } else if (CLIENT.player != null && uuid4.equals(CLIENT.player.getUuid())) {
            invoke8(uuid4);
            return true;
         } else if (CLIENT.getNetworkHandler() == null) {
            return false;
         } else {
            PlayerListEntry playerListEntry2 = CLIENT.getNetworkHandler().getPlayerListEntry(uuid4);
            if (playerListEntry2 != null && playerListEntry2.getLatency() > 0) {
               invoke8(uuid4);
               return true;
            } else {
               return false;
            }
         }
      }
   }

   public static boolean check7(PlayerEntity playerEntity) {
      if (!flag) {
         return false;
      } else if (check6(playerEntity)) {
         return false;
      } else if (VALUES.contains(playerEntity.getUuid())) {
         return true;
      } else {
         String text4 = playerEntity.getName().getString();
         if (text4.startsWith("CIT-") && !text4.contains("NPC") && !text4.startsWith("[ZNPC]")) {
            return true;
         } else {
            return playerEntity.isInvisible() && !text4.contains("NPC") && !text4.startsWith("[ZNPC]")
               ? !playerEntity.getUuid().equals(UUID.nameUUIDFromBytes(("OfflinePlayer:" + text4).getBytes()))
               : false;
         }
      }
   }
}
