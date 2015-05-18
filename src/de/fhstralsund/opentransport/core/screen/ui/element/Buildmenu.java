package de.fhstralsund.opentransport.core.screen.ui.element;

import de.fhstralsund.opentransport.core.io.ResourceLoader;
import de.fhstralsund.opentransport.core.screen.ui.UIElement;
import de.fhstralsund.opentransport.core.screen.ui.UISubelement;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import java.util.ArrayList;
import java.util.List;

public class Buildmenu extends UIElement{

    private List<UISubelement> buildObj;
    private int textureID;

    public Buildmenu(boolean visible, String name, int posX, int posY, int width, int height, int textureID) {
        super(visible, name, posX, posY, width, height);
        this.buildObj = new ArrayList<UISubelement>();
        this.textureID = textureID;
    }

    @Override
    public void render(ResourceLoader rl) {
        //TODO: render base
        Color.white.bind();
        rl.bindTextureByID(textureID);
            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2f(super.getPosX(), super.getPosY());
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex2f(super.getPosX() + super.getWidth(), super.getPosY());
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex2f(super.getPosX() + super.getWidth(), super.getPosY() + super.getHeight());
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex2f(super.getPosX(), super.getPosY() + super.getHeight());
        GL11.glEnd();

        //TODO: render UIButtons
    }

    @Override
    public void update() {
        //TODO: check if uielement is clicked for building

        //TODO: add selected Element to Mouselistener?
    }
}
