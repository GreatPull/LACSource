package net.minecraft.entity.player.Really.Client.module.modules.movement;

import net.minecraft.block.Block;
import net.minecraft.block.BlockGlass;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventMove;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPacketRecieve;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.management.ModuleManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.MoveUtils;
import net.minecraft.entity.player.Really.Client.utils.PlayerUtil;
import net.minecraft.entity.player.Really.Client.utils.Timer;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MovementInput;

public class SkyRun extends Module {
   Timer kickTimer = new Timer();
   private double flyHeight;
   private double startY;
   double count;
   Timer bowfly = new Timer();
   public static double hypixel = 0.0D;
   private float flytimer = 0.0F;
   public static int fastFlew;
   int stage;
   boolean shouldSpeed;
   private double flycrit = 0.0D;
   private boolean allowFlyingBefore;
   private static Numbers SPEED = new Numbers("Speed", "Speed", Double.valueOf(2.0D), Double.valueOf(0.25D), Double.valueOf(5.0D), Double.valueOf(0.25D));
   private static Numbers HYPIXELF = new Numbers("HYPIXELF", "HYPIXELF", Double.valueOf(4.8D), Double.valueOf(1.0D), Double.valueOf(6.5D), Double.valueOf(0.1D));
   public static Option BOBBING = new Option("Bobbing", "Bobbing", Boolean.valueOf(true));
   public static Mode MODE = new Mode("Mode", "Mode", SkyRun.FlyMode.values(), SkyRun.FlyMode.Vanilla);
   public static Mode HYPIXELMODE = new Mode("Mode", "Mode", SkyRun.HypixelFlyMode.values(), SkyRun.HypixelFlyMode.Basic);

   public SkyRun() {
      super("SkyRun", new String[]{"SkyRun"}, ModuleType.Movement);
      this.addValues(new Value[]{SPEED, HYPIXELF, BOBBING, MODE, HYPIXELMODE});
   }

   public void onEnable() {
      this.count = 0.0D;
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer != null) {
         if(MODE.getValue() == SkyRun.FlyMode.Hypixel && HYPIXELMODE.getValue() == SkyRun.HypixelFlyMode.Fast2) {
            fastFlew = 0;
         } else {
            fastFlew = 100;
         }

         net.minecraft.util.Timer var12 = mc.timer;
         net.minecraft.util.Timer.timerSpeed = 1.0F;
         if(MODE.getValue() != SkyRun.FlyMode.Cube1 && MODE.getValue() != SkyRun.FlyMode.Cube2 && MODE.getValue() != SkyRun.FlyMode.Cube3) {
            this.stage = 1;
         } else {
            this.shouldSpeed = false;
            if(!MoveUtils.isOnGround(0.001D)) {
               var12 = mc.timer;
               net.minecraft.util.Timer.timerSpeed = 0.3F;
            }

            this.stage = 0;
         }

         Minecraft var10001 = mc;
         this.startY = Minecraft.thePlayer.posY;
         hypixel = 0.0D;
         this.flycrit = 0.0D;
         var10001 = mc;
         this.allowFlyingBefore = Minecraft.thePlayer.capabilities.allowFlying;
         Minecraft var14 = mc;
         if(Minecraft.theWorld != null && MODE.getValue() == SkyRun.FlyMode.Hypixel && HYPIXELMODE.getValue() != SkyRun.HypixelFlyMode.Basic) {
            var14 = mc;
            double x = Minecraft.thePlayer.posX;
            var14 = mc;
            double y = Minecraft.thePlayer.posY;
            var14 = mc;
            double z = Minecraft.thePlayer.posZ;
            var14 = mc;
            if(Minecraft.thePlayer.onGround) {
               var14 = mc;
               if(Minecraft.thePlayer.isCollidedVertically && MoveUtils.isOnGround(0.01D)) {
                  String var7;
                  switch((var7 = HYPIXELMODE.getValue().toString()).hashCode()) {
                  case 67650357:
                     if(var7.equals("Fast1")) {
                        for(int index = 0; index < 49; ++index) {
                           var14 = mc;
                           NetworkManager var28 = Minecraft.thePlayer.sendQueue.getNetworkManager();
                           Minecraft var36 = mc;
                           Minecraft var41 = mc;
                           double var42 = Minecraft.thePlayer.posY + 0.06249D;
                           Minecraft var46 = mc;
                           var28.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, var42, Minecraft.thePlayer.posZ, false));
                           Minecraft var29 = mc;
                           NetworkManager var30 = Minecraft.thePlayer.sendQueue.getNetworkManager();
                           var36 = mc;
                           Minecraft var43 = mc;
                           var46 = mc;
                           var30.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, false));
                        }

                        var14 = mc;
                        NetworkManager var32 = Minecraft.thePlayer.sendQueue.getNetworkManager();
                        Minecraft var38 = mc;
                        Minecraft var44 = mc;
                        Minecraft var48 = mc;
                        var32.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, true));
                        MoveUtils.setMotion(0.3D + (double)((float)MoveUtils.getSpeedEffect() * 0.05F));
                        Minecraft var33 = mc;
                        Minecraft.thePlayer.motionY = 0.41999998688697815D + (double)MoveUtils.getJumpEffect() * 0.1D;
                        fastFlew = 25;
                        double speed = 13.0D + ((Double)HYPIXELF.getValue()).doubleValue();
                        hypixel = speed;
                     }
                     break;
                  case 67650358:
                     if(var7.equals("Fast2")) {
                        for(int index = 0; index < 49; ++index) {
                           var14 = mc;
                           NetworkManager var22 = Minecraft.thePlayer.sendQueue.getNetworkManager();
                           Minecraft var10003 = mc;
                           Minecraft var10004 = mc;
                           double var39 = Minecraft.thePlayer.posY + 0.06249D;
                           Minecraft var10005 = mc;
                           var22.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, var39, Minecraft.thePlayer.posZ, false));
                           Minecraft var23 = mc;
                           NetworkManager var24 = Minecraft.thePlayer.sendQueue.getNetworkManager();
                           var10003 = mc;
                           Minecraft var40 = mc;
                           var10005 = mc;
                           var24.sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, false));
                        }

                        var14 = mc;
                        Minecraft.thePlayer.onGround = false;
                        MoveUtils.setMotion(0.0D);
                        var14 = mc;
                        Minecraft.thePlayer.jumpMovementFactor = 0.0F;
                     }
                     break;
                  case 284829443:
                     if(var7.equals("OldFast")) {
                        MoveUtils.setMotion(0.361D + (double)((float)MoveUtils.getSpeedEffect() * 0.05F));
                        var14 = mc;
                        Minecraft.thePlayer.motionY = 0.41999998688697815D + (double)MoveUtils.getJumpEffect() * 0.1D;
                        fastFlew = 200;
                        hypixel = 17.0D;
                     }
                  }
               }
            }
         }

      }
   }

   public void onDisable() {
      if(MODE.getValue() == SkyRun.FlyMode.BowFly) {
         Minecraft var10000 = mc;
         Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
      }

      if(MODE.getValue() == SkyRun.FlyMode.Hypixel && HYPIXELMODE.getValue() != SkyRun.HypixelFlyMode.Basic) {
         Minecraft var1 = mc;
         Minecraft.thePlayer.motionX *= 0.0D;
         var1 = mc;
         Minecraft.thePlayer.motionZ *= 0.0D;
         var1 = mc;
         Minecraft.thePlayer.jumpMovementFactor = 0.1F;
      }

      this.count = 0.0D;
      fastFlew = 100;
      MoveUtils.setMotion(0.2D);
      Minecraft var4 = mc;
      Minecraft.thePlayer.jumpMovementFactor = 0.0F;
      var4 = mc;
      Minecraft.thePlayer.capabilities.isFlying = false;
      net.minecraft.util.Timer var6 = mc.timer;
      net.minecraft.util.Timer.timerSpeed = 1.0F;
      Minecraft var7 = mc;
      Minecraft.thePlayer.capabilities.allowFlying = this.allowFlyingBefore;
      if(MODE.getValue() == SkyRun.FlyMode.Cube1 || MODE.getValue() == SkyRun.FlyMode.Cube2 || MODE.getValue() == SkyRun.FlyMode.Cube3) {
         net.minecraft.util.Timer var8 = mc.timer;
         net.minecraft.util.Timer.timerSpeed = 1.0F;
         Minecraft var9 = mc;
         Minecraft.thePlayer.onGround = false;
         var9 = mc;
         Minecraft.thePlayer.jumpMovementFactor = 0.0F;
         var9 = mc;
         Minecraft.thePlayer.motionY = -0.4D;
         MoveUtils.setMotion(0.2D);
      }

   }

   @EventHandler
   public void EventMove(EventMove event) {
      String mode = MODE.getValue().toString();
      if(mode.equalsIgnoreCase("Antikick") || mode.equalsIgnoreCase("Motion") || mode.equalsIgnoreCase("glide")) {
         double speed = ((Double)SPEED.getValue()).doubleValue();
         Minecraft var10000 = mc;
         MovementInput var11 = Minecraft.thePlayer.movementInput;
         double forward = (double)MovementInput.moveForward;
         Minecraft var12 = mc;
         MovementInput var13 = Minecraft.thePlayer.movementInput;
         double strafe = (double)MovementInput.moveStrafe;
         Minecraft var14 = mc;
         float yaw = Minecraft.thePlayer.rotationYaw;
         if(forward == 0.0D && strafe == 0.0D) {
            EventMove.setX(0.0D);
            EventMove.setZ(0.0D);
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

            EventMove.setX(forward * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F))) + strafe * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F))));
            EventMove.setZ(forward * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F))) - strafe * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F))));
         }
      }

   }

   @EventHandler
   public void EventPreUpdate(EventPreUpdate event) {
      this.setSuffix(MODE.getValue());
      double speed = Math.max((double)((Double)SPEED.getValue()).floatValue(), getBaseMoveSpeed());
      if(MODE.getValue() == SkyRun.FlyMode.Hypixel) {
         ++fastFlew;
         if(HYPIXELMODE.getValue() == SkyRun.HypixelFlyMode.Fast2) {
            Minecraft var10000 = mc;
            if(Minecraft.thePlayer.onGround) {
               var10000 = mc;
               if(Minecraft.thePlayer.isCollidedVertically && MoveUtils.isOnGround(0.01D)) {
                  var10000 = mc;
                  if(Minecraft.thePlayer.hurtResistantTime == 19) {
                     MoveUtils.setMotion(0.3D + (double)((float)MoveUtils.getSpeedEffect() * 0.05F));
                     var10000 = mc;
                     Minecraft.thePlayer.motionY = 0.41999998688697815D + (double)MoveUtils.getJumpEffect() * 0.1D;
                     fastFlew = 25;
                     hypixel = 13.0D + ((Double)HYPIXELF.getValue()).doubleValue();
                  } else if(fastFlew < 25) {
                     var10000 = mc;
                     Minecraft.thePlayer.motionX = 0.0D;
                     var10000 = mc;
                     Minecraft.thePlayer.motionZ = 0.0D;
                     var10000 = mc;
                     Minecraft.thePlayer.jumpMovementFactor = 0.0F;
                     var10000 = mc;
                     Minecraft.thePlayer.onGround = false;
                  }
               }
            }
         }

         Minecraft var47 = mc;
         Block block = MoveUtils.getBlockUnderPlayer(Minecraft.thePlayer, 0.2D);
         if(!MoveUtils.isOnGround(1.0E-7D) && !block.isFullBlock() && !(block instanceof BlockGlass)) {
            var47 = mc;
            Minecraft.thePlayer.motionY = 0.0D;
            var47 = mc;
            Minecraft.thePlayer.motionX = 0.0D;
            var47 = mc;
            Minecraft.thePlayer.motionZ = 0.0D;
            float speedf = 0.29F + (float)MoveUtils.getSpeedEffect() * 0.06F;
            if(hypixel > 0.0D) {
               label326: {
                  label3216: {
                     var47 = mc;
                     if(Minecraft.thePlayer.moveForward == 0.0F) {
                        var47 = mc;
                        if(Minecraft.thePlayer.moveStrafing == 0.0F) {
                           break label326;
                        }
                     }

                     var47 = mc;
                     if(!Minecraft.thePlayer.isCollidedHorizontally) {
                        break label326;
                     }
                  }

                  hypixel = 0.0D;
               }

               speedf = (float)((double)speedf + hypixel / 18.0D);
               if(HYPIXELMODE.getValue() == SkyRun.HypixelFlyMode.OldFast) {
                  --hypixel;
               } else {
                  hypixel -= 0.155D + (double)MoveUtils.getSpeedEffect() * 0.006D;
               }
            }

            this.setSpecialMotion((double)speedf);
            var47 = mc;
            Minecraft.thePlayer.jumpMovementFactor = 0.0F;
            var47 = mc;
            Minecraft.thePlayer.onGround = false;
            var47 = mc;
            if(Minecraft.gameSettings.keyBindJump.isKeyDown()) {
               var47 = mc;
               Minecraft.thePlayer.motionY = 0.4D;
            }

            ++this.count;
            var47 = mc;
            Minecraft.thePlayer.lastReportedPosY = 0.0D;
            if(this.count <= 2.0D) {
               var47 = mc;
               Minecraft var10001 = mc;
               Minecraft var10002 = mc;
               double var154 = Minecraft.thePlayer.posY + 1.9999E-8D;
               Minecraft var10003 = mc;
               Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, var154, Minecraft.thePlayer.posZ);
            } else if(this.count == 4.0D) {
               var47 = mc;
               Minecraft var145 = mc;
               Minecraft var155 = mc;
               double var156 = Minecraft.thePlayer.posY + 1.0E-8D;
               Minecraft var165 = mc;
               Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, var156, Minecraft.thePlayer.posZ);
            } else if(this.count >= 5.0D) {
               var47 = mc;
               Minecraft var146 = mc;
               Minecraft var157 = mc;
               double var158 = Minecraft.thePlayer.posY + 1.99999E-8D;
               Minecraft var166 = mc;
               Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, var158, Minecraft.thePlayer.posZ);
               this.count = 0.0D;
            }
         }
      }

      Minecraft var62 = mc;
      double X = Minecraft.thePlayer.posX;
      var62 = mc;
      double Y = Minecraft.thePlayer.posY;
      var62 = mc;
      double Z = Minecraft.thePlayer.posZ;
      String var11;
      switch((var11 = MODE.getValue().toString()).hashCode()) {
      case -1984451626:
         if(var11.equals("Motion")) {
            boolean var153;
            label2881: {
               var62 = mc;
               Minecraft.thePlayer.onGround = false;
               if(!MoveUtils.isOnGround(0.001D)) {
                  Client.getModuleManager();
                  if(!ModuleManager.getModuleByName("NoFall").isEnabled()) {
                     var153 = false;
                     break label2881;
                  }
               }

               var153 = true;
            }

            event.setOnground(var153);
            var62 = mc;
            if(Minecraft.thePlayer.movementInput.jump) {
               var62 = mc;
               Minecraft.thePlayer.motionY = speed * 0.6D;
            } else {
               var62 = mc;
               if(Minecraft.thePlayer.movementInput.sneak) {
                  var62 = mc;
                  Minecraft.thePlayer.motionY = -speed * 0.6D;
               } else {
                  var62 = mc;
                  Minecraft.thePlayer.motionY = 0.0D;
               }
            }
         }
         break;
      case -1465377359:
         if(var11.equals("Guardian")) {
            Client.getModuleManager();
            if(!ModuleManager.getModuleByName("Hopper").isEnabled()) {
               var62 = mc;
               Minecraft.thePlayer.motionX *= 0.0D;
               var62 = mc;
               Minecraft.thePlayer.motionZ *= 0.0D;
               var62 = mc;
               Minecraft.thePlayer.jumpMovementFactor = 0.31F + (float)MoveUtils.getSpeedEffect() * 0.05F;
            }

            var62 = mc;
            Minecraft.thePlayer.motionY = 0.0D;
            var62 = mc;
            Minecraft var152 = mc;
            Minecraft var163 = mc;
            double var164 = Minecraft.thePlayer.posY + 1.0E-8D;
            Minecraft var168 = mc;
            Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, var164, Minecraft.thePlayer.posZ);
            var62 = mc;
            Minecraft.thePlayer.onGround = false;
            var62 = mc;
            if(Minecraft.gameSettings.keyBindJump.isKeyDown()) {
               var62 = mc;
               Minecraft.thePlayer.motionY = 0.4D;
            } else {
               var62 = mc;
               if(Minecraft.gameSettings.keyBindSneak.isKeyDown()) {
                  var62 = mc;
                  Minecraft.thePlayer.motionY = -0.4D;
               }
            }
         }
         break;
      case -419998488:
         if(var11.equals("AntiKick")) {
            var62 = mc;
            if(Minecraft.thePlayer.movementInput.jump) {
               var62 = mc;
               Minecraft.thePlayer.motionY = speed * 0.6D;
            } else {
               var62 = mc;
               if(Minecraft.thePlayer.movementInput.sneak) {
                  var62 = mc;
                  Minecraft.thePlayer.motionY = -speed * 0.6D;
               } else {
                  var62 = mc;
                  Minecraft.thePlayer.motionY = 0.0D;
               }
            }

            this.updateFlyHeight();
            var62 = mc;
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
            if(this.flyHeight <= 290.0D && Timer.delay(500) || this.flyHeight > 290.0D && Timer.delay(100)) {
               this.goToGround();
               Timer.reset();
            }
         }
         break;
      case 65458812:
         if(var11.equals("Cube1")) {
            if(!MoveUtils.isOnGround(0.001D)) {
               ++this.stage;
               var62 = mc;
               Minecraft.thePlayer.onGround = false;
               var62 = mc;
               Minecraft.thePlayer.lastReportedPosY = 0.0D;
               var62 = mc;
               Minecraft.thePlayer.jumpMovementFactor = 0.0F;
               float timer = 0.3F;
               double motion = 0.0D;
               speed = 0.0D;
               double dist = 4.0D;
               if(this.stage == 1) {
                  motion = 0.28D;
                  speed = 2.4D;
               } else if(this.stage == 2) {
                  motion = 0.28D;
               } else if(this.stage == 3) {
                  motion = 0.28D;
                  speed = 2.4D;
               } else if(this.stage == 4) {
                  motion = -0.9D;
                  this.stage = 0;
               }

               net.minecraft.util.Timer var119 = mc.timer;
               net.minecraft.util.Timer.timerSpeed = timer;
               Minecraft var120 = mc;
               Minecraft.thePlayer.motionY = motion;
               MoveUtils.setMotion(speed);
            } else {
               net.minecraft.util.Timer var121 = mc.timer;
               if(net.minecraft.util.Timer.timerSpeed == 0.3F) {
                  var121 = mc.timer;
                  net.minecraft.util.Timer.timerSpeed = 1.0F;
                  MoveUtils.setMotion(0.0D);
               }
            }
         }
         break;
      case 65458813:
         if(var11.equals("Cube2")) {
            if(!MoveUtils.isOnGround(0.001D)) {
               ++this.stage;
               net.minecraft.util.Timer var106 = mc.timer;
               net.minecraft.util.Timer.timerSpeed = 0.27F;
               Minecraft var107 = mc;
               Minecraft.thePlayer.lastReportedPosY = 0.0D;
               var107 = mc;
               Minecraft.thePlayer.jumpMovementFactor = 0.0F;
               var107 = mc;
               Minecraft.thePlayer.onGround = false;
               var107 = mc;
               double motion = Minecraft.thePlayer.motionY;
               speed = 0.0D;
               event.setOnground(false);
               if(this.stage == 1) {
                  var107 = mc;
                  Minecraft var150 = mc;
                  Minecraft var161 = mc;
                  boolean a = Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, Minecraft.thePlayer.getEntityBoundingBox().offset(0.0D, 0.4D, 0.0D)).isEmpty();
                  event.setY(Y + (a?0.4D:0.2D));
                  motion = 0.4D;
               } else if(this.stage == 2) {
                  motion = 0.28D;
                  var107 = mc;
                  Minecraft var151 = mc;
                  Minecraft var162 = mc;
                  boolean a = Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, Minecraft.thePlayer.getEntityBoundingBox().offset(0.0D, 0.68D, 0.0D)).isEmpty();
                  event.setY(Y + (a?0.68D:0.2D));
                  if(this.shouldSpeed) {
                     speed = 2.4D;
                  } else {
                     this.shouldSpeed = true;
                     speed = 2.2D;
                  }
               } else if(this.stage == 3) {
                  motion = -0.68D;
               } else if(this.stage == 4) {
                  motion = 0.0D;
                  speed = 2.4D;
                  this.stage = 0;
               }

               MoveUtils.setMotion(speed);
               var107 = mc;
               Minecraft.thePlayer.motionY = 0.0D;
            } else if(this.shouldSpeed) {
               MoveUtils.setMotion(0.0D);
               this.shouldSpeed = !this.shouldSpeed;
            } else {
               net.minecraft.util.Timer var114 = mc.timer;
               if(net.minecraft.util.Timer.timerSpeed != 1.0F) {
                  var114 = mc.timer;
                  net.minecraft.util.Timer.timerSpeed = 1.0F;
               }
            }
         }
         break;
      case 65458814:
         if(var11.equals("Cube3")) {
            if(!MoveUtils.isOnGround(0.001D)) {
               ++this.stage;
               var62 = mc;
               Minecraft.thePlayer.onGround = false;
               var62 = mc;
               Minecraft.thePlayer.lastReportedPosY = 0.0D;
               var62 = mc;
               Minecraft.thePlayer.jumpMovementFactor = 0.0F;
               float timer = 0.2F;
               double motion = 0.0D;
               speed = 0.0D;
               double dist = 4.0D;
               var62 = mc;
               Minecraft var149 = mc;
               Minecraft var160 = mc;
               boolean are = !Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, Minecraft.thePlayer.getEntityBoundingBox().expand(dist, dist, dist)).isEmpty();
               if(this.stage == 1) {
                  motion = 0.4D;
                  if(this.shouldSpeed) {
                     speed = 2.3D;
                  } else {
                     speed = 2.0D;
                     this.shouldSpeed = !this.shouldSpeed;
                  }
               } else if(this.stage == 2) {
                  motion = 0.0D;
                  timer = 1.0F;
               } else if(this.stage == 3) {
                  motion = 0.4D;
                  speed = 2.3D;
                  if(are) {
                     timer = 1.0F;
                  }
               } else if(this.stage == 4) {
                  motion = -0.9D;
                  this.stage = 0;
                  if(are) {
                     timer = 1.0F;
                  }
               }

               net.minecraft.util.Timer var102 = mc.timer;
               net.minecraft.util.Timer.timerSpeed = timer;
               Minecraft var103 = mc;
               Minecraft.thePlayer.motionY = motion;
               MoveUtils.setMotion(speed);
            } else if(this.shouldSpeed) {
               MoveUtils.setMotion(0.0D);
               this.shouldSpeed = !this.shouldSpeed;
            } else {
               net.minecraft.util.Timer var104 = mc.timer;
               if(net.minecraft.util.Timer.timerSpeed != 1.0F) {
                  var104 = mc.timer;
                  net.minecraft.util.Timer.timerSpeed = 1.0F;
               }
            }
         }
         break;
      case 68891525:
         if(var11.equals("Glide")) {
            boolean var92;
			label2058: {
               var62 = mc;
               if(Minecraft.thePlayer.posY + 0.1D >= this.startY) {
                  var62 = mc;
                  if(Minecraft.gameSettings.keyBindJump.isKeyDown()) {
                     var92 = true;
                     break label2058;
                  }
               }

               var92 = false;
            }

            boolean shouldBlock = var92;
            var62 = mc;
            if(Minecraft.thePlayer.isSneaking()) {
               var62 = mc;
               Minecraft.thePlayer.motionY = -0.4000000059604645D;
            } else {
               var62 = mc;
               if(Minecraft.gameSettings.keyBindJump.isKeyDown() && !shouldBlock) {
                  var62 = mc;
                  Minecraft.thePlayer.motionY = 0.4000000059604645D;
               } else {
                  var62 = mc;
                  Minecraft.thePlayer.motionY = -0.01D;
               }
            }
         }
         break;
      case 1897755483:
         if(var11.equals("Vanilla")) {
            var62 = mc;
            Minecraft.thePlayer.capabilities.isFlying = true;
            var62 = mc;
            Minecraft.thePlayer.capabilities.allowFlying = true;
         }
         break;
      case 1995650665:
         if(var11.equals("BowFly")) {
            if(this.getBowCount() == 0 && this.getArrowCount() == 0) {
               if(Timer.delay(5000)) {
                  Timer.reset();
               }

               return;
            }

            if(this.getBowCount() == 0) {
               if(Timer.delay(5000)) {
                  Timer.reset();
               }

               return;
            }

            if(this.getArrowCount() == 0) {
               if(Timer.delay(5000)) {
                  Timer.reset();
               }

               return;
            }

            if(this.invCheck()) {
               ItemStack is = new ItemStack(Item.getItemById(261));

               for(int i = 9; i < 36; ++i) {
                  var62 = mc;
                  if(Minecraft.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                     var62 = mc;
                     Item item = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack().getItem();
                     int count = 0;
                     if(item instanceof ItemBow) {
                        for(int a = 36; a < 45; ++a) {
                           var62 = mc;
                           Container var68 = Minecraft.thePlayer.inventoryContainer;
                           Minecraft var69 = mc;
                           if(Container.canAddItemToSlot(Minecraft.thePlayer.inventoryContainer.getSlot(a), is, true)) {
                              var69 = mc;
                              Minecraft var147 = mc;
                              int var167 = a - 36;
                              Minecraft var10005 = mc;
                              Minecraft.playerController.windowClick(Minecraft.thePlayer.inventoryContainer.windowId, i, var167, 2, Minecraft.thePlayer);
                              ++count;
                              break;
                           }
                        }

                        if(count == 0) {
                           var62 = mc;
                           Minecraft var148 = mc;
                           Minecraft var169 = mc;
                           Minecraft.playerController.windowClick(Minecraft.thePlayer.inventoryContainer.windowId, i, 7, 2, Minecraft.thePlayer);
                        }
                        break;
                     }
                  }
               }
            }

            this.getBow();
            var62 = mc;
            if(Minecraft.thePlayer.getCurrentEquippedItem() != null) {
               var62 = mc;
               if(Minecraft.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow) {
                  Packet C07;
                  Packet C08;
                  float yaw;
                  float pitch;
                  label2523: {
                     C07 = new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN);
                     Minecraft var159 = mc;
                     C08 = new C08PacketPlayerBlockPlacement(Minecraft.thePlayer.inventory.getCurrentItem());
                     var62 = mc;
                     yaw = Minecraft.thePlayer.rotationYaw;
                     pitch = -90.0F;
                     var62 = mc;
                     if(Minecraft.thePlayer.moveForward == 0.0F) {
                        var62 = mc;
                        if(Minecraft.thePlayer.moveStrafing == 0.0F) {
                           break label2523;
                        }
                     }

                     pitch = -80.0F;
                  }

                  var62 = mc;
                  if(Minecraft.thePlayer.moveForward < 0.0F) {
                     yaw -= 180.0F;
                  }

                  var62 = mc;
                  if(Minecraft.thePlayer.motionY < -0.1D) {
                     pitch = 90.0F;
                  }

                  label2660: {
                     event.setPitch(pitch);
                     event.setYaw(yaw);
                     var62 = mc;
                     if(Minecraft.thePlayer.onGround) {
                        var62 = mc;
                        if(Minecraft.thePlayer.isCollidedVertically && MoveUtils.isOnGround(1.0E-4D)) {
                           var62 = mc;
                           Minecraft.thePlayer.jump();
                           break label2660;
                        }
                     }

                     var62 = mc;
                     if(Minecraft.thePlayer.motionY < 0.0D) {
                        net.minecraft.util.Timer var82 = mc.timer;
                        net.minecraft.util.Timer.timerSpeed = 0.1F;
                     } else {
                        net.minecraft.util.Timer var83 = mc.timer;
                        if(net.minecraft.util.Timer.timerSpeed == 0.1F) {
                           var83 = mc.timer;
                           net.minecraft.util.Timer.timerSpeed = 1.0F;
                        }
                     }
                  }

                  ++this.count;
                  if(this.count >= 4.0D) {
                     var62 = mc;
                     Minecraft.thePlayer.sendQueue.addToSendQueue(C07);
                     this.count = 0.0D;
                  } else if(this.count == 1.0D) {
                     var62 = mc;
                     Minecraft.thePlayer.sendQueue.addToSendQueue(C08);
                  }
               }
            }
         }
      }

   }

   @EventHandler
   public void EventPacket(EventPacketRecieve event) {
      Packet p = event.getPacket();
      if(p instanceof S08PacketPlayerPosLook) {
         S08PacketPlayerPosLook pac = (S08PacketPlayerPosLook)p;
         hypixel = 0.0D;
      }

      if(MODE.getValue() != SkyRun.FlyMode.BowFly) {
         if(p instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity packet = (S12PacketEntityVelocity)p;
            int var10000 = packet.getEntityID();
            Minecraft var10001 = mc;
            if(var10000 == Minecraft.thePlayer.getEntityId()) {
               event.setCancelled(true);
            }
         }

         if(p instanceof S27PacketExplosion) {
            event.setCancelled(true);
         }
      }

      if(p instanceof C03PacketPlayer) {
         C03PacketPlayer pack = (C03PacketPlayer)p;
         if(MODE.getValue() == SkyRun.FlyMode.Hypixel) {
            pack.onGround = MoveUtils.isOnGround(0.001D);
            if(!PlayerUtil.isMoving2() && pack.y == 0.0D) {
               double var9 = pack.x;
            }
         }
      }

      if(MODE.getValue() == SkyRun.FlyMode.Guardian) {
         Client.getModuleManager();
         if(!ModuleManager.getModuleByName("NoFall").isEnabled()) {
            Minecraft var10 = mc;
            Block block = MoveUtils.getBlockUnderPlayer(Minecraft.thePlayer, 0.2D);
            if(p instanceof C03PacketPlayer && !MoveUtils.isOnGround(1.0E-7D) && !block.isFullBlock() && !(block instanceof BlockGlass)) {
               C03PacketPlayer packet = (C03PacketPlayer)p;
               packet.onGround = false;
            }
         }
      }

   }

   public static double getBaseMoveSpeed() {
      double baseSpeed = 0.2873D;
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
         var10000 = mc;
         int amplifier = Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
         baseSpeed *= 1.0D + 0.2D * (double)(amplifier + 1);
      }

      return baseSpeed;
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

   private boolean invCheck() {
      for(int i = 36; i < 45; ++i) {
         Minecraft var10000 = mc;
         if(Minecraft.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            var10000 = mc;
            Item item = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack().getItem();
            if(item instanceof ItemBow) {
               return false;
            }
         }
      }

      return true;
   }

   public int getArrowCount() {
      int arrowCount = 0;

      for(int i = 0; i < 45; ++i) {
         Minecraft var10000 = mc;
         if(Minecraft.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            var10000 = mc;
            ItemStack is = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack();
            Item item = is.getItem();
            Item arrow = Item.getItemById(262);
            if(item == arrow) {
               arrowCount += is.stackSize;
            }
         }
      }

      return arrowCount;
   }

   public int getBowCount() {
      int bowCount = 0;

      for(int i = 0; i < 45; ++i) {
         Minecraft var10000 = mc;
         if(Minecraft.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            var10000 = mc;
            ItemStack is = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack();
            Item item = is.getItem();
            if(is.getItem() instanceof ItemBow) {
               bowCount += is.stackSize;
            }
         }
      }

      return bowCount;
   }

   private void getBow() {
      ItemStack is = new ItemStack(Item.getItemById(262));

      try {
         Minecraft var10000 = mc;
         if(Minecraft.thePlayer.inventory.getCurrentItem() != null) {
            var10000 = mc;
            if(!(Minecraft.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow)) {
               for(int i = 36; i < 45; ++i) {
                  int theSlot = i - 36;
                  var10000 = mc;
                  Container var7 = Minecraft.thePlayer.inventoryContainer;
                  Minecraft var8 = mc;
                  if(!Container.canAddItemToSlot(Minecraft.thePlayer.inventoryContainer.getSlot(i), is, true)) {
                     var8 = mc;
                     if(Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemBow) {
                        var8 = mc;
                        if(Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack() != null) {
                           var8 = mc;
                           Minecraft.thePlayer.inventory.currentItem = theSlot;
                           var8 = mc;
                           NetHandlerPlayClient var13 = Minecraft.getNetHandler();
                           Minecraft var10003 = mc;
                           var13.addToSendQueue(new C09PacketHeldItemChange(Minecraft.thePlayer.inventory.currentItem));
                           Minecraft var14 = mc;
                           Minecraft.playerController.updateController();
                           break;
                        }
                     }
                  }
               }
            }
         }
      } catch (Exception var4) {
         ;
      }

   }

   private void setSpecialMotion(double speed) {
      Minecraft var10000 = mc;
      MovementInput var8 = Minecraft.thePlayer.movementInput;
      double forward = (double)MovementInput.moveForward;
      Minecraft var9 = mc;
      MovementInput var10 = Minecraft.thePlayer.movementInput;
      double strafe = (double)MovementInput.moveStrafe;
      Minecraft var11 = mc;
      float yaw = Minecraft.thePlayer.rotationYaw;
      if(forward == 0.0D && strafe == 0.0D) {
         var11 = mc;
         Minecraft.thePlayer.motionX = 0.0D;
         var11 = mc;
         Minecraft.thePlayer.motionZ = 0.0D;
      } else {
         if(forward != 0.0D) {
            if(hypixel <= 0.0D) {
               if(strafe > 0.0D) {
                  yaw += (float)(forward > 0.0D?-45:45);
               } else if(strafe < 0.0D) {
                  yaw += (float)(forward > 0.0D?45:-45);
               }
            }

            strafe = 0.0D;
            if(forward > 0.0D) {
               forward = 1.0D;
            } else if(forward < 0.0D) {
               forward = -1.0D;
            }
         }

         var11 = mc;
         Minecraft.thePlayer.motionX = forward * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F))) + strafe * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F)));
         var11 = mc;
         Minecraft.thePlayer.motionZ = forward * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F))) - strafe * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F)));
      }

   }

   public static enum FlyMode {
      Vanilla,
      Guardian,
      AntiKick,
      Glide,
      Motion,
      Hypixel,
      BowFly,
      Cube1,
      Cube2,
      Cube3;
   }

   static enum HypixelFlyMode {
      Basic,
      OldFast,
      Fast1,
      Fast2;
   }
}
