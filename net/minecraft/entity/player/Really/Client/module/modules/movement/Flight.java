package net.minecraft.entity.player.Really.Client.module.modules.movement;

import java.awt.Color;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventMove;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPacketRecieve;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPostUpdate;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.management.ModuleManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.PlayerUtil;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;
import net.minecraft.entity.player.Really.Client.utils.math.MathUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;

public class Flight extends Module {
   public Mode mode = new Mode("Mode", "Mode", Flight.FlyMode.values(), Flight.FlyMode.Vanilla);
   private Option lagcheck = new Option("LagCheck", "LagCheck", Boolean.valueOf(true));
   private Option dragonvalue = new Option("Dragon", "Dragon", Boolean.valueOf(false));
   private static Numbers zoomboost = new Numbers("BoostTime", "BoostTime", Double.valueOf(5.0D), Double.valueOf(0.0D), Double.valueOf(20.0D), Double.valueOf(0.2D));
   private static Numbers timerboost = new Numbers("timerboost", "timerboost", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(5.0D), Double.valueOf(0.2D));
   private static Numbers groundboost = new Numbers("Boost", "Boost", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(5.0D), Double.valueOf(0.2D));
   private static Numbers boostbegintime = new Numbers("boost begin time", "boost begin time", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(10.0D), Double.valueOf(1.0D));
   private static Numbers Packet = new Numbers("DamagePacket", "DamagePacket", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(500.0D), Double.valueOf(1.0D));
   private Option par = new Option("Particle", "Particle", Boolean.valueOf(false));
   private Option GroundPacket = new Option("GroundPacket", "GroundPacket", Boolean.valueOf(true));
   private Option WaittingTime = new Option("WaittingTime", "WaittingTime", Boolean.valueOf(false));
   private Option UHC = new Option("UHC", "UHC", Boolean.valueOf(false));
   private Option bob = new Option("Bobbing", "Bobbing", Boolean.valueOf(false));
   private TimerUtil time = new TimerUtil();
   private TimerUtil BeginTimer = new TimerUtil();
   private TimerUtil kickTimer = new TimerUtil();
   private double movementSpeed;
   private int hypixelCounter;
   private EntityDragon dragon;
   private int zcounter;
   private int zboost;
   private int hypixelCounter2;
   int counter;
   int level;
   private double flyHeight;
   private boolean dragoncrea = false;
   private int zoom;
   private int packetCounter;
   private TimerUtil deactivationDelay = new TimerUtil();
   double moveSpeed;
   double lastDist;
   boolean b2;
   boolean fly;

   public Flight() {
      super("Flight", new String[]{"fly", "angel"}, ModuleType.Movement);
      this.setColor((new Color(158, 114, 243)).getRGB());
      this.addValues(new Value[]{this.mode, timerboost, this.lagcheck, this.dragonvalue, this.par, zoomboost, this.UHC, Packet, groundboost, boostbegintime, this.GroundPacket, this.WaittingTime, this.bob});
   }

   public void goToGround() {
      if(this.flyHeight <= 300.0D) {
         Minecraft var10000 = mc;
         double minY = Minecraft.thePlayer.posY - this.flyHeight;
         if(minY > 0.0D) {
            var10000 = mc;
            double y = Minecraft.thePlayer.posY;

            while(y > minY) {
               y -= 8.0D;
               if(y < minY) {
                  y = minY;
               }

               Minecraft var10002 = mc;
               Minecraft var10004 = mc;
               C03PacketPlayer.C04PacketPlayerPosition packet = new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, y, Minecraft.thePlayer.posZ, true);
               var10000 = mc;
               Minecraft.thePlayer.sendQueue.addToSendQueue(packet);
            }

            y = minY;

            while(true) {
               Minecraft var10001 = mc;
               if(y >= Minecraft.thePlayer.posY) {
                  return;
               }

               y += 8.0D;
               var10001 = mc;
               if(y > Minecraft.thePlayer.posY) {
                  var10000 = mc;
                  y = Minecraft.thePlayer.posY;
               }

               Minecraft var13 = mc;
               Minecraft var14 = mc;
               C03PacketPlayer.C04PacketPlayerPosition packet = new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, y, Minecraft.thePlayer.posZ, true);
               var10000 = mc;
               Minecraft.thePlayer.sendQueue.addToSendQueue(packet);
            }
         }
      }
   }

   public void damagePlayer() {
      if(Minecraft.thePlayer.onGround) {
         for(int index = 0; (double)index < 49.0D + ((Double)Packet.getValue()).doubleValue(); ++index) {
            Minecraft var10000 = mc;
            NetworkManager var2 = Minecraft.thePlayer.sendQueue.getNetworkManager();
            Minecraft var10003 = mc;
            Minecraft var10004 = mc;
            double var9 = Minecraft.thePlayer.posY + 0.06249D;
            Minecraft var10005 = mc;
            var2.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, var9, Minecraft.thePlayer.posZ, false));
            Minecraft var3 = mc;
            NetworkManager var4 = Minecraft.thePlayer.sendQueue.getNetworkManager();
            var10003 = mc;
            Minecraft var10 = mc;
            var10005 = mc;
            var4.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, false));
         }

         Minecraft var5 = mc;
         NetworkManager var6 = Minecraft.thePlayer.sendQueue.getNetworkManager();
         Minecraft var8 = mc;
         Minecraft var11 = mc;
         Minecraft var13 = mc;
         var6.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, true));
      }

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
         if(ModuleManager.getModuleByName("Flight").isEnabled()) {
            var10000 = Client.instance;
            Client.getModuleManager();
            ModuleManager.getModuleByName("Flight").setEnabled(false);
         }
      }

   }

   public void onEnable() {
      this.zboost = 1;
      if(this.mode.getValue() == Flight.FlyMode.longjump) {
         if(((Boolean)this.UHC.getValue()).booleanValue()) {
            this.damagePlayer(2);
         } else {
            this.damagePlayer();
         }

         Minecraft var10000 = mc;
         if(Minecraft.thePlayer.onGround) {
            var10000 = mc;
            Minecraft.thePlayer.motionY = 0.41999998688697815D;
         }
      }

      this.zoom = (int)(((Double)zoomboost.getValue()).doubleValue() * 10.0D);
      float forward = MovementInput.moveForward;
      float strafe = MovementInput.moveStrafe;
      if(this.mode.getValue() == Flight.FlyMode.Hypixel) {
         this.hypixelCounter = 0;
         this.hypixelCounter2 = 1000;
      }

      if(this.mode.getValue() == Flight.FlyMode.HypixelZoom) {
         if(((Boolean)this.UHC.getValue()).booleanValue()) {
            this.damagePlayer(2);
         } else {
            this.damagePlayer();
         }

         this.fly = true;
         (new Timer()).schedule(new TimerTask() {
            public void run() {
               Flight.this.fly = false;
               if(((Boolean)Flight.this.GroundPacket.getValue()).booleanValue()) {
                  Minecraft var10000 = Flight.mc;
                  EventMove.setY(Minecraft.thePlayer.motionY = 0.42D);
               }

               this.cancel();
            }
         }, 240L + ((Double)boostbegintime.getValue()).longValue() * 100L);
         this.hypixelCounter = 0;
         this.hypixelCounter2 = 1000;
      }

      this.level = 1;
      this.dragoncrea = false;
      this.moveSpeed = 0.1D;
      this.b2 = true;
      this.lastDist = 0.0D;
   }

   public void onDisable() {
      this.hypixelCounter = 0;
      if(((Boolean)this.dragonvalue.getValue()).booleanValue()) {
         Minecraft var10000 = mc;
         Minecraft.theWorld.removeEntity(this.dragon);
      }

      Minecraft var7 = mc;
      double posX = Minecraft.thePlayer.posX;
      var7 = mc;
      double posY = Minecraft.thePlayer.posY;
      var7 = mc;
      double posZ = Minecraft.thePlayer.posZ;
      this.hypixelCounter2 = 100;
      net.minecraft.util.Timer var10 = mc.timer;
      net.minecraft.util.Timer.timerSpeed = 1.0F;
      this.level = 1;
      this.moveSpeed = 0.1D;
      this.b2 = false;
      this.lastDist = 0.0D;
      this.zboost = 1;
   }

   public void updateFlyHeight() {
      double h = 1.0D;
      Minecraft var10000 = mc;
      AxisAlignedBB box = Minecraft.thePlayer.getEntityBoundingBox().expand(0.0625D, 0.0625D, 0.0625D);
      this.flyHeight = 0.0D;

      while(true) {
         Minecraft var10001 = mc;
         if(this.flyHeight >= Minecraft.thePlayer.posY) {
            break;
         }

         AxisAlignedBB nextBox = box.offset(0.0D, -this.flyHeight, 0.0D);
         var10000 = mc;
         if(Minecraft.theWorld.checkBlockCollision(nextBox)) {
            if(h < 0.0625D) {
               break;
            }

            this.flyHeight -= h;
            h /= 2.0D;
         }

         this.flyHeight += h;
      }

   }

   public static boolean isOnGround(double height) {
      return !Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, Minecraft.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty();
   }

   @EventHandler
   private void onUpdate(final EventPreUpdate e) {
      this.setSuffix(this.mode.getValue());
      if(((Boolean)this.bob.getValue()).booleanValue()) {
         Minecraft.thePlayer.cameraYaw = 0.090909086F;
      }

      double speed = Math.max(3.0D, this.getBaseMoveSpeed());
      if(this.mode.getValue() == Flight.FlyMode.Motion) {
         Minecraft var10000 = mc;
         Minecraft.thePlayer.onGround = false;
         e.setOnground(isOnGround(0.001D));
         var10000 = mc;
         if(Minecraft.thePlayer.moving()) {
            var10000 = mc;
            if(Minecraft.thePlayer.hurtTime > 15) {
               e.setCancelled(false);
            } else {
               (new Timer()).schedule(new TimerTask() {
                  public void run() {
                     e.setCancelled(true);
                     this.cancel();
                  }
               }, 10L);
            }
         } else {
            e.setCancelled(false);
            var10000 = mc;
            Minecraft.thePlayer.motionX = 0.0D;
            var10000 = mc;
            Minecraft.thePlayer.motionY = 0.0D;
            var10000 = mc;
            Minecraft.thePlayer.motionZ = 0.0D;
         }

         var10000 = mc;
         if(Minecraft.thePlayer.movementInput.jump) {
            var10000 = mc;
            Minecraft.thePlayer.motionY = speed * 0.6D;
         } else {
            var10000 = mc;
            if(Minecraft.thePlayer.movementInput.sneak) {
               var10000 = mc;
               Minecraft.thePlayer.motionY = -speed * 0.6D;
            } else {
               var10000 = mc;
               Minecraft.thePlayer.motionY = 0.0D;
            }
         }

         this.updateFlyHeight();
         var10000 = mc;
         Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
      } else if(this.mode.getValue() == Flight.FlyMode.Vanilla) {
         Minecraft var15 = mc;
         EntityPlayerSP var16 = Minecraft.thePlayer;
         Minecraft var10001 = mc;
         double var27;
         if(Minecraft.thePlayer.movementInput.jump) {
            var27 = 1.0D;
         } else {
            Minecraft var28 = mc;
            var27 = Minecraft.thePlayer.movementInput.sneak?-1.0D:0.0D;
         }

         var16.motionY = var27;
         Minecraft var17 = mc;
         if(Minecraft.thePlayer.moving()) {
            var17 = mc;
            Minecraft.thePlayer.setSpeed(3.75D);
         } else {
            var17 = mc;
            Minecraft.thePlayer.setSpeed(0.0D);
         }

         var17 = mc;
         Minecraft.thePlayer.capabilities.allowFlying = true;
      } else if(this.mode.getValue() != Flight.FlyMode.Hypixel && this.mode.getValue() != Flight.FlyMode.HypixelZoom) {
         if(this.mode.getValue() == Flight.FlyMode.longjump) {
            Minecraft var24 = mc;
            Minecraft var31 = mc;
            Minecraft var35 = mc;
            Minecraft var39 = mc;
            Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ);
            switch(this.zcounter) {
            case 2:
               var24 = mc;
               var31 = mc;
               var35 = mc;
               double var37 = Minecraft.thePlayer.posY + 1.0E-5D;
               var39 = mc;
               Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, var37, Minecraft.thePlayer.posZ);
               this.counter = 0;
            case 1:
            default:
               ++this.zcounter;
               var24 = mc;
               Minecraft.thePlayer.motionY = 0.0D;
            }
         }
      } else {
         Minecraft var21 = mc;
         Minecraft var29 = mc;
         Minecraft var10002 = mc;
         Minecraft var10003 = mc;
         Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ);
         switch(this.counter) {
         case 2:
            var21 = mc;
            var29 = mc;
            var10002 = mc;
            double var34 = Minecraft.thePlayer.posY + 1.0E-5D;
            var10003 = mc;
            Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, var34, Minecraft.thePlayer.posZ);
            this.counter = 0;
         case 1:
         default:
            ++this.counter;
            var21 = mc;
            Minecraft.thePlayer.motionY = 0.0D;
         }
      }

   }

   @EventHandler
   public void onEvent(EventPostUpdate e) {
      if(this.mode.getValue() == Flight.FlyMode.HypixelZoom && ((Boolean)this.WaittingTime.getValue()).booleanValue() && this.fly) {
         Minecraft var10000 = mc;
         Minecraft.thePlayer.motionX = 0.0D;
         var10000 = mc;
         Minecraft.thePlayer.motionZ = 0.0D;
         var10000 = mc;
         Minecraft.thePlayer.jumpMovementFactor = 0.0F;
         var10000 = mc;
         Minecraft.thePlayer.onGround = false;
      }

      if(((Boolean)this.dragonvalue.getValue()).booleanValue() && !this.dragoncrea) {
         this.dragoncrea = true;
         Minecraft var10003 = mc;
         this.dragon = new EntityDragon(Minecraft.theWorld);
         Minecraft var56 = mc;
         Minecraft.theWorld.addEntityToWorld(666, this.dragon);
         var56 = mc;
         Minecraft.thePlayer.ridingEntity = this.dragon;
      } else if(((Boolean)this.dragonvalue.getValue()).booleanValue()) {
         Minecraft var49 = mc;
         double posX4 = Minecraft.thePlayer.posX;
         Minecraft var10001 = mc;
         double posX = posX4 - (double)(MathHelper.cos(Minecraft.thePlayer.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
         var49 = mc;
         double posY = Minecraft.thePlayer.posY;
         var49 = mc;
         double posZ2 = Minecraft.thePlayer.posZ;
         var10001 = mc;
         double posZ = posZ2 - (double)(MathHelper.sin(Minecraft.thePlayer.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
         float f = 0.4F;
         var49 = mc;
         float n2 = -MathHelper.sin(Minecraft.thePlayer.rotationYaw / 180.0F * 3.1415927F);
         var10001 = mc;
         double motionX = (double)(n2 * MathHelper.cos(Minecraft.thePlayer.rotationPitch / 180.0F * 3.1415927F) * 0.4F);
         var49 = mc;
         float cos = MathHelper.cos(Minecraft.thePlayer.rotationYaw / 180.0F * 3.1415927F);
         var10001 = mc;
         double motionZ = (double)(cos * MathHelper.cos(Minecraft.thePlayer.rotationPitch / 180.0F * 3.1415927F) * 0.4F);
         var49 = mc;
         double motionY = (double)(-MathHelper.sin((Minecraft.thePlayer.rotationPitch + 2.0F) / 180.0F * 3.1415927F) * 0.4F);
         double xCoord = posX + motionX;
         double var55 = posY + motionY;
         double zCoord = posZ + motionZ;
         var10001 = mc;
         this.dragon.rotationPitch = Minecraft.thePlayer.rotationPitch;
         var10001 = mc;
         this.dragon.rotationYaw = Minecraft.thePlayer.rotationYawHead - 180.0F;
         var10001 = mc;
         this.dragon.setRotationYawHead(Minecraft.thePlayer.rotationYawHead);
         Minecraft var10002 = mc;
         this.dragon.setPosition(xCoord, Minecraft.thePlayer.posY - 2.0D, zCoord);
      }

      if(((Boolean)this.par.getValue()).booleanValue()) {
         Minecraft var58 = mc;
         double posX4 = Minecraft.thePlayer.posX;
         Minecraft var73 = mc;
         double posX = posX4 - (double)(MathHelper.cos(Minecraft.thePlayer.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
         var58 = mc;
         double posY = Minecraft.thePlayer.posY;
         var58 = mc;
         double posZ2 = Minecraft.thePlayer.posZ;
         var73 = mc;
         double posZ = posZ2 - (double)(MathHelper.sin(Minecraft.thePlayer.rotationYaw / 180.0F * 3.1415927F) * 0.16F);
         float f = 0.4F;
         var58 = mc;
         float n2 = -MathHelper.sin(Minecraft.thePlayer.rotationYaw / 180.0F * 3.1415927F);
         var73 = mc;
         double motionX = (double)(n2 * MathHelper.cos(Minecraft.thePlayer.rotationPitch / 180.0F * 3.1415927F) * 0.4F);
         var58 = mc;
         float cos = MathHelper.cos(Minecraft.thePlayer.rotationYaw / 180.0F * 3.1415927F);
         var73 = mc;
         double motionZ = (double)(cos * MathHelper.cos(Minecraft.thePlayer.rotationPitch / 180.0F * 3.1415927F) * 0.4F);
         var58 = mc;
         double motionY = (double)(-MathHelper.sin((Minecraft.thePlayer.rotationPitch + 2.0F) / 180.0F * 3.1415927F) * 0.4F);

         for(int i = 0; i < 90; ++i) {
            var58 = mc;
            WorldClient theWorld = Minecraft.theWorld;
            EnumParticleTypes particleType = i % 4 == 0?EnumParticleTypes.CRIT_MAGIC:((new Random()).nextBoolean()?EnumParticleTypes.HEART:EnumParticleTypes.ENCHANTMENT_TABLE);
            double xCoord = posX + motionX;
            double yCoord = posY + motionY;
            double zCoord = posZ + motionZ;
            var58 = mc;
            double motionX2 = Minecraft.thePlayer.motionX;
            var58 = mc;
            double motionY2 = Minecraft.thePlayer.motionY;
            Minecraft var10007 = mc;
            theWorld.spawnParticle(particleType, xCoord, yCoord, zCoord, motionX2, motionY2, Minecraft.thePlayer.motionZ, new int[0]);
         }
      }

   }

   @EventHandler
   public void onPost(EventPostUpdate e) {
      if(this.mode.getValue() == Flight.FlyMode.longjump) {
         Minecraft var10000 = mc;
         Minecraft var10001 = mc;
         double xDist = Minecraft.thePlayer.posX - Minecraft.thePlayer.prevPosX;
         var10000 = mc;
         var10001 = mc;
         double zDist = Minecraft.thePlayer.posZ - Minecraft.thePlayer.prevPosZ;
         this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
      }

      if(this.mode.getValue() == Flight.FlyMode.Hypixel || this.mode.getValue() == Flight.FlyMode.HypixelZoom) {
         Minecraft.getMinecraft();
         double var9 = Minecraft.thePlayer.posX;
         Minecraft.getMinecraft();
         double xDist = var9 - Minecraft.thePlayer.prevPosX;
         Minecraft.getMinecraft();
         var9 = Minecraft.thePlayer.posZ;
         Minecraft.getMinecraft();
         double zDist = var9 - Minecraft.thePlayer.prevPosZ;
         this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
      }

   }

   @EventHandler
   private void onMove(EventMove e) {
      if(this.mode.getValue() != Flight.FlyMode.Hypixel && this.mode.getValue() != Flight.FlyMode.HypixelZoom) {
         if(this.mode.getValue() == Flight.FlyMode.longjump) {
            Minecraft.thePlayer.cameraYaw = 0.1F;
            Minecraft var30 = mc;
            float yaw = Minecraft.thePlayer.rotationYaw;
            if(this.zboost == 1 && PlayerUtil.isMoving()) {
               this.zboost = 2;
               var30 = mc;
               double boost = Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)?1.56D:2.034D;
               this.moveSpeed = boost * this.getBaseMoveSpeed();
            } else if(this.zboost == 2) {
               this.zboost = 3;
               this.moveSpeed *= ((Double)timerboost.getValue()).doubleValue();
            } else if(this.zboost == 3) {
               this.zboost = 4;
               var30 = mc;
               double change = (Minecraft.thePlayer.ticksExisted % 2 == 0?0.0103D:0.0123D) * (this.lastDist - this.getBaseMoveSpeed());
               this.moveSpeed = this.lastDist - change;
            } else {
               this.moveSpeed = this.lastDist - this.lastDist / 150.0D;
            }

            this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
            var30 = mc;
            Minecraft.thePlayer.setMoveSpeed(e, this.moveSpeed);
         } else if(this.mode.getValue() == Flight.FlyMode.Motion) {
            float forward = MovementInput.moveForward;
            float strafe = MovementInput.moveStrafe;
            Minecraft var34 = mc;
            float yaw = Minecraft.thePlayer.rotationYaw;
            if(forward == 0.0F && strafe == 0.0F) {
               EventMove.x = 0.0D;
               EventMove.z = 0.0D;
            } else if(forward != 0.0F) {
               if(strafe >= 1.0F) {
                  float var35 = yaw + (float)(forward > 0.0F?-45:45);
                  strafe = 0.0F;
               } else if(strafe <= -1.0F) {
                  float var36 = yaw + (float)(forward > 0.0F?45:-45);
                  strafe = 0.0F;
               }

               if(forward > 0.0F) {
                  forward = 1.0F;
               } else if(forward < 0.0F) {
                  forward = -1.0F;
               }
            }

            var34 = mc;
            Minecraft.thePlayer.onGround = false;
            var34 = mc;
            if(Minecraft.thePlayer.moving()) {
               var34 = mc;
               Minecraft.thePlayer.motionY = -0.5D;
               this.moveSpeed = 3.75D;
            } else {
               this.moveSpeed = 0.0D;
            }

            this.moveSpeed = Math.max(this.moveSpeed, MathUtil.getBaseMovementSpeed());
            var34 = mc;
            Minecraft.thePlayer.setMoveSpeed(e, this.moveSpeed);
         }
      } else if(!this.fly) {
         if(this.BeginTimer.hasReached(((Double)boostbegintime.getValue()).doubleValue() * 500.0D)) {
            if(this.zoom > 0 && ((Double)timerboost.getValue()).floatValue() > 0.0F) {
               net.minecraft.util.Timer var23 = mc.timer;
               net.minecraft.util.Timer.timerSpeed = 1.0F + ((Double)timerboost.getValue()).floatValue();
               if(this.zoom < 5) {
                  float percent = (float)(this.zoom / 10);
                  if((double)percent > 1.0D) {
                     percent = 1.0F;
                  }

                  var23 = mc.timer;
                  net.minecraft.util.Timer.timerSpeed = 1.0F + ((Double)timerboost.getValue()).floatValue() * percent;
               }
            } else {
               net.minecraft.util.Timer var10000 = mc.timer;
               net.minecraft.util.Timer.timerSpeed = 1.0F;
            }
         }

         --this.zoom;
         float forward = MovementInput.moveForward;
         float strafe = MovementInput.moveStrafe;
         Minecraft var25 = mc;
         float yaw = Minecraft.thePlayer.rotationYaw;
         double mx = Math.cos(Math.toRadians((double)(yaw + 90.0F)));
         double mz = Math.sin(Math.toRadians((double)(yaw + 90.0F)));
         if(forward == 0.0F && strafe == 0.0F) {
            EventMove.x = 0.0D;
            EventMove.z = 0.0D;
         } else if(forward != 0.0F) {
            if(strafe >= 1.0F) {
               yaw += (float)(forward > 0.0F?-45:45);
               strafe = 0.0F;
            } else if(strafe <= -1.0F) {
               yaw += (float)(forward > 0.0F?45:-45);
               strafe = 0.0F;
            }

            if(forward > 0.0F) {
               forward = 1.0F;
            } else if(forward < 0.0F) {
               forward = -1.0F;
            }
         }

         if(this.b2) {
            label349: {
               label3429: {
                  if(this.level == 1) {
                     Minecraft.getMinecraft();
                     if(Minecraft.thePlayer.moveForward != 0.0F) {
                        break label349;
                     }

                     Minecraft.getMinecraft();
                     if(Minecraft.thePlayer.moveStrafing != 0.0F) {
                        break label349;
                     }
                  }

                  if(this.level == 2) {
                     this.level = 3;
                     this.moveSpeed *= 2.7899999D + ((Double)groundboost.getValue()).doubleValue();
                     break label349;
                  }

                  if(this.level == 3) {
                     this.level = 4;
                     var25 = mc;
                     double difference = (Minecraft.thePlayer.ticksExisted % 2 == 0?0.0103D:0.0123D) * (this.lastDist - MathUtil.getBaseMovementSpeed());
                     this.moveSpeed = this.lastDist - difference;
                     break label349;
                  }

                  label490: {
                     Minecraft.getMinecraft();
                     WorldClient var26 = Minecraft.theWorld;
                     Minecraft.getMinecraft();
                     EntityPlayerSP var10001 = Minecraft.thePlayer;
                     Minecraft.getMinecraft();
                     AxisAlignedBB var10002 = Minecraft.thePlayer.boundingBox;
                     Minecraft.getMinecraft();
                     if(var26.getCollidingBoundingBoxes(var10001, var10002.offset(0.0D, Minecraft.thePlayer.motionY, 0.0D)).size() <= 0) {
                        Minecraft.getMinecraft();
                        if(!Minecraft.thePlayer.isCollidedVertically) {
                           break label490;
                        }
                     }

                     this.level = 1;
                  }

                  this.moveSpeed = this.lastDist - this.lastDist / 159.0D;
                  break label349;
               }
            }

            this.moveSpeed = this.mode.getValue() == Flight.FlyMode.HypixelZoom?Math.max(this.moveSpeed, MathUtil.getBaseMovementSpeed()):MathUtil.getBaseMovementSpeed();
            EventMove.x = (double)forward * this.moveSpeed * Math.cos(Math.toRadians((double)(yaw + 90.0F))) + (double)strafe * this.moveSpeed * Math.sin(Math.toRadians((double)(yaw + 90.0F)));
            EventMove.z = (double)forward * this.moveSpeed * Math.sin(Math.toRadians((double)(yaw + 90.0F))) - (double)strafe * this.moveSpeed * Math.cos(Math.toRadians((double)(yaw + 90.0F)));
            if(forward == 0.0F && strafe == 0.0F) {
               EventMove.x = 0.0D;
               EventMove.z = 0.0D;
            }
         }
      }

   }

   double getBaseMoveSpeed() {
      double baseSpeed = 0.275D;
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
         var10000 = mc;
         int amplifier = Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
         baseSpeed *= 1.0D + 0.2D * (double)(amplifier + 1);
      }

      return baseSpeed;
   }

   public static enum FlyMode {
      Vanilla,
      Hypixel,
      Motion,
      HypixelZoom,
      longjump;
   }
}
