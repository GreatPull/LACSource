package net.minecraft.entity.player.Really.Client.module.modules.world;

import java.awt.Color;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPacketSend;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import optifine.MathUtils;

public class PingSpoof extends Module {
   private List<Packet> packetList = new CopyOnWriteArrayList();
   private TimerUtil timer = new TimerUtil();

   public PingSpoof() {
      super("PingSpoof", new String[]{"spoofping", "ping"}, ModuleType.World);
      this.setColor((new Color(117, 52, 203)).getRGB());
   }

   @EventHandler
   private void onPacketSend(EventPacketSend e) {
       if(EventPacketSend.getPacket() instanceof C00PacketKeepAlive) {
           Minecraft var10000 = mc;
           if(Minecraft.thePlayer.isEntityAlive()) {
              this.packetList.add(EventPacketSend.getPacket());
              e.setCancelled(true);
           }
        }

        if(this.timer.hasReached(750.0D)) {
           if(!this.packetList.isEmpty()) {
              int i = 0;
              double totalPackets = MathUtils.getIncremental(Math.random() * 10.0D, 1.0D);

              for(Packet packet : this.packetList) {
                 if((double)i < totalPackets) {
                    ++i;
                    Minecraft var7 = mc;
                    Minecraft.getNetHandler().getNetworkManager().sendPacket(packet);
                    this.packetList.remove(packet);
                 }
              }
           }

           Minecraft var8 = mc;
           Minecraft.getNetHandler().getNetworkManager().sendPacket(new C00PacketKeepAlive(10000));
           this.timer.reset();
        }
   }
}
