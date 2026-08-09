package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import lombok.Generated;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ClientBossBar;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardDisplaySlot;
import net.minecraft.scoreboard.ScoreboardEntry;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.Text;
import org.wild.mixin.acceser.BossBarHudAccessor;

public class ServerStatsParser {
   public static final ServerStatsParser INSTANCE = new ServerStatsParser();
   private static final Pattern PATTERN = Pattern.compile("(?iu)(?:анарх(?:ия|ии)?|anarchy|an)\\s*[-:#№]?\\s*(\\d{1,5})");
   private static final Pattern PATTERN_2 = Pattern.compile("([a-zA-Z0-9_]{3,16})");
   private static final Pattern PATTERN_3 = Pattern.compile("Монет:\\s*(.+)");
   private static final Pattern PATTERN_4 = Pattern.compile("Токенов:\\s*(\\d+)");
   private static final Pattern PATTERN_5 = Pattern.compile("Ранг:\\s*(.+)");
   private static final Pattern PATTERN_6 = Pattern.compile("Убийств:\\s*(\\d+)");
   private static final Pattern PATTERN_7 = Pattern.compile("Смертей:\\s*(\\d+)");
   private static final Pattern PATTERN_8 = Pattern.compile("Наиграно:\\s*(.+)");
   public static String nA = "N/A";
   private String nA2 = "N/A";
   private String nA3 = "N/A";
   private String nA4 = "N/A";
   private String text0 = "0";
   private String text02 = "0";
   private String text03 = "0";
   private String text04 = "0";
   private String text05 = "0";
   private long timestamp;

   public void invoke(long l) {
      long longValue = System.currentTimeMillis();
      if (longValue - this.timestamp >= l) {
         this.timestamp = longValue;
         this.invoke2();
      }
   }

   public void invoke2() {
      MinecraftClient client = MinecraftClient.getInstance();
      this.nA2 = "N/A";
      this.text0 = "0";
      this.text02 = "0";
      this.text03 = "0";
      this.text04 = "0";
      this.text05 = "0";
      if (client.world != null && client.player != null) {
         Scoreboard scoreboard2 = client.world.getScoreboard();
         ScoreboardObjective scoreboardObjective2 = scoreboard2.getObjectiveForSlot(ScoreboardDisplaySlot.SIDEBAR);
         if (scoreboardObjective2 != null) {
            String text = scoreboardObjective2.getDisplayName().getString();
            Matcher matcher = PATTERN.matcher(this.resolve2(text));
            if (matcher.find()) {
               this.nA2 = matcher.group(1);
            }

            List items = this.resolve(scoreboard2, scoreboardObjective2);

            for (int intValue = 0; intValue < items.size(); intValue++) {
               String text2 = (String)items.get(intValue);
               String text3 = this.resolve2(text2);
               if ("N/A".equals(this.nA2)) {
                  Matcher matcher2 = PATTERN.matcher(text3);
                  if (matcher2.find()) {
                     this.nA2 = matcher2.group(1);
                  }
               }

               if (intValue < 5 && !text3.contains(":") && !text3.contains("=") && !text3.trim().isEmpty()) {
                  Matcher matcher3 = PATTERN_2.matcher(text3);
                  if (matcher3.find()) {
                     this.nA3 = matcher3.group(1);
                     nA = this.nA3;
                  }
               }

               Matcher matcher4 = PATTERN_5.matcher(text3);
               if (matcher4.find()) {
                  this.nA4 = matcher4.group(1).trim();
               }

               Matcher matcher5 = PATTERN_3.matcher(text3);
               if (matcher5.find()) {
                  String text4 = matcher5.group(1);
                  this.text0 = text4.replaceAll("[^0-9]", "");
               }

               Matcher matcher6 = PATTERN_4.matcher(text3);
               if (matcher6.find()) {
                  this.text02 = matcher6.group(1);
               }

               Matcher matcher7 = PATTERN_6.matcher(text3);
               if (matcher7.find()) {
                  this.text03 = matcher7.group(1);
               }

               Matcher matcher8 = PATTERN_7.matcher(text3);
               if (matcher8.find()) {
                  this.text04 = matcher8.group(1);
               }

               Matcher matcher9 = PATTERN_8.matcher(text3);
               if (matcher9.find()) {
                  this.text05 = matcher9.group(1);
               }
            }
         }
      }
   }

   private List<String> resolve(Scoreboard scoreboard, ScoreboardObjective scoreboardObjective) {
      ArrayList arrayList = new ArrayList();
      Collection items2 = scoreboard.getScoreboardEntries(scoreboardObjective);
      ArrayList arrayList2 = new ArrayList(items2);
      arrayList2.sort(Comparator.comparingInt(ScoreboardEntry::value).reversed());
      int intValue2 = Math.min(arrayList2.size(), 15);

      for (int intValue3 = 0; intValue3 < intValue2; intValue3++) {
         ScoreboardEntry scoreboardEntry = (ScoreboardEntry)arrayList2.get(intValue3);
         Team team = scoreboard.getScoreHolderTeam(scoreboardEntry.owner());
         arrayList.add(Team.decorateName(team, Text.literal(scoreboardEntry.owner())).getString());
      }

      return arrayList;
   }

   private String resolve2(String string) {
      return string == null ? "" : string.replaceAll("(?i)§[0-9a-fk-or]", "").trim();
   }

   public static boolean check() {
      if (MinecraftAccessor.a_.inGameHud != null && MinecraftAccessor.a_.inGameHud.getBossBarHud() != null) {
         Map valuesByKey = ((BossBarHudAccessor)MinecraftAccessor.a_.inGameHud.getBossBarHud()).getBossBars();

         for (ClientBossBar clientBossBar : (Collection<ClientBossBar>)valuesByKey.values()) {
            String text5 = clientBossBar.getName().getString().replaceAll("(?i)§[0-9a-fk-or]", "").toLowerCase();
            if (text5.contains("pvp") || text5.contains("пвп") || text5.contains("режим боя") || text5.contains("в бою") || text5.contains("до выхода")) {
               return true;
            }
         }

         return false;
      } else {
         return false;
      }
   }

   @Generated
   public String getNA2() {
      return this.nA2;
   }

   @Generated
   public String getNA3() {
      return this.nA3;
   }

   @Generated
   public String getNA4() {
      return this.nA4;
   }

   @Generated
   public String getText0() {
      return this.text0;
   }

   @Generated
   public String getText02() {
      return this.text02;
   }

   @Generated
   public String getText03() {
      return this.text03;
   }

   @Generated
   public String getText04() {
      return this.text04;
   }

   @Generated
   public String getText05() {
      return this.text05;
   }

   @Generated
   public long getTimestamp() {
      return this.timestamp;
   }
}
