package de.fhstralsund.project.map;

import org.lwjgl.util.vector.Vector2f;

public class Layer {

    private Tile map[][];
    private int size;

    public Layer(int size) {
        this.size = size;
        map = new Tile[size][size];

        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Gras
                map[i][j] = new Tile(new Vector2f(i, j), 0);
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
