package edu.sjsu.mithai.graphX

import java.util

import org.apache.spark.graphx._
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Madhura on 9/26/16.
  */

object GraphProc {

  def main(args: Array[String]) {
    val gp = new GraphProc()

    val conf = new SparkConf()
      .setAppName("GraphCreator")
      .setMaster("local[2]")

    gp.gProcessor(conf)
  }

}

class GraphProc {

  def gProcessor(conf: SparkConf) {

    val sc = new SparkContext(conf)

    val vertex: util.ArrayList[String] = new util.ArrayList[String]
    vertex.add("{\"sensor1\",\"100\"}")
    vertex.add("{\"sensor2\",\"200\"}")
    vertex.add("{\"sensor3\",\"300\"}")
    vertex.add("{\"sensor4\",\"400\"}")
    vertex.add("{\"sensor5\",\"500\"}")
    vertex.add("{\"sensor6\",\"600\"}")

    //generated graph
    val graph = new GraphCreator().createGraph(vertex, sc)

    //TODO: Add graph processing functions
    for ((id, (sensor)) <- graph.vertices.filter { case (id, (sensor)) => id > 0L }.collect) {
      println(s"$id is $sensor")
    }

    //Function to collect Neighbor Ids for each vertex
    val v = graph.collectNeighborIds(edgeDirection = EdgeDirection.Either)
    graph.aggregateMessages[Int](x => x.sendToDst(1), _ + _, TripletFields.None)
    print(s"-----------------------------------------------------------------------")
    v.collect().foreach(x => print(x._1 + "\nVertexRDD\n"))


  }

  def process(graph: Graph[(String), Int]): Unit = {
    val v = graph.collectNeighborIds(edgeDirection = EdgeDirection.Either)
    graph.aggregateMessages[Int](x => x.sendToDst(1), _ + _, TripletFields.None)
    print(s"-----------------------------------------------------------------------")
    v.collect().foreach(x => print(x._1 + "\nVertexRDD\n"))
  }

}