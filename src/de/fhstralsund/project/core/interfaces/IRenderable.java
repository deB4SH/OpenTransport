package de.fhstralsund.project.core.interfaces;

import de.fhstralsund.project.resource.ResourceLoader;

public interface IRenderable {

    //@Deprecated
    //public void render();

    public void render(ResourceLoader rl);
}
