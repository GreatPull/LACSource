package net.minecraft.entity.player.Really.Client.utils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.function.Consumer;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.IChatComponent;

public class UpdateChecker extends Thread {
   private final String url;
   private final Consumer callback;
   private final Gson gson = (new GsonBuilder()).registerTypeHierarchyAdapter(IChatComponent.class, new IChatComponent.Serializer()).registerTypeHierarchyAdapter(ChatStyle.class, new ChatStyle.Serializer()).registerTypeAdapterFactory(new EnumTypeAdapterFactory()).setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();

   public UpdateChecker(String url, Consumer callback) {
      this.url = url;
      this.callback = callback;
   }

   public void run() {
      for(int retry = 0; retry < 3; ++retry) {
         try {
            UpdateChecker.UpdateResponse response = this.check(this.url);

            try {
               this.callback.accept(response);
            } catch (Exception var4) {
               ;
            }

            return;
         } catch (Exception var6) {
            try {
               Thread.sleep(10000L);
            } catch (InterruptedException var5) {
               return;
            }
         }
      }

   }

   private UpdateChecker.UpdateResponse check(String url) throws IOException {
      HttpURLConnection con = null;

      UpdateChecker.UpdateResponse var10 = null;
      try {
         con = (HttpURLConnection)(new URL(url)).openConnection();
         con.setRequestMethod("GET");
         String agent = "Java/" + System.getProperty("java.version") + " " + "Forge/11.15.1.1722" + " " + System.getProperty("os.name") + " " + System.getProperty("os.arch") + " ";
         con.setRequestProperty("User-Agent", agent);
         int response = con.getResponseCode();
         if(response != 200) {
            throw new IOException("HTTP " + response);
         }

         Throwable var5 = null;
         Object var6 = null;

         try {
            InputStreamReader in = new InputStreamReader(con.getInputStream(), "UTF-8");

            try {
               UpdateChecker.UpdateResponse updateResponse = (UpdateChecker.UpdateResponse)this.gson.fromJson((Reader)in, (Class)UpdateChecker.UpdateResponse.class);
               var10 = updateResponse;
            } finally {
               if(in != null) {
                  in.close();
               }

            }
         } catch (Throwable var21) {
            var5 = var21;

         }
      } finally {
         if(con != null) {
            con.disconnect();
         }

      }

      return var10;
   }

   public static class UpdateResponse {
      private final IChatComponent updateMessage;

      public UpdateResponse(IChatComponent updateMessage) {
         this.updateMessage = updateMessage;
      }

      public IChatComponent getUpdateMessage() {
         return this.updateMessage;
      }
   }
}
