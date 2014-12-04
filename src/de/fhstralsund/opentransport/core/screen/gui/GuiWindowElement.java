package de.fhstralsund.opentransport.core.screen.gui;

import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

public class GuiWindowElement {

    private int textureID;
    private Vector2f position;
    private Vector2f dimension;
    private Rectangle rectangle;
    public boolean chosen = false;
    public boolean clickable;

    public GuiWindowElement(int textureID, Vector2f position, Vector2f dimension, boolean clickable) {
        this.textureID = textureID;
        this.position = position;
        this.dimension = dimension;
        this.rectangle = new Rectangle((int)position.x, (int)position.y, (int)dimension.x / 2, (int)dimension.y / 2);
        this.clickable = clickable;
    }

    public int getTextureID() {
        return textureID;
    }

    public void setTextureID(int textureID) {
        this.textureID = textureID;
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

    public Rectangle getRectangle() {
        return rectangle;
    }

    public void setRectangle(Rectangle rectangle) {
        this.rectangle = rectangle;
    }
}
