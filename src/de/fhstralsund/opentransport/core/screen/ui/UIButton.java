package de.fhstralsund.opentransport.core.screen.ui;

import de.fhstralsund.opentransport.Window;
import de.fhstralsund.opentransport.core.entity.EntityController;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import de.fhstralsund.opentransport.core.screen.ui.action.UiButtonAction;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;
import org.lwjgl.util.Rectangle;
import org.newdawn.slick.Color;

public class UIButton implements IRenderable, IUpdateable{

    private UIElement linkedElement;
    private int posX;
    private int posY;
    private int width;
    private int height;
    private int textureID;
    private Rectangle collisionBox;
    private UiButtonAction uiButtonAction;
    private boolean mouseUp = false;
    private EntityController entityController;

    public UIButton(UIElement linkedElement, int posX, int posY, int width, int height, int textureID) {
        this.linkedElement = linkedElement;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.textureID = textureID;
        this.collisionBox = new Rectangle(posX,posY,width,height);
    }

    public UIButton(UiButtonAction uiButtonAction, int posX, int posY, int width, int height, int textureID, EntityController entityController) {
        this.uiButtonAction = uiButtonAction;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.textureID = textureID;
        this.collisionBox = new Rectangle(posX,posY,width,height);
        this.entityController = entityController;
    }

    @Override
    public void render(ResourceLoader rl) {
        Color.white.bind();
        rl.bindTextureByID(textureID);
        GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2f(0, 0);
            GL11.glVertex2f(posX, posY);
            GL11.glTexCoord2f(1, 0);
            GL11.glVertex2f(posX + width, posY);
            GL11.glTexCoord2f(1, 1);
            GL11.glVertex2f(posX + width, posY + height);
            GL11.glTexCoord2f(0, 1);
            GL11.glVertex2f(posX, posY + height);
        GL11.glEnd();
    }

    @Override
    public void update() {
        //TODO: clickevent
        Point mousePoint =  new Point(Mouse.getX(), -Mouse.getY() + (int)Window.getDisplay().getY());
        if(this.collisionBox.contains(mousePoint) && Mouse.isButtonDown(0) && !mouseUp){

            if(uiButtonAction != null) {
                uiButtonAction.fireAction(entityController);
            }

            if(this.linkedElement != null) {

                if (this.linkedElement.isVisible()) {
                    this.linkedElement.setVisible(false);
                } else {
                    this.linkedElement.setVisible(true);
                }
            }
        }

        mouseUp = Mouse.isButtonDown(0);
    }

    private void updateCollisionBox(){
        this.collisionBox = new Rectangle(posX,posY,width,height);
    }

    public UIElement getLinkedElement() {
        return linkedElement;
    }

    public void setLinkedElement(UIElement linkedElement) {
        this.linkedElement = linkedElement;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
        this.updateCollisionBox();
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
        this.updateCollisionBox();
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getTextureID() {
        return textureID;
    }

    public void setTextureID(int textureID) {
        this.textureID = textureID;
    }
}
