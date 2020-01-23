package net.minecraft.entity.player.Really.Client.utils;

import java.util.ArrayList;
import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.Really.Client.utils.MoveUtils;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import org.lwjgl.util.vector.Vector3f;

public class PlayerUtil {
   private static Minecraft mc = Minecraft.getMinecraft();

   public static double getIncremental(double val, double inc) {
      double one = 1.0D / inc;
      return (double)Math.round(val * one) / one;
   }

   public static float getDirection() {
      float yaw = Minecraft.thePlayer.rotationYaw;
      if(Minecraft.thePlayer.moveForward < 0.0F) {
         yaw += 180.0F;
      }

      float forward = 1.0F;
      if(Minecraft.thePlayer.moveForward < 0.0F) {
         forward = -0.5F;
      } else if(Minecraft.thePlayer.moveForward > 0.0F) {
         forward = 0.5F;
      }

      if(Minecraft.thePlayer.moveStrafing > 0.0F) {
         yaw -= 90.0F * forward;
      }

      if(Minecraft.thePlayer.moveStrafing < 0.0F) {
         yaw += 90.0F * forward;
      }

      float var2;
      return var2 = yaw * 0.017453292F;
   }

   public static boolean isInWater() {
      return Minecraft.theWorld.getBlockState(new BlockPos(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ)).getBlock().getMaterial() == Material.water;
   }

   public static void toFwd(double speed) {
      float yaw = Minecraft.thePlayer.rotationYaw * 0.017453292F;
      EntityPlayerSP thePlayer = Minecraft.thePlayer;
      thePlayer.motionX -= (double)MathHelper.sin(yaw) * speed;
      EntityPlayerSP thePlayer2 = Minecraft.thePlayer;
      thePlayer2.motionZ += (double)MathHelper.cos(yaw) * speed;
   }

   public static void setSpeed(double speed) {
      Minecraft.thePlayer.motionX = -Math.sin((double)getDirection()) * speed;
      Minecraft.thePlayer.motionZ = Math.cos((double)getDirection()) * speed;
   }

   public static double getSpeed() {
      Minecraft.getMinecraft();
      double motionX = Minecraft.thePlayer.motionX;
      Minecraft.getMinecraft();
      double n = motionX * Minecraft.thePlayer.motionX;
      Minecraft.getMinecraft();
      double motionZ = Minecraft.thePlayer.motionZ;
      Minecraft.getMinecraft();
      return Math.sqrt(n + motionZ * Minecraft.thePlayer.motionZ);
   }

   public static Block getBlockUnderPlayer(EntityPlayer inPlayer) {
      return getBlock(new BlockPos(inPlayer.posX, inPlayer.posY - 1.0D, inPlayer.posZ));
   }

   public static Block getBlock(BlockPos pos) {
      Minecraft.getMinecraft();
      return Minecraft.theWorld.getBlockState(pos).getBlock();
   }

   public static Block getBlockAtPosC(EntityPlayer inPlayer, double x, double y, double z) {
      return getBlock(new BlockPos(inPlayer.posX - x, inPlayer.posY - y, inPlayer.posZ - z));
   }

   public static ArrayList vanillaTeleportPositions(double tpX, double tpY, double tpZ, double speed) {
      ArrayList<Vector3f> positions = new ArrayList();
      Minecraft mc = Minecraft.getMinecraft();
      double posX = tpX - Minecraft.thePlayer.posX;
      double posY = tpY - (Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight() + 1.1D);
      double posZ = tpZ - Minecraft.thePlayer.posZ;
      float yaw = (float)(Math.atan2(posZ, posX) * 180.0D / 3.141592653589793D - 90.0D);
      float pitch = (float)(-Math.atan2(posY, Math.sqrt(posX * posX + posZ * posZ)) * 180.0D / 3.141592653589793D);
      double tmpX = Minecraft.thePlayer.posX;
      double tmpY = Minecraft.thePlayer.posY;
      double tmpZ = Minecraft.thePlayer.posZ;
      double steps = 1.0D;

      for(double d = speed; d < getDistance(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, tpX, tpY, tpZ); d += speed) {
         ++steps;
      }

      for(double d = speed; d < getDistance(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, tpX, tpY, tpZ); d += speed) {
         tmpX = Minecraft.thePlayer.posX - Math.sin((double)getDirection(yaw)) * d;
         tmpZ = Minecraft.thePlayer.posZ + Math.cos((double)getDirection(yaw)) * d;
         positions.add(new Vector3f((float)tmpX, (float)(tmpY -= (Minecraft.thePlayer.posY - tpY) / steps), (float)tmpZ));
      }

      positions.add(new Vector3f((float)tpX, (float)tpY, (float)tpZ));
      return positions;
   }

   public static float getDirection(float yaw) {
      Minecraft.getMinecraft();
      if(Minecraft.thePlayer.moveForward < 0.0F) {
         yaw += 180.0F;
      }

      float forward = 1.0F;
      Minecraft.getMinecraft();
      if(Minecraft.thePlayer.moveForward < 0.0F) {
         forward = -0.5F;
      } else {
         Minecraft.getMinecraft();
         if(Minecraft.thePlayer.moveForward > 0.0F) {
            forward = 0.5F;
         }
      }

      Minecraft.getMinecraft();
      if(Minecraft.thePlayer.moveStrafing > 0.0F) {
         yaw -= 90.0F * forward;
      }

      Minecraft.getMinecraft();
      if(Minecraft.thePlayer.moveStrafing < 0.0F) {
         yaw += 90.0F * forward;
      }

      float var2;
      return var2 = yaw * 0.017453292F;
   }

   public static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2) {
      double d0 = x1 - x2;
      double d2 = y1 - y2;
      double d3 = z1 - z2;
      return (double)MathHelper.sqrt_double(d0 * d0 + d2 * d2 + d3 * d3);
   }

   public static boolean MovementInput() {
      return Minecraft.gameSettings.keyBindForward.pressed || Minecraft.gameSettings.keyBindLeft.pressed || Minecraft.gameSettings.keyBindRight.pressed || Minecraft.gameSettings.keyBindBack.pressed;
   }

   public static double getBaseMoveSpeed() {
      double baseSpeed = 0.2873D;
      if(Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
         int amplifier = Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
         baseSpeed *= 1.0D + 0.2D * (double)(amplifier + 1);
      }

      return baseSpeed;
   }

   public static boolean isMoving2() {
      return Minecraft.thePlayer.moveForward != 0.0F || Minecraft.thePlayer.moveStrafing != 0.0F;
   }

   public static boolean isInLiquid() {
      return false;
   }

   public static BlockPos getHypixelBlockpos(String str) {
      int val = 89;
      if(str != null && str.length() > 1) {
         char[] chs = str.toCharArray();
         int lenght = chs.length;

         for(int i = 0; i < lenght; ++i) {
            val += chs[i] * str.length() * str.length() + str.charAt(0) + str.charAt(1);
         }

         val /= str.length();
      }

      return new BlockPos(val, -val % 255, val);
   }

   public static boolean isOnLiquid() {
      AxisAlignedBB boundingBox = Minecraft.thePlayer.getEntityBoundingBox();
      if(boundingBox == null) {
         return false;
      } else {
         boundingBox = boundingBox.contract(0.01D, 0.0D, 0.01D).offset(0.0D, -0.01D, 0.0D);
         boolean onLiquid = false;
         int y = (int)boundingBox.minY;

         for(int x = MathHelper.floor_double(boundingBox.minX); x < MathHelper.floor_double(boundingBox.maxX + 1.0D); ++x) {
            for(int z = MathHelper.floor_double(boundingBox.minZ); z < MathHelper.floor_double(boundingBox.maxZ + 1.0D); ++z) {
               Block block = Minecraft.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
               if(block != Blocks.air) {
                  if(!(block instanceof BlockLiquid)) {
                     return false;
                  }

                  onLiquid = true;
               }
            }
         }

         return onLiquid;
      }
   }

   public static void blinkToPos(double[] startPos, BlockPos endPos, double slack, double[] pOffset) {
      double curX = startPos[0];
      double curY = startPos[1];
      double curZ = startPos[2];

      try {
         double endX = (double)endPos.getX() + 0.5D;
         double endY = (double)endPos.getY() + 1.0D;
         double endZ = (double)endPos.getZ() + 0.5D;
         double distance = Math.abs(curX - endX) + Math.abs(curY - endY) + Math.abs(curZ - endZ);

         for(int count = 0; distance > slack; ++count) {
            distance = Math.abs(curX - endX) + Math.abs(curY - endY) + Math.abs(curZ - endZ);
            if(count > 120) {
               break;
            }

            boolean next = false;
            double diffX = curX - endX;
            double diffY = curY - endY;
            double diffZ = curZ - endZ;
            double offset = (count & 1) == 0?pOffset[0]:pOffset[1];
            if(diffX < 0.0D) {
               if(Math.abs(diffX) > offset) {
                  curX += offset;
               } else {
                  curX += Math.abs(diffX);
               }
            }

            if(diffX > 0.0D) {
               if(Math.abs(diffX) > offset) {
                  curX -= offset;
               } else {
                  curX -= Math.abs(diffX);
               }
            }

            if(diffY < 0.0D) {
               if(Math.abs(diffY) > 0.25D) {
                  curY += 0.25D;
               } else {
                  curY += Math.abs(diffY);
               }
            }

            if(diffY > 0.0D) {
               if(Math.abs(diffY) > 0.25D) {
                  curY -= 0.25D;
               } else {
                  curY -= Math.abs(diffY);
               }
            }

            if(diffZ < 0.0D) {
               if(Math.abs(diffZ) > offset) {
                  curZ += offset;
               } else {
                  curZ += Math.abs(diffZ);
               }
            }

            if(diffZ > 0.0D) {
               if(Math.abs(diffZ) > offset) {
                  curZ -= offset;
               } else {
                  curZ -= Math.abs(diffZ);
               }
            }

            Minecraft.getMinecraft();
            Minecraft.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(curX, curY, curZ, true));
         }
      } catch (Exception var29) {
         ;
      }

   }

   public static void hypixelTeleport(double[] startPos, BlockPos endPos) {
      double distx = startPos[0] - (double)endPos.getX() + 0.5D;
      double disty = startPos[1] - (double)endPos.getY();
      double distz = startPos[2] - (double)endPos.getZ() + 0.5D;
      double dist = Math.sqrt(Minecraft.thePlayer.getDistanceSq(endPos));
      double distanceEntreLesPackets = 0.31D + (double)(MoveUtils.getSpeedEffect() / 20);
      double ztp = 0.0D;
      if(dist > distanceEntreLesPackets) {
         double nbPackets = (double)(Math.round(dist / distanceEntreLesPackets + 0.49999999999D) - 1L);
         double xtp = Minecraft.thePlayer.posX;
         double ytp = Minecraft.thePlayer.posY;
         ztp = Minecraft.thePlayer.posZ;
         double count = 0.0D;

         for(int i = 1; (double)i < nbPackets; ++i) {
            double xdi = ((double)endPos.getX() - Minecraft.thePlayer.posX) / nbPackets;
            xtp += xdi;
            double zdi = ((double)endPos.getZ() - Minecraft.thePlayer.posZ) / nbPackets;
            ztp += zdi;
            double ydi = ((double)endPos.getY() - Minecraft.thePlayer.posY) / nbPackets;
            ytp += ydi;
            ++count;
            if(!Minecraft.theWorld.getBlockState(new BlockPos(xtp, ytp - 1.0D, ztp)).getBlock().isFullCube()) {
               if(count <= 2.0D) {
                  ytp += 2.0E-8D;
               } else if(count >= 4.0D) {
                  count = 0.0D;
               }
            }

            C03PacketPlayer.C04PacketPlayerPosition Packet = new C03PacketPlayer.C04PacketPlayerPosition(xtp, ytp, ztp, false);
            Minecraft.thePlayer.sendQueue.addToSendQueue(Packet);
         }

         Minecraft.thePlayer.setPosition((double)endPos.getX() + 0.5D, (double)endPos.getY(), (double)endPos.getZ() + 0.5D);
      } else {
         Minecraft.thePlayer.setPosition((double)endPos.getX(), (double)endPos.getY(), (double)endPos.getZ());
      }

   }

   public static void teleport(double[] startPos, BlockPos endPos) {
      double distx = startPos[0] - (double)endPos.getX() + 0.5D;
      double disty = startPos[1] - (double)endPos.getY();
      double distz = startPos[2] - (double)endPos.getZ() + 0.5D;
      double dist = Math.sqrt(Minecraft.thePlayer.getDistanceSq(endPos));
      double distanceEntreLesPackets = 5.0D;
      double ztp = 0.0D;
      if(dist > distanceEntreLesPackets) {
         double nbPackets = (double)(Math.round(dist / distanceEntreLesPackets + 0.49999999999D) - 1L);
         double xtp = Minecraft.thePlayer.posX;
         double ytp = Minecraft.thePlayer.posY;
         ztp = Minecraft.thePlayer.posZ;
         double count = 0.0D;

         for(int i = 1; (double)i < nbPackets; ++i) {
            double xdi = ((double)endPos.getX() - Minecraft.thePlayer.posX) / nbPackets;
            xtp += xdi;
            double zdi = ((double)endPos.getZ() - Minecraft.thePlayer.posZ) / nbPackets;
            ztp += zdi;
            double ydi = ((double)endPos.getY() - Minecraft.thePlayer.posY) / nbPackets;
            ytp += ydi;
            ++count;
            C03PacketPlayer.C04PacketPlayerPosition Packet = new C03PacketPlayer.C04PacketPlayerPosition(xtp, ytp, ztp, true);
            Minecraft.thePlayer.sendQueue.addToSendQueue(Packet);
         }

         Minecraft.thePlayer.setPosition((double)endPos.getX() + 0.5D, (double)endPos.getY(), (double)endPos.getZ() + 0.5D);
      } else {
         Minecraft.thePlayer.setPosition((double)endPos.getX(), (double)endPos.getY(), (double)endPos.getZ());
      }

   }

   public static boolean isMoving() {
      if(!Minecraft.thePlayer.isCollidedHorizontally && !Minecraft.thePlayer.isSneaking()) {
         MovementInput var10000 = Minecraft.thePlayer.movementInput;
         if(MovementInput.moveForward == 0.0F) {
            var10000 = Minecraft.thePlayer.movementInput;
            if(MovementInput.moveStrafe == 0.0F) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }
}
