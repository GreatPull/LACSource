package net.minecraft.entity.player.Really.Client.module.modules.world;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;

public class Deathclip extends Module {
   private TimerUtil timer = new TimerUtil();

   public Deathclip() {
      super("DeathClip", new String[]{"deathc", "dc"}, ModuleType.World);
      this.setColor((new Color(157, 58, 157)).getRGB());
   }

   @EventHandler
   private void onUpdate(EventPreUpdate e) {
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer.getHealth() == 0.0F) {
         var10000 = mc;
         if(Minecraft.thePlayer.onGround) {
            var10000 = mc;
            Minecraft var10001 = mc;
            Minecraft var10003 = mc;
            Minecraft.thePlayer.boundingBox.offsetAndUpdate(Minecraft.thePlayer.posX, -10.0D, Minecraft.thePlayer.posZ);
            if(this.timer.hasReached(500.0D)) {
               var10000 = mc;
               Minecraft.thePlayer.sendChatMessage("/home");
            }
         }
      }

   }
}
