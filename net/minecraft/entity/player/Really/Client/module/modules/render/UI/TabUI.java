package net.minecraft.entity.player.Really.Client.module.modules.render.UI;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.api.EventBus;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.misc.EventKey;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender2D;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.management.Manager;
import net.minecraft.entity.player.Really.Client.management.ModuleManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.module.modules.render.HUD;
import net.minecraft.entity.player.Really.Client.ui.font.CFontRenderer;
import net.minecraft.entity.player.Really.Client.ui.font.FontLoaders;
import net.minecraft.entity.player.Really.Client.utils.Helper;
import net.minecraft.entity.player.Really.Client.utils.math.MathUtil;
import net.minecraft.entity.player.Really.Client.utils.render.RenderUtil;

public class TabUI implements Manager {
   private TabUI.Section section = TabUI.Section.TYPES;
   private ModuleType selectedType = ModuleType.values()[0];
   private Module selectedModule = null;
   private Value selectedValue = null;
   private int currentType = 0;
   private int currentModule = 0;
   private int currentValue = 0;
   private int height = 12;
   private int maxType;
   private int maxModule;
   private int maxValue;
   // $FF: synthetic field
   private static int[] $SWITCH_TABLE$net$minecraft$entity$player$Really$Client$module$modules$render$UI$TabUI$Section;

   public void init() {
      ModuleType[] var4;
      for(ModuleType mt : var4 = ModuleType.values()) {
         if(this.maxType <= Minecraft.fontRendererObj.getStringWidth(mt.name().toUpperCase()) + 4) {
            this.maxType = Minecraft.fontRendererObj.getStringWidth(mt.name().toUpperCase()) + 4;
         }
      }

      Client var10000 = Client.instance;
      Client.getModuleManager();

      for(Module m : ModuleManager.getModules()) {
         if(this.maxModule <= Minecraft.fontRendererObj.getStringWidth(m.getName().toUpperCase()) + 4) {
            this.maxModule = Minecraft.fontRendererObj.getStringWidth(m.getName().toUpperCase()) + 4;
         }
      }

      var10000 = Client.instance;
      Client.getModuleManager();

      for(Module m : ModuleManager.getModules()) {
         if(!m.getValues().isEmpty()) {
            for(Value val : m.getValues()) {
               if(this.maxValue <= Minecraft.fontRendererObj.getStringWidth(val.getDisplayName().toUpperCase()) + 4) {
                  this.maxValue = Minecraft.fontRendererObj.getStringWidth(val.getDisplayName().toUpperCase()) + 4;
               }
            }
         }
      }

      this.maxModule += 12;
      this.maxValue += 24;
      int highestWidth = 0;
      this.maxType = this.maxType < this.maxModule?this.maxModule:this.maxType;
      this.maxModule += this.maxType;
      this.maxValue += this.maxModule;
      EventBus.getInstance().register(new Object[]{this});
   }

   private void resetValuesLength() {
      this.maxValue = 0;

      for(Value val : this.selectedModule.getValues()) {
         int off = val instanceof Option?6:Minecraft.fontRendererObj.getStringWidth(String.format(" §7%s", new Object[]{val.getValue().toString()})) + 6;
         if(this.maxValue <= Minecraft.fontRendererObj.getStringWidth(val.getDisplayName().toUpperCase()) + off) {
            this.maxValue = Minecraft.fontRendererObj.getStringWidth(val.getDisplayName().toUpperCase()) + off;
         }
      }

      this.maxValue += this.maxModule;
   }

   @EventHandler
   private void renderTabGUI(EventRender2D e) {
      CFontRenderer font = FontLoaders.sansation18;
      if(HUD.useFont) {
         if(!Minecraft.gameSettings.showDebugInfo) {
            Client var10000 = Client.instance;
            Client.getModuleManager();
            if(ModuleManager.getModuleByName("HUD").isEnabled()) {
               var10000 = Client.instance;
               Client.getModuleManager();
               if(ModuleManager.getModuleByName("Tabgui").isEnabled()) {
                  var10000 = Client.instance;
                  Client.getModuleManager();
                  HUD i1 = (HUD)ModuleManager.getModuleByName("HUD");
                  int categoryY;
                  int moduleY = categoryY = ((Boolean)HUD.Logo.getValue()).booleanValue()?82:18;
                  int valueY = categoryY;
                  RenderUtil.drawBorderedRect(2.0F, (double)categoryY, (float)(this.maxType - 25), (double)(categoryY + 12 * ModuleType.values().length), 2.0F, (new Color(0, 0, 0, 130)).getRGB(), (new Color(0, 0, 0, 180)).getRGB());

                  for(ModuleType mt : ModuleType.values()) {
                     if(this.selectedType == mt) {
                        Gui.drawRect(2.5D, (double)categoryY + 0.5D, (double)this.maxType - 25.5D, (double)(categoryY + Minecraft.fontRendererObj.FONT_HEIGHT) + 2.5D, (new Color(102, 172, 255)).getRGB());
                        moduleY = categoryY;
                     }

                     if(this.selectedType == mt) {
                        font.drawStringWithShadow(mt.name(), 7.0D, (double)(categoryY + 3), -1);
                     } else {
                        font.drawStringWithShadow(mt.name(), 5.0D, (double)(categoryY + 3), (new Color(180, 180, 180)).getRGB());
                     }

                     categoryY += 12;
                  }

                  if(this.section == TabUI.Section.MODULES || this.section == TabUI.Section.VALUES) {
                     float var30 = (float)(this.maxType - 20);
                     double var10001 = (double)moduleY;
                     float var10002 = (float)(this.maxModule - 38);
                     Client var10005 = Client.instance;
                     Client.getModuleManager();
                     RenderUtil.drawBorderedRect(var30, var10001, var10002, (double)(moduleY + 12 * ModuleManager.getModulesInType(this.selectedType).size()), 2.0F, (new Color(0, 0, 0, 130)).getRGB(), (new Color(0, 0, 0, 180)).getRGB());
                     Client var31 = Client.instance;
                     Client.getModuleManager();

                     for(Module m : ModuleManager.getModulesInType(this.selectedType)) {
                        if(this.selectedModule == m) {
                           Gui.drawRect((double)this.maxType - 19.5D, (double)moduleY + 0.5D, (double)this.maxModule - 38.5D, (double)(moduleY + Minecraft.fontRendererObj.FONT_HEIGHT) + 2.5D, (new Color(102, 172, 255)).getRGB());
                           valueY = moduleY;
                        }

                        if(this.selectedModule == m) {
                           font.drawStringWithShadow(m.getName(), (double)(this.maxType - 15), (double)(moduleY + 3), m.isEnabled()?-1:11184810);
                        } else {
                           font.drawStringWithShadow(m.getName(), (double)(this.maxType - 17), (double)(moduleY + 3), m.isEnabled()?-1:11184810);
                        }

                        if(!m.getValues().isEmpty()) {
                           Gui.drawRect((double)(this.maxModule - 38), (double)moduleY + 0.5D, (double)(this.maxModule - 39), (double)(moduleY + Minecraft.fontRendererObj.FONT_HEIGHT) + 2.5D, (new Color(153, 200, 255)).getRGB());
                           if(this.section == TabUI.Section.VALUES && this.selectedModule == m) {
                              RenderUtil.drawBorderedRect((float)(this.maxModule - 32), (double)valueY, (float)(this.maxValue - 25), (double)(valueY + 12 * this.selectedModule.getValues().size()), 2.0F, (new Color(10, 10, 10, 180)).getRGB(), (new Color(10, 10, 10, 180)).getRGB());

                              for(Value val : this.selectedModule.getValues()) {
                                 Gui.drawRect((double)this.maxModule - 31.5D, (double)valueY + 0.5D, (double)this.maxValue - 25.5D, (double)(valueY + Minecraft.fontRendererObj.FONT_HEIGHT) + 2.5D, this.selectedValue == val?(new Color(102, 172, 255)).getRGB():0);
                                 if(val instanceof Option) {
                                    font.drawStringWithShadow(val.getDisplayName(), (double)(this.selectedValue == val?this.maxModule - 27:this.maxModule - 29), (double)(valueY + 3), ((Boolean)val.getValue()).booleanValue()?(new Color(153, 200, 255)).getRGB():11184810);
                                 } else {
                                    String toRender = String.format("%s: §7%s", new Object[]{val.getDisplayName(), val.getValue().toString()});
                                    if(this.selectedValue == val) {
                                       font.drawStringWithShadow(toRender, (double)(this.maxModule - 27), (double)(valueY + 3), -1);
                                    } else {
                                       font.drawStringWithShadow(toRender, (double)(this.maxModule - 29), (double)(valueY + 3), -1);
                                    }
                                 }

                                 valueY += 12;
                              }
                           }
                        }

                        moduleY += 12;
                     }
                  }
               }
            }
         }
      } else if(!Minecraft.gameSettings.showDebugInfo) {
         Client var32 = Client.instance;
         Client.getModuleManager();
         if(ModuleManager.getModuleByName("HUD").isEnabled()) {
            int categoryY;
            int moduleY = categoryY = HUD.shouldMove?26:this.height;
            int valueY = categoryY;
            RenderUtil.drawBorderedRect(2.0F, (double)categoryY, (float)(this.maxType - 25), (double)(categoryY + 12 * ModuleType.values().length), 2.0F, (new Color(0, 0, 0, 130)).getRGB(), (new Color(0, 0, 0, 180)).getRGB());

            for(ModuleType mt : ModuleType.values()) {
               if(this.selectedType == mt) {
                  Gui.drawRect(2.5D, (double)categoryY + 0.5D, (double)this.maxType - 25.5D, (double)(categoryY + Minecraft.fontRendererObj.FONT_HEIGHT) + 2.5D, (new Color(102, 172, 255)).getRGB());
                  moduleY = categoryY;
               }

               if(this.selectedType == mt) {
                  Minecraft.fontRendererObj.drawStringWithShadow(mt.name(), 7.0F, (float)(categoryY + 2), -1);
               } else {
                  Minecraft.fontRendererObj.drawStringWithShadow(mt.name(), 5.0F, (float)(categoryY + 2), (new Color(180, 180, 180)).getRGB());
               }

               categoryY += 12;
            }

            if(this.section == TabUI.Section.MODULES || this.section == TabUI.Section.VALUES) {
               float var33 = (float)(this.maxType - 20);
               double var35 = (double)moduleY;
               float var36 = (float)(this.maxModule - 38);
               Client var37 = Client.instance;
               Client.getModuleManager();
               RenderUtil.drawBorderedRect(var33, var35, var36, (double)(moduleY + 12 * ModuleManager.getModulesInType(this.selectedType).size()), 2.0F, (new Color(0, 0, 0, 130)).getRGB(), (new Color(0, 0, 0, 180)).getRGB());
               Client var34 = Client.instance;
               Client.getModuleManager();

               for(Module m : ModuleManager.getModulesInType(this.selectedType)) {
                  if(this.selectedModule == m) {
                     Gui.drawRect((double)this.maxType - 19.5D, (double)moduleY + 0.5D, (double)this.maxModule - 38.5D, (double)(moduleY + Minecraft.fontRendererObj.FONT_HEIGHT) + 2.5D, (new Color(102, 172, 255)).getRGB());
                     valueY = moduleY;
                  }

                  if(this.selectedModule == m) {
                     Minecraft.fontRendererObj.drawStringWithShadow(m.getName(), (float)(this.maxType - 15), (float)(moduleY + 2), m.isEnabled()?-1:11184810);
                  } else {
                     Minecraft.fontRendererObj.drawStringWithShadow(m.getName(), (float)(this.maxType - 17), (float)(moduleY + 2), m.isEnabled()?-1:11184810);
                  }

                  if(!m.getValues().isEmpty()) {
                     Gui.drawRect((double)(this.maxModule - 38), (double)moduleY + 0.5D, (double)(this.maxModule - 39), (double)(moduleY + Minecraft.fontRendererObj.FONT_HEIGHT) + 2.5D, (new Color(153, 200, 255)).getRGB());
                     if(this.section == TabUI.Section.VALUES && this.selectedModule == m) {
                        RenderUtil.drawBorderedRect((float)(this.maxModule - 32), (double)valueY, (float)(this.maxValue - 25), (double)(valueY + 12 * this.selectedModule.getValues().size()), 2.0F, (new Color(10, 10, 10, 180)).getRGB(), (new Color(10, 10, 10, 180)).getRGB());

                        for(Value val : this.selectedModule.getValues()) {
                           Gui.drawRect((double)this.maxModule - 31.5D, (double)valueY + 0.5D, (double)this.maxValue - 25.5D, (double)(valueY + Minecraft.fontRendererObj.FONT_HEIGHT) + 2.5D, this.selectedValue == val?(new Color(102, 172, 255)).getRGB():0);
                           if(val instanceof Option) {
                              Minecraft.fontRendererObj.drawStringWithShadow(val.getDisplayName(), (float)(this.selectedValue == val?this.maxModule - 27:this.maxModule - 29), (float)(valueY + 2), ((Boolean)val.getValue()).booleanValue()?(new Color(153, 200, 255)).getRGB():11184810);
                           } else {
                              String toRender = String.format("%s: §7%s", new Object[]{val.getDisplayName(), val.getValue().toString()});
                              if(this.selectedValue == val) {
                                 Minecraft.fontRendererObj.drawStringWithShadow(toRender, (float)(this.maxModule - 27), (float)(valueY + 2), -1);
                              } else {
                                 Minecraft.fontRendererObj.drawStringWithShadow(toRender, (float)(this.maxModule - 29), (float)(valueY + 2), -1);
                              }
                           }

                           valueY += 12;
                        }
                     }
                  }

                  moduleY += 12;
               }
            }
         }
      }

   }

   @EventHandler
   private void onKey(EventKey e) {
      if(!Minecraft.gameSettings.showDebugInfo) {
         switch(e.getKey()) {
         case 28:
            switch($SWITCH_TABLE$net$minecraft$entity$player$Really$Client$module$modules$render$UI$TabUI$Section()[this.section.ordinal()]) {
            case 1:
            default:
               return;
            case 2:
               this.selectedModule.setEnabled(!this.selectedModule.isEnabled());
               return;
            case 3:
               this.section = TabUI.Section.MODULES;
               return;
            }
         case 200:
            switch($SWITCH_TABLE$net$minecraft$entity$player$Really$Client$module$modules$render$UI$TabUI$Section()[this.section.ordinal()]) {
            case 1:
               --this.currentType;
               if(this.currentType < 0) {
                  this.currentType = ModuleType.values().length - 1;
               }

               this.selectedType = ModuleType.values()[this.currentType];
               return;
            case 2:
               --this.currentModule;
               if(this.currentModule < 0) {
                  Client var23 = Client.instance;
                  Client.getModuleManager();
                  this.currentModule = ModuleManager.getModulesInType(this.selectedType).size() - 1;
               }

               Client var24 = Client.instance;
               Client.getModuleManager();
               this.selectedModule = (Module)ModuleManager.getModulesInType(this.selectedType).get(this.currentModule);
               return;
            case 3:
               --this.currentValue;
               if(this.currentValue < 0) {
                  this.currentValue = this.selectedModule.getValues().size() - 1;
               }

               this.selectedValue = (Value)this.selectedModule.getValues().get(this.currentValue);
               return;
            default:
               return;
            }
         case 203:
            switch($SWITCH_TABLE$net$minecraft$entity$player$Really$Client$module$modules$render$UI$TabUI$Section()[this.section.ordinal()]) {
            case 1:
            default:
               return;
            case 2:
               this.section = TabUI.Section.TYPES;
               this.currentModule = 0;
               return;
            case 3:
               if(!Helper.onServer("enjoytheban")) {
                  if(this.selectedValue instanceof Option) {
                     this.selectedValue.setValue(Boolean.valueOf(!((Boolean)this.selectedValue.getValue()).booleanValue()));
                  } else if(this.selectedValue instanceof Numbers) {
                     Numbers value = (Numbers)this.selectedValue;
                     double inc = ((Double)value.getValue()).doubleValue();
                     inc = inc - ((Double)value.getIncrement()).doubleValue();
                     inc = MathUtil.toDecimalLength(inc, 1);
                     if(inc < ((Double)value.getMinimum()).doubleValue()) {
                        inc = ((Double)((Numbers)this.selectedValue).getMaximum()).doubleValue();
                     }

                     this.selectedValue.setValue(Double.valueOf(inc));
                  } else if(this.selectedValue instanceof Mode) {
                     Mode theme = (Mode)this.selectedValue;
                     Enum current = (Enum)theme.getValue();
                     int next = current.ordinal() - 1 < 0?theme.getModes().length - 1:current.ordinal() - 1;
                     this.selectedValue.setValue(theme.getModes()[next]);
                  }

                  this.maxValue = 0;

                  for(Value val : this.selectedModule.getValues()) {
                     int var19;
                     if(val instanceof Option) {
                        var19 = 6;
                     } else {
                        Minecraft.getMinecraft();
                        var19 = Minecraft.fontRendererObj.getStringWidth(String.format(" §7%s", new Object[]{val.getValue().toString()})) + 6;
                     }

                     int off = var19;
                     var19 = this.maxValue;
                     Minecraft.getMinecraft();
                     if(var19 <= Minecraft.fontRendererObj.getStringWidth(val.getDisplayName().toUpperCase()) + off) {
                        Minecraft.getMinecraft();
                        this.maxValue = Minecraft.fontRendererObj.getStringWidth(val.getDisplayName().toUpperCase()) + off;
                     }
                  }

                  this.maxValue += this.maxModule;
               }

               return;
            }
         case 205:
            switch($SWITCH_TABLE$net$minecraft$entity$player$Really$Client$module$modules$render$UI$TabUI$Section()[this.section.ordinal()]) {
            case 1:
               this.currentModule = 0;
               Client var22 = Client.instance;
               Client.getModuleManager();
               this.selectedModule = (Module)ModuleManager.getModulesInType(this.selectedType).get(this.currentModule);
               this.section = TabUI.Section.MODULES;
               return;
            case 2:
               if(!this.selectedModule.getValues().isEmpty()) {
                  this.resetValuesLength();
                  this.currentValue = 0;
                  this.selectedValue = (Value)this.selectedModule.getValues().get(this.currentValue);
                  this.section = TabUI.Section.VALUES;
               }

               return;
            case 3:
               if(!Helper.onServer("enjoytheban")) {
                  if(this.selectedValue instanceof Option) {
                     this.selectedValue.setValue(Boolean.valueOf(!((Boolean)this.selectedValue.getValue()).booleanValue()));
                  } else if(this.selectedValue instanceof Numbers) {
                     Numbers value = (Numbers)this.selectedValue;
                     double inc = ((Double)value.getValue()).doubleValue();
                     inc = inc + ((Double)value.getIncrement()).doubleValue();
                     inc = MathUtil.toDecimalLength(inc, 1);
                     if(inc > ((Double)value.getMaximum()).doubleValue()) {
                        inc = ((Double)((Numbers)this.selectedValue).getMinimum()).doubleValue();
                     }

                     this.selectedValue.setValue(Double.valueOf(inc));
                  } else if(this.selectedValue instanceof Mode) {
                     Mode theme = (Mode)this.selectedValue;
                     Enum current = (Enum)theme.getValue();
                     int next = current.ordinal() + 1 >= theme.getModes().length?0:current.ordinal() + 1;
                     this.selectedValue.setValue(theme.getModes()[next]);
                  }

                  this.resetValuesLength();
               }

               return;
            default:
               return;
            }
         case 208:
            switch($SWITCH_TABLE$net$minecraft$entity$player$Really$Client$module$modules$render$UI$TabUI$Section()[this.section.ordinal()]) {
            case 1:
               ++this.currentType;
               if(this.currentType > ModuleType.values().length - 1) {
                  this.currentType = 0;
               }

               this.selectedType = ModuleType.values()[this.currentType];
               break;
            case 2:
               ++this.currentModule;
               int var10000 = this.currentModule;
               Client var10001 = Client.instance;
               Client.getModuleManager();
               if(var10000 > ModuleManager.getModulesInType(this.selectedType).size() - 1) {
                  this.currentModule = 0;
               }

               var10001 = Client.instance;
               Client.getModuleManager();
               this.selectedModule = (Module)ModuleManager.getModulesInType(this.selectedType).get(this.currentModule);
               break;
            case 3:
               ++this.currentValue;
               if(this.currentValue > this.selectedModule.getValues().size() - 1) {
                  this.currentValue = 0;
               }

               this.selectedValue = (Value)this.selectedModule.getValues().get(this.currentValue);
            }
         }
      }

   }

   // $FF: synthetic method
   static int[] $SWITCH_TABLE$net$minecraft$entity$player$Really$Client$module$modules$render$UI$TabUI$Section() {
      int[] var10000 = $SWITCH_TABLE$net$minecraft$entity$player$Really$Client$module$modules$render$UI$TabUI$Section;
      if($SWITCH_TABLE$net$minecraft$entity$player$Really$Client$module$modules$render$UI$TabUI$Section != null) {
         return var10000;
      } else {
         int[] var0 = new int[TabUI.Section.values().length];

         try {
            var0[TabUI.Section.MODULES.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
            ;
         }

         try {
            var0[TabUI.Section.TYPES.ordinal()] = 1;
         } catch (NoSuchFieldError var2) {
            ;
         }

         try {
            var0[TabUI.Section.VALUES.ordinal()] = 3;
         } catch (NoSuchFieldError var1) {
            ;
         }

         $SWITCH_TABLE$net$minecraft$entity$player$Really$Client$module$modules$render$UI$TabUI$Section = var0;
         return var0;
      }
   }

   public static enum Section {
      TYPES,
      MODULES,
      VALUES;
   }
}
