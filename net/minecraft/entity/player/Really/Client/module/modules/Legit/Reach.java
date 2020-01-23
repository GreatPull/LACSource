package net.minecraft.entity.player.Really.Client.module.modules.Legit;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.EventAttack;
import net.minecraft.entity.player.Really.Client.utils.EventClickMouse;
import net.minecraft.entity.player.Really.Client.utils.Wrapper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

public class Reach extends Module {
   protected Random rand = new Random();
   public double currentRange = 3.0D;
   public long lastAttack = 0L;
   protected long lastMS = -1L;
   private Option combo = new Option("Combo", "combo", Boolean.valueOf(false));
   private Numbers min = new Numbers("Min Range", "min", Double.valueOf(3.0D), Double.valueOf(3.0D), Double.valueOf(8.0D), Double.valueOf(0.1D));
   private Numbers max = new Numbers("Max Range", "max", Double.valueOf(4.0D), Double.valueOf(3.0D), Double.valueOf(8.0D), Double.valueOf(0.1D));

   public Reach() {
      super("Reach", new String[]{"reachhack", "longarms"}, ModuleType.Legit);
      this.addValues(new Value[]{this.min, this.max, this.combo});
   }

   public boolean hasTimePassedMS(long MS) {
      return this.getCurrentMS() >= this.lastMS + MS;
   }

   public void updatebefore() {
      this.lastMS = this.getCurrentMS();
   }

   public long getCurrentMS() {
      return System.nanoTime() / 1000000L;
   }

   @EventHandler
   public void Attack(EventAttack event) {
      this.lastAttack = System.currentTimeMillis();
   }

   @EventHandler
   public void Update(EventPreUpdate event) {
      if(this.isEnabled()) {
         if(this.hasTimePassedMS(2000L)) {
            double rangeMin = ((Double)this.min.getValue()).doubleValue();
            double rangeMax = ((Double)this.max.getValue()).doubleValue();
            double rangeDiff = rangeMax - rangeMin;
            if(rangeDiff < 0.0D) {
               return;
            }

            this.currentRange = rangeMin + this.rand.nextDouble() * rangeDiff;
            this.updatebefore();
         }

      }
   }

   @EventHandler
   public void Click(EventClickMouse event) {
      if(this.isEnabled()) {
         if(!((Boolean)this.combo.getValue()).booleanValue() || System.currentTimeMillis() - this.lastAttack <= 300L) {
            Object[] objects = Wrapper.getEntity(this.currentRange, 0.0D, 0.0F);
            if(objects != null) {
               Minecraft.objectMouseOver = new MovingObjectPosition((Entity)objects[0], (Vec3)objects[1]);
               Wrapper.mc.pointedEntity = (Entity)objects[0];
            }
         }
      }
   }
}
