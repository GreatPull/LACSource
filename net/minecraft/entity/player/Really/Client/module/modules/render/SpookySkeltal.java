package net.minecraft.entity.player.Really.Client.module.modules.render;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender3D;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.render.RenderUtil;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class SpookySkeltal extends Module {
	private static final Map<EntityPlayer, float[][]> modelRotations = new HashMap<EntityPlayer, float[][]>();

   public SpookySkeltal() {
      super("SpookySkeltal", new String[0], ModuleType.Render);
   }

   @EventHandler
   public void onRender(EventRender3D event) {
      RenderUtil.setupRender(true);
      GL11.glDisable(2848);
      GlStateManager.disableLighting();
      modelRotations.keySet().removeIf((player) -> {
         Minecraft var10000 = mc;
         return !Minecraft.theWorld.playerEntities.contains(player);
      });
      Minecraft var10000 = mc;
      Minecraft.theWorld.playerEntities.forEach((player) -> {
         Minecraft var10001 = mc;
         if(player != Minecraft.thePlayer) {
            if(!player.isInvisible()) {
            	float[][] modelRotations = SpookySkeltal.modelRotations.get(player);
               if(modelRotations != null) {
                  GL11.glPushMatrix();
                  GL11.glLineWidth(1.0F);
                  GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                  Vec3 interp = RenderUtil.interpolateRender(player);
                  double x = interp.getX() - RenderManager.renderPosX;
                  double y = interp.getY() - RenderManager.renderPosY;
                  double z = interp.getZ() - RenderManager.renderPosZ;
                  GL11.glTranslated(x, y, z);
                  float bodyYawOffset = player.prevRenderYawOffset + (player.renderYawOffset - player.prevRenderYawOffset) * mc.timer.renderPartialTicks;
                  GL11.glRotatef(-bodyYawOffset, 0.0F, 1.0F, 0.0F);
                  GL11.glTranslated(0.0D, 0.0D, player.isSneaking()?-0.235D:0.0D);
                  float legHeight = player.isSneaking()?0.6F:0.75F;
                  GL11.glPushMatrix();
                  GL11.glTranslated(-0.125D, (double)legHeight, 0.0D);
                  if(modelRotations[3][0] != 0.0F) {
                     GL11.glRotatef(modelRotations[3][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
                  }

                  if(modelRotations[3][1] != 0.0F) {
                     GL11.glRotatef(modelRotations[3][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
                  }

                  if(modelRotations[3][2] != 0.0F) {
                     GL11.glRotatef(modelRotations[3][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
                  }

                  GL11.glBegin(3);
                  GL11.glVertex3d(0.0D, 0.0D, 0.0D);
                  GL11.glVertex3d(0.0D, (double)(-legHeight), 0.0D);
                  GL11.glEnd();
                  GL11.glPopMatrix();
                  GL11.glPushMatrix();
                  GL11.glTranslated(0.125D, (double)legHeight, 0.0D);
                  if(modelRotations[4][0] != 0.0F) {
                     GL11.glRotatef(modelRotations[4][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
                  }

                  if(modelRotations[4][1] != 0.0F) {
                     GL11.glRotatef(modelRotations[4][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
                  }

                  if(modelRotations[4][2] != 0.0F) {
                     GL11.glRotatef(modelRotations[4][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
                  }

                  GL11.glBegin(3);
                  GL11.glVertex3d(0.0D, 0.0D, 0.0D);
                  GL11.glVertex3d(0.0D, (double)(-legHeight), 0.0D);
                  GL11.glEnd();
                  GL11.glPopMatrix();
                  GL11.glTranslated(0.0D, 0.0D, player.isSneaking()?0.25D:0.0D);
                  GL11.glPushMatrix();
                  GL11.glTranslated(0.0D, player.isSneaking()?-0.05D:0.0D, player.isSneaking()?-0.01725D:0.0D);
                  GL11.glPushMatrix();
                  GL11.glTranslated(-0.375D, (double)legHeight + 0.55D, 0.0D);
                  if(modelRotations[1][0] != 0.0F) {
                     GL11.glRotatef(modelRotations[1][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
                  }

                  if(modelRotations[1][1] != 0.0F) {
                     GL11.glRotatef(modelRotations[1][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
                  }

                  if(modelRotations[1][2] != 0.0F) {
                     GL11.glRotatef(-modelRotations[1][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
                  }

                  GL11.glBegin(3);
                  GL11.glVertex3d(0.0D, 0.0D, 0.0D);
                  GL11.glVertex3d(0.0D, -0.5D, 0.0D);
                  GL11.glEnd();
                  GL11.glPopMatrix();
                  GL11.glPushMatrix();
                  GL11.glTranslated(0.375D, (double)legHeight + 0.55D, 0.0D);
                  if(modelRotations[2][0] != 0.0F) {
                     GL11.glRotatef(modelRotations[2][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
                  }

                  if(modelRotations[2][1] != 0.0F) {
                     GL11.glRotatef(modelRotations[2][1] * 57.295776F, 0.0F, 1.0F, 0.0F);
                  }

                  if(modelRotations[2][2] != 0.0F) {
                     GL11.glRotatef(-modelRotations[2][2] * 57.295776F, 0.0F, 0.0F, 1.0F);
                  }

                  GL11.glBegin(3);
                  GL11.glVertex3d(0.0D, 0.0D, 0.0D);
                  GL11.glVertex3d(0.0D, -0.5D, 0.0D);
                  GL11.glEnd();
                  GL11.glPopMatrix();
                  GL11.glRotatef(bodyYawOffset - player.rotationYawHead, 0.0F, 1.0F, 0.0F);
                  GL11.glPushMatrix();
                  GL11.glTranslated(0.0D, (double)legHeight + 0.55D, 0.0D);
                  if(modelRotations[0][0] != 0.0F) {
                     GL11.glRotatef(modelRotations[0][0] * 57.295776F, 1.0F, 0.0F, 0.0F);
                  }

                  GL11.glBegin(3);
                  GL11.glVertex3d(0.0D, 0.0D, 0.0D);
                  GL11.glVertex3d(0.0D, 0.3D, 0.0D);
                  GL11.glEnd();
                  GL11.glPopMatrix();
                  GL11.glPopMatrix();
                  GL11.glRotatef(player.isSneaking()?25.0F:0.0F, 1.0F, 0.0F, 0.0F);
                  GL11.glTranslated(0.0D, player.isSneaking()?-0.16175D:0.0D, player.isSneaking()?-0.48025D:0.0D);
                  GL11.glPushMatrix();
                  GL11.glTranslated(0.0D, (double)legHeight, 0.0D);
                  GL11.glBegin(3);
                  GL11.glVertex3d(-0.125D, 0.0D, 0.0D);
                  GL11.glVertex3d(0.125D, 0.0D, 0.0D);
                  GL11.glEnd();
                  GL11.glPopMatrix();
                  GL11.glPushMatrix();
                  GL11.glTranslated(0.0D, (double)legHeight, 0.0D);
                  GL11.glBegin(3);
                  GL11.glVertex3d(0.0D, 0.0D, 0.0D);
                  GL11.glVertex3d(0.0D, 0.55D, 0.0D);
                  GL11.glEnd();
                  GL11.glPopMatrix();
                  GL11.glPushMatrix();
                  GL11.glTranslated(0.0D, (double)legHeight + 0.55D, 0.0D);
                  GL11.glBegin(3);
                  GL11.glVertex3d(-0.375D, 0.0D, 0.0D);
                  GL11.glVertex3d(0.375D, 0.0D, 0.0D);
                  GL11.glEnd();
                  GL11.glPopMatrix();
                  GL11.glPopMatrix();
               }
            }
         }
      });
      RenderUtil.setupRender(false);
   }

   public static void updateModel(EntityPlayer player, ModelPlayer model) {
      modelRotations.put(player, new float[][]{{model.bipedHead.rotateAngleX, model.bipedHead.rotateAngleY, model.bipedHead.rotateAngleZ}, {model.bipedRightArm.rotateAngleX, model.bipedRightArm.rotateAngleY, model.bipedRightArm.rotateAngleZ}, {model.bipedLeftArm.rotateAngleX, model.bipedLeftArm.rotateAngleY, model.bipedLeftArm.rotateAngleZ}, {model.bipedRightLeg.rotateAngleX, model.bipedRightLeg.rotateAngleY, model.bipedRightLeg.rotateAngleZ}, {model.bipedLeftLeg.rotateAngleX, model.bipedLeftLeg.rotateAngleY, model.bipedLeftLeg.rotateAngleZ}});
   }
}
