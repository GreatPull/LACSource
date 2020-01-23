package net.minecraft.entity.player.Really.Client.module.modules.movement;

import java.awt.Color;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventMove;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPacketRecieve;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.management.ModuleManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.module.modules.movement.Scaffold;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;
import net.minecraft.entity.player.Really.Client.utils.math.MathUtil;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Timer;

public class Speed extends Module {
   private Mode mode = new Mode("Mode", "mode", Speed.SpeedMode.values(), Speed.SpeedMode.HypixelHop);
   private int stage;
   private double movementSpeed;
   private double distance;
   private TimerUtil timer = new TimerUtil();
   private Option lagcheck = new Option("LagCheck", "LagCheck", Boolean.valueOf(true));
   private int packetCounter;
   private TimerUtil deactivationDelay = new TimerUtil();

   public Speed() {
      super("Speed", new String[]{"zoom"}, ModuleType.Movement);
      this.setColor((new Color(99, 248, 91)).getRGB());
      this.addValues(new Value[]{this.mode, this.lagcheck});
   }

   public void onDisable() {
      Timer var10000 = mc.timer;
      Timer.timerSpeed = 1.0F;
   }

   private boolean canZoom() {
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer.moving()) {
         var10000 = mc;
         if(Minecraft.thePlayer.onGround) {
            return true;
         }
      }

      return false;
   }

   @EventHandler
   private void onUpdate(EventPreUpdate e) {
      this.setSuffix(this.mode.getValue());
   }

   private void onPacket(EventPacketRecieve ep) {
      if(((Boolean)this.lagcheck.getValue()).booleanValue() && ep.getPacket() instanceof S08PacketPlayerPosLook && this.deactivationDelay.delay(2000.0F)) {
         ++this.packetCounter;
         S08PacketPlayerPosLook pac = (S08PacketPlayerPosLook)ep.getPacket();
         Minecraft var10001 = mc;
         pac.yaw = Minecraft.thePlayer.rotationYaw;
         var10001 = mc;
         pac.pitch = Minecraft.thePlayer.rotationPitch;
         Client var10000 = Client.instance;
         Client.getModuleManager();
         if(ModuleManager.getModuleByName("Speed").isEnabled()) {
            var10000 = Client.instance;
            Client.getModuleManager();
            ModuleManager.getModuleByName("Speed").setEnabled(false);
         }
      }

   }

   private static int getJumpEffect() {
      Minecraft.getMinecraft();
      if(Minecraft.thePlayer.isPotionActive(Potion.jump)) {
         Minecraft.getMinecraft();
         return Minecraft.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1;
      } else {
         return 0;
      }
   }

   private static double randomD(double min, double max, int scl) {
      int pow = (int)Math.pow(10.0D, (double)scl);
      return Math.floor((Math.random() * (max - min) + min) * (double)pow) / (double)pow;
   }

   @EventHandler
   private void onMove(EventMove e) {
      Client var10000 = Client.instance;
      Client.getModuleManager();
      Scaffold i3 = (Scaffold)ModuleManager.getModuleByName("Scaffold");
      if(!i3.isEnabled() || !((Boolean)Scaffold.Lag.getValue()).booleanValue()) {
         if(this.mode.getValue() == Speed.SpeedMode.Hypixel) {
            if(this.stage < 1) {
               ++this.stage;
               this.distance = 0.0D;
               return;
            }

            if(this.canZoom() && this.stage == 2) {
               Minecraft var10 = mc;
               Minecraft.thePlayer.motionY = 0.4101D + (double)getJumpEffect() * 0.1D;
               EventMove.setY(0.41D + (double)getJumpEffect() * 0.1D);
               this.movementSpeed *= 1.0D + randomD(0.5D, 0.8D, 2);
            } else if(this.stage == 3) {
               double difference = 0.66D * (this.distance - MathUtil.getBaseMovementSpeed());
               this.movementSpeed = this.distance - difference;
            } else {
               label188: {
                  Minecraft var8 = mc;
                  Minecraft var10001 = mc;
                  Minecraft var10002 = mc;
                  Minecraft var10004 = mc;
                  List<AxisAlignedBB> collidingList = Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, Minecraft.thePlayer.boundingBox.offset(0.0D, Minecraft.thePlayer.motionY, 0.0D));
                  if(collidingList.size() <= 0) {
                     var8 = mc;
                     if(!Minecraft.thePlayer.isCollidedVertically || (double)this.stage <= 0.8D) {
                        break label188;
                     }
                  }

                  var10001 = mc;
                  this.stage = Minecraft.thePlayer.moving()?1:0;
               }

               this.movementSpeed = this.distance - this.distance / 149.0D;
            }

            this.movementSpeed = Math.max(this.movementSpeed * 1.0D, MathUtil.getBaseMovementSpeed());
            Minecraft var11 = mc;
            Minecraft.thePlayer.setMoveSpeed(e, this.movementSpeed);
            var11 = mc;
            if(Minecraft.thePlayer.moving()) {
               ++this.stage;
            }
         } else if(this.mode.getValue() == Speed.SpeedMode.HypixelHop) {
            if(this.stage < 1) {
               ++this.stage;
               this.distance = 0.0D;
               return;
            }

            if(this.canZoom() && this.stage == 2) {
               Minecraft var15 = mc;
               Minecraft.thePlayer.motionY = 0.412D + (double)getJumpEffect() * 0.1D;
               EventMove.setY(0.412D + (double)getJumpEffect() * 0.1D);
               this.movementSpeed *= 1.56D;
            } else if(this.stage == 3) {
               double difference = 0.66D * (this.distance - MathUtil.getBaseMovementSpeed());
               this.movementSpeed = this.distance - difference;
            } else {
               label499: {
                  Minecraft var13 = mc;
                  Minecraft var19 = mc;
                  Minecraft var21 = mc;
                  Minecraft var22 = mc;
                  List<AxisAlignedBB> collidingList = Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, Minecraft.thePlayer.boundingBox.offset(0.0D, Minecraft.thePlayer.motionY, 0.0D));
                  if(collidingList.size() <= 0) {
                     var13 = mc;
                     if(!Minecraft.thePlayer.isCollidedVertically || (double)this.stage <= 0.8D) {
                        break label499;
                     }
                  }

                  var19 = mc;
                  this.stage = Minecraft.thePlayer.moving()?1:0;
               }

               this.movementSpeed = this.distance - this.distance / 149.0D;
            }

            this.movementSpeed = Math.max(this.movementSpeed * 1.0D, MathUtil.getBaseMovementSpeed());
            Minecraft var16 = mc;
            Minecraft.thePlayer.setMoveSpeed(e, this.movementSpeed);
            var16 = mc;
            if(Minecraft.thePlayer.moving()) {
               ++this.stage;
            }
         }

      }
   }

   static enum SpeedMode {
      Hypixel,
      HypixelHop;
   }
}
