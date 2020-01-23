package net.minecraft.entity.player.Really.Client.command.commands;

import java.util.Arrays;
import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.command.Command;
import net.minecraft.entity.player.Really.Client.management.ModuleManager;
import net.minecraft.entity.player.Really.Client.module.modules.render.Xray;
import net.minecraft.entity.player.Really.Client.utils.Helper;
import net.minecraft.entity.player.Really.Client.utils.math.MathUtil;

public class Xraycmd extends Command {
   public Xraycmd() {
      super("xray", new String[]{"oreesp"}, "", "nigga");
   }

   public String execute(String[] args) {
      Client var10000 = Client.instance;
      Client.getModuleManager();
      Xray xray = (Xray)ModuleManager.getModuleByName("Xray");
      if(args.length == 2) {
         if(MathUtil.parsable(args[1], (byte)4)) {
            int id = Integer.parseInt(args[1]);
            if(args[0].equalsIgnoreCase("add")) {
               xray.KEY_IDS.add(Integer.valueOf(id));
               Helper.sendMessage("Added Block ID " + id);
            } else if(args[0].equalsIgnoreCase("remove")) {
               xray.KEY_IDS.remove(id);
               Helper.sendMessage("Removed Block ID " + id);
            } else {
               Helper.sendMessage("Invalid syntax");
            }
         } else {
            Helper.sendMessage("Invalid block ID");
         }
      } else if(args.length == 1 && args[0].equalsIgnoreCase("list")) {
         Arrays.toString(xray.KEY_IDS.toArray());
      }

      return null;
   }
}
