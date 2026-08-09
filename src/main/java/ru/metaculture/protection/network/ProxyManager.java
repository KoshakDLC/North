package ru.metaculture.protection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.proxy.ProxyConnectionEvent;
import io.netty.handler.proxy.ProxyHandler;
import io.netty.handler.proxy.Socks4ProxyHandler;
import io.netty.handler.proxy.Socks5ProxyHandler;
import java.io.File;
import java.io.FileReader;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.Locale;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ProxyManager {
   public static final String SOCKS4 = "Socks4";
   public static final String SOCKS5 = "Socks5";
   private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
   private static final Pattern PATTERN = Pattern.compile(
      "(?i)(?:socks\\s*[45]|so+cks?\\s*[45])?\\s*(?:://)?([A-Za-z0-9._~%+\\-]+):([^\\s@]+)@([A-Za-z0-9.\\-]+):(\\d{1,5})"
   );
   private static final Pattern PATTERN_2 = Pattern.compile(
      "(?i)(?:socks\\s*[45]|so+cks?\\s*[45])?\\s*(?:://)?([A-Za-z0-9.\\-]+):(\\d{1,5}):([^\\s:]+):([^\\s]+)"
   );
   private static final Pattern PATTERN_3 = Pattern.compile("(?i)(?<![A-Za-z0-9._:-])([A-Za-z0-9.\\-]+):(\\d{1,5})(?![A-Za-z0-9._:-])");
   public static volatile String text = "";
   public static volatile String text2 = "";
   public static volatile String socks5 = "Socks5";
   public static volatile String text3 = "";
   public static volatile String text4 = "";
   public static volatile boolean flag = false;
   private static volatile boolean flag2;

   private ProxyManager() {
   }

   public static synchronized void invoke() {
      if (!flag2) {
         flag2 = true;
         File file2 = resolve16();
         if (file2 != null && file2.exists() && file2.isFile()) {
            try {
               try (FileReader fileReader = new FileReader(file2, StandardCharsets.UTF_8)) {
                  JsonElement jsonElement = JsonParser.parseReader(fileReader);
                  if (jsonElement != null && jsonElement.isJsonObject()) {
                     JsonObject jsonObject2 = jsonElement.getAsJsonObject();
                     flag = check3(jsonObject2, "enabled", false);
                     socks5 = resolve7(resolve17(jsonObject2, "type", "Socks5"));
                     text = resolve10(resolve17(jsonObject2, "host", resolve17(jsonObject2, "ip", "")));
                     text2 = resolve11(resolve17(jsonObject2, "port", ""));
                     text3 = resolve18(resolve17(jsonObject2, "username", ""));
                     text4 = resolve18(resolve17(jsonObject2, "password", ""));
                     return;
                  }
               }
            } catch (Throwable exception) {
            }
         }
      }
   }

   public static synchronized void invoke2() {
      flag2 = true;

      try {
         File file3 = resolve16();
         if (file3 == null) {
            return;
         }

         JsonObject jsonObject3 = new JsonObject();
         jsonObject3.addProperty("enabled", flag);
         jsonObject3.addProperty("type", resolve7(socks5));
         jsonObject3.addProperty("host", resolve10(text));
         jsonObject3.addProperty("port", resolve11(text2));
         jsonObject3.addProperty("username", resolve18(text3));
         jsonObject3.addProperty("password", resolve18(text4));
         invoke4(file3, GSON.toJson(jsonObject3).getBytes(StandardCharsets.UTF_8));
      } catch (Throwable exception2) {
      }
   }

   public static synchronized void invoke3(ProxyManager.ProxyManagerEntry proxyManagerEntry) {
      if (proxyManagerEntry != null) {
         flag2 = true;
         flag = proxyManagerEntry.enabled();
         socks5 = resolve7(proxyManagerEntry.type());
         text = resolve10(proxyManagerEntry.host());
         text2 = resolve11(proxyManagerEntry.port());
         text3 = resolve18(proxyManagerEntry.username()).trim();
         text4 = resolve18(proxyManagerEntry.password());
         invoke2();
      }
   }

   public static ProxyManager.ProxyManagerEntry resolve() {
      invoke();
      return new ProxyManager.ProxyManagerEntry(
         flag,
         resolve7(socks5),
         resolve10(text),
         resolve11(text2),
         resolve18(text3).trim(),
         resolve18(text4)
      );
   }

   public static ProxyHandler resolve2() {
      return resolve3(resolve());
   }

   public static ProxyHandler resolve3(ProxyManager.ProxyManagerEntry proxyManagerEntry2) {
      ProxyManager.ProxyManagerEntry proxyManagerEntry3 = resolve9(proxyManagerEntry2);
      if (proxyManagerEntry3.enabled() && resolve5(proxyManagerEntry3, true) == null) {
         InetSocketAddress inetSocketAddress = new InetSocketAddress(proxyManagerEntry3.host(), proxyManagerEntry3.portInt());
         if (proxyManagerEntry3.isSocks4()) {
            String text = resolve12(proxyManagerEntry3.username());
            return text == null ? new Socks4ProxyHandler(inetSocketAddress) : new Socks4ProxyHandler(inetSocketAddress, text);
         } else {
            String text2 = resolve12(proxyManagerEntry3.username());
            return text2 == null ? new Socks5ProxyHandler(inetSocketAddress) : new Socks5ProxyHandler(inetSocketAddress, text2, proxyManagerEntry3.password());
         }
      } else {
         return null;
      }
   }

   public static CompletableFuture<ProxyManager.ProxyManagerResult> resolve4(ProxyManager.ProxyManagerEntry proxyManagerEntry4, String string, int i, int j) {
      final ProxyManager.ProxyManagerEntry proxyManagerEntry5 = resolve9(proxyManagerEntry4).withEnabled(true);
      String text3 = resolve5(proxyManagerEntry5, true);
      if (text3 != null) {
         return CompletableFuture.completedFuture(new ProxyManager.ProxyManagerResult(false, 0L, text3));
      } else {
         final CompletableFuture completableFuture = new CompletableFuture();
         final NioEventLoopGroup nioEventLoopGroup = new NioEventLoopGroup(1, runnable -> {
            Thread thread = new Thread(runnable, "Wild Proxy Test");
            thread.setDaemon(true);
            return thread;
         });
         final long longValue = System.nanoTime();

         try {
            Bootstrap bootstrap = (Bootstrap)((Bootstrap)((Bootstrap)((Bootstrap)new Bootstrap().group(nioEventLoopGroup)).channel(NioSocketChannel.class))
                  .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, j))
               .handler(new ChannelInitializer<SocketChannel>() {
                  protected void initChannel(SocketChannel socketChannel) {
                     ProxyHandler proxyHandler = ProxyManager.resolve3(proxyManagerEntry5);
                     if (proxyHandler == null) {
                        throw new IllegalStateException("Proxy config is invalid");
                     } else {
                        proxyHandler.setConnectTimeoutMillis(j);
                        socketChannel.pipeline().addFirst("wild_proxy_test", proxyHandler);
                        socketChannel.pipeline().addLast("wild_proxy_result", new ChannelInboundHandlerAdapter() {
                           public void userEventTriggered(ChannelHandlerContext channelHandlerContext, Object object) throws Exception {
                              if (object instanceof ProxyConnectionEvent) {
                                 long longValue2 = Math.max(1L, (System.nanoTime() - longValue) / 1000000L);
                                 completableFuture.complete(new ProxyManager.ProxyManagerResult(true, longValue2, "OK"));
                                 channelHandlerContext.close();
                                 nioEventLoopGroup.shutdownGracefully();
                              } else {
                                 super.userEventTriggered(channelHandlerContext, object);
                              }
                           }

                           public void exceptionCaught(ChannelHandlerContext channelHandlerContext, Throwable throwable) {
                              long longValue3 = Math.max(1L, (System.nanoTime() - longValue) / 1000000L);
                              completableFuture.complete(new ProxyManager.ProxyManagerResult(false, longValue3, ProxyManager.resolve15(throwable)));
                              channelHandlerContext.close();
                              nioEventLoopGroup.shutdownGracefully();
                           }

                           public void channelInactive(ChannelHandlerContext channelHandlerContext) throws Exception {
                              long var2x = Math.max(1L, (System.nanoTime() - longValue) / 1000000L);
                              completableFuture.complete(new ProxyManager.ProxyManagerResult(false, var2x, "Connection closed"));
                              nioEventLoopGroup.shutdownGracefully();
                              super.channelInactive(channelHandlerContext);
                           }
                        });
                     }
                  }
               });
            ChannelFuture channelFuture2 = bootstrap.connect(InetSocketAddress.createUnresolved(string, i));
            channelFuture2.addListener((ChannelFutureListener)channelFuture -> {
               if (!channelFuture.isSuccess()) {
                  long var5x = Math.max(1L, (System.nanoTime() - longValue) / 1000000L);
                  boolean var13x = false ;

                  try {
                     var13x = true;
                     completableFuture.complete(new ProxyManager.ProxyManagerResult(false, var5x, resolve15(channelFuture.cause())));
                     var13x = false;
                  } finally {
                     if (var13x) {
                        try {
                           channelFuture.channel().close();
                        } catch (Throwable var14x) {
                        }

                        nioEventLoopGroup.shutdownGracefully();
                     }
                  }

                  try {
                     channelFuture.channel().close();
                  } catch (Throwable exception3) {
                  }

                  nioEventLoopGroup.shutdownGracefully();
               }
            });
            nioEventLoopGroup.schedule(() -> {
               if (completableFuture.complete(new ProxyManager.ProxyManagerResult(false, j, "Timed out"))) {
                  try {
                     channelFuture2.channel().close();
                  } catch (Throwable var5x) {
                  }

                  nioEventLoopGroup.shutdownGracefully();
               }
            }, j + 1000L, TimeUnit.MILLISECONDS);
         } catch (Throwable exception4) {
            long longValue4 = Math.max(1L, (System.nanoTime() - longValue) / 1000000L);
            completableFuture.complete(new ProxyManager.ProxyManagerResult(false, longValue4, resolve15(exception4)));
            nioEventLoopGroup.shutdownGracefully();
         }

         return completableFuture;
      }
   }

   public static String resolve5(ProxyManager.ProxyManagerEntry proxyManagerEntry6, boolean bl) {
      ProxyManager.ProxyManagerEntry proxyManagerEntry7 = resolve9(proxyManagerEntry6);
      if (!bl && !proxyManagerEntry7.enabled() && proxyManagerEntry7.host().isBlank() && proxyManagerEntry7.port().isBlank()) {
         return null;
      } else if (proxyManagerEntry7.host().isBlank()) {
         return "Proxy host is empty";
      } else if (!check(proxyManagerEntry7.host())) {
         return "Proxy host has invalid characters";
      } else {
         int intValue = compute(proxyManagerEntry7.port());
         if (intValue <= 0) {
            return "Proxy port is invalid";
         } else {
            return proxyManagerEntry7.isSocks5() && !proxyManagerEntry7.password().isBlank() && proxyManagerEntry7.username().isBlank() ? "SOCKS5 username is empty" : null;
         }
      }
   }

   public static ProxyManager.ProxyManagerEntry2 resolve6(String string) {
      String text4 = resolve18(string).trim();
      if (text4.isEmpty()) {
         return ProxyManager.ProxyManagerEntry2.empty();
      } else {
         String text5 = resolve8(text4, "Socks5");
         Matcher matcher = PATTERN.matcher(text4);
         if (matcher.find()) {
            return new ProxyManager.ProxyManagerEntry2(text5, matcher.group(3), matcher.group(4), resolve14(matcher.group(1)), resolve14(matcher.group(2)));
         } else {
            Matcher matcher2 = PATTERN_2.matcher(text4);
            if (matcher2.find()) {
               return new ProxyManager.ProxyManagerEntry2(text5, matcher2.group(1), matcher2.group(2), resolve14(matcher2.group(3)), resolve14(matcher2.group(4)));
            } else {
               String text6 = "";
               String text7 = "";
               String text8 = "";
               String text9 = "";
               String[] texts = text4.replace("\r", "").split("\n");

               for (String text10 : texts) {
                  String text11 = text10.trim();
                  String text12 = text11.toLowerCase(Locale.ROOT);
                  String text13 = resolve13(text11);
                  if (!text13.isBlank()) {
                     matcher2 = PATTERN_2.matcher(text13);
                     if (matcher2.find()) {
                        return new ProxyManager.ProxyManagerEntry2(text5, matcher2.group(1), matcher2.group(2), resolve14(matcher2.group(3)), resolve14(matcher2.group(4)));
                     }

                     if (text12.contains("wexside")) {
                        ProxyManager.ProxyManagerEntry2 proxyManagerEntry22 = resolve6(text13);
                        if (!proxyManagerEntry22.host().isBlank()) {
                           return proxyManagerEntry22.withType(text5);
                        }
                     }

                     if (text12.contains("login") || text12.contains("username") || text12.contains("логин")) {
                        text8 = text13.trim();
                     } else if (text12.contains("password") || text12.contains("пароль")) {
                        text9 = text13.trim();
                     } else if (text12.contains("port") || text12.contains("порт")) {
                        text7 = resolve11(text13);
                     } else if (text12.contains("proxy") || text12.contains("прокси")) {
                        Matcher matcher3 = PATTERN_3.matcher(text13);
                        if (matcher3.find()) {
                           text6 = matcher3.group(1);
                           text7 = matcher3.group(2);
                        }
                     } else if (text12.matches(".*\\bip\\b.*")) {
                        text6 = resolve10(text13);
                     }
                  }
               }

               if (text6.isBlank() || text7.isBlank()) {
                  Matcher matcher4 = PATTERN_3.matcher(text4);
                  if (matcher4.find()) {
                     text6 = matcher4.group(1);
                     text7 = matcher4.group(2);
                  }
               }

               if (text8.isBlank() && text9.isBlank() && !text6.isBlank() && text4.contains("@")) {
                  matcher = PATTERN.matcher(text4);
                  if (matcher.find()) {
                     text8 = resolve14(matcher.group(1));
                     text9 = resolve14(matcher.group(2));
                  }
               }

               return new ProxyManager.ProxyManagerEntry2(text5, resolve10(text6), resolve11(text7), text8, text9);
            }
         }
      }
   }

   public static String resolve7(String string) {
      String text14 = resolve18(string).trim().toLowerCase(Locale.ROOT).replace(" ", "");
      return text14.contains("4") ? "Socks4" : "Socks5";
   }

   private static String resolve8(String string, String string2) {
      String text15 = resolve18(string).toLowerCase(Locale.ROOT).replace(" ", "");
      if (text15.contains("socks4") || text15.contains("sock4") || text15.contains("soock4")) {
         return "Socks4";
      } else {
         return !text15.contains("socks5") && !text15.contains("sock5") && !text15.contains("soock5") ? resolve7(string2) : "Socks5";
      }
   }

   private static ProxyManager.ProxyManagerEntry resolve9(ProxyManager.ProxyManagerEntry proxyManagerEntry8) {
      return proxyManagerEntry8 == null
         ? new ProxyManager.ProxyManagerEntry(false, "Socks5", "", "", "", "")
         : new ProxyManager.ProxyManagerEntry(
            proxyManagerEntry8.enabled(),
            resolve7(proxyManagerEntry8.type()),
            resolve10(proxyManagerEntry8.host()),
            resolve11(proxyManagerEntry8.port()),
            resolve18(proxyManagerEntry8.username()).trim(),
            resolve18(proxyManagerEntry8.password())
         );
   }

   private static String resolve10(String string) {
      String text16 = resolve18(string).trim();
      int intValue2 = text16.indexOf("://");
      if (intValue2 >= 0) {
         text16 = text16.substring(intValue2 + 3);
      }

      int intValue3 = text16.lastIndexOf(64);
      if (intValue3 >= 0 && intValue3 + 1 < text16.length()) {
         text16 = text16.substring(intValue3 + 1);
      }

      int intValue4 = text16.indexOf(47);
      if (intValue4 >= 0) {
         text16 = text16.substring(0, intValue4);
      }

      if (text16.startsWith("[")) {
         int intValue5 = text16.indexOf(93);
         if (intValue5 > 0) {
            return text16.substring(1, intValue5).trim();
         }
      }

      int intValue6 = text16.lastIndexOf(58);
      if (intValue6 > 0 && text16.indexOf(58) == intValue6 && check2(text16.substring(intValue6 + 1))) {
         text16 = text16.substring(0, intValue6);
      }

      return text16.trim();
   }

   private static boolean check(String string) {
      String text17 = resolve18(string);
      if (text17.length() > 255) {
         return false;
      } else {
         for (int intValue7 = 0; intValue7 < text17.length(); intValue7++) {
            char character = text17.charAt(intValue7);
            if (!Character.isLetterOrDigit(character) && character != '.' && character != '-' && character != '_' && character != ':') {
               return false;
            }
         }

         return true;
      }
   }

   private static String resolve11(String string) {
      String text18 = resolve18(string).trim();
      StringBuilder stringBuilder = new StringBuilder(5);

      for (int intValue8 = 0; intValue8 < text18.length() && stringBuilder.length() < 5; intValue8++) {
         char character2 = text18.charAt(intValue8);
         if (character2 >= '0' && character2 <= '9') {
            stringBuilder.append(character2);
         }
      }

      return stringBuilder.toString();
   }

   private static boolean check2(String string) {
      String text19 = resolve18(string).trim();
      if (!text19.isEmpty() && text19.length() <= 5) {
         for (int intValue9 = 0; intValue9 < text19.length(); intValue9++) {
            char character3 = text19.charAt(intValue9);
            if (character3 < '0' || character3 > '9') {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   static int compute(String string) {
      try {
         int intValue10 = Integer.parseInt(resolve18(string).trim());
         return intValue10 > 0 && intValue10 <= 65535 ? intValue10 : -1;
      } catch (Throwable exception5) {
         return -1;
      }
   }

   private static String resolve12(String string) {
      String text20 = resolve18(string).trim();
      return text20.isEmpty() ? null : text20;
   }

   private static String resolve13(String string) {
      int intValue11 = string.indexOf(58);
      return intValue11 >= 0 && intValue11 + 1 < string.length() ? string.substring(intValue11 + 1).trim() : "";
   }

   private static String resolve14(String string) {
      String text21 = resolve18(string);

      try {
         return URLDecoder.decode(text21.replace("+", "%2B"), StandardCharsets.UTF_8);
      } catch (Throwable exception6) {
         return text21;
      }
   }

   static String resolve15(Throwable throwable) {
      for (Throwable exception7 = throwable; exception7 != null; exception7 = exception7.getCause()) {
         String text22 = exception7.getMessage();
         if (text22 != null && !text22.isBlank()) {
            String text23 = text22.replace('\n', ' ').replace('\r', ' ').trim();
            String text24 = text23.toLowerCase(Locale.ROOT);
            if (!text24.contains("authstatus") && !text24.contains("authentication")) {
               return text23;
            }

            return "SOCKS5 auth rejected: check login/password";
         }
      }

      return throwable == null ? "Unknown error" : throwable.getClass().getSimpleName();
   }

   private static File resolve16() {
      try {
         if (WildClient.INSTANCE != null && WildClient.INSTANCE.file != null) {
            return new File(WildClient.INSTANCE.file, "proxy.json");
         }
      } catch (Throwable exception8) {
      }

      return new File(WildClient.getFILE(), "proxy.json");
   }

   private static void invoke4(File file, byte[] bs) throws Exception {
      Path path = file.toPath();
      Path path2 = path.getParent();
      if (path2 != null) {
         Files.createDirectories(path2);
      }

      Path path3 = path.resolveSibling(path.getFileName() + ".tmp");

      try (FileChannel fileChannel = FileChannel.open(path3, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE)) {
         ByteBuffer byteBuffer = ByteBuffer.wrap(bs);

         while (byteBuffer.hasRemaining()) {
            fileChannel.write(byteBuffer);
         }

         fileChannel.force(true);
      }

      try {
         Files.move(path3, path, StandardCopyOption.REPLACE_EXISTING, StandardCopyOption.ATOMIC_MOVE);
      } catch (AtomicMoveNotSupportedException atomicMoveNotSupportedException) {
         Files.move(path3, path, StandardCopyOption.REPLACE_EXISTING);
      }
   }

   private static String resolve17(JsonObject jsonObject, String string, String string2) {
      try {
         JsonElement jsonElement2 = jsonObject.get(string);
         return jsonElement2 != null && !jsonElement2.isJsonNull() ? jsonElement2.getAsString() : string2;
      } catch (Throwable exception9) {
         return string2;
      }
   }

   private static boolean check3(JsonObject jsonObject, String string, boolean bl) {
      try {
         JsonElement jsonElement3 = jsonObject.get(string);
         return jsonElement3 != null && !jsonElement3.isJsonNull() ? jsonElement3.getAsBoolean() : bl;
      } catch (Throwable exception10) {
         return bl;
      }
   }

   private static String resolve18(String string) {
      return string == null ? "" : string;
   }

   public record ProxyManagerEntry(boolean enabled, String type, String host, String port, String username, String password) {
      public boolean isSocks4() {
         return "Socks4".equals(ProxyManager.resolve7(this.type));
      }

      public boolean isSocks5() {
         return !this.isSocks4();
      }

      public int portInt() {
         return ProxyManager.compute(this.port);
      }

      public ProxyManager.ProxyManagerEntry withEnabled(boolean bl) {
         return new ProxyManager.ProxyManagerEntry(bl, this.type, this.host, this.port, this.username, this.password);
      }
   }

   public record ProxyManagerEntry2(String type, String host, String port, String username, String password) {
      public static ProxyManager.ProxyManagerEntry2 empty() {
         return new ProxyManager.ProxyManagerEntry2("Socks5", "", "", "", "");
      }

      public ProxyManager.ProxyManagerEntry2 withType(String string) {
         return new ProxyManager.ProxyManagerEntry2(ProxyManager.resolve7(string), this.host, this.port, this.username, this.password);
      }
   }

   public record ProxyManagerResult(boolean success, long millis, String message) {
   }
}
