package net.minecraft.entity.player.Really.Client.module.modules.combat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventAttack;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPacketSend;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.management.ModuleManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.world.World;

public class CustomAntiBot extends Module {
   private Option tabValue = new Option("Tab", "Tab", Boolean.valueOf(true));
   private Mode tabModeValue = new Mode("TabMode", "TabMode", CustomAntiBot.TabMode.values(), CustomAntiBot.TabMode.Contains);
   private Option entityIDValue = new Option("EntityID", "EntityID", Boolean.valueOf(true));
   private Option colorValue = new Option("Color", "Color", Boolean.valueOf(false));
   private Option livingTimeValue = new Option("LivingTime", "LivingTime", Boolean.valueOf(false));
   private Option groundValue = new Option("Ground", "Ground", Boolean.valueOf(true));
   private Option airValue = new Option("Air", "Air", Boolean.valueOf(false));
   private Option invaildGroundValue = new Option("InvaildGround", "InvaildGround", Boolean.valueOf(true));
   private Option swingValue = new Option("Swing", "Swing", Boolean.valueOf(false));
   private Option healthValue = new Option("Health", "Health", Boolean.valueOf(false));
   private Option derpValue = new Option("Derp", "Derp", Boolean.valueOf(true));
   private Option wasInvisibleValue = new Option("WasInvisible", "WasInvisible", Boolean.valueOf(false));
   private Option armorValue = new Option("Armor", "Armor", Boolean.valueOf(false));
   private Option pingValue = new Option("Ping", "Ping", Boolean.valueOf(false));
   private Option needHitValue = new Option("NeedHit", "NeedHit", Boolean.valueOf(false));
   private final List ground = new ArrayList();
   private final List air = new ArrayList();
   private final Map invaildGround = new HashMap();
   private final List swing = new ArrayList();
   private final List invisible = new ArrayList();
   private final List hitted = new ArrayList();

   public CustomAntiBot() {
      super("CustomAntiBot", new String[]{"Antibot"}, ModuleType.Combat);
      this.addValues(new Value[]{this.tabValue, this.tabModeValue, this.entityIDValue, this.colorValue, this.livingTimeValue, this.groundValue, this.airValue, this.invaildGroundValue, this.swingValue, this.healthValue, this.derpValue, this.wasInvisibleValue, this.armorValue, this.pingValue, this.needHitValue});
   }

   public void onDisable() {
      this.clearAll();
      super.onDisable();
   }

   @EventHandler
   public void onPacket(EventPacketSend event) {
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer != null) {
         var10000 = mc;
         if(Minecraft.theWorld != null) {
            Packet packet = EventPacketSend.getPacket();
            if(packet instanceof S14PacketEntity) {
               S14PacketEntity packetAnimation = (S14PacketEntity)EventPacketSend.getPacket();
               Minecraft var10001 = mc;
               Entity entity = packetAnimation.getEntity(Minecraft.theWorld);
               if(entity instanceof EntityPlayer) {
                  if(packetAnimation.getOnGround() && !this.ground.contains(Integer.valueOf(entity.getEntityId()))) {
                     this.ground.add(Integer.valueOf(entity.getEntityId()));
                  }

                  if(!packetAnimation.getOnGround() && !this.air.contains(Integer.valueOf(entity.getEntityId()))) {
                     this.air.add(Integer.valueOf(entity.getEntityId()));
                  }

                  if(packetAnimation.getOnGround()) {
                     if(entity.prevPosY != entity.posY) {
                        this.invaildGround.put(Integer.valueOf(entity.getEntityId()), Integer.valueOf(((Integer)this.invaildGround.getOrDefault(Integer.valueOf(entity.getEntityId()), Integer.valueOf(0))).intValue() + 1));
                     }
                  } else {
                     int currentVL = ((Integer)this.invaildGround.getOrDefault(Integer.valueOf(entity.getEntityId()), Integer.valueOf(0))).intValue() / 2;
                     if(currentVL <= 0) {
                        this.invaildGround.remove(Integer.valueOf(entity.getEntityId()));
                     } else {
                        this.invaildGround.put(Integer.valueOf(entity.getEntityId()), Integer.valueOf(currentVL));
                     }
                  }

                  if(entity.isInvisible() && !this.invisible.contains(Integer.valueOf(entity.getEntityId()))) {
                     this.invisible.add(Integer.valueOf(entity.getEntityId()));
                  }
               }
            }

            if(packet instanceof S0BPacketAnimation) {
               S0BPacketAnimation packetAnimation1 = (S0BPacketAnimation)EventPacketSend.getPacket();
               var10000 = mc;
               Entity entity = Minecraft.theWorld.getEntityByID(packetAnimation1.getEntityID());
               if(entity instanceof EntityLivingBase && packetAnimation1.getAnimationType() == 0 && !this.swing.contains(Integer.valueOf(entity.getEntityId()))) {
                  this.swing.add(Integer.valueOf(entity.getEntityId()));
               }
            }

            return;
         }
      }

   }

   @EventHandler
   public void onAttack(EventAttack e) {
      Entity entity = e.getEntity();
      if(entity instanceof EntityLivingBase && !this.hitted.contains(Integer.valueOf(entity.getEntityId()))) {
         this.hitted.add(Integer.valueOf(entity.getEntityId()));
      }

   }

   @EventHandler
   public void onWorld(World event) {
      this.clearAll();
   }

   private void clearAll() {
      this.hitted.clear();
      this.swing.clear();
      this.ground.clear();
      this.invaildGround.clear();
      this.invisible.clear();
   }

   public static boolean isBot(EntityLivingBase entity) {
      if(!(entity instanceof EntityPlayer)) {
         return false;
      } else {
         CustomAntiBot antiBot = (CustomAntiBot)ModuleManager.getModuleByClass(CustomAntiBot.class);
         if(antiBot != null && antiBot.isEnabled()) {
            if(((Boolean)antiBot.colorValue.getValue()).booleanValue() && !entity.getDisplayName().getFormattedText().replace("搂r", "").contains("搂")) {
               return true;
            } else if(((Boolean)antiBot.livingTimeValue.getValue()).booleanValue() && entity.ticksExisted < 40) {
               return true;
            } else if(((Boolean)antiBot.groundValue.getValue()).booleanValue() && !antiBot.ground.contains(Integer.valueOf(entity.getEntityId()))) {
               return true;
            } else if(((Boolean)antiBot.airValue.getValue()).booleanValue() && !antiBot.air.contains(Integer.valueOf(entity.getEntityId()))) {
               return true;
            } else if(((Boolean)antiBot.swingValue.getValue()).booleanValue() && !antiBot.swing.contains(Integer.valueOf(entity.getEntityId()))) {
               return true;
            } else if(((Boolean)antiBot.healthValue.getValue()).booleanValue() && entity.getHealth() > 20.0F) {
               return true;
            } else if(!((Boolean)antiBot.entityIDValue.getValue()).booleanValue() || entity.getEntityId() < 1000000000 && entity.getEntityId() > -1) {
               if(!((Boolean)antiBot.derpValue.getValue()).booleanValue() || entity.rotationPitch <= 90.0F && entity.rotationPitch >= -90.0F) {
                  if(((Boolean)antiBot.wasInvisibleValue.getValue()).booleanValue() && antiBot.invisible.contains(Integer.valueOf(entity.getEntityId()))) {
                     return true;
                  } else {
                     if(((Boolean)antiBot.armorValue.getValue()).booleanValue()) {
                        EntityPlayer player = (EntityPlayer)entity;
                        if(player.inventory.armorInventory[0] == null && player.inventory.armorInventory[1] == null && player.inventory.armorInventory[2] == null && player.inventory.armorInventory[3] == null) {
                           return true;
                        }
                     }

                     if(((Boolean)antiBot.pingValue.getValue()).booleanValue()) {
                        EntityPlayer player = (EntityPlayer)entity;
                        Minecraft var100001 = mc;
                        if(Minecraft.getNetHandler().getPlayerInfo(player.getUniqueID()).getResponseTime() == 0) {
                           return true;
                        }
                     }

                     if(((Boolean)antiBot.needHitValue.getValue()).booleanValue() && !antiBot.hitted.contains(Integer.valueOf(entity.getEntityId()))) {
                        return true;
                     } else if(((Boolean)antiBot.invaildGroundValue.getValue()).booleanValue() && ((Integer)antiBot.invaildGround.getOrDefault(Integer.valueOf(entity.getEntityId()), Integer.valueOf(0))).intValue() >= 10) {
                        return true;
                     } else {
                        if(!entity.getName().isEmpty()) {
                           String var10000 = entity.getName();
                           Minecraft var10001 = mc;
                           if(!var10000.equals(Minecraft.thePlayer.getName())) {
                              return false;
                           }
                        }

                        return true;
                     }
                  }
               } else {
                  return true;
               }
            } else {
               return true;
            }
         } else {
            return false;
         }
      }
   }

   static enum TabMode {
      Equals,
      Contains;
   }
}
