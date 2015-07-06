package de.fhstralsund.opentransport.core.screen.ui.element;

import de.fhstralsund.opentransport.*;
import de.fhstralsund.opentransport.core.entity.Entity;
import de.fhstralsund.opentransport.core.entity.EntityController;
import de.fhstralsund.opentransport.core.entity.type.Car;
import de.fhstralsund.opentransport.core.entity.type.Depot;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import de.fhstralsund.opentransport.core.screen.Camera;
import de.fhstralsund.opentransport.core.screen.screens.Game;
import de.fhstralsund.opentransport.core.screen.ui.UIButton;
import de.fhstralsund.opentransport.core.screen.ui.UIElement;
import de.fhstralsund.opentransport.core.screen.ui.UISubelement;
import de.fhstralsund.opentransport.core.screen.ui.action.CreateCarAction;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.*;
import org.lwjgl.util.Point;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.Color;
import org.newdawn.slick.TrueTypeFont;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class DepotMenue extends UIElement {

    private List<UIButton> buildMenuButtons;
    private int textureID;
    private int startButtonPosX;

    private UIButton createCarAction;

    private TrueTypeFont font;
    private Font awtFont;

    private boolean endChosen = false;
    private Entity endDepot = null;
    private boolean carChosen = false;


    private EntityController ec;

    private boolean mouseUp = false;

    public DepotMenue(boolean visible, String name, int posX, int posY, int width, int height, int textureID, EntityController entityController) {
        super(visible, name, posX, posY, width, height);
        this.buildMenuButtons = new ArrayList<UIButton>();
        this.textureID = textureID;
        this.ec = entityController;
        this.startButtonPosX = super.getPosX() + 70;

        awtFont = new Font("Times New Roman", Font.PLAIN, 18); //name, style (PLAIN, BOLD, or ITALIC), size
        this.font = new TrueTypeFont(awtFont, false); //base Font, anti-aliasing true/false

        DepotMenueVendor.setDepotMenue(this);
        //buildObj.add(new UISubelement(true, false, "street", this.getPosX() + 10, this.getPosY() + 10, 24, 48));
    }

    @Override
    public void render(ResourceLoader rl) {

        Color.white.bind();
        rl.bindTextureByID(textureID);
        GL11.glBegin(GL11.GL_QUADS);
        GL11.glTexCoord2f(0, 0);
        GL11.glVertex2f(super.getPosX(), super.getPosY());
        GL11.glTexCoord2f(1, 0);
        GL11.glVertex2f(super.getPosX() + super.getWidth(), super.getPosY());
        GL11.glTexCoord2f(1, 1);
        GL11.glVertex2f(super.getPosX() + super.getWidth(), super.getPosY() + super.getHeight());
        GL11.glTexCoord2f(0, 1);
        GL11.glVertex2f(super.getPosX(), super.getPosY() + super.getHeight());
        GL11.glEnd();

        if(endChosen) {
            renderText("Depot ausgewaehlt: " + ((Depot)endDepot).localnumber, new Vector2f(getPosX(), getPosY() + 10));
        }else {
            renderText("Depot ausgewaehlt: " + endChosen, new Vector2f(getPosX(), getPosY() + 10));
        }
        renderText("Auto   ausgewaehlt: " + carChosen, new Vector2f(getPosX(), getPosY() + 30));

        //render all buttonElements
        for(UIButton e: this.buildMenuButtons){
            e.render(rl);
        }
        createCarAction.render(rl);
    }

    public void renderText(String string, Vector2f position) {
        this.font.drawString(position.x, position.y, string);
    }

    @Override
    public void update() {
        if(isVisible()) {
            for (UIButton e : this.buildMenuButtons) {
                e.update();
            }

            Camera cam = Camera.getInstance();
            ReadablePoint p = new org.lwjgl.util.Point(Mouse.getX(), -Mouse.getY() + cam.getRectangle().getHeight()); // invertieren weil windows andere koordinaten liefert

            float isoMouseX = Math.round(((p.getX() + cam.getPosition().getX()) / Game.TILEWIDTH) - ((p.getY() + cam.getPosition().getY()) / Game.TILEHEIGHT));
            float isoMouseY = Math.round(((p.getX() + cam.getPosition().getX()) / Game.TILEWIDTH) + ((p.getY() + cam.getPosition().getY()) / Game.TILEHEIGHT)) - 1;

            if(isoMouseX > 0 && isoMouseY > 0 && ec.getEntityVec((int)isoMouseX, (int)isoMouseY) != null && ec.getEntityVec((int)isoMouseX, (int)isoMouseY).getClass() == Depot.class && Mouse.isButtonDown(0) &&!mouseUp) {
                if(DepotMenueVendor.startPos.getX() != (int)isoMouseX && DepotMenueVendor.startPos.getY() != (int)isoMouseY) {
                    endDepot = ec.getEntityVec((int) isoMouseX, (int) isoMouseY);
                    endChosen = true;
                }
            }

            if(endDepot != null && createCarAction.getCollisionBox().contains(new Point(Mouse.getX(), -Mouse.getY() + (int) de.fhstralsund.opentransport.Window.getDisplay().getY()))
                && Mouse.isButtonDown(0) && !mouseUp) {
                createCarAction.getUiButtonAction().fireAction(ec, DepotMenueVendor.startPos, endDepot);
                cancelGui();
            }

            if(Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
                cancelGui();
            }

            mouseUp = Mouse.isButtonDown(0);
        }
    }

    public void addButton(UIButton button) {

        UIButton tmp = button;
        tmp.setPosX(this.buildMenuButtons.size() == 0 ? startButtonPosX : buildMenuButtons.get(this.buildMenuButtons.size()-1).getPosX()
                + 48);
        tmp.setPosY(super.getPosY()+80);

        buildMenuButtons.add(button);
    }

    private void cancelGui(){
        this.setVisible(false);
        DepotMenueVendor.startPos = null;
        this.endDepot = null;
        endChosen = false;
    }

    public UIButton getCreateCarAction() {
        return createCarAction;
    }

    public void setCreateCarAction(UIButton createCarAction) {
        this.createCarAction = createCarAction;
    }
}