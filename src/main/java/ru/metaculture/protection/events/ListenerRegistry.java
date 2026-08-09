package ru.metaculture.protection;

import java.util.HashMap;

public final class ListenerRegistry extends HashMap<Class<? extends RotationListener>, RotationListener> {
   public void invoke() {
      this.invoke2(new FreeLookController(), new RotationController(), new PacketStateListener());
      this.values().forEach(rotationListener -> EventManager.register(rotationListener));
   }

   public void invoke2(RotationListener... rotationListeners) {
      for (RotationListener rotationListener2 : rotationListeners) {
         this.put((Class<? extends RotationListener>)rotationListener2.getClass(), rotationListener2);
      }
   }

   public void invoke3(RotationListener... rotationListeners2) {
      for (RotationListener rotationListener3 : rotationListeners2) {
         EventManager.unregister(rotationListener3);
         this.remove(rotationListener3.getClass());
      }
   }

   public <T extends RotationListener> T resolve(Class<T> class_) {
      return this.values().stream().filter(rotationListener4 -> rotationListener4.getClass() == class_).map(class_::cast).findFirst().orElse(null);
   }
}
