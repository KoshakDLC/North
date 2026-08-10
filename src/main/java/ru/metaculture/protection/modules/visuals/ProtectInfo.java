package ru.metaculture.protection;

import com.mojang.blaze3d.opengl.GlStateManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.client.util.SkinTextures.Model;
import net.minecraft.text.MutableText;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import org.wild.module.api.Module;
import org.wild.module.api.ModuleRegister;

@ModuleRegister(
   name = "ProtectInfo",
   description = "Скрывает ники, домены, бренды и заменяет скорборд",
   category = Category.Visuals
)
public class ProtectInfo extends Module {
   private static final String TEKST = "Текст";
   private static final String ISHODYASCHIY_CHAT = "Исходящий чат";
   private static final String DOMENY_IP = "Домены/IP";
   private static final String SKINY = "Скины";
   private static final String RAMKI = "Рамки";
   private static final String KARTINY = "Картины";
   private static final String[] FUNTIME = new String[]{
      "Funtime",
      "Spookytime",
      "HolyWorld",
      "LonyGrief",
      "Wellmine",
      "ArtyGrief",
      "Aresmine",
      "Triada",
      "SlimeWorld",
      "VimeMC",
      "ReallyWorld",
      "MineBlaze",
      "DexLand",
      "TeslaCraft",
      "MusteryWorld",
      "Gamely",
      "SunRise",
      "MSTNetwork",
      "ReallyGrief",
      "MineLand",
      "LastCraft",
      "McSkill",
      "Hypixel",
      "Фантайм",
      "Фан тайм",
      "фантайм",
      "Фунтиме",
      "Fun time",
      "Fun-Time",
      "Fun_time",
      "FT",
      "spacetimes",
      "spookytime",
      "GuvsHvh"
   };
   private static final Pattern PATTERN = Pattern.compile("(?iu)\\b(" + String.join("|", FUNTIME) + ")\\s*\\.\\s*([a-zа-я]{2,12})\\b");
   private static final Pattern PATTERN_2 = Pattern.compile(
      "(?iu)\\b(?!wild\\.)[a-z0-9-]{2,}(?:\\.[a-z0-9-]{2,})*\\.(ru|su|fun|net|org|com|me|pw|xyz|pro|gg|top|site|online)\\b"
   );
   private static final Pattern PATTERN_3 = Pattern.compile("\\b(?:\\d{1,3}\\.){3}\\d{1,3}(?::\\d{2,5})?\\b");
   private static final Pattern[] PATTERN_4 = resolve27();
   private static final Identifier IDENTIFIER = Identifier.of("wild", "textures/png/zov.png");
   private static final Identifier IDENTIFIER_2 = Identifier.of("wild", "textures/png/obla.png");
   private static final Identifier IDENTIFIER_3 = Identifier.of("wild", "textures/protect/streamer_skin.png");
   private static SkinTextures skinTextures;
   private static final Pattern PATTERN_5 = Pattern.compile("(?i)(?:\\u00A7|\\u0412\\u00A7)[0-9a-fk-or]");
   private static final Pattern PATTERN_6 = Pattern.compile("(?iu)(?:анарх(?:ия|ии)?|anarchy|an)\\s*(?:[-:#№]|\\s)*\\d{1,5}");
   public final BooleanSetting druzya = new BooleanSetting("Друзья", false);
   public final GroupSetting chtoSkryvat = new GroupSetting("Что скрывать", new BooleanSetting("Свой ник", true), new BooleanSetting("Анархию", false));
   public final GroupSetting zaschita = new GroupSetting(
      "Защита",
      new BooleanSetting("Текст", true),
      new BooleanSetting("Исходящий чат", true),
      new BooleanSetting("Домены/IP", true),
      new BooleanSetting("Скины", true),
      new BooleanSetting("Рамки", true),
      new BooleanSetting("Картины", true)
   );
   public final TextSetting zamena = new TextSetting("Замена", "North");
   public final TextSetting kastomNik = new TextSetting("Кастом ник", "Protect");
   public final TextSetting kastomAnarhiya = new TextSetting("Кастом анархия", "Скрыто");
   public final BooleanSetting tsvetSkorborda = new BooleanSetting("Цвет скорборда", true);
   public final ModeSetting ottenok = new ModeSetting("Оттенок", "Голубой", "Голубой", "Тёмно-синий");
   public final BooleanSetting renderitPng = new BooleanSetting("Рендерить пнг", false);
   public final ModeSetting variatsiyaPng = new ModeSetting("Вариация ПНГ ", "Чоткая", "Хмырь", "Чоткая");

   public ProtectInfo() {
      this.kastomNik.setVisibilityCondition(() -> !this.chtoSkryvat.isEnabled("Свой ник"));
      this.kastomAnarhiya.setVisibilityCondition(() -> !this.chtoSkryvat.isEnabled("Анархию"));
      this.ottenok.setVisibilityCondition(() -> !this.tsvetSkorborda.isEnabled());
      this.variatsiyaPng.setVisibilityCondition(() -> !this.renderitPng.isEnabled());
      this.addSettings(
         new Setting[]{
            this.druzya,
            this.chtoSkryvat,
            this.zaschita,
            this.zamena,
            this.kastomNik,
            this.kastomAnarhiya,
            this.tsvetSkorborda,
            this.ottenok,
            this.renderitPng,
            this.variatsiyaPng
         }
      );
   }

   @EventHandler
   public void onHudRender(HudRenderEvent hudRenderEvent) {
      if (this.renderitPng.isEnabled() && CLIENT.world != null && CLIENT.player != null) {
         RenderManager renderManager = hudRenderEvent.getRenderManager();
         float floatValue = 220.0F;
         float floatValue2 = 250.0F;
         float floatValue3 = hudRenderEvent.getIntValue();
         float floatValue4 = hudRenderEvent.getIntValue2();
         float floatValue5 = floatValue3 - floatValue - 2.0F - 5.0F;
         float floatValue6 = floatValue4 / 2.0F - floatValue2 / 2.0F - 60.0F;
         Identifier identifier2 = this.variatsiyaPng.getValue().equals("Чоткая") ? IDENTIFIER : IDENTIFIER_2;
         int intValue = compute(identifier2);
         if (intValue > 0) {
            GlStateManager._bindTexture(intValue);
            GlStateManager._texParameter(3553, 10240, 9728);
            GlStateManager._texParameter(3553, 10241, 9728);
            renderManager.invoke65(1.0F);
            float floatValue7 = floatValue5 + floatValue / 2.0F;
            float floatValue8 = floatValue6 + floatValue2 / 2.0F;
            renderManager.invoke56(floatValue7, floatValue8);
            renderManager.invoke59(1.0F, -1.0F);
            renderManager.invoke56(-floatValue7, -floatValue8);
            renderManager.invoke7(intValue, floatValue5, floatValue6, floatValue, floatValue2);
            renderManager.invoke57();
            renderManager.invoke64();
            renderManager.invoke57();
            renderManager.invoke66();
         }
      }
   }

   public static boolean check() {
      ProtectInfo protectInfo = resolve9();
      return protectInfo != null && protectInfo.enabled;
   }

   public static String resolve(String string) {
      ProtectInfo protectInfo2 = resolve9();
      if (protectInfo2 != null && protectInfo2.enabled) {
         String text2 = resolve13(string, protectInfo2);
         return !protectInfo2.zaschita.isEnabled("Текст") ? text2 : resolve20(text2, protectInfo2.zaschita.isEnabled("Домены/IP"), resolve10());
      } else {
         return string;
      }
   }

   public static String resolve2(String string) {
      ProtectInfo protectInfo3 = resolve9();
      if (protectInfo3 != null && protectInfo3.enabled && protectInfo3.zaschita.isEnabled("Исходящий чат")) {
         String text3 = resolve13(string, protectInfo3);
         return resolve20(text3, protectInfo3.zaschita.isEnabled("Домены/IP"), resolve10());
      } else {
         return string;
      }
   }

   public static OrderedText resolve3(OrderedText orderedText) {
      ProtectInfo protectInfo4 = resolve9();
      if (orderedText != null && protectInfo4 != null && protectInfo4.enabled && protectInfo4.zaschita.isEnabled("Текст")) {
         ArrayList arrayList = new ArrayList();
         StringBuilder stringBuilder = new StringBuilder();
         orderedText.accept((i, style, j) -> {
            String var5x = new String(Character.toChars(j));
            arrayList.add(new ProtectInfo.ProtectInfoData2(var5x, style));
            stringBuilder.append(var5x);
            return true;
         });
         String text4 = stringBuilder.toString();
         ProtectInfo.ProtectInfoData protectInfoData = resolve21(arrayList, text4, protectInfo4.zaschita.isEnabled("Домены/IP"), resolve10());
         return !protectInfoData.changed ? orderedText : characterVisitor -> {
            int var2x = 0;

            for (ProtectInfo.ProtectInfoData2 var4x : protectInfoData.tokens) {
               for (int var5x = 0; var5x < var4x.text.length(); var2x++) {
                  int intValue2 = var4x.text.codePointAt(var5x);
                  if (!characterVisitor.accept(var2x, var4x.style, intValue2)) {
                     return false;
                  }

                  var5x += Character.charCount(intValue2);
               }
            }

            return true;
         };
      } else {
         return orderedText;
      }
   }

   public static boolean check2() {
      ProtectInfo protectInfo5 = resolve9();
      return protectInfo5 != null && protectInfo5.enabled && protectInfo5.zaschita.isEnabled("Скины");
   }

   public static boolean check3() {
      ProtectInfo protectInfo6 = resolve9();
      return protectInfo6 != null && protectInfo6.enabled && protectInfo6.zaschita.isEnabled("Рамки");
   }

   public static boolean check4() {
      ProtectInfo protectInfo7 = resolve9();
      return protectInfo7 != null && protectInfo7.enabled && protectInfo7.zaschita.isEnabled("Картины");
   }

   public static boolean check5() {
      ProtectInfo protectInfo8 = resolve9();
      return protectInfo8 != null && protectInfo8.enabled && protectInfo8.tsvetSkorborda.isEnabled();
   }

   public static SkinTextures resolve4() {
      if (skinTextures == null) {
         skinTextures = new SkinTextures(IDENTIFIER_3, null, null, null, Model.SLIM, true);
      }

      return skinTextures;
   }

   public static Text resolve5(Text text) {
      if (text != null && CLIENT.player != null) {
         ProtectInfo protectInfo9 = resolve9();
         if (protectInfo9 != null && protectInfo9.enabled) {
            MutableText mutableText = Text.empty();
            text.visit((style, string) -> {
               String text5 = resolve(string);
               mutableText.append(Text.literal(text5).setStyle(style));
               return Optional.empty();
            }, Style.EMPTY);
            return mutableText;
         } else {
            return text;
         }
      } else {
         return text;
      }
   }

   public static Text resolve6(Text text) {
      Text text6 = resolve5(text);
      return !check5() ? text6 : resolve19(text6, resolve12());
   }

   public static String resolve7(String string) {
      return resolve(string);
   }

   public static String resolve8(String string, ProtectInfo protectInfo10) {
      if (string != null && !string.isEmpty()) {
         String text7 = resolve13(string, protectInfo10);
         if (protectInfo10.zaschita.isEnabled("Текст")) {
            text7 = resolve20(text7, protectInfo10.zaschita.isEnabled("Домены/IP"), resolve11(protectInfo10));
         }

         return text7;
      } else {
         return string;
      }
   }

   private static ProtectInfo resolve9() {
      return WildClient.INSTANCE != null && WildClient.INSTANCE.moduleManager != null ? WildClient.INSTANCE.moduleManager.getModule(ProtectInfo.class) : null;
   }

   private static String resolve10() {
      ProtectInfo protectInfo11 = resolve9();
      return resolve11(protectInfo11);
   }

   private static String resolve11(ProtectInfo protectInfo12) {
      if (protectInfo12 == null) {
         return "North";
      } else {
         String text8 = protectInfo12.zamena.getValue().trim();
         return text8.isEmpty() ? "North" : text8;
      }
   }

   private static Formatting resolve12() {
      ProtectInfo protectInfo13 = resolve9();
      return protectInfo13 != null && "Тёмно-синий".equals(protectInfo13.ottenok.getValue()) ? Formatting.DARK_BLUE : Formatting.AQUA;
   }

   private static String resolve13(String string, ProtectInfo protectInfo14) {
      if (string != null && !string.isEmpty()) {
         String text9 = string;
         if (protectInfo14.chtoSkryvat.isEnabled("Свой ник")) {
            text9 = resolve14(string, protectInfo14);
         }

         if (protectInfo14.druzya.isEnabled()) {
            text9 = resolve15(text9, protectInfo14);
         }

         if (protectInfo14.chtoSkryvat.isEnabled("Анархию")) {
            ServerStatsParser.INSTANCE.invoke(500L);
            text9 = resolve18(text9, protectInfo14.kastomAnarhiya.getValue(), ServerStatsParser.INSTANCE.getNA2());
         }

         return text9;
      } else {
         return string;
      }
   }

   private static String resolve14(String string, ProtectInfo protectInfo15) {
      String text10 = resolve16(protectInfo15);
      String text11 = string;
      if (CLIENT != null) {
         if (CLIENT.getSession() != null) {
            text11 = resolve17(string, CLIENT.getSession().getUsername(), text10);
         }

         if (CLIENT.player != null) {
            text11 = resolve17(text11, CLIENT.player.getGameProfile() == null ? null : CLIENT.player.getGameProfile().getName(), text10);
            text11 = resolve17(text11, CLIENT.player.getName() == null ? null : CLIENT.player.getName().getString(), text10);
         }
      }

      return resolve17(text11, ServerStatsParser.nA, text10);
   }

   private static String resolve15(String string, ProtectInfo protectInfo16) {
      String text12 = resolve16(protectInfo16);
      String text13 = string;
      List items = FriendCommand.resolve();
      if (items != null && !items.isEmpty()) {
         for (String text14 : (List<String>)items) {
            text13 = resolve17(text13, text14, text12);
         }

         return text13;
      } else {
         return string;
      }
   }

   private static String resolve16(ProtectInfo protectInfo17) {
      String text15 = protectInfo17.kastomNik.getValue();
      return text15 != null && !text15.isBlank() ? text15 : "Protect";
   }

   private static String resolve17(String string, String string2, String string3) {
      if (string != null && !string.isEmpty() && string2 != null) {
         String text16 = string3 != null && !string3.isBlank() ? string3 : "Protect";
         String text17 = PATTERN_5.matcher(string2).replaceAll("").trim();
         if (!text17.isEmpty() && !text17.equalsIgnoreCase("N/A") && !text17.equalsIgnoreCase(text16)) {
            Pattern pattern2 = Pattern.compile(Pattern.quote(text17), 66);
            Matcher matcher2 = pattern2.matcher(string);
            StringBuilder stringBuilder2 = new StringBuilder(string.length());

            while (matcher2.find()) {
               String text18 = matcher2.group();
               if (check6(string, matcher2.start(), matcher2.end()) && !check8(string, matcher2.start(), matcher2.end(), text17, text16)) {
                  matcher2.appendReplacement(stringBuilder2, Matcher.quoteReplacement(text16));
               } else {
                  matcher2.appendReplacement(stringBuilder2, Matcher.quoteReplacement(text18));
               }
            }

            matcher2.appendTail(stringBuilder2);
            return stringBuilder2.toString();
         } else {
            return string;
         }
      } else {
         return string;
      }
   }

   private static boolean check6(String string, int i, int j) {
      return (i <= 0 || !check7(string.charAt(i - 1))) && (j >= string.length() || !check7(string.charAt(j)));
   }

   private static boolean check7(char c) {
      return Character.isLetterOrDigit(c) || c == '_';
   }

   private static boolean check8(String string, int i, int j, String string2, String string3) {
      if (string3 != null && !string3.isEmpty()) {
         Matcher matcher3 = Pattern.compile(Pattern.quote(string2), 66).matcher(string3);

         while (matcher3.find()) {
            String text19 = string3.substring(0, matcher3.start());
            String text20 = string3.substring(matcher3.end());
            int intValue3 = i - text19.length();
            int intValue4 = j + text20.length();
            if (intValue3 >= 0
               && intValue4 <= string.length()
               && string.regionMatches(true, intValue3, text19, 0, text19.length())
               && string.regionMatches(true, j, text20, 0, text20.length())) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   private static String resolve18(String string, String string2, String string3) {
      if (string != null && !string.isEmpty()) {
         String text21 = string2 != null && !string2.isBlank() ? string2 : "Hidden";
         String text22 = PATTERN_6.matcher(string).replaceAll(Matcher.quoteReplacement(text21));
         if (string3 != null && !string3.equals("N/A") && !string3.isBlank()) {
            String text23 = PATTERN_5.matcher(text22).replaceAll("").trim();
            if (text23.equals(string3) || text23.equals("-" + string3) || text23.equals("#" + string3) || text23.equals("№" + string3)) {
               return text21;
            }

            Pattern pattern3 = Pattern.compile("(?iu)(?:анарх(?:ия|ии)?|anarchy|an)\\s*(?:[-:#№]|\\s)*" + Pattern.quote(string3));
            text22 = pattern3.matcher(text22).replaceAll(Matcher.quoteReplacement(text21));
         }

         return text22;
      } else {
         return string;
      }
   }

   private static Text resolve19(Text text, Formatting formatting) {
      MutableText mutableText2 = Text.empty();
      text.visit((style, string) -> {
         Style style2 = style.withColor(formatting);
         mutableText2.append(Text.literal(string).setStyle(style2));
         return Optional.empty();
      }, Style.EMPTY);
      return mutableText2;
   }

   private static String resolve20(String string, boolean bl, String string2) {
      if (string != null && !string.isEmpty()) {
         String text24 = string;
         if (bl) {
            text24 = resolve25(string, string2);
            text24 = resolve26(text24, string2);
            text24 = PATTERN_3.matcher(text24).replaceAll(string2 + ".net");
         }

         for (Pattern pattern4 : PATTERN_4) {
            text24 = pattern4.matcher(text24).replaceAll(Matcher.quoteReplacement(string2));
         }

         return text24;
      } else {
         return string;
      }
   }

   private static ProtectInfo.ProtectInfoData resolve21(List<ProtectInfo.ProtectInfoData2> list, String string, boolean bl, String string2) {
      ProtectInfo.ProtectInfoData protectInfoData2 = new ProtectInfo.ProtectInfoData(list, string, false);
      if (bl) {
         protectInfoData2 = resolve22(protectInfoData2, PATTERN, matcher -> string2 + "." + matcher.group(2).toLowerCase(Locale.ROOT));
         protectInfoData2 = resolve22(protectInfoData2, PATTERN_2, matcher -> string2 + "." + matcher.group(1).toLowerCase(Locale.ROOT));
         protectInfoData2 = resolve22(protectInfoData2, PATTERN_3, matcher -> string2 + ".net");
      }

      for (Pattern pattern5 : PATTERN_4) {
         protectInfoData2 = resolve22(protectInfoData2, pattern5, matcher -> string2);
      }

      return protectInfoData2;
   }

   private static ProtectInfo.ProtectInfoData resolve22(ProtectInfo.ProtectInfoData protectInfoData3, Pattern pattern, Function<Matcher, String> function) {
      Matcher matcher4 = pattern.matcher(protectInfoData3.text);
      if (!matcher4.find()) {
         return protectInfoData3;
      } else {
         ArrayList arrayList2 = new ArrayList();
         int intValue5 = 0;

         do {
            invoke(protectInfoData3.tokens, arrayList2, intValue5, matcher4.start());
            arrayList2.add(new ProtectInfo.ProtectInfoData2((String)function.apply(matcher4), resolve23(protectInfoData3.tokens, matcher4.start())));
            intValue5 = matcher4.end();
         } while (matcher4.find());

         invoke(protectInfoData3.tokens, arrayList2, intValue5, protectInfoData3.text.length());
         return new ProtectInfo.ProtectInfoData(arrayList2, resolve24((List<ProtectInfo.ProtectInfoData2>)arrayList2), true);
      }
   }

   private static void invoke(List<ProtectInfo.ProtectInfoData2> list, List<ProtectInfo.ProtectInfoData2> list2, int i, int j) {
      if (i < j) {
         int intValue6 = 0;

         for (ProtectInfo.ProtectInfoData2 protectInfoData22 : list) {
            int intValue7 = intValue6;
            int intValue8 = intValue6 + protectInfoData22.text.length();
            intValue6 = intValue8;
            int intValue9 = Math.max(i, intValue7);
            int intValue10 = Math.min(j, intValue8);
            if (intValue9 < intValue10) {
               list2.add(new ProtectInfo.ProtectInfoData2(protectInfoData22.text.substring(intValue9 - intValue7, intValue10 - intValue7), protectInfoData22.style));
            }
         }
      }
   }

   private static Style resolve23(List<ProtectInfo.ProtectInfoData2> list, int i) {
      int intValue11 = 0;
      Style style3 = Style.EMPTY;

      for (ProtectInfo.ProtectInfoData2 protectInfoData23 : list) {
         int intValue12 = intValue11 + protectInfoData23.text.length();
         if (i < intValue12) {
            return protectInfoData23.style;
         }

         intValue11 = intValue12;
         style3 = protectInfoData23.style;
      }

      return style3;
   }

   private static String resolve24(List<ProtectInfo.ProtectInfoData2> list) {
      StringBuilder stringBuilder3 = new StringBuilder();

      for (ProtectInfo.ProtectInfoData2 protectInfoData24 : list) {
         stringBuilder3.append(protectInfoData24.text);
      }

      return stringBuilder3.toString();
   }

   private static String resolve25(String string, String string2) {
      Matcher matcher5 = PATTERN.matcher(string);
      StringBuffer stringBuffer = new StringBuffer();

      while (matcher5.find()) {
         matcher5.appendReplacement(stringBuffer, Matcher.quoteReplacement(string2 + "." + matcher5.group(2).toLowerCase(Locale.ROOT)));
      }

      matcher5.appendTail(stringBuffer);
      return stringBuffer.toString();
   }

   private static String resolve26(String string, String string2) {
      Matcher matcher6 = PATTERN_2.matcher(string);
      StringBuffer stringBuffer2 = new StringBuffer();

      while (matcher6.find()) {
         matcher6.appendReplacement(stringBuffer2, Matcher.quoteReplacement(string2 + "." + matcher6.group(1).toLowerCase(Locale.ROOT)));
      }

      matcher6.appendTail(stringBuffer2);
      return stringBuffer2.toString();
   }

   private static Pattern[] resolve27() {
      Pattern[] patterns = new Pattern[FUNTIME.length];

      for (int intValue13 = 0; intValue13 < FUNTIME.length; intValue13++) {
         patterns[intValue13] = Pattern.compile("(?iu)(?<![\\p{L}\\p{N}])" + resolve28(FUNTIME[intValue13]) + "(?![\\p{L}\\p{N}])");
      }

      return patterns;
   }

   private static String resolve28(String string) {
      StringBuilder stringBuilder4 = new StringBuilder();

      for (int intValue14 = 0; intValue14 < string.length(); intValue14++) {
         if (intValue14 > 0) {
            stringBuilder4.append("[\\s._-]*");
         }

         stringBuilder4.append(Pattern.quote(String.valueOf(string.charAt(intValue14))));
      }

      return stringBuilder4.toString();
   }

   private static int compute(Identifier identifier) {
      if (CLIENT == null) {
         return -1;
      } else {
         TextureManager textureManager = CLIENT.getTextureManager();
         if (textureManager == null) {
            return -1;
         } else {
            AbstractTexture abstractTexture = textureManager.getTexture(identifier);
            if (abstractTexture == null) {
               return -1;
            } else {
               return abstractTexture.getGlTexture() instanceof GlTexture glTexture ? glTexture.getGlId() : -1;
            }
         }
      }
   }

   record ProtectInfoData(List<ProtectInfo.ProtectInfoData2> tokens, String text, boolean changed) {
   }

   record ProtectInfoData2(String text, Style style) {
   }
}
