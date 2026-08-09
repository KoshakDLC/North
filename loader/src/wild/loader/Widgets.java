package wild.loader;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Paint;
import java.awt.BasicStroke;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Path2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

/** Hand painted controls. Every one of them animates its hover and press state. */
final class Widgets {
   private Widgets() {
   }

   /** Attaches hover/press tracking plus a click action to a component. */
   private static void interactive(JComponent component, Anim.Val hover, Anim.Val press, Runnable[] action, boolean[] enabled) {
      component.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
      component.addMouseListener(new MouseAdapter() {
         @Override
         public void mouseEntered(MouseEvent event) {
            hover.to(enabled[0] ? 1.0 : 0.0);
         }

         @Override
         public void mouseExited(MouseEvent event) {
            hover.to(0.0);
            press.to(0.0);
         }

         @Override
         public void mousePressed(MouseEvent event) {
            if (enabled[0] && SwingUtilities.isLeftMouseButton(event)) {
               press.to(1.0);
            }
         }

         @Override
         public void mouseReleased(MouseEvent event) {
            press.to(0.0);
            boolean inside = event.getX() >= 0 && event.getY() >= 0 && event.getX() <= component.getWidth() && event.getY() <= component.getHeight();
            if (enabled[0] && inside && SwingUtilities.isLeftMouseButton(event) && action[0] != null) {
               action[0].run();
            }
         }
      });
   }

   /** Filled brand button (primary) or translucent outline button (ghost). */
   static final class Btn extends JComponent {
      private final Anim.Val hover;
      private final Anim.Val press;
      private final Anim.Val fade;
      private final Runnable[] action = new Runnable[1];
      private final boolean[] enabled = {true};
      private final boolean primary;
      private String text;
      private boolean busy;

      Btn(String text, boolean primary, Runnable action) {
         this.text = text;
         this.primary = primary;
         this.action[0] = action;
         this.hover = Anim.attach(this, 0.0, 0.18);
         this.press = Anim.attach(this, 0.0, 0.32);
         this.fade = Anim.attach(this, 1.0, 0.16);
         interactive(this, this.hover, this.press, this.action, this.enabled);
         Anim.tick(() -> {
            if (this.busy) {
               this.repaint();
            }
         });
      }

      void setText(String value) {
         this.text = value;
         this.repaint();
      }

      void setEnabledState(boolean value) {
         this.enabled[0] = value;
         this.fade.to(value ? 1.0 : 0.42);
         this.setCursor(Cursor.getPredefinedCursor(value ? Cursor.HAND_CURSOR : Cursor.DEFAULT_CURSOR));
         if (!value) {
            this.hover.to(0.0);
         }
      }

      void setBusy(boolean value) {
         this.busy = value;
         this.repaint();
      }

      @Override
      protected void paintComponent(Graphics g) {
         Graphics2D g2 = Theme.hq(g);
         double lift = this.press.get() * 1.5;
         double x = 0.0;
         double y = lift;
         double width = this.getWidth();
         double height = this.getHeight() - lift;
         double radius = height / 2.0;
         double fade = this.fade.get();
         double hover = this.hover.get() * fade;
         if (this.primary) {
            Theme.glow(g2, x + 6.0, y + 4.0, width - 12.0, height, radius, Theme.ACCENT, 14, (0.35 + 0.65 * hover) * fade);
            Paint paint = Theme.horizontal(x, width, Theme.mix(Theme.ACCENT, Theme.WHITE, 0.06 * hover), Theme.mix(Theme.ACCENT_2, Theme.ACCENT, 0.30 - 0.20 * hover));
            Theme.fill(g2, x, y, width, height, radius, paint);
            Theme.fill(g2, x + radius * 0.6, y + 1.0, width - radius * 1.2, height * 0.42, radius * 0.5, Theme.alpha(Theme.WHITE, 0.16 * fade));
            Theme.stroke(g2, x, y, width, height, radius, Theme.alpha(Theme.WHITE, 0.22 * fade), 1.0);
         } else {
            Theme.fill(g2, x, y, width, height, radius, Theme.alpha(Theme.WHITE, (0.05 + 0.05 * hover) * fade));
            Theme.stroke(g2, x, y, width, height, radius, Theme.alpha(Theme.WHITE, (0.10 + 0.10 * hover) * fade), 1.0);
         }

         Font font = Theme.font(Font.BOLD, this.primary ? 14.5F : 13.0F);
         Color color = this.primary ? Theme.alpha(new Color(0x14, 0x10, 0x24), fade) : Theme.alpha(Theme.mix(Theme.MUTED, Theme.TEXT, 0.35 + 0.65 * hover), fade);
         double textCenter = x + width / 2.0;
         if (this.busy) {
            double size = height * 0.34;
            double spinnerX = textCenter - Theme.width(g2, this.text, font) / 2.0 - size - 9.0;
            this.spinner(g2, spinnerX, y + height / 2.0 - size / 2.0, size, color);
            textCenter += size * 0.5 + 4.5;
         }

         Theme.textCentered(g2, this.text, textCenter, Theme.baseline(g2, y, height, font), font, color);
         g2.dispose();
      }

      private void spinner(Graphics2D g2, double x, double y, double size, Color color) {
         double angle = Anim.time() * 300.0 % 360.0;
         g2.setStroke(new BasicStroke((float)Math.max(1.6, size * 0.16), BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
         g2.setColor(Theme.alpha(color, 0.28));
         g2.draw(new Ellipse2D.Double(x, y, size, size));
         g2.setColor(color);
         g2.draw(new Arc2D.Double(x, y, size, size, -angle, 105.0, Arc2D.OPEN));
      }
   }

   /** Minimize / close controls in the title bar. */
   static final class IconBtn extends JComponent {
      private final Anim.Val hover;
      private final Anim.Val press;
      private final Runnable[] action = new Runnable[1];
      private final boolean[] enabled = {true};
      private final boolean close;

      IconBtn(boolean close, Runnable action) {
         this.close = close;
         this.action[0] = action;
         this.hover = Anim.attach(this, 0.0, 0.2);
         this.press = Anim.attach(this, 0.0, 0.3);
         interactive(this, this.hover, this.press, this.action, this.enabled);
      }

      @Override
      protected void paintComponent(Graphics g) {
         Graphics2D g2 = Theme.hq(g);
         double size = Math.min(this.getWidth(), this.getHeight());
         double hover = this.hover.get();
         Color tint = this.close ? Theme.BAD : Theme.WHITE;
         Theme.fill(g2, 0.0, 0.0, size, size, size / 2.0, Theme.alpha(tint, (0.10 + 0.06 * this.press.get()) * hover));
         double inset = size * 0.34;
         g2.setStroke(new BasicStroke(1.5F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
         g2.setColor(Theme.mix(Theme.FAINT, this.close ? Theme.mix(Theme.BAD, Theme.WHITE, 0.4) : Theme.TEXT, 0.25 + 0.75 * hover));
         if (this.close) {
            g2.draw(new Line2D.Double(inset, inset, size - inset, size - inset));
            g2.draw(new Line2D.Double(size - inset, inset, inset, size - inset));
         } else {
            g2.draw(new Line2D.Double(inset, size / 2.0, size - inset, size / 2.0));
         }

         g2.dispose();
      }
   }

   enum Icon {
      PLAY,
      GEAR,
      INFO
   }

   /** Sidebar entry with an animated active pill. */
   static final class Nav extends JComponent {
      private final Anim.Val hover;
      private final Anim.Val press;
      private final Anim.Val active;
      private final Runnable[] action = new Runnable[1];
      private final boolean[] enabled = {true};
      private final String label;
      private final Widgets.Icon icon;

      Nav(String label, Widgets.Icon icon, Runnable action) {
         this.label = label;
         this.icon = icon;
         this.action[0] = action;
         this.hover = Anim.attach(this, 0.0, 0.2);
         this.press = Anim.attach(this, 0.0, 0.3);
         this.active = Anim.attach(this, 0.0, 0.2);
         interactive(this, this.hover, this.press, this.action, this.enabled);
      }

      void setActive(boolean value) {
         this.active.to(value ? 1.0 : 0.0);
      }

      @Override
      protected void paintComponent(Graphics g) {
         Graphics2D g2 = Theme.hq(g);
         double width = this.getWidth();
         double height = this.getHeight();
         double active = this.active.get();
         double hover = this.hover.get() * (1.0 - active * 0.6);
         Theme.fill(g2, 0.0, 0.0, width, height, 11.0, Theme.alpha(Theme.WHITE, 0.045 * hover + 0.07 * active));
         if (active > 0.01) {
            Theme.stroke(g2, 0.0, 0.0, width, height, 11.0, Theme.alpha(Theme.WHITE, 0.09 * active), 1.0);
            double barHeight = height * 0.44 * active;
            Theme.fill(g2, 1.5, (height - barHeight) / 2.0, 2.5, barHeight, 1.25, Theme.alpha(Theme.ACCENT, active));
         }

         Color tint = Theme.mix(Theme.MUTED, Theme.TEXT, Math.max(active, hover * 0.6));
         this.paintIcon(g2, 18.0, height / 2.0, 7.0, Theme.mix(tint, Theme.ACCENT, active));
         Font font = Theme.font(active > 0.5 ? Font.BOLD : Font.PLAIN, 13.0F);
         Theme.text(g2, this.label, 38.0, Theme.baseline(g2, 0.0, height, font), font, tint);
         g2.dispose();
      }

      private void paintIcon(Graphics2D g2, double centerX, double centerY, double radius, Color color) {
         g2.setColor(color);
         g2.setStroke(new BasicStroke(1.5F, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
         switch (this.icon) {
            case PLAY -> {
               Path2D.Double path = new Path2D.Double();
               path.moveTo(centerX - radius * 0.55, centerY - radius);
               path.lineTo(centerX + radius * 0.85, centerY);
               path.lineTo(centerX - radius * 0.55, centerY + radius);
               path.closePath();
               g2.fill(path);
            }
            case GEAR -> {
               g2.draw(new Ellipse2D.Double(centerX - radius * 0.68, centerY - radius * 0.68, radius * 1.36, radius * 1.36));

               for (int i = 0; i < 6; i++) {
                  double angle = Math.PI * 2.0 * i / 6.0;
                  double from = radius * 0.92;
                  double to = radius * 1.2;
                  g2.draw(
                     new Line2D.Double(
                        centerX + Math.cos(angle) * from, centerY + Math.sin(angle) * from, centerX + Math.cos(angle) * to, centerY + Math.sin(angle) * to
                     )
                  );
               }
            }
            case INFO -> {
               g2.draw(new Ellipse2D.Double(centerX - radius, centerY - radius, radius * 2.0, radius * 2.0));
               g2.draw(new Line2D.Double(centerX, centerY - radius * 0.15, centerX, centerY + radius * 0.5));
               g2.fill(new Ellipse2D.Double(centerX - 0.9, centerY - radius * 0.62, 1.8, 1.8));
            }
         }
      }
   }

   /** Labelled text input with an animated focus ring and an optional trailing action. */
   static final class Field extends JComponent {
      private final Anim.Val focus;
      private final Anim.Val trailingHover;
      private final JTextField input = new JTextField();
      private final String label;
      private final String placeholder;
      private String trailing;
      private Runnable onTrailing;

      Field(String label, String placeholder, boolean secret) {
         this.label = label;
         this.placeholder = placeholder;
         this.focus = Anim.attach(this, 0.0, 0.2);
         this.trailingHover = Anim.attach(this, 0.0, 0.2);
         this.setLayout(null);
         this.input.setOpaque(false);
         this.input.setBorder(null);
         this.input.setForeground(Theme.TEXT);
         this.input.setCaretColor(Theme.ACCENT);
         this.input.setSelectionColor(Theme.alpha(Theme.ACCENT, 0.35));
         this.input.setSelectedTextColor(Theme.WHITE);
         this.input.setFont(secret ? Theme.mono(13.5F) : Theme.font(Font.PLAIN, 13.5F));
         this.input.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent event) {
               Field.this.focus.to(1.0);
            }

            @Override
            public void focusLost(FocusEvent event) {
               Field.this.focus.to(0.0);
            }
         });
         this.input.getDocument().addDocumentListener(new SimpleDocumentListener(this::repaint));
         this.add(this.input);
         this.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent event) {
               if (Field.this.hitTrailing(event.getX(), event.getY())) {
                  if (Field.this.onTrailing != null) {
                     Field.this.onTrailing.run();
                  }
               } else {
                  Field.this.input.requestFocusInWindow();
               }
            }

            @Override
            public void mouseExited(MouseEvent event) {
               Field.this.trailingHover.to(0.0);
            }
         });
         this.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent event) {
               boolean over = Field.this.hitTrailing(event.getX(), event.getY());
               Field.this.trailingHover.to(over ? 1.0 : 0.0);
               Field.this.setCursor(Cursor.getPredefinedCursor(over ? Cursor.HAND_CURSOR : Cursor.TEXT_CURSOR));
            }
         });
         this.setCursor(Cursor.getPredefinedCursor(Cursor.TEXT_CURSOR));
      }

      void setTrailing(String text, Runnable action) {
         this.trailing = text;
         this.onTrailing = action;
         this.revalidate();
         this.repaint();
      }

      JTextField input() {
         return this.input;
      }

      String value() {
         return this.input.getText().trim();
      }

      void value(String text) {
         this.input.setText(text == null ? "" : text);
      }

      private double boxTop() {
         return this.label == null || this.label.isEmpty() ? 0.0 : 20.0;
      }

      private double trailingWidth() {
         return this.trailing == null ? 0.0 : 78.0;
      }

      private boolean hitTrailing(int x, int y) {
         return this.trailing != null && x >= this.getWidth() - this.trailingWidth() - 6.0 && y >= this.boxTop();
      }

      @Override
      public void doLayout() {
         double top = this.boxTop();
         int height = (int)(this.getHeight() - top);
         this.input.setBounds(15, (int)top + (height - 20) / 2, (int)(this.getWidth() - 30 - this.trailingWidth()), 20);
      }

      @Override
      protected void paintComponent(Graphics g) {
         Graphics2D g2 = Theme.hq(g);
         double top = this.boxTop();
         double width = this.getWidth();
         double height = this.getHeight() - top;
         double focus = this.focus.get();
         if (top > 0.0) {
            Font labelFont = Theme.font(Font.BOLD, 10.5F);
            Theme.text(g2, this.label.toUpperCase(), 2.0, 11.0, labelFont, Theme.mix(Theme.FAINT, Theme.MUTED, 0.4 + 0.6 * focus));
         }

         Theme.fill(g2, 0.0, top, width, height, 11.0, Theme.alpha(Color.BLACK, 0.22));
         Theme.fill(g2, 0.0, top, width, height, 11.0, Theme.alpha(Theme.WHITE, 0.035 + 0.02 * focus));
         if (focus > 0.01) {
            Theme.glow(g2, 0.0, top, width, height, 11.0, Theme.ACCENT, 7, focus * 0.75);
         }

         Theme.stroke(g2, 0.0, top, width, height, 11.0, Theme.mix(Theme.alpha(Theme.WHITE, 0.09), Theme.alpha(Theme.ACCENT, 0.85), focus), 1.0);
         if (this.input.getText().isEmpty() && this.placeholder != null) {
            Font font = this.input.getFont();
            Theme.text(g2, this.placeholder, 15.0, Theme.baseline(g2, top, height, font), font, Theme.alpha(Theme.FAINT, 0.9));
         }

         if (this.trailing != null) {
            double buttonWidth = this.trailingWidth() - 12.0;
            double buttonX = width - buttonWidth - 6.0;
            double buttonY = top + 6.0;
            double buttonHeight = height - 12.0;
            double hover = this.trailingHover.get();
            Theme.fill(g2, buttonX, buttonY, buttonWidth, buttonHeight, 8.0, Theme.alpha(Theme.WHITE, 0.06 + 0.06 * hover));
            Theme.stroke(g2, buttonX, buttonY, buttonWidth, buttonHeight, 8.0, Theme.alpha(Theme.WHITE, 0.08 + 0.08 * hover), 1.0);
            Font font = Theme.font(Font.BOLD, 11.5F);
            Theme.textCentered(
               g2, this.trailing, buttonX + buttonWidth / 2.0, Theme.baseline(g2, buttonY, buttonHeight, font), font, Theme.mix(Theme.MUTED, Theme.TEXT, hover)
            );
         }

         g2.dispose();
      }
   }

   /** Horizontal slider used for the memory allocation. */
   static final class Slider extends JComponent {
      private final Anim.Val hover;
      private final Anim.Val position;
      private final String label;
      private final String suffix;
      private final int min;
      private final int max;
      private final int step;
      private int value;
      private Runnable onChange;

      Slider(String label, String suffix, int min, int max, int step, int value) {
         this.label = label;
         this.suffix = suffix;
         this.min = min;
         this.max = max;
         this.step = step;
         this.value = value;
         this.hover = Anim.attach(this, 0.0, 0.2);
         this.position = Anim.attach(this, this.fraction(), 0.25);
         this.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
         MouseAdapter adapter = new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent event) {
               Slider.this.hover.to(1.0);
            }

            @Override
            public void mouseExited(MouseEvent event) {
               Slider.this.hover.to(0.0);
            }

            @Override
            public void mousePressed(MouseEvent event) {
               Slider.this.pick(event.getX());
            }

            @Override
            public void mouseDragged(MouseEvent event) {
               Slider.this.pick(event.getX());
            }
         };
         this.addMouseListener(adapter);
         this.addMouseMotionListener(adapter);
      }

      void onChange(Runnable runnable) {
         this.onChange = runnable;
      }

      int value() {
         return this.value;
      }

      private double fraction() {
         return (double)(this.value - this.min) / Math.max(1, this.max - this.min);
      }

      private void pick(int x) {
         double raw = Theme.clamp((x - 2.0) / Math.max(1.0, this.getWidth() - 4.0), 0.0, 1.0);
         int next = (int)Math.round((this.min + raw * (this.max - this.min)) / this.step) * this.step;
         next = (int)Theme.clamp(next, this.min, this.max);
         if (next != this.value) {
            this.value = next;
            if (this.onChange != null) {
               this.onChange.run();
            }
         }

         this.position.to(this.fraction());
         this.repaint();
      }

      @Override
      protected void paintComponent(Graphics g) {
         Graphics2D g2 = Theme.hq(g);
         double width = this.getWidth();
         Font labelFont = Theme.font(Font.BOLD, 10.5F);
         Theme.text(g2, this.label.toUpperCase(), 2.0, 11.0, labelFont, Theme.MUTED);
         Font valueFont = Theme.font(Font.BOLD, 12.5F);
         String readout = this.value + this.suffix;
         Theme.text(g2, readout, width - Theme.width(g2, readout, valueFont) - 2.0, 12.0, valueFont, Theme.TEXT);
         double trackY = this.getHeight() - 14.0;
         double trackHeight = 6.0;
         Theme.fill(g2, 0.0, trackY, width, trackHeight, 3.0, Theme.alpha(Color.BLACK, 0.3));
         Theme.fill(g2, 0.0, trackY, width, trackHeight, 3.0, Theme.alpha(Theme.WHITE, 0.05));
         double filled = Math.max(trackHeight, width * this.position.get());
         Theme.fill(g2, 0.0, trackY, filled, trackHeight, 3.0, Theme.brand(0.0, width));
         double knobSize = 15.0 + 2.0 * this.hover.get();
         double knobX = Theme.clamp(filled - knobSize / 2.0, 0.0, width - knobSize);
         double knobY = trackY + trackHeight / 2.0 - knobSize / 2.0;
         Theme.glow(g2, knobX, knobY, knobSize, knobSize, knobSize / 2.0, Theme.ACCENT, 8, 0.5 + 0.5 * this.hover.get());
         Theme.fill(g2, knobX, knobY, knobSize, knobSize, knobSize / 2.0, Theme.WHITE);
         g2.dispose();
      }
   }

   /** Pill switch. */
   static final class Toggle extends JComponent {
      private final Anim.Val on;
      private final Anim.Val hover;
      private final Anim.Val press;
      private final Runnable[] action = new Runnable[1];
      private final boolean[] enabled = {true};
      private final String label;
      private final String hint;
      private boolean value;
      private Runnable onChange;

      Toggle(String label, String hint, boolean value) {
         this.label = label;
         this.hint = hint;
         this.value = value;
         this.on = Anim.attach(this, value ? 1.0 : 0.0, 0.22);
         this.hover = Anim.attach(this, 0.0, 0.2);
         this.press = Anim.attach(this, 0.0, 0.3);
         this.action[0] = () -> {
            this.value = !this.value;
            this.on.to(this.value ? 1.0 : 0.0);
            if (this.onChange != null) {
               this.onChange.run();
            }
         };
         interactive(this, this.hover, this.press, this.action, this.enabled);
      }

      void onChange(Runnable runnable) {
         this.onChange = runnable;
      }

      boolean value() {
         return this.value;
      }

      @Override
      protected void paintComponent(Graphics g) {
         Graphics2D g2 = Theme.hq(g);
         double height = this.getHeight();
         double on = this.on.get();
         double hover = this.hover.get();
         Theme.fill(g2, -8.0, 0.0, this.getWidth() + 16.0, height, 11.0, Theme.alpha(Theme.WHITE, 0.03 * hover));
         Font labelFont = Theme.font(Font.PLAIN, 13.0F);
         Font hintFont = Theme.font(Font.PLAIN, 11.0F);
         boolean hasHint = this.hint != null && !this.hint.isEmpty();
         double labelBaseline = hasHint ? height / 2.0 - 1.0 : Theme.baseline(g2, 0.0, height, labelFont);
         Theme.text(g2, this.label, 0.0, labelBaseline, labelFont, Theme.mix(Theme.MUTED, Theme.TEXT, 0.55 + 0.45 * Math.max(on, hover)));
         if (hasHint) {
            Theme.text(g2, this.hint, 0.0, labelBaseline + 15.0, hintFont, Theme.FAINT);
         }

         double trackWidth = 42.0;
         double trackHeight = 23.0;
         double trackX = this.getWidth() - trackWidth;
         double trackY = height / 2.0 - trackHeight / 2.0;
         if (on > 0.01) {
            Theme.glow(g2, trackX, trackY, trackWidth, trackHeight, trackHeight / 2.0, Theme.ACCENT, 8, on * 0.6);
         }

         Theme.fill(g2, trackX, trackY, trackWidth, trackHeight, trackHeight / 2.0, Theme.alpha(Color.BLACK, 0.25));
         Theme.fill(
            g2,
            trackX,
            trackY,
            trackWidth,
            trackHeight,
            trackHeight / 2.0,
            on > 0.01 ? Theme.alpha(Theme.mix(Theme.ACCENT_2, Theme.ACCENT, 0.55), on) : Theme.alpha(Theme.WHITE, 0.06)
         );
         Theme.stroke(g2, trackX, trackY, trackWidth, trackHeight, trackHeight / 2.0, Theme.alpha(Theme.WHITE, 0.10 + 0.10 * on), 1.0);
         double knobSize = trackHeight - 6.0;
         double knobX = trackX + 3.0 + (trackWidth - knobSize - 6.0) * on;
         Theme.fill(g2, knobX, trackY + 3.0, knobSize, knobSize, knobSize / 2.0, Theme.mix(new Color(0xC9, 0xC9, 0xD8), Theme.WHITE, on));
         g2.dispose();
      }
   }

   /** Progress bar with a travelling sheen while it is running. */
   static final class Progress extends JComponent {
      private final Anim.Val value = Anim.attach(this, 0.0, 0.12);
      private boolean running;

      Progress() {
         Anim.tick(() -> {
            if (this.running && this.isShowing()) {
               this.repaint();
            }
         });
      }

      void set(double fraction, boolean running) {
         this.value.to(Theme.clamp(fraction, 0.0, 1.0));
         this.running = running;
      }

      void reset() {
         this.value.set(0.0);
         this.running = false;
         this.repaint();
      }

      @Override
      protected void paintComponent(Graphics g) {
         Graphics2D g2 = Theme.hq(g);
         double width = this.getWidth();
         double height = this.getHeight();
         double radius = height / 2.0;
         Theme.fill(g2, 0.0, 0.0, width, height, radius, Theme.alpha(Color.BLACK, 0.32));
         Theme.fill(g2, 0.0, 0.0, width, height, radius, Theme.alpha(Theme.WHITE, 0.07));
         Theme.stroke(g2, 0.0, 0.0, width, height, radius, Theme.alpha(Theme.WHITE, 0.07), 1.0);
         double filled = width * this.value.get();
         if (filled > 1.0) {
            double drawn = Math.max(height, filled);
            Theme.glow(g2, 0.0, 0.0, drawn, height, radius, Theme.ACCENT, 7, 0.75);
            Theme.fill(g2, 0.0, 0.0, drawn, height, radius, Theme.brand(0.0, width));
            if (this.running) {
               Graphics2D sheen = (Graphics2D)g2.create();
               sheen.clip(Theme.round(0.0, 0.0, drawn, height, radius));
               double travel = (Anim.time() * 0.55 % 1.0) * (width + 160.0) - 80.0;
               sheen.setPaint(Theme.horizontal(travel, 80.0, Theme.alpha(Theme.WHITE, 0.0), Theme.alpha(Theme.WHITE, 0.28)));
               sheen.fill(Theme.round(travel, 0.0, 40.0, height, radius));
               sheen.setPaint(Theme.horizontal(travel + 40.0, 80.0, Theme.alpha(Theme.WHITE, 0.28), Theme.alpha(Theme.WHITE, 0.0)));
               sheen.fill(Theme.round(travel + 40.0, 0.0, 40.0, height, radius));
               sheen.dispose();
            }
         }

         g2.dispose();
      }
   }

   /** Console output. Keeps the tail of the log and fades the older lines. */
   static final class Log extends JComponent {
      private final List<Widgets.Log.Line> lines = new ArrayList<>();
      private static final int LIMIT = 200;

      void add(String text, Color color) {
         synchronized (this.lines) {
            this.lines.add(new Widgets.Log.Line(text, color));
            while (this.lines.size() > LIMIT) {
               this.lines.remove(0);
            }
         }

         this.repaint();
      }

      void clear() {
         synchronized (this.lines) {
            this.lines.clear();
         }

         this.repaint();
      }

      @Override
      protected void paintComponent(Graphics g) {
         Graphics2D g2 = Theme.hq(g);
         double width = this.getWidth();
         double height = this.getHeight();
         Theme.fill(g2, 0.0, 0.0, width, height, 12.0, Theme.alpha(Color.BLACK, 0.26));
         Theme.stroke(g2, 0.0, 0.0, width, height, 12.0, Theme.alpha(Theme.WHITE, 0.055), 1.0);
         Graphics2D clip = (Graphics2D)g2.create();
         clip.clip(Theme.round(0.0, 0.0, width, height, 12.0));
         Font font = Theme.mono(11.5F);
         double lineHeight = 17.0;
         int visible = (int)Math.floor((height - 26.0) / lineHeight);
         List<Widgets.Log.Line> tail;
         synchronized (this.lines) {
            int from = Math.max(0, this.lines.size() - visible);
            tail = new ArrayList<>(this.lines.subList(from, this.lines.size()));
         }

         double y = 26.0;

         for (int i = 0; i < tail.size(); i++) {
            Widgets.Log.Line line = tail.get(i);
            double age = tail.size() <= 1 ? 1.0 : (double)i / (tail.size() - 1);
            double opacity = 0.45 + 0.55 * age;
            Theme.text(clip, "›", 14.0, y, font, Theme.alpha(Theme.FAINT, opacity * 0.7));
            Theme.text(clip, Theme.ellipsize(clip, line.text, font, width - 44.0), 28.0, y, font, Theme.alpha(line.color, opacity));
            y += lineHeight;
         }

         clip.dispose();
         g2.dispose();
      }

      private record Line(String text, Color color) {
      }
   }

   /** Translucent card that groups a section of the page. */
   static final class Card extends JComponent {
      private final String title;

      Card(String title) {
         this.title = title;
         this.setLayout(null);
      }

      @Override
      protected void paintComponent(Graphics g) {
         Graphics2D g2 = Theme.hq(g);
         Theme.glass(g2, 0.0, 0.0, this.getWidth(), this.getHeight(), 16.0, 1.0);
         if (this.title != null) {
            Font font = Theme.font(Font.BOLD, 12.0F);
            Theme.text(g2, this.title.toUpperCase(), 20.0, 27.0, font, Theme.MUTED);
         }

         g2.dispose();
      }
   }

   /** The "L" brand mark. */
   static final class Brand extends JComponent {
      Brand() {
         Anim.tick(() -> {
            if (this.isShowing()) {
               this.repaint();
            }
         });
      }

      @Override
      protected void paintComponent(Graphics g) {
         Graphics2D g2 = Theme.hq(g);
         double size = Math.min(this.getWidth(), this.getHeight());
         double pulse = 0.5 + 0.5 * Math.sin(Anim.time() * 1.1);
         Theme.glow(g2, 0.0, 0.0, size, size, size * 0.31, Theme.ACCENT, 12, 0.45 + 0.25 * pulse);
         Theme.fill(g2, 0.0, 0.0, size, size, size * 0.31, Theme.brand(0.0, size));
         Theme.fill(g2, size * 0.12, 1.0, size * 0.76, size * 0.42, size * 0.2, Theme.alpha(Theme.WHITE, 0.18));
         Theme.stroke(g2, 0.0, 0.0, size, size, size * 0.31, Theme.alpha(Theme.WHITE, 0.28), 1.0);
         Font font = Theme.font(Font.BOLD, (float)(size * 0.56));
         Theme.textCentered(g2, "L", size / 2.0, Theme.baseline(g2, 0.0, size, font), font, new Color(0x18, 0x12, 0x2C));
         g2.dispose();
      }
   }

   /** Small status chip, e.g. the license state. */
   static final class Chip extends JComponent {
      private String text;
      private Color color;

      Chip(String text, Color color) {
         this.text = text;
         this.color = color;
         Anim.tick(() -> {
            if (this.isShowing()) {
               this.repaint();
            }
         });
      }

      void set(String text, Color color) {
         this.text = text;
         this.color = color;
         this.repaint();
      }

      @Override
      protected void paintComponent(Graphics g) {
         Graphics2D g2 = Theme.hq(g);
         double height = this.getHeight();
         double width = this.getWidth();
         Theme.fill(g2, 0.0, 0.0, width, height, height / 2.0, Theme.alpha(this.color, 0.13));
         Theme.stroke(g2, 0.0, 0.0, width, height, height / 2.0, Theme.alpha(this.color, 0.35), 1.0);
         double dot = 6.0;
         double pulse = 0.6 + 0.4 * Math.sin(Anim.time() * 2.2);
         Theme.fill(g2, 13.0, height / 2.0 - dot / 2.0, dot, dot, dot / 2.0, Theme.alpha(this.color, 0.55 + 0.45 * pulse));
         Font font = Theme.font(Font.BOLD, 11.0F);
         Theme.text(g2, this.text, 26.0, Theme.baseline(g2, 0.0, height, font), font, Theme.mix(this.color, Theme.WHITE, 0.35));
         g2.dispose();
      }
   }

   /** Minimal document listener so fields can repaint their placeholder. */
   private static final class SimpleDocumentListener implements javax.swing.event.DocumentListener {
      private final Runnable onChange;

      SimpleDocumentListener(Runnable onChange) {
         this.onChange = onChange;
      }

      @Override
      public void insertUpdate(javax.swing.event.DocumentEvent event) {
         this.onChange.run();
      }

      @Override
      public void removeUpdate(javax.swing.event.DocumentEvent event) {
         this.onChange.run();
      }

      @Override
      public void changedUpdate(javax.swing.event.DocumentEvent event) {
         this.onChange.run();
      }
   }
}
