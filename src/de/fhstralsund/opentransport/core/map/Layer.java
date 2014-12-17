package de.fhstralsund.opentransport.core.map;

import de.fhstralsund.opentransport.core.io.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;

import java.io.File;
import java.util.Random;

public class Layer {

    private Tile map[][];
    private int size;
    private ResourceLoader rl;

    public Layer(int size, ResourceLoader rl, String type) {
        this.size = size;
        map = new Tile[size][size];
        this.rl = rl;

        if (type.equalsIgnoreCase("")) {
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    // Gras
                    map[i][j] = new Tile(new Vector2f(i, j), this.rl.getTextureID("res" + File.separator + "landscape" + File.separator + "ground.png"));
                }
            }
        }
    }


    public void genereateEnvironement() {

    }


    public void generateStreets() {
        for (int i = 0; i < size; i++) {
            int j = 5;
            map[i][j] = new Tile(new Vector2f(i, j), rl.getTextureID("Street_NE.png"));
        }
        for (int i = 0; i < size; i++) {
            int j = 5;
            map[j][i] = new Tile(new Vector2f(i, j), rl.getTextureID("street_SE_.png"));
        }
    }

    public Tile[][] getMap(){
        return map;
    }
}
