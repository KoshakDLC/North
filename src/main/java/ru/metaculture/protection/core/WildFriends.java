package ru.metaculture.protection;

import java.awt.Color;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import net.minecraft.client.render.Camera;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.json.JSONObject;
import org.wild.module.api.Module;

public class WildFriends extends Module {
   private static final Identifier IDENTIFIER = Identifier.of("wild", "textures/png/skull_state_0.png");
   private static final Identifier IDENTIFIER_2 = Identifier.of("wild", "textures/png/skull_state_1.png");
   private static final Identifier IDENTIFIER_3 = Identifier.of("wild", "textures/png/skull_state_2.png");
   public final ModeSetting otobrazhat = new ModeSetting("Отображать: ", "Только у друзей", "Всех", "Только у друзей");
   public final GroupSetting informatsiya = new GroupSetting(
      "Информация", new BooleanSetting("Показ в табе", true), new BooleanSetting("Показ в нейм тегах", false), new BooleanSetting("Показ лого", false)
   );
   public final BooleanSetting ustanovkaMetok = new BooleanSetting("Установка меток", true);
   public final KeybindSetting knopkaUstanovki = new KeybindSetting("Кнопка установки", -1).visibleWhen(this.ustanovkaMetok::isEnabled);
   public final BooleanSetting fokusTseli = new BooleanSetting("Фокус цели", true);
   public final BooleanSetting schetchikPopnutyhTotemovVraga = new BooleanSetting("Счетчик попнутых тотемов врага", true);
   public static String text = "";
   private String text2 = "";
   private long timestamp = 0L;
   public static double doubleValue;
   public static double doubleValue2;
   public static double doubleValue3;
   private long timestamp2 = 0L;

   public WildFriends() {
      this.addSettings(new Setting[]{this.otobrazhat, this.informatsiya, this.ustanovkaMetok, this.knopkaUstanovki, this.fokusTseli, this.schetchikPopnutyhTotemovVraga});
   }

   @EventHandler
   public void onRawInput(RawInputEvent rawInputEvent) {
      if (!ServerModeDetector.check()) {
         if (rawInputEvent.getKeyCode() == this.knopkaUstanovki.getKeyCode()) {
         }
      }
   }

   @EventHandler
   public void onPlayerTick(PlayerTickEvent playerTickEvent) {
      if (!ServerModeDetector.check()) {
         if (IrcWebSocketClient.instance != null) {
            IrcWebSocketClient.instance.invoke2();
         }

         if (System.currentTimeMillis() - this.timestamp > 200L) {
            this.timestamp = System.currentTimeMillis();
            if (CLIENT.player != null && CLIENT.world != null) {
               ServerStatsParser.INSTANCE.invoke2();
               String text = CLIENT.world.getRegistryKey().getValue().getPath();
               float floatValue = CLIENT.player.getHealth() + CLIENT.player.getAbsorptionAmount();
               boolean flag = ServerStatsParser.check();
               IrcWebSocketClient ircWebSocketClient = IrcWebSocketClient.instance;
               if (ircWebSocketClient != null) {
                  ircWebSocketClient.invoke3(
                     CLIENT.player.getX(), CLIENT.player.getY(), CLIENT.player.getZ(), text, floatValue, ServerStatsParser.INSTANCE.getNA2(), flag
                  );
               }
            }
         }

         if (AttackAura.livingEntity instanceof PlayerEntity playerEntity2) {
            text = playerEntity2.isAlive() && playerEntity2.getHealth() > 0.0F ? playerEntity2.getName().getString() : "";
         }

         if (!text.isEmpty() && FriendCommand.check(text)) {
            text = "";
         }

         if (CLIENT.world != null) {
            PlayerEntity playerEntity3 = null;

            for (PlayerEntity playerEntity4 : CLIENT.world.getPlayers()) {
               if (!playerEntity4.isAlive() || playerEntity4.getHealth() <= 0.0F) {
                  IrcWebSocketClient.VALUES_BY_KEY_5.remove(playerEntity4.getName().getString());
                  if (playerEntity4.getName().getString().equalsIgnoreCase(text)) {
                     text = "";
                  }
               } else if (playerEntity4.getName().getString().equalsIgnoreCase(text)) {
                  playerEntity3 = playerEntity4;
               }
            }

            if (this.fokusTseli.isEnabled() && IrcWebSocketClient.instance != null && IrcWebSocketClient.instance.isOpen()) {
               long longValue = System.currentTimeMillis();
               if (playerEntity3 != null) {
                  doubleValue = playerEntity3.getX();
                  doubleValue2 = playerEntity3.getY();
                  doubleValue3 = playerEntity3.getZ();
                  if (longValue - this.timestamp2 > 200L) {
                     this.timestamp2 = longValue;
                     this.invoke(text, doubleValue, doubleValue2, doubleValue3);
                  }
               } else if (text.isEmpty() && !this.text2.isEmpty()) {
                  this.timestamp2 = longValue;
                  this.invoke("", 0.0, 0.0, 0.0);
               }
            }
         }
      }
   }

   private void invoke(String string, double d, double e, double f) {
      try {
         JSONObject jsonObject = new JSONObject();
         jsonObject.put("type", "target_sync");
         jsonObject.put("user", CLIENT.getSession().getUsername());
         jsonObject.put("target", string);
         jsonObject.put("server", IrcWebSocketClient.resolve());
         if (!string.isEmpty()) {
            jsonObject.put("x", d);
            jsonObject.put("y", e);
            jsonObject.put("z", f);
         }

         IrcWebSocketClient.instance.send(jsonObject.toString());
         this.text2 = string;
      } catch (Exception exception) {
      }
   }

   @EventHandler
   public void onPacket(PacketEvent packetEvent) {
      if (!ServerModeDetector.check() && this.schetchikPopnutyhTotemovVraga.isEnabled()) {
         if (packetEvent.getPacket() instanceof EntityStatusS2CPacket entityStatusS2CPacket && entityStatusS2CPacket.getStatus() == 35) {
            Entity entity = entityStatusS2CPacket.getEntity(CLIENT.world);
            if (entity instanceof PlayerEntity playerEntity5) {
               String text2 = playerEntity5.getName().getString();
               int intValue = IrcWebSocketClient.VALUES_BY_KEY_5.getOrDefault(text2, 0) + 1;
               IrcWebSocketClient.VALUES_BY_KEY_5.put(text2, intValue);
               IrcWebSocketClient.VALUES_BY_KEY_6.put(text2, System.currentTimeMillis());
               if (AttackAura.livingEntity != null
                  && AttackAura.livingEntity.getId() == entity.getId()
                  && IrcWebSocketClient.instance != null
                  && IrcWebSocketClient.instance.isOpen()) {
                  try {
                     JSONObject jsonObject2 = new JSONObject();
                     jsonObject2.put("type", "totem_pop");
                     jsonObject2.put("attacker", CLIENT.getSession().getUsername());
                     jsonObject2.put("victim", text2);
                     jsonObject2.put("count", intValue);
                     jsonObject2.put("server", IrcWebSocketClient.resolve());
                     IrcWebSocketClient.instance.send(jsonObject2.toString());
                  } catch (Exception exception2) {
                  }
               }
            }
         }
      }
   }

   @EventHandler
   public void onHudRender(HudRenderEvent hudRenderEvent) {
      if (!ServerModeDetector.check() && this.fokusTseli.isEnabled()) {
         HashSet hashSet = new HashSet();
         if (!text.isEmpty()) {
            hashSet.add(text);
         }

         String text3 = IrcWebSocketClient.resolve();
         if (IrcWebSocketClient.instance != null && IrcWebSocketClient.instance.isOpen()) {
            boolean flag2 = "Только у друзей".equals(this.otobrazhat.getValue());
            String text4 = CLIENT.getSession() != null ? CLIENT.getSession().getUsername() : "";

            for (Entry entry : IrcWebSocketClient.VALUES_BY_KEY_4.entrySet()) {
               String text5 = (String)entry.getKey();
               IrcWebSocketClient.IrcWebSocketClientState2 ircWebSocketClientState2 = (IrcWebSocketClient.IrcWebSocketClientState2)entry.getValue();
               if ((!flag2 || text5.equals(text4) || FriendCommand.check(text5)) && ircWebSocketClientState2.text2.equals(text3)) {
                  hashSet.add(ircWebSocketClientState2.text);
               }
            }
         }

         if (!hashSet.isEmpty()) {
            float floatValue2 = CLIENT.getRenderTickCounter().getTickProgress(true);
            long longValue2 = System.currentTimeMillis();

            assert CLIENT.world != null;

            for (String text6 : (HashSet<String>)hashSet) {
               PlayerEntity playerEntity6 = null;

               for (PlayerEntity playerEntity7 : CLIENT.world.getPlayers()) {
                  if (playerEntity7.getName().getString().equalsIgnoreCase(text6) && playerEntity7 != CLIENT.player) {
                     playerEntity6 = playerEntity7;
                     break;
                  }
               }

               if (playerEntity6 != null) {
                  this.invoke2(hudRenderEvent.getRenderManager(), playerEntity6, floatValue2);
               } else {
                  double doubleValue = 0.0;
                  double doubleValue2 = 0.0;
                  double doubleValue3 = 0.0;
                  boolean flag3 = false;
                  long longValue3 = 0L;
                  Iterator iterator = IrcWebSocketClient.VALUES_BY_KEY_4.values().iterator();

                  while (true) {
                     if (iterator.hasNext()) {
                        IrcWebSocketClient.IrcWebSocketClientState2 ircWebSocketClientState22 = (IrcWebSocketClient.IrcWebSocketClientState2)iterator.next();
                        if (!ircWebSocketClientState22.text.equalsIgnoreCase(text6) || !ircWebSocketClientState22.text2.equals(text3)) {
                           continue;
                        }

                        longValue3 = longValue2 - ircWebSocketClientState22.timestamp;
                        if (longValue3 >= 4000L) {
                           continue;
                        }

                        double doubleValue4 = MathHelper.clamp(longValue3 / 200.0, 0.0, 1.0);
                        doubleValue = MathHelper.lerp(doubleValue4, ircWebSocketClientState22.doubleValue4, ircWebSocketClientState22.doubleValue);
                        doubleValue2 = MathHelper.lerp(doubleValue4, ircWebSocketClientState22.doubleValue5, ircWebSocketClientState22.doubleValue2);
                        doubleValue3 = MathHelper.lerp(doubleValue4, ircWebSocketClientState22.doubleValue6, ircWebSocketClientState22.doubleValue3);
                        flag3 = true;
                     }

                     if (!flag3 && text6.equalsIgnoreCase(text)) {
                         this.doubleValue = doubleValue;
                         this.doubleValue2 = doubleValue2;
                         this.doubleValue3 = doubleValue3;
                        flag3 = true;
                     }

                     if (flag3) {
                        float floatValue3 = 1.0F;
                        if (longValue3 > 3000L) {
                           float floatValue4 = 1.0F - (float)(longValue3 - 3000L) / 1000.0F;
                           floatValue3 = MathHelper.clamp(floatValue4, 0.0F, 1.0F);
                        }

                        float floatValue5 = 20.0F;
                        this.invoke3(hudRenderEvent.getRenderManager(), text6, doubleValue, doubleValue2 + 2.0, doubleValue3, floatValue5, 20.0F, floatValue3);
                     }
                     break;
                  }
               }
            }
         }
      }
   }

   private void invoke2(RenderManager renderManager, PlayerEntity playerEntity, float f) {
      double doubleValue5 = MathHelper.lerp(f, playerEntity.lastX, playerEntity.getX());
      double doubleValue6 = MathHelper.lerp(f, playerEntity.lastY, playerEntity.getY());
      double doubleValue7 = MathHelper.lerp(f, playerEntity.lastZ, playerEntity.getZ());
      float floatValue6 = playerEntity.getHealth() + playerEntity.getAbsorptionAmount();
      float floatValue7 = playerEntity.getMaxHealth();
      this.invoke3(renderManager, playerEntity.getName().getString(), doubleValue5, doubleValue6 + playerEntity.getHeight(), doubleValue7, floatValue6, floatValue7, 1.0F);
   }

   private void invoke3(RenderManager renderManager2, String string, double d, double e, double f, float g, float h, float i) {
      Camera camera = CLIENT.gameRenderer.getCamera();
      Vec3d vec3d = camera.getPos();
      Vec3d vec3d2 = new Vec3d(d, e, f);
      if (!(vec3d2.squaredDistanceTo(vec3d) < 1.0E-6)) {
         Vec3d vec3d3 = MathUtils.resolve(vec3d2);
         if (vec3d3 != null && !(vec3d3.z <= 0.001F) && !(vec3d3.z > 1.0)) {
            float floatValue8 = (float)vec3d3.x;
            float floatValue9 = (float)vec3d3.y;
            long longValue4 = IrcWebSocketClient.VALUES_BY_KEY_6.getOrDefault(string, 0L);
            boolean flag4 = System.currentTimeMillis() - longValue4 < 2500L;
            Identifier identifier2;
            if (flag4) {
               identifier2 = IDENTIFIER_3;
            } else if (g <= h / 2.0F) {
               identifier2 = IDENTIFIER_2;
            } else {
               identifier2 = IDENTIFIER;
            }

            int intValue2 = this.compute2(identifier2);
            if (intValue2 > 0 && i > 0.05F) {
               float floatValue10 = 28.0F;
               float floatValue11 = 15.0F;
               float floatValue12 = floatValue8 - floatValue10 / 2.0F;
               float floatValue13 = floatValue9 - floatValue10 - floatValue11;
               renderManager2.invoke56(floatValue12, floatValue13);
               renderManager2.invoke56(floatValue10 / 2.0F, floatValue10 / 2.0F);
               renderManager2.invoke59(1.0F, -1.0F);
               renderManager2.invoke56(-floatValue10 / 2.0F, -floatValue10 / 2.0F);
               renderManager2.invoke7(intValue2, 0.0F, 0.0F, floatValue10, floatValue10);
               renderManager2.invoke57();
               renderManager2.invoke64();
               renderManager2.invoke57();
               renderManager2.invoke57();
            }

            if (this.schetchikPopnutyhTotemovVraga.isEnabled()) {
               int intValue3 = IrcWebSocketClient.VALUES_BY_KEY_5.getOrDefault(string, 0);
               if (intValue3 > 0) {
                  String text7 = intValue3 + " тотемов";
                  float floatValue14 = 22.0F;
                  float floatValue15 = RenderManager.resolve7(FontRegistry.fontObject, text7, floatValue14).floatValue;
                  renderManager2.invoke69(FontRegistry.fontObject, floatValue8 - floatValue15 / 2.0F, floatValue9 - 5.0F, floatValue14, text7, this.compute(Color.WHITE.getRGB(), i));
               }
            }
         }
      }
   }

   private int compute(int i, float f) {
      int intValue4 = i >> 24 & 0xFF;
      int intValue5 = i >> 16 & 0xFF;
      int intValue6 = i >> 8 & 0xFF;
      int intValue7 = i & 0xFF;
      intValue4 = (int)(intValue4 * f);
      return RenderManager.RenderManagerState.compute32(intValue5, intValue6, intValue7, intValue4);
   }

   private int compute2(Identifier identifier) {
      TextureManager textureManager = CLIENT.getTextureManager();
      if (textureManager == null) {
         return -1;
      } else {
         AbstractTexture abstractTexture = textureManager.getTexture(identifier);
         if (abstractTexture == null) {
            return -1;
         } else if (abstractTexture.getGlTexture() instanceof GlTexture glTexture) {
            int intValue8 = glTexture.getGlId();
            return intValue8 > 0 ? intValue8 : -1;
         } else {
            return -1;
         }
      }
   }
}
