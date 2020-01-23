package net.minecraft.entity.player.Really.Client.module.modules.render;

import java.awt.Color;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;

public class TabGui extends Module {
   public TabGui() {
      super("TabGui", new String[]{"noslow"}, ModuleType.Render);
      this.setColor((new Color(216, 253, 100)).getRGB());
      this.setEnabled(true);
   }
}
