package net.minecraft.entity.player.Really.Client.api.events.world;

import net.minecraft.entity.player.Really.Client.api.Event;
import net.minecraft.network.Packet;

public class EventPacketSend extends Event {
   private static Packet packet;

   public EventPacketSend(Packet packet) {
      packet = packet;
   }

   public static Packet getPacket() {
      return packet;
   }

   public void setPacket(Packet packet) {
      packet = packet;
   }
}
