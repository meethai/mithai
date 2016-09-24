package edu.sjsu.mithai.mqtt;

import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.config.MithaiProperties;
import edu.sjsu.mithai.data.AbstractData;
import edu.sjsu.mithai.data.TemperatureData;
import org.junit.Test;

import java.io.IOException;

/**
 * Created by kaustubh on 9/24/16.
 */
public class MQTTPublisherTest {

    @Test
    public void test(String[] args) {

        Configuration config = null;
        try {
            config = new Configuration("/Users/kaustubh/295B/mithai/src/main/resources/application.properties");
        } catch (IOException e) {
            e.printStackTrace();
        }

        MQTTPublisher mqttPublisher = new MQTTPublisher(config.getProperty(MithaiProperties.MQTT_BROKER));
        String topic = config.getProperty(MithaiProperties.MQTT_TOPIC);
        AbstractData abstractData = new TemperatureData("test data", "test_222", "test_kelvin");
        mqttPublisher.sendDataToTopic(abstractData, topic);
    }


}