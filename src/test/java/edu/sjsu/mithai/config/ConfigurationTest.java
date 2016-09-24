package edu.sjsu.mithai.config;

import edu.sjsu.mithai.util.TaskManager;

import java.io.IOException;

public class ConfigurationTest {

    public static void main(String[] args) {
        ConfigurationTest test = new ConfigurationTest();
        test.test();
    }

    private void test() {

        try {
            Configuration c = new Configuration(getClass().getClassLoader().getResource("application.properties").getFile());
            System.out.println(c.getProperties());

            // start monitor task
            TaskManager.getInstance().submitTask(new ConfigMonitorTask(c));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}