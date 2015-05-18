package de.fhstralsund.opentransport.core.entity;

import de.fhstralsund.opentransport.core.entity.type.City;
import de.fhstralsund.opentransport.core.entity.type.Street;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import de.fhstralsund.opentransport.core.screen.gui.Gui;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class CityController implements IRenderable,IUpdateable{

    private static final boolean DEBUG = false;
    private List<City> cityList;

    public CityController() {
        this.cityList = new ArrayList<City>();
    }

    public void spawnCity(Vector2f seed, String cityName ,int popCap, EntityController entityController, ResourceLoader rl){
        this.cityList.add(new City(seed,cityName, popCap, entityController,rl));
        int number = cityList.size()-1;
        this.cityList.get(number).generateCity(rl);

        Gui.Citynames.put(cityName, seed);
    }

    int a = 0;
    @Override
    public void update() {
        a++;
        if(a % 100 == 0){
            for(City c: this.cityList){
                c.update();
            }
        }
    }

    @Override
    public void render(ResourceLoader rl) {
        if(DEBUG){
            for(City c: this.cityList){
                c.showOpenblocks(rl);
            }
        }
    }
}
