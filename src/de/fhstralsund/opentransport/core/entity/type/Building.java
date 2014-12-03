package de.fhstralsund.opentransport.core.entity.type;

import de.fhstralsund.opentransport.core.entity.Entity;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;

public class Building extends Entity implements IRenderable,IUpdateable{

    private int citizienCount;

    public Building(Vector2f tilePos, Boolean enterAble, int textureID) {
        super(tilePos, enterAble);
        super.setTextureID(textureID);
    }

    @Override
    public void render(ResourceLoader rl) {
        super.render(rl);
    }

    @Override
    public void update() {
        //TODO: updateplans for buildings(homes for the peeps)
    }
}
