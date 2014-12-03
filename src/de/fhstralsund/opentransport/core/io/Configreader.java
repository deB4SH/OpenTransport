package de.fhstralsund.opentransport.core.io;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;


public class Configreader {

    public Configreader() {
    }

    public HashMap<String, Integer> loadConfig(){

        HashMap<String, Integer> returnmap = new HashMap<String, Integer>();

        JSONParser parser = new JSONParser();

        JSONArray a = null;
        try {
            a = (JSONArray) parser.parse(new FileReader("res"+ File.separator+"config.json"));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (Object o : a) {
            JSONObject cofig = (JSONObject) o;
            returnmap.put("size", Integer.parseInt((String)cofig.get("size")));
            returnmap.put("width", Integer.parseInt((String)cofig.get("width")));
            returnmap.put("height", Integer.parseInt((String)cofig.get("height")));
            returnmap.put("tilewidth", Integer.parseInt((String)cofig.get("tilewidth")));
            returnmap.put("tileheight", Integer.parseInt((String)cofig.get("tileheight")));
        }

        return returnmap;
    }

    public void saveConfig(HashMap<String,String> config,String dir, String filename){

        Collection<String> e = config.values();

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("derp","hep");


    }
}
