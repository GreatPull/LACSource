package net.minecraft.entity.player.Really.Client.management;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.Really.Client.api.EventBus;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.misc.EventKey;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender2D;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender3D;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.management.FileManager;
import net.minecraft.entity.player.Really.Client.management.Manager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.module.modules.Legit.GhostHand;
import net.minecraft.entity.player.Really.Client.module.modules.combat.AntiBot;
import net.minecraft.entity.player.Really.Client.module.modules.combat.ArmorBreaker;
import net.minecraft.entity.player.Really.Client.module.modules.combat.Aura;
import net.minecraft.entity.player.Really.Client.module.modules.combat.AutoHead;
import net.minecraft.entity.player.Really.Client.module.modules.combat.AutoPot;
import net.minecraft.entity.player.Really.Client.module.modules.combat.BowAimBot;
import net.minecraft.entity.player.Really.Client.module.modules.combat.Criticals;
import net.minecraft.entity.player.Really.Client.module.modules.combat.CustomAntiBot;
import net.minecraft.entity.player.Really.Client.module.modules.combat.MoreKnockBack;
import net.minecraft.entity.player.Really.Client.module.modules.combat.TNTBlock;
import net.minecraft.entity.player.Really.Client.module.modules.movement.ClickTp;
import net.minecraft.entity.player.Really.Client.module.modules.movement.Flight;
import net.minecraft.entity.player.Really.Client.module.modules.movement.Hopper;
import net.minecraft.entity.player.Really.Client.module.modules.movement.InvMove;
import net.minecraft.entity.player.Really.Client.module.modules.movement.Jesus;
import net.minecraft.entity.player.Really.Client.module.modules.movement.KeepSprint;
import net.minecraft.entity.player.Really.Client.module.modules.movement.Longjump;
import net.minecraft.entity.player.Really.Client.module.modules.movement.NoSlowDown;
import net.minecraft.entity.player.Really.Client.module.modules.movement.Scaffold;
import net.minecraft.entity.player.Really.Client.module.modules.movement.Sneak;
import net.minecraft.entity.player.Really.Client.module.modules.movement.Sprint;
import net.minecraft.entity.player.Really.Client.module.modules.player.AntiAim;
import net.minecraft.entity.player.Really.Client.module.modules.player.AntiFireBall;
import net.minecraft.entity.player.Really.Client.module.modules.player.AntiObs;
import net.minecraft.entity.player.Really.Client.module.modules.player.AntiVelocity;
import net.minecraft.entity.player.Really.Client.module.modules.player.AutoTool;
import net.minecraft.entity.player.Really.Client.module.modules.player.BetterGaming;
import net.minecraft.entity.player.Really.Client.module.modules.player.Bobbing;
import net.minecraft.entity.player.Really.Client.module.modules.player.Damage;
import net.minecraft.entity.player.Really.Client.module.modules.player.Disabler;
import net.minecraft.entity.player.Really.Client.module.modules.player.FastUse;
import net.minecraft.entity.player.Really.Client.module.modules.player.Freecam;
import net.minecraft.entity.player.Really.Client.module.modules.player.Fucker;
import net.minecraft.entity.player.Really.Client.module.modules.player.InvCleaner;
import net.minecraft.entity.player.Really.Client.module.modules.player.MCF;
import net.minecraft.entity.player.Really.Client.module.modules.player.NoCommand;
import net.minecraft.entity.player.Really.Client.module.modules.player.NoFall;
import net.minecraft.entity.player.Really.Client.module.modules.player.Teams;
import net.minecraft.entity.player.Really.Client.module.modules.render.Animations;
import net.minecraft.entity.player.Really.Client.module.modules.render.BlockOverlay;
import net.minecraft.entity.player.Really.Client.module.modules.render.Chams;
import net.minecraft.entity.player.Really.Client.module.modules.render.ChestESP;
import net.minecraft.entity.player.Really.Client.module.modules.render.ClickGui;
import net.minecraft.entity.player.Really.Client.module.modules.render.Crosshair;
import net.minecraft.entity.player.Really.Client.module.modules.render.ESP;
import net.minecraft.entity.player.Really.Client.module.modules.render.Emoji;
import net.minecraft.entity.player.Really.Client.module.modules.render.EnchantEffect;
import net.minecraft.entity.player.Really.Client.module.modules.render.FullBright;
import net.minecraft.entity.player.Really.Client.module.modules.render.HUD;
import net.minecraft.entity.player.Really.Client.module.modules.render.Health;
import net.minecraft.entity.player.Really.Client.module.modules.render.ItemEsp;
import net.minecraft.entity.player.Really.Client.module.modules.render.ItemPhysic;
import net.minecraft.entity.player.Really.Client.module.modules.render.Keyrender;
import net.minecraft.entity.player.Really.Client.module.modules.render.MemoryFixer;
import net.minecraft.entity.player.Really.Client.module.modules.render.Nametags;
import net.minecraft.entity.player.Really.Client.module.modules.render.NoHurtCam;
import net.minecraft.entity.player.Really.Client.module.modules.render.PlayerList;
import net.minecraft.entity.player.Really.Client.module.modules.render.SpookySkeltal;
import net.minecraft.entity.player.Really.Client.module.modules.render.TabGui;
import net.minecraft.entity.player.Really.Client.module.modules.render.TargetHUD;
import net.minecraft.entity.player.Really.Client.module.modules.render.Tracers;
import net.minecraft.entity.player.Really.Client.module.modules.render.ViewClip;
import net.minecraft.entity.player.Really.Client.module.modules.render.Xray;
import net.minecraft.entity.player.Really.Client.module.modules.render.setColor;
import net.minecraft.entity.player.Really.Client.module.modules.world.AntiFall;
import net.minecraft.entity.player.Really.Client.module.modules.world.AutoArmor;
import net.minecraft.entity.player.Really.Client.module.modules.world.AutoGG;
import net.minecraft.entity.player.Really.Client.module.modules.world.AutoL;
import net.minecraft.entity.player.Really.Client.module.modules.world.Blink;
import net.minecraft.entity.player.Really.Client.module.modules.world.ChestStealer;
import net.minecraft.entity.player.Really.Client.module.modules.world.FastPlace;
import net.minecraft.entity.player.Really.Client.module.modules.world.NoRotate;
import net.minecraft.entity.player.Really.Client.module.modules.world.PacketMotior;
import net.minecraft.entity.player.Really.Client.module.modules.world.PingSpoof;
import net.minecraft.entity.player.Really.Client.module.modules.world.SafeWalk;
import net.minecraft.entity.player.Really.Client.module.modules.world.SpeedMine;
import net.minecraft.entity.player.Really.Client.module.modules.world.WorldTime;
import net.minecraft.entity.player.Really.Client.utils.render.gl.GLUtils;
import org.lwjgl.input.Keyboard;

public class ModuleManager implements Manager {
   public static List<Module> modules = new ArrayList();
   private boolean enabledNeededMod = true;
   public boolean nicetry = true;
   public static boolean loaded = false;

   public void init() {
      modules.add(new AntiBot());
      modules.add(new Aura());
      modules.add(new AutoHead());
      modules.add(new ArmorBreaker());
      modules.add(new BowAimBot());
      modules.add(new Criticals());
      modules.add(new CustomAntiBot());
      modules.add(new AutoPot());
      modules.add(new MoreKnockBack());
      modules.add(new TNTBlock());
      modules.add(new ClickTp());
      modules.add(new Flight());
      modules.add(new InvMove());
      modules.add(new Jesus());
      modules.add(new Longjump());
      modules.add(new NoSlowDown());
      modules.add(new Scaffold());
      modules.add(new Hopper());
      modules.add(new KeepSprint());
      modules.add(new Sneak());
      modules.add(new Sprint());
      modules.add(new AntiAim());
      modules.add(new AntiFireBall());
      modules.add(new AntiObs());
      modules.add(new AntiVelocity());
      modules.add(new AutoTool());
      modules.add(new BetterGaming());
      modules.add(new Bobbing());
      modules.add(new Damage());
      modules.add(new Disabler());
      modules.add(new FastUse());
      modules.add(new Freecam());
      modules.add(new Fucker());
      modules.add(new InvCleaner());
      modules.add(new ItemPhysic());
      modules.add(new MCF());
      modules.add(new NoCommand());
      modules.add(new NoFall());
      modules.add(new Teams());
      modules.add(new Animations());
      modules.add(new BlockOverlay());
      modules.add(new Chams());
      modules.add(new ChestESP());
      modules.add(new ClickGui());
      modules.add(new setColor());
      modules.add(new Crosshair());
      modules.add(new EnchantEffect());
      modules.add(new ESP());
      modules.add(new Emoji());
      modules.add(new FullBright());
      modules.add(new HUD());
      modules.add(new Health());
      modules.add(new ItemEsp());
      modules.add(new Keyrender());
      modules.add(new MemoryFixer());
      modules.add(new Nametags());
      modules.add(new NoHurtCam());
      modules.add(new PlayerList());
      modules.add(new SpookySkeltal());
      modules.add(new TabGui());
      modules.add(new TargetHUD());
      modules.add(new Tracers());
      modules.add(new ViewClip());
      modules.add(new Xray());
      modules.add(new AntiFall());
      modules.add(new AutoArmor());
      modules.add(new AutoGG());
      modules.add(new AutoL());
      modules.add(new Blink());
      modules.add(new ChestStealer());
      modules.add(new FastPlace());
      modules.add(new NoRotate());
      modules.add(new PacketMotior());
      modules.add(new PingSpoof());
      modules.add(new SafeWalk());
      modules.add(new SpeedMine());
      modules.add(new WorldTime());
      modules.add(new GhostHand());
      this.readSettings();

      for(Module m : modules) {
         m.makeCommand();
      }

      EventBus.getInstance().register(new Object[]{this});
      loaded = true;

   }

   public static List<Module> getModules() {
      return modules;
   }

   public static Module getModuleByClass(Class class1) {
      for(Module m : modules) {
         if(m.getClass() == class1) {
            return m;
         }
      }

      return null;
   }

   public static Module getModuleByName(String name) {
      for(Module m : modules) {
         if(m.getName().equalsIgnoreCase(name)) {
            return m;
         }
      }

      return null;
   }

   public Module getAlias(String name) {
      for(Module f : modules) {
         if(f.getName().equalsIgnoreCase(name)) {
            return f;
         }

         for(String s : f.getAlias()) {
            if(s.equalsIgnoreCase(name)) {
               return f;
            }
         }
      }

      return null;
   }

   public static List<Module> getModulesInType(ModuleType t) {
      ArrayList<Module> output = new ArrayList();

      for(Module m : modules) {
         if(m.getType() == t) {
            output.add(m);
         }
      }

      return output;
   }

   @EventHandler
   private void onKeyPress(EventKey e) {
      for(Module m : modules) {
         if(m.getKey() == e.getKey()) {
            m.setEnabled(!m.isEnabled());
         }
      }

   }

   @EventHandler
   private void onGLHack(EventRender3D e) {
      GlStateManager.getFloat(2982, (FloatBuffer)GLUtils.MODELVIEW.clear());
      GlStateManager.getFloat(2983, (FloatBuffer)GLUtils.PROJECTION.clear());
      GlStateManager.glGetInteger(2978, (IntBuffer)GLUtils.VIEWPORT.clear());
   }

   @EventHandler
   private void on2DRender(EventRender2D e) {
      if(this.enabledNeededMod) {
         this.enabledNeededMod = false;

         for(Module m : modules) {
            if(m.enabledOnStartup) {
               m.setEnabled(true);
            }
         }
      }

   }

   private void readSettings() {
      for(String v : FileManager.read("Binds.txt")) {
         String name = v.split(":")[0];
         String bind = v.split(":")[1];
         Module m = getModuleByName(name);
         if(m != null) {
            m.setKey(Keyboard.getKeyIndex(bind.toUpperCase()));
         }
      }

      for(String v : FileManager.read("Enabled.txt")) {
         Module m = getModuleByName(v);
         if(m != null) {
            m.enabledOnStartup = true;
         }
      }

      for(String v : FileManager.read("Values.txt")) {
         String name = v.split(":")[0];
         String values = v.split(":")[1];
         Module m = getModuleByName(name);
         if(m != null) {
            for(Value value : m.getValues()) {
               if(value.getName().equalsIgnoreCase(values)) {
                  if(value instanceof Option) {
                     value.setValue(Boolean.valueOf(Boolean.parseBoolean(v.split(":")[2])));
                  } else if(value instanceof Numbers) {
                     value.setValue(Double.valueOf(Double.parseDouble(v.split(":")[2])));
                  } else {
                     ((Mode)value).setMode(v.split(":")[2]);
                  }
               }
            }
         }
      }

   }
}
