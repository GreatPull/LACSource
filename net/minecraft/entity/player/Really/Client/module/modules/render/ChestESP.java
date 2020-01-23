package net.minecraft.entity.player.Really.Client.module.modules.render;

import java.awt.Color;
import javax.vecmath.Vector3d;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender3D;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.math.Vec4f;
import net.minecraft.entity.player.Really.Client.utils.render.GLUProjection;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBrewingStand;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityDispenser;
import net.minecraft.tileentity.TileEntityDropper;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import org.lwjgl.opengl.GL11;

public class ChestESP extends Module {
   public ChestESP() {
      super("ChestESP", new String[]{"chesthack"}, ModuleType.Render);
      this.setColor((new Color(90, 209, 165)).getRGB());
   }

   @EventHandler
   public void onRender(EventRender3D eventRender) {
      ScaledResolution scaledResolution = new ScaledResolution(mc);
      Minecraft var10000 = mc;

      for(Object o : Minecraft.theWorld.loadedTileEntityList) {
         TileEntity tileEntity = (TileEntity)o;
         if(tileEntity != null && isStorage(tileEntity)) {
            double posX = (double)tileEntity.getPos().getX();
            double posY = (double)tileEntity.getPos().getY();
            double posZ = (double)tileEntity.getPos().getZ();
            AxisAlignedBB axisAlignedBB = null;
            if(tileEntity instanceof TileEntityChest) {
               var10000 = mc;
               Block block = Minecraft.theWorld.getBlockState(new BlockPos(posX, posY, posZ)).getBlock();
               var10000 = mc;
               Block x1 = Minecraft.theWorld.getBlockState(new BlockPos(posX + 1.0D, posY, posZ)).getBlock();
               var10000 = mc;
               Block x2 = Minecraft.theWorld.getBlockState(new BlockPos(posX - 1.0D, posY, posZ)).getBlock();
               var10000 = mc;
               Block z1 = Minecraft.theWorld.getBlockState(new BlockPos(posX, posY, posZ + 1.0D)).getBlock();
               var10000 = mc;
               Block z2 = Minecraft.theWorld.getBlockState(new BlockPos(posX, posY, posZ - 1.0D)).getBlock();
               if(block != Blocks.trapped_chest) {
                  if(x1 == Blocks.chest) {
                     mc.getRenderManager();
                     mc.getRenderManager();
                     mc.getRenderManager();
                     mc.getRenderManager();
                     mc.getRenderManager();
                     mc.getRenderManager();
                     axisAlignedBB = new AxisAlignedBB(posX + 0.05000000074505806D - RenderManager.renderPosX, posY - RenderManager.renderPosY, posZ + 0.05000000074505806D - RenderManager.renderPosZ, posX + 1.9500000476837158D - RenderManager.renderPosX, posY + 0.8999999761581421D - RenderManager.renderPosY, posZ + 0.949999988079071D - RenderManager.renderPosZ);
                  } else if(z2 == Blocks.chest) {
                     mc.getRenderManager();
                     mc.getRenderManager();
                     mc.getRenderManager();
                     mc.getRenderManager();
                     mc.getRenderManager();
                     mc.getRenderManager();
                     axisAlignedBB = new AxisAlignedBB(posX + 0.05000000074505806D - RenderManager.renderPosX, posY - RenderManager.renderPosY, posZ + 0.05000000074505806D - RenderManager.renderPosZ - 1.0D, posX + 0.949999988079071D - RenderManager.renderPosX, posY + 0.8999999761581421D - RenderManager.renderPosY, posZ + 0.949999988079071D - RenderManager.renderPosZ);
                  } else if(x1 != Blocks.chest && x2 != Blocks.chest && z1 != Blocks.chest && z2 != Blocks.chest) {
                     mc.getRenderManager();
                     mc.getRenderManager();
                     mc.getRenderManager();
                     mc.getRenderManager();
                     mc.getRenderManager();
                     mc.getRenderManager();
                     axisAlignedBB = new AxisAlignedBB(posX + 0.05000000074505806D - RenderManager.renderPosX, posY - RenderManager.renderPosY, posZ + 0.05000000074505806D - RenderManager.renderPosZ, posX + 0.949999988079071D - RenderManager.renderPosX, posY + 0.8999999761581421D - RenderManager.renderPosY, posZ + 0.949999988079071D - RenderManager.renderPosZ);
                  }
               } else if(x1 == Blocks.trapped_chest) {
                  mc.getRenderManager();
                  mc.getRenderManager();
                  mc.getRenderManager();
                  mc.getRenderManager();
                  mc.getRenderManager();
                  mc.getRenderManager();
                  axisAlignedBB = new AxisAlignedBB(posX + 0.05000000074505806D - RenderManager.renderPosX, posY - RenderManager.renderPosY, posZ + 0.05000000074505806D - RenderManager.renderPosZ, posX + 1.9500000476837158D - RenderManager.renderPosX, posY + 0.8999999761581421D - RenderManager.renderPosY, posZ + 0.949999988079071D - RenderManager.renderPosZ);
               } else if(z2 == Blocks.trapped_chest) {
                  mc.getRenderManager();
                  mc.getRenderManager();
                  mc.getRenderManager();
                  mc.getRenderManager();
                  mc.getRenderManager();
                  mc.getRenderManager();
                  axisAlignedBB = new AxisAlignedBB(posX + 0.05000000074505806D - RenderManager.renderPosX, posY - RenderManager.renderPosY, posZ + 0.05000000074505806D - RenderManager.renderPosZ - 1.0D, posX + 0.949999988079071D - RenderManager.renderPosX, posY + 0.8999999761581421D - RenderManager.renderPosY, posZ + 0.949999988079071D - RenderManager.renderPosZ);
               } else if(x1 != Blocks.trapped_chest && x2 != Blocks.trapped_chest && z1 != Blocks.trapped_chest && z2 != Blocks.trapped_chest) {
                  mc.getRenderManager();
                  mc.getRenderManager();
                  mc.getRenderManager();
                  mc.getRenderManager();
                  mc.getRenderManager();
                  mc.getRenderManager();
                  axisAlignedBB = new AxisAlignedBB(posX + 0.05000000074505806D - RenderManager.renderPosX, posY - RenderManager.renderPosY, posZ + 0.05000000074505806D - RenderManager.renderPosZ, posX + 0.949999988079071D - RenderManager.renderPosX, posY + 0.8999999761581421D - RenderManager.renderPosY, posZ + 0.949999988079071D - RenderManager.renderPosZ);
               }
            } else {
               mc.getRenderManager();
               mc.getRenderManager();
               mc.getRenderManager();
               mc.getRenderManager();
               mc.getRenderManager();
               mc.getRenderManager();
               axisAlignedBB = new AxisAlignedBB(posX - RenderManager.renderPosX, posY - RenderManager.renderPosY, posZ - RenderManager.renderPosZ, posX + 1.0D - RenderManager.renderPosX, posY + 1.0D - RenderManager.renderPosY, posZ + 1.0D - RenderManager.renderPosZ);
            }

            if(axisAlignedBB != null) {
               float[] colors = this.getColorForTileEntity(tileEntity);
               GlStateManager.disableAlpha();
               GlStateManager.enableBlend();
               GlStateManager.blendFunc(770, 771);
               GlStateManager.disableTexture2D();
               GlStateManager.disableDepth();
               GL11.glEnable(2848);
               RenderHelper.drawCompleteBox(axisAlignedBB, 1.0F, toRGBAHex(colors[0] / 255.0F, colors[1] / 255.0F, colors[2] / 255.0F, 0.1254902F), toRGBAHex(colors[0] / 255.0F, colors[1] / 255.0F, colors[2] / 255.0F, 1.0F));
               GL11.glDisable(2848);
               GlStateManager.enableDepth();
               GlStateManager.enableTexture2D();
               GlStateManager.enableBlend();
               GlStateManager.enableAlpha();
               AxisAlignedBB bb = null;
               if(tileEntity instanceof TileEntityChest) {
                  var10000 = mc;
                  Block block = Minecraft.theWorld.getBlockState(new BlockPos(posX, posY, posZ)).getBlock();
                  var10000 = mc;
                  Block posX1 = Minecraft.theWorld.getBlockState(new BlockPos(posX + 1.0D, posY, posZ)).getBlock();
                  var10000 = mc;
                  Block posX2 = Minecraft.theWorld.getBlockState(new BlockPos(posX - 1.0D, posY, posZ)).getBlock();
                  var10000 = mc;
                  Block posZ1 = Minecraft.theWorld.getBlockState(new BlockPos(posX, posY, posZ + 1.0D)).getBlock();
                  var10000 = mc;
                  Block posZ2 = Minecraft.theWorld.getBlockState(new BlockPos(posX, posY, posZ - 1.0D)).getBlock();
                  if(block != Blocks.trapped_chest) {
                     if(posX1 == Blocks.chest) {
                        bb = new AxisAlignedBB(posX + 0.05000000074505806D, posY, posZ + 0.05000000074505806D, posX + 1.9500000476837158D, posY + 0.8999999761581421D, posZ + 0.949999988079071D);
                     } else if(posZ2 == Blocks.chest) {
                        bb = new AxisAlignedBB(posX + 0.05000000074505806D, posY, posZ + 0.05000000074505806D - 1.0D, posX + 0.949999988079071D, posY + 0.8999999761581421D, posZ + 0.949999988079071D);
                     } else if(posX1 != Blocks.chest && posX2 != Blocks.chest && posZ1 != Blocks.chest && posZ2 != Blocks.chest) {
                        bb = new AxisAlignedBB(posX + 0.05000000074505806D, posY, posZ + 0.05000000074505806D, posX + 0.949999988079071D, posY + 0.8999999761581421D, posZ + 0.949999988079071D);
                     }
                  } else if(posX1 == Blocks.trapped_chest) {
                     bb = new AxisAlignedBB(posX + 0.05000000074505806D, posY, posZ + 0.05000000074505806D, posX + 1.9500000476837158D, posY + 0.8999999761581421D, posZ + 0.949999988079071D);
                  } else if(posZ2 == Blocks.trapped_chest) {
                     bb = new AxisAlignedBB(posX + 0.05000000074505806D, posY, posZ + 0.05000000074505806D - 1.0D, posX + 0.949999988079071D, posY + 0.8999999761581421D, posZ + 0.949999988079071D);
                  } else if(posX1 != Blocks.trapped_chest && posX2 != Blocks.trapped_chest && posZ1 != Blocks.trapped_chest && posZ2 != Blocks.trapped_chest) {
                     bb = new AxisAlignedBB(posX + 0.05000000074505806D, posY, posZ + 0.05000000074505806D, posX + 0.949999988079071D, posY + 0.8999999761581421D, posZ + 0.949999988079071D);
                  }
               } else {
                  bb = new AxisAlignedBB(posX, posY, posZ, posX + 1.0D, posY + 1.0D, posZ + 1.0D);
               }

               if(bb == null) {
                  break;
               }

               Vector3d[] corners = new Vector3d[]{new Vector3d(bb.minX, bb.minY, bb.minZ), new Vector3d(bb.maxX, bb.maxY, bb.maxZ), new Vector3d(bb.minX, bb.maxY, bb.maxZ), new Vector3d(bb.minX, bb.minY, bb.maxZ), new Vector3d(bb.maxX, bb.minY, bb.maxZ), new Vector3d(bb.maxX, bb.minY, bb.minZ), new Vector3d(bb.maxX, bb.maxY, bb.minZ), new Vector3d(bb.minX, bb.maxY, bb.minZ)};
               GLUProjection.Projection result = null;
               Vec4f transformed = new Vec4f((float)scaledResolution.getScaledWidth() * 2.0F, (float)scaledResolution.getScaledHeight() * 2.0F, -1.0F, -1.0F);

               for(Vector3d vec : corners) {
                  result = GLUProjection.getInstance().project(vec.x - mc.getRenderManager().viewerPosX, vec.y - mc.getRenderManager().viewerPosY, vec.z - mc.getRenderManager().viewerPosZ, GLUProjection.ClampMode.NONE, true);
                  transformed.setX((float)Math.min((double)transformed.getX(), result.getX()));
                  transformed.setY((float)Math.min((double)transformed.getY(), result.getY()));
                  transformed.setW((float)Math.max((double)transformed.getW(), result.getX()));
                  transformed.setH((float)Math.max((double)transformed.getH(), result.getY()));
               }

               if(result == null) {
                  break;
               }
            }
         }
      }

   }

   public static boolean isStorage(TileEntity entity) {
      return entity instanceof TileEntityChest || entity instanceof TileEntityBrewingStand || entity instanceof TileEntityDropper || entity instanceof TileEntityDispenser || entity instanceof TileEntityFurnace || entity instanceof TileEntityHopper || entity instanceof TileEntityEnderChest;
   }

   public static int toRGBAHex(float r, float g, float b, float a) {
      return ((int)(a * 255.0F) & 255) << 24 | ((int)(r * 255.0F) & 255) << 16 | ((int)(g * 255.0F) & 255) << 8 | (int)(b * 255.0F) & 255;
   }

   private float[] getColorForTileEntity(TileEntity entity) {
      if(entity instanceof TileEntityChest) {
         TileEntityChest chest = (TileEntityChest)entity;
         if(chest.getChestType() == 0) {
            return new float[]{180.0F, 160.0F, 0.0F, 255.0F};
         }

         if(chest.getChestType() == 1) {
            return new float[]{160.0F, 10.0F, 10.0F, 255.0F};
         }
      }

      return entity instanceof TileEntityEnderChest?new float[]{0.0F, 160.0F, 100.0F, 255.0F}:(entity instanceof TileEntityFurnace?new float[]{120.0F, 120.0F, 120.0F, 255.0F}:new float[]{255.0F, 255.0F, 255.0F, 255.0F});
   }
}
