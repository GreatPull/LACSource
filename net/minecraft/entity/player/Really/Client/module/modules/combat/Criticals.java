package net.minecraft.entity.player.Really.Client.module.modules.combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPacketRecieve;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPacketSend;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Numbers;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.management.ModuleManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.module.modules.combat.Aura;
import net.minecraft.entity.player.Really.Client.module.modules.movement.FleetHop;
import net.minecraft.entity.player.Really.Client.module.modules.movement.Hopper;
import net.minecraft.entity.player.Really.Client.module.modules.movement.Speed;
import net.minecraft.entity.player.Really.Client.utils.Helper;
import net.minecraft.entity.player.Really.Client.utils.MoveUtils;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;
import net.minecraft.entity.player.Really.Client.utils.math.MathUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import org.apache.commons.lang3.RandomUtils;

public class Criticals extends Module {
   public Mode mode = new Mode("Mode", "mode", Criticals.CritMode.values(), Criticals.CritMode.HypixelPacket);
   private boolean N;
   private boolean M;
   public static boolean Crit;
   private TimerUtil timer = new TimerUtil();
   private Numbers Delay = new Numbers("Delay", "Delay", Double.valueOf(200.0D), Double.valueOf(0.0D), Double.valueOf(1000.0D), Double.valueOf(1.0D));
   public static Option StopSpeed = new Option("StopSpeed", "StopSpeed", Boolean.valueOf(true));
   public static Option Always = new Option("Always", "Always", Boolean.valueOf(true));
   private static List invalid = new ArrayList();
   private static List removed = new ArrayList();
   private static int[] var1;
   private EventPacketRecieve event;
   int stage;
   int count;
   double y;

   static {
      lIlIlllIIIlIllII();
   }

   public Criticals() {
      super("Criticals", new String[]{"crits", "crit"}, ModuleType.Combat);
      this.setColor((new Color(235, 194, 138)).getRGB());
      this.addValues(new Value[]{this.mode, StopSpeed, this.Delay, Always});
   }

   public void onEnable() {
      this.stage = 0;
      this.count = 0;
      invalid.clear();
   }

   public void onDisable() {
      invalid.clear();
   }

   @EventHandler
   private void onUpdate(EventPreUpdate e10) {
      this.setSuffix(this.mode.getValue());
      if(this.mode.getValue() == Criticals.CritMode.NoGround) {
         label24: {
            Minecraft var10000 = mc;
            if(Minecraft.thePlayer.onGround) {
               Minecraft.getMinecraft();
               if(!Minecraft.gameSettings.keyBindAttack.isKeyDown()) {
                  e10.setOnground(false);
                  break label24;
               }
            }

            e10.setOnground(true);
         }
      }

      if(this.mode.getValue() == Criticals.CritMode.Always) {
         e10.setOnground(false);
      }

      if(this.mode.getValue() == Criticals.CritMode.Hover) {
         Minecraft var4 = mc;
         Minecraft.thePlayer.lastReportedPosY = 0.0D;
         double ypos = Minecraft.thePlayer.posY;
         if(MoveUtils.isOnGround(0.001D)) {
            e10.setOnground(false);
            if(this.stage == 0) {
               this.y = ypos + 1.0E-8D;
               e10.setOnground(true);
            } else if(this.stage == 1) {
               this.y -= 5.0E-15D;
            } else {
               this.y -= 4.0E-15D;
            }

            if(this.y <= Minecraft.thePlayer.posY) {
               this.stage = 0;
               this.y = Minecraft.thePlayer.posY;
               e10.setOnground(true);
            }

            e10.setY(this.y);
            ++this.stage;
         } else {
            this.stage = 0;
         }
      }

   }

   private boolean canCrit() {
      return Minecraft.thePlayer.onGround && !Minecraft.thePlayer.isInWater() || ((Boolean)Always.getValue()).booleanValue();
   }

   @EventHandler
   private void onPacket(EventPacketSend e10) {
      if(this.mode.getValue() == Criticals.CritMode.Hypixel) {
         Packet a = EventPacketSend.getPacket();
         if(Aura.curTarget == null) {
            return;
         }

         if(!(a instanceof C03PacketPlayer)) {
            return;
         }

         Minecraft var10000 = mc;
         if(Minecraft.thePlayer.onGround) {
            this.M = false;
            if(!this.N) {
               this.N = true;
               ((C03PacketPlayer)a).onGround = true;
               return;
            }

            ((C03PacketPlayer)a).onGround = false;
         } else {
            if(!this.M) {
               this.M = true;
            }

            var10000 = mc;
            if((double)Minecraft.thePlayer.fallDistance > 0.5D) {
               ((C03PacketPlayer)a).onGround = true;
               var10000 = mc;
               Minecraft.thePlayer.fallDistance = 0.0F;
            } else {
               ((C03PacketPlayer)a).onGround = false;
               this.N = false;
            }
         }
      }

      if(this.mode.getValue() == Criticals.CritMode.HvH && EventPacketSend.getPacket() instanceof C03PacketPlayer) {
         C03PacketPlayer packet = (C03PacketPlayer)EventPacketSend.getPacket();
         packet.onGround = false;
      }

      if(EventPacketSend.getPacket() instanceof C02PacketUseEntity && this.canCrit() && this.mode.getValue() == Criticals.CritMode.Minijumps) {
         Minecraft.thePlayer.motionY = 0.2D;
      }

   }

   void packetCrit() {
      if(this.timer.hasReached((double)(Helper.onServer("hypixel")?500:10)) && this.mode.getValue() == Criticals.CritMode.HypixelPacket && this.canCrit()) {
         double[] offsets = new double[]{0.0625D, 0.0D, 1.0E-4D, 0.0D};

         for(int i2 = 0; i2 < offsets.length; ++i2) {
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + offsets[i2], Minecraft.thePlayer.posZ, false));
         }

         this.timer.reset();
      }

   }

   void offsetCrit() {
      if(this.canCrit()) {
         Minecraft var10000 = mc;
         if(!Minecraft.getCurrentServerData().serverIP.contains("hypixel")) {
            double[] offsets = new double[]{0.0624D, 0.0D, 1.0E-4D, 0.0D};

            for(int i2 = 0; i2 < offsets.length; ++i2) {
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + offsets[i2], Minecraft.thePlayer.posZ, false));
            }
         }
      }

   }

   void RemixCrits() {
      if(this.timer.hasReached(500.0D) && isOnGround(0.072D) && this.canCrit() && this.mode.getValue() == Criticals.CritMode.Edit) {
         double[] var5;
         double[] offsets = var5 = new double[]{0.051D * MathUtil.randomDouble(1.08D, 1.1D), 0.0D, 0.0125D * MathUtil.randomDouble(1.01D, 1.07D), 0.0D};

         for(double v : offsets) {
            System.out.print("C08");
            NetHandlerPlayClient sendQueue = Minecraft.thePlayer.sendQueue;
            Minecraft mc2 = mc;
            double posX = Minecraft.thePlayer.posX;
            Minecraft mc3 = mc;
            double posY = Minecraft.thePlayer.posY + v;
            Minecraft mc4 = mc;
            sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(posX, posY, Minecraft.thePlayer.posZ, false));
         }

         this.timer.reset();
      }

   }

   public static boolean isOnGround(double height) {
      Minecraft.getMinecraft();
      WorldClient theWorld = Minecraft.theWorld;
      Minecraft.getMinecraft();
      EntityPlayerSP thePlayer = Minecraft.thePlayer;
      Minecraft.getMinecraft();
      return !theWorld.getCollidingBoundingBoxes(thePlayer, Minecraft.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty();
   }

   void AACCrits() {
      if(this.canCrit() && this.mode.getValue() == Criticals.CritMode.AAC) {
         Minecraft var10000 = mc;
         Minecraft var10003 = mc;
         Minecraft var10004 = mc;
         double var3 = Minecraft.thePlayer.posY + 1.0E-11D;
         Minecraft var10005 = mc;
         Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, var3, Minecraft.thePlayer.posZ, true));
         var10000 = mc;
         var10003 = mc;
         Minecraft var4 = mc;
         var10005 = mc;
         Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY, Minecraft.thePlayer.posZ, false));
      }

   }

   private static int GetRandomNumber(int n, int n2) {
      return (int)(Math.random() * (double)(n - n2)) + n2;
   }

   private static int randomNumber(int max, int min) {
      return (int)(Math.random() * (double)(max - min)) + min;
   }

   void newCrit() {
      if(this.mode.getValue() == Criticals.CritMode.NewPacket && this.canCrit()) {
         double offset1 = (double)(randomNumber(-9999, 9999) / 10000000);
         double offset2 = (double)(randomNumber(-9999, 9999) / 1000000000);

         for(double offset : new double[]{0.0624218713251234D + offset1, 0.0D, 1.0834773E-5D + offset2, 0.0D}) {
            Minecraft var10000 = mc;
            Minecraft var10003 = mc;
            Minecraft var10004 = mc;
            double var10 = Minecraft.thePlayer.posY + offset;
            Minecraft var10005 = mc;
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, var10, Minecraft.thePlayer.posZ, false));
         }
      }

   }

   void packet() {
      if(this.mode.getValue() == Criticals.CritMode.RandomFloat && this.canCrit()) {
         Minecraft.getMinecraft();
         EntityPlayerSP mc = Minecraft.thePlayer;
         double x = mc.posX;
         double y = mc.posY;
         double z = mc.posZ;
         double[] array = new double[]{0.05954136143876984D, 0.05943483573247983D, 0.01354835722479834D};

         for(int i = 0; i < array.length; ++i) {
            double rnd = this.getRetardedRandomVar((int)MathUtil.randomDouble(1.0D, 8.0D));
            double arrayTemp = array[i] - rnd;
            mc.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(x, y + arrayTemp, z, false));
         }

         this.timer.reset();
      }

   }

   double getRetardedRandomVar(int sss) {
      Minecraft.getMinecraft();
      EntityPlayerSP playerSP = Minecraft.thePlayer;
      double result = 0.0D;
      double[] randomUsedPoops = new double[]{Math.pow(playerSP.posY, -2.0D), Math.pow((double)(playerSP.rotationYawHead * playerSP.rotationPitch), -2.0D), Math.pow((double)playerSP.ticksExisted, -2.0D), Math.pow((double)playerSP.swingProgressInt, -3.0D)};

      for(int i = 0; sss > i; ++i) {
         result += Math.abs(randomUsedPoops[i % 4]);
      }

      while(result >= 0.005D) {
         result *= 0.6D * (Math.random() + 0.5D);
      }

      return sss == 0?Math.pow((double)(playerSP.rotationYawHead * playerSP.rotationPitch), -2.0D):result;
   }

   boolean autoCrit() {
      if(this.timer.hasReached(((Double)this.Delay.getValue()).doubleValue())) {
         Crit = true;
         this.packet();
         this.UsPacket();
         this.hypixelCrit();
         this.RemixCrits();
         this.packetCrit();
         this.newCrit();
         this.AACCrits();
         this.ExCrit();
         this.PacketOldCrit();
         this.newFloatCrit();
         this.HypixelTickCrit();
         this.Packet2Crit();
         this.timer.reset();
         Crit = false;
         return true;
      } else {
         return false;
      }
   }

   private void UsPacket() {
      if(this.canCrit() && this.mode.getValue() == Criticals.CritMode.PosY && this.canCrit()) {
         Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.01625D, Minecraft.thePlayer.posZ, false));
      }

   }

   public void hypixelCrit() {
      if(this.mode.getValue() == Criticals.CritMode.HypixelGod && this.canCrit()) {
         Speed speed = (Speed)ModuleManager.getModuleByName("Speed");
         Hopper Hopper = (Hopper)ModuleManager.getModuleByName("Hopper");
         FleetHop BHopper = (FleetHop)ModuleManager.getModuleByName("BHopper");
         if((speed.isEnabled() || Hopper.isEnabled() || BHopper.isEnabled()) && ((C02PacketUseEntity)this.event.getPacket()).getAction() == C02PacketUseEntity.Action.ATTACK) {
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.1625D, Minecraft.thePlayer.posZ, false));
         }

         double[] var9;
         for(double offset : var9 = new double[]{0.06142999976873398D, 0.0D, 0.012511000037193298D, 0.0D}) {
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + offset, Minecraft.thePlayer.posZ, false));
         }
      }

   }

   public void newFloatCrit() {
      if(this.mode.getValue() == Criticals.CritMode.NewFloat && this.canCrit()) {
         Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.02D, Minecraft.thePlayer.posZ, false));
         Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.01D, Minecraft.thePlayer.posZ, false));
         Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.01D, Minecraft.thePlayer.posZ);
      }

   }

   public void HypixelTickCrit() {
      if(this.mode.getValue() == Criticals.CritMode.HypixelTick && this.canCrit()) {
         Minecraft var10000 = mc;
         if(Minecraft.thePlayer.ticksExisted % 9 == 0) {
            var10000 = mc;
            double X = Minecraft.thePlayer.posX;
            var10000 = mc;
            double Y = Minecraft.thePlayer.posY;
            var10000 = mc;
            double Z = Minecraft.thePlayer.posZ;
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(X, Y, Z, false));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(X, Y + RandomUtils.nextDouble(0.01D, 0.06D), Z, false));
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(X, Y, Z, false));
         }
      }

   }

   public void Packet2Crit() {
      if(this.mode.getValue() == Criticals.CritMode.Packet2 && this.canCrit()) {
         Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY + 0.01D, Minecraft.thePlayer.posZ);
         Minecraft.thePlayer.setPosition(Minecraft.thePlayer.posX, Minecraft.thePlayer.posY - 0.001D, Minecraft.thePlayer.posZ);
         Minecraft.thePlayer.fallDistance = 0.001F;
      }

   }

   private static void lIlIlllIIIlIllII() {
      var1 = new int[21];
      var1[0] = 0;
      var1[1] = " ".length();
      var1[2] = "  ".length();
      var1[3] = "   ".length();
      var1[4] = 4;
      var1[5] = 5;
      var1[6] = 6;
      var1[7] = 7;
      var1[8] = 8;
      var1[9] = -" ".length();
      var1[10] = 9;
      var1[11] = 10;
      var1[12] = 11;
      var1[13] = 12;
      var1[14] = 350;
      var1[15] = 300;
      var1[16] = 13;
      var1[17] = 14;
      var1[18] = 15;
      var1[19] = 16;
      var1[20] = 17;
   }

   private static boolean lIlIlllIIIllIIIl(int llllllllllllIlIIIIlIIllIllllllll, int llllllllllllIlIIIIlIIllIlllllllI) {
      return llllllllllllIlIIIIlIIllIllllllll >= llllllllllllIlIIIIlIIllIlllllllI;
   }

   public void PacketOldCrit() {
      if(this.mode.getValue() == Criticals.CritMode.PacketOld && this.canCrit()) {
         Minecraft var10000 = mc;
         double var11 = Minecraft.thePlayer.posX;
         var10000 = mc;
         double var22 = Minecraft.thePlayer.posY;
         var10000 = mc;
         double var33 = Minecraft.thePlayer.posZ;
         double[] var13 = new double[var1[3]];
         var13[var1[0]] = 0.05954835722479834D;
         var13[var1[1]] = 0.05943483573247983D;
         var13[var1[2]] = 0.01354835722479834D;
         double[] llllllllllllIlIIIIlIIllllIIIlllI = var13;
         int llllllllllllIlIIIIlIIllllIIIllII = var13.length;
         int llllllllllllIlIIIIlIIllllIIIlIlI = var1[0];
         "".length();

         while(!lIlIlllIIIllIIIl(llllllllllllIlIIIIlIIllllIIIlIlI, llllllllllllIlIIIIlIIllllIIIllII)) {
            double array = llllllllllllIlIIIIlIIllllIIIlllI[llllllllllllIlIIIIlIIllllIIIlIlI];
            var10000 = mc;
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(var11, var22 + array, var33, false));
            ++llllllllllllIlIIIIlIIllllIIIlIlI;
         }
      }

   }

   void ExCrit() {
      if(this.mode.getValue() == Criticals.CritMode.Packet && this.canCrit()) {
         double offset1 = (double)(randomNumber(-9999, 9999) / 10000000);
         double offset2 = (double)(randomNumber(-9999, 9999) / 1000000000);

         for(double offset : new double[]{0.0624218713251234D + offset1, 0.0D, 1.0834773E-5D + offset2, 0.0D}) {
            Minecraft var10000 = mc;
            Minecraft var10003 = mc;
            Minecraft var10004 = mc;
            double var10 = Minecraft.thePlayer.posY + offset;
            Minecraft var10005 = mc;
            if(Aura.curTarget.hurtResistantTime <= Double.valueOf(14.0D).intValue()) {
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Minecraft.thePlayer.posX, var10, Minecraft.thePlayer.posZ, false));
            }
         }
      }

   }

   static enum CritMode {
      HypixelPacket,
      PacketOld,
      Packet,
      NewPacket,
      Minijumps,
      NewFloat,
      Edit,
      AAC,
      NoGround,
      HvH,
      Always,
      Hover,
      HypixelGod,
      Hypixel,
      HypixelTick,
      PosY,
      RandomFloat,
      Packet2;
   }
}
