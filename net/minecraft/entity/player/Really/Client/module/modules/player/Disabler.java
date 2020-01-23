package net.minecraft.entity.player.Really.Client.module.modules.player;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPacketRecieve;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPacketSend;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.MovementInput;

public class Disabler extends Module {
   private Mode mode = new Mode("Mode", "mode", Disabler.DisMode.values(), Disabler.DisMode.Kill);
   private boolean laggedback;
   private boolean felldown;
   private float[] lastrot = new float[2];
   int lastKey;

   public Disabler() {
      super("Disabler", new String[]{"autoyool"}, ModuleType.Player);
      this.addValues(new Value[]{this.mode});
   }

   public Class type() {
      return EventPacketSend.class;
   }

   public void onEnable() {
      Minecraft var10000 = mc;
      if(Minecraft.theWorld != null) {
         if(this.mode.getValue() == Disabler.DisMode.HypixelOPFly) {
            var10000 = mc;
            if(Minecraft.thePlayer != null) {
               this.laggedback = false;
               this.felldown = false;
               Minecraft var10002 = mc;
               this.lastrot[0] = Minecraft.thePlayer.rotationYaw;
               var10002 = mc;
               this.lastrot[1] = Minecraft.thePlayer.rotationPitch;
               var10000 = mc;
               if(Minecraft.thePlayer.onGround) {
                  var10000 = mc;
                  Minecraft.thePlayer.jump();
               }
            }
         } else if(this.mode.getValue() == Disabler.DisMode.Lobby) {
            var10000 = mc;
            Minecraft.thePlayer.sendChatMessage("/lobby");
         } else if(this.mode.getValue() == Disabler.DisMode.Kill) {
            var10000 = mc;
            Minecraft.thePlayer.sendChatMessage("/kill");
            this.setEnabled(false);
         }

      }
   }

   @EventHandler
   public void onPacket(EventPacketRecieve event) {
      if(this.felldown) {
         Minecraft var10000 = mc;
         if(!Minecraft.isSingleplayer()) {
            var10000 = mc;
            if(Minecraft.theWorld != null && event.getPacket() instanceof S08PacketPlayerPosLook) {
               S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook)event.getPacket();
               var10000 = mc;
               if(Minecraft.thePlayer.posY > packet.getY()) {
                  this.laggedback = true;
                  this.setEnabled(false);
               }
            }
         }
      }

   }

   private void setMoveSpeed(double speed) {
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

         var11 = mc;
         Minecraft.thePlayer.motionX = forward * speed * -Math.sin(Math.toRadians((double)yaw)) + strafe * speed * Math.cos(Math.toRadians((double)yaw));
         var11 = mc;
         Minecraft.thePlayer.motionZ = forward * speed * Math.cos(Math.toRadians((double)yaw)) - strafe * speed * -Math.sin(Math.toRadians((double)yaw));
      }

   }

   @EventHandler
   public void onEvent(EventPreUpdate event) {
      this.setSuffix(this.mode.getValue());
      if(this.mode.getValue() == Disabler.DisMode.HypixelOPFly) {
         if(this.felldown) {
            Minecraft var10000 = mc;
            if(Minecraft.thePlayer.movementInput.jump) {
               var10000 = mc;
               Minecraft.thePlayer.motionY = 3.0D;
            } else {
               var10000 = mc;
               if(Minecraft.thePlayer.movementInput.sneak) {
                  var10000 = mc;
                  Minecraft.thePlayer.motionY = -3.0D;
               } else {
                  var10000 = mc;
                  Minecraft.thePlayer.motionY = 0.0D;
               }
            }

            if(this.laggedback) {
               this.setMoveSpeed(3.0D);
            } else {
               var10000 = mc;
               MovementInput var7 = Minecraft.thePlayer.movementInput;
               MovementInput.moveForward = 0.0F;
               Minecraft var8 = mc;
               MovementInput var9 = Minecraft.thePlayer.movementInput;
               MovementInput.moveStrafe = 0.0F;
               event.setYaw(this.lastrot[0]);
               event.setPitch(this.lastrot[1]);
               Minecraft var10 = mc;
               Minecraft.thePlayer.motionX = 0.0D;
               var10 = mc;
               Minecraft.thePlayer.motionZ = 0.0D;
               var10 = mc;
               if(Minecraft.thePlayer.ticksExisted % 2 == 0) {
                  var10 = mc;
                  Minecraft var10001 = mc;
                  Minecraft var10002 = mc;
                  double var19 = Minecraft.thePlayer.posY + 0.01D;
                  Minecraft var10003 = mc;
                  Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, var19, Minecraft.thePlayer.posZ);
               } else {
                  var10 = mc;
                  Minecraft var18 = mc;
                  Minecraft var20 = mc;
                  double var21 = Minecraft.thePlayer.posY - 0.01D;
                  Minecraft var22 = mc;
                  Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, var21, Minecraft.thePlayer.posZ);
               }
            }
         } else {
            Minecraft var15 = mc;
            if((double)Minecraft.thePlayer.fallDistance > 0.0D) {
               this.felldown = true;
               var15 = mc;
               Minecraft.thePlayer.motionX = 0.0D;
               var15 = mc;
               Minecraft.thePlayer.motionZ = 0.0D;
            }
         }
      }

      if(mc.currentScreen == null && this.mode.getValue() == Disabler.DisMode.Lobby) {
         (new Thread(() -> {
            try {
               Thread.sleep(350L);
            } catch (InterruptedException var1) {
               var1.printStackTrace();
            }

            Minecraft var10000 = mc;
            Minecraft.thePlayer.sendChatMessage("/back");
         })).start();
         this.setEnabled(false);
      }

   }

   public void onDisable() {
      if(this.mode.getValue() == Disabler.DisMode.HypixelOPFly) {
         Minecraft var10000 = mc;
         if(Minecraft.thePlayer != null) {
            var10000 = mc;
            Minecraft.thePlayer.motionX = 0.0D;
            var10000 = mc;
            Minecraft.thePlayer.motionZ = 0.0D;
         }
      }

      super.onDisable();
   }

   static enum DisMode {
      HypixelOPFly,
      Lobby,
      Kill;
   }
}
