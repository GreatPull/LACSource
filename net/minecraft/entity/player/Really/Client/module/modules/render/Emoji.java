package net.minecraft.entity.player.Really.Client.module.modules.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender3D;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.RenderUtils;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class Emoji extends Module {
   public Mode mode = new Mode("Mode", "Mode", Emoji.EmojiMode.values(), Emoji.EmojiMode.Yaoer);

   public Emoji() {
      super("Emoji", new String[]{"PlayerFace"}, ModuleType.Render);
      this.addValues(new Value[]{this.mode});
   }

   private boolean isValid(EntityLivingBase entity) {
      return entity instanceof EntityPlayer && entity.getHealth() >= 0.0F && entity != Minecraft.thePlayer;
   }

   @EventHandler
   public void onpre(EventPreUpdate event) {
      this.setSuffix(this.mode.getValue());
   }

   @EventHandler
   public void onRender(EventRender3D event) {
      Minecraft var10000 = mc;

      for(EntityPlayer entity : Minecraft.theWorld.playerEntities) {
         if(this.isValid(entity)) {
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glDisable(2929);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.enableBlend();
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            float partialTicks = mc.timer.renderPartialTicks;
            mc.getRenderManager();
            double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks - RenderManager.renderPosX;
            mc.getRenderManager();
            double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks - RenderManager.renderPosY;
            mc.getRenderManager();
            double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks - RenderManager.renderPosZ;
            float DISTANCE = Minecraft.thePlayer.getDistanceToEntity(entity);
            float DISTANCE_SCALE = Math.min(DISTANCE * 0.15F, 0.15F);
            float SCALE = 0.035F;
            float xMid = (float)x;
            float yMid = (float)y + entity.height + 0.5F - (entity.isChild()?entity.height / 2.0F:0.0F);
            float zMid = (float)z;
            GlStateManager.translate((float)x, (float)y + entity.height + 0.5F - (entity.isChild()?entity.height / 2.0F:0.0F), (float)z);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            mc.getRenderManager();
            GlStateManager.rotate(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            float var32;
            GL11.glScalef(-SCALE, -SCALE, -(var32 = SCALE / 2.0F));
            Tessellator tesselator = Tessellator.getInstance();
            WorldRenderer worldRenderer = tesselator.getWorldRenderer();
            new Color(0, 0, 0);
            double thickness = (double)(1.5F + DISTANCE * 0.01F);
            double xLeft = -20.0D;
            double xRight = 20.0D;
            double yUp = 27.0D;
            double yDown = 130.0D;
            double size = 10.0D;
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GlStateManager.disableBlend();
            GL11.glDisable(3042);
            if(this.mode.getValue() == Emoji.EmojiMode.Yaoer) {
               RenderUtils.drawImage(new ResourceLocation("LAC/yaoer.png"), (int)xLeft + 9, (int)yUp - 20, 20, 25);
            } else if(this.mode.getValue() == Emoji.EmojiMode.Ganga) {
               RenderUtils.drawImage(new ResourceLocation("LAC/ganga.png"), (int)xLeft + 7, (int)yUp - 28, 27, 32);
            } else if(this.mode.getValue() == Emoji.EmojiMode.Taijun) {
               RenderUtils.drawImage(new ResourceLocation("LAC/taijun.png"), (int)xLeft + 7, (int)yUp - 28, 27, 32);
            } else if(this.mode.getValue() == Emoji.EmojiMode.China) {
               RenderUtils.drawImage(new ResourceLocation("LAC/ChinaBBBBBBIG.png"), (int)xLeft + 7, (int)yUp - 28, 27, 32);
            } else if(this.mode.getValue() == Emoji.EmojiMode.YaTian) {
               RenderUtils.drawImage(new ResourceLocation("LAC/YaTian.png"), (int)xLeft + 7, (int)yUp - 28, 27, 32);
            } else if(this.mode.getValue() == Emoji.EmojiMode.YaTian2) {
               RenderUtils.drawImage(new ResourceLocation("LAC/YaTian2.png"), (int)xLeft + 7, (int)yUp - 28, 27, 32);
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glNormal3f(1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
         }
      }

   }

   static enum EmojiMode {
      Yaoer,
      Taijun,
      Ganga,
      China,
      YaTian,
      YaTian2;
   }
}
