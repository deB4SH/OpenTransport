package de.fhstralsund.project.core.entity.type;

import de.fhstralsund.project.core.entity.Entity;
import de.fhstralsund.project.core.interfaces.IRenderable;
import de.fhstralsund.project.core.interfaces.IUpdateable;
import de.fhstralsund.project.core.io.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;

public class Building extends Entity implements IRenderable,IUpdateable{

    private int citizienCount;
    private int textureID;

    public Building(Vector2f tilePos, Boolean enterAble) {
        super(tilePos, enterAble);
    }

    @Override
    public void render(ResourceLoader rl) {
        rl.bindTextureByID(textureID);
        //TODO: building rendercalls
    }

    @Override
    public void update() {

    }
}
