package net.minecraft.entity.player.Really.Client.utils.render;

public class StringConversions {
   public static Object castNumber(String newValueText, Object currentValue) {
      return newValueText.contains(".")?(newValueText.toLowerCase().contains("f")?Float.valueOf(Float.parseFloat(newValueText)):Double.valueOf(Double.parseDouble(newValueText))):(isNumeric(newValueText)?Integer.valueOf(Integer.parseInt(newValueText)):newValueText);
   }

   public static boolean isNumeric(String text) {
      try {
         Integer.parseInt(text);
         return true;
      } catch (NumberFormatException var2) {
         return false;
      }
   }
}
