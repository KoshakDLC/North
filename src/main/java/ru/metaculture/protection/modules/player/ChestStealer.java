package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.Random;
import net.minecraft.client.gui.screen.ingame.Generic3x3ContainerScreen;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.screen.Generic3x3ContainerScreenHandler;
import net.minecraft.screen.GenericContainerScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.math.Box;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "ChestStealer",
   description = "Лутает предметы с сундуков",
   category = Category.Player
)
public class ChestStealer extends Module {
   public static BooleanSetting ubiratIgrokov = new BooleanSetting("Убирать игроков", false);
   public final ModeSetting rezhimRaboty = new ModeSetting("Режим работы", "Обычный", "Обычный", "FunTime Event");
   private static final int INT_VALUE = 9;
   private static final double DOUBLE_VALUE = 0.5;
   private static final double DOUBLE_VALUE_2 = 1.0;
   private final Random random = new Random();

   public ChestStealer() {
      this.addSettings(new Setting[]{ubiratIgrokov, this.rezhimRaboty});
   }

   @EventHandler
   public void onRender3D(Render3DEvent render3DEvent) {
      if (!ServerBlockUtils.check()) {
         if (ubiratIgrokov.isEnabled()) {
            this.invoke2(0.5);

            for (Entity entity : CLIENT.world.getEntities()) {
               if (entity instanceof PlayerEntity playerEntity && playerEntity != CLIENT.player) {
                  double doubleValue = entity.getX();
                  double doubleValue2 = entity.getY();
                  double doubleValue3 = entity.getZ();
                  entity.setBoundingBox(new Box(doubleValue - 1.0E-5, doubleValue2, doubleValue3 - 1.0E-5, doubleValue + 1.0E-5, doubleValue2 + entity.getHeight(), doubleValue3 + 1.0E-5));
               }
            }
         } else {
            this.invoke2(1.0);
         }
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.currentScreen instanceof GenericContainerScreen genericContainerScreen) {
         GenericContainerScreenHandler genericContainerScreenHandler = (GenericContainerScreenHandler)genericContainerScreen.getScreenHandler();
         if (genericContainerScreenHandler instanceof GenericContainerScreenHandler) {
            this.invoke(genericContainerScreen.getTitle().getString(), genericContainerScreenHandler, genericContainerScreenHandler.getRows() * 9);
         }
      } else if (CLIENT.currentScreen instanceof Generic3x3ContainerScreen generic3x3ContainerScreen) {
         Generic3x3ContainerScreenHandler generic3x3ContainerScreenHandler = (Generic3x3ContainerScreenHandler)generic3x3ContainerScreen.getScreenHandler();
         if (generic3x3ContainerScreenHandler instanceof Generic3x3ContainerScreenHandler) {
            this.invoke(generic3x3ContainerScreen.getTitle().getString(), generic3x3ContainerScreenHandler, 9);
         }
      }
   }

   private void invoke(String string, ScreenHandler screenHandler, int i) {
      ArrayList arrayList = new ArrayList();
      String text = this.rezhimRaboty.getValue();
      switch (text) {
         case "Обычный":
            for (int intValue = 0; intValue < i; intValue++) {
               if (!screenHandler.getSlot(intValue).getStack().isEmpty()) {
                  arrayList.add(intValue);
               }
            }
            break;
         case "FunTime Event":
            for (int intValue2 = 0; intValue2 < i; intValue2++) {
               ItemStack itemStack = screenHandler.getSlot(intValue2).getStack();
               if (!itemStack.isEmpty()) {
                  Item item = itemStack.getItem();
                  if (item == Items.NAUTILUS_SHELL || item == Items.GUNPOWDER || item == Items.WHITE_DYE || item == Items.LIGHT_GRAY_DYE) {
                     arrayList.add(intValue2);
                  }
               }
            }
            break;
         case "FunTime AIRDrop":
            if (this.check(string)) {
               boolean flag = false;

               for (int intValue3 = 0; intValue3 < i; intValue3++) {
                  ItemStack itemStack2 = screenHandler.getSlot(intValue3).getStack();
                  if (itemStack2.getItem() == Items.BLAZE_POWDER && itemStack2.getName().getString().contains("[★] Предмет еще не остыл")) {
                     flag = true;
                     break;
                  }
               }

               if (!flag) {
                  for (int intValue4 = 0; intValue4 < i; intValue4++) {
                     if (!screenHandler.getSlot(intValue4).getStack().isEmpty()) {
                        arrayList.add(intValue4);
                     }
                  }
               }
            }
      }

      if (!arrayList.isEmpty()) {
         int intValue5 = (Integer)arrayList.get(this.random.nextInt(arrayList.size()));
         CLIENT.interactionManager.clickSlot(screenHandler.syncId, intValue5, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
      }
   }

   private boolean check(String string) {
      String text2 = string.toLowerCase().replaceAll("§.", "").trim();
      return text2.equals("бочка")
         || text2.equals("раздатчик")
         || text2.equals("dispenser")
         || text2.equals("barrel")
         || text2.equals("аир-дроп")
         || text2.equals("аир дроп")
         || text2.equals("air-drop")
         || text2.equals("air drop")
         || text2.equals("airdrop");
   }

   private void invoke2(double d) {
      double doubleValue4 = (Double)CLIENT.options.getEntityDistanceScaling().getValue();
      if (Math.abs(doubleValue4 - d) > 0.001) {
         CLIENT.options.getEntityDistanceScaling().setValue(d);
      }
   }

   @Override
   public void onDisable() {
      super.onDisable();
      this.invoke2(1.0);
   }
}
