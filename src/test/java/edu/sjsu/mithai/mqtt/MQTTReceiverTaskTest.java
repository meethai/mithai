package edu.sjsu.mithai.mqtt;

import edu.sjsu.mithai.util.BaseTest;
import edu.sjsu.mithai.util.TaskManager;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by kaustubh on 9/21/16.
 */
public class MQTTReceiverTaskTest extends BaseTest {

    public MQTTReceiverTaskTest() throws IOException {

    }

    @Test
    public void test() {
        TaskManager.getInstance().submitTask(new MQTTReceiverTask(config));
        stopAfter(45);
    }
}