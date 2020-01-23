package net.minecraft.entity.player.Really.Client.module.modules.player;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.math.RotationUtil;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.network.play.client.C02PacketUseEntity;

public class AntiFireBall extends Module {
   private Numbers range = new Numbers("range", "range", Double.valueOf(4.5D), Double.valueOf(1.0D), Double.valueOf(6.0D), Double.valueOf(0.1D));
   private Option rot = new Option("rot", "rot", Boolean.valueOf(true));

   public AntiFireBall() {
      super("AntiFireball", new String[]{"AntiFireball"}, ModuleType.Player);
      this.addValues(new Value[]{this.range, this.rot});
   }

   @EventHandler
   private void EventPreUpdate(EventPreUpdate event) {
      for(Entity entity : Minecraft.theWorld.loadedEntityList) {
         if(entity instanceof EntityFireball) {
            double rangeToEntity = (double)Minecraft.thePlayer.getDistanceToEntity(entity);
            if(rangeToEntity <= ((Double)this.range.getValue()).doubleValue()) {
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
               if(((Boolean)this.rot.getValue()).booleanValue()) {
                  float[] rotation = RotationUtil.getRotations(entity);
                  event.setYaw(rotation[0]);
                  event.setPitch(rotation[1]);
               }
            }
         }
      }

   }
}
