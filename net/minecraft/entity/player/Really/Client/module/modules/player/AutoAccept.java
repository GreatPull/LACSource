package net.minecraft.entity.player.Really.Client.module.modules.player;

import java.awt.Color;
import java.util.function.BiConsumer;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.misc.EventChat;
import net.minecraft.entity.player.Really.Client.management.FriendManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;

public class AutoAccept extends Module {
   public AutoAccept() {
      super("AutoAccept", new String[]{"TPAccept, autotp"}, ModuleType.Player);
      this.setColor((new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255))).getRGB());
   }

   @EventHandler
   private void onPacket(EventChat e) {
      if(EventChat.getType() == 0) {
         String message = e.getMessage();
         if(this.gotTpaRequest(message)) {
            this.handleRequest(message, "/tpaccept", "Accepted teleport");
         }

         if(this.gotPartyRequest(message)) {
            this.handleRequest(message, "/party accept", "Accepted party invite");
         }

         if(this.gotFactionRequest(message)) {
            this.handleRequest(message, "/f join", "Accepted faction invitation");
         }
      }

   }

   private void handleRequest(String message, String messageToSend, String notificationMessage) {
      FriendManager.getFriends().forEach((friends, alias) -> {
         if(FriendManager.isFriend(FriendManager.getAlias(friends)) && message.contains(FriendManager.getAlias(friends))) {
            Minecraft var10000 = mc;
            Minecraft.thePlayer.sendChatMessage(String.valueOf(messageToSend) + " " + FriendManager.getAlias(friends));
         }

      });
   }

   private boolean gotTpaRequest(String message) {
      String var2;
      return (var2 = message.toLowerCase()).contains("has requested to teleport") || var2.contains("to teleport to you") || var2.contains("has requested that you teleport to them");
   }

   private boolean gotFactionRequest(String message) {
      String var2;
      return (var2 = message.toLowerCase()).contains("has invited you to join") && !var2.contains("party");
   }

   private boolean gotPartyRequest(String message) {
      message = message.toLowerCase();
      return message.contains("has invited you to join their party");
   }
}
