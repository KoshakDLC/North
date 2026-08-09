package ru.metaculture.protection;

import java.lang.reflect.Method;

public final class IrisCompatibility {
   private static final Object OBJECT;
   private static final Method METHOD;
   private static final Method METHOD_2;

   private IrisCompatibility() {
   }

   public static boolean check() {
      return check2(METHOD);
   }

   public static boolean isRenderingShadowPass() {
      return check2(METHOD_2);
   }

   private static boolean check2(Method method) {
      if (OBJECT != null && method != null) {
         try {
            return Boolean.TRUE.equals(method.invoke(OBJECT));
         } catch (LinkageError | ReflectiveOperationException exception) {
            return false;
         }
      } else {
         return false;
      }
   }

   static {
      Object object = null;
      Method method2 = null;
      Method method3 = null;

      try {
         Class type = Class.forName("net.irisshaders.iris.api.v0.IrisApi");
         object = type.getMethod("getInstance").invoke(null);
         method2 = type.getMethod("isShaderPackInUse");
         method3 = type.getMethod("isRenderingShadowPass");
      } catch (LinkageError | ReflectiveOperationException exception2) {
      }

      OBJECT = object;
      METHOD = method2;
      METHOD_2 = method3;
   }
}
