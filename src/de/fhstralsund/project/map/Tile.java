package de.fhstralsund.project.map;


import org.lwjgl.util.vector.Vector2f;

public class Tile {

    private Vector2f position;
    private int textureId;

    public Tile(Vector2f position, int textureId){
        this.position = position;
        this.textureId = textureId;
    }

    public int getTextureId() {
        return textureId;
    }
}
