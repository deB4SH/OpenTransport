package de.fhstralsund.project.core.screen;

import de.fhstralsund.project.Window;
import de.fhstralsund.project.core.GameScreen;
import de.fhstralsund.project.core.interfaces.IRenderable;
import de.fhstralsund.project.core.interfaces.IUpdateable;
import de.fhstralsund.project.resource.ResourceLoader;
import org.lwjgl.opengl.GL11;

import java.io.File;

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
    public void render() {
        //background
        this.rl.bindTextureByID(9);
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
        this.rl.bindTextureByID(10);
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
