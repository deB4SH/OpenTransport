package de.fhstralsund.opentransport.core.screen.screens;


import de.fhstralsund.opentransport.Pathfinding.Pathfinder;
import de.fhstralsund.opentransport.core.entity.CityController;
import de.fhstralsund.opentransport.core.entity.EntityController;
import de.fhstralsund.opentransport.core.entity.statics.StreetTID;
import de.fhstralsund.opentransport.core.entity.type.Car;
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
    private Pathfinder pathfinder;

    public static int TILEWIDTH=64;
    public static int TILEHEIGHT =32;
    public static boolean MOUSESCROLL = false;


    private EntityController buildingController, streetController, carController;
    private CityController cityController;


    public Game(ResourceLoader rlo,int size) {
        this.map = new Map(size, rlo);
        this.rl = rlo;
        this.mapSize = size; //quad map
        this.gui = new Gui(rl);

        this.buildingController = new EntityController(mapSize);
        this.streetController = new EntityController(mapSize);
        this.carController = new EntityController(mapSize);
        this.cityController = new CityController();

        generateTestStreet();


        generateCars();

        this.cityController.spawnCity(new Vector2f(20,20),"Stralsund",100,this.buildingController,this.streetController,rl);

    }

    @Override
    public void render(ResourceLoader rl) {
        map.render(rl);

        streetController.render(rl);
        carController.render(rl);
        buildingController.render(rl);

        gui.render(rl);
    }

    @Override
    public void update() {

        if(Keyboard.isKeyDown(Keyboard.KEY_B)) {
            this.gui.createStreetGui();
        }
        gui.update();

        buildingController.update();
        streetController.update();
        carController.update();
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
        for(int x=1; x<mapSize; x++){
            streetController.addEntity(new Street(new Vector2f(x,3), StreetTID.urban_street_ns));
        }
        for(int x=1; x<mapSize; x++){
            streetController.addEntity(new Street(new Vector2f(x,6), StreetTID.urban_street_ns));
        }
        for(int y=1; y<mapSize; y++){
            streetController.addEntity(new Street(new Vector2f(40,y), StreetTID.urban_street_we));
        }
        for(int y=1; y<mapSize; y++){
            streetController.addEntity(new Street(new Vector2f(4,y), StreetTID.urban_street_we));
        }
        for(int y=1; y<mapSize; y++){
            streetController.addEntity(new Street(new Vector2f(6,y), StreetTID.urban_street_we));
        }
    }

    private void generateCars() {
        this.pathfinder = new Pathfinder();
        this.carController.addEntity(new Car(new Vector2f(40,24), 20, false,
                this.pathfinder.findWay(this.streetController.getcollisionArray(), new Vector2f(40, 24), new Vector2f(2, 3))));
        this.carController.addEntity(new Car(new Vector2f(44,3), 20, false,
                this.pathfinder.findWay(this.streetController.getcollisionArray(), new Vector2f(44,3), new Vector2f(6, 9))));
    }
}
