package net.minecraft.entity.player.Really.Client.command.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.Really.Client.command.Command;
import net.minecraft.entity.player.Really.Client.utils.Helper;

public class Enchant extends Command {
   public Enchant() {
      super("Enchant", new String[]{"e"}, "", "enchanth");
   }

   public String execute(String[] args) {
      if(args.length < 1) {
         Minecraft.getMinecraft();
         EntityPlayerSP var10000 = Minecraft.thePlayer;
         StringBuilder var10001 = new StringBuilder("/give ");
         Minecraft.getMinecraft();
         var10000.sendChatMessage(var10001.append(Minecraft.thePlayer.getName()).append(" diamond_sword 1 0 {ench:[{id:16,lvl:127}]}").toString());
      } else {
         Helper.sendMessage("invalid syntax Valid .enchant");
      }

      return null;
   }
}
