package ru.metaculture.protection;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

final class WildSnapWriter {
   private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");

   Path resolve(RenderDiagnosticsTracker renderDiagnosticsTracker, int i, int j, int k) throws Exception {
      ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream(2048);

      try (DataOutputStream dataOutputStream2 = new DataOutputStream(byteArrayOutputStream)) {
         dataOutputStream2.writeInt(1465077328);
         dataOutputStream2.writeInt(1);
         this.invoke(dataOutputStream2);
         this.invoke2(dataOutputStream2, renderDiagnosticsTracker, i, j, k);
         this.invoke3(dataOutputStream2);
         this.invoke4(dataOutputStream2);
         this.invoke5(dataOutputStream2, renderDiagnosticsTracker);
         this.invoke6(dataOutputStream2);
         this.invoke7(dataOutputStream2);
      }

      byte[] byteValues = WildSnapCrypto.resolve(byteArrayOutputStream.toByteArray());
      Path path = this.resolve2();
      Path path2 = path.resolve(renderDiagnosticsTracker.resolve(i) + "-" + DATE_TIME_FORMATTER.format(LocalDateTime.now()) + ".wildsnap");
      Files.write(path2, byteValues, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
      return path2;
   }

   Path resolve2() throws Exception {
      Path path3 = Path.of(System.getProperty("user.dir", "."), "wild", "debug", "snapshots");
      Files.createDirectories(path3);
      return path3;
   }

   private void invoke(DataOutputStream dataOutputStream) throws Exception {
      ByteArrayOutputStream byteArrayOutputStream2 = new ByteArrayOutputStream(256);

      try (DataOutputStream dataOutputStream3 = new DataOutputStream(byteArrayOutputStream2)) {
         dataOutputStream3.writeUTF("wild-1.21.8-1783538716222");
         dataOutputStream3.writeUTF("1.21.8");
         dataOutputStream3.writeUTF("stable");
      }

      invoke8(dataOutputStream, 1, byteArrayOutputStream2.toByteArray());
   }

   private void invoke2(DataOutputStream dataOutputStream, RenderDiagnosticsTracker renderDiagnosticsTracker2, int i, int j, int k) throws Exception {
      ByteArrayOutputStream byteArrayOutputStream3 = new ByteArrayOutputStream(128);

      try (DataOutputStream dataOutputStream4 = new DataOutputStream(byteArrayOutputStream3)) {
         dataOutputStream4.writeInt(i);
         dataOutputStream4.writeInt(j);
         dataOutputStream4.writeInt(k);
         dataOutputStream4.writeLong(renderDiagnosticsTracker2.compute());
         dataOutputStream4.writeLong(renderDiagnosticsTracker2.getTimestamp());
         dataOutputStream4.writeInt(renderDiagnosticsTracker2.compute2());
      }

      invoke8(dataOutputStream, 2, byteArrayOutputStream3.toByteArray());
   }

   private void invoke3(DataOutputStream dataOutputStream) throws Exception {
      ByteArrayOutputStream byteArrayOutputStream4 = new ByteArrayOutputStream(64);

      try (DataOutputStream dataOutputStream5 = new DataOutputStream(byteArrayOutputStream4)) {
         GlStateInspector.writeInto(dataOutputStream5);
      }

      invoke8(dataOutputStream, 3, byteArrayOutputStream4.toByteArray());
   }

   private void invoke4(DataOutputStream dataOutputStream) throws Exception {
      ByteArrayOutputStream byteArrayOutputStream5 = new ByteArrayOutputStream(96);

      try (DataOutputStream dataOutputStream6 = new DataOutputStream(byteArrayOutputStream5)) {
         RenderMatrixValidator.invoke(dataOutputStream6);
      }

      invoke8(dataOutputStream, 4, byteArrayOutputStream5.toByteArray());
   }

   private void invoke5(DataOutputStream dataOutputStream, RenderDiagnosticsTracker renderDiagnosticsTracker3) throws Exception {
      ByteArrayOutputStream byteArrayOutputStream6 = new ByteArrayOutputStream(1024);

      try (DataOutputStream dataOutputStream7 = new DataOutputStream(byteArrayOutputStream6)) {
         renderDiagnosticsTracker3.invoke22(dataOutputStream7);
      }

      invoke8(dataOutputStream, 5, byteArrayOutputStream6.toByteArray());
   }

   private void invoke6(DataOutputStream dataOutputStream) throws Exception {
      ByteArrayOutputStream byteArrayOutputStream7 = new ByteArrayOutputStream(256);

      try (DataOutputStream dataOutputStream8 = new DataOutputStream(byteArrayOutputStream7)) {
         dataOutputStream8.writeUTF(resolve3("os.name"));
         dataOutputStream8.writeUTF(resolve3("os.arch"));
         dataOutputStream8.writeUTF(resolve3("java.version"));
         dataOutputStream8.writeUTF(resolve3("java.vm.name"));
      }

      invoke8(dataOutputStream, 6, byteArrayOutputStream7.toByteArray());
   }

   private void invoke7(DataOutputStream dataOutputStream) throws Exception {
      ByteArrayOutputStream byteArrayOutputStream8 = new ByteArrayOutputStream(128);

      try (DataOutputStream dataOutputStream9 = new DataOutputStream(byteArrayOutputStream8)) {
         dataOutputStream9.writeUTF("inject-only-runtime");
         dataOutputStream9.writeUTF("no-lvt-runtime");
      }

      invoke8(dataOutputStream, 7, byteArrayOutputStream8.toByteArray());
   }

   private static String resolve3(String string) {
      String text = System.getProperty(string, "unknown");
      return text != null && !text.isBlank() ? text.replace('\n', ' ').replace('\r', ' ').trim() : "unknown";
   }

   private static void invoke8(DataOutputStream dataOutputStream, int i, byte[] bs) throws Exception {
      dataOutputStream.writeShort(i);
      dataOutputStream.writeInt(bs.length);
      dataOutputStream.write(bs);
   }
}
