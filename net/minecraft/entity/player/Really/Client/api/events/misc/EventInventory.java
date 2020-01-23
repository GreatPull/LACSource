package net.minecraft.entity.player.Really.Client.api.events.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.Really.Client.api.Event;

public class EventInventory extends Event {
   private final EntityPlayer player;

   public EventInventory(EntityPlayer player) {
      this.player = player;
   }

   public EntityPlayer getPlayer() {
      return this.player;
   }
}
