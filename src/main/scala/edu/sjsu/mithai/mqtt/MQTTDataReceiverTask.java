package edu.sjsu.mithai.mqtt;

import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.data.AvroSerializationHelper;
import edu.sjsu.mithai.util.StoppableRunnableTask;
import org.apache.avro.generic.GenericRecord;
import org.apache.log4j.Logger;
import scala.reflect.ClassTag$;

import java.io.IOException;
import java.net.URISyntaxException;

import static edu.sjsu.mithai.config.MithaiProperties.MQTT_BROKER;
import static edu.sjsu.mithai.config.MithaiProperties.MQTT_TOPIC;

public class MQTTDataReceiverTask extends StoppableRunnableTask {

    private static Logger logger = Logger.getLogger(MQTTDataReceiverTask.class);
    private MQTTReciever dataReciever;
    private Configuration config;

    public MQTTDataReceiverTask(Configuration config) {
        this.config = config;
    }

    @Override
    public void run() {
        logger.debug("Mqtt dataReciever running....");
        dataReciever = new MQTTReciever<GenericRecord>(config.getProperty(MQTT_BROKER), config.getProperty(MQTT_TOPIC), ClassTag$.MODULE$.apply(GenericRecord.class));
        AvroSerializationHelper av = new AvroSerializationHelper();
        try {
            av.loadSchema("sensor.json");
        } catch (IOException e) {
            e.printStackTrace();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        dataReciever.setSerializationHelper(av);
        dataReciever.start();
    }

    @Override
    public void stop() {
        logger.debug("stopping...");
        dataReciever.stop(true);
    }
}
