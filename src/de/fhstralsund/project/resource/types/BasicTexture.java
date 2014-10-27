package de.fhstralsund.project.resource.types;

import org.newdawn.slick.opengl.Texture;

public class BasicTexture {
    private Texture texture;
    private String filename;

    public BasicTexture(Texture texture, String filename) {
        this.texture = texture;
        this.filename = filename;
    }

    public Texture getTexture() {
        return texture;
    }

    public String getFilename() {
        return filename;
    }

    public void bindTexture(){
        this.texture.bind();
    }
}
