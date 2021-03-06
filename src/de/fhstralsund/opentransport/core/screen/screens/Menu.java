package de.fhstralsund.opentransport.core.screen.screens;

import de.fhstralsund.opentransport.Window;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.screen.GameScreen;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import org.lwjgl.opengl.GL11;

public class Menu extends GameScreen implements IRenderable, IUpdateable {

    private ResourceLoader rl;

    public Menu(ResourceLoader rl) {
        this.rl = rl;
    }

    @Override
    public void setup() {

    }

    @Override
    public String getScreenName() {
        return "menu";
    }

    @Override
    public void render(ResourceLoader rl) {
        //background
        this.rl.bindTextureByFileName("menu_bg.png");
        GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2f(0,0);
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex2f((float) (Window.getDisplay().getX()*(1.5)),0);
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex2f((float) (Window.getDisplay().getX()*1.5), Window.getDisplay().getY()*2);
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex2f(0,Window.getDisplay().getY()*2);
        GL11.glEnd();

        //name
        this.rl.bindTextureByFileName("menu_logo.png");
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(50,0);
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(550,0);
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(550,350);
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(0,350);
        GL11.glEnd();
    }

    @Override
    public void update() {

    }
}
