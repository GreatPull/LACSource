package net.minecraft.entity.player.Really.Client.management;

import java.util.HashMap;
import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.management.FileManager;
import net.minecraft.entity.player.Really.Client.management.Manager;

public class FriendManager implements Manager {
   private static HashMap friends;

   public void init() {
      friends = new HashMap();

      for(String v : FileManager.read("Friends.txt")) {
         if(v.contains(":")) {
            String name = v.split(":")[0];
            String alias = v.split(":")[1];
            friends.put(name, alias);
         } else {
            friends.put(v, v);
         }
      }

      Client.instance.getCommandManager().add(new FriendManager_(this, "f", new String[]{"friend", "fren", "fr"}, "add/del/list name alias", "Manage client friends"));
   }

   public static boolean isFriend(String name) {
      return friends.containsKey(name);
   }

   public static String getAlias(Object friends2) {
      return (String)friends.get(friends2);
   }

   public static HashMap getFriends() {
      return friends;
   }

   static HashMap access$0() {
      return friends;
   }
}
