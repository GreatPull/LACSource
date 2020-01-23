package net.minecraft.entity.player.Really.Client.utils;

import net.minecraft.entity.player.Really.Client.api.Event;

public class EventStep extends Event {
   private double stepHeight;
   private double realHeight;
   private boolean active;
   private boolean pre;

   public EventStep(boolean state, double stepHeight, double realHeight) {
      this.pre = state;
      this.stepHeight = stepHeight;
      this.realHeight = realHeight;
   }

   public EventStep(boolean state, double stepHeight) {
      this.pre = state;
      this.realHeight = stepHeight;
      this.stepHeight = stepHeight;
   }

   public boolean isPre() {
      return this.pre;
   }

   public double getStepHeight() {
      return this.stepHeight;
   }

   public boolean isActive() {
      return this.active;
   }

   public void setStepHeight(double stepHeight) {
      this.stepHeight = stepHeight;
   }

   public void setActive(boolean bypass) {
      this.active = bypass;
   }

   public double getRealHeight() {
      return this.realHeight;
   }

   public void setRealHeight(double realHeight) {
      this.realHeight = realHeight;
   }

   public void setHeight(float height) {
      this.stepHeight = (double)height;
   }
}
