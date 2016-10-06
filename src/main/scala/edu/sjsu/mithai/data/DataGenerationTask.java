package edu.sjsu.mithai.data;

import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.config.MithaiProperties;
import edu.sjsu.mithai.mqtt.MQTTPublisher;
import edu.sjsu.mithai.sensors.IDevice;
import edu.sjsu.mithai.util.StoppableExecutableTask;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;

import java.io.IOException;

public class DataGenerationTask extends StoppableExecutableTask {

    private SensorStore sensorStore;
    private Configuration configuration;
    private AvroSerializationHelper avro;
    private MQTTPublisher mqttPublisher;
    private String topic;

    public DataGenerationTask(Configuration configuration, SensorStore sensorStore) throws IOException {

        this.configuration = configuration;
        this.sensorStore = sensorStore;
        avro = new AvroSerializationHelper();
        avro.loadSchema("sensor.json");
        mqttPublisher = new MQTTPublisher(configuration.getProperty(MithaiProperties.MQTT_BROKER));
        topic = configuration.getProperty(MithaiProperties.MQTT_TOPIC);

    }

    @Override
    public void execute() {

        for (IDevice device : sensorStore.getDevices()) {

            System.out.println(device.getId() + "=> " + device.sense());
            GenericRecord record = new GenericData.Record(avro.getSchema());
            record.put("id", device.getId());
            record.put("value", device.sense());
            try {
                mqttPublisher.sendDataToTopic(avro.serialize(record), topic);
                Thread.sleep(1 * 1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }


        try {
            Thread.sleep(Long.parseLong(configuration.getProperty(MithaiProperties.DATA_GENERATION_INTERVAL)));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
