package net.minecraft.entity.player.Really.Client.module.modules.render;

import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;

public class setColor extends Module {
   public static Numbers r = new Numbers("Red", "Red", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
   public static Numbers g = new Numbers("Green", "Green", Double.valueOf(111.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
   public static Numbers b = new Numbers("Blue", "Blue", Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
   public static Numbers a = new Numbers("Alpha", "Alpha", Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));

   public setColor() {
      super("CustomColor", new String[]{"SetColor"}, ModuleType.Render);
      this.addValues(new Value[]{r, g, b, a});
   }

   @EventHandler
   private void onUpdate(EventPreUpdate e) {
      this.setEnabled(false);
   }
}
