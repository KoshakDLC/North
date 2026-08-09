package ru.metaculture.protection;

import java.util.ArrayList;
import net.minecraft.client.gui.DrawContext;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "Hud",
   description = "Интерфейс клиента",
   category = Category.Visuals
)
public class HudModule extends Module implements ShaderBinding {
   private static final HudModule.HudModuleState[] HUD_MODULE_STATES = new HudModule.HudModuleState[32];
   private static int intValue;
   private static int intValue2;
   private static int intValue3;
   public static final GroupSetting ELEMENTS = new GroupSetting("Elements", resolve2());
   public static final String CLIENT_MODE = "Client";
   public static final String CUSTOM = "Custom";
   public static final GroupSetting GROUP_SETTING = ELEMENTS;
   public static final ModeSetting HUD_MODE = new ModeSetting("HUD Mode", "Client", "Client", "Custom").setVisibilityCondition(() -> !HudEditorAccessControl.check());
   public static final ButtonSetting HUD_CONSTRUCTOR = new ButtonSetting("HUD Constructor", 0)
      .setRun("Open")
      .setVisibilityCondition(() -> !HudEditorAccessControl.check() || !check2())
      .setRunnable(HudModule::invoke);
   public static final FoundryShaderSetting FOUNDRY_SHADER = new FoundryShaderSetting("Foundry Shader", ShaderSurface.HUD);

   public HudModule() {
      this.addSettings(new Setting[]{ELEMENTS, HUD_MODE, HUD_CONSTRUCTOR, FOUNDRY_SHADER});
   }

   private static BooleanSetting[] resolve2() {
      ArrayList arrayList = new ArrayList();
      arrayList.add(new BooleanSetting("Watermark", true));
      arrayList.add(new BooleanSetting("ArrayList", true));
      arrayList.add(new BooleanSetting("HotKeys", true));
      arrayList.add(new BooleanSetting("Potions", true));
      arrayList.add(new BooleanSetting("Cool Downs", true));
      arrayList.add(new BooleanSetting("TargetHud", true));
      arrayList.add(new BooleanSetting("Armor", true));
      arrayList.add(new BooleanSetting("Inventory", true));
      arrayList.add(new BooleanSetting("PlayerInfo", true));
      if (ru.metaculture.protection.HudPresetManager.check(AutoBuyInfoHud.class)) {
         arrayList.add(new BooleanSetting("AutoBuy Info", true));
      }

      arrayList.add(new BooleanSetting("Notifications", true));
      if (ru.metaculture.protection.HudPresetManager.check(AiStatusHud.class)) {
         arrayList.add(new BooleanSetting("AI Status", true));
      }

      arrayList.add(new BooleanSetting("Brew Monitor", true));
      arrayList.add(new BooleanSetting("HotBar", false));
      arrayList.add(new BooleanSetting("MediaPlayer", true));
      arrayList.add(new BooleanSetting("Server Helper", false));
      return (BooleanSetting[])arrayList.toArray(BooleanSetting[]::new);
   }

   @Override
   public void onEnable() {
      super.onEnable();
      ShaderBindingRegistry.getINSTANCE().invoke(this, this);
   }

   @Override
   public void onDisable() {
      ShaderBindingRegistry.getINSTANCE().invoke4(this);
      super.onDisable();
   }

   @EventHandler
   public void onHudRender(HudRenderEvent hudRenderEvent) {
      ShaderBindingRegistry.getINSTANCE().invoke7(this, this);
      if (CLIENT.player != null && CLIENT.world != null) {
         DrawContext context = hudRenderEvent.getDrawContext();
         RenderManager renderManager = hudRenderEvent.getRenderManager();
         if (renderManager != null) {
            boolean ultraLow = GraphicsQuality.isUltraLow();
            invoke2(hudRenderEvent.getIntValue(), hudRenderEvent.getIntValue2());
            if (ELEMENTS.isEnabled("Notifications")) {
               NotificationsHud.invoke();
               NotificationsHud.invoke7(renderManager);
            }

            if (!ultraLow && ru.metaculture.protection.HudPresetManager.check(AiStatusHud.class) && ELEMENTS.isEnabled("AI Status")) {
               AiStatusHud.invoke(renderManager);
            }

            if (!ultraLow && ELEMENTS.isEnabled("Brew Monitor")) {
               BrewMonitorHud.invoke(renderManager);
            }

            if (ELEMENTS.isEnabled("Watermark")) {
               WatermarkHud.invoke(renderManager);
            }

            if (ELEMENTS.isEnabled("ArrayList")) {
               ArrayListHud.invoke(renderManager);
            }

            if (!ultraLow && ELEMENTS.isEnabled("PlayerInfo")) {
               InformationHud.invoke(renderManager);
            }

            if (!ultraLow && ru.metaculture.protection.HudPresetManager.check(AutoBuyInfoHud.class) && ELEMENTS.isEnabled("AutoBuy Info")) {
               AutoBuyInfoHud.invoke(renderManager);
            }

            if (ELEMENTS.isEnabled("TargetHud")) {
               TargetHud.invoke(renderManager, hudRenderEvent.getDrawContext());
            }

            if (ELEMENTS.isEnabled("Potions")) {
               PotionsHud.invoke2(renderManager, hudRenderEvent.getDrawContext());
            }

            if (!ultraLow && ELEMENTS.isEnabled("Cool Downs")) {
               CooldownsHud.invoke2(renderManager, context);
            }

            if (ELEMENTS.isEnabled("Armor")) {
               ArmorHud.invoke(renderManager, hudRenderEvent.getDrawContext());
            }

            if (ELEMENTS.isEnabled("HotKeys")) {
               KeybindHud.invoke(renderManager);
            }

            if (!ultraLow && ELEMENTS.isEnabled("Inventory")) {
               InventoryHud.invoke(renderManager, hudRenderEvent.getDrawContext());
            }

            if (!ultraLow && ELEMENTS.isEnabled("HotBar")) {
               HotbarHud.invoke(renderManager, hudRenderEvent.getDrawContext());
            }

            if (!ultraLow && ELEMENTS.isEnabled("MediaPlayer")) {
               MusicPlayerHud.invoke(renderManager);
            }

            if (!ultraLow && ELEMENTS.isEnabled("Server Helper")) {
               ServerHelperHud.invoke3(renderManager, context);
            }
         }
      }
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      CooldownsHud.invoke(packetEvent);
      PotionsHud.invoke(packetEvent);
   }

   public static String resolve3() {
      String text = FOUNDRY_SHADER.resolve2();
      return text == null ? "" : text;
   }

   public static boolean check2() {
      return HudEditorAccessControl.check() && "Custom".equals(HUD_MODE.getValue());
   }

   private static void invoke() {
      if (CLIENT != null && HudEditorAccessControl.check()) {
         CLIENT.execute(() -> CLIENT.setScreen(new HudConstructorScreen()));
      }
   }

   @Override
   public ShaderSurface getESP() {
      return ShaderSurface.HUD;
   }

   @Override
   public String resolve() {
      String text2 = resolve3();
      return text2 != null && !text2.isBlank() ? text2 : null;
   }

   @Override
   public boolean check() {
      return true;
   }

   public static void invoke2(int i, int j) {
      intValue2 = Math.max(1, i);
      intValue3 = Math.max(1, j);
      intValue = 0;
   }

   public static void invoke3(String string, float f, float g, float h, float i) {
      if (!(h <= 0.0F) && !(i <= 0.0F) && Float.isFinite(f) && Float.isFinite(g)) {
         if (intValue < HUD_MODULE_STATES.length) {
            HudModule.HudModuleState hudModuleState = HUD_MODULE_STATES[intValue];
            if (hudModuleState == null) {
               hudModuleState = new HudModule.HudModuleState();
               HUD_MODULE_STATES[intValue] = hudModuleState;
            }

            hudModuleState.invoke(string, f, g, h, i);
            intValue++;
         }
      }
   }

   public static void invoke4(String string, RenderManager renderManager2, float f, float g, float h, float i) {
      if (renderManager2 == null) {
         invoke3(string, f, g, h, i);
      } else {
         float[] floatValues = renderManager2.getMatrix3Stack().resolve2();
         if (floatValues != null && floatValues.length >= 6) {
            float floatValue = f + h;
            float floatValue2 = g + i;
            float floatValue3 = floatValues[0] * f + floatValues[1] * g + floatValues[2];
            float floatValue4 = floatValues[3] * f + floatValues[4] * g + floatValues[5];
            float floatValue5 = floatValues[0] * floatValue + floatValues[1] * g + floatValues[2];
            float floatValue6 = floatValues[3] * floatValue + floatValues[4] * g + floatValues[5];
            float floatValue7 = floatValues[0] * floatValue + floatValues[1] * floatValue2 + floatValues[2];
            float floatValue8 = floatValues[3] * floatValue + floatValues[4] * floatValue2 + floatValues[5];
            float floatValue9 = floatValues[0] * f + floatValues[1] * floatValue2 + floatValues[2];
            float floatValue10 = floatValues[3] * f + floatValues[4] * floatValue2 + floatValues[5];
            float floatValue11 = Math.min(Math.min(floatValue3, floatValue5), Math.min(floatValue7, floatValue9));
            float floatValue12 = Math.min(Math.min(floatValue4, floatValue6), Math.min(floatValue8, floatValue10));
            float floatValue13 = Math.max(Math.max(floatValue3, floatValue5), Math.max(floatValue7, floatValue9));
            float floatValue14 = Math.max(Math.max(floatValue4, floatValue6), Math.max(floatValue8, floatValue10));
            invoke3(string, floatValue11, floatValue12, floatValue13 - floatValue11, floatValue14 - floatValue12);
         } else {
            invoke3(string, f, g, h, i);
         }
      }
   }

   public static HudModule.HudModuleState resolve4(String string, float f, float g, float h, float i, float j) {
      float floatValue15 = measure2(f, 0.0F, Math.max(0.0F, intValue2 - h));
      float floatValue16 = measure2(g, 0.0F, Math.max(0.0F, intValue3 - i));

      for (int intValue = 0; intValue < 6; intValue++) {
         boolean flag = false;

         for (int intValue2 = 0; intValue2 < intValue; intValue2++) {
            HudModule.HudModuleState hudModuleState2 = HUD_MODULE_STATES[intValue2];
            if (hudModuleState2 != null
               && !string.equals(hudModuleState2.text)
               && check3(floatValue15, floatValue16, h, i, hudModuleState2.floatValue - j, hudModuleState2.floatValue2 - j, hudModuleState2.floatValue3 + j * 2.0F, hudModuleState2.floatValue4 + j * 2.0F)) {
               float floatValue17 = hudModuleState2.floatValue2 - i - j;
               float floatValue18 = hudModuleState2.floatValue2 + hudModuleState2.floatValue4 + j;
               float floatValue19 = hudModuleState2.floatValue - h - j;
               float floatValue20 = hudModuleState2.floatValue + hudModuleState2.floatValue3 + j;
               float floatValue21 = floatValue15;
               float floatValue22 = floatValue16;
               float floatValue23 = Float.MAX_VALUE;
               float floatValue24 = measure(floatValue15, floatValue17, f, g, h, i);
               if (floatValue17 >= 0.0F && floatValue24 < floatValue23) {
                  floatValue23 = floatValue24;
                  floatValue22 = floatValue17;
                  floatValue21 = floatValue15;
               }

               float floatValue25 = measure(floatValue15, floatValue18, f, g, h, i);
               if (floatValue18 + i <= intValue3 && floatValue25 < floatValue23) {
                  floatValue23 = floatValue25;
                  floatValue22 = floatValue18;
                  floatValue21 = floatValue15;
               }

               float floatValue26 = measure(floatValue19, floatValue16, f, g, h, i);
               if (floatValue19 >= 0.0F && floatValue26 < floatValue23) {
                  floatValue23 = floatValue26;
                  floatValue21 = floatValue19;
                  floatValue22 = floatValue16;
               }

               float floatValue27 = measure(floatValue20, floatValue16, f, g, h, i);
               if (floatValue20 + h <= intValue2 && floatValue27 < floatValue23) {
                  floatValue21 = floatValue20;
                  floatValue22 = floatValue16;
               }

               floatValue15 = measure2(floatValue21, 0.0F, Math.max(0.0F, intValue2 - h));
               floatValue16 = measure2(floatValue22, 0.0F, Math.max(0.0F, intValue3 - i));
               flag = true;
            }
         }

         if (!flag) {
            break;
         }
      }

      return new HudModule.HudModuleState(string, floatValue15, floatValue16, h, i);
   }

   private static float measure(float f, float g, float h, float i, float j, float k) {
      if (Float.isFinite(f) && Float.isFinite(g)) {
         float floatValue28 = f - h;
         float floatValue29 = g - i;
         float floatValue30 = Math.abs(f + j * 0.5F - intValue2 * 0.5F) * 0.012F;
         float floatValue31 = Math.abs(g + k - intValue3) * 0.004F;
         return floatValue28 * floatValue28 + floatValue29 * floatValue29 + floatValue30 + floatValue31;
      } else {
         return Float.MAX_VALUE;
      }
   }

   private static boolean check3(float f, float g, float h, float i, float j, float k, float l, float m) {
      return f < j + l && f + h > j && g < k + m && g + i > k;
   }

   private static float measure2(float f, float g, float h) {
      return !Float.isFinite(f) ? g : Math.max(g, Math.min(h, f));
   }

   public static final class HudModuleState {
      public String text;
      public float floatValue;
      public float floatValue2;
      public float floatValue3;
      public float floatValue4;

      public HudModuleState() {
      }

      public HudModuleState(String string, float f, float g, float h, float i) {
         this.invoke(string, f, g, h, i);
      }

      public void invoke(String string, float f, float g, float h, float i) {
         this.text = string == null ? "" : string;
         this.floatValue = f;
         this.floatValue2 = g;
         this.floatValue3 = h;
         this.floatValue4 = i;
      }
   }
}
