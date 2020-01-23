package net.minecraft.entity.player.Really.Client.module.modules.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventTick;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;

public class MotionBlur extends Module {
   public MotionBlur() {
      super("MotionBlur", new String[]{"MotionBlur"}, ModuleType.Render);
   }

   public void onDisable() {
      Minecraft var10000 = mc;
      Minecraft.entityRenderer.useShader = true;
      var10000 = mc;
      if(Minecraft.entityRenderer.theShaderGroup != null) {
         var10000 = mc;
         Minecraft.entityRenderer.theShaderGroup.deleteShaderGroup();
      }

   }

   public void onEnable() {
      Minecraft var10000 = mc;
      EntityRenderer er = Minecraft.entityRenderer;
      er.activateNextShader();
   }

   @EventHandler
   public void onTick(EventTick event) {
      Minecraft var10000 = mc;
      EntityRenderer er = Minecraft.entityRenderer;
      var10000 = mc;
      Minecraft.entityRenderer.useShader = true;
      var10000 = mc;
      if(Minecraft.theWorld != null) {
         var10000 = mc;
         if(Minecraft.entityRenderer.theShaderGroup != null) {
            var10000 = mc;
            if(Minecraft.entityRenderer.theShaderGroup.getShaderGroupName().contains("phosphor")) {
               return;
            }
         }

         if(er.theShaderGroup != null) {
            er.theShaderGroup.deleteShaderGroup();
         }

         er.activateNextShader();
      }

   }
}
