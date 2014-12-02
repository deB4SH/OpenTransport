package de.fhstralsund.project.core.entity.type;

import de.fhstralsund.project.core.entity.Entity;
import de.fhstralsund.project.core.interfaces.IRenderable;
import de.fhstralsund.project.core.interfaces.IUpdateable;
import de.fhstralsund.project.core.io.ResourceLoader;
import de.fhstralsund.project.core.screen.Camera;
import de.fhstralsund.project.core.screen.screens.Game;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

public class Street extends Entity implements IUpdateable,IRenderable{

    private int textureID;
    private boolean north,east,south,west;

    public Street(Vector2f start, int textureID, boolean north, boolean south, boolean west, boolean east) {
        super(start,true);
        this.textureID = textureID;
        this.north = north;
        this.east = east;
        this.south = south;
        this.east = east;
    }

    @Override
    public void render(ResourceLoader rl) {
        rl.bindTextureByID(textureID);

        Camera cam = Camera.getInstance(); //TODO: rework to Controller/Object
        float xpos = (super.getTilePos().getX() * Game.TILEWIDTH / 2) + (super.getTilePos().getY() * Game.TILEWIDTH / 2) - cam.getPosition().x;
        float ypos = ((super.getTilePos().getY() * Game.TILEHEIGHT / 2) - (super.getTilePos().getX() * Game.TILEHEIGHT / 2) - cam.getPosition().y);

        //render me if in camera view
        if((super.getTilePos().getX() * Game.TILEWIDTH / 2 ) + ( super.getTilePos().getY() * Game.TILEWIDTH / 2 ) + Game.TILEWIDTH >= cam.getPosition().x &&
                (super.getTilePos().getX() * Game.TILEWIDTH / 2 ) + ( super.getTilePos().getY() * Game.TILEWIDTH / 2 ) <= cam.getPosition().x + cam.getRectangle().getWidth() &&
                (super.getTilePos().getX() * Game.TILEHEIGHT / 2 ) - ( super.getTilePos().getX() * Game.TILEHEIGHT / 2 ) + Game.TILEHEIGHT >= cam.getPosition().getY() &&
                (super.getTilePos().getY() * Game.TILEHEIGHT / 2) - (super.getTilePos().getX() * Game.TILEHEIGHT / 2) - Game.TILEHEIGHT / 2 <= cam.getPosition().getY() + cam.getRectangle().getHeight()) {

            GL11.glBegin(GL11.GL_QUADS);
                GL11.glTexCoord2f(0, 0);
                GL11.glVertex2f(xpos, ypos);
                GL11.glTexCoord2f(1, 0);
                GL11.glVertex2f(xpos + Game.TILEWIDTH, ypos);
                GL11.glTexCoord2f(1, 1);
                GL11.glVertex2f(xpos + Game.TILEWIDTH, ypos + rl.getTextureSizeByID(textureID).y);
                GL11.glTexCoord2f(0, 1);
                GL11.glVertex2f(xpos, ypos + rl.getTextureSizeByID(textureID).y);
            GL11.glEnd();
        }
    }

    @Override
    public void update() {

    }
}
