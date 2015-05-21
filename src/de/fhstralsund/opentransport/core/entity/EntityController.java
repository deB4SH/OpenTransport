package de.fhstralsund.opentransport.core.entity;

import de.fhstralsund.opentransport.core.entity.type.Car;
import de.fhstralsund.opentransport.core.entity.type.Street;
import de.fhstralsund.opentransport.core.interfaces.IDailycycle;
import de.fhstralsund.opentransport.core.pathfinding.Pathfinder;
import de.fhstralsund.opentransport.core.entity.type.Vegetation;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
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

    public EntityController(int mapSize) {
        this.mapSize = mapSize;
        this.entities = new Entity[this.mapSize][this.mapSize];
        this.cars = new ArrayList<Car>();
    }


    public void addEntity(Entity entity){
        //check if there is an entity on this vector
        if(!isEntityOnVec(entity.getTilePos())){
            this.entities[(int)entity.getTilePos().getX()][(int)entity.getTilePos().getY()] = entity;
            collisionMap = getcollisionArray();
            //remove veg if existing there
            if(veg != null && veg.isVegetationOn(entity.getTilePos())){
                veg.removeVegetationAt(entity.getTilePos());
            }
            //if tile == street.class
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
}
