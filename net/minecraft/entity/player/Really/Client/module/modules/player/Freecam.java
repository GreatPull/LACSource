package net.minecraft.entity.player.Really.Client.module.modules.player;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.misc.EventCollideWithBlock;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPacketRecieve;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

public class Freecam extends Module {
   private EntityOtherPlayerMP copy;
   private double x;
   private double y;
   private double z;

   public Freecam() {
      super("Freecam", new String[]{"outofbody"}, ModuleType.Player);
      this.setColor((new Color(221, 214, 51)).getRGB());
   }

   public void onEnable() {
      Minecraft var10003 = mc;
      Minecraft var10004 = mc;
      this.copy = new EntityOtherPlayerMP(Minecraft.theWorld, Minecraft.thePlayer.getGameProfile());
      Minecraft var10001 = mc;
      this.copy.clonePlayer(Minecraft.thePlayer, true);
      var10001 = mc;
      Minecraft var10002 = mc;
      var10003 = mc;
      var10004 = mc;
      Minecraft var10005 = mc;
      this.copy.setLocationAndAngles(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, Minecraft.thePlayer.rotationYaw, Minecraft.thePlayer.rotationPitch);
      var10001 = mc;
      this.copy.rotationYawHead = Minecraft.thePlayer.rotationYawHead;
      this.copy.setEntityId(-1337);
      var10001 = mc;
      this.copy.setSneaking(Minecraft.thePlayer.isSneaking());
      Minecraft var10000 = mc;
      Minecraft.theWorld.addEntityToWorld(this.copy.getEntityId(), this.copy);
      var10001 = mc;
      this.x = Minecraft.thePlayer.posX;
      var10001 = mc;
      this.y = Minecraft.thePlayer.posY;
      var10001 = mc;
      this.z = Minecraft.thePlayer.posZ;
   }

   @EventHandler
   private void onPreMotion(EventPreUpdate e) {
      Minecraft var10000 = mc;
      Minecraft.thePlayer.capabilities.isFlying = true;
      var10000 = mc;
      Minecraft.thePlayer.noClip = true;
      var10000 = mc;
      Minecraft.thePlayer.capabilities.setFlySpeed(0.1F);
      e.setCancelled(true);
   }

   @EventHandler
   private void onPacketSend(EventPacketRecieve e) {
      if(e.getPacket() instanceof C03PacketPlayer) {
         e.setCancelled(true);
      }

   }

   @EventHandler
   private void onBB(EventCollideWithBlock e) {
      e.setBoundingBox((AxisAlignedBB)null);
   }

   public void onDisable() {
      Minecraft var10000 = mc;
      Minecraft.thePlayer.setSpeed(0.0D);
      var10000 = mc;
      Minecraft.thePlayer.setLocationAndAngles(this.copy.posX, this.copy.posY, this.copy.posZ, this.copy.rotationYaw, this.copy.rotationPitch);
      var10000 = mc;
      Minecraft.thePlayer.rotationYawHead = this.copy.rotationYawHead;
      var10000 = mc;
      Minecraft.theWorld.removeEntityFromWorld(this.copy.getEntityId());
      var10000 = mc;
      Minecraft.thePlayer.setSneaking(this.copy.isSneaking());
      this.copy = null;
      var10000 = mc;
      Minecraft.renderGlobal.loadRenderers();
      var10000 = mc;
      Minecraft.thePlayer.setPosition(this.x, this.y, this.z);
      var10000 = mc;
      NetHandlerPlayClient var8 = Minecraft.getNetHandler();
      Minecraft var10003 = mc;
      Minecraft var10004 = mc;
      double var12 = Minecraft.thePlayer.posY + 0.01D;
      Minecraft var10005 = mc;
      Minecraft var10006 = mc;
      var8.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, var12, Minecraft.thePlayer.posZ, Minecraft.thePlayer.onGround));
      Minecraft var9 = mc;
      Minecraft.thePlayer.capabilities.isFlying = false;
      var9 = mc;
      Minecraft.thePlayer.noClip = false;
      var9 = mc;
      Minecraft.theWorld.removeEntityFromWorld(-1);
   }
}
