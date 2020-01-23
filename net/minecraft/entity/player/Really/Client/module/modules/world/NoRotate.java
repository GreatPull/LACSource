package net.minecraft.entity.player.Really.Client.module.modules.world;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPacketSend;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class NoRotate extends Module {
   public NoRotate() {
      super("NoRotate", new String[]{"rotate"}, ModuleType.World);
      this.setColor((new Color(17, 250, 154)).getRGB());
   }

   @EventHandler
   private void onPacket(EventPacketSend e) {
      if(EventPacketSend.getPacket() instanceof S08PacketPlayerPosLook) {
         S08PacketPlayerPosLook look = (S08PacketPlayerPosLook)EventPacketSend.getPacket();
         Minecraft var10001 = mc;
         look.yaw = Minecraft.thePlayer.rotationYaw;
         var10001 = mc;
         look.pitch = Minecraft.thePlayer.rotationPitch;
      }

   }
}
