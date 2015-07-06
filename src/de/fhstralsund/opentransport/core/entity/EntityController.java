package de.fhstralsund.opentransport.core.entity;

import de.fhstralsund.opentransport.core.entity.statics.BuildingStatic;
import de.fhstralsund.opentransport.core.entity.statics.StreetTID;
import de.fhstralsund.opentransport.core.entity.type.Car;
import de.fhstralsund.opentransport.core.entity.type.Depot;
import de.fhstralsund.opentransport.core.entity.type.Street;
import de.fhstralsund.opentransport.core.interfaces.IDailycycle;
import de.fhstralsund.opentransport.core.pathfinding.Pathfinder;
import de.fhstralsund.opentransport.core.entity.type.Vegetation;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import de.fhstralsund.opentransport.core.screen.Camera;
import de.fhstralsund.opentransport.core.screen.screens.Game;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.Point;
import org.lwjgl.util.ReadablePoint;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class EntityController implements IRenderable, IUpdateable, IDailycycle {

    private int mapSize;
    private static boolean[][] collisionMap;
    private Entity[][] entities;
    private List<Car> cars;
	private Pathfinder pathfinder;

    private Vegetation veg;

    private Depot dummyDepot; // dummy dummyDepot
    private Street dummyStreet; // dummy dummyStreet
    private boolean mouseUp = true;

    public EntityController(int mapSize) {
        this.mapSize = mapSize;
        this.entities = new Entity[this.mapSize][this.mapSize];
        this.cars = new ArrayList<Car>();
    }

    public void addEntity(Entity entity){
        //check if there is an entity on this vector
        if(!isEntityOnVec(entity.getTilePos()) && entity.getTilePos().getX() > 0 && entity.getTilePos().getY() > 0){
            this.entities[(int)entity.getTilePos().getX()][(int)entity.getTilePos().getY()] = entity;
            collisionMap = getcollisionArray();
            //remove veg if existing there
            if(veg != null && veg.isVegetationOn(entity.getTilePos())){
                veg.removeVegetationAt(entity.getTilePos());
            }
            //if tile == dummyStreet.class
            if(entity.getClass() == Street.class){
                spawnStreets(entity);
            }
        }
    }

    private void spawnStreets(Entity entity) {
        Vector2f seed = entity.getTilePos();
        entity.updateTexture(this); //central
        if(isEntityOnVec(new Vector2f(seed.getX()+1,seed.getY()))){ //north
            this.entities[(int)seed.getX()+1][(int)seed.getY()].updateTexture(this);
        }
        if(isEntityOnVec(new Vector2f(seed.getX()-1,seed.getY()))){ //south
            this.entities[(int)seed.getX()-1][(int)seed.getY()].updateTexture(this);
        }
        if(isEntityOnVec(new Vector2f(seed.getX(),seed.getY()+1))){ //east
            this.entities[(int)seed.getX()][(int)seed.getY()+1].updateTexture(this);
        }
        if(isEntityOnVec(new Vector2f(seed.getX(),seed.getY()-1))){ //west
            this.entities[(int)seed.getX()][(int)seed.getY()-1].updateTexture(this);
        }
    }

    public boolean[][] getcollisionArray(){
        boolean collisionarray[][] = new boolean[mapSize][mapSize];
        //fill with false(not moveable)
        for(int i=0;i<mapSize;i++){
            for(int j=0;j<mapSize;j++){
                collisionarray[i][j] = false;
            }
        }
        //place all enterable
        for(Entity[] e: this.entities){
            for(Entity entity: e) {
                if(entity != null){
                    if(entity.isEnterAble()) {
                        collisionarray[(int) entity.getTilePos().x][(int) entity.getTilePos().y] = true;
                    }
                }
            }
        }

        return collisionarray;
    }


    @Override
    public void update() {
        for(Entity[] e: this.entities){
            for(Entity entity: e){
                if(entity != null){
                    entity.update();
                }
            }
        }
        for(Car c : cars) {
            c.update();
        }

        updateDummyDepot();
        updateDummyStreet();
        mouseUp = Mouse.isButtonDown(0);
    }



    @Override
    public void render(ResourceLoader rl) {
        for(Entity[] e: this.entities){
            for(Entity entity: e){
                if(entity != null) {
                    entity.render(rl);
                }
            }
        }

        for(Car c : cars) {
            c.render(rl);
        }

        if(dummyDepot != null) {
            dummyDepot.render(rl);
        }
        if(dummyStreet != null) {
            dummyStreet.render(rl);
        }
    }

    public void updateTexture(){
        for(int x=0; x < this.mapSize; x++){
            for(int y=0; y < this.mapSize; y++){
                if(this.entities[x][y] != null){
                    this.entities[x][y].updateTexture(this);
                }
            }
        }
    }

    public boolean isEntityOnVec(Vector2f vec){
        if(vec.getX() < this.mapSize-1 && vec.getY() < this.mapSize-1 && vec.getX() > 0 &&  vec.getY() > 0){
            if(this.entities[(int)vec.getX()][(int)vec.getY()] != null){
                return true;
            }
        }
        return false;
    }

    public Entity getEntityVec(Vector2f vec){
        return this.entities[(int)vec.getX()][(int)vec.getY()];
    }

    public Entity getEntityVec(int x, int y){
        return this.entities[x][y];
    }

    public boolean isEntityOnVec(int x,int y){
        if(x < this.mapSize-1 && y < this.mapSize-1 && x > 0 &&  y > 0){
            if(this.entities[x][y] != null){
                return true;
            }
        }
        return false;
    }


    public static boolean[][] getCollisionMap() {
        return collisionMap;
    }

    public List<Vector2f> requestNewWay(Vector2f start, Vector2f target) {
        pathfinder = new Pathfinder();
        return this.pathfinder.findWay(getcollisionArray(), start, target);
    }

    public void setVeg(Vegetation veg) {
        this.veg = veg;
    }

    public void remove(Vector2f entityInArray) {
        entities[(int)entityInArray.x][(int)entityInArray.y] = null;
        collisionMap[(int)entityInArray.x][(int)entityInArray.y] = false;
    }

    public List<Car> getCars() {
        return cars;
    }

    public void addCar(Car c) {
        cars.add(c);
    }

    @Override
    public void dailyupdate() {
        for(int i=0; i < mapSize;i++){
            for(int j=0; j < mapSize; j++){
                if(entities[i][j] != null)
                    entities[i][j].dailyupdate();
            }
        }
    }

    public void buildDepot(){
        if(dummyStreet != null) {
            dummyStreet = null;
        }
        Camera cam = Camera.getInstance();
        ReadablePoint p = new Point(Mouse.getX(), -Mouse.getY() + cam.getRectangle().getHeight());
        dummyDepot = new Depot(new Vector2f(new Vector2f(p.getX(), p.getY())),
                false, BuildingStatic.depot, this);
    }

    private void updateDummyDepot() {
        if(dummyDepot != null) {
            dummyDepot.update();

            Camera cam = Camera.getInstance();
            ReadablePoint p = new Point(Mouse.getX(), -Mouse.getY() + cam.getRectangle().getHeight()); // invertieren weil windows andere koordinaten liefert

            float isoMouseX = Math.round(((p.getX() + cam.getPosition().getX()) / Game.TILEWIDTH) - ((p.getY() + cam.getPosition().getY()) / Game.TILEHEIGHT));
            float isoMouseY = Math.round(((p.getX() + cam.getPosition().getX()) / Game.TILEWIDTH) + ((p.getY() + cam.getPosition().getY()) / Game.TILEHEIGHT)) - 1;

            dummyDepot.setTilePos(new Vector2f(isoMouseX, isoMouseY));

            if (Mouse.isButtonDown(0) && !mouseUp) {
                this.addEntity(new Depot(new Vector2f(isoMouseX, isoMouseY), true, BuildingStatic.depot,
                        this));
                dummyDepot = null;
            }

            // right click - enough build
            if (Mouse.isButtonDown(1)) {
                dummyDepot = null;
            }
        }
    }

    public void buildSteet() {
        if(dummyDepot != null) {
            dummyDepot = null;
        }
        Camera cam = Camera.getInstance();
        ReadablePoint p = new Point(Mouse.getX(), -Mouse.getY() + cam.getRectangle().getHeight());
        dummyStreet = new Street(new Vector2f(p.getX(), p.getY()),StreetTID.urban_cross);

    }

    private void updateDummyStreet() {
        if(dummyStreet == null){
            return;
        }
        Camera cam = Camera.getInstance();
        ReadablePoint p = new Point(Mouse.getX(), -Mouse.getY() + cam.getRectangle().getHeight()); // invertieren weil windows andere koordinaten liefert

        float isoMouseX = Math.round(((p.getX() + cam.getPosition().getX()) / Game.TILEWIDTH) - ((p.getY() + cam.getPosition().getY()) / Game.TILEHEIGHT));
        float isoMouseY = Math.round(((p.getX() + cam.getPosition().getX()) / Game.TILEWIDTH) + ((p.getY() + cam.getPosition().getY()) / Game.TILEHEIGHT)) - 1;

        dummyStreet.setTilePos(new Vector2f(isoMouseX, isoMouseY));

        if (Mouse.isButtonDown(0) && !mouseUp) {
            this.addEntity(new Street(new Vector2f(isoMouseX, isoMouseY), StreetTID.urban_street_ns));
        }
        // right click - enough build
        if (Mouse.isButtonDown(1)) {
            dummyStreet = null;
        }
    }

    public Vegetation getVeg() {
        return veg;
    }
}
