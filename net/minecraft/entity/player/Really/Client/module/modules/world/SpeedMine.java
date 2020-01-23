package net.minecraft.entity.player.Really.Client.module.modules.world;

import java.awt.Color;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class SpeedMine extends Module {
   public static Numbers speed = new Numbers("Speed", "Speed", Double.valueOf(0.7D), Double.valueOf(0.0D), Double.valueOf(1.0D), Double.valueOf(0.1D));
   public static Numbers Pot = new Numbers("Potion", "Potion", Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(4.0D), Double.valueOf(1.0D));
   private Mode mode = new Mode("Mode", "mode", SpeedMine.AutoPlayMode.values(), SpeedMine.AutoPlayMode.Packet);
   public static Option SendPacket = new Option("SendPacket", "SendPacket", Boolean.valueOf(false));

   public SpeedMine() {
      super("SpeedMine", new String[]{"novoid", "antifall"}, ModuleType.World);
      this.setColor((new Color(223, 233, 233)).getRGB());
      super.addValues(new Value[]{speed, this.mode, Pot, SendPacket});
   }

   public Block getBlock(double x, double y, double z) {
      BlockPos bp = new BlockPos(x, y, z);
      Minecraft var10000 = mc;
      return Minecraft.theWorld.getBlockState(bp).getBlock();
   }

   @EventHandler
   private void onUpdate(EventPreUpdate e) {
      this.setSuffix(this.mode.getValue());
      if(((Double)Pot.getValue()).intValue() != 0) {
         Minecraft.thePlayer.addPotionEffect(new PotionEffect(Potion.digSpeed.getId(), 100, ((Double)Pot.getValue()).intValue() - 1));
      }

      if(this.mode.getValue() == SpeedMine.AutoPlayMode.Packet) {
         Minecraft.playerController.blockHitDelay = 0;
         if((double)Minecraft.playerController.curBlockDamageMP >= ((Double)speed.getValue()).doubleValue()) {
            Minecraft.playerController.curBlockDamageMP = 1.0F;
         }

         if(((Boolean)SendPacket.getValue()).booleanValue() && (double)Minecraft.playerController.curBlockDamageMP > 1.0E-5D) {
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
         }
      }

      if(this.mode.getValue() == SpeedMine.AutoPlayMode.NewPacket2) {
         Minecraft.playerController.blockHitDelay = 0;
         if(Minecraft.playerController.curBlockDamageMP == 0.2F) {
            Minecraft.playerController.curBlockDamageMP += 0.1F;
         }

         if(Minecraft.playerController.curBlockDamageMP == 0.4F) {
            Minecraft.playerController.curBlockDamageMP += 0.1F;
         }

         if(Minecraft.playerController.curBlockDamageMP == 0.6F) {
            Minecraft.playerController.curBlockDamageMP += 0.1F;
         }

         if(Minecraft.playerController.curBlockDamageMP == 0.8F) {
            Minecraft.playerController.curBlockDamageMP += 0.1F;
         }

         if(((Boolean)SendPacket.getValue()).booleanValue() && (double)Minecraft.playerController.curBlockDamageMP > 1.0E-5D) {
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
         }
      }

      if(this.mode.getValue() == SpeedMine.AutoPlayMode.MorePacket2) {
         Minecraft.playerController.blockHitDelay = 0;
         if(Minecraft.playerController.curBlockDamageMP == 0.1F) {
            Minecraft.playerController.curBlockDamageMP += 0.1F;
         }

         if(Minecraft.playerController.curBlockDamageMP == 0.3F) {
            Minecraft.playerController.curBlockDamageMP += 0.1F;
         }

         if(Minecraft.playerController.curBlockDamageMP == 0.5F) {
            Minecraft.playerController.curBlockDamageMP += 0.1F;
         }

         if(Minecraft.playerController.curBlockDamageMP == 0.7F) {
            Minecraft.playerController.curBlockDamageMP += 0.1F;
         }

         if(Minecraft.playerController.curBlockDamageMP == 0.9F) {
            Minecraft.playerController.curBlockDamageMP += 0.1F;
         }

         if(((Boolean)SendPacket.getValue()).booleanValue() && (double)Minecraft.playerController.curBlockDamageMP > 1.0E-5D) {
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
         }
      }

      if(this.mode.getValue() == SpeedMine.AutoPlayMode.NewPacket) {
         Minecraft.playerController.blockHitDelay = 0;
         if(Minecraft.playerController.curBlockDamageMP == 0.1F) {
            Minecraft.playerController.curBlockDamageMP += 0.1F;
         }

         if(Minecraft.playerController.curBlockDamageMP == 0.4F) {
            Minecraft.playerController.curBlockDamageMP += 0.1F;
         }

         if(Minecraft.playerController.curBlockDamageMP == 0.7F) {
            Minecraft.playerController.curBlockDamageMP += 0.1F;
         }

         if(((Boolean)SendPacket.getValue()).booleanValue() && (double)Minecraft.playerController.curBlockDamageMP > 1.0E-5D) {
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
         }
      }

      if(this.mode.getValue() == SpeedMine.AutoPlayMode.MorePacket) {
         Minecraft.playerController.blockHitDelay = 0;
         if(Minecraft.playerController.curBlockDamageMP == 0.1F) {
            Minecraft.playerController.curBlockDamageMP += 0.1F;
         }

         if(Minecraft.playerController.curBlockDamageMP == 0.4F) {
            Minecraft.playerController.curBlockDamageMP += 0.1F;
         }

         if(Minecraft.playerController.curBlockDamageMP == 0.7F) {
            Minecraft.playerController.curBlockDamageMP += 0.1F;
         }

         if(((Boolean)SendPacket.getValue()).booleanValue() && (double)Minecraft.playerController.curBlockDamageMP > 1.0E-5D) {
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
         }
      }

      if(this.mode.getValue() == SpeedMine.AutoPlayMode.RemixOld) {
         Minecraft.playerController.blockHitDelay = 0;
         if((double)Minecraft.playerController.curBlockDamageMP > 1.0E-5D) {
            PlayerControllerMP playerController = Minecraft.playerController;
            playerController.curBlockDamageMP += this.getBlock((double)Minecraft.objectMouseOver.getBlockPos().getX(), (double)Minecraft.objectMouseOver.getBlockPos().getY(), (double)Minecraft.objectMouseOver.getBlockPos().getZ()).getPlayerRelativeBlockHardness(Minecraft.thePlayer, Minecraft.theWorld, Minecraft.objectMouseOver.getBlockPos()) * 1.1F;
            if(((Boolean)SendPacket.getValue()).booleanValue()) {
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, Minecraft.objectMouseOver.getBlockPos(), EnumFacing.DOWN));
            }
         }
      }

   }

   public void onDisable() {
      super.onDisable();
   }

   static enum AutoPlayMode {
      Packet,
      NewPacket,
      MorePacket,
      RemixOld,
      NewPacket2,
      MorePacket2;
   }
}
