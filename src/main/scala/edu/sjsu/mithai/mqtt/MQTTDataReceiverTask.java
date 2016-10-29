package edu.sjsu.mithai.mqtt;

import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.data.SensorData;
import edu.sjsu.mithai.data.SensorDataSerializationHelper;
import edu.sjsu.mithai.util.StoppableRunnableTask;
import org.apache.avro.generic.GenericRecord;
import org.apache.log4j.Logger;
import scala.reflect.ClassTag$;

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
        dataReciever = new MQTTReciever<SensorData>(config.getProperty(MQTT_BROKER), config.getProperty(MQTT_TOPIC), ClassTag$.MODULE$.apply(SensorData.class));
        SensorDataSerializationHelper avro = new SensorDataSerializationHelper();
        dataReciever.setSerializationHelper(avro);
        dataReciever.start();
    }

    @Override
    public void stop() {
        logger.debug("stopping...");

        if (dataReciever != null) {
            dataReciever.stop(false);
        }
    }
}
