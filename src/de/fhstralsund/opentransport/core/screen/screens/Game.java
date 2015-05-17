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
import de.fhstralsund.opentransport.core.screen.Camera;
import de.fhstralsund.opentransport.core.screen.GameScreen;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import de.fhstralsund.opentransport.core.screen.gui.Gui;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;

import java.io.File;
import java.util.Random;

public class Game extends GameScreen implements IRenderable, IUpdateable {


    private ResourceLoader rl;
    private Vegetation vegatation;
    private int mapSize;
    private Gui gui;
    private Pathfinder pathfinder;

    public static int TILEWIDTH=64;
    public static int TILEHEIGHT =32;
    public static boolean MOUSESCROLL = false;


    private EntityController entityController;
    private CityController cityController;

    public Game(ResourceLoader rlo,int size) {
        this.rl = rlo;
        this.mapSize = size; //quad map
        this.gui = new Gui(rl);

        //init controller
        this.entityController = new EntityController(this.mapSize);
        this.vegatation = new Vegetation(mapSize,rl,entityController);
        this.entityController.setVeg(vegatation);
        this.cityController = new CityController();

        generateTestStreet();
        generateCars();
        generateCities();
        generateIndustry();
    }


    @Override
    public void render(ResourceLoader rl) {
        this.renderGreen();
        this.entityController.render(rl);
        vegatation.render(rl);
        gui.render(rl);
        this.cityController.render(rl);
    }

    @Override
    public void update() {

        if(Keyboard.isKeyDown(Keyboard.KEY_B)) {
            this.gui.createStreetGui();
        }
        gui.update(this.entityController);
        this.entityController.update();
        this.cityController.update();
    }

    @Override
    public String getScreenName() {
        return "game";
    }

    private void generateTestStreet(){
        for(int x=1; x<mapSize / 4; x++){
            this.entityController.addEntity(new Street(new Vector2f(x,3), StreetTID.urban_street_ns));
        }
        for(int x=1; x<mapSize/ 4; x++){
            this.entityController.addEntity(new Street(new Vector2f(x, 6), StreetTID.urban_street_ns));
        }
        for(int y=1; y<mapSize/ 4; y++){
            this.entityController.addEntity(new Street(new Vector2f(40, y), StreetTID.urban_street_we));
        }
        for(int y=1; y<mapSize/ 4; y++){
            this.entityController.addEntity(new Street(new Vector2f(4, y), StreetTID.urban_street_we));
        }
        for(int y=1; y<mapSize/ 4; y++){
            this.entityController.addEntity(new Street(new Vector2f(6, y), StreetTID.urban_street_we));
        }
        this.entityController.updateTexture();
    }

    private void generateCars() {
        this.pathfinder = new Pathfinder();
        this.entityController.addEntity(new Car(new Vector2f(40, 24), 20, false,
                this.pathfinder.findWay(this.entityController.getcollisionArray(), new Vector2f(40, 24), new Vector2f(4, 24)),
                entityController));

        this.entityController.addEntity(new Car(new Vector2f(44,3), 20, false,
                this.pathfinder.findWay(this.entityController.getcollisionArray(), new Vector2f(44,3), new Vector2f(6, 9)),
                entityController));

        this.entityController.addEntity(new Car(new Vector2f(40,50), 20, false,
                this.pathfinder.findWay(this.entityController.getcollisionArray(), new Vector2f(40,50), new Vector2f(6, 20)),
                entityController));

        this.entityController.addEntity(new Car(new Vector2f(40,80), 20, false,
                this.pathfinder.findWay(this.entityController.getcollisionArray(), new Vector2f(40,80), new Vector2f(6, 30)),
                entityController));

        this.entityController.addEntity(new Car(new Vector2f(40, 100), 20, false,
                this.pathfinder.findWay(this.entityController.getcollisionArray(), new Vector2f(40, 100), new Vector2f(6, 100)),
                entityController));
    }

    private void generateCities() {
        this.cityController.spawnCity(new Vector2f(20,20),"Wuff",100,this.entityController,rl);
        this.cityController.spawnCity(new Vector2f(18,40),"Moew",100,this.entityController,rl);
        this.cityController.spawnCity(new Vector2f(35,40),"Blubb",100,this.entityController,rl);
    }

    private void generateIndustry() {
        for(int i  = 0; i < 100; i++) {

            Vector2f position = new Vector2f(new Random().nextInt(500) + 3, new Random().nextInt(500) +3);

            if(!entityController.isEntityOnVec(position)) {
                this.entityController.addEntity(new Industry(new Vector2f(position.getX(), position.getY()), false, "farm",
                        this.rl.getTextureID("res" + File.separator + "industry" + File.separator + "farm.png")));

                if (new Random().nextInt(10) % 2 == 0) {
                    this.entityController.addEntity(new Industry(new Vector2f(position.getX() + 1, position.getY()), false, "field",
                            this.rl.getTextureID("res" + File.separator + "industry" + File.separator + "field1.png")));
                }

                if (new Random().nextInt(10) % 2 == 0) {
                    this.entityController.addEntity(new Industry(new Vector2f(position.getX(), position.getY() - 1), false, "field",
                            this.rl.getTextureID("res" + File.separator + "industry" + File.separator + "field2.png")));
                }

                if (new Random().nextInt(10) % 2 == 0) {
                    this.entityController.addEntity(new Industry(new Vector2f(position.getX() - 1, position.getY()), false, "field",
                            this.rl.getTextureID("res" + File.separator + "industry" + File.separator + "field3.png")));
                }
            }
        }
    }

    private void renderGreen(){

        Color.white.bind();
        Camera cam = Camera.getInstance();

        float textureSizeByIDWidth = rl.getTextureSizeByIDWidth(this.rl.getTextureID("res" + File.separator + "landscape" + File.separator + "ground.png"));
        float textureSizeByIDHeight = rl.getTextureSizeByIDHeight(this.rl.getTextureID("res" + File.separator + "landscape" + File.separator + "ground.png"));
        rl.bindTextureByID(rl.getTextureID("res" + File.separator + "landscape" + File.separator + "ground.png"));

        for(int xTile= 0; xTile < this.mapSize; xTile++) {
            for (int yTile = 0; yTile < this.mapSize; yTile++) {
                float xPos = (xTile * Game.TILEWIDTH / 2) + (yTile * Game.TILEWIDTH / 2) - cam.getPosition().x;
                float yPos = (yTile * Game.TILEHEIGHT / 2) - (xTile * Game.TILEHEIGHT / 2) - cam.getPosition().y;
                //screenrelated render
                if ((xTile * Game.TILEWIDTH / 2) + (yTile * Game.TILEWIDTH / 2) + Game.TILEWIDTH >= cam.getPosition().x &&
                        (xTile * Game.TILEWIDTH / 2) + (yTile * Game.TILEWIDTH / 2) <= cam.getPosition().x + cam.getRectangle().getWidth() &&
                        (yTile * Game.TILEHEIGHT / 2) - (xTile * Game.TILEHEIGHT / 2) + Game.TILEHEIGHT >= cam.getPosition().getY() &&
                        (yTile * Game.TILEHEIGHT / 2) - (xTile * Game.TILEHEIGHT / 2) - Game.TILEHEIGHT / 2 <= cam.getPosition().getY() + cam.getRectangle().getHeight()) {

                    GL11.glBegin(GL11.GL_QUADS);
                    GL11.glTexCoord2f(0, 0);
                    GL11.glVertex2f(xPos, yPos);
                    GL11.glTexCoord2f(1, 0);
                    GL11.glVertex2f(xPos + textureSizeByIDWidth, yPos);
                    GL11.glTexCoord2f(1, 1);
                    GL11.glVertex2f(xPos + textureSizeByIDWidth, yPos + textureSizeByIDHeight);
                    GL11.glTexCoord2f(0, 1);
                    GL11.glVertex2f(xPos, yPos + textureSizeByIDHeight);
                    GL11.glEnd();
                }
            }
        }
    }

}
