package ru.metaculture.protection;

public interface SettingPopup {
   void invoke();

   default void invoke7(boolean bl) {
   }

   void invoke2(float f, float g, float h);

   float measure();

   default float measure2() {
      return this.measure();
   }

   default void invoke8(RenderManager renderManager, float f, float g) {
      this.invoke3(renderManager, f, g, 0.0F);
   }

   default void invoke3(RenderManager renderManager2, float f, float g, float h) {
      this.invoke8(renderManager2, f, g);
   }

   void invoke5(double d, double e);

   default void invoke4(RenderManager renderManager3, float f, float g) {
   }

   default boolean check() {
      return false;
   }

   default boolean check2(double d, double e, int i) {
      return false;
   }

   default boolean check4(double d, double e, double f, double g) {
      return false;
   }

   default void invoke6() {
   }

   boolean check3(double d, double e, int i);

   default boolean check6(double d, double e, double f, double g) {
      return false;
   }

   default Setting getMultiSelectSetting() {
      return null;
   }

   default boolean check5() {
      return false;
   }
}
