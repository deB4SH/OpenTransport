package de.b4sh.core.map;

import de.b4sh.core.map.core.TileType;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

public class Tile {

    private TileType tileType;
    private Vector2f position;
    private Texture texture;

    public Tile(TileType tileType, Vector2f position, Texture textureRef) {
        this.tileType = tileType;
        this.position = position;
        this.texture = textureRef;
    }

    public void render(){

    }
}
