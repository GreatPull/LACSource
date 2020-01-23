package net.minecraft.entity.player.Really.Client.module.modules.render;

import java.awt.Color;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender3D;
import net.minecraft.entity.player.Really.Client.management.FriendManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.module.modules.player.Teams;
import net.minecraft.entity.player.Really.Client.utils.render.RenderUtil;
import net.minecraft.init.Items;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import optifine.Config;
import org.lwjgl.opengl.GL11;

public class Nametags extends Module {
   private int i = 0;

   public Nametags() {
      super("NameTags", new String[]{"tags"}, ModuleType.Render);
      this.setColor((new Color(29, 187, 102)).getRGB());
   }

   @EventHandler
   private void onRender(EventRender3D render) {
      ArrayList<EntityPlayer> validEnt = new ArrayList();
      if(validEnt.size() > 100) {
         validEnt.clear();
      }

      Minecraft var10000 = mc;

      for(EntityLivingBase player2 : Minecraft.theWorld.playerEntities) {
         if(player2.isEntityAlive()) {
            if(player2.isInvisible()) {
               if(validEnt.contains(player2)) {
                  validEnt.remove(player2);
               }
            } else {
               Minecraft var10001 = mc;
               if(player2 == Minecraft.thePlayer) {
                  if(validEnt.contains(player2)) {
                     validEnt.remove(player2);
                  }
               } else {
                  if(validEnt.size() > 100) {
                     break;
                  }

                  if(!validEnt.contains(player2)) {
                     validEnt.add((EntityPlayer)player2);
                  }
               }
            }
         } else if(validEnt.contains(player2)) {
            validEnt.remove(player2);
         }
      }

      validEnt.forEach((player) -> {
         float x = (float)(player.lastTickPosX + (player.posX - player.lastTickPosX) * (double)render.getPartialTicks() - RenderManager.renderPosX);
         float y = (float)(player.lastTickPosY + (player.posY - player.lastTickPosY) * (double)render.getPartialTicks() - RenderManager.renderPosY);
         float z = (float)(player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double)render.getPartialTicks() - RenderManager.renderPosZ);
         this.renderNametag(player, x, y, z);
      });
   }

   private String getHealth(EntityPlayer player) {
      DecimalFormat numberFormat = new DecimalFormat("0.#");
      return numberFormat.format((double)(player.getHealth() / 2.0F + player.getAbsorptionAmount() / 2.0F));
   }

   private void drawNames(EntityPlayer player) {
      float xP = 2.2F;
      float width = (float)this.getWidth(this.getPlayerName(player)) / 2.0F + xP;
      float var8;
      float w = var8 = (float)((double)width + (double)(this.getWidth(" " + this.getHealth(player)) / 2) + 2.5D);
      float nw = -var8 - xP;
      float offset = (float)(this.getWidth(this.getPlayerName(player)) + 4);
      RenderUtil.drawBorderedRect(nw + 6.0F, -1.0D, var8, 10.0D, 1.0F, (new Color(20, 20, 20, 0)).getRGB(), (new Color(10, 10, 10, 200)).getRGB());
      GlStateManager.disableDepth();
      offset = offset + (float)(this.getWidth(this.getHealth(player)) + this.getWidth(" ") - 1);
      this.drawString(this.getPlayerName(player), w - offset + 2.0F, 0.0F, 16777215);
      if(player.getHealth() == 10.0F) {
         int color = 16776960;
      }

      int color = player.getHealth() > 10.0F?RenderUtil.blend(new Color(-16711936), new Color(-256), (double)(1.0F / player.getHealth() / 2.0F * (player.getHealth() - 10.0F))).getRGB():RenderUtil.blend(new Color(-256), new Color(-65536), (double)(0.1F * player.getHealth())).getRGB();
      this.drawString(this.getHealth(player), w - (float)this.getWidth(String.valueOf(this.getHealth(player)) + " ") + 2.0F, 0.0F, color);
      GlStateManager.enableDepth();
   }

   private void drawString(String text, float x, float y, int color) {
      Minecraft var10000 = mc;
      Minecraft.fontRendererObj.drawStringWithShadow(text, x, y, color);
   }

   private int getWidth(String text) {
      Minecraft var10000 = mc;
      return Minecraft.fontRendererObj.getStringWidth(text);
   }

   private void startDrawing(float x, float y, float z, EntityPlayer player) {
      Minecraft var10000 = mc;
      float var10001 = Minecraft.gameSettings.thirdPersonView == 2?-1.0F:1.0F;
      double size = Config.zoomMode?(double)(this.getSize(player) / 10.0F) * 1.6D:(double)(this.getSize(player) / 10.0F) * 4.8D;
      GL11.glPushMatrix();
      RenderUtil.startDrawing();
      GL11.glTranslatef(x, y, z);
      GL11.glNormal3f(0.0F, 1.0F, 0.0F);
      mc.getRenderManager();
      GL11.glRotatef(-RenderManager.playerViewY, 0.0F, 1.0F, 0.0F);
      mc.getRenderManager();
      GL11.glRotatef(RenderManager.playerViewX, var10001, 0.0F, 0.0F);
      GL11.glScaled(-0.01666666753590107D * size, -0.01666666753590107D * size, 0.01666666753590107D * size);
   }

   private void stopDrawing() {
      RenderUtil.stopDrawing();
      GlStateManager.color(1.0F, 1.0F, 1.0F);
      GlStateManager.popMatrix();
   }

   private void renderNametag(EntityPlayer player, float x, float y, float z) {
      y = (float)((double)y + 1.55D + (player.isSneaking()?0.5D:0.7D));
      this.startDrawing(x, y, z, player);
      this.drawNames(player);
      GL11.glColor4d(1.0D, 1.0D, 1.0D, 1.0D);
      this.renderArmor(player);
      this.stopDrawing();
   }

   private void renderArmor(EntityPlayer player) {
      ItemStack[] renderStack = player.inventory.armorInventory;
      int length = renderStack.length;
      int xOffset = 0;

      for(ItemStack aRenderStack : renderStack) {
         if(aRenderStack != null) {
            xOffset -= 8;
         }
      }

      if(player.getHeldItem() != null) {
         xOffset = xOffset - 8;
         ItemStack stock = player.getHeldItem().copy();
         if(stock.hasEffect() && (stock.getItem() instanceof ItemTool || stock.getItem() instanceof ItemArmor)) {
            stock.stackSize = 1;
         }

         this.renderItemStack(stock, xOffset, -20);
         xOffset = xOffset + 16;
      }

      renderStack = player.inventory.armorInventory;

      for(int index = 3; index >= 0; --index) {
         ItemStack armourStack = renderStack[index];
         if(armourStack != null) {
            this.renderItemStack(armourStack, xOffset, -20);
            xOffset += 16;
         }
      }

      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
   }

   private String getPlayerName(EntityPlayer player) {
      String name = player.getDisplayName().getFormattedText();
      if(FriendManager.isFriend(player.getName())) {
         name = "§b[Fri]" + FriendManager.getAlias(player.getName());
      }

      if(Teams.isOnSameTeam(player)) {
         name = "§a[Teams]" + player.getDisplayName().getFormattedText();
      }

      return name;
   }

   private float getSize(EntityPlayer player) {
      Minecraft var10000 = mc;
      float var2;
      if(Minecraft.thePlayer.getDistanceToEntity(player) / 4.0F <= 2.0F) {
         var2 = 2.0F;
      } else {
         Minecraft var3 = mc;
         var2 = Minecraft.thePlayer.getDistanceToEntity(player) / 4.0F;
      }

      return var2;
   }

   private void renderItemStack(ItemStack stack, int x, int y) {
      GlStateManager.pushMatrix();
      GlStateManager.depthMask(true);
      GlStateManager.clear(256);
      RenderHelper.enableStandardItemLighting();
      mc.getRenderItem().zLevel = -150.0F;
      GlStateManager.disableDepth();
      GlStateManager.disableTexture2D();
      GlStateManager.enableBlend();
      GlStateManager.enableAlpha();
      GlStateManager.enableTexture2D();
      GlStateManager.enableLighting();
      GlStateManager.enableDepth();
      mc.getRenderItem().renderItemAndEffectIntoGUI(stack, x, y);
      RenderItem var10000 = mc.getRenderItem();
      Minecraft var10001 = mc;
      var10000.renderItemOverlays(Minecraft.fontRendererObj, stack, x, y);
      mc.getRenderItem().zLevel = 0.0F;
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableCull();
      GlStateManager.enableAlpha();
      GlStateManager.disableBlend();
      GlStateManager.disableLighting();
      double s = 0.5D;
      GlStateManager.scale(s, s, s);
      GlStateManager.disableDepth();
      this.renderEnchantText(stack, x, y);
      GlStateManager.enableDepth();
      GlStateManager.scale(2.0F, 2.0F, 2.0F);
      GlStateManager.popMatrix();
   }

   private void renderEnchantText(ItemStack stack, int x, int y) {
      int enchantmentY = y - 24;
      if(stack.getEnchantmentTagList() != null && stack.getEnchantmentTagList().tagCount() >= 6) {
         Minecraft var42 = mc;
         Minecraft.fontRendererObj.drawStringWithShadow("god", (float)(x * 2), (float)enchantmentY, 16711680);
      } else {
         if(stack.getItem() instanceof ItemArmor) {
            int protectionLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack);
            int projectileProtectionLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.projectileProtection.effectId, stack);
            int blastProtectionLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack);
            int fireProtectionLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack);
            int thornsLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack);
            int unbreakingLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
            int damage = stack.getMaxDamage() - stack.getItemDamage();
            if(protectionLevel > 0) {
               Minecraft var10000 = mc;
               Minecraft.fontRendererObj.drawStringWithShadow("prot" + protectionLevel, (float)(x * 2), (float)enchantmentY, -1);
               enchantmentY += 8;
            }

            if(projectileProtectionLevel > 0) {
               Minecraft var24 = mc;
               Minecraft.fontRendererObj.drawStringWithShadow("proj" + projectileProtectionLevel, (float)(x * 2), (float)enchantmentY, -1);
               enchantmentY += 8;
            }

            if(blastProtectionLevel > 0) {
               Minecraft var25 = mc;
               Minecraft.fontRendererObj.drawStringWithShadow("bp" + blastProtectionLevel, (float)(x * 2), (float)enchantmentY, -1);
               enchantmentY += 8;
            }

            if(fireProtectionLevel > 0) {
               Minecraft var26 = mc;
               Minecraft.fontRendererObj.drawStringWithShadow("frp" + fireProtectionLevel, (float)(x * 2), (float)enchantmentY, -1);
               enchantmentY += 8;
            }

            if(thornsLevel > 0) {
               Minecraft var27 = mc;
               Minecraft.fontRendererObj.drawStringWithShadow("th" + thornsLevel, (float)(x * 2), (float)enchantmentY, -1);
               enchantmentY += 8;
            }

            if(unbreakingLevel > 0) {
               Minecraft var28 = mc;
               Minecraft.fontRendererObj.drawStringWithShadow("unb" + unbreakingLevel, (float)(x * 2), (float)enchantmentY, -1);
               enchantmentY += 8;
            }
         }

         if(stack.getItem() instanceof ItemBow) {
            int powerLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, stack);
            int punchLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, stack);
            int flameLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, stack);
            int unbreakingLevel2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
            if(powerLevel > 0) {
               Minecraft var29 = mc;
               Minecraft.fontRendererObj.drawStringWithShadow("pow" + powerLevel, (float)(x * 2), (float)enchantmentY, -1);
               enchantmentY += 8;
            }

            if(punchLevel > 0) {
               Minecraft var30 = mc;
               Minecraft.fontRendererObj.drawStringWithShadow("pun" + punchLevel, (float)(x * 2), (float)enchantmentY, -1);
               enchantmentY += 8;
            }

            if(flameLevel > 0) {
               Minecraft var31 = mc;
               Minecraft.fontRendererObj.drawStringWithShadow("flame" + flameLevel, (float)(x * 2), (float)enchantmentY, -1);
               enchantmentY += 8;
            }

            if(unbreakingLevel2 > 0) {
               Minecraft var32 = mc;
               Minecraft.fontRendererObj.drawStringWithShadow("unb" + unbreakingLevel2, (float)(x * 2), (float)enchantmentY, -1);
               enchantmentY += 8;
            }
         }

         if(stack.getItem() instanceof ItemSword) {
            int sharpnessLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack);
            int knockbackLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.knockback.effectId, stack);
            int fireAspectLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack);
            int unbreakingLevel2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
            if(sharpnessLevel > 0) {
               Minecraft var33 = mc;
               Minecraft.fontRendererObj.drawStringWithShadow("sh" + sharpnessLevel, (float)(x * 2), (float)enchantmentY, -1);
               enchantmentY += 8;
            }

            if(knockbackLevel > 0) {
               Minecraft var34 = mc;
               Minecraft.fontRendererObj.drawStringWithShadow("kb" + knockbackLevel, (float)(x * 2), (float)enchantmentY, -1);
               enchantmentY += 8;
            }

            if(fireAspectLevel > 0) {
               Minecraft var35 = mc;
               Minecraft.fontRendererObj.drawStringWithShadow("fire" + fireAspectLevel, (float)(x * 2), (float)enchantmentY, -1);
               enchantmentY += 8;
            }

            if(unbreakingLevel2 > 0) {
               Minecraft var36 = mc;
               Minecraft.fontRendererObj.drawStringWithShadow("unb" + unbreakingLevel2, (float)(x * 2), (float)enchantmentY, -1);
            }
         }

         if(stack.getItem() instanceof ItemTool) {
            int unbreakingLevel22 = EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack);
            int efficiencyLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.efficiency.effectId, stack);
            int fortuneLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.fortune.effectId, stack);
            int silkTouch = EnchantmentHelper.getEnchantmentLevel(Enchantment.silkTouch.effectId, stack);
            if(efficiencyLevel > 0) {
               Minecraft var37 = mc;
               Minecraft.fontRendererObj.drawStringWithShadow("eff" + efficiencyLevel, (float)(x * 2), (float)enchantmentY, -1);
               enchantmentY += 8;
            }

            if(fortuneLevel > 0) {
               Minecraft var38 = mc;
               Minecraft.fontRendererObj.drawStringWithShadow("fo" + fortuneLevel, (float)(x * 2), (float)enchantmentY, -1);
               enchantmentY += 8;
            }

            if(silkTouch > 0) {
               Minecraft var39 = mc;
               Minecraft.fontRendererObj.drawStringWithShadow("silk" + silkTouch, (float)(x * 2), (float)enchantmentY, -1);
               enchantmentY += 8;
            }

            if(unbreakingLevel22 > 0) {
               Minecraft var40 = mc;
               Minecraft.fontRendererObj.drawStringWithShadow("ub" + unbreakingLevel22, (float)(x * 2), (float)enchantmentY, -1);
            }
         }

         if(stack.getItem() == Items.golden_apple && stack.hasEffect()) {
            Minecraft var41 = mc;
            Minecraft.fontRendererObj.drawStringWithShadow("god", (float)(x * 2), (float)enchantmentY, -1);
         }

      }
   }
}
