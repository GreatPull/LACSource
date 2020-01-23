package net.minecraft.entity.player.Really.Client.command.commands;

import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.command.Command;

public class SetVer extends Command {
   public SetVer() {
      super("SetVer", new String[]{"SV", "SetVersion", "ClientVersion"}, "", "clientname");
   }

   public String execute(String[] args) {
      if(args.length == 0) {
         Client.ClientVersion = Client.lastVer;
      } else {
         String Ganga = null;
         boolean a = false;

         for(String s : args) {
            if(!a) {
               Ganga = s;
               a = true;
            } else {
               Ganga = String.valueOf(Ganga) + " " + s;
            }
         }

         Client.ClientVersion = Ganga;
      }

      return null;
   }
}
