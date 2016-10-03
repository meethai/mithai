package client;

import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.config.MithaiProperties;
import edu.sjsu.mithai.data.AvroSerializationHelper;
import edu.sjsu.mithai.mqtt.MQTTPublisher;
import edu.sjsu.mithai.sensors.TemperatureSensor;
import org.apache.avro.generic.GenericData;
import org.apache.avro.generic.GenericRecord;

public class AvroClient {

    public static void main(String[] args) throws Exception {
        AvroClient c = new AvroClient();
        c.test();

    }

    private void test() throws Exception {

        Configuration configuration = new Configuration(getClass().getClassLoader().getResource("application.properties").getFile());

//        SimpleMqttReceiver receiver = new SimpleMqttReceiver(configuration);

        AvroSerializationHelper avro = new AvroSerializationHelper();
        avro.loadSchema("sensor.json");

        TemperatureSensor sensor = new TemperatureSensor("sensor-1");
        GenericRecord record = new GenericData.Record(avro.getSchema());
        record.put("id", "sensor1");

        for(int i=0;i<10000;i++) {
            record.put("value", sensor.sense());
            String serialize = avro.serialize(record);
            System.out.println(serialize);
            MQTTPublisher mqttPublisher = new MQTTPublisher(configuration.getProperty(MithaiProperties.MQTT_BROKER));
            String topic = configuration.getProperty(MithaiProperties.MQTT_TOPIC);
            mqttPublisher.sendDataToTopic(serialize, topic);

            try {
                Thread.sleep(1 * 10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

//        receiver.getClient().disconnect();
    }
}

