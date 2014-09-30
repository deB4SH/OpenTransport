package de.b4sh.core.io;

import org.newdawn.slick.opengl.Texture;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TextureStorage {

    private ArrayList<Texture> textureStorage;

    public TextureStorage(String fileDir){
        load(fileDir);
        System.out.println("Textures Loaded");
    }

    private void load(String fileDir){

        //scan directory for png images
        try {
            String canonicalDir = new File( "." ).getCanonicalPath();
            File dir = new File(canonicalDir + "/" + fileDir);
            String[] content = dir.list();

            dir = null; canonicalDir = null;
            ArrayList<String> imageFiles = new ArrayList<String>();
            //check if there are images in
            for(String e: content){
                if(e.toLowerCase().contains(".png"))
                    imageFiles.add(e);
            }

            
            int a = 0;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
