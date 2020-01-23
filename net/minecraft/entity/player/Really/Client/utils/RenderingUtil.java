package net.minecraft.entity.player.Really.Client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.entity.player.Really.Client.module.modules.movement.Colors;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public class RenderingUtil {
   public static void drawOutlinedString(String str, float x, float y, int color) {
      Minecraft mc = Minecraft.getMinecraft();
      Minecraft.fontRendererObj.drawString(str, (int)(x - 0.3F), (int)y, Colors.getColor(0));
      Minecraft.fontRendererObj.drawString(str, (int)(x + 0.3F), (int)y, Colors.getColor(0));
      Minecraft.fontRendererObj.drawString(str, (int)x, (int)(y + 0.3F), Colors.getColor(0));
      Minecraft.fontRendererObj.drawString(str, (int)x, (int)(y - 0.3F), Colors.getColor(0));
      Minecraft.fontRendererObj.drawString(str, (int)x, (int)y, color);
   }

   public static void drawCenteredGradient(double x, double y, double x2, double y2, int col1, int col2) {
      float width = (float)Math.abs(x - x2);
      float height = (float)Math.abs(y - y2);
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      int bright = 50;
      float var1 = 1.0F;
      Math.max(x, x2);
      Math.min(x, x2);

      for(double i = Math.min(x, x2); i < Math.min(x, x2) + (double)(width / 2.0F); ++i) {
         drawBorderRect(i, y, x2, y2, Colors.getColor(255, 255, 255, 255), 2.0D);
      }

      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GlStateManager.color(0.0F, 0.0F, 0.0F, 0.0F);
   }

   public static void drawBorderRect(double x, double y, double x1, double y1, int color, double lwidth) {
      drawHLine(x, y, x1, y, (float)lwidth, color);
      drawHLine(x1, y, x1, y1, (float)lwidth, color);
      drawHLine(x, y1, x1, y1, (float)lwidth, color);
      drawHLine(x, y1, x, y, (float)lwidth, color);
   }

   public static void drawFancy(double d, double e, double f2, double f3, int paramColor) {
      float alpha = (float)(paramColor >> 24 & 255) / 255.0F;
      float red = (float)(paramColor >> 16 & 255) / 255.0F;
      float green = (float)(paramColor >> 8 & 255) / 255.0F;
      float blue = (float)(paramColor & 255) / 255.0F;
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GL11.glPushMatrix();
      GL11.glEnable(2848);
      GL11.glEnable(2881);
      GL11.glEnable(2832);
      GL11.glEnable(3042);
      GL11.glColor4f(red, green, blue, alpha);
      GL11.glBegin(7);
      GL11.glVertex2d(f2 + 1.300000011920929D, e);
      GL11.glVertex2d(d + 1.0D, e);
      GL11.glVertex2d(d - 1.300000011920929D, f3);
      GL11.glVertex2d(f2 - 1.0D, f3);
      GL11.glEnd();
      GL11.glDisable(2848);
      GL11.glDisable(2881);
      GL11.glDisable(2832);
      GL11.glDisable(3042);
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GL11.glPopMatrix();
   }

   public static void drawGradient(double x, double y, double x2, double y2, int col1, int col2) {
      float f = (float)(col1 >> 24 & 255) / 255.0F;
      float f1 = (float)(col1 >> 16 & 255) / 255.0F;
      float f2 = (float)(col1 >> 8 & 255) / 255.0F;
      float f3 = (float)(col1 & 255) / 255.0F;
      float f4 = (float)(col2 >> 24 & 255) / 255.0F;
      float f5 = (float)(col2 >> 16 & 255) / 255.0F;
      float f6 = (float)(col2 >> 8 & 255) / 255.0F;
      float f7 = (float)(col2 & 255) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glShadeModel(7425);
      GL11.glPushMatrix();
      GL11.glBegin(7);
      GL11.glColor4f(f1, f2, f3, f);
      GL11.glVertex2d(x2, y);
      GL11.glVertex2d(x, y);
      GL11.glColor4f(f5, f6, f7, f4);
      GL11.glVertex2d(x, y2);
      GL11.glVertex2d(x2, y2);
      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glShadeModel(7424);
   }

   public static void drawGradientSideways(double left, double top, double right, double bottom, int col1, int col2) {
      float f = (float)(col1 >> 24 & 255) / 255.0F;
      float f1 = (float)(col1 >> 16 & 255) / 255.0F;
      float f2 = (float)(col1 >> 8 & 255) / 255.0F;
      float f3 = (float)(col1 & 255) / 255.0F;
      float f4 = (float)(col2 >> 24 & 255) / 255.0F;
      float f5 = (float)(col2 >> 16 & 255) / 255.0F;
      float f6 = (float)(col2 >> 8 & 255) / 255.0F;
      float f7 = (float)(col2 & 255) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glShadeModel(7425);
      GL11.glPushMatrix();
      GL11.glBegin(7);
      GL11.glColor4f(f1, f2, f3, f);
      GL11.glVertex2d(left, top);
      GL11.glVertex2d(left, bottom);
      GL11.glColor4f(f5, f6, f7, f4);
      GL11.glVertex2d(right, bottom);
      GL11.glVertex2d(right, top);
      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
      GL11.glShadeModel(7424);
      GL11.glColor4d(255.0D, 255.0D, 255.0D, 255.0D);
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

      float var11 = (float)(color >> 24 & 255) / 255.0F;
      float var6 = (float)(color >> 16 & 255) / 255.0F;
      float var7 = (float)(color >> 8 & 255) / 255.0F;
      float var8 = (float)(color & 255) / 255.0F;
      WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(var6, var7, var8, var11);
      worldRenderer.startDrawingQuads();
      worldRenderer.addVertex(left, bottom, 0.0D);
      worldRenderer.addVertex(right, bottom, 0.0D);
      worldRenderer.addVertex(right, top, 0.0D);
      worldRenderer.addVertex(left, top, 0.0D);
      Tessellator.getInstance().draw();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void rectangleBordered(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
      rectangle(x + width, y + width, x1 - width, y1 - width, internalColor);
      rectangle(x + width, y, x1 - width, y + width, borderColor);
      rectangle(x, y, x + width, y1, borderColor);
      rectangle(x1 - width, y, x1, y1, borderColor);
      rectangle(x + width, y1 - width, x1 - width, y1, borderColor);
   }

   public static void filledBox(AxisAlignedBB boundingBox, int color, boolean shouldColor) {
      GlStateManager.pushMatrix();
      float var11 = (float)(color >> 24 & 255) / 255.0F;
      float var6 = (float)(color >> 16 & 255) / 255.0F;
      float var7 = (float)(color >> 8 & 255) / 255.0F;
      float var8 = (float)(color & 255) / 255.0F;
      WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
      if(shouldColor) {
         GlStateManager.color(var6, var7, var8, var11);
      }

      byte draw = 7;
      worldRenderer.startDrawing(draw);
      worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
      worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
      worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
      worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
      worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
      worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
      worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
      worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
      Tessellator.getInstance().draw();
      worldRenderer.startDrawing(draw);
      worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
      worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
      worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
      worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
      worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
      worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
      worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
      worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
      Tessellator.getInstance().draw();
      worldRenderer.startDrawing(draw);
      worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
      worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
      worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
      worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
      worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
      worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
      worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
      worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
      Tessellator.getInstance().draw();
      worldRenderer.startDrawing(draw);
      worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
      worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
      worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
      worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
      worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
      worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
      worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
      worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
      Tessellator.getInstance().draw();
      worldRenderer.startDrawing(draw);
      worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
      worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
      worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
      worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
      worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
      worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
      worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
      worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
      Tessellator.getInstance().draw();
      worldRenderer.startDrawing(draw);
      worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
      worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
      worldRenderer.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
      worldRenderer.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
      worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
      worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
      worldRenderer.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
      worldRenderer.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
      Tessellator.getInstance().draw();
      GlStateManager.depthMask(true);
      GlStateManager.popMatrix();
   }

   public static void drawOutlinedBoundingBox(AxisAlignedBB boundingBox) {
      Tessellator var1 = Tessellator.getInstance();
      WorldRenderer var2 = var1.getWorldRenderer();
      var2.startDrawing(3);
      var2.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
      var2.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
      var2.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
      var2.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
      var2.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
      var1.draw();
      var2.startDrawing(3);
      var2.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
      var2.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
      var2.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
      var2.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
      var2.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
      var1.draw();
      var2.startDrawing(1);
      var2.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
      var2.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
      var2.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
      var2.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
      var2.addVertex(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
      var2.addVertex(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
      var2.addVertex(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
      var2.addVertex(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
      var1.draw();
   }

   public static void drawLines(AxisAlignedBB boundingBox) {
      GL11.glPushMatrix();
      GL11.glBegin(2);
      GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.minZ);
      GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
      GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
      GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ);
      GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ);
      GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
      GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
      GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.minZ);
      GL11.glVertex3d(boundingBox.maxX, boundingBox.minY, boundingBox.minZ);
      GL11.glVertex3d(boundingBox.minX, boundingBox.minY, boundingBox.maxZ);
      GL11.glVertex3d(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ);
      GL11.glVertex3d(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ);
      GL11.glEnd();
      GL11.glPopMatrix();
   }

   public static void drawBoundingBox(AxisAlignedBB axisalignedbb) {
      Tessellator tessellator = Tessellator.getInstance();
      WorldRenderer worldrender = Tessellator.getInstance().getWorldRenderer();
      worldrender.startDrawingQuads();
      worldrender.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
      worldrender.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
      worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
      worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
      worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
      worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
      worldrender.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
      worldrender.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
      worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
      worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
      worldrender.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
      worldrender.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
      worldrender.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
      worldrender.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
      worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
      worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
      worldrender.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
      worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
      worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
      worldrender.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
      worldrender.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
      worldrender.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
      worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
      worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
      worldrender.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
      worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
      worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
      worldrender.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
      worldrender.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
      worldrender.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
      worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
      worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
      worldrender.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
      worldrender.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
      worldrender.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
      worldrender.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
      worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
      worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
      worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
      worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
      worldrender.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.maxZ);
      worldrender.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.maxZ);
      worldrender.addVertex(axisalignedbb.minX, axisalignedbb.maxY, axisalignedbb.minZ);
      worldrender.addVertex(axisalignedbb.minX, axisalignedbb.minY, axisalignedbb.minZ);
      worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.minZ);
      worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.minZ);
      worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.maxY, axisalignedbb.maxZ);
      worldrender.addVertex(axisalignedbb.maxX, axisalignedbb.minY, axisalignedbb.maxZ);
      tessellator.draw();
   }

   public static void drawLine3D(float x, float y, float z, float x1, float y1, float z1, int color) {
      pre3D();
      GL11.glLoadIdentity();
      Minecraft.getMinecraft();
      Minecraft.entityRenderer.orientCamera(Minecraft.getMinecraft().timer.renderPartialTicks);
      float var11 = (float)(color >> 24 & 255) / 255.0F;
      float var6 = (float)(color >> 16 & 255) / 255.0F;
      float var7 = (float)(color >> 8 & 255) / 255.0F;
      float var8 = (float)(color & 255) / 255.0F;
      GL11.glColor4f(var6, var7, var8, var11);
      GL11.glLineWidth(0.5F);
      GL11.glBegin(3);
      GL11.glVertex3d((double)x, (double)y, (double)z);
      GL11.glVertex3d((double)x1, (double)y1, (double)z1);
      GL11.glEnd();
      post3D();
   }

   public static void draw3DLine(float x, float y, float z, int color) {
      pre3D();
      GL11.glLoadIdentity();
      Minecraft.getMinecraft();
      Minecraft.entityRenderer.orientCamera(Minecraft.getMinecraft().timer.renderPartialTicks);
      float var11 = (float)(color >> 24 & 255) / 255.0F;
      float var6 = (float)(color >> 16 & 255) / 255.0F;
      float var7 = (float)(color >> 8 & 255) / 255.0F;
      float var8 = (float)(color & 255) / 255.0F;
      GL11.glColor4f(var6, var7, var8, var11);
      GL11.glLineWidth(1.5F);
      GL11.glBegin(3);
      Minecraft.getMinecraft();
      GL11.glVertex3d(0.0D, (double)Minecraft.thePlayer.getEyeHeight(), 0.0D);
      GL11.glVertex3d((double)x, (double)y, (double)z);
      GL11.glEnd();
      post3D();
   }

   public static void pre3D() {
      GL11.glPushMatrix();
      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glShadeModel(7425);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDisable(2896);
      GL11.glDepthMask(false);
      GL11.glHint(3154, 4354);
   }

   public static void post3D() {
      GL11.glDepthMask(true);
      GL11.glEnable(2929);
      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void glColor(float alpha, int redRGB, int greenRGB, int blueRGB) {
      float red = 0.003921569F * (float)redRGB;
      float green = 0.003921569F * (float)greenRGB;
      float blue = 0.003921569F * (float)blueRGB;
      GL11.glColor4f(red, green, blue, alpha);
   }

   public static void drawRect(float x, float y, float x1, float y1) {
      GL11.glBegin(7);
      GL11.glVertex2f(x, y1);
      GL11.glVertex2f(x1, y1);
      GL11.glVertex2f(x1, y);
      GL11.glVertex2f(x, y);
      GL11.glEnd();
   }

   public static void glColor(int hex) {
      float alpha = (float)(hex >> 24 & 255) / 255.0F;
      float red = (float)(hex >> 16 & 255) / 255.0F;
      float green = (float)(hex >> 8 & 255) / 255.0F;
      float blue = (float)(hex & 255) / 255.0F;
      GL11.glColor4f(red, green, blue, alpha);
   }

   public static void drawRect(float x, float y, float x1, float y1, int color) {
      enableGL2D();
      glColor(color);
      drawRect(x, y, x1, y1);
      disableGL2D();
   }

   public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
      drawRect(x + 0.5F, y, x1 - 0.5F, y + 0.5F, insideC);
      drawRect(x + 0.5F, y1 - 0.5F, x1 - 0.5F, y1, insideC);
      drawRect(x, y + 0.5F, x1, y1 - 0.5F, insideC);
   }

   public static void drawRoundedRect(int xCoord, int yCoord, int xSize, int ySize, int colour) {
      int width = xCoord + xSize;
      int height = yCoord + ySize;
      drawRect((float)(xCoord + 1), (float)yCoord, (float)(width - 1), (float)height, colour);
      drawRect((float)xCoord, (float)(yCoord + 1), (float)width, (float)(height - 1), colour);
   }

   public static void drawHLine(double x, double y, double x1, double y1, float width, int color) {
      float var11 = (float)(color >> 24 & 255) / 255.0F;
      float var6 = (float)(color >> 16 & 255) / 255.0F;
      float var7 = (float)(color >> 8 & 255) / 255.0F;
      float var8 = (float)(color & 255) / 255.0F;
      GlStateManager.enableBlend();
      GlStateManager.disableTexture2D();
      GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
      GlStateManager.color(var6, var7, var8, var11);
      GL11.glPushMatrix();
      GL11.glLineWidth(width);
      GL11.glBegin(3);
      GL11.glVertex2d(x, y);
      GL11.glVertex2d(x1, y1);
      GL11.glEnd();
      GL11.glLineWidth(1.0F);
      GL11.glPopMatrix();
      GlStateManager.enableTexture2D();
      GlStateManager.disableBlend();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   public static void drawVLine(float x, float y, float x1, float y1, float width, int color) {
      if(width > 0.0F) {
         GL11.glPushMatrix();
         pre3D();
         float var11 = (float)(color >> 24 & 255) / 255.0F;
         float var6 = (float)(color >> 16 & 255) / 255.0F;
         float var7 = (float)(color >> 8 & 255) / 255.0F;
         float var8 = (float)(color & 255) / 255.0F;
         GlStateManager.enableAlpha();
         GlStateManager.enableBlend();
         int shade = GL11.glGetInteger(2900);
         GlStateManager.shadeModel(7425);
         GL11.glColor4f(var6, var7, var8, var11);
         float line = GL11.glGetFloat(2849);
         GL11.glLineWidth(width);
         GL11.glBegin(3);
         GL11.glVertex3d((double)x, (double)y, 0.0D);
         GL11.glVertex3d((double)x1, (double)y1, 0.0D);
         GL11.glEnd();
         GlStateManager.shadeModel(shade);
         GL11.glLineWidth(line);
         post3D();
         GL11.glPopMatrix();
      }
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

   public static void drawEllipse(float cx, float cy, float rx, float ry, int num_segments, int col) {
      GL11.glPushMatrix();
      cx = cx * 2.0F;
      cy = cy * 2.0F;
      float theta = (float)(6.283185307179586D / (double)num_segments);
      float c = (float)Math.cos((double)theta);
      float s = (float)Math.sin((double)theta);
      float f = (float)(col >> 24 & 255) / 255.0F;
      float f1 = (float)(col >> 16 & 255) / 255.0F;
      float f2 = (float)(col >> 8 & 255) / 255.0F;
      float f3 = (float)(col & 255) / 255.0F;
      float x = 1.0F;
      float y = 0.0F;
      enableGL2D();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      GL11.glColor4f(f1, f2, f3, f);
      GL11.glBegin(2);

      for(int ii = 0; ii < num_segments; ++ii) {
         GL11.glVertex2f(x * rx + cx, y * ry + cy);
         float t = x;
         x = c * x - s * y;
         y = s * t + c * y;
      }

      GL11.glEnd();
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      disableGL2D();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

   public static void drawCircle(float cx, float cy, float r, int num_segments, int c) {
      GL11.glPushMatrix();
      cx = cx * 2.0F;
      cy = cy * 2.0F;
      float f = (float)(c >> 24 & 255) / 255.0F;
      float f1 = (float)(c >> 16 & 255) / 255.0F;
      float f2 = (float)(c >> 8 & 255) / 255.0F;
      float f3 = (float)(c & 255) / 255.0F;
      float theta = (float)(6.2831852D / (double)num_segments);
      float p = (float)Math.cos((double)theta);
      float s = (float)Math.sin((double)theta);
      float var18;
      float x = var18 = r * 2.0F;
      float y = 0.0F;
      enableGL2D();
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      GL11.glColor4f(f1, f2, f3, f);
      GL11.glBegin(2);

      for(int ii = 0; ii < num_segments; ++ii) {
         GL11.glVertex2f(x + cx, y + cy);
         float t = x;
         x = p * x - s * y;
         y = s * t + p * y;
      }

      GL11.glEnd();
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      disableGL2D();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

   public static void drawBorderedCircle(float circleX, float circleY, double radius, double width, int borderColor, int innerColor) {
      enableGL2D();
      GlStateManager.enableBlend();
      GL11.glEnable(2881);
      drawCircle(circleX, circleY, (float)(radius - 0.5D + width), 72, borderColor);
      drawFullCircle(circleX, circleY, (float)radius, innerColor);
      GlStateManager.disableBlend();
      GL11.glDisable(2881);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      disableGL2D();
   }

   public static void drawFilledTriangle(float x, float y, float r, int c, int borderC) {
      enableGL2D();
      glColor(c);
      GL11.glEnable(2881);
      GL11.glBegin(4);
      GL11.glVertex2f(x + r / 2.0F, y + r / 2.0F);
      GL11.glVertex2f(x + r / 2.0F, y - r / 2.0F);
      GL11.glVertex2f(x - r / 2.0F, y);
      GL11.glEnd();
      GL11.glLineWidth(1.3F);
      glColor(borderC);
      GL11.glBegin(3);
      GL11.glVertex2f(x + r / 2.0F, y + r / 2.0F);
      GL11.glVertex2f(x + r / 2.0F, y - r / 2.0F);
      GL11.glEnd();
      GL11.glBegin(3);
      GL11.glVertex2f(x - r / 2.0F, y);
      GL11.glVertex2f(x + r / 2.0F, y - r / 2.0F);
      GL11.glEnd();
      GL11.glBegin(3);
      GL11.glVertex2f(x + r / 2.0F, y + r / 2.0F);
      GL11.glVertex2f(x - r / 2.0F, y);
      GL11.glEnd();
      GL11.glDisable(2881);
      disableGL2D();
   }

   public static void drawFullCircle(float cx, float cy, float r, int c) {
      r = r * 2.0F;
      cx = cx * 2.0F;
      cy = cy * 2.0F;
      float theta = 0.19634953F;
      float p = (float)Math.cos((double)theta);
      float s = (float)Math.sin((double)theta);
      float x = r;
      float y = 0.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glScalef(0.5F, 0.5F, 0.5F);
      GL11.glBegin(2);
      glColor(c);

      for(int ii = 0; ii < 32; ++ii) {
         GL11.glVertex2f(x + cx, y + cy);
         GL11.glVertex2f(cx, cy);
         float t = x;
         x = p * x - s * y;
         y = s * t + p * y;
      }

      GL11.glEnd();
      GL11.glScalef(2.0F, 2.0F, 2.0F);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glDisable(3042);
      GL11.glEnable(3553);
   }

   public static void ohnoes() {
      Minecraft.getMinecraft().shutdown();
   }
}
