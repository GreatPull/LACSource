package net.minecraft.entity.player.Really.Client.module.modules.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;

public class NoHurtCam extends Module {
   public NoHurtCam() {
      super("NoHurtCam", new String[]{"NoHurtCam"}, ModuleType.Render);
      this.setColor((new Color(208, 30, 142)).getRGB());
   }

   @EventHandler
   private void onUpdate(EventPreUpdate event) {
      Minecraft var10000 = mc;
      Minecraft.thePlayer.hurtTime = 0;
   }
}
