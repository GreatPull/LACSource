package net.minecraft.entity.player.Really.Client.module.modules.movement;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;

public class Sprint extends Module {
   private Option omni = new Option("MultiDirectional", "MultiDirectional", Boolean.valueOf(true));

   public Sprint() {
      super("Sprint", new String[]{"run"}, ModuleType.Movement);
      this.setColor((new Color(158, 205, 125)).getRGB());
      this.addValues(new Value[]{this.omni});
   }

   @EventHandler
   private void onUpdate(EventPreUpdate event) {
      Minecraft var10000 = mc;
      Minecraft.thePlayer.setSprinting(this.canSprint());
   }

   private boolean canSprint() {
      Minecraft var10000 = mc;
      if(!Minecraft.thePlayer.isCollidedHorizontally) {
         var10000 = mc;
         if(!Minecraft.thePlayer.isSneaking()) {
            var10000 = mc;
            if(Minecraft.thePlayer.getFoodStats().getFoodLevel() > 6) {
               if(((Boolean)this.omni.getValue()).booleanValue()) {
                  var10000 = mc;
                  if(Minecraft.thePlayer.moving()) {
                     return true;
                  }
               } else {
                  var10000 = mc;
                  if(Minecraft.thePlayer.moveForward > 0.0F) {
                     return true;
                  }
               }
            }
         }
      }

      return false;
   }
}
