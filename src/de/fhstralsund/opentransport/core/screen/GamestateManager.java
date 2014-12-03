package de.fhstralsund.opentransport.core.screen;

import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;

import java.util.ArrayList;

public class GamestateManager implements IRenderable,IUpdateable{

    ArrayList<GameScreen> gameStates;
    GameScreen activeScreen,oldScreen;

    public GamestateManager() {
        this.gameStates = new ArrayList<GameScreen>();
    }

    @Override
    public void update() {
        activeScreen.update();
    }

    public void addGameState(GameScreen gamescreen){
        this.gameStates.add(gamescreen);
    }

    public void switchGameState(String name){
        for(GameScreen e: this.gameStates){
            if(e.getScreenName() == name){
                oldScreen = activeScreen;
                activeScreen = e;
                break;
            }
        }
    }

    public void switchGameState(int id){
        oldScreen = activeScreen;
        activeScreen = this.gameStates.get(id);
    }

    @Override
    public void render(ResourceLoader rl) {
        activeScreen.render(rl);
    }
}
