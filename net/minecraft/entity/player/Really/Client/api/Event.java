package net.minecraft.entity.player.Really.Client.api;

public abstract class Event {
   private boolean cancelled;
   private boolean skipFutureCalls;
   public static byte type;

   public boolean isCancelled() {
      return this.cancelled;
   }

   public void setCancelled(boolean cancelled) {
      this.cancelled = cancelled;
   }

   public boolean skippingFutureCalls() {
      return this.skipFutureCalls;
   }

   public void skipFutureCalls(boolean skipFutureCalls) {
      this.skipFutureCalls = skipFutureCalls;
   }

   public static byte getType() {
      return type;
   }

   public void setType(byte type) {
      type = type;
   }
}
