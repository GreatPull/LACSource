package net.minecraft.entity.player.Really.Client.module.modules.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventTick;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class FullBright extends Module {
   private float old;

   public FullBright() {
      super("FullBright", new String[]{"fbright", "brightness", "bright"}, ModuleType.Render);
      this.setColor((new Color(244, 255, 149)).getRGB());
   }

   public void onEnable() {
      Minecraft var10001 = mc;
      this.old = Minecraft.gameSettings.gammaSetting;
      super.onEnable();
   }

   @EventHandler
   private void onTick(EventTick e) {
      Minecraft var10000 = mc;
      Minecraft.gameSettings.gammaSetting = 1.5999999E7F;
      Minecraft.thePlayer.addPotionEffect(new PotionEffect(Potion.nightVision.getId(), 10000, 1));
   }

   public void onDisable() {
      Minecraft var10000 = mc;
      Minecraft.gameSettings.gammaSetting = this.old;
      super.onDisable();
   }
}
