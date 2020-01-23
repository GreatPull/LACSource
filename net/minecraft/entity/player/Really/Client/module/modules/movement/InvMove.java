package net.minecraft.entity.player.Really.Client.module.modules.movement;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPacketSend;
import net.minecraft.entity.player.Really.Client.api.events.world.EventTick;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import org.lwjgl.input.Keyboard;

public class InvMove extends Module {
   public static Option Carry = new Option("Carry", "Carry", Boolean.valueOf(false));
   boolean inInventory = false;

   public InvMove() {
      super("InvMove", new String[]{"crits", "crit"}, ModuleType.Movement);
      this.setColor((new Color(235, 194, 138)).getRGB());
      this.addValues(new Value[]{Carry});
   }

   @EventHandler
   public void onPacket(EventPacketSend event) {
      Packet packet = EventPacketSend.getPacket();
      if(((Boolean)Carry.getValue()).booleanValue() && packet instanceof C0DPacketCloseWindow) {
         event.setCancelled(true);
      }

   }

   @EventHandler
   public void onTick(EventTick event) {
      if(!(mc.currentScreen instanceof GuiChat)) {
         if(mc.currentScreen != null) {
            KeyBinding[] var10000 = new KeyBinding[5];
            Minecraft var10003 = mc;
            var10000[0] = Minecraft.gameSettings.keyBindForward;
            var10003 = mc;
            var10000[1] = Minecraft.gameSettings.keyBindBack;
            var10003 = mc;
            var10000[2] = Minecraft.gameSettings.keyBindLeft;
            var10003 = mc;
            var10000[3] = Minecraft.gameSettings.keyBindRight;
            var10003 = mc;
            var10000[4] = Minecraft.gameSettings.keyBindJump;
            KeyBinding[] moveKeys = var10000;

            for(KeyBinding bind : moveKeys) {
               KeyBinding.setKeyBindState(bind.getKeyCode(), Keyboard.isKeyDown(bind.getKeyCode()));
            }

            if(!this.inInventory) {
               this.inInventory = !this.inInventory;
            }

            if(!(mc.currentScreen instanceof GuiInventory)) {
               if(Keyboard.isKeyDown(200)) {
                  Minecraft var7 = mc;
                  --Minecraft.thePlayer.rotationPitch;
               }

               if(Keyboard.isKeyDown(208)) {
                  Minecraft var8 = mc;
                  ++Minecraft.thePlayer.rotationPitch;
               }

               if(Keyboard.isKeyDown(203)) {
                  Minecraft var9 = mc;
                  Minecraft.thePlayer.rotationYaw -= 3.0F;
               }

               if(Keyboard.isKeyDown(205)) {
                  Minecraft var10 = mc;
                  Minecraft.thePlayer.rotationYaw += 3.0F;
               }
            }
         } else if(this.inInventory) {
            this.inInventory = !this.inInventory;
         }

      }
   }
}
