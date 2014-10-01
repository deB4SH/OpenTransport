package de.b4sh.core.resource;

import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ResourceStorage {

    private ArrayList<Texture> textureStorage;

    public ResourceStorage(String fileDir){
        load(fileDir);
        System.out.println("Textures Loaded");
    }

    private void load(String fileDir){

        //scan directory for png images
        try {
            String canonicalDir = new File( "." ).getCanonicalPath();
            File dir = new File(canonicalDir + "/" + fileDir);
            String[] content = dir.list();

            //check if there are images in and load them
            for(String e: content){
                if(e.toLowerCase().contains(".png"))
                    textureStorage.add(TextureLoader.getTexture("PNG", ResourceLoader.getResourceAsStream(dir.getCanonicalPath() + e)));
            }

        } catch (IOException e) {
            e.printStackTrace();
            //TODO: add gamelog
        }
        catch (Exception e){
            e.printStackTrace();
            //TODO: add gamelog
        }
    }

}
