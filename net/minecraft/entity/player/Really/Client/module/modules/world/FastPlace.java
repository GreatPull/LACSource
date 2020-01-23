package net.minecraft.entity.player.Really.Client.module.modules.world;

import java.awt.Color;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventTick;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;

public class FastPlace extends Module {
   public FastPlace() {
      super("FastPlace", new String[]{"fplace", "fc"}, ModuleType.Legit);
      this.setColor((new Color(226, 197, 78)).getRGB());
   }

   @EventHandler
   private void onTick(EventTick e) {
      mc.rightClickDelayTimer = 0;
   }
}
