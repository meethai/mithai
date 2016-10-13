package edu.sjsu.mithai.mqtt;

import edu.sjsu.mithai.util.BaseTest;
import edu.sjsu.mithai.util.TaskManager;

import java.io.IOException;

/**
 * Created by kaustubh on 10/12/16.
 */
public class MQTTMetaDataRecieverTaskTest extends BaseTest{

    public MQTTMetaDataRecieverTaskTest() throws IOException {
    }

    public static void main(String[] args) throws Exception {
        new MQTTMetaDataRecieverTaskTest().test();
    }

    @Override
    public void test() throws Exception {
        TaskManager.getInstance().submitTask(new MQTTMetaDataRecieverTask(config));
        stopAfter(60);
    }
}