package net.minecraft.entity.player.Really.Client.module.modules.world;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventTick;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.item.ItemStack;

public class ChestStealer extends Module {
   private Numbers delay = new Numbers("Delay", "delay", Double.valueOf(50.0D), Double.valueOf(0.0D), Double.valueOf(1000.0D), Double.valueOf(10.0D));
   private TimerUtil timer = new TimerUtil();

   public ChestStealer() {
      super("ChestStealer", new String[]{"cheststeal", "chests", "stealer"}, ModuleType.World);
      this.addValues(new Value[]{this.delay});
      this.setColor((new Color(218, 97, 127)).getRGB());
   }

   @EventHandler
   private void onUpdate(EventTick event) {
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer.openContainer != null) {
         var10000 = mc;
         if(Minecraft.thePlayer.openContainer instanceof ContainerChest) {
            var10000 = mc;
            ContainerChest container = (ContainerChest)Minecraft.thePlayer.openContainer;

            for(int i = 0; i < container.getLowerChestInventory().getSizeInventory(); ++i) {
               if(container.getLowerChestInventory().getStackInSlot(i) != null && this.timer.hasReached(((Double)this.delay.getValue()).doubleValue())) {
                  var10000 = mc;
                  Minecraft var10005 = mc;
                  Minecraft.playerController.windowClick(container.windowId, i, 0, 1, Minecraft.thePlayer);
                  this.timer.reset();
               }
            }

            if(this.isEmpty()) {
               var10000 = mc;
               Minecraft.thePlayer.closeScreen();
            }
         }
      }

   }

   private boolean isEmpty() {
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer.openContainer != null) {
         var10000 = mc;
         if(Minecraft.thePlayer.openContainer instanceof ContainerChest) {
            var10000 = mc;
            ContainerChest container = (ContainerChest)Minecraft.thePlayer.openContainer;

            for(int i = 0; i < container.getLowerChestInventory().getSizeInventory(); ++i) {
               ItemStack itemStack = container.getLowerChestInventory().getStackInSlot(i);
               if(itemStack != null && itemStack.getItem() != null) {
                  return false;
               }
            }
         }
      }

      return true;
   }
}
