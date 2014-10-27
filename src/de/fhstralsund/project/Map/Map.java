package de.fhstralsund.project.map;

import de.fhstralsund.project.entity.IRenderable;
import org.lwjgl.opengl.GL11;
import org.newdawn.slick.Color;
import org.newdawn.slick.opengl.Texture;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import de.fhstralsund.project.resource.ResourceLoader;


public class Map implements IRenderable{

    private List<Layer> layers = new ArrayList<Layer>();
    private static ResourceLoader rl;

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

        for(int i = 0; i < layers.size(); i++) {                         // layers
            for(int x = 0; x < layers.get(i).getMap().length; x++) {     // array x
                for (int y = 0; y < layers.get(i).getMap().length; y++) {// array y
                    GL11.glBegin(GL11.GL_QUADS);
                    GL11.glTexCoord2f(0,0);
                    GL11.glVertex2f((x * 64 / 2) + (y * 64 / 2), (y * 47 / 2) - (x * 47 / 2));
                    GL11.glTexCoord2f(1,0);
                    GL11.glVertex2f((x * 64 / 2) + (y * 64 / 2) + 64, (y * 47 / 2) - (x * 47 / 2));
                    GL11.glTexCoord2f(1,1);
                    GL11.glVertex2f((x * 64 / 2) + (y * 64 / 2) + 64, (y * 47 / 2) - (x * 47 / 2) + 47 );
                    GL11.glTexCoord2f(0,1);
                    GL11.glVertex2f((x * 64 / 2) + (y * 64 / 2), (y * 47 / 2) - (x * 47 / 2) + 47);
                    GL11.glEnd();
                }
            }
        }

    }
}
