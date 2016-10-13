package edu.sjsu.mithai.graphX

/**
  * Created by Madhura on 9/18/16.
  */

import java.util

import edu.sjsu.mithai.data.AvroGraphMetadata
import org.apache.spark.SparkContext
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD

import scala.collection.JavaConversions._
import scala.collection.mutable.ListBuffer
import scala.reflect.ClassTag

object GraphCreator {
  def main(vertexList: util.ArrayList[String], sc: SparkContext) {
    val gc = new GraphCreator()
    //  gc.createGraph(vertexList,sc)
  }
}

class GraphCreator {

  def createGraph[D: ClassTag](vertexList: List[D], sc: SparkContext): Graph[D, PartitionID] = {
    val v = getVertexArrayFromArrayList(vertexList)
    val e = getEdgeArrayFromVertexArray(v)
    val vertexRD: RDD[(VertexId, D)] = sc.parallelize(v)
    val edgeRDD: RDD[Edge[PartitionID]] = sc.parallelize(e)
    //TODO: parameterize ED-edge attribute type if possible
    val graph: Graph[D, PartitionID] = Graph(vertexRD, edgeRDD)

    return graph

  }

  def getEdgeArrayFromVertexArray[D](va: List[(VertexId, D)]): List[Edge[PartitionID]] = {
    val edge = new ListBuffer[Edge[PartitionID]]()
    for (i <- va.indices)
      for (j <- va.indices) {
        if (i != j)
          edge += Edge(i.toLong, j.toLong, 1)
      }
    return edge.toList
  }

  def getVertexArrayFromArrayList[D](al: List[D]): List[(VertexId, D)] = {
    val v = new ListBuffer[(VertexId, D)]()
    var curr: Long = 0L
    al.foreach(
      s => {
        curr += 1
        v.add((curr, s))
      }
    )
    return v.toList
  }

  def createMetaDataGraph(agm:AvroGraphMetadata , sc:SparkContext):Graph[(String, Double),PartitionID]={
    var edges = new ListBuffer[Edge[PartitionID]]
    var vert = new util.HashSet[(VertexId, (String, Double))]()
    agm.getLocalGraph.toList.foreach(p=>{
      p.getConnectedDevices.toList.foreach(x=>{
        edges+= Edge(p.getDeviceId.hashCode,x.hashCode)
        vert.add((x.hashCode,(x, 0)))
      })
      vert.add(p.getDeviceId.hashCode,(p.getDeviceId, 0))
    })

    val vertexRD: RDD[(VertexId, (String, Double))] = sc.parallelize(vert.toList)
    val edgeRDD: RDD[Edge[PartitionID]] = sc.parallelize(edges)
    //TODO: parameterize ED-edge attribute type if possible
    val graph: Graph[(String, Double), PartitionID] = Graph(vertexRD, edgeRDD)

    println("Graph in scala:")
    graph.vertices.collect().foreach(x=>println(x+"<--metaVertex"))
    graph.edges.collect().foreach(x=>println(x+"<--metaEdge"))

    return graph

  }
}