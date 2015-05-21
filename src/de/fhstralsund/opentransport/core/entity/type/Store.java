package de.fhstralsund.opentransport.core.entity.type;

import de.fhstralsund.opentransport.core.entity.Entity;
import de.fhstralsund.opentransport.core.interfaces.IDailycycle;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class Store extends Entity implements IUpdateable,IRenderable,IDailycycle {

    private int goodStorage;
    private List<Building> buildingList;

    public Store(Vector2f tilePos, Boolean enterAble) {
        super(tilePos, enterAble);
        this.setup();
    }

    private void setup(){
        this.buildingList = new ArrayList<Building>();
        this.goodStorage = 0;
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

    public int getGoods(){
        if(goodStorage > 0){
            int split = this.goodStorage/this.buildingList.size();
            this.goodStorage-=split;
            if(this.goodStorage-split <= 0){
                return this.goodStorage;
            }
            return split;
        }
        return 0;
    }
}
