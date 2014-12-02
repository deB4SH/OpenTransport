package de.fhstralsund.project.core.screen;

import de.fhstralsund.project.core.interfaces.IUpdateable;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Rectangle;
import org.lwjgl.util.vector.Vector2f;

public class Camera implements IUpdateable{

    private static Camera instance = null;
    private static Vector2f position;

    private static int width = 1600;
    private static int height = 800;
    private static int size = 64;
    private static int tilewidth = 64;
    private static int tileheight = 32;
    private Rectangle rectangle;
    private int zoomin;
    private int zoomout = 0;


    private Camera()  {
        position = new Vector2f(0, 0);
        rectangle = new Rectangle((int)position.x, (int)position.y, width, height);
    }

    public static Camera getInstance() {
        if (instance == null) {
            instance = new Camera();
        }
        return instance;
    }

    @Override
    public void update() {
        if ( instance == null)
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
}
