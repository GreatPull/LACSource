package net.minecraft.entity.player.Really.Client.module.modules.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender2D;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.module.modules.render.setColor;
import net.minecraft.entity.player.Really.Client.utils.render.Colors;
import net.minecraft.entity.player.Really.Client.utils.render.RenderUtil;
import net.minecraft.util.MovementInput;

public class Crosshair extends Module {
   private boolean dragging;
   float hue;
   private Option DYNAMIC = new Option("DYNAMIC", "DYNAMIC", Boolean.valueOf(true));
   public static Numbers GAP = new Numbers("gap", "gap", Double.valueOf(5.0D), Double.valueOf(0.25D), Double.valueOf(15.0D), Double.valueOf(0.25D));
   private Numbers WIDTH = new Numbers("width", "width", Double.valueOf(2.0D), Double.valueOf(0.25D), Double.valueOf(10.0D), Double.valueOf(0.25D));
   public static Numbers SIZE = new Numbers("size", "size", Double.valueOf(7.0D), Double.valueOf(0.25D), Double.valueOf(15.0D), Double.valueOf(0.25D));

   public Crosshair() {
      super("Crosshair", new String[]{"Crosshair"}, ModuleType.Render);
      this.addValues(new Value[]{this.DYNAMIC, GAP, this.WIDTH, SIZE});
   }

   @EventHandler
   public void onGui(EventRender2D e) {
      int red = ((Double)setColor.r.getValue()).intValue();
      int green = ((Double)setColor.g.getValue()).intValue();
      int blue = ((Double)setColor.b.getValue()).intValue();
      int alph = ((Double)setColor.a.getValue()).intValue();
      double gap = ((Double)GAP.getValue()).doubleValue();
      double width = ((Double)this.WIDTH.getValue()).doubleValue();
      double size = ((Double)SIZE.getValue()).doubleValue();
      ScaledResolution scaledRes = new ScaledResolution(mc);
      RenderUtil.rectangleBordered((double)(scaledRes.getScaledWidth() / 2) - width, (double)(scaledRes.getScaledHeight() / 2) - gap - size - (double)(this.isMoving()?2:0), (double)((float)(scaledRes.getScaledWidth() / 2) + 1.0F) + width, (double)(scaledRes.getScaledHeight() / 2) - gap - (double)(this.isMoving()?2:0), 0.5D, Colors.getColor(red, green, blue, alph), (new Color(0, 0, 0, alph)).getRGB());
      RenderUtil.rectangleBordered((double)(scaledRes.getScaledWidth() / 2) - width, (double)(scaledRes.getScaledHeight() / 2) + gap + 1.0D + (double)(this.isMoving()?2:0) - 0.15D, (double)((float)(scaledRes.getScaledWidth() / 2) + 1.0F) + width, (double)(scaledRes.getScaledHeight() / 2 + 1) + gap + size + (double)(this.isMoving()?2:0) - 0.15D, 0.5D, Colors.getColor(red, green, blue, alph), (new Color(0, 0, 0, alph)).getRGB());
      RenderUtil.rectangleBordered((double)(scaledRes.getScaledWidth() / 2) - gap - size - (double)(this.isMoving()?2:0) + 0.15D, (double)(scaledRes.getScaledHeight() / 2) - width, (double)(scaledRes.getScaledWidth() / 2) - gap - (double)(this.isMoving()?2:0) + 0.15D, (double)((float)(scaledRes.getScaledHeight() / 2) + 1.0F) + width, 0.5D, Colors.getColor(red, green, blue, alph), (new Color(0, 0, 0, alph)).getRGB());
      RenderUtil.rectangleBordered((double)(scaledRes.getScaledWidth() / 2 + 1) + gap + (double)(this.isMoving()?2:0), (double)(scaledRes.getScaledHeight() / 2) - width, (double)(scaledRes.getScaledWidth() / 2) + size + gap + 1.0D + (double)(this.isMoving()?2:0), (double)((float)(scaledRes.getScaledHeight() / 2) + 1.0F) + width, 0.5D, Colors.getColor(red, green, blue, alph), (new Color(0, 0, 0, alph)).getRGB());
   }

   public boolean isMoving() {
      if(((Boolean)this.DYNAMIC.getValue()).booleanValue()) {
         Minecraft var10000 = mc;
         if(!Minecraft.thePlayer.isCollidedHorizontally) {
            var10000 = mc;
            if(!Minecraft.thePlayer.isSneaking()) {
               var10000 = mc;
               MovementInput var3 = Minecraft.thePlayer.movementInput;
               if(MovementInput.moveForward != 0.0F) {
                  return true;
               }

               Minecraft var4 = mc;
               MovementInput var5 = Minecraft.thePlayer.movementInput;
               if(MovementInput.moveStrafe != 0.0F) {
                  return true;
               }
            }
         }
      }

      return false;
   }
}
