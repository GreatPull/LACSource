package net.minecraft.entity.player.Really.Client.utils;

public class Timer {
   private static long previousTime;

   public Timer() {
      previousTime = -1L;
   }

   public boolean check(float milliseconds) {
      return (float)this.getTime() >= milliseconds;
   }

   public long getTime() {
      return getCurrentTime() - previousTime;
   }

   public static void reset() {
      previousTime = getCurrentTime();
   }

   public static long getCurrentTime() {
      return System.currentTimeMillis();
   }

   public static boolean delay(int i) {
      return false;
   }
}
