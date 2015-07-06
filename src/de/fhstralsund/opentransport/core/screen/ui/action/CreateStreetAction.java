package de.fhstralsund.opentransport.core.screen.ui.action;

import de.fhstralsund.opentransport.core.entity.Entity;
import de.fhstralsund.opentransport.core.entity.EntityController;
import org.lwjgl.util.vector.Vector2f;

public class CreateStreetAction extends UiButtonAction {


    @Override
    public void fireAction(EntityController ec) {

        System.out.println("build Street");
        ec.buildSteet();
    }

    @Override
    public void fireAction(EntityController ec, Vector2f startPos, Entity endDepot) {

    }
}
