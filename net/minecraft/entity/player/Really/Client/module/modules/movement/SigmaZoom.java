package net.minecraft.entity.player.Really.Client.module.modules.movement;

import java.awt.Color;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventMove;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPostUpdate;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.management.ModuleManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.module.modules.movement.Speed;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;
import net.minecraft.entity.player.Really.Client.utils.math.MathUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Timer;

public class SigmaZoom extends Module {
   public static Mode mode = new Mode("Mode", "mode", SigmaZoom.FlightMode.values(), SigmaZoom.FlightMode.HypixelZoom);
   private TimerUtil timer = new TimerUtil();
   private double movementSpeed;
   private int hypixelCounter;
   private int hypixelCounter2;
   public static Numbers VanillaSpeed = new Numbers("VanillaSpeed", "VanillaSpeed", Double.valueOf(4.5D), Double.valueOf(1.0D), Double.valueOf(10.0D), Double.valueOf(1.0D));
   int counter;
   int level;
   double moveSpeed;
   double lastDist;
   boolean b2;

   public int V() {
      return Minecraft.thePlayer.isPotionActive(Potion.jump)?Minecraft.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1:0;
   }

   public SigmaZoom() {
      super("SigmaZoom", new String[]{"ZoomFly", "BoostFly"}, ModuleType.Movement);
      this.setColor((new Color(158, 114, 243)).getRGB());
      this.addValues(new Value[]{mode, VanillaSpeed});
   }

   public void damagePlayer(int damage) {
      for(int index = 0; index < 49; ++index) {
         Minecraft var10000 = mc;
         NetworkManager var3 = Minecraft.thePlayer.sendQueue.getNetworkManager();
         Minecraft var10003 = mc;
         Minecraft var10004 = mc;
         double var10 = Minecraft.thePlayer.posY + 0.06249D;
         Minecraft var10005 = mc;
         var3.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, var10, Minecraft.thePlayer.posZ, false));
         Minecraft var4 = mc;
         NetworkManager var5 = Minecraft.thePlayer.sendQueue.getNetworkManager();
         var10003 = mc;
         Minecraft var11 = mc;
         var10005 = mc;
         var5.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, false));
      }

      Minecraft var6 = mc;
      NetworkManager var7 = Minecraft.thePlayer.sendQueue.getNetworkManager();
      Minecraft var9 = mc;
      Minecraft var12 = mc;
      Minecraft var14 = mc;
      var7.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, true));
   }

   public void onEnable() {
      if(mode.getValue() == SigmaZoom.FlightMode.Hypixel || mode.getValue() == SigmaZoom.FlightMode.HypixelZoom) {
         if(mode.getValue() == SigmaZoom.FlightMode.HypixelZoom) {
            this.damagePlayer(1);
         }

         this.hypixelCounter = 0;
         this.hypixelCounter2 = 1000;
         Minecraft.thePlayer.motionY = 0.41999999999875D + (double)this.V() * 0.1D;
      }

      if(ModuleManager.getModuleByName("Scaffold").isEnabled()) {
         ModuleManager.getModuleByName("Scaffold").setEnabled(false);
      }

      this.level = 1;
      this.moveSpeed = 0.1D;
      this.b2 = true;
      this.lastDist = 0.0D;
   }

   public void onDisable() {
      if(mode.getValue() == SigmaZoom.FlightMode.Area51) {
         Minecraft.thePlayer.motionX = 0.0D;
         Minecraft.thePlayer.motionZ = 0.0D;
      }

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
      this.setSuffix(mode.getValue());
      if(mode.getValue() == SigmaZoom.FlightMode.Guardian) {
         Timer timer = mc.timer;
         Timer.timerSpeed = 1.7F;
         if(!Minecraft.thePlayer.onGround && Minecraft.thePlayer.ticksExisted % 2 == 0) {
            Minecraft.thePlayer.motionY = 0.04D;
         }

         Minecraft var10000 = mc;
         if(Minecraft.gameSettings.keyBindJump.pressed) {
            EntityPlayerSP thePlayer = Minecraft.thePlayer;
            ++thePlayer.motionY;
         }

         var10000 = mc;
         if(Minecraft.gameSettings.keyBindSneak.pressed) {
            EntityPlayerSP thePlayer2 = Minecraft.thePlayer;
            ++thePlayer2.motionY;
         }
      } else if(mode.getValue() == SigmaZoom.FlightMode.Vanilla) {
         EntityPlayerSP thePlayer8 = Minecraft.thePlayer;
         double motionY;
         if(Minecraft.thePlayer.movementInput.jump) {
            motionY = 1.0D;
         } else {
            motionY = Minecraft.thePlayer.movementInput.sneak?-1.0D:0.0D;
         }

         thePlayer8.motionY = motionY;
         if(Minecraft.thePlayer.moving()) {
            Minecraft.thePlayer.setSpeed(((Double)((Number)VanillaSpeed.getValue())).doubleValue());
         } else {
            Minecraft mc12 = mc;
            Minecraft.thePlayer.setSpeed(0.0D);
         }
      } else if(mode.getValue() == SigmaZoom.FlightMode.Area51) {
         Minecraft.thePlayer.motionY = Minecraft.thePlayer.movementInput.jump?1.0D:(Minecraft.thePlayer.movementInput.sneak?-1.0D:0.0D);
      } else if(mode.getValue() != SigmaZoom.FlightMode.Hypixel && mode.getValue() != SigmaZoom.FlightMode.HypixelZoom) {
         if(mode.getValue() == SigmaZoom.FlightMode.OldGuardianLongJumpFly && Minecraft.thePlayer.moving()) {
            Client instance = Client.instance;
            Client.getModuleManager();
            if(!ModuleManager.getModuleByClass(Speed.class).isEnabled()) {
               if(Minecraft.thePlayer.isAirBorne) {
                  if(Minecraft.thePlayer.ticksExisted % 12 == 0 && Minecraft.theWorld.getBlockState(new BlockPos(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ)).getBlock() instanceof BlockAir) {
                     Minecraft.thePlayer.setSpeed(6.5D);
                     Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 1.0E-9D, Minecraft.thePlayer.posZ, Minecraft.thePlayer.onGround));
                     Minecraft.thePlayer.motionY = 0.455D;
                  } else {
                     Minecraft.thePlayer.setSpeed((double)((float)Math.sqrt(Minecraft.thePlayer.motionX * Minecraft.thePlayer.motionX + Minecraft.thePlayer.motionZ * Minecraft.thePlayer.motionZ)));
                  }
               } else {
                  Minecraft.thePlayer.motionX = 0.0D;
                  Minecraft.thePlayer.motionZ = 0.0D;
               }

               if(Minecraft.thePlayer.movementInput.jump) {
                  Minecraft.thePlayer.motionY = 0.85D;
               } else if(Minecraft.thePlayer.movementInput.sneak) {
                  Minecraft.thePlayer.motionY = -0.85D;
               }
            }
         }
      } else {
         ++this.counter;
         if(Minecraft.thePlayer.moveForward == 0.0F && Minecraft.thePlayer.moveStrafing == 0.0F) {
            EntityPlayerSP thePlayer4 = Minecraft.thePlayer;
            double n = Minecraft.thePlayer.posX + 1.0D;
            double n2 = Minecraft.thePlayer.posY + 1.0D;
            thePlayer4.setPosition(n, n2, Minecraft.thePlayer.posZ + 1.0D);
            EntityPlayerSP thePlayer5 = Minecraft.thePlayer;
            double prevPosX = Minecraft.thePlayer.prevPosX;
            double prevPosY = Minecraft.thePlayer.prevPosY;
            thePlayer5.setPosition(prevPosX, prevPosY, Minecraft.thePlayer.prevPosZ);
            Minecraft.thePlayer.motionX = 0.0D;
            Minecraft.thePlayer.motionZ = 0.0D;
         }

         Minecraft.thePlayer.motionY = 0.0D;
         Minecraft.getMinecraft();
         if(Minecraft.gameSettings.keyBindJump.pressed) {
            EntityPlayerSP thePlayer6 = Minecraft.thePlayer;
            thePlayer6.motionY += 0.5D;
         }

         Minecraft.getMinecraft();
         if(Minecraft.gameSettings.keyBindSneak.pressed) {
            EntityPlayerSP thePlayer7 = Minecraft.thePlayer;
            thePlayer7.motionY -= 0.5D;
         }

         if(this.counter != 1 && this.counter == 2) {
            EntityPlayerSP thePlayer8 = Minecraft.thePlayer;
            double posX = Minecraft.thePlayer.posX;
            double n3 = Minecraft.thePlayer.posY + 1.0E-1D;
            thePlayer8.setPosition(posX, n3, Minecraft.thePlayer.posZ);
            this.counter = 0;
         }
      }

   }

   @EventHandler
   public void onPost(EventPostUpdate e) {
      if(mode.getValue() == SigmaZoom.FlightMode.Hypixel || mode.getValue() == SigmaZoom.FlightMode.HypixelZoom) {
         double posX = Minecraft.thePlayer.posX;
         double xDist = posX - Minecraft.thePlayer.prevPosX;
         double posZ = Minecraft.thePlayer.posZ;
         double zDist = posZ - Minecraft.thePlayer.prevPosZ;
         this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
      }

   }

   @EventHandler
   private void onMove(EventMove e) {
      if(mode.getValue() == SigmaZoom.FlightMode.Hypixel || mode.getValue() == SigmaZoom.FlightMode.HypixelZoom) {
         float forward = MovementInput.moveForward;
         float strafe = MovementInput.moveStrafe;
         float yaw = Minecraft.thePlayer.rotationYaw;
         double mx = Math.cos(Math.toRadians((double)(yaw + 90.0F)));
         double mz = Math.sin(Math.toRadians((double)(yaw + 90.0F)));
         if(forward == 0.0F && strafe == 0.0F) {
            EventMove.x = 0.0D;
            EventMove.z = 0.0D;
         }

         if(this.b2) {
            if(this.level != 1 || Minecraft.thePlayer.moveForward == 0.0F && Minecraft.thePlayer.moveStrafing == 0.0F) {
               if(this.level == 2) {
                  this.level = 3;
                  this.moveSpeed *= 2.1399D;
               } else if(this.level == 3) {
                  this.level = 4;
                  double difference = (Minecraft.thePlayer.ticksExisted % 2 == 0?0.0103D:0.0123D) * (this.lastDist - MathUtil.getBaseMovementSpeed());
                  this.moveSpeed = this.lastDist - difference;
               } else {
                  WorldClient theWorld = Minecraft.theWorld;
                  EntityPlayerSP thePlayer = Minecraft.thePlayer;
                  AxisAlignedBB boundingBox = Minecraft.thePlayer.boundingBox;
                  double n2 = 0.0D;
                  if(theWorld.getCollidingBoundingBoxes(thePlayer, boundingBox.offset(0.0D, Minecraft.thePlayer.motionY, 0.0D)).size() > 0 || Minecraft.thePlayer.isCollidedVertically) {
                     this.level = 1;
                  }

                  this.moveSpeed = this.lastDist - this.lastDist / 159.0D;
               }
            } else {
               this.level = 2;
               if(Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
                  int n = Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier() + 1;
               } else {
                  int n = 0;
               }

               double boost = Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)?1.56D:2.034D;
               this.moveSpeed = boost * MathUtil.getBaseMovementSpeed();
            }

            double moveSpeed = mode.getValue() == SigmaZoom.FlightMode.HypixelZoom?Math.max(this.moveSpeed, MathUtil.getBaseMovementSpeed()):MathUtil.getBaseMovementSpeed();
            this.moveSpeed = moveSpeed;
            if(strafe == 0.0F) {
               EventMove.x = (double)forward * this.moveSpeed * mx + (double)strafe * this.moveSpeed * mz;
               EventMove.z = (double)forward * this.moveSpeed * mz - (double)strafe * this.moveSpeed * mx;
            } else if(strafe != 0.0F) {
               Minecraft.thePlayer.setMoveSpeed(e, this.moveSpeed);
            }

            if(forward == 0.0F && strafe == 0.0F) {
               EventMove.x = 0.0D;
               EventMove.z = 0.0D;
            }
         }
      }

   }

   double getBaseMoveSpeed() {
      double baseSpeed = 0.275D;
      if(Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
         int amplifier = Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
         baseSpeed *= 1.0D + 0.2D * (double)(amplifier + 1);
      }

      return baseSpeed;
   }

   public static enum FlightMode {
      HypixelZoom,
      Hypixel,
      OldGuardianLongJumpFly,
      Area51,
      Vanilla,
      Guardian;
   }
}
