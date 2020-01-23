package net.minecraft.entity.player.Really.Client.module.modules.combat;

import com.ibm.icu.text.NumberFormat;
import java.awt.Color;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderManager;
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
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender2D;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender3D;
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
import net.minecraft.entity.player.Really.Client.module.modules.combat.AutoPot;
import net.minecraft.entity.player.Really.Client.module.modules.combat.Criticals;
import net.minecraft.entity.player.Really.Client.module.modules.combat.CustomAntiBot;
import net.minecraft.entity.player.Really.Client.module.modules.movement.Scaffold;
import net.minecraft.entity.player.Really.Client.module.modules.player.Teams;
import net.minecraft.entity.player.Really.Client.module.modules.render.setColor;
import net.minecraft.entity.player.Really.Client.utils.Helper;
import net.minecraft.entity.player.Really.Client.utils.RenderingUtil;
import net.minecraft.entity.player.Really.Client.utils.RotationUtils;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;
import net.minecraft.entity.player.Really.Client.utils.math.MathUtil;
import net.minecraft.entity.player.Really.Client.utils.math.RotationUtil;
import net.minecraft.entity.player.Really.Client.utils.render.Colors;
import net.minecraft.entity.player.Really.Client.utils.render.RenderUtil;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class Aura extends Module {
   private static String curBot = null;
   private TimerUtil timer = new TimerUtil();
   private TimerUtil SwitchTimer = new TimerUtil();
   public static EntityLivingBase curTarget;
   private Vector2f lastAngles = new Vector2f(0.0F, 0.0F);
   private boolean doBlock = false;
   private boolean unBlock = false;
   private List targets = new ArrayList(0);
   private int index;
   public static float sYaw;
   public static float sPitch;
   public static float aacB;
   private int xd;
   private int tpdelay;
   public static Numbers Existed = new Numbers("Existed", "Existed", Double.valueOf(30.0D), Double.valueOf(0.0D), Double.valueOf(100.0D), Double.valueOf(1.0D));
   public static Numbers FOV = new Numbers("FOV", "FOV", Double.valueOf(360.0D), Double.valueOf(15.0D), Double.valueOf(360.0D), Double.valueOf(5.0D));
   public static Numbers ThroughWallReach = new Numbers("ThroughWallReach", "ThroughWallReach", Double.valueOf(3.0D), Double.valueOf(0.0D), Double.valueOf(8.0D), Double.valueOf(0.1D));
   private Numbers MaxCPS = new Numbers("MaxCPS", "MaxCPS", Double.valueOf(17.0D), Double.valueOf(1.0D), Double.valueOf(20.0D), Double.valueOf(1.0D));
   private Numbers MinCPS = new Numbers("MinCPS", "MinCPS", Double.valueOf(6.0D), Double.valueOf(1.0D), Double.valueOf(20.0D), Double.valueOf(1.0D));
   private Numbers crack = new Numbers("CrackSize", "CrackSize", Double.valueOf(1.0D), Double.valueOf(0.0D), Double.valueOf(5.0D), Double.valueOf(1.0D));
   private Numbers AimSpeed = new Numbers("AimSpeed", "AimSpeed", Double.valueOf(120.0D), Double.valueOf(0.0D), Double.valueOf(180.0D), Double.valueOf(1.0D));
   private Numbers reach = new Numbers("Reach", "reach", Double.valueOf(4.5D), Double.valueOf(1.0D), Double.valueOf(8.0D), Double.valueOf(0.1D));
   private Numbers SwitchDelay = new Numbers("SwitchDelay", "SwitchDelay", Double.valueOf(200.0D), Double.valueOf(1.0D), Double.valueOf(2000.0D), Double.valueOf(1.0D));
   public static Numbers BlockReach = new Numbers("BlockReach", "BlockReach", Double.valueOf(1.0D), Double.valueOf(0.1D), Double.valueOf(8.0D), Double.valueOf(0.1D));
   private Numbers HurtTime = new Numbers("HurtTime", "HurtTime", Double.valueOf(10.0D), Double.valueOf(1.0D), Double.valueOf(10.0D), Double.valueOf(1.0D));
   private Option blocking = new Option("Autoblock", "autoblock", Boolean.valueOf(true));
   private Option raycast = new Option("Raycast", "Raycast", Boolean.valueOf(true));
   private Mode espmode = new Mode("ESP", "ESP", Aura.EMode.values(), Aura.EMode.Box);
   private Option players = new Option("Players", "players", Boolean.valueOf(true));
   private Option animals = new Option("Animals", "animals", Boolean.valueOf(false));
   private Option DuelMode = new Option("DuelMode", "DuelMode", Boolean.valueOf(false));
   private Option mobs = new Option("Mobs", "mobs", Boolean.valueOf(false));
   private Option invis = new Option("Invisibles", "invisibles", Boolean.valueOf(false));
   private Option ShowTargetHp = new Option("ShowTargetHp", "ShowTargetHp", Boolean.valueOf(true));
   private Mode mode = new Mode("Mode", "mode", Aura.AuraMode.values(), Aura.AuraMode.Switch);
   private Mode Prmode = new Mode("Priority", "mode", Aura.priority.values(), Aura.priority.Slowly);
   private Mode Aim = new Mode("Aim", "Aim", Aura.AimMode.values(), Aura.AimMode.Normal);
   private Mode ABmode = new Mode("AutoBlockMode", "AutoBlockMode", Aura.AutoBlockMode.values(), Aura.AutoBlockMode.Normal);
   private boolean isBlocking;
   private Comparator angleComparator = Comparator.comparingDouble((e2) -> {
      return (double)RotationUtil.getRotations((Entity) e2)[0];
   });
   private boolean crit;
   private RotationUtil CombatUtil;
   private float curYaw;
   private float curPitch;
   private EntityLivingBase entity;
   private EventPacketSend event;
   private boolean isAttacking;

   public Aura() {
      super("Aura", new String[]{"ka", "aura", "killa"}, ModuleType.Combat);
      this.setColor((new Color(226, 54, 30)).getRGB());
      this.angleComparator = Comparator.comparingDouble((e2) -> {
         return (double)RotationUtil.getRotations((Entity) e2)[0];
      });
      this.addValues(new Value[]{this.MaxCPS, this.MinCPS, FOV, this.reach, ThroughWallReach, BlockReach, this.AimSpeed, this.crack, this.blocking, this.players, this.animals, this.mobs, this.raycast, this.invis, this.Prmode, this.mode, this.espmode, this.Aim, this.ShowTargetHp, this.DuelMode, this.SwitchDelay, this.HurtTime, Existed});
   }

   @EventHandler
   public void onRender(EventRender2D event) {
      if(((Boolean)this.ShowTargetHp.getValue()).booleanValue()) {
         ScaledResolution sr = new ScaledResolution(mc);
         Minecraft var10000 = mc;
         FontRenderer font3 = Minecraft.fontRendererObj;
         if(curTarget != null) {
            Client instance = Client.instance;
            Client.getModuleManager();
            Client instance2 = Client.instance;
            Client.getModuleManager();
            String name2 = curTarget.getName();
            font3.drawStringWithShadow(name2, (float)(sr.getScaledWidth() / 2 - font3.getStringWidth(name2) / 2), (float)(sr.getScaledHeight() / 2 - 80), -1);
            Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("textures/gui/icons.png"));

            for(int i = 0; (float)i < curTarget.getMaxHealth() / 2.0F; ++i) {
               Minecraft.getMinecraft();
               Minecraft.ingameGUI.drawTexturedModalRect((float)(sr.getScaledWidth() / 2) - curTarget.getMaxHealth() / 2.0F * 10.0F / 2.0F + (float)(i * 10), (float)(sr.getScaledHeight() / 2 - 65), 16, 0, 9, 9);
            }

            for(int i = 0; (float)i < curTarget.getHealth() / 2.0F; ++i) {
               Minecraft.getMinecraft();
               Minecraft.ingameGUI.drawTexturedModalRect((float)(sr.getScaledWidth() / 2) - curTarget.getMaxHealth() / 2.0F * 10.0F / 2.0F + (float)(i * 10), (float)(sr.getScaledHeight() / 2 - 65), 52, 0, 9, 9);
            }
         }
      }

   }

   public static int[] getFractionIndicies(float[] fractions, float progress) {
      int[] range = new int[2];

      int startPoint;
      for(startPoint = 0; startPoint < fractions.length && fractions[startPoint] <= progress; ++startPoint) {
         ;
      }

      if(startPoint >= fractions.length) {
         startPoint = fractions.length - 1;
      }

      range[0] = startPoint - 1;
      range[1] = startPoint;
      return range;
   }

   public static Color blendColors(float[] fractions, Color[] colors, float progress) {
      Color color = null;
      if(fractions == null) {
         throw new IllegalArgumentException("Fractions can\'t be null");
      } else if(colors == null) {
         throw new IllegalArgumentException("Colours can\'t be null");
      } else if(fractions.length == colors.length) {
         int[] indicies = getFractionIndicies(fractions, progress);
         float[] range = new float[]{fractions[indicies[0]], fractions[indicies[1]]};
         Color[] colorRange = new Color[]{colors[indicies[0]], colors[indicies[1]]};
         float max = range[1] - range[0];
         float value = progress - range[0];
         float weight = value / max;
         color = blend(colorRange[0], colorRange[1], (double)(1.0F - weight));
         return color;
      } else {
         throw new IllegalArgumentException("Fractions and colours must have equal number of elements");
      }
   }

   private void startBlocking() {
      this.isBlocking = true;
      Minecraft var10000 = mc;
      KeyBinding.setKeyBindState(Minecraft.gameSettings.keyBindUseItem.getKeyCode(), true);
      if(Minecraft.playerController.sendUseItem(Minecraft.thePlayer, Minecraft.theWorld, Minecraft.thePlayer.inventory.getCurrentItem())) {
         mc.getItemRenderer().resetEquippedProgress2();
      }

   }

   private void stopBlocking() {
      this.isBlocking = false;
      Minecraft var10000 = mc;
      KeyBinding.setKeyBindState(Minecraft.gameSettings.keyBindUseItem.getKeyCode(), false);
      Minecraft.playerController.onStoppedUsingItem(Minecraft.thePlayer);
   }

   public static Color blend(Color color1, Color color2, double ratio) {
      float r = (float)ratio;
      float ir = 1.0F - r;
      float[] rgb1 = new float[3];
      float[] rgb2 = new float[3];
      color1.getColorComponents(rgb1);
      color2.getColorComponents(rgb2);
      float red = rgb1[0] * r + rgb2[0] * ir;
      float green = rgb1[1] * r + rgb2[1] * ir;
      float blue = rgb1[2] * r + rgb2[2] * ir;
      if(red < 0.0F) {
         red = 0.0F;
      } else if(red > 250.0F) {
         red = 255.0F;
      }

      if(green < 0.0F) {
         green = 0.0F;
      } else if(green > 250.0F) {
         green = 255.0F;
      }

      if(blue < 0.0F) {
         blue = 0.0F;
      } else if(blue > 250.0F) {
         blue = 250.0F;
      }

      Color color3 = null;

      try {
         color3 = new Color(red, green, blue);
      } catch (IllegalArgumentException var14) {
         NumberFormat nf = NumberFormat.getNumberInstance();
         System.out.println(String.valueOf(nf.format((double)red)) + "; " + nf.format((double)green) + "; " + nf.format((double)blue));
         var14.printStackTrace();
      }

      return color3;
   }

   private double getIncremental(double val, double inc) {
      double one = 1.0D / inc;
      return (double)Math.round(val * one) / one;
   }

   @EventHandler
   private void render(EventRender3D e) {
      if(curTarget != null && this.espmode.getValue() != Aura.EMode.None) {
         Color color = new Color(((Double)setColor.r.getValue()).floatValue() / 255.0F, ((Double)setColor.g.getValue()).floatValue() / 255.0F, ((Double)setColor.b.getValue()).floatValue() / 255.0F, ((Double)setColor.a.getValue()).floatValue() / 255.0F);
         if(curTarget.hurtResistantTime > 0) {
            color = new Color(Colors.RED.c);
         }

         if(curTarget != null) {
            if(this.espmode.getValue() == Aura.EMode.Box) {
               mc.getRenderManager();
               double x = curTarget.lastTickPosX + (curTarget.posX - curTarget.lastTickPosX) * (double)mc.timer.renderPartialTicks - RenderManager.renderPosX;
               mc.getRenderManager();
               double y = curTarget.lastTickPosY + (curTarget.posY - curTarget.lastTickPosY) * (double)mc.timer.renderPartialTicks - RenderManager.renderPosY;
               mc.getRenderManager();
               double z = curTarget.lastTickPosZ + (curTarget.posZ - curTarget.lastTickPosZ) * (double)mc.timer.renderPartialTicks - RenderManager.renderPosZ;
               double d = curTarget.isSneaking()?0.25D:0.0D;
               double mid = 0.275D;
               GL11.glPushMatrix();
               GL11.glEnable(3042);
               GL11.glBlendFunc(770, 771);
               double rotAdd = -0.25D * (double)(Math.abs(curTarget.rotationPitch) / 90.0F);
               GL11.glTranslated(0.0D, rotAdd, 0.0D);
               double x1 = 0;
               double y1 = 0;
               double z1 = 0;
               GL11.glTranslated((x1 = x1 - 0.275D) + 0.275D, (y1 = y1 + ((double)curTarget.getEyeHeight() - 0.225D - d)) + 0.275D, (z1 = z1 - 0.275D) + 0.275D);
               GL11.glRotated((double)(-curTarget.rotationYaw % 360.0F), 0.0D, 1.0D, 0.0D);
               GL11.glTranslated(-(x1 + 0.275D), -(y1 + 0.275D), -(z1 + 0.275D));
               GL11.glTranslated(x1 + 0.275D, y1 + 0.275D, z1 + 0.275D);
               GL11.glRotated((double)curTarget.rotationPitch, 1.0D, 0.0D, 0.0D);
               GL11.glTranslated(-(x1 + 0.275D), -(y1 + 0.275D), -(z1 + 0.275D));
               GL11.glDisable(3553);
               GL11.glEnable(2848);
               GL11.glDisable(2929);
               GL11.glDepthMask(false);
               GL11.glColor4f((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, 1.0F);
               GL11.glLineWidth(1.0F);
               RenderUtil.drawOutlinedBoundingBox(new AxisAlignedBB(x1 - 0.0025D, y1 - 0.0025D, z1 - 0.0025D, x1 + 0.55D + 0.0025D, y1 + 0.55D + 0.0025D, z1 + 0.55D + 0.0025D));
               GL11.glColor4f((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, 0.5F);
               RenderUtil.drawBoundingBox(new AxisAlignedBB(x1 - 0.0025D, y1 - 0.0025D, z1 - 0.0025D, x1 + 0.55D + 0.0025D, y1 + 0.55D + 0.0025D, z1 + 0.55D + 0.0025D));
               GL11.glDisable(2848);
               GL11.glEnable(3553);
               GL11.glEnable(2929);
               GL11.glDepthMask(true);
               GL11.glDisable(3042);
               GL11.glPopMatrix();
            } else if(this.espmode.getValue() == Aura.EMode.Liquidbounce) {
               mc.getRenderManager();
               double x = curTarget.lastTickPosX + (curTarget.posX - curTarget.lastTickPosX) * (double)mc.timer.renderPartialTicks - RenderManager.renderPosX;
               mc.getRenderManager();
               double y = curTarget.lastTickPosY + (curTarget.posY - curTarget.lastTickPosY) * (double)mc.timer.renderPartialTicks - RenderManager.renderPosY;
               mc.getRenderManager();
               double z = curTarget.lastTickPosZ + (curTarget.posZ - curTarget.lastTickPosZ) * (double)mc.timer.renderPartialTicks - RenderManager.renderPosZ;
               double d = curTarget.isSneaking()?0.25D:0.0D;
               double mid = 0.5D;
               GL11.glPushMatrix();
               GL11.glEnable(3042);
               GL11.glBlendFunc(770, 771);
               double rotAdd = -0.25D * (double)(Math.abs(curTarget.rotationPitch) / 90.0F);
               double var24;
               double var28;
               double var32;
               GL11.glTranslated((var24 = x - 0.5D) + 0.5D, (var28 = y + ((double)curTarget.getEyeHeight() + 0.35D - d)) + 0.5D, (var32 = z - 0.5D) + 0.5D);
               GL11.glRotated((double)(-curTarget.rotationYaw % 360.0F), 0.0D, 1.0D, 0.0D);
               GL11.glTranslated(-(var24 + 0.5D), -(var28 + 0.5D), -(var32 + 0.5D));
               GL11.glDisable(3553);
               GL11.glEnable(2848);
               GL11.glDisable(2929);
               GL11.glDepthMask(false);
               GL11.glColor4f((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, 0.5F);
               RenderUtil.drawBoundingBox(new AxisAlignedBB(var24, var28, var32, var24 + 1.0D, var28 + 0.05D, var32 + 1.0D));
               GL11.glDisable(2848);
               GL11.glEnable(3553);
               GL11.glEnable(2929);
               GL11.glDepthMask(true);
               GL11.glDisable(3042);
               GL11.glPopMatrix();
            } else if(this.espmode.getValue() == Aura.EMode.New) {
               mc.getRenderManager();
               double x1 = curTarget.lastTickPosX + (curTarget.posX - curTarget.lastTickPosX) * (double)mc.timer.renderPartialTicks - RenderManager.renderPosX;
               mc.getRenderManager();
               double y1 = curTarget.lastTickPosY + (curTarget.posY - curTarget.lastTickPosY) * (double)mc.timer.renderPartialTicks - RenderManager.renderPosY;
               mc.getRenderManager();
               double z1 = curTarget.lastTickPosZ + (curTarget.posZ - curTarget.lastTickPosZ) * (double)mc.timer.renderPartialTicks - RenderManager.renderPosZ;
               if(curTarget instanceof EntityPlayer) {
                  double width1 = curTarget.getEntityBoundingBox().maxX - curTarget.getEntityBoundingBox().minX - 0.35D;
                  double height1 = curTarget.getEntityBoundingBox().maxY - curTarget.getEntityBoundingBox().minY;
                  float red1 = curTarget.hurtTime > 0?1.0F:0.0F;
                  float green1 = curTarget.hurtTime > 0?0.2F:1.0F;
                  float blue1 = curTarget.hurtTime > 0?0.0F:0.2F;
                  float alpha1 = 0.2F;
                  float lineRed1 = curTarget.hurtTime > 0?1.0F:0.0F;
                  float lineGreen1 = curTarget.hurtTime > 0?0.2F:1.0F;
                  float lineBlue1 = curTarget.hurtTime > 0?0.0F:0.2F;
                  float lineAlpha1 = 0.2F;
                  float lineWdith1 = 2.0F;
                  RenderUtil.drawEntityESP(x1, y1, z1, width1, height1, red1, green1, blue1, 0.2F, lineRed1, lineGreen1, lineBlue1, 0.2F, 2.0F);
               } else {
                  double width1 = curTarget.getEntityBoundingBox().maxX - curTarget.getEntityBoundingBox().minX - 0.35D;
                  double height1 = curTarget.getEntityBoundingBox().maxY - curTarget.getEntityBoundingBox().minY;
                  float red1 = curTarget.hurtTime > 0?1.0F:0.0F;
                  float green1 = curTarget.hurtTime > 0?0.2F:1.0F;
                  float blue1 = curTarget.hurtTime > 0?0.0F:0.2F;
                  float alpha1 = 0.2F;
                  float lineRed1 = curTarget.hurtTime > 0?1.0F:0.0F;
                  float lineGreen1 = curTarget.hurtTime > 0?0.2F:1.0F;
                  float lineBlue1 = curTarget.hurtTime > 0?0.0F:0.2F;
                  float lineAlpha1 = 0.2F;
                  float lineWdith1 = 2.0F;
                  RenderUtil.drawEntityESP(x1, y1, z1, width1, height1, red1, green1, blue1, 0.2F, lineRed1, lineGreen1, lineBlue1, 0.2F, 2.0F);
               }
            }
         }

      }
   }

   public void drawESP(EntityLivingBase entity, int color) {
      double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)mc.timer.renderPartialTicks;
      double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)mc.timer.renderPartialTicks + (double)entity.getEyeHeight() * 1.2D;
      double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)mc.timer.renderPartialTicks;
      double width = Math.abs(entity.boundingBox.maxX - entity.boundingBox.minX) + 0.2D;
      double height = 0.1D;
      Vec3 vec = new Vec3(x - width / 2.0D, y, z - width / 2.0D);
      Vec3 vec2 = new Vec3(x + width / 2.0D, y + height, z + width / 2.0D);
      RenderingUtil.pre3D();
      Minecraft var10000 = mc;
      Minecraft.entityRenderer.setupCameraTransform(mc.timer.renderPartialTicks, 2);
      RenderingUtil.glColor(color);
      RenderingUtil.drawBoundingBox(new AxisAlignedBB(vec.xCoord - RenderManager.renderPosX, vec.yCoord - RenderManager.renderPosY, vec.zCoord - RenderManager.renderPosZ, vec2.xCoord - RenderManager.renderPosX, vec2.yCoord - RenderManager.renderPosY, vec2.zCoord - RenderManager.renderPosZ));
      GL11.glColor4f(0.0F, 0.0F, 0.0F, 1.0F);
      RenderingUtil.post3D();
   }

   public void onDisable() {
      if(Minecraft.thePlayer.isBlocking() && ((Boolean)this.blocking.getValue()).booleanValue()) {
         this.stopBlocking();
      }

      Minecraft.thePlayer.itemInUseCount = 0;
      this.targets.clear();
      curTarget = null;
      this.lastAngles.x = Minecraft.thePlayer.rotationYaw;
      super.onDisable();
   }

   public void onEnable() {
      this.lastAngles.x = Minecraft.thePlayer.rotationYaw;
      curTarget = null;
      this.index = 0;
      this.xd = 0;
      super.onEnable();
   }

   private boolean canBlock() {
      return Minecraft.thePlayer.getCurrentEquippedItem() != null && Minecraft.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword;
   }

   private boolean shouldAttack() {
      int APS = 20 / this.randomNumber(((Double)this.MaxCPS.getValue()).intValue(), ((Double)this.MinCPS.getValue()).intValue());
      if(this.timer.hasReached((double)(50 * APS))) {
         this.timer.reset();
         return true;
      } else {
         return false;
      }
   }

   public Entity raycast5(Entity fromEntity) {
      if(((Boolean)this.raycast.getValue()).booleanValue()) {
         for(Entity en2 : Minecraft.theWorld.loadedEntityList) {
            if(en2 != Minecraft.thePlayer && !en2.equals(Minecraft.thePlayer) && en2 != fromEntity && !en2.equals(fromEntity) && (en2.isInvisible() || en2 instanceof EntityArmorStand) && en2.boundingBox.intersectsWith(fromEntity.boundingBox)) {
               return Minecraft.thePlayer.canEntityBeSeen(en2)?en2:en2;
            }
         }
      }

      return fromEntity;
   }

   private void SwitchTarget() {
      Minecraft var10000 = mc;
      if((double)Minecraft.thePlayer.getDistanceToEntity((EntityLivingBase)this.targets.get(this.index)) > ((Double)this.reach.getValue()).doubleValue() + 0.2D) {
         ++this.index;
      }

      if(this.mode.getValue() == Aura.AuraMode.Switch && this.SwitchTimer.hasReached(((Double)this.SwitchDelay.getValue()).doubleValue())) {
         ++this.index;
         this.SwitchTimer.reset();
      }

      if(this.mode.getValue() == Aura.AuraMode.Single) {
         this.index = 0;
      }

   }

   private final int randomNumber(int var1, int var2) {
      return (int)(Math.random() * (double)(var1 - var2)) + var2;
   }

   @EventHandler
   private void onUpdate(EventPreUpdate event) {
      this.setSuffix(this.mode.getValue() + " " + this.Prmode.getValue() + " " + this.Aim.getValue());
      Client var10000 = Client.instance;
      Client.getModuleManager();
      Scaffold i1 = (Scaffold)ModuleManager.getModuleByName("Scaffold");
      if(i1.isEnabled() && ((Boolean)Scaffold.auracheck.getValue()).booleanValue()) {
         curTarget = null;
      } else {
         this.targets = this.sortList(this.loadTargets());
         if(curTarget != null && curTarget instanceof EntityPlayer || curTarget instanceof EntityMob || curTarget instanceof EntityAnimal) {
            curTarget = null;
         }

         if(Minecraft.thePlayer.ticksExisted % 22 == 0 && this.targets.size() > 1) {
            this.SwitchTarget();
         }

         if(!this.targets.isEmpty()) {
            if(this.index >= this.targets.size()) {
               this.index = 0;
            }

            curTarget = (EntityLivingBase)this.targets.get(this.index);
            if(AutoPot.isPotting()) {
               return;
            }

            if(this.Aim.getValue() == Aura.AimMode.Normal && !Helper.onServer("invaded") && !Helper.onServer("minemen") && !Helper.onServer("faithful")) {
               float yaw = RotationUtil.faceTarget(curTarget, 1000.0F, 1000.0F, false)[0];
               Client.RenderRotate(yaw);
               event.setYaw(yaw);
               event.setPitch(RotationUtil.faceTarget(curTarget, 1000.0F, 1000.0F, false)[1]);
            }

            if(this.Aim.getValue() == Aura.AimMode.AimSmooth) {
               float[] range1 = RotationUtil.getRotationsEx(curTarget);
               float isAttacking1 = MathHelper.clamp_float(RotationUtil.getYawChangeGiven(curTarget.posX, curTarget.posZ, this.lastAngles.x) + (float)this.randomNumber(-5, 5), -180.0F, 180.0F);
               int vel2 = ((Double)this.AimSpeed.getValue()).intValue();
               if(isAttacking1 > (float)vel2) {
                  isAttacking1 = (float)vel2;
               } else if(isAttacking1 < (float)(-vel2)) {
                  isAttacking1 = (float)(-vel2);
               }

               float yaw = this.lastAngles.x += isAttacking1 / 1.1F;
               Client.RenderRotate(yaw);
               event.setYaw(yaw);
               event.setPitch(range1[1] / 1.5F + (float)this.randomNumber(-5, 5));
            }

            float[] rot = RotationUtil.getRotations(curTarget);
            if(this.Aim.getValue() == Aura.AimMode.Basic) {
               float yaw = rot[0];
               Client.RenderRotate(yaw);
               event.setYaw(yaw);
               event.setPitch(rot[1]);
               sYaw = rot[0];
               sPitch = rot[1];
            }

            if(this.Aim.getValue() == Aura.AimMode.BetterSmooth) {
               this.smoothAim(event);
            }

            if(this.Aim.getValue() == Aura.AimMode.Predict) {
               rot = RotationUtil.getPredictedRotations(curTarget);
               float yaw = rot[0];
               Client.RenderRotate(yaw);
               event.setYaw(yaw);
               event.setPitch(rot[1]);
               sYaw = rot[0];
               sPitch = rot[1];
            }

            if(this.Aim.getValue() == Aura.AimMode.insane) {
               double range1 = ((Double)this.reach.getValue()).doubleValue();
               float[] NeedRotation = RotationUtil.getRotationsInsane(curTarget, range1 + 2.0D);
               if(NeedRotation == null) {
                  return;
               }

               new Random();
               float targetYaw = MathHelper.clamp_float(RotationUtil.getYawChangeGiven(curTarget.posX, curTarget.posZ, this.lastAngles.x), -180.0F, 180.0F);
               int yawspeed = ((Double)this.AimSpeed.getValue()).intValue();
               if(targetYaw > (float)yawspeed) {
                  targetYaw = (float)yawspeed;
               } else if(targetYaw < (float)(-yawspeed)) {
                  targetYaw = (float)(-yawspeed);
               }

               float yaw = this.lastAngles.x += targetYaw / 1.1F;
               Client.RenderRotate(yaw);
               event.setYaw(yaw);
               event.setPitch(NeedRotation[1]);
               sYaw = NeedRotation[0];
               sPitch = NeedRotation[1];
            }

            if(this.Aim.getValue() == Aura.AimMode.LAC) {
               float[] YP = RotationUtil.getRotationsForAura(curTarget, ((Double)this.reach.getValue()).doubleValue() + 1.0D);
               if(YP == null) {
                  return;
               }

               event.setYaw(YP[0]);
               event.setPitch(YP[1]);
               Client.RenderRotate(YP[0]);
               sYaw = YP[0];
               sPitch = YP[1];
            }

            float[] serverAngles = new float[2];
            if(this.Aim.getValue() == Aura.AimMode.LACSmooth) {
               float[] dstAngle = RotationUtil.LACrotate(curTarget);
               float[] srcAngle = new float[]{serverAngles[0], serverAngles[1]};
               serverAngles = this.smoothAngle(dstAngle, srcAngle);
               event.setYaw(serverAngles[0]);
               event.setPitch(serverAngles[1]);
               Client.RenderRotate(serverAngles[0]);
               sYaw = serverAngles[0];
               sPitch = serverAngles[1];
            }

            if(this.Aim.getValue() == Aura.AimMode.LACDynamic) {
               float[] rots = RotationUtil.LACrotate(curTarget);
               event.setYaw(rots[0]);
               event.setPitch(rots[1]);
               Client.RenderRotate(rots[0]);
               sYaw = rots[0];
               sPitch = rots[1];
            }

            if(this.Aim.getValue() == Aura.AimMode.Random) {
               float[] rotations = RotationUtil.getRotationsEx(curTarget);
               float targetYaw = MathHelper.clamp_float(RotationUtil.EXgetYawChangeGiven(curTarget.posX, curTarget.posZ, this.lastAngles.x) + (float)MathUtil.randomNumber(-5, 5), -180.0F, 180.0F);
               int maxAngleStep = ((Double)this.AimSpeed.getValue()).intValue();
               if(targetYaw > (float)maxAngleStep) {
                  targetYaw = (float)maxAngleStep;
               } else if(targetYaw < (float)(-maxAngleStep)) {
                  targetYaw = (float)(-maxAngleStep);
               }

               event.setYaw(this.lastAngles.x += targetYaw / 1.1F);
               event.setPitch(rotations[1] / 1.5F + (float)MathUtil.randomNumber(-5, 5));
            }

            if(this.Aim.getValue() == Aura.AimMode.LittleRandom) {
               Random random = new Random();
               Vector2f vec = new Vector2f(0.0F, 0.0F);
               float rot1 = MathHelper.clamp_float(RotationUtil.EXgetYawChangeGiven(curTarget.posX, curTarget.posZ, vec.x), -180.0F, 180.0F);
               int rot2 = ((Double)this.AimSpeed.getValue()).intValue() - 20 + random.nextInt(30);
               if(rot1 > (float)rot2) {
                  rot1 = (float)rot2;
               } else if(rot1 < (float)(-rot2)) {
                  rot1 = (float)(-rot2);
               }

               event.setYaw(vec.x += rot1 / 1.1F + (float)(random.nextInt(200) > 100?-3:3));
               event.setPitch(rot[1] / 1.1F + (float)random.nextInt(5));
            }

            Minecraft var29 = mc;
            sYaw = Minecraft.thePlayer.rotationYaw;
            var29 = mc;
            sPitch = Minecraft.thePlayer.rotationPitch;
         }

      }
   }

   public float[] smoothAngle(float[] dst, float[] src) {
      float[] smoothedAngle = new float[]{src[0] - dst[0], src[1] - dst[1]};
      smoothedAngle = MathUtil.constrainAngle(smoothedAngle);
      smoothedAngle[0] = src[0] - smoothedAngle[0] / 100.0F * this.randomFloat(8.0F, 20.0F);
      smoothedAngle[1] = src[1] - smoothedAngle[1] / 100.0F * this.randomFloat(0.0F, 8.0F);
      return smoothedAngle;
   }

   public float randomFloat(float min, float max) {
      return min + random.nextFloat() * (max - min);
   }

   private void swap(int slot, int hotbarNum) {
      Minecraft.playerController.windowClick(Minecraft.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, Minecraft.thePlayer);
   }

   @EventHandler
   private void onUpdatePost(EventPostUpdate e2) {
      Client var10000 = Client.instance;
      Client.getModuleManager();
      Scaffold i1 = (Scaffold)ModuleManager.getModuleByName("Scaffold");
      if(i1.isEnabled() && ((Boolean)Scaffold.auracheck.getValue()).booleanValue()) {
         curTarget = null;
         Minecraft.thePlayer.itemInUseCount = 0;
      } else {
         int crackSize = ((Double)this.crack.getValue()).intValue();
         if(curTarget != null) {
            if(!Minecraft.thePlayer.isBlocking() && ((Boolean)this.blocking.getValue()).booleanValue() && this.canBlock() && !this.isAttacking) {
               this.startBlocking();
            }

            if((double)curTarget.hurtTime >= ((Double)this.HurtTime.getValue()).doubleValue()) {
               return;
            }

            double angle = Math.toRadians((double)(curTarget.rotationYaw - 90.0F + 360.0F)) % 360.0D;
            if(this.shouldAttack()) {
               this.attack();
            }

            int i2 = 0;
            if((double)Minecraft.thePlayer.getDistanceToEntity(curTarget) > ((Double)this.reach.getValue()).doubleValue() + 0.2D) {
               return;
            }

            while(i2 < crackSize) {
               Minecraft var7 = mc;
               Minecraft.effectRenderer.emitParticleAtEntity(curTarget, EnumParticleTypes.CRIT);
               ++i2;
            }
         } else {
            this.lastAngles.x = Minecraft.thePlayer.rotationYaw;
            if(this.isBlocking && Minecraft.thePlayer.isBlocking() && ((Boolean)this.blocking.getValue()).booleanValue()) {
               this.stopBlocking();
               Minecraft.thePlayer.itemInUseCount = 0;
            }
         }

      }
   }

   private List loadTargets() {
      return (List)Minecraft.theWorld.loadedEntityList.stream().filter((e2) -> {
         return (double)Minecraft.thePlayer.getDistanceToEntity(e2) <= ((Double)this.reach.getValue()).doubleValue() + 0.2D + ((Double)BlockReach.getValue()).doubleValue() && this.qualifies(e2) && !CustomAntiBot.isBot((EntityLivingBase)e2) && this.isInFOV(e2);
      }).collect(Collectors.toList());
   }

   private boolean isInFOV(Entity entity) {
      int fov1 = ((Double)FOV.getValue()).intValue();
      return RotationUtils.getYawChange(entity.posX, entity.posZ) <= (float)fov1 && RotationUtils.getPitchChange(entity, entity.posY) <= (float)fov1;
   }

   private boolean qualifies(Entity e2) {
      if(!RotationUtils.canEntityBeSeen(e2)) {
         Minecraft var10000 = mc;
         if((double)Minecraft.thePlayer.getDistanceToEntity(e2) > ((Double)ThroughWallReach.getValue()).doubleValue()) {
            return false;
         }
      }

      if(e2 == Minecraft.thePlayer) {
         return false;
      } else if(e2 instanceof EntityMob && ((Boolean)this.mobs.getValue()).booleanValue()) {
         return true;
      } else if(e2 instanceof EntityAnimal && ((Boolean)this.animals.getValue()).booleanValue()) {
         return true;
      } else {
         AntiBot ab2 = (AntiBot)ModuleManager.getModuleByName("AntiBot");
         return ab2.isServerBot(e2)?false:(!e2.isEntityAlive()?false:(FriendManager.isFriend(e2.getName())?false:(e2 instanceof EntityPlayer && ((Boolean)this.players.getValue()).booleanValue() && !Teams.isOnSameTeam(e2) && ((EntityLivingBase)e2).ticksExisted > ((Double)Existed.getValue()).intValue()?true:e2.isInvisible() && !((Boolean)this.invis.getValue()).booleanValue())));
      }
   }

   private void attack() {
      if((double)Minecraft.thePlayer.getDistanceToEntity(curTarget) <= ((Double)this.reach.getValue()).doubleValue() + 0.2D) {
         this.isAttacking = true;
         if(Minecraft.thePlayer.isBlocking() && ((Boolean)this.blocking.getValue()).booleanValue()) {
            this.stopBlocking();
         }

         Client var10000 = Client.instance;
         Client.getModuleManager();
         Criticals i1 = (Criticals)ModuleManager.getModuleByName("Criticals");
         if(i1.isEnabled()) {
            i1.autoCrit();
         }

         Minecraft.thePlayer.swingItem();
         Minecraft.thePlayer.sendQueue.addToSendQueue(new C02PacketUseEntity(curTarget, C02PacketUseEntity.Action.ATTACK));
         float sharpLevel = EnchantmentHelper.func_152377_a(Minecraft.thePlayer.getHeldItem(), curTarget.getCreatureAttribute());
         if(sharpLevel > 0.0F) {
            Minecraft.thePlayer.onEnchantmentCritical(curTarget);
         }

         if(!Minecraft.thePlayer.isBlocking() && ((Boolean)this.blocking.getValue()).booleanValue() && this.canBlock()) {
            this.startBlocking();
         }

         this.isAttacking = false;
      }
   }

   private List sortList(List list) {
      if(this.Prmode.getValue() == Aura.priority.Health) {
         list.sort((o1, o2) -> {
            return (int)(((EntityLivingBase)o1).getHealth() - ((EntityLivingBase)o2).getHealth());
         });
      }

      if(this.Prmode.getValue() == Aura.priority.Angle) {
         list.sort((o1, o2) -> {
            float[] rot1 = RotationUtil.getRotations((Entity)o1);
            float[] rot2 = RotationUtil.getRotations((Entity) o2);
            return (int)(Minecraft.thePlayer.rotationYaw - rot1[0] - Minecraft.thePlayer.rotationYaw - rot2[0]);
         });
      }

      if(this.Prmode.getValue() == Aura.priority.Slowly) {
         list.sort((ent1, ent2) -> {
            float f2 = 0.0F;
            float e1 = RotationUtil.getRotations((Entity)ent1)[0];
            float e2 = RotationUtil.getRotations((Entity)ent2)[0];
            return e1 < f2?1:(e1 == e2?0:-2);
         });
      }

      if(this.Prmode.getValue() == Aura.priority.Range) {
         list.sort((o1, o2) -> {
            Minecraft var10001 = mc;
            float var10000 = ((Entity) o1).getDistanceToEntity(Minecraft.thePlayer);
            Minecraft var10002 = mc;
            return (int)(var10000 - ((Entity) o2).getDistanceToEntity(Minecraft.thePlayer));
         });
      }

      if(this.Prmode.getValue() == Aura.priority.Fov) {
         list.sort(Comparator.comparingDouble((o) -> {
            Minecraft var10000 = mc;
            return (double)RotationUtil.getDistanceBetweenAngles(Minecraft.thePlayer.rotationPitch, getRotationToEntity((Entity) o)[0]);
         }));
      }

      return list;
   }

   public static float[] getRotationToEntity(Entity target) {
      double var10000 = target.posX;
      Minecraft.getMinecraft();
      double xDiff = var10000 - Minecraft.thePlayer.posX;
      var10000 = target.posY;
      Minecraft.getMinecraft();
      double yDiff = var10000 - Minecraft.thePlayer.posY;
      var10000 = target.posZ;
      Minecraft.getMinecraft();
      double zDiff = var10000 - Minecraft.thePlayer.posZ;
      float yaw = (float)(Math.atan2(zDiff, xDiff) * 180.0D / 3.141592653589793D) - 90.0F;
      var10000 = target.posY + (double)target.getEyeHeight() / 0.0D;
      Minecraft.getMinecraft();
      double var10001 = Minecraft.thePlayer.posY;
      Minecraft.getMinecraft();
      float pitch = (float)(-Math.atan2(var10000 - (var10001 + (double)Minecraft.thePlayer.getEyeHeight()), Math.hypot(xDiff, zDiff)) * 180.0D / 3.141592653589793D);
      if(yDiff > -0.2D && yDiff < 0.2D) {
         var10000 = target.posY + (double)target.getEyeHeight() / Aura.HitLocation.CHEST.getOffset();
         Minecraft.getMinecraft();
         var10001 = Minecraft.thePlayer.posY;
         Minecraft.getMinecraft();
         pitch = (float)(-Math.atan2(var10000 - (var10001 + (double)Minecraft.thePlayer.getEyeHeight()), Math.hypot(xDiff, zDiff)) * 180.0D / 3.141592653589793D);
      } else if(yDiff > -0.2D) {
         var10000 = target.posY + (double)target.getEyeHeight() / Aura.HitLocation.FEET.getOffset();
         Minecraft.getMinecraft();
         var10001 = Minecraft.thePlayer.posY;
         Minecraft.getMinecraft();
         pitch = (float)(-Math.atan2(var10000 - (var10001 + (double)Minecraft.thePlayer.getEyeHeight()), Math.hypot(xDiff, zDiff)) * 180.0D / 3.141592653589793D);
      } else if(yDiff < 0.3D) {
         var10000 = target.posY + (double)target.getEyeHeight() / Aura.HitLocation.HEAD.getOffset();
         Minecraft.getMinecraft();
         var10001 = Minecraft.thePlayer.posY;
         Minecraft.getMinecraft();
         pitch = (float)(-Math.atan2(var10000 - (var10001 + (double)Minecraft.thePlayer.getEyeHeight()), Math.hypot(xDiff, zDiff)) * 180.0D / 3.141592653589793D);
      }

      return new float[]{yaw, pitch};
   }

   private void smoothAim(EventPreUpdate em) {
      Minecraft var10000 = mc;
      sYaw = Minecraft.thePlayer.rotationYaw;
      var10000 = mc;
      sPitch = Minecraft.thePlayer.rotationPitch;
      double randomYaw = 0.05D;
      double randomPitch = 0.05D;
      float targetYaw = RotationUtil.getYawChange(sYaw, curTarget.posX + (double)this.randomNumber(1, -1) * randomYaw, curTarget.posZ + (double)this.randomNumber(1, -1) * randomYaw);
      float yawFactor = targetYaw / 1.7F;
      float yaw = sYaw + yawFactor;
      Client.RenderRotate(yaw);
      em.setYaw(yaw);
      sYaw += yawFactor;
      float targetPitch = RotationUtil.getPitchChange(sPitch, curTarget, curTarget.posY + (double)this.randomNumber(1, -1) * randomPitch);
      float pitchFactor = targetPitch / 1.7F;
      em.setPitch(sPitch + pitchFactor);
      sPitch += pitchFactor;
   }

   public static double randomNumber(double max, double min) {
      return Math.random() * (max - min) + min;
   }

   static enum AimMode {
      Normal,
      Basic,
      Predict,
      AimSmooth,
      BetterSmooth,
      insane,
      LAC,
      LACDynamic,
      LACSmooth,
      Random,
      LittleRandom;
   }

   static enum AuraMode {
      Single,
      Switch;
   }

   static enum AutoBlockMode {
      Normal,
      Interact;
   }

   static enum EMode {
      Box,
      None,
      Liquidbounce,
      New;
   }

   static enum HitLocation {
      AUTO(0.0D),
      HEAD(1.0D),
      CHEST(1.5D),
      FEET(3.5D);

      private double offset;

      private HitLocation(double offset) {
         this.offset = offset;
      }

      public double getOffset() {
         return this.offset;
      }
   }

   static enum priority {
      Range,
      Fov,
      Health,
      Slowly,
      Angle;
   }
}
