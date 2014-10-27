package de.fhstralsund.project;

import de.fhstralsund.project.map.Map;
import de.fhstralsund.project.resource.ResourceLoader;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

public class Window {

    private Map map;
    private static ResourceLoader rl;

    public static void main(String[] args) {
        Window window = new Window();
        window.start();
    }

    public void start() {
        this.setupGL(800,600);
        this.setup();


        while(true){
            GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
            this.render();
            Display.update();
            Display.sync(100);

            if(Display.isCloseRequested()){
                Display.destroy();
                System.exit(0);
            }
        }
    }

    private void render() {
        map.Render();
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
        rl  = new ResourceLoader();

        //load grastexture
        rl.loadImageFile("res","ground.png");

        map = new Map(200,rl);
    }
}
