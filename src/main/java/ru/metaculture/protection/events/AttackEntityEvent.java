package ru.metaculture.protection;

import lombok.Generated;
import net.minecraft.entity.Entity;

public class AttackEntityEvent extends Event {
   private Entity entity;

   @Generated
   public Entity getEntity() {
      return this.entity;
   }

   @Generated
   public void setEntity(Entity entity) {
      this.entity = entity;
   }

   @Generated
   public AttackEntityEvent(Entity entity) {
      this.entity = entity;
   }
}
