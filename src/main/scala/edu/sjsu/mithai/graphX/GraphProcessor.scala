package edu.sjsu.mithai.graphX

import java.lang.reflect.Array

import edu.sjsu.mithai.spark.Store
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

  def shortestPath(graph: Graph[(String, Double), PartitionID], from: VertexId): String = {

    val vert: Seq[VertexId] = graph.vertices.collect().map(x=>x._1).toSeq
    val result = ShortestPaths.run(graph, vert)
    if (result != null) {
      val shortPath = result.vertices.filter({ case (vId, _) => vId == from }).first()._2
      println("Shortest Path from vertice: " + from + " is: " + shortPath.toString())
      return shortPath.toString()
    }
    return "";
  }

  def average(graph: Graph[(String, Double), PartitionID]): Double = {
    val sum: Double =0
    //graph.vertices.collect().reduceLeft((,x) => { (x._2._2)})
    graph.vertices.collect().foreach(x => sum + x._2._2)
    val average  = sum/graph.vertices.count()
    return average
  }

  def graphOperation(f:Graph[(String, Double), PartitionID] => (VertexId, (String, Double)), graph: Graph[(String, Double), PartitionID]): (VertexId, (String, Double)) = {
    if (graph!=null) {
      val result = f(graph);
      return result;
    }
    else return f(graph);
  }

  def main(args: Array[String]) {
    graphOperation(max,Store.graph)
    graphOperation(min,Store.graph)
  }
}


