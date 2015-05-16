package de.fhstralsund.opentransport.core.entity.type;

import de.fhstralsund.opentransport.core.entity.EntityController;
import de.fhstralsund.opentransport.core.entity.statics.StreetTID;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class City {

    private List<Building> cityBuilding;
    private String cityName;
    private Vector2f seed;
    private int startPopulation;
    private List<Vector2f> openBlocks;
    private List<Building> pregeneratedBuilding;

    private EntityController buildingController, streetController;


    public City(Vector2f seed, String cityName, int startPopulation, EntityController building, EntityController street){
        this.cityName = cityName;
        this.cityBuilding = new ArrayList<Building>();
        this.seed = seed;
        this.startPopulation = startPopulation;
        this.buildingController = building;
        this.streetController = street;
        this.openBlocks = new ArrayList<Vector2f>();
        this.pregeneratedBuilding = new ArrayList<Building>();
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
        addStreet(new Street(seed, StreetTID.urban_cross));

        //attach some extending street points
        //north
        addStreet(new Street(new Vector2f(seed.getX()+1,seed.getY()), StreetTID.urban_street_ns));
        this.openBlocks.add(new Vector2f(seed.getX() + 1, seed.getY() + 1));//left north street
        this.openBlocks.add(new Vector2f(seed.getX() + 1, seed.getY() - 1));//right north street
        //south
        addStreet(new Street(new Vector2f(seed.getX() - 1, seed.getY()), StreetTID.urban_street_ns));
        this.openBlocks.add(new Vector2f(seed.getX() - 1, seed.getY() + 1));//left north street
        this.openBlocks.add(new Vector2f(seed.getX() - 1, seed.getY() - 1));//right north street
        //east
        addStreet(new Street(new Vector2f(seed.getX(),seed.getY()+1), StreetTID.urban_street_we));
        //west
        addStreet(new Street(new Vector2f(seed.getX(),seed.getY()-1), StreetTID.urban_street_we));
    }

    public void expandCity(ResourceLoader rl){
        Random rand = new Random();
        Vector2f cB = this.openBlocks.get(rand.nextInt(this.openBlocks.size()));
        this.openBlocks.remove(cB);

        //width & height of this block
        int width,height;
        if(rand.nextBoolean()){
            width = rand.nextInt(5); height = 2 ;
        }
        else{
            width = 2; height = rand.nextInt(5);
        }

        //generate block entites
    }
}