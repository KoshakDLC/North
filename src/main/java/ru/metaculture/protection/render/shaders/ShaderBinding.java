package ru.metaculture.protection;

public interface ShaderBinding {
   ShaderSurface getESP();

   default String resolve() {
      return null;
   }

   default boolean check() {
      return false;
   }
}
