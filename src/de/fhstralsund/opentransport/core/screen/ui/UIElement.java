package de.fhstralsund.opentransport.core.screen.ui;

import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;

import java.util.List;

public class UIElement implements IUpdateable,IRenderable {

    private boolean visible;
    private String name;
    private int posX;
    private int posY;
    private int width;
    private int height;
    private List<UISubelement> uiSubelements;

    public UIElement(boolean visible, String name, int posX, int posY, int width, int height) {
        this.visible = visible;
        this.name = name;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
    }

    public UIElement(boolean visible, String name, int posX, int posY, int width, int height, List<UISubelement> uiSubelements) {
        this.visible = visible;
        this.name = name;
        this.posX = posX;
        this.posY = posY;
        this.width = width;
        this.height = height;
        this.uiSubelements = uiSubelements;
    }

    @Override
    public void render(ResourceLoader rl) {

    }

    @Override
    public void update() {

    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPosX() {
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }

    public void setPosY(int posY) {
        this.posY = posY;
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

    public List<UISubelement> getUiSubelements() {
        return uiSubelements;
    }

    public void setUiSubelements(List<UISubelement> uiSubelements) {
        this.uiSubelements = uiSubelements;
    }
}
