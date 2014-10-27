package de.fhstralsund.project;


import de.b4sh.core.resource.ResourceStorage;
<<<<<<< HEAD
import de.fhstralsund.project.Map.Map;
=======
import de.fhstralsund.project.resource.ResourceLoader;
>>>>>>> 2ff6c0f40e5f2f2897814f2e2daf5439e0e8f213
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

public class Window {

    private Map map;

    public static void main(String[] args) {
<<<<<<< HEAD

        ResourceStorage resourceStorage = new ResourceStorage();

=======
>>>>>>> 2ff6c0f40e5f2f2897814f2e2daf5439e0e8f213
        Window window = new Window();
        window.start();
    }

    public void start() {
        try {
            this.setup();

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

    private void setup(){
        ResourceLoader rl = new ResourceLoader();
    }
}
