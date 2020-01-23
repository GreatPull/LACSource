package net.minecraft.entity.player.Really.Client.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.Really.Client.api.events.world.EventMove;
import net.minecraft.entity.player.Really.Client.management.FriendManager;
import net.minecraft.entity.player.Really.Client.utils.BlockUtils;
import net.minecraft.entity.player.Really.Client.utils.R3DUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import optifine.Reflector;

public class Wrapper {
   private static BlockUtils blockUtils = new BlockUtils();
   private static R3DUtil r3DUtils = new R3DUtil();
   public static final byte HOTBAR_UP = 0;
   public static final byte HOTBAR_DOWN = 1;
   public static final Minecraft mc = Minecraft.getMinecraft();

   public static FontRenderer fr() {
      return Minecraft.fontRendererObj;
   }

   public static int width() {
      return (new ScaledResolution(mc)).getScaledWidth();
   }

   public static int height() {
      return (new ScaledResolution(mc)).getScaledHeight();
   }

   public static Block block(BlockPos pos, double offset) {
      return Minecraft.theWorld.getBlockState(pos.add(0.0D, offset, 0.0D)).getBlock();
   }

   public static void position(double x2, double y2, double z2, boolean grounded) {
      Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x2, y2, z2, grounded));
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

   public static double x() {
      return Minecraft.thePlayer.posX;
   }

   public static void x(double x2) {
      Minecraft.thePlayer.posX = x2;
   }

   public static double y() {
      return Minecraft.thePlayer.posY;
   }

   public static void y(double y2) {
      Minecraft.thePlayer.posY = y2;
   }

   public static double z() {
      return Minecraft.thePlayer.posZ;
   }

   public static void z(double z2) {
      Minecraft.thePlayer.posZ = z2;
   }

   public static String withColors(String identifier, String input) {
      String output = input;
      input.indexOf(identifier);

      while(output.indexOf(identifier) != -1) {
         output = output.replace(identifier, "§");
         output.indexOf(identifier);
      }

      return output;
   }

   public static void sendMessage(String message, boolean toServer) {
      if(toServer) {
         Minecraft.thePlayer.sendChatMessage(message);
      } else {
         Minecraft.thePlayer.addChatMessage(new ChatComponentText(String.format("%s%s", new Object[]{"Null: " + EnumChatFormatting.GRAY, message})));
      }

   }

   public static void msgPlayer(String msg) {
      Minecraft.thePlayer.addChatMessage(new ChatComponentText(String.format("%s%s", new Object[]{"Null: " + EnumChatFormatting.GRAY, msg})));
   }

   public static int windowWidth() {
      return (new ScaledResolution(mc)).getScaledWidth();
   }

   public static int windowHeight() {
      return (new ScaledResolution(mc)).getScaledHeight();
   }

   public static void setMoveSpeed(EventMove eventMove, double d2) {
      throw new Error("Unresolved compilation problems: \n\tCannot make a static reference to the non-static method setX(double) from the type EventMove\n\tCannot make a static reference to the non-static method setZ(double) from the type EventMove\n\tCannot make a static reference to the non-static method setX(double) from the type EventMove\n\tCannot make a static reference to the non-static method setZ(double) from the type EventMove\n");
   }

   public static String capitalize(String line) {
      return String.valueOf(String.valueOf(Character.toUpperCase(line.charAt(0)))) + line.substring(1);
   }

   public static Entity getEntity(double distance) {
      return getEntity(distance, 0.0D, 0.0F) == null?null:(Entity)getEntity(distance, 0.0D, 0.0F)[0];
   }

   public static Object[] getEntity(double distance, double expand, float partialTicks) {
      Entity var2 = mc.getRenderViewEntity();
      Entity entity = null;
      if(var2 != null && Minecraft.theWorld != null) {
         mc.mcProfiler.startSection("pick");
         Vec3 var3 = var2.getPositionEyes(0.0F);
         Vec3 var4 = var2.getLook(0.0F);
         Vec3 var5 = var3.addVector(var4.xCoord * distance, var4.yCoord * distance, var4.zCoord * distance);
         Vec3 var6 = null;
         float var7 = 1.0F;
         List<Entity> var8 = Minecraft.theWorld.getEntitiesWithinAABBExcludingEntity(var2, var2.getEntityBoundingBox().addCoord(var4.xCoord * distance, var4.yCoord * distance, var4.zCoord * distance).expand(1.0D, 1.0D, 1.0D));
         double var9 = distance;

         for(int var10 = 0; var10 < var8.size(); ++var10) {
            Entity var11 = (Entity)var8.get(var10);
            if(var11.canBeCollidedWith()) {
               float var12 = var11.getCollisionBorderSize();
               AxisAlignedBB var13 = var11.getEntityBoundingBox().expand((double)var12, (double)var12, (double)var12);
               var13 = var13.expand(expand, expand, expand);
               MovingObjectPosition var14 = var13.calculateIntercept(var3, var5);
               if(var13.isVecInside(var3)) {
                  if(0.0D < var9 || var9 == 0.0D) {
                     entity = var11;
                     var6 = var14 == null?var3:var14.hitVec;
                     var9 = 0.0D;
                  }
               } else {
                  double var15;
                  if(var14 != null && ((var15 = var3.distanceTo(var14.hitVec)) < var9 || var9 == 0.0D)) {
                     boolean canRiderInteract = false;
                     if(Reflector.ForgeEntity_canRiderInteract.exists()) {
                        canRiderInteract = Reflector.callBoolean(var11, Reflector.ForgeEntity_canRiderInteract, new Object[0]);
                     }

                     if(var11 == var2.ridingEntity && !canRiderInteract) {
                        if(var9 == 0.0D) {
                           entity = var11;
                           var6 = var14.hitVec;
                        }
                     } else {
                        entity = var11;
                        var6 = var14.hitVec;
                        var9 = var15;
                     }
                  }
               }
            }
         }

         if(var9 < distance && !(entity instanceof EntityLivingBase) && !(entity instanceof EntityItemFrame)) {
            entity = null;
         }

         mc.mcProfiler.endSection();
         if(entity != null && var6 != null) {
            return new Object[]{entity, var6};
         } else {
            return null;
         }
      } else {
         return null;
      }
   }

   public static BlockUtils blockUtils() {
      return blockUtils;
   }

   public static R3DUtil get3DUtils() {
      return r3DUtils;
   }

   public static void sendPacketInQueue(Packet p2) {
      Minecraft.getMinecraft();
      Minecraft.thePlayer.sendQueue.addToSendQueue(p2);
   }

   public static float getDistanceToEntity(Entity from, Entity to2) {
      return from.getDistanceToEntity(to2);
   }

   public static boolean isWithinFOV(Entity en2, double angle) {
      double angleDifference = angleDifference((double)Minecraft.thePlayer.rotationYaw, getRotationToEntity(en2)[0]);
      double var5;
      return angleDifference > 0.0D && angleDifference < angle || -(var5 = angle * 0.5D) < angleDifference && angleDifference < 0.0D;
   }

   public static double angleDifference(double a2, double b2) {
      return ((a2 - b2) % 360.0D + 540.0D) % 360.0D - 180.0D;
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

   public static float[] faceTarget(Entity target, double p_70625_2_, double p_70625_3_, boolean miss) {
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
      float pitch = changeRotation((double)Minecraft.thePlayer.rotationPitch, (double)var13, p_70625_3_);
      float yaw = changeRotation((double)Minecraft.thePlayer.rotationYaw, (double)var12, p_70625_2_);
      return new float[]{yaw, pitch};
   }

   public static float changeRotation(double p_70663_1_, double p_70663_2_, double p_70663_3_) {
      float var4 = MathHelper.wrapAngleTo180_float((float)(p_70663_2_ - p_70663_1_));
      if((double)var4 > p_70663_3_) {
         var4 = (float)p_70663_3_;
      }

      if((double)var4 < -p_70663_3_) {
         var4 = (float)(-p_70663_3_);
      }

      return (float)(p_70663_1_ + (double)var4);
   }

   public static Entity rayTrace(float f2, double d2) {
      throw new Error("Unresolved compilation problems: \n\tThe field Minecraft.renderViewEntity is not visible\n\tThe field Minecraft.renderViewEntity is not visible\n\tThe method func_174822_a(double, float) is undefined for the type Entity\n\tThe field Minecraft.renderViewEntity is not visible\n\tThe field Minecraft.renderViewEntity is not visible\n\tThe field Minecraft.renderViewEntity is not visible\n\tThe field Minecraft.renderViewEntity is not visible\n\tThe field Minecraft.renderViewEntity is not visible\n");
   }

   public static Entity findClosestToCross(double range) {
      Entity e2 = null;
      double best = 360.0D;
      Minecraft.getMinecraft();

      for(Object o2 : Minecraft.theWorld.loadedEntityList) {
         Entity ent = (Entity)o2;
         if(ent instanceof EntityPlayer) {
            Minecraft.getMinecraft();
            double diffX = ent.posX - Minecraft.thePlayer.posX;
            Minecraft.getMinecraft();
            double diffZ = ent.posZ - Minecraft.thePlayer.posZ;
            float newYaw = (float)(Math.toDegrees(Math.atan2(diffZ, diffX)) - 90.0D);
            Minecraft.getMinecraft();
            double difference = Math.abs(angleDifference((double)newYaw, (double)Minecraft.thePlayer.rotationYaw));
            Minecraft.getMinecraft();
            if(ent != Minecraft.thePlayer) {
               Minecraft.getMinecraft();
               if((double)Minecraft.thePlayer.getDistanceToEntity(ent) <= range && ent instanceof EntityPlayer && !FriendManager.isFriend(ent.getName()) && difference < best) {
                  best = difference;
                  e2 = ent;
               }
            }
         }
      }

      return e2;
   }

   public static void updatePosition(double x2, double y2, double z2) {
      Minecraft.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x2, y2, z2, Minecraft.thePlayer.onGround));
   }

   public static int getStringWidth(String text, int increase) {
      return fr().getStringWidth(text);
   }

   public static int RGBtoHEX(int r2, int g2, int b2, int a2) {
      return (a2 << 24) + (r2 << 16) + (g2 << 8) + b2;
   }

   public static List loadedEntityList() {
      ArrayList<EntityLivingBase> loadedList = new ArrayList();
      loadedList.remove(Minecraft.thePlayer);
      return loadedList;
   }

   public static void sendPacketNoEvents(Packet a2) {
      Minecraft.getNetHandler().getNetworkManager().sendPacket(a2);
   }

   public static boolean isStorage(TileEntity entity) {
      return entity instanceof TileEntityChest || entity instanceof TileEntityBrewingStand || entity instanceof TileEntityDropper || entity instanceof TileEntityDispenser || entity instanceof TileEntityFurnace || entity instanceof TileEntityHopper || entity instanceof TileEntityEnderChest;
   }

   public static int toRGBAHex(float r2, float g2, float b2, float a2) {
      return ((int)(a2 * 255.0F) & 255) << 24 | ((int)(r2 * 255.0F) & 255) << 16 | ((int)(g2 * 255.0F) & 255) << 8 | (int)(b2 * 255.0F) & 255;
   }

   public static boolean isOnSameTeam(boolean teams, EntityLivingBase e2) {
      return teams && e2.isOnSameTeam(Minecraft.thePlayer);
   }

   public static int changeAlpha(int color, int alpha) {
      int var2;
      return alpha << 24 | (var2 = color & 16777215);
   }

   public static float[] getRotations(Entity target, Entity caster) {
      double x2 = target.posX - caster.posX;
      double y2 = target.posY + (double)target.getEyeHeight() / 1.3D - (caster.posY + (double)caster.getEyeHeight());
      double z2 = target.posZ - caster.posZ;
      double pos = Math.sqrt(x2 * x2 + z2 * z2);
      float yaw = (float)(Math.atan2(z2, x2) * 180.0D / 3.141592653589793D) - 90.0F;
      float pitch = (float)(-Math.atan2(y2, pos) * 180.0D / 3.141592653589793D);
      return new float[]{yaw, pitch};
   }

   public static void blink(double[] startPos, BlockPos endPos, double slack, double[] pOffset) {
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
               curX = Math.abs(diffX) > offset?curX + offset:curX + Math.abs(diffX);
            }

            if(diffX > 0.0D) {
               curX = Math.abs(diffX) > offset?curX - offset:curX - Math.abs(diffX);
            }

            if(diffY < 0.0D) {
               double var34;
               curY = Math.abs(diffY) > 0.25D?(var34 = curY + 0.25D):curY + Math.abs(diffY);
            }

            if(diffY > 0.0D) {
               double var35;
               curY = Math.abs(diffY) > 0.25D?(var35 = curY - 0.25D):curY - Math.abs(diffY);
            }

            if(diffZ < 0.0D) {
               curZ = Math.abs(diffZ) > offset?curZ + offset:curZ + Math.abs(diffZ);
            }

            if(diffZ > 0.0D) {
               curZ = Math.abs(diffZ) > offset?curZ - offset:curZ - Math.abs(diffZ);
            }

            Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            Minecraft.getMinecraft();
            Minecraft.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(curX, curY, curZ, Minecraft.thePlayer.rotationYaw, Minecraft.thePlayer.rotationPitch, true));
         }
      } catch (Exception var33) {
         ;
      }

   }
}
