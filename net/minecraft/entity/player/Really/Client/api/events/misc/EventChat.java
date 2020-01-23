package net.minecraft.entity.player.Really.Client.api.events.misc;

import net.minecraft.entity.player.Really.Client.api.Event;

public class EventChat extends Event {
   private String message;

   public EventChat(String message) {
      this.message = message;
      this.setType((byte)0);
   }

   public String getMessage() {
      return this.message;
   }

   public void setMessage(String message) {
      this.message = message;
   }
}
