package net.minecraft.entity.player.Really.Client.module.modules.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.EnumFacing;

public class TNTBlock extends Module {
   Runnable r2 = () -> {
      System.out.println("233333333");
   };
   int ticks = 10;
   private boolean hasBlocked;
   public static boolean Reach;

   public TNTBlock() {
      super("TNTBlock", new String[]{"TNTBlock"}, ModuleType.Combat);
   }

   public void onDisable() {
      super.onDisable();
   }

   public void onEnable() {
      super.isEnabled();
   }

   @EventHandler
   public void onCombat(EventPreUpdate e2) {
      boolean foundTnt = false;
      if(!Minecraft.thePlayer.isDead) {
         for(Entity e1 : Minecraft.theWorld.loadedEntityList) {
            if(e1 instanceof EntityTNTPrimed) {
               EntityTNTPrimed entityTNTPrimed = (EntityTNTPrimed)e1;
               if((double)Minecraft.thePlayer.getDistanceToEntity(e1) <= 4.0D) {
                  foundTnt = true;
                  if((double)entityTNTPrimed.fuse == (double)this.ticks && !entityTNTPrimed.isDead) {
                     this.blockItem();
                  }
               }
            }
         }

         if(!foundTnt && this.hasBlocked) {
            this.unblockItem();
         }
      }

   }

   private void unblockItem() {
      Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, Minecraft.thePlayer.getPosition(), EnumFacing.DOWN));
      Minecraft.playerController.onStoppedUsingItem(Minecraft.thePlayer);
      this.hasBlocked = false;
   }

   private void blockItem() {
      if(Minecraft.thePlayer.getCurrentEquippedItem() != null && Minecraft.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemSword) {
         Minecraft.playerController.sendUseItem(Minecraft.thePlayer, Minecraft.theWorld, Minecraft.thePlayer.inventory.getCurrentItem());
         Minecraft.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(Minecraft.thePlayer.getPosition(), 0, Minecraft.thePlayer.getCurrentEquippedItem(), 0.0F, 0.0F, 0.0F));
         this.hasBlocked = true;
      }

   }
}
