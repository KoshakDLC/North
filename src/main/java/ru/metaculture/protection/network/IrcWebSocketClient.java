package ru.metaculture.protection;

import java.awt.Color;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;
import org.json.JSONObject;
import ru.metaculture.sdk.Loader;

public class IrcWebSocketClient extends WebSocketClient {
   public static IrcWebSocketClient instance;
   private static String text = "";
   private static volatile boolean flag;
   private static volatile Thread thread;
   private static final Map<String, String> VALUES_BY_KEY = new HashMap<>();
   public static final Map<String, String> VALUES_BY_KEY_2 = new ConcurrentHashMap<>();
   public static final Map<String, IrcWebSocketClient.IrcWebSocketClientState> VALUES_BY_KEY_3 = new ConcurrentHashMap<>();
   public static final Map<String, IrcWebSocketClient.IrcWebSocketClientState2> VALUES_BY_KEY_4 = new ConcurrentHashMap<>();
   public static final Map<String, Integer> VALUES_BY_KEY_5 = new ConcurrentHashMap<>();
   public static final Map<String, Long> VALUES_BY_KEY_6 = new ConcurrentHashMap<>();
   private static String text2 = null;

   public static String resolve() {
      return "prota_$crashdammi1337";
   }

   public IrcWebSocketClient(URI uRI) {
      super(uRI);
      flag = false;
      instance = this;
   }

   public static void invoke() {
      flag = true;
      Thread connectionThread = thread;
      thread = null;
      if (connectionThread != null) {
         connectionThread.interrupt();
      }

      IrcWebSocketClient ircWebSocketClient = instance;
      instance = null;
      text = "";
      if (ircWebSocketClient != null) {
         try {
            ircWebSocketClient.closeBlocking();
         } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
         } catch (Throwable exception2) {
         }
      }
   }

   public void onOpen(ServerHandshake serverHandshake) {
      System.out.println("[IRC] Успешно подключено к серверу! Ждем проверку...");
      invoke5(() -> {
         if (MinecraftAccessor.a_.player != null) {
            MinecraftAccessor.a_.player.sendMessage(Text.literal("§a[IRC] Соединение установлено, проверка..."), false);
         }
      });
   }

   public void invoke2() {
      if (this.isOpen() && MinecraftAccessor.a_.getSession() != null) {
         String username = MinecraftAccessor.a_.getSession().getUsername();
         if (!username.equals(text2)) {
            text2 = username;

            try {
               JSONObject jsonObject = new JSONObject();
               jsonObject.put("type", "handshake");
               jsonObject.put("user", username);
               jsonObject.put(
                  "client", WildClient.INSTANCE != null && WildClient.INSTANCE.resolve4() != null ? WildClient.INSTANCE.resolve4() : "LitkaFree"
               );
               this.send(jsonObject.toString());
            } catch (Exception exception3) {
            }
         }
      }
   }

   public void invoke3(double d, double e, double f, String string, float g, String string2, boolean bl) {
      if (this.isOpen() && MinecraftAccessor.a_.getSession() != null) {
         try {
            JSONObject jsonObject2 = new JSONObject();
            jsonObject2.put("type", "pos_sync");
            jsonObject2.put("user", MinecraftAccessor.a_.getSession().getUsername());
            jsonObject2.put("client", WildClient.INSTANCE != null && WildClient.INSTANCE.resolve4() != null ? WildClient.INSTANCE.resolve4() : "LitkaFree");
            jsonObject2.put("x", d);
            jsonObject2.put("y", e);
            jsonObject2.put("z", f);
            jsonObject2.put("dim", string);
            jsonObject2.put("hp", g);
            jsonObject2.put("server", resolve());
            jsonObject2.put("anarchy", string2);
            jsonObject2.put("pvp", bl);
            this.send(jsonObject2.toString());
         } catch (Exception exception4) {
         }
      }
   }

   public void onMessage(String string) {
      invoke5(() -> this.invoke4(string));
   }

   private void invoke4(String string) {
      try {
         JSONObject jsonObject3 = new JSONObject(string);
         if (jsonObject3.has("sys_msg")) {
            if (MinecraftAccessor.a_.player != null) {
               MinecraftAccessor.a_.player.sendMessage(Text.literal(jsonObject3.getString("sys_msg")), false);
            }

            return;
         }

         String text2 = jsonObject3.has("type") ? jsonObject3.getString("type") : "";
         if ("challenge".equals(text2)) {
            String text3 = jsonObject3.getString("salt");
            String text4 = "AiJgW2femCr4LFbNEqbMWVYX3SblusdD1TbUbPeoVarZCRQQnZ";
            String text5 = resolve3();
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-256");
            String text6 = text3 + text4 + text5;
            byte[] byteValues = messageDigest.digest(text6.getBytes(StandardCharsets.UTF_8));
            StringBuilder stringBuilder = new StringBuilder();

            for (byte byteValue : byteValues) {
               String text7 = Integer.toHexString(255 & byteValue);
               if (text7.length() == 1) {
                  stringBuilder.append('0');
               }

               stringBuilder.append(text7);
            }

            text = MinecraftAccessor.a_.getSession() != null ? MinecraftAccessor.a_.getSession().getUsername() : "Unknown";
            JSONObject jsonObject4 = new JSONObject();
            jsonObject4.put("type", "handshake");
            jsonObject4.put("user", text);
            jsonObject4.put("client", WildClient.INSTANCE != null && WildClient.INSTANCE.resolve4() != null ? WildClient.INSTANCE.resolve4() : "LitkaFree");
            jsonObject4.put("hwid", text5);
            jsonObject4.put("hash", stringBuilder.toString());
            this.send(jsonObject4.toString());
            if (MinecraftAccessor.a_.player != null) {
               MinecraftAccessor.a_.player.sendMessage(Text.literal("§a[IRC] Успешно авторизовано!"), false);
            }

            return;
         }

         if ("sync".equals(text2)) {
            JSONObject jsonObject5 = jsonObject3.getJSONObject("users");
            VALUES_BY_KEY_2.clear();
            Iterator iterator = jsonObject5.keys();

            while (iterator.hasNext()) {
               String text8 = (String)iterator.next();
               VALUES_BY_KEY_2.put(text8, jsonObject5.getString(text8));
            }

            return;
         }

         if ("pos_sync".equals(text2)) {
            String text9 = jsonObject3.getString("user");
            if (this.check(text9)) {
               return;
            }

            String text10 = jsonObject3.getString("client");
            double doubleValue = jsonObject3.getDouble("x");
            double doubleValue2 = jsonObject3.getDouble("y");
            double doubleValue3 = jsonObject3.getDouble("z");
            String text11 = jsonObject3.getString("dim");
            float floatValue = (float)jsonObject3.getDouble("hp");
            String text12 = jsonObject3.has("server") ? jsonObject3.getString("server") : "unknown";
            String text13 = jsonObject3.has("anarchy") ? jsonObject3.getString("anarchy") : "N/A";
            boolean flag = jsonObject3.has("pvp") && jsonObject3.getBoolean("pvp");
            String text14 = text9.toLowerCase();
            if (VALUES_BY_KEY_3.containsKey(text14)) {
               VALUES_BY_KEY_3.get(text14).invoke(doubleValue, doubleValue2, doubleValue3, text11, floatValue, text12, text13, flag);
            } else {
               VALUES_BY_KEY_3.put(text14, new IrcWebSocketClient.IrcWebSocketClientState(text9, text10, doubleValue, doubleValue2, doubleValue3, text11, floatValue, text12, text13, flag));
            }

            return;
         }

         if ("target_sync".equals(text2)) {
            String text15 = jsonObject3.getString("user");
            if (this.check(text15)) {
               return;
            }

            String text16 = jsonObject3.getString("target");
            String text17 = jsonObject3.has("server") ? jsonObject3.getString("server") : "unknown";
            double doubleValue4 = jsonObject3.has("x") ? jsonObject3.getDouble("x") : 0.0;
            double doubleValue5 = jsonObject3.has("y") ? jsonObject3.getDouble("y") : 0.0;
            double doubleValue6 = jsonObject3.has("z") ? jsonObject3.getDouble("z") : 0.0;
            if (text16.isEmpty()) {
               VALUES_BY_KEY_4.remove(text15);
            } else if (VALUES_BY_KEY_4.containsKey(text15)) {
               VALUES_BY_KEY_4.get(text15).invoke(doubleValue4, doubleValue5, doubleValue6, text17, text16);
            } else {
               VALUES_BY_KEY_4.put(text15, new IrcWebSocketClient.IrcWebSocketClientState2(text16, text17, doubleValue4, doubleValue5, doubleValue6));
            }

            return;
         }

         if ("totem_pop".equals(text2)) {
            String text18 = jsonObject3.has("attacker") ? jsonObject3.getString("attacker") : "";
            if (this.check(text18)) {
               return;
            }

            String text19 = jsonObject3.getString("victim");
            int intValue = jsonObject3.getInt("count");
            String text20 = jsonObject3.has("server") ? jsonObject3.getString("server") : "unknown";
            if (text20.equals(resolve())) {
               VALUES_BY_KEY_5.put(text19, intValue);
               VALUES_BY_KEY_6.put(text19, System.currentTimeMillis());
            }

            return;
         }

         if ("chat".equals(text2)) {
            String text21 = jsonObject3.has("user") ? jsonObject3.getString("user") : "Unknown";
            String text22 = jsonObject3.has("msg") ? jsonObject3.getString("msg") : "";
            String text23 = jsonObject3.has("client") ? jsonObject3.getString("client") : "LitkaFree";
            String text24 = jsonObject3.has("role") ? jsonObject3.getString("role") : "User";
            VALUES_BY_KEY_2.put(text21, text23);
            String text25 = DiscordRpcManager.text != null ? DiscordRpcManager.text : "";
            String text26 = MinecraftAccessor.a_.getSession() != null ? MinecraftAccessor.a_.getSession().getUsername() : "Unknown";
            if (VALUES_BY_KEY.containsKey(text25)) {
               text26 = VALUES_BY_KEY.get(text25);
            }

            boolean flag2 = text22.toLowerCase().contains("@" + text26.toLowerCase()) || text22.toLowerCase().contains(text26.toLowerCase());
            MutableText mutableText = Text.empty();
            mutableText.append(Text.literal("§8["));
            if (text23.toLowerCase().contains("wild")) {
               mutableText.append(this.resolve2(text23, Color.DARK_GRAY, Color.WHITE));
            } else if (text23.toLowerCase().contains("nightix")) {
               mutableText.append(this.resolve2(text23, Color.WHITE, new Color(85, 85, 255)));
            } else {
               mutableText.append(Text.literal(text23).formatted(Formatting.AQUA));
            }

            mutableText.append(Text.literal("§8] "));
            switch (text24) {
               case "Developer":
                  mutableText.append(Text.literal("§8["))
                     .append(this.resolve2("Developer", new Color(170, 0, 255), new Color(255, 85, 255)))
                     .append(Text.literal("§8] "));
                  break;
               case "Admin":
                  mutableText.append(Text.literal("§8[")).append(this.resolve2("Admin", new Color(255, 85, 85), new Color(170, 0, 0))).append(Text.literal("§8] "));
            }

            switch (text24) {
               case "Developer":
                  mutableText.append(this.resolve2(text21, new Color(85, 255, 255), new Color(85, 85, 255)));
                  break;
               case "Admin":
                  mutableText.append(Text.literal("§c" + text21));
                  break;
               default:
                  mutableText.append(Text.literal("§7" + text21));
            }

            mutableText.append(Text.literal(" §8» "));
            if (flag2) {
               mutableText.append(this.resolve2(text22, new Color(85, 255, 85), new Color(255, 170, 0)));
               if (MinecraftAccessor.a_.player != null) {
                  MinecraftAccessor.a_.player.playSound(SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F);
               }
            } else {
               mutableText.append(Text.literal("§f" + text22));
            }

            if (MinecraftAccessor.a_.player != null) {
               MinecraftAccessor.a_.player.sendMessage(mutableText, false);
            }
         }
      } catch (Exception exception5) {
         System.err.println("[IRC] Ошибка парсинга пакета: " + exception5.getMessage());
         exception5.printStackTrace();
      }
   }

   public void onClose(int i, String string, boolean bl) {
      if (!flag) {
         if (MinecraftAccessor.a_.player != null) {
         }

         Thread thread2 = new Thread(() -> {}, "IRC-Reconnect-Thread");
         thread2.setDaemon(true);
         thread = thread2;
         thread2.start();
      }
   }

   public void onError(Exception exception) {
   }

   private static void invoke5(Runnable runnable) {
      MinecraftClient client = MinecraftClient.getInstance();
      if (client != null && !client.isOnThread()) {
         client.execute(runnable);
      } else {
         runnable.run();
      }
   }

   public void invoke6(String string, String string2) {
   }

   private MutableText resolve2(String string, Color color, Color color2) {
      MutableText mutableText2 = Text.empty();
      int intValue2 = string.length();

      for (int intValue3 = 0; intValue3 < intValue2; intValue3++) {
         float floatValue2 = intValue2 > 1 ? (float)intValue3 / (intValue2 - 1) : 0.0F;
         int intValue4 = (int)(color.getRed() * (1.0F - floatValue2) + color2.getRed() * floatValue2);
         int intValue5 = (int)(color.getGreen() * (1.0F - floatValue2) + color2.getGreen() * floatValue2);
         int intValue6 = (int)(color.getBlue() * (1.0F - floatValue2) + color2.getBlue() * floatValue2);
         TextColor textColor = TextColor.fromRgb(intValue4 << 16 | intValue5 << 8 | intValue6);
         mutableText2.append(Text.literal(String.valueOf(string.charAt(intValue3))).styled(style -> style.withColor(textColor)));
      }

      return mutableText2;
   }

   public static String resolve3() {
      if (text2 != null) {
         return text2;
      } else {
         try {
            String text27 = System.getenv("COMPUTERNAME")
               + System.getProperty("user.name")
               + System.getenv("PROCESSOR_IDENTIFIER")
               + System.getenv("PROCESSOR_LEVEL");
            MessageDigest messageDigest2 = MessageDigest.getInstance("MD5");
            byte[] byteValues2 = messageDigest2.digest(text27.getBytes());
            StringBuilder stringBuilder2 = new StringBuilder();

            for (byte byteValue2 : byteValues2) {
               stringBuilder2.append(String.format("%02X", byteValue2));
            }

            text2 = stringBuilder2.toString();
            return text2;
         } catch (Exception exception6) {
            return "FALLBACK_HWID_" + System.currentTimeMillis();
         }
      }
   }

   private boolean check(String string) {
      if (string == null || string.isEmpty()) {
         return false;
      } else if (MinecraftAccessor.a_.getSession() != null && string.equals(MinecraftAccessor.a_.getSession().getUsername())) {
         return false;
      } else {
         if (WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null) {
            WildFriends wildFriends = WildClient.INSTANCE.moduleManager.getModule(WildFriends.class);
            if (wildFriends != null && "Только у друзей".equals(wildFriends.otobrazhat.getValue())) {
               return !FriendCommand.check(string);
            }
         }

         return false;
      }
   }

   static {
      Loader.initialize();
      VALUES_BY_KEY.put("811282287772565514", "fr1zy1337");
      VALUES_BY_KEY.put("1386776511520178290", "Chaser");
      VALUES_BY_KEY.put("1142359429090648134", "safurai4ik");
   }

   public static class IrcWebSocketClientState {
      public String text;
      public String text2;
      public String text3;
      public String text4;
      public String text5;
      public boolean flag;
      public double doubleValue;
      public double doubleValue2;
      public double doubleValue3;
      public double doubleValue4;
      public double doubleValue5;
      public double doubleValue6;
      public float floatValue;
      public long timestamp;

      public IrcWebSocketClientState(String string, String string2, double d, double e, double f, String string3, float g, String string4, String string5, boolean bl) {
         this.text = string;
         this.text2 = string2;
         this.doubleValue = this.doubleValue4 = d;
         this.doubleValue2 = this.doubleValue5 = e;
         this.doubleValue3 = this.doubleValue6 = f;
         this.text3 = string3;
         this.floatValue = g;
         this.text4 = string4;
         this.text5 = string5;
         this.flag = bl;
         this.timestamp = System.currentTimeMillis();
      }

      public void invoke(double d, double e, double f, String string, float g, String string2, String string3, boolean bl) {
         this.doubleValue4 = this.doubleValue;
         this.doubleValue5 = this.doubleValue2;
         this.doubleValue6 = this.doubleValue3;
         this.doubleValue = d;
         this.doubleValue2 = e;
         this.doubleValue3 = f;
         this.text3 = string;
         this.floatValue = g;
         this.text4 = string2;
         this.text5 = string3;
         this.flag = bl;
         this.timestamp = System.currentTimeMillis();
      }
   }

   public static class IrcWebSocketClientState2 {
      public String text;
      public String text2;
      public double doubleValue;
      public double doubleValue2;
      public double doubleValue3;
      public double doubleValue4;
      public double doubleValue5;
      public double doubleValue6;
      public long timestamp;

      public IrcWebSocketClientState2(String string, String string2, double d, double e, double f) {
         this.text = string;
         this.text2 = string2;
         this.doubleValue = this.doubleValue4 = d;
         this.doubleValue2 = this.doubleValue5 = e;
         this.doubleValue3 = this.doubleValue6 = f;
         this.timestamp = System.currentTimeMillis();
      }

      public void invoke(double d, double e, double f, String string, String string2) {
         this.doubleValue4 = this.doubleValue;
         this.doubleValue5 = this.doubleValue2;
         this.doubleValue6 = this.doubleValue3;
         this.doubleValue = d;
         this.doubleValue2 = e;
         this.doubleValue3 = f;
         this.text2 = string;
         this.text = string2;
         this.timestamp = System.currentTimeMillis();
      }
   }
}
