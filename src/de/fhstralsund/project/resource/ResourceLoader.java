package de.fhstralsund.project.resource;

import de.fhstralsund.project.resource.types.BasicTexture;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ResourceLoader {

    ArrayList<BasicTexture> textureList;

    public ResourceLoader() {
        this.textureList = new ArrayList<BasicTexture>();
    }

    public void loadImageFile(String filedir, String filename){
        try{
            String canoncialDir = new File(".").getCanonicalPath();
            this.textureList.add(new BasicTexture(TextureLoader.getTexture("PNG", org.newdawn.slick.util.ResourceLoader.getResourceAsStream(canoncialDir + "\\" + filedir + "\\" + filename)),filedir+"-"+filename,textureList.size()));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadImageDir(String filedir){
        try{
            String canoncialDir = new File(".").getCanonicalPath();
            File dir = new File(canoncialDir + "/" + filedir);
            String[] content = dir.list();
            //get content
            for(String e: content){
                if(e.toLowerCase().contains(".png")){
                    this.textureList.add(new BasicTexture(TextureLoader.getTexture("PNG", org.newdawn.slick.util.ResourceLoader.getResourceAsStream(canoncialDir + "\\" + filedir + "\\" + e)),filedir+"-"+e,this.textureList.size()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void bindTextureByID(int id){
        this.textureList.get(id).bindTexture();
    }

    public Vector2f getTextureSizeByID(int id){
        return new Vector2f(this.textureList.get(id).getTexture().getWidth(),this.textureList.get(id).getTexture().getHeight());
    }

    public void bindTextureByFileName(String filename){
        for(BasicTexture e: this.textureList){
            if(e.getFilename() == filename){
                e.bindTexture();
            }
        }
    }
}
