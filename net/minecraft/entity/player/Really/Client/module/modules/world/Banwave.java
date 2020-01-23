package net.minecraft.entity.player.Really.Client.module.modules.world;

import java.awt.Color;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.management.FriendManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;

public class Banwave extends Module {
   private TimerUtil timer = new TimerUtil();
   public ArrayList banned;
   private String banMessage = "twitter.com/CustomKKK";
   private Option tempBan = new Option("Temp Ban", "temp", Boolean.valueOf(false));
   private Numbers banDelay = new Numbers("Delay", "delay", Double.valueOf(10.0D), Double.valueOf(1.0D), Double.valueOf(20.0D), Double.valueOf(1.0D));

   public Banwave() {
      super("BanWave", new String[]{"dick", "banner"}, ModuleType.Player);
      this.setColor((new Color(255, 0, 0)).getRGB());
      this.banned = new ArrayList();
      this.addValues(new Value[]{this.tempBan, this.banDelay});
   }

   public void onEnable() {
      this.banned.clear();
      super.onEnable();
   }

   @EventHandler
   public void onUpdate(EventPreUpdate event) {
      Minecraft var10000 = mc;

      for(Object o : Minecraft.theWorld.getLoadedEntityList()) {
         if(o instanceof EntityOtherPlayerMP) {
            EntityOtherPlayerMP e = (EntityOtherPlayerMP)o;
            if(this.timer.hasReached(((Double)this.banDelay.getValue()).doubleValue() * 100.0D) && !FriendManager.isFriend(e.getName())) {
               String var5 = e.getName();
               Minecraft var10001 = mc;
               if(var5 != Minecraft.thePlayer.getName() && !this.banned.contains(e)) {
                  if(((Boolean)this.tempBan.getValue()).booleanValue()) {
                     Minecraft var6 = mc;
                     Minecraft.thePlayer.sendChatMessage("/tempban " + e.getName() + " 7d" + " " + this.banMessage);
                     System.out.println("/tempban " + e.getName() + " 7d" + " " + this.banMessage);
                  } else {
                     Minecraft var7 = mc;
                     Minecraft.thePlayer.sendChatMessage("/ban " + e.getName() + " " + this.banMessage);
                     System.out.println("/ban " + e.getName() + " " + this.banMessage);
                  }

                  this.banned.add(e);
                  this.timer.reset();
               }
            }
         }
      }

   }
}
