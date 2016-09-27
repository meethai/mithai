package edu.sjsu.mithai.mqtt;

import edu.sjsu.mithai.util.BaseTest;
import edu.sjsu.mithai.util.TaskManager;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by kaustubh on 9/21/16.
 */
public class MQTTReceiverTaskTest extends BaseTest {


<<<<<<< HEAD
    public MQTTReceiverTaskTest() throws IOException {
=======
        try {
            Thread.sleep(45 * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
>>>>>>> 6066aeb25467c96dc14810e3f7071401131937aa

    }

    @Test
    public void test() {
        TaskManager.getInstance().submitTask(new MQTTReceiverTask(config));
        stopAfter(15);
    }
}