package net.minecraft.entity.player.Really.Client.command.commands;

import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.command.Command;

public class SetClientName extends Command {
   public SetClientName() {
      super("SetClientName", new String[]{"CN", "SetName", "ClientName"}, "", "clientname");
   }

   public String execute(String[] args) {
      if(args.length == 0) {
         Client.ClientName = Client.lastName;
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

         Client.ClientName = Ganga;
      }

      return null;
   }
}
