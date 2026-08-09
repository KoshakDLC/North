package ru.metaculture.protection;

import java.util.ArrayDeque;

public final class ShaderEditHistory {
   private static final int INT_VALUE = 50;
   private final ArrayDeque<String> arrayDeque = new ArrayDeque<>();
   private final ArrayDeque<String> arrayDeque2 = new ArrayDeque<>();

   public void invoke(String string) {
      if (string != null && !string.equals(this.arrayDeque.peekLast())) {
         this.arrayDeque.addLast(string);

         while (this.arrayDeque.size() > 50) {
            this.arrayDeque.pollFirst();
         }

         this.arrayDeque2.clear();
      }
   }

   public String resolve(String string) {
      if (string == null) {
         return null;
      } else {
         while (!this.arrayDeque.isEmpty() && string.equals(this.arrayDeque.peekLast())) {
            this.arrayDeque.pollLast();
         }

         if (this.arrayDeque.isEmpty()) {
            return null;
         } else {
            this.arrayDeque2.addLast(string);
            return this.arrayDeque.pollLast();
         }
      }
   }

   public String resolve2(String string) {
      if (string == null) {
         return null;
      } else {
         while (!this.arrayDeque2.isEmpty() && string.equals(this.arrayDeque2.peekLast())) {
            this.arrayDeque2.pollLast();
         }

         if (this.arrayDeque2.isEmpty()) {
            return null;
         } else {
            this.arrayDeque.addLast(string);
            return this.arrayDeque2.pollLast();
         }
      }
   }

   public boolean check() {
      return !this.arrayDeque.isEmpty();
   }

   public boolean check2() {
      return !this.arrayDeque2.isEmpty();
   }

   public void invoke2() {
      this.arrayDeque.clear();
      this.arrayDeque2.clear();
   }
}
