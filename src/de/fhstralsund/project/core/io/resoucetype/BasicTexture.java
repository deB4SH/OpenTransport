package de.fhstralsund.project.core.io.resoucetype;

import org.newdawn.slick.opengl.Texture;

public class BasicTexture {
    private Texture texture;
    private String filename;
    private int id;

    public BasicTexture(Texture texture, String filename, int id) {
        this.texture = texture;
        this.filename = filename;
        this.id = id;
    }

    public int getId() {
        return id;
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
