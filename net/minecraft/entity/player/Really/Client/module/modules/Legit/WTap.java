package net.minecraft.entity.player.Really.Client.module.modules.Legit;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPacketSend;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.Timer;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class WTap extends Module {
   private Timer timer = new Timer();

   public WTap() {
      super("Wtap", new String[]{"Wtap", "Wtap"}, ModuleType.Legit);
      this.setColor((new Color(244, 255, 149)).getRGB());
   }

   @EventHandler
   private void onTick(EventPacketSend e) {
      C02PacketUseEntity packet;
      if(EventPacketSend.getType() == 2 && EventPacketSend.getPacket() instanceof C02PacketUseEntity && Minecraft.thePlayer != null && (packet = (C02PacketUseEntity)EventPacketSend.getPacket()).getAction() == C02PacketUseEntity.Action.ATTACK && packet.getEntityFromWorld(Minecraft.theWorld) != Minecraft.thePlayer && Minecraft.thePlayer.getFoodStats().getFoodLevel() > 6) {
         boolean sprint = Minecraft.thePlayer.isSprinting();
         Minecraft.thePlayer.setSprinting(false);
         Minecraft.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(Minecraft.thePlayer, C0BPacketEntityAction.Action.STOP_SPRINTING));
         Minecraft.thePlayer.sendQueue.addToSendQueue(new C0BPacketEntityAction(Minecraft.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
         Minecraft.thePlayer.setSprinting(sprint);
      }

   }
}
