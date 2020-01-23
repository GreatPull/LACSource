package net.minecraft.entity.player.Really.Client.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;

public class MoveUtils {
   private static Minecraft mc = Minecraft.getMinecraft();

   public static double defaultSpeed() {
      double baseSpeed = 0.2873D;
      Minecraft.getMinecraft();
      if(Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
         Minecraft.getMinecraft();
         int amplifier = Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
         baseSpeed *= 1.0D + 0.2D * (double)(amplifier + 1);
      }

      return baseSpeed;
   }

   public static void strafe(double speed) {
      float a = Minecraft.thePlayer.rotationYaw * 0.017453292F;
      float l = Minecraft.thePlayer.rotationYaw * 0.017453292F - 4.712389F;
      float r = Minecraft.thePlayer.rotationYaw * 0.017453292F + 4.712389F;
      float rf = Minecraft.thePlayer.rotationYaw * 0.017453292F + 0.5969026F;
      float lf = Minecraft.thePlayer.rotationYaw * 0.017453292F + -0.5969026F;
      float lb = Minecraft.thePlayer.rotationYaw * 0.017453292F - 2.3876104F;
      float rb = Minecraft.thePlayer.rotationYaw * 0.017453292F - -2.3876104F;
      if(Minecraft.gameSettings.keyBindForward.pressed) {
         if(Minecraft.gameSettings.keyBindLeft.pressed && !Minecraft.gameSettings.keyBindRight.pressed) {
            Minecraft.thePlayer.motionX -= (double)MathHelper.sin(lf) * speed;
            Minecraft.thePlayer.motionZ += (double)MathHelper.cos(lf) * speed;
         } else if(Minecraft.gameSettings.keyBindRight.pressed && !Minecraft.gameSettings.keyBindLeft.pressed) {
            Minecraft.thePlayer.motionX -= (double)MathHelper.sin(rf) * speed;
            Minecraft.thePlayer.motionZ += (double)MathHelper.cos(rf) * speed;
         } else {
            Minecraft.thePlayer.motionX -= (double)MathHelper.sin(a) * speed;
            Minecraft.thePlayer.motionZ += (double)MathHelper.cos(a) * speed;
         }
      } else if(Minecraft.gameSettings.keyBindBack.pressed) {
         if(Minecraft.gameSettings.keyBindLeft.pressed && !Minecraft.gameSettings.keyBindRight.pressed) {
            Minecraft.thePlayer.motionX -= (double)MathHelper.sin(lb) * speed;
            Minecraft.thePlayer.motionZ += (double)MathHelper.cos(lb) * speed;
         } else if(Minecraft.gameSettings.keyBindRight.pressed && !Minecraft.gameSettings.keyBindLeft.pressed) {
            Minecraft.thePlayer.motionX -= (double)MathHelper.sin(rb) * speed;
            Minecraft.thePlayer.motionZ += (double)MathHelper.cos(rb) * speed;
         } else {
            Minecraft.thePlayer.motionX += (double)MathHelper.sin(a) * speed;
            Minecraft.thePlayer.motionZ -= (double)MathHelper.cos(a) * speed;
         }
      } else if(Minecraft.gameSettings.keyBindLeft.pressed && !Minecraft.gameSettings.keyBindRight.pressed && !Minecraft.gameSettings.keyBindForward.pressed && !Minecraft.gameSettings.keyBindBack.pressed) {
         Minecraft.thePlayer.motionX += (double)MathHelper.sin(l) * speed;
         Minecraft.thePlayer.motionZ -= (double)MathHelper.cos(l) * speed;
      } else if(Minecraft.gameSettings.keyBindRight.pressed && !Minecraft.gameSettings.keyBindLeft.pressed && !Minecraft.gameSettings.keyBindForward.pressed && !Minecraft.gameSettings.keyBindBack.pressed) {
         Minecraft.thePlayer.motionX += (double)MathHelper.sin(r) * speed;
         Minecraft.thePlayer.motionZ -= (double)MathHelper.cos(r) * speed;
      }

   }

   public static void setMotion(double speed) {
      MovementInput var10000 = Minecraft.thePlayer.movementInput;
      double forward = (double)MovementInput.moveForward;
      var10000 = Minecraft.thePlayer.movementInput;
      double strafe = (double)MovementInput.moveStrafe;
      float yaw = Minecraft.thePlayer.rotationYaw;
      if(forward == 0.0D && strafe == 0.0D) {
         Minecraft.thePlayer.motionX = 0.0D;
         Minecraft.thePlayer.motionZ = 0.0D;
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
            } else if(forward < 0.0D) {
               forward = -1.0D;
            }
         }

         Minecraft.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F))) + strafe * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F)));
         Minecraft.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F))) - strafe * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F)));
      }

   }

   public static boolean checkTeleport(double x, double y, double z, double distBetweenPackets) {
      double var10000 = Minecraft.thePlayer.posX - x;
      var10000 = Minecraft.thePlayer.posY - y;
      var10000 = Minecraft.thePlayer.posZ - z;
      double dist = Math.sqrt(Minecraft.thePlayer.getDistanceSq(x, y, z));
      double nbPackets = (double)(Math.round(dist / distBetweenPackets + 0.49999999999D) - 1L);
      double xtp = Minecraft.thePlayer.posX;
      double ytp = Minecraft.thePlayer.posY;
      double ztp = Minecraft.thePlayer.posZ;

      for(int i = 1; (double)i < nbPackets; ++i) {
         double xdi = (x - Minecraft.thePlayer.posX) / nbPackets;
         xtp += xdi;
         double zdi = (z - Minecraft.thePlayer.posZ) / nbPackets;
         ztp += zdi;
         double ydi = (y - Minecraft.thePlayer.posY) / nbPackets;
         ytp += ydi;
         AxisAlignedBB bb = new AxisAlignedBB(xtp - 0.3D, ytp, ztp - 0.3D, xtp + 0.3D, ytp + 1.8D, ztp + 0.3D);
         if(!Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, bb).isEmpty()) {
            return false;
         }
      }

      return true;
   }

   public static boolean isOnGround(double height) {
      return !Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, Minecraft.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty();
   }

   public static int getJumpEffect() {
      return Minecraft.thePlayer.isPotionActive(Potion.jump)?Minecraft.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1:0;
   }

   public static int getSpeedEffect() {
      return Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)?Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1:0;
   }

   public static Block getBlockUnderPlayer(EntityPlayer inPlayer, double height) {
      Minecraft.getMinecraft();
      return Minecraft.theWorld.getBlockState(new BlockPos(inPlayer.posX, inPlayer.posY - height, inPlayer.posZ)).getBlock();
   }

   public static Block getBlockAtPosC(double x, double y, double z) {
      Minecraft.getMinecraft();
      EntityPlayer inPlayer = Minecraft.thePlayer;
      Minecraft.getMinecraft();
      return Minecraft.theWorld.getBlockState(new BlockPos(inPlayer.posX + x, inPlayer.posY + y, inPlayer.posZ + z)).getBlock();
   }

   public static float getDistanceToGround(Entity e) {
      if(Minecraft.thePlayer.isCollidedVertically && Minecraft.thePlayer.onGround) {
         return 0.0F;
      } else {
         for(float a = (float)e.posY; a > 0.0F; --a) {
            int[] stairs = new int[]{53, 67, 108, 109, 114, 128, 134, 135, 136, 156, 163, 164, 180};
            int[] exemptIds = new int[]{6, 27, 28, 30, 31, 32, 37, 38, 39, 40, 50, 51, 55, 59, 63, 65, 66, 68, 69, 70, 72, 75, 76, 77, 83, 92, 93, 94, 104, 105, 106, 115, 119, 131, 132, 143, 147, 148, 149, 150, 157, 171, 175, 176, 177};
            Block block = Minecraft.theWorld.getBlockState(new BlockPos(e.posX, (double)(a - 1.0F), e.posZ)).getBlock();
            if(!(block instanceof BlockAir)) {
               if(Block.getIdFromBlock(block) != 44 && Block.getIdFromBlock(block) != 126) {
                  for(int id : stairs) {
                     if(Block.getIdFromBlock(block) == id) {
                        return (float)(e.posY - (double)a - 1.0D) < 0.0F?0.0F:(float)(e.posY - (double)a - 1.0D);
                     }
                  }

                  for(int id : exemptIds) {
                     if(Block.getIdFromBlock(block) == id) {
                        return (float)(e.posY - (double)a) < 0.0F?0.0F:(float)(e.posY - (double)a);
                     }
                  }

                  return (float)(e.posY - (double)a + block.getBlockBoundsMaxY() - 1.0D);
               }

               return (float)(e.posY - (double)a - 0.5D) < 0.0F?0.0F:(float)(e.posY - (double)a - 0.5D);
            }
         }

         return 0.0F;
      }
   }

   public static float[] getRotationsBlock(BlockPos block, EnumFacing face) {
      double x = (double)block.getX() + 0.5D - Minecraft.thePlayer.posX + (double)face.getFrontOffsetX() / 2.0D;
      double z = (double)block.getZ() + 0.5D - Minecraft.thePlayer.posZ + (double)face.getFrontOffsetZ() / 2.0D;
      double y = (double)block.getY() + 0.5D;
      double d1 = Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight() - y;
      double d3 = (double)MathHelper.sqrt_double(x * x + z * z);
      float yaw = (float)(Math.atan2(z, x) * 180.0D / 3.141592653589793D) - 90.0F;
      float pitch = (float)(Math.atan2(d1, d3) * 180.0D / 3.141592653589793D);
      if(yaw < 0.0F) {
         yaw += 360.0F;
      }

      return new float[]{yaw, pitch};
   }

   public static boolean isBlockAboveHead() {
      AxisAlignedBB bb = new AxisAlignedBB(Minecraft.thePlayer.posX - 0.3D, Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight(), Minecraft.thePlayer.posZ + 0.3D, Minecraft.thePlayer.posX + 0.3D, Minecraft.thePlayer.posY + 2.5D, Minecraft.thePlayer.posZ - 0.3D);
      return !Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, bb).isEmpty();
   }

   public static boolean isCollidedH(double dist) {
      AxisAlignedBB bb = new AxisAlignedBB(Minecraft.thePlayer.posX - 0.3D, Minecraft.thePlayer.posY + 2.0D, Minecraft.thePlayer.posZ + 0.3D, Minecraft.thePlayer.posX + 0.3D, Minecraft.thePlayer.posY + 3.0D, Minecraft.thePlayer.posZ - 0.3D);
      return !Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, bb.offset(0.3D + dist, 0.0D, 0.0D)).isEmpty()?true:(!Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, bb.offset(-0.3D - dist, 0.0D, 0.0D)).isEmpty()?true:(!Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, bb.offset(0.0D, 0.0D, 0.3D + dist)).isEmpty()?true:!Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, bb.offset(0.0D, 0.0D, -0.3D - dist)).isEmpty()));
   }

   public static boolean isRealCollidedH(double dist) {
      AxisAlignedBB bb = new AxisAlignedBB(Minecraft.thePlayer.posX - 0.3D, Minecraft.thePlayer.posY + 0.5D, Minecraft.thePlayer.posZ + 0.3D, Minecraft.thePlayer.posX + 0.3D, Minecraft.thePlayer.posY + 1.9D, Minecraft.thePlayer.posZ - 0.3D);
      return !Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, bb.offset(0.3D + dist, 0.0D, 0.0D)).isEmpty()?true:(!Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, bb.offset(-0.3D - dist, 0.0D, 0.0D)).isEmpty()?true:(!Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, bb.offset(0.0D, 0.0D, 0.3D + dist)).isEmpty()?true:!Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, bb.offset(0.0D, 0.0D, -0.3D - dist)).isEmpty()));
   }
}
