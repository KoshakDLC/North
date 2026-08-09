package ru.metaculture.protection;

import java.util.List;
import lombok.Generated;

public final class ModuleLayoutResult {
   private final List<ModulePlacement> items;
   private final float floatValue;

   public static ModuleLayoutResult resolve() {
      return new ModuleLayoutResult(List.of(), 0.0F);
   }

   @Generated
   public ModuleLayoutResult(List<ModulePlacement> list, float f) {
      this.items = list;
      this.floatValue = f;
   }

   @Generated
   public List<ModulePlacement> getItems() {
      return this.items;
   }

   @Generated
   public float getFloatValue() {
      return this.floatValue;
   }

   @Generated
   @Override
   public boolean equals(Object object) {
      if (object == this) {
         return true;
      } else if (!(object instanceof ModuleLayoutResult moduleLayoutResult)) {
         return false;
      } else if (Float.compare(this.getFloatValue(), moduleLayoutResult.getFloatValue()) != 0) {
         return false;
      } else {
         List items = this.getItems();
         List items2 = moduleLayoutResult.getItems();
         return items == null ? items2 == null : items.equals(items2);
      }
   }

   @Generated
   @Override
   public int hashCode() {
      byte byteValue = 59;
      int intValue = 1;
      intValue = intValue * 59 + Float.floatToIntBits(this.getFloatValue());
      List items3 = this.getItems();
      return intValue * 59 + (items3 == null ? 43 : items3.hashCode());
   }

   @Generated
   @Override
   public String toString() {
      return "ModuleLayoutResult(placements=" + this.getItems() + ", maxScroll=" + this.getFloatValue() + ")";
   }
}
