package de.fhstralsund.project.core.entity.type;

import de.fhstralsund.project.core.entity.Entity;
import de.fhstralsund.project.core.interfaces.IRenderable;
import de.fhstralsund.project.core.interfaces.IUpdateable;
import de.fhstralsund.project.core.io.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;

public class Street extends Entity implements IUpdateable,IRenderable{

    private Vector2f to;
    private int textureID;

    public Street(Vector2f start, Vector2f to) {
        super(start,true);
        this.to = to;
    }

    @Override
    public void render(ResourceLoader rl) {
        rl.bindTextureByID(textureID);
        //TODO: street rendercalls
    }

    @Override
    public void update() {

    }
}
