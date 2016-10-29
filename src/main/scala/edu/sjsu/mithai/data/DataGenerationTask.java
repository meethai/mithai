package edu.sjsu.mithai.data;

import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.config.MithaiProperties;
import edu.sjsu.mithai.mqtt.MQTTPublisher;
import edu.sjsu.mithai.mqtt.MqttService;
import edu.sjsu.mithai.sensors.IDevice;
import edu.sjsu.mithai.util.StoppableExecutableTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DataGenerationTask extends StoppableExecutableTask {

    private static final Logger logger = LoggerFactory.getLogger(DataGenerationTask.class);

    private SensorStore sensorStore;
    private Configuration configuration;
    private MQTTPublisher publisher;
    private List<SensorData> dataList;
    private SensorDataSerializationHelper avro;

    public DataGenerationTask(Configuration configuration, SensorStore sensorStore) throws IOException {
        this.configuration = configuration;
        this.sensorStore = sensorStore;
        this.publisher = MqttService.getPublisher(configuration);
        this.dataList = new ArrayList<>(sensorStore.getDevices().size());

        avro = new SensorDataSerializationHelper();

        for (IDevice device : sensorStore.getDevices()) {
            SensorData record = new SensorData();
            record.setId(device.getId());
            dataList.add(record);
        }
    }

    @Override
    public void execute() {

        int index = 0;
        for (IDevice device : sensorStore.getDevices()) {
            SensorData record = dataList.get(index++);
            record.setValue(device.sense());

            try {
                publisher.sendDataToTopic(avro.serialize(record), configuration.getProperty(MithaiProperties.MQTT_TOPIC));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        try {
            Thread.sleep(Long.parseLong(configuration.getProperty(MithaiProperties.DATA_GENERATION_INTERVAL)));
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
