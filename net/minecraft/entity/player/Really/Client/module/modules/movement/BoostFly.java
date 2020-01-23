package net.minecraft.entity.player.Really.Client.module.modules.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventMove;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPacketRecieve;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPostUpdate;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.management.ModuleManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.TimeHelper;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;
import net.minecraft.entity.player.Really.Client.utils.math.MathUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Timer;

public class BoostFly extends Module {
   private Option lagcheck = new Option("LagCheck", "LagCheck", Boolean.valueOf(true));
   private Option bob = new Option("Bobbing", "Bobbing", Boolean.valueOf(true));
   private Option stay = new Option("Stayed", "Stayed", Boolean.valueOf(false));
   private Numbers delay = new Numbers("StayDelay", "StayDelay", Double.valueOf(50.0D), Double.valueOf(0.0D), Double.valueOf(200.0D), Double.valueOf(1.0D));
   private double movementSpeed;
   private int hypixelCounter;
   private int hypixelCounter2;
   private int packetCounter;
   private TimerUtil deactivationDelay = new TimerUtil();
   int counter;
   int level;
   double moveSpeed;
   double lastDist;
   boolean b2;
   int sb;
   TimeHelper timer = new TimeHelper();

   public BoostFly() {
      super("BoostFly", new String[]{"Zoomfly", "Boostfly", "fastfly"}, ModuleType.Movement);
      this.addValues(new Value[]{this.bob, this.stay, this.delay, this.lagcheck});
   }

   public void damagePlayer(int damage) {
      if(damage < 1) {
         damage = 1;
      }

      Minecraft var10001 = mc;
      if(damage > MathHelper.floor_double((double)Minecraft.thePlayer.getMaxHealth())) {
         Minecraft var10000 = mc;
         damage = MathHelper.floor_double((double)Minecraft.thePlayer.getMaxHealth());
      }

      double offset = 0.0625D;
      Minecraft var5 = mc;
      if(Minecraft.thePlayer != null) {
         var5 = mc;
         if(Minecraft.getNetHandler() != null) {
            var5 = mc;
            if(Minecraft.thePlayer.onGround) {
               for(int i = 0; (double)i <= (double)(3 + damage) / offset; ++i) {
                  var5 = mc;
                  NetHandlerPlayClient var9 = Minecraft.getNetHandler();
                  Minecraft var10003 = mc;
                  Minecraft var10004 = mc;
                  double var13 = Minecraft.thePlayer.posY + offset;
                  Minecraft var10005 = mc;
                  var9.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, var13, Minecraft.thePlayer.posZ, false));
                  Minecraft var10 = mc;
                  NetHandlerPlayClient var11 = Minecraft.getNetHandler();
                  var10003 = mc;
                  Minecraft var14 = mc;
                  var10005 = mc;
                  var11.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, (double)i == (double)(3 + damage) / offset));
               }
            }
         }
      }

   }

   @EventHandler
   private void onPacket(EventPacketRecieve ep) {
      if(((Boolean)this.lagcheck.getValue()).booleanValue() && ep.getPacket() instanceof S08PacketPlayerPosLook && this.deactivationDelay.delay(2000.0F)) {
         ++this.packetCounter;
         S08PacketPlayerPosLook pac = (S08PacketPlayerPosLook)ep.getPacket();
         Minecraft var10001 = mc;
         pac.yaw = Minecraft.thePlayer.rotationYaw;
         var10001 = mc;
         pac.pitch = Minecraft.thePlayer.rotationPitch;
         this.level = -5;
         Client var10000 = Client.instance;
         Client.getModuleManager();
         if(ModuleManager.getModuleByName("BoostFly").isEnabled()) {
            var10000 = Client.instance;
            Client.getModuleManager();
            ModuleManager.getModuleByName("BoostFly").setEnabled(false);
         }
      }

   }

   public void onEnable() {
      this.damagePlayer(1);
      this.hypixelCounter = 0;
      this.hypixelCounter2 = 1000;
      Minecraft var10000 = mc;
      Minecraft.thePlayer.motionY = 0.41999998688697815D;
      this.level = 1;
      this.moveSpeed = 0.1D;
      this.b2 = true;
      this.lastDist = 0.0D;
   }

   public void onDisable() {
      this.hypixelCounter = 0;
      this.hypixelCounter2 = 100;
      Timer var10000 = mc.timer;
      Timer.timerSpeed = 1.0F;
      this.level = 1;
      this.moveSpeed = 0.1D;
      this.b2 = false;
      this.lastDist = 0.0D;
   }

   @EventHandler
   private void onUpdate(EventPreUpdate e) {
      if(((Boolean)this.bob.getValue()).booleanValue()) {
         Minecraft var10000 = mc;
         Minecraft.thePlayer.cameraYaw = 0.090909086F;
      }

      ++this.counter;
      Minecraft.getMinecraft();
      if(Minecraft.thePlayer.moveForward == 0.0F) {
         Minecraft.getMinecraft();
         if(Minecraft.thePlayer.moveStrafing == 0.0F) {
            Minecraft.getMinecraft();
            EntityPlayerSP var2 = Minecraft.thePlayer;
            Minecraft.getMinecraft();
            double var10001 = Minecraft.thePlayer.posX + 1.0D;
            Minecraft.getMinecraft();
            double var10002 = Minecraft.thePlayer.posY + 1.0D;
            Minecraft.getMinecraft();
            var2.setPosition(var10001, var10002, Minecraft.thePlayer.posZ + 1.0D);
            Minecraft.getMinecraft();
            var2 = Minecraft.thePlayer;
            Minecraft.getMinecraft();
            var10001 = Minecraft.thePlayer.prevPosX;
            Minecraft.getMinecraft();
            var10002 = Minecraft.thePlayer.prevPosY;
            Minecraft.getMinecraft();
            var2.setPosition(var10001, var10002, Minecraft.thePlayer.prevPosZ);
            Minecraft.getMinecraft();
            Minecraft.thePlayer.motionX = 0.0D;
            Minecraft.getMinecraft();
            Minecraft.thePlayer.motionZ = 0.0D;
         }
      }

      Minecraft.getMinecraft();
      Minecraft.thePlayer.motionY = 0.0D;
      Minecraft.getMinecraft();
      if(Minecraft.gameSettings.keyBindJump.pressed) {
         Minecraft.getMinecraft();
         Minecraft.thePlayer.motionY += 0.5D;
      }

      Minecraft.getMinecraft();
      if(Minecraft.gameSettings.keyBindSneak.pressed) {
         Minecraft.getMinecraft();
         Minecraft.thePlayer.motionY -= 0.5D;
      }

      if(this.counter != 1 && this.counter == 2) {
         Minecraft.getMinecraft();
         EntityPlayerSP var4 = Minecraft.thePlayer;
         Minecraft.getMinecraft();
         double var6 = Minecraft.thePlayer.posX;
         Minecraft.getMinecraft();
         double var8 = Minecraft.thePlayer.posY + 1.0E-1D;
         Minecraft.getMinecraft();
         var4.setPosition(var6, var8, Minecraft.thePlayer.posZ);
         this.counter = 0;
      }

   }

   @EventHandler
   public void onPost(EventPostUpdate e) {
      Minecraft.getMinecraft();
      double var10000 = Minecraft.thePlayer.posX;
      Minecraft.getMinecraft();
      double xDist = var10000 - Minecraft.thePlayer.prevPosX;
      Minecraft.getMinecraft();
      var10000 = Minecraft.thePlayer.posZ;
      Minecraft.getMinecraft();
      double zDist = var10000 - Minecraft.thePlayer.prevPosZ;
      this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
   }

   @EventHandler
   private void onMove(EventMove e) {
      float forward = MovementInput.moveForward;
      float strafe = MovementInput.moveStrafe;
      Minecraft var10000 = mc;
      float yaw = Minecraft.thePlayer.rotationYaw;
      double mx = Math.cos(Math.toRadians((double)(yaw + 90.0F)));
      double mz = Math.sin(Math.toRadians((double)(yaw + 90.0F)));
      if(forward == 0.0F && strafe == 0.0F) {
         EventMove.x = 0.0D;
         EventMove.z = 0.0D;
      } else if(forward != 0.0F) {
         if(strafe >= 1.0F) {
            float var12 = yaw + (float)(forward > 0.0F?-45:45);
            strafe = 0.0F;
         } else if(strafe <= -1.0F) {
            float var13 = yaw + (float)(forward > 0.0F?45:-45);
            strafe = 0.0F;
         }

         if(forward > 0.0F) {
            forward = 1.0F;
         } else if(forward < 0.0F) {
            forward = -1.0F;
         }
      }

      if(this.b2) {
         label166: {
            label1662: {
               if(this.level == 1) {
                  Minecraft.getMinecraft();
                  if(Minecraft.thePlayer.moveForward != 0.0F) {
                     break label166;
                  }

                  Minecraft.getMinecraft();
                  if(Minecraft.thePlayer.moveStrafing != 0.0F) {
                     break label166;
                  }
               }

               if(this.level == 2) {
                  this.level = 3;
                  this.moveSpeed *= 2.1499999D;
                  break label166;
               }

               if(this.level == 3) {
                  this.level = 4;
                  var10000 = mc;
                  double difference = (Minecraft.thePlayer.ticksExisted % 2 == 0?0.0103D:0.0123D) * (this.lastDist - MathUtil.getBaseMovementSpeed());
                  this.moveSpeed = this.lastDist - difference;
                  break label166;
               }

               label294: {
                  Minecraft.getMinecraft();
                  WorldClient var14 = Minecraft.theWorld;
                  Minecraft.getMinecraft();
                  EntityPlayerSP var10001 = Minecraft.thePlayer;
                  Minecraft.getMinecraft();
                  AxisAlignedBB var10002 = Minecraft.thePlayer.boundingBox;
                  Minecraft.getMinecraft();
                  if(var14.getCollidingBoundingBoxes(var10001, var10002.offset(0.0D, Minecraft.thePlayer.motionY, 0.0D)).size() <= 0) {
                     Minecraft.getMinecraft();
                     if(!Minecraft.thePlayer.isCollidedVertically) {
                        break label294;
                     }
                  }

                  this.level = 1;
               }

               this.moveSpeed = this.lastDist - this.lastDist / 159.0D;
               break label166;
            }
         }

         this.moveSpeed = Math.max(this.moveSpeed, MathUtil.getBaseMovementSpeed());
         EventMove.x = (double)forward * this.moveSpeed * mx + (double)strafe * this.moveSpeed * mz;
         EventMove.z = (double)forward * this.moveSpeed * mz - (double)strafe * this.moveSpeed * mx;
         if(forward == 0.0F && strafe == 0.0F) {
            EventMove.x = 0.0D;
            EventMove.z = 0.0D;
         }
      }

   }
}
