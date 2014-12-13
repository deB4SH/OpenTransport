package de.fhstralsund.opentransport.core.entity;

import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class EntityController implements IRenderable, IUpdateable {

    private List<Entity> entityList;    //TODO: implement this as R-Tree (searchable and alot faster than ArrayList)
    private int mapSize;
    private static boolean[][] collisionMap;

    public EntityController(int mapSize) {
        this.mapSize = mapSize;
        this.entityList = new ArrayList<Entity>();
    }

    public void addEntity(Entity entity){
        //check if there is an entity on this vector
        if(!isEntityOnVec(entity.getTilePos())){
            this.entityList.add(entity);
            collisionMap = getcollisionArray();
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
        for(Entity e: this.entityList){
            if(e.isEnterAble()){
                collisionarray[(int)e.getTilePos().x][(int)e.getTilePos().y] = true;
            }
        }

        return collisionarray;
    }


    @Override
    public void update() {
        for(Entity e: this.entityList){
            e.update();
        }
    }

    @Override
    public void render(ResourceLoader rl) {
        for(Entity e: this.entityList){
            e.render(rl);
        }
    }

    private boolean isEntityOnVec(Vector2f vec){
        for(Entity e: this.entityList){
            if(e.getTilePos() == vec){
                return true;
            }
        }
        return false;
    }

    public static boolean[][] getCollisionMap() {
        return collisionMap;
    }
}
