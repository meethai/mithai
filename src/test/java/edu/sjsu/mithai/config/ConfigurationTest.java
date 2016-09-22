package edu.sjsu.mithai.config;

import edu.sjsu.mithai.util.TaskManager;

import java.io.IOException;

public class ConfigurationTest {

    public static void main(String[] args) {
        try {
            Configuration c = new Configuration("/Users/sjinturkar/workspace/spstest/sc-web-test/src/main/mithai/src/main/resources/application.properties");
            System.out.println(c.getProperties());

            // start monitor task
            TaskManager.getInstance().submitTask(new ConfigMonitorTask(c));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}