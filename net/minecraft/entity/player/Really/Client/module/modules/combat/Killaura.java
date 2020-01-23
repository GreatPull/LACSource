package net.minecraft.entity.player.Really.Client.module.modules.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPacketSend;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPostUpdate;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.management.FriendManager;
import net.minecraft.entity.player.Really.Client.management.ModuleManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.module.modules.combat.AntiBot;
import net.minecraft.entity.player.Really.Client.module.modules.combat.AuraMode;
import net.minecraft.entity.player.Really.Client.module.modules.combat.AutoHeal;
import net.minecraft.entity.player.Really.Client.module.modules.combat.Criticals;
import net.minecraft.entity.player.Really.Client.module.modules.movement.Scaffold;
import net.minecraft.entity.player.Really.Client.module.modules.player.Teams;
import net.minecraft.entity.player.Really.Client.utils.Helper;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;
import net.minecraft.entity.player.Really.Client.utils.math.MathUtil;
import net.minecraft.entity.player.Really.Client.utils.math.RotationUtil;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;

public class Killaura extends Module {
   private static String curBot = null;
   public static EntityLivingBase curTarget = null;
   private TimerUtil timer = new TimerUtil();
   public static Entity target;
   private boolean doBlock;
   private boolean unBlock;
   private List targets = new ArrayList(0);
   private int index;
   private int xd;
   private int tpdelay;
   private Numbers aps = new Numbers("APS", "APS", Double.valueOf(10.0D), Double.valueOf(1.0D), Double.valueOf(20.0D), Double.valueOf(0.5D));
   private Numbers crack = new Numbers("CrackSize", "CrackSize", Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(5.0D), Double.valueOf(1.0D));
   private Numbers reach = new Numbers("Reach", "reach", Double.valueOf(4.5D), Double.valueOf(1.0D), Double.valueOf(5.0D), Double.valueOf(0.1D));
   private Option blocking = new Option("Autoblock", "autoblock", Boolean.valueOf(true));
   private static Numbers Turnspeed = new Numbers("TurnSpeed", "TurnSpeed", Double.valueOf(90.0D), Double.valueOf(1.0D), Double.valueOf(360.0D), Double.valueOf(1.0D));
   private static Numbers Switchdelay = new Numbers("Switchdelay", "switchdelay", Double.valueOf(11.0D), Double.valueOf(0.0D), Double.valueOf(50.0D), Double.valueOf(1.0D));
   private Option raycast = new Option("Raycast", "Raycast", Boolean.valueOf(true));
   private Option players = new Option("Players", "players", Boolean.valueOf(true));
   private Option animals = new Option("Animals", "animals", Boolean.valueOf(false));
   private Option mobs = new Option("Mobs", "mobs", Boolean.valueOf(false));
   private Option invis = new Option("Invisibles", "invisibles", Boolean.valueOf(false));
   private Option hackkiller = new Option("Hackkiller", "hackkiller", Boolean.valueOf(false));
   private Mode mode = new Mode("Mode", "mode", AuraMode.values(), AuraMode.Switch);
   private boolean isBlocking;
   private Comparator angleComparator = Comparator.comparingDouble((e2) -> {
      return (double)RotationUtil.getRotations((Entity)e2)[0];
   });
   private boolean crit;
   private RotationUtil CombatUtil;
   private float curYaw;
   private float curPitch;
   private EntityLivingBase entity;
   private EventPacketSend event;

   public Killaura() {
      super("Killaura", new String[]{"ka", "aura", "killa"}, ModuleType.Combat);
      this.setColor((new Color(226, 54, 30)).getRGB());
      this.angleComparator = Comparator.comparingDouble((e2) -> {
         return (double)RotationUtil.getRotations((Entity)e2)[0];
      });
      this.addValues(new Value[]{this.aps, Switchdelay, this.reach, this.crack, this.blocking, this.players, this.animals, this.mobs, this.raycast, this.invis, this.mode});
   }

   public void onDisable() {
      super.onDisable();
      Minecraft var10000 = mc;
      Minecraft.thePlayer.itemInUseCount = 0;
      this.targets.clear();
      if(((Boolean)this.blocking.getValue()).booleanValue() && this.canBlock()) {
         var10000 = mc;
         if(Minecraft.thePlayer.isBlocking()) {
            this.stopAutoBlockHypixel();
         }
      }

      var10000 = mc;
      Minecraft.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
   }

   public void onEnable() {
      target = null;
      this.index = 0;
      this.xd = 0;
      super.onEnable();
   }

   private boolean canBlock() {
      Minecraft var10000 = mc;
      if(Minecraft.thePlayer.getCurrentEquippedItem() != null) {
         var10000 = mc;
         if(Minecraft.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
            return true;
         }
      }

      return false;
   }

   private void startAutoBlockHypixel() {
      if(Helper.onServer("hyp")) {
         Minecraft var10000 = mc;
         KeyBinding.setKeyBindState(Minecraft.gameSettings.keyBindUseItem.getKeyCode(), true);
         var10000 = mc;
         Minecraft var10001 = mc;
         Minecraft var10002 = mc;
         Minecraft var10003 = mc;
         if(Minecraft.playerController.sendUseItem(Minecraft.thePlayer, Minecraft.theWorld, Minecraft.thePlayer.inventory.getCurrentItem())) {
            mc.getItemRenderer().resetEquippedProgress2();
         }
      }

   }

   private void stopAutoBlockHypixel() {
      if(Helper.onServer("hyp")) {
         Minecraft var10000 = mc;
         KeyBinding.setKeyBindState(Minecraft.gameSettings.keyBindUseItem.getKeyCode(), false);
         var10000 = mc;
         Minecraft var10001 = mc;
         Minecraft.playerController.onStoppedUsingItem(Minecraft.thePlayer);
      }

   }

   private void startAutoBlock() {
      if(!Helper.onServer("hyp")) {
         Minecraft var10000 = mc;
         Minecraft var10001 = mc;
         Minecraft var10002 = mc;
         Minecraft var10003 = mc;
         Minecraft.playerController.sendUseItem(Minecraft.thePlayer, Minecraft.theWorld, Minecraft.thePlayer.getCurrentEquippedItem());
         var10000 = mc;
         Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
      }

   }

   private void stopAutoBlock() {
      if(!Helper.onServer("hyp")) {
         Minecraft var10000 = mc;
         Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
      }

   }

   private boolean shouldAttack() {
      return this.timer.hasReached(1000.0D / (((Double)this.aps.getValue()).doubleValue() + MathUtil.randomDouble(0.0D, 5.0D)));
   }

   @EventHandler
   private void onUpdate(EventPreUpdate event) {
      this.setSuffix(this.mode.getValue());
      this.targets = this.loadTargets();
      this.targets.sort(this.angleComparator);
      if(target != null && target instanceof EntityPlayer || target instanceof EntityMob || target instanceof EntityAnimal) {
         target = null;
      }

      if(Minecraft.thePlayer.ticksExisted % ((Double)Switchdelay.getValue()).intValue() == 0 && this.targets.size() > 1) {
         ++this.index;
      }

      if(!this.targets.isEmpty() && this.index >= this.targets.size()) {
         this.index = 0;
      }

      if(!this.targets.isEmpty()) {
         if(this.index >= this.targets.size()) {
            this.index = 0;
         }

         target = (Entity)this.targets.get(this.index);
         if(!Helper.onServer("invaded") && !Helper.onServer("minemen") && !Helper.onServer("faithful")) {
            event.setYaw(RotationUtil.faceTarget(target, 500.0F, 1000.0F, false)[0]);
            if(!AutoHeal.currentlyPotting) {
               event.setPitch(RotationUtil.faceTarget(target, 500.0F, 1000.0F, false)[1]);
            }
         }

         if(((Boolean)this.blocking.getValue()).booleanValue() && this.canBlock() && this.isBlocking) {
            this.stopAutoBlock();
         }
      } else if(((Boolean)this.blocking.getValue()).booleanValue() && this.canBlock()) {
         Minecraft var10000 = mc;
         if(Minecraft.thePlayer.isBlocking()) {
            this.stopAutoBlockHypixel();
         }
      }

   }

   private void swap(int slot, int hotbarNum) {
      Minecraft var10000 = mc;
      Minecraft var10001 = mc;
      Minecraft var10005 = mc;
      Minecraft.playerController.windowClick(Minecraft.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, Minecraft.thePlayer);
   }

   @EventHandler
   private void onUpdatePost(EventPostUpdate e2) {
      int crackSize = ((Double)this.crack.getValue()).intValue();
      if(target != null) {
         double angle = Math.toRadians((double)(target.rotationYaw - 90.0F + 360.0F)) % 360.0D;
         if(this.shouldAttack()) {
            Client var10000 = Client.instance;
            Client.getModuleManager();
            Criticals i2 = (Criticals)ModuleManager.getModuleByName("Criticals");
            if(i2.isEnabled()) {
               i2.autoCrit();
            }
         }

         if(this.mode.getValue() == AuraMode.Switch) {
            this.attack();
         }

         Minecraft var7 = mc;
         if(!Minecraft.thePlayer.isBlocking() && this.canBlock() && ((Boolean)this.blocking.getValue()).booleanValue()) {
            this.startAutoBlock();
         }

         this.timer.reset();
      }

      if(this.canBlock() && ((Boolean)this.blocking.getValue()).booleanValue()) {
         Minecraft var8 = mc;
         if(!Minecraft.thePlayer.isBlocking()) {
            this.startAutoBlockHypixel();
         }
      }

      for(int var6 = 0; (double)var6 < (double)crackSize; ++var6) {
         Minecraft var9 = mc;
         Minecraft.effectRenderer.emitParticleAtEntity(target, EnumParticleTypes.CRIT);
         var9 = mc;
         Minecraft.effectRenderer.emitParticleAtEntity(target, EnumParticleTypes.CRIT_MAGIC);
      }

   }

   private List loadTargets() {
      List var1;
      if(((Boolean)this.hackkiller.getValue()).booleanValue()) {
         Minecraft var10000 = mc;
         var1 = (List)Minecraft.theWorld.loadedEntityList.stream().filter((e2) -> {
            return (double)Minecraft.thePlayer.getDistanceToEntity(e2) <= Double.valueOf(15.0D).doubleValue() + 0.2D && this.qualifies(e2);
         }).collect(Collectors.toList());
      } else {
         Minecraft var2 = mc;
         var1 = (List)Minecraft.theWorld.loadedEntityList.stream().filter((e2) -> {
            Minecraft var10000 = mc;
            return (double)Minecraft.thePlayer.getDistanceToEntity(e2) <= ((Double)this.reach.getValue()).doubleValue() + 0.2D && this.qualifies(e2);
         }).collect(Collectors.toList());
      }

      return var1;
   }

   private boolean qualifies(Entity e2) {
      Minecraft var10001 = mc;
      if(e2 == Minecraft.thePlayer) {
         return false;
      } else {
         Client var10000 = Client.instance;
         Client.getModuleManager();
         Scaffold sca = (Scaffold)ModuleManager.getModuleByName("Scaffold");
         if(sca.isEnabled()) {
            curTarget = null;
            return false;
         } else {
            var10000 = Client.instance;
            Client.getModuleManager();
            AntiBot ab2 = (AntiBot)ModuleManager.getModuleByName("AntiBot");
            return ab2.isServerBot(e2)?false:(!e2.isEntityAlive()?false:(FriendManager.isFriend(e2.getName())?false:(e2 instanceof EntityArmorStand?false:(e2 instanceof EntityPlayer && ((Boolean)this.players.getValue()).booleanValue() && !Teams.isOnSameTeam(e2)?true:(e2 instanceof EntityMob && ((Boolean)this.mobs.getValue()).booleanValue()?true:(e2 instanceof EntityAnimal && ((Boolean)this.animals.getValue()).booleanValue()?true:e2.isInvisible() && !((Boolean)this.invis.getValue()).booleanValue()))))));
         }
      }
   }

   private void attack() {
      if(((Boolean)this.blocking.getValue()).booleanValue() && this.canBlock()) {
         Minecraft var10000 = mc;
         if(Minecraft.thePlayer.isBlocking()) {
            this.stopAutoBlockHypixel();
         }
      }

      if(((Boolean)this.blocking.getValue()).booleanValue() && this.canBlock()) {
         Minecraft var11 = mc;
         if(Minecraft.thePlayer.isBlocking()) {
            this.qualifies(target);
         }
      }

      if(Helper.onServer("invaded")) {
         Minecraft var12 = mc;
         Minecraft.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
      } else {
         Minecraft var13 = mc;
         Minecraft.thePlayer.swingItem();
         double sharpLevel = 0.0D;
         double d2 = 0.0D;
         sharpLevel = target.posX + (target.posX - target.lastTickPosX);
         sharpLevel = d2 + (sharpLevel > target.posX?0.5D:(sharpLevel == target.posX?0.0D:-0.5D));
         double eZ = target.posZ + (target.posZ - target.lastTickPosZ);
         eZ = eZ + (eZ > target.posZ?0.5D:(eZ == target.posZ?0.0D:-0.5D));
      }

      Minecraft var14 = mc;
      Minecraft.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
      var14 = mc;
      float sharpLevel1 = EnchantmentHelper.func_152377_a(Minecraft.thePlayer.getHeldItem(), ((EntityLivingBase)target).getCreatureAttribute());
      if(sharpLevel1 > 0.0F) {
         var14 = mc;
         Minecraft.thePlayer.onEnchantmentCritical(target);
      }

      var14 = mc;
      if(Minecraft.thePlayer.isBlocking() && ((Boolean)this.blocking.getValue()).booleanValue()) {
         var14 = mc;
         if(Minecraft.thePlayer.inventory.getCurrentItem() != null) {
            var14 = mc;
            if(Minecraft.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
               var14 = mc;
               Minecraft var10003 = mc;
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(Minecraft.thePlayer.inventory.getCurrentItem()));
               var14 = mc;
               Minecraft.thePlayer.onEnchantmentCritical(target);
            }
         }
      }

      var14 = mc;
      if(Minecraft.thePlayer.isBlocking() && ((Boolean)this.blocking.getValue()).booleanValue()) {
         var14 = mc;
         Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
      }

      if(((Boolean)this.blocking.getValue()).booleanValue() && this.canBlock()) {
         var14 = mc;
         if(Minecraft.thePlayer.isBlocking()) {
            var14 = mc;
            Minecraft.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
         }
      }

   }

   @EventHandler
   private void blockinglistener(EventPacketSend packet) {
      if(EventPacketSend.getPacket() instanceof C07PacketPlayerDigging && ((C07PacketPlayerDigging)EventPacketSend.getPacket()).getStatus().equals(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM)) {
         this.isBlocking = false;
      }

      C08PacketPlayerBlockPlacement blockPlacement;
      if(EventPacketSend.getPacket() instanceof C08PacketPlayerBlockPlacement && (blockPlacement = (C08PacketPlayerBlockPlacement)EventPacketSend.getPacket()).getStack() != null && blockPlacement.getStack().getItem() instanceof ItemSword && blockPlacement.getPosition().equals(new BlockPos(-1, -1, -1))) {
         this.isBlocking = true;
      }

   }

   public void mmcAttack(EntityLivingBase entity) {
      this.mmcAttack(entity, false);
   }

   public void mmcAttack(EntityLivingBase entity, boolean crit) {
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
}
