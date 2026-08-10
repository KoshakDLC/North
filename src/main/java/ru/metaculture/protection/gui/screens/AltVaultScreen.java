package ru.metaculture.protection;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import java.awt.Color;
import java.io.File;
import java.net.SocketAddress;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.option.ServerList;
import net.minecraft.client.session.Session;
import net.minecraft.client.session.Session.AccountType;
import net.minecraft.client.texture.AbstractTexture;
import net.minecraft.client.texture.GlTexture;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.Window;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.lwjgl.opengl.GL11;

public final class AltVaultScreen extends Screen implements BackdropScreen {
   private static final ThemePalette THEME_PALETTE = ThemePalette.resolve2();
   private static final int INT_VALUE = 14;
   private static final long TIMESTAMP = 350L;
   private static final ScheduledExecutorService SCHEDULED_EXECUTOR_SERVICE = Executors.newSingleThreadScheduledExecutor(runnable -> {
      Thread thread = new Thread(runnable, "Wild-AltVaultSave");
      thread.setDaemon(true);
      return thread;
   });
   private static final String[] X = new String[]{
      "x",
      "z",
      "q",
      "v",
      "mx",
      "im",
      "by",
      "not",
      "its",
      "real",
      "just",
      "i",
      "fx",
      "rx",
      "nx",
      "neo",
      "raw",
      "low",
      "old",
      "the",
      "mr",
      "lil",
      "big",
      "dr",
      "sir",
      "yo",
      "ez",
      "op",
      "gg",
      "yt",
      "tv",
      "north",
      "pro",
      "uwu",
      "ya",
      "el",
      "an",
      "su",
      "ko"
   };
   private static final String[] ALEX = new String[]{
      "alex",
      "dani",
      "nik",
      "max",
      "roma",
      "kir",
      "drew",
      "mark",
      "luka",
      "tim",
      "ivan",
      "mira",
      "sasha",
      "art",
      "lev",
      "egor",
      "mike",
      "tony",
      "vlad",
      "step",
      "andrew",
      "niko",
      "den",
      "semy",
      "yar",
      "kost",
      "ilya",
      "gleb",
      "dima",
      "serg",
      "matvey",
      "rad",
      "kira",
      "mila",
      "sonya",
      "kai",
      "leo",
      "rian",
      "noah",
      "mason",
      "kevin",
      "rem",
      "zen",
      "nova",
      "pixel",
      "byte",
      "void",
      "ray",
      "fox",
      "wolf",
      "moon",
      "storm",
      "rain",
      "ash",
      "raven",
      "cole",
      "liam",
      "owen",
      "eric",
      "aron",
      "milo",
      "tomas",
      "nolan",
      "ron",
      "lars",
      "vega",
      "skye",
      "jack",
      "finn",
      "theo",
      "hugo",
      "bruno",
      "diego",
      "enzo",
      "jude",
      "reed",
      "cruz",
      "jax",
      "zane",
      "ace",
      "dash",
      "blake",
      "cody",
      "trey",
      "jett",
      "knox",
      "beck",
      "reid",
      "colt",
      "gage",
      "wade",
      "zeke",
      "onyx",
      "jinx",
      "flux",
      "ghost",
      "frost",
      "blaze",
      "drake",
      "hawk",
      "lynx",
      "puma",
      "arlo",
      "remy",
      "yuki",
      "aki",
      "ren",
      "sora",
      "haru",
      "kaze",
      "mei",
      "rio",
      "neon",
      "echo",
      "dusk",
      "sage",
      "wren"
   };
   private static final String[] TEXT = new String[]{
      "",
      "",
      "",
      "x",
      "yy",
      "on",
      "er",
      "ix",
      "is",
      "way",
      "pro",
      "mc",
      "dev",
      "boy",
      "top",
      "live",
      "sky",
      "craft",
      "mine",
      "play",
      "hd",
      "fps",
      "low",
      "new",
      "old",
      "go",
      "run",
      "win",
      "bit",
      "core",
      "qq",
      "zz",
      "xd",
      "yt",
      "gg",
      "ez",
      "op",
      "wow",
      "god",
      "main",
      "gang",
      "ster",
      "izz",
      "us",
      "io",
      "ly",
      "ne"
   };
   private static final String[] KA = new String[]{
      "ka",
      "ki",
      "ko",
      "mi",
      "mo",
      "ra",
      "ri",
      "ro",
      "sa",
      "si",
      "so",
      "ta",
      "ti",
      "to",
      "ne",
      "ni",
      "no",
      "la",
      "li",
      "lo",
      "ve",
      "vi",
      "vo",
      "za",
      "ze",
      "zu",
      "da",
      "de",
      "du",
      "ny",
      "re",
      "xo",
      "ku",
      "ke",
      "fa",
      "fi",
      "fo",
      "ga",
      "go",
      "ha",
      "hi",
      "ho",
      "ba",
      "bo",
      "pa",
      "po",
      "wu",
      "yo",
      "ju",
      "ce",
      "dra",
      "vex",
      "zar",
      "kra",
      "nyx",
      "rox"
   };
   private final Screen screen;
   private final MainMenuRenderer mainMenuRenderer = new MainMenuRenderer();
   private final List<AltVaultScreen.AltVaultScreenUiState> items = new ArrayList<>();
   private final Set<String> values = new HashSet<>();
   private final Map<String, AltVaultScreen.AltVaultScreenResources> valuesByKey = new HashMap<>();
   private final Map<String, String> valuesByKey2 = new HashMap<>();
   private final List<AltVaultScreen.AltVaultScreenUiState2> items2 = List.of(
      new AltVaultScreen.AltVaultScreenUiState2("Login", AltVaultScreen.AltVaultScreenState2.USE),
      new AltVaultScreen.AltVaultScreenUiState2("Add", AltVaultScreen.AltVaultScreenState2.ADD_CRACKED),
      new AltVaultScreen.AltVaultScreenUiState2("Random", AltVaultScreen.AltVaultScreenState2.RANDOM),
      new AltVaultScreen.AltVaultScreenUiState2("Edit", AltVaultScreen.AltVaultScreenState2.EDIT),
      new AltVaultScreen.AltVaultScreenUiState2("Delete", AltVaultScreen.AltVaultScreenState2.DELETE),
      new AltVaultScreen.AltVaultScreenUiState2("Back", AltVaultScreen.AltVaultScreenState2.BACK)
   );
   private final AltVaultScreen.AltVaultScreenVariant altVaultScreenVariant = new AltVaultScreen.AltVaultScreenVariant("nick@password", false);
   private final AltVaultScreen.AltVaultScreenState5[] altVaultScreenState5s = new AltVaultScreen.AltVaultScreenState5[14];
   private final SpringIntegrator springIntegrator = new SpringIntegrator(SpringSpec.resolve6());
   private final SpringIntegrator springIntegrator2 = new SpringIntegrator(SpringSpec.resolve6());
   private float floatValue;
   private float floatValue2;
   private float floatValue3;
   private float floatValue4;
   private long timestamp;
   private long timestamp2;
   private long timestamp3;
   private long timestamp4;
   private float floatValue5;
   private float floatValue6;
   private float floatValue7;
   private float floatValue8;
   private float floatValue9;
   private float floatValue10;
   private float floatValue11;
   private float floatValue12;
   private float floatValue13;
   private float floatValue14;
   private float floatValue15;
   private boolean flag;
   private boolean flag2;
   private boolean flag3;
   private int intValue;
   private int intValue2;
   private int intValue3 = -6357021;
   private int intValue4 = -11341636;
   private Theme theme = Theme.AURORA;
   private boolean flag4;
   private int intValue5 = -1;
   private int intValue6 = 5;
   private String selectAnIdentity = "Select an identity";
   private String text = null;
   private float floatValue16;
   private float floatValue17;
   private boolean flag5;
   private float floatValue18;
   private float floatValue19;
   private float floatValue20;
   private float floatValue21;
   private float floatValue22;
   private float floatValue23;
   private float floatValue24;
   private boolean flag6;
   private volatile ScheduledFuture<?> scheduledFuture;

   public AltVaultScreen(Screen screen) {
      super(Text.literal("Alt Manager"));
      this.screen = screen;

      for (int intValue = 0; intValue < this.altVaultScreenState5s.length; intValue++) {
         this.altVaultScreenState5s[intValue] = new AltVaultScreen.AltVaultScreenState5();
      }
   }

   public static void invoke(MinecraftClient minecraftClient) {
      AltVaultScreen.AltVaultScreenState4.invoke(minecraftClient);
   }

   protected void init() {
      super.init();
      boolean flag = this.timestamp != 0L;
      this.timestamp = System.nanoTime();
      this.timestamp2 = this.timestamp;
      this.timestamp3 = this.timestamp;
      this.floatValue5 = 0.0F;
      this.flag = false;
      this.flag2 = false;
      this.flag3 = false;
      this.intValue = 0;
      this.intValue2 = 0;
      this.floatValue16 = 0.0F;
      this.floatValue17 = 0.0F;
      this.springIntegrator.setFloatValue(0.0F);
      this.springIntegrator2.setFloatValue(0.0F);
      this.altVaultScreenVariant.invoke5();

      for (AltVaultScreen.AltVaultScreenUiState2 altVaultScreenUiState2 : this.items2) {
         altVaultScreenUiState2.invoke();
      }

      this.invoke5(flag);
      this.invoke12();
   }

   public void resize(MinecraftClient client, int width, int height) {
      int intValue2 = this.intValue5;
      float floatValue = this.floatValue16;
      String text = this.selectAnIdentity;
      super.resize(client, width, height);
      this.intValue5 = intValue2;
      this.floatValue16 = floatValue;
      this.floatValue17 = floatValue;
      this.selectAnIdentity = text;
   }

   public void render(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
      this.invoke3(mouseX, mouseY, deltaTicks, false);
   }

   @Override
   public void invoke2(int i, int j, float f) {
      this.invoke3(i, j, f, true);
   }

   private void invoke3(int i, int j, float f, boolean bl) {
      Window window2 = this.client == null ? null : this.client.getWindow();
      if (window2 != null && !window2.hasZeroWidthOrHeight() && window2.getFramebufferWidth() > 0 && window2.getFramebufferHeight() > 0) {
         int intValue3 = window2.getFramebufferWidth();
         int intValue4 = window2.getFramebufferHeight();
         long longValue = System.nanoTime();
         float floatValue2 = Math.max(0.001F, Math.min(0.05F, (float)(longValue - this.timestamp2) / 1.0E9F));
         this.timestamp2 = longValue;
         this.floatValue5 = (float)(longValue - this.timestamp) / 1.0E9F;
         if (this.check3(window2, intValue3, intValue4, i, j, longValue)) {
            floatValue2 = 0.001F;
         }

         this.invoke28();
         this.invoke29(window2, i, j, floatValue2, longValue);
         this.invoke30(intValue3, intValue4, floatValue2);
         this.invoke31();
         float floatValue3 = (this.floatValue6 / Math.max(1.0F, (float)intValue3) - 0.5F) * 2.0F;
         float floatValue4 = (this.floatValue7 / Math.max(1.0F, (float)intValue4) - 0.5F) * 2.0F;
         float floatValue5 = this.springIntegrator.measure(floatValue3, floatValue2);
         float floatValue6 = this.springIntegrator2.measure(floatValue4, floatValue2);
         this.invoke34(intValue3, intValue4, floatValue5, floatValue6, floatValue2);
         this.invoke26();
         int intValue5 = GL11.glGetInteger(36006);
         MainMenuScreen.MainMenuScreenTimedEntry mainMenuScreenTimedEntry = this.resolve14(intValue3, intValue4, intValue5, floatValue5, floatValue6, longValue);
         if (bl) {
            FramebufferUtils.GlStateSnapshot glStateSnapshot = FramebufferUtils.captureGlState();

            try {
               this.mainMenuRenderer.check(mainMenuScreenTimedEntry);
            } finally {
               FramebufferUtils.restoreGlState(glStateSnapshot);
            }

            this.invoke38(mainMenuScreenTimedEntry);
         }
      }
   }

   public void renderBackground(DrawContext context, int mouseX, int mouseY, float deltaTicks) {
   }

   public void renderInGameBackground(DrawContext context) {
   }

   public boolean shouldPause() {
      return false;
   }

   public boolean shouldCloseOnEsc() {
      return false;
   }

   public void close() {
      this.invoke27(AltVaultScreen.AltVaultScreenState2.BACK);
   }

   public void removed() {
      if (WildClient.isShuttingDown()) {
         this.invoke9(0L);
      } else {
         this.invoke8();
      }

      AltVaultScreen.AltVaultScreenState4.invoke2();
      this.invoke40();
      this.mainMenuRenderer.close();
      super.removed();
   }

   public boolean mouseClicked(double mouseX, double mouseY, int button) {
      if (button == 0 && this.client != null && this.client.getWindow() != null) {
         float floatValue7 = this.measure(this.client.getWindow(), mouseX);
         float floatValue8 = this.measure2(this.client.getWindow(), mouseY);
         long longValue2 = System.nanoTime();
         if (this.flag6) {
            float floatValue9 = 8.0F;
            if (floatValue7 >= this.floatValue19 - floatValue9
               && floatValue7 <= this.floatValue19 + this.floatValue21 + floatValue9
               && floatValue8 >= this.floatValue20
               && floatValue8 <= this.floatValue20 + this.floatValue22) {
               this.flag5 = true;
               if (floatValue8 >= this.floatValue23 && floatValue8 <= this.floatValue23 + this.floatValue24) {
                  this.floatValue18 = floatValue8 - this.floatValue23;
               } else {
                  this.floatValue18 = this.floatValue24 * 0.5F;
               }

               this.invoke4(floatValue8);
               return true;
            }
         }

         if (this.altVaultScreenVariant.check(floatValue7, floatValue8)) {
            this.altVaultScreenVariant.flag2 = true;
            this.altVaultScreenVariant.flag3 = false;
            this.altVaultScreenVariant.intValue = this.altVaultScreenVariant.text.length();
            this.altVaultScreenVariant.floatValue10 = 1.0F;
            return true;
         } else {
            for (AltVaultScreen.AltVaultScreenUiState2 altVaultScreenUiState22 : this.items2) {
               if (altVaultScreenUiState22.flag && altVaultScreenUiState22.check(floatValue7, floatValue8)) {
                  this.altVaultScreenVariant.flag2 = false;
                  altVaultScreenUiState22.floatValue10 = 1.0F;
                  altVaultScreenUiState22.floatValue11 = 1.0F;
                  this.invoke27(altVaultScreenUiState22.altVaultScreenState2);
                  return true;
               }
            }

            for (int intValue6 = 0; intValue6 < this.items.size(); intValue6++) {
               AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState = this.items.get(intValue6);
               if (!altVaultScreenUiState.flag6 && altVaultScreenUiState.flag7 && altVaultScreenUiState.check(floatValue7, floatValue8)) {
                  this.altVaultScreenVariant.flag2 = false;
                  if (this.intValue5 == intValue6 && longValue2 - this.timestamp4 < 360000000L) {
                     altVaultScreenUiState.floatValue11 = 1.0F;
                     altVaultScreenUiState.floatValue12 = 1.0F;
                     this.invoke27(AltVaultScreen.AltVaultScreenState2.USE);
                  } else {
                     this.intValue5 = intValue6;
                     altVaultScreenUiState.floatValue12 = Math.max(altVaultScreenUiState.floatValue12, 0.42F);
                     this.selectAnIdentity = "Selected identity: " + altVaultScreenUiState.text2;
                     this.invoke6();
                  }

                  this.timestamp4 = longValue2;
                  return true;
               }
            }

            this.altVaultScreenVariant.flag2 = false;
            return true;
         }
      } else {
         return super.mouseClicked(mouseX, mouseY, button);
      }
   }

   public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
      if (this.compute6() <= this.intValue6) {
         return true;
      } else {
         this.floatValue16 -= (float)verticalAmount;
         int intValue7 = Math.max(0, this.compute6() - Math.max(1, this.intValue6));
         this.floatValue16 = measure9(this.floatValue16, 0.0F, (float)intValue7);
         return true;
      }
   }

   public boolean mouseDragged(double mouseX, double mouseY, int button, double deltaX, double deltaY) {
      if (this.flag5 && this.flag6 && this.client != null && this.client.getWindow() != null) {
         this.invoke4(this.measure2(this.client.getWindow(), mouseY));
         return true;
      } else {
         return super.mouseDragged(mouseX, mouseY, button, deltaX, deltaY);
      }
   }

   public boolean mouseReleased(double mouseX, double mouseY, int button) {
      if (button == 0 && this.flag5) {
         this.flag5 = false;
         return true;
      } else {
         return super.mouseReleased(mouseX, mouseY, button);
      }
   }

   private void invoke4(float f) {
      float floatValue10 = this.floatValue22 - this.floatValue24;
      if (!(floatValue10 <= 0.001F)) {
         float floatValue11 = measure9(f - this.floatValue18, this.floatValue20, this.floatValue20 + floatValue10);
         float floatValue12 = (floatValue11 - this.floatValue20) / floatValue10;
         int intValue8 = Math.max(0, this.compute6() - Math.max(1, this.intValue6));
         this.floatValue16 = floatValue12 * intValue8;
      }
   }

   public boolean charTyped(char chr, int modifiers) {
      if (!this.altVaultScreenVariant.flag2) {
         return super.charTyped(chr, modifiers);
      } else if (!isAccountInputChar(chr)) {
         return true;
      } else {
         this.altVaultScreenVariant.invoke2(chr);
         return true;
      }
   }

   public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
      if (keyCode == 256) {
         if (this.checkSelf()) {
            this.invoke21();
            return true;
         } else if (this.altVaultScreenVariant.flag2) {
            this.altVaultScreenVariant.flag2 = false;
            this.altVaultScreenVariant.flag3 = false;
            return true;
         } else {
            this.invoke27(AltVaultScreen.AltVaultScreenState2.BACK);
            return true;
         }
      } else {
         if (this.altVaultScreenVariant.flag2) {
            boolean flag2 = (modifiers & 2) != 0 || (modifiers & 8) != 0;
            if (flag2) {
               if (keyCode == 67) {
                  if (this.client != null && this.client.keyboard != null && !this.altVaultScreenVariant.text.isEmpty()) {
                     this.client.keyboard.setClipboard(this.altVaultScreenVariant.text);
                  }

                  return true;
               }

               if (keyCode == 86) {
                  if (this.client != null && this.client.keyboard != null) {
                     String text2 = this.client.keyboard.getClipboard();
                     if (text2 != null) {
                        this.altVaultScreenVariant.invoke(sanitizeAccountInput(text2));
                     }
                  }

                  return true;
               }

               if (keyCode == 65) {
                  this.altVaultScreenVariant.flag3 = true;
                  return true;
               }
            }

            if (keyCode == 259) {
               this.altVaultScreenVariant.invoke3();
               return true;
            }

            if (keyCode == 261) {
               this.altVaultScreenVariant.invoke4();
               return true;
            }

            if (keyCode == 263) {
               this.altVaultScreenVariant.flag3 = false;
               this.altVaultScreenVariant.intValue = compute8(this.altVaultScreenVariant.intValue - 1, 0, this.altVaultScreenVariant.text.length());
               return true;
            }

            if (keyCode == 262) {
               this.altVaultScreenVariant.flag3 = false;
               this.altVaultScreenVariant.intValue = compute8(this.altVaultScreenVariant.intValue + 1, 0, this.altVaultScreenVariant.text.length());
               return true;
            }

            if (keyCode == 257 || keyCode == 335) {
               this.invoke27(AltVaultScreen.AltVaultScreenState2.ADD_CRACKED);
               return true;
            }
         }

         boolean flag3 = (modifiers & 2) != 0 || (modifiers & 8) != 0;
         if (flag3 && keyCode == 67) {
            this.invoke23();
            return true;
         } else if (flag3 && keyCode == 83) {
            this.invoke24();
            return true;
         } else if (flag3 && keyCode == 71) {
            this.invoke25(5);
            return true;
         } else if (keyCode == 257 || keyCode == 335) {
            this.invoke27(AltVaultScreen.AltVaultScreenState2.USE);
            return true;
         } else if (keyCode == 261) {
            this.invoke27(AltVaultScreen.AltVaultScreenState2.DELETE);
            return true;
         } else if (keyCode == 264) {
            this.invoke46(1);
            return true;
         } else if (keyCode == 265) {
            this.invoke46(-1);
            return true;
         } else {
            return super.keyPressed(keyCode, scanCode, modifiers);
         }
      }
   }

   private void invoke5(boolean bl) {
      this.items.clear();
      this.valuesByKey2.clear();
      File file = this.resolve2();
      boolean flag4 = file.exists() && !AltVaultStore.check(file);

      for (AltVaultStore.AltVaultStoreTimedEntry altVaultStoreTimedEntry : AltVaultStore.resolve(file)) {
         this.valuesByKey2.put(altVaultStoreTimedEntry.id(), altVaultStoreTimedEntry.password());
         this.items
            .add(
               new AltVaultScreen.AltVaultScreenUiState(
                  altVaultStoreTimedEntry.name(), AltVaultScreen.AltVaultScreenState.resolve(altVaultStoreTimedEntry.type()), false, this.floatValue5, altVaultStoreTimedEntry.id(), altVaultStoreTimedEntry.createdAt(), altVaultStoreTimedEntry.lastUsedAt()
               )
            );
      }

      MinecraftClient client2 = this.client == null ? MinecraftClient.getInstance() : this.client;
      if (client2 != null && client2.getSession() != null) {
         SessionManager.invoke(client2);
         SessionManager.resolve(client2)
            .filter(session -> !session.getUsername().equalsIgnoreCase(client2.getSession().getUsername()))
            .ifPresent(session -> this.invoke48(session, true));
         this.invoke48(client2.getSession(), true);
      }

      for (int intValue9 = 0; intValue9 < this.items.size(); intValue9++) {
         AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState3 = this.items.get(intValue9);
         altVaultScreenUiState3.floatValue = bl ? -1.0F : this.floatValue5 + intValue9 * 0.045F;
         altVaultScreenUiState3.floatValue18 = bl ? 1.0F : 0.0F;
      }

      if (flag4) {
         this.invoke7();
      }
   }

   private void invoke6() {
      this.invoke9(350L);
   }

   private void invoke7() {
      this.invoke9(0L);
   }

   private void invoke8() {
      ScheduledFuture scheduledFuture = this.scheduledFuture;
      if (scheduledFuture != null) {
         scheduledFuture.cancel(false);
         this.scheduledFuture = null;
      }

      try {
         this.scheduledFuture = SCHEDULED_EXECUTOR_SERVICE.schedule((Runnable)this::invoke10, 0L, TimeUnit.MILLISECONDS);
         this.scheduledFuture.get(10L, TimeUnit.SECONDS);
      } catch (Throwable exception) {
      } finally {
         this.scheduledFuture = null;
      }
   }

   private void invoke9(long l) {
      ScheduledFuture scheduledFuture2 = this.scheduledFuture;
      if (scheduledFuture2 != null) {
         scheduledFuture2.cancel(false);
      }

      this.scheduledFuture = SCHEDULED_EXECUTOR_SERVICE.schedule((Runnable)this::invoke10, l, TimeUnit.MILLISECONDS);
   }

   private void invoke10() {
      File file2 = this.resolve2();
      ArrayList arrayList;
      String text3;
      synchronized (this.items) {
         arrayList = new ArrayList();

         for (AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState4 : this.items) {
            if (!altVaultScreenUiState4.flag6 && !altVaultScreenUiState4.flag5 && !altVaultScreenUiState4.flag) {
               arrayList.add(
                  new AltVaultStore.AltVaultStoreTimedEntry(
                     altVaultScreenUiState4.text,
                     altVaultScreenUiState4.text2,
                     altVaultScreenUiState4.altVaultScreenState.name(),
                     this.valuesByKey2.getOrDefault(altVaultScreenUiState4.text, ""),
                     altVaultScreenUiState4.timestamp,
                     altVaultScreenUiState4.timestamp2
                  )
               );
            }
         }

         text3 = this.resolve();
      }

      AltVaultStore.invoke2(file2, arrayList, text3);
   }

   private void invoke11() {
      this.invoke7();
   }

   private String resolve() {
      AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState5 = this.resolve13();
      return altVaultScreenUiState5 != null && !altVaultScreenUiState5.flag ? altVaultScreenUiState5.text : AltVaultStore.resolve2(this.resolve2());
   }

   private File resolve2() {
      File file3 = WildClient.INSTANCE != null && WildClient.INSTANCE.file != null ? WildClient.INSTANCE.file : WildClient.getFILE();
      return new File(file3, "accounts.json");
   }

   private void invoke12() {
      MinecraftClient client3 = this.client == null ? MinecraftClient.getInstance() : this.client;
      String text4 = client3 != null && client3.getSession() != null ? client3.getSession().getUsername() : "";
      String text5 = AltVaultStore.resolve2(this.resolve2());
      int intValue10 = this.compute(text5);
      if (intValue10 >= 0) {
         this.invoke13(intValue10, this.items.get(intValue10).text2.equalsIgnoreCase(text4) ? "Active identity: " : "Selected identity: ");
      } else {
         int intValue11 = this.compute2(text4);
         if (intValue11 >= 0) {
            this.invoke13(intValue11, "Active identity: ");
         } else if (!this.items.isEmpty()) {
            this.invoke13(0, "Selected identity: ");
         } else {
            this.intValue5 = -1;
         }
      }
   }

   private int compute(String string) {
      if (string != null && !string.isBlank()) {
         for (int intValue12 = 0; intValue12 < this.items.size(); intValue12++) {
            AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState6 = this.items.get(intValue12);
            if (!altVaultScreenUiState6.flag6 && !altVaultScreenUiState6.flag5 && string.equals(altVaultScreenUiState6.text)) {
               return intValue12;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   private int compute2(String string) {
      if (string != null && !string.isBlank()) {
         for (int intValue13 = 0; intValue13 < this.items.size(); intValue13++) {
            AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState7 = this.items.get(intValue13);
            if (!altVaultScreenUiState7.flag6 && !altVaultScreenUiState7.flag5 && altVaultScreenUiState7.text2.equalsIgnoreCase(string)) {
               return intValue13;
            }
         }

         return -1;
      } else {
         return -1;
      }
   }

   private void invoke13(int i, String string) {
      if (i >= 0 && i < this.items.size()) {
         this.intValue5 = i;
         AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState8 = this.items.get(this.intValue5);
         this.selectAnIdentity = string + altVaultScreenUiState8.text2;
         this.invoke47();
      } else {
         this.intValue5 = -1;
      }
   }

   private void invoke14() {
      if (this.checkSelf()) {
         this.invoke22();
      } else {
         String rawInput = this.altVaultScreenVariant.text == null ? "" : this.altVaultScreenVariant.text.trim();
         String text6 = resolve22(rawInput);
         String password = resolvePassword(rawInput);
         if (text6.isBlank()) {
            this.selectAnIdentity = "Enter username or nick@password";
            this.altVaultScreenVariant.floatValue11 = 1.0F;
         } else {
            for (int intValue14 = 0; intValue14 < this.items.size(); intValue14++) {
               AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState9 = this.items.get(intValue14);
               if (!altVaultScreenUiState9.flag6 && !altVaultScreenUiState9.flag5 && altVaultScreenUiState9.text2.equalsIgnoreCase(text6)) {
                  this.intValue5 = intValue14;
                  altVaultScreenUiState9.floatValue12 = 1.0F;
                  if (!password.isEmpty() || rawInput.contains("@")) {
                     this.valuesByKey2.put(altVaultScreenUiState9.text, password);
                     this.selectAnIdentity = password.isEmpty() ? "Password cleared" : "Password updated";
                     this.invoke11();
                  } else {
                     this.selectAnIdentity = "Identity already exists";
                  }

                  this.invoke47();
                  this.invoke6();
                  this.altVaultScreenVariant.invoke4();
                  return;
               }
            }

            long longValue3 = System.currentTimeMillis();
            String accountId = resolve23(text6, AltVaultScreen.AltVaultScreenState.CRACKED);
            AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState10 = new AltVaultScreen.AltVaultScreenUiState(
               text6, AltVaultScreen.AltVaultScreenState.CRACKED, false, this.floatValue5, accountId, longValue3, 0L
            );
            altVaultScreenUiState10.floatValue12 = 1.0F;
            this.items.add(altVaultScreenUiState10);
            this.values.add(text6.toLowerCase(Locale.ROOT));
            this.valuesByKey2.put(accountId, password);
            this.intValue5 = this.items.size() - 1;
            this.invoke47();
            this.altVaultScreenVariant.invoke4();
            this.selectAnIdentity = password.isEmpty() ? "Cracked identity added" : "Cracked identity added with password";
            this.invoke11();
         }
      }
   }

   private void invoke15() {
      Set values = this.resolve3();

      for (int intValue15 = 0; intValue15 < 256; intValue15++) {
         String text7 = resolve4();
         if (!text7.isBlank() && !values.contains(text7.toLowerCase(Locale.ROOT))) {
            this.invoke16(text7);
            return;
         }
      }

      String text8 = resolve11(values);
      if (!text8.isBlank()) {
         this.invoke16(text8);
      } else {
         this.selectAnIdentity = "Generated identity collision";
         this.altVaultScreenVariant.floatValue11 = 1.0F;
      }
   }

   private Set<String> resolve3() {
      HashSet hashSet = new HashSet<>(this.values);

      for (AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState11 : this.items) {
         if (!altVaultScreenUiState11.flag6 && !altVaultScreenUiState11.flag5) {
            hashSet.add(altVaultScreenUiState11.text2.toLowerCase(Locale.ROOT));
         }
      }

      String text9 = resolve22(this.altVaultScreenVariant.text);
      if (!text9.isBlank()) {
         hashSet.add(text9.toLowerCase(Locale.ROOT));
      }

      return hashSet;
   }

   private void invoke16(String string) {
      this.values.add(string.toLowerCase(Locale.ROOT));
      this.altVaultScreenVariant.invoke4();
      this.altVaultScreenVariant.invoke(string);
      this.altVaultScreenVariant.flag2 = true;
      this.altVaultScreenVariant.flag3 = true;
      this.altVaultScreenVariant.floatValue11 = 1.0F;
      this.selectAnIdentity = "Generated identity: " + string;
   }

   private static String resolve4() {
      ThreadLocalRandom threadLocalRandom2 = ThreadLocalRandom.current();

      for (int intValue16 = 0; intValue16 < 28; intValue16++) {
         String text10 = ALEX[threadLocalRandom2.nextInt(ALEX.length)];
         String text11 = ALEX[threadLocalRandom2.nextInt(ALEX.length)];
         String text12 = X[threadLocalRandom2.nextInt(X.length)];
         String text13 = TEXT[threadLocalRandom2.nextInt(TEXT.length)];
         String text14 = resolve9(threadLocalRandom2, threadLocalRandom2.nextInt(2, 5));
         String text15 = text10.substring(0, Math.min(text10.length(), threadLocalRandom2.nextInt(2, Math.min(4, text10.length()) + 1)));
         String text16 = text11.substring(0, Math.min(text11.length(), threadLocalRandom2.nextInt(2, Math.min(4, text11.length()) + 1)));
         String text17 = threadLocalRandom2.nextInt(100) < 18 ? "_" : "";
         String text18 = resolve10(threadLocalRandom2);
         String text19 = threadLocalRandom2.nextInt(100) < 40 ? text18 : "";
         String text20 = threadLocalRandom2.nextInt(100) < 45 ? text13 : "";

         String text21 = switch (threadLocalRandom2.nextInt(20)) {
            case 0 -> resolve12(text10) + resolve12(text11);
            case 1 -> text12 + resolve12(text10);
            case 2 -> resolve12(text10) + text13;
            case 3 -> text10 + text17 + text18;
            case 4 -> resolve12(text15) + resolve12(text11);
            case 5 -> text10 + resolve12(text16);
            case 6 -> resolve12(text14) + (threadLocalRandom2.nextInt(100) < 28 ? text13 : "");
            case 7 -> text14 + text17 + text18;
            case 8 -> resolve12(text10) + resolve12(text16) + text19;
            case 9 -> text12 + text17 + text14;
            case 10 -> text15 + resolve12(resolve9(threadLocalRandom2, threadLocalRandom2.nextInt(1, 3))) + text13;
            case 11 -> resolve5(text10) + text20;
            case 12 -> resolve12(text10) + "_" + resolve12(text11);
            case 13 -> resolve6(text10) + resolve12(text11);
            case 14 -> "xX" + resolve12(text10) + "Xx";
            case 15 -> resolve7(text10) + text19;
            case 16 -> resolve12(text12) + resolve12(text10) + text13;
            case 17 -> resolve12(text10) + resolve5(text16);
            case 18 -> resolve12(text10) + resolve8(threadLocalRandom2);
            default -> resolve12(text10) + text20 + (threadLocalRandom2.nextInt(100) < 36 ? text18 : "");
         };
         text21 = resolve22(text21);
         if (text21.length() >= 3 && text21.length() <= 16) {
            return text21;
         }
      }

      return resolve11(Set.of());
   }

   private static String resolve5(String string) {
      if (string != null && !string.isEmpty()) {
         StringBuilder stringBuilder = new StringBuilder(string.length());

         for (int intValue17 = 0; intValue17 < string.length(); intValue17++) {
            char character = Character.toLowerCase(string.charAt(intValue17));

            stringBuilder.append(switch (character) {
               case 'a' -> '4';
               default -> string.charAt(intValue17);
               case 'e' -> '3';
               case 'i' -> '1';
               case 'o' -> '0';
               case 's' -> '5';
               case 't' -> '7';
            });
         }

         return stringBuilder.toString();
      } else {
         return "";
      }
   }

   private static String resolve6(String string) {
      if (string != null && string.length() >= 4) {
         StringBuilder stringBuilder2 = new StringBuilder(string.length());

         for (int intValue18 = 0; intValue18 < string.length(); intValue18++) {
            char character2 = string.charAt(intValue18);
            boolean flag5 = "aeiouAEIOU".indexOf(character2) >= 0;
            if (!flag5 || intValue18 == 0) {
               stringBuilder2.append(character2);
            }
         }

         return stringBuilder2.length() < 2 ? string : stringBuilder2.toString();
      } else {
         return string == null ? "" : string;
      }
   }

   private static String resolve7(String string) {
      return string != null && !string.isEmpty() ? string + string.charAt(string.length() - 1) : "";
   }

   private static String resolve8(ThreadLocalRandom threadLocalRandom) {
      int intValue19 = threadLocalRandom.nextInt(100);
      return intValue19 < 10 ? "0" + intValue19 : String.valueOf(intValue19);
   }

   private static String resolve9(ThreadLocalRandom threadLocalRandom, int i) {
      StringBuilder stringBuilder3 = new StringBuilder();

      for (int intValue20 = 0; intValue20 < i; intValue20++) {
         stringBuilder3.append(KA[threadLocalRandom.nextInt(KA.length)]);
      }

      return stringBuilder3.toString();
   }

   private static String resolve10(ThreadLocalRandom threadLocalRandom) {
      return switch (threadLocalRandom.nextInt(4)) {
         case 0 -> String.valueOf(threadLocalRandom.nextInt(7, 99));
         case 1 -> String.valueOf(threadLocalRandom.nextInt(100, 999));
         case 2 -> String.valueOf(threadLocalRandom.nextInt(1000, 9999));
         default -> String.valueOf(threadLocalRandom.nextInt(10, 9999));
      };
   }

   private static String resolve11(Set<String> set) {
      ThreadLocalRandom threadLocalRandom3 = ThreadLocalRandom.current();

      for (int intValue21 = 0; intValue21 < 64; intValue21++) {
         String text22 = Long.toUnsignedString(threadLocalRandom3.nextLong(), 36);
         if (text22.length() > 8) {
            text22 = text22.substring(0, 8);
         }

         String text23 = resolve22("Low" + text22);
         if (!text23.isBlank() && !set.contains(text23.toLowerCase(Locale.ROOT))) {
            return text23;
         }
      }

      return "";
   }

   private static String resolve12(String string) {
      if (string != null && !string.isEmpty()) {
         return string.length() == 1 ? string.toUpperCase(Locale.ROOT) : string.substring(0, 1).toUpperCase(Locale.ROOT) + string.substring(1);
      } else {
         return "";
      }
   }

   private void invoke17() {
      AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState12 = this.resolve13();
      MinecraftClient client4 = this.client == null ? MinecraftClient.getInstance() : this.client;
      if (altVaultScreenUiState12 != null && client4 != null) {
         SessionManager.invoke(client4);
         boolean flag6 = SessionManager.check(client4, altVaultScreenUiState12.text2);
         if (!flag6) {
            SessionManager.resolve2(client4, altVaultScreenUiState12.text2);
         }

         altVaultScreenUiState12.timestamp2 = System.currentTimeMillis();
         altVaultScreenUiState12.floatValue12 = 1.0F;
         String password = this.valuesByKey2.getOrDefault(altVaultScreenUiState12.text, "");
         AutoLoginManager.invoke(altVaultScreenUiState12.text2, password);
         this.selectAnIdentity = "Active identity: " + altVaultScreenUiState12.text2;
         this.invoke11();
      } else {
         this.selectAnIdentity = "Select an identity";
      }
   }

   private void invoke18() {
      AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState13 = this.resolve13();
      if (altVaultScreenUiState13 == null) {
         this.selectAnIdentity = "Select an identity";
      } else {
         altVaultScreenUiState13.flag5 = true;
         altVaultScreenUiState13.floatValue19 = this.floatValue5;
         altVaultScreenUiState13.floatValue12 = 1.0F;
         this.selectAnIdentity = "Identity removed";
         int intValue22 = this.intValue5;
         this.intValue5 = this.items.size() <= 1 ? -1 : (intValue22 >= this.items.size() - 1 ? intValue22 - 1 : intValue22 + 1);
         this.invoke47();
         this.invoke11();
      }
   }

   private boolean checkSelf() {
      return this.text != null && this.compute(this.text) >= 0;
   }

   private boolean check2() {
      return this.checkSelf() || this.resolve13() != null;
   }

   private void invoke19() {
      if (this.checkSelf()) {
         this.invoke21();
      } else {
         this.invoke20();
      }
   }

   private void invoke20() {
      AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState15 = this.resolve13();
      if (altVaultScreenUiState15 != null) {
         this.text = altVaultScreenUiState15.text;
         this.altVaultScreenVariant.invoke4();
         this.altVaultScreenVariant.invoke(this.resolveEditFieldValue(altVaultScreenUiState15));
         this.altVaultScreenVariant.flag2 = true;
         this.altVaultScreenVariant.flag3 = true;
         this.altVaultScreenVariant.floatValue11 = 1.0F;
         this.selectAnIdentity = "Editing: " + altVaultScreenUiState15.text2 + " (nick@password)";
      } else {
         this.selectAnIdentity = "Select an identity";
         this.altVaultScreenVariant.floatValue11 = 1.0F;
      }
   }

   private void invoke21() {
      this.text = null;
      this.altVaultScreenVariant.invoke4();
      this.altVaultScreenVariant.flag2 = false;
      this.altVaultScreenVariant.flag3 = false;
      this.selectAnIdentity = "Edit cancelled";
   }

   private void invoke22() {
      String rawInput = this.altVaultScreenVariant.text == null ? "" : this.altVaultScreenVariant.text.trim();
      String text24 = resolve22(rawInput);
      String password = resolvePassword(rawInput);
      boolean passwordProvided = rawInput.contains("@");
      if (text24.isBlank()) {
         this.selectAnIdentity = "Enter username or nick@password";
         this.altVaultScreenVariant.floatValue11 = 1.0F;
      } else {
         int intValue23 = this.compute(this.text);
         if (intValue23 < 0) {
            this.text = null;
            this.selectAnIdentity = "Identity not found";
         } else {
            AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState16 = this.items.get(intValue23);

            for (int intValue24 = 0; intValue24 < this.items.size(); intValue24++) {
               if (intValue24 != intValue23) {
                  AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState17 = this.items.get(intValue24);
                  if (!altVaultScreenUiState17.flag6 && !altVaultScreenUiState17.flag5 && altVaultScreenUiState17.text2.equalsIgnoreCase(text24)) {
                     this.selectAnIdentity = "Identity already exists";
                     this.altVaultScreenVariant.floatValue11 = 1.0F;
                     return;
                  }
               }
            }

            String oldPassword = this.valuesByKey2.getOrDefault(altVaultScreenUiState16.text, "");
            String newPassword = passwordProvided ? password : oldPassword;
            if (altVaultScreenUiState16.text2.equals(text24) && !altVaultScreenUiState16.flag) {
               if (passwordProvided) {
                  this.valuesByKey2.put(altVaultScreenUiState16.text, newPassword);
                  this.invoke11();
               }

               this.text = null;
               this.altVaultScreenVariant.invoke4();
               this.altVaultScreenVariant.flag2 = false;
               this.selectAnIdentity = passwordProvided ? (newPassword.isEmpty() ? "Password cleared" : "Password updated") : "No changes";
            } else {
               String text25 = resolve23(text24, altVaultScreenUiState16.altVaultScreenState);
               AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState18 = new AltVaultScreen.AltVaultScreenUiState(
                  text24, altVaultScreenUiState16.altVaultScreenState, false, -1.0F, text25, altVaultScreenUiState16.timestamp, altVaultScreenUiState16.timestamp2
               );
               altVaultScreenUiState18.floatValue18 = 1.0F;
               altVaultScreenUiState18.floatValue12 = 1.0F;
               this.items.set(intValue23, altVaultScreenUiState18);
               this.valuesByKey2.remove(altVaultScreenUiState16.text);
               this.valuesByKey2.put(text25, newPassword);
               this.values.add(text24.toLowerCase(Locale.ROOT));
               this.intValue5 = intValue23;
               this.text = null;
               this.altVaultScreenVariant.invoke4();
               this.altVaultScreenVariant.flag2 = false;
               this.selectAnIdentity = "Saved: " + text24;
               this.invoke11();
            }
         }
      }
   }

   private String resolveEditFieldValue(AltVaultScreen.AltVaultScreenUiState state) {
      String password = this.valuesByKey2.getOrDefault(state.text, "");
      return password == null || password.isEmpty() ? state.text2 : state.text2 + "@" + password;
   }

   private void invoke23() {
      AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState19 = this.resolve13();
      if (altVaultScreenUiState19 != null && this.client != null && this.client.keyboard != null) {
         this.client.keyboard.setClipboard(altVaultScreenUiState19.text2);
         this.selectAnIdentity = "Copied: " + altVaultScreenUiState19.text2;
      } else {
         this.selectAnIdentity = "Select an identity";
      }
   }

   private void invoke24() {
      if (this.items.size() >= 2) {
         AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState20 = this.resolve13();
         String text27 = altVaultScreenUiState20 == null ? null : altVaultScreenUiState20.text;
         this.items.sort((altVaultScreenUiState21, altVaultScreenUiState23) -> Long.compare(altVaultScreenUiState23.timestamp2, altVaultScreenUiState21.timestamp2));
         if (text27 != null) {
            int intValue25 = this.compute(text27);
            if (intValue25 >= 0) {
               this.intValue5 = intValue25;
            }
         }

         this.selectAnIdentity = "Sorted by last used";
         this.invoke47();
         this.invoke6();
      }
   }

   private void invoke25(int i) {
      int intValue26 = 0;
      long longValue4 = System.currentTimeMillis();

      for (int intValue27 = 0; intValue27 < i; intValue27++) {
         Set values2 = this.resolve3();
         String text28 = resolve4();
         if (text28.isBlank() || values2.contains(text28.toLowerCase(Locale.ROOT))) {
            text28 = resolve11(values2);
         }

         if (!text28.isBlank()) {
            AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState24 = new AltVaultScreen.AltVaultScreenUiState(
               text28, AltVaultScreen.AltVaultScreenState.CRACKED, false, this.floatValue5, resolve23(text28, AltVaultScreen.AltVaultScreenState.CRACKED), longValue4, 0L
            );
            altVaultScreenUiState24.floatValue12 = 1.0F;
            this.items.add(altVaultScreenUiState24);
            this.values.add(text28.toLowerCase(Locale.ROOT));
            intValue26++;
         }
      }

      if (intValue26 > 0) {
         this.intValue5 = this.items.size() - 1;
         this.invoke47();
         this.selectAnIdentity = "Added " + intValue26 + " identities";
         this.invoke11();
      } else {
         this.selectAnIdentity = "Generation collision";
         this.altVaultScreenVariant.floatValue11 = 1.0F;
      }
   }

   private AltVaultScreen.AltVaultScreenUiState resolve13() {
      if (this.intValue5 >= 0 && this.intValue5 < this.items.size()) {
         AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState25 = this.items.get(this.intValue5);
         return !altVaultScreenUiState25.flag6 && !altVaultScreenUiState25.flag5 ? altVaultScreenUiState25 : null;
      } else {
         return null;
      }
   }

   private void invoke26() {
      boolean flag7 = false;

      for (int intValue28 = this.items.size() - 1; intValue28 >= 0; intValue28--) {
         AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState26 = this.items.get(intValue28);
         if (altVaultScreenUiState26.flag5 && this.floatValue5 - altVaultScreenUiState26.floatValue19 > 0.46F) {
            this.items.remove(intValue28);
            flag7 = true;
            if (this.intValue5 >= intValue28) {
               this.intValue5--;
            }
         }
      }

      if (flag7) {
         this.intValue5 = this.items.isEmpty() ? -1 : compute8(this.intValue5, 0, this.items.size() - 1);
         this.invoke47();
      }
   }

   private void invoke27(AltVaultScreen.AltVaultScreenState2 altVaultScreenState2) {
      MinecraftClient client5 = this.client == null ? MinecraftClient.getInstance() : this.client;
      switch (altVaultScreenState2) {
         case USE:
            this.invoke17();
            break;
         case ADD_CRACKED:
            this.invoke14();
            break;
         case RANDOM:
            this.invoke15();
            break;
         case EDIT:
            this.invoke19();
            break;
         case DELETE:
            this.invoke18();
            break;
         case BACK:
            if (client5 != null) {
               client5.execute(() -> client5.setScreen(this.screen));
            }
      }
   }

   private void invoke28() {
      Theme theme = WildClient.INSTANCE != null && WildClient.INSTANCE.themeManager != null ? WildClient.INSTANCE.themeManager.getTheme() : Theme.AURORA;
      this.theme = theme;
      ThemePalette.Swatch swatch = THEME_PALETTE.resolve3(theme);
      if (swatch != null) {
         this.intValue3 = swatch.getIntValue();
         this.intValue4 = swatch.getIntValue2();
         this.flag4 = swatch.isFlag();
      } else {
         this.flag4 = false;
         Color color = theme.getColor();
         this.intValue3 = 0xFF000000 | color.getRGB() & 16777215;
         float[] floatValues = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
         this.intValue4 = 0xFF000000
            | Color.HSBtoRGB((floatValues[0] + 0.075F) % 1.0F, Math.min(1.0F, floatValues[1] * 1.08F), Math.min(1.0F, floatValues[2] * 1.18F)) & 16777215;
      }
   }

   private void invoke29(Window window, int i, int j, float f, long l) {
      float floatValue13 = this.measure(window, (double)i);
      float floatValue14 = this.measure2(window, j);
      if (!this.flag) {
         this.floatValue6 = floatValue13;
         this.floatValue7 = floatValue14;
         this.floatValue8 = 0.0F;
         this.floatValue9 = 0.0F;
         this.flag = true;
      } else {
         float floatValue15 = floatValue13 - this.floatValue6;
         float floatValue16 = floatValue14 - this.floatValue7;
         float floatValue17 = measure7(floatValue15, floatValue16);
         if (floatValue17 > 0.2F) {
            this.floatValue8 = measure9(floatValue15 / Math.max(1.0F, (float)window.getFramebufferWidth()) / f, -3.0F, 3.0F);
            this.floatValue9 = measure9(floatValue16 / Math.max(1.0F, (float)window.getFramebufferHeight()) / f, -3.0F, 3.0F);
         } else {
            float floatValue18 = (float)Math.pow(8.0E-4F, f);
            this.floatValue8 *= floatValue18;
            this.floatValue9 *= floatValue18;
         }

         this.floatValue6 = floatValue13;
         this.floatValue7 = floatValue14;
         if (floatValue17 > 1.5F) {
            this.timestamp3 = l;
         }
      }
   }

   private void invoke30(int i, int j, float f) {
      if (!this.flag2) {
         this.floatValue10 = this.floatValue6;
         this.floatValue11 = this.floatValue7;
         this.floatValue12 = 0.0F;
         this.floatValue13 = 0.0F;
         this.flag2 = true;
      } else {
         float floatValue19 = this.floatValue10;
         float floatValue20 = this.floatValue11;
         float floatValue21 = measure7(this.floatValue6 - this.floatValue10, this.floatValue7 - this.floatValue11);
         float floatValue22 = (1.0F - (float)Math.pow(1.8E-5F, f)) * (0.58F + measure9(floatValue21 / 780.0F, 0.0F, 0.28F));
         this.floatValue10 = this.floatValue10 + (this.floatValue6 - this.floatValue10) * measure9(floatValue22, 0.028F, 0.16F);
         this.floatValue11 = this.floatValue11 + (this.floatValue7 - this.floatValue11) * measure9(floatValue22, 0.028F, 0.16F);
         float floatValue23 = measure9((this.floatValue10 - floatValue19) / Math.max(1.0F, (float)i) / f, -1.25F, 1.25F);
         float floatValue24 = measure9((this.floatValue11 - floatValue20) / Math.max(1.0F, (float)j) / f, -1.25F, 1.25F);
         float floatValue25 = 1.0F - (float)Math.pow(0.0045F, f);
         this.floatValue12 = this.floatValue12 + (floatValue23 - this.floatValue12) * floatValue25;
         this.floatValue13 = this.floatValue13 + (floatValue24 - this.floatValue13) * floatValue25;
      }
   }

   private void invoke31() {
      if (!this.flag3) {
         this.floatValue14 = this.floatValue10;
         this.floatValue15 = this.floatValue11;
         this.flag3 = true;
         this.invoke33(this.floatValue10, this.floatValue11, 0.18F);
      } else {
         float floatValue26 = measure7(this.floatValue10 - this.floatValue14, this.floatValue11 - this.floatValue15);
         if (floatValue26 > 10.5F) {
            this.invoke33(this.floatValue10, this.floatValue11, measure9(floatValue26 / 280.0F, 0.06F, 0.3F));
            this.floatValue14 = this.floatValue10;
            this.floatValue15 = this.floatValue11;
         }
      }
   }

   private boolean check3(Window window, int i, int j, int k, int l, long m) {
      if (this.intValue == i && this.intValue2 == j) {
         return false;
      } else {
         this.intValue = i;
         this.intValue2 = j;
         float floatValue27 = measure9(this.measure(window, (double)k), 0.0F, (float)i);
         float floatValue28 = measure9(this.measure2(window, l), 0.0F, (float)j);
         this.floatValue6 = this.floatValue10 = this.floatValue14 = floatValue27;
         this.floatValue7 = this.floatValue11 = this.floatValue15 = floatValue28;
         this.floatValue8 = this.floatValue9 = 0.0F;
         this.floatValue12 = this.floatValue13 = 0.0F;
         this.flag = true;
         this.flag2 = true;
         this.flag3 = true;
         this.timestamp3 = m;
         this.flag5 = false;
         this.springIntegrator.setFloatValue(0.0F);
         this.springIntegrator2.setFloatValue(0.0F);
         this.floatValue17 = this.floatValue16;
         this.invoke32();
         this.invoke33(floatValue27, floatValue28, 0.12F);
         this.invoke47();
         return true;
      }
   }

   private void invoke32() {
      for (AltVaultScreen.AltVaultScreenState5 altVaultScreenState5 : this.altVaultScreenState5s) {
         altVaultScreenState5.floatValue = 0.0F;
         altVaultScreenState5.floatValue2 = 0.0F;
         altVaultScreenState5.floatValue3 = -100.0F;
         altVaultScreenState5.floatValue4 = 0.0F;
      }
   }

   private void invoke33(float f, float g, float h) {
      int intValue29 = 0;
      float floatValue29 = -1.0F;

      for (int intValue30 = 0; intValue30 < this.altVaultScreenState5s.length; intValue30++) {
         float floatValue30 = this.floatValue5 - this.altVaultScreenState5s[intValue30].floatValue3;
         if (this.altVaultScreenState5s[intValue30].floatValue4 <= 0.0F) {
            intValue29 = intValue30;
            break;
         }

         if (floatValue30 > floatValue29) {
            floatValue29 = floatValue30;
            intValue29 = intValue30;
         }
      }

      this.altVaultScreenState5s[intValue29].floatValue = f;
      this.altVaultScreenState5s[intValue29].floatValue2 = g;
      this.altVaultScreenState5s[intValue29].floatValue3 = this.floatValue5;
      this.altVaultScreenState5s[intValue29].floatValue4 = h;
   }

   private void invoke34(int i, int j, float f, float g, float h) {
      float floatValue31 = measure5(i, j);
      float floatValue32 = measure9(i * 0.25F, 280.0F * floatValue31, 420.0F * floatValue31);
      float floatValue33 = measure9(j * 0.078F, 72.0F * floatValue31, 94.0F * floatValue31);
      float floatValue34 = 14.0F * floatValue31;
      int intValue31 = this.compute6();
      this.intValue6 = Math.max(3, Math.min(5, (int)(j * 0.46F / (floatValue33 + floatValue34))));
      if (intValue31 < this.intValue6 && intValue31 > 0) {
         this.intValue6 = Math.max(1, intValue31);
      }

      if (intValue31 == 0) {
         this.intValue6 = 1;
      }

      int intValue32 = Math.max(0, intValue31 - Math.max(1, this.intValue6));
      this.floatValue16 = measure9(this.floatValue16, 0.0F, (float)intValue32);
      float floatValue35 = 1.0F - (float)Math.exp(-22.0F * h);
      this.floatValue17 = this.floatValue17 + (this.floatValue16 - this.floatValue17) * floatValue35;
      if (Float.isNaN(this.floatValue17)) {
         this.floatValue17 = this.floatValue16;
      }

      float floatValue36 = this.intValue6 * floatValue33 + Math.max(0, this.intValue6 - 1) * floatValue34;
      float floatValue37 = measure9(i * 0.3F, 320.0F * floatValue31, 480.0F * floatValue31);
      float floatValue38 = 42.0F * floatValue31;
      float floatValue39 = 42.0F * floatValue31;
      float floatValue40 = 10.0F * floatValue31;
      float floatValue41 = measure9(i * 0.067F, 86.0F * floatValue31, 124.0F * floatValue31);
      float floatValue42 = this.items2.size() * floatValue41 + (this.items2.size() - 1) * floatValue40;
      float floatValue43 = 28.0F * floatValue31;
      float floatValue44 = 18.0F * floatValue31;
      float floatValue45 = floatValue36 + floatValue43 + floatValue38 + floatValue44 + floatValue39;
      float floatValue46 = j * 0.275F + g * 1.0F * floatValue31;
      if (floatValue46 + floatValue45 > j - 58.0F * floatValue31) {
         floatValue46 = j - floatValue45 - 58.0F * floatValue31;
      }

      floatValue46 = Math.max(j * 0.19F, floatValue46);
      float floatValue47 = i * 0.5F + f * 1.55F * floatValue31;
      float floatValue48 = floatValue47 - floatValue32 * 0.5F;
      this.floatValue = floatValue48;
      this.floatValue2 = floatValue46;
      this.floatValue3 = floatValue32;
      this.floatValue4 = floatValue36;
      this.flag6 = intValue32 > 0;
      if (this.flag6) {
         this.floatValue21 = Math.max(4.0F, 5.5F * floatValue31);
         this.floatValue19 = floatValue47 + floatValue32 * 0.5F + 16.0F * floatValue31;
         this.floatValue20 = floatValue46;
         this.floatValue22 = floatValue36;
         float floatValue49 = measure9((float)this.intValue6 / intValue31, 0.1F, 1.0F);
         this.floatValue24 = Math.max(34.0F * floatValue31, this.floatValue22 * floatValue49);
         float floatValue50 = this.floatValue22 - this.floatValue24;
         float floatValue51 = intValue32 == 0 ? 0.0F : this.floatValue17 / intValue32;
         this.floatValue23 = this.floatValue20 + floatValue50 * floatValue51;
      }

      String text29 = this.resolve20();
      int intValue33 = 0;

      for (int intValue34 = 0; intValue34 < this.items.size(); intValue34++) {
         AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState27 = this.items.get(intValue34);
         if (altVaultScreenUiState27.flag6) {
            altVaultScreenUiState27.flag7 = false;
            altVaultScreenUiState27.floatValue18 = 0.0F;
         } else {
            int intValue35 = intValue33;
            if (!altVaultScreenUiState27.flag5) {
               intValue33++;
            }

            float floatValue52 = intValue35 - this.floatValue17;
            altVaultScreenUiState27.flag7 = floatValue52 > -1.0F && floatValue52 < this.intValue6;
            altVaultScreenUiState27.floatValue2 = floatValue48;
            altVaultScreenUiState27.floatValue3 = floatValue46 + floatValue52 * (floatValue33 + floatValue34);
            altVaultScreenUiState27.floatValue6 = floatValue32;
            altVaultScreenUiState27.floatValue7 = floatValue33;
            altVaultScreenUiState27.floatValue8 = Math.min(floatValue33 * 0.36F, 20.0F * floatValue31);
            altVaultScreenUiState27.floatValue17 = 58.0F * floatValue31;
            altVaultScreenUiState27.flag3 = intValue34 == this.intValue5;
            altVaultScreenUiState27.flag4 = this.check5(altVaultScreenUiState27, text29);
            if (!altVaultScreenUiState27.flag7 && !altVaultScreenUiState27.flag5) {
               altVaultScreenUiState27.floatValue9 = 0.0F;
               altVaultScreenUiState27.floatValue10 = altVaultScreenUiState27.flag3 ? 0.42F : 0.0F;
               altVaultScreenUiState27.floatValue11 = 0.0F;
               altVaultScreenUiState27.floatValue16 = 0.0F;
               altVaultScreenUiState27.floatValue4 = altVaultScreenUiState27.floatValue2;
               altVaultScreenUiState27.floatValue5 = altVaultScreenUiState27.floatValue3;
               altVaultScreenUiState27.floatValue13 = 1.0F;
            } else {
               this.invoke36(altVaultScreenUiState27, h, floatValue31);
            }

            float floatValue53 = altVaultScreenUiState27.floatValue3 + floatValue33 * 0.5F;
            float floatValue54 = floatValue33 * 0.45F;
            float floatValue55 = measure9((floatValue53 - floatValue46 + floatValue54) / floatValue54, 0.0F, 1.0F);
            float floatValue56 = measure9((floatValue46 + floatValue36 + floatValue54 - floatValue53) / floatValue54, 0.0F, 1.0F);
            float floatValue57 = floatValue55 * floatValue56;
            float floatValue58 = measure8(measure9((this.floatValue5 - altVaultScreenUiState27.floatValue) / 0.72F, 0.0F, 1.0F));
            altVaultScreenUiState27.floatValue18 = !altVaultScreenUiState27.flag7 && !altVaultScreenUiState27.flag5
               ? 0.0F
               : floatValue58
                  * floatValue57
                  * (altVaultScreenUiState27.flag5 ? measure8(measure9(1.0F - (this.floatValue5 - altVaultScreenUiState27.floatValue19) / 0.42F, 0.0F, 1.0F)) : 1.0F);
         }
      }

      float floatValue59 = floatValue46 + floatValue36 + floatValue43 + g * 0.35F * floatValue31;
      float floatValue60 = i * 0.5F - floatValue37 * 0.5F + f * 1.15F * floatValue31;
      this.invoke35(this.altVaultScreenVariant, floatValue60, floatValue59, floatValue37, floatValue38, floatValue38 * 0.5F, h, floatValue31);
      float floatValue61 = i * 0.5F - floatValue42 * 0.5F + f * 1.35F * floatValue31;
      float floatValue62 = floatValue59 + floatValue38 + floatValue44;

      for (int intValue36 = 0; intValue36 < this.items2.size(); intValue36++) {
         AltVaultScreen.AltVaultScreenUiState2 altVaultScreenUiState28 = this.items2.get(intValue36);
         altVaultScreenUiState28.flag = this.check4(altVaultScreenUiState28.altVaultScreenState2);
         if (altVaultScreenUiState28.altVaultScreenState2 == AltVaultScreen.AltVaultScreenState2.ADD_CRACKED) {
            altVaultScreenUiState28.text = this.checkSelf() ? "Save" : "Add";
         } else if (altVaultScreenUiState28.altVaultScreenState2 == AltVaultScreen.AltVaultScreenState2.EDIT) {
            altVaultScreenUiState28.text = this.checkSelf() ? "Cancel" : "Edit";
         }

         altVaultScreenUiState28.floatValue = floatValue61 + intValue36 * (floatValue41 + floatValue40);
         altVaultScreenUiState28.floatValue2 = floatValue62;
         altVaultScreenUiState28.floatValue5 = floatValue41;
         altVaultScreenUiState28.floatValue6 = floatValue39;
         altVaultScreenUiState28.floatValue7 = floatValue39 * 0.5F;
         altVaultScreenUiState28.floatValue16 = 46.0F * floatValue31;
         altVaultScreenUiState28.floatValue17 = measure8(measure9((this.floatValue5 - 0.38F - intValue36 * 0.035F) / 0.78F, 0.0F, 1.0F));
         this.invoke37(altVaultScreenUiState28, h, floatValue31, altVaultScreenUiState28.flag ? 1.0F : 0.28F);
      }
   }

   private void invoke35(AltVaultScreen.AltVaultScreenVariant altVaultScreenVariant, float f, float g, float h, float i, float j, float k, float l) {
      altVaultScreenVariant.floatValue = f;
      altVaultScreenVariant.floatValue2 = g;
      altVaultScreenVariant.floatValue5 = h;
      altVaultScreenVariant.floatValue6 = i;
      altVaultScreenVariant.floatValue7 = j;
      altVaultScreenVariant.floatValue16 = 48.0F * l;
      altVaultScreenVariant.floatValue17 = measure8(measure9((this.floatValue5 - 0.3F) / 0.82F, 0.0F, 1.0F));
      this.invoke37(altVaultScreenVariant, k, l, 1.0F);
      altVaultScreenVariant.floatValue = altVaultScreenVariant.floatValue
         + ((altVaultScreenVariant.flag2 ? 1.0F : 0.0F) - altVaultScreenVariant.floatValue) * (1.0F - (float)Math.pow(1.0E-4F, k));
   }

   private void invoke36(AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState29, float f, float g) {
      float floatValue63 = altVaultScreenUiState29.flag5 ? measure8(measure9((this.floatValue5 - altVaultScreenUiState29.floatValue19) / 0.42F, 0.0F, 1.0F)) : 0.0F;
      float floatValue64 = measure6(
         this.floatValue6,
         this.floatValue7,
         altVaultScreenUiState29.floatValue2,
         altVaultScreenUiState29.floatValue3,
         altVaultScreenUiState29.floatValue6,
         altVaultScreenUiState29.floatValue7,
         altVaultScreenUiState29.floatValue8
      );
      float floatValue65 = 1.0F - measure8(measure9(Math.max(0.0F, floatValue64) / Math.max(1.0F, 42.0F * g), 0.0F, 1.0F));
      boolean flag8 = floatValue64 <= 0.0F && !altVaultScreenUiState29.flag5 && altVaultScreenUiState29.flag7;
      float floatValue66 = altVaultScreenUiState29.flag3 ? 0.42F : 0.0F;
      altVaultScreenUiState29.floatValue9 = altVaultScreenUiState29.floatValue9 + ((flag8 ? 1.0F : 0.0F) - altVaultScreenUiState29.floatValue9) * (1.0F - (float)Math.pow(1.0E-4F, f));
      altVaultScreenUiState29.floatValue10 = altVaultScreenUiState29.floatValue10
         + ((altVaultScreenUiState29.flag7 ? Math.max(floatValue65, floatValue66) : 0.0F) - altVaultScreenUiState29.floatValue10) * (1.0F - (float)Math.pow(1.5E-4F, f));
      altVaultScreenUiState29.floatValue11 = altVaultScreenUiState29.floatValue11 + (0.0F - altVaultScreenUiState29.floatValue11) * (1.0F - (float)Math.pow(1.8E-5F, f));
      altVaultScreenUiState29.floatValue12 = altVaultScreenUiState29.floatValue12 + (0.0F - altVaultScreenUiState29.floatValue12) * (1.0F - (float)Math.pow(6.0E-6F, f));
      float floatValue67 = measure9((this.floatValue10 - altVaultScreenUiState29.floatValue2) / Math.max(1.0F, altVaultScreenUiState29.floatValue6), 0.0F, 1.0F);
      float floatValue68 = measure9((this.floatValue11 - altVaultScreenUiState29.floatValue3) / Math.max(1.0F, altVaultScreenUiState29.floatValue7), 0.0F, 1.0F);
      float floatValue69 = 1.0F - (float)Math.pow(1.8E-4F, f);
      altVaultScreenUiState29.floatValue14 = altVaultScreenUiState29.floatValue14 + (floatValue67 - altVaultScreenUiState29.floatValue14) * floatValue69;
      altVaultScreenUiState29.floatValue15 = altVaultScreenUiState29.floatValue15 + (floatValue68 - altVaultScreenUiState29.floatValue15) * floatValue69;
      float floatValue70 = 1.0F + altVaultScreenUiState29.floatValue10 * 0.034F + (altVaultScreenUiState29.flag4 ? 0.008F : 0.0F) - altVaultScreenUiState29.floatValue11 * 0.065F - floatValue63 * 0.2F;
      altVaultScreenUiState29.floatValue13 = altVaultScreenUiState29.springIntegrator.measure(floatValue70, f);
      float floatValue71 = floatValue63 * altVaultScreenUiState29.floatValue7 * 0.35F;
      float floatValue72 = (altVaultScreenUiState29.floatValue14 - 0.5F) * 9.5F * g * altVaultScreenUiState29.floatValue10;
      float floatValue73 = (altVaultScreenUiState29.floatValue15 - 0.5F) * 5.5F * g * altVaultScreenUiState29.floatValue10 - altVaultScreenUiState29.floatValue9 * 1.2F * g + floatValue71;
      altVaultScreenUiState29.floatValue4 = altVaultScreenUiState29.floatValue2 + floatValue72;
      altVaultScreenUiState29.floatValue5 = altVaultScreenUiState29.floatValue3 + floatValue73;
      altVaultScreenUiState29.floatValue16 = measure9(
         measure7(this.floatValue12, this.floatValue13) * 0.5F * altVaultScreenUiState29.floatValue10 + Math.abs(altVaultScreenUiState29.springIntegrator.getFloatValue2()) * 0.04F,
         0.0F,
         1.0F
      );
   }

   private void invoke37(AltVaultScreen.AltVaultScreenState3 altVaultScreenState3, float f, float g, float h) {
      float floatValue74 = measure6(
         this.floatValue6,
         this.floatValue7,
         altVaultScreenState3.floatValue,
         altVaultScreenState3.floatValue2,
         altVaultScreenState3.floatValue5,
         altVaultScreenState3.floatValue6,
         altVaultScreenState3.floatValue7
      );
      boolean flag9 = floatValue74 <= 0.0F && h > 0.5F;
      float floatValue75 = 1.0F - measure8(measure9(Math.max(0.0F, floatValue74) / Math.max(1.0F, 26.0F * g), 0.0F, 1.0F));
      float floatValue76 = flag9 ? Math.max(0.72F, floatValue75) : floatValue75 * 0.54F;
      altVaultScreenState3.floatValue8 = altVaultScreenState3.floatValue8
         + ((flag9 ? 1.0F : 0.0F) - altVaultScreenState3.floatValue8) * (1.0F - (float)Math.pow(1.0E-4F, f));
      altVaultScreenState3.floatValue9 = altVaultScreenState3.floatValue9 + (floatValue76 * h - altVaultScreenState3.floatValue9) * (1.0F - (float)Math.pow(1.4E-4F, f));
      altVaultScreenState3.floatValue10 = altVaultScreenState3.floatValue10 + (0.0F - altVaultScreenState3.floatValue10) * (1.0F - (float)Math.pow(1.8E-5F, f));
      altVaultScreenState3.floatValue11 = altVaultScreenState3.floatValue11 + (0.0F - altVaultScreenState3.floatValue11) * (1.0F - (float)Math.pow(6.0E-6F, f));
      float floatValue77 = measure9((this.floatValue10 - altVaultScreenState3.floatValue) / Math.max(1.0F, altVaultScreenState3.floatValue5), 0.0F, 1.0F);
      float floatValue78 = measure9((this.floatValue11 - altVaultScreenState3.floatValue2) / Math.max(1.0F, altVaultScreenState3.floatValue6), 0.0F, 1.0F);
      float floatValue79 = 1.0F - (float)Math.pow(2.2E-4F, f);
      altVaultScreenState3.floatValue13 = altVaultScreenState3.floatValue13 + (floatValue77 - altVaultScreenState3.floatValue13) * floatValue79;
      altVaultScreenState3.floatValue14 = altVaultScreenState3.floatValue14 + (floatValue78 - altVaultScreenState3.floatValue14) * floatValue79;
      float floatValue80 = 1.0F
         + altVaultScreenState3.floatValue9 * 0.04F
         - altVaultScreenState3.floatValue10 * 0.065F
         + (altVaultScreenState3 instanceof AltVaultScreen.AltVaultScreenVariant altVaultScreenVariant2 && altVaultScreenVariant2.flag2 ? 0.018F : 0.0F);
      altVaultScreenState3.floatValue12 = altVaultScreenState3.springIntegrator.measure(floatValue80, f);
      altVaultScreenState3.floatValue3 = altVaultScreenState3.floatValue + (altVaultScreenState3.floatValue13 - 0.5F) * 5.0F * g * altVaultScreenState3.floatValue9;
      altVaultScreenState3.floatValue4 = altVaultScreenState3.floatValue2
         + (altVaultScreenState3.floatValue14 - 0.5F) * 3.5F * g * altVaultScreenState3.floatValue9
         - altVaultScreenState3.floatValue8 * 1.2F * g;
      altVaultScreenState3.floatValue15 = measure9(
         measure7(this.floatValue12, this.floatValue13) * 0.42F * altVaultScreenState3.floatValue9 + Math.abs(altVaultScreenState3.springIntegrator.getFloatValue2()) * 0.04F,
         0.0F,
         1.0F
      );
   }

   private MainMenuScreen.MainMenuScreenTimedEntry resolve14(int i, int j, int k, float f, float g, long l) {
      float floatValue81 = Math.max(0.0F, (float)(l - this.timestamp3) / 1.0E9F);
      float floatValue82 = measure9(measure7(this.floatValue12, this.floatValue13), 0.0F, 3.0F);
      float floatValue83 = Math.max((float)Math.exp(-floatValue81 * 1.25F), measure9(floatValue82 * 0.22F, 0.0F, 1.0F));
      float floatValue84 = measure8(measure9(this.floatValue5 / 0.86F, 0.0F, 1.0F));
      float floatValue85 = measure5(i, j);
      ArrayList arrayList2 = new ArrayList();

      for (AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState30 : this.items) {
         if (!altVaultScreenUiState30.flag6 && altVaultScreenUiState30.flag7 && !(altVaultScreenUiState30.floatValue18 <= 0.001F)) {
            arrayList2.add(
               new MainMenuScreen.MainMenuScreenBounds(
                  altVaultScreenUiState30.text2,
                  altVaultScreenUiState30.floatValue4,
                  altVaultScreenUiState30.floatValue5,
                  altVaultScreenUiState30.floatValue6,
                  altVaultScreenUiState30.floatValue7,
                  altVaultScreenUiState30.floatValue8,
                  altVaultScreenUiState30.floatValue9,
                  Math.max(altVaultScreenUiState30.floatValue10, altVaultScreenUiState30.flag4 ? 0.34F : 0.0F),
                  altVaultScreenUiState30.floatValue11,
                  altVaultScreenUiState30.floatValue18,
                  altVaultScreenUiState30.floatValue12,
                  altVaultScreenUiState30.floatValue17,
                  altVaultScreenUiState30.floatValue13,
                  altVaultScreenUiState30.floatValue14,
                  altVaultScreenUiState30.floatValue15,
                  altVaultScreenUiState30.floatValue16
               )
            );
         }
      }

      arrayList2.add(this.resolve15(this.altVaultScreenVariant, this.altVaultScreenVariant.floatValue17));

      for (AltVaultScreen.AltVaultScreenUiState2 altVaultScreenUiState210 : this.items2) {
         arrayList2.add(this.resolve15(altVaultScreenUiState210, altVaultScreenUiState210.flag ? altVaultScreenUiState210.floatValue17 : altVaultScreenUiState210.floatValue17 * 0.62F));
      }

      MainMenuScreen.MainMenuScreenData[] w283s = new MainMenuScreen.MainMenuScreenData[14];

      for (int intValue37 = 0; intValue37 < 14; intValue37++) {
         AltVaultScreen.AltVaultScreenState5 altVaultScreenState52 = this.altVaultScreenState5s[intValue37];
         float floatValue86 = Math.max(0.0F, this.floatValue5 - altVaultScreenState52.floatValue3);
         float floatValue87 = floatValue86 > 3.1F ? 0.0F : altVaultScreenState52.floatValue4;
         w283s[intValue37] = new MainMenuScreen.MainMenuScreenData(altVaultScreenState52.floatValue / Math.max(1.0F, (float)i), altVaultScreenState52.floatValue2 / Math.max(1.0F, (float)j), floatValue86, floatValue87);
      }

      return new MainMenuScreen.MainMenuScreenTimedEntry(
         i,
         j,
         k,
         this.floatValue5 * 0.46F,
         this.floatValue10,
         this.floatValue11,
         this.floatValue10 / Math.max(1.0F, (float)i),
         this.floatValue11 / Math.max(1.0F, (float)j),
         this.floatValue12 * 0.42F,
         this.floatValue13 * 0.42F,
         floatValue82 * 0.42F,
         measure10(this.intValue3),
         measure11(this.intValue3),
         measure12(this.intValue3),
         measure10(this.intValue4),
         measure11(this.intValue4),
         measure12(this.intValue4),
         -f * 6.5E-4F,
         -g * 5.0E-4F,
         f * 0.75F * floatValue85,
         g * 0.62F * floatValue85,
         f * 1.2F * floatValue85,
         g * 1.0F * floatValue85,
         floatValue83 * 0.55F,
         floatValue83 > 0.1F ? 0.82F : 0.72F,
         0.58F + floatValue84 * 0.18F,
         0.0F,
         this.theme == Theme.SAKURA_BREEZE,
         this.theme == Theme.VERNAL_SOLSTICE,
         this.theme == Theme.MIDNIGHT_AZURE,
         this.flag4,
         null,
         null,
         List.of(),
         List.of(),
         List.of(),
         new MainMenuScreen.MainMenuScreenBounds3(0.0F, 0.0F, 0.0F, 0.0F, 0.0F),
         arrayList2,
         w283s
      );
   }

   private MainMenuScreen.MainMenuScreenBounds resolve15(AltVaultScreen.AltVaultScreenState3 altVaultScreenState32, float f) {
      return new MainMenuScreen.MainMenuScreenBounds(
         altVaultScreenState32.text,
         altVaultScreenState32.floatValue3,
         altVaultScreenState32.floatValue4,
         altVaultScreenState32.floatValue5,
         altVaultScreenState32.floatValue6,
         altVaultScreenState32.floatValue7,
         altVaultScreenState32.floatValue8,
         altVaultScreenState32.floatValue9,
         altVaultScreenState32.floatValue10,
         f,
         altVaultScreenState32.floatValue11,
         altVaultScreenState32.floatValue16,
         altVaultScreenState32.floatValue12,
         altVaultScreenState32.floatValue13,
         altVaultScreenState32.floatValue14,
         altVaultScreenState32.floatValue15
      );
   }

   private void invoke38(MainMenuScreen.MainMenuScreenTimedEntry mainMenuScreenTimedEntry2) {
      try {
         WildClient.invoke15();
         RenderManager renderManager = WildClient.resolve();
         if (renderManager == null) {
            return;
         }

         FramebufferUtils.GlStateSnapshot glStateSnapshot2 = FramebufferUtils.captureGlState();
         boolean flag10 = false;

         try {
            renderManager.invoke(mainMenuScreenTimedEntry2.framebufferWidth(), mainMenuScreenTimedEntry2.framebufferHeight());
            flag10 = true;
            float floatValue88 = measure5(mainMenuScreenTimedEntry2.framebufferWidth(), mainMenuScreenTimedEntry2.framebufferHeight());
            float floatValue89 = measure8(measure9(this.floatValue5 / 0.82F, 0.0F, 1.0F));
            renderManager.invoke20();
            renderManager.invoke24(
               this.floatValue - 15.0F * floatValue88,
               this.floatValue2 - 5.0F * floatValue88,
               this.floatValue3 + 30.0F * floatValue88,
               this.floatValue4 + 10.0F * floatValue88,
               0.0F,
               0.0F,
               0.0F,
               0.0F
            );
            AltVaultScreen.AltVaultScreenBounds altVaultScreenBounds = null;

            for (AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState31 : this.items) {
               if (!altVaultScreenUiState31.flag6 && altVaultScreenUiState31.flag7 && altVaultScreenUiState31.floatValue18 > 0.002F) {
                  AltVaultScreen.AltVaultScreenBounds altVaultScreenBounds2 = this.resolve16(renderManager, altVaultScreenUiState31, floatValue88);
                  if (altVaultScreenBounds2 != null) {
                     altVaultScreenBounds = altVaultScreenBounds2;
                  }
               }
            }

            renderManager.invoke20();
            renderManager.invoke25();
            if (this.flag6) {
               float floatValue90 = measure8(measure9(this.floatValue5 / 0.82F, 0.0F, 1.0F));
               renderManager.invoke5(
                  this.floatValue19,
                  this.floatValue20,
                  this.floatValue21,
                  this.floatValue22,
                  this.floatValue21 * 0.5F,
                  this.flag4 ? compute9(0.0F, 0.0F, 0.0F, 0.045F * floatValue90) : compute9(1.0F, 1.0F, 1.0F, 0.05F * floatValue90)
               );
               int intValue38 = compute10(this.intValue4, this.intValue3, 0.5F, (this.flag5 ? 0.75F : 0.45F) * floatValue90);
               renderManager.invoke5(this.floatValue19, this.floatValue23, this.floatValue21, this.floatValue24, this.floatValue21 * 0.5F, intValue38);
            }

            this.invoke41(renderManager, this.altVaultScreenVariant, floatValue88);

            for (AltVaultScreen.AltVaultScreenUiState2 altVaultScreenUiState211 : this.items2) {
               this.invoke42(renderManager, altVaultScreenUiState211, floatValue88);
            }

            float floatValue91 = mainMenuScreenTimedEntry2.framebufferWidth() * 0.5F + mainMenuScreenTimedEntry2.uiParallaxX() * 0.12F;
            float floatValue92 = mainMenuScreenTimedEntry2.framebufferHeight() * 0.126F + mainMenuScreenTimedEntry2.uiParallaxY() * 0.08F;
            renderManager.invoke70(FontRegistry.fontObject4, floatValue91, floatValue92, 40.0F * floatValue88, "Alt Manager", this.compute4(0.94F * floatValue89), "c");
            renderManager.invoke70(
               FontRegistry.fontObject,
               floatValue91,
               floatValue92 + 30.0F * floatValue88,
               24.0F * floatValue88,
               this.selectAnIdentity + "  /  " + this.resolve21(),
               this.compute5(0.5F * floatValue89),
               "c"
            );
            if (altVaultScreenBounds != null) {
               try {
                  this.invoke39(renderManager, altVaultScreenBounds, floatValue88, mainMenuScreenTimedEntry2.framebufferWidth(), mainMenuScreenTimedEntry2.framebufferHeight());
               } catch (Throwable exception2) {
               }
            }
         } finally {
            if (flag10) {
               try {
                  renderManager.invoke19();
               } catch (Throwable exception3) {
               }
            }

            FramebufferUtils.restoreGlState(glStateSnapshot2);
         }
      } catch (Throwable exception4) {
      }
   }

   private AltVaultScreen.AltVaultScreenBounds resolve16(RenderManager renderManager2, AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState32, float f) {
      float floatValue93 = altVaultScreenUiState32.floatValue18;
      float floatValue94 = 0.5F + 0.5F * (float)Math.sin(this.floatValue5 * (altVaultScreenUiState32.flag4 ? 1.25F : 0.86F) + altVaultScreenUiState32.text2.hashCode() * 0.01F);
      float floatValue95 = measure4(Math.min(altVaultScreenUiState32.floatValue7 * 0.64F, 56.0F * f));
      float floatValue96 = altVaultScreenUiState32.floatValue4 + 25.0F * f;
      float floatValue97 = altVaultScreenUiState32.floatValue5 + altVaultScreenUiState32.floatValue7 * 0.5F - floatValue95 * 0.5F;
      this.invoke44(renderManager2, altVaultScreenUiState32, floatValue96, measure4(floatValue97), floatValue95, floatValue93, floatValue94, f);
      float floatValue98 = floatValue96 + floatValue95 + 18.0F * f;
      AltVaultScreen.AltVaultScreenData altVaultScreenData = AltVaultScreen.AltVaultScreenState4.resolve(altVaultScreenUiState32.text, altVaultScreenUiState32.text2);
      float floatValue99 = altVaultScreenUiState32.floatValue4 + altVaultScreenUiState32.floatValue6 - 25.0F * f - floatValue95;
      float floatValue100 = measure4(floatValue97);
      float floatValue101 = Math.max(88.0F * f, altVaultScreenUiState32.floatValue6 - (floatValue98 - altVaultScreenUiState32.floatValue4) - 24.0F * f);
      if (altVaultScreenData != null) {
         floatValue101 = Math.max(54.0F * f, floatValue99 - floatValue98 - 14.0F * f);
      }

      float floatValue102 = 25.0F * f;
      String text30 = resolve25(altVaultScreenUiState32.text2, floatValue101, floatValue102, FontRegistry.fontObject4);
      renderManager2.invoke69(
         FontRegistry.fontObject4,
         floatValue98,
         altVaultScreenUiState32.floatValue5 + altVaultScreenUiState32.floatValue7 * 0.38F,
         floatValue102,
         text30,
         this.compute4((0.88F + altVaultScreenUiState32.floatValue10 * 0.08F) * floatValue93)
      );
      AltVaultScreen.AltVaultScreenBounds altVaultScreenBounds3 = null;
      if (altVaultScreenData != null) {
         altVaultScreenBounds3 = this.resolve17(renderManager2, altVaultScreenUiState32, altVaultScreenData, floatValue99, floatValue100, floatValue95, f, floatValue93);
      }

      String text31 = altVaultScreenUiState32.flag4 ? "Active session" : (altVaultScreenUiState32.flag3 ? "Offline / Selected" : "Offline identity");
      float floatValue103 = 18.0F * f;
      float floatValue104 = RenderManager.resolve7(FontRegistry.fontObject, text31, floatValue103).floatValue;
      float floatValue105 = 22.0F * f;
      float floatValue106 = 6.0F * f;
      float floatValue107 = floatValue104 + floatValue106 + 18.0F * f;
      float floatValue108 = altVaultScreenUiState32.floatValue5 + altVaultScreenUiState32.floatValue7 * 0.56F;
      int intValue39 = altVaultScreenUiState32.flag4
         ? compute9(0.2F, 1.0F, 0.4F, 0.12F * floatValue93)
         : (
            this.flag4
               ? compute9(0.0F, 0.0F, 0.0F, (altVaultScreenUiState32.flag3 ? 0.05F : 0.025F) * floatValue93)
               : compute9(1.0F, 1.0F, 1.0F, (altVaultScreenUiState32.flag3 ? 0.08F : 0.035F) * floatValue93)
         );
      int intValue40 = altVaultScreenUiState32.flag4
         ? compute10(this.intValue4, this.intValue3, floatValue94, floatValue93)
         : (
            this.flag4
               ? compute9(0.1F, 0.1F, 0.1F, (altVaultScreenUiState32.flag3 ? 0.66F : 0.3F) * floatValue93)
               : compute9(1.0F, 1.0F, 1.0F, (altVaultScreenUiState32.flag3 ? 0.7F : 0.25F) * floatValue93)
         );
      int intValue41 = altVaultScreenUiState32.flag4
         ? compute9(0.28F, 0.62F, 0.34F, (0.8F + altVaultScreenUiState32.floatValue10 * 0.2F) * floatValue93)
         : (
            this.flag4
               ? compute9(0.23F, 0.23F, 0.23F, (altVaultScreenUiState32.flag3 ? 0.82F : 0.52F) * floatValue93)
               : compute9(1.0F, 1.0F, 1.0F, (altVaultScreenUiState32.flag3 ? 0.85F : 0.45F) * floatValue93)
         );
      renderManager2.invoke5(floatValue98, floatValue108, floatValue107, floatValue105, floatValue105 * 0.5F, intValue39);
      renderManager2.invoke5(floatValue98 + 7.0F * f, floatValue108 + floatValue105 * 0.5F - floatValue106 * 0.5F, floatValue106, floatValue106, floatValue106 * 0.5F, intValue40);
      renderManager2.invoke69(FontRegistry.fontObject, floatValue98 + floatValue106 + 12.0F * f, floatValue108 + floatValue105 * 0.61F, floatValue103, text31, intValue41);
      return altVaultScreenBounds3;
   }

   private AltVaultScreen.AltVaultScreenBounds resolve17(
      RenderManager renderManager3, AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState33, AltVaultScreen.AltVaultScreenData altVaultScreenData2, float f, float g, float h, float i, float j
   ) {
      boolean flag11 = this.floatValue6 >= f && this.floatValue6 <= f + h && this.floatValue7 >= g && this.floatValue7 <= g + h;
      float floatValue109 = flag11 ? 1.0F : Math.max(altVaultScreenUiState33.floatValue9 * 0.35F, altVaultScreenUiState33.flag3 ? 0.18F : 0.0F);
      float floatValue110 = h * 0.32F;
      int intValue42 = this.flag4 ? compute9(1.0F, 1.0F, 1.0F, (0.3F + floatValue109 * 0.12F) * j) : compute9(1.0F, 1.0F, 1.0F, (0.055F + floatValue109 * 0.06F) * j);
      int intValue43 = this.flag4 ? compute9(0.0F, 0.0F, 0.0F, (0.045F + floatValue109 * 0.05F) * j) : compute9(0.0F, 0.0F, 0.0F, (0.18F + floatValue109 * 0.08F) * j);
      int intValue44 = compute10(this.intValue4, this.intValue3, 0.45F + floatValue109 * 0.25F, (0.18F + floatValue109 * 0.26F) * j);
      int intValue45 = compute10(this.intValue4, this.intValue3, 0.35F + altVaultScreenUiState33.floatValue10 * 0.25F, (0.72F + floatValue109 * 0.2F) * j);
      int intValue46 = this.flag4 ? compute9(0.08F, 0.1F, 0.12F, (0.38F + floatValue109 * 0.24F) * j) : compute9(1.0F, 1.0F, 1.0F, (0.3F + floatValue109 * 0.26F) * j);
      renderManager3.invoke31(f, g, h, h, floatValue110, intValue42, intValue42, intValue43, intValue43);
      renderManager3.invoke28(f, g, h, h, floatValue110, intValue44, Math.max(0.7F, 1.0F * i));
      AltVaultScreen.AltVaultScreenResources altVaultScreenResources = this.resolve18(altVaultScreenData2);
      int intValue47 = altVaultScreenResources == null ? 0 : altVaultScreenResources.compute();
      if (intValue47 > 0) {
         renderManager3.invoke12(intValue47, f + 2.0F * i, g + 2.0F * i, h - 4.0F * i, h - 4.0F * i, 0.0F, 0.0F, 1.0F, 1.0F, h * 0.24F);
         renderManager3.invoke5(f, g, h, h, floatValue110, compute9(1.0F, 1.0F, 1.0F, (0.03F + floatValue109 * 0.03F) * j));
      } else {
         float floatValue111 = f + h * 0.27F;
         float floatValue112 = g + h * 0.27F;
         float floatValue113 = h * 0.46F;
         float floatValue114 = h * 0.13F;
         float floatValue115 = h * 0.075F;

         for (int intValue48 = 0; intValue48 < 3; intValue48++) {
            float floatValue116 = floatValue112 + intValue48 * (floatValue114 + floatValue115);
            renderManager3.invoke5(floatValue111, floatValue116, floatValue113, floatValue114, floatValue114 * 0.5F, intValue48 == 1 ? intValue46 : intValue45);
            renderManager3.invoke5(
               floatValue111 + floatValue113 - floatValue114 * 0.75F,
               floatValue116 + floatValue114 * 0.3F,
               floatValue114 * 0.4F,
               floatValue114 * 0.4F,
               floatValue114 * 0.2F,
               this.flag4 ? compute9(1.0F, 1.0F, 1.0F, 0.62F * j) : compute9(0.0F, 0.0F, 0.0F, 0.35F * j)
            );
         }
      }

      return flag11 ? new AltVaultScreen.AltVaultScreenBounds(altVaultScreenData2, f + h * 0.5F, g, j) : null;
   }

   private void invoke39(RenderManager renderManager4, AltVaultScreen.AltVaultScreenBounds altVaultScreenBounds4, float f, float g, float h) {
      String text32 = resolve25(resolve24(altVaultScreenBounds4.server.displayName()), 190.0F * f, 17.0F * f, FontRegistry.fontObject4);
      String text33 = "IP: " + resolve25(resolve24(altVaultScreenBounds4.server.address()), 190.0F * f, 15.0F * f, FontRegistry.fontObject);
      String text34 = "Active session: " + resolve26(altVaultScreenBounds4.server.totalMs());
      float floatValue117 = 17.0F * f;
      float floatValue118 = 15.0F * f;
      float floatValue119 = 12.0F * f;
      float floatValue120 = 9.0F * f;
      float floatValue121 = 4.0F * f;
      float floatValue122 = RenderManager.resolve7(FontRegistry.fontObject4, text32, floatValue117).floatValue;
      float floatValue123 = RenderManager.resolve7(FontRegistry.fontObject, text33, floatValue118).floatValue;
      float floatValue124 = RenderManager.resolve7(FontRegistry.fontObject, text34, floatValue118).floatValue;
      float floatValue125 = Math.max(floatValue122, Math.max(floatValue123, floatValue124)) + floatValue119 * 2.0F;
      float floatValue126 = floatValue120 * 2.0F + floatValue117 + floatValue121 + floatValue118 + floatValue121 + floatValue118 * 0.92F;
      float floatValue127 = measure9(altVaultScreenBounds4.centerX() - floatValue125 * 0.5F, 8.0F * f, g - floatValue125 - 8.0F * f);
      float floatValue128 = altVaultScreenBounds4.topY() - floatValue126 - 10.0F * f;
      if (floatValue128 < 8.0F * f) {
         floatValue128 = altVaultScreenBounds4.topY() + 30.0F * f;
      }

      floatValue128 = measure9(floatValue128, 8.0F * f, h - floatValue126 - 8.0F * f);
      float floatValue129 = altVaultScreenBounds4.alpha();
      float floatValue130 = 10.0F * f;
      renderManager4.invoke5(
         floatValue127, floatValue128, floatValue125, floatValue126, floatValue130, this.flag4 ? compute9(1.0F, 1.0F, 1.0F, 0.88F * floatValue129) : compute9(0.02F, 0.025F, 0.035F, 0.86F * floatValue129)
      );
      renderManager4.invoke28(
         floatValue127, floatValue128, floatValue125, floatValue126, floatValue130, compute10(this.intValue4, this.intValue3, 0.48F, 0.24F * floatValue129), Math.max(0.7F, 1.0F * f)
      );
      renderManager4.invoke69(FontRegistry.fontObject4, floatValue127 + floatValue119, floatValue128 + floatValue120 + floatValue117 * 0.73F, floatValue117, text32, this.compute4(0.92F * floatValue129));
      renderManager4.invoke69(FontRegistry.fontObject, floatValue127 + floatValue119, floatValue128 + floatValue120 + floatValue117 + floatValue121 + floatValue118 * 0.75F, floatValue118, text33, this.compute5(0.76F * floatValue129));
      renderManager4.invoke69(
         FontRegistry.fontObject, floatValue127 + floatValue119, floatValue128 + floatValue120 + floatValue117 + floatValue121 + floatValue118 + floatValue121 + floatValue118 * 0.75F, floatValue118, text34, this.compute5(0.7F * floatValue129)
      );
   }

   private AltVaultScreen.AltVaultScreenResources resolve18(AltVaultScreen.AltVaultScreenData altVaultScreenData3) {
      byte[] byteValues = altVaultScreenData3 == null ? null : altVaultScreenData3.favicon();
      if (byteValues != null && byteValues.length != 0) {
         String text35 = altVaultScreenData3.address() + ":" + Arrays.hashCode(byteValues);
         AltVaultScreen.AltVaultScreenResources altVaultScreenResources2 = this.valuesByKey.get(text35);
         if (altVaultScreenResources2 != null) {
            return altVaultScreenResources2;
         } else {
            try {
               NativeImage nativeImage = NativeImage.read(byteValues);
               NativeImageBackedTexture nativeImageBackedTexture2 = new NativeImageBackedTexture(() -> "wild_alt_server_icon", nativeImage);
               nativeImageBackedTexture2.setFilter(true, false);
               nativeImageBackedTexture2.upload();
               AltVaultScreen.AltVaultScreenResources altVaultScreenResources3 = new AltVaultScreen.AltVaultScreenResources(nativeImageBackedTexture2);
               this.valuesByKey.put(text35, altVaultScreenResources3);
               return altVaultScreenResources3;
            } catch (Throwable exception5) {
               return null;
            }
         }
      } else {
         return null;
      }
   }

   private void invoke40() {
      for (AltVaultScreen.AltVaultScreenResources altVaultScreenResources4 : this.valuesByKey.values()) {
         altVaultScreenResources4.close();
      }

      this.valuesByKey.clear();
   }

   private void invoke41(RenderManager renderManager5, AltVaultScreen.AltVaultScreenVariant altVaultScreenVariant3, float f) {
      float floatValue131 = altVaultScreenVariant3.floatValue17;
      String text36 = altVaultScreenVariant3.flag ? "*".repeat(altVaultScreenVariant3.text.length()) : altVaultScreenVariant3.text;
      boolean flag12 = text36.isBlank();
      float floatValue132 = 22.0F * f;
      float floatValue133 = altVaultScreenVariant3.floatValue3 + floatValue132;
      float floatValue134 = Math.max(8.0F * f, altVaultScreenVariant3.floatValue5 - floatValue132 * 2.0F);
      float floatValue135 = 18.0F * f;
      boolean flag13 = flag12 && !altVaultScreenVariant3.flag2;
      String text37 = flag13 ? altVaultScreenVariant3.label : text36;
      int intValue49 = flag13 ? this.compute5(0.42F * floatValue131) : this.compute4((0.78F + altVaultScreenVariant3.floatValue * 0.18F) * floatValue131);
      renderManager5.invoke24(
         floatValue133,
         altVaultScreenVariant3.floatValue4 + 3.0F * f,
         floatValue134,
         altVaultScreenVariant3.floatValue6 - 6.0F * f,
         altVaultScreenVariant3.floatValue7 * 0.55F,
         altVaultScreenVariant3.floatValue7 * 0.55F,
         altVaultScreenVariant3.floatValue7 * 0.55F,
         altVaultScreenVariant3.floatValue7 * 0.55F
      );
      if (!text37.isBlank()) {
         if (flag13) {
            invoke45(
               renderManager5,
               FontRegistry.fontObject,
               altVaultScreenVariant3.floatValue3,
               altVaultScreenVariant3.floatValue4,
               altVaultScreenVariant3.floatValue5,
               altVaultScreenVariant3.floatValue6,
               floatValue135,
               text37,
               intValue49
            );
         } else {
            float floatValue136 = measure3(FontRegistry.fontObject, floatValue135, altVaultScreenVariant3.floatValue4, altVaultScreenVariant3.floatValue6);
            if (altVaultScreenVariant3.flag3) {
               float floatValue137 = RenderManager.resolve7(FontRegistry.fontObject, text37, floatValue135).floatValue;
               renderManager5.invoke5(
                  measure4(floatValue133 - altVaultScreenVariant3.floatValue3 - 2.0F * f),
                  altVaultScreenVariant3.floatValue4 + altVaultScreenVariant3.floatValue6 * 0.25F,
                  floatValue137 + 4.0F * f,
                  altVaultScreenVariant3.floatValue6 * 0.5F,
                  2.0F * f,
                  compute9(0.25F, 0.55F, 0.95F, 0.45F * floatValue131)
               );
            }

            renderManager5.invoke69(FontRegistry.fontObject, measure4(floatValue133 - altVaultScreenVariant3.floatValue3), measure4(floatValue136), floatValue135, text37, intValue49);
         }
      }

      if (altVaultScreenVariant3.flag2) {
         String text38 = text36.substring(0, compute8(altVaultScreenVariant3.intValue, 0, text36.length()));
         float floatValue138 = RenderManager.resolve7(FontRegistry.fontObject, text38, floatValue135).floatValue;
         if (altVaultScreenVariant3.flag3) {
            floatValue138 = RenderManager.resolve7(FontRegistry.fontObject, text36, floatValue135).floatValue;
         }

         float floatValue139 = altVaultScreenVariant3.floatValue3;
         float floatValue140 = floatValue134 - 9.0F * f;
         if (floatValue138 - floatValue139 > floatValue140) {
            floatValue139 = floatValue138 - floatValue140;
         }

         if (floatValue138 - floatValue139 < 0.0F) {
            floatValue139 = floatValue138;
         }

         floatValue139 = Math.max(0.0F, floatValue139);
         altVaultScreenVariant3.floatValue3 = altVaultScreenVariant3.floatValue3 + (floatValue139 - altVaultScreenVariant3.floatValue3) * 0.3F;
         altVaultScreenVariant3.floatValue2 = altVaultScreenVariant3.floatValue2 + (floatValue138 - altVaultScreenVariant3.floatValue2) * 0.3F;
         float floatValue141 = 0.54F + 0.46F * (float)Math.sin(this.floatValue5 * 5.4F);
         if (!altVaultScreenVariant3.flag3) {
            int intValue50 = compute10(this.intValue4, this.intValue3, floatValue141, (0.42F + floatValue141 * 0.36F) * floatValue131);
            float floatValue142 = floatValue133 + altVaultScreenVariant3.floatValue2 - altVaultScreenVariant3.floatValue3 + 2.0F * f;
            float floatValue143 = 20.0F * f;
            float floatValue144 = altVaultScreenVariant3.floatValue4 + (altVaultScreenVariant3.floatValue6 - floatValue143) * 0.5F;
            renderManager5.invoke5(measure4(floatValue142), measure4(floatValue144), Math.max(1.25F * f, 1.0F), floatValue143, 1.0F * f, intValue50);
         }

         renderManager5.invoke28(
            altVaultScreenVariant3.floatValue3,
            altVaultScreenVariant3.floatValue4,
            altVaultScreenVariant3.floatValue5,
            altVaultScreenVariant3.floatValue6,
            altVaultScreenVariant3.floatValue7,
            compute10(this.intValue4, this.intValue3, floatValue141, 0.24F * floatValue131 * (0.35F + altVaultScreenVariant3.floatValue * 0.65F)),
            1.0F * f
         );
      }

      renderManager5.invoke25();
   }

   private void invoke42(RenderManager renderManager6, AltVaultScreen.AltVaultScreenUiState2 altVaultScreenUiState212, float f) {
      float floatValue145 = altVaultScreenUiState212.floatValue17 * (altVaultScreenUiState212.flag ? 0.92F : 0.28F);
      String text39 = resolve25(altVaultScreenUiState212.text, altVaultScreenUiState212.floatValue5 - 20.0F * f, 18.0F * f, FontRegistry.fontObject);
      invoke45(
         renderManager6,
         FontRegistry.fontObject,
         altVaultScreenUiState212.floatValue3,
         altVaultScreenUiState212.floatValue4,
         altVaultScreenUiState212.floatValue5,
         altVaultScreenUiState212.floatValue6,
         18.0F * f,
         text39,
         this.compute4(floatValue145)
      );
   }

   private void invoke43(RenderManager renderManager7, AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState34, float f, float g, float h, float i) {
      float floatValue146 = h * 0.34F;

      try {
         MinecraftClient client6 = this.client == null ? MinecraftClient.getInstance() : this.client;
         Identifier identifier = this.resolve19(client6, altVaultScreenUiState34);
         AbstractTexture abstractTexture = client6.getTextureManager().getTexture(identifier);
         if (abstractTexture != null && abstractTexture.getGlTexture() instanceof GlTexture glTexture && glTexture.getGlId() > 0) {
            int intValue51 = glTexture.getGlId();
            if (altVaultScreenUiState34.intValue != intValue51) {
               abstractTexture.setFilter(false, false);
               GL11.glBindTexture(3553, intValue51);
               GL11.glTexParameteri(3553, 10241, 9728);
               GL11.glTexParameteri(3553, 10240, 9728);
               altVaultScreenUiState34.intValue = intValue51;
            }

            renderManager7.invoke65(i);
            renderManager7.invoke12(intValue51, measure4(f), measure4(g), measure4(h), measure4(h), 0.125F, 0.125F, 0.25F, 0.25F, floatValue146);
            renderManager7.invoke12(intValue51, measure4(f), measure4(g), measure4(h), measure4(h), 0.625F, 0.125F, 0.75F, 0.25F, floatValue146);
            renderManager7.invoke66();
            return;
         }
      } catch (Throwable exception6) {
      }

      renderManager7.invoke5(f, g, h, h, floatValue146, this.flag4 ? compute9(1.0F, 1.0F, 1.0F, 0.74F * i) : compute9(0.05F, 0.06F, 0.075F, 0.78F * i));
      invoke45(renderManager7, FontRegistry.fontObject4, f, g, h, h, h * 0.52F, altVaultScreenUiState34.text3, this.compute4(0.72F * i));
   }

   private void invoke44(RenderManager renderManager8, AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState35, float f, float g, float h, float i, float j, float k) {
      float floatValue147 = h * 0.34F;
      int intValue52 = this.compute3(altVaultScreenUiState35, j, i);
      renderManager8.invoke5(f, g, h, h, floatValue147, intValue52);
      this.invoke43(renderManager8, altVaultScreenUiState35, f, g, h, i);
      renderManager8.invoke31(
         f,
         g,
         h,
         h,
         floatValue147,
         compute9(1.0F, 1.0F, 1.0F, 0.1F * i),
         compute9(1.0F, 1.0F, 1.0F, 0.035F * i),
         compute9(0.0F, 0.0F, 0.0F, 0.04F * i),
         compute9(0.0F, 0.0F, 0.0F, 0.02F * i)
      );
      renderManager8.invoke28(f, g, h, h, floatValue147, this.flag4 ? compute9(1.0F, 1.0F, 1.0F, 0.58F * i) : compute9(1.0F, 1.0F, 1.0F, 0.16F * i), 1.0F * k);
   }

   private int compute3(AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState36, float f, float g) {
      float floatValue148 = (altVaultScreenUiState36.flag4 ? 0.24F : 0.12F) + altVaultScreenUiState36.floatValue10 * 0.1F + f * 0.05F;
      if (altVaultScreenUiState36.flag4) {
         return compute10(this.intValue4, this.intValue3, f, floatValue148 * g);
      } else {
         return this.flag4 ? compute9(0.0F, 0.0F, 0.0F, floatValue148 * 0.26F * g) : compute9(0.48F, 0.58F, 0.7F, floatValue148 * 0.72F * g);
      }
   }

   private int compute4(float f) {
      return this.flag4 ? compute9(0.1F, 0.1F, 0.1F, f) : compute9(1.0F, 1.0F, 1.0F, f);
   }

   private int compute5(float f) {
      return this.flag4 ? compute9(0.4F, 0.4F, 0.4F, f) : compute9(0.8F, 0.86F, 0.9F, f);
   }

   private static void invoke45(RenderManager renderManager9, FontObject fontObject, float f, float g, float h, float i, float j, String string, int k) {
      String text40 = string == null ? "" : string;
      float floatValue149 = RenderManager.resolve7(fontObject, text40, j).floatValue;
      float floatValue150 = measure4(f + (h - floatValue149) * 0.5F);
      float floatValue151 = measure4(measure3(fontObject, j, g, i));
      renderManager9.invoke69(fontObject, floatValue150, floatValue151, j, text40, k);
   }

   private boolean check4(AltVaultScreen.AltVaultScreenState2 altVaultScreenState22) {
      return switch (altVaultScreenState22) {
         case USE, DELETE -> this.resolve13() != null;
         case ADD_CRACKED -> !resolve22(this.altVaultScreenVariant.text).isBlank();
         case RANDOM -> true;
         case EDIT -> this.check2();
         case BACK -> true;
      };
   }

   private Identifier resolve19(MinecraftClient minecraftClient, AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState37) {
      if (!altVaultScreenUiState37.flag2) {
         altVaultScreenUiState37.identifier = minecraftClient.getSkinProvider().getSkinTextures(altVaultScreenUiState37.gameProfile).texture();
         altVaultScreenUiState37.flag2 = true;
      }

      return altVaultScreenUiState37.identifier;
   }

   private boolean check5(AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState38, String string) {
      return !string.isBlank() && string.equalsIgnoreCase(altVaultScreenUiState38.text2);
   }

   private String resolve20() {
      MinecraftClient client7 = this.client == null ? MinecraftClient.getInstance() : this.client;
      return client7 != null && client7.getSession() != null ? client7.getSession().getUsername() : "";
   }

   private String resolve21() {
      MinecraftClient client8 = this.client == null ? MinecraftClient.getInstance() : this.client;
      return client8 != null && client8.getSession() != null ? "Active: " + client8.getSession().getUsername() : "No active session";
   }

   private void invoke46(int i) {
      if (this.compute6() == 0) {
         this.intValue5 = -1;
         this.selectAnIdentity = "No identities";
      } else {
         int intValue53 = this.intValue5 < 0 ? (i >= 0 ? -1 : this.items.size()) : this.intValue5;

         for (int intValue54 = 0; intValue54 < this.items.size(); intValue54++) {
            intValue53 = compute8(intValue53 + i, 0, this.items.size() - 1);
            AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState39 = this.items.get(intValue53);
            if (!altVaultScreenUiState39.flag6 && !altVaultScreenUiState39.flag5) {
               this.intValue5 = intValue53;
               altVaultScreenUiState39.floatValue12 = Math.max(altVaultScreenUiState39.floatValue12, 0.24F);
               this.selectAnIdentity = "Selected identity: " + altVaultScreenUiState39.text2;
               this.invoke47();
               this.invoke6();
               return;
            }

            if (i > 0 && intValue53 == this.items.size() - 1 || i < 0 && intValue53 == 0) {
               return;
            }
         }
      }
   }

   private void invoke47() {
      if (this.intValue5 >= 0 && this.intValue5 < this.items.size()) {
         AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState40 = this.items.get(this.intValue5);
         if (!altVaultScreenUiState40.flag6 && !altVaultScreenUiState40.flag5) {
            int intValue55 = this.compute7(this.intValue5);
            int intValue56 = (int)Math.floor(this.floatValue16);
            int intValue57 = intValue56 + this.intValue6 - 1;
            if (intValue55 < intValue56 || intValue55 > intValue57) {
               if (intValue55 < this.floatValue16) {
                  this.floatValue16 = intValue55;
               }

               if (intValue55 > this.floatValue16 + this.intValue6 - 1.0F) {
                  this.floatValue16 = intValue55 - this.intValue6 + 1;
               }

               int intValue58 = Math.max(0, this.compute6() - Math.max(1, this.intValue6));
               this.floatValue16 = measure9(this.floatValue16, 0.0F, (float)intValue58);
            }
         }
      }
   }

   private int compute6() {
      int intValue59 = 0;

      for (AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState41 : this.items) {
         if (!altVaultScreenUiState41.flag5 && !altVaultScreenUiState41.flag6) {
            intValue59++;
         }
      }

      return intValue59;
   }

   private int compute7(int i) {
      int intValue60 = 0;

      for (int intValue61 = 0; intValue61 < i; intValue61++) {
         if (!this.items.get(intValue61).flag5) {
            intValue60++;
         }
      }

      return intValue60;
   }

   private float measure(Window window, double d) {
      return (float)(d * window.getFramebufferWidth() / Math.max(1.0, (double)window.getScaledWidth()));
   }

   private float measure2(Window window, double d) {
      return (float)(d * window.getFramebufferHeight() / Math.max(1.0, (double)window.getScaledHeight()));
   }

   static String resolve22(String string) {
      if (string == null) {
         return "";
      } else {
         String text41 = string.trim();
         int intValue62 = text41.indexOf(64);
         if (intValue62 > 0) {
            text41 = text41.substring(0, intValue62);
         }

         text41 = text41.replaceAll("[^A-Za-z0-9_]", "");
         if (text41.length() > 16) {
            text41 = text41.substring(0, 16);
         }

         return text41;
      }
   }

   static String resolvePassword(String string) {
      if (string == null) {
         return "";
      } else {
         String text = string.trim();
         int at = text.indexOf(64);
         return at < 0 ? "" : text.substring(at + 1).trim();
      }
   }

   static boolean isAccountInputChar(char c) {
      return c >= 33 && c <= 126 && c != 167;
   }

   static String sanitizeAccountInput(String string) {
      if (string == null || string.isEmpty()) {
         return "";
      } else {
         StringBuilder builder = new StringBuilder(string.length());

         for (int i = 0; i < string.length(); i++) {
            char c = string.charAt(i);
            if (isAccountInputChar(c)) {
               builder.append(c);
            }
         }

         return builder.toString();
      }
   }

   private void invoke48(Session session, boolean bl) {
      if (session != null) {
         String text42 = resolve22(session.getUsername());
         if (!text42.isBlank()) {
            for (AltVaultScreen.AltVaultScreenUiState altVaultScreenUiState42 : this.items) {
               if (!altVaultScreenUiState42.flag6 && !altVaultScreenUiState42.flag5 && altVaultScreenUiState42.text2.equalsIgnoreCase(text42)) {
                  return;
               }
            }

            AltVaultScreen.AltVaultScreenState altVaultScreenState = session.getAccountType() == AccountType.LEGACY ? AltVaultScreen.AltVaultScreenState.CRACKED : AltVaultScreen.AltVaultScreenState.PREMIUM;
            long longValue5 = System.currentTimeMillis();
            this.items.add(0, new AltVaultScreen.AltVaultScreenUiState(text42, altVaultScreenState, bl, this.floatValue5, resolve23(text42, altVaultScreenState), longValue5, longValue5));
         }
      }
   }

   static String resolve23(String string, AltVaultScreen.AltVaultScreenState altVaultScreenState4) {
      return UUID.nameUUIDFromBytes(("wild-alt-vault:" + altVaultScreenState4.name() + ":" + string.toLowerCase(Locale.ROOT)).getBytes(StandardCharsets.UTF_8))
         .toString();
   }

   private static float measure3(FontObject fontObject2, float f, float g, float h) {
      try {
         return g + h * 0.5F + FontRegistry.measure(fontObject2, 72, f * 0.5F);
      } catch (Throwable exception7) {
         return g + h * 0.5F + f * 0.18F;
      }
   }

   private static float measure4(float f) {
      return Math.round(f);
   }

   private static String resolve24(String string) {
      if (string != null && !string.isEmpty()) {
         StringBuilder stringBuilder4 = new StringBuilder(string.length());

         for (int intValue63 = 0; intValue63 < string.length(); intValue63++) {
            char character3 = string.charAt(intValue63);
            if (character3 == 167) {
               intValue63++;
            } else if (character3 == '&' && intValue63 + 1 < string.length() && check6(string.charAt(intValue63 + 1))) {
               intValue63++;
            } else if (!Character.isISOControl(character3)) {
               stringBuilder4.append(character3);
            }
         }

         return stringBuilder4.toString().trim();
      } else {
         return "";
      }
   }

   private static boolean check6(char c) {
      return c >= '0' && c <= '9' || c >= 'a' && c <= 'f' || c >= 'A' && c <= 'F' || c >= 'k' && c <= 'o' || c >= 'K' && c <= 'O' || c == 'r' || c == 'R';
   }

   private static String resolve25(String string, float f, float g, FontObject fontObject3) {
      if (string == null) {
         return "";
      } else if (f <= 0.0F) {
         return "";
      } else if (RenderManager.resolve7(fontObject3, string, g).floatValue <= f) {
         return string;
      } else {
         String text43 = "...";
         if (RenderManager.resolve7(fontObject3, text43, g).floatValue > f) {
            return "";
         } else {
            int intValue64 = 1;
            int intValue65 = string.length();
            int intValue66 = 1;

            while (intValue64 <= intValue65) {
               int intValue67 = intValue64 + intValue65 >>> 1;
               if (RenderManager.resolve7(fontObject3, string.substring(0, intValue67) + text43, g).floatValue <= f) {
                  intValue66 = intValue67;
                  intValue64 = intValue67 + 1;
               } else {
                  intValue65 = intValue67 - 1;
               }
            }

            return string.substring(0, intValue66) + text43;
         }
      }
   }

   private static String resolve26(long l) {
      long longValue6 = Math.max(0L, l / 1000L);
      long longValue7 = longValue6 / 3600L;
      long longValue8 = longValue6 % 3600L / 60L;
      long longValue9 = longValue6 % 60L;
      if (longValue7 > 0L) {
         return longValue8 > 0L ? longValue7 + "h " + longValue8 + "m" : longValue7 + "h";
      } else if (longValue8 <= 0L) {
         return Math.max(1L, longValue9) + "s";
      } else {
         return longValue9 > 0L && longValue8 < 10L ? longValue8 + "m " + longValue9 + "s" : longValue8 + "m";
      }
   }

   private static float measure5(float f, float g) {
      return measure9(Math.min(f / 1920.0F, g / 1080.0F) * 1.16F, 0.72F, 1.38F);
   }

   static float measure6(float f, float g, float h, float i, float j, float k, float l) {
      float floatValue152 = h + j * 0.5F;
      float floatValue153 = i + k * 0.5F;
      float floatValue154 = j * 0.5F - l;
      float floatValue155 = k * 0.5F - l;
      float floatValue156 = Math.abs(f - floatValue152) - floatValue154;
      float floatValue157 = Math.abs(g - floatValue153) - floatValue155;
      float floatValue158 = Math.max(floatValue156, 0.0F);
      float floatValue159 = Math.max(floatValue157, 0.0F);
      return (float)Math.sqrt(floatValue158 * floatValue158 + floatValue159 * floatValue159) + Math.min(Math.max(floatValue156, floatValue157), 0.0F) - l;
   }

   private static float measure7(float f, float g) {
      return (float)Math.sqrt(f * f + g * g);
   }

   private static float measure8(float f) {
      float floatValue160 = measure9(f, 0.0F, 1.0F);
      return floatValue160 * floatValue160 * floatValue160 * (floatValue160 * (floatValue160 * 6.0F - 15.0F) + 10.0F);
   }

   private static float measure9(float f, float g, float h) {
      return Math.max(g, Math.min(h, f));
   }

   static int compute8(int i, int j, int k) {
      return Math.max(j, Math.min(k, i));
   }

   private static float measure10(int i) {
      return (i >> 16 & 0xFF) / 255.0F;
   }

   private static float measure11(int i) {
      return (i >> 8 & 0xFF) / 255.0F;
   }

   private static float measure12(int i) {
      return (i & 0xFF) / 255.0F;
   }

   private static int compute9(float f, float g, float h, float i) {
      int intValue68 = Math.round(measure9(f, 0.0F, 1.0F) * 255.0F);
      int intValue69 = Math.round(measure9(g, 0.0F, 1.0F) * 255.0F);
      int intValue70 = Math.round(measure9(h, 0.0F, 1.0F) * 255.0F);
      int intValue71 = Math.round(measure9(i, 0.0F, 1.0F) * 255.0F);
      return intValue71 << 24 | intValue68 << 16 | intValue69 << 8 | intValue70;
   }

   private static int compute10(int i, int j, float f, float g) {
      float floatValue161 = measure9(f, 0.0F, 1.0F);
      int intValue72 = ColorUtils.compute16(i, j, floatValue161);
      int intValue73 = Math.round(measure9(g, 0.0F, 1.0F) * 255.0F);
      return intValue73 << 24 | intValue72;
   }

   static final class AltVaultScreenUiState {
      final String text;
      final String text2;
      final AltVaultScreen.AltVaultScreenState altVaultScreenState;
      final boolean flag;
      final GameProfile gameProfile;
      final String text3;
      final SpringIntegrator springIntegrator = new SpringIntegrator(SpringSpec.resolve6());
      final long timestamp;
      long timestamp2;
      Identifier identifier;
      int intValue;
      boolean flag2;
      float floatValue;
      float floatValue2;
      float floatValue3;
      float floatValue4;
      float floatValue5;
      float floatValue6;
      float floatValue7;
      float floatValue8;
      float floatValue9;
      float floatValue10;
      float floatValue11;
      float floatValue12;
      float floatValue13 = 1.0F;
      float floatValue14 = 0.5F;
      float floatValue15 = 0.5F;
      float floatValue16;
      float floatValue17;
      float floatValue18;
      float floatValue19;
      boolean flag3;
      boolean flag4;
      boolean flag5;
      boolean flag6;
      boolean flag7 = true;

      AltVaultScreenUiState(String string, AltVaultScreen.AltVaultScreenState altVaultScreenState6, boolean bl, float f, String string2, long l, long m) {
         this.text = string2;
         this.text2 = string;
         this.altVaultScreenState = altVaultScreenState6;
         this.flag = bl;
         this.timestamp = l;
         this.timestamp2 = m;
         this.floatValue = f;
         this.gameProfile = new GameProfile(UUID.nameUUIDFromBytes(("OfflinePlayer:" + string).getBytes(StandardCharsets.UTF_8)), string);
         this.text3 = string.isBlank() ? "?" : string.substring(0, 1).toUpperCase(Locale.ROOT);
         this.springIntegrator.setFloatValue(1.0F);
      }

      boolean check(float f, float g) {
         return AltVaultScreen.measure6(f, g, this.floatValue2, this.floatValue3, this.floatValue6, this.floatValue7, this.floatValue8) <= 0.0F;
      }
   }

   static enum AltVaultScreenState {
      PREMIUM,
      CRACKED;

      static AltVaultScreen.AltVaultScreenState resolve(String string) {
         if (string == null) {
            return CRACKED;
         } else {
            try {
               return valueOf(string.toUpperCase(Locale.ROOT));
            } catch (IllegalArgumentException illegalArgumentException) {
               return CRACKED;
            }
         }
      }
   }

   static enum AltVaultScreenState2 {
      USE,
      ADD_CRACKED,
      RANDOM,
      EDIT,
      DELETE,
      BACK;
   }

   static final class AltVaultScreenUiState2 extends AltVaultScreen.AltVaultScreenState3 {
      final AltVaultScreen.AltVaultScreenState2 altVaultScreenState2;
      boolean flag = true;

      AltVaultScreenUiState2(String string, AltVaultScreen.AltVaultScreenState2 altVaultScreenState23) {
         super(string);
         this.altVaultScreenState2 = altVaultScreenState23;
      }
   }

   static final class AltVaultScreenResources implements AutoCloseable {
      private final NativeImageBackedTexture nativeImageBackedTexture;

      AltVaultScreenResources(NativeImageBackedTexture nativeImageBackedTexture) {
         this.nativeImageBackedTexture = nativeImageBackedTexture;
      }

      int compute() {
         return this.nativeImageBackedTexture.getGlTexture() instanceof GlTexture glTexture2 ? glTexture2.getGlId() : 0;
      }

      @Override
      public void close() {
         this.nativeImageBackedTexture.close();
      }
   }

   static final class AltVaultScreenVariant extends AltVaultScreen.AltVaultScreenState3 {
      final boolean flag;
      final String label;
      String text = "";
      int intValue;
      boolean flag2;
      boolean flag3;
      float floatValue;
      float floatValue2;
      float floatValue3;

      AltVaultScreenVariant(String string, boolean bl) {
         super(string);
         this.label = string;
         this.flag = bl;
      }

      void invoke(String string) {
         if (this.flag3) {
            this.text = "";
            this.intValue = 0;
            this.flag3 = false;
         }

         String text44 = AltVaultScreen.sanitizeAccountInput(string);
         if (!text44.isEmpty()) {
            int intValue74 = 80 - this.text.length();
            if (intValue74 > 0) {
               if (text44.length() > intValue74) {
                  text44 = text44.substring(0, intValue74);
               }

               this.text = this.text.substring(0, this.intValue) + text44 + this.text.substring(this.intValue);
               this.intValue = this.intValue + text44.length();
            }
         }
      }

      void invoke2(char c) {
         this.invoke(String.valueOf(c));
      }

      void invoke3() {
         if (this.flag3) {
            this.invoke4();
         } else if (this.intValue > 0 && !this.text.isEmpty()) {
            this.text = this.text.substring(0, this.intValue - 1) + this.text.substring(this.intValue);
            this.intValue--;
         }
      }

      void invoke4() {
         this.text = "";
         this.intValue = 0;
         this.floatValue2 = this.floatValue3 = 0.0F;
         this.flag3 = false;
      }

      void invoke5() {
         this.invoke();
         this.flag2 = false;
         this.flag3 = false;
         this.floatValue = this.floatValue3 = 0.0F;
         this.intValue = AltVaultScreen.compute8(this.intValue, 0, this.text.length());
      }
   }

   static class AltVaultScreenState3 {
      protected String text;
      protected final SpringIntegrator springIntegrator = new SpringIntegrator(SpringSpec.resolve6());
      protected float floatValue;
      protected float floatValue2;
      protected float floatValue3;
      protected float floatValue4;
      protected float floatValue5;
      protected float floatValue6;
      protected float floatValue7;
      protected float floatValue8;
      protected float floatValue9;
      protected float floatValue10;
      protected float floatValue11;
      protected float floatValue12 = 1.0F;
      protected float floatValue13 = 0.5F;
      protected float floatValue14 = 0.5F;
      protected float floatValue15;
      protected float floatValue16;
      protected float floatValue17;

      protected AltVaultScreenState3(String string) {
         this.text = string;
      }

      protected boolean check(float f, float g) {
         return AltVaultScreen.measure6(f, g, this.floatValue, this.floatValue2, this.floatValue5, this.floatValue6, this.floatValue7) <= 0.0F;
      }

      protected void invoke() {
         this.floatValue8 = this.floatValue9 = this.floatValue10 = this.floatValue11 = this.floatValue15 = this.floatValue17 = 0.0F;
         this.floatValue12 = 1.0F;
         this.floatValue13 = this.floatValue14 = 0.5F;
         this.springIntegrator.setFloatValue(1.0F);
      }
   }

   record AltVaultScreenBounds(AltVaultScreen.AltVaultScreenData server, float centerX, float topY, float alpha) {
   }

   static final class AltVaultScreenState4 {
      private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().setPrettyPrinting().create();
      private static final long TIMESTAMP = 10000L;
      private static final long TIMESTAMP_2 = 60000L;
      private static final Map<String, AltVaultFolder> VALUES_BY_KEY = new HashMap<>();
      private static final Map<String, byte[]> VALUES_BY_KEY_2 = new HashMap<>();
      private static boolean flag;
      private static boolean flag2;
      private static boolean flag3;
      private static String text = "";
      private static String text2 = "";
      private static long timestamp;
      private static long timestamp2;

      private AltVaultScreenState4() {
      }

      static synchronized void invoke(MinecraftClient minecraftClient) {
         invoke5();
         long longValue10 = System.currentTimeMillis();
         AltVaultServerSnapshot altVaultServerSnapshot = resolve2(minecraftClient);
         String text45 = resolve3(minecraftClient);
         if (altVaultServerSnapshot != null && !text45.isBlank()) {
            AltVaultScreen.AltVaultScreenState altVaultScreenState7 = resolve4(minecraftClient);
            String text46 = AltVaultScreen.resolve23(text45, altVaultScreenState7);
            if (text46.equals(text) && altVaultServerSnapshot.key().equals(text2)) {
               long longValue11 = Math.min(60000L, Math.max(0L, longValue10 - timestamp));
               timestamp = longValue10;
               if (longValue11 > 0L) {
                  invoke4(text46, text45, altVaultServerSnapshot, longValue11, longValue10);
               }

               invoke6(longValue10, false);
            } else {
               invoke3(longValue10);
               text = text46;
               text2 = altVaultServerSnapshot.key();
               timestamp = longValue10;
               invoke4(text46, text45, altVaultServerSnapshot, 0L, longValue10);
               invoke6(longValue10, false);
            }
         } else {
            invoke3(longValue10);
            invoke6(longValue10, true);
         }
      }

      static synchronized void invoke2() {
         invoke5();
         long longValue12 = System.currentTimeMillis();
         invoke3(longValue12);
         invoke6(longValue12, true);
      }

      static synchronized AltVaultScreen.AltVaultScreenData resolve(String string, String string2) {
         invoke5();
         AltVaultServerEntry altVaultServerEntry = null;
         ArrayList arrayList3 = new ArrayList(3);
         if (string != null && !string.isBlank()) {
            arrayList3.add(string);
         }

         String text47 = AltVaultScreen.resolve22(string2);
         if (!text47.isBlank()) {
            arrayList3.add(AltVaultScreen.resolve23(text47, AltVaultScreen.AltVaultScreenState.CRACKED));
            arrayList3.add(AltVaultScreen.resolve23(text47, AltVaultScreen.AltVaultScreenState.PREMIUM));
         }

         HashSet hashSet2 = new HashSet();

         for (String text48 : (ArrayList<String>)arrayList3) {
            if (text48 != null && !text48.isBlank() && hashSet2.add(text48)) {
               AltVaultFolder altVaultFolder = VALUES_BY_KEY.get(text48);
               if (altVaultFolder != null) {
                  for (AltVaultServerEntry altVaultServerEntry2 : altVaultFolder.valuesByKey.values()) {
                     if (altVaultServerEntry2.timestamp > 0L
                        && (
                           altVaultServerEntry == null
                              || altVaultServerEntry2.timestamp > altVaultServerEntry.timestamp
                              || altVaultServerEntry2.timestamp == altVaultServerEntry.timestamp && altVaultServerEntry2.timestamp2 > altVaultServerEntry.timestamp2
                        )) {
                        altVaultServerEntry = altVaultServerEntry2;
                     }
                  }
               }
            }
         }

         if (altVaultServerEntry == null) {
            return null;
         } else {
            byte[] byteValues2 = altVaultServerEntry.bytes == null ? null : Arrays.copyOf(altVaultServerEntry.bytes, altVaultServerEntry.bytes.length);
            if ((byteValues2 == null || byteValues2.length == 0) && !altVaultServerEntry.text3.isBlank()) {
               byteValues2 = resolve6(altVaultServerEntry.text3);
               if (byteValues2 != null && byteValues2.length > 0) {
                  altVaultServerEntry.bytes = Arrays.copyOf(byteValues2, byteValues2.length);
                  flag3 = true;
               }
            }

            return new AltVaultScreen.AltVaultScreenData(altVaultServerEntry.resolve(), altVaultServerEntry.text3, byteValues2, altVaultServerEntry.timestamp, altVaultServerEntry.timestamp2);
         }
      }

      private static void invoke3(long l) {
         if (!text.isBlank() && !text2.isBlank() && timestamp > 0L) {
            AltVaultFolder altVaultFolder2 = VALUES_BY_KEY.get(text);
            AltVaultServerEntry altVaultServerEntry3 = altVaultFolder2 == null ? null : altVaultFolder2.valuesByKey.get(text2);
            if (altVaultServerEntry3 != null) {
               long longValue13 = Math.min(60000L, Math.max(0L, l - timestamp));
               if (longValue13 > 0L) {
                  altVaultServerEntry3.timestamp += longValue13;
                  altVaultServerEntry3.timestamp2 = l;
                  flag3 = true;
               }
            }
         }

         text = "";
         text2 = "";
         timestamp = 0L;
      }

      private static void invoke4(String string, String string2, AltVaultServerSnapshot altVaultServerSnapshot2, long l, long m) {
         AltVaultFolder altVaultFolder3 = VALUES_BY_KEY.computeIfAbsent(string, string3 -> new AltVaultFolder(string, string2));
         altVaultFolder3.text2 = string2;
         AltVaultServerEntry altVaultServerEntry4 = altVaultFolder3.valuesByKey
            .computeIfAbsent(altVaultServerSnapshot2.key(), stringx -> new AltVaultServerEntry(altVaultServerSnapshot2.key(), altVaultServerSnapshot2.name(), altVaultServerSnapshot2.address()));
         altVaultServerEntry4.text2 = altVaultServerSnapshot2.name();
         altVaultServerEntry4.text3 = altVaultServerSnapshot2.address();
         if (altVaultServerSnapshot2.favicon() != null && altVaultServerSnapshot2.favicon().length > 0) {
            altVaultServerEntry4.bytes = Arrays.copyOf(altVaultServerSnapshot2.favicon(), altVaultServerSnapshot2.favicon().length);
         }

         altVaultServerEntry4.timestamp = altVaultServerEntry4.timestamp + Math.max(0L, l);
         altVaultServerEntry4.timestamp2 = m;
         flag3 = true;
      }

      private static AltVaultServerSnapshot resolve2(MinecraftClient minecraftClient) {
         if (minecraftClient != null && minecraftClient.player != null && minecraftClient.world != null && minecraftClient.getNetworkHandler() != null) {
            try {
               if (minecraftClient.isConnectedToLocalServer()) {
                  return new AltVaultServerSnapshot("local:localhost", "Local Server", "localhost", null);
               }
            } catch (Throwable exception8) {
            }

            ServerInfo serverInfo = null;

            try {
               serverInfo = minecraftClient.getCurrentServerEntry();
            } catch (Throwable exception9) {
            }

            if (serverInfo == null) {
               try {
                  serverInfo = minecraftClient.getNetworkHandler().getServerInfo();
               } catch (Throwable exception10) {
               }
            }

            if (serverInfo != null) {
               String text49 = resolve8(serverInfo.address);
               if (!text49.isBlank()) {
                  String text50 = resolve12(serverInfo.name).trim();
                  if (text50.isBlank()) {
                     text50 = resolve10(text49);
                  }

                  byte[] byteValues3 = serverInfo.getFavicon();
                  return new AltVaultServerSnapshot("server:" + resolve7(text49), text50, text49, byteValues3 == null ? null : Arrays.copyOf(byteValues3, byteValues3.length));
               }
            }

            try {
               SocketAddress socketAddress = minecraftClient.getNetworkHandler().getConnection().getAddress();
               String text51 = socketAddress == null ? "" : resolve8(socketAddress.toString());
               if (!text51.isBlank()) {
                  return new AltVaultServerSnapshot("server:" + resolve7(text51), resolve10(text51), text51, null);
               }
            } catch (Throwable exception11) {
            }

            return null;
         } else {
            return null;
         }
      }

      private static String resolve3(MinecraftClient minecraftClient) {
         try {
            return minecraftClient != null && minecraftClient.getSession() != null
               ? AltVaultScreen.resolve22(minecraftClient.getSession().getUsername())
               : "";
         } catch (Throwable exception12) {
            return "";
         }
      }

      private static AltVaultScreen.AltVaultScreenState resolve4(MinecraftClient minecraftClient) {
         try {
            if (minecraftClient != null && minecraftClient.getSession() != null && minecraftClient.getSession().getAccountType() != AccountType.LEGACY) {
               return AltVaultScreen.AltVaultScreenState.PREMIUM;
            }
         } catch (Throwable exception13) {
         }

         return AltVaultScreen.AltVaultScreenState.CRACKED;
      }

      private static void invoke5() {
         if (!flag) {
            flag = true;
            File file4 = resolve5();
            if (file4 != null && file4.exists() && file4.isFile()) {
               try {
                  JsonElement jsonElement = JsonParser.parseString(Files.readString(file4.toPath(), StandardCharsets.UTF_8));
                  if (jsonElement == null || !jsonElement.isJsonObject()) {
                     return;
                  }

                  JsonObject jsonObject2 = jsonElement.getAsJsonObject();
                  JsonElement jsonElement2 = jsonObject2.get("accounts");
                  if (jsonElement2 == null || !jsonElement2.isJsonArray()) {
                     return;
                  }

                  for (JsonElement jsonElement3 : jsonElement2.getAsJsonArray()) {
                     if (jsonElement3.isJsonObject()) {
                        JsonObject jsonObject3 = jsonElement3.getAsJsonObject();
                        String text52 = resolve11(jsonObject3, "id", "");
                        String text53 = AltVaultScreen.resolve22(resolve11(jsonObject3, "name", ""));
                        if (!text52.isBlank()) {
                           AltVaultFolder altVaultFolder4 = new AltVaultFolder(text52, text53);
                           JsonElement jsonElement4 = jsonObject3.get("servers");
                           if (jsonElement4 != null && jsonElement4.isJsonArray()) {
                              for (JsonElement jsonElement5 : jsonElement4.getAsJsonArray()) {
                                 if (jsonElement5.isJsonObject()) {
                                    JsonObject jsonObject4 = jsonElement5.getAsJsonObject();
                                    String text54 = resolve7(resolve11(jsonObject4, "key", ""));
                                    String text55 = resolve8(resolve11(jsonObject4, "address", ""));
                                    String text56 = resolve12(resolve11(jsonObject4, "name", "")).trim();
                                    byte[] byteValues4 = resolve9(resolve11(jsonObject4, "favicon", ""));
                                    long longValue14 = Math.max(0L, compute(jsonObject4, "totalMs", 0L));
                                    long longValue15 = Math.max(0L, compute(jsonObject4, "lastActiveAt", 0L));
                                    if (!text54.isBlank() && longValue14 > 0L) {
                                       AltVaultServerEntry altVaultServerEntry5 = new AltVaultServerEntry(text54, text56, text55);
                                       altVaultServerEntry5.bytes = byteValues4;
                                       altVaultServerEntry5.timestamp = longValue14;
                                       altVaultServerEntry5.timestamp2 = longValue15;
                                       altVaultFolder4.valuesByKey.put(text54, altVaultServerEntry5);
                                    }
                                 }
                              }
                           }

                           if (!altVaultFolder4.valuesByKey.isEmpty()) {
                              VALUES_BY_KEY.put(text52, altVaultFolder4);
                           }
                        }
                     }
                  }
               } catch (Throwable exception14) {
               }
            }
         }
      }

      private static void invoke6(long l, boolean bl) {
         if (flag3 && (bl || l - timestamp2 >= 10000L)) {
            File file5 = resolve5();
            if (file5 != null) {
               try {
                  File file6 = file5.getParentFile();
                  if (file6 != null) {
                     file6.mkdirs();
                  }

                  JsonObject jsonObject5 = new JsonObject();
                  jsonObject5.addProperty("version", 1);
                  jsonObject5.addProperty("updatedAt", l);
                  JsonArray jsonArray = new JsonArray();

                  for (AltVaultFolder altVaultFolder5 : VALUES_BY_KEY.values()) {
                     if (!altVaultFolder5.text.isBlank() && !altVaultFolder5.valuesByKey.isEmpty()) {
                        JsonObject jsonObject6 = new JsonObject();
                        jsonObject6.addProperty("id", altVaultFolder5.text);
                        jsonObject6.addProperty("name", altVaultFolder5.text2);
                        JsonArray jsonArray2 = new JsonArray();

                        for (AltVaultServerEntry altVaultServerEntry6 : altVaultFolder5.valuesByKey.values()) {
                           if (altVaultServerEntry6.timestamp > 0L) {
                              JsonObject jsonObject7 = new JsonObject();
                              jsonObject7.addProperty("key", altVaultServerEntry6.text);
                              jsonObject7.addProperty("name", altVaultServerEntry6.text2);
                              jsonObject7.addProperty("address", altVaultServerEntry6.text3);
                              if (altVaultServerEntry6.bytes != null && altVaultServerEntry6.bytes.length > 0) {
                                 jsonObject7.addProperty("favicon", Base64.getEncoder().encodeToString(altVaultServerEntry6.bytes));
                              }

                              jsonObject7.addProperty("totalMs", altVaultServerEntry6.timestamp);
                              jsonObject7.addProperty("lastActiveAt", altVaultServerEntry6.timestamp2);
                              jsonArray2.add(jsonObject7);
                           }
                        }

                        if (jsonArray2.size() > 0) {
                           jsonObject6.add("servers", jsonArray2);
                           jsonArray.add(jsonObject6);
                        }
                     }
                  }

                  jsonObject5.add("accounts", jsonArray);
                  Files.writeString(file5.toPath(), GSON.toJson(jsonObject5), StandardCharsets.UTF_8);
                  flag3 = false;
                  timestamp2 = l;
               } catch (Throwable exception15) {
               }
            }
         }
      }

      private static File resolve5() {
         try {
            File file7 = WildClient.INSTANCE != null ? WildClient.INSTANCE.file : new File(MinecraftClient.getInstance().runDirectory, "Wild");
            return new File(file7, "account_server_stats.json");
         } catch (Throwable exception16) {
            return null;
         }
      }

      private static byte[] resolve6(String string) {
         invoke7();
         byte[] byteValues5 = VALUES_BY_KEY_2.get(resolve7(string));
         return byteValues5 == null ? null : Arrays.copyOf(byteValues5, byteValues5.length);
      }

      private static void invoke7() {
         if (!flag2) {
            flag2 = true;

            try {
               MinecraftClient client9 = MinecraftClient.getInstance();
               ServerList serverList = new ServerList(client9);
               serverList.loadFile();
               int intValue75 = serverList.size();

               for (int intValue76 = 0; intValue76 < intValue75; intValue76++) {
                  ServerInfo serverInfo2 = serverList.get(intValue76);
                  if (serverInfo2 != null && serverInfo2.address != null && !serverInfo2.address.isBlank()) {
                     byte[] byteValues6 = serverInfo2.getFavicon();
                     if (byteValues6 != null && byteValues6.length != 0) {
                        VALUES_BY_KEY_2.put(resolve7(serverInfo2.address), Arrays.copyOf(byteValues6, byteValues6.length));
                     }
                  }
               }
            } catch (Throwable exception17) {
            }
         }
      }

      private static String resolve7(String string) {
         return resolve8(string).toLowerCase(Locale.ROOT);
      }

      private static String resolve8(String string) {
         String text57 = resolve12(string).trim();
         if (text57.startsWith("/")) {
            text57 = text57.substring(1);
         }

         int intValue77 = text57.indexOf("<unresolved>");
         if (intValue77 >= 0) {
            text57 = text57.substring(0, intValue77) + text57.substring(intValue77 + "<unresolved>".length());
         }

         return text57.trim();
      }

      private static byte[] resolve9(String string) {
         String text58 = resolve12(string).trim();
         if (text58.isBlank()) {
            return null;
         } else {
            try {
               return Base64.getDecoder().decode(text58);
            } catch (Throwable exception18) {
               return null;
            }
         }
      }

      static String resolve10(String string) {
         String text59 = resolve8(string);
         int intValue78 = text59.indexOf(47);
         if (intValue78 >= 0 && intValue78 + 1 < text59.length()) {
            text59 = text59.substring(intValue78 + 1);
         }

         return text59.isBlank() ? "Server" : text59;
      }

      private static String resolve11(JsonObject jsonObject, String string, String string2) {
         try {
            JsonElement jsonElement6 = jsonObject.get(string);
            return jsonElement6 != null && !jsonElement6.isJsonNull() ? jsonElement6.getAsString() : string2;
         } catch (Throwable exception19) {
            return string2;
         }
      }

      private static long compute(JsonObject jsonObject, String string, long l) {
         try {
            JsonElement jsonElement7 = jsonObject.get(string);
            return jsonElement7 != null && !jsonElement7.isJsonNull() ? jsonElement7.getAsLong() : l;
         } catch (Throwable exception20) {
            return l;
         }
      }

      static String resolve12(String string) {
         return string == null ? "" : string;
      }
   }

   record AltVaultScreenData(String displayName, String address, byte[] favicon, long totalMs, long lastActiveAt) {
   }

   static final class AltVaultScreenState5 {
      float floatValue;
      float floatValue2;
      float floatValue3 = -100.0F;
      float floatValue4;
   }
}
