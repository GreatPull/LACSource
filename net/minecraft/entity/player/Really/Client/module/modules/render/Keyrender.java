package net.minecraft.entity.player.Really.Client.module.modules.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender2D;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.ui.font.CFontRenderer;
import net.minecraft.entity.player.Really.Client.ui.font.FontLoaders;

public class Keyrender extends Module {
   int anima;
   int anima2;
   int anima3;
   int anima4;
   int anima5;
   int anima6;
   private Numbers x = new Numbers("X", "X", Double.valueOf(500.0D), Double.valueOf(1.0D), Double.valueOf(1920.0D), Double.valueOf(5.0D));
   private Numbers y = new Numbers("Y", "Y", Double.valueOf(2.0D), Double.valueOf(1.0D), Double.valueOf(1080.0D), Double.valueOf(5.0D));
   private double rainbowTick;

   public Keyrender() {
      super("KeyRender", new String[]{"Key"}, ModuleType.Render);
      this.addValues(new Value[]{this.x, this.y});
   }

   @EventHandler
   public void onGui(EventRender2D e) {
      CFontRenderer font = FontLoaders.kiona18;
      Color rainbow = new Color(Color.HSBtoRGB((float)((double)Minecraft.thePlayer.ticksExisted / 250.0D + Math.sin(this.rainbowTick / 100.0D * 1.5D)) % 1.0F, 0.8F, 0.9F));
      float xOffset = ((Double)this.x.getValue()).floatValue();
      float yOffset = ((Double)this.y.getValue()).floatValue();
      Gui.drawRect((double)xOffset + 26.0D, (double)yOffset, (double)(xOffset + 51.0F), (double)(yOffset + 25.0F), (new Color(0, 0, 0, 150)).getRGB());
      Gui.drawRect((double)xOffset + 26.0D, (double)yOffset + 26.0D, (double)(xOffset + 51.0F), (double)(yOffset + 51.0F), (new Color(0, 0, 0, 150)).getRGB());
      Gui.drawRect((double)xOffset, (double)yOffset + 26.0D, (double)(xOffset + 25.0F), (double)(yOffset + 51.0F), (new Color(0, 0, 0, 150)).getRGB());
      Gui.drawRect((double)xOffset + 52.0D, (double)yOffset + 26.0D, (double)(xOffset + 77.0F), (double)(yOffset + 51.0F), (new Color(0, 0, 0, 150)).getRGB());
      Gui.drawRect((double)xOffset + 1.0D + 38.0D, (double)yOffset + 52.0D, (double)(xOffset + 77.0F), (double)(yOffset + 77.0F), (new Color(0, 0, 0, 150)).getRGB());
      Gui.drawRect((double)xOffset, (double)yOffset + 52.0D, (double)(xOffset + 38.0F), (double)(yOffset + 77.0F), (new Color(0, 0, 0, 150)).getRGB());
      font.drawStringWithShadow("W", (double)(xOffset + 34.5F), (double)(yOffset + 9.0F), rainbow.getRGB());
      font.drawStringWithShadow("S", (double)(xOffset + 36.0F), (double)(yOffset + 35.0F), rainbow.getRGB());
      font.drawStringWithShadow("A", (double)(xOffset + 10.0F), (double)(yOffset + 35.0F), rainbow.getRGB());
      font.drawStringWithShadow("D", (double)(xOffset + 62.0F), (double)(yOffset + 35.0F), rainbow.getRGB());
      font.drawStringWithShadow("LMB", (double)(xOffset + 10.0F), (double)(yOffset + 60.0F), rainbow.getRGB());
      font.drawStringWithShadow("RMB", (double)(xOffset + 50.0F), (double)(yOffset + 60.0F), rainbow.getRGB());
      if(++this.rainbowTick > 10000.0D) {
         this.rainbowTick = 0.0D;
      }

      Minecraft var10000 = mc;
      if(Minecraft.gameSettings.keyBindForward.pressed) {
         if(this.anima < 25) {
            this.anima += 5;
         }
      } else if(this.anima > 0) {
         this.anima -= 5;
      }

      var10000 = mc;
      if(Minecraft.gameSettings.keyBindBack.pressed) {
         if(this.anima2 < 25) {
            this.anima2 += 5;
         }
      } else if(this.anima2 > 0) {
         this.anima2 -= 5;
      }

      var10000 = mc;
      if(Minecraft.gameSettings.keyBindLeft.pressed) {
         if(this.anima3 < 25) {
            this.anima3 += 5;
         }
      } else if(this.anima3 > 0) {
         this.anima3 -= 5;
      }

      var10000 = mc;
      if(Minecraft.gameSettings.keyBindRight.pressed) {
         if(this.anima4 < 25) {
            this.anima4 += 5;
         }
      } else if(this.anima4 > 0) {
         this.anima4 -= 5;
      }

      var10000 = mc;
      if(Minecraft.gameSettings.keyBindUseItem.pressed) {
         if(this.anima5 < 25) {
            this.anima5 += 5;
         }
      } else if(this.anima5 > 0) {
         this.anima5 -= 5;
      }

      var10000 = mc;
      if(Minecraft.gameSettings.keyBindAttack.pressed) {
         if(this.anima6 < 25) {
            this.anima6 += 5;
         }
      } else if(this.anima6 > 0) {
         this.anima6 -= 5;
      }

      Gui.drawRect((double)xOffset + 26.0D, (double)yOffset + 25.0D, (double)(xOffset + 51.0F), (double)(yOffset + 25.0F - (float)this.anima), (new Color(255, 255, 255, 120)).getRGB());
      Gui.drawRect((double)xOffset + 26.0D, (double)yOffset + 51.0D, (double)(xOffset + 51.0F), (double)(yOffset + 51.0F - (float)this.anima2), (new Color(255, 255, 255, 120)).getRGB());
      Gui.drawRect((double)xOffset, (double)yOffset + 51.0D, (double)(xOffset + 25.0F), (double)(yOffset + 51.0F - (float)this.anima3), (new Color(255, 255, 255, 120)).getRGB());
      Gui.drawRect((double)xOffset + 52.0D, (double)yOffset + 51.0D, (double)(xOffset + 77.0F), (double)(yOffset + 51.0F - (float)this.anima4), (new Color(255, 255, 255, 120)).getRGB());
      Gui.drawRect((double)xOffset + 1.0D + 38.0D, (double)yOffset + 77.0D, (double)(xOffset + 77.0F), (double)(yOffset + 77.0F - (float)this.anima5), (new Color(255, 255, 255, 120)).getRGB());
      Gui.drawRect((double)xOffset, (double)yOffset + 77.0D, (double)(xOffset + 38.0F), (double)(yOffset + 77.0F - (float)this.anima6), (new Color(255, 255, 255, 120)).getRGB());
   }

   public void onDisable() {
      this.anima = 0;
      this.anima2 = 0;
      this.anima3 = 0;
      this.anima4 = 0;
      this.anima5 = 0;
      this.anima6 = 0;
      super.onDisable();
   }

   public void onEnable() {
      super.isEnabled();
   }
}
