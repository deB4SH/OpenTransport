package de.fhstralsund.opentransport.core.screen.ui.action;

import de.fhstralsund.opentransport.core.entity.Entity;
import de.fhstralsund.opentransport.core.entity.EntityController;
import org.lwjgl.util.vector.Vector2f;

public abstract class UiButtonAction {

    public abstract void fireAction(EntityController ec);

    public abstract void fireAction(EntityController ec, Vector2f startPos, Entity endDepot);
}
