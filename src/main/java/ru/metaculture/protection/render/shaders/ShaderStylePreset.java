package ru.metaculture.protection;

import java.util.concurrent.ThreadLocalRandom;

public final class ShaderStylePreset {
   private static final String[] VELVET = new String[]{
      "Velvet",
      "Aurora",
      "Magnetic",
      "Prismatic",
      "Silent",
      "Crystal",
      "Solar",
      "Lunar",
      "Holographic",
      "Obsidian",
      "Radiant",
      "Neon",
      "Frosted",
      "Kinetic",
      "Vivid",
      "Phantom"
   };
   private static final String[] GLASS = new String[]{
      "Glass", "Halo", "Mica", "Pulse", "Mist", "Bloom", "Signal", "Ribbon", "Veil", "Plate", "Glow", "Drift", "Shell", "Field", "Aura", "Prism"
   };

   private ShaderStylePreset() {
   }

   public static String resolve() {
      ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
      return VELVET[threadLocalRandom.nextInt(VELVET.length)] + " " + GLASS[threadLocalRandom.nextInt(GLASS.length)];
   }
}
