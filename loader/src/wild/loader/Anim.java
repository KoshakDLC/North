package wild.loader;

import java.util.concurrent.CopyOnWriteArrayList;
import javax.swing.JComponent;
import javax.swing.Timer;

/**
 * One Swing timer drives every animation in the loader, so hover states, progress and the
 * background all advance on the same 60 Hz beat instead of each widget owning a timer.
 */
final class Anim {
   private static final CopyOnWriteArrayList<Runnable> TICKS = new CopyOnWriteArrayList<>();
   private static final long START = System.nanoTime();
   private static Timer timer;

   private Anim() {
   }

   static void tick(Runnable runnable) {
      TICKS.add(runnable);
      if (timer == null) {
         timer = new Timer(16, event -> {
            for (Runnable tick : TICKS) {
               tick.run();
            }
         });
         timer.setCoalesce(true);
         timer.start();
      }
   }

   /** Seconds since start, for the looping background motion. */
   static double time() {
      return (System.nanoTime() - START) / 1.0E9;
   }

   /** Eased value that chases a target with a per-frame lerp. */
   static final class Val {
      private final double speed;
      private double value;
      private double target;

      Val(double initial, double speed) {
         this.value = initial;
         this.target = initial;
         this.speed = speed;
      }

      void to(double next) {
         this.target = next;
      }

      void set(double next) {
         this.target = next;
         this.value = next;
      }

      double get() {
         return this.value;
      }

      double target() {
         return this.target;
      }

      boolean step() {
         double delta = this.target - this.value;
         if (Math.abs(delta) < 0.0015) {
            if (this.value == this.target) {
               return false;
            } else {
               this.value = this.target;
               return true;
            }
         } else {
            this.value = this.value + delta * this.speed;
            return true;
         }
      }
   }

   /** Eased value bound to a component: the component repaints while the value moves. */
   static Val attach(JComponent owner, double initial, double speed) {
      Anim.Val val = new Anim.Val(initial, speed);
      tick(() -> {
         if (val.step()) {
            owner.repaint();
         }
      });
      return val;
   }
}
