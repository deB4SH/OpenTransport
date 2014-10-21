package de.b4sh.core.resource;

import org.newdawn.slick.Font;
import org.newdawn.slick.openal.Audio;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.util.ArrayList;
import com.google.common.jimfs.*;

public class ResourceStorage {

    private ArrayList<Texture> textures;
    private ArrayList<Audio> audios;
    private ArrayList<Font> fonts;

    public ResourceStorage(){
        this.textures = new ArrayList<Texture>();
        this.audios = new ArrayList<Audio>();
        this.fonts = new ArrayList<Font>();
    }


    /*
     * Load an image from folder
     * @return: void
     */
    public void loadImage(String fileDir,String fileName){
        try{
            String canoncialDir = new File(".").getCanonicalPath();
            this.textures.add(TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream(canoncialDir + "/" + fileDir + "/" + fileName)));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /*
     * Load images from folder
     * @return: void
     */
    public void loadImagesFromFolder(String fileDir){
        try{
            String canoncialDir = new File(".").getCanonicalPath();
            File dir = new File(canoncialDir + "/" + fileDir);
            String[] content = dir.list();



            //get content
            for(String e: content){
                if(e.toLowerCase().contains(".png")){
                    this.textures.add(TextureLoader.getTexture("PNG",ResourceLoader.getResourceAsStream(dir.getCanonicalPath() + "/" + e)));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
