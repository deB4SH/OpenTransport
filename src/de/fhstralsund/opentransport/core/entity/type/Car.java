package de.fhstralsund.opentransport.core.entity.type;


import de.fhstralsund.opentransport.core.entity.Entity;
import de.fhstralsund.opentransport.core.entity.EntityController;
import de.fhstralsund.opentransport.core.entity.statics.CarID;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Car extends Entity implements IRenderable, IUpdateable{

    private List<Vector2f> path;
    private int currentNode = 0;
    private EntityController entityController = null;
    private Depot depot = null;

    public Car(Vector2f tilePos, int textureID, Boolean enterAble) {
        super(tilePos, enterAble);
        super.setTextureID(textureID);
        this.path = new ArrayList<Vector2f>();
    }

    public Car(Vector2f tilePos, int textureID, Boolean enterAble, List<Vector2f> path) {
        super(tilePos, enterAble);
        super.setTextureID(textureID);
        this.path = path;
    }

    public Car(Vector2f tilePos, int textureID, boolean enterAble, List<Vector2f> path, EntityController entityController) {
        super(tilePos, enterAble);
        super.setTextureID(textureID);
        this.path = path;
        this.entityController = entityController;
    }

    @Override
    public void update() {
        Vector2f wayPoint;

        if (path != null && path.size() != 1) {
            wayPoint = path.get(currentNode);
            if(distance(wayPoint, this.getTilePos()) <= 0.05f) {
                currentNode++;
                if(currentNode == path.size()) {
                    //path = null;
                    //currentNode = 0;
                    //return;
                    currentNode = 1;
                    requestNewWay();
                }
            }
            float movementX = (path.get(currentNode-1).x - path.get(currentNode).x);
            float movementY = (path.get(currentNode-1).y - path.get(currentNode).y);
            if(movementX < 0 && movementY == 0) {
                super.setTextureID(CarID.car_wood_NE);
            }
            if(movementX > 0 && movementY <= 0) {
                super.setTextureID(CarID.car_wood_SW);
            }
            if(movementX >= 0 && movementY > 0) {
                super.setTextureID(CarID.car_wood_NW);
            }
            if(movementX <= 0 && movementY < 0) {
                super.setTextureID(CarID.car_wood_SE);
            }

            this.setTilePos(new Vector2f(this.getTilePos().x - movementX / 20, this.getTilePos().y - movementY / 20));
        }
    }

    @Override
    public void render(ResourceLoader rl) {
        super.render(rl);
    }

    public void setPath(List<Vector2f> path) {
        this.path = path;
    }

    private float distance(Vector2f wayPoint, Vector2f tilePos) {
        return (float)Math.sqrt((wayPoint.x - tilePos.x) * (wayPoint.x - tilePos.x) + (wayPoint.y - tilePos.y) * (wayPoint.y - tilePos.y));
    }

    private void requestNewWay() {
        if(entityController != null) {
            path = this.entityController.requestNewWay(path.get(path.size() -1), path.get(0));        }
    }
}
