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

  def createGraph(vertexList: util.ArrayList[String], sc: SparkContext): Graph[(String), Int] = {
//    val conf = new SparkConf()
//      .setAppName("GraphCreator")
//      .setMaster("local[2]")
//
//    val sc = new SparkContext(conf)

//    var vertex: util.ArrayList[String] = new util.ArrayList[String]()
//    vertex.add("{\"sensor1\",\"100\"}")
//    vertex.add("{\"sensor2\",\"200\"}")
//    vertex.add("{\"sensor3\",\"300\"}")
//    vertex.add("{\"sensor4\",\"400\"}")
//    vertex.add("{\"sensor5\",\"500\"}")
//    vertex.add("{\"sensor6\",\"600\"}")

    val v = getVertexArrayFromArrayList(vertexList)

    val e = getEdgeArrayFromVertexArray(v)


    val vertexRD: RDD[(Long, (String))] = sc.parallelize(v.toList)
    val edgeRDD: RDD[Edge[Int]] = sc.parallelize(e.toList)

    val graph: Graph[(String), Int] = Graph(vertexRD, edgeRDD)


//    for ((id, (sensor)) <- graph.vertices.filter { case (id, (sensor)) => id > 0L }.collect) {
//      println(s"$id is $sensor")
//    }

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