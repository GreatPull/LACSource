package net.minecraft.entity.player.Really.Client.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender2D;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.management.ModuleManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.module.modules.render.setColor;
import net.minecraft.entity.player.Really.Client.module.modules.render.UI.TabUI;
import net.minecraft.entity.player.Really.Client.ui.font.CFontRenderer;
import net.minecraft.entity.player.Really.Client.ui.font.FontLoaders;
import net.minecraft.entity.player.Really.Client.utils.RenderUtils;
import net.minecraft.entity.player.Really.Client.utils.render.RenderUtil;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class HUD extends Module {
   public TabUI tabui;
   private Numbers customrainbow = new Numbers("RainbowSpeed", "RainbowSpeed", Double.valueOf(0.5D), Double.valueOf(0.0D), Double.valueOf(1.0D), Double.valueOf(0.1D));
   public static Numbers red = new Numbers("Red", "Red", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
   public static Numbers green = new Numbers("Green", "Green", Double.valueOf(125.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
   public static Numbers blue = new Numbers("Blue", "Blue", Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
   public static Numbers bg = new Numbers("background", "background", Double.valueOf(50.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
   public static Option Sound = new Option("Sound", "Sound", Boolean.valueOf(true));
   private Option info = new Option("Information", "Info", Boolean.valueOf(true));
   public static Option DisRender = new Option("DisRender", "DisRender", Boolean.valueOf(true));
   private Option rainbow = new Option("Rainbow", "Rainbow", Boolean.valueOf(true));
   private Option Tpgui = new Option("Tpgui", "Tpgui", Boolean.valueOf(false));
   private Option CHENXI = new Option("CHENXI", "CHENXI", Boolean.valueOf(true));
   public static Option Logo = new Option("Logo", "Logo", Boolean.valueOf(false));
   private Mode mode = new Mode("Mode", "mode", HUD.fontMode.values(), HUD.fontMode.Vanilla);
   private Option Helte = new Option("Helte", "Helte", Boolean.valueOf(false));
   private Option VersionRainbow = new Option("VersionRainbow", "VersionRainbow", Boolean.valueOf(false));
   private Option TitleRainbow = new Option("TitleRainbow", "TitleRainbow", Boolean.valueOf(false));
   public static boolean shouldMove;
   public static boolean useFont;
   private String[] directions = new String[]{"S", "SW", "W", "NW", "N", "NE", "E", "SE"};

   public HUD() {
      super("HUD", new String[]{"gui"}, ModuleType.Render);
      this.setColor((new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255))).getRGB());
      this.addValues(new Value[]{this.info, this.mode, DisRender, Sound, bg, this.customrainbow, red, green, blue, this.CHENXI, this.VersionRainbow, this.TitleRainbow, Logo});

   }

   @EventHandler
   private void renderHud(EventRender2D event) {
      CFontRenderer font = FontLoaders.sansation18;
      CFontRenderer mfont = FontLoaders.sansation16;
      CFontRenderer bigfont = FontLoaders.sansation28;
      useFont = true;
      Minecraft var10000 = mc;
      if(!Minecraft.gameSettings.showDebugInfo) {
         if(((Boolean)this.CHENXI.getValue()).booleanValue()) {
            RenderUtils.drawImage(new ResourceLocation("LAC/ChenXi.jpg"), 0, RenderUtil.height() / 2 - 85, 128, 171);
         }

         shouldMove = true;
         GlStateManager.enableAlpha();
         GlStateManager.enableBlend();
         GlStateManager.disableAlpha();
         GlStateManager.disableBlend();
         if(useFont) {
            shouldMove = true;
            if(useFont) {
               int rainbowTick = 0;
               Minecraft var10002 = mc;
               Color rainbow = new Color(Color.HSBtoRGB((float)((double)Minecraft.thePlayer.ticksExisted / 50.0D + Math.sin((double)rainbowTick / 50.0D * 1.6D)) % 1.0F, 0.5F, 1.0F));
               Client.instance.getClass();
               new ScaledResolution(mc);
               if(((Boolean)Logo.getValue()).booleanValue()) {
                  RenderUtils.drawImage(new ResourceLocation("LAC/logo.png"), 2, 4, 80, 80);
               } else {
                  bigfont.drawStringWithShadow(Client.ClientName.substring(0, 1), 2.0D, 4.0D, ((Boolean)this.TitleRainbow.getValue()).booleanValue()?rainbow.getRGB():(new Color(((Double)setColor.r.getValue()).intValue(), ((Double)setColor.g.getValue()).intValue(), ((Double)setColor.b.getValue()).intValue())).getRGB());
                  bigfont.drawStringWithShadow(Client.ClientName.substring(1), (double)(2 + bigfont.getStringWidth(Client.ClientName.substring(0, 1))), 4.0D, ((Boolean)this.TitleRainbow.getValue()).booleanValue()?rainbow.getRGB():Color.WHITE.getRGB());
                  font.drawStringWithShadow(" " + Client.ClientVersion, (double)(2 + bigfont.getStringWidth(Client.ClientName)), 4.0D, ((Boolean)this.VersionRainbow.getValue()).booleanValue()?rainbow.getRGB():Color.GRAY.getRGB());
               }

               ++rainbowTick;
               if(rainbowTick > 50) {
                  rainbowTick = 0;
               }
            }

            ArrayList<Module> sorted = new ArrayList();

            for(Module m2 : ModuleManager.getModules()) {
               if(m2.isEnabled() && !m2.wasRemoved()) {
                  sorted.add(m2);
               }
            }

            if(useFont) {
               sorted.sort((o1, o2) -> {
                  return font.getStringWidth(o2.getSuffix().isEmpty()?o2.getName():String.format("%s%s", new Object[]{o2.getName(), o2.getSuffix()})) - font.getStringWidth(o1.getSuffix().isEmpty()?o1.getName():String.format("%s%s", new Object[]{o1.getName(), o1.getSuffix()}));
               });
            }

            int y2 = 1;
            int y = 1;
            int rainbowTick = 0;
            float borderSize = 0.0F;
            int countMod = 0;
            if(useFont) {
               if(this.mode.getValue() == HUD.fontMode.Vanilla) {
                  sorted.sort((o1, o2) -> {
                     int var2 = Minecraft.fontRendererObj.getStringWidth(o2.getSuffix().isEmpty()?o2.getName():String.format("%s%s", new Object[]{o2.getName(), o2.getSuffix()}));
                     Minecraft var10001 = mc;
                     return var2 - Minecraft.fontRendererObj.getStringWidth(o1.getSuffix().isEmpty()?o1.getName():String.format("%s%s", new Object[]{o1.getName(), o1.getSuffix()}));
                  });

                  for(Module m : sorted) {
                     String name = m.getSuffix().isEmpty()?m.getName():String.format("%s%s", new Object[]{m.getName(), m.getSuffix()});
                     int var43 = RenderUtil.width();
                     Minecraft var10001 = mc;
                     float x = (float)(var43 - Minecraft.fontRendererObj.getStringWidth(name));
                     Minecraft var48 = mc;
                     Color rainbow = new Color(Color.HSBtoRGB((float)((double)Minecraft.thePlayer.ticksExisted / 50.0D + Math.sin((double)rainbowTick / 50.0D * 1.6D)) % 1.0F, 0.5F, 1.0F));
                     Minecraft var44 = mc;
                     Minecraft.fontRendererObj.drawStringWithShadow(name, x - 2.0F, (float)y, ((Boolean)this.rainbow.getValue()).booleanValue()?rainbow.getRGB():m.getColor());
                     ++rainbowTick;
                     if(rainbowTick > 50) {
                        rainbowTick = 0;
                     }

                     y += 9;
                  }
               }

               for(Module m : sorted) {
                  float x = (float)(RenderUtil.width() - FontLoaders.sansation18.getStringWidth(String.valueOf(m.getName()) + " " + m.getSuffix()));
                  if(this.mode.getValue() == HUD.fontMode.Normal) {
                     Gui.drawRect((double)RenderUtil.width(), (double)(y - 3), (double)(x - 2.0F), (double)(y + 11), (new Color(0, 0, 0, ((Double)bg.getValue()).intValue())).getRGB());
                     FontLoaders.sansation18.drawStringWithShadow(m.getName(), (double)x, (double)(y + 3), (new Color(((Double)red.getValue()).intValue(), ((Double)green.getValue()).intValue(), ((Double)blue.getValue()).intValue())).getRGB());
                     FontLoaders.sansation18.drawStringWithShadow(m.getSuffix(), (double)(RenderUtil.width() - FontLoaders.sansation18.getStringWidth(m.getSuffix()) - 2), (double)(y + 3), (new Color(180, 180, 180)).getRGB());
                  }

                  if(this.mode.getValue() == HUD.fontMode.right) {
                     Gui.drawRect((double)(x - 1.0F), (double)(y - 3), (double)(x - 2.0F), (double)(y + 11), (new Color(((Double)red.getValue()).intValue(), ((Double)green.getValue()).intValue(), ((Double)blue.getValue()).intValue())).getRGB());
                     Gui.drawRect((double)RenderUtil.width(), (double)(y - 3), (double)(x - 2.0F), (double)(y + 11), (new Color(0, 0, 0, ((Double)bg.getValue()).intValue())).getRGB());
                     FontLoaders.sansation18.drawStringWithShadow(m.getName(), (double)x, (double)(y + 3), (new Color(((Double)red.getValue()).intValue(), ((Double)green.getValue()).intValue(), ((Double)blue.getValue()).intValue())).getRGB());
                     FontLoaders.sansation18.drawStringWithShadow(m.getSuffix(), (double)(RenderUtil.width() - FontLoaders.sansation18.getStringWidth(m.getSuffix()) - 2), (double)(y + 3), (new Color(180, 180, 180)).getRGB());
                  }

                  if(this.mode.getValue() == HUD.fontMode.left) {
                     Gui.drawRect((double)RenderUtil.width(), (double)(y - 3), (double)(x - 2.0F), (double)(y + 11), (new Color(0, 0, 0, ((Double)bg.getValue()).intValue())).getRGB());
                     FontLoaders.sansation18.drawStringWithShadow(m.getName(), (double)x, (double)(y + 3), (new Color(((Double)red.getValue()).intValue(), ((Double)green.getValue()).intValue(), ((Double)blue.getValue()).intValue())).getRGB());
                     FontLoaders.sansation18.drawStringWithShadow(m.getSuffix(), (double)(RenderUtil.width() - FontLoaders.sansation18.getStringWidth(m.getSuffix()) - 2), (double)(y + 3), (new Color(180, 180, 180)).getRGB());
                     Gui.drawRect((double)RenderUtil.width(), (double)(y - 3), (double)(RenderUtil.width() - 2), (double)(y + 11), (new Color(((Double)red.getValue()).intValue(), ((Double)green.getValue()).intValue(), ((Double)blue.getValue()).intValue())).getRGB());
                  }

                  if(this.mode.getValue() == HUD.fontMode.Remix) {
                     Gui.drawRect((double)RenderUtil.width(), (double)(y - 3), (double)(x - 2.0F), (double)(y + 11), (new Color(0, 0, 0, ((Double)bg.getValue()).intValue())).getRGB());
                     Gui.drawRect((double)(x - 1.0F), (double)y, (double)(x - 2.0F), (double)(y + 9), (new Color(((Double)red.getValue()).intValue(), ((Double)green.getValue()).intValue(), ((Double)blue.getValue()).intValue())).getRGB());
                     FontLoaders.sansation18.drawStringWithShadow(m.getName(), (double)x, (double)(y + 3), (new Color(((Double)red.getValue()).intValue(), ((Double)green.getValue()).intValue(), ((Double)blue.getValue()).intValue())).getRGB());
                     FontLoaders.sansation18.drawStringWithShadow(m.getSuffix(), (double)(RenderUtil.width() - FontLoaders.sansation18.getStringWidth(m.getSuffix()) - 2), (double)(y + 3), (new Color(180, 180, 180)).getRGB());
                  }

                  if(this.mode.getValue() == HUD.fontMode.Remix2) {
                     Gui.drawRect((double)RenderUtil.width(), (double)(y + 11), (double)(x - 4.0F), (double)(y + 13), (new Color(((Double)red.getValue()).intValue(), ((Double)green.getValue()).intValue(), ((Double)blue.getValue()).intValue())).getRGB());
                     Gui.drawRect((double)RenderUtil.width(), (double)(y - 3), (double)(x - 2.0F), (double)(y + 11), (new Color(0, 0, 0, 255)).getRGB());
                     Gui.drawRect((double)(x - 2.0F), (double)(y - 3), (double)(x - 4.0F), (double)(y + 11), (new Color(((Double)red.getValue()).intValue(), ((Double)green.getValue()).intValue(), ((Double)blue.getValue()).intValue())).getRGB());
                     FontLoaders.sansation18.drawStringWithShadow(m.getName(), (double)x, (double)(y + 3), (new Color(((Double)red.getValue()).intValue(), ((Double)green.getValue()).intValue(), ((Double)blue.getValue()).intValue())).getRGB());
                     FontLoaders.sansation18.drawStringWithShadow(m.getSuffix(), (double)(RenderUtil.width() - FontLoaders.sansation18.getStringWidth(m.getSuffix()) - 2), (double)(y + 3), (new Color(180, 180, 180)).getRGB());
                  }

                  if(this.mode.getValue() == HUD.fontMode.Rainbow) {
                     Color customrainbow1 = new Color(Color.HSBtoRGB((float)((double)Minecraft.thePlayer.ticksExisted / 50.0D + Math.sin((double)rainbowTick / 50.0D * 1.6D)) % 1.0F, ((Double)this.customrainbow.getValue()).floatValue(), 1.0F));
                     Gui.drawRect((double)RenderUtil.width(), (double)(y - 3), (double)(x - 2.0F), (double)(y + 11), (new Color(0, 0, 0, ((Double)bg.getValue()).intValue())).getRGB());
                     FontLoaders.sansation18.drawStringWithShadow(m.getName(), (double)x, (double)(y + 3), customrainbow1.getRGB());
                     FontLoaders.sansation18.drawStringWithShadow(m.getSuffix(), (double)(RenderUtil.width() - FontLoaders.sansation18.getStringWidth(m.getSuffix()) - 2), (double)(y + 3), (new Color(180, 180, 180)).getRGB());
                  }

                  if(this.mode.getValue() == HUD.fontMode.BRainbow) {
                     int rainbowCol = Gui.rainbow(System.nanoTime() * 5L, (float)countMod, 0.5F).getRGB();
                     ++countMod;
                     Color col = new Color(rainbowCol);
                     Color Ranbow = new Color(0.0F, (float)col.getGreen() / 255.0F, 1.0F);
                     Gui.drawRect((double)RenderUtil.width(), (double)(y - 3), (double)(x - 2.0F), (double)(y + 11), (new Color(0, 0, 0, ((Double)bg.getValue()).intValue())).getRGB());
                     FontLoaders.sansation18.drawStringWithShadow(m.getName(), (double)x, (double)(y + 3), Ranbow.getRGB());
                     FontLoaders.sansation18.drawStringWithShadow(m.getSuffix(), (double)(RenderUtil.width() - FontLoaders.sansation18.getStringWidth(m.getSuffix()) - 2), (double)(y + 3), (new Color(180, 180, 180)).getRGB());
                  }

                  if(this.mode.getValue() == HUD.fontMode.GRainbow) {
                     int rainbowCol = Gui.rainbow(System.nanoTime() * 5L, (float)countMod, 0.5F).getRGB();
                     ++countMod;
                     Color col = new Color(rainbowCol);
                     Color Ranbow = new Color(0.0F, 1.0F, (float)col.getBlue() / 255.0F);
                     Gui.drawRect((double)RenderUtil.width(), (double)(y - 3), (double)(x - 2.0F), (double)(y + 11), (new Color(0, 0, 0, ((Double)bg.getValue()).intValue())).getRGB());
                     FontLoaders.sansation18.drawStringWithShadow(m.getName(), (double)x, (double)(y + 3), Ranbow.getRGB());
                     FontLoaders.sansation18.drawStringWithShadow(m.getSuffix(), (double)(RenderUtil.width() - FontLoaders.sansation18.getStringWidth(m.getSuffix()) - 2), (double)(y + 3), (new Color(180, 180, 180)).getRGB());
                  }

                  if(this.mode.getValue() == HUD.fontMode.RRainbow) {
                     int rainbowCol = Gui.rainbow(System.nanoTime() * 5L, (float)countMod, 0.5F).getRGB();
                     ++countMod;
                     Color col = new Color(rainbowCol);
                     Color Ranbow = new Color((float)col.getRed() / 150.0F, 0.09803922F, 0.09803922F);
                     Gui.drawRect((double)RenderUtil.width(), (double)(y - 3), (double)(x - 2.0F), (double)(y + 11), (new Color(0, 0, 0, ((Double)bg.getValue()).intValue())).getRGB());
                     FontLoaders.sansation18.drawStringWithShadow(m.getName(), (double)x, (double)(y + 3), Ranbow.getRGB());
                     FontLoaders.sansation18.drawStringWithShadow(m.getSuffix(), (double)(RenderUtil.width() - FontLoaders.sansation18.getStringWidth(m.getSuffix()) - 2), (double)(y + 3), (new Color(180, 180, 180)).getRGB());
                  }

                  if(this.mode.getValue() == HUD.fontMode.WRainbow) {
                     int rainbowCol = Gui.rainbow(System.nanoTime() * 5L, (float)countMod, 0.5F).getRGB();
                     ++countMod;
                     Color col = new Color(rainbowCol);
                     Color Ranbow = new Color((float)col.getRed() / 255.0F, (float)col.getRed() / 255.0F, (float)col.getRed() / 255.0F);
                     Gui.drawRect((double)RenderUtil.width(), (double)(y - 3), (double)(x - 2.0F), (double)(y + 11), (new Color(0, 0, 0, ((Double)bg.getValue()).intValue())).getRGB());
                     FontLoaders.sansation18.drawStringWithShadow(m.getName(), (double)x, (double)(y + 3), Ranbow.getRGB());
                     FontLoaders.sansation18.drawStringWithShadow(m.getSuffix(), (double)(RenderUtil.width() - FontLoaders.sansation18.getStringWidth(m.getSuffix()) - 2), (double)(y + 3), (new Color(180, 180, 180)).getRGB());
                  }

                  if(this.mode.getValue() == HUD.fontMode.Sigma) {
                     Color customrainbow1 = new Color(Color.HSBtoRGB((float)((double)Minecraft.thePlayer.ticksExisted / 50.0D + Math.sin((double)rainbowTick / 50.0D * 1.6D)) % 1.0F, ((Double)this.customrainbow.getValue()).floatValue(), 1.0F));
                     Gui.drawRect((double)(x - 4.0F), (double)(y - 1), (double)(x - 3.0F), (double)(y + 8), customrainbow1.getRGB());
                     Gui.drawRect((double)RenderUtil.width(), (double)(y + 8), (double)(x - 4.0F), (double)(y + 12), customrainbow1.getRGB());
                     Gui.drawRect((double)RenderUtil.width(), (double)(y - 1), (double)(x - 3.0F), (double)(y + 11), (new Color(0, 0, 0, 255)).getRGB());
                     font.drawStringWithShadow(m.getName(), (double)x, (double)(y + 2), customrainbow1.getRGB());
                     font.drawStringWithShadow(m.getSuffix(), (double)(x + (float)font.getStringWidth(m.getName())), (double)(y + 2), (new Color(180, 180, 180)).getRGB());
                  }

                  Minecraft var47 = mc;
                  y += Minecraft.fontRendererObj.FONT_HEIGHT + 5;
                  ++rainbowTick;
                  if(rainbowTick > 50) {
                     rainbowTick = 0;
                  }
               }
            }

            String text = EnumChatFormatting.GRAY + "X" + EnumChatFormatting.WHITE + ": " + MathHelper.floor_double(Minecraft.thePlayer.posX) + " " + EnumChatFormatting.GRAY + "Y" + EnumChatFormatting.WHITE + ": " + MathHelper.floor_double(Minecraft.thePlayer.posY) + " " + EnumChatFormatting.GRAY + "Z" + EnumChatFormatting.WHITE + ": " + MathHelper.floor_double(Minecraft.thePlayer.posZ) + " " + EnumChatFormatting.GRAY + "FPS" + EnumChatFormatting.WHITE + ": " + Minecraft.debugFPS + " " + EnumChatFormatting.GRAY + "Ping" + EnumChatFormatting.WHITE + ": ";
            String info = EnumChatFormatting.WHITE + Client.ClientName + EnumChatFormatting.GRAY + " - " + EnumChatFormatting.GREEN + Client.ClientVersion + EnumChatFormatting.GRAY + " - " + EnumChatFormatting.DARK_RED + "Un Log in" + EnumChatFormatting.WHITE;
            info = EnumChatFormatting.WHITE + Client.ClientName + EnumChatFormatting.GRAY + " - " + EnumChatFormatting.GREEN + Client.ClientVersion + EnumChatFormatting.GRAY + " - " + EnumChatFormatting.RED + "[DEV]" + EnumChatFormatting.YELLOW + "Source Eclipse Ready - Margele" + EnumChatFormatting.WHITE;

            if(useFont) {
               var10000 = mc;
               int ychat = Minecraft.ingameGUI.getChatGUI().getChatOpen()?23:20;
               var10000 = mc;
               int me2 = Minecraft.ingameGUI.getChatGUI().getChatOpen()?-10:10;
               if(((Boolean)this.info.getValue()).booleanValue()) {
                  font.drawStringWithShadow(text, 4.0D, (double)((new ScaledResolution(mc)).getScaledHeight() - ychat), (new Color(11, 12, 17)).getRGB());
                  font.drawStringWithShadow(info, 4.0D, (double)((new ScaledResolution(mc)).getScaledHeight() - me2), (new Color(11, 12, 17)).getRGB());
                  this.drawPotionStatus(new ScaledResolution(mc));
                  Client.instance.getClass();
                  Client.instance.getClass();
               }
            }
         }
      }

   }

   private void wadawdaw() {
   }

   private void drawPotionStatus(ScaledResolution sr2) {
      CFontRenderer font = FontLoaders.sansation18;
      int y2 = 0;

      for(PotionEffect effect : Minecraft.thePlayer.getActivePotionEffects()) {
         Potion potion = Potion.potionTypes[effect.getPotionID()];
         String PType = I18n.format(potion.getName(), new Object[0]);
         switch(effect.getAmplifier()) {
         case 1:
            PType = String.valueOf(String.valueOf(PType)) + " II";
            break;
         case 2:
            PType = String.valueOf(String.valueOf(PType)) + " III";
            break;
         case 3:
            PType = String.valueOf(String.valueOf(PType)) + " IV";
         }

         if(effect.getDuration() < 600 && effect.getDuration() > 300) {
            PType = String.valueOf(String.valueOf(PType)) + "§7:§6 " + Potion.getDurationString(effect);
         } else if(effect.getDuration() < 300) {
            PType = String.valueOf(String.valueOf(PType)) + "§7:§c " + Potion.getDurationString(effect);
         } else if(effect.getDuration() > 600) {
            PType = String.valueOf(String.valueOf(PType)) + "§7:§7 " + Potion.getDurationString(effect);
         }

         Minecraft var10000 = mc;
         int ychat = Minecraft.ingameGUI.getChatGUI().getChatOpen()?5:-10;
         if(useFont) {
            var10000 = mc;
            FontRenderer var11 = Minecraft.fontRendererObj;
            int var10002 = sr2.getScaledWidth();
            Minecraft var10003 = mc;
            float var12 = (float)(var10002 - Minecraft.fontRendererObj.getStringWidth(PType) - 2);
            int var13 = sr2.getScaledHeight();
            Minecraft var10004 = mc;
            var11.drawStringWithShadow(PType, var12, (float)(var13 - Minecraft.fontRendererObj.FONT_HEIGHT + y2 - 12 - ychat), potion.getLiquidColor());
         }

         y2 -= 10;
      }

   }

   static enum fontMode {
      left,
      right,
      Remix,
      Remix2,
      Rainbow,
      BRainbow,
      GRainbow,
      RRainbow,
      WRainbow,
      Normal,
      Sigma,
      Vanilla;
   }
}
