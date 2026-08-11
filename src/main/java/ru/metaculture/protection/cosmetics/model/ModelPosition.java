package ru.metaculture.protection.cosmetics.model;

public enum ModelPosition {
   FREE,
   BODY,
   HEAD,
   ABOVE_HEAD,
   RIGHT_ARM,
   LEFT_ARM,
   RIGHT_LEG,
   LEFT_LEG;

   public static ModelPosition getById(int id) {
      ModelPosition[] values = values();
      return id >= 0 && id < values.length ? values[id] : BODY;
   }
}
