package de.fhstralsund.project.core.screen.screens;


import de.fhstralsund.project.core.interfaces.IRenderable;
import de.fhstralsund.project.core.interfaces.IUpdateable;
import de.fhstralsund.project.core.screen.GameScreen;
import de.fhstralsund.project.core.map.Map;
import de.fhstralsund.project.core.io.ResourceLoader;

public class Game extends GameScreen implements IRenderable, IUpdateable {

    private Map map;
    private ResourceLoader rl;
    private int mapSize;

    public Game(ResourceLoader rlo,int size) {
        this.map = new Map(size, rlo);
        this.rl = rlo;
        this.mapSize = size; //quad map
    }

    @Override
    public void render() {
        map.render();
    }

    @Override
    public void update() {

    }

    @Override
    public void setup() {

        map = new Map(50, rl);
    }

    @Override
    public String getScreenName() {
        return "game";
    }
}
