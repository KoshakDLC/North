package ru.metaculture.protection;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;

public class KeyCodeUtils {
   public static final Map<String, Integer> VALUES_BY_KEY = new HashMap<>();
   public static final Map<Integer, String> VALUES_BY_KEY_2 = new HashMap<>();
   public static MinecraftClient client = MinecraftClient.getInstance();

   public static boolean check(int i) {
      return InputUtil.isKeyPressed(client.getWindow().getHandle(), i);
   }

   public static String resolve(int i) {
      if (i == -1) {
         return "KEY";
      } else if (i == -200) {
         return "Wheel Up";
      } else if (i == -201) {
         return "Wheel Down";
      } else if (i == -100) {
         return "Mouse Left";
      } else if (i == -101) {
         return "Mouse Right";
      } else if (i == -102) {
         return "Mouse Middle";
      } else if (i == -103) {
         return "Mouse 4";
      } else if (i == -104) {
         return "Mouse 5";
      } else if (i == -105) {
         return "Mouse 6";
      } else if (i == -106) {
         return "Mouse 7";
      } else if (i == -107) {
         return "Mouse 8";
      } else if (i == -108) {
         return "Mouse 9";
      } else if (i == 32) {
         return "Space";
      } else if (i == 39) {
         return "Apostrophe";
      } else if (i == 44) {
         return "Comma";
      } else if (i == 45) {
         return "Minus";
      } else if (i == 46) {
         return "Period";
      } else if (i == 47) {
         return "Slash";
      } else if (i == 48) {
         return "0";
      } else if (i == 49) {
         return "1";
      } else if (i == 50) {
         return "2";
      } else if (i == 51) {
         return "3";
      } else if (i == 52) {
         return "4";
      } else if (i == 53) {
         return "5";
      } else if (i == 54) {
         return "6";
      } else if (i == 55) {
         return "7";
      } else if (i == 56) {
         return "8";
      } else if (i == 57) {
         return "9";
      } else if (i == 59) {
         return "SemiColon";
      } else if (i == 61) {
         return "Equal";
      } else if (i == 65) {
         return "A";
      } else if (i == 66) {
         return "B";
      } else if (i == 67) {
         return "C";
      } else if (i == 68) {
         return "D";
      } else if (i == 69) {
         return "E";
      } else if (i == 70) {
         return "F";
      } else if (i == 71) {
         return "G";
      } else if (i == 72) {
         return "H";
      } else if (i == 73) {
         return "I";
      } else if (i == 74) {
         return "J";
      } else if (i == 75) {
         return "K";
      } else if (i == 76) {
         return "L";
      } else if (i == 77) {
         return "M";
      } else if (i == 78) {
         return "N";
      } else if (i == 79) {
         return "O";
      } else if (i == 80) {
         return "P";
      } else if (i == 81) {
         return "Q";
      } else if (i == 82) {
         return "R";
      } else if (i == 83) {
         return "S";
      } else if (i == 84) {
         return "T";
      } else if (i == 85) {
         return "U";
      } else if (i == 86) {
         return "V";
      } else if (i == 87) {
         return "W";
      } else if (i == 88) {
         return "X";
      } else if (i == 89) {
         return "Y";
      } else if (i == 90) {
         return "Z";
      } else if (i == 91) {
         return "LeftBracket";
      } else if (i == 92) {
         return "BackSlash";
      } else if (i == 93) {
         return "RightBracket";
      } else if (i == 96) {
         return "GraveAccent";
      } else if (i == 161) {
         return "World1";
      } else if (i == 162) {
         return "World2";
      } else if (i == 256) {
         return "Escape";
      } else if (i == 257) {
         return "Enter";
      } else if (i == 258) {
         return "Tab";
      } else if (i == 259) {
         return "BackSpace";
      } else if (i == 260) {
         return "Insert";
      } else if (i == 261) {
         return "Delete";
      } else if (i == 262) {
         return "Right";
      } else if (i == 263) {
         return "Left";
      } else if (i == 264) {
         return "Down";
      } else if (i == 265) {
         return "Up";
      } else if (i == 266) {
         return "PageUp";
      } else if (i == 267) {
         return "PageDown";
      } else if (i == 268) {
         return "Home";
      } else if (i == 269) {
         return "End";
      } else if (i == 280) {
         return "CapsLock";
      } else if (i == 281) {
         return "ScrollLock";
      } else if (i == 282) {
         return "NumLock";
      } else if (i == 283) {
         return "PrintScreen";
      } else if (i == 284) {
         return "Pause";
      } else if (i == 290) {
         return "F1";
      } else if (i == 291) {
         return "F2";
      } else if (i == 292) {
         return "F3";
      } else if (i == 293) {
         return "F4";
      } else if (i == 294) {
         return "F5";
      } else if (i == 295) {
         return "F6";
      } else if (i == 296) {
         return "F7";
      } else if (i == 297) {
         return "F8";
      } else if (i == 298) {
         return "F9";
      } else if (i == 299) {
         return "F10";
      } else if (i == 300) {
         return "F11";
      } else if (i == 301) {
         return "F12";
      } else if (i == 302) {
         return "F13";
      } else if (i == 303) {
         return "F14";
      } else if (i == 304) {
         return "F15";
      } else if (i == 305) {
         return "F16";
      } else if (i == 306) {
         return "F17";
      } else if (i == 307) {
         return "F18";
      } else if (i == 308) {
         return "F19";
      } else if (i == 309) {
         return "F20";
      } else if (i == 310) {
         return "F21";
      } else if (i == 311) {
         return "F22";
      } else if (i == 312) {
         return "F23";
      } else if (i == 313) {
         return "F24";
      } else if (i == 314) {
         return "F25";
      } else if (i == 320) {
         return "NUM 0";
      } else if (i == 321) {
         return "NUM 1";
      } else if (i == 322) {
         return "NUM 2";
      } else if (i == 323) {
         return "NUM 3";
      } else if (i == 324) {
         return "NUM 4";
      } else if (i == 325) {
         return "NUM 5";
      } else if (i == 326) {
         return "NUM 6";
      } else if (i == 327) {
         return "NUM 7";
      } else if (i == 328) {
         return "NUM 8";
      } else if (i == 329) {
         return "NUM 9";
      } else if (i == 330) {
         return "Decimal";
      } else if (i == 331) {
         return "Divine";
      } else if (i == 332) {
         return "Multiply";
      } else if (i == 333) {
         return "Subtract";
      } else if (i == 334) {
         return "Add";
      } else if (i == 335) {
         return "Enter";
      } else if (i == 336) {
         return "Equal";
      } else if (i == 340) {
         return "LeftShift";
      } else if (i == 341) {
         return "LeftControl";
      } else if (i == 342) {
         return "LeftAlt";
      } else if (i == 343) {
         return "LeftSuper";
      } else if (i == 344) {
         return "RightShift";
      } else if (i == 345) {
         return "RightControl";
      } else if (i == 346) {
         return "RightAlt";
      } else if (i == 347) {
         return "RightSuper";
      } else {
         return i == 348 ? "Menu" : "error";
      }
   }

   private static void invoke() {
      VALUES_BY_KEY.put("A", 65);
      VALUES_BY_KEY.put("B", 66);
      VALUES_BY_KEY.put("C", 67);
      VALUES_BY_KEY.put("D", 68);
      VALUES_BY_KEY.put("E", 69);
      VALUES_BY_KEY.put("F", 70);
      VALUES_BY_KEY.put("G", 71);
      VALUES_BY_KEY.put("H", 72);
      VALUES_BY_KEY.put("I", 73);
      VALUES_BY_KEY.put("J", 74);
      VALUES_BY_KEY.put("K", 75);
      VALUES_BY_KEY.put("L", 76);
      VALUES_BY_KEY.put("M", 77);
      VALUES_BY_KEY.put("N", 78);
      VALUES_BY_KEY.put("O", 79);
      VALUES_BY_KEY.put("P", 80);
      VALUES_BY_KEY.put("Q", 81);
      VALUES_BY_KEY.put("R", 82);
      VALUES_BY_KEY.put("S", 83);
      VALUES_BY_KEY.put("T", 84);
      VALUES_BY_KEY.put("U", 85);
      VALUES_BY_KEY.put("V", 86);
      VALUES_BY_KEY.put("W", 87);
      VALUES_BY_KEY.put("X", 88);
      VALUES_BY_KEY.put("Y", 89);
      VALUES_BY_KEY.put("Z", 90);
      VALUES_BY_KEY.put("0", 48);
      VALUES_BY_KEY.put("1", 49);
      VALUES_BY_KEY.put("2", 50);
      VALUES_BY_KEY.put("3", 51);
      VALUES_BY_KEY.put("4", 52);
      VALUES_BY_KEY.put("5", 53);
      VALUES_BY_KEY.put("6", 54);
      VALUES_BY_KEY.put("7", 55);
      VALUES_BY_KEY.put("8", 56);
      VALUES_BY_KEY.put("9", 57);
      VALUES_BY_KEY.put("F1", 290);
      VALUES_BY_KEY.put("F2", 291);
      VALUES_BY_KEY.put("F3", 292);
      VALUES_BY_KEY.put("F4", 293);
      VALUES_BY_KEY.put("F5", 294);
      VALUES_BY_KEY.put("F6", 295);
      VALUES_BY_KEY.put("F7", 296);
      VALUES_BY_KEY.put("F8", 297);
      VALUES_BY_KEY.put("F9", 298);
      VALUES_BY_KEY.put("F10", 299);
      VALUES_BY_KEY.put("F11", 300);
      VALUES_BY_KEY.put("F12", 301);
      VALUES_BY_KEY.put("NUMPAD1", 321);
      VALUES_BY_KEY.put("NUMPAD2", 322);
      VALUES_BY_KEY.put("NUMPAD3", 323);
      VALUES_BY_KEY.put("NUMPAD4", 324);
      VALUES_BY_KEY.put("NUMPAD5", 325);
      VALUES_BY_KEY.put("NUMPAD6", 326);
      VALUES_BY_KEY.put("NUMPAD7", 327);
      VALUES_BY_KEY.put("NUMPAD8", 328);
      VALUES_BY_KEY.put("NUMPAD9", 329);
      VALUES_BY_KEY.put("SPACE", 32);
      VALUES_BY_KEY.put("ENTER", 257);
      VALUES_BY_KEY.put("ESCAPE", 256);
      VALUES_BY_KEY.put("HOME", 268);
      VALUES_BY_KEY.put("INSERT", 260);
      VALUES_BY_KEY.put("DELETE", 261);
      VALUES_BY_KEY.put("END", 269);
      VALUES_BY_KEY.put("PAGEUP", 266);
      VALUES_BY_KEY.put("PAGEDOWN", 267);
      VALUES_BY_KEY.put("RIGHT", 262);
      VALUES_BY_KEY.put("LEFT", 263);
      VALUES_BY_KEY.put("DOWN", 264);
      VALUES_BY_KEY.put("UP", 265);
      VALUES_BY_KEY.put("RIGHT_SHIFT", 344);
      VALUES_BY_KEY.put("LEFT_SHIFT", 340);
      VALUES_BY_KEY.put("RIGHT_CONTROL", 345);
      VALUES_BY_KEY.put("LEFT_CONTROL", 341);
      VALUES_BY_KEY.put("RIGHT_ALT", 346);
      VALUES_BY_KEY.put("LEFT_ALT", 342);
      VALUES_BY_KEY.put("RIGHT_SUPER", 347);
      VALUES_BY_KEY.put("LEFT_SUPER", 343);
      VALUES_BY_KEY.put("MENU", 348);
      VALUES_BY_KEY.put("CAPS_LOCK", 280);
      VALUES_BY_KEY.put("NUM_LOCK", 282);
      VALUES_BY_KEY.put("SCROLL_LOCK", 281);
      VALUES_BY_KEY.put("KP_DECIMAL", 330);
      VALUES_BY_KEY.put("KP_DIVIDE", 331);
      VALUES_BY_KEY.put("KP_MULTIPLY", 332);
      VALUES_BY_KEY.put("KP_SUBTRACT", 333);
      VALUES_BY_KEY.put("KP_PLUS", 334);
      VALUES_BY_KEY.put("KP_ENTER", 335);
      VALUES_BY_KEY.put("KP_EQUAL", 336);
      VALUES_BY_KEY.put("'", 39);
      VALUES_BY_KEY.put("/", 47);
      VALUES_BY_KEY.put("-", 45);
      VALUES_BY_KEY.put("+", 61);
      VALUES_BY_KEY.put("BACK", 259);
      VALUES_BY_KEY.put("BACKSLASH", 92);
      VALUES_BY_KEY.put(".", 46);
      VALUES_BY_KEY.put("COMMA", 44);
      VALUES_BY_KEY.put("PAUSE", 284);
   }

   private static void invoke2() {
      for (Entry entry : VALUES_BY_KEY.entrySet()) {
         VALUES_BY_KEY_2.put((Integer)entry.getValue(), (String)entry.getKey());
      }
   }

   static {
      invoke();
      invoke2();
   }
}
