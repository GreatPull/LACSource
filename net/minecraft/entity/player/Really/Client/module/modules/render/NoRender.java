package net.minecraft.entity.player.Really.Client.module.modules.render;

import java.awt.Color;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;

public class NoRender extends Module {
   public NoRender() {
      super("NoRender", new String[]{"noitems"}, ModuleType.Render);
      this.setColor((new Color(166, 185, 123)).getRGB());
   }
}
