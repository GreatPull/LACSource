package net.minecraft.entity.player.Really.Client.command.commands;

import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.command.Command;
import net.minecraft.entity.player.Really.Client.management.ModuleManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.utils.Helper;
import net.minecraft.util.EnumChatFormatting;

public class Cheats extends Command {
   public Cheats() {
      super("Cheats", new String[]{"mods"}, "", "sketit");
   }

   public String execute(String[] args) {
      if(args.length == 0) {
         Client var10000 = Client.instance;
         Client.getModuleManager();
         StringBuilder list = new StringBuilder(String.valueOf(ModuleManager.getModules().size()) + " Cheats - ");
         var10000 = Client.instance;
         Client.getModuleManager();

         for(Module cheat : ModuleManager.getModules()) {
            list.append(cheat.isEnabled()?EnumChatFormatting.GREEN:EnumChatFormatting.RED).append(cheat.getName()).append(", ");
         }

         Helper.sendMessage("> " + list.toString().substring(0, list.toString().length() - 2));
      } else {
         Helper.sendMessage("> Correct usage .cheats");
      }

      return null;
   }
}
