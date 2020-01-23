package net.minecraft.entity.player.Really.Client.module.modules.combat;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPacketRecieve;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class FastBow extends Module {
   private TimerUtil timer = new TimerUtil();
   private Option faithful = new Option("Faithful", "faithful", Boolean.valueOf(true));
   int counter;

   public FastBow() {
      super("FastBow", new String[]{"zoombow", "quickbow"}, ModuleType.Combat);
      this.setColor((new Color(255, 99, 99)).getRGB());
      this.addValues(new Value[]{this.faithful});
      this.counter = 0;
   }

   private boolean canConsume() {
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer.inventory.getCurrentItem() != null) {
         var10000 = mc;
         if(Minecraft.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow) {
            return true;
         }
      }

      return false;
   }

   private void killGuardian() {
      if(this.timer.hasReached(1000.0D)) {
         Minecraft var10000 = mc;
         Minecraft var10003 = mc;
         Minecraft var10004 = mc;
         double var1 = Minecraft.thePlayer.posY - Double.POSITIVE_INFINITY;
         Minecraft var10005 = mc;
         Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, var1, Minecraft.thePlayer.posZ, false));
         this.timer.reset();
      }

   }

   @EventHandler
   private void onUpdate(EventPreUpdate e) {
      if(((Boolean)this.faithful.getValue()).booleanValue()) {
         Minecraft var10000 = mc;
         if(Minecraft.thePlayer.onGround) {
            var10000 = mc;
            if(Minecraft.thePlayer.getCurrentEquippedItem() != null) {
               var10000 = mc;
               if(Minecraft.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow) {
                  var10000 = mc;
                  if(Minecraft.gameSettings.keyBindUseItem.pressed) {
                     var10000 = mc;
                     Minecraft var10003 = mc;
                     Minecraft.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(Minecraft.thePlayer.inventory.getCurrentItem()));

                     for(int index = 0; index < 16; ++index) {
                        var10000 = mc;
                        if(!Minecraft.thePlayer.isDead) {
                           var10000 = mc;
                           var10003 = mc;
                           Minecraft var10004 = mc;
                           double var37 = Minecraft.thePlayer.posY - 0.09D;
                           Minecraft var10005 = mc;
                           Minecraft var10006 = mc;
                           Minecraft var10007 = mc;
                           Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(Minecraft.thePlayer.posX, var37, Minecraft.thePlayer.posZ, Minecraft.thePlayer.rotationYaw, Minecraft.thePlayer.rotationPitch, true));
                        }
                     }

                     var10000 = mc;
                     Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                  }
               }
            }
         }

         var10000 = mc;
         if(Minecraft.thePlayer.onGround) {
            var10000 = mc;
            if(Minecraft.thePlayer.getCurrentEquippedItem() != null) {
               var10000 = mc;
               if(Minecraft.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow) {
                  var10000 = mc;
                  if(Minecraft.gameSettings.keyBindUseItem.pressed) {
                     var10000 = mc;
                     Minecraft var32 = mc;
                     Minecraft.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(Minecraft.thePlayer.inventory.getCurrentItem()));
                     var10000 = mc;
                     if(Minecraft.thePlayer.ticksExisted % 2 == 0) {
                        var10000 = mc;
                        Minecraft var10001 = mc;
                        Minecraft.thePlayer.setItemInUse(Minecraft.thePlayer.getCurrentEquippedItem(), 12);
                        ++this.counter;
                        if(this.counter > 0) {
                           var10000 = mc;
                           var32 = mc;
                           Minecraft var38 = mc;
                           double var39 = Minecraft.thePlayer.posY - 0.0D;
                           Minecraft var44 = mc;
                           Minecraft var47 = mc;
                           Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, var39, Minecraft.thePlayer.posZ, Minecraft.thePlayer.onGround));
                           this.counter = 0;
                        }

                        int dist = 20;

                        for(int index = 0; index < dist; ++index) {
                           var10000 = mc;
                           var32 = mc;
                           Minecraft var40 = mc;
                           double var41 = Minecraft.thePlayer.posY + 1.0E-12D;
                           Minecraft var45 = mc;
                           Minecraft var48 = mc;
                           Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, var41, Minecraft.thePlayer.posZ, Minecraft.thePlayer.onGround));
                        }

                        var10000 = mc;
                        Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                        var10000 = mc;
                        var10001 = mc;
                        Minecraft.playerController.onStoppedUsingItem(Minecraft.thePlayer);
                     }
                  }
               }
            }
         }
      } else {
         Minecraft var24 = mc;
         if(Minecraft.thePlayer.onGround && this.canConsume()) {
            var24 = mc;
            if(Minecraft.gameSettings.keyBindUseItem.pressed) {
               var24 = mc;
               Minecraft var35 = mc;
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(Minecraft.thePlayer.inventory.getCurrentItem()));

               for(int i = 0; i < 20; ++i) {
                  var24 = mc;
                  var35 = mc;
                  Minecraft var42 = mc;
                  double var43 = Minecraft.thePlayer.posY + 1.0E-9D;
                  Minecraft var46 = mc;
                  Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, var43, Minecraft.thePlayer.posZ, true));
               }

               var24 = mc;
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
               return;
            }
         }

         net.minecraft.util.Timer var26 = mc.timer;
         net.minecraft.util.Timer.timerSpeed = 1.0F;
      }

   }

   @EventHandler
   public void onRecieve(EventPacketRecieve event) {
      if(event.getPacket() instanceof S18PacketEntityTeleport) {
         S18PacketEntityTeleport packet = (S18PacketEntityTeleport)event.getPacket();
         Minecraft var10000 = mc;
         if(Minecraft.thePlayer != null) {
            Minecraft var10001 = mc;
            packet.yaw = (byte)((int)Minecraft.thePlayer.rotationYaw);
         }

         Minecraft var3 = mc;
         packet.pitch = (byte)((int)Minecraft.thePlayer.rotationPitch);
      }

   }
}
