package edu.sjsu.mithai.mqtt;

import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.config.MithaiProperties;
import edu.sjsu.mithai.data.AvroSerializationHelper;
import org.eclipse.paho.client.mqttv3.*;

import java.io.IOException;
import java.net.URISyntaxException;

public class SimpleMqttReceiver implements MqttCallback {

    private MqttClient client;
    private Configuration configuration;
    private AvroSerializationHelper avro;
    private AvroSerializationHelper metadataAvro;

    public SimpleMqttReceiver(Configuration configuration) throws MqttException, IOException, URISyntaxException {
        this.configuration = configuration;
        avro = new AvroSerializationHelper();
        avro.loadSchema("sensor.json");
        metadataAvro = new AvroSerializationHelper();
        metadataAvro.loadSchema("metadata.json");
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
            System.out.println("Metadata: " + metadataAvro.deserialize(message.toString()));
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
