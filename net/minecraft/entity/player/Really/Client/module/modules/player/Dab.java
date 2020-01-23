package net.minecraft.entity.player.Really.Client.module.modules.player;

import java.awt.Color;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;

public class Dab extends Module {
   public Dab() {
      super("Dab", new String[]{"dab"}, ModuleType.Player);
      this.setColor((new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255))).getRGB());
      this.setRemoved(false);
   }
}
