package net.minecraft.entity.player.Really.Client.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.api.Event;
import net.minecraft.entity.player.Really.Client.api.EventBus;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.command.Command;
import net.minecraft.entity.player.Really.Client.management.FileManager;
import net.minecraft.entity.player.Really.Client.management.ModuleManager;
import net.minecraft.entity.player.Really.Client.module.modules.render.HUD;
import net.minecraft.entity.player.Really.Client.utils.Helper;
import net.minecraft.entity.player.Really.Client.utils.math.MathUtil;
import net.minecraft.util.EnumChatFormatting;

public class Module {
   public String name;
   private String suffix;
   private int color;
   private String[] alias;
   private boolean enabled;
   public boolean enabledOnStartup = false;
   private int key;
   private int anim;
   public List<Value> values;
   public ModuleType type;
   private boolean removed;
   public int clickanim;
   public static Minecraft mc = Minecraft.getMinecraft();
   public static Random random = new Random();

   public Module(String name, String[] alias, ModuleType type) {
      this.name = name;
      this.alias = alias;
      this.type = type;
      this.suffix = "";
      this.key = 0;
      this.removed = false;
      this.enabled = false;
      this.values = new ArrayList();
   }

   public String getName() {
      return this.name;
   }

   public String[] getAlias() {
      return this.alias;
   }

   public ModuleType getType() {
      return this.type;
   }

   public boolean isEnabled() {
      return this.enabled;
   }

   public boolean wasRemoved() {
      return this.removed;
   }

   public void setRemoved(boolean removed) {
      this.removed = removed;
   }

   public String getSuffix() {
      return this.suffix;
   }

   public void setSuffix(Object obj) {
      String suffix = obj.toString();
      if(suffix.isEmpty()) {
         this.suffix = suffix;
      } else {
         this.suffix = String.format("Â§7 Â§f%sÂ§7", new Object[]{EnumChatFormatting.GRAY + suffix});
      }

   }

   public void setEnabled(boolean enable) {
      if(((Boolean)HUD.DisRender.getValue()).booleanValue() && this.getType() == ModuleType.Render) {
         this.setRemoved(true);
      }

      this.enabled = enable;
      if(enable) {
         this.onEnable();
         EventBus.getInstance().register(new Object[]{this});
         if(ModuleManager.loaded && ((Boolean)HUD.Sound.getValue()).booleanValue()) {
            Minecraft.thePlayer.playSound("random.click", 9.0F, this.enabled?0.7F:0.6F);
         }

         if(this.anim == -1) {
            this.anim = 0;
         }
      } else {
         EventBus.getInstance().unregister(new Object[]{this});
         if(ModuleManager.loaded && ((Boolean)HUD.Sound.getValue()).booleanValue()) {
            Minecraft.thePlayer.playSound("random.click", 9.0F, this.enabled?0.7F:0.6F);
         }

         this.onDisable();
      }

   }

   public int getAnim() {
      return this.anim;
   }

   public void setAnim(int anim) {
      this.anim = anim;
   }

   public void setColor(int color) {
      this.color = color;
   }

   public int getColor() {
      return this.color;
   }

   protected void addValues(Value... values) {
      for(Value value : values) {
         this.values.add(value);
      }

   }

   public List<Value> getValues() {
      return this.values;
   }

   public int getKey() {
      return this.key;
   }

   public void setKey(int key) {
      this.key = key;
      String content = "";
      Client var10000 = Client.instance;
      Client.getModuleManager();

      for(Module m : ModuleManager.getModules()) {
         content = content + String.format("%s:%s%s", new Object[]{m.getName(), Keyboard.getKeyName(m.getKey()), System.lineSeparator()});
      }

      FileManager.save("Binds.txt", content, false);
   }

   public void onEnable() {
   }

   public void onDisable() {
   }

   public void makeCommand() {
      if(this.values.size() > 0) {
         String options = "";
         String other = "";

         for(Value v : this.values) {
            if(!(v instanceof Mode)) {
               if(options.isEmpty()) {
                  options = String.valueOf(options) + v.getName();
               } else {
                  options = String.valueOf(options) + String.format(", %s", new Object[]{v.getName()});
               }
            }
         }

         for(Value v : this.values) {
            if(v instanceof Mode) {
               Mode mode = (Mode)v;

               Enum[] modes;
               for(Enum e : modes = mode.getModes()) {
                  if(other.isEmpty()) {
                     other = String.valueOf(other) + e.name().toLowerCase();
                  } else {
                     other = String.valueOf(other) + String.format(", %s", new Object[]{e.name().toLowerCase()});
                  }
               }
            }
         }

         Client.instance.getCommandManager().add(new Module$1(this, this.name, this.alias, String.format("%s%s", new Object[]{options.isEmpty()?"":String.format("%s,", new Object[]{options}), other.isEmpty()?"":String.format("%s", new Object[]{other})}), "Setup this module"));
      }
   }

   public void onEvent(Event event) {
   }
}

class Module$1 extends Command {
	   private final Module m;
	   final Module this$0;

	   Module$1(Module var1, String $anonymous0, String[] $anonymous1, String $anonymous2, String $anonymous3) {
	      super($anonymous0, $anonymous1, $anonymous2, $anonymous3);
	      this.this$0 = var1;
	      this.m = var1;
	   }

	   public String execute(String[] args) {
	      if(args.length >= 2) {
	         Option option = null;
	         Numbers fuck = null;
	         Mode xd = null;

	         for(Value v : this.m.values) {
	            if(v instanceof Option && v.getName().equalsIgnoreCase(args[0])) {
	               option = (Option)v;
	            }
	         }

	         if(option != null) {
	            option.setValue(Boolean.valueOf(!((Boolean)option.getValue()).booleanValue()));
	            Helper.sendMessage(String.format("> %s has been set to %s", new Object[]{option.getName(), option.getValue()}));
	         } else {
	            for(Value v : this.m.values) {
	               if(v instanceof Numbers && v.getName().equalsIgnoreCase(args[0])) {
	                  fuck = (Numbers)v;
	               }
	            }

	            if(fuck != null) {
	               if(MathUtil.parsable(args[1], (byte)4)) {
	                  double v1 = MathUtil.round(Double.parseDouble(args[1]), 1);
	                  fuck.setValue(Double.valueOf(v1 > ((Double)fuck.getMaximum()).doubleValue()?((Double)fuck.getMaximum()).doubleValue():v1));
	                  Helper.sendMessage(String.format("> %s has been set to %s", new Object[]{fuck.getName(), fuck.getValue()}));
	               } else {
	                  Helper.sendMessage("> " + args[1] + " is not a number");
	               }
	            }

	            for(Value v : this.m.values) {
	               if(args[0].equalsIgnoreCase(v.getDisplayName()) && v instanceof Mode) {
	                  xd = (Mode)v;
	               }
	            }

	            if(xd != null) {
	               if(xd.isValid(args[1])) {
	                  xd.setMode(args[1]);
	                  Helper.sendMessage(String.format("> %s set to %s", new Object[]{xd.getName(), xd.getModeAsString()}));
	               } else {
	                  Helper.sendMessage("> " + args[1] + " is an invalid mode");
	               }
	            }
	         }

	         if(fuck == null && option == null && xd == null) {
	            this.syntaxError("Valid .<module> <setting> <mode if needed>");
	         }
	      } else if(args.length >= 1) {
	         Option option = null;

	         for(Value fuck1 : this.m.values) {
	            if(fuck1 instanceof Option && fuck1.getName().equalsIgnoreCase(args[0])) {
	               option = (Option)fuck1;
	            }
	         }

	         if(option != null) {
	            option.setValue(Boolean.valueOf(!((Boolean)option.getValue()).booleanValue()));
	            String fuck2 = option.getName().substring(1);
	            String xd2 = option.getName().substring(0, 1).toUpperCase();
	            if(((Boolean)option.getValue()).booleanValue()) {
	               Helper.sendMessage(String.format("> %s has been set to ¡ìa%s", new Object[]{xd2 + fuck2, option.getValue()}));
	            } else {
	               Helper.sendMessage(String.format("> %s has been set to ¡ìc%s", new Object[]{xd2 + fuck2, option.getValue()}));
	            }
	         } else {
	            this.syntaxError("Valid .<module> <setting> <mode if needed>");
	         }
	      } else {
	         Helper.sendMessage(String.format("%s Values: \n %s", new Object[]{this.getName().substring(0, 1).toUpperCase() + this.getName().substring(1).toLowerCase(), this.getSyntax(), "false"}));
	      }

	      return null;
	   }
	}

