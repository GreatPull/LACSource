package net.minecraft.entity.player.Really.Client.module.modules.combat;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPostUpdate;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemSoup;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class AutoHeal extends Module {
   private Numbers health = new Numbers("Health", "health", Double.valueOf(3.0D), Double.valueOf(0.0D), Double.valueOf(10.0D), Double.valueOf(0.5D));
   private Numbers delay = new Numbers("Delay", "delay", Double.valueOf(400.0D), Double.valueOf(0.0D), Double.valueOf(1000.0D), Double.valueOf(10.0D));
   private Option jump = new Option("Jump", "jump", Boolean.valueOf(true));
   private Mode mode = new Mode("Mode", "mode", AutoHeal.HealMode.values(), AutoHeal.HealMode.Potion);
   static boolean currentlyPotting = false;
   private boolean isUsing;
   private int slot;
   private TimerUtil timer = new TimerUtil();

   public AutoHeal() {
      super("AutoHeal", new String[]{"autopot", "autop", "autosoup"}, ModuleType.Combat);
      this.setColor((new Color(76, 249, 247)).getRGB());
      this.addValues(new Value[]{this.health, this.delay, this.jump, this.mode});
   }

   @EventHandler
   private void onUpdate(EventPreUpdate e) {
      if(this.timer.hasReached(((Double)this.delay.getValue()).doubleValue())) {
         Minecraft var10000 = mc;
         if((double)Minecraft.thePlayer.getHealth() <= ((Double)this.health.getValue()).doubleValue() * 2.0D) {
            boolean var3 = false;
            label100: {
                label1002: {
                this.slot = this.mode.getValue() == AutoHeal.HealMode.Potion?this.getPotionSlot():(this.mode.getValue() == AutoHeal.HealMode.Soup?this.getSoupSlot():this.getPotionSlot());
                if(this.slot != -1) {
                   if(!((Boolean)this.jump.getValue()).booleanValue()) {
                      break label100;
                   }

                   Minecraft var10001 = mc;
                   if(Minecraft.thePlayer.onGround) {
                      break label100;
                   }
                }

                var3 = false;
                break label100;
             }
            }

            boolean bl = this.isUsing = var3;
            if(this.isUsing) {
               this.timer.reset();
               if(this.mode.getValue() == AutoHeal.HealMode.Potion) {
                  currentlyPotting = true;
                  e.setPitch((float)(((Boolean)this.jump.getValue()).booleanValue()?-90:90));
                  if(this.timer.hasReached(1.0D)) {
                     currentlyPotting = false;
                     this.timer.reset();
                  }
               }
            }
         }
      }

   }

   @EventHandler
   private void onUpdatePost(EventPostUpdate e) {
      if(this.isUsing) {
         Minecraft var10000 = mc;
         int current = Minecraft.thePlayer.inventory.currentItem;
         var10000 = mc;
         int next = Minecraft.thePlayer.nextSlot();
         var10000 = mc;
         Minecraft.thePlayer.moveToHotbar(this.slot, next);
         var10000 = mc;
         Minecraft.thePlayer.inventory.currentItem = next;
         var10000 = mc;
         Minecraft var10003 = mc;
         Minecraft.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(Minecraft.thePlayer.inventory.currentItem));
         var10000 = mc;
         Minecraft var10001 = mc;
         Minecraft var10002 = mc;
         var10003 = mc;
         Minecraft.playerController.sendUseItem(Minecraft.thePlayer, Minecraft.theWorld, Minecraft.thePlayer.getHeldItem());
         var10000 = mc;
         Minecraft.thePlayer.inventory.currentItem = current;
         var10000 = mc;
         var10003 = mc;
         Minecraft.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(Minecraft.thePlayer.inventory.currentItem));
         this.isUsing = false;
         var10000 = mc;
         if(Minecraft.thePlayer.onGround && ((Boolean)this.jump.getValue()).booleanValue() && this.mode.getValue() == AutoHeal.HealMode.Potion) {
            var10000 = mc;
            Minecraft.thePlayer.setSpeed(0.0D);
            var10000 = mc;
            Minecraft.thePlayer.motionY = 0.42D;
         }
      }

   }

   private int getPotionSlot() {
      int slot = -1;
      Minecraft var10000 = mc;

      for(Slot s : Minecraft.thePlayer.inventoryContainer.inventorySlots) {
         ItemStack is;
         if(s.getHasStack() && (is = s.getStack()).getItem() instanceof ItemPotion) {
            ItemPotion ip = (ItemPotion)is.getItem();
            if(ItemPotion.isSplash(is.getMetadata())) {
               boolean hasHealing = false;

               for(PotionEffect pe : ip.getEffects(is)) {
                  if(pe.getPotionID() == Potion.heal.id) {
                     hasHealing = true;
                     break;
                  }
               }

               if(hasHealing) {
                  slot = s.slotNumber;
                  break;
               }
            }
         }
      }

      return slot;
   }

   private int getSoupSlot() {
      int slot = -1;
      Minecraft var10000 = mc;

      for(Slot s : Minecraft.thePlayer.inventoryContainer.inventorySlots) {
         if(s.getHasStack() && s.getStack().getItem() instanceof ItemSoup) {
            slot = s.slotNumber;
            break;
         }
      }

      return slot;
   }

   private int getPotionCount() {
      int count = 0;
      Minecraft var10000 = mc;

      for(Slot s : Minecraft.thePlayer.inventoryContainer.inventorySlots) {
         ItemStack is;
         if(s.getHasStack() && (is = s.getStack()).getItem() instanceof ItemPotion) {
            ItemPotion ip = (ItemPotion)is.getItem();
            if(ItemPotion.isSplash(is.getMetadata())) {
               boolean hasHealing = false;

               for(PotionEffect pe : ip.getEffects(is)) {
                  if(pe.getPotionID() == Potion.heal.id) {
                     hasHealing = true;
                     break;
                  }
               }

               if(hasHealing) {
                  ++count;
               }
            }
         }
      }

      return count;
   }

   private int getSoupCount() {
      int count = 0;
      Minecraft var10000 = mc;

      for(Slot s : Minecraft.thePlayer.inventoryContainer.inventorySlots) {
         if(s.getHasStack() && s.getStack().getItem() instanceof ItemSoup) {
            ++count;
         }
      }

      return count;
   }

   static enum HealMode {
      Potion,
      Soup;
   }
}
