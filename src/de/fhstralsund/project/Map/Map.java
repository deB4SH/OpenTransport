package de.fhstralsund.project.map;

import de.fhstralsund.project.entity.Camera;
import de.fhstralsund.project.entity.IRenderable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import de.fhstralsund.project.resource.ResourceLoader;


public class Map implements IRenderable{

    private List<Layer> layers = new ArrayList<Layer>();
    private static ResourceLoader rl;
    private int grasslandID = 0;
    private int tileWidth=64,tileHeigth=32;

    public Map(int size, ResourceLoader rl) {

        this.rl = rl;
        layers.add(new Layer(size));   // layer 0 Gras
        //layers.add(new Layer(size)); // layer 1 Streets
        //layers.add(new Layer(size)); // layer 2 Buildings

    }

    @Override
    public void Render() {
        Color.white.bind();
        rl.bindTextureByID(0);
        Camera cam = Camera.getInstance();

        for(int i = 0; i < layers.size(); i++) {                         // layers
            for(int x = 0; x < layers.get(i).getMap().length; x++) {     // array x
                for(int y = 0; y < layers.get(i).getMap().length; y++) {// array y

                    GL11.glBegin(GL11.GL_QUADS);

                    float xpos = (x * tileWidth / 2) + (y * tileWidth / 2) - cam.getPosition().x;
                    float ypos = ((y * tileHeigth / 2) - (x * tileHeigth / 2) - cam.getPosition().y);

                    GL11.glTexCoord2f(0, 0);
                    GL11.glVertex2f(xpos, ypos);
                    GL11.glTexCoord2f(1, 0);
                    GL11.glVertex2f(xpos + tileWidth, ypos);
                    GL11.glTexCoord2f(1, 1);
                    GL11.glVertex2f(xpos + tileWidth, ypos + tileHeigth);
                    GL11.glTexCoord2f(0, 1);
                    GL11.glVertex2f(xpos, ypos + tileHeigth);
                    GL11.glEnd();
                }
            }
        }

    }
}
