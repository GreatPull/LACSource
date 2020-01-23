package net.minecraft.entity.player.Really.Client.utils;

public class TimeHelper {
   private long lastMs;

   public boolean isDelayComplete(long delay) {
      return System.currentTimeMillis() - this.lastMs > delay;
   }

   public void reset() {
      this.lastMs = System.currentTimeMillis();
   }

   public long getLastMs() {
      return this.lastMs;
   }

   public void setLastMs(int i) {
      this.lastMs = System.currentTimeMillis() + (long)i;
   }
}
