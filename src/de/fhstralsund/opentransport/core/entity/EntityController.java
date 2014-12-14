package de.fhstralsund.opentransport.core.entity;

import de.fhstralsund.opentransport.core.entity.type.Street;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class EntityController implements IRenderable, IUpdateable {

    private int mapSize;
    private static boolean[][] collisionMap;
    private Entity[][] entities;

    public EntityController(int mapSize) {
        this.mapSize = mapSize;
        this.entities = new Entity[this.mapSize][this.mapSize];
    }

    public void addEntity(Entity entity){
        //check if there is an entity on this vector
        if(!isEntityOnVec(entity.getTilePos())){
            this.entities[(int)entity.getTilePos().getX()][(int)entity.getTilePos().getY()] = entity;
            collisionMap = getcollisionArray();

            //if street update next tiles
            if(entity.getClass() == Street.class){
                Vector2f seed = entity.getTilePos();
                entity.updateTexture(this); //central
                if(this.entities[(int)seed.getX()+1][(int)seed.getY()] != null){ //north
                    this.entities[(int)seed.getX()+1][(int)seed.getY()].updateTexture(this);
                }
                if(this.entities[(int)seed.getX()-1][(int)seed.getY()] != null){ //south
                    this.entities[(int)seed.getX()-1][(int)seed.getY()].updateTexture(this);
                }
                if(this.entities[(int)seed.getX()][(int)seed.getY()+1] != null){ //east
                    this.entities[(int)seed.getX()][(int)seed.getY()+1].updateTexture(this);
                }
                if(this.entities[(int)seed.getX()][(int)seed.getY()-1] != null){ //west
                    this.entities[(int)seed.getX()][(int)seed.getY()-1].updateTexture(this);
                }
            }
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
    }

    public boolean isEntityOnVec(Vector2f vec){
        if(vec.getX() < this.mapSize-1 && vec.getY() < this.mapSize-1 && vec.getX() > 0 &&  vec.getY() > 0){
            if(this.entities[(int)vec.getX()][(int)vec.getY()] != null){
                return true;
            }
        }
        return false;
    }

    public static boolean[][] getCollisionMap() {
        return collisionMap;
    }
}
