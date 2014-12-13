package de.fhstralsund.opentransport.core.entity.type;

import de.fhstralsund.opentransport.core.entity.EntityController;
import de.fhstralsund.opentransport.core.entity.statics.StreetTID;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class City {

    private List<Building> cityBuilding;
    private String cityName;

    private Vector2f seed;
    private int startPopulation;

    private EntityController buildingController, streetController;


    public City(Vector2f seed, String cityName, int startPopulation, EntityController building, EntityController street){
        this.cityName = cityName;
        this.cityBuilding = new ArrayList<Building>();
        this.seed = seed;
        this.startPopulation = startPopulation;
        this.buildingController = building;
        this.streetController = street;
    }

    public void addBuilding(Building e){
        this.cityBuilding.add(e);
        this.buildingController.addEntity(e);
    }

    public void addStreet(Street e){
        this.streetController.addEntity(e);
    }

    public void generateCity(ResourceLoader rl){
        //place at seed a street
        this.streetController.addEntity(new Street(seed, StreetTID.urban_cross,true,true,true,true));
        //TODO: generate Code
        //attach some extending street points
        Vector2f north=seed, south=seed, west=seed,east = seed;

    }
}