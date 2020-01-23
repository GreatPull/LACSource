package net.minecraft.entity.player.Really.Client.module.modules.world;

import java.awt.Color;
import net.minecraft.block.BlockAir;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.BlockPos;

public class AntiVoid extends Module {
   public AntiVoid() {
      super("AntiVoid", new String[]{"novoid", "antifall"}, ModuleType.World);
      this.setColor((new Color(223, 233, 233)).getRGB());
   }

   @EventHandler
   private void onUpdate(EventPreUpdate e2) {
      boolean blockUnderneath = false;

      for(int i2 = 0; (double)i2 < Minecraft.thePlayer.posY + 8.0D; ++i2) {
         BlockPos pos = new BlockPos(Minecraft.thePlayer.posX, (double)i2, Minecraft.thePlayer.posZ);
         if(!(Minecraft.theWorld.getBlockState(pos).getBlock() instanceof BlockAir)) {
            blockUnderneath = true;
         }
      }

      if(blockUnderneath && e2.getPacket() instanceof C03PacketPlayer && Minecraft.thePlayer.fallDistance > 3.0F) {
         EventPreUpdate.packet.onGround = true;
      }

   }
}
