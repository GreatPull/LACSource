package net.minecraft.entity.player.Really.Client;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MovementInput;

public class HookMove {
   private final Minecraft mc = Minecraft.getMinecraft();
   private double x;
   private double y;
   private double z;

   public HookMove(double x, double y, double z) {
      this.x = x;
      this.y = y;
      this.z = z;
   }

   public double getX() {
      return this.x;
   }

   public void setX(double x) {
      this.x = x;
   }

   public double getY() {
      return this.y;
   }

   public void setY(double y) {
      this.y = y;
   }

   public double getZ() {
      return this.z;
   }

   public void setZ(double z) {
      this.z = z;
   }

   public void setMoveSpeed(double speed) {
      MovementInput var10000 = Minecraft.thePlayer.movementInput;
      double forward = (double)MovementInput.moveForward;
      var10000 = Minecraft.thePlayer.movementInput;
      double strafe = (double)MovementInput.moveStrafe;
      Minecraft.getMinecraft();
      float yaw = Minecraft.thePlayer.rotationYaw;
      if(forward == 0.0D && strafe == 0.0D) {
         this.setX(0.0D);
         this.setZ(0.0D);
      } else {
         if(forward != 0.0D) {
            if(strafe > 0.0D) {
               yaw += (float)(forward > 0.0D?-45:45);
            } else if(strafe < 0.0D) {
               yaw += (float)(forward > 0.0D?45:-45);
            }

            strafe = 0.0D;
            if(forward > 0.0D) {
               forward = 1.0D;
            } else {
               forward = -1.0D;
            }
         }

         this.setX(forward * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F))) + strafe * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F))));
         this.setZ(forward * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F))) - strafe * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F))));
      }

   }
}
