package de.fhstralsund.opentransport.core.entity.statics;

import de.fhstralsund.opentransport.core.io.ResourceLoader;

import java.io.File;

/**
 * Texture ID Class , to keep it easier to change tiletextures on updates
 */
public class StreetTID {

    public static int urban_cross;
    public static int urban_cross_sen;
    public static int urban_cross_swn;
    public static int urban_cross_nwe;
    public static int urban_cross_swe;
    public static int urban_curve_se;
    public static int urban_curve_sw;
    public static int urban_curve_ne;
    public static int urban_curve_nw;
    public static int urban_end_e;
    public static int urban_end_n;
    public static int urban_end_s;
    public static int urban_end_w;
    public static int urban_street_ns;
    public static int urban_street_we;

    public StreetTID(ResourceLoader rl){
        urban_cross = rl.getTextureID("res"+ File.separator+"street"+File.separator+"urban"+File.separator+"Street_cross.png");
        urban_street_ns = rl.getTextureID("res"+ File.separator+"street"+File.separator+"urban"+File.separator+"Street_NE.png");
        urban_street_we = rl.getTextureID("res"+ File.separator+"street"+File.separator+"urban"+File.separator+"Street_SE_.png");

    }

}
