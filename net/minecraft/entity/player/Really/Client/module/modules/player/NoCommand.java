package net.minecraft.entity.player.Really.Client.module.modules.player;

import java.awt.Color;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;

public class NoCommand extends Module {
   public NoCommand() {
      super("NoCommand", new String[]{"Commnand", "Commnand"}, ModuleType.Player);
      this.setColor((new Color(223, 233, 233)).getRGB());
   }
}
