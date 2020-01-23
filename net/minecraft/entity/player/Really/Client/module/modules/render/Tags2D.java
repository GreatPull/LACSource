package net.minecraft.entity.player.Really.Client.module.modules.render;

import java.awt.Color;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender3D;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.module.modules.combat.AntiBot;
import net.minecraft.entity.player.Really.Client.module.modules.player.Teams;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import org.lwjgl.opengl.GL11;

public class Tags2D extends Module {
   private Numbers size = new Numbers("NameTag_Size", "Size", Double.valueOf(1.0D), Double.valueOf(1.0D), Double.valueOf(10.0D), Double.valueOf(0.1D));
   private static Map glCapMap = new HashMap();

   public Tags2D() {
      super("Tags2D", new String[]{"Nametags", "Nametags"}, ModuleType.Render);
      this.addValues(new Value[]{this.size});
   }

   @EventHandler
   public void onRender(EventRender3D e) {
      Minecraft var10000 = mc;

      for(Object o : Minecraft.theWorld.playerEntities) {
         EntityPlayer p = (EntityPlayer)o;
         Minecraft var10001 = mc;
         if(p != Minecraft.thePlayer) {
            double var11 = p.lastTickPosX + (p.posX - p.lastTickPosX) * (double)mc.timer.renderPartialTicks;
            mc.getRenderManager();
            double pX = var11 - RenderManager.renderPosX;
            var11 = p.lastTickPosY + (p.posY - p.lastTickPosY) * (double)mc.timer.renderPartialTicks;
            mc.getRenderManager();
            double pY = var11 - RenderManager.renderPosY;
            var11 = p.lastTickPosZ + (p.posZ - p.lastTickPosZ) * (double)mc.timer.renderPartialTicks;
            mc.getRenderManager();
            double pZ = var11 - RenderManager.renderPosZ;
            this.renderNameTag(p, String.valueOf(p.getDisplayName()), pX, pY, pZ);
         }
      }

   }

   public void renderNameTag(EntityPlayer entity, String tag, double pX, double pY, double pZ) {
      FontRenderer fr = Minecraft.fontRendererObj;
      Minecraft var10000 = mc;
      float var10 = Minecraft.thePlayer.getDistanceToEntity(entity) / 6.0F;
      if(var10 < 0.8F) {
         var10 = 0.8F;
      }

      pY = pY + (entity.isSneaking()?0.5D:0.7D);
      float var11 = (float)((double)var10 * ((Double)this.size.getValue()).doubleValue());
      var11 = var11 / 100.0F;
      tag = entity.getName();
      String var12 = "";
      if(AntiBot.Bots.contains(entity)) {
         var12 = "§9[BOT]";
      } else {
         var12 = "";
      }

      String var13 = "";
      if(!Teams.isOnSameTeam(entity)) {
         var13 = "";
      } else {
         var13 = "§b[TEAM]";
      }

      if((var13 + var12).equals("")) {
         var13 = "§a";
      }

      String var14 = var13 + var12 + tag;
      String var15 = "§7HP:" + (int)entity.getHealth();
      GL11.glPushMatrix();
      GL11.glTranslatef((float)pX, (float)pY + 1.4F, (float)pZ);
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      GL11.glRotatef(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
      GL11.glRotatef(RenderManager.playerViewX, 1.0F, 0.0F, 0.0F);
      GL11.glScalef(-var11, -var11, var11);
      setGLCap(2896, false);
      setGLCap(2929, false);
      int var16 = Minecraft.fontRendererObj.getStringWidth(var14) / 2;
      setGLCap(3042, true);
      GL11.glBlendFunc(770, 771);
      this.drawBorderedRectNameTag((float)(-var16 - 2), (float)(-(Minecraft.fontRendererObj.FONT_HEIGHT + 9)), (float)(var16 + 2), 2.0F, 1.0F, reAlpha(Color.BLACK.getRGB(), 0.3F), reAlpha(Color.BLACK.getRGB(), 0.3F));
      GL11.glColor3f(1.0F, 1.0F, 1.0F);
      fr.drawString(var14, -var16, -(Minecraft.fontRendererObj.FONT_HEIGHT + 8), -1);
      fr.drawString(var15, -Minecraft.fontRendererObj.getStringWidth(var15) / 2, -(Minecraft.fontRendererObj.FONT_HEIGHT - 2), -1);
      int var17 = (new Color(188, 0, 0)).getRGB();
      if(entity.getHealth() > 20.0F) {
         var17 = -65292;
      }

      float var18 = (float)Math.ceil((double)(entity.getHealth() + entity.getAbsorptionAmount()));
      float var19 = var18 / (entity.getMaxHealth() + entity.getAbsorptionAmount());
      Gui.drawRect((double)((float)var16 + var19 * 40.0F - 40.0F + 2.0F), 2.0D, (double)((float)(-var16) - 1.98F), 0.8999999761581421D, var17);
      GL11.glPushMatrix();
      int var20 = 0;

      for(ItemStack var21 : entity.inventory.armorInventory) {
         if(var21 != null) {
            var20 -= 11;
         }
      }

      if(entity.getHeldItem() != null) {
         var20 = var20 - 8;
         ItemStack var21 = entity.getHeldItem().copy();
         if(var21.hasEffect() && (var21.getItem() instanceof ItemTool || var21.getItem() instanceof ItemArmor)) {
            var21.stackSize = 1;
         }

         this.renderItemStack(var21, var20, -35);
         var20 = var20 + 20;
      }

      for(ItemStack var27 : entity.inventory.armorInventory) {
         if(var27 != null) {
            ItemStack var26 = var27.copy();
            if(var26.hasEffect() && (var26.getItem() instanceof ItemTool || var26.getItem() instanceof ItemArmor)) {
               var26.stackSize = 1;
            }

            this.renderItemStack(var26, var20, -35);
            var20 += 20;
         }
      }

      GL11.glPopMatrix();
      revertAllCaps();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GL11.glPopMatrix();
   }

   public static void revertAllCaps() {
      Iterator localIterator = glCapMap.keySet().iterator();

      while(localIterator.hasNext()) {
         int cap = ((Integer)localIterator.next()).intValue();
         revertGLCap(cap);
      }

   }

   public static void revertGLCap(int cap) {
      Boolean origCap = (Boolean)glCapMap.get(Integer.valueOf(cap));
      if(origCap != null) {
         if(origCap.booleanValue()) {
            GL11.glEnable(cap);
         } else {
            GL11.glDisable(cap);
         }
      }

   }

   public void renderItemStack(ItemStack var1, int var2, int var3) {
      GL11.glPushMatrix();
      GL11.glDepthMask(true);
      GlStateManager.clear(256);
      RenderHelper.enableStandardItemLighting();
      Module.mc.getRenderItem().zLevel = -150.0F;
      this.whatTheFuckOpenGLThisFixesItemGlint();
      Module.mc.getRenderItem().renderItemAndEffectIntoGUI(var1, var2, var3);
      Module.mc.getRenderItem().renderItemOverlays(Minecraft.fontRendererObj, var1, var2, var3);
      Module.mc.getRenderItem().zLevel = 0.0F;
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableCull();
      GlStateManager.enableAlpha();
      GlStateManager.disableBlend();
      GlStateManager.disableLighting();
      GlStateManager.scale(0.5D, 0.5D, 0.5D);
      GlStateManager.disableDepth();
      GlStateManager.enableDepth();
      GlStateManager.scale(2.0F, 2.0F, 2.0F);
      GL11.glPopMatrix();
   }

   public void whatTheFuckOpenGLThisFixesItemGlint() {
      GlStateManager.disableLighting();
      GlStateManager.disableDepth();
      GlStateManager.disableBlend();
      GlStateManager.enableLighting();
      GlStateManager.enableDepth();
      GlStateManager.disableLighting();
      GlStateManager.disableDepth();
      GlStateManager.disableTexture2D();
      GlStateManager.disableAlpha();
      GlStateManager.disableBlend();
      GlStateManager.enableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.enableTexture2D();
      GlStateManager.enableLighting();
      GlStateManager.enableDepth();
   }

   public static void setGLCap(int cap, boolean flag) {
      glCapMap.put(Integer.valueOf(cap), Boolean.valueOf(GL11.glGetBoolean(cap)));
      if(flag) {
         GL11.glEnable(cap);
      } else {
         GL11.glDisable(cap);
      }

   }

   public void drawBorderedRectNameTag(float var1, float var2, float var3, float var4, float var5, int var6, int var7) {
      Gui.drawRect((double)var1, (double)var2, (double)var3, (double)var4, var7);
      float var8 = (float)(var6 >> 24 & 255) / 255.0F;
      float var9 = (float)(var6 >> 16 & 255) / 255.0F;
      float var10 = (float)(var6 >> 8 & 255) / 255.0F;
      float var11 = (float)(var6 & 255) / 255.0F;
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glPushMatrix();
      GL11.glColor4f(var9, var10, var11, var8);
      GL11.glLineWidth(var5);
      GL11.glBegin(1);
      GL11.glVertex2d((double)var1, (double)var2);
      GL11.glVertex2d((double)var1, (double)var4);
      GL11.glVertex2d((double)var3, (double)var4);
      GL11.glVertex2d((double)var3, (double)var2);
      GL11.glVertex2d((double)var1, (double)var2);
      GL11.glVertex2d((double)var3, (double)var2);
      GL11.glVertex2d((double)var1, (double)var4);
      GL11.glVertex2d((double)var3, (double)var4);
      GL11.glEnd();
      GL11.glPopMatrix();
      GL11.glEnable(3553);
      GL11.glDisable(3042);
      GL11.glDisable(2848);
   }

   public static int reAlpha(int color, float alpha) {
      Color c = new Color(color);
      float r = 0.003921569F * (float)c.getRed();
      float g = 0.003921569F * (float)c.getGreen();
      float b = 0.003921569F * (float)c.getBlue();
      return (new Color(r, g, b, alpha)).getRGB();
   }
}
