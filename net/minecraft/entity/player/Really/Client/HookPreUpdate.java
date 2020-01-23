package net.minecraft.entity.player.Really.Client;

import java.util.Random;

public class HookPreUpdate {
   private static final Random random = new Random();
   public float yaw;
   public float pitch;

   public HookPreUpdate(float yaw, float pitch) {
      this.yaw = yaw;
      this.pitch = pitch;
   }

   public float getYaw() {
      return this.yaw;
   }

   public float getPitch() {
      return this.pitch;
   }

   public void setPitch(float pitch) {
      this.pitch = pitch;
   }

   public void setYaw(float yaw) {
      this.yaw = yaw;
   }

   public void setRotations(float[] rotations, boolean random) {
      if(random) {
         this.yaw = rotations[0] + (float)(random?Math.random():-Math.random());
         this.pitch = rotations[1] + (float)(random?Math.random():-Math.random());
      } else {
         this.yaw = rotations[0];
         this.pitch = rotations[1];
      }

   }
}
