package de.fhstralsund.project.core.entity;

import de.fhstralsund.project.core.interfaces.IRenderable;
import de.fhstralsund.project.core.interfaces.IUpdateable;
import de.fhstralsund.project.core.io.ResourceLoader;

import java.util.List;

public class EntityController implements IRenderable, IUpdateable {

    private List<Entity> entityList;    //TODO: implement this as R-Tree (searchable and alot faster than ArrayList
    private int mapSize;

    public EntityController(int mapSize) {
        this.mapSize = mapSize;
    }

    public void addEntity(Entity entity){
        entityList.add(entity);
    }

    public boolean[][] getcollisionArray(int mapSize){

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

    }

    @Override
    public void render(ResourceLoader rl) {

    }
}
