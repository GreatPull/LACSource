package net.minecraft.entity.player.Really.Client.module.modules.movement;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPostUpdate;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.module.modules.combat.Aura;
import net.minecraft.entity.player.Really.Client.utils.PlayerUtil;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlow extends Module {
   private Mode mode = new Mode("Mode", "Mode", NoSlow.NoslowMode.values(), NoSlow.NoslowMode.Vanilla);
   public TimerUtil timer;

   public NoSlow() {
      super("NoSlowDown", new String[]{"noslow"}, ModuleType.Movement);
      this.setColor((new Color(216, 253, 100)).getRGB());
      this.addValues(new Value[]{this.mode});
   }

   public void onEnable() {
      this.timer.reset();
      super.onEnable();
   }

   @EventHandler
   private void onPre(EventPreUpdate e) {
      this.setSuffix(this.mode.getValue());
      if(this.mode.getValue() != NoSlow.NoslowMode.Vanilla) {
         if(this.mode.getValue() == NoSlow.NoslowMode.NCP) {
            Minecraft var10000 = mc;
            if(Minecraft.thePlayer.isBlocking() && PlayerUtil.isMoving() && isOnGround(0.42D)) {
               var10000 = mc;
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }
         }

         if(this.mode.getValue() == NoSlow.NoslowMode.NCP2) {
            Minecraft var3 = mc;
            if(Minecraft.thePlayer.isBlocking() && PlayerUtil.isMoving() && isOnGround(0.42D)) {
               var3 = mc;
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }
         }

         if(this.mode.getValue() == NoSlow.NoslowMode.Hypixel) {
            Minecraft var5 = mc;
            if(Minecraft.thePlayer.isBlocking() && PlayerUtil.isMoving() && isOnGround(0.42D)) {
               var5 = mc;
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }
         }

         if(this.mode.getValue() == NoSlow.NoslowMode.AAC) {
            Minecraft var7 = mc;
            if(Minecraft.thePlayer.onGround || isOnGround(0.5D)) {
               var7 = mc;
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }
         }

      }
   }

   @EventHandler
   private void onPost(EventPostUpdate e) {
      this.setSuffix(this.mode.getValue());
      this.mode.getValue();
      NoSlow.NoslowMode var10000 = NoSlow.NoslowMode.Vanilla;
      if(this.mode.getValue() == NoSlow.NoslowMode.NCP) {
         Minecraft var2 = mc;
         if(Minecraft.thePlayer.isBlocking() && PlayerUtil.isMoving() && isOnGround(0.42D) && Aura.curTarget == null) {
            var2 = mc;
            Minecraft var10003 = mc;
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(Minecraft.thePlayer.inventory.getCurrentItem()));
         }
      }

      if(this.mode.getValue() == NoSlow.NoslowMode.NCP2) {
         Minecraft var4 = mc;
         if(Minecraft.thePlayer.isBlocking() && PlayerUtil.isMoving() && isOnGround(0.42D)) {
            EntityLivingBase var5 = Aura.curTarget;
         }
      }

      if(this.mode.getValue() == NoSlow.NoslowMode.Hypixel) {
         Minecraft var6 = mc;
         if(Minecraft.thePlayer.isBlocking() && PlayerUtil.isMoving() && isOnGround(0.42D) && Aura.curTarget == null) {
            var6 = mc;
            NetHandlerPlayClient var8 = Minecraft.thePlayer.sendQueue;
            BlockPos var11 = PlayerUtil.getHypixelBlockpos(mc.getSession().getUsername());
            Minecraft var10005 = mc;
            var8.addToSendQueue(new C08PacketPlayerBlockPlacement(var11, 255, Minecraft.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
         }
      }

      if(this.mode.getValue() == NoSlow.NoslowMode.AAC) {
         Minecraft var9 = mc;
         if(Minecraft.thePlayer.isBlocking() && PlayerUtil.isMoving() && isOnGround(0.42D) && Aura.curTarget == null && this.timer.delay(65.0F)) {
            var9 = mc;
            Minecraft var12 = mc;
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(Minecraft.thePlayer.inventory.getCurrentItem()));
            this.timer.reset();
         }
      }

   }

   public static boolean isOnGround(double height) {
      Minecraft.getMinecraft();
      Minecraft.getMinecraft();
      Minecraft.getMinecraft();
      return !Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, Minecraft.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty();
   }

   static enum NoslowMode {
      NCP,
      NCP2,
      Vanilla,
      Hypixel,
      AAC;
   }
}
