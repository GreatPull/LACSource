package net.minecraft.entity.player.Really.Client.module.modules.movement;

public class SpeedTimer {
   private long time = System.nanoTime() / 1000000L;
   private long prevMS = 0L;

   public boolean delay(float milliSec) {
      return (float)(this.getTime() - this.prevMS) >= milliSec;
   }

   public long getTime() {
      return System.nanoTime() / 1000000L;
   }

   public boolean hasTimeElapsed(long time, boolean reset) {
      if(this.time() >= time) {
         if(reset) {
            this.reset();
         }

         return true;
      } else {
         return false;
      }
   }

   public boolean hasTimeElapsed(long time) {
      return this.time() >= time;
   }

   public boolean hasTicksElapsed(int ticks) {
      return this.time() >= (long)(1000 / ticks - 50);
   }

   public boolean hasTicksElapsed(int time, int ticks) {
      return this.time() >= (long)(time / ticks - 50);
   }

   public long time() {
      return System.nanoTime() / 1000000L - this.time;
   }

   public void resetsig() {
      this.prevMS = this.getTime();
   }

   public void reset() {
      this.time = System.nanoTime() / 1000000L;
   }
}
