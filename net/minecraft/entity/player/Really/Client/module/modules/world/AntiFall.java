package net.minecraft.entity.player.Really.Client.module.modules.world;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventMove;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.management.ModuleManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;

public class AntiFall extends Module {
   private boolean saveMe;
   private TimerUtil timer = new TimerUtil();
   private Mode mode = new Mode("Mode", "Mode", AntiFall.AntiMode.values(), AntiFall.AntiMode.Motion);
   private Option ov = new Option("OnlyVoid", "OnlyVoid", Boolean.valueOf(true));
   private static Numbers distance = new Numbers("Distance", "Distance", Double.valueOf(5.0D), Double.valueOf(1.0D), Double.valueOf(25.0D), Double.valueOf(1.0D));

   public AntiFall() {
      super("AntiFall", new String[]{"novoid", "antifall"}, ModuleType.World);
      this.setColor((new Color(223, 233, 233)).getRGB());
      this.addValues(new Value[]{this.ov, distance, this.mode});
   }

   private boolean isBlockUnder() {
      if(Minecraft.thePlayer.posY < 0.0D) {
         return false;
      } else {
         for(int off = 0; off < (int)Minecraft.thePlayer.posY + 2; off += 2) {
            AxisAlignedBB bb = Minecraft.thePlayer.boundingBox.offset(0.0D, (double)(-off), 0.0D);
            if(!Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, bb).isEmpty()) {
               return true;
            }
         }

         return false;
      }
   }

   @EventHandler
   private void onMove(EventMove e) {
      if((double)Minecraft.thePlayer.fallDistance > ((Double)distance.getValue()).doubleValue()) {
         Client var10000 = Client.instance;
         Client.getModuleManager();
         if(!ModuleManager.getModuleByName("Flight").isEnabled() && (!((Boolean)this.ov.getValue()).booleanValue() || !this.isBlockUnder())) {
            if(!this.saveMe) {
               this.saveMe = true;
               this.timer.reset();
            }

            Minecraft.thePlayer.fallDistance = 0.0F;
            if(this.mode.getValue() == AntiFall.AntiMode.Hypixel) {
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 10.0D, Minecraft.thePlayer.posZ, false));
            } else if(this.mode.getValue() == AntiFall.AntiMode.Motion) {
               Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + ((Double)distance.getValue()).doubleValue() + 1.0D, Minecraft.thePlayer.posZ);
            }
         }
      }

   }

   @EventHandler
   private void onUpdate(EventPreUpdate e) {
      this.setSuffix(this.mode.getValue());
      if(this.saveMe && this.timer.delay(150.0F) || Minecraft.thePlayer.isCollidedVertically) {
         this.saveMe = false;
         this.timer.reset();
      }

   }

   public void onEnable() {
   }

   static enum AntiMode {
      Motion,
      Hypixel;
   }
}
