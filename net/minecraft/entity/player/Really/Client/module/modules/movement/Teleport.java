package net.minecraft.entity.player.Really.Client.module.modules.movement;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import org.lwjgl.input.Mouse;

public class Teleport extends Module {
   public Teleport() {
      super("Teleport", new String[]{"teleport"}, ModuleType.Movement);
      this.setColor((new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255))).getRGB());
   }

   @EventHandler
   private void onUpdate(EventPreUpdate event) {
      MovingObjectPosition ray = this.rayTrace(500.0D);
      if(ray != null) {
         if(Mouse.isButtonDown(1)) {
            double x_new = (double)ray.getBlockPos().getX() + 0.5D;
            double y_new = (double)(ray.getBlockPos().getY() + 1);
            double z_new = (double)ray.getBlockPos().getZ() + 0.5D;
            Minecraft var10000 = mc;
            double distance = Minecraft.thePlayer.getDistance(x_new, y_new, z_new);

            for(double d = 0.0D; d < distance; d += 2.0D) {
               Minecraft var10001 = mc;
               double var14 = Minecraft.thePlayer.posX;
               Minecraft var10003 = mc;
               double var10002 = x_new - (double)Minecraft.thePlayer.getHorizontalFacing().getFrontOffsetX();
               var10003 = mc;
               var14 = var14 + (var10002 - Minecraft.thePlayer.posX) * d / distance;
               Minecraft var16 = mc;
               Minecraft var10004 = mc;
               double var17 = Minecraft.thePlayer.posY + (y_new - Minecraft.thePlayer.posY) * d / distance;
               var10003 = mc;
               double var20 = Minecraft.thePlayer.posZ;
               Minecraft var10005 = mc;
               double var21 = z_new - (double)Minecraft.thePlayer.getHorizontalFacing().getFrontOffsetZ();
               var10005 = mc;
               this.setPos(var14, var17, var20 + (var21 - Minecraft.thePlayer.posZ) * d / distance);
            }

            this.setPos(x_new, y_new, z_new);
            var10000 = mc;
            Minecraft.renderGlobal.loadRenderers();
         }

      }
   }

   public MovingObjectPosition rayTrace(double blockReachDistance) {
      Minecraft var10000 = mc;
      Vec3 vec3 = Minecraft.thePlayer.getPositionEyes(1.0F);
      var10000 = mc;
      Vec3 vec4 = Minecraft.thePlayer.getLookVec();
      Vec3 vec5 = vec3.addVector(vec4.xCoord * blockReachDistance, vec4.yCoord * blockReachDistance, vec4.zCoord * blockReachDistance);
      var10000 = mc;
      Minecraft var10003 = mc;
      return Minecraft.theWorld.rayTraceBlocks(vec3, vec5, !Minecraft.thePlayer.isInWater(), false, false);
   }

   public void setPos(double x, double y, double z) {
      Minecraft var10000 = mc;
      Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y, z, true));
      var10000 = mc;
      Minecraft.thePlayer.setPosition(x, y, z);
   }
}
