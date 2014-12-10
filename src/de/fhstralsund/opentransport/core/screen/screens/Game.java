package de.fhstralsund.opentransport.core.screen.screens;


import de.fhstralsund.opentransport.core.entity.CityController;
import de.fhstralsund.opentransport.core.entity.EntityController;
import de.fhstralsund.opentransport.core.entity.statics.StreetTID;
import de.fhstralsund.opentransport.core.entity.type.Street;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.screen.GameScreen;
import de.fhstralsund.opentransport.core.map.Map;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import de.fhstralsund.opentransport.core.screen.gui.Gui;
import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

public class Game extends GameScreen implements IRenderable, IUpdateable {

    private Map map;
    private ResourceLoader rl;
    private int mapSize;
    private Gui gui;

    public static int TILEWIDTH=64;
    public static int TILEHEIGHT =32;
    public static boolean MOUSESCROLL = false;


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
            this.gui.createStreetGui();
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
            streetController.addEntity(new Street(new Vector2f(x,10), StreetTID.urban_street_ns,true,true,true,true));
        }

    }
}
