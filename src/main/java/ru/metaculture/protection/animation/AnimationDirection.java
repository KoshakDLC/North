package ru.metaculture.protection;

public enum AnimationDirection {
   FORWARDS,
   BACKWARDS;

   public AnimationDirection resolve() {
      return this == FORWARDS ? BACKWARDS : FORWARDS;
   }
}
