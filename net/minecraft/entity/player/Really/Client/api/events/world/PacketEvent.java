package net.minecraft.entity.player.Really.Client.api.events.world;

import net.minecraft.entity.player.Really.Client.api.CancelableEvent;
import net.minecraft.network.Packet;

public class PacketEvent extends CancelableEvent {
   private boolean sending;
   private Packet packet;

   public PacketEvent(Packet packet, boolean sending) {
      this.packet = packet;
      this.sending = sending;
   }

   public Packet getPacket() {
      return this.packet;
   }

   public boolean isSending() {
      return this.sending;
   }

   public void setPacket(Packet packet) {
      this.packet = packet;
   }
}
