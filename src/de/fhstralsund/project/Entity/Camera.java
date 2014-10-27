package de.fhstralsund.project.entity;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

public class Camera {

    private static Camera instance = null;
    private static Vector2f position;

    private static int width = 800;
    private static int height = 600;
    private static int size = 100;

    private Camera() {
        position = new Vector2f(0, 0);
    }

    public static Camera getInstance() {
        if (instance == null) {
            instance = new Camera();
        }
        return instance;
    }

    public void Update() {
        if ( instance == null)
            return;

        clamp();

        if(Mouse.getX() >(width * 90) / 100 || Keyboard.isKeyDown(Keyboard.KEY_D)) {
            position.setX(position.getX() + 3);
        }

        if(Mouse.getX() < (width * 10) / 100 || Keyboard.isKeyDown(Keyboard.KEY_A)) {
            position.setX(position.getX() - 3);
        }

        if(Mouse.getY() > (height * 90) / 100 || Keyboard.isKeyDown(Keyboard.KEY_W)) {
            position.setY(position.getY() - 3);
        }

        if(Mouse.getY() < (height * 10) / 100 || Keyboard.isKeyDown(Keyboard.KEY_S)) {
            position.setY(position.getY() + 3);
        }
    }

    private void clamp() {
        if(position.getX() < 0) {
            position.setX(0);
        }
        if(position.getX() > (size * 64 - width)) {
            position.setX(size * 64 - width);
        }
        if(position.getY() < (-size / 2) * 32) {
            position.setY((-size / 2) * 32);
        }
        if(position.getY() > (size / 2) * 32) {
            position.setY((size / 2) * 32);
        }
    }

    public Vector2f getPosition() {
        return position;
    }
}
