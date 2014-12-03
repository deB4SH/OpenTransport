package de.fhstralsund.project.core.screen.screens;


import de.fhstralsund.project.core.entity.CityController;
import de.fhstralsund.project.core.entity.EntityController;
import de.fhstralsund.project.core.entity.type.Street;
import de.fhstralsund.project.core.interfaces.IRenderable;
import de.fhstralsund.project.core.interfaces.IUpdateable;
import de.fhstralsund.project.core.screen.GameScreen;
import de.fhstralsund.project.core.map.Map;
import de.fhstralsund.project.core.io.ResourceLoader;
import de.fhstralsund.project.core.screen.gui.Gui;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

import java.security.Key;

public class Game extends GameScreen implements IRenderable, IUpdateable {

    private Map map;
    private ResourceLoader rl;
    private int mapSize;
    private Gui gui;

    public static int TILEWIDTH=64;
    public static int TILEHEIGHT =32;


    private EntityController buildingController, streetController;
    private CityController cityController;


    public Game(ResourceLoader rlo,int size) {
        this.map = new Map(size, rlo);
        this.rl = rlo;
        this.mapSize = size; //quad map
        this.gui = new Gui(rl);

        this.buildingController = new EntityController(mapSize);
        this.streetController = new EntityController(mapSize);
        this.cityController = new CityController();


        generateTestStreet();


    }

    @Override
    public void render(ResourceLoader rl) {
        map.render(rl);

        streetController.render(rl);
        buildingController.render(rl);

        gui.render(rl);
    }

    @Override
    public void update() {

        if(Keyboard.isKeyDown(Keyboard.KEY_B)) {
            this.gui.createBuildGui();
        }
        gui.update();
    }

    @Override
    public void setup() {
        map = new Map(50, rl);
    }

    @Override
    public String getScreenName() {
        return "game";
    }

    private void generateTestStreet(){
        for(int x=5; x<mapSize; x++){
            streetController.addEntity(new Street(new Vector2f(x,10),rl.getTexturesID("Street_NE.png"),true,true,true,true));
        }

    }
}
