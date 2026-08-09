package ru.metaculture.protection;

import java.util.List;
import net.minecraft.client.gui.DrawContext;
import org.wild.module.api.Module;

public interface SpecialModuleCardHandler {
   boolean check(Module module);

   default boolean check2(Module module, ClickGuiState clickGuiState) {
      return false;
   }

   default void invoke(ClickGuiState clickGuiState2) {
   }

   default void invoke2(ClickGuiState clickGuiState3) {
   }

   float measure(Module module, Metrics metrics, ClickGuiState clickGuiState4);

   void invoke3(Module module, ClickGuiState clickGuiState5, SpringSpec springSpec, SpringSpec springSpec2);

   void invoke4(
      RenderManager renderManager, DrawContext drawContext, ClickGuiState clickGuiState6, ModulePlacement modulePlacement, ThemeContext themeContext
   );

   void invoke5(List<ClickGuiHitTarget> list, ClickGuiState clickGuiState7, ModulePlacement modulePlacement2, Metrics metrics2);

   default boolean check3(ClickGuiState clickGuiState8, ModuleLayoutResult moduleLayoutResult, Metrics metrics3, float f, float g, double d) {
      return false;
   }

   default boolean check4(ClickGuiState clickGuiState9, float f, float g) {
      return false;
   }

   default boolean check5(ClickGuiState clickGuiState10) {
      return false;
   }

   default boolean check6(ClickGuiState clickGuiState11, int i) {
      return false;
   }

   default boolean check7(ClickGuiState clickGuiState12, char c) {
      return false;
   }
}
