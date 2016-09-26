package edu.sjsu.mithai.util;

import edu.sjsu.mithai.config.Configuration;

import java.io.IOException;

/**
 * Created by sushained on 9/24/16.
 */
public abstract class BaseTest {

    protected Configuration config;

    public BaseTest() throws IOException {
        loadConfig();
    }

    public abstract void test();

    public void loadConfig() throws IOException {
        config = new Configuration(getClass().getClassLoader().getResource("application.properties").getFile());
    }

    public void stopAfter(int seconds) {

        try {
            Thread.sleep(seconds * 1000);
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
