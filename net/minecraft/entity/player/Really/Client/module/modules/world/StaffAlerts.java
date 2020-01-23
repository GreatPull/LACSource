package net.minecraft.entity.player.Really.Client.module.modules.world;

import java.awt.Color;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.Helper;

public class StaffAlerts extends Module {
   private static List<String> staff = new ArrayList();
   private static Scanner scanner;
   public boolean isStaff = false;

   public StaffAlerts() {
      super("StaffAlerts", new String[]{"staff", "stafffinder"}, ModuleType.World);
      this.setColor((new Color(198, 253, 191)).getRGB());
   }

   public void onEnable() {
      this.checkStaff();
      this.isStaff = false;
      super.onEnable();
   }

   @EventHandler
   public void onUpdate(EventPreUpdate event) {
      Minecraft var10000 = mc;
      if(Minecraft.theWorld.playerEntities != null) {
         var10000 = mc;

         for(Object object : Minecraft.theWorld.playerEntities) {
            EntityPlayer entityPlayer = (EntityPlayer)object;

            for(String staffxd : staff) {
               if(entityPlayer != null && entityPlayer.getName().equalsIgnoreCase(staffxd) && !this.isStaff) {
                  Helper.sendMessage(String.valueOf(entityPlayer.getName()) + " is staff!");
                  this.isStaff = true;
                  staff.clear();
               }
            }
         }
      }

   }

   public void checkStaff() {
      try {
         URLConnection openConnection = (new URL("http://box.enjoytheban.com/staffnames.txt")).openConnection();
         openConnection.addRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:25.0) Gecko/20100101 Firefox/25.0");
         scanner = new Scanner(new InputStreamReader(openConnection.getInputStream()));

         while(scanner.hasNextLine()) {
            String meme = scanner.nextLine();
            if(!meme.contains(":") && !meme.contains("(") && meme.length() > 1) {
               staff.add(meme);
            }
         }

         scanner.close();
      } catch (Exception var3) {
         var3.printStackTrace();
      }

   }
}
