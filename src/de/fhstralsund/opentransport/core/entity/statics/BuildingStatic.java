package de.fhstralsund.opentransport.core.entity.statics;


import de.fhstralsund.opentransport.core.io.ResourceLoader;

import java.io.File;

public class BuildingStatic {

    public static int tierOneMaxCitizen;
    public static int tierTwoMaxCitizen;

    public static int tierOneHouseOne;
    public static int tierOneHouseTwo;
    public static int tierOneHouseThree;
    public static int tierOneHouseFour;
    public static int tierOneHouseLeft;

    public static int tierTwoHouseOne;
    public static int tierTwoHouseOneLeft;
    public static int tierTwoHouseTwo;
    public static int tierTwoHouseTwoLeft;
    public static int tierTwoHouseThree;
    public static int tierTwoHouseThreeLeft;

    public static int storeOne;
    public static int storeTwo;

    public BuildingStatic(ResourceLoader rl) {
        tierOneMaxCitizen = 20;
        tierTwoMaxCitizen = 60;

        tierOneHouseOne = rl.getTextureID("res" + File.separator + "building" + File.separator + "house_1_1.png");
        tierOneHouseTwo = rl.getTextureID("res" + File.separator + "building" + File.separator + "house_1_2.png");
        tierOneHouseThree = rl.getTextureID("res" + File.separator + "building" + File.separator + "house_1_3.png");
        tierOneHouseFour = rl.getTextureID("res" + File.separator + "building" + File.separator + "house_1_4.png");
        tierOneHouseLeft = rl.getTextureID("res" + File.separator + "building" + File.separator + "house_1_1_left.png");

        tierTwoHouseOne = rl.getTextureID("res" + File.separator + "building" + File.separator + "house_2_1.png");
        tierTwoHouseOneLeft = rl.getTextureID("res" + File.separator + "building" + File.separator + "house_2_1_left.png");
        tierTwoHouseTwo = rl.getTextureID("res" + File.separator + "building" + File.separator + "house_2_2.png");
        tierTwoHouseTwoLeft = rl.getTextureID("res" + File.separator + "building" + File.separator + "house_2_2_left.png");

        storeOne = rl.getTextureID("res" + File.separator + "building" + File.separator + "store_1.png");
        storeTwo = rl.getTextureID("res" + File.separator + "building" + File.separator + "store_2.png");

    }
}
