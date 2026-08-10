package ru.metaculture.protection;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

public final class HudConstructorScreen extends Screen {
   private static volatile boolean flag;
   private static final String PANEL = "panel";
   private static final String HEADER = "header";
   private static final String MODULES = "modules";
   private static final String BINDS = "binds";
   private static final String CONTENT = "content";
   private static final String TITLE = "title";
   private static final String ICON = "icon";
   private static final String SLOTS = "slots";
   private static final String PANELRADIUS = "panelRadius";
   private static final String HEADERRADIUS = "headerRadius";
   private static final String CONTENTRADIUS = "contentRadius";
   private static final String MODULESRADIUS = "modulesRadius";
   private static final String BINDSRADIUS = "bindsRadius";
   private static final String ROWRADIUS = "rowRadius";
   private static final String SLOTRADIUS = "slotRadius";
   private static final String PADDING = "padding";
   private static final String GAP = "gap";
   private static final String HEADERHEIGHT = "headerHeight";
   private static final String ROWHEIGHT = "rowHeight";
   private static final String TITLESIZE = "titleSize";
   private static final String ICONSIZE = "iconSize";
   private static final String BINDWIDTH = "bindWidth";
   private static final String ACCENTWIDTH = "accentWidth";
   private static final String RESET = "reset";
   private static final String CENTERX = "centerX";
   private static final String CENTERY = "centerY";
   private static final String PRESETSOFT = "presetSoft";
   private static final String PRESETCOMPACT = "presetCompact";
   private static final String PRESETSHARP = "presetSharp";
   private static final String CORNERS = "CORNERS";
   private static final String SPACING = "SPACING";
   private static final String SIZE = "SIZE";
   private static final String TYPOGRAPHY = "TYPOGRAPHY";
   private static final String ACTIONS = "ACTIONS";
   private static final String PRESETS = "PRESETS";
   private static final String DRAG_SURFACE_RESIZE_CORNER = "drag surface  ·  resize corner";
   private static final String[] PANEL_2 = new String[]{"panel", "header", "modules", "binds", "content", "title", "icon"};
   private static final String[] PANEL_3 = new String[]{"panel", "header", "content", "slots", "title", "icon"};
   private static final String[] PANEL_4 = new String[]{"panel", "content", "slots"};
   private static final String[] PANEL_5 = new String[]{"panel", "content", "modules"};
   private static final String[] PANEL_6 = new String[]{"panel", "content", "modules", "slots"};
   private static final String[] PANEL_7 = new String[]{"panel", "content", "modules", "binds"};
   private static final String[] PANEL_8 = new String[]{"panel", "header", "modules", "content", "title", "icon"};
   private static final String[] PANEL_9 = new String[]{"panel", "header", "modules", "binds", "content", "title", "icon", "slots"};
   private static final String[] RESET_2 = new String[]{"reset", "centerX", "centerY", "presetSoft", "presetCompact", "presetSharp"};
   private static final HudConstructorScreen.HudConstructorScreenDisplayEntry2[] HUD_CONSTRUCTOR_SCREEN_DISPLAY_ENTRY2S = new HudConstructorScreen.HudConstructorScreenDisplayEntry2[]{
      new HudConstructorScreen.HudConstructorScreenDisplayEntry2("panelRadius", "Panel radius", "CORNERS", 0.0F, 32.0F),
      new HudConstructorScreen.HudConstructorScreenDisplayEntry2("headerRadius", "Header radius", "CORNERS", 0.0F, 28.0F),
      new HudConstructorScreen.HudConstructorScreenDisplayEntry2("contentRadius", "Content radius", "CORNERS", 0.0F, 24.0F),
      new HudConstructorScreen.HudConstructorScreenDisplayEntry2("modulesRadius", "Modules radius", "CORNERS", 0.0F, 24.0F),
      new HudConstructorScreen.HudConstructorScreenDisplayEntry2("bindsRadius", "Binds radius", "CORNERS", 0.0F, 24.0F),
      new HudConstructorScreen.HudConstructorScreenDisplayEntry2("rowRadius", "Row radius", "CORNERS", 0.0F, 22.0F),
      new HudConstructorScreen.HudConstructorScreenDisplayEntry2("slotRadius", "Slot radius", "CORNERS", 0.0F, 14.0F),
      new HudConstructorScreen.HudConstructorScreenDisplayEntry2("padding", "Padding", "SPACING", 2.0F, 18.0F),
      new HudConstructorScreen.HudConstructorScreenDisplayEntry2("gap", "Gap", "SPACING", 0.0F, 18.0F),
      new HudConstructorScreen.HudConstructorScreenDisplayEntry2("headerHeight", "Header height", "SIZE", 0.0F, 48.0F),
      new HudConstructorScreen.HudConstructorScreenDisplayEntry2("rowHeight", "Row height", "SIZE", 14.0F, 42.0F),
      new HudConstructorScreen.HudConstructorScreenDisplayEntry2("titleSize", "Title size", "TYPOGRAPHY", 14.0F, 38.0F),
      new HudConstructorScreen.HudConstructorScreenDisplayEntry2("iconSize", "Icon size", "TYPOGRAPHY", 12.0F, 38.0F),
      new HudConstructorScreen.HudConstructorScreenDisplayEntry2("bindWidth", "Bind column", "TYPOGRAPHY", -24.0F, 90.0F),
      new HudConstructorScreen.HudConstructorScreenDisplayEntry2("accentWidth", "Accent width", "TYPOGRAPHY", 0.0F, 7.0F)
   };
   private static final HudConstructorScreen.HudConstructorScreenDisplayEntry[] HUD_CONSTRUCTOR_SCREEN_DISPLAY_ENTRYS = new HudConstructorScreen.HudConstructorScreenDisplayEntry[]{
      new HudConstructorScreen.HudConstructorScreenDisplayEntry("HUD_HotKeys", "KeyBinds", "HotKeys", FontRegistry.fontObject8, "q", HudConstructorScreen.HudConstructorScreenState2.KEYBINDS, true),
      new HudConstructorScreen.HudConstructorScreenDisplayEntry("HUD_Inventory", "Inventory", "Inventory", FontRegistry.fontObject5, "h", HudConstructorScreen.HudConstructorScreenState2.INVENTORY, true),
      new HudConstructorScreen.HudConstructorScreenDisplayEntry("HUD_Potions", "Potions", "Potions", FontRegistry.fontObject5, "t", HudConstructorScreen.HudConstructorScreenState2.POTIONS, true),
      new HudConstructorScreen.HudConstructorScreenDisplayEntry("HUD_CoolDowns", "Cooldowns", "Cool Downs", FontRegistry.fontObject5, "g", HudConstructorScreen.HudConstructorScreenState2.COOLDOWNS, true),
      new HudConstructorScreen.HudConstructorScreenDisplayEntry("HUD_Info", "Information", "PlayerInfo", FontRegistry.fontObject5, "e", HudConstructorScreen.HudConstructorScreenState2.INFO, true),
      new HudConstructorScreen.HudConstructorScreenDisplayEntry("HUD_WaterMark", "Watermark", "Watermark", BrandMark.font(), BrandMark.GLYPH, HudConstructorScreen.HudConstructorScreenState2.WATERMARK, true),
      new HudConstructorScreen.HudConstructorScreenDisplayEntry("HUD_ArrayList", "ArrayList", "ArrayList", FontRegistry.fontObject8, "n", HudConstructorScreen.HudConstructorScreenState2.ARRAYLIST, false),
      new HudConstructorScreen.HudConstructorScreenDisplayEntry("HUD_TargetHUD", "TargetHUD", "TargetHud", FontRegistry.fontObject8, "r", HudConstructorScreen.HudConstructorScreenState2.TARGET, false),
      new HudConstructorScreen.HudConstructorScreenDisplayEntry("hud_armor", "Armor", "Armor", FontRegistry.fontObject5, "h", HudConstructorScreen.HudConstructorScreenState2.SLOTS, false),
      new HudConstructorScreen.HudConstructorScreenDisplayEntry("HUD_HotBar", "HotBar", "HotBar", FontRegistry.fontObject5, "h", HudConstructorScreen.HudConstructorScreenState2.HOTBAR, false),
      new HudConstructorScreen.HudConstructorScreenDisplayEntry(
         "HUD_Notifications", "Notifications", "Notifications", FontRegistry.fontObject8, "l", HudConstructorScreen.HudConstructorScreenState2.NOTIFICATION, false
      ),
      new HudConstructorScreen.HudConstructorScreenDisplayEntry("HUD_MusicPlayer", "Media", "MediaPlayer", FontRegistry.fontObject8, "m", HudConstructorScreen.HudConstructorScreenState2.MEDIA, false),
      new HudConstructorScreen.HudConstructorScreenDisplayEntry("HUD_ServerHelper", "Server", "Server Helper", FontRegistry.fontObject5, "e", HudConstructorScreen.HudConstructorScreenState2.SERVER, false)
   };
   private static final String[] HITAURA = new String[]{"HitAura", "AutoTotem", "Speed", "InventoryMove"};
   private static final String[] R = new String[]{"R", "F", "V", "G"};
   private static final String[] STRENGTH_III = new String[]{"Strength III", "Fire Resistance", "Poison II"};
   private static final String[] TEXT158 = new String[]{"1:58", "6:40", "0:12"};
   private static final String[] ENDER_PEARL = new String[]{"Ender Pearl", "Golden Apple", "Chorus Fruit"};
   private static final String[] TEXT84S = new String[]{"8.4s", "2.1s", "0.7s"};
   private static final String[] BPS = new String[]{"BPS", "TPS", "XYZ", "PING"};
   private static final String[] TEXT742 = new String[]{"7.42", "20.0", "120 64 -80", "42 ms"};
   private static final String[] MODULE_TOGGLED = new String[]{"Module toggled", "Config saved", "Friend joined"};
   private static final String[] NOW = new String[]{"now", "1s", "4s"};
   private static final String[] WILD = new String[]{"north", "fr1zy", "144 fps", "12:40"};
   private static final String[] D = new String[]{BrandMark.GLYPH, "r", "u", "y"};
   private static final String[] HITAURA_2 = new String[]{"HitAura", "AutoTotem", "ElytraFly", "NoSlow"};
   private static final String[] MIDNIGHT_DRIVE = new String[]{"Midnight Drive", "2:18 / 3:42", "Volume"};
   private static final String[] PLAYING = new String[]{"PLAYING", "", "72%"};
   private static final String[] FUNTIME = new String[]{"FunTime", "Anarchy-01", "Online"};
   private static final String[] EU = new String[]{"EU", "42 ms", "128"};
   private static final String[] TEXT = new String[]{"", "", "", ""};
   private static final String[] KEYS = new String[]{
      "Keys", "Inventory", "Potions", "Cooldowns", "Info", "Watermark", "ArrayList", "Target", "Armor", "HotBar", "Alerts", "Media", "Server"
   };
   private static final String PREVIEW_RESIZE = "preview.resize";
   private final HudConstructorScreen.HudConstructorScreenState[] hudConstructorScreenStates = new HudConstructorScreen.HudConstructorScreenState[HUD_CONSTRUCTOR_SCREEN_DISPLAY_ENTRYS.length];
   private final Map<String, HudConstructorScreen.HudConstructorScreenState> valuesByKey = new HashMap<>();
   private final Map<String, HudConstructorScreen.HudConstructorScreenState> valuesByKey2 = new HashMap<>();
   private final Map<String, HudConstructorScreen.HudConstructorScreenState> valuesByKey3 = new HashMap<>();
   private final Map<String, Animation> valuesByKey4 = new HashMap<>();
   private final Map<String, Animation> valuesByKey5 = new HashMap<>();
   private final Map<String, Animation> valuesByKey6 = new HashMap<>();
   private final Map<String, String> valuesByKey7 = new HashMap<>();
   private final HudEditorRenderer.HudEditorRendererState3[] hudEditorRendererState3s = new HudEditorRenderer.HudEditorRendererState3[8];
   private final Animation animation = new Animation();
   private final Animation animation2 = new Animation();
   private final Animation animation3 = new Animation();
   private final Animation animation4 = new Animation();
   private final Animation animation5 = new Animation();
   private final Animation animation6 = new Animation();
   private final Animation animation7 = new Animation();
   private final Animation animation8 = new Animation();
   private String text;
   private float floatValue;
   private float floatValue2;
   private float floatValue3;
   private float floatValue4;
   private float floatValue5 = 1.0F;
   private float floatValue6 = 1.0F;
   private float floatValue7;
   private float floatValue8;
   private final float[] floats = new float[HUD_CONSTRUCTOR_SCREEN_DISPLAY_ENTRYS.length];
   private final float[] floats2 = new float[HUD_CONSTRUCTOR_SCREEN_DISPLAY_ENTRYS.length];
   private boolean flag2;
   private boolean flag3;
   private boolean flag4;
   private boolean flag5;
   private float floatValue9;
   private float floatValue10;
   private float floatValue11;
   private float floatValue12;
   private float floatValue13;
   private float floatValue14;
   private float floatValue15;
   private float floatValue16;
   private float floatValue17;
   private float floatValue18;
   private float floatValue19;
   private float floatValue20;
   private boolean flag6;
   private boolean flag7;
   private HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState = HudConstructorScreen.HudConstructorScreenState.resolve4();
   private HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState2 = HudConstructorScreen.HudConstructorScreenState.resolve4();
   private HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState3 = HudConstructorScreen.HudConstructorScreenState.resolve4();
   private HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState4 = HudConstructorScreen.HudConstructorScreenState.resolve4();
   private HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState5 = HudConstructorScreen.HudConstructorScreenState.resolve4();
   private HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState6 = HudConstructorScreen.HudConstructorScreenState.resolve4();
   private HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState7 = HudConstructorScreen.HudConstructorScreenState.resolve4();
   private HudConstructorScreen.HudConstructorScreenState DynamicButtonSetting = HudConstructorScreen.HudConstructorScreenState.resolve4();
   private HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState8 = HudConstructorScreen.HudConstructorScreenState.resolve4();
   private HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState9 = HudConstructorScreen.HudConstructorScreenState.resolve4();
   private HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState10 = HudConstructorScreen.HudConstructorScreenState.resolve4();
   private HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState11 = HudConstructorScreen.HudConstructorScreenState.resolve4();
   private HudConstructorScreen.HudConstructorScreenState SpacerSetting = HudConstructorScreen.HudConstructorScreenState.resolve4();
   private HudConstructorScreen.HudConstructorScreenState FoundryShaderSetting = HudConstructorScreen.HudConstructorScreenState.resolve4();
   private String panel = "panel";
   private String text2;
   private String text3;
   private int intValue;
   private String text4 = "";
   private String text5 = "";
   private String text6 = "";
   private float floatValue21 = 1.0F;
   private float floatValue22;
   private float floatValue23;
   private HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState12 = HudConstructorScreen.HudConstructorScreenState.resolve4();
   private HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState13 = HudConstructorScreen.HudConstructorScreenState.resolve4();
   private HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState14 = HudConstructorScreen.HudConstructorScreenState.resolve4();
   private static final ThemePalette THEME_PALETTE = ThemePalette.resolve2();
   private Theme theme;
   private ColorScheme colorScheme;

   public HudConstructorScreen() {
      super(Text.literal("HUD Constructor"));
      invoke2();
      this.animation.invoke(0.0);

      for (int intValue = 0; intValue < this.hudConstructorScreenStates.length; intValue++) {
         this.hudConstructorScreenStates[intValue] = HudConstructorScreen.HudConstructorScreenState.resolve4();
      }

      for (int intValue2 = 0; intValue2 < this.hudEditorRendererState3s.length; intValue2++) {
         this.hudEditorRendererState3s[intValue2] = new HudEditorRenderer.HudEditorRendererState3();
      }

      for (HudConstructorScreen.HudConstructorScreenDisplayEntry2 hudConstructorScreenDisplayEntry2 : HUD_CONSTRUCTOR_SCREEN_DISPLAY_ENTRY2S) {
         this.valuesByKey.put(hudConstructorScreenDisplayEntry2.id, HudConstructorScreen.HudConstructorScreenState.resolve4());
      }

      for (String text : PANEL_9) {
         this.valuesByKey2.put(text, HudConstructorScreen.HudConstructorScreenState.resolve4());
      }

      this.valuesByKey3.put("close", HudConstructorScreen.HudConstructorScreenState.resolve4());
      this.valuesByKey3.put("reset", HudConstructorScreen.HudConstructorScreenState.resolve4());
      this.valuesByKey3.put("centerX", HudConstructorScreen.HudConstructorScreenState.resolve4());
      this.valuesByKey3.put("centerY", HudConstructorScreen.HudConstructorScreenState.resolve4());
      this.valuesByKey3.put("presetSoft", HudConstructorScreen.HudConstructorScreenState.resolve4());
      this.valuesByKey3.put("presetCompact", HudConstructorScreen.HudConstructorScreenState.resolve4());
      this.valuesByKey3.put("presetSharp", HudConstructorScreen.HudConstructorScreenState.resolve4());
      this.invoke43();
      this.invoke46();
      this.invoke47();
   }

   public boolean shouldPause() {
      return false;
   }

   public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
      this.invoke48(this.measure16((double)mouseX), this.measure17((double)mouseY));
      super.render(context, mouseX, mouseY, deltaTicks);
   }

   public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
   }

   public void renderInGameBackground(DrawContext context) {
   }

   public void invoke(RenderManager renderManager, DrawContext drawContext, int i, int j) {
      if (!HudEditorAccessControl.check()) {
         if (this.client != null) {
            this.client.setScreen(null);
         }
      } else if (renderManager != null && i > 0 && j > 0) {
         this.invoke49();
         this.animation.check();
         this.animation.resolve4(1.0, 0.42F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         this.animation2.check();
         this.animation2
            .resolve4(this.text2 == null && !this.flag2 && !this.flag3 ? 0.0 : 1.0, 0.18F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         float floatValue = measure18(this.animation.measure3(), 0.0F, 1.0F);
         ColorScheme colorScheme = this.resolve();
         this.invoke4(i, j);
         this.invoke3(renderManager, i, j, floatValue, colorScheme);
         renderManager.invoke65(floatValue);
         float floatValue2 = 0.945F + 0.055F * floatValue;
         renderManager.invoke63(floatValue2, floatValue2, i * 0.5F, j * 0.5F);
         renderManager.invoke56(0.0F, (1.0F - floatValue) * this.measure(24.0F));

         try {
            this.invoke5(renderManager, colorScheme);
            this.invoke10(renderManager, colorScheme);
            this.invoke11(renderManager, colorScheme);
            this.invoke24(renderManager, colorScheme);
         } finally {
            renderManager.invoke57();
            renderManager.invoke64();
            renderManager.invoke66();
         }
      }
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      this.invoke48(this.measure16(mouseX), this.measure17(mouseY));
      if (button != 0) {
         return true;
      } else {
         HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState = this.valuesByKey3.get("close");
         if (hudConstructorScreenState != null && hudConstructorScreenState.check(this.floatValue, this.floatValue2)) {
            this.close();
            return true;
         } else {
            if (this.hudConstructorScreenState13.check(this.floatValue, this.floatValue2)) {
               for (int intValue3 = 0; intValue3 < this.hudConstructorScreenStates.length; intValue3++) {
                  if (this.hudConstructorScreenStates[intValue3].check(this.floatValue, this.floatValue2)) {
                     this.intValue = intValue3;
                     this.panel = this.resolve3(HUD_CONSTRUCTOR_SCREEN_DISPLAY_ENTRYS[intValue3].kind);
                     this.floatValue7 = 0.0F;
                     this.floats[intValue3] = 0.0F;
                     this.floats2[intValue3] = 0.0F;
                     this.invoke46();
                     this.invoke47();
                     return true;
                  }
               }
            }

            String text2 = this.resolve8(this.floatValue, this.floatValue2);
            if (text2 != null) {
               this.invoke34(text2);
               return true;
            } else {
               String text3 = this.resolve9(this.floatValue, this.floatValue2);
               if (text3 != null) {
                  this.panel = text3;
                  this.invoke47();
                  return true;
               } else {
                  String text4 = this.resolve7(this.floatValue, this.floatValue2);
                  if (text4 != null) {
                     this.text3 = text4;
                     this.invoke33(this.floatValue);
                     return true;
                  } else if (this.SpacerSetting.check(this.floatValue, this.floatValue2)) {
                     this.flag3 = true;
                     this.panel = "panel";
                     this.floatValue9 = this.floatValue;
                     this.floatValue10 = this.floatValue2;
                     this.floatValue11 = Math.max(1.0F, this.hudConstructorScreenState4.floatValue3);
                     this.floatValue12 = Math.max(1.0F, this.hudConstructorScreenState4.floatValue4);
                     this.floatValue13 = this.floatValue11 / Math.max(0.001F, this.floatValue5);
                     this.floatValue14 = this.floatValue12 / Math.max(0.001F, this.floatValue6);
                     this.floatValue15 = HudEditorRenderer.getINSTANCE().measure8(this.getId(), this.floatValue13, this.floatValue14);
                     this.floatValue16 = Math.max(0.001F, this.floatValue5);
                     this.floatValue17 = this.floats[this.intValue];
                     this.floatValue18 = this.floats2[this.intValue];
                     HudEditorRenderer.HudEditorRendererData2 hudEditorRendererData2 = HudEditorRenderer.getINSTANCE().getValuesByKey2().get(this.getId());
                     this.floatValue19 = hudEditorRendererData2 == null ? 0.5F : hudEditorRendererData2.nx();
                     this.floatValue20 = hudEditorRendererData2 == null ? 0.5F : hudEditorRendererData2.ny();
                     this.floatValue3 = this.floatValue;
                     this.floatValue4 = this.floatValue2;
                     return true;
                  } else {
                     String text5 = this.resolve6(this.floatValue, this.floatValue2);
                     if (text5 == null) {
                        return true;
                     } else {
                        boolean flag = text5.equals(this.panel) && this.check5(text5);
                        this.panel = text5;
                        this.invoke47();
                        if (flag) {
                           this.text2 = text5;
                        } else {
                           this.flag2 = true;
                        }

                        this.floatValue3 = this.floatValue;
                        this.floatValue4 = this.floatValue2;
                        return true;
                     }
                  }
               }
            }
         }
      }
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      this.invoke48(this.measure16(mouseX), this.measure17(mouseY));
      this.text2 = null;
      this.flag2 = false;
      this.flag3 = false;
      this.flag4 = false;
      this.flag5 = false;
      this.text3 = null;
      this.invoke42();
      return true;
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      this.invoke48(this.measure16(mouseX), this.measure17(mouseY));
      HudLayoutManager.HudLayoutManagerState hudLayoutManagerState = this.resolve13();
      if (this.text3 != null) {
         this.invoke33(this.floatValue);
         return true;
      } else if (this.flag3) {
         this.invoke31(this.floatValue, this.floatValue2);
         return true;
      } else if (this.flag2) {
         this.invoke30(this.floatValue - this.floatValue3, this.floatValue2 - this.floatValue4);
         this.floatValue3 = this.floatValue;
         this.floatValue4 = this.floatValue2;
         return true;
      } else if (this.text2 != null) {
         float floatValue3 = (this.floatValue - this.floatValue3) / Math.max(0.001F, this.floatValue5);
         float floatValue4 = (this.floatValue2 - this.floatValue4) / Math.max(0.001F, this.floatValue6);
         if ("title".equals(this.text2)) {
            hudLayoutManagerState.hudLayoutManagerState3.floatValue += floatValue3;
            hudLayoutManagerState.hudLayoutManagerState3.floatValue2 += floatValue4;
         } else if ("icon".equals(this.text2)) {
            hudLayoutManagerState.hudLayoutManagerState32.floatValue += floatValue3;
         } else if ("modules".equals(this.text2)) {
            hudLayoutManagerState.hudLayoutManagerState33.floatValue += floatValue3;
            hudLayoutManagerState.hudLayoutManagerState33.floatValue2 += floatValue4;
         } else if ("binds".equals(this.text2)) {
            hudLayoutManagerState.hudLayoutManagerState34.floatValue += floatValue3;
            hudLayoutManagerState.hudLayoutManagerState34.floatValue2 += floatValue4;
         }

         hudLayoutManagerState.invoke();
         this.flag6 = true;
         this.invoke47();
         this.floatValue3 = this.floatValue;
         this.floatValue4 = this.floatValue2;
         return true;
      } else {
         return true;
      }
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
      this.invoke48(this.measure16(mouseX), this.measure17(mouseY));
      if (this.hudConstructorScreenState12.check(this.floatValue, this.floatValue2) && this.floatValue23 > 0.0F) {
         this.floatValue22 = measure18(this.floatValue22 - (float)verticalAmount * this.measure(28.0F), 0.0F, this.floatValue23);
         return true;
      } else if (this.hudConstructorScreenState9.check(this.floatValue, this.floatValue2) && this.floatValue8 > 0.0F) {
         this.floatValue7 = measure18(this.floatValue7 - (float)verticalAmount * this.measure(28.0F), 0.0F, this.floatValue8);
         return true;
      } else {
         return true;
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) {
         this.close();
         return true;
      } else if (keyCode == 82) {
         this.invoke37();
         return true;
      } else if (keyCode == 67) {
         this.invoke35(true, false);
         this.invoke42();
         return true;
      } else {
         return super.keyPressed(keyCode, scanCode, modifiers);
      }
   }

   public void close() {
      this.invoke42();
      super.close();
   }

   public void removed() {
      this.invoke42();
      super.removed();
   }

   private static void invoke2() {
      if (!flag) {
         flag = true;
         EventManager.register(new Object() {
            @EventHandler(
               priority = 4
            )
            public void onHudRender(HudRenderEvent hudRenderEvent) {
               if (hudRenderEvent.getClient() != null && hudRenderEvent.getClient().currentScreen instanceof HudConstructorScreen hudConstructorScreen) {
                  hudConstructorScreen.invoke(hudRenderEvent.getRenderManager(), hudRenderEvent.getDrawContext(), hudRenderEvent.getIntValue(), hudRenderEvent.getIntValue2());
                  if (hudRenderEvent.getRenderManager() != null) {
                     hudRenderEvent.getRenderManager().invoke20();
                  }
               }
            }
         });
      }
   }

   private void invoke3(RenderManager renderManager2, int i, int j, float f, ColorScheme colorScheme2) {
      int intValue4 = colorScheme2.isFlag() ? compute4(12, 14, 20, Math.round(48.0F * f)) : compute4(0, 0, 0, Math.round(96.0F * f));
      renderManager2.invoke5(0.0F, 0.0F, (float)i, (float)j, 0.0F, intValue4);
      renderManager2.invoke30(
         0.0F,
         0.0F,
         (float)i,
         (float)j,
         ColorScheme.compute6(colorScheme2.getIntValue14(), Math.round(14.0F * f)),
         compute4(0, 0, 0, Math.round(26.0F * f)),
         compute4(0, 0, 0, Math.round(44.0F * f)),
         ColorScheme.compute6(colorScheme2.getIntValue15(), Math.round(16.0F * f))
      );
      float floatValue5 = this.animation2.measure3() * f;
      if (floatValue5 > 0.01F) {
         int intValue5 = this.compute();
         HudEditorRenderer.getINSTANCE().invoke4(i, j, this.hudEditorRendererState3s, intValue5, this.text2, this.floatValue, this.floatValue2, floatValue5);
      }
   }

   private int compute() {
      int intValue6 = 0;
      intValue6 = this.compute2(intValue6, "panel", this.hudConstructorScreenState4, "panel".equals(this.panel) ? 0.78F : 0.24F);
      intValue6 = this.compute2(intValue6, "header", this.hudConstructorScreenState5, "header".equals(this.panel) ? 0.72F : 0.22F);
      intValue6 = this.compute2(intValue6, "modules", this.hudConstructorScreenState7, "modules".equals(this.panel) ? 0.92F : 0.34F);
      intValue6 = this.compute2(intValue6, "binds", this.DynamicButtonSetting, "binds".equals(this.panel) ? 0.92F : 0.34F);
      intValue6 = this.compute2(intValue6, "title", this.hudConstructorScreenState2, "title".equals(this.panel) ? 0.62F : 0.18F);
      intValue6 = this.compute2(intValue6, "icon", this.hudConstructorScreenState3, "icon".equals(this.panel) ? 0.72F : 0.2F);
      return this.compute2(intValue6, "slots", this.hudConstructorScreenState8, "slots".equals(this.panel) ? 0.84F : 0.24F);
   }

   private int compute2(int i, String string, HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState2, float f) {
      if (i < this.hudEditorRendererState3s.length && hudConstructorScreenState2 != null && !(hudConstructorScreenState2.floatValue3 <= 1.0F) && !(hudConstructorScreenState2.floatValue4 <= 1.0F)) {
         float floatValue6 = (float)Math.sqrt(hudConstructorScreenState2.floatValue3 * hudConstructorScreenState2.floatValue3 + hudConstructorScreenState2.floatValue4 * hudConstructorScreenState2.floatValue4) * 0.52F;
         this.hudEditorRendererState3s[i]
            .invoke(
               string,
               hudConstructorScreenState2.floatValue + hudConstructorScreenState2.floatValue3 * 0.5F,
               hudConstructorScreenState2.floatValue2 + hudConstructorScreenState2.floatValue4 * 0.5F,
               Math.max(24.0F, floatValue6),
               f,
               hudConstructorScreenState2.floatValue3,
               hudConstructorScreenState2.floatValue4
            );
         return i + 1;
      } else {
         return i;
      }
   }

   private void invoke4(int i, int j) {
      this.floatValue21 = measure18(j / 760.0F, 0.82F, 3.0F);
      float floatValue7 = Math.max(this.measure(28.0F), i * 0.05F);
      float floatValue8 = Math.max(this.measure(24.0F), j * 0.06F);
      float floatValue9 = Math.max(this.measure(320.0F), i - floatValue7 * 2.0F);
      float floatValue10 = Math.max(this.measure(240.0F), j - floatValue8 * 2.0F);
      float floatValue11 = measure18(i * 0.76F, Math.min(this.measure(560.0F), floatValue9), floatValue9);
      float floatValue12 = measure18(j * 0.78F, Math.min(this.measure(380.0F), floatValue10), floatValue10);
      float floatValue13 = floatValue11 / Math.max(1.0F, floatValue12);
      if (floatValue13 > 2.05F) {
         floatValue11 = floatValue12 * 2.05F;
      } else if (floatValue13 < 1.34F) {
         floatValue12 = floatValue11 / 1.34F;
      }

      float floatValue14 = (i - floatValue11) * 0.5F;
      float floatValue15 = (j - floatValue12) * 0.5F;
      this.hudConstructorScreenState.resolve(Math.round(floatValue14), Math.round(floatValue15), Math.round(floatValue11), Math.round(floatValue12));
      floatValue14 = this.hudConstructorScreenState.floatValue;
      floatValue15 = this.hudConstructorScreenState.floatValue2;
      floatValue11 = this.hudConstructorScreenState.floatValue3;
      floatValue12 = this.hudConstructorScreenState.floatValue4;
      float floatValue16 = this.measure(14.0F);
      float floatValue17 = floatValue15 + this.measure(52.0F);
      float floatValue18 = floatValue15 + floatValue12 - floatValue16;
      float floatValue19 = Math.max(this.measure(120.0F), floatValue18 - floatValue17);
      float floatValue20 = this.measure(12.0F);
      float floatValue21 = floatValue14 + floatValue16;
      float floatValue22 = floatValue11 - floatValue16 * 2.0F;
      float floatValue23 = this.measure(150.0F);
      float floatValue24 = this.measure(300.0F);
      float floatValue25 = measure18(floatValue22 * 0.22F, Math.min(floatValue23, floatValue22 * 0.3F), floatValue24);
      float floatValue26 = measure18(floatValue22 * 0.26F, Math.min(floatValue23, floatValue22 * 0.3F), floatValue24);
      float floatValue27 = floatValue22 - floatValue25 - floatValue26 - floatValue20 * 2.0F;
      float floatValue28 = floatValue22 * 0.34F;
      if (floatValue27 < floatValue28 && floatValue25 + floatValue26 > 0.0F) {
         float floatValue29 = floatValue28 - floatValue27;
         float floatValue30 = floatValue25 + floatValue26;
         floatValue25 -= floatValue29 * (floatValue25 / floatValue30);
         floatValue26 -= floatValue29 * (floatValue26 / floatValue30);
         floatValue27 = floatValue22 - floatValue25 - floatValue26 - floatValue20 * 2.0F;
      }

      this.hudConstructorScreenState12.resolve(Math.round(floatValue21), Math.round(floatValue17), Math.round(floatValue25), Math.round(floatValue19));
      this.hudConstructorScreenState14.resolve(Math.round(floatValue21 + floatValue25 + floatValue20), Math.round(floatValue17), Math.round(Math.max(this.measure(80.0F), floatValue27)), Math.round(floatValue19));
      this.hudConstructorScreenState9
         .resolve(Math.round(floatValue21 + floatValue25 + floatValue20 + this.hudConstructorScreenState14.floatValue3 + floatValue20), Math.round(floatValue17), Math.round(floatValue26), Math.round(floatValue19));
   }

   private void invoke5(RenderManager renderManager3, ColorScheme colorScheme3) {
      float floatValue31 = this.hudConstructorScreenState.floatValue;
      float floatValue32 = this.hudConstructorScreenState.floatValue2;
      float floatValue33 = this.hudConstructorScreenState.floatValue3;
      float floatValue34 = this.hudConstructorScreenState.floatValue4;
      float floatValue35 = this.measure(18.0F);
      renderManager3.invoke41(
         floatValue31, floatValue32, floatValue33, floatValue34, floatValue35, this.measure(30.0F), this.measure(8.0F), compute4(0, 0, 0, colorScheme3.isFlag() ? 34 : 150)
      );
      renderManager3.invoke41(
         floatValue31, floatValue32, floatValue33, floatValue34, floatValue35, this.measure(6.0F), this.measure(5.0F), compute5(colorScheme3, colorScheme3.isFlag() ? 14 : 24)
      );
      renderManager3.invoke48(30.0F);
      renderManager3.invoke44(floatValue31, floatValue32, floatValue33, floatValue34, floatValue35, colorScheme3.isFlag() ? 0.96F : 0.92F);
      renderManager3.invoke5(floatValue31, floatValue32, floatValue33, floatValue34, floatValue35, colorScheme3.getIntValue());
      renderManager3.invoke28(floatValue31, floatValue32, floatValue33, floatValue34, floatValue35, colorScheme3.getIntValue8(), Math.max(1.0F, this.measure(1.0F)));
      renderManager3.invoke69(
         FontRegistry.fontObject4,
         floatValue31 + this.measure(24.0F),
         measure20(floatValue32 + this.measure(30.0F), this.measure(21.0F)),
         this.measure(21.0F),
         "HUD Constructor",
         colorScheme3.getIntValue13()
      );
      float floatValue36 = Math.round(this.measure(30.0F));
      HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState3 = this.valuesByKey3.get("close");
      hudConstructorScreenState3.resolve(Math.round(floatValue31 + floatValue33 - floatValue36 - this.measure(16.0F)), Math.round(floatValue32 + this.measure(30.0F) - floatValue36 * 0.5F), floatValue36, floatValue36);
      float floatValue37 = this.measure14("close", hudConstructorScreenState3.check(this.floatValue, this.floatValue2) ? 1.0F : 0.0F);
      float floatValue38 = hudConstructorScreenState3.floatValue + hudConstructorScreenState3.floatValue3 * 0.5F;
      float floatValue39 = hudConstructorScreenState3.floatValue2 + hudConstructorScreenState3.floatValue4 * 0.5F;
      renderManager3.invoke5(
         hudConstructorScreenState3.floatValue,
         hudConstructorScreenState3.floatValue2,
         hudConstructorScreenState3.floatValue3,
         hudConstructorScreenState3.floatValue4,
         this.measure(9.0F),
         ColorScheme.compute7(
            ColorScheme.compute6(colorScheme3.getIntValue13(), colorScheme3.isFlag() ? 14 : 10),
            ColorScheme.compute6(colorScheme3.compute2(), 46),
            floatValue37
         )
      );
      renderManager3.invoke28(
         hudConstructorScreenState3.floatValue,
         hudConstructorScreenState3.floatValue2,
         hudConstructorScreenState3.floatValue3,
         hudConstructorScreenState3.floatValue4,
         this.measure(9.0F),
         ColorScheme.compute7(colorScheme3.getIntValue8(), ColorScheme.compute6(colorScheme3.compute2(), 90), floatValue37),
         1.0F
      );
      this.invoke53(
         renderManager3,
         floatValue38,
         floatValue39,
         this.measure(5.0F),
         Math.max(1.5F, this.measure(2.0F)),
         ColorScheme.compute7(compute7(colorScheme3), colorScheme3.compute2(), floatValue37 * 0.85F)
      );
   }

   private float measure(float f) {
      return f * this.floatValue21;
   }

   private ColorScheme resolve() {
      Theme theme = null;

      try {
         if (WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null) {
            theme = WildClient.INSTANCE.themeManager.getTheme();
         }
      } catch (Throwable exception) {
      }

      if (theme == null) {
         theme = Theme.WILD;
      }

      if (theme != this.theme || this.colorScheme == null) {
         this.theme = theme;
         this.colorScheme = ColorScheme.resolve2(theme, THEME_PALETTE.check(theme));
      }

      return this.colorScheme;
   }

   private void invoke6(RenderManager renderManager4, ColorScheme colorScheme4, float f, float g, float h, float i) {
      float floatValue40 = Math.round(f);
      float floatValue41 = Math.round(g);
      float floatValue42 = Math.round(h);
      float floatValue43 = Math.round(i);
      float floatValue44 = this.measure(14.0F);
      int intValue7 = ColorScheme.compute6(
         ColorScheme.compute7(colorScheme4.getIntValue2(), compute4(0, 0, 0, 255), colorScheme4.isFlag() ? 0.02F : 0.05F),
         colorScheme4.isFlag() ? 182 : 186
      );
      renderManager4.invoke41(
         floatValue40, floatValue41, floatValue42, floatValue43, floatValue44, this.measure(16.0F), this.measure(2.0F), compute4(0, 0, 0, colorScheme4.isFlag() ? 22 : 82)
      );
      renderManager4.invoke48(26.0F);
      renderManager4.invoke44(floatValue40, floatValue41, floatValue42, floatValue43, floatValue44, colorScheme4.isFlag() ? 0.74F : 0.86F);
      renderManager4.invoke5(floatValue40, floatValue41, floatValue42, floatValue43, floatValue44, intValue7);
      renderManager4.invoke28(floatValue40, floatValue41, floatValue42, floatValue43, floatValue44, colorScheme4.getIntValue7(), Math.max(1.0F, this.measure(1.0F)));
   }

   private void invoke7(RenderManager renderManager5, ColorScheme colorScheme5, float f, float g, float h) {
      renderManager5.invoke5(
         (float)Math.round(f), (float)Math.round(g), (float)Math.round(h), Math.max(1.0F, this.measure(1.0F)), 0.0F, colorScheme5.getIntValue7()
      );
   }

   private void invoke8(RenderManager renderManager6, ColorScheme colorScheme6, float f, float g, float h, float i, float j, float k) {
      if (!(j <= 0.0F) && !(h <= 0.0F)) {
         float floatValue45 = measure18(0.1F + 0.9F * k, 0.0F, 1.0F);
         float floatValue46 = Math.max(this.measure(30.0F), h * (h / (h + j)));
         float floatValue47 = g + (h - floatValue46) * (i / Math.max(1.0F, j));
         float floatValue48 = Math.max(1.0F, (float)Math.round(this.measure(2.5F)));
         float floatValue49 = floatValue48 * 0.5F;
         renderManager6.invoke5(
            (float)Math.round(f),
            (float)Math.round(g),
            floatValue48,
            (float)Math.round(h),
            floatValue49,
            ColorScheme.compute6(colorScheme6.getIntValue13(), Math.round((colorScheme6.isFlag() ? 16.0F : 10.0F) * floatValue45))
         );
         renderManager6.invoke5(
            (float)Math.round(f),
            (float)Math.round(floatValue47),
            floatValue48,
            (float)Math.round(floatValue46),
            floatValue49,
            ColorScheme.compute6(colorScheme6.getIntValue14(), Math.round((colorScheme6.isFlag() ? 120.0F : 110.0F) * floatValue45))
         );
      }
   }

   private void invoke9(RenderManager renderManager7, ColorScheme colorScheme7, boolean bl, boolean bl2, float f, float g) {
      String text6 = bl2 ? (bl ? "LIVE" : "OFF") : "PREVIEW";
      int intValue8 = bl2
         ? (bl ? colorScheme7.compute() : ColorScheme.compute6(colorScheme7.getIntValue13(), colorScheme7.isFlag() ? 120 : 110))
         : colorScheme7.compute3();
      boolean flag2 = bl2 && bl;
      float floatValue50 = flag2 ? measure19(1900.0F, 0.0F) : 0.0F;
      float floatValue51 = this.measure(12.0F);
      float floatValue52 = TextMeasureCache.measure(FontRegistry.fontObject4, text6, floatValue51);
      float floatValue53 = Math.max(1.0F, (float)Math.round(this.measure(6.0F)));
      float floatValue54 = Math.round(f - floatValue52);
      float floatValue55 = Math.round(floatValue54 - this.measure(8.0F) - floatValue53);
      float floatValue56 = Math.round(g - floatValue53 * 0.5F);
      if (flag2 && floatValue50 > 0.0F) {
         renderManager7.invoke41(
            floatValue55,
            floatValue56,
            floatValue53,
            floatValue53,
            floatValue53 * 0.5F,
            this.measure(5.0F),
            this.measure(0.5F),
            ColorScheme.compute6(intValue8, Math.round(35.0F + floatValue50 * 120.0F))
         );
      }

      renderManager7.invoke5(floatValue55, floatValue56, floatValue53, floatValue53, floatValue53 * 0.5F, ColorScheme.compute6(intValue8, flag2 ? Math.round(170.0F + floatValue50 * 85.0F) : 220));
      renderManager7.invoke69(
         FontRegistry.fontObject4, floatValue54, Math.round(measure20(g, floatValue51)), floatValue51, text6, ColorScheme.compute6(intValue8, flag2 ? 235 : 210)
      );
   }

   private String[] resolve2(HudConstructorScreen.HudConstructorScreenState2 hudConstructorScreenState22) {
      return switch (hudConstructorScreenState22) {
         case INVENTORY -> PANEL_3;
         default -> PANEL_2;
         case INFO, MEDIA, SERVER -> PANEL_8;
         case WATERMARK -> PANEL_7;
         case ARRAYLIST -> PANEL_5;
         case TARGET -> PANEL_6;
         case SLOTS, HOTBAR -> PANEL_4;
      };
   }

   private String resolve3(HudConstructorScreen.HudConstructorScreenState2 hudConstructorScreenState23) {
      String[] texts = this.resolve2(hudConstructorScreenState23);

      for (String text7 : texts) {
         if ("panel".equals(text7)) {
            return text7;
         }
      }

      return texts[0];
   }

   private static boolean check(String[] strings, String string) {
      for (String text8 : strings) {
         if (text8.equals(string)) {
            return true;
         }
      }

      return false;
   }

   private boolean check2(HudConstructorScreen.HudConstructorScreenDisplayEntry hudConstructorScreenDisplayEntry, String string) {
      HudConstructorScreen.HudConstructorScreenState2 hudConstructorScreenState24 = hudConstructorScreenDisplayEntry.kind;
      if (hudConstructorScreenState24 != HudConstructorScreen.HudConstructorScreenState2.KEYBINDS || !"contentRadius".equals(string) && !"rowRadius".equals(string) && !"slotRadius".equals(string)) {
         return switch (string) {
            case "panelRadius", "padding", "gap" -> true;
            case "slotRadius" -> hudConstructorScreenState24 == HudConstructorScreen.HudConstructorScreenState2.INVENTORY
               || hudConstructorScreenState24 == HudConstructorScreen.HudConstructorScreenState2.HOTBAR
               || hudConstructorScreenState24 == HudConstructorScreen.HudConstructorScreenState2.SLOTS
               || hudConstructorScreenState24 == HudConstructorScreen.HudConstructorScreenState2.TARGET;
            case "rowRadius" -> hudConstructorScreenState24 == HudConstructorScreen.HudConstructorScreenState2.ARRAYLIST
               || hudConstructorScreenState24 == HudConstructorScreen.HudConstructorScreenState2.TARGET
               || hudConstructorScreenState24 == HudConstructorScreen.HudConstructorScreenState2.NOTIFICATION;
            case "contentRadius" -> hudConstructorScreenState24 != HudConstructorScreen.HudConstructorScreenState2.KEYBINDS && hudConstructorScreenState24 != HudConstructorScreen.HudConstructorScreenState2.WATERMARK;
            case "modulesRadius" -> hudConstructorScreenState24 != HudConstructorScreen.HudConstructorScreenState2.HOTBAR && hudConstructorScreenState24 != HudConstructorScreen.HudConstructorScreenState2.SLOTS;
            case "bindsRadius" -> hudConstructorScreenState24 == HudConstructorScreen.HudConstructorScreenState2.KEYBINDS
               || hudConstructorScreenState24 == HudConstructorScreen.HudConstructorScreenState2.POTIONS
               || hudConstructorScreenState24 == HudConstructorScreen.HudConstructorScreenState2.COOLDOWNS
               || hudConstructorScreenState24 == HudConstructorScreen.HudConstructorScreenState2.TARGET
               || hudConstructorScreenState24 == HudConstructorScreen.HudConstructorScreenState2.WATERMARK;
            case "bindWidth" -> hudConstructorScreenState24 == HudConstructorScreen.HudConstructorScreenState2.KEYBINDS
               || hudConstructorScreenState24 == HudConstructorScreen.HudConstructorScreenState2.POTIONS
               || hudConstructorScreenState24 == HudConstructorScreen.HudConstructorScreenState2.COOLDOWNS
               || hudConstructorScreenState24 == HudConstructorScreen.HudConstructorScreenState2.TARGET;
            case "accentWidth" -> hudConstructorScreenState24 == HudConstructorScreen.HudConstructorScreenState2.KEYBINDS
               || hudConstructorScreenState24 == HudConstructorScreen.HudConstructorScreenState2.POTIONS
               || hudConstructorScreenState24 == HudConstructorScreen.HudConstructorScreenState2.COOLDOWNS
               || hudConstructorScreenState24 == HudConstructorScreen.HudConstructorScreenState2.INFO;
            case "headerRadius", "headerHeight" -> hudConstructorScreenState24 != HudConstructorScreen.HudConstructorScreenState2.HOTBAR
               && hudConstructorScreenState24 != HudConstructorScreen.HudConstructorScreenState2.SLOTS
               && hudConstructorScreenState24 != HudConstructorScreen.HudConstructorScreenState2.WATERMARK
               && hudConstructorScreenState24 != HudConstructorScreen.HudConstructorScreenState2.ARRAYLIST;
            case "rowHeight" -> hudConstructorScreenState24 != HudConstructorScreen.HudConstructorScreenState2.HOTBAR
               && hudConstructorScreenState24 != HudConstructorScreen.HudConstructorScreenState2.SLOTS
               && hudConstructorScreenState24 != HudConstructorScreen.HudConstructorScreenState2.INVENTORY;
            case "titleSize", "iconSize" -> hudConstructorScreenState24 != HudConstructorScreen.HudConstructorScreenState2.ARRAYLIST
               && hudConstructorScreenState24 != HudConstructorScreen.HudConstructorScreenState2.HOTBAR
               && hudConstructorScreenState24 != HudConstructorScreen.HudConstructorScreenState2.SLOTS;
            default -> true;
         };
      } else {
         return false;
      }
   }

   private void invoke10(RenderManager renderManager8, ColorScheme colorScheme8) {
      float floatValue57 = this.hudConstructorScreenState12.floatValue;
      float floatValue58 = this.hudConstructorScreenState12.floatValue2;
      float floatValue59 = this.hudConstructorScreenState12.floatValue3;
      float floatValue60 = this.hudConstructorScreenState12.floatValue4;
      this.invoke6(renderManager8, colorScheme8, floatValue57, floatValue58, floatValue59, floatValue60);
      float floatValue61 = Math.round(this.measure(42.0F));
      renderManager8.invoke69(
         FontRegistry.fontObject4,
         floatValue57 + this.measure(16.0F),
         measure20(floatValue58 + this.measure(21.0F), this.measure(16.0F)),
         this.measure(16.0F),
         "Elements",
         compute7(colorScheme8)
      );
      String text9 = Integer.toString(HUD_CONSTRUCTOR_SCREEN_DISPLAY_ENTRYS.length);
      renderManager8.invoke69(
         FontRegistry.fontObject,
         floatValue57 + floatValue59 - this.measure(16.0F) - TextMeasureCache.measure(FontRegistry.fontObject, text9, this.measure(13.0F)),
         measure20(floatValue58 + this.measure(21.0F), this.measure(13.0F)),
         this.measure(13.0F),
         text9,
         compute8(colorScheme8)
      );
      this.invoke7(renderManager8, colorScheme8, floatValue57 + this.measure(14.0F), floatValue58 + floatValue61, floatValue59 - this.measure(28.0F));
      float floatValue62 = Math.round(floatValue58 + floatValue61 + this.measure(8.0F));
      float floatValue63 = Math.round(Math.max(this.measure(40.0F), floatValue58 + floatValue60 - floatValue62 - this.measure(8.0F)));
      this.hudConstructorScreenState13.resolve(Math.round(floatValue57 + this.measure(4.0F)), floatValue62, Math.round(floatValue59 - this.measure(8.0F)), floatValue63);
      renderManager8.invoke20();
      renderManager8.invoke24(
         this.hudConstructorScreenState13.floatValue,
         this.hudConstructorScreenState13.floatValue2,
         this.hudConstructorScreenState13.floatValue3,
         this.hudConstructorScreenState13.floatValue4,
         this.measure(8.0F),
         this.measure(8.0F),
         this.measure(8.0F),
         this.measure(8.0F)
      );

      try {
         float floatValue64 = Math.round(this.measure(38.0F));
         float floatValue65 = Math.round(this.measure(4.0F));
         float floatValue66 = Math.round(floatValue57 + this.measure(10.0F));
         float floatValue67 = Math.round(floatValue59 - this.measure(20.0F));
         float floatValue68 = Math.round(this.measure(26.0F));
         float floatValue69 = floatValue62 - this.floatValue22;

         for (int intValue9 = 0; intValue9 < HUD_CONSTRUCTOR_SCREEN_DISPLAY_ENTRYS.length; intValue9++) {
            HudConstructorScreen.HudConstructorScreenDisplayEntry hudConstructorScreenDisplayEntry3 = HUD_CONSTRUCTOR_SCREEN_DISPLAY_ENTRYS[intValue9];
            HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState4 = this.hudConstructorScreenStates[intValue9];
            float floatValue70 = Math.round(floatValue69);
            hudConstructorScreenState4.resolve(floatValue66, floatValue70, floatValue67, floatValue64);
            boolean flag3 = intValue9 == this.intValue;
            boolean flag4 = this.check4(hudConstructorScreenDisplayEntry3);
            boolean flag5 = floatValue70 + floatValue64 >= floatValue62 && floatValue70 <= floatValue62 + floatValue63;
            if (flag5) {
               float floatValue71 = this.measure14(hudConstructorScreenDisplayEntry3.id, !hudConstructorScreenState4.check(this.floatValue, this.floatValue2) && !flag3 ? 0.0F : 1.0F);
               if (flag3) {
                  renderManager8.invoke5(floatValue66, floatValue70, floatValue67, floatValue64, this.measure(10.0F), compute5(colorScheme8, colorScheme8.isFlag() ? 30 : 24));
                  renderManager8.invoke28(
                     floatValue66, floatValue70, floatValue67, floatValue64, this.measure(10.0F), compute5(colorScheme8, colorScheme8.isFlag() ? 66 : 50), 1.0F
                  );
                  float floatValue72 = floatValue64 - this.measure(16.0F);
                  float floatValue73 = floatValue70 + (floatValue64 - floatValue72) * 0.5F;
                  renderManager8.invoke5(
                     (float)Math.round(floatValue66 + this.measure(4.0F)),
                     (float)Math.round(floatValue73),
                     (float)Math.round(this.measure(3.0F)),
                     (float)Math.round(floatValue72),
                     this.measure(1.5F),
                     colorScheme8.getIntValue14()
                  );
               } else if (floatValue71 > 0.01F) {
                  renderManager8.invoke5(
                     floatValue66,
                     floatValue70,
                     floatValue67,
                     floatValue64,
                     this.measure(10.0F),
                     ColorScheme.compute6(colorScheme8.getIntValue13(), Math.round(floatValue71 * (colorScheme8.isFlag() ? 13 : 10)))
                  );
               }

               boolean flag6 = !flag4;
               if (flag6) {
                  renderManager8.invoke65(colorScheme8.isFlag() ? 0.5F : 0.4F);
               }

               int intValue10 = flag3 ? colorScheme8.compute4() : ColorScheme.compute7(compute8(colorScheme8), colorScheme8.getIntValue13(), floatValue71 * 0.5F);
               int intValue11 = flag3 ? colorScheme8.getIntValue13() : ColorScheme.compute7(compute7(colorScheme8), colorScheme8.getIntValue13(), floatValue71 * 0.4F);
               float floatValue74 = Math.round(floatValue66 + this.measure(11.0F));
               float floatValue75 = Math.round(floatValue70 + (floatValue64 - floatValue68) * 0.5F);
               renderManager8.invoke5(
                  floatValue74,
                  floatValue75,
                  floatValue68,
                  floatValue68,
                  this.measure(8.0F),
                  flag3 ? compute5(colorScheme8, 40) : ColorScheme.compute6(colorScheme8.getIntValue13(), colorScheme8.isFlag() ? 14 : 12)
               );
               renderManager8.invoke20();
               renderManager8.invoke24(floatValue74, floatValue75, floatValue68, floatValue68, this.measure(8.0F), this.measure(8.0F), this.measure(8.0F), this.measure(8.0F));

               try {
                  this.invoke51(renderManager8, hudConstructorScreenDisplayEntry3.iconFont, hudConstructorScreenDisplayEntry3.icon, floatValue74, floatValue75, floatValue68, this.measure(18.0F), intValue10);
               } finally {
                  renderManager8.invoke20();
                  renderManager8.invoke25();
               }

               float floatValue76 = this.measure(16.0F);
               float floatValue77 = Math.round(floatValue74 + floatValue68 + this.measure(12.0F));
               float floatValue78 = floatValue66 + floatValue67 - this.measure(14.0F) - floatValue77;
               String text10 = TextMeasureCache.measure(FontRegistry.fontObject, hudConstructorScreenDisplayEntry3.label, floatValue76) <= floatValue78 ? hudConstructorScreenDisplayEntry3.label : KEYS[intValue9];
               renderManager8.invoke69(FontRegistry.fontObject, floatValue77, Math.round(measure20(floatValue70 + floatValue64 * 0.5F, floatValue76)), floatValue76, text10, intValue11);
               if (flag6) {
                  renderManager8.invoke66();
               }
            }

            floatValue69 += floatValue64 + floatValue65;
         }

         float floatValue79 = HUD_CONSTRUCTOR_SCREEN_DISPLAY_ENTRYS.length * floatValue64 + Math.max(0, HUD_CONSTRUCTOR_SCREEN_DISPLAY_ENTRYS.length - 1) * floatValue65;
         this.floatValue23 = Math.max(0.0F, floatValue79 - floatValue63);
         this.floatValue22 = measure18(this.floatValue22, 0.0F, this.floatValue23);
      } finally {
         renderManager8.invoke20();
         renderManager8.invoke25();
      }

      this.animation7.check();
      this.animation7
         .resolve4(this.hudConstructorScreenState12.check(this.floatValue, this.floatValue2) ? 1.0 : 0.0, 0.22F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
      this.invoke8(
         renderManager8,
         colorScheme8,
         floatValue57 + floatValue59 - this.measure(7.0F),
         floatValue62,
         floatValue63,
         this.floatValue22,
         this.floatValue23,
         this.animation7.measure3()
      );
   }

   private void invoke11(RenderManager renderManager9, ColorScheme colorScheme9) {
      float floatValue80 = this.hudConstructorScreenState14.floatValue;
      float floatValue81 = this.hudConstructorScreenState14.floatValue2;
      float floatValue82 = this.hudConstructorScreenState14.floatValue3;
      float floatValue83 = this.hudConstructorScreenState14.floatValue4;
      this.invoke6(renderManager9, colorScheme9, floatValue80, floatValue81, floatValue82, floatValue83);
      float floatValue84 = Math.round(this.measure(42.0F));
      HudConstructorScreen.HudConstructorScreenDisplayEntry hudConstructorScreenDisplayEntry4 = this.resolve15();
      renderManager9.invoke69(
         FontRegistry.fontObject4,
         floatValue80 + this.measure(16.0F),
         measure20(floatValue81 + this.measure(21.0F), this.measure(16.0F)),
         this.measure(16.0F),
         hudConstructorScreenDisplayEntry4.label,
         colorScheme9.getIntValue13()
      );
      this.invoke9(renderManager9, colorScheme9, this.check4(hudConstructorScreenDisplayEntry4), hudConstructorScreenDisplayEntry4.layoutBacked, floatValue80 + floatValue82 - this.measure(16.0F), floatValue81 + this.measure(21.0F));
      this.invoke7(renderManager9, colorScheme9, floatValue80 + this.measure(14.0F), floatValue81 + floatValue84, floatValue82 - this.measure(28.0F));
      float floatValue85 = this.measure(14.0F);
      float floatValue86 = floatValue80 + floatValue85;
      float floatValue87 = floatValue81 + floatValue84 + this.measure(8.0F);
      float floatValue88 = Math.max(this.measure(60.0F), floatValue82 - floatValue85 * 2.0F);
      float floatValue89 = Math.max(this.measure(60.0F), floatValue81 + floatValue83 - floatValue87 - floatValue85);
      this.hudConstructorScreenState11.resolve(Math.round(floatValue86), Math.round(floatValue87), Math.round(floatValue88), Math.round(floatValue89));
      renderManager9.invoke41(
         this.hudConstructorScreenState11.floatValue,
         this.hudConstructorScreenState11.floatValue2,
         this.hudConstructorScreenState11.floatValue3,
         this.hudConstructorScreenState11.floatValue4,
         this.measure(10.0F),
         this.measure(10.0F),
         this.measure(1.0F),
         compute4(0, 0, 0, colorScheme9.isFlag() ? 30 : 105)
      );
      renderManager9.invoke5(
         this.hudConstructorScreenState11.floatValue,
         this.hudConstructorScreenState11.floatValue2,
         this.hudConstructorScreenState11.floatValue3,
         this.hudConstructorScreenState11.floatValue4,
         this.measure(10.0F),
         colorScheme9.isFlag() ? ColorScheme.compute6(colorScheme9.getIntValue2(), 170) : compute4(2, 6, 12, 126)
      );
      renderManager9.invoke28(
         this.hudConstructorScreenState11.floatValue,
         this.hudConstructorScreenState11.floatValue2,
         this.hudConstructorScreenState11.floatValue3,
         this.hudConstructorScreenState11.floatValue4,
         this.measure(10.0F),
         colorScheme9.getIntValue8(),
         Math.max(1.0F, this.measure(1.0F))
      );
      renderManager9.invoke20();
      renderManager9.invoke24(
         this.hudConstructorScreenState11.floatValue,
         this.hudConstructorScreenState11.floatValue2,
         this.hudConstructorScreenState11.floatValue3,
         this.hudConstructorScreenState11.floatValue4,
         this.measure(10.0F),
         this.measure(10.0F),
         this.measure(10.0F),
         this.measure(10.0F)
      );

      try {
         this.invoke14(
            renderManager9,
            this.hudConstructorScreenState11.floatValue,
            this.hudConstructorScreenState11.floatValue2,
            this.hudConstructorScreenState11.floatValue3,
            this.hudConstructorScreenState11.floatValue4,
            colorScheme9
         );
         this.invoke13();
         this.invoke12(
            renderManager9, this.hudConstructorScreenState11.floatValue, this.hudConstructorScreenState11.floatValue2, this.hudConstructorScreenState11.floatValue3, this.hudConstructorScreenState11.floatValue4
         );
         this.invoke27(renderManager9, colorScheme9);
         this.invoke28(renderManager9, colorScheme9);
         renderManager9.invoke69(
            FontRegistry.fontObject,
            this.hudConstructorScreenState11.floatValue + this.measure(14.0F),
            this.hudConstructorScreenState11.floatValue2 + this.hudConstructorScreenState11.floatValue4 - this.measure(14.0F),
            this.measure(12.0F),
            "drag surface  ·  resize corner",
            compute8(colorScheme9)
         );
      } finally {
         renderManager9.invoke20();
         renderManager9.invoke25();
      }
   }

   private void invoke12(RenderManager renderManager10, float f, float g, float h, float i) {
      switch (this.resolve15().kind) {
         case INVENTORY:
            this.invoke16(renderManager10, f, g, h, i);
            break;
         case POTIONS:
            this.invoke15(
               renderManager10, f, g, h, i, "Potions", this.resolve15().iconFont, this.resolve15().icon, STRENGTH_III, TEXT158, 24.0F, true
            );
            break;
         case COOLDOWNS:
            this.invoke15(
               renderManager10, f, g, h, i, "Cooldowns", this.resolve15().iconFont, this.resolve15().icon, ENDER_PEARL, TEXT84S, 24.0F, true
            );
            break;
         case INFO:
            this.invoke15(
               renderManager10,
               f,
               g,
               h,
               i,
               this.resolve15().label,
               this.resolve15().iconFont,
               this.resolve15().icon,
               BPS,
               TEXT742,
               24.0F,
               true
            );
            break;
         case WATERMARK:
            this.invoke18(renderManager10, f, g, h, i);
            break;
         case ARRAYLIST:
            this.invoke19(renderManager10, f, g, h, i);
            break;
         case TARGET:
            this.invoke20(renderManager10, f, g, h, i);
            break;
         case SLOTS:
         case HOTBAR:
            this.invoke17(renderManager10, f, g, h, i);
            break;
         case NOTIFICATION:
            this.invoke15(
               renderManager10, f, g, h, i, "Notifications", this.resolve15().iconFont, this.resolve15().icon, MODULE_TOGGLED, NOW, 22.0F, true
            );
            break;
         case MEDIA:
            this.invoke15(
               renderManager10, f, g, h, i, "Now Playing", this.resolve15().iconFont, this.resolve15().icon, MIDNIGHT_DRIVE, PLAYING, 22.0F, true
            );
            break;
         case SERVER:
            this.invoke15(
               renderManager10, f, g, h, i, "Server Helper", this.resolve15().iconFont, this.resolve15().icon, FUNTIME, EU, 22.0F, true
            );
            break;
         default:
            this.invoke15(renderManager10, f, g, h, i, "Binds", FontRegistry.fontObject8, "q", HITAURA, R, 22.0F, true);
      }
   }

   private void invoke13() {
      this.hudConstructorScreenState2.resolve3();
      this.hudConstructorScreenState3.resolve3();
      this.hudConstructorScreenState4.resolve3();
      this.hudConstructorScreenState5.resolve3();
      this.hudConstructorScreenState6.resolve3();
      this.hudConstructorScreenState7.resolve3();
      this.DynamicButtonSetting.resolve3();
      this.hudConstructorScreenState8.resolve3();
      this.SpacerSetting.resolve3();
   }

   private void invoke14(RenderManager renderManager11, float f, float g, float h, float i, ColorScheme colorScheme10) {
      int intValue12 = compute5(colorScheme10, colorScheme10.isFlag() ? 18 : 14);
      float floatValue90 = Math.max(this.measure(20.0F), (float)Math.round(this.measure(26.0F)));
      float floatValue91 = Math.max(1.0F, (float)Math.round(this.measure(1.5F)));

      for (float floatValue92 = Math.round(f + floatValue90 * 0.5F); floatValue92 < f + h; floatValue92 += floatValue90) {
         for (float floatValue93 = Math.round(g + floatValue90 * 0.5F); floatValue93 < g + i; floatValue93 += floatValue90) {
            renderManager11.invoke5((float)Math.round(floatValue92), (float)Math.round(floatValue93), floatValue91, floatValue91, floatValue91 * 0.5F, intValue12);
         }
      }

      int intValue13 = compute5(colorScheme10, colorScheme10.isFlag() ? 30 : 22);
      renderManager11.invoke5((float)Math.round(f + h * 0.5F), (float)Math.round(g), 1.0F, (float)Math.round(i), 0.0F, intValue13);
      renderManager11.invoke5((float)Math.round(f), (float)Math.round(g + i * 0.5F), (float)Math.round(h), 1.0F, 0.0F, intValue13);
   }

   private void invoke15(
      RenderManager renderManager12,
      float f,
      float g,
      float h,
      float i,
      String string,
      FontObject fontObject,
      String string2,
      String[] strings,
      String[] strings2,
      float j,
      boolean bl
   ) {
      HudLayoutManager.HudLayoutManagerState hudLayoutManagerState2 = this.resolve13();
      hudLayoutManagerState2.invoke();
      HudElement hudElement = this.resolve14();
      boolean flag7 = this.resolve15().kind != HudConstructorScreen.HudConstructorScreenState2.ARRAYLIST;
      float floatValue94 = this.measure3(string, fontObject, string2, strings, strings2, j, bl, hudLayoutManagerState2);
      float floatValue95 = hudLayoutManagerState2.floatValue8
         + (flag7 ? Math.max(0.0F, hudLayoutManagerState2.floatValue10) + hudLayoutManagerState2.floatValue9 : 0.0F)
         + strings.length * hudLayoutManagerState2.floatValue11
         + hudLayoutManagerState2.floatValue8;
      HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState5 = this.resolve5(f, g, h, i, floatValue94, floatValue95);
      float floatValue96 = hudConstructorScreenState5.floatValue;
      float floatValue97 = hudConstructorScreenState5.floatValue2;
      float floatValue98 = hudConstructorScreenState5.floatValue3;
      float floatValue99 = hudConstructorScreenState5.floatValue4;
      float floatValue100 = this.floatValue5;
      float floatValue101 = this.floatValue6;
      float floatValue102 = Math.min(floatValue100, floatValue101);
      float floatValue103 = hudLayoutManagerState2.floatValue8 * floatValue100;
      float floatValue104 = hudLayoutManagerState2.floatValue8 * floatValue101;
      float floatValue105 = flag7 ? hudLayoutManagerState2.floatValue10 * floatValue101 : 0.0F;
      float floatValue106 = hudLayoutManagerState2.floatValue9 * floatValue100;
      float floatValue107 = flag7 ? hudLayoutManagerState2.floatValue9 * floatValue101 : 0.0F;
      float floatValue108 = hudLayoutManagerState2.floatValue11 * floatValue101;
      float floatValue109 = floatValue97 + floatValue104 + floatValue105 + floatValue107;
      float floatValue110 = strings.length * floatValue108;
      float floatValue111 = 0.0F;

      for (String text11 : strings2) {
         floatValue111 = Math.max(floatValue111, TextMeasureCache.measure(FontRegistry.fontObject, text11, j));
      }

      float floatValue112 = bl ? Math.max(26.0F, floatValue111 + 20.0F + hudLayoutManagerState2.floatValue14) * floatValue100 : 0.0F;
      float floatValue113 = floatValue98 - floatValue103 * 2.0F;
      float floatValue114 = bl ? Math.max(30.0F, floatValue113 - floatValue106 - floatValue112) : floatValue113;
      float floatValue115 = floatValue96 + floatValue103 + this.measure13(hudLayoutManagerState2.hudLayoutManagerState33.floatValue, "modules.x") * floatValue100;
      float floatValue116 = floatValue109 + this.measure13(hudLayoutManagerState2.hudLayoutManagerState33.floatValue2, "modules.y") * floatValue101;
      float floatValue117 = floatValue96 + floatValue103 + floatValue114 + floatValue106 + this.measure13(hudLayoutManagerState2.hudLayoutManagerState34.floatValue, "binds.x") * floatValue100;
      float floatValue118 = floatValue109 + this.measure13(hudLayoutManagerState2.hudLayoutManagerState34.floatValue2, "binds.y") * floatValue101;
      this.invoke22(renderManager12, hudElement, floatValue96, floatValue97, floatValue98, floatValue99, hudLayoutManagerState2.floatValue * floatValue102, 0.95F);
      this.hudConstructorScreenState4.resolve(floatValue96, floatValue97, floatValue98, floatValue99);
      if (flag7) {
         this.hudConstructorScreenState5.resolve(floatValue96 + floatValue103, floatValue97 + floatValue104, floatValue113, floatValue105);
         this.invoke23(
            renderManager12,
            hudElement,
            this.hudConstructorScreenState5.floatValue,
            this.hudConstructorScreenState5.floatValue2,
            this.hudConstructorScreenState5.floatValue3,
            this.hudConstructorScreenState5.floatValue4,
            hudLayoutManagerState2.floatValue2 * floatValue102,
            false,
            0.95F
         );
      } else {
         this.hudConstructorScreenState5.resolve3();
      }

      this.hudConstructorScreenState7.resolve(floatValue115, floatValue116, floatValue114, floatValue110);
      this.invoke23(
         renderManager12,
         hudElement,
         this.hudConstructorScreenState7.floatValue,
         this.hudConstructorScreenState7.floatValue2,
         this.hudConstructorScreenState7.floatValue3,
         this.hudConstructorScreenState7.floatValue4,
         hudLayoutManagerState2.floatValue4 * floatValue102,
         true,
         0.95F
      );
      if (bl) {
         this.DynamicButtonSetting.resolve(floatValue117, floatValue118, floatValue112, floatValue110);
         this.invoke23(
            renderManager12,
            hudElement,
            this.DynamicButtonSetting.floatValue,
            this.DynamicButtonSetting.floatValue2,
            this.DynamicButtonSetting.floatValue3,
            this.DynamicButtonSetting.floatValue4,
            hudLayoutManagerState2.floatValue5 * floatValue102,
            true,
            0.95F
         );
         invoke50(this.hudConstructorScreenState6, this.hudConstructorScreenState7, this.DynamicButtonSetting);
      } else {
         this.DynamicButtonSetting.resolve3();
         this.hudConstructorScreenState6.resolve2(this.hudConstructorScreenState7);
      }

      if (flag7) {
         this.invoke21(renderManager12, hudElement, hudLayoutManagerState2, floatValue96, floatValue97, floatValue98, floatValue100, floatValue101, string, FontRegistry.fontObject4, fontObject, string2);
      } else {
         this.hudConstructorScreenState2.resolve3();
         this.hudConstructorScreenState3.resolve3();
      }

      for (int intValue14 = 0; intValue14 < strings.length; intValue14++) {
         float floatValue119 = floatValue116 + intValue14 * floatValue108;
         float floatValue120 = floatValue118 + intValue14 * floatValue108;
         boolean flag8 = this.resolve15().kind == HudConstructorScreen.HudConstructorScreenState2.POTIONS && intValue14 == 2;
         int intValue15 = flag8 ? this.resolve().compute2() : hudElement.compute9(0.9F);
         int intValue16 = flag8 ? ColorScheme.compute6(this.resolve().compute2(), 235) : hudElement.compute6(0.9F);
         if (hudLayoutManagerState2.floatValue15 > 0.05F) {
            renderManager12.invoke5(
               (float)Math.round(floatValue115 + 10.0F * floatValue100),
               (float)Math.round(floatValue119 + (floatValue108 - 8.0F * floatValue101) * 0.5F),
               Math.max(1.0F, (float)Math.round(hudLayoutManagerState2.floatValue15 * floatValue100)),
               Math.max(1.0F, (float)Math.round(8.0F * floatValue101)),
               Math.max(0.8F, hudLayoutManagerState2.floatValue15 * 0.5F) * floatValue100,
               intValue15
            );
         }

         renderManager12.invoke69(FontRegistry.fontObject, floatValue115 + 20.0F * floatValue100, floatValue119 + floatValue108 * 0.5F + 4.0F * floatValue101, j * floatValue102, strings[intValue14], intValue16);
         if (bl) {
            float floatValue121 = TextMeasureCache.measure(FontRegistry.fontObject, strings2[intValue14], j * floatValue102);
            renderManager12.invoke69(
               FontRegistry.fontObject, floatValue117 + (floatValue112 - floatValue121) * 0.5F, floatValue120 + floatValue108 * 0.5F + 4.0F * floatValue101, j * floatValue102, strings2[intValue14], intValue15
            );
         }
      }
   }

   private void invoke16(RenderManager renderManager13, float f, float g, float h, float i) {
      HudLayoutManager.HudLayoutManagerState hudLayoutManagerState3 = this.resolve13();
      hudLayoutManagerState3.invoke();
      HudElement hudElement2 = this.resolve14();
      float floatValue122 = 22.0F;
      float floatValue123 = 9.0F * floatValue122 + hudLayoutManagerState3.floatValue8 * 2.0F;
      float floatValue124 = 3.0F * floatValue122 + hudLayoutManagerState3.floatValue8 * 2.0F;
      float floatValue125 = TextMeasureCache.measure(FontRegistry.fontObject4, "Inventory", hudLayoutManagerState3.floatValue12);
      float floatValue126 = Math.max(floatValue123 + hudLayoutManagerState3.floatValue8 * 2.0F, floatValue125 + 22.0F + hudLayoutManagerState3.floatValue8 * 2.0F + hudLayoutManagerState3.floatValue13 + 14.0F);
      float floatValue127 = hudLayoutManagerState3.floatValue8 + hudLayoutManagerState3.floatValue10 + hudLayoutManagerState3.floatValue9 + floatValue124 + hudLayoutManagerState3.floatValue8;
      HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState6 = this.resolve5(f, g, h, i, floatValue126, floatValue127);
      float floatValue128 = hudConstructorScreenState6.floatValue;
      float floatValue129 = hudConstructorScreenState6.floatValue2;
      float floatValue130 = hudConstructorScreenState6.floatValue3;
      float floatValue131 = hudConstructorScreenState6.floatValue4;
      float floatValue132 = this.floatValue5;
      float floatValue133 = this.floatValue6;
      float floatValue134 = Math.min(floatValue132, floatValue133);
      float floatValue135 = hudLayoutManagerState3.floatValue8 * floatValue132;
      float floatValue136 = hudLayoutManagerState3.floatValue8 * floatValue133;
      float floatValue137 = hudLayoutManagerState3.floatValue10 * floatValue133;
      float floatValue138 = floatValue129 + floatValue136 + floatValue137 + hudLayoutManagerState3.floatValue9 * floatValue133;
      float floatValue139 = floatValue130 - floatValue135 * 2.0F;
      float floatValue140 = floatValue128 + floatValue135 + this.measure13(hudLayoutManagerState3.hudLayoutManagerState33.floatValue, "modules.x") * floatValue132;
      float floatValue141 = floatValue138 + this.measure13(hudLayoutManagerState3.hudLayoutManagerState33.floatValue2, "modules.y") * floatValue133;
      float floatValue142 = floatValue124 * floatValue133;
      this.invoke22(renderManager13, hudElement2, floatValue128, floatValue129, floatValue130, floatValue131, hudLayoutManagerState3.floatValue * floatValue134, 0.95F);
      this.hudConstructorScreenState4.resolve(floatValue128, floatValue129, floatValue130, floatValue131);
      this.hudConstructorScreenState5.resolve(floatValue128 + floatValue135, floatValue129 + floatValue136, floatValue139, floatValue137);
      this.invoke23(
         renderManager13,
         hudElement2,
         this.hudConstructorScreenState5.floatValue,
         this.hudConstructorScreenState5.floatValue2,
         this.hudConstructorScreenState5.floatValue3,
         this.hudConstructorScreenState5.floatValue4,
         hudLayoutManagerState3.floatValue2 * floatValue134,
         false,
         0.95F
      );
      this.hudConstructorScreenState7.resolve(floatValue140, floatValue141, floatValue139, floatValue142);
      this.hudConstructorScreenState6.resolve2(this.hudConstructorScreenState7);
      this.invoke23(
         renderManager13,
         hudElement2,
         this.hudConstructorScreenState7.floatValue,
         this.hudConstructorScreenState7.floatValue2,
         this.hudConstructorScreenState7.floatValue3,
         this.hudConstructorScreenState7.floatValue4,
         hudLayoutManagerState3.floatValue3 * floatValue134,
         true,
         0.95F
      );
      this.invoke21(renderManager13, hudElement2, hudLayoutManagerState3, floatValue128, floatValue129, floatValue130, floatValue132, floatValue133, "Inventory", FontRegistry.fontObject4, FontRegistry.fontObject5, "h");
      float floatValue143 = floatValue122 * floatValue134;
      float floatValue144 = floatValue140 + (floatValue139 - 9.0F * floatValue143) * 0.5F;
      float floatValue145 = floatValue141 + (floatValue142 - 3.0F * floatValue143) * 0.5F;
      int intValue17 = hudElement2.check7() ? compute4(0, 0, 0, 58) : hudElement2.compute3(0.72F);

      for (int intValue18 = 0; intValue18 < 3; intValue18++) {
         for (int intValue19 = 0; intValue19 < 9; intValue19++) {
            float floatValue146 = floatValue144 + intValue19 * floatValue143;
            float floatValue147 = floatValue145 + intValue18 * floatValue143;
            renderManager13.invoke5(
               (float)Math.round(floatValue146 + 1.0F),
               (float)Math.round(floatValue147 + 1.0F),
               Math.max(1.0F, (float)Math.round(floatValue143 - 2.0F)),
               Math.max(1.0F, (float)Math.round(floatValue143 - 2.0F)),
               hudLayoutManagerState3.floatValue7 * floatValue134,
               intValue17
            );
         }
      }

      this.hudConstructorScreenState8.resolve(Math.round(floatValue144), Math.round(floatValue145), Math.round(9.0F * floatValue143), Math.round(3.0F * floatValue143));
      if (this.client != null && this.client.player != null) {
         renderManager13.invoke20();
         renderManager13.invoke24(
            this.hudConstructorScreenState8.floatValue,
            this.hudConstructorScreenState8.floatValue2,
            this.hudConstructorScreenState8.floatValue3,
            this.hudConstructorScreenState8.floatValue4,
            hudLayoutManagerState3.floatValue3 * floatValue134,
            hudLayoutManagerState3.floatValue3 * floatValue134,
            hudLayoutManagerState3.floatValue3 * floatValue134,
            hudLayoutManagerState3.floatValue3 * floatValue134
         );

         try {
            int intValue20 = 9;
            float floatValue148 = ItemRenderUtil.measure3(Math.max(0.25F, (floatValue143 - this.measure(4.0F)) / 16.0F));
            float floatValue149 = 16.0F * floatValue148;

            for (int intValue21 = 0; intValue21 < 3; intValue21++) {
               for (int intValue22 = 0; intValue22 < 9; intValue22++) {
                  ItemStack itemStack = this.client.player.getInventory().getStack(intValue20);
                  if (itemStack != null && !itemStack.isEmpty()) {
                     float floatValue150 = floatValue144 + intValue22 * floatValue143 + (floatValue143 - floatValue149) * 0.5F;
                     float floatValue151 = floatValue145 + intValue21 * floatValue143 + (floatValue143 - floatValue149) * 0.5F;
                     ItemRenderUtil.invoke3(renderManager13, itemStack, ItemRenderUtil.measure(floatValue150), ItemRenderUtil.measure(floatValue151), floatValue148, intValue20, true, intValue20);
                  }

                  intValue20++;
               }
            }
         } finally {
            renderManager13.invoke20();
            renderManager13.invoke25();
         }
      }
   }

   private void invoke17(RenderManager renderManager14, float f, float g, float h, float i) {
      HudLayoutManager.HudLayoutManagerState hudLayoutManagerState4 = this.resolve13();
      hudLayoutManagerState4.invoke();
      HudElement hudElement3 = this.resolve14();
      int intValue23 = this.resolve15().kind == HudConstructorScreen.HudConstructorScreenState2.HOTBAR ? 9 : 4;
      float floatValue152 = this.resolve15().kind == HudConstructorScreen.HudConstructorScreenState2.HOTBAR ? 24.0F : 28.0F;
      float floatValue153 = intValue23 * floatValue152 + hudLayoutManagerState4.floatValue8 * 2.0F;
      float floatValue154 = floatValue152 + hudLayoutManagerState4.floatValue8 * 2.0F;
      HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState7 = this.resolve5(f, g, h, i, floatValue153, floatValue154);
      float floatValue155 = hudConstructorScreenState7.floatValue;
      float floatValue156 = hudConstructorScreenState7.floatValue2;
      float floatValue157 = hudConstructorScreenState7.floatValue3;
      float floatValue158 = hudConstructorScreenState7.floatValue4;
      float floatValue159 = Math.min(this.floatValue5, this.floatValue6);
      this.invoke22(renderManager14, hudElement3, floatValue155, floatValue156, floatValue157, floatValue158, hudLayoutManagerState4.floatValue * floatValue159, 0.95F);
      this.hudConstructorScreenState4.resolve(floatValue155, floatValue156, floatValue157, floatValue158);
      this.hudConstructorScreenState7
         .resolve(
            floatValue155 + hudLayoutManagerState4.floatValue8 * this.floatValue5,
            floatValue156 + hudLayoutManagerState4.floatValue8 * this.floatValue6,
            floatValue157 - hudLayoutManagerState4.floatValue8 * 2.0F * this.floatValue5,
            floatValue158 - hudLayoutManagerState4.floatValue8 * 2.0F * this.floatValue6
         );
      this.hudConstructorScreenState6.resolve2(this.hudConstructorScreenState7);
      float floatValue160 = floatValue152 * floatValue159;
      float floatValue161 = floatValue155 + (floatValue157 - intValue23 * floatValue160) * 0.5F;
      float floatValue162 = floatValue156 + (floatValue158 - floatValue160) * 0.5F;

      for (int intValue24 = 0; intValue24 < intValue23; intValue24++) {
         float floatValue163 = floatValue161 + intValue24 * floatValue160;
         int intValue25 = intValue24 == 0 ? hudElement3.compute9(0.28F) : hudElement3.compute3(0.76F);
         renderManager14.invoke5(
            (float)Math.round(floatValue163 + 1.0F),
            (float)Math.round(floatValue162 + 1.0F),
            Math.max(1.0F, (float)Math.round(floatValue160 - 2.0F)),
            Math.max(1.0F, (float)Math.round(floatValue160 - 2.0F)),
            hudLayoutManagerState4.floatValue7 * floatValue159,
            intValue25
         );
      }

      this.hudConstructorScreenState8.resolve(Math.round(floatValue161), Math.round(floatValue162), Math.round(intValue23 * floatValue160), Math.round(floatValue160));
      if (this.client != null && this.client.player != null) {
         renderManager14.invoke20();
         renderManager14.invoke24(
            this.hudConstructorScreenState8.floatValue,
            this.hudConstructorScreenState8.floatValue2,
            this.hudConstructorScreenState8.floatValue3,
            this.hudConstructorScreenState8.floatValue4,
            hudLayoutManagerState4.floatValue * floatValue159,
            hudLayoutManagerState4.floatValue * floatValue159,
            hudLayoutManagerState4.floatValue * floatValue159,
            hudLayoutManagerState4.floatValue * floatValue159
         );

         try {
            float floatValue164 = ItemRenderUtil.measure3(Math.max(0.25F, (floatValue160 - this.measure(5.0F)) / 16.0F));
            float floatValue165 = 16.0F * floatValue164;

            for (int intValue26 = 0; intValue26 < intValue23; intValue26++) {
               ItemStack itemStack2 = this.resolve4(intValue26);
               if (itemStack2 != null && !itemStack2.isEmpty()) {
                  float floatValue166 = floatValue161 + intValue26 * floatValue160 + (floatValue160 - floatValue165) * 0.5F;
                  float floatValue167 = floatValue162 + (floatValue160 - floatValue165) * 0.5F;
                  ItemRenderUtil.invoke3(renderManager14, itemStack2, ItemRenderUtil.measure(floatValue166), ItemRenderUtil.measure(floatValue167), floatValue164, intValue26, true, intValue26);
               }
            }
         } finally {
            renderManager14.invoke20();
            renderManager14.invoke25();
         }
      }
   }

   private ItemStack resolve4(int i) {
      if (this.client == null || this.client.player == null) {
         return ItemStack.EMPTY;
      } else if (this.resolve15().kind == HudConstructorScreen.HudConstructorScreenState2.HOTBAR) {
         return this.client.player.getInventory().getStack(i);
      } else {
         return switch (i) {
            case 0 -> this.client.player.getEquippedStack(EquipmentSlot.HEAD);
            case 1 -> this.client.player.getEquippedStack(EquipmentSlot.CHEST);
            case 2 -> this.client.player.getEquippedStack(EquipmentSlot.LEGS);
            case 3 -> this.client.player.getEquippedStack(EquipmentSlot.FEET);
            default -> ItemStack.EMPTY;
         };
      }
   }

   private void invoke18(RenderManager renderManager15, float f, float g, float h, float i) {
      HudLayoutManager.HudLayoutManagerState hudLayoutManagerState5 = this.resolve13();
      hudLayoutManagerState5.invoke();
      HudElement hudElement4 = this.resolve14();
      float floatValue168 = hudLayoutManagerState5.floatValue12;
      float floatValue169 = hudLayoutManagerState5.floatValue11;
      float floatValue170 = hudLayoutManagerState5.floatValue8 * 2.0F + floatValue169;

      for (String text12 : WILD) {
         floatValue170 += hudLayoutManagerState5.floatValue9 + TextMeasureCache.measure(FontRegistry.fontObject, text12, floatValue168) + 36.0F;
      }

      float floatValue171 = floatValue169 + hudLayoutManagerState5.floatValue8 * 2.0F;
      HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState8 = this.resolve5(f, g, h, i, floatValue170, floatValue171);
      float floatValue172 = hudConstructorScreenState8.floatValue;
      float floatValue173 = hudConstructorScreenState8.floatValue2;
      float floatValue174 = hudConstructorScreenState8.floatValue3;
      float floatValue175 = hudConstructorScreenState8.floatValue4;
      float floatValue176 = this.floatValue5;
      float floatValue177 = this.floatValue6;
      float floatValue178 = Math.min(floatValue176, floatValue177);
      this.invoke22(renderManager15, hudElement4, floatValue172, floatValue173, floatValue174, floatValue175, hudLayoutManagerState5.floatValue * floatValue178, 0.95F);
      this.hudConstructorScreenState4.resolve(floatValue172, floatValue173, floatValue174, floatValue175);
      float floatValue179 = floatValue172 + hudLayoutManagerState5.floatValue8 * floatValue176;
      float floatValue180 = floatValue173 + hudLayoutManagerState5.floatValue8 * floatValue177;
      float floatValue181 = floatValue169 * floatValue177;
      float floatValue182 = floatValue169 * floatValue176;
      renderManager15.invoke5(
         (float)Math.round(floatValue179),
         (float)Math.round(floatValue180),
         Math.max(1.0F, (float)Math.round(floatValue182)),
         Math.max(1.0F, (float)Math.round(floatValue181)),
         hudLayoutManagerState5.floatValue4 * floatValue178,
         hudElement4.compute2(0.95F)
      );
      float floatValue183 = TextMeasureCache.measure(BrandMark.font(), BrandMark.GLYPH, hudLayoutManagerState5.floatValue13 * floatValue178);
      renderManager15.invoke69(
         BrandMark.font(),
         floatValue179 + (floatValue182 - floatValue183) * 0.5F,
         floatValue180 + floatValue181 * 0.5F + 5.5F * floatValue177,
         hudLayoutManagerState5.floatValue13 * floatValue178,
         BrandMark.GLYPH,
         hudElement4.compute9(0.95F)
      );
      this.hudConstructorScreenState7.resolve(floatValue179, floatValue180, floatValue182, floatValue181);
      floatValue179 += floatValue182;

      for (int intValue27 = 0; intValue27 < WILD.length; intValue27++) {
         floatValue179 += hudLayoutManagerState5.floatValue9 * floatValue176;
         float floatValue184 = TextMeasureCache.measure(FontRegistry.fontObject, WILD[intValue27], floatValue168 * floatValue178);
         float floatValue185 = floatValue184 + 36.0F * floatValue176;
         renderManager15.invoke5(
            (float)Math.round(floatValue179),
            (float)Math.round(floatValue180),
            Math.max(1.0F, (float)Math.round(floatValue185)),
            Math.max(1.0F, (float)Math.round(floatValue181)),
            hudLayoutManagerState5.floatValue5 * floatValue178,
            hudElement4.compute2(0.88F)
         );
         FontObject chipIconFont = intValue27 == 0 ? BrandMark.font() : FontRegistry.fontObject8;
         renderManager15.invoke69(
            chipIconFont,
            floatValue179 + 9.0F * floatValue176,
            floatValue180 + floatValue181 * 0.5F + 5.0F * floatValue177,
            22.0F * floatValue178,
            D[intValue27],
            hudElement4.compute9(0.9F)
         );
         renderManager15.invoke69(
            FontRegistry.fontObject, floatValue179 + 25.0F * floatValue176, floatValue180 + floatValue181 * 0.5F + 4.5F * floatValue177, floatValue168 * floatValue178, WILD[intValue27], hudElement4.compute6(0.92F)
         );
         floatValue179 += floatValue185;
      }

      this.hudConstructorScreenState6.resolve(floatValue172 + hudLayoutManagerState5.floatValue8 * floatValue176, floatValue180, floatValue174 - hudLayoutManagerState5.floatValue8 * 2.0F * floatValue176, floatValue181);
      this.DynamicButtonSetting.resolve2(this.hudConstructorScreenState6);
   }

   private void invoke19(RenderManager renderManager16, float f, float g, float h, float i) {
      this.invoke15(renderManager16, f, g, h, i, "ArrayList", FontRegistry.fontObject8, "n", HITAURA_2, TEXT, 24.0F, false);
      this.hudConstructorScreenState5.resolve3();
      this.hudConstructorScreenState2.resolve3();
      this.hudConstructorScreenState3.resolve3();
   }

   private void invoke20(RenderManager renderManager17, float f, float g, float h, float i) {
      HudLayoutManager.HudLayoutManagerState hudLayoutManagerState6 = this.resolve13();
      hudLayoutManagerState6.invoke();
      HudElement hudElement5 = this.resolve14();
      float floatValue186 = 190.0F + hudLayoutManagerState6.floatValue14;
      float floatValue187 = 72.0F + hudLayoutManagerState6.floatValue8 * 2.0F;
      HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState9 = this.resolve5(f, g, h, i, floatValue186, floatValue187);
      float floatValue188 = hudConstructorScreenState9.floatValue;
      float floatValue189 = hudConstructorScreenState9.floatValue2;
      float floatValue190 = hudConstructorScreenState9.floatValue3;
      float floatValue191 = hudConstructorScreenState9.floatValue4;
      float floatValue192 = this.floatValue5;
      float floatValue193 = this.floatValue6;
      float floatValue194 = Math.min(floatValue192, floatValue193);
      this.invoke22(renderManager17, hudElement5, floatValue188, floatValue189, floatValue190, floatValue191, hudLayoutManagerState6.floatValue * floatValue194, 0.95F);
      this.hudConstructorScreenState4.resolve(floatValue188, floatValue189, floatValue190, floatValue191);
      float floatValue195 = 46.0F * floatValue194;
      float floatValue196 = floatValue188 + hudLayoutManagerState6.floatValue8 * floatValue192;
      float floatValue197 = floatValue189 + (floatValue191 - floatValue195) * 0.5F;
      renderManager17.invoke5(
         (float)Math.round(floatValue196),
         (float)Math.round(floatValue197),
         Math.max(1.0F, (float)Math.round(floatValue195)),
         Math.max(1.0F, (float)Math.round(floatValue195)),
         hudLayoutManagerState6.floatValue7 * floatValue194 + 7.0F * floatValue194,
         hudElement5.compute2(0.95F)
      );
      renderManager17.invoke69(FontRegistry.fontObject8, floatValue196 + floatValue195 * 0.32F, floatValue197 + floatValue195 * 0.63F, 28.0F * floatValue194, "r", hudElement5.compute9(0.9F));
      float floatValue198 = floatValue196 + floatValue195 + hudLayoutManagerState6.floatValue9 * floatValue192 + 8.0F * floatValue192 + this.measure13(hudLayoutManagerState6.hudLayoutManagerState33.floatValue, "modules.x") * floatValue192;
      float floatValue199 = floatValue189 + hudLayoutManagerState6.floatValue8 * floatValue193 + 8.0F * floatValue193 + this.measure13(hudLayoutManagerState6.hudLayoutManagerState33.floatValue2, "modules.y") * floatValue193;
      renderManager17.invoke69(FontRegistry.fontObject4, floatValue198, floatValue199 + 12.0F * floatValue193, 24.0F * floatValue194, "Enemy", hudElement5.compute6(0.95F));
      float floatValue200 = floatValue199 + 28.0F * floatValue193;
      float floatValue201 = floatValue190 - (floatValue198 - floatValue188) - hudLayoutManagerState6.floatValue8 * floatValue192;
      renderManager17.invoke5(
         (float)Math.round(floatValue198),
         (float)Math.round(floatValue200),
         Math.max(1.0F, (float)Math.round(floatValue201)),
         Math.max(1.0F, (float)Math.round(8.0F * floatValue193)),
         hudLayoutManagerState6.floatValue6 * floatValue194,
         hudElement5.compute3(0.88F)
      );
      renderManager17.invoke5(
         (float)Math.round(floatValue198),
         (float)Math.round(floatValue200),
         Math.max(1.0F, (float)Math.round(floatValue201 * 0.68F)),
         Math.max(1.0F, (float)Math.round(8.0F * floatValue193)),
         hudLayoutManagerState6.floatValue6 * floatValue194,
         hudElement5.compute9(0.9F)
      );
      this.hudConstructorScreenState7.resolve(floatValue198, floatValue199, floatValue190 - (floatValue198 - floatValue188) - hudLayoutManagerState6.floatValue8 * floatValue192, 44.0F * floatValue193);
      this.hudConstructorScreenState8.resolve(floatValue196, floatValue197, floatValue195, floatValue195);
      invoke50(this.hudConstructorScreenState6, this.hudConstructorScreenState7, this.hudConstructorScreenState8);
   }

   private void invoke21(
      RenderManager renderManager18,
      HudElement hudElement6,
      HudLayoutManager.HudLayoutManagerState hudLayoutManagerState7,
      float f,
      float g,
      float h,
      float i,
      float j,
      String string,
      FontObject fontObject2,
      FontObject fontObject3,
      String string2
   ) {
      float floatValue202 = Math.min(i, j);
      int intValue28 = hudElement6.compute6(0.95F);
      int intValue29 = hudElement6.compute9(0.95F);
      float floatValue203 = f + this.measure13(hudLayoutManagerState7.hudLayoutManagerState3.floatValue, "title.x") * i;
      float floatValue204 = g + this.measure13(hudLayoutManagerState7.hudLayoutManagerState3.floatValue2, "title.y") * j;
      float floatValue205 = hudLayoutManagerState7.floatValue12 * floatValue202;
      renderManager18.invoke69(fontObject2, floatValue203, floatValue204, floatValue205, string, intValue28);
      float floatValue206 = TextMeasureCache.measure(fontObject2, string, floatValue205);
      this.hudConstructorScreenState2.resolve(floatValue203 - 4.0F, floatValue204 - floatValue205 * 0.8F, floatValue206 + 8.0F, floatValue205);
      float floatValue207 = hudLayoutManagerState7.floatValue13 * floatValue202;
      float floatValue208 = (hudLayoutManagerState7.hudLayoutManagerState32.flag ? f + h : f) + this.measure13(hudLayoutManagerState7.hudLayoutManagerState32.floatValue, "icon.x") * i;
      float floatValue209 = g + this.measure13(hudLayoutManagerState7.hudLayoutManagerState32.floatValue2, "icon.y") * j;
      float floatValue210 = TextMeasureCache.measure(fontObject3, string2, floatValue207);
      renderManager18.invoke69(fontObject3, floatValue208, floatValue209, floatValue207, string2, intValue29);
      this.hudConstructorScreenState3.resolve(floatValue208 - 6.0F, floatValue209 - floatValue207 * 0.85F, floatValue210 + 12.0F, floatValue207 + 4.0F);
   }

   private void invoke22(RenderManager renderManager19, HudElement hudElement7, float f, float g, float h, float i, float j, float k) {
      hudElement7.invoke(renderManager19, Math.round(f), Math.round(g), Math.round(h), Math.round(i), j, k);
   }

   private void invoke23(RenderManager renderManager20, HudElement hudElement8, float f, float g, float h, float i, float j, boolean bl, float k) {
      if (!(h <= 0.0F) && !(i <= 0.0F)) {
         f = Math.round(f);
         g = Math.round(g);
         h = Math.round(h);
         i = Math.round(i);
         if (!bl || hudElement8.check5()) {
            if (hudElement8.check8()) {
               if (bl) {
                  hudElement8.invoke2(renderManager20, f, g, h, i, j, k);
               } else if (!hudElement8.check15(f, g, h, i, j, false, k, 1)) {
                  renderManager20.invoke5(f, g, h, i, j, hudElement8.compute2(k));
               }
            } else {
               renderManager20.invoke5(f, g, h, i, j, bl ? hudElement8.compute3(k) : hudElement8.compute2(k));
            }
         }
      }
   }

   private HudConstructorScreen.HudConstructorScreenState resolve5(float f, float g, float h, float i, float j, float k) {
      float floatValue211 = HudEditorRenderer.getINSTANCE().measure8(this.getId(), j, k);
      float floatValue212 = this.measure2(h, i, j, k, floatValue211);
      this.floatValue5 = floatValue212;
      this.floatValue6 = floatValue212;
      float floatValue213 = Math.round(j * this.floatValue5);
      float floatValue214 = Math.round(k * this.floatValue6);
      float floatValue215 = this.floats[this.intValue];
      float floatValue216 = this.floats2[this.intValue];
      float floatValue217 = f + (h - floatValue213) * 0.5F + floatValue215;
      float floatValue218 = g + (i - floatValue214) * 0.5F + floatValue216;
      float floatValue219 = this.measure(10.0F);
      floatValue217 = measure18(floatValue217, f + floatValue219, Math.max(f + floatValue219, f + h - floatValue219 - floatValue213));
      floatValue218 = measure18(floatValue218, g + floatValue219, Math.max(g + floatValue219, g + i - floatValue219 - floatValue214));
      return this.FoundryShaderSetting.resolve(Math.round(floatValue217), Math.round(floatValue218), floatValue213, floatValue214);
   }

   private float measure2(float f, float g, float h, float i, float j) {
      float floatValue220 = Math.max(this.measure(60.0F), f - this.measure(72.0F));
      float floatValue221 = Math.max(this.measure(60.0F), g - this.measure(72.0F));
      float floatValue222 = Math.min(floatValue220 / Math.max(1.0F, h), floatValue221 / Math.max(1.0F, i));
      floatValue222 = measure18(floatValue222 * 0.76F, 0.55F, 1.7F);
      float floatValue223 = Math.max(0.35F, (f - this.measure(22.0F)) / Math.max(1.0F, h));
      float floatValue224 = Math.max(0.35F, (g - this.measure(22.0F)) / Math.max(1.0F, i));
      return measure18(floatValue222 * j, 0.35F, Math.min(floatValue223, floatValue224));
   }

   private float measure3(
      String string, FontObject fontObject4, String string2, String[] strings, String[] strings2, float f, boolean bl, HudLayoutManager.HudLayoutManagerState hudLayoutManagerState8
   ) {
      float floatValue225 = TextMeasureCache.measure(FontRegistry.fontObject4, string, hudLayoutManagerState8.floatValue12);
      float floatValue226 = 0.0F;
      float floatValue227 = 0.0F;

      for (int intValue30 = 0; intValue30 < strings.length; intValue30++) {
         floatValue226 = Math.max(floatValue226, TextMeasureCache.measure(FontRegistry.fontObject, strings[intValue30], f));
         if (intValue30 < strings2.length) {
            floatValue227 = Math.max(floatValue227, TextMeasureCache.measure(FontRegistry.fontObject, strings2[intValue30], f));
         }
      }

      float floatValue228 = floatValue226 + 24.0F;
      if (bl) {
         floatValue228 += hudLayoutManagerState8.floatValue9 + floatValue227 + 20.0F + hudLayoutManagerState8.floatValue14;
      }

      float floatValue229 = floatValue225 + hudLayoutManagerState8.floatValue13 + 36.0F;
      return Math.max(floatValue228 + hudLayoutManagerState8.floatValue8 * 2.0F, floatValue229 + hudLayoutManagerState8.floatValue8 * 2.0F);
   }

   private void invoke24(RenderManager renderManager21, ColorScheme colorScheme11) {
      HudLayoutManager.HudLayoutManagerState hudLayoutManagerState9 = this.resolve13();
      this.invoke25();
      float floatValue230 = this.hudConstructorScreenState9.floatValue;
      float floatValue231 = this.hudConstructorScreenState9.floatValue2;
      float floatValue232 = this.hudConstructorScreenState9.floatValue3;
      float floatValue233 = this.hudConstructorScreenState9.floatValue4;
      HudConstructorScreen.HudConstructorScreenDisplayEntry hudConstructorScreenDisplayEntry5 = this.resolve15();
      String[] texts2 = this.resolve2(hudConstructorScreenDisplayEntry5.kind);
      if (!check(texts2, this.panel)) {
         this.panel = texts2[0];
      }

      this.invoke6(renderManager21, colorScheme11, floatValue230, floatValue231, floatValue232, floatValue233);
      float floatValue234 = Math.round(this.measure(50.0F));
      renderManager21.invoke69(
         FontRegistry.fontObject, floatValue230 + this.measure(16.0F), floatValue231 + this.measure(20.0F), this.measure(13.0F), hudConstructorScreenDisplayEntry5.label, compute8(colorScheme11)
      );
      renderManager21.invoke69(
         FontRegistry.fontObject4,
         floatValue230 + this.measure(16.0F),
         floatValue231 + this.measure(39.0F),
         this.measure(16.0F),
         this.resolve16(),
         colorScheme11.getIntValue13()
      );
      this.invoke7(renderManager21, colorScheme11, floatValue230 + this.measure(14.0F), floatValue231 + floatValue234, floatValue232 - this.measure(28.0F));
      float floatValue235 = floatValue231 + floatValue234 + this.measure(1.0F);
      float floatValue236 = Math.max(this.measure(40.0F), floatValue231 + floatValue233 - floatValue235 - this.measure(8.0F));
      this.hudConstructorScreenState10.resolve(floatValue230 + this.measure(4.0F), floatValue235, floatValue232 - this.measure(8.0F), floatValue236);
      renderManager21.invoke20();
      renderManager21.invoke24(
         this.hudConstructorScreenState10.floatValue,
         this.hudConstructorScreenState10.floatValue2,
         this.hudConstructorScreenState10.floatValue3,
         this.hudConstructorScreenState10.floatValue4,
         this.measure(8.0F),
         this.measure(8.0F),
         this.measure(8.0F),
         this.measure(8.0F)
      );
      boolean flag9 = false ;

      try {
         flag9 = true;
         float floatValue237 = floatValue230 + this.measure(16.0F);
         float floatValue238 = floatValue232 - this.measure(32.0F);
         float floatValue239 = floatValue235 + this.measure(12.0F) - this.floatValue7;
         if (!hudConstructorScreenDisplayEntry5.layoutBacked) {
            floatValue239 = this.measure5(renderManager21, colorScheme11, floatValue237, floatValue239, floatValue238);
         }

         floatValue239 = this.measure6(renderManager21, colorScheme11, floatValue237, floatValue239, floatValue238, texts2);
         floatValue239 += this.measure(15.0F);
         floatValue239 = this.measure4(renderManager21, colorScheme11, floatValue237, floatValue239, floatValue238, "ACTIONS");
         floatValue239 = this.measure7(renderManager21, colorScheme11, floatValue237, floatValue239, floatValue238);
         floatValue239 += this.measure(15.0F);
         floatValue239 = this.measure4(renderManager21, colorScheme11, floatValue237, floatValue239, floatValue238, "PRESETS");
         floatValue239 = this.measure8(renderManager21, colorScheme11, floatValue237, floatValue239, floatValue238, hudLayoutManagerState9);
         String text13 = null;

         for (HudConstructorScreen.HudConstructorScreenDisplayEntry2 hudConstructorScreenDisplayEntry22 : HUD_CONSTRUCTOR_SCREEN_DISPLAY_ENTRY2S) {
            if (this.check2(hudConstructorScreenDisplayEntry5, hudConstructorScreenDisplayEntry22.id)) {
               if (!hudConstructorScreenDisplayEntry22.section.equals(text13)) {
                  floatValue239 += this.measure(15.0F);
                  floatValue239 = this.measure4(renderManager21, colorScheme11, floatValue237, floatValue239, floatValue238, hudConstructorScreenDisplayEntry22.section);
                  text13 = hudConstructorScreenDisplayEntry22.section;
               }

               floatValue239 = this.measure9(renderManager21, colorScheme11, floatValue237, floatValue239, floatValue238, hudConstructorScreenDisplayEntry22, this.measure11(hudLayoutManagerState9, hudConstructorScreenDisplayEntry22.id));
            }
         }

         floatValue239 += this.measure(6.0F);
         floatValue239 = this.measure10(renderManager21, colorScheme11, floatValue237, floatValue239);
         floatValue239 += this.measure(14.0F);
         float floatValue240 = this.hudConstructorScreenState10.floatValue2 + this.hudConstructorScreenState10.floatValue4;
         this.floatValue8 = Math.max(0.0F, floatValue239 + this.floatValue7 - floatValue240);
         this.floatValue7 = measure18(this.floatValue7, 0.0F, this.floatValue8);
         flag9 = false;
      } finally {
         if (flag9) {
            renderManager21.invoke20();
            renderManager21.invoke25();
         }
      }

      renderManager21.invoke20();
      renderManager21.invoke25();
      this.animation8.check();
      this.animation8
         .resolve4(this.hudConstructorScreenState9.check(this.floatValue, this.floatValue2) ? 1.0 : 0.0, 0.22F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
      this.invoke8(
         renderManager21,
         colorScheme11,
         floatValue230 + floatValue232 - this.measure(7.0F),
         this.hudConstructorScreenState10.floatValue2,
         this.hudConstructorScreenState10.floatValue4,
         this.floatValue7,
         this.floatValue8,
         this.animation8.measure3()
      );
   }

   private void invoke25() {
      for (HudConstructorScreen.HudConstructorScreenDisplayEntry2 hudConstructorScreenDisplayEntry23 : HUD_CONSTRUCTOR_SCREEN_DISPLAY_ENTRY2S) {
         this.valuesByKey.get(hudConstructorScreenDisplayEntry23.id).resolve3();
      }

      for (String text14 : PANEL_9) {
         this.valuesByKey2.get(text14).resolve3();
      }

      for (String text15 : RESET_2) {
         this.valuesByKey3.get(text15).resolve3();
      }
   }

   private float measure4(RenderManager renderManager22, ColorScheme colorScheme12, float f, float g, float h, String string) {
      renderManager22.invoke69(FontRegistry.fontObject4, f, g + this.measure(10.0F), this.measure(12.0F), string, compute8(colorScheme12));
      float floatValue241 = TextMeasureCache.measure(FontRegistry.fontObject4, string, this.measure(12.0F));
      float floatValue242 = f + floatValue241 + this.measure(10.0F);
      this.invoke7(renderManager22, colorScheme12, floatValue242, g + this.measure(6.0F), Math.max(0.0F, f + h - floatValue242));
      return g + this.measure(24.0F);
   }

   private float measure5(RenderManager renderManager23, ColorScheme colorScheme13, float f, float g, float h) {
      float floatValue243 = this.measure(46.0F);
      int intValue31 = colorScheme13.compute3();
      renderManager23.invoke5(
         (float)Math.round(f),
         (float)Math.round(g),
         (float)Math.round(h),
         (float)Math.round(floatValue243),
         this.measure(10.0F),
         ColorScheme.compute6(intValue31, colorScheme13.isFlag() ? 26 : 20)
      );
      renderManager23.invoke28(
         (float)Math.round(f),
         (float)Math.round(g),
         (float)Math.round(h),
         (float)Math.round(floatValue243),
         this.measure(10.0F),
         ColorScheme.compute6(intValue31, 72),
         1.0F
      );
      float floatValue244 = Math.round(this.measure(6.0F));
      renderManager23.invoke5((float)Math.round(f + this.measure(14.0F)), (float)Math.round(g + floatValue243 * 0.5F - floatValue244 * 0.5F), floatValue244, floatValue244, floatValue244 * 0.5F, intValue31);
      float floatValue245 = f + this.measure(14.0F) + floatValue244 + this.measure(10.0F);
      renderManager23.invoke69(
         FontRegistry.fontObject4, floatValue245, g + this.measure(19.0F), this.measure(12.5F), "Style preview", ColorScheme.compute6(intValue31, 240)
      );
      renderManager23.invoke69(
         FontRegistry.fontObject, floatValue245, g + this.measure(34.0F), this.measure(12.0F), "Position & scale stay live", compute7(colorScheme13)
      );
      return g + floatValue243 + this.measure(12.0F);
   }

   private float measure6(RenderManager renderManager24, ColorScheme colorScheme14, float f, float g, float h, String[] strings) {
      int intValue32 = strings.length;
      int intValue33 = intValue32 <= 4 ? 1 : 2;
      int intValue34 = (int)Math.ceil((float)intValue32 / intValue33);
      float floatValue246 = this.measure(7.0F);
      float floatValue247 = this.measure(7.0F);
      float floatValue248 = this.measure(33.0F);
      float floatValue249 = this.measure(15.0F);
      float floatValue250 = this.measure(9.0F);
      float floatValue251 = (h - floatValue246 * (intValue34 - 1)) / intValue34;
      HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState10 = null;

      for (int intValue35 = 0; intValue35 < intValue32; intValue35++) {
         String text16 = strings[intValue35];
         int intValue36 = intValue35 % intValue34;
         int intValue37 = intValue35 / intValue34;
         float floatValue252 = f + intValue36 * (floatValue251 + floatValue246);
         float floatValue253 = g + intValue37 * (floatValue248 + floatValue247);
         HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState11 = this.valuesByKey2.get(text16);
         hudConstructorScreenState11.resolve(Math.round(floatValue252), Math.round(floatValue253), Math.round(floatValue251), Math.round(floatValue248));
         if (text16.equals(this.panel)) {
            hudConstructorScreenState10 = hudConstructorScreenState11;
         }
      }

      if (hudConstructorScreenState10 != null) {
         this.animation3.check();
         this.animation4.check();
         this.animation5.check();
         this.animation6.check();
         boolean flag10 = !this.panel.equals(this.text);
         if (!(this.animation5.measure3() <= 0.0F) && flag10) {
            this.animation3.resolve4(hudConstructorScreenState10.floatValue, 0.22F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_6, false);
            this.animation4.resolve4(hudConstructorScreenState10.floatValue2, 0.22F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_6, false);
            this.animation5.resolve4(hudConstructorScreenState10.floatValue3, 0.22F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_6, false);
            this.animation6.resolve4(hudConstructorScreenState10.floatValue4, 0.22F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_6, false);
            if (Math.abs(this.animation3.measure3() - hudConstructorScreenState10.floatValue) < 0.6F && Math.abs(this.animation4.measure3() - hudConstructorScreenState10.floatValue2) < 0.6F) {
               this.text = this.panel;
            }
         } else {
            this.animation3.invoke(hudConstructorScreenState10.floatValue);
            this.animation4.invoke(hudConstructorScreenState10.floatValue2);
            this.animation5.invoke(hudConstructorScreenState10.floatValue3);
            this.animation6.invoke(hudConstructorScreenState10.floatValue4);
            this.text = this.panel;
         }

         float floatValue254 = Math.round(this.animation3.measure3());
         float floatValue255 = Math.round(this.animation4.measure3());
         float floatValue256 = Math.round(this.animation5.measure3());
         float floatValue257 = Math.round(this.animation6.measure3());
         renderManager24.invoke41(
            floatValue254, floatValue255, floatValue256, floatValue257, floatValue250, this.measure(7.0F), this.measure(0.5F), compute5(colorScheme14, colorScheme14.isFlag() ? 34 : 46)
         );
         renderManager24.invoke5(floatValue254, floatValue255, floatValue256, floatValue257, floatValue250, compute5(colorScheme14, colorScheme14.isFlag() ? 42 : 36));
         renderManager24.invoke28(floatValue254, floatValue255, floatValue256, floatValue257, floatValue250, compute5(colorScheme14, colorScheme14.isFlag() ? 98 : 76), 1.0F);
      }

      for (String text17 : strings) {
         HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState12 = this.valuesByKey2.get(text17);
         boolean flag11 = text17.equals(this.panel);
         float floatValue258 = this.measure14(text17, !hudConstructorScreenState12.check(this.floatValue, this.floatValue2) && !flag11 ? 0.0F : 1.0F);
         if (!flag11) {
            renderManager24.invoke5(
               hudConstructorScreenState12.floatValue,
               hudConstructorScreenState12.floatValue2,
               hudConstructorScreenState12.floatValue3,
               hudConstructorScreenState12.floatValue4,
               floatValue250,
               ColorScheme.compute6(
                  colorScheme14.getIntValue13(),
                  Math.round((colorScheme14.isFlag() ? 12.0F : 9.0F) + floatValue258 * (colorScheme14.isFlag() ? 14.0F : 11.0F))
               )
            );
            renderManager24.invoke28(hudConstructorScreenState12.floatValue, hudConstructorScreenState12.floatValue2, hudConstructorScreenState12.floatValue3, hudConstructorScreenState12.floatValue4, floatValue250, colorScheme14.getIntValue7(), 1.0F);
         }

         int intValue38 = flag11 ? colorScheme14.getIntValue13() : ColorScheme.compute7(compute7(colorScheme14), colorScheme14.getIntValue13(), floatValue258 * 0.4F);
         this.invoke52(
            renderManager24,
            FontRegistry.fontObject,
            this.resolve17(text17),
            hudConstructorScreenState12.floatValue,
            hudConstructorScreenState12.floatValue2,
            hudConstructorScreenState12.floatValue3,
            hudConstructorScreenState12.floatValue4,
            floatValue249,
            intValue38
         );
      }

      return g + intValue33 * floatValue248 + (intValue33 - 1) * floatValue247;
   }

   private float measure7(RenderManager renderManager25, ColorScheme colorScheme15, float f, float g, float h) {
      float floatValue259 = this.measure(8.0F);
      float floatValue260 = (h - floatValue259 * 2.0F) / 3.0F;
      float floatValue261 = this.measure(34.0F);
      this.invoke26(renderManager25, colorScheme15, "centerX", f, g, floatValue260, floatValue261, "Center X", false, false);
      this.invoke26(renderManager25, colorScheme15, "centerY", f + floatValue260 + floatValue259, g, floatValue260, floatValue261, "Center Y", false, false);
      this.invoke26(renderManager25, colorScheme15, "reset", f + (floatValue260 + floatValue259) * 2.0F, g, floatValue260, floatValue261, "Reset", false, false);
      return g + floatValue261;
   }

   private float measure8(RenderManager renderManager26, ColorScheme colorScheme16, float f, float g, float h, HudLayoutManager.HudLayoutManagerState hudLayoutManagerState10) {
      float floatValue262 = this.measure(8.0F);
      float floatValue263 = (h - floatValue262 * 2.0F) / 3.0F;
      float floatValue264 = this.measure(34.0F);
      int intValue39 = this.compute3(hudLayoutManagerState10);
      this.invoke26(renderManager26, colorScheme16, "presetSoft", f, g, floatValue263, floatValue264, "Soft", true, intValue39 == 0);
      this.invoke26(renderManager26, colorScheme16, "presetCompact", f + floatValue263 + floatValue262, g, floatValue263, floatValue264, "Compact", true, intValue39 == 1);
      this.invoke26(renderManager26, colorScheme16, "presetSharp", f + (floatValue263 + floatValue262) * 2.0F, g, floatValue263, floatValue264, "Sharp", true, intValue39 == 2);
      return g + floatValue264;
   }

   private int compute3(HudLayoutManager.HudLayoutManagerState hudLayoutManagerState11) {
      if (this.check3(hudLayoutManagerState11, 17.0F, 8.0F)) {
         return 0;
      } else if (this.check3(hudLayoutManagerState11, 10.0F, 5.0F)) {
         return 1;
      } else {
         return this.check3(hudLayoutManagerState11, 4.0F, 7.0F) ? 2 : -1;
      }
   }

   private boolean check3(HudLayoutManager.HudLayoutManagerState hudLayoutManagerState12, float f, float g) {
      return Math.abs(hudLayoutManagerState12.floatValue - f) < 1.2F && Math.abs(hudLayoutManagerState12.floatValue8 - g) < 1.2F;
   }

   private void invoke26(
      RenderManager renderManager27, ColorScheme colorScheme17, String string, float f, float g, float h, float i, String string2, boolean bl, boolean bl2
   ) {
      HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState13 = this.valuesByKey3.get(string);
      hudConstructorScreenState13.resolve(Math.round(f), Math.round(g), Math.round(h), Math.round(i));
      float floatValue265 = this.measure14(string, hudConstructorScreenState13.check(this.floatValue, this.floatValue2) ? 1.0F : 0.0F);
      float floatValue266 = this.measure(11.0F);
      int intValue40;
      int intValue41;
      int intValue42;
      if (bl2) {
         intValue40 = compute5(colorScheme17, colorScheme17.isFlag() ? 44 : 38);
         intValue41 = compute5(colorScheme17, colorScheme17.isFlag() ? 108 : 84);
         intValue42 = colorScheme17.getIntValue13();
      } else {
         int intValue43 = bl
            ? compute5(colorScheme17, colorScheme17.isFlag() ? 22 : 18)
            : ColorScheme.compute6(colorScheme17.getIntValue13(), colorScheme17.isFlag() ? 16 : 12);
         intValue40 = ColorScheme.compute7(intValue43, compute5(colorScheme17, colorScheme17.isFlag() ? 42 : 36), floatValue265);
         intValue41 = ColorScheme.compute7(colorScheme17.getIntValue7(), compute5(colorScheme17, 92), floatValue265);
         intValue42 = ColorScheme.compute7(compute7(colorScheme17), colorScheme17.getIntValue13(), 0.2F + floatValue265 * 0.6F);
      }

      renderManager27.invoke5(hudConstructorScreenState13.floatValue, hudConstructorScreenState13.floatValue2, hudConstructorScreenState13.floatValue3, hudConstructorScreenState13.floatValue4, floatValue266, intValue40);
      renderManager27.invoke28(hudConstructorScreenState13.floatValue, hudConstructorScreenState13.floatValue2, hudConstructorScreenState13.floatValue3, hudConstructorScreenState13.floatValue4, floatValue266, intValue41, 1.0F);
      this.invoke52(
         renderManager27,
         FontRegistry.fontObject4,
         string2,
         hudConstructorScreenState13.floatValue,
         hudConstructorScreenState13.floatValue2,
         hudConstructorScreenState13.floatValue3,
         hudConstructorScreenState13.floatValue4,
         this.measure(15.0F),
         intValue42
      );
   }

   private float measure9(RenderManager renderManager28, ColorScheme colorScheme18, float f, float g, float h, HudConstructorScreen.HudConstructorScreenDisplayEntry2 hudConstructorScreenDisplayEntry24, float i) {
      float floatValue267 = measure18((i - hudConstructorScreenDisplayEntry24.min) / Math.max(0.001F, hudConstructorScreenDisplayEntry24.max - hudConstructorScreenDisplayEntry24.min), 0.0F, 1.0F);
      renderManager28.invoke69(FontRegistry.fontObject, f, g + this.measure(11.0F), this.measure(13.5F), hudConstructorScreenDisplayEntry24.label, compute7(colorScheme18));
      String text18 = this.valuesByKey7.get(hudConstructorScreenDisplayEntry24.id);
      if (text18 == null) {
         text18 = "";
      }

      float floatValue268 = this.measure(13.5F);
      float floatValue269 = TextMeasureCache.measure(FontRegistry.fontObject4, text18, floatValue268);
      renderManager28.invoke69(FontRegistry.fontObject4, f + h - floatValue269, g + this.measure(11.0F), floatValue268, text18, colorScheme18.compute4());
      float floatValue270 = Math.round(g + this.measure(24.0F));
      float floatValue271 = Math.max(2.0F, (float)Math.round(this.measure(4.0F)));
      float floatValue272 = Math.round(f);
      float floatValue273 = Math.round(h);
      float floatValue274 = floatValue271 * 0.5F;
      float floatValue275 = measure18(this.measure13(floatValue267, hudConstructorScreenDisplayEntry24.id), 0.0F, 1.0F);
      float floatValue276 = Math.max(0.0F, floatValue273 * floatValue275);
      HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState14 = this.valuesByKey.get(hudConstructorScreenDisplayEntry24.id);
      hudConstructorScreenState14.resolve(floatValue272, Math.round(floatValue270 - this.measure(11.0F)), floatValue273, Math.round(this.measure(26.0F)));
      boolean flag12 = hudConstructorScreenDisplayEntry24.id.equals(this.text3);
      float floatValue277 = this.measure14(hudConstructorScreenDisplayEntry24.id + ".thumb", !hudConstructorScreenState14.check(this.floatValue, this.floatValue2) && !flag12 ? 0.0F : 1.0F);
      renderManager28.invoke5(floatValue272, floatValue270, floatValue273, floatValue271, floatValue274, colorScheme18.getIntValue8());
      if (floatValue276 > 1.0F) {
         renderManager28.invoke41(
            floatValue272, floatValue270, floatValue276, floatValue271, floatValue274, this.measure(4.0F), this.measure(0.5F), compute5(colorScheme18, Math.round(36.0F + floatValue277 * 70.0F))
         );
         renderManager28.invoke34(floatValue272, floatValue270, floatValue276, floatValue271, floatValue274, colorScheme18.getIntValue14(), compute6(colorScheme18, 205));
      }

      float floatValue278 = floatValue272 + floatValue276;
      float floatValue279 = floatValue270 + floatValue271 * 0.5F;
      float floatValue280 = this.measure(7.0F) + floatValue277 * this.measure(1.0F);
      int intValue44 = Math.round((flag12 ? 150.0F : 68.0F) + floatValue277 * 95.0F);
      renderManager28.invoke41(
         floatValue278 - floatValue280, floatValue279 - floatValue280, floatValue280 * 2.0F, floatValue280 * 2.0F, floatValue280, this.measure(6.0F), this.measure(0.5F), compute5(colorScheme18, intValue44)
      );
      renderManager28.invoke39(floatValue278, floatValue279, floatValue280, 0.0F, 1.0F, compute5(colorScheme18, 240));
      renderManager28.invoke39(floatValue278, floatValue279, floatValue280 - this.measure(1.6F), 0.0F, 1.0F, colorScheme18.getIntValue13());
      return g + this.measure(40.0F);
   }

   private float measure10(RenderManager renderManager29, ColorScheme colorScheme19, float f, float g) {
      HudLayoutManager.HudLayoutManagerState3 hudLayoutManagerState32 = this.resolve11();
      if (hudLayoutManagerState32 == null) {
         renderManager29.invoke69(
            FontRegistry.fontObject, f, g + this.measure(13.0F), this.measure(13.0F), "Drag title, modules, binds or icon", compute8(colorScheme19)
         );
         return g + this.measure(22.0F);
      } else if ("icon".equals(this.panel)) {
         renderManager29.invoke69(FontRegistry.fontObject, f, g + this.measure(13.0F), this.measure(13.0F), this.text6, compute7(colorScheme19));
         return g + this.measure(22.0F);
      } else {
         renderManager29.invoke69(FontRegistry.fontObject, f, g + this.measure(13.0F), this.measure(13.0F), this.text4, compute7(colorScheme19));
         renderManager29.invoke69(
            FontRegistry.fontObject, f + this.measure(102.0F), g + this.measure(13.0F), this.measure(13.0F), this.text5, compute7(colorScheme19)
         );
         return g + this.measure(22.0F);
      }
   }

   private void invoke27(RenderManager renderManager30, ColorScheme colorScheme20) {
      this.invoke29(renderManager30, colorScheme20, this.hudConstructorScreenState4, "panel");
      this.invoke29(renderManager30, colorScheme20, this.hudConstructorScreenState5, "header");
      this.invoke29(renderManager30, colorScheme20, this.hudConstructorScreenState6, "content");
      this.invoke29(renderManager30, colorScheme20, this.hudConstructorScreenState7, "modules");
      this.invoke29(renderManager30, colorScheme20, this.DynamicButtonSetting, "binds");
      this.invoke29(renderManager30, colorScheme20, this.hudConstructorScreenState8, "slots");
      this.invoke29(renderManager30, colorScheme20, this.hudConstructorScreenState2, "title");
      this.invoke29(renderManager30, colorScheme20, this.hudConstructorScreenState3, "icon");
   }

   private void invoke28(RenderManager renderManager31, ColorScheme colorScheme21) {
      if (!(this.hudConstructorScreenState4.floatValue3 <= 0.0F) && !(this.hudConstructorScreenState4.floatValue4 <= 0.0F)) {
         if (this.flag2 && (this.flag4 || this.flag5)) {
            int intValue45 = compute5(colorScheme21, 170);
            if (this.flag4) {
               renderManager31.invoke5(
                  (float)Math.round(this.hudConstructorScreenState11.floatValue + this.hudConstructorScreenState11.floatValue3 * 0.5F),
                  (float)Math.round(this.hudConstructorScreenState11.floatValue2),
                  Math.max(1.0F, this.measure(1.0F)),
                  (float)Math.round(this.hudConstructorScreenState11.floatValue4),
                  0.0F,
                  intValue45
               );
            }

            if (this.flag5) {
               renderManager31.invoke5(
                  (float)Math.round(this.hudConstructorScreenState11.floatValue),
                  (float)Math.round(this.hudConstructorScreenState11.floatValue2 + this.hudConstructorScreenState11.floatValue4 * 0.5F),
                  (float)Math.round(this.hudConstructorScreenState11.floatValue3),
                  Math.max(1.0F, this.measure(1.0F)),
                  0.0F,
                  intValue45
               );
            }
         }

         float floatValue281 = !this.flag2 && !this.flag3 ? 0.0F : 1.0F;
         float floatValue282 = Math.min(this.measure(10.0F), Math.min(this.hudConstructorScreenState4.floatValue3, this.hudConstructorScreenState4.floatValue4) * 0.25F);
         int intValue46 = ColorScheme.compute6(colorScheme21.getIntValue14(), Math.round(110.0F + floatValue281 * 100.0F));
         if (floatValue281 > 0.0F) {
            renderManager31.invoke41(
               this.hudConstructorScreenState4.floatValue,
               this.hudConstructorScreenState4.floatValue2,
               this.hudConstructorScreenState4.floatValue3,
               this.hudConstructorScreenState4.floatValue4,
               floatValue282,
               this.measure(9.0F),
               this.measure(1.0F),
               ColorScheme.compute6(colorScheme21.getIntValue14(), 42)
            );
         }

         renderManager31.invoke28(
            (float)Math.round(this.hudConstructorScreenState4.floatValue),
            (float)Math.round(this.hudConstructorScreenState4.floatValue2),
            (float)Math.round(this.hudConstructorScreenState4.floatValue3),
            (float)Math.round(this.hudConstructorScreenState4.floatValue4),
            floatValue282,
            intValue46,
            Math.max(1.0F, this.measure(1.25F))
         );
         float floatValue283 = this.measure(17.0F);
         float floatValue284 = Math.round(this.hudConstructorScreenState4.floatValue + this.hudConstructorScreenState4.floatValue3 - floatValue283 * 0.72F);
         float floatValue285 = Math.round(this.hudConstructorScreenState4.floatValue2 + this.hudConstructorScreenState4.floatValue4 - floatValue283 * 0.72F);
         this.SpacerSetting.resolve(floatValue284 - this.measure(3.0F), floatValue285 - this.measure(3.0F), floatValue283 + this.measure(6.0F), floatValue283 + this.measure(6.0F));
         float floatValue286 = this.measure14("preview.resize", !this.SpacerSetting.check(this.floatValue, this.floatValue2) && !this.flag3 ? 0.0F : 1.0F);
         float floatValue287 = Math.max(1.0F, (float)Math.round(floatValue283));
         renderManager31.invoke5(
            floatValue284,
            floatValue285,
            floatValue287,
            floatValue287,
            this.measure(5.0F),
            ColorScheme.compute7(ColorScheme.compute6(colorScheme21.getIntValue(), 228), ColorScheme.compute6(colorScheme21.getIntValue14(), 92), floatValue286)
         );
         renderManager31.invoke28(
            floatValue284,
            floatValue285,
            floatValue287,
            floatValue287,
            this.measure(5.0F),
            ColorScheme.compute6(colorScheme21.getIntValue14(), Math.round(105.0F + floatValue286 * 100.0F)),
            Math.max(1.0F, this.measure(1.0F))
         );
         float floatValue288 = Math.max(1.0F, this.measure(1.0F));
         int intValue47 = ColorScheme.compute6(colorScheme21.getIntValue13(), Math.round(130.0F + floatValue286 * 100.0F));
         renderManager31.invoke5(
            (float)Math.round(floatValue284 + this.measure(5.0F)),
            (float)Math.round(floatValue285 + this.measure(11.0F)),
            Math.max(1.0F, (float)Math.round(this.measure(7.0F))),
            Math.max(1.0F, (float)Math.round(floatValue288)),
            floatValue288 * 0.5F,
            intValue47
         );
         renderManager31.invoke5(
            (float)Math.round(floatValue284 + this.measure(8.0F)),
            (float)Math.round(floatValue285 + this.measure(8.0F)),
            Math.max(1.0F, (float)Math.round(this.measure(4.0F))),
            Math.max(1.0F, (float)Math.round(floatValue288)),
            floatValue288 * 0.5F,
            intValue47
         );
         renderManager31.invoke5(
            (float)Math.round(floatValue284 + this.measure(11.0F)),
            (float)Math.round(floatValue285 + this.measure(5.0F)),
            Math.max(1.0F, (float)Math.round(floatValue288)),
            Math.max(1.0F, (float)Math.round(floatValue288)),
            floatValue288 * 0.5F,
            intValue47
         );
      } else {
         this.SpacerSetting.resolve3();
      }
   }

   private void invoke29(RenderManager renderManager32, ColorScheme colorScheme22, HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState15, String string) {
      if (hudConstructorScreenState15 != null && !(hudConstructorScreenState15.floatValue3 <= 0.0F) && !(hudConstructorScreenState15.floatValue4 <= 0.0F)) {
         boolean flag13 = string.equals(this.panel);
         boolean flag14 = hudConstructorScreenState15.check(this.floatValue, this.floatValue2);
         float floatValue289 = this.measure15(string, !flag13 && !flag14 ? 0.0F : 1.0F);
         int intValue48 = flag13
            ? ColorScheme.compute6(colorScheme22.getIntValue14(), 210)
            : ColorScheme.compute6(colorScheme22.getIntValue13(), Math.round(30.0F + floatValue289 * 72.0F));
         renderManager32.invoke28(
            (float)Math.round(hudConstructorScreenState15.floatValue),
            (float)Math.round(hudConstructorScreenState15.floatValue2),
            Math.max(1.0F, (float)Math.round(hudConstructorScreenState15.floatValue3)),
            Math.max(1.0F, (float)Math.round(hudConstructorScreenState15.floatValue4)),
            this.measure(4.0F),
            intValue48,
            flag13 ? Math.max(1.5F, this.measure(1.5F)) : Math.max(1.0F, this.measure(1.0F))
         );
      }
   }

   private String resolve6(float f, float g) {
      if (this.hudConstructorScreenState2.check(f, g)) {
         return "title";
      } else if (this.hudConstructorScreenState3.check(f, g)) {
         return "icon";
      } else if (this.hudConstructorScreenState8.check(f, g)) {
         return "slots";
      } else if (this.hudConstructorScreenState7.check(f, g)) {
         return "modules";
      } else if (this.DynamicButtonSetting.check(f, g)) {
         return "binds";
      } else if (this.hudConstructorScreenState5.check(f, g)) {
         return "header";
      } else if (this.hudConstructorScreenState6.check(f, g)) {
         return "content";
      } else {
         return this.hudConstructorScreenState4.check(f, g) ? "panel" : null;
      }
   }

   private void invoke30(float f, float g) {
      if (!(this.hudConstructorScreenState11.floatValue3 <= 0.0F)
         && !(this.hudConstructorScreenState11.floatValue4 <= 0.0F)
         && !(this.hudConstructorScreenState4.floatValue3 <= 0.0F)
         && !(this.hudConstructorScreenState4.floatValue4 <= 0.0F)) {
         float floatValue290 = this.measure(10.0F);
         float floatValue291 = this.hudConstructorScreenState11.floatValue + floatValue290;
         float floatValue292 = this.hudConstructorScreenState11.floatValue2 + floatValue290;
         float floatValue293 = this.hudConstructorScreenState11.floatValue + this.hudConstructorScreenState11.floatValue3 - this.hudConstructorScreenState4.floatValue3 - floatValue290;
         float floatValue294 = this.hudConstructorScreenState11.floatValue2 + this.hudConstructorScreenState11.floatValue4 - this.hudConstructorScreenState4.floatValue4 - floatValue290;
         float floatValue295 = measure18(this.hudConstructorScreenState4.floatValue + f, floatValue291, Math.max(floatValue291, floatValue293));
         float floatValue296 = measure18(this.hudConstructorScreenState4.floatValue2 + g, floatValue292, Math.max(floatValue292, floatValue294));
         float floatValue297 = this.measure(7.0F);
         float floatValue298 = this.hudConstructorScreenState11.floatValue + (this.hudConstructorScreenState11.floatValue3 - this.hudConstructorScreenState4.floatValue3) * 0.5F;
         float floatValue299 = this.hudConstructorScreenState11.floatValue2 + (this.hudConstructorScreenState11.floatValue4 - this.hudConstructorScreenState4.floatValue4) * 0.5F;
         this.flag4 = Math.abs(floatValue295 - floatValue298) < floatValue297;
         this.flag5 = Math.abs(floatValue296 - floatValue299) < floatValue297;
         if (this.flag4) {
            floatValue295 = measure18(floatValue298, floatValue291, Math.max(floatValue291, floatValue293));
         } else if (Math.abs(floatValue295 - floatValue291) < floatValue297) {
            floatValue295 = floatValue291;
         } else if (Math.abs(floatValue295 - floatValue293) < floatValue297) {
            floatValue295 = Math.max(floatValue291, floatValue293);
         }

         if (this.flag5) {
            floatValue296 = measure18(floatValue299, floatValue292, Math.max(floatValue292, floatValue294));
         } else if (Math.abs(floatValue296 - floatValue292) < floatValue297) {
            floatValue296 = floatValue292;
         } else if (Math.abs(floatValue296 - floatValue294) < floatValue297) {
            floatValue296 = Math.max(floatValue292, floatValue294);
         }

         this.floats[this.intValue] = this.floats[this.intValue] + (floatValue295 - this.hudConstructorScreenState4.floatValue);
         this.floats2[this.intValue] = this.floats2[this.intValue] + (floatValue296 - this.hudConstructorScreenState4.floatValue2);
         this.invoke32(floatValue295, floatValue296);
      }
   }

   private void invoke31(float f, float g) {
      float floatValue300 = f - this.floatValue9;
      float floatValue301 = g - this.floatValue10;
      float floatValue302 = Math.max(1.0F, this.floatValue11 * this.floatValue11 + this.floatValue12 * this.floatValue12);
      float floatValue303 = 1.0F + (floatValue300 * this.floatValue11 + floatValue301 * this.floatValue12) / floatValue302;
      HudEditorRenderer.HudEditorRendererData2 hudEditorRendererData22 = HudEditorRenderer.getINSTANCE()
         .resolve6(this.getId(), this.floatValue15 * floatValue303, this.floatValue19, this.floatValue20, this.floatValue13, this.floatValue14);
      if (hudEditorRendererData22 != null) {
         float floatValue304 = this.measure2(this.hudConstructorScreenState11.floatValue3, this.hudConstructorScreenState11.floatValue4, this.floatValue13, this.floatValue14, hudEditorRendererData22.scaleX());
         float floatValue305 = floatValue304 / this.floatValue16;
         float floatValue306 = this.floatValue11 * floatValue305;
         float floatValue307 = this.floatValue12 * floatValue305;
         this.floats[this.intValue] = this.floatValue17 + (floatValue306 - this.floatValue11) * 0.5F;
         this.floats2[this.intValue] = this.floatValue18 + (floatValue307 - this.floatValue12) * 0.5F;
         this.flag7 = true;
      }
   }

   private void invoke32(float f, float g) {
      float floatValue308 = this.measure(10.0F);
      float floatValue309 = this.hudConstructorScreenState11.floatValue + floatValue308;
      float floatValue310 = this.hudConstructorScreenState11.floatValue2 + floatValue308;
      float floatValue311 = Math.max(1.0F, this.hudConstructorScreenState11.floatValue3 - floatValue308 * 2.0F - this.hudConstructorScreenState4.floatValue3);
      float floatValue312 = Math.max(1.0F, this.hudConstructorScreenState11.floatValue4 - floatValue308 * 2.0F - this.hudConstructorScreenState4.floatValue4);
      float floatValue313 = this.hudConstructorScreenState4.floatValue3 / Math.max(0.001F, this.floatValue5);
      float floatValue314 = this.hudConstructorScreenState4.floatValue4 / Math.max(0.001F, this.floatValue6);
      HudEditorRenderer.getINSTANCE()
         .resolve7(this.getId(), measure18((f - floatValue309) / floatValue311, 0.0F, 1.0F), measure18((g - floatValue310) / floatValue312, 0.0F, 1.0F), floatValue313, floatValue314);
      this.flag7 = true;
   }

   private String resolve7(float f, float g) {
      if (!this.hudConstructorScreenState10.check(f, g)) {
         return null;
      } else {
         for (Entry entry : this.valuesByKey.entrySet()) {
            if (((HudConstructorScreen.HudConstructorScreenState)entry.getValue()).check(f, g)) {
               return (String)entry.getKey();
            }
         }

         return null;
      }
   }

   private String resolve8(float f, float g) {
      if (!this.hudConstructorScreenState10.check(f, g)) {
         return null;
      } else {
         for (Entry entry2 : this.valuesByKey3.entrySet()) {
            if (!"close".equals(entry2.getKey()) && ((HudConstructorScreen.HudConstructorScreenState)entry2.getValue()).check(f, g)) {
               return (String)entry2.getKey();
            }
         }

         return null;
      }
   }

   private String resolve9(float f, float g) {
      if (!this.hudConstructorScreenState10.check(f, g)) {
         return null;
      } else {
         for (Entry entry3 : this.valuesByKey2.entrySet()) {
            if (((HudConstructorScreen.HudConstructorScreenState)entry3.getValue()).check(f, g)) {
               return (String)entry3.getKey();
            }
         }

         return null;
      }
   }

   private void invoke33(float f) {
      HudLayoutManager.HudLayoutManagerState hudLayoutManagerState13 = this.resolve13();
      HudConstructorScreen.HudConstructorScreenDisplayEntry2 hudConstructorScreenDisplayEntry25 = this.resolve12(this.text3);
      HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState16 = this.valuesByKey.get(this.text3);
      if (hudConstructorScreenDisplayEntry25 != null && hudConstructorScreenState16 != null) {
         this.invoke41(hudLayoutManagerState13, hudConstructorScreenDisplayEntry25.id, this.measure12(hudConstructorScreenState16, f, hudConstructorScreenDisplayEntry25.min, hudConstructorScreenDisplayEntry25.max));
         hudLayoutManagerState13.invoke();
         this.valuesByKey7.put(hudConstructorScreenDisplayEntry25.id, resolve18(this.measure11(hudLayoutManagerState13, hudConstructorScreenDisplayEntry25.id)));
         this.flag6 = true;
      }
   }

   private void invoke34(String string) {
      if ("reset".equals(string)) {
         this.invoke37();
      } else if ("centerX".equals(string)) {
         this.invoke35(true, false);
      } else if ("centerY".equals(string)) {
         this.invoke35(false, true);
      } else {
         HudLayoutManager.HudLayoutManagerState hudLayoutManagerState14 = this.resolve13();
         if ("presetSoft".equals(string)) {
            this.invoke38(hudLayoutManagerState14);
         } else if ("presetCompact".equals(string)) {
            this.invoke39(hudLayoutManagerState14);
         } else if ("presetSharp".equals(string)) {
            this.invoke40(hudLayoutManagerState14);
         }

         hudLayoutManagerState14.invoke();
         this.invoke46();
         this.flag6 = true;
      }
   }

   private void invoke35(boolean bl, boolean bl2) {
      if (this.resolve11() == null && !"content".equals(this.panel)) {
         this.invoke36(bl, bl2);
      } else {
         HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState17 = this.resolve10();
         if (hudConstructorScreenState17 != null && !(hudConstructorScreenState17.floatValue3 <= 0.0F) && !(this.hudConstructorScreenState4.floatValue3 <= 0.0F)) {
            HudLayoutManager.HudLayoutManagerState hudLayoutManagerState15 = this.resolve13();
            float floatValue315 = bl
               ? (this.hudConstructorScreenState4.floatValue + this.hudConstructorScreenState4.floatValue3 * 0.5F - (hudConstructorScreenState17.floatValue + hudConstructorScreenState17.floatValue3 * 0.5F))
                  / Math.max(0.001F, this.floatValue5)
               : 0.0F;
            float floatValue316 = bl2
               ? (this.hudConstructorScreenState4.floatValue2 + this.hudConstructorScreenState4.floatValue4 * 0.5F - (hudConstructorScreenState17.floatValue2 + hudConstructorScreenState17.floatValue4 * 0.5F))
                  / Math.max(0.001F, this.floatValue6)
               : 0.0F;
            if ("title".equals(this.panel)) {
               hudLayoutManagerState15.hudLayoutManagerState3.floatValue += floatValue315;
               hudLayoutManagerState15.hudLayoutManagerState3.floatValue2 += floatValue316;
            } else if ("icon".equals(this.panel)) {
               hudLayoutManagerState15.hudLayoutManagerState32.floatValue += floatValue315;
            } else if ("modules".equals(this.panel)) {
               hudLayoutManagerState15.hudLayoutManagerState33.floatValue += floatValue315;
               hudLayoutManagerState15.hudLayoutManagerState33.floatValue2 += floatValue316;
            } else if ("binds".equals(this.panel)) {
               hudLayoutManagerState15.hudLayoutManagerState34.floatValue += floatValue315;
               hudLayoutManagerState15.hudLayoutManagerState34.floatValue2 += floatValue316;
            } else if ("content".equals(this.panel)) {
               hudLayoutManagerState15.hudLayoutManagerState33.floatValue += floatValue315;
               hudLayoutManagerState15.hudLayoutManagerState34.floatValue += floatValue315;
               hudLayoutManagerState15.hudLayoutManagerState33.floatValue2 += floatValue316;
               hudLayoutManagerState15.hudLayoutManagerState34.floatValue2 += floatValue316;
            }

            hudLayoutManagerState15.invoke();
            this.invoke47();
            this.flag6 = true;
         }
      }
   }

   private void invoke36(boolean bl, boolean bl2) {
      if (!(this.hudConstructorScreenState4.floatValue3 <= 0.0F)
         && !(this.hudConstructorScreenState4.floatValue4 <= 0.0F)
         && !(this.hudConstructorScreenState11.floatValue3 <= 0.0F)
         && !(this.hudConstructorScreenState11.floatValue4 <= 0.0F)) {
         float floatValue317 = bl
            ? this.hudConstructorScreenState11.floatValue + (this.hudConstructorScreenState11.floatValue3 - this.hudConstructorScreenState4.floatValue3) * 0.5F
            : this.hudConstructorScreenState4.floatValue;
         float floatValue318 = bl2
            ? this.hudConstructorScreenState11.floatValue2 + (this.hudConstructorScreenState11.floatValue4 - this.hudConstructorScreenState4.floatValue4) * 0.5F
            : this.hudConstructorScreenState4.floatValue2;
         this.floats[this.intValue] = this.floats[this.intValue] + (floatValue317 - this.hudConstructorScreenState4.floatValue);
         this.floats2[this.intValue] = this.floats2[this.intValue] + (floatValue318 - this.hudConstructorScreenState4.floatValue2);
         this.invoke32(floatValue317, floatValue318);
      }
   }

   private void invoke37() {
      HudLayoutManager.invoke2(this.getId());
      this.panel = this.resolve3(this.resolve15().kind);
      this.floatValue7 = 0.0F;
      this.floats[this.intValue] = 0.0F;
      this.floats2[this.intValue] = 0.0F;
      this.flag6 = false;
      this.flag7 = false;
      this.invoke46();
      this.invoke47();
   }

   private HudConstructorScreen.HudConstructorScreenState resolve10() {
      String text19 = this.panel;

      return switch (text19) {
         case "header" -> this.hudConstructorScreenState5;
         case "modules" -> this.hudConstructorScreenState7;
         case "binds" -> this.DynamicButtonSetting;
         case "content" -> this.hudConstructorScreenState6;
         case "title" -> this.hudConstructorScreenState2;
         case "icon" -> this.hudConstructorScreenState3;
         case "slots" -> this.hudConstructorScreenState8;
         default -> this.hudConstructorScreenState4;
      };
   }

   private HudLayoutManager.HudLayoutManagerState3 resolve11() {
      HudLayoutManager.HudLayoutManagerState hudLayoutManagerState16 = this.resolve13();
      String text20 = this.panel;

      return switch (text20) {
         case "title" -> hudLayoutManagerState16.hudLayoutManagerState3;
         case "icon" -> hudLayoutManagerState16.hudLayoutManagerState32;
         case "modules" -> hudLayoutManagerState16.hudLayoutManagerState33;
         case "binds" -> hudLayoutManagerState16.hudLayoutManagerState34;
         default -> null;
      };
   }

   private void invoke38(HudLayoutManager.HudLayoutManagerState hudLayoutManagerState17) {
      hudLayoutManagerState17.floatValue = 17.0F;
      hudLayoutManagerState17.floatValue2 = 13.0F;
      hudLayoutManagerState17.floatValue3 = 10.0F;
      hudLayoutManagerState17.floatValue4 = 10.0F;
      hudLayoutManagerState17.floatValue5 = 10.0F;
      hudLayoutManagerState17.floatValue6 = 8.0F;
      hudLayoutManagerState17.floatValue7 = 6.0F;
      hudLayoutManagerState17.floatValue8 = 8.0F;
      hudLayoutManagerState17.floatValue9 = 6.0F;
      hudLayoutManagerState17.floatValue10 = Math.max(30.0F, hudLayoutManagerState17.floatValue10);
      hudLayoutManagerState17.floatValue11 = Math.max(22.0F, hudLayoutManagerState17.floatValue11);
      hudLayoutManagerState17.floatValue14 = 10.0F;
      hudLayoutManagerState17.floatValue15 = 2.4F;
   }

   private void invoke39(HudLayoutManager.HudLayoutManagerState hudLayoutManagerState18) {
      hudLayoutManagerState18.floatValue = 10.0F;
      hudLayoutManagerState18.floatValue2 = 8.0F;
      hudLayoutManagerState18.floatValue3 = 5.0F;
      hudLayoutManagerState18.floatValue4 = 5.0F;
      hudLayoutManagerState18.floatValue5 = 5.0F;
      hudLayoutManagerState18.floatValue6 = 4.0F;
      hudLayoutManagerState18.floatValue7 = 3.0F;
      hudLayoutManagerState18.floatValue8 = 5.0F;
      hudLayoutManagerState18.floatValue9 = 3.0F;
      hudLayoutManagerState18.floatValue10 = Math.min(28.0F, Math.max(22.0F, hudLayoutManagerState18.floatValue10));
      hudLayoutManagerState18.floatValue11 = 18.0F;
      hudLayoutManagerState18.floatValue14 = -6.0F;
      hudLayoutManagerState18.floatValue15 = 1.4F;
   }

   private void invoke40(HudLayoutManager.HudLayoutManagerState hudLayoutManagerState19) {
      hudLayoutManagerState19.floatValue = 4.0F;
      hudLayoutManagerState19.floatValue2 = 3.0F;
      hudLayoutManagerState19.floatValue3 = 2.0F;
      hudLayoutManagerState19.floatValue4 = 2.0F;
      hudLayoutManagerState19.floatValue5 = 2.0F;
      hudLayoutManagerState19.floatValue6 = 1.0F;
      hudLayoutManagerState19.floatValue7 = 1.0F;
      hudLayoutManagerState19.floatValue8 = 7.0F;
      hudLayoutManagerState19.floatValue9 = 5.0F;
      hudLayoutManagerState19.floatValue10 = 32.0F;
      hudLayoutManagerState19.floatValue11 = 22.0F;
      hudLayoutManagerState19.floatValue14 = 0.0F;
      hudLayoutManagerState19.floatValue15 = 2.0F;
   }

   private HudConstructorScreen.HudConstructorScreenDisplayEntry2 resolve12(String string) {
      for (HudConstructorScreen.HudConstructorScreenDisplayEntry2 hudConstructorScreenDisplayEntry26 : HUD_CONSTRUCTOR_SCREEN_DISPLAY_ENTRY2S) {
         if (hudConstructorScreenDisplayEntry26.id.equals(string)) {
            return hudConstructorScreenDisplayEntry26;
         }
      }

      return null;
   }

   private float measure11(HudLayoutManager.HudLayoutManagerState hudLayoutManagerState20, String string) {
      return switch (string) {
         case "panelRadius" -> hudLayoutManagerState20.floatValue;
         case "headerRadius" -> hudLayoutManagerState20.floatValue2;
         case "contentRadius" -> hudLayoutManagerState20.floatValue3;
         case "modulesRadius" -> hudLayoutManagerState20.floatValue4;
         case "bindsRadius" -> hudLayoutManagerState20.floatValue5;
         case "rowRadius" -> hudLayoutManagerState20.floatValue6;
         case "slotRadius" -> hudLayoutManagerState20.floatValue7;
         case "padding" -> hudLayoutManagerState20.floatValue8;
         case "gap" -> hudLayoutManagerState20.floatValue9;
         case "headerHeight" -> hudLayoutManagerState20.floatValue10;
         case "rowHeight" -> hudLayoutManagerState20.floatValue11;
         case "titleSize" -> hudLayoutManagerState20.floatValue12;
         case "iconSize" -> hudLayoutManagerState20.floatValue13;
         case "bindWidth" -> hudLayoutManagerState20.floatValue14;
         case "accentWidth" -> hudLayoutManagerState20.floatValue15;
         default -> 0.0F;
      };
   }

   private void invoke41(HudLayoutManager.HudLayoutManagerState hudLayoutManagerState21, String string, float f) {
      switch (string) {
         case "panelRadius":
            hudLayoutManagerState21.floatValue = f;
            break;
         case "headerRadius":
            hudLayoutManagerState21.floatValue2 = f;
            break;
         case "contentRadius":
            hudLayoutManagerState21.floatValue3 = f;
            break;
         case "modulesRadius":
            hudLayoutManagerState21.floatValue4 = f;
            break;
         case "bindsRadius":
            hudLayoutManagerState21.floatValue5 = f;
            break;
         case "rowRadius":
            hudLayoutManagerState21.floatValue6 = f;
            break;
         case "slotRadius":
            hudLayoutManagerState21.floatValue7 = f;
            break;
         case "padding":
            hudLayoutManagerState21.floatValue8 = f;
            break;
         case "gap":
            hudLayoutManagerState21.floatValue9 = f;
            break;
         case "headerHeight":
            hudLayoutManagerState21.floatValue10 = f;
            break;
         case "rowHeight":
            hudLayoutManagerState21.floatValue11 = f;
            break;
         case "titleSize":
            hudLayoutManagerState21.floatValue12 = f;
            break;
         case "iconSize":
            hudLayoutManagerState21.floatValue13 = f;
            break;
         case "bindWidth":
            hudLayoutManagerState21.floatValue14 = f;
            break;
         case "accentWidth":
            hudLayoutManagerState21.floatValue15 = f;
      }
   }

   private float measure12(HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState18, float f, float g, float h) {
      float floatValue319 = hudConstructorScreenState18.floatValue3 <= 0.0F ? 0.0F : measure18((f - hudConstructorScreenState18.floatValue) / hudConstructorScreenState18.floatValue3, 0.0F, 1.0F);
      return g + (h - g) * floatValue319;
   }

   private HudLayoutManager.HudLayoutManagerState resolve13() {
      return HudLayoutManager.resolve4(this.getId());
   }

   private HudElement resolve14() {
      String text21 = this.getId();

      return (HudElement)(switch (text21) {
         case "HUD_Inventory" -> InventoryHud.getINSTANCE();
         case "HUD_Potions" -> PotionsHud.getINSTANCE();
         case "HUD_CoolDowns" -> CooldownsHud.getINSTANCE();
         case "HUD_Info" -> InformationHud.getINSTANCE();
         case "HUD_WaterMark" -> WatermarkHud.getINSTANCE();
         case "HUD_ArrayList" -> ArrayListHud.getINSTANCE();
         case "HUD_TargetHUD" -> TargetHud.getINSTANCE();
         case "hud_armor" -> ArmorHud.getINSTANCE();
         case "HUD_HotBar" -> HotbarHud.getINSTANCE();
         case "HUD_Notifications" -> NotificationsHud.getINSTANCE();
         case "HUD_MusicPlayer" -> MusicPlayerHud.getINSTANCE();
         case "HUD_ServerHelper" -> ServerHelperHud.getINSTANCE();
         default -> KeybindHud.getINSTANCE();
      });
   }

   private HudConstructorScreen.HudConstructorScreenDisplayEntry resolve15() {
      return HUD_CONSTRUCTOR_SCREEN_DISPLAY_ENTRYS[Math.max(0, Math.min(HUD_CONSTRUCTOR_SCREEN_DISPLAY_ENTRYS.length - 1, this.intValue))];
   }

   private boolean check4(HudConstructorScreen.HudConstructorScreenDisplayEntry hudConstructorScreenDisplayEntry6) {
      if (hudConstructorScreenDisplayEntry6 == null) {
         return false;
      } else {
         try {
            return HudModule.ELEMENTS.isEnabled(hudConstructorScreenDisplayEntry6.settingName);
         } catch (Throwable exception2) {
            return false;
         }
      }
   }

   private String getId() {
      return this.resolve15().id;
   }

   private boolean check5(String string) {
      return "title".equals(string) || "icon".equals(string) || "modules".equals(string) || "binds".equals(string);
   }

   private void invoke42() {
      if (this.flag6) {
         this.flag6 = false;
         HudLayoutManager.invoke3();
      }

      if (this.flag7) {
         this.flag7 = false;
         if (WildClient.INSTANCE != null && WildClient.INSTANCE.configManager != null) {
            WildClient.INSTANCE.configManager.scheduleSave();
         }
      }
   }

   private String resolve16() {
      String text22 = this.panel;

      return switch (text22) {
         case "panel" -> "Panel";
         case "header" -> "Header";
         case "modules" -> "Modules block";
         case "binds" -> "Binds block";
         case "content" -> "Content group";
         case "icon" -> "Icon";
         case "slots" -> "Slots";
         default -> "Title";
      };
   }

   private String resolve17(String string) {
      return switch (string) {
         case "panel" -> "Panel";
         case "header" -> "Header";
         case "modules" -> "Modules";
         case "binds" -> "Binds";
         case "content" -> "Content";
         case "icon" -> "Icon";
         case "slots" -> "Slots";
         default -> "Title";
      };
   }

   private void invoke43() {
      HudLayoutManager.HudLayoutManagerState hudLayoutManagerState22 = this.resolve13();
      this.invoke44("title.x", hudLayoutManagerState22.hudLayoutManagerState3.floatValue);
      this.invoke44("title.y", hudLayoutManagerState22.hudLayoutManagerState3.floatValue2);
      this.invoke44("icon.x", hudLayoutManagerState22.hudLayoutManagerState32.floatValue);
      this.invoke44("icon.y", hudLayoutManagerState22.hudLayoutManagerState32.floatValue2);
      this.invoke44("modules.x", hudLayoutManagerState22.hudLayoutManagerState33.floatValue);
      this.invoke44("modules.y", hudLayoutManagerState22.hudLayoutManagerState33.floatValue2);
      this.invoke44("binds.x", hudLayoutManagerState22.hudLayoutManagerState34.floatValue);
      this.invoke44("binds.y", hudLayoutManagerState22.hudLayoutManagerState34.floatValue2);

      for (HudConstructorScreen.HudConstructorScreenDisplayEntry2 hudConstructorScreenDisplayEntry27 : HUD_CONSTRUCTOR_SCREEN_DISPLAY_ENTRY2S) {
         float floatValue320 = measure18((this.measure11(hudLayoutManagerState22, hudConstructorScreenDisplayEntry27.id) - hudConstructorScreenDisplayEntry27.min) / Math.max(0.001F, hudConstructorScreenDisplayEntry27.max - hudConstructorScreenDisplayEntry27.min), 0.0F, 1.0F);
         this.invoke44(hudConstructorScreenDisplayEntry27.id, floatValue320);
         this.invoke45(hudConstructorScreenDisplayEntry27.id + ".thumb");
      }

      for (HudConstructorScreen.HudConstructorScreenDisplayEntry hudConstructorScreenDisplayEntry7 : HUD_CONSTRUCTOR_SCREEN_DISPLAY_ENTRYS) {
         this.invoke45(hudConstructorScreenDisplayEntry7.id);
      }

      for (String text23 : PANEL_9) {
         this.invoke45(text23);
         Animation animation = new Animation();
         animation.invoke(0.0);
         this.valuesByKey6.put(text23, animation);
      }

      for (String text24 : this.valuesByKey3.keySet()) {
         this.invoke45(text24);
      }

      this.invoke45("preview.resize");
   }

   private void invoke44(String string, float f) {
      Animation animation2 = new Animation();
      animation2.invoke(f);
      this.valuesByKey4.put(string, animation2);
   }

   private void invoke45(String string) {
      if (!this.valuesByKey5.containsKey(string)) {
         Animation animation3 = new Animation();
         animation3.invoke(0.0);
         this.valuesByKey5.put(string, animation3);
      }
   }

   private void invoke46() {
      HudLayoutManager.HudLayoutManagerState hudLayoutManagerState23 = this.resolve13();

      for (HudConstructorScreen.HudConstructorScreenDisplayEntry2 hudConstructorScreenDisplayEntry28 : HUD_CONSTRUCTOR_SCREEN_DISPLAY_ENTRY2S) {
         this.valuesByKey7.put(hudConstructorScreenDisplayEntry28.id, resolve18(this.measure11(hudLayoutManagerState23, hudConstructorScreenDisplayEntry28.id)));
      }
   }

   private void invoke47() {
      HudLayoutManager.HudLayoutManagerState3 hudLayoutManagerState33 = this.resolve11();
      if (hudLayoutManagerState33 == null) {
         this.text4 = "";
         this.text5 = "";
         this.text6 = "";
      } else {
         this.text4 = String.format(Locale.ROOT, "X %.1f", hudLayoutManagerState33.floatValue);
         this.text5 = String.format(Locale.ROOT, "Y %.1f", hudLayoutManagerState33.floatValue2);
         this.text6 = String.format(Locale.ROOT, "X %.1f    Y locked", hudLayoutManagerState33.floatValue);
      }
   }

   private float measure13(float f, String string) {
      Animation animation4 = this.valuesByKey4.get(string);
      if (animation4 == null) {
         return f;
      } else {
         animation4.check();
         animation4.resolve4(f, this.text2 == null ? 0.18F : 0.1F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         return animation4.measure3();
      }
   }

   private float measure14(String string, float f) {
      Animation animation5 = this.valuesByKey5.get(string);
      if (animation5 == null) {
         return f;
      } else {
         animation5.check();
         animation5.resolve4(f, 0.14F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         return animation5.measure3();
      }
   }

   private float measure15(String string, float f) {
      Animation animation6 = this.valuesByKey6.get(string);
      if (animation6 == null) {
         return f;
      } else {
         animation6.check();
         animation6.resolve4(f, 0.14F, LegacyEasingFunctions.LEGACY_EASING_FUNCTION_9, false);
         return animation6.measure3();
      }
   }

   private boolean check6(HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState19) {
      return hudConstructorScreenState19 != null && hudConstructorScreenState19.check(this.floatValue, this.floatValue2);
   }

   private void invoke48(float f, float g) {
      this.floatValue = f;
      this.floatValue2 = g;
   }

   private void invoke49() {
      if (this.client != null && this.client.getWindow() != null && this.client.mouse != null) {
         double doubleValue = this.client.getWindow().getFramebufferWidth();
         double doubleValue2 = this.client.getWindow().getFramebufferHeight();
         if (!(doubleValue <= 0.0) && !(doubleValue2 <= 0.0)) {
            double doubleValue3 = this.client.mouse.getX();
            double doubleValue4 = this.client.mouse.getY();
            if (doubleValue3 >= 0.0 && doubleValue4 >= 0.0 && doubleValue3 <= doubleValue + 2.0 && doubleValue4 <= doubleValue2 + 2.0) {
               this.invoke48((float)doubleValue3, (float)doubleValue4);
            }
         }
      }
   }

   private float measure16(double d) {
      if (this.client != null && this.client.getWindow() != null) {
         int intValue49 = this.client.getWindow().getFramebufferWidth();
         int intValue50 = this.client.getWindow().getScaledWidth();
         return intValue49 > 0 && intValue50 > 0 ? (float)(d * intValue49 / Math.max(1.0, (double)intValue50)) : (float)d;
      } else {
         return (float)d;
      }
   }

   private float measure17(double d) {
      if (this.client != null && this.client.getWindow() != null) {
         int intValue51 = this.client.getWindow().getFramebufferHeight();
         int intValue52 = this.client.getWindow().getScaledHeight();
         return intValue51 > 0 && intValue52 > 0 ? (float)(d * intValue51 / Math.max(1.0, (double)intValue52)) : (float)d;
      } else {
         return (float)d;
      }
   }

   private static void invoke50(HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState20, HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState21, HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState25) {
      if (hudConstructorScreenState20 != null) {
         if (hudConstructorScreenState21 == null || hudConstructorScreenState21.floatValue3 <= 0.0F || hudConstructorScreenState21.floatValue4 <= 0.0F) {
            hudConstructorScreenState20.resolve2(hudConstructorScreenState25);
         } else if (hudConstructorScreenState25 != null && !(hudConstructorScreenState25.floatValue3 <= 0.0F) && !(hudConstructorScreenState25.floatValue4 <= 0.0F)) {
            float floatValue321 = Math.min(hudConstructorScreenState21.floatValue, hudConstructorScreenState25.floatValue);
            float floatValue322 = Math.min(hudConstructorScreenState21.floatValue2, hudConstructorScreenState25.floatValue2);
            float floatValue323 = Math.max(hudConstructorScreenState21.floatValue + hudConstructorScreenState21.floatValue3, hudConstructorScreenState25.floatValue + hudConstructorScreenState25.floatValue3);
            float floatValue324 = Math.max(hudConstructorScreenState21.floatValue2 + hudConstructorScreenState21.floatValue4, hudConstructorScreenState25.floatValue2 + hudConstructorScreenState25.floatValue4);
            hudConstructorScreenState20.resolve(floatValue321, floatValue322, floatValue323 - floatValue321, floatValue324 - floatValue322);
         } else {
            hudConstructorScreenState20.resolve2(hudConstructorScreenState21);
         }
      }
   }

   private static String resolve18(float f) {
      return String.format(Locale.ROOT, "%.1f", f);
   }

   private static float measure18(float f, float g, float h) {
      return !Float.isFinite(f) ? g : Math.max(g, Math.min(h, f));
   }

   private static int compute4(int i, int j, int k, int l) {
      return RenderManager.RenderManagerState.compute37(i, j, k, Math.max(0, Math.min(255, l)));
   }

   private static int compute5(ColorScheme colorScheme23, int i) {
      return ColorScheme.compute6(colorScheme23.getIntValue14(), Math.max(0, Math.min(255, i)));
   }

   private static int compute6(ColorScheme colorScheme24, int i) {
      return ColorScheme.compute6(colorScheme24.getIntValue15(), Math.max(0, Math.min(255, i)));
   }

   private static int compute7(ColorScheme colorScheme25) {
      return ColorScheme.compute6(colorScheme25.getIntValue13(), colorScheme25.isFlag() ? 150 : 168);
   }

   private static int compute8(ColorScheme colorScheme26) {
      return ColorScheme.compute6(colorScheme26.getIntValue13(), colorScheme26.isFlag() ? 128 : 98);
   }

   private static float measure19(float f, float g) {
      double doubleValue5 = (float)(System.currentTimeMillis() % (long)Math.max(1.0F, f)) / Math.max(1.0F, f);
      return (float)(0.5 + 0.5 * Math.sin((doubleValue5 + g) * Math.PI * 2.0));
   }

   private static float measure20(float f, float g) {
      return f + g * 0.3F;
   }

   private void invoke51(RenderManager renderManager33, FontObject fontObject5, String string, float f, float g, float h, float i, int j) {
      float floatValue325 = TextMeasureCache.measure(fontObject5, string, i);
      renderManager33.invoke69(fontObject5, Math.round(f + (h - floatValue325) * 0.5F), Math.round(measure20(g + h * 0.5F, i)), i, string, j);
   }

   private void invoke52(RenderManager renderManager34, FontObject fontObject6, String string, float f, float g, float h, float i, float j, int k) {
      float floatValue326 = TextMeasureCache.measure(fontObject6, string, j);
      renderManager34.invoke69(fontObject6, Math.round(f + (h - floatValue326) * 0.5F), Math.round(measure20(g + i * 0.5F, j)), j, string, k);
   }

   private void invoke53(RenderManager renderManager35, float f, float g, float h, float i, int j) {
      renderManager35.invoke56(f, g);
      renderManager35.invoke54(45.0F);
      renderManager35.invoke5(-h, -i * 0.5F, h * 2.0F, i, i * 0.5F, j);
      renderManager35.invoke55();
      renderManager35.invoke54(-45.0F);
      renderManager35.invoke5(-h, -i * 0.5F, h * 2.0F, i, i * 0.5F, j);
      renderManager35.invoke55();
      renderManager35.invoke57();
   }

   static final class HudConstructorScreenState {
      float floatValue;
      float floatValue2;
      float floatValue3;
      float floatValue4;

      HudConstructorScreenState() {
         this(0.0F, 0.0F, 0.0F, 0.0F);
      }

      HudConstructorScreenState(float f, float g, float h, float i) {
         this.resolve(f, g, h, i);
      }

      HudConstructorScreen.HudConstructorScreenState resolve(float f, float g, float h, float i) {
         this.floatValue = f;
         this.floatValue2 = g;
         this.floatValue3 = h;
         this.floatValue4 = i;
         return this;
      }

      HudConstructorScreen.HudConstructorScreenState resolve2(HudConstructorScreen.HudConstructorScreenState hudConstructorScreenState26) {
         return hudConstructorScreenState26 == null ? this.resolve3() : this.resolve(hudConstructorScreenState26.floatValue, hudConstructorScreenState26.floatValue2, hudConstructorScreenState26.floatValue3, hudConstructorScreenState26.floatValue4);
      }

      HudConstructorScreen.HudConstructorScreenState resolve3() {
         return this.resolve(0.0F, 0.0F, 0.0F, 0.0F);
      }

      static HudConstructorScreen.HudConstructorScreenState resolve4() {
         return new HudConstructorScreen.HudConstructorScreenState();
      }

      boolean check(float f, float g) {
         return f >= this.floatValue && g >= this.floatValue2 && f <= this.floatValue + this.floatValue3 && g <= this.floatValue2 + this.floatValue4;
      }
   }

   static enum HudConstructorScreenState2 {
      KEYBINDS,
      INVENTORY,
      POTIONS,
      COOLDOWNS,
      INFO,
      WATERMARK,
      ARRAYLIST,
      TARGET,
      SLOTS,
      HOTBAR,
      NOTIFICATION,
      MEDIA,
      SERVER;
   }

   record HudConstructorScreenDisplayEntry(String id, String label, String settingName, FontObject iconFont, String icon, HudConstructorScreen.HudConstructorScreenState2 kind, boolean layoutBacked) {
   }

   record HudConstructorScreenDisplayEntry2(String id, String label, String section, float min, float max) {
   }
}
