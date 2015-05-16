package de.fhstralsund.opentransport.core.entity.type;

import de.fhstralsund.opentransport.core.entity.Entity;
import de.fhstralsund.opentransport.core.entity.EntityController;
import de.fhstralsund.opentransport.core.entity.statics.StreetTID;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import de.fhstralsund.opentransport.core.screen.Camera;
import de.fhstralsund.opentransport.core.screen.screens.Game;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import sun.security.tools.keytool.Resources_sv;

import java.io.File;
import java.util.*;

public class City {

    private List<Building> cityBuilding;
    private String cityName;
    private Vector2f seed;
    private int startPopulation;
    private List<Vector2f> openBlocks;
    private Queue<Entity> buildQueue;

    private EntityController entityController;


    public City(Vector2f seed, String cityName, int startPopulation, EntityController entityController){
        this.cityName = cityName;
        this.cityBuilding = new ArrayList<Building>();
        this.seed = seed;
        this.startPopulation = startPopulation;
        this.entityController = entityController;
        this.openBlocks = new ArrayList<Vector2f>();
        this.buildQueue = new PriorityQueue<Entity>();
    }

    public void addBuilding(Building e){
        this.cityBuilding.add(e);
        this.entityController.addEntity(e);
    }

    public void addStreet(Street e){
        this.entityController.addEntity(e);
    }

    public void generateCity(ResourceLoader rl){
        //place at seed a street
        addStreet(new Street(seed, StreetTID.urban_cross));

        //attach some extending street points
        //north
        addStreet(new Street(new Vector2f(seed.getX()+1,seed.getY()), StreetTID.urban_street_ns));
        this.openBlocks.add(new Vector2f(seed.getX() + 1, seed.getY() + 1));//left north street
        this.openBlocks.add(new Vector2f(seed.getX() + 1, seed.getY() - 1));//right north street
        //south
        addStreet(new Street(new Vector2f(seed.getX() - 1, seed.getY()), StreetTID.urban_street_ns));
        this.openBlocks.add(new Vector2f(seed.getX() - 1, seed.getY() + 1));//left north street
        this.openBlocks.add(new Vector2f(seed.getX() - 1, seed.getY() - 1));//right north street
        //east
        addStreet(new Street(new Vector2f(seed.getX(), seed.getY() + 1), StreetTID.urban_street_we));
        //west
        addStreet(new Street(new Vector2f(seed.getX(),seed.getY()-1), StreetTID.urban_street_we));
    }

    public void expandCity(ResourceLoader rl){
        Random rand = new Random();
        int width,height;
        boolean extendLeft,extendUp;

        Vector2f castBlock = this.openBlocks.get(rand.nextInt(this.openBlocks.size()));
        this.openBlocks.remove(castBlock);
        if(rand.nextBoolean()){width=rand.nextInt(5);height=2;}
        else{width=2;height=rand.nextInt(5);}

        if(this.entityController.isEntityOnVec((int)castBlock.getX()-1,(int)castBlock.getY())){
            extendLeft = false;
        }
        else{
            extendLeft = true;
        }
        if(this.entityController.isEntityOnVec((int)castBlock.getX(),(int)castBlock.getY()-1)){
            extendUp = false;
        }
        else{
            extendUp = true;
        }


        int multx, multy;
        if(extendLeft)
            multx = -1;
        else
            multx = 1;
        if(extendUp)
            multy = -1;
        else
            multy = 1;


        //generate block entites
        for(int i=0;i<width; i++){
            for(int j=0;j<height;j++){
                this.buildQueue.add(new Building(castBlock,false,rl.getTextureID("res" + File.separator + "building" + File.separator + "house_01.png")));
            }
        }
    }

    public void showOpenblocks(ResourceLoader rl){

        int textureID = rl.getTextureID("res" + File.separator + "debug" + File.separator + "floordebug.png");
        rl.bindTextureByID(textureID);
        for(Vector2f e: this.openBlocks){
            Camera cam = Camera.getInstance(); //TODO: rework to Controller/Object
            float xpos = (e.getX() * Game.TILEWIDTH / 2) + (e.getY() * Game.TILEWIDTH / 2) - cam.getPosition().x;
            float ypos = ((e.getY() * Game.TILEHEIGHT / 2) - (e.getX() * Game.TILEHEIGHT / 2) - cam.getPosition().y);
            //screenrelated render
            if((e.getX() * Game.TILEWIDTH / 2 ) + ( e.getY() * Game.TILEWIDTH / 2 ) + Game.TILEWIDTH >= cam.getPosition().x &&
                    (e.getX() * Game.TILEWIDTH / 2 ) + ( e.getY() * Game.TILEWIDTH / 2 ) <= cam.getPosition().x + cam.getRectangle().getWidth() &&
                    (e.getY() * Game.TILEHEIGHT / 2 ) - ( e.getX() * Game.TILEHEIGHT / 2 ) + Game.TILEHEIGHT >= cam.getPosition().getY() &&
                    (e.getY() * Game.TILEHEIGHT / 2) - (e.getX() * Game.TILEHEIGHT / 2) - Game.TILEHEIGHT / 2 <= cam.getPosition().getY() + cam.getRectangle().getHeight()) {

                GL11.glBegin(GL11.GL_QUADS);
                    GL11.glTexCoord2f(0, 0);
                    GL11.glVertex2f(xpos, ypos);
                    GL11.glTexCoord2f(1, 0);
                    GL11.glVertex2f(xpos + rl.getTextureSizeByIDWidth(textureID), ypos);
                    GL11.glTexCoord2f(1, 1);
                    GL11.glVertex2f(xpos + rl.getTextureSizeByIDWidth(textureID), ypos + rl.getTextureSizeByIDHeight(textureID));
                    GL11.glTexCoord2f(0, 1);
                    GL11.glVertex2f(xpos, ypos + rl.getTextureSizeByIDHeight(textureID));
                GL11.glEnd();
            }
        }
    }
}