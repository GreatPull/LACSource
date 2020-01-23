package net.minecraft.entity.player.Really.Client.module.modules.world;

import com.mojang.authlib.GameProfile;
import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPacketSend;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;

public class Blink extends Module {
   private EntityOtherPlayerMP blinkEntity;
   private List<Packet> packetList = new ArrayList();

   public Blink() {
      super("Blink", new String[]{"blonk"}, ModuleType.Player);
   }

   public void onEnable() {
      this.setColor((new Color(200, 100, 200)).getRGB());
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer != null) {
         Minecraft var10003 = mc;
         this.blinkEntity = new EntityOtherPlayerMP(Minecraft.theWorld, new GameProfile(new UUID(69L, 96L), "Blink"));
         Minecraft var10001 = mc;
         this.blinkEntity.inventory = Minecraft.thePlayer.inventory;
         var10001 = mc;
         this.blinkEntity.inventoryContainer = Minecraft.thePlayer.inventoryContainer;
         var10001 = mc;
         Minecraft var10002 = mc;
         var10003 = mc;
         Minecraft var10004 = mc;
         Minecraft var10005 = mc;
         this.blinkEntity.setPositionAndRotation(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, Minecraft.thePlayer.rotationYaw, Minecraft.thePlayer.rotationPitch);
         var10001 = mc;
         this.blinkEntity.rotationYawHead = Minecraft.thePlayer.rotationYawHead;
         var10000 = mc;
         Minecraft.theWorld.addEntityToWorld(this.blinkEntity.getEntityId(), this.blinkEntity);
      }
   }

   @EventHandler
   private void onPacketSend(EventPacketSend event) {
      if(EventPacketSend.getPacket() instanceof C0BPacketEntityAction || EventPacketSend.getPacket() instanceof C03PacketPlayer || EventPacketSend.getPacket() instanceof C02PacketUseEntity || EventPacketSend.getPacket() instanceof C0APacketAnimation || EventPacketSend.getPacket() instanceof C08PacketPlayerBlockPlacement) {
         this.packetList.add(EventPacketSend.getPacket());
         event.setCancelled(true);
      }

   }

   public void onDisable() {
      for(Packet packet : this.packetList) {
         Minecraft var10000 = mc;
         Minecraft.getNetHandler().addToSendQueue(packet);
      }

      this.packetList.clear();
      Minecraft var3 = mc;
      Minecraft.theWorld.removeEntityFromWorld(this.blinkEntity.getEntityId());
   }
}
