package de.fhstralsund.project.core.screen;


import de.fhstralsund.project.core.GameScreen;
import de.fhstralsund.project.core.interfaces.IRenderable;
import de.fhstralsund.project.core.interfaces.IUpdateable;
import de.fhstralsund.project.map.Map;
import de.fhstralsund.project.resource.ResourceLoader;

public class Game extends GameScreen implements IRenderable, IUpdateable {

    private Map map;
    private static ResourceLoader rl;

    public Game(ResourceLoader rl,int size) {
        this.map = new Map(size, rl);
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
        rl  = new ResourceLoader();

        //load grastexture
        rl.loadImageFile("res","ground.png");
        rl.loadImageFile("res\\street","ne.png");

        map = new Map(50, rl);
    }

    @Override
    public String getScreenName() {
        return "game";
    }
}
