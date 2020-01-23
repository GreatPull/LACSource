package net.minecraft.entity.player.Really.Client.module.modules.render;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class BLCRender {
   private static final Frustum frustum = new Frustum();

   public static double interpolate(double newPos, double oldPos) {
      return oldPos + (newPos - oldPos) * (double)Minecraft.getMinecraft().timer.renderPartialTicks;
   }

   public static boolean isInFrustumView(Entity ent) {
      Entity current = Minecraft.getMinecraft().getRenderViewEntity();
      double x = interpolate(current.posX, current.lastTickPosX);
      double y = interpolate(current.posY, current.lastTickPosY);
      double z = interpolate(current.posZ, current.lastTickPosZ);
      frustum.setPosition(x, y, z);
      return frustum.isBoundingBoxInFrustum(ent.getEntityBoundingBox()) || ent.ignoreFrustumCheck;
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

   public static int getHexRGB(int hex) {
      return -16777216 | hex;
   }

   public static void drawBordered1(double x, double y, double width, double height, double length, int innerColor, int outerColor) {
      Gui.drawRect(x, y, x + width, y + height, innerColor);
      Gui.drawRect(x, y, x, y, outerColor);
   }

   public static int withTransparency(int rgb, float alpha) {
      float r = (float)(rgb >> 16 & 255) / 255.0F;
      float g = (float)(rgb >> 8 & 255) / 255.0F;
      float b = (float)(rgb >> 0 & 255) / 255.0F;
      return (new Color(r, g, b, alpha)).getRGB();
   }

   public static void drawRect(int x, int y, int x1, int y1, int color) {
      GL11.glDisable(2929);
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glDepthMask(true);
      GL11.glEnable(2848);
      GL11.glHint(3154, 4354);
      GL11.glHint(3155, 4354);
      Gui.drawRect((double)x, (double)y, (double)x1, (double)y1, color);
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glEnable(2929);
      GL11.glDisable(2848);
      GL11.glHint(3154, 4352);
      GL11.glHint(3155, 4352);
   }

   public static void drawRect(double x1, double y1, double x2, double y2, int color) {
      Gui.drawRect(x1, y1, x2, y2, color);
   }

   public static void drawBorderRect2(double left, double top, double right, double bottom, int bwidth, int icolor, int bcolor) {
      Gui.drawRect(left + (double)bwidth, top + (double)bwidth, right - (double)bwidth, bottom - (double)bwidth, icolor);
      Gui.drawRect(left, top, left + (double)bwidth, bottom, bcolor);
      Gui.drawRect(left + (double)bwidth, top, right, top + (double)bwidth, bcolor);
      Gui.drawRect(left + (double)bwidth, bottom - (double)bwidth, right, bottom, bcolor);
      Gui.drawRect(right - (double)bwidth, top + (double)bwidth, right, bottom - (double)bwidth, bcolor);
   }

   public static void rectangleBordered(double x, double y, double x1, double y1, double width, int internalColor, int borderColor) {
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


   public static Vec3 interpolateRender(EntityPlayer player) {
      float part = Minecraft.getMinecraft().timer.renderPartialTicks;
      double interpX = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)part;
      double interpY = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)part;
      double interpZ = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)part;
      return new Vec3(interpX, interpY, interpZ);
   }
}
