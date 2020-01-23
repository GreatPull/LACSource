package net.minecraft.entity.player.Really.Client.module.modules.combat;

import com.google.common.eventbus.Subscribe;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.module.modules.combat.UpdateEvent;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import org.lwjgl.input.Mouse;

public class AutoHead extends Module {
   private boolean eatingApple;
   private int switched = -1;
   public static boolean doingStuff = false;
   private final TimerUtil timer = new TimerUtil();
   private final Option eatHeads = new Option("Eatheads", "eatheads", Boolean.valueOf(true));
   private final Option eatApples = new Option("Eatapples", "eatapples", Boolean.valueOf(true));
   private final Numbers health = new Numbers("Health", "health", Integer.valueOf(10), Integer.valueOf(1), Integer.valueOf(20), Integer.valueOf(1));
   private final Numbers delay = new Numbers("Delay", "delay", Integer.valueOf(750), Integer.valueOf(100), Integer.valueOf(2000), Integer.valueOf(25));

   public AutoHead() {
      super("AutoHead", new String[]{"AH", "EH", "eathead"}, ModuleType.Combat);
      this.addValues(new Value[]{this.health, this.delay, this.eatApples, this.eatHeads});
   }

   public void onEnable() {
      doingStuff = false;
      this.eatingApple = false;
      this.switched = -1;
      this.timer.reset();
      super.onEnable();
   }

   public void onDisable() {
      doingStuff = false;
      if(this.eatingApple) {
         this.repairItemPress();
         this.repairItemSwitch();
      }

      super.onDisable();
   }

   private void repairItemPress() {
      Minecraft var10000 = mc;
      if(Minecraft.gameSettings != null) {
         var10000 = mc;
         KeyBinding keyBindUseItem = Minecraft.gameSettings.keyBindUseItem;
         if(keyBindUseItem != null) {
            keyBindUseItem.pressed = false;
         }
      }

   }

   @Subscribe
   public void onUpdate(UpdateEvent event) {
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer != null) {
         var10000 = mc;
         InventoryPlayer inventory = Minecraft.thePlayer.inventory;
         if(inventory != null) {
            doingStuff = false;
            if(!Mouse.isButtonDown(0) && !Mouse.isButtonDown(1)) {
               var10000 = mc;
               KeyBinding useItem = Minecraft.gameSettings.keyBindUseItem;
               if(!this.timer.hasReached(((Double)this.delay.getValue()).doubleValue())) {
                  this.eatingApple = false;
                  this.repairItemPress();
                  this.repairItemSwitch();
               } else {
                  var10000 = mc;
                  if(!Minecraft.thePlayer.capabilities.isCreativeMode) {
                     var10000 = mc;
                     if(!Minecraft.thePlayer.isPotionActive(Potion.regeneration)) {
                        var10000 = mc;
                        if((double)Minecraft.thePlayer.getHealth() < ((Double)this.health.getValue()).doubleValue()) {
                           for(int i = 0; i < 2; ++i) {
                              boolean doEatHeads = i != 0;
                              if(doEatHeads) {
                                 if(!((Boolean)this.eatHeads.getValue()).booleanValue()) {
                                    continue;
                                 }
                              } else if(!((Boolean)this.eatApples.getValue()).booleanValue()) {
                                 this.eatingApple = false;
                                 this.repairItemPress();
                                 this.repairItemSwitch();
                                 continue;
                              }

                              int slot;
                              if(doEatHeads) {
                                 slot = this.getItemFromHotbar(397);
                              } else {
                                 slot = this.getItemFromHotbar(322);
                              }

                              if(slot != -1) {
                                 int tempSlot = inventory.currentItem;
                                 doingStuff = true;
                                 if(doEatHeads) {
                                    var10000 = mc;
                                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(slot));
                                    var10000 = mc;
                                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(inventory.getCurrentItem()));
                                    var10000 = mc;
                                    Minecraft.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(tempSlot));
                                    this.timer.reset();
                                 } else {
                                    inventory.currentItem = slot;
                                    useItem.pressed = true;
                                    if(!this.eatingApple) {
                                       this.eatingApple = true;
                                       this.switched = tempSlot;
                                    }
                                 }
                              }
                           }

                           return;
                        }
                     }
                  }

                  this.timer.reset();
                  if(this.eatingApple) {
                     this.eatingApple = false;
                     this.repairItemPress();
                     this.repairItemSwitch();
                  }

               }
            }
         }
      }
   }

   private void repairItemSwitch() {
      Minecraft var10000 = mc;
      EntityPlayerSP p = Minecraft.thePlayer;
      if(p != null) {
         InventoryPlayer inventory = p.inventory;
         if(inventory != null) {
            int switched = this.switched;
            if(switched != -1) {
               inventory.currentItem = switched;
               switched = -1;
               this.switched = switched;
            }
         }
      }
   }

   private int getItemFromHotbar(int id) {
      for(int i = 0; i < 9; ++i) {
         Minecraft var10000 = mc;
         if(Minecraft.thePlayer.inventory.mainInventory[i] != null) {
            var10000 = mc;
            ItemStack is = Minecraft.thePlayer.inventory.mainInventory[i];
            Item item = is.getItem();
            if(Item.getIdFromItem(item) == id) {
               return i;
            }
         }
      }

      return -1;
   }
}
