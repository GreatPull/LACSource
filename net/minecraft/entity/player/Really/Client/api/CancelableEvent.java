package net.minecraft.entity.player.Really.Client.api;

import net.minecraft.entity.player.Really.Client.api.Event;

public class CancelableEvent extends Event {
   private boolean canceled;

   public boolean isCanceled() {
      return this.canceled;
   }

   public void setCanceled(boolean canceled) {
      this.canceled = canceled;
   }
}
