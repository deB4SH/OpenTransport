package de.fhstralsund.opentransport.core.entity.type;

import de.fhstralsund.opentransport.core.entity.Entity;
import de.fhstralsund.opentransport.core.interfaces.IDailycycle;
import de.fhstralsund.opentransport.core.entity.EntityController;
import de.fhstralsund.opentransport.core.entity.statics.IndustryType;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import de.fhstralsund.opentransport.core.screen.Camera;
import org.lwjgl.util.vector.Vector2f;

import java.io.File;
import java.util.Random;


public class Industry extends Entity implements IUpdateable, IRenderable, IDailycycle{

    private IndustryType type;
    private EntityController entityController;
    private int availableGeneratingComponents = 0;
    private int availableAmount = 0;

    public Industry(Vector2f tilePos, Boolean enterAble, IndustryType type, int textureID) {
        super(tilePos, enterAble);
        this.type = type;
        this.setTextureID(textureID);
    }

    public Industry(Vector2f tilePos, Boolean enterAble, IndustryType type, int textureID, EntityController entityController, ResourceLoader rl) {
        super(tilePos, enterAble);
        this.type = type;
        this.setTextureID(textureID);
        this.entityController  = entityController;


        if(type == IndustryType.Farm) {
            generateFields(tilePos, rl);
        }
        if(type == IndustryType.Wood) {
            checkForAvailableWood(entityController);
        }
    }

    private void checkForAvailableWood(EntityController entityController) {
        Camera cam = Camera.getInstance();
        int forestAround = 0;
        for(int i = -3; i <= 3; i++) {
            for(int j = -3; j <= 3; j++) {
                if(super.getTilePos().getX() + i > 0 && super.getTilePos().getX() + i < cam.getSize() && super.getTilePos().getY() + j > 0 && super.getTilePos().getY() + j < cam.getSize()) {
                    if (entityController.getVeg().isVegetationOn((int) super.getTilePos().getX() + i, (int) super.getTilePos().getY() + j)) {
                        forestAround++;
                    }
                }
            }
        }
        availableGeneratingComponents = forestAround;
        forestAround = 0;
    }

    private void generateFields(Vector2f tilePos, ResourceLoader rl) {

        if (new Random().nextInt(10) % 2 == 0) {
            this.entityController.addEntity(new Industry(new Vector2f(tilePos.getX() + 1, tilePos.getY()), false, IndustryType.Field,
                    rl.getTextureID("res" + File.separator + "industry" + File.separator + "field1.png")));
            availableGeneratingComponents++;
        }

        if (new Random().nextInt(10) % 2 == 0) {
            this.entityController.addEntity(new Industry(new Vector2f(tilePos.getX(), tilePos.getY() - 1), false, IndustryType.Field,
                    rl.getTextureID("res" + File.separator + "industry" + File.separator + "field2.png")));
            availableGeneratingComponents++;
        }

        if (new Random().nextInt(10) % 2 == 0) {
            this.entityController.addEntity(new Industry(new Vector2f(tilePos.getX() - 1, tilePos.getY()), false, IndustryType.Field,
                    rl.getTextureID("res" + File.separator + "industry" + File.separator + "field3.png")));
            availableGeneratingComponents++;
        }

    }

    @Override
    public void render(ResourceLoader rl) {
        super.render(rl);
    }

    @Override
    public void update() {

    }

    @Override
    public void dailyupdate(){
        int toAdd = 30 * availableGeneratingComponents;
        toAdd = toAdd == 0 ? 30 : toAdd;
        availableAmount += toAdd;
    }

    public IndustryType getType() {
        return type;
    }

    public int getAvailableAmount() {
        int tempReturn = availableAmount;
        availableAmount = 0;
        return tempReturn;
    }
}
