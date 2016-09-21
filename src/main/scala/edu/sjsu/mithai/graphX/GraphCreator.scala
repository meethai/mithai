package edu.sjsu.mithai.graphX

/**
  * Created by Madhura on 9/18/16.
  */

import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}


object GraphCreator {

  def main(args: Array[String]) {

    val conf = new SparkConf()
      .setAppName("GraphCreator")
      .setMaster("local[2]")

    val sc = new SparkContext(conf)
    print("___________________________________________________________________________________________")

    val vertexArray = Array(
      (1L, ("Sensor1", 28)),
      (2L, ("Sensor2", 27)),
      (3L, ("Sensor3", 65)),
      (4L, ("Sensor4", 42)),
      (5L, ("Sensor5", 55)),
      (6L, ("Sensor6", 50))
    )
    val edgeArray = Array(
      Edge(2L, 1L, 7),
      Edge(2L, 4L, 2),
      Edge(3L, 2L, 4),
      Edge(3L, 6L, 3),
      Edge(4L, 1L, 1),
      Edge(5L, 2L, 2),
      Edge(5L, 3L, 8),
      Edge(5L, 6L, 3)
    )
    val vertexRDD: RDD[(Long, (String, Int))] = sc.parallelize(vertexArray)
    val edgeRDD: RDD[Edge[Int]] = sc.parallelize(edgeArray)

    val graph: Graph[(String, Int), Int] = Graph(vertexRDD, edgeRDD)

    for ((id, (sensor, tempval)) <- graph.vertices.filter { case (id, (sensor, tempval)) => tempval > 30 }.collect) {
      println(s"$sensor is $tempval")
    }

  }
}