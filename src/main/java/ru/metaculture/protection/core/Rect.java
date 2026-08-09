package ru.metaculture.protection;

public record Rect(float x, float y, float w, float h) {
   public boolean contains(float f, float g) {
      return f >= this.x && g >= this.y && f < this.x + this.w && g < this.y + this.h;
   }
}
