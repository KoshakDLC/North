package ru.metaculture.protection;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Base64;
import java.util.Locale;

public final class WildSnapReportWriter {
   private WildSnapReportWriter() {
   }

   public static void main(String[] strings) throws Exception {
      if (strings.length < 2) {
         System.out.println("usage: WildSnapDecoder <x25519-private-der-b64-or-file> <snapshot.wildsnap>");
      } else {
         byte[] byteValues = resolve(strings[0]);
         byte[] byteValues2 = Files.readAllBytes(Path.of(strings[1]));
         byte[] byteValues3 = WildSnapCrypto.resolve3(byteValues2, byteValues);

         try (DataInputStream dataInputStream2 = new DataInputStream(new ByteArrayInputStream(byteValues3))) {
            int intValue = dataInputStream2.readInt();
            int intValue2 = dataInputStream2.readInt();
            System.out.println("# WildSnap Report");
            System.out.println();
            System.out.println("- magic: 0x" + Integer.toHexString(intValue));
            System.out.println("- version: " + intValue2);

            while (dataInputStream2.available() > 0) {
               int intValue3 = Short.toUnsignedInt(dataInputStream2.readShort());
               int intValue4 = dataInputStream2.readInt();
               byte[] byteValues4 = dataInputStream2.readNBytes(intValue4);
               invoke(intValue3, byteValues4);
            }
         }
      }
   }

   private static byte[] resolve(String string) throws Exception {
      Path path = Path.of(string);
      String text = Files.exists(path) ? Files.readString(path) : string;
      return Base64.getDecoder().decode(text.replace("\n", "").replace("\r", "").trim());
   }

   private static void invoke(int i, byte[] bs) throws Exception {
      try (DataInputStream dataInputStream3 = new DataInputStream(new ByteArrayInputStream(bs))) {
         switch (i) {
            case 1:
               invoke2(dataInputStream3);
               break;
            case 2:
               invoke3(dataInputStream3);
               break;
            case 3:
               invoke4(dataInputStream3);
               break;
            case 4:
               invoke5(dataInputStream3);
               break;
            case 5:
               invoke6(dataInputStream3);
               break;
            case 6:
               invoke7(dataInputStream3);
               break;
            case 7:
               invoke8(dataInputStream3);
               break;
            default:
               System.out.println("- record[" + i + "]: " + resolve3(bs, Math.min(bs.length, 96)));
         }
      }
   }

   private static void invoke2(DataInputStream dataInputStream) throws Exception {
      System.out.println();
      System.out.println("## Build");
      System.out.println("- id: " + dataInputStream.readUTF());
      System.out.println("- version: " + dataInputStream.readUTF());
      System.out.println("- channel: " + dataInputStream.readUTF());
   }

   private static void invoke3(DataInputStream dataInputStream) throws Exception {
      int intValue5 = dataInputStream.readInt();
      int intValue6 = dataInputStream.readInt();
      int intValue7 = dataInputStream.readInt();
      long longValue = dataInputStream.readLong();
      long longValue2 = dataInputStream.readLong();
      int intValue8 = dataInputStream.readInt();
      System.out.println();
      System.out.println("## Core");
      System.out.println("- tracker: " + resolve2(intValue5));
      System.out.println("- code: " + RenderAnomalyType.label(intValue6));
      System.out.println("- detail: 0x" + Integer.toHexString(intValue7));
      System.out.println("- cfi: 0x" + Long.toUnsignedString(longValue, 16));
      System.out.println("- frame: " + longValue2);
      System.out.println("- anomalyTotal: " + intValue8);
   }

   private static void invoke4(DataInputStream dataInputStream) throws Exception {
      int intValue9 = dataInputStream.readInt();
      int intValue10 = dataInputStream.readInt();
      int intValue11 = dataInputStream.readInt();
      int intValue12 = dataInputStream.readInt();
      System.out.println();
      System.out.println("## GL");
      System.out.println("- currentProgram: " + intValue9);
      System.out.println("- activeTexture: " + intValue10);
      System.out.println("- texture2D: " + intValue11);
      System.out.println("- error: " + GlStateInspector.glErrorName(intValue12));
   }

   private static void invoke5(DataInputStream dataInputStream) throws Exception {
      long longValue3 = dataInputStream.readLong();
      boolean flag = dataInputStream.readBoolean();
      System.out.println();
      System.out.println("## Matrix");
      System.out.println("- hash: 0x" + Long.toUnsignedString(longValue3, 16));
      System.out.println("- finite: " + flag);
      System.out.print("- modelView: [");

      for (int intValue13 = 0; intValue13 < 16; intValue13++) {
         if (intValue13 > 0) {
            System.out.print(", ");
         }

         System.out.print(dataInputStream.readFloat());
      }

      System.out.println("]");
   }

   private static void invoke6(DataInputStream dataInputStream) throws Exception {
      int intValue14 = dataInputStream.readInt();
      System.out.println();
      System.out.println("## Anomalies");
      System.out.println("- count: " + intValue14);

      for (int intValue15 = 0; intValue15 < intValue14; intValue15++) {
         long longValue4 = dataInputStream.readLong();
         int intValue16 = dataInputStream.readInt();
         int intValue17 = dataInputStream.readInt();
         int intValue18 = dataInputStream.readInt();
         long longValue5 = dataInputStream.readLong();
         System.out
            .println(
               "- "
                  + resolve2(intValue16)
                  + " code="
                  + RenderAnomalyType.label(intValue17)
                  + " detail=0x"
                  + Integer.toHexString(intValue18)
                  + " nanos="
                  + longValue4
                  + " cfi=0x"
                  + Long.toUnsignedString(longValue5, 16)
            );
      }
   }

   private static void invoke7(DataInputStream dataInputStream) throws Exception {
      System.out.println();
      System.out.println("## Environment");
      System.out.println("- os.name: " + dataInputStream.readUTF());
      System.out.println("- os.arch: " + dataInputStream.readUTF());
      System.out.println("- java.version: " + dataInputStream.readUTF());
      System.out.println("- java.vm.name: " + dataInputStream.readUTF());
   }

   private static void invoke8(DataInputStream dataInputStream) throws Exception {
      System.out.println();
      System.out.println("## Mixin Audit");
      System.out.println("- policy: " + dataInputStream.readUTF());
      System.out.println("- locals: " + dataInputStream.readUTF());
   }

   private static String resolve2(int i) {
      String text2 = Integer.toUnsignedString(i, 16).toUpperCase(Locale.ROOT);
      return text2.length() >= 8 ? "WS-" + text2.substring(text2.length() - 8) : "WS-" + "00000000".substring(text2.length()) + text2;
   }

   private static String resolve3(byte[] bs, int i) {
      StringBuilder stringBuilder = new StringBuilder(i * 2);

      for (int intValue19 = 0; intValue19 < i; intValue19++) {
         int intValue20 = bs[intValue19] & 255;
         if (intValue20 < 16) {
            stringBuilder.append('0');
         }

         stringBuilder.append(Integer.toHexString(intValue20));
      }

      return stringBuilder.toString();
   }
}
