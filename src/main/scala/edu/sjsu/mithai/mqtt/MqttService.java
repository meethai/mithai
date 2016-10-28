package edu.sjsu.mithai.mqtt;

import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.config.MithaiProperties;

public class MqttService {
    private static MQTTPublisher publisher;

    private MqttService() {
    }

    public static MQTTPublisher getPublisher(Configuration configuration) {

        if (publisher == null) {
            publisher = new MQTTPublisher(configuration.getProperty(MithaiProperties.MQTT_BROKER));
        }

        return publisher;
    }
}
