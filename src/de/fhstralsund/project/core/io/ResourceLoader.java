package de.fhstralsund.project.core.io;

import de.fhstralsund.project.core.io.resoucetype.BasicTexture;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.TextureLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ResourceLoader {

    ArrayList<BasicTexture> textureList;
    private Map<String, Integer> textures;

    public ResourceLoader() {
        this.textureList = new ArrayList<BasicTexture>();
        this.textures = new HashMap<String, Integer>();
    }

    public void loadImageFile(String filedir, String filename){
        try{
            String canoncialDir = new File(".").getCanonicalPath();
            this.textures.put(filename, textureList.size());
            this.textureList.add(new BasicTexture(TextureLoader.getTexture("PNG", org.newdawn.slick.util.ResourceLoader.getResourceAsStream(canoncialDir + File.separator + filedir + File.separator + filename)),filedir+"-"+filename,textureList.size()));
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
                    this.textures.put(e, textureList.size());
                    this.textureList.add(new BasicTexture(TextureLoader.getTexture("PNG", org.newdawn.slick.util.ResourceLoader.getResourceAsStream(canoncialDir + File.separator + filedir + File.separator + e)),filedir+"-"+e,this.textureList.size()));
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
        return new Vector2f(this.textureList.get(id).getTexture().getImageWidth(), this.textureList.get(id).getTexture().getImageHeight());
    }

    public Vector2f getTextureSizeByFileName(String filename){
        return new Vector2f(this.textureList.get(textures.get(filename)).getTexture().getImageWidth(), this.textureList.get(textures.get(filename)).getTexture().getImageHeight());
    }

    public void bindTextureByFileName(String filename){
        textureList.get(textures.get(filename)).bindTexture();
    }

    public Integer getTexturesID(String name) {
        return textures.get(name);
    }
}
