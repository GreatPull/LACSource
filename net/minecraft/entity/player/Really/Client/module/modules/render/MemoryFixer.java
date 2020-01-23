package net.minecraft.entity.player.Really.Client.module.modules.render;

import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;

public class MemoryFixer extends Module {
   public MemoryFixer() {
      super("MemoryFixer", new String[]{"FpsLoader"}, ModuleType.Render);
      System.gc();
      Runtime.getRuntime().gc();
   }
}
