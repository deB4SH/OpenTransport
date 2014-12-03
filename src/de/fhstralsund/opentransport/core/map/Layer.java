package de.fhstralsund.opentransport.core.map;

import de.fhstralsund.opentransport.core.io.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;

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
                    map[i][j] = new Tile(new Vector2f(i, j), this.rl.getTexturesID("ground.png"));
                }
            }
        }

        if (type.equalsIgnoreCase("vegetation")) {
            genereateEnvironement();
        }

        if(type.equalsIgnoreCase("streets")) {
            generateStreets();
        }
    }


    public void genereateEnvironement() {
        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if((i % 2 == 0 || j % 2 == 0) && i != 5 && j != 5 &&
                        ((new Random().nextInt(size) + 2) % (j + 1) != 0 &&
                                (new Random().nextInt(size) + 2) % (i + 1) != 0) && (new Random().nextInt(6) + 1) % 2 == 0) {
                    map[i][j] = new Tile(new Vector2f(i, j), rl.getTexturesID("wood" + (new Random().nextInt(6) + 1) + ".png"));
                }
            }
        }
    }


    public void generateStreets() {
        for (int i = 0; i < size; i++) {
            int j = 5;
            map[i][j] = new Tile(new Vector2f(i, j), rl.getTexturesID("Street_NE.png"));
        }
        for (int i = 0; i < size; i++) {
            int j = 5;
            map[j][i] = new Tile(new Vector2f(i, j), rl.getTexturesID("street_SE_.png"));
        }
    }

    public Tile[][] getMap(){
        return map;
    }
}
