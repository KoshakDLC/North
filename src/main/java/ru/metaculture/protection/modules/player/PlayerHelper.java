package ru.metaculture.protection;

import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2IntMap.Entry;
import java.util.List;
import java.util.Locale;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.common.ResourcePackStatusC2SPacket;
import net.minecraft.network.packet.c2s.common.ResourcePackStatusC2SPacket.Status;
import net.minecraft.network.packet.c2s.play.CloseHandledScreenC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerActionC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractBlockC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractEntityC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerInteractItemC2SPacket;
import net.minecraft.network.packet.s2c.common.ResourcePackSendS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "PlayerHelper",
   category = Category.Player,
   description = "Полезные твики для игрока"
)
public class PlayerHelper extends Module {
   private static final String PLAYERHELPER_AUTOARMOR = "PlayerHelper_AutoArmor";
   public final ModeSetting rezhimResursPakov = new ModeSetting("Режим ресурс паков", "Load", "Load", "Skip", "Vanilla");
   public final BooleanSetting avtoRespavn = new BooleanSetting("Авто респавн", true);
   public final BooleanSetting skipResursPakov = new BooleanSetting("Скип ресурс паков", true);
   public final BooleanSetting pisatKoordinatySmerti = new BooleanSetting("Писать координаты смерти", false);
   public final BooleanSetting avtomaticheskiKushat = new BooleanSetting("Автоматически кушать", false);
   public final NumberSetting porogGoloda = new NumberSetting("Порог голода", 10.0F, 1.0F, 20.0F, 1.0F, false)
      .setVisibilityCondition(() -> !this.avtomaticheskiKushat.isEnabled());
   public final BooleanSetting otpravlyatKoordinaty = new BooleanSetting("Отправлять координаты", false);
   public final ModeSetting komuOtpravlyat = new ModeSetting("Кому отправлять: ", "СОО.Клановцам", "Друзьям", "Общий чат", "СОО.Клановцам")
      .setVisibilityCondition(() -> !this.otpravlyatKoordinaty.isEnabled());
   public final KeybindSetting bindNaOtpravku = new KeybindSetting("Бинд на отправку", -1).visibleWhen(() -> !this.otpravlyatKoordinaty.isEnabled());
   public final BooleanSetting neLomatPredmet = new BooleanSetting("Не ломать предмет", false);
   public final BooleanSetting avtomaticheskiChinit = new BooleanSetting("Автоматически чинить", false);
   public final NumberSetting porogProchnosti = new NumberSetting("Порог прочности", 100.0F, 1.0F, 500.0F, 1.0F, false)
      .setVisibilityCondition(() -> !this.avtomaticheskiChinit.isEnabled());
   public final BooleanSetting autoarmor = new BooleanSetting("AutoArmor", false);
   public final NumberSetting skorostNadevaniya = new NumberSetting("Скорость надевания", 150.0F, 50.0F, 1000.0F, 50.0F, false)
      .setVisibilityCondition(() -> !this.autoarmor.isEnabled());
   public final KeybindSetting bindZuma = new KeybindSetting("Бинд зума", -1, true);
   public final BooleanSetting priZahodeNaNovuyuAnarhiyuPisatEventDelay = new BooleanSetting("При заходе на новую анархию писать /event delay", true);
   public final BooleanSetting perezahodPriAfk = new BooleanSetting("Перезаход при афк", true);
   private int intValue = -1;
   public static boolean flag = false;
   public static boolean flag2 = false;
   private int intValue2 = -1;
   private float floatValue = 0.0F;
   public static boolean flag3 = false;
   public static float floatValue2 = 0.25F;
   private final DualTimer dualTimer = new DualTimer();
   private final DualTimer dualTimer2 = new DualTimer();
   private PlayerHelper.PlayerHelperData playerHelperData = null;
   private int intValue3 = 0;
   private int intValue4 = 0;
   private String nA = "N/A";
   private String nA2 = "N/A";
   private String nA3 = "N/A";
   private boolean flag4 = false;

   public PlayerHelper() {
      this.addSettings(
         new Setting[]{
            this.avtoRespavn,
            this.rezhimResursPakov,
            this.pisatKoordinatySmerti,
            this.avtomaticheskiKushat,
            this.porogGoloda,
            this.otpravlyatKoordinaty,
            this.komuOtpravlyat,
            this.bindNaOtpravku,
            this.neLomatPredmet,
            this.avtomaticheskiChinit,
            this.porogProchnosti,
            this.autoarmor,
            this.skorostNadevaniya,
            this.bindZuma,
            this.priZahodeNaNovuyuAnarhiyuPisatEventDelay,
            this.perezahodPriAfk
         }
      );
   }

   @Override
   public void loadFromJson(JsonObject jsonObject) {
      super.loadFromJson(jsonObject);
      if (jsonObject != null) {
         JsonObject jsonObject2 = null;

         try {
            jsonObject2 = jsonObject.getAsJsonObject("Settings");
         } catch (Throwable exception) {
         }

         if (jsonObject2 != null && !jsonObject2.has(this.rezhimResursPakov.name) && jsonObject2.has(this.skipResursPakov.name)) {
            try {
               boolean flag = jsonObject2.get(this.skipResursPakov.name).getAsBoolean();
               this.rezhimResursPakov.value = flag ? "Skip" : "Load";
               this.rezhimResursPakov.selectedIndex = this.rezhimResursPakov.options.indexOf(this.rezhimResursPakov.value);
            } catch (Throwable exception2) {
            }
         }
      }
   }

   public static boolean check() {
      return flag || flag2;
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.nA = "N/A";
      this.nA2 = "N/A";
      this.nA3 = "N/A";
      this.flag4 = false;
      this.dualTimer.invoke();
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (!ServerModeDetector.check() && CLIENT.player != null && CLIENT.world != null) {
         this.invoke4();
         this.invoke();
         this.invoke2();
         if (!(CLIENT.player.getHealth() <= 0.0F) && !(CLIENT.currentScreen instanceof DeathScreen)) {
            if (this.avtomaticheskiKushat.isEnabled()) {
               this.invoke12();
            }

            if (this.neLomatPredmet.isEnabled()) {
               this.invoke5();
            }

            if (this.avtomaticheskiChinit.isEnabled() && !flag) {
               this.invoke6();
            }

            if (this.autoarmor.isEnabled()) {
               this.invoke8();
            }
         } else {
            if (this.pisatKoordinatySmerti.isEnabled() && CLIENT.player.deathTime < 2) {
               CLIENT.player
                  .sendMessage(
                     Text.of(
                        String.format(
                           "§cDeathCoords: §fX: %d Y: %d Z: %d", (int)CLIENT.player.getX(), (int)CLIENT.player.getY(), (int)CLIENT.player.getZ()
                        )
                     ),
                     false
                  );
            }

            if (this.avtoRespavn.isEnabled()) {
               CLIENT.player.requestRespawn();
               CLIENT.setScreen(null);
            }

            this.invoke13();
            this.invoke7();
            this.invoke10();
         }
      }
   }

   @EventHandler
   public void onRawInput(RawInputEvent rawInputEvent) {
      if (CLIENT.currentScreen == null && CLIENT.player != null && rawInputEvent.getAction() == 1) {
         if (rawInputEvent.getKeyCode() == this.bindNaOtpravku.getKeyCode() && this.bindNaOtpravku.getKeyCode() != -1 && this.otpravlyatKoordinaty.isEnabled()) {
            this.invoke11();
         }
      }
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (CLIENT.player != null) {
         if (this.rezhimResursPakov.is("Skip") && packetEvent.getPacket() instanceof ResourcePackSendS2CPacket resourcePackSendS2CPacket) {
            CLIENT.getNetworkHandler().sendPacket(new ResourcePackStatusC2SPacket(resourcePackSendS2CPacket.id(), Status.ACCEPTED));
            CLIENT.getNetworkHandler().sendPacket(new ResourcePackStatusC2SPacket(resourcePackSendS2CPacket.id(), Status.SUCCESSFULLY_LOADED));
            packetEvent.invalidate();
         }

         if (this.perezahodPriAfk.isEnabled() && packetEvent.getPacket() instanceof GameMessageS2CPacket gameMessageS2CPacket) {
            String text = gameMessageS2CPacket.content().getString();
            if (this.check2(text)) {
               this.invoke3();
            }
         }

         if (this.neLomatPredmet.isEnabled()) {
            ItemStack itemStack2 = CLIENT.player.getMainHandStack();
            if (this.check5(itemStack2)
               && (
                  packetEvent.getPacket() instanceof PlayerActionC2SPacket
                     || packetEvent.getPacket() instanceof PlayerInteractBlockC2SPacket
                     || packetEvent.getPacket() instanceof PlayerInteractEntityC2SPacket
                     || packetEvent.getPacket() instanceof PlayerInteractItemC2SPacket
               )) {
               packetEvent.invalidate();
            }
         }
      }
   }

   private void invoke() {
      ServerStatsParser.INSTANCE.invoke(200L);
      String text2 = this.resolve(ServerStatsParser.INSTANCE.getNA2());
      if (this.check3(text2)) {
         boolean flag2 = !text2.equals(this.nA);
         this.nA = text2;
         if (this.priZahodeNaNovuyuAnarhiyuPisatEventDelay.isEnabled() && !text2.equals(this.nA2) && CLIENT.player.networkHandler != null) {
            CLIENT.player.networkHandler.sendChatCommand("event delay");
            this.nA2 = text2;
         }
      }
   }

   private void invoke2() {
      if (this.flag4 && CLIENT.player != null && CLIENT.player.networkHandler != null && !ServerStatsParser.check()) {
         if (this.dualTimer.check5(1000L)) {
            CLIENT.player.networkHandler.sendChatCommand("an" + this.nA3);
            this.flag4 = false;
            this.dualTimer.invoke();
         }
      }
   }

   private void invoke3() {
      if (!this.flag4 && CLIENT.player != null && CLIENT.player.networkHandler != null) {
         ServerStatsParser.INSTANCE.invoke2();
         String text3 = this.resolve(ServerStatsParser.INSTANCE.getNA2());
         this.nA3 = this.check3(text3) ? text3 : this.nA;
         if (this.check3(this.nA3)) {
            if (CLIENT.currentScreen != null) {
               CLIENT.player.closeScreen();
            }

            CLIENT.player.networkHandler.sendChatCommand("hub");
            this.flag4 = true;
            this.dualTimer.invoke();
         }
      }
   }

   private boolean check2(String string) {
      if (string == null) {
         return false;
      } else {
         String text4 = string.replaceAll("§.", "").toLowerCase(Locale.ROOT);
         return text4.contains("недоступна в режиме afk") || text4.contains("недопустимо нажимать в режиме afk");
      }
   }

   private String resolve(String string) {
      if (string == null) {
         return "N/A";
      } else {
         String text5 = string.replaceAll("\\D+", "");
         return text5.isEmpty() ? "N/A" : text5;
      }
   }

   private boolean check3(String string) {
      return string != null && !"N/A".equals(string) && !string.isBlank();
   }

   private boolean check4(KeybindSetting keybindSetting) {
      return keybindSetting != null && keybindSetting.getKeyCode() != -1;
   }

   private void invoke4() {
      if (this.bindZuma.getKeyCode() != -1) {
         boolean flag3 = KeybindSetting.isPressed(this.bindZuma.getKeyCode());
         if (flag3 && !flag3) {
            floatValue2 = 0.25F;
         }

         this.flag3 = flag3;
      } else {
         flag3 = false;
         floatValue2 = 0.25F;
      }
   }

   private void invoke5() {
      ItemStack itemStack3 = CLIENT.player.getMainHandStack();
      if (this.check5(itemStack3)) {
         CLIENT.options.attackKey.setPressed(false);
         CLIENT.options.useKey.setPressed(false);
      }
   }

   private boolean check5(ItemStack itemStack) {
      if (itemStack != null && itemStack.isDamageable()) {
         int intValue = itemStack.getMaxDamage();
         if (intValue <= 0) {
            return false;
         } else {
            int intValue2 = intValue - itemStack.getDamage();
            int intValue3 = intValue < 70 ? Math.max(1, (int)Math.ceil(intValue * 0.12)) : 70;
            return intValue2 <= intValue3;
         }
      } else {
         return false;
      }
   }

   private void invoke6() {
      if (CLIENT.currentScreen != null) {
         if (flag2) {
            this.invoke7();
         }
      } else {
         ItemStack itemStack4 = CLIENT.player.getMainHandStack();
         ItemStack itemStack5 = CLIENT.player.getOffHandStack();
         if (!flag2) {
            if (CLIENT.player.isUsingItem()) {
               return;
            }

            if (itemStack4.isDamageable() && itemStack4.getMaxDamage() - itemStack4.getDamage() <= this.porogProchnosti.getValue()) {
               if (this.compute() == -1) {
                  return;
               }

               flag2 = true;
               this.intValue2 = CLIENT.player.getInventory().getSelectedSlot();
               this.floatValue = CLIENT.player.getPitch();
               CLIENT.interactionManager
                  .clickSlot(CLIENT.player.playerScreenHandler.syncId, 45, this.intValue2, SlotActionType.SWAP, CLIENT.player);
               this.check6();
            }
         } else {
            CLIENT.player.setPitch(90.0F);
            if (itemStack5.isEmpty() || itemStack5.getDamage() == 0 || !itemStack5.isDamageable()) {
               this.invoke7();
               return;
            }

            if (CLIENT.player.getMainHandStack().getItem() != Items.EXPERIENCE_BOTTLE && !this.check6()) {
               this.invoke7();
               return;
            }

            CLIENT.options.useKey.setPressed(true);
            CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
         }
      }
   }

   private boolean check6() {
      int intValue4 = this.compute();
      if (intValue4 == -1) {
         return false;
      } else {
         if (intValue4 >= 36 && intValue4 <= 44) {
            CLIENT.player.getInventory().setSelectedSlot(intValue4 - 36);
         } else {
            CLIENT.interactionManager
               .clickSlot(
                  CLIENT.player.playerScreenHandler.syncId,
                  intValue4,
                  CLIENT.player.getInventory().getSelectedSlot(),
                  SlotActionType.SWAP,
                  CLIENT.player
               );
         }

         return true;
      }
   }

   private int compute() {
      for (int intValue5 = 9; intValue5 <= 44; intValue5++) {
         if (((Slot)CLIENT.player.playerScreenHandler.slots.get(intValue5)).getStack().getItem() == Items.EXPERIENCE_BOTTLE) {
            return intValue5;
         }
      }

      return -1;
   }

   private void invoke7() {
      if (flag2) {
         flag2 = false;
         CLIENT.options.useKey.setPressed(false);
         CLIENT.player.setPitch(this.floatValue);
         if (this.intValue2 != -1) {
            CLIENT.interactionManager
               .clickSlot(CLIENT.player.playerScreenHandler.syncId, 45, this.intValue2, SlotActionType.SWAP, CLIENT.player);
            CLIENT.player.getInventory().setSelectedSlot(this.intValue2);
            this.intValue2 = -1;
         }
      }
   }

   private void invoke8() {
      if (this.intValue3 > 0) {
         this.invoke9();
      } else if (CLIENT.interactionManager != null && !flag && !flag2 && !CLIENT.player.isUsingItem()) {
         if (this.dualTimer2.check5((long)this.skorostNadevaniya.getValue())) {
            PlayerHelper.PlayerHelperData playerHelperData = this.resolve2();
            if (playerHelperData != null) {
               this.playerHelperData = playerHelperData;
               this.intValue3 = 1;
               this.intValue4 = 0;
               this.invoke9();
            }
         }
      }
   }

   private void invoke9() {
      if (CLIENT.player != null && CLIENT.world != null && CLIENT.interactionManager != null && this.playerHelperData != null) {
         switch (this.intValue3) {
            case 1:
               InputUtils.getINSTANCE().invoke("PlayerHelper_AutoArmor");
               CLIENT.options.sprintKey.setPressed(false);
               CLIENT.player.setSprinting(false);
               this.intValue3 = 2;
               this.intValue4 = 1;
               break;
            case 2:
               if (this.intValue4-- > 0) {
                  return;
               }

               ItemStackUtils.invoke2(this.playerHelperData.sourceSlot(), this.playerHelperData.armorSlotId());
               CLIENT.player.networkHandler.sendPacket(new CloseHandledScreenC2SPacket(CLIENT.player.playerScreenHandler.syncId));
               this.dualTimer2.invoke();
               this.intValue3 = 3;
               this.intValue4 = 1;
               break;
            case 3:
               if (this.intValue4-- > 0) {
                  return;
               }

               this.invoke10();
               break;
            default:
               this.invoke10();
         }
      } else {
         this.invoke10();
      }
   }

   private void invoke10() {
      if (this.intValue3 > 0) {
         InputUtils.getINSTANCE().invoke2("PlayerHelper_AutoArmor");
      }

      this.playerHelperData = null;
      this.intValue3 = 0;
      this.intValue4 = 0;
   }

   private PlayerHelper.PlayerHelperData resolve2() {
      Object object = null;
      object = this.resolve3((PlayerHelper.PlayerHelperData)object, this.resolve4(EquipmentSlot.HEAD, 5));
      object = this.resolve3((PlayerHelper.PlayerHelperData)object, this.resolve4(EquipmentSlot.CHEST, 6));
      object = this.resolve3((PlayerHelper.PlayerHelperData)object, this.resolve4(EquipmentSlot.LEGS, 7));
      return this.resolve3((PlayerHelper.PlayerHelperData)object, this.resolve4(EquipmentSlot.FEET, 8));
   }

   private PlayerHelper.PlayerHelperData resolve3(PlayerHelper.PlayerHelperData playerHelperData2, PlayerHelper.PlayerHelperData playerHelperData3) {
      if (playerHelperData3 == null) {
         return playerHelperData2;
      } else if (playerHelperData2 == null) {
         return playerHelperData3;
      } else {
         return playerHelperData3.improvement() > playerHelperData2.improvement() ? playerHelperData3 : playerHelperData2;
      }
   }

   private PlayerHelper.PlayerHelperData resolve4(EquipmentSlot equipmentSlot, int i) {
      ItemStack itemStack6 = CLIENT.player.getEquippedStack(equipmentSlot);
      int intValue6 = this.compute2(itemStack6, equipmentSlot);
      int intValue7 = -1;
      int intValue8 = intValue6;

      for (int intValue9 = 0; intValue9 < 36; intValue9++) {
         ItemStack itemStack7 = CLIENT.player.getInventory().getStack(intValue9);
         int intValue10 = this.compute2(itemStack7, equipmentSlot);
         if (intValue10 > intValue8) {
            intValue8 = intValue10;
            intValue7 = intValue9 < 9 ? intValue9 + 36 : intValue9;
         }
      }

      return intValue7 == -1 ? null : new PlayerHelper.PlayerHelperData(intValue7, i, intValue8 - intValue6);
   }

   private int compute2(ItemStack itemStack, EquipmentSlot equipmentSlot) {
      if (itemStack != null && !itemStack.isEmpty() && this.resolve5(itemStack.getItem()) == equipmentSlot) {
         int intValue11 = this.compute3(itemStack.getItem()) * 10000;
         ItemEnchantmentsComponent itemEnchantmentsComponent = (ItemEnchantmentsComponent)itemStack.get(DataComponentTypes.ENCHANTMENTS);
         if (itemEnchantmentsComponent != null && !itemEnchantmentsComponent.isEmpty()) {
            for (Entry entry : itemEnchantmentsComponent.getEnchantmentEntries()) {
               intValue11 += entry.getIntValue() * 100;
            }
         }

         if (itemStack.isDamageable()) {
            intValue11 += Math.max(0, itemStack.getMaxDamage() - itemStack.getDamage()) * 100 / Math.max(1, itemStack.getMaxDamage());
         }

         return intValue11;
      } else {
         return -1;
      }
   }

   private EquipmentSlot resolve5(Item item) {
      if (item == Items.NETHERITE_HELMET
         || item == Items.DIAMOND_HELMET
         || item == Items.IRON_HELMET
         || item == Items.CHAINMAIL_HELMET
         || item == Items.GOLDEN_HELMET
         || item == Items.LEATHER_HELMET
         || item == Items.TURTLE_HELMET) {
         return EquipmentSlot.HEAD;
      } else if (item == Items.NETHERITE_CHESTPLATE
         || item == Items.DIAMOND_CHESTPLATE
         || item == Items.IRON_CHESTPLATE
         || item == Items.CHAINMAIL_CHESTPLATE
         || item == Items.GOLDEN_CHESTPLATE
         || item == Items.LEATHER_CHESTPLATE) {
         return EquipmentSlot.CHEST;
      } else if (item == Items.NETHERITE_LEGGINGS
         || item == Items.DIAMOND_LEGGINGS
         || item == Items.IRON_LEGGINGS
         || item == Items.CHAINMAIL_LEGGINGS
         || item == Items.GOLDEN_LEGGINGS
         || item == Items.LEATHER_LEGGINGS) {
         return EquipmentSlot.LEGS;
      } else {
         return item != Items.NETHERITE_BOOTS
               && item != Items.DIAMOND_BOOTS
               && item != Items.IRON_BOOTS
               && item != Items.CHAINMAIL_BOOTS
               && item != Items.GOLDEN_BOOTS
               && item != Items.LEATHER_BOOTS
            ? null
            : EquipmentSlot.FEET;
      }
   }

   private int compute3(Item item) {
      if (item == Items.NETHERITE_HELMET || item == Items.NETHERITE_CHESTPLATE || item == Items.NETHERITE_LEGGINGS || item == Items.NETHERITE_BOOTS) {
         return 6;
      } else if (item == Items.DIAMOND_HELMET || item == Items.DIAMOND_CHESTPLATE || item == Items.DIAMOND_LEGGINGS || item == Items.DIAMOND_BOOTS) {
         return 5;
      } else if (item == Items.IRON_HELMET || item == Items.IRON_CHESTPLATE || item == Items.IRON_LEGGINGS || item == Items.IRON_BOOTS) {
         return 4;
      } else if (item == Items.CHAINMAIL_HELMET || item == Items.CHAINMAIL_CHESTPLATE || item == Items.CHAINMAIL_LEGGINGS || item == Items.CHAINMAIL_BOOTS) {
         return 3;
      } else if (item == Items.GOLDEN_HELMET || item == Items.GOLDEN_CHESTPLATE || item == Items.GOLDEN_LEGGINGS || item == Items.GOLDEN_BOOTS) {
         return 2;
      } else if (item == Items.LEATHER_HELMET || item == Items.LEATHER_CHESTPLATE || item == Items.LEATHER_LEGGINGS || item == Items.LEATHER_BOOTS) {
         return 1;
      } else {
         return item == Items.TURTLE_HELMET ? 2 : 0;
      }
   }

   private void invoke11() {
      int intValue12 = (int)CLIENT.player.getX();
      int intValue13 = (int)CLIENT.player.getY();
      int intValue14 = (int)CLIENT.player.getZ();
      String text6 = String.format(" %d %d %d", intValue12, intValue13, intValue14);
      String text7 = this.komuOtpravlyat.getValue();
      switch (text7) {
         case "Общий чат":
            CLIENT.getNetworkHandler().sendChatMessage("! Мои координаты:" + text6);
            break;
         case "Друзья":
            List items = FriendCommand.resolve();
            if (items.isEmpty()) {
               CLIENT.player.sendMessage(Text.of("§cСписок друзей пуст!"), true);
               return;
            }

            for (String text8 : (List<String>)items) {
               CLIENT.getNetworkHandler().sendChatMessage("/msg " + text8 + " Мои координаты:" + text6);
            }

            CLIENT.player.sendMessage(Text.of("§aКоординаты отправлены друзьям."), true);
            break;
         case "СОО.Клановцам":
            CLIENT.getNetworkHandler().sendChatMessage("/clan chat" + text6);
      }
   }

   private void invoke12() {
      if (CLIENT.currentScreen != null) {
         if (flag) {
            this.invoke13();
         }
      } else if (CLIENT.player.getHungerManager().getFoodLevel() >= this.porogGoloda.getValue()) {
         if (flag) {
            this.invoke13();
         }
      } else if (flag || !CLIENT.player.isUsingItem()) {
         int intValue15 = this.compute4();
         if (intValue15 != -1 && !flag) {
            this.intValue = CLIENT.player.getInventory().getSelectedSlot();
            CLIENT.player.getInventory().setSelectedSlot(intValue15);
            CLIENT.options.useKey.setPressed(true);
            if (CLIENT.interactionManager != null) {
               CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
            }

            flag = true;
         }
      }
   }

   private void invoke13() {
      if (flag) {
         CLIENT.options.useKey.setPressed(false);
         if (this.intValue != -1 && CLIENT.player != null) {
            CLIENT.player.getInventory().setSelectedSlot(this.intValue);
            this.intValue = -1;
         }

         flag = false;
      }
   }

   private int compute4() {
      for (int intValue16 = 0; intValue16 < 9; intValue16++) {
         if (CLIENT.player.getInventory().getStack(intValue16).contains(DataComponentTypes.FOOD)) {
            return intValue16;
         }
      }

      return -1;
   }

   @Override
   public void onDisable() {
      this.invoke13();
      this.invoke7();
      this.invoke10();
      flag3 = false;
      floatValue2 = 0.25F;
      this.flag4 = false;
      super.onDisable();
   }

   record PlayerHelperData(int sourceSlot, int armorSlotId, int improvement) {
   }
}
