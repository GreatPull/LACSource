package net.minecraft.entity.player.Really.Client.module.modules.player;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;

public class Bobbing extends Module {
   private Numbers boob = new Numbers("Amount", "Amount", Double.valueOf(1.0D), Double.valueOf(0.1D), Double.valueOf(5.0D), Double.valueOf(0.5D));

   public Bobbing() {
      super("Bobbing+", new String[]{"bobbing+"}, ModuleType.Player);
      this.addValues(new Value[]{this.boob});
   }

   @EventHandler
   public void onUpdate(EventPreUpdate event) {
      this.setColor((new Color(20, 200, 100)).getRGB());
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer.onGround) {
         var10000 = mc;
         Minecraft.thePlayer.cameraYaw = (float)(0.09090908616781235D * ((Double)this.boob.getValue()).doubleValue());
      }

   }
}
