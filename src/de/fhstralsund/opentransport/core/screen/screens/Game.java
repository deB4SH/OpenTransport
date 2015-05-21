package de.fhstralsund.opentransport.core.screen.screens;

import de.fhstralsund.opentransport.core.entity.statics.IndustryType;
import de.fhstralsund.opentransport.core.entity.type.*;
import de.fhstralsund.opentransport.core.pathfinding.Pathfinder;
import de.fhstralsund.opentransport.core.entity.CityController;
import de.fhstralsund.opentransport.core.entity.EntityController;
import de.fhstralsund.opentransport.core.entity.statics.StreetTID;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.screen.Camera;
import de.fhstralsund.opentransport.core.screen.GameScreen;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import de.fhstralsund.opentransport.core.screen.gui.Gui;
import de.fhstralsund.opentransport.core.screen.ui.UserInterface;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;
import org.lwjgl.util.ReadablePoint;
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
    private UserInterface ui;

    public static int TILEWIDTH=64;
    public static int TILEHEIGHT =32;
    public static boolean MOUSESCROLL = false;


    private EntityController entityController;
    private CityController cityController;

    private Depot depot;
    private int frameCount;

    public Game(ResourceLoader rlo,int size) {
        this.rl = rlo;
        this.mapSize = size; //quad map
        //this.gui = new Gui(rl);
        this.ui = new UserInterface(rlo);

        this.frameCount = 0;

        //init controller
        this.entityController = new EntityController(this.mapSize);
        this.vegatation = new Vegetation(mapSize,rl,entityController);
        this.entityController.setVeg(vegatation);
        this.cityController = new CityController();

        generateTestStreet();
        generateCars();
        generateCities();
        generateIndustry();



        Camera cam = Camera.getInstance();
        ReadablePoint p = new Point(Mouse.getX(), -Mouse.getY() + cam.getRectangle().getHeight());
        depot = new Depot(new Vector2f(new Vector2f(p.getX(), p.getY())),
                false, rl.getTextureID("res"+ File.separator+"building"+File.separator+"road_Depot.png"),
                this.entityController);
    }


    @Override
    public void render(ResourceLoader rl) {
        this.renderGreen();
        this.entityController.render(rl);
        vegatation.render(rl);
        //gui.render(rl);
        this.cityController.render(rl);
        this.ui.render(rl);
		depot.render(rl);
    }

    @Override
    public void update() {

        if(Keyboard.isKeyDown(Keyboard.KEY_B)) {
            this.gui.createStreetGui();
        }
        //gui.update(this.entityController);
        this.entityController.update();
        this.cityController.update();
        this.ui.update();

        this.frameCount++;
        if(this.frameCount % 100 == 0){
            this.entityController.dailyupdate();
            this.frameCount = 0;
            System.out.println("NEW DAY");
        }


        // TODO: fliegt raus wenn gui vorhanden ist
        ///////////
        depot.update();

        Camera cam = Camera.getInstance();
        ReadablePoint p = new Point(Mouse.getX(), -Mouse.getY() + cam.getRectangle().getHeight()); // invertieren weil windows andere koordinaten liefert


        float isoMouseX = Math.round(((p.getX() + cam.getPosition().getX()) / Game.TILEWIDTH) - ((p.getY() + cam.getPosition().getY()) / Game.TILEHEIGHT));
        float isoMouseY = Math.round(((p.getX()  + cam.getPosition().getX()) / Game.TILEWIDTH) +  ((p.getY() + cam.getPosition().getY()) / Game.TILEHEIGHT))-1;

        depot.setTilePos(new Vector2f(isoMouseX, isoMouseY));

        if(Mouse.isButtonDown(0)) {
            entityController.addEntity(new Depot(new Vector2f(isoMouseX, isoMouseY),true, rl.getTextureID("res"+ File.separator+"building"+File.separator+"road_Depot.png"),
                    this.entityController));
        }
        ///////

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
        this.entityController.addCar(new Car(new Vector2f(40, 24), 20, false,
                this.pathfinder.findWay(this.entityController.getcollisionArray(), new Vector2f(40, 24), new Vector2f(4, 24)),
                entityController));

        this.entityController.addCar(new Car(new Vector2f(44, 3), 20, false,
                this.pathfinder.findWay(this.entityController.getcollisionArray(), new Vector2f(44, 3), new Vector2f(6, 9)),
                entityController));

        this.entityController.addCar(new Car(new Vector2f(40, 50), 20, false,
                this.pathfinder.findWay(this.entityController.getcollisionArray(), new Vector2f(40, 50), new Vector2f(6, 20)),
                entityController));

        this.entityController.addCar(new Car(new Vector2f(40, 80), 20, false,
                this.pathfinder.findWay(this.entityController.getcollisionArray(), new Vector2f(40, 80), new Vector2f(6, 30)),
                entityController));

        this.entityController.addCar(new Car(new Vector2f(40, 100), 20, false,
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

            int x = new Random().nextInt(500) + 3;
            int y  =new Random().nextInt(500) + 3;

            Vector2f position = new Vector2f(x, y);

            if(!entityController.isEntityOnVec(position) &&!entityController.isEntityOnVec(new Vector2f(x+1,y)) &&
                    !entityController.isEntityOnVec(new Vector2f(x,y+1))
                    ) {
                this.entityController.addEntity(new Industry(new Vector2f(position.getX(), position.getY()), false, IndustryType.Farm,
                        this.rl.getTextureID("res" + File.separator + "industry" + File.separator + "farm.png"),
                        entityController, rl));
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
