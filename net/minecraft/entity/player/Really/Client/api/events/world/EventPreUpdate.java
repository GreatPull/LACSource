package net.minecraft.entity.player.Really.Client.api.events.world;

import net.minecraft.entity.player.Really.Client.api.Event;
import net.minecraft.network.play.client.C03PacketPlayer;

public class EventPreUpdate extends Event {
   public static final C03PacketPlayer packet = null;
   public float yaw;
   public float pitch;
   public double y;
   private boolean ground;

   public EventPreUpdate(float yaw, float pitch, double y2, boolean ground) {
      this.yaw = yaw;
      this.pitch = pitch;
      this.y = y2;
      this.ground = ground;
   }

   public float getYaw() {
      return this.yaw;
   }

   public void setYaw(float yaw) {
      this.yaw = yaw;
   }

   public float getPitch() {
      return this.pitch;
   }

   public void setPitch(float pitch) {
      this.pitch = pitch;
   }

   public double getY() {
      return this.y;
   }

   public void setY(double y2) {
      this.y = y2;
   }

   public boolean isOnground() {
      return this.ground;
   }

   public void setOnground(boolean ground) {
      this.ground = ground;
   }

   public C03PacketPlayer getPacket() {
      return null;
   }
}
