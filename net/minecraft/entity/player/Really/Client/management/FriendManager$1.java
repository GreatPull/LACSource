package net.minecraft.entity.player.Really.Client.management;

import net.minecraft.entity.player.Really.Client.command.Command;
import net.minecraft.entity.player.Really.Client.management.FileManager;
import net.minecraft.entity.player.Really.Client.management.FriendManager;
import net.minecraft.entity.player.Really.Client.utils.Helper;
import net.minecraft.util.EnumChatFormatting;

class FriendManager_ extends Command {
   private final FriendManager fm;
   final FriendManager this$0;

   FriendManager_(FriendManager var1, String $anonymous0, String[] $anonymous1, String $anonymous2, String $anonymous3) {
      super($anonymous0, $anonymous1, $anonymous2, $anonymous3);
      this.this$0 = var1;
      this.fm = var1;
   }

   public String execute(String[] args) {
      if(args.length >= 3) {
         if(args[0].equalsIgnoreCase("add")) {
            String friends = "";
            friends = friends + String.format("%s:%s%s", new Object[]{args[1], args[2], System.lineSeparator()});
            FriendManager.access$0().put(args[1], args[2]);
            Helper.sendMessage("> " + String.format("%s has been added as %s", new Object[]{args[1], args[2]}));
            FileManager.save("Friends.txt", friends, true);
         } else if(args[0].equalsIgnoreCase("del")) {
            FriendManager.access$0().remove(args[1]);
            Helper.sendMessage("> " + String.format("%s has been removed from your friends list", new Object[]{args[1]}));
         } else if(args[0].equalsIgnoreCase("list")) {
            if(FriendManager.access$0().size() > 0) {
               int var5 = 1;

            } else {
               Helper.sendMessage("> get some friends fag lmao");
            }
         }
      } else if(args.length == 2) {
         if(args[0].equalsIgnoreCase("add")) {
            String friends = "";
            friends = friends + String.format("%s%s", new Object[]{args[1], System.lineSeparator()});
            FriendManager.access$0().put(args[1], args[1]);
            Helper.sendMessage("> " + String.format("%s has been added as %s", new Object[]{args[1], args[1]}));
            FileManager.save("Friends.txt", friends, true);
         } else if(args[0].equalsIgnoreCase("del")) {
            FriendManager.access$0().remove(args[1]);
            Helper.sendMessage("> " + String.format("%s has been removed from your friends list", new Object[]{args[1]}));
         } else if(args[0].equalsIgnoreCase("list")) {
            if(FriendManager.access$0().size() > 0) {
               int var5 = 1;

            } else {
               Helper.sendMessage("> you dont have any you lonely fuck");
            }
         }
      } else if(args.length == 1) {
         if(args[0].equalsIgnoreCase("list")) {
            if(FriendManager.access$0().size() > 0) {
               int var5 = 1;

            } else {
               Helper.sendMessage("you dont have any you lonely fuck");
            }
         } else if(!args[0].equalsIgnoreCase("add") && !args[0].equalsIgnoreCase("del")) {
            Helper.sendMessage("> Correct usage: " + EnumChatFormatting.GRAY + "Valid .f add/del <player>");
         } else {
            Helper.sendMessage("> " + EnumChatFormatting.GRAY + "Please enter a players name");
         }
      } else if(args.length == 0) {
         Helper.sendMessage("> Correct usage: " + EnumChatFormatting.GRAY + "Valid .f add/del <player>");
      }

      return null;
   }
}
