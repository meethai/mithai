package edu.sjsu.mithai.config;

import edu.sjsu.mithai.util.StoppableRunnableTask;

import java.io.IOException;

public class VisualizationServerTask extends StoppableRunnableTask {

    @Override
    public void run() {
        try {
            Runtime.getRuntime().exec("startWebServer.sh");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
