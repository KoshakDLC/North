package ru.metaculture.protection;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONObject;

public final class ShaderLibraryState {
   private static final ShaderLibraryState INSTANCE = new ShaderLibraryState();
   private static final int INT_VALUE = 8;
   private final LinkedHashSet<String> linkedHashSet = new LinkedHashSet<>();
   private final ArrayList<String> arrayList = new ArrayList<>();
   private boolean flag;

   private ShaderLibraryState() {
   }

   public static ShaderLibraryState getINSTANCE() {
      return INSTANCE;
   }

   public synchronized Set<String> resolve() {
      this.invoke3();
      return new LinkedHashSet<>(this.linkedHashSet);
   }

   public synchronized List<String> resolve2() {
      this.invoke3();
      return new ArrayList<>(this.arrayList);
   }

   public synchronized boolean check(String string) {
      this.invoke3();
      return string != null && this.linkedHashSet.contains(string);
   }

   public synchronized void invoke(String string) {
      if (string != null && !string.isBlank()) {
         this.invoke3();
         if (!this.linkedHashSet.remove(string)) {
            this.linkedHashSet.add(string);
         }

         this.invoke4();
      }
   }

   public synchronized void invoke2(String string) {
      if (string != null && !string.isBlank()) {
         this.invoke3();
         this.arrayList.remove(string);
         this.arrayList.add(0, string);

         while (this.arrayList.size() > 8) {
            this.arrayList.remove(this.arrayList.size() - 1);
         }

         this.invoke4();
      }
   }

   private File resolve3() {
      return new File(ShaderPresetStore.getINSTANCE().resolve7(), "library.json");
   }

   private void invoke3() {
      if (!this.flag) {
         this.flag = true;

         try {
            File file = this.resolve3();
            if (!file.isFile()) {
               return;
            }

            JSONObject jsonObject = new JSONObject(new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8));
            JSONArray jsonArray = jsonObject.optJSONArray("favorites");
            if (jsonArray != null) {
               for (int intValue = 0; intValue < jsonArray.length(); intValue++) {
                  String text = jsonArray.optString(intValue, "");
                  if (!text.isBlank()) {
                     this.linkedHashSet.add(text);
                  }
               }
            }

            JSONArray jsonArray2 = jsonObject.optJSONArray("recents");
            if (jsonArray2 != null) {
               for (int intValue2 = 0; intValue2 < jsonArray2.length() && this.arrayList.size() < 8; intValue2++) {
                  String text2 = jsonArray2.optString(intValue2, "");
                  if (!text2.isBlank() && !this.arrayList.contains(text2)) {
                     this.arrayList.add(text2);
                  }
               }
            }
         } catch (Throwable exception) {
         }
      }
   }

   private void invoke4() {
      try {
         JSONObject jsonObject2 = new JSONObject();
         jsonObject2.put("favorites", new JSONArray(this.linkedHashSet));
         jsonObject2.put("recents", new JSONArray(this.arrayList));
         Files.write(this.resolve3().toPath(), jsonObject2.toString(2).getBytes(StandardCharsets.UTF_8));
      } catch (Throwable exception2) {
      }
   }
}
