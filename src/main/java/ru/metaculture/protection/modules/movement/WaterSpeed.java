package ru.metaculture.protection;

import net.minecraft.block.Blocks;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifierSlot;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "WaterSpeed",
   category = Category.Movement,
   description = "Ускорение в воде!"
)
public class WaterSpeed extends Module {
   public final ModeSetting rezhim = new ModeSetting("Режим", "HVH", "HVH");
   private final Stopwatch stopwatch = new Stopwatch();
   private final Stopwatch stopwatch2 = new Stopwatch();
   private boolean flag = false;
   private boolean flag2 = false;
   float floatValue;

   public WaterSpeed() {
      this.addSettings(new Setting[]{this.rezhim});
   }

   private boolean check() {
      BlockPos blockPos = CLIENT.player.getBlockPos();
      BlockPos blockPos2 = blockPos.up(1);
      BlockPos blockPos3 = blockPos.up(2);
      boolean flag = CLIENT.world.getBlockState(blockPos2).getBlock() == Blocks.ICE || CLIENT.world.getBlockState(blockPos3).getBlock() == Blocks.ICE;
      boolean flag2 = ServerBlockUtils.check5(Blocks.ICE, blockPos, 1.0F, 1.0F);
      return flag || flag2;
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (!ServerModeDetector.check()) {
         if (MovementUtils.check()) {
            this.stopwatch.invoke();
         }

         if (this.rezhim.is("FunTime") && CLIENT.player.isTouchingWater()) {
            boolean flag3 = CLIENT.options.forwardKey.isPressed();
            RegistryEntry registryEntry = (RegistryEntry)CLIENT.world
               .getRegistryManager()
               .getOrThrow(RegistryKeys.ENCHANTMENT)
               .getOptional(Enchantments.DEPTH_STRIDER)
               .orElseThrow();
            int intValue = EnchantmentHelper.getEquipmentLevel(registryEntry, CLIENT.player);
            boolean flag4 = intValue >= 3;
            ItemStack itemStack = CLIENT.player.getOffHandStack();
            boolean flag5 = !itemStack.isEmpty() && itemStack.getItem() == Items.PLAYER_HEAD;
            boolean flag6 = false;
            if (flag5) {
               AttributeModifiersComponent attributeModifiersComponent = (AttributeModifiersComponent)itemStack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
               if (attributeModifiersComponent != null) {
                  flag6 = attributeModifiersComponent.modifiers().stream().anyMatch(entry -> {
                     boolean flag7 = entry.slot() == AttributeModifierSlot.OFFHAND || entry.slot() == AttributeModifierSlot.ANY;
                     boolean var2x = entry.attribute() == EntityAttributes.MOVEMENT_SPEED;
                     EntityAttributeModifier var3x = entry.modifier();
                     boolean var4x = var3x.operation() == Operation.ADD_MULTIPLIED_TOTAL || var3x.operation() == Operation.ADD_MULTIPLIED_BASE;
                     return flag7 && var2x && var4x && var3x.value() >= 0.14 && var3x.value() <= 0.16;
                  });
               }
            }

            boolean flag8 = this.check();
            if (flag8 && !this.flag && !this.flag2) {
               this.flag = true;
               this.flag2 = true;
               this.stopwatch2.invoke();
            }

            if (!this.flag || !this.flag2 || !this.stopwatch2.check2(3000.0)) {
               this.floatValue = 1.0481F;
            } else if (flag4 && flag8) {
               this.floatValue = 1.175F;
            } else {
               this.floatValue = 1.04839F;
            }

            if (!flag8) {
               this.flag = false;
               this.flag2 = false;
            }

            if (flag3) {
               Vec3d vec3d = CLIENT.player.getVelocity();
               CLIENT.player.setVelocity(vec3d.x * this.floatValue, vec3d.y, vec3d.z * this.floatValue);
            }
         }
      }
   }
}
