package net.minecraft.entity.player.Really.Client.ui.clickgui;

import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.ui.clickgui.ClickUI;
import net.minecraft.entity.player.Really.Client.ui.clickgui.ValueButton;
import net.minecraft.entity.player.Really.Client.ui.font.CFontRenderer;
import net.minecraft.entity.player.Really.Client.ui.font.FontLoaders;
import org.lwjgl.input.Keyboard;

public class KeyBindButton extends ValueButton {
   public Module cheat;
   public double opacity = 0.0D;
   public boolean bind;

   public KeyBindButton(Module cheat, int x2, int y2) {
      super((Value)null, x2, y2);
      this.custom = true;
      this.bind = false;
      this.cheat = cheat;
   }

   public void render(int mouseX, int mouseY) {
      double d2 = mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y - 6 && mouseY < this.y + FontLoaders.sansation18.getStringHeight(this.cheat.getName()) + 5?(this.opacity + 10.0D < 200.0D?(this.opacity += 10.0D):200.0D):(this.opacity - 6.0D > 0.0D?(this.opacity -= 6.0D):0.0D);
      this.opacity = d2;
      CFontRenderer font = FontLoaders.sansation14;
      font.drawStringWithShadow("Bind", (double)(this.x - 3), (double)(this.y + 10), -1);
      font.drawStringWithShadow(Keyboard.getKeyName(this.cheat.getKey()), (double)(this.x + 70 - font.getStringWidth(Keyboard.getKeyName(this.cheat.getKey()))), (double)(this.y + 10), -1);
   }

   public void key(char typedChar, int keyCode) {
      if(this.bind) {
         this.cheat.setKey(keyCode);
         if(keyCode == 1) {
            this.cheat.setKey(0);
         }

         ClickUI.binding = false;
         this.bind = false;
      }

      super.key(typedChar, keyCode);
   }

   public void click(int mouseX, int mouseY, int button) {
      if(mouseX > this.x - 7 && mouseX < this.x + 85 && mouseY > this.y - 6 && mouseY < this.y + FontLoaders.sansation18.getStringHeight(this.cheat.getName()) + 5 && button == 0) {
         this.bind = !this.bind;
         ClickUI.binding = this.bind;
      }

      super.click(mouseX, mouseY, button);
   }
}
