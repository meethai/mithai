package edu.sjsu.mithai.mqtt;

import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.data.AvroSerializationHelper;
import edu.sjsu.mithai.util.StoppableRunnableTask;
import org.apache.avro.generic.GenericRecord;
import org.apache.log4j.Logger;

import java.io.IOException;

import static edu.sjsu.mithai.config.MithaiProperties.MQTT_BROKER;
import static edu.sjsu.mithai.config.MithaiProperties.MQTT_TOPIC;

public class MQTTReceiverTask extends StoppableRunnableTask {

    private static Logger logger = Logger.getLogger(MQTTReceiverTask.class);
    private MQTTReciever reciever;
    private Configuration config;

    public MQTTReceiverTask(Configuration config) {
        this.config = config;
    }

    @Override
    public void run() {
<<<<<<< HEAD
//        logger.debug("mqtt reciever running....");
//        reciever = new MQTTReciever<GenericRecord>(config.getProperty(MQTT_BROKER), config.getProperty(MQTT_TOPIC));
//        AvroSerializationHelper av = new AvroSerializationHelper();
//        try {
//            av.loadSchema("sensor.json");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        reciever.setSerializationHelper(av);
//        reciever.start();
=======
        logger.debug("Mqtt reciever running....");
        reciever = new MQTTReciever<GenericRecord>(config.getProperty(MQTT_BROKER), config.getProperty(MQTT_TOPIC));
        AvroSerializationHelper av = new AvroSerializationHelper();
        try {
            av.loadSchema("sensor.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
        reciever.setSerializationHelper(av);
        reciever.start();
>>>>>>> 429483caa6043d975e15ff2c50f6ee2e1261a108
    }

    @Override
    public void stop() {
        logger.debug("stopping...");
        reciever.stop(true);
    }
}
