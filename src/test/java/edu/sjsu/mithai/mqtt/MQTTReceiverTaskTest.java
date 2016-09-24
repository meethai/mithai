package edu.sjsu.mithai.mqtt;

import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.util.TaskManager;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by kaustubh on 9/21/16.
 */
public class MQTTReceiverTaskTest {
    @Test
    public void MQTTTest() {

        try {
            Configuration config = new Configuration(getClass().getClassLoader().getResource("application.properties").getFile());
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