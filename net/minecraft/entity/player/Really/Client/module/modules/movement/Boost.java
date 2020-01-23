package net.minecraft.entity.player.Really.Client.module.modules.movement;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;
import net.minecraft.util.Timer;

public class Boost extends Module {
   private TimerUtil timer = new TimerUtil();

   public Boost() {
      super("Boost", new String[]{"boost"}, ModuleType.Movement);
      this.setColor((new Color(216, 253, 100)).getRGB());
   }

   @EventHandler
   public void onUpdate(EventPreUpdate event) {
      Timer var10000 = mc.timer;
      Timer.timerSpeed = 3.0F;
      Minecraft var2 = mc;
      if(Minecraft.thePlayer.ticksExisted % 15 == 0) {
         this.setEnabled(false);
      }

   }

   public void onDisable() {
      this.timer.reset();
      Timer var10000 = mc.timer;
      Timer.timerSpeed = 1.0F;
   }
}
