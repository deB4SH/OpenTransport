package de.fhstralsund.project.entity;

import java.util.ArrayList;

public class City {

    private ArrayList<Building> buildingList;
    private ArrayList<Street> streetList;

    public City(ArrayList<Building> buildingList, ArrayList<Street> streetList) {
        this.buildingList = buildingList;
        this.streetList = streetList;
    }

    public ArrayList<Building> getBuildingList() {
        return buildingList;
    }

    public ArrayList<Street> getStreetList() {
        return streetList;
    }
}
