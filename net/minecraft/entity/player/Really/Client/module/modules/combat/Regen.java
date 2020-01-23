package net.minecraft.entity.player.Really.Client.module.modules.combat;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Regen extends Module {
   private Option guardian = new Option("Guardian", "guardian", Boolean.valueOf(true));

   public Regen() {
      super("Regen", new String[]{"fastgen"}, ModuleType.Combat);
      this.setColor((new Color(208, 30, 142)).getRGB());
   }

   @EventHandler
   private void onUpdate(EventPreUpdate event) {
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer.onGround) {
         var10000 = mc;
         if((double)Minecraft.thePlayer.getHealth() < 16.0D) {
            var10000 = mc;
            if(Minecraft.thePlayer.getFoodStats().getFoodLevel() > 17) {
               var10000 = mc;
               if(Minecraft.thePlayer.isCollidedVertically) {
                  for(int i = 0; i < 60; ++i) {
                     var10000 = mc;
                     Minecraft var10003 = mc;
                     Minecraft var10004 = mc;
                     double var13 = Minecraft.thePlayer.posY + 1.0E-9D;
                     Minecraft var10005 = mc;
                     Minecraft var10006 = mc;
                     Minecraft var10007 = mc;
                     Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(Minecraft.thePlayer.posX, var13, Minecraft.thePlayer.posZ, Minecraft.thePlayer.rotationYaw, Minecraft.thePlayer.rotationPitch, true));
                     var10000 = mc;
                     Minecraft.thePlayer.motionX = 0.0D;
                     var10000 = mc;
                     Minecraft.thePlayer.motionZ = 0.0D;
                  }

                  if(((Boolean)this.guardian.getValue()).booleanValue()) {
                     var10000 = mc;
                     if(Minecraft.thePlayer.ticksExisted % 3 == 0) {
                        var10000 = mc;
                        Minecraft var12 = mc;
                        Minecraft var14 = mc;
                        double var15 = Minecraft.thePlayer.posY - 999.0D;
                        Minecraft var16 = mc;
                        Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, var15, Minecraft.thePlayer.posZ, true));
                     }
                  }

                  var10000 = mc;
                  Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
               }
            }
         }
      }

   }
}
