package de.fhstralsund.opentransport.core.screen.ui.action;

import de.fhstralsund.opentransport.core.entity.Entity;
import de.fhstralsund.opentransport.core.entity.EntityController;
import de.fhstralsund.opentransport.core.entity.statics.CarID;
import de.fhstralsund.opentransport.core.entity.type.Car;
import org.lwjgl.util.vector.Vector2f;

public class CreateCarAction extends UiButtonAction{

    @Override
    public void fireAction(EntityController ec) {

    }

    @Override
    public void fireAction(EntityController ec, Vector2f startPos, Entity endDepot) {
        Car car = new Car(startPos, 20, false, endDepot.getTilePos(), ec);
        ec.addCar(car);
    }
}
