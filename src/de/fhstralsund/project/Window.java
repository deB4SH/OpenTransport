package de.fhstralsund.project;

import de.fhstralsund.project.Map.Map;
import de.fhstralsund.project.resource.ResourceLoader;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Window {

    private Map map;

    public static void main(String[] args) {
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
