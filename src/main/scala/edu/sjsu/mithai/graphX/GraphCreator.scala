package edu.sjsu.mithai.graphX

/**
  * Created by Madhura on 9/18/16.
  */

import java.util

import org.apache.spark.{SparkConf, SparkContext}
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

  def createGraph[D:ClassTag](vertexList: List[D], sc: SparkContext): Graph[D, PartitionID] = {

    def getEdgeArrayFromVertexArray[D](va: List[(VertexId, D)]): List[Edge[PartitionID]] = {
      val edge = new ListBuffer[Edge[PartitionID]]()
      for (i <- va.indices)
        for (j <- va.indices){
          if (i != j)
            edge += Edge(i.toLong, j.toLong, 1)
        }
      return edge.toList
    }

    def getVertexArrayFromArrayList[D](al: List[D]): List[(VertexId, D)] = {
      val v = new ListBuffer[(VertexId,D)]()
     var curr: Long = 0L
      al.foreach(
        s => {
          curr += 1
          v.add((curr,s))
        }
      )
      return v.toList
    }

    val v = getVertexArrayFromArrayList(vertexList)
    val e = getEdgeArrayFromVertexArray(v)
    val vertexRD: RDD[(VertexId, D)] = sc.parallelize(v)
    val edgeRDD: RDD[Edge[PartitionID]] = sc.parallelize(e)
    //TODO: parameterize ED-edge attribute type if possible
    val graph: Graph[D, PartitionID] = Graph(vertexRD, edgeRDD)

    return graph

  }
}