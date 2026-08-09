package ru.metaculture.protection;

import java.awt.Color;
import java.util.List;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.client.util.math.Vector2f;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public class GpsCommand extends Command {
   public static Vector2f vector2f = new Vector2f(Float.MAX_VALUE, Float.MAX_VALUE);
   public static float floatValue = Float.MAX_VALUE;
   public static String metka = "Метка";
   private static volatile boolean flag;
   private final DirectionalAnimation directionalAnimation = new EaseInOutQuadAnimation(300, 1.0);
   private boolean flag2;

   public GpsCommand() {
      super("gps", "Добавление меток, для ивентов и тд", ".gps off | .gps <x> <z> | .gps <x> <y> <z> [название]");
      this.addCompletionProvider("off", List::of);
   }

   @Compile
   @Override
   public void execute(String[] strings) {
      if (strings.length == 1 && strings[0].equalsIgnoreCase("off")) {
         this.invoke();
         return;
      }

      try {
         if (strings.length == 2) {
            invoke2(Float.parseFloat(strings[0]), Float.parseFloat(strings[1]));
         } else if (strings.length >= 3) {
            float floatValue = Float.parseFloat(strings[0]);
            float floatValue2 = Float.parseFloat(strings[1]);
            float floatValue3 = Float.parseFloat(strings[2]);
            String text = strings.length > 3 ? String.join(" ", java.util.Arrays.copyOfRange(strings, 3, strings.length)) : "Метка";
            invoke3(floatValue, floatValue2, floatValue3, text);
         } else {
            ChatUtil.sendClientMessage("§cИспользование: §f" + this.getUsage());
         }
      } catch (NumberFormatException numberFormatException) {
         ChatUtil.sendClientMessage("§cКоординаты GPS должны быть числами. Использование: §f" + this.getUsage());
      }
   }

   @Compile
   private void invoke() {
      vector2f = new Vector2f(Float.MAX_VALUE, Float.MAX_VALUE);
      floatValue = Float.MAX_VALUE;
      metka = "Метка";
      flag = false;
      this.flag2 = false;
      ChatUtil.sendClientMessage("§7[GPS] Метка удалена.");
   }

   public static void invoke2(float f, float g) {
      vector2f = new Vector2f(f, g);
      floatValue = Float.MAX_VALUE;
      metka = "Метка";
      flag = false;
      ChatUtil.sendClientMessage("§a[GPS] Метка установлена на X: " + f + " Z: " + g);
   }

   public static void invoke3(float f, float g, float h, String string) {
      vector2f = new Vector2f(f, h);
      floatValue = g;
      metka = string;
      flag = false;
      ChatUtil.sendClientMessage("§a[GPS] Метка '" + string + "' установлена на X: " + f + " Y: " + g + " Z: " + h);
   }

   public static void invoke4() {
      flag = true;
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (packetEvent.getPacket() instanceof GameMessageS2CPacket gameMessageS2CPacket) {
         String text2 = gameMessageS2CPacket.content().getString().toLowerCase();
         if (text2.contains("заверш") || text2.contains("окончен") || text2.contains("время вышло") || text2.contains("вы у цели")) {
            invoke4();
         }
      }
   }

   @EventHandler
   public void onHudRender(HudRenderEvent hudRenderEvent) {
      RenderManager renderManager = hudRenderEvent.getRenderManager();
      if (vector2f.getX() != Float.MAX_VALUE && vector2f.getY() != Float.MAX_VALUE) {
         if (!this.flag2 && this.directionalAnimation.getAnimationDirection() != AnimationDirection.FORWARDS) {
            this.directionalAnimation.invoke3(AnimationDirection.FORWARDS);
         }

         if (a_.player != null && a_.world != null) {
            double doubleValue = a_.gameRenderer.getCamera().getPos().x;
            double doubleValue2 = a_.gameRenderer.getCamera().getPos().y;
            double doubleValue3 = a_.gameRenderer.getCamera().getPos().z;
            double doubleValue4 = vector2f.getX();
            double doubleValue5 = vector2f.getY();
            double doubleValue6 = floatValue == Float.MAX_VALUE ? a_.player.getY() + 1.5 : floatValue;
            double doubleValue7 = doubleValue4 - doubleValue;
            double doubleValue8 = doubleValue6 - doubleValue2;
            double doubleValue9 = doubleValue5 - doubleValue3;
            double doubleValue10 = Math.sqrt(doubleValue7 * doubleValue7 + doubleValue8 * doubleValue8 + doubleValue9 * doubleValue9);
            if (doubleValue10 <= 3.75 || flag) {
               this.flag2 = true;
               flag = false;
               this.directionalAnimation.invoke3(AnimationDirection.BACKWARDS);
            } else if (doubleValue10 > 5.5) {
               this.flag2 = false;
            }

            if (this.flag2 && this.directionalAnimation.measure3() <= 0.02) {
               vector2f = new Vector2f(Float.MAX_VALUE, Float.MAX_VALUE);
               floatValue = Float.MAX_VALUE;
               metka = "Метка";
               this.flag2 = false;
            } else {
               Vec3d vec3d = new Vec3d(doubleValue4, doubleValue6, doubleValue5);
               Vec3d vec3d2 = MathUtils.resolve(vec3d);
               boolean flag = vec3d2 != null && vec3d2.z > 0.001F && vec3d2.z <= 1.0;
               ProjectionUtils projectionUtils = new ProjectionUtils(a_);
               if (flag) {
                  float floatValue4 = (float)vec3d2.x;
                  float floatValue5 = (float)vec3d2.y;
                  float floatValue6 = (float)Math.max(0.6, 1.2 - doubleValue10 / 150.0) * Math.max(0.0F, this.directionalAnimation.measure3());
                  renderManager.invoke56(floatValue4, floatValue5);
                  renderManager.invoke59(floatValue6, floatValue6);
                  float floatValue7 = 85.0F;
                  int intValue = ColorUtils.compute43(255, 255, 255, (int)(255.0F * this.directionalAnimation.measure3()));
                  float floatValue8 = RenderManager.resolve7(FontRegistry.fontObject7, "B", floatValue7).floatValue;
                  renderManager.invoke69(FontRegistry.fontObject7, -floatValue8 / 2.0F, -45.0F, floatValue7, "B", intValue);
                  float floatValue9 = 30.0F;
                  float floatValue10 = RenderManager.resolve7(FontRegistry.fontObject, metka, floatValue9).floatValue;
                  int intValue2 = ColorUtils.compute43(255, 255, 255, (int)(255.0F * this.directionalAnimation.measure3()));
                  renderManager.invoke69(FontRegistry.fontObject, -floatValue10 / 2.0F, -20.0F, floatValue9, metka, intValue2);
                  String text3 = String.format("%.1fM", doubleValue10);
                  float floatValue11 = 35.0F;
                  float floatValue12 = RenderManager.resolve7(FontRegistry.fontObject, text3, floatValue11).floatValue;
                  int intValue3 = new Color(200, 200, 200, (int)(220.0F * this.directionalAnimation.measure3())).getRGB();
                  renderManager.invoke69(FontRegistry.fontObject, -floatValue12 / 2.0F, 0.0F, floatValue11, text3, intValue3);
                  renderManager.invoke57();
                  renderManager.invoke57();
               } else {
                  float floatValue13 = a_.gameRenderer.getCamera().getYaw();
                  double doubleValue11 = MathHelper.cos((float)Math.toRadians(floatValue13));
                  double doubleValue12 = MathHelper.sin((float)Math.toRadians(floatValue13));
                  double doubleValue13 = -(doubleValue9 * doubleValue11 - doubleValue7 * doubleValue12);
                  double doubleValue14 = -(doubleValue7 * doubleValue11 + doubleValue9 * doubleValue12);
                  double doubleValue15 = Math.atan2(doubleValue13, doubleValue14) * 180.0 / Math.PI;
                  float floatValue14 = this.directionalAnimation.measure3() * 180.0F;
                  double doubleValue16 = projectionUtils.getDoubleValue();
                  double doubleValue17 = projectionUtils.getDoubleValue2();
                  double doubleValue18 = floatValue14 * MathHelper.cos((float)Math.toRadians(doubleValue15));
                  double doubleValue19 = floatValue14 * MathHelper.sin((float)Math.toRadians(doubleValue15));
                  double doubleValue20 = Math.min(1.0, doubleValue10 / 20.0);
                  double doubleValue21 = doubleValue16 + doubleValue18;
                  double doubleValue22 = doubleValue17 + doubleValue19 + doubleValue20;
                  Identifier identifier = Identifier.of("wild", "/textures/arrows/arrows.png");
                  if (a_.getTextureManager().getTexture(identifier).getGlTexture() instanceof GlTexture glTexture) {
                     int intValue4 = glTexture.getGlId();
                     if (intValue4 > 0) {
                        int intValue5 = ColorUtils.compute43(255, 255, 255, (int)(255.0F * this.directionalAnimation.measure3()));
                        int intValue6 = (int)(this.directionalAnimation.measure3() * 255.0F);
                        Color color = RenderManager.RenderManagerState.resolve15(
                           RenderManager.RenderManagerState.compute23(RenderManager.RenderManagerState.compute6(1, 1), this.directionalAnimation.measure3() * 50.0F)
                        );
                        renderManager.invoke56((float)doubleValue21, (float)doubleValue22);
                        renderManager.invoke54((float)(doubleValue15 + 90.0));
                        renderManager.invoke41(0.5F, -1.0F, 0.1F, 0.1F, 5.0F, 8.0F, 0.1F, color.getRGB());
                        renderManager.invoke65(intValue6);
                        renderManager.invoke9(intValue4, -16.0F, -16.0F, 32.0F, 32.0F, intValue5, false);
                        renderManager.invoke66();
                        renderManager.invoke57();
                        float floatValue15 = 22.0F;
                        float floatValue16 = RenderManager.resolve7(FontRegistry.fontObject, metka, floatValue15).floatValue;
                        int intValue7 = ColorUtils.compute43(255, 255, 255, (int)(255.0F * this.directionalAnimation.measure3()));
                        renderManager.invoke69(FontRegistry.fontObject, -floatValue16 / 2.0F, 25.0F, floatValue15, metka, intValue7);
                        String text4 = String.format("%.1fм", doubleValue10);
                        float floatValue17 = 25.0F;
                        float floatValue18 = RenderManager.resolve7(FontRegistry.fontObject, text4, floatValue17).floatValue;
                        int intValue8 = new Color(200, 200, 200, (int)(220.0F * this.directionalAnimation.measure3())).getRGB();
                        renderManager.invoke69(FontRegistry.fontObject, -floatValue18 / 2.0F, 40.0F, floatValue17, text4, intValue8);
                        renderManager.invoke57();
                     }
                  }
               }
            }
         }
      } else {
         this.flag2 = false;
         if (this.directionalAnimation.measure3() > 0.0F) {
            this.directionalAnimation.invoke3(AnimationDirection.BACKWARDS);
         }
      }
   }

   static {
      Loader.initialize();
   }
}
