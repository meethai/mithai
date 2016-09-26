package edu.sjsu.mithai.data;

import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.config.MithaiProperties;
import edu.sjsu.mithai.sensors.IDevice;
import edu.sjsu.mithai.util.StoppableExecutableTask;

public class DataGenerationTask extends StoppableExecutableTask {

    private SensorStore sensorStore;
    private Configuration configuration;

    public DataGenerationTask(Configuration configuration, SensorStore sensorStore) {
        this.configuration = configuration;
        this.sensorStore = sensorStore;
    }

    @Override
    public void execute() {

        for (IDevice device : sensorStore.getDevices()) {
            // TODO send this data to MQTT using avro
            System.out.println(device.getId() + "=> " + device.sense());
        }

        try {
            Thread.sleep(Long.parseLong(configuration.getProperty(MithaiProperties.DATA_GENERATION_INTERVAL)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
