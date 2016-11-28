package edu.sjsu.mithai.graphX

import org.apache.spark.graphx._
import org.apache.spark.graphx.lib.ShortestPaths

object GraphProcessor {

  def min(graph: Graph[(String, Double), PartitionID]): (VertexId, (String, Double)) = {
    return graph.vertices.min()(new Ordering[Tuple2[VertexId, (String, Double)]]() {
      override def compare(x: (VertexId, (String, Double)), y: (VertexId, (String, Double))): Int =
        Ordering[Double].compare(x._2._2, y._2._2)
    })
  }

  def max(graph: Graph[(String, Double), PartitionID]): (VertexId, (String, Double)) = {
    return graph.vertices.max()(new Ordering[Tuple2[VertexId, (String, Double)]]() {
      override def compare(x: (VertexId, (String, Double)), y: (VertexId, (String, Double))): Int =
        Ordering[Double].compare(x._2._2, y._2._2)
    })
  }

  def shortestPath(graph: Graph[(String, Double), PartitionID], entry: String): String = {

    //TODO : List entry points in the graph (Need to add identifiers in graph properties)
    val firstVId = entry.hashCode

    println("Vertex ID --->" + firstVId)
    val vert: Seq[VertexId] = graph.vertices.collect().map(x=>x._1).toSeq
    val result = ShortestPaths.run(graph, vert)
    if (result.vertices != null && result.vertices.count() > 1) {
      val shortPath = result.vertices.filter(
        {
          case (vid, path) => {
            vid == firstVId
          }
        }).first()._2
      //Finds the non zero min value
      var second = shortPath.filterNot(_._2 == 0).min
      println("Shortest Path from vertices " + entry + " is: " + second._1 + " with length: " + second._2)
      return second._1.toString
    }
    return null;
  }

  def average(graph: Graph[(String, Double), PartitionID]): Double = {
    return graph.vertices.map(_._2._2).sum() / graph.vertices.count()
  }

  def graphOperation(f:Graph[(String, Double), PartitionID] => (VertexId, (String, Double)), graph: Graph[(String, Double), PartitionID]): (VertexId, (String, Double)) = {
    if (graph!=null) {
      val result = f(graph);
      return result;
    }
    else return f(graph);
  }

}


