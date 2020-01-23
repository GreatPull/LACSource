package net.minecraft.entity.player.Really.Client;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLadder;
import net.minecraft.block.BlockVine;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.Really.Client.api.value.Value;
import net.minecraft.entity.player.Really.Client.management.CommandManager;
import net.minecraft.entity.player.Really.Client.management.FileManager;
import net.minecraft.entity.player.Really.Client.management.FriendManager;
import net.minecraft.entity.player.Really.Client.management.ModuleManager;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.modules.render.UI.TabUI;
import net.minecraft.entity.player.Really.Client.ui.login.AltManager;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;

public class Client {
   public final String name = "Lonely Anti Cracker";
   public final String version = "Release";
   public static boolean publicMode = false;
   public static Client instance = new Client();
   private static ModuleManager modulemanager;
   private CommandManager commandmanager;
   private AltManager altmanager;
   private FriendManager friendmanager;
   private TabUI tabui;
   public static ResourceLocation CLIENT_CAPE = new ResourceLocation("LAC/cape.png");
   public static String FakeName = EnumChatFormatting.AQUA + "[LAC Client]" + EnumChatFormatting.YELLOW + "source leaked by margele";
   public static String ClientName = "LAC Client";
   public static String ClientVersion = "200117";
   public static String lastVer = ClientVersion;
   public static String lastName = ClientName;

   public static BlockPos getBlockCorner(BlockPos start, BlockPos end) {
      for(int x = 0; x <= 1; ++x) {
         for(int y = 0; y <= 1; ++y) {
            for(int z = 0; z <= 1; ++z) {
               BlockPos pos = new BlockPos(end.getX() + x, end.getY() + y, end.getZ() + z);
               if(!isBlockBetween(start, pos)) {
                  return pos;
               }
            }
         }
      }

      return null;
   }

   public static boolean isBlockBetween(BlockPos start, BlockPos end) {
      int startX = start.getX();
      int startY = start.getY();
      int startZ = start.getZ();
      int endX = end.getX();
      int endY = end.getY();
      int endZ = end.getZ();
      double diffX = (double)(endX - startX);
      double diffY = (double)(endY - startY);
      double diffZ = (double)(endZ - startZ);
      double x = (double)startX;
      double y = (double)startY;
      double z = (double)startZ;
      double STEP = 0.1D;
      int STEPS = (int)Math.max(Math.abs(diffX), Math.max(Math.abs(diffY), Math.abs(diffZ))) * 4;

      for(int i = 0; i < STEPS - 1; ++i) {
         x += diffX / (double)STEPS;
         y += diffY / (double)STEPS;
         z += diffZ / (double)STEPS;
         if(x != (double)endX || y != (double)endY || z != (double)endZ) {
            BlockPos pos = new BlockPos(x, y, z);
            Block block = Minecraft.theWorld.getBlockState(pos).getBlock();
            if(block.getMaterial() != Material.air && block.getMaterial() != Material.water && !(block instanceof BlockVine) && !(block instanceof BlockLadder)) {
               return true;
            }
         }
      }

      return false;
   }

   public void initiate() {
      this.commandmanager = new CommandManager();
      this.commandmanager.init();
      this.friendmanager = new FriendManager();
      this.friendmanager.init();
      modulemanager = new ModuleManager();
      modulemanager.init();
      this.tabui = new TabUI();
      this.tabui.init();
      this.altmanager = new AltManager();
      AltManager.init();
      AltManager.setupAlts();
      FileManager.init();
   }

   public static ModuleManager getModuleManager() {
      return modulemanager;
   }

   public CommandManager getCommandManager() {
      return this.commandmanager;
   }

   public AltManager getAltManager() {
      return this.altmanager;
   }

   public void shutDown() {
      String values = "";
      getModuleManager();

      for(Module m : ModuleManager.getModules()) {
         for(Value v : m.getValues()) {
            values = String.valueOf(values) + String.format("%s:%s:%s%s", new Object[]{m.getName(), v.getName(), v.getValue(), System.lineSeparator()});
         }
      }

      FileManager.save("Values.txt", values, false);
      String enabled = "";
      getModuleManager();

      for(Module m : ModuleManager.getModules()) {
         if(m.isEnabled()) {
            enabled = String.valueOf(enabled) + String.format("%s%s", new Object[]{m.getName(), System.lineSeparator()});
         }
      }

      FileManager.save("Enabled.txt", enabled, false);
   }

   public static void RenderRotate(float yaw) {
      Minecraft.thePlayer.renderYawOffset = yaw;
      Minecraft.thePlayer.rotationYawHead = yaw;
   }
}
