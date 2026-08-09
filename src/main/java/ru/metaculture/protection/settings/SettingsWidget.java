package ru.metaculture.protection;

public interface SettingsWidget {
   void invoke(PopupContext.PopupContextState popupContextState);

   float measure();

   void invoke2();

   void invoke3(double d, double e);

   boolean check(double d, double e, int i);

   boolean check2(double d, double e, double f, double g);

   void invoke4(RenderManager renderManager, float f, float g);

   void invoke5(RenderManager renderManager2, float f, float g);

   boolean check3();
}
