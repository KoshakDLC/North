package ru.metaculture.protection;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class EventManager {
   private static final Map<Class<? extends Event>, EventManager.RegisteredHandler[]> handlersByEventType = new ConcurrentHashMap<>();

   public static void register(Object object) {
      for (Method method : object.getClass().getDeclaredMethods()) {
         if (!isNotEventHandler(method)) {
            registerHandler(method, object);
         }
      }
   }

   public static void unregister(Object object) {
      for (Entry<Class<? extends Event>, RegisteredHandler[]> entry : handlersByEventType.entrySet()) {
         RegisteredHandler[] handlers = entry.getValue();
         int remainingCount = 0;

         for (RegisteredHandler handler : handlers) {
            if (!handler.listener().equals(object)) {
               remainingCount++;
            }
         }

         if (remainingCount != handlers.length) {
            if (remainingCount == 0) {
               handlersByEventType.remove(entry.getKey(), handlers);
            } else {
               RegisteredHandler[] retainedHandlers = new RegisteredHandler[remainingCount];
               int writeIndex = 0;

               for (RegisteredHandler handler : handlers) {
                  if (!handler.listener().equals(object)) {
                     retainedHandlers[writeIndex++] = handler;
                  }
               }

               handlersByEventType.put(entry.getKey(), retainedHandlers);
            }
         }
      }

      cleanup(true);
   }

   private static void registerHandler(Method method, Object object) {
      try {
         Class<? extends Event> eventType = (Class<? extends Event>)method.getParameterTypes()[0];
         RegisteredHandler newHandler = new RegisteredHandler(object, method, method.getAnnotation(EventHandler.class).priority());
         if (!newHandler.method().isAccessible()) {
            newHandler.method().setAccessible(true);
         }

         RegisteredHandler[] existingHandlers = handlersByEventType.get(eventType);
         if (existingHandlers != null) {
            for (RegisteredHandler existingHandler : existingHandlers) {
               if (existingHandler.equals(newHandler)) {
                  return;
               }
            }

            RegisteredHandler[] updatedHandlers = new RegisteredHandler[existingHandlers.length + 1];
            System.arraycopy(existingHandlers, 0, updatedHandlers, 0, existingHandlers.length);
            updatedHandlers[existingHandlers.length] = newHandler;
            handlersByEventType.put(eventType, sortHandlers(updatedHandlers));
         } else {
            handlersByEventType.put(eventType, new RegisteredHandler[]{newHandler});
         }
      } catch (Exception exception) {
         exception.printStackTrace();
      }
   }

   public static void cleanup(boolean bl) {
      if (!bl) {
         handlersByEventType.clear();
      } else {
         for (Entry<Class<? extends Event>, RegisteredHandler[]> entry : handlersByEventType.entrySet()) {
            RegisteredHandler[] handlers = entry.getValue();
            if (handlers == null || handlers.length == 0) {
               handlersByEventType.remove(entry.getKey(), handlers);
            }
         }
      }
   }

   private static boolean isNotEventHandler(Method method) {
      return method.getParameterTypes().length != 1 || !method.isAnnotationPresent(EventHandler.class);
   }

   public static void initialize() {
   }

   public static Event post(Event event) {
      RegisteredHandler[] handlers = handlersByEventType.get(event.getClass());
      if (handlers != null && handlers.length > 0) {
         if (event instanceof CancellableEvent cancellableEvent) {
            for (RegisteredHandler handler : handlers) {
               invokeHandler(handler, event);
               if (cancellableEvent.isCancelled()) {
                  break;
               }
            }
         } else {
            for (RegisteredHandler handler : handlers) {
               invokeHandler(handler, event);
            }
         }
      }

      return event;
   }

   private static RegisteredHandler[] sortHandlers(RegisteredHandler[] handlers) {
      Arrays.sort(handlers, (left, right) -> Integer.compare(priorityIndex(left.priority()), priorityIndex(right.priority())));
      return handlers;
   }

   private static int priorityIndex(byte priority) {
      for (int index = 0; index < EventPriorityOrder.ORDER.length; index++) {
         if (EventPriorityOrder.ORDER[index] == priority) {
            return index;
         }
      }

      return EventPriorityOrder.ORDER.length;
   }

   private static void invokeHandler(RegisteredHandler handler, Event event) {
      try {
         handler.method().invoke(handler.listener(), event);
      } catch (IllegalArgumentException | IllegalAccessException exception) {
         System.err
            .println(
               "[EventManager] Failed to invoke "
                  + handler.method().getName()
                  + " on "
                  + handler.listener().getClass().getSimpleName()
                  + ": "
                  + exception.getMessage()
            );
      } catch (InvocationTargetException exception) {
         Throwable cause = exception.getCause();
         System.err
            .println(
               "[EventManager] Exception in handler "
                  + handler.method().getName()
                  + " on "
                  + handler.listener().getClass().getSimpleName()
                  + ": "
                  + (cause != null ? cause.getMessage() : exception.getMessage())
            );
         if (cause != null) {
            cause.printStackTrace();
         }
      }
   }

   static final class RegisteredHandler {
      private final Object listener;
      private final Method method;
      private final byte priority;

      public RegisteredHandler(Object object, Method method, byte priority) {
         this.listener = object;
         this.method = method;
         this.priority = priority;
      }

      public Object listener() {
         return this.listener;
      }

      public Method method() {
         return this.method;
      }

      public byte priority() {
         return this.priority;
      }

      @Override
      public boolean equals(Object object) {
         if (this == object) {
            return true;
         } else if (object != null && this.getClass() == object.getClass()) {
            RegisteredHandler other = (RegisteredHandler)object;
            return this.priority == other.priority && this.listener.equals(other.listener) && this.method.equals(other.method);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return Objects.hash(this.listener, this.method, this.priority);
      }
   }
}
