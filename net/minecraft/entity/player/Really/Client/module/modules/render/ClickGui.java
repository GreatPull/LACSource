package net.minecraft.entity.player.Really.Client.module.modules.render;

import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.ui.csgo.NewClickGui;
import net.minecraft.entity.player.Really.Client.utils.Wrapper;

public class ClickGui extends Module {
   public static Option rb = new Option("rainbow", "rainbow", Boolean.valueOf(true));

   public ClickGui() {
      super("ClickGui", new String[]{"clickui"}, ModuleType.Render);
   }

   public void onEnable() {
      Wrapper.mc.displayGuiScreen(new NewClickGui());
      this.setEnabled(false);
   }
}
