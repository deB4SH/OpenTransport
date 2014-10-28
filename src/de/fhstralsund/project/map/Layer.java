package de.fhstralsund.project.map;

import de.fhstralsund.project.resource.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;

public class Layer {

    private Tile map[][];
    private int size;

    public Layer(int size, ResourceLoader rl) {
        this.size = size;
        map = new Tile[size][size];

        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Gras
                map[i][j] = new Tile(new Vector2f(i, j), rl.getTexturesID("ground.png"));
            }
        }
    }


    public void genereateEnvironement() {
        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                map[i][j] = new Tile(new Vector2f(i, j), 0);
            }
        }
    }

    public Tile[][] getMap(){
        return map;
    }
}
