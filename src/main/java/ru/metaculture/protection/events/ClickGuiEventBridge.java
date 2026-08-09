package ru.metaculture.protection;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.lwjgl.glfw.GLFW;

public final class ClickGuiEventBridge {
   private static volatile boolean flag = false;

   private ClickGuiEventBridge() {
   }

   public static void invoke() {
      if (!flag) {
         flag = true;
         EventManager.register(
            new Object() {
               @EventHandler
               public void onHudFrame(HudRenderEvent hudRenderEvent) {
                  MinecraftClient client2 = hudRenderEvent.getClient();
                  if (client2 != null) {
                     if (client2.currentScreen instanceof ClickGuiScreen) {
                        if (ClickGuiEventBridge.check(client2)) {
                           double[] doubleValues3 = new double[1];
                           double[] doubleValues4 = new double[1];
                           if (client2.getWindow() != null) {
                              GLFW.glfwGetCursorPos(client2.getWindow().getHandle(), doubleValues3, doubleValues4);
                              if (client2.mouse != null) {
                                 client2.mouse.unlockCursor();
                              }
                           }

                           BlurRenderer blurRenderer2 = BlurRenderer.getINSTANCE();
                           boolean flag2 = blurRenderer2.check(client2.currentScreen)
                              && blurRenderer2.check3(client2.getWindow().getFramebufferWidth(), client2.getWindow().getFramebufferHeight());

                           try {
                              LegacyClickGuiRenderer.invoke(
                                 hudRenderEvent.getRenderManager(),
                                 hudRenderEvent.getDrawContext(),
                                 (int)doubleValues3[0],
                                 (int)doubleValues4[0],
                                 client2.getRenderTickCounter().getDynamicDeltaTicks()
                              );
                              hudRenderEvent.getRenderManager().invoke20();
                           } finally {
                              if (flag2) {
                                 blurRenderer2.invoke5();
                              }
                           }
                        }
                     } else if (client2.currentScreen instanceof ModernClickGuiScreen modernClickGuiScreen) {
                        modernClickGuiScreen.invoke(
                           hudRenderEvent.getRenderManager(),
                           hudRenderEvent.getDrawContext(),
                           hudRenderEvent.getIntValue(),
                           hudRenderEvent.getIntValue2(),
                           client2.getRenderTickCounter().getDynamicDeltaTicks()
                        );
                     }
                  }
               }
            }
         );
      }
   }

   static boolean check(MinecraftClient minecraftClient) {
      if (minecraftClient != null && minecraftClient.getWindow() != null) {
         Window window = minecraftClient.getWindow();
         return !window.hasZeroWidthOrHeight() && window.getFramebufferWidth() > 0 && window.getFramebufferHeight() > 0;
      } else {
         return false;
      }
   }
}
