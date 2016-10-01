package edu.sjsu.mithai.data;

import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.config.MithaiProperties;
import edu.sjsu.mithai.mqtt.MQTTPublisher;
import edu.sjsu.mithai.util.StoppableExecutableTask;

import java.util.ArrayList;
import java.util.List;

public class MetadataGenerationTask extends StoppableExecutableTask {

    private Configuration configuration;

    public MetadataGenerationTask(Configuration configuration) {
        this.configuration = configuration;
    }

    @Override
    public void execute() {
        System.out.println("Executing this");
        GraphMetadata metadata = new GraphMetadata(configuration.getProperty(MithaiProperties.ID));
        String connectedDeviceIds = configuration.getProperty(MithaiProperties.CONNECTED_DEVICE_IDS);

        List<String> connectedDevices = new ArrayList<>();

        if (connectedDeviceIds != null && !connectedDeviceIds.trim().isEmpty()) {
            String[] ids = connectedDeviceIds.split(",");

            if (ids != null && ids.length > 0) {
                for (String id: ids) {
                    connectedDevices.add(id.trim());
                }
            }
        }

        metadata.setConnectedDevices(connectedDevices);

        System.out.println(metadata);

        MQTTPublisher publisher = new MQTTPublisher(configuration.getProperty(MithaiProperties.MQTT_BROKER));
        publisher.sendDataToTopic(metadata.toString(), "metadata");

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
