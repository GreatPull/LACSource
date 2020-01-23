package net.minecraft.entity.player.Really.Client.module.modules.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventTick;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.potion.Potion;

public class TPAura extends Module {
   private int ticks;
   private List loaded = new ArrayList();
   private EntityLivingBase target;
   private int tpdelay;
   public boolean criticals;
   private TimerUtil timer = new TimerUtil();

   public TPAura() {
      super("TPAura", new String[]{"tpaura"}, ModuleType.Combat);
   }

   @EventHandler
   public void onUpdate(EventTick event) {
      this.setColor((new Color(255, 50, 70)).getRGB());
      ++this.ticks;
      ++this.tpdelay;
      if(this.ticks >= 20 - this.speed()) {
         this.ticks = 0;
         Minecraft var10000 = mc;

         for(Object object : Minecraft.theWorld.loadedEntityList) {
            EntityLivingBase entity;
            if(object instanceof EntityLivingBase && !((entity = (EntityLivingBase)object) instanceof EntityPlayerSP)) {
               var10000 = mc;
               if(Minecraft.thePlayer.getDistanceToEntity(entity) <= 10.0F && entity.isEntityAlive()) {
                  if(this.tpdelay >= 4) {
                     var10000 = mc;
                     Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(entity.posX, entity.posY, entity.posZ, false));
                  }

                  var10000 = mc;
                  if(Minecraft.thePlayer.getDistanceToEntity(entity) < 10.0F) {
                     this.attack(entity);
                  }
               }
            }
         }
      }

   }

   public void attack(EntityLivingBase entity) {
      this.attack(entity, false);
   }

   public void attack(EntityLivingBase entity, boolean crit) {
      float sharpLevel;
      boolean var12;
      label0: {
         Minecraft var10000 = mc;
         Minecraft.thePlayer.swingItem();
         var10000 = mc;
         sharpLevel = EnchantmentHelper.func_152377_a(Minecraft.thePlayer.getHeldItem(), entity.getCreatureAttribute());
         var10000 = mc;
         if(Minecraft.thePlayer.fallDistance > 0.0F) {
            var10000 = mc;
            if(!Minecraft.thePlayer.onGround) {
               var10000 = mc;
               if(!Minecraft.thePlayer.isOnLadder()) {
                  var10000 = mc;
                  if(!Minecraft.thePlayer.isInWater()) {
                     var10000 = mc;
                     if(!Minecraft.thePlayer.isPotionActive(Potion.blindness)) {
                        var10000 = mc;
                        if(Minecraft.thePlayer.ridingEntity == null) {
                           var12 = true;
                           break label0;
                        }
                     }
                  }
               }
            }
         }

         var12 = false;
      }

      boolean vanillaCrit = var12;
      Minecraft var13 = mc;
      Minecraft.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(entity, C02PacketUseEntity.Action.ATTACK));
      if(crit || vanillaCrit) {
         var13 = mc;
         Minecraft.thePlayer.onCriticalHit(entity);
      }

      if(sharpLevel > 0.0F) {
         var13 = mc;
         Minecraft.thePlayer.onEnchantmentCritical(entity);
      }

   }

   private int speed() {
      return 8;
   }
}
