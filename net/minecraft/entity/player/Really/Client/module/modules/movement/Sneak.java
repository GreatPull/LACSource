package net.minecraft.entity.player.Really.Client.module.modules.movement;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class Sneak extends Module {
   public Sneak() {
      super("Sneak", new String[]{"stealth", "snek"}, ModuleType.Movement);
      this.setColor((new Color(84, 194, 110)).getRGB());
   }

   @EventHandler
   private void onUpdate(EventPreUpdate e) {
      if(EventPreUpdate.getType() == 0) {
         Minecraft var10000 = mc;
         if(Minecraft.thePlayer.isSneaking()) {
            return;
         }

         var10000 = mc;
         if(Minecraft.thePlayer.moving()) {
            return;
         }

         var10000 = mc;
         Minecraft var10003 = mc;
         Minecraft.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(Minecraft.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
      } else if(EventPreUpdate.getType() == 1) {
         Minecraft var4 = mc;
         Minecraft var5 = mc;
         Minecraft.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(Minecraft.thePlayer, C0BPacketEntityAction.Action.START_SNEAKING));
      }

   }

   public void onDisable() {
      Minecraft var10000 = mc;
      Minecraft var10003 = mc;
      Minecraft.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(Minecraft.thePlayer, C0BPacketEntityAction.Action.STOP_SNEAKING));
   }
}
