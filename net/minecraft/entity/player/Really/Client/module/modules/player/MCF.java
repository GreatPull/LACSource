package net.minecraft.entity.player.Really.Client.module.modules.player;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.management.FriendManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import org.lwjgl.input.Mouse;

public class MCF extends Module {
   private boolean down;

   public MCF() {
      super("MCF", new String[]{"middleclickfriends", "middleclick"}, ModuleType.Player);
      this.setColor((new Color(241, 175, 67)).getRGB());
   }

   @EventHandler
   private void onClick(EventPreUpdate e) {
      if(Mouse.isButtonDown(2) && !this.down) {
         Minecraft var10000 = mc;
         if(Minecraft.objectMouseOver.entityHit != null) {
            var10000 = mc;
            EntityPlayer player = (EntityPlayer)Minecraft.objectMouseOver.entityHit;
            String playername = player.getName();
            if(!FriendManager.isFriend(playername)) {
               var10000 = mc;
               Minecraft.thePlayer.sendChatMessage(".f add " + playername);
            } else {
               var10000 = mc;
               Minecraft.thePlayer.sendChatMessage(".f del " + playername);
            }
         }

         this.down = true;
      }

      if(!Mouse.isButtonDown(2)) {
         this.down = false;
      }

   }
}
