package edu.sjsu.mithai.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import edu.sjsu.mithai.config.Configuration;
import edu.sjsu.mithai.config.MithaiProperties;
import edu.sjsu.mithai.mqtt.MQTTPublisher;
import edu.sjsu.mithai.util.StoppableExecutableTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MetadataGenerationTask extends StoppableExecutableTask {
    private static final String TOPIC = "metadata";
    private static final int RESEND_COUNT = 3;
    private final Gson gson;
    private Configuration configuration;
    private MQTTPublisher publisher;
    private int sendCount;
    private AvroSerializationHelper avro;

    public MetadataGenerationTask(Configuration configuration) {
        this.configuration = configuration;
        this.publisher = new MQTTPublisher(configuration.getProperty(MithaiProperties.MQTT_BROKER));
        this.gson = new Gson();
        this.avro = new AvroSerializationHelper();
        try {
            avro.loadSchema("metadata.json");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute() {

        if (++sendCount >= RESEND_COUNT) {
            stop();
        }

        AvroGraphMetadata metadata = new AvroGraphMetadata();
        metadata.setDeviceId(configuration.getProperty(MithaiProperties.ID));
        String connectedDeviceIds = configuration.getProperty(MithaiProperties.CONNECTED_DEVICE_IDS);

        String localGraph = configuration.getProperty(MithaiProperties.LOCAL_GRAPH);

        JsonParser parser = new JsonParser();
        JsonElement graph = parser.parse(localGraph);

        if (graph.isJsonArray()) {

            List<GraphMetadata> metadataList = new ArrayList<>();
            graph.getAsJsonArray().forEach(t -> {
                t.getAsJsonObject();
                System.out.println(t.getAsJsonObject());
                t.getAsJsonObject();
            });
        }

        List<CharSequence> connectedDevices = new ArrayList<>();

        if (connectedDeviceIds != null && !connectedDeviceIds.trim().isEmpty()) {
            String[] ids = connectedDeviceIds.split(",");

            if (ids != null && ids.length > 0) {
                for (String id: ids) {
                    connectedDevices.add(id.trim());
                }
            }
        }

        metadata.setConnectedDevices(connectedDevices);
        metadata.setLocalGraph(new ArrayList<>());

        try {
            publisher.sendDataToTopic(avro.serialize(metadata), "metadata");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
