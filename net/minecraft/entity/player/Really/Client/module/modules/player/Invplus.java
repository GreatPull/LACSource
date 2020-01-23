package net.minecraft.entity.player.Really.Client.module.modules.player;

import java.awt.Color;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.misc.EventInventory;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender2D;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPacketSend;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.math.RotationUtil;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import org.lwjgl.input.Keyboard;

public class Invplus extends Module {
   public Option sw = new Option("ScreenWalk", "screenwalk", Boolean.valueOf(true));
   private Option xc = new Option("MoreInventory", "MoreInventory", Boolean.valueOf(false));

   public Invplus() {
      super("Inventory+", new String[]{"inventorywalk", "invwalk", "inventorymove", "inv+"}, ModuleType.Player);
      this.setColor((new Color(174, 174, 227)).getRGB());
      this.addValues(new Value[]{this.sw, this.xc});
   }

   @EventHandler
   public void onEvent(EventPacketSend event) {
      if(EventPacketSend.getPacket() instanceof C0DPacketCloseWindow && ((Boolean)this.xc.getValue()).booleanValue()) {
         event.setCancelled(true);
      }

   }

   @EventHandler
   public void onInv(EventInventory event) {
      if(((Boolean)this.xc.getValue()).booleanValue()) {
         event.setCancelled(true);
      }

   }

   @EventHandler
   private void onRender(EventRender2D e) {
      if(mc.currentScreen != null && !(mc.currentScreen instanceof GuiChat) && ((Boolean)this.sw.getValue()).booleanValue()) {
         if(Keyboard.isKeyDown(200)) {
            RotationUtil.pitch(RotationUtil.pitch() - 2.0F);
         }

         if(Keyboard.isKeyDown(208)) {
            RotationUtil.pitch(RotationUtil.pitch() + 2.0F);
         }

         if(Keyboard.isKeyDown(203)) {
            RotationUtil.yaw(RotationUtil.yaw() - 2.0F);
         }

         if(Keyboard.isKeyDown(205)) {
            RotationUtil.yaw(RotationUtil.yaw() + 2.0F);
         }
      }

   }
}
