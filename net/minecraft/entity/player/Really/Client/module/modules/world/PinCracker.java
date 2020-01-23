package net.minecraft.entity.player.Really.Client.module.modules.world;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;

public class PinCracker extends Module {
   private TimerUtil time = new TimerUtil();
   int num;
   private Option login = new Option("/login?", "login", Boolean.valueOf(false));
   private Numbers delay = new Numbers("Delay", "Delay", Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(20.0D), Double.valueOf(1.0D));

   public PinCracker() {
      super("PinCracker", new String[]{"pincracker"}, ModuleType.World);
      this.addValues(new Value[]{this.login, this.delay});
   }

   @EventHandler
   public void onUpdate(EventPreUpdate event) {
      this.setColor((new Color(200, 200, 100)).getRGB());
      if(((Boolean)this.login.getValue()).booleanValue()) {
         if(this.time.delay((float)(((Double)this.delay.getValue()).doubleValue() * 100.0D))) {
            Minecraft var10000 = mc;
            Minecraft.thePlayer.sendChatMessage("/login " + this.numbers());
            this.time.reset();
         }
      } else if(this.time.delay((float)(((Double)this.delay.getValue()).doubleValue() * 100.0D))) {
         Minecraft var2 = mc;
         Minecraft.thePlayer.sendChatMessage("/pin " + this.numbers());
         this.time.reset();
      }

   }

   private int numbers() {
      if(this.num <= 10000) {
         ++this.num;
      }

      return this.num;
   }

   public void onDisable() {
      this.num = 0;
      super.onDisable();
   }
}
