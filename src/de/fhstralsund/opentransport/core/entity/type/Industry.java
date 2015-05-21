package de.fhstralsund.opentransport.core.entity.type;

import de.fhstralsund.opentransport.core.entity.Entity;
import de.fhstralsund.opentransport.core.interfaces.IDailycycle;
import de.fhstralsund.opentransport.core.entity.EntityController;
import de.fhstralsund.opentransport.core.entity.statics.IndustryType;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;

import java.io.File;
import java.util.Random;


public class Industry extends Entity implements IUpdateable, IRenderable, IDailycycle{

    private IndustryType type;
    private EntityController entityController;
    private int availableGeneratingComponents = 0;
    float availableAmount = 0;

    float resourceTick = 100.0f;

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

        if(type == IndustryType.Farm) {
            resourceTick -= 0.1f;

            if(resourceTick == 0) {
                resourceTick = 100.0f;

                int toAdd = 30 * availableGeneratingComponents;
                toAdd = toAdd == 0 ? 30 : toAdd;
                availableAmount += toAdd;
            }
        }
    }

    @Override
    public void dailyupdate(){

    }
}
