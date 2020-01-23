package net.minecraft.entity.player.Really.Client.module.modules.Legit;

import java.awt.Color;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;

public class GhostHand extends Module {
   public GhostHand() {
      super("GhostHand", new String[]{"GhostHand"}, ModuleType.Legit);
      this.setColor((new Color(208, 30, 142)).getRGB());
   }
}
