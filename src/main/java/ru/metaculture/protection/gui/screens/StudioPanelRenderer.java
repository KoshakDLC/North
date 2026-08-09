package ru.metaculture.protection;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;

public final class StudioPanelRenderer {
   private Metrics metrics;
   private float floatValue;
   private float floatValue2;
   private float floatValue3;
   private float floatValue4;
   private StudioAssetCategory studioAssetCategory = StudioAssetCategory.MODELS;
   private float floatValue5;
   private float floatValue6;
   private float floatValue7 = 200.0F;
   private float floatValue8 = -8.0F;
   private float floatValue9 = 1.0F;
   private boolean flag;
   private float floatValue10;
   private float floatValue11;
   private String text = "";
   private boolean flag2;
   private boolean flag3;
   private final ArrayList<Long> arrayList = new ArrayList<>();
   private final ArrayList<StudioPanelRenderer.StudioPanelRendererTimedEntry> arrayList2 = new ArrayList<>();
   private static final float FLOAT_VALUE = 170.0F;
   private boolean flag4;
   private String text2 = "";
   private boolean flag5;
   private String text3 = "";
   private boolean flag6;
   private long timestamp;
   private String text4 = "";
   private float floatValue12;
   private float floatValue13 = 1.0F;
   private int intValue = 1;
   private float floatValue14;
   private float floatValue15;
   private boolean flag7;
   private long timestamp2;
   private long timestamp3;
   private final HashMap<String, Long> hashMap = new HashMap<>();
   private static final String[] CHIP0 = new String[]{"chip0", "chip1", "chip2", "chip3"};
   private String text5 = "";
   private long timestamp4;

   public boolean check(ClickGuiState clickGuiState) {
      return clickGuiState != null && clickGuiState.isFlag3();
   }

   public boolean check2(ClickGuiState clickGuiState2) {
      return clickGuiState2 != null && clickGuiState2.isFlag3();
   }

   public boolean check3() {
      return this.flag2 || this.flag4 || this.flag5;
   }

   public void invoke() {
      this.flag = false;
      this.flag2 = false;
      this.flag3 = false;
      this.flag4 = false;
      this.flag5 = false;
      this.flag6 = false;
   }

   public void invoke2(RenderManager renderManager, ClickGuiState clickGuiState3, ThemeContext themeContext, float f, float g, float h, float i) {
      if (renderManager != null && clickGuiState3 != null && themeContext != null && !(h <= 0.0F) && !(i <= 0.0F)) {
         Metrics metrics = themeContext.getMetrics();
         ColorScheme colorScheme = themeContext.getColorScheme();
         this.metrics = metrics;
         this.floatValue = f;
         this.floatValue2 = g;
         this.floatValue3 = h;
         this.floatValue4 = i;
         this.floatValue5 = this.floatValue5 + (this.floatValue6 - this.floatValue5) * 0.32F;
         this.floatValue12 = this.floatValue12 + ((this.text4.isEmpty() ? 0.0F : 1.0F) - this.floatValue12) * 0.3F;
         this.floatValue13 = this.floatValue13 + (1.0F - this.floatValue13) * 0.18F;
         if (this.floatValue13 > 0.999F) {
            this.floatValue13 = 1.0F;
         }

         long longValue = System.currentTimeMillis();
         if (longValue - this.timestamp3 > 240L) {
            this.timestamp2 = longValue;
         }

         this.timestamp3 = longValue;
         float floatValue = Math.min(1.0F, (float)(longValue - this.timestamp2) / 360.0F);
         float floatValue2 = 1.0F - (1.0F - floatValue) * (1.0F - floatValue) * (1.0F - floatValue);
         StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData = new StudioPanelRenderer.StudioPanelRendererData(f, g, h, i);
         boolean flag = floatValue2 < 0.999F;
         if (flag) {
            renderManager.invoke65(Math.max(0.0F, floatValue2));
         }

         try {
            this.invoke3(renderManager, clickGuiState3, metrics, colorScheme, studioPanelRendererData, 1.0F);
            this.invoke4(renderManager, clickGuiState3, metrics, colorScheme, studioPanelRendererData, 1.0F);
            this.invoke5(renderManager, clickGuiState3, themeContext, metrics, colorScheme, studioPanelRendererData, 1.0F);
            this.invoke9(renderManager, clickGuiState3, themeContext, metrics, colorScheme, studioPanelRendererData, 1.0F);
         } finally {
            if (flag) {
               renderManager.invoke66();
            }
         }
      }
   }

   private void invoke3(
      RenderManager renderManager2, ClickGuiState clickGuiState4, Metrics metrics2, ColorScheme colorScheme2, StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData2, float f
   ) {
      float floatValue3 = metrics2.measure(18.0F);
      float floatValue4 = metrics2.measure(44.0F);
      float floatValue5 = metrics2.measure(18.0F);
      ClickGuiRenderUtils.invoke4(
         renderManager2,
         metrics2,
         FontRegistry.fontObject8,
         studioPanelRendererData2.x + floatValue3,
         studioPanelRendererData2.y,
         floatValue4,
         13.0F,
         "a",
         ColorScheme.compute6(colorScheme2.getIntValue14(), Math.round(255.0F * f))
      );
      ClickGuiRenderUtils.invoke4(
         renderManager2,
         metrics2,
         FontRegistry.fontObject4,
         studioPanelRendererData2.x + floatValue3 + floatValue5,
         studioPanelRendererData2.y,
         floatValue4,
         15.0F,
         "Studio",
         ColorScheme.compute6(ClickGuiRenderUtils.compute2(colorScheme2), Math.round(255.0F * f))
      );
      StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData3 = this.resolve4(metrics2, studioPanelRendererData2);
      boolean flag2 = ClickGuiRenderUtils.check(clickGuiState4, studioPanelRendererData3.x, studioPanelRendererData3.y, studioPanelRendererData3.w, studioPanelRendererData3.h);
      renderManager2.invoke5(
         studioPanelRendererData3.x,
         studioPanelRendererData3.y,
         studioPanelRendererData3.w,
         studioPanelRendererData3.h,
         studioPanelRendererData3.h * 0.5F,
         ColorScheme.compute6(this.flag2 ? colorScheme2.getIntValue6() : colorScheme2.getIntValue4(), Math.round(255.0F * f))
      );
      if (this.flag2 || flag2) {
         renderManager2.invoke28(
            studioPanelRendererData3.x,
            studioPanelRendererData3.y,
            studioPanelRendererData3.w,
            studioPanelRendererData3.h,
            studioPanelRendererData3.h * 0.5F,
            ColorScheme.compute6(colorScheme2.getIntValue14(), Math.round((this.flag2 ? 150 : 80) * f)),
            0.7F
         );
      }

      ClickGuiRenderUtils.invoke4(
         renderManager2,
         metrics2,
         FontRegistry.fontObject5,
         studioPanelRendererData3.x + metrics2.measure(10.0F),
         studioPanelRendererData3.y,
         studioPanelRendererData3.h,
         10.0F,
         "m",
         ColorScheme.compute6(ClickGuiRenderUtils.compute4(colorScheme2), Math.round(200.0F * f))
      );
      float floatValue6 = this.flag2 ? (float)((Math.sin(System.currentTimeMillis() * 0.006) + 1.0) * 0.5) : 0.0F;
      long longValue2 = System.currentTimeMillis();
      renderManager2.invoke23((int)(studioPanelRendererData3.x + metrics2.measure(26.0F)), (int)studioPanelRendererData3.y, (int)(studioPanelRendererData3.w - metrics2.measure(34.0F)), (int)studioPanelRendererData3.h);
      if (this.text.isEmpty() && !this.flag2) {
         ClickGuiRenderUtils.invoke4(
            renderManager2,
            metrics2,
            FontRegistry.fontObject,
            studioPanelRendererData3.x + metrics2.measure(26.0F),
            studioPanelRendererData3.y,
            studioPanelRendererData3.h,
            10.0F,
            "Поиск...",
            ColorScheme.compute6(ClickGuiRenderUtils.compute4(colorScheme2), Math.round(255.0F * f))
         );
      } else {
         if (this.flag3 && !this.text.isEmpty()) {
            float floatValue7 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, this.text, 10.0F);
            renderManager2.invoke5(
               studioPanelRendererData3.x + metrics2.measure(24.0F),
               studioPanelRendererData3.y + (studioPanelRendererData3.h - metrics2.measure(16.0F)) * 0.5F,
               floatValue7 + metrics2.measure(5.0F),
               metrics2.measure(16.0F),
               metrics2.measure(3.0F),
               ColorScheme.compute6(colorScheme2.getIntValue14(), Math.round(70.0F * f))
            );
         }

         float floatValue8 = studioPanelRendererData3.x + metrics2.measure(26.0F);

         for (int intValue = 0; intValue < this.text.length(); intValue++) {
            String text = String.valueOf(this.text.charAt(intValue));
            float floatValue9 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, text, 10.0F);
            long longValue3 = intValue < this.arrayList.size() ? this.arrayList.get(intValue) : 0L;
            float floatValue10 = (float)(longValue2 - longValue3) / 170.0F;
            float floatValue11 = 0.0F;
            float floatValue12 = 1.0F;
            if (floatValue10 < 1.0F) {
               float floatValue13 = 1.0F - (1.0F - floatValue10) * (1.0F - floatValue10);
               floatValue11 = (1.0F - floatValue13) * metrics2.measure(6.0F);
               floatValue12 = floatValue13;
            }

            ClickGuiRenderUtils.invoke4(
               renderManager2,
               metrics2,
               FontRegistry.fontObject,
               floatValue8,
               studioPanelRendererData3.y + floatValue11,
               studioPanelRendererData3.h,
               10.0F,
               text,
               ColorScheme.compute6(ClickGuiRenderUtils.compute2(colorScheme2), Math.round(255.0F * floatValue12 * f))
            );
            floatValue8 += floatValue9;
         }

         if (this.flag2 && !this.flag3) {
            ClickGuiRenderUtils.invoke4(
               renderManager2,
               metrics2,
               FontRegistry.fontObject,
               floatValue8,
               studioPanelRendererData3.y,
               studioPanelRendererData3.h,
               10.0F,
               "|",
               ColorScheme.compute6(colorScheme2.getIntValue14(), Math.round(255.0F * floatValue6 * f))
            );
         }
      }

      for (int intValue2 = this.arrayList2.size() - 1; intValue2 >= 0; intValue2--) {
         StudioPanelRenderer.StudioPanelRendererTimedEntry studioPanelRendererTimedEntry = this.arrayList2.get(intValue2);
         float floatValue14 = (float)(longValue2 - studioPanelRendererTimedEntry.born()) / 170.0F;
         if (floatValue14 >= 1.0F) {
            this.arrayList2.remove(intValue2);
         } else {
            float floatValue15 = 1.0F - (1.0F - floatValue14) * (1.0F - floatValue14);
            ClickGuiRenderUtils.invoke4(
               renderManager2,
               metrics2,
               FontRegistry.fontObject,
               studioPanelRendererTimedEntry.x(),
               studioPanelRendererData3.y + floatValue15 * metrics2.measure(7.0F),
               studioPanelRendererData3.h,
               10.0F,
               studioPanelRendererTimedEntry.ch(),
               ColorScheme.compute6(ClickGuiRenderUtils.compute2(colorScheme2), Math.round(255.0F * (1.0F - floatValue15) * f))
            );
         }
      }

      renderManager2.invoke25();
      if (!this.text.isEmpty()) {
         boolean flag3 = ClickGuiRenderUtils.check(
            clickGuiState4, studioPanelRendererData3.x + studioPanelRendererData3.w - metrics2.measure(28.0F), studioPanelRendererData3.y, metrics2.measure(28.0F), studioPanelRendererData3.h
         );
         ClickGuiRenderUtils.invoke4(
            renderManager2,
            metrics2,
            FontRegistry.fontObject5,
            studioPanelRendererData3.x + studioPanelRendererData3.w - metrics2.measure(20.0F),
            studioPanelRendererData3.y,
            studioPanelRendererData3.h,
            9.0F,
            "l",
            ColorScheme.compute6(flag3 ? colorScheme2.getIntValue14() : ClickGuiRenderUtils.compute4(colorScheme2), Math.round(220.0F * f))
         );
      }
   }

   private void invoke4(
      RenderManager renderManager3, ClickGuiState clickGuiState5, Metrics metrics3, ColorScheme colorScheme3, StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData4, float f
   ) {
      StudioAssetCategory[] studioAssetCategories = StudioAssetCategory.values();
      float floatValue16 = studioPanelRendererData4.y + metrics3.measure(44.0F);
      float floatValue17 = metrics3.measure(34.0F);
      float floatValue18 = metrics3.measure(18.0F);
      float floatValue19 = metrics3.measure(26.0F);
      float floatValue20 = floatValue16 + (floatValue17 - floatValue19) * 0.5F;
      float floatValue21 = studioPanelRendererData4.x + floatValue18;
      float floatValue22 = floatValue21;
      float floatValue23 = metrics3.measure(40.0F);

      for (StudioAssetCategory studioAssetCategory : studioAssetCategories) {
         float floatValue24 = ClickGuiRenderUtils.measure(FontRegistry.fontObject4, studioAssetCategory.getText2(), 11.0F) + metrics3.measure(20.0F);
         if (studioAssetCategory == this.studioAssetCategory) {
            floatValue22 = floatValue21;
            floatValue23 = floatValue24;
         }

         floatValue21 += floatValue24 + metrics3.measure(6.0F);
      }

      if (!this.flag7) {
         this.floatValue14 = floatValue22;
         this.floatValue15 = floatValue23;
         this.flag7 = true;
      } else {
         this.floatValue14 = this.floatValue14 + (floatValue22 - this.floatValue14) * 0.3F;
         this.floatValue15 = this.floatValue15 + (floatValue23 - this.floatValue15) * 0.3F;
      }

      renderManager3.invoke5(
         this.floatValue14,
         floatValue20,
         this.floatValue15,
         floatValue19,
         floatValue19 * 0.5F,
         ColorScheme.compute6(colorScheme3.getIntValue14(), Math.round((colorScheme3.isFlag() ? 60 : 86) * f))
      );
      floatValue21 = studioPanelRendererData4.x + floatValue18;

      for (StudioAssetCategory studioAssetCategory2 : studioAssetCategories) {
         float floatValue25 = ClickGuiRenderUtils.measure(FontRegistry.fontObject4, studioAssetCategory2.getText2(), 11.0F) + metrics3.measure(20.0F);
         boolean flag4 = studioAssetCategory2 == this.studioAssetCategory;
         boolean flag5 = ClickGuiRenderUtils.check(clickGuiState5, floatValue21, floatValue20, floatValue25, floatValue19);
         boolean flag6 = this.check10(renderManager3, studioAssetCategory2.name(), floatValue21 + floatValue25 * 0.5F, floatValue20 + floatValue19 * 0.5F);

         try {
            if (!flag4 && flag5) {
               renderManager3.invoke5(floatValue21, floatValue20, floatValue25, floatValue19, floatValue19 * 0.5F, ColorScheme.compute6(colorScheme3.getIntValue6(), Math.round(255.0F * f)));
            }

            ClickGuiRenderUtils.invoke4(
               renderManager3,
               metrics3,
               FontRegistry.fontObject4,
               floatValue21 + metrics3.measure(11.0F),
               floatValue20,
               floatValue19,
               11.0F,
               studioAssetCategory2.getText2(),
               ColorScheme.compute6(flag4 ? ClickGuiRenderUtils.compute2(colorScheme3) : ClickGuiRenderUtils.compute4(colorScheme3), Math.round(255.0F * f))
            );
         } finally {
            this.invoke21(renderManager3, flag6);
         }

         floatValue21 += floatValue25 + metrics3.measure(6.0F);
      }

      StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData5 = this.resolve5(metrics3, studioPanelRendererData4);
      StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData6 = this.resolve6(metrics3, studioPanelRendererData4);
      boolean flag7 = ClickGuiRenderUtils.check(clickGuiState5, studioPanelRendererData6.x, studioPanelRendererData6.y, studioPanelRendererData6.w, studioPanelRendererData6.h);
      boolean flag8 = this.check10(renderManager3, "import", studioPanelRendererData6.x + studioPanelRendererData6.w * 0.5F, studioPanelRendererData6.y + studioPanelRendererData6.h * 0.5F);

      try {
         renderManager3.invoke5(
            studioPanelRendererData6.x,
            studioPanelRendererData6.y,
            studioPanelRendererData6.w,
            studioPanelRendererData6.h,
            studioPanelRendererData6.h * 0.5F,
            ColorScheme.compute6(flag7 ? colorScheme3.getIntValue14() : colorScheme3.getIntValue6(), Math.round((flag7 ? 70 : 255) * f))
         );
         renderManager3.invoke28(
            studioPanelRendererData6.x, studioPanelRendererData6.y, studioPanelRendererData6.w, studioPanelRendererData6.h, studioPanelRendererData6.h * 0.5F, ColorScheme.compute6(colorScheme3.getIntValue14(), Math.round(110.0F * f)), 0.7F
         );
         String text2 = "Импорт";
         float floatValue26 = ClickGuiRenderUtils.measure(FontRegistry.fontObject4, text2, 10.0F);
         ClickGuiRenderUtils.invoke4(
            renderManager3,
            metrics3,
            FontRegistry.fontObject4,
            studioPanelRendererData6.x + (studioPanelRendererData6.w - floatValue26) * 0.5F,
            studioPanelRendererData6.y,
            studioPanelRendererData6.h,
            10.0F,
            text2,
            ColorScheme.compute6(ClickGuiRenderUtils.compute2(colorScheme3), Math.round(255.0F * f))
         );
      } finally {
         this.invoke21(renderManager3, flag8);
      }

      boolean flag9 = ClickGuiRenderUtils.check(clickGuiState5, studioPanelRendererData5.x, studioPanelRendererData5.y, studioPanelRendererData5.w, studioPanelRendererData5.h);
      boolean flag10 = this.check10(renderManager3, "reload", studioPanelRendererData5.x + studioPanelRendererData5.w * 0.5F, studioPanelRendererData5.y + studioPanelRendererData5.h * 0.5F);

      try {
         renderManager3.invoke5(
            studioPanelRendererData5.x,
            studioPanelRendererData5.y,
            studioPanelRendererData5.w,
            studioPanelRendererData5.h,
            studioPanelRendererData5.h * 0.5F,
            ColorScheme.compute6(flag9 ? colorScheme3.getIntValue6() : colorScheme3.getIntValue4(), Math.round(255.0F * f))
         );
         float floatValue27 = ClickGuiRenderUtils.measure(FontRegistry.fontObject5, "r", 10.0F);
         ClickGuiRenderUtils.invoke4(
            renderManager3,
            metrics3,
            FontRegistry.fontObject5,
            studioPanelRendererData5.x + (studioPanelRendererData5.w - floatValue27) * 0.5F,
            studioPanelRendererData5.y,
            studioPanelRendererData5.h,
            10.0F,
            "r",
            ColorScheme.compute6(flag9 ? colorScheme3.getIntValue14() : ClickGuiRenderUtils.compute4(colorScheme3), Math.round(255.0F * f))
         );
      } finally {
         this.invoke21(renderManager3, flag10);
      }
   }

   private void invoke5(
      RenderManager renderManager4,
      ClickGuiState clickGuiState6,
      ThemeContext themeContext2,
      Metrics metrics4,
      ColorScheme colorScheme4,
      StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData7,
      float f
   ) {
      StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData8 = this.resolve9(metrics4, studioPanelRendererData7);
      this.invoke7(renderManager4, metrics4, colorScheme4, studioPanelRendererData8.x, studioPanelRendererData8.y, studioPanelRendererData8.w, studioPanelRendererData8.h, metrics4.measure(10.0F), f);
      List items = this.resolve();
      int intValue3 = this.compute(metrics4, studioPanelRendererData8);
      float floatValue28 = metrics4.measure(10.0F);
      float floatValue29 = (studioPanelRendererData8.w - metrics4.measure(12.0F) - (intValue3 - 1) * floatValue28) / intValue3;
      float floatValue30 = floatValue29;
      float floatValue31 = floatValue29 + metrics4.measure(20.0F);
      float floatValue32 = metrics4.measure(10.0F);
      int intValue4 = (items.size() + intValue3 - 1) / intValue3;
      float floatValue33 = intValue4 * (floatValue31 + floatValue32) + metrics4.measure(6.0F);
      float floatValue34 = Math.max(0.0F, floatValue33 - studioPanelRendererData8.h);
      this.floatValue6 = measure2(this.floatValue6, 0.0F, floatValue34);
      this.floatValue5 = measure2(this.floatValue5, 0.0F, floatValue34);
      StudioAsset studioAsset = StudioLibrary.resolve().resolve4();
      renderManager4.invoke24(
         studioPanelRendererData8.x, studioPanelRendererData8.y, studioPanelRendererData8.w, studioPanelRendererData8.h, metrics4.measure(10.0F), metrics4.measure(10.0F), metrics4.measure(10.0F), metrics4.measure(10.0F)
      );
      boolean flag11 = false ;

      try {
         flag11 = true;
         boolean flag12 = this.floatValue13 < 0.999F;
         if (flag12) {
            renderManager4.invoke65(Math.max(0.0F, this.floatValue13));
            renderManager4.invoke56((1.0F - this.floatValue13) * this.intValue * studioPanelRendererData8.w * 0.16F, 0.0F);
         }

         try {
            float floatValue35 = studioPanelRendererData8.x + metrics4.measure(6.0F);
            float floatValue36 = studioPanelRendererData8.y + metrics4.measure(6.0F) - this.floatValue5;
            String text3 = "";

            for (int intValue5 = 0; intValue5 < items.size(); intValue5++) {
               int intValue6 = intValue5 % intValue3;
               int intValue7 = intValue5 / intValue3;
               float floatValue37 = floatValue35 + intValue6 * (floatValue29 + floatValue28);
               float floatValue38 = floatValue36 + intValue7 * (floatValue31 + floatValue32);
               if (!(floatValue38 + floatValue31 < studioPanelRendererData8.y) && !(floatValue38 > studioPanelRendererData8.y + studioPanelRendererData8.h)) {
                  StudioAsset studioAsset2 = (StudioAsset)items.get(intValue5);
                  if (ClickGuiRenderUtils.check(clickGuiState6, floatValue37, floatValue38, floatValue29, floatValue31)) {
                     text3 = studioAsset2.getText();
                  }

                  float floatValue39 = studioAsset2.getText().equals(this.text4) ? this.floatValue12 : 0.0F;
                  float floatValue40 = 1.0F;
                  float floatValue41 = floatValue38;
                  long longValue4 = System.currentTimeMillis() - this.timestamp2 - intValue5 * 26L;
                  if (longValue4 < 240L) {
                     float floatValue42 = Math.max(0.0F, (float)longValue4) / 240.0F;
                     float floatValue43 = 1.0F - (1.0F - floatValue42) * (1.0F - floatValue42);
                     floatValue40 = floatValue43;
                     floatValue41 = floatValue38 + (1.0F - floatValue43) * metrics4.measure(14.0F);
                  }

                  boolean flag13 = this.check10(renderManager4, studioAsset2.getText(), floatValue37 + floatValue29 * 0.5F, floatValue41 + floatValue31 * 0.5F);
                  boolean flag14 = false ;

                  try {
                     flag14 = true;
                     this.invoke6(renderManager4, clickGuiState6, metrics4, colorScheme4, studioAsset2, floatValue37, floatValue41, floatValue29, floatValue31, floatValue30, studioAsset, f * floatValue40, floatValue39);
                     flag14 = false;
                  } finally {
                     if (flag14) {
                        this.invoke21(renderManager4, flag13);
                     }
                  }

                  this.invoke21(renderManager4, flag13);
               }
            }

            this.text4 = text3;
            if (items.isEmpty()) {
               String text4 = this.text.isEmpty() ? "Пусто. Нажмите «Импорт»" : "Ничего не найдено";
               float floatValue44 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, text4, 10.0F);
               ClickGuiRenderUtils.invoke4(
                  renderManager4,
                  metrics4,
                  FontRegistry.fontObject,
                  studioPanelRendererData8.x + (studioPanelRendererData8.w - floatValue44) * 0.5F,
                  studioPanelRendererData8.y + studioPanelRendererData8.h * 0.42F,
                  metrics4.measure(14.0F),
                  10.0F,
                  text4,
                  ColorScheme.compute6(ClickGuiRenderUtils.compute4(colorScheme4), Math.round(190.0F * f))
               );
            }
         } finally {
            if (flag12) {
               renderManager4.invoke57();
               renderManager4.invoke66();
            }
         }

         flag11 = false;
      } finally {
         if (flag11) {
            renderManager4.invoke20();
            renderManager4.invoke25();
         }
      }

      renderManager4.invoke20();
      renderManager4.invoke25();
   }

   private void invoke6(
      RenderManager renderManager5,
      ClickGuiState clickGuiState7,
      Metrics metrics5,
      ColorScheme colorScheme5,
      StudioAsset studioAsset3,
      float f,
      float g,
      float h,
      float i,
      float j,
      StudioAsset studioAsset4,
      float k,
      float l
   ) {
      boolean flag15 = studioAsset4 != null && studioAsset4.getText().equals(studioAsset3.getText());
      boolean flag16 = flag15 && StudioLibrary.resolve().check();
      float floatValue45 = metrics5.measure(10.0F);
      if (!flag15 && l > 0.01F) {
         renderManager5.invoke41(
            f,
            g,
            h,
            i,
            floatValue45,
            metrics5.measure(12.0F) * l,
            metrics5.measure(1.0F),
            ColorScheme.compute6(colorScheme5.getIntValue14(), Math.round(46.0F * l * k))
         );
      }

      int intValue8 = flag15
         ? ColorScheme.compute6(colorScheme5.getIntValue14(), Math.round((colorScheme5.isFlag() ? 40 : 54) * k))
         : ColorScheme.compute6(ColorScheme.compute7(colorScheme5.getIntValue4(), colorScheme5.getIntValue7(), l), Math.round(255.0F * k));
      renderManager5.invoke5(f, g, h, i, floatValue45, intValue8);
      if (flag15) {
         renderManager5.invoke41(
            f,
            g,
            h,
            i,
            floatValue45,
            metrics5.measure(14.0F),
            metrics5.measure(1.0F),
            ColorScheme.compute6(colorScheme5.getIntValue14(), Math.round(60.0F * k))
         );
         renderManager5.invoke28(f, g, h, i, floatValue45, ColorScheme.compute6(colorScheme5.getIntValue14(), Math.round(180.0F * k)), 0.9F);
      } else if (l > 0.01F) {
         renderManager5.invoke28(f, g, h, i, floatValue45, ColorScheme.compute6(colorScheme5.getIntValue14(), Math.round(80.0F * l * k)), 0.7F);
      }

      renderManager5.invoke24(
         f + metrics5.measure(4.0F),
         g + metrics5.measure(4.0F),
         h - metrics5.measure(8.0F),
         j - metrics5.measure(2.0F),
         floatValue45 * 0.7F,
         floatValue45 * 0.7F,
         0.0F,
         0.0F
      );

      try {
         renderManager5.invoke5(
            f + metrics5.measure(4.0F),
            g + metrics5.measure(4.0F),
            h - metrics5.measure(8.0F),
            j - metrics5.measure(2.0F),
            0.0F,
            ColorScheme.compute5(10, 12, 18, Math.round(230.0F * k))
         );
         StudioPreviewRenderer.invoke2(
            renderManager5,
            studioAsset3.resolve7(),
            studioAsset3.getText(),
            f + metrics5.measure(4.0F),
            g + metrics5.measure(4.0F),
            h - metrics5.measure(8.0F),
            j - metrics5.measure(2.0F),
            k
         );
      } finally {
         renderManager5.invoke20();
         renderManager5.invoke25();
      }

      String text5 = studioAsset3.getText6();
      if (text5 != null && !text5.isEmpty()) {
         String text6 = resolve17(text5, 10);
         float floatValue46 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, text6, 8.0F) + metrics5.measure(8.0F);
         renderManager5.invoke5(
            f + metrics5.measure(6.0F),
            g + metrics5.measure(6.0F),
            floatValue46,
            metrics5.measure(13.0F),
            metrics5.measure(6.0F),
            ColorScheme.compute6(colorScheme5.getIntValue14(), Math.round(210.0F * k))
         );
         ClickGuiRenderUtils.invoke4(
            renderManager5,
            metrics5,
            FontRegistry.fontObject,
            f + metrics5.measure(10.0F),
            g + metrics5.measure(6.0F),
            metrics5.measure(13.0F),
            8.0F,
            text6,
            ColorScheme.compute6(-1, Math.round(255.0F * k))
         );
      }

      float floatValue47 = g + j;
      ClickGuiRenderUtils.invoke4(
         renderManager5,
         metrics5,
         FontRegistry.fontObject,
         f + metrics5.measure(8.0F),
         floatValue47,
         metrics5.measure(20.0F),
         9.0F,
         resolve17(studioAsset3.getText3(), 16),
         ColorScheme.compute6(ClickGuiRenderUtils.compute2(colorScheme5), Math.round(255.0F * k))
      );
      StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData9 = this.resolve16(metrics5, f, g, h, j);
      this.invoke8(renderManager5, metrics5, colorScheme5, studioPanelRendererData9, flag16, k);
   }

   private void invoke7(RenderManager renderManager6, Metrics metrics6, ColorScheme colorScheme6, float f, float g, float h, float i, float j, float k) {
      renderManager6.invoke41(f, g, h, i, j, metrics6.measure(9.0F), metrics6.measure(1.4F), ColorScheme.compute5(0, 0, 0, Math.round(46.0F * k)));
      renderManager6.invoke5(f, g, h, i, j, ColorScheme.compute6(colorScheme6.getIntValue4(), Math.round(255.0F * k)));
      renderManager6.invoke28(f, g, h, i, j, ColorScheme.compute6(colorScheme6.getIntValue14(), Math.round(48.0F * k)), 0.8F);
      float floatValue48 = Math.max(0.0F, (h - j * 2.0F) * 0.5F);
      int intValue9 = ColorScheme.compute6(ColorScheme.compute7(-1, colorScheme6.getIntValue14(), 0.35F), Math.round(48.0F * k));
      int intValue10 = ColorScheme.compute6(intValue9, Math.round(8.0F * k));
      renderManager6.invoke34(f + j, g + metrics6.measure(1.0F), floatValue48, Math.max(1.0F, metrics6.measure(1.0F)), 0.0F, intValue10, intValue9);
      renderManager6.invoke34(f + j + floatValue48, g + metrics6.measure(1.0F), floatValue48, Math.max(1.0F, metrics6.measure(1.0F)), 0.0F, intValue9, intValue10);
   }

   private void invoke8(RenderManager renderManager7, Metrics metrics7, ColorScheme colorScheme7, StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData10, boolean bl, float f) {
      renderManager7.invoke5(
         studioPanelRendererData10.x,
         studioPanelRendererData10.y,
         studioPanelRendererData10.w,
         studioPanelRendererData10.h,
         studioPanelRendererData10.h * 0.5F,
         ColorScheme.compute6(bl ? colorScheme7.getIntValue14() : colorScheme7.getIntValue11(), Math.round((bl ? 220 : 255) * f))
      );
      float floatValue49 = studioPanelRendererData10.h - metrics7.measure(3.0F);
      float floatValue50 = bl ? studioPanelRendererData10.x + studioPanelRendererData10.w - floatValue49 - metrics7.measure(1.5F) : studioPanelRendererData10.x + metrics7.measure(1.5F);
      renderManager7.invoke39(
         floatValue50 + floatValue49 * 0.5F, studioPanelRendererData10.y + studioPanelRendererData10.h * 0.5F, floatValue49 * 0.5F, 0.0F, 1.0F, ColorScheme.compute6(-1, Math.round(255.0F * f))
      );
   }

   private void invoke9(
      RenderManager renderManager8,
      ClickGuiState clickGuiState8,
      ThemeContext themeContext3,
      Metrics metrics8,
      ColorScheme colorScheme8,
      StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData11,
      float f
   ) {
      StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData12 = this.resolve8(metrics8, studioPanelRendererData11);
      this.invoke7(renderManager8, metrics8, colorScheme8, studioPanelRendererData12.x, studioPanelRendererData12.y, studioPanelRendererData12.w, studioPanelRendererData12.h, metrics8.measure(12.0F), f);
      ClickGuiRenderUtils.invoke4(
         renderManager8,
         metrics8,
         FontRegistry.fontObject4,
         studioPanelRendererData12.x + metrics8.measure(14.0F),
         studioPanelRendererData12.y,
         metrics8.measure(34.0F),
         11.0F,
         "Превью",
         ColorScheme.compute6(ClickGuiRenderUtils.compute2(colorScheme8), Math.round(235.0F * f))
      );
      StudioAsset studioAsset5 = StudioLibrary.resolve().resolve4();
      StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData13 = this.resolve10(metrics8, studioPanelRendererData11);
      StudioPreviewRenderer.invoke(
         renderManager8, themeContext3, studioPanelRendererData13.x, studioPanelRendererData13.y, studioPanelRendererData13.w, studioPanelRendererData13.h, studioAsset5, this.floatValue7, this.floatValue8, this.floatValue9, f
      );
      StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData14 = this.resolve11(metrics8, studioPanelRendererData11);
      if (this.flag4 && studioAsset5 != null) {
         StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData15 = this.resolve14(metrics8, studioPanelRendererData11);
         renderManager8.invoke5(
            studioPanelRendererData15.x, studioPanelRendererData15.y, studioPanelRendererData15.w, studioPanelRendererData15.h, metrics8.measure(5.0F), ColorScheme.compute6(colorScheme8.getIntValue6(), Math.round(255.0F * f))
         );
         renderManager8.invoke28(
            studioPanelRendererData15.x, studioPanelRendererData15.y, studioPanelRendererData15.w, studioPanelRendererData15.h, metrics8.measure(5.0F), ColorScheme.compute6(colorScheme8.getIntValue14(), Math.round(170.0F * f)), 0.8F
         );
         float floatValue51 = (float)((Math.sin(System.currentTimeMillis() * 0.006) + 1.0) * 0.5);
         float floatValue52 = ClickGuiRenderUtils.measure(FontRegistry.fontObject4, this.text2, 11.0F);
         renderManager8.invoke23((int)(studioPanelRendererData15.x + metrics8.measure(7.0F)), (int)studioPanelRendererData15.y, (int)(studioPanelRendererData15.w - metrics8.measure(12.0F)), (int)studioPanelRendererData15.h);
         ClickGuiRenderUtils.invoke4(
            renderManager8,
            metrics8,
            FontRegistry.fontObject4,
            studioPanelRendererData15.x + metrics8.measure(7.0F),
            studioPanelRendererData15.y,
            studioPanelRendererData15.h,
            11.0F,
            this.text2,
            ColorScheme.compute6(ClickGuiRenderUtils.compute2(colorScheme8), Math.round(255.0F * f))
         );
         ClickGuiRenderUtils.invoke4(
            renderManager8,
            metrics8,
            FontRegistry.fontObject4,
            studioPanelRendererData15.x + metrics8.measure(7.0F) + floatValue52,
            studioPanelRendererData15.y,
            studioPanelRendererData15.h,
            11.0F,
            "|",
            ColorScheme.compute6(colorScheme8.getIntValue14(), Math.round(255.0F * floatValue51 * f))
         );
         renderManager8.invoke25();
      } else {
         String text7 = studioAsset5 == null ? "Ничего не выбрано" : studioAsset5.getText3();
         boolean flag17 = studioAsset5 != null && ClickGuiRenderUtils.check(clickGuiState8, studioPanelRendererData14.x, studioPanelRendererData14.y, studioPanelRendererData14.w * 0.7F, metrics8.measure(16.0F));
         ClickGuiRenderUtils.invoke4(
            renderManager8,
            metrics8,
            FontRegistry.fontObject4,
            studioPanelRendererData14.x,
            studioPanelRendererData14.y,
            metrics8.measure(18.0F),
            12.0F,
            resolve17(text7, 22),
            ColorScheme.compute6(flag17 ? colorScheme8.getIntValue14() : ClickGuiRenderUtils.compute2(colorScheme8), Math.round(255.0F * f))
         );
      }

      String text8 = this.resolve2(studioAsset5);
      ClickGuiRenderUtils.invoke4(
         renderManager8,
         metrics8,
         FontRegistry.fontObject,
         studioPanelRendererData14.x,
         studioPanelRendererData14.y + metrics8.measure(17.0F),
         metrics8.measure(15.0F),
         9.0F,
         resolve17(text8, 40),
         ColorScheme.compute6(ClickGuiRenderUtils.compute4(colorScheme8), Math.round(200.0F * f))
      );
      if (studioAsset5 != null) {
         StudioAssetCategory[] studioAssetCategories2 = StudioAssetCategory.values();
         float floatValue53 = metrics8.measure(20.0F);
         float floatValue54 = studioPanelRendererData14.y + metrics8.measure(34.0F);
         float floatValue55 = studioPanelRendererData14.x;

         for (StudioAssetCategory studioAssetCategory3 : studioAssetCategories2) {
            String text9 = studioAssetCategory3.getText2();
            float floatValue56 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, text9, 9.0F) + metrics8.measure(12.0F);
            boolean flag18 = studioAsset5.getStudioAssetCategory() == studioAssetCategory3;
            boolean flag19 = this.check10(renderManager8, CHIP0[studioAssetCategory3.ordinal()], floatValue55 + floatValue56 * 0.5F, floatValue54 + floatValue53 * 0.5F);

            try {
               renderManager8.invoke5(
                  floatValue55,
                  floatValue54,
                  floatValue56,
                  floatValue53,
                  floatValue53 * 0.5F,
                  ColorScheme.compute6(flag18 ? colorScheme8.getIntValue14() : colorScheme8.getIntValue4(), Math.round((flag18 ? 70 : 255) * f))
               );
               ClickGuiRenderUtils.invoke4(
                  renderManager8,
                  metrics8,
                  FontRegistry.fontObject,
                  floatValue55 + metrics8.measure(6.0F),
                  floatValue54,
                  floatValue53,
                  9.0F,
                  text9,
                  ColorScheme.compute6(flag18 ? ClickGuiRenderUtils.compute2(colorScheme8) : ClickGuiRenderUtils.compute4(colorScheme8), Math.round(255.0F * f))
               );
            } finally {
               this.invoke21(renderManager8, flag19);
            }

            floatValue55 += floatValue56 + metrics8.measure(4.0F);
         }

         StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData16 = this.resolve15(metrics8, studioPanelRendererData11);
         if (this.flag5) {
            renderManager8.invoke5(
               studioPanelRendererData16.x, studioPanelRendererData16.y, studioPanelRendererData16.w, studioPanelRendererData16.h, metrics8.measure(5.0F), ColorScheme.compute6(colorScheme8.getIntValue6(), Math.round(255.0F * f))
            );
            renderManager8.invoke28(
               studioPanelRendererData16.x,
               studioPanelRendererData16.y,
               studioPanelRendererData16.w,
               studioPanelRendererData16.h,
               metrics8.measure(5.0F),
               ColorScheme.compute6(colorScheme8.getIntValue14(), Math.round(170.0F * f)),
               0.8F
            );
            float floatValue57 = (float)((Math.sin(System.currentTimeMillis() * 0.006) + 1.0) * 0.5);
            float floatValue58 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, this.text3, 9.0F);
            renderManager8.invoke23((int)(studioPanelRendererData16.x + metrics8.measure(7.0F)), (int)studioPanelRendererData16.y, (int)(studioPanelRendererData16.w - metrics8.measure(12.0F)), (int)studioPanelRendererData16.h);
            ClickGuiRenderUtils.invoke4(
               renderManager8,
               metrics8,
               FontRegistry.fontObject,
               studioPanelRendererData16.x + metrics8.measure(7.0F),
               studioPanelRendererData16.y,
               studioPanelRendererData16.h,
               9.0F,
               this.text3,
               ColorScheme.compute6(ClickGuiRenderUtils.compute2(colorScheme8), Math.round(255.0F * f))
            );
            ClickGuiRenderUtils.invoke4(
               renderManager8,
               metrics8,
               FontRegistry.fontObject,
               studioPanelRendererData16.x + metrics8.measure(7.0F) + floatValue58,
               studioPanelRendererData16.y,
               studioPanelRendererData16.h,
               9.0F,
               "|",
               ColorScheme.compute6(colorScheme8.getIntValue14(), Math.round(255.0F * floatValue57 * f))
            );
            renderManager8.invoke25();
         } else {
            boolean flag20 = ClickGuiRenderUtils.check(clickGuiState8, studioPanelRendererData16.x, studioPanelRendererData16.y, studioPanelRendererData16.w, studioPanelRendererData16.h);
            renderManager8.invoke5(
               studioPanelRendererData16.x,
               studioPanelRendererData16.y,
               studioPanelRendererData16.w,
               studioPanelRendererData16.h,
               metrics8.measure(5.0F),
               ColorScheme.compute6(flag20 ? colorScheme8.getIntValue6() : colorScheme8.getIntValue4(), Math.round(255.0F * f))
            );
            String text10 = studioAsset5.getText6();
            if (text10 != null && !text10.isEmpty()) {
               float floatValue59 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, "Префикс: ", 9.0F);
               ClickGuiRenderUtils.invoke4(
                  renderManager8,
                  metrics8,
                  FontRegistry.fontObject,
                  studioPanelRendererData16.x + metrics8.measure(7.0F),
                  studioPanelRendererData16.y,
                  studioPanelRendererData16.h,
                  9.0F,
                  "Префикс: ",
                  ColorScheme.compute6(ClickGuiRenderUtils.compute4(colorScheme8), Math.round(200.0F * f))
               );
               ClickGuiRenderUtils.invoke4(
                  renderManager8,
                  metrics8,
                  FontRegistry.fontObject,
                  studioPanelRendererData16.x + metrics8.measure(7.0F) + floatValue59,
                  studioPanelRendererData16.y,
                  studioPanelRendererData16.h,
                  9.0F,
                  resolve17(text10, 18),
                  ColorScheme.compute6(colorScheme8.getIntValue14(), Math.round(255.0F * f))
               );
            } else {
               ClickGuiRenderUtils.invoke4(
                  renderManager8,
                  metrics8,
                  FontRegistry.fontObject,
                  studioPanelRendererData16.x + metrics8.measure(7.0F),
                  studioPanelRendererData16.y,
                  studioPanelRendererData16.h,
                  9.0F,
                  "+ префикс",
                  ColorScheme.compute6(ClickGuiRenderUtils.compute4(colorScheme8), Math.round(180.0F * f))
               );
            }
         }

         StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData17 = this.resolve12(metrics8, studioPanelRendererData11);
         boolean flag21 = StudioLibrary.resolve().check();
         boolean flag22 = ClickGuiRenderUtils.check(clickGuiState8, studioPanelRendererData17.x, studioPanelRendererData17.y, studioPanelRendererData17.w, studioPanelRendererData17.h);
         boolean flag23 = this.check10(renderManager8, "equip", studioPanelRendererData17.x + studioPanelRendererData17.w * 0.5F, studioPanelRendererData17.y + studioPanelRendererData17.h * 0.5F);

         try {
            int intValue11 = flag21
               ? ColorScheme.compute6(colorScheme8.getIntValue14(), Math.round((flag22 ? 200 : 160) * f))
               : ColorScheme.compute6(flag22 ? colorScheme8.getIntValue6() : colorScheme8.getIntValue4(), Math.round(255.0F * f));
            renderManager8.invoke5(studioPanelRendererData17.x, studioPanelRendererData17.y, studioPanelRendererData17.w, studioPanelRendererData17.h, metrics8.measure(8.0F), intValue11);
            renderManager8.invoke28(
               studioPanelRendererData17.x,
               studioPanelRendererData17.y,
               studioPanelRendererData17.w,
               studioPanelRendererData17.h,
               metrics8.measure(8.0F),
               ColorScheme.compute6(colorScheme8.getIntValue14(), Math.round(140.0F * f)),
               0.7F
            );
            String text11 = flag21 ? "Снять" : "Надеть";
            float floatValue60 = ClickGuiRenderUtils.measure(FontRegistry.fontObject4, text11, 11.0F);
            ClickGuiRenderUtils.invoke4(
               renderManager8,
               metrics8,
               FontRegistry.fontObject4,
               studioPanelRendererData17.x + (studioPanelRendererData17.w - floatValue60) * 0.5F,
               studioPanelRendererData17.y,
               studioPanelRendererData17.h,
               11.0F,
               text11,
               ColorScheme.compute6(flag21 ? ClickGuiRenderUtils.compute2(colorScheme8) : ClickGuiRenderUtils.compute4(colorScheme8), Math.round(255.0F * f))
            );
         } finally {
            this.invoke21(renderManager8, flag23);
         }

         StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData18 = this.resolve13(metrics8, studioPanelRendererData11);
         boolean flag24 = ClickGuiRenderUtils.check(clickGuiState8, studioPanelRendererData18.x, studioPanelRendererData18.y, studioPanelRendererData18.w, studioPanelRendererData18.h);
         boolean flag25 = this.flag6 && System.currentTimeMillis() - this.timestamp < 2600L;
         boolean flag26 = this.check10(renderManager8, "delete", studioPanelRendererData18.x + studioPanelRendererData18.w * 0.5F, studioPanelRendererData18.y + studioPanelRendererData18.h * 0.5F);
         boolean flag27 = false ;

         try {
            flag27 = true;
            int intValue12 = flag25
               ? ColorScheme.compute5(196, 64, 64, Math.round(235.0F * f))
               : ColorScheme.compute6(flag24 ? colorScheme8.getIntValue7() : colorScheme8.getIntValue4(), Math.round(255.0F * f));
            renderManager8.invoke5(studioPanelRendererData18.x, studioPanelRendererData18.y, studioPanelRendererData18.w, studioPanelRendererData18.h, metrics8.measure(8.0F), intValue12);
            renderManager8.invoke28(
               studioPanelRendererData18.x,
               studioPanelRendererData18.y,
               studioPanelRendererData18.w,
               studioPanelRendererData18.h,
               metrics8.measure(8.0F),
               flag25
                  ? ColorScheme.compute5(255, 120, 120, Math.round(220.0F * f))
                  : ColorScheme.compute6(colorScheme8.getIntValue9(), Math.round(190.0F * f)),
               0.7F
            );
            String text12 = flag25 ? "Точно?" : "Удалить";
            float floatValue61 = ClickGuiRenderUtils.measure(FontRegistry.fontObject4, text12, 10.0F);
            ClickGuiRenderUtils.invoke4(
               renderManager8,
               metrics8,
               FontRegistry.fontObject4,
               studioPanelRendererData18.x + (studioPanelRendererData18.w - floatValue61) * 0.5F,
               studioPanelRendererData18.y,
               studioPanelRendererData18.h,
               10.0F,
               text12,
               ColorScheme.compute6(flag25 ? -1 : ClickGuiRenderUtils.compute4(colorScheme8), Math.round(255.0F * f))
            );
            flag27 = false;
         } finally {
            if (flag27) {
               this.invoke21(renderManager8, flag26);
            }
         }

         this.invoke21(renderManager8, flag26);
      }
   }

   public boolean check4(ClickGuiState clickGuiState9, ThemeContext themeContext4, float f, float g, int i) {
      if (this.check(clickGuiState9) && this.metrics != null) {
         Metrics metrics9 = this.metrics;
         StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData19 = this.resolve3();
         if (!studioPanelRendererData19.contains(f, g)) {
            return false;
         } else if (i != 0) {
            return true;
         } else {
            this.flag2 = false;
            if (this.flag4 && !this.resolve14(metrics9, studioPanelRendererData19).contains(f, g)) {
               this.invoke17();
            }

            if (this.flag5 && !this.resolve15(metrics9, studioPanelRendererData19).contains(f, g)) {
               this.invoke14();
            }

            if (this.resolve4(metrics9, studioPanelRendererData19).contains(f, g)) {
               StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData20 = this.resolve4(metrics9, studioPanelRendererData19);
               if (!this.text.isEmpty() && f >= studioPanelRendererData20.x + studioPanelRendererData20.w - metrics9.measure(28.0F)) {
                  this.invoke24();
               }

               this.flag2 = true;
               this.flag3 = false;
               return true;
            } else if (this.resolve6(metrics9, studioPanelRendererData19).contains(f, g)) {
               this.invoke20("import");
               this.invoke19();
               return true;
            } else if (this.resolve5(metrics9, studioPanelRendererData19).contains(f, g)) {
               this.invoke20("reload");
               StudioLibrary.resolve().invoke2();
               this.setText5("Обновлено");
               return true;
            } else {
               StudioAssetCategory[] studioAssetCategories3 = StudioAssetCategory.values();
               float floatValue62 = studioPanelRendererData19.y + metrics9.measure(44.0F);
               float floatValue63 = metrics9.measure(26.0F);
               float floatValue64 = floatValue62 + (metrics9.measure(34.0F) - floatValue63) * 0.5F;
               float floatValue65 = studioPanelRendererData19.x + metrics9.measure(18.0F);

               for (StudioAssetCategory studioAssetCategory4 : studioAssetCategories3) {
                  float floatValue66 = ClickGuiRenderUtils.measure(FontRegistry.fontObject4, studioAssetCategory4.getText2(), 11.0F) + metrics9.measure(20.0F);
                  if (f >= floatValue65 && f <= floatValue65 + floatValue66 && g >= floatValue64 && g <= floatValue64 + floatValue63) {
                     this.invoke20(studioAssetCategory4.name());
                     if (this.studioAssetCategory != studioAssetCategory4) {
                        this.intValue = studioAssetCategory4.ordinal() > this.studioAssetCategory.ordinal() ? 1 : -1;
                        this.floatValue13 = 0.0F;
                        this.studioAssetCategory = studioAssetCategory4;
                        this.floatValue5 = this.floatValue6 = 0.0F;
                     }

                     return true;
                  }

                  floatValue65 += floatValue66 + metrics9.measure(6.0F);
               }

               if (this.resolve9(metrics9, studioPanelRendererData19).contains(f, g)) {
                  this.invoke10(metrics9, studioPanelRendererData19, f, g);
                  return true;
               } else if (this.resolve10(metrics9, studioPanelRendererData19).contains(f, g)) {
                  this.flag = true;
                  this.floatValue10 = f;
                  this.floatValue11 = g;
                  return true;
               } else {
                  this.invoke18(metrics9, studioPanelRendererData19, f, g);
                  return true;
               }
            }
         }
      } else {
         return false;
      }
   }

   private void invoke10(Metrics metrics10, StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData21, float f, float g) {
      StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData22 = this.resolve9(metrics10, studioPanelRendererData21);
      List items2 = this.resolve();
      int intValue13 = this.compute(metrics10, studioPanelRendererData22);
      float floatValue67 = metrics10.measure(10.0F);
      float floatValue68 = (studioPanelRendererData22.w - metrics10.measure(12.0F) - (intValue13 - 1) * floatValue67) / intValue13;
      float floatValue69 = floatValue68 + metrics10.measure(20.0F);
      float floatValue70 = metrics10.measure(10.0F);
      float floatValue71 = studioPanelRendererData22.x + metrics10.measure(6.0F);
      float floatValue72 = studioPanelRendererData22.y + metrics10.measure(6.0F) - this.floatValue5;
      int intValue14 = (int)Math.floor((f - floatValue71) / (floatValue68 + floatValue67));
      int intValue15 = (int)Math.floor((g - floatValue72) / (floatValue69 + floatValue70));
      if (intValue14 >= 0 && intValue14 < intValue13 && intValue15 >= 0) {
         int intValue16 = intValue15 * intValue13 + intValue14;
         if (intValue16 < items2.size()) {
            float floatValue73 = floatValue71 + intValue14 * (floatValue68 + floatValue67);
            float floatValue74 = floatValue72 + intValue15 * (floatValue69 + floatValue70);
            if (!(f > floatValue73 + floatValue68) && !(g > floatValue74 + floatValue69)) {
               StudioAsset studioAsset6 = (StudioAsset)items2.get(intValue16);
               StudioAsset studioAsset7 = StudioLibrary.resolve().resolve4();
               this.invoke20(studioAsset6.getText());
               StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData23 = this.resolve16(metrics10, floatValue73, floatValue74, floatValue68, floatValue68);
               if (!studioPanelRendererData23.contains(f, g)) {
                  this.invoke11(studioAsset6);
               } else {
                  if (studioAsset7 != null && studioAsset7.getText().equals(studioAsset6.getText())) {
                     StudioLibrary.resolve().setFlag(!StudioLibrary.resolve().check());
                  } else {
                     this.invoke11(studioAsset6);
                  }
               }
            }
         }
      }
   }

   private void invoke11(StudioAsset studioAsset8) {
      StudioLibrary.resolve().invoke3(studioAsset8);
      StudioTextureCache.getINSTANCE().invoke(studioAsset8.getText());
      this.floatValue7 = 200.0F;
      this.floatValue8 = -8.0F;
      this.floatValue9 = 1.0F;
      this.invoke16();
      this.invoke13();
      this.flag6 = false;
   }

   private void invoke12(StudioAsset studioAsset9) {
      this.flag5 = true;
      this.text3 = studioAsset9.getText6() == null ? "" : studioAsset9.getText6();
      this.flag4 = false;
      this.flag6 = false;
   }

   private void invoke13() {
      this.flag5 = false;
      this.text3 = "";
   }

   private void invoke14() {
      if (this.flag5) {
         StudioAsset studioAsset10 = StudioLibrary.resolve().resolve4();
         if (studioAsset10 != null) {
            StudioLibrary.resolve().invoke6(studioAsset10, this.text3);
            this.setText5("Префикс сохранён");
         }

         this.flag5 = false;
         this.text3 = "";
      }
   }

   private void invoke15(StudioAsset studioAsset11) {
      this.flag4 = true;
      this.text2 = studioAsset11.getText3() == null ? "" : studioAsset11.getText3();
      this.flag6 = false;
   }

   private void invoke16() {
      this.flag4 = false;
      this.text2 = "";
   }

   private void invoke17() {
      if (this.flag4) {
         StudioAsset studioAsset12 = StudioLibrary.resolve().resolve4();
         if (studioAsset12 != null) {
            StudioLibrary.resolve().invoke5(studioAsset12, this.text2);
            this.setText5("Переименовано");
         }

         this.flag4 = false;
         this.text2 = "";
      }
   }

   private void invoke18(Metrics metrics11, StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData24, float f, float g) {
      StudioAsset studioAsset13 = StudioLibrary.resolve().resolve4();
      if (studioAsset13 != null) {
         if (this.resolve14(metrics11, studioPanelRendererData24).contains(f, g)) {
            this.invoke15(studioAsset13);
         } else if (this.resolve15(metrics11, studioPanelRendererData24).contains(f, g)) {
            this.invoke12(studioAsset13);
         } else {
            StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData25 = this.resolve11(metrics11, studioPanelRendererData24);
            StudioAssetCategory[] studioAssetCategories4 = StudioAssetCategory.values();
            float floatValue75 = metrics11.measure(20.0F);
            float floatValue76 = studioPanelRendererData25.y + metrics11.measure(34.0F);
            float floatValue77 = studioPanelRendererData25.x;

            for (StudioAssetCategory studioAssetCategory5 : studioAssetCategories4) {
               float floatValue78 = ClickGuiRenderUtils.measure(FontRegistry.fontObject, studioAssetCategory5.getText2(), 9.0F) + metrics11.measure(12.0F);
               if (f >= floatValue77 && f <= floatValue77 + floatValue78 && g >= floatValue76 && g <= floatValue76 + floatValue75) {
                  this.invoke20(CHIP0[studioAssetCategory5.ordinal()]);
                  StudioLibrary.resolve().invoke4(studioAsset13, studioAssetCategory5);
                  this.setText5("Категория: " + studioAssetCategory5.getText2());
                  return;
               }

               floatValue77 += floatValue78 + metrics11.measure(4.0F);
            }

            StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData26 = this.resolve12(metrics11, studioPanelRendererData24);
            if (studioPanelRendererData26.contains(f, g)) {
               this.invoke20("equip");
               StudioLibrary.resolve().setFlag(!StudioLibrary.resolve().check());
            } else {
               StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData27 = this.resolve13(metrics11, studioPanelRendererData24);
               if (studioPanelRendererData27.contains(f, g)) {
                  this.invoke20("delete");
                  if (this.flag6 && System.currentTimeMillis() - this.timestamp < 2600L) {
                     StudioLibrary.resolve().check2(studioAsset13);
                     StudioTextureCache.getINSTANCE().invoke("");
                     this.flag6 = false;
                     this.setText5("Удалено");
                  } else {
                     this.flag6 = true;
                     this.timestamp = System.currentTimeMillis();
                  }
               }
            }
         }
      }
   }

   public boolean check5(ClickGuiState clickGuiState10, float f, float g) {
      this.flag = false;
      return this.check(clickGuiState10);
   }

   public boolean check6(ClickGuiState clickGuiState11, float f, float g) {
      if (!this.flag) {
         return false;
      } else {
         this.floatValue7 = this.floatValue7 + (f - this.floatValue10) * 0.55F;
         this.floatValue8 = measure2(this.floatValue8 + (g - this.floatValue11) * 0.55F, -89.0F, 89.0F);
         this.floatValue10 = f;
         this.floatValue11 = g;
         return true;
      }
   }

   public boolean check7(ClickGuiState clickGuiState12, float f, float g, double d) {
      if (this.check(clickGuiState12) && this.metrics != null) {
         Metrics metrics12 = this.metrics;
         StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData28 = this.resolve3();
         if (this.resolve10(metrics12, studioPanelRendererData28).contains(f, g)) {
            this.floatValue9 = measure2(this.floatValue9 * (float)(1.0 + d * 0.12), 0.35F, 4.0F);
            return true;
         } else if (this.resolve9(metrics12, studioPanelRendererData28).contains(f, g)) {
            this.floatValue6 = this.floatValue6 - (float)d * metrics12.measure(52.0F);
            return true;
         } else {
            return studioPanelRendererData28.contains(f, g);
         }
      } else {
         return false;
      }
   }

   public boolean check8(ClickGuiState clickGuiState13, int i) {
      if (!this.check(clickGuiState13)) {
         return false;
      } else if (this.flag4) {
         if (i == 256) {
            this.invoke16();
            return true;
         } else if (i == 257) {
            this.invoke17();
            return true;
         } else if (i == 259) {
            if (!this.text2.isEmpty()) {
               this.text2 = this.text2.substring(0, this.text2.length() - 1);
            }

            return true;
         } else {
            return true;
         }
      } else if (this.flag5) {
         if (i == 256) {
            this.invoke13();
            return true;
         } else if (i == 257) {
            this.invoke14();
            return true;
         } else if (i == 259) {
            if (!this.text3.isEmpty()) {
               this.text3 = this.text3.substring(0, this.text3.length() - 1);
            }

            return true;
         } else {
            return true;
         }
      } else if (!this.flag2) {
         return false;
      } else if (i != 256 && i != 257) {
         if (Screen.hasControlDown()) {
            if (i == 65) {
               this.flag3 = !this.text.isEmpty();
               return true;
            }

            if (i == 86) {
               if (this.flag3) {
                  this.invoke24();
                  this.flag3 = false;
               }

               String text13 = MinecraftClient.getInstance().keyboard.getClipboard();
               if (text13 != null) {
                  for (int intValue17 = 0; intValue17 < text13.length(); intValue17++) {
                     this.invoke22(text13.charAt(intValue17));
                  }
               }

               return true;
            }

            if (i == 67 && !this.text.isEmpty()) {
               MinecraftClient.getInstance().keyboard.setClipboard(this.text);
               this.setText5("Скопировано");
               return true;
            }

            if (i == 88) {
               if (!this.text.isEmpty()) {
                  MinecraftClient.getInstance().keyboard.setClipboard(this.text);
                  this.setText5("Вырезано");
               }

               this.invoke24();
               this.flag3 = false;
               return true;
            }

            if (i == 259) {
               this.invoke24();
               this.flag3 = false;
               return true;
            }
         }

         if (i == 259) {
            if (this.flag3) {
               this.invoke24();
               this.flag3 = false;
            } else {
               this.invoke23();
            }

            return true;
         } else if (i != 263 && i != 262) {
            return true;
         } else {
            this.flag3 = false;
            return true;
         }
      } else {
         this.flag2 = false;
         this.flag3 = false;
         return true;
      }
   }

   public boolean check9(ClickGuiState clickGuiState14, char c) {
      if (!this.check(clickGuiState14)) {
         return false;
      } else if (this.flag4) {
         if (c >= ' ' && c != 127 && this.text2.length() < 40) {
            this.text2 = this.text2 + c;
         }

         return true;
      } else if (this.flag5) {
         if (c >= ' ' && c != 127 && this.text3.length() < 24) {
            this.text3 = this.text3 + c;
         }

         return true;
      } else if (!this.flag2) {
         return false;
      } else {
         if (this.flag3) {
            this.invoke24();
            this.flag3 = false;
         }

         this.invoke22(c);
         return true;
      }
   }

   private void invoke19() {
      File file = FiguraAvatarImporter.resolve();
      if (file != null) {
         this.setText5(StudioLibrary.resolve().resolve5(file, this.studioAssetCategory));
      } else {
         FiguraAvatarImporter.invoke();
         this.setText5("Бросьте .zip в папку и нажмите обновить");
      }
   }

   private List<StudioAsset> resolve() {
      List items3 = StudioLibrary.resolve().resolve3(this.studioAssetCategory);
      if (this.text.isEmpty()) {
         return items3;
      } else {
         String text14 = this.text.toLowerCase();
         ArrayList arrayList = new ArrayList();

         for (StudioAsset studioAsset14 : (Iterable<StudioAsset>)items3) {
            String text15 = studioAsset14.getText3() == null ? "" : studioAsset14.getText3().toLowerCase();
            String text16 = studioAsset14.getText6() == null ? "" : studioAsset14.getText6().toLowerCase();
            String text17 = studioAsset14.getText4() == null ? "" : studioAsset14.getText4().toLowerCase();
            if (text15.contains(text14) || text16.contains(text14) || text17.contains(text14)) {
               arrayList.add(studioAsset14);
            }
         }

         return arrayList;
      }
   }

   private void setText5(String string) {
      this.text5 = string == null ? "" : string;
      this.timestamp4 = System.currentTimeMillis();
   }

   private String resolve2(StudioAsset studioAsset15) {
      if (!this.text5.isEmpty() && System.currentTimeMillis() - this.timestamp4 < 4200L) {
         return this.text5;
      } else if (studioAsset15 == null) {
         return "Тяните — вращать · колесо — зум";
      } else {
         return studioAsset15.getText4() != null && !studioAsset15.getText4().isEmpty() ? "Автор: " + studioAsset15.getText4() : "";
      }
   }

   private StudioPanelRenderer.StudioPanelRendererData resolve3() {
      return new StudioPanelRenderer.StudioPanelRendererData(this.floatValue, this.floatValue2, this.floatValue3, this.floatValue4);
   }

   private void invoke20(String string) {
      this.hashMap.put(string, System.currentTimeMillis());
   }

   private float measure(String string) {
      Long longValue5 = this.hashMap.get(string);
      if (longValue5 == null) {
         return 1.0F;
      } else {
         float floatValue79 = (float)(System.currentTimeMillis() - longValue5) / 320.0F;
         if (floatValue79 >= 1.0F) {
            return 1.0F;
         } else {
            float floatValue80 = (float)Math.exp(-floatValue79 * 4.0);
            float floatValue81 = (float)Math.cos(floatValue79 * Math.PI * 2.2);
            return 1.0F - 0.14F * floatValue80 * floatValue81;
         }
      }
   }

   private boolean check10(RenderManager renderManager9, String string, float f, float g) {
      float floatValue82 = this.measure(string);
      if (floatValue82 > 0.999F && floatValue82 < 1.001F) {
         return false;
      } else {
         renderManager9.invoke62(floatValue82, f, g);
         return true;
      }
   }

   private void invoke21(RenderManager renderManager10, boolean bl) {
      if (bl) {
         renderManager10.invoke64();
      }
   }

   private void invoke22(char c) {
      if (c >= ' ' && c != 127 && this.text.length() < 48) {
         this.text = this.text + c;
         this.arrayList.add(System.currentTimeMillis());
         this.floatValue6 = 0.0F;
      }
   }

   private void invoke23() {
      if (!this.text.isEmpty()) {
         int intValue18 = this.text.length() - 1;
         this.invoke25(intValue18);
         this.text = this.text.substring(0, intValue18);
         if (intValue18 < this.arrayList.size()) {
            this.arrayList.remove(intValue18);
         }

         this.floatValue6 = 0.0F;
      }
   }

   private void invoke24() {
      for (int intValue19 = 0; intValue19 < this.text.length(); intValue19++) {
         this.invoke25(intValue19);
      }

      this.text = "";
      this.arrayList.clear();
      this.floatValue6 = 0.0F;
   }

   private void invoke25(int i) {
      if (this.metrics != null && i >= 0 && i < this.text.length()) {
         StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData29 = this.resolve4(this.metrics, this.resolve3());
         float floatValue83 = studioPanelRendererData29.x + this.metrics.measure(26.0F) + ClickGuiRenderUtils.measure(FontRegistry.fontObject, this.text.substring(0, i), 10.0F);
         this.arrayList2.add(new StudioPanelRenderer.StudioPanelRendererTimedEntry(String.valueOf(this.text.charAt(i)), floatValue83, System.currentTimeMillis()));
      }
   }

   private StudioPanelRenderer.StudioPanelRendererData resolve4(Metrics metrics13, StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData30) {
      float floatValue84 = metrics13.measure(220.0F);
      float floatValue85 = metrics13.measure(28.0F);
      float floatValue86 = studioPanelRendererData30.x + studioPanelRendererData30.w - metrics13.measure(18.0F) - floatValue84;
      return new StudioPanelRenderer.StudioPanelRendererData(floatValue86, studioPanelRendererData30.y + (metrics13.measure(44.0F) - floatValue85) * 0.5F, floatValue84, floatValue85);
   }

   private StudioPanelRenderer.StudioPanelRendererData resolve5(Metrics metrics14, StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData31) {
      float floatValue87 = metrics14.measure(28.0F);
      float floatValue88 = studioPanelRendererData31.y + metrics14.measure(44.0F) + (metrics14.measure(34.0F) - floatValue87) * 0.5F;
      return new StudioPanelRenderer.StudioPanelRendererData(studioPanelRendererData31.x + studioPanelRendererData31.w - metrics14.measure(18.0F) - floatValue87, floatValue88, floatValue87, floatValue87);
   }

   private StudioPanelRenderer.StudioPanelRendererData resolve6(Metrics metrics15, StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData32) {
      float floatValue89 = metrics15.measure(28.0F);
      float floatValue90 = metrics15.measure(86.0F);
      StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData33 = this.resolve5(metrics15, studioPanelRendererData32);
      return new StudioPanelRenderer.StudioPanelRendererData(studioPanelRendererData33.x - metrics15.measure(8.0F) - floatValue90, studioPanelRendererData33.y, floatValue90, floatValue89);
   }

   private StudioPanelRenderer.StudioPanelRendererData resolve7(Metrics metrics16, StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData34) {
      float floatValue91 = metrics16.measure(18.0F);
      float floatValue92 = studioPanelRendererData34.y + metrics16.measure(44.0F) + metrics16.measure(34.0F) + metrics16.measure(6.0F);
      return new StudioPanelRenderer.StudioPanelRendererData(studioPanelRendererData34.x + floatValue91, floatValue92, studioPanelRendererData34.w - floatValue91 * 2.0F, studioPanelRendererData34.y + studioPanelRendererData34.h - floatValue91 - floatValue92);
   }

   private StudioPanelRenderer.StudioPanelRendererData resolve8(Metrics metrics17, StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData35) {
      StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData36 = this.resolve7(metrics17, studioPanelRendererData35);
      float floatValue93 = measure2(studioPanelRendererData36.w * 0.33F, metrics17.measure(280.0F), metrics17.measure(420.0F));
      return new StudioPanelRenderer.StudioPanelRendererData(studioPanelRendererData36.x + studioPanelRendererData36.w - floatValue93, studioPanelRendererData36.y, floatValue93, studioPanelRendererData36.h);
   }

   private StudioPanelRenderer.StudioPanelRendererData resolve9(Metrics metrics18, StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData37) {
      StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData38 = this.resolve7(metrics18, studioPanelRendererData37);
      StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData39 = this.resolve8(metrics18, studioPanelRendererData37);
      float floatValue94 = studioPanelRendererData39.x - metrics18.measure(12.0F) - studioPanelRendererData38.x;
      return new StudioPanelRenderer.StudioPanelRendererData(studioPanelRendererData38.x, studioPanelRendererData38.y, floatValue94, studioPanelRendererData38.h);
   }

   private StudioPanelRenderer.StudioPanelRendererData resolve10(Metrics metrics19, StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData40) {
      StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData41 = this.resolve8(metrics19, studioPanelRendererData40);
      StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData42 = this.resolve11(metrics19, studioPanelRendererData40);
      float floatValue95 = studioPanelRendererData41.y + metrics19.measure(34.0F);
      return new StudioPanelRenderer.StudioPanelRendererData(
         studioPanelRendererData41.x + metrics19.measure(10.0F),
         floatValue95,
         studioPanelRendererData41.w - metrics19.measure(20.0F),
         Math.max(metrics19.measure(40.0F), studioPanelRendererData42.y - floatValue95 - metrics19.measure(8.0F))
      );
   }

   private StudioPanelRenderer.StudioPanelRendererData resolve11(Metrics metrics20, StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData43) {
      StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData44 = this.resolve8(metrics20, studioPanelRendererData43);
      float floatValue96 = metrics20.measure(120.0F);
      return new StudioPanelRenderer.StudioPanelRendererData(
         studioPanelRendererData44.x + metrics20.measure(14.0F), studioPanelRendererData44.y + studioPanelRendererData44.h - metrics20.measure(12.0F) - floatValue96, studioPanelRendererData44.w - metrics20.measure(28.0F), floatValue96
      );
   }

   private StudioPanelRenderer.StudioPanelRendererData resolve12(Metrics metrics21, StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData45) {
      StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData46 = this.resolve11(metrics21, studioPanelRendererData45);
      float floatValue97 = metrics21.measure(28.0F);
      float floatValue98 = studioPanelRendererData46.w * 0.6F;
      return new StudioPanelRenderer.StudioPanelRendererData(studioPanelRendererData46.x, studioPanelRendererData46.y + studioPanelRendererData46.h - floatValue97, floatValue98, floatValue97);
   }

   private StudioPanelRenderer.StudioPanelRendererData resolve13(Metrics metrics22, StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData47) {
      StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData48 = this.resolve11(metrics22, studioPanelRendererData47);
      StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData49 = this.resolve12(metrics22, studioPanelRendererData47);
      float floatValue99 = studioPanelRendererData48.w - studioPanelRendererData49.w - metrics22.measure(8.0F);
      return new StudioPanelRenderer.StudioPanelRendererData(studioPanelRendererData49.x + studioPanelRendererData49.w + metrics22.measure(8.0F), studioPanelRendererData49.y, floatValue99, studioPanelRendererData49.h);
   }

   private StudioPanelRenderer.StudioPanelRendererData resolve14(Metrics metrics23, StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData50) {
      StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData51 = this.resolve11(metrics23, studioPanelRendererData50);
      return new StudioPanelRenderer.StudioPanelRendererData(studioPanelRendererData51.x, studioPanelRendererData51.y - metrics23.measure(2.0F), studioPanelRendererData51.w, metrics23.measure(16.0F));
   }

   private StudioPanelRenderer.StudioPanelRendererData resolve15(Metrics metrics24, StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData52) {
      StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData53 = this.resolve11(metrics24, studioPanelRendererData52);
      return new StudioPanelRenderer.StudioPanelRendererData(studioPanelRendererData53.x, studioPanelRendererData53.y + metrics24.measure(58.0F), studioPanelRendererData53.w, metrics24.measure(16.0F));
   }

   private StudioPanelRenderer.StudioPanelRendererData resolve16(Metrics metrics25, float f, float g, float h, float i) {
      float floatValue100 = metrics25.measure(28.0F);
      float floatValue101 = metrics25.measure(15.0F);
      return new StudioPanelRenderer.StudioPanelRendererData(f + h - floatValue100 - metrics25.measure(8.0F), g + i + (metrics25.measure(20.0F) - floatValue101) * 0.5F, floatValue100, floatValue101);
   }

   private int compute(Metrics metrics26, StudioPanelRenderer.StudioPanelRendererData studioPanelRendererData54) {
      return Math.max(3, Math.min(5, Math.round((studioPanelRendererData54.w - metrics26.measure(12.0F)) / metrics26.measure(132.0F))));
   }

   private static String resolve17(String string, int i) {
      if (string == null) {
         return "";
      } else {
         return string.length() <= i ? string : string.substring(0, i - 1) + "…";
      }
   }

   private static float measure2(float f, float g, float h) {
      return f < g ? g : Math.min(f, h);
   }

   record StudioPanelRendererTimedEntry(String ch, float x, long born) {
   }

   record StudioPanelRendererData(float x, float y, float w, float h) {

      boolean contains(float f, float g) {
         return f >= this.x && g >= this.y && f < this.x + this.w && g < this.y + this.h;
      }
   }
}
