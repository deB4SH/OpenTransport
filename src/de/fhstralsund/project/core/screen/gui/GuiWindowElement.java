package de.fhstralsund.project.core.screen.gui;

import org.lwjgl.util.vector.Vector2f;

public class GuiWindowElement {

    private int textureID;
    private Vector2f position;
    private Vector2f dimension;

    public GuiWindowElement(int textureID, Vector2f position, Vector2f dimension) {
        this.textureID = textureID;
        this.position = position;
        this.dimension = dimension;
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
}
