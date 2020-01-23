package net.minecraft.entity.player.Really.Client.ui.font;

import java.awt.Font;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.ui.font.CFontRenderer;
import net.minecraft.entity.player.Really.Client.ui.font.FontManager;

public class CFontFonts {
   public static final CFontFonts instance = new CFontFonts();
   public CFontRenderer tahomaSM = new CFontRenderer(new Font("Helvetica", 0, 12), true, true);

   public static void renderStringComfortaa(String text, float x2, float y2, int color) {
      CFontRenderer font = FontManager.getInstance().getFont("tabui");
      if(font == null) {
         Minecraft.getMinecraft();
         Minecraft.fontRendererObj.drawStringWithShadow(text, x2, y2, color);
      } else {
         font.drawString(text, x2, y2, color);
      }

   }

   public static float getStringWidthComfortaa(String text) {
      float width = 0.0F;
      CFontRenderer font = FontManager.getInstance().getFont("tabui");
      float var10000;
      if(font == null) {
         Minecraft.getMinecraft();
         var10000 = (float)Minecraft.fontRendererObj.getStringWidth(text);
      } else {
         var10000 = (float)font.getStringWidth(text);
      }

      width = var10000;
      return width;
   }

   public static void renderStringCentredComfortaa(String text, float x2, float y2, int color) {
      CFontRenderer font = FontManager.getInstance().getFont("tabui");
      if(font == null) {
         Minecraft.getMinecraft();
         Minecraft.fontRendererObj.drawStringWithShadow(text, x2, y2, color);
      } else {
         font.drawString(text, x2, y2, color);
      }

   }

   public static void renderStringBariol(String text, float x2, float y2, int color) {
      CFontRenderer font = FontManager.getInstance().getFont("bariol");
      if(font == null) {
         Minecraft.getMinecraft();
         Minecraft.fontRendererObj.drawStringWithShadow(text, x2, y2, color);
      } else {
         font.drawString(text, x2, y2, color);
      }

   }

   public static float getStringWidthBariol(String text) {
      float width = 0.0F;
      CFontRenderer font = FontManager.getInstance().getFont("bariol");
      float var10000;
      if(font == null) {
         Minecraft.getMinecraft();
         var10000 = (float)Minecraft.fontRendererObj.getStringWidth(text);
      } else {
         var10000 = (float)font.getStringWidth(text);
      }

      width = var10000;
      return width;
   }

   public static void renderStringCentredBariol(String text, float x2, float y2, int color) {
      CFontRenderer font = FontManager.getInstance().getFont("bariol");
      if(font == null) {
         Minecraft.getMinecraft();
         Minecraft.fontRendererObj.drawStringWithShadow(text, x2, y2, color);
      } else {
         font.drawString(text, x2, y2, color);
      }

   }

   public static void renderString10(String text, float x2, float y2, int color) {
      CFontRenderer font = FontManager.getInstance().getFont("10");
      if(font == null) {
         Minecraft.getMinecraft();
         Minecraft.fontRendererObj.drawStringWithShadow(text, x2, y2, color);
      } else {
         font.drawString(text, x2, y2, color);
      }

   }

   public static float getStringWidth10(String text) {
      float width = 0.0F;
      CFontRenderer font = FontManager.getInstance().getFont("10");
      float var10000;
      if(font == null) {
         Minecraft.getMinecraft();
         var10000 = (float)Minecraft.fontRendererObj.getStringWidth(text);
      } else {
         var10000 = (float)font.getStringWidth(text);
      }

      width = var10000;
      return width;
   }

   public static void renderStringCentred10(String text, float x2, float y2, int color) {
      CFontRenderer font = FontManager.getInstance().getFont("10");
      if(font == null) {
         Minecraft.getMinecraft();
         Minecraft.fontRendererObj.drawStringWithShadow(text, x2, y2, color);
      } else {
         font.drawString(text, x2, y2, color);
      }

   }

   public static void renderString50(String text, float x2, float y2, int color) {
      CFontRenderer font = FontManager.getInstance().getFont("40");
      if(font == null) {
         Minecraft.getMinecraft();
         Minecraft.fontRendererObj.drawStringWithShadow(text, x2, y2, color);
      } else {
         font.drawString(text, x2, y2, color);
      }

   }

   public static float getStringWidth50(String text) {
      float width = 0.0F;
      CFontRenderer font = FontManager.getInstance().getFont("40");
      float var10000;
      if(font == null) {
         Minecraft.getMinecraft();
         var10000 = (float)Minecraft.fontRendererObj.getStringWidth(text);
      } else {
         var10000 = (float)font.getStringWidth(text);
      }

      width = var10000;
      return width;
   }

   public static void renderStringCentred50(String text, float x2, float y2, int color) {
      CFontRenderer font = FontManager.getInstance().getFont("40");
      if(font == null) {
         Minecraft.getMinecraft();
         Minecraft.fontRendererObj.drawStringWithShadow(text, x2, y2, color);
      } else {
         font.drawString(text, x2, y2, color);
      }

   }

   public static void renderStringtitle(String text, float x2, float y2, int color) {
      CFontRenderer font = FontManager.getInstance().getFont("title");
      if(font == null) {
         Minecraft.getMinecraft();
         Minecraft.fontRendererObj.drawStringWithShadow(text, x2, y2, color);
      } else {
         font.drawString(text, x2, y2, color);
      }

   }

   public static float getStringWidthtitle(String text) {
      float width = 0.0F;
      CFontRenderer font = FontManager.getInstance().getFont("title");
      float var10000;
      if(font == null) {
         Minecraft.getMinecraft();
         var10000 = (float)Minecraft.fontRendererObj.getStringWidth(text);
      } else {
         var10000 = (float)font.getStringWidth(text);
      }

      width = var10000;
      return width;
   }

   public static void renderStringCentredtitle(String text, float x2, float y2, int color) {
      CFontRenderer font = FontManager.getInstance().getFont("title");
      if(font == null) {
         Minecraft.getMinecraft();
         Minecraft.fontRendererObj.drawStringWithShadow(text, x2, y2, color);
      } else {
         font.drawString(text, x2, y2, color);
      }

   }

   public static void renderStringBARON(String text, float x2, float y2, int color) {
      CFontRenderer font = FontManager.getInstance().getFont("BAROND");
      if(font == null) {
         Minecraft.getMinecraft();
         Minecraft.fontRendererObj.drawStringWithShadow(text, x2, y2, color);
      } else {
         font.drawString(text, x2, y2, color);
      }

   }

   public static float getStringWidthBARON(String text) {
      float width = 0.0F;
      CFontRenderer font = FontManager.getInstance().getFont("BAROND");
      float var10000;
      if(font == null) {
         Minecraft.getMinecraft();
         var10000 = (float)Minecraft.fontRendererObj.getStringWidth(text);
      } else {
         var10000 = (float)font.getStringWidth(text);
      }

      width = var10000;
      return width;
   }

   public static void renderStringCentredBARON(String text, float x2, float y2, int color) {
      CFontRenderer font = FontManager.getInstance().getFont("BAROND");
      if(font == null) {
         Minecraft.getMinecraft();
         Minecraft.fontRendererObj.drawStringWithShadow(text, x2, y2, color);
      } else {
         font.drawString(text, x2, y2, color);
      }

   }
}
