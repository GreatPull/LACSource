package net.minecraft.entity.player.Really.Client.module.modules.render;

import java.awt.Color;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender2D;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender3D;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.render.Colors;
import net.minecraft.entity.player.Really.Client.utils.render.RenderUtil;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MovingObjectPosition;
import org.lwjgl.opengl.GL11;

public class BlockOverlay extends Module {
   public Numbers r = new Numbers("Red", "Red", Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
   public Numbers g = new Numbers("Green", "Green", Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
   public Numbers b = new Numbers("Blue", "Blue", Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
   public Option togg = new Option("RenderString", "RenderString", Boolean.valueOf(true));

   public BlockOverlay() {
      super("BlockOverlay", new String[]{"BlockOverlay"}, ModuleType.Render);
      this.addValues(new Value[]{this.r, this.g, this.b, this.togg});
   }

   public int getRed() {
      return ((Double)this.r.getValue()).intValue();
   }

   public int getGreen() {
      return ((Double)this.g.getValue()).intValue();
   }

   public int getBlue() {
      return ((Double)this.b.getValue()).intValue();
   }

   public boolean getRender() {
      return ((Boolean)this.togg.getValue()).booleanValue();
   }

   @EventHandler
   public void onRender(EventRender2D event) {
      Minecraft var10000 = mc;
      if(Minecraft.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
         var10000 = mc;
         FontRenderer fr = Minecraft.fontRendererObj;
         var10000 = mc;
         BlockPos pos = Minecraft.objectMouseOver.getBlockPos();
         Block block = Minecraft.theWorld.getBlockState(pos).getBlock();
         int id = Block.getIdFromBlock(block);
         (new StringBuilder(String.valueOf(String.valueOf(String.valueOf(block.getLocalizedName()))))).append(" ID:").append(id).toString();
         String s2 = block.getLocalizedName();
         (new StringBuilder(" ID:")).append(id).toString();
         var10000 = mc;
         if(Minecraft.objectMouseOver != null && this.getRender()) {
            ScaledResolution res = new ScaledResolution(mc);
            int x = res.getScaledWidth() / 2 + 6;
            int y = res.getScaledHeight() / 2 - 1;
            var10000 = mc;
            Minecraft.fontRendererObj.drawStringWithShadow(s2, (float)x + 4.0F, (float)y - 2.65F, Colors.GREY.c);
         }
      }

   }

   public static int reAlpha(int color, float alpha) {
      Color c = new Color(color);
      float r = 0.003921569F * (float)c.getRed();
      float g = 0.003921569F * (float)c.getGreen();
      float b = 0.003921569F * (float)c.getBlue();
      return (new Color(r, g, b, alpha)).getRGB();
   }

   @EventHandler
   public void onRender3D(EventRender3D event) {
      Minecraft var10000 = mc;
      if(Minecraft.objectMouseOver != null) {
         var10000 = mc;
         if(Minecraft.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
            var10000 = mc;
            BlockPos pos = Minecraft.objectMouseOver.getBlockPos();
            Block block = Minecraft.theWorld.getBlockState(pos).getBlock();
            String s = block.getLocalizedName();
            mc.getRenderManager();
            double n = (double)pos.getX();
            mc.getRenderManager();
            double x = n - RenderManager.renderPosX;
            mc.getRenderManager();
            double n2 = (double)pos.getY();
            mc.getRenderManager();
            double y = n2 - RenderManager.renderPosY;
            mc.getRenderManager();
            double n3 = (double)pos.getZ();
            mc.getRenderManager();
            double z = n3 - RenderManager.renderPosZ;
            GL11.glPushMatrix();
            GL11.glEnable(3042);
            GL11.glBlendFunc(770, 771);
            GL11.glDisable(3553);
            GL11.glEnable(2848);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            GL11.glColor4f((float)this.getRed() / 255.0F, (float)this.getGreen() / 255.0F, (float)this.getBlue() / 255.0F, 0.15F);
            double minX = !(block instanceof BlockStairs) && Block.getIdFromBlock(block) != 134?block.getBlockBoundsMinX():0.0D;
            double minY = !(block instanceof BlockStairs) && Block.getIdFromBlock(block) != 134?block.getBlockBoundsMinY():0.0D;
            double minZ = !(block instanceof BlockStairs) && Block.getIdFromBlock(block) != 134?block.getBlockBoundsMinZ():0.0D;
            RenderUtil.drawBoundingBox(new AxisAlignedBB(x + minX, y + minY, z + minZ, x + block.getBlockBoundsMaxX(), y + block.getBlockBoundsMaxY(), z + block.getBlockBoundsMaxZ()));
            GL11.glColor4f((float)this.getRed() / 255.0F, (float)this.getGreen() / 255.0F, (float)this.getBlue() / 255.0F, 1.0F);
            GL11.glLineWidth(0.5F);
            RenderUtil.drawOutlinedBoundingBox(new AxisAlignedBB(x + minX, y + minY, z + minZ, x + block.getBlockBoundsMaxX(), y + block.getBlockBoundsMaxY(), z + block.getBlockBoundsMaxZ()));
            GL11.glDisable(2848);
            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
            GL11.glPopMatrix();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         }
      }

   }
}
