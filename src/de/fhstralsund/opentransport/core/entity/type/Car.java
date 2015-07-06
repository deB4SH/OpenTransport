package de.fhstralsund.opentransport.core.entity.type;


import de.fhstralsund.opentransport.core.entity.Entity;
import de.fhstralsund.opentransport.core.entity.EntityController;
import de.fhstralsund.opentransport.core.entity.statics.CarID;
import de.fhstralsund.opentransport.core.entity.statics.CarType;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import de.fhstralsund.opentransport.core.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Car extends Entity implements IRenderable, IUpdateable{

    private List<Vector2f> path;
    private int currentNode = 0;
    private EntityController entityController = null;

    private Depot startDepot = null;
    private Depot endDepot = null;

    private Rectangle rectangle;

    private float age = 0.0f;
    private boolean breakdown = false;
    private float breakdownTime = 5.0f;

    private int speed = 20;
    private CarType carType;

    private boolean isDelivering = true;
    private boolean isReturning = false;

    private Storage storage;

    enum heading {
        NE,
        SW,
        NW,
        SE
    }

    private heading isHeading;

    public Car(Vector2f tilePos, int textureID, Boolean enterAble) {
        super(tilePos, enterAble);
        super.setTextureID(textureID);
        this.path = new ArrayList<Vector2f>();
        this.storage = new Storage(this);
    }

    public Car(Vector2f tilePos, int textureID, Boolean enterAble, List<Vector2f> path) {
        super(tilePos, enterAble);
        super.setTextureID(textureID);
        this.path = path;
        this.storage = new Storage(this);
    }

    public Car(Vector2f tilePos, int textureID, boolean enterAble, List<Vector2f> path, EntityController entityController) {
        super(tilePos, enterAble);
        super.setTextureID(textureID);
        this.path = path;
        this.entityController = entityController;
        this.rectangle = new Rectangle(super.getTilePos().x, super.getTilePos().y, 0.5f, 0.5f); //small numbers, percentage image and 1 of position
        this.storage = new Storage(this);
    }

    public Car(Vector2f tilePos, int textureID, boolean enterAble, Vector2f target, EntityController entityController) {
        super(tilePos, enterAble);
        super.setTextureID(textureID);
        this.entityController = entityController;
        this.rectangle = new Rectangle(super.getTilePos().x, super.getTilePos().y, 0.5f, 0.5f); //small numbers, percentage image and 1 of position
        this.path = entityController.requestNewWay(tilePos, target);
        this.storage = new Storage(this);
    }


    @Override
    public void update() {

        if(startDepot == null && endDepot == null && path != null) {
            startDepot = (Depot)entityController.getEntityVec((int)path.get(0).getX(), (int)path.get(0).getY());
            startDepot.requestRessources(this.storage);
            endDepot = (Depot)entityController.getEntityVec((int)path.get(path.size()-1).getX(), (int)path.get(path.size()-1).getY());
        }

        Vector2f wayPoint = null;

        float movementX = 0f;
        float movementY = 0f;


        if(updateAgeAndBreakdown()) {
            return;
        }

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

                    if(isDelivering) {
                        isDelivering = false;
                        isReturning = true;
                        endDepot.giveRessources(this.storage);
                        return;
                    }
                    if(isReturning){
                        isReturning = false;
                        isDelivering = true;
                        startDepot.requestRessources(this.storage);
                    }
                }
            }
            movementX = (path.get(currentNode-1).x - path.get(currentNode).x);
            movementY = (path.get(currentNode-1).y - path.get(currentNode).y);

            if(movementX < 0 && movementY == 0) {
                super.setTextureID(CarID.car_wood_NE);
                isHeading = heading.NE;
            }
            if(movementX > 0 && movementY <= 0) {
                super.setTextureID(CarID.car_wood_SW);
                isHeading = heading.SW;
            }
            if(movementX >= 0 && movementY > 0) {
                super.setTextureID(CarID.car_wood_NW);
                isHeading = heading.NW;
            }
            if(movementX <= 0 && movementY < 0) {
                super.setTextureID(CarID.car_wood_SE);
                isHeading = heading.SE;
            }


        }

        // should cars block each other ?
        // todo: performance, each car looks over each car, quadtree maybe ?
        this.rectangle.x = super.getTilePos().x;
        this.rectangle.y = super.getTilePos().y;

        for(Car c : this.entityController.getCars()) {
            if(c != this) {
                if (c.isDelivering && this.rectangle.contains(c.rectangle) && this.isDelivering && !this.isReturning && !c.isReturning
                        && isHeading == c.isHeading) {
                    if(wayPoint != null && distance(wayPoint, this.getTilePos()) > distance(wayPoint, c.getTilePos())) {
                        return;
                    }
                }
                else if (c.isReturning && this.rectangle.contains(c.rectangle) && this.isReturning && !this.isDelivering && !c.isDelivering
                        && isHeading == c.isHeading) {
                    if(wayPoint != null && distance(wayPoint, this.getTilePos()) > distance(wayPoint, c.getTilePos())) {
                        return;
                    }
                }
            }
        }

        this.setTilePos(new Vector2f(this.getTilePos().x - movementX / speed, this.getTilePos().y - movementY / speed));

    }

    private boolean updateAgeAndBreakdown() {

        if(age >= 10 && !breakdown) {
            if(new Random(200).nextFloat() / 10f < 0.2f * (age / 2.0f )) {
                breakdown = true;
                age = age / 1.5f;
            }
        }
        age += .002f;
        if(breakdown) {
            breakdownTime -= 0.05f;
            breakdown = breakdownTime <= 0 ? false : true;
            if(!breakdown)
                breakdownTime = new Random(20).nextFloat() + 10;
            return true;
        }

        return false;
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

    public void setDepot(Depot depot) {
        this.startDepot = depot;
    }

    public Depot getDepot() {
        return startDepot;
    }
}
