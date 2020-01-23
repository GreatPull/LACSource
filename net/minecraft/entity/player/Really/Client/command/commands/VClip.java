package net.minecraft.entity.player.Really.Client.command.commands;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.command.Command;
import net.minecraft.entity.player.Really.Client.utils.Helper;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;
import net.minecraft.entity.player.Really.Client.utils.math.MathUtil;
import net.minecraft.util.EnumChatFormatting;

public class VClip extends Command {
   private TimerUtil timer = new TimerUtil();

   public VClip() {
      super("Vc", new String[]{"Vclip", "clip", "verticalclip", "clip"}, "", "Teleport down a specific ammount");
   }

   public String execute(String[] args) {
      if(!Helper.onServer("enjoytheban")) {
         if(args.length > 0) {
            if(MathUtil.parsable(args[0], (byte)4)) {
               float distance = Float.parseFloat(args[0]);
               Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + (double)distance, Minecraft.thePlayer.posZ);
               Helper.sendMessage("> Vclipped " + distance + " blocks");
            } else {
               this.syntaxError(EnumChatFormatting.GRAY + args[0] + " is not a valid number");
            }
         } else {
            this.syntaxError(EnumChatFormatting.GRAY + "Valid .vclip <number>");
         }
      } else {
         Helper.sendMessage("> You cannot use vclip on the ETB Server.");
      }

      return null;
   }
}
