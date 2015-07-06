package de.fhstralsund.opentransport.core.entity.type;

import de.fhstralsund.opentransport.core.entity.Entity;
import de.fhstralsund.opentransport.core.entity.statics.Goods;
import de.fhstralsund.opentransport.core.interfaces.IDailycycle;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Store extends Entity implements IUpdateable,IRenderable,IDailycycle {

    private Storage storage;
    private List<Building> buildingList;
    private int textureID;

    public Store(Vector2f tilePos, Boolean enterAble, int textureID) {
        super(tilePos, enterAble);
        super.setTextureID(textureID);
        this.textureID = textureID;
        this.setup();
    }

    private void setup(){
        this.buildingList = new ArrayList<Building>();
        this.storage = new Storage(this);
    }

    @Override
    public void render(ResourceLoader rl) {
        super.render(rl);
    }

    @Override
    public  void update(){

    }

    @Override
    public void dailyupdate() {

    }

    public int getFood(){
        int goodStorage = this.storage.getGoods(Goods.Food);
        if( goodStorage > 0){
            int split = goodStorage/this.buildingList.size();
            goodStorage-=split;
            this.storage.consumeGoods(Goods.Food,split);
            if(goodStorage-split <= 0){
                return goodStorage;
            }
            return split;
        }
        return 0;
    }
}
