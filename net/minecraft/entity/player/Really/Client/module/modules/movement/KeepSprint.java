package net.minecraft.entity.player.Really.Client.module.modules.movement;

import java.awt.Color;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPacketRecieve;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class KeepSprint extends Module {
   public KeepSprint() {
      super("KeepSprint", new String[]{"rotate"}, ModuleType.Movement);
      this.setColor((new Color(17, 250, 154)).getRGB());
   }

   @EventHandler
   private void onPacket(EventPacketRecieve e) {
      try {
         if(e.getPacket() instanceof C0BPacketEntityAction) {
            C0BPacketEntityAction packet = (C0BPacketEntityAction)e.getPacket();
            if(packet.getAction() == C0BPacketEntityAction.Action.STOP_SPRINTING) {
               e.setCancelled(true);
            }
         }
      } catch (ClassCastException var3) {
         ;
      }

   }
}
