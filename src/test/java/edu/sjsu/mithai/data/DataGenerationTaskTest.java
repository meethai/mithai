package edu.sjsu.mithai.data;

import edu.sjsu.mithai.sensors.IDevice;
import edu.sjsu.mithai.util.BaseTest;

import java.io.IOException;
import java.util.Random;

public class DataGenerationTaskTest extends BaseTest {

    public DataGenerationTaskTest() throws IOException {

    }

    public static void main(String[] args) {

        try {
            DataGenerationTaskTest test = new DataGenerationTaskTest();
            test.test();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void test() throws Exception {

        SensorStore sensorStore = new SensorStore();
        sensorStore.addDevice(new IDevice() {

            Random random = new Random();

            @Override
            public double sense() {
                return random.nextDouble();
            }

            @Override
            public String getId() {
                return "sensor-1";
            }

            @Override
            public void sendData() {

            }
        });

        DataGenerationTask task = new DataGenerationTask(config, sensorStore);
        stopAfter(45);
    }
}
