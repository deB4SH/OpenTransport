package de.fhstralsund.opentransport.core.entity.type;

import de.fhstralsund.opentransport.core.entity.Entity;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;

public class Building extends Entity implements IRenderable,IUpdateable{

    private int citizienCount;
    private boolean planned;

    public Building(Vector2f tilePos, Boolean enterAble, int textureID) {
        super(tilePos, enterAble);
        super.setTextureID(textureID);
        this.planned = false;
    }

    public Building(Vector2f tilePos, Boolean enterAble, int textureID, boolean planned) {
        super(tilePos, enterAble);
        super.setTextureID(textureID);
        this.planned = planned;
    }


    @Override
    public void render(ResourceLoader rl) {
        if(!planned){
            super.render(rl);
        }
        else{
            //nothing
        }
    }

    @Override
    public void update() {
        //TODO: updateplans for buildings(homes for the peeps)
    }
}
