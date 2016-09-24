package edu.sjsu.mithai.mqtt;

import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.config.MithaiProperties;
import org.eclipse.paho.client.mqttv3.*;

public class SimpleMqttReceiver implements MqttCallback {

    private MqttClient client;
    private Configuration configuration;

    public SimpleMqttReceiver(Configuration configuration) throws MqttException {
        this.configuration = configuration;
        init();
    }

    private void init() throws MqttException {
        System.out.println("Starting client...");
        client = new MqttClient(configuration.getProperty(MithaiProperties.MQTT_BROKER), "SimpleClient");
        client.setCallback(this);
        client.connect();
        client.subscribe(configuration.getProperty(MithaiProperties.MQTT_TOPIC));
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println("Message received: ");
        System.out.println("Topic:" + topic);
        System.out.println("Message:" + message);
        System.out.println();
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }
}
