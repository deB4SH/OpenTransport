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

    public void createStreetGui() {
        if (windows.containsKey("streetMenue")) { return;}

        GuiWindow buildwindow = new GuiWindow(new Vector2f(250,50), rl.getTextureSizeByFileName("gui_background.png"), "streetMenue", this);

        // add elements to buildGUI
        int xoffset = 0;
        int yoffset = 1;
        for(int i = 1; i < 16; i++) {
            buildwindow.getGuiWindowElements().add(new GuiWindowElement(i, new Vector2f(buildwindow.getPosition().x + xoffset,
                    buildwindow.getPosition().y + 30 * yoffset), new Vector2f(rl.getTextureSizeByID(i)), true));
            xoffset += 30;
            if(i % 4 == 0) {
                yoffset++;
                xoffset = 0;
            }
        }

        // add last element - the destruction icon
        buildwindow.getGuiWindowElements().add(new GuiWindowElement(rl.getTexturesID("delete.png"), new Vector2f(buildwindow.getPosition().x + xoffset,
                buildwindow.getPosition().y + 30 * yoffset), new Vector2f(rl.getTextureSizeByFileName("delete.png")), true));

        windows.put("streetMenue", buildwindow);
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
