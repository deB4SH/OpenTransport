package de.fhstralsund.project.core.entity.type;

import de.fhstralsund.project.core.entity.Entity;
import de.fhstralsund.project.core.interfaces.IRenderable;
import de.fhstralsund.project.core.interfaces.IUpdateable;
import de.fhstralsund.project.core.io.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;

public class Street extends Entity implements IUpdateable,IRenderable{

    private int textureID;
    private boolean north,east,south,west;


    public Street(Vector2f start, int textureID, boolean north, boolean south, boolean west, boolean east) {
        super(start,true);
        this.textureID = textureID;
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
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
