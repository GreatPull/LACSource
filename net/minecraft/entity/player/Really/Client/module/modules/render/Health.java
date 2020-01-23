package net.minecraft.entity.player.Really.Client.module.modules.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender2D;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.ui.font.CFontRenderer;
import net.minecraft.entity.player.Really.Client.ui.font.FontLoaders;
import net.minecraft.util.MathHelper;

public class Health extends Module {
   private int width;

   public Health() {
      super("Health", new String[]{"Healthy"}, ModuleType.Render);
   }

   @EventHandler
   private void renderHud(EventRender2D event) {
      CFontRenderer font = FontLoaders.sansation18;
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer.getHealth() >= 0.0F) {
         var10000 = mc;
         if(Minecraft.thePlayer.getHealth() < 10.0F) {
            this.width = 3;
         }
      }

      var10000 = mc;
      if(Minecraft.thePlayer.getHealth() >= 10.0F) {
         var10000 = mc;
         if(Minecraft.thePlayer.getHealth() < 100.0F) {
            this.width = 6;
         }
      }

      String var10001 = "" + MathHelper.ceiling_float_int(Minecraft.thePlayer.getHealth());
      double var10002 = (double)((new ScaledResolution(mc)).getScaledWidth() / 2 - this.width);
      double var10003 = (double)((new ScaledResolution(mc)).getScaledHeight() / 2 - 13);
      Minecraft var10004 = mc;
      font.drawStringWithShadow(var10001, var10002, var10003, Minecraft.thePlayer.getHealth() <= 10.0F?(new Color(255, 0, 0)).getRGB():(new Color(0, 255, 0)).getRGB());
   }
}
