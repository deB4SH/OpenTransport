package de.fhstralsund.project.core.entity.type;

import de.fhstralsund.project.core.entity.Entity;
import de.fhstralsund.project.core.interfaces.IRenderable;
import de.fhstralsund.project.core.interfaces.IUpdateable;
import de.fhstralsund.project.core.io.ResourceLoader;
import de.fhstralsund.project.core.screen.Camera;
import de.fhstralsund.project.core.screen.screens.Game;
import org.lwjgl.opengl.GL11;
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

    }
}
