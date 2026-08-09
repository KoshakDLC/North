package ru.metaculture.protection;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public final class ShaderNode {
   private final Map<String, ShaderNodeKind> valuesByKey = new LinkedHashMap<>();
   private final List<ShaderConnection> items = new ArrayList<>();
   private final ShaderTemplate shaderTemplate = new ShaderTemplate();
   private int intValue;
   private String preview = "preview";

   public ShaderTemplate getShaderTemplate() {
      return this.shaderTemplate;
   }

   public void invoke(ShaderTemplate shaderTemplate) {
      this.shaderTemplate.invoke(shaderTemplate);
      this.intValue++;
   }

   public String getPreview() {
      return this.preview;
   }

   public void invoke2(String string) {
      if (string != null && !string.isBlank() && !this.preview.equals(string)) {
         this.preview = string;
         this.intValue++;
      }
   }

   public ShaderNodeKind resolve(String string, float f, float g, ShaderNodeRegistry shaderNodeRegistry) {
      String text = resolve7(string);
      ShaderNodeKind shaderNodeKind = new ShaderNodeKind(text, string, f, g);
      ShaderNodeDefinition shaderNodeDefinition = shaderNodeRegistry.resolve(string);
      if (shaderNodeDefinition != null) {
         shaderNodeKind.setFloatValue3(shaderNodeDefinition.getFloatValue());
      }

      this.valuesByKey.put(text, shaderNodeKind);
      this.intValue++;
      return shaderNodeKind;
   }

   public void invoke3(ShaderNodeKind shaderNodeKind2, ShaderNodeRegistry shaderNodeRegistry2) {
      Objects.requireNonNull(shaderNodeKind2, "node");
      ShaderNodeDefinition shaderNodeDefinition2 = shaderNodeRegistry2.resolve(shaderNodeKind2.getText2());
      if (shaderNodeDefinition2 != null) {
         shaderNodeKind2.setFloatValue3(shaderNodeDefinition2.getFloatValue());
      }

      this.valuesByKey.put(shaderNodeKind2.getText(), shaderNodeKind2);
      this.intValue++;
   }

   public boolean check(String string) {
      ShaderNodeKind shaderNodeKind3 = this.valuesByKey.remove(string);
      if (shaderNodeKind3 == null) {
         return false;
      } else {
         this.items.removeIf(shaderConnection -> shaderConnection.getText().equals(string) || shaderConnection.getText3().equals(string));
         this.intValue++;
         return true;
      }
   }

   public boolean check2(String string, String string2, String string3, String string4, ShaderNodeRegistry shaderNodeRegistry3) {
      ShaderNodeKind shaderNodeKind4 = this.valuesByKey.get(string);
      ShaderNodeKind shaderNodeKind5 = this.valuesByKey.get(string3);
      if (shaderNodeKind4 != null && shaderNodeKind5 != null && shaderNodeKind4 != shaderNodeKind5) {
         ShaderNodeDefinition shaderNodeDefinition3 = shaderNodeRegistry3.resolve(shaderNodeKind4.getText2());
         ShaderNodeDefinition shaderNodeDefinition4 = shaderNodeRegistry3.resolve(shaderNodeKind5.getText2());
         if (shaderNodeDefinition3 != null && shaderNodeDefinition4 != null) {
            ShaderPin shaderPin = shaderNodeDefinition3.resolve2(string2);
            ShaderPin shaderPin2 = shaderNodeDefinition4.resolve(string4);
            if (shaderPin == null || shaderPin2 == null || shaderPin.type() != shaderPin2.type()) {
               return false;
            } else if (this.check4(string, string3)) {
               return false;
            } else {
               this.items.removeIf(shaderConnection2 -> shaderConnection2.getText3().equals(string3) && shaderConnection2.getText4().equals(string4));
               this.items.add(new ShaderConnection(string, string2, string3, string4));
               this.intValue++;
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public boolean check3(String string, String string2) {
      boolean flag = this.items.removeIf(shaderConnection3 -> shaderConnection3.getText3().equals(string) && shaderConnection3.getText4().equals(string2));
      if (flag) {
         this.intValue++;
      }

      return flag;
   }

   public Collection<ShaderNodeKind> resolve2() {
      return this.valuesByKey.values();
   }

   public List<ShaderConnection> getItems() {
      return this.items;
   }

   public ShaderNodeKind resolve3(String string) {
      return this.valuesByKey.get(string);
   }

   public ShaderConnection resolve4(String string, String string2) {
      for (ShaderConnection shaderConnection4 : this.items) {
         if (shaderConnection4.getText3().equals(string) && shaderConnection4.getText4().equals(string2)) {
            return shaderConnection4;
         }
      }

      return null;
   }

   public List<ShaderConnection> resolve5(String string) {
      ArrayList arrayList = new ArrayList();

      for (ShaderConnection shaderConnection5 : this.items) {
         if (shaderConnection5.getText().equals(string)) {
            arrayList.add(shaderConnection5);
         }
      }

      return arrayList;
   }

   public ShaderNode resolve6(String string) {
      ShaderNode shaderNode = new ShaderNode();
      shaderNode.preview = this.preview;
      shaderNode.shaderTemplate.invoke(this.shaderTemplate);
      if (string != null && this.valuesByKey.containsKey(string)) {
         LinkedHashSet linkedHashSet = new LinkedHashSet();
         ArrayDeque arrayDeque = new ArrayDeque();
         arrayDeque.push(string);

         while (!arrayDeque.isEmpty()) {
            String text2 = (String)arrayDeque.pop();
            if (linkedHashSet.add(text2)) {
               for (ShaderConnection shaderConnection6 : this.items) {
                  if (shaderConnection6.getText3().equals(text2)) {
                     arrayDeque.push(shaderConnection6.getText());
                  }
               }
            }
         }

         for (String text3 : (LinkedHashSet<String>)linkedHashSet) {
            ShaderNodeKind shaderNodeKind6 = this.valuesByKey.get(text3);
            if (shaderNodeKind6 != null) {
               ShaderNodeKind shaderNodeKind7 = new ShaderNodeKind(shaderNodeKind6.getText(), shaderNodeKind6.getText2(), shaderNodeKind6.getFloatValue(), shaderNodeKind6.getFloatValue2());
               shaderNodeKind7.setFloatValue3(shaderNodeKind6.getFloatValue3());
               shaderNodeKind7.getValuesByKey().putAll(shaderNodeKind6.getValuesByKey());
               shaderNodeKind7.getValuesByKey2().putAll(shaderNodeKind6.getValuesByKey2());
               shaderNode.valuesByKey.put(shaderNodeKind7.getText(), shaderNodeKind7);
            }
         }

         for (ShaderConnection shaderConnection7 : this.items) {
            if (linkedHashSet.contains(shaderConnection7.getText()) && linkedHashSet.contains(shaderConnection7.getText3())) {
               shaderNode.items.add(new ShaderConnection(shaderConnection7.getText(), shaderConnection7.getText2(), shaderConnection7.getText3(), shaderConnection7.getText4()));
            }
         }

         return shaderNode;
      } else {
         return shaderNode;
      }
   }

   public int getIntValue() {
      return this.intValue;
   }

   public void invoke4() {
      this.intValue++;
   }

   public void invoke5() {
      this.valuesByKey.clear();
      this.items.clear();
      this.intValue++;
   }

   public boolean check4(String string, String string2) {
      if (string.equals(string2)) {
         return true;
      } else {
         LinkedHashSet linkedHashSet2 = new LinkedHashSet();
         ArrayDeque arrayDeque2 = new ArrayDeque();
         arrayDeque2.push(string2);

         while (!arrayDeque2.isEmpty()) {
            String text4 = (String)arrayDeque2.pop();
            if (linkedHashSet2.add(text4)) {
               if (text4.equals(string)) {
                  return true;
               }

               for (ShaderConnection shaderConnection8 : this.items) {
                  if (shaderConnection8.getText().equals(text4)) {
                     arrayDeque2.push(shaderConnection8.getText3());
                  }
               }
            }
         }

         return false;
      }
   }

   private static String resolve7(String string) {
      String text5 = string != null && !string.isBlank() ? string.toLowerCase().replaceAll("[^a-z0-9]+", "_") : "node";
      return text5 + "_" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
   }
}
