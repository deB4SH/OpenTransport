package de.fhstralsund.opentransport.core.entity.statics;


import de.fhstralsund.opentransport.core.io.ResourceLoader;

import java.io.File;

public class CarID{

    public static int car_wood_NE;
    public static int car_wood_NW;
    public static int car_wood_SE;
    public static int car_wood_SW;

    public CarID(ResourceLoader rl) {
        car_wood_NE = rl.getTextureID("res"+ File.separator+"cars"+File.separator+"car_wood_NE.png");
        car_wood_NW = rl.getTextureID("res"+ File.separator+"cars"+File.separator+"car_wood_NW.png");
        car_wood_SE = rl.getTextureID("res"+ File.separator+"cars"+File.separator+"car_wood_SE.png");
        car_wood_SW = rl.getTextureID("res"+ File.separator+"cars"+File.separator+"car_wood_SW.png");
    }
}
