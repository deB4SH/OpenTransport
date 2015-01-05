package de.fhstralsund.opentransport.core.entity.type;

import de.fhstralsund.opentransport.core.entity.EntityController;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import de.fhstralsund.opentransport.core.screen.Camera;
import de.fhstralsund.opentransport.core.screen.screens.Game;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import java.io.File;
import java.util.Random;

public class Vegetation implements IRenderable {

    private int mapSize;
    private int[][] veg;
    private static int tileWidth = 64, tileHeight = 32;

    public Vegetation(int mapSize, ResourceLoader rl, EntityController ec) {
        this.mapSize = mapSize;
        veg = new int[mapSize][mapSize];
        generateVegeration(rl,ec);
    }

    private void generateVegeration(ResourceLoader rl, EntityController ec) {
        for (int i = 0; i < mapSize; i++) {
            for (int j = 0; j < mapSize; j++) {
                if ((i % 2 == 0 || j % 2 == 0) && i != 5 && j != 5 &&
                        ((new Random().nextInt(mapSize) + 2) % (j + 1) != 0 &&
                                (new Random().nextInt(mapSize) + 2) % (i + 1) != 0) && (new Random().nextInt(6) + 1) % 2 == 0) {
                    if(!ec.isEntityOnVec(new Vector2f(i,j)))
                        this.veg[i][j] = rl.getTextureID("res" + File.separator + "landscape" + File.separator + "wood" + (int)(new Random().nextInt(5)+1) + ".png");
                }
            }
        }
    }


    @Override
    public void render(ResourceLoader rl) {

        Camera cam = Camera.getInstance();

        for (int x = mapSize - 1; x >= 0; x--) {
            for (int y = 0; y < mapSize; y++) {
                if(veg[x][y] >= 1){
                    float xpos = (x * tileWidth / 2) + (y * tileWidth / 2) - cam.getPosition().x;
                    float ypos = (y * tileHeight / 2) - (x * tileHeight / 2) - cam.getPosition().y;

                    if ((x * Game.TILEWIDTH / 2) + (y * Game.TILEWIDTH / 2) + Game.TILEWIDTH >= cam.getPosition().x &&
                            (x * Game.TILEWIDTH / 2) + (y * Game.TILEWIDTH / 2) <= cam.getPosition().x + cam.getRectangle().getWidth() &&
                            (y * Game.TILEHEIGHT / 2) - (x * Game.TILEHEIGHT / 2) + Game.TILEHEIGHT >= cam.getPosition().getY() &&
                            (y * Game.TILEHEIGHT / 2) - (x * Game.TILEHEIGHT / 2) - Game.TILEHEIGHT / 2 <= cam.getPosition().getY() + cam.getRectangle().getHeight()) {

                        rl.bindTextureByID(veg[x][y]);

                        GL11.glBegin(GL11.GL_QUADS);
                            GL11.glTexCoord2f(0, 0);
                            GL11.glVertex2f(xpos, ypos - tileHeight);
                            GL11.glTexCoord2f(1, 0);
                            GL11.glVertex2f(xpos + tileWidth, ypos - tileHeight);
                            GL11.glTexCoord2f(1, 1);
                            GL11.glVertex2f(xpos + tileWidth, ypos + (int) rl.getTextureSizeByID(veg[x][y]).y - tileHeight);
                            GL11.glTexCoord2f(0, 1);
                            GL11.glVertex2f(xpos, ypos + (int) rl.getTextureSizeByID(veg[x][y]).y - tileHeight);
                        GL11.glEnd();
                    }
                }
            }
        }
    }

    public boolean isVegetationOn(Vector2f vec){
        if(veg[((int) vec.x)][((int) vec.y)] >= 0)
            return true;
        return false;
    }

    public boolean isVegetationOn(int x, int y){
        if(veg[x][y] >= 0)
            return true;
        return false;
    }

    public void removeVegetationAt(Vector2f vec){
        this.veg[((int) vec.x)][((int) vec.y)] = 0;
    }

    public void removeVegetationAt(int x,int y){
        this.veg[x][y] = 0;
    }
}
