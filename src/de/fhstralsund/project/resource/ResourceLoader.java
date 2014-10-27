package de.fhstralsund.project.resource;

import de.fhstralsund.project.resource.types.BasicTexture;
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
            this.textureList.add(new BasicTexture(TextureLoader.getTexture("PNG", org.newdawn.slick.util.ResourceLoader.getResourceAsStream(canoncialDir + "/" + filedir + "/" + filename)),filedir+"-"+filename));
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
                    this.textureList.add(new BasicTexture(TextureLoader.getTexture("PNG", org.newdawn.slick.util.ResourceLoader.getResourceAsStream(canoncialDir + "/" + filedir + "/" + e)),filedir+"-"+e));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
