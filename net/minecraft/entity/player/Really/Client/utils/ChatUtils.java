package net.minecraft.entity.player.Really.Client.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

public class ChatUtils {
   private final ChatComponentText message;

   private ChatUtils(ChatComponentText message) {
      this.message = message;
   }

   public static String addFormat(String message, String regex) {
      return message.replaceAll("(?i)" + regex + "([0-9a-fklmnor])", "§$1");
   }

   public void displayClientSided() {
      Minecraft.getMinecraft();
      Minecraft.thePlayer.addChatMessage(this.message);
   }

   private ChatComponentText getChatComponent() {
      return this.message;
   }

   ChatUtils(ChatComponentText chatComponentText, ChatUtils chatUtils) {
      this(chatComponentText);
   }

   public static class ChatMessageBuilder {
      private static final EnumChatFormatting defaultMessageColor = EnumChatFormatting.WHITE;
      private ChatComponentText theMessage = new ChatComponentText("");
      private boolean useDefaultMessageColor = false;
      private ChatStyle workingStyle = new ChatStyle();
      private ChatComponentText workerMessage = new ChatComponentText("");

      public ChatMessageBuilder(boolean prependDefaultPrefix, boolean useDefaultMessageColor) {
         if(prependDefaultPrefix) {
            Client.instance.getClass();
            this.theMessage.appendSibling((new ChatUtils.ChatMessageBuilder(false, false)).appendText(String.valueOf(EnumChatFormatting.AQUA + Client.ClientName + " ")).setColor(EnumChatFormatting.RED).build().getChatComponent());
         }

         this.useDefaultMessageColor = useDefaultMessageColor;
      }

      public ChatMessageBuilder() {
      }

      public ChatUtils.ChatMessageBuilder appendText(String text) {
         this.appendSibling();
         this.workerMessage = new ChatComponentText(text);
         this.workingStyle = new ChatStyle();
         if(this.useDefaultMessageColor) {
            this.setColor(defaultMessageColor);
         }

         return this;
      }

      public ChatUtils.ChatMessageBuilder setColor(EnumChatFormatting color) {
         this.workingStyle.setColor(color);
         return this;
      }

      public ChatUtils.ChatMessageBuilder bold() {
         this.workingStyle.setBold(Boolean.valueOf(true));
         return this;
      }

      public ChatUtils.ChatMessageBuilder italic() {
         this.workingStyle.setItalic(Boolean.valueOf(true));
         return this;
      }

      public ChatUtils.ChatMessageBuilder strikethrough() {
         this.workingStyle.setStrikethrough(Boolean.valueOf(true));
         return this;
      }

      public ChatUtils.ChatMessageBuilder underline() {
         this.workingStyle.setUnderlined(Boolean.valueOf(true));
         return this;
      }

      public ChatUtils build() {
         this.appendSibling();
         return new ChatUtils(this.theMessage, (ChatUtils)null);
      }

      private void appendSibling() {
         this.theMessage.appendSibling(this.workerMessage.setChatStyle(this.workingStyle));
      }
   }
}
