package net.minecraft.entity.player.Really.Client.command.commands;

import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.command.Command;
import org.lwjgl.opengl.Display;

public class ChangeTitle extends Command {
   public ChangeTitle() {
      super("ChangeTitle", new String[]{"CT", "Title", "SetName", "ClientName"}, "", "ChangeTitle");
   }

   public String execute(String[] args) {
      if(args.length == 0) {
         Display.setTitle(Client.lastName + " " + Client.lastVer);
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

         Display.setTitle(Ganga);
      }

      return null;
   }
}
