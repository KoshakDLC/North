package ru.metaculture.protection;

public enum ShaderSurface {
   PREVIEW_ONLY("preview", "Preview", "live editor preview", false, false, false, false, false),
   HUD("hud", "HUD", "screen-space HUD shader", false, true, false, false, true),
   MODULE_CARD("module_card", "Module Card", "module row surface and hover body", false, true, false, false, true),
   PANEL_BACKGROUND("panel_background", "Panel Background", "dock and settings panel surface", true, false, false, false, true),
   AUDIT_PANEL("audit_panel", "Audit Panel", "verification and diagnostics panel surface", true, true, false, false, false),
   BUTTON("button", "Button", "interactive button surface", false, true, false, false, true),
   HEALTH_BAR("health_bar", "Health Bar", "bar fill and shield style shader", false, true, false, false, true),
   ESP("esp", "ESP", "entity silhouette shader", false, false, true, false, true),
   CHAMS("chams", "Chams", "model-space entity material overlay", false, false, true, true, true),
   SKY("sky", "Sky", "world sky and atmospheric pass", false, false, false, false, true),
   NAMETAG("nametag", "Nametag", "billboard nametag surface", false, true, false, true, true),
   TRAILS("trails", "Trails", "motion trail ribbon material", false, false, true, true, true),
   BACKGROUND("background", "Background", "full-screen interface background", true, false, false, false, true),
   MENU_BACKGROUND("menu_bg", "Menu Background", "legacy full ClickGUI background", true, false, false, false, false),
   MENU_PANEL_BG("menu_panel", "Panel Background", "legacy panel surface", true, false, false, false, false),
   HUD_OVERLAY("hud_overlay", "HUD Overlay", "legacy HUD overlay", false, true, false, false, false),
   ESP_OVERLAY("esp_overlay", "ESP Overlay", "legacy ESP fill", false, false, true, false, false),
   ENTITY_HIGHLIGHT("entity_highlight", "Entity Highlight", "legacy entity highlight", false, false, false, true, false);

   private final String text;
   private final String text2;
   private final String text3;
   private final boolean flag;
   private final boolean flag2;
   private final boolean flag3;
   private final boolean flag4;
   private final boolean flag5;

   private ShaderSurface(String string2, String string3, String string4, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5) {
      this.text = string2;
      this.text2 = string3;
      this.text3 = string4;
      this.flag = bl;
      this.flag2 = bl2;
      this.flag3 = bl3;
      this.flag4 = bl4;
      this.flag5 = bl5;
   }

   public String getText() {
      return this.text;
   }

   public String getText2() {
      return this.text2;
   }

   public String getText3() {
      return this.text3;
   }

   public ShaderSurface resolve() {
      return switch (this) {
         case PREVIEW_ONLY -> PREVIEW_ONLY;
         case HUD, MODULE_CARD, PANEL_BACKGROUND, AUDIT_PANEL, BUTTON, HEALTH_BAR, NAMETAG, MENU_PANEL_BG, HUD_OVERLAY -> HUD;
         case ESP, CHAMS, TRAILS, ESP_OVERLAY, ENTITY_HIGHLIGHT -> ESP;
         case SKY, BACKGROUND, MENU_BACKGROUND -> BACKGROUND;
      };
   }

   public String resolve2() {
      return switch (this) {
         case PREVIEW_ONLY -> "System";
         case HUD, MODULE_CARD, BUTTON, HEALTH_BAR, HUD_OVERLAY -> "HUD";
         case PANEL_BACKGROUND, AUDIT_PANEL, BACKGROUND, MENU_BACKGROUND, MENU_PANEL_BG -> "Interface";
         case ESP, CHAMS, NAMETAG, TRAILS, ESP_OVERLAY, ENTITY_HIGHLIGHT -> "Entity";
         case SKY -> "World";
      };
   }

   public boolean check() {
      return this.resolve() == HUD || this == PANEL_BACKGROUND || this == AUDIT_PANEL || this == MENU_PANEL_BG;
   }

   public boolean isFlag5() {
      return this.flag5;
   }

   public boolean check2() {
      return this.resolve() == ESP;
   }

   public boolean check3() {
      return this.resolve() == BACKGROUND && (this == BACKGROUND || this == MENU_BACKGROUND);
   }

   public boolean check4() {
      return this.flag && (this == MENU_PANEL_BG || this == PANEL_BACKGROUND || this == AUDIT_PANEL);
   }

   public boolean check5() {
      return this.resolve() == HUD;
   }

   public boolean check6() {
      return this.resolve() == ESP && !this.flag4;
   }

   public boolean isFlag4() {
      return this.flag4;
   }

   public static ShaderSurface[] resolve3() {
      return new ShaderSurface[]{HUD, BACKGROUND, ESP};
   }

   public static ShaderSurface resolve4(String string) {
      if (string == null) {
         return PREVIEW_ONLY;
      } else {
         String text = string.trim();

         for (ShaderSurface shaderSurface : values()) {
            if (shaderSurface.text.equals(text) || shaderSurface.name().equalsIgnoreCase(text)) {
               return shaderSurface;
            }
         }

         return PREVIEW_ONLY;
      }
   }
}
