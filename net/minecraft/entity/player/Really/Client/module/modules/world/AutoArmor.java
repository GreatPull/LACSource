package net.minecraft.entity.player.Really.Client.module.modules.world;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventTick;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.management.ModuleManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.Timer;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C16PacketClientStatus;

public class AutoArmor extends Module {
   public static Numbers DELAY = new Numbers("DELAY", "DELAY", Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(10.0D), Double.valueOf(1.0D));
   public static Mode MODE = new Mode("MODE", "MODE", AutoArmor.EMode.values(), AutoArmor.EMode.Basic);
   private Timer timer = new Timer();

   public AutoArmor() {
      super("AutoArmor", new String[]{"AutoArmor"}, ModuleType.World);
      super.addValues(new Value[]{DELAY, MODE});
   }

   @EventHandler
   public void onEvent(EventTick event) {
      this.setSuffix(MODE.getValue());
      Client var10000 = Client.instance;
      Client.getModuleManager();
      if(!ModuleManager.getModuleByName("InvCleaner").isEnabled()) {
         long delay = ((Double)DELAY.getValue()).longValue() * 50L;
         if(MODE.getValue() != AutoArmor.EMode.OpenInv || mc.currentScreen instanceof GuiInventory) {
            if((mc.currentScreen == null || mc.currentScreen instanceof GuiInventory || mc.currentScreen instanceof GuiChat) && this.timer.check((float)delay)) {
               this.getBestArmor();
            }

         }
      }
   }

   public void getBestArmor() {
      for(int type = 1; type < 5; ++type) {
         Minecraft var10000 = mc;
         if(Minecraft.thePlayer.inventoryContainer.getSlot(4 + type).getHasStack()) {
            var10000 = mc;
            ItemStack is = Minecraft.thePlayer.inventoryContainer.getSlot(4 + type).getStack();
            if(isBestArmor(is, type)) {
               continue;
            }

            if(MODE.getValue() == AutoArmor.EMode.FakeInv) {
               C16PacketClientStatus p = new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT);
               var10000 = mc;
               Minecraft.thePlayer.sendQueue.addToSendQueue(p);
            }

            this.drop(4 + type);
         }

         for(int i = 9; i < 45; ++i) {
            var10000 = mc;
            if(Minecraft.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
               var10000 = mc;
               ItemStack is = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack();
               if(isBestArmor(is, type) && getProtection(is) > 0.0F) {
                  this.shiftClick(i);
                  Timer.reset();
                  if(((Double)DELAY.getValue()).longValue() > 0L) {
                     return;
                  }
               }
            }
         }
      }

   }

   public static boolean isBestArmor(ItemStack stack, int type) {
      float prot = getProtection(stack);
      String strType = "";
      if(type == 1) {
         strType = "helmet";
      } else if(type == 2) {
         strType = "chestplate";
      } else if(type == 3) {
         strType = "leggings";
      } else if(type == 4) {
         strType = "boots";
      }

      if(!stack.getUnlocalizedName().contains(strType)) {
         return false;
      } else {
         for(int i = 5; i < 45; ++i) {
            Minecraft var10000 = mc;
            if(Minecraft.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
               var10000 = mc;
               ItemStack is = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack();
               if(getProtection(is) > prot && is.getUnlocalizedName().contains(strType)) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   public void shiftClick(int slot) {
      Minecraft var10000 = mc;
      Minecraft var10001 = mc;
      Minecraft var10005 = mc;
      Minecraft.playerController.windowClick(Minecraft.thePlayer.inventoryContainer.windowId, slot, 0, 1, Minecraft.thePlayer);
   }

   public void drop(int slot) {
      Minecraft var10000 = mc;
      Minecraft var10001 = mc;
      Minecraft var10005 = mc;
      Minecraft.playerController.windowClick(Minecraft.thePlayer.inventoryContainer.windowId, slot, 1, 4, Minecraft.thePlayer);
   }

   public static float getProtection(ItemStack stack) {
      float prot = 0.0F;
      if(stack.getItem() instanceof ItemArmor) {
         ItemArmor armor = (ItemArmor)stack.getItem();
         prot = (float)((double)prot + (double)armor.damageReduceAmount + (double)((100 - armor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack)) * 0.0075D);
         prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack) / 100.0D);
         prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack) / 100.0D);
         prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack) / 100.0D);
         prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 50.0D);
         prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.featherFalling.effectId, stack) / 100.0D);
      }

      return prot;
   }

   public static enum EMode {
      Basic,
      OpenInv,
      FakeInv;
   }
}
