package edu.sjsu.mithai.export;

import edu.sjsu.mithai.data.AvroGraphMetadata;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class GraphVisualizationUtilTest {
    @Test
    public void parseGraphTuple() throws Exception {

        AvroGraphMetadata metadata = new AvroGraphMetadata();

        metadata.setDeviceId("Test1");
        metadata.setConnectedDevices(new ArrayList<>());

        List<AvroGraphMetadata> localGraph = new ArrayList<>();

        AvroGraphMetadata p1 = new AvroGraphMetadata();
        p1.setDeviceId("P1");
        List<String> connectedDevices1 = new ArrayList<>();
        connectedDevices1.add("P2");
        connectedDevices1.add("P3");
        p1.setConnectedDevices(connectedDevices1);
        localGraph.add(p1);

        AvroGraphMetadata p2 = new AvroGraphMetadata();
        p2.setDeviceId("P2");
        List<String> connectedDevices2 = new ArrayList<>();
        connectedDevices2.add("P1");
        connectedDevices2.add("P3");
        p2.setConnectedDevices(connectedDevices2);
        localGraph.add(p2);

        AvroGraphMetadata p3 = new AvroGraphMetadata();
        p3.setDeviceId("P3");
        List<String> connectedDevices3 = new ArrayList<>();
        connectedDevices3.add("P2");
        connectedDevices3.add("P1");
        p3.setConnectedDevices(connectedDevices3);
        localGraph.add(p3);

        metadata.setLocalGraph(localGraph);

    }

}