package edu.sjsu.mithai.mqtt;

import edu.sjsu.mithai.util.BaseTest;
import edu.sjsu.mithai.util.TaskManager;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by kaustubh on 9/21/16.
 */
public class MQTTDataReceiverTaskTest extends BaseTest {

    public MQTTDataReceiverTaskTest() throws IOException {
    }

    public static void main(String[] args) throws IOException {

            MQTTDataReceiverTaskTest mr = new MQTTDataReceiverTaskTest();
            mr.test();

    }

    @Test
    public void test() {
        TaskManager.getInstance().submitTask(new MQTTDataReceiverTask(config));
        stopAfter(35);
    }
}