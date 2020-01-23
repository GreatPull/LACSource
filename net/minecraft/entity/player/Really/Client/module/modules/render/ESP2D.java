package net.minecraft.entity.player.Really.Client.module.modules.render;

import java.awt.Color;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender2D;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender3D;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.module.modules.player.Teams;
import net.minecraft.entity.player.Really.Client.module.modules.render.Colors2;
import net.minecraft.entity.player.Really.Client.utils.render.RenderUtil;
import org.lwjgl.opengl.GL11;

public class ESP2D extends Module {
   public static Mode mode = new Mode("Mode", "mode", ESP2D.TwoD.values(), ESP2D.TwoD.Box);
   public static Option HEALTH = new Option("Health", "Health", Boolean.valueOf(true));
   public static Option player = new Option("Players", "Players", Boolean.valueOf(true));
   public static Option mobs = new Option("Mobs", "Mobs", Boolean.valueOf(true));
   public static Option animals = new Option("Animals", "Animals", Boolean.valueOf(true));
   public static Option invis = new Option("Invis", "Invis", Boolean.valueOf(true));
   public static Option ARMOR = new Option("Armor", "Armor", Boolean.valueOf(true));
   private Map<Entity, double[]> entityConvertedPointsMap;
   FontRenderer fr;

   public ESP2D() {
      super("ESP2D", new String[0], ModuleType.Render);
      this.addValues(new Value[]{mode, HEALTH, player, mobs, animals, invis, ARMOR});
      this.entityConvertedPointsMap = new HashMap();
      Minecraft var10001 = mc;
      this.fr = Minecraft.fontRendererObj;
      new ArrayList();
   }

   @EventHandler
   public void onRender(EventRender3D event) {
      try {
         this.updatePositions();
      } catch (Exception var3) {
         ;
      }

   }

   @EventHandler
   public void onRender2D(EventRender2D event) {
      GlStateManager.pushMatrix();

      for(Entity entity : this.entityConvertedPointsMap.keySet()) {
         EntityPlayer ent = (EntityPlayer)entity;
         double[] renderPositions = (double[])this.entityConvertedPointsMap.get(ent);
         double[] renderPositionsBottom = new double[]{renderPositions[4], renderPositions[5], renderPositions[6]};
         double[] renderPositionsX = new double[]{renderPositions[7], renderPositions[8], renderPositions[9]};
         double[] renderPositionsX2 = new double[]{renderPositions[10], renderPositions[11], renderPositions[12]};
         double[] renderPositionsZ = new double[]{renderPositions[13], renderPositions[14], renderPositions[15]};
         double[] renderPositionsZ2 = new double[]{renderPositions[16], renderPositions[17], renderPositions[18]};
         double[] renderPositionsTop1 = new double[]{renderPositions[19], renderPositions[20], renderPositions[21]};
         double[] renderPositionsTop2 = new double[]{renderPositions[22], renderPositions[23], renderPositions[24]};
         if(renderPositions[3] > 0.0D && renderPositions[3] <= 1.0D && renderPositionsBottom[2] > 0.0D && renderPositionsBottom[2] <= 1.0D && renderPositionsX[2] > 0.0D && renderPositionsX[2] <= 1.0D && renderPositionsX2[2] > 0.0D && renderPositionsX2[2] <= 1.0D && renderPositionsZ[2] > 0.0D && renderPositionsZ[2] <= 1.0D && renderPositionsZ2[2] > 0.0D && renderPositionsZ2[2] <= 1.0D && renderPositionsTop1[2] > 0.0D && renderPositionsTop1[2] <= 1.0D && renderPositionsTop2[2] > 0.0D && renderPositionsTop2[2] <= 1.0D) {
            boolean var61 = true;
         } else {
            boolean var10000 = false;
         }

         GlStateManager.pushMatrix();
         GlStateManager.scale(0.5D, 0.5D, 0.5D);
         if((((Boolean)invis.getValue()).booleanValue() || !ent.isInvisible()) && ent instanceof EntityPlayer && !(ent instanceof EntityPlayerSP)) {
            try {
               double[] xValues = new double[]{renderPositions[0], renderPositionsBottom[0], renderPositionsX[0], renderPositionsX2[0], renderPositionsZ[0], renderPositionsZ2[0], renderPositionsTop1[0], renderPositionsTop2[0]};
               double[] yValues = new double[]{renderPositions[1], renderPositionsBottom[1], renderPositionsX[1], renderPositionsX2[1], renderPositionsZ[1], renderPositionsZ2[1], renderPositionsTop1[1], renderPositionsTop2[1]};
               double x = renderPositions[0];
               double y = renderPositions[1];
               double endx = renderPositionsBottom[0];
               double endy = renderPositionsBottom[1];

               for(double bdubs : xValues) {
                  if(bdubs < x) {
                     x = bdubs;
                  }
               }

               for(double bdubs : xValues) {
                  if(bdubs > endx) {
                     endx = bdubs;
                  }
               }

               for(double bdubs : yValues) {
                  if(bdubs < y) {
                     y = bdubs;
                  }
               }

               for(double bdubs : yValues) {
                  if(bdubs > endy) {
                     endy = bdubs;
                  }
               }

               double xDiff = (endx - x) / 4.0D;
               double x2Diff = (endx - x) / 4.0D;
               int color = Colors2.getColor(255, 255);
               color = Teams.isOnSameTeam(ent)?Colors2.getColor(0, 255, 0, 255):(ent.hurtTime > 0?Colors2.getColor(255, 0, 0, 255):(ent.isInvisible()?Colors2.getColor(255, 255, 0, 255):Colors2.getColor(255, 255, 255, 255)));
               if(mode.getValue() == ESP2D.TwoD.Box) {
                  RenderUtil.rectangleBordered(x + 0.5D, y + 0.5D, endx - 0.5D, endy - 0.5D, 1.0D, Colors2.getColor(0, 0, 0, 0), color);
                  RenderUtil.rectangleBordered(x - 0.5D, y - 0.5D, endx + 0.5D, endy + 0.5D, 1.0D, Colors2.getColor(0, 0), Colors2.getColor(0, 150));
                  RenderUtil.rectangleBordered(x + 1.5D, y + 1.5D, endx - 1.5D, endy - 1.5D, 1.0D, Colors2.getColor(0, 0), Colors2.getColor(0, 150));
               }

               if(mode.getValue() == ESP2D.TwoD.CornerB) {
                  RenderUtil.rectangle(x + 0.5D, y + 0.5D, x + 1.5D, y + xDiff + 0.5D, color);
                  RenderUtil.rectangle(x + 0.5D, endy - 0.5D, x + 1.5D, endy - xDiff - 0.5D, color);
                  RenderUtil.rectangle(x - 0.5D, y + 0.5D, x + 0.5D, y + xDiff + 0.5D, Colors2.getColor(0, 150));
                  RenderUtil.rectangle(x + 1.5D, y + 2.5D, x + 2.5D, y + xDiff + 0.5D, Colors2.getColor(0, 150));
                  RenderUtil.rectangle(x - 0.5D, y + xDiff + 0.5D, x + 2.5D, y + xDiff + 1.5D, Colors2.getColor(0, 150));
                  RenderUtil.rectangle(x - 0.5D, endy - 0.5D, x + 0.5D, endy - xDiff - 0.5D, Colors2.getColor(0, 150));
                  RenderUtil.rectangle(x + 1.5D, endy - 2.5D, x + 2.5D, endy - xDiff - 0.5D, Colors2.getColor(0, 150));
                  RenderUtil.rectangle(x - 0.5D, endy - xDiff - 0.5D, x + 2.5D, endy - xDiff - 1.5D, Colors2.getColor(0, 150));
                  RenderUtil.rectangle(x + 1.0D, y + 0.5D, x + x2Diff, y + 1.5D, color);
                  RenderUtil.rectangle(x - 0.5D, y - 0.5D, x + x2Diff, y + 0.5D, Colors2.getColor(0, 150));
                  RenderUtil.rectangle(x + 1.5D, y + 1.5D, x + x2Diff, y + 2.5D, Colors2.getColor(0, 150));
                  RenderUtil.rectangle(x + x2Diff, y - 0.5D, x + x2Diff + 1.0D, y + 2.5D, Colors2.getColor(0, 150));
                  RenderUtil.rectangle(x + 1.0D, endy - 0.5D, x + x2Diff, endy - 1.5D, color);
                  RenderUtil.rectangle(x - 0.5D, endy + 0.5D, x + x2Diff, endy - 0.5D, Colors2.getColor(0, 150));
                  RenderUtil.rectangle(x + 1.5D, endy - 1.5D, x + x2Diff, endy - 2.5D, Colors2.getColor(0, 150));
                  RenderUtil.rectangle(x + x2Diff, endy + 0.5D, x + x2Diff + 1.0D, endy - 2.5D, Colors2.getColor(0, 150));
                  RenderUtil.rectangle(endx - 0.5D, y + 0.5D, endx - 1.5D, y + xDiff + 0.5D, color);
                  RenderUtil.rectangle(endx - 0.5D, endy - 0.5D, endx - 1.5D, endy - xDiff - 0.5D, color);
                  RenderUtil.rectangle(endx + 0.5D, y + 0.5D, endx - 0.5D, y + xDiff + 0.5D, Colors2.getColor(0, 150));
                  RenderUtil.rectangle(endx - 1.5D, y + 2.5D, endx - 2.5D, y + xDiff + 0.5D, Colors2.getColor(0, 150));
                  RenderUtil.rectangle(endx + 0.5D, y + xDiff + 0.5D, endx - 2.5D, y + xDiff + 1.5D, Colors2.getColor(0, 150));
                  RenderUtil.rectangle(endx + 0.5D, endy - 0.5D, endx - 0.5D, endy - xDiff - 0.5D, Colors2.getColor(0, 150));
                  RenderUtil.rectangle(endx - 1.5D, endy - 2.5D, endx - 2.5D, endy - xDiff - 0.5D, Colors2.getColor(0, 150));
                  RenderUtil.rectangle(endx + 0.5D, endy - xDiff - 0.5D, endx - 2.5D, endy - xDiff - 1.5D, Colors2.getColor(0, 150));
                  RenderUtil.rectangle(endx - 1.0D, y + 0.5D, endx - x2Diff, y + 1.5D, color);
                  RenderUtil.rectangle(endx + 0.5D, y - 0.5D, endx - x2Diff, y + 0.5D, Colors2.getColor(0, 150));
                  RenderUtil.rectangle(endx - 1.5D, y + 1.5D, endx - x2Diff, y + 2.5D, Colors2.getColor(0, 150));
                  RenderUtil.rectangle(endx - x2Diff, y - 0.5D, endx - x2Diff - 1.0D, y + 2.5D, Colors2.getColor(0, 150));
                  RenderUtil.rectangle(endx - 1.0D, endy - 0.5D, endx - x2Diff, endy - 1.5D, color);
                  RenderUtil.rectangle(endx + 0.5D, endy + 0.5D, endx - x2Diff, endy - 0.5D, Colors2.getColor(0, 150));
                  RenderUtil.rectangle(endx - 1.5D, endy - 1.5D, endx - x2Diff, endy - 2.5D, Colors2.getColor(0, 150));
                  RenderUtil.rectangle(endx - x2Diff, endy + 0.5D, endx - x2Diff - 1.0D, endy - 2.5D, Colors2.getColor(0, 150));
               }

               mode.getValue();
               if(((Boolean)HEALTH.getValue()).booleanValue()) {
                  float health = ent.getHealth();
                  float[] fractions = new float[]{0.0F, 0.5F, 1.0F};
                  Color[] colors = new Color[]{Color.RED, Color.YELLOW, Color.GREEN};
                  float progress = health / ent.getMaxHealth();
                  Color customColor = health >= 0.0F?blendColors(fractions, colors, progress).brighter():Color.RED;
                  double difference = y - endy + 0.5D;
                  double healthLocation = endy + difference * (double)progress;
                  RenderUtil.rectangleBordered(x - 6.5D, y - 0.5D, x - 2.5D, endy, 1.0D, Colors2.getColor(0, 100), Colors2.getColor(0, 150));
                  RenderUtil.rectangle(x - 5.5D, endy - 1.0D, x - 3.5D, healthLocation, customColor.getRGB());
                  if(-difference > 50.0D) {
                     for(int i = 1; i < 10; ++i) {
                        double dThing = difference / 10.0D * (double)i;
                        RenderUtil.rectangle(x - 6.5D, endy - 0.5D + dThing, x - 2.5D, endy - 0.5D + dThing - 1.0D, Colors2.getColor(0));
                     }
                  }

                  if((int)getIncremental((double)(progress * 100.0F), 1.0D) <= 40) {
                     GlStateManager.pushMatrix();
                     GlStateManager.scale(2.0F, 2.0F, 2.0F);
                     String nigger = String.valueOf((int)getIncremental((double)(health * 5.0F), 1.0D)) + "HP";
                     GlStateManager.popMatrix();
                  }
               }
            } catch (Exception var44) {
               ;
            }
         }

         GlStateManager.popMatrix();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      }

      GL11.glScalef(1.0F, 1.0F, 1.0F);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
      RenderUtil.rectangle(0.0D, 0.0D, 0.0D, 0.0D, -1);
   }

   public static double getIncremental(double val, double inc) {
      double one = 1.0D / inc;
      return (double)Math.round(val * one) / one;
   }

   public static Color blendColors(float[] fractions, Color[] colors, float progress) {
      Object color = null;
      if(fractions == null) {
         throw new IllegalArgumentException("Fractions can\'t be null");
      } else if(colors == null) {
         throw new IllegalArgumentException("Colours can\'t be null");
      } else if(fractions.length != colors.length) {
         throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
      } else {
         int[] indicies = getFractionIndicies(fractions, progress);
         float[] range = new float[]{fractions[indicies[0]], fractions[indicies[1]]};
         Color[] colorRange = new Color[]{colors[indicies[0]], colors[indicies[1]]};
         float max = range[1] - range[0];
         float value = progress - range[0];
         float weight = value / max;
         return blend(colorRange[0], colorRange[1], (double)(1.0F - weight));
      }
   }

   public static Color blend(Color color1, Color color2, double ratio) {
      float r = (float)ratio;
      float ir = 1.0F - r;
      float[] rgb1 = new float[3];
      float[] rgb2 = new float[3];
      color1.getColorComponents(rgb1);
      color2.getColorComponents(rgb2);
      float red = rgb1[0] * r + rgb2[0] * ir;
      float green = rgb1[1] * r + rgb2[1] * ir;
      float blue = rgb1[2] * r + rgb2[2] * ir;
      if(red < 0.0F) {
         red = 0.0F;
      } else if(red > 255.0F) {
         red = 255.0F;
      }

      if(green < 0.0F) {
         green = 0.0F;
      } else if(green > 255.0F) {
         green = 255.0F;
      }

      if(blue < 0.0F) {
         blue = 0.0F;
      } else if(blue > 255.0F) {
         blue = 255.0F;
      }

      Color color3 = null;

      try {
         color3 = new Color(red, green, blue);
      } catch (IllegalArgumentException var14) {
         NumberFormat nf = NumberFormat.getNumberInstance();
         System.out.println(String.valueOf(String.valueOf(nf.format((double)red))) + "; " + nf.format((double)green) + "; " + nf.format((double)blue));
         var14.printStackTrace();
      }

      return color3;
   }

   public static int[] getFractionIndicies(float[] fractions, float progress) {
      int[] range = new int[2];

      int startPoint;
      for(startPoint = 0; startPoint < fractions.length && fractions[startPoint] <= progress; ++startPoint) {
         ;
      }

      if(startPoint >= fractions.length) {
         startPoint = fractions.length - 1;
      }

      range[0] = startPoint - 1;
      range[1] = startPoint;
      return range;
   }

   private void updatePositions() {
      this.entityConvertedPointsMap.clear();
      float pTicks = mc.timer.renderPartialTicks;

      for(Entity e2 : Minecraft.theWorld.getLoadedEntityList()) {
         EntityPlayer ent;
         if(e2 instanceof EntityPlayer && (ent = (EntityPlayer)e2) != Minecraft.thePlayer) {
            double x = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)pTicks - mc.getRenderManager().viewerPosX + 0.36D;
            double y = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * (double)pTicks - mc.getRenderManager().viewerPosY;
            double z = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)pTicks - mc.getRenderManager().viewerPosZ + 0.36D;
            double topY;
            y = topY = y + (double)ent.height + 0.15D;
            double[] convertedPoints = RenderUtil.convertTo2D(x, y, z);
            double[] convertedPoints2 = RenderUtil.convertTo2D(x - 0.36D, y, z - 0.36D);
            double xd = 0.0D;
            if(convertedPoints2[2] >= 0.0D && convertedPoints2[2] < 1.0D) {
               x = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)pTicks - mc.getRenderManager().viewerPosX - 0.36D;
               z = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)pTicks - mc.getRenderManager().viewerPosZ - 0.36D;
               double[] convertedPointsBottom = RenderUtil.convertTo2D(x, y, z);
               y = ent.lastTickPosY + (ent.posY - ent.lastTickPosY) * (double)pTicks - mc.getRenderManager().viewerPosY - 0.05D;
               double[] convertedPointsx = RenderUtil.convertTo2D(x, y, z);
               x = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)pTicks - mc.getRenderManager().viewerPosX - 0.36D;
               z = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)pTicks - mc.getRenderManager().viewerPosZ + 0.36D;
               double[] convertedPointsTop1 = RenderUtil.convertTo2D(x, topY, z);
               double[] convertedPointsx2 = RenderUtil.convertTo2D(x, y, z);
               x = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)pTicks - mc.getRenderManager().viewerPosX + 0.36D;
               z = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)pTicks - mc.getRenderManager().viewerPosZ + 0.36D;
               double[] convertedPointsz = RenderUtil.convertTo2D(x, y, z);
               x = ent.lastTickPosX + (ent.posX - ent.lastTickPosX) * (double)pTicks - mc.getRenderManager().viewerPosX + 0.36D;
               z = ent.lastTickPosZ + (ent.posZ - ent.lastTickPosZ) * (double)pTicks - mc.getRenderManager().viewerPosZ - 0.36D;
               double[] convertedPointsTop2 = RenderUtil.convertTo2D(x, topY, z);
               double[] convertedPointsz2 = RenderUtil.convertTo2D(x, y, z);
               this.entityConvertedPointsMap.put(ent, new double[]{convertedPoints[0], convertedPoints[1], 0.0D, convertedPoints[2], convertedPointsBottom[0], convertedPointsBottom[1], convertedPointsBottom[2], convertedPointsx[0], convertedPointsx[1], convertedPointsx[2], convertedPointsx2[0], convertedPointsx2[1], convertedPointsx2[2], convertedPointsz[0], convertedPointsz[1], convertedPointsz[2], convertedPointsz2[0], convertedPointsz2[1], convertedPointsz2[2], convertedPointsTop1[0], convertedPointsTop1[1], convertedPointsTop1[2], convertedPointsTop2[0], convertedPointsTop2[1], convertedPointsTop2[2]});
            }
         }
      }

   }

   private String getColor(int level) {
      return level == 2?"§a":(level == 3?"§3":(level == 4?"§4":(level >= 5?"§6":"§f")));
   }

   static enum TwoD {
      Box,
      CornerA,
      CornerB;
   }
}
