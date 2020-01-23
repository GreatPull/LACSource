package net.minecraft.entity.player.Really.Client.module.modules.player;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventTick;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;

public class SkinFlash extends Module {
   public SkinFlash() {
      super("SkinFlash", new String[]{"derpskin"}, ModuleType.Player);
      this.setColor((new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255))).getRGB());
   }

   public void onDisable() {
      Minecraft var10000 = mc;
      EnumPlayerModelParts[] parts;
      if(Minecraft.thePlayer != null && (parts = EnumPlayerModelParts.values()) != null) {
         for(EnumPlayerModelParts part : parts) {
            var10000 = mc;
            Minecraft.gameSettings.setModelPartEnabled(part, true);
         }
      }

   }

   @EventHandler
   private void onTick(EventTick e) {
      Minecraft var10000 = mc;
      EnumPlayerModelParts[] parts;
      if(Minecraft.thePlayer != null && (parts = EnumPlayerModelParts.values()) != null) {
         for(EnumPlayerModelParts part : parts) {
            boolean newState = this.isEnabled()?random.nextBoolean():true;
            var10000 = mc;
            Minecraft.gameSettings.setModelPartEnabled(part, newState);
         }
      }

   }
}
