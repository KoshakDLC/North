package ru.metaculture.protection;

public interface BackdropScreen {
   void invoke2(int i, int j, float f);

   default boolean check() {
      return true;
   }
}
