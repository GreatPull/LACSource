package net.minecraft.entity.player.Really.Client.ui.font;

import java.awt.Font;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.ui.font.CFontRenderer;
import net.minecraft.entity.player.Really.Client.ui.font.PureFontRenderer;
import net.minecraft.util.ResourceLocation;

public class FontManager {
   private static FontManager instance;
   private Map fontMap = new HashMap();
   public static final PureFontRenderer fontRendererGUI = new PureFontRenderer(getFont(40), true, 8);
   public static final PureFontRenderer fontRendererScale = new PureFontRenderer(getFont(36), true, 8);

   private FontManager() {
      this.registerFonts();
   }

   public static FontManager getInstance() {
      if(instance == null) {
         instance = new FontManager();
      }

      return instance;
   }

   public Map getFontMap() {
      return this.fontMap;
   }

   public CFontRenderer getFont(String font) {
      return (CFontRenderer)this.fontMap.get(font);
   }

   private void registerFonts() {
      this.fontMap.put("tabui", new CFontRenderer(new Font("Comfortaa", 0, 18), true, false));
      this.fontMap.put("bariol", new CFontRenderer(new Font("Bariol-Regular", 0, 18), true, false));
      this.fontMap.put("40", new CFontRenderer(new Font("Comfortaa", 0, 40), true, false));
      this.fontMap.put("title", new CFontRenderer(new Font("Comfortaa", 0, 62), true, false));
      this.fontMap.put("10", new CFontRenderer(new Font("Comfortaa", 0, 20), true, false));
      this.fontMap.put("BAROND", new CFontRenderer(new Font("Baron-Neue-Bold", 0, 45), true, false));
   }

   private static Font getFont(int size) {
      Font font = null;

      try {
         InputStream is2 = Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation("0day/Comfortaa_Regular.ttf")).getInputStream();
         font = Font.createFont(0, is2);
         font = font.deriveFont(0, (float)size);
      } catch (Exception var3) {
         var3.printStackTrace();
         System.out.println("Error loading font");
         font = new Font("default", 0, size);
      }

      System.out.println("Fonts loading?");
      return font;
   }
}
