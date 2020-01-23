package net.minecraft.entity.player.Really.Client.module.modules.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventTick;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;

public class Chinese extends Module {
   private float old;

   public Chinese() {
      super("中文测试", new String[]{"fbright", "brightness", "bright"}, ModuleType.Render);
      this.setColor((new Color(244, 255, 149)).getRGB());
   }

   public void onEnable() {
      Minecraft var10001 = mc;
      this.old = Minecraft.gameSettings.gammaSetting;
   }

   @EventHandler
   private void onTick(EventTick e) {
      Minecraft var10000 = mc;
      Minecraft.gameSettings.gammaSetting = 1.5999999E7F;
   }

   public void onDisable() {
      Minecraft var10000 = mc;
      Minecraft.gameSettings.gammaSetting = this.old;
   }
}
