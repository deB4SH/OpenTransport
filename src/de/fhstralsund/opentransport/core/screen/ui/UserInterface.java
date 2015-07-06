package de.fhstralsund.opentransport.core.screen.ui;

import de.fhstralsund.opentransport.core.entity.EntityController;
import de.fhstralsund.opentransport.core.entity.statics.StreetTID;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import de.fhstralsund.opentransport.core.screen.ui.action.CreateCarAction;
import de.fhstralsund.opentransport.core.screen.ui.action.CreateDepotAction;
import de.fhstralsund.opentransport.core.screen.ui.action.CreateStreetAction;
import de.fhstralsund.opentransport.core.screen.ui.element.Buildmenu;
import de.fhstralsund.opentransport.core.screen.ui.element.DepotMenue;
import de.fhstralsund.opentransport.core.screen.ui.element.Topbar;

import java.io.File;
import java.util.ArrayList;

public class UserInterface implements IRenderable,IUpdateable{

    private static ArrayList<UIElement> uiElements;
    private EntityController entityController;

    //basicElements
    Topbar topbar;
    Buildmenu buildmenu;
    DepotMenue depotmenue;

    public UserInterface(ResourceLoader rl, EntityController entityController){
        uiElements = new ArrayList<UIElement>();
        this.entityController = entityController;

        this.setup(rl);
    }

    private void setup(ResourceLoader rl) {
        //create topbar
        topbar = new Topbar(true,"Topbar",100,0,600,50,rl.getTextureID("res"+ File.separator+"ui"+File.separator+"topbar.png"));
        uiElements.add(topbar);

        //create buildmenu
        buildmenu = new Buildmenu(false,"BuildMenu",100,100,200,48,rl.getTextureID("res"+ File.separator+"ui"+File.separator+"buildmenu_bg.png"));
        UIButton buildStreetButton = new UIButton(new CreateStreetAction(), 100, 100, 48, 24, StreetTID.urban_street_ns, entityController);
        UIButton buildDepotButton = new UIButton(new CreateDepotAction(), 100, 100, 48, 24, rl.getTextureID("res"+ File.separator+"building"+File.separator+"road_Depot.png"), entityController);
        buildmenu.addButton(buildStreetButton);
        buildmenu.addButton(buildDepotButton);

        depotmenue = new DepotMenue(false,"DepotMenu",100,200,200,130,rl.getTextureID("res"+ File.separator+"ui"+File.separator+"buildmenu_bg.png"), entityController);
        depotmenue.setCreateCarAction(new UIButton(new CreateCarAction(), 100, 280, 60, 40, rl.getTextureID("res"+ File.separator+"ui"+File.separator+"hire.png"), entityController));


        UIButton buildMenuButton = new UIButton(buildmenu,0,0,30,30,rl.getTextureID("res"+ File.separator+"ui"+File.separator+"build_button.png"));
        topbar.addButton(buildMenuButton);

        uiElements.add(buildmenu);
        uiElements.add(depotmenue);
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
