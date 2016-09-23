package edu.sjsu.mithai.export;

import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.util.TaskManager;

public class Client {
    public static void main(String[] args) {
        Client c = new Client();
        c.run();
    }

    private void run() {
        try {
            Configuration configuration = new Configuration(getClass().getClassLoader().getResource("application.properties").getFile());
            TaskManager.getInstance().submitTask(new ExporterTask(configuration));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
