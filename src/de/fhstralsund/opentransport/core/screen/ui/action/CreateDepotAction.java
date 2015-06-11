package de.fhstralsund.opentransport.core.screen.ui.action;

import de.fhstralsund.opentransport.core.entity.EntityController;

public class CreateDepotAction extends UiButtonAction{

    @Override
    public void fireAction(EntityController ec) {
        // create depot here
        System.out.println("build Depot");
        ec.buildDepot();
    }
}
