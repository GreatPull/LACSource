package net.minecraft.entity.player.Really.Client.ui.HanabiClickGui;

import java.awt.Color;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.RenderHack;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.management.ModuleManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.module.modules.render.ClickGui;
import net.minecraft.entity.player.Really.Client.ui.HanabiClickGui.HanabiRender;
import net.minecraft.entity.player.Really.Client.ui.font.CFontRenderer;
import net.minecraft.entity.player.Really.Client.ui.font.FontLoaders;
import net.minecraft.entity.player.Really.Client.utils.RenderUtils;
import net.minecraft.entity.player.Really.Client.utils.RenderingUtil;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

public class HanabiClickGui extends GuiScreen {
   public static ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
   public static ModuleType currentModuleType = ModuleType.Combat;
   public static Module currentModule = (Module)ModuleManager.getModulesInType(currentModuleType).get(0);
   public static float startX = (float)(sr.getScaledWidth() / 2 - 185);
   public static float startY = (float)(sr.getScaledHeight() / 2 - 162);
   public static int moduleStart = 0;
   public static int valueStart = 0;
   boolean previousmouse = true;
   boolean mouse;
   public float moveX = 0.0F;
   public float moveY = 0.0F;
   int rex = 0;
   int rey = 0;
   boolean bind = false;

   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
      sr = new ScaledResolution(this.mc);
      int color = (new Color(0, 100, 255)).getRGB();
      if(((Boolean)ClickGui.rb.getValue()).booleanValue()) {
         int[] rainboww = new int[1];
         color = HanabiRender.rainbow(rainboww[0] * -20);
         rainboww[0] += 4;
      } else {
         color = (new Color(0, 100, 255)).getRGB();
      }

      if(this.rex < 185) {
         this.rex = this.rex + 20 > 185?185:this.rex + 20;
      }

      if(this.rey < 162) {
         this.rey = this.rey + 20 > 162?162:this.rey + 20;
      }

      if((double)startX > sr.getScaledWidth_double() || (double)startY > sr.getScaledHeight_double() || startX < 161.0F || startX < 0.0F) {
         startX = (float)(sr.getScaledWidth() / 2 - 185);
         startY = (float)(sr.getScaledHeight() / 2 - 162);
      }

      Gui.drawRect((double)(startX + 185.0F - (float)this.rex), (double)startY + 212.5D - (double)this.rey, (double)(startX + 185.0F + (float)this.rex), (double)(startY + 240.0F + (float)this.rey), (new Color(47, 47, 47)).getRGB());
      Gui.drawRect((double)(startX + 185.0F - (float)this.rex < startX + 150.0F?startX + 150.0F:startX + 185.0F - (float)this.rex), (double)startY + 212.5D - (double)this.rey, (double)(startX + 185.0F + (float)this.rex), (double)(startY + 240.0F + (float)this.rey), (new Color(81, 86, 88)).getRGB());
      if(this.rex >= 185 || this.rey >= 162) {
         int m = Mouse.getDWheel();
         if(this.isCategoryHovered(startX, startY + 50.0F, startX + 150.0F, startY + 375.0F, mouseX, mouseY)) {
            if(m < 0 && moduleStart < ModuleManager.getModulesInType(currentModuleType).size() - 1) {
               ++moduleStart;
            }

            if(m > 0 && moduleStart > 0) {
               --moduleStart;
            }
         }

         if(this.isCategoryHovered(startX + 150.0F, startY + 50.0F, startX + 370.0F, startY + 375.0F, mouseX, mouseY)) {
            if(m < 0 && valueStart < currentModule.getValues().size() - 1) {
               ++valueStart;
            }

            if(m > 0 && valueStart > 0) {
               --valueStart;
            }
         }

         RenderingUtil.drawBorderRect(0.0D, 0.0D, 0.0D, 0.0D, (new Color(0, 0, 0, 0)).getRGB(), 1.0D);
         FontLoaders.sansation24.drawString(currentModuleType.toString() + "/" + currentModule.getName(), startX + 15.0F, startY + 58.0F, color);
         float mY = startY + 30.0F;

         for(int i = 0; i < ModuleManager.getModulesInType(currentModuleType).size(); ++i) {
            Module module = (Module)ModuleManager.getModulesInType(currentModuleType).get(i);
            if(mY > startY + 350.0F) {
               break;
            }

            if(i >= moduleStart) {
               if(!module.isEnabled()) {
                  if(module.clickanim > 110) {
                     --module.clickanim;
                  }

                  Gui.drawRect((double)startX, (double)(mY + 45.0F), (double)(startX + 150.0F), (double)(mY + 70.0F), (new Color(175, 175, 175)).getRGB());
                  Gui.drawRect((double)(startX + 110.0F), (double)(mY + 54.0F), (double)(startX + 125.0F), (double)(mY + 62.0F), (new Color(55, 55, 55)).getRGB());
                  Gui.drawFilledCircle((double)(startX + 125.0F), (double)(mY + 58.0F), 4.0D, (new Color(55, 55, 55)).getRGB(), 5);
                  Gui.drawFilledCircle((double)(startX + 110.0F), (double)(mY + 58.0F), 4.0D, (new Color(55, 55, 55)).getRGB(), 5);
                  Gui.drawFilledCircle((double)(startX + (float)module.clickanim), (double)(mY + 58.0F), 5.0D, (new Color(88, 88, 88)).getRGB(), 5);
                  RenderingUtil.drawBorderRect(0.0D, 0.0D, 0.0D, 0.0D, (new Color(0, 0, 0, 0)).getRGB(), 1.0D);
                  FontLoaders.sansation18.drawString(module.getName(), startX + 10.0F, mY + 55.0F, (new Color(1, 1, 1)).getRGB());
               } else {
                  if(module.clickanim < 125) {
                     ++module.clickanim;
                  }

                  Gui.drawRect((double)startX, (double)(mY + 45.0F), (double)(startX + 150.0F), (double)(mY + 70.0F), (new Color(150, 150, 150)).getRGB());
                  Gui.drawRect((double)(startX + 110.0F), (double)(mY + 54.0F), (double)(startX + 125.0F), (double)(mY + 62.0F), (new Color(88, 88, 88)).getRGB());
                  Gui.drawFilledCircle((double)(startX + 125.0F), (double)(mY + 58.0F), 4.0D, (new Color(88, 88, 88)).getRGB(), 5);
                  Gui.drawFilledCircle((double)(startX + 110.0F), (double)(mY + 58.0F), 4.0D, (new Color(88, 88, 88)).getRGB(), 5);
                  Gui.drawFilledCircle((double)(startX + (float)module.clickanim), (double)(mY + 58.0F), 5.0D, color, 5);
                  RenderingUtil.drawBorderRect(0.0D, 0.0D, 0.0D, 0.0D, (new Color(0, 0, 0, 0)).getRGB(), 1.0D);
                  FontLoaders.sansation18.drawString(module.getName(), startX + 10.0F, mY + 55.0F, (new Color(1, 1, 1)).getRGB());
               }

               if(this.isSettingsButtonHovered(startX, mY + 45.0F, startX + 125.0F, mY + 70.0F, mouseX, mouseY)) {
                  if(!this.previousmouse && Mouse.isButtonDown(0)) {
                     if(module.isEnabled()) {
                        module.setEnabled(false);
                     } else {
                        module.setEnabled(true);
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

               if(this.isSettingsButtonHovered(startX, mY + 45.0F, startX + 125.0F, mY + 70.0F, mouseX, mouseY) && Mouse.isButtonDown(1)) {
                  for(int j = 0; j < currentModule.getValues().size(); ++j) {
                     Value value = (Value)currentModule.getValues().get(j);
                     if(value instanceof Option) {
                        ((Option)value).anim = 55;
                     }
                  }

                  for(Module mod : ModuleManager.getModulesInType(currentModuleType)) {
                     mod.clickanim = 115;
                  }

                  currentModule = module;
                  valueStart = 0;
                  this.rex = 0;
                  this.rey = 0;
               }

               mY += 25.0F;
            }
         }

         mY = startY + 30.0F;
         CFontRenderer font = FontLoaders.sansation16;

         for(int i = 0; i < currentModule.getValues().size() && mY <= startY + 350.0F; ++i) {
            if(i >= valueStart) {
               Value value = (Value)currentModule.getValues().get(i);
               if(value instanceof Numbers) {
                  float x = startX + 270.0F;
                  double render = (double)(68.0F * (((Number)value.getValue()).floatValue() - ((Numbers)value).getMinimum().floatValue()) / (((Numbers)value).getMaximum().floatValue() - ((Numbers)value).getMinimum().floatValue()));
                  Gui.drawRect((double)(x + 2.0F), (double)(mY + 52.0F), (double)((float)((double)x + 75.0D)), (double)(mY + 53.0F), (new Color(50, 50, 50)).getRGB());
                  Gui.drawRect((double)(x + 2.0F), (double)(mY + 52.0F), (double)((float)((double)x + render + 6.5D)), (double)(mY + 53.0F), color);
                  Gui.drawFilledCircle((double)((float)((double)x + render + 2.0D) + 3.0F), (double)mY + 52.25D, 3.7D, color, 5);
                  RenderingUtil.drawBorderRect(0.0D, 0.0D, 0.0D, 0.0D, (new Color(0, 0, 0, 0)).getRGB(), 1.0D);
                  font.drawStringWithShadow(value.getName() + ": " + value.getValue(), (double)(startX + 170.0F), (double)(mY + 50.0F), (new Color(175, 175, 175)).getRGB());
                  if(!Mouse.isButtonDown(0)) {
                     this.previousmouse = false;
                  }

                  if(this.isButtonHovered(x, mY + 52.0F, x + 100.0F, mY + 57.0F, mouseX, mouseY) && Mouse.isButtonDown(0)) {
                     if(!this.previousmouse && Mouse.isButtonDown(0)) {
                        render = ((Numbers)value).getMinimum().doubleValue();
                        double max = ((Numbers)value).getMaximum().doubleValue();
                        double inc = ((Numbers)value).getIncrement().doubleValue();
                        double valAbs = (double)mouseX - ((double)x + 1.0D);
                        double perc = valAbs / 68.0D;
                        perc = Math.min(Math.max(0.0D, perc), 1.0D);
                        double valRel = (max - render) * perc;
                        double val = render + valRel;
                        val = (double)Math.round(val * (1.0D / inc)) / (1.0D / inc);
                        ((Numbers)value).setValue(Double.valueOf(val));
                     }

                     if(!Mouse.isButtonDown(0)) {
                        this.previousmouse = false;
                     }
                  }

                  mY += 20.0F;
               }

               if(value instanceof Option) {
                  float x = startX + 245.0F;
                  int xx = 50;
                  int x2x = 65;
                  font.drawStringWithShadow(value.getName(), (double)(startX + 170.0F), (double)(mY + 50.0F), (new Color(175, 175, 175)).getRGB());
                  RenderHack.drawBorderedRect(1.0F, 1.0F, 1.0F, 1.0F, 1.0F, (new Color(0, 0, 0, 0)).getRGB(), 77);
                  if(((Boolean)value.getValue()).booleanValue()) {
                     if(((Option)value).anim < x2x) {
                        ++((Option)value).anim;
                     }

                     Gui.drawRect((double)(x + (float)xx), (double)(mY + 50.0F), (double)(x + (float)x2x), (double)(mY + 59.0F), (new Color(47, 47, 47)).getRGB());
                     Gui.drawFilledCircle((double)(x + (float)xx), (double)mY + 54.5D, 4.5D, (new Color(47, 47, 47)).getRGB(), 10);
                     Gui.drawFilledCircle((double)(x + (float)x2x), (double)mY + 54.5D, 4.5D, (new Color(47, 47, 47)).getRGB(), 10);
                     Gui.drawFilledCircle((double)(x + (float)((Option)value).anim), (double)mY + 54.5D, 5.0D, color, 10);
                  } else {
                     if(((Option)value).anim > xx) {
                        --((Option)value).anim;
                     }

                     Gui.drawRect((double)(x + (float)xx), (double)(mY + 50.0F), (double)(x + (float)x2x), (double)(mY + 59.0F), (new Color(20, 20, 20)).getRGB());
                     Gui.drawFilledCircle((double)(x + (float)xx), (double)mY + 54.5D, 4.5D, (new Color(20, 20, 20)).getRGB(), 10);
                     Gui.drawFilledCircle((double)(x + (float)x2x), (double)mY + 54.5D, 4.5D, (new Color(20, 20, 20)).getRGB(), 10);
                     Gui.drawFilledCircle((double)(x + (float)((Option)value).anim), (double)mY + 54.5D, 5.0D, (new Color(47, 47, 47)).getRGB(), 10);
                  }

                  if(this.isCheckBoxHovered(x + (float)xx - 5.0F, mY + 50.0F, x + (float)x2x + 6.0F, mY + 59.0F, mouseX, mouseY)) {
                     if(!this.previousmouse && Mouse.isButtonDown(0)) {
                        this.previousmouse = true;
                        this.mouse = true;
                     }

                     if(this.mouse) {
                        value.setValue(Boolean.valueOf(!((Boolean)value.getValue()).booleanValue()));
                        this.mouse = false;
                     }
                  }

                  if(!Mouse.isButtonDown(0)) {
                     this.previousmouse = false;
                  }

                  mY += 20.0F;
               }

               if(value instanceof Mode) {
                  float x = startX + 260.0F;
                  font.drawStringWithShadow(value.getName(), (double)(startX + 170.0F), (double)(mY + 50.0F), (new Color(175, 175, 175)).getRGB());
                  Gui.drawRect((double)(x + 5.0F), (double)(mY + 45.0F), (double)(x + 75.0F), (double)(mY + 65.0F), (new Color(56, 56, 56)).getRGB());
                  Gui.drawFilledCircle((double)(x + 5.0F), (double)(mY + 55.0F), 10.0D, (new Color(56, 56, 56)).getRGB(), 5);
                  Gui.drawFilledCircle((double)(x + 75.0F), (double)(mY + 55.0F), 10.0D, (new Color(56, 56, 56)).getRGB(), 5);
                  RenderingUtil.drawBorderRect(0.0D, 0.0D, 0.0D, 0.0D, color, 1.0D);
                  font.drawStringWithShadow(((Mode)value).getModeAsString(), (double)(x + 40.0F - (float)(font.getStringWidth(((Mode)value).getModeAsString()) / 2)), (double)(mY + 51.0F), -1);
                  if(this.isStringHovered(x, mY + 45.0F, x + 75.0F, mY + 65.0F, mouseX, mouseY)) {
                     if(Mouse.isButtonDown(0) && !this.previousmouse) {
                        Enum current = (Enum)((Mode)value).getValue();
                        int next = current.ordinal() + 1 >= ((Mode)value).getModes().length?0:current.ordinal() + 1;
                        value.setValue(((Mode)value).getModes()[next]);
                        this.previousmouse = true;
                     }

                     if(!Mouse.isButtonDown(0)) {
                        this.previousmouse = false;
                     }
                  }

                  mY += 25.0F;
               }
            }
         }

         if(mY < startY + 350.0F) {
            float x = startX + 260.0F;
            font.drawStringWithShadow("Bind", (double)(startX + 170.0F), (double)(mY + 50.0F), (new Color(170, 170, 170)).getRGB());
            Gui.drawRect((double)(x + 5.0F), (double)(mY + 45.0F), (double)(x + 75.0F), (double)(mY + 65.0F), (new Color(56, 56, 56)).getRGB());
            Gui.drawFilledCircle((double)(x + 5.0F), (double)(mY + 55.0F), 10.0D, (new Color(56, 56, 56)).getRGB(), 5);
            Gui.drawFilledCircle((double)(x + 75.0F), (double)(mY + 55.0F), 10.0D, (new Color(56, 56, 56)).getRGB(), 5);
            RenderingUtil.drawBorderRect(0.0D, 0.0D, 0.0D, 0.0D, color, 1.0D);
            font.drawStringWithShadow(String.valueOf(this.bind?"":"") + Keyboard.getKeyName(currentModule.getKey()), (double)(x + 40.0F - (float)(font.getStringWidth(Keyboard.getKeyName(currentModule.getKey())) / 2)), (double)(mY + 51.0F), -1);
         }

         if(this.isHovered(startX, startY + 50.0F, startX + 400.0F, startY + 70.0F, mouseX, mouseY) && Mouse.isButtonDown(0)) {
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
      }

      RenderingUtil.drawBorderRect(0.0D, 0.0D, 0.0D, 0.0D, (new Color(0, 0, 0, 0)).getRGB(), 1.0D);
      Gui.drawRect(0.0D, 0.0D, 160.0D, sr.getScaledHeight_double(), (new Color(32, 32, 32)).getRGB());
      Gui.drawRect(160.0D, 0.0D, 161.0D, sr.getScaledHeight_double(), color);
      RenderUtils.drawImage(new ResourceLocation("LAC/logo.png"), 35, 20, 85, 85);
      int j = 60;
      int l = 45;
      int k = 80;
      int xx = 40;

      for(int i = 0; i < ModuleType.values().length; ++i) {
         ModuleType[] iterator = ModuleType.values();
         if(iterator[i] != currentModuleType) {
            Gui.drawFilledCircle(35.0D, (double)(k + j + i * l), 15.0D, (new Color(88, 88, 88)).getRGB(), 5);
            Gui.drawFilledCircle(120.0D, (double)(k + j + i * l), 15.0D, (new Color(88, 88, 88)).getRGB(), 5);
            Gui.drawRect(35.0D, (double)(k - 15 + j + i * l), 120.0D, (double)(k + 15 + j + i * l), (new Color(88, 88, 88)).getRGB());
            RenderingUtil.drawBorderRect(0.0D, 0.0D, 0.0D, 0.0D, (new Color(0, 0, 0, 0)).getRGB(), 1.0D);
         } else {
            Gui.drawFilledCircle(35.0D, (double)(k + j + i * l), 15.0D, color, 5);
            Gui.drawFilledCircle(120.0D, (double)(k + j + i * l), 15.0D, color, 5);
            Gui.drawRect(35.0D, (double)(k - 15 + j + i * l), 120.0D, (double)(k + 15 + j + i * l), color);
            RenderingUtil.drawBorderRect(0.0D, 0.0D, 0.0D, 0.0D, (new Color(0, 0, 0, 0)).getRGB(), 1.0D);
         }

         HanabiRender.drawIcon((float)xx, (float)(k + 50 + l * i), 20, 20, new ResourceLocation("LAC/Clickicon/" + iterator[i].toString() + ".png"));
         FontLoaders.sansation24.drawString(iterator[i].toString(), (float)(xx + 25), (float)(k + 56 + l * i), (new Color(255, 255, 255)).getRGB());

         try {
            if(this.isCategoryHovered(15.0F, (float)(k - 10 + j + i * l), 120.0F, (float)(k + 20 + j + i * l), mouseX, mouseY) && Mouse.isButtonDown(0)) {
               currentModuleType = iterator[i];
               currentModule = (Module)ModuleManager.getModulesInType(currentModuleType).get(0);
               moduleStart = 0;
               valueStart = 0;
               this.rex = 0;
               this.rey = 0;

               for(int x = 0; x < currentModule.getValues().size(); ++x) {
                  Value value = (Value)currentModule.getValues().get(x);
                  if(value instanceof Option) {
                     ((Option)value).anim = 55;
                  }
               }

               for(Module mod : ModuleManager.getModulesInType(currentModuleType)) {
                  mod.clickanim = 115;
               }
            }
         } catch (Exception var25) {
            System.err.println(var25);
         }
      }

   }

   public void initGui() {
      for(int i = 0; i < currentModule.getValues().size(); ++i) {
         Value value = (Value)currentModule.getValues().get(i);
         if(value instanceof Option) {
            ((Option)value).anim = 55;
         }
      }

      for(Module mod : ModuleManager.getModulesInType(currentModuleType)) {
         mod.clickanim = 115;
      }

      WorldClient var10000 = Minecraft.theWorld;
      super.initGui();
   }

   public void keyTyped(char typedChar, int keyCode) throws IOException {
      if(this.bind) {
         currentModule.setKey(keyCode);
         if(keyCode == 1) {
            currentModule.setKey(0);
         }

         this.bind = false;
      } else if(keyCode == 1) {
         this.mc.displayGuiScreen((GuiScreen)null);
         ModuleManager.getModuleByClass(ClickGui.class).setEnabled(false);
         if(this.mc.currentScreen == null) {
            this.mc.setIngameFocus();
         }

         return;
      }

   }

   public void mouseClicked(int mouseX, int mouseY, int button) throws IOException {
      float x = startX + 220.0F;
      float mY = startY + 30.0F;

      for(int i = 0; i < currentModule.getValues().size() && mY <= startY + 350.0F; ++i) {
         if(i >= valueStart) {
            Value value = (Value)currentModule.getValues().get(i);
            if(value instanceof Numbers) {
               mY += 20.0F;
            }

            if(value instanceof Option) {
               mY += 20.0F;
            }

            if(value instanceof Mode) {
               mY += 25.0F;
            }
         }
      }

      if(mY < startY + 350.0F && (float)mouseX > x - 5.0F && (float)mouseX < x + 90.0F && (float)mouseY > mY + 45.0F && (float)mouseY < mY + 65.0F && button == 0) {
         this.bind = !this.bind;
      }

      super.mouseClicked(mouseX, mouseY, button);
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
      this.rex = 0;
      this.rey = 0;
      Minecraft.entityRenderer.switchUseShader();
   }
}
