package net.minecraft.entity.player.Really.Client.module.modules.combat;

public class Timer {
   private long previousTime = -1L;

   public long getDifference() {
      return this.getTime() - this.previousTime;
   }

   public boolean check(float milliseconds) {
      return (float)this.getTime() >= milliseconds;
   }

   public long getTime() {
      return this.getCurrentTime() - this.previousTime;
   }

   public void reset() {
      this.previousTime = this.getCurrentTime();
   }

   public long getCurrentTime() {
      return System.currentTimeMillis();
   }
}
