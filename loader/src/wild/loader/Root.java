package wild.loader;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.RadialGradientPaint;
import java.awt.geom.Point2D;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 * The window body: drop shadow, rounded card, and a slow aurora that keeps the surface alive.
 * The aurora is rendered into a quarter sized buffer and scaled up, which costs almost nothing
 * and blurs the gradients for free.
 */
final class Root extends JPanel {
   static final int MARGIN = 22;
   static final double RADIUS = 18.0;
   private static final int DOWNSCALE = 4;
   private static final Color BLOB_A = Theme.ACCENT;
   private static final Color BLOB_B = Theme.ACCENT_2;
   private static final Color BLOB_C = new Color(0xFF, 0x6B, 0xD6);
   private BufferedImage buffer;
   private int frame;

   Root() {
      this.setOpaque(false);
      Anim.tick(() -> {
         if (++this.frame % 2 == 0 && this.isShowing()) {
            this.repaint();
         }
      });
   }

   RoundRectangle2D body() {
      return Theme.round(MARGIN, MARGIN, this.getWidth() - MARGIN * 2.0, this.getHeight() - MARGIN * 2.0, RADIUS);
   }

   @Override
   protected void paintComponent(Graphics g) {
      Graphics2D g2 = Theme.hq(g);
      double x = MARGIN;
      double y = MARGIN;
      double width = this.getWidth() - MARGIN * 2.0;
      double height = this.getHeight() - MARGIN * 2.0;
      Theme.shadow(g2, x, y, width, height, RADIUS, MARGIN - 2, 1.0);
      Theme.fill(g2, x, y, width, height, RADIUS, Theme.BG);
      Graphics2D inner = (Graphics2D)g2.create();
      inner.clip(Theme.round(x, y, width, height, RADIUS));
      this.paintAurora(inner, (int)width, (int)height);
      this.paintVignette(inner, x, y, width, height);
      inner.dispose();
      Theme.stroke(g2, x, y, width, height, RADIUS, Theme.alpha(Theme.WHITE, 0.075), 1.0);
      Theme.fill(g2, x + RADIUS, y, width - RADIUS * 2.0, 1.0, 0.5, Theme.alpha(Theme.WHITE, 0.14));
      g2.dispose();
   }

   private void paintAurora(Graphics2D g2, int width, int height) {
      int bufferWidth = Math.max(1, width / DOWNSCALE);
      int bufferHeight = Math.max(1, height / DOWNSCALE);
      if (this.buffer == null || this.buffer.getWidth() != bufferWidth || this.buffer.getHeight() != bufferHeight) {
         this.buffer = new BufferedImage(bufferWidth, bufferHeight, BufferedImage.TYPE_INT_ARGB);
      }

      Graphics2D blobs = Theme.hq(this.buffer.createGraphics());
      blobs.setComposite(java.awt.AlphaComposite.Clear);
      blobs.fillRect(0, 0, bufferWidth, bufferHeight);
      blobs.setComposite(java.awt.AlphaComposite.SrcOver);
      double t = Anim.time();
      this.blob(blobs, bufferWidth * (0.20 + 0.10 * Math.sin(t * 0.11)), bufferHeight * (0.16 + 0.10 * Math.cos(t * 0.09)), bufferWidth * 0.62, BLOB_A, 0.30);
      this.blob(blobs, bufferWidth * (0.86 + 0.09 * Math.sin(t * 0.07 + 2.1)), bufferHeight * (0.78 + 0.10 * Math.cos(t * 0.083 + 1.2)), bufferWidth * 0.55, BLOB_B, 0.18);
      this.blob(blobs, bufferWidth * (0.62 + 0.14 * Math.cos(t * 0.055 + 0.7)), bufferHeight * (0.30 + 0.13 * Math.sin(t * 0.065 + 3.0)), bufferWidth * 0.42, BLOB_C, 0.13);
      blobs.dispose();
      g2.drawImage(this.buffer, MARGIN, MARGIN, width, height, null);
   }

   private void blob(Graphics2D g2, double centerX, double centerY, double radius, Color color, double strength) {
      float r = (float)Math.max(4.0, radius);
      g2.setPaint(
         new RadialGradientPaint(
            new Point2D.Double(centerX, centerY),
            r,
            new float[]{0.0F, 0.45F, 1.0F},
            new Color[]{Theme.alpha(color, strength), Theme.alpha(color, strength * 0.35), Theme.alpha(color, 0.0)},
            CycleMethod.NO_CYCLE
         )
      );
      g2.fill(new java.awt.geom.Ellipse2D.Double(centerX - r, centerY - r, r * 2.0, r * 2.0));
   }

   private void paintVignette(Graphics2D g2, double x, double y, double width, double height) {
      float radius = (float)Math.max(width, height) * 0.78F;
      g2.setPaint(
         new RadialGradientPaint(
            new Point2D.Double(x + width / 2.0, y + height * 0.42),
            radius,
            new float[]{0.0F, 0.58F, 1.0F},
            new Color[]{Theme.alpha(Color.BLACK, 0.0), Theme.alpha(Color.BLACK, 0.12), Theme.alpha(Color.BLACK, 0.42)},
            CycleMethod.NO_CYCLE
         )
      );
      g2.fill(Theme.round(x, y, width, height, RADIUS));
   }
}
