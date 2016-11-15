//package edu.sjsu.mithai.mqtt;
//
//import edu.sjsu.mithai.config.MithaiProperties;
//import edu.sjsu.mithai.data.AbstractData;
//import edu.sjsu.mithai.data.TemperatureData;
//import edu.sjsu.mithai.util.BaseTest;
//import org.junit.Test;
//
//import java.io.IOException;
//
///**
// * Created by kaustubh on 9/24/16.
// */
//public class MQTTPublisherTest extends BaseTest {
//
//    public MQTTPublisherTest() throws IOException {
//
//    }
//
//    @Test
//    public void test() {
//        MQTTPublisher mqttPublisher = new MQTTPublisher(config.getProperty(MithaiProperties.MQTT_BROKER));
//        String topic = config.getProperty(MithaiProperties.MQTT_TOPIC);
//        AbstractData abstractData = new TemperatureData("test data", "test_222", "test_kelvin");
//        mqttPublisher.sendDataToTopic(abstractData, topic);
//    }
//}