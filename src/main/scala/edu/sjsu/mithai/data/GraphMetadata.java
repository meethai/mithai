package edu.sjsu.mithai.data;

import java.util.List;

public class GraphMetadata {
    private String deviceId;
    private List<String> connectedDevices;
    private List<GraphMetadata> localGraph;

    public GraphMetadata(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public List<String> getConnectedDevices() {
        return connectedDevices;
    }

    public void setConnectedDevices(List<String> connectedDevices) {
        this.connectedDevices = connectedDevices;
    }

    public List<GraphMetadata> getLocalGraph() {
        return localGraph;
    }

    public void setLocalGraph(List<GraphMetadata> localGraph) {
        this.localGraph = localGraph;
    }

    @Override
    public String toString() {
        return "GraphMetadata{" +
                "deviceId='" + deviceId + '\'' +
                ", connectedDevices=" + connectedDevices +
                ", localGraph=" + localGraph +
                '}';
    }
}
