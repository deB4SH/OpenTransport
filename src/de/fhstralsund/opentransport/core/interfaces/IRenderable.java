package de.fhstralsund.opentransport.core.interfaces;

import de.fhstralsund.opentransport.core.io.ResourceLoader;

public interface IRenderable {

    //@Deprecated
    //public void render();

    public void render(ResourceLoader rl);
}
