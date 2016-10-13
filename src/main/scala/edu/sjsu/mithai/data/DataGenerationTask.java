package edu.sjsu.mithai.data;

import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.config.MithaiProperties;
import edu.sjsu.mithai.mqtt.MQTTPublisher;
import edu.sjsu.mithai.sensors.IDevice;
import edu.sjsu.mithai.util.StoppableExecutableTask;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class DataGenerationTask extends StoppableExecutableTask {

    private static final Logger logger = LoggerFactory.getLogger(DataGenerationTask.class);

    private SensorStore sensorStore;
    private Configuration configuration;
    private MQTTPublisher publisher;
    private List<GenericRecord> dataList;
    private AvroSerializationHelper avro;

    public DataGenerationTask(Configuration configuration, SensorStore sensorStore) throws IOException {
        this.configuration = configuration;
        this.sensorStore = sensorStore;
        this.publisher = new MQTTPublisher(configuration.getProperty(MithaiProperties.MQTT_BROKER));
        this.dataList = new ArrayList<>(sensorStore.getDevices().size());

        avro = new AvroSerializationHelper();
        try {
            avro.loadSchema("sensor.json");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        for (IDevice device : sensorStore.getDevices()) {
            GenericRecord record = new GenericData.Record(avro.getSchema());
            record.put("id", device.getId());
            dataList.add(record);
        }
    }

    @Override
    public void execute() {

        int index = 0;
        for (IDevice device : sensorStore.getDevices()) {
            GenericRecord record = dataList.get(index++);
            record.put("value", device.sense());
            try {
                publisher.sendDataToTopic(avro.serialize(record), configuration.getProperty(MithaiProperties.MQTT_TOPIC));
            } catch (Exception e) {
               logger.error(e.getMessage(), e);
            }
        }

        try {
            Thread.sleep(Long.parseLong(configuration.getProperty(MithaiProperties.DATA_GENERATION_INTERVAL)));
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
