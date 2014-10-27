package de.fhstralsund.project.Map;

import org.lwjgl.util.vector.Vector2f;

/**
 * Created by Florian on 27.10.2014.
 */
public class Layer {

    private Tile map[][];

    public Layer(int size) {
        map = new Tile[size][size];

        for(int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                // Gras
                map[i][j] = new Tile(new Vector2f(i, j), 0);
            }
        }
    }
}
