package net.minecraft.entity.player.Really.Client.api.events.world;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.Event;
import net.minecraft.util.MovementInput;

public class EventMove extends Event {
   public static double x;
   public static double y;
   public static double z;
   private double motionX;
   private double motionY;
   private double motionZ;

   public EventMove(double x, double y, double z) {
      x = x;
      y = y;
      z = z;
      this.motionX = x;
      this.motionY = y;
      this.motionZ = z;
   }

   public double getX() {
      return x;
   }

   public static void setX(double x) {
      x = x;
   }

   public double getY() {
      return y;
   }

   public static void setY(double y) {
      y = y;
   }

   public double getZ() {
      return z;
   }

   public static void setZ(double z) {
      z = z;
   }

   public static void setMoveSpeed(double speed) {
      MovementInput var10000 = Minecraft.thePlayer.movementInput;
      double forward = (double)MovementInput.moveForward;
      var10000 = Minecraft.thePlayer.movementInput;
      double strafe = (double)MovementInput.moveStrafe;
      Minecraft.getMinecraft();
      float yaw = Minecraft.thePlayer.rotationYaw;
      if(forward == 0.0D && strafe == 0.0D) {
         setX(0.0D);
         setZ(0.0D);
      } else {
         if(forward != 0.0D) {
            if(strafe > 0.0D) {
               yaw += (float)(forward > 0.0D?-45:45);
            } else if(strafe < 0.0D) {
               yaw += (float)(forward > 0.0D?45:-45);
            }

            strafe = 0.0D;
            forward = forward > 0.0D?1.0D:-1.0D;
         }

         setX(forward * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F))) + strafe * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F))));
         setZ(forward * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F))) - strafe * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F))));
      }

   }
}
