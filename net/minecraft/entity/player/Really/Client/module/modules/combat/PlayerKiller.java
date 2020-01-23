package net.minecraft.entity.player.Really.Client.module.modules.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
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
import net.minecraft.entity.player.Really.Client.module.modules.combat.AutoHeal;
import net.minecraft.entity.player.Really.Client.module.modules.combat.Criticals;
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
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class PlayerKiller extends Module {
   private TimerUtil timer = new TimerUtil();
   private TimerUtil switchdelay = new TimerUtil();
   public static Entity target;
   private List targets = new ArrayList(0);
   private int index;
   private int xd;
   private int tpdelay;
   private Numbers maxaps = new Numbers("MaxAPS", "MaxAPS", Double.valueOf(10.0D), Double.valueOf(1.0D), Double.valueOf(20.0D), Double.valueOf(0.5D));
   private Numbers minaps = new Numbers("MinAPS", "MinAPS", Double.valueOf(10.0D), Double.valueOf(1.0D), Double.valueOf(20.0D), Double.valueOf(0.5D));
   private Numbers attackswitchdelay = new Numbers("SwitchDelay", "SwitchDelay", Double.valueOf(1800.0D), Double.valueOf(1.0D), Double.valueOf(2000.0D), Double.valueOf(5.0D));
   private Numbers reach = new Numbers("Reach", "reach", Double.valueOf(4.5D), Double.valueOf(1.0D), Double.valueOf(6.0D), Double.valueOf(0.1D));
   private Option random = new Option("Random", "Random", Boolean.valueOf(true));
   private Option blocking = new Option("Autoblock", "autoblock", Boolean.valueOf(true));
   private Option players = new Option("Players", "players", Boolean.valueOf(true));
   private Option animals = new Option("Animals", "animals", Boolean.valueOf(true));
   private Option mobs = new Option("Mobs", "mobs", Boolean.valueOf(false));
   private Option invis = new Option("Invisibles", "invisibles", Boolean.valueOf(false));
   private Mode mode = new Mode("Mode", "mode", PlayerKiller.AuraMode.values(), PlayerKiller.AuraMode.Switch);
   private boolean isBlocking;
   private Comparator angleComparator = Comparator.comparingDouble((e2) -> {
      return (double)RotationUtil.getRotations((Entity) e2)[0];
   });

   public PlayerKiller() {
      super("PlayerKiller", new String[]{"PK"}, ModuleType.Combat);
      this.setColor((new Color(226, 54, 30)).getRGB());
      this.addValues(new Value[]{this.random, this.minaps, this.maxaps, this.reach, this.blocking, this.players, this.animals, this.mobs, this.invis, this.mode, this.attackswitchdelay});
   }

   public void onDisable() {
      this.targets.clear();
      if(((Boolean)this.blocking.getValue()).booleanValue() && this.canBlock() && Minecraft.thePlayer.isBlocking()) {
         this.stopAutoBlockHypixel();
      }

   }

   public void onEnable() {
      target = null;
      this.index = 0;
      this.xd = 0;
   }

   private boolean canBlock() {
      return Minecraft.thePlayer.getCurrentEquippedItem() != null && Minecraft.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword;
   }

   private void startAutoBlockHypixel() {
      if(Helper.onServer("hypixel")) {
         Minecraft var10000 = mc;
         KeyBinding.setKeyBindState(Minecraft.gameSettings.keyBindUseItem.getKeyCode(), true);
         if(Minecraft.playerController.sendUseItem(Minecraft.thePlayer, Minecraft.theWorld, Minecraft.thePlayer.inventory.getCurrentItem())) {
            mc.getItemRenderer().resetEquippedProgress2();
         }
      }

   }

   private void stopAutoBlockHypixel() {
      if(Helper.onServer("hypixel")) {
         Minecraft var10000 = mc;
         KeyBinding.setKeyBindState(Minecraft.gameSettings.keyBindUseItem.getKeyCode(), false);
         Minecraft.playerController.onStoppedUsingItem(Minecraft.thePlayer);
      }

   }

   private void startAutoBlock() {
      if(!Helper.onServer("hypixel")) {
         Minecraft.playerController.sendUseItem(Minecraft.thePlayer, Minecraft.theWorld, Minecraft.thePlayer.getCurrentEquippedItem());
      }

   }

   private void stopAutoBlock() {
      if(!Helper.onServer("hypixel")) {
         Minecraft.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
      }

   }

   private boolean shouldAttack() {
      int MaxAPS = ((Double)this.maxaps.getValue()).intValue();
      int MineAPS = ((Double)this.minaps.getValue()).intValue();
      int delayValue = (20 / MaxAPS + MathUtil.randomNumber(MineAPS, MineAPS)) * 50;
      return this.timer.hasReached((double)delayValue);
   }

   @EventHandler
   private void onUpdate(EventPreUpdate event) {
      this.setSuffix(this.mode.getValue());
      this.targets = this.loadTargets();
      this.targets.sort(this.angleComparator);
      if(target != null && target instanceof EntityPlayer || target instanceof EntityMob || target instanceof EntityAnimal) {
         target = null;
      }

      if(this.mode.getValue() != PlayerKiller.AuraMode.Single && this.switchdelay.hasReached(((Double)this.attackswitchdelay.getValue()).doubleValue() + (double)(((Boolean)this.random.getValue()).booleanValue()?(new Random()).nextInt(233):0)) && this.targets.size() > 1) {
         this.switchdelay.reset();
         ++this.index;
      }

      if(!this.targets.isEmpty()) {
         if(this.index >= this.targets.size()) {
            this.index = 0;
         }

         target = (Entity)this.targets.get(this.index);
         if(!Helper.onServer("invaded") && !Helper.onServer("minemen") && !Helper.onServer("faithful")) {
            event.setYaw(RotationUtil.faceTarget(target, 1000.0F, 1000.0F, false)[0]);
            if(!AutoHeal.currentlyPotting) {
               event.setPitch(RotationUtil.faceTarget(target, 1000.0F, 1000.0F, false)[1]);
            }

            if((double)Minecraft.thePlayer.getDistanceToEntity(target) <= 0.5D) {
               event.setPitch(90.0F);
            }

            if((double)Minecraft.thePlayer.getDistanceToEntity(target) > ((Double)this.reach.getValue()).doubleValue()) {
               event.setYaw(RotationUtil.faceTarget(target, 600.0F, 600.0F, false)[0]);
            }

            if((double)Minecraft.thePlayer.getDistanceToEntity(target) <= 1.0D) {
               event.setYaw(RotationUtil.faceTarget(target, 1400.0F, 1400.0F, false)[0]);
               event.setPitch(RotationUtil.faceTarget(target, 1000.0F, 1000.0F, false)[1]);
            }

            if(Minecraft.thePlayer.rotationPitch > 170.0F) {
               Minecraft.thePlayer.rotationPitch = 100.0F;
            }

            Minecraft.thePlayer.rotationYawHead = RotationUtil.faceTarget(target, 1000.0F, 1000.0F, false)[0];
         }

         if(((Boolean)this.blocking.getValue()).booleanValue() && this.canBlock() && this.isBlocking) {
            this.stopAutoBlock();
         }
      } else if(((Boolean)this.blocking.getValue()).booleanValue() && this.canBlock() && Minecraft.thePlayer.isBlocking()) {
         this.stopAutoBlockHypixel();
      }

   }

   private void swap(int slot, int hotbarNum) {
      Minecraft.playerController.windowClick(Minecraft.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, Minecraft.thePlayer);
   }

   @EventHandler
   private void onUpdatePost(EventPostUpdate e) {
      if(Helper.onServer("enjoytheban")) {
         this.reach.setValue(Double.valueOf(4.0D));
         this.maxaps.setValue(Double.valueOf(12.0D));
         this.minaps.setValue(Double.valueOf(8.0D));
      }

      if(!Helper.onServer("faithful") && target != null) {
         double angle = Math.toRadians((double)(target.rotationYaw - 90.0F + 360.0F)) % 360.0D;
         if(this.shouldAttack()) {
            Client var10000 = Client.instance;
            Client.getModuleManager();
            Criticals i2 = (Criticals)ModuleManager.getModuleByName("Criticals");
            if(i2.isEnabled()) {
               i2.autoCrit();
            }

            if(this.mode.getValue() != PlayerKiller.AuraMode.Switch && this.mode.getValue() != PlayerKiller.AuraMode.Single) {
               this.swap(9, Minecraft.thePlayer.inventory.currentItem);
               this.attack();
               this.attack();
               this.attack();
               this.swap(9, Minecraft.thePlayer.inventory.currentItem);
               this.attack();
               this.attack();
            } else {
               this.attack();
            }

            if(!Minecraft.thePlayer.isBlocking() && this.canBlock() && ((Boolean)this.blocking.getValue()).booleanValue()) {
               this.startAutoBlockHypixel();
            }

            this.timer.reset();
         }

         if(this.canBlock() && ((Boolean)this.blocking.getValue()).booleanValue() && !Minecraft.thePlayer.isBlocking()) {
            this.startAutoBlock();
         }
      }

   }

   private List loadTargets() {
      return (List)Minecraft.theWorld.loadedEntityList.stream().filter((e) -> {
         return (double)Minecraft.thePlayer.getDistanceToEntity(e) <= ((Double)this.reach.getValue()).doubleValue() && this.qualifies(e);
      }).collect(Collectors.toList());
   }

   private boolean qualifies(Entity e) {
      if(e == Minecraft.thePlayer) {
         return false;
      } else {
         Client var10000 = Client.instance;
         Client.getModuleManager();
         AntiBot ab = (AntiBot)ModuleManager.getModuleByName("AntiBot");
         return ab.isServerBot(e)?false:(!e.isEntityAlive()?false:(FriendManager.isFriend(e.getName())?false:(e instanceof EntityPlayer && ((Boolean)this.players.getValue()).booleanValue() && !Teams.isOnSameTeam(e)?true:(e instanceof EntityMob && ((Boolean)this.mobs.getValue()).booleanValue()?true:(e instanceof EntityAnimal && ((Boolean)this.animals.getValue()).booleanValue()?true:e.isInvisible() && !((Boolean)this.invis.getValue()).booleanValue())))));
      }
   }

   private void attack() {
      if(((Boolean)this.blocking.getValue()).booleanValue() && this.canBlock() && Minecraft.thePlayer.isBlocking() && this.qualifies(target)) {
         this.stopAutoBlockHypixel();
      }

      if(Helper.onServer("invaded")) {
         Minecraft.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
      } else {
         Minecraft.thePlayer.swingItem();
      }

      Minecraft.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(target, C02PacketUseEntity.Action.ATTACK));
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

   static enum AuraMode {
      Single,
      Switch,
      Tick;
   }
}
