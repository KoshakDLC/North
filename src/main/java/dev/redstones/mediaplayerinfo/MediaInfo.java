package dev.redstones.mediaplayerinfo;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;
import javax.imageio.ImageIO;

public final class MediaInfo implements Serializable {
   private final String title;
   private final String artist;
   private final byte[] artworkPng;
   private final long position;
   private final long duration;
   private final boolean playing;

   public MediaInfo(String string, String string2, byte[] bs, long l, long m, boolean bl) {
      this.title = string == null ? "" : string;
      this.artist = string2 == null ? "" : string2;
      this.artworkPng = bs == null ? new byte[0] : Arrays.copyOf(bs, bs.length);
      this.position = Math.max(0L, l);
      this.duration = Math.max(0L, m);
      this.playing = bl;
   }

   public String getTitle() {
      return this.title;
   }

   public String getArtist() {
      return this.artist;
   }

   public byte[] getArtworkPng() {
      return Arrays.copyOf(this.artworkPng, this.artworkPng.length);
   }

   public long getPosition() {
      return this.position;
   }

   public long getDuration() {
      return this.duration;
   }

   public boolean isPlaying() {
      return this.playing;
   }

   public boolean getPlaying() {
      return this.playing;
   }

   public BufferedImage getArtwork() {
      if (this.artworkPng.length == 0) {
         return null;
      } else {
         try {
            return ImageIO.read(new ByteArrayInputStream(this.artworkPng));
         } catch (Exception exception) {
            return null;
         }
      }
   }

   @Override
   public boolean equals(Object object) {
      if (this == object) {
         return true;
      } else {
         return !(object instanceof MediaInfo mediaInfo)
            ? false
            : this.position == mediaInfo.position
               && this.duration == mediaInfo.duration
               && this.playing == mediaInfo.playing
               && Objects.equals(this.title, mediaInfo.title)
               && Objects.equals(this.artist, mediaInfo.artist)
               && Arrays.equals(this.artworkPng, mediaInfo.artworkPng);
      }
   }

   @Override
   public int hashCode() {
      int intValue = Objects.hash(this.title, this.artist, this.position, this.duration, this.playing);
      return 31 * intValue + Arrays.hashCode(this.artworkPng);
   }

   @Override
   public String toString() {
      return "MediaInfo{title='"
         + this.title
         + "', artist='"
         + this.artist
         + "', position="
         + this.position
         + ", duration="
         + this.duration
         + ", playing="
         + this.playing
         + "}";
   }
}
