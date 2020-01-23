package net.minecraft.entity.player.Really.Client.api;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.Really.Client.api.CancelableEvent;

public class StepEvent extends CancelableEvent {
   private Entity entity;
   private float height;
   private boolean pre;

   public StepEvent(Entity entity, boolean pre) {
      this.entity = entity;
      this.height = entity.stepHeight;
      this.pre = pre;
   }

   public StepEvent(Entity entity) {
      this.entity = entity;
      this.height = entity.stepHeight;
   }

   public Entity getEntity() {
      return this.entity;
   }

   public float getHeight() {
      return this.height;
   }

   public void setHeight(float height) {
      this.height = height;
   }

   public boolean isPre() {
      return this.pre;
   }
}
