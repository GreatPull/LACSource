package net.minecraft.entity.player.Really.Client.ui.font;

import java.awt.Font;
import java.io.InputStream;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.ui.font.CFontRenderer;
import net.minecraft.util.ResourceLocation;

public abstract class FontLoaders {
   public static CFontRenderer GoogleSans18 = new CFontRenderer(getGoogleSans(18), true, true);
   public static CFontRenderer GoogleSans26 = new CFontRenderer(getGoogleSans(26), true, true);
   public static CFontRenderer GoogleSans15 = new CFontRenderer(getGoogleSans(15), true, true);
   public static CFontRenderer comfortaa16 = new CFontRenderer(getcomfortaa(16), true, true);
   public static CFontRenderer comfortaa20 = new CFontRenderer(getcomfortaa(20), true, true);
   public static CFontRenderer comfortaa15 = new CFontRenderer(getcomfortaa(15), true, true);
   public static CFontRenderer comfortaa34 = new CFontRenderer(getcomfortaa(34), true, true);
   public static CFontRenderer GoogleSans20 = new CFontRenderer(getGoogleSans(20), true, true);
   public static CFontRenderer GoogleSans28 = new CFontRenderer(getGoogleSans(28), true, true);
   public static CFontRenderer GoogleSans16 = new CFontRenderer(getGoogleSans(16), true, true);
   public static CFontRenderer GoogleSans32 = new CFontRenderer(getGoogleSans(32), true, true);
   public static CFontRenderer kiona12 = new CFontRenderer(getKiona(12), true, true);
   public static CFontRenderer kiona14 = new CFontRenderer(getKiona(14), true, true);
   public static CFontRenderer kiona16 = new CFontRenderer(getKiona(16), true, true);
   public static CFontRenderer kiona18 = new CFontRenderer(getKiona(18), true, true);
   public static CFontRenderer kiona20 = new CFontRenderer(getKiona(20), true, true);
   public static CFontRenderer kiona22 = new CFontRenderer(getKiona(22), true, true);
   public static CFontRenderer kiona24 = new CFontRenderer(getKiona(24), true, true);
   public static CFontRenderer kiona26 = new CFontRenderer(getKiona(26), true, true);
   public static CFontRenderer kiona28 = new CFontRenderer(getKiona(28), true, true);
   public static CFontRenderer kiona32 = new CFontRenderer(getKiona(32), true, true);
   public static CFontRenderer kiona40 = new CFontRenderer(getKiona(40), true, true);
   public static CFontRenderer Leain12 = new CFontRenderer(Leain(12), true, true);
   public static CFontRenderer Leain14 = new CFontRenderer(Leain(14), true, true);
   public static CFontRenderer Leain16 = new CFontRenderer(Leain(16), true, true);
   public static CFontRenderer Leain18 = new CFontRenderer(Leain(18), true, true);
   public static CFontRenderer Leain20 = new CFontRenderer(Leain(20), true, true);
   public static CFontRenderer Leain22 = new CFontRenderer(Leain(22), true, true);
   public static CFontRenderer Leain24 = new CFontRenderer(Leain(24), true, true);
   public static CFontRenderer Leain26 = new CFontRenderer(Leain(26), true, true);
   public static CFontRenderer Leain28 = new CFontRenderer(Leain(28), true, true);
   public static CFontRenderer Leain32 = new CFontRenderer(Leain(32), true, true);
   public static CFontRenderer Leain40 = new CFontRenderer(Leain(40), true, true);
   public static CFontRenderer sansation18 = new CFontRenderer(getsansation(18), true, true);
   public static CFontRenderer sansation16 = new CFontRenderer(getsansation(16), true, true);
   public static CFontRenderer sansation14 = new CFontRenderer(getsansation(14), true, true);
   public static CFontRenderer sansation24 = new CFontRenderer(getsansation(24), true, true);
   public static CFontRenderer sansation28 = new CFontRenderer(getsansation(28), true, true);
   public static CFontRenderer Leain121 = new CFontRenderer(Leain2(12), true, true);
   public static CFontRenderer Leain141 = new CFontRenderer(Leain2(14), true, true);
   public static CFontRenderer Leain161 = new CFontRenderer(Leain2(16), true, true);
   public static CFontRenderer Leain181 = new CFontRenderer(Leain2(18), true, true);
   public static CFontRenderer Leain201 = new CFontRenderer(Leain2(40), true, true);

   private static Font getKiona(int size) {
      Font font;
      try {
         InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("LAC/raleway.ttf")).getInputStream();
         font = Font.createFont(0, is);
         font = font.deriveFont(0, (float)size);
      } catch (Exception var3) {
         var3.printStackTrace();
         System.out.println("Error loading font");
         font = new Font("default", 0, size);
      }

      return font;
   }

   private static Font Leain(int size) {
      Font font;
      try {
         InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("Leain/fonts/Leain.ttf")).getInputStream();
         font = Font.createFont(0, is);
         font = font.deriveFont(0, (float)size);
      } catch (Exception var3) {
         var3.printStackTrace();
         System.out.println("Error loading font");
         font = new Font("default", 0, size);
      }

      return font;
   }

   private static Font Leain2(int size) {
      Font font;
      try {
         InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("Leain/fonts/Leain2.ttf")).getInputStream();
         font = Font.createFont(0, is);
         font = font.deriveFont(0, (float)size);
      } catch (Exception var3) {
         var3.printStackTrace();
         System.out.println("Error loading font");
         font = new Font("default", 0, size);
      }

      return font;
   }

   private static Font getcomfortaa(int size) {
      Font font;
      try {
         InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("Leain/fonts/comfortaa.ttf")).getInputStream();
         font = Font.createFont(0, is);
         font = font.deriveFont(0, (float)size);
      } catch (Exception var3) {
         var3.printStackTrace();
         System.out.println("Error loading font");
         font = new Font("default", 0, size);
      }

      return font;
   }

   private static Font getGoogleSans(int size) {
      Font font;
      try {
         InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("LAC/GoogleSans.ttf")).getInputStream();
         font = Font.createFont(0, is);
         font = font.deriveFont(0, (float)size);
      } catch (Exception var3) {
         var3.printStackTrace();
         System.out.println("Error loading font");
         font = new Font("default", 0, size);
      }

      return font;
   }

   private static Font getsansation(int size) {
      Font font;
      try {
         InputStream is = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("LAC/sansation.ttf")).getInputStream();
         font = Font.createFont(0, is);
         font = font.deriveFont(0, (float)size);
      } catch (Exception var3) {
         var3.printStackTrace();
         System.out.println("Error loading font");
         font = new Font("default", 0, size);
      }

      return font;
   }

   private static Font getyahei(int i) {
      return null;
   }
}
