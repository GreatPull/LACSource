package net.minecraft.entity.player.Really.Client.module.modules.player;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class Zoot extends Module {
   public Zoot() {
      super("Zoot", new String[]{"Firion", "antipotion", "antifire"}, ModuleType.Player);
      this.setColor((new Color(208, 203, 229)).getRGB());
   }

   @EventHandler
   private void onUpdate(EventPreUpdate e) {
      for(Potion potion : Potion.potionTypes) {
         if(EventPreUpdate.getType() == 0 && potion != null) {
            Minecraft var10000 = mc;
            PotionEffect effect;
            if((effect = Minecraft.thePlayer.getActivePotionEffect(potion)) == null || !potion.isBadEffect()) {
               var10000 = mc;
               if(!Minecraft.thePlayer.isBurning()) {
                  continue;
               }

               var10000 = mc;
               if(Minecraft.thePlayer.isInWater()) {
                  continue;
               }

               var10000 = mc;
               if(!Minecraft.thePlayer.onGround) {
                  continue;
               }
            }

            int i = 0;

            while(true) {
               var10000 = mc;
               if(Minecraft.thePlayer.isBurning()) {
                  if(i >= 20) {
                     break;
                  }
               } else if(i >= effect.getDuration() / 20) {
                  break;
               }

               var10000 = mc;
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer(true));
               ++i;
            }
         }
      }

   }
}
