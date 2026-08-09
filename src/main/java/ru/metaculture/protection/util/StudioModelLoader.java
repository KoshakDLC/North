package ru.metaculture.protection;

import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONObject;

public final class StudioModelLoader {
   private static final String[] NORTH = new String[]{"north", "east", "south", "west", "up", "down"};

   private StudioModelLoader() {
   }

   public static StudioModel resolve(String string) {
      JSONObject jsonObject = new JSONObject(string);
      int intValue = 16;
      int intValue2 = 16;
      JSONObject jsonObject2 = jsonObject.optJSONObject("resolution");
      if (jsonObject2 != null) {
         intValue = jsonObject2.optInt("width", 16);
         intValue2 = jsonObject2.optInt("height", 16);
      }

      List items = resolve2(jsonObject.optJSONArray("textures"), intValue, intValue2);
      HashMap hashMap = new HashMap();
      HashMap hashMap2 = new HashMap();
      JSONArray jsonArray = jsonObject.optJSONArray("elements");
      if (jsonArray != null) {
         for (int intValue3 = 0; intValue3 < jsonArray.length(); intValue3++) {
            JSONObject jsonObject3 = jsonArray.optJSONObject(intValue3);
            if (jsonObject3 != null) {
               String text = jsonObject3.optString("type", "cube");
               String text2 = jsonObject3.optString("uuid", "el" + intValue3);
               if ("mesh".equals(text)) {
                  StudioModel.StudioModelState4 studioModelState4 = resolve5(jsonObject3);
                  if (studioModelState4 != null) {
                     hashMap2.put(text2, studioModelState4);
                  }
               } else if ("cube".equals(text)) {
                  StudioModel.StudioModelState2 studioModelState2 = resolve6(jsonObject3);
                  if (studioModelState2 != null) {
                     hashMap.put(text2, studioModelState2);
                  }
               }
            }
         }
      }

      ArrayList arrayList = new ArrayList();
      JSONArray jsonArray2 = jsonObject.optJSONArray("outliner");
      if (jsonArray2 != null) {
         StudioModel.StudioModelState studioModelState = null;

         for (int intValue4 = 0; intValue4 < jsonArray2.length(); intValue4++) {
            Object object = jsonArray2.get(intValue4);
            if (object instanceof JSONObject jsonObject4) {
               StudioModel.StudioModelState studioModelState3 = resolve4(jsonObject4, hashMap, hashMap2);
               if (studioModelState3 != null) {
                  arrayList.add(studioModelState3);
               }
            } else if (object instanceof String text3) {
               StudioModel.StudioModelState2 studioModelState22 = (StudioModel.StudioModelState2)hashMap.get(text3);
               StudioModel.StudioModelState4 studioModelState42 = (StudioModel.StudioModelState4)hashMap2.get(text3);
               if (studioModelState22 != null || studioModelState42 != null) {
                  if (studioModelState == null) {
                     studioModelState = new StudioModel.StudioModelState("root", 0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
                     arrayList.add(studioModelState);
                  }

                  if (studioModelState22 != null) {
                     studioModelState.getItems2().add(studioModelState22);
                  }

                  if (studioModelState42 != null) {
                     studioModelState.getItems3().add(studioModelState42);
                  }
               }
            }
         }
      }

      return new StudioModel(intValue, intValue2, items, arrayList);
   }

   private static List<StudioModel.StudioModelState6> resolve2(JSONArray jSONArray, int i, int j) {
      ArrayList arrayList2 = new ArrayList();
      if (jSONArray == null) {
         return arrayList2;
      } else {
         for (int intValue5 = 0; intValue5 < jSONArray.length(); intValue5++) {
            JSONObject jsonObject5 = jSONArray.optJSONObject(intValue5);
            if (jsonObject5 != null) {
               String text4 = jsonObject5.optString("name", "texture_" + intValue5);
               int intValue6 = jsonObject5.optInt("uv_width", jsonObject5.optInt("width", i));
               int intValue7 = jsonObject5.optInt("uv_height", jsonObject5.optInt("height", j));
               byte[] byteValues = resolve3(jsonObject5.optString("source", ""));
               arrayList2.add(new StudioModel.StudioModelState6(text4, byteValues, intValue6, intValue7));
            }
         }

         return arrayList2;
      }
   }

   private static byte[] resolve3(String string) {
      if (string != null && !string.isEmpty()) {
         int intValue8 = string.indexOf(44);
         String text5 = string.startsWith("data:") && intValue8 >= 0 ? string.substring(intValue8 + 1) : string;

         try {
            return Base64.getDecoder().decode(text5.replaceAll("\\s", ""));
         } catch (RuntimeException exception) {
            return new byte[0];
         }
      } else {
         return new byte[0];
      }
   }

   private static StudioModel.StudioModelState resolve4(JSONObject jSONObject, Map<String, StudioModel.StudioModelState2> map, Map<String, StudioModel.StudioModelState4> map2) {
      String text6 = jSONObject.optString("name", "");
      float[] floatValues = resolve7(jSONObject.optJSONArray("origin"), 0.0F);
      float[] floatValues2 = resolve7(jSONObject.optJSONArray("rotation"), 0.0F);
      StudioModel.StudioModelState studioModelState5 = new StudioModel.StudioModelState(text6, floatValues[0], floatValues[1], floatValues[2], floatValues2[0], floatValues2[1], floatValues2[2]);
      JSONArray jsonArray3 = jSONObject.optJSONArray("children");
      if (jsonArray3 != null) {
         for (int intValue9 = 0; intValue9 < jsonArray3.length(); intValue9++) {
            Object object2 = jsonArray3.get(intValue9);
            if (object2 instanceof JSONObject jsonObject6) {
               StudioModel.StudioModelState studioModelState6 = resolve4(jsonObject6, map, map2);
               if (studioModelState6 != null) {
                  studioModelState5.getItems().add(studioModelState6);
               }
            } else if (object2 instanceof String text7) {
               StudioModel.StudioModelState2 studioModelState23 = (StudioModel.StudioModelState2)map.get(text7);
               if (studioModelState23 != null) {
                  studioModelState5.getItems2().add(studioModelState23);
               }

               StudioModel.StudioModelState4 studioModelState43 = (StudioModel.StudioModelState4)map2.get(text7);
               if (studioModelState43 != null) {
                  studioModelState5.getItems3().add(studioModelState43);
               }
            }
         }
      }

      return studioModelState5;
   }

   private static StudioModel.StudioModelState4 resolve5(JSONObject jSONObject) {
      float[] floatValues3 = resolve7(jSONObject.optJSONArray("origin"), 0.0F);
      float[] floatValues4 = resolve7(jSONObject.optJSONArray("rotation"), 0.0F);
      JSONObject jsonObject7 = jSONObject.optJSONObject("vertices");
      if (jsonObject7 != null && !jsonObject7.isEmpty()) {
         HashMap hashMap3 = new HashMap();
         float[] floatValues5 = new float[jsonObject7.length() * 3];
         int intValue10 = 0;

         for (String text8 : jsonObject7.keySet()) {
            JSONArray jsonArray4 = jsonObject7.optJSONArray(text8);
            if (jsonArray4 != null && jsonArray4.length() >= 3) {
               hashMap3.put(text8, intValue10);
               floatValues5[intValue10 * 3] = (float)jsonArray4.optDouble(0, 0.0);
               floatValues5[intValue10 * 3 + 1] = (float)jsonArray4.optDouble(1, 0.0);
               floatValues5[intValue10 * 3 + 2] = (float)jsonArray4.optDouble(2, 0.0);
               intValue10++;
            }
         }

         JSONObject jsonObject8 = jSONObject.optJSONObject("faces");
         if (jsonObject8 == null) {
            return null;
         } else {
            ArrayList arrayList3 = new ArrayList();

            for (String text9 : jsonObject8.keySet()) {
               JSONObject jsonObject9 = jsonObject8.optJSONObject(text9);
               if (jsonObject9 != null) {
                  JSONArray jsonArray5 = jsonObject9.optJSONArray("vertices");
                  JSONObject jsonObject10 = jsonObject9.optJSONObject("uv");
                  if (jsonArray5 != null && jsonArray5.length() >= 3) {
                     int intValue11 = Math.min(4, jsonArray5.length());
                     int[] intValues = new int[4];
                     float[] floatValues6 = new float[4];
                     float[] floatValues7 = new float[4];
                     boolean flag = true;

                     for (int intValue12 = 0; intValue12 < intValue11; intValue12++) {
                        String text10 = jsonArray5.optString(intValue12, "");
                        Integer integerValue = (Integer)hashMap3.get(text10);
                        if (integerValue == null) {
                           flag = false;
                           break;
                        }

                        intValues[intValue12] = integerValue;
                        JSONArray jsonArray6 = jsonObject10 == null ? null : jsonObject10.optJSONArray(text10);
                        floatValues6[intValue12] = jsonArray6 == null ? 0.0F : (float)jsonArray6.optDouble(0, 0.0);
                        floatValues7[intValue12] = jsonArray6 == null ? 0.0F : (float)jsonArray6.optDouble(1, 0.0);
                     }

                     if (flag) {
                        arrayList3.add(new StudioModel.StudioModelState5(intValue11, intValues, floatValues6, floatValues7, jsonObject9.optInt("texture", 0)));
                     }
                  }
               }
            }

            return arrayList3.isEmpty()
               ? null
               : new StudioModel.StudioModelState4(floatValues3[0], floatValues3[1], floatValues3[2], floatValues4[0], floatValues4[1], floatValues4[2], floatValues5, (StudioModel.StudioModelState5[])arrayList3.toArray(new StudioModel.StudioModelState5[0]));
         }
      } else {
         return null;
      }
   }

   private static StudioModel.StudioModelState2 resolve6(JSONObject jSONObject) {
      float[] floatValues8 = resolve7(jSONObject.optJSONArray("from"), 0.0F);
      float[] floatValues9 = resolve7(jSONObject.optJSONArray("to"), 0.0F);
      float[] floatValues10 = resolve7(jSONObject.optJSONArray("origin"), 0.0F);
      float[] floatValues11 = resolve7(jSONObject.optJSONArray("rotation"), 0.0F);
      float floatValue = (float)jSONObject.optDouble("inflate", 0.0);
      JSONObject jsonObject11 = jSONObject.optJSONObject("faces");
      StudioModel.StudioModelState3[] w198s = new StudioModel.StudioModelState3[NORTH.length];
      boolean flag2 = false;
      if (jsonObject11 != null) {
         for (int intValue13 = 0; intValue13 < NORTH.length; intValue13++) {
            JSONObject jsonObject12 = jsonObject11.optJSONObject(NORTH[intValue13]);
            if (jsonObject12 != null && !jsonObject12.isNull("texture")) {
               JSONArray jsonArray7 = jsonObject12.optJSONArray("uv");
               if (jsonArray7 != null && jsonArray7.length() >= 4) {
                  int intValue14 = jsonObject12.optInt("texture", 0);
                  w198s[intValue13] = new StudioModel.StudioModelState3(
                     intValue14, (float)jsonArray7.optDouble(0, 0.0), (float)jsonArray7.optDouble(1, 0.0), (float)jsonArray7.optDouble(2, 0.0), (float)jsonArray7.optDouble(3, 0.0)
                  );
                  flag2 = true;
               }
            }
         }
      }

      return !flag2
         ? null
         : new StudioModel.StudioModelState2(floatValues8[0], floatValues8[1], floatValues8[2], floatValues9[0], floatValues9[1], floatValues9[2], floatValues10[0], floatValues10[1], floatValues10[2], floatValues11[0], floatValues11[1], floatValues11[2], floatValue, w198s);
   }

   private static float[] resolve7(JSONArray jSONArray, float f) {
      float[] floatValues12 = new float[]{f, f, f};
      if (jSONArray == null) {
         return floatValues12;
      } else {
         for (int intValue15 = 0; intValue15 < 3 && intValue15 < jSONArray.length(); intValue15++) {
            floatValues12[intValue15] = (float)jSONArray.optDouble(intValue15, f);
         }

         return floatValues12;
      }
   }
}
