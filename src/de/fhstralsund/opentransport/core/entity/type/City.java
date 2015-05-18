package de.fhstralsund.opentransport.core.entity.type;

import de.fhstralsund.opentransport.core.entity.Entity;
import de.fhstralsund.opentransport.core.entity.EntityController;
import de.fhstralsund.opentransport.core.entity.statics.StreetTID;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import de.fhstralsund.opentransport.core.screen.Camera;
import de.fhstralsund.opentransport.core.screen.screens.Game;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import sun.security.tools.keytool.Resources_sv;

import java.io.File;
import java.util.*;

public class City implements IUpdateable {

    private List<Building> cityBuilding;
    private String cityName;
    private Vector2f seed;
    private int startPopulation;
    private List<Vector2f> openBlocks;
    private Queue<Entity> buildQueue;
    LinkedList<Building> blist;

    private EntityController entityController;
    private ResourceLoader rl;


    public City(Vector2f seed, String cityName, int startPopulation, EntityController entityController, ResourceLoader rl){
        this.cityName = cityName;
        this.cityBuilding = new ArrayList<Building>();
        this.seed = seed;
        this.startPopulation = startPopulation;
        this.entityController = entityController;
        this.openBlocks = new ArrayList<Vector2f>();
        this.buildQueue = new LinkedList<Entity>();
        this.blist = new LinkedList<Building>();
        this.rl = rl;
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
        addStreet(new Street(new Vector2f(seed.getX() + 1, seed.getY()), StreetTID.urban_street_ns));
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

    public void expandCity(){
        Random rand = new Random(new Random().nextInt(Integer.MAX_VALUE));
        int width,height;
        boolean extendLeft,extendUp;

        if(this.openBlocks.size() > 0) {


            Vector2f castBlock = this.openBlocks.get(rand.nextInt(this.openBlocks.size()));
            this.openBlocks.remove(castBlock);
            if (rand.nextBoolean()) {
                width = rand.nextInt(5);
                height = 2;
            } else {
                width = 2;
                height = rand.nextInt(5);
            }

            if (this.entityController.isEntityOnVec((int) castBlock.getX() - 1, (int) castBlock.getY())) {
                extendLeft = false;
            } else {
                extendLeft = true;
            }
            if (this.entityController.isEntityOnVec((int) castBlock.getX(), (int) castBlock.getY() - 1)) {
                extendUp = false;
            } else {
                extendUp = true;
            }

            int multx, multy;
            if (extendLeft)
                multx = -1;
            else
                multx = 1;
            if (extendUp)
                multy = -1;
            else
                multy = 1;

            int minX, maxX, minY, maxY;
            if (extendLeft) {
                minX = (int) castBlock.getX() - width;
                maxX = (int) castBlock.getX();
            } else {
                maxX = (int) castBlock.getX() + width;
                minX = (int) castBlock.getX();
            }
            if (extendUp) {
                minY = (int) castBlock.getY();
                maxY = (int) castBlock.getY() + height;
            } else {
                minY = (int) castBlock.getY() - height;
                maxY = (int) castBlock.getY();
            }


            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    this.blist.add(new Building(new Vector2f(castBlock.getX() + x * multx, castBlock.getY() + y * multy), false, rl.getTextureID("res" + File.separator + "building" + File.separator + "house_01.png")));
                }
            }

            //generate block entites
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    Building bEntity = this.blist.poll();
                    this.buildQueue.add(bEntity);

                    createStreetNearBuilding((int) bEntity.getTilePos().getX() - 1, (int) bEntity.getTilePos().getY());
                    createStreetNearBuilding((int) bEntity.getTilePos().getX() + 1, (int) bEntity.getTilePos().getY());
                    createStreetNearBuilding((int) bEntity.getTilePos().getX(), (int) bEntity.getTilePos().getY() - 1);
                    createStreetNearBuilding((int) bEntity.getTilePos().getX(), (int) bEntity.getTilePos().getY() + 1);
                    //check diagonals
                    createStreetNearBuilding((int) bEntity.getTilePos().getX() - 1, (int) bEntity.getTilePos().getY() - 1);
                    createStreetNearBuilding((int) bEntity.getTilePos().getX() + 1, (int) bEntity.getTilePos().getY() + 1);
                    createStreetNearBuilding((int) bEntity.getTilePos().getX() - 1, (int) bEntity.getTilePos().getY() + 1);
                    createStreetNearBuilding((int) bEntity.getTilePos().getX() + 1, (int) bEntity.getTilePos().getY() - 1);
                }
            }

            lookForNewOpenBlocks(castBlock,width,height,multx,multy);
        }
    }

    private void createStreetNearBuilding(int x, int y){
        if(!this.entityController.isEntityOnVec(x,y)){
            if(!checkIfBuildingIsPlanned(x,y)){
                //point is not in building block, check if point is queued in building list
                boolean contained = false;
                for(Entity e: this.buildQueue){
                    if(e.getTilePos().getX() == x && e.getTilePos().getY() == y){
                        contained = true;
                    }
                }
                if(!contained){
                    this.buildQueue.add(new Street(new Vector2f(x,y),StreetTID.urban_street_ns));
                }
            }
        }
    }

    private void lookForNewOpenBlocks(Vector2f base,int width,int height,int multx,int multy){
        //point 1
        if(!this.entityController.isEntityOnVec((int)base.getX()+(width+1)*multx,(int)base.getY())){
            //check if this point is maybe already in opennodes
            if(!checkIfBuildingIsPlanned((int)base.getX()+(width+1)*multx,(int)base.getY())){
                //seems to be ok so add it
                this.openBlocks.add(new Vector2f((int)base.getX()+(width+1)*multx,(int)base.getY()));
            }
        }
        //point2
        if(!this.entityController.isEntityOnVec((int)base.getX()-1*multx,(int)base.getY())){
            //check if this point is maybe already in opennodes
            if(!checkIfBuildingIsPlanned((int)base.getX()-1*multx,(int)base.getY())){
                //seems to be ok so add it
                this.openBlocks.add(new Vector2f((int)base.getX()-1*multx,(int)base.getY()));
            }
        }
        //point3
        if(!this.entityController.isEntityOnVec((int)base.getX(),(int)base.getY()+(height+1)*multy)){
            //check if this point is maybe already in opennodes
            if(!checkIfBuildingIsPlanned((int)base.getX(),(int)base.getY()+(height+1)*multy)){
                //seems to be ok so add it
                this.openBlocks.add(new Vector2f((int)base.getX(),(int)base.getY()+(height+1)*multy));
            }
        }
        //point4
        if(!this.entityController.isEntityOnVec((int)base.getX(),(int)base.getY()-1*multy)){
            //check if this point is maybe already in opennodes
            if(!checkIfBuildingIsPlanned((int)base.getX(),(int)base.getY()-1*multy)){
                //seems to be ok so add it
                this.openBlocks.add(new Vector2f((int)base.getX(),(int)base.getY()-1*multy));
            }
        }
    }

    private boolean isPointInOpenNode(int x, int y){
        for(Vector2f e: this.openBlocks){
            if(e.getX() == x && e.getY() == y){
                return true;
            }
        }
        return false;
    }

    private boolean checkIfBuildingIsPlanned(int x,int y){
        for(Building b: this.blist){
            if(b.getTilePos().getX() == x && b.getTilePos().getY() == y){
                return true;
            }
        }
        return false;
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

    @Override
    public void update() {
        Entity e = this.buildQueue.poll();
        if(e != null){
            this.entityController.addEntity(e);
        }
        else{
            expandCity();
        }

    }

    public void setRl(ResourceLoader rl)
    {
        this.rl = rl;
    }
}