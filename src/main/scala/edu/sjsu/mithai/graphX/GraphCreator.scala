package edu.sjsu.mithai.graphX

/**
  * Created by Madhura on 9/18/16.
  */

import java.util

import org.apache.spark.SparkContext
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD

import scala.collection.JavaConversions._

object GraphCreator {
  def main(vertexList: util.ArrayList[String], sc: SparkContext) {
    val gc = new GraphCreator()
    gc.createGraph(vertexList,sc)
  }
}

class GraphCreator {

  def createGraph(vertexList: List[String], sc: SparkContext): Graph[(String), Int] = {

    def getVertexArrayFromArrayList(al: List[String]): util.ArrayList[(Long, String)] = {
      val v = new util.ArrayList[(Long, String)]()
      var curr: Long = 0L
      al.foreach(
        s => {
          curr += 1
          v.add((curr, s))
        }
      )
      return v
    }

    val v = getVertexArrayFromArrayList(vertexList)

    val e = getEdgeArrayFromVertexArray(v)


    val vertexRD: RDD[(Long, (String))] = sc.parallelize(v.toList)

    val edgeRDD: RDD[Edge[Int]] = sc.parallelize(e.toList)

    val graph: Graph[(String), Int] = Graph(vertexRD, edgeRDD)

    return graph

  }

  def createGraph(vertexList: util.ArrayList[String], sc: SparkContext): Graph[(String), Int] = {

    val v = getVertexArrayFromArrayList(vertexList)
    val e = getEdgeArrayFromVertexArray(v)
    val vertexRD: RDD[(Long, (String))] = sc.parallelize(v.toList)
    val edgeRDD: RDD[Edge[Int]] = sc.parallelize(e.toList)
    val graph: Graph[(String), Int] = Graph(vertexRD, edgeRDD)
    return graph

  }

  def getVertexArrayFromArrayList(al: util.ArrayList[String]): util.ArrayList[(Long, String)] = {
    val v = new util.ArrayList[(Long, String)]()
    var curr: Long = 0L
    al.toList.foreach(
      s => {
        curr += 1
        v.add((curr, s))
      }
    )
    //    print("\n"+"------>"+v)
    return v
  }

  def getEdgeArrayFromVertexArray(va: util.ArrayList[(Long, String)]): util.ArrayList[Edge[PartitionID]] = {

    val edge = new util.ArrayList[Edge[Int]]()
    for (i <- 0 until va.size())
      for (j <- 0 until va.size()) {
        if (i != j)
          edge += Edge(i.toLong, j.toLong, 1)
      }

    //    print("Edge Array----> "+edge)

    return edge
  }
}