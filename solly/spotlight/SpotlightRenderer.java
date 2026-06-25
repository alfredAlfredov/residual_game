package solly.spotlight;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11
  
@SideOnly(Side.CLIENT)
public class SpotlightRenderer {

    // light cyrcle
    private static final float RADIUS_FRACTION = 0.18f;

    // invis
    private static final float DARKNESS = 0.92f;

    private static final int CIRCLE_SEGMENTS = 128;

    @SubscribeEvent
    public void onRenderOverlay(RenderGameOverlayEvent.Post event) {
        if (event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;

        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.player;
        if (player == null) return;

        // if player holds flashlight
        ItemStack mainHand = player.getHeldItem(EnumHand.MAIN_HAND);
        ItemStack offHand  = player.getHeldItem(EnumHand.OFF_HAND);
        boolean active = (mainHand.getItem() instanceof ItemFlashlight && ItemFlashlight.isOn(mainHand))
                      || (offHand.getItem()  instanceof ItemFlashlight && ItemFlashlight.isOn(offHand));

        if (!active) return;

        int screenW = mc.displayWidth;
        int screenH = mc.displayHeight;
        float cx = screenW / 2.0f;
        float cy = screenH / 2.0f;
        float radius = screenH * RADIUS_FRACTION;

        // coords
        GlStateManager.pushMatrix();
        setupOrtho(screenW, screenH);

        GL11.glEnable(GL11.GL_STENCIL_TEST);

        GL11.glClear(GL11.GL_STENCIL_BUFFER_BIT);
        GL11.glStencilFunc(GL11.GL_ALWAYS, 1, 0xFF);
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_REPLACE);
        GL11.glStencilMask(0xFF);
        GL11.glColorMask(false, false, false, false); // do not register color

        GlStateManager.disableTexture2D();
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);
        GL11.glVertex2f(cx, cy);
        for (int i = 0; i <= CIRCLE_SEGMENTS; i++) {
            double angle = 2.0 * Math.PI * i / CIRCLE_SEGMENTS;
            GL11.glVertex2f(
                cx + (float)(Math.cos(angle) * radius),
                cy + (float)(Math.sin(angle) * radius)
            );
        }
        GL11.glEnd();

        GL11.glColorMask(true, true, true, true);
        GL11.glStencilFunc(GL11.GL_NOTEQUAL, 1, 0xFF);
        GL11.glStencilOp(GL11.GL_KEEP, GL11.GL_KEEP, GL11.GL_KEEP);
        GL11.glStencilMask(0x00);

        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glColor4f(0f, 0f, 0f, DARKNESS);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glVertex2f(0,       0);
        GL11.glVertex2f(screenW, 0);
        GL11.glVertex2f(screenW, screenH);
        GL11.glVertex2f(0,       screenH);
        GL11.glEnd();
      
        // gradient
        float innerR = radius * 0.88f;
        float outerR = radius;
        GL11.glStencilFunc(GL11.GL_ALWAYS, 0, 0xFF);

        GL11.glBegin(GL11.GL_TRIANGLE_STRIP);
        for (int i = 0; i <= CIRCLE_SEGMENTS; i++) {
            double angle = 2.0 * Math.PI * i / CIRCLE_SEGMENTS;
            float cosA = (float) Math.cos(angle);
            float sinA = (float) Math.sin(angle);

            // inside power
            GL11.glColor4f(0f, 0f, 0f, 0f);
            GL11.glVertex2f(cx + cosA * innerR, cy + sinA * innerR);

            // outside point
            GL11.glColor4f(0f, 0f, 0f, DARKNESS);
            GL11.glVertex2f(cx + cosA * outerR, cy + sinA * outerR);
        }
        GL11.glEnd();

        // del
        GL11.glDisable(GL11.GL_STENCIL_TEST);
        GL11.glStencilMask(0xFF);
        GlStateManager.enableTexture2D();
        GlStateManager.disableBlend();
        GL11.glColor4f(1f, 1f, 1f, 1f);

        restoreOrtho();
        GlStateManager.popMatrix();
    }

    // utils

    private void setupOrtho(int w, int h) {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
        GL11.glOrtho(0, w, h, 0, -1, 1); // y down
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPushMatrix();
        GL11.glLoadIdentity();
    }

    private void restoreOrtho() {
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glPopMatrix();
    }
}

