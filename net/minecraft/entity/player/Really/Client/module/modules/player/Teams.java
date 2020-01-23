package net.minecraft.entity.player.Really.Client.module.modules.player;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.management.ModuleManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;

public class Teams extends Module {
   public Teams() {
      super("Teams", new String[0], ModuleType.Player);
   }

   public static boolean isOnSameTeam(Entity entity) {
      Client var10000 = Client.instance;
      Client.getModuleManager();
      if(!ModuleManager.getModuleByName("Teams").isEnabled()) {
         return false;
      } else {
         Minecraft.getMinecraft();
         if(Minecraft.thePlayer.getDisplayName().getUnformattedText().startsWith("§")) {
            Minecraft.getMinecraft();
            if(Minecraft.thePlayer.getDisplayName().getUnformattedText().length() <= 2 || entity.getDisplayName().getUnformattedText().length() <= 2) {
               return false;
            }

            Minecraft.getMinecraft();
            if(Minecraft.thePlayer.getDisplayName().getUnformattedText().substring(0, 2).equals(entity.getDisplayName().getUnformattedText().substring(0, 2))) {
               return true;
            }
         }

         return false;
      }
   }
}
