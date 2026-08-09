package ru.metaculture.protection;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import lombok.Generated;
import ru.metaculture.sdk.Compile;
import ru.metaculture.sdk.Loader;

public final class TelegramApi {
   private static String text = "";
   private static String text2 = "";

   @Compile
   public static void invoke(String string, String string2) {
      text = string == null ? "" : string.trim();
      text2 = string2 == null ? "" : string2.trim();
   }

   public static boolean check() {
      return text != null && !text.isEmpty() && text2 != null && !text2.isEmpty();
   }

   public static void invoke2(String string) {
      if (!check()) {
         System.out.println("[TelegramApi] Not configured");
      } else {
         try {
            String endpoint = "https://api.telegram.org/bot" + text + "/sendMessage";
            String requestBody = "chat_id=" + text2 + "&text=" + URLEncoder.encode(string, StandardCharsets.UTF_8);
            URL url = new URL(endpoint);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpURLConnection.setConnectTimeout(6000);
            httpURLConnection.setReadTimeout(8000);

            try (OutputStream outputStream = httpURLConnection.getOutputStream()) {
               outputStream.write(requestBody.getBytes(StandardCharsets.UTF_8));
            }

            httpURLConnection.getInputStream().close();
         } catch (Exception exception) {
            exception.printStackTrace();
         }
      }
   }

   @Generated
   private TelegramApi() {
      throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
   }

   static {
      Loader.initialize();
   }
}
