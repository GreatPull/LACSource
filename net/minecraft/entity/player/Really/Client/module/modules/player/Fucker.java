package net.minecraft.entity.player.Really.Client.module.modules.player;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class Fucker extends Module {
   private int xPos;
   private int yPos;
   private int zPos;
   private static int radius = 5;
   private Mode mode = new Mode("Mode", "Mode", Fucker.Break.values(), Fucker.Break.Bed);

   public Fucker() {
      super("Fucker", new String[]{"fplace", "fc"}, ModuleType.Player);
      this.addValues(new Value[]{this.mode});
   }

   @EventHandler
   private void onUpdate(EventPreUpdate event) {
      this.setSuffix(this.mode.getValue());

      for(int x = -radius; x < radius; ++x) {
         for(int y = radius; y > -radius; --y) {
            for(int z = -radius; z < radius; ++z) {
               this.xPos = (int)Minecraft.thePlayer.posX + x;
               this.yPos = (int)Minecraft.thePlayer.posY + y;
               this.zPos = (int)Minecraft.thePlayer.posZ + z;
               BlockPos blockPos = new BlockPos(this.xPos, this.yPos, this.zPos);
               Block block = Minecraft.theWorld.getBlockState(blockPos).getBlock();
               if(block.getBlockState().getBlock() == Block.getBlockById(92) && this.mode.getValue() == Fucker.Break.Cake) {
                  Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                  Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
               } else if(block.getBlockState().getBlock() == Block.getBlockById(122) && this.mode.getValue() == Fucker.Break.Egg) {
                  Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                  Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
               } else if(block.getBlockState().getBlock() == Block.getBlockById(26) && this.mode.getValue() == Fucker.Break.Bed) {
                  Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
                  Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, blockPos, EnumFacing.NORTH));
               }
            }
         }
      }

   }

   static enum Break {
      Cake,
      Egg,
      Bed;
   }
}
