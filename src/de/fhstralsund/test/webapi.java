package de.fhstralsund.test;

import de.fhstralsund.project.core.api.web;

import java.net.URL;
import java.net.URLEncoder;

public class webapi {

    public static void main(String args[]){

        URL serverURL = null;
        String messageBody = null;

        try{
            serverURL = new URL("http://games.b4sh.de/OpenTransport-Server");
            messageBody =   "task=" + URLEncoder.encode("nasen", "UTF-8") + "&" +
                            "amount=" + URLEncoder.encode("1000", "UTF-8");
        }
        catch (Exception e){
            e.printStackTrace();
        }

        web webApi = new web(serverURL,80);
        webApi.sendPostMessage(messageBody);
    }

}
