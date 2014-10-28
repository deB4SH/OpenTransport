package de.fhstralsund.project.core.screen;


import de.fhstralsund.project.core.GameScreen;
import de.fhstralsund.project.core.interfaces.IRenderable;
import de.fhstralsund.project.core.interfaces.IUpdateable;
import de.fhstralsund.project.map.Map;
import de.fhstralsund.project.resource.ResourceLoader;

public class Game extends GameScreen implements IRenderable, IUpdateable {

    private Map map;
    private ResourceLoader rl;

    public Game(ResourceLoader rlo,int size) {
        this.map = new Map(size, rlo);
        this.rl = rlo;
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
