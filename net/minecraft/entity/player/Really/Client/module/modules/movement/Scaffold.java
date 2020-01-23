package net.minecraft.entity.player.Really.Client.module.modules.movement;

import java.awt.Color;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockCarpet;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockSkull;
import net.minecraft.block.BlockSnow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender2D;
import net.minecraft.entity.player.Really.Client.api.events.rendering.EventRender3D;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPostUpdate;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Option;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.module.modules.movement.BlockData;
import net.minecraft.entity.player.Really.Client.ui.font.CFontRenderer;
import net.minecraft.entity.player.Really.Client.ui.font.FontLoaders;
import net.minecraft.entity.player.Really.Client.utils.MoveUtils;
import net.minecraft.entity.player.Really.Client.utils.PlayerUtil;
import net.minecraft.entity.player.Really.Client.utils.render.RenderUtil;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.Container;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.potion.Potion;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Timer;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;

public class Scaffold extends Module {
   public static boolean firstdown = true;
   public static boolean safewalk;
   public static Option tower = new Option("Tower", "tower", Boolean.valueOf(true));
   public static Option silent = new Option("Silent", "Silent", Boolean.valueOf(true));
   public static Option aac = new Option("AAC", "AAC", Boolean.valueOf(false));
   public static Option Esp = new Option("ESP", "ESP", Boolean.valueOf(true));
   public static Option swingItem = new Option("swingItem", "swingItem", Boolean.valueOf(true));
   public static Option towermove = new Option("TowerMove", "BetterTower", Boolean.valueOf(false));
   public static Option pick = new Option("pick", "pick", Boolean.valueOf(true));
   public static Option safe = new Option("SafeWalk", "SafeWalk", Boolean.valueOf(false));
   public static Option down = new Option("DownScaffold", "DownScaffold", Boolean.valueOf(true));
   public static Option Lag = new Option("Lag", "Lag", Boolean.valueOf(true));
   public static Option auracheck = new Option("Auracheck", "Auracheck", Boolean.valueOf(true));
   private Boolean SprintKeyDown;
   public BlockData blockdata;
   private float Disfall;
   private Boolean autodis;
   private List blacklist;
   private int width = 0;
   private Scaffold.BlockCache blockCache;
   private int currentItem;
   private static List blacklistedBlocks = Arrays.asList(new Block[]{Blocks.air, Blocks.water, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.enchanting_table, Blocks.carpet, Blocks.glass_pane, Blocks.stained_glass_pane, Blocks.iron_bars, Blocks.snow_layer, Blocks.ice, Blocks.packed_ice, Blocks.coal_ore, Blocks.diamond_ore, Blocks.emerald_ore, Blocks.chest, Blocks.trapped_chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.tnt, Blocks.gold_ore, Blocks.iron_ore, Blocks.lapis_ore, Blocks.lit_redstone_ore, Blocks.quartz_ore, Blocks.redstone_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.tallgrass, Blocks.tripwire, Blocks.tripwire_hook, Blocks.rail, Blocks.waterlily, Blocks.red_flower, Blocks.red_mushroom, Blocks.brown_mushroom, Blocks.vine, Blocks.trapdoor, Blocks.yellow_flower, Blocks.ladder, Blocks.furnace, Blocks.sand, Blocks.cactus, Blocks.dispenser, Blocks.noteblock, Blocks.dropper, Blocks.crafting_table, Blocks.web, Blocks.pumpkin, Blocks.sapling, Blocks.cobblestone_wall, Blocks.oak_fence});

   public static List getBlacklistedBlocks() {
      return blacklistedBlocks;
   }

   public Scaffold() {
      super("Scaffold", new String[]{"magiccarpet", "blockplacer", "airwalk"}, ModuleType.Movement);
      this.addValues(new Value[]{tower, silent, aac, Esp, swingItem, towermove, safe, Lag, down, pick, auracheck});
      this.currentItem = 0;
      this.setColor((new Color(244, 119, 194)).getRGB());
   }

   public void onEnable() {
      super.onEnable();
   }

   public void onDisable() {
      super.onDisable();
   }

   @EventHandler
   public void onRender2D(EventRender2D event) {
      CFontRenderer font = FontLoaders.sansation18;
      ScaledResolution sr = new ScaledResolution(mc);
      String var10001 = Integer.toString(getBlockCount()) + " Blocks";
      int var10002 = sr.getScaledWidth() / 2 + 1;
      Minecraft var10003 = mc;
      font.drawStringWithShadow(var10001, (double)(var10002 - Minecraft.fontRendererObj.getStringWidth(Integer.toString(getBlockCount())) / 2), (double)(sr.getScaledHeight() / 2 + 12), this.getBlockColor(getBlockCount()));
   }

   private int getBlockColor(int count) {
      float f = (float)count;
      float f1 = 64.0F;
      float f2 = Math.max(0.0F, Math.min(f, f1) / f1);
      return Color.HSBtoRGB(f2 / 3.0F, 1.0F, 1.0F) | -16777216;
   }

   @EventHandler
   private void onrender(EventRender3D event) {
      Color color = new Color(net.minecraft.entity.player.Really.Client.utils.render.Colors.BLACK.c);
      Color color2 = new Color(net.minecraft.entity.player.Really.Client.utils.render.Colors.ORANGE.c);
      double x = Minecraft.thePlayer.lastTickPosX + (Minecraft.thePlayer.posX - Minecraft.thePlayer.lastTickPosX) * (double)mc.timer.renderPartialTicks - RenderManager.renderPosX;
      double y = Minecraft.thePlayer.lastTickPosY + (Minecraft.thePlayer.posY - Minecraft.thePlayer.lastTickPosY) * (double)mc.timer.renderPartialTicks - RenderManager.renderPosY;
      double z = Minecraft.thePlayer.lastTickPosZ + (Minecraft.thePlayer.posZ - Minecraft.thePlayer.lastTickPosZ) * (double)mc.timer.renderPartialTicks - RenderManager.renderPosZ;
      double x2 = Minecraft.thePlayer.lastTickPosX + (Minecraft.thePlayer.posX - Minecraft.thePlayer.lastTickPosX) * (double)mc.timer.renderPartialTicks - RenderManager.renderPosX;
      double y2 = Minecraft.thePlayer.lastTickPosY + (Minecraft.thePlayer.posY - Minecraft.thePlayer.lastTickPosY) * (double)mc.timer.renderPartialTicks - RenderManager.renderPosY;
      double z2 = Minecraft.thePlayer.lastTickPosZ + (Minecraft.thePlayer.posZ - Minecraft.thePlayer.lastTickPosZ) * (double)mc.timer.renderPartialTicks - RenderManager.renderPosZ;
      x = x - 0.65D;
      z = z - 0.65D;
      x2 = x2 - 0.5D;
      z2 = z2 - 0.5D;
      y = y + ((double)Minecraft.thePlayer.getEyeHeight() + 0.35D - (Minecraft.thePlayer.isSneaking()?0.25D:0.0D));
      if(((Boolean)Esp.getValue()).booleanValue()) {
         GL11.glPushMatrix();
         GL11.glEnable(3042);
         GL11.glBlendFunc(770, 771);
         double rotAdd = -0.25D * (double)(Math.abs(Minecraft.thePlayer.rotationPitch) / 90.0F);
         GL11.glDisable(3553);
         GL11.glEnable(2848);
         GL11.glDisable(2929);
         GL11.glDepthMask(false);
         GL11.glColor4f((float)color.getRed() / 255.0F, (float)color.getGreen() / 255.0F, (float)color.getBlue() / 255.0F, 1.0F);
         GL11.glLineWidth(2.0F);
         RenderUtil.drawOutlinedBoundingBox(new AxisAlignedBB(x, y - 2.0D, z, x + 1.3D, y - 2.0D, z + 1.3D));
         RenderUtil.drawOutlinedBoundingBox(new AxisAlignedBB(x2, y - 2.0D, z2, x2 + 1.0D, y - 2.0D, z2 + 1.0D));
         GL11.glColor4f((float)color2.getRed() / 255.0F, (float)color2.getGreen() / 255.0F, (float)color2.getBlue() / 255.0F, 1.0F);
         RenderUtil.drawOutlinedBoundingBox(new AxisAlignedBB(x, y - 2.0D, z, x + 1.3D, y - 2.0D, z + 1.3D));
      }

      GL11.glDisable(2848);
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDepthMask(true);
      GL11.glDisable(3042);
      GL11.glPopMatrix();
   }

   @EventHandler
   private void onUpdate(EventPreUpdate event) {
      if(((Boolean)aac.getValue()).booleanValue()) {
         this.setSuffix("AAC");
      } else {
         this.setSuffix("Smooth");
      }

      if(((Boolean)aac.getValue()).booleanValue()) {
         Minecraft.thePlayer.setSprinting(false);
      }

      if(Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
         Timer var10000 = mc.timer;
         Timer.timerSpeed = 0.999F;
      }

      if(this.grabBlockSlot() != -1) {
         this.blockCache = this.grab();
         if(((Boolean)tower.getValue()).booleanValue() && (((Boolean)towermove.getValue()).booleanValue() || !PlayerUtil.isMoving2())) {
            this.tower();
         }

         Minecraft var10002 = mc;
         Minecraft var10003 = mc;
         double var7 = Minecraft.thePlayer.posY - 1.0D;
         Minecraft var10004 = mc;
         BlockPos underPos = new BlockPos(Minecraft.thePlayer.posX, var7, Minecraft.thePlayer.posZ);
         Minecraft var6 = mc;
         Block underBlock = Minecraft.theWorld.getBlockState(underPos).getBlock();
         BlockData data = this.getBlockData(underPos);
         float[] rot = getRotations(data.position, data.face);
         Client.RenderRotate(rot[0]);
         event.setYaw(rot[0]);
         event.setPitch(rot[1]);
      }

   }

   public static float[] getRotations(BlockPos block, EnumFacing face) {
      double var10000 = (double)block.getX() + 0.5D;
      Minecraft var10001 = mc;
      double x = var10000 - Minecraft.thePlayer.posX + (double)face.getFrontOffsetX() / 2.0D;
      var10000 = (double)block.getZ() + 0.5D;
      var10001 = mc;
      double z = var10000 - Minecraft.thePlayer.posZ + (double)face.getFrontOffsetZ() / 2.0D;
      double y = (double)block.getY() + 0.5D;
      Minecraft var15 = mc;
      var10001 = mc;
      double d1 = Minecraft.thePlayer.posY + (double)Minecraft.thePlayer.getEyeHeight() - y;
      double d3 = (double)MathHelper.sqrt_double(x * x + z * z);
      float yaw = (float)(Math.atan2(z, x) * 180.0D / 3.141592653589793D) - 90.0F;
      float pitch = (float)(Math.atan2(d1, d3) * 180.0D / 3.141592653589793D);
      if(yaw < 0.0F) {
         yaw += 360.0F;
      }

      return new float[]{yaw, pitch};
   }

   private boolean invCheck() {
      for(int i = 36; i < 45; ++i) {
         if(Minecraft.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            Item item = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack().getItem();
            if(item instanceof ItemBlock && isValid(item)) {
               return false;
            }
         }
      }

      return true;
   }

   public static int getBlockCount() {
      int blockCount = 0;

      for(int i = 0; i < 45; ++i) {
         if(Minecraft.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
            ItemStack is = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack();
            Item item = is.getItem();
            if(is.getItem() instanceof ItemBlock && isValid(item)) {
               blockCount += is.stackSize;
            }
         }
      }

      return blockCount;
   }

   private static boolean isValid(Item item) {
      if(!(item instanceof ItemBlock)) {
         return false;
      } else {
         ItemBlock iBlock = (ItemBlock)item;
         Block block = iBlock.getBlock();
         return !blacklistedBlocks.contains(block);
      }
   }

   public static Vec3 getVec3(BlockPos pos, EnumFacing face) {
      double x = (double)pos.getX() + 0.5D;
      double y = (double)pos.getY() + 0.5D;
      double z = (double)pos.getZ() + 0.5D;
      x = x + (double)face.getFrontOffsetX() / 2.0D;
      z = z + (double)face.getFrontOffsetZ() / 2.0D;
      y = y + (double)face.getFrontOffsetY() / 2.0D;
      if(face != EnumFacing.UP && face != EnumFacing.DOWN) {
         y += randomNumber(0.3D, -0.3D);
      } else {
         x += randomNumber(0.3D, -0.3D);
         z += randomNumber(0.3D, -0.3D);
      }

      if(face == EnumFacing.WEST || face == EnumFacing.EAST) {
         z += randomNumber(0.3D, -0.3D);
      }

      if(face == EnumFacing.SOUTH || face == EnumFacing.NORTH) {
         x += randomNumber(0.3D, -0.3D);
      }

      return new Vec3(x, y, z);
   }

   public static double randomNumber(double max, double min) {
      return Math.random() * (max - min) + min;
   }

   @EventHandler
   private void onPostUpdate(EventPostUpdate event) {
      this.getBestBlocks();
      if(this.blockCache != null) {
         if(((Boolean)swingItem.getValue()).booleanValue()) {
            Minecraft.thePlayer.swingItem();
         } else {
            Minecraft var10000 = mc;
            Minecraft.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
         }

         int currentSlot = Minecraft.thePlayer.inventory.currentItem;
         int slot = this.grabBlockSlot();
         if(slot == -1) {
            this.blockCache = null;
            return;
         }

         Minecraft.thePlayer.inventory.currentItem = slot;
         Minecraft var10002 = mc;
         Minecraft var10003 = mc;
         double var8 = Minecraft.thePlayer.posY - 1.0D;
         Minecraft var10004 = mc;
         BlockPos underPos = new BlockPos(Minecraft.thePlayer.posX, var8, Minecraft.thePlayer.posZ);
         Minecraft var7 = mc;
         Block underBlock = Minecraft.theWorld.getBlockState(underPos).getBlock();
         BlockData data = this.getBlockData(underPos);
         if(this.placeBlock(data)) {
            if(((Boolean)silent.getValue()).booleanValue()) {
               Minecraft.thePlayer.inventory.currentItem = currentSlot;
               Minecraft.thePlayer.sendQueue.addToSendQueue(new C09PacketHeldItemChange(currentSlot));
            }

            this.blockCache = null;
         }
      }

   }

   private boolean isPosSolid(BlockPos pos) {
      Minecraft var10000 = mc;
      Block block = Minecraft.theWorld.getBlockState(pos).getBlock();
      return (block.getMaterial().isSolid() || !block.isTranslucent() || block instanceof BlockLadder || block instanceof BlockCarpet || block instanceof BlockSnow || block instanceof BlockSkull) && !block.getMaterial().isLiquid() && !(block instanceof BlockContainer);
   }

   private boolean CanDownPut() {
      if(((Boolean)down.getValue()).booleanValue()) {
         Minecraft var10000 = mc;
         if(Minecraft.gameSettings.keyBindSprint.isKeyDown()) {
            var10000 = mc;
            if(Minecraft.thePlayer.onGround) {
               return true;
            }
         }
      }

      return false;
   }

   private BlockData getBlockData(BlockPos pos) {
      if(this.isPosSolid(pos.add(0, -1, 0))) {
         return new BlockData(pos.add(0, -1, 0), this.CanDownPut()?EnumFacing.DOWN:EnumFacing.UP);
      } else if(this.isPosSolid(pos.add(-1, 0, 0))) {
         return new BlockData(pos.add(-1, 0, 0), this.CanDownPut()?EnumFacing.DOWN:EnumFacing.EAST);
      } else if(this.isPosSolid(pos.add(1, 0, 0))) {
         return new BlockData(pos.add(1, 0, 0), this.CanDownPut()?EnumFacing.DOWN:EnumFacing.WEST);
      } else if(this.isPosSolid(pos.add(0, 0, 1))) {
         return new BlockData(pos.add(0, 0, 1), this.CanDownPut()?EnumFacing.DOWN:EnumFacing.NORTH);
      } else if(this.isPosSolid(pos.add(0, 0, -1))) {
         return new BlockData(pos.add(0, 0, -1), this.CanDownPut()?EnumFacing.DOWN:EnumFacing.SOUTH);
      } else {
         BlockPos pos1 = pos.add(-1, 0, 0);
         if(this.isPosSolid(pos1.add(0, -1, 0))) {
            return new BlockData(pos1.add(0, -1, 0), EnumFacing.UP);
         } else if(this.isPosSolid(pos1.add(-1, 0, 0))) {
            return new BlockData(pos1.add(-1, 0, 0), EnumFacing.EAST);
         } else if(this.isPosSolid(pos1.add(1, 0, 0))) {
            return new BlockData(pos1.add(1, 0, 0), EnumFacing.WEST);
         } else if(this.isPosSolid(pos1.add(0, 0, 1))) {
            return new BlockData(pos1.add(0, 0, 1), EnumFacing.NORTH);
         } else if(this.isPosSolid(pos1.add(0, 0, -1))) {
            return new BlockData(pos1.add(0, 0, -1), EnumFacing.SOUTH);
         } else {
            BlockPos pos2 = pos.add(1, 0, 0);
            if(this.isPosSolid(pos2.add(0, -1, 0))) {
               return new BlockData(pos2.add(0, -1, 0), EnumFacing.UP);
            } else if(this.isPosSolid(pos2.add(-1, 0, 0))) {
               return new BlockData(pos2.add(-1, 0, 0), EnumFacing.EAST);
            } else if(this.isPosSolid(pos2.add(1, 0, 0))) {
               return new BlockData(pos2.add(1, 0, 0), EnumFacing.WEST);
            } else if(this.isPosSolid(pos2.add(0, 0, 1))) {
               return new BlockData(pos2.add(0, 0, 1), EnumFacing.NORTH);
            } else if(this.isPosSolid(pos2.add(0, 0, -1))) {
               return new BlockData(pos2.add(0, 0, -1), EnumFacing.SOUTH);
            } else {
               BlockPos pos3 = pos.add(0, 0, 1);
               if(this.isPosSolid(pos3.add(0, -1, 0))) {
                  return new BlockData(pos3.add(0, -1, 0), EnumFacing.UP);
               } else if(this.isPosSolid(pos3.add(-1, 0, 0))) {
                  return new BlockData(pos3.add(-1, 0, 0), EnumFacing.EAST);
               } else if(this.isPosSolid(pos3.add(1, 0, 0))) {
                  return new BlockData(pos3.add(1, 0, 0), EnumFacing.WEST);
               } else if(this.isPosSolid(pos3.add(0, 0, 1))) {
                  return new BlockData(pos3.add(0, 0, 1), EnumFacing.NORTH);
               } else if(this.isPosSolid(pos3.add(0, 0, -1))) {
                  return new BlockData(pos3.add(0, 0, -1), EnumFacing.SOUTH);
               } else {
                  BlockPos pos4 = pos.add(0, 0, -1);
                  if(this.isPosSolid(pos4.add(0, -1, 0))) {
                     return new BlockData(pos4.add(0, -1, 0), EnumFacing.UP);
                  } else if(this.isPosSolid(pos4.add(-1, 0, 0))) {
                     return new BlockData(pos4.add(-1, 0, 0), EnumFacing.EAST);
                  } else if(this.isPosSolid(pos4.add(1, 0, 0))) {
                     return new BlockData(pos4.add(1, 0, 0), EnumFacing.WEST);
                  } else if(this.isPosSolid(pos4.add(0, 0, 1))) {
                     return new BlockData(pos4.add(0, 0, 1), EnumFacing.NORTH);
                  } else if(this.isPosSolid(pos4.add(0, 0, -1))) {
                     return new BlockData(pos4.add(0, 0, -1), EnumFacing.SOUTH);
                  } else {
                     BlockPos pos19 = pos.add(-2, 0, 0);
                     if(this.isPosSolid(pos1.add(0, -1, 0))) {
                        return new BlockData(pos1.add(0, -1, 0), EnumFacing.UP);
                     } else if(this.isPosSolid(pos1.add(-1, 0, 0))) {
                        return new BlockData(pos1.add(-1, 0, 0), EnumFacing.EAST);
                     } else if(this.isPosSolid(pos1.add(1, 0, 0))) {
                        return new BlockData(pos1.add(1, 0, 0), EnumFacing.WEST);
                     } else if(this.isPosSolid(pos1.add(0, 0, 1))) {
                        return new BlockData(pos1.add(0, 0, 1), EnumFacing.NORTH);
                     } else if(this.isPosSolid(pos1.add(0, 0, -1))) {
                        return new BlockData(pos1.add(0, 0, -1), EnumFacing.SOUTH);
                     } else {
                        BlockPos pos29 = pos.add(2, 0, 0);
                        if(this.isPosSolid(pos2.add(0, -1, 0))) {
                           return new BlockData(pos2.add(0, -1, 0), EnumFacing.UP);
                        } else if(this.isPosSolid(pos2.add(-1, 0, 0))) {
                           return new BlockData(pos2.add(-1, 0, 0), EnumFacing.EAST);
                        } else if(this.isPosSolid(pos2.add(1, 0, 0))) {
                           return new BlockData(pos2.add(1, 0, 0), EnumFacing.WEST);
                        } else if(this.isPosSolid(pos2.add(0, 0, 1))) {
                           return new BlockData(pos2.add(0, 0, 1), EnumFacing.NORTH);
                        } else if(this.isPosSolid(pos2.add(0, 0, -1))) {
                           return new BlockData(pos2.add(0, 0, -1), EnumFacing.SOUTH);
                        } else {
                           BlockPos pos39 = pos.add(0, 0, 2);
                           if(this.isPosSolid(pos3.add(0, -1, 0))) {
                              return new BlockData(pos3.add(0, -1, 0), EnumFacing.UP);
                           } else if(this.isPosSolid(pos3.add(-1, 0, 0))) {
                              return new BlockData(pos3.add(-1, 0, 0), EnumFacing.EAST);
                           } else if(this.isPosSolid(pos3.add(1, 0, 0))) {
                              return new BlockData(pos3.add(1, 0, 0), EnumFacing.WEST);
                           } else if(this.isPosSolid(pos3.add(0, 0, 1))) {
                              return new BlockData(pos3.add(0, 0, 1), EnumFacing.NORTH);
                           } else if(this.isPosSolid(pos3.add(0, 0, -1))) {
                              return new BlockData(pos3.add(0, 0, -1), EnumFacing.SOUTH);
                           } else {
                              BlockPos pos49 = pos.add(0, 0, -2);
                              if(this.isPosSolid(pos4.add(0, -1, 0))) {
                                 return new BlockData(pos4.add(0, -1, 0), EnumFacing.UP);
                              } else if(this.isPosSolid(pos4.add(-1, 0, 0))) {
                                 return new BlockData(pos4.add(-1, 0, 0), EnumFacing.EAST);
                              } else if(this.isPosSolid(pos4.add(1, 0, 0))) {
                                 return new BlockData(pos4.add(1, 0, 0), EnumFacing.WEST);
                              } else if(this.isPosSolid(pos4.add(0, 0, 1))) {
                                 return new BlockData(pos4.add(0, 0, 1), EnumFacing.NORTH);
                              } else if(this.isPosSolid(pos4.add(0, 0, -1))) {
                                 return new BlockData(pos4.add(0, 0, -1), EnumFacing.SOUTH);
                              } else {
                                 BlockPos pos5 = pos.add(0, -1, 0);
                                 if(this.isPosSolid(pos5.add(0, -1, 0))) {
                                    return new BlockData(pos5.add(0, -1, 0), EnumFacing.UP);
                                 } else if(this.isPosSolid(pos5.add(-1, 0, 0))) {
                                    return new BlockData(pos5.add(-1, 0, 0), EnumFacing.EAST);
                                 } else if(this.isPosSolid(pos5.add(1, 0, 0))) {
                                    return new BlockData(pos5.add(1, 0, 0), EnumFacing.WEST);
                                 } else if(this.isPosSolid(pos5.add(0, 0, 1))) {
                                    return new BlockData(pos5.add(0, 0, 1), EnumFacing.NORTH);
                                 } else if(this.isPosSolid(pos5.add(0, 0, -1))) {
                                    return new BlockData(pos5.add(0, 0, -1), EnumFacing.SOUTH);
                                 } else {
                                    BlockPos pos6 = pos5.add(1, 0, 0);
                                    if(this.isPosSolid(pos6.add(0, -1, 0))) {
                                       return new BlockData(pos6.add(0, -1, 0), EnumFacing.UP);
                                    } else if(this.isPosSolid(pos6.add(-1, 0, 0))) {
                                       return new BlockData(pos6.add(-1, 0, 0), EnumFacing.EAST);
                                    } else if(this.isPosSolid(pos6.add(1, 0, 0))) {
                                       return new BlockData(pos6.add(1, 0, 0), EnumFacing.WEST);
                                    } else if(this.isPosSolid(pos6.add(0, 0, 1))) {
                                       return new BlockData(pos6.add(0, 0, 1), EnumFacing.NORTH);
                                    } else if(this.isPosSolid(pos6.add(0, 0, -1))) {
                                       return new BlockData(pos6.add(0, 0, -1), EnumFacing.SOUTH);
                                    } else {
                                       BlockPos pos7 = pos5.add(-1, 0, 0);
                                       if(this.isPosSolid(pos7.add(0, -1, 0))) {
                                          return new BlockData(pos7.add(0, -1, 0), EnumFacing.UP);
                                       } else if(this.isPosSolid(pos7.add(-1, 0, 0))) {
                                          return new BlockData(pos7.add(-1, 0, 0), EnumFacing.EAST);
                                       } else if(this.isPosSolid(pos7.add(1, 0, 0))) {
                                          return new BlockData(pos7.add(1, 0, 0), EnumFacing.WEST);
                                       } else if(this.isPosSolid(pos7.add(0, 0, 1))) {
                                          return new BlockData(pos7.add(0, 0, 1), EnumFacing.NORTH);
                                       } else if(this.isPosSolid(pos7.add(0, 0, -1))) {
                                          return new BlockData(pos7.add(0, 0, -1), EnumFacing.SOUTH);
                                       } else {
                                          BlockPos pos8 = pos5.add(0, 0, 1);
                                          if(this.isPosSolid(pos8.add(0, -1, 0))) {
                                             return new BlockData(pos8.add(0, -1, 0), EnumFacing.UP);
                                          } else if(this.isPosSolid(pos8.add(-1, 0, 0))) {
                                             return new BlockData(pos8.add(-1, 0, 0), EnumFacing.EAST);
                                          } else if(this.isPosSolid(pos8.add(1, 0, 0))) {
                                             return new BlockData(pos8.add(1, 0, 0), EnumFacing.WEST);
                                          } else if(this.isPosSolid(pos8.add(0, 0, 1))) {
                                             return new BlockData(pos8.add(0, 0, 1), EnumFacing.NORTH);
                                          } else if(this.isPosSolid(pos8.add(0, 0, -1))) {
                                             return new BlockData(pos8.add(0, 0, -1), EnumFacing.SOUTH);
                                          } else {
                                             BlockPos pos9 = pos5.add(0, 0, -1);
                                             return this.isPosSolid(pos9.add(0, -1, 0))?new BlockData(pos9.add(0, -1, 0), EnumFacing.UP):(this.isPosSolid(pos9.add(-1, 0, 0))?new BlockData(pos9.add(-1, 0, 0), EnumFacing.EAST):(this.isPosSolid(pos9.add(1, 0, 0))?new BlockData(pos9.add(1, 0, 0), EnumFacing.WEST):(this.isPosSolid(pos9.add(0, 0, 1))?new BlockData(pos9.add(0, 0, 1), EnumFacing.NORTH):(this.isPosSolid(pos9.add(0, 0, -1))?new BlockData(pos9.add(0, 0, -1), EnumFacing.SOUTH):null))));
                                          }
                                       }
                                    }
                                 }
                              }
                           }
                        }
                     }
                  }
               }
            }
         }
      }
   }

   public void tower() {
      Minecraft var10002 = mc;
      Minecraft var10003 = mc;
      double var38 = Minecraft.thePlayer.posY - 1.0D;
      Minecraft var10004 = mc;
      BlockPos underPos = new BlockPos(Minecraft.thePlayer.posX, var38, Minecraft.thePlayer.posZ);
      Minecraft var10000 = mc;
      Block underBlock = Minecraft.theWorld.getBlockState(underPos).getBlock();
      BlockData data = this.getBlockData(underPos);
      var10000 = mc;
      if(!Minecraft.gameSettings.keyBindJump.isKeyDown()) {
         if(((Boolean)towermove.getValue()).booleanValue() && PlayerUtil.isMoving2()) {
            if(MoveUtils.isOnGround(0.76D) && !MoveUtils.isOnGround(0.75D)) {
               var10000 = mc;
               if(Minecraft.thePlayer.motionY > 0.23D) {
                  var10000 = mc;
                  if(Minecraft.thePlayer.motionY < 0.25D) {
                     var10000 = mc;
                     EntityPlayerSP var24 = Minecraft.thePlayer;
                     Minecraft var32 = mc;
                     double var33 = (double)Math.round(Minecraft.thePlayer.posY);
                     var10002 = mc;
                     var24.motionY = var33 - Minecraft.thePlayer.posY;
                  }
               }
            }

            if(!MoveUtils.isOnGround(1.0E-4D)) {
               var10000 = mc;
               if(Minecraft.thePlayer.motionY > 0.1D) {
                  var10000 = mc;
                  Minecraft var34 = mc;
                  if(Minecraft.thePlayer.posY >= (double)Math.round(Minecraft.thePlayer.posY) - 1.0E-4D) {
                     var10000 = mc;
                     var34 = mc;
                     if(Minecraft.thePlayer.posY <= (double)Math.round(Minecraft.thePlayer.posY) + 1.0E-4D) {
                        var10000 = mc;
                        Minecraft.thePlayer.motionY = 0.0D;
                     }
                  }
               }
            }
         }

      } else {
         if(PlayerUtil.isMoving2()) {
            if(MoveUtils.isOnGround(0.76D) && !MoveUtils.isOnGround(0.75D)) {
               var10000 = mc;
               if(Minecraft.thePlayer.motionY > 0.23D) {
                  var10000 = mc;
                  if(Minecraft.thePlayer.motionY < 0.25D) {
                     var10000 = mc;
                     EntityPlayerSP var8 = Minecraft.thePlayer;
                     Minecraft var10001 = mc;
                     double var29 = (double)Math.round(Minecraft.thePlayer.posY);
                     var10002 = mc;
                     var8.motionY = var29 - Minecraft.thePlayer.posY;
                  }
               }
            }

            if(MoveUtils.isOnGround(1.0E-4D)) {
               var10000 = mc;
               Minecraft.thePlayer.motionY = 0.42D;
               var10000 = mc;
               Minecraft.thePlayer.motionX *= 0.9D;
               var10000 = mc;
               Minecraft.thePlayer.motionZ *= 0.9D;
            } else {
               var10000 = mc;
               Minecraft var30 = mc;
               if(Minecraft.thePlayer.posY >= (double)Math.round(Minecraft.thePlayer.posY) - 1.0E-4D) {
                  var10000 = mc;
                  var30 = mc;
                  if(Minecraft.thePlayer.posY <= (double)Math.round(Minecraft.thePlayer.posY) + 1.0E-4D) {
                     var10000 = mc;
                     Minecraft.thePlayer.motionY = 0.0D;
                  }
               }
            }
         } else {
            var10000 = mc;
            Minecraft.thePlayer.motionX = 0.0D;
            var10000 = mc;
            Minecraft.thePlayer.motionZ = 0.0D;
            var10000 = mc;
            Minecraft.thePlayer.jumpMovementFactor = 0.0F;
            if(this.isAirBlock(underBlock) && data != null) {
               var10000 = mc;
               Minecraft.thePlayer.motionY = 0.4196D;
               var10000 = mc;
               Minecraft.thePlayer.motionX *= 0.75D;
               var10000 = mc;
               Minecraft.thePlayer.motionZ *= 0.75D;
            }
         }

      }
   }

   public static boolean isOnGround(double height) {
      Minecraft.getMinecraft();
      Minecraft.getMinecraft();
      Minecraft.getMinecraft();
      return !Minecraft.theWorld.getCollidingBoundingBoxes(Minecraft.thePlayer, Minecraft.thePlayer.getEntityBoundingBox().offset(0.0D, -height, 0.0D)).isEmpty();
   }

   public boolean isAirBlock(Block block) {
      return block.getMaterial().isReplaceable()?!(block instanceof BlockSnow) || block.getBlockBoundsMaxY() <= 0.125D:false;
   }

   public static boolean isMoving2() {
      Minecraft.getMinecraft();
      if(Minecraft.thePlayer.moveForward == 0.0F) {
         Minecraft.getMinecraft();
         if(Minecraft.thePlayer.moveStrafing == 0.0F) {
            return false;
         }
      }

      return true;
   }

   private boolean placeBlock(BlockData data) {
      Minecraft var10001 = mc;
      Minecraft var10002 = mc;
      Minecraft var10003 = mc;
      if(Minecraft.playerController.onPlayerRightClick(Minecraft.thePlayer, Minecraft.theWorld, Minecraft.thePlayer.inventory.getCurrentItem(), data.position, data.face, getVec3(data.position, data.face))) {
         Minecraft.thePlayer.sendQueue.addToSendQueue(new C0APacketAnimation());
         return true;
      } else {
         return false;
      }
   }

   private Vec3 grabPosition(BlockPos position, EnumFacing facing) {
      Vec3 offset = new Vec3((double)facing.getDirectionVec().getX() / 2.0D, (double)facing.getDirectionVec().getY() / 2.0D, (double)facing.getDirectionVec().getZ() / 2.0D);
      Vec3 point = new Vec3((double)position.getX() + 0.5D, (double)position.getY() + 0.5D, (double)position.getZ() + 0.5D);
      return point.add(offset);
   }

   private Scaffold.BlockCache grab() {
      EnumFacing[] invert = new EnumFacing[]{EnumFacing.UP, EnumFacing.DOWN, EnumFacing.SOUTH, EnumFacing.NORTH, EnumFacing.EAST, EnumFacing.WEST};
      BlockPos position = (new BlockPos(Minecraft.thePlayer.getPositionVector())).offset(EnumFacing.DOWN);
      if(!(Minecraft.theWorld.getBlockState(position).getBlock() instanceof BlockAir)) {
         return null;
      } else {
         for(EnumFacing offsets : EnumFacing.values()) {
            BlockPos offset1 = position.offset(offsets);
            Minecraft.theWorld.getBlockState(offset1);
            if(!(Minecraft.theWorld.getBlockState(offset1).getBlock() instanceof BlockAir)) {
               return new Scaffold.BlockCache(this, offset1, invert[offsets.ordinal()], (Scaffold.BlockCache)null);
            }
         }

         BlockPos[] var16 = new BlockPos[]{new BlockPos(-1, 0, 0), new BlockPos(0, 0, 1), new BlockPos(0, 0, -1), new BlockPos(1, 0, 0)};

         for(BlockPos var17 : var16) {
            BlockPos offsetPos = position.add(var17.getX(), 0, var17.getZ());
            Minecraft.theWorld.getBlockState(offsetPos);
            if(Minecraft.theWorld.getBlockState(offsetPos).getBlock() instanceof BlockAir) {
               for(EnumFacing facing2 : EnumFacing.values()) {
                  BlockPos offset2 = offsetPos.offset(facing2);
                  Minecraft.theWorld.getBlockState(offset2);
                  if(!(Minecraft.theWorld.getBlockState(offset2).getBlock() instanceof BlockAir)) {
                     return new Scaffold.BlockCache(this, offset2, invert[facing2.ordinal()], (Scaffold.BlockCache)null);
                  }
               }
            }
         }

         return null;
      }
   }

   public void getBestBlocks() {
      if(((Boolean)pick.getValue()).booleanValue()) {
         new ItemStack(Item.getItemById(261));
         int bestInvSlot = this.getBiggestBlockSlotInv();
         int bestHotbarSlot = this.getBiggestBlockSlotHotbar();
         int bestSlot = this.getBiggestBlockSlotHotbar() > 0?this.getBiggestBlockSlotHotbar():this.getBiggestBlockSlotInv();
         int spoofSlot = 42;
         if(bestHotbarSlot > 0 && bestInvSlot > 0 && Minecraft.thePlayer.inventoryContainer.getSlot(bestInvSlot).getHasStack() && Minecraft.thePlayer.inventoryContainer.getSlot(bestHotbarSlot).getHasStack() && Minecraft.thePlayer.inventoryContainer.getSlot(bestHotbarSlot).getStack().stackSize < Minecraft.thePlayer.inventoryContainer.getSlot(bestInvSlot).getStack().stackSize) {
            bestSlot = bestInvSlot;
         }

         if(this.hotbarContainBlock()) {
            for(int a = 36; a < 45; ++a) {
               if(Minecraft.thePlayer.inventoryContainer.getSlot(a).getHasStack()) {
                  Item item = Minecraft.thePlayer.inventoryContainer.getSlot(a).getStack().getItem();
                  if(item instanceof ItemBlock && isValid(item)) {
                     spoofSlot = a;
                     break;
                  }
               }
            }
         } else {
            for(int a = 36; a < 45; ++a) {
               if(!Minecraft.thePlayer.inventoryContainer.getSlot(a).getHasStack()) {
                  spoofSlot = a;
                  break;
               }
            }
         }

         if(Minecraft.thePlayer.inventoryContainer.getSlot(spoofSlot).slotNumber != bestSlot) {
            this.swap(bestSlot, spoofSlot - 36);
            Minecraft.playerController.updateController();
         }
      } else if(this.invCheck()) {
         ItemStack is = new ItemStack(Item.getItemById(261));

         for(int i = 9; i < 36; ++i) {
            if(Minecraft.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
               Item item = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack().getItem();
               int count = 0;
               if(item instanceof ItemBlock && isValid(item)) {
                  for(int a = 36; a < 45; ++a) {
                     Container var10000 = Minecraft.thePlayer.inventoryContainer;
                     if(Container.canAddItemToSlot(Minecraft.thePlayer.inventoryContainer.getSlot(a), is, true)) {
                        this.swap(i, a - 36);
                        ++count;
                        break;
                     }
                  }

                  if(count == 0) {
                     this.swap(i, 7);
                  }
                  break;
               }
            }
         }
      }

   }

   protected void swap(int slot, int hotbarNum) {
      Minecraft.playerController.windowClick(Minecraft.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, Minecraft.thePlayer);
   }

   public int getBiggestBlockSlotInv() {
      int slot = -1;
      int size = 0;
      if(getBlockCount() == 0) {
         return -1;
      } else {
         for(int i = 9; i < 36; ++i) {
            if(Minecraft.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
               Item item = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack().getItem();
               ItemStack is = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack();
               if(item instanceof ItemBlock && isValid(item) && is.stackSize > size) {
                  size = is.stackSize;
                  slot = i;
               }
            }
         }

         return slot;
      }
   }

   public int getBiggestBlockSlotHotbar() {
      int slot = -1;
      int size = 0;
      if(getBlockCount() == 0) {
         return -1;
      } else {
         for(int i = 36; i < 45; ++i) {
            if(Minecraft.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
               Item item = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack().getItem();
               ItemStack is = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack();
               if(item instanceof ItemBlock && isValid(item) && is.stackSize > size) {
                  size = is.stackSize;
                  slot = i;
               }
            }
         }

         return slot;
      }
   }

   private boolean hotbarContainBlock() {
      int i = 36;

      while(i < 45) {
         try {
            ItemStack stack = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack();
            if(stack != null && stack.getItem() != null && stack.getItem() instanceof ItemBlock && isValid(stack.getItem())) {
               return true;
            }

            ++i;
         } catch (Exception var3) {
            ;
         }
      }

      return false;
   }

   private int grabBlockSlot() {
      for(int i2 = 0; i2 < 9; ++i2) {
         ItemStack itemStack = Minecraft.thePlayer.inventory.mainInventory[i2];
         if(itemStack != null && itemStack.getItem() instanceof ItemBlock) {
            return i2;
         }
      }

      return -1;
   }

   static class BlockCache {
      private BlockPos position;
      private EnumFacing facing;
      final Scaffold this$0;

      private BlockCache(Scaffold var1, BlockPos position, EnumFacing facing) {
         this.this$0 = var1;
         this.position = position;
         this.facing = facing;
      }

      private BlockPos getPosition() {
         return this.position;
      }

      private EnumFacing getFacing() {
         return this.facing;
      }

      static BlockPos access$0(Scaffold.BlockCache var0) {
         return var0.getPosition();
      }

      static EnumFacing access$1(Scaffold.BlockCache var0) {
         return var0.getFacing();
      }

      static BlockPos access$2(Scaffold.BlockCache var0) {
         return var0.position;
      }

      static EnumFacing access$3(Scaffold.BlockCache var0) {
         return var0.facing;
      }

      BlockCache(Scaffold var1, BlockPos var2, EnumFacing var3, Scaffold.BlockCache var4) {
         this(var1, var2, var3);
      }
   }
}
