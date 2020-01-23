package net.minecraft.entity.player.Really.Client.module.modules.combat;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPacketSend;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;

public class ArmorBreaker extends Module {
   public ArmorBreaker() {
      super("ArmorBreaker", new String[]{"ArmorBreaker"}, ModuleType.Combat);
   }

   @EventHandler
   public void onPacketSent(EventPacketSend packet) {
      Packet ThePacket = EventPacketSend.getPacket();
      if(ThePacket instanceof C02PacketUseEntity && ((C02PacketUseEntity)ThePacket).getAction() == C02PacketUseEntity.Action.ATTACK) {
         C02PacketUseEntity var10001 = (C02PacketUseEntity)ThePacket;
         Minecraft var10002 = mc;
         this.breaker(var10001.getEntityFromWorld(Minecraft.theWorld));
      }

   }

   public void breaker(Entity en) {
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer.onGround) {
         var10000 = mc;
         ItemStack current = Minecraft.thePlayer.getHeldItem();

         for(int i = 0; i < 46; ++i) {
            var10000 = mc;
            ItemStack toSwitch = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack();
            if(current != null && toSwitch != null && toSwitch.getItem() instanceof ItemSword) {
               var10000 = mc;
               Minecraft var10003 = mc;
               Minecraft var10005 = mc;
               Minecraft.playerController.windowClick(0, i, Minecraft.thePlayer.inventory.currentItem, 2, Minecraft.thePlayer);
            }
         }

      }
   }
}
