package net.minecraft.entity.player.Really.Client.module.modules.movement;

import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockBarrier;
import net.minecraft.block.BlockGlass;
import net.minecraft.block.BlockIce;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockPackedIce;
import net.minecraft.block.BlockSlab;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.BlockStairs;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
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
import net.minecraft.entity.player.Really.Client.module.modules.combat.Aura;
import net.minecraft.entity.player.Really.Client.module.modules.combat.Criticals;
import net.minecraft.entity.player.Really.Client.module.modules.movement.Scaffold;
import net.minecraft.entity.player.Really.Client.utils.BlockUtils;
import net.minecraft.entity.player.Really.Client.utils.MoveUtils;
import net.minecraft.entity.player.Really.Client.utils.PlayerUtil;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;
import net.minecraft.entity.player.Really.Client.utils.math.MathUtil;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;
import net.minecraft.util.Timer;
import net.minecraft.world.World;

public class Hopper extends Module {
   private double delay3 = 1.7D;
   private Minecraft var10000;
   private Minecraft var10001;
   private double zDist;
   private double xDist;
   private Mode mode = new Mode("Mode", "Mode", Hopper.SigmaSpeedMode.values(), Hopper.SigmaSpeedMode.Bhop);
   private Option lagcheck = new Option("LagCheck", "LagCheck", Boolean.valueOf(true));
   private Option HypixelBoost = new Option("HypixelBoost", "HypixelBoost", Boolean.valueOf(true));
   private double[] values = new double[]{0.08D, 0.09316090325960147D, 1.688D, 2.149D, 0.66D};
   public static int stage;
   private int stage2 = 1;
   private int stag = 0;
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
   private List collidingList;
   public boolean shouldslow = false;
   private TimerUtil timer = new TimerUtil();
   private double difference;
   private double speed;
   int steps;
   private int level;
   public static int aacCount;
   boolean collided = false;
   boolean lessSlow;
   TimerUtil aac = new TimerUtil();
   TimerUtil lastCheck = new TimerUtil();
   TimerUtil lastFall = new TimerUtil();
   double less;
   double stair;
   private int counter = 0;

   public Hopper() {
      super("Hopper", new String[]{"zoom"}, ModuleType.Movement);
      this.setColor((new Color(99, 248, 91)).getRGB());
      this.addValues(new Value[]{this.mode, this.lagcheck, this.HypixelBoost});
   }

   private boolean isInLiquid() {
      Minecraft var10000x = mc;
      if(Minecraft.thePlayer == null) {
         return false;
      } else {
         var10000x = mc;
         int x = MathHelper.floor_double(Minecraft.thePlayer.boundingBox.minX);

         while(true) {
            Minecraft var10001x = mc;
            if(x >= MathHelper.floor_double(Minecraft.thePlayer.boundingBox.maxX) + 1) {
               return false;
            }

            var10000x = mc;
            int z = MathHelper.floor_double(Minecraft.thePlayer.boundingBox.minZ);

            while(true) {
               var10001x = mc;
               if(z >= MathHelper.floor_double(Minecraft.thePlayer.boundingBox.maxZ) + 1) {
                  ++x;
                  break;
               }

               Minecraft var10003 = mc;
               BlockPos pos = new BlockPos(x, (int)Minecraft.thePlayer.boundingBox.minY, z);
               var10000x = mc;
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
      Timer var10000x = mc.timer;
      Timer.timerSpeed = 1.0F;
      this.tick = 0;
      aacCount = 0;
   }

   public void onEnable() {
      Minecraft var10000x = mc;
      boolean player = Minecraft.thePlayer == null;
      boolean var10001x;
      if(player) {
         var10001x = false;
      } else {
         Minecraft var4 = mc;
         var10001x = Minecraft.thePlayer.isCollidedHorizontally;
      }

      this.collided = var10001x;
      this.lessSlow = false;
      var10000x = mc;
      if(Minecraft.thePlayer != null) {
         this.speed = MoveUtils.defaultSpeed();
      }

      this.less = 0.0D;
      this.lastDist = 0.0D;
      stage = 2;
      Timer var3 = mc.timer;
      Timer.timerSpeed = 1.0F;
      super.onEnable();
   }

   private boolean canZoom() {
      Minecraft var10000x = mc;
      if(Minecraft.thePlayer.moving()) {
         var10000x = mc;
         if(Minecraft.thePlayer.onGround && !this.isInLiquid()) {
            return true;
         }
      }

      return false;
   }

   public double getDistanceToFall() {
      double distance = 0.0D;

      for(double i = Minecraft.thePlayer.posY; i > 0.0D && i >= 0.0D; i -= 0.1D) {
         Block block = BlockUtils.getBlock(new BlockPos(Minecraft.thePlayer.posX, i, Minecraft.thePlayer.posZ));
         if(block.getMaterial() != Material.air && block.isCollidable() && (block.isFullBlock() || block instanceof BlockSlab || block instanceof BlockBarrier || block instanceof BlockStairs || block instanceof BlockGlass || block instanceof BlockStainedGlass)) {
            if(block instanceof BlockSlab) {
               i -= 0.5D;
            }

            distance = i;
            break;
         }
      }

      return Minecraft.thePlayer.posY - distance;
   }

   public void freezePlayer() {
      Minecraft var10000x = mc;
      Minecraft var10001x = mc;
      double var2 = Minecraft.thePlayer.posX + 1.0D;
      Minecraft var10002 = mc;
      Minecraft var10003 = mc;
      Minecraft.thePlayer.setPosition(var2, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ + 1.0D);
      var10000x = mc;
      Minecraft var3 = mc;
      var10002 = mc;
      var10003 = mc;
      Minecraft.thePlayer.setPosition(Minecraft.thePlayer.prevPosX, Minecraft.thePlayer.posY, Minecraft.thePlayer.prevPosZ);
   }

   @EventHandler
   private void onUpdate(EventPreUpdate e) {
      this.setSuffix(this.mode.getValue());
      Client var10000x = Client.instance;
      Client.getModuleManager();
      Scaffold i3 = (Scaffold)ModuleManager.getModuleByName("Scaffold");
      if(!i3.isEnabled() || !((Boolean)Scaffold.Lag.getValue()).booleanValue()) {
         var10000x = Client.instance;
         Client.getModuleManager();
         Criticals i4 = (Criticals)ModuleManager.getModuleByName("Criticals");
         if(!i4.isEnabled() || !((Boolean)Criticals.StopSpeed.getValue()).booleanValue() || Aura.curTarget == null) {
            if(this.mode.getValue() == Hopper.SigmaSpeedMode.HypixelTick) {
               Minecraft var10001x = mc;
               Minecraft var10002 = mc;
               double var10 = Minecraft.thePlayer.posX - Minecraft.thePlayer.prevPosX;
               var10002 = mc;
               Minecraft var10003 = mc;
               var10 = var10 * (Minecraft.thePlayer.posX - Minecraft.thePlayer.prevPosX);
               var10002 = mc;
               var10003 = mc;
               double var14 = Minecraft.thePlayer.posZ - Minecraft.thePlayer.prevPosZ;
               var10003 = mc;
               Minecraft var10004 = mc;
               this.lastDist = Math.sqrt(var10 + var14 * (Minecraft.thePlayer.posZ - Minecraft.thePlayer.prevPosZ));
            }

            if(this.mode.getValue() == Hopper.SigmaSpeedMode.Hypixel) {
               double xDist = Minecraft.thePlayer.posX - Minecraft.thePlayer.prevPosX;
               double zDist = Minecraft.thePlayer.posZ - Minecraft.thePlayer.prevPosZ;
               this.distance = Math.sqrt(xDist * xDist + zDist * zDist);
            }

            if(this.mode.getValue() == Hopper.SigmaSpeedMode.Hypixel3) {
               this.var10000 = mc;
               this.var10001 = mc;
               this.xDist = Minecraft.thePlayer.posX - Minecraft.thePlayer.prevPosX;
               this.var10000 = mc;
               this.var10001 = mc;
               this.zDist = Minecraft.thePlayer.posZ - Minecraft.thePlayer.prevPosZ;
               this.lastDist = Math.sqrt(this.xDist * this.xDist + this.zDist * this.zDist);
            }

            if(this.mode.getValue() == Hopper.SigmaSpeedMode.HypixelTick) {
               ++this.counter;
               if(!PlayerUtil.MovementInput()) {
                  this.freezePlayer();
               }

               if(this.counter == 2) {
                  Timer.timerSpeed = 1.0F;
               } else if(this.counter >= 10 && this.getDistanceToFall() <= 0.25D) {
                  Timer.timerSpeed = 2.0F;
               }

               Minecraft var9 = mc;
               if(Minecraft.thePlayer.onGround) {
                  this.counter = 0;
                  if(PlayerUtil.MovementInput()) {
                     Timer.timerSpeed = 1.5F;
                  }
               }
            }

            if(this.mode.getValue() == Hopper.SigmaSpeedMode.OnGround && this.canZoom()) {
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
      }
   }

   private boolean isBlockUnder(Material air) {
      return false;
   }

   private int getRandom(int i) {
      return 0;
   }

   public void setSpeed(double speed) {
      Minecraft var10000x = mc;
      Minecraft.thePlayer.motionX = -(Math.sin((double)this.getDirection()) * speed);
      var10000x = mc;
      Minecraft.thePlayer.motionZ = Math.cos((double)this.getDirection()) * speed;
   }

   public float getDirection() {
      Minecraft var10000x = mc;
      float yaw = Minecraft.thePlayer.rotationYaw;
      var10000x = mc;
      if(Minecraft.thePlayer.moveForward < 0.0F) {
         yaw += 180.0F;
      }

      float forward = 1.0F;
      var10000x = mc;
      if(Minecraft.thePlayer.moveForward < 0.0F) {
         forward = -0.5F;
      } else {
         var10000x = mc;
         if(Minecraft.thePlayer.moveForward > 0.0F) {
            forward = 0.5F;
         }
      }

      var10000x = mc;
      if(Minecraft.thePlayer.moveStrafing > 0.0F) {
         yaw -= 90.0F * forward;
      }

      var10000x = mc;
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
         aacCount = 0;
         S08PacketPlayerPosLook pac = (S08PacketPlayerPosLook)ep.getPacket();
         Minecraft var10001x = mc;
         pac.yaw = Minecraft.thePlayer.rotationYaw;
         var10001x = mc;
         pac.pitch = Minecraft.thePlayer.rotationPitch;
         stage = -5;
         Client var10000x = Client.instance;
         Client.getModuleManager();
         if(ModuleManager.getModuleByName("Hopper").isEnabled()) {
            var10000x = Client.instance;
            Client.getModuleManager();
            ModuleManager.getModuleByName("Hopper").setEnabled(false);
         }
      }

   }

   private double getAACSpeed(int stage, int jumps) {
      double value = 0.29D;
      double firstvalue = 0.3019D;
      double thirdvalue = 0.0286D - (double)stage / 1000.0D;
      if(stage == 0) {
         value = 0.497D;
         if(jumps >= 2) {
            value += 0.1069D;
         }

         if(jumps >= 3) {
            value += 0.046D;
         }

         Minecraft var10000x = mc;
         Block block = MoveUtils.getBlockUnderPlayer(Minecraft.thePlayer, 0.01D);
         if(block instanceof BlockIce || block instanceof BlockPackedIce) {
            value = 0.59D;
         }
      } else if(stage == 1) {
         value = 0.3031D;
         if(jumps >= 2) {
            value += 0.0642D;
         }

         if(jumps >= 3) {
            value += thirdvalue;
         }
      } else if(stage == 2) {
         value = 0.302D;
         if(jumps >= 2) {
            value += 0.0629D;
         }

         if(jumps >= 3) {
            value += thirdvalue;
         }
      } else if(stage == 3) {
         value = firstvalue;
         if(jumps >= 2) {
            value = firstvalue + 0.0607D;
         }

         if(jumps >= 3) {
            value += thirdvalue;
         }
      } else if(stage == 4) {
         value = firstvalue;
         if(jumps >= 2) {
            value = firstvalue + 0.0584D;
         }

         if(jumps >= 3) {
            value += thirdvalue;
         }
      } else if(stage == 5) {
         value = firstvalue;
         if(jumps >= 2) {
            value = firstvalue + 0.0561D;
         }

         if(jumps >= 3) {
            value += thirdvalue;
         }
      } else if(stage == 6) {
         value = firstvalue;
         if(jumps >= 2) {
            value = firstvalue + 0.0539D;
         }

         if(jumps >= 3) {
            value += thirdvalue;
         }
      } else if(stage == 7) {
         value = firstvalue;
         if(jumps >= 2) {
            value = firstvalue + 0.0517D;
         }

         if(jumps >= 3) {
            value += thirdvalue;
         }
      } else if(stage == 8) {
         value = firstvalue;
         if(MoveUtils.isOnGround(0.05D)) {
            value = firstvalue - 0.002D;
         }

         if(jumps >= 2) {
            value += 0.0496D;
         }

         if(jumps >= 3) {
            value += thirdvalue;
         }
      } else if(stage == 9) {
         value = firstvalue;
         if(jumps >= 2) {
            value = firstvalue + 0.0475D;
         }

         if(jumps >= 3) {
            value += thirdvalue;
         }
      } else if(stage == 10) {
         value = firstvalue;
         if(jumps >= 2) {
            value = firstvalue + 0.0455D;
         }

         if(jumps >= 3) {
            value += thirdvalue;
         }
      } else if(stage == 11) {
         value = 0.3D;
         if(jumps >= 2) {
            value += 0.045D;
         }

         if(jumps >= 3) {
            value += 0.018D;
         }
      } else if(stage == 12) {
         value = 0.301D;
         if(jumps <= 2) {
            aacCount = 0;
         }

         if(jumps >= 2) {
            value += 0.042D;
         }

         if(jumps >= 3) {
            value += thirdvalue + 0.001D;
         }
      } else if(stage == 13) {
         value = 0.298D;
         if(jumps >= 2) {
            value += 0.042D;
         }

         if(jumps >= 3) {
            value += thirdvalue + 0.001D;
         }
      } else if(stage == 14) {
         value = 0.297D;
         if(jumps >= 2) {
            value += 0.042D;
         }

         if(jumps >= 3) {
            value += thirdvalue + 0.001D;
         }
      }

      Minecraft var10 = mc;
      if(Minecraft.thePlayer.moveForward <= 0.0F) {
         value -= 0.06D;
      }

      var10 = mc;
      if(Minecraft.thePlayer.isCollidedHorizontally) {
         value -= 0.1D;
         aacCount = 0;
      }

      return value;
   }

   @EventHandler
   private void onMove(EventMove e) {
      Client var10000x = Client.instance;
      Client.getModuleManager();
      Scaffold i3 = (Scaffold)ModuleManager.getModuleByName("Scaffold");
      if(!i3.isEnabled() || !((Boolean)Scaffold.Lag.getValue()).booleanValue()) {
         var10000x = Client.instance;
         Client.getModuleManager();
         Criticals i4 = (Criticals)ModuleManager.getModuleByName("Criticals");
         if(!i4.isEnabled() || !((Boolean)Criticals.StopSpeed.getValue()).booleanValue() || Aura.curTarget == null) {
            if(this.mode.getValue() == Hopper.SigmaSpeedMode.Hypixel && !this.isInLiquid()) {
               if(this.canZoom()) {
                  this.moveSpeed = this.getBaseMoveSpeed();
               }

               if(stage == 1 && Minecraft.thePlayer.isCollidedVertically && this.canZoom()) {
                  this.moveSpeed = 1.5D + this.getBaseMoveSpeed() - 0.01D;
               }

               if(this.canZoom() && stage == 2) {
                  double jump = 0.418D;
                  Minecraft.thePlayer.motionY = jump;
                  EventMove.setY(Minecraft.thePlayer.motionY);
                  this.moveSpeed *= 1.89D;
               } else if(stage == 3) {
                  double diff = 0.66D * (this.distance - this.getBaseMoveSpeed());
                  this.moveSpeed = this.distance - diff;
               } else {
                  List<AxisAlignedBB> collidingList = Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, Minecraft.thePlayer.boundingBox.offset(0.0D, Minecraft.thePlayer.motionY, 0.0D));
                  if((collidingList.size() > 0 || Minecraft.thePlayer.isCollidedVertically) && stage > 0) {
                     stage = Minecraft.thePlayer.moving()?1:0;
                  }

                  this.moveSpeed = this.getBaseMoveSpeed() * 1.00000011920929D;
                  this.moveSpeed = this.distance - this.distance / 159.0D;
               }

               this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
               Minecraft.thePlayer.setMoveSpeed(e, this.moveSpeed);
               if(stage > 0) {
                  this.setMotion(e, this.moveSpeed);
               }

               float forward = MovementInput.moveForward;
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
                     float var49 = yaw + (forward > 0.0F?-45.0F:45.0F);
                     strafe = 0.0F;
                  } else if(strafe <= -1.0F) {
                     float var50 = yaw + (forward > 0.0F?45.0F:-45.0F);
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

            if(this.mode.getValue() == Hopper.SigmaSpeedMode.OnGround && this.canZoom()) {
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
               Minecraft var105 = mc;
               Minecraft.thePlayer.setMoveSpeed(e, this.movementSpeed);
               ++stage;
            } else if(this.mode.getValue() == Hopper.SigmaSpeedMode.Bhop) {
               Minecraft var51 = mc;
               if(Minecraft.thePlayer.moveForward == 0.0F) {
                  var51 = mc;
                  if(Minecraft.thePlayer.moveStrafing == 0.0F) {
                     this.speed = MoveUtils.defaultSpeed();
                  }
               }

               if(stage == 1) {
                  var51 = mc;
                  if(Minecraft.thePlayer.isCollidedVertically) {
                     label994: {
                        var51 = mc;
                        if(Minecraft.thePlayer.moveForward == 0.0F) {
                           var51 = mc;
                           if(Minecraft.thePlayer.moveStrafing == 0.0F) {
                              break label994;
                           }
                        }

                        this.speed = 1.35D + MoveUtils.defaultSpeed() - 0.01D;
                     }
                  }
               }

               label1039: {
                  label12039: {
                     if(!this.isInLiquid() && stage == 2) {
                        var51 = mc;
                        if(Minecraft.thePlayer.isCollidedVertically && MoveUtils.isOnGround(0.01D)) {
                           var51 = mc;
                           if(Minecraft.thePlayer.moveForward != 0.0F) {
                              break label1039;
                           }

                           var51 = mc;
                           if(Minecraft.thePlayer.moveStrafing != 0.0F) {
                              break label1039;
                           }
                        }
                     }

                     if(stage == 3) {
                        double difference = 0.66D * (this.lastDist - MoveUtils.defaultSpeed());
                        this.speed = this.lastDist - difference;
                        break label1039;
                     }

                     label1242: {
                        var51 = mc;
                        Minecraft var10001x = mc;
                        Minecraft var100023 = mc;
                        Minecraft var100041 = mc;
                        List collidingList = Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, Minecraft.thePlayer.boundingBox.offset(0.0D, Minecraft.thePlayer.motionY, 0.0D));
                        if(collidingList.size() <= 0) {
                           var51 = mc;
                           if(!Minecraft.thePlayer.isCollidedVertically) {
                              break label1242;
                           }
                        }

                        if(stage > 0) {
                           int var63;
						label1315: {
                              var51 = mc;
                              if(Minecraft.thePlayer.moveForward == 0.0F) {
                                 var51 = mc;
                                 if(Minecraft.thePlayer.moveStrafing == 0.0F) {
                                    var63 = 0;
                                    break label1315;
                                 }
                              }

                              var63 = 1;
                           }

                           stage = var63;
                        }
                     }

                     this.speed = this.lastDist - this.lastDist / 159.0D;
                     break label1039;
                  }
               }

               this.speed = Math.max(this.speed, MoveUtils.defaultSpeed());
               if(stage > 0) {
                  if(BlockUtils.isInLiquid()) {
                     this.speed = 0.1D;
                  }

                  this.setMotion(e, this.speed);
               }

               var51 = mc;
               if(Minecraft.thePlayer.moveForward == 0.0F) {
                  var51 = mc;
                  if(Minecraft.thePlayer.moveStrafing == 0.0F) {
                     return;
                  }
               }

               ++stage;
            } else if(this.mode.getValue() == Hopper.SigmaSpeedMode.AAC) {
               Minecraft var70 = mc;
               if((double)Minecraft.thePlayer.fallDistance > 1.2D) {
                  this.lastFall.reset();
               }

               if(!BlockUtils.isInLiquid()) {
                  var70 = mc;
                  if(Minecraft.thePlayer.isCollidedVertically && MoveUtils.isOnGround(0.01D)) {
                     label1519: {
                        var70 = mc;
                        if(Minecraft.thePlayer.moveForward == 0.0F) {
                           var70 = mc;
                           if(Minecraft.thePlayer.moveStrafing == 0.0F) {
                              break label1519;
                           }
                        }

                        stage = 0;
                        var70 = mc;
                        Minecraft.thePlayer.jump();
                        var70 = mc;
                        EventMove.setY(Minecraft.thePlayer.motionY = 0.41999998688698D + (double)MoveUtils.getJumpEffect());
                        if(aacCount < 4) {
                           ++aacCount;
                        }
                     }
                  }
               }

               label1600: {
                  this.speed = this.getAACSpeed(stage, aacCount);
                  var70 = mc;
                  if(Minecraft.thePlayer.moveForward == 0.0F) {
                     var70 = mc;
                     if(Minecraft.thePlayer.moveStrafing == 0.0F) {
                        break label1600;
                     }
                  }

                  if(BlockUtils.isInLiquid()) {
                     this.speed = 0.075D;
                  }

                  this.setMotion(e, this.speed);
               }

               var70 = mc;
               if(Minecraft.thePlayer.moveForward == 0.0F) {
                  var70 = mc;
                  if(Minecraft.thePlayer.moveStrafing == 0.0F) {
                     return;
                  }
               }

               ++stage;
            } else if(this.mode.getValue() == Hopper.SigmaSpeedMode.Hypixel2) {
               Minecraft var80 = mc;
               if(Minecraft.thePlayer.isCollidedHorizontally) {
                  this.collided = true;
               }

               if(this.collided) {
                  Timer var81 = mc.timer;
                  Timer.timerSpeed = 1.0F;
                  stage = -1;
               }

               if(this.stair > 0.0D) {
                  this.stair -= 0.25D;
               }

               this.less -= this.less > 1.0D?0.12D:0.11D;
               if(this.less < 0.0D) {
                  this.less = 0.0D;
               }

               if(!BlockUtils.isInLiquid() && MoveUtils.isOnGround(0.01D) && isMoving2()) {
                  Minecraft var106 = mc;
                  this.collided = Minecraft.thePlayer.isCollidedHorizontally;
                  if(stage >= 0 || this.collided) {
                     stage = 0;
                     double motY = 0.407D + (double)MoveUtils.getJumpEffect() * 0.1D;
                     if(this.stair == 0.0D) {
                        var80 = mc;
                        Minecraft.thePlayer.jump();
                        var80 = mc;
                        EventMove.setY(Minecraft.thePlayer.motionY = motY);
                     }

                     ++this.less;
                     if(this.less > 1.0D && !this.lessSlow) {
                        this.lessSlow = true;
                     } else {
                        this.lessSlow = false;
                     }

                     if(this.less > 1.12D) {
                        this.less = 1.12D;
                     }
                  }
               }

               this.speed = this.getHypixelSpeed(stage) + 0.0331D;
               this.speed *= 0.91D;
               if(this.stair > 0.0D) {
                  this.speed *= 0.7D - (double)MoveUtils.getSpeedEffect() * 0.1D;
               }

               if(stage < 0) {
                  this.speed = MoveUtils.defaultSpeed();
               }

               if(this.lessSlow) {
                  this.speed *= 0.95D;
               }

               if(BlockUtils.isInLiquid()) {
                  this.speed = 0.12D;
               }

               var80 = mc;
               if(Minecraft.thePlayer.moveForward == 0.0F) {
                  var80 = mc;
                  if(Minecraft.thePlayer.moveStrafing == 0.0F) {
                     return;
                  }
               }

               this.setMotion(e, this.speed);
               ++stage;
            } else if(this.mode.getValue() == Hopper.SigmaSpeedMode.HypixelTick) {
               Minecraft var86 = mc;
               MovementInput var87 = Minecraft.thePlayer.movementInput;
               double forward = (double)MovementInput.moveForward;
               Minecraft var88 = mc;
               MovementInput var89 = Minecraft.thePlayer.movementInput;
               double strafe = (double)MovementInput.moveStrafe;
               Minecraft var90 = mc;
               double yaw = (double)Minecraft.thePlayer.rotationYaw;
               switch(stage) {
               case 0:
                  ++stage;
                  this.lastDist = 0.0D;
                  break;
               case 1:
               default:
                  label2389: {
                     var90 = mc;
                     WorldClient var97 = Minecraft.theWorld;
                     Minecraft var108 = mc;
                     EntityPlayerSP var109 = Minecraft.thePlayer;
                     Minecraft var110 = mc;
                     AxisAlignedBB var111 = Minecraft.thePlayer.getEntityBoundingBox();
                     Minecraft var112 = mc;
                     if(var97.getCollidingBoundingBoxes(var109, var111.offset(0.0D, Minecraft.thePlayer.motionY, 0.0D)).size() <= 0) {
                        Minecraft var98 = mc;
                        if(!Minecraft.thePlayer.isCollidedVertically) {
                           break label2389;
                        }
                     }

                     if(stage > 0) {
                        int var101;
						label2458: {
                           Minecraft var99 = mc;
                           if(Minecraft.thePlayer.moveForward == 0.0F) {
                              var99 = mc;
                              if(Minecraft.thePlayer.moveStrafing == 0.0F) {
                                 var101 = 0;
                                 break label2458;
                              }
                           }

                           var101 = 1;
                        }

                        stage = var101;
                     }
                  }

                  this.moveSpeed = this.lastDist - this.lastDist / 159.0D;
                  break;
               case 2:
                  double motionY = 0.40123128D;
                  var90 = mc;
                  if(Minecraft.thePlayer.moveForward == 0.0F) {
                     var90 = mc;
                     if(Minecraft.thePlayer.moveStrafing == 0.0F) {
                        break;
                     }
                  }

                  var90 = mc;
                  if(Minecraft.thePlayer.onGround) {
                     var90 = mc;
                     if(Minecraft.thePlayer.isPotionActive(Potion.jump)) {
                        Minecraft var107 = mc;
                        motionY += (double)((float)(Minecraft.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
                     }

                     var90 = mc;
                     EventMove.setY(Minecraft.thePlayer.motionY = motionY);
                     this.moveSpeed *= 2.149D;
                  }
                  break;
               case 3:
                  this.moveSpeed = this.lastDist - 0.7095D * (this.lastDist - this.getBaseMoveSpeed2());
               }

               this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed2());
               if(forward == 0.0D && strafe == 0.0D) {
                  EventMove.setX(0.0D);
                  EventMove.setZ(0.0D);
               }

               if(forward != 0.0D && strafe != 0.0D) {
                  forward *= Math.sin(0.7853981633974483D);
                  strafe *= Math.cos(0.7853981633974483D);
               }

               EventMove.setX((forward * this.moveSpeed * -Math.sin(Math.toRadians(yaw)) + strafe * this.moveSpeed * Math.cos(Math.toRadians(yaw))) * 0.99D);
               EventMove.setZ((forward * this.moveSpeed * Math.cos(Math.toRadians(yaw)) - strafe * this.moveSpeed * -Math.sin(Math.toRadians(yaw))) * 0.99D);
               ++stage;
            } else if(this.mode.getValue() == Hopper.SigmaSpeedMode.Hypixel3) {
               if(stage < 1) {
                  ++stage;
                  this.lastDist = 0.0D;
                  return;
               }

               if(this.steps > 2) {
                  this.steps = 0;
               }

               label2726: {
                  if(stage == 2) {
                     label2733: {
                        this.var10000 = mc;
                        if(Minecraft.thePlayer.moveForward == 0.0F) {
                           this.var10000 = mc;
                           if(Minecraft.thePlayer.moveStrafing == 0.0F) {
                              break label2733;
                           }
                        }

                        this.var10000 = mc;
                        if(Minecraft.thePlayer.isCollidedVertically) {
                           this.var10000 = mc;
                           if(Minecraft.thePlayer.onGround) {
                              this.zDist = 0.41999998688697815D;
                              this.var10000 = mc;
                              if(Minecraft.thePlayer.isPotionActive(Potion.jump)) {
                                 this.var10001 = mc;
                                 this.zDist += (double)((float)(Minecraft.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier() + 1) * 0.1F);
                              }

                              this.var10000 = mc;
                              Minecraft.thePlayer.motionY = this.zDist;
                              this.var10001 = mc;
                              EventMove.setY(Minecraft.thePlayer.motionY);
                              this.speed *= 1.9499999D;
                              break label2726;
                           }
                        }
                     }
                  }

                  if(stage == 3) {
                     this.zDist = (stage % 3 == 0?0.68D:0.72D) * (this.lastDist - this.defaultSpeed());
                     this.speed = this.lastDist - this.zDist;
                  } else {
                     label2968: {
                        this.var10001 = mc;
                        Minecraft var10002 = mc;
                        Minecraft var10004 = mc;
                        Minecraft var102 = mc;
                        List var20 = Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, Minecraft.thePlayer.boundingBox.offset(0.0D, Minecraft.thePlayer.motionY, 0.0D));
                        if(var20.size() <= 0) {
                           this.var10000 = mc;
                           if(!Minecraft.thePlayer.isCollidedVertically) {
                              break label2968;
                           }
                        }

                        if(stage > 0) {
                           byte var23;
                           label3052: {
                              this.var10000 = mc;
                              if(Minecraft.thePlayer.moveForward == 0.0F) {
                                 this.var10000 = mc;
                                 if(Minecraft.thePlayer.moveStrafing == 0.0F) {
                                    var23 = 0;
                                    break label3052;
                                 }
                              }

                              var23 = 1;
                           }

                           stage = var23;
                        }
                     }

                     this.speed = this.lastDist - this.lastDist / 159.0D;
                  }
               }

               this.speed = Math.max(this.speed, this.defaultSpeed());
               this.var10000 = mc;
               MovementInput var21 = Minecraft.thePlayer.movementInput;
               this.zDist = (double)MovementInput.moveForward;
               this.var10000 = mc;
               var21 = Minecraft.thePlayer.movementInput;
               double strafe = (double)MovementInput.moveStrafe;
               this.var10000 = mc;
               float yaw = Minecraft.thePlayer.rotationYaw;
               if(this.zDist == 0.0D && strafe == 0.0D) {
                  this.var10000 = mc;
                  this.var10001 = mc;
                  double var22 = Minecraft.thePlayer.posX + 1.0D;
                  Minecraft var10002 = mc;
                  Minecraft var10003 = mc;
                  Minecraft.thePlayer.setPosition(var22, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ + 1.0D);
                  this.var10000 = mc;
                  this.var10001 = mc;
                  var10002 = mc;
                  var10003 = mc;
                  Minecraft.thePlayer.setPosition(Minecraft.thePlayer.prevPosX, Minecraft.thePlayer.posY, Minecraft.thePlayer.prevPosZ);
                  EventMove.setX(0.0D);
                  EventMove.setZ(0.0D);
               } else if(this.zDist != 0.0D) {
                  if(strafe >= 1.0D) {
                     yaw += this.zDist > 0.0D?-45.0F:45.0F;
                     strafe = 0.0D;
                  } else if(strafe <= -1.0D) {
                     yaw += this.zDist > 0.0D?45.0F:-45.0F;
                     strafe = 0.0D;
                  }

                  if(this.zDist > 0.0D) {
                     this.zDist = 1.0D;
                  } else if(this.zDist < 0.0D) {
                     this.zDist = -1.0D;
                  }
               }

               double mx = Math.cos(Math.toRadians((double)(yaw + 90.0F)));
               double mz = Math.sin(Math.toRadians((double)(yaw + 90.0F)));
               EventMove.setX((this.zDist * this.speed * mx + strafe * this.speed * mz) * 0.987D);
               EventMove.setZ((this.zDist * this.speed * mz - strafe * this.speed * mx) * 0.987D);
               this.var10000 = mc;
               Minecraft.thePlayer.stepHeight = 0.6F;
               if(this.zDist == 0.0D && strafe == 0.0D) {
                  EventMove.setX(0.0D);
                  EventMove.setZ(0.0D);
                  this.var10000 = mc;
                  this.var10001 = mc;
                  double var22 = Minecraft.thePlayer.posX + 1.0D;
                  Minecraft var10002 = mc;
                  Minecraft var10003 = mc;
                  Minecraft.thePlayer.setPosition(var22, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ + 1.0D);
                  this.var10000 = mc;
                  this.var10001 = mc;
                  var10002 = mc;
                  var10003 = mc;
                  Minecraft.thePlayer.setPosition(Minecraft.thePlayer.prevPosX, Minecraft.thePlayer.posY, Minecraft.thePlayer.prevPosZ);
               } else if(this.zDist != 0.0D) {
                  if(strafe >= 1.0D) {
                     float var103 = yaw + (this.zDist > 0.0D?-45.0F:45.0F);
                     strafe = 0.0D;
                  } else if(strafe <= -1.0D) {
                     float var104 = yaw + (this.zDist > 0.0D?45.0F:-45.0F);
                     strafe = 0.0D;
                  }

                  if(this.zDist > 0.0D) {
                     this.zDist = 1.0D;
                  } else if(this.zDist < 0.0D) {
                     this.zDist = -1.0D;
                  }
               }

               ++stage;
            }

         }
      }
   }

   public static boolean isMoving2() {
      Minecraft.getMinecraft();
      if(Minecraft.thePlayer.moveForward == 0.0F) {
         Minecraft.getMinecraft();
         if(Minecraft.thePlayer.moveStrafing == 0.0F) {
            return false;
         }
      }

      return true;
   }

   private boolean MovementInput() {
      Minecraft var10000x = mc;
      if(!Minecraft.gameSettings.keyBindForward.pressed) {
         var10000x = mc;
         if(!Minecraft.gameSettings.keyBindLeft.pressed) {
            var10000x = mc;
            if(!Minecraft.gameSettings.keyBindRight.pressed) {
               var10000x = mc;
               if(!Minecraft.gameSettings.keyBindBack.pressed) {
                  return false;
               }
            }
         }
      }

      return true;
   }

   public void setMoveSpeed(EventMove event, double speed) {
      Minecraft var10000x = mc;
      MovementInput movementInput = Minecraft.thePlayer.movementInput;
      double forward = (double)MovementInput.moveForward;
      double strafe = (double)MovementInput.moveStrafe;
      var10000x = mc;
      float yaw = Minecraft.thePlayer.rotationYaw;
      if(forward == 0.0D && strafe == 0.0D) {
         EventMove.x = 0.0D;
         EventMove.x = 0.0D;
      } else {
         if(forward != 0.0D) {
            var10000x = mc;
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
      Minecraft var10000x = mc;
      if(Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
         var10000x = mc;
         int amplifier = Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
         baseSpeed *= 1.0D + 0.2D * (double)(amplifier + 1);
      }

      return baseSpeed;
   }

   public static double round(double value, int places) {
      if(places < 0) {
         throw new IllegalArgumentException();
      } else {
         BigDecimal bd = new BigDecimal(value);
         bd = bd.setScale(places, RoundingMode.HALF_UP);
         return bd.doubleValue();
      }
   }

   private void setMotion(EventMove em, double speed) {
      Minecraft var10000x = mc;
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
      Minecraft var10000x = mc;
      if(Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
         var10000x = mc;
         int amplifier = Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
         baseSpeed *= 1.0D + 0.2D * (double)(amplifier + 1);
      }

      return baseSpeed;
   }

   private double getBaseMoveSpeed2() {
      double baseSpeed = 0.272D;
      Minecraft var10000x = mc;
      if(Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
         var10000x = mc;
         int amplifier = Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
         baseSpeed *= 1.0D + 0.2D * (double)amplifier;
      }

      return baseSpeed;
   }

   private void setMoveSpeed2(EventMove event, double speed) {
      Minecraft var10000x = mc;
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

         EventMove.setX(forward * speed * -Math.sin(Math.toRadians((double)yaw)) + strafe * speed * Math.cos(Math.toRadians((double)yaw)));
         EventMove.setZ(forward * speed * Math.cos(Math.toRadians((double)yaw)) - strafe * speed * -Math.sin(Math.toRadians((double)yaw)));
      }

   }

   private double getHypixelSpeed(int stage) {
      double value = MoveUtils.defaultSpeed() + 0.028D * (double)MoveUtils.getSpeedEffect() + (double)MoveUtils.getSpeedEffect() / 15.0D;
      double firstvalue = 0.4145D + (double)MoveUtils.getSpeedEffect() / 12.5D;
      double decr = (double)stage / 500.0D * 2.0D;
      if(stage == 0) {
         if(this.timer.delay(300.0F)) {
            this.timer.reset();
            if(((Boolean)this.HypixelBoost.getValue()).booleanValue()) {
               Timer var10000x = mc.timer;
               Timer.timerSpeed = 1.354F;
            }
         }

         if(!this.lastCheck.delay(500.0F)) {
            if(!this.shouldslow) {
               this.shouldslow = true;
            }
         } else if(this.shouldslow) {
            this.shouldslow = false;
         }

         value = 0.64D + ((double)MoveUtils.getSpeedEffect() + 0.028D * (double)MoveUtils.getSpeedEffect()) * 0.134D;
      } else if(stage == 1) {
         Timer var8 = mc.timer;
         if(Timer.timerSpeed == 1.354F && ((Boolean)this.HypixelBoost.getValue()).booleanValue()) {
            var8 = mc.timer;
            Timer.timerSpeed = 1.254F;
         }

         value = firstvalue;
      } else if(stage >= 2) {
         Timer var10 = mc.timer;
         if(Timer.timerSpeed == 1.254F && ((Boolean)this.HypixelBoost.getValue()).booleanValue()) {
            var10 = mc.timer;
            Timer.timerSpeed = 1.0F;
         }

         value = firstvalue - decr;
      }

      if(this.shouldslow || !this.lastCheck.delay(500.0F) || this.collided) {
         value = 0.2D;
         if(stage == 0) {
            value = 0.0D;
         }
      }

      return Math.max(value, this.shouldslow?value:MoveUtils.defaultSpeed() + 0.028D * (double)MoveUtils.getSpeedEffect());
   }

   static enum SigmaSpeedMode {
      Hypixel,
      OnGround,
      Bhop,
      AAC,
      Hypixel2,
      Hypixel3,
      HypixelTick;
   }
}
