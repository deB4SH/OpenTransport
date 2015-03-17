package de.fhstralsund.opentransport.core.screen.gui;

import de.fhstralsund.opentransport.core.entity.EntityController;
import de.fhstralsund.opentransport.core.interfaces.IGuiClose;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import de.fhstralsund.opentransport.core.screen.Camera;
import de.fhstralsund.opentransport.core.screen.screens.Game;
import org.lwjgl.util.vector.Vector2f;

import org.newdawn.slick.TrueTypeFont;

import java.awt.*;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class Gui implements IUpdateable, IRenderable, IGuiClose{

    public Map<String, GuiWindow> windows;
    public ResourceLoader rl;

    public static HashMap<String, Vector2f> Citynames = new HashMap<String, Vector2f>();
    private TrueTypeFont font;
    private Font awtFont;



    public Gui(ResourceLoader rl) {
        this.rl = rl;
        windows = new HashMap<String, GuiWindow>();

        awtFont = new Font("Times New Roman", Font.PLAIN, 18); //name, style (PLAIN, BOLD, or ITALIC), size
        this.font = new TrueTypeFont(awtFont, false); //base Font, anti-aliasing true/false
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

        int StreetNS = rl.getTextureID("res"+ File.separator+"street"+File.separator+"urban"+File.separator+"street_ns.png");
        int StreetWE = rl.getTextureID("res"+ File.separator+"street"+File.separator+"urban"+File.separator+"street_we.png");

        buildWindow.getGuiWindowElements().add(new GuiWindowElement(StreetNS, new Vector2f(buildWindow.getPosition().x + xoffset,
                buildWindow.getPosition().y + 30 * yoffset), new Vector2f(rl.getTextureSizeByID(StreetNS)), true));
        xoffset += 30;

        buildWindow.getGuiWindowElements().add(new GuiWindowElement(StreetWE, new Vector2f(buildWindow.getPosition().x + xoffset,
                buildWindow.getPosition().y + 30 * yoffset), new Vector2f(rl.getTextureSizeByID(StreetWE)), true));
        xoffset += 30;

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

        for(String name : Citynames.keySet()) {
            renderCityText(name, Citynames.get(name));
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

    public void renderCityText(String text, Vector2f position) {
        Camera cam = Camera.getInstance();

        float xpos = (position.getX() * Game.TILEWIDTH / 2) + (position.getY() * Game.TILEWIDTH / 2) - cam.getPosition().x;
        float ypos = ((position.getY() * Game.TILEHEIGHT / 2) - (position.getX() * Game.TILEHEIGHT / 2) - cam.getPosition().y);

        this.font.drawString(xpos, ypos, text);
    }

    public void renderText(String name, Vector2f position) {
        this.font.drawString(position.x, position.y, name);
    }
}
