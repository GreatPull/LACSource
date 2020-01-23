package net.minecraft.entity.player.Really.Client.utils.math;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.potion.Potion;
import net.minecraft.util.MathHelper;

public class MathUtil {
   public static Random random = new Random();

   public static double toDecimalLength(double in, int places) {
      return Double.parseDouble(String.format("%." + places + "f", new Object[]{Double.valueOf(in)}));
   }

   public static double round(double in, int places) {
      places = (int)MathHelper.clamp_double((double)places, 0.0D, 2.147483647E9D);
      return Double.parseDouble(String.format("%." + places + "f", new Object[]{Double.valueOf(in)}));
   }

   public static float[] constrainAngle(float[] vector) {
      vector[0] %= 360.0F;

      for(vector[1] %= 360.0F; vector[0] <= -180.0F; vector[0] += 360.0F) {
         ;
      }

      while(vector[1] <= -180.0F) {
         vector[1] += 360.0F;
      }

      while(vector[0] > 180.0F) {
         vector[0] -= 360.0F;
      }

      while(vector[1] > 180.0F) {
         vector[1] -= 360.0F;
      }

      return vector;
   }

   public static boolean parsable(String s, byte type) {
      try {
         switch(type) {
         case 0:
            Short.parseShort(s);
            break;
         case 1:
            Byte.parseByte(s);
            break;
         case 2:
            Integer.parseInt(s);
            break;
         case 3:
            Float.parseFloat(s);
            break;
         case 4:
            Double.parseDouble(s);
            break;
         case 5:
            Long.parseLong(s);
         }

         return true;
      } catch (NumberFormatException var3) {
         var3.printStackTrace();
         return false;
      }
   }

   public static double square(double in) {
      return in * in;
   }

   public static double randomDouble(double min, double max) {
      return ThreadLocalRandom.current().nextDouble(min, max);
   }

   public static double getBaseMovementSpeed() {
      double baseSpeed = 0.2873D;
      if(Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
         int amplifier = Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
         baseSpeed *= 1.0D + 0.2D * (double)(amplifier + 1);
      }

      return baseSpeed;
   }

   public static double getHighestOffset(double max) {
      for(double i = 0.0D; i < max; i += 0.01D) {
         int[] arrn = new int[]{-2, -1, 0, 1, 2};

         for(int offset : arrn) {
            if(Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, Minecraft.thePlayer.getEntityBoundingBox().offset(Minecraft.thePlayer.motionX * (double)offset, i, Minecraft.thePlayer.motionZ * (double)offset)).size() > 0) {
               return i - 0.01D;
            }
         }
      }

      return max;
   }

   public static int randomNumber(int max, int min) {
      return -min + (int)(Math.random() * (double)(max - -min + 1));
   }

   public static class NumberType {
      public static final byte SHORT = 0;
      public static final byte BYTE = 1;
      public static final byte INT = 2;
      public static final byte FLOAT = 3;
      public static final byte DOUBLE = 4;
      public static final byte LONG = 5;

      public static byte getByType(Class cls) {
         return (byte)(cls == Short.class?0:(cls == Byte.class?1:(cls == Integer.class?2:(cls == Float.class?3:(cls == Double.class?4:(cls == Long.class?5:-1))))));
      }
   }
}
