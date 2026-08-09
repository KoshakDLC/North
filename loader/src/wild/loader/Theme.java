package wild.loader;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Paint;
import java.awt.RenderingHints;
import java.awt.BasicStroke;
import java.awt.geom.RoundRectangle2D;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/** Colors, fonts and the Java2D helpers every widget in the loader paints with. */
final class Theme {
   static final Color BG = new Color(0x0B, 0x0B, 0x10);
   static final Color SURFACE = new Color(0x14, 0x14, 0x1D);
   static final Color TEXT = new Color(0xF3, 0xF3, 0xF8);
   static final Color MUTED = new Color(0x8C, 0x8C, 0xA3);
   static final Color FAINT = new Color(0x5E, 0x5E, 0x74);
   static final Color ACCENT = new Color(0xA2, 0x86, 0xFF);
   static final Color ACCENT_2 = new Color(0x6F, 0xE3, 0xFF);
   static final Color OK = new Color(0x5B, 0xE3, 0x9B);
   static final Color WARN = new Color(0xFF, 0xC4, 0x6B);
   static final Color BAD = new Color(0xFF, 0x6B, 0x7A);
   static final Color WHITE = Color.WHITE;

   private static final String[] CANDIDATES = {"Inter", "Segoe UI Variable Display", "Segoe UI", "Helvetica Neue", "SansSerif"};
   private static final String[] MONO_CANDIDATES = {"JetBrains Mono", "Cascadia Mono", "Consolas", "Monospaced"};
   private static String family;
   private static String monoFamily;

   private Theme() {
   }

   static String family() {
      if (family == null) {
         family = pick(CANDIDATES);
      }

      return family;
   }

   static String monoFamily() {
      if (monoFamily == null) {
         monoFamily = pick(MONO_CANDIDATES);
      }

      return monoFamily;
   }

   private static String pick(String[] candidates) {
      Set<String> installed = new HashSet<>(Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames()));

      for (String candidate : candidates) {
         if (installed.contains(candidate)) {
            return candidate;
         }
      }

      return candidates[candidates.length - 1];
   }

   static Font font(int style, float size) {
      return new Font(family(), style, 12).deriveFont(size);
   }

   static Font mono(float size) {
      return new Font(monoFamily(), Font.PLAIN, 12).deriveFont(size);
   }

   static Graphics2D hq(Graphics g) {
      Graphics2D g2 = (Graphics2D)g.create();
      g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
      g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
      g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
      return g2;
   }

   static Color alpha(Color color, double value) {
      int a = (int)Math.round(clamp(value, 0.0, 1.0) * 255.0);
      return new Color(color.getRed(), color.getGreen(), color.getBlue(), a);
   }

   static Color mix(Color from, Color to, double t) {
      double k = clamp(t, 0.0, 1.0);
      return new Color(
         (int)Math.round(from.getRed() + (to.getRed() - from.getRed()) * k),
         (int)Math.round(from.getGreen() + (to.getGreen() - from.getGreen()) * k),
         (int)Math.round(from.getBlue() + (to.getBlue() - from.getBlue()) * k),
         (int)Math.round(from.getAlpha() + (to.getAlpha() - from.getAlpha()) * k)
      );
   }

   static Paint vertical(double y, double height, Color top, Color bottom) {
      float span = (float)Math.max(1.0, height);
      return new GradientPaint(0.0F, (float)y, top, 0.0F, (float)y + span, bottom);
   }

   static Paint horizontal(double x, double width, Color left, Color right) {
      float span = (float)Math.max(1.0, width);
      return new GradientPaint((float)x, 0.0F, left, (float)x + span, 0.0F, right);
   }

   /** Brand gradient used for the primary button, the logo and the progress fill. */
   static Paint brand(double x, double width) {
      return horizontal(x, width, ACCENT, mix(ACCENT_2, ACCENT, 0.35));
   }

   static RoundRectangle2D round(double x, double y, double width, double height, double radius) {
      return new RoundRectangle2D.Double(x, y, Math.max(0.0, width), Math.max(0.0, height), radius * 2.0, radius * 2.0);
   }

   static void fill(Graphics2D g, double x, double y, double width, double height, double radius, Paint paint) {
      g.setPaint(paint);
      g.fill(round(x, y, width, height, radius));
   }

   static void stroke(Graphics2D g, double x, double y, double width, double height, double radius, Paint paint, double thickness) {
      g.setPaint(paint);
      g.setStroke(new BasicStroke((float)thickness));
      double inset = thickness / 2.0;
      g.draw(round(x + inset, y + inset, width - thickness, height - thickness, Math.max(0.0, radius - inset)));
   }

   /** Frosted surface: translucent fill plus the hairline highlight that sells the glass look. */
   static void glass(Graphics2D g, double x, double y, double width, double height, double radius, double strength) {
      fill(g, x, y, width, height, radius, vertical(y, height, alpha(WHITE, 0.075 * strength), alpha(WHITE, 0.028 * strength)));
      stroke(g, x, y, width, height, radius, alpha(WHITE, 0.09 * strength), 1.0);
      Graphics2D clip = (Graphics2D)g.create();
      clip.clip(round(x, y, width, height, radius));
      fill(clip, x + radius * 0.5, y, width - radius, 1.0, 0.5, alpha(WHITE, 0.16 * strength));
      clip.dispose();
   }

   /** Layered soft shadow. Cheap enough to run every frame at this window size. */
   static void shadow(Graphics2D g, double x, double y, double width, double height, double radius, int spread, double strength) {
      for (int i = spread; i > 0; i--) {
         double t = (double)i / spread;
         double a = 0.055 * strength * (1.0 - t) * (1.0 - t);
         if (a > 0.002) {
            fill(g, x - i, y - i * 0.55 + spread * 0.22, width + i * 2.0, height + i * 2.0, radius + i, alpha(Color.BLACK, a));
         }
      }
   }

   static void glow(Graphics2D g, double x, double y, double width, double height, double radius, Color color, int spread, double strength) {
      for (int i = spread; i > 0; i--) {
         double t = (double)i / spread;
         double a = 0.14 * strength * (1.0 - t) * (1.0 - t);
         if (a > 0.002) {
            fill(g, x - i, y - i, width + i * 2.0, height + i * 2.0, radius + i, alpha(color, a));
         }
      }
   }

   static void text(Graphics2D g, String value, double x, double baseline, Font font, Color color) {
      g.setFont(font);
      g.setColor(color);
      g.drawString(value, (float)x, (float)baseline);
   }

   static void textCentered(Graphics2D g, String value, double centerX, double baseline, Font font, Color color) {
      g.setFont(font);
      g.setColor(color);
      g.drawString(value, (float)(centerX - width(g, value, font) / 2.0), (float)baseline);
   }

   static double width(Graphics2D g, String value, Font font) {
      return g.getFontMetrics(font).stringWidth(value);
   }

   /** Baseline that vertically centers a line of text inside the given box. */
   static double baseline(Graphics2D g, double y, double height, Font font) {
      FontMetrics metrics = g.getFontMetrics(font);
      return y + (height - metrics.getHeight()) / 2.0 + metrics.getAscent();
   }

   static String ellipsize(Graphics2D g, String value, Font font, double maxWidth) {
      if (value == null || value.isEmpty() || width(g, value, font) <= maxWidth) {
         return value == null ? "" : value;
      } else {
         String result = value;

         while (result.length() > 1 && width(g, result + "…", font) > maxWidth) {
            result = result.substring(0, result.length() - 1);
         }

         return result + "…";
      }
   }

   static double clamp(double value, double min, double max) {
      return value < min ? min : Math.min(value, max);
   }

   static double easeOut(double t) {
      double k = 1.0 - clamp(t, 0.0, 1.0);
      return 1.0 - k * k * k;
   }
}
