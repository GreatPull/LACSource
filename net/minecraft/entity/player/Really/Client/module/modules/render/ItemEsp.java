package net.minecraft.entity.player.Really.Client.module.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender3D;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.render.RenderUtil;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

public class ItemEsp extends Module {
   public static Option outlinedboundingBox = new Option("OutlinedBoundingBox", "OutlinedBoundingBox", Boolean.valueOf(true));
   public static Option boundingBox = new Option("BoundingBox", "BoundingBox", Boolean.valueOf(true));
   public static Mode heigh = new Mode("Height", "Height", ItemEsp.height.values(), ItemEsp.height.High);

   public ItemEsp() {
      super("ItemESP", new String[]{"ItemESP"}, ModuleType.Render);
      this.addValues(new Value[]{outlinedboundingBox, boundingBox, heigh});
   }

   @EventHandler
   public void onRender(EventRender3D event) {
      Minecraft var100001 = mc;

      for(Object o : Minecraft.theWorld.loadedEntityList) {
         if(o instanceof EntityItem) {
            EntityItem item = (EntityItem)o;
            double var10000 = item.posX;
            Minecraft.getMinecraft().getRenderManager();
            double x = var10000 - RenderManager.renderPosX;
            var10000 = item.posY + 0.5D;
            Minecraft.getMinecraft().getRenderManager();
            double y = var10000 - RenderManager.renderPosY;
            var10000 = item.posZ;
            Minecraft.getMinecraft().getRenderManager();
            double z = var10000 - RenderManager.renderPosZ;
            GL11.glEnable(3042);
            GL11.glLineWidth(2.0F);
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.75F);
            GL11.glDisable(3553);
            GL11.glDisable(2929);
            GL11.glDepthMask(false);
            if(((Boolean)outlinedboundingBox.getValue()).booleanValue() && heigh.getValue() == ItemEsp.height.High) {
               RenderUtil.drawOutlinedBoundingBox(new AxisAlignedBB(x - 0.2D, y - 0.05D, z - 0.2D, x + 0.2D, y - 0.45D, z + 0.2D));
            }

            if(((Boolean)outlinedboundingBox.getValue()).booleanValue() && heigh.getValue() == ItemEsp.height.Low) {
               RenderUtil.drawOutlinedBoundingBox(new AxisAlignedBB(x - 0.2D, y - 0.3D, z - 0.2D, x + 0.2D, y - 0.4D, z + 0.2D));
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.15F);
            if(((Boolean)boundingBox.getValue()).booleanValue() && heigh.getValue() == ItemEsp.height.High) {
               RenderUtil.drawBoundingBox(new AxisAlignedBB(x - 0.2D, y - 0.05D, z - 0.2D, x + 0.2D, y - 0.45D, z + 0.2D));
            }

            GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.15F);
            if(((Boolean)boundingBox.getValue()).booleanValue() && heigh.getValue() == ItemEsp.height.Low) {
               RenderUtil.drawBoundingBox(new AxisAlignedBB(x - 0.2D, y - 0.3D, z - 0.2D, x + 0.2D, y - 0.4D, z + 0.2D));
            }

            GL11.glEnable(3553);
            GL11.glEnable(2929);
            GL11.glDepthMask(true);
            GL11.glDisable(3042);
         }
      }

   }

   static enum height {
      High,
      Low;
   }
}
