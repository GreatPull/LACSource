package net.minecraft.entity.player.Really.Client.ui.clickgui;

import java.awt.Color;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.ui.font.FontLoaders;
import net.minecraft.entity.player.Really.Client.utils.RenderUtils;
import net.minecraft.entity.player.Really.Client.utils.render.RenderUtil;
import org.lwjgl.input.Mouse;

public class ValueButton {
   public Value value;
   public String name;
   public boolean custom = false;
   public boolean change;
   public int x;
   public int y;
   public double opacity = 0.0D;

   public ValueButton(Value value, int x, int y) {
      this.value = value;
      this.x = x;
      this.y = y;
      this.name = "";
      if(this.value instanceof Option) {
         this.change = ((Boolean)((Option)this.value).getValue()).booleanValue();
      } else if(this.value instanceof Mode) {
         this.name = "" + ((Mode)this.value).getValue();
      } else if(value instanceof Numbers) {
         Numbers v = (Numbers)value;
         this.name = String.valueOf(this.name) + (v.isInteger()?(double)((Number)v.getValue()).intValue():((Number)v.getValue()).doubleValue());
      }

      this.opacity = 0.0D;
   }

   public void render(int mouseX, int mouseY) {
      if(!this.custom) {
         if(mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y - 6 && mouseY < this.y + FontLoaders.sansation14.getStringHeight(this.value.getName()) + 5) {
            if(this.opacity + 10.0D < 200.0D) {
               this.opacity += 10.0D;
            } else {
               this.opacity = 200.0D;
            }
         } else if(this.opacity - 6.0D > 0.0D) {
            this.opacity -= 6.0D;
         } else {
            this.opacity = 0.0D;
         }

         if(this.value instanceof Option) {
            if(this.change) {
               Gui.drawRect((double)(this.x - 7), (double)(this.y + 2), (double)(this.x + 1), (double)(this.y + 10), (new Color(255, 255, 255)).getRGB());
            } else {
               RenderUtil.rectangleBordered((double)(this.x - 7), (double)(this.y + 2), (double)(this.x + 1), (double)(this.y + 10), 0.5D, (new Color(61, 141, 255, 0)).getRGB(), (new Color(255, 255, 255)).getRGB());
            }
         }

         if(this.value instanceof Option) {
            this.change = ((Boolean)((Option)this.value).getValue()).booleanValue();
         } else if(this.value instanceof Mode) {
            this.name = "" + ((Mode)this.value).getValue();
         } else if(this.value instanceof Numbers) {
            Numbers v1 = (Numbers)this.value;
            this.name = "" + (v1.isInteger()?(double)((Number)v1.getValue()).intValue():((Number)v1.getValue()).doubleValue());
            if(mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y + 6 && mouseY < this.y + 11 && Mouse.isButtonDown(0)) {
               double render = v1.getMinimum().doubleValue();
               double max = v1.getMaximum().doubleValue();
               double inc = v1.getIncrement().doubleValue();
               double valAbs = (double)mouseX - ((double)this.x + 1.0D);
               double perc = valAbs / 68.0D;
               perc = Math.min(Math.max(0.0D, perc), 1.0D);
               double valRel = (max - render) * perc;
               double val = render + valRel;
               val = (double)Math.round(val * (1.0D / inc)) / (1.0D / inc);
               v1.setValue(Double.valueOf(val));
            }
         }

         if(this.value instanceof Numbers) {
            Numbers v1 = (Numbers)this.value;
            double render = (double)(68.0F * (((Number)v1.getValue()).floatValue() - v1.getMinimum().floatValue()) / (v1.getMaximum().floatValue() - v1.getMinimum().floatValue()));
            RenderUtils.drawRect((float)this.x - 6.0F, (float)(this.y + 10), (float)((double)this.x + 2.0D), (float)(this.y + 11), (new Color(50, 50, 50)).getRGB());
            RenderUtils.drawRect((float)this.x - 6.0F, (float)(this.y + 10), (float)((double)this.x + render + 2.0D), (float)(this.y + 11), (new Color(255, 255, 255)).getRGB());
            Gui.drawFilledCircle((double)((float)((double)this.x + render + 4.0D)), (double)((float)((double)this.y + 10.5D)), 2.0D, (new Color(255, 255, 255)).getRGB(), mouseY);
         }

         if(this.value instanceof Option) {
            FontLoaders.sansation14.drawString(this.value.getName(), (float)(this.x + 12), (float)(this.y + 1 - 1 + 5), -1);
         } else {
            FontLoaders.sansation14.drawString(this.value.getName(), (float)(this.x - 2), (float)(this.y + 4), -10);
         }

         if(this.name != "") {
            FontLoaders.sansation14.drawString(this.name, (float)(this.x - FontLoaders.sansation14.getStringWidth(this.name) + 75), (float)(this.y + 4), -5);
         }
      }

   }

   public void key(char typedChar, int keyCode) {
   }

   public void click(int mouseX, int mouseY, int button) {
      if(!this.custom && mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y - 4 && mouseY < this.y + FontLoaders.sansation14.getStringHeight(this.value.getName()) + 2) {
         if(this.value instanceof Option) {
            Option m1 = (Option)this.value;
            m1.setValue(Boolean.valueOf(!((Boolean)m1.getValue()).booleanValue()));
            return;
         }

         if(this.value instanceof Mode) {
            Mode m = (Mode)this.value;
            Enum current = (Enum)m.getValue();
            int next = current.ordinal() + 1 >= m.getModes().length?0:current.ordinal() + 1;
            this.value.setValue(m.getModes()[next]);
         }
      }

   }
}
