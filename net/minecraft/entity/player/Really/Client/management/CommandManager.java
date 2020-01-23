package net.minecraft.entity.player.Really.Client.management;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.api.EventBus;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.misc.EventChat;
import net.minecraft.entity.player.Really.Client.command.Command;
import net.minecraft.entity.player.Really.Client.command.commands.Bind;
import net.minecraft.entity.player.Really.Client.command.commands.ChangeTitle;
import net.minecraft.entity.player.Really.Client.command.commands.Cheats;
import net.minecraft.entity.player.Really.Client.command.commands.Enchant;
import net.minecraft.entity.player.Really.Client.command.commands.FakeName;
import net.minecraft.entity.player.Really.Client.command.commands.Help;
import net.minecraft.entity.player.Really.Client.command.commands.SetClientName;
import net.minecraft.entity.player.Really.Client.command.commands.SetVer;
import net.minecraft.entity.player.Really.Client.command.commands.Toggle;
import net.minecraft.entity.player.Really.Client.command.commands.VClip;
import net.minecraft.entity.player.Really.Client.command.commands.Xraycmd;
import net.minecraft.entity.player.Really.Client.management.Manager;
import net.minecraft.entity.player.Really.Client.management.ModuleManager;
import net.minecraft.entity.player.Really.Client.module.modules.player.NoCommand;
import net.minecraft.entity.player.Really.Client.utils.Helper;

public class CommandManager implements Manager {
   private List<Command> commands;

   public void init() {
      this.commands = new ArrayList();
      this.commands.add(new Command("test", new String[]{"test"}, "", "testing") {
         public String execute(String[] args) {
            for(Command var2 : CommandManager.this.commands) {
               ;
            }

            return null;
         }
      });
      this.commands.add(new Help());
      this.commands.add(new Toggle());
      this.commands.add(new Bind());
      this.commands.add(new VClip());
      this.commands.add(new Cheats());
      this.commands.add(new Xraycmd());
      this.commands.add(new Enchant());
      this.commands.add(new ChangeTitle());
      this.commands.add(new SetClientName());
      this.commands.add(new SetVer());
      this.commands.add(new FakeName());
      EventBus.getInstance().register(new Object[]{this});
   }

   public List getCommands() {
      return this.commands;
   }

   public Optional getCommandByName(String name) {
      return this.commands.stream().filter((c2) -> {
         boolean isAlias = false;

         for(String str : c2.getAlias()) {
            if(str.equalsIgnoreCase(name)) {
               isAlias = true;
               break;
            }
         }

         return c2.getName().equalsIgnoreCase(name) || isAlias;
      }).findFirst();
   }

   public void add(Command command) {
      this.commands.add(command);
   }

   @EventHandler
   private void onChat(EventChat e) {
      Client var10000 = Client.instance;
      Client.getModuleManager();
      NoCommand CCommand = (NoCommand)ModuleManager.getModuleByName("NoCommand");
      if(!CCommand.isEnabled() && e.getMessage().length() > 1 && e.getMessage().startsWith(".")) {
         e.setCancelled(true);
         String[] args = e.getMessage().trim().substring(1).split(" ");
         Optional<Command> possibleCmd = this.getCommandByName(args[0]);
         if(possibleCmd.isPresent()) {
            String result = ((Command)possibleCmd.get()).execute((String[])Arrays.copyOfRange(args, 1, args.length));
            if(result != null && !result.isEmpty()) {
               Helper.sendMessage(result);
            }
         } else {
            Helper.sendMessage(String.format("Command not found Try \'%shelp\'", new Object[]{"."}));
         }
      }

   }
}
