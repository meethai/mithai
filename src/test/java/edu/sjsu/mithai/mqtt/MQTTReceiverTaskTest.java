package edu.sjsu.mithai.mqtt;

import edu.sjsu.mithai.util.BaseTest;
import edu.sjsu.mithai.util.TaskManager;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by kaustubh on 9/21/16.
 */
public class MQTTReceiverTaskTest extends BaseTest {

    public static void main(String[] args) {
        try {
            MQTTReceiverTaskTest mr = new MQTTReceiverTaskTest();
            mr.test();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public MQTTReceiverTaskTest() throws IOException {

//        try {
//            Thread.sleep(45 * 1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }


    }

    @Test
    public void test() {
        TaskManager.getInstance().submitTask(new MQTTReceiverTask(config));
        stopAfter(45);
    }
}