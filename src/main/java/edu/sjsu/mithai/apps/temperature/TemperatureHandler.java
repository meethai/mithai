package edu.sjsu.mithai.apps.temperature;

import edu.sjsu.mithai.util.Ihandler;

/**
 * Created by sushained on 11/30/16.
 */
public class TemperatureHandler implements Ihandler {

    @Override
    public void handle(String functionName, String msg) {
        if(functionName.equals("average")) {
            System.out.println("Average is =>" + msg);
        }
    }
}
