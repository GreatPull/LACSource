package net.minecraft.entity.player.Really.Client.utils.math;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.Really.Client.utils.Location;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

public class RotationUtil {
   public static float[] LACrotate(EntityLivingBase ent) {
      double x = ent.posX - Minecraft.thePlayer.posX;
      double y = ent.posY - Minecraft.thePlayer.posY;
      double z = ent.posZ - Minecraft.thePlayer.posZ;
      y = y / (double)Minecraft.thePlayer.getDistanceToEntity(ent);
      float yaw = (float)(-(Math.atan2(x, z) * 57.29577951308232D));
      float pitch = (float)(-(Math.asin(y) * 57.29577951308232D));
      return new float[]{yaw, pitch};
   }

   public static float EXgetYawChangeGiven(double posX, double posZ, float yaw) {
      Minecraft.getMinecraft();
      double deltaX = posX - Minecraft.thePlayer.posX;
      Minecraft.getMinecraft();
      double deltaZ = posZ - Minecraft.thePlayer.posZ;
      double yawToEntity;
      if(deltaZ < 0.0D && deltaX < 0.0D) {
         yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
      } else if(deltaZ < 0.0D && deltaX > 0.0D) {
         yawToEntity = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
      } else {
         yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
      }

      return MathHelper.wrapAngleTo180_float(-(yaw - (float)yawToEntity));
   }

   public static float[] getRotationsForAura(Entity entity, double maxRange) {
      double diffX = entity.posX - Minecraft.thePlayer.posX;
      double diffZ = entity.posZ - Minecraft.thePlayer.posZ;
      Location BestPos = new Location(entity.posX, entity.posY, entity.posZ);
      Minecraft.getMinecraft();
      double var10002 = Minecraft.thePlayer.posX;
      Minecraft.getMinecraft();
      double var10003 = Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight();
      Minecraft.getMinecraft();
      Location myEyePos = new Location(var10002, var10003, Minecraft.thePlayer.posZ);

      for(double i = entity.boundingBox.minY + 0.7D; i < entity.boundingBox.maxY - 0.1D; i += 0.1D) {
         Location location = new Location(entity.posX, i, entity.posZ);
         if(myEyePos.distanceTo(location) < myEyePos.distanceTo(BestPos)) {
            BestPos = new Location(entity.posX, i, entity.posZ);
         }
      }

      if(myEyePos.distanceTo(BestPos) > maxRange) {
         return null;
      } else {
         double var10000 = BestPos.getY();
         Minecraft.getMinecraft();
         double var10001 = Minecraft.thePlayer.posY;
         Minecraft.getMinecraft();
         double diffY = var10000 - (var10001 + (double)Minecraft.thePlayer.getEyeHeight());
         double dist = (double)MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
         float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
         float pitch = (float)(-(Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D));
         return new float[]{yaw, pitch};
      }
   }

   public static float pitch() {
      return Minecraft.thePlayer.rotationPitch;
   }

   public static void pitch(float pitch) {
      Minecraft.thePlayer.rotationPitch = pitch;
   }

   public static float yaw() {
      return Minecraft.thePlayer.rotationYaw;
   }

   public static void yaw(float yaw) {
      Minecraft.thePlayer.rotationYaw = yaw;
   }

   public static float[] faceTarget(Entity target, float p_706252, float p_706253, boolean miss) {
      double var4 = target.posX - Minecraft.thePlayer.posX;
      double var8 = target.posZ - Minecraft.thePlayer.posZ;
      double var6;
      if(target instanceof EntityLivingBase) {
         EntityLivingBase var10 = (EntityLivingBase)target;
         var6 = var10.posY + (double)var10.getEyeHeight() - (Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight());
      } else {
         var6 = (target.getEntityBoundingBox().minY + target.getEntityBoundingBox().maxY) / 2.0D - (Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight());
      }

      new Random();
      double var14 = (double)MathHelper.sqrt_double(var4 * var4 + var8 * var8);
      float var12 = (float)(Math.atan2(var8, var4) * 180.0D / 3.141592653589793D) - 90.0F;
      float var13 = (float)(-Math.atan2(var6 - (target instanceof EntityPlayer?0.25D:0.0D), var14) * 180.0D / 3.141592653589793D);
      float pitch = changeRotation(Minecraft.thePlayer.rotationPitch, var13, p_706253);
      float yaw = changeRotation(Minecraft.thePlayer.rotationYaw, var12, p_706252);
      return new float[]{yaw, pitch};
   }

   public static float getYawChangeGiven(double posX, double posZ, float yaw) {
      Minecraft.getMinecraft();
      double deltaX = posX - Minecraft.thePlayer.posX;
      Minecraft.getMinecraft();
      double deltaZ = posZ - Minecraft.thePlayer.posZ;
      double yawToEntity = deltaZ < 0.0D && deltaX < 0.0D?90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX)):(deltaZ < 0.0D && deltaX > 0.0D?-90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX)):Math.toDegrees(-Math.atan(deltaX / deltaZ)));
      return MathHelper.wrapAngleTo180_float(-(yaw - (float)yawToEntity));
   }

   public static float changeRotation(float p_706631, float p_706632, float p_706633) {
      float var4 = MathHelper.wrapAngleTo180_float(p_706632 - p_706631);
      if(var4 > p_706633) {
         var4 = p_706633;
      }

      if(var4 < -p_706633) {
         var4 = -p_706633;
      }

      return p_706631 + var4;
   }

   public static double[] getRotationToEntity(Entity entity) {
      double pX = Minecraft.thePlayer.posX;
      double pY = Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight();
      double pZ = Minecraft.thePlayer.posZ;
      double eX = entity.posX;
      double eY = entity.posY + (double)(entity.height / 2.0F);
      double eZ = entity.posZ;
      double dX = pX - eX;
      double dY = pY - eY;
      double dZ = pZ - eZ;
      double dH = Math.sqrt(Math.pow(dX, 2.0D) + Math.pow(dZ, 2.0D));
      double yaw = Math.toDegrees(Math.atan2(dZ, dX)) + 90.0D;
      double pitch = Math.toDegrees(Math.atan2(dH, dY));
      return new double[]{yaw, 90.0D - pitch};
   }

   public static float[] getRotations(Entity entity) {
      if(entity == null) {
         return null;
      } else {
         double diffX = entity.posX - Minecraft.thePlayer.posX;
         double diffZ = entity.posZ - Minecraft.thePlayer.posZ;
         double diffY;
         if(entity instanceof EntityLivingBase) {
            EntityLivingBase elb = (EntityLivingBase)entity;
            diffY = elb.posY + ((double)elb.getEyeHeight() - 0.4D) - (Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight());
         } else {
            diffY = (entity.boundingBox.minY + entity.boundingBox.maxY) / 2.0D - (Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight());
         }

         double dist = (double)MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
         float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
         float pitch = (float)(-Math.atan2(diffY, dist) * 180.0D / 3.141592653589793D);
         return new float[]{yaw, pitch};
      }
   }

   public static float getDistanceBetweenAngles(float angle1, float angle2) {
      float angle3 = Math.abs(angle1 - angle2) % 360.0F;
      if(angle3 > 180.0F) {
         angle3 = 0.0F;
      }

      return angle3;
   }

   public static float[] grabBlockRotations(BlockPos pos) {
      return getVecRotation(Minecraft.thePlayer.getPositionVector().addVector(0.0D, (double)Minecraft.thePlayer.getEyeHeight(), 0.0D), new Vec3((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D));
   }

   public static float[] getVecRotation(Vec3 position) {
      return getVecRotation(Minecraft.thePlayer.getPositionVector().addVector(0.0D, (double)Minecraft.thePlayer.getEyeHeight(), 0.0D), position);
   }

   public static float[] getVecRotation(Vec3 origin, Vec3 position) {
      Vec3 difference = position.subtract(origin);
      double distance = difference.flat().lengthVector();
      float yaw = (float)Math.toDegrees(Math.atan2(difference.zCoord, difference.xCoord)) - 90.0F;
      float pitch = (float)(-Math.toDegrees(Math.atan2(difference.yCoord, distance)));
      return new float[]{yaw, pitch};
   }

   public static int wrapAngleToDirection(float yaw, int zones) {
      int angle = (int)((double)(yaw + (float)(360 / (2 * zones))) + 0.5D) % 360;
      if(angle < 0) {
         angle += 360;
      }

      return angle / (360 / zones);
   }

   public static float[] getRotationFromPosition(double x, double z, double y) {
      Minecraft.getMinecraft();
      double xDiff = x - Minecraft.thePlayer.posX;
      Minecraft.getMinecraft();
      double zDiff = z - Minecraft.thePlayer.posZ;
      Minecraft.getMinecraft();
      double yDiff = y - Minecraft.thePlayer.posY - 1.2D;
      double dist = (double)MathHelper.sqrt_double(xDiff * xDiff + zDiff * zDiff);
      float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
      float pitch = (float)(-(Math.atan2(yDiff, dist) * 180.0D / 3.141592653589793D));
      return new float[]{yaw, pitch};
   }

   public static float[] getRotationsEx(EntityLivingBase ent) {
      double x = ent.posX;
      double z = ent.posZ;
      double y = ent.posY + (double)(ent.getEyeHeight() / 2.0F);
      return getRotationFromPosition(x, z, y);
   }

   public static float getYawChange(float yaw, double posX, double posZ) {
      Minecraft.getMinecraft();
      double deltaX = posX - Minecraft.thePlayer.posX;
      Minecraft.getMinecraft();
      double deltaZ = posZ - Minecraft.thePlayer.posZ;
      double yawToEntity = 0.0D;
      if(deltaZ < 0.0D && deltaX < 0.0D) {
         if(deltaX != 0.0D) {
            yawToEntity = 90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
         }
      } else if(deltaZ < 0.0D && deltaX > 0.0D) {
         if(deltaX != 0.0D) {
            yawToEntity = -90.0D + Math.toDegrees(Math.atan(deltaZ / deltaX));
         }
      } else if(deltaZ != 0.0D) {
         yawToEntity = Math.toDegrees(-Math.atan(deltaX / deltaZ));
      }

      return MathHelper.wrapAngleTo180_float(-(yaw - (float)yawToEntity));
   }

   public static float getPitchChange(float pitch, Entity entity, double posY) {
      double var10000 = entity.posX;
      Minecraft.getMinecraft();
      double deltaX = var10000 - Minecraft.thePlayer.posX;
      var10000 = entity.posZ;
      Minecraft.getMinecraft();
      double deltaZ = var10000 - Minecraft.thePlayer.posZ;
      var10000 = posY - 2.2D + (double)entity.getEyeHeight();
      Minecraft.getMinecraft();
      double deltaY = var10000 - Minecraft.thePlayer.posY;
      double distanceXZ = (double)MathHelper.sqrt_double(deltaX * deltaX + deltaZ * deltaZ);
      double pitchToEntity = -Math.toDegrees(Math.atan(deltaY / distanceXZ));
      return -MathHelper.wrapAngleTo180_float(pitch - (float)pitchToEntity) - 2.5F;
   }

   public static float[] getPredictedRotations(EntityLivingBase ent) {
      double x = ent.posX + (ent.posX - ent.lastTickPosX);
      double z = ent.posZ + (ent.posZ - ent.lastTickPosZ);
      double y = ent.posY + (double)(ent.getEyeHeight() / 2.0F);
      return getRotationFromPosition(x, z, y);
   }

   public static float[] getRotationsInsane(Entity entity, double maxRange) {
      if(entity == null) {
         System.out.println("Fuck you ! Entity is nul!");
         return null;
      } else {
         double diffX = entity.posX - Minecraft.thePlayer.posX;
         double diffZ = entity.posZ - Minecraft.thePlayer.posZ;
         Location BestPos = new Location(entity.posX, entity.posY, entity.posZ);
         Location myEyePos = new Location(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight(), Minecraft.thePlayer.posZ);

         for(double diffY = entity.boundingBox.minY + 0.7D; diffY < entity.boundingBox.maxY - 0.1D; diffY += 0.1D) {
            if(myEyePos.distanceTo(new Location(entity.posX, diffY, entity.posZ)) < myEyePos.distanceTo(BestPos)) {
               BestPos = new Location(entity.posX, diffY, entity.posZ);
            }
         }

         if(myEyePos.distanceTo(BestPos) >= maxRange) {
            return null;
         } else {
            double var15 = BestPos.getY() - (Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight());
            double dist = (double)MathHelper.sqrt_double(diffX * diffX + diffZ * diffZ);
            float yaw = (float)(Math.atan2(diffZ, diffX) * 180.0D / 3.141592653589793D) - 90.0F;
            float pitch = (float)(-(Math.atan2(var15, dist) * 180.0D / 3.141592653589793D));
            return new float[]{yaw, pitch};
         }
      }
   }
}
