package net.minecraft.entity.player.Really.Client.module.modules.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventAttack;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.Wrapper;
import net.minecraft.network.play.client.C03PacketPlayer;

public class MoreKnockBack extends Module {
   private Numbers packets = new Numbers("Packets", "packets", Double.valueOf(100.0D), Double.valueOf(50.0D), Double.valueOf(2000.0D), Double.valueOf(50.0D));
   private EntityPlayer entity;

   public MoreKnockBack() {
      super("MoreKnockBack", new String[]{"superknockback"}, ModuleType.Combat);
      this.addValues(new Value[]{this.packets});
   }

   @EventHandler
   public void onAttack(EventAttack e) {
      if(Minecraft.thePlayer.getDistanceToEntity(e.getEntity()) <= 1.0F || Minecraft.thePlayer.getEntityBoundingBox().intersectsWith(e.getEntity().getEntityBoundingBox())) {
         for(int i = 0; (double)i < ((Double)this.packets.getValue()).doubleValue(); ++i) {
            if(Minecraft.thePlayer.onGround) {
               Wrapper.sendPacketInQueue(new C03PacketPlayer());
            }
         }
      }

   }
}
