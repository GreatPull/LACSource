package net.minecraft.entity.player.Really.Client.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender3D;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.management.ModuleManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.module.modules.combat.Killaura;
import net.minecraft.entity.player.Really.Client.module.modules.player.Teams;
import net.minecraft.entity.player.Really.Client.utils.math.Vec3f;
import net.minecraft.entity.player.Really.Client.utils.render.Colors;
import net.minecraft.entity.player.Really.Client.utils.render.RenderUtil;
import org.lwjgl.opengl.GL11;

public class ESP extends Module {
   private ArrayList points = new ArrayList();
   float h;
   public int ncolor;
   public Mode mode = new Mode("Mode", "mode", ESP.ESPMode.values(), ESP.ESPMode.Other2D);
   public static Numbers r = new Numbers("Red", "Red", Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
   public static Numbers g = new Numbers("Green", "Green", Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
   public static Numbers b = new Numbers("Blue", "Blue", Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
   public static Numbers a = new Numbers("Alpha", "Alpha", Double.valueOf(100.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));

   public ESP() {
      super("ESP", new String[]{"outline", "wallhack"}, ModuleType.Render);
      this.addValues(new Value[]{this.mode, r, g, b, a});

      for(int i = 0; i < 8; ++i) {
         this.points.add(new Vec3f());
      }

      this.setColor((new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255))).getRGB());
   }

   @EventHandler
   private void onUpdate(EventPreUpdate e) {
      this.setSuffix(this.mode.getValue());
   }

   private void drawHBox() {
      Minecraft var10000 = mc;

      for(Object o : Minecraft.theWorld.loadedEntityList) {
         if(o instanceof EntityPlayer) {
            Minecraft var10001 = mc;
            if(o != Minecraft.thePlayer) {
               EntityPlayer ent = (EntityPlayer)o;
               if(ent.hurtTime > 0) {
                  new Color(-1618884);
               } else {
                  new Color(255, 255, 255);
               }

               mc.getRenderManager();
               double x = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)mc.timer.renderPartialTicks - RenderManager.renderPosX;
               mc.getRenderManager();
               double y = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * (double)mc.timer.renderPartialTicks - RenderManager.renderPosY;
               mc.getRenderManager();
               double z = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)mc.timer.renderPartialTicks - RenderManager.renderPosZ;
               if(ent instanceof EntityPlayer) {
                  Minecraft var100011 = mc;
                  if(ent != Minecraft.thePlayer) {
                     double width = ent.getEntityBoundingBox().maxX - ent.getEntityBoundingBox().minX;
                     double height = ent.getEntityBoundingBox().maxY - ent.getEntityBoundingBox().minY + 0.25D;
                     float red = ent.hurtTime > 0?1.0F:1.0F;
                     float green = ent.hurtTime > 0?0.2F:1.0F;
                     float blue = ent.hurtTime > 0?0.0F:1.0F;
                     float alpha = 0.2F;
                     float lineRed = ent.hurtTime > 0?1.0F:1.0F;
                     float lineGreen = ent.hurtTime > 0?0.2F:1.0F;
                     float lineBlue = ent.hurtTime > 0?0.0F:1.0F;
                     float lineAlpha = 0.2F;
                     float lineWdith = 1.0F;
                     RenderUtil.drawEntityESP(x, y, z, width, height, red, green, blue, 0.2F, lineRed, lineGreen, lineBlue, 0.2F, 1.0F);
                  }
               } else {
                  Minecraft var37 = mc;
                  if(ent != Minecraft.thePlayer) {
                     double width = ent.getEntityBoundingBox().maxX - ent.getEntityBoundingBox().minX + 0.1D;
                     double height = ent.getEntityBoundingBox().maxY - ent.getEntityBoundingBox().minY + 0.25D;
                     float red = ent.hurtTime > 0?1.0F:1.0F;
                     float green = ent.hurtTime > 0?0.2F:1.0F;
                     float blue = ent.hurtTime > 0?0.0F:1.0F;
                     float alpha = 0.2F;
                     float lineRed = ent.hurtTime > 0?1.0F:1.0F;
                     float lineGreen = ent.hurtTime > 0?0.2F:1.0F;
                     float lineBlue = ent.hurtTime > 0?0.0F:1.0F;
                     float lineAlpha = 0.2F;
                     float lineWdith = 1.0F;
                     RenderUtil.drawEntityESP(x, y, z, width, height, red, green, blue, 0.2F, lineRed, lineGreen, lineBlue, 0.2F, 1.0F);
                  }
               }
            }
         }
      }

   }

   @EventHandler
   public void onasd(EventPreUpdate e) {
      if(this.h > 255.0F) {
         this.h = 0.0F;
      }

      this.h = (float)((double)this.h + 0.1D);
   }

   @EventHandler
   public void onRender(EventRender3D event) {
      this.ncolor = (new Color((float)((Double)r.getValue()).intValue() / 255.0F, (float)((Double)g.getValue()).intValue() / 255.0F, (float)((Double)b.getValue()).intValue() / 255.0F, (float)((Double)a.getValue()).intValue() / 255.0F)).getRGB();
      if(this.mode.getValue() == ESP.ESPMode.Box) {
         this.doBoxESP(event);
      }

      if(this.mode.getValue() == ESP.ESPMode.Other2D) {
         this.doOther2DESP();
      }

      if(this.mode.getValue() == ESP.ESPMode.Flat) {
         this.doOtherH2DESP();
      }

      if(this.mode.getValue() == ESP.ESPMode.HBox) {
         this.drawHBox();
      }

      if(this.mode.getValue() == ESP.ESPMode.H2D) {
         this.doH2DESP();
      }

      if(this.mode.getValue() == ESP.ESPMode.New2D) {
         this.doCornerESP();
      }

   }

   private void doCornerESP() {
      Minecraft var10000 = mc;

      for(EntityPlayer entity : Minecraft.theWorld.playerEntities) {
         Minecraft var10001 = mc;
         if(entity != Minecraft.thePlayer) {
            if(!isValid(entity)) {
               return;
            }

            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glDisable(2929);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.enableBlend();
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            float partialTicks = mc.timer.renderPartialTicks;
            double var29 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)partialTicks;
            mc.getRenderManager();
            double x = var29 - RenderManager.renderPosX;
            var29 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)partialTicks;
            mc.getRenderManager();
            double y = var29 - RenderManager.renderPosY;
            var29 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)partialTicks;
            mc.getRenderManager();
            double z = var29 - RenderManager.renderPosZ;
            var10000 = mc;
            float DISTANCE = Minecraft.thePlayer.getDistanceToEntity(entity);
            float DISTANCE_SCALE = Math.min(DISTANCE * 0.15F, 2.5F);
            float SCALE = 0.035F;
            SCALE = SCALE / 2.0F;
            GlStateManager.translate((float)x, (float)y + entity.height + 0.5F - (entity.isChild()?entity.height / 2.0F:0.0F), (float)z);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            mc.getRenderManager();
            GlStateManager.rotate(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(-SCALE, -SCALE, -SCALE);
            Tessellator tesselator = Tessellator.getInstance();
            WorldRenderer worldRenderer = tesselator.getWorldRenderer();
            Color color = new Color((float)((Double)r.getValue()).intValue() / 255.0F, (float)((Double)g.getValue()).intValue() / 255.0F, (float)((Double)b.getValue()).intValue() / 255.0F, (float)((Double)a.getValue()).intValue() / 255.0F);
            if(entity.hurtTime > 0) {
               color = new Color(Colors.BLUE.c);
            } else if(entity == Killaura.target && ModuleManager.getModuleByName("KillAura").isEnabled()) {
               color = new Color(Colors.RED.c);
            }

            Color gray = new Color(0, 0, 0);
            double thickness = (double)(2.0F + DISTANCE * 0.08F);
            double xLeft = -30.0D;
            double xRight = 30.0D;
            double yUp = 20.0D;
            double yDown = 130.0D;
            double size = 10.0D;
            this.drawVerticalLine(xLeft + size / 2.0D - 1.0D, yUp + 1.0D, size / 2.0D, thickness, gray);
            this.drawHorizontalLine(xLeft + 1.0D, yUp + size, size, thickness, gray);
            this.drawVerticalLine(xLeft + size / 2.0D - 1.0D, yUp, size / 2.0D, thickness, color);
            this.drawHorizontalLine(xLeft, yUp + size, size, thickness, color);
            this.drawVerticalLine(xRight - size / 2.0D + 1.0D, yUp + 1.0D, size / 2.0D, thickness, gray);
            this.drawHorizontalLine(xRight - 1.0D, yUp + size, size, thickness, gray);
            this.drawVerticalLine(xRight - size / 2.0D + 1.0D, yUp, size / 2.0D, thickness, color);
            this.drawHorizontalLine(xRight, yUp + size, size, thickness, color);
            this.drawVerticalLine(xLeft + size / 2.0D - 1.0D, yDown - 1.0D, size / 2.0D, thickness, gray);
            this.drawHorizontalLine(xLeft + 1.0D, yDown - size, size, thickness, gray);
            this.drawVerticalLine(xLeft + size / 2.0D - 1.0D, yDown, size / 2.0D, thickness, color);
            this.drawHorizontalLine(xLeft, yDown - size, size, thickness, color);
            this.drawVerticalLine(xRight - size / 2.0D + 1.0D, yDown - 1.0D, size / 2.0D, thickness, gray);
            this.drawHorizontalLine(xRight - 1.0D, yDown - size, size, thickness, gray);
            this.drawVerticalLine(xRight - size / 2.0D + 1.0D, yDown, size / 2.0D, thickness, color);
            this.drawHorizontalLine(xRight, yDown - size, size, thickness, color);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GlStateManager.disableBlend();
            GL11.glDisable(3042);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glNormal3f(1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
         }
      }

   }

   private void doBoxESP(EventRender3D event) {
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(3042);
      GL11.glEnable(2848);
      GL11.glLineWidth(2.0F);
      GL11.glDisable(3553);
      GL11.glDisable(2929);
      GL11.glDepthMask(false);
      Minecraft var10000 = mc;

      for(Object o : Minecraft.theWorld.loadedEntityList) {
         if(o instanceof EntityPlayer) {
            Minecraft var10001 = mc;
            if(o != Minecraft.thePlayer) {
               EntityPlayer ent = (EntityPlayer)o;
               if(Teams.isOnSameTeam(ent)) {
                  RenderUtil.entityESPBox(ent, new Color(0, 255, 0), event);
               } else if(ent.hurtTime > 0) {
                  RenderUtil.entityESPBox(ent, new Color(255, 0, 0), event);
               } else if(!ent.isInvisible()) {
                  RenderUtil.entityESPBox(ent, new Color((float)((Double)r.getValue()).intValue() / 255.0F, (float)((Double)g.getValue()).intValue() / 255.0F, (float)((Double)b.getValue()).intValue() / 255.0F), event);
               }
            }
         }
      }

      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
   }

   public static boolean isValid(EntityLivingBase entity) {
      if(entity instanceof EntityPlayer && entity.getHealth() >= 0.0F) {
         Minecraft var10001 = mc;
         if(entity != Minecraft.thePlayer) {
            return true;
         }
      }

      return false;
   }

   private void doOther2DESP() {
      Minecraft var10000 = mc;

      for(EntityPlayer entity : Minecraft.theWorld.playerEntities) {
         if(isValid(entity)) {
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
            var10000 = mc;
            float DISTANCE = Minecraft.thePlayer.getDistanceToEntity(entity);
            float DISTANCE_SCALE = Math.min(DISTANCE * 0.15F, 0.15F);
            float SCALE = 0.035F;
            SCALE = SCALE / 2.0F;
            float xMid = (float)x;
            float yMid = (float)y + entity.height + 0.5F - (entity.isChild()?entity.height / 2.0F:0.0F);
            float zMid = (float)z;
            GlStateManager.translate((float)x, (float)y + entity.height + 0.5F - (entity.isChild()?entity.height / 2.0F:0.0F), (float)z);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            mc.getRenderManager();
            GlStateManager.rotate(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(-SCALE, -SCALE, -SCALE);
            Tessellator tesselator = Tessellator.getInstance();
            WorldRenderer worldRenderer = tesselator.getWorldRenderer();
            float HEALTH = entity.getHealth();
            boolean COLOR = true;
            int COLOR1;
            if((double)HEALTH > 20.0D) {
               COLOR1 = -65292;
            } else if((double)HEALTH >= 10.0D) {
               COLOR1 = -16711936;
            } else if((double)HEALTH >= 3.0D) {
               COLOR1 = -23296;
            } else {
               COLOR1 = -65536;
            }

            new Color(0, 0, 0);
            double thickness = (double)(1.5F + DISTANCE * 0.01F);
            double xLeft = -20.0D;
            double xRight = 20.0D;
            double yUp = 27.0D;
            double yDown = 130.0D;
            double size = 10.0D;
            Color color = new Color((float)((Double)r.getValue()).intValue() / 255.0F, (float)((Double)g.getValue()).intValue() / 255.0F, (float)((Double)b.getValue()).intValue() / 255.0F, (float)((Double)a.getValue()).intValue() / 255.0F);
            if(entity.hurtTime > 0) {
               color = new Color(255, 0, 0);
            } else if(Teams.isOnSameTeam(entity)) {
               color = new Color(0, 255, 0);
            } else {
               entity.isInvisible();
            }

            drawBorderedRect((float)xLeft, (float)yUp, (float)xRight, (float)yDown, (float)thickness + 0.5F, Colors.BLACK.c, 0);
            drawBorderedRect((float)xLeft, (float)yUp, (float)xRight, (float)yDown, (float)thickness, color.getRGB(), 0);
            drawBorderedRect((float)xLeft - 3.0F - DISTANCE * 0.2F, (float)yDown - (float)(yDown - yUp), (float)xLeft - 2.0F, (float)yDown, 0.15F, Colors.BLACK.c, (new Color(100, 100, 100)).getRGB());
            drawBorderedRect((float)xLeft - 3.0F - DISTANCE * 0.2F, (float)yDown - (float)(yDown - yUp) * Math.min(1.0F, entity.getHealth() / 20.0F), (float)xLeft - 2.0F, (float)yDown, 0.15F, Colors.BLACK.c, COLOR1);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GlStateManager.disableBlend();
            GL11.glDisable(3042);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glNormal3f(1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
         }
      }

   }

   private void doH2DESP() {
      Minecraft var10000 = mc;

      for(EntityPlayer entity : Minecraft.theWorld.playerEntities) {
         if(isValid(entity)) {
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
            var10000 = mc;
            float DISTANCE = Minecraft.thePlayer.getDistanceToEntity(entity);
            float DISTANCE_SCALE = Math.min(DISTANCE * 0.15F, 0.15F);
            float SCALE = 0.035F;
            SCALE = SCALE / 2.0F;
            float xMid = (float)x;
            float yMid = (float)y + entity.height + 0.5F - (entity.isChild()?entity.height / 2.0F:0.0F);
            float zMid = (float)z;
            GlStateManager.translate((float)x, (float)y + entity.height + 0.5F - (entity.isChild()?entity.height / 2.0F:0.0F), (float)z);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            mc.getRenderManager();
            GlStateManager.rotate(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(-SCALE, -SCALE, -SCALE);
            Tessellator tesselator = Tessellator.getInstance();
            WorldRenderer worldRenderer = tesselator.getWorldRenderer();
            float HEALTH = entity.getHealth();
            boolean COLOR = true;
            if((double)HEALTH > 20.0D) {
               int COLOR1 = -65292;
            } else if((double)HEALTH >= 10.0D) {
               int COLOR1 = -16711936;
            } else if((double)HEALTH >= 3.0D) {
               int COLOR1 = -23296;
            } else {
               int COLOR1 = -65536;
            }

            new Color(0, 0, 0);
            double thickness = (double)(1.5F + DISTANCE * 0.01F);
            double xLeft = -20.0D;
            double xRight = 20.0D;
            double yUp = 27.0D;
            double yDown = 130.0D;
            double size = 10.0D;
            new Color(255, 255, 255);
            if(entity.hurtTime > 0) {
               new Color(255, 0, 0);
            } else if(Teams.isOnSameTeam(entity)) {
               new Color(0, 255, 0);
            } else {
               entity.isInvisible();
            }

            drawBorderedRect((float)xLeft - 14.0F, (float)yUp - 14.0F, (float)xRight + 14.0F, (float)yDown + 14.0F, (float)thickness, 0, entity.hurtTime > 0?(new Color(255, 0, 0, 100)).getRGB():this.ncolor);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GlStateManager.disableBlend();
            GL11.glDisable(3042);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glNormal3f(1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
         }
      }

   }

   private void doOtherH2DESP() {
      Minecraft var10000 = mc;

      for(EntityPlayer entity : Minecraft.theWorld.playerEntities) {
         if(isValid(entity)) {
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
            var10000 = mc;
            float DISTANCE = Minecraft.thePlayer.getDistanceToEntity(entity);
            float DISTANCE_SCALE = Math.min(DISTANCE * 0.15F, 0.15F);
            float SCALE = 0.035F;
            SCALE = SCALE / 2.0F;
            float xMid = (float)x;
            float yMid = (float)y + entity.height + 0.5F - (entity.isChild()?entity.height / 2.0F:0.0F);
            float zMid = (float)z;
            GlStateManager.translate((float)x, (float)y + entity.height + 0.5F - (entity.isChild()?entity.height / 2.0F:0.0F), (float)z);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            mc.getRenderManager();
            GlStateManager.rotate(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GL11.glScalef(-SCALE, -SCALE, -SCALE);
            Tessellator tesselator = Tessellator.getInstance();
            WorldRenderer worldRenderer = tesselator.getWorldRenderer();
            float HEALTH = entity.getHealth();
            boolean COLOR = true;
            if((double)HEALTH > 20.0D) {
               int COLOR1 = -65292;
            } else if((double)HEALTH >= 10.0D) {
               int COLOR1 = -16711936;
            } else if((double)HEALTH >= 3.0D) {
               int COLOR1 = -23296;
            } else {
               int COLOR1 = -65536;
            }

            new Color(0, 0, 0);
            double thickness = (double)(1.5F + DISTANCE * 0.01F);
            double xLeft = -20.0D;
            double xRight = 20.0D;
            double yUp = 27.0D;
            double yDown = 130.0D;
            double size = 10.0D;
            new Color(255, 255, 255);
            if(entity.hurtTime > 0) {
               new Color(255, 0, 0);
            } else if(Teams.isOnSameTeam(entity)) {
               new Color(0, 255, 0);
            } else {
               entity.isInvisible();
            }

            drawBorderedRect((float)xLeft - 17.0F, (float)yUp - 14.0F, (float)xRight + 14.0F, (float)yDown + 14.0F, (float)thickness, entity.hurtTime > 0?(new Color(255, 0, 0, 255)).getRGB():this.ncolor, 0);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GlStateManager.disableBlend();
            GL11.glDisable(3042);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glNormal3f(1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
         }
      }

   }

   public static void drawBorderedRect(float x, float y, float x2, float y2, float l1, int col1, int col2) {
      drawRect(x, y, x2, y2, col2);
      float f = (float)(col1 >> 24 & 255) / 255.0F;
      float f1 = (float)(col1 >> 16 & 255) / 255.0F;
      float f2 = (float)(col1 >> 8 & 255) / 255.0F;
      float f3 = (float)(col1 & 255) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glPushMatrix();
      GL11.glColor4f(f1, f2, f3, f);
      GL11.glLineWidth(l1);
      GL11.glBegin(1);
      GL11.glVertex2d((double)x, (double)y);
      GL11.glVertex2d((double)x, (double)y2);
      GL11.glVertex2d((double)x2, (double)y2);
      GL11.glVertex2d((double)x2, (double)y);
      GL11.glVertex2d((double)x, (double)y);
      GL11.glVertex2d((double)x2, (double)y);
      GL11.glVertex2d((double)x, (double)y2);
      GL11.glVertex2d((double)x2, (double)y2);
      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
   }

   public static void drawRect(float g, float h, float i, float j, int col1) {
      float f = (float)(col1 >> 24 & 255) / 255.0F;
      float f1 = (float)(col1 >> 16 & 255) / 255.0F;
      float f2 = (float)(col1 >> 8 & 255) / 255.0F;
      float f3 = (float)(col1 & 255) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glPushMatrix();
      GL11.glColor4f(f1, f2, f3, f);
      GL11.glBegin(7);
      GL11.glVertex2d((double)i, (double)h);
      GL11.glVertex2d((double)g, (double)h);
      GL11.glVertex2d((double)g, (double)j);
      GL11.glVertex2d((double)i, (double)j);
      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
   }

   private void drawVerticalLine(double xPos, double yPos, double xSize, double thickness, Color color) {
      Tessellator tesselator = Tessellator.getInstance();
      WorldRenderer worldRenderer = tesselator.getWorldRenderer();
      worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
      worldRenderer.pos(xPos - xSize, yPos - thickness / 2.0D, 0.0D).color((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F).endVertex();
      worldRenderer.pos(xPos - xSize, yPos + thickness / 2.0D, 0.0D).color((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F).endVertex();
      worldRenderer.pos(xPos + xSize, yPos + thickness / 2.0D, 0.0D).color((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F).endVertex();
      worldRenderer.pos(xPos + xSize, yPos - thickness / 2.0D, 0.0D).color((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F).endVertex();
      tesselator.draw();
   }

   private void drawHorizontalLine(double xPos, double yPos, double ySize, double thickness, Color color) {
      Tessellator tesselator = Tessellator.getInstance();
      WorldRenderer worldRenderer = tesselator.getWorldRenderer();
      worldRenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
      worldRenderer.pos(xPos - thickness / 2.0D, yPos - ySize, 0.0D).color((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F).endVertex();
      worldRenderer.pos(xPos - thickness / 2.0D, yPos + ySize, 0.0D).color((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F).endVertex();
      worldRenderer.pos(xPos + thickness / 2.0D, yPos + ySize, 0.0D).color((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F).endVertex();
      worldRenderer.pos(xPos + thickness / 2.0D, yPos - ySize, 0.0D).color((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F).endVertex();
      tesselator.draw();
   }

   public boolean checkValidity1(EntityLivingBase entity) {
      return entity == Minecraft.thePlayer?false:entity.isInvisible();
   }

   public static enum ESPMode {
      Box,
      HBox,
      Line,
      CSGO,
      Other2D,
      H2D,
      Flat,
      New2D;
   }
}
