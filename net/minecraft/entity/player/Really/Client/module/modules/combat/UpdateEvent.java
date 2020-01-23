package net.minecraft.entity.player.Really.Client.module.modules.combat;

import net.minecraft.entity.player.Really.Client.api.CancelableEvent;

public class UpdateEvent extends CancelableEvent {
   private boolean onGround;
   private float yaw;
   private float pitch;
   private double y;
   private boolean pre;

   public UpdateEvent(float yaw, float pitch, double y, boolean onGround, boolean pre) {
      this.yaw = yaw;
      this.pitch = pitch;
      this.y = y;
      this.onGround = onGround;
      this.pre = pre;
   }

   public UpdateEvent(float yaw, float pitch, double y, boolean onGround) {
      this.yaw = yaw;
      this.pitch = pitch;
      this.y = y;
      this.onGround = onGround;
   }

   public float getYaw() {
      return this.yaw;
   }

   public float getPitch() {
      return this.pitch;
   }

   public double getY() {
      return this.y;
   }

   public boolean isOnGround() {
      return this.onGround;
   }

   public void setYaw(float yaw) {
      this.yaw = yaw;
   }

   public void setPitch(float pitch) {
      this.pitch = pitch;
   }

   public void setY(double y) {
      this.y = y;
   }

   public void setOnGround(boolean onGround) {
      this.onGround = onGround;
   }

   public boolean isPre() {
      return this.pre;
   }
}
