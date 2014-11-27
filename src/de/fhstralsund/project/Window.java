package de.fhstralsund.project;

import de.fhstralsund.project.core.screen.GamestateManager;
import de.fhstralsund.project.core.io.Configreader;
import de.fhstralsund.project.core.screen.screens.Game;
import de.fhstralsund.project.core.screen.screens.Menu;
import de.fhstralsund.project.core.screen.Camera;
import de.fhstralsund.project.core.io.ResourceLoader;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import java.io.File;
import java.util.HashMap;

public class Window {

    private GamestateManager gs;
    private static ResourceLoader rl;
    private Configreader config;
    private HashMap<String, Integer> configmap;
    private static Vector2f displaySize;

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
        config = new Configreader();
        //TODO: config wird bisher NICHT in Map und camera genutzt.. muss noch angepasst werden!
        configmap = config.loadConfig();
        this.displaySize = new Vector2f(configmap.get("width"), configmap.get("height"));
    }

    private void update() {
        Camera cam = Camera.getInstance();
        cam.update();
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
        rl.loadImageFile("res"+File.separator+"street"+File.separator+"rural", "Street_NE.png"); //0
        rl.loadImageFile("res"+File.separator+"street"+File.separator+"urban", "street_SE_.png"); //1
        rl.loadImageFile("res"+File.separator+"landscape", "wood1.png"); //0
        rl.loadImageFile("res"+File.separator+"landscape", "wood2.png"); //0
        rl.loadImageFile("res"+File.separator+"landscape", "wood3.png"); //0
        rl.loadImageFile("res"+File.separator+"landscape", "wood4.png"); //0
        rl.loadImageFile("res"+File.separator+"landscape", "wood5.png"); //0
        rl.loadImageFile("res"+File.separator+"landscape", "wood6.png"); //0

        rl.loadImageFile("res"+ File.separator + "background","menu_bg.png"); //9
        rl.loadImageFile("res"+ File.separator + "background","menu_logo.png"); //10

        this.gs = new GamestateManager();
        this.gs.addGameState(new Game(rl, configmap.get("size")));
        this.gs.addGameState(new Menu(rl));
        this.gs.switchGameState("game");
    }

    public static Vector2f getDisplay(){
        return displaySize;
    }
}