package edu.sjsu.mithai.config;

import edu.sjsu.mithai.util.BaseTest;
import edu.sjsu.mithai.util.TaskManager;
import org.junit.Test;

import java.io.IOException;

public class ConfigurationTest extends BaseTest {


    public ConfigurationTest() throws IOException {

    }

    @Test
    public void test() {
        // start monitor task
        TaskManager.getInstance().submitTask(new ConfigMonitorTask(config));
        stopAfter(60);
    }
}
