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

public class NoSlowDown extends Module {
   private Mode mode = new Mode("Mode", "Mode", NoSlowDown.JMode.values(), NoSlowDown.JMode.Vanilla);
   public TimerUtil timer = new TimerUtil();

   public NoSlowDown() {
      super("NoSlowDown", new String[]{"waterwalk", "float"}, ModuleType.Movement);
      this.setColor((new Color(188, 233, 248)).getRGB());
      this.addValues(new Value[]{this.mode});
   }

   public void onEnable() {
      super.onEnable();
   }

   @EventHandler
   public void onPost(EventPostUpdate event) {
      this.mode.getValue();
      NoSlowDown.JMode var10000 = NoSlowDown.JMode.Vanilla;
      if(this.mode.getValue() == NoSlowDown.JMode.NCP) {
         Minecraft var2 = mc;
         if(Minecraft.thePlayer.isBlocking() && PlayerUtil.isMoving() && isOnGround(0.42D) && Aura.curTarget == null) {
            var2 = mc;
            Minecraft var10003 = mc;
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(Minecraft.thePlayer.inventory.getCurrentItem()));
         }
      }

      if(this.mode.getValue() == NoSlowDown.JMode.NCP2) {
         Minecraft var4 = mc;
         if(Minecraft.thePlayer.isBlocking() && PlayerUtil.isMoving() && isOnGround(0.42D)) {
            EntityLivingBase var5 = Aura.curTarget;
         }
      }

      if(this.mode.getValue() == NoSlowDown.JMode.Hypixel) {
         Minecraft var6 = mc;
         if(Minecraft.thePlayer.isBlocking() && PlayerUtil.isMoving() && isOnGround(0.42D) && Aura.curTarget == null) {
            var6 = mc;
            NetHandlerPlayClient var8 = Minecraft.thePlayer.sendQueue;
            BlockPos var11 = PlayerUtil.getHypixelBlockpos(mc.getSession().getUsername());
            Minecraft var10005 = mc;
            var8.addToSendQueue(new C08PacketPlayerBlockPlacement(var11, 255, Minecraft.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
         }
      }

      if(this.mode.getValue() == NoSlowDown.JMode.AAC) {
         Minecraft var9 = mc;
         if(Minecraft.thePlayer.isBlocking() && PlayerUtil.isMoving() && isOnGround(0.42D) && Aura.curTarget == null && this.timer.delay(65.0F)) {
            var9 = mc;
            Minecraft var12 = mc;
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(Minecraft.thePlayer.inventory.getCurrentItem()));
            this.timer.reset();
         }
      }

   }

   @EventHandler
   public void onPre(EventPreUpdate e) {
      this.setSuffix(this.mode.getValue());
      if(this.mode.getValue() != NoSlowDown.JMode.Vanilla) {
         if(this.mode.getValue() == NoSlowDown.JMode.NCP) {
            Minecraft var10000 = mc;
            if(Minecraft.thePlayer.isBlocking() && PlayerUtil.isMoving() && isOnGround(0.42D)) {
               var10000 = mc;
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }
         }

         if(this.mode.getValue() == NoSlowDown.JMode.NCP2) {
            Minecraft var3 = mc;
            if(Minecraft.thePlayer.isBlocking() && PlayerUtil.isMoving() && isOnGround(0.42D)) {
               var3 = mc;
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }
         }

         if(this.mode.getValue() == NoSlowDown.JMode.Hypixel) {

            Minecraft var5 = mc;
            if(Minecraft.thePlayer.isBlocking() && PlayerUtil.isMoving() && isOnGround(0.42D)) {
               var5 = mc;
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }
         }

         if(this.mode.getValue() == NoSlowDown.JMode.AAC) {

            Minecraft var7 = mc;
            if(Minecraft.thePlayer.onGround || isOnGround(0.5D)) {
               var7 = mc;
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            }
         }

      }
   }

   public static boolean isOnGround(double height) {
      Minecraft.getMinecraft();
      Minecraft.getMinecraft();
      Minecraft.getMinecraft();
      return !Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, Minecraft.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty();
   }

   static enum JMode {
      NCP,
      NCP2,
      Vanilla,
      Hypixel,
      AAC;
   }
}
