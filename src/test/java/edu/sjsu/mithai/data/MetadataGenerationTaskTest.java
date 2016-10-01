package edu.sjsu.mithai.data;

import edu.sjsu.mithai.mqtt.SimpleMqttReceiver;
import edu.sjsu.mithai.util.BaseTest;
import edu.sjsu.mithai.util.TaskManager;

import java.io.IOException;

public class MetadataGenerationTaskTest extends BaseTest {

    public MetadataGenerationTaskTest() throws IOException {}

    public static void main(String[] args) {

        MetadataGenerationTaskTest test = null;
        try {
            test = new MetadataGenerationTaskTest();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
                test.test();
            } catch (Exception e) {
                e.printStackTrace();
            }

    }
    @Override
    public void test() throws Exception {
        MetadataGenerationTask task = new MetadataGenerationTask(config);
        SimpleMqttReceiver receiver = new SimpleMqttReceiver(config);

        TaskManager.getInstance().submitTask(task);
        stopAfter(45);
    }
}
