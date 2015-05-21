package de.fhstralsund.opentransport.core.entity.type;

import de.fhstralsund.opentransport.core.entity.Entity;
import de.fhstralsund.opentransport.core.interfaces.IDailycycle;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;


public class Industry extends Entity implements IUpdateable, IRenderable, IDailycycle{

    String typ;

    public Industry(Vector2f tilePos, Boolean enterAble, String typ, int textureID) {
        super(tilePos, enterAble);
        this.typ = typ;
        this.setTextureID(textureID);
    }

    @Override
    public void render(ResourceLoader rl) {
        super.render(rl);
    }

    @Override
    public void update() {

    }

    @Override
    public void dailyupdate(){

    }
}
