package de.fhstralsund.opentransport.core.entity;

import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import de.fhstralsund.opentransport.core.map.Tile;
import org.lwjgl.util.vector.Vector2f;

import java.io.File;
import java.util.Random;

public class Vegatation implements IRenderable{

    private int mapSize;


    public Vegatation() {
    }


    @Override
    public void render(ResourceLoader rl) {
        for(int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if((i % 2 == 0 || j % 2 == 0) && i != 5 && j != 5 &&
                        ((new Random().nextInt(mapSize) + 2) % (j + 1) != 0 &&
                                (new Random().nextInt(mapSize) + 2) % (i + 1) != 0) && (new Random().nextInt(6) + 1) % 2 == 0) {

                                //rendercode similiar to entity here

                }
            }
        }
    }
}
