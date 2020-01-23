package net.minecraft.entity.player.Really.Client.module.modules.render;

import java.awt.Color;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;

public class EnchantEffect extends Module {
   public static Numbers r = new Numbers("R", "R", Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
   public static Numbers g = new Numbers("G", "G", Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
   public static Numbers b = new Numbers("B", "B", Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));

   public EnchantEffect() {
      super("EnchantEffect", new String[]{"EnchantEffect", "EnchantEffect", "FluxColor"}, ModuleType.Render);
      this.setColor((new Color(191, 191, 191)).getRGB());
      this.addValues(new Value[]{r, g, b});
   }
}
