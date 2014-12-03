package de.fhstralsund.opentransport.core.entity;

import de.fhstralsund.opentransport.core.entity.type.City;
import de.fhstralsund.opentransport.core.entity.type.Street;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class CityController implements IUpdateable{

    private List<City> cityList;

    public CityController() {
        this.cityList = new ArrayList<City>();
    }

    public void spawnCity(Vector2f seed, int popCap, EntityController buildingController, EntityController streetController, ResourceLoader rl){
        this.cityList.add(new City(seed,"Stralsund",100,buildingController,streetController));
        int number = cityList.size();
        this.cityList.get(number).addStreet(new Street(seed,rl.getTexturesID("Street_NE.png"),true,true,true,true));
        this.cityList.get(number).generateCity(rl);
    }

    @Override
    public void update() {

    }
}
