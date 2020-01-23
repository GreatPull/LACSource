package net.minecraft.entity.player.Really.Client.module.modules.render;

import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;

public class Animations extends Module {
   public static Mode mode = new Mode("Mode", "mode", Animations.renderMode.values(), Animations.renderMode.Slide);
   public static Option NoFire = new Option("NoFire", "NoFire", Boolean.valueOf(false));
   public static Option EveryThingBlock = new Option("EveryThingBlock", "EveryThingBlock", Boolean.valueOf(false));
   public static Numbers x = new Numbers("x", "x", Double.valueOf(0.0D), Double.valueOf(-1.0D), Double.valueOf(1.0D), Double.valueOf(0.1D));
   public static Numbers y = new Numbers("y", "y", Double.valueOf(0.0D), Double.valueOf(-1.0D), Double.valueOf(1.0D), Double.valueOf(0.1D));
   public static Numbers z = new Numbers("z", "z", Double.valueOf(0.0D), Double.valueOf(-1.0D), Double.valueOf(1.0D), Double.valueOf(0.1D));

   public Animations() {
      super("Animations", new String[]{"BlockHitanimations"}, ModuleType.Render);
      this.addValues(new Value[]{mode, NoFire, x, y, z, EveryThingBlock});
      this.setEnabled(true);
   }

   public static enum renderMode {
      Remix,
      tem,
      Custom,
      Sigma,
      Slide,
      Poke,
      None,
      Old,
      Gay,
      Punch,
      Aris,
      Down,
      XIV,
      Winter,
      Inertia,
      Random,
      Conceit;
   }
}
