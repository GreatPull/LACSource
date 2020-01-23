package net.minecraft.entity.player.Really.Client.api.events.world;

import net.minecraft.entity.player.Really.Client.api.Event;
import net.minecraft.network.Packet;

public class EventPacketRecieve extends Event {
   private Packet packet;

   public EventPacketRecieve(Packet packet) {
      this.packet = packet;
   }

   public Packet getPacket() {
      return this.packet;
   }

   public void setPacket(Packet packet) {
      this.packet = packet;
   }
}
