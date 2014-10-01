package de.b4sh.core.resource;

import org.newdawn.slick.Font;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.opengl.Texture;

public class BasicResource {

    //Basic Fields
    private Texture texture;
    private Audio sound;
    private Font font;

    private String identifier;
    private int id;

    public BasicResource(Texture texture, String identifier) {
        this.texture = texture;
        this.identifier = identifier;
    }

    public BasicResource(Audio sound, String identifier) {
        this.sound = sound;
        this.identifier = identifier;
    }

    public BasicResource(Font font, String identifier) {
        this.font = font;
        this.identifier = identifier;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getId() {
        return id;
    }

    public void bindTexture(){
        //TODO: bind texture
    }
}
