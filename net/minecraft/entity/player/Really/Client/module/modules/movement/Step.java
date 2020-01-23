package net.minecraft.entity.player.Really.Client.module.modules.movement;

import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.module.modules.movement.StepTimer;
import net.minecraft.entity.player.Really.Client.utils.Helper;
import net.minecraft.entity.player.Really.Client.utils.PlayerUtil;
import net.minecraft.entity.player.Really.Client.utils.Timer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MovementInput;

public class Step extends Module {
   private Mode SMODE = new Mode("Step Mode", "Step Mode", Step.SMode.values(), Step.SMode.NCP);
   private Numbers STEP = new Numbers("HEIGHT", "HEIGHT", Double.valueOf(0.5D), Double.valueOf(0.0D), Double.valueOf(10.0D), Double.valueOf(0.1D));
   private Numbers NCPHEIGHT = new Numbers("HEIGHT2", "HEIGHT2", Double.valueOf(0.5D), Double.valueOf(0.0D), Double.valueOf(2.5D), Double.valueOf(0.1D));
   private Numbers TIMER = new Numbers("TIMER", "TIMER", Double.valueOf(0.6D), Double.valueOf(0.0D), Double.valueOf(0.6D), Double.valueOf(0.01D));
   private Numbers DELAY = new Numbers("DELAY", "DELAY", Double.valueOf(0.5D), Double.valueOf(0.0D), Double.valueOf(2.0D), Double.valueOf(0.1D));
   boolean resetTimer;
   StepTimer time = new StepTimer();
   public static Timer lastStep = new Timer();

   public Step() {
      super("Step", new String[]{"Step", "autojump"}, ModuleType.Movement);
      this.addValues(new Value[]{this.SMODE, this.STEP, this.NCPHEIGHT, this.TIMER, this.DELAY});
   }

   public void onEnable() {
      this.resetTimer = false;
      super.onEnable();
   }

   public void onDisable() {
      net.minecraft.util.Timer var10000 = Minecraft.getMinecraft().timer;
      net.minecraft.util.Timer.timerSpeed = 1.0F;
      super.onDisable();
   }

   @EventHandler
   public void onEvent(EventPreUpdate event) {
      this.setSuffix(this.SMODE.getValue());
      Minecraft.getMinecraft();
      double x = Minecraft.thePlayer.posX;
      Minecraft.getMinecraft();
      double y = Minecraft.thePlayer.posY;
      Minecraft.getMinecraft();
      double z = Minecraft.thePlayer.posZ;
      if(!Timer.delay(60) && this.SMODE.getValue() == Step.SMode.Cubecraft) {
         Minecraft.getMinecraft();
         Minecraft.thePlayer.setSprinting(true);
      }

      float stepValue = 1.5F;
      float timer = ((Double)this.TIMER.getValue()).floatValue();
      float delay = ((Double)this.DELAY.getValue()).floatValue() * 1000.0F;
      if(this.SMODE.getValue() == Step.SMode.Vanilla || this.SMODE.getValue() == Step.SMode.Cubecraft) {
         stepValue = ((Double)this.STEP.getValue()).floatValue();
      }

      if(this.SMODE.getValue() == Step.SMode.NCP) {
         stepValue = ((Double)this.NCPHEIGHT.getValue()).floatValue();
      }

      if(this.resetTimer) {
         this.resetTimer = !this.resetTimer;
         net.minecraft.util.Timer var10000 = Minecraft.getMinecraft().timer;
         net.minecraft.util.Timer.timerSpeed = 1.0F;
      }

      if(!PlayerUtil.isInLiquid()) {
         Minecraft.getMinecraft();
         if(Minecraft.thePlayer.isCollidedVertically) {
            Minecraft.getMinecraft();
            if(!Minecraft.gameSettings.keyBindJump.isKeyDown() && this.time.delay(delay)) {
               Minecraft.getMinecraft();
               double var16 = Minecraft.thePlayer.getEntityBoundingBox().minY;
               Minecraft.getMinecraft();
               double rheight = var16 - Minecraft.thePlayer.posY;
               Helper.sendMessage("" + rheight);
               boolean canStep = rheight >= 0.625D;
               if(canStep) {
                  Timer.reset();
                  this.time.reset();
               }

               if(this.SMODE.getValue() == Step.SMode.NCP) {
                  if(canStep) {
                     net.minecraft.util.Timer var17 = Minecraft.getMinecraft().timer;
                     net.minecraft.util.Timer.timerSpeed = timer - (rheight >= 1.0D?Math.abs(1.0F - (float)rheight) * timer * 0.55F:0.0F);
                     var17 = Minecraft.getMinecraft().timer;
                     if(net.minecraft.util.Timer.timerSpeed <= 0.05F) {
                        var17 = Minecraft.getMinecraft().timer;
                        net.minecraft.util.Timer.timerSpeed = 0.05F;
                     }

                     this.resetTimer = true;
                     this.ncpStep(rheight);
                  }
               } else if(this.SMODE.getValue() == Step.SMode.Cubecraft) {
                  if(canStep) {
                     this.cubeStep(rheight);
                     this.resetTimer = true;
                     net.minecraft.util.Timer var20 = Minecraft.getMinecraft().timer;
                     net.minecraft.util.Timer.timerSpeed = rheight < 2.0D?0.6F:0.3F;
                  }
               } else if(this.SMODE.getValue() == Step.SMode.AAC) {
                  if(canStep) {
                     if(rheight < 1.1D) {
                        net.minecraft.util.Timer var21 = Minecraft.getMinecraft().timer;
                        net.minecraft.util.Timer.timerSpeed = 0.5F;
                        this.resetTimer = true;
                     } else {
                        net.minecraft.util.Timer var22 = Minecraft.getMinecraft().timer;
                        net.minecraft.util.Timer.timerSpeed = 1.0F - (float)rheight * 0.57F;
                        this.resetTimer = true;
                     }

                     this.aacStep(rheight);
                  }
               } else if(this.SMODE.getValue() == Step.SMode.Vanilla) {
                  return;
               }
            }
         }
      }

   }

   void cubeStep(double height) {
      Minecraft.getMinecraft();
      double posX = Minecraft.thePlayer.posX;
      Minecraft.getMinecraft();
      double posZ = Minecraft.thePlayer.posZ;
      Minecraft.getMinecraft();
      double y = Minecraft.thePlayer.posY;
      double first = 0.42D;
      double second = 0.75D;
      Minecraft.getMinecraft();
      Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, y + first, posZ, false));
   }

   void ncpStep(double height) {
      List<Double> offset = Arrays.asList(new Double[]{Double.valueOf(0.42D), Double.valueOf(0.333D), Double.valueOf(0.248D), Double.valueOf(0.083D), Double.valueOf(-0.078D)});
      Minecraft.getMinecraft();
      double posX = Minecraft.thePlayer.posX;
      Minecraft.getMinecraft();
      double posZ = Minecraft.thePlayer.posZ;
      Minecraft.getMinecraft();
      double y = Minecraft.thePlayer.posY;
      if(height < 1.1D) {
         double first = 0.42D;
         double second = 0.75D;
         if(height != 1.0D) {
            first *= height;
            second *= height;
            if(first > 0.425D) {
               first = 0.425D;
            }

            if(second > 0.78D) {
               second = 0.78D;
            }

            if(second < 0.49D) {
               second = 0.49D;
            }
         }

         if(first == 0.42D) {
            first = 0.41999998688698D;
         }

         Minecraft.getMinecraft();
         Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, y + first, posZ, false));
         if(y + second < y + height) {
            Minecraft.getMinecraft();
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, y + second, posZ, false));
         }

      } else {
         if(height < 1.6D) {
            for(int i = 0; i < offset.size(); ++i) {
               double off = ((Double)offset.get(i)).doubleValue();
               y += off;
               Minecraft.getMinecraft();
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, y, posZ, false));
            }
         } else if(height < 2.1D) {
            double[] heights = new double[]{0.425D, 0.821D, 0.699D, 0.599D, 1.022D, 1.372D, 1.652D, 1.869D};

            for(double off : heights) {
               Minecraft.getMinecraft();
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, y + off, posZ, false));
            }
         } else {
            double[] heights = new double[]{0.425D, 0.821D, 0.699D, 0.599D, 1.022D, 1.372D, 1.652D, 1.869D, 2.019D, 1.907D};

            for(double off : heights) {
               Minecraft.getMinecraft();
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, y + off, posZ, false));
            }
         }

      }
   }

   void aacStep(double height) {
      Minecraft.getMinecraft();
      double posX = Minecraft.thePlayer.posX;
      Minecraft.getMinecraft();
      double posY = Minecraft.thePlayer.posY;
      Minecraft.getMinecraft();
      double posZ = Minecraft.thePlayer.posZ;
      if(height < 1.1D) {
         double first = 0.42D;
         double second = 0.75D;
         if(height > 1.0D) {
            first *= height;
            second *= height;
            if(first > 0.4349D) {
               first = 0.4349D;
            } else if(first < 0.405D) {
               first = 0.405D;
            }
         }

         Minecraft.getMinecraft();
         Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + first, posZ, false));
         if(posY + second < posY + height) {
            Minecraft.getMinecraft();
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, posY + second, posZ, false));
         }

      } else {
         List<Double> offset = Arrays.asList(new Double[]{Double.valueOf(0.434999999999998D), Double.valueOf(0.360899999999992D), Double.valueOf(0.290241999999991D), Double.valueOf(0.220997159999987D), Double.valueOf(0.13786084000003104D), Double.valueOf(0.055D)});
         Minecraft.getMinecraft();
         double y = Minecraft.thePlayer.posY;

         for(int i = 0; i < offset.size(); ++i) {
            double off = ((Double)offset.get(i)).doubleValue();
            y += off;
            Minecraft.getMinecraft();
            if(y > Minecraft.thePlayer.posY + height) {
               Minecraft.getMinecraft();
               double x = Minecraft.thePlayer.posX;
               Minecraft.getMinecraft();
               double z = Minecraft.thePlayer.posZ;
               Minecraft.getMinecraft();
               MovementInput var38 = Minecraft.thePlayer.movementInput;
               double forward = (double)MovementInput.moveForward;
               Minecraft.getMinecraft();
               var38 = Minecraft.thePlayer.movementInput;
               double strafe = (double)MovementInput.moveStrafe;
               Minecraft.getMinecraft();
               float YAW = Minecraft.thePlayer.rotationYaw;
               double speed = 0.3D;
               if(forward != 0.0D && strafe != 0.0D) {
                  speed -= 0.09D;
               }

               x = x + (forward * speed * Math.cos(Math.toRadians((double)(YAW + 90.0F))) + strafe * speed * Math.sin(Math.toRadians((double)(YAW + 90.0F)))) * 1.0D;
               z = z + (forward * speed * Math.sin(Math.toRadians((double)(YAW + 90.0F))) - strafe * speed * Math.cos(Math.toRadians((double)(YAW + 90.0F)))) * 1.0D;
               Minecraft.getMinecraft();
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
               break;
            }

            if(i == offset.size() - 1) {
               Minecraft.getMinecraft();
               double x = Minecraft.thePlayer.posX;
               Minecraft.getMinecraft();
               double z = Minecraft.thePlayer.posZ;
               Minecraft.getMinecraft();
               MovementInput var10000 = Minecraft.thePlayer.movementInput;
               double forward = (double)MovementInput.moveForward;
               Minecraft.getMinecraft();
               var10000 = Minecraft.thePlayer.movementInput;
               double strafe = (double)MovementInput.moveStrafe;
               Minecraft.getMinecraft();
               float YAW = Minecraft.thePlayer.rotationYaw;
               double speed = 0.3D;
               if(forward != 0.0D && strafe != 0.0D) {
                  speed -= 0.09D;
               }

               x = x + (forward * speed * Math.cos(Math.toRadians((double)(YAW + 90.0F))) + strafe * speed * Math.sin(Math.toRadians((double)(YAW + 90.0F)))) * 1.0D;
               z = z + (forward * speed * Math.sin(Math.toRadians((double)(YAW + 90.0F))) - strafe * speed * Math.cos(Math.toRadians((double)(YAW + 90.0F)))) * 1.0D;
               Minecraft.getMinecraft();
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, false));
            } else {
               Minecraft.getMinecraft();
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, y, posZ, false));
            }
         }

      }
   }

   static enum SMode {
      NCP,
      Vanilla,
      AAC,
      Cubecraft;
   }
}
