package net.minecraft.entity.player.Really.Client.ui.csgo;

import java.awt.Color;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.management.ModuleManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.ui.csgo.Opacity;
import net.minecraft.entity.player.Really.Client.ui.csgo.RenderUtil;
import net.minecraft.entity.player.Really.Client.ui.font.FontLoaders;
import net.minecraft.entity.player.Really.Client.utils.RenderUtils;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class NewClickGui extends GuiScreen implements GuiYesNoCallback {
   public static ModuleType currentModuleType = ModuleType.Combat;
   public static Module currentModule = ModuleManager.getModulesInType(currentModuleType).size() != 0?(Module)ModuleManager.getModulesInType(currentModuleType).get(0):null;
   public static float startX = 100.0F;
   public static float startY = 100.0F;
   public int moduleStart = 0;
   public int valueStart = 0;
   boolean previousmouse = true;
   boolean mouse;
   public Opacity opacity = new Opacity(0);
   public int opacityx = 255;
   public float moveX = 0.0F;
   public float moveY = 0.0F;

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      if(this.isHovered(startX, startY - 25.0F, startX + 400.0F, startY + 25.0F, mouseX, mouseY) && Mouse.isButtonDown(0)) {
         if(this.moveX == 0.0F && this.moveY == 0.0F) {
            this.moveX = (float)mouseX - startX;
            this.moveY = (float)mouseY - startY;
         } else {
            startX = (float)mouseX - this.moveX;
            startY = (float)mouseY - this.moveY;
         }

         this.previousmouse = true;
      } else if(this.moveX != 0.0F || this.moveY != 0.0F) {
         this.moveX = 0.0F;
         this.moveY = 0.0F;
      }

      this.opacity.interpolate((float)this.opacityx);
      RenderUtil.drawRect(startX, startY, startX + 60.0F, startY + 320.0F, (new Color(40, 40, 40, (int)this.opacity.getOpacity())).getRGB());
      RenderUtil.drawRect(startX + 60.0F, startY, startX + 200.0F, startY + 320.0F, (new Color(31, 31, 31, (int)this.opacity.getOpacity())).getRGB());
      RenderUtil.drawRect(startX + 200.0F, startY, startX + 420.0F, startY + 320.0F, (new Color(40, 40, 40, (int)this.opacity.getOpacity())).getRGB());

      for(int m = 0; m < ModuleType.values().length; ++m) {
         ModuleType[] mY = ModuleType.values();
         if(mY[m] != currentModuleType) {
            RenderUtil.drawFilledCircle((double)(startX + 30.0F), (double)(startY + 30.0F + (float)(m * 40)), 15.0D, (new Color(56, 56, 56, (int)this.opacity.getOpacity())).getRGB(), 5);
            if(mY[m].toString() == "Combat") {
               Client.instance.getClass();
               RenderUtils.drawImage(new ResourceLocation("LAC/Clickui/combat.png"), (int)startX + 20, (int)startY + 20 + m * 40, 20, 20);
            }

            if(mY[m].toString() == "Movement") {
               Client.instance.getClass();
               RenderUtils.drawImage(new ResourceLocation("LAC/Clickui/movement.png"), (int)startX + 20, (int)startY + 20 + m * 40, 20, 20);
            }

            if(mY[m].toString() == "Player") {
               Client.instance.getClass();
               RenderUtils.drawImage(new ResourceLocation("LAC/Clickui/player.png"), (int)startX + 20, (int)startY + 20 + m * 40, 20, 20);
            }

            if(mY[m].toString() == "Render") {
               Client.instance.getClass();
               RenderUtils.drawImage(new ResourceLocation("LAC/Clickui/render.png"), (int)startX + 20, (int)startY + 20 + m * 40, 20, 20);
            }

            if(mY[m].toString() == "World") {
               Client.instance.getClass();
               RenderUtils.drawImage(new ResourceLocation("LAC/Clickui/world.png"), (int)startX + 20, (int)startY + 20 + m * 40, 20, 20);
            }
         } else {
            RenderUtil.drawFilledCircle((double)(startX + 30.0F), (double)(startY + 30.0F + (float)(m * 40)), 15.0D, (new Color(101, 81, 255, (int)this.opacity.getOpacity())).getRGB(), 5);
            if(mY[m].toString() == "Combat") {
               Client.instance.getClass();
               RenderUtils.drawImage(new ResourceLocation("LAC/Clickui/combat2.png"), (int)startX + 20, (int)startY + 20 + m * 40, 20, 20);
            }

            if(mY[m].toString() == "Movement") {
               Client.instance.getClass();
               RenderUtils.drawImage(new ResourceLocation("LAC/Clickui/movement2.png"), (int)startX + 20, (int)startY + 20 + m * 40, 20, 20);
            }

            if(mY[m].toString() == "Player") {
               Client.instance.getClass();
               RenderUtils.drawImage(new ResourceLocation("LAC/Clickui/player2.png"), (int)startX + 20, (int)startY + 20 + m * 40, 20, 20);
            }

            if(mY[m].toString() == "Render") {
               Client.instance.getClass();
               RenderUtils.drawImage(new ResourceLocation("LAC/Clickui/render2.png"), (int)startX + 20, (int)startY + 20 + m * 40, 20, 20);
            }

            if(mY[m].toString() == "World") {
               Client.instance.getClass();
               RenderUtils.drawImage(new ResourceLocation("LAC/Clickui/world2.png"), (int)startX + 20, (int)startY + 20 + m * 40, 20, 20);
            }
         }

         try {
            if(this.isCategoryHovered(startX + 15.0F, startY + 15.0F + (float)(m * 40), startX + 45.0F, startY + 45.0F + (float)(m * 40), mouseX, mouseY) && Mouse.isButtonDown(0)) {
               currentModuleType = mY[m];
               currentModule = ModuleManager.getModulesInType(currentModuleType).size() != 0?(Module)ModuleManager.getModulesInType(currentModuleType).get(0):null;
               this.moduleStart = 0;
            }
         } catch (Exception var231) {
            System.err.println(var231);
         }
      }

      int var24 = Mouse.getDWheel();
      if(this.isCategoryHovered(startX + 60.0F, startY, startX + 200.0F, startY + 320.0F, mouseX, mouseY)) {
         if(var24 < 0 && this.moduleStart < ModuleManager.getModulesInType(currentModuleType).size() - 1) {
            ++this.moduleStart;
         }

         if(var24 > 0 && this.moduleStart > 0) {
            --this.moduleStart;
         }
      }

      if(this.isCategoryHovered(startX + 200.0F, startY, startX + 420.0F, startY + 320.0F, mouseX, mouseY)) {
         if(var24 < 0 && this.valueStart < currentModule.getValues().size() - 1) {
            ++this.valueStart;
         }

         if(var24 > 0 && this.valueStart > 0) {
            --this.valueStart;
         }
      }

      FontLoaders.comfortaa16.drawString(currentModule == null?currentModuleType.toString():currentModuleType.toString() + "/" + currentModule.getName(), startX + 70.0F, startY + 15.0F, (new Color(248, 248, 248)).getRGB());
      if(currentModule != null) {
         float var241 = startY + 30.0F;

         for(int i = 0; i < ModuleManager.getModulesInType(currentModuleType).size(); ++i) {
            Module value = (Module)ModuleManager.getModulesInType(currentModuleType).get(i);
            if(var241 > startY + 300.0F) {
               break;
            }

            if(i >= this.moduleStart) {
               RenderUtil.drawRect(startX + 75.0F, var241, startX + 185.0F, var241 + 2.0F, (new Color(40, 40, 40, (int)this.opacity.getOpacity())).getRGB());
               FontLoaders.comfortaa16.drawString(value.getName(), startX + 90.0F, var241 + 8.0F, (new Color(248, 248, 248, (int)this.opacity.getOpacity())).getRGB());
               if(!value.isEnabled()) {
                  RenderUtil.drawFilledCircle((double)(startX + 75.0F), (double)(var241 + 13.0F), 2.0D, (new Color(255, 0, 0, (int)this.opacity.getOpacity())).getRGB(), 5);
               } else {
                  RenderUtil.drawFilledCircle((double)(startX + 75.0F), (double)(var241 + 13.0F), 2.0D, (new Color(0, 255, 0, (int)this.opacity.getOpacity())).getRGB(), 5);
               }

               if(this.isSettingsButtonHovered(startX + 90.0F, var241, startX + 100.0F + (float)FontLoaders.comfortaa20.getStringWidth(value.getName()), var241 + 8.0F + 0.0F, mouseX, mouseY)) {
                  if(!this.previousmouse && Mouse.isButtonDown(0)) {
                     if(value.isEnabled()) {
                        value.setEnabled(false);
                     } else {
                        value.setEnabled(true);
                     }

                     this.previousmouse = true;
                  }

                  if(!this.previousmouse && Mouse.isButtonDown(1)) {
                     this.previousmouse = true;
                  }
               }

               if(!Mouse.isButtonDown(0)) {
                  this.previousmouse = false;
               }

               if(this.isSettingsButtonHovered(startX + 90.0F, var241, startX + 100.0F + (float)FontLoaders.comfortaa20.getStringWidth(value.getName()), var241 + 8.0F + 0.0F, mouseX, mouseY) && Mouse.isButtonDown(1)) {
                  currentModule = value;
                  this.valueStart = 0;
               }

               var241 += 25.0F;
            }
         }

         var241 = startY + 30.0F;

         for(int i = 0; i < currentModule.getValues().size() && var241 <= startY + 300.0F; ++i) {
            if(i >= this.valueStart) {
               Value var25 = (Value)currentModule.getValues().get(i);
               if(var25 instanceof Numbers) {
                  float x = startX + 300.0F;
                  double current = (double)(68.0F * (((Number)var25.getValue()).floatValue() - ((Numbers)var25).getMinimum().floatValue()) / (((Numbers)var25).getMaximum().floatValue() - ((Numbers)var25).getMinimum().floatValue()));
                  RenderUtil.drawRect(x - 6.0F, var241 + 2.0F, (float)((double)x + 75.0D), var241 + 3.0F, (new Color(50, 50, 50, (int)this.opacity.getOpacity())).getRGB());
                  RenderUtil.drawRect(x - 6.0F, var241 + 2.0F, (float)((double)x + current + 6.5D), var241 + 3.0F, (new Color(61, 141, 255, (int)this.opacity.getOpacity())).getRGB());
                  RenderUtil.drawRect((float)((double)x + current + 2.0D), var241, (float)((double)x + current + 7.0D), var241 + 5.0F, (new Color(61, 141, 255, (int)this.opacity.getOpacity())).getRGB());
                  FontLoaders.sansation18.drawStringWithShadow(var25.getName() + ": " + var25.getValue(), (double)(startX + 210.0F), (double)var241, -1);
                  if(!Mouse.isButtonDown(0)) {
                     this.previousmouse = false;
                  }

                  if(this.isButtonHovered(x, var241 - 2.0F, x + 100.0F, var241 + 7.0F, mouseX, mouseY) && Mouse.isButtonDown(0)) {
                     if(!this.previousmouse && Mouse.isButtonDown(0)) {
                        current = ((Numbers)var25).getMinimum().doubleValue();
                        double max = ((Numbers)var25).getMaximum().doubleValue();
                        double inc = ((Numbers)var25).getIncrement().doubleValue();
                        double valAbs = (double)mouseX - ((double)x + 1.0D);
                        double perc = valAbs / 68.0D;
                        perc = Math.min(Math.max(0.0D, perc), 1.0D);
                        double valRel = (max - current) * perc;
                        double val = current + valRel;
                        val = (double)Math.round(val * (1.0D / inc)) / (1.0D / inc);
                        ((Numbers)var25).setValue(Double.valueOf(val));
                     }

                     if(!Mouse.isButtonDown(0)) {
                        this.previousmouse = false;
                     }
                  }

                  var241 += 20.0F;
               }

               if(var25 instanceof Option) {
                  float x = startX + 300.0F;
                  FontLoaders.sansation18.drawStringWithShadow(var25.getName(), (double)(startX + 210.0F), (double)var241, -1);
                  RenderUtil.drawRect(x + 56.0F, var241, x + 76.0F, var241 + 1.0F, (new Color(255, 255, 255, (int)this.opacity.getOpacity())).getRGB());
                  RenderUtil.drawRect(x + 56.0F, var241 + 8.0F, x + 76.0F, var241 + 9.0F, (new Color(255, 255, 255, (int)this.opacity.getOpacity())).getRGB());
                  RenderUtil.drawRect(x + 56.0F, var241, x + 57.0F, var241 + 9.0F, (new Color(255, 255, 255, (int)this.opacity.getOpacity())).getRGB());
                  RenderUtil.drawRect(x + 77.0F, var241, x + 76.0F, var241 + 9.0F, (new Color(255, 255, 255, (int)this.opacity.getOpacity())).getRGB());
                  if(((Boolean)var25.getValue()).booleanValue()) {
                     RenderUtil.drawRect(x + 67.0F, var241 + 2.0F, x + 75.0F, var241 + 7.0F, (new Color(61, 141, 255, (int)this.opacity.getOpacity())).getRGB());
                  } else {
                     RenderUtil.drawRect(x + 58.0F, var241 + 2.0F, x + 65.0F, var241 + 7.0F, (new Color(150, 150, 150, (int)this.opacity.getOpacity())).getRGB());
                  }

                  if(this.isCheckBoxHovered(x + 56.0F, var241, x + 76.0F, var241 + 9.0F, mouseX, mouseY)) {
                     if(!this.previousmouse && Mouse.isButtonDown(0)) {
                        this.previousmouse = true;
                        this.mouse = true;
                     }

                     if(this.mouse) {
                        var25.setValue(Boolean.valueOf(!((Boolean)var25.getValue()).booleanValue()));
                        this.mouse = false;
                     }
                  }

                  if(!Mouse.isButtonDown(0)) {
                     this.previousmouse = false;
                  }

                  var241 += 20.0F;
               }

               if(var25 instanceof Mode) {
                  float x = startX + 300.0F;
                  FontLoaders.sansation18.drawStringWithShadow(var25.getName(), (double)(startX + 210.0F), (double)var241, -1);
                  RenderUtil.drawRect(x - 5.0F, var241 - 5.0F, x + 90.0F, var241 + 15.0F, (new Color(56, 56, 56, (int)this.opacity.getOpacity())).getRGB());
                  RenderUtil.drawBorderRect((double)(x - 5.0F), (double)(var241 - 5.0F), (double)(x + 90.0F), (double)(var241 + 15.0F), (new Color(101, 81, 255, (int)this.opacity.getOpacity())).getRGB(), 2.0D);
                  FontLoaders.sansation18.drawStringWithShadow(((Mode)var25).getModeAsString(), (double)(x + 40.0F - (float)(FontLoaders.sansation18.getStringWidth(((Mode)var25).getModeAsString()) / 2)), (double)var241, -1);
                  if(this.isStringHovered(x, var241 - 5.0F, x + 100.0F, var241 + 15.0F, mouseX, mouseY)) {
                     if(Mouse.isButtonDown(0) && !this.previousmouse) {
                        Enum var26 = (Enum)((Mode)var25).getValue();
                        int next = var26.ordinal() + 1 >= ((Mode)var25).getModes().length?0:var26.ordinal() + 1;
                        var25.setValue(((Mode)var25).getModes()[next]);
                        this.previousmouse = true;
                     }

                     if(!Mouse.isButtonDown(0)) {
                        this.previousmouse = false;
                     }
                  }

                  var241 += 25.0F;
               }
            }
         }
      }

   }

   public boolean isStringHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
      return (float)mouseX >= f && (float)mouseX <= g && (float)mouseY >= y && (float)mouseY <= y2;
   }

   public boolean isSettingsButtonHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
      return (float)mouseX >= x && (float)mouseX <= x2 && (float)mouseY >= y && (float)mouseY <= y2;
   }

   public boolean isButtonHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
      return (float)mouseX >= f && (float)mouseX <= g && (float)mouseY >= y && (float)mouseY <= y2;
   }

   public boolean isCheckBoxHovered(float f, float y, float g, float y2, int mouseX, int mouseY) {
      return (float)mouseX >= f && (float)mouseX <= g && (float)mouseY >= y && (float)mouseY <= y2;
   }

   public boolean isCategoryHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
      return (float)mouseX >= x && (float)mouseX <= x2 && (float)mouseY >= y && (float)mouseY <= y2;
   }

   public boolean isHovered(float x, float y, float x2, float y2, int mouseX, int mouseY) {
      return (float)mouseX >= x && (float)mouseX <= x2 && (float)mouseY >= y && (float)mouseY <= y2;
   }

   public void onGuiClosed() {
      this.opacity.setOpacity(0.0F);
   }
}
