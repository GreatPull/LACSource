package net.minecraft.entity.player.Really.Client.ui.font;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import net.minecraft.client.renderer.texture.TextureUtil;
import org.lwjgl.opengl.GL11;

public class PureFont {
   public int IMAGE_WIDTH = 1024;
   public int IMAGE_HEIGHT = 1024;
   private int texID;
   private final PureFont.IntObject[] chars = new PureFont.IntObject[2048];
   private final Font font;
   private boolean antiAlias;
   private int fontHeight = -1;
   private int charOffset = 8;

   public PureFont(Font font, boolean antiAlias, int charOffset) {
      this.font = font;
      this.antiAlias = antiAlias;
      this.charOffset = charOffset;
      this.setupTexture(antiAlias);
   }

   public PureFont(Font font, boolean antiAlias) {
      this.font = font;
      this.antiAlias = antiAlias;
      this.charOffset = 8;
      this.setupTexture(antiAlias);
   }

   private void setupTexture(boolean antiAlias) {
      if(this.font.getSize() <= 15) {
         this.IMAGE_WIDTH = 256;
         this.IMAGE_HEIGHT = 256;
      }

      if(this.font.getSize() <= 43) {
         this.IMAGE_WIDTH = 512;
         this.IMAGE_HEIGHT = 512;
      } else if(this.font.getSize() <= 91) {
         this.IMAGE_WIDTH = 1024;
         this.IMAGE_HEIGHT = 1024;
      } else {
         this.IMAGE_WIDTH = 2048;
         this.IMAGE_HEIGHT = 2048;
      }

      BufferedImage img = new BufferedImage(this.IMAGE_WIDTH, this.IMAGE_HEIGHT, 2);
      Graphics2D g2 = (Graphics2D)img.getGraphics();
      g2.setFont(this.font);
      g2.setColor(new Color(255, 255, 255, 0));
      g2.fillRect(0, 0, this.IMAGE_WIDTH, this.IMAGE_HEIGHT);
      g2.setColor(Color.white);
      int rowHeight = 0;
      int positionX = 0;
      int positionY = 0;

      for(int i2 = 0; i2 < 2048; ++i2) {
         char ch = (char)i2;
         BufferedImage fontImage = this.getFontImage(ch, antiAlias);
         PureFont.IntObject newIntObject = new PureFont.IntObject(this, (PureFont.IntObject)null);
         newIntObject.width = fontImage.getWidth();
         newIntObject.height = fontImage.getHeight();
         if(positionX + newIntObject.width >= this.IMAGE_WIDTH) {
            positionX = 0;
            positionY += rowHeight;
            rowHeight = 0;
         }

         newIntObject.storedX = positionX;
         newIntObject.storedY = positionY;
         if(newIntObject.height > this.fontHeight) {
            this.fontHeight = newIntObject.height;
         }

         if(newIntObject.height > rowHeight) {
            rowHeight = newIntObject.height;
         }

         this.chars[i2] = newIntObject;
         g2.drawImage(fontImage, positionX, positionY, (ImageObserver)null);
         positionX += newIntObject.width;
      }

      try {
         this.texID = TextureUtil.uploadTextureImageAllocate(TextureUtil.glGenTextures(), img, true, true);
      } catch (NullPointerException var11) {
         var11.printStackTrace();
      }

   }

   private BufferedImage getFontImage(char ch, boolean antiAlias) {
      BufferedImage tempfontImage = new BufferedImage(1, 1, 2);
      Graphics2D g2 = (Graphics2D)tempfontImage.getGraphics();
      if(antiAlias) {
         g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      } else {
         g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
      }

      g2.setFont(this.font);
      FontMetrics fontMetrics = g2.getFontMetrics();
      int charwidth = fontMetrics.charWidth(ch) + 8;
      if(charwidth <= 0) {
         charwidth = 7;
      }

      int charheight;
      if((charheight = fontMetrics.getHeight() + 3) <= 0) {
         charheight = this.font.getSize();
      }

      BufferedImage fontImage = new BufferedImage(charwidth, charheight, 2);
      Graphics2D gt2 = (Graphics2D)fontImage.getGraphics();
      if(antiAlias) {
         gt2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
      } else {
         gt2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
      }

      gt2.setFont(this.font);
      gt2.setColor(Color.WHITE);
      int charx = 3;
      boolean chary = true;
      gt2.drawString(String.valueOf(ch), 3, 1 + fontMetrics.getAscent());
      return fontImage;
   }

   public void drawChar(char c2, float x2, float y2) throws ArrayIndexOutOfBoundsException {
      try {
         this.drawQuad(x2, y2, (float)this.chars[c2].width, (float)this.chars[c2].height, (float)this.chars[c2].storedX, (float)this.chars[c2].storedY, (float)this.chars[c2].width, (float)this.chars[c2].height);
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }

   private void drawQuad(float x2, float y2, float width, float height, float srcX, float srcY, float srcWidth, float srcHeight) {
      float renderSRCX = srcX / (float)this.IMAGE_WIDTH;
      float renderSRCY = srcY / (float)this.IMAGE_HEIGHT;
      float renderSRCWidth = srcWidth / (float)this.IMAGE_WIDTH;
      float renderSRCHeight = srcHeight / (float)this.IMAGE_HEIGHT;
      GL11.glBegin(4);
      GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
      GL11.glVertex2d((double)(x2 + width), (double)y2);
      GL11.glTexCoord2f(renderSRCX, renderSRCY);
      GL11.glVertex2d((double)x2, (double)y2);
      GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
      GL11.glVertex2d((double)x2, (double)(y2 + height));
      GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
      GL11.glVertex2d((double)x2, (double)(y2 + height));
      GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY + renderSRCHeight);
      GL11.glVertex2d((double)(x2 + width), (double)(y2 + height));
      GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
      GL11.glVertex2d((double)(x2 + width), (double)y2);
      GL11.glEnd();
   }

   public void drawString(String text, double x2, double y2, Color color, boolean shadow) {
      x2 = x2 * 2.0D;
      y2 = y2 * 2.0D - 2.0D;
      GL11.glPushMatrix();
      GL11.glScaled(0.25D, 0.25D, 0.25D);
      TextureUtil.bindTexture(this.texID);
      this.glColor(shadow?new Color(0.05F, 0.05F, 0.05F, (float)color.getAlpha() / 255.0F):color);
      int size = text.length();

      for(int indexInString = 0; indexInString < size; ++indexInString) {
         char character = text.charAt(indexInString);
         if(character < this.chars.length && character >= 0) {
            this.drawChar(character, (float)x2, (float)y2);
            x2 += (double)(this.chars[character].width - this.charOffset);
         }
      }

      GL11.glPopMatrix();
   }

   public void glColor(Color color) {
      float red = (float)color.getRed() / 255.0F;
      float green = (float)color.getGreen() / 255.0F;
      float blue = (float)color.getBlue() / 255.0F;
      float alpha = (float)color.getAlpha() / 255.0F;
      GL11.glColor4f(red, green, blue, alpha);
   }

   public int getStringHeight(String text) {
      int lines = 1;

      for(char c2 : text.toCharArray()) {
         if(c2 == 10) {
            ++lines;
         }
      }

      return (this.fontHeight - this.charOffset) / 2 * lines;
   }

   public int getHeight() {
      return (this.fontHeight - this.charOffset) / 2;
   }

   public int getStringWidth(String text) {
      int width = 0;

      for(char c2 : text.toCharArray()) {
         if(c2 < this.chars.length && c2 >= 0) {
            width += this.chars[c2].width - this.charOffset;
         }
      }

      return width / 2;
   }

   public boolean isAntiAlias() {
      return this.antiAlias;
   }

   public void setAntiAlias(boolean antiAlias) {
      if(this.antiAlias != antiAlias) {
         this.antiAlias = antiAlias;
         this.setupTexture(antiAlias);
      }

   }

   public Font getFont() {
      return this.font;
   }

   public static void main(String[] args) {
   }

   class IntObject {
      public int width;
      public int height;
      public int storedX;
      public int storedY;
      final PureFont this$0;

      private IntObject(PureFont var1x) {
         this.this$0 = var1x;
      }

      IntObject(PureFont var1x, PureFont.IntObject var2) {
         this(var1x);
      }
   }
}
