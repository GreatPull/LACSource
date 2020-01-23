package net.minecraft.entity.player.Really.Client.module.modules.world;

import java.awt.Color;
import net.minecraft.block.state.pattern.BlockHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.misc.EventCollideWithBlock;
import net.minecraft.entity.player.Really.Client.api.events.world.EventMove;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPostUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.math.RotationUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovementInput;

public class Phase extends Module {
   private Mode mode = new Mode("Mode", "mode", Phase.PhaseMode.values(), Phase.PhaseMode.NewNCP);

   public Phase() {
      super("Phase", new String[]{"noclip"}, ModuleType.World);
      this.setColor((new Color(255, 166, 25)).getRGB());
      this.addValues(new Value[]{this.mode});
   }

   @EventHandler
   private void onBlockCollision(EventCollideWithBlock e) {
      if(e.getBoundingBox() != null) {
         Minecraft var10001 = mc;
         if(e.getBoundingBox().maxY > Minecraft.thePlayer.boundingBox.minY) {
            Minecraft var10000 = mc;
            if(Minecraft.thePlayer.isSneaking() && this.mode.getValue() != Phase.PhaseMode.OldNCP) {
               e.setBoundingBox((AxisAlignedBB)null);
            }
         }
      }

      if(e.getBoundingBox() != null) {
         Minecraft var2 = mc;
         if(e.getBoundingBox().maxY > Minecraft.thePlayer.boundingBox.minY && this.mode.getValue() == Phase.PhaseMode.OldNCP) {
            e.setBoundingBox((AxisAlignedBB)null);
         }
      }

   }

   @EventHandler
   private void onMove(EventMove e) {
      if(BlockHelper.insideBlock()) {
         Minecraft var10000 = mc;
         if(Minecraft.thePlayer.isSneaking() && this.mode.getValue() == Phase.PhaseMode.SkipClip) {
            var10000 = mc;
            AxisAlignedBB var3 = Minecraft.thePlayer.boundingBox;
            Minecraft var10001 = mc;
            MovementInput var4 = Minecraft.thePlayer.movementInput;
            double var5 = (double)MovementInput.moveForward * 3.6D;
            Minecraft var10002 = mc;
            var5 = var5 * Math.cos(Math.toRadians((double)(Minecraft.thePlayer.rotationYaw + 90.0F)));
            var10002 = mc;
            MovementInput var9 = Minecraft.thePlayer.movementInput;
            double var10 = (double)MovementInput.moveStrafe * 3.6D;
            Minecraft var10003 = mc;
            var5 = var5 + var10 * Math.sin(Math.toRadians((double)(Minecraft.thePlayer.rotationYaw + 90.0F)));
            var10003 = mc;
            MovementInput var12 = Minecraft.thePlayer.movementInput;
            double var13 = (double)MovementInput.moveForward * 3.6D;
            Minecraft var10004 = mc;
            var13 = var13 * Math.sin(Math.toRadians((double)(Minecraft.thePlayer.rotationYaw + 90.0F)));
            var10004 = mc;
            MovementInput var16 = Minecraft.thePlayer.movementInput;
            double var17 = (double)MovementInput.moveStrafe * 3.6D;
            Minecraft var10005 = mc;
            var3.offsetAndUpdate(var5, 0.0D, var13 - var17 * Math.cos(Math.toRadians((double)(Minecraft.thePlayer.rotationYaw + 90.0F))));
         }
      }

   }

   @EventHandler
   private void onUpdate(EventPostUpdate e) {
      if(BlockHelper.insideBlock()) {
         if(this.mode.getValue() == Phase.PhaseMode.NewNCP) {
            Minecraft var10000 = mc;
            if(Minecraft.thePlayer.isSneaking()) {
               var10000 = mc;
               Minecraft.thePlayer.boundingBox.offsetAndUpdate(0.0524D * Math.cos(Math.toRadians((double)(RotationUtil.yaw() + 90.0F))), 0.0D, 0.0524D * Math.sin(Math.toRadians((double)(RotationUtil.yaw() + 90.0F))));
            }
         }

         if(this.mode.getValue() == Phase.PhaseMode.OldNCP) {
            Minecraft var7 = mc;
            if(Minecraft.thePlayer.isCollidedVertically) {
               var7 = mc;
               double x = (double)(-MathHelper.sin(Minecraft.thePlayer.getDirection())) * 0.2D;
               var7 = mc;
               double z = (double)MathHelper.cos(Minecraft.thePlayer.getDirection()) * 0.2D;
               var7 = mc;
               Minecraft var10003 = mc;
               double var16 = Minecraft.thePlayer.posX + x;
               Minecraft var10004 = mc;
               Minecraft var10005 = mc;
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(var16, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ + z, false));
               var7 = mc;
               Minecraft var17 = mc;
               double var18 = Minecraft.thePlayer.posX + x;
               var10005 = mc;
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(var18, Double.MIN_VALUE, Minecraft.thePlayer.posZ + z, true));
               var7 = mc;
               Minecraft var10001 = mc;
               double var15 = Minecraft.thePlayer.posX + x;
               Minecraft var10002 = mc;
               Minecraft var19 = mc;
               Minecraft.thePlayer.setPosition(var15, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ + z);
            }
         }

         Minecraft var13 = mc;
         if(Minecraft.thePlayer.onGround && this.mode.getValue() == Phase.PhaseMode.NewNCP) {
            var13 = mc;
            Minecraft.thePlayer.jump();
         }
      }

   }

   static enum PhaseMode {
      NewNCP,
      OldNCP,
      SkipClip;
   }
}
