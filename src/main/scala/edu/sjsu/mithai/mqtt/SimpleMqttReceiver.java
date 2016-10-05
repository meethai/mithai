package edu.sjsu.mithai.mqtt;

import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.config.MithaiProperties;
import edu.sjsu.mithai.data.AvroSerializationHelper;
import edu.sjsu.mithai.data.GenericSerializationHelper;
import edu.sjsu.mithai.data.GraphMetadata;
import org.eclipse.paho.client.mqttv3.*;

import java.io.IOException;

public class SimpleMqttReceiver implements MqttCallback {

    private MqttClient client;
    private Configuration configuration;
    private AvroSerializationHelper avro;

    public SimpleMqttReceiver(Configuration configuration) throws MqttException, IOException {
        this.configuration = configuration;
        avro = new AvroSerializationHelper();
        avro.loadSchema("sensor.json");
        init();
    }

    private void init() throws MqttException {
        System.out.println("Starting client...");
        client = new MqttClient(configuration.getProperty(MithaiProperties.MQTT_BROKER), "SimpleClient");
        client.setCallback(this);
        client.connect();
        client.subscribe(configuration.getProperty(MithaiProperties.MQTT_TOPIC));
        client.subscribe("metadata");
    }

    @Override
    public void connectionLost(Throwable cause) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage message) throws Exception {
        System.out.println();
        System.out.println("<--------------------------------->");
        System.out.println("Message received: ");
        System.out.println("Topic:" + topic);
        System.out.println("Message:" + message);

        if (topic.equals("metadata")) {
            GenericSerializationHelper helper = new GenericSerializationHelper(GraphMetadata.class);
            Object deserialize = helper.deserialize(message.toString());
            System.out.println("Metadata:" + (GraphMetadata) deserialize);
        } else {
            System.out.println("Data:" + avro.deserialize(message.toString()));
        }
        System.out.println("<--------------------------------->");
        System.out.println();
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken token) {

    }

    public MqttClient getClient() {
        return client;
    }
}
