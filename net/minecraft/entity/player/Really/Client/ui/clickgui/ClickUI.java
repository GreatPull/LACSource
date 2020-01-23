package net.minecraft.entity.player.Really.Client.ui.clickgui;

import com.google.common.collect.Lists;
import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import java.util.function.Consumer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.shader.ShaderGroup;
import net.minecraft.entity.player.Really.Client.module.ModuleType;
import net.minecraft.entity.player.Really.Client.ui.Particle;
import net.minecraft.entity.player.Really.Client.ui.clickgui.Window;
import net.minecraft.entity.player.Really.Client.utils.render.RenderUtil;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;

public class ClickUI
extends GuiScreen {
    public static ArrayList<Window> windows = Lists.newArrayList();
    private ArrayList<Particle> particles7789;
    public double opacity = 0.0;
    public int scrollVelocity;
    public static boolean binding = false;
    Random random = new Random();

    public ClickUI() {
        if (windows.isEmpty()) {
            int x2 = 5;
            for (ModuleType c2 : ModuleType.values()) {
                windows.add((Window)new Window(c2, x2, 10));
                x2 += 100;
            }
        }
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        Gui.drawRect((double)0.0, (double)0.0, (double)RenderUtil.width(), (double)RenderUtil.height(), (int)new Color(0, 0, 0, 150).getRGB());
        this.particles7789 = new ArrayList();
        ScaledResolution resolution = new ScaledResolution(this.mc);
        for (int i = 0; i < 2; i += 50) {
            this.particles7789.add((Particle)new Particle(this.random.nextInt(resolution.getScaledWidth()), this.random.nextInt(resolution.getScaledHeight())));
        }
        this.opacity = this.opacity + 10.0 < 200.0 ? (this.opacity = this.opacity + 10.0) : 200.0;
        Color color = new Color(-2146365167);
        GlStateManager.pushMatrix();
        ScaledResolution scaledRes = new ScaledResolution(this.mc);
        float scale = (float)scaledRes.getScaleFactor() / (float)Math.pow((double)scaledRes.getScaleFactor(), (double)5.0);
        windows.forEach(w2 -> w2.render(mouseX, mouseY));
        GlStateManager.popMatrix();
        if (Mouse.hasWheel()) {
            int wheel = Mouse.getDWheel();
            this.scrollVelocity = wheel < 0 ? -120 : (wheel > 0 ? 130 : 0);
        }
        windows.forEach(w2 -> w2.mouseScroll(mouseX, mouseY, this.scrollVelocity));
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    public void initGui() {
        Minecraft.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
    }

    public void onGuiClosed() {
        if (Minecraft.entityRenderer.theShaderGroup != null) {
            Minecraft.entityRenderer.theShaderGroup.deleteShaderGroup();
            Minecraft.entityRenderer.theShaderGroup = null;
        }
        super.onGuiClosed();
    }

    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        windows.forEach(w2 -> w2.click(mouseX, mouseY, mouseButton));
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1 && !binding) {
            this.mc.displayGuiScreen(null);
            return;
        }
        windows.forEach(w2 -> w2.key(typedChar, keyCode));
    }

    public synchronized void sendToFront(Window window) {
        int panelIndex = 0;
        for (int i2 = 0; i2 < windows.size(); ++i2) {
            if (windows.get(i2) != window) continue;
            panelIndex = i2;
            break;
        }
        Window t2 = (Window)windows.get(windows.size() - 1);
        windows.set(windows.size() - 1, (Window)((Window)windows.get(panelIndex)));
        windows.set(panelIndex, (Window)t2);
    }
}
