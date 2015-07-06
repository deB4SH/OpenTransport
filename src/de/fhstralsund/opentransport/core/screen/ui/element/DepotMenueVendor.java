package de.fhstralsund.opentransport.core.screen.ui.element;

import org.lwjgl.util.vector.Vector2f;

public class DepotMenueVendor{
    private static DepotMenue depotMenue;
    public static Vector2f startPos;

    public static void setDepotMenue(DepotMenue pdepotMenue){
        depotMenue = pdepotMenue;
    }

    public static DepotMenue getDepotMenue(){
        return depotMenue;
    }
}
