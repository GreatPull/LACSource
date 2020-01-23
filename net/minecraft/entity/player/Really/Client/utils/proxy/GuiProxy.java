package net.minecraft.entity.player.Really.Client.utils.proxy;

import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.entity.player.Really.Client.utils.proxy.ConnectionInfo;
import net.minecraft.entity.player.Really.Client.utils.proxy.ProxyConfig;
import org.lwjgl.input.Keyboard;

public class GuiProxy extends GuiScreen {
   private GuiMultiplayer prevMenu;
   private GuiTextField proxyBox;
   private String error = "";
   public static boolean connected = false;

   public GuiProxy(GuiMultiplayer guiMultiplayer) {
      this.prevMenu = guiMultiplayer;
   }

   public void updateScreen() {
      this.proxyBox.updateCursorCounter();
   }

   public void initGui() {
      Keyboard.enableRepeatEvents(true);
      this.buttonList.clear();
      this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 120 + 12, "Back"));
      this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 72 + 12, "Connect"));
      this.buttonList.add(new GuiButton(2, this.width / 2 - 100, this.height / 4 + 96 + 12, "Disconnect"));
      this.proxyBox = new GuiTextField(0, this.fontRendererObj, this.width / 2 - 100, 60, 200, 20);
      this.proxyBox.setFocused(true);
   }

   public void onGuiClosed() {
      Keyboard.enableRepeatEvents(false);
   }

   protected void actionPerformed(GuiButton clickedButton) {
      if(clickedButton.enabled) {
         if(clickedButton.id == 0) {
            this.mc.displayGuiScreen(this.prevMenu);
         } else if(clickedButton.id == 1) {
            if(!this.proxyBox.getText().contains(":") || this.proxyBox.getText().split(":").length != 2) {
               this.error = "Not a proxy!";
               return;
            }

            String[] parts = this.proxyBox.getText().split(":");
            if(isInteger(parts[1]) && Integer.parseInt(parts[1]) <= 65536) {
               Integer.parseInt(parts[1]);
            }

            try {
               ProxyConfig.proxyAddr = new ConnectionInfo();
               ProxyConfig.proxyAddr.ip = parts[0];
               ProxyConfig.proxyAddr.port = Integer.parseInt(parts[1]);
               connected = true;
            } catch (Exception var4) {
               this.error = var4.toString();
               return;
            }

            if(!this.error.isEmpty()) {
               return;
            }
         } else if(clickedButton.id == 2) {
            ProxyConfig.stop();
            connected = false;
         }
      }

   }

   protected void keyTyped(char par1, int par2) {
      this.proxyBox.textboxKeyTyped(par1, par2);
      if(par2 == 28 || par2 == 156) {
         this.actionPerformed((GuiButton)this.buttonList.get(1));
      }

   }

   protected void mouseClicked(int par1, int par2, int par3) throws IOException {
      super.mouseClicked(par1, par2, par3);
      this.proxyBox.mouseClicked(par1, par2, par3);
      if(this.proxyBox.isFocused()) {
         this.error = "";
      }

   }

   public void drawScreen(int par1, int par2, float par3) {
      this.drawDefaultBackground();
      Minecraft.fontRendererObj.drawCenteredString("Proxies Reloaded", (float)(this.width / 2), 20.0F, 16777215);
      Minecraft.fontRendererObj.drawCenteredString("(SOCKS5 Proxies only)", (float)(this.width / 2), 30.0F, 16777215);
      Minecraft.fontRendererObj.drawStringWithShadow("IP:Port", (float)(this.width / 2 - 100), 47.0F, 10526880);
      Minecraft.fontRendererObj.drawCenteredString(this.error, (float)(this.width / 2), 95.0F, 16711680);
      String currentProxy = "";
      if(connected) {
         currentProxy = "§a" + ProxyConfig.proxyAddr.ip + ":" + ProxyConfig.proxyAddr.port;
      }

      if(!connected) {
         currentProxy = "§cN/A";
      }

      Minecraft.fontRendererObj.drawStringWithShadow("Current proxy: " + currentProxy, 1.0F, 3.0F, -1);
      this.proxyBox.drawTextBox();
      super.drawScreen(par1, par2, par3);
   }

   public static boolean isInteger(String s) {
      try {
         Integer.parseInt(s);
         return true;
      } catch (NumberFormatException var2) {
         return false;
      }
   }
}
