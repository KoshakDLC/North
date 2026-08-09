package dev.redstones.mediaplayerinfo.impl.win;

import dev.redstones.mediaplayerinfo.IMediaSession;
import dev.redstones.mediaplayerinfo.MediaInfo;

public final class WindowsMediaSession implements IMediaSession {
   private final MediaInfo media;
   private final String owner;
   private final int index;
   private static volatile int cycle = -1;

   public WindowsMediaSession(MediaInfo mediaInfo, String string, int i) {
      this.media = mediaInfo;
      this.owner = string == null ? "" : string;
      this.index = i;
   }

   @Override
   public MediaInfo getMedia() {
      return this.media;
   }

   @Override
   public String getOwner() {
      return this.owner;
   }

   public int getIndex() {
      return this.index;
   }

   @Override
   public void play() {
   }

   @Override
   public void pause() {
   }

   @Override
   public void playPause() {
   }

   @Override
   public void stop() {
   }

   @Override
   public void next() {
   }

   @Override
   public void previous() {
   }

   @Override
   public void swapCycle() {
      cycle = cycle >= 2 ? 0 : cycle + 1;
   }

   @Override
   public int getCycleType() {
      return cycle;
   }

   public static int getCycle() {
      return cycle;
   }

   public static void setCycle(int i) {
      cycle = i;
   }
}
