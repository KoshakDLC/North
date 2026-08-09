package ru.metaculture.protection;

import com.mojang.logging.LogUtils;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Util;
import org.slf4j.Logger;

public final class RenderDiagnosticsTracker {
   private static final RenderDiagnosticsTracker INSTANCE = new RenderDiagnosticsTracker();
   private static final long TIMESTAMP = 2400000000L;
   private static final long TIMESTAMP_2 = 250000000L;
   private static final long TIMESTAMP_3 = 100000000L;
   private static final int INT_VALUE = 65536;
   private static final Logger LOGGER = LogUtils.getLogger();
   private final RenderStateHasher renderStateHasher = new RenderStateHasher();
   private final RenderHashSink renderHashSink = new RenderHashSink();
   private final RenderEventRingBuffer renderEventRingBuffer = new RenderEventRingBuffer();
   private final WildSnapWriter wildSnapWriter = new WildSnapWriter();
   private long timestamp;
   private long timestamp2;
   private long timestamp3;
   private long timestamp4 = -1L;
   private long timestamp5 = -1L;
   private boolean flag;
   private boolean flag2;
   private boolean flag3;
   private boolean flag4;
   private boolean flag5;
   private boolean flag6;
   private long timestamp6;
   private int intValue;
   private int intValue2;
   private int intValue3;
   private String text0x0000000000000000 = "0x0000000000000000";
   private String none = "none";
   private String none2 = "none";
   private String glClean = "GL clean";
   private String matrixFinite = "Matrix finite";
   private String ozhidanie = "ожидание";
   private String ozhidanieNone = "ожидание  none";
   private String none3 = "none";
   private String none4 = "none";
   private String ozhidanie2 = "Ожидание";
   private String injectHeadTail = "Inject HEAD/TAIL";
   private String localEncrypted = "Local encrypted";
   private String none5 = "none";
   private String none6 = "none";
   private String none7 = "none";
   private String text0 = "0";
   private String latestLog = "latest.log";
   private String latestLog2 = "latest.log";
   private final String[] text = new String[96];
   private final int[] ints = new int[96];
   private String text02 = "0";
   private String text03 = "0";
   private int intValue4;
   private int intValue5;
   private boolean flag7;

   private RenderDiagnosticsTracker() {
      this.renderHashSink.setRenderStateHasher(this.renderStateHasher);
      this.invoke28();
   }

   public static RenderDiagnosticsTracker getInstance() {
      return INSTANCE;
   }

   public void invoke() {
      if (this.flag) {
         this.invoke25(8193, 257);
      }

      this.flag = true;
      this.invoke23(257);
   }

   public void invoke2() {
      if (!this.flag) {
         this.invoke25(8193, 258);
      }

      this.invoke23(258);
      this.flag = false;
      this.invoke27();
   }

   public void invoke3() {
      this.timestamp++;
      if (this.flag2) {
         this.invoke25(8193, 513);
      }

      this.flag2 = true;
      this.invoke23(513);
      boolean flag = RenderMatrixValidator.check(this.renderStateHasher);
      if (!flag) {
         this.invoke25(12289, 513);
      }
   }

   public void invoke4() {
      if (!this.flag2) {
         this.invoke25(8193, 514);
      }

      this.invoke23(514);
      this.flag2 = false;
      ru.metaculture.protection.GlStateInspector.pollGlError();
   }

   public void invoke5() {
      if (this.flag3) {
         this.invoke25(8193, 769);
      }

      this.flag3 = true;
      this.invoke23(769);
   }

   public void invoke6() {
      if (!this.flag3) {
         this.invoke25(8193, 770);
      }

      this.invoke23(770);
      this.flag3 = false;
   }

   public void invoke7(int i, int j) {
      if (this.flag4) {
         this.invoke25(8193, 1025);
      }

      this.flag4 = true;
      this.invoke23(1025);
      this.renderStateHasher.invoke(i);
      this.renderStateHasher.invoke(j);
   }

   public void invoke8() {
      if (!this.flag4) {
         this.invoke25(8193, 1026);
      }

      this.invoke23(1026);
      this.flag4 = false;
   }

   public void invoke9() {
      if (this.flag5) {
         this.invoke25(8193, 1281);
      }

      this.flag5 = true;
      this.invoke23(1281);
   }

   public void invoke10(int i, int j, int k) {
      this.invoke23(1282);
      this.flag5 = false;
      int intValue2 = ru.metaculture.protection.GlStateInspector.getCurrentProgram();
      int intValue3 = ru.metaculture.protection.GlStateInspector.getActiveTexture();
      int intValue4 = ru.metaculture.protection.GlStateInspector.getTextureBinding2D();
      if (i != intValue2 || j != intValue3 || k != intValue4) {
         this.invoke25(4098, intValue2 ^ intValue3 ^ intValue4);
      }
   }

   public void invoke11(RenderDiagnosticSink renderDiagnosticSink) {
      if (renderDiagnosticSink != null) {
         this.renderStateHasher.invoke(renderDiagnosticSink.compute());
         renderDiagnosticSink.invoke(this.renderHashSink);
      }
   }

   public void invoke12() {
      long longValue = System.nanoTime();
      int intValue5 = (int)(this.renderStateHasher.getTimestamp() ^ longValue >>> 13 ^ 20481L);
      this.none = this.resolve(intValue5);
      this.none2 = RenderAnomalyType.label(20481);
      this.flag6 = true;
      this.intValue = intValue5;
      this.intValue2 = 20481;
      this.intValue3 = 0;
      this.timestamp6 = longValue;
      this.ozhidanie = "ожидает";
      this.ozhidanie2 = "Ручной слепок";
      this.ozhidanieNone = "pending  " + this.none;
      LOGGER.info("[WildCore] tracker={} code={} snapshot=pending", this.none, this.none2);
   }

   public void invoke13() {
      try {
         Path path = this.wildSnapWriter.resolve2();
         Util.getOperatingSystem().open(path.toFile());
      } catch (Throwable exception) {
         this.ozhidanie = "папка недоступна";
         this.ozhidanie2 = "Open folder failed";
         LOGGER.warn("[WildCore] tracker={} code=OPEN_FOLDER_FAILED", this.none);
      }
   }

   public void invoke14() {
      try {
         Path path2 = this.resolve4();
         Files.createDirectories(path2);
         Util.getOperatingSystem().open(path2.toFile());
         this.latestLog = "логи открыты";
      } catch (Throwable exception2) {
         this.latestLog = "ошибка открытия";
         LOGGER.warn("[WildCore] tracker={} code=OPEN_LOGS_FAILED", this.none);
      }
   }

   public void invoke15() {
      this.flag7 = true;
      this.timestamp3 = 0L;
      this.invoke18();
   }

   public void invoke16() {
      this.flag7 = !this.flag7;
      if (this.flag7) {
         this.timestamp3 = 0L;
         this.invoke18();
      }
   }

   public void invoke17() {
      this.flag7 = false;
   }

   public void invoke18() {
      Path path3 = this.resolve4().resolve("latest.log");
      this.latestLog2 = "latest.log";
      this.invoke28();
      if (!Files.exists(path3)) {
         this.timestamp4 = -1L;
         this.timestamp5 = -1L;
         this.latestLog = "latest.log not found";
         this.invoke31("WARN latest.log not found", 2);
      } else {
         try (RandomAccessFile randomAccessFile = new RandomAccessFile(path3.toFile(), "r")) {
            long longValue2 = randomAccessFile.length();
            this.timestamp4 = longValue2;
            this.timestamp5 = Files.getLastModifiedTime(path3).toMillis();
            int intValue6 = (int)Math.min(65536L, longValue2);
            byte[] byteValues = new byte[intValue6];
            randomAccessFile.seek(Math.max(0L, longValue2 - intValue6));
            randomAccessFile.readFully(byteValues);
            this.invoke30(new String(byteValues, StandardCharsets.UTF_8));
            this.latestLog = "loaded " + this.intValue5;
         } catch (Throwable exception3) {
            this.latestLog = "read failed";
            this.invoke31("ERROR " + exception3.getClass().getSimpleName(), 3);
            LOGGER.warn("[WildCore] tracker={} code=READ_LOG_FAILED", this.none);
         }
      }
   }

   public boolean isFlag7() {
      return this.flag7;
   }

   public void invoke19(String string, Throwable throwable) {
      this.intValue4++;
      this.none5 = resolve3(string, 96);
      this.none6 = throwable == null ? "unknown" : resolve3(throwable.getClass().getName(), 96);
      this.none7 = throwable == null ? "no throwable" : resolve3(throwable.getMessage(), 160);
      this.text0 = Integer.toString(this.intValue4);
      this.latestLog = "shader exception";
      this.invoke31(ShaderDiagnostics.resolve(this.none5, this.intValue4), 3);
      int intValue7 = 0;
      Throwable exception4 = throwable;
      if (throwable == null) {
         this.invoke31("cause[0]=unknown", 3);
         this.invoke31("message=no throwable", 3);
      }

      for (int intValue8 = 0; exception4 != null && intValue8 < 3; intValue8++) {
         this.invoke31(ShaderDiagnostics.resolve2(intValue8, exception4), 3);
         this.invoke31(ShaderDiagnostics.resolve3(exception4), 3);
         String text = ShaderDiagnostics.resolve4(exception4);
         if (!"none".equals(text)) {
            this.invoke31(text, 4);
         }

         StackTraceElement[] stackTraceElements = exception4.getStackTrace();

         for (int intValue9 = 0; intValue9 < stackTraceElements.length && intValue7 < 14; intValue9++) {
            this.invoke31(ShaderDiagnostics.resolve5(stackTraceElements[intValue9]), 3);
            intValue7++;
         }

         exception4 = exception4.getCause();
      }

      this.invoke25(24577, this.none7.hashCode());
      LOGGER.error(
         "[WildCore] tracker={} shaderStage={} exception={}", new Object[]{this.none, this.none5, this.none6, throwable}
      );
   }

   public void invoke20(String string, int i) {
      if (i != 0) {
         this.invoke25(4097, i);
         this.glClean = ru.metaculture.protection.GlStateInspector.glErrorName(i);
         this.latestLog = "OpenGL error";
         this.invoke31(ShaderDiagnostics.resolve6(string, i), 4);
         this.invoke31(ShaderDiagnostics.resolve7(), 4);
      }
   }

   public void fail(String string, Throwable throwable) {
      this.invoke19(string, throwable);
      if (throwable instanceof Error error) {
         throw error;
      } else if (throwable instanceof RuntimeException exception5) {
         throw exception5;
      } else {
         throw new IllegalStateException("WildCore shader failure at " + this.none5, throwable);
      }
   }

   public void invoke21(RenderDiagnosticsStatus renderDiagnosticsStatus) {
      if (renderDiagnosticsStatus != null) {
         renderDiagnosticsStatus.nominal = this.renderEventRingBuffer.getIntValue3() == 0 ? "Nominal" : "Anomaly";
         renderDiagnosticsStatus.text0x0000000000000000 = this.text0x0000000000000000;
         renderDiagnosticsStatus.none = this.none;
         renderDiagnosticsStatus.none2 = this.none2;
         renderDiagnosticsStatus.glClean = this.glClean;
         renderDiagnosticsStatus.matrixFinite = this.matrixFinite;
         renderDiagnosticsStatus.ozhidanie = this.ozhidanie;
         renderDiagnosticsStatus.ozhidanieNone = this.ozhidanieNone;
         renderDiagnosticsStatus.none3 = this.none3;
         renderDiagnosticsStatus.none4 = this.none4;
         renderDiagnosticsStatus.ozhidanie2 = this.ozhidanie2;
         renderDiagnosticsStatus.injectHeadTail = this.injectHeadTail;
         renderDiagnosticsStatus.localEncrypted = this.localEncrypted;
         renderDiagnosticsStatus.none5 = this.none5;
         renderDiagnosticsStatus.none6 = this.none6;
         renderDiagnosticsStatus.none7 = this.none7;
         renderDiagnosticsStatus.text0 = this.text0;
         renderDiagnosticsStatus.latestLog = this.latestLog;
         renderDiagnosticsStatus.latestLog2 = this.latestLog2;
         renderDiagnosticsStatus.text02 = this.text02;
         renderDiagnosticsStatus.text03 = this.text03;
         renderDiagnosticsStatus.intValue = this.renderEventRingBuffer.getIntValue3();
         renderDiagnosticsStatus.intValue2 = this.renderEventRingBuffer.getIntValue2();
         renderDiagnosticsStatus.intValue3 = this.intValue5;
         renderDiagnosticsStatus.flag2 = this.flag7;

         for (int intValue10 = 0; intValue10 < 96; intValue10++) {
            renderDiagnosticsStatus.text[intValue10] = this.text[intValue10];
            renderDiagnosticsStatus.ints[intValue10] = this.ints[intValue10];
         }

         renderDiagnosticsStatus.timestamp = this.timestamp;
         renderDiagnosticsStatus.flag = this.flag6;
      }
   }

   long compute() {
      return this.renderStateHasher.getTimestamp();
   }

   long getTimestamp() {
      return this.timestamp;
   }

   int compute2() {
      return this.renderEventRingBuffer.getIntValue3();
   }

   void invoke22(DataOutputStream dataOutputStream) throws IOException {
      this.renderEventRingBuffer.invoke2(dataOutputStream);
   }

   String resolve(int i) {
      return "WS-" + resolve2(i);
   }

   private void invoke23(int i) {
      this.renderStateHasher.invoke(i);
      this.renderStateHasher.invoke2(this.timestamp);
   }

   private void invoke24(int i) {
      this.invoke20("GameRenderer.tail", i);
   }

   private void invoke25(int i, int j) {
      long longValue3 = System.nanoTime();
      int intValue11 = (int)(this.renderStateHasher.getTimestamp() ^ longValue3 >>> 11 ^ (long)i << 16 ^ j);
      this.renderEventRingBuffer.invoke(longValue3, intValue11, i, j, this.renderStateHasher.getTimestamp());
      this.none = this.resolve(intValue11);
      this.none2 = RenderAnomalyType.label(i);
      this.invoke26(longValue3, intValue11, i, j);
      LOGGER.warn("[WildCore] tracker={} code={} snapshot=pending", this.none, this.none2);
   }

   private void invoke26(long l, int i, int j, int k) {
      if (!this.flag6) {
         this.flag6 = true;
         this.intValue = i;
         this.intValue2 = j;
         this.intValue3 = k;
         this.timestamp6 = l + 2400000000L;
         this.ozhidanie = "ожидает";
         this.ozhidanie2 = "Ожидает запись";
      }
   }

   private void invoke27() {
      long longValue4 = System.nanoTime();
      if (longValue4 - this.timestamp2 >= 250000000L) {
         this.timestamp2 = longValue4;
         this.text0x0000000000000000 = "0x" + Long.toUnsignedString(this.renderStateHasher.getTimestamp(), 16);
         this.text02 = Integer.toString(this.renderEventRingBuffer.getIntValue3());
         this.text03 = Long.toString(this.timestamp);
         this.text0 = Integer.toString(this.intValue4);
         this.ozhidanieNone = this.ozhidanie + "  " + this.none;
         this.ozhidanie2 = this.flag6 ? "Ожидает запись" : this.ozhidanie;
         if (this.renderEventRingBuffer.getIntValue3() == 0) {
            this.glClean = "GL clean";
            this.matrixFinite = "Matrix finite";
         }
      }

      if (this.flag7 && longValue4 - this.timestamp3 >= 100000000L) {
         this.timestamp3 = longValue4;
         this.invoke29();
      }

      if (this.flag6 && longValue4 >= this.timestamp6) {
         this.flag6 = false;
         this.ozhidanie = "запись";
         this.ozhidanie2 = "Запись";

         try {
            Path path4 = this.wildSnapWriter.resolve(this, this.intValue, this.intValue2, this.intValue3);
            this.none3 = path4.toString();
            this.none4 = path4.getFileName().toString();
            this.ozhidanie = "записан";
            this.ozhidanie2 = "Записан";
         } catch (Throwable exception6) {
            this.ozhidanie = "ошибка";
            this.ozhidanie2 = "Ошибка слепка";
            short shortValue = 16385;
            int intValue12 = exception6.getClass().getName().hashCode();
            long longValue5 = this.renderStateHasher.getTimestamp();
            int intValue13 = (int)(longValue5 ^ intValue12 ^ shortValue);
            this.renderEventRingBuffer.invoke(System.nanoTime(), intValue13, shortValue, intValue12, longValue5);
            this.none = this.resolve(intValue13);
            this.none2 = RenderAnomalyType.label(shortValue);
            LOGGER.warn("[WildCore] tracker={} code={} snapshot=failed", this.none, this.none2);
         }
      }
   }

   private static String resolve2(int i) {
      String text2 = Integer.toUnsignedString(i, 16).toUpperCase(Locale.ROOT);
      return text2.length() >= 8 ? text2.substring(text2.length() - 8) : "00000000".substring(text2.length()) + text2;
   }

   private static String resolve3(String string, int i) {
      if (string != null && !string.isBlank()) {
         String text3 = string.replace('\n', ' ').replace('\r', ' ').trim();
         return text3.length() <= i ? text3 : text3.substring(0, Math.max(0, i - 3)) + "...";
      } else {
         return "none";
      }
   }

   private Path resolve4() {
      MinecraftClient client = MinecraftClient.getInstance();
      return (client == null ? Path.of(System.getProperty("user.dir", ".")) : client.runDirectory.toPath()).resolve("logs");
   }

   private void invoke28() {
      this.intValue5 = 0;

      for (int intValue14 = 0; intValue14 < this.text.length; intValue14++) {
         this.text[intValue14] = "";
         this.ints[intValue14] = 0;
      }
   }

   private void invoke29() {
      Path path5 = this.resolve4().resolve("latest.log");

      try {
         if (!Files.exists(path5)) {
            if (this.timestamp4 != -1L || this.intValue5 == 0) {
               this.invoke18();
            }

            return;
         }

         long longValue6 = Files.size(path5);
         long longValue7 = Files.getLastModifiedTime(path5).toMillis();
         if (longValue6 != this.timestamp4 || longValue7 != this.timestamp5) {
            this.invoke18();
         }
      } catch (Throwable exception7) {
         this.invoke18();
      }
   }

   private void invoke30(String string) {
      if (string != null && !string.isEmpty()) {
         int intValue15 = 0;
         int intValue16 = string.length();

         for (int intValue17 = 0; intValue17 <= intValue16; intValue17++) {
            if (intValue17 == intValue16 || string.charAt(intValue17) == '\n') {
               int intValue18 = intValue17;
               if (intValue17 > intValue15 && string.charAt(intValue17 - 1) == '\r') {
                  intValue18 = intValue17 - 1;
               }

               if (intValue18 > intValue15) {
                  String text4 = resolve3(string.substring(intValue15, intValue18), 170);
                  this.invoke31(text4, this.compute3(text4));
               }

               intValue15 = intValue17 + 1;
            }
         }

         if (this.intValue5 == 0) {
            this.invoke31("INFO latest.log has no visible lines", 1);
         }
      } else {
         this.invoke31("INFO latest.log is empty", 1);
      }
   }

   private void invoke31(String string, int i) {
      if (this.intValue5 < this.text.length) {
         this.text[this.intValue5] = string;
         this.ints[this.intValue5] = i;
         this.intValue5++;
      } else {
         for (int intValue19 = 1; intValue19 < this.text.length; intValue19++) {
            this.text[intValue19 - 1] = this.text[intValue19];
            this.ints[intValue19 - 1] = this.ints[intValue19];
         }

         int intValue20 = this.text.length - 1;
         this.text[intValue20] = string;
         this.ints[intValue20] = i;
      }
   }

   private int compute3(String string) {
      if (string == null) {
         return 0;
      } else if (this.check(string, "ERROR") || this.check(string, "Exception") || this.check(string, "Crash")) {
         return 3;
      } else if (this.check(string, "WARN")) {
         return 2;
      } else if (this.check(string, "Shader") || this.check(string, "GL_") || this.check(string, "OpenGL")) {
         return 4;
      } else {
         return !this.check(string, "DEBUG") && !this.check(string, "TRACE") ? 1 : 0;
      }
   }

   private boolean check(String string, String string2) {
      return string.indexOf(string2) >= 0;
   }
}
