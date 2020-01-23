package net.minecraft.entity.player.Really.Client.module.modules.player;

import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C17PacketCustomPayload;

public class BetterGaming extends Module {
   public BetterGaming() {
      super("BetterGaming", new String[]{"HytBypass"}, ModuleType.Player);
   }

   public void onEnable() {
      StringBuilder var10000 = new StringBuilder("{\"base64\":\"ecbeb575677ab9a37410748a5f429f9f\",\"cltitle\":\"我的世界 1.8.9\",\"isLiquidbounce\":false,\"path\":\"mixins.mcwrapper.json\",\"player\":\"");
      Minecraft var10001 = mc;
      String Bypass = var10000.append(Minecraft.thePlayer.getName()).append("\"}").toString();
      PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
      packetbuffer.writeString(Bypass);
      Minecraft var3 = mc;
      Minecraft.thePlayer.sendQueue.addToSendQueue(new C17PacketCustomPayload("AntiCheat", packetbuffer));
      this.setKey(false);
   }

   private void setKey(boolean b) {
   }
}
