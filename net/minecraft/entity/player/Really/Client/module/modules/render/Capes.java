package net.minecraft.entity.player.Really.Client.module.modules.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRenderCape;
import net.minecraft.entity.player.Really.Client.management.FriendManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;

public class Capes extends Module {
   public Capes() {
      super("Capes", new String[]{"kape"}, ModuleType.Render);
      this.setColor((new Color(159, 190, 192)).getRGB());
      this.setEnabled(true);
   }

   @EventHandler
   public void onRender(EventRenderCape event) {
      Minecraft var10000 = mc;
      if(Minecraft.theWorld != null && FriendManager.isFriend(event.getPlayer().getName())) {
         event.setLocation(Client.CLIENT_CAPE);
         event.setCancelled(true);
      }

   }
}
