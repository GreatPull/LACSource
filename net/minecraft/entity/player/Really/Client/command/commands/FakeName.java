package net.minecraft.entity.player.Really.Client.command.commands;

import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.command.Command;
import net.minecraft.util.EnumChatFormatting;

public class FakeName extends Command {
   public FakeName() {
      super("FakeName", new String[]{"SN", "FN", "NP"}, "", "fakename");
   }

   public String execute(String[] args) {
      if(args.length == 0) {
         Client.FakeName = EnumChatFormatting.AQUA + "[LAC Client]" + EnumChatFormatting.YELLOW + "source leaked by margele";
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

         Client.FakeName = Ganga;
      }

      return null;
   }
}
