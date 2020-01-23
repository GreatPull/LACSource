package net.minecraft.entity.player.Really.Client.module.modules.combat;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.module.modules.combat.Timer;
import net.minecraft.entity.player.Really.Client.utils.math.RotationUtil;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.PotionEffect;

public class AutoPot extends Module {
   private Option REGEN = new Option("REGEN", "REGEN", Boolean.valueOf(true));
   private Option SPEED = new Option("SPEED", "SPEED", Boolean.valueOf(true));
   private Option PREDICT = new Option("PREDICT", "PREDICT", Boolean.valueOf(true));
   private Numbers HEALTH = new Numbers("HEALTH", "HEALTH", Double.valueOf(6.0D), Double.valueOf(0.5D), Double.valueOf(10.0D), Double.valueOf(0.5D));
   public static boolean potting;
   Timer timer = new Timer();

   public AutoPot() {
      super("AutoPot", new String[]{"AutoPot"}, ModuleType.Combat);
      super.addValues(new Value[]{this.REGEN, this.SPEED, this.PREDICT, this.HEALTH});
      this.setColor((new Color(208, 30, 142)).getRGB());
   }

   public void onEnable() {
      super.onEnable();
   }

   public static boolean isPotting() {
      return potting;
   }

   @EventHandler
   private void onUpdate(EventPreUpdate em) {
      boolean speed = ((Boolean)this.SPEED.getValue()).booleanValue();
      boolean regen = ((Boolean)this.REGEN.getValue()).booleanValue();
      if(this.timer.check(200.0F) && potting) {
         potting = false;
      }

      int spoofSlot = this.getBestSpoofSlot();
      int[] pots = new int[]{6, -1, -1};
      if(regen) {
         pots[1] = 10;
      }

      if(speed) {
         pots[2] = 1;
      }

      for(int i = 0; i < pots.length; ++i) {
         if(pots[i] != -1) {
            if(pots[i] != 6 && pots[i] != 10) {
               if(this.timer.check(1000.0F)) {
                  Minecraft var8 = mc;
                  if(!Minecraft.thePlayer.isPotionActive(pots[i])) {
                     this.getBestPot(spoofSlot, pots[i]);
                  }
               }
            } else if(this.timer.check(900.0F)) {
               Minecraft var10000 = mc;
               if(!Minecraft.thePlayer.isPotionActive(pots[i])) {
                  var10000 = mc;
                  if((double)Minecraft.thePlayer.getHealth() < ((Double)this.HEALTH.getValue()).doubleValue() * 2.0D) {
                     this.getBestPot(spoofSlot, pots[i]);
                  }
               }
            }
         }
      }

   }

   public void swap(int slot1, int hotbarSlot) {
      Minecraft var10000 = mc;
      Minecraft var10001 = mc;
      Minecraft var10005 = mc;
      Minecraft.playerController.windowClick(Minecraft.thePlayer.inventoryContainer.windowId, slot1, hotbarSlot, 2, Minecraft.thePlayer);
   }

   float[] getRotations() {
      Minecraft var10000 = mc;
      Minecraft var10001 = mc;
      double movedPosX = Minecraft.thePlayer.posX + Minecraft.thePlayer.motionX * 26.0D;
      var10000 = mc;
      double movedPosY = Minecraft.thePlayer.boundingBox.minY - 3.6D;
      var10000 = mc;
      var10001 = mc;
      double movedPosZ = Minecraft.thePlayer.posZ + Minecraft.thePlayer.motionZ * 26.0D;
      if(((Boolean)this.PREDICT.getValue()).booleanValue()) {
         return RotationUtil.getRotationFromPosition(movedPosX, movedPosZ, movedPosY);
      } else {
         float[] var9 = new float[2];
         Minecraft var10003 = mc;
         var9[0] = Minecraft.thePlayer.rotationYaw;
         var9[1] = 90.0F;
         return var9;
      }
   }

   int getBestSpoofSlot() {
      int spoofSlot = 5;

      for(int i = 36; i < 45; ++i) {
         Minecraft var10000 = mc;
         if(!Minecraft.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            spoofSlot = i - 36;
            break;
         }

         var10000 = mc;
         if(Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack().getItem() instanceof ItemPotion) {
            spoofSlot = i - 36;
            break;
         }
      }

      return spoofSlot;
   }

   void getBestPot(int hotbarSlot, int potID) {
      for(int i = 9; i < 45; ++i) {
         Minecraft var10000 = mc;
         if(Minecraft.thePlayer.inventoryContainer.getSlot(i).getHasStack() && (mc.currentScreen == null || mc.currentScreen instanceof GuiInventory)) {
            var10000 = mc;
            ItemStack is = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack();
            if(is.getItem() instanceof ItemPotion) {
               ItemPotion pot = (ItemPotion)is.getItem();
               if(pot.getEffects(is).isEmpty()) {
                  return;
               }

               PotionEffect effect = (PotionEffect)pot.getEffects(is).get(0);
               int potionID = effect.getPotionID();
               if(potionID == potID && ItemPotion.isSplash(is.getItemDamage()) && this.isBestPot(pot, is)) {
                  if(36 + hotbarSlot != i) {
                     this.swap(i, hotbarSlot);
                  }

                  this.timer.reset();
                  boolean canpot = true;
                  var10000 = mc;
                  int oldSlot = Minecraft.thePlayer.inventory.currentItem;
                  var10000 = mc;
                  Minecraft.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(hotbarSlot));
                  var10000 = mc;
                  NetHandlerPlayClient var14 = Minecraft.thePlayer.sendQueue;
                  float var10003 = this.getRotations()[0];
                  float var10004 = this.getRotations()[1];
                  Minecraft var10005 = mc;
                  var14.addToSendQueue(new C03PacketPlayer.C05PacketPlayerLook(var10003, var10004, Minecraft.thePlayer.onGround));
                  Minecraft var15 = mc;
                  Minecraft var17 = mc;
                  Minecraft.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(Minecraft.thePlayer.inventory.getCurrentItem()));
                  var15 = mc;
                  Minecraft.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(oldSlot));
                  potting = true;
                  break;
               }
            }
         }
      }

   }

   boolean isBestPot(ItemPotion potion, ItemStack stack) {
      if(potion.getEffects(stack) != null && potion.getEffects(stack).size() == 1) {
         PotionEffect effect = (PotionEffect)potion.getEffects(stack).get(0);
         int potionID = effect.getPotionID();
         int amplifier = effect.getAmplifier();
         int duration = effect.getDuration();

         for(int i = 9; i < 45; ++i) {
            Minecraft var10000 = mc;
            if(Minecraft.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
               var10000 = mc;
               ItemStack is = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack();
               if(is.getItem() instanceof ItemPotion) {
                  ItemPotion pot = (ItemPotion)is.getItem();
                  if(pot.getEffects(is) != null) {
                     for(Object o : pot.getEffects(is)) {
                        PotionEffect effects = (PotionEffect)o;
                        int id = effects.getPotionID();
                        int ampl = effects.getAmplifier();
                        int dur = effects.getDuration();
                        if(id == potionID && ItemPotion.isSplash(is.getItemDamage())) {
                           if(ampl > amplifier) {
                              return false;
                           }

                           if(ampl == amplifier && dur > duration) {
                              return false;
                           }
                        }
                     }
                  }
               }
            }
         }

         return true;
      } else {
         return false;
      }
   }
}
