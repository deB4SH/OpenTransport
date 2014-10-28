package de.fhstralsund.project.map;

import de.fhstralsund.project.entity.Camera;
import de.fhstralsund.project.core.interfaces.IRenderable;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import de.fhstralsund.project.resource.ResourceLoader;

import java.util.ArrayList;
import java.util.List;


public class Map implements IRenderable{

    private List<Layer> layers = new ArrayList<Layer>();
    private static ResourceLoader rl;
    private int tileWidth=64,tileHeigth=32;

    public Map(int size, ResourceLoader rl) {

        this.rl = rl;
        layers.add(new Layer(size, rl, ""));                  // layer 0 Gras
        layers.add(new Layer(size, rl, "vegetation"));        // layer 1 Vegetation
        layers.add(new Layer(size, rl, "streets"));           // layer 2 Streets
    }

    @Override
    public void render() {
        Color.white.bind();
        Camera cam = Camera.getInstance();

        for(int i = 0; i < layers.size(); i++) {                         // layers
            for(int x = 0; x < layers.get(i).getMap().length; x++) {     // array x
                for(int y = 0; y < layers.get(i).getMap().length; y++) { // array y
                    if(layers.get(i).getMap()[x][y] != null) {
                        rl.bindTextureByID(layers.get(i).getMap()[x][y].getTextureId());
                        GL11.glBegin(GL11.GL_QUADS);

                        float xpos = (x * tileWidth / 2) + (y * tileWidth / 2) - cam.getPosition().x;
                        float ypos = ((y * tileHeigth / 2) - (x * tileHeigth / 2) - cam.getPosition().y);

                        GL11.glTexCoord2f(0, 0);
                        GL11.glVertex2f(xpos, ypos);
                        GL11.glTexCoord2f(1, 0);
                        GL11.glVertex2f(xpos + tileWidth, ypos);
                        GL11.glTexCoord2f(1, 1);
                        int g = (int)rl.getTextureSizeByID(layers.get(i).getMap()[x][y].getTextureId()).y;
                        GL11.glVertex2f(xpos + tileWidth, ypos + g);
                        GL11.glTexCoord2f(0, 1);
                        GL11.glVertex2f(xpos, ypos + rl.getTextureSizeByID(layers.get(i).getMap()[x][y].getTextureId()).y);
                        GL11.glEnd();
                    }
                }
            }
        }
    }
}
