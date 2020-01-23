package net.minecraft.entity.player.Really.Client.module.modules.render;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender2D;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.module.modules.combat.AntiBot;
import net.minecraft.entity.player.Really.Client.ui.font.FontLoaders;
import net.minecraft.entity.player.Really.Client.utils.render.RenderUtil;

public class PlayerList extends Module {
   public static Numbers red = new Numbers("Red", "Red", Double.valueOf(255.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
   public static Numbers green = new Numbers("Green", "Green", Double.valueOf(125.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
   public static Numbers blue = new Numbers("Blue", "Blue", Double.valueOf(0.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
   public static Numbers alpha = new Numbers("alpha", "alpha", Double.valueOf(50.0D), Double.valueOf(0.0D), Double.valueOf(255.0D), Double.valueOf(1.0D));
   float ULX2 = 2.0F;

   public PlayerList() {
      super("PlayerList", new String[]{"PlayerList"}, ModuleType.Render);
      this.addValues(new Value[]{red, green, blue, alpha});
   }

   @EventHandler
   private void renderHud(EventRender2D event) {
      float ULY2 = (float)(RenderUtil.height() / 3);
      float last = 2.0F;

      for(EntityPlayer Player : getTabPlayerList()) {
         ULY2 += 8.0F;
         String Textx = Player.getName() + " [ " + (int)Player.posX + " , " + (int)Player.posY + " , " + (int)Player.posZ + " ]";
         if((float)(FontLoaders.sansation16.getStringWidth(Textx) + 2) > this.ULX2) {
            this.ULX2 = (float)(FontLoaders.sansation16.getStringWidth(Textx) + 2);
         }

         FontLoaders.sansation18.drawStringWithShadow(Textx, (double)last, (double)ULY2, (new Color(((Double)red.getValue()).intValue(), ((Double)green.getValue()).intValue(), ((Double)blue.getValue()).intValue(), ((Double)alpha.getValue()).intValue())).getRGB());
         if((float)(RenderUtil.height() / 3 + 200) == ULY2) {
            ULY2 = (float)(RenderUtil.height() / 3);
            last += this.ULX2;
            this.ULX2 = 0.0F;
         }
      }

   }

   public static List<EntityPlayer> getTabPlayerList() {
      Minecraft mc = AntiBot.mc;
      NetHandlerPlayClient var4 = Minecraft.thePlayer.sendQueue;
      ArrayList list = new ArrayList();

      for(Object o : GuiPlayerTabOverlay.field_175252_a.sortedCopy(var4.getPlayerInfoMap())) {
         NetworkPlayerInfo info = (NetworkPlayerInfo)o;
         if(info != null) {
            Minecraft mc2 = AntiBot.mc;
            list.add(Minecraft.theWorld.getPlayerEntityByName(info.getGameProfile().getName()));
         }
      }

      return list;
   }
}
