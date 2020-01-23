package net.minecraft.entity.player.Really.Client.module.modules.world;

import java.awt.Color;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPacketSend;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPostUpdate;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.Helper;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;
import net.minecraft.network.play.client.C03PacketPlayer;

public class PacketMotior extends Module {
   private int packetcount;
   private TimerUtil time = new TimerUtil();

   public PacketMotior() {
      super("PacketMotior", new String[]{"rotate"}, ModuleType.World);
      this.setColor((new Color(17, 250, 154)).getRGB());
   }

   @EventHandler
   private void onPacket(EventPacketSend e) {
      if(EventPacketSend.getPacket() instanceof C03PacketPlayer) {
         ++this.packetcount;
      }

   }

   @EventHandler
   public void OnUpdate(EventPostUpdate event) {
      if(this.time.hasReached(1000.0D)) {
         super.setSuffix("PPS:" + this.packetcount);
         if(this.packetcount > 22) {
            Helper.sendMessage("警告！Packet发送数量不正常!");
         }

         this.packetcount = 0;
         this.time.reset();
      }

   }
}
