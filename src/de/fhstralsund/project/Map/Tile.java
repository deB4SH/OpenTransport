package de.fhstralsund.project.Map;


import org.lwjgl.util.vector.Vector2f;

public class Tile {

    private Vector2f position;
    private int textureId;

    public Tile(Vector2f position, int textureId){
        this.position = position;
        this.textureId = textureId;
    }
}
