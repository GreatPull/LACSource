package net.minecraft.entity.player.Really.Client.module.modules.player;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPacketSend;
import net.minecraft.entity.player.Really.Client.api.events.world.EventTick;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.BlockUtils;
import net.minecraft.util.BlockPos;

public class AutoTool extends Module {
   public AutoTool() {
      super("AutoTool", new String[]{"autoyool"}, ModuleType.Player);
   }

   public Class type() {
      return EventPacketSend.class;
   }

   @EventHandler
   public void onEvent(EventTick event) {
      Minecraft var10000 = mc;
      if(Minecraft.gameSettings.keyBindAttack.isKeyDown()) {
         var10000 = mc;
         if(Minecraft.objectMouseOver != null) {
            var10000 = mc;
            BlockPos pos = Minecraft.objectMouseOver.getBlockPos();
            if(pos != null) {
               BlockUtils.updateTool(pos);
            }
         }
      }
   }
}
