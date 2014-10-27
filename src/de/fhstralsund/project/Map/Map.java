package de.fhstralsund.project.map;

import de.fhstralsund.project.entity.IRenderable;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Florian on 27.10.2014.
 */
public class Map implements IRenderable{

    private List<Layer> layers = new ArrayList<Layer>();
    private Texture grastexture;

    public Map(int size) {

        layers.add(new Layer(size));   // layer 0 Gras
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
        Color.white.bind();
        grastexture.bind();

        for(int i = 0; i < layers.size(); i++) {                         // layers
            for(int x = 0; x < layers.get(i).getMap().length; x++) {     // array x
                for (int y = 0; y < layers.get(i).getMap().length; y++) {// array y
                    GL11.glBegin(GL11.GL_QUADS);
                    GL11.glTexCoord2f(0,0);
                    GL11.glVertex2f(100, 100);
                    GL11.glTexCoord2f(1,0);
                    GL11.glVertex2f(150, 100);
                    GL11.glTexCoord2f(1,1);
                    GL11.glVertex2f(150, 150);
                    GL11.glTexCoord2f(0,1);
                    GL11.glVertex2f(100, 150);
                    GL11.glEnd();
                }
            }
        }

    }
}
