package de.fhstralsund.opentransport.core.screen.ui.action;

import de.fhstralsund.opentransport.core.entity.EntityController;

public class CreateStreetAction extends UiButtonAction {


    @Override
    public void fireAction(EntityController ec) {

        System.out.println("build Street");
        ec.buildSteet();
    }
}
