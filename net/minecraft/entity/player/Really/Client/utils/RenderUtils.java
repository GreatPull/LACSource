package net.minecraft.entity.player.Really.Client.utils;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.Really.Client.utils.Wrapper;
import net.minecraft.entity.player.Really.Client.utils.math.MathUtil;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class RenderUtils {
   private static final Frustum frustum = new Frustum();

   public static double interpolate(double newPos, double oldPos) {
      return oldPos + (newPos - oldPos) * (double)Wrapper.mc.timer.renderPartialTicks;
   }

   public static int getRandomRGB(double min, double max, float alpha) {
      return (new Color((float)MathUtil.randomDouble(min, max), (float)MathUtil.randomDouble(min, max), (float)MathUtil.randomDouble(min, max), alpha)).getRGB();
   }

   public static void rectangleBordered(float x, float y, float x1, float y1, float width, int internalColor, int borderColor) {
      drawRect(x + width, y + width, x1 - width, y1 - width, internalColor);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      drawRect(x + width, y, x1 - width, y + width, borderColor);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      drawRect(x, y, x + width, y1, borderColor);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      drawRect(x1 - width, y, x1, y1, borderColor);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      drawRect(x + width, y1 - width, x1 - width, y1, borderColor);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static int withTransparency(int rgb, float alpha) {
      float r2 = (float)(rgb >> 16 & 255) / 255.0F;
      float g2 = (float)(rgb >> 8 & 255) / 255.0F;
      float b2 = (float)(rgb >> 0 & 255) / 255.0F;
      return (new Color(r2, g2, b2, alpha)).getRGB();
   }

   public static int getHexRGB(int hex) {
      return -16777216 | hex;
   }

   public static void pre() {
      GL11.glDisable(2929);
      GL11.glDisable(3553);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
   }

   public static void post() {
      GL11.glDisable(3042);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glColor3d(1.0D, 1.0D, 1.0D);
   }

   public static void drawRoundedRect(float x, float y, float x2, float y2, float round, int color) {
      x = x + (float)((double)(round / 2.0F) + 0.5D);
      y = y + (float)((double)(round / 2.0F) + 0.5D);
      x2 = x2 - (float)((double)(round / 2.0F) + 0.5D);
      y2 = y2 - (float)((double)(round / 2.0F) + 0.5D);
      Gui.drawRect((double)((int)x), (double)((int)y), (double)((int)x2), (double)((int)y2), color);
      circle(x2 - round / 2.0F, y + round / 2.0F, round, color);
      circle(x + round / 2.0F, y2 - round / 2.0F, round, color);
      circle(x + round / 2.0F, y + round / 2.0F, round, color);
      circle(x2 - round / 2.0F, y2 - round / 2.0F, round, color);
      Gui.drawRect((double)((int)(x - round / 2.0F - 0.5F)), (double)((int)(y + round / 2.0F)), (double)((int)x2), (double)((int)(y2 - round / 2.0F)), color);
      Gui.drawRect((double)((int)x), (double)((int)(y + round / 2.0F)), (double)((int)(x2 + round / 2.0F + 0.5F)), (double)((int)(y2 - round / 2.0F)), color);
      Gui.drawRect((double)((int)(x + round / 2.0F)), (double)((int)(y - round / 2.0F - 0.5F)), (double)((int)(x2 - round / 2.0F)), (double)((int)(y2 - round / 2.0F)), color);
      Gui.drawRect((double)((int)(x + round / 2.0F)), (double)((int)y), (double)((int)(x2 - round / 2.0F)), (double)((int)(y2 + round / 2.0F + 0.5F)), color);
   }

   public static void circle(float x, float y, float radius, int fill) {
      arc(x, y, 0.0F, 360.0F, radius, fill);
   }

   public static void circle(float x, float y, float radius, Color fill) {
      arc(x, y, 0.0F, 360.0F, radius, fill);
   }

   public static void arc(float x, float y, float start, float end, float radius, int color) {
      arcEllipse(x, y, start, end, radius, radius, color);
   }

   public static void arc(float x, float y, float start, float end, float radius, Color color) {
      arcEllipse(x, y, start, end, radius, radius, color);
   }

   public static void arcEllipse(float x, float y, float start, float end, float w, float h, int color) {
      GlStateManager.color(0.0F, 0.0F, 0.0F);
      GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);
      float temp = 0.0F;
      if(start > end) {
         temp = end;
         end = start;
         start = temp;
      }

      float var11 = (float)(color >> 24 & 255) / 255.0F;
      float var12 = (float)(color >> 16 & 255) / 255.0F;
      float var13 = (float)(color >> 8 & 255) / 255.0F;
      float var14 = (float)(color & 255) / 255.0F;
      Tessellator var15 = Tessellator.getInstance();
      WorldRenderer var16 = var15.getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(var12, var13, var14, var11);
      if(var11 > 0.5F) {
         GL11.glEnable(2848);
         GL11.glLineWidth(2.0F);
         GL11.glBegin(3);

         for(float i = end; i >= start; i -= 4.0F) {
            float ldx = (float)Math.cos((double)i * 3.141592653589793D / 180.0D) * w * 1.001F;
            float ldy = (float)Math.sin((double)i * 3.141592653589793D / 180.0D) * h * 1.001F;
            GL11.glVertex2f(x + ldx, y + ldy);
         }

         GL11.glEnd();
         GL11.glDisable(2848);
      }

      GL11.glBegin(6);

      for(float i = end; i >= start; i -= 4.0F) {
         float ldx = (float)Math.cos((double)i * 3.141592653589793D / 180.0D) * w;
         float ldy = (float)Math.sin((double)i * 3.141592653589793D / 180.0D) * h;
         GL11.glVertex2f(x + ldx, y + ldy);
      }

      GL11.glEnd();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
   }

   public static void arcEllipse(float x, float y, float start, float end, float w, float h, Color color) {
      GlStateManager.color(0.0F, 0.0F, 0.0F);
      GL11.glColor4f(0.0F, 0.0F, 0.0F, 0.0F);
      float temp = 0.0F;
      if(start > end) {
         temp = end;
         end = start;
         start = temp;
      }

      Tessellator var9 = Tessellator.getInstance();
      WorldRenderer var10 = var9.getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getAlpha() / 255.0F);
      if((float)color.getAlpha() > 0.5F) {
         GL11.glEnable(2848);
         GL11.glLineWidth(2.0F);
         GL11.glBegin(3);

         for(float i = end; i >= start; i -= 4.0F) {
            float ldx = (float)Math.cos((double)i * 3.141592653589793D / 180.0D) * w * 1.001F;
            float ldy = (float)Math.sin((double)i * 3.141592653589793D / 180.0D) * h * 1.001F;
            GL11.glVertex2f(x + ldx, y + ldy);
         }

         GL11.glEnd();
         GL11.glDisable(2848);
      }

      GL11.glBegin(6);

      for(float i = end; i >= start; i -= 4.0F) {
         float ldx = (float)Math.cos((double)i * 3.141592653589793D / 180.0D) * w;
         float ldy = (float)Math.sin((double)i * 3.141592653589793D / 180.0D) * h;
         GL11.glVertex2f(x + ldx, y + ldy);
      }

      GL11.glEnd();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
   }

   public static void drawBordered(double x2, double y2, double width, double height, double length, int innerColor, int outerColor) {
      Gui.drawRect(x2, y2, x2 + width, y2 + height, innerColor);
      Gui.drawRect(x2 - length, y2, x2, y2 + height, outerColor);
      Gui.drawRect(x2 - length, y2 - length, x2 + width, y2, outerColor);
      Gui.drawRect(x2 + width, y2 - length, x2 + width + length, y2 + height + length, outerColor);
      Gui.drawRect(x2 - length, y2 + height, x2 + width, y2 + height + length, outerColor);
   }

   public static void drawImage(ResourceLocation image, int x, int y, int width, int height) {
      new ScaledResolution(Minecraft.getMinecraft());
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDepthMask(false);
      OpenGlHelper.glBlendFunc(770, 771, 1, 0);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      Minecraft.getMinecraft().getTextureManager().bindTexture(image);
      Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, (float)width, (float)height);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
   }

   public static void drawImage(ResourceLocation image, int x, int y, int width, int height, Color color) {
      new ScaledResolution(Minecraft.getMinecraft());
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDepthMask(false);
      OpenGlHelper.glBlendFunc(770, 771, 1, 0);
      GL11.glColor4f((float)color.getRed() / 255.0F, (float)color.getBlue() / 255.0F, (float)color.getRed() / 255.0F, 1.0F);
      Minecraft.getMinecraft().getTextureManager().bindTexture(image);
      Gui.drawModalRectWithCustomSizedTexture(x, y, 0.0F, 0.0F, width, height, (float)width, (float)height);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
   }

   public static void drawBordered1(double x2, double y2, double width, double height, double length, int innerColor, int outerColor) {
      Gui.drawRect(x2, y2, x2 + width, y2 + height, innerColor);
      Gui.drawRect(x2, y2, x2, y2, outerColor);
   }

   public static boolean isInFrustumView(Entity ent) {
      Entity current = Minecraft.getMinecraft().getRenderViewEntity();
      double x2 = interpolate(current.posX, current.lastTickPosX);
      double y2 = interpolate(current.posY, current.lastTickPosY);
      double z2 = interpolate(current.posZ, current.lastTickPosZ);
      frustum.setPosition(x2, y2, z2);
      return frustum.isBoundingBoxInFrustum(ent.getEntityBoundingBox()) || ent.ignoreFrustumCheck;
   }

   public static final ScaledResolution getScaledRes() {
      ScaledResolution scaledRes = new ScaledResolution(Minecraft.getMinecraft());
      return scaledRes;
   }

   public static void rectangle(double left, double top, double right, double bottom, int color) {
      if(left < right) {
         double var5 = left;
         left = right;
         right = var5;
      }

      if(top < bottom) {
         double var5 = top;
         top = bottom;
         bottom = var5;
      }

      float var6 = (float)(color >> 24 & 255) / 255.0F;
      float var7 = (float)(color >> 16 & 255) / 255.0F;
      float var8 = (float)(color >> 8 & 255) / 255.0F;
      float var9 = (float)(color & 255) / 255.0F;
      WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(var7, var8, var9, var6);
      worldRenderer.startDrawingQuads();
      worldRenderer.addVertex(left, bottom, 0.0D);
      worldRenderer.addVertex(right, bottom, 0.0D);
      worldRenderer.addVertex(right, top, 0.0D);
      worldRenderer.addVertex(left, top, 0.0D);
      Tessellator.getInstance().draw();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
   }

   public static void disableLighting() {
      OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GL11.glDisable(3553);
      OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glDisable(2896);
      GL11.glDisable(3553);
   }

   public static void enableGL2D() {
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glDepthMask(true);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glHint(3155, 4354);
   }

   public static void disableGL2D() {
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
      GL11.glDisable(2848);
      GL11.glHint(3154, 4352);
      GL11.glHint(3155, 4352);
   }

   public static void drawGradientRect(float x2, float y2, float x1, float y1, int topColor, int bottomColor) {
      enableGL2D();
      GL11.glShadeModel(7425);
      GL11.glBegin(7);
      glColor(topColor);
      GL11.glVertex2f(x2, y1);
      GL11.glVertex2f(x1, y1);
      glColor(bottomColor);
      GL11.glVertex2f(x1, y2);
      GL11.glVertex2f(x2, y2);
      GL11.glEnd();
      GL11.glShadeModel(7424);
      disableGL2D();
   }

   public static void glColor(int hex) {
      float alpha = (float)(hex >> 24 & 255) / 255.0F;
      float red = (float)(hex >> 16 & 255) / 255.0F;
      float green = (float)(hex >> 8 & 255) / 255.0F;
      float blue = (float)(hex & 255) / 255.0F;
      GL11.glColor4f(red, green, blue, alpha);
   }

   public static void drawGradientBordere(float x2, float y2, float x1, float y1, float lineWidth, int border, int bottom, int top) {
      enableGL2D();
      drawGradientRect(x2, y2, x1, y1, top, bottom);
      glColor(border);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glLineWidth(lineWidth);
      GL11.glBegin(3);
      GL11.glVertex2f(x2, y2);
      GL11.glVertex2f(x2, y1);
      GL11.glVertex2f(x1, y1);
      GL11.glVertex2f(x1, y2);
      GL11.glVertex2f(x2, y2);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      disableGL2D();
   }

   public static void drawBorderedRect(float x2, float y2, float x22, float y22, float l1, int col1, int col2) {
      drawRect(x2, y2, x22, y22, col2);
      float f2 = (float)(col1 >> 24 & 255) / 255.0F;
      float f22 = (float)(col1 >> 16 & 255) / 255.0F;
      float f3 = (float)(col1 >> 8 & 255) / 255.0F;
      float f4 = (float)(col1 & 255) / 255.0F;
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glColor4f(f22, f3, f4, f2);
      GL11.glLineWidth(l1);
      GL11.glBegin(1);
      GL11.glVertex2d((double)x2, (double)y2);
      GL11.glVertex2d((double)x2, (double)y22);
      GL11.glVertex2d((double)x22, (double)y22);
      GL11.glVertex2d((double)x22, (double)y2);
      GL11.glVertex2d((double)x2, (double)y2);
      GL11.glVertex2d((double)x22, (double)y2);
      GL11.glVertex2d((double)x2, (double)y22);
      GL11.glVertex2d((double)x22, (double)y22);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glPopMatrix();
   }

   public static void drawRect(float g2, float h2, float i2, float j2, int col1) {
      float f2 = (float)(col1 >> 24 & 255) / 255.0F;
      float f22 = (float)(col1 >> 16 & 255) / 255.0F;
      float f3 = (float)(col1 >> 8 & 255) / 255.0F;
      float f4 = (float)(col1 & 255) / 255.0F;
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glColor4f(f22, f3, f4, f2);
      GL11.glBegin(7);
      GL11.glVertex2d((double)i2, (double)h2);
      GL11.glVertex2d((double)g2, (double)h2);
      GL11.glVertex2d((double)g2, (double)j2);
      GL11.glVertex2d((double)i2, (double)j2);
      GL11.glEnd();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glPopMatrix();
   }

   public static class R3DUtils {
      public static void startDrawing() {
         GL11.glEnable(3042);
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(2848);
         GL11.glDisable(3553);
         GL11.glDisable(2929);
         Minecraft.entityRenderer.setupCameraTransform(Wrapper.mc.timer.renderPartialTicks, 0);
      }

      public static void stopDrawing() {
         GL11.glDisable(3042);
         GL11.glEnable(3553);
         GL11.glDisable(2848);
         GL11.glDisable(3042);
         GL11.glEnable(2929);
      }

      public static void drawOutlinedBox(AxisAlignedBB box2) {
         if(box2 != null) {
            Minecraft.entityRenderer.setupCameraTransform(Wrapper.mc.timer.renderPartialTicks, 0);
            GL11.glBegin(3);
            GL11.glVertex3d(box2.minX, box2.minY, box2.minZ);
            GL11.glVertex3d(box2.maxX, box2.minY, box2.minZ);
            GL11.glVertex3d(box2.maxX, box2.minY, box2.maxZ);
            GL11.glVertex3d(box2.minX, box2.minY, box2.maxZ);
            GL11.glVertex3d(box2.minX, box2.minY, box2.minZ);
            GL11.glEnd();
            GL11.glBegin(3);
            GL11.glVertex3d(box2.minX, box2.maxY, box2.minZ);
            GL11.glVertex3d(box2.maxX, box2.maxY, box2.minZ);
            GL11.glVertex3d(box2.maxX, box2.maxY, box2.maxZ);
            GL11.glVertex3d(box2.minX, box2.maxY, box2.maxZ);
            GL11.glVertex3d(box2.minX, box2.maxY, box2.minZ);
            GL11.glEnd();
            GL11.glBegin(1);
            GL11.glVertex3d(box2.minX, box2.minY, box2.minZ);
            GL11.glVertex3d(box2.minX, box2.maxY, box2.minZ);
            GL11.glVertex3d(box2.maxX, box2.minY, box2.minZ);
            GL11.glVertex3d(box2.maxX, box2.maxY, box2.minZ);
            GL11.glVertex3d(box2.maxX, box2.minY, box2.maxZ);
            GL11.glVertex3d(box2.maxX, box2.maxY, box2.maxZ);
            GL11.glVertex3d(box2.minX, box2.minY, box2.maxZ);
            GL11.glVertex3d(box2.minX, box2.maxY, box2.maxZ);
            GL11.glEnd();
         }
      }

      public static void drawBoundingBox(AxisAlignedBB aabb) {
         WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
         Tessellator tessellator = Tessellator.getInstance();
         Minecraft.entityRenderer.setupCameraTransform(Wrapper.mc.timer.renderPartialTicks, 0);
         worldRenderer.startDrawingQuads();
         worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
         worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
         worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
         worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
         worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
         worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
         worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
         worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
         tessellator.draw();
         worldRenderer.startDrawingQuads();
         worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
         worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
         worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
         worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
         worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
         worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
         worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
         worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
         tessellator.draw();
         worldRenderer.startDrawingQuads();
         worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
         worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
         worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
         worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
         worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
         worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
         worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
         worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
         tessellator.draw();
         worldRenderer.startDrawingQuads();
         worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
         worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
         worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
         worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
         worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
         worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
         worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
         worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
         tessellator.draw();
         worldRenderer.startDrawingQuads();
         worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
         worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
         worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
         worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
         worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
         worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
         worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
         worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
         tessellator.draw();
         worldRenderer.startDrawingQuads();
         worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.maxZ);
         worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.maxZ);
         worldRenderer.addVertex(aabb.minX, aabb.maxY, aabb.minZ);
         worldRenderer.addVertex(aabb.minX, aabb.minY, aabb.minZ);
         worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.minZ);
         worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.minZ);
         worldRenderer.addVertex(aabb.maxX, aabb.maxY, aabb.maxZ);
         worldRenderer.addVertex(aabb.maxX, aabb.minY, aabb.maxZ);
         tessellator.draw();
      }
   }
}
