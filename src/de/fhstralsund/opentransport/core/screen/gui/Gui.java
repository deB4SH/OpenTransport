package de.fhstralsund.opentransport.core.screen.gui;

import de.fhstralsund.opentransport.core.entity.EntityController;
import de.fhstralsund.opentransport.core.interfaces.IGuiClose;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;

import java.io.File;
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

        GuiWindow buildWindow = new GuiWindow(new Vector2f(250,50), rl.getTextureSizeByFileName("res"+ File.separator+"gui"+File.separator+"gui_background.png"), "streetMenue", this);

        // add elements to buildGUI
        int xoffset = 0;
        int yoffset = 1;
        int startingStreetIds = rl.getTextureID("res"+ File.separator+"street"+File.separator+"urban"+File.separator+"street_cross.png");
        for(int i = 1; i < 15; i++) {
            buildWindow.getGuiWindowElements().add(new GuiWindowElement(i + startingStreetIds, new Vector2f(buildWindow.getPosition().x + xoffset,
                    buildWindow.getPosition().y + 30 * yoffset), new Vector2f(rl.getTextureSizeByID(i + startingStreetIds)), true));
            xoffset += 30;
            if(i % 4 == 0) {
                yoffset++;
                xoffset = 0;
            }
        }

        // add last element - the destruction icon
        buildWindow.getGuiWindowElements().add(new GuiWindowElement(rl.getTextureID("res"+ File.separator+"delete.png"), new Vector2f(buildWindow.getPosition().x + xoffset,
                buildWindow.getPosition().y + 30 * yoffset), new Vector2f(rl.getTextureSizeByFileName("res"+ File.separator+"delete.png")), true));

        windows.put("streetMenue", buildWindow);
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

    public void update(EntityController entityController) {
        for(String window : windows.keySet()) {
            windows.get(window).update(entityController);
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
