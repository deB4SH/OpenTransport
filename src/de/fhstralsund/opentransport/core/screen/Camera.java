package de.fhstralsund.opentransport.core.screen;

import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.screen.screens.Game;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

public class Camera implements IUpdateable{

    private static Camera instance = null;
    private static Vector2f position;

    private int width;
    private int height;
    private int size;
    private int tilewidth;
    private int tileheight;
    private Rectangle rectangle;
    private int zoomin = 0;
    private int zoomout = 0;


    private Camera()  {
        position = new Vector2f(0, 0);
    }

    public static Camera getInstance() {
        if (instance == null) {
            instance = new Camera();
        }
        return instance;
    }

    @Override
    public void update() {
        if (instance == null)
            return;

        if(Keyboard.isKeyDown(Keyboard.KEY_E) && !Keyboard.isKeyDown(Keyboard.KEY_Q)) {
            if(zoomin < 10) {
                GL11.glTranslated(width/2, height/2, 1);
                GL11.glScaled(1.05f, 1.05f, 1);
                GL11.glTranslated(-width/2, -height/2, -1);
                zoomin++;
                zoomout--;
            }
        }

        if(Keyboard.isKeyDown(Keyboard.KEY_Q) && !Keyboard.isKeyDown(Keyboard.KEY_E)) {
            if(zoomout < 10) {
                GL11.glTranslated(width/2, height/2, 1);
                GL11.glScaled(0.95f, 0.95f, 1);
                GL11.glTranslated(-width/2, -height/2, -1);
                zoomin--;
                zoomout++;
            }
        }

        if(Game.MOUSESCROLL){
            if(Mouse.getX() > (width * 95) / 100 || Keyboard.isKeyDown(Keyboard.KEY_D)) {
                position.setX(position.getX() + 3);
            }

            if(Mouse.getX() < (width * 5) / 100 || Keyboard.isKeyDown(Keyboard.KEY_A)) {
                position.setX(position.getX() - 3);
            }

            if(Mouse.getY() > (height * 95) / 100 || Keyboard.isKeyDown(Keyboard.KEY_W)) {
                position.setY(position.getY() - 3);
            }

            if(Mouse.getY() < (height * 5) / 100 || Keyboard.isKeyDown(Keyboard.KEY_S)) {
                position.setY(position.getY() + 3);
            }
        }
        else{
            if(Keyboard.isKeyDown(Keyboard.KEY_D)) {
                position.setX(position.getX() + 3);
            }

            if(Keyboard.isKeyDown(Keyboard.KEY_A)) {
                position.setX(position.getX() - 3);
            }

            if(Keyboard.isKeyDown(Keyboard.KEY_W)) {
                position.setY(position.getY() - 3);
            }

            if(Keyboard.isKeyDown(Keyboard.KEY_S)) {
                position.setY(position.getY() + 3);
            }
        }
        clamp();
    }

    private void clamp() {
        if(position.getX() < 0) {
            position.setX(0);
        }
        if(position.getX() > (size * tilewidth - width)) {
            position.setX(size * tilewidth - width);
        }
        if(position.getY() < (-size / 2) * tileheight) {
            position.setY((-size / 2) * tileheight);
        }
        if(position.getY() > (size / 2) * tileheight - height + tileheight /2) {
            position.setY((size / 2) * tileheight - height + tileheight / 2);
        }
    }

    public Vector2f getPosition() {
        return position;
    }

    public Rectangle getRectangle() {
        return rectangle;
    }

    public int getZoomin() {
        return zoomin;
    }

    public int getZoomout() {
        return zoomout;
    }

    public void setValues(int size, Vector2f tileWidthHeight, Vector2f screenWidthHeight) {
        this.size = size;
        this.tilewidth = (int)tileWidthHeight.x;
        this.tileheight = (int)tileWidthHeight.y;
        this.width = (int)screenWidthHeight.x;
        this.height = (int)screenWidthHeight.y;
        rectangle = new Rectangle((int)position.x, (int)position.y, width, height);
    }

    public int getSize() {
        return size;
    }
}
