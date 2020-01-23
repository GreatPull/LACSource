package net.minecraft.entity.player.Really.Client.module.modules.movement;

import java.awt.Color;
import java.util.TimerTask;
import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockSign;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender3D;
import net.minecraft.entity.player.Really.Client.api.events.world.EventMove;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPostUpdate;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.management.ModuleManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;
import net.minecraft.entity.player.Really.Client.utils.math.MathUtil;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class ClickTp extends Module {
   public Mode mode = new Mode("Mode", "mode", ClickTp.TeleMode.values(), ClickTp.TeleMode.Hypixel);
   private Numbers speed = new Numbers("Speed", "Speed", Double.valueOf(14.0D), Double.valueOf(0.0D), Double.valueOf(20.0D), Double.valueOf(1.0D));
   private Numbers maxticks = new Numbers("MaxTicks", "MaxTicks", Double.valueOf(25.0D), Double.valueOf(0.0D), Double.valueOf(50.0D), Double.valueOf(5.0D));
   private Option UHC = new Option("UHC", "UHC", Boolean.valueOf(false));
   int counter;
   int level;
   double moveSpeed;
   double lastDist;
   boolean b2;
   private int zoom;
   boolean startTP = false;
   boolean needTP = false;
   TimerUtil cooldown = new TimerUtil();
   boolean canTP;
   int TeleX;
   int TeleY;
   int TeleZ;

   public int Height() {
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer.isPotionActive(Potion.jump)) {
         var10000 = mc;
         return Minecraft.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1;
      } else {
         return 0;
      }
   }

   public ClickTp() {
      super("ClickTP", new String[]{"Teleport"}, ModuleType.Movement);
      this.setColor((new Color(158, 114, 243)).getRGB());
      this.addValues(new Value[]{this.mode, this.speed, this.maxticks, this.UHC});
   }

   public void damagePlayer() {
      if(Minecraft.thePlayer.onGround) {
         for(int index = 0; index < 49; ++index) {
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

   public void onEnable() {
      this.startTP = false;
      this.level = 1;
      this.moveSpeed = 0.1D;
      this.b2 = true;
      this.lastDist = 0.0D;
   }

   public void onDisable() {
      this.level = 1;
      this.moveSpeed = 0.1D;
      this.b2 = false;
      this.lastDist = 0.0D;
      Timer var10000 = mc.timer;
      Timer.timerSpeed = 1.0F;
      this.canTP = false;
   }

   public static Block getBlock(int x, int y, int z) {
      Minecraft var10000 = mc;
      return Minecraft.theWorld.getBlockState(new BlockPos(x, y, z)).getBlock();
   }

   public static Block getBlock(BlockPos pos) {
      Minecraft var10000 = mc;
      return Minecraft.theWorld.getBlockState(pos).getBlock();
   }

   @EventHandler
   public void EventRender3D(EventRender3D er) {
      try {
         int x = this.getTeleBlock().getBlockPos().getX();
         int y = this.getTeleBlock().getBlockPos().getY();
         int z = this.getTeleBlock().getBlockPos().getZ();
         Block block1 = getBlock(x, y, z);
         Block block2 = getBlock(x, y + 1, z);
         Block block3 = getBlock(x, y + 2, z);
         boolean blockBelow = !(block1 instanceof BlockSign) && block1.getMaterial().isSolid();
         boolean blockLevel = !(block2 instanceof BlockSign) && block1.getMaterial().isSolid();
         boolean blockAbove = !(block3 instanceof BlockSign) && block1.getMaterial().isSolid();
         if(getBlock(this.getTeleBlock().getBlockPos()).getMaterial() != Material.air && blockBelow && blockLevel && blockAbove && !(getBlock(this.getTeleBlock().getBlockPos()) instanceof BlockChest)) {
            this.canTP = true;
         } else {
            this.canTP = false;
         }
      } catch (Exception var11) {
         ;
      }

   }

   public Vec3 func_174824_e(float partial) {
      if(partial == 1.0F) {
         Minecraft var16 = mc;
         double var17 = Minecraft.thePlayer.posX;
         Minecraft var10003 = mc;
         Minecraft var10004 = mc;
         double var18 = Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight();
         var10004 = mc;
         return new Vec3(var17, var18, Minecraft.thePlayer.posZ);
      } else {
         Minecraft var10000 = mc;
         Minecraft var10001 = mc;
         Minecraft var10002 = mc;
         double var2 = Minecraft.thePlayer.prevPosX + (Minecraft.thePlayer.posX - Minecraft.thePlayer.prevPosX) * (double)partial;
         var10000 = mc;
         var10001 = mc;
         var10002 = mc;
         double var9 = Minecraft.thePlayer.prevPosY + (Minecraft.thePlayer.posY - Minecraft.thePlayer.prevPosY) * (double)partial;
         var10001 = mc;
         double var4 = var9 + (double)Minecraft.thePlayer.getEyeHeight();
         Minecraft var10 = mc;
         var10001 = mc;
         var10002 = mc;
         double var6 = Minecraft.thePlayer.prevPosZ + (Minecraft.thePlayer.posZ - Minecraft.thePlayer.prevPosZ) * (double)partial;
         return new Vec3(var2, var4, var6);
      }
   }

   public MovingObjectPosition getTeleBlock() {
      Vec3 var4 = this.func_174824_e(EventRender3D.ticks);
      Minecraft var10000 = mc;
      Vec3 var5 = Minecraft.thePlayer.getLook(EventRender3D.ticks);
      Vec3 var6 = var4.addVector(var5.xCoord * 70.0D, var5.yCoord * 70.0D, var5.zCoord * 70.0D);
      var10000 = mc;
      return Minecraft.thePlayer.worldObj.rayTraceBlocks(var4, var6, false, false, true);
   }

   @EventHandler
   private void onUpdate(EventPreUpdate e) {
      this.setSuffix(this.mode.getValue());
      if(this.cooldown.hasReached(500.0D) && this.canTP && Mouse.isButtonDown(2)) {
         Minecraft var10000 = mc;
         if(!Minecraft.thePlayer.isSneaking() && mc.inGameHasFocus && this.getTeleBlock().entityHit == null && !(getBlock(this.getTeleBlock().getBlockPos()) instanceof BlockChest)) {
            if(ModuleManager.getModuleByName("Scaffold").isEnabled()) {
               ModuleManager.getModuleByName("Scaffold").setEnabled(false);
            }

            this.cooldown.reset();
            if(this.mode.getValue() == ClickTp.TeleMode.Hypixel) {
               this.needTP = true;
               this.startTP = false;
               if(((Boolean)this.UHC.getValue()).booleanValue()) {
                  this.damagePlayer(2);
               } else {
                  this.damagePlayer();
               }

               (new java.util.Timer()).schedule(new TimerTask() {
                  public void run() {
                     ClickTp.this.startTP = true;
                     ClickTp.this.level = 1;
                     ClickTp.this.moveSpeed = 0.1D;
                     ClickTp.this.b2 = true;
                     ClickTp.this.lastDist = 0.0D;
                     ClickTp.this.zoom = ((Double)ClickTp.this.maxticks.getValue()).intValue();
                     Minecraft var10000 = ClickTp.mc;
                     Minecraft.thePlayer.motionY = 0.41999999999875D + (double)ClickTp.this.Height() * 0.1D;
                     ClickTp.this.TeleX = ClickTp.this.getTeleBlock().getBlockPos().getX();
                     ClickTp.this.TeleY = ClickTp.this.getTeleBlock().getBlockPos().getY();
                     ClickTp.this.TeleZ = ClickTp.this.getTeleBlock().getBlockPos().getZ();
                     this.cancel();
                  }
               }, 240L);
            }
         }
      }

      if(this.mode.getValue() == ClickTp.TeleMode.Hypixel && this.needTP) {
         Minecraft var2 = mc;
         KeyBinding.setKeyBindState(Minecraft.gameSettings.keyBindForward.getKeyCode(), true);
         if(this.startTP) {
            ++this.counter;
            if(this.zoom > 0 && ((Double)this.speed.getValue()).floatValue() > 0.0F) {
               Timer var6 = mc.timer;
               Timer.timerSpeed = ((Double)this.speed.getValue()).floatValue();
            } else {
               Timer var3 = mc.timer;
               Timer.timerSpeed = 1.0F;
               Minecraft var4 = mc;
               int var5 = Minecraft.gameSettings.keyBindForward.getKeyCode();
               Minecraft var10001 = mc;
               KeyBinding.setKeyBindState(var5, Keyboard.isKeyDown(Minecraft.gameSettings.keyBindForward.getKeyCode()));
               this.needTP = false;
            }

            double var7 = (double)this.TeleX;
            Minecraft var25 = mc;
            var7 = Math.max(var7, Minecraft.thePlayer.posX);
            double var26 = (double)this.TeleX;
            Minecraft var10002 = mc;
            if(var7 - Math.min(var26, Minecraft.thePlayer.posX) <= 1.4D) {
               var7 = (double)this.TeleZ;
               Minecraft var27 = mc;
               var7 = Math.max(var7, Minecraft.thePlayer.posZ);
               double var28 = (double)this.TeleZ;
               var10002 = mc;
               if(var7 - Math.min(var28, Minecraft.thePlayer.posZ) <= 1.4D) {
                  Timer var11 = mc.timer;
                  Timer.timerSpeed = 1.0F;
                  Minecraft var12 = mc;
                  int var13 = Minecraft.gameSettings.keyBindForward.getKeyCode();
                  Minecraft var29 = mc;
                  KeyBinding.setKeyBindState(var13, Keyboard.isKeyDown(Minecraft.gameSettings.keyBindForward.getKeyCode()));
                  this.needTP = false;
               }
            }

            --this.zoom;
            Minecraft var14 = mc;
            if(Minecraft.thePlayer.moveForward == 0.0F) {
               var14 = mc;
               if(Minecraft.thePlayer.moveStrafing == 0.0F) {
                  var14 = mc;
                  Minecraft var30 = mc;
                  double var31 = Minecraft.thePlayer.posX + 1.0D;
                  var10002 = mc;
                  double var36 = Minecraft.thePlayer.posY + 1.0D;
                  Minecraft var10003 = mc;
                  Minecraft.thePlayer.setPosition(var31, var36, Minecraft.thePlayer.posZ + 1.0D);
                  var14 = mc;
                  Minecraft var32 = mc;
                  Minecraft var37 = mc;
                  var10003 = mc;
                  Minecraft.thePlayer.setPosition(Minecraft.thePlayer.prevPosX, Minecraft.thePlayer.prevPosY, Minecraft.thePlayer.prevPosZ);
                  var14 = mc;
                  Minecraft.thePlayer.motionX = 0.0D;
                  var14 = mc;
                  Minecraft.thePlayer.motionZ = 0.0D;
               }
            }

            var14 = mc;
            Minecraft.thePlayer.motionY = 0.0D;
            if(this.counter != 1 && this.counter == 2) {
               var14 = mc;
               Minecraft var33 = mc;
               var10002 = mc;
               double var39 = Minecraft.thePlayer.posY + 1.0E-1D;
               Minecraft var41 = mc;
               Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, var39, Minecraft.thePlayer.posZ);
               this.counter = 0;
            }
         } else {
            var2 = mc;
            Minecraft.thePlayer.setSprinting(false);
            var2 = mc;
            Minecraft.thePlayer.motionX = 0.0D;
            var2 = mc;
            Minecraft.thePlayer.motionZ = 0.0D;
         }
      }

   }

   @EventHandler
   public void onPost(EventPostUpdate e) {
      if(this.mode.getValue() == ClickTp.TeleMode.Hypixel && this.needTP && this.startTP) {
         Minecraft var10000 = mc;
         double posX = Minecraft.thePlayer.posX;
         Minecraft var10001 = mc;
         double xDist = posX - Minecraft.thePlayer.prevPosX;
         var10000 = mc;
         double posZ = Minecraft.thePlayer.posZ;
         var10001 = mc;
         double zDist = posZ - Minecraft.thePlayer.prevPosZ;
         this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
      }

   }

   @EventHandler
   private void onMove(EventMove e) {
      if(this.mode.getValue() == ClickTp.TeleMode.Hypixel && this.needTP && this.startTP) {
         Minecraft var10000 = mc;
         MovementInput var17 = Minecraft.thePlayer.movementInput;
         float forward = MovementInput.moveForward;
         Minecraft var18 = mc;
         MovementInput var19 = Minecraft.thePlayer.movementInput;
         float strafe = MovementInput.moveStrafe;
         Minecraft var20 = mc;
         float yaw = Minecraft.thePlayer.rotationYaw;
         double mx = Math.cos(Math.toRadians((double)(yaw + 90.0F)));
         double mz = Math.sin(Math.toRadians((double)(yaw + 90.0F)));
         if(forward == 0.0F && strafe == 0.0F) {
            EventMove.x = 0.0D;
            EventMove.z = 0.0D;
         }

         if(this.b2) {
            label126: {
               label1226: {
                  if(this.level == 1) {
                     var20 = mc;
                     if(Minecraft.thePlayer.moveForward != 0.0F) {
                        break label126;
                     }

                     var20 = mc;
                     if(Minecraft.thePlayer.moveStrafing != 0.0F) {
                        break label126;
                     }
                  }

                  if(this.level == 2) {
                     this.level = 3;
                     this.moveSpeed *= 2.1399D;
                     break label126;
                  }

                  if(this.level == 3) {
                     this.level = 4;
                     var20 = mc;
                     double difference = (Minecraft.thePlayer.ticksExisted % 2 == 0?0.0103D:0.0123D) * (this.lastDist - MathUtil.getBaseMovementSpeed());
                     this.moveSpeed = this.lastDist - difference;
                     break label126;
                  }

                  label302: {
                     var20 = mc;
                     WorldClient theWorld = Minecraft.theWorld;
                     var20 = mc;
                     EntityPlayerSP thePlayer = Minecraft.thePlayer;
                     var20 = mc;
                     AxisAlignedBB boundingBox = Minecraft.thePlayer.getEntityBoundingBox();
                     double n2 = 0.0D;
                     Minecraft var10004 = mc;
                     if(theWorld.getCollidingBoundingBoxes(thePlayer, boundingBox.offset(n2, Minecraft.thePlayer.motionY, 0.0D)).size() <= 0) {
                        var20 = mc;
                        if(!Minecraft.thePlayer.isCollidedVertically) {
                           break label302;
                        }
                     }

                     this.level = 1;
                  }

                  this.moveSpeed = this.lastDist - this.lastDist / 159.0D;
                  break label126;
               }
            }

            double moveSpeed = Math.max(this.moveSpeed, MathUtil.getBaseMovementSpeed());
            this.moveSpeed = moveSpeed;
            if(strafe == 0.0F) {
               EventMove.x = (double)forward * moveSpeed * mx + (double)strafe * moveSpeed * mz;
               EventMove.z = (double)forward * moveSpeed * mz - (double)strafe * moveSpeed * mx;
            } else if(strafe != 0.0F) {
               EventMove.setMoveSpeed(moveSpeed);
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
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
         var10000 = mc;
         int amplifier = Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
         baseSpeed *= 1.0D + 0.2D * (double)(amplifier + 1);
      }

      return baseSpeed;
   }

   public static enum TeleMode {
      Hypixel;
   }
}
