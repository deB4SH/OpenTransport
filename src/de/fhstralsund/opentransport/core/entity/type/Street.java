package de.fhstralsund.opentransport.core.entity.type;

import de.fhstralsund.opentransport.core.entity.Entity;
import de.fhstralsund.opentransport.core.entity.EntityController;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;

public class Street extends Entity implements IUpdateable,IRenderable{

    private boolean north,east,south,west;

    public Street(Vector2f start, int textureID, boolean north, boolean south, boolean west, boolean east) {
        super(start,true);
        super.setTextureID(textureID);
        this.north = north;
        this.east = east;
        this.south = south;
        this.west = west;
    }

    @Override
    public void render(ResourceLoader rl) {
        super.render(rl);
    }

    @Override
    public void update() {
        //check if i need to update my textureID
    }

    public void updateTexture(EntityController streetController){

    }
}
