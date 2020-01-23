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
import net.minecraft.entity.player.Really.Client.utils.Helper;
import net.minecraft.entity.player.Really.Client.utils.TimeHelper;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;
import net.minecraft.entity.player.Really.Client.utils.math.MathUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Timer;

public class BoosterHypixel extends Module {
   private Option particle = new Option("Particle", "Particle", Boolean.valueOf(false));
   private Option hurtcheck = new Option("Hurtcheck", "Hurtcheck", Boolean.valueOf(false));
   private Option bob = new Option("Bobbing", "Bobbing", Boolean.valueOf(true));
   private Numbers boost = new Numbers("Boost", "Boost", Double.valueOf(2.0D), Double.valueOf(0.1D), Double.valueOf(2.0D), Double.valueOf(0.1D));
   private Numbers delay = new Numbers("Delay", "Delay", Double.valueOf(250.0D), Double.valueOf(0.0D), Double.valueOf(500.0D), Double.valueOf(1.0D));
   private Option lagcheck = new Option("LagCheck", "LagCheck", Boolean.valueOf(true));
   private double movementSpeed;
   private int hypixelCounter;
   private int hypixelCounter2;
   int counter;
   int level;
   double moveSpeed;
   double lastDist;
   boolean b2;
   boolean b3 = false;
   int sb;
   TimeHelper timer = new TimeHelper();
   TimerUtil timer2 = new TimerUtil();
   private int packetCounter;
   private TimerUtil deactivationDelay = new TimerUtil();

   public BoosterHypixel() {
      super("BoosterHypixel", new String[]{"Zoomfly", "Boostfly", "fastfly"}, ModuleType.Movement);
      this.addValues(new Value[]{this.particle, this.hurtcheck, this.bob, this.boost, this.delay, this.lagcheck});
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
         if(ModuleManager.getModuleByName("BoosterHypixel").isEnabled()) {
            var10000 = Client.instance;
            Client.getModuleManager();
            ModuleManager.getModuleByName("BoosterHypixel").setEnabled(false);
         }
      }

   }

   public void damagePlayer(int damage) {
      if(damage < 1) {
         damage = 1;
      }

      if(damage > MathHelper.floor_double((double)Minecraft.thePlayer.getMaxHealth())) {
         Minecraft mc2 = mc;
         damage = MathHelper.floor_double((double)Minecraft.thePlayer.getMaxHealth());
      }

      double offset = 0.0625D;
      Minecraft mc3 = mc;
      if(Minecraft.thePlayer != null) {
         Minecraft var10000 = mc;
         if(Minecraft.getNetHandler() != null) {
            Minecraft mc4 = mc;
            if(Minecraft.thePlayer.onGround) {
               for(int i = 0; (double)i <= (double)(3 + damage) / 0.0625D; ++i) {
                  var10000 = mc;
                  NetHandlerPlayClient netHandler = Minecraft.getNetHandler();
                  Minecraft mc5 = mc;
                  double posX = Minecraft.thePlayer.posX;
                  Minecraft mc6 = mc;
                  double posY = Minecraft.thePlayer.posY + 0.0625D;
                  Minecraft mc7 = mc;
                  netHandler.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, posY, Minecraft.thePlayer.posZ, false));
                  var10000 = mc;
                  NetHandlerPlayClient netHandler2 = Minecraft.getNetHandler();
                  Minecraft mc8 = mc;
                  double posX2 = Minecraft.thePlayer.posX;
                  Minecraft mc9 = mc;
                  double posY2 = Minecraft.thePlayer.posY;
                  Minecraft mc10 = mc;
                  netHandler2.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX2, posY2, Minecraft.thePlayer.posZ, (double)i == (double)(3 + damage) / 0.0625D));
               }
            }
         }
      }

   }

   public void onEnable() {
      this.timer2.reset();
      if(!((Boolean)this.hurtcheck.getValue()).booleanValue()) {
         this.damagePlayer(1);
         this.b2 = true;
      }

      if(((Boolean)this.particle.getValue()).booleanValue()) {
         double x2 = Math.cos(Math.toRadians((double)(Minecraft.thePlayer.rotationYaw + 90.0F)));
         Minecraft mc2 = mc;
         double z2 = Math.sin(Math.toRadians((double)(Minecraft.thePlayer.rotationYaw + 90.0F)));
         double lul = 1.0D;
         double xOffset = (double)MovementInput.moveForward * 1.0D * x2 + (double)MovementInput.moveStrafe * 1.0D * z2;
         double zOffset = (double)MovementInput.moveForward * 1.0D * z2 + (double)MovementInput.moveStrafe * 1.0D * x2;
         Minecraft mc3 = mc;
         double x3 = Minecraft.thePlayer.posX + xOffset;
         Minecraft mc4 = mc;
         double y = Minecraft.thePlayer.posY + 0.42D;
         Minecraft mc5 = mc;
         double z3 = Minecraft.thePlayer.posZ + zOffset;
         Minecraft mc6 = mc;
         Minecraft.theWorld.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, x3, y, z3, 0.0D, 0.0D, 0.0D, new int[0]);
         Minecraft mc7 = mc;
         Minecraft.theWorld.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, x3, y, z3, 0.0D, 0.0D, 0.0D, new int[0]);
         Minecraft mc8 = mc;
         Minecraft.theWorld.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, x3, y, z3, 0.0D, 10.0D, 0.0D, new int[0]);
      }

      this.hypixelCounter = 0;
      this.hypixelCounter2 = 1000;
      Minecraft mc9 = mc;
      Minecraft.thePlayer.motionY = 0.41999998688697815D;
      this.level = 1;
      this.moveSpeed = 0.1D;
      this.lastDist = 0.0D;
   }

   public void onDisable() {
      this.hypixelCounter = 0;
      this.hypixelCounter2 = 100;
      Timer timer = mc.timer;
      Timer.timerSpeed = 1.0F;
      this.level = 1;
      this.moveSpeed = 0.1D;
      this.b2 = false;
      this.lastDist = 0.0D;
   }

   @EventHandler
   private void onUpdate(EventPreUpdate e) {
      if(!this.b2 && ((Boolean)this.hurtcheck.getValue()).booleanValue()) {
         if(this.timer2.hasReached(((Double)this.delay.getValue()).doubleValue())) {
            this.damagePlayer(1);
            this.b2 = true;
            this.b3 = false;
            this.timer2.reset();
         } else {
            Minecraft.thePlayer.motionX = 0.0D;
            Minecraft mc2 = mc;
            Minecraft.thePlayer.motionZ = 0.0D;
            Minecraft mc3 = mc;
            Minecraft.thePlayer.jumpMovementFactor = 0.0F;
            Minecraft mc4 = mc;
            Minecraft.thePlayer.onGround = false;
         }
      }

      if(((Boolean)this.particle.getValue()).booleanValue()) {
         Minecraft mc5 = mc;
         double x = Minecraft.thePlayer.posX;
         Minecraft mc6 = mc;
         double y = Minecraft.thePlayer.posY + 0.42D;
         Minecraft mc7 = mc;
         double z = Minecraft.thePlayer.posZ;
         Minecraft mc8 = mc;
         Minecraft.theWorld.spawnParticle(EnumParticleTypes.CLOUD, x, y, z, 0.0D, 0.0D, 0.0D, new int[0]);
         Minecraft mc9 = mc;
         Minecraft.theWorld.spawnParticle(EnumParticleTypes.CLOUD, x, y, z, 0.0D, 0.0D, 0.0D, new int[0]);
         Minecraft mc10 = mc;
         Minecraft.theWorld.spawnParticle(EnumParticleTypes.CLOUD, x, y, z, 0.0D, 0.0D, 0.0D, new int[0]);
      }

      if(((Boolean)this.bob.getValue()).booleanValue()) {
         Minecraft.thePlayer.cameraYaw = 0.090909086F;
      }

      ++this.counter;
      Minecraft.getMinecraft();
      if(Minecraft.thePlayer.moveForward == 0.0F) {
         Minecraft.getMinecraft();
         if(Minecraft.thePlayer.moveStrafing == 0.0F) {
            Minecraft.getMinecraft();
            EntityPlayerSP thePlayer = Minecraft.thePlayer;
            Minecraft.getMinecraft();
            double x2 = Minecraft.thePlayer.posX + 1.0D;
            Minecraft.getMinecraft();
            double y2 = Minecraft.thePlayer.posY + 1.0D;
            Minecraft.getMinecraft();
            thePlayer.setPosition(x2, y2, Minecraft.thePlayer.posZ + 1.0D);
            Minecraft.getMinecraft();
            EntityPlayerSP thePlayer2 = Minecraft.thePlayer;
            Minecraft.getMinecraft();
            double prevPosX = Minecraft.thePlayer.prevPosX;
            Minecraft.getMinecraft();
            double prevPosY = Minecraft.thePlayer.prevPosY;
            Minecraft.getMinecraft();
            thePlayer2.setPosition(prevPosX, prevPosY, Minecraft.thePlayer.prevPosZ);
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
         EntityPlayerSP thePlayer3 = Minecraft.thePlayer;
         thePlayer3.motionY += 0.5D;
      }

      Minecraft.getMinecraft();
      if(Minecraft.gameSettings.keyBindSneak.pressed) {
         Minecraft.getMinecraft();
         EntityPlayerSP thePlayer4 = Minecraft.thePlayer;
         thePlayer4.motionY -= 0.5D;
      }

      if(this.counter != 1 && this.counter == 2) {
         Minecraft.getMinecraft();
         EntityPlayerSP thePlayer5 = Minecraft.thePlayer;
         Minecraft.getMinecraft();
         double posX = Minecraft.thePlayer.posX;
         Minecraft.getMinecraft();
         double y3 = Minecraft.thePlayer.posY + 1.0E-1D;
         Minecraft.getMinecraft();
         thePlayer5.setPosition(posX, y3, Minecraft.thePlayer.posZ);
         this.counter = 0;
      }

   }

   @EventHandler
   public void onPost(EventPostUpdate e) {
      Minecraft.getMinecraft();
      double posX = Minecraft.thePlayer.posX;
      Minecraft.getMinecraft();
      double xDist = posX - Minecraft.thePlayer.prevPosX;
      Minecraft.getMinecraft();
      double posZ = Minecraft.thePlayer.posZ;
      Minecraft.getMinecraft();
      double zDist = posZ - Minecraft.thePlayer.prevPosZ;
      this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
   }

   @EventHandler
   private void onMove(EventMove e) {
      float forward = MovementInput.moveForward;
      float strafe = MovementInput.moveStrafe;
      float yaw = Minecraft.thePlayer.rotationYaw;
      double mx = Math.cos(Math.toRadians((double)(yaw + 90.0F)));
      double mz = Math.sin(Math.toRadians((double)(yaw + 90.0F)));
      if(forward == 0.0F && strafe == 0.0F) {
         EventMove.x = 0.0D;
         EventMove.z = 0.0D;
      } else if(forward != 0.0F) {
         if(strafe >= 1.0F) {
            float var10000 = yaw + (float)(forward > 0.0F?-45:45);
            strafe = 0.0F;
         } else if(strafe <= -1.0F) {
            float var20 = yaw + (float)(forward > 0.0F?45:-45);
            strafe = 0.0F;
         }

         if(forward > 0.0F) {
            forward = 1.0F;
         } else if(forward < 0.0F) {
            forward = -1.0F;
         }
      }

      if(this.b2) {
         label167: {
            label1672: {
               this.timer.reset();
               if(this.level == 1) {
                  Minecraft.getMinecraft();
                  if(Minecraft.thePlayer.moveForward != 0.0F) {
                     break label167;
                  }

                  Minecraft.getMinecraft();
                  if(Minecraft.thePlayer.moveStrafing != 0.0F) {
                     break label167;
                  }
               }

               if(this.level == 2) {
                  this.level = 3;
                  this.moveSpeed *= 2.1499999D;
                  break label167;
               }

               if(this.level == 3) {
                  this.level = 4;
                  Minecraft mc2 = mc;
                  double difference = (Minecraft.thePlayer.ticksExisted % 2 == 0?0.0103D:0.0123D) * (this.lastDist - MathUtil.getBaseMovementSpeed());
                  this.moveSpeed = this.lastDist - difference;
                  break label167;
               }

               label408: {
                  Minecraft.getMinecraft();
                  WorldClient theWorld = Minecraft.theWorld;
                  Minecraft.getMinecraft();
                  EntityPlayerSP thePlayer = Minecraft.thePlayer;
                  Minecraft.getMinecraft();
                  AxisAlignedBB boundingBox = Minecraft.thePlayer.boundingBox;
                  double x = 0.0D;
                  Minecraft.getMinecraft();
                  if(theWorld.getCollidingBoundingBoxes(thePlayer, boundingBox.offset(0.0D, Minecraft.thePlayer.motionY, 0.0D)).size() <= 0) {
                     Minecraft.getMinecraft();
                     if(!Minecraft.thePlayer.isCollidedVertically) {
                        break label408;
                     }
                  }

                  this.level = 1;
               }

               this.moveSpeed = this.lastDist - this.lastDist / 159.0D;
               break label167;
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
