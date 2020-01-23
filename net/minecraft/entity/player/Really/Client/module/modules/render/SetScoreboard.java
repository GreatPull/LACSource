package net.minecraft.entity.player.Really.Client.module.modules.render;

import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;

public class SetScoreboard extends Module {
   public static Option hideboard = new Option("hideboard", "hideboard", Boolean.valueOf(false));
   public static Option fastbord = new Option("fastbord", "fastbord", Boolean.valueOf(false));
   public static Option Norednumber = new Option("Norednumber", "Norednumber", Boolean.valueOf(false));
   public static Option noServername = new Option("noServername", "noServername", Boolean.valueOf(false));
   public static Option noanyfont = new Option("noanyfont", "noanyfont", Boolean.valueOf(false));
   public static Numbers X = new Numbers("X", "X", Double.valueOf(4.5D), Double.valueOf(0.0D), Double.valueOf(1000.0D), Double.valueOf(1.0D));
   public static Numbers Y = new Numbers("Y", "Y", Double.valueOf(4.5D), Double.valueOf(-300.0D), Double.valueOf(300.0D), Double.valueOf(1.0D));

   public SetScoreboard() {
      super("Scoreboard", new String[]{"SetScoreboard"}, ModuleType.Render);
      this.addValues(new Value[]{X, Y, hideboard, fastbord, Norednumber, noServername, noanyfont});
   }
}
