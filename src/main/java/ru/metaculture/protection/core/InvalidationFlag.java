package ru.metaculture.protection;

public class InvalidationFlag implements Invalidatable {
   private boolean invalidated;

   @Override
   public boolean isInvalidated() {
      return this.invalidated;
   }

   @Override
   public void invalidate() {
      this.invalidated = true;
   }
}
