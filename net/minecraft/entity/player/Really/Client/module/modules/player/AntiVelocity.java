package net.minecraft.entity.player.Really.Client.module.modules.player;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPacketRecieve;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.module.modules.player.ThisMode;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class AntiVelocity extends Module {
   private Numbers percentage = new Numbers("Percentage", "percentage", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(100.0D), Double.valueOf(5.0D));
   private Mode mode = new Mode("Mode", "mode", ThisMode.values(), ThisMode.Normal);

   public AntiVelocity() {
      super("Velocity", new String[]{"antivelocity", "antiknockback", "antikb"}, ModuleType.Player);
      this.addValues(new Value[]{this.percentage, this.mode});
      this.setColor((new Color(191, 191, 191)).getRGB());
   }

   @EventHandler
   private void onPacket(EventPacketRecieve e) {
      if(e.getPacket() instanceof S12PacketEntityVelocity || e.getPacket() instanceof S27PacketExplosion) {
         if(this.mode.getValue() == ThisMode.AAC) {
            Minecraft var10000 = mc;
            if(!Minecraft.thePlayer.onGround) {
               if(((Double)this.percentage.getValue()).equals(Double.valueOf(0.0D))) {
                  e.setCancelled(true);
               } else {
                  S12PacketEntityVelocity packet = (S12PacketEntityVelocity)e.getPacket();
                  packet.motionX = (int)(((Double)this.percentage.getValue()).doubleValue() / 100.0D);
                  packet.motionY = (int)(((Double)this.percentage.getValue()).doubleValue() / 100.0D);
                  packet.motionZ = (int)(((Double)this.percentage.getValue()).doubleValue() / 100.0D);
               }
            }
         }

         if(this.mode.getValue() == ThisMode.Normal) {
            if(((Double)this.percentage.getValue()).equals(Double.valueOf(0.0D))) {
               e.setCancelled(true);
            } else {
               S12PacketEntityVelocity packet = (S12PacketEntityVelocity)e.getPacket();
               packet.motionX = (int)(((Double)this.percentage.getValue()).doubleValue() / 100.0D);
               packet.motionY = (int)(((Double)this.percentage.getValue()).doubleValue() / 100.0D);
               packet.motionZ = (int)(((Double)this.percentage.getValue()).doubleValue() / 100.0D);
            }
         }
      }

   }
}
