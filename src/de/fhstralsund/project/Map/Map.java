package de.fhstralsund.project.Map;

import de.fhstralsund.project.Entity.IRenderable;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Florian on 27.10.2014.
 */
public class Map implements IRenderable{

    private List<Layer> layers = new ArrayList<Layer>();
    private Texture grastexture;

    public Map(int size) {

        layers.add(new Layer(size)); // layer 0 Gras
        //layers.add(new Layer(size)); // layer 1 Streets
        //layers.add(new Layer(size)); // layer 2 Buildings

        try {
            grastexture = TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream("res/ground.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void Render() {
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glColor3f(0.5f,0.5f,1.0f);
        GL11.glBegin(GL11.GL_QUADS);
        Color.white.bind();
        grastexture.bind();

        for(int i = 0; i < layers.size(); i++) {         // layers
            for(int x = 0; x < layers.size(); x++) {     // array x
                for (int y = 0; y < layers.size(); y++) {// array y

                    GL11.glVertex2f(x * grastexture.getImageWidth(), y * grastexture.getImageHeight());
                    GL11.glVertex2f(x * grastexture.getImageWidth() + grastexture.getImageWidth(), y * grastexture.getImageHeight());
                    GL11.glVertex2f(x * grastexture.getImageWidth() + grastexture.getImageWidth(), y * grastexture.getImageHeight() + grastexture.getImageHeight());
                    GL11.glVertex2f(x * grastexture.getImageWidth(), y * grastexture.getImageHeight() + grastexture.getImageHeight());

                }
            }
        }

        GL11.glEnd();
        Display.update();

    }
}
