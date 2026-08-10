package ru.metaculture.protection;

/**
 * Brand mark glyph shown in menu / HUD / clickgui.
 * Uses Inter Semibold MSDF letter instead of the old icon-font logo.
 */
public final class BrandMark {
   public static final String GLYPH = "N";

   private BrandMark() {
   }

   public static FontObject font() {
      return FontRegistry.fontObject4 != null ? FontRegistry.fontObject4 : FontRegistry.fontObject;
   }
}
