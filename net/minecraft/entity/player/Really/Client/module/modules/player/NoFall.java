package net.minecraft.entity.player.Really.Client.module.modules.player;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;

public class NoFall extends Module {
   private float fall;

   public NoFall() {
      super("NoFall", new String[]{"Nofalldamage"}, ModuleType.Player);
      this.setColor((new Color(242, 137, 73)).getRGB());
   }

   @EventHandler
   private void onUpdate(EventPreUpdate e) {
      this.setSuffix("Hypixel");
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer.onGround) {
         this.fall = 0.0F;
      }

      var10000 = mc;
      if(Minecraft.thePlayer.fallDistance > 2.9F) {
         var10000 = mc;
         if(!Minecraft.thePlayer.isInWater()) {
            var10000 = mc;
            if(!Minecraft.thePlayer.isInLava()) {
               e.setOnground(true);
            }
         }
      }

   }
}
