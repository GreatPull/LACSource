package net.minecraft.entity.player.Really.Client.module.modules.movement;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventMove;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.MoveUtils;
import net.minecraft.entity.player.Really.Client.utils.math.MathUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Timer;

public class Longjump extends Module {
   private Mode mode = new Mode("Mode", "mode", Longjump.JumpMode.values(), Longjump.JumpMode.NCP);
   private Option OFF = new Option("OFF", "OFF", Boolean.valueOf(true));
   private Option glide = new Option("glide", "glide", Boolean.valueOf(true));
   private int stage;
   private float air;
   private float groundTicks;
   private double moveSpeed;
   private double lastDist;

   public Longjump() {
      super("LongJump", new String[]{"lj", "jumpman", "jump"}, ModuleType.Movement);
      this.addValues(new Value[]{this.mode, this.OFF, this.glide});
      this.setColor((new Color(76, 67, 216)).getRGB());
   }

   public void onDisable() {
      Timer.timerSpeed = 1.0F;
      if(this.mode.getValue() == Longjump.JumpMode.Area51) {
         Minecraft.thePlayer.motionX = 0.0D;
         Minecraft.thePlayer.motionZ = 0.0D;
      }

      if(Minecraft.thePlayer != null) {
         this.moveSpeed = this.getBaseMoveSpeed();
      }

      this.lastDist = 0.0D;
      this.stage = 0;
   }

   public void onEnable() {
      this.groundTicks = 0.0F;
      super.onEnable();
   }

   @EventHandler
   private void onUpdate(EventPreUpdate e2) {
      this.setSuffix(this.mode.getValue());
      if(this.mode.getValue() == Longjump.JumpMode.OldGuardian) {
         if(Minecraft.thePlayer.moving() && Minecraft.thePlayer.onGround) {
            Minecraft.thePlayer.motionY = 0.44D;
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 1.0E-9D, Minecraft.thePlayer.posZ, Minecraft.thePlayer.onGround));
            Minecraft.thePlayer.setSpeed(7.0D);
         } else {
            Minecraft.thePlayer.setSpeed(Math.sqrt(Minecraft.thePlayer.motionX * Minecraft.thePlayer.motionX + Minecraft.thePlayer.motionZ * Minecraft.thePlayer.motionZ));
         }
      } else if(this.mode.getValue() == Longjump.JumpMode.Hypixel) {
         boolean glide = ((Boolean)this.glide.getValue()).booleanValue();
         float x2 = 0.7F + (float)MoveUtils.getSpeedEffect() * 0.45F;
         if((Minecraft.thePlayer.moveForward != 0.0F || Minecraft.thePlayer.moveStrafing != 0.0F) && Minecraft.thePlayer.onGround) {
            if(((Boolean)this.OFF.getValue()).booleanValue() && this.groundTicks > 0.0F) {
               this.groundTicks = 0.0F;
               this.setEnabled(false);
               return;
            }

            ++this.groundTicks;
            MoveUtils.setMotion(0.15D);
            Minecraft.thePlayer.jump();
            this.stage = 1;
         }

         if(MoveUtils.isOnGround(0.001D)) {
            this.air = 0.0F;
         } else {
            if(Minecraft.thePlayer.isCollidedVertically) {
               this.stage = 0;
            }

            if(this.stage > 0 && this.stage <= 3 && glide) {
               Minecraft.thePlayer.onGround = true;
            }

            double speed = 0.95D + (double)MoveUtils.getSpeedEffect() * 0.2D - (double)(this.air / 25.0F);
            if(glide) {
               speed = 1.1D + (double)((float)MoveUtils.getSpeedEffect() * 0.2F) - (double)(this.air / 20.0F);
            }

            if(speed < MoveUtils.defaultSpeed() - 0.05D) {
               speed = MoveUtils.defaultSpeed() - 0.05D;
            }

            if(this.stage < 4 && glide) {
               speed = MoveUtils.defaultSpeed();
            }

            MoveUtils.setMotion(speed);
            Minecraft.thePlayer.motionY = glide?this.getMotion(this.stage):this.getOldMotion(this.stage);
            if(this.stage > 0) {
               ++this.stage;
            }

            this.air += x2;
         }
      } else if(this.mode.getValue() == Longjump.JumpMode.Area51) {
         if(Minecraft.thePlayer.moving()) {
            Timer.timerSpeed = 0.33F;
            if(Minecraft.thePlayer.onGround) {
               Minecraft.thePlayer.setSpeed(5.0D);
               Minecraft.thePlayer.motionY = 0.45500001311302185D;
            } else {
               Minecraft.thePlayer.setSpeed(7.0D);
            }
         } else {
            Timer.timerSpeed = 0.33F;
            Minecraft.thePlayer.motionX = 0.0D;
            Minecraft.thePlayer.motionZ = 0.0D;
         }
      } else if(this.mode.getValue() == Longjump.JumpMode.Janitor && EventPreUpdate.getType() == 0 && Minecraft.thePlayer.moving() && Minecraft.thePlayer.onGround) {
         e2.setY(e2.getY() + (Minecraft.thePlayer.ticksExisted % 1 == 0?MathUtil.getHighestOffset(0.1D):0.0D));
      } else if(EventPreUpdate.getType() == 0) {
         double xDist = Minecraft.thePlayer.posX - Minecraft.thePlayer.prevPosX;
         double zDist = Minecraft.thePlayer.posZ - Minecraft.thePlayer.prevPosZ;
         this.moveSpeed = 0.5D * this.getBaseMoveSpeed() - 0.01D;
         this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
      }

   }

   double getOldMotion(int stage) {
      if(Minecraft.thePlayer.moveStrafing == 0.0F && Minecraft.thePlayer.moveForward == 0.0F) {
         boolean var6 = false;
      } else {
         boolean var10000 = true;
      }

      double[] mot = new double[]{0.345D, 0.2699D, 0.183D, 0.103D, 0.024D, -0.008D, -0.04D, -0.072D, -0.104D, -0.13D, -0.019D, -0.097D};
      double[] notMoving = new double[]{0.345D, 0.2699D, 0.183D, 0.103D, 0.024D, -0.008D, -0.04D, -0.072D, -0.14D, -0.17D, -0.019D, -0.13D};
      --stage;
      return stage >= 0 && stage < mot.length?((Minecraft.thePlayer.moveStrafing != 0.0F || Minecraft.thePlayer.moveForward != 0.0F) && !Minecraft.thePlayer.isCollidedHorizontally?mot[stage]:notMoving[stage]):Minecraft.thePlayer.motionY;
   }

   double getMotion(int stage) {
      if(Minecraft.thePlayer.moveStrafing == 0.0F && Minecraft.thePlayer.moveForward == 0.0F) {
         boolean var7 = false;
      } else {
         boolean var10000 = true;
      }

      double[] mot = new double[]{0.396D, -0.122D, -0.1D, 0.423D, 0.35D, 0.28D, 0.217D, 0.15D, 0.025D, -0.00625D, -0.038D, -0.0693D, -0.102D, -0.13D, -0.018D, -0.1D, -0.117D, -0.14532D, -0.1334D, -0.1581D, -0.183141D, -0.170695D, -0.195653D, -0.221D, -0.209D, -0.233D, -0.25767D, -0.314917D, -0.371019D, -0.426D};
      --stage;
      if(stage >= 0 && stage < mot.length) {
         double motion = mot[stage];
         return motion;
      } else {
         return Minecraft.thePlayer.motionY;
      }
   }

   @EventHandler
   private void onMove(EventMove e2) {
      if(this.mode.getValue() == Longjump.JumpMode.NCP) {
         boolean glide = ((Boolean)this.glide.getValue()).booleanValue();
         Minecraft.thePlayer.lastReportedPosY = 0.0D;
         float x2 = 1.0F + (float)MoveUtils.getSpeedEffect() * 0.45F;
         if((Minecraft.thePlayer.moveForward != 0.0F || Minecraft.thePlayer.moveStrafing != 0.0F) && Minecraft.thePlayer.onGround) {
            if(((Boolean)this.OFF.getValue()).booleanValue() && this.groundTicks > 0.0F) {
               this.groundTicks = 0.0F;
               this.setEnabled(false);
               return;
            }

            this.stage = 1;
            ++this.groundTicks;
            Minecraft.thePlayer.jump();
         }

         if(MoveUtils.isOnGround(0.01D)) {
            this.air = 0.0F;
         } else {
            if(Minecraft.thePlayer.isCollidedVertically) {
               this.stage = 0;
            }

            if(this.stage > 0 && this.stage <= 3 && glide) {
               Minecraft.thePlayer.onGround = true;
            }

            double speed;
            if((speed = (double)(0.75F + (float)MoveUtils.getSpeedEffect() * 0.2F - this.air / 25.0F)) < MoveUtils.defaultSpeed()) {
               speed = MoveUtils.defaultSpeed();
            }

            if(glide && (speed = (double)(0.8F + (float)MoveUtils.getSpeedEffect() * 0.2F - this.air / 25.0F)) < MoveUtils.defaultSpeed()) {
               speed = MoveUtils.defaultSpeed();
            }

            Minecraft.thePlayer.jumpMovementFactor = 0.0F;
            if(this.stage < 4 && glide) {
               speed = MoveUtils.defaultSpeed();
            }

            MoveUtils.setMotion(speed);
            Minecraft.thePlayer.motionY = glide?this.getMotion(this.stage):this.getOldMotion(this.stage);
            if(this.stage > 0) {
               ++this.stage;
            }

            this.air += x2;
         }
      } else if(this.mode.getValue() == Longjump.JumpMode.Janitor && Minecraft.thePlayer.moving()) {
         this.moveSpeed = MathUtil.getBaseMovementSpeed() * (double)(Minecraft.thePlayer.ticksExisted % 2 != 0?3:4);
         double x2 = -Math.sin((double)Minecraft.thePlayer.getDirection()) * this.moveSpeed;
         double z2 = Math.cos((double)Minecraft.thePlayer.getDirection()) * this.moveSpeed;
         EventMove.setX(x2);
         EventMove.setZ(z2);
         if(Minecraft.thePlayer.onGround) {
            Minecraft.thePlayer.motionY = 0.415D;
            EventMove.setY(0.415D);
         }

         this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
         MovementInput movementInput = Minecraft.thePlayer.movementInput;
         float forward = MovementInput.moveForward;
         float strafe = MovementInput.moveStrafe;
         float yaw = Minecraft.thePlayer.rotationYaw;
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

         double mx3 = Math.cos(Math.toRadians((double)(yaw + 90.0F)));
         double mz3 = Math.sin(Math.toRadians((double)(yaw + 90.0F)));
         EventMove.x = (double)forward * this.moveSpeed * mx3 + (double)strafe * this.moveSpeed * mz3;
         EventMove.z = (double)forward * this.moveSpeed * mz3 - (double)strafe * this.moveSpeed * mx3;
         if(forward == 0.0F && strafe == 0.0F) {
            EventMove.x = 0.0D;
            EventMove.z = 0.0D;
         } else if(forward != 0.0F) {
            if(strafe >= 1.0F) {
               float var10000 = yaw + (float)(forward > 0.0F?-45:45);
               strafe = 0.0F;
            } else if(strafe <= -1.0F) {
               float var21 = yaw + (float)(forward > 0.0F?45:-45);
               strafe = 0.0F;
            }

            if(forward > 0.0F) {
               forward = 1.0F;
            } else if(forward < 0.0F) {
               forward = -1.0F;
            }
         }
      } else if(this.mode.getValue() == Longjump.JumpMode.Guardian && Minecraft.thePlayer.moving()) {
         if(Minecraft.thePlayer.moveForward == 0.0F && Minecraft.thePlayer.moveStrafing == 0.0F) {
            Minecraft.thePlayer.motionX = 0.0D;
            Minecraft.thePlayer.motionZ = 0.0D;
         } else if(Minecraft.thePlayer.onGround) {
            for(int i2 = 0; i2 < 20; ++i2) {
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 1.0E-9D, Minecraft.thePlayer.posZ, Minecraft.thePlayer.onGround));
            }

            Minecraft.thePlayer.motionY = 0.4D;
            EventMove.y = 0.4D;
            Minecraft.thePlayer.setSpeed(8.0D);
         }
      }

   }

   double getBaseMoveSpeed() {
      double baseSpeed = 0.2873D;
      if(Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
         int amplifier = Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
         baseSpeed *= 1.0D + 0.2D * (double)(amplifier + 1);
      }

      return baseSpeed;
   }

   static enum JumpMode {
      NCP,
      OldGuardian,
      Guardian,
      Janitor,
      Area51,
      Hypixel;
   }
}
