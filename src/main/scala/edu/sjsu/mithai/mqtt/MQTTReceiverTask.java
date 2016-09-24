package edu.sjsu.mithai.mqtt;

import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.util.StoppableRunnableTask;
import org.apache.log4j.Logger;

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
        logger.debug("mqtt reciever running....");
        reciever = new MQTTReciever(config.getProperty(MQTT_BROKER), config.getProperty(MQTT_TOPIC));
    }

    @Override
    public void execute() {
        // Do nothing
    }

    @Override
    public void stop() {
        logger.debug("stopping...");
        reciever.ssc().stop(true, true);
    }
}
