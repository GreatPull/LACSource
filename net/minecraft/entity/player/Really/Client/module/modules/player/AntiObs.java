package net.minecraft.entity.player.Really.Client.module.modules.player;

import java.awt.Color;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Vec3;

public class AntiObs extends Module {
   private Option Auto = new Option("Auto", "Auto", Boolean.valueOf(false));

   public AntiObs() {
      super("AntiObsidian", new String[]{"AntiObs"}, ModuleType.Player);
      super.addValues(new Value[]{this.Auto});
      this.setColor((new Color(208, 30, 142)).getRGB());
   }

   @EventHandler
   public void OnUpdate(EventPreUpdate e) {
      if(((Boolean)this.Auto.getValue()).booleanValue()) {
         Minecraft var10004 = mc;
         Minecraft var10005 = mc;
         double var7 = Minecraft.thePlayer.posY + 1.0D;
         Minecraft var10006 = mc;
         BlockPos obsidianpos = new BlockPos(new Vec3(Minecraft.thePlayer.posX, var7, Minecraft.thePlayer.posZ));
         Minecraft var10000 = mc;
         Block obsidianblock = Minecraft.theWorld.getBlockState(obsidianpos).getBlock();
         if(obsidianblock == Block.getBlockById(49)) {
            var10004 = mc;
            Minecraft var8 = mc;
            double var9 = Minecraft.thePlayer.posY - 1.0D;
            var10006 = mc;
            BlockPos downpos = new BlockPos(new Vec3(Minecraft.thePlayer.posX, var9, Minecraft.thePlayer.posZ));
            var10000 = mc;
            Minecraft.playerController.onPlayerDamageBlock(downpos, EnumFacing.UP);
         }
      }

   }
}
