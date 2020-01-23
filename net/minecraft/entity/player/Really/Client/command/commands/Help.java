package net.minecraft.entity.player.Really.Client.command.commands;

import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.command.Command;
import net.minecraft.entity.player.Really.Client.utils.Helper;

public class Help extends Command {
   public Help() {
      super("Help", new String[]{"list"}, "", "sketit");
   }

   public String execute(String[] args) {
      if(args.length == 0) {
         Helper.sendMessageWithoutPrefix("§7§m§l----------------------------------");
         Helper.sendMessageWithoutPrefix("                    §b§l" + Client.ClientName + " Client");
         Helper.sendMessageWithoutPrefix("§b.help >§7 list commands");
         Helper.sendMessageWithoutPrefix("§b.bind >§7 bind a module to a key");
         Helper.sendMessageWithoutPrefix("§b.t >§7 toggle a module on/off");
         Helper.sendMessageWithoutPrefix("§b.friend >§7 friend a player");
         Helper.sendMessageWithoutPrefix("§b.cheats >§7 list all modules");
         Helper.sendMessageWithoutPrefix("§b.config >§7 load a premade config");
         Helper.sendMessageWithoutPrefix("§b.ChangeTitle >§7 ChangeTitle for the client");
         Helper.sendMessageWithoutPrefix("§b.SetVer >§7 Set Client HUD Version");
         Helper.sendMessageWithoutPrefix("§b.SetClientName >§7 Set Client HUD Name");
         Helper.sendMessageWithoutPrefix("§b.IRC >§7 Send a message to IRC");
         Helper.sendMessageWithoutPrefix("§b.Check >§7 Login your IRC");
         Helper.sendMessageWithoutPrefix("§7§m§l----------------------------------");
      } else {
         Helper.sendMessage("invalid syntax Valid .help");
      }

      return null;
   }
}
