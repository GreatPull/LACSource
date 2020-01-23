package net.minecraft.entity.player.Really.Client.api.events.rendering;

import net.minecraft.entity.player.Really.Client.api.Event;
import shadersmod.client.Shaders;

public class EventRender3D extends Event {
   public static float ticks;
   private boolean isUsingShaders;

   public EventRender3D() {
      this.isUsingShaders = Shaders.getShaderPackName() != null;
   }

   public EventRender3D(float ticks) {
      ticks = ticks;
      this.isUsingShaders = Shaders.getShaderPackName() != null;
   }

   public float getPartialTicks() {
      return ticks;
   }

   public boolean isUsingShaders() {
      return this.isUsingShaders;
   }
}
