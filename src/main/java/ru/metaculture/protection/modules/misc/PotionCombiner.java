package ru.metaculture.protection;

import java.util.Random;
import java.util.function.Predicate;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.PotionContentsComponent;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.packet.c2s.play.RenameItemC2SPacket;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.AnvilScreenHandler;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult.Type;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.RaycastContext.FluidHandling;
import net.minecraft.world.RaycastContext.ShapeType;
import org.wild.mixin.acceser.ClientPlayerInteractionManagerAccessor;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "PotionCombiner",
   category = Category.Misc,
   description = "Автоматически объединяет зелья в наковальне"
)
public class PotionCombiner extends Module {
   private static final String SILA = "Сила";
   private static final String SKOROST = "Скорость";
   private static final String SKOROST3_SILA3 = "Скорость 3 + Сила 3";
   private static final String SILA3_SKOROST3 = "Сила 3 + Скорость 3";
   private static final float FLOAT_VALUE = 0.92F;
   private static final float FLOAT_VALUE_2 = 0.005F;
   private static final float FLOAT_VALUE_3 = 0.02F;
   private static final int INT_VALUE = 6;
   private static final double DOUBLE_VALUE = 4.6;
   public final ModeSetting zele = new ModeSetting("Зелье", "Сила", "Сила", "Скорость", "Скорость 3 + Сила 3", "Сила 3 + Скорость 3");
   public final NumberSetting uroven = new NumberSetting("Уровень", 5.0F, 1.0F, 30.0F, 1.0F, false);
   public final BooleanSetting ekonomiyaOpyta = new BooleanSetting("Экономия опыта", true);
   private final DualTimer dualTimer = new DualTimer();
   private final DualTimer dualTimer2 = new DualTimer();
   private final DualTimer dualTimer3 = new DualTimer();
   private final DualTimer dualTimer4 = new DualTimer();
   private final Random random = new Random();
   private boolean flag;
   private int intValue = 8;
   private int intValue2 = 300;
   private int intValue3 = 220;
   private int intValue4 = -1;
   private int intValue5 = -1;
   private float floatValue;
   private String text = "";
   private int intValue6;

   public PotionCombiner() {
      this.addSettings(new Setting[]{this.zele, this.uroven, this.ekonomiyaOpyta});
   }

   @Override
   public void onEnable() {
      super.onEnable();
      this.text = "";
      this.dualTimer4.setTimestamp2(-10000L);
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (CLIENT.player == null || CLIENT.world == null || CLIENT.interactionManager == null) {
         this.invoke14(false);
      } else if (this.flag) {
         this.invoke9();
      } else if (CLIENT.player.experienceLevel < this.compute4()) {
         if (this.compute3() != -1) {
            this.invoke8();
         } else {
            this.invoke13("§cНет пузырьков опыта. Нужно добить уровень до " + this.compute4() + ".");
         }
      } else if (CLIENT.currentScreen instanceof AnvilScreen && CLIENT.player.currentScreenHandler instanceof AnvilScreenHandler anvilScreenHandler2) {
         this.invoke2(anvilScreenHandler2);
      } else {
         if (CLIENT.currentScreen == null) {
            this.invoke();
         }
      }
   }

   private void invoke() {
      BlockPos blockPos2 = this.resolve7(6);
      if (blockPos2 == null) {
         this.invoke12("§cНаковальня не найдена в радиусе 6 блоков.");
      } else {
         Vec3d vec3d2 = new Vec3d(blockPos2.getX() + 0.5, blockPos2.getY() + 0.9, blockPos2.getZ() + 0.5);
         Vec3d vec3d3 = this.resolve9(vec3d2, 0.02F);
         Rotation rotation = this.resolve8(vec3d3);
         float floatValue = 55.0F + this.measure(-2.0F, 2.0F);
         RotationController.invoke3(rotation, floatValue * 0.92F, floatValue * 0.92F, 25.0F, 25.0F, 2, 30, false);
         if (this.dualTimer3.check((long)this.intValue)) {
            if (!(new Rotation(CLIENT.player).measure(rotation) > 4.0F)) {
               if (this.check14(vec3d3, 4.6) && this.check13(blockPos2, vec3d3)) {
                  BlockHitResult blockHitResult = new BlockHitResult(this.resolve9(Vec3d.ofCenter(blockPos2), 0.08F), Direction.UP, blockPos2, false);
                  CLIENT.player.swingHand(Hand.MAIN_HAND);
                  CLIENT.interactionManager.interactBlock(CLIENT.player, Hand.MAIN_HAND, blockHitResult);
                  this.dualTimer3.invoke();
                  this.intValue = this.compute5(0, 1);
               }
            }
         }
      }
   }

   private void invoke2(AnvilScreenHandler anvilScreenHandler) {
      if (!this.check3(anvilScreenHandler)) {
         if (!this.dualTimer2.check((long)this.intValue3) || !this.check2(anvilScreenHandler)) {
            this.invoke3(anvilScreenHandler);
            if (CLIENT.player.experienceLevel < this.compute4()) {
               this.invoke8();
            } else {
               if (this.check(anvilScreenHandler)
                  && anvilScreenHandler.getSlot(2).hasStack()
                  && this.dualTimer2.check((long)this.intValue3)) {
                  if (this.ekonomiyaOpyta.isEnabled()) {
                     this.invoke7(anvilScreenHandler);
                  }

                  CLIENT.interactionManager.clickSlot(anvilScreenHandler.syncId, 2, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
                  this.dualTimer2.invoke();
                  this.intValue3 = this.compute5(85, 120);
               }
            }
         }
      }
   }

   private void invoke3(AnvilScreenHandler anvilScreenHandler) {
      if (this.dualTimer2.check((long)this.intValue3)) {
         if (this.check15()) {
            this.invoke4(anvilScreenHandler);
         } else {
            for (int intValue = 0; intValue < 2; intValue++) {
               ItemStack itemStack2 = this.resolve6(anvilScreenHandler, intValue);
               if (!itemStack2.isEmpty() && !this.check4(itemStack2)) {
                  this.invoke6(anvilScreenHandler, intValue);
                  this.invoke11();
                  return;
               }
            }

            for (int intValue2 = 0; intValue2 < 2; intValue2++) {
               if (this.resolve6(anvilScreenHandler, intValue2).isEmpty()) {
                  int intValue3 = this.compute2(anvilScreenHandler, this::check4);
                  if (intValue3 != -1) {
                     this.invoke5(anvilScreenHandler, intValue3, intValue2);
                     this.invoke11();
                  }

                  return;
               }
            }
         }
      }
   }

   private void invoke4(AnvilScreenHandler anvilScreenHandler) {
      for (int intValue4 = 0; intValue4 < 2; intValue4++) {
         ItemStack itemStack3 = this.resolve6(anvilScreenHandler, intValue4);
         if (!itemStack3.isEmpty() && !this.check5(itemStack3, intValue4)) {
            this.invoke6(anvilScreenHandler, intValue4);
            this.invoke11();
            return;
         }
      }

      for (int intValue5 = 0; intValue5 < 2; intValue5++) {
         if (this.resolve6(anvilScreenHandler, intValue5).isEmpty()) {
            int intValue6 = this.compute2(anvilScreenHandler, this.resolve2(intValue5));
            if (intValue6 != -1) {
               this.invoke5(anvilScreenHandler, intValue6, intValue5);
               this.invoke11();
            }

            return;
         }
      }
   }

   private boolean check(AnvilScreenHandler anvilScreenHandler) {
      ItemStack itemStack4 = this.resolve6(anvilScreenHandler, 0);
      ItemStack itemStack5 = this.resolve6(anvilScreenHandler, 1);
      return this.check15() ? this.check5(itemStack4, 0) && this.check5(itemStack5, 1) : this.check4(itemStack4) && this.check4(itemStack5);
   }

   private boolean check2(AnvilScreenHandler anvilScreenHandler) {
      if (this.check15()) {
         if (this.compute(anvilScreenHandler, this::check8) <= 0) {
            this.invoke13("§cНет ингредиента: Скорость III.");
            return true;
         } else if (this.compute(anvilScreenHandler, this::check9) <= 0) {
            this.invoke13("§cНет ингредиента: Сила III.");
            return true;
         } else {
            return false;
         }
      } else {
         int intValue7 = this.compute(anvilScreenHandler, this::check4);
         if (intValue7 < 2) {
            this.invoke13("§cНет ингредиента: " + this.resolve() + " x" + (2 - intValue7) + ".");
            return true;
         } else {
            return false;
         }
      }
   }

   private boolean check3(AnvilScreenHandler anvilScreenHandler) {
      for (int intValue8 = 0; intValue8 < 2; intValue8++) {
         ItemStack itemStack6 = this.resolve6(anvilScreenHandler, intValue8);
         if (!itemStack6.isEmpty() && itemStack6.getCount() > 1) {
            if (this.dualTimer2.check((long)this.intValue3)) {
               this.invoke6(anvilScreenHandler, intValue8);
               this.invoke11();
            }

            return true;
         }
      }

      return false;
   }

   private int compute(AnvilScreenHandler anvilScreenHandler, Predicate<ItemStack> predicate) {
      int intValue9 = 0;

      for (int intValue10 = 0; intValue10 < anvilScreenHandler.slots.size(); intValue10++) {
         if (intValue10 != 2) {
            ItemStack itemStack7 = anvilScreenHandler.getSlot(intValue10).getStack();
            if (predicate.test(itemStack7)) {
               intValue9 += Math.max(1, itemStack7.getCount());
            }
         }
      }

      return intValue9;
   }

   private String resolve() {
      if (this.zele.is("Сила")) {
         return "Сила II";
      } else {
         return this.zele.is("Скорость") ? "Скорость II" : "зелье";
      }
   }

   private boolean check4(ItemStack itemStack) {
      if (this.zele.is("Сила")) {
         return this.check7(itemStack, StatusEffects.STRENGTH, 2);
      } else {
         return this.zele.is("Скорость")
            ? this.check7(itemStack, StatusEffects.SPEED, 2)
            : this.check8(itemStack) || this.check9(itemStack);
      }
   }

   private boolean check5(ItemStack itemStack, int i) {
      return this.check6(i) ? this.check8(itemStack) : this.check9(itemStack);
   }

   private Predicate<ItemStack> resolve2(int i) {
      return this.check6(i) ? this::check8 : this::check9;
   }

   private boolean check6(int i) {
      boolean flag = this.zele.is("Скорость 3 + Сила 3");
      return i == 0 ? flag : !flag;
   }

   private boolean check7(ItemStack itemStack, RegistryEntry<StatusEffect> registryEntry, int i) {
      if (!this.check10(itemStack)) {
         return false;
      } else {
         PotionContentsComponent potionContentsComponent = (PotionContentsComponent)itemStack.get(DataComponentTypes.POTION_CONTENTS);
         if (potionContentsComponent == null) {
            return false;
         } else {
            for (StatusEffectInstance statusEffectInstance : potionContentsComponent.getEffects()) {
               if (statusEffectInstance.getEffectType().equals(registryEntry) && statusEffectInstance.getAmplifier() == i - 1) {
                  return true;
               }
            }

            return false;
         }
      }
   }

   private boolean check8(ItemStack itemStack) {
      return this.check7(itemStack, StatusEffects.SPEED, 3) && !this.check7(itemStack, StatusEffects.STRENGTH, 3);
   }

   private boolean check9(ItemStack itemStack) {
      return this.check7(itemStack, StatusEffects.STRENGTH, 3) && !this.check7(itemStack, StatusEffects.SPEED, 3);
   }

   private boolean check10(ItemStack itemStack) {
      return itemStack != null
         && !itemStack.isEmpty()
         && (itemStack.isOf(Items.POTION) || itemStack.isOf(Items.SPLASH_POTION) || itemStack.isOf(Items.LINGERING_POTION));
   }

   private int compute2(AnvilScreenHandler anvilScreenHandler, Predicate<ItemStack> predicate) {
      for (int intValue11 = 3; intValue11 < anvilScreenHandler.slots.size(); intValue11++) {
         ItemStack itemStack8 = anvilScreenHandler.getSlot(intValue11).getStack();
         if (predicate.test(itemStack8)) {
            return intValue11;
         }
      }

      return -1;
   }

   private void invoke5(AnvilScreenHandler anvilScreenHandler, int i, int j) {
      CLIENT.interactionManager.clickSlot(anvilScreenHandler.syncId, i, 0, SlotActionType.PICKUP, CLIENT.player);
      CLIENT.interactionManager.clickSlot(anvilScreenHandler.syncId, j, 1, SlotActionType.PICKUP, CLIENT.player);
      CLIENT.interactionManager.clickSlot(anvilScreenHandler.syncId, i, 0, SlotActionType.PICKUP, CLIENT.player);
   }

   private void invoke6(AnvilScreenHandler anvilScreenHandler, int i) {
      CLIENT.interactionManager.clickSlot(anvilScreenHandler.syncId, i, 0, SlotActionType.QUICK_MOVE, CLIENT.player);
   }

   private void invoke7(AnvilScreenHandler anvilScreenHandler) {
      if (CLIENT.player != null && CLIENT.player.networkHandler != null) {
         String text = this.resolve3(anvilScreenHandler);

         for (int intValue12 = 0; intValue12 < 10; intValue12++) {
            String text2 = intValue12 % 2 == 0 ? text + this.resolve4() : text;
            anvilScreenHandler.setNewItemName(text2);
            CLIENT.player.networkHandler.sendPacket(new RenameItemC2SPacket(text2));
         }
      }
   }

   private String resolve3(AnvilScreenHandler anvilScreenHandler) {
      ItemStack itemStack9 = this.resolve6(anvilScreenHandler, 0);
      if (!itemStack9.isEmpty()) {
         return this.resolve5(itemStack9.getName().getString());
      } else {
         ItemStack itemStack10 = this.resolve6(anvilScreenHandler, 2);
         return !itemStack10.isEmpty() ? this.resolve5(itemStack10.getName().getString()) : "Potion";
      }
   }

   private String resolve4() {
      this.intValue6++;
      return "_" + Integer.toString(this.intValue6, 36) + Integer.toString(this.random.nextInt(1296), 36);
   }

   private String resolve5(String string) {
      if (string != null && !string.isBlank()) {
         return string.length() > 32 ? string.substring(0, 32) : string;
      } else {
         return "Potion";
      }
   }

   private ItemStack resolve6(AnvilScreenHandler anvilScreenHandler, int i) {
      return anvilScreenHandler != null && i >= 0 && i < anvilScreenHandler.slots.size() ? anvilScreenHandler.getSlot(i).getStack() : ItemStack.EMPTY;
   }

   private void invoke8() {
      this.flag = true;
      this.intValue4 = CLIENT.player.getInventory().getSelectedSlot();
      this.floatValue = CLIENT.player.getPitch();
      this.dualTimer.invoke();
   }

   private void invoke9() {
      if (CLIENT.player.experienceLevel >= this.compute4()) {
         this.invoke10(true);
      } else if (CLIENT.currentScreen != null) {
         CLIENT.player.closeHandledScreen();
      } else {
         float floatValue2 = 87.0F + this.measure(-0.7F, 0.7F);
         CLIENT.player.setPitch(measure3(floatValue2));
         if (!this.check11()) {
            this.invoke10(true);
            this.invoke13("§cНет пузырьков опыта. Нужно добить уровень до " + this.compute4() + ".");
         } else if (this.dualTimer.check((long)this.intValue2)) {
            CLIENT.interactionManager.interactItem(CLIENT.player, Hand.MAIN_HAND);
            CLIENT.player.swingHand(Hand.MAIN_HAND);
            this.dualTimer.invoke();
            this.intValue2 = this.compute5(50, 70);
         }
      }
   }

   private boolean check11() {
      if (CLIENT.player.getMainHandStack().isOf(Items.EXPERIENCE_BOTTLE)) {
         return true;
      } else {
         int intValue13 = this.compute3();
         if (intValue13 == -1) {
            return false;
         } else if (intValue13 >= 36 && intValue13 <= 44) {
            CLIENT.player.getInventory().setSelectedSlot(intValue13 - 36);
            ((ClientPlayerInteractionManagerAccessor)CLIENT.interactionManager).invokeSyncSelectedSlot();
            return true;
         } else {
            if (this.intValue4 < 0) {
               this.intValue4 = CLIENT.player.getInventory().getSelectedSlot();
            }

            this.intValue5 = intValue13;
            CLIENT.interactionManager
               .clickSlot(CLIENT.player.playerScreenHandler.syncId, intValue13, this.intValue4, SlotActionType.SWAP, CLIENT.player);
            ((ClientPlayerInteractionManagerAccessor)CLIENT.interactionManager).invokeSyncSelectedSlot();
            return true;
         }
      }
   }

   private int compute3() {
      if (CLIENT.player == null) {
         return -1;
      } else {
         for (int intValue14 = 9; intValue14 <= 44; intValue14++) {
            if (CLIENT.player.playerScreenHandler.getSlot(intValue14).getStack().isOf(Items.EXPERIENCE_BOTTLE)) {
               return intValue14;
            }
         }

         return -1;
      }
   }

   private void invoke10(boolean bl) {
      if (bl && CLIENT.player != null && CLIENT.interactionManager != null) {
         if (this.intValue5 != -1 && this.intValue4 >= 0) {
            CLIENT.interactionManager
               .clickSlot(CLIENT.player.playerScreenHandler.syncId, this.intValue5, this.intValue4, SlotActionType.SWAP, CLIENT.player);
         }

         if (this.intValue4 >= 0) {
            CLIENT.player.getInventory().setSelectedSlot(this.intValue4);
            ((ClientPlayerInteractionManagerAccessor)CLIENT.interactionManager).invokeSyncSelectedSlot();
         }

         CLIENT.player.setPitch(this.floatValue);
      }

      this.flag = false;
      this.intValue5 = -1;
      this.intValue4 = -1;
   }

   private BlockPos resolve7(int i) {
      BlockPos blockPos3 = CLIENT.player.getBlockPos();
      Vec3d vec3d4 = CLIENT.player.getEyePos();
      BlockPos blockPos4 = null;
      double doubleValue = Double.MAX_VALUE;

      for (int intValue15 = -i; intValue15 <= i; intValue15++) {
         for (int intValue16 = -2; intValue16 <= 2; intValue16++) {
            for (int intValue17 = -i; intValue17 <= i; intValue17++) {
               BlockPos blockPos5 = blockPos3.add(intValue15, intValue16, intValue17);
               Block block2 = CLIENT.world.getBlockState(blockPos5).getBlock();
               if (this.check12(block2)) {
                  Vec3d vec3d5 = new Vec3d(blockPos5.getX() + 0.5, blockPos5.getY() + 0.9, blockPos5.getZ() + 0.5);
                  double doubleValue2 = vec3d4.squaredDistanceTo(vec3d5);
                  if (doubleValue2 < doubleValue) {
                     doubleValue = doubleValue2;
                     blockPos4 = blockPos5.toImmutable();
                  }
               }
            }
         }
      }

      return blockPos4;
   }

   private boolean check12(Block block) {
      return block == Blocks.ANVIL || block == Blocks.CHIPPED_ANVIL || block == Blocks.DAMAGED_ANVIL;
   }

   private boolean check13(BlockPos blockPos, Vec3d vec3d) {
      Vec3d vec3d6 = CLIENT.player.getEyePos();
      BlockHitResult blockHitResult2 = CLIENT.world.raycast(new RaycastContext(vec3d6, vec3d, ShapeType.OUTLINE, FluidHandling.NONE, CLIENT.player));
      return blockHitResult2.getType() == Type.BLOCK && blockHitResult2.getBlockPos().equals(blockPos);
   }

   private boolean check14(Vec3d vec3d, double d) {
      return CLIENT.player.getEyePos().squaredDistanceTo(vec3d) <= d * d;
   }

   private Rotation resolve8(Vec3d vec3d) {
      Vec3d vec3d7 = CLIENT.player.getEyePos();
      double doubleValue3 = vec3d.x - vec3d7.x;
      double doubleValue4 = vec3d.y - vec3d7.y;
      double doubleValue5 = vec3d.z - vec3d7.z;
      double doubleValue6 = Math.hypot(doubleValue3, doubleValue5);
      float floatValue3 = (float)Math.toDegrees(Math.atan2(doubleValue5, doubleValue3)) - 90.0F;
      float floatValue4 = (float)(-Math.toDegrees(Math.atan2(doubleValue4, doubleValue6)));
      floatValue3 += this.measure(-0.03F, 0.03F);
      floatValue4 += this.measure(-0.03F, 0.03F);
      return new Rotation(measure2(floatValue3), measure3(floatValue4));
   }

   private Vec3d resolve9(Vec3d vec3d, float f) {
      return new Vec3d(vec3d.x + this.measure(-f, f), vec3d.y + this.measure(-f * 0.5F, f * 0.5F), vec3d.z + this.measure(-f, f));
   }

   private void invoke11() {
      this.dualTimer2.invoke();
      this.intValue3 = this.compute5(85, 120);
   }

   private void invoke12(String string) {
      if (string != null && !string.isBlank()) {
         if (!string.equals(this.text) || this.dualTimer4.check(2500L)) {
            ChatUtil.sendClientMessage("§8[§dPotionCombiner§8] §f" + string);
            this.text = string;
            this.dualTimer4.invoke();
         }
      }
   }

   private void invoke13(String string) {
      this.invoke12(string);
      if (this.enabled) {
         this.toggle();
      }
   }

   private int compute4() {
      return Math.max(1, Math.round(this.uroven.getValue()));
   }

   private boolean check15() {
      return this.zele.is("Скорость 3 + Сила 3") || this.zele.is("Сила 3 + Скорость 3");
   }

   private float measure(float f, float g) {
      return f + (g - f) * this.random.nextFloat();
   }

   private int compute5(int i, int j) {
      return i + this.random.nextInt(Math.max(1, j - i + 1));
   }

   private static float measure2(float f) {
      f %= 360.0F;
      if (f >= 180.0F) {
         f -= 360.0F;
      }

      if (f < -180.0F) {
         f += 360.0F;
      }

      return f;
   }

   private static float measure3(float f) {
      return Math.max(-90.0F, Math.min(90.0F, f));
   }

   private void invoke14(boolean bl) {
      this.invoke10(bl);
      RotationController.rotationControllerState2 = RotationController.RotationControllerState2.IDLE;
      RotationController.intValue = 0;
      RotationController.rotation = null;
      FreeLookController.active = false;
   }

   @Override
   public void onDisable() {
      this.invoke14(true);
      super.onDisable();
   }
}
