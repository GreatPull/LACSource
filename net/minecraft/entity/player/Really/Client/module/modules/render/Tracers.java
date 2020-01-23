package net.minecraft.entity.player.Really.Client.module.modules.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender3D;
import net.minecraft.entity.player.Really.Client.management.FriendManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.math.MathUtil;
import net.minecraft.entity.player.Really.Client.utils.render.RenderUtil;
import org.lwjgl.opengl.GL11;

public class Tracers extends Module {
   public Tracers() {
      super("Tracers", new String[]{"lines", "tracer"}, ModuleType.Render);
      this.setColor((new Color(60, 136, 166)).getRGB());
   }

   @EventHandler
   private void on3DRender(EventRender3D e) {
      Minecraft var10000 = mc;

      for(Object o : Minecraft.theWorld.loadedEntityList) {
         Entity entity = (Entity)o;
         if(entity.isEntityAlive() && entity instanceof EntityPlayer) {
            Minecraft var10001 = mc;
            if(entity != Minecraft.thePlayer) {
               double posX = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)e.getPartialTicks() - RenderManager.renderPosX;
               double posY = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)e.getPartialTicks() - RenderManager.renderPosY;
               double posZ = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)e.getPartialTicks() - RenderManager.renderPosZ;
               var10000 = mc;
               boolean old = Minecraft.gameSettings.viewBobbing;
               RenderUtil.startDrawing();
               var10000 = mc;
               Minecraft.gameSettings.viewBobbing = false;
               var10000 = mc;
               Minecraft.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 2);
               var10000 = mc;
               Minecraft.gameSettings.viewBobbing = old;
               var10001 = mc;
               double var21 = Minecraft.thePlayer.getDistanceSqToEntity(entity) * 255.0D;
               Minecraft var10002 = mc;
               float color = (float)Math.round(255.0D - var21 / MathUtil.square((double)Minecraft.gameSettings.renderDistanceChunks * 2.5D)) / 255.0F;
               double[] arrd;
               if(FriendManager.isFriend(entity.getName())) {
                  double[] arrd2 = new double[]{0.0D, 1.0D, 0.0D};
                  arrd = arrd2;
                  arrd2[2] = 1.0D;
               } else {
                  double[] arrd3 = new double[]{(double)color, (double)(1.0F - color), 0.0D};
                  arrd = arrd3;
                  arrd3[2] = 0.0D;
               }

               this.drawLine(entity, arrd, posX, posY, posZ);
               RenderUtil.stopDrawing();
            }
         }
      }

   }

   private void drawLine(Entity entity, double[] color, double x, double y, double z) {
      Minecraft var10000 = mc;
      float distance = Minecraft.thePlayer.getDistanceToEntity(entity);
      float xD = distance / 48.0F;
      if(xD >= 1.0F) {
         xD = 1.0F;
      }

      boolean entityesp = false;
      GL11.glEnable(2848);
      if(color.length >= 4) {
         if(color[3] <= 0.1D) {
            return;
         }

         GL11.glColor4d(color[0], color[1], color[2], color[3]);
      } else {
         GL11.glColor3d(color[0], color[1], color[2]);
      }

      GL11.glLineWidth(1.0F);
      GL11.glBegin(1);
      Minecraft var10001 = mc;
      GL11.glVertex3d(0.0D, (double)Minecraft.thePlayer.getEyeHeight(), 0.0D);
      GL11.glVertex3d(x, y, z);
      GL11.glEnd();
      GL11.glDisable(2848);
   }
}
