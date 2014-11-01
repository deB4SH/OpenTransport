package de.fhstralsund.project.entity;

import org.lwjgl.util.vector.Vector2f;

import java.util.ArrayList;

public class City {

    private ArrayList<Building> buildingList;
    private ArrayList<Street> streetList;
    private Vector2f position;


    public City(ArrayList<Building> buildingList, ArrayList<Street> streetList, Vector2f spawn) {
        this.buildingList = buildingList;
        this.streetList = streetList;
        this.position = spawn;
    }

    private void generate(){
        //create rootstreet at spawn
        this.streetList.add(new Street(this.position,true,true,true,true));

        //generate in each direction
        for(int i=0; i < 4; i++){
            int counter = 0;
            while(counter < 10){
                //generate in all directory

            }
        }

    }

    public ArrayList<Building> getBuildingList() {
        return buildingList;
    }

    public ArrayList<Street> getStreetList() {
        return streetList;
    }
}
