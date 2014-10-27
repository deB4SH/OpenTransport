package de.fhstralsund.project;


import de.b4sh.core.resource.ResourceStorage;
import de.fhstralsund.project.Map.Map;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class Window {

    private Map map;

    public static void main(String[] args) {

        ResourceStorage resourceStorage = new ResourceStorage();

        Window window = new Window();
        window.start();
    }

    public void start() {
        try {
            Display.setDisplayMode(new DisplayMode(800,600));
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        // init OpenGL
        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0,800,0,600,1,-1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        map = new Map(200);

        while (!Display.isCloseRequested()) {
            map.Render();
        }

        Display.destroy();
    }
}
