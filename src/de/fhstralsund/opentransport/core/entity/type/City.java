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
        addStreet(new Street(seed, StreetTID.urban_cross));

        //attach some extending street points
            //north
        addStreet(new Street(new Vector2f(seed.getX()+1,seed.getY()), StreetTID.urban_street_ns));
            //spawn buildings beside //east
            if(new Random().nextInt(10) % 2 == 0){
                addBuilding(new Building(new Vector2f(seed.getX()+1,seed.getY()+1),false,rl.getTextureID("res"+ File.separator+ "building"+File.separator+"house_01.png")));
            }
            if(new Random().nextInt(10) % 2 == 0){
                addBuilding(new Building(new Vector2f(seed.getX()+1,seed.getY()-1),false,rl.getTextureID("res"+ File.separator+ "building"+File.separator+"house_01.png")));
            }
            //south
        addStreet(new Street(new Vector2f(seed.getX()-1,seed.getY()), StreetTID.urban_street_ns));
            //spawn buildings beside //east
            if(new Random().nextInt(10) % 2 == 0){
                addBuilding(new Building(new Vector2f(seed.getX()-1,seed.getY()+1),false,rl.getTextureID("res"+ File.separator+ "building"+File.separator+"house_01.png")));
            }
            if(new Random().nextInt(10) % 2 == 0){
                addBuilding(new Building(new Vector2f(seed.getX()-1,seed.getY()-1),false,rl.getTextureID("res"+ File.separator+ "building"+File.separator+"house_01.png")));
            }
            //east
        addStreet(new Street(new Vector2f(seed.getX(),seed.getY()+1), StreetTID.urban_street_we));
            //west
        addStreet(new Street(new Vector2f(seed.getX(),seed.getY()-1), StreetTID.urban_street_we));

    }
}