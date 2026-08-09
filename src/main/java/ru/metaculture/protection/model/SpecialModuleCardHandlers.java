package ru.metaculture.protection;

import java.util.List;
import org.wild.module.api.Module;

public final class SpecialModuleCardHandlers {
   private static final List<SpecialModuleCardHandler> ITEMS = List.of(new AutoBuyModuleCardHandler(), new AutoCraftModuleCardHandler());

   private SpecialModuleCardHandlers() {
   }

   public static SpecialModuleCardHandler resolve(Module module) {
      for (SpecialModuleCardHandler specialModuleCardHandler : ITEMS) {
         if (specialModuleCardHandler.check(module)) {
            return specialModuleCardHandler;
         }
      }

      return null;
   }

   public static boolean check(Module module) {
      return resolve(module) != null;
   }

   public static ModulePlacement resolve2(Module module, ClickGuiGeometry clickGuiGeometry, Metrics metrics) {
      return new ModulePlacement(
         module,
         clickGuiGeometry.getFloatValue11(),
         clickGuiGeometry.getFloatValue12() - metrics.getFloatValue15() - metrics.measure(10.0F),
         clickGuiGeometry.getFloatValue13(),
         metrics.getFloatValue15() + clickGuiGeometry.getFloatValue14(),
         clickGuiGeometry.getFloatValue14() + metrics.measure(20.0F)
      );
   }

   public static void invoke(ClickGuiState clickGuiState) {
      for (SpecialModuleCardHandler specialModuleCardHandler2 : ITEMS) {
         specialModuleCardHandler2.invoke(clickGuiState);
      }
   }

   public static void invoke2(ClickGuiState clickGuiState2) {
      for (SpecialModuleCardHandler specialModuleCardHandler3 : ITEMS) {
         specialModuleCardHandler3.invoke2(clickGuiState2);
      }
   }

   public static void invoke3(Module module, ClickGuiState clickGuiState3, SpringSpec springSpec, SpringSpec springSpec2) {
      SpecialModuleCardHandler specialModuleCardHandler4 = resolve(module);
      if (specialModuleCardHandler4 != null) {
         specialModuleCardHandler4.invoke3(module, clickGuiState3, springSpec, springSpec2);
      }
   }

   public static void invoke4(List<ClickGuiHitTarget> list, ClickGuiState clickGuiState4, ModulePlacement modulePlacement, Metrics metrics2) {
      SpecialModuleCardHandler specialModuleCardHandler5 = resolve(modulePlacement.getModule());
      if (specialModuleCardHandler5 != null) {
         specialModuleCardHandler5.invoke5(list, clickGuiState4, modulePlacement, metrics2);
      }
   }

   public static boolean check2(ClickGuiState clickGuiState5, ModuleLayoutResult moduleLayoutResult, Metrics metrics3, float f, float g, double d) {
      for (SpecialModuleCardHandler specialModuleCardHandler6 : ITEMS) {
         if (specialModuleCardHandler6.check3(clickGuiState5, moduleLayoutResult, metrics3, f, g, d)) {
            return true;
         }
      }

      return false;
   }

   public static boolean check3(ClickGuiState clickGuiState6, float f, float g) {
      for (SpecialModuleCardHandler specialModuleCardHandler7 : ITEMS) {
         if (specialModuleCardHandler7.check4(clickGuiState6, f, g)) {
            return true;
         }
      }

      return false;
   }

   public static boolean check4(ClickGuiState clickGuiState7) {
      for (SpecialModuleCardHandler specialModuleCardHandler8 : ITEMS) {
         if (specialModuleCardHandler8.check5(clickGuiState7)) {
            return true;
         }
      }

      return false;
   }

   public static boolean check5(ClickGuiState clickGuiState8, int i) {
      for (SpecialModuleCardHandler specialModuleCardHandler9 : ITEMS) {
         if (specialModuleCardHandler9.check6(clickGuiState8, i)) {
            return true;
         }
      }

      return false;
   }

   public static boolean check6(ClickGuiState clickGuiState9, char c) {
      for (SpecialModuleCardHandler specialModuleCardHandler10 : ITEMS) {
         if (specialModuleCardHandler10.check7(clickGuiState9, c)) {
            return true;
         }
      }

      return false;
   }
}
