package net.minecraft.entity.player.Really.Client.module.modules.combat;

import java.awt.Color;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.InventoryUtils;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class AutoSword extends Module {
   private ItemStack bestSword;
   private ItemStack prevBestSword;
   private boolean shouldSwitch = false;
   public TimerUtil timer = new TimerUtil();

   public AutoSword() {
      super("AutoSword", new String[]{"autosword"}, ModuleType.Combat);
      this.setColor((new Color(208, 30, 142)).getRGB());
   }

   @EventHandler
   private void onUpdate(EventPreUpdate event) {
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer.ticksExisted % 7 == 0) {
         var10000 = mc;
         if(Minecraft.thePlayer.capabilities.isCreativeMode) {
            return;
         }

         var10000 = mc;
         if(Minecraft.thePlayer.openContainer != null) {
            var10000 = mc;
            if(Minecraft.thePlayer.openContainer.windowId != 0) {
               return;
            }
         }

         this.bestSword = this.getBestItem(ItemSword.class, Comparator.comparingDouble(this::getSwordDamage));
         if(this.bestSword == null) {
            return;
         }

         boolean isInHBSlot = InventoryUtils.hotbarHas(this.bestSword.getItem(), 0);
         if(isInHBSlot) {
            if(InventoryUtils.getItemBySlotID(0) != null) {
               if(InventoryUtils.getItemBySlotID(0).getItem() instanceof ItemSword && this.getSwordDamage(InventoryUtils.getItemBySlotID(0)) > this.getSwordDamage(this.bestSword)) {
                  isInHBSlot = true;
               }
            } else {
               isInHBSlot = false;
            }
         }

         if(this.prevBestSword != null && this.prevBestSword.equals(this.bestSword) && isInHBSlot) {
            this.shouldSwitch = false;
         } else {
            this.shouldSwitch = true;
            this.prevBestSword = this.bestSword;
         }

         if(this.shouldSwitch && this.timer.hasReached(1.0D)) {
            int slotHB = InventoryUtils.getBestSwordSlotID(this.bestSword, this.getSwordDamage(this.bestSword));
            switch(slotHB) {
            case 0:
               slotHB = 36;
               break;
            case 1:
               slotHB = 37;
               break;
            case 2:
               slotHB = 38;
               break;
            case 3:
               slotHB = 39;
               break;
            case 4:
               slotHB = 40;
               break;
            case 5:
               slotHB = 41;
               break;
            case 6:
               slotHB = 42;
               break;
            case 7:
               slotHB = 43;
               break;
            case 8:
               slotHB = 44;
            }

            var10000 = mc;
            Minecraft var10005 = mc;
            Minecraft.playerController.windowClick(0, slotHB, 0, 2, Minecraft.thePlayer);
            this.timer.reset();
         }
      }

   }

   private ItemStack getBestItem(Class itemType, Comparator comparator) {
      Minecraft var10000 = mc;
      Optional<ItemStack> bestItem = Minecraft.thePlayer.inventoryContainer.inventorySlots.stream().map(Slot::getStack).filter(Objects::nonNull).filter((itemStack) -> {
         return itemStack.getItem().getClass().equals(itemType);
      }).max(comparator);
      return (ItemStack)bestItem.orElse((ItemStack)null);
   }

   private double getSwordDamage(ItemStack itemStack) {
      double damage = 0.0D;
      Optional attributeModifier = itemStack.getAttributeModifiers().values().stream().findFirst();
      if(attributeModifier.isPresent()) {
         damage = ((AttributeModifier)attributeModifier.get()).getAmount();
      }

      return damage + (double)EnchantmentHelper.func_152377_a(itemStack, EnumCreatureAttribute.UNDEFINED);
   }
}
