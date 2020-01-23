package net.minecraft.entity.player.Really.Client.module.modules.render;

import com.google.common.collect.Ordering;
import com.ibm.icu.text.NumberFormat;
import java.awt.Color;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender2D;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.module.modules.combat.Aura;
import net.minecraft.entity.player.Really.Client.module.modules.combat.Criticals;
import net.minecraft.entity.player.Really.Client.ui.font.CFontRenderer;
import net.minecraft.entity.player.Really.Client.ui.font.FontLoaders;
import net.minecraft.entity.player.Really.Client.utils.PlayerUtil;
import net.minecraft.entity.player.Really.Client.utils.R3DUtil;
import net.minecraft.entity.player.Really.Client.utils.render.Colors;
import net.minecraft.entity.player.Really.Client.utils.render.RenderUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.opengl.GL11;

public class TargetHUD extends Module {
   public static boolean shouldMove;
   public static boolean useFont;
   public static float AnimotaiX;
   public static float AnimotaiSpeed;
   private Option black = new Option("Black", "Black", Boolean.valueOf(false));
   private Mode mode = new Mode("Mode", "mode", TargetHUD.rendermode.values(), TargetHUD.rendermode.Rainbowline);
   Colors hurtcolor;
   String hurtrender;
   String linehealth = null;

   public TargetHUD() {
      super("TargetHUD", new String[]{"gui"}, ModuleType.Render);
      this.setColor((new Color(244, 255, 149)).getRGB());
      this.addValues(new Value[]{this.mode, this.black});
   }

   @EventHandler
   public void onRender(EventRender2D event) {
      Minecraft var100001 = mc;
      FontRenderer font2 = Minecraft.fontRendererObj;
      CFontRenderer font = FontLoaders.sansation16;
      ScaledResolution res = new ScaledResolution(mc);
      ScaledResolution sr2 = new ScaledResolution(mc);
      int y2 = 2;
      boolean count = false;
      new Color(1.0F, 0.75F, 0.0F, 0.45F);
      int x1 = 600;
      int y1 = 355;
      int x2 = 750;
      int y22 = sr2.getScaledHeight() - 50;
      int nametag = y1 + 12;
      double thickness = 0.014999999664723873D;
      double xLeft = -20.0D;
      double xRight = 20.0D;
      double yUp = 27.0D;
      double yDown = 130.0D;
      double size = 1.0D;
      int red = 0;
      int green = 0;
      int blue = 0;
      double rainbowTick = 0.0D;
      Color rainbow = new Color(Color.HSBtoRGB((float)((double)Minecraft.thePlayer.ticksExisted / 100.0D + Math.sin(rainbowTick / 100.0D * 1.6D)) % 1.0F, 1.0F, 1.0F));
      if(Aura.curTarget != null) {
         this.tagrender();
         if(this.mode.getValue() == TargetHUD.rendermode.Rainbowline) {
            RenderUtil.drawBorderedRect((float)((new ScaledResolution(mc)).getScaledWidth() - 5), (double)((new ScaledResolution(mc)).getScaledHeight() - 30), (float)((new ScaledResolution(mc)).getScaledWidth() - 140), (double)((new ScaledResolution(mc)).getScaledHeight() - 80), 1.0F, (new Color(35, 35, 35, 0)).getRGB(), (new Color(35, 35, 35, 180)).getRGB());
            font2.drawString("Name:" + Aura.curTarget.getName(), (new ScaledResolution(mc)).getScaledWidth() - 138, (new ScaledResolution(mc)).getScaledHeight() - 40 + 3, 16777215);
            font.drawString("§aHP:§c" + (int)Aura.curTarget.getHealth() + "/" + (int)Aura.curTarget.getMaxHealth(), (float)((new ScaledResolution(mc)).getScaledWidth() - 108), (float)((new ScaledResolution(mc)).getScaledHeight() - 75), 1677721);
            font.drawString("§bHurt:" + (Aura.curTarget.hurtResistantTime > 0), (float)((new ScaledResolution(mc)).getScaledWidth() - 58), (float)((new ScaledResolution(mc)).getScaledHeight() - 75), 16777215);
            font.drawString("§dArmor:" + Aura.curTarget.getTotalArmorValue(), (float)((new ScaledResolution(mc)).getScaledWidth() - 108), (float)((new ScaledResolution(mc)).getScaledHeight() - 65 + 3), 16777215);
            font.drawString("§eDis:" + (int)Minecraft.thePlayer.getDistanceToEntity(Aura.curTarget), (float)((new ScaledResolution(mc)).getScaledWidth() - 65), (float)((new ScaledResolution(mc)).getScaledHeight() - 65 + 3), 16777215);
            font.drawString("X:" + (int)Aura.curTarget.posX + " " + "Y:" + (int)Aura.curTarget.posY + " " + "Z:" + (int)Aura.curTarget.posZ, (float)((new ScaledResolution(mc)).getScaledWidth() - 108), (float)((new ScaledResolution(mc)).getScaledHeight() - 55 + 3), 16777215);
            R3DUtil.drawEntityOnScreen((new ScaledResolution(mc)).getScaledWidth() - 125, (new ScaledResolution(mc)).getScaledHeight() - 45, 15, 2.0F, 15.0F, Aura.curTarget);
         }

         if(this.mode.getValue() == TargetHUD.rendermode.Rainbowline) {
            RenderUtil.drawBorderedRect((float)((new ScaledResolution(mc)).getScaledWidth() - 8), (double)((new ScaledResolution(mc)).getScaledHeight() - 29), (float)(new ScaledResolution(mc)).getScaledWidth() - 110.5F * Math.min((float)((new ScaledResolution(mc)).getScaledWidth() + 5), Aura.curTarget.getHealth() / (float)((int)Aura.curTarget.getMaxHealth())), (double)((new ScaledResolution(mc)).getScaledHeight() - 28), 3.0F, rainbow.getRGB(), rainbow.getRGB());
         }

         if(this.mode.getValue() == TargetHUD.rendermode.HTB) {
            int x = res.getScaledWidth() / 2 + 10;
            int y = res.getScaledHeight() - 90;
            Minecraft var100011 = mc;
            List var5 = GuiPlayerTabOverlay.field_175252_a.sortedCopy(Minecraft.thePlayer.sendQueue.getPlayerInfoMap());
            Iterator bigDecimal = var5.iterator();
            if(bigDecimal.hasNext()) {
               Object aVar5 = bigDecimal.next();
               NetworkPlayerInfo var6 = (NetworkPlayerInfo)aVar5;
               mc.getTextureManager().bindTexture(var6.getLocationSkin());
               Gui.drawScaledCustomSizeModalRect(x + 75, y - 220, 8.0F, 8.0F, 8, 8, 32, 32, 64.0F, 64.0F);
               GlStateManager.bindTexture(0);
            }

            font2.drawString("Name:" + Aura.curTarget.getName(), (new ScaledResolution(mc)).getScaledWidth() - 420, (new ScaledResolution(mc)).getScaledHeight() - 275, 16777215);
            font2.drawString(this.linehealth, (new ScaledResolution(mc)).getScaledWidth() - 390, (new ScaledResolution(mc)).getScaledHeight() - 265, Colors.AQUA.c);
         }

         if(this.mode.getValue() == TargetHUD.rendermode.Flat) {
            ScaledResolution res1 = new ScaledResolution(mc);
            int x = res1.getScaledWidth() / 2 + 10;
            int y = res1.getScaledHeight() - 90;
            EntityLivingBase player = Aura.curTarget;
            if(player != null) {
               GlStateManager.pushMatrix();
               Gui.drawRect((double)x + 1.0D, (double)y + 1.0D, (double)x + 123.0D, (double)y + 35.0D, ((Boolean)this.black.getValue()).booleanValue()?Integer.MIN_VALUE:(new Color(240, 238, 225, 150)).getRGB());
               RenderUtil.drawBorderedRect((float)x, (double)((float)y), (float)(x + 124), (double)(y + 36), 2.0F, ((Boolean)this.black.getValue()).booleanValue()?(new Color(240, 238, 225)).getRGB():(new Color(0, 0, 0)).getRGB(), 1);
               Gui.drawRect((double)x + 35.0D, (double)y + 1.0D, (double)x + 123.0D, (double)y + 35.0D, ((Boolean)this.black.getValue()).booleanValue()?Integer.MIN_VALUE:(new Color(240, 238, 225, 150)).getRGB());
               Minecraft.fontRendererObj.drawString(player.getName(), (float)x + 38.0F, (float)y + 4.0F, ((Boolean)this.black.getValue()).booleanValue()?-1:1, false);
               BigDecimal bigDecimal = new BigDecimal((double)player.getHealth());
               bigDecimal = bigDecimal.setScale(1, RoundingMode.HALF_UP);
               double HEALTH = bigDecimal.doubleValue();
               Minecraft var100021 = mc;
               BigDecimal DT = new BigDecimal((double)Minecraft.thePlayer.getDistanceToEntity(player));
               DT = DT.setScale(1, RoundingMode.HALF_UP);
               double Dis = DT.doubleValue();
               float health = player.getHealth();
               float[] fractions = new float[]{0.0F, 0.5F, 1.0F};
               Color[] colors = new Color[]{Color.RED, Color.YELLOW, Color.GREEN};
               float progress = health / player.getMaxHealth();
               Color customColor = health >= 0.0F?blendColors(fractions, colors, progress).brighter():Color.RED;
               var100001 = mc;
               double width = (double)Minecraft.fontRendererObj.getStringWidth(player.getName());
               width = this.getIncremental(width, 10.0D);
               if(width < 50.0D) {
                  width = 50.0D;
               }

               double healthLocation = width * (double)progress;
               Gui.drawRect((double)x + 37.5D, (double)y + 22.5D, (double)x + 48.0D + healthLocation + 0.5D, (double)y + 14.5D, customColor.getRGB());
               RenderUtil.rectangleBordered((double)x + 37.0D, (double)y + 22.0D, (double)x + 49.0D + width, (double)y + 15.0D, 0.5D, Colors.getColor(0, 0), Colors.getColor(0));
               if((double)health > 20.0D) {
                  String COLOR1 = " §9";
               } else if((double)health >= 10.0D) {
                  String COLOR1 = " §a";
               } else if((double)health >= 3.0D) {
                  String COLOR1 = " §e";
               } else {
                  String COLOR1 = " §4";
               }

               GlStateManager.scale(0.5D, 0.5D, 0.5D);
               String str3 = String.format("HP: %s HURT: %s", new Object[]{Integer.valueOf(Math.round(player.getHealth())), Integer.valueOf(player.hurtTime)});
               FontLoaders.comfortaa34.drawStringWithShadow(str3, (double)((float)(x * 2) + 76.0F), (double)((float)(y * 2) + 49.0F), ((Boolean)this.black.getValue()).booleanValue()?-1:1);
               GlStateManager.scale(2.0F, 2.0F, 2.0F);
               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
               GlStateManager.enableAlpha();
               GlStateManager.enableBlend();
               GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
               if(player instanceof EntityPlayer) {
                  Minecraft var128 = mc;

                  for(Object aVar5 : GuiPlayerTabOverlay.field_175252_a.sortedCopy(Minecraft.thePlayer.sendQueue.getPlayerInfoMap())) {
                     NetworkPlayerInfo var6 = (NetworkPlayerInfo)aVar5;
                     var100001 = mc;
                     if(Minecraft.theWorld.getPlayerEntityByUUID(var6.getGameProfile().getId()) == player) {
                        mc.getTextureManager().bindTexture(var6.getLocationSkin());
                        Gui.drawScaledCustomSizeModalRect(x + 2, y + 2, 8.0F, 8.0F, 8, 8, 32, 32, 64.0F, 64.0F);
                        if(((EntityPlayer)player).isWearing(EnumPlayerModelParts.HAT)) {
                           Gui.drawScaledCustomSizeModalRect(x + 2, y + 2, 40.0F, 8.0F, 8, 8, 35, 32, 74.0F, 74.0F);
                        }

                        GlStateManager.bindTexture(0);
                        break;
                     }
                  }
               }

               GlStateManager.popMatrix();
            }
         }

         if(this.mode.getValue() == TargetHUD.rendermode.New) {
            CFontRenderer font4 = FontLoaders.kiona18;
            ScaledResolution sr3 = new ScaledResolution(mc);
            int thecolor = (new Color(0, 230, 0, 220)).getRGB();
            var100001 = mc;
            FontRenderer font5 = Minecraft.fontRendererObj;
            EntityLivingBase player = Aura.curTarget;
            float opacity = 0.0F;
            if(player != null) {
               Minecraft var10000 = mc;
               int playerhp = (int)Minecraft.thePlayer.getHealth();
               int targethp = (int)player.getHealth();
               float render = 150.0F * player.getHealth() / player.getMaxHealth();
               GlStateManager.pushMatrix();
               thecolor = (new Color(190, 250, 0, 150)).getRGB();
               if((double)player.getHealth() >= (double)player.getMaxHealth() * 0.8D) {
                  thecolor = (new Color(0, 255, 0, 150)).getRGB();
               } else if((double)player.getHealth() < (double)player.getMaxHealth() * 0.8D && (double)player.getHealth() >= (double)player.getMaxHealth() * 0.6D) {
                  thecolor = (new Color(190, 230, 0, 150)).getRGB();
               } else if((double)player.getHealth() < (double)player.getMaxHealth() * 0.8D && (double)player.getHealth() < (double)player.getMaxHealth() * 0.6D && (double)player.getHealth() >= (double)player.getMaxHealth() * 0.3D) {
                  thecolor = (new Color(255, 255, 0, 150)).getRGB();
               } else if((double)player.getHealth() < (double)player.getMaxHealth() * 0.8D && (double)player.getHealth() < (double)player.getMaxHealth() * 0.6D && (double)player.getHealth() < (double)player.getMaxHealth() * 0.3D) {
                  thecolor = (new Color(255, 0, 0, 150)).getRGB();
               }

               GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
               RenderUtil.drawBorderedRect((float)(sr3.getScaledWidth() / 2), (double)((float)(sr3.getScaledHeight() / 2)), (float)(sr3.getScaledWidth() / 2 + 150), (double)((float)(sr3.getScaledHeight() / 2 + 60)), 1.0F, (new Color(5, 5, 5, 0)).getRGB(), (new Color(5, 6, 4, 100)).getRGB());
               font4.drawStringWithShadow(EnumChatFormatting.GRAY + "TargetName: ", (double)((float)(sr3.getScaledWidth() / 2) + 3.0F), (double)((float)(sr3.getScaledHeight() / 2) + 3.0F), 16777215);
               var100001 = mc;
               FontRenderer var10 = Minecraft.fontRendererObj;
               StringBuilder var10001 = (new StringBuilder()).append(EnumChatFormatting.WHITE).append(player.getName()).append("[");
               Minecraft var10002 = mc;
               var10.drawStringWithShadow(var10001.append((byte)((int)Minecraft.thePlayer.getDistanceToEntity(player))).append("m]").toString(), (float)(sr3.getScaledWidth() / 2) + 3.0F + (float)font2.getStringWidth("TargetName: "), (float)(sr3.getScaledHeight() / 2) + 3.0F, 16777215);
               font4.drawStringWithShadow(player.isEntityInsideOpaqueBlock()?EnumChatFormatting.GRAY + "isBlocking: " + EnumChatFormatting.WHITE + "true":EnumChatFormatting.GRAY + "isBlocking: " + EnumChatFormatting.WHITE + "false", (double)((float)(sr3.getScaledWidth() / 2) + 3.0F), (double)((float)(sr3.getScaledHeight() / 2) + 16.0F), -1);
               font4.drawStringWithShadow(EnumChatFormatting.GRAY + "HurtTime: " + EnumChatFormatting.WHITE + player.hurtTime, (double)((float)(sr3.getScaledWidth() / 2) + 3.0F), (double)((float)(sr3.getScaledHeight() / 2) + 29.0F), -1);
               font4.drawStringWithShadow(player.hurtTime > 0?EnumChatFormatting.GRAY + "isAttacking: " + EnumChatFormatting.WHITE + "true":EnumChatFormatting.GRAY + "isAttacking: " + EnumChatFormatting.WHITE + "false", (double)((float)(sr3.getScaledWidth() / 2) + 6.0F + (float)font2.getStringWidth("HurtTime: " + player.hurtTime)), (double)((float)(sr3.getScaledHeight() / 2) + 29.0F), -1);
               font4.drawStringWithShadow(EnumChatFormatting.GRAY + "HP: " + EnumChatFormatting.WHITE + targethp + "/" + (int)player.getMaxHealth(), (double)((float)(sr3.getScaledWidth() / 2) + 3.0F), (double)((float)(sr3.getScaledHeight() / 2) + 42.0F), -1);
               font5.drawStringWithShadow(playerhp > targethp?"奥里给,干他!":"三十六计,走为上计!", (float)(sr3.getScaledWidth() / 2) + 17.0F + (float)font2.getStringWidth("HP: " + targethp + "/" + (int)player.getMaxHealth()), (float)(sr3.getScaledHeight() / 2) + 42.0F, -1);
               RenderUtil.drawBorderedRect((float)(sr3.getScaledWidth() / 2), (double)(sr3.getScaledHeight() / 2) + 58.0D, (float)(sr3.getScaledWidth() / 2) + render + 2.0F, (double)(sr3.getScaledHeight() / 2) + 60.0D, 1.0F, (new Color(1, 1, 1, 0)).getRGB(), thecolor);
               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
               GlStateManager.enableAlpha();
               GlStateManager.enableBlend();
               GlStateManager.popMatrix();
            }
         }

         if(this.mode.getValue() == TargetHUD.rendermode.BLC) {
            ScaledResolution res1 = new ScaledResolution(mc);
            int x = res1.getScaledWidth() / 2 + 10;
            int y = res1.getScaledHeight() - 100;
            EntityLivingBase player = Aura.curTarget;
            if(player != null) {
               GlStateManager.pushMatrix();
               AnimotaiX = (float)RenderUtil.getAnimationState((double)AnimotaiX, 150.0D, 2.0D);
               BLCRender.drawRect((double)x + 0.0D, (double)y + 0.0D, (double)x + 125.0D, (double)y + 36.0D, Colors.getColor(0, 150));
               RenderUtil.drawBorderedRect((float)x - 7.0F, (double)((float)y - 7.0F), (float)x + 132.0F, (double)((float)y + 43.0F), 1.2F, Colors.getColor(0, 150), Colors.getColor(0, 150));
               var100001 = mc;
               Minecraft.fontRendererObj.drawStringWithShadow(player.getName(), (float)x + 38.0F, (float)y + 1.0F, -1);
               BigDecimal bigDecimal = new BigDecimal((double)player.getHealth());
               bigDecimal = bigDecimal.setScale(1, RoundingMode.HALF_UP);
               double HEALTH = bigDecimal.doubleValue();
               Minecraft var129 = mc;
               BigDecimal DT = new BigDecimal((double)Minecraft.thePlayer.getDistanceToEntity(player));
               DT = DT.setScale(1, RoundingMode.HALF_UP);
               double Dis = DT.doubleValue();
               float health = player.getHealth();
               float[] fractions = new float[]{0.0F, 0.5F, 1.0F};
               Color[] colors = new Color[]{Color.RED, Color.YELLOW, Color.GREEN};
               float progress = health / player.getMaxHealth();
               Color customColor = health >= 0.0F?blendColors(fractions, colors, progress).brighter():Color.RED;
               var100001 = mc;
               double width = (double)Minecraft.fontRendererObj.getStringWidth(player.getName());
               width = PlayerUtil.getIncremental(width, 10.0D);
               if(width < 50.0D) {
                  width = 50.0D;
               }

               double healthLocation = width * (double)progress;
               AnimotaiSpeed = (float)RenderUtil.getAnimationState((double)AnimotaiSpeed, healthLocation, 120.0D);
               BLCRender.drawRect((double)x + 37.5D, (double)y + 11.5D, (double)x + 38.0D + (double)AnimotaiSpeed + 0.5D, (double)y + 14.5D, Aura.curTarget.hurtTime > 5?(new Color(255, 0, 0)).getRGB():customColor.getRGB());
               BLCRender.rectangleBordered((double)x + 37.0D, (double)y + 11.0D, (double)x + 39.0D + width, (double)y + 15.0D, 0.5D, Colors.getColor(0, 0), Colors.getColor(0));

               for(int i = 1; i < 10; ++i) {
                  double dThing = width / 10.0D * (double)i;
                  BLCRender.drawRect((double)x + 38.0D + dThing, (double)y + 11.0D, (double)x + 38.0D + dThing + 0.5D, (double)y + 15.0D, Colors.getColor(0));
               }

               String COLOR1;
               if((double)health > 20.0D) {
                  COLOR1 = " §9";
               } else if((double)health >= 10.0D) {
                  COLOR1 = " §a";
               } else if((double)health >= 3.0D) {
                  COLOR1 = " §e";
               } else {
                  COLOR1 = " §4";
               }

               int rate = 0;
               BLCRender.rectangleBordered((double)x + 1.0D, (double)y + 1.0D, (double)x + 35.0D, (double)y + 35.0D, 0.5D, Colors.getColor(0, 0), Aura.curTarget.hurtTime > 5?(new Color(255, 0, 0)).getRGB():(new Color(255, 255, 255)).getRGB());
               var100001 = mc;
               if(Minecraft.thePlayer.getHealth() > Aura.curTarget.getHealth()) {
                  rate += 20;
               }

               var100001 = mc;
               if(Minecraft.thePlayer.getTotalArmorValue() > Aura.curTarget.getTotalArmorValue()) {
                  rate += 20;
               }

               var100001 = mc;
               if(Minecraft.thePlayer.onGround != Aura.curTarget.onGround) {
                  rate += 20;
               }

               if(Criticals.Crit) {
                  rate += 30;
               }

               if(player.getDistanceToEntity(Aura.curTarget) < ((Double)Aura.BlockReach.getValue()).floatValue()) {
                  rate += 10;
               }

               String Rate;
               if((double)rate > 90.0D) {
                  Rate = " §9";
               } else if((double)rate >= 60.0D) {
                  Rate = " §a";
               } else if((double)rate >= 30.0D) {
                  Rate = " §e";
               } else {
                  Rate = " §4";
               }

               if(Minecraft.thePlayer.getHeldItem() != null && Minecraft.thePlayer.getHeldItem().getItem() instanceof ItemSword || Minecraft.thePlayer.isBlocking()) {
                  rate += 20;
               }

               GlStateManager.scale(0.5D, 0.5D, 0.5D);
               String str = "HP: " + COLOR1 + HEALTH + "§r | Dist: " + Dis;
               var100001 = mc;
               Minecraft.fontRendererObj.drawStringWithShadow(str, (float)(x * 2) + 76.0F, (float)(y * 2) + 35.0F, Colors.getColor(255, 0));
               String str2 = String.format("Yaw: %s Pitch: %s BodyYaw: %s", new Object[]{Integer.valueOf((int)player.rotationYaw), Integer.valueOf((int)player.rotationPitch), Integer.valueOf((int)player.renderYawOffset)});
               var100001 = mc;
               Minecraft.fontRendererObj.drawStringWithShadow(str2, (float)(x * 2) + 76.0F, (float)(y * 2) + 47.0F, Colors.getColor(255, 0));
               String str3 = String.format("G: %s HURT: %s TE: %s", new Object[]{Boolean.valueOf(player.onGround), Integer.valueOf(player.hurtTime), Integer.valueOf(player.ticksExisted)});
               var100001 = mc;
               Minecraft.fontRendererObj.drawStringWithShadow(str3, (float)(x * 2) + 76.0F, (float)(y * 2) + 59.0F, Colors.getColor(255, 0));
               String Modlue = String.format("Feedback | Crit : " + Criticals.Crit + " Speed : " + " rightClick :" + mc.rightClickDelayTimer, new Object[0]);
               var100001 = mc;
               Minecraft.fontRendererObj.drawStringWithShadow(Modlue, (float)(x * 2) + 1.0F, (float)(y * 2) - 12.0F, Colors.getColor(255, 0));
               String Win = "Winrate : " + Rate + rate + "%";
               var100001 = mc;
               Minecraft.fontRendererObj.drawStringWithShadow(Win, (float)(x * 2) + 80.0F, (float)(y * 2) + 75.0F, Colors.getColor(255, 0));
               GlStateManager.scale(2.0F, 2.0F, 2.0F);
               GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
               GlStateManager.enableAlpha();
               GlStateManager.enableBlend();
               GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
               if(player instanceof EntityPlayer) {
                  Ordering var127 = GuiPlayerTabOverlay.field_175252_a;
                  Minecraft.getMinecraft();

                  for(Object aVar5 : var127.sortedCopy(Minecraft.thePlayer.sendQueue.getPlayerInfoMap())) {
                     NetworkPlayerInfo var6 = (NetworkPlayerInfo)aVar5;
                     Minecraft.getMinecraft();
                     if(Minecraft.theWorld.getPlayerEntityByUUID(var6.getGameProfile().getId()) == player) {
                        Minecraft.getMinecraft().getTextureManager().bindTexture(var6.getLocationSkin());
                        Gui.drawScaledCustomSizeModalRect(x + 2, y + 2, 8.0F, 8.0F, 8, 8, 32, 32, 64.0F, 64.0F);
                        if(((EntityPlayer)player).isWearing(EnumPlayerModelParts.HAT)) {
                           Gui.drawScaledCustomSizeModalRect(x + 2, y + 2, 40.0F, 8.0F, 8, 8, 32, 32, 64.0F, 64.0F);
                        }

                        GlStateManager.bindTexture(0);
                        break;
                     }
                  }
               }

               GlStateManager.popMatrix();
            }
         }

         if(this.mode.getValue() == TargetHUD.rendermode.Edebug) {
            this.Edebug();
         }
      }

   }

   private void Edebug() {
      Minecraft var10000 = mc;
      FontRenderer font2 = Minecraft.fontRendererObj;
      new ScaledResolution(mc);
      ScaledResolution sr2 = new ScaledResolution(mc);
      int y2 = 2;
      boolean count = false;
      new Color(1.0F, 0.75F, 0.0F, 0.45F);
      int x1 = 600;
      int y1 = 355;
      int x2 = 750;
      int y22 = sr2.getScaledHeight() - 50;
      int nametag = y1 + 12;
      double thickness = 0.014999999664723873D;
      double xLeft = -20.0D;
      double xRight = 20.0D;
      double yUp = 27.0D;
      double yDown = 130.0D;
      double size = 10.0D;
      int right = (new ScaledResolution(mc)).getScaledWidth() - (new ScaledResolution(mc)).getScaledWidth() / 2;
      int right2 = (new ScaledResolution(mc)).getScaledWidth() - (new ScaledResolution(mc)).getScaledWidth() / 2 + 30;
      int height = (new ScaledResolution(mc)).getScaledHeight() - 70;
      if(Aura.curTarget != null) {
         Gui.drawRect((double)right, (double)(height - 50), (double)(right + 130), (double)(height - 90), (new Color(0, 0, 0, 130)).getRGB());
         font2.drawString(Aura.curTarget.getName(), right + 30, height - 87, 16777215);
         font2.drawString("HP:" + (int)Aura.curTarget.getHealth() + "/" + (int)Aura.curTarget.getMaxHealth() + " " + "Hurt:" + (Aura.curTarget.hurtResistantTime > 0), right + 30, height - 70, (new Color(255, 255, 255)).getRGB());
         font2.drawString("Coords: " + (int)Aura.curTarget.posX + " " + (int)Aura.curTarget.posY + " " + (int)Aura.curTarget.posZ, right + 30, height - 60, (new Color(255, 255, 255)).getRGB());
         R3DUtil.drawEntityOnScreen(right + 14, height - 54, 15, 2.0F, 15.0F, Aura.curTarget);
         Gui.drawRainbowRectVertical(right2, height - 77, (int)((float)right2 + 95.0F * Math.min((float)right2, Aura.curTarget.getHealth() / Aura.curTarget.getMaxHealth())), height - 73, 3, 1.0F);
      }

   }

   private void tagrender() {
      int health = (int)Aura.curTarget.getHealth();
      if((double)health >= 20.0D) {
         this.linehealth = "§a||||||||||||||||||||";
      } else if((double)health > 18.0D) {
         this.linehealth = "§a||||||||||||||||||§c||";
      } else if((double)health > 16.0D) {
         this.linehealth = "§a||||||||||||||||§c||||";
      } else if((double)health > 14.0D) {
         this.linehealth = "§a||||||||||||||§c||||||";
      } else if((double)health > 12.0D) {
         this.linehealth = "§a||||||||||||§c||||||||";
      } else if((double)health > 10.0D) {
         this.linehealth = "§a||||||||||§c||||||||||";
      } else if((double)health > 8.0D) {
         this.linehealth = "§a||||||||§c||||||||||||";
      } else if((double)health > 6.0D) {
         this.linehealth = "§a||||||§c||||||||||||||";
      } else if((double)health > 4.0D) {
         this.linehealth = "§a||||§c||||||||||||||||";
      } else if((double)health > 2.0D) {
         this.linehealth = "§a||§c||||||||||||||||||";
      } else if((double)health > 0.0D) {
         this.linehealth = "§c||||||||||||||||||||";
      }

   }

   @EventHandler
   public void onScreenDraw(EventRender2D er) {
      if(this.mode.getValue() == TargetHUD.rendermode.Mikov) {
         ScaledResolution res = new ScaledResolution(mc);
         int x = res.getScaledWidth() / 2 + 10;
         int y = res.getScaledHeight() - 90;
         EntityLivingBase player = Aura.curTarget;
         if(player != null) {
            GlStateManager.pushMatrix();
            RenderUtil.drawRect((float)x + 0.0F, (float)y + 0.0F, (float)x + 143.0F, (float)y + 36.0F, Colors.getColor(0, 150));
            Minecraft var10000 = mc;
            Minecraft.fontRendererObj.drawStringWithShadow(player.getName(), (float)x + 38.0F, (float)y + 2.0F, -1);
            BigDecimal bigDecimal = new BigDecimal((double)player.getHealth());
            bigDecimal = bigDecimal.setScale(1, RoundingMode.HALF_UP);
            double HEALTH = bigDecimal.doubleValue();
            Minecraft var10002 = mc;
            BigDecimal DT = new BigDecimal((double)Minecraft.thePlayer.getDistanceToEntity(player));
            DT = DT.setScale(1, RoundingMode.HALF_UP);
            double Dis = DT.doubleValue();
            float health = player.getHealth();
            float[] fractions = new float[]{0.0F, 0.5F, 1.0F};
            Color[] colors = new Color[]{Color.RED, Color.YELLOW, Color.GREEN};
            float progress = health / player.getMaxHealth();
            if(health >= 0.0F) {
               blendColors(fractions, colors, progress).brighter();
            } else {
               Color var37 = Color.RED;
            }

            var10000 = mc;
            double width = (double)Minecraft.fontRendererObj.getStringWidth(player.getName());
            width = PlayerUtil.getIncremental(width, 10.0D);
            if(width < 50.0D) {
               width = 50.0D;
            }

            double healthLocation = width * (double)progress;
            RenderUtil.drawGradientSideways((double)x, (double)(y + 35), (double)x + healthLocation * 2.86D, (double)(y + 36), (new Color(81, 174, 255)).getRGB(), (new Color(40, 62, 255)).getRGB());

            for(int i = 1; i < 10; ++i) {
               double str = width / 10.0D * (double)i;
            }

            if((double)health > 20.0D) {
               String COLOR1 = " §9";
            } else if((double)health >= 10.0D) {
               String COLOR1 = " §a";
            } else if((double)health >= 3.0D) {
               String COLOR1 = " §e";
            } else {
               String COLOR1 = " §4";
            }

            GlStateManager.scale(0.5D, 0.5D, 0.5D);
            String str = "HP: " + HEALTH + " Dist: " + Dis;
            var10000 = mc;
            Minecraft.fontRendererObj.drawStringWithShadow(str, (float)(x * 2) + 76.0F, (float)(y * 2) + 35.0F, -1);
            String str2 = String.format("Yaw: %s Pitch: %s ", new Object[]{Integer.valueOf((int)player.rotationYaw), Integer.valueOf((int)player.rotationPitch)});
            var10000 = mc;
            Minecraft.fontRendererObj.drawStringWithShadow(str2, (float)(x * 2) + 76.0F, (float)(y * 2) + 47.0F, -1);
            String str3 = String.format("G: %s HURT: %s TE: %s", new Object[]{Boolean.valueOf(player.onGround), Integer.valueOf(player.hurtTime), Integer.valueOf(player.ticksExisted)});
            var10000 = mc;
            Minecraft.fontRendererObj.drawStringWithShadow(str3, (float)(x * 2) + 76.0F, (float)(y * 2) + 59.0F, -1);
            GlStateManager.scale(2.0F, 2.0F, 2.0F);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            if(player instanceof EntityPlayer) {
               Minecraft var10001 = mc;

               for(Object aVar5 : GuiPlayerTabOverlay.field_175252_a.sortedCopy(Minecraft.thePlayer.sendQueue.getPlayerInfoMap())) {
                  NetworkPlayerInfo var6 = (NetworkPlayerInfo)aVar5;
                  var10000 = mc;
                  if(Minecraft.theWorld.getPlayerEntityByUUID(var6.getGameProfile().getId()) == player) {
                     mc.getTextureManager().bindTexture(var6.getLocationSkin());
                     Gui.drawScaledCustomSizeModalRect(x + 2, y + 2, 8.0F, 8.0F, 8, 8, 32, 32, 64.0F, 64.0F);
                     if(((EntityPlayer)player).isWearing(EnumPlayerModelParts.HAT)) {
                        Gui.drawScaledCustomSizeModalRect(x + 2, y + 2, 40.0F, 8.0F, 8, 8, 32, 32, 64.0F, 64.0F);
                     }

                     GlStateManager.bindTexture(0);
                     break;
                  }
               }
            }

            GlStateManager.popMatrix();
         }
      }

   }

   private void renderStuffStatus(ScaledResolution scaledRes) {
      int yOffset = 15;
      int slot = 3;

      for(int xOffset = 0; slot >= 0; --slot) {
         Minecraft var10000 = mc;
         ItemStack stack = Minecraft.thePlayer.inventory.armorItemInSlot(slot);
         new GuiIngame(mc);
         if(stack != null) {
            GL11.glDisable(2929);
            GL11.glScalef(0.5F, 0.5F, 0.5F);
            var10000 = mc;
            Minecraft.fontRendererObj.drawStringWithShadow(String.valueOf(stack.getMaxDamage() - stack.getItemDamage()), (float)(scaledRes.getScaledWidth() + 32 - xOffset * 2 + (stack.getMaxDamage() - stack.getItemDamage() >= 100?4:(stack.getMaxDamage() - stack.getItemDamage() <= 100 && stack.getMaxDamage() - stack.getItemDamage() >= 10?7:11))), (float)(scaledRes.getScaledHeight() * 2 - 147 - yOffset + 30), 16777215);
            GL11.glScalef(2.0F, 2.0F, 2.0F);
            GL11.glEnable(2929);
            mc.getRenderItem().renderItemIntoGUI(stack, scaledRes.getScaledWidth() / 2 + 25 - xOffset, scaledRes.getScaledHeight() - 70 - yOffset / 2 + 15);
            xOffset -= 18;
         }
      }

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

   public static Color blendColors(float[] fractions, Color[] colors, float progress) {
      Color color = null;
      if(fractions == null) {
         throw new IllegalArgumentException("Fractions can\'t be null");
      } else if(colors == null) {
         throw new IllegalArgumentException("Colours can\'t be null");
      } else if(fractions.length == colors.length) {
         int[] indicies = getFractionIndicies(fractions, progress);
         float[] range = new float[]{fractions[indicies[0]], fractions[indicies[1]]};
         Color[] colorRange = new Color[]{colors[indicies[0]], colors[indicies[1]]};
         float max = range[1] - range[0];
         float value = progress - range[0];
         float weight = value / max;
         color = blend(colorRange[0], colorRange[1], (double)(1.0F - weight));
         return color;
      } else {
         throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
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
         System.out.println(nf.format((double)red) + "; " + nf.format((double)green) + "; " + nf.format((double)blue));
         var14.printStackTrace();
      }

      return color3;
   }

   private double getIncremental(double val, double inc) {
      double one = 1.0D / inc;
      return (double)Math.round(val * one) / one;
   }

   public static enum Direction {
      S("S", 0),
      SW("SW", 1),
      W("W", 2),
      NW("NW", 3),
      N("N", 4),
      NE("NE", 5),
      E("E", 6),
      SE("SE", 7);

      private Direction(String s, int n) {
      }
   }

   static enum rendermode {
      Rainbowline,
      HTB,
      Mikov,
      Flat,
      BLC,
      Edebug,
      New;
   }
}
