package de.fhstralsund.project.core.api;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class web {

    private URL serverURL;
    private int port;

    private String response;
    private boolean responseAction;

    public web(URL serverURL, int port) {
        this.serverURL = serverURL;
        this.port = port;
    }

    public void sendPostMessage(String messageBody){
        try{
            HttpURLConnection connection = (HttpURLConnection) this.serverURL.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setUseCaches(false);
            connection.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-Length", String.valueOf(messageBody.length()));

            OutputStreamWriter osw = new OutputStreamWriter(connection.getOutputStream());
            osw.write(messageBody);
            osw.flush();

            BufferedReader bfr = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            for(String line; (line = bfr.readLine()) != null;){
                System.out.println(line);
            }

            osw.close();
            bfr.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }


    }
}
