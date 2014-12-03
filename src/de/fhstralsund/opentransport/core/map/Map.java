package de.fhstralsund.opentransport.core.map;

import de.fhstralsund.opentransport.core.screen.Camera;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import de.fhstralsund.opentransport.core.io.ResourceLoader;

import java.util.ArrayList;
import java.util.List;


public class Map implements IRenderable{

    private List<Layer> layers = new ArrayList<Layer>();
    private static ResourceLoader rl;
    private int tileWidth=64, tileHeight =32;

    public Map(int size, ResourceLoader rl) {

        this.rl = rl;
        layers.add(new Layer(size, rl, ""));                  // layer 0 Gras
        //layers.add(new Layer(size, rl, "streets"));           // layer 2 Streets
        layers.add(new Layer(size, rl, "vegetation"));        // layer 1 Vegetation
    }

    @Override
    public void render(ResourceLoader rl) {
        Color.white.bind();
        Camera cam = Camera.getInstance();



        for (int i = 0; i < layers.size(); i++) {                           // layers
            for (int x = layers.get(i).getMap().length - 1; x >= 0; x--) {  // array x
                for (int y = 0; y < layers.get(i).getMap().length; y++) {   // array y

                    float xpos = (x * tileWidth / 2) + (y * tileWidth / 2) - cam.getPosition().x;
                    float ypos = ((y * tileHeight / 2) - (x * tileHeight / 2) - cam.getPosition().y);

                    // nur das zeichnen was in der Kamera zu sehen ist
                    if ((x * tileWidth / 2) + (y * tileWidth / 2) + tileWidth >= cam.getPosition().x &&
                            (x * tileWidth / 2) + (y * tileWidth / 2) <= cam.getPosition().x + cam.getRectangle().getWidth() &&
                            (y * tileHeight / 2) - (x * tileHeight / 2) + tileHeight >= cam.getPosition().getY() &&
                            (y * tileHeight / 2) - (x * tileHeight / 2) - tileHeight / 2 <= cam.getPosition().getY() + cam.getRectangle().getHeight()) {

                        if (layers.get(i).getMap()[x][y] != null) {
                            rl.bindTextureByID(layers.get(i).getMap()[x][y].getTextureId());
                            GL11.glBegin(GL11.GL_QUADS);

                            if ((int) rl.getTextureSizeByID(layers.get(i).getMap()[x][y].getTextureId()).y == 32) {
                                GL11.glTexCoord2f(0, 0);
                                GL11.glVertex2f(xpos, ypos);
                                GL11.glTexCoord2f(1, 0);
                                GL11.glVertex2f(xpos + tileWidth, ypos);
                                GL11.glTexCoord2f(1, 1);
                                GL11.glVertex2f(xpos + tileWidth, ypos + (int) rl.getTextureSizeByID(layers.get(i).getMap()[x][y].getTextureId()).y);
                                GL11.glTexCoord2f(0, 1);
                                GL11.glVertex2f(xpos, ypos + rl.getTextureSizeByID(layers.get(i).getMap()[x][y].getTextureId()).y);
                                GL11.glEnd();
                            }
                            // wenn tiles höher als 32 px sind, in dem fall nur 64, dann 32 abziehen damit es ordentlich gerendert wird
                            else {
                                GL11.glTexCoord2f(0, 0);
                                GL11.glVertex2f(xpos, ypos - tileHeight);
                                GL11.glTexCoord2f(1, 0);
                                GL11.glVertex2f(xpos + tileWidth, ypos - tileHeight);
                                GL11.glTexCoord2f(1, 1);
                                GL11.glVertex2f(xpos + tileWidth, ypos + (int) rl.getTextureSizeByID(layers.get(i).getMap()[x][y].getTextureId()).y - tileHeight);
                                GL11.glTexCoord2f(0, 1);
                                GL11.glVertex2f(xpos, ypos + rl.getTextureSizeByID(layers.get(i).getMap()[x][y].getTextureId()).y - tileHeight);
                                GL11.glEnd();
                            }
                        }
                    }
                }
            }
        }
    }

    private Vector2f rotate(Vector2f point, float degree) {

        double xNeu = point.getX() * Math.cos(degree) - point.getY() * Math.sin(degree);
        double yNeu = point.getX() * Math.sin(degree) + point.getY() * Math.cos(degree);

        return new Vector2f((float)xNeu, (float)yNeu);
    }
}