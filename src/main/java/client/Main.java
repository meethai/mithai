package client;

import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.mqtt.MQTTReceiverTask;
import edu.sjsu.mithai.util.TaskManager;

import java.io.IOException;

/**
 * Created by kaustubh on 9/16/16.
 */
public class Main {
    public static void main(String[] args) {
        System.out.println("Project Mithai...");

        try {
            Configuration config = new Configuration(Main.class.getClassLoader().getResource("application.properties").getFile());
            TaskManager.getInstance().submitTask(new MQTTReceiverTask(config));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(15 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            TaskManager.getInstance().stopAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
