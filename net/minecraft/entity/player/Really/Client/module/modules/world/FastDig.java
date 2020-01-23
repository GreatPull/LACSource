package net.minecraft.entity.player.Really.Client.module.modules.world;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;

public class FastDig extends Module {
   public FastDig() {
      super("FastDig", new String[]{"novoid", "antifall"}, ModuleType.World);
      this.setColor((new Color(223, 233, 233)).getRGB());
   }

   @EventHandler
   private void onUpdate(EventPreUpdate e) {
       Minecraft var10000 = mc;
       Minecraft.playerController.blockHitDelay = 0;
       var10000 = mc;
       if(Minecraft.playerController.curBlockDamageMP >= 0.7F) {
          var10000 = mc;
          Minecraft.playerController.curBlockDamageMP = 1.0F;
       }
   }
}
