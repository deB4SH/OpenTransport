package de.fhstralsund.opentransport.core.entity.type;

import de.fhstralsund.opentransport.core.entity.Entity;
import de.fhstralsund.opentransport.core.entity.EntityController;
import de.fhstralsund.opentransport.core.entity.statics.Goods;
import de.fhstralsund.opentransport.core.entity.statics.IndustryType;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import de.fhstralsund.opentransport.core.screen.Camera;
import de.fhstralsund.opentransport.core.screen.screens.Game;
import de.fhstralsund.opentransport.core.screen.ui.element.DepotMenueVendor;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.Point;
import org.lwjgl.util.ReadablePoint;
import org.lwjgl.util.vector.Vector2f;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Depot extends Entity {

    public boolean isPlaced = false;
    public static int globalNumbers = 0;
    public int localnumber = 0;
    private EntityController entityController;
    private List<Entity> nearbyindustry = new ArrayList<Entity>();
    private Storage storage;

    private boolean mouseUp = false;

    public Depot(Vector2f tilePos, Boolean enterAble, int textureId, EntityController entityController) {
        super(tilePos, enterAble);
        this.isPlaced = enterAble;
        if(isPlaced) {
            localnumber = globalNumbers;
            globalNumbers++;

            for(int i = -4; i <= 4; i++) {
                for (int j = -4; j <= 4; j++) {
                    if((i != 0 || j!= 0)
                            && (int) tilePos.getX() - i > 0 && (int) tilePos.getY() - j > 0
                            && (int) tilePos.getX() - i < Camera.getInstance().getSize() && (int) tilePos.getY() - j < Camera.getInstance().getSize()) {
                        Entity temp = entityController.getEntityVec((int) tilePos.getX() - i, (int) tilePos.getY() - j);
                        if (temp != null && temp.getClass() == Industry.class) {
                            if(((Industry)temp).getType() != IndustryType.Field) {
                                nearbyindustry.add(temp);
                            }
                        }
                    }
                }
            }
        }

        this.setTextureID(textureId);
        this.entityController = entityController;
        this.storage = new Storage(this);
    }

    @Override
    public void update() {

        for(int i = 0; i < nearbyindustry.size(); i++) {
            if(((Industry)nearbyindustry.get(i)).getType() == IndustryType.Farm) {
                storage.addGoods(Goods.Food, ((Industry)nearbyindustry.get(i)).getAvailableAmount());
            }
            if(((Industry)nearbyindustry.get(i)).getType() == IndustryType.Wood) {
                storage.addGoods(Goods.Furniture, ((Industry)nearbyindustry.get(i)).getAvailableAmount() / 25);
            }
        }

        Camera cam = Camera.getInstance();
        ReadablePoint p = new Point(Mouse.getX(), -Mouse.getY() + cam.getRectangle().getHeight()); // invertieren weil windows andere koordinaten liefert

        float isoMouseX = Math.round(((p.getX() + cam.getPosition().getX()) / Game.TILEWIDTH) - ((p.getY() + cam.getPosition().getY()) / Game.TILEHEIGHT));
        float isoMouseY = Math.round(((p.getX() + cam.getPosition().getX()) / Game.TILEWIDTH) + ((p.getY() + cam.getPosition().getY()) / Game.TILEHEIGHT)) - 1;

        if(this.getTilePos().getX() == isoMouseX && this.getTilePos().getY() == isoMouseY && Mouse.isButtonDown(0) && !mouseUp) {
            if (isPlaced) {
                if(!DepotMenueVendor.getDepotMenue().isVisible()) {
                    DepotMenueVendor.getDepotMenue().setVisible(true);
                    DepotMenueVendor.startPos = this.getTilePos();
                    DepotMenueVendor.startDepot = this;
                }
            }
        }
        mouseUp = Mouse.isButtonDown(0);
    }

    @Override
    public void render(ResourceLoader rl) {

        if(!isPlaced) {
            drawCircle(rl, super.getTilePos());
        }

        super.render(rl);
    }

    private void drawCircle(ResourceLoader rl, Vector2f position) {

        int textureID = rl.getTextureID("res"+ File.separator+"gui"+File.separator+"Unbenannt.png");

        rl.bindTextureByID(textureID);

        Camera cam = Camera.getInstance();
        float xpos = (position.getX() * Game.TILEWIDTH / 2) + (position.getY() * Game.TILEWIDTH / 2) - cam.getPosition().x;
        float ypos = ((position.getY() * Game.TILEHEIGHT / 2) - (position.getX() * Game.TILEHEIGHT / 2) - cam.getPosition().y);

        //screenrelated render
        if((getTilePos().getX() * Game.TILEWIDTH / 2 ) + ( getTilePos().getY() * Game.TILEWIDTH / 2 ) + Game.TILEWIDTH >= cam.getPosition().x &&
                (getTilePos().getX() * Game.TILEWIDTH / 2 ) + ( getTilePos().getY() * Game.TILEWIDTH / 2 ) <= cam.getPosition().x + cam.getRectangle().getWidth() &&
                (getTilePos().getY() * Game.TILEHEIGHT / 2 ) - ( getTilePos().getX() * Game.TILEHEIGHT / 2 ) + Game.TILEHEIGHT >= cam.getPosition().getY() &&
                (getTilePos().getY() * Game.TILEHEIGHT / 2) - (getTilePos().getX() * Game.TILEHEIGHT / 2) - Game.TILEHEIGHT / 2 <= cam.getPosition().getY() + cam.getRectangle().getHeight()) {

            GL11.glBegin(GL11.GL_QUADS);
            GL11.glTexCoord2f(0, 0);

            GL11.glVertex2f((xpos - rl.getTextureSizeByIDWidth(textureID) / 2)  + 64 ,
                    ypos - rl.getTextureSizeByIDHeight(textureID) / 2 + 32);

            GL11.glTexCoord2f(1, 0);
            GL11.glVertex2f((xpos - rl.getTextureSizeByIDWidth(textureID) / 2)  + 64 + rl.getTextureSizeByIDWidth(textureID),
                    ypos - rl.getTextureSizeByIDHeight(textureID) / 2 + 32);

            GL11.glTexCoord2f(1, 1);
            GL11.glVertex2f((xpos - rl.getTextureSizeByIDWidth(textureID) / 2)  + 64 + rl.getTextureSizeByIDWidth(textureID),
                    ypos+ rl.getTextureSizeByIDHeight(textureID) - rl.getTextureSizeByIDHeight(textureID) / 2 +32);

            GL11.glTexCoord2f(0, 1);
            GL11.glVertex2f((xpos - rl.getTextureSizeByIDWidth(textureID) / 2)  + 64,
                    ypos+ rl.getTextureSizeByIDHeight(textureID) - rl.getTextureSizeByIDHeight(textureID) / 2 +32);

            GL11.glEnd();
        }
    }

    public void requestRessources(Storage carStorage){
        carStorage.addAllGoods(this.storage);
        storage = new Storage(this);
    }

    public void giveRessources(Storage carStorage){
        this.storage.addAllGoods(carStorage);
    }

    public Storage getStorage() {
        return storage;
    }
}
