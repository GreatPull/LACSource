package net.minecraft.entity.player.Really.Client.module.modules.world;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;

public class WorldTime extends Module {
   private Numbers Time = new Numbers("Time", "Time", Double.valueOf(18000.0D), Double.valueOf(0.0D), Double.valueOf(24000.0D), Double.valueOf(1.0D));

   public WorldTime() {
      super("WorldTime", new String[]{"WorldTime", "WorldTime"}, ModuleType.World);
      this.setColor((new Color(198, 253, 191)).getRGB());
      super.addValues(new Value[]{this.Time});
   }

   public void onEnable() {
      super.onEnable();
   }

   @EventHandler
   public void onUpdate(EventPreUpdate event) {
      Minecraft.theWorld.setWorldTime(((Double)this.Time.getValue()).longValue());
   }
}
