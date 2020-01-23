package net.minecraft.entity.player.Really.Client.ui.clickgui;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.ArrayList;
import java.util.function.Consumer;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.management.ModuleManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.modules.render.HUD;
import net.minecraft.entity.player.Really.Client.ui.clickgui.KeyBindButton;
import net.minecraft.entity.player.Really.Client.ui.clickgui.ValueButton;
import net.minecraft.entity.player.Really.Client.ui.clickgui.Window;
import net.minecraft.entity.player.Really.Client.ui.font.FontLoaders;

public class Button {
   public Module cheat;
   public Window parent;
   public int anima;
   public int x;
   public int y;
   public int index;
   public int remander;
   public double opacity = 0.0D;
   public ArrayList<ValueButton> buttons = Lists.newArrayList();
   public boolean expand;

   public Button(Module cheat, int x2, int y2) {
      this.cheat = cheat;
      this.x = x2;
      this.y = y2;
      int y22 = y2 + 14;

      for(Value v2 : cheat.getValues()) {
         this.buttons.add(new ValueButton(v2, x2 + 5, y22));
         y22 += 15;
      }

      this.buttons.add(new KeyBindButton(cheat, x2 + 5, y22));
   }

   public void render(int mouseX, int mouseY) {
      int var10000 = this.cheat.getValues().size();
      Module var10001 = this.cheat;
      Client.getModuleManager();
      if(var10000 + (var10001 == ModuleManager.getModuleByClass(HUD.class)?2:1) != this.buttons.size()) {
         this.buttons.clear();
         int y2 = this.y + 14;

         for(Value v2 : this.cheat.getValues()) {
            this.buttons.add(new ValueButton(v2, this.x + 5, y2));
            y2 += 10;
         }

         this.buttons.add(new KeyBindButton(this.cheat, this.x + 5, y2));
      }

      if(this.index != 0) {
         Button b22 = (Button)this.parent.buttons.get(this.index - 1);
         this.y = b22.y + 19 + (b22.expand?19 * b22.buttons.size():0);
      }

      for(int i2 = 0; i2 < this.buttons.size(); ++i2) {
         ((ValueButton)this.buttons.get(i2)).y = this.y + 14 + 19 * i2;
         ((ValueButton)this.buttons.get(i2)).x = this.x + 5;
      }

      FontLoaders.sansation14.drawStringWithShadow(this.cheat.getName(), (double)(this.x + 2), (double)(this.y + 3), (new Color(180, 180, 180)).getRGB());
      if(this.cheat.isEnabled()) {
         Gui.drawRect((double)(this.x - 5), (double)(this.y - 7), (double)(this.x + 85), (double)(this.y + 12), (new Color(255, 255, 255, 30)).getRGB());
         FontLoaders.sansation14.drawStringWithShadow(this.cheat.getName(), (double)(this.x + 2), (double)(this.y + 3), (new Color(255, 255, 255)).getRGB());
      }

      if(!this.expand && this.buttons.size() > 1) {
         FontLoaders.sansation16.drawStringWithShadow("+", (double)(this.x + 75), (double)(this.y + 3), (new Color(255, 255, 255)).getRGB());
      } else if(this.expand) {
         FontLoaders.sansation16.drawStringWithShadow("-", (double)(this.x + 75), (double)(this.y + 3), (new Color(255, 255, 255)).getRGB());
      }

      if(this.expand) {
         this.buttons.forEach((b2) -> {
            b2.render(mouseX, mouseY);
         });
      }

   }

   public void key(char typedChar, int keyCode) {
      this.buttons.forEach((b2) -> {
         b2.key(typedChar, keyCode);
      });
   }

   public void click(int mouseX, int mouseY, int button) {
      if(mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y - 6 && mouseY < this.y + FontLoaders.sansation14.getStringHeight(this.cheat.getName()) + 2) {
         if(button == 0) {
            this.cheat.setEnabled(!this.cheat.isEnabled());
         }

         if(button == 1 && !this.buttons.isEmpty()) {
            this.expand = !this.expand;
            boolean var4 = this.expand;
         }
      }

      if(this.expand) {
         this.buttons.forEach((b2) -> {
            b2.click(mouseX, mouseY, button);
         });
      }

   }

   public void setParent(Window parent) {
      this.parent = parent;

      for(int i2 = 0; i2 < this.parent.buttons.size(); ++i2) {
         if(this.parent.buttons.get(i2) == this) {
            this.index = i2;
            this.remander = this.parent.buttons.size() - i2;
            break;
         }
      }

   }
}
