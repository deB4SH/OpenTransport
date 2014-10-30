package de.fhstralsund.project.core.screen;

import de.fhstralsund.project.core.GameScreen;
import de.fhstralsund.project.core.interfaces.IRenderable;
import de.fhstralsund.project.core.interfaces.IUpdateable;
import de.fhstralsund.project.resource.ResourceLoader;

public class Menu extends GameScreen implements IRenderable, IUpdateable {

    private ResourceLoader rl;

    public Menu(ResourceLoader rl) {
        this.rl = rl;
    }

    @Override
    public void setup() {

    }

    @Override
    public String getScreenName() {
        return "menu";
    }

    @Override
    public void render() {

    }

    @Override
    public void update() {

    }
}
