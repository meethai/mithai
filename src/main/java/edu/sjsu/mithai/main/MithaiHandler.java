package edu.sjsu.mithai.main;

import edu.sjsu.mithai.util.Ihandler;

public class MithaiHandler implements Ihandler {

    @Override
    public void handle(String functionName, String msg) {

        System.out.println("===============================");
        switch (functionName) {
            case "min":
                System.out.println("Min message: " + msg);
                break;
            case "max":
                System.out.println("Max message: " + msg);
                break;
            case "average":
                System.out.println("Average message: " + msg);
                break;
            case "ShortestPath":
                System.out.println("Shortest Path message: " + msg);
                break;
            default:
                System.out.println("Handler not present!");
        }
    }
}
