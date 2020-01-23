package net.minecraft.entity.player.Really.Client.module.modules.player;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;

public class Damage extends Module {
   private static Numbers health = new Numbers("DamageHealth", "DamageHealth", Double.valueOf(1.0D), Double.valueOf(1.0D), Double.valueOf(100.0D), Double.valueOf(1.0D));

   public Damage() {
      super("Damage", new String[]{"Damage", "Die"}, ModuleType.Player);
      this.setColor((new Color(158, 114, 243)).getRGB());
      this.addValues(new Value[]{health});
   }

   public void damagePlayer(double d) {
      if(d < 1.0D) {
         d = 1.0D;
      }

      Minecraft var10001 = mc;
      if(d > (double)MathHelper.floor_double((double)Minecraft.thePlayer.getMaxHealth())) {
         Minecraft var10000 = mc;
         d = (double)MathHelper.floor_double((double)Minecraft.thePlayer.getMaxHealth());
      }

      double offset = 0.0625D;
      Minecraft var6 = mc;
      if(Minecraft.thePlayer != null) {
         var6 = mc;
         if(Minecraft.getNetHandler() != null) {
            var6 = mc;
            if(Minecraft.thePlayer.onGround) {
               for(int i = 0; (double)i <= (3.0D + d) / offset; ++i) {
                  var6 = mc;
                  NetHandlerPlayClient var10 = Minecraft.getNetHandler();
                  Minecraft var10003 = mc;
                  Minecraft var10004 = mc;
                  double var14 = Minecraft.thePlayer.posY + offset;
                  Minecraft var10005 = mc;
                  var10.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, var14, Minecraft.thePlayer.posZ, false));
                  Minecraft var11 = mc;
                  NetHandlerPlayClient var12 = Minecraft.getNetHandler();
                  var10003 = mc;
                  Minecraft var15 = mc;
                  var10005 = mc;
                  var12.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, (double)i == (3.0D + d) / offset));
               }
            }
         }
      }

   }

   public void onEnable() {
      this.damagePlayer(((Double)health.getValue()).doubleValue());
      this.setEnabled(false);
   }

   public void onDisable() {
   }
}
