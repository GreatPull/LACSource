package net.minecraft.entity.player.Really.Client.ui.clickgui;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.management.ModuleManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.ui.clickgui.Button;
import net.minecraft.entity.player.Really.Client.ui.clickgui.ValueButton;
import net.minecraft.entity.player.Really.Client.ui.font.CFontRenderer;
import net.minecraft.entity.player.Really.Client.ui.font.FontLoaders;
import net.minecraft.entity.player.Really.Client.utils.RenderUtils;
import org.lwjgl.input.Mouse;

public class Window {
   public ModuleType category;
   public ArrayList<Button> buttons = Lists.newArrayList();
   public boolean drag;
   public boolean extended = true;
   public int x;
   public int y;
   public int expand;
   public int dragX;
   public int dragY;
   public int max;
   public int scroll;
   public int scrollTo;
   int R;
   int G = 0;
   int B = 0;
   int Rx = 0;
   int Gx = 0;
   int Bx = 0;
   public double angel;

   public Window(ModuleType category, int x2, int y2) {
      this.category = category;
      this.x = x2;
      this.y = y2;
      this.max = 120;
      int y22 = y2 + 22;
      Client.getModuleManager();

      for(Module c2 : ModuleManager.getModules()) {
         if(c2.getType() == category) {
            this.buttons.add(new Button(c2, x2 + 5, y22));
            y22 += 15;
         }
      }

      for(Button b2 : this.buttons) {
         b2.setParent(this);
      }

   }

   public void render(int mouseX, int mouseY) {
      CFontRenderer font = FontLoaders.sansation28;
      int current = 0;

      for(Button b3 : this.buttons) {
         if(b3.expand) {
            for(ValueButton v2 : b3.buttons) {
               current += 19;
            }
         }

         current += 19;
      }

      int height = 12 + current;
      if(this.extended) {
         this.expand = this.expand + 5 < height?(this.expand += 5):height;
         this.angel = this.angel + 20.0D < 180.0D?(this.angel += 20.0D):180.0D;
      } else {
         this.expand = this.expand - 5 > 0?(this.expand -= 5):0;
         this.angel = this.angel - 20.0D > 0.0D?(this.angel -= 20.0D):0.0D;
      }

      Gui.drawRect((double)this.x, (double)(this.y + 15), (double)(this.x + 90), (double)(this.y + 3 + this.expand), (new Color(0, 0, 0, 200)).getRGB());
      Gui.drawRect((double)this.x, (double)(this.y + 3 + this.expand), (double)(this.x + 90), (double)(this.y + 5 + this.expand), (new Color(0, 0, 0, 150)).getRGB());
      Gui.drawRect((double)(this.x + 1), (double)(this.y + 5 + this.expand), (double)(this.x + 89), (double)(this.y + 6 + this.expand), (new Color(0, 0, 0, 150)).getRGB());
      Gui.drawRect((double)(this.x + 1), (double)(this.y + 5 + this.expand), (double)this.x + 0.5D, (double)this.y + 5.5D + (double)this.expand, (new Color(0, 0, 0, 150)).getRGB());
      Gui.drawRect((double)this.x + 89.5D, (double)(this.y + 5 + this.expand), (double)(this.x + 89), (double)this.y + 5.5D + (double)this.expand, (new Color(0, 0, 0, 150)).getRGB());
      Gui.drawRect((double)this.x, (double)(this.y - 2), (double)(this.x + 90), (double)(this.y + 15), (new Color(0, 0, 0, 200)).getRGB());
      Gui.drawRect((double)(this.x + 1), (double)(this.y - 2), (double)(this.x + 89), (double)(this.y - 3), (new Color(0, 0, 0, 200)).getRGB());
      Gui.drawRect((double)(this.x + 1), (double)(this.y - 2), (double)this.x + 0.5D, (double)this.y - 2.5D, (new Color(0, 0, 0, 200)).getRGB());
      Gui.drawRect((double)this.x + 89.5D, (double)(this.y - 2), (double)(this.x + 89), (double)this.y - 2.5D, (new Color(0, 0, 0, 200)).getRGB());
      FontLoaders.sansation18.drawStringWithShadow(this.category.name(), (double)(this.x + 6), (double)(this.y + 5), (new Color(180, 180, 180)).getRGB());
      FontLoaders.sansation14.drawStringWithShadow(this.extended?"-":"+", (double)(this.x + 80), (double)(this.extended?this.y + 3:this.y + 3), (new Color(180, 180, 180)).getRGB());
      GlStateManager.pushMatrix();
      GlStateManager.translate((float)(this.x + 90 - 10), (float)(this.y + 5), 0.0F);
      GlStateManager.rotate((float)this.angel, 0.0F, 0.0F, -1.0F);
      GlStateManager.translate((float)(-this.x + 90 - 10), (float)(-this.y + 5), 0.0F);
      GlStateManager.popMatrix();
      if(this.expand == height) {
         GlStateManager.pushMatrix();
         this.buttons.forEach((b2) -> {
            b2.render(mouseX, mouseY);
         });
         RenderUtils.post();
         GlStateManager.popMatrix();
      }

      if(this.drag) {
         if(!Mouse.isButtonDown(0)) {
            this.drag = false;
         }

         this.x = mouseX - this.dragX;
         this.y = mouseY - this.dragY;
         ((Button)this.buttons.get(0)).y = this.y + 22 - this.scroll;

         for(Button b4 : this.buttons) {
            b4.x = this.x + 5;
         }
      }

   }

   public void key(char typedChar, int keyCode) {
      this.buttons.forEach((b2) -> {
         b2.key(typedChar, keyCode);
      });
   }

   public void mouseScroll(int mouseX, int mouseY, int amount) {
      if(mouseX > this.x - 2 && mouseX < this.x + 92 && mouseY > this.y - 2 && mouseY < this.y + 17 + this.expand) {
         this.scrollTo = (int)((float)this.scrollTo - (float)(amount / 120 * 28));
      }

   }

   public void click(int mouseX, int mouseY, int button) {
      if(mouseX > this.x - 2 && mouseX < this.x + 92 && mouseY > this.y - 2 && mouseY < this.y + 17) {
         if(button == 1) {
            this.extended = !this.extended;
            boolean var4 = this.extended;
         }

         if(button == 0) {
            this.drag = true;
            this.dragX = mouseX - this.x;
            this.dragY = mouseY - this.y;
         }
      }

      if(this.extended) {
         this.buttons.stream().filter((b2) -> {
            return b2.y < this.y + this.expand;
         }).forEach((b2) -> {
            b2.click(mouseX, mouseY, button);
         });
      }

   }
}
