package net.minecraft.entity.player.Really.Client.utils;

import java.awt.Color;

public class ColorsUtils {
   public static int getColor(Color color) {
      return getColor(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
   }

   public static int getColor(int brightness) {
      return getColor(brightness, brightness, brightness, 255);
   }

   public static int getColor(int brightness, int alpha) {
      return getColor(brightness, brightness, brightness, alpha);
   }

   public static int getColor(int red, int green, int blue) {
      return getColor(red, green, blue, 255);
   }

   public static int getColor(int red, int green, int blue, int alpha) {
      int color = 0;
      color = color | alpha << 24;
      color = color | red << 16;
      color = color | green << 8;
      color = color | blue;
      return color;
   }
}
