package de.fhstralsund.project.core.entity;

import de.fhstralsund.project.core.interfaces.IRenderable;
import de.fhstralsund.project.core.interfaces.IUpdateable;
import de.fhstralsund.project.core.io.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;

public class Entity implements IUpdateable, IRenderable {

    private Vector2f tilePos;
    private boolean enterAble;

    public Entity(Vector2f tilePos, Boolean enterAble) {
        this.enterAble = enterAble;
        this.tilePos = tilePos;
    }

    public boolean isEnterAble() {
        return enterAble;
    }

    public void setEnterAble(boolean enterAble) {
        this.enterAble = enterAble;
    }

    public Vector2f getTilePos() {
        return tilePos;
    }

    public void setTilePos(Vector2f tilePos) {
        this.tilePos = tilePos;
    }

    @Override
    public void render(ResourceLoader rl) {

    }

    @Override
    public void update() {

    }

}
