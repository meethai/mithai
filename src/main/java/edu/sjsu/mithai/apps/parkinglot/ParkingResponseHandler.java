package edu.sjsu.mithai.apps.parkinglot;

import edu.sjsu.mithai.util.Ihandler;

public class ParkingResponseHandler implements Ihandler {
    @Override
    public void handle(String functionName, String msg) {
        System.out.println(functionName + "=>" + msg);
    }
}
