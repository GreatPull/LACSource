package net.minecraft.entity.player.Really.Client.api;

public class AALAPI {
   private static String username;

   public static String getUsername() {
      if(username == null) {
         username = getUsernameUncached();
      }

      return username;
   }

   private static String getUsernameUncached() {
      Class<?> api;
      try {
         api = Class.forName("net.aal.API");
      } catch (ClassNotFoundException var3) {
         api = null;
      }

      if(api == null) {
         return "debug-mode";
      } else {
         try {
            return (String)api.getMethod("getUsername", new Class[0]).invoke((Object)null, new Object[0]);
         } catch (Exception var2) {
            var2.printStackTrace();
            return "error-getting-username";
         }
      }
   }
}
