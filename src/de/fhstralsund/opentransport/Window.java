package de.fhstralsund.opentransport;

import de.fhstralsund.opentransport.core.entity.statics.BuildingStatic;
import de.fhstralsund.opentransport.core.entity.statics.CarID;
import de.fhstralsund.opentransport.core.entity.statics.StreetTID;
import de.fhstralsund.opentransport.core.screen.GamestateManager;
import de.fhstralsund.opentransport.core.io.Configreader;
import de.fhstralsund.opentransport.core.screen.screens.Game;
import de.fhstralsund.opentransport.core.screen.screens.Menu;
import de.fhstralsund.opentransport.core.screen.Camera;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
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
    private Camera cam;
    private static Vector2f displaySize;
    private StreetTID streetTID;
    private CarID carIDs;
    private BuildingStatic buildingStatic;

    public static void main(String[] args) {
        Window window = new Window();
        window.start();
    }

    public void start() {
        this.readConfig();
        this.setupGL((int)this.displaySize.x,(int)this.displaySize.y);
        this.setup();

        Display.setTitle("OpenTransport");

        while(true){
            GL11.glClearColor(0,0,0,1);
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
        configmap = config.loadConfig();
        this.displaySize = new Vector2f(configmap.get("width"), configmap.get("height"));
        cam = Camera.getInstance();
        cam.setValues(configmap.get("size"), new Vector2f(configmap.get("tilewidth"), configmap.get("tileheight")),
            new Vector2f(configmap.get("width"), configmap.get("height")));
    }

    private void update() {
        cam.update();
        this.gs.update();
    }

    private void render() {
        //render actual gamestate
        this.gs.render(rl);
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

        loadAssets(rl);

        //init ids and statics
        this.streetTID = new StreetTID(rl);
        this.carIDs = new CarID(rl);
        this.buildingStatic = new BuildingStatic(rl);

        //init gamestate manager and change gamestate
        this.gs = new GamestateManager();
        this.gs.addGameState(new Game(rl, configmap.get("size")));
        this.gs.addGameState(new Menu(rl));
        this.gs.switchGameState("game");
    }

    private void loadAssets(ResourceLoader rl) {
        rl.loadImageDir("res");
        rl.loadImageDir("res"+File.separator+"debug");
        rl.loadImageDir("res"+File.separator+"background");
        rl.loadImageDir("res"+File.separator+"ui");
        rl.loadImageDir("res"+File.separator+"gui");
        rl.loadImageDir("res"+File.separator+"landscape");
        rl.loadImageDir("res"+File.separator+"street"+File.separator+"rural");
        rl.loadImageDir("res"+File.separator+"street"+File.separator+"urban");
        rl.loadImageDir("res"+File.separator+"cars");
        rl.loadImageDir("res"+File.separator+"building");
        rl.loadImageDir("res"+File.separator+"industry");
    }

    public static Vector2f getDisplay(){
        return displaySize;
    }
}