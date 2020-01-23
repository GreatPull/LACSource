package net.minecraft.entity.player.Really.Client.ui.font;

import java.awt.Color;
import java.awt.Font;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.entity.player.Really.Client.ui.font.PureFont;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class PureFontRenderer extends FontRenderer {
   public final Random fontRandom;
   private final Color[] customColorCodes;
   private final int[] colorCode;
   private PureFont font;
   private PureFont boldFont;
   private PureFont italicFont;
   private PureFont boldItalicFont;
   private String colorcodeIdentifiers;
   private boolean bidi;

   public PureFontRenderer(Font font, boolean antiAlias, int charOffset) {
      super(Minecraft.gameSettings, new ResourceLocation("textures/font/ascii.png"), Minecraft.getMinecraft().getTextureManager(), false);
      this.fontRandom = new Random();
      this.customColorCodes = new Color[256];
      this.colorCode = new int[32];
      this.colorcodeIdentifiers = "0123456789abcdefklmnor";
      this.setFont(font, antiAlias, charOffset);
      this.customColorCodes[113] = new Color(0, 90, 163);
      this.colorcodeIdentifiers = this.setupColorcodeIdentifier();
      this.setupMinecraftColorcodes();
      this.FONT_HEIGHT = this.getHeight();
   }

   public int drawString(String s2, float x2, float y2, int color) {
      return this.drawString(s2, x2, y2, color, false);
   }

   public int drawStringWithShadow(String s2, float x2, float y2, int color) {
      return this.drawString(s2, x2, y2, color, false);
   }

   public void drawCenteredString(String s2, int x2, int y2, int color, boolean shadow) {
      if(shadow) {
         this.drawStringWithShadow(s2, (float)(x2 - this.getStringWidth(s2) / 2), (float)y2, color);
      } else {
         this.drawString(s2, x2 - this.getStringWidth(s2) / 2, y2, color);
      }

   }

   public void drawCenteredStringXY(String s2, int x2, int y2, int color, boolean shadow) {
      this.drawCenteredString(s2, x2, y2 - this.getHeight() / 2, color, shadow);
   }

   public void drawCenteredString(String s2, int x2, int y2, int color) {
      this.drawStringWithShadow(s2, (float)(x2 - this.getStringWidth(s2) / 2), (float)y2, color);
   }

   public int drawString(String text, float x2, float y2, int color, boolean shadow) {
      int result = 0;
      String[] lines = text.split("\n");

      for(int i2 = 0; i2 < lines.length; ++i2) {
         result = this.drawLine(lines[i2], x2, y2 + (float)(i2 * this.getHeight()), color, shadow);
      }

      return result;
   }

   private int drawLine(String text, float x2, float y2, int color, boolean shadow) {
      if(text == null) {
         return 0;
      } else {
         GL11.glPushMatrix();
         GL11.glTranslated((double)x2 - 1.5D, (double)y2 + 0.5D, 0.0D);
         boolean wasBlend = GL11.glGetBoolean(3042);
         GlStateManager.enableAlpha();
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         GL11.glEnable(3553);
         if((color & -67108864) == 0) {
            color |= -16777216;
         }

         if(shadow) {
            color = (color & 16579836) >> 2 | color & -16777216;
         }

         float red = (float)(color >> 16 & 255) / 255.0F;
         float green = (float)(color >> 8 & 255) / 255.0F;
         float blue = (float)(color & 255) / 255.0F;
         float alpha = (float)(color >> 24 & 255) / 255.0F;
         Color c2 = new Color(red, green, blue, alpha);
         if(text.contains("搂")) {
            String[] parts = text.split("搂");
            Color currentColor = c2;
            PureFont currentFont = this.font;
            int width = 0;
            boolean randomCase = false;
            boolean bold = false;
            boolean italic = false;
            boolean strikethrough = false;
            boolean underline = false;

            for(int index = 0; index < parts.length; ++index) {
               if(parts[index].length() > 0) {
                  if(index == 0) {
                     currentFont.drawString(parts[index], (double)width, 0.0D, currentColor, shadow);
                     width += currentFont.getStringWidth(parts[index]);
                  } else {
                     String words = parts[index].substring(1);
                     char type = parts[index].charAt(0);
                     int colorIndex = this.colorcodeIdentifiers.indexOf(type);
                     if(colorIndex != -1) {
                        if(colorIndex < 16) {
                           int colorcode = this.colorCode[colorIndex];
                           currentColor = this.getColor(colorcode, alpha);
                           bold = false;
                           italic = false;
                           randomCase = false;
                           underline = false;
                           strikethrough = false;
                        } else if(colorIndex == 16) {
                           randomCase = true;
                        } else if(colorIndex == 17) {
                           bold = true;
                        } else if(colorIndex == 18) {
                           strikethrough = true;
                        } else if(colorIndex == 19) {
                           underline = true;
                        } else if(colorIndex == 20) {
                           italic = true;
                        } else if(colorIndex == 21) {
                           bold = false;
                           italic = false;
                           randomCase = false;
                           underline = false;
                           strikethrough = false;
                           currentColor = c2;
                        } else if(colorIndex > 21) {
                           Color customColor = this.customColorCodes[type];
                           currentColor = new Color((float)customColor.getRed() / 255.0F, (float)customColor.getGreen() / 255.0F, (float)customColor.getBlue() / 255.0F, alpha);
                        }
                     }

                     if(bold && italic) {
                        this.boldItalicFont.drawString(randomCase?this.toRandom(currentFont, words):words, (double)width, 0.0D, currentColor, shadow);
                        currentFont = this.boldItalicFont;
                     } else if(bold) {
                        this.boldFont.drawString(randomCase?this.toRandom(currentFont, words):words, (double)width, 0.0D, currentColor, shadow);
                        currentFont = this.boldFont;
                     } else if(italic) {
                        this.italicFont.drawString(randomCase?this.toRandom(currentFont, words):words, (double)width, 0.0D, currentColor, shadow);
                        currentFont = this.italicFont;
                     } else {
                        this.font.drawString(randomCase?this.toRandom(currentFont, words):words, (double)width, 0.0D, currentColor, shadow);
                        currentFont = this.font;
                     }

                     float u2 = (float)this.font.getHeight() / 16.0F;
                     int h2 = currentFont.getStringHeight(words);
                     if(strikethrough) {
                        this.drawLine((double)width / 2.0D + 1.0D, (double)(h2 / 3), (double)(width + currentFont.getStringWidth(words)) / 2.0D + 1.0D, (double)(h2 / 3), u2);
                     }

                     if(underline) {
                        this.drawLine((double)width / 2.0D + 1.0D, (double)(h2 / 2), (double)(width + currentFont.getStringWidth(words)) / 2.0D + 1.0D, (double)(h2 / 2), u2);
                     }

                     width += currentFont.getStringWidth(words);
                  }
               }
            }
         } else {
            this.font.drawString(text, 0.0D, 0.0D, c2, shadow);
         }

         if(!wasBlend) {
            GL11.glDisable(3042);
         }

         GL11.glPopMatrix();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         return (int)(x2 + (float)this.getStringWidth(text));
      }
   }

   private String toRandom(PureFont font, String text) {
      String newText = "";
      String allowedCharacters = "脌脕脗脠脢脣脥脫脭脮脷脽茫玫臒陌谋艗艙艦艧糯诺啪葒\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000脟眉茅芒盲脿氓莽锚毛猫茂卯矛脛脜脡忙脝么枚貌没霉每脰脺酶拢脴脳茠谩铆贸煤帽脩陋潞驴庐卢陆录隆芦禄鈻戔枓鈻撯攤鈹も暋鈺⑩晼鈺曗暎鈺戔晽鈺濃暅鈺涒攼鈹斺敶鈹敎鈹�鈹尖暈鈺熲暁鈺斺暕鈺︹暊鈺愨暚鈺р暔鈺も暐鈺欌晿鈺掆晸鈺暘鈹樷攲鈻堚杽鈻屸枑鈻�伪尾螕蟺危蟽渭蟿桅螛惟未鈭炩垍鈭堚埄鈮÷扁墺鈮も尃鈱∶封増掳鈭櫬封垰鈦柯测枲\u0000";

      for(char c2 : text.toCharArray()) {
         if(ChatAllowedCharacters.isAllowedCharacter(c2)) {
            int index = this.fontRandom.nextInt("脌脕脗脠脢脣脥脫脭脮脷脽茫玫臒陌谋艗艙艦艧糯诺啪葒\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000脟眉茅芒盲脿氓莽锚毛猫茂卯矛脛脜脡忙脝么枚貌没霉每脰脺酶拢脴脳茠谩铆贸煤帽脩陋潞驴庐卢陆录隆芦禄鈻戔枓鈻撯攤鈹も暋鈺⑩晼鈺曗暎鈺戔晽鈺濃暅鈺涒攼鈹斺敶鈹敎鈹�鈹尖暈鈺熲暁鈺斺暕鈺︹暊鈺愨暚鈺р暔鈺も暐鈺欌晿鈺掆晸鈺暘鈹樷攲鈻堚杽鈻屸枑鈻�伪尾螕蟺危蟽渭蟿桅螛惟未鈭炩垍鈭堚埄鈮÷扁墺鈮も尃鈱∶封増掳鈭櫬封垰鈦柯测枲\u0000".length());
            newText = String.valueOf(String.valueOf(newText)) + "脌脕脗脠脢脣脥脫脭脮脷脽茫玫臒陌谋艗艙艦艧糯诺啪葒\u0000\u0000\u0000\u0000\u0000\u0000\u0000 !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~\u0000脟眉茅芒盲脿氓莽锚毛猫茂卯矛脛脜脡忙脝么枚貌没霉每脰脺酶拢脴脳茠谩铆贸煤帽脩陋潞驴庐卢陆录隆芦禄鈻戔枓鈻撯攤鈹も暋鈺⑩晼鈺曗暎鈺戔晽鈺濃暅鈺涒攼鈹斺敶鈹敎鈹�鈹尖暈鈺熲暁鈺斺暕鈺︹暊鈺愨暚鈺р暔鈺も暐鈺欌晿鈺掆晸鈺暘鈹樷攲鈻堚杽鈻屸枑鈻�伪尾螕蟺危蟽渭蟿桅螛惟未鈭炩垍鈭堚埄鈮÷扁墺鈮も尃鈱∶封増掳鈭櫬封垰鈦柯测枲\u0000".toCharArray()[index];
         }
      }

      return newText;
   }

   public int getStringHeight(String text) {
      return text == null?0:this.font.getStringHeight(text) / 2;
   }

   public int getHeight() {
      return this.font.getHeight() / 2;
   }

   public static String getFormatFromString(String p_78282_0_) {
      String var1 = "";
      int var2 = -1;
      int var3 = p_78282_0_.length();

      while((var2 = p_78282_0_.indexOf(167, var2 + 1)) != -1) {
         if(var2 < var3 - 1) {
            char var4 = p_78282_0_.charAt(var2 + 1);
            if(isFormatColor(var4)) {
               var1 = "搂" + var4;
            } else if(isFormatSpecial(var4)) {
               var1 = String.valueOf(String.valueOf(var1)) + "搂" + var4;
            }
         }
      }

      return var1;
   }

   private static boolean isFormatSpecial(char formatChar) {
      return formatChar >= 107 && formatChar <= 111 || formatChar >= 75 && formatChar <= 79 || formatChar == 114 || formatChar == 82;
   }

   public int getColorCode(char p_175064_1_) {
      return this.colorCode["0123456789abcdef".indexOf(p_175064_1_)];
   }

   public void setBidiFlag(boolean state) {
      this.bidi = state;
   }

   public boolean getBidiFlag() {
      return this.bidi;
   }

   private int sizeStringToWidth(String str, int wrapWidth) {
      int var3 = str.length();
      int var4 = 0;
      int var5 = 0;
      int var6 = -1;

      for(boolean var7 = false; var5 < var3; ++var5) {
         char var8 = str.charAt(var5);
         switch(var8) {
         case '\n':
            --var5;
            break;
         case ' ':
            var6 = var5;
         default:
            var4 += this.getStringWidth(Character.toString(var8));
            if(var7) {
               ++var4;
            }
            break;
         case '搂':
            if(var5 < var3 - 1) {
               ++var5;
               char var9;
               if((var9 = str.charAt(var5)) != 108 && var9 != 76) {
                  if(var9 == 114 || var9 == 82 || isFormatColor(var9)) {
                     var7 = false;
                  }
               } else {
                  var7 = true;
               }
            }
         }

         if(var8 == 10) {
            ++var5;
            var6 = var5;
            break;
         }

         if(var4 > wrapWidth) {
            break;
         }
      }

      return var5 != var3 && var6 != -1 && var6 < var5?var6:var5;
   }

   private static boolean isFormatColor(char colorChar) {
      return colorChar >= 48 && colorChar <= 57 || colorChar >= 97 && colorChar <= 102 || colorChar >= 65 && colorChar <= 70;
   }

   public int getCharWidth(char c2) {
      return this.getStringWidth(Character.toString(c2));
   }

   public int getStringWidth(String text) {
      if(text == null) {
         return 0;
      } else if(!text.contains("搂")) {
         return this.font.getStringWidth(text) / 2;
      } else {
         String[] parts = text.split("搂");
         PureFont currentFont = this.font;
         int width = 0;
         boolean bold = false;
         boolean italic = false;

         for(int index = 0; index < parts.length; ++index) {
            if(parts[index].length() > 0) {
               if(index == 0) {
                  width += currentFont.getStringWidth(parts[index]);
               } else {
                  String words = parts[index].substring(1);
                  char type = parts[index].charAt(0);
                  int colorIndex = this.colorcodeIdentifiers.indexOf(type);
                  if(colorIndex != -1) {
                     if(colorIndex < 16) {
                        bold = false;
                        italic = false;
                     } else if(colorIndex != 16) {
                        if(colorIndex == 17) {
                           bold = true;
                        } else if(colorIndex != 18 && colorIndex != 19) {
                           if(colorIndex == 20) {
                              italic = true;
                           } else if(colorIndex == 21) {
                              bold = false;
                              italic = false;
                           }
                        }
                     }
                  }

                  currentFont = bold && italic?this.boldItalicFont:(bold?this.boldFont:(italic?this.italicFont:this.font));
                  width += currentFont.getStringWidth(words);
               }
            }
         }

         return width / 2;
      }
   }

   public void setFont(Font font, boolean antiAlias, int charOffset) {
      synchronized(this) {
         this.font = new PureFont(font, antiAlias, charOffset);
         this.boldFont = new PureFont(font.deriveFont(1), antiAlias, charOffset);
         this.italicFont = new PureFont(font.deriveFont(2), antiAlias, charOffset);
         this.boldItalicFont = new PureFont(font.deriveFont(3), antiAlias, charOffset);
         this.FONT_HEIGHT = this.getHeight();
      }
   }

   public PureFont getFont() {
      return this.font;
   }

   public String getFontName() {
      return this.font.getFont().getFontName();
   }

   public int getSize() {
      return this.font.getFont().getSize();
   }

   public List wrapWords(String text, double width) {
      ArrayList<String> finalWords = new ArrayList();
      if((double)this.getStringWidth(text) > width) {
         String[] words = text.split(" ");
         String currentWord = "";
         char lastColorCode = '\uffff';

         for(String word : words) {
            for(int i2 = 0; i2 < word.toCharArray().length; ++i2) {
               char c2 = word.toCharArray()[i2];
               if(c2 == 167 && i2 < word.toCharArray().length - 1) {
                  lastColorCode = word.toCharArray()[i2 + 1];
               }
            }

            if((double)this.getStringWidth(String.valueOf(String.valueOf(currentWord)) + word + " ") < width) {
               currentWord = String.valueOf(String.valueOf(currentWord)) + word + " ";
            } else {
               finalWords.add(currentWord);
               currentWord = lastColorCode == 99?String.valueOf(String.valueOf(word)) + " ":"搂" + lastColorCode + word + " ";
            }
         }

         if(!currentWord.equals("")) {
            if((double)this.getStringWidth(currentWord) < width) {
               finalWords.add(lastColorCode == 122?String.valueOf(String.valueOf(currentWord)) + " ":"搂" + lastColorCode + currentWord + " ");
               currentWord = "";
            } else {
               for(String s2 : this.formatString(currentWord, width)) {
                  finalWords.add(s2);
               }
            }
         }
      } else {
         finalWords.add(text);
      }

      return finalWords;
   }

   public List<String> formatString(String s2, double width) {
      ArrayList<String> finalWords = new ArrayList();
      String currentWord = "";
      char lastColorCode = '\uffff';

      for(int i2 = 0; i2 < s2.toCharArray().length; ++i2) {
         char c2 = s2.toCharArray()[i2];
         if(c2 == 167 && i2 < s2.toCharArray().length - 1) {
            lastColorCode = s2.toCharArray()[i2 + 1];
         }

         if((double)this.getStringWidth(String.valueOf(String.valueOf(currentWord)) + c2) < width) {
            currentWord = String.valueOf(String.valueOf(currentWord)) + c2;
         } else {
            finalWords.add(currentWord);
            currentWord = lastColorCode == 97?String.valueOf(c2):"搂" + lastColorCode + c2;
         }
      }

      if(!currentWord.equals("")) {
         finalWords.add(currentWord);
      }

      return finalWords;
   }

   private void drawLine(double x2, double y2, double x1, double y1, float width) {
      GL11.glDisable(3553);
      GL11.glLineWidth(width);
      GL11.glBegin(1);
      GL11.glVertex2d(x2, y2);
      GL11.glVertex2d(x1, y1);
      GL11.glEnd();
      GL11.glEnable(3553);
   }

   public boolean isAntiAliasing() {
      return this.font.isAntiAlias() && this.boldFont.isAntiAlias() && this.italicFont.isAntiAlias() && this.boldItalicFont.isAntiAlias();
   }

   public void setAntiAliasing(boolean antiAlias) {
      this.font.setAntiAlias(antiAlias);
      this.boldFont.setAntiAlias(antiAlias);
      this.italicFont.setAntiAlias(antiAlias);
      this.boldItalicFont.setAntiAlias(antiAlias);
   }

   private void setupMinecraftColorcodes() {
      for(int index = 0; index < 32; ++index) {
         int var6 = (index >> 3 & 1) * 85;
         int var7 = (index >> 2 & 1) * 170 + var6;
         int var8 = (index >> 1 & 1) * 170 + var6;
         int var9 = (index >> 0 & 1) * 170 + var6;
         if(index == 6) {
            var7 += 85;
         }

         if(index >= 16) {
            var7 /= 4;
            var8 /= 4;
            var9 /= 4;
         }

         this.colorCode[index] = (var7 & 255) << 16 | (var8 & 255) << 8 | var9 & 255;
      }

   }

   public String trimStringToWidth(String p_78269_1_, int p_78269_2_) {
      return this.trimStringToWidth(p_78269_1_, p_78269_2_, false);
   }

   public String trimStringToWidth(String p_78262_1_, int p_78262_2_, boolean p_78262_3_) {
      StringBuilder var4 = new StringBuilder();
      int var5 = 0;
      int var6 = p_78262_3_?p_78262_1_.length() - 1:0;
      int var7 = p_78262_3_?-1:1;
      boolean var8 = false;
      boolean var9 = false;

      for(int var10 = var6; var10 >= 0 && var10 < p_78262_1_.length() && var5 < p_78262_2_; var10 += var7) {
         char var11 = p_78262_1_.charAt(var10);
         int var12 = this.getStringWidth(Character.toString(var11));
         if(var8) {
            var8 = false;
            if(var11 != 108 && var11 != 76) {
               if(var11 == 114 || var11 == 82) {
                  var9 = false;
               }
            } else {
               var9 = true;
            }
         } else if(var12 < 0) {
            var8 = true;
         } else {
            var5 += var12;
            if(var9) {
               ++var5;
            }
         }

         if(var5 > p_78262_2_) {
            break;
         }

         if(p_78262_3_) {
            var4.insert(0, var11);
         } else {
            var4.append(var11);
         }
      }

      return var4.toString();
   }

   public List listFormattedStringToWidth(String str, int wrapWidth) {
      return Arrays.asList(this.wrapFormattedStringToWidth(str, wrapWidth).split("\n"));
   }

   public String wrapFormattedStringToWidth(String str, int wrapWidth) {
      int var3 = this.sizeStringToWidth(str, wrapWidth);
      if(str.length() <= var3) {
         return str;
      } else {
         String var4 = str.substring(0, var3);
         char var5 = str.charAt(var3);
         boolean var6 = var5 == 32 || var5 == 10;
         String var7 = String.valueOf(String.valueOf(getFormatFromString(var4))) + str.substring(var3 + (var6?1:0));
         return String.valueOf(String.valueOf(var4)) + "\n" + this.wrapFormattedStringToWidth(var7, wrapWidth);
      }
   }

   public Color getColor(int colorCode, float alpha) {
      return new Color((float)(colorCode >> 16) / 255.0F, (float)(colorCode >> 8 & 255) / 255.0F, (float)(colorCode & 255) / 255.0F, alpha);
   }

   private String setupColorcodeIdentifier() {
      String minecraftColorCodes = "0123456789abcdefklmnor";

      for(int i2 = 0; i2 < this.customColorCodes.length; ++i2) {
         if(this.customColorCodes[i2] != null) {
            minecraftColorCodes = String.valueOf(String.valueOf(minecraftColorCodes)) + (char)i2;
         }
      }

      return minecraftColorCodes;
   }

   public void onResourceManagerReload(IResourceManager p_110549_1_) {
   }
}
