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
        urban_cross = rl.getTextureID("res"+ File.separator+"street"+File.separator+"urban"+File.separator+"street_cross.png");
        urban_street_ns = rl.getTextureID("res"+ File.separator+"street"+File.separator+"urban"+File.separator+"street_ns.png");
        urban_street_we = rl.getTextureID("res"+ File.separator+"street"+File.separator+"urban"+File.separator+"street_we.png");
        urban_cross_sen = rl.getTextureID("res"+ File.separator+"street"+File.separator+"urban"+File.separator+"street_cross_sen.png");
        urban_cross_swn = rl.getTextureID("res"+ File.separator+"street"+File.separator+"urban"+File.separator+"street_cross_swn.png");
        urban_cross_nwe = rl.getTextureID("res"+ File.separator+"street"+File.separator+"urban"+File.separator+"street_cross_wne.png");
        urban_cross_swe = rl.getTextureID("res"+ File.separator+"street"+File.separator+"urban"+File.separator+"street_cross_wse.png");
        urban_curve_ne = rl.getTextureID("res"+ File.separator+"street"+File.separator+"urban"+File.separator+"street_curve_ne.png");
        urban_curve_nw = rl.getTextureID("res"+ File.separator+"street"+File.separator+"urban"+File.separator+"street_curve_wn.png");
        urban_curve_se = rl.getTextureID("res"+ File.separator+"street"+File.separator+"urban"+File.separator+"street_curve_es.png");
        urban_curve_sw = rl.getTextureID("res"+ File.separator+"street"+File.separator+"urban"+File.separator+"street_curve_ws.png");
        urban_end_e = rl.getTextureID("res"+ File.separator+"street"+File.separator+"urban"+File.separator+"street_end_e.png");
        urban_end_n = rl.getTextureID("res"+ File.separator+"street"+File.separator+"urban"+File.separator+"street_end_n.png");
        urban_end_s = rl.getTextureID("res"+ File.separator+"street"+File.separator+"urban"+File.separator+"street_end_s.png");
        urban_end_w = rl.getTextureID("res"+ File.separator+"street"+File.separator+"urban"+File.separator+"street_end_w.png");

    }

}
