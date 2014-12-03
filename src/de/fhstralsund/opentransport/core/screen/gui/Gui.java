package de.fhstralsund.opentransport.core.screen.gui;

import de.fhstralsund.opentransport.core.interfaces.IGuiClose;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;

import java.util.HashMap;
import java.util.Map;

public class Gui implements IUpdateable, IRenderable, IGuiClose{

    private Map<String, GuiWindow> windows;
    private ResourceLoader rl;

    public Gui(ResourceLoader rl) {
        this.rl = rl;
        windows = new HashMap<String, GuiWindow>();
    }

    // TODO: Optionen hinzufügen für buttons, text, diagramme, etc..
    // bisher nur position, höhe breite..
    public void addWindow(Vector2f position, Vector2f dimension) {
    }

    public void createBuildGui() {
        if (windows.containsKey("buildMenue")) { return;}

        GuiWindow buildwindow = new GuiWindow(new Vector2f(250,50), rl.getTextureSizeByFileName("gui_background.png"), "buildMenue", this);

        buildwindow.getGuiWindowElements().add(new GuiWindowElement(1, new Vector2f(buildwindow.getPosition().x + 5,
                buildwindow.getPosition().y + 30), new Vector2f(rl.getTextureSizeByID(1))));

        buildwindow.getGuiWindowElements().add(new GuiWindowElement(2, new Vector2f(buildwindow.getPosition().x + 40,
                buildwindow.getPosition().y + 30), new Vector2f(rl.getTextureSizeByID(2))));

        windows.put("buildMenue", buildwindow);
    }

    public void destroyGui() {
        windows.clear();
    }

    @Override
    public void render(ResourceLoader rl) {
        for(String window : windows.keySet()) {
            windows.get(window).render(rl);
        }
    }

    @Override
    public void update() {
        for(String window : windows.keySet()) {
            windows.get(window).update();
        }
    }

    @Override
    public void closeRequest(String name) {
        windows.remove(name);
    }
}
