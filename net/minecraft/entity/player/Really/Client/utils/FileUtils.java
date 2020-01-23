package net.minecraft.entity.player.Really.Client.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.Client;

public final class FileUtils {
   public static List read(File inputFile) {
      List<String> readContent = new ArrayList();

      try {
         BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(inputFile), "UTF8"));

         String str;
         while((str = in.readLine()) != null) {
            readContent.add(str);
         }

         in.close();
      } catch (Exception var4) {
         var4.printStackTrace();
      }

      return readContent;
   }

   public static void write(File outputFile, List<String> writeContent, boolean overrideContent) {
      try {
         Writer out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outputFile), "UTF-8"));

         for(String outputLine : writeContent) {
            out.write(String.valueOf(outputLine) + System.getProperty("line.separator"));
         }

         out.close();
      } catch (Exception var6) {
         var6.printStackTrace();
      }

   }

   private static File getConfigDir() {
      File var10002 = Minecraft.getMinecraft().mcDataDir;
      Client.instance.getClass();
      File file = new File(var10002, "Lonely Anti Cracker");
      if(!file.exists()) {
         file.mkdir();
      }

      return file;
   }

   private static File getConfigFolder() {
      File var10002 = Minecraft.getMinecraft().mcDataDir;
      Client.instance.getClass();
      File Sigma = new File(var10002, "Lonely Anti Cracker");
      File file = new File(Sigma, "Configs");
      if(!file.exists()) {
         file.mkdir();
      }

      return file;
   }

   public static File getConfig(String name) {
      File file = new File(getConfigFolder(), String.format("%s.txt", new Object[]{name}));
      return !file.exists()?null:file;
   }

   public static File getConfigFile(String name) {
      File file = new File(getConfigDir(), String.format("%s.txt", new Object[]{name}));
      if(!file.exists()) {
         try {
            file.createNewFile();
         } catch (Exception var3) {
            var3.printStackTrace();
         }
      }

      return file;
   }
}
