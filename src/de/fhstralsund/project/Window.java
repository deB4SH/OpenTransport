package de.fhstralsund.project;

import de.fhstralsund.project.core.GamestateManager;
import de.fhstralsund.project.core.io.Configreader;
import de.fhstralsund.project.core.screen.Game;
import de.fhstralsund.project.core.screen.Menu;
import de.fhstralsund.project.entity.Camera;
import de.fhstralsund.project.resource.ResourceLoader;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import java.io.File;

public class Window {

    private GamestateManager gs;
    private static ResourceLoader rl;
    private Configreader config;
    private Vector2f displaySize;

    public static void main(String[] args) {
        Window window = new Window();
        window.start();
    }

    public void start() {
        this.readConfig();
        this.setupGL((int)this.displaySize.x,(int)this.displaySize.y);
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

    private void readConfig() {
        this.displaySize = new Vector2f(1600, 800);
    }

    private void update() {
        Camera cam = Camera.getInstance();
        cam.Update();
        this.gs.update();
    }

    private void render() {
        //render actual gamestate
        this.gs.render();
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
        GL11.glOrtho(0, width, height, 0, 1, -1);
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
    }

    private void setup(){
        rl = new ResourceLoader();
        //load grastexture
        rl.loadImageFile("res", "ground.png");
        rl.loadImageFile("res"+File.separator+"street"+File.separator+"rural", "Street_NE.png");
        rl.loadImageFile("res"+File.separator+"street"+File.separator+"urban", "street_SE_.png");
        rl.loadImageFile("res"+File.separator+"landscape", "wood1.png");
        rl.loadImageFile("res"+File.separator+"landscape", "wood2.png");
        rl.loadImageFile("res"+File.separator+"landscape", "wood3.png");
        rl.loadImageFile("res"+File.separator+"landscape", "wood4.png");
        rl.loadImageFile("res"+File.separator+"landscape", "wood5.png");
        rl.loadImageFile("res"+File.separator+"landscape", "wood6.png");

        this.gs = new GamestateManager();
        this.gs.addGameState(new Game(rl, 50));
        this.gs.addGameState(new Menu(rl));
        this.gs.switchGameState("game");
    }
}