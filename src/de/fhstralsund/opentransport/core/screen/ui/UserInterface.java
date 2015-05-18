package de.fhstralsund.opentransport.core.screen.ui;

import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import de.fhstralsund.opentransport.core.screen.ui.element.Buildmenu;
import de.fhstralsund.opentransport.core.screen.ui.element.Topbar;

import java.io.File;
import java.util.ArrayList;

public class UserInterface implements IRenderable,IUpdateable{

    private static ArrayList<UIElement> uiElements;

    //basicElements
    Topbar topbar;
    Buildmenu buildmenu;

    public UserInterface(ResourceLoader rl){
        uiElements = new ArrayList<UIElement>();
        
        this.setup(rl);
    }

    private void setup(ResourceLoader rl) {
        //create topbar
        topbar = new Topbar(true,"Topbar",100,0,600,50,rl.getTextureID("res"+ File.separator+"ui"+File.separator+"topbar.png"));
        addUIElement(topbar);

        //create buildmenu
        buildmenu = new Buildmenu(false,"BuildMenu",100,100,200,400,rl.getTextureID("res"+ File.separator+"ui"+File.separator+"buildmenu_bg.png"));
        UIButton buildMenuButton = new UIButton(buildmenu,0,0,30,30,rl.getTextureID("res"+ File.separator+"ui"+File.separator+"build_button.png"));
        topbar.addButton(buildMenuButton);
        uiElements.add(buildmenu);
    }


    @Override
    public void render(ResourceLoader rl) {
        for(UIElement e: uiElements){
            if(e.isVisible()){
                e.render(rl);
            }
        }
    }

    @Override
    public void update() {
        for(UIElement e: uiElements){
            e.update();
        }
    }

    public static void addUIElement(UIElement e){
        uiElements.add(e);
    }
}
