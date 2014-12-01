package de.fhstralsund.project.core.entity.type;

import de.fhstralsund.project.core.entity.Entity;
import de.fhstralsund.project.core.entity.EntityController;
import de.fhstralsund.project.core.io.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class City extends Entity {

    private List<Building> cityBuilding;
    private String cityName;

    private Vector2f seed;
    private int startPopulation;

    private EntityController buildingController, streetController;


    public City(Vector2f tilePos, Boolean enterAble, String cityName, Vector2f seed, int startPopulation, EntityController building, EntityController street){
        super(tilePos, enterAble);
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
        this.streetController.addEntity(new Street(seed,rl.getTexturesID("Street_cross.png"),true,true,true,true));


    }
}