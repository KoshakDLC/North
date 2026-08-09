package ru.metaculture.protection;

import java.util.HashMap;
import java.util.Map;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.AttributeModifiersComponent;
import net.minecraft.component.type.AttributeModifiersComponent.Entry;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributeModifier.Operation;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.entry.RegistryEntry;

public class ItemAttributeUtils {
   private static Map<RegistryEntry<EntityAttribute>, Double> resolve(ItemStack itemStack) {
      AttributeModifiersComponent attributeModifiersComponent = (AttributeModifiersComponent)itemStack.get(DataComponentTypes.ATTRIBUTE_MODIFIERS);
      HashMap hashMap = new HashMap();
      if (attributeModifiersComponent == null) {
         return hashMap;
      } else {
         for (Entry entry : attributeModifiersComponent.modifiers()) {
            if (entry.slot().matches(EquipmentSlot.OFFHAND)) {
               EntityAttributeModifier entityAttributeModifier = entry.modifier();
               if (entityAttributeModifier.operation() == Operation.ADD_VALUE) {
                  hashMap.put(entry.attribute(), entityAttributeModifier.value());
               }
            }
         }

         return hashMap;
      }
   }

   private static boolean check(Map<RegistryEntry<EntityAttribute>, Double> map, RegistryEntry<EntityAttribute> registryEntry, double d) {
      return Double.compare(map.getOrDefault(registryEntry, 0.0), d) == 0;
   }

   public static boolean check2(ItemStack itemStack) {
      return itemStack.isOf(Items.ENCHANTED_GOLDEN_APPLE);
   }

   public static boolean check3(ItemStack itemStack) {
      return itemStack.isOf(Items.TOTEM_OF_UNDYING);
   }

   public static boolean check4(ItemStack itemStack) {
      return itemStack.isOf(Items.DIAMOND);
   }

   public static boolean check5(ItemStack itemStack) {
      return itemStack.isOf(Items.SPAWNER);
   }

   public static boolean check6(ItemStack itemStack) {
      return itemStack.isOf(Items.GOLDEN_APPLE);
   }
}
