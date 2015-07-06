package de.fhstralsund.opentransport.core.entity.type;

import de.fhstralsund.opentransport.core.entity.Entity;
import de.fhstralsund.opentransport.core.entity.statics.BuildingStatic;
import de.fhstralsund.opentransport.core.entity.statics.Goods;
import de.fhstralsund.opentransport.core.interfaces.IDailycycle;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;

public class Building extends Entity implements IRenderable,IUpdateable, IDailycycle{

    private int citizienCount;
    private int maxCititzenCount;
    private boolean planned;
    private int tier;
    private boolean willingToUpgrade;
    private City parentCity;
    private Store personalStore;
    private Storage storage;
    private boolean buildingDead;

    private final float consumptionTierOne = 1.10f;
    private final float conumptionTierTwo = 1.30f;

    public Building(Vector2f tilePos, Boolean enterAble, int textureID, City parentCity) {
        super(tilePos, enterAble);
        super.setTextureID(textureID);
        this.planned = false;
        this.parentCity = parentCity;
        this.setup();
    }

    public Building(Vector2f tilePos, Boolean enterAble, int textureID, boolean planned, City parentCity) {
        super(tilePos, enterAble);
        super.setTextureID(textureID);
        this.planned = planned;
        this.parentCity = parentCity;
        this.setup();
    }

    private void setup(){
        this.tier = 1;
        this.maxCititzenCount = BuildingStatic.tierOneMaxCitizen;
        this.citizienCount = 2;
        this.willingToUpgrade = false;
        this.personalStore = null;
        this.buildingDead = false;
        this.storage = new Storage(this);
        this.storage.addGoods(Goods.Food,200);
        this.storage.addGoods(Goods.Furniture, 10);

        this.findStore();
        if(this.personalStore == null){
            this.parentCity.setStoreNeed(this.parentCity.getStoreNeed() + 5);
        }
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

    private void findStore(){
        //find store in 5x5 range
        for(Entity e: this.parentCity.getCityBuilding()){
            if(e.getClass() == Store.class){
                if(Math.abs(this.getTilePos().x) - Math.abs(e.getTilePos().x) < 5){
                    if(Math.abs(this.getTilePos().y) - Math.abs(e.getTilePos().y) < 5){
                        this.personalStore = (Store)e;
                    }
                }
            }
        }
    }

    @Override
    public void dailyupdate(){
        if(this.personalStore == null){
            findStore();
        }
        if(!buildingDead) {
            int oldStorageVal = this.storage.getGoods(Goods.Food);
            //get goods from store
            if (personalStore != null) {
                this.storage.addGoods(Goods.Food,this.personalStore.getFood());
            }
            //update peeps
            if (this.tier == 1) {
                //calc usage of citizens in building
                int requirement = (int) (this.citizienCount * this.consumptionTierOne);
                this.storage.consumeGoods(Goods.Food,requirement);
                if(oldStorageVal > 0){
                    float diffStorage = this.storage.getGoods(Goods.Food) * 100 / oldStorageVal;

                    if (this.storage.getGoods(Goods.Food) <= 0) {
                        if (this.citizienCount - 1 >= 0)
                            this.citizienCount -= 1;
                        if(this.citizienCount == 0){
                            this.buildingDead = true;
                            System.out.println("Hey iam dead");
                            return;
                        }
                    } else if (diffStorage < 95) {
                        //nothing
                    } else if (this.storage.getGoods(Goods.Food) > oldStorageVal) {
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
                        if (this.storage.getGoods(Goods.Food) != 0) {
                            this.willingToUpgrade = true;
                        } else {
                            this.willingToUpgrade = false;
                        }
                    } else {
                        this.willingToUpgrade = false;
                    }
                }
            } else if (this.tier == 2) {

            } else {
                System.out.println("[ERROR]:[" + this.toString() + "][Wrong TIER]");
            }
        }
    }
}
