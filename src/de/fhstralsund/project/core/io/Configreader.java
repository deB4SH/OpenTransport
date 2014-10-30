package de.fhstralsund.project.core.io;

import org.json.simple.JSONObject;

import java.util.Collection;
import java.util.HashMap;


public class Configreader {

    public Configreader() {
    }

    public HashMap<String,String> loadConfig(String dir, String filename){

        return new HashMap<String, String>();
    }

    public void saveConfig(HashMap<String,String> config,String dir, String filename){

        Collection<String> e = config.values();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("derp","hep");


    }
}
