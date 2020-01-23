package net.minecraft.entity.player.Really.Client.api.events.rendering;

import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.Really.Client.api.Event;

public class EventRender2D extends Event {
   private float partialTicks;

   public EventRender2D(float partialTicks) {
      this.partialTicks = partialTicks;
   }

   public float getPartialTicks() {
      return this.partialTicks;
   }

   public void setPartialTicks(float partialTicks) {
      this.partialTicks = partialTicks;
   }

   public ScaledResolution getResolution() {
      return null;
   }
}
