package de.b4sh.misc.log;

public class LogController {
    private static LogController ourInstance = new LogController();

    public static LogController getInstance() {
        return ourInstance;
    }

    private LogController() {

    }

    public void writeLog(){
        
    }

}
