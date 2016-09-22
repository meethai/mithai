package edu.sjsu.mithai.mqtt;

import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.util.StoppableRunnableTask;

import static edu.sjsu.mithai.config.MithaiProperties.MQTT_BROKER;
import static edu.sjsu.mithai.config.MithaiProperties.MQTT_TOPIC;

public class MQTTReceiverTask extends StoppableRunnableTask {

    private MQTTReciever reciever;
    private Configuration config;

    public MQTTReceiverTask(Configuration config) {
        this.config = config;
    }

    @Override
    public void run() {
        reciever = new MQTTReciever(config.getProperty(MQTT_BROKER), config.getProperty(MQTT_TOPIC));
    }

    @Override
    public void execute() {
        // Do nothing
    }

    @Override
    public void stop() {
        reciever.ssc().stop(true, true);
    }
}
