package net.minecraft.entity.player.Really.Client.module.modules.world;

import net.minecraft.entity.player.Really.Client.api.Event;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class EventDamageBlock extends Event {
   public BlockPos pos;
   public EnumFacing fac;
   public boolean cal;

   public EventDamageBlock(BlockPos p_180512_1_, EnumFacing p_180512_2_) {
      this.pos = p_180512_1_;
      this.fac = p_180512_2_;
      this.cal = false;
   }

   public BlockPos getpos() {
      return this.pos;
   }

   public EnumFacing getfac() {
      return this.fac;
   }

   public boolean isCancelled() {
      return this.cal;
   }

   public void setCancelled(boolean s) {
      this.cal = s;
   }
}
