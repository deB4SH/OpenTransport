package de.fhstralsund.project;

import de.fhstralsund.project.core.GamestateManager;
import de.fhstralsund.project.entity.Camera;
import de.fhstralsund.project.map.Map;
import de.fhstralsund.project.resource.ResourceLoader;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Window {

    private GamestateManager gs;

    public static void main(String[] args) {
        Window window = new Window();
        window.start();
    }

    public void start() {
        this.setupGL(1600,800);
        this.setup();


        while(true){
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            this.update();
            this.render();
            Display.update();
            Display.sync(100);

            if(Display.isCloseRequested()){
                Display.destroy();
                System.exit(0);
            }
        }
    }

    private void update() {
        Camera cam = Camera.getInstance();
        cam.Update();
    }

    private void render() {

    }

    private void setupGL(int width,int height){
        try{
            Display.setDisplayMode(new DisplayMode(width,height));
            Display.create();
            Display.setVSyncEnabled(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
            System.exit(0);
        }

        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA,GL11.GL_ONE_MINUS_SRC_ALPHA);

        GL11.glViewport(0,0,width,height);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);

        GL11.glMatrixMode(GL11.GL_PROJECTION);
        GL11.glLoadIdentity();
        GL11.glOrtho(0,width,height,0,1,-1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    private void setup(){
        this.gs = new GamestateManager();
    }
}
