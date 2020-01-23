package net.minecraft.entity.player.Really.Client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.utils.ChatUtils;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;

public class Helper {
   public static Minecraft mc = Minecraft.getMinecraft();

   public static void sendMessageOLD(String msg) {
      Object[] arrobject = new Object[2];
      Client.instance.getClass();
      arrobject[0] = EnumChatFormatting.BLUE + Client.ClientName + EnumChatFormatting.GRAY + ": ";
      arrobject[1] = msg;
      Minecraft.thePlayer.addChatMessage(new ChatComponentText(String.format("%s%s", arrobject)));
   }

   public static void sendMessage(String message) {
      (new ChatUtils.ChatMessageBuilder(true, true)).appendText(message).setColor(EnumChatFormatting.GRAY).build().displayClientSided();
   }

   public static void sendMessageWithoutPrefix(String message) {
      (new ChatUtils.ChatMessageBuilder(false, true)).appendText(message).setColor(EnumChatFormatting.GRAY).build().displayClientSided();
   }

   public static boolean onServer(String server) {
      return !Minecraft.isSingleplayer() && Minecraft.getCurrentServerData().serverIP.toLowerCase().contains(server);
   }
}
