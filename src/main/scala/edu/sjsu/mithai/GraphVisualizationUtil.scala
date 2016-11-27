package edu.sjsu.mithai

import java.util.{HashMap, LinkedList}

import com.google.gson.Gson
import org.apache.spark.graphx.{Graph, PartitionID}

class GraphVisualizationUtil {
  private val NODE_DISTANCE: Int = 30
  private val SENSOR_DEVICE_DISTANCE: Int = 10
  private val SENSOR_DISTANCE: Int = 1

  def prepareGraphVisualization(graph:Graph[(String, Double), PartitionID]) = {

    var output = ""
    val nodes = new LinkedList[(String, Double, Int)]()
    val links = new LinkedList[(String, String, Int)]()

    val map = new HashMap[Long, String]()
    graph.vertices.collect().foreach(v => {
      nodes.add(v._2._1, v._2._2, 1)
      map.put(v._1, v._2._1)
    })
    graph.edges.collect().foreach(e=> {
      links.add(map.get(e.srcId),map.get(e.dstId), e.attr)
    })
    val visualization = new HashMap[String, Any]()
    visualization.put("nodes", nodes.toArray)
    visualization.put("links", links.toArray)
    val gson = new Gson()
    output = gson.toJson(visualization)
    print(output)
    output
  }
}