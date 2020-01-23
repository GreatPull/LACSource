package net.minecraft.entity.player.Really.Client.module.modules.render;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender3D;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.XrayBlock;
import net.minecraft.entity.player.Really.Client.utils.render.RenderUtil;

public class Xray extends Module {
   private static final HashSet blockIDs = new HashSet();
   private int opacity = 160;
   public List<Integer> KEY_IDS = Lists.newArrayList((Integer[])(new Integer[]{Integer.valueOf(10), Integer.valueOf(11), Integer.valueOf(8), Integer.valueOf(9), Integer.valueOf(14), Integer.valueOf(15), Integer.valueOf(16), Integer.valueOf(21), Integer.valueOf(41), Integer.valueOf(42), Integer.valueOf(46), Integer.valueOf(48), Integer.valueOf(52), Integer.valueOf(56), Integer.valueOf(57), Integer.valueOf(61), Integer.valueOf(62), Integer.valueOf(73), Integer.valueOf(74), Integer.valueOf(84), Integer.valueOf(89), Integer.valueOf(103), Integer.valueOf(116), Integer.valueOf(117), Integer.valueOf(118), Integer.valueOf(120), Integer.valueOf(129), Integer.valueOf(133), Integer.valueOf(137), Integer.valueOf(145), Integer.valueOf(152), Integer.valueOf(153), Integer.valueOf(154)}));
   public static Numbers OPACITY = new Numbers("OPACITY", "OPACITY", Double.valueOf(160.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(5.0D));
   public static Numbers Dis = new Numbers("Dis", "Dis", Double.valueOf(50.0D), Double.valueOf(0.0D), Double.valueOf(256.0D), Double.valueOf(1.0D));
   public static Option CAVE = new Option("CAVE", "CAVE", Boolean.valueOf(false));
   public static CopyOnWriteArrayList<XrayBlock> list = new CopyOnWriteArrayList();

   public Xray() {
      super("Xray", new String[]{"Xray"}, ModuleType.Render);
      this.addValues(new Value[]{OPACITY, CAVE, Dis});
   }

   public void onEnable() {
      blockIDs.clear();
      list.clear();
      this.opacity = ((Double)OPACITY.getValue()).intValue();

      try {
         for(Integer o : this.KEY_IDS) {
            blockIDs.add(o);
         }
      } catch (Exception var3) {
         var3.printStackTrace();
      }

      Minecraft var10000 = mc;
      Minecraft.renderGlobal.loadRenderers();
   }

   public void onDisable() {
      Minecraft var10000 = mc;
      Minecraft.renderGlobal.loadRenderers();
      list.clear();
   }

   public static boolean containsID(int id) {
      return blockIDs.contains(Integer.valueOf(id));
   }

   @EventHandler
   public void onEvent(EventRender3D event) {
      for(XrayBlock x1 : list) {
         if(x1.type.contains("Diamond")) {
            RenderUtil.drawblock(x1.x - mc.getRenderManager().viewerPosX, x1.y - mc.getRenderManager().viewerPosY, x1.z - mc.getRenderManager().viewerPosZ, getColor(228, 228, 65, 50), (new Color(138, 43, 226)).getRGB(), 2.0F);
         } else if(x1.type.contains("Iron")) {
            RenderUtil.drawblock(x1.x - mc.getRenderManager().viewerPosX, x1.y - mc.getRenderManager().viewerPosY, x1.z - mc.getRenderManager().viewerPosZ, getColor(184, 134, 11), (new Color(0, 0, 255)).getRGB(), 2.0F);
         } else if(x1.type.contains("Gold")) {
            RenderUtil.drawblock(x1.x - mc.getRenderManager().viewerPosX, x1.y - mc.getRenderManager().viewerPosY, x1.z - mc.getRenderManager().viewerPosZ, getColor(184, 134, 11), (new Color(255, 255, 0)).getRGB(), 2.0F);
         } else if(x1.type.contains("Redstone")) {
            RenderUtil.drawblock(x1.x - mc.getRenderManager().viewerPosX, x1.y - mc.getRenderManager().viewerPosY, x1.z - mc.getRenderManager().viewerPosZ, getColor(184, 134, 11), (new Color(255, 0, 0)).getRGB(), 2.0F);
         } else {
            RenderUtil.drawblock(x1.x - mc.getRenderManager().viewerPosX, x1.y - mc.getRenderManager().viewerPosY, x1.z - mc.getRenderManager().viewerPosZ, getColor(228, 228, 65, 50), (new Color(155, 255, 110)).getRGB(), 2.0F);
         }
      }

   }

   public static int getColor(int red, int green, int blue) {
      return getColor(red, green, blue, 255);
   }

   public static int getColor(int red, int green, int blue, int alpha) {
      int color = 0;
      color = color | alpha << 24;
      color = color | red << 16;
      color = color | green << 8;
      color = color | blue;
      return color;
   }

   public int getOpacity() {
      return this.opacity;
   }
}
