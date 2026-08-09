package ru.metaculture.protection;

public class FreeLookController extends RotationListener {
   public static boolean active;
   public static boolean flag;
   public static float floatValue;
   public static float floatValue2;

   @EventHandler
   public void onMouseDelta(MouseDeltaEvent mouseDeltaEvent) {
      if (active) {
         this.invoke(mouseDeltaEvent.getDoubleValue(), mouseDeltaEvent.getDoubleValue2());
         mouseDeltaEvent.invalidate();
      }
   }

   @EventHandler
   public void onCameraRotation(CameraRotationEvent cameraRotationEvent) {
      if (active) {
         cameraRotationEvent.setFloatValue(floatValue);
         cameraRotationEvent.setFloatValue2(floatValue2);
      } else {
         floatValue = cameraRotationEvent.getFloatValue();
         floatValue2 = cameraRotationEvent.getFloatValue2();
      }
   }

   private void invoke(double d, double e) {
      floatValue2 = RenderMath.measure49((float)(floatValue2 + e * 0.15), -90.0F, 90.0F);
      floatValue = (float)(floatValue + d * 0.15);
   }
}
