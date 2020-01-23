package net.minecraft.entity.player.Really.Client.module.modules.player;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSword;
import net.minecraft.util.Timer;

public class FastUse extends Module {
   public FastUse() {
      super("FastUse", new String[]{"fasteat", "fuse"}, ModuleType.Player);
   }

   public void onEnable() {
      super.onEnable();
      Timer var10000 = mc.timer;
      Timer.timerSpeed = 1.0F;
   }

   public void onDisable() {
      super.onDisable();
      Timer var10000 = mc.timer;
      Timer.timerSpeed = 1.0F;
   }

   private boolean canConsume() {
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer.getCurrentEquippedItem() != null) {
         var10000 = mc;
         if(Minecraft.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemPotion) {
            return true;
         }
      }

      var10000 = mc;
      if(!(Minecraft.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemFood)) {
         return false;
      } else {
         return true;
      }
   }

   @EventHandler
   private void onUpdate(EventPreUpdate e) {
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer.getItemInUseDuration() >= 10) {
         Minecraft var10001 = mc;
         if(this.canUseItem(Minecraft.thePlayer.getItemInUse().getItem())) {
            Timer var4 = mc.timer;
            Timer.timerSpeed = 1.3555F;
            return;
         }
      }

      Timer var2 = mc.timer;
      if(Timer.timerSpeed == 1.3555F) {
         var2 = mc.timer;
         Timer.timerSpeed = 1.0F;
      }

   }

   private boolean canUseItem(Item item) {
      boolean result = !(item instanceof ItemSword) && !(item instanceof ItemBow);
      return result;
   }
}
