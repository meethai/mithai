package edu.sjsu.mithai.graphX

import java.util

import edu.sjsu.mithai.GraphVisualizationUtil
import edu.sjsu.mithai.data.AvroGraphMetadata
import edu.sjsu.mithai.export.HttpExportMessage
import edu.sjsu.mithai.spark.Store
import org.apache.log4j.Logger
import org.apache.spark.SparkContext
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD

import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer

class GraphCreator {

  val logger:Logger = Logger.getLogger(GraphCreator.this.getClass)
  val visualizationUtil = new GraphVisualizationUtil

  def createMetaDataGraph(existingGraph:Graph[(String, Double), PartitionID], agm:AvroGraphMetadata , sc:SparkContext):Graph[(String, Double),PartitionID]={

    var edges = new ListBuffer[Edge[PartitionID]]
    var vert = new util.HashSet[(VertexId,(String, Double))]()
    agm.getLocalGraph.toList.foreach(p=>{
      p.getConnectedDevices.toList.foreach(x=>{
        edges+= Edge(p.getDeviceId.hashCode,x.hashCode)
        vert.add((x.hashCode,(x, 0)))
      })
      vert.add(p.getDeviceId.hashCode,(p.getDeviceId, 0))
    })

    val vertexRD: RDD[(VertexId, (String, Double))] = sc.parallelize(vert.toList)
    val edgeRDD: RDD[Edge[PartitionID]] = sc.parallelize(edges)
    val graph: Graph[(String, Double), PartitionID] = Graph(vertexRD, edgeRDD)

    logger.debug("Graph in scala:")
    graph.vertices.collect().foreach(x=>logger.debug(x+"<--metaVertex"))
    graph.edges.collect().foreach(x=>logger.debug(x+"<--metaEdge"))

    var output: Graph[(String, Double), PartitionID] = null

    if (existingGraph != null) {
      logger.debug("Graphs should be merged...")
      output = Graph(existingGraph.vertices.union(graph.vertices).distinct(),
        existingGraph.edges.union(existingGraph.edges).distinct())

      logger.debug("------------Merged Graph------------------")
      output.vertices.collect().foreach(logger.debug)
    } else {
      output = graph
    }

    println("Metadata Task")
    var visualization = visualizationUtil.prepareGraphVisualization(output)
    Store.httpMessageStore.addMessage(new HttpExportMessage(visualization, "http://localhost:3000/ingress"))
    output

  }
}