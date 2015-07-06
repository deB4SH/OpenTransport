package de.fhstralsund.opentransport.core.entity.type;

import de.fhstralsund.opentransport.core.entity.Entity;
import de.fhstralsund.opentransport.core.entity.statics.Goods;

import java.util.HashMap;

/**
 * Storage represents the internal Storage on every Car/Depot/Store
 * Keeps a list of all possible holdings and amount carried
 */
public class Storage {

    private HashMap<Goods, Integer> storage;
    private Entity parent;

    public Storage(Entity parent){
        this.parent = parent;
        if(this.parent.getClass() == Building.class){
            this.storage = new HashMap<Goods, Integer>();
            this.storage.put(Goods.Food,0);
            this.storage.put(Goods.Furniture,0);
        }
        else
        {
            this.storage = new HashMap<Goods, Integer>();
            for (Goods g : Goods.values()) {
                this.storage.put(g, 0);
            }
        }
    }

    public void addGoods(Goods type, int amount){
        if(this.storage.containsKey(type)){
            this.storage.put(type, this.storage.get(type).intValue() + amount);
        }
    }

    public void consumeGoods(Goods type, int amount){
        if(this.storage.containsKey(type)){
            this.storage.put(type, this.storage.get(type).intValue() - amount);
        }
    }

    public int getGoods(Goods type){
        return this.storage.get(type).intValue();
    }

}
