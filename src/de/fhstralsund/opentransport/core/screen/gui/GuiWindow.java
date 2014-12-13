package de.fhstralsund.opentransport.core.screen.gui;


import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;
import org.lwjgl.util.ReadablePoint;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GuiWindow implements IUpdateable, IRenderable{

    private Vector2f position;
    private Vector2f dimension;
    private Rectangle closeRectangle;
    private List<GuiWindowElement> guiWindowElements = null;
    private String name;
    private Gui gui;
    private GuiWindowElement lastChosenGuiElement = null;

    public GuiWindow(Vector2f position, Vector2f dimension, String name, Gui gui) {
        this.position = position;
        this.dimension = dimension;
        this.closeRectangle = new Rectangle((int)position.x + (int)dimension.x - 28, (int)position.y, 28, 19);
        this.name = name;
        this.guiWindowElements = new ArrayList<GuiWindowElement>();
        this.gui = gui;
    }

    @Override
    public void render(ResourceLoader rl) {

        Color.white.bind();
        rl.bindTextureByFileName("res"+ File.separator+"gui"+File.separator+"gui_background.png");
        GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2f(position.x, position.y);
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex2f(position.x + dimension.x, position.y);
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex2f(position.x + dimension.x, position.y + dimension.y);
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex2f(position.x, position.y + dimension.y);
        GL11.glEnd();


        for (GuiWindowElement element : guiWindowElements) {
            rl.bindTextureByID(element.getTextureID());

            GL11.glBegin(GL11.GL_QUADS);
                GL11.glTexCoord2f(0, 0);
                GL11.glVertex2f(element.getPosition().x, element.getPosition().y);
                GL11.glTexCoord2f(1, 0);
                GL11.glVertex2f(element.getPosition().x + element.getDimension().x / 2, element.getPosition().y);
                GL11.glTexCoord2f(1, 1);
                GL11.glVertex2f(element.getPosition().x + element.getDimension().x / 2, element.getPosition().y + element.getDimension().y /2);
                GL11.glTexCoord2f(0, 1);
                GL11.glVertex2f(element.getPosition().x, element.getPosition().y + element.getDimension().y / 2);
            GL11.glEnd();

            if(element.chosen) {
                rl.bindTextureByFileName("res"+ File.separator+"gui"+File.separator+"gui_chosen.png");
                GL11.glBegin(GL11.GL_QUADS);
                    GL11.glTexCoord2f(0, 0);
                    GL11.glVertex2f(element.getPosition().x, element.getPosition().y);
                    GL11.glTexCoord2f(1, 0);
                    GL11.glVertex2f(element.getPosition().x + element.getDimension().x / 2, element.getPosition().y);
                    GL11.glTexCoord2f(1, 1);
                    GL11.glVertex2f(element.getPosition().x + element.getDimension().x / 2, element.getPosition().y + element.getDimension().y / 2);
                    GL11.glTexCoord2f(0, 1);
                    GL11.glVertex2f(element.getPosition().x, element.getPosition().y + element.getDimension().y / 2);
                GL11.glEnd();
            }
        }
    }

    @Override
    public void update() {

        // X Knöp
        ReadablePoint p = new Point(Mouse.getX(), -Mouse.getY() + 800); // invertieren weil windows andere koordinaten liefert
        if(closeRectangle.contains(p) && Mouse.isButtonDown(0)) {
            gui.closeRequest(this.name);
        }

        // Elemente angeklickt
        for(GuiWindowElement guiWindowElement : this.guiWindowElements) {
            if(guiWindowElement.getRectangle().contains(p) && guiWindowElement.clickable && Mouse.isButtonDown(0)){
                if( lastChosenGuiElement != null) {lastChosenGuiElement.chosen =false;}
                guiWindowElement.chosen = true;
                lastChosenGuiElement = guiWindowElement;
            }
        }
    }

    public List<GuiWindowElement> getGuiWindowElements() {
        return guiWindowElements;
    }

    public void setGuiWindowElements(List<GuiWindowElement> guiWindowElements) {
        this.guiWindowElements = guiWindowElements;
    }

    public Vector2f getPosition() {
        return position;
    }

    public void setPosition(Vector2f position) {
        this.position = position;
    }

    public Vector2f getDimension() {
        return dimension;
    }

    public void setDimension(Vector2f dimension) {
        this.dimension = dimension;
    }
}
