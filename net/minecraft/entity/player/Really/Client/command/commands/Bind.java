package net.minecraft.entity.player.Really.Client.command.commands;

import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.command.Command;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.utils.Helper;
import org.lwjgl.input.Keyboard;

public class Bind extends Command {
   public Bind() {
      super("Bind", new String[]{"b"}, "", "sketit");
   }

   public String execute(String[] args) {
      if(args.length >= 2) {
         Client var10000 = Client.instance;
         Module m = Client.getModuleManager().getAlias(args[0]);
         if(m != null) {
            int k = Keyboard.getKeyIndex(args[1].toUpperCase());
            m.setKey(k);
            Object[] arrobject = new Object[]{m.getName(), k == 0?"none":args[1].toUpperCase()};
            Helper.sendMessage(String.format("> Bound %s to %s", arrobject));
         } else {
            Helper.sendMessage("> Invalid module name, double check spelling.");
         }
      } else {
         Helper.sendMessageWithoutPrefix("§bCorrect usage:§7 .bind <module> <key>");
      }

      return null;
   }
}
