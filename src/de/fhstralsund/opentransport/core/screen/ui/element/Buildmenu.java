package de.fhstralsund.opentransport.core.screen.ui.element;

import de.fhstralsund.opentransport.core.io.ResourceLoader;
import de.fhstralsund.opentransport.core.screen.ui.UIButton;
import de.fhstralsund.opentransport.core.screen.ui.UIElement;
import de.fhstralsund.opentransport.core.screen.ui.UISubelement;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import java.util.ArrayList;
import java.util.List;

public class Buildmenu extends UIElement{

    private List<UISubelement> buildObj;
    private List<UIButton> buildMenuButtons;
    private int textureID;

    private int startButtonPosX;

    public Buildmenu(boolean visible, String name, int posX, int posY, int width, int height, int textureID) {
        super(visible, name, posX, posY, width, height);
        this.buildObj = new ArrayList<UISubelement>();
        this.buildMenuButtons = new ArrayList<UIButton>();
        this.textureID = textureID;
        this.startButtonPosX = super.getPosX();

        buildObj.add(new UISubelement(true, false, "street", this.getPosX() + 10, this.getPosY() + 10, 24, 48));
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

        //render all buttonElements
        for(UIButton e: this.buildMenuButtons){
            e.render(rl);
        }
    }

    @Override
    public void update() {
        for(UIButton e: this.buildMenuButtons){
            e.update();
        }

        //TODO: add selected Element to Mouselistener?
    }

    public void addButton(UIButton button) {

        UIButton tmp = button;
        tmp.setPosX(this.buildMenuButtons.size() == 0 ? startButtonPosX : buildMenuButtons.get(this.buildMenuButtons.size()-1).getPosX()
                + 48);
        tmp.setPosY(super.getPosY()+5);

        buildMenuButtons.add(button);
    }
}
