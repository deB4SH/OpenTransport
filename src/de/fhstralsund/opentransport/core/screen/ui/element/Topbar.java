package de.fhstralsund.opentransport.core.screen.ui.element;

import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import de.fhstralsund.opentransport.core.screen.ui.UIButton;
import de.fhstralsund.opentransport.core.screen.ui.UIElement;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Topbar extends UIElement implements IRenderable,IUpdateable{

    private int textureID;
    private List<UIButton> buttonElements;

    private final int BUTTONOFFSET = 10;
    private int startButton;

    public Topbar(boolean visible, String name, int posX, int posY, int width, int height, int textureID) {
        super(visible, name, posX, posY, width, height);
        this.textureID = textureID;
        this.buttonElements = new ArrayList<UIButton>();
        this.startButton = super.getPosX() + 2*BUTTONOFFSET;
    }

    public void addButton(UIButton e){

        UIButton tmp = e;
        tmp.setPosX(startButton + this.buttonElements.size()*BUTTONOFFSET);
        tmp.setPosY(super.getPosY()+5);

        this.buttonElements.add(tmp);
    }

    @Override
    public void render(ResourceLoader rl) {
        //render background
        Color.white.bind();
        rl.bindTextureByID(this.textureID);
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
        for(UIButton e: this.buttonElements){
            e.render(rl);
        }
    }

    @Override
    public void update() {
        for(UIButton e: this.buttonElements){
            e.update();
        }
    }
}
