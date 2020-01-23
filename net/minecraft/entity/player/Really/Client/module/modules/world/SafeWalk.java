package net.minecraft.entity.player.Really.Client.module.modules.world;

import java.awt.Color;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;

public class SafeWalk extends Module {
   public SafeWalk() {
      super("SafeWalk", new String[]{"eagle", "parkour"}, ModuleType.World);
      this.setColor((new Color(198, 253, 191)).getRGB());
   }
}
