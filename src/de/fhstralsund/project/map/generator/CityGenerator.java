package de.fhstralsund.project.map.generator;

import de.fhstralsund.project.entity.City;

public class CityGenerator {

    private int seedX, seedY;

    public CityGenerator(int seedX, int seedY) {
        this.seedX = seedX;
        this.seedY = seedY;
    }

    public City generateCity(){

        return new City();
    }
}
