package edu.sjsu.mithai.graphX;

import edu.sjsu.mithai.data.AvroGraphMetadata;
import edu.sjsu.mithai.spark.SparkStreamingObject;
import edu.sjsu.mithai.util.BaseTest;
import org.apache.spark.graphx.Graph;
import org.junit.Test;
import scala.Tuple2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GraphCreatorTest extends BaseTest {


    public GraphCreatorTest() throws IOException {
    }

    @Test
    public void createMetaDataGraph() throws Exception {
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

        System.out.println(localGraph);

        GraphCreator creator = new GraphCreator();

        Graph<Tuple2<String, Object>, Object> metaDataGraph = creator.createMetaDataGraph(metadata, SparkStreamingObject.sparkContext());
        System.out.println(metaDataGraph);

        GraphProcessor gp = new GraphProcessor();
//        gp.mapAttributes(metaDataGraph);
    }

//    @Test
    public void createMetaDataGraph2() throws Exception {
        AvroGraphMetadata metadata = new AvroGraphMetadata();

        metadata.setDeviceId("Test1");
        metadata.setConnectedDevices(new ArrayList<>());

        List<AvroGraphMetadata> localGraph = new ArrayList<>();

        AvroGraphMetadata p1 = new AvroGraphMetadata();
        p1.setDeviceId("P1");
        List<String> connectedDevices1 = new ArrayList<>();
        connectedDevices1.add("P2");
//        connectedDevices1.add("P3");
        p1.setConnectedDevices(connectedDevices1);
        localGraph.add(p1);

        AvroGraphMetadata p2 = new AvroGraphMetadata();
        p2.setDeviceId("P2");
        List<String> connectedDevices2 = new ArrayList<>();
//        connectedDevices2.add("P1");
        connectedDevices2.add("P3");
        p2.setConnectedDevices(connectedDevices2);
        localGraph.add(p2);

//        AvroGraphMetadata p3 = new AvroGraphMetadata();
//        p3.setDeviceId("P3");
//        List<String> connectedDevices3 = new ArrayList<>();
//        connectedDevices3.add("P2");
//        connectedDevices3.add("P1");
//        p3.setConnectedDevices(connectedDevices3);
//        localGraph.add(p3);

        metadata.setLocalGraph(localGraph);

        System.out.println(localGraph);

        GraphCreator creator = new GraphCreator();

//        Graph<String, Object> metaDataGraph = creator.createMetaDataGraph(metadata, SparkStreamingObject.sparkContext());
//        System.out.println(metaDataGraph);
    }

    @Override
    public void test() throws Exception {
        createMetaDataGraph();
    }

    public static void main(String[] args) throws IOException {
        GraphCreatorTest test = new GraphCreatorTest();
        try {
            test.test();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}