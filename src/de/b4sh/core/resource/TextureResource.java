package de.b4sh.core.resource;

import org.newdawn.slick.opengl.Texture;

public class TextureResource implements Resource {

    private int ressourceID;
    private Texture texture;

    public TextureResource(Texture texture, int ressourceID) {
        this.texture = texture;
        this.ressourceID = ressourceID;
    }

    @Override
    public int getRID() {
        return 0;
    }

    @Override
    public ResourceType getType() {
        return ResourceType.Texture;
    }

    @Override
    public String getRIdent() {
        return null;
    }
}
