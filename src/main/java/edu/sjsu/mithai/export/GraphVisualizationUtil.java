package edu.sjsu.mithai.export;

import com.google.gson.Gson;
import edu.sjsu.mithai.data.AvroGraphMetadata;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

class NodeTuple {
    private String id;
    private int group;

    public NodeTuple(String id, int group) {
        this.id = id;
        this.group = group;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getGroup() {
        return group;
    }

    public void setGroup(int group) {
        this.group = group;
    }
}

class LinkTuple {
    private String source;
    private String target;
    private int value;

    public LinkTuple(String source, String target, int value) {
        this.source = source;
        this.target = target;
        this.value = value;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}

public class GraphVisualizationUtil {

    private final static int NODE_DISTANCE = 15;
    private final static int SENSOR_DEVICE_DISTANCE = 10;
    private final static int SENSOR_DISTANCE = 1;

    public static String parseGraphTuple(AvroGraphMetadata metadata) {
        String output = "";

        List<NodeTuple> nodes = new LinkedList<>();
        List<LinkTuple> links = new LinkedList<>();

        String deviceId = metadata.getDeviceId();
        int groupId = 0;
        NodeTuple tuple = new NodeTuple(deviceId, groupId++);
        nodes.add(tuple);

        for (String connectedDevice : metadata.getConnectedDevices()) {
            NodeTuple tuple1 = new NodeTuple(connectedDevice, groupId++);
            nodes.add(tuple1);
            LinkTuple link = new LinkTuple(deviceId, connectedDevice, NODE_DISTANCE);
            links.add(link);
        }

        for (AvroGraphMetadata localMetadata: metadata.getLocalGraph()) {
            NodeTuple tuple2 = new NodeTuple(localMetadata.getDeviceId(), tuple.getGroup());
            nodes.add(tuple2);

            links.add(new LinkTuple(tuple.getId(), tuple2.getId(), SENSOR_DEVICE_DISTANCE));

            for (String connectedDevice: localMetadata.getConnectedDevices()) {
                LinkTuple link2 = new LinkTuple(localMetadata.getDeviceId(), connectedDevice, SENSOR_DISTANCE);
                links.add(link2);
            }
        }

        Map<String, Object> visualization = new HashMap<>();
        visualization.put("nodes", nodes);
        visualization.put("links", links);

//        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Gson gson = new Gson();
        output = gson.toJson(visualization);

        return output;
    }

}
