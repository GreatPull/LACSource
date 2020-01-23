package net.minecraft.entity.player.Really.Client.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.module.modules.render.ESP;

public class Line extends Module {
   private ArrayList points;
   private Option animals;
   private Option mobs;

   public Line() {
      super("Line", new String[]{"outline", "wallhack"}, ModuleType.Render);
      this.setColor((new Color(ESP.random.nextInt(255), ESP.random.nextInt(255), ESP.random.nextInt(255))).getRGB());
   }
}
