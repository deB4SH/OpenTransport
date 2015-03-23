package de.fhstralsund.opentransport.core.entity;

import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import de.fhstralsund.opentransport.core.screen.Camera;
import de.fhstralsund.opentransport.core.screen.screens.Game;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class Entity implements IUpdateable, IRenderable {

    private Vector2f tilePos;
    private boolean enterAble;
    private int textureID;

    public Entity(Vector2f tilePos, Boolean enterAble) {
        this.enterAble = enterAble;
        this.tilePos = tilePos;
    }

    public boolean isEnterAble() {
        return enterAble;
    }

    public void setEnterAble(boolean enterAble) {
        this.enterAble = enterAble;
    }

    public Vector2f getTilePos() {
        return tilePos;
    }

    public void setTilePos(Vector2f tilePos) {
        this.tilePos = tilePos;
    }

    @Override
    public void render(ResourceLoader rl) {
        rl.bindTextureByID(textureID);

        Camera cam = Camera.getInstance(); //TODO: rework to Controller/Object
        float xpos = (getTilePos().getX() * Game.TILEWIDTH / 2) + (getTilePos().getY() * Game.TILEWIDTH / 2) - cam.getPosition().x;
        float ypos = ((getTilePos().getY() * Game.TILEHEIGHT / 2) - (getTilePos().getX() * Game.TILEHEIGHT / 2) - cam.getPosition().y);
        //screenrelated render
        if((getTilePos().getX() * Game.TILEWIDTH / 2 ) + ( getTilePos().getY() * Game.TILEWIDTH / 2 ) + Game.TILEWIDTH >= cam.getPosition().x &&
                (getTilePos().getX() * Game.TILEWIDTH / 2 ) + ( getTilePos().getY() * Game.TILEWIDTH / 2 ) <= cam.getPosition().x + cam.getRectangle().getWidth() &&
                (getTilePos().getY() * Game.TILEHEIGHT / 2 ) - ( getTilePos().getX() * Game.TILEHEIGHT / 2 ) + Game.TILEHEIGHT >= cam.getPosition().getY() &&
                (getTilePos().getY() * Game.TILEHEIGHT / 2) - (getTilePos().getX() * Game.TILEHEIGHT / 2) - Game.TILEHEIGHT / 2 <= cam.getPosition().getY() + cam.getRectangle().getHeight()) {

            GL11.glBegin(GL11.GL_QUADS);
                GL11.glTexCoord2f(0, 0);
                GL11.glVertex2f(xpos, ypos);
                GL11.glTexCoord2f(1, 0);
                GL11.glVertex2f(xpos + rl.getTextureSizeByIDWidth(textureID), ypos);
                GL11.glTexCoord2f(1, 1);
                GL11.glVertex2f(xpos + rl.getTextureSizeByIDWidth(textureID), ypos + rl.getTextureSizeByIDHeight(textureID));
                GL11.glTexCoord2f(0, 1);
                GL11.glVertex2f(xpos, ypos + rl.getTextureSizeByIDHeight(textureID));
            GL11.glEnd();
        }
    }

    @Override
    public void update() {

    }

    public void updateTexture(EntityController entityController){

    }

    public int getTextureID() {
        return textureID;
    }

    public void setTextureID(int textureID) {
        this.textureID = textureID;
    }
}
