package net.minecraft.entity.player.Really.Client.module.modules.combat;

import net.minecraft.entity.player.*;
import net.minecraft.entity.player.Really.Client.api.EventHandler;
import net.minecraft.entity.player.Really.Client.api.events.world.EventPreUpdate;
import net.minecraft.entity.player.Really.Client.api.value.Mode;
import net.minecraft.entity.player.Really.Client.module.Module;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.utils.TimerUtil;
import net.minecraft.client.*;
import net.minecraft.entity.*;

import net.minecraft.entity.item.*;
import java.util.*;

import net.minecraft.client.gui.*;
import net.minecraft.client.network.*;

public class AntiBot extends Module
{
    public Mode mode;
    public static ArrayList<EntityPlayer> Bots;
    private TimerUtil timer;
    int bots;
    
    static {
        AntiBot.Bots = new ArrayList<EntityPlayer>();
    }
    
    public AntiBot() {
        super("AntiBot", new String[] { "AntiBot" }, ModuleType.Combat);
        this.mode = new Mode("Mode", "Mode", AntiMode.values(), AntiMode.Hypixel);
        this.timer = new TimerUtil();
        this.bots = 0;
        this.addValues(this.mode);
    }
    
    @EventHandler
    public void onUpdate(final EventPreUpdate event) {
        this.setSuffix(this.mode.getValue());
        if (this.mode.getValue() == AntiMode.Hypixel) {
            final Minecraft mc2 = AntiBot.mc;
            for (final Object entities : Minecraft.theWorld.loadedEntityList) {
                if (entities instanceof EntityPlayer) {
                    final EntityPlayer entityPlayer2;
                    final EntityPlayer entity = entityPlayer2 = (EntityPlayer)entities;
                    final Minecraft mc3 = AntiBot.mc;
                    if (entityPlayer2 != Minecraft.thePlayer) {
                        final Minecraft mc4 = AntiBot.mc;
                        if (Minecraft.thePlayer.getDistanceToEntity(entity) < 10.0f && (!entity.getDisplayName().getFormattedText().contains("\u5594\u2467\u7afe") || entity.isInvisible() || entity.getDisplayName().getFormattedText().toLowerCase().contains("npc") || entity.getDisplayName().getFormattedText().toLowerCase().contains("\u6402r"))) {
                            AntiBot.Bots.add(entity);
                        }
                    }
                    if (!AntiBot.Bots.contains(entity)) {
                        continue;
                    }
                    AntiBot.Bots.remove(entity);
                }
            }
            this.bots = 0;
            for (final Entity entity2 : Minecraft.theWorld.getLoadedEntityList()) {
                if (entity2 instanceof EntityPlayer) {
                    final EntityPlayer entityPlayer;
                    final EntityPlayer ent = entityPlayer = (EntityPlayer)entity2;
                    final Minecraft mc = AntiBot.mc;
                    if (entityPlayer == Minecraft.thePlayer) {
                        continue;
                    }
                    if (!ent.isInvisible()) {
                        continue;
                    }
                    if (ent.ticksExisted <= 105) {
                        continue;
                    }
                    if (getTabPlayerList().contains(ent)) {
                        if (!ent.isInvisible()) {
                            continue;
                        }
                        ent.setInvisible(false);
                    }
                    else {
                        ent.setInvisible(false);
                        ++this.bots;
                        Minecraft.theWorld.removeEntity(ent);
                    }
                }
            }

        }
    }
    
    public boolean isInGodMode(final Entity entity) {
        return this.isEnabled() && this.mode.getValue() == AntiMode.Hypixel && entity.ticksExisted <= 25;
    }
    
    public boolean isServerBot(final Entity entity) {
        if (this.isEnabled()) {
            if (this.mode.getValue() == AntiMode.Hypixel) {
                return !entity.getDisplayName().getFormattedText().startsWith("§") || entity instanceof EntityArmorStand || entity.getDisplayName().getFormattedText().contains("[NPC]") || this.isInGodMode(entity);
            }
            if (this.mode.getValue() == AntiMode.Mineplex) {
                final Minecraft mc = AntiBot.mc;
                for (final Object object : Minecraft.theWorld.playerEntities) {
                    final EntityPlayer entityPlayer = (EntityPlayer)object;
                    if (entityPlayer != null) {
                        final EntityPlayer entityPlayer2 = entityPlayer;
                        final Minecraft mc2 = AntiBot.mc;
                        if (entityPlayer2 == Minecraft.thePlayer) {
                            continue;
                        }
                        if (!entityPlayer.getName().startsWith("Body #") && entityPlayer.getMaxHealth() != 20.0f) {
                            continue;
                        }
                        return true;
                    }
                }
            }
        }
        return false;
    }
    
    @Override
    public void onEnable() {
        AntiBot.Bots.clear();
    }
    
    @Override
    public void onDisable() {
        AntiBot.Bots.clear();
    }
    
    public static List<EntityPlayer> getTabPlayerList() {
        final Minecraft mc = AntiBot.mc;
        final NetHandlerPlayClient var4 = Minecraft.thePlayer.sendQueue;
        final ArrayList list = new ArrayList();
        final List players = GuiPlayerTabOverlay.field_175252_a.sortedCopy((Iterable)var4.getPlayerInfoMap());
        for (final Object o : players) {
            final NetworkPlayerInfo info = (NetworkPlayerInfo)o;
            if (info == null) {
                continue;
            }
            final ArrayList list2 = list;
            final Minecraft mc2 = AntiBot.mc;
            list2.add(Minecraft.theWorld.getPlayerEntityByName(info.getGameProfile().getName()));
        }
        return (List<EntityPlayer>)list;
    }
    
    enum AntiMode
    {
        Hypixel("Hypixel", 0), 
        Mineplex("Mineplex", 1);
        
        private AntiMode(final String s, final int n) {
        }
    }
}
