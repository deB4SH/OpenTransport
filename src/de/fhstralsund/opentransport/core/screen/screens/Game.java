package de.fhstralsund.opentransport.core.screen.screens;


import de.fhstralsund.opentransport.core.pathfinding.Pathfinder;
import de.fhstralsund.opentransport.core.entity.CityController;
import de.fhstralsund.opentransport.core.entity.EntityController;
import de.fhstralsund.opentransport.core.entity.type.Industry;
import de.fhstralsund.opentransport.core.entity.type.Vegetation;
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

import java.io.File;
import java.util.Random;

public class Game extends GameScreen implements IRenderable, IUpdateable {

    private Map map;
    private ResourceLoader rl;
    private Vegetation vegatation;
    private int mapSize;
    private Gui gui;
    private Pathfinder pathfinder;

    public static int TILEWIDTH=64;
    public static int TILEHEIGHT =32;
    public static boolean MOUSESCROLL = false;


    private EntityController buildingController, streetController, carController, industryController;
    private CityController cityController;

    public Game(ResourceLoader rlo,int size) {
        this.map = new Map(size, rlo);
        this.rl = rlo;
        this.mapSize = size; //quad map
        this.gui = new Gui(rl);

        this.streetController = new EntityController(mapSize);
        this.carController = new EntityController(mapSize);
        this.cityController = new CityController();
        this.vegatation = new Vegetation(mapSize,rl,streetController);
        this.buildingController = new EntityController(mapSize);
        this.industryController = new EntityController(mapSize);

        //hand over veg
        this.streetController.setVeg(vegatation);
        this.industryController.setVeg(vegatation);
        this.buildingController.setVeg(vegatation);

        generateIndustry();
        generateTestStreet();

        this.cityController.spawnCity(new Vector2f(20,20),"Wuff",100,this.buildingController,this.streetController,rl);
        this.cityController.spawnCity(new Vector2f(60,18),"Moew",100,this.buildingController,this.streetController,rl);
        this.cityController.spawnCity(new Vector2f(35,40),"Blubb",100,this.buildingController,this.streetController,rl);

        generateCars();
        generateCities();

    }


    @Override
    public void render(ResourceLoader rl) {
        map.render(rl);

        streetController.render(rl);
        carController.render(rl);
        buildingController.render(rl);
        industryController.render(rl);
        vegatation.render(rl);
        gui.render(rl);
    }

    @Override
    public void update() {

        if(Keyboard.isKeyDown(Keyboard.KEY_B)) {
            this.gui.createStreetGui();
        }
        gui.update(streetController);
        buildingController.update();
        streetController.update();
        carController.update();
        industryController.update();
    }

    @Override
    public String getScreenName() {
        return "game";
    }

    private void generateTestStreet(){
        for(int x=1; x<mapSize / 4; x++){
            streetController.addEntity(new Street(new Vector2f(x,3), StreetTID.urban_street_ns));
        }
        for(int x=1; x<mapSize/ 4; x++){
            streetController.addEntity(new Street(new Vector2f(x,6), StreetTID.urban_street_ns));
        }
        for(int y=1; y<mapSize/ 4; y++){
            streetController.addEntity(new Street(new Vector2f(40,y), StreetTID.urban_street_we));
        }
        for(int y=1; y<mapSize/ 4; y++){
            streetController.addEntity(new Street(new Vector2f(4,y), StreetTID.urban_street_we));
        }
        for(int y=1; y<mapSize/ 4; y++){
            streetController.addEntity(new Street(new Vector2f(6,y), StreetTID.urban_street_we));
        }
        this.streetController.updateTexture();
    }

    private void generateCars() {
        this.pathfinder = new Pathfinder();
        this.carController.addEntity(new Car(new Vector2f(40,24), 20, false,
                this.pathfinder.findWay(this.streetController.getcollisionArray(), new Vector2f(40, 24), new Vector2f(4, 24)),
                streetController));

        this.carController.addEntity(new Car(new Vector2f(44,3), 20, false,
                this.pathfinder.findWay(this.streetController.getcollisionArray(), new Vector2f(44,3), new Vector2f(6, 9)),
                streetController));

        this.carController.addEntity(new Car(new Vector2f(40,50), 20, false,
                this.pathfinder.findWay(this.streetController.getcollisionArray(), new Vector2f(40,50), new Vector2f(6, 20)),
                streetController));

        this.carController.addEntity(new Car(new Vector2f(40,80), 20, false,
                this.pathfinder.findWay(this.streetController.getcollisionArray(), new Vector2f(40,80), new Vector2f(6, 30)),
                streetController));

        this.carController.addEntity(new Car(new Vector2f(40,100), 20, false,
                this.pathfinder.findWay(this.streetController.getcollisionArray(), new Vector2f(40,100), new Vector2f(6, 100)),
                streetController));
    }

    private void generateCities() {
        this.cityController.spawnCity(new Vector2f(20,20),"Wuff",100,this.buildingController,this.streetController,rl);
        this.cityController.spawnCity(new Vector2f(60,18),"Moew",100,this.buildingController,this.streetController,rl);
        this.cityController.spawnCity(new Vector2f(35,40),"Blubb",100,this.buildingController,this.streetController,rl);
    }

    private void generateIndustry() {

        for(int i  = 0; i < 100; i++) {

            Vector2f position = new Vector2f(new Random().nextInt(500) + 3, new Random().nextInt(500) +3);

            if( !streetController.isEntityOnVec(position)) {
                this.industryController.addEntity(new Industry(new Vector2f(position.getX(), position.getY()), false, "farm",
                        this.rl.getTextureID("res" + File.separator + "industry" + File.separator + "farm.png")));

                if (new Random().nextInt(10) % 2 == 0) {
                    this.industryController.addEntity(new Industry(new Vector2f(position.getX() + 1, position.getY()), false, "field",
                            this.rl.getTextureID("res" + File.separator + "industry" + File.separator + "field1.png")));
                }

                if (new Random().nextInt(10) % 2 == 0) {
                    this.industryController.addEntity(new Industry(new Vector2f(position.getX(), position.getY() - 1), false, "field",
                            this.rl.getTextureID("res" + File.separator + "industry" + File.separator + "field2.png")));
                }

                if (new Random().nextInt(10) % 2 == 0) {
                    this.industryController.addEntity(new Industry(new Vector2f(position.getX() - 1, position.getY()), false, "field",
                            this.rl.getTextureID("res" + File.separator + "industry" + File.separator + "field3.png")));
                }
            }
        }
    }
}
