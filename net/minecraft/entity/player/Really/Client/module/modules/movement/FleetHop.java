package net.minecraft.entity.player.Really.Client.module.modules.movement;

import net.minecraft.client.*;
import net.minecraft.init.*;
import net.minecraft.network.play.server.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.Really.Client.Client;
import net.minecraft.entity.player.Really.Client.api.*;
import net.minecraft.entity.player.Really.Client.api.events.world.*;
import net.minecraft.entity.player.Really.Client.api.value.*;
import net.minecraft.entity.player.Really.Client.management.*;
import net.minecraft.entity.player.Really.Client.module.*;
import net.minecraft.entity.player.Really.Client.utils.*;
import net.minecraft.entity.player.Really.Client.utils.math.*;
import net.minecraft.potion.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.entity.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.util.Timer;
import net.minecraft.block.*;

public class FleetHop extends Module
{
    public static Mode mode;
    public static Option watercheck;
    private double movementSpeed;
    private double distance;
    public boolean shouldslow;
    public boolean wf;
    double count;
    int jumps;
    boolean collided;
    boolean lessSlow;
    int spoofSlot;
    double less;
    double stair;
    private TimerUtil timer;
    private int counter;
    private int stage;
    private double posY;
    private int tick;
    private int speedTick;
    private boolean firstjump;
    private boolean legitHop;
    private double delay1;
    private double delay2;
    int delay;
    private boolean boost;
    private SpeedTimer timerino;
    public static boolean speed;
    private int setback;
    private int timeState;
    public int state;
    private int cooldownHops;
    private float i;
    private boolean wasOnWater;
    private boolean doTime;
    private double delay3;
    private double hAllowedDistance;
    private int JUMPTicks;
    private int level;
    private double moveSpeed;
    private double lastDist;
    private boolean b2;
    private boolean b3;
    private boolean glide;
    private boolean slowFall;
    private int stag;
    int steps;
    Random rnd;
    
    static {
        FleetHop.mode = new Mode("Mode", "mode", (Enum[])FleetSpeedMode.values(), (Enum)FleetSpeedMode.Hypixel);
        FleetHop.watercheck = new Option("WaterCheck", "WaterCheck", false);
    }
    
    public FleetHop() {
        super("Bhopper", new String[] { "zoom" }, ModuleType.Movement);
        this.shouldslow = false;
        this.wf = true;
        this.count = 0.0;
        this.collided = false;
        this.spoofSlot = 0;
        this.timer = new TimerUtil();
        this.counter = 0;
        this.stage = 1;
        this.delay1 = 0.0;
        this.delay2 = 0.0;
        this.timerino = new SpeedTimer();
        this.delay3 = 1.7;
        this.hAllowedDistance = 0.2873000087011036;
        this.glide = false;
        this.slowFall = false;
        this.stag = 0;
        this.addValues(FleetHop.mode, FleetHop.watercheck);
    }
    
    public void onEnable() {

        this.b2 = FleetHop.mc.gameSettings.viewBobbing;
        this.timerino.reset();
        this.level = 1;
        final Timer timer = FleetHop.mc.timer;
        Timer.timerSpeed = 1.0f;
        this.stag = 0;
        this.delay3 = 0.0;
        final Minecraft mc = FleetHop.mc;
        this.moveSpeed = ((Minecraft.thePlayer == null) ? 0.2873 : this.getBaseMoveSpeed());
        this.b3 = true;
        super.onEnable();
    }
    

    public void onDisable() {
        final Timer timer = FleetHop.mc.timer;
        Timer.timerSpeed = 1.0f;
        this.counter = 0;
        this.delay3 = 0.0;
        this.delay2 = 0.0;
        this.delay1 = 0.0;
        Timer.timerSpeed = 1.0f;
        this.slowFall = false;
        this.moveSpeed = this.getBaseMoveSpeed();
        this.b2 = true;
        this.glide = false;
        this.level = 0;
        this.posY = 0.0;
        final Timer timer2 = FleetHop.mc.timer;
        Timer.timerSpeed = 1.0f;
        this.tick = 0;
        this.legitHop = true;
        this.firstjump = false;
        final Minecraft mc = FleetHop.mc;
        Minecraft.thePlayer.speedInAir = 0.02f;
        this.speedTick = 0;
        Blocks.packed_ice.slipperiness = 0.98f;
        Blocks.ice.slipperiness = 0.98f;
        this.timerino.reset();
        FleetHop.mc.gameSettings.viewBobbing = this.b2;
        super.onDisable();
    }
    
    private boolean canZoom() {
        final Minecraft mc = FleetHop.mc;
        if (Minecraft.thePlayer.moving()) {
            final Minecraft mc2 = FleetHop.mc;
            if (Minecraft.thePlayer.onGround) {
                return true;
            }
        }
        return false;
    }
    
    @EventHandler
    public void onPacketGet(final EventPacketRecieve e) {
        if (FleetHop.mode.getValue() == FleetSpeedMode.HypSpeed && e.getPacket() instanceof S08PacketPlayerPosLook) {
            this.stage = -2;
        }
    }
    
    @EventHandler
    public void onPost(final EventPostUpdate e) {
        if (FleetHop.mode.getValue() == FleetSpeedMode.HypSpeed) {
            if (this.glide) {
                final Minecraft mc = FleetHop.mc;
                if (Minecraft.thePlayer.fallDistance <= 0.475 && PlayerUtil.MovementInput()) {
                    final Minecraft mc2 = FleetHop.mc;
                    Minecraft.thePlayer.motionY = -0.005;
                }
            }
            final Minecraft mc3 = FleetHop.mc;
            final double posX = Minecraft.thePlayer.posX;
            final Minecraft mc4 = FleetHop.mc;
            final double xDist = posX - Minecraft.thePlayer.prevPosX;
            final Minecraft mc5 = FleetHop.mc;
            final double posZ = Minecraft.thePlayer.posZ;
            final Minecraft mc6 = FleetHop.mc;
            final double zDist = posZ - Minecraft.thePlayer.prevPosZ;
            this.lastDist = Math.sqrt(xDist * xDist + zDist * zDist);
        }
    }
    
    @EventHandler
    private void onUpdate(final EventPreUpdate e) {
		Scaffold i3 = (Scaffold)Client.instance.getModuleManager().getModuleByName("Scaffold");
		if(i3.isEnabled() && (boolean) i3.Lag.getValue()) {
			return;
		}
        this.setSuffix(FleetHop.mode.getValue());
        if ((boolean)FleetHop.watercheck.getValue()) {
            final Minecraft mc = FleetHop.mc;
            if (Minecraft.thePlayer.isInWater()) {
                if (this.wf) {
                    Helper.sendMessage("You in the Water!");
                    this.wf = false;
                }
                return;
            }
        }
        if (ModuleManager.getModuleByName("Flight").isEnabled() || ModuleManager.getModuleByName("SkyRun").isEnabled() || ModuleManager.getModuleByName("BoosterHypixel").isEnabled()) {
            return;
        }
        this.wf = true;
        if (FleetHop.mode.getValue() == FleetSpeedMode.Hypixel) {
            final Minecraft mc2 = FleetHop.mc;
            final double posX = Minecraft.thePlayer.posX;
            final Minecraft mc3 = FleetHop.mc;
            final double xDist = posX - Minecraft.thePlayer.prevPosX;
            final Minecraft mc4 = FleetHop.mc;
            final double posZ = Minecraft.thePlayer.posZ;
            final Minecraft mc5 = FleetHop.mc;
            final double zDist = posZ - Minecraft.thePlayer.prevPosZ;
            this.distance = Math.sqrt(xDist * xDist + zDist * zDist);
        }
        if (FleetHop.mode.getValue() == FleetSpeedMode.HypSpeed) {
            if (!this.canSpeed()) {
                this.delay2 = -2.0;
                Timer.timerSpeed = 1.0f;
            }
            final Minecraft mc6 = FleetHop.mc;
            Label_0297: {
                Label_0257: {
                    if (!Minecraft.thePlayer.isSneaking()) {
                        final Minecraft mc7 = FleetHop.mc;
                        if (!Minecraft.thePlayer.isCollidedHorizontally) {
                            final Minecraft mc8 = FleetHop.mc;
                            if (Minecraft.thePlayer.motionX != 0.0) {
                                final Minecraft mc9 = FleetHop.mc;
                                if (!Minecraft.thePlayer.isOnLadder()) {
                                    if (!ModuleManager.getModuleByName("NoSlowDown").isEnabled()) {
                                        final Minecraft mc10 = FleetHop.mc;
                                        if (Minecraft.thePlayer.isBlocking()) {
                                            break Label_0257;
                                        }
                                    }
                                    final Minecraft mc11 = FleetHop.mc;
                                    if (Minecraft.thePlayer.moveForward > 0.0f) {
                                        final Minecraft mc12 = FleetHop.mc;
                                        Minecraft.thePlayer.setSprinting(true);
                                    }
                                    break Label_0297;
                                }
                            }
                        }
                    }
                }
                final Minecraft mc13 = FleetHop.mc;
                Minecraft.thePlayer.setSprinting(false);
            }
            this.setSpeed(getSpeed());
        }
        if (FleetHop.mode.getValue() == FleetSpeedMode.Onground && this.canZoom()) {
            switch (this.stage) {
                case 1: {
                    e.setY(e.getY() + 0.4);
                    e.setOnground(false);
                    ++this.stage;
                    break;
                }
                case 2: {
                    e.setY(e.getY() + 0.4);
                    e.setOnground(false);
                    ++this.stage;
                    break;
                }
                default: {
                    this.stage = 1;
                    break;
                }
            }
        }
    }
    
    @EventHandler
    private void onMove(final EventMove e) {
		Scaffold i3 = (Scaffold)Client.instance.getModuleManager().getModuleByName("Scaffold");
		if(i3.isEnabled() && (boolean)i3.Lag.getValue()) {
			return;
		}
    	if ((boolean)FleetHop.watercheck.getValue()) {
            final Minecraft mc = FleetHop.mc;
            if (Minecraft.thePlayer.isInWater()) {
                this.level = 1;
                this.timerino.reset();
                return;
            }
        }
        if (!PlayerUtil.MovementInput()) {
            this.level = 1;
            this.timerino.reset();
            return;
        }
        if (FleetHop.mode.getValue() == FleetSpeedMode.HypixelFast && !this.isInLiquid()) {
            if (this.canZoom()) {
                this.moveSpeed = this.getBaseMoveSpeed();
            }
            if (this.stage == 1) {
                final Minecraft mc2 = FleetHop.mc;
                if (Minecraft.thePlayer.isCollidedVertically && this.canZoom()) {
                    this.moveSpeed = 1.5 + this.getBaseMoveSpeed() - 0.01;
                }
            }
            if (this.canZoom() && this.stage == 2) {
                final double jump = 0.418;
                final Minecraft mc3 = FleetHop.mc;
                EventMove.setY(Minecraft.thePlayer.motionY = jump);
                this.moveSpeed *= 1.89;
            }
            else if (this.stage == 3) {
                final double diff = 0.66 * (this.distance - this.getBaseMoveSpeed());
                this.moveSpeed = this.distance - diff;
            }
            else {
                final Minecraft mc4 = FleetHop.mc;
                final WorldClient theWorld = Minecraft.theWorld;
                final Minecraft mc5 = FleetHop.mc;
                final EntityPlayerSP thePlayer = Minecraft.thePlayer;
                final Minecraft mc6 = FleetHop.mc;
                final AxisAlignedBB boundingBox = Minecraft.thePlayer.boundingBox;
                final double x2 = 0.0;
                final Minecraft mc7 = FleetHop.mc;
                final List collidingList = theWorld.getCollidingBoundingBoxes(thePlayer, boundingBox.offset(x2, Minecraft.thePlayer.motionY, 0.0));
                Label_0329: {
                    if (collidingList.size() <= 0) {
                        final Minecraft mc8 = FleetHop.mc;
                        if (!Minecraft.thePlayer.isCollidedVertically) {
                            break Label_0329;
                        }
                    }
                    if (this.stage > 0) {
                        final Minecraft mc9 = FleetHop.mc;
                        this.stage = (Minecraft.thePlayer.moving() ? 1 : 0);
                    }
                }
                this.moveSpeed = this.getBaseMoveSpeed() * 1.00000011920929;
                this.moveSpeed = this.distance - this.distance / 159.0;
            }
            this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
            final Minecraft mc10 = FleetHop.mc;
            Minecraft.thePlayer.setMoveSpeed(e, this.moveSpeed);
            if (this.stage > 0) {
                this.setMotion(e, this.moveSpeed);
            }
            final MovementInput movementInput2 = Minecraft.thePlayer.movementInput;
            float forward = MovementInput.moveForward;
            final MovementInput movementInput3 = Minecraft.thePlayer.movementInput;
            float strafe = MovementInput.moveStrafe;
            float yaw = Minecraft.thePlayer.rotationYaw;
            if (forward == 0.0f && strafe == 0.0f) {
                final EntityPlayerSP thePlayer2 = Minecraft.thePlayer;
                final double motionX = thePlayer2.motionX;
                final EntityPlayerSP thePlayer3 = Minecraft.thePlayer;
                final double motionZ = thePlayer3.motionZ * 0.0;
                thePlayer3.motionZ = motionZ;
                thePlayer2.motionX = motionX * motionZ;
                EventMove.x = 0.0;
                EventMove.z = 0.0;
            }
            else if (forward != 0.0f) {
                if (strafe >= 1.0f) {
                    yaw += ((forward > 0.0f) ? -45.0f : 45.0f);
                    strafe = 0.0f;
                }
                else if (strafe <= -1.0f) {
                    yaw += ((forward > 0.0f) ? 45.0f : -45.0f);
                    strafe = 0.0f;
                }
                if (forward > 0.0f) {
                    forward = 1.0f;
                }
                else if (forward < 0.0f) {
                    forward = -1.0f;
                }
            }
            if (forward == 0.0f && strafe == 0.0f) {
                EventMove.x = 0.0;
                EventMove.z = 0.0;
                final EntityPlayerSP thePlayer4 = Minecraft.thePlayer;
                final double motionX2 = thePlayer4.motionX;
                final EntityPlayerSP thePlayer5 = Minecraft.thePlayer;
                final double motionZ2 = thePlayer5.motionZ * 0.0;
                thePlayer5.motionZ = motionZ2;
                thePlayer4.motionX = motionX2 * motionZ2;
            }
            else if (forward != 0.0f) {
                if (strafe >= 1.0f) {
                    final float var10000 = yaw + ((forward > 0.0f) ? -45.0f : 45.0f);
                    strafe = 0.0f;
                }
                else if (strafe <= -1.0f) {
                    final float var10000 = yaw + ((forward > 0.0f) ? 45.0f : -45.0f);
                    strafe = 0.0f;
                }
                if (forward > 0.0f) {
                    forward = 1.0f;
                }
                else if (forward < 0.0f) {
                    forward = -1.0f;
                }
            }
            ++this.stage;
        }
        if (FleetHop.mode.getValue() == FleetSpeedMode.HypSpeed) {
            final Minecraft mc11 = FleetHop.mc;
            final MovementInput movementInput = Minecraft.thePlayer.movementInput;
            float forward2 = MovementInput.moveForward;
            float strafe2 = MovementInput.moveStrafe;
            final Minecraft mc12 = FleetHop.mc;
            float yaw2 = Minecraft.thePlayer.rotationYaw;
            if (forward2 == 0.0f && strafe2 == 0.0f) {
                EventMove.x = 0.0;
                EventMove.z = 0.0;
            }
            else if (forward2 != 0.0f) {
                if (strafe2 >= 1.0f) {
                    yaw2 += ((forward2 > 0.0f) ? -45 : 45);
                    strafe2 = 0.0f;
                }
                else if (strafe2 <= -1.0f) {
                    yaw2 += ((forward2 > 0.0f) ? 45 : -45);
                    strafe2 = 0.0f;
                }
                if (forward2 > 0.0f) {
                    forward2 = 1.0f;
                }
                else if (forward2 < 0.0f) {
                    forward2 = -1.0f;
                }
            }
            final double mx = Math.cos(Math.toRadians(yaw2 + 90.0f));
            final double mz = Math.sin(Math.toRadians(yaw2 + 90.0f));
            Label_0967: {
                if (!FleetHop.mc.gameSettings.keyBindLeft.isPressed() && !FleetHop.mc.gameSettings.keyBindRight.isPressed()) {
                    if (!FleetHop.mc.gameSettings.keyBindBack.isPressed()) {
                        break Label_0967;
                    }
                    final Minecraft mc13 = FleetHop.mc;
                    if (!Minecraft.thePlayer.moving()) {
                        break Label_0967;
                    }
                }
                this.setSpeed(0.10000000149011612);
            }
            Label_1122: {
                Label_1037: {
                    if (this.stage == 2) {
                        final Minecraft mc14 = FleetHop.mc;
                        if (Minecraft.thePlayer.moveForward == 0.0f) {
                            final Minecraft mc15 = FleetHop.mc;
                            if (Minecraft.thePlayer.moveStrafing == 0.0f) {
                                break Label_1037;
                            }
                        }
                        final Minecraft mc16 = FleetHop.mc;
                        final EntityPlayerSP thePlayer6 = Minecraft.thePlayer;
                        final double n = 0.399996969;
                        thePlayer6.motionY = n;
                        EventMove.y = n;
                        this.moveSpeed *= 2.14999;
                        break Label_1122;
                    }
                }
                if (this.stage == 3) {
                    final Minecraft mc17 = FleetHop.mc;
                    this.moveSpeed = (Minecraft.thePlayer.isPotionActive(Potion.moveSpeed) ? (this.getBaseMoveSpeed() * 1.59999999) : (this.getBaseMoveSpeed() * 1.379999999));
                }
                else {
                    final Minecraft mc18 = FleetHop.mc;
                    if (Minecraft.thePlayer.onGround) {
                        this.stage = 1;
                    }
                    this.moveSpeed = this.lastDist - this.lastDist / 155.7;
                }
            }
            this.moveSpeed = Math.max(this.moveSpeed, this.getBaseMoveSpeed());
            if (forward2 == 0.0f && strafe2 == 0.0f) {
                EventMove.x = 0.0;
                EventMove.z = 0.0;
            }
            else if (forward2 != 0.0f) {
                if (strafe2 >= 1.0f) {
                    yaw2 += ((forward2 > 0.0f) ? -40 : 40);
                    strafe2 = 0.0f;
                }
                else if (strafe2 <= -1.0f) {
                    yaw2 += ((forward2 > 0.0f) ? 40 : -40);
                    strafe2 = 0.0f;
                }
                if (forward2 > 0.0f) {
                    forward2 = 1.0f;
                }
                else if (forward2 < 0.0f) {
                    forward2 = -1.0f;
                }
            }
            final double mx2 = Math.cos(Math.toRadians(yaw2 + 90.0f));
            final double mz2 = Math.sin(Math.toRadians(yaw2 + 90.0f));
            EventMove.x = (forward2 * this.moveSpeed * mx2 + strafe2 * this.moveSpeed * mz2) * 0.99;
            EventMove.z = (forward2 * this.moveSpeed * mz2 - strafe2 * this.moveSpeed * mx2) * 0.99;
            final Minecraft mc19 = FleetHop.mc;
            Minecraft.thePlayer.stepHeight = 0.5f;
            if (forward2 == 0.0f && strafe2 == 0.0f) {
                EventMove.x = 0.0;
                EventMove.z = 0.0;
            }
            else if (forward2 != 0.0f) {
                if (strafe2 >= 1.0f) {
                    yaw2 += ((forward2 > 0.0f) ? -40 : 40);
                    strafe2 = 0.0f;
                }
                else if (strafe2 <= -1.0f) {
                    yaw2 += ((forward2 > 0.0f) ? 40 : -40);
                    strafe2 = 0.0f;
                }
                if (forward2 > 0.0f) {
                    forward2 = 1.0f;
                }
                else if (forward2 < 0.0f) {
                    forward2 = -1.0f;
                }
            }
            ++this.stage;
        }
        if (FleetHop.mode.getValue() == FleetSpeedMode.ChinaZZHop) {
            final Minecraft mc20 = FleetHop.mc;
            if (!Minecraft.thePlayer.moving()) {
                final Timer timer = FleetHop.mc.timer;
                Timer.timerSpeed = 1.0f;
                return;
            }
            final Minecraft mc21 = FleetHop.mc;
            if (Minecraft.thePlayer.moving()) {
                ++this.stage;
            }
            if (this.stage > 4) {
                this.stage = 1;
            }
            if (this.canZoom() && this.stage == 1) {
                final float[] ez = { 0.08f, 0.093160905f, 1.6f, 2.049f, 0.66f };
                this.movementSpeed = 1.7442 * MathUtil.getBaseMovementSpeed();
                try {
                    final Timer timer2 = FleetHop.mc.timer;
                    final Minecraft mc22 = FleetHop.mc;
                    Timer.timerSpeed = (float)((Minecraft.thePlayer.ticksExisted % 2 == 0) ? 1.06 : (1.02 + this.rnd.nextInt(2)));
                }
                catch (Exception x) {
                    final Timer timer3 = FleetHop.mc.timer;
                    Timer.timerSpeed = 1.0f;
                }
            }
            else if (this.canZoom() && this.stage == 2) {
                final double jumpt = 0.4123;
                final Minecraft mc23 = FleetHop.mc;
                Minecraft.thePlayer.motionY = jumpt;
                final Minecraft mc24 = FleetHop.mc;
                EventMove.setY(Minecraft.thePlayer.motionY);
                this.movementSpeed *= 1.7;
                final Timer timer4 = FleetHop.mc.timer;
                final Minecraft mc25 = FleetHop.mc;
                Timer.timerSpeed = (float)(Minecraft.thePlayer.motionY * 1.5);
            }
            else if (this.stage == 3) {
                final double difference = 0.66 * (this.distance - MathUtil.getBaseMovementSpeed());
                this.movementSpeed = this.distance - difference;
                final Timer timer5 = FleetHop.mc.timer;
                Timer.timerSpeed = 1.02f;
            }
            else {
                final Minecraft mc26 = FleetHop.mc;
                final WorldClient theWorld2 = Minecraft.theWorld;
                final Minecraft mc27 = FleetHop.mc;
                final EntityPlayerSP thePlayer7 = Minecraft.thePlayer;
                final Minecraft mc28 = FleetHop.mc;
                final AxisAlignedBB boundingBox2 = Minecraft.thePlayer.boundingBox;
                final double x3 = 0.0;
                final Minecraft mc29 = FleetHop.mc;
                final List collidingList = theWorld2.getCollidingBoundingBoxes(thePlayer7, boundingBox2.offset(x3, Minecraft.thePlayer.motionY, 0.0));
                Label_1896: {
                    if (collidingList.size() <= 0) {
                        final Minecraft mc30 = FleetHop.mc;
                        if (!Minecraft.thePlayer.isCollidedVertically || this.stage <= 0) {
                            break Label_1896;
                        }
                    }
                    final Minecraft mc31 = FleetHop.mc;
                    this.stage = (Minecraft.thePlayer.moving() ? 1 : 0);
                }
                this.movementSpeed = this.distance - this.distance / 159.0;
            }
            this.movementSpeed = Math.max(this.movementSpeed, MathUtil.getBaseMovementSpeed());
            final Minecraft mc32 = FleetHop.mc;
            Minecraft.thePlayer.setMoveSpeed(e, this.movementSpeed);
        }
        if (FleetHop.mode.getValue() == FleetSpeedMode.Hypixel) {
            if (this.canZoom() && this.stage == 1) {
                this.movementSpeed = 1.56 * MathUtil.getBaseMovementSpeed() - 0.01;
                final Timer timer6 = FleetHop.mc.timer;
                Timer.timerSpeed = 1.15f;
            }
            else if (this.canZoom() && this.stage == 2) {
                final Minecraft mc33 = FleetHop.mc;
                EventMove.setY(Minecraft.thePlayer.motionY = 0.3999);
                this.movementSpeed *= 1.58;
                final Timer timer7 = FleetHop.mc.timer;
                Timer.timerSpeed = 1.2f;
            }
            else if (this.stage == 3) {
                final double difference = 0.66 * (this.distance - MathUtil.getBaseMovementSpeed());
                this.movementSpeed = this.distance - difference;
                final Timer timer8 = FleetHop.mc.timer;
                Timer.timerSpeed = 1.1f;
            }
            else {
                final Minecraft mc34 = FleetHop.mc;
                final WorldClient theWorld3 = Minecraft.theWorld;
                final Minecraft mc35 = FleetHop.mc;
                final EntityPlayerSP thePlayer8 = Minecraft.thePlayer;
                final Minecraft mc36 = FleetHop.mc;
                final AxisAlignedBB boundingBox3 = Minecraft.thePlayer.boundingBox;
                final double x4 = 0.0;
                final Minecraft mc37 = FleetHop.mc;
                final List collidingList = theWorld3.getCollidingBoundingBoxes(thePlayer8, boundingBox3.offset(x4, Minecraft.thePlayer.motionY, 0.0));
                Label_2203: {
                    if (collidingList.size() <= 0) {
                        final Minecraft mc38 = FleetHop.mc;
                        if (!Minecraft.thePlayer.isCollidedVertically || this.stage <= 0) {
                            break Label_2203;
                        }
                    }
                    final Minecraft mc39 = FleetHop.mc;
                    this.stage = (Minecraft.thePlayer.moving() ? 1 : 0);
                }
                this.movementSpeed = this.distance - this.distance / 159.0;
            }
            this.movementSpeed = Math.max(this.movementSpeed, MathUtil.getBaseMovementSpeed());
            final Minecraft mc40 = FleetHop.mc;
            Minecraft.thePlayer.setMoveSpeed(e, this.movementSpeed);
            final Timer timer9 = FleetHop.mc.timer;
            Timer.timerSpeed = 1.15f;
            final Minecraft mc41 = FleetHop.mc;
            if (Minecraft.thePlayer.moving()) {
                ++this.stage;
            }
        }
        Label_2767: {
            if (FleetHop.mode.getValue() == FleetSpeedMode.NewHop) {
                final Minecraft mc42 = FleetHop.mc;
                if (Minecraft.thePlayer.isCollidedHorizontally) {
                    this.collided = true;
                }
                if (this.collided) {
                    final Timer timer10 = FleetHop.mc.timer;
                    Timer.timerSpeed = 1.0f;
                    this.stage = -1;
                }
                if (this.stair > 0.0) {
                    this.stair -= 0.25;
                }
                this.less -= ((this.less > 1.0) ? 0.12 : 0.11);
                if (this.less < 0.0) {
                    this.less = 0.0;
                }
                final Minecraft mc43 = FleetHop.mc;
                if (!Minecraft.thePlayer.isInWater()) {
                    final Minecraft mc44 = FleetHop.mc;
                    if (Minecraft.thePlayer.onGround) {
                        final Minecraft mc45 = FleetHop.mc;
                        if (Minecraft.thePlayer.moving()) {
                            final Minecraft mc46 = FleetHop.mc;
                            this.collided = Minecraft.thePlayer.isCollidedHorizontally;
                            if (this.stage >= 0 || this.collided) {
                                this.stage = 0;
                                final double motY = 0.407 + MoveUtils.getJumpEffect() * 0.1;
                                if (this.stair == 0.0) {
                                    final Minecraft mc47 = FleetHop.mc;
                                    Minecraft.thePlayer.jump();
                                    final Minecraft mc48 = FleetHop.mc;
                                    EventMove.setY(Minecraft.thePlayer.motionY = motY);
                                }
                                ++this.less;
                                if (this.less > 1.0 && !this.lessSlow) {
                                    this.lessSlow = true;
                                }
                                else {
                                    this.lessSlow = false;
                                }
                                if (this.less > 1.12) {
                                    this.less = 1.12;
                                }
                            }
                        }
                    }
                }
                Minecraft.getMinecraft();
                this.movementSpeed = (Minecraft.thePlayer.isPotionActive(Potion.moveSpeed) ? (this.getHypixelSpeed(this.stage) - 0.065) : (this.getHypixelSpeed(this.stage) + 0.0331));
                this.movementSpeed *= 0.91;
                if (this.stair > 0.0) {
                    this.movementSpeed *= 0.7 - MoveUtils.getSpeedEffect() * 0.1;
                }
                if (this.stage < 0) {
                    this.movementSpeed = MoveUtils.defaultSpeed();
                }
                if (this.lessSlow) {
                    this.movementSpeed *= 0.95;
                }
                final Minecraft mc49 = FleetHop.mc;
                if (Minecraft.thePlayer.isInWater()) {
                    this.movementSpeed = 0.12;
                }
                final Minecraft mc50 = FleetHop.mc;
                if (Minecraft.thePlayer.moveForward == 0.0f) {
                    final Minecraft mc51 = FleetHop.mc;
                    if (Minecraft.thePlayer.moveStrafing == 0.0f) {
                        break Label_2767;
                    }
                }
                this.setMotion(e, this.movementSpeed);
                ++this.stage;
            }
        }
        if (FleetHop.mode.getValue() == FleetSpeedMode.Onground && this.canZoom()) {
            switch (this.stage) {
                case 1: {
                    final Timer timer11 = FleetHop.mc.timer;
                    Timer.timerSpeed = 1.22f;
                    this.movementSpeed = 1.89 * MathUtil.getBaseMovementSpeed() - 0.01;
                    ++this.distance;
                    if (this.distance == 1.0) {
                        EventMove.setY(e.getY() + 8.0E-6);
                        break;
                    }
                    if (this.distance != 2.0) {
                        break;
                    }
                    EventMove.setY(e.getY() - 8.0E-6);
                    this.distance = 0.0;
                    break;
                }
                case 2: {
                    this.movementSpeed = 1.2 * MathUtil.getBaseMovementSpeed() - 0.01;
                    break;
                }
                default: {
                    this.movementSpeed = (float)MathUtil.getBaseMovementSpeed();
                    break;
                }
            }
            this.movementSpeed = Math.max(this.movementSpeed, MathUtil.getBaseMovementSpeed());
            final Minecraft mc52 = FleetHop.mc;
            Minecraft.thePlayer.setMoveSpeed(e, this.movementSpeed);
            ++this.stage;
        }
        if (FleetHop.mode.getValue() == FleetSpeedMode.Bhop) {
            final Timer timer12 = FleetHop.mc.timer;
            Timer.timerSpeed = 1.07f;
            if (this.canZoom() && this.stage == 1) {
                this.movementSpeed = 2.55 * MathUtil.getBaseMovementSpeed() - 0.01;
            }
            else if (this.canZoom() && this.stage == 2) {
                final Minecraft mc53 = FleetHop.mc;
                EventMove.setY(Minecraft.thePlayer.motionY = 0.3999);
                this.movementSpeed *= 2.1;
            }
            else if (this.stage == 3) {
                final double difference = 0.66 * (this.distance - MathUtil.getBaseMovementSpeed());
                this.movementSpeed = this.distance - difference;
            }
            else {
                final Minecraft mc54 = FleetHop.mc;
                final WorldClient theWorld4 = Minecraft.theWorld;
                final Minecraft mc55 = FleetHop.mc;
                final EntityPlayerSP thePlayer9 = Minecraft.thePlayer;
                final Minecraft mc56 = FleetHop.mc;
                final AxisAlignedBB boundingBox4 = Minecraft.thePlayer.boundingBox;
                final double x5 = 0.0;
                final Minecraft mc57 = FleetHop.mc;
                final List collidingList = theWorld4.getCollidingBoundingBoxes(thePlayer9, boundingBox4.offset(x5, Minecraft.thePlayer.motionY, 0.0));
                Label_3207: {
                    if (collidingList.size() <= 0) {
                        final Minecraft mc58 = FleetHop.mc;
                        if (!Minecraft.thePlayer.isCollidedVertically || this.stage <= 0) {
                            break Label_3207;
                        }
                    }
                    final Minecraft mc59 = FleetHop.mc;
                    this.stage = (Minecraft.thePlayer.moving() ? 1 : 0);
                }
                this.movementSpeed = this.distance - this.distance / 159.0;
            }
            this.movementSpeed = Math.max(this.movementSpeed, MathUtil.getBaseMovementSpeed());
            final Minecraft mc60 = FleetHop.mc;
            Minecraft.thePlayer.setMoveSpeed(e, this.movementSpeed);
            final Minecraft mc61 = FleetHop.mc;
            if (Minecraft.thePlayer.moving()) {
                ++this.stage;
            }
        }
    }
    
    private void setMotion(final EventMove em, final double speed) {
        final Minecraft mc = FleetHop.mc;
        final MovementInput movementInput = Minecraft.thePlayer.movementInput;
        double forward = MovementInput.moveForward;
        final Minecraft mc2 = FleetHop.mc;
        final MovementInput movementInput2 = Minecraft.thePlayer.movementInput;
        double strafe = MovementInput.moveStrafe;
        final Minecraft mc3 = FleetHop.mc;
        float yaw = Minecraft.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            EventMove.setX(0.0);
            EventMove.setZ(0.0);
        }
        else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                }
                else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                }
                else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            EventMove.setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0f)) + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)));
            EventMove.setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0f)) - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)));
        }
    }
    
    private double getHypixelSpeed(final int stage) {
        double value = MoveUtils.defaultSpeed() + 0.028 * MoveUtils.getSpeedEffect() + MoveUtils.getSpeedEffect() / 15.0;
        final double firstvalue = 0.4145 + MoveUtils.getSpeedEffect() / 12.5;
        final double decr = stage / 500.0 * 2.0;
        if (stage == 0) {
            if (this.timer.delay(300.0f)) {
                this.timer.reset();
            }
            value = 0.64 + (MoveUtils.getSpeedEffect() + 0.028 * MoveUtils.getSpeedEffect()) * 0.134;
        }
        else if (stage == 1) {
            final Timer timer = FleetHop.mc.timer;
            final float timerSpeed = Timer.timerSpeed;
            value = firstvalue;
        }
        else if (stage >= 2) {
            final Timer timer2 = FleetHop.mc.timer;
            final float timerSpeed2 = Timer.timerSpeed;
            value = firstvalue - decr;
        }
        return Math.max(value, this.shouldslow ? value : (MoveUtils.defaultSpeed() + 0.028 * MoveUtils.getSpeedEffect()));
    }
    
    private void strafe(final float speed) {
        final Minecraft mc = FleetHop.mc;
        boolean b = false;
        Label_0035: {
            if (Minecraft.thePlayer.moveForward == 0.0f) {
                final Minecraft mc2 = FleetHop.mc;
                if (Minecraft.thePlayer.moveStrafing == 0.0f) {
                    b = false;
                    break Label_0035;
                }
            }
            b = true;
        }
        final boolean isMoving = b;
        final Minecraft mc3 = FleetHop.mc;
        final boolean isMovingForward = Minecraft.thePlayer.moveForward > 0.0f;
        final Minecraft mc4 = FleetHop.mc;
        final boolean isMovingBackward = Minecraft.thePlayer.moveForward < 0.0f;
        final Minecraft mc5 = FleetHop.mc;
        final boolean isMovingRight = Minecraft.thePlayer.moveStrafing > 0.0f;
        final Minecraft mc6 = FleetHop.mc;
        final boolean isMovingLeft = Minecraft.thePlayer.moveStrafing < 0.0f;
        final boolean isMovingSideways = isMovingLeft || isMovingRight;
        final boolean bl;
        final boolean isMovingStraight = bl = (isMovingForward || isMovingBackward);
        if (!isMoving) {
            return;
        }
        final Minecraft mc7 = FleetHop.mc;
        double yaw = Minecraft.thePlayer.rotationYaw;
        if (isMovingForward && !isMovingSideways) {
            yaw += 0.0;
        }
        else if (isMovingBackward && !isMovingSideways) {
            yaw += 180.0;
        }
        else if (isMovingForward && isMovingLeft) {
            yaw += 45.0;
        }
        else if (isMovingForward) {
            yaw -= 45.0;
        }
        else if (!isMovingStraight && isMovingLeft) {
            yaw += 90.0;
        }
        else if (!isMovingStraight && isMovingRight) {
            yaw -= 90.0;
        }
        else if (isMovingBackward && isMovingLeft) {
            yaw += 135.0;
        }
        else if (isMovingBackward) {
            yaw -= 135.0;
        }
        yaw = Math.toRadians(yaw);
        final Minecraft mc8 = FleetHop.mc;
        Minecraft.thePlayer.motionX = -Math.sin(yaw) * speed;
        final Minecraft mc9 = FleetHop.mc;
        Minecraft.thePlayer.motionZ = Math.cos(yaw) * speed;
    }
    
    public void clear() {
        this.delay3 = 0.0;
        this.delay2 = 0.0;
        this.delay1 = 0.0;
        Timer.timerSpeed = 1.0f;
        this.moveSpeed = this.getBaseMoveSpeed();
        this.b2 = true;
        this.glide = false;
        this.level = 0;
        Blocks.packed_ice.slipperiness = 0.98f;
        Blocks.ice.slipperiness = 0.98f;
    }
    
    private double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        final Minecraft mc = FleetHop.mc;
        if (Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
            final Minecraft mc2 = FleetHop.mc;
            final int amplifier = Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }
    
    private double getHypixelBaseMoveSpeed() {
        double baseSpeed = 0.28630000352859497;
        final Minecraft mc = FleetHop.mc;
        if (Minecraft.thePlayer.isPotionActive(Potion.moveSpeed)) {
            final Minecraft mc2 = FleetHop.mc;
            final int amplifier = Minecraft.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1) - GetRandomNumber(-9999, 9999) / 10000;
        }
        return baseSpeed;
    }
    
    private static int GetRandomNumber(final int n, final int n2) {
        return (int)(Math.random() * (n - n2)) + n2;
    }
    
    public double getJump() {
        double baseJump = 0.416565;
        final Minecraft mc = FleetHop.mc;
        if (Minecraft.thePlayer.isPotionActive(Potion.jump)) {
            final Minecraft mc2 = FleetHop.mc;
            final int amplifier = Minecraft.thePlayer.getActivePotionEffect(Potion.jump).getAmplifier();
            baseJump = 0.42 + amplifier;
        }
        return baseJump;
    }
    
    private boolean canSpeed() {
        final Minecraft mc = FleetHop.mc;
        return !Minecraft.thePlayer.isInWater();
    }
    
    public void toFwd(final double speed) {
        final Minecraft mc = FleetHop.mc;
        final float yaw = Minecraft.thePlayer.rotationYaw * 0.017453292f;
        final Minecraft mc2 = FleetHop.mc;
        final EntityPlayerSP thePlayer3;
        final EntityPlayerSP thePlayer = thePlayer3 = Minecraft.thePlayer;
        thePlayer3.motionX -= MathHelper.sin(yaw) * speed;
        final Minecraft mc3 = FleetHop.mc;
        final EntityPlayerSP thePlayer4;
        final EntityPlayerSP thePlayer2 = thePlayer4 = Minecraft.thePlayer;
        thePlayer4.motionZ += MathHelper.cos(yaw) * speed;
    }
    
    public static double getSpeed() {
        Minecraft.getMinecraft();
        final double motionX = Minecraft.thePlayer.motionX;
        Minecraft.getMinecraft();
        final double n = motionX * Minecraft.thePlayer.motionX;
        Minecraft.getMinecraft();
        final double motionZ = Minecraft.thePlayer.motionZ;
        Minecraft.getMinecraft();
        return Math.sqrt(n + motionZ * Minecraft.thePlayer.motionZ);
    }
    
    public void setSpeed(final double speed) {
        final Minecraft mc = FleetHop.mc;
        Minecraft.thePlayer.motionX = -(Math.sin(this.getDirection()) * speed);
        final Minecraft mc2 = FleetHop.mc;
        Minecraft.thePlayer.motionZ = Math.cos(this.getDirection()) * speed;
    }
    
    public float getDirection() {
        final Minecraft mc = FleetHop.mc;
        float yaw = Minecraft.thePlayer.rotationYaw;
        final Minecraft mc2 = FleetHop.mc;
        if (Minecraft.thePlayer.moveForward < 0.0f) {
            yaw += 180.0f;
        }
        float forward = 1.0f;
        final Minecraft mc3 = FleetHop.mc;
        if (Minecraft.thePlayer.moveForward < 0.0f) {
            forward = -0.5f;
        }
        else {
            final Minecraft mc4 = FleetHop.mc;
            if (Minecraft.thePlayer.moveForward > 0.0f) {
                forward = 0.5f;
            }
        }
        final Minecraft mc5 = FleetHop.mc;
        if (Minecraft.thePlayer.moveStrafing > 0.0f) {
            yaw -= 90.0f * forward;
        }
        final Minecraft mc6 = FleetHop.mc;
        if (Minecraft.thePlayer.moveStrafing < 0.0f) {
            yaw += 90.0f * forward;
        }
        yaw *= 0.017453292f;
        return yaw;
    }
    
    private boolean isInLiquid() {
        final Minecraft mc = FleetHop.mc;
        if (Minecraft.thePlayer == null) {
            return false;
        }
        final Minecraft mc2 = FleetHop.mc;
        int x = MathHelper.floor_double(Minecraft.thePlayer.boundingBox.minX);
        int i;
		do {
		    final Minecraft mc3 = FleetHop.mc;
		    int z = MathHelper.floor_double(Minecraft.thePlayer.boundingBox.minZ);
		    int j;
			do {
			    final int x2 = x;
			    final Minecraft mc4 = FleetHop.mc;
			    final BlockPos pos = new BlockPos(x2, (int)Minecraft.thePlayer.boundingBox.minY, z);
			    final Minecraft mc5 = FleetHop.mc;
			    final Block block = Minecraft.theWorld.getBlockState(pos).getBlock();
			    if (block != null && !(block instanceof BlockAir)) {
			        return block instanceof BlockLiquid;
			    }
			    ++z;
			    j = z;
			    final Minecraft mc6 = FleetHop.mc;
			} while (j < MathHelper.floor_double(Minecraft.thePlayer.boundingBox.maxZ) + 1);
		    ++x;
		    i = x;
		    final Minecraft mc7 = FleetHop.mc;
		} while (i < MathHelper.floor_double(Minecraft.thePlayer.boundingBox.maxX) + 1);
        return false;
    }
    
    enum FleetSpeedMode
    {
        ChinaZZHop, 
        Bhop, 
        Hypixel, 
        HypSpeed, 
        NewHop, 
        Onground, 
        HypixelFast;
        

    }
}
