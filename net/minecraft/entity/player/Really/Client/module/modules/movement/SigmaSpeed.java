package net.minecraft.entity.player.Really.Client.module.modules.movement;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
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
import net.minecraft.entity.player.Really.Client.utils.BlockUtils;
import net.minecraft.entity.player.Really.Client.utils.MoveUtils;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;
import net.minecraft.entity.player.Really.Client.utils.math.MathUtil;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Timer;
import net.minecraft.world.World;

public class SigmaSpeed extends Module {
   private Mode mode = new Mode("Mode", "Mode", SigmaSpeed.SigmaSpeedMode.values(), SigmaSpeed.SigmaSpeedMode.Bhop);
   private Option lagcheck = new Option("LagCheck", "LagCheck", Boolean.valueOf(true));
   private double[] values = new double[]{0.08D, 0.09316090325960147D, 1.688D, 2.149D, 0.66D};
   public static int stage;
   private double movementSpeed;
   private boolean firstjump;
   private int stupidAutisticTickCounting;
   private World world;
   double moveSpeed;
   private double distance;
   private int packetCounter;
   private TimerUtil deactivationDelay = new TimerUtil();
   private double posY;
   private int speedTick;
   private boolean legitHop = false;
   private double lastDist;
   private int tick;
   private TimerUtil timer = new TimerUtil();
   private double speed;
   int steps;
   private int level;

   public SigmaSpeed() {
      super("Hopper", new String[]{"zoom"}, ModuleType.Movement);
      this.setColor((new Color(99, 248, 91)).getRGB());
      this.addValues(new Value[]{this.mode, this.lagcheck});
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

   public void onDisable() {
      Timer var10000 = mc.timer;
      Timer.timerSpeed = 1.0F;
      this.tick = 0;
   }

   private boolean canZoom() {
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer.moving()) {
         var10000 = mc;
         if(Minecraft.thePlayer.onGround && !this.isInLiquid()) {
            return true;
         }
      }

      return false;
   }

   @EventHandler
   private void onUpdate(EventPreUpdate e) {
      this.setSuffix(this.mode.getValue());
      if(this.mode.getValue() == SigmaSpeed.SigmaSpeedMode.Hypixel || this.mode.getValue() == SigmaSpeed.SigmaSpeedMode.Bhop) {
         Minecraft var10000 = mc;
         Minecraft var10001 = mc;
         double xDist = Minecraft.thePlayer.posX - Minecraft.thePlayer.prevPosX;
         var10000 = mc;
         var10001 = mc;
         double zDist = Minecraft.thePlayer.posZ - Minecraft.thePlayer.prevPosZ;
         this.distance = Math.sqrt(xDist * xDist + zDist * zDist);
      }

      if(this.mode.getValue() == SigmaSpeed.SigmaSpeedMode.OnGround && this.canZoom()) {
         switch(stage) {
         case 1:
            e.setY(e.getY() + 0.4D);
            e.setOnground(false);
            ++stage;
            break;
         case 2:
            e.setY(e.getY() + 0.4D);
            e.setOnground(false);
            ++stage;
            break;
         default:
            stage = 1;
         }
      }

   }

   private boolean isBlockUnder(Material air) {
      return false;
   }

   private int getRandom(int i) {
      return 0;
   }

   public void setSpeed(double speed) {
      Minecraft var10000 = mc;
      Minecraft.thePlayer.motionX = -(Math.sin((double)this.getDirection()) * speed);
      var10000 = mc;
      Minecraft.thePlayer.motionZ = Math.cos((double)this.getDirection()) * speed;
   }

   public float getDirection() {
      Minecraft var10000 = mc;
      float yaw = Minecraft.thePlayer.rotationYaw;
      var10000 = mc;
      if(Minecraft.thePlayer.moveForward < 0.0F) {
         yaw += 180.0F;
      }

      float forward = 1.0F;
      var10000 = mc;
      if(Minecraft.thePlayer.moveForward < 0.0F) {
         forward = -0.5F;
      } else {
         var10000 = mc;
         if(Minecraft.thePlayer.moveForward > 0.0F) {
            forward = 0.5F;
         }
      }

      var10000 = mc;
      if(Minecraft.thePlayer.moveStrafing > 0.0F) {
         yaw -= 90.0F * forward;
      }

      var10000 = mc;
      if(Minecraft.thePlayer.moveStrafing < 0.0F) {
         yaw += 90.0F * forward;
      }

      yaw = yaw * 0.017453292F;
      return yaw;
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
         stage = -5;
         Client var10000 = Client.instance;
         Client.getModuleManager();
         if(ModuleManager.getModuleByName("SigmaSpeed").isEnabled()) {
            var10000 = Client.instance;
            Client.getModuleManager();
            ModuleManager.getModuleByName("SigmaSpeed").setEnabled(false);
         }
      }

   }

   @EventHandler
   private void onMove(EventMove e) {
      if(this.mode.getValue() == SigmaSpeed.SigmaSpeedMode.Hypixel) {
         if(!this.isInLiquid()) {
            if(this.canZoom()) {
               this.moveSpeed = this.getBaseMoveSpeed();
            }

            if(stage == 1) {
               Minecraft var10000 = mc;
               if(Minecraft.thePlayer.isCollidedVertically && this.canZoom()) {
                  this.moveSpeed = 1.5D + this.getBaseMoveSpeed() - 0.01D;
               }
            }

            if(this.canZoom() && stage == 2) {
               double jump = 0.418D;
               Minecraft var18 = mc;
               EventMove.setY(Minecraft.thePlayer.motionY = jump);
               this.moveSpeed *= 1.89D;
            } else if(stage == 3) {
               double diff = 0.66D * (this.distance - this.getBaseMoveSpeed());
               this.moveSpeed = this.distance - diff;
            } else {
               label160: {
                  Minecraft var15 = mc;
                  Minecraft var10001 = mc;
                  Minecraft var10002 = mc;
                  Minecraft var10004 = mc;
                  List collidingList = Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, Minecraft.thePlayer.boundingBox.offset(0.0D, Minecraft.thePlayer.motionY, 0.0D));
                  if(collidingList.size() <= 0) {
                     var15 = mc;
                     if(!Minecraft.thePlayer.isCollidedVertically) {
                        break label160;
                     }
                  }

                  if(stage > 0) {
                     var15 = mc;
                     stage = Minecraft.thePlayer.moving()?1:0;
                  }
               }

               this.moveSpeed = this.getBaseMoveSpeed() * 1.00000011920929D;
               this.moveSpeed = this.distance - this.distance / 159.0D;
            }

            this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
            Minecraft var19 = mc;
            Minecraft.thePlayer.setMoveSpeed(e, this.moveSpeed);
            if(stage > 0) {
               this.setMotion(e, this.moveSpeed);
            }

            MovementInput var20 = Minecraft.thePlayer.movementInput;
            float forward = MovementInput.moveForward;
            var20 = Minecraft.thePlayer.movementInput;
            float strafe = MovementInput.moveStrafe;
            float yaw = Minecraft.thePlayer.rotationYaw;
            if(forward == 0.0F && strafe == 0.0F) {
               Minecraft.thePlayer.motionX *= Minecraft.thePlayer.motionZ *= 0.0D;
               EventMove.x = 0.0D;
               EventMove.z = 0.0D;
            } else if(forward != 0.0F) {
               if(strafe >= 1.0F) {
                  yaw += forward > 0.0F?-45.0F:45.0F;
                  strafe = 0.0F;
               } else if(strafe <= -1.0F) {
                  yaw += forward > 0.0F?45.0F:-45.0F;
                  strafe = 0.0F;
               }

               if(forward > 0.0F) {
                  forward = 1.0F;
               } else if(forward < 0.0F) {
                  forward = -1.0F;
               }
            }

            if(forward == 0.0F && strafe == 0.0F) {
               EventMove.x = 0.0D;
               EventMove.z = 0.0D;
               Minecraft.thePlayer.motionX *= Minecraft.thePlayer.motionZ *= 0.0D;
            } else if(forward != 0.0F) {
               if(strafe >= 1.0F) {
                  float var22 = yaw + (forward > 0.0F?-45.0F:45.0F);
                  strafe = 0.0F;
               } else if(strafe <= -1.0F) {
                  float var23 = yaw + (forward > 0.0F?45.0F:-45.0F);
                  strafe = 0.0F;
               }

               if(forward > 0.0F) {
                  forward = 1.0F;
               } else if(forward < 0.0F) {
                  forward = -1.0F;
               }
            }

            ++stage;
         }
      } else if(this.mode.getValue() == SigmaSpeed.SigmaSpeedMode.OnGround && this.canZoom()) {
         switch(stage) {
         case 1:
            this.movementSpeed = 1.89D * MathUtil.getBaseMovementSpeed() - 0.01D;
            ++this.distance;
            if(this.distance == 1.0D) {
               EventMove.setY(e.getY() + 8.0E-6D);
            } else if(this.distance == 2.0D) {
               EventMove.setY(e.getY() - 8.0E-6D);
               this.distance = 0.0D;
            }
            break;
         case 2:
            this.movementSpeed = 1.2D * MathUtil.getBaseMovementSpeed() - 0.01D;
            break;
         default:
            this.movementSpeed = (double)((float)MathUtil.getBaseMovementSpeed());
         }

         this.movementSpeed = Math.max(this.movementSpeed, MathUtil.getBaseMovementSpeed());
         Minecraft var43 = mc;
         Minecraft.thePlayer.setMoveSpeed(e, this.movementSpeed);
         ++stage;
      } else if(this.mode.getValue() == SigmaSpeed.SigmaSpeedMode.Bhop) {
         Minecraft var24 = mc;
         if(Minecraft.thePlayer.moveForward == 0.0F) {
            var24 = mc;
            if(Minecraft.thePlayer.moveStrafing == 0.0F) {
               this.speed = MoveUtils.defaultSpeed();
            }
         }

         if(stage == 1) {
            var24 = mc;
            if(Minecraft.thePlayer.isCollidedVertically) {
               label902: {
                  var24 = mc;
                  if(Minecraft.thePlayer.moveForward == 0.0F) {
                     var24 = mc;
                     if(Minecraft.thePlayer.moveStrafing == 0.0F) {
                        break label902;
                     }
                  }

                  this.speed = 1.35D + MoveUtils.defaultSpeed() - 0.01D;
               }
            }
         }

         label947: {
            label2947: {
               if(!this.isInLiquid() && stage == 2) {
                  var24 = mc;
                  if(Minecraft.thePlayer.isCollidedVertically && MoveUtils.isOnGround(0.01D)) {
                     var24 = mc;
                     if(Minecraft.thePlayer.moveForward != 0.0F) {
                        break label947;
                     }

                     var24 = mc;
                     if(Minecraft.thePlayer.moveStrafing != 0.0F) {
                        break label947;
                     }
                  }
               }

               if(stage == 3) {
                  double difference = 0.66D * (this.lastDist - MoveUtils.defaultSpeed());
                  this.speed = this.lastDist - difference;
                  break label947;
               }

               label1148: {
                  var24 = mc;
                  Minecraft var44 = mc;
                  Minecraft var45 = mc;
                  Minecraft var46 = mc;
                  List collidingList = Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, Minecraft.thePlayer.boundingBox.offset(0.0D, Minecraft.thePlayer.motionY, 0.0D));
                  if(collidingList.size() <= 0) {
                     var24 = mc;
                     if(!Minecraft.thePlayer.isCollidedVertically) {
                        break label1148;
                     }
                  }

                  if(stage > 0) {
                     int var36;
					label1219: {
                        var24 = mc;
                        if(Minecraft.thePlayer.moveForward == 0.0F) {
                           var24 = mc;
                           if(Minecraft.thePlayer.moveStrafing == 0.0F) {
                              var36 = 0;
                              break label1219;
                           }
                        }

                        var36 = 1;
                     }

                     stage = var36;
                  }
               }

               this.speed = this.lastDist - this.lastDist / 159.0D;
               break label947;
            }
         }

         this.speed = Math.max(this.speed, MoveUtils.defaultSpeed());
         if(stage > 0) {
            if(BlockUtils.isInLiquid()) {
               this.speed = 0.1D;
            }

            this.setMotion(e, this.speed);
         }

         var24 = mc;
         if(Minecraft.thePlayer.moveForward == 0.0F) {
            var24 = mc;
            if(Minecraft.thePlayer.moveStrafing == 0.0F) {
               return;
            }
         }

         ++stage;
      }

   }

   private boolean MovementInput() {
      Minecraft var10000 = mc;
      if(!Minecraft.gameSettings.keyBindForward.pressed) {
         var10000 = mc;
         if(!Minecraft.gameSettings.keyBindLeft.pressed) {
            var10000 = mc;
            if(!Minecraft.gameSettings.keyBindRight.pressed) {
               var10000 = mc;
               if(!Minecraft.gameSettings.keyBindBack.pressed) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   public void setMoveSpeed(EventMove event, double speed) {
      Minecraft var10000 = mc;
      MovementInput movementInput = Minecraft.thePlayer.movementInput;
      double forward = (double)MovementInput.moveForward;
      double strafe = (double)MovementInput.moveStrafe;
      var10000 = mc;
      float yaw = Minecraft.thePlayer.rotationYaw;
      if(forward == 0.0D && strafe == 0.0D) {
         EventMove.x = 0.0D;
         EventMove.x = 0.0D;
      } else {
         if(forward != 0.0D) {
            var10000 = mc;
            Minecraft.thePlayer.setSpeed(0.279D);
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

         EventMove.x = forward * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F))) + strafe * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F)));
         EventMove.z = forward * speed * Math.sin(Math.toRadians((double)(yaw + 90.0F))) - strafe * speed * Math.cos(Math.toRadians((double)(yaw + 90.0F)));
      }

   }

   private double defaultSpeed() {
      double baseSpeed = 0.2873D;
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
         var10000 = mc;
         int amplifier = Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
         baseSpeed *= 1.0D + 0.2D * (double)(amplifier + 1);
      }

      return baseSpeed;
   }

   public double round(double var1, int var3) {
      if(var3 < 0) {
         throw new IllegalArgumentException();
      } else {
         BigDecimal var4 = new BigDecimal(var1);
         var4 = var4.setScale(var3, RoundingMode.HALF_UP);
         return var4.doubleValue();
      }
   }

   private void setMotion(EventMove em, double speed) {
      Minecraft var10000 = mc;
      MovementInput var9 = Minecraft.thePlayer.movementInput;
      double forward = (double)MovementInput.moveForward;
      Minecraft var10 = mc;
      MovementInput var11 = Minecraft.thePlayer.movementInput;
      double strafe = (double)MovementInput.moveStrafe;
      Minecraft var12 = mc;
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

   double getBaseMoveSpeed() {
      double baseSpeed = 0.2873D;
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
         var10000 = mc;
         int amplifier = Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
         baseSpeed *= 1.0D + 0.2D * (double)(amplifier + 1);
      }

      return baseSpeed;
   }

   static enum SigmaSpeedMode {
      Hypixel,
      OnGround,
      Bhop;
   }
}
