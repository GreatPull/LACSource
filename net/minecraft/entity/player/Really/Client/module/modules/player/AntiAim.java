package net.minecraft.entity.player.Really.Client.module.modules.player;

import java.awt.Color;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.management.ModuleManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.module.modules.combat.Aura;
import net.minecraft.entity.player.Really.Client.module.modules.movement.Scaffold;
import net.minecraft.entity.player.Really.Client.utils.Timer;

public class AntiAim extends Module {
   float[] lastAngles;
   public static float rotationPitch;
   private boolean fake;
   private boolean fake1;
   public static byte var4 = -1;
   public static float pitchDown;
   public static float lastMeme;
   public static float reverse;
   public static float sutter;
   Minecraft var10000;
   Timer fakeJitter = new Timer();
   private Mode AAYAW = new Mode("AAYAW", "AAYAW", AntiAim.YAW.values(), AntiAim.YAW.FakeJitter);
   private Mode AAPITCH = new Mode("AAPITCH", "AAPITCH", AntiAim.PITCH.values(), AntiAim.PITCH.HalfDown);

   public AntiAim() {
      super("AntiAim", new String[]{"derp"}, ModuleType.Player);
      this.setColor((new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255))).getRGB());
      this.addValues(new Value[]{this.AAYAW, this.AAPITCH});
   }

   public void updateAngles(float yaw, float pitch) {
      Minecraft var10000x = mc;
      if(Minecraft.gameSettings.thirdPersonView != 0) {
         rotationPitch = pitch;
         Minecraft var10000 = mc;
         Minecraft.thePlayer.rotationYawHead = yaw;
         var10000 = mc;
         Minecraft.thePlayer.renderYawOffset = yaw;
      }

   }

   public void onDisable() {
      this.fake1 = true;
      this.lastAngles = null;
      rotationPitch = 0.0F;
      Minecraft var10000 = mc;
      Minecraft var10001 = mc;
      Minecraft.thePlayer.renderYawOffset = Minecraft.thePlayer.rotationYaw;
      super.onDisable();
   }

   public void onEnable() {
      this.fake1 = true;
      this.lastAngles = null;
      rotationPitch = 0.0F;
      super.onEnable();
   }

   @EventHandler
   public void onEvent(EventPreUpdate event) {
      Scaffold Scaffold = (Scaffold)ModuleManager.getModuleByName("Scaffold");
      if(Aura.curTarget == null && !Scaffold.isEnabled()) {
         if(this.lastAngles == null) {
            float[] var10001 = new float[2];
            Minecraft var10004 = mc;
            var10001[0] = Minecraft.thePlayer.rotationYaw;
            var10004 = mc;
            var10001[1] = Minecraft.thePlayer.rotationPitch;
            this.lastAngles = var10001;
         }

         this.fake = !this.fake;
         if(this.AAYAW.getValue() == AntiAim.YAW.Jitter) {
            var4 = 0;
         }

         if(this.AAYAW.getValue() == AntiAim.YAW.SpinFast) {
            var4 = 7;
         }

         if(this.AAYAW.getValue() == AntiAim.YAW.SpinSlow) {
            var4 = 8;
         }

         if(this.AAYAW.getValue() == AntiAim.YAW.Freestanding) {
            var4 = 6;
         }

         if(this.AAYAW.getValue() == AntiAim.YAW.Reverse) {
            var4 = 2;
         }

         if(this.AAYAW.getValue() == AntiAim.YAW.FakeJitter) {
            var4 = 4;
         }

         if(this.AAYAW.getValue() == AntiAim.YAW.Lisp) {
            var4 = 1;
         }

         if(this.AAYAW.getValue() == AntiAim.YAW.Sideways) {
            var4 = 3;
         }

         if(this.AAYAW.getValue() == AntiAim.YAW.FakeHead) {
            var4 = 5;
         }

         switch(var4) {
         case 0:
            pitchDown = 0.0F;
            event.setYaw(pitchDown = this.lastAngles[0] + 90.0F);
            this.lastAngles = new float[]{pitchDown, this.lastAngles[1]};
            this.updateAngles(pitchDown, this.lastAngles[1]);
            this.var10000 = mc;
            Minecraft.thePlayer.renderYawOffset = pitchDown;
            this.var10000 = mc;
            Minecraft.thePlayer.prevRenderYawOffset = pitchDown;
            break;
         case 1:
            lastMeme = this.lastAngles[0] + 150000.0F;
            this.lastAngles = new float[]{lastMeme, this.lastAngles[1]};
            event.setYaw(lastMeme);
            this.updateAngles(lastMeme, this.lastAngles[1]);
            break;
         case 2:
            this.var10000 = mc;
            reverse = Minecraft.thePlayer.rotationYaw + 180.0F;
            this.lastAngles = new float[]{reverse, this.lastAngles[1]};
            event.setYaw(reverse);
            this.updateAngles(reverse, this.lastAngles[1]);
            break;
         case 3:
            this.var10000 = mc;
            sutter = Minecraft.thePlayer.rotationYaw + -90.0F;
            this.lastAngles = new float[]{sutter, this.lastAngles[1]};
            event.setYaw(sutter);
            this.updateAngles(sutter, this.lastAngles[1]);
            break;
         case 4:
            if(Timer.delay(350)) {
               this.fake1 = !this.fake1;
               Timer.reset();
            }

            this.var10000 = mc;
            float yawRight = Minecraft.thePlayer.rotationYaw + (float)(this.fake1?90:-90);
            this.lastAngles = new float[]{yawRight, this.lastAngles[1]};
            event.setYaw(yawRight);
            this.updateAngles(yawRight, this.lastAngles[1]);
            break;
         case 5:
            if(Timer.delay(1100)) {
               this.fake1 = !this.fake1;
               Timer.reset();
            }

            this.var10000 = mc;
            float yawFakeHead = Minecraft.thePlayer.rotationYaw + (float)(this.fake1?90:-90);
            if(this.fake1) {
               this.fake1 = false;
            }

            this.lastAngles = new float[]{yawFakeHead, this.lastAngles[1]};
            event.setYaw(yawFakeHead);
            this.updateAngles(yawFakeHead, this.lastAngles[1]);
            break;
         case 6:
            this.var10000 = mc;
            float freestandHead = (float)((double)(Minecraft.thePlayer.rotationYaw + 5.0F) + Math.random() * 175.0D);
            this.lastAngles = new float[]{freestandHead, this.lastAngles[1]};
            event.setYaw(freestandHead);
            this.updateAngles(freestandHead, this.lastAngles[1]);
            break;
         case 7:
            float yawSpinFast = this.lastAngles[0] + 45.0F;
            this.lastAngles = new float[]{yawSpinFast, this.lastAngles[1]};
            event.setYaw(yawSpinFast);
            this.updateAngles(yawSpinFast, this.lastAngles[1]);
            break;
         case 8:
            float yawSpinSlow = this.lastAngles[0] + 10.0F;
            this.lastAngles = new float[]{yawSpinSlow, this.lastAngles[1]};
            event.setYaw(yawSpinSlow);
            this.updateAngles(yawSpinSlow, this.lastAngles[1]);
         }

         if(this.AAPITCH.getValue() == AntiAim.PITCH.Normal) {
            var4 = 2;
         }

         if(this.AAPITCH.getValue() == AntiAim.PITCH.Reverse) {
            var4 = 3;
         }

         if(this.AAPITCH.getValue() == AntiAim.PITCH.Stutter) {
            var4 = 4;
         }

         if(this.AAPITCH.getValue() == AntiAim.PITCH.Up) {
            var4 = 5;
         }

         if(this.AAPITCH.getValue() == AntiAim.PITCH.Meme) {
            var4 = 1;
         }

         if(this.AAPITCH.getValue() == AntiAim.PITCH.Zero) {
            var4 = 6;
         }

         if(this.AAPITCH.getValue() == AntiAim.PITCH.HalfDown) {
            var4 = 0;
         }

         switch(var4) {
         case 0:
            pitchDown = 90.0F;
            this.lastAngles = new float[]{this.lastAngles[0], pitchDown};
            event.setPitch(pitchDown);
            this.updateAngles(this.lastAngles[0], pitchDown);
            break;
         case 1:
            lastMeme = this.lastAngles[1];
            lastMeme += 10.0F;
            if(lastMeme > 90.0F) {
               lastMeme = -90.0F;
            }

            this.lastAngles = new float[]{this.lastAngles[0], lastMeme};
            event.setPitch(lastMeme);
            this.updateAngles(this.lastAngles[0], lastMeme);
            break;
         case 2:
            this.updateAngles(this.lastAngles[0], 0.0F);
            break;
         case 3:
            this.var10000 = mc;
            reverse = Minecraft.thePlayer.rotationPitch + 180.0F;
            this.lastAngles = new float[]{this.lastAngles[0], reverse};
            event.setPitch(reverse);
            this.updateAngles(this.lastAngles[0], reverse);
            break;
         case 4:
            if(this.fake) {
               sutter = 90.0F;
               event.setPitch(sutter);
            } else {
               sutter = -45.0F;
               event.setPitch(sutter);
            }

            this.lastAngles = new float[]{this.lastAngles[0], sutter};
            this.updateAngles(this.lastAngles[0], sutter);
            break;
         case 5:
            this.lastAngles = new float[]{this.lastAngles[0], -90.0F};
            event.setPitch(-90.0F);
            this.updateAngles(this.lastAngles[0], -90.0F);
            break;
         case 6:
            this.lastAngles = new float[]{this.lastAngles[0], -179.0F};
            event.setPitch(-180.0F);
            this.updateAngles(this.lastAngles[0], -179.0F);
         }
      }

   }

   static enum PITCH {
      Normal,
      HalfDown,
      Zero,
      Up,
      Stutter,
      Reverse,
      Meme;
   }

   static enum YAW {
      Reverse,
      Jitter,
      Lisp,
      SpinSlow,
      SpinFast,
      Sideways,
      FakeJitter,
      FakeHead,
      Freestanding;
   }
}
