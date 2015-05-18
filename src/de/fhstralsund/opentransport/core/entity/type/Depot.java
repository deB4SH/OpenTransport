package de.fhstralsund.opentransport.core.entity.type;

import de.fhstralsund.opentransport.core.entity.Entity;
import de.fhstralsund.opentransport.core.entity.EntityController;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import de.fhstralsund.opentransport.core.screen.Camera;
import de.fhstralsund.opentransport.core.screen.screens.Game;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import java.io.File;

public class Depot extends Entity {

    public boolean isPlaced = false;
    private EntityController entityController;

    int i = 0;

    public Depot(Vector2f tilePos, Boolean enterAble, int textureId, EntityController entityController) {
        super(tilePos, enterAble);
        this.isPlaced = enterAble;
        this.setTextureID(textureId);
        this.entityController = entityController;
    }

    @Override
    public void update() {

        //TODO: per GUI den Zielweg bestimmen
        if(i % 1000 == 0 && isPlaced) {
            i  = 0;
            entityController.addCar(new Car(this.getTilePos(), 20, false, entityController.requestNewWay(this.getTilePos(), new Vector2f(6, 20)),entityController));
        }
        i++;

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
}
