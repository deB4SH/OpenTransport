package de.fhstralsund.opentransport.core.entity.type;

import de.fhstralsund.opentransport.core.entity.Entity;
import de.fhstralsund.opentransport.core.entity.EntityController;
import de.fhstralsund.opentransport.core.entity.statics.StreetTID;
import de.fhstralsund.opentransport.core.interfaces.IRenderable;
import de.fhstralsund.opentransport.core.interfaces.IUpdateable;
import de.fhstralsund.opentransport.core.io.ResourceLoader;
import org.lwjgl.util.vector.Vector2f;

public class Street extends Entity implements IUpdateable,IRenderable{

    public Street(Vector2f start, int textureID) {
        super(start,true);
        super.setTextureID(textureID);
    }

    @Override
    public void render(ResourceLoader rl) {
        super.render(rl);
    }

    @Override
    public void update() {

    }

    @Override
    public void updateTexture(EntityController entityController){

        boolean north=false,east=false,south=false,west=false;

        //check the directions
        if(entityController.isEntityOnVec(new Vector2f(super.getTilePos().getX()+1,super.getTilePos().getY()))){
            north = true;
        }
        if(entityController.isEntityOnVec(new Vector2f(super.getTilePos().getX()-1,super.getTilePos().getY()))){
            south = true;
        }
        if(entityController.isEntityOnVec(new Vector2f(super.getTilePos().getX(),super.getTilePos().getY()+1))){
            east = true;
        }
        if(entityController.isEntityOnVec(new Vector2f(super.getTilePos().getX(),super.getTilePos().getY()-1))){
            west = true;
        }

        //select by direction the correct texture
        if(north && east && west && !south){
            super.setTextureID(StreetTID.urban_cross_nwe);
        }
        else if(north && east && south && !west){
            super.setTextureID(StreetTID.urban_cross_sen);
        }
        else if(north && west && south && !east){
            super.setTextureID(StreetTID.urban_cross_swn);
        }
        else if(south && east && west && !north){
            super.setTextureID(StreetTID.urban_cross_swe);
        }
        else if(north && east && south && west) {
            super.setTextureID(StreetTID.urban_cross);
        }
        else if(north && south && !east && !west){
            super.setTextureID(StreetTID.urban_street_ns);
        }
        else if(north && east && !west && !south){
            super.setTextureID(StreetTID.urban_curve_ne);
        }
        else if(north && west && !south && !east){
            super.setTextureID(StreetTID.urban_curve_nw);
        }
        else if(south && east && !north && !west) {
            super.setTextureID(StreetTID.urban_curve_se);
        }
        else if(south && west && !north && !east) {
            super.setTextureID(StreetTID.urban_curve_sw);
        }
        else if(!south && !west && north && !east) {
            super.setTextureID(StreetTID.urban_end_s);
        }
        else if(!south && west && !north && east) {
            super.setTextureID(StreetTID.urban_street_we);
        }
        else if(south && !west && !north && !east) {
            super.setTextureID(StreetTID.urban_end_n);
        }
        else if(!south && !west && !north && east) {
            super.setTextureID(StreetTID.urban_end_w);
        }
        else if(!south && west && !north && !east) {
            super.setTextureID(StreetTID.urban_end_e);
        }
    }
}
