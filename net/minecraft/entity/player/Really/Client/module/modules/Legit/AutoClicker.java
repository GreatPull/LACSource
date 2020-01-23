package net.minecraft.entity.player.Really.Client.module.modules.Legit;

import java.awt.Color;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;

public class AutoClicker extends Module {
   private static Numbers delay = new Numbers("Delay", "Delay", Double.valueOf(20.0D), Double.valueOf(0.0D), Double.valueOf(100.0D), Double.valueOf(1.0D));
   private TimerUtil timer = new TimerUtil();
   Random r = new Random();
   private long lastMS = 0L;

   public AutoClicker() {
      super("AutoClicker", new String[]{"AutoClicker", "AutoClicker"}, ModuleType.Legit);
      this.setColor((new Color(235, 194, 138)).getRGB());
      this.addValues(new Value[]{delay});
   }

   @EventHandler
   private void onUpdate(EventPreUpdate e2) {
      Minecraft var10000 = mc;
      if(Minecraft.gameSettings.keyBindAttack.pressed && this.isDelayComplete((long)(((Double)delay.getValue()).doubleValue() + (double)this.r.nextInt(80)))) {
         this.setLastMS();
         mc.clickMouse();
      }

   }

   public boolean isDelayComplete(long delay) {
      return System.currentTimeMillis() - this.lastMS >= delay;
   }

   public void setLastMS() {
      this.lastMS = System.currentTimeMillis();
   }
}
