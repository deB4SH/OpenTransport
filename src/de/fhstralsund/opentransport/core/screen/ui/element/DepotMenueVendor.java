package de.fhstralsund.opentransport.core.screen.ui.element;

import de.fhstralsund.opentransport.core.entity.type.Depot;
import org.lwjgl.util.vector.Vector2f;

public class DepotMenueVendor{
    private static DepotMenue depotMenue;
    public static Vector2f startPos;
    public static Depot startDepot;

    public static void setDepotMenue(DepotMenue pdepotMenue){
        depotMenue = pdepotMenue;
    }

    public static DepotMenue getDepotMenue(){
        return depotMenue;
    }
}
