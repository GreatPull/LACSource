package net.minecraft.entity.player.Really.Client.module.modules.movement;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.misc.EventCollideWithBlock;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPacketSend;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;

public class Jesus extends Module {
   int stage;
   int water;
   private TimerUtil timer = new TimerUtil();
   private boolean wasWater = false;
   private int ticks = 0;
   private Mode mode = new Mode("Mode", "Mode", Jesus.JMode.values(), Jesus.JMode.Dolphin);

   public Jesus() {
      super("LiquidWalk", new String[]{"waterwalk", "float"}, ModuleType.Movement);
      this.setColor((new Color(188, 233, 248)).getRGB());
      this.addValues(new Value[]{this.mode});
   }

   public void onEnable() {
      this.wasWater = false;
      super.onEnable();
   }

   private boolean canJeboos() {
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer.fallDistance < 3.0F) {
         var10000 = mc;
         if(!Minecraft.gameSettings.keyBindJump.isPressed() && !BlockHelper.isInLiquid()) {
            var10000 = mc;
            if(!Minecraft.thePlayer.isSneaking()) {
               return true;
            }
         }
      }

      return false;
   }

   boolean shouldJesus() {
      Minecraft var10000 = mc;
      double x = Minecraft.thePlayer.posX;
      var10000 = mc;
      double y = Minecraft.thePlayer.posY;
      var10000 = mc;
      double z = Minecraft.thePlayer.posZ;


      return false;
   }

   @EventHandler
   public void onPre(EventPreUpdate e) {
      this.setSuffix(this.mode.getValue());
      if(this.mode.getValue() == Jesus.JMode.Dolphin) {
         Minecraft var10000 = mc;
         if(Minecraft.thePlayer.isInWater()) {
            var10000 = mc;
            if(!Minecraft.thePlayer.isSneaking() && this.shouldJesus()) {
               var10000 = mc;
               Minecraft.thePlayer.motionY = 0.09D;
            }
         }

         if(EventPreUpdate.getType() == 1) {
            return;
         }

         label78: {
            var10000 = mc;
            if(!Minecraft.thePlayer.onGround) {
               var10000 = mc;
               if(!Minecraft.thePlayer.isOnLadder()) {
                  break label78;
               }
            }

            this.wasWater = false;
         }

         var10000 = mc;
         if(Minecraft.thePlayer.motionY > 0.0D && this.wasWater) {
            var10000 = mc;
            if(Minecraft.thePlayer.motionY <= 0.11D) {
               var10000 = mc;
               EntityPlayerSP player = Minecraft.thePlayer;
               player.motionY *= 1.2671D;
            }

            var10000 = mc;
            EntityPlayerSP player2 = Minecraft.thePlayer;
            player2.motionY += 0.05172D;
         }

         if(this.isInLiquid()) {
            var10000 = mc;
            if(!Minecraft.thePlayer.isSneaking()) {
               if(this.ticks < 3) {
                  var10000 = mc;
                  Minecraft.thePlayer.motionY = 0.13D;
                  ++this.ticks;
                  this.wasWater = false;
               } else {
                  var10000 = mc;
                  Minecraft.thePlayer.motionY = 0.5D;
                  this.ticks = 0;
                  this.wasWater = true;
               }
            }
         }
      } else if(this.mode.getValue() == Jesus.JMode.NCP && BlockHelper.isInLiquid()) {
         Minecraft var15 = mc;
         if(!Minecraft.thePlayer.isSneaking()) {
            var15 = mc;
            if(!Minecraft.gameSettings.keyBindJump.isPressed()) {
               var15 = mc;
               Minecraft.thePlayer.motionY = 0.05D;
               var15 = mc;
               Minecraft.thePlayer.onGround = true;
            }
         }
      }

   }

   private boolean isInLiquid() {
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer == null) {
         return false;
      } else {
         var10000 = mc;
         int x = MathHelper.floor_double(Minecraft.thePlayer.boundingBox.minX);

         while(true) {
            Minecraft var10001 = mc;
            if(x >= MathHelper.floor_double(Minecraft.thePlayer.boundingBox.maxX) + 1) {
               return false;
            }

            var10000 = mc;
            int z = MathHelper.floor_double(Minecraft.thePlayer.boundingBox.minZ);

            while(true) {
               var10001 = mc;
               if(z >= MathHelper.floor_double(Minecraft.thePlayer.boundingBox.maxZ) + 1) {
                  ++x;
                  break;
               }

               Minecraft var10003 = mc;
               BlockPos pos = new BlockPos(x, (int)Minecraft.thePlayer.boundingBox.minY, z);
               var10000 = mc;
               Block block = Minecraft.theWorld.getBlockState(pos).getBlock();
               if(block != null && !(block instanceof BlockAir)) {
                  return block instanceof BlockLiquid;
               }

               ++z;
            }
         }
      }
   }

   public double getMotionY(double stage) {
      --stage;
      double[] motion = new double[]{0.5D, 0.484D, 0.468D, 0.436D, 0.404D, 0.372D, 0.34D, 0.308D, 0.276D, 0.244D, 0.212D, 0.18D, 0.166D, 0.166D, 0.156D, 0.123D, 0.135D, 0.111D, 0.086D, 0.098D, 0.073D, 0.048D, 0.06D, 0.036D, 0.0106D, 0.015D, 0.004D, 0.004D, 0.004D, 0.004D, -0.013D, -0.045D, -0.077D, -0.109D};
      return stage < (double)motion.length && stage >= 0.0D?motion[(int)stage]:-999.0D;
   }

   public static boolean isOnGround(double height) {
      return !Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, Minecraft.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty();
   }

   public static int getSpeedEffect() {
      return Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)?Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1:0;
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

   @EventHandler
   public void onPacket(EventPacketSend e) {
      if(this.mode.getValue() == Jesus.JMode.NCP && EventPacketSend.getPacket() instanceof C03PacketPlayer && this.canJeboos() && BlockHelper.isOnLiquid()) {
         C03PacketPlayer packet = (C03PacketPlayer)EventPacketSend.getPacket();
         Minecraft var10001 = mc;
         packet.y = Minecraft.thePlayer.ticksExisted % 2 == 0?packet.y + 0.01D:packet.y - 0.01D;
      }

   }

   @EventHandler
   public void onBB(EventCollideWithBlock e) {
      if(this.mode.getValue() == Jesus.JMode.NCP && e.getBlock() instanceof BlockLiquid && this.canJeboos()) {
         e.setBoundingBox(new AxisAlignedBB((double)e.getPos().getX(), (double)e.getPos().getY(), (double)e.getPos().getZ(), (double)e.getPos().getX() + 1.0D, (double)e.getPos().getY() + 1.0D, (double)e.getPos().getZ() + 1.0D));
      }

   }

   static enum JMode {
      NCP,
      Dolphin;
   }
}
