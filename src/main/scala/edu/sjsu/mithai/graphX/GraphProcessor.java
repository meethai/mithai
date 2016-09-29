//package edu.sjsu.mithai.graphX;
//
//import org.apache.spark.graphx.Graph;
//
//import java.util.ArrayList;
//
///**
// * Created by Madhura on 9/26/16.
// */
//
//public class GraphProcessor {
//
//    public static void main(String[] args) {
//
//        //incoming arrayliist (call from kaustubh`s func)
//        ArrayList<String> vertex = new ArrayList<>();
//        vertex.add("{\"sensor1\",\"100\"}");
//        vertex.add("{\"sensor2\",\"200\"}");
//        vertex.add("{\"sensor3\",\"300\"}");
//        vertex.add("{\"sensor4\",\"400\"}");
//        vertex.add("{\"sensor5\",\"500\"}");
//        vertex.add("{\"sensor6\",\"600\"}");
//
//        //generated graph
//        Graph<String, Object> graph = new GraphCreator().createGraph(vertex);
//
//        System.out.println("Graph Object ------->>>>>"+graph);
//
//        //TODO: Add graph processing functions
//        System.out.println(graph.aggregateMessages$default$3());
//        System.out.println(graph.vertices().collect());
//
//    }
//
//
//
//}
