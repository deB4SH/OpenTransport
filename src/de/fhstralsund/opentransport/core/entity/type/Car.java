package de.fhstralsund.opentransport.core.entity.type;


import de.fhstralsund.opentransport.core.entity.Entity;
import de.fhstralsund.opentransport.core.entity.EntityController;
import de.fhstralsund.opentransport.core.entity.statics.CarID;
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
    private Depot depot = null;
    private Rectangle rectangle;

    private float age = 0.0f;
    private boolean breakdown = false;
    private float breakdownTime = 5.0f;

    private boolean isDelivering = true;
    private boolean isReturning = false;

    private boolean headingNE;
    private boolean headingSW;
    private boolean headingNW;
    private boolean headingSE;

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
        this.rectangle = new Rectangle(super.getTilePos().x, super.getTilePos().y, 0.45f, 0.45f); //small numbers, percentage image and 1 of position
    }

    @Override
    public void update() {

        Vector2f wayPoint = null;

        float movementX = 0f;
        float movementY = 0f;


        updateAgeAndBreakdown();

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
                        return;
                    }
                    if(isReturning){
                        isReturning = false;
                        isDelivering = true;
                    }
                }
            }
            movementX = (path.get(currentNode-1).x - path.get(currentNode).x);
            movementY = (path.get(currentNode-1).y - path.get(currentNode).y);

            if(movementX < 0 && movementY == 0) {
                super.setTextureID(CarID.car_wood_NE);
                headingNE = true;
                headingNW = headingSE = headingSW = false;
            }
            if(movementX > 0 && movementY <= 0) {
                super.setTextureID(CarID.car_wood_SW);
                headingSW = true;
                headingNW = headingSE = headingNE = false;
            }
            if(movementX >= 0 && movementY > 0) {
                super.setTextureID(CarID.car_wood_NW);
                headingNW = true;
                headingNE = headingSE = headingSW = false;
            }
            if(movementX <= 0 && movementY < 0) {
                super.setTextureID(CarID.car_wood_SE);
                headingSE = true;
                headingNW = headingNE = headingSW = false;
            }


        }

        // do cars should block each other ?
        // todo: performance, each car looks over each car
        this.rectangle.x = super.getTilePos().x;
        this.rectangle.y = super.getTilePos().y;

        for(Car c : this.entityController.getCars()) {
            if(c != this) {
                if (c.isDelivering && this.rectangle.contains(c.rectangle) && this.isDelivering && !this.isReturning && !c.isReturning
                        && !c.headingSW && !this.headingNE && !c.headingNW && !this.headingSE) {
                    if(wayPoint != null && distance(wayPoint, this.getTilePos()) > distance(wayPoint, c.getTilePos())) {
                        return;
                    }
                }
                else if (c.isReturning && this.rectangle.contains(c.rectangle) && this.isReturning && !this.isDelivering && !c.isDelivering
                        && !c.headingSW && !this.headingNE && !c.headingNW && !this.headingSE) {
                    if(wayPoint != null && distance(wayPoint, this.getTilePos()) > distance(wayPoint, c.getTilePos())) {
                        return;
                    }
                }
            }
        }

        this.setTilePos(new Vector2f(this.getTilePos().x - movementX / 20, this.getTilePos().y - movementY / 20));

    }

    private void updateAgeAndBreakdown() {

        if(age >= 10 && !breakdown) {
            if(new Random().nextInt(200) / 10 < 0.2 * (age / 2 )) {
                breakdown = true;
                age /= 1.5f;
            }
        }
        age += .002f;
        if(breakdown) {
            breakdownTime -= 0.05f;
            breakdown = breakdownTime <= 0 ? false : true;
            if(!breakdown)
                breakdownTime = new Random(20).nextFloat() + 10;
            return;
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

    public void setDepot(Depot depot) {
        this.depot = depot;
    }
}
