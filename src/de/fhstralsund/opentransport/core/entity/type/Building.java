package de.fhstralsund.opentransport.core.entity.type;

import de.fhstralsund.opentransport.core.entity.Entity;
import de.fhstralsund.opentransport.core.entity.statics.BuildingStatic;
import de.fhstralsund.opentransport.core.interfaces.IDailycycle;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;

public class Building extends Entity implements IRenderable,IUpdateable, IDailycycle{

    private int citizienCount;
    private int maxCititzenCount;
    private int goodsInStorage;
    private boolean planned;
    private int tier;
    private boolean willingToUpgrade;
    private Store personalStore;
    private boolean buildingDead;

    private final float consumptionTierOne = 1.10f;
    private final float conumptionTierTwo = 1.30f;

    public Building(Vector2f tilePos, Boolean enterAble, int textureID) {
        super(tilePos, enterAble);
        super.setTextureID(textureID);
        this.planned = false;
        this.setup();
    }

    public Building(Vector2f tilePos, Boolean enterAble, int textureID, boolean planned) {
        super(tilePos, enterAble);
        super.setTextureID(textureID);
        this.planned = planned;
        this.setup();
    }

    private void setup(){
        this.tier = 1;
        this.goodsInStorage = 200;
        this.maxCititzenCount = BuildingStatic.tierOneMaxCitizen;
        this.citizienCount = 2;
        this.willingToUpgrade = false;
        this.personalStore = null;
        this.buildingDead = false;
    }

    @Override
    public void render(ResourceLoader rl) {
        if(!planned){
            super.render(rl);
        }
        else{
            //nothing
        }
    }

    @Override
    public void update() {
        if(!buildingDead){
            //TODO: updateplans for buildings(homes for the peeps)
        }
        else{
            this.setTextureID(BuildingStatic.tierOneHouseLeft);
        }
    }

    @Override
    public void dailyupdate(){
        if(!buildingDead) {
            int oldStorageVal = this.goodsInStorage;
            //get goods from store
            if (personalStore != null) {
                this.goodsInStorage += this.personalStore.getGoods();
            }
            //update peeps
            if (this.tier == 1) {
                //calc usage of citizens in building
                int requirement = (int) (this.citizienCount * this.consumptionTierOne);
                this.goodsInStorage -= requirement;
                float diffStorage = this.goodsInStorage * 100 / oldStorageVal;

                if (this.goodsInStorage <= 0) {
                    if (this.citizienCount - 1 >= 0)
                        this.citizienCount -= 1;
                    if(this.citizienCount == 0){
                        this.buildingDead = true;
                        System.out.println("Hey iam dead");
                        return;
                    }
                } else if (diffStorage < 95) {
                    //nothing
                } else if (this.goodsInStorage > oldStorageVal) {
                    if (!(this.citizienCount + 1 > this.maxCititzenCount))
                        this.citizienCount += 1;
                } else if (diffStorage > 150) {
                    if (!(this.citizienCount + 2 > this.maxCititzenCount))
                        this.citizienCount += 2;
                    else if (!(this.citizienCount + 1 > this.maxCititzenCount))
                        this.citizienCount += 1;
                }

                //check if building is maxed out
                if (this.citizienCount == this.maxCititzenCount) {
                    if (this.goodsInStorage != 0) {
                        this.willingToUpgrade = true;
                    } else {
                        this.willingToUpgrade = false;
                    }
                } else {
                    this.willingToUpgrade = false;
                }
            } else if (this.tier == 2) {

            } else {
                System.out.println("[ERROR]:[" + this.toString() + "][Wrong TIER]");
            }
        }
    }
}
